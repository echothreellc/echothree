// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.util.server.persistence;

import com.echothree.util.common.exception.PersistenceEncryptionException;
import com.echothree.util.common.persistence.BaseKeys;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HexFormat;
import java.util.List;
import javax.crypto.Cipher;
import org.junit.jupiter.api.Test;
import static com.echothree.util.server.persistence.EncryptionUtils.EntityEncryptionKeyTypes.*;
import static org.junit.jupiter.api.Assertions.*;

public class EncryptionUtilsTest extends EncryptionTestSupport {

    private static final String FIRST_CACHE_KEY = "/com/echothree/security/base/BaseKey1";
    private static final String SECOND_CACHE_KEY = "/com/echothree/security/base/BaseKey2";
    private static final String IV = Base64.getEncoder().encodeToString(HexFormat.of().parseHex("03".repeat(16)));

    private String hashName(String name) throws Exception {
        return Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-256").digest(name.getBytes(StandardCharsets.UTF_8)));
    }

    private void seedKey(String name, byte[] key) throws Exception {
        var wrapped = transform(secondBaseKey, transform(firstBaseKey, key, Cipher.ENCRYPT_MODE), Cipher.ENCRYPT_MODE);
        control.put(hashName(name), Base64.getEncoder().encodeToString(wrapped));
    }

    private BaseKeys registeredBaseKeys() {
        control.createBaseEncryptionKey(null, firstBaseKey, secondBaseKey, null);
        // The fixture keys are all-zero and all-one bytes, so their XOR is the second key.
        return new BaseKeys(firstBaseKey, secondBaseKey, secondBaseKey);
    }

    @Test
    public void generatedIvIsCanonicalBase64AndSixteenBytes() {
        var iv = encryption.generateRowInitializationVector();
        var decoded = Base64.getDecoder().decode(iv);
        assertEquals(16, decoded.length);
        assertEquals(iv, Base64.getEncoder().encodeToString(decoded));
        assertNotEquals(iv, encryption.generateRowInitializationVector());
    }

    @Test
    public void encryptionMatchesIndependentOpenSslVector() throws Exception {
        seedKey("TEST.Card", HexFormat.of().parseHex("02".repeat(32)));
        var factory = factory("TEST", "Card");
        var plain = "Confidential café 日本語 123";
        // Independently calculated with openssl enc -aes-256-cbc, key 02 repeated 32 times and IV 03 repeated 16 times.
        var expected = "YRyNchntbszXiqzb2XWqDFW41je6Tkqmex8CSnYM4w2VZkW7amHZCPz+F97tUEI6";
        assertEquals(expected, encryption.encrypt(factory, IV, plain));
        assertEquals(plain, encryption.decrypt(factory, IV, expected));
        assertEquals(expected, encryption.encrypt("TEST.Card", IV, false, plain));
        assertEquals(0, control.creates);
    }

    @Test
    public void internalAndExternalRoundTripsHandleEmptyUnicodeAndMultipleBlocks() {
        for(var external : List.of(false, true)) {
            for(var value : List.of("", "hello", "café 日本語 🔐", "0123456789".repeat(40))) {
                var encrypted = encryption.encrypt("TEST.Card", IV, external, value);
                assertNotEquals(value, encrypted);
                assertEquals(value, encryption.decrypt("TEST.Card", IV, external, encrypted));
            }
        }
        assertEquals(2, control.creates);
    }

    @Test
    public void differentIvsChangeCiphertextWhilePreservingDecryption() {
        var factory = factory("TEST", "Card");
        var otherIv = Base64.getEncoder().encodeToString(new byte[16]);
        var first = encryption.encrypt(factory, IV, "same value");
        var second = encryption.encrypt(factory, otherIv, "same value");
        assertNotEquals(first, second);
        assertEquals("same value", encryption.decrypt(factory, IV, first));
        assertEquals("same value", encryption.decrypt(factory, otherIv, second));
        assertEquals(1, control.creates);
    }

    @Test
    public void nullValuesDoNotRequireBaseKeysOrCreateEntityKeys() {
        securityCache.clear();
        var factory = factory("TEST", "Card");
        assertNull(encryption.encrypt(factory, null, null));
        assertNull(encryption.decrypt(factory, null, null));
        assertNull(encryption.encrypt("Card", null, true, null));
        assertNull(encryption.decrypt("Card", null, true, null));
        assertEquals(0, control.creates);
    }

    @Test
    public void keyTypesUseDistinctStableLookupNames() throws Exception {
        var factory = factory("TEST", "Card");
        for(var type : EncryptionUtils.EntityEncryptionKeyTypes.values()) {
            encryption.getEntityEncryptionKey("AES", 256, factory, type);
        }
        assertEquals(3, control.entries.size());
        for(var name : List.of("TEST.Card", "TEST.Card.#EXTERNAL#", "TEST.Card.#HMAC_SHA256#")) {
            assertTrue(control.entries.containsKey(hashName(name)), name);
        }
    }

    @Test
    public void keysAreScopedByVendorAndEntity() {
        var first = encryption.getEntityEncryptionKey("AES", 256, factory("FIRST", "Card"), INTERNAL);
        var otherVendor = encryption.getEntityEncryptionKey("AES", 256, factory("SECOND", "Card"), INTERNAL);
        var otherEntity = encryption.getEntityEncryptionKey("AES", 256, factory("FIRST", "Other"), INTERNAL);
        assertFalse(java.util.Arrays.equals(first.getEncoded(), otherVendor.getEncoded()));
        assertFalse(java.util.Arrays.equals(first.getEncoded(), otherEntity.getEncoded()));
        assertArrayEquals(first.getEncoded(), encryption.getEntityEncryptionKey("AES", 256, factory("FIRST", "Card"), INTERNAL).getEncoded());
        assertEquals(3, control.creates);
    }

    @Test
    public void generatedKeyHonorsSizeAndIsStoredWrapped() throws Exception {
        var key = encryption.getEntityEncryptionKey("AES", 128, factory("TEST", "Card"), INTERNAL);
        assertEquals("AES", key.getAlgorithm());
        assertEquals(16, key.getEncoded().length);
        var stored = Base64.getDecoder().decode(control.entries.get(hashName("TEST.Card")).getSecretKey());
        assertFalse(java.util.Arrays.equals(key.getEncoded(), stored));
        assertArrayEquals(key.getEncoded(), encryption.decryptDataUsingBaseKeys(stored));
    }

    @Test
    public void hmacKeyRetainsItsAlgorithmAndBytesAfterReload() {
        var factory = factory("TEST", "Hash");
        var created = encryption.getEntityEncryptionKey("HmacSHA256", 256, factory, HMAC_SHA256);
        var reloaded = encryption.getEntityEncryptionKey("HmacSHA256", 256, factory, HMAC_SHA256);
        assertEquals("HmacSHA256", created.getAlgorithm());
        assertEquals("HmacSHA256", reloaded.getAlgorithm());
        assertArrayEquals(created.getEncoded(), reloaded.getEncoded());
        assertEquals(1, control.creates);
    }

    @Test
    public void unsupportedAlgorithmFailsWithoutPersistingAKey() {
        var error = assertThrows(PersistenceEncryptionException.class,
                () -> encryption.getEntityEncryptionKey("NoSuchAlgorithm", 256, factory("TEST", "Card"), INTERNAL));
        assertInstanceOf(NoSuchAlgorithmException.class, error.getCause());
        assertEquals(0, control.creates);
    }

    @Test
    public void missingAndIncompleteBaseKeysRejectEncryptionAndDecryption() {
        var encrypted = encryption.encrypt("Card", IV, true, "value");
        securityCache.remove(SECOND_CACHE_KEY);
        assertThrows(PersistenceEncryptionException.class, () -> encryption.encrypt("Card", IV, true, "value"));
        assertThrows(PersistenceEncryptionException.class, () -> encryption.decrypt("Card", IV, true, encrypted));
        securityCache.clear();
        assertThrows(PersistenceEncryptionException.class, () -> encryption.encrypt("NewCard", IV, true, "value"));
        assertThrows(PersistenceEncryptionException.class, () -> encryption.decrypt("Card", IV, true, encrypted));
        assertEquals(1, control.creates);
    }

    @Test
    public void malformedIvAndCiphertextAreRejected() {
        encryption.encrypt("Card", IV, true, "value");
        assertThrows(IllegalArgumentException.class, () -> encryption.encrypt("Card", "!", true, "value"));
        var shortIv = Base64.getEncoder().encodeToString(new byte[15]);
        assertThrows(PersistenceEncryptionException.class, () -> encryption.encrypt("Card", shortIv, true, "value"));
        assertThrows(IllegalArgumentException.class, () -> encryption.decrypt("Card", IV, true, "!"));
        assertThrows(PersistenceEncryptionException.class,
                () -> encryption.decrypt("Card", IV, true, Base64.getEncoder().encodeToString(new byte[1])));
        assertThrows(PersistenceEncryptionException.class, () -> encryption.decryptDataUsingBaseKeys(new byte[1]));
    }

    @Test
    public void baseKeyGenerationCreatesRecoverableKeysAndCachesThem() {
        var keys = encryption.generateBaseKeys(null, null);
        assertNotNull(keys);
        assertEquals(3, keys.getBaseKeyCount());
        assertEquals("TestBaseKey1", keys.getBaseEncryptionKeyName());
        assertEquals(keys.getBaseKey1(), securityCache.get(FIRST_CACHE_KEY));
        assertEquals(keys.getBaseKey2(), securityCache.get(SECOND_CACHE_KEY));
        var recovered = new BaseKeys(keys.getBaseKey1(), null, keys.getBaseKey3());
        encryption.loadBaseKeys(recovered);
        assertEquals(keys.getBaseKey2(), recovered.getBaseKey2());
    }

    @Test
    public void baseKeyGenerationRefusesToReplaceKeysWhenEntityKeysExist() {
        encryption.encrypt("Card", IV, true, "value");
        assertNull(encryption.generateBaseKeys(null, null));
        assertEquals(0, control.baseCreates);
        assertEquals(firstBaseKey, securityCache.get(FIRST_CACHE_KEY));
        assertEquals(secondBaseKey, securityCache.get(SECOND_CACHE_KEY));
    }

    @Test
    public void loadingRecoversEitherMissingBaseKey() {
        var keys = registeredBaseKeys();
        for(var supplied : List.of(new BaseKeys(firstBaseKey, secondBaseKey),
                new BaseKeys(null, secondBaseKey, keys.getBaseKey3()), new BaseKeys(firstBaseKey, null, keys.getBaseKey3()), keys)) {
            securityCache.clear();
            encryption.loadBaseKeys(supplied);
            assertEquals(firstBaseKey, securityCache.get(FIRST_CACHE_KEY));
            assertEquals(secondBaseKey, securityCache.get(SECOND_CACHE_KEY));
        }
    }

    @Test
    public void loadingRejectsMissingIncompleteUnknownAndInvalidRecoveryKeys() {
        securityCache.clear();
        for(var keys : List.of(new BaseKeys(null, null), new BaseKeys(firstBaseKey, null),
                new BaseKeys(firstBaseKey, secondBaseKey), new BaseKeys(firstBaseKey, secondBaseKey, firstBaseKey))) {
            assertThrows(PersistenceEncryptionException.class, () -> encryption.loadBaseKeys(keys));
            assertTrue(securityCache.isEmpty());
        }
    }

    @Test
    public void loadingRejectsInactiveBaseKeys() {
        var keys = registeredBaseKeys();
        workflowStepName = "INACTIVE";
        securityCache.clear();
        assertThrows(PersistenceEncryptionException.class, () -> encryption.loadBaseKeys(keys));
        assertTrue(securityCache.isEmpty());
    }

    @Test
    public void changingBaseKeysRequiresAllThreeKeys() {
        registeredBaseKeys();
        assertThrows(PersistenceEncryptionException.class,
                () -> encryption.changeBaseKeys(null, new BaseKeys(firstBaseKey, secondBaseKey), null));
        assertEquals(1, control.baseCreates);
    }

    @Test
    public void changingBaseKeysRewrapsAesAndHmacKeysWithoutChangingTheirValues() {
        var oldKeys = registeredBaseKeys();
        var factory = factory("TEST", "Card");
        var ciphertext = encryption.encrypt(factory, IV, "retain me");
        var fingerprint = hmac.hash(factory, "4111111111111111");
        var oldWrapped = control.entries.values().stream().map(e -> e.getSecretKey()).toList();
        var newKeys = encryption.changeBaseKeys(null, oldKeys, null);
        assertNotNull(newKeys);
        assertEquals(3, newKeys.getBaseKeyCount());
        assertEquals(2, control.entries.size());
        for(var entry : control.entries.values()) {
            assertFalse(oldWrapped.contains(entry.getSecretKey()));
        }
        assertEquals("retain me", encryption.decrypt(factory, IV, ciphertext));
        assertEquals(fingerprint, hmac.hash(factory, "4111111111111111"));
        securityCache.clear();
        encryption.loadBaseKeys(newKeys);
        assertEquals("retain me", encryption.decrypt(factory, IV, ciphertext));
    }

}

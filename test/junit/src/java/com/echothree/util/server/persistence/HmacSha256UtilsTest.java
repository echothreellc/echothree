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
import java.util.Base64;
import java.util.HexFormat;
import javax.crypto.Cipher;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HmacSha256UtilsTest extends EncryptionTestSupport {

    private void assertDigest(String expectedHex, String actual) {
        assertEquals(expectedHex, HexFormat.of().formatHex(Base64.getDecoder().decode(actual)));
        assertEquals(44, actual.length());
        assertEquals(actual, Base64.getEncoder().encodeToString(Base64.getDecoder().decode(actual)));
    }

    // RFC 4231, test cases 1 and 2: https://www.rfc-editor.org/rfc/rfc4231.html
    @Test
    public void storedKeysMatchStandardTestVectors() throws Exception {
        assertDigest("b0344c61d8db38535ca8afceaf0bf12b881dc200c9833da726e9376c2e32cff7",
                hmac.hash(seed("TEST", "First", "0b".repeat(20)), "Hi There"));
        assertDigest("5bdcc146bf60754e6a042426089575c75a003f089d2739839dec58b964ec3843",
                hmac.hash(seed("TEST", "Second", "4a656665"), "what do ya want for nothing?"));
        assertEquals(0, control.creates);
    }

    @Test
    public void emptyAndUtf8InputsMatchIndependentVectors() throws Exception {
        var factory = seed("TEST", "Unicode", "000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f");

        // Independently calculated with Python hmac and hashlib.
        assertEquals("04tCCW2A9F+Ca0Sp1WB95ySWpBXT9KGoyI47udqNwcs=", hmac.hash(factory, ""));
        assertEquals("OJaNopYqRUjoQtwahaLZKlb900dLw8bBBo9aZLZl7Ww=", hmac.hash(factory, "p\u00e4ss\u96ea\ud83d\udd10"));
    }

    @Test
    public void repeatedCallsDependOnVendorEntityAndValue() throws Exception {
        var first = seed("FIRST", "Card", "01".repeat(32));
        var otherVendor = seed("SECOND", "Card", "02".repeat(32));
        var otherEntity = seed("FIRST", "Other", "03".repeat(32));
        var expected = hmac.hash(first, "test");

        assertNotEquals(expected, hmac.hash(otherVendor, "test"));
        assertNotEquals(expected, hmac.hash(otherEntity, "test"));
        assertNotEquals(expected, hmac.hash(first, "other"));
        assertEquals(expected, hmac.hash(factory("FIRST", "Card"), "test"));
        assertEquals(0, control.creates);
    }

    @Test
    public void newKeyIsWrappedAndReusedOnSubsequentCalls() throws Exception {
        var factory = factory("TEST", "NewKey");
        var firstHash = hmac.hash(factory, "test");
        var stored = control.entries.get(keyNameHash("TEST", "NewKey"));
        assertNotNull(stored);
        var wrapped = Base64.getDecoder().decode(stored.getSecretKey());
        var raw = transform(firstBaseKey, transform(secondBaseKey, wrapped, Cipher.DECRYPT_MODE), Cipher.DECRYPT_MODE);

        assertEquals(32, raw.length);
        assertEquals(44, firstHash.length());
        assertEquals(firstHash, hmac.hash(factory, "test"));
        assertEquals(1, control.creates);
        assertEquals(1, control.entries.size());
    }

    @Test
    public void unavailableBaseKeysFailForStoredAndNewKeys() throws Exception {
        var stored = seed("TEST", "Stored", "01".repeat(32));
        securityCache.clear();

        assertThrows(PersistenceEncryptionException.class, () -> hmac.hash(stored, "test"));
        assertThrows(PersistenceEncryptionException.class, () -> hmac.hash(factory("TEST", "New"), "test"));
        assertEquals(0, control.creates);
    }

}

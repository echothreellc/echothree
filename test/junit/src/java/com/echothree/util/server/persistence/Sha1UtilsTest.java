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

import com.echothree.util.common.persistence.BaseKey;
import java.util.Base64;
import java.util.HexFormat;
import javax.crypto.spec.SecretKeySpec;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Sha1UtilsTest {

    private final ShaUtils sha = CryptoTestUtils.sha1Utils();

    // Fixed vectors calculated independently with Python hashlib; all outputs use padded Base64.
    private static final String SALT = "AAECAwQFBgc=";
    private static final String PASSWORD = "p\u00e4ss\u96ea\ud83d\udd10";

    private void assertDigest(String expected, String actual) {
        assertEquals(expected, actual);
        assertEquals(28, actual.length());
        assertEquals(20, Base64.getDecoder().decode(actual).length);
    }

    @Test
    public void generatedSaltHasExpectedBase64LengthAndByteCount() {
        var salt = sha.generateSalt();
        var decoded = Base64.getDecoder().decode(salt);

        assertEquals(12, salt.length());
        assertEquals(8, decoded.length);
        assertEquals(salt, Base64.getEncoder().encodeToString(decoded));
        var encoded = sha.encode(salt, PASSWORD);
        assertEquals(28, encoded.length());
        assertEquals(20, Base64.getDecoder().decode(encoded).length);
    }

    @Test
    public void emptyInputMatchesKnownDigest() {
        assertDigest("2jmj7l5rSw0yVb/vlWAYkK/YBwk=", sha.hash(""));
        assertDigest("2jmj7l5rSw0yVb/vlWAYkK/YBwk=", sha.hash(new byte[0]));
    }

    @Test
    public void asciiInputMatchesKnownDigest() {
        assertDigest("qZk+NkcGgWq6PiVxeFDCbJzQ2J0=", sha.hash("abc"));
        assertDigest("qZk+NkcGgWq6PiVxeFDCbJzQ2J0=", sha.hash(new byte[] {97, 98, 99}));
    }

    @Test
    public void defaultStringHashUsesUtf8() {
        assertDigest("UCFBCgiceXQX1JWrtrePnKdV9fc=", sha.hash(PASSWORD));
        assertDigest("UCFBCgiceXQX1JWrtrePnKdV9fc=", sha.hash(HexFormat.of().parseHex("70c3a47373e99baaf09f9490")));
    }

    @Test
    public void binaryInputMatchesKnownDigest() {
        assertDigest("R/bmQzliipidvtlYvrpZRNDqbXs=", sha.hash(new byte[] {0, 1, 127, (byte)128, (byte)255}));
    }

    @Test
    public void saltedPasswordMatchesKnownDigestAndDependsOnBothInputs() {
        assertDigest("0Id5xevieQcaKYJ7oiNFcuhzTvA=", sha.encode(SALT, PASSWORD));
        assertDigest("4qUXIJvxASwbsEbL4aF2loRltQM=", sha.encode(SALT, PASSWORD + "!"));
        assertDigest("9WJp8VRhO7OnPM4VUA0CLNGNWmA=", sha.encode("BwYFBAMCAQA=", PASSWORD));
        assertDigest("0Id5xevieQcaKYJ7oiNFcuhzTvA=", sha.encode(SALT, PASSWORD));
    }

    private BaseKey baseKey(String keyHex, String ivHex) {
        return new BaseKey(new SecretKeySpec(HexFormat.of().parseHex(keyHex), "AES"),
                HexFormat.of().parseHex(ivHex));
    }

    @Test
    public void baseKeyEncodingMatchesKnownDigestAndPreservesOrder() {
        var first = baseKey("000102030405060708090a0b0c0d0e0f", "101112131415161718191a1b1c1d1e1f");
        var second = baseKey("202122232425262728292a2b2c2d2e2f", "303132333435363738393a3b3c3d3e3f");

        assertDigest("xhONUU/6ITW/zg7QuPrGVmmRfsc=", sha.encode(first, second));
        assertDigest("isnvn96qfWa+fzHFb9rfRZa/Wjw=", sha.encode(second, first));
    }

    @Test
    public void paddingAndBlockBoundariesMatchIndependentVectors() {
        assertDigest("wci73CJ5bijA4VFj0giZtlYh1lo=", sha.hash("a".repeat(55)));
        assertDigest("wtszD2CDhUyZ1LW/tujynyAb5pk=", sha.hash("a".repeat(56)));
        assertDigest("A/CfWxWKeoza2SC93Cm4HBilUfU=", sha.hash("a".repeat(63)));
        assertDigest("AJi6gktcFkJ716ESKlpEKiXsZE0=", sha.hash("a".repeat(64)));
        assertDigest("EWVTJscI1wMZviYQ6KV9mluVnTs=", sha.hash("a".repeat(65)));
        assertDigest("idlfoy7USnxhC37jhRfd9X4LuXU=", sha.hash("a".repeat(127)));
        assertDigest("rVs/28tSZ3jCg50vFR6nU5leJqA=", sha.hash("a".repeat(128)));
    }

    @Test
    public void largeInputMatchesIndependentVector() {
        assertDigest("NKqXPNTE2qT2Husr260nMWU0AW8=", sha.hash("a".repeat(1_000_000)));
    }

    @Test
    public void emptyPasswordAndSaltMatchIndependentVectors() {
        assertDigest("MUt3qrGi276EXd6GeUO2ZjOM6L4=", sha.encode(SALT, ""));
        assertDigest("3EyTmE++yCt6yYqpHBCeHibVVIM=", sha.encode("", ""));
    }

    @Test
    public void everyBaseKeyAndIvContributesToTheDigestWithoutBeingModified() {
        var first = baseKey("000102030405060708090a0b0c0d0e0f", "101112131415161718191a1b1c1d1e1f");
        var second = baseKey("202122232425262728292a2b2c2d2e2f", "303132333435363738393a3b3c3d3e3f");
        var originalFirstIv = first.getIv().clone();
        var originalSecondIv = second.getIv().clone();

        assertDigest("1N54viNF8e+QdzfSBWQg6LgwD8g=", sha.encode(baseKey("ff0102030405060708090a0b0c0d0e0f", "101112131415161718191a1b1c1d1e1f"), second));
        assertDigest("WeN0WNE+yiBI3WYxxTFx+5LufSE=", sha.encode(baseKey("000102030405060708090a0b0c0d0e0f", "ef1112131415161718191a1b1c1d1e1f"), second));
        assertDigest("qc5hZKVt5hWBvCJT3f6qwUtKd5g=", sha.encode(first, baseKey("df2122232425262728292a2b2c2d2e2f", "303132333435363738393a3b3c3d3e3f")));
        assertDigest("+KmwK/XT5/YFkTTE/USpWzQAO74=", sha.encode(first, baseKey("202122232425262728292a2b2c2d2e2f", "cf3132333435363738393a3b3c3d3e3f")));
        sha.encode(first, second);
        assertArrayEquals(originalFirstIv, first.getIv());
        assertArrayEquals(originalSecondIv, second.getIv());
        assertArrayEquals(HexFormat.of().parseHex("000102030405060708090a0b0c0d0e0f"), first.getKey().getEncoded());
        assertArrayEquals(HexFormat.of().parseHex("202122232425262728292a2b2c2d2e2f"), second.getKey().getEncoded());
    }

}

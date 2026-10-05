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

public class Sha256UtilsTest {

    private final ShaUtils sha = Sha256Utils.getInstance();

    // Fixed vectors calculated independently with Python hashlib; all outputs use padded Base64.
    private static final String SALT = "AAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8=";
    private static final String PASSWORD = "p\u00e4ss\u96ea\ud83d\udd10";

    private void assertDigest(String expected, String actual) {
        assertEquals(expected, actual);
        assertEquals(44, actual.length());
        assertEquals(32, Base64.getDecoder().decode(actual).length);
    }

    @Test
    public void generatedSaltHasExpectedBase64LengthAndByteCount() {
        var salt = sha.generateSalt();
        var decoded = Base64.getDecoder().decode(salt);

        assertEquals(44, salt.length());
        assertEquals(32, decoded.length);
        assertEquals(salt, Base64.getEncoder().encodeToString(decoded));
        var encoded = sha.encode(salt, PASSWORD);
        assertEquals(44, encoded.length());
        assertEquals(32, Base64.getDecoder().decode(encoded).length);
    }

    @Test
    public void emptyInputMatchesKnownDigest() {
        assertDigest("47DEQpj8HBSa+/TImW+5JCeuQeRkm5NMpJWZG3hSuFU=", sha.hash(""));
        assertDigest("47DEQpj8HBSa+/TImW+5JCeuQeRkm5NMpJWZG3hSuFU=", sha.hash(new byte[0]));
    }

    @Test
    public void asciiInputMatchesKnownDigest() {
        assertDigest("ungWv48Bz+pBQUDeXa4iI7ADYaOWF3qctBD/YfIAFa0=", sha.hash("abc"));
        assertDigest("ungWv48Bz+pBQUDeXa4iI7ADYaOWF3qctBD/YfIAFa0=", sha.hash(new byte[] {97, 98, 99}));
    }

    @Test
    public void defaultStringHashUsesUtf8() {
        assertDigest("WgDXOXU30PKharU20J3n1Tf4s3+hHfypqjWb7WndpaY=", sha.hash(PASSWORD));
        assertDigest("WgDXOXU30PKharU20J3n1Tf4s3+hHfypqjWb7WndpaY=", sha.hash(HexFormat.of().parseHex("70c3a47373e99baaf09f9490")));
    }

    @Test
    public void binaryInputMatchesKnownDigest() {
        assertDigest("AVCpK7EhLNAFFrZf3gcEYUdgAAljh0/LsR6qc07oeAk=", sha.hash(new byte[] {0, 1, 127, (byte)128, (byte)255}));
    }

    @Test
    public void saltedPasswordMatchesKnownDigestAndDependsOnBothInputs() {
        assertDigest("VKZdtL416+2JJnEKqF9XxcT6c15py6XzQQqy6ndu5B0=", sha.encode(SALT, PASSWORD));
        assertDigest("e6xR8KqewrZIY2WJx30w0oTzFEmAwnJyAlyPlOjQkjk=", sha.encode(SALT, PASSWORD + "!"));
        assertDigest("EfwANkzcHW5ZAYwEk1rqbWnOMhTxVtqh7yre+nwhFms=", sha.encode("Hx4dHBsaGRgXFhUUExIREA8ODQwLCgkIBwYFBAMCAQA=", PASSWORD));
        assertDigest("VKZdtL416+2JJnEKqF9XxcT6c15py6XzQQqy6ndu5B0=", sha.encode(SALT, PASSWORD));
    }

    private BaseKey baseKey(String keyHex, String ivHex) {
        return new BaseKey(new SecretKeySpec(HexFormat.of().parseHex(keyHex), "AES"),
                HexFormat.of().parseHex(ivHex));
    }

    @Test
    public void baseKeyEncodingMatchesKnownDigestAndPreservesOrder() {
        var first = baseKey("000102030405060708090a0b0c0d0e0f", "101112131415161718191a1b1c1d1e1f");
        var second = baseKey("202122232425262728292a2b2c2d2e2f", "303132333435363738393a3b3c3d3e3f");

        assertDigest("/eq5rPNxA2K9JljNyaKej5x1f8+YEWA6jER80dkVEQg=", sha.encode(first, second));
        assertDigest("hOS9bKKvlkEv3GL+RNTpcJzcMZMwgaYkhqSKFUtYLVM=", sha.encode(second, first));
    }

}

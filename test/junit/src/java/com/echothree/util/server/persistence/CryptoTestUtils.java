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

/** Supplies explicitly wired utility instances for unit tests without a CDI container. */
@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies explicitly without a container.
public final class CryptoTestUtils {

    private CryptoTestUtils() {
    }

    public static Sha1Utils sha1Utils() {
        var sha = new Sha1Utils();
        sha.randomUtils = new RandomUtils();
        return sha;
    }

    public static Sha256Utils sha256Utils() {
        var sha = new Sha256Utils();
        sha.randomUtils = new RandomUtils();
        return sha;
    }

    public static EncryptionUtils encryptionUtils() {
        var encryption = new EncryptionUtils();
        encryption.randomUtils = new RandomUtils();
        encryption.sha1Utils = sha1Utils();
        encryption.sha256Utils = sha256Utils();
        return encryption;
    }

    public static HmacSha256Utils hmacSha256Utils(EncryptionUtils encryption) {
        var hmac = new HmacSha256Utils();
        hmac.encryptionUtils = encryption;
        return hmac;
    }

}

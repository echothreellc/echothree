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
import com.google.common.io.BaseEncoding;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;

public class HmacSha256Utils {

    private static final String HMAC_256_ALGORITHM = "HmacSHA256";
    private static final int HMAC_256_KEYSIZE = 256;

    private HmacSha256Utils() {
        super();
    }

    private static class HmacSha256UtilsHolder {
        static HmacSha256Utils instance = new HmacSha256Utils();
    }

    public static HmacSha256Utils getInstance() {
        return HmacSha256UtilsHolder.instance;
    }

    public String hash(BaseFactory<?, ?> baseFactory, final String value) {
        try {
            var secretKey = EncryptionUtils.getInstance().getEntityEncryptionKey(HMAC_256_ALGORITHM, HMAC_256_KEYSIZE, baseFactory,
                    EncryptionUtils.EntityEncryptionKeyTypes.HMAC_SHA256);
            var hmac = Mac.getInstance(HMAC_256_ALGORITHM);

            hmac.init(secretKey);

            return BaseEncoding.base64().encode(hmac.doFinal(value.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new PersistenceEncryptionException(e);
        }
    }

}

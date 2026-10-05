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
import com.google.common.io.BaseEncoding;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256Utils
        implements ShaUtils {
    
    private Sha256Utils() {
        super();
    }
    
    private static class Sha256UtilsHolder {
        static Sha256Utils instance = new Sha256Utils();
    }
    
    public static Sha256Utils getInstance() {
        return Sha256UtilsHolder.instance;
    }
    
    @Override
    public String generateSalt() {
        var salt = new byte[32];
        
        EncryptionUtils.getInstance().getRandom().nextBytes(salt);
        
        return BaseEncoding.base64().encode(salt);
    }
    
    @Override
    public String encode(String salt, String password) {
        byte[] input;
        
        try {
            var sha256Encoder = MessageDigest.getInstance("SHA-256");
            
            sha256Encoder.reset();
            sha256Encoder.update(BaseEncoding.base64().decode(salt));
            input = sha256Encoder.digest(password.getBytes(StandardCharsets.UTF_8));
            
            for(var i = 0; i < 1000; i++) {
                sha256Encoder.reset();
                input = sha256Encoder.digest(input);
            }
        } catch (NoSuchAlgorithmException nsae) {
            input = null;
        }
        
        return input == null ? null : BaseEncoding.base64().encode(input);
    }
    
    @Override
    public String encode(BaseKey baseKey1, BaseKey baseKey2) {
        byte[] input;
        
        try {
            var sha256Encoder = MessageDigest.getInstance("SHA-256");
            
            sha256Encoder.reset();
            sha256Encoder.update(baseKey1.getKey().getEncoded());
            sha256Encoder.update(baseKey1.getIv());
            sha256Encoder.update(baseKey2.getKey().getEncoded());
            sha256Encoder.update(baseKey2.getIv());
            input = sha256Encoder.digest();
        } catch (NoSuchAlgorithmException nsae) {
            input = null;
        }
        
        return input == null ? null : BaseEncoding.base64().encode(input);
    }
    
    @Override
    public String hash(byte[] bytes) {
        byte[] input;

        try {
            var sha256Encoder = MessageDigest.getInstance("SHA-256");
            
            sha256Encoder.reset();
            sha256Encoder.update(bytes);
            input = sha256Encoder.digest();
        } catch (NoSuchAlgorithmException nsae) {
            input = null;
        }
        
        return input == null ? null : BaseEncoding.base64().encode(input);
    }
    
}

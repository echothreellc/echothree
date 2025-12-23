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

public class Sha1Utils {
    
    private Sha1Utils() {
        super();
    }
    
    private static class Sha1UtilsHolder {
        static Sha1Utils instance = new Sha1Utils();
    }
    
    public static Sha1Utils getInstance() {
        return Sha1UtilsHolder.instance;
    }
    
    public String generateSalt() {
        var salt = new byte[8];
        
        EncryptionUtils.getInstance().getRandom().nextBytes(salt);
        
        return BaseEncoding.base64().encode(salt);
    }
    
    public String encode(String salt, String password) {
        byte[] input;
        
        try {
            var sha1Encoder = MessageDigest.getInstance("SHA-1");
            
            sha1Encoder.reset();
            sha1Encoder.update(BaseEncoding.base64().decode(salt));
            input = sha1Encoder.digest(password.getBytes(StandardCharsets.UTF_8));
            
            for(var i = 0; i < 1000; i++) {
                sha1Encoder.reset();
                input = sha1Encoder.digest(input);
            }
        } catch (NoSuchAlgorithmException nsae) {
            input = null;
        }
        
        return BaseEncoding.base64().encode(input);
    }
    
    public String encode(BaseKey baseKey1, BaseKey baseKey2) {
        byte[] input;
        
        try {
            var sha1Encoder = MessageDigest.getInstance("SHA-1");
            
            sha1Encoder.reset();
            sha1Encoder.update(baseKey1.getKey().getEncoded());
            sha1Encoder.update(baseKey1.getIv());
            sha1Encoder.update(baseKey2.getKey().getEncoded());
            sha1Encoder.update(baseKey2.getIv());
            input = sha1Encoder.digest();
        } catch (NoSuchAlgorithmException nsae) {
            input = null;
        }
        
        return BaseEncoding.base64().encode(input);
    }
    
    public String hash(String string) {
        return hash(string.getBytes(StandardCharsets.UTF_8));
    }
    
    public String hash(byte[] bytes) {
        byte[] input;

        try {
            var sha1Encoder = MessageDigest.getInstance("SHA-1");
            
            sha1Encoder.reset();
            sha1Encoder.update(bytes);
            input = sha1Encoder.digest();
        } catch (NoSuchAlgorithmException nsae) {
            input = null;
        }
        
        return BaseEncoding.base64().encode(input);
    }
    
}

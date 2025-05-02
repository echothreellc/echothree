// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.util.common.string;

import com.google.common.io.BaseEncoding;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    
    private MD5Utils() {
        super();
    }
    
    private static class MD5UtilsHolder {
        static MD5Utils instance = new MD5Utils();
    }
    
    public static MD5Utils getInstance() {
        return MD5UtilsHolder.instance;
    }
    
    public String encode(String string) {
        byte[] md5Hash = null;
        
        try {
            var md5Encoder = MessageDigest.getInstance("MD5");
            
            md5Encoder.update(string.getBytes(StandardCharsets.UTF_8));
            md5Hash = md5Encoder.digest();
        } catch (NoSuchAlgorithmException nsae) {
            // nothing
        }
        
        return BaseEncoding.base64().encode(md5Hash);
    }
    
}

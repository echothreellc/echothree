// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.util.server.string;

import com.echothree.util.server.persistence.EncryptionUtils;
import java.util.Random;

public class KeyUtils {
    
    private KeyUtils() {
        super();
    }
    
    private static class KeyUtilsHolder {
        static KeyUtils instance = new KeyUtils();
    }
    
    public static KeyUtils getInstance() {
        return KeyUtilsHolder.instance;
    }
    
    private static final String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_";
    private static final char []characterArray = characters.toCharArray();
    private static final int characterCount = characters.length();
    private static final int keyLength = 80;
    
    public String generateKey() {
        StringBuilder keyBuilder = new StringBuilder(keyLength);
        Random random = EncryptionUtils.getInstance().getRandom();
        
        for(int i = 0; i < keyLength; i++) {
            keyBuilder.append(characterArray[random.nextInt(characterCount)]);
        }
        
        return keyBuilder.toString();
    }
    
}

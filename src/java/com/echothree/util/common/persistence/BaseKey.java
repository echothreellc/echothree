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

package com.echothree.util.common.persistence;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import javax.crypto.SecretKey;

public class BaseKey
        implements Serializable {
    
    private SecretKey key;
    private byte[] iv;
    
    /** Creates a new instance of BaseKey */
    public BaseKey(SecretKey key, byte[] iv) {
        this.setKey(key);
        this.setIv(iv);
    }
    
    public SecretKey getKey() {
        return key;
    }
    
    public final void setKey(SecretKey key) {
        this.key = key;
    }
    
    public byte[] getIv() {
        return iv;
    }
    
    public final void setIv(byte[] iv) {
        this.iv = iv;
    }
    
    @Override
    public boolean equals(Object other) {
        if(this == other)
            return true;
        
        if(other instanceof BaseKey that) {
            var thatEncoded = that.getKey().getEncoded();
            var thatIv = that.getIv();

            var objectsEqual = Arrays.equals(key.getEncoded(), thatEncoded);
            if(objectsEqual) {
                objectsEqual = Arrays.equals(iv, thatIv);
            }
            
            return objectsEqual;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        var hash = 7;
        
        hash = 53 * hash + Objects.hashCode(this.key);
        hash = 53 * hash + Arrays.hashCode(this.iv);
        
        return hash;
    }
    
}

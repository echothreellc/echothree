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

package com.echothree.util.common.persistence;

import java.io.Serializable;
import javax.crypto.SecretKey;

public class BaseKeys
        implements Serializable {
    
    private BaseKey baseKey1;
    private BaseKey baseKey2;
    private BaseKey baseKey3;
    private String baseEncryptionKeyName;
    
    private void init(BaseKey baseKey1, BaseKey baseKey2, BaseKey baseKey3, String baseEncryptionKeyName) {
        this.setBaseKey1(baseKey1);
        this.setBaseKey2(baseKey2);
        this.setBaseKey3(baseKey3);
        this.setBaseEncryptionKeyName(baseEncryptionKeyName);
    }
    
    /** Creates a new instance of BaseKeys */
    public BaseKeys(BaseKey baseKey1, BaseKey baseKey2, BaseKey baseKey3, String baseEncryptionKeyName) {
        init(baseKey1, baseKey2, baseKey3, baseEncryptionKeyName);
    }
    
    /** Creates a new instance of BaseKeys */
    public BaseKeys(BaseKey baseKey1, BaseKey baseKey2, BaseKey baseKey3) {
        init(baseKey1, baseKey2, baseKey3, null);
    }
    
    /** Creates a new instance of BaseKeys */
    public BaseKeys(BaseKey baseKey1, BaseKey baseKey2) {
        init(baseKey1, baseKey2, null, null);
    }
    
    public int getBaseKeyCount() {
        return (baseKey1 == null ? 0 : 1) + (baseKey2 == null ? 0 : 1) + (baseKey3 == null ? 0 : 1);
    }
    
    public BaseKey getBaseKey1() {
        return baseKey1;
    }
    
    public void setBaseKey1(BaseKey baseKey1) {
        this.baseKey1 = baseKey1;
    }
    
    public BaseKey getBaseKey2() {
        return baseKey2;
    }
    
    public void setBaseKey2(BaseKey baseKey2) {
        this.baseKey2 = baseKey2;
    }
    
    public BaseKey getBaseKey3() {
        return baseKey3;
    }
    
    public void setBaseKey3(BaseKey baseKey3) {
        this.baseKey3 = baseKey3;
    }
    
    public String getBaseEncryptionKeyName() {
        return baseEncryptionKeyName;
    }
    
    public void setBaseEncryptionKeyName(String baseEncryptionKeyName) {
        this.baseEncryptionKeyName = baseEncryptionKeyName;
    }
    
    public SecretKey getKey1() {
        return baseKey1 == null? null: baseKey1.getKey();
    }
    
    public byte[] getIv1() {
        return baseKey1.getIv();
    }
    
    public SecretKey getKey2() {
        return baseKey2 == null? null: baseKey2.getKey();
    }
    
    public byte[] getIv2() {
        return baseKey2.getIv();
    }
    
    public SecretKey getKey3() {
        return baseKey3 == null? null: baseKey3.getKey();
    }
    
    public byte[] getIv3() {
        return baseKey3.getIv();
    }
    
}

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

package com.echothree.util.common.form;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TransferOptions
        implements Serializable {
    
    private Map<Class, Set<String>> classesAndOptions = new HashMap<>();

    public TransferOptions addClassAndProperty(Class clazz, String property) {
        var options = classesAndOptions.get(clazz);
        
        if(options == null) {
            options = new HashSet<>();
            
            classesAndOptions.put(clazz, options);
        }
        
        if(property != null) {
            options.add(property);
        }

        return this;
    }
    
    public Set<String> getOptions(Class clazz) {
        return classesAndOptions.get(clazz);
    }
    
}

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

package com.echothree.util.common.command;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ProxyInvocationHandler
        implements Serializable, InvocationHandler {
    
    private Map<String, Object> map;
    
    /** Creates a new instance of ProxyInvocationHandler */
    public ProxyInvocationHandler() {
        map = new HashMap<>();
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        var name = method.getName();
        var length = name.length();
        
        if(name.startsWith("get")) {
            if(length == 3) {
                if(args == null) {
                    return Collections.unmodifiableMap(map);
                } else {
                    return map.get(args[0]);
                }
            } else {
                return map.get(name.substring(3));
            }
        } else if(name.startsWith("is")) {
            if(length == 2) {
                return map.get(args[0]);
            } else {
                return map.get(name.substring(2));
            }
        } else if(name.startsWith("set")) {
            if(length == 3) {
                var arg0 = args[0];

                if(arg0 instanceof String) {
                    return map.put((String)args[0], args[1]);
                } else if(arg0 instanceof Map) {
                    map.putAll((Map<String, Object>)arg0);
                }
            } else {
                return map.put(name.substring(3), args[0]);
            }
        } else if(name.equals("reset")) {
            map.clear();
        } else if(name.equals("toString")) {
            return "{ map = " + map + " }";
        } else if(name.equals("hashCode")) {
            return map.hashCode();
        } else if(name.equals("equals")) {
            return map.equals(args[0]);
        }
        
        return null;
    }
    
}

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

package com.echothree.util.common.transfer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapWrapper<V>
        implements BaseWrapper<V> {
    
    private Map<String, V> map;
    
    /** Creates a new instance of MapWrapper */
    public MapWrapper() {
        this.map = new LinkedHashMap<>();
    }
    
    /** Creates a new instance of MapWrapper */
    public MapWrapper(int initialCapacity) {
        this.map = new LinkedHashMap<>(initialCapacity);
    }
    
    /** Creates a new instance of MapWrapper */
    public MapWrapper(Map<String, V> map) {
        this.map = map;
    }
    
    public void put(String key, V value) {
        map.put(key, value);
    }
    
    public Map<String, V> getMap() {
        return map;
    }
    
    @Override
    public List<V> getList() {
        return new ArrayList<>(getCollection());
    }

    @Override
    public Collection<V> getCollection() {
        return map.values();
    }
    
    @Override
    public int getSize() {
        return map.size();
    }
    
}

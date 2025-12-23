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

package com.echothree.util.server.transfer;

import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.transfer.MapWrapper;
import java.util.Map;

public class MapWrapperBuilder
        extends BaseWrapperBuilder {
    
    private MapWrapperBuilder() {
        super();
    }

    private static class MapWrapperBuilderHolder {
        static MapWrapperBuilder instance = new MapWrapperBuilder();
    }

    public static MapWrapperBuilder getInstance() {
        return MapWrapperBuilderHolder.instance;
    }
    
    public <V> MapWrapper<V> filter(TransferProperties transferProperties, Map<String, V> map) {
        filterCollection(transferProperties, map.values());
        
        return new MapWrapper<>(map) ;
    }
    
}

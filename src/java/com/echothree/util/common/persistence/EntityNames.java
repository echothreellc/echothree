// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.util.common.transfer.MapWrapper;
import java.io.Serializable;

public class EntityNames
        implements Serializable {
    
    private String target;
    private MapWrapper<String> names;
    
    /** Creates a new instance of EntityNames */
    public EntityNames(String target, MapWrapper<String> names) {
        this.target = target;
        this.names = names;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public MapWrapper<String> getNames() {
        return names;
    }

    public void setNames(MapWrapper<String> names) {
        this.names = names;
    }
    
}

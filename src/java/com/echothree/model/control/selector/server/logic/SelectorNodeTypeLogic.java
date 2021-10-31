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

package com.echothree.model.control.selector.server.logic;

import com.echothree.model.control.core.server.logic.*;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.selector.common.exception.UnknownSelectorNodeTypeNameException;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.selector.server.entity.SelectorNodeType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;

public class SelectorNodeTypeLogic
        extends BaseLogic {
    
    private SelectorNodeTypeLogic() {
        super();
    }
    
    private static class SelectorNodeLogicHolder {
        static SelectorNodeTypeLogic instance = new SelectorNodeTypeLogic();
    }
    
    public static SelectorNodeTypeLogic getInstance() {
        return SelectorNodeLogicHolder.instance;
    }
    
    public SelectorNodeType getSelectorNodeTypeByName(final ExecutionErrorAccumulator eea, final String selectorNodeTypeName) {
        var selectorControl = Session.getModelController(SelectorControl.class);
        SelectorNodeType selectorNodeType = selectorControl.getSelectorNodeTypeByName(selectorNodeTypeName);

        if(selectorNodeType == null) {
            handleExecutionError(UnknownSelectorNodeTypeNameException.class, eea, ExecutionErrors.UnknownSelectorNodeTypeName.name(), selectorNodeTypeName);
        }

        return selectorNodeType;
    }

    public SelectorNodeType getSelectorNodeTypeByUlid(final ExecutionErrorAccumulator eea, final String ulid, final EntityPermission entityPermission) {
        SelectorNodeType selectorNodeType = null;
        
        var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, (String)null, null, null, ulid,
                ComponentVendors.ECHOTHREE.name(), EntityTypes.SelectorNodeType.name());

        if(eea == null || !eea.hasExecutionErrors()) {
            var selectorControl = Session.getModelController(SelectorControl.class);
            
            selectorNodeType = selectorControl.getSelectorNodeTypeByEntityInstance(entityInstance, entityPermission);
        }

        return selectorNodeType;
    }
    
    public SelectorNodeType getSelectorNodeTypeByUlid(final ExecutionErrorAccumulator eea, final String ulid) {
        return getSelectorNodeTypeByUlid(eea, ulid, EntityPermission.READ_ONLY);
    }
    
    public SelectorNodeType getSelectorNodeTypeByUlidForUpdate(final ExecutionErrorAccumulator eea, final String ulid) {
        return getSelectorNodeTypeByUlid(eea, ulid, EntityPermission.READ_WRITE);
    }
    
}

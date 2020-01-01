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

package com.echothree.model.control.core.server.logic;

import com.echothree.model.control.core.common.exception.UnknownEntityTypeNameException;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class EntityTypeLogic
        extends BaseLogic {

    private EntityTypeLogic() {
        super();
    }

    private static class EntityTypeLogicHolder {
        static EntityTypeLogic instance = new EntityTypeLogic();
    }

    public static EntityTypeLogic getInstance() {
        return EntityTypeLogicHolder.instance;
    }
    
    public EntityType getEntityTypeByName(final ExecutionErrorAccumulator eea, final ComponentVendor componentVendor, final String entityTypeName) {
        var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        EntityType entityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);

        if(entityType == null) {
            handleExecutionError(UnknownEntityTypeNameException.class, eea, ExecutionErrors.UnknownEntityTypeName.name(),
                    componentVendor.getLastDetail().getComponentVendorName(), entityTypeName);
        }

        return entityType;
    }

    public EntityType getEntityTypeByName(final ExecutionErrorAccumulator eea, final String componentVendorName, final String entityTypeName) {
        ComponentVendor componentVendor = ComponentVendorLogic.getInstance().getComponentVendorByName(eea, componentVendorName);
        EntityType entityType = null;
        
        if(!hasExecutionErrors(eea)) {
            entityType = getEntityTypeByName(eea, componentVendor, entityTypeName);
        }
        
        return entityType;
    }
    
}

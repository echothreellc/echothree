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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.common.transfer.EntityAttributeGroupTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.EntityAttributeGroup;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.MapWrapper;
import com.echothree.util.server.persistence.Session;

public class EntityAttributeGroupTransferCache
        extends BaseCoreTransferCache<EntityAttributeGroup, EntityAttributeGroupTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);

    boolean includeEntityAttributes;
    
    /** Creates a new instance of EntityAttributeGroupTransferCache */
    public EntityAttributeGroupTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        var options = session.getOptions();
        if(options != null) {
            includeEntityAttributes = options.contains(CoreOptions.EntityAttributeGroupIncludeEntityAttributes);
        }

        setIncludeEntityInstance(true);
    }
    
    public EntityAttributeGroupTransfer getEntityAttributeGroupTransfer(EntityAttributeGroup entityAttributeGroup, EntityInstance entityInstance) {
        var entityAttributeGroupTransfer = get(entityAttributeGroup);
        
        if(entityAttributeGroupTransfer == null) {
            var entityAttributeGroupDetail = entityAttributeGroup.getLastDetail();
            var entityAttributeGroupName = entityAttributeGroupDetail.getEntityAttributeGroupName();
            var isDefault = entityAttributeGroupDetail.getIsDefault();
            var sortOrder = entityAttributeGroupDetail.getSortOrder();
            var description = coreControl.getBestEntityAttributeGroupDescription(entityAttributeGroup, getLanguage(userVisit));
            
            entityAttributeGroupTransfer = new EntityAttributeGroupTransfer(entityAttributeGroupName, isDefault, sortOrder, description);
            if(entityInstance == null) {
                put(userVisit, entityAttributeGroup, entityAttributeGroupTransfer);
            } else {
                setupEntityInstance(userVisit, entityAttributeGroup, null, entityAttributeGroupTransfer);
            }
            
            if(includeEntityAttributes) {
                if(entityInstance != null) {
                    var entityAttributeTransfers = coreControl.getEntityAttributeTransfersByEntityAttributeGroupAndEntityType(userVisit,
                            entityAttributeGroup, entityInstance.getEntityType(), entityInstance);
                    var mapWrapper = new MapWrapper<EntityAttributeTransfer>(entityAttributeTransfers.size());

                    entityAttributeTransfers.forEach((entityAttributeTransfer) -> {
                        mapWrapper.put(entityAttributeTransfer.getEntityAttributeName(), entityAttributeTransfer);
                    });

                    entityAttributeGroupTransfer.setEntityAttributes(mapWrapper);
                } else {
                    getLog().error("entityInstance is null");
                }
            }
        }
        
        return entityAttributeGroupTransfer;
    }
    
}

// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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
import com.echothree.model.control.core.common.CoreProperties;
import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.server.persistence.EntityDescriptionUtils;
import com.echothree.util.server.persistence.EntityNamesUtils;
import com.echothree.util.server.persistence.Session;

public class EntityInstanceTransferCache
        extends BaseCoreTransferCache<EntityInstance, EntityInstanceTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);

    boolean includeEntityAppearance;
    boolean includeNames;
    boolean includeKeyIfAvailable;
    boolean includeGuidIfAvailable;
    boolean includeUlidIfAvailable;
    
    TransferProperties transferProperties;
    boolean filterEntityType;
    boolean filterEntityUniqueId;
    boolean filterEntityRef;
    boolean filterEntityTime;
    boolean filterDescription;
    
    /** Creates a new instance of EntityInstanceTransferCache */
    public EntityInstanceTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        var options = session.getOptions();
        if(options != null) {
            includeEntityAppearance = options.contains(CoreOptions.EntityInstanceIncludeEntityAppearance);
            includeNames = options.contains(CoreOptions.EntityInstanceIncludeNames);
            includeKeyIfAvailable = options.contains(CoreOptions.EntityInstanceIncludeKeyIfAvailable);
            includeGuidIfAvailable = options.contains(CoreOptions.EntityInstanceIncludeGuidIfAvailable);
            includeUlidIfAvailable = options.contains(CoreOptions.EntityInstanceIncludeUlidIfAvailable);
        }
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(EntityInstanceTransfer.class);
            
            if(properties != null) {
                filterEntityType = !properties.contains(CoreProperties.ENTITY_TYPE);
                filterEntityUniqueId = !properties.contains(CoreProperties.ENTITY_UNIQUE_ID);
                filterEntityRef = !properties.contains(CoreProperties.ENTITY_REF);
                filterEntityTime = !properties.contains(CoreProperties.ENTITY_TIME);
                filterDescription = !properties.contains(CoreProperties.DESCRIPTION);
            }
        }
    }

    public EntityInstanceTransfer getEntityInstanceTransfer(EntityInstance entityInstance, boolean includeEntityAppearance, boolean includeNames,
            boolean includeKey, boolean includeGuid, boolean includeUlid) {
        EntityInstanceTransfer entityInstanceTransfer = get(entityInstance);
        
        if(entityInstanceTransfer == null) {
            var entityTypeTransfer = filterEntityType ? null : coreControl.getEntityTypeTransfer(userVisit, entityInstance.getEntityType());
            var entityUniqueId = filterEntityUniqueId ? null : entityInstance.getEntityUniqueId();
            String key = null;
            String guid = null;
            String ulid = null;
            var componentVendorTransfer = entityTypeTransfer == null ? null : entityTypeTransfer.getComponentVendor();
            var componentVendorName = componentVendorTransfer == null ? null : componentVendorTransfer.getComponentVendorName();
            var entityTypeName = entityTypeTransfer == null ? null : entityTypeTransfer.getEntityTypeName();
            var entityRef = filterEntityRef || componentVendorName == null || entityTypeName == null || entityUniqueId == null ? null : new StringBuilder(componentVendorName).append('.').append(entityTypeName).append('.').append(entityUniqueId).toString();
            var entityTime = filterEntityTime ? null : coreControl.getEntityTime(entityInstance);
            var entityTimeTransfer = entityTime == null ? null : coreControl.getEntityTimeTransfer(userVisit, entityTime);
            String description = null;
            
            if(includeKey || includeKeyIfAvailable) {
                key = entityInstance.getKey();
                
                if(includeKey && key == null) {
                    entityInstance = coreControl.ensureKeyForEntityInstance(entityInstance, false);
                    key = entityInstance.getKey();
                }
            }
            
            if(includeGuid || includeGuidIfAvailable) {
                guid = entityInstance.getGuid();
                
                if(includeGuid && guid == null) {
                    entityInstance = coreControl.ensureGuidForEntityInstance(entityInstance, false);
                    guid = entityInstance.getGuid();
                }
            }
            
            if(includeUlid || includeUlidIfAvailable) {
                ulid = entityInstance.getUlid();
                
                if(includeUlid && ulid == null) {
                    entityInstance = coreControl.ensureUlidForEntityInstance(entityInstance, false);
                    ulid = entityInstance.getUlid();
                }
            }
            
            if(!filterDescription) {
                description = EntityDescriptionUtils.getInstance().getDescription(userVisit, entityInstance);
            }
            
            entityInstanceTransfer = new EntityInstanceTransfer(entityTypeTransfer, entityUniqueId, key, guid, ulid, entityRef,
                    entityTimeTransfer, description);
            put(entityInstance, entityInstanceTransfer);
            
            if(includeEntityAppearance || this.includeEntityAppearance) {
                var entityAppearance = coreControl.getEntityAppearance(entityInstance);
                
                entityInstanceTransfer.setEntityAppearance(entityAppearance == null ? null : coreControl.getEntityAppearanceTransfer(userVisit, entityAppearance));
            }
            
            if(includeNames || this.includeNames) {
                var entityNamesMapping = EntityNamesUtils.getInstance().getEntityNames(entityInstance);
                
                entityInstanceTransfer.setEntityNames(entityNamesMapping == null ? null : entityNamesMapping.getEntityNames());
            }
        }
        
        return entityInstanceTransfer;
    }

}

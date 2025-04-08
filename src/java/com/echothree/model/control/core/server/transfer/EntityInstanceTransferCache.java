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
import com.echothree.model.control.core.common.CoreProperties;
import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.server.persistence.EntityDescriptionUtils;
import com.echothree.util.server.persistence.EntityNamesUtils;
import com.echothree.util.server.persistence.Session;

public class EntityInstanceTransferCache
        extends BaseCoreTransferCache<EntityInstance, EntityInstanceTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);
    EntityTypeControl entityTypeControl = Session.getModelController(EntityTypeControl.class);

    boolean includeEntityAppearance;
    boolean includeEntityVisit;
    boolean includeNames;
    boolean includeUuidIfAvailable;

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
            includeEntityVisit = options.contains(CoreOptions.EntityInstanceIncludeEntityVisit);
            includeNames = options.contains(CoreOptions.EntityInstanceIncludeNames);
            includeUuidIfAvailable = options.contains(CoreOptions.EntityInstanceIncludeUuidIfAvailable);
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

    public EntityInstanceTransfer getEntityInstanceTransfer(EntityInstance entityInstance, boolean includeEntityAppearance,
            boolean includeEntityVisit, boolean includeNames, boolean includeUuid) {
        var entityInstanceTransfer = get(entityInstance);
        
        if(entityInstanceTransfer == null) {
            var entityTypeTransfer = filterEntityType ? null : entityTypeControl.getEntityTypeTransfer(userVisit, entityInstance.getEntityType());
            var entityUniqueId = filterEntityUniqueId ? null : entityInstance.getEntityUniqueId();
            String uuid = null;
            var componentVendorTransfer = entityTypeTransfer == null ? null : entityTypeTransfer.getComponentVendor();
            var componentVendorName = componentVendorTransfer == null ? null : componentVendorTransfer.getComponentVendorName();
            var entityTypeName = entityTypeTransfer == null ? null : entityTypeTransfer.getEntityTypeName();
            var entityRef = filterEntityRef || componentVendorName == null || entityTypeName == null || entityUniqueId == null ? null : componentVendorName + '.' + entityTypeName + '.' + entityUniqueId;
            var entityTime = filterEntityTime ? null : coreControl.getEntityTime(entityInstance);
            var entityTimeTransfer = entityTime == null ? null : coreControl.getEntityTimeTransfer(userVisit, entityTime);
            String description = null;
            
            if(includeUuid || includeUuidIfAvailable) {
                uuid = entityInstance.getUuid();
                
                if(includeUuid && uuid == null) {
                    entityInstance = coreControl.ensureUuidForEntityInstance(entityInstance, false);
                    uuid = entityInstance.getUuid();
                }
            }
            
            if(!filterDescription) {
                description = EntityDescriptionUtils.getInstance().getDescription(userVisit, entityInstance);
            }
            
            entityInstanceTransfer = new EntityInstanceTransfer(entityTypeTransfer, entityUniqueId, uuid, entityRef,
                    entityTimeTransfer, description);
            put(entityInstance, entityInstanceTransfer);

            if(includeEntityAppearance || this.includeEntityAppearance) {
                var entityAppearance = coreControl.getEntityAppearance(entityInstance);

                entityInstanceTransfer.setEntityAppearance(entityAppearance == null ? null : coreControl.getEntityAppearanceTransfer(userVisit, entityAppearance));
            }

            if(includeEntityVisit || this.includeEntityVisit) {
                // visitingParty could be null
                var visitingParty = getUserControl().getPartyFromUserVisit(userVisit);
                var visitingEntityInstance = visitingParty == null ? null : coreControl.getEntityInstanceByBasePK(visitingParty.getPrimaryKey());

                // visitingEntityInstance = the entityInstance parameter
                // entityInstance = the visitedEntityInstance parameter
                var entityVisit = visitingEntityInstance == null ? null : coreControl.getEntityVisit(visitingEntityInstance, entityInstance);

                entityInstanceTransfer.setEntityVisit(entityVisit == null ? null : coreControl.getEntityVisitTransfer(userVisit, entityVisit));
            }

            if(includeNames || this.includeNames) {
                var entityNamesMapping = EntityNamesUtils.getInstance().getEntityNames(entityInstance);
                
                entityInstanceTransfer.setEntityNames(entityNamesMapping == null ? null : entityNamesMapping.getEntityNames());
            }
        }
        
        return entityInstanceTransfer;
    }

}

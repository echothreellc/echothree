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
import com.echothree.model.control.core.common.transfer.EntityAliasTypeTransfer;
import com.echothree.model.control.core.server.control.EntityAliasControl;
import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.data.core.server.entity.EntityAliasType;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.server.persistence.Session;

public class EntityAliasTypeTransferCache
        extends BaseCoreTransferCache<EntityAliasType, EntityAliasTypeTransfer> {

    EntityAliasControl entityAliasControl = Session.getModelController(EntityAliasControl.class);
    EntityTypeControl entityTypeControl = Session.getModelController(EntityTypeControl.class);

    boolean includeAlias;

    TransferProperties transferProperties;
    boolean filterEntityType;
    boolean filterEntityAliasTypeName;
    boolean filterValidationPattern;
    boolean filterIsDefault;
    boolean filterSortOrder;
    boolean filterDescription;
    boolean filterEntityInstance;

    /** Creates a new instance of EntityAliasTypeTransferCache */
    public EntityAliasTypeTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        var options = session.getOptions();
        if(options != null) {
            includeAlias = options.contains(CoreOptions.EntityAliasTypeIncludeAlias);
        }
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(EntityAliasTypeTransfer.class);
            
            if(properties != null) {
                filterEntityType = !properties.contains(CoreProperties.ENTITY_TYPE);
                filterEntityAliasTypeName = !properties.contains(CoreProperties.ENTITY_ATTRIBUTE_NAME);
                filterValidationPattern = !properties.contains(CoreProperties.VALIDATION_PATTERN);
                filterIsDefault = !properties.contains(CoreProperties.IS_DEFAULT);
                filterSortOrder = !properties.contains(CoreProperties.SORT_ORDER);
                filterDescription = !properties.contains(CoreProperties.DESCRIPTION);
                filterEntityInstance = !properties.contains(CoreProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }
    
    public EntityAliasTypeTransfer getEntityAliasTypeTransfer(EntityAliasType entityAliasType, EntityInstance entityInstance) {
        var entityAliasTypeTransfer = get(entityAliasType);
        
        if(entityAliasTypeTransfer == null) {
            var entityAliasTypeDetail = entityAliasType.getLastDetail();
            var entityTypeTransfer = filterEntityType ? null : entityTypeControl.getEntityTypeTransfer(userVisit, entityAliasTypeDetail.getEntityType());
            var entityAliasTypeName = filterEntityAliasTypeName ? null : entityAliasTypeDetail.getEntityAliasTypeName();
            var validationPattern = filterValidationPattern ? null : entityAliasTypeDetail.getValidationPattern();
            var isDefault = filterIsDefault ? null : entityAliasTypeDetail.getIsDefault();
            var sortOrder = filterSortOrder ? null : entityAliasTypeDetail.getSortOrder();
            var description = filterDescription ? null : entityAliasControl.getBestEntityAliasTypeDescription(entityAliasType, getLanguage());
            
            entityAliasTypeTransfer = new EntityAliasTypeTransfer(entityTypeTransfer, entityAliasTypeName,
                    validationPattern, isDefault, sortOrder, description);

            if(entityInstance == null) {
                put(entityAliasType, entityAliasTypeTransfer);
            } else {
                setupEntityInstance(entityAliasType, null, entityAliasTypeTransfer);
            }
        }
        return entityAliasTypeTransfer;
    }
    
}

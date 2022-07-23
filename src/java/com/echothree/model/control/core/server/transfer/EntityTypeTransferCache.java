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
import com.echothree.model.control.core.common.transfer.EntityTypeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.EntityTypeDetail;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.UnitOfMeasureUtils;
import java.util.Set;

public class EntityTypeTransferCache
        extends BaseCoreTransferCache<EntityType, EntityTypeTransfer> {
    
    IndexControl indexControl;
    UomControl uomControl = Session.getModelController(UomControl.class);
    UnitOfMeasureKind timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
    UnitOfMeasureUtils unitOfMeasureUtils = UnitOfMeasureUtils.getInstance();

    TransferProperties transferProperties;
    boolean filterComponentVendor;
    boolean filterEntityTypeName;
    boolean filterKeepAllHistory;
    boolean filterUnformattedLockTimeout;
    boolean filterLockTimeout;
    boolean filterSortOrder;
    boolean filterDescription;
    boolean filterEntityInstance;

    boolean includeIndexTypesCount;
    boolean includeIndexTypes;
    
    /** Creates a new instance of EntityTypeTransferCache */
    public EntityTypeTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(EntityTypeTransfer.class);
            
            if(properties != null) {
                filterComponentVendor = !properties.contains(CoreProperties.COMPONENT_VENDOR);
                filterEntityTypeName = !properties.contains(CoreProperties.ENTITY_TYPE_NAME);
                filterKeepAllHistory = !properties.contains(CoreProperties.KEEP_ALL_HISTORY);
                filterUnformattedLockTimeout = !properties.contains(CoreProperties.UNFORMATTED_LOCK_TIMEOUT);
                filterLockTimeout = !properties.contains(CoreProperties.LOCK_TIMEOUT);
                filterSortOrder = !properties.contains(CoreProperties.SORT_ORDER);
                filterDescription = !properties.contains(CoreProperties.DESCRIPTION);
                filterEntityInstance = !properties.contains(CoreProperties.ENTITY_INSTANCE);
            }
        }

        var options = session.getOptions();
        if(options != null) {
            includeIndexTypesCount = options.contains(CoreOptions.EntityTypeIncludeIndexTypesCount);
            includeIndexTypes = options.contains(CoreOptions.EntityTypeIncludeIndexTypes);
            
            if(includeIndexTypesCount || includeIndexTypes) {
                indexControl = Session.getModelController(IndexControl.class);
            }
        }
    }
    
    public EntityTypeTransfer getEntityTypeTransfer(EntityType entityType) {
        EntityTypeTransfer entityTypeTransfer = get(entityType);
        
        if(entityTypeTransfer == null) {
            EntityTypeDetail entityTypeDetail = entityType.getLastDetail();
            Long unformattedLockTimeout = entityTypeDetail.getLockTimeout();
            
            entityTypeTransfer = new EntityTypeTransfer();
            put(entityType, entityTypeTransfer);
            
            if(!filterComponentVendor) {
                entityTypeTransfer.setComponentVendor(coreControl.getComponentVendorTransfer(userVisit, entityTypeDetail.getComponentVendor()));
            }
            
            if(!filterEntityTypeName) {
                entityTypeTransfer.setEntityTypeName(entityTypeDetail.getEntityTypeName());
            }
            
            if(!filterKeepAllHistory) {
                entityTypeTransfer.setKeepAllHistory(entityTypeDetail.getKeepAllHistory());
            }
            
            if(!filterUnformattedLockTimeout) {
                entityTypeTransfer.setUnformattedLockTimeout(unformattedLockTimeout);
            }
            
            if(!filterLockTimeout) {
                entityTypeTransfer.setLockTimeout(unitOfMeasureUtils.formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedLockTimeout));
            }
            
            if(!filterSortOrder) {
                entityTypeTransfer.setSortOrder(entityTypeDetail.getSortOrder());
            }
            
            if(!filterDescription) {
                entityTypeTransfer.setEntityInstance(coreControl.getEntityInstanceTransfer(userVisit, entityType, false, false, false, false, false));
            }
            
            if(!filterEntityInstance) {
                entityTypeTransfer.setDescription(coreControl.getBestEntityTypeDescription(entityType, getLanguage()));
            }
            
            setupEntityInstance(entityType, null, entityTypeTransfer);
            
            if(includeIndexTypesCount) {
                entityTypeTransfer.setIndexTypesCount(indexControl.countIndexTypesByEntityType(entityType));
            }
            
            if(includeIndexTypes) {
                entityTypeTransfer.setIndexTypes(new ListWrapper<>(indexControl.getIndexTypeTransfersByEntityType(userVisit, entityType)));
            }
        }
        
        return entityTypeTransfer;
    }
    
}

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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.CoreProperties;
import com.echothree.model.control.core.common.transfer.EntityAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityLongRangeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityLongRange;
import com.echothree.model.data.core.server.entity.EntityLongRangeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import java.util.Set;

public class EntityLongRangeTransferCache
        extends BaseCoreTransferCache<EntityLongRange, EntityLongRangeTransfer> {
    
    TransferProperties transferProperties;
    boolean filterEntityAttribute;
    boolean filterEntityLongRangeName;
    boolean filterMinimumLongValue;
    boolean filterMaximumLongValue;
    boolean filterIsDefault;
    boolean filterSortOrder;
    boolean filterDescription;
    boolean filterEntityInstance;

    /** Creates a new instance of EntityLongRangeTransferCache */
    public EntityLongRangeTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            Set<String> properties = transferProperties.getProperties(EntityLongRangeTransfer.class);
            
            if(properties != null) {
                filterEntityAttribute = !properties.contains(CoreProperties.ENTITY_ATTRIBUTE);
                filterEntityLongRangeName = !properties.contains(CoreProperties.ENTITY_LONG_RANGE_NAME);
                filterMinimumLongValue = !properties.contains(CoreProperties.MINIMUM_LONG_VALUE);
                filterMaximumLongValue = !properties.contains(CoreProperties.MAXIMUM_LONG_VALUE);
                filterIsDefault = !properties.contains(CoreProperties.IS_DEFAULT);
                filterSortOrder = !properties.contains(CoreProperties.SORT_ORDER);
                filterDescription = !properties.contains(CoreProperties.DESCRIPTION);
                filterEntityInstance = !properties.contains(CoreProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }
    
    public EntityLongRangeTransfer getEntityLongRangeTransfer(EntityLongRange entityLongRange, EntityInstance entityInstance) {
        EntityLongRangeTransfer entityLongRangeTransfer = get(entityLongRange);
        
        if(entityLongRangeTransfer == null) {
            EntityLongRangeDetail entityLongRangeDetail = entityLongRange.getLastDetail();
            EntityAttributeTransfer entityAttributeTransfer = filterEntityAttribute ? null : entityInstance == null ? coreControl.getEntityAttributeTransfer(userVisit, entityLongRangeDetail.getEntityAttribute(), entityInstance) : null;
            String entityLongRangeName = filterEntityLongRangeName ? null : entityLongRangeDetail.getEntityLongRangeName();
            Long minimumLongValue = filterMinimumLongValue ? null : entityLongRangeDetail.getMinimumLongValue();
            Long maximumLongValue = filterMaximumLongValue ? null : entityLongRangeDetail.getMaximumLongValue();
            Boolean isDefault = filterIsDefault ? null : entityLongRangeDetail.getIsDefault();
            Integer sortOrder = filterSortOrder ? null : entityLongRangeDetail.getSortOrder();
            String description = coreControl.getBestEntityLongRangeDescription(entityLongRange, getLanguage());
            
            entityLongRangeTransfer = new EntityLongRangeTransfer(entityAttributeTransfer, entityLongRangeName, minimumLongValue, maximumLongValue, isDefault,
                    sortOrder, description);
            put(entityLongRange, entityLongRangeTransfer);
        }
        return entityLongRangeTransfer;
    }
    
}

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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.CoreProperties;
import com.echothree.model.control.core.common.transfer.EntityLongRangeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityLongRange;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EntityLongRangeTransferCache
        extends BaseCoreTransferCache<EntityLongRange, EntityLongRangeTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);

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
    protected EntityLongRangeTransferCache() {
        super();
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(EntityLongRangeTransfer.class);
            
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
    
    public EntityLongRangeTransfer getEntityLongRangeTransfer(final UserVisit userVisit, final EntityLongRange entityLongRange, final EntityInstance entityInstance) {
        var entityLongRangeTransfer = get(entityLongRange);
        
        if(entityLongRangeTransfer == null) {
            var entityLongRangeDetail = entityLongRange.getLastDetail();
            var entityAttributeTransfer = filterEntityAttribute ? null : entityInstance == null ? coreControl.getEntityAttributeTransfer(userVisit, entityLongRangeDetail.getEntityAttribute(), entityInstance) : null;
            var entityLongRangeName = filterEntityLongRangeName ? null : entityLongRangeDetail.getEntityLongRangeName();
            var minimumLongValue = filterMinimumLongValue ? null : entityLongRangeDetail.getMinimumLongValue();
            var maximumLongValue = filterMaximumLongValue ? null : entityLongRangeDetail.getMaximumLongValue();
            var isDefault = filterIsDefault ? null : entityLongRangeDetail.getIsDefault();
            var sortOrder = filterSortOrder ? null : entityLongRangeDetail.getSortOrder();
            var description = coreControl.getBestEntityLongRangeDescription(entityLongRange, getLanguage(userVisit));
            
            entityLongRangeTransfer = new EntityLongRangeTransfer(entityAttributeTransfer, entityLongRangeName, minimumLongValue, maximumLongValue, isDefault,
                    sortOrder, description);
            put(userVisit, entityLongRange, entityLongRangeTransfer);
        }
        return entityLongRangeTransfer;
    }
    
}

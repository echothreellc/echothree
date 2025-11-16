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

import com.echothree.model.control.core.common.CoreProperties;
import com.echothree.model.control.core.common.transfer.EntityIntegerRangeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityIntegerRange;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EntityIntegerRangeTransferCache
        extends BaseCoreTransferCache<EntityIntegerRange, EntityIntegerRangeTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);

    TransferProperties transferProperties;
    boolean filterEntityAttribute;
    boolean filterEntityIntegerRangeName;
    boolean filterMinimumIntegerValue;
    boolean filterMaximumIntegerValue;
    boolean filterIsDefault;
    boolean filterSortOrder;
    boolean filterDescription;
    boolean filterEntityInstance;

    /** Creates a new instance of EntityIntegerRangeTransferCache */
    protected EntityIntegerRangeTransferCache() {
        super();
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(EntityIntegerRangeTransfer.class);
            
            if(properties != null) {
                filterEntityAttribute = !properties.contains(CoreProperties.ENTITY_ATTRIBUTE);
                filterEntityIntegerRangeName = !properties.contains(CoreProperties.ENTITY_INTEGER_RANGE_NAME);
                filterMinimumIntegerValue = !properties.contains(CoreProperties.MINIMUM_INTEGER_VALUE);
                filterMaximumIntegerValue = !properties.contains(CoreProperties.MAXIMUM_INTEGER_VALUE);
                filterIsDefault = !properties.contains(CoreProperties.IS_DEFAULT);
                filterSortOrder = !properties.contains(CoreProperties.SORT_ORDER);
                filterDescription = !properties.contains(CoreProperties.DESCRIPTION);
                filterEntityInstance = !properties.contains(CoreProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }
    
    public EntityIntegerRangeTransfer getEntityIntegerRangeTransfer(final UserVisit userVisit, final EntityIntegerRange entityIntegerRange, final EntityInstance entityInstance) {
        var entityIntegerRangeTransfer = get(entityIntegerRange);
        
        if(entityIntegerRangeTransfer == null) {
            var entityIntegerRangeDetail = entityIntegerRange.getLastDetail();
            var entityAttributeTransfer = filterEntityAttribute ? null : entityInstance == null ? coreControl.getEntityAttributeTransfer(userVisit, entityIntegerRangeDetail.getEntityAttribute(), entityInstance) : null;
            var entityIntegerRangeName = filterEntityIntegerRangeName ? null : entityIntegerRangeDetail.getEntityIntegerRangeName();
            var minimumIntegerValue = filterMinimumIntegerValue ? null : entityIntegerRangeDetail.getMinimumIntegerValue();
            var maximumIntegerValue = filterMaximumIntegerValue ? null : entityIntegerRangeDetail.getMaximumIntegerValue();
            var isDefault = filterIsDefault ? null : entityIntegerRangeDetail.getIsDefault();
            var sortOrder = filterSortOrder ? null : entityIntegerRangeDetail.getSortOrder();
            var description = coreControl.getBestEntityIntegerRangeDescription(entityIntegerRange, getLanguage(userVisit));
            
            entityIntegerRangeTransfer = new EntityIntegerRangeTransfer(entityAttributeTransfer, entityIntegerRangeName, minimumIntegerValue, maximumIntegerValue, isDefault,
                    sortOrder, description);
            put(userVisit, entityIntegerRange, entityIntegerRangeTransfer);
        }
        return entityIntegerRangeTransfer;
    }
    
}

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
import com.echothree.model.control.core.common.transfer.EntityListItemTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityListItem;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.server.persistence.Session;

public class EntityListItemTransferCache
        extends BaseCoreTransferCache<EntityListItem, EntityListItemTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);

    TransferProperties transferProperties;
    boolean filterEntityAttribute;
    boolean filterEntityListItemName;
    boolean filterIsDefault;
    boolean filterSortOrder;
    boolean filterDescription;
    boolean filterEntityInstance;

    /** Creates a new instance of EntityListItemTransferCache */
    public EntityListItemTransferCache(UserVisit userVisit) {
        super(userVisit);

        var options = session.getOptions();
        if(options != null) {
            setIncludeEntityAttributeGroups(options.contains(CoreOptions.EntityListItemIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(CoreOptions.EntityListItemIncludeTagScopes));
        }

        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(EntityListItemTransfer.class);
            
            if(properties != null) {
                filterEntityAttribute = !properties.contains(CoreProperties.ENTITY_ATTRIBUTE);
                filterEntityListItemName = !properties.contains(CoreProperties.ENTITY_LIST_ITEM_NAME);
                filterIsDefault = !properties.contains(CoreProperties.IS_DEFAULT);
                filterSortOrder = !properties.contains(CoreProperties.SORT_ORDER);
                filterDescription = !properties.contains(CoreProperties.DESCRIPTION);
                filterEntityInstance = !properties.contains(CoreProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }
    
    public EntityListItemTransfer getEntityListItemTransfer(EntityListItem entityListItem, EntityInstance entityInstance) {
        var entityListItemTransfer = get(entityListItem);
        
        if(entityListItemTransfer == null) {
            var entityListItemDetail = entityListItem.getLastDetail();
            var entityAttributeTransfer = filterEntityAttribute ? null : entityInstance == null ? coreControl.getEntityAttributeTransfer(userVisit, entityListItemDetail.getEntityAttribute(), entityInstance) : null;
            var entityListItemName = filterEntityListItemName ? null : entityListItemDetail.getEntityListItemName();
            var isDefault = filterIsDefault ? null : entityListItemDetail.getIsDefault();
            var sortOrder = filterSortOrder ? null : entityListItemDetail.getSortOrder();
            var description = filterDescription ? null : coreControl.getBestEntityListItemDescription(entityListItem, getLanguage());
            
            entityListItemTransfer = new EntityListItemTransfer(entityAttributeTransfer, entityListItemName, isDefault, sortOrder, description);
            put(entityListItem, entityListItemTransfer);
        }
        
        return entityListItemTransfer;
    }
    
}

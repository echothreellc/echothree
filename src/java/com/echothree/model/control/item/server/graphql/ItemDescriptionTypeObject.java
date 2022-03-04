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

package com.echothree.model.control.item.server.graphql;

import com.echothree.model.control.core.server.graphql.CoreSecurityUtils;
import com.echothree.model.control.core.server.graphql.MimeTypeUsageTypeObject;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.item.server.entity.ItemDescriptionType;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("item description type object")
@GraphQLName("ItemDescriptionType")
public class ItemDescriptionTypeObject
        extends BaseEntityInstanceObject {
    
    private final ItemDescriptionType itemDescriptionType; // Always Present
    
    public ItemDescriptionTypeObject(ItemDescriptionType itemDescriptionType) {
        super(itemDescriptionType.getPrimaryKey());
        
        this.itemDescriptionType = itemDescriptionType;
    }

    private ItemDescriptionTypeDetail itemDescriptionTypeDetail; // Optional, use getItemDescriptionTypeDetail()
    
    private ItemDescriptionTypeDetail getItemDescriptionTypeDetail() {
        if(itemDescriptionTypeDetail == null) {
            itemDescriptionTypeDetail = itemDescriptionType.getLastDetail();
        }
        
        return itemDescriptionTypeDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("item category name")
    @GraphQLNonNull
    public String getItemDescriptionTypeName() {
        return getItemDescriptionTypeDetail().getItemDescriptionTypeName();
    }

    @GraphQLField
    @GraphQLDescription("parent item description type")
    public ItemDescriptionTypeObject getParentItemDescriptionType() {
        var parentItemDescriptionType = getItemDescriptionTypeDetail().getParentItemDescriptionType();
        
        return parentItemDescriptionType == null ? null : new ItemDescriptionTypeObject(parentItemDescriptionType);
    }

    @GraphQLField
    @GraphQLDescription("use parent if missing")
    @GraphQLNonNull
    public boolean getUseParentIfMissing() {
        return getItemDescriptionTypeDetail().getUseParentIfMissing();
    }

    @GraphQLField
    @GraphQLDescription("mime type usage type")
    @GraphQLNonNull
    public MimeTypeUsageTypeObject getMimeTypeUsageType(final DataFetchingEnvironment env) {
        var mimeTypeUsageType = getItemDescriptionTypeDetail().getMimeTypeUsageType();

        return mimeTypeUsageType == null ? null : (CoreSecurityUtils.getInstance().getHasMimeTypeUsageTypeAccess(env) ? new MimeTypeUsageTypeObject(mimeTypeUsageType) : null);
    }

    @GraphQLField
    @GraphQLDescription("check content web address")
    @GraphQLNonNull
    public boolean getCheckContentWebAddress() {
        return getItemDescriptionTypeDetail().getCheckContentWebAddress();
    }

    @GraphQLField
    @GraphQLDescription("include in index")
    @GraphQLNonNull
    public boolean getIncludeInIndex() {
        return getItemDescriptionTypeDetail().getIncludeInIndex();
    }

    @GraphQLField
    @GraphQLDescription("index default")
    @GraphQLNonNull
    public boolean getIndexDefault() {
        return getItemDescriptionTypeDetail().getIndexDefault();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getItemDescriptionTypeDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getItemDescriptionTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var itemControl = Session.getModelController(ItemControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return itemControl.getBestItemDescriptionTypeDescription(itemDescriptionType, userControl.getPreferredLanguageFromUserVisit(getUserVisit(env)));
    }

}

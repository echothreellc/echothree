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

package com.echothree.model.control.item.server.graphql;

import com.echothree.model.control.core.server.graphql.CoreSecurityUtils;
import com.echothree.model.control.core.server.graphql.MimeTypeObject;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.item.server.entity.ItemImageType;
import com.echothree.model.data.item.server.entity.ItemImageTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("item image type object")
@GraphQLName("ItemImageType")
public class ItemImageTypeObject
        extends BaseEntityInstanceObject {
    
    private final ItemImageType itemImageType; // Always Present
    
    public ItemImageTypeObject(ItemImageType itemImageType) {
        super(itemImageType.getPrimaryKey());
        
        this.itemImageType = itemImageType;
    }

    private ItemImageTypeDetail itemImageTypeDetail; // Optional, use getItemImageTypeDetail()
    
    private ItemImageTypeDetail getItemImageTypeDetail() {
        if(itemImageTypeDetail == null) {
            itemImageTypeDetail = itemImageType.getLastDetail();
        }
        
        return itemImageTypeDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("item image type name")
    @GraphQLNonNull
    public String getItemImageTypeName() {
        return getItemImageTypeDetail().getItemImageTypeName();
    }

    @GraphQLField
    @GraphQLDescription("preferred mime type")
    public MimeTypeObject getPreferredMimeType(final DataFetchingEnvironment env) {
        var preferredMimeType = getItemImageTypeDetail().getPreferredMimeType();

        return preferredMimeType == null ? null : (CoreSecurityUtils.getHasMimeTypeAccess(env) ? new MimeTypeObject(preferredMimeType) : null);
    }

    @GraphQLField
    @GraphQLDescription("quality")
    public Integer getQuality() {
        return getItemImageTypeDetail().getQuality();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getItemImageTypeDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getItemImageTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var itemControl = Session.getModelController(ItemControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return itemControl.getBestItemImageTypeDescription(itemImageType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}

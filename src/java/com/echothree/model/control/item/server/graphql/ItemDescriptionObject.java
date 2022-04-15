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
import com.echothree.model.control.core.server.graphql.MimeTypeObject;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.party.server.graphql.LanguageObject;
import com.echothree.model.control.party.server.graphql.PartySecurityUtils;
import com.echothree.model.data.item.server.entity.ItemDescription;
import com.echothree.model.data.item.server.entity.ItemDescriptionDetail;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("item description object")
@GraphQLName("ItemDescription")
public class ItemDescriptionObject
        extends BaseEntityInstanceObject {
    
    private final ItemDescription itemDescription; // Always Present
    
    public ItemDescriptionObject(ItemDescription itemDescription) {
        super(itemDescription.getPrimaryKey());
        
        this.itemDescription = itemDescription;
    }

    private ItemDescriptionDetail itemDescriptionDetail; // Optional, use getItemDescriptionDetail()
    
    private ItemDescriptionDetail getItemDescriptionDetail() {
        if(itemDescriptionDetail == null) {
            itemDescriptionDetail = itemDescription.getLastDetail();
        }
        
        return itemDescriptionDetail;
    }

    @GraphQLField
    @GraphQLDescription("item description type")
    @GraphQLNonNull
    public ItemDescriptionTypeObject getItemDescriptionType(final DataFetchingEnvironment env) {
        var itemDescriptionType = getItemDescriptionDetail().getItemDescriptionType();

        return itemDescriptionType == null ? null : (ItemSecurityUtils.getInstance().getHasItemDescriptionTypeAccess(env) ? new ItemDescriptionTypeObject(itemDescriptionType) : null);
    }

    @GraphQLField
    @GraphQLDescription("item")
    @GraphQLNonNull
    public ItemObject getItem(final DataFetchingEnvironment env) {
        var item = getItemDescriptionDetail().getItem();

        return item == null ? null : (ItemSecurityUtils.getInstance().getHasItemAccess(env) ? new ItemObject(item) : null);
    }

    @GraphQLField
    @GraphQLDescription("language")
    @GraphQLNonNull
    public LanguageObject getLanguage(final DataFetchingEnvironment env) {
        var mimeType = getItemDescriptionDetail().getLanguage();

        return mimeType == null ? null : (PartySecurityUtils.getInstance().getHasLanguageAccess(env) ? new LanguageObject(mimeType) : null);
    }

    @GraphQLField
    @GraphQLDescription("mime type")
    public MimeTypeObject getMimeType(final DataFetchingEnvironment env) {
        var mimeType = getItemDescriptionDetail().getMimeType();

        return mimeType == null ? null : (CoreSecurityUtils.getInstance().getHasMimeTypeAccess(env) ? new MimeTypeObject(mimeType) : null);
    }

}

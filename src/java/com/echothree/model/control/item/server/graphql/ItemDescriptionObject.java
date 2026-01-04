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
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.server.graphql.LanguageObject;
import com.echothree.model.control.party.server.graphql.PartySecurityUtils;
import com.echothree.model.data.item.server.entity.ItemClobDescription;
import com.echothree.model.data.item.server.entity.ItemDescription;
import com.echothree.model.data.item.server.entity.ItemDescriptionDetail;
import com.echothree.model.data.item.server.entity.ItemImageDescription;
import com.echothree.model.data.item.server.entity.ItemStringDescription;
import com.echothree.util.server.persistence.Session;
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

    private ItemClobDescription itemClobDescription; // Optional, use getItemClobDescription()

    private ItemClobDescription getItemClobDescription() {
        if(itemClobDescription == null) {
            var itemControl = Session.getModelController(ItemControl.class);

            itemClobDescription = itemControl.getItemClobDescription(itemDescription);
        }

        return itemClobDescription;
    }

    private ItemStringDescription itemStringDescription; // Optional, use getItemStringDescription()

    private ItemStringDescription getItemStringDescription() {
        if(itemStringDescription == null) {
            var itemControl = Session.getModelController(ItemControl.class);

            itemStringDescription = itemControl.getItemStringDescription(itemDescription);
        }

        return itemStringDescription;
    }

    private ItemImageDescription itemImageDescription; // Optional, use getItemImageDescription()

    private ItemImageDescription getItemImageDescription() {
        if(itemImageDescription == null) {
            var itemControl = Session.getModelController(ItemControl.class);

            itemImageDescription = itemControl.getItemImageDescription(itemDescription);
        }

        return itemImageDescription;
    }

    @GraphQLField
    @GraphQLDescription("item description type")
    @GraphQLNonNull
    public ItemDescriptionTypeObject getItemDescriptionType(final DataFetchingEnvironment env) {
        var itemDescriptionType = getItemDescriptionDetail().getItemDescriptionType();

        return itemDescriptionType == null ? null : (ItemSecurityUtils.getHasItemDescriptionTypeAccess(env) ? new ItemDescriptionTypeObject(itemDescriptionType) : null);
    }

    @GraphQLField
    @GraphQLDescription("item")
    @GraphQLNonNull
    public ItemObject getItem(final DataFetchingEnvironment env) {
        var item = getItemDescriptionDetail().getItem();

        return item == null ? null : (ItemSecurityUtils.getHasItemAccess(env) ? new ItemObject(item) : null);
    }

    @GraphQLField
    @GraphQLDescription("language")
    @GraphQLNonNull
    public LanguageObject getLanguage(final DataFetchingEnvironment env) {
        var mimeType = getItemDescriptionDetail().getLanguage();

        return mimeType == null ? null : (PartySecurityUtils.getHasLanguageAccess(env) ? new LanguageObject(mimeType) : null);
    }

    @GraphQLField
    @GraphQLDescription("mime type")
    public MimeTypeObject getMimeType(final DataFetchingEnvironment env) {
        var mimeType = getItemDescriptionDetail().getMimeType();

        return mimeType == null ? null : (CoreSecurityUtils.getHasMimeTypeAccess(env) ? new MimeTypeObject(mimeType) : null);
    }

    @GraphQLField
    @GraphQLDescription("clob description")
    public String getClobDescription(final DataFetchingEnvironment env) {
        var itemClobDescription = getItemClobDescription();
        
        return itemClobDescription == null ? null : itemClobDescription.getClobDescription();
    }

    @GraphQLField
    @GraphQLDescription("string description")
    public String getStringDescription(final DataFetchingEnvironment env) {
        var itemStringDescription = getItemStringDescription();

        return itemStringDescription == null ? null : itemStringDescription.getStringDescription();
    }

    @GraphQLField
    @GraphQLDescription("item image type")
    public ItemImageTypeObject getItemImageType(final DataFetchingEnvironment env) {
        var itemImageDescription = getItemImageDescription();
        var itemImageType = itemImageDescription == null ? null : getItemImageDescription().getItemImageType();

        return itemImageType == null ? null : (ItemSecurityUtils.getHasItemImageTypeAccess(env) ? new ItemImageTypeObject(itemImageType) : null);
    }

    @GraphQLField
    @GraphQLDescription("height")
    public Integer getHeight(final DataFetchingEnvironment env) {
        var itemImageDescription = getItemImageDescription();

        return itemImageDescription == null ? null : itemImageDescription.getHeight();
    }

    @GraphQLField
    @GraphQLDescription("width")
    public Integer getWidth(final DataFetchingEnvironment env) {
        var itemImageDescription = getItemImageDescription();

        return itemImageDescription == null ? null : itemImageDescription.getWidth();
    }

    @GraphQLField
    @GraphQLDescription("scaled from parent")
    public Boolean getScaledFromParent(final DataFetchingEnvironment env) {
        var itemImageDescription = getItemImageDescription();

        return itemImageDescription == null ? null : itemImageDescription.getScaledFromParent();
    }

}

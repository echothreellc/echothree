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
import com.echothree.model.control.core.server.graphql.MimeTypeUsageTypeObject;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.item.common.ItemDescriptionTypeUseConstants;
import com.echothree.model.data.item.server.entity.ItemDescriptionType;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeDetail;
import com.echothree.model.data.item.server.entity.ItemImageDescriptionType;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

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

    private ItemImageDescriptionType itemImageDescriptionType; // Optional, use getItemImageDescriptionType()

    private ItemImageDescriptionType getItemImageDescriptionType() {
        if(itemImageDescriptionType == null) {
            var itemControl = Session.getModelController(ItemControl.class);

            itemImageDescriptionType = itemControl.getItemImageDescriptionType(itemDescriptionType);
        }

        return itemImageDescriptionType;
    }

    @GraphQLField
    @GraphQLDescription("item description type name")
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

        return mimeTypeUsageType == null ? null : (CoreSecurityUtils.getHasMimeTypeUsageTypeAccess(env) ? new MimeTypeUsageTypeObject(mimeTypeUsageType) : null);
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

        return itemControl.getBestItemDescriptionTypeDescription(itemDescriptionType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("minimum height")
    public Integer getMinimumHeight() {
        var itemImageDescriptionType = getItemImageDescriptionType();

        return itemImageDescriptionType == null ? null : itemImageDescriptionType.getMinimumHeight();
    }

    @GraphQLField
    @GraphQLDescription("minimum width")
    public Integer getMinimumWidth() {
        var itemImageDescriptionType = getItemImageDescriptionType();

        return itemImageDescriptionType == null ? null : itemImageDescriptionType.getMinimumWidth();
    }

    @GraphQLField
    @GraphQLDescription("maximum height")
    public Integer getMaximumHeight() {
        var itemImageDescriptionType = getItemImageDescriptionType();

        return itemImageDescriptionType == null ? null : itemImageDescriptionType.getMaximumHeight();
    }

    @GraphQLField
    @GraphQLDescription("maximum width")
    public Integer getMaximumWidth() {
        var itemImageDescriptionType = getItemImageDescriptionType();

        return itemImageDescriptionType == null ? null : itemImageDescriptionType.getMaximumWidth();
    }

    @GraphQLField
    @GraphQLDescription("preferred height")
    public Integer getPreferredHeight() {
        var itemImageDescriptionType = getItemImageDescriptionType();

        return itemImageDescriptionType == null ? null : itemImageDescriptionType.getPreferredHeight();
    }

    @GraphQLField
    @GraphQLDescription("preferred width")
    public Integer getPreferredWidth() {
        var itemImageDescriptionType = getItemImageDescriptionType();

        return itemImageDescriptionType == null ? null : itemImageDescriptionType.getPreferredWidth();
    }

    @GraphQLField
    @GraphQLDescription("preferred mime type")
    public MimeTypeObject getPreferredMimeType(final DataFetchingEnvironment env) {
        var itemImageDescriptionType = getItemImageDescriptionType();
        MimeTypeObject mimeTypeObject = null;

        if(itemImageDescriptionType != null && CoreSecurityUtils.getHasMimeTypeAccess(env)) {
            var preferredMimeType = itemImageDescriptionType.getPreferredMimeType();

            mimeTypeObject = preferredMimeType == null ? null : new MimeTypeObject(preferredMimeType);
        }

        return mimeTypeObject;
    }

    @GraphQLField
    @GraphQLDescription("quality")
    public Integer getQuality() {
        var itemImageDescriptionType = getItemImageDescriptionType();

        return itemImageDescriptionType == null ? null : itemImageDescriptionType.getQuality();
    }

    @GraphQLField
    @GraphQLDescription("scale from parent")
    public Boolean getScaleFromParent() {
        var itemImageDescriptionType = getItemImageDescriptionType();

        return itemImageDescriptionType == null ? null : itemImageDescriptionType.getScaleFromParent();
    }

    @GraphQLField
    @GraphQLDescription("item description type uses")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<ItemDescriptionTypeUseObject> getItemDescriptionTypeUses(final DataFetchingEnvironment env) {
        if(ItemSecurityUtils.getHasItemDescriptionTypeUsesAccess(env)) {
            var itemControl = Session.getModelController(ItemControl.class);
            var totalCount = itemControl.countItemDescriptionTypeUsesByItemDescriptionType(itemDescriptionType);

            try(var objectLimiter = new ObjectLimiter(env, ItemDescriptionTypeUseConstants.COMPONENT_VENDOR_NAME, ItemDescriptionTypeUseConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = itemControl.getItemDescriptionTypeUsesByItemDescriptionType(itemDescriptionType);
                var itemDescriptionTypeUses = entities.stream().map(ItemDescriptionTypeUseObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, itemDescriptionTypeUses);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}

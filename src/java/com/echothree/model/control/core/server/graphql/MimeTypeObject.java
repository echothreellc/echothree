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

package com.echothree.model.control.core.server.graphql;

import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.core.common.MimeTypeFileExtensionConstants;
import com.echothree.model.data.core.common.MimeTypeUsageConstants;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@GraphQLDescription("mime type object")
@GraphQLName("MimeType")
public class MimeTypeObject
        extends BaseEntityInstanceObject {
    
    private final MimeType mimeType; // Always Present
    
    public MimeTypeObject(MimeType mimeType) {
        super(mimeType.getPrimaryKey());
        
        this.mimeType = mimeType;
    }

    private MimeTypeDetail mimeTypeDetail; // Optional, use getMimeTypeDetail()
    
    private MimeTypeDetail getMimeTypeDetail() {
        if(mimeTypeDetail == null) {
            mimeTypeDetail = mimeType.getLastDetail();
        }
        
        return mimeTypeDetail;
    }

    @GraphQLField
    @GraphQLDescription("mime type name")
    @GraphQLNonNull
    public String getMimeTypeName() {
        return getMimeTypeDetail().getMimeTypeName();
    }

    @GraphQLField
    @GraphQLDescription("entity attribute type")
    @GraphQLNonNull
    public EntityAttributeTypeObject getEntityAttributeType(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityAttributeTypeAccess(env) ? new EntityAttributeTypeObject(getMimeTypeDetail().getEntityAttributeType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getMimeTypeDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getMimeTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return mimeTypeControl.getBestMimeTypeDescription(mimeType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
    @GraphQLField
    @GraphQLDescription("mime type usages")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<MimeTypeUsageObject> getMimeTypeUsages(final DataFetchingEnvironment env) {
        if(CoreSecurityUtils.getHasMimeTypeUsagesAccess(env)) {
            var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
            var totalCount = mimeTypeControl.countMimeTypeUsagesByMimeType(mimeType);

            try(var objectLimiter = new ObjectLimiter(env, MimeTypeUsageConstants.COMPONENT_VENDOR_NAME, MimeTypeUsageConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = mimeTypeControl.getMimeTypeUsagesByMimeType(mimeType);
                var mimeTypeUsages = entities.stream().map(MimeTypeUsageObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, mimeTypeUsages);
            }
        } else {
            return Connections.emptyConnection();
        }
    }
    
    @GraphQLField
    @GraphQLDescription("mime type file extensions")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<MimeTypeFileExtensionObject> getMimeTypeFileExtensions(final DataFetchingEnvironment env) {
        if(CoreSecurityUtils.getHasMimeTypeFileExtensionsAccess(env)) {
            var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
            var totalCount = mimeTypeControl.countMimeTypeFileExtensionsByMimeType(mimeType);

            try(var objectLimiter = new ObjectLimiter(env, MimeTypeFileExtensionConstants.COMPONENT_VENDOR_NAME, MimeTypeFileExtensionConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = mimeTypeControl.getMimeTypeFileExtensionsByMimeType(mimeType);
                var mimeTypeFileExtensions = entities.stream().map(MimeTypeFileExtensionObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, mimeTypeFileExtensions);
            }
        } else {
            return Connections.emptyConnection();
        }
    }
    
}

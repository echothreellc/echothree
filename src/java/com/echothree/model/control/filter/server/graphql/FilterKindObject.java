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

package com.echothree.model.control.filter.server.graphql;

import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.filter.common.FilterAdjustmentConstants;
import com.echothree.model.data.filter.common.FilterTypeConstants;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.entity.FilterKindDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@GraphQLDescription("filter kind object")
@GraphQLName("FilterKind")
public class FilterKindObject
        extends BaseEntityInstanceObject {
    
    private final FilterKind filterKind; // Always Present
    
    public FilterKindObject(FilterKind filterKind) {
        super(filterKind.getPrimaryKey());
        
        this.filterKind = filterKind;
    }

    private FilterKindDetail filterKindDetail; // Optional, use getFilterKindDetail()
    
    private FilterKindDetail getFilterKindDetail() {
        if(filterKindDetail == null) {
            filterKindDetail = filterKind.getLastDetail();
        }
        
        return filterKindDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("filter kind name")
    @GraphQLNonNull
    public String getFilterKindName() {
        return getFilterKindDetail().getFilterKindName();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getFilterKindDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getFilterKindDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var filterControl = Session.getModelController(FilterControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return filterControl.getBestFilterKindDescription(filterKind, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("filter types")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<FilterTypeObject> getFilterTypes(final DataFetchingEnvironment env) {
        if(FilterSecurityUtils.getHasFilterTypesAccess(env)) {
            var filterControl = Session.getModelController(FilterControl.class);
            var totalCount = filterControl.countFilterTypesByFilterKind(filterKind);

            try(var objectLimiter = new ObjectLimiter(env, FilterTypeConstants.COMPONENT_VENDOR_NAME, FilterTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = filterControl.getFilterTypes(filterKind);
                var filterTypes = entities.stream().map(FilterTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, filterTypes);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("filter adjustments")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<FilterAdjustmentObject> getFilterAdjustments(final DataFetchingEnvironment env) {
        if(FilterSecurityUtils.getHasFilterAdjustmentsAccess(env)) {
            var filterControl = Session.getModelController(FilterControl.class);
            var totalCount = filterControl.countFilterAdjustmentsByFilterKind(filterKind);

            try(var objectLimiter = new ObjectLimiter(env, FilterAdjustmentConstants.COMPONENT_VENDOR_NAME, FilterAdjustmentConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = filterControl.getFilterAdjustmentsByFilterKind(filterKind);
                var filterAdjustments = entities.stream().map(FilterAdjustmentObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, filterAdjustments);
            }
        } else {
            return Connections.emptyConnection();
        }
    }
    
}

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

import com.echothree.model.control.filter.server.control.FilterAdjustmentControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.filter.common.FilterAdjustmentAmountConstants;
import com.echothree.model.data.filter.common.FilterAdjustmentFixedAmountConstants;
import com.echothree.model.data.filter.common.FilterAdjustmentPercentConstants;
import com.echothree.model.data.filter.server.entity.FilterAdjustment;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("filter adjustment object")
@GraphQLName("FilterAdjustment")
public class FilterAdjustmentObject
        extends BaseEntityInstanceObject {

    private final FilterAdjustment filterAdjustment; // Always Present

    public FilterAdjustmentObject(FilterAdjustment filterAdjustment) {
        super(filterAdjustment.getPrimaryKey());

        this.filterAdjustment = filterAdjustment;
    }

    private FilterAdjustmentDetail filterAdjustmentDetail; // Optional, use getFilterAdjustmentDetail()

    private FilterAdjustmentDetail getFilterAdjustmentDetail() {
        if(filterAdjustmentDetail == null) {
            filterAdjustmentDetail = filterAdjustment.getLastDetail();
        }

        return filterAdjustmentDetail;
    }

    @GraphQLField
    @GraphQLDescription("filter kind")
    public FilterKindObject getFilterKind(final DataFetchingEnvironment env) {
        return FilterSecurityUtils.getHasFilterKindAccess(env) ? new FilterKindObject(getFilterAdjustmentDetail().getFilterKind()) : null;
    }

    @GraphQLField
    @GraphQLDescription("filter type name")
    @GraphQLNonNull
    public String getFilterAdjustmentName() {
        return getFilterAdjustmentDetail().getFilterAdjustmentName();
    }

    @GraphQLField
    @GraphQLDescription("filter adjustment source")
    public FilterAdjustmentSourceObject getFilterAdjustmentSource(final DataFetchingEnvironment env) {
        return FilterSecurityUtils.getHasFilterAdjustmentSourceAccess(env) ? new FilterAdjustmentSourceObject(getFilterAdjustmentDetail().getFilterAdjustmentSource()) : null;
    }

    @GraphQLField
    @GraphQLDescription("filter adjustment type")
    public FilterAdjustmentTypeObject getFilterAdjustmentType(final DataFetchingEnvironment env) {
        var filterAdjustmentType = getFilterAdjustmentDetail().getFilterAdjustmentType();

        return filterAdjustmentType == null ? null : FilterSecurityUtils.getHasFilterAdjustmentTypeAccess(env) ? new FilterAdjustmentTypeObject(filterAdjustmentType) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getFilterAdjustmentDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getFilterAdjustmentDetail().getSortOrder();
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var filterAdjustmentControl = Session.getModelController(FilterAdjustmentControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return filterAdjustmentControl.getBestFilterAdjustmentDescription(filterAdjustment, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("filter adjustment amounts")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<FilterAdjustmentAmountObject> getFilterAdjustmentAmounts(final DataFetchingEnvironment env) {
        CountingPaginatedData<FilterAdjustmentAmountObject> filterAdjustmentAmountObjects;

        if(FilterSecurityUtils.getHasFilterAdjustmentAmountsAccess(env)) {
            var filterAdjustmentControl = Session.getModelController(FilterAdjustmentControl.class);
            var totalCount = filterAdjustmentControl.countFilterAdjustmentAmountsByFilterAdjustment(filterAdjustment);

            try(var objectLimiter = new ObjectLimiter(env, FilterAdjustmentAmountConstants.COMPONENT_VENDOR_NAME, FilterAdjustmentAmountConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = filterAdjustmentControl.getFilterAdjustmentAmounts(filterAdjustment);
                var items = entities.stream().map(FilterAdjustmentAmountObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                filterAdjustmentAmountObjects = new CountedObjects<>(objectLimiter, items);
            }
        } else {
            filterAdjustmentAmountObjects = Connections.emptyConnection();
        }

        return filterAdjustmentAmountObjects;
    }

    @GraphQLField
    @GraphQLDescription("filter adjustment fixed amounts")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<FilterAdjustmentFixedAmountObject> getFilterAdjustmentFixedAmounts(final DataFetchingEnvironment env) {
        CountingPaginatedData<FilterAdjustmentFixedAmountObject> filterAdjustmentFixedAmountObjects;

        if(FilterSecurityUtils.getHasFilterAdjustmentFixedAmountsAccess(env)) {
            var filterAdjustmentControl = Session.getModelController(FilterAdjustmentControl.class);
            var totalCount = filterAdjustmentControl.countFilterAdjustmentFixedAmountsByFilterAdjustment(filterAdjustment);

            try(var objectLimiter = new ObjectLimiter(env, FilterAdjustmentFixedAmountConstants.COMPONENT_VENDOR_NAME, FilterAdjustmentFixedAmountConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = filterAdjustmentControl.getFilterAdjustmentFixedAmounts(filterAdjustment);
                var items = entities.stream().map(FilterAdjustmentFixedAmountObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                filterAdjustmentFixedAmountObjects = new CountedObjects<>(objectLimiter, items);
            }
        } else {
            filterAdjustmentFixedAmountObjects = Connections.emptyConnection();
        }

        return filterAdjustmentFixedAmountObjects;
    }

    @GraphQLField
    @GraphQLDescription("filter adjustment percents")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<FilterAdjustmentPercentObject> getFilterAdjustmentPercents(final DataFetchingEnvironment env) {
        CountingPaginatedData<FilterAdjustmentPercentObject> filterAdjustmentPercentObjects;

        if(FilterSecurityUtils.getHasFilterAdjustmentPercentsAccess(env)) {
            var filterAdjustmentControl = Session.getModelController(FilterAdjustmentControl.class);
            var totalCount = filterAdjustmentControl.countFilterAdjustmentPercentsByFilterAdjustment(filterAdjustment);

            try(var objectLimiter = new ObjectLimiter(env, FilterAdjustmentPercentConstants.COMPONENT_VENDOR_NAME, FilterAdjustmentPercentConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = filterAdjustmentControl.getFilterAdjustmentPercents(filterAdjustment);
                var items = entities.stream().map(FilterAdjustmentPercentObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                filterAdjustmentPercentObjects = new CountedObjects<>(objectLimiter, items);
            }
        } else {
            filterAdjustmentPercentObjects = Connections.emptyConnection();
        }

        return filterAdjustmentPercentObjects;
    }

}

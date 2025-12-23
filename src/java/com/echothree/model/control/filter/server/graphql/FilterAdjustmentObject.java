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
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.filter.server.entity.FilterAdjustment;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.Collection;

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
        var filterControl = Session.getModelController(FilterControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return filterControl.getBestFilterAdjustmentDescription(filterAdjustment, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("filter adjustment amounts")
    public Collection<FilterAdjustmentAmountObject> getFilterAdjustmentAmounts(final DataFetchingEnvironment env) {
        Collection<FilterAdjustmentAmountObject> filterAdjustmentAmountObjects = null;

        if(FilterSecurityUtils.getHasFilterAdjustmentAmountsAccess(env)) {
            var filterControl = Session.getModelController(FilterControl.class);
            var filterAdjustmentAmounts = filterControl.getFilterAdjustmentAmounts(filterAdjustment);

            filterAdjustmentAmountObjects = new ArrayList<>(filterAdjustmentAmounts.size());

            filterAdjustmentAmounts.stream()
                    .map(FilterAdjustmentAmountObject::new)
                    .forEachOrdered(filterAdjustmentAmountObjects::add);
        }

        return filterAdjustmentAmountObjects;
    }

    @GraphQLField
    @GraphQLDescription("filter adjustment amounts")
    public Collection<FilterAdjustmentFixedAmountObject> getFilterAdjustmentFixedAmounts(final DataFetchingEnvironment env) {
        Collection<FilterAdjustmentFixedAmountObject> filterAdjustmentFixedAmountObjects = null;

        if(FilterSecurityUtils.getHasFilterAdjustmentFixedAmountsAccess(env)) {
            var filterControl = Session.getModelController(FilterControl.class);
            var filterAdjustmentFixedAmounts = filterControl.getFilterAdjustmentFixedAmounts(filterAdjustment);

            filterAdjustmentFixedAmountObjects = new ArrayList<>(filterAdjustmentFixedAmounts.size());

            filterAdjustmentFixedAmounts.stream()
                    .map(FilterAdjustmentFixedAmountObject::new)
                    .forEachOrdered(filterAdjustmentFixedAmountObjects::add);
        }

        return filterAdjustmentFixedAmountObjects;
    }

    @GraphQLField
    @GraphQLDescription("filter adjustment percents")
    public Collection<FilterAdjustmentPercentObject> getFilterAdjustmentPercents(final DataFetchingEnvironment env) {
        Collection<FilterAdjustmentPercentObject> filterAdjustmentPercentObjects = null;

        if(FilterSecurityUtils.getHasFilterAdjustmentPercentsAccess(env)) {
            var filterControl = Session.getModelController(FilterControl.class);
            var filterAdjustmentPercents = filterControl.getFilterAdjustmentPercents(filterAdjustment);

            filterAdjustmentPercentObjects = new ArrayList<>(filterAdjustmentPercents.size());

            filterAdjustmentPercents.stream()
                    .map(FilterAdjustmentPercentObject::new)
                    .forEachOrdered(filterAdjustmentPercentObjects::add);
        }

        return filterAdjustmentPercentObjects;
    }

}

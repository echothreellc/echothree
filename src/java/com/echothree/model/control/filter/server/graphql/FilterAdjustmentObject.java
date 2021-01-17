// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.filter.server.entity.FilterAdjustment;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

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
        return FilterSecurityUtils.getInstance().getHasFilterKindAccess(env) ? new FilterKindObject(getFilterAdjustmentDetail().getFilterKind()) : null;
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
        return FilterSecurityUtils.getInstance().getHasFilterAdjustmentSourceAccess(env) ? new FilterAdjustmentSourceObject(getFilterAdjustmentDetail().getFilterAdjustmentSource()) : null;
    }

    @GraphQLField
    @GraphQLDescription("filter adjustment type")
    public FilterAdjustmentTypeObject getFilterAdjustmentType(final DataFetchingEnvironment env) {
        var filterAdjustmentType = getFilterAdjustmentDetail().getFilterAdjustmentType();

        return filterAdjustmentType == null ? null : FilterSecurityUtils.getInstance().getHasFilterAdjustmentTypeAccess(env) ? new FilterAdjustmentTypeObject(filterAdjustmentType) : null;
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
        GraphQlContext context = env.getContext();

        return filterControl.getBestFilterAdjustmentDescription(filterAdjustment, userControl.getPreferredLanguageFromUserVisit(context.getUserVisit()));
    }
    
}

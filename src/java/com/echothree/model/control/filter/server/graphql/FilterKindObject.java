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
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.entity.FilterKindDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.Collection;

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
    public Collection<FilterTypeObject> getFilterTypes(final DataFetchingEnvironment env) {
        Collection<FilterTypeObject> filterTypeObjects = null;

        if(FilterSecurityUtils.getHasFilterTypesAccess(env)) {
            var filterControl = Session.getModelController(FilterControl.class);
            var filterTypes = filterControl.getFilterTypes(filterKind);

            filterTypeObjects = new ArrayList<>(filterTypes.size());

            filterTypes.stream()
                    .map(FilterTypeObject::new)
                    .forEachOrdered(filterTypeObjects::add);
        }

        return filterTypeObjects;
    }

    @GraphQLField
    @GraphQLDescription("filter adjustments")
    public Collection<FilterAdjustmentObject> getFilterAdjustments(final DataFetchingEnvironment env) {
        Collection<FilterAdjustmentObject> filterAdjustmentObjects = null;

        if(FilterSecurityUtils.getHasFilterAdjustmentsAccess(env)) {
            var filterControl = Session.getModelController(FilterControl.class);
            var filterAdjustments = filterControl.getFilterAdjustmentsByFilterKind(filterKind);

            filterAdjustmentObjects = new ArrayList<>(filterAdjustments.size());

            filterAdjustments.stream()
                    .map(FilterAdjustmentObject::new)
                    .forEachOrdered(filterAdjustmentObjects::add);
        }

        return filterAdjustmentObjects;
    }
    
}

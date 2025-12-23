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
import com.echothree.model.data.filter.server.entity.FilterType;
import com.echothree.model.data.filter.server.entity.FilterTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.Collection;

@GraphQLDescription("filter type object")
@GraphQLName("FilterType")
public class FilterTypeObject
        extends BaseEntityInstanceObject {
    
    private final FilterType filterType; // Always Present
    
    public FilterTypeObject(FilterType filterType) {
        super(filterType.getPrimaryKey());
        
        this.filterType = filterType;
    }

    private FilterTypeDetail filterTypeDetail; // Optional, use getFilterTypeDetail()
    
    private FilterTypeDetail getFilterTypeDetail() {
        if(filterTypeDetail == null) {
            filterTypeDetail = filterType.getLastDetail();
        }
        
        return filterTypeDetail;
    }

    @GraphQLField
    @GraphQLDescription("filter kind")
    public FilterKindObject getFilterKind(final DataFetchingEnvironment env) {
        return FilterSecurityUtils.getHasFilterKindAccess(env) ? new FilterKindObject(getFilterTypeDetail().getFilterKind()) : null;
    }

    @GraphQLField
    @GraphQLDescription("filter type name")
    @GraphQLNonNull
    public String getFilterTypeName() {
        return getFilterTypeDetail().getFilterTypeName();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getFilterTypeDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getFilterTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var filterControl = Session.getModelController(FilterControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return filterControl.getBestFilterTypeDescription(filterType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("filters")
    public Collection<FilterObject> getFilters(final DataFetchingEnvironment env) {
        Collection<FilterObject> filterObjects = null;

        if(FilterSecurityUtils.getHasFiltersAccess(env)) {
            var filterControl = Session.getModelController(FilterControl.class);
            var filters = filterControl.getFilters(filterType);

            filterObjects = new ArrayList<>(filters.size());

            filters.stream()
                    .map(FilterObject::new)
                    .forEachOrdered(filterObjects::add);
        }

        return filterObjects;
    }

}

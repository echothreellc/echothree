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
import com.echothree.model.control.selector.server.graphql.SelectorObject;
import com.echothree.model.control.selector.server.graphql.SelectorSecurityUtils;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.filter.common.FilterEntranceStepConstants;
import com.echothree.model.data.filter.common.FilterStepConstants;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.filter.server.entity.FilterDetail;
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

@GraphQLDescription("filter object")
@GraphQLName("Filter")
public class FilterObject
        extends BaseEntityInstanceObject {
    
    private final Filter filter; // Always Present
    
    public FilterObject(Filter filter) {
        super(filter.getPrimaryKey());
        
        this.filter = filter;
    }

    private FilterDetail filterDetail; // Optional, use getFilterDetail()
    
    private FilterDetail getFilterDetail() {
        if(filterDetail == null) {
            filterDetail = filter.getLastDetail();
        }
        
        return filterDetail;
    }

    @GraphQLField
    @GraphQLDescription("filter type")
    public FilterTypeObject getFilterType(final DataFetchingEnvironment env) {
        return FilterSecurityUtils.getHasFilterTypeAccess(env) ? new FilterTypeObject(getFilterDetail().getFilterType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("filter name")
    @GraphQLNonNull
    public String getFilterName() {
        return getFilterDetail().getFilterName();
    }

    @GraphQLField
    @GraphQLDescription("initial filter adjustment")
    public FilterAdjustmentObject getInitialFilterAdjustment(final DataFetchingEnvironment env) {
        return FilterSecurityUtils.getHasFilterAdjustmentAccess(env) ? new FilterAdjustmentObject(getFilterDetail().getInitialFilterAdjustment()) : null;
    }

    @GraphQLField
    @GraphQLDescription("filter item selector")
    public SelectorObject getFilterItemSelector(final DataFetchingEnvironment env) {
        SelectorObject result;

        if(SelectorSecurityUtils.getHasSelectorAccess(env)) {
            var selector = getFilterDetail().getFilterItemSelector();

            result = selector == null ? null : new SelectorObject(selector);
        } else {
            result = null;
        }

        return result;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getFilterDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getFilterDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var filterControl = Session.getModelController(FilterControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return filterControl.getBestFilterDescription(filter, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("filter entrance steps")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<FilterEntranceStepObject> getFilterEntranceSteps(final DataFetchingEnvironment env) {
        if(FilterSecurityUtils.getHasFilterEntranceStepsAccess(env)) {
            var filterControl = Session.getModelController(FilterControl.class);
            var totalCount = filterControl.countFilterEntranceStepsByFilter(filter);

            try(var objectLimiter = new ObjectLimiter(env, FilterEntranceStepConstants.COMPONENT_VENDOR_NAME, FilterEntranceStepConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = filterControl.getFilterEntranceStepsByFilter(filter);
                var unitOfMeasureTypes = entities.stream().map(FilterEntranceStepObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, unitOfMeasureTypes);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("filter steps")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<FilterStepObject> getFilterSteps(final DataFetchingEnvironment env) {
        if(FilterSecurityUtils.getHasFilterStepsAccess(env)) {
            var filterControl = Session.getModelController(FilterControl.class);
            var totalCount = filterControl.countFilterStepsByFilter(filter);

            try(var objectLimiter = new ObjectLimiter(env, FilterStepConstants.COMPONENT_VENDOR_NAME, FilterStepConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = filterControl.getFilterStepsByFilter(filter);
                var unitOfMeasureTypes = entities.stream().map(FilterStepObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, unitOfMeasureTypes);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}

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

package com.echothree.model.control.selector.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.selector.server.entity.SelectorType;
import com.echothree.model.data.selector.server.entity.SelectorTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.Collection;

@GraphQLDescription("selector type object")
@GraphQLName("SelectorType")
public class SelectorTypeObject
        extends BaseEntityInstanceObject {
    
    private final SelectorType selectorType; // Always Present
    
    public SelectorTypeObject(SelectorType selectorType) {
        super(selectorType.getPrimaryKey());
        
        this.selectorType = selectorType;
    }

    private SelectorTypeDetail selectorTypeDetail; // Optional, use getSelectorTypeDetail()
    
    private SelectorTypeDetail getSelectorTypeDetail() {
        if(selectorTypeDetail == null) {
            selectorTypeDetail = selectorType.getLastDetail();
        }
        
        return selectorTypeDetail;
    }

    @GraphQLField
    @GraphQLDescription("selector kind")
    public SelectorKindObject getSelectorKind(final DataFetchingEnvironment env) {
        return SelectorSecurityUtils.getHasSelectorKindAccess(env) ? new SelectorKindObject(getSelectorTypeDetail().getSelectorKind()) : null;
    }

    @GraphQLField
    @GraphQLDescription("selector type name")
    @GraphQLNonNull
    public String getSelectorTypeName() {
        return getSelectorTypeDetail().getSelectorTypeName();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getSelectorTypeDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getSelectorTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var selectorControl = Session.getModelController(SelectorControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return selectorControl.getBestSelectorTypeDescription(selectorType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("selectors")
    public Collection<SelectorObject> getSelectors(final DataFetchingEnvironment env) {
        Collection<SelectorObject> selectorObjects = null;

        if(SelectorSecurityUtils.getHasSelectorsAccess(env)) {
            var selectorControl = Session.getModelController(SelectorControl.class);
            var selectors = selectorControl.getSelectorsBySelectorType(selectorType);

            selectorObjects = new ArrayList<>(selectors.size());

            selectors.stream()
                    .map(SelectorObject::new)
                    .forEachOrdered(selectorObjects::add);
        }

        return selectorObjects;
    }

}

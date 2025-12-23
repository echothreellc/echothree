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
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.selector.server.entity.SelectorKindDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.Collection;

@GraphQLDescription("selector kind object")
@GraphQLName("SelectorKind")
public class SelectorKindObject
        extends BaseEntityInstanceObject {
    
    private final SelectorKind selectorKind; // Always Present
    
    public SelectorKindObject(SelectorKind selectorKind) {
        super(selectorKind.getPrimaryKey());
        
        this.selectorKind = selectorKind;
    }

    private SelectorKindDetail selectorKindDetail; // Optional, use getSelectorKindDetail()
    
    private SelectorKindDetail getSelectorKindDetail() {
        if(selectorKindDetail == null) {
            selectorKindDetail = selectorKind.getLastDetail();
        }
        
        return selectorKindDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("selector kind name")
    @GraphQLNonNull
    public String getSelectorKindName() {
        return getSelectorKindDetail().getSelectorKindName();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getSelectorKindDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getSelectorKindDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var selectorControl = Session.getModelController(SelectorControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return selectorControl.getBestSelectorKindDescription(selectorKind, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("selector types")
    public Collection<SelectorTypeObject> getSelectorTypes(final DataFetchingEnvironment env) {
        Collection<SelectorTypeObject> selectorTypeObjects = null;

        if(SelectorSecurityUtils.getHasSelectorTypesAccess(env)) {
            var selectorControl = Session.getModelController(SelectorControl.class);
            var selectorTypes = selectorControl.getSelectorTypes(selectorKind);

            selectorTypeObjects = new ArrayList<>(selectorTypes.size());

            selectorTypes.stream()
                    .map(SelectorTypeObject::new)
                    .forEachOrdered(selectorTypeObjects::add);
        }

        return selectorTypeObjects;
    }

}

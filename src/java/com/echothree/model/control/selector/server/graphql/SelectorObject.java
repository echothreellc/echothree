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
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.entity.SelectorDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("selector object")
@GraphQLName("Selector")
public class SelectorObject
        extends BaseEntityInstanceObject {
    
    private final Selector selector; // Always Present
    
    public SelectorObject(Selector selector) {
        super(selector.getPrimaryKey());
        
        this.selector = selector;
    }

    private SelectorDetail selectorDetail; // Optional, use getSelectorDetail()
    
    private SelectorDetail getSelectorDetail() {
        if(selectorDetail == null) {
            selectorDetail = selector.getLastDetail();
        }
        
        return selectorDetail;
    }

    @GraphQLField
    @GraphQLDescription("selector kind")
    public SelectorTypeObject getSelectorType(final DataFetchingEnvironment env) {
        return SelectorSecurityUtils.getHasSelectorTypeAccess(env) ? new SelectorTypeObject(getSelectorDetail().getSelectorType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("selector type name")
    @GraphQLNonNull
    public String getSelectorName() {
        return getSelectorDetail().getSelectorName();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getSelectorDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getSelectorDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var selectorControl = Session.getModelController(SelectorControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return selectorControl.getBestSelectorDescription(selector, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}

// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.model.control.term.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.term.server.entity.TermDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("term object")
@GraphQLName("Term")
public class TermObject
        extends BaseEntityInstanceObject {
    
    private final Term term; // Always Present
    
    public TermObject(Term term) {
        super(term.getPrimaryKey());
        
        this.term = term;
    }

    private TermDetail termDetail; // Optional, use getTermDetail()

    private TermDetail getTermDetail() {
        if(termDetail == null) {
            termDetail = term.getLastDetail();
        }

        return termDetail;
    }

    @GraphQLField
    @GraphQLDescription("term name")
    @GraphQLNonNull
    public String getTermName() {
        return getTermDetail().getTermName();
    }

    @GraphQLField
    @GraphQLDescription("term type")
    public TermTypeObject getTermType(final DataFetchingEnvironment env) {
        return TermSecurityUtils.getInstance().getHasTermTypeAccess(env) ? new TermTypeObject(getTermDetail().getTermType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getTermDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getTermDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var termControl = Session.getModelController(TermControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return termControl.getBestTermDescription(term, userControl.getPreferredLanguageFromUserVisit(getUserVisit(env)));
    }

}

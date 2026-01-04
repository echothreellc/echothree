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

package com.echothree.model.control.offer.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.offer.server.control.UseControl;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.offer.server.entity.Use;
import com.echothree.model.data.offer.server.entity.UseDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("use object")
@GraphQLName("Use")
public class UseObject
        extends BaseEntityInstanceObject {
    
    private final Use use; // Always Present
    
    public UseObject(Use use) {
        super(use.getPrimaryKey());
        
        this.use = use;
    }

    private UseDetail useDetail; // Optional, use getUseDetail()
    
    private UseDetail getUseDetail() {
        if(useDetail == null) {
            useDetail = use.getLastDetail();
        }
        
        return useDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("use type name")
    @GraphQLNonNull
    public String getUseName() {
        return getUseDetail().getUseName();
    }

    @GraphQLField
    @GraphQLDescription("use type")
    public UseTypeObject getUseType(final DataFetchingEnvironment env) {
        return OfferSecurityUtils.getHasUseTypeAccess(env) ? new UseTypeObject(getUseDetail().getUseType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getUseDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getUseDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var useControl = Session.getModelController(UseControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return useControl.getBestUseDescription(use, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}

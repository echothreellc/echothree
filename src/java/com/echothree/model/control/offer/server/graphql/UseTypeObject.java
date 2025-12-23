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
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.offer.server.control.UseTypeControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.offer.server.entity.UseType;
import com.echothree.model.data.offer.server.entity.UseTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("use type object")
@GraphQLName("UseType")
public class UseTypeObject
        extends BaseEntityInstanceObject {
    
    private final UseType useType; // Always Present
    
    public UseTypeObject(UseType useType) {
        super(useType.getPrimaryKey());
        
        this.useType = useType;
    }

    private UseTypeDetail useTypeDetail; // Optional, use getUseTypeDetail()
    
    private UseTypeDetail getUseTypeDetail() {
        if(useTypeDetail == null) {
            useTypeDetail = useType.getLastDetail();
        }
        
        return useTypeDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("use type name")
    @GraphQLNonNull
    public String getUseTypeName() {
        return getUseTypeDetail().getUseTypeName();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getUseTypeDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getUseTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var useTypeControl = Session.getModelController(UseTypeControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return useTypeControl.getBestUseTypeDescription(useType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}

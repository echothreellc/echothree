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
import com.echothree.model.control.offer.server.control.UseNameElementControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.offer.server.entity.UseNameElement;
import com.echothree.model.data.offer.server.entity.UseNameElementDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("offer name element object")
@GraphQLName("UseNameElement")
public class UseNameElementObject
        extends BaseEntityInstanceObject {
    
    private final UseNameElement useNameElement; // Always Present
    
    public UseNameElementObject(UseNameElement useNameElement) {
        super(useNameElement.getPrimaryKey());
        
        this.useNameElement = useNameElement;
    }

    private UseNameElementDetail useNameElementDetail; // Optional, use getUseNameElementDetail()
    
    private UseNameElementDetail getUseNameElementDetail() {
        if(useNameElementDetail == null) {
            useNameElementDetail = useNameElement.getLastDetail();
        }
        
        return useNameElementDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("use type name")
    @GraphQLNonNull
    public String getUseNameElementName() {
        return getUseNameElementDetail().getUseNameElementName();
    }
    
    @GraphQLField
    @GraphQLDescription("offset")
    @GraphQLNonNull
    public int getOffset() {
        return getUseNameElementDetail().getOffset();
    }

    @GraphQLField
    @GraphQLDescription("length")
    @GraphQLNonNull
    public int getLength() {
        return getUseNameElementDetail().getLength();
    }

    @GraphQLField
    @GraphQLDescription("validation pattern")
    public String getValidationPattern() {
        return getUseNameElementDetail().getValidationPattern();
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var useNameElementControl = Session.getModelController(UseNameElementControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return useNameElementControl.getBestUseNameElementDescription(useNameElement, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}

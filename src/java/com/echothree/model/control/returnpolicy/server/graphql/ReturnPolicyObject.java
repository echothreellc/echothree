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

package com.echothree.model.control.returnpolicy.server.graphql;

import com.echothree.model.control.core.server.graphql.CoreSecurityUtils;
import com.echothree.model.control.core.server.graphql.MimeTypeObject;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicyDetail;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicyTranslation;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("return policy object")
@GraphQLName("ReturnPolicy")
public class ReturnPolicyObject
        extends BaseEntityInstanceObject {
    
    private final ReturnPolicy returnPolicy; // Always Present
    
    public ReturnPolicyObject(ReturnPolicy returnPolicy) {
        super(returnPolicy.getPrimaryKey());
        
        this.returnPolicy = returnPolicy;
    }

    private ReturnPolicyDetail returnPolicyDetail; // Optional, use getReturnPolicyDetail()

    private ReturnPolicyDetail getReturnPolicyDetail() {
        if(returnPolicyDetail == null) {
            returnPolicyDetail = returnPolicy.getLastDetail();
        }

        return returnPolicyDetail;
    }

    private ReturnPolicyTranslation returnPolicyTranslation; // Optional, use getReturnPolicyTranslation()

    private ReturnPolicyTranslation getReturnPolicyTranslation(final DataFetchingEnvironment env) {
        if(returnPolicyTranslation == null) {
            var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
            var userControl = Session.getModelController(UserControl.class);

            returnPolicyTranslation = returnPolicyControl.getBestReturnPolicyTranslation(returnPolicy, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
        }

        return returnPolicyTranslation;
    }

    @GraphQLField
    @GraphQLDescription("return policy name")
    @GraphQLNonNull
    public String getReturnPolicyName() {
        return getReturnPolicyDetail().getReturnPolicyName();
    }

    @GraphQLField
    @GraphQLDescription("return kind")
    public ReturnKindObject getReturnKind(final DataFetchingEnvironment env) {
        return ReturnPolicySecurityUtils.getHasReturnKindAccess(env) ? new ReturnKindObject(getReturnPolicyDetail().getReturnKind()) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getReturnPolicyDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getReturnPolicyDetail().getSortOrder();
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var returnPolicyTranslation = getReturnPolicyTranslation(env);

        return returnPolicyTranslation == null ? null : returnPolicyTranslation.getDescription();
    }

    @GraphQLField
    @GraphQLDescription("policy mime type")
    public MimeTypeObject getPolicyMimeType(final DataFetchingEnvironment env) {
        var returnPolicyTranslation = getReturnPolicyTranslation(env);
        var policyMimeType = returnPolicyTranslation == null ? null : returnPolicyTranslation.getPolicyMimeType();

        return policyMimeType == null ? null : (CoreSecurityUtils.getHasMimeTypeAccess(env) ? new MimeTypeObject(policyMimeType) : null);
    }

    @GraphQLField
    @GraphQLDescription("policy")
    @GraphQLNonNull
    public String getPolicy(final DataFetchingEnvironment env) {
        var returnPolicyTranslation = getReturnPolicyTranslation(env);

        return returnPolicyTranslation == null ? null : returnPolicyTranslation.getPolicy();
    }

}

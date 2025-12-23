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

package com.echothree.model.control.cancellationpolicy.server.graphql;

import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.core.server.graphql.CoreSecurityUtils;
import com.echothree.model.control.core.server.graphql.MimeTypeObject;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicyDetail;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicyTranslation;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("cancellation policy object")
@GraphQLName("CancellationPolicy")
public class CancellationPolicyObject
        extends BaseEntityInstanceObject {
    
    private final CancellationPolicy cancellationPolicy; // Always Present
    
    public CancellationPolicyObject(CancellationPolicy cancellationPolicy) {
        super(cancellationPolicy.getPrimaryKey());
        
        this.cancellationPolicy = cancellationPolicy;
    }

    private CancellationPolicyDetail cancellationPolicyDetail; // Optional, use getCancellationPolicyDetail()

    private CancellationPolicyDetail getCancellationPolicyDetail() {
        if(cancellationPolicyDetail == null) {
            cancellationPolicyDetail = cancellationPolicy.getLastDetail();
        }

        return cancellationPolicyDetail;
    }

    private CancellationPolicyTranslation cancellationPolicyTranslation; // Optional, use getCancellationPolicyTranslation()

    private CancellationPolicyTranslation getCancellationPolicyTranslation(final DataFetchingEnvironment env) {
        if(cancellationPolicyTranslation == null) {
            var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
            var userControl = Session.getModelController(UserControl.class);

            cancellationPolicyTranslation = cancellationPolicyControl.getBestCancellationPolicyTranslation(cancellationPolicy, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
        }

        return cancellationPolicyTranslation;
    }

    @GraphQLField
    @GraphQLDescription("cancellation policy name")
    @GraphQLNonNull
    public String getCancellationPolicyName() {
        return getCancellationPolicyDetail().getCancellationPolicyName();
    }

    @GraphQLField
    @GraphQLDescription("cancellation kind")
    public CancellationKindObject getCancellationKind(final DataFetchingEnvironment env) {
        return CancellationPolicySecurityUtils.getHasCancellationKindAccess(env) ? new CancellationKindObject(getCancellationPolicyDetail().getCancellationKind()) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getCancellationPolicyDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getCancellationPolicyDetail().getSortOrder();
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var cancellationPolicyTranslation = getCancellationPolicyTranslation(env);

        return cancellationPolicyTranslation == null ? null : cancellationPolicyTranslation.getDescription();
    }

    @GraphQLField
    @GraphQLDescription("policy mime type")
    public MimeTypeObject getPolicyMimeType(final DataFetchingEnvironment env) {
        var cancellationPolicyTranslation = getCancellationPolicyTranslation(env);
        var policyMimeType = cancellationPolicyTranslation == null ? null : cancellationPolicyTranslation.getPolicyMimeType();

        return policyMimeType == null ? null : (CoreSecurityUtils.getHasMimeTypeAccess(env) ? new MimeTypeObject(policyMimeType) : null);
    }

    @GraphQLField
    @GraphQLDescription("policy")
    @GraphQLNonNull
    public String getPolicy(final DataFetchingEnvironment env) {
        var cancellationPolicyTranslation = getCancellationPolicyTranslation(env);

        return cancellationPolicyTranslation == null ? null : cancellationPolicyTranslation.getPolicy();
    }

}

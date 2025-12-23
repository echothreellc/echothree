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

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.sequence.server.graphql.SequenceSecurityUtils;
import com.echothree.model.control.sequence.server.graphql.SequenceTypeObject;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.returnpolicy.common.ReturnPolicyConstants;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKindDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("return kind object")
@GraphQLName("ReturnKind")
public class ReturnKindObject
        extends BaseEntityInstanceObject {
    
    private final ReturnKind returnKind; // Always Present
    
    public ReturnKindObject(ReturnKind returnKind) {
        super(returnKind.getPrimaryKey());
        
        this.returnKind = returnKind;
    }

    private ReturnKindDetail returnKindDetail; // Optional, use getReturnKindDetail()

    private ReturnKindDetail getReturnKindDetail() {
        if(returnKindDetail == null) {
            returnKindDetail = returnKind.getLastDetail();
        }

        return returnKindDetail;
    }

    @GraphQLField
    @GraphQLDescription("return kind name")
    @GraphQLNonNull
    public String getReturnKindName() {
        return getReturnKindDetail().getReturnKindName();
    }

    @GraphQLField
    @GraphQLDescription("return sequence type")
    public SequenceTypeObject getReturnSequenceType(final DataFetchingEnvironment env) {
        return SequenceSecurityUtils.getHasSequenceTypeAccess(env) ? new SequenceTypeObject(getReturnKindDetail().getReturnSequenceType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getReturnKindDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getReturnKindDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return returnPolicyControl.getBestReturnKindDescription(returnKind, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("return policies")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<ReturnPolicyObject> getReturnPolicies(final DataFetchingEnvironment env) {
        if(ReturnPolicySecurityUtils.getHasReturnPoliciesAccess(env)) {
            var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
            var totalCount = returnPolicyControl.countReturnPoliciesByReturnKind(returnKind);
    
            try(var objectLimiter = new ObjectLimiter(env, ReturnPolicyConstants.COMPONENT_VENDOR_NAME, ReturnPolicyConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = returnPolicyControl.getReturnPolicies(returnKind);
                var items = entities.stream().map(ReturnPolicyObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));
    
                return new CountedObjects<>(objectLimiter, items);
            }
        } else {
            return Connections.emptyConnection();
        }
    }
    
}

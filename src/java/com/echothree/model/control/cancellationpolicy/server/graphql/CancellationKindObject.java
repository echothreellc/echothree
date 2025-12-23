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
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.sequence.server.graphql.SequenceSecurityUtils;
import com.echothree.model.control.sequence.server.graphql.SequenceTypeObject;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.cancellationpolicy.common.CancellationPolicyConstants;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKindDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("cancellation kind object")
@GraphQLName("CancellationKind")
public class CancellationKindObject
        extends BaseEntityInstanceObject {
    
    private final CancellationKind cancellationKind; // Always Present
    
    public CancellationKindObject(CancellationKind cancellationKind) {
        super(cancellationKind.getPrimaryKey());
        
        this.cancellationKind = cancellationKind;
    }

    private CancellationKindDetail cancellationKindDetail; // Optional, use getCancellationKindDetail()

    private CancellationKindDetail getCancellationKindDetail() {
        if(cancellationKindDetail == null) {
            cancellationKindDetail = cancellationKind.getLastDetail();
        }

        return cancellationKindDetail;
    }

    @GraphQLField
    @GraphQLDescription("cancellation kind name")
    @GraphQLNonNull
    public String getCancellationKindName() {
        return getCancellationKindDetail().getCancellationKindName();
    }

    @GraphQLField
    @GraphQLDescription("cancellation sequence type")
    public SequenceTypeObject getCancellationSequenceType(final DataFetchingEnvironment env) {
        return SequenceSecurityUtils.getHasSequenceTypeAccess(env) ? new SequenceTypeObject(getCancellationKindDetail().getCancellationSequenceType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getCancellationKindDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getCancellationKindDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return cancellationPolicyControl.getBestCancellationKindDescription(cancellationKind, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("cancellation policies")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<CancellationPolicyObject> getCancellationPolicies(final DataFetchingEnvironment env) {
        if(CancellationPolicySecurityUtils.getHasCancellationPoliciesAccess(env)) {
            var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
            var totalCount = cancellationPolicyControl.countCancellationPoliciesByCancellationKind(cancellationKind);

            try(var objectLimiter = new ObjectLimiter(env, CancellationPolicyConstants.COMPONENT_VENDOR_NAME, CancellationPolicyConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = cancellationPolicyControl.getCancellationPolicies(cancellationKind);
                var items = entities.stream().map(CancellationPolicyObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, items);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}

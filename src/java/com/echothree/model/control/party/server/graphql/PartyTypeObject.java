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

package com.echothree.model.control.party.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.sequence.server.graphql.SequenceSecurityUtils;
import com.echothree.model.control.sequence.server.graphql.SequenceTypeObject;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.party.common.PartyAliasTypeConstants;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("party type object")
@GraphQLName("PartyType")
public class PartyTypeObject
        extends BaseEntityInstanceObject {

    private final PartyType partyType; // Always Present
    
    public PartyTypeObject(PartyType partyType) {
        super(partyType.getPrimaryKey());

        this.partyType = partyType;
    }
    
    @GraphQLField
    @GraphQLDescription("party type name")
    @GraphQLNonNull
    public String getPartyTypeName() {
        return partyType.getPartyTypeName();
    }

    @GraphQLField
    @GraphQLDescription("parent party type")
    public PartyTypeObject getParentPartyType() {
        var parentPartyType = partyType.getParentPartyType();

        return parentPartyType == null ? null : new PartyTypeObject(parentPartyType);
    }
    
    @GraphQLField
    @GraphQLDescription("billing account sequence type")
    public SequenceTypeObject getBillingAccountSequenceType(final DataFetchingEnvironment env) {
        return SequenceSecurityUtils.getHasSequenceTypeAccess(env) ? new SequenceTypeObject(partyType.getBillingAccountSequenceType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("allow user logins")
    @GraphQLNonNull
    public Boolean getAllowUserLogins() {
        return partyType.getAllowUserLogins();
    }

    @GraphQLField
    @GraphQLDescription("allow party aliases")
    @GraphQLNonNull
    public Boolean getAllowPartyAliases() {
        return partyType.getAllowPartyAliases();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public Boolean getIsDefault() {
        return partyType.getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public Integer getSortOrder() {
        return partyType.getSortOrder();
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var partyControl = Session.getModelController(PartyControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return partyControl.getBestPartyTypeDescription(partyType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("party alias types")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<PartyAliasTypeObject> getPartyAliasTypes(final DataFetchingEnvironment env) {
        if(PartySecurityUtils.getHasPartyAliasTypesAccess(env)) {
            var partyControl = Session.getModelController(PartyControl.class);
            var totalCount = partyControl.countPartyAliasTypesByPartyType(partyType);

            try(var objectLimiter = new ObjectLimiter(env, PartyAliasTypeConstants.COMPONENT_VENDOR_NAME, PartyAliasTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = partyControl.getPartyAliasTypes(partyType);
                var partyAliasTypes = entities.stream().map(PartyAliasTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, partyAliasTypes);
            }
        } else {
            return Connections.emptyConnection();
        }
    }
    
}

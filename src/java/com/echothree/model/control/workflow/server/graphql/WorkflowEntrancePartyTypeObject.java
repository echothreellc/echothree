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

package com.echothree.model.control.workflow.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.party.server.graphql.PartySecurityUtils;
import com.echothree.model.control.party.server.graphql.PartyTypeObject;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.workflow.common.WorkflowEntranceSecurityRoleConstants;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrancePartyType;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("workflow entrance party type object")
@GraphQLName("WorkflowEntrancePartyType")
public class WorkflowEntrancePartyTypeObject
        extends BaseObject {
    
    private final WorkflowEntrancePartyType workflowEntrancePartyType; // Always Present
    
    public WorkflowEntrancePartyTypeObject(WorkflowEntrancePartyType workflowEntrancePartyType) {
        this.workflowEntrancePartyType = workflowEntrancePartyType;
    }

    @GraphQLField
    @GraphQLDescription("workflow entrance")
    public WorkflowEntranceObject getWorkflowEntrance(final DataFetchingEnvironment env) {
        return WorkflowSecurityUtils.getHasWorkflowEntranceAccess(env) ? new WorkflowEntranceObject(workflowEntrancePartyType.getWorkflowEntrance()) : null;
    }

    @GraphQLField
    @GraphQLDescription("party type")
    @GraphQLNonNull
    public PartyTypeObject getPartyType(final DataFetchingEnvironment env) {
        return PartySecurityUtils.getHasPartyTypeAccess(env) ? new PartyTypeObject(workflowEntrancePartyType.getPartyType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("workflow entrance security roles")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<WorkflowEntranceSecurityRoleObject> getWorkflowEntranceSecurityRoles(final DataFetchingEnvironment env) {
        if(WorkflowSecurityUtils.getHasWorkflowEntranceSecurityRolesAccess(env)) {
            var workflowControl = Session.getModelController(WorkflowControl.class);
            var totalCount = workflowControl.countWorkflowEntranceSecurityRolesByWorkflowEntrancePartyType(workflowEntrancePartyType);

            try(var objectLimiter = new ObjectLimiter(env, WorkflowEntranceSecurityRoleConstants.COMPONENT_VENDOR_NAME, WorkflowEntranceSecurityRoleConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = workflowControl.getWorkflowEntranceSecurityRolesByWorkflowEntrancePartyType(workflowEntrancePartyType);
                var wishlistPriorities = entities.stream().map(WorkflowEntranceSecurityRoleObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, wishlistPriorities);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}

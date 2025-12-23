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
import com.echothree.model.data.workflow.common.WorkflowDestinationSecurityRoleConstants;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationPartyType;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("workflow destination party type object")
@GraphQLName("WorkflowDestinationPartyType")
public class WorkflowDestinationPartyTypeObject
        extends BaseObject {
    
    private final WorkflowDestinationPartyType workflowDestinationPartyType; // Always Present
    
    public WorkflowDestinationPartyTypeObject(WorkflowDestinationPartyType workflowDestinationPartyType) {
        this.workflowDestinationPartyType = workflowDestinationPartyType;
    }

    @GraphQLField
    @GraphQLDescription("workflow destination")
    public WorkflowDestinationObject getWorkflowDestination(final DataFetchingEnvironment env) {
        return WorkflowSecurityUtils.getHasWorkflowDestinationAccess(env) ? new WorkflowDestinationObject(workflowDestinationPartyType.getWorkflowDestination()) : null;
    }

    @GraphQLField
    @GraphQLDescription("party type")
    @GraphQLNonNull
    public PartyTypeObject getPartyType(final DataFetchingEnvironment env) {
        return PartySecurityUtils.getHasPartyTypeAccess(env) ? new PartyTypeObject(workflowDestinationPartyType.getPartyType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("workflow destination security roles")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<WorkflowDestinationSecurityRoleObject> getWorkflowDestinationSecurityRoles(final DataFetchingEnvironment env) {
        if(WorkflowSecurityUtils.getHasWorkflowDestinationSecurityRolesAccess(env)) {
            var workflowControl = Session.getModelController(WorkflowControl.class);
            var totalCount = workflowControl.countWorkflowDestinationSecurityRolesByWorkflowDestinationPartyType(workflowDestinationPartyType);

            try(var objectLimiter = new ObjectLimiter(env, WorkflowDestinationSecurityRoleConstants.COMPONENT_VENDOR_NAME, WorkflowDestinationSecurityRoleConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = workflowControl.getWorkflowDestinationSecurityRolesByWorkflowDestinationPartyType(workflowDestinationPartyType);
                var wishlistPriorities = entities.stream().map(WorkflowDestinationSecurityRoleObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, wishlistPriorities);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}

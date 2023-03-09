// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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
import com.echothree.model.control.party.server.graphql.PartyTypeObject;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationPartyType;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("workflow destination party type object")
@GraphQLName("WorkflowDestinationPartyType")
public class WorkflowDestinationPartyTypeObject
        extends BaseObject {
    
    private final WorkflowDestinationPartyType workflowDestinationPartyType; // Always Present
    
    public WorkflowDestinationPartyTypeObject(WorkflowDestinationPartyType workflowDestinationPartyType) {
        super(workflowDestinationPartyType.getPrimaryKey());
        
        this.workflowDestinationPartyType = workflowDestinationPartyType;
    }

    @GraphQLField
    @GraphQLDescription("workflow destination")
    public WorkflowDestinationObject getWorkflowDestination(final DataFetchingEnvironment env) {
        return WorkflowSecurityUtils.getInstance().getHasWorkflowDestinationAccess(env) ? new WorkflowDestinationObject(workflowDestinationPartyType.getWorkflowDestination()) : null;
    }

    @GraphQLField
    @GraphQLDescription("party type")
    @GraphQLNonNull
    public PartyTypeObject getPartyType() {
        return new PartyTypeObject(workflowDestinationPartyType.getPartyType());
    }

//    @GraphQLField
//    @GraphQLDescription("workflow destination security roles")
//    @GraphQLNonNull
//    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
//    public CountingPaginatedData<WorkflowDestinationSecurityRoleObject> getWorkflowDestinationSecurityRoles(final DataFetchingEnvironment env) {
//        if(WorkflowSecurityUtils.getInstance().getHasWorkflowDestinationSecurityRolesAccess(env)) {
//            var workflowControl = Session.getModelController(WorkflowControl.class);
//            var totalCount = workflowControl.countWorkflowDestinationSecurityRolesByWorkflowDestinationPartyType(workflowDestinationPartyType);
//
//            try(var objectLimiter = new ObjectLimiter(env, WorkflowDestinationSecurityRoleConstants.COMPONENT_VENDOR_NAME, WorkflowDestinationSecurityRoleConstants.ENTITY_TYPE_NAME, totalCount)) {
//                var entities = workflowControl.getWorkflowDestinationSecurityRolesByWorkflowDestinationPartyType(workflowDestinationPartyType);
//                var wishlistTypePriorities = entities.stream().map(WorkflowDestinationSecurityRoleObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));
//
//                return new CountedObjects<>(objectLimiter, wishlistTypePriorities);
//            }
//        } else {
//            return Connections.emptyConnection();
//        }
//    }

}

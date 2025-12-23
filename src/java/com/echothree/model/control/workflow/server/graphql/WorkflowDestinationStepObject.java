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
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationStep;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("workflow destination step object")
@GraphQLName("WorkflowDestinationStep")
public class WorkflowDestinationStepObject
        extends BaseObject {
    
    private final WorkflowDestinationStep workflowDestinationStep; // Always Present
    
    public WorkflowDestinationStepObject(WorkflowDestinationStep workflowDestinationStep) {
        this.workflowDestinationStep = workflowDestinationStep;
    }

    @GraphQLField
    @GraphQLDescription("workflowDestination")
    public WorkflowDestinationObject getWorkflowDestination(final DataFetchingEnvironment env) {
        return WorkflowSecurityUtils.getHasWorkflowDestinationAccess(env) ? new WorkflowDestinationObject(workflowDestinationStep.getWorkflowDestination()) : null;
    }

    @GraphQLField
    @GraphQLDescription("workflowStep")
    public WorkflowStepObject getWorkflowStep(final DataFetchingEnvironment env) {
        return WorkflowSecurityUtils.getHasWorkflowStepAccess(env) ? new WorkflowStepObject(workflowDestinationStep.getWorkflowStep()) : null;
    }

}

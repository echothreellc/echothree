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

import com.echothree.model.control.core.server.graphql.CoreSecurityUtils;
import com.echothree.model.control.core.server.graphql.EntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.TimeObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("workflow entity status object")
@GraphQLName("WorkflowEntityStatus")
public class WorkflowEntityStatusObject
        implements BaseGraphQl {

    private final WorkflowEntityStatus workflowEntityStatus; // Always Present

    public WorkflowEntityStatusObject(WorkflowEntityStatus workflowEntityStatus) {
        this.workflowEntityStatus = workflowEntityStatus;
    }

    @GraphQLField
    @GraphQLDescription("entity instance")
    public EntityInstanceObject getEntityInstance(final DataFetchingEnvironment env) {
        if(CoreSecurityUtils.getHasEntityInstanceAccess(env)) {
            return new EntityInstanceObject(workflowEntityStatus.getEntityInstance());
        } else {
            return null;
        }
    }

    @GraphQLField
    @GraphQLDescription("workflow step")
    public WorkflowStepObject getWorkflowStep(final DataFetchingEnvironment env) {
        if(WorkflowSecurityUtils.getHasWorkflowStepAccess(env)) {
            return new WorkflowStepObject(workflowEntityStatus.getWorkflowStep());
        } else {
            return null;
        }
    }

    // TODO: WorkEffortScope

    @GraphQLField
    @GraphQLDescription("from time")
    @GraphQLNonNull
    public TimeObject getFromTime(final DataFetchingEnvironment env) {
        return new TimeObject(workflowEntityStatus.getFromTime());
    }

    @GraphQLField
    @GraphQLDescription("thru time")
    @GraphQLNonNull
    public TimeObject getThruTime(final DataFetchingEnvironment env) {
        return new TimeObject(workflowEntityStatus.getThruTime());
    }

}

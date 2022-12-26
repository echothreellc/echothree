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

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.model.data.workflow.server.entity.WorkflowStepDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("workflow step object")
@GraphQLName("WorkflowStep")
public class WorkflowStepObject
        extends BaseEntityInstanceObject {
    
    private final WorkflowStep workflowStep; // Always Present
    
    public WorkflowStepObject(WorkflowStep workflowStep) {
        super(workflowStep.getPrimaryKey());
        
        this.workflowStep = workflowStep;
    }

    private WorkflowStepDetail workflowStepDetail; // Optional, use getWorkflowStepDetail()
    
    private WorkflowStepDetail getWorkflowStepDetail() {
        if(workflowStepDetail == null) {
            workflowStepDetail = workflowStep.getLastDetail();
        }
        
        return workflowStepDetail;
    }

    @GraphQLField
    @GraphQLDescription("workflow")
    public WorkflowObject getWorkflow(final DataFetchingEnvironment env) {
        return WorkflowSecurityUtils.getInstance().getHasWorkflowAccess(env) ? new WorkflowObject(getWorkflowStepDetail().getWorkflow()) : null;
    }

    @GraphQLField
    @GraphQLDescription("workflow step name")
    @GraphQLNonNull
    public String getWorkflowStepName() {
        return getWorkflowStepDetail().getWorkflowStepName();
    }

    @GraphQLField
    @GraphQLDescription("workflow step type")
    public WorkflowStepTypeObject getWorkflowStepType(final DataFetchingEnvironment env) {
        return WorkflowSecurityUtils.getInstance().getHasWorkflowStepTypeAccess(env) ? new WorkflowStepTypeObject(getWorkflowStepDetail().getWorkflowStepType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getWorkflowStepDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getWorkflowStepDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return workflowControl.getBestWorkflowStepDescription(workflowStep, userControl.getPreferredLanguageFromUserVisit(getUserVisit(env)));
    }

}

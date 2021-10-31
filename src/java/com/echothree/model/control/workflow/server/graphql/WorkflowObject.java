// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.security.server.graphql.SecurityRoleGroupObject;
import com.echothree.model.control.security.server.graphql.SecuritySecurityUtils;
import com.echothree.model.control.selector.server.graphql.SelectorSecurityUtils;
import com.echothree.model.control.selector.server.graphql.SelectorTypeObject;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.Collection;

@GraphQLDescription("workflow object")
@GraphQLName("Workflow")
public class WorkflowObject
        extends BaseEntityInstanceObject {
    
    private final Workflow workflow; // Always Present
    
    public WorkflowObject(Workflow workflow) {
        super(workflow.getPrimaryKey());
        
        this.workflow = workflow;
    }

    private WorkflowDetail workflowDetail; // Optional, use getWorkflowDetail()
    
    private WorkflowDetail getWorkflowDetail() {
        if(workflowDetail == null) {
            workflowDetail = workflow.getLastDetail();
        }
        
        return workflowDetail;
    }

    @GraphQLField
    @GraphQLDescription("workflow name")
    @GraphQLNonNull
    public String getWorkflowName() {
        return getWorkflowDetail().getWorkflowName();
    }

    @GraphQLField
    @GraphQLDescription("workflow type")
    public WorkflowTypeObject getWorkflowType(final DataFetchingEnvironment env) {
        return WorkflowSecurityUtils.getInstance().getHasWorkflowTypeAccess(env) ? new WorkflowTypeObject(getWorkflowDetail().getWorkflowType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("selector type")
    public SelectorTypeObject getSelectorType(final DataFetchingEnvironment env) {
        if(SelectorSecurityUtils.getInstance().getHasSelectorTypeAccess(env)) {
            var selectorType = getWorkflowDetail().getSelectorType();

            return selectorType == null ? null : new SelectorTypeObject(selectorType);
        } else {
            return null;
        }
    }

    @GraphQLField
    @GraphQLDescription("security role group")
    public SecurityRoleGroupObject getSecurityRoleGroup(final DataFetchingEnvironment env) {
        if(SecuritySecurityUtils.getInstance().getHasSecurityRoleGroupAccess(env)) {
            var securityRoleGroup = getWorkflowDetail().getSecurityRoleGroup();

            return securityRoleGroup == null ? null : new SecurityRoleGroupObject(securityRoleGroup);
        } else {
            return null;
        }
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getWorkflowDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var userControl = Session.getModelController(UserControl.class);
        GraphQlContext context = env.getContext();
        
        return workflowControl.getBestWorkflowDescription(workflow, userControl.getPreferredLanguageFromUserVisit(context.getUserVisit()));
    }

    @GraphQLField
    @GraphQLDescription("workflow steps")
    public Collection<WorkflowStepObject> getWorkflowSteps(final DataFetchingEnvironment env) {
        Collection<WorkflowStepObject> workflowStepObjects = null;

        if(WorkflowSecurityUtils.getInstance().getHasWorkflowStepsAccess(env)) {
            var workflowControl = Session.getModelController(WorkflowControl.class);
            var workflowSteps = workflowControl.getWorkflowStepsByWorkflow(workflow);

            workflowStepObjects = new ArrayList<>(workflowSteps.size());

            workflowSteps.stream()
                    .map(WorkflowStepObject::new)
                    .forEachOrdered(workflowStepObjects::add);
        }

        return workflowStepObjects;
    }

}

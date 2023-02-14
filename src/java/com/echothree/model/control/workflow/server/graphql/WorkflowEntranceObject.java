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
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.model.data.workflow.server.entity.WorkflowEntranceDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("workflow entrance object")
@GraphQLName("WorkflowEntrance")
public class WorkflowEntranceObject
        extends BaseEntityInstanceObject {
    
    private final WorkflowEntrance workflowEntrance; // Always Present
    
    public WorkflowEntranceObject(WorkflowEntrance workflowEntrance) {
        super(workflowEntrance.getPrimaryKey());
        
        this.workflowEntrance = workflowEntrance;
    }

    private WorkflowEntranceDetail workflowEntranceDetail; // Optional, use getWorkflowEntranceDetail()
    
    private WorkflowEntranceDetail getWorkflowEntranceDetail() {
        if(workflowEntranceDetail == null) {
            workflowEntranceDetail = workflowEntrance.getLastDetail();
        }
        
        return workflowEntranceDetail;
    }

    @GraphQLField
    @GraphQLDescription("workflow")
    public WorkflowObject getWorkflow(final DataFetchingEnvironment env) {
        return WorkflowSecurityUtils.getInstance().getHasWorkflowAccess(env) ? new WorkflowObject(getWorkflowEntranceDetail().getWorkflow()) : null;
    }

    @GraphQLField
    @GraphQLDescription("workflow entrance name")
    @GraphQLNonNull
    public String getWorkflowEntranceName() {
        return getWorkflowEntranceDetail().getWorkflowEntranceName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getWorkflowEntranceDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getWorkflowEntranceDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return workflowControl.getBestWorkflowEntranceDescription(workflowEntrance, userControl.getPreferredLanguageFromUserVisit(getUserVisit(env)));
    }

}

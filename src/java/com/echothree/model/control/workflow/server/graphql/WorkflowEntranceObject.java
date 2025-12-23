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

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.workflow.common.WorkflowEntrancePartyTypeConstants;
import com.echothree.model.data.workflow.common.WorkflowEntranceSelectorConstants;
import com.echothree.model.data.workflow.common.WorkflowEntranceStepConstants;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.model.data.workflow.server.entity.WorkflowEntranceDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

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
        return WorkflowSecurityUtils.getHasWorkflowAccess(env) ? new WorkflowObject(getWorkflowEntranceDetail().getWorkflow()) : null;
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

        return workflowControl.getBestWorkflowEntranceDescription(workflowEntrance, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("workflow entrance steps")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<WorkflowEntranceStepObject> getWorkflowEntranceSteps(final DataFetchingEnvironment env) {
        if(WorkflowSecurityUtils.getHasWorkflowEntranceStepsAccess(env)) {
            var workflowControl = Session.getModelController(WorkflowControl.class);
            var totalCount = workflowControl.countWorkflowEntranceStepsByWorkflowEntrance(workflowEntrance);

            try(var objectLimiter = new ObjectLimiter(env, WorkflowEntranceStepConstants.COMPONENT_VENDOR_NAME, WorkflowEntranceStepConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = workflowControl.getWorkflowEntranceStepsByWorkflowEntrance(workflowEntrance);
                var wishlistPriorities = entities.stream().map(WorkflowEntranceStepObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, wishlistPriorities);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("workflow entrance party types")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<WorkflowEntrancePartyTypeObject> getWorkflowEntrancePartyTypes(final DataFetchingEnvironment env) {
        if(WorkflowSecurityUtils.getHasWorkflowEntrancePartyTypesAccess(env)) {
            var workflowControl = Session.getModelController(WorkflowControl.class);
            var totalCount = workflowControl.countWorkflowEntrancePartyTypesByWorkflowEntrance(workflowEntrance);

            try(var objectLimiter = new ObjectLimiter(env, WorkflowEntrancePartyTypeConstants.COMPONENT_VENDOR_NAME, WorkflowEntrancePartyTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = workflowControl.getWorkflowEntrancePartyTypesByWorkflowEntrance(workflowEntrance);
                var wishlistPriorities = entities.stream().map(WorkflowEntrancePartyTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, wishlistPriorities);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("workflow entrance selectors")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<WorkflowEntranceSelectorObject> getWorkflowEntranceSelectors(final DataFetchingEnvironment env) {
        if(WorkflowSecurityUtils.getHasWorkflowEntranceSelectorsAccess(env)) {
            var workflowControl = Session.getModelController(WorkflowControl.class);
            var totalCount = workflowControl.countWorkflowEntranceSelectorsByWorkflowEntrance(workflowEntrance);

            try(var objectLimiter = new ObjectLimiter(env, WorkflowEntranceSelectorConstants.COMPONENT_VENDOR_NAME, WorkflowEntranceSelectorConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = workflowControl.getWorkflowEntranceSelectorsByWorkflowEntrance(workflowEntrance);
                var wishlistPriorities = entities.stream().map(WorkflowEntranceSelectorObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, wishlistPriorities);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}

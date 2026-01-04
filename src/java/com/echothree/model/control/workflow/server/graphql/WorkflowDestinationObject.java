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
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.workflow.common.WorkflowDestinationPartyTypeConstants;
import com.echothree.model.data.workflow.common.WorkflowDestinationSelectorConstants;
import com.echothree.model.data.workflow.common.WorkflowDestinationStepConstants;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("workflow destination object")
@GraphQLName("WorkflowDestination")
public class WorkflowDestinationObject
        extends BaseEntityInstanceObject {
    
    private final WorkflowDestination workflowDestination; // Always Present
    
    public WorkflowDestinationObject(WorkflowDestination workflowDestination) {
        super(workflowDestination.getPrimaryKey());
        
        this.workflowDestination = workflowDestination;
    }

    private WorkflowDestinationDetail workflowDestinationDetail; // Optional, use getWorkflowDestinationDetail()
    
    private WorkflowDestinationDetail getWorkflowDestinationDetail() {
        if(workflowDestinationDetail == null) {
            workflowDestinationDetail = workflowDestination.getLastDetail();
        }
        
        return workflowDestinationDetail;
    }

    @GraphQLField
    @GraphQLDescription("workflow step")
    public WorkflowStepObject getWorkflowStep(final DataFetchingEnvironment env) {
        return WorkflowSecurityUtils.getHasWorkflowStepAccess(env) ? new WorkflowStepObject(getWorkflowDestinationDetail().getWorkflowStep()) : null;
    }

    @GraphQLField
    @GraphQLDescription("workflow destination name")
    @GraphQLNonNull
    public String getWorkflowDestinationName() {
        return getWorkflowDestinationDetail().getWorkflowDestinationName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getWorkflowDestinationDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getWorkflowDestinationDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return workflowControl.getBestWorkflowDestinationDescription(workflowDestination, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("workflow destination steps")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<WorkflowDestinationStepObject> getWorkflowDestinationSteps(final DataFetchingEnvironment env) {
        if(WorkflowSecurityUtils.getHasWorkflowDestinationStepsAccess(env)) {
            var workflowControl = Session.getModelController(WorkflowControl.class);
            var totalCount = workflowControl.countWorkflowDestinationStepsByWorkflowDestination(workflowDestination);

            try(var objectLimiter = new ObjectLimiter(env, WorkflowDestinationStepConstants.COMPONENT_VENDOR_NAME, WorkflowDestinationStepConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = workflowControl.getWorkflowDestinationStepsByWorkflowDestination(workflowDestination);
                var wishlistPriorities = entities.stream().map(WorkflowDestinationStepObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, wishlistPriorities);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("workflow destination party types")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<WorkflowDestinationPartyTypeObject> getWorkflowDestinationPartyTypes(final DataFetchingEnvironment env) {
        if(WorkflowSecurityUtils.getHasWorkflowDestinationPartyTypesAccess(env)) {
            var workflowControl = Session.getModelController(WorkflowControl.class);
            var totalCount = workflowControl.countWorkflowDestinationPartyTypesByWorkflowDestination(workflowDestination);

            try(var objectLimiter = new ObjectLimiter(env, WorkflowDestinationPartyTypeConstants.COMPONENT_VENDOR_NAME, WorkflowDestinationPartyTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = workflowControl.getWorkflowDestinationPartyTypesByWorkflowDestination(workflowDestination);
                var wishlistPriorities = entities.stream().map(WorkflowDestinationPartyTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, wishlistPriorities);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("workflow destination selectors")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<WorkflowDestinationSelectorObject> getWorkflowDestinationSelectors(final DataFetchingEnvironment env) {
        if(WorkflowSecurityUtils.getHasWorkflowDestinationSelectorsAccess(env)) {
            var workflowControl = Session.getModelController(WorkflowControl.class);
            var totalCount = workflowControl.countWorkflowDestinationSelectorsByWorkflowDestination(workflowDestination);

            try(var objectLimiter = new ObjectLimiter(env, WorkflowDestinationSelectorConstants.COMPONENT_VENDOR_NAME, WorkflowDestinationSelectorConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = workflowControl.getWorkflowDestinationSelectorsByWorkflowDestination(workflowDestination);
                var wishlistPriorities = entities.stream().map(WorkflowDestinationSelectorObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, wishlistPriorities);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}

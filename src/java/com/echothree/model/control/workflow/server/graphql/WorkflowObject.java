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
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.security.server.graphql.SecurityRoleGroupObject;
import com.echothree.model.control.security.server.graphql.SecuritySecurityUtils;
import com.echothree.model.control.selector.server.graphql.SelectorSecurityUtils;
import com.echothree.model.control.selector.server.graphql.SelectorTypeObject;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.wishlist.server.graphql.WishlistSecurityUtils;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.workflow.common.WorkflowEntityTypeConstants;
import com.echothree.model.data.workflow.common.WorkflowEntranceConstants;
import com.echothree.model.data.workflow.common.WorkflowStepConstants;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

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

        return workflowControl.getBestWorkflowDescription(workflow, userControl.getPreferredLanguageFromUserVisit(getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("workflow entrances")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<WorkflowEntranceObject> getWorkflowEntrances(final DataFetchingEnvironment env) {
        if(WishlistSecurityUtils.getInstance().getHasWishlistTypePrioritiesAccess(env)) {
            var workflowControl = Session.getModelController(WorkflowControl.class);
            var totalCount = workflowControl.countWorkflowEntrancesByWorkflow(workflow);

            try(var objectLimiter = new ObjectLimiter(env, WorkflowEntranceConstants.COMPONENT_VENDOR_NAME, WorkflowEntranceConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = workflowControl.getWorkflowEntrancesByWorkflow(workflow);
                var wishlistTypePriorities = entities.stream().map(WorkflowEntranceObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, wishlistTypePriorities);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("workflow steps")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<WorkflowStepObject> getWorkflowSteps(final DataFetchingEnvironment env) {
        if(WishlistSecurityUtils.getInstance().getHasWishlistTypePrioritiesAccess(env)) {
            var workflowControl = Session.getModelController(WorkflowControl.class);
            var totalCount = workflowControl.countWorkflowStepsByWorkflow(workflow);

            try(var objectLimiter = new ObjectLimiter(env, WorkflowStepConstants.COMPONENT_VENDOR_NAME, WorkflowStepConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = workflowControl.getWorkflowStepsByWorkflow(workflow);
                var wishlistTypePriorities = entities.stream().map(WorkflowStepObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, wishlistTypePriorities);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("workflow entrances")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<WorkflowEntityTypeObject> getWorkflowEntityTypes(final DataFetchingEnvironment env) {
        if(WishlistSecurityUtils.getInstance().getHasWishlistTypePrioritiesAccess(env)) {
            var workflowControl = Session.getModelController(WorkflowControl.class);
            var totalCount = workflowControl.countWorkflowEntityTypesByWorkflow(workflow);

            try(var objectLimiter = new ObjectLimiter(env, WorkflowEntityTypeConstants.COMPONENT_VENDOR_NAME, WorkflowEntityTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = workflowControl.getWorkflowEntityTypesByWorkflow(workflow);
                var wishlistTypePriorities = entities.stream().map(WorkflowEntityTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, wishlistTypePriorities);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}

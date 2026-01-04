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

package com.echothree.model.control.core.server.graphql;

import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntityStatusObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowSecurityUtils;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.workflow.common.WorkflowEntityStatusConstants;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;

@GraphQLDescription("entity workflow attribute object")
@GraphQLName("EntityWorkflowAttribute")
public class EntityWorkflowAttributeObject
        implements BaseGraphQl, AttributeInterface {

    private final EntityAttribute entityAttribute; // Always Present
    private final EntityInstance entityInstance; // Always Present

    public EntityWorkflowAttributeObject(final EntityAttribute entityAttribute, final EntityInstance entityInstance) {
        this.entityAttribute = entityAttribute;
        this.entityInstance = entityInstance;
    }

    @GraphQLField
    @GraphQLDescription("entity attribute")
    public EntityAttributeObject getEntityAttribute(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityAttributeAccess(env) ? new EntityAttributeObject(entityAttribute, entityInstance) : null;
    }

    @GraphQLField
    @GraphQLDescription("entity instance")
    public EntityInstanceObject getEntityInstance(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityInstanceAccess(env) ? new EntityInstanceObject(entityInstance) : null;
    }

    @GraphQLField
    @GraphQLDescription("workflow entity statuses")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<WorkflowEntityStatusObject> getWorkflowEntityStatuses(final DataFetchingEnvironment env) {
        if(WorkflowSecurityUtils.getHasWorkflowEntityStatusesAccess(env)) {
            var coreControl = Session.getModelController(CoreControl.class);
            var workflowControl = Session.getModelController(WorkflowControl.class);
            var entityAttributeWorkflow = coreControl.getEntityAttributeWorkflow(entityAttribute);
            var workflow = entityAttributeWorkflow.getWorkflow();
            var totalCount = workflowControl.countWorkflowEntityStatusesByWorkflowAndEntityInstance(workflow, entityInstance);

            try(var objectLimiter = new ObjectLimiter(env, WorkflowEntityStatusConstants.COMPONENT_VENDOR_NAME, WorkflowEntityStatusConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = workflowControl.getWorkflowEntityStatusesByEntityInstance(workflow, entityInstance);
                var workflowEntityStatuses = new ArrayList<WorkflowEntityStatusObject>(entities.size());

                entities.forEach((entity) -> workflowEntityStatuses.add(new WorkflowEntityStatusObject(entity)));

                return new CountedObjects<>(objectLimiter, workflowEntityStatuses);
            }
        } else {
            return Connections.emptyConnection();
        }
    }
    
}

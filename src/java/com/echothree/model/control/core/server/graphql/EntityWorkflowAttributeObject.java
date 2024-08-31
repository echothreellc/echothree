// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntityStatusObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowSecurityUtils;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    public Collection<WorkflowEntityStatusObject> getWorkflowEntityStatuses(final DataFetchingEnvironment env) {
        List<WorkflowEntityStatusObject> entityListItemObjects = null;

        if(WorkflowSecurityUtils.getHasWorkflowEntityStatusesAccess(env)) {
            var coreControl = Session.getModelController(CoreControl.class);
            var workflowControl = Session.getModelController(WorkflowControl.class);
            var entityAttributeWorkflow = coreControl.getEntityAttributeWorkflow(entityAttribute);
            var workflow = entityAttributeWorkflow.getWorkflow();
            var workflowEntityStatuses = workflowControl.getWorkflowEntityStatusesByEntityInstanceForUpdate(workflow, entityInstance);

            entityListItemObjects = new ArrayList<WorkflowEntityStatusObject>(workflowEntityStatuses.size());

            for(var workflowEntityStatu : workflowEntityStatuses) {
                entityListItemObjects.add(new WorkflowEntityStatusObject(workflowEntityStatu));
            }
        }

        return entityListItemObjects;
    }
    
}

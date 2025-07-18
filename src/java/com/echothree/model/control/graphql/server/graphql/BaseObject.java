// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.graphql.server.graphql;

import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntityStatusObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowSecurityUtils;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.Session;
import graphql.schema.DataFetchingEnvironment;

public abstract class BaseObject {

    protected WorkflowEntityStatusObject getWorkflowEntityStatusObject(final DataFetchingEnvironment env,
            final BasePK basePrimaryKey, final String workflowName) {
        WorkflowEntityStatusObject result = null;

        if(WorkflowSecurityUtils.getHasWorkflowEntityStatusesAccess(env)) {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var workflowControl = Session.getModelController(WorkflowControl.class);
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(basePrimaryKey);
            var workflow = WorkflowLogic.getInstance().getWorkflowByName(null, workflowName);
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstance(workflow, entityInstance);

            result = workflowEntityStatus == null ? null : new WorkflowEntityStatusObject(workflowEntityStatus);
        }

        return result;
    }

}

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

import com.echothree.control.user.workflow.server.command.GetWorkflowCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowEntityStatusesCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowStepCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowStepTypeCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowStepTypesCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowStepsCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowTypeCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowTypesCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowsCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public final class WorkflowSecurityUtils
        extends BaseGraphQl {

    private static class WorkflowSecurityUtilsHolder {
        static WorkflowSecurityUtils instance = new WorkflowSecurityUtils();
    }
    
    public static WorkflowSecurityUtils getInstance() {
        return WorkflowSecurityUtilsHolder.instance;
    }

    public boolean getHasWorkflowsAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetWorkflowsCommand.class);
    }

    public boolean getHasWorkflowAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetWorkflowCommand.class);
    }

    public boolean getHasWorkflowTypesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetWorkflowTypesCommand.class);
    }

    public boolean getHasWorkflowTypeAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetWorkflowTypeCommand.class);
    }

    public boolean getHasWorkflowStepsAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetWorkflowStepsCommand.class);
    }

    public boolean getHasWorkflowStepAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetWorkflowStepCommand.class);
    }

    public boolean getHasWorkflowStepTypesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetWorkflowStepTypesCommand.class);
    }

    public boolean getHasWorkflowStepTypeAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetWorkflowStepTypeCommand.class);
    }

    public boolean getHasWorkflowEntityStatusesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetWorkflowEntityStatusesCommand.class);
    }

}

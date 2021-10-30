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

import com.echothree.control.user.workflow.server.command.GetWorkflowStepTypeCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowStepTypesCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowTypeCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowTypesCommand;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import graphql.schema.DataFetchingEnvironment;

public final class WorkflowSecurityUtils {

    private static class WorkflowSecurityUtilsHolder {
        static WorkflowSecurityUtils instance = new WorkflowSecurityUtils();
    }
    
    public static WorkflowSecurityUtils getInstance() {
        return WorkflowSecurityUtilsHolder.instance;
    }

    public boolean getHasWorkflowTypesAccess(final DataFetchingEnvironment env) {
        return env.<GraphQlContext>getContext().hasAccess(GetWorkflowTypesCommand.class);
    }

    public boolean getHasWorkflowTypeAccess(final DataFetchingEnvironment env) {
        return env.<GraphQlContext>getContext().hasAccess(GetWorkflowTypeCommand.class);
    }

    public boolean getHasWorkflowStepTypesAccess(final DataFetchingEnvironment env) {
        return env.<GraphQlContext>getContext().hasAccess(GetWorkflowStepTypesCommand.class);
    }

    public boolean getHasWorkflowStepTypeAccess(final DataFetchingEnvironment env) {
        return env.<GraphQlContext>getContext().hasAccess(GetWorkflowStepTypeCommand.class);
    }

}
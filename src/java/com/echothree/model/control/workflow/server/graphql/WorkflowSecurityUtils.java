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

import com.echothree.control.user.workflow.server.command.GetWorkflowCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowDestinationCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowDestinationPartyTypeCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowDestinationPartyTypesCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowDestinationSecurityRoleCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowDestinationSecurityRolesCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowDestinationSelectorCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowDestinationSelectorsCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowDestinationStepCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowDestinationStepsCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowDestinationsCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowEntityStatusesCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowEntityTypeCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowEntityTypesCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowEntranceCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowEntrancePartyTypeCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowEntrancePartyTypesCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowEntranceSecurityRoleCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowEntranceSecurityRolesCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowEntranceSelectorCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowEntranceSelectorsCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowEntranceStepCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowEntranceStepsCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowEntrancesCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowSelectorKindCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowSelectorKindsCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowStepCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowStepTypeCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowStepTypesCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowStepsCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowsCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public interface WorkflowSecurityUtils {

    static boolean getHasWorkflowsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowsCommand.class);
    }

    static boolean getHasWorkflowAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowCommand.class);
    }

    static boolean getHasWorkflowStepsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowStepsCommand.class);
    }

    static boolean getHasWorkflowStepAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowStepCommand.class);
    }

    static boolean getHasWorkflowStepTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowStepTypesCommand.class);
    }

    static boolean getHasWorkflowStepTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowStepTypeCommand.class);
    }

    static boolean getHasWorkflowEntityStatusesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowEntityStatusesCommand.class);
    }

    static boolean getHasWorkflowEntrancesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowEntrancesCommand.class);
    }

    static boolean getHasWorkflowEntranceAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowEntranceCommand.class);
    }

    static boolean getHasWorkflowEntranceStepsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowEntranceStepsCommand.class);
    }

    static boolean getHasWorkflowEntranceStepAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowEntranceStepCommand.class);
    }

    static boolean getHasWorkflowEntrancePartyTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowEntrancePartyTypesCommand.class);
    }

    static boolean getHasWorkflowEntrancePartyTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowEntrancePartyTypeCommand.class);
    }

    static boolean getHasWorkflowEntranceSecurityRolesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowEntranceSecurityRolesCommand.class);
    }

    static boolean getHasWorkflowEntranceSecurityRoleAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowEntranceSecurityRoleCommand.class);
    }

    static boolean getHasWorkflowEntranceSelectorsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowEntranceSelectorsCommand.class);
    }

    static boolean getHasWorkflowEntranceSelectorAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowEntranceSelectorCommand.class);
    }

    static boolean getHasWorkflowDestinationsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowDestinationsCommand.class);
    }

    static boolean getHasWorkflowDestinationAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowDestinationCommand.class);
    }

    static boolean getHasWorkflowDestinationStepsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowDestinationStepsCommand.class);
    }

    static boolean getHasWorkflowDestinationStepAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowDestinationStepCommand.class);
    }

    static boolean getHasWorkflowDestinationPartyTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowDestinationPartyTypesCommand.class);
    }

    static boolean getHasWorkflowDestinationPartyTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowDestinationPartyTypeCommand.class);
    }

    static boolean getHasWorkflowDestinationSecurityRolesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowDestinationSecurityRolesCommand.class);
    }

    static boolean getHasWorkflowDestinationSecurityRoleAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowDestinationSecurityRoleCommand.class);
    }

    static boolean getHasWorkflowDestinationSelectorsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowDestinationSelectorsCommand.class);
    }

    static boolean getHasWorkflowDestinationSelectorAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowDestinationSelectorCommand.class);
    }

    static boolean getHasWorkflowEntityTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowEntityTypesCommand.class);
    }

    static boolean getHasWorkflowEntityTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowEntityTypeCommand.class);
    }

    static boolean getHasWorkflowSelectorKindsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowSelectorKindsCommand.class);
    }

    static boolean getHasWorkflowSelectorKindAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWorkflowSelectorKindCommand.class);
    }

}

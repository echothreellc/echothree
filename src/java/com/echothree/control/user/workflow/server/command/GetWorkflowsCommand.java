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

package com.echothree.control.user.workflow.server.command;

import com.echothree.control.user.workflow.common.form.GetWorkflowsForm;
import com.echothree.control.user.workflow.common.result.WorkflowResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.control.selector.server.logic.SelectorKindLogic;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetWorkflowsCommand
        extends BaseMultipleEntitiesCommand<Workflow, GetWorkflowsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.Workflow.name(), SecurityRoles.List.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SelectorKindName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetWorkflowsCommand */
    public GetWorkflowsCommand(UserVisitPK userVisitPK, GetWorkflowsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    SelectorKind selectorKind = null;

    @Override
    protected Collection<Workflow> getEntities() {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var selectorKindName = form.getSelectorKindName();
        Collection<Workflow> workflows = null;

        if(selectorKindName == null) {
            workflows = workflowControl.getWorkflows();
        } else {
            selectorKind = SelectorKindLogic.getInstance().getSelectorKindByName(this, selectorKindName);

            if(!this.hasExecutionErrors()) {
                workflows = workflowControl.getWorkflowsBySelectorKind(selectorKind);
            }
        }

        return workflows;
    }

    @Override
    protected BaseResult getTransfers(Collection<Workflow> entities) {
        var result = WorkflowResultFactory.getGetWorkflowsResult();

        if(entities != null) {
            var workflowControl = Session.getModelController(WorkflowControl.class);
            var userVisit = getUserVisit();

            if(selectorKind != null) {
                var selectorControl = Session.getModelController(SelectorControl.class);

                result.setSelectorKind(selectorControl.getSelectorKindTransfer(userVisit, selectorKind));
            }

            result.setWorkflows(workflowControl.getWorkflowTransfers(userVisit, entities));
        }

        return result;
    }

}

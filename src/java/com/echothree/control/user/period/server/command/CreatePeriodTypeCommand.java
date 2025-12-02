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

package com.echothree.control.user.period.server.command;

import com.echothree.control.user.period.common.form.CreatePeriodTypeForm;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class CreatePeriodTypeCommand
        extends BaseSimpleCommand<CreatePeriodTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.PeriodType.name(), SecurityRoles.Create.name())
                    )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PeriodKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PeriodTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentPeriodTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WorkflowEntranceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreatePeriodTypeCommand */
    public CreatePeriodTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var periodControl = Session.getModelController(PeriodControl.class);
        var workflowName = form.getWorkflowName();
        var workflowEntranceName = form.getWorkflowEntranceName();
        var parameterCount = (workflowName == null ? 0 : 1) + (workflowEntranceName == null ? 0 : 1);

        if(parameterCount == 0 || parameterCount == 2) {
            var periodKindName = form.getPeriodKindName();
            var periodKind = periodControl.getPeriodKindByName(periodKindName);

            if(periodKind != null) {
                var periodTypeName = form.getPeriodTypeName();
                var periodType = periodControl.getPeriodTypeByName(periodKind, periodTypeName);

                if(periodType == null) {
                    var parentPeriodTypeName = form.getParentPeriodTypeName();
                    var parentPeriodType = periodControl.getPeriodTypeByName(periodKind, parentPeriodTypeName);

                    if(parentPeriodTypeName == null || parentPeriodType != null) {
                        var workflowControl = Session.getModelController(WorkflowControl.class);
                        var workflow = workflowName == null ? null : workflowControl.getWorkflowByName(workflowName);

                        if(workflowName == null || workflow != null) {
                            var workflowEntrance = workflowEntranceName == null? null: workflowControl.getWorkflowEntranceByName(workflow, workflowEntranceName);

                            if(workflowEntranceName == null || workflowEntrance != null) {
                                var partyPK = getPartyPK();
                                var isDefault = Boolean.valueOf(form.getIsDefault());
                                var sortOrder = Integer.valueOf(form.getSortOrder());
                                var description = form.getDescription();

                                periodType = periodControl.createPeriodType(periodKind, periodTypeName, parentPeriodType, workflowEntrance, isDefault, sortOrder, partyPK);

                                if(description != null) {
                                    periodControl.createPeriodTypeDescription(periodType, getPreferredLanguage(), description, partyPK);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownWorkflowEntranceName.name(), workflowName, workflowEntranceName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownWorkflowName.name(), workflowName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownParentPeriodTypeName.name(), periodKindName, parentPeriodTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicatePeriodTypeName.name(), periodKindName, periodTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.DuplicatePeriodKindName.name(), periodKindName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return null;
    }
    
}

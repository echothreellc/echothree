// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.control.user.inventory.server.command;

import com.echothree.control.user.inventory.common.form.CreateLotTypeForm;
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.inventory.server.entity.LotType;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
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

public class CreateLotTypeCommand
        extends BaseSimpleCommand<CreateLotTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.LotType.name(), SecurityRoles.Create.name())
                    )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LotTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentLotTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LotSequenceTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LotWorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LotWorkflowEntranceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateLotTypeCommand */
    public CreateLotTypeCommand(UserVisitPK userVisitPK, CreateLotTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        String lotTypeName = form.getLotTypeName();
        LotType lotType = inventoryControl.getLotTypeByName(lotTypeName);

        if(lotType == null) {
            String parentLotTypeName = form.getParentLotTypeName();
            LotType parentLotType = null;

            if(parentLotTypeName != null) {
                parentLotType = inventoryControl.getLotTypeByName(parentLotTypeName);
            }

            if(parentLotTypeName == null || parentLotType != null) {
                var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
                String lotSequenceTypeName = form.getLotSequenceTypeName();
                SequenceType lotSequenceType = sequenceControl.getSequenceTypeByName(lotSequenceTypeName);

                if(lotSequenceTypeName == null || lotSequenceType != null) {
                    var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
                    String lotWorkflowName = form.getLotWorkflowName();
                    Workflow lotWorkflow = lotWorkflowName == null ? null : workflowControl.getWorkflowByName(lotWorkflowName);

                    if(lotWorkflowName == null || lotWorkflow != null) {
                        String lotWorkflowEntranceName = form.getLotWorkflowEntranceName();

                        if(lotWorkflowEntranceName == null || (lotWorkflow != null && lotWorkflowEntranceName != null)) {
                            WorkflowEntrance lotWorkflowEntrance = lotWorkflowEntranceName == null ? null : workflowControl.getWorkflowEntranceByName(lotWorkflow, lotWorkflowEntranceName);

                            if(lotWorkflowEntranceName == null || lotWorkflowEntrance != null) {
                                PartyPK partyPK = getPartyPK();
                                Boolean isDefault = Boolean.valueOf(form.getIsDefault());
                                Integer sortOrder = Integer.valueOf(form.getSortOrder());
                                String description = form.getDescription();

                                lotType = inventoryControl.createLotType(lotTypeName, parentLotType, lotSequenceType,
                                        lotWorkflow, lotWorkflowEntrance, isDefault, sortOrder, partyPK);

                                if(description != null) {
                                    inventoryControl.createLotTypeDescription(lotType, getPreferredLanguage(), description, partyPK);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownLotWorkflowEntranceName.name(), lotWorkflowName, lotWorkflowEntranceName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.MissingRequiredLotWorkflowName.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLotWorkflowName.name(), lotWorkflowName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLotSequenceTypeName.name(), lotSequenceTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentLotTypeName.name(), parentLotTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateLotTypeName.name(), lotTypeName);
        }

        return null;
    }
    
}

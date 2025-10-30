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

package com.echothree.control.user.inventory.server.command;

import com.echothree.control.user.inventory.common.form.CreateInventoryTransactionTypeForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.inventory.common.exception.UnknownInventoryTransactionWorkflowEntranceNameException;
import com.echothree.model.control.inventory.common.exception.UnknownInventoryTransactionWorkflowNameException;
import com.echothree.model.control.inventory.server.logic.InventoryTransactionTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.logic.SequenceTypeLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowEntranceLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.EntityPermission;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateInventoryTransactionTypeCommand
        extends BaseSimpleCommand<CreateInventoryTransactionTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.InventoryTransactionType.name(), SecurityRoles.Create.name())
                    )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("InventoryTransactionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryTransactionSequenceTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("InventoryTransactionWorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("InventoryTransactionWorkflowEntranceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateInventoryTransactionTypeCommand */
    public CreateInventoryTransactionTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = InventoryResultFactory.getCreateInventoryTransactionTypeResult();
        var inventoryTransactionSequenceTypeName = form.getInventoryTransactionSequenceTypeName();
        var inventoryTransactionSequenceType = inventoryTransactionSequenceTypeName == null ? null : SequenceTypeLogic.getInstance().getSequenceTypeByName(this, inventoryTransactionSequenceTypeName);
        InventoryTransactionType inventoryTransactionType = null;

        if(!hasExecutionErrors()) {
            var inventoryTransactionWorkflowName = form.getInventoryTransactionWorkflowName();
            var inventoryTransactionWorkflow = inventoryTransactionWorkflowName == null ? null : WorkflowLogic.getInstance().getWorkflowByName(
                    UnknownInventoryTransactionWorkflowNameException.class, ExecutionErrors.UnknownInventoryTransactionWorkflowName, this,
                    inventoryTransactionWorkflowName, EntityPermission.READ_ONLY);

            if(!hasExecutionErrors()) {
                var inventoryTransactionWorkflowEntranceName = form.getInventoryTransactionWorkflowEntranceName();

                if(inventoryTransactionWorkflowEntranceName == null || inventoryTransactionWorkflow != null) {
                    var inventoryTransactionWorkflowEntrance = inventoryTransactionWorkflowEntranceName == null ? null : WorkflowEntranceLogic.getInstance().getWorkflowEntranceByName(
                            UnknownInventoryTransactionWorkflowEntranceNameException.class, ExecutionErrors.UnknownInventoryTransactionWorkflowEntranceName, this,
                            inventoryTransactionWorkflow, inventoryTransactionWorkflowEntranceName);

                    if(!hasExecutionErrors()) {
                        var inventoryTransactionTypeName = form.getInventoryTransactionTypeName();
                        var isDefault = Boolean.valueOf(form.getIsDefault());
                        var sortOrder = Integer.valueOf(form.getSortOrder());
                        var description = form.getDescription();
                        var partyPK = getPartyPK();

                        inventoryTransactionType = InventoryTransactionTypeLogic.getInstance().createInventoryTransactionType(this, inventoryTransactionTypeName, inventoryTransactionSequenceType, inventoryTransactionWorkflow,
                                inventoryTransactionWorkflowEntrance, isDefault, sortOrder, getPreferredLanguage(), description, partyPK);
                    }
                } else {
                    addExecutionError(ExecutionErrors.MissingRequiredInventoryTransactionWorkflowName.name());
                }
            }
        }

        if(inventoryTransactionType != null) {
            result.setEntityRef(inventoryTransactionType.getPrimaryKey().getEntityRef());
            result.setInventoryTransactionTypeName(inventoryTransactionType.getLastDetail().getInventoryTransactionTypeName());
        }

        return result;
    }
    
}

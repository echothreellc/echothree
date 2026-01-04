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

package com.echothree.control.user.inventory.server.command;

import com.echothree.control.user.inventory.common.edit.InventoryEditFactory;
import com.echothree.control.user.inventory.common.edit.InventoryConditionEdit;
import com.echothree.control.user.inventory.common.form.EditInventoryConditionForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.result.EditInventoryConditionResult;
import com.echothree.control.user.inventory.common.spec.InventoryConditionUniversalSpec;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.inventory.server.logic.InventoryConditionLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditInventoryConditionCommand
        extends BaseAbstractEditCommand<InventoryConditionUniversalSpec, InventoryConditionEdit, EditInventoryConditionResult, InventoryCondition, InventoryCondition> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.InventoryCondition.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditInventoryConditionCommand */
    public EditInventoryConditionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditInventoryConditionResult getResult() {
        return InventoryResultFactory.getEditInventoryConditionResult();
    }
    
    @Override
    public InventoryConditionEdit getEdit() {
        return InventoryEditFactory.getInventoryConditionEdit();
    }
    
    @Override
    public InventoryCondition getEntity(EditInventoryConditionResult result) {
        return InventoryConditionLogic.getInstance().getInventoryConditionByUniversalSpec(this, spec, false, editModeToEntityPermission(editMode));
    }
    
    @Override
    public InventoryCondition getLockEntity(InventoryCondition inventoryCondition) {
        return inventoryCondition;
    }
    
    @Override
    public void fillInResult(EditInventoryConditionResult result, InventoryCondition inventoryCondition) {
        var inventoryControl = Session.getModelController(InventoryControl.class);
        
        result.setInventoryCondition(inventoryControl.getInventoryConditionTransfer(getUserVisit(), inventoryCondition));
    }
    
    @Override
    public void doLock(InventoryConditionEdit edit, InventoryCondition inventoryCondition) {
        var inventoryControl = Session.getModelController(InventoryControl.class);
        var inventoryConditionDescription = inventoryControl.getInventoryConditionDescription(inventoryCondition, getPreferredLanguage());
        var inventoryConditionDetail = inventoryCondition.getLastDetail();
        
        edit.setInventoryConditionName(inventoryConditionDetail.getInventoryConditionName());
        edit.setIsDefault(inventoryConditionDetail.getIsDefault().toString());
        edit.setSortOrder(inventoryConditionDetail.getSortOrder().toString());

        if(inventoryConditionDescription != null) {
            edit.setDescription(inventoryConditionDescription.getDescription());
        }
    }
        
    @Override
    public void canUpdate(InventoryCondition inventoryCondition) {
        var inventoryControl = Session.getModelController(InventoryControl.class);
        var inventoryConditionName = edit.getInventoryConditionName();
        var duplicateInventoryCondition = inventoryControl.getInventoryConditionByName(inventoryConditionName);

        if(duplicateInventoryCondition != null && !inventoryCondition.equals(duplicateInventoryCondition)) {
            addExecutionError(ExecutionErrors.DuplicateInventoryConditionName.name(), inventoryConditionName);
        }
    }
    
    @Override
    public void doUpdate(InventoryCondition inventoryCondition) {
        var inventoryControl = Session.getModelController(InventoryControl.class);
        var partyPK = getPartyPK();
        var inventoryConditionDetailValue = inventoryControl.getInventoryConditionDetailValueForUpdate(inventoryCondition);
        var inventoryConditionDescription = inventoryControl.getInventoryConditionDescriptionForUpdate(inventoryCondition, getPreferredLanguage());
        var description = edit.getDescription();

        inventoryConditionDetailValue.setInventoryConditionName(edit.getInventoryConditionName());
        inventoryConditionDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        inventoryConditionDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        inventoryControl.updateInventoryConditionFromValue(inventoryConditionDetailValue, partyPK);

        if(inventoryConditionDescription == null && description != null) {
            inventoryControl.createInventoryConditionDescription(inventoryCondition, getPreferredLanguage(), description, partyPK);
        } else if(inventoryConditionDescription != null && description == null) {
            inventoryControl.deleteInventoryConditionDescription(inventoryConditionDescription, partyPK);
        } else if(inventoryConditionDescription != null && description != null) {
            var inventoryConditionDescriptionValue = inventoryControl.getInventoryConditionDescriptionValue(inventoryConditionDescription);

            inventoryConditionDescriptionValue.setDescription(description);
            inventoryControl.updateInventoryConditionDescriptionFromValue(inventoryConditionDescriptionValue, partyPK);
        }
    }
    
}

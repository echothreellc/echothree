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

import com.echothree.control.user.inventory.common.edit.InventoryCostingMethodEdit;
import com.echothree.control.user.inventory.common.edit.InventoryEditFactory;
import com.echothree.control.user.inventory.common.result.EditInventoryCostingMethodResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.InventoryCostingMethodUniversalSpec;
import com.echothree.model.control.inventory.server.control.InventoryCostingMethodControl;
import com.echothree.model.control.inventory.server.logic.InventoryCostingMethodLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryCostingMethod;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.inject.Inject;
import javax.enterprise.context.Dependent;

@Dependent
public class EditInventoryCostingMethodCommand
        extends BaseAbstractEditCommand<InventoryCostingMethodUniversalSpec, InventoryCostingMethodEdit, EditInventoryCostingMethodResult, InventoryCostingMethod, InventoryCostingMethod> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InventoryCostingMethod.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryCostingMethodName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryCostingMethodName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    InventoryCostingMethodControl inventoryCostingMethodControl;

    @Inject
    InventoryCostingMethodLogic inventoryCostingMethodLogic;

    /** Creates a new instance of EditInventoryCostingMethodCommand */
    public EditInventoryCostingMethodCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditInventoryCostingMethodResult getResult() {
        return InventoryResultFactory.getEditInventoryCostingMethodResult();
    }

    @Override
    public InventoryCostingMethodEdit getEdit() {
        return InventoryEditFactory.getInventoryCostingMethodEdit();
    }

    @Override
    public InventoryCostingMethod getEntity(EditInventoryCostingMethodResult result) {
        var inventoryCostingMethodName = spec.getInventoryCostingMethodName();
        var inventoryCostingMethod = inventoryCostingMethodLogic.getInventoryCostingMethodByUniversalSpec(this, spec, false,
                editModeToEntityPermission(editMode));

        if(inventoryCostingMethod != null) {
            result.setInventoryCostingMethod(inventoryCostingMethodControl.getInventoryCostingMethodTransfer(getUserVisit(), inventoryCostingMethod));
        } else {
            addExecutionError(ExecutionErrors.UnknownInventoryCostingMethodName.name(), inventoryCostingMethodName);
        }

        return inventoryCostingMethod;
    }

    @Override
    public InventoryCostingMethod getLockEntity(InventoryCostingMethod inventoryCostingMethod) {
        return inventoryCostingMethod;
    }

    @Override
    public void fillInResult(EditInventoryCostingMethodResult result, InventoryCostingMethod inventoryCostingMethod) {
        result.setInventoryCostingMethod(inventoryCostingMethodControl.getInventoryCostingMethodTransfer(getUserVisit(), inventoryCostingMethod));
    }

    @Override
    public void doLock(InventoryCostingMethodEdit edit, InventoryCostingMethod inventoryCostingMethod) {
        var inventoryCostingMethodDescription = inventoryCostingMethodControl.getInventoryCostingMethodDescription(inventoryCostingMethod, getPreferredLanguage());
        var inventoryCostingMethodDetail = inventoryCostingMethod.getLastDetail();

        edit.setInventoryCostingMethodName(inventoryCostingMethodDetail.getInventoryCostingMethodName());
        edit.setIsDefault(inventoryCostingMethodDetail.getIsDefault().toString());
        edit.setSortOrder(inventoryCostingMethodDetail.getSortOrder().toString());

        if(inventoryCostingMethodDescription != null) {
            edit.setDescription(inventoryCostingMethodDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(InventoryCostingMethod inventoryCostingMethod) {
        var inventoryCostingMethodName = edit.getInventoryCostingMethodName();
        var duplicateInventoryCostingMethod = inventoryCostingMethodControl.getInventoryCostingMethodByName(inventoryCostingMethodName);

        if(duplicateInventoryCostingMethod != null && !inventoryCostingMethod.equals(duplicateInventoryCostingMethod)) {
            addExecutionError(ExecutionErrors.DuplicateInventoryCostingMethodName.name(), inventoryCostingMethodName);
        }
    }

    @Override
    public void doUpdate(InventoryCostingMethod inventoryCostingMethod) {
        var partyPK = getPartyPK();
        var inventoryCostingMethodDetailValue = inventoryCostingMethodControl.getInventoryCostingMethodDetailValueForUpdate(inventoryCostingMethod);
        var inventoryCostingMethodDescription = inventoryCostingMethodControl.getInventoryCostingMethodDescriptionForUpdate(inventoryCostingMethod, getPreferredLanguage());
        var description = edit.getDescription();

        inventoryCostingMethodDetailValue.setInventoryCostingMethodName(edit.getInventoryCostingMethodName());
        inventoryCostingMethodDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        inventoryCostingMethodDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        inventoryCostingMethodLogic.updateInventoryCostingMethodFromValue(inventoryCostingMethodDetailValue, partyPK);

        if(inventoryCostingMethodDescription == null && description != null) {
            inventoryCostingMethodControl.createInventoryCostingMethodDescription(inventoryCostingMethod, getPreferredLanguage(), description, partyPK);
        } else {
            if(inventoryCostingMethodDescription != null && description == null) {
                inventoryCostingMethodControl.deleteInventoryCostingMethodDescription(inventoryCostingMethodDescription, partyPK);
            } else {
                if(inventoryCostingMethodDescription != null && description != null) {
                    var inventoryCostingMethodDescriptionValue = inventoryCostingMethodControl.getInventoryCostingMethodDescriptionValue(inventoryCostingMethodDescription);

                    inventoryCostingMethodDescriptionValue.setDescription(description);
                    inventoryCostingMethodControl.updateInventoryCostingMethodDescriptionFromValue(inventoryCostingMethodDescriptionValue, partyPK);
                }
            }
        }
    }

}

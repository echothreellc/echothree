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
import com.echothree.control.user.inventory.common.edit.InventoryCostingMethodDescriptionEdit;
import com.echothree.control.user.inventory.common.form.EditInventoryCostingMethodDescriptionForm;
import com.echothree.control.user.inventory.common.result.EditInventoryCostingMethodDescriptionResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.InventoryCostingMethodDescriptionSpec;
import com.echothree.model.control.inventory.server.control.InventoryCostingMethodControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryCostingMethod;
import com.echothree.model.data.inventory.server.entity.InventoryCostingMethodDescription;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.inject.Inject;
import javax.enterprise.context.Dependent;

@Dependent
public class EditInventoryCostingMethodDescriptionCommand
        extends BaseAbstractEditCommand<InventoryCostingMethodDescriptionSpec, InventoryCostingMethodDescriptionEdit, EditInventoryCostingMethodDescriptionResult, InventoryCostingMethodDescription, InventoryCostingMethod> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InventoryCostingMethod.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryCostingMethodName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    InventoryCostingMethodControl inventoryCostingMethodControl;

    @Inject
    PartyControl partyControl;

    /** Creates a new instance of EditInventoryCostingMethodDescriptionCommand */
    public EditInventoryCostingMethodDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditInventoryCostingMethodDescriptionResult getResult() {
        return InventoryResultFactory.getEditInventoryCostingMethodDescriptionResult();
    }

    @Override
    public InventoryCostingMethodDescriptionEdit getEdit() {
        return InventoryEditFactory.getInventoryCostingMethodDescriptionEdit();
    }

    @Override
    public InventoryCostingMethodDescription getEntity(EditInventoryCostingMethodDescriptionResult result) {
        InventoryCostingMethodDescription inventoryCostingMethodDescription = null;
        var inventoryCostingMethodName = spec.getInventoryCostingMethodName();
        var inventoryCostingMethod = inventoryCostingMethodControl.getInventoryCostingMethodByName(inventoryCostingMethodName);

        if(inventoryCostingMethod != null) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    inventoryCostingMethodDescription = inventoryCostingMethodControl.getInventoryCostingMethodDescription(inventoryCostingMethod, language);
                } else { // EditMode.UPDATE
                    inventoryCostingMethodDescription = inventoryCostingMethodControl.getInventoryCostingMethodDescriptionForUpdate(inventoryCostingMethod, language);
                }

                if(inventoryCostingMethodDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownInventoryCostingMethodDescription.name(), inventoryCostingMethodName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownInventoryCostingMethodName.name(), inventoryCostingMethodName);
        }

        return inventoryCostingMethodDescription;
    }

    @Override
    public InventoryCostingMethod getLockEntity(InventoryCostingMethodDescription inventoryCostingMethodDescription) {
        return inventoryCostingMethodDescription.getInventoryCostingMethod();
    }

    @Override
    public void fillInResult(EditInventoryCostingMethodDescriptionResult result, InventoryCostingMethodDescription inventoryCostingMethodDescription) {
        result.setInventoryCostingMethodDescription(inventoryCostingMethodControl.getInventoryCostingMethodDescriptionTransfer(getUserVisit(), inventoryCostingMethodDescription));
    }

    @Override
    public void doLock(InventoryCostingMethodDescriptionEdit edit, InventoryCostingMethodDescription inventoryCostingMethodDescription) {
        edit.setDescription(inventoryCostingMethodDescription.getDescription());
    }

    @Override
    public void doUpdate(InventoryCostingMethodDescription inventoryCostingMethodDescription) {
        var inventoryCostingMethodDescriptionValue = inventoryCostingMethodControl.getInventoryCostingMethodDescriptionValue(inventoryCostingMethodDescription);
        inventoryCostingMethodDescriptionValue.setDescription(edit.getDescription());

        inventoryCostingMethodControl.updateInventoryCostingMethodDescriptionFromValue(inventoryCostingMethodDescriptionValue, getPartyPK());
    }

}

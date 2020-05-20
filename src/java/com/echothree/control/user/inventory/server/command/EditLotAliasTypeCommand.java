// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.control.user.inventory.common.edit.LotAliasTypeEdit;
import com.echothree.control.user.inventory.common.form.EditLotAliasTypeForm;
import com.echothree.control.user.inventory.common.result.EditLotAliasTypeResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.LotAliasTypeSpec;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.LotAliasType;
import com.echothree.model.data.inventory.server.entity.LotAliasTypeDescription;
import com.echothree.model.data.inventory.server.entity.LotAliasTypeDetail;
import com.echothree.model.data.inventory.server.value.LotAliasTypeDescriptionValue;
import com.echothree.model.data.inventory.server.value.LotAliasTypeDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
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

public class EditLotAliasTypeCommand
        extends BaseAbstractEditCommand<LotAliasTypeSpec, LotAliasTypeEdit, EditLotAliasTypeResult, LotAliasType, LotAliasType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.LotAliasType.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LotAliasTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LotAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ValidationPattern", FieldType.REGULAR_EXPRESSION, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }

    /** Creates a new instance of EditLotAliasTypeCommand */
    public EditLotAliasTypeCommand(UserVisitPK userVisitPK, EditLotAliasTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditLotAliasTypeResult getResult() {
        return InventoryResultFactory.getEditLotAliasTypeResult();
    }

    @Override
    public LotAliasTypeEdit getEdit() {
        return InventoryEditFactory.getLotAliasTypeEdit();
    }

    @Override
    public LotAliasType getEntity(EditLotAliasTypeResult result) {
        var inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        LotAliasType lotAliasType;
        String lotAliasTypeName = spec.getLotAliasTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            lotAliasType = inventoryControl.getLotAliasTypeByName(lotAliasTypeName);
        } else { // EditMode.UPDATE
            lotAliasType = inventoryControl.getLotAliasTypeByNameForUpdate(lotAliasTypeName);
        }

        if(lotAliasType != null) {
            result.setLotAliasType(inventoryControl.getLotAliasTypeTransfer(getUserVisit(), lotAliasType));
        } else {
            addExecutionError(ExecutionErrors.UnknownLotAliasTypeName.name(), lotAliasTypeName);
        }

        return lotAliasType;
    }

    @Override
    public LotAliasType getLockEntity(LotAliasType lotAliasType) {
        return lotAliasType;
    }

    @Override
    public void fillInResult(EditLotAliasTypeResult result, LotAliasType lotAliasType) {
        var inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);

        result.setLotAliasType(inventoryControl.getLotAliasTypeTransfer(getUserVisit(), lotAliasType));
    }

    @Override
    public void doLock(LotAliasTypeEdit edit, LotAliasType lotAliasType) {
        var inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        LotAliasTypeDescription lotAliasTypeDescription = inventoryControl.getLotAliasTypeDescription(lotAliasType, getPreferredLanguage());
        LotAliasTypeDetail lotAliasTypeDetail = lotAliasType.getLastDetail();

        edit.setLotAliasTypeName(lotAliasTypeDetail.getLotAliasTypeName());
        edit.setValidationPattern(lotAliasTypeDetail.getValidationPattern());
        edit.setIsDefault(lotAliasTypeDetail.getIsDefault().toString());
        edit.setSortOrder(lotAliasTypeDetail.getSortOrder().toString());

        if(lotAliasTypeDescription != null) {
            edit.setDescription(lotAliasTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(LotAliasType lotAliasType) {
        var inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        String lotAliasTypeName = edit.getLotAliasTypeName();
        LotAliasType duplicateLotAliasType = inventoryControl.getLotAliasTypeByName(lotAliasTypeName);

        if(duplicateLotAliasType != null && !lotAliasType.equals(duplicateLotAliasType)) {
            addExecutionError(ExecutionErrors.DuplicateLotAliasTypeName.name(), lotAliasTypeName);
        }
    }

    @Override
    public void doUpdate(LotAliasType lotAliasType) {
        var inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        PartyPK partyPK = getPartyPK();
        LotAliasTypeDetailValue lotAliasTypeDetailValue = inventoryControl.getLotAliasTypeDetailValueForUpdate(lotAliasType);
        LotAliasTypeDescription lotAliasTypeDescription = inventoryControl.getLotAliasTypeDescriptionForUpdate(lotAliasType, getPreferredLanguage());
        String description = edit.getDescription();

        lotAliasTypeDetailValue.setLotAliasTypeName(edit.getLotAliasTypeName());
        lotAliasTypeDetailValue.setValidationPattern(edit.getValidationPattern());
        lotAliasTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        lotAliasTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        inventoryControl.updateLotAliasTypeFromValue(lotAliasTypeDetailValue, partyPK);

        if(lotAliasTypeDescription == null && description != null) {
            inventoryControl.createLotAliasTypeDescription(lotAliasType, getPreferredLanguage(), description, partyPK);
        } else if(lotAliasTypeDescription != null && description == null) {
            inventoryControl.deleteLotAliasTypeDescription(lotAliasTypeDescription, partyPK);
        } else if(lotAliasTypeDescription != null && description != null) {
            LotAliasTypeDescriptionValue lotAliasTypeDescriptionValue = inventoryControl.getLotAliasTypeDescriptionValue(lotAliasTypeDescription);

            lotAliasTypeDescriptionValue.setDescription(description);
            inventoryControl.updateLotAliasTypeDescriptionFromValue(lotAliasTypeDescriptionValue, partyPK);
        }
    }

}

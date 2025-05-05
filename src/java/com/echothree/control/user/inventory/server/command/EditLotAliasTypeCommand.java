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

import com.echothree.control.user.inventory.common.edit.InventoryEditFactory;
import com.echothree.control.user.inventory.common.edit.LotAliasTypeEdit;
import com.echothree.control.user.inventory.common.form.EditLotAliasTypeForm;
import com.echothree.control.user.inventory.common.result.EditLotAliasTypeResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.LotAliasTypeSpec;
import com.echothree.model.control.inventory.server.control.LotAliasControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.LotAliasType;
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
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditLotAliasTypeCommand */
    public EditLotAliasTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
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
        var lotAliasControl = Session.getModelController(LotAliasControl.class);
        LotAliasType lotAliasType;
        var lotAliasTypeName = spec.getLotAliasTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            lotAliasType = lotAliasControl.getLotAliasTypeByName(lotAliasTypeName);
        } else { // EditMode.UPDATE
            lotAliasType = lotAliasControl.getLotAliasTypeByNameForUpdate(lotAliasTypeName);
        }

        if(lotAliasType != null) {
            result.setLotAliasType(lotAliasControl.getLotAliasTypeTransfer(getUserVisit(), lotAliasType));
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
        var lotAliasControl = Session.getModelController(LotAliasControl.class);

        result.setLotAliasType(lotAliasControl.getLotAliasTypeTransfer(getUserVisit(), lotAliasType));
    }

    @Override
    public void doLock(LotAliasTypeEdit edit, LotAliasType lotAliasType) {
        var lotAliasControl = Session.getModelController(LotAliasControl.class);
        var lotAliasTypeDescription = lotAliasControl.getLotAliasTypeDescription(lotAliasType, getPreferredLanguage());
        var lotAliasTypeDetail = lotAliasType.getLastDetail();

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
        var lotAliasControl = Session.getModelController(LotAliasControl.class);
        var lotAliasTypeName = edit.getLotAliasTypeName();
        var duplicateLotAliasType = lotAliasControl.getLotAliasTypeByName(lotAliasTypeName);

        if(duplicateLotAliasType != null && !lotAliasType.equals(duplicateLotAliasType)) {
            addExecutionError(ExecutionErrors.DuplicateLotAliasTypeName.name(), lotAliasTypeName);
        }
    }

    @Override
    public void doUpdate(LotAliasType lotAliasType) {
        var lotAliasControl = Session.getModelController(LotAliasControl.class);
        var partyPK = getPartyPK();
        var lotAliasTypeDetailValue = lotAliasControl.getLotAliasTypeDetailValueForUpdate(lotAliasType);
        var lotAliasTypeDescription = lotAliasControl.getLotAliasTypeDescriptionForUpdate(lotAliasType, getPreferredLanguage());
        var description = edit.getDescription();

        lotAliasTypeDetailValue.setLotAliasTypeName(edit.getLotAliasTypeName());
        lotAliasTypeDetailValue.setValidationPattern(edit.getValidationPattern());
        lotAliasTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        lotAliasTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        lotAliasControl.updateLotAliasTypeFromValue(lotAliasTypeDetailValue, partyPK);

        if(lotAliasTypeDescription == null && description != null) {
            lotAliasControl.createLotAliasTypeDescription(lotAliasType, getPreferredLanguage(), description, partyPK);
        } else if(lotAliasTypeDescription != null && description == null) {
            lotAliasControl.deleteLotAliasTypeDescription(lotAliasTypeDescription, partyPK);
        } else if(lotAliasTypeDescription != null && description != null) {
            var lotAliasTypeDescriptionValue = lotAliasControl.getLotAliasTypeDescriptionValue(lotAliasTypeDescription);

            lotAliasTypeDescriptionValue.setDescription(description);
            lotAliasControl.updateLotAliasTypeDescriptionFromValue(lotAliasTypeDescriptionValue, partyPK);
        }
    }

}

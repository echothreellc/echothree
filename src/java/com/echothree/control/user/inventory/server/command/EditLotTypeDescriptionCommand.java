// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.inventory.remote.edit.InventoryEditFactory;
import com.echothree.control.user.inventory.remote.edit.LotTypeDescriptionEdit;
import com.echothree.control.user.inventory.remote.form.EditLotTypeDescriptionForm;
import com.echothree.control.user.inventory.remote.result.EditLotTypeDescriptionResult;
import com.echothree.control.user.inventory.remote.result.InventoryResultFactory;
import com.echothree.control.user.inventory.remote.spec.LotTypeDescriptionSpec;
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.LotType;
import com.echothree.model.data.inventory.server.entity.LotTypeDescription;
import com.echothree.model.data.inventory.server.value.LotTypeDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditLotTypeDescriptionCommand
        extends BaseAbstractEditCommand<LotTypeDescriptionSpec, LotTypeDescriptionEdit, EditLotTypeDescriptionResult, LotTypeDescription, LotType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.LotType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LotTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditLotTypeDescriptionCommand */
    public EditLotTypeDescriptionCommand(UserVisitPK userVisitPK, EditLotTypeDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditLotTypeDescriptionResult getResult() {
        return InventoryResultFactory.getEditLotTypeDescriptionResult();
    }

    @Override
    public LotTypeDescriptionEdit getEdit() {
        return InventoryEditFactory.getLotTypeDescriptionEdit();
    }

    @Override
    public LotTypeDescription getEntity(EditLotTypeDescriptionResult result) {
        InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        LotTypeDescription lotTypeDescription = null;
        String lotTypeName = spec.getLotTypeName();
        LotType lotType = inventoryControl.getLotTypeByName(lotTypeName);

        if(lotType != null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    lotTypeDescription = inventoryControl.getLotTypeDescription(lotType, language);
                } else { // EditMode.UPDATE
                    lotTypeDescription = inventoryControl.getLotTypeDescriptionForUpdate(lotType, language);
                }

                if(lotTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownLotTypeDescription.name(), lotTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownLotTypeName.name(), lotTypeName);
        }

        return lotTypeDescription;
    }

    @Override
    public LotType getLockEntity(LotTypeDescription lotTypeDescription) {
        return lotTypeDescription.getLotType();
    }

    @Override
    public void fillInResult(EditLotTypeDescriptionResult result, LotTypeDescription lotTypeDescription) {
        InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);

        result.setLotTypeDescription(inventoryControl.getLotTypeDescriptionTransfer(getUserVisit(), lotTypeDescription));
    }

    @Override
    public void doLock(LotTypeDescriptionEdit edit, LotTypeDescription lotTypeDescription) {
        edit.setDescription(lotTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(LotTypeDescription lotTypeDescription) {
        InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        LotTypeDescriptionValue lotTypeDescriptionValue = inventoryControl.getLotTypeDescriptionValue(lotTypeDescription);
        lotTypeDescriptionValue.setDescription(edit.getDescription());

        inventoryControl.updateLotTypeDescriptionFromValue(lotTypeDescriptionValue, getPartyPK());
    }
    
}

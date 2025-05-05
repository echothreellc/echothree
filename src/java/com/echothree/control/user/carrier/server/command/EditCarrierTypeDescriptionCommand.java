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

package com.echothree.control.user.carrier.server.command;

import com.echothree.control.user.carrier.common.edit.CarrierEditFactory;
import com.echothree.control.user.carrier.common.edit.CarrierTypeDescriptionEdit;
import com.echothree.control.user.carrier.common.form.EditCarrierTypeDescriptionForm;
import com.echothree.control.user.carrier.common.result.CarrierResultFactory;
import com.echothree.control.user.carrier.common.result.EditCarrierTypeDescriptionResult;
import com.echothree.control.user.carrier.common.spec.CarrierTypeDescriptionSpec;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.carrier.server.entity.CarrierType;
import com.echothree.model.data.carrier.server.entity.CarrierTypeDescription;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditCarrierTypeDescriptionCommand
        extends BaseAbstractEditCommand<CarrierTypeDescriptionSpec, CarrierTypeDescriptionEdit, EditCarrierTypeDescriptionResult, CarrierTypeDescription, CarrierType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CarrierType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CarrierTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditCarrierTypeDescriptionCommand */
    public EditCarrierTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditCarrierTypeDescriptionResult getResult() {
        return CarrierResultFactory.getEditCarrierTypeDescriptionResult();
    }

    @Override
    public CarrierTypeDescriptionEdit getEdit() {
        return CarrierEditFactory.getCarrierTypeDescriptionEdit();
    }

    @Override
    public CarrierTypeDescription getEntity(EditCarrierTypeDescriptionResult result) {
        var carrierControl = Session.getModelController(CarrierControl.class);
        CarrierTypeDescription carrierTypeDescription = null;
        var carrierTypeName = spec.getCarrierTypeName();
        var carrierType = carrierControl.getCarrierTypeByName(carrierTypeName);

        if(carrierType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    carrierTypeDescription = carrierControl.getCarrierTypeDescription(carrierType, language);
                } else { // EditMode.UPDATE
                    carrierTypeDescription = carrierControl.getCarrierTypeDescriptionForUpdate(carrierType, language);
                }

                if(carrierTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownCarrierTypeDescription.name(), carrierTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCarrierTypeName.name(), carrierTypeName);
        }

        return carrierTypeDescription;
    }

    @Override
    public CarrierType getLockEntity(CarrierTypeDescription carrierTypeDescription) {
        return carrierTypeDescription.getCarrierType();
    }

    @Override
    public void fillInResult(EditCarrierTypeDescriptionResult result, CarrierTypeDescription carrierTypeDescription) {
        var carrierControl = Session.getModelController(CarrierControl.class);

        result.setCarrierTypeDescription(carrierControl.getCarrierTypeDescriptionTransfer(getUserVisit(), carrierTypeDescription));
    }

    @Override
    public void doLock(CarrierTypeDescriptionEdit edit, CarrierTypeDescription carrierTypeDescription) {
        edit.setDescription(carrierTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(CarrierTypeDescription carrierTypeDescription) {
        var carrierControl = Session.getModelController(CarrierControl.class);
        var carrierTypeDescriptionValue = carrierControl.getCarrierTypeDescriptionValue(carrierTypeDescription);
        
        carrierTypeDescriptionValue.setDescription(edit.getDescription());
        
        carrierControl.updateCarrierTypeDescriptionFromValue(carrierTypeDescriptionValue, getPartyPK());
    }

}

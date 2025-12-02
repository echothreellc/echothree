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
import com.echothree.control.user.carrier.common.edit.CarrierServiceDescriptionEdit;
import com.echothree.control.user.carrier.common.form.EditCarrierServiceDescriptionForm;
import com.echothree.control.user.carrier.common.result.CarrierResultFactory;
import com.echothree.control.user.carrier.common.result.EditCarrierServiceDescriptionResult;
import com.echothree.control.user.carrier.common.spec.CarrierServiceDescriptionSpec;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.carrier.server.entity.CarrierService;
import com.echothree.model.data.carrier.server.entity.CarrierServiceDescription;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditCarrierServiceDescriptionCommand
        extends BaseAbstractEditCommand<CarrierServiceDescriptionSpec, CarrierServiceDescriptionEdit, EditCarrierServiceDescriptionResult, CarrierServiceDescription, CarrierService> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CarrierService.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CarrierName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CarrierServiceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditCarrierServiceDescriptionCommand */
    public EditCarrierServiceDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCarrierServiceDescriptionResult getResult() {
        return CarrierResultFactory.getEditCarrierServiceDescriptionResult();
    }

    @Override
    public CarrierServiceDescriptionEdit getEdit() {
        return CarrierEditFactory.getCarrierServiceDescriptionEdit();
    }

    @Override
    public CarrierServiceDescription getEntity(EditCarrierServiceDescriptionResult result) {
        var carrierControl = Session.getModelController(CarrierControl.class);
        CarrierServiceDescription carrierServiceDescription = null;
        var carrierName = spec.getCarrierName();
        var carrier = carrierControl.getCarrierByName(carrierName);

        if(carrier != null) {
            var carrierParty = carrier.getParty();
            var carrierServiceName = spec.getCarrierServiceName();
            var carrierService = carrierControl.getCarrierServiceByName(carrierParty, carrierServiceName);

            if(carrierService != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        carrierServiceDescription = carrierControl.getCarrierServiceDescription(carrierService, language);
                    } else { // EditMode.UPDATE
                        carrierServiceDescription = carrierControl.getCarrierServiceDescriptionForUpdate(carrierService, language);
                    }

                    if(carrierServiceDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownCarrierServiceDescription.name(), carrierServiceName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCarrierServiceName.name(), carrierName, carrierServiceName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCarrierName.name(), carrierName);
        }

        return carrierServiceDescription;
    }

    @Override
    public CarrierService getLockEntity(CarrierServiceDescription carrierServiceDescription) {
        return carrierServiceDescription.getCarrierService();
    }

    @Override
    public void fillInResult(EditCarrierServiceDescriptionResult result, CarrierServiceDescription carrierServiceDescription) {
        var carrierControl = Session.getModelController(CarrierControl.class);

        result.setCarrierServiceDescription(carrierControl.getCarrierServiceDescriptionTransfer(getUserVisit(), carrierServiceDescription));
    }

    @Override
    public void doLock(CarrierServiceDescriptionEdit edit, CarrierServiceDescription carrierServiceDescription) {
        edit.setDescription(carrierServiceDescription.getDescription());
    }

    @Override
    public void doUpdate(CarrierServiceDescription carrierServiceDescription) {
        var carrierControl = Session.getModelController(CarrierControl.class);
        var carrierServiceDescriptionValue = carrierControl.getCarrierServiceDescriptionValue(carrierServiceDescription);

        carrierServiceDescriptionValue.setDescription(edit.getDescription());

        carrierControl.updateCarrierServiceDescriptionFromValue(carrierServiceDescriptionValue, getPartyPK());
    }

}

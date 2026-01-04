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

package com.echothree.control.user.carrier.server.command;

import com.echothree.control.user.carrier.common.edit.CarrierEditFactory;
import com.echothree.control.user.carrier.common.edit.CarrierOptionDescriptionEdit;
import com.echothree.control.user.carrier.common.form.EditCarrierOptionDescriptionForm;
import com.echothree.control.user.carrier.common.result.CarrierResultFactory;
import com.echothree.control.user.carrier.common.result.EditCarrierOptionDescriptionResult;
import com.echothree.control.user.carrier.common.spec.CarrierOptionDescriptionSpec;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.carrier.server.entity.CarrierOption;
import com.echothree.model.data.carrier.server.entity.CarrierOptionDescription;
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
public class EditCarrierOptionDescriptionCommand
        extends BaseAbstractEditCommand<CarrierOptionDescriptionSpec, CarrierOptionDescriptionEdit, EditCarrierOptionDescriptionResult, CarrierOptionDescription, CarrierOption> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CarrierOption.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CarrierName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CarrierOptionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditCarrierOptionDescriptionCommand */
    public EditCarrierOptionDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditCarrierOptionDescriptionResult getResult() {
        return CarrierResultFactory.getEditCarrierOptionDescriptionResult();
    }

    @Override
    public CarrierOptionDescriptionEdit getEdit() {
        return CarrierEditFactory.getCarrierOptionDescriptionEdit();
    }

    @Override
    public CarrierOptionDescription getEntity(EditCarrierOptionDescriptionResult result) {
        var carrierControl = Session.getModelController(CarrierControl.class);
        CarrierOptionDescription carrierOptionDescription = null;
        var carrierName = spec.getCarrierName();
        var carrier = carrierControl.getCarrierByName(carrierName);

        if(carrier != null) {
            var carrierParty = carrier.getParty();
            var carrierOptionName = spec.getCarrierOptionName();
            var carrierOption = carrierControl.getCarrierOptionByName(carrierParty, carrierOptionName);

            if(carrierOption != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        carrierOptionDescription = carrierControl.getCarrierOptionDescription(carrierOption, language);
                    } else { // EditMode.UPDATE
                        carrierOptionDescription = carrierControl.getCarrierOptionDescriptionForUpdate(carrierOption, language);
                    }

                    if(carrierOptionDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownCarrierOptionDescription.name(), carrierOptionName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCarrierOptionName.name(), carrierName, carrierOptionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCarrierName.name(), carrierName);
        }

        return carrierOptionDescription;
    }

    @Override
    public CarrierOption getLockEntity(CarrierOptionDescription carrierOptionDescription) {
        return carrierOptionDescription.getCarrierOption();
    }

    @Override
    public void fillInResult(EditCarrierOptionDescriptionResult result, CarrierOptionDescription carrierOptionDescription) {
        var carrierControl = Session.getModelController(CarrierControl.class);

        result.setCarrierOptionDescription(carrierControl.getCarrierOptionDescriptionTransfer(getUserVisit(), carrierOptionDescription));
    }

    @Override
    public void doLock(CarrierOptionDescriptionEdit edit, CarrierOptionDescription carrierOptionDescription) {
        edit.setDescription(carrierOptionDescription.getDescription());
    }

    @Override
    public void doUpdate(CarrierOptionDescription carrierOptionDescription) {
        var carrierControl = Session.getModelController(CarrierControl.class);
        var carrierOptionDescriptionValue = carrierControl.getCarrierOptionDescriptionValue(carrierOptionDescription);

        carrierOptionDescriptionValue.setDescription(edit.getDescription());

        carrierControl.updateCarrierOptionDescriptionFromValue(carrierOptionDescriptionValue, getPartyPK());
    }

}

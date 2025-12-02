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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.edit.HarmonizedTariffScheduleCodeUnitDescriptionEdit;
import com.echothree.control.user.item.common.edit.ItemEditFactory;
import com.echothree.control.user.item.common.form.EditHarmonizedTariffScheduleCodeUnitDescriptionForm;
import com.echothree.control.user.item.common.result.EditHarmonizedTariffScheduleCodeUnitDescriptionResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.HarmonizedTariffScheduleCodeUnitDescriptionSpec;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUnit;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUnitDescription;
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
public class EditHarmonizedTariffScheduleCodeUnitDescriptionCommand
        extends BaseAbstractEditCommand<HarmonizedTariffScheduleCodeUnitDescriptionSpec, HarmonizedTariffScheduleCodeUnitDescriptionEdit, EditHarmonizedTariffScheduleCodeUnitDescriptionResult, HarmonizedTariffScheduleCodeUnitDescription, HarmonizedTariffScheduleCodeUnit> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.HarmonizedTariffScheduleCodeUnit.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("HarmonizedTariffScheduleCodeUnitName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditHarmonizedTariffScheduleCodeUnitDescriptionCommand */
    public EditHarmonizedTariffScheduleCodeUnitDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditHarmonizedTariffScheduleCodeUnitDescriptionResult getResult() {
        return ItemResultFactory.getEditHarmonizedTariffScheduleCodeUnitDescriptionResult();
    }

    @Override
    public HarmonizedTariffScheduleCodeUnitDescriptionEdit getEdit() {
        return ItemEditFactory.getHarmonizedTariffScheduleCodeUnitDescriptionEdit();
    }

    @Override
    public HarmonizedTariffScheduleCodeUnitDescription getEntity(EditHarmonizedTariffScheduleCodeUnitDescriptionResult result) {
        var itemControl = Session.getModelController(ItemControl.class);
        HarmonizedTariffScheduleCodeUnitDescription harmonizedTariffScheduleCodeUnitDescription = null;
        var harmonizedTariffScheduleCodeUnitName = spec.getHarmonizedTariffScheduleCodeUnitName();
        var harmonizedTariffScheduleCodeUnit = itemControl.getHarmonizedTariffScheduleCodeUnitByName(harmonizedTariffScheduleCodeUnitName);

        if(harmonizedTariffScheduleCodeUnit != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    harmonizedTariffScheduleCodeUnitDescription = itemControl.getHarmonizedTariffScheduleCodeUnitDescription(harmonizedTariffScheduleCodeUnit, language);
                } else { // EditMode.UPDATE
                    harmonizedTariffScheduleCodeUnitDescription = itemControl.getHarmonizedTariffScheduleCodeUnitDescriptionForUpdate(harmonizedTariffScheduleCodeUnit, language);
                }

                if(harmonizedTariffScheduleCodeUnitDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownHarmonizedTariffScheduleCodeUnitDescription.name(), harmonizedTariffScheduleCodeUnitName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownHarmonizedTariffScheduleCodeUnitName.name(), harmonizedTariffScheduleCodeUnitName);
        }

        return harmonizedTariffScheduleCodeUnitDescription;
    }

    @Override
    public HarmonizedTariffScheduleCodeUnit getLockEntity(HarmonizedTariffScheduleCodeUnitDescription harmonizedTariffScheduleCodeUnitDescription) {
        return harmonizedTariffScheduleCodeUnitDescription.getHarmonizedTariffScheduleCodeUnit();
    }

    @Override
    public void fillInResult(EditHarmonizedTariffScheduleCodeUnitDescriptionResult result, HarmonizedTariffScheduleCodeUnitDescription harmonizedTariffScheduleCodeUnitDescription) {
        var itemControl = Session.getModelController(ItemControl.class);

        result.setHarmonizedTariffScheduleCodeUnitDescription(itemControl.getHarmonizedTariffScheduleCodeUnitDescriptionTransfer(getUserVisit(), harmonizedTariffScheduleCodeUnitDescription));
    }

    @Override
    public void doLock(HarmonizedTariffScheduleCodeUnitDescriptionEdit edit, HarmonizedTariffScheduleCodeUnitDescription harmonizedTariffScheduleCodeUnitDescription) {
        edit.setDescription(harmonizedTariffScheduleCodeUnitDescription.getDescription());
    }

    @Override
    public void doUpdate(HarmonizedTariffScheduleCodeUnitDescription harmonizedTariffScheduleCodeUnitDescription) {
        var itemControl = Session.getModelController(ItemControl.class);
        var harmonizedTariffScheduleCodeUnitDescriptionValue = itemControl.getHarmonizedTariffScheduleCodeUnitDescriptionValue(harmonizedTariffScheduleCodeUnitDescription);

        harmonizedTariffScheduleCodeUnitDescriptionValue.setDescription(edit.getDescription());

        itemControl.updateHarmonizedTariffScheduleCodeUnitDescriptionFromValue(harmonizedTariffScheduleCodeUnitDescriptionValue, getPartyPK());
    }

}

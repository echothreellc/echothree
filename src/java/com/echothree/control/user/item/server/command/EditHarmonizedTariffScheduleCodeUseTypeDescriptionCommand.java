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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.edit.HarmonizedTariffScheduleCodeUseTypeDescriptionEdit;
import com.echothree.control.user.item.common.edit.ItemEditFactory;
import com.echothree.control.user.item.common.form.EditHarmonizedTariffScheduleCodeUseTypeDescriptionForm;
import com.echothree.control.user.item.common.result.EditHarmonizedTariffScheduleCodeUseTypeDescriptionResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.HarmonizedTariffScheduleCodeUseTypeDescriptionSpec;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUseType;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUseTypeDescription;
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
public class EditHarmonizedTariffScheduleCodeUseTypeDescriptionCommand
        extends BaseAbstractEditCommand<HarmonizedTariffScheduleCodeUseTypeDescriptionSpec, HarmonizedTariffScheduleCodeUseTypeDescriptionEdit, EditHarmonizedTariffScheduleCodeUseTypeDescriptionResult, HarmonizedTariffScheduleCodeUseTypeDescription, HarmonizedTariffScheduleCodeUseType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.HarmonizedTariffScheduleCodeUseType.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("HarmonizedTariffScheduleCodeUseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditHarmonizedTariffScheduleCodeUseTypeDescriptionCommand */
    public EditHarmonizedTariffScheduleCodeUseTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditHarmonizedTariffScheduleCodeUseTypeDescriptionResult getResult() {
        return ItemResultFactory.getEditHarmonizedTariffScheduleCodeUseTypeDescriptionResult();
    }

    @Override
    public HarmonizedTariffScheduleCodeUseTypeDescriptionEdit getEdit() {
        return ItemEditFactory.getHarmonizedTariffScheduleCodeUseTypeDescriptionEdit();
    }

    @Override
    public HarmonizedTariffScheduleCodeUseTypeDescription getEntity(EditHarmonizedTariffScheduleCodeUseTypeDescriptionResult result) {
        var itemControl = Session.getModelController(ItemControl.class);
        HarmonizedTariffScheduleCodeUseTypeDescription harmonizedTariffScheduleCodeUseTypeDescription = null;
        var harmonizedTariffScheduleCodeUseTypeName = spec.getHarmonizedTariffScheduleCodeUseTypeName();
        var harmonizedTariffScheduleCodeUseType = itemControl.getHarmonizedTariffScheduleCodeUseTypeByName(harmonizedTariffScheduleCodeUseTypeName);

        if(harmonizedTariffScheduleCodeUseType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    harmonizedTariffScheduleCodeUseTypeDescription = itemControl.getHarmonizedTariffScheduleCodeUseTypeDescription(harmonizedTariffScheduleCodeUseType, language);
                } else { // EditMode.UPDATE
                    harmonizedTariffScheduleCodeUseTypeDescription = itemControl.getHarmonizedTariffScheduleCodeUseTypeDescriptionForUpdate(harmonizedTariffScheduleCodeUseType, language);
                }

                if(harmonizedTariffScheduleCodeUseTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownHarmonizedTariffScheduleCodeUseTypeDescription.name(), harmonizedTariffScheduleCodeUseTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownHarmonizedTariffScheduleCodeUseTypeName.name(), harmonizedTariffScheduleCodeUseTypeName);
        }

        return harmonizedTariffScheduleCodeUseTypeDescription;
    }

    @Override
    public HarmonizedTariffScheduleCodeUseType getLockEntity(HarmonizedTariffScheduleCodeUseTypeDescription harmonizedTariffScheduleCodeUseTypeDescription) {
        return harmonizedTariffScheduleCodeUseTypeDescription.getHarmonizedTariffScheduleCodeUseType();
    }

    @Override
    public void fillInResult(EditHarmonizedTariffScheduleCodeUseTypeDescriptionResult result, HarmonizedTariffScheduleCodeUseTypeDescription harmonizedTariffScheduleCodeUseTypeDescription) {
        var itemControl = Session.getModelController(ItemControl.class);

        result.setHarmonizedTariffScheduleCodeUseTypeDescription(itemControl.getHarmonizedTariffScheduleCodeUseTypeDescriptionTransfer(getUserVisit(), harmonizedTariffScheduleCodeUseTypeDescription));
    }

    @Override
    public void doLock(HarmonizedTariffScheduleCodeUseTypeDescriptionEdit edit, HarmonizedTariffScheduleCodeUseTypeDescription harmonizedTariffScheduleCodeUseTypeDescription) {
        edit.setDescription(harmonizedTariffScheduleCodeUseTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(HarmonizedTariffScheduleCodeUseTypeDescription harmonizedTariffScheduleCodeUseTypeDescription) {
        var itemControl = Session.getModelController(ItemControl.class);
        var harmonizedTariffScheduleCodeUseTypeDescriptionValue = itemControl.getHarmonizedTariffScheduleCodeUseTypeDescriptionValue(harmonizedTariffScheduleCodeUseTypeDescription);

        harmonizedTariffScheduleCodeUseTypeDescriptionValue.setDescription(edit.getDescription());

        itemControl.updateHarmonizedTariffScheduleCodeUseTypeDescriptionFromValue(harmonizedTariffScheduleCodeUseTypeDescriptionValue, getPartyPK());
    }

}

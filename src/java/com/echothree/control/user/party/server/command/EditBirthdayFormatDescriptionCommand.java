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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.edit.BirthdayFormatDescriptionEdit;
import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.form.EditBirthdayFormatDescriptionForm;
import com.echothree.control.user.party.common.result.EditBirthdayFormatDescriptionResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.BirthdayFormatDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.server.entity.BirthdayFormat;
import com.echothree.model.data.party.server.entity.BirthdayFormatDescription;
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
public class EditBirthdayFormatDescriptionCommand
        extends BaseAbstractEditCommand<BirthdayFormatDescriptionSpec, BirthdayFormatDescriptionEdit, EditBirthdayFormatDescriptionResult, BirthdayFormatDescription, BirthdayFormat> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.BirthdayFormat.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("BirthdayFormatName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditBirthdayFormatDescriptionCommand */
    public EditBirthdayFormatDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditBirthdayFormatDescriptionResult getResult() {
        return PartyResultFactory.getEditBirthdayFormatDescriptionResult();
    }

    @Override
    public BirthdayFormatDescriptionEdit getEdit() {
        return PartyEditFactory.getBirthdayFormatDescriptionEdit();
    }

    @Override
    public BirthdayFormatDescription getEntity(EditBirthdayFormatDescriptionResult result) {
        var partyControl = Session.getModelController(PartyControl.class);
        BirthdayFormatDescription birthdayFormatDescription = null;
        var birthdayFormatName = spec.getBirthdayFormatName();
        var birthdayFormat = partyControl.getBirthdayFormatByName(birthdayFormatName);

        if(birthdayFormat != null) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    birthdayFormatDescription = partyControl.getBirthdayFormatDescription(birthdayFormat, language);
                } else { // EditMode.UPDATE
                    birthdayFormatDescription = partyControl.getBirthdayFormatDescriptionForUpdate(birthdayFormat, language);
                }

                if(birthdayFormatDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownBirthdayFormatDescription.name(), birthdayFormatName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownBirthdayFormatName.name(), birthdayFormatName);
        }

        return birthdayFormatDescription;
    }

    @Override
    public BirthdayFormat getLockEntity(BirthdayFormatDescription birthdayFormatDescription) {
        return birthdayFormatDescription.getBirthdayFormat();
    }

    @Override
    public void fillInResult(EditBirthdayFormatDescriptionResult result, BirthdayFormatDescription birthdayFormatDescription) {
        var partyControl = Session.getModelController(PartyControl.class);

        result.setBirthdayFormatDescription(partyControl.getBirthdayFormatDescriptionTransfer(getUserVisit(), birthdayFormatDescription));
    }

    @Override
    public void doLock(BirthdayFormatDescriptionEdit edit, BirthdayFormatDescription birthdayFormatDescription) {
        edit.setDescription(birthdayFormatDescription.getDescription());
    }

    @Override
    public void doUpdate(BirthdayFormatDescription birthdayFormatDescription) {
        var partyControl = Session.getModelController(PartyControl.class);
        var birthdayFormatDescriptionValue = partyControl.getBirthdayFormatDescriptionValue(birthdayFormatDescription);
        birthdayFormatDescriptionValue.setDescription(edit.getDescription());

        partyControl.updateBirthdayFormatDescriptionFromValue(birthdayFormatDescriptionValue, getPartyPK());
    }

}

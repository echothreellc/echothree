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

package com.echothree.control.user.letter.server.command;

import com.echothree.control.user.letter.common.edit.LetterEditFactory;
import com.echothree.control.user.letter.common.edit.LetterSourceEdit;
import com.echothree.control.user.letter.common.result.EditLetterSourceResult;
import com.echothree.control.user.letter.common.result.LetterResultFactory;
import com.echothree.control.user.letter.common.spec.LetterSourceUniversalSpec;
import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.control.letter.server.logic.LetterSourceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.letter.server.entity.LetterSource;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditLetterSourceCommand
        extends BaseAbstractEditCommand<LetterSourceUniversalSpec, LetterSourceEdit, EditLetterSourceResult, LetterSource, LetterSource> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.LetterSource.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("LetterSourceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("LetterSourceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EmailAddressContactMechanismName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EmailAddressContactMechanismAliasTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EmailAddressContactMechanismAlias", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PostalAddressContactMechanismName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PostalAddressContactMechanismAliasTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PostalAddressContactMechanismAlias", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LetterSourceContactMechanismName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LetterSourceContactMechanismAliasTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LetterSourceContactMechanismAlias", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    private PartyContactMechanism emailAddressPartyContactMechanism;
    private PartyContactMechanism postalAddressPartyContactMechanism;
    private PartyContactMechanism letterSourcePartyContactMechanism;

    @Inject
    LetterControl letterControl;

    @Inject
    LetterSourceLogic letterSourceLogic;

    /** Creates a new instance of EditLetterSourceCommand */
    public EditLetterSourceCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditLetterSourceResult getResult() {
        return LetterResultFactory.getEditLetterSourceResult();
    }

    @Override
    public LetterSourceEdit getEdit() {
        return LetterEditFactory.getLetterSourceEdit();
    }

    @Override
    public LetterSource getEntity(EditLetterSourceResult result) {
        return letterSourceLogic.getLetterSourceByUniversalSpec(this, spec, false, editModeToEntityPermission(editMode));
    }

    @Override
    public LetterSource getLockEntity(LetterSource letterSource) {
        return letterSource;
    }

    @Override
    public void fillInResult(EditLetterSourceResult result, LetterSource letterSource) {
        result.setLetterSource(letterControl.getLetterSourceTransfer(getUserVisit(), letterSource));
    }

    @Override
    public void doLock(LetterSourceEdit edit, LetterSource letterSource) {
        var letterSourceDescription = letterControl.getLetterSourceDescription(letterSource, getPreferredLanguage());
        var letterSourceDetail = letterSource.getLastDetail();

        edit.setLetterSourceName(letterSourceDetail.getLetterSourceName());
        edit.setEmailAddressContactMechanismName(letterSourceDetail.getEmailAddressPartyContactMechanism().getLastDetail().getContactMechanism().getLastDetail().getContactMechanismName());
        edit.setPostalAddressContactMechanismName(letterSourceDetail.getPostalAddressPartyContactMechanism().getLastDetail().getContactMechanism().getLastDetail().getContactMechanismName());
        edit.setLetterSourceContactMechanismName(letterSourceDetail.getLetterSourcePartyContactMechanism().getLastDetail().getContactMechanism().getLastDetail().getContactMechanismName());
        edit.setIsDefault(letterSourceDetail.getIsDefault().toString());
        edit.setSortOrder(letterSourceDetail.getSortOrder().toString());

        if(letterSourceDescription != null) {
            edit.setDescription(letterSourceDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(LetterSource letterSource) {
        var letterSourceName = edit.getLetterSourceName();
        var duplicateLetterSource = letterControl.getLetterSourceByName(letterSourceName);

        if(duplicateLetterSource != null && !letterSource.equals(duplicateLetterSource)) {
            addExecutionError(ExecutionErrors.DuplicateLetterSourceName.name(), letterSourceName);
        }

        if(!hasExecutionErrors()) {
            var companyParty = letterSource.getLastDetail().getCompanyParty();

            emailAddressPartyContactMechanism = letterSourceLogic.getLetterSourcePartyContactMechanism(this, companyParty,
                    edit.getEmailAddressContactMechanismName(), edit.getEmailAddressContactMechanismAliasTypeName(),
                    edit.getEmailAddressContactMechanismAlias(), ContactMechanismTypes.EMAIL_ADDRESS);
            postalAddressPartyContactMechanism = letterSourceLogic.getLetterSourcePartyContactMechanism(this, companyParty,
                    edit.getPostalAddressContactMechanismName(), edit.getPostalAddressContactMechanismAliasTypeName(),
                    edit.getPostalAddressContactMechanismAlias(), ContactMechanismTypes.POSTAL_ADDRESS);
            letterSourcePartyContactMechanism = letterSourceLogic.getLetterSourcePartyContactMechanism(this, companyParty,
                    edit.getLetterSourceContactMechanismName(), edit.getLetterSourceContactMechanismAliasTypeName(),
                    edit.getLetterSourceContactMechanismAlias(), ContactMechanismTypes.WEB_ADDRESS);
        }
    }

    @Override
    public void doUpdate(LetterSource letterSource) {
        var partyPK = getPartyPK();
        var letterSourceDetailValue = letterControl.getLetterSourceDetailValueForUpdate(letterSource);
        var letterSourceDescription = letterControl.getLetterSourceDescriptionForUpdate(letterSource, getPreferredLanguage());
        var description = edit.getDescription();

        letterSourceDetailValue.setLetterSourceName(edit.getLetterSourceName());
        letterSourceDetailValue.setEmailAddressPartyContactMechanismPK(emailAddressPartyContactMechanism.getPrimaryKey());
        letterSourceDetailValue.setPostalAddressPartyContactMechanismPK(postalAddressPartyContactMechanism.getPrimaryKey());
        letterSourceDetailValue.setLetterSourcePartyContactMechanismPK(letterSourcePartyContactMechanism.getPrimaryKey());
        letterSourceDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        letterSourceDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        letterControl.updateLetterSourceFromValue(letterSourceDetailValue, partyPK);

        if(letterSourceDescription == null && description != null) {
            letterControl.createLetterSourceDescription(letterSource, getPreferredLanguage(), description, partyPK);
        } else if(letterSourceDescription != null && description == null) {
            letterControl.deleteLetterSourceDescription(letterSourceDescription, partyPK);
        } else if(letterSourceDescription != null && description != null) {
            var letterSourceDescriptionValue = letterControl.getLetterSourceDescriptionValue(letterSourceDescription);

            letterSourceDescriptionValue.setDescription(description);

            letterControl.updateLetterSourceDescriptionFromValue(letterSourceDescriptionValue, partyPK);
        }
    }

}

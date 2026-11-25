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

import com.echothree.control.user.letter.common.edit.LetterEdit;
import com.echothree.control.user.letter.common.edit.LetterEditFactory;
import com.echothree.control.user.letter.common.result.EditLetterResult;
import com.echothree.control.user.letter.common.result.LetterResultFactory;
import com.echothree.control.user.letter.common.spec.LetterUniversalSpec;
import com.echothree.model.control.contactlist.server.logic.ContactListLogic;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.control.letter.server.logic.LetterLogic;
import com.echothree.model.control.letter.server.logic.LetterSourceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contactlist.server.entity.ContactList;
import com.echothree.model.data.letter.server.entity.Letter;
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
public class EditLetterCommand
        extends BaseAbstractEditCommand<LetterUniversalSpec, LetterEdit, EditLetterResult, Letter, Letter> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Letter.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LetterName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("LetterName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LetterSourceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContactListName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    private ContactList contactList;
    private LetterSource letterSource;

    @Inject
    LetterControl letterControl;

    @Inject
    ContactListLogic contactListLogic;

    @Inject
    LetterLogic letterLogic;

    @Inject
    LetterSourceLogic letterSourceLogic;

    /** Creates a new instance of EditLetterCommand */
    public EditLetterCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditLetterResult getResult() {
        return LetterResultFactory.getEditLetterResult();
    }

    @Override
    public LetterEdit getEdit() {
        return LetterEditFactory.getLetterEdit();
    }

    @Override
    public Letter getEntity(EditLetterResult result) {
        return letterLogic.getLetterByUniversalSpec(this, spec, editModeToEntityPermission(editMode));
    }

    @Override
    public Letter getLockEntity(Letter letter) {
        return letter;
    }

    @Override
    public void fillInResult(EditLetterResult result, Letter letter) {
        result.setLetter(letterControl.getLetterTransfer(getUserVisit(), letter));
    }

    @Override
    public void doLock(LetterEdit edit, Letter letter) {
        var letterDescription = letterControl.getLetterDescription(letter, getPreferredLanguage());
        var letterDetail = letter.getLastDetail();
        var contactList = letterDetail.getContactList();

        edit.setLetterName(letterDetail.getLetterName());
        edit.setLetterSourceName(letterDetail.getLetterSource().getLastDetail().getLetterSourceName());
        edit.setContactListName(contactList == null? null: contactList.getLastDetail().getContactListName());
        edit.setIsDefault(letterDetail.getIsDefault().toString());
        edit.setSortOrder(letterDetail.getSortOrder().toString());

        if(letterDescription != null) {
            edit.setDescription(letterDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(Letter letter) {
        var chainType = letter.getLastDetail().getChainType();
        var letterName = edit.getLetterName();
        var duplicateLetter = letterControl.getLetterByName(chainType, letterName);
        var letterSourceName = edit.getLetterSourceName();
        var contactListName = edit.getContactListName();

        if(duplicateLetter != null && !letter.equals(duplicateLetter)) {
            var chainTypeDetail = chainType.getLastDetail();

            addExecutionError(ExecutionErrors.DuplicateLetterName.name(),
                    chainTypeDetail.getChainKind().getLastDetail().getChainKindName(), chainTypeDetail.getChainTypeName(), letterName);
        }

        letterSource = letterSourceLogic.getLetterSourceByName(this, letterSourceName);
        contactList = contactListName == null ? null : contactListLogic.getContactListByName(this, contactListName);
    }

    @Override
    public void doUpdate(Letter letter) {
        var partyPK = getPartyPK();
        var letterDetailValue = letterControl.getLetterDetailValueForUpdate(letter);
        var letterDescription = letterControl.getLetterDescriptionForUpdate(letter, getPreferredLanguage());
        var description = edit.getDescription();

        letterDetailValue.setLetterName(edit.getLetterName());
        letterDetailValue.setLetterSourcePK(letterSource.getPrimaryKey());
        letterDetailValue.setContactListPK(contactList == null? null: contactList.getPrimaryKey());
        letterDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        letterDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        letterControl.updateLetterFromValue(letterDetailValue, partyPK);

        if(letterDescription == null && description != null) {
            letterControl.createLetterDescription(letter, getPreferredLanguage(), description, partyPK);
        } else if(letterDescription != null && description == null) {
            letterControl.deleteLetterDescription(letterDescription, partyPK);
        } else if(letterDescription != null && description != null) {
            var letterDescriptionValue = letterControl.getLetterDescriptionValue(letterDescription);

            letterDescriptionValue.setDescription(description);

            letterControl.updateLetterDescriptionFromValue(letterDescriptionValue, partyPK);
        }
    }

}

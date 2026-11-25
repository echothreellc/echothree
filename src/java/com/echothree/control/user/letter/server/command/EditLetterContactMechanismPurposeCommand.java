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

import com.echothree.control.user.letter.common.edit.LetterContactMechanismPurposeEdit;
import com.echothree.control.user.letter.common.edit.LetterEditFactory;
import com.echothree.control.user.letter.common.result.EditLetterContactMechanismPurposeResult;
import com.echothree.control.user.letter.common.result.LetterResultFactory;
import com.echothree.control.user.letter.common.spec.LetterContactMechanismPurposeSpec;
import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.contact.server.logic.ContactMechanismPurposeLogic;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.control.letter.server.logic.LetterContactMechanismPurposeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.model.data.letter.server.entity.Letter;
import com.echothree.model.data.letter.server.entity.LetterContactMechanismPurpose;
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
public class EditLetterContactMechanismPurposeCommand
        extends BaseAbstractEditCommand<LetterContactMechanismPurposeSpec, LetterContactMechanismPurposeEdit, EditLetterContactMechanismPurposeResult, LetterContactMechanismPurpose, Letter> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.LetterContactMechanismPurpose.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LetterName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Priority", FieldType.SIGNED_INTEGER, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ContactMechanismPurposeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    private ContactMechanismPurpose contactMechanismPurpose;

    @Inject
    LetterControl letterControl;

    @Inject
    ContactMechanismPurposeLogic contactMechanismPurposeLogic;

    @Inject
    LetterContactMechanismPurposeLogic letterContactMechanismPurposeLogic;

    /** Creates a new instance of EditLetterContactMechanismPurposeCommand */
    public EditLetterContactMechanismPurposeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditLetterContactMechanismPurposeResult getResult() {
        return LetterResultFactory.getEditLetterContactMechanismPurposeResult();
    }

    @Override
    public LetterContactMechanismPurposeEdit getEdit() {
        return LetterEditFactory.getLetterContactMechanismPurposeEdit();
    }

    @Override
    public LetterContactMechanismPurpose getEntity(EditLetterContactMechanismPurposeResult result) {
        return letterContactMechanismPurposeLogic.getLetterContactMechanismPurpose(this, spec.getChainKindName(), spec.getChainTypeName(),
                spec.getLetterName(), Integer.valueOf(spec.getPriority()), editModeToEntityPermission(editMode));
    }

    @Override
    public Letter getLockEntity(LetterContactMechanismPurpose letterContactMechanismPurpose) {
        return letterContactMechanismPurpose.getLastDetail().getLetter();
    }

    @Override
    public void fillInResult(EditLetterContactMechanismPurposeResult result, LetterContactMechanismPurpose letterContactMechanismPurpose) {
        result.setLetterContactMechanismPurpose(letterControl.getLetterContactMechanismPurposeTransfer(getUserVisit(), letterContactMechanismPurpose));
    }

    @Override
    public void doLock(LetterContactMechanismPurposeEdit edit, LetterContactMechanismPurpose letterContactMechanismPurpose) {
        edit.setContactMechanismPurposeName(letterContactMechanismPurpose.getLastDetail().getContactMechanismPurpose().getContactMechanismPurposeName());
    }

    @Override
    public void canUpdate(LetterContactMechanismPurpose letterContactMechanismPurpose) {
        contactMechanismPurpose = contactMechanismPurposeLogic.getContactMechanismPurposeByName(this, edit.getContactMechanismPurposeName());

        if(!hasExecutionErrors()) {
            var contactMechanismTypeName = contactMechanismPurpose.getContactMechanismType().getContactMechanismTypeName();

            if(!ContactMechanismTypes.EMAIL_ADDRESS.name().equals(contactMechanismTypeName)
                    && !ContactMechanismTypes.POSTAL_ADDRESS.name().equals(contactMechanismTypeName)) {
                addExecutionError(ExecutionErrors.InvalidContactMechanismType.name(), contactMechanismTypeName);
            }
        }
    }

    @Override
    public void doUpdate(LetterContactMechanismPurpose letterContactMechanismPurpose) {
        var letterContactMechanismPurposeDetailValue = letterControl.getLetterContactMechanismPurposeDetailValueForUpdate(letterContactMechanismPurpose);

        letterContactMechanismPurposeDetailValue.setContactMechanismPurposePK(contactMechanismPurpose.getPrimaryKey());

        letterControl.updateLetterContactMechanismPurposeFromValue(letterContactMechanismPurposeDetailValue, getPartyPK());
    }

}

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

import com.echothree.control.user.letter.common.edit.LetterDescriptionEdit;
import com.echothree.control.user.letter.common.edit.LetterEditFactory;
import com.echothree.control.user.letter.common.result.EditLetterDescriptionResult;
import com.echothree.control.user.letter.common.result.LetterResultFactory;
import com.echothree.control.user.letter.common.spec.LetterDescriptionSpec;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.control.letter.server.logic.LetterLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.letter.server.entity.Letter;
import com.echothree.model.data.letter.server.entity.LetterDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditLetterDescriptionCommand
        extends BaseEditCommand<LetterDescriptionSpec, LetterDescriptionEdit, EditLetterDescriptionResult, LetterDescription, Letter> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Letter.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LetterName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    LetterControl letterControl;

    @Inject
    LanguageLogic languageLogic;

    @Inject
    LetterLogic letterLogic;

    /** Creates a new instance of EditLetterDescriptionCommand */
    public EditLetterDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditLetterDescriptionResult getResult() {
        return LetterResultFactory.getEditLetterDescriptionResult();
    }

    @Override
    public LetterDescriptionEdit getEdit() {
        return LetterEditFactory.getLetterDescriptionEdit();
    }

    @Override
    public LetterDescription getEntity(EditLetterDescriptionResult result) {
        LetterDescription letterDescription = null;
        var chainKindName = spec.getChainKindName();
        var chainTypeName = spec.getChainTypeName();
        var letterName = spec.getLetterName();
        var languageIsoName = spec.getLanguageIsoName();
        var letter = letterLogic.getLetterByName(this, chainKindName, chainTypeName, letterName);
        var language = languageLogic.getLanguageByName(this, languageIsoName);

        if(!hasExecutionErrors()) {
            letterDescription = letterControl.getLetterDescription(letter, language,
                    editModeToEntityPermission(editMode));

            if(letterDescription == null) {
                addExecutionError(ExecutionErrors.UnknownLetterDescription.name(), chainKindName, chainTypeName, letterName, languageIsoName);
            }
        }

        return letterDescription;
    }

    @Override
    public Letter getLockEntity(LetterDescription letterDescription) {
        return letterDescription.getLetter();
    }

    @Override
    public void fillInResult(EditLetterDescriptionResult result, LetterDescription letterDescription) {
        result.setLetterDescription(letterControl.getLetterDescriptionTransfer(getUserVisit(), letterDescription));
    }

    @Override
    public void doLock(LetterDescriptionEdit edit, LetterDescription letterDescription) {
        edit.setDescription(letterDescription.getDescription());
    }

    @Override
    public void doUpdate(LetterDescription letterDescription) {
        var letterDescriptionValue = letterControl.getLetterDescriptionValue(letterDescription);

        letterDescriptionValue.setDescription(edit.getDescription());

        letterControl.updateLetterDescriptionFromValue(letterDescriptionValue, getPartyPK());
    }

}

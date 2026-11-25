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
import com.echothree.control.user.letter.common.edit.LetterSourceDescriptionEdit;
import com.echothree.control.user.letter.common.result.EditLetterSourceDescriptionResult;
import com.echothree.control.user.letter.common.result.LetterResultFactory;
import com.echothree.control.user.letter.common.spec.LetterSourceDescriptionSpec;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.control.letter.server.logic.LetterSourceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.letter.server.entity.LetterSource;
import com.echothree.model.data.letter.server.entity.LetterSourceDescription;
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
public class EditLetterSourceDescriptionCommand
        extends BaseAbstractEditCommand<LetterSourceDescriptionSpec, LetterSourceDescriptionEdit, EditLetterSourceDescriptionResult, LetterSourceDescription, LetterSource> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.LetterSource.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("LetterSourceName", FieldType.ENTITY_NAME, true, null, null),
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
    LetterSourceLogic letterSourceLogic;

    /** Creates a new instance of EditLetterSourceDescriptionCommand */
    public EditLetterSourceDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditLetterSourceDescriptionResult getResult() {
        return LetterResultFactory.getEditLetterSourceDescriptionResult();
    }

    @Override
    public LetterSourceDescriptionEdit getEdit() {
        return LetterEditFactory.getLetterSourceDescriptionEdit();
    }

    @Override
    public LetterSourceDescription getEntity(EditLetterSourceDescriptionResult result) {
        LetterSourceDescription letterSourceDescription = null;
        var letterSourceName = spec.getLetterSourceName();
        var languageIsoName = spec.getLanguageIsoName();
        var letterSource = letterSourceLogic.getLetterSourceByName(this, letterSourceName);
        var language = languageLogic.getLanguageByName(this, languageIsoName);

        if(!hasExecutionErrors()) {
            letterSourceDescription = letterControl.getLetterSourceDescription(letterSource, language,
                    editModeToEntityPermission(editMode));

            if(letterSourceDescription == null) {
                addExecutionError(ExecutionErrors.UnknownLetterSourceDescription.name(), letterSourceName, languageIsoName);
            }
        }

        return letterSourceDescription;
    }

    @Override
    public LetterSource getLockEntity(LetterSourceDescription letterSourceDescription) {
        return letterSourceDescription.getLetterSource();
    }

    @Override
    public void fillInResult(EditLetterSourceDescriptionResult result, LetterSourceDescription letterSourceDescription) {
        result.setLetterSourceDescription(letterControl.getLetterSourceDescriptionTransfer(getUserVisit(), letterSourceDescription));
    }

    @Override
    public void doLock(LetterSourceDescriptionEdit edit, LetterSourceDescription letterSourceDescription) {
        edit.setDescription(letterSourceDescription.getDescription());
    }

    @Override
    public void doUpdate(LetterSourceDescription letterSourceDescription) {
        var letterSourceDescriptionValue = letterControl.getLetterSourceDescriptionValue(letterSourceDescription);

        letterSourceDescriptionValue.setDescription(edit.getDescription());

        letterControl.updateLetterSourceDescriptionFromValue(letterSourceDescriptionValue, getPartyPK());
    }

}

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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.edit.TextDecorationDescriptionEdit;
import com.echothree.control.user.core.common.form.EditTextDecorationDescriptionForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditTextDecorationDescriptionResult;
import com.echothree.control.user.core.common.spec.TextDecorationDescriptionSpec;
import com.echothree.model.control.core.server.control.TextControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.TextDecoration;
import com.echothree.model.data.core.server.entity.TextDecorationDescription;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
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
public class EditTextDecorationDescriptionCommand
        extends BaseAbstractEditCommand<TextDecorationDescriptionSpec, TextDecorationDescriptionEdit, EditTextDecorationDescriptionResult, TextDecorationDescription, TextDecoration> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TextDecoration.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TextDecorationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditTextDecorationDescriptionCommand */
    public EditTextDecorationDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditTextDecorationDescriptionResult getResult() {
        return CoreResultFactory.getEditTextDecorationDescriptionResult();
    }

    @Override
    public TextDecorationDescriptionEdit getEdit() {
        return CoreEditFactory.getTextDecorationDescriptionEdit();
    }

    @Override
    public TextDecorationDescription getEntity(EditTextDecorationDescriptionResult result) {
        var textControl = Session.getModelController(TextControl.class);
        TextDecorationDescription textDecorationDescription = null;
        var textDecorationName = spec.getTextDecorationName();
        var textDecoration = textControl.getTextDecorationByName(textDecorationName);

        if(textDecoration != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    textDecorationDescription = textControl.getTextDecorationDescription(textDecoration, language);
                } else { // EditMode.UPDATE
                    textDecorationDescription = textControl.getTextDecorationDescriptionForUpdate(textDecoration, language);
                }

                if(textDecorationDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownTextDecorationDescription.name(), textDecorationName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTextDecorationName.name(), textDecorationName);
        }

        return textDecorationDescription;
    }

    @Override
    public TextDecoration getLockEntity(TextDecorationDescription textDecorationDescription) {
        return textDecorationDescription.getTextDecoration();
    }

    @Override
    public void fillInResult(EditTextDecorationDescriptionResult result, TextDecorationDescription textDecorationDescription) {
        var textControl = Session.getModelController(TextControl.class);

        result.setTextDecorationDescription(textControl.getTextDecorationDescriptionTransfer(getUserVisit(), textDecorationDescription));
    }

    @Override
    public void doLock(TextDecorationDescriptionEdit edit, TextDecorationDescription textDecorationDescription) {
        edit.setDescription(textDecorationDescription.getDescription());
    }

    @Override
    public void doUpdate(TextDecorationDescription textDecorationDescription) {
        var textControl = Session.getModelController(TextControl.class);
        var textDecorationDescriptionValue = textControl.getTextDecorationDescriptionValue(textDecorationDescription);
        textDecorationDescriptionValue.setDescription(edit.getDescription());

        textControl.updateTextDecorationDescriptionFromValue(textDecorationDescriptionValue, getPartyPK());
    }
    
}

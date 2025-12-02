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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.edit.TextTransformationDescriptionEdit;
import com.echothree.control.user.core.common.form.EditTextTransformationDescriptionForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditTextTransformationDescriptionResult;
import com.echothree.control.user.core.common.spec.TextTransformationDescriptionSpec;
import com.echothree.model.control.core.server.control.TextControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.TextTransformation;
import com.echothree.model.data.core.server.entity.TextTransformationDescription;
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
public class EditTextTransformationDescriptionCommand
        extends BaseAbstractEditCommand<TextTransformationDescriptionSpec, TextTransformationDescriptionEdit, EditTextTransformationDescriptionResult, TextTransformationDescription, TextTransformation> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TextTransformation.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TextTransformationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditTextTransformationDescriptionCommand */
    public EditTextTransformationDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditTextTransformationDescriptionResult getResult() {
        return CoreResultFactory.getEditTextTransformationDescriptionResult();
    }

    @Override
    public TextTransformationDescriptionEdit getEdit() {
        return CoreEditFactory.getTextTransformationDescriptionEdit();
    }

    @Override
    public TextTransformationDescription getEntity(EditTextTransformationDescriptionResult result) {
        var textControl = Session.getModelController(TextControl.class);
        TextTransformationDescription textTransformationDescription = null;
        var textTransformationName = spec.getTextTransformationName();
        var textTransformation = textControl.getTextTransformationByName(textTransformationName);

        if(textTransformation != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    textTransformationDescription = textControl.getTextTransformationDescription(textTransformation, language);
                } else { // EditMode.UPDATE
                    textTransformationDescription = textControl.getTextTransformationDescriptionForUpdate(textTransformation, language);
                }

                if(textTransformationDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownTextTransformationDescription.name(), textTransformationName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTextTransformationName.name(), textTransformationName);
        }

        return textTransformationDescription;
    }

    @Override
    public TextTransformation getLockEntity(TextTransformationDescription textTransformationDescription) {
        return textTransformationDescription.getTextTransformation();
    }

    @Override
    public void fillInResult(EditTextTransformationDescriptionResult result, TextTransformationDescription textTransformationDescription) {
        var textControl = Session.getModelController(TextControl.class);

        result.setTextTransformationDescription(textControl.getTextTransformationDescriptionTransfer(getUserVisit(), textTransformationDescription));
    }

    @Override
    public void doLock(TextTransformationDescriptionEdit edit, TextTransformationDescription textTransformationDescription) {
        edit.setDescription(textTransformationDescription.getDescription());
    }

    @Override
    public void doUpdate(TextTransformationDescription textTransformationDescription) {
        var textControl = Session.getModelController(TextControl.class);
        var textTransformationDescriptionValue = textControl.getTextTransformationDescriptionValue(textTransformationDescription);

        textTransformationDescriptionValue.setDescription(edit.getDescription());

        textControl.updateTextTransformationDescriptionFromValue(textTransformationDescriptionValue, getPartyPK());
    }
    
}

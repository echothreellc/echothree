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
import com.echothree.control.user.core.common.edit.EditorDescriptionEdit;
import com.echothree.control.user.core.common.form.EditEditorDescriptionForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEditorDescriptionResult;
import com.echothree.control.user.core.common.spec.EditorDescriptionSpec;
import com.echothree.model.control.core.server.control.EditorControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Editor;
import com.echothree.model.data.core.server.entity.EditorDescription;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditEditorDescriptionCommand
        extends BaseAbstractEditCommand<EditorDescriptionSpec, EditorDescriptionEdit, EditEditorDescriptionResult, EditorDescription, Editor> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Editor.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EditorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditEditorDescriptionCommand */
    public EditEditorDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditEditorDescriptionResult getResult() {
        return CoreResultFactory.getEditEditorDescriptionResult();
    }

    @Override
    public EditorDescriptionEdit getEdit() {
        return CoreEditFactory.getEditorDescriptionEdit();
    }

    @Override
    public EditorDescription getEntity(EditEditorDescriptionResult result) {
        var editorControl = Session.getModelController(EditorControl.class);
        EditorDescription editorDescription = null;
        var editorName = spec.getEditorName();
        var editor = editorControl.getEditorByName(editorName);

        if(editor != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    editorDescription = editorControl.getEditorDescription(editor, language);
                } else { // EditMode.UPDATE
                    editorDescription = editorControl.getEditorDescriptionForUpdate(editor, language);
                }

                if(editorDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownEditorDescription.name(), editorName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownEditorName.name(), editorName);
        }

        return editorDescription;
    }

    @Override
    public Editor getLockEntity(EditorDescription editorDescription) {
        return editorDescription.getEditor();
    }

    @Override
    public void fillInResult(EditEditorDescriptionResult result, EditorDescription editorDescription) {
        var editorControl = Session.getModelController(EditorControl.class);

        result.setEditorDescription(editorControl.getEditorDescriptionTransfer(getUserVisit(), editorDescription));
    }

    @Override
    public void doLock(EditorDescriptionEdit edit, EditorDescription editorDescription) {
        edit.setDescription(editorDescription.getDescription());
    }

    @Override
    public void doUpdate(EditorDescription editorDescription) {
        var editorControl = Session.getModelController(EditorControl.class);
        var editorDescriptionValue = editorControl.getEditorDescriptionValue(editorDescription);

        editorDescriptionValue.setDescription(edit.getDescription());

        editorControl.updateEditorDescriptionFromValue(editorDescriptionValue, getPartyPK());
    }
    
}

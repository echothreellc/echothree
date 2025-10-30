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
import com.echothree.control.user.core.common.edit.EditorEdit;
import com.echothree.control.user.core.common.form.EditEditorForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEditorResult;
import com.echothree.control.user.core.common.spec.EditorSpec;
import com.echothree.model.control.core.server.control.EditorControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Editor;
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
public class EditEditorCommand
        extends BaseAbstractEditCommand<EditorSpec, EditorEdit, EditEditorResult, Editor, Editor> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Editor.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EditorName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EditorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("HasDimensions", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("MinimumHeight", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("MinimumWidth", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("MaximumHeight", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("MaximumWidth", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("DefaultHeight", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("DefaultWidth", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditEditorCommand */
    public EditEditorCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditEditorResult getResult() {
        return CoreResultFactory.getEditEditorResult();
    }

    @Override
    public EditorEdit getEdit() {
        return CoreEditFactory.getEditorEdit();
    }

    @Override
    public Editor getEntity(EditEditorResult result) {
        var editorControl = Session.getModelController(EditorControl.class);
        Editor editor;
        var editorName = spec.getEditorName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            editor = editorControl.getEditorByName(editorName);
        } else { // EditMode.UPDATE
            editor = editorControl.getEditorByNameForUpdate(editorName);
        }

        if(editor == null) {
            addExecutionError(ExecutionErrors.UnknownEditorName.name(), editorName);
        }

        return editor;
    }

    @Override
    public Editor getLockEntity(Editor editor) {
        return editor;
    }

    @Override
    public void fillInResult(EditEditorResult result, Editor editor) {
        var editorControl = Session.getModelController(EditorControl.class);

        result.setEditor(editorControl.getEditorTransfer(getUserVisit(), editor));
    }

    @Override
    public void doLock(EditorEdit edit, Editor editor) {
        var editorControl = Session.getModelController(EditorControl.class);
        var editorDescription = editorControl.getEditorDescription(editor, getPreferredLanguage());
        var editorDetail = editor.getLastDetail();
        var minimumHeight = editorDetail.getMinimumHeight();
        var minimumWidth = editorDetail.getMinimumWidth();
        var maximumHeight = editorDetail.getMaximumHeight();
        var maximumWidth = editorDetail.getMaximumWidth();
        var defaultHeight = editorDetail.getDefaultHeight();
        var defaultWidth = editorDetail.getDefaultWidth();

        edit.setEditorName(editorDetail.getEditorName());
        edit.setHasDimensions(editorDetail.getHasDimensions().toString());
        edit.setMinimumHeight(minimumHeight == null ? null : minimumHeight.toString());
        edit.setMinimumWidth(minimumWidth == null ? null : minimumWidth.toString());
        edit.setMaximumHeight(maximumHeight == null ? null : maximumHeight.toString());
        edit.setMaximumWidth(maximumWidth == null ? null : maximumWidth.toString());
        edit.setDefaultHeight(defaultHeight == null ? null : defaultHeight.toString());
        edit.setDefaultWidth(defaultWidth == null ? null : defaultWidth.toString());
        edit.setIsDefault(editorDetail.getIsDefault().toString());
        edit.setSortOrder(editorDetail.getSortOrder().toString());

        if(editorDescription != null) {
            edit.setDescription(editorDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(Editor editor) {
        var editorControl = Session.getModelController(EditorControl.class);
        var editorName = edit.getEditorName();
        var duplicateEditor = editorControl.getEditorByName(editorName);

        if(duplicateEditor != null && !editor.equals(duplicateEditor)) {
            addExecutionError(ExecutionErrors.DuplicateEditorName.name(), editorName);
        }
    }

    @Override
    public void doUpdate(Editor editor) {
        var editorControl = Session.getModelController(EditorControl.class);
        var partyPK = getPartyPK();
        var editorDetailValue = editorControl.getEditorDetailValueForUpdate(editor);
        var editorDescription = editorControl.getEditorDescriptionForUpdate(editor, getPreferredLanguage());
        var strMinimumHeight = edit.getMinimumHeight();
        var strMinimumWidth = edit.getMinimumWidth();
        var strMaximumHeight = edit.getMaximumHeight();
        var strMaximumWidth = edit.getMaximumWidth();
        var strDefaultHeight = edit.getDefaultHeight();
        var strDefaultWidth = edit.getDefaultWidth();
        var description = edit.getDescription();

        editorDetailValue.setEditorName(edit.getEditorName());
        editorDetailValue.setHasDimensions(Boolean.valueOf(edit.getHasDimensions()));
        editorDetailValue.setMinimumHeight(strMinimumHeight == null ? null : Integer.valueOf(strMinimumHeight));
        editorDetailValue.setMinimumWidth(strMinimumWidth == null ? null : Integer.valueOf(strMinimumWidth));
        editorDetailValue.setMaximumHeight(strMaximumHeight == null ? null : Integer.valueOf(strMaximumHeight));
        editorDetailValue.setMaximumWidth(strMaximumWidth == null ? null : Integer.valueOf(strMaximumWidth));
        editorDetailValue.setDefaultHeight(strDefaultHeight == null ? null : Integer.valueOf(strDefaultHeight));
        editorDetailValue.setDefaultWidth(strDefaultWidth == null ? null : Integer.valueOf(strDefaultWidth));
        editorDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        editorDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        editorControl.updateEditorFromValue(editorDetailValue, partyPK);

        if(editorDescription == null && description != null) {
            editorControl.createEditorDescription(editor, getPreferredLanguage(), description, partyPK);
        } else {
            if(editorDescription != null && description == null) {
                editorControl.deleteEditorDescription(editorDescription, partyPK);
            } else {
                if(editorDescription != null && description != null) {
                    var editorDescriptionValue = editorControl.getEditorDescriptionValue(editorDescription);

                    editorDescriptionValue.setDescription(description);
                    editorControl.updateEditorDescriptionFromValue(editorDescriptionValue, partyPK);
                }
            }
        }
    }
    
}

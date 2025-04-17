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

import com.echothree.control.user.core.common.edit.ApplicationEditorEdit;
import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.form.EditApplicationEditorForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditApplicationEditorResult;
import com.echothree.control.user.core.common.spec.ApplicationEditorSpec;
import com.echothree.model.control.core.server.logic.ApplicationLogic;
import com.echothree.model.control.core.server.logic.EditorLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.ApplicationEditor;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditApplicationEditorCommand
        extends BaseAbstractEditCommand<ApplicationEditorSpec, ApplicationEditorEdit, EditApplicationEditorResult, ApplicationEditor, ApplicationEditor> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ApplicationEditor.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ApplicationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EditorName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditApplicationEditorCommand */
    public EditApplicationEditorCommand(UserVisitPK userVisitPK, EditApplicationEditorForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditApplicationEditorResult getResult() {
        return CoreResultFactory.getEditApplicationEditorResult();
    }

    @Override
    public ApplicationEditorEdit getEdit() {
        return CoreEditFactory.getApplicationEditorEdit();
    }

    @Override
    public ApplicationEditor getEntity(EditApplicationEditorResult result) {
        ApplicationEditor applicationEditor = null;
        var applicationName = spec.getApplicationName();
        var application = ApplicationLogic.getInstance().getApplicationByName(this, applicationName);
        
        if(!hasExecutionErrors()) {
            var editorName = spec.getEditorName();
            var editor = EditorLogic.getInstance().getEditorByName(this, editorName);
            
            if(!hasExecutionErrors()) {
                var coreControl = getCoreControl();
                
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    applicationEditor = coreControl.getApplicationEditor(application, editor);
                } else { // EditMode.UPDATE
                    applicationEditor = coreControl.getApplicationEditorForUpdate(application, editor);
                }

                if(applicationEditor == null) {
                    addExecutionError(ExecutionErrors.UnknownApplicationEditor.name(), applicationName, editorName);
                }
            }
        }

        return applicationEditor;
    }

    @Override
    public ApplicationEditor getLockEntity(ApplicationEditor applicationEditor) {
        return applicationEditor;
    }

    @Override
    public void fillInResult(EditApplicationEditorResult result, ApplicationEditor applicationEditor) {
        var coreControl = getCoreControl();

        result.setApplicationEditor(coreControl.getApplicationEditorTransfer(getUserVisit(), applicationEditor));
    }

    @Override
    public void doLock(ApplicationEditorEdit edit, ApplicationEditor applicationEditor) {
        var applicationEditorDetail = applicationEditor.getLastDetail();

        edit.setIsDefault(applicationEditorDetail.getIsDefault().toString());
        edit.setSortOrder(applicationEditorDetail.getSortOrder().toString());
    }

    @Override
    public void doUpdate(ApplicationEditor applicationEditor) {
        var coreControl = getCoreControl();
        var partyPK = getPartyPK();
        var applicationEditorDetailValue = coreControl.getApplicationEditorDetailValueForUpdate(applicationEditor);

        applicationEditorDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        applicationEditorDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        coreControl.updateApplicationEditorFromValue(applicationEditorDetailValue, partyPK);
    }
    
}

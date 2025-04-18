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

import com.echothree.control.user.core.common.edit.ApplicationEditorUseEdit;
import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.form.EditApplicationEditorUseForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditApplicationEditorUseResult;
import com.echothree.control.user.core.common.spec.ApplicationEditorUseSpec;
import com.echothree.model.control.core.server.control.ApplicationControl;
import com.echothree.model.control.core.server.control.EditorControl;
import com.echothree.model.control.core.server.logic.ApplicationLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Application;
import com.echothree.model.data.core.server.entity.ApplicationEditor;
import com.echothree.model.data.core.server.entity.ApplicationEditorUse;
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

public class EditApplicationEditorUseCommand
        extends BaseAbstractEditCommand<ApplicationEditorUseSpec, ApplicationEditorUseEdit, EditApplicationEditorUseResult, ApplicationEditorUse, ApplicationEditorUse> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ApplicationEditorUse.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ApplicationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ApplicationEditorUseName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ApplicationEditorUseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DefaultEditorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultHeight", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("DefaultWidth", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditApplicationEditorUseCommand */
    public EditApplicationEditorUseCommand(UserVisitPK userVisitPK, EditApplicationEditorUseForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditApplicationEditorUseResult getResult() {
        return CoreResultFactory.getEditApplicationEditorUseResult();
    }

    @Override
    public ApplicationEditorUseEdit getEdit() {
        return CoreEditFactory.getApplicationEditorUseEdit();
    }

    Application application;
    
    @Override
    public ApplicationEditorUse getEntity(EditApplicationEditorUseResult result) {
        ApplicationEditorUse applicationEditorUse = null;
        var applicationName = spec.getApplicationName();
        
        application = ApplicationLogic.getInstance().getApplicationByName(this, applicationName);
        
        if(!hasExecutionErrors()) {
            var applicationControl = Session.getModelController(ApplicationControl.class);
            var applicationEditorUseName = spec.getApplicationEditorUseName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                applicationEditorUse = applicationControl.getApplicationEditorUseByName(application, applicationEditorUseName);
            } else { // EditMode.UPDATE
                applicationEditorUse = applicationControl.getApplicationEditorUseByNameForUpdate(application, applicationEditorUseName);
            }

            if(applicationEditorUse == null) {
                addExecutionError(ExecutionErrors.UnknownApplicationEditorUseName.name(), applicationName, applicationEditorUseName);
            }
        }
        
        return applicationEditorUse;
    }

    @Override
    public ApplicationEditorUse getLockEntity(ApplicationEditorUse applicationEditorUse) {
        return applicationEditorUse;
    }

    @Override
    public void fillInResult(EditApplicationEditorUseResult result, ApplicationEditorUse applicationEditorUse) {
        var applicationControl = Session.getModelController(ApplicationControl.class);

        result.setApplicationEditorUse(applicationControl.getApplicationEditorUseTransfer(getUserVisit(), applicationEditorUse));
    }

    ApplicationEditor applicationEditor;
    
    @Override
    public void doLock(ApplicationEditorUseEdit edit, ApplicationEditorUse applicationEditorUse) {
        var applicationControl = Session.getModelController(ApplicationControl.class);
        var applicationEditorUseDescription = applicationControl.getApplicationEditorUseDescription(applicationEditorUse, getPreferredLanguage());
        var applicationEditorUseDetail = applicationEditorUse.getLastDetail();
        var defaultHeight = applicationEditorUseDetail.getDefaultHeight();
        var defaultWidth = applicationEditorUseDetail.getDefaultWidth();

        applicationEditor = applicationEditorUseDetail.getDefaultApplicationEditor();
       
        edit.setApplicationEditorUseName(applicationEditorUseDetail.getApplicationEditorUseName());
        edit.setDefaultEditorName(applicationEditor == null ? null : applicationEditor.getLastDetail().getEditor().getLastDetail().getEditorName());
        edit.setDefaultHeight(defaultHeight == null ? null : defaultHeight.toString());
        edit.setDefaultWidth(defaultWidth == null ? null : defaultWidth.toString());
        edit.setIsDefault(applicationEditorUseDetail.getIsDefault().toString());
        edit.setSortOrder(applicationEditorUseDetail.getSortOrder().toString());

        if(applicationEditorUseDescription != null) {
            edit.setDescription(applicationEditorUseDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(ApplicationEditorUse applicationEditorUse) {
        var applicationControl = Session.getModelController(ApplicationControl.class);
        var applicationEditorUseName = edit.getApplicationEditorUseName();
        var duplicateApplicationEditorUse = applicationControl.getApplicationEditorUseByName(application, applicationEditorUseName);

        if(duplicateApplicationEditorUse != null && !applicationEditorUse.equals(duplicateApplicationEditorUse)) {
            addExecutionError(ExecutionErrors.DuplicateApplicationEditorUseName.name(), applicationEditorUseName);
        } else {
            var editorControl = Session.getModelController(EditorControl.class);
            var defaultEditorName = edit.getDefaultEditorName();
            var editor = defaultEditorName == null ? null : editorControl.getEditorByName(defaultEditorName);

            if(defaultEditorName == null || editor != null) {
                applicationEditor = editor == null ? null : ApplicationLogic.getInstance().getApplicationEditor(this, application, editor);
            }
        }
    }

    @Override
    public void doUpdate(ApplicationEditorUse applicationEditorUse) {
        var applicationControl = Session.getModelController(ApplicationControl.class);
        var partyPK = getPartyPK();
        var applicationEditorUseDetailValue = applicationControl.getApplicationEditorUseDetailValueForUpdate(applicationEditorUse);
        var applicationEditorUseDescription = applicationControl.getApplicationEditorUseDescriptionForUpdate(applicationEditorUse, getPreferredLanguage());
        var strDefaultHeight = edit.getDefaultHeight();
        var defaultHeight = strDefaultHeight == null ? null : Integer.valueOf(strDefaultHeight);
        var strDefaultWidth = edit.getDefaultWidth();
        var defaultWidth = strDefaultWidth == null ? null : Integer.valueOf(strDefaultWidth);
        var description = edit.getDescription();

        applicationEditorUseDetailValue.setApplicationEditorUseName(edit.getApplicationEditorUseName());
        applicationEditorUseDetailValue.setDefaultApplicationEditorPK(applicationEditor == null ? null : applicationEditor.getPrimaryKey());
        applicationEditorUseDetailValue.setDefaultHeight(defaultHeight);
        applicationEditorUseDetailValue.setDefaultWidth(defaultWidth);
        applicationEditorUseDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        applicationEditorUseDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        applicationControl.updateApplicationEditorUseFromValue(applicationEditorUseDetailValue, partyPK);

        if(applicationEditorUseDescription == null && description != null) {
            applicationControl.createApplicationEditorUseDescription(applicationEditorUse, getPreferredLanguage(), description, partyPK);
        } else {
            if(applicationEditorUseDescription != null && description == null) {
                applicationControl.deleteApplicationEditorUseDescription(applicationEditorUseDescription, partyPK);
            } else {
                if(applicationEditorUseDescription != null && description != null) {
                    var applicationEditorUseDescriptionValue = applicationControl.getApplicationEditorUseDescriptionValue(applicationEditorUseDescription);

                    applicationEditorUseDescriptionValue.setDescription(description);
                    applicationControl.updateApplicationEditorUseDescriptionFromValue(applicationEditorUseDescriptionValue, partyPK);
                }
            }
        }
    }
    
}

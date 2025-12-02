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

import com.echothree.control.user.core.common.form.CreateApplicationEditorUseForm;
import com.echothree.model.control.core.server.control.ApplicationControl;
import com.echothree.model.control.core.server.control.EditorControl;
import com.echothree.model.control.core.server.logic.ApplicationLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class CreateApplicationEditorUseCommand
        extends BaseSimpleCommand<CreateApplicationEditorUseForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ApplicationEditorUse.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ApplicationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ApplicationEditorUseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DefaultEditorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultHeight", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("DefaultWidth", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateApplicationEditorUseCommand */
    public CreateApplicationEditorUseCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var applicationName = form.getApplicationName();
        var application = ApplicationLogic.getInstance().getApplicationByName(this, applicationName);
        
        if(!hasExecutionErrors()) {
            var applicationControl = Session.getModelController(ApplicationControl.class);
            var applicationEditorUseName = form.getApplicationEditorUseName();
            var applicationEditorUse = applicationControl.getApplicationEditorUseByName(application, applicationEditorUseName);

            if(applicationEditorUse == null) {
                var editorControl = Session.getModelController(EditorControl.class);
                var defaultEditorName = form.getDefaultEditorName();
                var editor = defaultEditorName == null ? null : editorControl.getEditorByName(defaultEditorName);
                
                if(defaultEditorName == null || editor != null) {
                    var applicationEditor = editor == null ? null : ApplicationLogic.getInstance().getApplicationEditor(this, application, editor);
                    
                    if(!hasExecutionErrors()) {
                        var strDefaultHeight = form.getDefaultHeight();
                        var defaultHeight = strDefaultHeight == null ? null : Integer.valueOf(strDefaultHeight);
                        var strDefaultWidth = form.getDefaultWidth();
                        var defaultWidth = strDefaultWidth == null ? null : Integer.valueOf(strDefaultWidth);
                        var isDefault = Boolean.valueOf(form.getIsDefault());
                        var sortOrder = Integer.valueOf(form.getSortOrder());
                        var createdBy = getPartyPK();
                        var description = form.getDescription();
                        
                        applicationEditorUse = applicationControl.createApplicationEditorUse(application, applicationEditorUseName, applicationEditor, defaultHeight,
                                defaultWidth, isDefault, sortOrder, createdBy);
                        
                        if(description != null) {
                            applicationControl.createApplicationEditorUseDescription(applicationEditorUse, getPreferredLanguage(), description, createdBy);
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownDefaultEditorName.name(), defaultEditorName);
                }
            } else {
                addExecutionError(ExecutionErrors.DuplicateApplicationEditorUseName.name(), applicationName, applicationEditorUseName);
            }
        }
        
        return null;
    }
    
}

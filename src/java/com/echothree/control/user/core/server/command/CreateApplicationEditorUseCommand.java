// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.core.remote.form.CreateApplicationEditorUseForm;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.core.server.logic.ApplicationLogic;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Application;
import com.echothree.model.data.core.server.entity.ApplicationEditor;
import com.echothree.model.data.core.server.entity.ApplicationEditorUse;
import com.echothree.model.data.core.server.entity.Editor;
import com.echothree.model.data.party.remote.pk.PartyPK;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateApplicationEditorUseCommand
        extends BaseSimpleCommand<CreateApplicationEditorUseForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
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
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateApplicationEditorUseCommand */
    public CreateApplicationEditorUseCommand(UserVisitPK userVisitPK, CreateApplicationEditorUseForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        String applicationName = form.getApplicationName();
        Application application = ApplicationLogic.getInstance().getApplicationByName(this, applicationName);
        
        if(!hasExecutionErrors()) {
            CoreControl coreControl = getCoreControl();
            String applicationEditorUseName = form.getApplicationEditorUseName();
            ApplicationEditorUse applicationEditorUse = coreControl.getApplicationEditorUseByName(application, applicationEditorUseName);

            if(applicationEditorUse == null) {
                String defaultEditorName = form.getDefaultEditorName();
                Editor editor = defaultEditorName == null ? null : coreControl.getEditorByName(defaultEditorName);
                
                if(defaultEditorName == null || editor != null) {
                    ApplicationEditor applicationEditor = editor == null ? null : ApplicationLogic.getInstance().getApplicationEditor(this, application, editor);
                    
                    if(!hasExecutionErrors()) {
                        String strDefaultHeight = form.getDefaultHeight();
                        Integer defaultHeight = strDefaultHeight == null ? null : Integer.valueOf(strDefaultHeight);
                        String strDefaultWidth = form.getDefaultWidth();
                        Integer defaultWidth = strDefaultWidth == null ? null : Integer.valueOf(strDefaultWidth);
                        Boolean isDefault = Boolean.valueOf(form.getIsDefault());
                        Integer sortOrder = Integer.valueOf(form.getSortOrder());
                        PartyPK createdBy = getPartyPK();
                        String description = form.getDescription();
                        
                        applicationEditorUse = coreControl.createApplicationEditorUse(application, applicationEditorUseName, applicationEditor, defaultHeight,
                                defaultWidth, isDefault, sortOrder, createdBy);
                        
                        if(description != null) {
                            coreControl.createApplicationEditorUseDescription(applicationEditorUse, getPreferredLanguage(), description, createdBy);
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

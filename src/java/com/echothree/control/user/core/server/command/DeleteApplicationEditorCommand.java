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

import com.echothree.control.user.core.common.form.DeleteApplicationEditorForm;
import com.echothree.model.control.core.server.control.ApplicationControl;
import com.echothree.model.control.core.server.logic.ApplicationLogic;
import com.echothree.model.control.core.server.logic.EditorLogic;
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

public class DeleteApplicationEditorCommand
        extends BaseSimpleCommand<DeleteApplicationEditorForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ApplicationEditor.name(), SecurityRoles.Delete.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ApplicationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EditorName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteApplicationEditorCommand */
    public DeleteApplicationEditorCommand(UserVisitPK userVisitPK, DeleteApplicationEditorForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var applicationName = form.getApplicationName();
        var application = ApplicationLogic.getInstance().getApplicationByName(this, applicationName);
        
        if(!hasExecutionErrors()) {
            var editorName = form.getEditorName();
            var editor = EditorLogic.getInstance().getEditorByName(this, editorName);
            
            if(!hasExecutionErrors()) {
                var applicationControl = Session.getModelController(ApplicationControl.class);
                var applicationEditor = applicationControl.getApplicationEditorForUpdate(application, editor);
                
                if(applicationEditor != null) {
                    applicationControl.deleteApplicationEditor(applicationEditor, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.UnknownApplicationEditor.name(), applicationName, editorName);
                }
            }
        }
        
        return null;
    }
    
}

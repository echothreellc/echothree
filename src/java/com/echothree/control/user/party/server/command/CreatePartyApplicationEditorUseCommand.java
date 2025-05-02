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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.form.CreatePartyApplicationEditorUseForm;
import com.echothree.model.control.party.server.control.PartyApplicationEditorUseControl;
import com.echothree.model.control.core.server.logic.ApplicationLogic;
import com.echothree.model.control.core.server.logic.EditorLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.PartyLogic;
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

public class CreatePartyApplicationEditorUseCommand
        extends BaseSimpleCommand<CreatePartyApplicationEditorUseForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyApplicationEditorUse.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ApplicationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ApplicationEditorUseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EditorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PreferredHeight", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("PreferredWidth", FieldType.UNSIGNED_INTEGER, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreatePartyApplicationEditorUseCommand */
    public CreatePartyApplicationEditorUseCommand(UserVisitPK userVisitPK, CreatePartyApplicationEditorUseForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var partyName = form.getPartyName();
        var party = PartyLogic.getInstance().getPartyByName(this, partyName);
        
        if(!hasExecutionErrors()) {
            var applicationName = form.getApplicationName();
            var application = ApplicationLogic.getInstance().getApplicationByName(this, applicationName);
            
            if(!hasExecutionErrors()) {
                var applicationEditorUseName = form.getApplicationEditorUseName();
                var applicationEditorUse = ApplicationLogic.getInstance().getApplicationEditorUseByName(this, application, applicationEditorUseName);
                
                if(!hasExecutionErrors()) {
                    var partyApplicationEditorUseControl = Session.getModelController(PartyApplicationEditorUseControl.class);
                    var partyApplicationEditorUse = partyApplicationEditorUseControl.getPartyApplicationEditorUse(party, applicationEditorUse);
                    
                    if(partyApplicationEditorUse == null) {
                        var editorName = form.getEditorName();
                        var editor = editorName == null ? null : EditorLogic.getInstance().getEditorByName(this, editorName);
                        
                        if(!hasExecutionErrors()) {
                            var applicationEditor = editor == null ? null : ApplicationLogic.getInstance().getApplicationEditor(this, application, editor);
                            
                            if(!hasExecutionErrors()) {
                                var strPreferredHeight = form.getPreferredHeight();
                                var preferredHeight = strPreferredHeight == null ? null : Integer.valueOf(strPreferredHeight);
                                var strPreferredWidth = form.getPreferredWidth();
                                var preferredWidth = strPreferredWidth == null ? null : Integer.valueOf(strPreferredWidth);

                                partyApplicationEditorUseControl.createPartyApplicationEditorUse(party, applicationEditorUse, applicationEditor, preferredHeight, preferredWidth,
                                        getPartyPK());
                            }
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicatePartyApplicationEditorUse.name(), partyName, applicationName, applicationEditorUseName);
                    }
                }
            }
        }
        
        return null;
    }
    
}

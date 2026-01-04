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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.form.GetPartyApplicationEditorUseForm;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.ApplicationControl;
import com.echothree.model.control.core.server.logic.ApplicationLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyApplicationEditorUseControl;
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
import javax.enterprise.context.Dependent;

@Dependent
public class GetPartyApplicationEditorUseCommand
        extends BaseSimpleCommand<GetPartyApplicationEditorUseForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyApplicationEditorUse.name(), SecurityRoles.Review.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ApplicationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ApplicationEditorUseName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetPartyApplicationEditorUseCommand */
    public GetPartyApplicationEditorUseCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var result = PartyResultFactory.getGetPartyApplicationEditorUseResult();
        var partyName = form.getPartyName();
        var party = partyName == null ? getParty() : PartyLogic.getInstance().getPartyByName(this, partyName);
        
        if(!hasExecutionErrors()) {
            var applicationName = form.getApplicationName();
            var application = ApplicationLogic.getInstance().getApplicationByName(this, applicationName);
            
            if(!hasExecutionErrors()) {
                var applicationEditorUseName = form.getApplicationEditorUseName();
                var applicationEditorUse = ApplicationLogic.getInstance().getApplicationEditorUseByName(this, application, applicationEditorUseName);
                
                if(!hasExecutionErrors()) {
                    var partyApplicationEditorUseControl = Session.getModelController(PartyApplicationEditorUseControl.class);
                    var partyApplicationEditorUse = partyApplicationEditorUseControl.getPartyApplicationEditorUse(party, applicationEditorUse);
                    var partyPK = getPartyPK();
                    
                    if(partyApplicationEditorUse == null && partyName == null) {
                        partyApplicationEditorUse = partyApplicationEditorUseControl.createPartyApplicationEditorUse(party, applicationEditorUse, null, null, null, partyPK);
                    }
                    
                    if(partyApplicationEditorUse != null) {
                        var userVisit = getUserVisit();
                        var partyApplicationEditorUseTransfer = partyApplicationEditorUseControl.getPartyApplicationEditorUseTransfer(userVisit, partyApplicationEditorUse);
                        
                        // Fill in a few defaults in the TO of the Party is requesting this for themselves.
                        if(partyName == null) {
                            var applicationEditorUseDetail = applicationEditorUse.getLastDetail();
                            var preferredHeight = partyApplicationEditorUseTransfer.getPreferredHeight();
                            var preferredWidth = partyApplicationEditorUseTransfer.getPreferredWidth();
                            
                            if(partyApplicationEditorUseTransfer.getApplicationEditor() == null) {
                                var applicationControl = Session.getModelController(ApplicationControl.class);
                                var applicationEditor = applicationEditorUseDetail.getDefaultApplicationEditor();
                                        
                                if(applicationEditor == null) {
                                    applicationEditor = applicationControl.getDefaultApplicationEditor(application);
                                    
                                    if(applicationEditor == null) {
                                        addExecutionError(ExecutionErrors.UnknownDefaultApplicationEditor.name(), applicationName);
                                    }
                                }
                                
                                if(!hasExecutionErrors()) {
                                    partyApplicationEditorUseTransfer.setApplicationEditor(applicationControl.getApplicationEditorTransfer(userVisit, applicationEditor));
                                }
                            }

                            if(preferredHeight == null || preferredWidth == null) {
                                if(preferredHeight == null) {
                                    preferredHeight = applicationEditorUseDetail.getDefaultHeight();
                                }

                                if(preferredWidth == null) {
                                    preferredWidth = applicationEditorUseDetail.getDefaultWidth();
                                }

                                if(preferredHeight == null || preferredWidth == null) {
                                    var editor = partyApplicationEditorUseTransfer.getApplicationEditor().getEditor();

                                    if(preferredHeight == null) {
                                        preferredHeight = editor.getDefaultHeight();
                                    }

                                    if(preferredWidth == null) {
                                        preferredWidth = editor.getDefaultWidth();
                                    }
                                }

                                partyApplicationEditorUseTransfer.setPreferredHeight(preferredHeight);
                                partyApplicationEditorUseTransfer.setPreferredWidth(preferredWidth);
                            }
                        }
                        
                        result.setPartyApplicationEditorUse(partyApplicationEditorUseTransfer);

                        sendEvent(partyApplicationEditorUse.getPrimaryKey(), EventTypes.READ, null, null, partyPK);
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPartyApplicationEditorUse.name(), partyName, applicationName, applicationEditorUseName);
                    }
                }
            }
        }
        
        return result;
    }
    
}

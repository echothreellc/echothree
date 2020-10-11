// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.core.common.form.GetPartyApplicationEditorUseForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.GetPartyApplicationEditorUseResult;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.transfer.EditorTransfer;
import com.echothree.model.control.core.common.transfer.PartyApplicationEditorUseTransfer;
import com.echothree.model.control.core.server.logic.ApplicationLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Application;
import com.echothree.model.data.core.server.entity.ApplicationEditor;
import com.echothree.model.data.core.server.entity.ApplicationEditorUse;
import com.echothree.model.data.core.server.entity.ApplicationEditorUseDetail;
import com.echothree.model.data.core.server.entity.PartyApplicationEditorUse;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    public GetPartyApplicationEditorUseCommand(UserVisitPK userVisitPK, GetPartyApplicationEditorUseForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        GetPartyApplicationEditorUseResult result = CoreResultFactory.getGetPartyApplicationEditorUseResult();
        String partyName = form.getPartyName();
        Party party = partyName == null ? getParty() : PartyLogic.getInstance().getPartyByName(this, partyName);
        
        if(!hasExecutionErrors()) {
            String applicationName = form.getApplicationName();
            Application application = ApplicationLogic.getInstance().getApplicationByName(this, applicationName);
            
            if(!hasExecutionErrors()) {
                String applicationEditorUseName = form.getApplicationEditorUseName();
                ApplicationEditorUse applicationEditorUse = ApplicationLogic.getInstance().getApplicationEditorUseByName(this, application, applicationEditorUseName);
                
                if(!hasExecutionErrors()) {
                    var coreControl = getCoreControl();
                    PartyApplicationEditorUse partyApplicationEditorUse = coreControl.getPartyApplicationEditorUse(party, applicationEditorUse);
                    var partyPK = getPartyPK();
                    
                    if(partyApplicationEditorUse == null && partyName == null) {
                        partyApplicationEditorUse = coreControl.createPartyApplicationEditorUse(party, applicationEditorUse, null, null, null, partyPK);
                    }
                    
                    if(partyApplicationEditorUse != null) {
                        UserVisit userVisit = getUserVisit();
                        PartyApplicationEditorUseTransfer partyApplicationEditorUseTransfer = coreControl.getPartyApplicationEditorUseTransfer(userVisit, partyApplicationEditorUse);
                        
                        // Fill in a few defaults in the TO of the Party is requesting this for themselves.
                        if(partyName == null) {
                            ApplicationEditorUseDetail applicationEditorUseDetail = applicationEditorUse.getLastDetail();
                            Integer preferredHeight = partyApplicationEditorUseTransfer.getPreferredHeight();
                            Integer preferredWidth = partyApplicationEditorUseTransfer.getPreferredWidth();
                            
                            if(partyApplicationEditorUseTransfer.getApplicationEditor() == null) {
                                ApplicationEditor applicationEditor = applicationEditorUseDetail.getDefaultApplicationEditor();
                                        
                                if(applicationEditor == null) {
                                    applicationEditor = coreControl.getDefaultApplicationEditor(application);
                                    
                                    if(applicationEditor == null) {
                                        addExecutionError(ExecutionErrors.UnknownDefaultApplicationEditor.name(), applicationName);
                                    }
                                }
                                
                                if(!hasExecutionErrors()) {
                                    partyApplicationEditorUseTransfer.setApplicationEditor(coreControl.getApplicationEditorTransfer(userVisit, applicationEditor));
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
                                    EditorTransfer editor = partyApplicationEditorUseTransfer.getApplicationEditor().getEditor();

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

                        sendEventUsingNames(partyApplicationEditorUse.getPrimaryKey(), EventTypes.READ.name(), null, null, partyPK);
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPartyApplicationEditorUse.name(), partyName, applicationName, applicationEditorUseName);
                    }
                }
            }
        }
        
        return result;
    }
    
}

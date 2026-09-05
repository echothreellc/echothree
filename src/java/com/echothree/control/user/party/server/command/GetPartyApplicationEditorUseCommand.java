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
import com.echothree.model.data.party.server.entity.PartyApplicationEditorUse;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPartyApplicationEditorUseCommand
        extends BaseSingleEntityCommand<PartyApplicationEditorUse, GetPartyApplicationEditorUseForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyApplicationEditorUse.name(), SecurityRoles.Review.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ApplicationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ApplicationEditorUseName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    ApplicationControl applicationControl;

    @Inject
    PartyApplicationEditorUseControl partyApplicationEditorUseControl;

    @Inject
    ApplicationLogic applicationLogic;

    @Inject
    PartyLogic partyLogic;

    /** Creates a new instance of GetPartyApplicationEditorUseCommand */
    public GetPartyApplicationEditorUseCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected PartyApplicationEditorUse getEntity() {
        PartyApplicationEditorUse partyApplicationEditorUse = null;
        var partyName = form.getPartyName();
        var party = partyName == null ? getParty() : partyLogic.getPartyByName(this, partyName);
        
        if(!hasExecutionErrors()) {
            var applicationName = form.getApplicationName();
            var application = applicationLogic.getApplicationByName(this, applicationName);
            
            if(!hasExecutionErrors()) {
                var applicationEditorUseName = form.getApplicationEditorUseName();
                var applicationEditorUse = applicationLogic.getApplicationEditorUseByName(this, application, applicationEditorUseName);
                
                if(!hasExecutionErrors()) {
                    var partyPK = getPartyPK();

                    partyApplicationEditorUse = partyApplicationEditorUseControl.getPartyApplicationEditorUse(party, applicationEditorUse);

                    if(partyApplicationEditorUse == null && partyName == null) {
                        partyApplicationEditorUse = partyApplicationEditorUseControl.createPartyApplicationEditorUse(party, applicationEditorUse, null, null, null, partyPK);
                    }
                    
                    if(partyApplicationEditorUse != null) {
                        sendEvent(partyApplicationEditorUse.getPrimaryKey(), EventTypes.READ, null, null, partyPK);
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPartyApplicationEditorUse.name(), partyName, applicationName, applicationEditorUseName);
                    }
                }
            }
        }

        return partyApplicationEditorUse;
    }

    @Override
    protected BaseResult getResult(PartyApplicationEditorUse partyApplicationEditorUse) {
        var result = PartyResultFactory.getGetPartyApplicationEditorUseResult();

        if(partyApplicationEditorUse != null) {
            var userVisit = getUserVisit();
            var partyApplicationEditorUseTransfer = partyApplicationEditorUseControl.getPartyApplicationEditorUseTransfer(userVisit, partyApplicationEditorUse);

            // Fill in a few defaults in the TO if the Party is requesting this for themselves.
            if(form.getPartyName() == null) {
                var applicationEditorUse = partyApplicationEditorUse.getLastDetail().getApplicationEditorUse();
                var applicationEditorUseDetail = applicationEditorUse.getLastDetail();
                var application = applicationEditorUseDetail.getApplication();
                var preferredHeight = partyApplicationEditorUseTransfer.getPreferredHeight();
                var preferredWidth = partyApplicationEditorUseTransfer.getPreferredWidth();

                if(partyApplicationEditorUseTransfer.getApplicationEditor() == null) {
                    var applicationEditor = applicationEditorUseDetail.getDefaultApplicationEditor();

                    if(applicationEditor == null) {
                        applicationEditor = applicationControl.getDefaultApplicationEditor(application);

                        if(applicationEditor == null) {
                            addExecutionError(ExecutionErrors.UnknownDefaultApplicationEditor.name(), form.getApplicationName());
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
        }
        
        return result;
    }
    
}

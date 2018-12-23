// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.control.user.core.common.edit.ServerDescriptionEdit;
import com.echothree.control.user.core.common.form.EditServerDescriptionForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditServerDescriptionResult;
import com.echothree.control.user.core.common.spec.ServerDescriptionSpec;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Server;
import com.echothree.model.data.core.server.entity.ServerDescription;
import com.echothree.model.data.core.server.value.ServerDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditServerDescriptionCommand
        extends BaseAbstractEditCommand<ServerDescriptionSpec, ServerDescriptionEdit, EditServerDescriptionResult, ServerDescription, Server> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Server.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ServerName", FieldType.HOST_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditServerDescriptionCommand */
    public EditServerDescriptionCommand(UserVisitPK userVisitPK, EditServerDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditServerDescriptionResult getResult() {
        return CoreResultFactory.getEditServerDescriptionResult();
    }

    @Override
    public ServerDescriptionEdit getEdit() {
        return CoreEditFactory.getServerDescriptionEdit();
    }

    @Override
    public ServerDescription getEntity(EditServerDescriptionResult result) {
        CoreControl coreControl = getCoreControl();
        ServerDescription serverDescription = null;
        String serverName = spec.getServerName();
        Server server = coreControl.getServerByName(serverName);

        if(server != null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    serverDescription = coreControl.getServerDescription(server, language);
                } else { // EditMode.UPDATE
                    serverDescription = coreControl.getServerDescriptionForUpdate(server, language);
                }

                if(serverDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownServerDescription.name(), serverName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownServerName.name(), serverName);
        }

        return serverDescription;
    }

    @Override
    public Server getLockEntity(ServerDescription serverDescription) {
        return serverDescription.getServer();
    }

    @Override
    public void fillInResult(EditServerDescriptionResult result, ServerDescription serverDescription) {
        CoreControl coreControl = getCoreControl();

        result.setServerDescription(coreControl.getServerDescriptionTransfer(getUserVisit(), serverDescription));
    }

    @Override
    public void doLock(ServerDescriptionEdit edit, ServerDescription serverDescription) {
        edit.setDescription(serverDescription.getDescription());
    }

    @Override
    public void doUpdate(ServerDescription serverDescription) {
        CoreControl coreControl = getCoreControl();
        ServerDescriptionValue serverDescriptionValue = coreControl.getServerDescriptionValue(serverDescription);
        serverDescriptionValue.setDescription(edit.getDescription());

        coreControl.updateServerDescriptionFromValue(serverDescriptionValue, getPartyPK());
    }
    
}

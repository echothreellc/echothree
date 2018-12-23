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
import com.echothree.control.user.core.common.edit.ServerEdit;
import com.echothree.control.user.core.common.form.EditServerForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditServerResult;
import com.echothree.control.user.core.common.spec.ServerSpec;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Server;
import com.echothree.model.data.core.server.entity.ServerDescription;
import com.echothree.model.data.core.server.entity.ServerDetail;
import com.echothree.model.data.core.server.value.ServerDescriptionValue;
import com.echothree.model.data.core.server.value.ServerDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditServerCommand
        extends BaseAbstractEditCommand<ServerSpec, ServerEdit, EditServerResult, Server, Server> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Server.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ServerName", FieldType.HOST_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ServerName", FieldType.HOST_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditServerCommand */
    public EditServerCommand(UserVisitPK userVisitPK, EditServerForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditServerResult getResult() {
        return CoreResultFactory.getEditServerResult();
    }

    @Override
    public ServerEdit getEdit() {
        return CoreEditFactory.getServerEdit();
    }

    @Override
    public Server getEntity(EditServerResult result) {
        CoreControl coreControl = getCoreControl();
        Server server = null;
        String serverName = spec.getServerName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            server = coreControl.getServerByName(serverName);
        } else { // EditMode.UPDATE
            server = coreControl.getServerByNameForUpdate(serverName);
        }

        if(server != null) {
            result.setServer(coreControl.getServerTransfer(getUserVisit(), server));
        } else {
            addExecutionError(ExecutionErrors.UnknownServerName.name(), serverName);
        }

        return server;
    }

    @Override
    public Server getLockEntity(Server server) {
        return server;
    }

    @Override
    public void fillInResult(EditServerResult result, Server server) {
        CoreControl coreControl = getCoreControl();

        result.setServer(coreControl.getServerTransfer(getUserVisit(), server));
    }

    @Override
    public void doLock(ServerEdit edit, Server server) {
        CoreControl coreControl = getCoreControl();
        ServerDescription serverDescription = coreControl.getServerDescription(server, getPreferredLanguage());
        ServerDetail serverDetail = server.getLastDetail();

        edit.setServerName(serverDetail.getServerName());
        edit.setIsDefault(serverDetail.getIsDefault().toString());
        edit.setSortOrder(serverDetail.getSortOrder().toString());

        if(serverDescription != null) {
            edit.setDescription(serverDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(Server server) {
        CoreControl coreControl = getCoreControl();
        String serverName = edit.getServerName();
        Server duplicateServer = coreControl.getServerByName(serverName);

        if(duplicateServer != null && !server.equals(duplicateServer)) {
            addExecutionError(ExecutionErrors.DuplicateServerName.name(), serverName);
        }
    }

    @Override
    public void doUpdate(Server server) {
        CoreControl coreControl = getCoreControl();
        PartyPK partyPK = getPartyPK();
        ServerDetailValue serverDetailValue = coreControl.getServerDetailValueForUpdate(server);
        ServerDescription serverDescription = coreControl.getServerDescriptionForUpdate(server, getPreferredLanguage());
        String description = edit.getDescription();

        serverDetailValue.setServerName(edit.getServerName());
        serverDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        serverDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        coreControl.updateServerFromValue(serverDetailValue, partyPK);

        if(serverDescription == null && description != null) {
            coreControl.createServerDescription(server, getPreferredLanguage(), description, partyPK);
        } else {
            if(serverDescription != null && description == null) {
                coreControl.deleteServerDescription(serverDescription, partyPK);
            } else {
                if(serverDescription != null && description != null) {
                    ServerDescriptionValue serverDescriptionValue = coreControl.getServerDescriptionValue(serverDescription);

                    serverDescriptionValue.setDescription(description);
                    coreControl.updateServerDescriptionFromValue(serverDescriptionValue, partyPK);
                }
            }
        }
    }
    
}

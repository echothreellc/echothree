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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.GetServerServicesForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.control.ServerControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Server;
import com.echothree.model.data.core.server.entity.ServerService;
import com.echothree.model.data.core.server.factory.ServerServiceFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetServerServicesCommand
        extends BasePaginatedMultipleEntitiesCommand<ServerService, GetServerServicesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ServerService.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ServerName", FieldType.HOST_NAME, true, null, null)
        );
    }
    
    @Inject
    ServerControl serverControl;

    /** Creates a new instance of GetServerServicesCommand */
    public GetServerServicesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    Server server;

    @Override
    protected void handleForm() {
        var serverName = form.getServerName();

        server = serverControl.getServerByName(serverName);

        if(server == null) {
            addExecutionError(ExecutionErrors.UnknownServerName.name(), serverName);
        }
    }

    @Override
    protected Long getTotalEntities() {
        return server == null ? null : serverControl.countServerServicesByServer(server);
    }

    @Override
    protected Collection<ServerService> getEntities() {
        return server == null ? null : serverControl.getServerServicesByServer(server);
    }

    @Override
    protected BaseResult getResult(Collection<ServerService> entities) {
        var result = CoreResultFactory.getGetServerServicesResult();
        
        if(server != null) {
            var userVisit = getUserVisit();

            result.setServer(serverControl.getServerTransfer(userVisit, server));

            if(session.hasLimit(ServerServiceFactory.class)) {
                result.setServerServicesCount(getTotalEntities());
            }

            result.setServerServices(serverControl.getServerServiceTransfersByServer(userVisit, server));
        }
        
        return result;
    }
    
}

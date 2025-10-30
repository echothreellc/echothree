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

import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.edit.ProtocolEdit;
import com.echothree.control.user.core.common.form.EditProtocolForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditProtocolResult;
import com.echothree.control.user.core.common.spec.ProtocolSpec;
import com.echothree.model.control.core.server.control.ServerControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Protocol;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditProtocolCommand
        extends BaseAbstractEditCommand<ProtocolSpec, ProtocolEdit, EditProtocolResult, Protocol, Protocol> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Protocol.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ProtocolName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ProtocolName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditProtocolCommand */
    public EditProtocolCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditProtocolResult getResult() {
        return CoreResultFactory.getEditProtocolResult();
    }

    @Override
    public ProtocolEdit getEdit() {
        return CoreEditFactory.getProtocolEdit();
    }

    @Override
    public Protocol getEntity(EditProtocolResult result) {
        var serverControl = Session.getModelController(ServerControl.class);
        Protocol protocol;
        var protocolName = spec.getProtocolName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            protocol = serverControl.getProtocolByName(protocolName);
        } else { // EditMode.UPDATE
            protocol = serverControl.getProtocolByNameForUpdate(protocolName);
        }

        if(protocol != null) {
            result.setProtocol(serverControl.getProtocolTransfer(getUserVisit(), protocol));
        } else {
            addExecutionError(ExecutionErrors.UnknownProtocolName.name(), protocolName);
        }

        return protocol;
    }

    @Override
    public Protocol getLockEntity(Protocol protocol) {
        return protocol;
    }

    @Override
    public void fillInResult(EditProtocolResult result, Protocol protocol) {
        var serverControl = Session.getModelController(ServerControl.class);

        result.setProtocol(serverControl.getProtocolTransfer(getUserVisit(), protocol));
    }

    @Override
    public void doLock(ProtocolEdit edit, Protocol protocol) {
        var serverControl = Session.getModelController(ServerControl.class);
        var protocolDescription = serverControl.getProtocolDescription(protocol, getPreferredLanguage());
        var protocolDetail = protocol.getLastDetail();

        edit.setProtocolName(protocolDetail.getProtocolName());
        edit.setIsDefault(protocolDetail.getIsDefault().toString());
        edit.setSortOrder(protocolDetail.getSortOrder().toString());

        if(protocolDescription != null) {
            edit.setDescription(protocolDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(Protocol protocol) {
        var serverControl = Session.getModelController(ServerControl.class);
        var protocolName = edit.getProtocolName();
        var duplicateProtocol = serverControl.getProtocolByName(protocolName);

        if(duplicateProtocol != null && !protocol.equals(duplicateProtocol)) {
            addExecutionError(ExecutionErrors.DuplicateProtocolName.name(), protocolName);
        }
    }

    @Override
    public void doUpdate(Protocol protocol) {
        var serverControl = Session.getModelController(ServerControl.class);
        var partyPK = getPartyPK();
        var protocolDetailValue = serverControl.getProtocolDetailValueForUpdate(protocol);
        var protocolDescription = serverControl.getProtocolDescriptionForUpdate(protocol, getPreferredLanguage());
        var description = edit.getDescription();

        protocolDetailValue.setProtocolName(edit.getProtocolName());
        protocolDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        protocolDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        serverControl.updateProtocolFromValue(protocolDetailValue, partyPK);

        if(protocolDescription == null && description != null) {
            serverControl.createProtocolDescription(protocol, getPreferredLanguage(), description, partyPK);
        } else {
            if(protocolDescription != null && description == null) {
                serverControl.deleteProtocolDescription(protocolDescription, partyPK);
            } else {
                if(protocolDescription != null && description != null) {
                    var protocolDescriptionValue = serverControl.getProtocolDescriptionValue(protocolDescription);

                    protocolDescriptionValue.setDescription(description);
                    serverControl.updateProtocolDescriptionFromValue(protocolDescriptionValue, partyPK);
                }
            }
        }
    }
    
}

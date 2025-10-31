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
import com.echothree.control.user.core.common.edit.ServiceEdit;
import com.echothree.control.user.core.common.form.EditServiceForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditServiceResult;
import com.echothree.control.user.core.common.spec.ServiceSpec;
import com.echothree.model.control.core.server.control.ServerControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Protocol;
import com.echothree.model.data.core.server.entity.Service;
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
public class EditServiceCommand
        extends BaseAbstractEditCommand<ServiceSpec, ServiceEdit, EditServiceResult, Service, Service> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Service.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ServiceName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ServiceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Port", FieldType.UNSIGNED_INTEGER, true, 1L, 65535L),
                new FieldDefinition("ProtocolName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditServiceCommand */
    public EditServiceCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditServiceResult getResult() {
        return CoreResultFactory.getEditServiceResult();
    }

    @Override
    public ServiceEdit getEdit() {
        return CoreEditFactory.getServiceEdit();
    }

    @Override
    public Service getEntity(EditServiceResult result) {
        var serverControl = Session.getModelController(ServerControl.class);
        Service service;
        var serviceName = spec.getServiceName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            service = serverControl.getServiceByName(serviceName);
        } else { // EditMode.UPDATE
            service = serverControl.getServiceByNameForUpdate(serviceName);
        }

        if(service != null) {
            result.setService(serverControl.getServiceTransfer(getUserVisit(), service));
        } else {
            addExecutionError(ExecutionErrors.UnknownServiceName.name(), serviceName);
        }

        return service;
    }

    @Override
    public Service getLockEntity(Service service) {
        return service;
    }

    @Override
    public void fillInResult(EditServiceResult result, Service service) {
        var serverControl = Session.getModelController(ServerControl.class);

        result.setService(serverControl.getServiceTransfer(getUserVisit(), service));
    }

    @Override
    public void doLock(ServiceEdit edit, Service service) {
        var serverControl = Session.getModelController(ServerControl.class);
        var serviceDescription = serverControl.getServiceDescription(service, getPreferredLanguage());
        var serviceDetail = service.getLastDetail();

        edit.setServiceName(serviceDetail.getServiceName());
        edit.setPort(serviceDetail.getPort().toString());
        edit.setProtocolName(serviceDetail.getProtocol().getLastDetail().getProtocolName());
        edit.setIsDefault(serviceDetail.getIsDefault().toString());
        edit.setSortOrder(serviceDetail.getSortOrder().toString());

        if(serviceDescription != null) {
            edit.setDescription(serviceDescription.getDescription());
        }
    }

    Protocol protocol;

    @Override
    public void canUpdate(Service service) {
        var serverControl = Session.getModelController(ServerControl.class);
        var serviceName = edit.getServiceName();
        var duplicateService = serverControl.getServiceByName(serviceName);

        if(duplicateService != null && !service.equals(duplicateService)) {
            addExecutionError(ExecutionErrors.DuplicateServiceName.name(), serviceName);
        } else {
            var protocolName = edit.getProtocolName();

            protocol = serverControl.getProtocolByName(protocolName);

            if(protocol == null) {
                addExecutionError(ExecutionErrors.UnknownProtocolName.name(), protocolName);
            }
        }
    }

    @Override
    public void doUpdate(Service service) {
        var serverControl = Session.getModelController(ServerControl.class);
        var partyPK = getPartyPK();
        var serviceDetailValue = serverControl.getServiceDetailValueForUpdate(service);
        var serviceDescription = serverControl.getServiceDescriptionForUpdate(service, getPreferredLanguage());
        var description = edit.getDescription();

        serviceDetailValue.setServiceName(edit.getServiceName());
        serviceDetailValue.setPort(Integer.valueOf(edit.getPort()));
        serviceDetailValue.setProtocolPK(protocol.getPrimaryKey());
        serviceDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        serviceDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        serverControl.updateServiceFromValue(serviceDetailValue, partyPK);

        if(serviceDescription == null && description != null) {
            serverControl.createServiceDescription(service, getPreferredLanguage(), description, partyPK);
        } else {
            if(serviceDescription != null && description == null) {
                serverControl.deleteServiceDescription(serviceDescription, partyPK);
            } else {
                if(serviceDescription != null && description != null) {
                    var serviceDescriptionValue = serverControl.getServiceDescriptionValue(serviceDescription);

                    serviceDescriptionValue.setDescription(description);
                    serverControl.updateServiceDescriptionFromValue(serviceDescriptionValue, partyPK);
                }
            }
        }
    }
    
}

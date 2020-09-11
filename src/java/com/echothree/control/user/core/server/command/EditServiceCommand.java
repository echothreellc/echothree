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

import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.edit.ServiceEdit;
import com.echothree.control.user.core.common.form.EditServiceForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditServiceResult;
import com.echothree.control.user.core.common.spec.ServiceSpec;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Protocol;
import com.echothree.model.data.core.server.entity.Service;
import com.echothree.model.data.core.server.entity.ServiceDescription;
import com.echothree.model.data.core.server.entity.ServiceDetail;
import com.echothree.model.data.core.server.value.ServiceDescriptionValue;
import com.echothree.model.data.core.server.value.ServiceDetailValue;
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
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditServiceCommand */
    public EditServiceCommand(UserVisitPK userVisitPK, EditServiceForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
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
        var coreControl = getCoreControl();
        Service service = null;
        String serviceName = spec.getServiceName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            service = coreControl.getServiceByName(serviceName);
        } else { // EditMode.UPDATE
            service = coreControl.getServiceByNameForUpdate(serviceName);
        }

        if(service != null) {
            result.setService(coreControl.getServiceTransfer(getUserVisit(), service));
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
        var coreControl = getCoreControl();

        result.setService(coreControl.getServiceTransfer(getUserVisit(), service));
    }

    @Override
    public void doLock(ServiceEdit edit, Service service) {
        var coreControl = getCoreControl();
        ServiceDescription serviceDescription = coreControl.getServiceDescription(service, getPreferredLanguage());
        ServiceDetail serviceDetail = service.getLastDetail();

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
        var coreControl = getCoreControl();
        String serviceName = edit.getServiceName();
        Service duplicateService = coreControl.getServiceByName(serviceName);

        if(duplicateService != null && !service.equals(duplicateService)) {
            addExecutionError(ExecutionErrors.DuplicateServiceName.name(), serviceName);
        } else {
            String protocolName = edit.getProtocolName();

            protocol = coreControl.getProtocolByName(protocolName);

            if(protocol == null) {
                addExecutionError(ExecutionErrors.UnknownProtocolName.name(), protocolName);
            }
        }
    }

    @Override
    public void doUpdate(Service service) {
        var coreControl = getCoreControl();
        var partyPK = getPartyPK();
        ServiceDetailValue serviceDetailValue = coreControl.getServiceDetailValueForUpdate(service);
        ServiceDescription serviceDescription = coreControl.getServiceDescriptionForUpdate(service, getPreferredLanguage());
        String description = edit.getDescription();

        serviceDetailValue.setServiceName(edit.getServiceName());
        serviceDetailValue.setPort(Integer.valueOf(edit.getPort()));
        serviceDetailValue.setProtocolPK(protocol.getPrimaryKey());
        serviceDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        serviceDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        coreControl.updateServiceFromValue(serviceDetailValue, partyPK);

        if(serviceDescription == null && description != null) {
            coreControl.createServiceDescription(service, getPreferredLanguage(), description, partyPK);
        } else {
            if(serviceDescription != null && description == null) {
                coreControl.deleteServiceDescription(serviceDescription, partyPK);
            } else {
                if(serviceDescription != null && description != null) {
                    ServiceDescriptionValue serviceDescriptionValue = coreControl.getServiceDescriptionValue(serviceDescription);

                    serviceDescriptionValue.setDescription(description);
                    coreControl.updateServiceDescriptionFromValue(serviceDescriptionValue, partyPK);
                }
            }
        }
    }
    
}

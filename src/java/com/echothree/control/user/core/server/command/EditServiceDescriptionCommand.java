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

import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.edit.ServiceDescriptionEdit;
import com.echothree.control.user.core.common.form.EditServiceDescriptionForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditServiceDescriptionResult;
import com.echothree.control.user.core.common.spec.ServiceDescriptionSpec;
import com.echothree.model.control.core.server.control.ServerControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Service;
import com.echothree.model.data.core.server.entity.ServiceDescription;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditServiceDescriptionCommand
        extends BaseAbstractEditCommand<ServiceDescriptionSpec, ServiceDescriptionEdit, EditServiceDescriptionResult, ServiceDescription, Service> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Service.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ServiceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditServiceDescriptionCommand */
    public EditServiceDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditServiceDescriptionResult getResult() {
        return CoreResultFactory.getEditServiceDescriptionResult();
    }

    @Override
    public ServiceDescriptionEdit getEdit() {
        return CoreEditFactory.getServiceDescriptionEdit();
    }

    @Override
    public ServiceDescription getEntity(EditServiceDescriptionResult result) {
        var serverControl = Session.getModelController(ServerControl.class);
        ServiceDescription serviceDescription = null;
        var serviceName = spec.getServiceName();
        var service = serverControl.getServiceByName(serviceName);

        if(service != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    serviceDescription = serverControl.getServiceDescription(service, language);
                } else { // EditMode.UPDATE
                    serviceDescription = serverControl.getServiceDescriptionForUpdate(service, language);
                }

                if(serviceDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownServiceDescription.name(), serviceName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownServiceName.name(), serviceName);
        }

        return serviceDescription;
    }

    @Override
    public Service getLockEntity(ServiceDescription serviceDescription) {
        return serviceDescription.getService();
    }

    @Override
    public void fillInResult(EditServiceDescriptionResult result, ServiceDescription serviceDescription) {
        var serverControl = Session.getModelController(ServerControl.class);

        result.setServiceDescription(serverControl.getServiceDescriptionTransfer(getUserVisit(), serviceDescription));
    }

    @Override
    public void doLock(ServiceDescriptionEdit edit, ServiceDescription serviceDescription) {
        edit.setDescription(serviceDescription.getDescription());
    }

    @Override
    public void doUpdate(ServiceDescription serviceDescription) {
        var serverControl = Session.getModelController(ServerControl.class);
        var serviceDescriptionValue = serverControl.getServiceDescriptionValue(serviceDescription);
        serviceDescriptionValue.setDescription(edit.getDescription());

        serverControl.updateServiceDescriptionFromValue(serviceDescriptionValue, getPartyPK());
    }
    
}

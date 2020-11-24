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

package com.echothree.control.user.scale.server.command;

import com.echothree.control.user.scale.common.form.CreateScaleForm;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.data.core.server.entity.Server;
import com.echothree.model.data.core.server.entity.ServerService;
import com.echothree.model.data.core.server.entity.Service;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.scale.server.entity.Scale;
import com.echothree.model.data.scale.server.entity.ScaleType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateScaleCommand
        extends BaseSimpleCommand<CreateScaleForm> {
    
   private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ScaleName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ScaleTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ServerName", FieldType.HOST_NAME, true, null, null),
                new FieldDefinition("ServiceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }

    /** Creates a new instance of CreateScaleCommand */
    public CreateScaleCommand(UserVisitPK userVisitPK, CreateScaleForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
   @Override
    protected BaseResult execute() {
        var scaleControl = Session.getModelController(ScaleControl.class);
        String scaleName = form.getScaleName();
        Scale scale = scaleControl.getScaleByName(scaleName);

        if(scale == null) {
            String scaleTypeName = form.getScaleTypeName();
            ScaleType scaleType = scaleControl.getScaleTypeByName(scaleTypeName);

            if(scaleType != null) {
                var coreControl = getCoreControl();
                String serverName = form.getServerName();
                Server server = coreControl.getServerByName(serverName);

                if(server != null) {
                    String serviceName = form.getServiceName();
                    Service service = coreControl.getServiceByName(serviceName);

                    if(service != null) {
                        ServerService serverService = coreControl.getServerService(server, service);

                        if(serverService != null) {
                            var isDefault = Boolean.valueOf(form.getIsDefault());
                            var sortOrder = Integer.valueOf(form.getSortOrder());
                            var description = form.getDescription();
                            PartyPK createdBy = getPartyPK();

                            scale = scaleControl.createScale(scaleName, scaleType, serverService, isDefault, sortOrder, createdBy);

                            if(description != null) {
                                Language language = getPreferredLanguage();

                                scaleControl.createScaleDescription(scale, language, description, createdBy);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownServerService.name(), serverName, serviceName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownServiceName.name(), serviceName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownServerName.name(), serverName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownScaleTypeName.name(), scaleTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateScaleName.name(), scaleName);
        }
        
        return null;
    }
    
}

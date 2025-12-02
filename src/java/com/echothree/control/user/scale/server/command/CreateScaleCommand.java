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

package com.echothree.control.user.scale.server.command;

import com.echothree.control.user.scale.common.form.CreateScaleForm;
import com.echothree.model.control.core.server.control.ServerControl;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
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
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of CreateScaleCommand */
    public CreateScaleCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
   @Override
    protected BaseResult execute() {
        var scaleControl = Session.getModelController(ScaleControl.class);
       var scaleName = form.getScaleName();
       var scale = scaleControl.getScaleByName(scaleName);

        if(scale == null) {
            var scaleTypeName = form.getScaleTypeName();
            var scaleType = scaleControl.getScaleTypeByName(scaleTypeName);

            if(scaleType != null) {
                var serverControl = Session.getModelController(ServerControl.class);
                var serverName = form.getServerName();
                var server = serverControl.getServerByName(serverName);

                if(server != null) {
                    var serviceName = form.getServiceName();
                    var service = serverControl.getServiceByName(serviceName);

                    if(service != null) {
                        var serverService = serverControl.getServerService(server, service);

                        if(serverService != null) {
                            var isDefault = Boolean.valueOf(form.getIsDefault());
                            var sortOrder = Integer.valueOf(form.getSortOrder());
                            var description = form.getDescription();
                            var createdBy = getPartyPK();

                            scale = scaleControl.createScale(scaleName, scaleType, serverService, isDefault, sortOrder, createdBy);

                            if(description != null) {
                                var language = getPreferredLanguage();

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

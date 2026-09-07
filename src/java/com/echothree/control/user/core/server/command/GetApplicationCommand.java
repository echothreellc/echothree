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

import com.echothree.control.user.core.common.form.GetApplicationForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.ApplicationControl;
import com.echothree.model.control.core.server.logic.ApplicationLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.core.server.entity.Application;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetApplicationCommand
        extends BaseSingleEntityCommand<Application, GetApplicationForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ApplicationName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }

    @Inject
    ApplicationControl applicationControl;

    @Inject
    ApplicationLogic applicationLogic;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    /** Creates a new instance of GetApplicationCommand */
    public GetApplicationCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected Application getEntity() {
        var applicationName = form.getApplicationName();
        var parameterCount = (applicationName == null ? 0 : 1) + entityInstanceLogic.countPossibleEntitySpecs(form);
        Application application = null;

        if(parameterCount == 1) {
            if(applicationName == null) {
                var entityInstance = entityInstanceLogic.getEntityInstance(this, form, ComponentVendors.ECHO_THREE.name(),
                        EntityTypes.Application.name());
                
                if(!hasExecutionErrors()) {
                    application = applicationControl.getApplicationByEntityInstance(entityInstance);
                }
            } else {
                application = applicationLogic.getApplicationByName(this, applicationName);
            }

            if(application != null) {
                sendEvent(application.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return application;
    }

    @Override
    protected BaseResult getResult(Application application) {
        var result = CoreResultFactory.getGetApplicationResult();

        if(application != null) {
            result.setApplication(applicationControl.getApplicationTransfer(getUserVisit(), application));
        }

        return result;
    }
    
}

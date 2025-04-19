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

import com.echothree.control.user.core.common.form.CreateComponentVersionForm;
import com.echothree.model.control.core.server.control.ComponentControl;
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

public class CreateComponentVersionCommand
        extends BaseSimpleCommand<CreateComponentVersionForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ComponentName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("MajorRevision", FieldType.UNSIGNED_INTEGER, true, null, null),
                new FieldDefinition("MinorRevision", FieldType.UNSIGNED_INTEGER, true, null, null),
                new FieldDefinition("ComponentStageName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("BuildNumber", FieldType.SIGNED_INTEGER,  true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateComponentVersionCommand */
    public CreateComponentVersionCommand(UserVisitPK userVisitPK, CreateComponentVersionForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var componentControl = Session.getModelController(ComponentControl.class);
        var componentVendorName = form.getComponentVendorName();
        var componentVendor = getComponentControl().getComponentVendorByName(componentVendorName);
        
        if(componentVendor != null) {
            var componentName = form.getComponentName();
            var component = componentControl.getComponentByName(componentVendor, componentName);
            
            if(component != null) {
                var componentVersion = componentControl.getComponentVersion(component);
                
                if(componentVersion == null) {
                    var componentStageName = form.getComponentStageName();
                    var componentStage = componentControl.getComponentStageByName(componentStageName);
                    
                    if(componentStage != null) {
                        var majorRevision = Integer.valueOf(form.getMajorRevision());
                        var minorRevision = Integer.valueOf(form.getMinorRevision());
                        var buildNumber = Integer.valueOf(form.getBuildNumber());
                        
                        componentControl.createComponentVersion(component, majorRevision, minorRevision, componentStage, buildNumber, getPartyPK());
                    } else {
                        addExecutionError(ExecutionErrors.UnknownComponentStageName.name(), componentStageName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateComponentVersion.name());
                }
            } else {
                addExecutionError(ExecutionErrors.DuplicateComponentName.name(), componentVendorName, componentName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateComponentVendorName.name(), componentVendorName);
        }
        
        return null;
    }
    
}

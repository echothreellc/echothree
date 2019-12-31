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

import com.echothree.control.user.core.common.form.CreateComponentStageForm;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.data.core.server.entity.ComponentStage;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateComponentStageCommand
        extends BaseSimpleCommand<CreateComponentStageForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentStageName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L),
                new FieldDefinition("RelativeAge", FieldType.UNSIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateComponentStageCommand */
    public CreateComponentStageCommand(UserVisitPK userVisitPK, CreateComponentStageForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var coreControl = getCoreControl();
        String componentStageName = form.getComponentStageName();
        ComponentStage componentStage = coreControl.getComponentStageByName(componentStageName);
        
        if(componentStage == null) {
            String description = form.getDescription();
            Integer relativeAge = Integer.valueOf(form.getRelativeAge());
            
            coreControl.createComponentStage(componentStageName, description, relativeAge);
        } else {
            addExecutionError(ExecutionErrors.DuplicateComponentStageName.name(), componentStageName);
        }
        
        return null;
    }
    
}

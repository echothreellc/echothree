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

package com.echothree.control.user.icon.server.command;

import com.echothree.control.user.icon.common.form.GetIconChoicesForm;
import com.echothree.control.user.icon.common.result.IconResultFactory;
import com.echothree.model.control.icon.server.control.IconControl;
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

public class GetIconChoicesCommand
        extends BaseSimpleCommand<GetIconChoicesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("IconUsageTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("DefaultIconChoice", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetIconChoicesCommand */
    public GetIconChoicesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var iconControl = Session.getModelController(IconControl.class);
        var result = IconResultFactory.getGetIconChoicesResult();
        var iconUsageTypeName = form.getIconUsageTypeName();
        var iconUsageType = iconControl.getIconUsageTypeByName(iconUsageTypeName);
        
        if(iconUsageType != null) {
            var defaultIconChoice = form.getDefaultIconChoice();
            var allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());
            
            result.setIconChoices(iconControl.getIconChoicesByIconUsageType(iconUsageType, defaultIconChoice,
                    getPreferredLanguage(), allowNullChoice));
        } else {
            addExecutionError(ExecutionErrors.UnknownIconUsageTypeName.name(), iconUsageTypeName);
        }
        
        return result;
    }
    
}

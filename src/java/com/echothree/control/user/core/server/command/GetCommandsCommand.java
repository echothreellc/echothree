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

import com.echothree.control.user.core.common.form.GetCommandsForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.control.CommandControl;
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

public class GetCommandsCommand
        extends BaseSimpleCommand<GetCommandsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetCommandsCommand */
    public GetCommandsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var commandControl = Session.getModelController(CommandControl.class);
        var result = CoreResultFactory.getGetCommandsResult();
        var componentVendorName = form.getComponentVendorName();
        
        if(componentVendorName == null) {
            result.setCommands(commandControl.getCommandTransfers(getUserVisit()));
        } else {
            var componentVendor = getComponentControl().getComponentVendorByName(componentVendorName);
            
            if(componentVendor != null) {
                result.setCommands(commandControl.getCommandTransfersByComponentVendor(getUserVisit(), componentVendor));
            } else {
                addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
            }
        }
        
        return result;
    }
    
}

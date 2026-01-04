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

package com.echothree.control.user.user.server.command;

import com.echothree.control.user.user.common.form.CreateUserLoginPasswordTypeForm;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class CreateUserLoginPasswordTypeCommand
        extends BaseSimpleCommand<CreateUserLoginPasswordTypeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UserLoginPasswordTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UserLoginPasswordEncoderTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateUserLoginPasswordTypeCommand */
    public CreateUserLoginPasswordTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var userControl = getUserControl();
        var userLoginPasswordTypeName = form.getUserLoginPasswordTypeName();
        var userLoginPasswordType = userControl.getUserLoginPasswordTypeByName(userLoginPasswordTypeName);
        
        if(userLoginPasswordType == null) {
            var userLoginPasswordEncoderTypeName = form.getUserLoginPasswordEncoderTypeName();
            var userLoginPasswordEncoderType = userControl.getUserLoginPasswordEncoderTypeByName(userLoginPasswordEncoderTypeName);
            
            if(userLoginPasswordEncoderTypeName == null || userLoginPasswordEncoderType != null) {
                userControl.createUserLoginPasswordType(userLoginPasswordTypeName, userLoginPasswordEncoderType);
            } // TODO: error, unknown userLoginPasswordEncoderTypeName
        } // TODO: error, duplicate userLoginPasswordTypeName
        
        return null;
    }
    
}

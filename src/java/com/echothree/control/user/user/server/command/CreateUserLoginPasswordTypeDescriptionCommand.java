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

package com.echothree.control.user.user.server.command;

import com.echothree.control.user.user.common.form.CreateUserLoginPasswordTypeDescriptionForm;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateUserLoginPasswordTypeDescriptionCommand
        extends BaseSimpleCommand<CreateUserLoginPasswordTypeDescriptionForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UserLoginPasswordTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateUserLoginPasswordTypeDescriptionCommand */
    public CreateUserLoginPasswordTypeDescriptionCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var userControl = getUserControl();
        var userLoginPasswordTypeName = form.getUserLoginPasswordTypeName();
        var userLoginPasswordType = userControl.getUserLoginPasswordTypeByName(userLoginPasswordTypeName);
        
        if(userLoginPasswordType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = form.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                var userLoginPasswordTypeDescription = userControl.getUserLoginPasswordTypeDescription(userLoginPasswordType, language);
                
                if(userLoginPasswordTypeDescription == null) {
                    var description = form.getDescription();
                    
                    userControl.createUserLoginPasswordTypeDescription(userLoginPasswordType, language, description);
                } // TODO: error, duplicate userLoginPasswordTypeDescription
            } // TODO: error, unknown languageIsoName
        } // TODO: error, unknown userLoginPasswordTypeName
        
        return null;
    }
    
}

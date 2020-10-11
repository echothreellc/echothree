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

package com.echothree.control.user.user.server.command;

import com.echothree.control.user.user.common.form.CreateUserLoginPasswordTypeDescriptionForm;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserLoginPasswordType;
import com.echothree.model.data.user.server.entity.UserLoginPasswordTypeDescription;
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
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateUserLoginPasswordTypeDescriptionCommand */
    public CreateUserLoginPasswordTypeDescriptionCommand(UserVisitPK userVisitPK, CreateUserLoginPasswordTypeDescriptionForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        UserControl userControl = getUserControl();
        String userLoginPasswordTypeName = form.getUserLoginPasswordTypeName();
        UserLoginPasswordType userLoginPasswordType = userControl.getUserLoginPasswordTypeByName(userLoginPasswordTypeName);
        
        if(userLoginPasswordType != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = form.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                UserLoginPasswordTypeDescription userLoginPasswordTypeDescription = userControl.getUserLoginPasswordTypeDescription(userLoginPasswordType, language);
                
                if(userLoginPasswordTypeDescription == null) {
                    var description = form.getDescription();
                    
                    userControl.createUserLoginPasswordTypeDescription(userLoginPasswordType, language, description);
                } // TODO: error, duplicate userLoginPasswordTypeDescription
            } // TODO: error, unknown languageIsoName
        } // TODO: error, unknown userLoginPasswordTypeName
        
        return null;
    }
    
}

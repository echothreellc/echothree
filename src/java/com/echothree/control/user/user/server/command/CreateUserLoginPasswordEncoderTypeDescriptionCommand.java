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

import com.echothree.control.user.user.common.form.CreateUserLoginPasswordEncoderTypeDescriptionForm;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserLoginPasswordEncoderType;
import com.echothree.model.data.user.server.entity.UserLoginPasswordEncoderTypeDescription;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateUserLoginPasswordEncoderTypeDescriptionCommand
        extends BaseSimpleCommand<CreateUserLoginPasswordEncoderTypeDescriptionForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UserLoginPasswordEncoderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateUserLoginPasswordEncoderTypeDescriptionCommand */
    public CreateUserLoginPasswordEncoderTypeDescriptionCommand(UserVisitPK userVisitPK, CreateUserLoginPasswordEncoderTypeDescriptionForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        UserControl userControl = getUserControl();
        String userLoginPasswordEncoderTypeName = form.getUserLoginPasswordEncoderTypeName();
        UserLoginPasswordEncoderType userLoginPasswordEncoderType = userControl.getUserLoginPasswordEncoderTypeByName(userLoginPasswordEncoderTypeName);
        
        if(userLoginPasswordEncoderType != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = form.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                UserLoginPasswordEncoderTypeDescription userLoginPasswordEncoderTypeDescription = userControl.getUserLoginPasswordEncoderTypeDescription(userLoginPasswordEncoderType, language);
                
                if(userLoginPasswordEncoderTypeDescription == null) {
                    var description = form.getDescription();
                    
                    userControl.createUserLoginPasswordEncoderTypeDescription(userLoginPasswordEncoderType, language, description);
                } // TODO: error, duplicate userLoginPasswordEncoderTypeDescription
            } // TODO: error, unknown languageIsoName
        } // TODO: error, unknown userLoginPasswordEncoderTypeName
        
        return null;
    }
    
}

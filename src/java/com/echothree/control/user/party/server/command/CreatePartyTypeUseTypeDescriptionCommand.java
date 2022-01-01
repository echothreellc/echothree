// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.form.CreatePartyTypeUseTypeDescriptionForm;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.PartyTypeUseType;
import com.echothree.model.data.party.server.entity.PartyTypeUseTypeDescription;
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

public class CreatePartyTypeUseTypeDescriptionCommand
        extends BaseSimpleCommand<CreatePartyTypeUseTypeDescriptionForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyTypeUseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreatePartyTypeUseTypeDescriptionCommand */
    public CreatePartyTypeUseTypeDescriptionCommand(UserVisitPK userVisitPK, CreatePartyTypeUseTypeDescriptionForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        String partyTypeUseTypeName = form.getPartyTypeUseTypeName();
        PartyTypeUseType partyTypeUseType = partyControl.getPartyTypeUseTypeByName(partyTypeUseTypeName);
        
        if(partyTypeUseType != null) {
            String languageIsoName = form.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                PartyTypeUseTypeDescription partyTypeUseTypeDescription = partyControl.getPartyTypeUseTypeDescription(partyTypeUseType, language);
                
                if(partyTypeUseTypeDescription == null) {
                    var description = form.getDescription();
                    
                    partyControl.createPartyTypeUseTypeDescription(partyTypeUseType, language, description);
                } else {
                    addExecutionError(ExecutionErrors.DuplicatePartyTypeUseTypeDescription.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyTypeUseTypeName.name(), partyTypeUseTypeName);
        }
        
        return null;
    }
    
}

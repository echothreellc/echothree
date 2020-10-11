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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.form.CreatePartyRelationshipTypeDescriptionForm;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.PartyRelationshipType;
import com.echothree.model.data.party.server.entity.PartyRelationshipTypeDescription;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreatePartyRelationshipTypeDescriptionCommand
        extends BaseSimpleCommand<CreatePartyRelationshipTypeDescriptionForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyRelationshipTypeName", FieldType.STRING, true, null, 40L),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreatePartyRelationshipTypeDescriptionCommand */
    public CreatePartyRelationshipTypeDescriptionCommand(UserVisitPK userVisitPK, CreatePartyRelationshipTypeDescriptionForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        String partyRelationshipTypeName = form.getPartyRelationshipTypeName();
        PartyRelationshipType partyRelationshipType = partyControl.getPartyRelationshipTypeByName(partyRelationshipTypeName);
        
        if(partyRelationshipType != null) {
            String languageIsoName = form.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                PartyRelationshipTypeDescription partyRelationshipTypeDescription = partyControl.getPartyRelationshipTypeDescription(partyRelationshipType, language);
                
                if(partyRelationshipTypeDescription == null) {
                    var description = form.getDescription();
                    
                    partyControl.createPartyRelationshipTypeDescription(partyRelationshipType, language, description);
                } // TODO: error, duplicate partyRelationshipTypeDescription
            } // TODO: error, unknown languageIsoName
        } // TODO: error, unknown partyRelationshipTypeName
        
        return null;
    }
    
}

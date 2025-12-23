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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.form.CreatePartyRelationshipTypeDescriptionForm;
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
import javax.enterprise.context.Dependent;

@Dependent
public class CreatePartyRelationshipTypeDescriptionCommand
        extends BaseSimpleCommand<CreatePartyRelationshipTypeDescriptionForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyRelationshipTypeName", FieldType.STRING, true, null, 40L),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreatePartyRelationshipTypeDescriptionCommand */
    public CreatePartyRelationshipTypeDescriptionCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var partyRelationshipTypeName = form.getPartyRelationshipTypeName();
        var partyRelationshipType = partyControl.getPartyRelationshipTypeByName(partyRelationshipTypeName);
        
        if(partyRelationshipType != null) {
            var languageIsoName = form.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                var partyRelationshipTypeDescription = partyControl.getPartyRelationshipTypeDescription(partyRelationshipType, language);
                
                if(partyRelationshipTypeDescription == null) {
                    var description = form.getDescription();
                    
                    partyControl.createPartyRelationshipTypeDescription(partyRelationshipType, language, description);
                } // TODO: error, duplicate partyRelationshipTypeDescription
            } // TODO: error, unknown languageIsoName
        } // TODO: error, unknown partyRelationshipTypeName
        
        return null;
    }
    
}

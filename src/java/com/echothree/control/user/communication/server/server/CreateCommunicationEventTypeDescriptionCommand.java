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

package com.echothree.control.user.communication.server.server;

import com.echothree.control.user.communication.common.form.CreateCommunicationEventTypeDescriptionForm;
import com.echothree.model.control.communication.server.CommunicationControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.communication.server.entity.CommunicationEventType;
import com.echothree.model.data.communication.server.entity.CommunicationEventTypeDescription;
import com.echothree.model.data.party.server.entity.Language;
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

public class CreateCommunicationEventTypeDescriptionCommand
        extends BaseSimpleCommand<CreateCommunicationEventTypeDescriptionForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("CommunicationEventTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
        ));
    }
    
    /** Creates a new instance of CreateCommunicationEventTypeDescriptionCommand */
    public CreateCommunicationEventTypeDescriptionCommand(UserVisitPK userVisitPK, CreateCommunicationEventTypeDescriptionForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var communicationControl = (CommunicationControl)Session.getModelController(CommunicationControl.class);
        String communicationEventTypeName = form.getCommunicationEventTypeName();
        CommunicationEventType communicationEventType = communicationControl.getCommunicationEventTypeByName(communicationEventTypeName);
        
        if(communicationEventType != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = form.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                CommunicationEventTypeDescription communicationEventTypeDescription = communicationControl.getCommunicationEventTypeDescription(communicationEventType, language);
                
                if(communicationEventTypeDescription == null) {
                    String description = form.getDescription();
                    
                    communicationControl.createCommunicationEventTypeDescription(communicationEventType, language, description);
                } else {
                    addExecutionError(ExecutionErrors.DuplicateCommunicationEventTypeDescription.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCommunicationEventTypeName.name(), communicationEventTypeName);
        }
        
        return null;
    }
    
}

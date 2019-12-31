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

import com.echothree.control.user.communication.common.edit.CommunicationEditFactory;
import com.echothree.control.user.communication.common.edit.CommunicationEventPurposeDescriptionEdit;
import com.echothree.control.user.communication.common.form.EditCommunicationEventPurposeDescriptionForm;
import com.echothree.control.user.communication.common.result.CommunicationResultFactory;
import com.echothree.control.user.communication.common.result.EditCommunicationEventPurposeDescriptionResult;
import com.echothree.control.user.communication.common.spec.CommunicationEventPurposeDescriptionSpec;
import com.echothree.model.control.communication.server.CommunicationControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.communication.server.entity.CommunicationEventPurpose;
import com.echothree.model.data.communication.server.entity.CommunicationEventPurposeDescription;
import com.echothree.model.data.communication.server.value.CommunicationEventPurposeDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditCommunicationEventPurposeDescriptionCommand
        extends BaseEditCommand<CommunicationEventPurposeDescriptionSpec, CommunicationEventPurposeDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        List<FieldDefinition> temp = new ArrayList<>(2);
        temp.add(new FieldDefinition("CommunicationEventPurposeName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(1);
        temp.add(new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditCommunicationEventPurposeDescriptionCommand */
    public EditCommunicationEventPurposeDescriptionCommand(UserVisitPK userVisitPK, EditCommunicationEventPurposeDescriptionForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var communicationControl = (CommunicationControl)Session.getModelController(CommunicationControl.class);
        EditCommunicationEventPurposeDescriptionResult result = CommunicationResultFactory.getEditCommunicationEventPurposeDescriptionResult();
        String communicationEventPurposeName = spec.getCommunicationEventPurposeName();
        CommunicationEventPurpose communicationEventPurpose = communicationControl.getCommunicationEventPurposeByName(communicationEventPurposeName);
        
        if(communicationEventPurpose != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    CommunicationEventPurposeDescription communicationEventPurposeDescription = communicationControl.getCommunicationEventPurposeDescription(communicationEventPurpose, language);
                    
                    if(communicationEventPurposeDescription != null) {
                        result.setCommunicationEventPurposeDescription(communicationControl.getCommunicationEventPurposeDescriptionTransfer(getUserVisit(), communicationEventPurposeDescription));
                        
                        if(lockEntity(communicationEventPurpose)) {
                            CommunicationEventPurposeDescriptionEdit edit = CommunicationEditFactory.getCommunicationEventPurposeDescriptionEdit();
                            
                            result.setEdit(edit);
                            edit.setDescription(communicationEventPurposeDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(communicationEventPurpose));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownCommunicationEventPurposeDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    CommunicationEventPurposeDescriptionValue communicationEventPurposeDescriptionValue = communicationControl.getCommunicationEventPurposeDescriptionValueForUpdate(communicationEventPurpose, language);
                    
                    if(communicationEventPurposeDescriptionValue != null) {
                        if(lockEntityForUpdate(communicationEventPurpose)) {
                            try {
                                String description = edit.getDescription();
                                
                                communicationEventPurposeDescriptionValue.setDescription(description);
                                
                                communicationControl.updateCommunicationEventPurposeDescriptionFromValue(communicationEventPurposeDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(communicationEventPurpose);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownCommunicationEventPurposeDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCommunicationEventPurposeName.name(), communicationEventPurposeName);
        }
        
        return result;
    }
    
}

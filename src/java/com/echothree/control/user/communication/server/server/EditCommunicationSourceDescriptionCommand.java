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
import com.echothree.control.user.communication.common.edit.CommunicationSourceDescriptionEdit;
import com.echothree.control.user.communication.common.form.EditCommunicationSourceDescriptionForm;
import com.echothree.control.user.communication.common.result.CommunicationResultFactory;
import com.echothree.control.user.communication.common.result.EditCommunicationSourceDescriptionResult;
import com.echothree.control.user.communication.common.spec.CommunicationSourceDescriptionSpec;
import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.communication.server.entity.CommunicationSource;
import com.echothree.model.data.communication.server.entity.CommunicationSourceDescription;
import com.echothree.model.data.communication.server.value.CommunicationSourceDescriptionValue;
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

public class EditCommunicationSourceDescriptionCommand
        extends BaseEditCommand<CommunicationSourceDescriptionSpec, CommunicationSourceDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        List<FieldDefinition> temp = new ArrayList<>(2);
        temp.add(new FieldDefinition("CommunicationSourceName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(1);
        temp.add(new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditCommunicationSourceDescriptionCommand */
    public EditCommunicationSourceDescriptionCommand(UserVisitPK userVisitPK, EditCommunicationSourceDescriptionForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var communicationControl = (CommunicationControl)Session.getModelController(CommunicationControl.class);
        EditCommunicationSourceDescriptionResult result = CommunicationResultFactory.getEditCommunicationSourceDescriptionResult();
        String communicationSourceName = spec.getCommunicationSourceName();
        CommunicationSource communicationSource = communicationControl.getCommunicationSourceByName(communicationSourceName);
        
        if(communicationSource != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    CommunicationSourceDescription communicationSourceDescription = communicationControl.getCommunicationSourceDescription(communicationSource, language);
                    
                    if(communicationSourceDescription != null) {
                        result.setCommunicationSourceDescription(communicationControl.getCommunicationSourceDescriptionTransfer(getUserVisit(), communicationSourceDescription));
                        
                        if(lockEntity(communicationSource)) {
                            CommunicationSourceDescriptionEdit edit = CommunicationEditFactory.getCommunicationSourceDescriptionEdit();
                            
                            result.setEdit(edit);
                            edit.setDescription(communicationSourceDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(communicationSource));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownCommunicationSourceDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    CommunicationSourceDescriptionValue communicationSourceDescriptionValue = communicationControl.getCommunicationSourceDescriptionValueForUpdate(communicationSource, language);
                    
                    if(communicationSourceDescriptionValue != null) {
                        if(lockEntityForUpdate(communicationSource)) {
                            try {
                                String description = edit.getDescription();
                                
                                communicationSourceDescriptionValue.setDescription(description);
                                
                                communicationControl.updateCommunicationSourceDescriptionFromValue(communicationSourceDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(communicationSource);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownCommunicationSourceDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCommunicationSourceName.name(), communicationSourceName);
        }
        
        return result;
    }
    
}

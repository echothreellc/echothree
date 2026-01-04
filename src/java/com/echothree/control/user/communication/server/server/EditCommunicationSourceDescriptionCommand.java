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

package com.echothree.control.user.communication.server.server;

import com.echothree.control.user.communication.common.edit.CommunicationEditFactory;
import com.echothree.control.user.communication.common.edit.CommunicationSourceDescriptionEdit;
import com.echothree.control.user.communication.common.form.EditCommunicationSourceDescriptionForm;
import com.echothree.control.user.communication.common.result.CommunicationResultFactory;
import com.echothree.control.user.communication.common.spec.CommunicationSourceDescriptionSpec;
import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.control.party.server.control.PartyControl;
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
import javax.enterprise.context.Dependent;

@Dependent
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
        temp.add(new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditCommunicationSourceDescriptionCommand */
    public EditCommunicationSourceDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var communicationControl = Session.getModelController(CommunicationControl.class);
        var result = CommunicationResultFactory.getEditCommunicationSourceDescriptionResult();
        var communicationSourceName = spec.getCommunicationSourceName();
        var communicationSource = communicationControl.getCommunicationSourceByName(communicationSourceName);
        
        if(communicationSource != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    var communicationSourceDescription = communicationControl.getCommunicationSourceDescription(communicationSource, language);
                    
                    if(communicationSourceDescription != null) {
                        result.setCommunicationSourceDescription(communicationControl.getCommunicationSourceDescriptionTransfer(getUserVisit(), communicationSourceDescription));
                        
                        if(lockEntity(communicationSource)) {
                            var edit = CommunicationEditFactory.getCommunicationSourceDescriptionEdit();
                            
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
                    var communicationSourceDescriptionValue = communicationControl.getCommunicationSourceDescriptionValueForUpdate(communicationSource, language);
                    
                    if(communicationSourceDescriptionValue != null) {
                        if(lockEntityForUpdate(communicationSource)) {
                            try {
                                var description = edit.getDescription();
                                
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

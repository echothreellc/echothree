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
import com.echothree.control.user.communication.common.edit.CommunicationEventPurposeEdit;
import com.echothree.control.user.communication.common.form.EditCommunicationEventPurposeForm;
import com.echothree.control.user.communication.common.result.CommunicationResultFactory;
import com.echothree.control.user.communication.common.result.EditCommunicationEventPurposeResult;
import com.echothree.control.user.communication.common.spec.CommunicationEventPurposeSpec;
import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.data.communication.server.entity.CommunicationEventPurpose;
import com.echothree.model.data.communication.server.entity.CommunicationEventPurposeDescription;
import com.echothree.model.data.communication.server.entity.CommunicationEventPurposeDetail;
import com.echothree.model.data.communication.server.value.CommunicationEventPurposeDescriptionValue;
import com.echothree.model.data.communication.server.value.CommunicationEventPurposeDetailValue;
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

public class EditCommunicationEventPurposeCommand
        extends BaseEditCommand<CommunicationEventPurposeSpec, CommunicationEventPurposeEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        List<FieldDefinition> temp = new ArrayList<>(1);
        temp.add(new FieldDefinition("CommunicationEventPurposeName", FieldType.ENTITY_NAME, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(4);
        temp.add(new FieldDefinition("CommunicationEventPurposeName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null));
        temp.add(new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null));
        temp.add(new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditCommunicationEventPurposeCommand */
    public EditCommunicationEventPurposeCommand(UserVisitPK userVisitPK, EditCommunicationEventPurposeForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var communicationControl = (CommunicationControl)Session.getModelController(CommunicationControl.class);
        EditCommunicationEventPurposeResult result = CommunicationResultFactory.getEditCommunicationEventPurposeResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            String communicationEventPurposeName = spec.getCommunicationEventPurposeName();
            CommunicationEventPurpose communicationEventPurpose = communicationControl.getCommunicationEventPurposeByName(communicationEventPurposeName);
            
            if(communicationEventPurpose != null) {
                result.setCommunicationEventPurpose(communicationControl.getCommunicationEventPurposeTransfer(getUserVisit(), communicationEventPurpose));
                
                if(lockEntity(communicationEventPurpose)) {
                    CommunicationEventPurposeDescription communicationEventPurposeDescription = communicationControl.getCommunicationEventPurposeDescription(communicationEventPurpose, getPreferredLanguage());
                    CommunicationEventPurposeEdit edit = CommunicationEditFactory.getCommunicationEventPurposeEdit();
                    CommunicationEventPurposeDetail communicationEventPurposeDetail = communicationEventPurpose.getLastDetail();
                    
                    result.setEdit(edit);
                    edit.setCommunicationEventPurposeName(communicationEventPurposeDetail.getCommunicationEventPurposeName());
                    edit.setIsDefault(communicationEventPurposeDetail.getIsDefault().toString());
                    edit.setSortOrder(communicationEventPurposeDetail.getSortOrder().toString());
                    
                    if(communicationEventPurposeDescription != null)
                        edit.setDescription(communicationEventPurposeDescription.getDescription());
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(communicationEventPurpose));
            } else {
                addExecutionError(ExecutionErrors.UnknownCommunicationEventPurposeName.name(), communicationEventPurposeName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            String communicationEventPurposeName = spec.getCommunicationEventPurposeName();
            CommunicationEventPurpose communicationEventPurpose = communicationControl.getCommunicationEventPurposeByNameForUpdate(communicationEventPurposeName);
            
            if(communicationEventPurpose != null) {
                if(lockEntityForUpdate(communicationEventPurpose)) {
                    try {
                        var partyPK = getPartyPK();
                        CommunicationEventPurposeDetailValue communicationEventPurposeDetailValue = communicationControl.getCommunicationEventPurposeDetailValueForUpdate(communicationEventPurpose);
                        CommunicationEventPurposeDescription communicationEventPurposeDescription = communicationControl.getCommunicationEventPurposeDescriptionForUpdate(communicationEventPurpose, getPreferredLanguage());
                        String description = edit.getDescription();
                        
                        communicationEventPurposeDetailValue.setCommunicationEventPurposeName(edit.getCommunicationEventPurposeName());
                        communicationEventPurposeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                        communicationEventPurposeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                        
                        communicationControl.updateCommunicationEventPurposeFromValue(communicationEventPurposeDetailValue, partyPK);
                        
                        if(communicationEventPurposeDescription == null && description != null) {
                            communicationControl.createCommunicationEventPurposeDescription(communicationEventPurpose, getPreferredLanguage(), description, partyPK);
                        } else if(communicationEventPurposeDescription != null && description == null) {
                            communicationControl.deleteCommunicationEventPurposeDescription(communicationEventPurposeDescription, partyPK);
                        } else if(communicationEventPurposeDescription != null && description != null) {
                            CommunicationEventPurposeDescriptionValue communicationEventPurposeDescriptionValue = communicationControl.getCommunicationEventPurposeDescriptionValue(communicationEventPurposeDescription);
                            
                            communicationEventPurposeDescriptionValue.setDescription(description);
                            communicationControl.updateCommunicationEventPurposeDescriptionFromValue(communicationEventPurposeDescriptionValue, partyPK);
                        }
                    } finally {
                        unlockEntity(communicationEventPurpose);
                    }
                } else {
                    addExecutionError(ExecutionErrors.EntityLockStale.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCommunicationEventPurposeName.name(), communicationEventPurposeName);
            }
        }
        
        return result;
    }
    
}

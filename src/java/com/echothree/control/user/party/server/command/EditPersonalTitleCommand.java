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

import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.edit.PersonalTitleEdit;
import com.echothree.control.user.party.common.form.EditPersonalTitleForm;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.PersonalTitleSpec;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditPersonalTitleCommand
        extends BaseEditCommand<PersonalTitleSpec, PersonalTitleEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("PersonalTitleId", FieldType.ID, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L),
            new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
            new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
        ));
    }
    
    /** Creates a new instance of EditPersonalTitleCommand */
    public EditPersonalTitleCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var result = PartyResultFactory.getEditPersonalTitleResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            var personalTitleId = spec.getPersonalTitleId();
            var personalTitle = partyControl.convertPersonalTitleIdToEntity(personalTitleId, EntityPermission.READ_ONLY);
            
            if(personalTitle != null) {
                result.setPersonalTitle(partyControl.getPersonalTitleTransfer(getUserVisit(), personalTitle));
                
                if(lockEntity(personalTitle)) {
                    var edit = PartyEditFactory.getPersonalTitleEdit();
                    var personalTitleDetail = personalTitle.getLastDetail();
                    
                    result.setEdit(edit);
                    edit.setDescription(personalTitleDetail.getDescription());
                    edit.setIsDefault(personalTitleDetail.getIsDefault().toString());
                    edit.setSortOrder(personalTitleDetail.getSortOrder().toString());
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(personalTitle));
            } else {
                addExecutionError(ExecutionErrors.UnknownPersonalTitleId.name(), personalTitleId);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            var personalTitleId = spec.getPersonalTitleId();
            var personalTitlePK = partyControl.convertPersonalTitleIdToPK(personalTitleId);
            
            if(personalTitlePK != null) {
                if(lockEntityForUpdate(personalTitlePK)) {
                    try {
                        var partyPK = getPartyPK();
                        var personalTitleDetailValue = partyControl.getPersonalTitleDetailValueByPKForUpdate(personalTitlePK);
                        
                        personalTitleDetailValue.setDescription(edit.getDescription());
                        personalTitleDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                        personalTitleDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                        
                        partyControl.updatePersonalTitleFromValue(personalTitleDetailValue, partyPK);
                    } finally {
                        unlockEntity(personalTitlePK);
                    }
                } else {
                    addExecutionError(ExecutionErrors.EntityLockStale.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPersonalTitleId.name(), personalTitleId);
            }
        }
        
        return result;
    }
    
}

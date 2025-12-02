// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.control.user.party.common.edit.GenderEdit;
import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.form.EditGenderForm;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.GenderSpec;
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
public class EditGenderCommand
        extends BaseEditCommand<GenderSpec, GenderEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        List<FieldDefinition> temp = new ArrayList<>(1);
        temp.add(new FieldDefinition("GenderName", FieldType.ENTITY_NAME, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(4);
        temp.add(new FieldDefinition("GenderName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null));
        temp.add(new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null));
        temp.add(new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditGenderCommand */
    public EditGenderCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var result = PartyResultFactory.getEditGenderResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            var genderName = spec.getGenderName();
            var gender = partyControl.getGenderByName(genderName);
            
            if(gender != null) {
                result.setGender(partyControl.getGenderTransfer(getUserVisit(), gender));
                
                if(lockEntity(gender)) {
                    var genderDescription = partyControl.getGenderDescription(gender, getPreferredLanguage());
                    var edit = PartyEditFactory.getGenderEdit();
                    var genderDetail = gender.getLastDetail();
                    
                    result.setEdit(edit);
                    edit.setGenderName(genderDetail.getGenderName());
                    edit.setIsDefault(genderDetail.getIsDefault().toString());
                    edit.setSortOrder(genderDetail.getSortOrder().toString());
                    
                    if(genderDescription != null)
                        edit.setDescription(genderDescription.getDescription());
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(gender));
            } else {
                addExecutionError(ExecutionErrors.UnknownGenderName.name(), genderName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            var genderName = spec.getGenderName();
            var gender = partyControl.getGenderByNameForUpdate(genderName);
            
            if(gender != null) {
                genderName = edit.getGenderName();
                var duplicateGender = partyControl.getGenderByName(genderName);
                
                if(duplicateGender == null || gender.equals(duplicateGender)) {
                    if(lockEntityForUpdate(gender)) {
                        try {
                            var partyPK = getPartyPK();
                            var genderDetailValue = partyControl.getGenderDetailValueForUpdate(gender);
                            var genderDescription = partyControl.getGenderDescriptionForUpdate(gender, getPreferredLanguage());
                            var description = edit.getDescription();
                            
                            genderDetailValue.setGenderName(edit.getGenderName());
                            genderDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                            genderDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                            
                            partyControl.updateGenderFromValue(genderDetailValue, partyPK);
                            
                            if(genderDescription == null && description != null) {
                                partyControl.createGenderDescription(gender, getPreferredLanguage(), description, partyPK);
                            } else if(genderDescription != null && description == null) {
                                partyControl.deleteGenderDescription(genderDescription, partyPK);
                            } else if(genderDescription != null && description != null) {
                                var genderDescriptionValue = partyControl.getGenderDescriptionValue(genderDescription);
                                
                                genderDescriptionValue.setDescription(description);
                                partyControl.updateGenderDescriptionFromValue(genderDescriptionValue, partyPK);
                            }
                        } finally {
                            unlockEntity(gender);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateGenderName.name(), genderName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownGenderName.name(), genderName);
            }
            
            if(hasExecutionErrors()) {
                result.setGender(partyControl.getGenderTransfer(getUserVisit(), gender));
                result.setEntityLock(getEntityLockTransfer(gender));
            }
        }
        
        return result;
    }
    
}

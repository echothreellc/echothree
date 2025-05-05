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

package com.echothree.control.user.employee.server.command;

import com.echothree.control.user.employee.common.edit.EmployeeEditFactory;
import com.echothree.control.user.employee.common.edit.SkillTypeDescriptionEdit;
import com.echothree.control.user.employee.common.form.EditSkillTypeDescriptionForm;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.control.user.employee.common.spec.SkillTypeDescriptionSpec;
import com.echothree.model.control.employee.server.control.EmployeeControl;
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

public class EditSkillTypeDescriptionCommand
        extends BaseEditCommand<SkillTypeDescriptionSpec, SkillTypeDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        List<FieldDefinition> temp = new ArrayList<>(2);
        temp.add(new FieldDefinition("SkillTypeName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(1);
        temp.add(new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditSkillTypeDescriptionCommand */
    public EditSkillTypeDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        var result = EmployeeResultFactory.getEditSkillTypeDescriptionResult();
        var skillTypeName = spec.getSkillTypeName();
        var skillType = employeeControl.getSkillTypeByName(skillTypeName);
        
        if(skillType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    var skillTypeDescription = employeeControl.getSkillTypeDescription(skillType, language);
                    
                    if(skillTypeDescription != null) {
                        result.setSkillTypeDescription(employeeControl.getSkillTypeDescriptionTransfer(getUserVisit(), skillTypeDescription));
                        
                        if(lockEntity(skillType)) {
                            var edit = EmployeeEditFactory.getSkillTypeDescriptionEdit();
                            
                            result.setEdit(edit);
                            edit.setDescription(skillTypeDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(skillType));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownSkillTypeDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    var skillTypeDescriptionValue = employeeControl.getSkillTypeDescriptionValueForUpdate(skillType, language);
                    
                    if(skillTypeDescriptionValue != null) {
                        if(lockEntityForUpdate(skillType)) {
                            try {
                                var description = edit.getDescription();
                                
                                skillTypeDescriptionValue.setDescription(description);
                                
                                employeeControl.updateSkillTypeDescriptionFromValue(skillTypeDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(skillType);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownSkillTypeDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSkillTypeName.name(), skillTypeName);
        }
        
        return result;
    }
    
}

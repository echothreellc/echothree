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

package com.echothree.control.user.employee.server.command;

import com.echothree.control.user.employee.common.edit.EmployeeEditFactory;
import com.echothree.control.user.employee.common.edit.SkillTypeEdit;
import com.echothree.control.user.employee.common.form.EditSkillTypeForm;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.control.user.employee.common.spec.SkillTypeSpec;
import com.echothree.model.control.employee.server.control.EmployeeControl;
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
public class EditSkillTypeCommand
        extends BaseEditCommand<SkillTypeSpec, SkillTypeEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        List<FieldDefinition> temp = new ArrayList<>(1);
        temp.add(new FieldDefinition("SkillTypeName", FieldType.ENTITY_NAME, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(4);
        temp.add(new FieldDefinition("SkillTypeName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null));
        temp.add(new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null));
        temp.add(new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditSkillTypeCommand */
    public EditSkillTypeCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        var result = EmployeeResultFactory.getEditSkillTypeResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            var skillTypeName = spec.getSkillTypeName();
            var skillType = employeeControl.getSkillTypeByName(skillTypeName);
            
            if(skillType != null) {
                result.setSkillType(employeeControl.getSkillTypeTransfer(getUserVisit(), skillType));
                
                if(lockEntity(skillType)) {
                    var skillTypeDescription = employeeControl.getSkillTypeDescription(skillType, getPreferredLanguage());
                    var edit = EmployeeEditFactory.getSkillTypeEdit();
                    var skillTypeDetail = skillType.getLastDetail();
                    
                    result.setEdit(edit);
                    edit.setSkillTypeName(skillTypeDetail.getSkillTypeName());
                    edit.setIsDefault(skillTypeDetail.getIsDefault().toString());
                    edit.setSortOrder(skillTypeDetail.getSortOrder().toString());
                    
                    if(skillTypeDescription != null)
                        edit.setDescription(skillTypeDescription.getDescription());
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(skillType));
            } else {
                addExecutionError(ExecutionErrors.UnknownSkillTypeName.name(), skillTypeName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            var skillTypeName = spec.getSkillTypeName();
            var skillType = employeeControl.getSkillTypeByNameForUpdate(skillTypeName);
            
            if(skillType != null) {
                if(lockEntityForUpdate(skillType)) {
                    try {
                        var partyPK = getPartyPK();
                        var skillTypeDetailValue = employeeControl.getSkillTypeDetailValueForUpdate(skillType);
                        var skillTypeDescription = employeeControl.getSkillTypeDescriptionForUpdate(skillType, getPreferredLanguage());
                        var description = edit.getDescription();
                        
                        skillTypeDetailValue.setSkillTypeName(edit.getSkillTypeName());
                        skillTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                        skillTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                        
                        employeeControl.updateSkillTypeFromValue(skillTypeDetailValue, partyPK);
                        
                        if(skillTypeDescription == null && description != null) {
                            employeeControl.createSkillTypeDescription(skillType, getPreferredLanguage(), description, partyPK);
                        } else if(skillTypeDescription != null && description == null) {
                            employeeControl.deleteSkillTypeDescription(skillTypeDescription, partyPK);
                        } else if(skillTypeDescription != null && description != null) {
                            var skillTypeDescriptionValue = employeeControl.getSkillTypeDescriptionValue(skillTypeDescription);
                            
                            skillTypeDescriptionValue.setDescription(description);
                            employeeControl.updateSkillTypeDescriptionFromValue(skillTypeDescriptionValue, partyPK);
                        }
                    } finally {
                        unlockEntity(skillType);
                    }
                } else {
                    addExecutionError(ExecutionErrors.EntityLockStale.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSkillTypeName.name(), skillTypeName);
            }
        }
        
        return result;
    }
    
}

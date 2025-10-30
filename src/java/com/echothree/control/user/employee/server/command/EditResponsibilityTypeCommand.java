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
import com.echothree.control.user.employee.common.edit.ResponsibilityTypeEdit;
import com.echothree.control.user.employee.common.form.EditResponsibilityTypeForm;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.control.user.employee.common.spec.ResponsibilityTypeSpec;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditResponsibilityTypeCommand
        extends BaseEditCommand<ResponsibilityTypeSpec, ResponsibilityTypeEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        List<FieldDefinition> temp = new ArrayList<>(1);
        temp.add(new FieldDefinition("ResponsibilityTypeName", FieldType.ENTITY_NAME, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(4);
        temp.add(new FieldDefinition("ResponsibilityTypeName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null));
        temp.add(new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null));
        temp.add(new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditResponsibilityTypeCommand */
    public EditResponsibilityTypeCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        var result = EmployeeResultFactory.getEditResponsibilityTypeResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            var responsibilityTypeName = spec.getResponsibilityTypeName();
            var responsibilityType = employeeControl.getResponsibilityTypeByName(responsibilityTypeName);
            
            if(responsibilityType != null) {
                result.setResponsibilityType(employeeControl.getResponsibilityTypeTransfer(getUserVisit(), responsibilityType));
                
                if(lockEntity(responsibilityType)) {
                    var responsibilityTypeDescription = employeeControl.getResponsibilityTypeDescription(responsibilityType, getPreferredLanguage());
                    var edit = EmployeeEditFactory.getResponsibilityTypeEdit();
                    var responsibilityTypeDetail = responsibilityType.getLastDetail();
                    
                    result.setEdit(edit);
                    edit.setResponsibilityTypeName(responsibilityTypeDetail.getResponsibilityTypeName());
                    edit.setIsDefault(responsibilityTypeDetail.getIsDefault().toString());
                    edit.setSortOrder(responsibilityTypeDetail.getSortOrder().toString());
                    
                    if(responsibilityTypeDescription != null)
                        edit.setDescription(responsibilityTypeDescription.getDescription());
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(responsibilityType));
            } else {
                addExecutionError(ExecutionErrors.UnknownResponsibilityTypeName.name(), responsibilityTypeName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            var responsibilityTypeName = spec.getResponsibilityTypeName();
            var responsibilityType = employeeControl.getResponsibilityTypeByNameForUpdate(responsibilityTypeName);
            
            if(responsibilityType != null) {
                if(lockEntityForUpdate(responsibilityType)) {
                    try {
                        var partyPK = getPartyPK();
                        var responsibilityTypeDetailValue = employeeControl.getResponsibilityTypeDetailValueForUpdate(responsibilityType);
                        var responsibilityTypeDescription = employeeControl.getResponsibilityTypeDescriptionForUpdate(responsibilityType, getPreferredLanguage());
                        var description = edit.getDescription();
                        
                        responsibilityTypeDetailValue.setResponsibilityTypeName(edit.getResponsibilityTypeName());
                        responsibilityTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                        responsibilityTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                        
                        employeeControl.updateResponsibilityTypeFromValue(responsibilityTypeDetailValue, partyPK);
                        
                        if(responsibilityTypeDescription == null && description != null) {
                            employeeControl.createResponsibilityTypeDescription(responsibilityType, getPreferredLanguage(), description, partyPK);
                        } else if(responsibilityTypeDescription != null && description == null) {
                            employeeControl.deleteResponsibilityTypeDescription(responsibilityTypeDescription, partyPK);
                        } else if(responsibilityTypeDescription != null && description != null) {
                            var responsibilityTypeDescriptionValue = employeeControl.getResponsibilityTypeDescriptionValue(responsibilityTypeDescription);
                            
                            responsibilityTypeDescriptionValue.setDescription(description);
                            employeeControl.updateResponsibilityTypeDescriptionFromValue(responsibilityTypeDescriptionValue, partyPK);
                        }
                    } finally {
                        unlockEntity(responsibilityType);
                    }
                } else {
                    addExecutionError(ExecutionErrors.EntityLockStale.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownResponsibilityTypeName.name(), responsibilityTypeName);
            }
        }
        
        return result;
    }
    
}

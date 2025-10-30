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

package com.echothree.control.user.uom.server.command;

import com.echothree.control.user.uom.common.edit.UnitOfMeasureKindUseEdit;
import com.echothree.control.user.uom.common.edit.UomEditFactory;
import com.echothree.control.user.uom.common.form.EditUnitOfMeasureKindUseForm;
import com.echothree.control.user.uom.common.result.UomResultFactory;
import com.echothree.control.user.uom.common.spec.UnitOfMeasureKindUseSpec;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditUnitOfMeasureKindUseCommand
        extends BaseEditCommand<UnitOfMeasureKindUseSpec, UnitOfMeasureKindUseEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureKindUseTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditUnitOfMeasureKindUseCommand */
    public EditUnitOfMeasureKindUseCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var uomControl = Session.getModelController(UomControl.class);
        var result = UomResultFactory.getEditUnitOfMeasureKindUseResult();
        var unitOfMeasureKindName = spec.getUnitOfMeasureKindName();
        var unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);
        
        if(unitOfMeasureKind != null) {
            var unitOfMeasureKindUseTypeName = spec.getUnitOfMeasureKindUseTypeName();
            var unitOfMeasureKindUseType = uomControl.getUnitOfMeasureKindUseTypeByName(unitOfMeasureKindUseTypeName);
            
            if(unitOfMeasureKindUseType != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    var unitOfMeasureKindUse = uomControl.getUnitOfMeasureKindUse(unitOfMeasureKindUseType, unitOfMeasureKind);
                    
                    if(unitOfMeasureKindUse != null) {
                        result.setUnitOfMeasureKindUse(uomControl.getUnitOfMeasureKindUseTransfer(getUserVisit(), unitOfMeasureKindUse));
                        
                        if(lockEntity(unitOfMeasureKind)) {
                            var edit = UomEditFactory.getUnitOfMeasureKindUseEdit();
                            
                            result.setEdit(edit);
                            edit.setIsDefault(unitOfMeasureKindUse.getIsDefault().toString());
                            edit.setSortOrder(unitOfMeasureKindUse.getSortOrder().toString());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(unitOfMeasureKind));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindUse.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    var unitOfMeasureKindUseValue = uomControl.getUnitOfMeasureKindUseValueForUpdate(unitOfMeasureKindUseType, unitOfMeasureKind);
                    
                    if(unitOfMeasureKindUseValue != null) {
                        if(lockEntityForUpdate(unitOfMeasureKind)) {
                            try {
                                unitOfMeasureKindUseValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                unitOfMeasureKindUseValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                
                                uomControl.updateUnitOfMeasureKindUseFromValue(unitOfMeasureKindUseValue, getPartyPK());
                            } finally {
                                unlockEntity(unitOfMeasureKind);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindUse.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindUseTypeName.name(), unitOfMeasureKindUseTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
        }
        
        return result;
    }
    
}

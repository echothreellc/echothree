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

import com.echothree.control.user.uom.common.edit.UnitOfMeasureEquivalentEdit;
import com.echothree.control.user.uom.common.edit.UomEditFactory;
import com.echothree.control.user.uom.common.form.EditUnitOfMeasureEquivalentForm;
import com.echothree.control.user.uom.common.result.UomResultFactory;
import com.echothree.control.user.uom.common.spec.UnitOfMeasureEquivalentSpec;
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

public class EditUnitOfMeasureEquivalentCommand
        extends BaseEditCommand<UnitOfMeasureEquivalentSpec, UnitOfMeasureEquivalentEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FromUnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ToUnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ToQuantity", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditUnitOfMeasureEquivalentCommand */
    public EditUnitOfMeasureEquivalentCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var uomControl = Session.getModelController(UomControl.class);
        var result = UomResultFactory.getEditUnitOfMeasureEquivalentResult();
        var unitOfMeasureKindName = spec.getUnitOfMeasureKindName();
        var unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);
        
        if(unitOfMeasureKind != null) {
            var fromUnitOfMeasureTypeName = spec.getFromUnitOfMeasureTypeName();
            var fromUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind,
                    fromUnitOfMeasureTypeName);
            
            if(fromUnitOfMeasureType != null) {
                var toUnitOfMeasureTypeName = spec.getToUnitOfMeasureTypeName();
                var toUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind,
                        toUnitOfMeasureTypeName);
                
                if(toUnitOfMeasureType != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        var unitOfMeasureEquivalent = uomControl.getUnitOfMeasureEquivalent(fromUnitOfMeasureType,
                                toUnitOfMeasureType);
                        
                        if(unitOfMeasureEquivalent != null) {
                            result.setUnitOfMeasureEquivalent(uomControl.getUnitOfMeasureEquivalentTransfer(getUserVisit(),
                                    unitOfMeasureEquivalent));
                            
                            if(lockEntity(unitOfMeasureKind)) {
                                var edit = UomEditFactory.getUnitOfMeasureEquivalentEdit();
                                
                                result.setEdit(edit);
                                edit.setToQuantity(unitOfMeasureEquivalent.getToQuantity().toString());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                            
                            result.setEntityLock(getEntityLockTransfer(unitOfMeasureKind));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureEquivalent.name());
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        var unitOfMeasureEquivalentValue = uomControl.getUnitOfMeasureEquivalentValueForUpdate(fromUnitOfMeasureType,
                                toUnitOfMeasureType);
                        
                        if(unitOfMeasureEquivalentValue != null) {
                            if(lockEntityForUpdate(unitOfMeasureKind)) {
                                try {
                                    unitOfMeasureEquivalentValue.setToQuantity(Long.valueOf(edit.getToQuantity()));
                                    
                                    uomControl.updateUnitOfMeasureEquivalentFromValue(unitOfMeasureEquivalentValue, getPartyPK());
                                } finally {
                                    unlockEntity(unitOfMeasureKind);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureEquivalent.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownToUnitOfMeasureTypeName.name(), toUnitOfMeasureTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownFromUnitOfMeasureTypeName.name(), fromUnitOfMeasureTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
        }
        
        return result;
    }
    
}

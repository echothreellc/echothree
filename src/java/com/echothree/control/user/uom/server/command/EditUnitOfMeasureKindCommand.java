// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.control.user.uom.common.edit.UnitOfMeasureKindEdit;
import com.echothree.control.user.uom.common.edit.UomEditFactory;
import com.echothree.control.user.uom.common.form.EditUnitOfMeasureKindForm;
import com.echothree.control.user.uom.common.result.EditUnitOfMeasureKindResult;
import com.echothree.control.user.uom.common.result.UomResultFactory;
import com.echothree.control.user.uom.common.spec.UnitOfMeasureKindSpec;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindDescription;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindDetail;
import com.echothree.model.data.uom.server.value.UnitOfMeasureKindDescriptionValue;
import com.echothree.model.data.uom.server.value.UnitOfMeasureKindDetailValue;
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

public class EditUnitOfMeasureKindCommand
        extends BaseEditCommand<UnitOfMeasureKindSpec, UnitOfMeasureKindEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FractionDigits", FieldType.UNSIGNED_INTEGER, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditUnitOfMeasureKindCommand */
    public EditUnitOfMeasureKindCommand(UserVisitPK userVisitPK, EditUnitOfMeasureKindForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var uomControl = Session.getModelController(UomControl.class);
        EditUnitOfMeasureKindResult result = UomResultFactory.getEditUnitOfMeasureKindResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            String unitOfMeasureKindName = spec.getUnitOfMeasureKindName();
            UnitOfMeasureKind unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);
            
            if(unitOfMeasureKind != null) {
                result.setUnitOfMeasureKind(uomControl.getUnitOfMeasureKindTransfer(getUserVisit(), unitOfMeasureKind));
                
                if(lockEntity(unitOfMeasureKind)) {
                    UnitOfMeasureKindDescription unitOfMeasureKindDescription = uomControl.getUnitOfMeasureKindDescription(unitOfMeasureKind, getPreferredLanguage());
                    UnitOfMeasureKindEdit edit = UomEditFactory.getUnitOfMeasureKindEdit();
                    UnitOfMeasureKindDetail unitOfMeasureKindDetail = unitOfMeasureKind.getLastDetail();
                    
                    result.setEdit(edit);
                    edit.setUnitOfMeasureKindName(unitOfMeasureKindDetail.getUnitOfMeasureKindName());
                    edit.setFractionDigits(unitOfMeasureKindDetail.getFractionDigits().toString());
                    edit.setIsDefault(unitOfMeasureKindDetail.getIsDefault().toString());
                    edit.setSortOrder(unitOfMeasureKindDetail.getSortOrder().toString());
                    
                    if(unitOfMeasureKindDescription != null)
                        edit.setDescription(unitOfMeasureKindDescription.getDescription());
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(unitOfMeasureKind));
            } else {
                addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            String unitOfMeasureKindName = spec.getUnitOfMeasureKindName();
            UnitOfMeasureKind unitOfMeasureKind = uomControl.getUnitOfMeasureKindByNameForUpdate(unitOfMeasureKindName);
            
            if(unitOfMeasureKind != null) {
                unitOfMeasureKindName = edit.getUnitOfMeasureKindName();
                UnitOfMeasureKind duplicateUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);
                
                if(duplicateUnitOfMeasureKind == null || unitOfMeasureKind.equals(duplicateUnitOfMeasureKind)) {
                    if(lockEntityForUpdate(unitOfMeasureKind)) {
                        try {
                            var partyPK = getPartyPK();
                            UnitOfMeasureKindDetailValue unitOfMeasureKindDetailValue = uomControl.getUnitOfMeasureKindDetailValueForUpdate(unitOfMeasureKind);
                            UnitOfMeasureKindDescription unitOfMeasureKindDescription = uomControl.getUnitOfMeasureKindDescriptionForUpdate(unitOfMeasureKind, getPreferredLanguage());
                            String description = edit.getDescription();
                            
                            unitOfMeasureKindDetailValue.setUnitOfMeasureKindName(edit.getUnitOfMeasureKindName());
                            unitOfMeasureKindDetailValue.setFractionDigits(Integer.valueOf(edit.getFractionDigits()));
                            unitOfMeasureKindDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                            unitOfMeasureKindDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                            
                            uomControl.updateUnitOfMeasureKindFromValue(unitOfMeasureKindDetailValue, partyPK);
                            
                            if(unitOfMeasureKindDescription == null && description != null) {
                                uomControl.createUnitOfMeasureKindDescription(unitOfMeasureKind, getPreferredLanguage(), description, partyPK);
                            } else if(unitOfMeasureKindDescription != null && description == null) {
                                uomControl.deleteUnitOfMeasureKindDescription(unitOfMeasureKindDescription, partyPK);
                            } else if(unitOfMeasureKindDescription != null && description != null) {
                                UnitOfMeasureKindDescriptionValue unitOfMeasureKindDescriptionValue = uomControl.getUnitOfMeasureKindDescriptionValue(unitOfMeasureKindDescription);
                                
                                unitOfMeasureKindDescriptionValue.setDescription(description);
                                uomControl.updateUnitOfMeasureKindDescriptionFromValue(unitOfMeasureKindDescriptionValue, partyPK);
                            }
                        } finally {
                            unlockEntity(unitOfMeasureKind);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateUnitOfMeasureKindName.name(), unitOfMeasureKindName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
            }
            
            if(hasExecutionErrors()) {
                result.setUnitOfMeasureKind(uomControl.getUnitOfMeasureKindTransfer(getUserVisit(), unitOfMeasureKind));
                result.setEntityLock(getEntityLockTransfer(unitOfMeasureKind));
            }
        }
        
        return result;
    }
    
}

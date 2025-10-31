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

import com.echothree.control.user.uom.common.edit.UnitOfMeasureTypeEdit;
import com.echothree.control.user.uom.common.edit.UomEditFactory;
import com.echothree.control.user.uom.common.form.EditUnitOfMeasureTypeForm;
import com.echothree.control.user.uom.common.result.UomResultFactory;
import com.echothree.control.user.uom.common.spec.UnitOfMeasureTypeSpec;
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
public class EditUnitOfMeasureTypeCommand
        extends BaseEditCommand<UnitOfMeasureTypeSpec, UnitOfMeasureTypeEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SymbolPositionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SuppressSymbolSeparator", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("SingularDescription", FieldType.STRING, false, 1L, 132L),
                new FieldDefinition("PluralDescription", FieldType.STRING, false, 1L, 132L),
                new FieldDefinition("Symbol", FieldType.STRING, true, 1L, 20L)
                ));
    }
    
    /** Creates a new instance of EditUnitOfMeasureTypeCommand */
    public EditUnitOfMeasureTypeCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var uomControl = Session.getModelController(UomControl.class);
        var result = UomResultFactory.getEditUnitOfMeasureTypeResult();
        var unitOfMeasureKindName = spec.getUnitOfMeasureKindName();
        var unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);
        
        if(unitOfMeasureKind != null) {
            if(editMode.equals(EditMode.LOCK)) {
                var unitOfMeasureTypeName = spec.getUnitOfMeasureTypeName();
                var unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);
                
                if(unitOfMeasureType != null) {
                    result.setUnitOfMeasureType(uomControl.getUnitOfMeasureTypeTransfer(getUserVisit(), unitOfMeasureType));
                    
                    if(lockEntity(unitOfMeasureType)) {
                        var unitOfMeasureTypeDescription = uomControl.getUnitOfMeasureTypeDescription(unitOfMeasureType, getPreferredLanguage());
                        var edit = UomEditFactory.getUnitOfMeasureTypeEdit();
                        var unitOfMeasureTypeDetail = unitOfMeasureType.getLastDetail();
                        var symbolPositionDetail = unitOfMeasureTypeDetail.getSymbolPosition().getLastDetail();
                        
                        result.setEdit(edit);
                        edit.setUnitOfMeasureTypeName(unitOfMeasureTypeDetail.getUnitOfMeasureTypeName());
                        edit.setSymbolPositionName(symbolPositionDetail.getSymbolPositionName());
                        edit.setSuppressSymbolSeparator(unitOfMeasureTypeDetail.getSuppressSymbolSeparator().toString());
                        edit.setIsDefault(unitOfMeasureTypeDetail.getIsDefault().toString());
                        edit.setSortOrder(unitOfMeasureTypeDetail.getSortOrder().toString());
                        
                        if(unitOfMeasureTypeDescription != null) {
                            edit.setSingularDescription(unitOfMeasureTypeDescription.getSingularDescription());
                            edit.setPluralDescription(unitOfMeasureTypeDescription.getPluralDescription());
                            edit.setSymbol(unitOfMeasureTypeDescription.getSymbol());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                    }
                    
                    result.setEntityLock(getEntityLockTransfer(unitOfMeasureType));
                } else {
                    addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
                }
            } else if(editMode.equals(EditMode.UPDATE)) {
                var unitOfMeasureTypeName = spec.getUnitOfMeasureTypeName();
                var unitOfMeasureType = uomControl.getUnitOfMeasureTypeByNameForUpdate(unitOfMeasureKind, unitOfMeasureTypeName);
                
                if(unitOfMeasureType != null) {
                    unitOfMeasureTypeName = edit.getUnitOfMeasureTypeName();
                    var duplicateUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);
                    
                    if(duplicateUnitOfMeasureType == null || unitOfMeasureType.equals(duplicateUnitOfMeasureType)) {
                        var singularDescription = edit.getSingularDescription();
                        var pluralDescription = edit.getPluralDescription();
                        var symbol = edit.getSymbol();
                        var descriptionCount = (singularDescription == null ? 0 : 1) + (pluralDescription == null ? 0 : 1) + (symbol == null ? 0 : 1);
                        
                        if(descriptionCount == 0 || descriptionCount == 3) {
                            if(lockEntityForUpdate(unitOfMeasureType)) {
                                try {
                                    var partyPK = getPartyPK();
                                    var unitOfMeasureTypeDetailValue = uomControl.getUnitOfMeasureTypeDetailValueForUpdate(unitOfMeasureType);
                                    var unitOfMeasureTypeDescription = uomControl.getUnitOfMeasureTypeDescriptionForUpdate(unitOfMeasureType, getPreferredLanguage());
                                    
                                    unitOfMeasureTypeDetailValue.setUnitOfMeasureTypeName(edit.getUnitOfMeasureTypeName());
                                    unitOfMeasureTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                    unitOfMeasureTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                    
                                    uomControl.updateUnitOfMeasureTypeFromValue(unitOfMeasureTypeDetailValue, partyPK);
                                    
                                    if(unitOfMeasureTypeDescription == null && singularDescription != null) {
                                        uomControl.createUnitOfMeasureTypeDescription(unitOfMeasureType, getPreferredLanguage(), singularDescription, pluralDescription, symbol, partyPK);
                                    } else if(unitOfMeasureTypeDescription != null && singularDescription == null) {
                                        uomControl.deleteUnitOfMeasureTypeDescription(unitOfMeasureTypeDescription, partyPK);
                                    } else if(unitOfMeasureTypeDescription != null && singularDescription != null) {
                                        var unitOfMeasureTypeDescriptionValue = uomControl.getUnitOfMeasureTypeDescriptionValue(unitOfMeasureTypeDescription);
                                        
                                        unitOfMeasureTypeDescriptionValue.setSingularDescription(singularDescription);
                                        unitOfMeasureTypeDescriptionValue.setPluralDescription(pluralDescription);
                                        unitOfMeasureTypeDescriptionValue.setSymbol(symbol);
                                        uomControl.updateUnitOfMeasureTypeDescriptionFromValue(unitOfMeasureTypeDescriptionValue, partyPK);
                                    }
                                } finally {
                                    unlockEntity(unitOfMeasureType);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            if(singularDescription == null) {
                                addExecutionError(ExecutionErrors.MissingSingularDescription.name());
                            }
                            
                            if(pluralDescription == null) {
                                addExecutionError(ExecutionErrors.MissingPluralDescription.name());
                            }
                            
                            if(symbol == null) {
                                addExecutionError(ExecutionErrors.MissingSymbol.name());
                            }
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
                }
                
                if(hasExecutionErrors()) {
                    result.setUnitOfMeasureType(uomControl.getUnitOfMeasureTypeTransfer(getUserVisit(), unitOfMeasureType));
                    result.setEntityLock(getEntityLockTransfer(unitOfMeasureType));
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
        }
        
        return result;
    }
    
}

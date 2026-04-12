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

package com.echothree.control.user.uom.server.command;

import com.echothree.control.user.uom.common.edit.UnitOfMeasureKindUseEdit;
import com.echothree.control.user.uom.common.edit.UomEditFactory;
import com.echothree.control.user.uom.common.result.EditUnitOfMeasureKindUseResult;
import com.echothree.control.user.uom.common.result.UomResultFactory;
import com.echothree.control.user.uom.common.spec.UnitOfMeasureKindUseSpec;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUse;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditUnitOfMeasureKindUseCommand
        extends BaseAbstractEditCommand<UnitOfMeasureKindUseSpec, UnitOfMeasureKindUseEdit, EditUnitOfMeasureKindUseResult, UnitOfMeasureKindUse, UnitOfMeasureKind> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureKindUseTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
        );
    }
    
    @Inject
    UomControl uomControl;
    
    /** Creates a new instance of EditUnitOfMeasureKindUseCommand */
    public EditUnitOfMeasureKindUseCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditUnitOfMeasureKindUseResult getResult() {
        return UomResultFactory.getEditUnitOfMeasureKindUseResult();
    }

    @Override
    public UnitOfMeasureKindUseEdit getEdit() {
        return UomEditFactory.getUnitOfMeasureKindUseEdit();
    }

    @Override
    public UnitOfMeasureKindUse getEntity(EditUnitOfMeasureKindUseResult result) {
        UnitOfMeasureKindUse unitOfMeasureKindUse = null;
        var unitOfMeasureKindName = spec.getUnitOfMeasureKindName();
        var unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);

        if(unitOfMeasureKind != null) {
            var unitOfMeasureKindUseTypeName = spec.getUnitOfMeasureKindUseTypeName();
            var unitOfMeasureKindUseType = uomControl.getUnitOfMeasureKindUseTypeByName(unitOfMeasureKindUseTypeName);

            if(unitOfMeasureKindUseType != null) {
                unitOfMeasureKindUse = uomControl.getUnitOfMeasureKindUse(unitOfMeasureKindUseType, unitOfMeasureKind, editModeToEntityPermission(editMode));

                if(unitOfMeasureKindUse == null) {
                    addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindUse.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindUseTypeName.name(), unitOfMeasureKindUseTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
        }

        return unitOfMeasureKindUse;
    }

    @Override
    public UnitOfMeasureKind getLockEntity(UnitOfMeasureKindUse unitOfMeasureKindUse) {
        return unitOfMeasureKindUse.getUnitOfMeasureKind();
    }

    @Override
    public void fillInResult(EditUnitOfMeasureKindUseResult result, UnitOfMeasureKindUse unitOfMeasureKindUse) {
        result.setUnitOfMeasureKindUse(uomControl.getUnitOfMeasureKindUseTransfer(getUserVisit(), unitOfMeasureKindUse));
    }

    @Override
    public void doLock(UnitOfMeasureKindUseEdit edit, UnitOfMeasureKindUse unitOfMeasureKindUse) {
        edit.setIsDefault(unitOfMeasureKindUse.getIsDefault().toString());
        edit.setSortOrder(unitOfMeasureKindUse.getSortOrder().toString());
    }

    @Override
    public void doUpdate(UnitOfMeasureKindUse unitOfMeasureKindUse) {
        var unitOfMeasureKindUseValue = uomControl.getUnitOfMeasureKindUseValue(unitOfMeasureKindUse);

        unitOfMeasureKindUseValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        unitOfMeasureKindUseValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        uomControl.updateUnitOfMeasureKindUseFromValue(unitOfMeasureKindUseValue, getPartyPK());
    }

}

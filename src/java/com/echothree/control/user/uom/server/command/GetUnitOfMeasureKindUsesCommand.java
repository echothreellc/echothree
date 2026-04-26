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

import com.echothree.control.user.uom.common.form.GetUnitOfMeasureKindUsesForm;
import com.echothree.control.user.uom.common.result.UomResultFactory;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureKindLogic;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureKindUseLogic;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUse;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUseType;
import com.echothree.model.data.uom.server.factory.UnitOfMeasureKindUseFactory;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetUnitOfMeasureKindUsesCommand
        extends BasePaginatedMultipleEntitiesCommand<UnitOfMeasureKindUse, GetUnitOfMeasureKindUsesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("UnitOfMeasureKindUseTypeName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    /** Creates a new instance of GetUnitOfMeasureKindUsesCommand */
    public GetUnitOfMeasureKindUsesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Inject
    UomControl uomControl;

    @Inject
    UnitOfMeasureKindLogic unitOfMeasureKindLogic;

    @Inject
    UnitOfMeasureKindUseLogic unitOfMeasureKindUseLogic;

    private UnitOfMeasureKind unitOfMeasureKind;
    private UnitOfMeasureKindUseType unitOfMeasureKindUseType;
    
    @Override
    protected void handleForm() {
        var unitOfMeasureKindName = form.getUnitOfMeasureKindName();
        var unitOfMeasureKindUseTypeName = form.getUnitOfMeasureKindUseTypeName();
        var parameterCount = (unitOfMeasureKindName == null ? 0 : 1) + (unitOfMeasureKindUseTypeName == null ? 0 : 1);

        if(parameterCount == 1) {
            if(unitOfMeasureKindName != null) {
                unitOfMeasureKind = unitOfMeasureKindLogic.getUnitOfMeasureKindByName(this, unitOfMeasureKindName);
            } else {
                unitOfMeasureKindUseType = unitOfMeasureKindUseLogic.getUnitOfMeasureKindUseTypeByName(this, unitOfMeasureKindUseTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long totalEntities = null;

        if(!hasExecutionErrors()) {
            if(unitOfMeasureKind != null) {
                totalEntities = uomControl.countUnitOfMeasureKindUsesByUnitOfMeasureKind(unitOfMeasureKind);
            } else {
                totalEntities = uomControl.countUnitOfMeasureKindUsesByUnitOfMeasureKindUseType(unitOfMeasureKindUseType);
            }
        }

        return totalEntities;
    }

    @Override
    protected Collection<UnitOfMeasureKindUse> getEntities() {
        Collection<UnitOfMeasureKindUse> entities = null;
        
        if(!hasExecutionErrors()) {
            if(unitOfMeasureKind != null) {
                entities = uomControl.getUnitOfMeasureKindUsesByUnitOfMeasureKind(unitOfMeasureKind);
            } else {
                entities = uomControl.getUnitOfMeasureKindUsesByUnitOfMeasureKindUseType(unitOfMeasureKindUseType);
            }
        }
        
        return entities;
    }
    
    @Override
    protected BaseResult getResult(Collection<UnitOfMeasureKindUse> entities) {
        var result = UomResultFactory.getGetUnitOfMeasureKindUsesResult();
        
        if(entities != null) {
            var userVisit = getUserVisit();

            if(unitOfMeasureKind != null) {
                result.setUnitOfMeasureKind(uomControl.getUnitOfMeasureKindTransfer(userVisit, unitOfMeasureKind));
            } else {
                result.setUnitOfMeasureKindUseType(uomControl.getUnitOfMeasureKindUseTypeTransfer(userVisit, unitOfMeasureKindUseType));
            }

            if(session.hasLimit(UnitOfMeasureKindUseFactory.class)) {
                result.setUnitOfMeasureKindUseCount(getTotalEntities());
            }

            result.setUnitOfMeasureKindUses(uomControl.getUnitOfMeasureKindUseTransfers(userVisit, entities));
        }
        
        return result;
    }
    
}

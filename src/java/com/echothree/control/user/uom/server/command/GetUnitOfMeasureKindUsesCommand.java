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

import com.echothree.control.user.uom.common.form.GetUnitOfMeasureKindUsesForm;
import com.echothree.control.user.uom.common.result.UomResultFactory;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureKindLogic;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureKindUseLogic;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUse;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUseType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetUnitOfMeasureKindUsesCommand
        extends BaseMultipleEntitiesCommand<UnitOfMeasureKindUse, GetUnitOfMeasureKindUsesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("UnitOfMeasureKindUseTypeName", FieldType.ENTITY_NAME, false, null, null)
        ));
    }
    
    /** Creates a new instance of GetUnitOfMeasureKindUsesCommand */
    public GetUnitOfMeasureKindUsesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    UnitOfMeasureKind unitOfMeasureKind;
    UnitOfMeasureKindUseType unitOfMeasureKindUseType;
    
    @Override
    protected Collection<UnitOfMeasureKindUse> getEntities() {
        Collection<UnitOfMeasureKindUse> entities = null;
        var unitOfMeasureKindName = form.getUnitOfMeasureKindName();
        var unitOfMeasureKindUseTypeName = form.getUnitOfMeasureKindUseTypeName();
        var parameterCount = (unitOfMeasureKindName == null ? 0 : 1) + (unitOfMeasureKindUseTypeName == null ? 0 : 1);
        
        if(parameterCount == 1) {
            if(unitOfMeasureKindName != null) {
                unitOfMeasureKind = UnitOfMeasureKindLogic.getInstance().getUnitOfMeasureKindByName(this, unitOfMeasureKindName);
                
                if(!hasExecutionErrors()) {
                    var uomControl = Session.getModelController(UomControl.class);

                    entities = uomControl.getUnitOfMeasureKindUsesByUnitOfMeasureKind(unitOfMeasureKind);
                }
            } else {
                unitOfMeasureKindUseType = UnitOfMeasureKindUseLogic.getInstance().getUnitOfMeasureKindUseTypeByName(this, unitOfMeasureKindUseTypeName);
                
                if(!hasExecutionErrors()) {
                    var uomControl = Session.getModelController(UomControl.class);

                    entities = uomControl.getUnitOfMeasureKindUsesByUnitOfMeasureKindUseType(unitOfMeasureKindUseType);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return entities;
    }
    
    @Override
    protected BaseResult getResult(Collection<UnitOfMeasureKindUse> entities) {
        var result = UomResultFactory.getGetUnitOfMeasureKindUsesResult();
        
        if(entities != null) {
            var uomControl = Session.getModelController(UomControl.class);

            if(unitOfMeasureKind != null) {
                result.setUnitOfMeasureKind(uomControl.getUnitOfMeasureKindTransfer(getUserVisit(), unitOfMeasureKind));
            } else {
                result.setUnitOfMeasureKindUseType(uomControl.getUnitOfMeasureKindUseTypeTransfer(getUserVisit(), unitOfMeasureKindUseType));
            }

            result.setUnitOfMeasureKindUses(uomControl.getUnitOfMeasureKindUseTransfers(getUserVisit(), entities));
        }
        
        return result;
    }
        
}

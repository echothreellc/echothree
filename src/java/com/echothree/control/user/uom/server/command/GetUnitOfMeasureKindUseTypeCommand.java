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

import com.echothree.control.user.uom.common.form.GetUnitOfMeasureKindUseTypeForm;
import com.echothree.control.user.uom.common.result.UomResultFactory;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureKindUseLogic;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUseType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetUnitOfMeasureKindUseTypeCommand
        extends BaseSingleEntityCommand<UnitOfMeasureKindUseType, GetUnitOfMeasureKindUseTypeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("UnitOfMeasureKindUseTypeName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetUnitOfMeasureKindUseTypeCommand */
    public GetUnitOfMeasureKindUseTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected UnitOfMeasureKindUseType getEntity() {
        return UnitOfMeasureKindUseLogic.getInstance().getUnitOfMeasureKindUseTypeByName(this, form.getUnitOfMeasureKindUseTypeName());
    }
    
    @Override
    protected BaseResult getResult(UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        var uomControl = Session.getModelController(UomControl.class);
        var result = UomResultFactory.getGetUnitOfMeasureKindUseTypeResult();
        
        if(unitOfMeasureKindUseType != null) {
            result.setUnitOfMeasureKindUseType(uomControl.getUnitOfMeasureKindUseTypeTransfer(getUserVisit(), unitOfMeasureKindUseType));
        }
        
        return result;
    }
        
}

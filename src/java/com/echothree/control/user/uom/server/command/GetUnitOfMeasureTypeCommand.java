// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.uom.remote.form.GetUnitOfMeasureTypeForm;
import com.echothree.control.user.uom.remote.result.GetUnitOfMeasureTypeResult;
import com.echothree.control.user.uom.remote.result.UomResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetUnitOfMeasureTypeCommand
        extends BaseSingleEntityCommand<UnitOfMeasureType, GetUnitOfMeasureTypeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetUnitOfMeasureTypeCommand */
    public GetUnitOfMeasureTypeCommand(UserVisitPK userVisitPK, GetUnitOfMeasureTypeForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected UnitOfMeasureType getEntity() {
        UnitOfMeasureType unitOfMeasureType = UnitOfMeasureTypeLogic.getInstance().getUnitOfMeasureTypeByName(this,
                form.getUnitOfMeasureKindName(), form.getUnitOfMeasureTypeName());
        
        if(!hasExecutionErrors()) {
            sendEventUsingNames(unitOfMeasureType.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
        }
        
        return unitOfMeasureType;
    }
    
    @Override
    protected BaseResult getTransfer(UnitOfMeasureType unitOfMeasureType) {
        UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
        GetUnitOfMeasureTypeResult result = UomResultFactory.getGetUnitOfMeasureTypeResult();
        
        if(unitOfMeasureType != null) {
            result.setUnitOfMeasureType(uomControl.getUnitOfMeasureTypeTransfer(getUserVisit(), unitOfMeasureType));
        }
        
        return result;
    }
    
}

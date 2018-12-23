// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.uom.common.form.GetUnitOfMeasureKindForm;
import com.echothree.control.user.uom.common.result.GetUnitOfMeasureKindResult;
import com.echothree.control.user.uom.common.result.UomResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureKindLogic;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetUnitOfMeasureKindCommand
        extends BaseSingleEntityCommand<UnitOfMeasureKind, GetUnitOfMeasureKindForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetUnitOfMeasureKindCommand */
    public GetUnitOfMeasureKindCommand(UserVisitPK userVisitPK, GetUnitOfMeasureKindForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected UnitOfMeasureKind getEntity() {
        UnitOfMeasureKind unitOfMeasureKind = UnitOfMeasureKindLogic.getInstance().getUnitOfMeasureKindByName(this, form.getUnitOfMeasureKindName());
        
        if(!hasExecutionErrors()) {
            sendEventUsingNames(unitOfMeasureKind.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
        }
        
        return unitOfMeasureKind;
    }
    
    @Override
    protected BaseResult getTransfer(UnitOfMeasureKind unitOfMeasureKind) {
        UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
        GetUnitOfMeasureKindResult result = UomResultFactory.getGetUnitOfMeasureKindResult();
        
        if(unitOfMeasureKind != null) {
            result.setUnitOfMeasureKind(uomControl.getUnitOfMeasureKindTransfer(getUserVisit(), unitOfMeasureKind));
        }
        
        return result;
    }
    
}

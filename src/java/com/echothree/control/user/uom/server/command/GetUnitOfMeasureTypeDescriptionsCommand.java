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

import com.echothree.control.user.uom.common.form.GetUnitOfMeasureTypeDescriptionsForm;
import com.echothree.control.user.uom.common.result.GetUnitOfMeasureTypeDescriptionsResult;
import com.echothree.control.user.uom.common.result.UomResultFactory;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetUnitOfMeasureTypeDescriptionsCommand
        extends BaseSimpleCommand<GetUnitOfMeasureTypeDescriptionsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetUnitOfMeasureTypeDescriptionsCommand */
    public GetUnitOfMeasureTypeDescriptionsCommand(UserVisitPK userVisitPK, GetUnitOfMeasureTypeDescriptionsForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
        GetUnitOfMeasureTypeDescriptionsResult result = UomResultFactory.getGetUnitOfMeasureTypeDescriptionsResult();
        String unitOfMeasureKindName = form.getUnitOfMeasureKindName();
        UnitOfMeasureKind unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);
        
        if(unitOfMeasureKind != null) {
            String unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
            UnitOfMeasureType unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);
            
            result.setUnitOfMeasureKind(uomControl.getUnitOfMeasureKindTransfer(getUserVisit(), unitOfMeasureKind));
            
            if(unitOfMeasureType != null) {
                result.setUnitOfMeasureType(uomControl.getUnitOfMeasureTypeTransfer(getUserVisit(), unitOfMeasureType));
                result.setUnitOfMeasureTypeDescriptions(uomControl.getUnitOfMeasureTypeDescriptionTransfers(getUserVisit(), unitOfMeasureType));
            } else {
                addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
        }
        
        return result;
    }
    
}

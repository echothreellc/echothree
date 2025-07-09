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

import com.echothree.control.user.uom.common.form.SetDefaultUnitOfMeasureKindUseForm;
import com.echothree.model.control.uom.server.control.UomControl;
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

public class SetDefaultUnitOfMeasureKindUseCommand
        extends BaseSimpleCommand<SetDefaultUnitOfMeasureKindUseForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("UnitOfMeasureKindUseTypeName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of SetDefaultUnitOfMeasureKindUseCommand */
    public SetDefaultUnitOfMeasureKindUseCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var uomControl = Session.getModelController(UomControl.class);
        var unitOfMeasureKindUseTypeName = form.getUnitOfMeasureKindUseTypeName();
        var unitOfMeasureKindUseType = uomControl.getUnitOfMeasureKindUseTypeByName(unitOfMeasureKindUseTypeName);
        
        if(unitOfMeasureKindUseType != null) {
            var unitOfMeasureKindName = form.getUnitOfMeasureKindName();
            var unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);
            
            if(unitOfMeasureKind != null) {
                var unitOfMeasureKindUseValue = uomControl.getUnitOfMeasureKindUseValueForUpdate(unitOfMeasureKindUseType,
                        unitOfMeasureKind);
                
                if(unitOfMeasureKindUseValue != null) {
                    unitOfMeasureKindUseValue.setIsDefault(true);
                    uomControl.updateUnitOfMeasureKindUseFromValue(unitOfMeasureKindUseValue, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindUse.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindUseTypeName.name(), unitOfMeasureKindUseTypeName);
        }
        
        return null;
    }
    
}

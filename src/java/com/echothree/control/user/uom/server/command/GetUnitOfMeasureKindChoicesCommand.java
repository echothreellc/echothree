// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.uom.common.form.GetUnitOfMeasureKindChoicesForm;
import com.echothree.control.user.uom.common.result.GetUnitOfMeasureKindChoicesResult;
import com.echothree.control.user.uom.common.result.UomResultFactory;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUseType;
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

public class GetUnitOfMeasureKindChoicesCommand
        extends BaseSimpleCommand<GetUnitOfMeasureKindChoicesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("DefaultUnitOfMeasureKindChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("UnitOfMeasureKindUseTypeName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetUnitOfMeasureKindChoicesCommand */
    public GetUnitOfMeasureKindChoicesCommand(UserVisitPK userVisitPK, GetUnitOfMeasureKindChoicesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        GetUnitOfMeasureKindChoicesResult result = UomResultFactory.getGetUnitOfMeasureKindChoicesResult();
        var uomControl = (UomControl)Session.getModelController(UomControl.class);
        String unitOfMeasureKindUseTypeName = form.getUnitOfMeasureKindUseTypeName();
        UnitOfMeasureKindUseType unitOfMeasureKindUseType = unitOfMeasureKindUseTypeName == null? null: uomControl.getUnitOfMeasureKindUseTypeByName(unitOfMeasureKindUseTypeName);
        
        if(unitOfMeasureKindUseTypeName == null || unitOfMeasureKindUseType != null) {
            String defaultUnitOfMeasureKindChoice = form.getDefaultUnitOfMeasureKindChoice();
            boolean allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());
            
            if(unitOfMeasureKindUseType == null) {
                result.setUnitOfMeasureKindChoices(uomControl.getUnitOfMeasureKindChoices(defaultUnitOfMeasureKindChoice,
                        getPreferredLanguage(), allowNullChoice));
            } else {
                result.setUnitOfMeasureKindChoices(uomControl.getUnitOfMeasureKindChoicesByUnitOfMeasureKindUseType(defaultUnitOfMeasureKindChoice,
                        getPreferredLanguage(), allowNullChoice, unitOfMeasureKindUseType));
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindUseTypeName.name(), unitOfMeasureKindUseTypeName);
        }
        
        return result;
    }
    
}

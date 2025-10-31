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

import com.echothree.control.user.uom.common.form.CreateUnitOfMeasureKindUseTypeForm;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateUnitOfMeasureKindUseTypeCommand
        extends BaseSimpleCommand<CreateUnitOfMeasureKindUseTypeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("UnitOfMeasureKindUseTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("AllowMultiple", FieldType.BOOLEAN, true, null, null),
            new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
            new FieldDefinition("AllowFractionDigits", FieldType.BOOLEAN, true, null, null),
            new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
        ));
    }
    
    /** Creates a new instance of CreateUnitOfMeasureKindUseTypeCommand */
    public CreateUnitOfMeasureKindUseTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var uomControl = Session.getModelController(UomControl.class);
        var unitOfMeasureKindUseTypeName = form.getUnitOfMeasureKindUseTypeName();
        var unitOfMeasureKindUseType = uomControl.getUnitOfMeasureKindUseTypeByName(unitOfMeasureKindUseTypeName);
        
        if(unitOfMeasureKindUseType == null) {
            var allowMultiple = Boolean.valueOf(form.getAllowMultiple());
            var isDefault = Boolean.valueOf(form.getIsDefault());
            var allowFractionDigits = Boolean.valueOf(form.getAllowFractionDigits());
            var sortOrder = Integer.valueOf(form.getSortOrder());
            
            uomControl.createUnitOfMeasureKindUseType(unitOfMeasureKindUseTypeName, allowMultiple, allowFractionDigits, isDefault, sortOrder);
        } else {
            addExecutionError(ExecutionErrors.DuplicateUnitOfMeasureKindUseTypeName.name(), unitOfMeasureKindUseTypeName);
        }
        
        return null;
    }
    
}

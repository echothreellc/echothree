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

package com.echothree.control.user.selector.server.command;


import com.echothree.control.user.selector.common.form.CreateSelectorNodeTypeUseForm;
import com.echothree.model.control.selector.server.control.SelectorControl;
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
import javax.enterprise.context.Dependent;

@Dependent
public class CreateSelectorNodeTypeUseCommand
        extends BaseSimpleCommand<CreateSelectorNodeTypeUseForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("SelectorKindName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("SelectorNodeTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null)
        ));
    }
    
    /** Creates a new instance of CreateSelectorNodeTypeUseCommand */
    public CreateSelectorNodeTypeUseCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var selectorControl = Session.getModelController(SelectorControl.class);
        var selectorKindName = form.getSelectorKindName();
        var selectorKind = selectorControl.getSelectorKindByName(selectorKindName);
        
        if(selectorKind != null) {
            var selectorNodeTypeName = form.getSelectorNodeTypeName();
            var selectorNodeType = selectorControl.getSelectorNodeTypeByName(selectorNodeTypeName);
            
            if(selectorNodeType != null) {
                var selectorNodeTypeUse = selectorControl.getSelectorNodeTypeUse(selectorKind, selectorNodeType);
                
                if(selectorNodeTypeUse == null) {
                    var isDefault = Boolean.valueOf(form.getIsDefault());
                    
                    selectorControl.createSelectorNodeTypeUse(selectorKind, selectorNodeType, isDefault);
                } else {
                    addExecutionError(ExecutionErrors.DuplicateSelectorNodeTypeUse.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSelectorNodeTypeName.name(), selectorNodeTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), selectorKindName);
        }
        
        return null;
    }
    
}

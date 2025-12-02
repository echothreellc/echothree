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

package com.echothree.control.user.selector.server.command;

import com.echothree.control.user.selector.common.form.GetSelectorNodeChoicesForm;
import com.echothree.control.user.selector.common.result.SelectorResultFactory;
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
public class GetSelectorNodeChoicesCommand
        extends BaseSimpleCommand<GetSelectorNodeChoicesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("SelectorKindName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("SelectorTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("SelectorName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("DefaultSelectorNodeChoice", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetSelectorNodeChoicesCommand */
    public GetSelectorNodeChoicesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var selectorControl = Session.getModelController(SelectorControl.class);
        var result = SelectorResultFactory.getGetSelectorNodeChoicesResult();
        var selectorKindName = form.getSelectorKindName();
        var selectorKind = selectorControl.getSelectorKindByName(selectorKindName);
        
        if(selectorKind != null) {
            var selectorTypeName = form.getSelectorTypeName();
            var selectorType = selectorControl.getSelectorTypeByName(selectorKind, selectorTypeName);
            
            if(selectorType != null) {
                var selectorName = form.getSelectorName();
                var selector = selectorControl.getSelectorByName(selectorType, selectorName);
                
                if(selector != null) {
                    var defaultSelectorNodeChoice = form.getDefaultSelectorNodeChoice();
                    var language = getPreferredLanguage();
                    var allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());
                    
                    result.setSelectorNodeChoices(selectorControl.getSelectorNodeChoices(selector, defaultSelectorNodeChoice,
                            language, allowNullChoice));
                } else {
                    addExecutionError(ExecutionErrors.UnknownSelectorName.name(), selectorName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), selectorTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), selectorKindName);
        }
        
        return result;
    }
    
}

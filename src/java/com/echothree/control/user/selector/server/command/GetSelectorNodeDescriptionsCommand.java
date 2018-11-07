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

package com.echothree.control.user.selector.server.command;

import com.echothree.control.user.selector.common.form.GetSelectorNodeDescriptionsForm;
import com.echothree.control.user.selector.common.result.GetSelectorNodeDescriptionsResult;
import com.echothree.control.user.selector.common.result.SelectorResultFactory;
import com.echothree.model.control.selector.server.SelectorControl;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.selector.server.entity.SelectorNode;
import com.echothree.model.data.selector.server.entity.SelectorType;
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

public class GetSelectorNodeDescriptionsCommand
        extends BaseSimpleCommand<GetSelectorNodeDescriptionsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("SelectorKindName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("SelectorTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("SelectorName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("SelectorNodeName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetSelectorNodeDescriptionsCommand */
    public GetSelectorNodeDescriptionsCommand(UserVisitPK userVisitPK, GetSelectorNodeDescriptionsForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        SelectorControl selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
        GetSelectorNodeDescriptionsResult result = SelectorResultFactory.getGetSelectorNodeDescriptionsResult();
        String selectorKindName = form.getSelectorKindName();
        SelectorKind selectorKind = selectorControl.getSelectorKindByName(selectorKindName);
        
        if(selectorKind != null) {
            String selectorTypeName = form.getSelectorTypeName();
            SelectorType selectorType = selectorControl.getSelectorTypeByName(selectorKind, selectorTypeName);
            
            result.setSelectorKind(selectorControl.getSelectorKindTransfer(getUserVisit(), selectorKind));
            
            if(selectorType != null) {
                String selectorName = form.getSelectorName();
                Selector selector = selectorControl.getSelectorByName(selectorType, selectorName);
                
                result.setSelectorType(selectorControl.getSelectorTypeTransfer(getUserVisit(), selectorType));
                
                if(selector != null) {
                    String selectorNodeName = form.getSelectorNodeName();
                    SelectorNode selectorNode = selectorControl.getSelectorNodeByName(selector, selectorNodeName);
                    
                    result.setSelector(selectorControl.getSelectorTransfer(getUserVisit(), selector));
                    
                    if(selectorNode != null) {
                        result.setSelectorNode(selectorControl.getSelectorNodeTransfer(getUserVisit(), selectorNode));
                        result.setSelectorNodeDescriptions(selectorControl.getSelectorNodeDescriptionTransfers(getUserVisit(), selectorNode));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownSelectorNodeName.name(), selectorNodeName);
                    }
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

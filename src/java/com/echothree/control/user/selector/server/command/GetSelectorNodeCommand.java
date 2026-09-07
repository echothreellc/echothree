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

import com.echothree.control.user.selector.common.form.GetSelectorNodeForm;
import com.echothree.control.user.selector.common.result.SelectorResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.control.selector.server.logic.SelectorKindLogic;
import com.echothree.model.control.selector.server.logic.SelectorLogic;
import com.echothree.model.control.selector.server.logic.SelectorTypeLogic;
import com.echothree.model.data.selector.server.entity.SelectorNode;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetSelectorNodeCommand
        extends BaseSingleEntityCommand<SelectorNode, GetSelectorNodeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("SelectorKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SelectorTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SelectorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SelectorNodeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    SelectorControl selectorControl;

    @Inject
    SelectorKindLogic selectorKindLogic;

    @Inject
    SelectorLogic selectorLogic;

    @Inject
    SelectorTypeLogic selectorTypeLogic;

    /** Creates a new instance of GetSelectorNodeCommand */
    public GetSelectorNodeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected SelectorNode getEntity() {
        var selectorKindName = form.getSelectorKindName();
        var selectorKind = selectorKindLogic.getSelectorKindByName(this, selectorKindName);
        SelectorNode selectorNode = null;
        
        if(!hasExecutionErrors()) {
            var selectorTypeName = form.getSelectorTypeName();
            var selectorType = selectorTypeLogic.getSelectorTypeByName(this, selectorKind, selectorTypeName);
            
            if(!hasExecutionErrors()) {
                var selectorName = form.getSelectorName();
                var selector = selectorLogic.getSelectorByName(this, selectorType, selectorName);
                
                if(!hasExecutionErrors()) {
                    var selectorNodeName = form.getSelectorNodeName();
                    selectorNode = selectorControl.getSelectorNodeByName(selector, selectorNodeName);

                    if(selectorNode == null) {
                        addExecutionError(ExecutionErrors.UnknownSelectorNodeName.name(), selectorKindName, selectorTypeName,
                                selectorName, selectorNodeName);
                    } else {
                        sendEvent(selectorNode.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
                    }
                }
            }
        }

        return selectorNode;
    }

    @Override
    protected BaseResult getResult(SelectorNode selectorNode) {
        var result = SelectorResultFactory.getGetSelectorNodeResult();
        var userVisit = getUserVisit();

        if(selectorNode != null) {
            result.setSelectorNode(selectorControl.getSelectorNodeTransfer(userVisit, selectorNode));
        }

        return result;
    }
    
}

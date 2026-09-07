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

import com.echothree.control.user.selector.common.form.GetSelectorNodeTypeForm;
import com.echothree.control.user.selector.common.result.SelectorResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.control.selector.server.logic.SelectorNodeTypeLogic;
import com.echothree.model.data.selector.server.entity.SelectorNodeType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetSelectorNodeTypeCommand
        extends BaseSingleEntityCommand<SelectorNodeType, GetSelectorNodeTypeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("SelectorNodeType", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    @Inject
    SelectorControl selectorControl;

    @Inject
    SelectorNodeTypeLogic selectorNodeTypeLogic;

    /** Creates a new instance of GetSelectorNodeTypeCommand */
    public GetSelectorNodeTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected SelectorNodeType getEntity() {
        var selectorNodeTypeName = form.getSelectorNodeTypeName();
        var selectorNodeType = selectorNodeTypeLogic.getSelectorNodeTypeByName(this, selectorNodeTypeName);

        if(!hasExecutionErrors()) {
            sendEvent(selectorNodeType.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return selectorNodeType;
    }

    @Override
    protected BaseResult getResult(SelectorNodeType selectorNodeType) {
        var result = SelectorResultFactory.getGetSelectorNodeTypeResult();

        if(selectorNodeType != null) {
            result.setSelectorNodeType(selectorControl.getSelectorNodeTypeTransfer(getUserVisit(), selectorNodeType));
        }

        return result;
    }
    
}

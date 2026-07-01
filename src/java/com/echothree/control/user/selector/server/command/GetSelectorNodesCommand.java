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

import com.echothree.control.user.selector.common.form.GetSelectorNodesForm;
import com.echothree.control.user.selector.common.result.SelectorResultFactory;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.control.selector.server.logic.SelectorLogic;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.entity.SelectorNode;
import com.echothree.model.data.selector.server.factory.SelectorNodeFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetSelectorNodesCommand
        extends BasePaginatedMultipleEntitiesCommand<SelectorNode, GetSelectorNodesForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("SelectorKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SelectorTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SelectorName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    SelectorControl selectorControl;

    @Inject
    SelectorLogic selectorLogic;

    /** Creates a new instance of GetSelectorNodesCommand */
    public GetSelectorNodesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    private Selector selector;

    @Override
    protected void handleForm() {
        selector = selectorLogic.getSelectorByName(this, form.getSelectorKindName(), form.getSelectorTypeName(), form.getSelectorName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : selectorControl.countSelectorNodesBySelector(selector);
    }

    @Override
    protected Collection<SelectorNode> getEntities() {
        return hasExecutionErrors() ? null : selectorControl.getSelectorNodesBySelector(selector);
    }

    @Override
    protected BaseResult getResult(Collection<SelectorNode> entities) {
        var result = SelectorResultFactory.getGetSelectorNodesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setSelector(selectorControl.getSelectorTransfer(userVisit, selector));

            if(session.hasLimit(SelectorNodeFactory.class)) {
                result.setSelectorNodeCount(getTotalEntities());
            }

            result.setSelectorNodes(selectorControl.getSelectorNodeTransfersBySelector(userVisit, selector));
        }

        return result;
    }

}

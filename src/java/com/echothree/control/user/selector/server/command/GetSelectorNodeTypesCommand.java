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

import com.echothree.control.user.selector.common.form.GetSelectorNodeTypesForm;
import com.echothree.control.user.selector.common.result.SelectorResultFactory;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.control.selector.server.logic.SelectorKindLogic;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.selector.server.entity.SelectorNodeType;
import com.echothree.model.data.selector.server.factory.SelectorNodeTypeFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetSelectorNodeTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<SelectorNodeType, GetSelectorNodeTypesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("SelectorKindName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    @Inject
    SelectorControl selectorControl;

    @Inject
    SelectorKindLogic selectorKindLogic;

    /** Creates a new instance of GetSelectorNodeTypesCommand */
    public GetSelectorNodeTypesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    SelectorKind selectorKind;

    @Override
    protected void handleForm() {
        var selectorKindName = form.getSelectorKindName();

        if(selectorKindName != null) {
            selectorKind = selectorKindLogic.getSelectorKindByName(this, selectorKindName);
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null :
                selectorKind == null ? selectorControl.countSelectorNodeTypes() :
                        selectorControl.countSelectorNodeTypesBySelectorKind(selectorKind);
    }

    @Override
    protected Collection<SelectorNodeType> getEntities() {
        return hasExecutionErrors() ? null :
                selectorKind == null ? selectorControl.getSelectorNodeTypes() :
                        selectorControl.getSelectorNodeTypesBySelectorKind(selectorKind);
    }

    @Override
    protected BaseResult getResult(Collection<SelectorNodeType> entities) {
        var result = SelectorResultFactory.getGetSelectorNodeTypesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(selectorKind != null) {
                result.setSelectorKind(selectorControl.getSelectorKindTransfer(userVisit, selectorKind));
            }

            if(session.hasLimit(SelectorNodeTypeFactory.class)) {
                result.setSelectorNodeTypeCount(getTotalEntities());
            }

            result.setSelectorNodeTypes(selectorControl.getSelectorNodeTypeTransfers(userVisit, entities));
        }

        return result;
    }
    
}

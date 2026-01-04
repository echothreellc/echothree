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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.GetItemTypesForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemType;
import com.echothree.model.data.item.server.factory.ItemTypeFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetItemTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<ItemType, GetItemTypesForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of();
    }

    /** Creates a new instance of GetItemTypesCommand */
    public GetItemTypesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        // No form fields.
    }

    @Override
    protected Long getTotalEntities() {
        var itemControl = Session.getModelController(ItemControl.class);

        return itemControl.countItemTypes();
    }

    @Override
    protected Collection<ItemType> getEntities() {
        var itemControl = Session.getModelController(ItemControl.class);

        return itemControl.getItemTypes();
    }

    @Override
    protected BaseResult getResult(Collection<ItemType> entities) {
        var result = ItemResultFactory.getGetItemTypesResult();

        if(entities != null) {
            var itemControl = Session.getModelController(ItemControl.class);

            if(session.hasLimit(ItemTypeFactory.class)) {
                result.setItemTypeCount(getTotalEntities());
            }

            result.setItemTypes(itemControl.getItemTypeTransfers(getUserVisit(), entities));
        }

        return result;
    }

}

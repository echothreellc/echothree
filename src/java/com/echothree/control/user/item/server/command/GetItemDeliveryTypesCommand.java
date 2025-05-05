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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.GetItemDeliveryTypesForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemDeliveryType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;

public class GetItemDeliveryTypesCommand
        extends BaseMultipleEntitiesCommand<ItemDeliveryType, GetItemDeliveryTypesForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of(
        );
    }

    /** Creates a new instance of GetItemDeliveryTypesCommand */
    public GetItemDeliveryTypesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected Collection<ItemDeliveryType> getEntities() {
        var itemControl = Session.getModelController(ItemControl.class);

        return itemControl.getItemDeliveryTypes();
    }

    @Override
    protected BaseResult getResult(Collection<ItemDeliveryType> entities) {
        var result = ItemResultFactory.getGetItemDeliveryTypesResult();
        var itemControl = Session.getModelController(ItemControl.class);
        var userVisit = getUserVisit();

        result.setItemDeliveryTypes(itemControl.getItemDeliveryTypeTransfers(userVisit, entities));

        return result;
    }

}

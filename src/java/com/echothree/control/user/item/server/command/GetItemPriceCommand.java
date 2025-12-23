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

import com.echothree.control.user.item.common.form.GetItemPriceForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.accounting.server.logic.CurrencyLogic;
import com.echothree.model.control.inventory.server.logic.InventoryConditionLogic;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.logic.SecurityRoleLogic;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.data.item.server.entity.ItemPrice;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetItemPriceCommand
        extends BaseSingleEntityCommand<ItemPrice, GetItemPriceForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IncludeHistory", FieldType.BOOLEAN, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetItemPriceCommand */
    public GetItemPriceCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected boolean checkOptionalSecurityRoles() {
        // This occurs before validation, and parseBoolean is more lax than our validation of what's permitted for a FieldType.BOOLEAN.
        return Boolean.parseBoolean(form.getIncludeHistory()) ? SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(this, getParty(),
                SecurityRoleGroups.ItemPrice.name(), SecurityRoles.History.name()) : true;
    }

    @Override
    protected ItemPrice getEntity() {
        var itemControl = Session.getModelController(ItemControl.class);
        var item = ItemLogic.getInstance().getItemByName(this, form.getItemName());
        var inventoryCondition = InventoryConditionLogic.getInstance().getInventoryConditionByName(this, form.getInventoryConditionName());
        var currency = CurrencyLogic.getInstance().getCurrencyByName(this, form.getCurrencyIsoName());
        ItemPrice itemPrice = null;

        if(!hasExecutionErrors()) {
            var unitOfMeasureType = UnitOfMeasureTypeLogic.getInstance().getUnitOfMeasureTypeByName(this, item.getLastDetail().getUnitOfMeasureKind(), form.getUnitOfMeasureTypeName());

            if(!hasExecutionErrors()) {
                itemPrice = itemControl.getItemPrice(item, inventoryCondition, unitOfMeasureType, currency);

                if(itemPrice == null) {
                    addExecutionError(ExecutionErrors.UnknownItemPrice.name(), item.getLastDetail().getItemName(),
                            inventoryCondition.getLastDetail().getInventoryConditionName(),
                            unitOfMeasureType.getLastDetail().getUnitOfMeasureTypeName(), currency.getCurrencyIsoName());
                }
            }

        }

        return itemPrice;
    }

    @Override
    protected BaseResult getResult(ItemPrice entity) {
        var result = ItemResultFactory.getGetItemPriceResult();

        if(entity != null) {
            var itemControl = Session.getModelController(ItemControl.class);
            var userVisit = getUserVisit();

            result.setItemPrice(itemControl.getItemPriceTransfer(userVisit, entity));

            if(Boolean.parseBoolean(form.getIncludeHistory())) {
                result.setHistory(itemControl.getItemPriceHistory(userVisit, entity));
            }
        }

        return result;
    }

}

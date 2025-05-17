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

import com.echothree.control.user.item.common.edit.ItemEditFactory;
import com.echothree.control.user.item.common.edit.ItemShippingTimeEdit;
import com.echothree.control.user.item.common.form.EditItemShippingTimeForm;
import com.echothree.control.user.item.common.result.EditItemShippingTimeResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.ItemShippingTimeSpec;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemShippingTime;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.DateUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditItemShippingTimeCommand
        extends BaseAbstractEditCommand<ItemShippingTimeSpec, ItemShippingTimeEdit, EditItemShippingTimeResult, ItemShippingTime, Item> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ShippingStartTime", FieldType.DATE_TIME, true, null, null),
                new FieldDefinition("ShippingEndTime", FieldType.DATE_TIME, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditItemShippingTimeCommand */
    public EditItemShippingTimeCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditItemShippingTimeResult getResult() {
        return ItemResultFactory.getEditItemShippingTimeResult();
    }

    @Override
    public ItemShippingTimeEdit getEdit() {
        return ItemEditFactory.getItemShippingTimeEdit();
    }

    @Override
    public ItemShippingTime getEntity(EditItemShippingTimeResult result) {
        var itemControl = Session.getModelController(ItemControl.class);
        ItemShippingTime itemShippingTime = null;
        var itemName = spec.getItemName();
        var item = itemControl.getItemByName(itemName);

        if(item != null) {
            var customerControl = Session.getModelController(CustomerControl.class);
            var customerTypeName = spec.getCustomerTypeName();
            var customerType = customerControl.getCustomerTypeByName(customerTypeName);

            if(customerType != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    itemShippingTime = itemControl.getItemShippingTime(item, customerType);
                } else { // EditMode.UPDATE
                    itemShippingTime = itemControl.getItemShippingTimeForUpdate(item, customerType);
                }

                if(itemShippingTime == null) {
                    addExecutionError(ExecutionErrors.UnknownItemShippingTime.name(), itemName, customerTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCustomerTypeName.name(), customerTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
        }

        return itemShippingTime;
    }

    @Override
    public Item getLockEntity(ItemShippingTime itemShippingTime) {
        return itemShippingTime.getItem();
    }

    @Override
    public void fillInResult(EditItemShippingTimeResult result, ItemShippingTime itemShippingTime) {
        var itemControl = Session.getModelController(ItemControl.class);

        result.setItemShippingTime(itemControl.getItemShippingTimeTransfer(getUserVisit(), itemShippingTime));
    }

    @Override
    public void doLock(ItemShippingTimeEdit edit, ItemShippingTime itemShippingTime) {
        var dateUtils = DateUtils.getInstance();
        var shippingEndTime = itemShippingTime.getShippingEndTime();
        var preferredDateTimeFormat = getPreferredDateTimeFormat();
        var userVisit = getUserVisit();

        edit.setShippingStartTime(dateUtils.formatTypicalDateTime(userVisit, preferredDateTimeFormat, itemShippingTime.getShippingStartTime()));
        edit.setShippingEndTime(shippingEndTime == null? null: dateUtils.formatTypicalDateTime(userVisit, preferredDateTimeFormat, shippingEndTime));
    }

    @Override
    public void doUpdate(ItemShippingTime itemShippingTime) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemShippingTimeValue = itemControl.getItemShippingTimeValue(itemShippingTime);
        var strShippingEndTime = edit.getShippingEndTime();
        var shippingEndTime = strShippingEndTime == null? null: Long.valueOf(strShippingEndTime);

        itemShippingTimeValue.setShippingStartTime(Long.valueOf(edit.getShippingStartTime()));
        itemShippingTimeValue.setShippingEndTime(shippingEndTime);

        itemControl.updateItemShippingTimeFromValue(itemShippingTimeValue, getPartyPK());
    }
    
}

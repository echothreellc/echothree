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

import com.echothree.control.user.item.common.form.GetItemShippingTimeForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.item.server.control.ItemControl;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetItemShippingTimeCommand
        extends BaseSimpleCommand<GetItemShippingTimeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("CustomerTypeName", FieldType.PERCENT, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetItemShippingTimeCommand */
    public GetItemShippingTimeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var itemControl = Session.getModelController(ItemControl.class);
        var result = ItemResultFactory.getGetItemShippingTimeResult();
        var itemName = form.getItemName();
        var item = itemControl.getItemByName(itemName);
        
        if(item != null) {
            var customerControl = Session.getModelController(CustomerControl.class);
            var customerTypeName = form.getCustomerTypeName();
            var customerType = customerControl.getCustomerTypeByName(customerTypeName);

            if(customerType != null) {
                var itemShippingTime = itemControl.getItemShippingTime(item, customerType);

                if(itemShippingTime != null) {
                    result.setItemShippingTime(itemControl.getItemShippingTimeTransfer(getUserVisit(), itemShippingTime));
                } else {
                    addExecutionError(ExecutionErrors.UnknownItemShippingTime.name(), itemName, customerTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCustomerTypeName.name(), customerTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
        }
        
        return result;
    }
    
}

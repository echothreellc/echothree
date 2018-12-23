// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.control.user.order.server;

import com.echothree.control.user.order.common.OrderRemote;
import com.echothree.control.user.order.common.form.*;
import com.echothree.control.user.order.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class OrderBean
        extends OrderFormsImpl
        implements OrderRemote, OrderLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "OrderBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Order Role Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createOrderRoleType(UserVisitPK userVisitPK, CreateOrderRoleTypeForm form) {
        return new CreateOrderRoleTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Order Role Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createOrderRoleTypeDescription(UserVisitPK userVisitPK, CreateOrderRoleTypeDescriptionForm form) {
        return new CreateOrderRoleTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Order Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderType(UserVisitPK userVisitPK, CreateOrderTypeForm form) {
        return new CreateOrderTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderTypeChoices(UserVisitPK userVisitPK, GetOrderTypeChoicesForm form) {
        return new GetOrderTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderType(UserVisitPK userVisitPK, GetOrderTypeForm form) {
        return new GetOrderTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderTypes(UserVisitPK userVisitPK, GetOrderTypesForm form) {
        return new GetOrderTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultOrderType(UserVisitPK userVisitPK, SetDefaultOrderTypeForm form) {
        return new SetDefaultOrderTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editOrderType(UserVisitPK userVisitPK, EditOrderTypeForm form) {
        return new EditOrderTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteOrderType(UserVisitPK userVisitPK, DeleteOrderTypeForm form) {
        return new DeleteOrderTypeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Order Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderTypeDescription(UserVisitPK userVisitPK, CreateOrderTypeDescriptionForm form) {
        return new CreateOrderTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderTypeDescription(UserVisitPK userVisitPK, GetOrderTypeDescriptionForm form) {
        return new GetOrderTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderTypeDescriptions(UserVisitPK userVisitPK, GetOrderTypeDescriptionsForm form) {
        return new GetOrderTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editOrderTypeDescription(UserVisitPK userVisitPK, EditOrderTypeDescriptionForm form) {
        return new EditOrderTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteOrderTypeDescription(UserVisitPK userVisitPK, DeleteOrderTypeDescriptionForm form) {
        return new DeleteOrderTypeDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Order Time Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderTimeType(UserVisitPK userVisitPK, CreateOrderTimeTypeForm form) {
        return new CreateOrderTimeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderTimeTypeChoices(UserVisitPK userVisitPK, GetOrderTimeTypeChoicesForm form) {
        return new GetOrderTimeTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderTimeType(UserVisitPK userVisitPK, GetOrderTimeTypeForm form) {
        return new GetOrderTimeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderTimeTypes(UserVisitPK userVisitPK, GetOrderTimeTypesForm form) {
        return new GetOrderTimeTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultOrderTimeType(UserVisitPK userVisitPK, SetDefaultOrderTimeTypeForm form) {
        return new SetDefaultOrderTimeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editOrderTimeType(UserVisitPK userVisitPK, EditOrderTimeTypeForm form) {
        return new EditOrderTimeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteOrderTimeType(UserVisitPK userVisitPK, DeleteOrderTimeTypeForm form) {
        return new DeleteOrderTimeTypeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Order Time Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderTimeTypeDescription(UserVisitPK userVisitPK, CreateOrderTimeTypeDescriptionForm form) {
        return new CreateOrderTimeTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderTimeTypeDescription(UserVisitPK userVisitPK, GetOrderTimeTypeDescriptionForm form) {
        return new GetOrderTimeTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderTimeTypeDescriptions(UserVisitPK userVisitPK, GetOrderTimeTypeDescriptionsForm form) {
        return new GetOrderTimeTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editOrderTimeTypeDescription(UserVisitPK userVisitPK, EditOrderTimeTypeDescriptionForm form) {
        return new EditOrderTimeTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteOrderTimeTypeDescription(UserVisitPK userVisitPK, DeleteOrderTimeTypeDescriptionForm form) {
        return new DeleteOrderTimeTypeDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Order Alias Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderAliasType(UserVisitPK userVisitPK, CreateOrderAliasTypeForm form) {
        return new CreateOrderAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderAliasTypeChoices(UserVisitPK userVisitPK, GetOrderAliasTypeChoicesForm form) {
        return new GetOrderAliasTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderAliasType(UserVisitPK userVisitPK, GetOrderAliasTypeForm form) {
        return new GetOrderAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderAliasTypes(UserVisitPK userVisitPK, GetOrderAliasTypesForm form) {
        return new GetOrderAliasTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultOrderAliasType(UserVisitPK userVisitPK, SetDefaultOrderAliasTypeForm form) {
        return new SetDefaultOrderAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editOrderAliasType(UserVisitPK userVisitPK, EditOrderAliasTypeForm form) {
        return new EditOrderAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteOrderAliasType(UserVisitPK userVisitPK, DeleteOrderAliasTypeForm form) {
        return new DeleteOrderAliasTypeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Order Alias Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderAliasTypeDescription(UserVisitPK userVisitPK, CreateOrderAliasTypeDescriptionForm form) {
        return new CreateOrderAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderAliasTypeDescription(UserVisitPK userVisitPK, GetOrderAliasTypeDescriptionForm form) {
        return new GetOrderAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderAliasTypeDescriptions(UserVisitPK userVisitPK, GetOrderAliasTypeDescriptionsForm form) {
        return new GetOrderAliasTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editOrderAliasTypeDescription(UserVisitPK userVisitPK, EditOrderAliasTypeDescriptionForm form) {
        return new EditOrderAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteOrderAliasTypeDescription(UserVisitPK userVisitPK, DeleteOrderAliasTypeDescriptionForm form) {
        return new DeleteOrderAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Order Aliases
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderAlias(UserVisitPK userVisitPK, CreateOrderAliasForm form) {
        return new CreateOrderAliasCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderAlias(UserVisitPK userVisitPK, GetOrderAliasForm form) {
        return new GetOrderAliasCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderAliases(UserVisitPK userVisitPK, GetOrderAliasesForm form) {
        return new GetOrderAliasesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editOrderAlias(UserVisitPK userVisitPK, EditOrderAliasForm form) {
        return new EditOrderAliasCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteOrderAlias(UserVisitPK userVisitPK, DeleteOrderAliasForm form) {
        return new DeleteOrderAliasCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Order Priorities
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderPriority(UserVisitPK userVisitPK, CreateOrderPriorityForm form) {
        return new CreateOrderPriorityCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderPriorityChoices(UserVisitPK userVisitPK, GetOrderPriorityChoicesForm form) {
        return new GetOrderPriorityChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderPriority(UserVisitPK userVisitPK, GetOrderPriorityForm form) {
        return new GetOrderPriorityCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderPriorities(UserVisitPK userVisitPK, GetOrderPrioritiesForm form) {
        return new GetOrderPrioritiesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultOrderPriority(UserVisitPK userVisitPK, SetDefaultOrderPriorityForm form) {
        return new SetDefaultOrderPriorityCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editOrderPriority(UserVisitPK userVisitPK, EditOrderPriorityForm form) {
        return new EditOrderPriorityCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteOrderPriority(UserVisitPK userVisitPK, DeleteOrderPriorityForm form) {
        return new DeleteOrderPriorityCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Order Priority Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderPriorityDescription(UserVisitPK userVisitPK, CreateOrderPriorityDescriptionForm form) {
        return new CreateOrderPriorityDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderPriorityDescription(UserVisitPK userVisitPK, GetOrderPriorityDescriptionForm form) {
        return new GetOrderPriorityDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderPriorityDescriptions(UserVisitPK userVisitPK, GetOrderPriorityDescriptionsForm form) {
        return new GetOrderPriorityDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editOrderPriorityDescription(UserVisitPK userVisitPK, EditOrderPriorityDescriptionForm form) {
        return new EditOrderPriorityDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteOrderPriorityDescription(UserVisitPK userVisitPK, DeleteOrderPriorityDescriptionForm form) {
        return new DeleteOrderPriorityDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Order Adjustment Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderAdjustmentType(UserVisitPK userVisitPK, CreateOrderAdjustmentTypeForm form) {
        return new CreateOrderAdjustmentTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderAdjustmentTypeChoices(UserVisitPK userVisitPK, GetOrderAdjustmentTypeChoicesForm form) {
        return new GetOrderAdjustmentTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderAdjustmentType(UserVisitPK userVisitPK, GetOrderAdjustmentTypeForm form) {
        return new GetOrderAdjustmentTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderAdjustmentTypes(UserVisitPK userVisitPK, GetOrderAdjustmentTypesForm form) {
        return new GetOrderAdjustmentTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultOrderAdjustmentType(UserVisitPK userVisitPK, SetDefaultOrderAdjustmentTypeForm form) {
        return new SetDefaultOrderAdjustmentTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editOrderAdjustmentType(UserVisitPK userVisitPK, EditOrderAdjustmentTypeForm form) {
        return new EditOrderAdjustmentTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteOrderAdjustmentType(UserVisitPK userVisitPK, DeleteOrderAdjustmentTypeForm form) {
        return new DeleteOrderAdjustmentTypeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Order Adjustment Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderAdjustmentTypeDescription(UserVisitPK userVisitPK, CreateOrderAdjustmentTypeDescriptionForm form) {
        return new CreateOrderAdjustmentTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderAdjustmentTypeDescription(UserVisitPK userVisitPK, GetOrderAdjustmentTypeDescriptionForm form) {
        return new GetOrderAdjustmentTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderAdjustmentTypeDescriptions(UserVisitPK userVisitPK, GetOrderAdjustmentTypeDescriptionsForm form) {
        return new GetOrderAdjustmentTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editOrderAdjustmentTypeDescription(UserVisitPK userVisitPK, EditOrderAdjustmentTypeDescriptionForm form) {
        return new EditOrderAdjustmentTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteOrderAdjustmentTypeDescription(UserVisitPK userVisitPK, DeleteOrderAdjustmentTypeDescriptionForm form) {
        return new DeleteOrderAdjustmentTypeDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Order Line Adjustment Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderLineAdjustmentType(UserVisitPK userVisitPK, CreateOrderLineAdjustmentTypeForm form) {
        return new CreateOrderLineAdjustmentTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderLineAdjustmentTypeChoices(UserVisitPK userVisitPK, GetOrderLineAdjustmentTypeChoicesForm form) {
        return new GetOrderLineAdjustmentTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderLineAdjustmentType(UserVisitPK userVisitPK, GetOrderLineAdjustmentTypeForm form) {
        return new GetOrderLineAdjustmentTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderLineAdjustmentTypes(UserVisitPK userVisitPK, GetOrderLineAdjustmentTypesForm form) {
        return new GetOrderLineAdjustmentTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultOrderLineAdjustmentType(UserVisitPK userVisitPK, SetDefaultOrderLineAdjustmentTypeForm form) {
        return new SetDefaultOrderLineAdjustmentTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editOrderLineAdjustmentType(UserVisitPK userVisitPK, EditOrderLineAdjustmentTypeForm form) {
        return new EditOrderLineAdjustmentTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteOrderLineAdjustmentType(UserVisitPK userVisitPK, DeleteOrderLineAdjustmentTypeForm form) {
        return new DeleteOrderLineAdjustmentTypeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Order Line Adjustment Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderLineAdjustmentTypeDescription(UserVisitPK userVisitPK, CreateOrderLineAdjustmentTypeDescriptionForm form) {
        return new CreateOrderLineAdjustmentTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderLineAdjustmentTypeDescription(UserVisitPK userVisitPK, GetOrderLineAdjustmentTypeDescriptionForm form) {
        return new GetOrderLineAdjustmentTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOrderLineAdjustmentTypeDescriptions(UserVisitPK userVisitPK, GetOrderLineAdjustmentTypeDescriptionsForm form) {
        return new GetOrderLineAdjustmentTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editOrderLineAdjustmentTypeDescription(UserVisitPK userVisitPK, EditOrderLineAdjustmentTypeDescriptionForm form) {
        return new EditOrderLineAdjustmentTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteOrderLineAdjustmentTypeDescription(UserVisitPK userVisitPK, DeleteOrderLineAdjustmentTypeDescriptionForm form) {
        return new DeleteOrderLineAdjustmentTypeDescriptionCommand(userVisitPK, form).run();
    }
    
}

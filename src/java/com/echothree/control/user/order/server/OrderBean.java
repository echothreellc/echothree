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
        return new CreateOrderRoleTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Order Role Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createOrderRoleTypeDescription(UserVisitPK userVisitPK, CreateOrderRoleTypeDescriptionForm form) {
        return new CreateOrderRoleTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Order Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderType(UserVisitPK userVisitPK, CreateOrderTypeForm form) {
        return new CreateOrderTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderTypeChoices(UserVisitPK userVisitPK, GetOrderTypeChoicesForm form) {
        return new GetOrderTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderType(UserVisitPK userVisitPK, GetOrderTypeForm form) {
        return new GetOrderTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderTypes(UserVisitPK userVisitPK, GetOrderTypesForm form) {
        return new GetOrderTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultOrderType(UserVisitPK userVisitPK, SetDefaultOrderTypeForm form) {
        return new SetDefaultOrderTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOrderType(UserVisitPK userVisitPK, EditOrderTypeForm form) {
        return new EditOrderTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteOrderType(UserVisitPK userVisitPK, DeleteOrderTypeForm form) {
        return new DeleteOrderTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Order Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderTypeDescription(UserVisitPK userVisitPK, CreateOrderTypeDescriptionForm form) {
        return new CreateOrderTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderTypeDescription(UserVisitPK userVisitPK, GetOrderTypeDescriptionForm form) {
        return new GetOrderTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderTypeDescriptions(UserVisitPK userVisitPK, GetOrderTypeDescriptionsForm form) {
        return new GetOrderTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOrderTypeDescription(UserVisitPK userVisitPK, EditOrderTypeDescriptionForm form) {
        return new EditOrderTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteOrderTypeDescription(UserVisitPK userVisitPK, DeleteOrderTypeDescriptionForm form) {
        return new DeleteOrderTypeDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Order Time Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderTimeType(UserVisitPK userVisitPK, CreateOrderTimeTypeForm form) {
        return new CreateOrderTimeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderTimeTypeChoices(UserVisitPK userVisitPK, GetOrderTimeTypeChoicesForm form) {
        return new GetOrderTimeTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderTimeType(UserVisitPK userVisitPK, GetOrderTimeTypeForm form) {
        return new GetOrderTimeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderTimeTypes(UserVisitPK userVisitPK, GetOrderTimeTypesForm form) {
        return new GetOrderTimeTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultOrderTimeType(UserVisitPK userVisitPK, SetDefaultOrderTimeTypeForm form) {
        return new SetDefaultOrderTimeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOrderTimeType(UserVisitPK userVisitPK, EditOrderTimeTypeForm form) {
        return new EditOrderTimeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteOrderTimeType(UserVisitPK userVisitPK, DeleteOrderTimeTypeForm form) {
        return new DeleteOrderTimeTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Order Time Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderTimeTypeDescription(UserVisitPK userVisitPK, CreateOrderTimeTypeDescriptionForm form) {
        return new CreateOrderTimeTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderTimeTypeDescription(UserVisitPK userVisitPK, GetOrderTimeTypeDescriptionForm form) {
        return new GetOrderTimeTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderTimeTypeDescriptions(UserVisitPK userVisitPK, GetOrderTimeTypeDescriptionsForm form) {
        return new GetOrderTimeTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOrderTimeTypeDescription(UserVisitPK userVisitPK, EditOrderTimeTypeDescriptionForm form) {
        return new EditOrderTimeTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteOrderTimeTypeDescription(UserVisitPK userVisitPK, DeleteOrderTimeTypeDescriptionForm form) {
        return new DeleteOrderTimeTypeDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Order Alias Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderAliasType(UserVisitPK userVisitPK, CreateOrderAliasTypeForm form) {
        return new CreateOrderAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderAliasTypeChoices(UserVisitPK userVisitPK, GetOrderAliasTypeChoicesForm form) {
        return new GetOrderAliasTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderAliasType(UserVisitPK userVisitPK, GetOrderAliasTypeForm form) {
        return new GetOrderAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderAliasTypes(UserVisitPK userVisitPK, GetOrderAliasTypesForm form) {
        return new GetOrderAliasTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultOrderAliasType(UserVisitPK userVisitPK, SetDefaultOrderAliasTypeForm form) {
        return new SetDefaultOrderAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOrderAliasType(UserVisitPK userVisitPK, EditOrderAliasTypeForm form) {
        return new EditOrderAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteOrderAliasType(UserVisitPK userVisitPK, DeleteOrderAliasTypeForm form) {
        return new DeleteOrderAliasTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Order Alias Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderAliasTypeDescription(UserVisitPK userVisitPK, CreateOrderAliasTypeDescriptionForm form) {
        return new CreateOrderAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderAliasTypeDescription(UserVisitPK userVisitPK, GetOrderAliasTypeDescriptionForm form) {
        return new GetOrderAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderAliasTypeDescriptions(UserVisitPK userVisitPK, GetOrderAliasTypeDescriptionsForm form) {
        return new GetOrderAliasTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOrderAliasTypeDescription(UserVisitPK userVisitPK, EditOrderAliasTypeDescriptionForm form) {
        return new EditOrderAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteOrderAliasTypeDescription(UserVisitPK userVisitPK, DeleteOrderAliasTypeDescriptionForm form) {
        return new DeleteOrderAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Order Aliases
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderAlias(UserVisitPK userVisitPK, CreateOrderAliasForm form) {
        return new CreateOrderAliasCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderAlias(UserVisitPK userVisitPK, GetOrderAliasForm form) {
        return new GetOrderAliasCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderAliases(UserVisitPK userVisitPK, GetOrderAliasesForm form) {
        return new GetOrderAliasesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOrderAlias(UserVisitPK userVisitPK, EditOrderAliasForm form) {
        return new EditOrderAliasCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteOrderAlias(UserVisitPK userVisitPK, DeleteOrderAliasForm form) {
        return new DeleteOrderAliasCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Order Priorities
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderPriority(UserVisitPK userVisitPK, CreateOrderPriorityForm form) {
        return new CreateOrderPriorityCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderPriorityChoices(UserVisitPK userVisitPK, GetOrderPriorityChoicesForm form) {
        return new GetOrderPriorityChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderPriority(UserVisitPK userVisitPK, GetOrderPriorityForm form) {
        return new GetOrderPriorityCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderPriorities(UserVisitPK userVisitPK, GetOrderPrioritiesForm form) {
        return new GetOrderPrioritiesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultOrderPriority(UserVisitPK userVisitPK, SetDefaultOrderPriorityForm form) {
        return new SetDefaultOrderPriorityCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOrderPriority(UserVisitPK userVisitPK, EditOrderPriorityForm form) {
        return new EditOrderPriorityCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteOrderPriority(UserVisitPK userVisitPK, DeleteOrderPriorityForm form) {
        return new DeleteOrderPriorityCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Order Priority Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderPriorityDescription(UserVisitPK userVisitPK, CreateOrderPriorityDescriptionForm form) {
        return new CreateOrderPriorityDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderPriorityDescription(UserVisitPK userVisitPK, GetOrderPriorityDescriptionForm form) {
        return new GetOrderPriorityDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderPriorityDescriptions(UserVisitPK userVisitPK, GetOrderPriorityDescriptionsForm form) {
        return new GetOrderPriorityDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOrderPriorityDescription(UserVisitPK userVisitPK, EditOrderPriorityDescriptionForm form) {
        return new EditOrderPriorityDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteOrderPriorityDescription(UserVisitPK userVisitPK, DeleteOrderPriorityDescriptionForm form) {
        return new DeleteOrderPriorityDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Order Adjustment Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderAdjustmentType(UserVisitPK userVisitPK, CreateOrderAdjustmentTypeForm form) {
        return new CreateOrderAdjustmentTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderAdjustmentTypeChoices(UserVisitPK userVisitPK, GetOrderAdjustmentTypeChoicesForm form) {
        return new GetOrderAdjustmentTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderAdjustmentType(UserVisitPK userVisitPK, GetOrderAdjustmentTypeForm form) {
        return new GetOrderAdjustmentTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderAdjustmentTypes(UserVisitPK userVisitPK, GetOrderAdjustmentTypesForm form) {
        return new GetOrderAdjustmentTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultOrderAdjustmentType(UserVisitPK userVisitPK, SetDefaultOrderAdjustmentTypeForm form) {
        return new SetDefaultOrderAdjustmentTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOrderAdjustmentType(UserVisitPK userVisitPK, EditOrderAdjustmentTypeForm form) {
        return new EditOrderAdjustmentTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteOrderAdjustmentType(UserVisitPK userVisitPK, DeleteOrderAdjustmentTypeForm form) {
        return new DeleteOrderAdjustmentTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Order Adjustment Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderAdjustmentTypeDescription(UserVisitPK userVisitPK, CreateOrderAdjustmentTypeDescriptionForm form) {
        return new CreateOrderAdjustmentTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderAdjustmentTypeDescription(UserVisitPK userVisitPK, GetOrderAdjustmentTypeDescriptionForm form) {
        return new GetOrderAdjustmentTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderAdjustmentTypeDescriptions(UserVisitPK userVisitPK, GetOrderAdjustmentTypeDescriptionsForm form) {
        return new GetOrderAdjustmentTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOrderAdjustmentTypeDescription(UserVisitPK userVisitPK, EditOrderAdjustmentTypeDescriptionForm form) {
        return new EditOrderAdjustmentTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteOrderAdjustmentTypeDescription(UserVisitPK userVisitPK, DeleteOrderAdjustmentTypeDescriptionForm form) {
        return new DeleteOrderAdjustmentTypeDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Order Line Adjustment Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderLineAdjustmentType(UserVisitPK userVisitPK, CreateOrderLineAdjustmentTypeForm form) {
        return new CreateOrderLineAdjustmentTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderLineAdjustmentTypeChoices(UserVisitPK userVisitPK, GetOrderLineAdjustmentTypeChoicesForm form) {
        return new GetOrderLineAdjustmentTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderLineAdjustmentType(UserVisitPK userVisitPK, GetOrderLineAdjustmentTypeForm form) {
        return new GetOrderLineAdjustmentTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderLineAdjustmentTypes(UserVisitPK userVisitPK, GetOrderLineAdjustmentTypesForm form) {
        return new GetOrderLineAdjustmentTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultOrderLineAdjustmentType(UserVisitPK userVisitPK, SetDefaultOrderLineAdjustmentTypeForm form) {
        return new SetDefaultOrderLineAdjustmentTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOrderLineAdjustmentType(UserVisitPK userVisitPK, EditOrderLineAdjustmentTypeForm form) {
        return new EditOrderLineAdjustmentTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteOrderLineAdjustmentType(UserVisitPK userVisitPK, DeleteOrderLineAdjustmentTypeForm form) {
        return new DeleteOrderLineAdjustmentTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Order Line Adjustment Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderLineAdjustmentTypeDescription(UserVisitPK userVisitPK, CreateOrderLineAdjustmentTypeDescriptionForm form) {
        return new CreateOrderLineAdjustmentTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderLineAdjustmentTypeDescription(UserVisitPK userVisitPK, GetOrderLineAdjustmentTypeDescriptionForm form) {
        return new GetOrderLineAdjustmentTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderLineAdjustmentTypeDescriptions(UserVisitPK userVisitPK, GetOrderLineAdjustmentTypeDescriptionsForm form) {
        return new GetOrderLineAdjustmentTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOrderLineAdjustmentTypeDescription(UserVisitPK userVisitPK, EditOrderLineAdjustmentTypeDescriptionForm form) {
        return new EditOrderLineAdjustmentTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteOrderLineAdjustmentTypeDescription(UserVisitPK userVisitPK, DeleteOrderLineAdjustmentTypeDescriptionForm form) {
        return new DeleteOrderLineAdjustmentTypeDescriptionCommand().run(userVisitPK, form);
    }
    
}

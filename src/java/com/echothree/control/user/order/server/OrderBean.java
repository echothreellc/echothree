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

package com.echothree.control.user.order.server;

import com.echothree.control.user.order.common.OrderRemote;
import com.echothree.control.user.order.common.form.*;
import com.echothree.control.user.order.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateOrderRoleTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Order Role Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createOrderRoleTypeDescription(UserVisitPK userVisitPK, CreateOrderRoleTypeDescriptionForm form) {
        return CDI.current().select(CreateOrderRoleTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Order Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderType(UserVisitPK userVisitPK, CreateOrderTypeForm form) {
        return CDI.current().select(CreateOrderTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderTypeChoices(UserVisitPK userVisitPK, GetOrderTypeChoicesForm form) {
        return CDI.current().select(GetOrderTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderType(UserVisitPK userVisitPK, GetOrderTypeForm form) {
        return CDI.current().select(GetOrderTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderTypes(UserVisitPK userVisitPK, GetOrderTypesForm form) {
        return CDI.current().select(GetOrderTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultOrderType(UserVisitPK userVisitPK, SetDefaultOrderTypeForm form) {
        return CDI.current().select(SetDefaultOrderTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOrderType(UserVisitPK userVisitPK, EditOrderTypeForm form) {
        return CDI.current().select(EditOrderTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteOrderType(UserVisitPK userVisitPK, DeleteOrderTypeForm form) {
        return CDI.current().select(DeleteOrderTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Order Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderTypeDescription(UserVisitPK userVisitPK, CreateOrderTypeDescriptionForm form) {
        return CDI.current().select(CreateOrderTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderTypeDescription(UserVisitPK userVisitPK, GetOrderTypeDescriptionForm form) {
        return CDI.current().select(GetOrderTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderTypeDescriptions(UserVisitPK userVisitPK, GetOrderTypeDescriptionsForm form) {
        return CDI.current().select(GetOrderTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOrderTypeDescription(UserVisitPK userVisitPK, EditOrderTypeDescriptionForm form) {
        return CDI.current().select(EditOrderTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteOrderTypeDescription(UserVisitPK userVisitPK, DeleteOrderTypeDescriptionForm form) {
        return CDI.current().select(DeleteOrderTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Order Time Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderTimeType(UserVisitPK userVisitPK, CreateOrderTimeTypeForm form) {
        return CDI.current().select(CreateOrderTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderTimeTypeChoices(UserVisitPK userVisitPK, GetOrderTimeTypeChoicesForm form) {
        return CDI.current().select(GetOrderTimeTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderTimeType(UserVisitPK userVisitPK, GetOrderTimeTypeForm form) {
        return CDI.current().select(GetOrderTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderTimeTypes(UserVisitPK userVisitPK, GetOrderTimeTypesForm form) {
        return CDI.current().select(GetOrderTimeTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultOrderTimeType(UserVisitPK userVisitPK, SetDefaultOrderTimeTypeForm form) {
        return CDI.current().select(SetDefaultOrderTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOrderTimeType(UserVisitPK userVisitPK, EditOrderTimeTypeForm form) {
        return CDI.current().select(EditOrderTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteOrderTimeType(UserVisitPK userVisitPK, DeleteOrderTimeTypeForm form) {
        return CDI.current().select(DeleteOrderTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Order Time Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderTimeTypeDescription(UserVisitPK userVisitPK, CreateOrderTimeTypeDescriptionForm form) {
        return CDI.current().select(CreateOrderTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderTimeTypeDescription(UserVisitPK userVisitPK, GetOrderTimeTypeDescriptionForm form) {
        return CDI.current().select(GetOrderTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderTimeTypeDescriptions(UserVisitPK userVisitPK, GetOrderTimeTypeDescriptionsForm form) {
        return CDI.current().select(GetOrderTimeTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOrderTimeTypeDescription(UserVisitPK userVisitPK, EditOrderTimeTypeDescriptionForm form) {
        return CDI.current().select(EditOrderTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteOrderTimeTypeDescription(UserVisitPK userVisitPK, DeleteOrderTimeTypeDescriptionForm form) {
        return CDI.current().select(DeleteOrderTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Order Alias Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderAliasType(UserVisitPK userVisitPK, CreateOrderAliasTypeForm form) {
        return CDI.current().select(CreateOrderAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderAliasTypeChoices(UserVisitPK userVisitPK, GetOrderAliasTypeChoicesForm form) {
        return CDI.current().select(GetOrderAliasTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderAliasType(UserVisitPK userVisitPK, GetOrderAliasTypeForm form) {
        return CDI.current().select(GetOrderAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderAliasTypes(UserVisitPK userVisitPK, GetOrderAliasTypesForm form) {
        return CDI.current().select(GetOrderAliasTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultOrderAliasType(UserVisitPK userVisitPK, SetDefaultOrderAliasTypeForm form) {
        return CDI.current().select(SetDefaultOrderAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOrderAliasType(UserVisitPK userVisitPK, EditOrderAliasTypeForm form) {
        return CDI.current().select(EditOrderAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteOrderAliasType(UserVisitPK userVisitPK, DeleteOrderAliasTypeForm form) {
        return CDI.current().select(DeleteOrderAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Order Alias Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderAliasTypeDescription(UserVisitPK userVisitPK, CreateOrderAliasTypeDescriptionForm form) {
        return CDI.current().select(CreateOrderAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderAliasTypeDescription(UserVisitPK userVisitPK, GetOrderAliasTypeDescriptionForm form) {
        return CDI.current().select(GetOrderAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderAliasTypeDescriptions(UserVisitPK userVisitPK, GetOrderAliasTypeDescriptionsForm form) {
        return CDI.current().select(GetOrderAliasTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOrderAliasTypeDescription(UserVisitPK userVisitPK, EditOrderAliasTypeDescriptionForm form) {
        return CDI.current().select(EditOrderAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteOrderAliasTypeDescription(UserVisitPK userVisitPK, DeleteOrderAliasTypeDescriptionForm form) {
        return CDI.current().select(DeleteOrderAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Order Aliases
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderAlias(UserVisitPK userVisitPK, CreateOrderAliasForm form) {
        return CDI.current().select(CreateOrderAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderAlias(UserVisitPK userVisitPK, GetOrderAliasForm form) {
        return CDI.current().select(GetOrderAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderAliases(UserVisitPK userVisitPK, GetOrderAliasesForm form) {
        return CDI.current().select(GetOrderAliasesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOrderAlias(UserVisitPK userVisitPK, EditOrderAliasForm form) {
        return CDI.current().select(EditOrderAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteOrderAlias(UserVisitPK userVisitPK, DeleteOrderAliasForm form) {
        return CDI.current().select(DeleteOrderAliasCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Order Priorities
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderPriority(UserVisitPK userVisitPK, CreateOrderPriorityForm form) {
        return CDI.current().select(CreateOrderPriorityCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderPriorityChoices(UserVisitPK userVisitPK, GetOrderPriorityChoicesForm form) {
        return CDI.current().select(GetOrderPriorityChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderPriority(UserVisitPK userVisitPK, GetOrderPriorityForm form) {
        return CDI.current().select(GetOrderPriorityCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderPriorities(UserVisitPK userVisitPK, GetOrderPrioritiesForm form) {
        return CDI.current().select(GetOrderPrioritiesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultOrderPriority(UserVisitPK userVisitPK, SetDefaultOrderPriorityForm form) {
        return CDI.current().select(SetDefaultOrderPriorityCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOrderPriority(UserVisitPK userVisitPK, EditOrderPriorityForm form) {
        return CDI.current().select(EditOrderPriorityCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteOrderPriority(UserVisitPK userVisitPK, DeleteOrderPriorityForm form) {
        return CDI.current().select(DeleteOrderPriorityCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Order Priority Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderPriorityDescription(UserVisitPK userVisitPK, CreateOrderPriorityDescriptionForm form) {
        return CDI.current().select(CreateOrderPriorityDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderPriorityDescription(UserVisitPK userVisitPK, GetOrderPriorityDescriptionForm form) {
        return CDI.current().select(GetOrderPriorityDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderPriorityDescriptions(UserVisitPK userVisitPK, GetOrderPriorityDescriptionsForm form) {
        return CDI.current().select(GetOrderPriorityDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOrderPriorityDescription(UserVisitPK userVisitPK, EditOrderPriorityDescriptionForm form) {
        return CDI.current().select(EditOrderPriorityDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteOrderPriorityDescription(UserVisitPK userVisitPK, DeleteOrderPriorityDescriptionForm form) {
        return CDI.current().select(DeleteOrderPriorityDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Order Adjustment Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderAdjustmentType(UserVisitPK userVisitPK, CreateOrderAdjustmentTypeForm form) {
        return CDI.current().select(CreateOrderAdjustmentTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderAdjustmentTypeChoices(UserVisitPK userVisitPK, GetOrderAdjustmentTypeChoicesForm form) {
        return CDI.current().select(GetOrderAdjustmentTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderAdjustmentType(UserVisitPK userVisitPK, GetOrderAdjustmentTypeForm form) {
        return CDI.current().select(GetOrderAdjustmentTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderAdjustmentTypes(UserVisitPK userVisitPK, GetOrderAdjustmentTypesForm form) {
        return CDI.current().select(GetOrderAdjustmentTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultOrderAdjustmentType(UserVisitPK userVisitPK, SetDefaultOrderAdjustmentTypeForm form) {
        return CDI.current().select(SetDefaultOrderAdjustmentTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOrderAdjustmentType(UserVisitPK userVisitPK, EditOrderAdjustmentTypeForm form) {
        return CDI.current().select(EditOrderAdjustmentTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteOrderAdjustmentType(UserVisitPK userVisitPK, DeleteOrderAdjustmentTypeForm form) {
        return CDI.current().select(DeleteOrderAdjustmentTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Order Adjustment Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderAdjustmentTypeDescription(UserVisitPK userVisitPK, CreateOrderAdjustmentTypeDescriptionForm form) {
        return CDI.current().select(CreateOrderAdjustmentTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderAdjustmentTypeDescription(UserVisitPK userVisitPK, GetOrderAdjustmentTypeDescriptionForm form) {
        return CDI.current().select(GetOrderAdjustmentTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderAdjustmentTypeDescriptions(UserVisitPK userVisitPK, GetOrderAdjustmentTypeDescriptionsForm form) {
        return CDI.current().select(GetOrderAdjustmentTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOrderAdjustmentTypeDescription(UserVisitPK userVisitPK, EditOrderAdjustmentTypeDescriptionForm form) {
        return CDI.current().select(EditOrderAdjustmentTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteOrderAdjustmentTypeDescription(UserVisitPK userVisitPK, DeleteOrderAdjustmentTypeDescriptionForm form) {
        return CDI.current().select(DeleteOrderAdjustmentTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Order Line Adjustment Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderLineAdjustmentType(UserVisitPK userVisitPK, CreateOrderLineAdjustmentTypeForm form) {
        return CDI.current().select(CreateOrderLineAdjustmentTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderLineAdjustmentTypeChoices(UserVisitPK userVisitPK, GetOrderLineAdjustmentTypeChoicesForm form) {
        return CDI.current().select(GetOrderLineAdjustmentTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderLineAdjustmentType(UserVisitPK userVisitPK, GetOrderLineAdjustmentTypeForm form) {
        return CDI.current().select(GetOrderLineAdjustmentTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderLineAdjustmentTypes(UserVisitPK userVisitPK, GetOrderLineAdjustmentTypesForm form) {
        return CDI.current().select(GetOrderLineAdjustmentTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultOrderLineAdjustmentType(UserVisitPK userVisitPK, SetDefaultOrderLineAdjustmentTypeForm form) {
        return CDI.current().select(SetDefaultOrderLineAdjustmentTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOrderLineAdjustmentType(UserVisitPK userVisitPK, EditOrderLineAdjustmentTypeForm form) {
        return CDI.current().select(EditOrderLineAdjustmentTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteOrderLineAdjustmentType(UserVisitPK userVisitPK, DeleteOrderLineAdjustmentTypeForm form) {
        return CDI.current().select(DeleteOrderLineAdjustmentTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Order Line Adjustment Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createOrderLineAdjustmentTypeDescription(UserVisitPK userVisitPK, CreateOrderLineAdjustmentTypeDescriptionForm form) {
        return CDI.current().select(CreateOrderLineAdjustmentTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderLineAdjustmentTypeDescription(UserVisitPK userVisitPK, GetOrderLineAdjustmentTypeDescriptionForm form) {
        return CDI.current().select(GetOrderLineAdjustmentTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOrderLineAdjustmentTypeDescriptions(UserVisitPK userVisitPK, GetOrderLineAdjustmentTypeDescriptionsForm form) {
        return CDI.current().select(GetOrderLineAdjustmentTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOrderLineAdjustmentTypeDescription(UserVisitPK userVisitPK, EditOrderLineAdjustmentTypeDescriptionForm form) {
        return CDI.current().select(EditOrderLineAdjustmentTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteOrderLineAdjustmentTypeDescription(UserVisitPK userVisitPK, DeleteOrderLineAdjustmentTypeDescriptionForm form) {
        return CDI.current().select(DeleteOrderLineAdjustmentTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
}

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

package com.echothree.control.user.order.common;

import com.echothree.control.user.order.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface OrderService
        extends OrderForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Order Role Types
    // -------------------------------------------------------------------------
    
    CommandResult createOrderRoleType(UserVisitPK userVisitPK, CreateOrderRoleTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Order Role Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createOrderRoleTypeDescription(UserVisitPK userVisitPK, CreateOrderRoleTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Order Types
    // --------------------------------------------------------------------------------

    CommandResult createOrderType(UserVisitPK userVisitPK, CreateOrderTypeForm form);

    CommandResult getOrderTypeChoices(UserVisitPK userVisitPK, GetOrderTypeChoicesForm form);

    CommandResult getOrderType(UserVisitPK userVisitPK, GetOrderTypeForm form);

    CommandResult getOrderTypes(UserVisitPK userVisitPK, GetOrderTypesForm form);

    CommandResult setDefaultOrderType(UserVisitPK userVisitPK, SetDefaultOrderTypeForm form);

    CommandResult editOrderType(UserVisitPK userVisitPK, EditOrderTypeForm form);

    CommandResult deleteOrderType(UserVisitPK userVisitPK, DeleteOrderTypeForm form);

    // --------------------------------------------------------------------------------
    //   Order Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createOrderTypeDescription(UserVisitPK userVisitPK, CreateOrderTypeDescriptionForm form);

    CommandResult getOrderTypeDescription(UserVisitPK userVisitPK, GetOrderTypeDescriptionForm form);

    CommandResult getOrderTypeDescriptions(UserVisitPK userVisitPK, GetOrderTypeDescriptionsForm form);

    CommandResult editOrderTypeDescription(UserVisitPK userVisitPK, EditOrderTypeDescriptionForm form);

    CommandResult deleteOrderTypeDescription(UserVisitPK userVisitPK, DeleteOrderTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Order Time Types
    // --------------------------------------------------------------------------------

    CommandResult createOrderTimeType(UserVisitPK userVisitPK, CreateOrderTimeTypeForm form);

    CommandResult getOrderTimeTypeChoices(UserVisitPK userVisitPK, GetOrderTimeTypeChoicesForm form);

    CommandResult getOrderTimeType(UserVisitPK userVisitPK, GetOrderTimeTypeForm form);

    CommandResult getOrderTimeTypes(UserVisitPK userVisitPK, GetOrderTimeTypesForm form);

    CommandResult setDefaultOrderTimeType(UserVisitPK userVisitPK, SetDefaultOrderTimeTypeForm form);

    CommandResult editOrderTimeType(UserVisitPK userVisitPK, EditOrderTimeTypeForm form);

    CommandResult deleteOrderTimeType(UserVisitPK userVisitPK, DeleteOrderTimeTypeForm form);

    // --------------------------------------------------------------------------------
    //   Order Time Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createOrderTimeTypeDescription(UserVisitPK userVisitPK, CreateOrderTimeTypeDescriptionForm form);

    CommandResult getOrderTimeTypeDescription(UserVisitPK userVisitPK, GetOrderTimeTypeDescriptionForm form);

    CommandResult getOrderTimeTypeDescriptions(UserVisitPK userVisitPK, GetOrderTimeTypeDescriptionsForm form);

    CommandResult editOrderTimeTypeDescription(UserVisitPK userVisitPK, EditOrderTimeTypeDescriptionForm form);

    CommandResult deleteOrderTimeTypeDescription(UserVisitPK userVisitPK, DeleteOrderTimeTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Order Alias Types
    // --------------------------------------------------------------------------------

    CommandResult createOrderAliasType(UserVisitPK userVisitPK, CreateOrderAliasTypeForm form);

    CommandResult getOrderAliasTypeChoices(UserVisitPK userVisitPK, GetOrderAliasTypeChoicesForm form);

    CommandResult getOrderAliasType(UserVisitPK userVisitPK, GetOrderAliasTypeForm form);

    CommandResult getOrderAliasTypes(UserVisitPK userVisitPK, GetOrderAliasTypesForm form);

    CommandResult setDefaultOrderAliasType(UserVisitPK userVisitPK, SetDefaultOrderAliasTypeForm form);

    CommandResult editOrderAliasType(UserVisitPK userVisitPK, EditOrderAliasTypeForm form);

    CommandResult deleteOrderAliasType(UserVisitPK userVisitPK, DeleteOrderAliasTypeForm form);

    // --------------------------------------------------------------------------------
    //   Order Alias Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createOrderAliasTypeDescription(UserVisitPK userVisitPK, CreateOrderAliasTypeDescriptionForm form);

    CommandResult getOrderAliasTypeDescription(UserVisitPK userVisitPK, GetOrderAliasTypeDescriptionForm form);

    CommandResult getOrderAliasTypeDescriptions(UserVisitPK userVisitPK, GetOrderAliasTypeDescriptionsForm form);

    CommandResult editOrderAliasTypeDescription(UserVisitPK userVisitPK, EditOrderAliasTypeDescriptionForm form);

    CommandResult deleteOrderAliasTypeDescription(UserVisitPK userVisitPK, DeleteOrderAliasTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Order Aliases
    // --------------------------------------------------------------------------------

    CommandResult createOrderAlias(UserVisitPK userVisitPK, CreateOrderAliasForm form);

    CommandResult getOrderAlias(UserVisitPK userVisitPK, GetOrderAliasForm form);

    CommandResult getOrderAliases(UserVisitPK userVisitPK, GetOrderAliasesForm form);

    CommandResult editOrderAlias(UserVisitPK userVisitPK, EditOrderAliasForm form);

    CommandResult deleteOrderAlias(UserVisitPK userVisitPK, DeleteOrderAliasForm form);

    // --------------------------------------------------------------------------------
    //   Order Priorities
    // --------------------------------------------------------------------------------

    CommandResult createOrderPriority(UserVisitPK userVisitPK, CreateOrderPriorityForm form);

    CommandResult getOrderPriorityChoices(UserVisitPK userVisitPK, GetOrderPriorityChoicesForm form);

    CommandResult getOrderPriority(UserVisitPK userVisitPK, GetOrderPriorityForm form);

    CommandResult getOrderPriorities(UserVisitPK userVisitPK, GetOrderPrioritiesForm form);

    CommandResult setDefaultOrderPriority(UserVisitPK userVisitPK, SetDefaultOrderPriorityForm form);

    CommandResult editOrderPriority(UserVisitPK userVisitPK, EditOrderPriorityForm form);

    CommandResult deleteOrderPriority(UserVisitPK userVisitPK, DeleteOrderPriorityForm form);

    // --------------------------------------------------------------------------------
    //   Order Priority Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createOrderPriorityDescription(UserVisitPK userVisitPK, CreateOrderPriorityDescriptionForm form);

    CommandResult getOrderPriorityDescription(UserVisitPK userVisitPK, GetOrderPriorityDescriptionForm form);

    CommandResult getOrderPriorityDescriptions(UserVisitPK userVisitPK, GetOrderPriorityDescriptionsForm form);

    CommandResult editOrderPriorityDescription(UserVisitPK userVisitPK, EditOrderPriorityDescriptionForm form);

    CommandResult deleteOrderPriorityDescription(UserVisitPK userVisitPK, DeleteOrderPriorityDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Order Adjustment Types
    // --------------------------------------------------------------------------------

    CommandResult createOrderAdjustmentType(UserVisitPK userVisitPK, CreateOrderAdjustmentTypeForm form);

    CommandResult getOrderAdjustmentTypeChoices(UserVisitPK userVisitPK, GetOrderAdjustmentTypeChoicesForm form);

    CommandResult getOrderAdjustmentType(UserVisitPK userVisitPK, GetOrderAdjustmentTypeForm form);

    CommandResult getOrderAdjustmentTypes(UserVisitPK userVisitPK, GetOrderAdjustmentTypesForm form);

    CommandResult setDefaultOrderAdjustmentType(UserVisitPK userVisitPK, SetDefaultOrderAdjustmentTypeForm form);

    CommandResult editOrderAdjustmentType(UserVisitPK userVisitPK, EditOrderAdjustmentTypeForm form);

    CommandResult deleteOrderAdjustmentType(UserVisitPK userVisitPK, DeleteOrderAdjustmentTypeForm form);

    // --------------------------------------------------------------------------------
    //   Order Adjustment Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createOrderAdjustmentTypeDescription(UserVisitPK userVisitPK, CreateOrderAdjustmentTypeDescriptionForm form);

    CommandResult getOrderAdjustmentTypeDescription(UserVisitPK userVisitPK, GetOrderAdjustmentTypeDescriptionForm form);

    CommandResult getOrderAdjustmentTypeDescriptions(UserVisitPK userVisitPK, GetOrderAdjustmentTypeDescriptionsForm form);

    CommandResult editOrderAdjustmentTypeDescription(UserVisitPK userVisitPK, EditOrderAdjustmentTypeDescriptionForm form);

    CommandResult deleteOrderAdjustmentTypeDescription(UserVisitPK userVisitPK, DeleteOrderAdjustmentTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Order Line Adjustment Types
    // --------------------------------------------------------------------------------

    CommandResult createOrderLineAdjustmentType(UserVisitPK userVisitPK, CreateOrderLineAdjustmentTypeForm form);

    CommandResult getOrderLineAdjustmentTypeChoices(UserVisitPK userVisitPK, GetOrderLineAdjustmentTypeChoicesForm form);

    CommandResult getOrderLineAdjustmentType(UserVisitPK userVisitPK, GetOrderLineAdjustmentTypeForm form);

    CommandResult getOrderLineAdjustmentTypes(UserVisitPK userVisitPK, GetOrderLineAdjustmentTypesForm form);

    CommandResult setDefaultOrderLineAdjustmentType(UserVisitPK userVisitPK, SetDefaultOrderLineAdjustmentTypeForm form);

    CommandResult editOrderLineAdjustmentType(UserVisitPK userVisitPK, EditOrderLineAdjustmentTypeForm form);

    CommandResult deleteOrderLineAdjustmentType(UserVisitPK userVisitPK, DeleteOrderLineAdjustmentTypeForm form);

    // --------------------------------------------------------------------------------
    //   Order Line Adjustment Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createOrderLineAdjustmentTypeDescription(UserVisitPK userVisitPK, CreateOrderLineAdjustmentTypeDescriptionForm form);

    CommandResult getOrderLineAdjustmentTypeDescription(UserVisitPK userVisitPK, GetOrderLineAdjustmentTypeDescriptionForm form);

    CommandResult getOrderLineAdjustmentTypeDescriptions(UserVisitPK userVisitPK, GetOrderLineAdjustmentTypeDescriptionsForm form);

    CommandResult editOrderLineAdjustmentTypeDescription(UserVisitPK userVisitPK, EditOrderLineAdjustmentTypeDescriptionForm form);

    CommandResult deleteOrderLineAdjustmentTypeDescription(UserVisitPK userVisitPK, DeleteOrderLineAdjustmentTypeDescriptionForm form);

}

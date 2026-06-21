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
import com.echothree.control.user.order.common.result.*;
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
    
    CommandResult<?> createOrderRoleType(UserVisitPK userVisitPK, CreateOrderRoleTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Order Role Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createOrderRoleTypeDescription(UserVisitPK userVisitPK, CreateOrderRoleTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Order Types
    // --------------------------------------------------------------------------------

    CommandResult<?> createOrderType(UserVisitPK userVisitPK, CreateOrderTypeForm form);

    CommandResult<GetOrderTypeChoicesResult> getOrderTypeChoices(UserVisitPK userVisitPK, GetOrderTypeChoicesForm form);

    CommandResult<GetOrderTypeResult> getOrderType(UserVisitPK userVisitPK, GetOrderTypeForm form);

    CommandResult<GetOrderTypesResult> getOrderTypes(UserVisitPK userVisitPK, GetOrderTypesForm form);

    CommandResult<?> setDefaultOrderType(UserVisitPK userVisitPK, SetDefaultOrderTypeForm form);

    CommandResult<EditOrderTypeResult> editOrderType(UserVisitPK userVisitPK, EditOrderTypeForm form);

    CommandResult<?> deleteOrderType(UserVisitPK userVisitPK, DeleteOrderTypeForm form);

    // --------------------------------------------------------------------------------
    //   Order Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<?> createOrderTypeDescription(UserVisitPK userVisitPK, CreateOrderTypeDescriptionForm form);

    CommandResult<GetOrderTypeDescriptionResult> getOrderTypeDescription(UserVisitPK userVisitPK, GetOrderTypeDescriptionForm form);

    CommandResult<GetOrderTypeDescriptionsResult> getOrderTypeDescriptions(UserVisitPK userVisitPK, GetOrderTypeDescriptionsForm form);

    CommandResult<EditOrderTypeDescriptionResult> editOrderTypeDescription(UserVisitPK userVisitPK, EditOrderTypeDescriptionForm form);

    CommandResult<?> deleteOrderTypeDescription(UserVisitPK userVisitPK, DeleteOrderTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Order Time Types
    // --------------------------------------------------------------------------------

    CommandResult<?> createOrderTimeType(UserVisitPK userVisitPK, CreateOrderTimeTypeForm form);

    CommandResult<GetOrderTimeTypeChoicesResult> getOrderTimeTypeChoices(UserVisitPK userVisitPK, GetOrderTimeTypeChoicesForm form);

    CommandResult<GetOrderTimeTypeResult> getOrderTimeType(UserVisitPK userVisitPK, GetOrderTimeTypeForm form);

    CommandResult<GetOrderTimeTypesResult> getOrderTimeTypes(UserVisitPK userVisitPK, GetOrderTimeTypesForm form);

    CommandResult<?> setDefaultOrderTimeType(UserVisitPK userVisitPK, SetDefaultOrderTimeTypeForm form);

    CommandResult<EditOrderTimeTypeResult> editOrderTimeType(UserVisitPK userVisitPK, EditOrderTimeTypeForm form);

    CommandResult<?> deleteOrderTimeType(UserVisitPK userVisitPK, DeleteOrderTimeTypeForm form);

    // --------------------------------------------------------------------------------
    //   Order Time Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<?> createOrderTimeTypeDescription(UserVisitPK userVisitPK, CreateOrderTimeTypeDescriptionForm form);

    CommandResult<GetOrderTimeTypeDescriptionResult> getOrderTimeTypeDescription(UserVisitPK userVisitPK, GetOrderTimeTypeDescriptionForm form);

    CommandResult<GetOrderTimeTypeDescriptionsResult> getOrderTimeTypeDescriptions(UserVisitPK userVisitPK, GetOrderTimeTypeDescriptionsForm form);

    CommandResult<EditOrderTimeTypeDescriptionResult> editOrderTimeTypeDescription(UserVisitPK userVisitPK, EditOrderTimeTypeDescriptionForm form);

    CommandResult<?> deleteOrderTimeTypeDescription(UserVisitPK userVisitPK, DeleteOrderTimeTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Order Alias Types
    // --------------------------------------------------------------------------------

    CommandResult<?> createOrderAliasType(UserVisitPK userVisitPK, CreateOrderAliasTypeForm form);

    CommandResult<GetOrderAliasTypeChoicesResult> getOrderAliasTypeChoices(UserVisitPK userVisitPK, GetOrderAliasTypeChoicesForm form);

    CommandResult<GetOrderAliasTypeResult> getOrderAliasType(UserVisitPK userVisitPK, GetOrderAliasTypeForm form);

    CommandResult<GetOrderAliasTypesResult> getOrderAliasTypes(UserVisitPK userVisitPK, GetOrderAliasTypesForm form);

    CommandResult<?> setDefaultOrderAliasType(UserVisitPK userVisitPK, SetDefaultOrderAliasTypeForm form);

    CommandResult<EditOrderAliasTypeResult> editOrderAliasType(UserVisitPK userVisitPK, EditOrderAliasTypeForm form);

    CommandResult<?> deleteOrderAliasType(UserVisitPK userVisitPK, DeleteOrderAliasTypeForm form);

    // --------------------------------------------------------------------------------
    //   Order Alias Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<?> createOrderAliasTypeDescription(UserVisitPK userVisitPK, CreateOrderAliasTypeDescriptionForm form);

    CommandResult<GetOrderAliasTypeDescriptionResult> getOrderAliasTypeDescription(UserVisitPK userVisitPK, GetOrderAliasTypeDescriptionForm form);

    CommandResult<GetOrderAliasTypeDescriptionsResult> getOrderAliasTypeDescriptions(UserVisitPK userVisitPK, GetOrderAliasTypeDescriptionsForm form);

    CommandResult<EditOrderAliasTypeDescriptionResult> editOrderAliasTypeDescription(UserVisitPK userVisitPK, EditOrderAliasTypeDescriptionForm form);

    CommandResult<?> deleteOrderAliasTypeDescription(UserVisitPK userVisitPK, DeleteOrderAliasTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Order Aliases
    // --------------------------------------------------------------------------------

    CommandResult<?> createOrderAlias(UserVisitPK userVisitPK, CreateOrderAliasForm form);

    CommandResult<GetOrderAliasResult> getOrderAlias(UserVisitPK userVisitPK, GetOrderAliasForm form);

    CommandResult<GetOrderAliasesResult> getOrderAliases(UserVisitPK userVisitPK, GetOrderAliasesForm form);

    CommandResult<EditOrderAliasResult> editOrderAlias(UserVisitPK userVisitPK, EditOrderAliasForm form);

    CommandResult<?> deleteOrderAlias(UserVisitPK userVisitPK, DeleteOrderAliasForm form);

    // --------------------------------------------------------------------------------
    //   Order Priorities
    // --------------------------------------------------------------------------------

    CommandResult<?> createOrderPriority(UserVisitPK userVisitPK, CreateOrderPriorityForm form);

    CommandResult<GetOrderPriorityChoicesResult> getOrderPriorityChoices(UserVisitPK userVisitPK, GetOrderPriorityChoicesForm form);

    CommandResult<GetOrderPriorityResult> getOrderPriority(UserVisitPK userVisitPK, GetOrderPriorityForm form);

    CommandResult<GetOrderPrioritiesResult> getOrderPriorities(UserVisitPK userVisitPK, GetOrderPrioritiesForm form);

    CommandResult<?> setDefaultOrderPriority(UserVisitPK userVisitPK, SetDefaultOrderPriorityForm form);

    CommandResult<EditOrderPriorityResult> editOrderPriority(UserVisitPK userVisitPK, EditOrderPriorityForm form);

    CommandResult<?> deleteOrderPriority(UserVisitPK userVisitPK, DeleteOrderPriorityForm form);

    // --------------------------------------------------------------------------------
    //   Order Priority Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<?> createOrderPriorityDescription(UserVisitPK userVisitPK, CreateOrderPriorityDescriptionForm form);

    CommandResult<GetOrderPriorityDescriptionResult> getOrderPriorityDescription(UserVisitPK userVisitPK, GetOrderPriorityDescriptionForm form);

    CommandResult<GetOrderPriorityDescriptionsResult> getOrderPriorityDescriptions(UserVisitPK userVisitPK, GetOrderPriorityDescriptionsForm form);

    CommandResult<EditOrderPriorityDescriptionResult> editOrderPriorityDescription(UserVisitPK userVisitPK, EditOrderPriorityDescriptionForm form);

    CommandResult<?> deleteOrderPriorityDescription(UserVisitPK userVisitPK, DeleteOrderPriorityDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Order Adjustment Types
    // --------------------------------------------------------------------------------

    CommandResult<?> createOrderAdjustmentType(UserVisitPK userVisitPK, CreateOrderAdjustmentTypeForm form);

    CommandResult<GetOrderAdjustmentTypeChoicesResult> getOrderAdjustmentTypeChoices(UserVisitPK userVisitPK, GetOrderAdjustmentTypeChoicesForm form);

    CommandResult<GetOrderAdjustmentTypeResult> getOrderAdjustmentType(UserVisitPK userVisitPK, GetOrderAdjustmentTypeForm form);

    CommandResult<GetOrderAdjustmentTypesResult> getOrderAdjustmentTypes(UserVisitPK userVisitPK, GetOrderAdjustmentTypesForm form);

    CommandResult<?> setDefaultOrderAdjustmentType(UserVisitPK userVisitPK, SetDefaultOrderAdjustmentTypeForm form);

    CommandResult<EditOrderAdjustmentTypeResult> editOrderAdjustmentType(UserVisitPK userVisitPK, EditOrderAdjustmentTypeForm form);

    CommandResult<?> deleteOrderAdjustmentType(UserVisitPK userVisitPK, DeleteOrderAdjustmentTypeForm form);

    // --------------------------------------------------------------------------------
    //   Order Adjustment Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<?> createOrderAdjustmentTypeDescription(UserVisitPK userVisitPK, CreateOrderAdjustmentTypeDescriptionForm form);

    CommandResult<GetOrderAdjustmentTypeDescriptionResult> getOrderAdjustmentTypeDescription(UserVisitPK userVisitPK, GetOrderAdjustmentTypeDescriptionForm form);

    CommandResult<GetOrderAdjustmentTypeDescriptionsResult> getOrderAdjustmentTypeDescriptions(UserVisitPK userVisitPK, GetOrderAdjustmentTypeDescriptionsForm form);

    CommandResult<EditOrderAdjustmentTypeDescriptionResult> editOrderAdjustmentTypeDescription(UserVisitPK userVisitPK, EditOrderAdjustmentTypeDescriptionForm form);

    CommandResult<?> deleteOrderAdjustmentTypeDescription(UserVisitPK userVisitPK, DeleteOrderAdjustmentTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Order Line Adjustment Types
    // --------------------------------------------------------------------------------

    CommandResult<?> createOrderLineAdjustmentType(UserVisitPK userVisitPK, CreateOrderLineAdjustmentTypeForm form);

    CommandResult<GetOrderLineAdjustmentTypeChoicesResult> getOrderLineAdjustmentTypeChoices(UserVisitPK userVisitPK, GetOrderLineAdjustmentTypeChoicesForm form);

    CommandResult<GetOrderLineAdjustmentTypeResult> getOrderLineAdjustmentType(UserVisitPK userVisitPK, GetOrderLineAdjustmentTypeForm form);

    CommandResult<GetOrderLineAdjustmentTypesResult> getOrderLineAdjustmentTypes(UserVisitPK userVisitPK, GetOrderLineAdjustmentTypesForm form);

    CommandResult<?> setDefaultOrderLineAdjustmentType(UserVisitPK userVisitPK, SetDefaultOrderLineAdjustmentTypeForm form);

    CommandResult<EditOrderLineAdjustmentTypeResult> editOrderLineAdjustmentType(UserVisitPK userVisitPK, EditOrderLineAdjustmentTypeForm form);

    CommandResult<?> deleteOrderLineAdjustmentType(UserVisitPK userVisitPK, DeleteOrderLineAdjustmentTypeForm form);

    // --------------------------------------------------------------------------------
    //   Order Line Adjustment Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<?> createOrderLineAdjustmentTypeDescription(UserVisitPK userVisitPK, CreateOrderLineAdjustmentTypeDescriptionForm form);

    CommandResult<GetOrderLineAdjustmentTypeDescriptionResult> getOrderLineAdjustmentTypeDescription(UserVisitPK userVisitPK, GetOrderLineAdjustmentTypeDescriptionForm form);

    CommandResult<GetOrderLineAdjustmentTypeDescriptionsResult> getOrderLineAdjustmentTypeDescriptions(UserVisitPK userVisitPK, GetOrderLineAdjustmentTypeDescriptionsForm form);

    CommandResult<EditOrderLineAdjustmentTypeDescriptionResult> editOrderLineAdjustmentTypeDescription(UserVisitPK userVisitPK, EditOrderLineAdjustmentTypeDescriptionForm form);

    CommandResult<?> deleteOrderLineAdjustmentTypeDescription(UserVisitPK userVisitPK, DeleteOrderLineAdjustmentTypeDescriptionForm form);

}

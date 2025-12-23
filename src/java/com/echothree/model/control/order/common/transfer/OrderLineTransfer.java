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

package com.echothree.model.control.order.common.transfer;

import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationPolicyTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryConditionTransfer;
import com.echothree.model.control.item.common.transfer.ItemTransfer;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnPolicyTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.MapWrapper;

public class OrderLineTransfer<O extends OrderTransfer>
        extends BaseTransfer {
    
    protected O order;
    protected Integer orderLineSequence;
    private ItemTransfer item;
    private InventoryConditionTransfer inventoryCondition;
    private UnitOfMeasureTypeTransfer unitOfMeasureType;
    private Long quantity;
    private Long unformattedUnitAmount;
    private String unitAmount;
    private String description;
    private CancellationPolicyTransfer cancellationPolicy;
    private ReturnPolicyTransfer returnPolicy;
    private Boolean taxable;

    private MapWrapper<OrderLineTimeTransfer> orderTimes;

    protected OrderLineTransfer(O order, Integer orderLineSequence, ItemTransfer item, InventoryConditionTransfer inventoryCondition,
            UnitOfMeasureTypeTransfer unitOfMeasureType, Long quantity, Long unformattedUnitAmount, String unitAmount, String description,
            CancellationPolicyTransfer cancellationPolicy, ReturnPolicyTransfer returnPolicy, Boolean taxable) {
        this.order = order;
        this.orderLineSequence = orderLineSequence;
        this.item = item;
        this.inventoryCondition = inventoryCondition;
        this.unitOfMeasureType = unitOfMeasureType;
        this.quantity = quantity;
        this.unformattedUnitAmount = unformattedUnitAmount;
        this.unitAmount = unitAmount;
        this.description = description;
        this.cancellationPolicy = cancellationPolicy;
        this.returnPolicy = returnPolicy;
        this.taxable = taxable;
    }

    /**
     * Returns the order.
     * @return the order
     */
    public O getOrder() {
        return order;
    }

    /**
     * Sets the order.
     * @param order the order to set
     */
    public void setOrder(O order) {
        this.order = order;
    }

    /**
     * Returns the orderLineSequence.
     * @return the orderLineSequence
     */
    public Integer getOrderLineSequence() {
        return orderLineSequence;
    }

    /**
     * Sets the orderLineSequence.
     * @param orderLineSequence the orderLineSequence to set
     */
    public void setOrderLineSequence(Integer orderLineSequence) {
        this.orderLineSequence = orderLineSequence;
    }

    /**
     * Returns the item.
     * @return the item
     */
    public ItemTransfer getItem() {
        return item;
    }

    /**
     * Sets the item.
     * @param item the item to set
     */
    public void setItem(ItemTransfer item) {
        this.item = item;
    }

    /**
     * Returns the inventoryCondition.
     * @return the inventoryCondition
     */
    public InventoryConditionTransfer getInventoryCondition() {
        return inventoryCondition;
    }

    /**
     * Sets the inventoryCondition.
     * @param inventoryCondition the inventoryCondition to set
     */
    public void setInventoryCondition(InventoryConditionTransfer inventoryCondition) {
        this.inventoryCondition = inventoryCondition;
    }

    /**
     * Returns the unitOfMeasureType.
     * @return the unitOfMeasureType
     */
    public UnitOfMeasureTypeTransfer getUnitOfMeasureType() {
        return unitOfMeasureType;
    }

    /**
     * Sets the unitOfMeasureType.
     * @param unitOfMeasureType the unitOfMeasureType to set
     */
    public void setUnitOfMeasureType(UnitOfMeasureTypeTransfer unitOfMeasureType) {
        this.unitOfMeasureType = unitOfMeasureType;
    }

    /**
     * Returns the quantity.
     * @return the quantity
     */
    public Long getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity.
     * @param quantity the quantity to set
     */
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns the unformattedUnitAmount.
     * @return the unformattedUnitAmount
     */
    public Long getUnformattedUnitAmount() {
        return unformattedUnitAmount;
    }

    /**
     * Sets the unformattedUnitAmount.
     * @param unformattedUnitAmount the unformattedUnitAmount to set
     */
    public void setUnformattedUnitAmount(Long unformattedUnitAmount) {
        this.unformattedUnitAmount = unformattedUnitAmount;
    }

    /**
     * Returns the unitAmount.
     * @return the unitAmount
     */
    public String getUnitAmount() {
        return unitAmount;
    }

    /**
     * Sets the unitAmount.
     * @param unitAmount the unitAmount to set
     */
    public void setUnitAmount(String unitAmount) {
        this.unitAmount = unitAmount;
    }

    /**
     * Returns the description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the cancellationPolicy.
     * @return the cancellationPolicy
     */
    public CancellationPolicyTransfer getCancellationPolicy() {
        return cancellationPolicy;
    }

    /**
     * Sets the cancellationPolicy.
     * @param cancellationPolicy the cancellationPolicy to set
     */
    public void setCancellationPolicy(CancellationPolicyTransfer cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

    /**
     * Returns the returnPolicy.
     * @return the returnPolicy
     */
    public ReturnPolicyTransfer getReturnPolicy() {
        return returnPolicy;
    }

    /**
     * Sets the returnPolicy.
     * @param returnPolicy the returnPolicy to set
     */
    public void setReturnPolicy(ReturnPolicyTransfer returnPolicy) {
        this.returnPolicy = returnPolicy;
    }

    /**
     * Returns the taxable.
     * @return the taxable
     */
    public Boolean getTaxable() {
        return taxable;
    }

    /**
     * Sets the taxable.
     * @param taxable the taxable to set
     */
    public void setTaxable(Boolean taxable) {
        this.taxable = taxable;
    }

    /**
     * Returns the orderTimes.
     * @return the orderTimes
     */
    public MapWrapper<OrderLineTimeTransfer> getOrderTimes() {
        return orderTimes;
    }

    /**
     * Sets the orderTimes.
     * @param orderTimes the orderTimes to set
     */
    public void setOrderTimes(MapWrapper<OrderLineTimeTransfer> orderTimes) {
        this.orderTimes = orderTimes;
    }

}
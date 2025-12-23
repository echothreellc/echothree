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

package com.echothree.ui.cli.amazon.batch.order.content;

import com.echothree.util.common.string.StringUtils;
import java.util.HashMap;
import java.util.Map;

public class AmazonOrder
        implements AmazonOrderKey {

    private AmazonOrders amazonOrders;
    
    private String orderId;
    private String purchaseDate;
    private String paymentsDate;
    private String buyerName;
    private String buyerEmail;

    private Map<String, AmazonOrderShipmentGroup> amazonOrderShipmentGroups;

    private String customerName;
    private String partyName;
    private String orderName;

    /** Creates a new instance of AmazonOrder */
    public AmazonOrder(AmazonOrders amazonOrders, Map<String, String> dataFieldMap) {
        this.amazonOrders = amazonOrders;
        orderId = StringUtils.getInstance().trimToNull(dataFieldMap.get(AmazonOrderFields.Field_OrderId));
        purchaseDate = StringUtils.getInstance().trimToNull(dataFieldMap.get(AmazonOrderFields.Field_PurchaseDate));
        paymentsDate = StringUtils.getInstance().trimToNull(dataFieldMap.get(AmazonOrderFields.Field_PaymentsDate));
        buyerName = StringUtils.getInstance().trimToNull(dataFieldMap.get(AmazonOrderFields.Field_BuyerName));
        buyerEmail = StringUtils.getInstance().trimToNull(dataFieldMap.get(AmazonOrderFields.Field_BuyerEmail));
    }

    @Override
    public String getKey() {
        return getOrderId();
    }

    /**
     * Returns the amazonOrders.
     * @return the amazonOrders
     */
    public AmazonOrders getAmazonOrders() {
        return amazonOrders;
    }

    /**
     * Sets the amazonOrders.
     * @param amazonOrders the amazonOrders to set
     */
    public void setAmazonOrders(AmazonOrders amazonOrders) {
        this.amazonOrders = amazonOrders;
    }

    /**
     * Returns the orderId.
     * @return the orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Sets the orderId.
     * @param orderId the orderId to set
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * Returns the purchaseDate.
     * @return the purchaseDate
     */
    public String getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * Sets the purchaseDate.
     * @param purchaseDate the purchaseDate to set
     */
    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    /**
     * Returns the paymentsDate.
     * @return the paymentsDate
     */
    public String getPaymentsDate() {
        return paymentsDate;
    }

    /**
     * Sets the paymentsDate.
     * @param paymentsDate the paymentsDate to set
     */
    public void setPaymentsDate(String paymentsDate) {
        this.paymentsDate = paymentsDate;
    }

    /**
     * Returns the buyerName.
     * @return the buyerName
     */
    public String getBuyerName() {
        return buyerName;
    }

    /**
     * Sets the buyerName.
     * @param buyerName the buyerName to set
     */
    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    /**
     * Returns the buyerEmail.
     * @return the buyerEmail
     */
    public String getBuyerEmail() {
        return buyerEmail;
    }

    /**
     * Sets the buyerEmail.
     * @param buyerEmail the buyerEmail to set
     */
    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    /**
     * Returns the amazonOrderShipmentGroups.
     * @return the amazonOrderShipmentGroups
     */
    public Map<String, AmazonOrderShipmentGroup> getAmazonOrderShipmentGroups() {
        if(amazonOrderShipmentGroups == null) {
            amazonOrderShipmentGroups = new HashMap<>();
        }

        return amazonOrderShipmentGroups;
    }

    /**
     * Sets the amazonOrderShipmentGroups.
     * @param amazonOrderShipmentGroups the amazonOrderShipmentGroups to set
     */
    public void setAmazonOrderShipmentGroups(Map<String, AmazonOrderShipmentGroup> amazonOrderShipmentGroups) {
        this.amazonOrderShipmentGroups = amazonOrderShipmentGroups;
    }

    /**
     * Returns the customerName.
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the customerName.
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Returns the partyName.
     * @return the partyName
     */
    public String getPartyName() {
        return partyName;
    }

    /**
     * Sets the partyName.
     * @param partyName the partyName to set
     */
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    /**
     * Returns the orderName.
     * @return the orderName
     */
    public String getOrderName() {
        return orderName;
    }

    /**
     * Sets the orderName.
     * @param orderName the orderName to set
     */
    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

}

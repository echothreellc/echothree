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

import com.echothree.util.common.string.NameResult;
import com.echothree.util.common.string.StringUtils;
import java.util.HashMap;
import java.util.Map;

public class AmazonOrderShipmentGroup
        implements AmazonOrderKey {

    private AmazonOrder order;
    
    private String recipientName;
    private String shipAddress1;
    private String shipAddress2;
    private String shipCity;
    private String shipState;
    private String shipZip;
    private String shipCountry;
    private String shipMethod;

    private Map<String, AmazonOrderLine> amazonOrderLines;

    private NameResult recipientNameResult;
    private String contactMechanismName;

    /** Creates a new instance of AmazonOrderShipmentGroup */
    public AmazonOrderShipmentGroup(AmazonOrder order, Map<String, String> dataFieldMap) {
        this.order = order;
        recipientName = StringUtils.getInstance().trimToNull(dataFieldMap.get(AmazonOrderFields.Field_RecipientName));
        shipAddress1 = StringUtils.getInstance().trimToNull(dataFieldMap.get(AmazonOrderFields.Field_ShipAddress1));
        shipAddress2 = StringUtils.getInstance().trimToNull(dataFieldMap.get(AmazonOrderFields.Field_ShipAddress2));
        shipCity = StringUtils.getInstance().trimToNull(dataFieldMap.get(AmazonOrderFields.Field_ShipCity));
        shipState = StringUtils.getInstance().trimToNull(dataFieldMap.get(AmazonOrderFields.Field_ShipState));
        shipZip = StringUtils.getInstance().trimToNull(dataFieldMap.get(AmazonOrderFields.Field_ShipZip));
        shipCountry = StringUtils.getInstance().trimToNull(dataFieldMap.get(AmazonOrderFields.Field_ShipCountry));
        shipMethod = StringUtils.getInstance().trimToNull(dataFieldMap.get(AmazonOrderFields.Field_ShipMethod));
    }

    @Override
    public String getKey() {
        return new StringBuilder(getRecipientName()).append('~').append(getShipAddress1()).append('~').append(getShipAddress2()).append('~').append(getShipCity()).append('~')
                .append(getShipState()).append('~').append(getShipZip()).append('~').append(getShipCountry()).append('~').append(getShipMethod()).toString();
    }

    /**
     * Returns the order.
     * @return the order
     */
    public AmazonOrder getOrder() {
        return order;
    }

    /**
     * Sets the order.
     * @param order the order to set
     */
    public void setOrder(AmazonOrder order) {
        this.order = order;
    }

    /**
     * Returns the recipientName.
     * @return the recipientName
     */
    public String getRecipientName() {
        return recipientName;
    }

    /**
     * Sets the recipientName.
     * @param recipientName the recipientName to set
     */
    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    /**
     * Returns the shipAddress1.
     * @return the shipAddress1
     */
    public String getShipAddress1() {
        return shipAddress1;
    }

    /**
     * Sets the shipAddress1.
     * @param shipAddress1 the shipAddress1 to set
     */
    public void setShipAddress1(String shipAddress1) {
        this.shipAddress1 = shipAddress1;
    }

    /**
     * Returns the shipAddress2.
     * @return the shipAddress2
     */
    public String getShipAddress2() {
        return shipAddress2;
    }

    /**
     * Sets the shipAddress2.
     * @param shipAddress2 the shipAddress2 to set
     */
    public void setShipAddress2(String shipAddress2) {
        this.shipAddress2 = shipAddress2;
    }

    /**
     * Returns the shipCity.
     * @return the shipCity
     */
    public String getShipCity() {
        return shipCity;
    }

    /**
     * Sets the shipCity.
     * @param shipCity the shipCity to set
     */
    public void setShipCity(String shipCity) {
        this.shipCity = shipCity;
    }

    /**
     * Returns the shipState.
     * @return the shipState
     */
    public String getShipState() {
        return shipState;
    }

    /**
     * Sets the shipState.
     * @param shipState the shipState to set
     */
    public void setShipState(String shipState) {
        this.shipState = shipState;
    }

    /**
     * Returns the shipZip.
     * @return the shipZip
     */
    public String getShipZip() {
        return shipZip;
    }

    /**
     * Sets the shipZip.
     * @param shipZip the shipZip to set
     */
    public void setShipZip(String shipZip) {
        this.shipZip = shipZip;
    }

    /**
     * Returns the shipCountry.
     * @return the shipCountry
     */
    public String getShipCountry() {
        return shipCountry;
    }

    /**
     * Sets the shipCountry.
     * @param shipCountry the shipCountry to set
     */
    public void setShipCountry(String shipCountry) {
        this.shipCountry = shipCountry;
    }

    /**
     * Returns the shipMethod.
     * @return the shipMethod
     */
    public String getShipMethod() {
        return shipMethod;
    }

    /**
     * Sets the shipMethod.
     * @param shipMethod the shipMethod to set
     */
    public void setShipMethod(String shipMethod) {
        this.shipMethod = shipMethod;
    }

    /**
     * Returns the amazonOrderLines.
     * @return the amazonOrderLines
     */
    public Map<String, AmazonOrderLine> getAmazonOrderLines() {
        if(amazonOrderLines == null) {
            amazonOrderLines = new HashMap<>();
        }

        return amazonOrderLines;
    }

    /**
     * Sets the amazonOrderLines.
     * @param amazonOrderLines the amazonOrderLines to set
     */
    public void setAmazonOrderLines(Map<String, AmazonOrderLine> amazonOrderLines) {
        this.amazonOrderLines = amazonOrderLines;
    }

    /**
     * Returns the recipientNameResult.
     * @return the recipientNameResult
     */
    public NameResult getRecipientNameResult() {
        return recipientNameResult;
    }

    /**
     * Sets the recipientNameResult.
     * @param recipientNameResult the recipientNameResult to set
     */
    public void setRecipientNameResult(NameResult recipientNameResult) {
        this.recipientNameResult = recipientNameResult;
    }

    /**
     * Returns the contactMechanismName.
     * @return the contactMechanismName
     */
    public String getContactMechanismName() {
        return contactMechanismName;
    }

    /**
     * Sets the contactMechanismName.
     * @param contactMechanismName the contactMechanismName to set
     */
    public void setContactMechanismName(String contactMechanismName) {
        this.contactMechanismName = contactMechanismName;
    }

}

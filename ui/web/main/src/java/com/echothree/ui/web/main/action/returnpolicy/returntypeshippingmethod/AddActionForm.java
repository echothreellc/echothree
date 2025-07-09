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

package com.echothree.ui.web.main.action.returnpolicy.returntypeshippingmethod;

import com.echothree.control.user.shipping.common.ShippingUtil;
import com.echothree.control.user.shipping.common.result.GetShippingMethodChoicesResult;
import com.echothree.model.control.shipment.common.ShipmentTypes;
import com.echothree.model.control.shipping.common.choice.ShippingMethodChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ReturnTypeShippingMethodAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private ShippingMethodChoicesBean shippingMethodChoices;
    
    private String returnKindName;
    private String returnTypeName;
    private String shippingMethodChoice;
    private Boolean isDefault;
    private String sortOrder;
    
    private void setupShippingMethodChoices()
            throws NamingException {
        if(shippingMethodChoices == null) {
            var form = ShippingUtil.getHome().getGetShippingMethodChoicesForm();

            form.setShipmentTypeName(ShipmentTypes.CUSTOMER_RETURN.name());
            form.setDefaultShippingMethodChoice(shippingMethodChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = ShippingUtil.getHome().getShippingMethodChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getShippingMethodChoicesResult = (GetShippingMethodChoicesResult)executionResult.getResult();
            shippingMethodChoices = getShippingMethodChoicesResult.getShippingMethodChoices();

            if(shippingMethodChoice == null) {
                shippingMethodChoice = shippingMethodChoices.getDefaultValue();
            }
        }
    }
    
    public String getReturnKindName() {
        return returnKindName;
    }
    
    public void setReturnKindName(String returnKindName) {
        this.returnKindName = returnKindName;
    }
    
    public String getReturnTypeName() {
        return returnTypeName;
    }
    
    public void setReturnTypeName(String returnTypeName) {
        this.returnTypeName = returnTypeName;
    }
    
    public String getShippingMethodChoice()
            throws NamingException {
        setupShippingMethodChoices();
        
        return shippingMethodChoice;
    }
    
    public void setShippingMethodChoice(String shippingMethodChoice) {
        this.shippingMethodChoice = shippingMethodChoice;
    }
    
    public List<LabelValueBean> getShippingMethodChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupShippingMethodChoices();
        if(shippingMethodChoices != null) {
            choices = convertChoices(shippingMethodChoices);
        }
        
        return choices;
    }
    
    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public String getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        isDefault = false;
    }
    
}

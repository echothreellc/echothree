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

package com.echothree.ui.web.main.action.filter.filteradjustmentamount;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.result.GetCurrencyChoicesResult;
import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.common.result.GetUnitOfMeasureChoicesResult;
import com.echothree.model.control.accounting.common.choice.CurrencyChoicesBean;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="FilterAdjustmentAmountAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private CurrencyChoicesBean currencyChoices;
    private UnitOfMeasureChoicesBean unitOfMeasureChoices;
    
    private String filterKindName;
    private String filterAdjustmentName;
    private String currencyChoice;
    private String unitOfMeasureChoice;
    private String amount;
    
    private void setupCurrencyChoices()
            throws NamingException {
        if(currencyChoices == null) {
            var form = AccountingUtil.getHome().getGetCurrencyChoicesForm();

            form.setDefaultCurrencyChoice(currencyChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = AccountingUtil.getHome().getCurrencyChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getCurrencyChoicesResult = (GetCurrencyChoicesResult)executionResult.getResult();
            currencyChoices = getCurrencyChoicesResult.getCurrencyChoices();

            if(currencyChoice == null)
                currencyChoice = currencyChoices.getDefaultValue();
        }
    }
    
    private void setupUnitOfMeasureChoices()
            throws NamingException {
        if(unitOfMeasureChoices == null) {
            var form = UomUtil.getHome().getGetUnitOfMeasureChoicesForm();

            form.setDefaultUnitOfMeasureChoice(unitOfMeasureChoice);
            form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_QUANTITY);

            var commandResult = UomUtil.getHome().getUnitOfMeasureChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getUnitOfMeasureChoicesResult = (GetUnitOfMeasureChoicesResult)executionResult.getResult();
            unitOfMeasureChoices = getUnitOfMeasureChoicesResult.getUnitOfMeasureChoices();

            if(unitOfMeasureChoice == null)
                unitOfMeasureChoice = unitOfMeasureChoices.getDefaultValue();
        }
    }
    
    public void setFilterKindName(String filterKindName) {
        this.filterKindName = filterKindName;
    }
    
    public String getFilterKindName() {
        return filterKindName;
    }
    
    public void setFilterAdjustmentName(String filterAdjustmentName) {
        this.filterAdjustmentName = filterAdjustmentName;
    }
    
    public String getFilterAdjustmentName() {
        return filterAdjustmentName;
    }
    
    public List<LabelValueBean> getCurrencyChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupCurrencyChoices();
        if(currencyChoices != null)
            choices = convertChoices(currencyChoices);
        
        return choices;
    }
    
    public void setCurrencyChoice(String currencyChoice) {
        this.currencyChoice = currencyChoice;
    }
    
    public String getCurrencyChoice()
            throws NamingException {
        setupCurrencyChoices();
        return currencyChoice;
    }
    
    public List<LabelValueBean> getUnitOfMeasureChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupUnitOfMeasureChoices();
        if(unitOfMeasureChoices != null)
            choices = convertChoices(unitOfMeasureChoices);
        
        return choices;
    }
    
    public void setUnitOfMeasureChoice(String unitOfMeasureChoice) {
        this.unitOfMeasureChoice = unitOfMeasureChoice;
    }
    
    public String getUnitOfMeasureChoice()
            throws NamingException {
        setupUnitOfMeasureChoices();
        return unitOfMeasureChoice;
    }
    
    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    public String getAmount() {
        return amount;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }
    
}

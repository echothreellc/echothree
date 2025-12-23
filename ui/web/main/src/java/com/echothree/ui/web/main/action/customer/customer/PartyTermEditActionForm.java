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

package com.echothree.ui.web.main.action.customer.customer;

import com.echothree.control.user.term.common.TermUtil;
import com.echothree.control.user.term.common.result.GetTermChoicesResult;
import com.echothree.model.control.term.common.choice.TermChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="PartyTermEdit")
public class PartyTermEditActionForm
        extends BaseActionForm {
    
    private TermChoicesBean termChoices;
    
    private String partyName;
    private String customerName;
    private String termChoice;
    private Boolean taxable;
    
    public void setupTermChoices()
            throws NamingException {
        if(termChoices == null) {
            var form = TermUtil.getHome().getGetTermChoicesForm();

            form.setDefaultTermChoice(termChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = TermUtil.getHome().getTermChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getTermChoicesResult = (GetTermChoicesResult)executionResult.getResult();
            termChoices = getTermChoicesResult.getTermChoices();

            if(termChoice == null)
                termChoice = termChoices.getDefaultValue();
        }
    }
    
    public String getPartyName() {
        return partyName;
    }
    
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public List<LabelValueBean> getTermChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupTermChoices();
        if(termChoices != null)
            choices = convertChoices(termChoices);
        
        return choices;
    }
    
    public void setTermChoice(String termChoice) {
        this.termChoice = termChoice;
    }
    
    public String getTermChoice()
            throws NamingException {
        setupTermChoices();
        return termChoice;
    }
    
    public Boolean getTaxable() {
        return taxable;
    }
    
    public void setTaxable(Boolean taxable) {
        this.taxable = taxable;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        setTaxable(false);
    }
    
}

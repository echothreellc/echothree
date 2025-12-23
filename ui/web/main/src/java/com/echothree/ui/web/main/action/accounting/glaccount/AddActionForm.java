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

package com.echothree.ui.web.main.action.accounting.glaccount;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.result.GetCurrencyChoicesResult;
import com.echothree.control.user.accounting.common.result.GetGlAccountCategoryChoicesResult;
import com.echothree.control.user.accounting.common.result.GetGlAccountChoicesResult;
import com.echothree.control.user.accounting.common.result.GetGlAccountClassChoicesResult;
import com.echothree.control.user.accounting.common.result.GetGlAccountTypeChoicesResult;
import com.echothree.control.user.accounting.common.result.GetGlResourceTypeChoicesResult;
import com.echothree.model.control.accounting.common.choice.CurrencyChoicesBean;
import com.echothree.model.control.accounting.common.choice.GlAccountCategoryChoicesBean;
import com.echothree.model.control.accounting.common.choice.GlAccountChoicesBean;
import com.echothree.model.control.accounting.common.choice.GlAccountClassChoicesBean;
import com.echothree.model.control.accounting.common.choice.GlAccountTypeChoicesBean;
import com.echothree.model.control.accounting.common.choice.GlResourceTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="GlAccountAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private GlAccountChoicesBean parentGlAccountChoices;
    private GlAccountTypeChoicesBean glAccountTypeChoices;
    private GlAccountClassChoicesBean glAccountClassChoices;
    private GlAccountCategoryChoicesBean glAccountCategoryChoices;
    private GlResourceTypeChoicesBean glResourceTypeChoices;
    private CurrencyChoicesBean currencyChoices;
    
    private String glAccountName;
    private String parentGlAccountChoice;
    private String glAccountTypeChoice;
    private String glAccountClassChoice;
    private String glAccountCategoryChoice;
    private String glResourceTypeChoice;
    private String currencyChoice;
    private Boolean isDefault;
    private String description;
    
    private void setupParentGlAccountChoices()
            throws NamingException {
        if(parentGlAccountChoices == null) {
            var form = AccountingUtil.getHome().getGetGlAccountChoicesForm();

            form.setDefaultGlAccountChoice(parentGlAccountChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = AccountingUtil.getHome().getGlAccountChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getGlAccountChoicesResult = (GetGlAccountChoicesResult)executionResult.getResult();
            parentGlAccountChoices = getGlAccountChoicesResult.getGlAccountChoices();

            if(parentGlAccountChoice == null)
                parentGlAccountChoice = parentGlAccountChoices.getDefaultValue();
        }
    }
    
    private void setupGlAccountTypeChoices()
            throws NamingException {
        if(glAccountTypeChoices == null) {
            var form = AccountingUtil.getHome().getGetGlAccountTypeChoicesForm();

            form.setDefaultGlAccountTypeChoice(glAccountTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = AccountingUtil.getHome().getGlAccountTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getGlAccountTypeChoicesResult = (GetGlAccountTypeChoicesResult)executionResult.getResult();
            glAccountTypeChoices = getGlAccountTypeChoicesResult.getGlAccountTypeChoices();

            if(glAccountTypeChoice == null)
                glAccountTypeChoice = glAccountTypeChoices.getDefaultValue();
        }
    }
    
    private void setupGlAccountClassChoices()
            throws NamingException {
        if(glAccountClassChoices == null) {
            var form = AccountingUtil.getHome().getGetGlAccountClassChoicesForm();

            form.setDefaultGlAccountClassChoice(glAccountClassChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = AccountingUtil.getHome().getGlAccountClassChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getGlAccountClassChoicesResult = (GetGlAccountClassChoicesResult)executionResult.getResult();
            glAccountClassChoices = getGlAccountClassChoicesResult.getGlAccountClassChoices();

            if(glAccountClassChoice == null)
                glAccountClassChoice = glAccountClassChoices.getDefaultValue();
        }
    }
    
    private void setupGlAccountCategoryChoices()
            throws NamingException {
        if(glAccountCategoryChoices == null) {
            var form = AccountingUtil.getHome().getGetGlAccountCategoryChoicesForm();

            form.setDefaultGlAccountCategoryChoice(glAccountCategoryChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = AccountingUtil.getHome().getGlAccountCategoryChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getGlAccountCategoryChoicesResult = (GetGlAccountCategoryChoicesResult)executionResult.getResult();
            glAccountCategoryChoices = getGlAccountCategoryChoicesResult.getGlAccountCategoryChoices();

            if(glAccountCategoryChoice == null)
                glAccountCategoryChoice = glAccountCategoryChoices.getDefaultValue();
        }
    }
    
    private void setupGlResourceTypeChoices()
            throws NamingException {
        if(glResourceTypeChoices == null) {
            var form = AccountingUtil.getHome().getGetGlResourceTypeChoicesForm();

            form.setDefaultGlResourceTypeChoice(glResourceTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = AccountingUtil.getHome().getGlResourceTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getGlResourceTypeChoicesResult = (GetGlResourceTypeChoicesResult)executionResult.getResult();
            glResourceTypeChoices = getGlResourceTypeChoicesResult.getGlResourceTypeChoices();

            if(glResourceTypeChoice == null)
                glResourceTypeChoice = glResourceTypeChoices.getDefaultValue();
        }
    }
    
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
    
    public void setGlAccountName(String glAccountName) {
        this.glAccountName = glAccountName;
    }
    
    public String getGlAccountName() {
        return glAccountName;
    }
    
    public String getParentGlAccountChoice() {
        return parentGlAccountChoice;
    }
    
    public void setParentGlAccountChoice(String parentGlAccountChoice) {
        this.parentGlAccountChoice = parentGlAccountChoice;
    }
    
    public List<LabelValueBean> getParentGlAccountChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupParentGlAccountChoices();
        if(parentGlAccountChoices != null)
            choices = convertChoices(parentGlAccountChoices);
        
        return choices;
    }
    
    public String getGlAccountTypeChoice() {
        return glAccountTypeChoice;
    }
    
    public void setGlAccountTypeChoice(String glAccountTypeChoice) {
        this.glAccountTypeChoice = glAccountTypeChoice;
    }
    
    public List<LabelValueBean> getGlAccountTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupGlAccountTypeChoices();
        if(glAccountTypeChoices != null)
            choices = convertChoices(glAccountTypeChoices);
        
        return choices;
    }
    
    public String getGlAccountClassChoice() {
        return glAccountClassChoice;
    }
    
    public void setGlAccountClassChoice(String glAccountClassChoice) {
        this.glAccountClassChoice = glAccountClassChoice;
    }
    
    public List<LabelValueBean> getGlAccountClassChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupGlAccountClassChoices();
        if(glAccountClassChoices != null)
            choices = convertChoices(glAccountClassChoices);
        
        return choices;
    }
    
    public String getGlAccountCategoryChoice() {
        return glAccountCategoryChoice;
    }
    
    public void setGlAccountCategoryChoice(String glAccountCategoryChoice) {
        this.glAccountCategoryChoice = glAccountCategoryChoice;
    }
    
    public List<LabelValueBean> getGlAccountCategoryChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupGlAccountCategoryChoices();
        if(glAccountCategoryChoices != null)
            choices = convertChoices(glAccountCategoryChoices);
        
        return choices;
    }
    
    public String getGlResourceTypeChoice() {
        return glResourceTypeChoice;
    }
    
    public void setGlResourceTypeChoice(String glResourceTypeChoice) {
        this.glResourceTypeChoice = glResourceTypeChoice;
    }
    
    public List<LabelValueBean> getGlResourceTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupGlResourceTypeChoices();
        if(glResourceTypeChoices != null)
            choices = convertChoices(glResourceTypeChoices);
        
        return choices;
    }
    
    public String getCurrencyChoice() {
        return currencyChoice;
    }
    
    public void setCurrencyChoice(String currencyChoice) {
        this.currencyChoice = currencyChoice;
    }
    
    public List<LabelValueBean> getCurrencyChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupCurrencyChoices();
        if(currencyChoices != null)
            choices = convertChoices(currencyChoices);
        
        return choices;
    }
    
    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        isDefault = false;
    }
    
}

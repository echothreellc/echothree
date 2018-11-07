// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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
import com.echothree.control.user.accounting.common.form.GetCurrencyChoicesForm;
import com.echothree.control.user.accounting.common.form.GetGlAccountCategoryChoicesForm;
import com.echothree.control.user.accounting.common.form.GetGlAccountChoicesForm;
import com.echothree.control.user.accounting.common.form.GetGlAccountClassChoicesForm;
import com.echothree.control.user.accounting.common.form.GetGlAccountTypeChoicesForm;
import com.echothree.control.user.accounting.common.form.GetGlResourceTypeChoicesForm;
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
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
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
    
    private void setupParentGlAccountChoices() {
        if(parentGlAccountChoices == null) {
            try {
                GetGlAccountChoicesForm form = AccountingUtil.getHome().getGetGlAccountChoicesForm();
                
                form.setDefaultGlAccountChoice(parentGlAccountChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = AccountingUtil.getHome().getGlAccountChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetGlAccountChoicesResult getGlAccountChoicesResult = (GetGlAccountChoicesResult)executionResult.getResult();
                parentGlAccountChoices = getGlAccountChoicesResult.getGlAccountChoices();
                
                if(parentGlAccountChoice == null)
                    parentGlAccountChoice = parentGlAccountChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, parentGlAccountChoices remains null, no default
            }
        }
    }
    
    private void setupGlAccountTypeChoices() {
        if(glAccountTypeChoices == null) {
            try {
                GetGlAccountTypeChoicesForm form = AccountingUtil.getHome().getGetGlAccountTypeChoicesForm();
                
                form.setDefaultGlAccountTypeChoice(glAccountTypeChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = AccountingUtil.getHome().getGlAccountTypeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetGlAccountTypeChoicesResult getGlAccountTypeChoicesResult = (GetGlAccountTypeChoicesResult)executionResult.getResult();
                glAccountTypeChoices = getGlAccountTypeChoicesResult.getGlAccountTypeChoices();
                
                if(glAccountTypeChoice == null)
                    glAccountTypeChoice = glAccountTypeChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, glAccountTypeChoices remains null, no default
            }
        }
    }
    
    private void setupGlAccountClassChoices() {
        if(glAccountClassChoices == null) {
            try {
                GetGlAccountClassChoicesForm form = AccountingUtil.getHome().getGetGlAccountClassChoicesForm();
                
                form.setDefaultGlAccountClassChoice(glAccountClassChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = AccountingUtil.getHome().getGlAccountClassChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetGlAccountClassChoicesResult getGlAccountClassChoicesResult = (GetGlAccountClassChoicesResult)executionResult.getResult();
                glAccountClassChoices = getGlAccountClassChoicesResult.getGlAccountClassChoices();
                
                if(glAccountClassChoice == null)
                    glAccountClassChoice = glAccountClassChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, glAccountClassChoices remains null, no default
            }
        }
    }
    
    private void setupGlAccountCategoryChoices() {
        if(glAccountCategoryChoices == null) {
            try {
                GetGlAccountCategoryChoicesForm form = AccountingUtil.getHome().getGetGlAccountCategoryChoicesForm();
                
                form.setDefaultGlAccountCategoryChoice(glAccountCategoryChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = AccountingUtil.getHome().getGlAccountCategoryChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetGlAccountCategoryChoicesResult getGlAccountCategoryChoicesResult = (GetGlAccountCategoryChoicesResult)executionResult.getResult();
                glAccountCategoryChoices = getGlAccountCategoryChoicesResult.getGlAccountCategoryChoices();
                
                if(glAccountCategoryChoice == null)
                    glAccountCategoryChoice = glAccountCategoryChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, glAccountCategoryChoices remains null, no default
            }
        }
    }
    
    private void setupGlResourceTypeChoices() {
        if(glResourceTypeChoices == null) {
            try {
                GetGlResourceTypeChoicesForm form = AccountingUtil.getHome().getGetGlResourceTypeChoicesForm();
                
                form.setDefaultGlResourceTypeChoice(glResourceTypeChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = AccountingUtil.getHome().getGlResourceTypeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetGlResourceTypeChoicesResult getGlResourceTypeChoicesResult = (GetGlResourceTypeChoicesResult)executionResult.getResult();
                glResourceTypeChoices = getGlResourceTypeChoicesResult.getGlResourceTypeChoices();
                
                if(glResourceTypeChoice == null)
                    glResourceTypeChoice = glResourceTypeChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, glResourceTypeChoices remains null, no default
            }
        }
    }
    
    private void setupCurrencyChoices() {
        if(currencyChoices == null) {
            try {
                GetCurrencyChoicesForm form = AccountingUtil.getHome().getGetCurrencyChoicesForm();
                
                form.setDefaultCurrencyChoice(currencyChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = AccountingUtil.getHome().getCurrencyChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetCurrencyChoicesResult getCurrencyChoicesResult = (GetCurrencyChoicesResult)executionResult.getResult();
                currencyChoices = getCurrencyChoicesResult.getCurrencyChoices();
                
                if(currencyChoice == null)
                    currencyChoice = currencyChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, currencyChoices remains null, no default
            }
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
    
    public List<LabelValueBean> getParentGlAccountChoices() {
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
    
    public List<LabelValueBean> getGlAccountTypeChoices() {
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
    
    public List<LabelValueBean> getGlAccountClassChoices() {
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
    
    public List<LabelValueBean> getGlAccountCategoryChoices() {
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
    
    public List<LabelValueBean> getGlResourceTypeChoices() {
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
    
    public List<LabelValueBean> getCurrencyChoices() {
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
        
        isDefault = Boolean.FALSE;
    }
    
}

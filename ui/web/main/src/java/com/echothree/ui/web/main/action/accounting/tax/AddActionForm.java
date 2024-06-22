// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.ui.web.main.action.accounting.tax;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.form.GetGlAccountChoicesForm;
import com.echothree.control.user.accounting.common.result.GetGlAccountChoicesResult;
import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.form.GetContactMechanismPurposeChoicesForm;
import com.echothree.control.user.contact.common.result.GetContactMechanismPurposeChoicesResult;
import com.echothree.model.control.accounting.common.choice.GlAccountChoicesBean;
import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.contact.common.choice.ContactMechanismPurposeChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="TaxAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private ContactMechanismPurposeChoicesBean contactMechanismPurposeChoices;
    private GlAccountChoicesBean glAccountChoices;
    
    private String taxName;
    private String contactMechanismPurposeChoice;
    private String glAccountChoice;
    private Boolean includeShippingCharge;
    private Boolean includeProcessingCharge;
    private Boolean includeInsuranceCharge;
    private Boolean isDefault;
    private String sortOrder;
    private String percent;
    private String description;
    
    private void setupContactMechanismPurposeChoices() {
        if(contactMechanismPurposeChoices == null) {
            try {
                GetContactMechanismPurposeChoicesForm form = ContactUtil.getHome().getGetContactMechanismPurposeChoicesForm();
                
                form.setContactMechanismTypeName(ContactMechanismTypes.POSTAL_ADDRESS.name());
                form.setDefaultContactMechanismPurposeChoice(contactMechanismPurposeChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = ContactUtil.getHome().getContactMechanismPurposeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetContactMechanismPurposeChoicesResult getContactMechanismPurposeChoicesResult = (GetContactMechanismPurposeChoicesResult)executionResult.getResult();
                contactMechanismPurposeChoices = getContactMechanismPurposeChoicesResult.getContactMechanismPurposeChoices();
                
                if(contactMechanismPurposeChoice == null) {
                    contactMechanismPurposeChoice = contactMechanismPurposeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, contactMechanismPurposeChoices remains null, no default
            }
        }
    }
    
    private void setupGlAccountChoices() {
        if(glAccountChoices == null) {
            try {
                GetGlAccountChoicesForm form = AccountingUtil.getHome().getGetGlAccountChoicesForm();
                
                form.setDefaultGlAccountChoice(glAccountChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = AccountingUtil.getHome().getGlAccountChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetGlAccountChoicesResult getGlAccountChoicesResult = (GetGlAccountChoicesResult)executionResult.getResult();
                glAccountChoices = getGlAccountChoicesResult.getGlAccountChoices();
                
                if(glAccountChoice == null) {
                    glAccountChoice = glAccountChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, glAccountChoices remains null, no default
            }
        }
    }
    
    public String getTaxName() {
        return taxName;
    }
    
    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }
    
    public String getContactMechanismPurposeChoice() {
        setupContactMechanismPurposeChoices();
        
        return contactMechanismPurposeChoice;
    }
    
    public void setContactMechanismPurposeChoice(String contactMechanismPurposeChoice) {
        this.contactMechanismPurposeChoice = contactMechanismPurposeChoice;
    }
    
    public List<LabelValueBean> getContactMechanismPurposeChoices() {
        List<LabelValueBean> choices = null;
        
        setupContactMechanismPurposeChoices();
        if(contactMechanismPurposeChoices != null)
            choices = convertChoices(contactMechanismPurposeChoices);
        
        return choices;
    }
    
    public String getGlAccountChoice() {
        setupGlAccountChoices();
        
        return glAccountChoice;
    }
    
    public void setGlAccountChoice(String glAccountChoice) {
        this.glAccountChoice = glAccountChoice;
    }
    
    public List<LabelValueBean> getGlAccountChoices() {
        List<LabelValueBean> choices = null;
        
        setupGlAccountChoices();
        if(glAccountChoices != null)
            choices = convertChoices(glAccountChoices);
        
        return choices;
    }
    
    public Boolean getIncludeShippingCharge() {
        return includeShippingCharge;
    }
    
    public void setIncludeShippingCharge(Boolean includeShippingCharge) {
        this.includeShippingCharge = includeShippingCharge;
    }
    
    public Boolean getIncludeProcessingCharge() {
        return includeProcessingCharge;
    }
    
    public void setIncludeProcessingCharge(Boolean includeProcessingCharge) {
        this.includeProcessingCharge = includeProcessingCharge;
    }
    
    public Boolean getIncludeInsuranceCharge() {
        return includeInsuranceCharge;
    }
    
    public void setIncludeInsuranceCharge(Boolean includeInsuranceCharge) {
        this.includeInsuranceCharge = includeInsuranceCharge;
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
    
    public String getPercent() {
        return percent;
    }
    
    public void setPercent(String percent) {
        this.percent = percent;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        includeShippingCharge = Boolean.FALSE;
        includeProcessingCharge = Boolean.FALSE;
        includeInsuranceCharge = Boolean.FALSE;
        isDefault = Boolean.FALSE;
    }
    
}

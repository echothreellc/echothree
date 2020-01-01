// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.ui.web.main.action.customer.customertype;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.form.GetGlAccountChoicesForm;
import com.echothree.control.user.accounting.common.result.GetGlAccountChoicesResult;
import com.echothree.control.user.cancellationpolicy.common.CancellationPolicyUtil;
import com.echothree.control.user.cancellationpolicy.common.form.GetCancellationPolicyChoicesForm;
import com.echothree.control.user.cancellationpolicy.common.result.GetCancellationPolicyChoicesResult;
import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.common.form.GetCustomerCreditStatusChoicesForm;
import com.echothree.control.user.customer.common.form.GetCustomerStatusChoicesForm;
import com.echothree.control.user.customer.common.result.GetCustomerCreditStatusChoicesResult;
import com.echothree.control.user.customer.common.result.GetCustomerStatusChoicesResult;
import com.echothree.control.user.inventory.common.InventoryUtil;
import com.echothree.control.user.inventory.common.form.GetAllocationPriorityChoicesForm;
import com.echothree.control.user.inventory.common.result.GetAllocationPriorityChoicesResult;
import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.offer.common.form.GetSourceChoicesForm;
import com.echothree.control.user.offer.common.result.GetSourceChoicesResult;
import com.echothree.control.user.returnpolicy.common.ReturnPolicyUtil;
import com.echothree.control.user.returnpolicy.common.form.GetReturnPolicyChoicesForm;
import com.echothree.control.user.returnpolicy.common.result.GetReturnPolicyChoicesResult;
import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.common.form.GetSequenceChoicesForm;
import com.echothree.control.user.sequence.common.result.GetSequenceChoicesResult;
import com.echothree.control.user.term.common.TermUtil;
import com.echothree.control.user.term.common.form.GetTermChoicesForm;
import com.echothree.control.user.term.common.result.GetTermChoicesResult;
import com.echothree.model.control.accounting.common.AccountingConstants;
import com.echothree.model.control.accounting.common.choice.GlAccountChoicesBean;
import com.echothree.model.control.cancellationpolicy.common.CancellationPolicyConstants;
import com.echothree.model.control.cancellationpolicy.common.choice.CancellationPolicyChoicesBean;
import com.echothree.model.control.customer.common.choice.CustomerCreditStatusChoicesBean;
import com.echothree.model.control.customer.common.choice.CustomerStatusChoicesBean;
import com.echothree.model.control.inventory.common.choice.AllocationPriorityChoicesBean;
import com.echothree.model.control.offer.common.choice.SourceChoicesBean;
import com.echothree.model.control.returnpolicy.common.ReturnPolicyConstants;
import com.echothree.model.control.returnpolicy.common.choice.ReturnPolicyChoicesBean;
import com.echothree.model.control.sequence.common.SequenceConstants;
import com.echothree.model.control.sequence.common.choice.SequenceChoicesBean;
import com.echothree.model.control.term.common.choice.TermChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="CustomerTypeAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private SequenceChoicesBean customerSequenceChoices;
    private SourceChoicesBean defaultSourceChoices;
    private TermChoicesBean defaultTermChoices;
    private CancellationPolicyChoicesBean defaultCancellationPolicyChoices;
    private ReturnPolicyChoicesBean defaultReturnPolicyChoices;
    private CustomerStatusChoicesBean defaultCustomerStatusChoices;
    private CustomerCreditStatusChoicesBean defaultCustomerCreditStatusChoices;
    private GlAccountChoicesBean defaultArGlAccountChoices;
    private AllocationPriorityChoicesBean allocationPriorityChoices;
    
    private String customerTypeName;
    private String customerSequenceChoice;
    private String defaultSourceChoice;
    private String defaultTermChoice;
    private String defaultCancellationPolicyChoice;
    private String defaultReturnPolicyChoice;
    private String defaultCustomerStatusChoice;
    private String defaultCustomerCreditStatusChoice;
    private String defaultArGlAccountChoice;
    private Boolean defaultHoldUntilComplete;
    private Boolean defaultAllowBackorders;
    private Boolean defaultAllowSubstitutions;
    private Boolean defaultAllowCombiningShipments;
    private Boolean defaultRequireReference;
    private Boolean defaultAllowReferenceDuplicates;
    private String defaultReferenceValidationPattern;
    private Boolean defaultTaxable;
    private String allocationPriorityChoice;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    public void setupCustomerSequenceChoices() {
        if(customerSequenceChoices == null) {
            try {
                GetSequenceChoicesForm form = SequenceUtil.getHome().getGetSequenceChoicesForm();
                
                form.setSequenceTypeName(SequenceConstants.SequenceType_CUSTOMER);
                form.setDefaultSequenceChoice(customerSequenceChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = SequenceUtil.getHome().getSequenceChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetSequenceChoicesResult result = (GetSequenceChoicesResult)executionResult.getResult();
                customerSequenceChoices = result.getSequenceChoices();
                
                if(customerSequenceChoice == null)
                    customerSequenceChoice = customerSequenceChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, customerSequenceChoices remains null, no default
            }
        }
    }
    
    public void setupDefaultSourceChoices() {
        if(defaultSourceChoices == null) {
            try {
                GetSourceChoicesForm form = OfferUtil.getHome().getGetSourceChoicesForm();
                
                form.setDefaultSourceChoice(defaultSourceChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = OfferUtil.getHome().getSourceChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetSourceChoicesResult result = (GetSourceChoicesResult)executionResult.getResult();
                defaultSourceChoices = result.getSourceChoices();
                
                if(defaultSourceChoice == null)
                    defaultSourceChoice = defaultSourceChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, defaultSourceChoices remains null, no default
            }
        }
    }
    
    public void setupDefaultTermChoices() {
        if(defaultTermChoices == null) {
            try {
                GetTermChoicesForm form = TermUtil.getHome().getGetTermChoicesForm();
                
                form.setDefaultTermChoice(defaultTermChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = TermUtil.getHome().getTermChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetTermChoicesResult result = (GetTermChoicesResult)executionResult.getResult();
                defaultTermChoices = result.getTermChoices();
                
                if(defaultTermChoice == null)
                    defaultTermChoice = defaultTermChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, defaultTermChoices remains null, no default
            }
        }
    }
    
    public void setupDefaultCancellationPolicyChoices() {
        if(defaultCancellationPolicyChoices == null) {
            try {
                GetCancellationPolicyChoicesForm form = CancellationPolicyUtil.getHome().getGetCancellationPolicyChoicesForm();
                
                form.setCancellationKindName(CancellationPolicyConstants.CancellationKind_CUSTOMER_CANCELLATION);
                form.setDefaultCancellationPolicyChoice(defaultCancellationPolicyChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = CancellationPolicyUtil.getHome().getCancellationPolicyChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetCancellationPolicyChoicesResult result = (GetCancellationPolicyChoicesResult)executionResult.getResult();
                defaultCancellationPolicyChoices = result.getCancellationPolicyChoices();
                
                if(defaultCancellationPolicyChoice == null)
                    defaultCancellationPolicyChoice = defaultCancellationPolicyChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, defaultCancellationPolicyChoices remains null, no default
            }
        }
    }
    
    public void setupDefaultReturnPolicyChoices() {
        if(defaultReturnPolicyChoices == null) {
            try {
                GetReturnPolicyChoicesForm form = ReturnPolicyUtil.getHome().getGetReturnPolicyChoicesForm();
                
                form.setReturnKindName(ReturnPolicyConstants.ReturnKind_CUSTOMER_RETURN);
                form.setDefaultReturnPolicyChoice(defaultReturnPolicyChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = ReturnPolicyUtil.getHome().getReturnPolicyChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetReturnPolicyChoicesResult result = (GetReturnPolicyChoicesResult)executionResult.getResult();
                defaultReturnPolicyChoices = result.getReturnPolicyChoices();
                
                if(defaultReturnPolicyChoice == null)
                    defaultReturnPolicyChoice = defaultReturnPolicyChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, defaultReturnPolicyChoices remains null, no default
            }
        }
    }
    
    public void setupDefaultCustomerStatusChoices() {
        if(defaultCustomerStatusChoices == null) {
            try {
                GetCustomerStatusChoicesForm form = CustomerUtil.getHome().getGetCustomerStatusChoicesForm();
                
                form.setDefaultCustomerStatusChoice(defaultCustomerStatusChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = CustomerUtil.getHome().getCustomerStatusChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetCustomerStatusChoicesResult result = (GetCustomerStatusChoicesResult)executionResult.getResult();
                defaultCustomerStatusChoices = result.getCustomerStatusChoices();
                
                if(defaultCustomerStatusChoice == null)
                    defaultCustomerStatusChoice = defaultCustomerStatusChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, defaultCustomerStatusChoices remains null, no default
            }
        }
    }
    
    public void setupDefaultCustomerCreditStatusChoices() {
        if(defaultCustomerCreditStatusChoices == null) {
            try {
                GetCustomerCreditStatusChoicesForm form = CustomerUtil.getHome().getGetCustomerCreditStatusChoicesForm();
                
                form.setDefaultCustomerCreditStatusChoice(defaultCustomerCreditStatusChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = CustomerUtil.getHome().getCustomerCreditStatusChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetCustomerCreditStatusChoicesResult result = (GetCustomerCreditStatusChoicesResult)executionResult.getResult();
                defaultCustomerCreditStatusChoices = result.getCustomerCreditStatusChoices();
                
                if(defaultCustomerCreditStatusChoice == null)
                    defaultCustomerCreditStatusChoice = defaultCustomerCreditStatusChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, defaultCustomerCreditStatusChoices remains null, no default
            }
        }
    }
    
    private void setupDefaultArGlAccountChoices() {
        if(defaultArGlAccountChoices == null) {
            try {
                GetGlAccountChoicesForm form = AccountingUtil.getHome().getGetGlAccountChoicesForm();
                
                form.setGlAccountCategoryName(AccountingConstants.GlAccountCategory_ACCOUNTS_RECEIVABLE);
                form.setDefaultGlAccountChoice(defaultArGlAccountChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = AccountingUtil.getHome().getGlAccountChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetGlAccountChoicesResult getGlAccountChoicesResult = (GetGlAccountChoicesResult)executionResult.getResult();
                defaultArGlAccountChoices = getGlAccountChoicesResult.getGlAccountChoices();
                
                if(defaultArGlAccountChoice == null) {
                    defaultArGlAccountChoice = defaultArGlAccountChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, defaultArGlAccountChoices remains null, no default
            }
        }
    }
    
    private void setupAllocationPriorityChoices() {
        if(allocationPriorityChoices == null) {
            try {
                GetAllocationPriorityChoicesForm form = InventoryUtil.getHome().getGetAllocationPriorityChoicesForm();
                
                form.setDefaultAllocationPriorityChoice(allocationPriorityChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = InventoryUtil.getHome().getAllocationPriorityChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetAllocationPriorityChoicesResult getAllocationPriorityChoicesResult = (GetAllocationPriorityChoicesResult)executionResult.getResult();
                allocationPriorityChoices = getAllocationPriorityChoicesResult.getAllocationPriorityChoices();
                
                if(allocationPriorityChoice == null) {
                    allocationPriorityChoice = allocationPriorityChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, defaultAllocationPriorityChoices remains null, no default
            }
        }
    }
    
    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
    }
    
    public String getCustomerTypeName() {
        return customerTypeName;
    }
    
    public List<LabelValueBean> getCustomerSequenceChoices() {
        List<LabelValueBean> choices = null;
        
        setupCustomerSequenceChoices();
        if(customerSequenceChoices != null)
            choices = convertChoices(customerSequenceChoices);
        
        return choices;
    }
    
    public void setCustomerSequenceChoice(String customerSequenceChoice) {
        this.customerSequenceChoice = customerSequenceChoice;
    }
    
    public String getCustomerSequenceChoice() {
        setupCustomerSequenceChoices();
        
        return customerSequenceChoice;
    }
    
    public List<LabelValueBean> getDefaultSourceChoices() {
        List<LabelValueBean> choices = null;
        
        setupDefaultSourceChoices();
        if(defaultSourceChoices != null)
            choices = convertChoices(defaultSourceChoices);
        
        return choices;
    }
    
    public void setDefaultSourceChoice(String defaultSourceChoice) {
        this.defaultSourceChoice = defaultSourceChoice;
    }
    
    public String getDefaultSourceChoice() {
        setupDefaultSourceChoices();
        
        return defaultSourceChoice;
    }
    
    public List<LabelValueBean> getDefaultTermChoices() {
        List<LabelValueBean> choices = null;
        
        setupDefaultTermChoices();
        if(defaultTermChoices != null)
            choices = convertChoices(defaultTermChoices);
        
        return choices;
    }
    
    public void setDefaultTermChoice(String defaultTermChoice) {
        this.defaultTermChoice = defaultTermChoice;
    }
    
    public String getDefaultTermChoice() {
        setupDefaultTermChoices();
        
        return defaultTermChoice;
    }
    
    public List<LabelValueBean> getDefaultCancellationPolicyChoices() {
        List<LabelValueBean> choices = null;
        
        setupDefaultCancellationPolicyChoices();
        if(defaultCancellationPolicyChoices != null)
            choices = convertChoices(defaultCancellationPolicyChoices);
        
        return choices;
    }
    
    public void setDefaultCancellationPolicyChoice(String defaultCancellationPolicyChoice) {
        this.defaultCancellationPolicyChoice = defaultCancellationPolicyChoice;
    }
    
    public String getDefaultCancellationPolicyChoice() {
        setupDefaultCancellationPolicyChoices();
        
        return defaultCancellationPolicyChoice;
    }
    
    public List<LabelValueBean> getDefaultReturnPolicyChoices() {
        List<LabelValueBean> choices = null;
        
        setupDefaultReturnPolicyChoices();
        if(defaultReturnPolicyChoices != null)
            choices = convertChoices(defaultReturnPolicyChoices);
        
        return choices;
    }
    
    public void setDefaultReturnPolicyChoice(String defaultReturnPolicyChoice) {
        this.defaultReturnPolicyChoice = defaultReturnPolicyChoice;
    }
    
    public String getDefaultReturnPolicyChoice() {
        setupDefaultReturnPolicyChoices();
        
        return defaultReturnPolicyChoice;
    }
    
    public String getDefaultCustomerStatusChoice() {
        return defaultCustomerStatusChoice;
    }
    
    public void setDefaultCustomerStatusChoice(String defaultCustomerStatusChoice) {
        this.defaultCustomerStatusChoice = defaultCustomerStatusChoice;
    }
    
    public List<LabelValueBean> getDefaultCustomerStatusChoices() {
        List<LabelValueBean> choices = null;
        
        setupDefaultCustomerStatusChoices();
        if(defaultCustomerStatusChoices != null)
            choices = convertChoices(defaultCustomerStatusChoices);
        
        return choices;
    }
    
    public String getDefaultCustomerCreditStatusChoice() {
        return defaultCustomerCreditStatusChoice;
    }
    
    public void setDefaultCustomerCreditStatusChoice(String defaultCustomerCreditStatusChoice) {
        this.defaultCustomerCreditStatusChoice = defaultCustomerCreditStatusChoice;
    }
    
    public List<LabelValueBean> getDefaultCustomerCreditStatusChoices() {
        List<LabelValueBean> choices = null;
        
        setupDefaultCustomerCreditStatusChoices();
        if(defaultCustomerCreditStatusChoices != null)
            choices = convertChoices(defaultCustomerCreditStatusChoices);
        
        return choices;
    }
    
    public String getDefaultArGlAccountChoice() {
        setupDefaultArGlAccountChoices();
        
        return defaultArGlAccountChoice;
    }
    
    public void setDefaultArGlAccountChoice(String defaultArGlAccountChoice) {
        this.defaultArGlAccountChoice = defaultArGlAccountChoice;
    }
    
    public List<LabelValueBean> getDefaultArGlAccountChoices() {
        List<LabelValueBean> choices = null;
        
        setupDefaultArGlAccountChoices();
        if(defaultArGlAccountChoices != null)
            choices = convertChoices(defaultArGlAccountChoices);
        
        return choices;
    }
    
    public Boolean getDefaultHoldUntilComplete() {
        return defaultHoldUntilComplete;
    }

    public void setDefaultHoldUntilComplete(Boolean defaultHoldUntilComplete) {
        this.defaultHoldUntilComplete = defaultHoldUntilComplete;
    }

    public Boolean getDefaultAllowBackorders() {
        return defaultAllowBackorders;
    }

    public void setDefaultAllowBackorders(Boolean defaultAllowBackorders) {
        this.defaultAllowBackorders = defaultAllowBackorders;
    }

    public Boolean getDefaultAllowSubstitutions() {
        return defaultAllowSubstitutions;
    }

    public void setDefaultAllowSubstitutions(Boolean defaultAllowSubstitutions) {
        this.defaultAllowSubstitutions = defaultAllowSubstitutions;
    }

    public Boolean getDefaultAllowCombiningShipments() {
        return defaultAllowCombiningShipments;
    }

    public void setDefaultAllowCombiningShipments(Boolean defaultAllowCombiningShipments) {
        this.defaultAllowCombiningShipments = defaultAllowCombiningShipments;
    }

    public Boolean getDefaultRequireReference() {
        return defaultRequireReference;
    }

    public void setDefaultRequireReference(Boolean defaultRequireReference) {
        this.defaultRequireReference = defaultRequireReference;
    }

    public Boolean getDefaultAllowReferenceDuplicates() {
        return defaultAllowReferenceDuplicates;
    }

    public void setDefaultAllowReferenceDuplicates(Boolean defaultAllowReferenceDuplicates) {
        this.defaultAllowReferenceDuplicates = defaultAllowReferenceDuplicates;
    }
    
    public String getDefaultReferenceValidationPattern() {
        return defaultReferenceValidationPattern;
    }

    public void setDefaultReferenceValidationPattern(String defaultReferenceValidationPattern) {
        this.defaultReferenceValidationPattern = defaultReferenceValidationPattern;
    }

    public Boolean getDefaultTaxable() {
        return defaultTaxable;
    }

    public void setDefaultTaxable(Boolean defaultTaxable) {
        this.defaultTaxable = defaultTaxable;
    }

    public List<LabelValueBean> getAllocationPriorityChoices() {
        List<LabelValueBean> choices = null;
        
        setupAllocationPriorityChoices();
        if(allocationPriorityChoices != null)
            choices = convertChoices(allocationPriorityChoices);
        
        return choices;
    }
    
    public void setAllocationPriorityChoice(String allocationPriorityChoice) {
        this.allocationPriorityChoice = allocationPriorityChoice;
    }
    
    public String getAllocationPriorityChoice() {
        setupAllocationPriorityChoices();
        
        return allocationPriorityChoice;
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
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        defaultHoldUntilComplete = Boolean.FALSE;
        defaultAllowBackorders = Boolean.FALSE;
        defaultAllowSubstitutions = Boolean.FALSE;
        defaultAllowCombiningShipments = Boolean.FALSE;
        defaultRequireReference = Boolean.FALSE;
        defaultAllowReferenceDuplicates = Boolean.FALSE;
        defaultTaxable = Boolean.FALSE;
        isDefault = Boolean.FALSE;
    }

}

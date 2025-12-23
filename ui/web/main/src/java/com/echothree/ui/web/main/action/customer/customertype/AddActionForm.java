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

package com.echothree.ui.web.main.action.customer.customertype;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.result.GetGlAccountChoicesResult;
import com.echothree.control.user.cancellationpolicy.common.CancellationPolicyUtil;
import com.echothree.control.user.cancellationpolicy.common.result.GetCancellationPolicyChoicesResult;
import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.common.result.GetCustomerCreditStatusChoicesResult;
import com.echothree.control.user.customer.common.result.GetCustomerStatusChoicesResult;
import com.echothree.control.user.inventory.common.InventoryUtil;
import com.echothree.control.user.inventory.common.result.GetAllocationPriorityChoicesResult;
import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.offer.common.result.GetSourceChoicesResult;
import com.echothree.control.user.returnpolicy.common.ReturnPolicyUtil;
import com.echothree.control.user.returnpolicy.common.result.GetReturnPolicyChoicesResult;
import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.common.result.GetSequenceChoicesResult;
import com.echothree.control.user.shipment.common.ShipmentUtil;
import com.echothree.control.user.shipment.common.result.GetFreeOnBoardChoicesResult;
import com.echothree.control.user.term.common.TermUtil;
import com.echothree.control.user.term.common.result.GetTermChoicesResult;
import com.echothree.model.control.accounting.common.AccountingConstants;
import com.echothree.model.control.accounting.common.choice.GlAccountChoicesBean;
import com.echothree.model.control.cancellationpolicy.common.CancellationKinds;
import com.echothree.model.control.cancellationpolicy.common.choice.CancellationPolicyChoicesBean;
import com.echothree.model.control.customer.common.choice.CustomerCreditStatusChoicesBean;
import com.echothree.model.control.customer.common.choice.CustomerStatusChoicesBean;
import com.echothree.model.control.inventory.common.choice.AllocationPriorityChoicesBean;
import com.echothree.model.control.offer.common.choice.SourceChoicesBean;
import com.echothree.model.control.returnpolicy.common.ReturnKinds;
import com.echothree.model.control.returnpolicy.common.choice.ReturnPolicyChoicesBean;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.common.choice.SequenceChoicesBean;
import com.echothree.model.control.shipment.common.choice.FreeOnBoardChoicesBean;
import com.echothree.model.control.term.common.choice.TermChoicesBean;
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
    private FreeOnBoardChoicesBean defaultFreeOnBoardChoices;
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
    private String defaultFreeOnBoardChoice;
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
    
    public void setupCustomerSequenceChoices()
            throws NamingException {
        if(customerSequenceChoices == null) {
            var form = SequenceUtil.getHome().getGetSequenceChoicesForm();

            form.setSequenceTypeName(SequenceTypes.CUSTOMER.name());
            form.setDefaultSequenceChoice(customerSequenceChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = SequenceUtil.getHome().getSequenceChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetSequenceChoicesResult)executionResult.getResult();
            customerSequenceChoices = result.getSequenceChoices();

            if(customerSequenceChoice == null)
                customerSequenceChoice = customerSequenceChoices.getDefaultValue();
        }
    }
    
    public void setupDefaultSourceChoices()
            throws NamingException {
        if(defaultSourceChoices == null) {
            var form = OfferUtil.getHome().getGetSourceChoicesForm();

            form.setDefaultSourceChoice(defaultSourceChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = OfferUtil.getHome().getSourceChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetSourceChoicesResult)executionResult.getResult();
            defaultSourceChoices = result.getSourceChoices();

            if(defaultSourceChoice == null)
                defaultSourceChoice = defaultSourceChoices.getDefaultValue();
        }
    }

    public void setupDefaultTermChoices()
            throws NamingException {
        if(defaultTermChoices == null) {
            var form = TermUtil.getHome().getGetTermChoicesForm();

            form.setDefaultTermChoice(defaultTermChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = TermUtil.getHome().getTermChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetTermChoicesResult)executionResult.getResult();
            defaultTermChoices = result.getTermChoices();

            if(defaultTermChoice == null)
                defaultTermChoice = defaultTermChoices.getDefaultValue();
        }
    }

    public void setupDefaultFreeOnBoardChoices()
            throws NamingException {
        if(defaultFreeOnBoardChoices == null) {
            var form = ShipmentUtil.getHome().getGetFreeOnBoardChoicesForm();

            form.setDefaultFreeOnBoardChoice(defaultFreeOnBoardChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = ShipmentUtil.getHome().getFreeOnBoardChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetFreeOnBoardChoicesResult)executionResult.getResult();
            defaultFreeOnBoardChoices = result.getFreeOnBoardChoices();

            if(defaultFreeOnBoardChoice == null)
                defaultFreeOnBoardChoice = defaultFreeOnBoardChoices.getDefaultValue();
        }
    }

    public void setupDefaultCancellationPolicyChoices()
            throws NamingException {
        if(defaultCancellationPolicyChoices == null) {
            var form = CancellationPolicyUtil.getHome().getGetCancellationPolicyChoicesForm();

            form.setCancellationKindName(CancellationKinds.CUSTOMER_CANCELLATION.name());
            form.setDefaultCancellationPolicyChoice(defaultCancellationPolicyChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = CancellationPolicyUtil.getHome().getCancellationPolicyChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetCancellationPolicyChoicesResult)executionResult.getResult();
            defaultCancellationPolicyChoices = result.getCancellationPolicyChoices();

            if(defaultCancellationPolicyChoice == null)
                defaultCancellationPolicyChoice = defaultCancellationPolicyChoices.getDefaultValue();
        }
    }
    
    public void setupDefaultReturnPolicyChoices()
            throws NamingException {
        if(defaultReturnPolicyChoices == null) {
            var form = ReturnPolicyUtil.getHome().getGetReturnPolicyChoicesForm();

            form.setReturnKindName(ReturnKinds.CUSTOMER_RETURN.name());
            form.setDefaultReturnPolicyChoice(defaultReturnPolicyChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = ReturnPolicyUtil.getHome().getReturnPolicyChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetReturnPolicyChoicesResult)executionResult.getResult();
            defaultReturnPolicyChoices = result.getReturnPolicyChoices();

            if(defaultReturnPolicyChoice == null)
                defaultReturnPolicyChoice = defaultReturnPolicyChoices.getDefaultValue();
        }
    }
    
    public void setupDefaultCustomerStatusChoices()
            throws NamingException {
        if(defaultCustomerStatusChoices == null) {
            var form = CustomerUtil.getHome().getGetCustomerStatusChoicesForm();

            form.setDefaultCustomerStatusChoice(defaultCustomerStatusChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = CustomerUtil.getHome().getCustomerStatusChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetCustomerStatusChoicesResult)executionResult.getResult();
            defaultCustomerStatusChoices = result.getCustomerStatusChoices();

            if(defaultCustomerStatusChoice == null)
                defaultCustomerStatusChoice = defaultCustomerStatusChoices.getDefaultValue();
        }
    }
    
    public void setupDefaultCustomerCreditStatusChoices()
            throws NamingException {
        if(defaultCustomerCreditStatusChoices == null) {
            var form = CustomerUtil.getHome().getGetCustomerCreditStatusChoicesForm();

            form.setDefaultCustomerCreditStatusChoice(defaultCustomerCreditStatusChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = CustomerUtil.getHome().getCustomerCreditStatusChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetCustomerCreditStatusChoicesResult)executionResult.getResult();
            defaultCustomerCreditStatusChoices = result.getCustomerCreditStatusChoices();

            if(defaultCustomerCreditStatusChoice == null)
                defaultCustomerCreditStatusChoice = defaultCustomerCreditStatusChoices.getDefaultValue();
        }
    }
    
    private void setupDefaultArGlAccountChoices()
            throws NamingException {
        if(defaultArGlAccountChoices == null) {
            var form = AccountingUtil.getHome().getGetGlAccountChoicesForm();

            form.setGlAccountCategoryName(AccountingConstants.GlAccountCategory_ACCOUNTS_RECEIVABLE);
            form.setDefaultGlAccountChoice(defaultArGlAccountChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = AccountingUtil.getHome().getGlAccountChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getGlAccountChoicesResult = (GetGlAccountChoicesResult)executionResult.getResult();
            defaultArGlAccountChoices = getGlAccountChoicesResult.getGlAccountChoices();

            if(defaultArGlAccountChoice == null) {
                defaultArGlAccountChoice = defaultArGlAccountChoices.getDefaultValue();
            }
        }
    }
    
    private void setupAllocationPriorityChoices()
            throws NamingException {
        if(allocationPriorityChoices == null) {
            var form = InventoryUtil.getHome().getGetAllocationPriorityChoicesForm();

            form.setDefaultAllocationPriorityChoice(allocationPriorityChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = InventoryUtil.getHome().getAllocationPriorityChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getAllocationPriorityChoicesResult = (GetAllocationPriorityChoicesResult)executionResult.getResult();
            allocationPriorityChoices = getAllocationPriorityChoicesResult.getAllocationPriorityChoices();

            if(allocationPriorityChoice == null) {
                allocationPriorityChoice = allocationPriorityChoices.getDefaultValue();
            }
        }
    }
    
    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
    }
    
    public String getCustomerTypeName() {
        return customerTypeName;
    }
    
    public List<LabelValueBean> getCustomerSequenceChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupCustomerSequenceChoices();
        if(customerSequenceChoices != null)
            choices = convertChoices(customerSequenceChoices);
        
        return choices;
    }
    
    public void setCustomerSequenceChoice(String customerSequenceChoice) {
        this.customerSequenceChoice = customerSequenceChoice;
    }
    
    public String getCustomerSequenceChoice()
            throws NamingException {
        setupCustomerSequenceChoices();
        
        return customerSequenceChoice;
    }
    
    public List<LabelValueBean> getDefaultSourceChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupDefaultSourceChoices();
        if(defaultSourceChoices != null)
            choices = convertChoices(defaultSourceChoices);
        
        return choices;
    }
    
    public void setDefaultSourceChoice(String defaultSourceChoice) {
        this.defaultSourceChoice = defaultSourceChoice;
    }
    
    public String getDefaultSourceChoice()
            throws NamingException {
        setupDefaultSourceChoices();
        
        return defaultSourceChoice;
    }

    public List<LabelValueBean> getDefaultTermChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupDefaultTermChoices();
        if(defaultTermChoices != null)
            choices = convertChoices(defaultTermChoices);

        return choices;
    }

    public void setDefaultTermChoice(String defaultTermChoice) {
        this.defaultTermChoice = defaultTermChoice;
    }

    public String getDefaultTermChoice()
            throws NamingException {
        setupDefaultTermChoices();

        return defaultTermChoice;
    }

    public List<LabelValueBean> getDefaultFreeOnBoardChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupDefaultFreeOnBoardChoices();
        if(defaultFreeOnBoardChoices != null)
            choices = convertChoices(defaultFreeOnBoardChoices);

        return choices;
    }

    public void setDefaultFreeOnBoardChoice(String defaultFreeOnBoardChoice) {
        this.defaultFreeOnBoardChoice = defaultFreeOnBoardChoice;
    }

    public String getDefaultFreeOnBoardChoice()
            throws NamingException {
        setupDefaultFreeOnBoardChoices();

        return defaultFreeOnBoardChoice;
    }

    public List<LabelValueBean> getDefaultCancellationPolicyChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupDefaultCancellationPolicyChoices();
        if(defaultCancellationPolicyChoices != null)
            choices = convertChoices(defaultCancellationPolicyChoices);
        
        return choices;
    }
    
    public void setDefaultCancellationPolicyChoice(String defaultCancellationPolicyChoice) {
        this.defaultCancellationPolicyChoice = defaultCancellationPolicyChoice;
    }
    
    public String getDefaultCancellationPolicyChoice()
            throws NamingException {
        setupDefaultCancellationPolicyChoices();
        
        return defaultCancellationPolicyChoice;
    }
    
    public List<LabelValueBean> getDefaultReturnPolicyChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupDefaultReturnPolicyChoices();
        if(defaultReturnPolicyChoices != null)
            choices = convertChoices(defaultReturnPolicyChoices);
        
        return choices;
    }
    
    public void setDefaultReturnPolicyChoice(String defaultReturnPolicyChoice) {
        this.defaultReturnPolicyChoice = defaultReturnPolicyChoice;
    }
    
    public String getDefaultReturnPolicyChoice()
            throws NamingException {
        setupDefaultReturnPolicyChoices();
        
        return defaultReturnPolicyChoice;
    }
    
    public String getDefaultCustomerStatusChoice() {
        return defaultCustomerStatusChoice;
    }
    
    public void setDefaultCustomerStatusChoice(String defaultCustomerStatusChoice) {
        this.defaultCustomerStatusChoice = defaultCustomerStatusChoice;
    }
    
    public List<LabelValueBean> getDefaultCustomerStatusChoices()
            throws NamingException {
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
    
    public List<LabelValueBean> getDefaultCustomerCreditStatusChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupDefaultCustomerCreditStatusChoices();
        if(defaultCustomerCreditStatusChoices != null)
            choices = convertChoices(defaultCustomerCreditStatusChoices);
        
        return choices;
    }
    
    public String getDefaultArGlAccountChoice()
            throws NamingException {
        setupDefaultArGlAccountChoices();
        
        return defaultArGlAccountChoice;
    }
    
    public void setDefaultArGlAccountChoice(String defaultArGlAccountChoice) {
        this.defaultArGlAccountChoice = defaultArGlAccountChoice;
    }
    
    public List<LabelValueBean> getDefaultArGlAccountChoices()
            throws NamingException {
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

    public List<LabelValueBean> getAllocationPriorityChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupAllocationPriorityChoices();
        if(allocationPriorityChoices != null)
            choices = convertChoices(allocationPriorityChoices);
        
        return choices;
    }
    
    public void setAllocationPriorityChoice(String allocationPriorityChoice) {
        this.allocationPriorityChoice = allocationPriorityChoice;
    }
    
    public String getAllocationPriorityChoice()
            throws NamingException {
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
        
        defaultHoldUntilComplete = false;
        defaultAllowBackorders = false;
        defaultAllowSubstitutions = false;
        defaultAllowCombiningShipments = false;
        defaultRequireReference = false;
        defaultAllowReferenceDuplicates = false;
        defaultTaxable = false;
        isDefault = false;
    }

}

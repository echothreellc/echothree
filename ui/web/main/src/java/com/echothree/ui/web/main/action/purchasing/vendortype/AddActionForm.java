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

package com.echothree.ui.web.main.action.purchasing.vendortype;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.remote.form.GetGlAccountChoicesForm;
import com.echothree.control.user.accounting.remote.result.GetGlAccountChoicesResult;
import com.echothree.control.user.cancellationpolicy.common.CancellationPolicyUtil;
import com.echothree.control.user.cancellationpolicy.remote.form.GetCancellationPolicyChoicesForm;
import com.echothree.control.user.cancellationpolicy.remote.result.GetCancellationPolicyChoicesResult;
import com.echothree.control.user.returnpolicy.common.ReturnPolicyUtil;
import com.echothree.control.user.returnpolicy.remote.form.GetReturnPolicyChoicesForm;
import com.echothree.control.user.returnpolicy.remote.result.GetReturnPolicyChoicesResult;
import com.echothree.model.control.accounting.common.AccountingConstants;
import com.echothree.model.control.accounting.remote.choice.GlAccountChoicesBean;
import com.echothree.model.control.cancellationpolicy.common.CancellationPolicyConstants;
import com.echothree.model.control.cancellationpolicy.remote.choice.CancellationPolicyChoicesBean;
import com.echothree.model.control.returnpolicy.common.ReturnPolicyConstants;
import com.echothree.model.control.returnpolicy.remote.choice.ReturnPolicyChoicesBean;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="VendorTypeAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private CancellationPolicyChoicesBean defaultCancellationPolicyChoices;
    private ReturnPolicyChoicesBean defaultReturnPolicyChoices;
    private GlAccountChoicesBean defaultApGlAccountChoices;
    
    private String vendorTypeName;
    private String defaultCancellationPolicyChoice;
    private String defaultReturnPolicyChoice;
    private String defaultApGlAccountChoice;
    private Boolean defaultHoldUntilComplete;
    private Boolean defaultAllowBackorders;
    private Boolean defaultAllowSubstitutions;
    private Boolean defaultAllowCombiningShipments;
    private Boolean defaultRequireReference;
    private Boolean defaultAllowReferenceDuplicates;
    private String defaultReferenceValidationPattern;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    public void setupDefaultCancellationPolicyChoices() {
        if(defaultCancellationPolicyChoices == null) {
            try {
                GetCancellationPolicyChoicesForm form = CancellationPolicyUtil.getHome().getGetCancellationPolicyChoicesForm();

                form.setCancellationKindName(CancellationPolicyConstants.CancellationKind_VENDOR_CANCELLATION);
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

    private void setupDefaultApGlAccountChoices() {
        if(defaultApGlAccountChoices == null) {
            try {
                GetGlAccountChoicesForm form = AccountingUtil.getHome().getGetGlAccountChoicesForm();
                
                form.setGlAccountCategoryName(AccountingConstants.GlAccountCategory_ACCOUNTS_PAYABLE);
                form.setDefaultGlAccountChoice(defaultApGlAccountChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = AccountingUtil.getHome().getGlAccountChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetGlAccountChoicesResult getGlAccountChoicesResult = (GetGlAccountChoicesResult)executionResult.getResult();
                defaultApGlAccountChoices = getGlAccountChoicesResult.getGlAccountChoices();
                
                if(defaultApGlAccountChoice == null) {
                    defaultApGlAccountChoice = defaultApGlAccountChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, defaultApGlAccountChoices remains null, no default
            }
        }
    }
    
    public void setVendorTypeName(String vendorTypeName) {
        this.vendorTypeName = vendorTypeName;
    }
    
    public String getVendorTypeName() {
        return vendorTypeName;
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

    public String getDefaultApGlAccountChoice() {
        setupDefaultApGlAccountChoices();
        
        return defaultApGlAccountChoice;
    }
    
    public void setDefaultApGlAccountChoice(String defaultApGlAccountChoice) {
        this.defaultApGlAccountChoice = defaultApGlAccountChoice;
    }
    
    public List<LabelValueBean> getDefaultApGlAccountChoices() {
        List<LabelValueBean> choices = null;
        
        setupDefaultApGlAccountChoices();
        if(defaultApGlAccountChoices != null)
            choices = convertChoices(defaultApGlAccountChoices);
        
        return choices;
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
        isDefault = Boolean.FALSE;
    }
    
}

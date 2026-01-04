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

package com.echothree.ui.web.main.action.purchasing.vendortype;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.result.GetGlAccountChoicesResult;
import com.echothree.control.user.cancellationpolicy.common.CancellationPolicyUtil;
import com.echothree.control.user.cancellationpolicy.common.result.GetCancellationPolicyChoicesResult;
import com.echothree.control.user.returnpolicy.common.ReturnPolicyUtil;
import com.echothree.control.user.returnpolicy.common.result.GetReturnPolicyChoicesResult;
import com.echothree.control.user.shipment.common.ShipmentUtil;
import com.echothree.control.user.shipment.common.result.GetFreeOnBoardChoicesResult;
import com.echothree.control.user.term.common.TermUtil;
import com.echothree.control.user.term.common.result.GetTermChoicesResult;
import com.echothree.model.control.accounting.common.AccountingConstants;
import com.echothree.model.control.accounting.common.choice.GlAccountChoicesBean;
import com.echothree.model.control.cancellationpolicy.common.CancellationKinds;
import com.echothree.model.control.cancellationpolicy.common.choice.CancellationPolicyChoicesBean;
import com.echothree.model.control.returnpolicy.common.ReturnKinds;
import com.echothree.model.control.returnpolicy.common.choice.ReturnPolicyChoicesBean;
import com.echothree.model.control.shipment.common.choice.FreeOnBoardChoicesBean;
import com.echothree.model.control.term.common.choice.TermChoicesBean;
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

    private TermChoicesBean defaultTermChoices;
    private FreeOnBoardChoicesBean defaultFreeOnBoardChoices;
    private CancellationPolicyChoicesBean defaultCancellationPolicyChoices;
    private ReturnPolicyChoicesBean defaultReturnPolicyChoices;
    private GlAccountChoicesBean defaultApGlAccountChoices;
    
    private String vendorTypeName;
    private String defaultTermChoice;
    private String defaultFreeOnBoardChoice;
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

            form.setCancellationKindName(CancellationKinds.VENDOR_CANCELLATION.name());
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

    private void setupDefaultApGlAccountChoices()
            throws NamingException {
        if(defaultApGlAccountChoices == null) {
            var form = AccountingUtil.getHome().getGetGlAccountChoicesForm();
            
            form.setGlAccountCategoryName(AccountingConstants.GlAccountCategory_ACCOUNTS_PAYABLE);
            form.setDefaultGlAccountChoice(defaultApGlAccountChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = AccountingUtil.getHome().getGlAccountChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getGlAccountChoicesResult = (GetGlAccountChoicesResult)executionResult.getResult();
            defaultApGlAccountChoices = getGlAccountChoicesResult.getGlAccountChoices();
            
            if(defaultApGlAccountChoice == null) {
                defaultApGlAccountChoice = defaultApGlAccountChoices.getDefaultValue();
            }
        }
    }
    
    public void setVendorTypeName(String vendorTypeName) {
        this.vendorTypeName = vendorTypeName;
    }
    
    public String getVendorTypeName() {
        return vendorTypeName;
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

    public String getDefaultApGlAccountChoice()
            throws NamingException {
        setupDefaultApGlAccountChoices();
        
        return defaultApGlAccountChoice;
    }
    
    public void setDefaultApGlAccountChoice(String defaultApGlAccountChoice) {
        this.defaultApGlAccountChoice = defaultApGlAccountChoice;
    }
    
    public List<LabelValueBean> getDefaultApGlAccountChoices()
            throws NamingException {
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
        
        defaultHoldUntilComplete = false;
        defaultAllowBackorders = false;
        defaultAllowSubstitutions = false;
        defaultAllowCombiningShipments = false;
        defaultRequireReference = false;
        defaultAllowReferenceDuplicates = false;
        isDefault = false;
    }
    
}

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

package com.echothree.ui.web.main.action.purchasing.vendor;

import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.common.edit.VendorEdit;
import com.echothree.control.user.vendor.common.form.EditVendorForm;
import com.echothree.control.user.vendor.common.result.EditVendorResult;
import com.echothree.control.user.vendor.common.spec.VendorSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Purchasing/Vendor/VendorEdit",
    mappingClass = SecureActionMapping.class,
    name = "VendorEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Purchasing/Vendor/Review", redirect = true),
        @SproutForward(name = "Form", path = "/purchasing/vendor/vendorEdit.jsp")
    }
)
public class VendorEditAction
        extends MainBaseEditAction<VendorEditActionForm, VendorSpec, VendorEdit, EditVendorForm, EditVendorResult> {
    
    @Override
    protected VendorSpec getSpec(HttpServletRequest request, VendorEditActionForm actionForm)
            throws NamingException {
        var spec = VendorUtil.getHome().getVendorUniversalSpec();

        spec.setVendorName(findParameter(request, ParameterConstants.ORIGINAL_VENDOR_NAME, actionForm.getOriginalVendorName()));

        return spec;
    }
    
    @Override
    protected VendorEdit getEdit(HttpServletRequest request, VendorEditActionForm actionForm)
            throws NamingException {
        var edit = VendorUtil.getHome().getVendorEdit();

        edit.setVendorName(actionForm.getVendorName());
        edit.setVendorTypeName(actionForm.getVendorTypeChoice());
        edit.setMinimumPurchaseOrderLines(actionForm.getMinimumPurchaseOrderLines());
        edit.setMaximumPurchaseOrderLines(actionForm.getMaximumPurchaseOrderLines());
        edit.setMinimumPurchaseOrderAmount(actionForm.getMinimumPurchaseOrderAmount());
        edit.setMaximumPurchaseOrderAmount(actionForm.getMaximumPurchaseOrderAmount());
        edit.setUseItemPurchasingCategories(actionForm.getUseItemPurchasingCategories().toString());
        edit.setDefaultItemAliasTypeName(actionForm.getDefaultItemAliasTypeChoice());
        edit.setCancellationPolicyName(actionForm.getCancellationPolicyChoice());
        edit.setReturnPolicyName(actionForm.getReturnPolicyChoice());
        edit.setApGlAccountName(actionForm.getApGlAccountChoice());
        edit.setHoldUntilComplete(actionForm.getHoldUntilComplete().toString());
        edit.setAllowBackorders(actionForm.getAllowBackorders().toString());
        edit.setAllowSubstitutions(actionForm.getAllowSubstitutions().toString());
        edit.setAllowCombiningShipments(actionForm.getAllowCombiningShipments().toString());
        edit.setRequireReference(actionForm.getRequireReference().toString());
        edit.setAllowReferenceDuplicates(actionForm.getAllowReferenceDuplicates().toString());
        edit.setReferenceValidationPattern(actionForm.getReferenceValidationPattern());
        edit.setPersonalTitleId(actionForm.getPersonalTitleChoice());
        edit.setFirstName(actionForm.getFirstName());
        edit.setMiddleName(actionForm.getMiddleName());
        edit.setLastName(actionForm.getLastName());
        edit.setNameSuffixId(actionForm.getNameSuffixChoice());
        edit.setName(actionForm.getName());
        edit.setPreferredLanguageIsoName(actionForm.getLanguageChoice());
        edit.setPreferredCurrencyIsoName(actionForm.getCurrencyChoice());
        edit.setPreferredJavaTimeZoneName(actionForm.getTimeZoneChoice());
        edit.setPreferredDateTimeFormatName(actionForm.getDateTimeFormatChoice());

        return edit;
    }
    
    @Override
    protected EditVendorForm getForm()
            throws NamingException {
        return VendorUtil.getHome().getEditVendorForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, VendorEditActionForm actionForm, EditVendorResult result, VendorSpec spec, VendorEdit edit) {
        actionForm.setOriginalVendorName(spec.getVendorName());
        actionForm.setVendorName(edit.getVendorName());
        actionForm.setVendorTypeChoice(edit.getVendorTypeName());
        actionForm.setMinimumPurchaseOrderLines(edit.getMinimumPurchaseOrderLines());
        actionForm.setMaximumPurchaseOrderLines(edit.getMaximumPurchaseOrderLines());
        actionForm.setMinimumPurchaseOrderAmount(edit.getMinimumPurchaseOrderAmount());
        actionForm.setMaximumPurchaseOrderAmount(edit.getMaximumPurchaseOrderAmount());
        actionForm.setUseItemPurchasingCategories(Boolean.valueOf(edit.getUseItemPurchasingCategories()));
        actionForm.setDefaultItemAliasTypeChoice(edit.getDefaultItemAliasTypeName());
        actionForm.setCancellationPolicyChoice(edit.getCancellationPolicyName());
        actionForm.setReturnPolicyChoice(edit.getReturnPolicyName());
        actionForm.setApGlAccountChoice(edit.getApGlAccountName());
        actionForm.setHoldUntilComplete(Boolean.valueOf(edit.getHoldUntilComplete()));
        actionForm.setAllowBackorders(Boolean.valueOf(edit.getAllowBackorders()));
        actionForm.setAllowSubstitutions(Boolean.valueOf(edit.getAllowSubstitutions()));
        actionForm.setAllowCombiningShipments(Boolean.valueOf(edit.getAllowCombiningShipments()));
        actionForm.setRequireReference(Boolean.valueOf(edit.getRequireReference()));
        actionForm.setAllowReferenceDuplicates(Boolean.valueOf(edit.getAllowReferenceDuplicates()));
        actionForm.setReferenceValidationPattern(edit.getReferenceValidationPattern());
        actionForm.setPersonalTitleChoice(edit.getPersonalTitleId());
        actionForm.setFirstName(edit.getFirstName());
        actionForm.setMiddleName(edit.getMiddleName());
        actionForm.setLastName(edit.getLastName());
        actionForm.setNameSuffixChoice(edit.getNameSuffixId());
        actionForm.setName(edit.getName());
        actionForm.setLanguageChoice(edit.getPreferredLanguageIsoName());
        actionForm.setCurrencyChoice(edit.getPreferredCurrencyIsoName());
        actionForm.setTimeZoneChoice(edit.getPreferredJavaTimeZoneName());
        actionForm.setDateTimeFormatChoice(edit.getPreferredDateTimeFormatName());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditVendorForm commandForm)
            throws Exception {
        return VendorUtil.getHome().editVendor(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(VendorEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.VENDOR_NAME, actionForm.getVendorName());
    }

    @Override
    protected void setupTransferForForm(HttpServletRequest request, VendorEditActionForm actionForm, EditVendorResult result) {
        request.setAttribute(AttributeConstants.VENDOR, result.getVendor());
    }

}

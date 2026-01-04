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
import com.echothree.control.user.vendor.common.result.GetVendorResult;
import com.echothree.model.control.comment.common.CommentOptions;
import com.echothree.model.control.contact.common.ContactOptions;
import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.invoice.common.InvoiceOptions;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.model.control.vendor.common.VendorOptions;
import com.echothree.model.data.communication.common.CommunicationEventConstants;
import com.echothree.model.data.invoice.common.InvoiceConstants;
import com.echothree.model.data.vendor.common.VendorItemConstants;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.string.ContactPostalAddressUtils;
import com.echothree.util.common.transfer.Limit;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Purchasing/Vendor/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/purchasing/vendor/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var commandForm = VendorUtil.getHome().getGetVendorForm();
        
        commandForm.setVendorName(request.getParameter(ParameterConstants.VENDOR_NAME));
        commandForm.setPartyName(request.getParameter(ParameterConstants.PARTY_NAME));
        
        Set<String> options = new HashSet<>();
        options.add(CommentOptions.CommentIncludeClob);
        options.add(PartyOptions.PartyIncludePartyAliases);
        options.add(PartyOptions.PartyIncludePartyContactLists);
        options.add(PartyOptions.PartyIncludePartyContactMechanisms);
        options.add(PartyOptions.PartyIncludePartyCarriers);
        options.add(PartyOptions.PartyIncludePartyCarrierAccounts);
        options.add(PartyOptions.PartyIncludePartyDocuments);
        options.add(ContactOptions.PartyContactMechanismIncludePartyContactMechanismPurposes);
        options.add(ContactOptions.PartyContactMechanismIncludePartyContactMechanismRelationshipsByFromPartyContactMechanism);
        options.add(VendorOptions.VendorIncludeEntityAttributeGroups);
        options.add(VendorOptions.VendorIncludeTagScopes);
        options.add(VendorOptions.VendorIncludeBillingAccounts);
        options.add(VendorOptions.VendorIncludeVendorItems);
        options.add(VendorOptions.VendorIncludeInvoicesFrom);
        options.add(VendorOptions.VendorIncludePurchasingComments);
        options.add(VendorOptions.VendorIncludePartyCreditLimits);
        options.add(VendorOptions.VendorIncludePartyTerm);
        options.add(VendorOptions.VendorIncludeSubscriptions);
        options.add(VendorOptions.VendorIncludeCommunicationEvents);
        options.add(InvoiceOptions.InvoiceIncludeRoles);
        options.add(CoreOptions.EntityAttributeGroupIncludeEntityAttributes);
        options.add(CoreOptions.EntityAttributeIncludeValue);
        options.add(CoreOptions.EntityStringAttributeIncludeString);
        options.add(CoreOptions.EntityInstanceIncludeNames);
        commandForm.setOptions(ContactPostalAddressUtils.getInstance().addOptions(options));
        
        Map<String, Limit> limits = new HashMap<>();
        limits.put(VendorItemConstants.ENTITY_TYPE_NAME, new Limit("5"));
        limits.put(InvoiceConstants.ENTITY_TYPE_NAME, new Limit("5"));
        limits.put(CommunicationEventConstants.ENTITY_TYPE_NAME, new Limit("5"));
        commandForm.setLimits(limits);

        var commandResult = VendorUtil.getHome().getVendor(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetVendorResult)executionResult.getResult();
        var vendor = result.getVendor();
        
        if(vendor == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            request.setAttribute(AttributeConstants.VENDOR, vendor);
            forwardKey = ForwardConstants.DISPLAY;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}
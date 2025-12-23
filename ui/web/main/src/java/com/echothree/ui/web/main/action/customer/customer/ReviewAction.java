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

import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.common.result.GetCustomerResult;
import com.echothree.model.control.comment.common.CommentOptions;
import com.echothree.model.control.contact.common.ContactOptions;
import com.echothree.model.control.contactlist.common.ContactListOptions;
import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.customer.common.CustomerOptions;
import com.echothree.model.control.invoice.common.InvoiceOptions;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.model.data.communication.common.CommunicationEventConstants;
import com.echothree.model.data.invoice.common.InvoiceConstants;
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
    path = "/Customer/Customer/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/customer/customer/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var commandForm = CustomerUtil.getHome().getGetCustomerForm();
        
        commandForm.setCustomerName(request.getParameter(ParameterConstants.CUSTOMER_NAME));
        commandForm.setPartyName(request.getParameter(ParameterConstants.PARTY_NAME));
        
        Set<String> options = new HashSet<>();
        options.add(PartyOptions.PartyIncludePartyAliases);
        options.add(PartyOptions.PartyIncludePartyContactLists);
        options.add(PartyOptions.PartyIncludePartyContactMechanisms);
        options.add(PartyOptions.PartyIncludePartyCarriers);
        options.add(PartyOptions.PartyIncludePartyCarrierAccounts);
        options.add(PartyOptions.PartyIncludePartyDocuments);
        options.add(PartyOptions.PartyIncludeUserLogin);
        options.add(PartyOptions.PartyIncludeRecoveryAnswer);
        options.add(ContactOptions.PartyContactMechanismIncludePartyContactMechanismPurposes);
        options.add(ContactOptions.PartyContactMechanismIncludePartyContactMechanismRelationshipsByFromPartyContactMechanism);
        options.add(ContactListOptions.PartyContactListIncludeStatus);
        options.add(CommentOptions.CommentIncludeClob);
        options.add(CustomerOptions.CustomerIncludeBillingAccounts);
        options.add(CustomerOptions.CustomerIncludeCustomerServiceComments);
        options.add(CustomerOptions.CustomerIncludeInvoicesTo);
        options.add(CustomerOptions.CustomerIncludeOrderEntryComments);
        options.add(CustomerOptions.CustomerIncludePartyCreditLimits);
        options.add(CustomerOptions.CustomerIncludePartyTerm);
        options.add(CustomerOptions.CustomerIncludePartyPaymentMethods);
        options.add(CustomerOptions.CustomerIncludePartyCancellationPolicies);
        options.add(CustomerOptions.CustomerIncludePartyReturnPolicies);
        options.add(CustomerOptions.CustomerIncludeSubscriptions);
        options.add(CustomerOptions.CustomerIncludeCommunicationEvents);
        options.add(CustomerOptions.CustomerIncludeEntityAttributeGroups);
        options.add(CustomerOptions.CustomerIncludeTagScopes);
        options.add(InvoiceOptions.InvoiceIncludeRoles);
        options.add(CoreOptions.EntityAttributeGroupIncludeEntityAttributes);
        options.add(CoreOptions.EntityAttributeIncludeValue);
        options.add(CoreOptions.EntityStringAttributeIncludeString);
        options.add(CoreOptions.EntityInstanceIncludeNames);
        options.add(CoreOptions.EntityInstanceIncludeEntityAppearance);
        options.add(CoreOptions.AppearanceIncludeTextDecorations);
        options.add(CoreOptions.AppearanceIncludeTextTransformations);
        commandForm.setOptions(ContactPostalAddressUtils.getInstance().addOptions(options));
        
        Map<String, Limit> limits = new HashMap<>();
        limits.put(InvoiceConstants.ENTITY_TYPE_NAME, new Limit("5"));
        limits.put(CommunicationEventConstants.ENTITY_TYPE_NAME, new Limit("5"));
        commandForm.setLimits(limits);

        var commandResult = CustomerUtil.getHome().getCustomer(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetCustomerResult)executionResult.getResult();
        var customer = result.getCustomer();
        
        if(customer == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            var wishlists = result.getWishlists();
            
            request.setAttribute(AttributeConstants.CUSTOMER, customer);
            request.setAttribute(AttributeConstants.WISHLISTS, wishlists.isEmpty() ? null: wishlists);
            forwardKey = ForwardConstants.DISPLAY;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}
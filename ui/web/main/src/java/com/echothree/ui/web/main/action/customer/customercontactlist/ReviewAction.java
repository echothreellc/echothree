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

package com.echothree.ui.web.main.action.customer.customercontactlist;

import com.echothree.control.user.contactlist.common.ContactListUtil;
import com.echothree.control.user.contactlist.common.result.GetPartyContactListResult;
import com.echothree.model.control.comment.common.CommentOptions;
import com.echothree.model.control.contactlist.common.ContactListOptions;
import com.echothree.model.control.contactlist.common.transfer.PartyContactListTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.string.ContactPostalAddressUtils;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Customer/CustomerContactList/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/customer/customercontactlist/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var commandForm = ContactListUtil.getHome().getGetPartyContactListForm();
        var partyName = request.getParameter(ParameterConstants.PARTY_NAME);
        var contactListName = request.getParameter(ParameterConstants.CONTACT_LIST_NAME);

        commandForm.setPartyName(partyName);
        commandForm.setContactListName(contactListName);

        Set<String> options = new HashSet<>();
        options.add(ContactListOptions.PartyContactListIncludeStatus);
        options.add(ContactListOptions.PartyContactListIncludeComments);
        options.add(CommentOptions.CommentIncludeClob);
        commandForm.setOptions(ContactPostalAddressUtils.getInstance().addOptions(options));

        var commandResult = ContactListUtil.getHome().getPartyContactList(getUserVisitPK(request), commandForm);
        PartyContactListTransfer partyContactList = null;

        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetPartyContactListResult)executionResult.getResult();
            partyContactList = result.getPartyContactList();
        }

        if(partyContactList == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            request.setAttribute(AttributeConstants.PARTY_CONTACT_LIST, partyContactList);
            CustomerContactListUtil.getInstance().setupCustomer(request, partyName);
            forwardKey = ForwardConstants.DISPLAY;
        }

        return mapping.findForward(forwardKey);
    }
    
}
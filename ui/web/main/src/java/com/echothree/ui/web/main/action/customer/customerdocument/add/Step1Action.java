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

package com.echothree.ui.web.main.action.customer.customerdocument.add;

import com.echothree.control.user.document.common.DocumentUtil;
import com.echothree.control.user.document.common.result.GetPartyTypeDocumentTypeUsageTypesResult;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.ui.web.main.action.customer.customerdocument.BaseCustomerDocumentAction;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Customer/CustomerDocument/Add/Step1",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/customer/customerdocument/add/step1.jsp")
    }
)
public class Step1Action
        extends BaseCustomerDocumentAction {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        var commandForm = DocumentUtil.getHome().getGetPartyTypeDocumentTypeUsageTypesForm();

        commandForm.setPartyTypeName(PartyTypes.CUSTOMER.name());

        var commandResult = DocumentUtil.getHome().getPartyTypeDocumentTypeUsageTypes(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetPartyTypeDocumentTypeUsageTypesResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.PARTY_TYPE_DOCUMENT_TYPE_USAGE_TYPES, result.getPartyTypeDocumentTypeUsageTypes());

        setupCustomer(request);
        
        return mapping.findForward(ForwardConstants.DISPLAY);
    }

}

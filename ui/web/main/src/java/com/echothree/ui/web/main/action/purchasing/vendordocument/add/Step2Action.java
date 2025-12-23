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

package com.echothree.ui.web.main.action.purchasing.vendordocument.add;

import com.echothree.control.user.document.common.DocumentUtil;
import com.echothree.control.user.document.common.result.GetDocumentTypeUsagesResult;
import com.echothree.ui.web.main.action.purchasing.vendordocument.BaseVendorDocumentAction;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.ParameterConstants;
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
    path = "/Purchasing/VendorDocument/Add/Step2",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/purchasing/vendordocument/add/step2.jsp")
    }
)
public class Step2Action
        extends BaseVendorDocumentAction {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        var commandForm = DocumentUtil.getHome().getGetDocumentTypeUsagesForm();
        var documentTypeUsageTypeName = request.getParameter(ParameterConstants.DOCUMENT_TYPE_USAGE_TYPE_NAME);

        commandForm.setDocumentTypeUsageTypeName(documentTypeUsageTypeName);

        var commandResult = DocumentUtil.getHome().getDocumentTypeUsages(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetDocumentTypeUsagesResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.DOCUMENT_TYPE_USAGES, result.getDocumentTypeUsages());

        setupVendor(request);
        
        return mapping.findForward(ForwardConstants.DISPLAY);
    }

}

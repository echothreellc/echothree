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

package com.echothree.ui.web.main.action.shipping.shippingmethod;

import com.echothree.control.user.shipping.common.ShippingUtil;
import com.echothree.control.user.shipping.common.result.GetShippingMethodResult;
import com.echothree.model.control.comment.common.CommentOptions;
import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.shipping.common.ShippingOptions;
import com.echothree.model.control.shipping.common.transfer.ShippingMethodTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
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
    path = "/Shipping/ShippingMethod/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/shipping/shippingmethod/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var commandForm = ShippingUtil.getHome().getGetShippingMethodForm();

        commandForm.setShippingMethodName(request.getParameter(ParameterConstants.SHIPPING_METHOD_NAME));

        Set<String> options = new HashSet<>();
        options.add(CommentOptions.CommentIncludeClob);
        options.add(CoreOptions.EntityAttributeGroupIncludeEntityAttributes);
        options.add(CoreOptions.EntityAttributeIncludeValue);
        options.add(CoreOptions.EntityStringAttributeIncludeString);
        options.add(CoreOptions.EntityInstanceIncludeNames);
        options.add(ShippingOptions.ShippingMethodIncludeComments);
        options.add(ShippingOptions.ShippingMethodIncludeEntityAttributeGroups);
        commandForm.setOptions(options);

        var commandResult = ShippingUtil.getHome().getShippingMethod(getUserVisitPK(request), commandForm);
        ShippingMethodTransfer shippingMethod = null;

        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetShippingMethodResult)executionResult.getResult();

            shippingMethod = result.getShippingMethod();
        }

        if(shippingMethod == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            request.setAttribute(AttributeConstants.SHIPPING_METHOD, shippingMethod);
            forwardKey = ForwardConstants.DISPLAY;
        }

        return mapping.findForward(forwardKey);
    }

}

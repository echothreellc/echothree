// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.ui.web.main.action.item.relateditemtype;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.form.GetRelatedItemTypeDescriptionsForm;
import com.echothree.control.user.item.common.result.GetRelatedItemTypeDescriptionsResult;
import com.echothree.model.control.item.common.transfer.RelatedItemTypeTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
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
    path = "/Item/RelatedItemType/Description",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/item/relateditemtype/description.jsp")
    }
)
public class DescriptionAction
        extends MainBaseAction<ActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        String relatedItemTypeName = request.getParameter(ParameterConstants.RELATED_ITEM_TYPE_NAME);
        GetRelatedItemTypeDescriptionsForm commandForm = ItemUtil.getHome().getGetRelatedItemTypeDescriptionsForm();

        commandForm.setRelatedItemTypeName(relatedItemTypeName);

        CommandResult commandResult = ItemUtil.getHome().getRelatedItemTypeDescriptions(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetRelatedItemTypeDescriptionsResult result = (GetRelatedItemTypeDescriptionsResult) executionResult.getResult();
            RelatedItemTypeTransfer relatedItemTypeTransfer = result.getRelatedItemType();

            request.setAttribute(AttributeConstants.RELATED_ITEM_TYPE, relatedItemTypeTransfer);
            request.setAttribute(AttributeConstants.RELATED_ITEM_TYPE_DESCRIPTIONS, result.getRelatedItemTypeDescriptions());
            forwardKey = ForwardConstants.DISPLAY;
        } else {
            forwardKey = ForwardConstants.ERROR_404;
        }

        return mapping.findForward(forwardKey);
    }
    
}

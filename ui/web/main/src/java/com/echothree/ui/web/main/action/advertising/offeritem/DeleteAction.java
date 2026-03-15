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

package com.echothree.ui.web.main.action.advertising.offeritem;

import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.offer.common.result.GetOfferItemResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
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
    path = "/Advertising/OfferItem/Delete",
    mappingClass = SecureActionMapping.class,
    name = "OfferItemDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Advertising/OfferItem/Main", redirect = true),
        @SproutForward(name = "Form", path = "/advertising/offeritem/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName(final DeleteActionForm actionForm) {
        return EntityTypes.OfferItem.name();
    }

    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setOfferName(findParameter(request, ParameterConstants.OFFER_NAME, actionForm.getOfferName()));
        actionForm.setItemName(findParameter(request, ParameterConstants.ITEM_NAME, actionForm.getItemName()));
    }

    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = OfferUtil.getHome().getGetOfferItemForm();

        commandForm.setOfferName(actionForm.getOfferName());
        commandForm.setItemName(actionForm.getItemName());

        var commandResult = OfferUtil.getHome().getOfferItem(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetOfferItemResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.OFFER_ITEM, result.getOfferItem());
    }

    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = OfferUtil.getHome().getDeleteOfferItemForm();

        commandForm.setOfferName(actionForm.getOfferName());
        commandForm.setItemName(actionForm.getItemName());

        return OfferUtil.getHome().deleteOfferItem(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.OFFER_NAME, actionForm.getOfferName());
    }

}

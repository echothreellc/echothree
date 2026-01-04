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

package com.echothree.ui.web.main.action.core.ratingtypelistitem;

import com.echothree.control.user.rating.common.RatingUtil;
import com.echothree.control.user.rating.common.result.GetRatingTypeListItemsResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Core/RatingTypeListItem/Main",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/core/ratingtypelistitem/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey;
        
        try {
            var componentVendorName = request.getParameter(ParameterConstants.COMPONENT_VENDOR_NAME);
            var entityTypeName = request.getParameter(ParameterConstants.ENTITY_TYPE_NAME);
            var ratingTypeName = request.getParameter(ParameterConstants.RATING_TYPE_NAME);
            var commandForm = RatingUtil.getHome().getGetRatingTypeListItemsForm();
            
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setRatingTypeName(ratingTypeName);

            var commandResult = RatingUtil.getHome().getRatingTypeListItems(getUserVisitPK(request), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetRatingTypeListItemsResult)executionResult.getResult();
            var componentVendorTransfer = result.getComponentVendor();
            var entityTypeTransfer = result.getEntityType();
            var ratingTypeTransfer = result.getRatingType();
            
            request.setAttribute(AttributeConstants.COMPONENT_VENDOR, componentVendorTransfer);
            request.setAttribute(AttributeConstants.COMPONENT_VENDOR_NAME, componentVendorTransfer.getComponentVendorName());
            request.setAttribute(AttributeConstants.ENTITY_TYPE, entityTypeTransfer);
            request.setAttribute(AttributeConstants.ENTITY_TYPE_NAME, entityTypeTransfer.getEntityTypeName());
            request.setAttribute(AttributeConstants.RATING_TYPE, ratingTypeTransfer);
            request.setAttribute(AttributeConstants.RATING_TYPE_NAME, ratingTypeTransfer.getRatingTypeName());
            request.setAttribute(AttributeConstants.RATING_TYPE_LIST_ITEMS, result.getRatingTypeListItems());
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}
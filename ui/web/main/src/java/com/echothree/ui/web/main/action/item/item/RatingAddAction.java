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

package com.echothree.ui.web.main.action.item.item;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.GetItemResult;
import com.echothree.control.user.rating.common.RatingUtil;
import com.echothree.control.user.rating.common.result.GetRatingTypeResult;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAddAction;
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
    path = "/Item/Item/RatingAdd",
    mappingClass = SecureActionMapping.class,
    name = "ItemRatingAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/Item/Review", redirect = true),
        @SproutForward(name = "Form", path = "/item/item/ratingAdd.jsp")
    }
)
public class RatingAddAction
        extends MainBaseAddAction<RatingAddActionForm> {

    @Override
    public void setupParameters(RatingAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setItemName(findParameter(request, ParameterConstants.ITEM_NAME, actionForm.getItemName()));
        actionForm.setRatingTypeName(findParameter(request, ParameterConstants.RATING_TYPE_NAME, actionForm.getRatingTypeName()));
    }
    
    public String getItemEntityRef(RatingAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ItemUtil.getHome().getGetItemForm();
        
        commandForm.setItemName(actionForm.getItemName());

        var commandResult = ItemUtil.getHome().getItem(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetItemResult)executionResult.getResult();
        var item = result.getItem();
        
        request.setAttribute(AttributeConstants.ITEM, item);
        
        return item.getEntityInstance().getEntityRef();
    }
    
    public void setupRatingTypeTransfer(RatingAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = RatingUtil.getHome().getGetRatingTypeForm();
        
        commandForm.setComponentVendorName(ComponentVendors.ECHO_THREE.name());
        commandForm.setEntityTypeName(EntityTypes.Item.name());
        commandForm.setRatingTypeName(actionForm.getRatingTypeName());

        var commandResult = RatingUtil.getHome().getRatingType(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetRatingTypeResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.RATING_TYPE, result.getRatingType());
    }
    
    @Override
    public void setupTransfer(RatingAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        if(request.getAttribute(AttributeConstants.ITEM) == null) {
            getItemEntityRef(actionForm, request);
        }
        
        setupRatingTypeTransfer(actionForm, request);
    }
    
    @Override
    public CommandResult doAdd(RatingAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = RatingUtil.getHome().getCreateRatingForm();

        commandForm.setEntityRef(getItemEntityRef(actionForm, request));
        commandForm.setRatingTypeName(actionForm.getRatingTypeName());
        commandForm.setRatingTypeListItemName(actionForm.getRatingTypeListItemChoice());
        
        return RatingUtil.getHome().createRating(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(RatingAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_NAME, actionForm.getItemName());
    }
    
}

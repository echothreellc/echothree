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
import com.echothree.control.user.rating.common.result.GetRatingResult;
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
    path = "/Item/Item/RatingDelete",
    mappingClass = SecureActionMapping.class,
    name = "ItemRatingDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/Item/Review", redirect = true),
        @SproutForward(name = "Form", path = "/item/item/ratingDelete.jsp")
    }
)
public class RatingDeleteAction
        extends MainBaseDeleteAction<RatingDeleteActionForm> {
    
    @Override
    public String getEntityTypeName(final RatingDeleteActionForm actionForm) {
        return EntityTypes.Rating.name();
    }
    
    @Override
    public void setupParameters(RatingDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setItemName(findParameter(request, ParameterConstants.ITEM_NAME, actionForm.getItemName()));
        actionForm.setRatingName(findParameter(request, ParameterConstants.RATING_NAME, actionForm.getRatingName()));
    }
    
    public void setupItemTransfer(RatingDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ItemUtil.getHome().getGetItemForm();
        
        commandForm.setItemName(actionForm.getItemName());

        var commandResult = ItemUtil.getHome().getItem(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetItemResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.ITEM, result.getItem());
    }
    
    public void setupRatingTransfer(RatingDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = RatingUtil.getHome().getGetRatingForm();
        
        commandForm.setRatingName(actionForm.getRatingName());

        var commandResult = RatingUtil.getHome().getRating(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetRatingResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.RATING, result.getRating());
    }
    
    @Override
    public void setupTransfer(RatingDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        setupItemTransfer(actionForm, request);
        setupRatingTransfer(actionForm, request);
    }
    
    @Override
    public CommandResult doDelete(RatingDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = RatingUtil.getHome().getDeleteRatingForm();
        
        commandForm.setRatingName(actionForm.getRatingName());

        return RatingUtil.getHome().deleteRating(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(RatingDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_NAME, actionForm.getItemName());
    }
    
}

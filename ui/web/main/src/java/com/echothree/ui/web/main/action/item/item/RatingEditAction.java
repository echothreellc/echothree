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
import com.echothree.control.user.rating.common.edit.RatingEdit;
import com.echothree.control.user.rating.common.form.EditRatingForm;
import com.echothree.control.user.rating.common.result.EditRatingResult;
import com.echothree.control.user.rating.common.spec.RatingSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
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
    path = "/Item/Item/RatingEdit",
    mappingClass = SecureActionMapping.class,
    name = "ItemRatingEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/Item/Review", redirect = true),
        @SproutForward(name = "Form", path = "/item/item/ratingEdit.jsp")
    }
)
public class RatingEditAction
        extends MainBaseEditAction<RatingEditActionForm, RatingSpec, RatingEdit, EditRatingForm, EditRatingResult> {
    
    @Override
    protected RatingSpec getSpec(HttpServletRequest request, RatingEditActionForm actionForm)
            throws NamingException {
        var spec = RatingUtil.getHome().getRatingSpec();
        var ratingName = request.getParameter(ParameterConstants.RATING_NAME);

        if(ratingName == null) {
            ratingName = actionForm.getRatingName();
        }

        spec.setRatingName(ratingName);
        
        return spec;
    }
    
    @Override
    protected RatingEdit getEdit(HttpServletRequest request, RatingEditActionForm actionForm)
            throws NamingException {
        var edit = RatingUtil.getHome().getRatingEdit();

        edit.setRatingTypeListItemName(actionForm.getRatingTypeListItemChoice());

        return edit;
    }
    
    @Override
    protected EditRatingForm getForm()
            throws NamingException {
        return RatingUtil.getHome().getEditRatingForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, RatingEditActionForm actionForm, EditRatingResult result, RatingSpec spec, RatingEdit edit) {
        actionForm.setItemName(request.getParameter(ParameterConstants.ITEM_NAME));
        actionForm.setRatingName(spec.getRatingName());
        actionForm.setRatingTypeListItemChoice(edit.getRatingTypeListItemName());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditRatingForm commandForm)
            throws Exception {
        var commandResult = RatingUtil.getHome().editRating(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (EditRatingResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.RATING, result.getRating());
        
        return commandResult;
    }
    
    @Override
    public void setupForwardParameters(RatingEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_NAME, actionForm.getItemName());
    }
    
    @Override
    public void setupTransfer(RatingEditActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ItemUtil.getHome().getGetItemForm();
        
        commandForm.setItemName(actionForm.getItemName());

        var commandResult = ItemUtil.getHome().getItem(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetItemResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.ITEM, result.getItem());
    }
    
}
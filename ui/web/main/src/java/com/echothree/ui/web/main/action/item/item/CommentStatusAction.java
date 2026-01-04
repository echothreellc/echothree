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

import com.echothree.control.user.comment.common.CommentUtil;
import com.echothree.control.user.comment.common.result.GetCommentResult;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.GetItemResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Item/Item/CommentStatus",
    mappingClass = SecureActionMapping.class,
    name = "ItemCommentStatus",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/Item/Review", redirect = true),
        @SproutForward(name = "Form", path = "/item/item/commentStatus.jsp")
    }
)
public class CommentStatusAction
        extends MainBaseAction<CommentStatusActionForm> {
    
    public void setupItem(HttpServletRequest request, String itemName)
            throws NamingException {
        var commandForm = ItemUtil.getHome().getGetItemForm();

        commandForm.setItemName(itemName);

        var commandResult = ItemUtil.getHome().getItem(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetItemResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.ITEM, result.getItem());
    }

    public void setupComment(HttpServletRequest request, String commentName)
            throws NamingException {
        var commandForm = CommentUtil.getHome().getGetCommentForm();

        commandForm.setCommentName(commentName);

        var commandResult = CommentUtil.getHome().getComment(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetCommentResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.COMMENT, result.getComment());
    }

    @Override
    public ActionForward executeAction(ActionMapping mapping, CommentStatusActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var itemName = request.getParameter(ParameterConstants.ITEM_NAME);
        var commentName = request.getParameter(ParameterConstants.COMMENT_NAME);

        if(itemName == null) {
            itemName = actionForm.getItemName();
        }
        if(commentName == null) {
            commentName = actionForm.getCommentName();
        }

        if(wasPost(request)) {
            var commandForm = CommentUtil.getHome().getSetCommentStatusForm();
        
            commandForm.setCommentName(commentName);
            commandForm.setCommentStatusChoice(actionForm.getCommentStatusChoice());

            var commandResult = CommentUtil.getHome().setCommentStatus(getUserVisitPK(request), commandForm);

            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            actionForm.setItemName(itemName);
            actionForm.setCommentName(commentName);

            forwardKey = ForwardConstants.FORM;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            setupItem(request, itemName);
            setupComment(request, commentName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);

            parameters.put(ParameterConstants.ITEM_NAME, itemName);
            customActionForward.setParameters(parameters);
        }

        return customActionForward;
    }
    
}

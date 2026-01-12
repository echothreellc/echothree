// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.ui.web.main.action.content.contentcatalogitem;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.result.GetContentCatalogItemResult;
import com.echothree.model.control.comment.common.CommentOptions;
import com.echothree.model.control.content.common.ContentOptions;
import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Content/ContentCatalogItem/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/content/contentcatalogitem/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var commandForm = ContentUtil.getHome().getGetContentCatalogItemForm();

        commandForm.setContentCollectionName(request.getParameter(ParameterConstants.CONTENT_COLLECTION_NAME));
        commandForm.setContentCatalogName(request.getParameter(ParameterConstants.CONTENT_CATALOG_NAME));
        commandForm.setItemName(request.getParameter(ParameterConstants.ITEM_NAME));
        commandForm.setInventoryConditionName(request.getParameter(ParameterConstants.INVENTORY_CONDITION_NAME));
        commandForm.setUnitOfMeasureTypeName(request.getParameter(ParameterConstants.UNIT_OF_MEASURE_TYPE_NAME));
        commandForm.setCurrencyIsoName(request.getParameter(ParameterConstants.CURRENCY_ISO_NAME));

        var options = new HashSet<String>();
        options.add(CommentOptions.CommentIncludeClob);
        options.add(ContentOptions.ContentCatalogItemIncludeEntityAttributeGroups);
        options.add(ContentOptions.ContentCatalogItemIncludeTagScopes);
        options.add(CoreOptions.EntityAttributeGroupIncludeEntityAttributes);
        options.add(CoreOptions.EntityAttributeIncludeValue);
        options.add(CoreOptions.EntityStringAttributeIncludeString);
        options.add(CoreOptions.EntityInstanceIncludeNames);
        commandForm.setOptions(options);

        var commandResult = ContentUtil.getHome().getContentCatalogItem(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetContentCatalogItemResult)executionResult.getResult();
        var contentCatalogItem = result.getContentCatalogItem();
        
        if(contentCatalogItem == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            saveToken(request); // Required for ContentCatalogItemIncludeTagScopes and tagScopes.jsp
            request.setAttribute(AttributeConstants.CONTENT_CATALOG_ITEM, contentCatalogItem);
            forwardKey = ForwardConstants.DISPLAY;
        }
        
        return mapping.findForward(forwardKey);
    }

}
// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.ui.web.main.action.content.contentcategoryitem;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.result.GetContentCategoryItemsResult;
import com.echothree.model.data.content.common.ContentCategoryItemConstants;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.transfer.Limit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import static java.lang.Math.toIntExact;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

@SproutAction(
    path = "/Content/ContentCategoryItem/Main",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/content/contentcategoryitem/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        var commandForm = ContentUtil.getHome().getGetContentCategoryItemsForm();
        var results = request.getParameter(ParameterConstants.RESULTS);
        
        commandForm.setContentCollectionName(request.getParameter(ParameterConstants.CONTENT_COLLECTION_NAME));
        commandForm.setContentCatalogName(request.getParameter(ParameterConstants.CONTENT_CATALOG_NAME));
        commandForm.setContentCategoryName(request.getParameter(ParameterConstants.CONTENT_CATEGORY_NAME));
        
        if(results == null) {
            var offsetParameter = request.getParameter(new ParamEncoder("contentCategoryItem").encodeParameterName(TableTagParameters.PARAMETER_PAGE));
            var offset = offsetParameter == null ? null : (Integer.parseInt(offsetParameter) - 1) * 20;

            Map<String, Limit> limits = new HashMap<>();
            limits.put(ContentCategoryItemConstants.ENTITY_TYPE_NAME, new Limit("20", offset == null ? null : offset.toString()));
            commandForm.setLimits(limits);
        }

        var commandResult = ContentUtil.getHome().getContentCategoryItems(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetContentCategoryItemsResult)executionResult.getResult();

            var contentCategoryItemCount = result.getContentCategoryItemCount();
            if(contentCategoryItemCount != null) {
                request.setAttribute(AttributeConstants.CONTENT_CATEGORY_ITEM_COUNT, toIntExact(contentCategoryItemCount));
            }

            request.setAttribute(AttributeConstants.CONTENT_CATEGORY, result.getContentCategory());
            request.setAttribute(AttributeConstants.CONTENT_CATEGORY_ITEMS, new ListWrapper<>(result.getContentCategoryItems()));
            forwardKey = ForwardConstants.DISPLAY;
        }
        
        return mapping.findForward(forwardKey == null ? ForwardConstants.ERROR_404 : forwardKey);
    }
    
}

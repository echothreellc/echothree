// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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
import com.echothree.control.user.content.remote.form.GetContentCatalogItemsForm;
import com.echothree.control.user.content.remote.result.GetContentCatalogItemsResult;
import com.echothree.model.control.content.common.ContentOptions;
import com.echothree.model.data.content.common.ContentCatalogItemConstants;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
import com.echothree.util.remote.transfer.Limit;
import com.echothree.util.remote.transfer.ListWrapper;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

@SproutAction(
    path = "/Content/ContentCatalogItem/Main",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/content/contentcatalogitem/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        GetContentCatalogItemsForm commandForm = ContentUtil.getHome().getGetContentCatalogItemsForm();
        String results = request.getParameter(ParameterConstants.RESULTS);

        commandForm.setContentCollectionName(request.getParameter(ParameterConstants.CONTENT_COLLECTION_NAME));
        commandForm.setContentCatalogName(request.getParameter(ParameterConstants.CONTENT_CATALOG_NAME));

        Set<String> options = new HashSet<>();
        options.add(ContentOptions.ContentCatalogIncludeContentCatalogItems);
        commandForm.setOptions(options);

        if(results == null) {
            String offsetParameter = request.getParameter((new ParamEncoder("contentCatalogItem").encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
            Integer offset = offsetParameter == null ? null : (Integer.parseInt(offsetParameter) - 1) * 20;

            Map<String, Limit> limits = new HashMap<>();
            limits.put(ContentCatalogItemConstants.ENTITY_TYPE_NAME, new Limit("20", offset == null ? null : offset.toString()));
            commandForm.setLimits(limits);
        }
        
        CommandResult commandResult = ContentUtil.getHome().getContentCatalogItems(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetContentCatalogItemsResult result = (GetContentCatalogItemsResult)executionResult.getResult();

            Long contentCatalogItemCount = result.getContentCatalogItemCount();
            if(contentCatalogItemCount != null) {
                request.setAttribute(AttributeConstants.CONTENT_CATALOG_ITEM_COUNT, contentCatalogItemCount.intValue());
            }
            
            request.setAttribute(AttributeConstants.CONTENT_CATALOG, result.getContentCatalog());
            request.setAttribute(AttributeConstants.CONTENT_CATALOG_ITEMS, new ListWrapper<>(result.getContentCatalogItems()));
            forwardKey = ForwardConstants.DISPLAY;
        }

        return mapping.findForward(forwardKey == null ? ForwardConstants.ERROR_404 : forwardKey);
    }
    
}

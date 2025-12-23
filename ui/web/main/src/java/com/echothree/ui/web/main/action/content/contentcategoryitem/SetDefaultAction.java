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

package com.echothree.ui.web.main.action.content.contentcategoryitem;

import com.echothree.control.user.content.common.ContentUtil;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Content/ContentCategoryItem/SetDefault",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Content/ContentCategoryItem/Main", redirect = true)
    }
)
public class SetDefaultAction
        extends MainBaseAction<ActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        var contentCollectionName = request.getParameter(ParameterConstants.CONTENT_COLLECTION_NAME);
        var contentCatalogName = request.getParameter(ParameterConstants.CONTENT_CATALOG_NAME);
        var contentCategoryName = request.getParameter(ParameterConstants.CONTENT_CATEGORY_NAME);
        var commandForm = ContentUtil.getHome().getSetDefaultContentCategoryItemForm();

        commandForm.setContentCollectionName(contentCollectionName);
        commandForm.setContentCatalogName(contentCatalogName);
        commandForm.setContentCategoryName(contentCategoryName);
        commandForm.setItemName(request.getParameter(ParameterConstants.ITEM_NAME));
        commandForm.setInventoryConditionName(request.getParameter(ParameterConstants.INVENTORY_CONDITION_NAME));
        commandForm.setUnitOfMeasureTypeName(request.getParameter(ParameterConstants.UNIT_OF_MEASURE_TYPE_NAME));
        commandForm.setCurrencyIsoName(request.getParameter(ParameterConstants.CURRENCY_ISO_NAME));

        ContentUtil.getHome().setDefaultContentCategoryItem(getUserVisitPK(request), commandForm);

        var customActionForward = new CustomActionForward(mapping.findForward(ForwardConstants.DISPLAY));
        Map<String, String> parameters = new HashMap<>(3);

        parameters.put(ParameterConstants.CONTENT_COLLECTION_NAME, contentCollectionName);
        parameters.put(ParameterConstants.CONTENT_CATALOG_NAME, contentCatalogName);
        parameters.put(ParameterConstants.CONTENT_CATEGORY_NAME, contentCategoryName);
        customActionForward.setParameters(parameters);

        return customActionForward;
    }

}

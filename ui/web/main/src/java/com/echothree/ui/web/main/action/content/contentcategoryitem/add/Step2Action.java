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

package com.echothree.ui.web.main.action.content.contentcategoryitem.add;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.result.GetContentCategoryResult;
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
    path = "/Content/ContentCategoryItem/Add/Step2",
    mappingClass = SecureActionMapping.class,
    name = "ContentCategoryItemAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Content/ContentCategoryItem/Main", redirect = true),
        @SproutForward(name = "Form", path = "/content/contentcategoryitem/add/step2.jsp")
    }
)
public class Step2Action
        extends MainBaseAddAction<AddActionForm> {

    @Override
    public void setupParameters(AddActionForm actionForm, HttpServletRequest request) {
        actionForm.setContentCollectionName(findParameter(request, ParameterConstants.CONTENT_COLLECTION_NAME, actionForm.getContentCollectionName()));
        actionForm.setContentCatalogName(findParameter(request, ParameterConstants.CONTENT_CATALOG_NAME, actionForm.getContentCatalogName()));
        actionForm.setContentCategoryName(findParameter(request, ParameterConstants.CONTENT_CATEGORY_NAME, actionForm.getContentCategoryName()));
        actionForm.setItemName(findParameter(request, ParameterConstants.ITEM_NAME, actionForm.getItemName()));
    }
    
    @Override
    public void setupDefaults(AddActionForm actionForm)
            throws NamingException {
        actionForm.setSortOrder("1");
    }
    
    @Override
    public void setupTransfer(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ContentUtil.getHome().getGetContentCategoryForm();

        commandForm.setContentCollectionName(actionForm.getContentCollectionName());
        commandForm.setContentCatalogName(actionForm.getContentCatalogName());
        commandForm.setContentCategoryName(actionForm.getContentCategoryName());

        var commandResult = ContentUtil.getHome().getContentCategory(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetContentCategoryResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.CONTENT_CATEGORY, result.getContentCategory());
    }
    
    @Override
    public CommandResult doAdd(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ContentUtil.getHome().getCreateContentCategoryItemForm();

        commandForm.setContentCollectionName(actionForm.getContentCollectionName());
        commandForm.setContentCatalogName(actionForm.getContentCatalogName());
        commandForm.setContentCategoryName(actionForm.getContentCategoryName());
        commandForm.setItemName(actionForm.getItemName());
        commandForm.setInventoryConditionName(actionForm.getInventoryConditionChoice());
        commandForm.setUnitOfMeasureTypeName(actionForm.getUnitOfMeasureTypeChoice());
        commandForm.setCurrencyIsoName(actionForm.getCurrencyChoice());
        commandForm.setIsDefault(actionForm.getIsDefault().toString());
        commandForm.setSortOrder(actionForm.getSortOrder());

        return ContentUtil.getHome().createContentCategoryItem(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(AddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CONTENT_COLLECTION_NAME, actionForm.getContentCollectionName());
        parameters.put(ParameterConstants.CONTENT_CATALOG_NAME, actionForm.getContentCatalogName());
        parameters.put(ParameterConstants.CONTENT_CATEGORY_NAME, actionForm.getContentCategoryName());
    }
    
}

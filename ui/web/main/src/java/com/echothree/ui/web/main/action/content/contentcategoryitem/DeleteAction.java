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
import com.echothree.control.user.content.common.result.GetContentCategoryItemResult;
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
    path = "/Content/ContentCategoryItem/Delete",
    mappingClass = SecureActionMapping.class,
    name = "ContentCategoryItemDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Content/ContentCategoryItem/Main", redirect = true),
        @SproutForward(name = "Form", path = "/content/contentcategoryitem/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {
    
    @Override
    public String getEntityTypeName() {
        return EntityTypes.ContentCategoryItem.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setContentCollectionName(findParameter(request, ParameterConstants.CONTENT_COLLECTION_NAME, actionForm.getContentCollectionName()));
        actionForm.setContentCatalogName(findParameter(request, ParameterConstants.CONTENT_CATALOG_NAME, actionForm.getContentCatalogName()));
        actionForm.setContentCategoryName(findParameter(request, ParameterConstants.CONTENT_CATEGORY_NAME, actionForm.getContentCategoryName()));
        actionForm.setItemName(findParameter(request, ParameterConstants.ITEM_NAME, actionForm.getItemName()));
        actionForm.setInventoryConditionName(findParameter(request, ParameterConstants.INVENTORY_CONDITION_NAME, actionForm.getInventoryConditionName()));
        actionForm.setUnitOfMeasureTypeName(findParameter(request, ParameterConstants.UNIT_OF_MEASURE_TYPE_NAME, actionForm.getUnitOfMeasureTypeName()));
        actionForm.setCurrencyIsoName(findParameter(request, ParameterConstants.CURRENCY_ISO_NAME, actionForm.getCurrencyIsoName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ContentUtil.getHome().getGetContentCategoryItemForm();
        
        commandForm.setContentCollectionName(actionForm.getContentCollectionName());
        commandForm.setContentCatalogName(actionForm.getContentCatalogName());
        commandForm.setContentCategoryName(actionForm.getContentCategoryName());
        commandForm.setItemName(actionForm.getItemName());
        commandForm.setInventoryConditionName(actionForm.getInventoryConditionName());
        commandForm.setUnitOfMeasureTypeName(actionForm.getUnitOfMeasureTypeName());
        commandForm.setCurrencyIsoName(actionForm.getCurrencyIsoName());

        var commandResult = ContentUtil.getHome().getContentCategoryItem(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetContentCategoryItemResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.CONTENT_CATEGORY_ITEM, result.getContentCategoryItem());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ContentUtil.getHome().getDeleteContentCategoryItemForm();

        commandForm.setContentCollectionName(actionForm.getContentCollectionName());
        commandForm.setContentCatalogName(actionForm.getContentCatalogName());
        commandForm.setContentCategoryName(actionForm.getContentCategoryName());
        commandForm.setItemName(actionForm.getItemName());
        commandForm.setInventoryConditionName(actionForm.getInventoryConditionName());
        commandForm.setUnitOfMeasureTypeName(actionForm.getUnitOfMeasureTypeName());
        commandForm.setCurrencyIsoName(actionForm.getCurrencyIsoName());

        return ContentUtil.getHome().deleteContentCategoryItem(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CONTENT_COLLECTION_NAME, actionForm.getContentCollectionName());
        parameters.put(ParameterConstants.CONTENT_CATALOG_NAME, actionForm.getContentCatalogName());
        parameters.put(ParameterConstants.CONTENT_CATEGORY_NAME, actionForm.getContentCategoryName());
    }
    
}
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
import com.echothree.control.user.content.common.edit.ContentCategoryItemEdit;
import com.echothree.control.user.content.common.form.EditContentCategoryItemForm;
import com.echothree.control.user.content.common.result.EditContentCategoryItemResult;
import com.echothree.control.user.content.common.spec.ContentCategoryItemSpec;
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
    path = "/Content/ContentCategoryItem/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ContentCategoryItemEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Content/ContentCategoryItem/Main", redirect = true),
        @SproutForward(name = "Form", path = "/content/contentcategoryitem/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ContentCategoryItemSpec, ContentCategoryItemEdit, EditContentCategoryItemForm, EditContentCategoryItemResult> {
    
    @Override
    protected ContentCategoryItemSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ContentUtil.getHome().getContentCategoryItemSpec();
        
        spec.setContentCollectionName(findParameter(request, ParameterConstants.CONTENT_COLLECTION_NAME, actionForm.getContentCollectionName()));
        spec.setContentCatalogName(findParameter(request, ParameterConstants.CONTENT_CATALOG_NAME, actionForm.getContentCatalogName()));
        spec.setContentCategoryName(findParameter(request, ParameterConstants.CONTENT_CATEGORY_NAME, actionForm.getContentCategoryName()));
        spec.setItemName(findParameter(request, ParameterConstants.ITEM_NAME, actionForm.getItemName()));
        spec.setInventoryConditionName(findParameter(request, ParameterConstants.INVENTORY_CONDITION_NAME, actionForm.getInventoryConditionName()));
        spec.setUnitOfMeasureTypeName(findParameter(request, ParameterConstants.UNIT_OF_MEASURE_TYPE_NAME, actionForm.getUnitOfMeasureTypeName()));
        spec.setCurrencyIsoName(findParameter(request, ParameterConstants.CURRENCY_ISO_NAME, actionForm.getCurrencyIsoName()));
        
        return spec;
    }
    
    @Override
    protected ContentCategoryItemEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ContentUtil.getHome().getContentCategoryItemEdit();

        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());

        return edit;
    }
    
    @Override
    protected EditContentCategoryItemForm getForm()
            throws NamingException {
        return ContentUtil.getHome().getEditContentCategoryItemForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditContentCategoryItemResult result, ContentCategoryItemSpec spec, ContentCategoryItemEdit edit) {
        actionForm.setContentCollectionName(spec.getContentCollectionName());
        actionForm.setContentCatalogName(spec.getContentCatalogName());
        actionForm.setContentCategoryName(spec.getContentCategoryName());
        actionForm.setItemName(spec.getItemName());
        actionForm.setInventoryConditionName(spec.getInventoryConditionName());
        actionForm.setUnitOfMeasureTypeName(spec.getUnitOfMeasureTypeName());
        actionForm.setCurrencyIsoName(spec.getCurrencyIsoName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditContentCategoryItemForm commandForm)
            throws Exception {
        var commandResult = ContentUtil.getHome().editContentCategoryItem(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (EditContentCategoryItemResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.CONTENT_CATEGORY_ITEM, result.getContentCategoryItem());
        
        return commandResult;
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CONTENT_COLLECTION_NAME, actionForm.getContentCollectionName());
        parameters.put(ParameterConstants.CONTENT_CATALOG_NAME, actionForm.getContentCatalogName());
        parameters.put(ParameterConstants.CONTENT_CATEGORY_NAME, actionForm.getContentCategoryName());
    }
    
}
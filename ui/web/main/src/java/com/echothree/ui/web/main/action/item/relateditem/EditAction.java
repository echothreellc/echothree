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

package com.echothree.ui.web.main.action.item.relateditem;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.edit.RelatedItemEdit;
import com.echothree.control.user.item.common.form.EditRelatedItemForm;
import com.echothree.control.user.item.common.result.EditRelatedItemResult;
import com.echothree.control.user.item.common.spec.RelatedItemSpec;
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
    path = "/Item/RelatedItem/Edit",
    mappingClass = SecureActionMapping.class,
    name = "RelatedItemEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/RelatedItem/Main", redirect = true),
        @SproutForward(name = "Form", path = "/item/relateditem/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, RelatedItemSpec, RelatedItemEdit, EditRelatedItemForm, EditRelatedItemResult> {
    
    @Override
    protected RelatedItemSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getRelatedItemSpec();
        
        spec.setRelatedItemTypeName(findParameter(request, ParameterConstants.RELATED_ITEM_TYPE_NAME, actionForm.getRelatedItemTypeName()));
        spec.setFromItemName(findParameter(request, ParameterConstants.FROM_ITEM_NAME, actionForm.getFromItemName()));
        spec.setToItemName(findParameter(request, ParameterConstants.TO_ITEM_NAME, actionForm.getToItemName()));
        
        return spec;
    }
    
    @Override
    protected RelatedItemEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getRelatedItemEdit();

        edit.setSortOrder(actionForm.getSortOrder());

        return edit;
    }
    
    @Override
    protected EditRelatedItemForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditRelatedItemForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditRelatedItemResult result, RelatedItemSpec spec, RelatedItemEdit edit) {
        actionForm.setRelatedItemTypeName(spec.getRelatedItemTypeName());
        actionForm.setFromItemName(spec.getFromItemName());
        actionForm.setToItemName(spec.getToItemName());
        actionForm.setSortOrder(edit.getSortOrder());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditRelatedItemForm commandForm)
            throws Exception {
        var commandResult = ItemUtil.getHome().editRelatedItem(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (EditRelatedItemResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.RELATED_ITEM, result.getRelatedItem());
        
        return commandResult;
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_NAME, actionForm.getFromItemName());
    }
    
}
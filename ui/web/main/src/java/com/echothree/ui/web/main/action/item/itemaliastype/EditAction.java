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

package com.echothree.ui.web.main.action.item.itemaliastype;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.edit.ItemAliasTypeEdit;
import com.echothree.control.user.item.common.form.EditItemAliasTypeForm;
import com.echothree.control.user.item.common.result.EditItemAliasTypeResult;
import com.echothree.control.user.item.common.spec.ItemAliasTypeSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Item/ItemAliasType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ItemAliasTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemAliasType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemaliastype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ItemAliasTypeSpec, ItemAliasTypeEdit, EditItemAliasTypeForm, EditItemAliasTypeResult> {
    
    @Override
    protected ItemAliasTypeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getItemAliasTypeUniversalSpec();
        
        spec.setItemAliasTypeName(findParameter(request, ParameterConstants.ORIGINAL_ITEM_ALIAS_TYPE_NAME, actionForm.getOriginalItemAliasTypeName()));
        
        return spec;
    }
    
    @Override
    protected ItemAliasTypeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getItemAliasTypeEdit();

        edit.setItemAliasTypeName(actionForm.getItemAliasTypeName());
        edit.setValidationPattern(actionForm.getValidationPattern());
        edit.setItemAliasChecksumTypeName(actionForm.getItemAliasChecksumTypeChoice());
        edit.setAllowMultiple(actionForm.getAllowMultiple().toString());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditItemAliasTypeForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditItemAliasTypeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditItemAliasTypeResult result, ItemAliasTypeSpec spec, ItemAliasTypeEdit edit) {
        actionForm.setOriginalItemAliasTypeName(spec.getItemAliasTypeName());
        actionForm.setItemAliasTypeName(edit.getItemAliasTypeName());
        actionForm.setValidationPattern(edit.getValidationPattern());
        actionForm.setItemAliasChecksumTypeChoice(edit.getItemAliasChecksumTypeName());
        actionForm.setAllowMultiple(Boolean.valueOf(edit.getAllowMultiple()));
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditItemAliasTypeForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editItemAliasType(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditItemAliasTypeResult result) {
        request.setAttribute(AttributeConstants.ITEM_ALIAS_TYPE, result.getItemAliasType());
    }

}

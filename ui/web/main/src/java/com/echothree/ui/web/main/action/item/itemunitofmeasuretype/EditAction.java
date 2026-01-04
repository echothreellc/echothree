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

package com.echothree.ui.web.main.action.item.itemunitofmeasuretype;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.edit.ItemUnitOfMeasureTypeEdit;
import com.echothree.control.user.item.common.form.EditItemUnitOfMeasureTypeForm;
import com.echothree.control.user.item.common.result.EditItemUnitOfMeasureTypeResult;
import com.echothree.control.user.item.common.spec.ItemUnitOfMeasureTypeSpec;
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
    path = "/Item/ItemUnitOfMeasureType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ItemUnitOfMeasureTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemUnitOfMeasureType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemunitofmeasuretype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ItemUnitOfMeasureTypeSpec, ItemUnitOfMeasureTypeEdit, EditItemUnitOfMeasureTypeForm, EditItemUnitOfMeasureTypeResult> {

    @Override
    protected ItemUnitOfMeasureTypeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getItemUnitOfMeasureTypeSpec();

        spec.setItemName(findParameter(request, ParameterConstants.ITEM_NAME, actionForm.getItemName()));
        spec.setUnitOfMeasureTypeName(findParameter(request, ParameterConstants.UNIT_OF_MEASURE_TYPE_NAME, actionForm.getUnitOfMeasureTypeName()));

        return spec;
    }

    @Override
    protected ItemUnitOfMeasureTypeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getItemUnitOfMeasureTypeEdit();

        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());

        return edit;
    }

    @Override
    protected EditItemUnitOfMeasureTypeForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditItemUnitOfMeasureTypeForm();
    }

    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditItemUnitOfMeasureTypeResult result, ItemUnitOfMeasureTypeSpec spec, ItemUnitOfMeasureTypeEdit edit) {
        actionForm.setItemName(spec.getItemName());
        actionForm.setUnitOfMeasureTypeName(spec.getUnitOfMeasureTypeName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
    }

    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditItemUnitOfMeasureTypeForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editItemUnitOfMeasureType(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_NAME, actionForm.getItemName());
    }

    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditItemUnitOfMeasureTypeResult result) {
        request.setAttribute(AttributeConstants.ITEM_UNIT_OF_MEASURE_TYPE, result.getItemUnitOfMeasureType());
    }

}

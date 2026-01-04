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

package com.echothree.ui.web.main.action.item.itemweight;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.edit.ItemWeightEdit;
import com.echothree.control.user.item.common.form.EditItemWeightForm;
import com.echothree.control.user.item.common.result.EditItemWeightResult;
import com.echothree.control.user.item.common.spec.ItemWeightSpec;
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
    path = "/Item/ItemWeight/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ItemWeightEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemWeight/Main", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemweight/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ItemWeightSpec, ItemWeightEdit, EditItemWeightForm, EditItemWeightResult> {

    @Override
    protected ItemWeightSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getItemWeightSpec();

        spec.setItemName(findParameter(request, ParameterConstants.ITEM_NAME, actionForm.getItemName()));
        spec.setUnitOfMeasureTypeName(findParameter(request, ParameterConstants.UNIT_OF_MEASURE_TYPE_NAME, actionForm.getUnitOfMeasureTypeName()));
        spec.setItemWeightTypeName(findParameter(request, ParameterConstants.ITEM_WEIGHT_TYPE_NAME, actionForm.getItemWeightTypeName()));

        return spec;
    }

    @Override
    protected ItemWeightEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getItemWeightEdit();

        edit.setWeightUnitOfMeasureTypeName(actionForm.getWeightUnitOfMeasureTypeChoice());
        edit.setWeight(actionForm.getWeight());

        return edit;
    }

    @Override
    protected EditItemWeightForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditItemWeightForm();
    }

    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditItemWeightResult result, ItemWeightSpec spec, ItemWeightEdit edit) {
        actionForm.setItemName(spec.getItemName());
        actionForm.setUnitOfMeasureTypeName(spec.getUnitOfMeasureTypeName());
        actionForm.setItemWeightTypeName(spec.getItemWeightTypeName());
        actionForm.setWeightUnitOfMeasureTypeChoice(edit.getWeightUnitOfMeasureTypeName());
        actionForm.setWeight(edit.getWeight());
    }

    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditItemWeightForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editItemWeight(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_NAME, actionForm.getItemName());
    }

    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditItemWeightResult result) {
        request.setAttribute(AttributeConstants.ITEM_WEIGHT, result.getItemWeight());
    }

}

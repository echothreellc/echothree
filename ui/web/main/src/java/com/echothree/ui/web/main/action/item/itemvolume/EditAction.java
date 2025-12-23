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

package com.echothree.ui.web.main.action.item.itemvolume;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.edit.ItemVolumeEdit;
import com.echothree.control.user.item.common.form.EditItemVolumeForm;
import com.echothree.control.user.item.common.result.EditItemVolumeResult;
import com.echothree.control.user.item.common.spec.ItemVolumeSpec;
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
    path = "/Item/ItemVolume/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ItemVolumeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemVolume/Main", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemvolume/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ItemVolumeSpec, ItemVolumeEdit, EditItemVolumeForm, EditItemVolumeResult> {

    @Override
    protected ItemVolumeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getItemVolumeSpec();

        spec.setItemName(findParameter(request, ParameterConstants.ITEM_NAME, actionForm.getItemName()));
        spec.setUnitOfMeasureTypeName(findParameter(request, ParameterConstants.UNIT_OF_MEASURE_TYPE_NAME, actionForm.getUnitOfMeasureTypeName()));
        spec.setItemVolumeTypeName(findParameter(request, ParameterConstants.ITEM_VOLUME_TYPE_NAME, actionForm.getItemVolumeTypeName()));

        return spec;
    }

    @Override
    protected ItemVolumeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getItemVolumeEdit();

        edit.setHeightUnitOfMeasureTypeName(actionForm.getHeightUnitOfMeasureTypeChoice());
        edit.setHeight(actionForm.getHeight());
        edit.setWidthUnitOfMeasureTypeName(actionForm.getWidthUnitOfMeasureTypeChoice());
        edit.setWidth(actionForm.getWidth());
        edit.setDepthUnitOfMeasureTypeName(actionForm.getDepthUnitOfMeasureTypeChoice());
        edit.setDepth(actionForm.getDepth());

        return edit;
    }

    @Override
    protected EditItemVolumeForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditItemVolumeForm();
    }

    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditItemVolumeResult result, ItemVolumeSpec spec, ItemVolumeEdit edit) {
        actionForm.setItemName(spec.getItemName());
        actionForm.setUnitOfMeasureTypeName(spec.getUnitOfMeasureTypeName());
        actionForm.setItemVolumeTypeName(spec.getItemVolumeTypeName());
        actionForm.setHeightUnitOfMeasureTypeChoice(edit.getHeightUnitOfMeasureTypeName());
        actionForm.setHeight(edit.getHeight());
        actionForm.setWidthUnitOfMeasureTypeChoice(edit.getWidthUnitOfMeasureTypeName());
        actionForm.setWidth(edit.getWidth());
        actionForm.setDepthUnitOfMeasureTypeChoice(edit.getDepthUnitOfMeasureTypeName());
        actionForm.setDepth(edit.getDepth());
    }

    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditItemVolumeForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editItemVolume(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_NAME, actionForm.getItemName());
    }

    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditItemVolumeResult result) {
        request.setAttribute(AttributeConstants.ITEM_VOLUME, result.getItemVolume());
    }

}

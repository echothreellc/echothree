// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.ui.web.main.action.item.itemvolumetype;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.GetItemVolumeTypeResult;
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
    path = "/Item/ItemVolumeType/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "ItemVolumeTypeDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemVolumeType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemvolumetype/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAddAction<DescriptionAddActionForm> {

    @Override
    public void setupParameters(DescriptionAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setItemVolumeTypeName(findParameter(request, ParameterConstants.ITEM_VOLUME_TYPE_NAME, actionForm.getItemVolumeTypeName()));
    }
    
    @Override
    public void setupTransfer(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ItemUtil.getHome().getGetItemVolumeTypeForm();

        commandForm.setItemVolumeTypeName(actionForm.getItemVolumeTypeName());

        var commandResult = ItemUtil.getHome().getItemVolumeType(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetItemVolumeTypeResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.ITEM_VOLUME_TYPE, result.getItemVolumeType());
        }
    }
    
    @Override
    public CommandResult doAdd(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ItemUtil.getHome().getCreateItemVolumeTypeDescriptionForm();

        commandForm.setItemVolumeTypeName( actionForm.getItemVolumeTypeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
        commandForm.setDescription(actionForm.getDescription());

        return ItemUtil.getHome().createItemVolumeTypeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_VOLUME_TYPE_NAME, actionForm.getItemVolumeTypeName());
    }
    
}

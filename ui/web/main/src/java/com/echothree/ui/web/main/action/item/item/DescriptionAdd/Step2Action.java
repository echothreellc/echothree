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

package com.echothree.ui.web.main.action.item.item.DescriptionAdd;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.GetItemDescriptionTypeResult;
import com.echothree.control.user.item.common.result.GetItemResult;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.item.common.transfer.ItemDescriptionTypeTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAddAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Item/Item/DescriptionAdd/Step2",
    mappingClass = SecureActionMapping.class,
    name = "ItemDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/Item/Description", redirect = true),
        @SproutForward(name = "Form", path = "/item/item/descriptionAdd/step2.jsp")
    }
)
public class Step2Action
        extends MainBaseAddAction<DescriptionAddActionForm> {

    @Override
    public void setupParameters(DescriptionAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setItemName(findParameter(request, ParameterConstants.ITEM_NAME, actionForm.getItemName()));
        actionForm.setItemDescriptionTypeName(findParameter(request, ParameterConstants.ITEM_DESCRIPTION_TYPE_NAME, actionForm.getItemDescriptionTypeName()));
    }

    private void setupItemTransfer(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ItemUtil.getHome().getGetItemForm();

        commandForm.setItemName(actionForm.getItemName());

        var commandResult = ItemUtil.getHome().getItem(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetItemResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.ITEM, result.getItem());
    }

    private ItemDescriptionTypeTransfer getItemDescriptionTypeTransfer(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ItemUtil.getHome().getGetItemDescriptionTypeForm();

        commandForm.setItemDescriptionTypeName(actionForm.getItemDescriptionTypeName());

        var commandResult = ItemUtil.getHome().getItemDescriptionType(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetItemDescriptionTypeResult)executionResult.getResult();

        return result.getItemDescriptionType();
    }

    private void setupItemDescriptionTypeTransfers(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        request.setAttribute(AttributeConstants.ITEM_DESCRIPTION_TYPE, getItemDescriptionTypeTransfer(actionForm, request));
    }

    @Override
    public void setupTransfer(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        setupItemTransfer(actionForm, request);
        setupItemDescriptionTypeTransfers(actionForm, request);
    }

    @Override
    public CommandResult doAdd(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var itemDescriptionType = getItemDescriptionTypeTransfer(actionForm, request);
        var commandForm = ItemUtil.getHome().getCreateItemDescriptionForm();
        var mimeTypeUsageType = itemDescriptionType.getMimeTypeUsageType();

        commandForm.setItemName(actionForm.getItemName());
        commandForm.setItemDescriptionTypeName(actionForm.getItemDescriptionTypeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageChoice());

        if(mimeTypeUsageType == null) {
            commandForm.setStringDescription(actionForm.getStringDescription());
        } else {
            var mimeTypeUsageTypeName = mimeTypeUsageType.getMimeTypeUsageTypeName();

            if(mimeTypeUsageTypeName.equals(MimeTypeUsageTypes.TEXT.name())) {
                commandForm.setMimeTypeName(actionForm.getMimeTypeChoice());
                commandForm.setClobDescription(actionForm.getClobDescription());
            } else {
                var blobDescription = actionForm.getBlobDescription();

                commandForm.setMimeTypeName(blobDescription.getContentType());
                commandForm.setItemImageTypeName(actionForm.getItemImageTypeChoice());

                try {
                    commandForm.setBlobDescription(new ByteArray(blobDescription.getFileData()));
                } catch(FileNotFoundException fnfe) {
                    // Leave blobDescription null.
                } catch(IOException ioe) {
                    // Leave blobDescription null.
                }
            }
        }

        return ItemUtil.getHome().createItemDescription(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(DescriptionAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_NAME, actionForm.getItemName());
    }
    
}
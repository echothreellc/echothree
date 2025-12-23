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

package com.echothree.ui.web.main.action.item.item;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.edit.ItemDescriptionEdit;
import com.echothree.control.user.item.common.form.EditItemDescriptionForm;
import com.echothree.control.user.item.common.result.EditItemDescriptionResult;
import com.echothree.control.user.item.common.result.GetItemDescriptionTypeResult;
import com.echothree.control.user.item.common.spec.ItemDescriptionSpec;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.item.common.transfer.ItemDescriptionTypeTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
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
    path = "/Item/Item/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "ItemDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/Item/Description", redirect = true),
        @SproutForward(name = "Form", path = "/item/item/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, ItemDescriptionSpec, ItemDescriptionEdit, EditItemDescriptionForm, EditItemDescriptionResult> {

    @Override
    protected ItemDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getItemDescriptionSpec();

        spec.setItemDescriptionTypeName(findParameter(request, ParameterConstants.ITEM_DESCRIPTION_TYPE_NAME, actionForm.getItemDescriptionTypeName()));
        spec.setItemName(findParameter(request, ParameterConstants.ITEM_NAME, actionForm.getItemName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));

        return spec;
    }

    private ItemDescriptionTypeTransfer getItemDescriptionTypeTransfer(DescriptionEditActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        ItemDescriptionTypeTransfer itemDescriptionType = null;
        var commandForm = ItemUtil.getHome().getGetItemDescriptionTypeForm();

        commandForm.setItemDescriptionTypeName(actionForm.getItemDescriptionTypeName());

        var commandResult = ItemUtil.getHome().getItemDescriptionType(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetItemDescriptionTypeResult)executionResult.getResult();

            itemDescriptionType = result.getItemDescriptionType();
        }

        return itemDescriptionType;
    }

    @Override
    protected ItemDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getItemDescriptionEdit();
        var itemDescriptionType = getItemDescriptionTypeTransfer(actionForm, request);
        var mimeTypeUsageType = itemDescriptionType.getMimeTypeUsageType();

        if(mimeTypeUsageType == null) {
            edit.setStringDescription(actionForm.getStringDescription());
        } else {
            var mimeTypeUsageTypeName = mimeTypeUsageType.getMimeTypeUsageTypeName();

            if(mimeTypeUsageTypeName.equals(MimeTypeUsageTypes.TEXT.name())) {
                edit.setMimeTypeName(actionForm.getMimeTypeChoice());
                edit.setClobDescription(actionForm.getClobDescription());
            } else {
                var blobDescription = actionForm.getBlobDescription();

                edit.setMimeTypeName(blobDescription.getContentType());
                if(mimeTypeUsageTypeName.equals(MimeTypeUsageTypes.IMAGE.name())) {
                    edit.setItemImageTypeName(actionForm.getItemImageTypeChoice());
                }

                try {
                    edit.setBlobDescription(new ByteArray(blobDescription.getFileData()));
                } catch(FileNotFoundException fnfe) {
                    // Leave blobDescription null.
                } catch(IOException ioe) {
                    // Leave blobDescription null.
                }
            }
        }

        return edit;
    }

    @Override
    protected EditItemDescriptionForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditItemDescriptionForm();
    }

    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditItemDescriptionResult result, ItemDescriptionSpec spec, ItemDescriptionEdit edit)
            throws NamingException {
        var itemDescriptionType = result.getItemDescription().getItemDescriptionType();
        var mimeTypeUsageType = itemDescriptionType.getMimeTypeUsageType();

        actionForm.setItemDescriptionTypeName(spec.getItemDescriptionTypeName());
        actionForm.setItemName(spec.getItemName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());

        if(mimeTypeUsageType == null) {
            actionForm.setStringDescription(edit.getStringDescription());
        } else {
            var mimeTypeUsageTypeName = mimeTypeUsageType.getMimeTypeUsageTypeName();

            if(mimeTypeUsageTypeName.equals(MimeTypeUsageTypes.TEXT.name())) {
                actionForm.setMimeTypeChoice(edit.getMimeTypeName());
                actionForm.setClobDescription(edit.getClobDescription());
            } else if(mimeTypeUsageTypeName.equals(MimeTypeUsageTypes.IMAGE.name())) {
                actionForm.setItemImageTypeChoice(edit.getItemImageTypeName());
            }
        }
    }

    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditItemDescriptionForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editItemDescription(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_NAME, actionForm.getItemName());
    }

    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditItemDescriptionResult result) {
        request.setAttribute(AttributeConstants.ITEM_DESCRIPTION, result.getItemDescription());
    }
    
}
// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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
import com.echothree.control.user.item.common.form.GetItemDescriptionForm;
import com.echothree.control.user.item.common.result.GetItemDescriptionResult;
import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.common.transfer.MimeTypeFileExtensionTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.item.common.ItemOptions;
import com.echothree.model.control.item.common.transfer.ItemDescriptionTransfer;
import com.echothree.ui.web.main.framework.ByteArrayStreamInfo;
import com.echothree.ui.web.main.framework.DispositionConstants;
import com.echothree.ui.web.main.framework.MainBaseDownloadAction;
import com.echothree.ui.web.main.framework.MainBaseDownloadAction.StreamInfo;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Item/Item/ItemBlobDescriptionView",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    }
)
public class ItemBlobDescriptionViewAction
        extends MainBaseDownloadAction {
    
    private String getDefaultMimeTypeFileExtension(List<MimeTypeFileExtensionTransfer> mimeTypeFileExtensions) {
        MimeTypeFileExtensionTransfer defaultMimeTypeFileExtension = null;
        
        for(MimeTypeFileExtensionTransfer mimeTypeFileExtension : mimeTypeFileExtensions) {
            if(mimeTypeFileExtension.getIsDefault()) {
                defaultMimeTypeFileExtension = mimeTypeFileExtension;
            }
        }
        
        // "bin" is the default extension of no other default is found.
        return defaultMimeTypeFileExtension == null ? "bin" : defaultMimeTypeFileExtension.getFileExtension();
    }
    
    @Override
    protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        StreamInfo streamInfo = null;
        String disposition = request.getParameter(ParameterConstants.DISPOSITION);
        GetItemDescriptionForm commandForm = ItemUtil.getHome().getGetItemDescriptionForm();

        commandForm.setItemDescriptionTypeName(request.getParameter(ParameterConstants.ITEM_DESCRIPTION_TYPE_NAME));
        commandForm.setItemName(request.getParameter(ParameterConstants.ITEM_NAME));
        commandForm.setLanguageIsoName(request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME));
        commandForm.setReferrer(request.getHeader("Referer"));

        Set<String> options = new HashSet<>();
        options.add(ItemOptions.ItemDescriptionIncludeBlob);
        options.add(CoreOptions.MimeTypeIncludeMimeTypeFileExtensions);
        commandForm.setOptions(options);

        CommandResult commandResult = ItemUtil.getHome().getItemDescription(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetItemDescriptionResult result = (GetItemDescriptionResult)executionResult.getResult();

            ItemDescriptionTransfer itemDescription = result.getItemDescription();

            if(itemDescription != null) {
                MimeTypeTransfer mimeType = itemDescription.getMimeType();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(itemDescription.getBlobDescription().byteArrayValue());

                if(disposition == null || !disposition.equals(DispositionConstants.ATTACHMENT)) {
                    streamInfo = new ByteArrayStreamInfo(mimeType.getMimeTypeName(), byteArrayInputStream, null, null);
                } else {
                    String itemName = itemDescription.getItem().getItemName();
                    String itemDescriptionTypeName = itemDescription.getItemDescriptionType().getItemDescriptionTypeName();
                    String fileExtension = getDefaultMimeTypeFileExtension(mimeType.getMimeTypeFileExtensions().getList());

                    streamInfo = new ByteArrayStreamInfo(mimeType.getMimeTypeName(), byteArrayInputStream, DispositionConstants.ATTACHMENT, itemName + " - " + itemDescriptionTypeName + "." + fileExtension);
                }
            }
        }

        return streamInfo;
    }

}

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

package com.echothree.ui.web.main.action.purchasing.vendordocument.add;

import com.echothree.control.user.document.common.DocumentUtil;
import com.echothree.control.user.document.common.result.GetDocumentTypeResult;
import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.common.result.GetVendorResult;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.document.common.transfer.DocumentTypeTransfer;
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
    path = "/Purchasing/VendorDocument/Add/Step3",
    mappingClass = SecureActionMapping.class,
    name = "VendorDocumentAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Purchasing/VendorDocument/Main", redirect = true),
        @SproutForward(name = "Form", path = "/purchasing/vendordocument/add/step3.jsp")
    }
)
public class Step3Action
        extends MainBaseAddAction<Step3ActionForm> {

    @Override
    public void setupParameters(Step3ActionForm actionForm, HttpServletRequest request) {
        actionForm.setPartyName(findParameter(request, ParameterConstants.PARTY_NAME, actionForm.getPartyName()));
        actionForm.setDocumentTypeName(findParameter(request, ParameterConstants.DOCUMENT_TYPE_NAME, actionForm.getDocumentTypeName()));
    }

    @Override
    public void setupDefaults(Step3ActionForm actionForm)
            throws NamingException {
        actionForm.setSortOrder("1");
    }
    
    private void setupVendorTransfer(Step3ActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = VendorUtil.getHome().getGetVendorForm();

        commandForm.setPartyName(actionForm.getPartyName());

        var commandResult = VendorUtil.getHome().getVendor(getUserVisitPK(request), commandForm);

        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetVendorResult)executionResult.getResult();
            var vendor = result.getVendor();

            if(vendor != null) {
                request.setAttribute(AttributeConstants.VENDOR, vendor);
            }
        }
    }

    private DocumentTypeTransfer getDocumentTypeTransfer(Step3ActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = DocumentUtil.getHome().getGetDocumentTypeForm();

        commandForm.setDocumentTypeName(actionForm.getDocumentTypeName());

        var commandResult = DocumentUtil.getHome().getDocumentType(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetDocumentTypeResult)executionResult.getResult();

        return result.getDocumentType();
    }

    private void setupDocumentTypeTransfers(Step3ActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        request.setAttribute(AttributeConstants.DOCUMENT_TYPE, getDocumentTypeTransfer(actionForm, request));
    }

    @Override
    public void setupTransfer(Step3ActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        setupVendorTransfer(actionForm, request);
        setupDocumentTypeTransfers(actionForm, request);
    }

    @Override
    public CommandResult doAdd(Step3ActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = DocumentUtil.getHome().getCreatePartyDocumentForm();
        var documentType = getDocumentTypeTransfer(actionForm, request);
        var mimeTypeUsageTypeName = documentType.getMimeTypeUsageType().getMimeTypeUsageTypeName();

        commandForm.setPartyName(actionForm.getPartyName());
        commandForm.setDocumentTypeName(actionForm.getDocumentTypeName());
        commandForm.setIsDefault(actionForm.getIsDefault().toString());
        commandForm.setSortOrder(actionForm.getSortOrder());
        commandForm.setDescription(actionForm.getDescription());

        if(mimeTypeUsageTypeName.equals(MimeTypeUsageTypes.TEXT.name())) {
            commandForm.setMimeTypeName(actionForm.getMimeTypeChoice());
            commandForm.setClob(actionForm.getClob());
        } else {
            var blob = actionForm.getBlob();

            commandForm.setMimeTypeName(blob.getContentType());

            try {
                commandForm.setBlob(new ByteArray(blob.getFileData()));
            } catch(FileNotFoundException fnfe) {
                // Leave blobDescription null.
            } catch(IOException ioe) {
                // Leave blobDescription null.
            }
        }

        return DocumentUtil.getHome().createPartyDocument(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(Step3ActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_NAME, actionForm.getPartyName());
    }
    
}
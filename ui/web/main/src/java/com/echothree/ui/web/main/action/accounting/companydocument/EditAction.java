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

package com.echothree.ui.web.main.action.accounting.companydocument;

import com.echothree.control.user.document.common.DocumentUtil;
import com.echothree.control.user.document.common.edit.PartyDocumentEdit;
import com.echothree.control.user.document.common.form.EditPartyDocumentForm;
import com.echothree.control.user.document.common.result.EditPartyDocumentResult;
import com.echothree.control.user.document.common.spec.DocumentSpec;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.document.common.transfer.PartyDocumentTransfer;
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
    path = "/Accounting/CompanyDocument/Edit",
    mappingClass = SecureActionMapping.class,
    name = "CompanyDocumentEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Accounting/CompanyDocument/Main", redirect = true),
        @SproutForward(name = "Form", path = "/accounting/companydocument/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, DocumentSpec, PartyDocumentEdit, EditPartyDocumentForm, EditPartyDocumentResult> {

    @Override
    protected DocumentSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = DocumentUtil.getHome().getDocumentSpec();

        actionForm.setPartyName(findParameter(request, ParameterConstants.PARTY_NAME, actionForm.getPartyName()));
        spec.setDocumentName(findParameter(request, ParameterConstants.DOCUMENT_NAME, actionForm.getDocumentName()));

        return spec;
    }

    private PartyDocumentTransfer getPartyDocumentTransfer(EditActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        return BaseCompanyDocumentAction.setupPartyDocumentTransfer(request, actionForm.getDocumentName(), null);
    }

    private PartyDocumentTransfer getPartyDocumentTransfer(DocumentSpec spec, HttpServletRequest request)
            throws NamingException {
        return BaseCompanyDocumentAction.setupPartyDocumentTransfer(request, spec.getDocumentName(), null);
    }

    @Override
    protected PartyDocumentEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = DocumentUtil.getHome().getPartyDocumentEdit();
        var partyDocument = getPartyDocumentTransfer(actionForm, request);
        var mimeTypeUsageType = partyDocument.getDocument().getDocumentType().getMimeTypeUsageType();
        var mimeTypeUsageTypeName = mimeTypeUsageType.getMimeTypeUsageTypeName();

        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        if(mimeTypeUsageTypeName.equals(MimeTypeUsageTypes.TEXT.name())) {
            edit.setMimeTypeName(actionForm.getMimeTypeChoice());
            edit.setClob(actionForm.getClob());
        } else {
            var blob = actionForm.getBlob();

            edit.setMimeTypeName(blob.getContentType());

            try {
                edit.setBlob(new ByteArray(blob.getFileData()));
            } catch(FileNotFoundException fnfe) {
                // Leave blob null.
            } catch(IOException ioe) {
                // Leave blob null.
            }
        }
        
        return edit;
    }

    @Override
    protected EditPartyDocumentForm getForm()
            throws NamingException {
        return DocumentUtil.getHome().getEditPartyDocumentForm();
    }

    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditPartyDocumentResult result, DocumentSpec spec, PartyDocumentEdit edit)
            throws NamingException {
        var partyDocument = getPartyDocumentTransfer(spec, request);
        var mimeTypeUsageType = partyDocument.getDocument().getDocumentType().getMimeTypeUsageType();

        actionForm.setDocumentName(spec.getDocumentName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());

        if(mimeTypeUsageType.getMimeTypeUsageTypeName().equals(MimeTypeUsageTypes.TEXT.name())) {
            actionForm.setMimeTypeChoice(edit.getMimeTypeName());
            actionForm.setClob(edit.getClob());
        }
    }

    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditPartyDocumentForm commandForm)
            throws Exception {
        return DocumentUtil.getHome().editPartyDocument(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_NAME, actionForm.getPartyName());
    }

    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditPartyDocumentResult result)
            throws NamingException {
        request.setAttribute(AttributeConstants.PARTY_DOCUMENT, result.getPartyDocument());
        BaseCompanyDocumentAction.setupCompany(request, actionForm.getPartyName());
    }
    
}
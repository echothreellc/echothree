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

package com.echothree.ui.web.main.action.customer.customerdocument;

import com.echothree.control.user.document.common.DocumentUtil;
import com.echothree.control.user.document.common.edit.DocumentDescriptionEdit;
import com.echothree.control.user.document.common.form.EditDocumentDescriptionForm;
import com.echothree.control.user.document.common.result.EditDocumentDescriptionResult;
import com.echothree.control.user.document.common.spec.DocumentDescriptionSpec;
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
    path = "/Customer/CustomerDocument/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "CustomerDocumentDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Customer/CustomerDocument/Description", redirect = true),
        @SproutForward(name = "Form", path = "/customer/customerdocument/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, DocumentDescriptionSpec, DocumentDescriptionEdit, EditDocumentDescriptionForm, EditDocumentDescriptionResult> {
    
    @Override
    protected DocumentDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = DocumentUtil.getHome().getDocumentDescriptionSpec();
        
        actionForm.setPartyName(findParameter(request, ParameterConstants.PARTY_NAME, actionForm.getPartyName()));
        spec.setDocumentName(findParameter(request, ParameterConstants.DOCUMENT_NAME, actionForm.getDocumentName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected DocumentDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = DocumentUtil.getHome().getDocumentDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditDocumentDescriptionForm getForm()
            throws NamingException {
        return DocumentUtil.getHome().getEditDocumentDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditDocumentDescriptionResult result, DocumentDescriptionSpec spec, DocumentDescriptionEdit edit) {
        actionForm.setDocumentName(spec.getDocumentName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditDocumentDescriptionForm commandForm)
            throws Exception {
        return DocumentUtil.getHome().editDocumentDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_NAME, actionForm.getPartyName());
        parameters.put(ParameterConstants.DOCUMENT_NAME, actionForm.getDocumentName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditDocumentDescriptionResult result)
            throws NamingException {
        request.setAttribute(AttributeConstants.DOCUMENT_DESCRIPTION, result.getDocumentDescription());
        BaseCustomerDocumentAction.setupCustomer(request, actionForm.getPartyName());
    }

}

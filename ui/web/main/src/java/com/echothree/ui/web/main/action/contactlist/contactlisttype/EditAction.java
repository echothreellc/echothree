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

package com.echothree.ui.web.main.action.contactlist.contactlisttype;

import com.echothree.control.user.contactlist.common.ContactListUtil;
import com.echothree.control.user.contactlist.common.edit.ContactListTypeEdit;
import com.echothree.control.user.contactlist.common.form.EditContactListTypeForm;
import com.echothree.control.user.contactlist.common.result.EditContactListTypeResult;
import com.echothree.control.user.contactlist.common.spec.ContactListTypeSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/ContactList/ContactListType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ContactListTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/ContactList/ContactListType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/contactlist/contactlisttype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ContactListTypeSpec, ContactListTypeEdit, EditContactListTypeForm, EditContactListTypeResult> {
    
    @Override
    protected ContactListTypeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ContactListUtil.getHome().getContactListTypeSpec();
        
        spec.setContactListTypeName(findParameter(request, ParameterConstants.ORIGINAL_CONTACT_LIST_TYPE_NAME, actionForm.getOriginalContactListTypeName()));
        
        return spec;
    }
    
    @Override
    protected ContactListTypeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ContactListUtil.getHome().getContactListTypeEdit();

        edit.setContactListTypeName(actionForm.getContactListTypeName());
        edit.setConfirmationRequestChainName(actionForm.getConfirmationRequestChainChoice());
        edit.setSubscribeChainName(actionForm.getSubscribeChainChoice());
        edit.setUnsubscribeChainName(actionForm.getUnsubscribeChainChoice());
        edit.setUsedForSolicitation(actionForm.getUsedForSolicitation().toString());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditContactListTypeForm getForm()
            throws NamingException {
        return ContactListUtil.getHome().getEditContactListTypeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditContactListTypeResult result, ContactListTypeSpec spec, ContactListTypeEdit edit) {
        actionForm.setOriginalContactListTypeName(spec.getContactListTypeName());
        actionForm.setContactListTypeName(edit.getContactListTypeName());
        actionForm.setConfirmationRequestChainChoice(edit.getConfirmationRequestChainName());
        actionForm.setSubscribeChainChoice(edit.getSubscribeChainName());
        actionForm.setUnsubscribeChainChoice(edit.getUnsubscribeChainName());
        actionForm.setUsedForSolicitation(Boolean.valueOf(edit.getUsedForSolicitation()));
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditContactListTypeForm commandForm)
            throws Exception {
        return ContactListUtil.getHome().editContactListType(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditContactListTypeResult result) {
        request.setAttribute(AttributeConstants.CONTACT_LIST_TYPE, result.getContactListType());
    }

}

// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.ui.web.main.action.purchasing.vendorcontactmechanism;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.edit.ContactEmailAddressEdit;
import com.echothree.control.user.contact.common.form.EditContactEmailAddressForm;
import com.echothree.control.user.contact.common.result.EditContactEmailAddressResult;
import com.echothree.control.user.contact.common.spec.PartyContactMechanismSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Purchasing/VendorContactMechanism/ContactEmailAddressEdit",
    mappingClass = SecureActionMapping.class,
    name = "VendorContactEmailAddressEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Purchasing/VendorContactMechanism/Main", redirect = true),
        @SproutForward(name = "Form", path = "/purchasing/vendorcontactmechanism/contactEmailAddressEdit.jsp")
    }
)
public class ContactEmailAddressEditAction
        extends BaseVendorContactMechanismAction<ContactEmailAddressEditActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ContactEmailAddressEditActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        String partyName = request.getParameter(ParameterConstants.PARTY_NAME);
        String contactMechanismName = request.getParameter(ParameterConstants.CONTACT_MECHANISM_NAME);
        EditContactEmailAddressForm commandForm = ContactUtil.getHome().getEditContactEmailAddressForm();
        PartyContactMechanismSpec spec = ContactUtil.getHome().getPartyContactMechanismSpec();

        if(partyName == null) {
            partyName = actionForm.getPartyName();
        }
        if(contactMechanismName == null) {
            contactMechanismName = actionForm.getContactMechanismName();
        }

        commandForm.setSpec(spec);
        spec.setPartyName(partyName);
        spec.setContactMechanismName(contactMechanismName);

        if(wasPost(request)) {
            boolean wasCanceled = wasCanceled(request);

            if(wasCanceled) {
                commandForm.setEditMode(EditMode.ABANDON);
            } else {
                ContactEmailAddressEdit edit = ContactUtil.getHome().getContactEmailAddressEdit();

                commandForm.setEditMode(EditMode.UPDATE);
                commandForm.setEdit(edit);

                edit.setAllowSolicitation(actionForm.getAllowSolicitation().toString());
                edit.setEmailAddress(actionForm.getEmailAddress());
                edit.setDescription(actionForm.getDescription());
            }

            CommandResult commandResult = ContactUtil.getHome().editContactEmailAddress(getUserVisitPK(request), commandForm);

            if(commandResult.hasErrors() && !wasCanceled) {
                ExecutionResult executionResult = commandResult.getExecutionResult();

                if(executionResult != null) {
                    EditContactEmailAddressResult result = (EditContactEmailAddressResult)executionResult.getResult();

                    request.setAttribute(AttributeConstants.CONTACT_MECHANISM, result.getContactMechanism());
                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }

                setCommandResultAttribute(request, commandResult);

                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);

            CommandResult commandResult = ContactUtil.getHome().editContactEmailAddress(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            EditContactEmailAddressResult result = (EditContactEmailAddressResult)executionResult.getResult();

            if(result != null) {
                ContactEmailAddressEdit edit = result.getEdit();

                if(edit != null) {
                    actionForm.setPartyName(partyName);
                    actionForm.setContactMechanismName(contactMechanismName);
                    actionForm.setAllowSolicitation(Boolean.valueOf(edit.getAllowSolicitation()));
                    actionForm.setEmailAddress(edit.getEmailAddress());
                    actionForm.setDescription(edit.getDescription());
                }

                request.setAttribute(AttributeConstants.CONTACT_MECHANISM, result.getContactMechanism());
                request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
            }

            setCommandResultAttribute(request, commandResult);

            forwardKey = ForwardConstants.FORM;
        }

        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            setupVendor(request, partyName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);

            parameters.put(ParameterConstants.PARTY_NAME, partyName);
            customActionForward.setParameters(parameters);
        }

        return customActionForward;
    }

}
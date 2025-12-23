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

package com.echothree.ui.web.main.action.accounting.divisioncontactmechanism;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.result.EditContactTelephoneResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.EditMode;
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
    path = "/Accounting/DivisionContactMechanism/ContactTelephoneEdit",
    mappingClass = SecureActionMapping.class,
    name = "DivisionContactTelephoneEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Accounting/DivisionContactMechanism/Main", redirect = true),
        @SproutForward(name = "Form", path = "/accounting/divisioncontactmechanism/contactTelephoneEdit.jsp")
    }
)
public class ContactTelephoneEditAction
        extends BaseDivisionContactMechanismAction<ContactTelephoneEditActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ContactTelephoneEditActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var partyName = request.getParameter(ParameterConstants.PARTY_NAME);
        var contactMechanismName = request.getParameter(ParameterConstants.CONTACT_MECHANISM_NAME);
        var commandForm = ContactUtil.getHome().getEditContactTelephoneForm();
        var spec = ContactUtil.getHome().getPartyContactMechanismSpec();

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
            var wasCanceled = wasCanceled(request);

            if(wasCanceled) {
                commandForm.setEditMode(EditMode.ABANDON);
            } else {
                var edit = ContactUtil.getHome().getContactTelephoneEdit();

                commandForm.setEditMode(EditMode.UPDATE);
                commandForm.setEdit(edit);

                edit.setAllowSolicitation(actionForm.getAllowSolicitation().toString());
                edit.setCountryName(actionForm.getCountryName());
                edit.setAreaCode(actionForm.getAreaCode());
                edit.setTelephoneNumber(actionForm.getTelephoneNumber());
                edit.setTelephoneExtension(actionForm.getTelephoneExtension());
                edit.setDescription(actionForm.getDescription());
            }

            var commandResult = ContactUtil.getHome().editContactTelephone(getUserVisitPK(request), commandForm);

            if(commandResult.hasErrors() && !wasCanceled) {
                var executionResult = commandResult.getExecutionResult();

                if(executionResult != null) {
                    var result = (EditContactTelephoneResult)executionResult.getResult();

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

            var commandResult = ContactUtil.getHome().editContactTelephone(getUserVisitPK(request), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (EditContactTelephoneResult)executionResult.getResult();

            if(result != null) {
                var edit = result.getEdit();

                if(edit != null) {
                    actionForm.setPartyName(partyName);
                    actionForm.setContactMechanismName(contactMechanismName);
                    actionForm.setAllowSolicitation(Boolean.valueOf(edit.getAllowSolicitation()));
                    actionForm.setCountryName(edit.getCountryName());
                    actionForm.setAreaCode(edit.getAreaCode());
                    actionForm.setTelephoneNumber(edit.getTelephoneNumber());
                    actionForm.setTelephoneExtension(edit.getTelephoneExtension());
                    actionForm.setDescription(edit.getDescription());
                }

                request.setAttribute(AttributeConstants.CONTACT_MECHANISM, result.getContactMechanism());
                request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
            }

            setCommandResultAttribute(request, commandResult);

            forwardKey = ForwardConstants.FORM;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            setupDivision(request, partyName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);

            parameters.put(ParameterConstants.PARTY_NAME, partyName);
            customActionForward.setParameters(parameters);
        }

        return customActionForward;
    }

}
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

package com.echothree.ui.web.main.action.shipping.carriercontactmechanism;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.result.EditContactPostalAddressResult;
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
    path = "/Shipping/CarrierContactMechanism/ContactPostalAddressEdit",
    mappingClass = SecureActionMapping.class,
    name = "CarrierContactPostalAddressEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Shipping/CarrierContactMechanism/Main", redirect = true),
        @SproutForward(name = "Form", path = "/shipping/carriercontactmechanism/contactPostalAddressEdit.jsp")
    }
)
public class ContactPostalAddressEditAction
        extends BaseCarrierContactMechanismAction<ContactPostalAddressEditActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ContactPostalAddressEditActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var partyName = request.getParameter(ParameterConstants.PARTY_NAME);
        var contactMechanismName = request.getParameter(ParameterConstants.CONTACT_MECHANISM_NAME);
        var commandForm = ContactUtil.getHome().getEditContactPostalAddressForm();
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
                var edit = ContactUtil.getHome().getContactPostalAddressEdit();

                commandForm.setEditMode(EditMode.UPDATE);
                commandForm.setEdit(edit);

                edit.setAllowSolicitation(actionForm.getAllowSolicitation().toString());
                edit.setPersonalTitleId(actionForm.getPersonalTitleChoice());
                edit.setFirstName(actionForm.getFirstName());
                edit.setMiddleName(actionForm.getMiddleName());
                edit.setLastName(actionForm.getLastName());
                edit.setNameSuffixId(actionForm.getNameSuffixChoice());
                edit.setCompanyName(actionForm.getCompanyName());
                edit.setAttention(actionForm.getAttention());
                edit.setAddress1(actionForm.getAddress1());
                edit.setAddress2(actionForm.getAddress2());
                edit.setAddress3(actionForm.getAddress3());
                edit.setCity(actionForm.getCity());
                edit.setState(actionForm.getState());
                edit.setPostalCode(actionForm.getPostalCode());
                edit.setCountryName(actionForm.getCountryName());
                edit.setIsCommercial(actionForm.getIsCommercial().toString());
                edit.setDescription(actionForm.getDescription());
            }

            var commandResult = ContactUtil.getHome().editContactPostalAddress(getUserVisitPK(request), commandForm);

            if(commandResult.hasErrors() && !wasCanceled) {
                var executionResult = commandResult.getExecutionResult();

                if(executionResult != null) {
                    var result = (EditContactPostalAddressResult)executionResult.getResult();

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

            var commandResult = ContactUtil.getHome().editContactPostalAddress(getUserVisitPK(request), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (EditContactPostalAddressResult)executionResult.getResult();

            if(result != null) {
                var edit = result.getEdit();

                if(edit != null) {
                    actionForm.setPartyName(partyName);
                    actionForm.setContactMechanismName(contactMechanismName);
                    actionForm.setAllowSolicitation(Boolean.valueOf(edit.getAllowSolicitation()));
                    actionForm.setPersonalTitleChoice(edit.getPersonalTitleId());
                    actionForm.setFirstName(edit.getFirstName());
                    actionForm.setMiddleName(edit.getMiddleName());
                    actionForm.setLastName(edit.getLastName());
                    actionForm.setNameSuffixChoice(edit.getNameSuffixId());
                    actionForm.setCompanyName(edit.getCompanyName());
                    actionForm.setAttention(edit.getAttention());
                    actionForm.setAddress1(edit.getAddress1());
                    actionForm.setAddress2(edit.getAddress2());
                    actionForm.setAddress3(edit.getAddress3());
                    actionForm.setCity(edit.getCity());
                    actionForm.setState(edit.getState());
                    actionForm.setPostalCode(edit.getPostalCode());
                    actionForm.setCountryName(edit.getCountryName());
                    actionForm.setIsCommercial(Boolean.valueOf(edit.getIsCommercial()));
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
            setupCarrier(request, partyName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);

            parameters.put(ParameterConstants.PARTY_NAME, partyName);
            customActionForward.setParameters(parameters);
        }

        return customActionForward;
    }

}
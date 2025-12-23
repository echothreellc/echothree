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

package com.echothree.ui.web.main.action.employee.employeeentitytype;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.edit.PartyEntityTypeEdit;
import com.echothree.control.user.party.common.form.EditPartyEntityTypeForm;
import com.echothree.control.user.party.common.result.EditPartyEntityTypeResult;
import com.echothree.control.user.party.common.spec.PartyEntityTypeSpec;
import com.echothree.ui.web.main.action.humanresources.employeeentitytype.BaseEmployeeEntityTypeAction;
import com.echothree.ui.web.main.action.humanresources.employeeentitytype.EditActionForm;
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
    path = "/Employee/EmployeeEntityType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "EmployeeEntityTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Employee/EmployeeEntityType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/employee/employeeentitytype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, PartyEntityTypeSpec, PartyEntityTypeEdit, EditPartyEntityTypeForm, EditPartyEntityTypeResult> {

    @Override
    protected PartyEntityTypeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = PartyUtil.getHome().getPartyEntityTypeSpec();

        spec.setComponentVendorName(findParameter(request, ParameterConstants.COMPONENT_VENDOR_NAME, actionForm.getComponentVendorName()));
        spec.setEntityTypeName(findParameter(request, ParameterConstants.ENTITY_TYPE_NAME, actionForm.getEntityTypeName()));

        return spec;
    }

    @Override
    protected PartyEntityTypeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = PartyUtil.getHome().getPartyEntityTypeEdit();

        edit.setConfirmDelete(actionForm.getConfirmDelete().toString());

        return edit;
    }

    @Override
    protected EditPartyEntityTypeForm getForm()
            throws NamingException {
        return PartyUtil.getHome().getEditPartyEntityTypeForm();
    }

    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditPartyEntityTypeResult result, PartyEntityTypeSpec spec, PartyEntityTypeEdit edit)
            throws NamingException {
        actionForm.setComponentVendorName(spec.getComponentVendorName());
        actionForm.setEntityTypeName(spec.getEntityTypeName());
        actionForm.setConfirmDelete(Boolean.valueOf(edit.getConfirmDelete()));
    }

    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditPartyEntityTypeForm commandForm)
            throws Exception {
        return PartyUtil.getHome().editPartyEntityType(getUserVisitPK(request), commandForm);
    }

    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditPartyEntityTypeResult result)
            throws NamingException {
        request.setAttribute(AttributeConstants.PARTY_ENTITY_TYPE, result.getPartyEntityType());
        BaseEmployeeEntityTypeAction.setupEmployee(request, null);
    }
    
}
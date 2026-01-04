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

package com.echothree.ui.web.main.action.employee.employeeapplicationeditoruse;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.edit.PartyApplicationEditorUseEdit;
import com.echothree.control.user.party.common.form.EditPartyApplicationEditorUseForm;
import com.echothree.control.user.party.common.result.EditPartyApplicationEditorUseResult;
import com.echothree.control.user.party.common.spec.PartyApplicationEditorUseSpec;
import com.echothree.ui.web.main.action.humanresources.employeeapplicationeditoruse.BaseEmployeeApplicationEditorUseAction;
import com.echothree.ui.web.main.action.humanresources.employeeapplicationeditoruse.EditActionForm;
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
    path = "/Employee/EmployeeApplicationEditorUse/Edit",
    mappingClass = SecureActionMapping.class,
    name = "EmployeeApplicationEditorUseEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Employee/EmployeeApplicationEditorUse/Main", redirect = true),
        @SproutForward(name = "Form", path = "/employee/employeeapplicationeditoruse/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, PartyApplicationEditorUseSpec, PartyApplicationEditorUseEdit, EditPartyApplicationEditorUseForm, EditPartyApplicationEditorUseResult> {

    @Override
    protected PartyApplicationEditorUseSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = PartyUtil.getHome().getPartyApplicationEditorUseSpec();

        spec.setApplicationName(findParameter(request, ParameterConstants.APPLICATION_NAME, actionForm.getApplicationName()));
        spec.setApplicationEditorUseName(findParameter(request, ParameterConstants.APPLICATION_EDITOR_USE_NAME, actionForm.getApplicationEditorUseName()));

        return spec;
    }

    @Override
    protected PartyApplicationEditorUseEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = PartyUtil.getHome().getPartyApplicationEditorUseEdit();

        edit.setEditorName(actionForm.getEditorChoice());
        edit.setPreferredHeight(actionForm.getPreferredHeight());
        edit.setPreferredWidth(actionForm.getPreferredWidth());

        return edit;
    }

    @Override
    protected EditPartyApplicationEditorUseForm getForm()
            throws NamingException {
        return PartyUtil.getHome().getEditPartyApplicationEditorUseForm();
    }

    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditPartyApplicationEditorUseResult result, PartyApplicationEditorUseSpec spec, PartyApplicationEditorUseEdit edit)
            throws NamingException {
        actionForm.setApplicationName(spec.getApplicationName());
        actionForm.setApplicationEditorUseName(spec.getApplicationEditorUseName());
        actionForm.setEditorChoice(edit.getEditorName());
        actionForm.setPreferredHeight(edit.getPreferredHeight());
        actionForm.setPreferredWidth(edit.getPreferredWidth());
    }

    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditPartyApplicationEditorUseForm commandForm)
            throws Exception {
        return PartyUtil.getHome().editPartyApplicationEditorUse(getUserVisitPK(request), commandForm);
    }

    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditPartyApplicationEditorUseResult result)
            throws NamingException {
        request.setAttribute(AttributeConstants.PARTY_APPLICATION_EDITOR_USE, result.getPartyApplicationEditorUse());
        BaseEmployeeApplicationEditorUseAction.setupEmployee(request, null);
    }
    
}
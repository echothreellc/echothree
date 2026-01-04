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

package com.echothree.ui.web.main.action.humanresources.employee;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.EditProfileResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
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
    path = "/HumanResources/Employee/EmployeeProfileEdit",
    mappingClass = SecureActionMapping.class,
    name = "EmployeeProfileEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/Employee/Review", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/employee/employeeProfileEdit.jsp")
    }
)
public class EmployeeProfileEditAction
        extends MainBaseAction<EmployeeProfileEditActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, EmployeeProfileEditActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var partyName = request.getParameter(ParameterConstants.PARTY_NAME);
        var employeeName = request.getParameter(ParameterConstants.EMPLOYEE_NAME);
        var commandForm = PartyUtil.getHome().getEditProfileForm();
        var spec = PartyUtil.getHome().getPartySpec();
        
        if(partyName == null) {
            partyName = actionForm.getPartyName();
        }
        if(employeeName == null) {
            employeeName = actionForm.getEmployeeName();
        }
        
        commandForm.setSpec(spec);
        spec.setPartyName(partyName);
        
        if(wasPost(request)) {
            var wasCanceled = wasCanceled(request);

            if(wasCanceled) {
                commandForm.setEditMode(EditMode.ABANDON);
            } else {
                var edit = PartyUtil.getHome().getProfileEdit();

                commandForm.setEditMode(EditMode.UPDATE);
                commandForm.setEdit(edit);

                edit.setNickname(actionForm.getNickname());
                edit.setIconName(actionForm.getIconChoice());
                edit.setPronunciation(actionForm.getPronunciation());
                edit.setGenderName(actionForm.getGenderChoice());
                edit.setPronouns(actionForm.getPronouns());
                edit.setBirthday(actionForm.getBirthday());
                edit.setBirthdayFormatName(actionForm.getBirthdayFormatChoice());
                edit.setOccupation(actionForm.getOccupation());
                edit.setHobbies(actionForm.getHobbies());
                edit.setLocation(actionForm.getLocation());
                edit.setBioMimeTypeName(actionForm.getBioMimeTypeChoice());
                edit.setBio(actionForm.getBio());
                edit.setSignatureMimeTypeName(actionForm.getSignatureMimeTypeChoice());
                edit.setSignature(actionForm.getSignature());
            }

            var commandResult = PartyUtil.getHome().editProfile(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors() && !wasCanceled) {
                var executionResult = commandResult.getExecutionResult();
                
                if(executionResult != null) {
                    var result = (EditProfileResult)executionResult.getResult();
                    
                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }
                
                setCommandResultAttribute(request, commandResult);
                
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = PartyUtil.getHome().editProfile(getUserVisitPK(request), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (EditProfileResult)executionResult.getResult();
            
            if(result != null) {
                var edit = result.getEdit();
                
                if(edit != null) {
                    actionForm.setPartyName(partyName);
                    actionForm.setEmployeeName(employeeName);
                    actionForm.setNickname(edit.getNickname());
                    actionForm.setIconChoice(edit.getIconName());
                    actionForm.setPronunciation(edit.getPronunciation());
                    actionForm.setGenderChoice(edit.getGenderName());
                    actionForm.setPronouns(edit.getPronouns());
                    actionForm.setBirthday(edit.getBirthday());
                    actionForm.setBirthdayFormatChoice(edit.getBirthdayFormatName());
                    actionForm.setOccupation(edit.getOccupation());
                    actionForm.setHobbies(edit.getHobbies());
                    actionForm.setLocation(edit.getLocation());
                    actionForm.setBioMimeTypeChoice(edit.getBioMimeTypeName());
                    actionForm.setBio(edit.getBio());
                    actionForm.setSignatureMimeTypeChoice(edit.getSignatureMimeTypeName());
                    actionForm.setSignature(edit.getSignature());
                }
                
                request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
            }
            
            setCommandResultAttribute(request, commandResult);
            
            forwardKey = ForwardConstants.FORM;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.EMPLOYEE_NAME, employeeName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.EMPLOYEE_NAME, employeeName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}
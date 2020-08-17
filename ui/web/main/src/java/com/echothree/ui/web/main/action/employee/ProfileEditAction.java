// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.ui.web.main.action.employee;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.edit.ProfileEdit;
import com.echothree.control.user.party.common.form.EditProfileForm;
import com.echothree.control.user.party.common.result.EditProfileResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Employee/ProfileEdit",
    mappingClass = SecureActionMapping.class,
    name = "ProfileEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Form", path = "/employee/profileEdit.jsp")
    }
)
public class ProfileEditAction
        extends MainBaseAction<ProfileEditActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ProfileEditActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        
        EditProfileForm commandForm = PartyUtil.getHome().getEditProfileForm();
        
        commandForm.setSpec(PartyUtil.getHome().getPartySpec());
        
        if(wasPost(request)) {
            boolean wasCanceled = wasCanceled(request);
            
            if(wasCanceled) {
                commandForm.setEditMode(EditMode.ABANDON);
            } else {
                ProfileEdit edit = PartyUtil.getHome().getProfileEdit();

                commandForm.setEditMode(EditMode.UPDATE);
                commandForm.setEdit(edit);

                edit.setNickname(actionForm.getNickname());
                edit.setIconName(actionForm.getIconChoice());
                edit.setGenderName(actionForm.getGenderChoice());
                edit.setMoodName(actionForm.getMoodChoice());
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
            
            CommandResult commandResult = PartyUtil.getHome().editProfile(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors() && !wasCanceled) {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                
                if(executionResult != null) {
                    EditProfileResult result = (EditProfileResult)executionResult.getResult();
                    
                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }
                
                setCommandResultAttribute(request, commandResult);
                
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.PORTAL;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);
            
            CommandResult commandResult = PartyUtil.getHome().editProfile(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            EditProfileResult result = (EditProfileResult)executionResult.getResult();
            
            if(result != null) {
                ProfileEdit edit = result.getEdit();
                
                if(edit != null) {
                    actionForm.setNickname(edit.getNickname());
                    actionForm.setIconChoice(edit.getIconName());
                    actionForm.setGenderChoice(edit.getGenderName());
                    actionForm.setMoodChoice(edit.getMoodName());
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
        
        return mapping.findForward(forwardKey);
    }
    
}
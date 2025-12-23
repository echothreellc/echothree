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

package com.echothree.ui.web.main.action.sequence.sequencetype;

import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Sequence/SequenceType/Add",
    mappingClass = SecureActionMapping.class,
    name = "SequenceTypeAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Sequence/SequenceType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/sequence/sequencetype/add.jsp")
    }
)
public class AddAction
        extends MainBaseAction<AddActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, AddActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;

        if(wasPost(request)) {
            var commandForm = SequenceUtil.getHome().getCreateSequenceTypeForm();

            commandForm.setSequenceTypeName(actionForm.getSequenceTypeName());
            commandForm.setPrefix(actionForm.getPrefix());
            commandForm.setSuffix(actionForm.getSuffix());
            commandForm.setSequenceEncoderTypeName(actionForm.getSequenceEncoderTypeChoice());
            commandForm.setSequenceChecksumTypeName(actionForm.getSequenceChecksumTypeChoice());
            commandForm.setChunkSize(actionForm.getChunkSize());
            commandForm.setIsDefault(actionForm.getIsDefault().toString());
            commandForm.setSortOrder(actionForm.getSortOrder());
            commandForm.setDescription(actionForm.getDescription());

            var commandResult = SequenceUtil.getHome().createSequenceType(getUserVisitPK(request), commandForm);

            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            actionForm.setupSequenceEncoderTypeChoices();
            actionForm.setupSequenceChecksumTypeChoices();
            actionForm.setSortOrder("1");
            forwardKey = ForwardConstants.FORM;
        }

        return mapping.findForward(forwardKey);
    }
}
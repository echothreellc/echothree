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
import com.echothree.control.user.sequence.common.result.EditSequenceTypeResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.EditMode;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Sequence/SequenceType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "SequenceTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Sequence/SequenceType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/sequence/sequencetype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<EditActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, EditActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var originalTypeSequenceName = request.getParameter(ParameterConstants.ORIGINAL_SEQUENCE_TYPE_NAME);
        var commandForm = SequenceUtil.getHome().getEditSequenceTypeForm();
        var spec = SequenceUtil.getHome().getSequenceTypeSpec();

        if(originalTypeSequenceName == null) {
            originalTypeSequenceName = actionForm.getOriginalSequenceTypeName();
        }
        
        commandForm.setSpec(spec);
        spec.setSequenceTypeName(originalTypeSequenceName);

        if(wasPost(request)) {
            var edit = SequenceUtil.getHome().getSequenceTypeEdit();

            commandForm.setEditMode(EditMode.UPDATE);
            commandForm.setEdit(edit);

            edit.setSequenceTypeName(actionForm.getSequenceTypeName());
            edit.setPrefix(actionForm.getPrefix());
            edit.setSuffix(actionForm.getSuffix());
            edit.setSequenceEncoderTypeName(actionForm.getSequenceEncoderTypeChoice());
            edit.setSequenceChecksumTypeName(actionForm.getSequenceChecksumTypeChoice());
            edit.setChunkSize(actionForm.getChunkSize());
            edit.setIsDefault(actionForm.getIsDefault().toString());
            edit.setSortOrder(actionForm.getSortOrder());
            edit.setDescription(actionForm.getDescription());

            var commandResult = SequenceUtil.getHome().editSequenceType(getUserVisitPK(request), commandForm);

            if(commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();

                if(executionResult != null) {
                    var result = (EditSequenceTypeResult)executionResult.getResult();

                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }

                setCommandResultAttribute(request, commandResult);

                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = SequenceUtil.getHome().editSequenceType(getUserVisitPK(request), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (EditSequenceTypeResult)executionResult.getResult();

            if(result != null) {
                var edit = result.getEdit();

                if(edit != null) {
                    actionForm.setOriginalSequenceTypeName(edit.getSequenceTypeName());
                    actionForm.setSequenceTypeName(edit.getSequenceTypeName());
                    actionForm.setPrefix(edit.getPrefix());
                    actionForm.setSuffix(edit.getSuffix());
                    actionForm.setSequenceEncoderTypeChoice(edit.getSequenceEncoderTypeName());
                    actionForm.setSequenceChecksumTypeChoice(edit.getSequenceChecksumTypeName());
                    actionForm.setChunkSize(edit.getChunkSize());
                    actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                    actionForm.setSortOrder(edit.getSortOrder());
                    actionForm.setDescription(edit.getDescription());
                }

                request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
            }

            setCommandResultAttribute(request, commandResult);

            forwardKey = ForwardConstants.FORM;
        }

        return mapping.findForward(forwardKey);
    }
}
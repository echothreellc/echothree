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

package com.echothree.ui.web.main.action.sequence.sequence;

import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.common.result.EditSequenceResult;
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
    path = "/Sequence/Sequence/Edit",
    mappingClass = SecureActionMapping.class,
    name = "SequenceEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Sequence/Sequence/Main", redirect = true),
        @SproutForward(name = "Form", path = "/sequence/sequence/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<EditActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, EditActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var sequenceTypeName = request.getParameter(ParameterConstants.SEQUENCE_TYPE_NAME);
        var originalSequenceName = request.getParameter(ParameterConstants.ORIGINAL_SEQUENCE_NAME);
        var commandForm = SequenceUtil.getHome().getEditSequenceForm();
        var spec = SequenceUtil.getHome().getSequenceSpec();

        if(sequenceTypeName == null) {
            sequenceTypeName = actionForm.getSequenceTypeName();
        }

        if(originalSequenceName == null) {
            originalSequenceName = actionForm.getOriginalSequenceName();
        }

        commandForm.setSpec(spec);
        spec.setSequenceTypeName(sequenceTypeName);
        spec.setSequenceName(originalSequenceName);

        if(wasPost(request)) {
            var edit = SequenceUtil.getHome().getSequenceEdit();

            commandForm.setEditMode(EditMode.UPDATE);
            commandForm.setEdit(edit);

            edit.setSequenceName(actionForm.getSequenceName());
            edit.setMask(actionForm.getMask());
            edit.setChunkSize(actionForm.getChunkSize());
            edit.setIsDefault(actionForm.getIsDefault().toString());
            edit.setSortOrder(actionForm.getSortOrder());
            edit.setDescription(actionForm.getDescription());

            var commandResult = SequenceUtil.getHome().editSequence(getUserVisitPK(request), commandForm);

            if(commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();

                if(executionResult != null) {
                    var result = (EditSequenceResult)executionResult.getResult();

                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }

                setCommandResultAttribute(request, commandResult);

                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = SequenceUtil.getHome().editSequence(getUserVisitPK(request), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (EditSequenceResult)executionResult.getResult();

            if(result != null) {
                var edit = result.getEdit();

                if(edit != null) {
                    actionForm.setSequenceTypeName(sequenceTypeName);
                    actionForm.setOriginalSequenceName(edit.getSequenceName());
                    actionForm.setSequenceName(edit.getSequenceName());
                    actionForm.setMask(edit.getMask());
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

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.SEQUENCE_TYPE_NAME, sequenceTypeName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);

            parameters.put(ParameterConstants.SEQUENCE_TYPE_NAME, sequenceTypeName);
            customActionForward.setParameters(parameters);
        }

        return customActionForward;
    }
}
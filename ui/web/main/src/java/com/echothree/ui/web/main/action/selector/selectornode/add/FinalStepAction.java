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

package com.echothree.ui.web.main.action.selector.selectornode.add;


import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetEntityListItemResult;
import com.echothree.control.user.selector.common.SelectorUtil;
import com.echothree.control.user.selector.common.result.GetSelectorNodeTypeResult;
import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.result.GetWorkflowStepResult;
import com.echothree.model.control.core.common.transfer.EntityListItemTransfer;
import com.echothree.model.control.selector.common.transfer.SelectorNodeTypeTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowStepTransfer;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Selector/SelectorNode/Add/FinalStep",
    mappingClass = SecureActionMapping.class,
    name = "SelectorNodeAddFinalStep",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Selector/SelectorNode/Main", redirect = true),
        @SproutForward(name = "Form", path = "/selector/selectornode/add/finalStep.jsp")
    }
)
public class FinalStepAction
        extends MainBaseAction<ActionForm> {
    
    private SelectorNodeTypeTransfer getSelectorNodeTypeTransfer(UserVisitPK userVisitPK, String selectorNodeTypeName)
            throws NamingException {
        var commandForm = SelectorUtil.getHome().getGetSelectorNodeTypeForm();
        
        commandForm.setSelectorNodeTypeName(selectorNodeTypeName);

        var commandResult = SelectorUtil.getHome().getSelectorNodeType(userVisitPK, commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetSelectorNodeTypeResult)executionResult.getResult();
        
        return result.getSelectorNodeType();
    }
    
    private WorkflowStepTransfer getWorkflowStepTransfer(UserVisitPK userVisitPK, String workflowName, String workflowStepName)
            throws NamingException {
        var commandForm = WorkflowUtil.getHome().getGetWorkflowStepForm();
        
        commandForm.setWorkflowName(workflowName);
        commandForm.setWorkflowStepName(workflowStepName);

        var commandResult = WorkflowUtil.getHome().getWorkflowStep(userVisitPK, commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetWorkflowStepResult)executionResult.getResult();
        
        return result.getWorkflowStep();
    }
    
    private EntityListItemTransfer getEntityListItemTransfer(UserVisitPK userVisitPK, String componentVendorName,
            String entityTypeName, String entityAttributeName, String entityListItemName)
            throws NamingException {
        var commandForm = CoreUtil.getHome().getGetEntityListItemForm();
        
        commandForm.setComponentVendorName(componentVendorName);
        commandForm.setEntityTypeName(entityTypeName);
        commandForm.setEntityAttributeName(entityAttributeName);
        commandForm.setEntityListItemName(entityListItemName);

        var commandResult = CoreUtil.getHome().getEntityListItem(userVisitPK, commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetEntityListItemResult)executionResult.getResult();
        
        return result.getEntityListItem();
    }
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var selectorKindName = request.getParameter(ParameterConstants.SELECTOR_KIND_NAME);
        var selectorTypeName = request.getParameter(ParameterConstants.SELECTOR_TYPE_NAME);
        var selectorName = request.getParameter(ParameterConstants.SELECTOR_NAME);
        var selectorNodeTypeName = request.getParameter(ParameterConstants.SELECTOR_NODE_TYPE_NAME);
        SelectorNodeTypeTransfer selectorNodeType = null;
        WorkflowStepTransfer workflowStep = null;
        EntityListItemTransfer entityListItem = null;
        
        try {
            var workflowName = request.getParameter(ParameterConstants.WORKFLOW_NAME);
            var workflowStepName = request.getParameter(ParameterConstants.WORKFLOW_STEP_NAME);
            var componentVendorName = request.getParameter(ParameterConstants.COMPONENT_VENDOR_NAME);
            var entityTypeName = request.getParameter(ParameterConstants.ENTITY_TYPE_NAME);
            var entityAttributeName = request.getParameter(ParameterConstants.ENTITY_ATTRIBUTE_NAME);
            var entityListItemName = request.getParameter(ParameterConstants.ENTITY_LIST_ITEM_NAME);
            var userVisitPK = getUserVisitPK(request);
            var actionForm = (FinalStepForm)form;
            
            if(selectorKindName == null)
                selectorKindName = actionForm.getSelectorKindName();
            if(selectorTypeName == null)
                selectorTypeName = actionForm.getSelectorTypeName();
            if(selectorName == null)
                selectorName = actionForm.getSelectorName();
            if(selectorNodeTypeName == null)
                selectorNodeTypeName = actionForm.getSelectorNodeTypeName();
            if(workflowName == null)
                workflowName = actionForm.getWorkflowName();
            if(workflowStepName == null)
                workflowStepName = actionForm.getWorkflowStepName();
            if(componentVendorName == null)
                componentVendorName = actionForm.getComponentVendorName();
            if(entityTypeName == null)
                entityTypeName = actionForm.getEntityTypeName();
            if(entityAttributeName == null)
                entityAttributeName = actionForm.getEntityAttributeName();
            if(entityListItemName == null)
                entityListItemName = actionForm.getEntityListItemName();
            
            selectorNodeType = getSelectorNodeTypeTransfer(userVisitPK, selectorNodeTypeName);
            
            if(workflowStepName != null && workflowStepName.length() != 0) {
                workflowStep = getWorkflowStepTransfer(userVisitPK, workflowName, workflowStepName);
            }
            
            if(entityListItemName != null && entityListItemName.length() != 0) {
                entityListItem = getEntityListItemTransfer(userVisitPK, componentVendorName, entityTypeName, entityAttributeName,
                        entityListItemName);
            }
            
            if(wasPost(request)) {
                var commandForm = SelectorUtil.getHome().getCreateSelectorNodeForm();
                
                commandForm.setSelectorKindName(selectorKindName);
                commandForm.setSelectorTypeName(selectorTypeName);
                commandForm.setSelectorName(selectorName);
                commandForm.setSelectorNodeName(actionForm.getSelectorNodeName());
                commandForm.setIsRootSelectorNode(actionForm.getIsRootSelectorNode().toString());
                commandForm.setSelectorNodeTypeName(selectorNodeTypeName);
                commandForm.setNegate(actionForm.getNegate().toString());
                commandForm.setDescription(actionForm.getDescription());
                
                commandForm.setSelectorBooleanTypeName(actionForm.getSelectorBooleanTypeChoice());
                commandForm.setLeftSelectorNodeName(actionForm.getLeftSelectorNodeChoice());
                commandForm.setRightSelectorNodeName(actionForm.getRightSelectorNodeChoice());
                commandForm.setItemCategoryName(actionForm.getItemCategoryChoice());
                commandForm.setItemAccountingCategoryName(actionForm.getItemAccountingCategoryChoice());
                commandForm.setItemPurchasingCategoryName(actionForm.getItemPurchasingCategoryChoice());
                commandForm.setResponsibilityTypeName(actionForm.getResponsibilityTypeChoice());
                commandForm.setSkillTypeName(actionForm.getSkillTypeChoice());
                commandForm.setTrainingClassName(actionForm.getTrainingClassChoice());
                commandForm.setWorkflowName(actionForm.getWorkflowName());
                commandForm.setWorkflowStepName(actionForm.getWorkflowStepName());
                commandForm.setComponentVendorName(componentVendorName);
                commandForm.setEntityTypeName(entityTypeName);
                commandForm.setEntityAttributeName(entityAttributeName);
                commandForm.setEntityListItemName(entityListItemName);
                
                commandForm.setCheckParents(actionForm.getCheckParents().toString());

                var commandResult = SelectorUtil.getHome().createSelectorNode(userVisitPK, commandForm);
                
                if(commandResult.hasErrors()) {
                    setCommandResultAttribute(request, commandResult);
                    forwardKey = ForwardConstants.FORM;
                } else {
                    forwardKey = ForwardConstants.DISPLAY;
                }
            } else {
                actionForm.setSelectorKindName(selectorKindName);
                actionForm.setSelectorTypeName(selectorTypeName);
                actionForm.setSelectorName(selectorName);
                actionForm.setSelectorNodeTypeName(selectorNodeTypeName);
                actionForm.setWorkflowName(workflowName);
                actionForm.setWorkflowStepName(workflowStepName);
                actionForm.setComponentVendorName(componentVendorName);
                actionForm.setEntityTypeName(entityTypeName);
                actionForm.setEntityAttributeName(entityAttributeName);
                actionForm.setEntityListItemName(entityListItemName);
                forwardKey = ForwardConstants.FORM;
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.SELECTOR_KIND_NAME, selectorKindName);
            request.setAttribute(AttributeConstants.SELECTOR_TYPE_NAME, selectorTypeName);
            request.setAttribute(AttributeConstants.SELECTOR_NAME, selectorName);
            request.setAttribute(AttributeConstants.SELECTOR_NODE_TYPE_NAME, selectorNodeTypeName);
            request.setAttribute(AttributeConstants.SELECTOR_NODE_TYPE, selectorNodeType);
            
            if(workflowStep != null) {
                request.setAttribute(AttributeConstants.WORKFLOW_STEP, workflowStep);
            }
            
            if(entityListItem != null) {
                request.setAttribute(AttributeConstants.ENTITY_LIST_ITEM, entityListItem);
            }
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(3);
            
            parameters.put(ParameterConstants.SELECTOR_KIND_NAME, selectorKindName);
            parameters.put(ParameterConstants.SELECTOR_TYPE_NAME, selectorTypeName);
            parameters.put(ParameterConstants.SELECTOR_NAME, selectorName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}
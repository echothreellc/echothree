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

package com.echothree.ui.web.main.action.purchasing.vendortype;

import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.common.result.EditVendorTypeResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.EditMode;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Purchasing/VendorType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "VendorTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Purchasing/VendorType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/purchasing/vendortype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        var originalVendorTypeName = request.getParameter(ParameterConstants.ORIGINAL_VENDOR_TYPE_NAME);
        
        try {
            if(forwardKey == null) {
                var actionForm = (EditActionForm)form;
                var commandForm = VendorUtil.getHome().getEditVendorTypeForm();
                var spec = VendorUtil.getHome().getVendorTypeUniversalSpec();
                
                if(originalVendorTypeName == null)
                    originalVendorTypeName = actionForm.getOriginalVendorTypeName();
                
                commandForm.setSpec(spec);
                spec.setVendorTypeName(originalVendorTypeName);
                
                if(wasPost(request)) {
                    var edit = VendorUtil.getHome().getVendorTypeEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    
                    edit.setVendorTypeName(actionForm.getVendorTypeName());
                    edit.setDefaultTermName(actionForm.getDefaultTermChoice());
                    edit.setDefaultFreeOnBoardName(actionForm.getDefaultFreeOnBoardChoice());
                    edit.setDefaultCancellationPolicyName(actionForm.getDefaultCancellationPolicyChoice());
                    edit.setDefaultReturnPolicyName(actionForm.getDefaultReturnPolicyChoice());
                    edit.setDefaultApGlAccountName(actionForm.getDefaultApGlAccountChoice());
                    edit.setDefaultHoldUntilComplete(actionForm.getDefaultHoldUntilComplete().toString());
                    edit.setDefaultAllowBackorders(actionForm.getDefaultAllowBackorders().toString());
                    edit.setDefaultAllowSubstitutions(actionForm.getDefaultAllowSubstitutions().toString());
                    edit.setDefaultAllowCombiningShipments(actionForm.getDefaultAllowCombiningShipments().toString());
                    edit.setDefaultRequireReference(actionForm.getDefaultRequireReference().toString());
                    edit.setDefaultAllowReferenceDuplicates(actionForm.getDefaultAllowReferenceDuplicates().toString());
                    edit.setDefaultReferenceValidationPattern(actionForm.getDefaultReferenceValidationPattern());
                    edit.setIsDefault(actionForm.getIsDefault().toString());
                    edit.setSortOrder(actionForm.getSortOrder());
                    edit.setDescription(actionForm.getDescription());

                    var commandResult = VendorUtil.getHome().editVendorType(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        var executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            var result = (EditVendorTypeResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = VendorUtil.getHome().editVendorType(getUserVisitPK(request), commandForm);
                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditVendorTypeResult)executionResult.getResult();
                    
                    if(result != null) {
                        var edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setOriginalVendorTypeName(edit.getVendorTypeName());
                            actionForm.setVendorTypeName(edit.getVendorTypeName());
                            actionForm.setDefaultTermChoice(edit.getDefaultTermName());
                            actionForm.setDefaultFreeOnBoardChoice(edit.getDefaultFreeOnBoardName());
                            actionForm.setDefaultCancellationPolicyChoice(edit.getDefaultCancellationPolicyName());
                            actionForm.setDefaultReturnPolicyChoice(edit.getDefaultReturnPolicyName());
                            actionForm.setDefaultApGlAccountChoice(edit.getDefaultApGlAccountName());
                            actionForm.setDefaultHoldUntilComplete(Boolean.valueOf(edit.getDefaultHoldUntilComplete()));
                            actionForm.setDefaultAllowBackorders(Boolean.valueOf(edit.getDefaultAllowBackorders()));
                            actionForm.setDefaultAllowSubstitutions(Boolean.valueOf(edit.getDefaultAllowSubstitutions()));
                            actionForm.setDefaultAllowCombiningShipments(Boolean.valueOf(edit.getDefaultAllowCombiningShipments()));
                            actionForm.setDefaultRequireReference(Boolean.valueOf(edit.getDefaultRequireReference()));
                            actionForm.setDefaultAllowReferenceDuplicates(Boolean.valueOf(edit.getDefaultAllowReferenceDuplicates()));
                            actionForm.setDefaultReferenceValidationPattern(edit.getDefaultReferenceValidationPattern());
                            actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                            actionForm.setSortOrder(edit.getSortOrder());
                            actionForm.setDescription(edit.getDescription());
                        }
                        
                        request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                    }
                    
                    setCommandResultAttribute(request, commandResult);
                    
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}
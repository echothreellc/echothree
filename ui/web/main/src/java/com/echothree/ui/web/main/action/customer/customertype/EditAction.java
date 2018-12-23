// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.ui.web.main.action.customer.customertype;

import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.common.edit.CustomerTypeEdit;
import com.echothree.control.user.customer.common.form.EditCustomerTypeForm;
import com.echothree.control.user.customer.common.result.EditCustomerTypeResult;
import com.echothree.control.user.customer.common.spec.CustomerTypeSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.command.ExecutionResult;
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
    path = "/Customer/CustomerType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "CustomerTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Customer/CustomerType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/customer/customertype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String originalTypeCustomerName = request.getParameter(ParameterConstants.ORIGINAL_CUSTOMER_TYPE_NAME);
        
        try {
            if(forwardKey == null) {
                EditActionForm actionForm = (EditActionForm)form;
                EditCustomerTypeForm commandForm = CustomerUtil.getHome().getEditCustomerTypeForm();
                CustomerTypeSpec spec = CustomerUtil.getHome().getCustomerTypeSpec();
                
                if(originalTypeCustomerName == null)
                    originalTypeCustomerName = actionForm.getOriginalCustomerTypeName();
                
                commandForm.setSpec(spec);
                spec.setCustomerTypeName(originalTypeCustomerName);
                
                if(wasPost(request)) {
                    CustomerTypeEdit edit = CustomerUtil.getHome().getCustomerTypeEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    
                    edit.setCustomerTypeName(actionForm.getCustomerTypeName());
                    edit.setCustomerSequenceName(actionForm.getCustomerSequenceChoice());
                    edit.setDefaultSourceName(actionForm.getDefaultSourceChoice());
                    edit.setDefaultTermName(actionForm.getDefaultTermChoice());
                    edit.setDefaultCancellationPolicyName(actionForm.getDefaultCancellationPolicyChoice());
                    edit.setDefaultReturnPolicyName(actionForm.getDefaultReturnPolicyChoice());
                    edit.setDefaultCustomerStatusChoice(actionForm.getDefaultCustomerStatusChoice());
                    edit.setDefaultCustomerCreditStatusChoice(actionForm.getDefaultCustomerCreditStatusChoice());
                    edit.setDefaultArGlAccountName(actionForm.getDefaultArGlAccountChoice());
                    edit.setDefaultHoldUntilComplete(actionForm.getDefaultHoldUntilComplete().toString());
                    edit.setDefaultAllowBackorders(actionForm.getDefaultAllowBackorders().toString());
                    edit.setDefaultAllowSubstitutions(actionForm.getDefaultAllowSubstitutions().toString());
                    edit.setDefaultAllowCombiningShipments(actionForm.getDefaultAllowCombiningShipments().toString());
                    edit.setDefaultRequireReference(actionForm.getDefaultRequireReference().toString());
                    edit.setDefaultAllowReferenceDuplicates(actionForm.getDefaultAllowReferenceDuplicates().toString());
                    edit.setDefaultReferenceValidationPattern(actionForm.getDefaultReferenceValidationPattern());
                    edit.setDefaultTaxable(actionForm.getDefaultTaxable().toString());
                    edit.setAllocationPriorityName(actionForm.getAllocationPriorityChoice());
                    edit.setIsDefault(actionForm.getIsDefault().toString());
                    edit.setSortOrder(actionForm.getSortOrder());
                    edit.setDescription(actionForm.getDescription());
                    
                    CommandResult commandResult = CustomerUtil.getHome().editCustomerType(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        ExecutionResult executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            EditCustomerTypeResult result = (EditCustomerTypeResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = CustomerUtil.getHome().editCustomerType(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditCustomerTypeResult result = (EditCustomerTypeResult)executionResult.getResult();
                    
                    if(result != null) {
                        CustomerTypeEdit edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setOriginalCustomerTypeName(edit.getCustomerTypeName());
                            actionForm.setCustomerTypeName(edit.getCustomerTypeName());
                            actionForm.setCustomerSequenceChoice(edit.getCustomerSequenceName());
                            actionForm.setDefaultSourceChoice(edit.getDefaultSourceName());
                            actionForm.setDefaultTermChoice(edit.getDefaultTermName());
                            actionForm.setDefaultCancellationPolicyChoice(edit.getDefaultCancellationPolicyName());
                            actionForm.setDefaultReturnPolicyChoice(edit.getDefaultReturnPolicyName());
                            actionForm.setDefaultCustomerStatusChoice(edit.getDefaultCustomerStatusChoice());
                            actionForm.setDefaultCustomerCreditStatusChoice(edit.getDefaultCustomerCreditStatusChoice());
                            actionForm.setDefaultArGlAccountChoice(edit.getDefaultArGlAccountName());
                            actionForm.setDefaultHoldUntilComplete(Boolean.valueOf(edit.getDefaultHoldUntilComplete()));
                            actionForm.setDefaultAllowBackorders(Boolean.valueOf(edit.getDefaultAllowBackorders()));
                            actionForm.setDefaultAllowSubstitutions(Boolean.valueOf(edit.getDefaultAllowSubstitutions()));
                            actionForm.setDefaultAllowCombiningShipments(Boolean.valueOf(edit.getDefaultAllowCombiningShipments()));
                            actionForm.setDefaultRequireReference(Boolean.valueOf(edit.getDefaultRequireReference()));
                            actionForm.setDefaultAllowReferenceDuplicates(Boolean.valueOf(edit.getDefaultAllowReferenceDuplicates()));
                            actionForm.setDefaultReferenceValidationPattern(edit.getDefaultReferenceValidationPattern());
                            actionForm.setDefaultTaxable(Boolean.valueOf(edit.getDefaultTaxable()));
                            actionForm.setAllocationPriorityChoice(edit.getAllocationPriorityName());
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
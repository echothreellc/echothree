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

package com.echothree.ui.web.main.action.accounting.tax;

import com.echothree.control.user.tax.common.TaxUtil;
import com.echothree.control.user.tax.common.result.EditTaxResult;
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
    path = "/Accounting/Tax/Edit",
    mappingClass = SecureActionMapping.class,
    name = "TaxEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Accounting/Tax/Main", redirect = true),
        @SproutForward(name = "Form", path = "/accounting/tax/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<EditActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, EditActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var originalTaxName = request.getParameter(ParameterConstants.ORIGINAL_TAX_NAME);
        var commandForm = TaxUtil.getHome().getEditTaxForm();
        var spec = TaxUtil.getHome().getTaxSpec();
        
        if(originalTaxName == null) {
            originalTaxName = actionForm.getOriginalTaxName();
        }
        
        commandForm.setSpec(spec);
        spec.setTaxName(originalTaxName);
        
        if(wasPost(request)) {
            var edit = TaxUtil.getHome().getTaxEdit();
            
            commandForm.setEditMode(EditMode.UPDATE);
            commandForm.setEdit(edit);
            
            edit.setTaxName(actionForm.getTaxName());
            edit.setContactMechanismPurposeName(actionForm.getContactMechanismPurposeChoice());
            edit.setGlAccountName(actionForm.getGlAccountChoice());
            edit.setIncludeShippingCharge(actionForm.getIncludeShippingCharge().toString());
            edit.setIncludeProcessingCharge(actionForm.getIncludeProcessingCharge().toString());
            edit.setIncludeInsuranceCharge(actionForm.getIncludeInsuranceCharge().toString());
            edit.setPercent(actionForm.getPercent());
            edit.setIsDefault(actionForm.getIsDefault().toString());
            edit.setSortOrder(actionForm.getSortOrder());
            edit.setDescription(actionForm.getDescription());

            var commandResult = TaxUtil.getHome().editTax(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                
                if(executionResult != null) {
                    var result = (EditTaxResult)executionResult.getResult();
                    
                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }
                
                setCommandResultAttribute(request, commandResult);
                
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = TaxUtil.getHome().editTax(getUserVisitPK(request), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (EditTaxResult)executionResult.getResult();
            
            if(result != null) {
                var edit = result.getEdit();
                
                if(edit != null) {
                    actionForm.setOriginalTaxName(edit.getTaxName());
                    actionForm.setTaxName(edit.getTaxName());
                    actionForm.setContactMechanismPurposeChoice(edit.getContactMechanismPurposeName());
                    actionForm.setGlAccountChoice(edit.getGlAccountName());
                    actionForm.setIncludeShippingCharge(Boolean.valueOf(edit.getIncludeShippingCharge()));
                    actionForm.setIncludeProcessingCharge(Boolean.valueOf(edit.getIncludeProcessingCharge()));
                    actionForm.setIncludeInsuranceCharge(Boolean.valueOf(edit.getIncludeInsuranceCharge()));
                    actionForm.setPercent(edit.getPercent());
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

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

package com.echothree.ui.web.main.action.advertising.offer.Add;

import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.GetDepartmentResult;
import com.echothree.ui.web.main.action.advertising.offer.AddActionForm;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.CustomActionForward;
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
    path = "/Advertising/Offer/Add/Step3",
    mappingClass = SecureActionMapping.class,
    name = "OfferAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Advertising/Offer/Main", redirect = true),
        @SproutForward(name = "Form", path = "/advertising/offer/add/step3.jsp")
    }
)
public class Step3Action
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        var companyName = request.getParameter(ParameterConstants.COMPANY_NAME);
        var divisionName = request.getParameter(ParameterConstants.DIVISION_NAME);
        var departmentName = request.getParameter(ParameterConstants.DEPARTMENT_NAME);
        
        try {
            if(forwardKey == null) {
                var actionForm = (AddActionForm)form;
                
                if(companyName == null) {
                    companyName = actionForm.getCompanyName();
                }
                
                if(divisionName == null) {
                    divisionName = actionForm.getDivisionName();
                }
                
                if(departmentName == null) {
                    departmentName = actionForm.getDepartmentName();
                }
                
                if(wasPost(request)) {
                    var commandForm = OfferUtil.getHome().getCreateOfferForm();
                    
                    commandForm.setOfferName(actionForm.getOfferName());
                    commandForm.setSalesOrderSequenceName(actionForm.getSalesOrderSequenceChoice());
                    commandForm.setCompanyName(companyName);
                    commandForm.setDivisionName(divisionName);
                    commandForm.setDepartmentName(departmentName);
                    commandForm.setOfferItemSelectorName(actionForm.getOfferItemSelectorChoice());
                    commandForm.setOfferItemPriceFilterName(actionForm.getOfferItemPriceFilterChoice());
                    commandForm.setIsDefault(actionForm.getIsDefault().toString());
                    commandForm.setSortOrder(actionForm.getSortOrder());
                    commandForm.setDescription(actionForm.getDescription());

                    var commandResult = OfferUtil.getHome().createOffer(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    actionForm.setCompanyName(companyName);
                    actionForm.setDivisionName(divisionName);
                    actionForm.setDepartmentName(departmentName);
                    actionForm.setSortOrder("1");
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            var commandForm = PartyUtil.getHome().getGetDepartmentForm();
            
            commandForm.setCompanyName(companyName);
            commandForm.setDivisionName(divisionName);
            commandForm.setDepartmentName(departmentName);

            var commandResult = PartyUtil.getHome().getDepartment(getUserVisitPK(request), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetDepartmentResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.DEPARTMENT, result.getDepartment());
        }
        
        return customActionForward;
    }
    
}
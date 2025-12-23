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

package com.echothree.ui.web.main.action.accounting.departmentcontactmechanism;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.result.GetContactMechanismResult;
import com.echothree.control.user.geo.common.GeoUtil;
import com.echothree.control.user.geo.common.result.GetCountryResult;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.GetDepartmentResult;
import com.echothree.model.control.geo.common.GeoOptions;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import java.util.HashSet;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;

public abstract class BaseDepartmentContactMechanismAction<A
        extends ActionForm>
        extends MainBaseAction<A> {

    public void setupDefaultCountry(HttpServletRequest request)
            throws NamingException {
        var commandForm =  GeoUtil.getHome().getGetCountryForm();
        
        Set<String> options = new HashSet<>();
        options.add(GeoOptions.CountryIncludeAliases);
        commandForm.setOptions(options);

        var commandResult = GeoUtil.getHome().getCountry(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetCountryResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.DEFAULT_COUNTRY, result.getCountry());
    }
    
    public void setupCountry(HttpServletRequest request, String countryName)
            throws NamingException {
        var commandForm =  GeoUtil.getHome().getGetCountryForm();
        
        commandForm.setCountryName(countryName);

        var commandResult = GeoUtil.getHome().getCountry(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetCountryResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.COUNTRY, result.getCountry());
    }
    
    public static void setupDepartment(HttpServletRequest request, String partyName)
            throws NamingException {
        var commandForm = PartyUtil.getHome().getGetDepartmentForm();

        commandForm.setPartyName(partyName);

        var commandResult = PartyUtil.getHome().getDepartment(getUserVisitPK(request), commandForm);

        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetDepartmentResult)executionResult.getResult();
            var department = result.getDepartment();

            if(department != null) {
                request.setAttribute(AttributeConstants.DEPARTMENT, department);
            }
        }
    }

    public void setupDepartment(HttpServletRequest request)
            throws NamingException {
        setupDepartment(request, request.getParameter(ParameterConstants.PARTY_NAME));
    }

    public static void setupContactMechanismTransfer(HttpServletRequest request, String contactMechanismName)
            throws NamingException {
        var commandForm = ContactUtil.getHome().getGetContactMechanismForm();

        commandForm.setContactMechanismName(contactMechanismName);

        var commandResult = ContactUtil.getHome().getContactMechanism(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetContactMechanismResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.CONTACT_MECHANISM, result.getContactMechanism());
    }

    public static void setupPartyContactMechanismTransfer(HttpServletRequest request, String partyName, String contactMechanismName)
            throws NamingException {
        var commandForm = ContactUtil.getHome().getGetContactMechanismForm();

        commandForm.setPartyName(partyName);
        commandForm.setContactMechanismName(contactMechanismName);

        var commandResult = ContactUtil.getHome().getContactMechanism(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetContactMechanismResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.PARTY_CONTACT_MECHANISM, result.getPartyContactMechanism());
    }

}

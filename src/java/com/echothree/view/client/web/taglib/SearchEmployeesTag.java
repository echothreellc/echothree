// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
// Copyright 1999-2004 The Apache Software Foundation.
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

package com.echothree.view.client.web.taglib;

import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.result.SearchEmployeesResult;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class SearchEmployeesTag
        extends BaseTag {
    
    protected String searchTypeName;
    protected String employeeName;
    protected String partyName;
    protected String firstName;
    protected String firstNameSoundex;
    protected String middleName;
    protected String middleNameSoundex;
    protected String lastName;
    protected String lastNameSoundex;
    protected String employeeStatusChoice;
    protected String employeeAvailabilityChoice;
    protected String createdSince;
    protected String modifiedSince;
    protected String fields;
    protected String countVar;
    protected String commandResultVar;
    protected int scope;
    protected boolean logErrors;
    
    private void init() {
        searchTypeName = null;
        employeeName = null;
        partyName = null;
        firstName = null;
        firstNameSoundex = null;
        middleName = null;
        middleNameSoundex = null;
        lastName = null;
        lastNameSoundex = null;
        employeeStatusChoice = null;
        employeeAvailabilityChoice = null;
        createdSince = null;
        modifiedSince = null;
        fields = null;
        countVar = null;
        commandResultVar = null;
        scope = PageContext.PAGE_SCOPE;
        logErrors = true;
    }
    
    /** Creates a new instance of SearchEmployeesTag */
    public SearchEmployeesTag() {
        super();
        init();
    }
    
    @Override
    public void release() {
        super.release();
        init();
    }
    
    /**
     * Sets the searchTypeName.
     * @param searchTypeName the searchTypeName to set
     */
    public void setSearchTypeName(String searchTypeName) {
        this.searchTypeName = searchTypeName;
    }

    /**
     * Sets the employeeName.
     * @param employeeName the employeeName to set
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    /**
     * Sets the partyName.
     * @param partyName the partyName to set
     */
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    /**
     * Sets the firstName.
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the firstNameSoundex.
     * @param firstNameSoundex the firstNameSoundex to set
     */
    public void setFirstNameSoundex(String firstNameSoundex) {
        this.firstNameSoundex = firstNameSoundex;
    }

    /**
     * Sets the middleName.
     * @param middleName the middleName to set
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * Sets the middleNameSoundex.
     * @param middleNameSoundex the middleNameSoundex to set
     */
    public void setMiddleNameSoundex(String middleNameSoundex) {
        this.middleNameSoundex = middleNameSoundex;
    }

    /**
     * Sets the lastName.
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the lastNameSoundex.
     * @param lastNameSoundex the lastNameSoundex to set
     */
    public void setLastNameSoundex(String lastNameSoundex) {
        this.lastNameSoundex = lastNameSoundex;
    }

    /**
     * Sets the employeeStatusChoice.
     * @param employeeStatusChoice the employeeStatusChoice to set
     */
    public void setEmployeeStatusChoice(String employeeStatusChoice) {
        this.employeeStatusChoice = employeeStatusChoice;
    }

    /**
     * Sets the employeeAvailabilityChoice.
     * @param employeeAvailabilityChoice the employeeAvailabilityChoice to set
     */
    public void setEmployeeAvailabilityChoice(String employeeAvailabilityChoice) {
        this.employeeAvailabilityChoice = employeeAvailabilityChoice;
    }

    /**
     * Sets the createdSince.
     * @param createdSince the createdSince to set
     */
    public void setCreatedSince(String createdSince) {
        this.createdSince = createdSince;
    }

    /**
     * Sets the modifiedSince.
     * @param modifiedSince the modifiedSince to set
     */
    public void setModifiedSince(String modifiedSince) {
        this.modifiedSince = modifiedSince;
    }

    /**
     * Sets the fields.
     * @param fields the fields to set
     */
    public void setFields(String fields) {
        this.fields = fields;
    }

    /**
     * Sets the countVar.
     * @param countVar the countVar to set
     */
    public void setCountVar(String countVar) {
        this.countVar = countVar;
    }

    public void setCommandResultVar(String commandResultVar) {
        this.commandResultVar = commandResultVar;
    }

    /**
     * Sets the scope.
     * @param scope the scope to set
     */
    public void setScope(String scope) {
        this.scope = translateScope(scope);
    }
    
    public void setLogErrors(Boolean logErrors) {
        this.logErrors = logErrors;
    }
    
    @Override
    public int doStartTag()
            throws JspException {
        try {
            var commandForm = SearchUtil.getHome().getSearchEmployeesForm();
            
            commandForm.setSearchTypeName(searchTypeName);
            commandForm.setEmployeeName(employeeName);
            commandForm.setPartyName(partyName);
            commandForm.setFirstName(firstName);
            commandForm.setFirstNameSoundex(firstNameSoundex);
            commandForm.setMiddleName(middleName);
            commandForm.setMiddleNameSoundex(middleNameSoundex);
            commandForm.setLastName(lastName);
            commandForm.setLastNameSoundex(lastNameSoundex);
            commandForm.setEmployeeStatusChoice(employeeStatusChoice);
            commandForm.setEmployeeAvailabilityChoice(employeeAvailabilityChoice);
            commandForm.setCreatedSince(createdSince);
            commandForm.setModifiedSince(modifiedSince);
            commandForm.setFields(fields);

            var commandResult = SearchUtil.getHome().searchEmployees(getUserVisitPK(), commandForm);
            
            pageContext.setAttribute(commandResultVar == null ? TagConstants.CommandResultName : commandResultVar, commandResult, scope);
            if(commandResult.hasErrors()) {
                if(logErrors) {
                    getLog().error(commandResult);
                }
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (SearchEmployeesResult)executionResult.getResult();

                if(countVar != null) {
                    pageContext.setAttribute(countVar, result.getCount(), scope);
                }
            }
        } catch (NamingException ne) {
            throw new JspException(ne);
        }
        
        return SKIP_BODY;
    }

}

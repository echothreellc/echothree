// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.control.user.search.server.command;

import com.echothree.control.user.search.common.form.SearchEmployeesForm;
import com.echothree.control.user.search.common.result.SearchEmployeesResult;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.search.common.SearchConstants;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.employee.server.search.EmployeeSearchEvaluator;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.control.employee.common.workflow.EmployeeAvailabilityConstants;
import com.echothree.model.control.employee.common.workflow.EmployeeStatusConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.search.server.entity.SearchKind;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import com.google.common.base.Splitter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SearchEmployeesCommand
        extends BaseSimpleCommand<SearchEmployeesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Employee.name(), SecurityRoles.Search.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FirstName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("FirstNameSoundex", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("MiddleName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("MiddleNameSoundex", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("LastName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("LastNameSoundex", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("EmployeeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EmployeeStatusChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EmployeeAvailabilityChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CreatedSince", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("ModifiedSince", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("Fields", FieldType.STRING, false, null, null)
                ));
    }

    /** Creates a new instance of SearchEmployeesCommand */
    public SearchEmployeesCommand(UserVisitPK userVisitPK, SearchEmployeesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        SearchEmployeesResult result = SearchResultFactory.getSearchEmployeesResult();
        String employeeName = form.getEmployeeName();
        String partyName = form.getPartyName();
        int parameterCount = (employeeName == null? 0: 1) + (partyName == null? 0: 1);

        if(parameterCount < 2) {
            var searchControl = Session.getModelController(SearchControl.class);
            SearchKind searchKind = searchControl.getSearchKindByName(SearchConstants.SearchKind_EMPLOYEE);

            if(searchKind != null) {
                String searchTypeName = form.getSearchTypeName();
                SearchType searchType = searchControl.getSearchTypeByName(searchKind, searchTypeName);

                if(searchType != null) {
                    var workflowControl = Session.getModelController(WorkflowControl.class);
                    String employeeStatusChoice = form.getEmployeeStatusChoice();
                    WorkflowStep employeeStatusWorkflowStep = employeeStatusChoice == null ? null
                            : workflowControl.getWorkflowStepByName(workflowControl.getWorkflowByName(EmployeeStatusConstants.Workflow_EMPLOYEE_STATUS),
                            employeeStatusChoice);

                    if(employeeStatusChoice == null || employeeStatusWorkflowStep != null) {
                        String employeeAvailabilityChoice = form.getEmployeeAvailabilityChoice();
                        WorkflowStep employeeAvailabilityWorkflowStep = employeeAvailabilityChoice == null ? null
                                : workflowControl.getWorkflowStepByName(workflowControl.getWorkflowByName(EmployeeAvailabilityConstants.Workflow_EMPLOYEE_AVAILABILITY),
                                employeeAvailabilityChoice);

                        if(employeeAvailabilityChoice == null || employeeAvailabilityWorkflowStep != null) {
                            SearchLogic searchLogic = SearchLogic.getInstance();
                            UserVisit userVisit = getUserVisit();
                            EmployeeSearchEvaluator employeeSearchEvaluator = new EmployeeSearchEvaluator(userVisit, searchType,
                                    searchLogic.getDefaultSearchDefaultOperator(null), searchLogic.getDefaultSearchSortOrder(null, searchKind),
                                    searchLogic.getDefaultSearchSortDirection(null));
                            String createdSince = form.getCreatedSince();
                            String modifiedSince = form.getModifiedSince();
                            String fields = form.getFields();

                            employeeSearchEvaluator.setFirstName(form.getFirstName());
                            employeeSearchEvaluator.setFirstNameSoundex(Boolean.valueOf(form.getFirstNameSoundex()));
                            employeeSearchEvaluator.setMiddleName(form.getMiddleName());
                            employeeSearchEvaluator.setMiddleNameSoundex(Boolean.valueOf(form.getMiddleNameSoundex()));
                            employeeSearchEvaluator.setLastName(form.getLastName());
                            employeeSearchEvaluator.setLastNameSoundex(Boolean.valueOf(form.getLastNameSoundex()));
                            employeeSearchEvaluator.setPartyEmployeeName(form.getEmployeeName());
                            employeeSearchEvaluator.setPartyName(form.getPartyName());
                            employeeSearchEvaluator.setEmployeeStatusWorkflowStep(employeeStatusWorkflowStep);
                            employeeSearchEvaluator.setEmployeeAvailabilityWorkflowStep(employeeAvailabilityWorkflowStep);
                            employeeSearchEvaluator.setCreatedSince(createdSince == null ? null : Long.valueOf(createdSince));
                            employeeSearchEvaluator.setModifiedSince(modifiedSince == null ? null : Long.valueOf(modifiedSince));
                            employeeSearchEvaluator.setFields(fields == null ? null : Splitter.on(':').trimResults().omitEmptyStrings().splitToList(fields).toArray(new String[0]));

                            result.setCount(employeeSearchEvaluator.execute(this));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownEmployeeAvailabilityChoice.name(), employeeAvailabilityChoice);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownEmployeeStatusChoice.name(), employeeStatusChoice);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownSearchTypeName.name(), SearchConstants.SearchKind_EMPLOYEE, searchTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSearchKindName.name(), SearchConstants.SearchKind_EMPLOYEE);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return result;
    }

}

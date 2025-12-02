// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.control.user.search.common.form.SearchLeavesForm;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.model.control.employee.server.logic.LeaveLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.CompanyLogic;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.employee.server.search.LeaveSearchEvaluator;
import com.echothree.model.control.search.common.SearchKinds;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.employee.common.workflow.LeaveStatusConstants;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
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
import javax.enterprise.context.Dependent;

@Dependent
public class SearchLeavesCommand
        extends BaseSimpleCommand<SearchLeavesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Leave.name(), SecurityRoles.Search.name())
                        )))
                )));
                
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LeaveName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LeaveTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LeaveReasonName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LeaveStatusChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CreatedSince", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("ModifiedSince", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("Fields", FieldType.STRING, false, null, null)
                ));
    }

    /** Creates a new instance of SearchLeavesCommand */
    public SearchLeavesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var searchLogic = SearchLogic.getInstance();
        var result = SearchResultFactory.getSearchLeavesResult();
        var searchKind = searchLogic.getSearchKindByName(this, SearchKinds.LEAVE.name());

        if(!hasExecutionErrors()) {
            var searchTypeName = form.getSearchTypeName();
            var searchType = searchLogic.getSearchTypeByName(this, searchKind, searchTypeName);

            if(!hasExecutionErrors()) {
                var partyName = form.getPartyName();
                var party = partyName == null ? null : PartyLogic.getInstance().getPartyByName(this, partyName);

                if(!hasExecutionErrors()) {
                    var partyCompany = CompanyLogic.getInstance().getPartyCompanyByName(this, form.getCompanyName(), null, null, false);

                    if(!hasExecutionErrors()) {
                        var leaveTypeName = form.getLeaveTypeName();
                        var leaveType = leaveTypeName == null ? null : LeaveLogic.getInstance().getLeaveTypeByName(this, leaveTypeName);

                        if(!hasExecutionErrors()) {
                            var leaveReasonName = form.getLeaveReasonName();
                            var leaveReason = leaveReasonName == null ? null : LeaveLogic.getInstance().getLeaveReasonByName(this, leaveReasonName);

                            if(!hasExecutionErrors()) {
                                var workflowControl = Session.getModelController(WorkflowControl.class);
                                var leaveStatusChoice = form.getLeaveStatusChoice();
                                var leaveStatusWorkflowStep = leaveStatusChoice == null ? null
                                        : workflowControl.getWorkflowStepByName(workflowControl.getWorkflowByName(LeaveStatusConstants.Workflow_LEAVE_STATUS),
                                        leaveStatusChoice);

                                if(leaveStatusChoice == null || leaveStatusWorkflowStep != null) {
                                    var userVisit = getUserVisit();
                                    var createdSince = form.getCreatedSince();
                                    var modifiedSince = form.getModifiedSince();
                                    var fields = form.getFields();

                                    var leaveSearchEvaluator = new LeaveSearchEvaluator(userVisit, searchType,
                                            searchLogic.getDefaultSearchDefaultOperator(null), searchLogic.getDefaultSearchSortOrder(null, searchKind),
                                            searchLogic.getDefaultSearchSortDirection(null));

                                    leaveSearchEvaluator.setLeaveName(form.getLeaveName());
                                    leaveSearchEvaluator.setParty(party);
                                    leaveSearchEvaluator.setCompanyParty(partyCompany == null ? null : partyCompany.getParty());
                                    leaveSearchEvaluator.setLeaveType(leaveType);
                                    leaveSearchEvaluator.setLeaveReason(leaveReason);
                                    leaveSearchEvaluator.setLeaveStatusWorkflowStep(leaveStatusWorkflowStep);
                                    leaveSearchEvaluator.setCreatedSince(createdSince == null ? null : Long.valueOf(createdSince));
                                    leaveSearchEvaluator.setModifiedSince(modifiedSince == null ? null : Long.valueOf(modifiedSince));
                                    leaveSearchEvaluator.setFields(fields == null ? null : Splitter.on(':').trimResults().omitEmptyStrings().splitToList(fields).toArray(new String[0]));

                                    result.setCount(leaveSearchEvaluator.execute(this));
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownLeaveStatusChoice.name(), leaveStatusChoice);
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return result;
    }

}

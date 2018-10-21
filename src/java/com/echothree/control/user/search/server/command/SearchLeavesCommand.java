// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.search.remote.form.SearchLeavesForm;
import com.echothree.control.user.search.remote.result.SearchLeavesResult;
import com.echothree.control.user.search.remote.result.SearchResultFactory;
import com.echothree.model.control.employee.server.logic.LeaveLogic;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.logic.CompanyLogic;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.search.common.SearchConstants;
import com.echothree.model.control.employee.server.search.LeaveSearchEvaluator;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.employee.common.workflow.LeaveStatusConstants;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.employee.server.entity.LeaveReason;
import com.echothree.model.data.employee.server.entity.LeaveType;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.model.data.search.server.entity.SearchKind;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import com.google.common.base.Splitter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SearchLeavesCommand
        extends BaseSimpleCommand<SearchLeavesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
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
    public SearchLeavesCommand(UserVisitPK userVisitPK, SearchLeavesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        SearchLogic searchLogic = SearchLogic.getInstance();
        SearchLeavesResult result = SearchResultFactory.getSearchLeavesResult();
        SearchKind searchKind = searchLogic.getSearchKindByName(null, SearchConstants.SearchKind_LEAVE);

        if(!hasExecutionErrors()) {
            String searchTypeName = form.getSearchTypeName();
            SearchType searchType = searchLogic.getSearchTypeByName(this, searchKind, searchTypeName);

            if(!hasExecutionErrors()) {
                String partyName = form.getPartyName();
                Party party = partyName == null ? null : PartyLogic.getInstance().getPartyByName(this, partyName);

                if(!hasExecutionErrors()) {
                    PartyCompany partyCompany = CompanyLogic.getInstance().getPartyCompanyByName(this, form.getCompanyName(), null, false);

                    if(!hasExecutionErrors()) {
                        String leaveTypeName = form.getLeaveTypeName();
                        LeaveType leaveType = leaveTypeName == null ? null : LeaveLogic.getInstance().getLeaveTypeByName(this, leaveTypeName);

                        if(!hasExecutionErrors()) {
                            String leaveReasonName = form.getLeaveReasonName();
                            LeaveReason leaveReason = leaveReasonName == null ? null : LeaveLogic.getInstance().getLeaveReasonByName(this, leaveReasonName);

                            if(!hasExecutionErrors()) {
                                WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
                                String leaveStatusChoice = form.getLeaveStatusChoice();
                                WorkflowStep leaveStatusWorkflowStep = leaveStatusChoice == null ? null
                                        : workflowControl.getWorkflowStepByName(workflowControl.getWorkflowByName(LeaveStatusConstants.Workflow_LEAVE_STATUS),
                                        leaveStatusChoice);

                                if(leaveStatusChoice == null || leaveStatusWorkflowStep != null) {
                                    UserVisit userVisit = getUserVisit();
                                    String createdSince = form.getCreatedSince();
                                    String modifiedSince = form.getModifiedSince();
                                    String fields = form.getFields();

                                    LeaveSearchEvaluator leaveSearchEvaluator = new LeaveSearchEvaluator(userVisit, searchType,
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

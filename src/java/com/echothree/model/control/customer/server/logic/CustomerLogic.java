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

package com.echothree.model.control.customer.server.logic;

import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.customer.common.exception.CannotSpecifyCustomerNameAndPartyNameException;
import com.echothree.model.control.customer.common.exception.MustSpecifyCustomerNameOrPartyNameException;
import com.echothree.model.control.customer.common.exception.UnknownCustomerNameException;
import com.echothree.model.control.customer.common.exception.UnknownCustomerStatusChoiceException;
import com.echothree.model.control.customer.common.workflow.CustomerStatusConstants;
import com.echothree.model.control.customer.server.CustomerControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.common.exception.UnknownPartyNameException;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.user.server.logic.UserKeyLogic;
import com.echothree.model.control.user.server.logic.UserSessionLogic;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowDestinationLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.customer.server.entity.Customer;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.Map;
import java.util.Set;

public class CustomerLogic
        extends BaseLogic {
    
    private CustomerLogic() {
        super();
    }
    
    private static class CustomerLogicHolder {
        static CustomerLogic instance = new CustomerLogic();
    }
    
    public static CustomerLogic getInstance() {
        return CustomerLogicHolder.instance;
    }

    public Customer getCustomerByName(final ExecutionErrorAccumulator eea, final String customerName, final String partyName) {
        int parameterCount = (customerName == null? 0: 1) + (partyName == null? 0: 1);
        Customer customer = null;

        if(parameterCount == 1) {
            var customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);

            if(customerName != null) {
                customer = customerControl.getCustomerByName(customerName);

                if(customer == null) {
                    handleExecutionError(UnknownCustomerNameException.class, eea, ExecutionErrors.UnknownCustomerName.name(), customerName);
                }
            } else {
                var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                Party party = partyControl.getPartyByName(partyName);

                if(party != null) {
                    PartyLogic.getInstance().checkPartyType(eea, party, PartyTypes.CUSTOMER.name());

                    customer = customerControl.getCustomer(party);
                } else {
                    handleExecutionError(UnknownPartyNameException.class, eea, ExecutionErrors.UnknownPartyName.name(), partyName);
                }
            }
        } else {
            if(parameterCount == 2) {
                handleExecutionError(CannotSpecifyCustomerNameAndPartyNameException.class, eea, ExecutionErrors.CannotSpecifyCustomerNameAndPartyName.name());
            } else {
                handleExecutionError(MustSpecifyCustomerNameOrPartyNameException.class, eea, ExecutionErrors.MustSpecifyCustomerNameOrPartyName.name());
            }
        }

        return customer;
    }

    public void setCustomerStatus(final Session session, ExecutionErrorAccumulator eea, Party party, String customerStatusChoice, PartyPK modifiedBy) {
        var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        Workflow workflow = WorkflowLogic.getInstance().getWorkflowByName(eea, CustomerStatusConstants.Workflow_CUSTOMER_STATUS);
        EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(party.getPrimaryKey());
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdate(workflow, entityInstance);
        WorkflowDestination workflowDestination = customerStatusChoice == null ? null : workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), customerStatusChoice);

        if(workflowDestination != null || customerStatusChoice == null) {
            var workflowDestinationLogic = WorkflowDestinationLogic.getInstance();
            String currentWorkflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();
            Map<String, Set<String>> map = workflowDestinationLogic.getWorkflowDestinationsAsMap(workflowDestination);
            Long triggerTime = null;

            if(currentWorkflowStepName.equals(CustomerStatusConstants.WorkflowStep_ACTIVE)) {
                if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, CustomerStatusConstants.Workflow_CUSTOMER_STATUS, CustomerStatusConstants.WorkflowStep_INACTIVE)) {
                    UserKeyLogic.getInstance().clearUserKeysByParty(party);
                    UserSessionLogic.getInstance().deleteUserSessionsByParty(party);
                }
            } else if(currentWorkflowStepName.equals(CustomerStatusConstants.WorkflowStep_INACTIVE)) {
                if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, CustomerStatusConstants.Workflow_CUSTOMER_STATUS, CustomerStatusConstants.WorkflowStep_ACTIVE)) {
                    // Nothing at this time.
                }
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, triggerTime, modifiedBy);
            }
        } else {
            handleExecutionError(UnknownCustomerStatusChoiceException.class, eea, ExecutionErrors.UnknownCustomerStatusChoice.name(), customerStatusChoice);
        }
    }

}

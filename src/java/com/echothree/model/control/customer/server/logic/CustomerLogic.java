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

package com.echothree.model.control.customer.server.logic;

import com.echothree.control.user.core.common.spec.UniversalEntitySpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.customer.common.exception.UnknownCustomerNameException;
import com.echothree.model.control.customer.common.exception.UnknownCustomerStatusChoiceException;
import com.echothree.model.control.customer.common.workflow.CustomerStatusConstants;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.common.exception.UnknownPartyNameException;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowDestinationLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.customer.server.entity.Customer;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class CustomerLogic
        extends BaseLogic {

    protected CustomerLogic() {
        super();
    }

    public static CustomerLogic getInstance() {
        return CDI.current().select(CustomerLogic.class).get();
    }

    public Customer getCustomerByName(final ExecutionErrorAccumulator eea, final String customerName, final String partyName,
            final UniversalEntitySpec universalEntitySpec) {
        var parameterCount = (customerName == null ? 0 : 1) + (partyName == null ? 0 : 1) +
                EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalEntitySpec);
        Customer customer = null;

        if(parameterCount == 1) {
            var customerControl = Session.getModelController(CustomerControl.class);
            var partyControl = Session.getModelController(PartyControl.class);

            if(customerName != null) {
                customer = customerControl.getCustomerByName(customerName);

                if(customer == null) {
                    handleExecutionError(UnknownCustomerNameException.class, eea, ExecutionErrors.UnknownCustomerName.name(), customerName);
                }
            } else if(partyName != null) {
                var party = partyControl.getPartyByName(partyName);

                if(party != null) {
                    PartyLogic.getInstance().checkPartyType(eea, party, PartyTypes.CUSTOMER.name());

                    customer = customerControl.getCustomer(party);
                } else {
                    handleExecutionError(UnknownPartyNameException.class, eea, ExecutionErrors.UnknownPartyName.name(), partyName);
                }
            } else if(universalEntitySpec != null) {
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalEntitySpec,
                        ComponentVendors.ECHO_THREE.name(), EntityTypes.Party.name());

                if(!eea.hasExecutionErrors()) {
                    var party = partyControl.getPartyByEntityInstance(entityInstance);

                    PartyLogic.getInstance().checkPartyType(eea, party, PartyTypes.CUSTOMER.name());

                    customer = customerControl.getCustomer(party);
                }
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return customer;
    }

    public void setCustomerStatus(final Session session, final ExecutionErrorAccumulator eea, final Party party, final String customerStatusChoice, final PartyPK modifiedBy) {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflow = WorkflowLogic.getInstance().getWorkflowByName(eea, CustomerStatusConstants.Workflow_CUSTOMER_STATUS);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(party.getPrimaryKey());
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdate(workflow, entityInstance);
        var workflowDestination = customerStatusChoice == null ? null : workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), customerStatusChoice);

        if(workflowDestination != null || customerStatusChoice == null) {
            var workflowDestinationLogic = WorkflowDestinationLogic.getInstance();
            var currentWorkflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();
            var map = workflowDestinationLogic.getWorkflowDestinationsAsMap(workflowDestination);
            Long triggerTime = null;

            if(currentWorkflowStepName.equals(CustomerStatusConstants.WorkflowStep_VISITOR)) {
                if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, CustomerStatusConstants.Workflow_CUSTOMER_STATUS, CustomerStatusConstants.WorkflowStep_SHOPPER)) {
                    // Nothing at this time.
                }
            } else if(currentWorkflowStepName.equals(CustomerStatusConstants.WorkflowStep_SHOPPER)) {
                // Nothing at this time.
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, triggerTime, modifiedBy);
            }
        } else {
            handleExecutionError(UnknownCustomerStatusChoiceException.class, eea, ExecutionErrors.UnknownCustomerStatusChoice.name(), customerStatusChoice);
        }
    }

}

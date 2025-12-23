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

package com.echothree.model.control.returnpolicy.server.logic;

import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.returnpolicy.common.choice.PartyReturnPolicyStatusChoicesBean;
import com.echothree.model.control.returnpolicy.common.workflow.PartyReturnPolicyStatusConstants;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.returnpolicy.server.entity.PartyReturnPolicy;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class PartyReturnPolicyLogic
        extends BaseLogic {

    protected PartyReturnPolicyLogic() {
        super();
    }

    public static PartyReturnPolicyLogic getInstance() {
        return CDI.current().select(PartyReturnPolicyLogic.class).get();
    }

    public WorkflowEntrance insertPartyReturnPolicyIntoWorkflow(EntityInstance entityInstance, BasePK createdBy) {
        var workflowControl = Session.getModelController(WorkflowControl.class);

        return workflowControl.addEntityToWorkflowUsingNames(null, PartyReturnPolicyStatusConstants.Workflow_PARTY_RETURN_POLICY_STATUS,
                PartyReturnPolicyStatusConstants.WorkflowEntrance_NEW_PARTY_RETURN_POLICY, entityInstance, null, null, createdBy);
    }

    public WorkflowEntrance insertPartyReturnPolicyIntoWorkflow(PartyReturnPolicy partyReturnPolicy, BasePK createdBy) {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(partyReturnPolicy.getPrimaryKey());

        return insertPartyReturnPolicyIntoWorkflow(entityInstance, createdBy);
    }

    public WorkflowEntityStatus getPartyReturnPolicyStatus(EntityInstance entityInstance, BasePK createdBy) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var partyReturnPolicyStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(
                PartyReturnPolicyStatusConstants.Workflow_PARTY_RETURN_POLICY_STATUS, entityInstance);

        if(partyReturnPolicyStatus == null) {
            var workflowEntrance = PartyReturnPolicyLogic.getInstance().insertPartyReturnPolicyIntoWorkflow(entityInstance, createdBy);

            if(workflowEntrance != null) {
                partyReturnPolicyStatus = getPartyReturnPolicyStatus(entityInstance, null);
            }
        }

        return partyReturnPolicyStatus;
    }

    public WorkflowEntityStatus getPartyReturnPolicyStatus(PartyReturnPolicy partyReturnPolicy, BasePK createdBy) {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(partyReturnPolicy.getPrimaryKey());

        return getPartyReturnPolicyStatus(entityInstance, createdBy);
    }

    public WorkflowEntityStatusTransfer getPartyReturnPolicyStatusTransfer(UserVisit userVisit, EntityInstance entityInstance, BasePK createdBy) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var partyReturnPolicyStatus = getPartyReturnPolicyStatus(entityInstance, createdBy);

        return partyReturnPolicyStatus == null ? null : workflowControl.getWorkflowEntityStatusTransfer(userVisit, partyReturnPolicyStatus);
    }

    public PartyReturnPolicy createPartyReturnPolicy(Party party, ReturnPolicy returnPolicy, BasePK createdBy) {
        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
        var partyReturnPolicy = returnPolicyControl.createPartyReturnPolicy(party, returnPolicy, createdBy);

        insertPartyReturnPolicyIntoWorkflow(partyReturnPolicy, createdBy);

        return partyReturnPolicy;
    }

    public PartyReturnPolicyStatusChoicesBean getPartyReturnPolicyStatusChoices(final String defaultOrderStatusChoice, final Language language, final boolean allowNullChoice,
            final PartyReturnPolicy partyReturnPolicy, final PartyPK partyPK) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var partyReturnPolicyStatusChoicesBean = new PartyReturnPolicyStatusChoicesBean();

        if(partyReturnPolicy == null) {
            workflowControl.getWorkflowEntranceChoices(partyReturnPolicyStatusChoicesBean, defaultOrderStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(PartyReturnPolicyStatusConstants.Workflow_PARTY_RETURN_POLICY_STATUS), partyPK);
        } else {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(partyReturnPolicy.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(PartyReturnPolicyStatusConstants.Workflow_PARTY_RETURN_POLICY_STATUS, entityInstance);

            workflowControl.getWorkflowDestinationChoices(partyReturnPolicyStatusChoicesBean, defaultOrderStatusChoice, language, allowNullChoice, workflowEntityStatus.getWorkflowStep(), partyPK);
        }

        return partyReturnPolicyStatusChoicesBean;
    }

    public void setPartyReturnPolicyStatus(final ExecutionErrorAccumulator eea, final PartyReturnPolicy partyReturnPolicy, final String orderStatusChoice, final PartyPK modifiedBy) {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(partyReturnPolicy.getPrimaryKey());
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(PartyReturnPolicyStatusConstants.Workflow_PARTY_RETURN_POLICY_STATUS,
                entityInstance);
        var workflowDestination = orderStatusChoice == null? null: workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), orderStatusChoice);

        if(workflowDestination != null || orderStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownPartyReturnPolicyStatusChoice.name(), orderStatusChoice);
        }
    }

}

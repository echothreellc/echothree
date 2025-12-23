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

package com.echothree.model.control.cancellationpolicy.server.logic;

import com.echothree.model.control.cancellationpolicy.common.choice.PartyCancellationPolicyStatusChoicesBean;
import com.echothree.model.control.cancellationpolicy.common.workflow.PartyCancellationPolicyStatusConstants;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.cancellationpolicy.server.entity.PartyCancellationPolicy;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
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
public class PartyCancellationPolicyLogic
        extends BaseLogic {

    protected PartyCancellationPolicyLogic() {
        super();
    }

    public static PartyCancellationPolicyLogic getInstance() {
        return CDI.current().select(PartyCancellationPolicyLogic.class).get();
    }

    public WorkflowEntrance insertPartyCancellationPolicyIntoWorkflow(EntityInstance entityInstance, BasePK createdBy) {
        var workflowControl = Session.getModelController(WorkflowControl.class);

        return workflowControl.addEntityToWorkflowUsingNames(null, PartyCancellationPolicyStatusConstants.Workflow_PARTY_CANCELLATION_POLICY_STATUS,
                PartyCancellationPolicyStatusConstants.WorkflowEntrance_NEW_PARTY_CANCELLATION_POLICY, entityInstance, null, null, createdBy);
    }

    public WorkflowEntrance insertPartyCancellationPolicyIntoWorkflow(PartyCancellationPolicy partyCancellationPolicy, BasePK createdBy) {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(partyCancellationPolicy.getPrimaryKey());

        return insertPartyCancellationPolicyIntoWorkflow(entityInstance, createdBy);
    }

    public WorkflowEntityStatus getPartyCancellationPolicyStatus(EntityInstance entityInstance, BasePK createdBy) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var partyCancellationPolicyStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(
                PartyCancellationPolicyStatusConstants.Workflow_PARTY_CANCELLATION_POLICY_STATUS, entityInstance);

        if(partyCancellationPolicyStatus == null) {
            var workflowEntrance = PartyCancellationPolicyLogic.getInstance().insertPartyCancellationPolicyIntoWorkflow(entityInstance, createdBy);

            if(workflowEntrance != null) {
                partyCancellationPolicyStatus = getPartyCancellationPolicyStatus(entityInstance, null);
            }
        }

        return partyCancellationPolicyStatus;
    }

    public WorkflowEntityStatus getPartyCancellationPolicyStatus(PartyCancellationPolicy partyCancellationPolicy, BasePK createdBy) {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(partyCancellationPolicy.getPrimaryKey());

        return getPartyCancellationPolicyStatus(entityInstance, createdBy);
    }

    public WorkflowEntityStatusTransfer getPartyCancellationPolicyStatusTransfer(UserVisit userVisit, EntityInstance entityInstance, BasePK createdBy) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var partyCancellationPolicyStatus = getPartyCancellationPolicyStatus(entityInstance, createdBy);

        return partyCancellationPolicyStatus == null ? null : workflowControl.getWorkflowEntityStatusTransfer(userVisit, partyCancellationPolicyStatus);
    }

    public PartyCancellationPolicy createPartyCancellationPolicy(Party party, CancellationPolicy cancellationPolicy, BasePK createdBy) {
        var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
        var partyCancellationPolicy = cancellationPolicyControl.createPartyCancellationPolicy(party, cancellationPolicy, createdBy);

        insertPartyCancellationPolicyIntoWorkflow(partyCancellationPolicy, createdBy);

        return partyCancellationPolicy;
    }

    public PartyCancellationPolicyStatusChoicesBean getPartyCancellationPolicyStatusChoices(final String defaultOrderStatusChoice, final Language language, final boolean allowNullChoice,
            final PartyCancellationPolicy partyCancellationPolicy, final PartyPK partyPK) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var partyCancellationPolicyStatusChoicesBean = new PartyCancellationPolicyStatusChoicesBean();

        if(partyCancellationPolicy == null) {
            workflowControl.getWorkflowEntranceChoices(partyCancellationPolicyStatusChoicesBean, defaultOrderStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(PartyCancellationPolicyStatusConstants.Workflow_PARTY_CANCELLATION_POLICY_STATUS), partyPK);
        } else {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(partyCancellationPolicy.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(PartyCancellationPolicyStatusConstants.Workflow_PARTY_CANCELLATION_POLICY_STATUS, entityInstance);

            workflowControl.getWorkflowDestinationChoices(partyCancellationPolicyStatusChoicesBean, defaultOrderStatusChoice, language, allowNullChoice, workflowEntityStatus.getWorkflowStep(), partyPK);
        }

        return partyCancellationPolicyStatusChoicesBean;
    }

    public void setPartyCancellationPolicyStatus(final ExecutionErrorAccumulator eea, final PartyCancellationPolicy partyCancellationPolicy, final String orderStatusChoice, final PartyPK modifiedBy) {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(partyCancellationPolicy.getPrimaryKey());
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(PartyCancellationPolicyStatusConstants.Workflow_PARTY_CANCELLATION_POLICY_STATUS,
                entityInstance);
        var workflowDestination = orderStatusChoice == null? null: workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), orderStatusChoice);

        if(workflowDestination != null || orderStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownPartyCancellationPolicyStatusChoice.name(), orderStatusChoice);
        }
    }

}

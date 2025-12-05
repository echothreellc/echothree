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

package com.echothree.model.control.workflow.server.logic;

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.order.server.trigger.OrderTrigger;
import com.echothree.model.control.printer.server.trigger.PrinterGroupJobTrigger;
import com.echothree.model.control.training.server.trigger.PartyTrainingClassTrigger;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.trigger.EntityTypeTrigger;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.workflow.server.entity.WorkflowTrigger;
import com.echothree.model.data.workflow.server.factory.WorkflowTriggerFactory;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class WorkflowTriggerLogic {

    protected WorkflowTriggerLogic() {
        super();
    }

    public static WorkflowTriggerLogic getInstance() {
        return CDI.current().select(WorkflowTriggerLogic.class).get();
    }
    
    // TODO: configure using a property file.
    private EntityTypeTrigger locateTrigger(final ExecutionErrorAccumulator eea, final String componentVendorName, final String entityTypeName) {
        EntityTypeTrigger result = null;
        
        if(componentVendorName.equals(ComponentVendors.ECHO_THREE.name())) {
            if(entityTypeName.equals(EntityTypes.PartyTrainingClass.name())) {
                result = new PartyTrainingClassTrigger();
            } else if(entityTypeName.equals(EntityTypes.PrinterGroupJob.name())) {
                result = new PrinterGroupJobTrigger();
            } else if(entityTypeName.equals(EntityTypes.Order.name())) {
                result = new OrderTrigger();
            }
        }
        
        if(result == null) {
            eea.addExecutionError(ExecutionErrors.UnknownGeneralTrigger.name(), componentVendorName, entityTypeName);
        }
        
        return result;
    }
    
    private void processWorkflowTrigger(final Session session, final ExecutionErrorAccumulator eea, final WorkflowTrigger workflowTrigger, final PartyPK triggeredBy) {
        var workflowEntityStatus = workflowTrigger.getWorkflowEntityStatusForUpdate();
        var entityInstance = workflowEntityStatus.getEntityInstance();
        var entityTypeDetail = entityInstance.getEntityType().getLastDetail();
        var entityTypeName = entityTypeDetail.getEntityTypeName();
        var componentVendorName = entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName();
        var entityTypeTrigger = locateTrigger(eea, componentVendorName, entityTypeName);
        
        if(eea == null || !eea.hasExecutionErrors()) {
            entityTypeTrigger.handleTrigger(session, eea, workflowEntityStatus, triggeredBy);
        }
    }
    
    public void processWorkflowTriggers(final Session session, final ExecutionErrorAccumulator eea, final PartyPK triggeredBy) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var remainingTime = 1L * 60 * 1000; // 1 minute
        
        for(var workflowTrigger : workflowControl.getWorkflowTriggersByTriggerTime(session.getStartTimeLong())) {
            var errorsOccurred = workflowTrigger.getErrorsOccurred();

            if(errorsOccurred == null || errorsOccurred == false) {
                var startTime = System.currentTimeMillis();

                workflowTrigger = WorkflowTriggerFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, workflowTrigger.getPrimaryKey());
                processWorkflowTrigger(session, eea, workflowTrigger, triggeredBy);

                if(eea.hasExecutionErrors()) {
                    workflowTrigger.setErrorsOccurred(true);
                    break;
                } else {
                    remainingTime -= System.currentTimeMillis() - startTime;
                    if(remainingTime < 0) {
                        break;
                    }
                }
            }
        }
    }

}

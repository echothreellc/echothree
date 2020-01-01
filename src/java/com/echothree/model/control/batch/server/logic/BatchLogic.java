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

package com.echothree.model.control.batch.server.logic;

import com.echothree.model.control.batch.common.exception.UnknownBatchAliasTypeNameException;
import com.echothree.model.control.batch.common.exception.UnknownBatchTypeEntityTypeException;
import com.echothree.model.control.batch.common.exception.UnknownBatchTypeNameException;
import com.echothree.model.control.batch.server.BatchControl;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.batch.server.entity.BatchAliasType;
import com.echothree.model.data.batch.server.entity.BatchEntity;
import com.echothree.model.data.batch.server.entity.BatchType;
import com.echothree.model.data.batch.server.entity.BatchTypeDetail;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.EntityTypeDetail;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.Session;

public class BatchLogic
        extends BaseLogic {

    private BatchLogic() {
        super();
    }

    private static class BatchLogicHolder {
        static BatchLogic instance = new BatchLogic();
    }

    public static BatchLogic getInstance() {
        return BatchLogicHolder.instance;
    }
    
    public SequenceType getBatchSequenceType(final ExecutionErrorAccumulator eea, final BatchType batchType) {
        SequenceType sequenceType = null;
        BatchType parentBatchType = batchType;

        do {
            BatchTypeDetail batchTypeDetail = parentBatchType.getLastDetail();

            sequenceType = batchTypeDetail.getBatchSequenceType();

            if(sequenceType == null) {
                parentBatchType = batchTypeDetail.getParentBatchType();
            } else {
                break;
            }
        } while(parentBatchType != null);

        if(sequenceType == null) {
            var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);

            sequenceType = sequenceControl.getDefaultSequenceType();
        }

        if(sequenceType == null) {
            eea.addExecutionError(ExecutionErrors.UnknownBatchSequenceType.name(), batchType.getLastDetail().getBatchTypeName());
        }

        return sequenceType;
    }

    public Sequence getBatchSequence(final ExecutionErrorAccumulator eea, final BatchType batchType) {
        Sequence sequence = null;
        SequenceType sequenceType = getBatchSequenceType(eea, batchType);

        if(eea == null || !eea.hasExecutionErrors()) {
            var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);

            sequence = sequenceControl.getDefaultSequence(sequenceType);
        }

        if(sequence == null) {
            eea.addExecutionError(ExecutionErrors.UnknownBatchSequence.name(), batchType.getLastDetail().getBatchTypeName());
        }

        return sequence;
    }

    public String getBatchName(final ExecutionErrorAccumulator eea, final BatchType batchType) {
        String batchName = null;
        Sequence sequence = getBatchSequence(eea, batchType);

        if(eea == null || !eea.hasExecutionErrors()) {
            var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);

            batchName = sequenceControl.getNextSequenceValue(sequence);
        }

        return batchName;
    }

    public Batch createBatch(final ExecutionErrorAccumulator eea, final String batchTypeName, final BasePK createdBy) {
        var batchControl = (BatchControl)Session.getModelController(BatchControl.class);
        BatchType batchType = batchControl.getBatchTypeByName(batchTypeName);
        Batch batch = null;

        if(batchType != null) {
            var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
            String batchName = getBatchName(eea, batchType);

            if(eea == null || !eea.hasExecutionErrors()) {
                BatchTypeDetail batchTypeDetail = batchType.getLastDetail();
                WorkflowEntrance workflowEntrance = batchTypeDetail.getBatchWorkflowEntrance();

                if(workflowEntrance == null) {
                    Workflow workflow = batchTypeDetail.getBatchWorkflow();

                    if(workflow != null) {
                        workflowEntrance = workflowControl.getDefaultWorkflowEntrance(workflow);

                        if(workflowEntrance == null) {
                            eea.addExecutionError(ExecutionErrors.MissingDefaultWorkflowEntrance.name(), workflow.getLastDetail().getWorkflowName());
                        }
                    }
                }

                if(eea == null || !eea.hasExecutionErrors()) {
                    batch = batchControl.createBatch(batchType, batchName, createdBy);

                    if(workflowEntrance != null) {
                        var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
                        EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(batch.getPrimaryKey());

                        // TODO: A WorkEffort should be created for the batch entry, if it's manually entered.
                        workflowControl.addEntityToWorkflow(workflowEntrance, entityInstance, null, null, createdBy);
                    }
                }
            }
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownBatchTypeName.name(), batchTypeName);
        }

        return batch;
    }

    public void deleteBatch(final ExecutionErrorAccumulator eea, final Batch batch, final BasePK deletedBy) {
        var batchControl = (BatchControl)Session.getModelController(BatchControl.class);

        batchControl.deleteBatch(batch, deletedBy);
    }

    public BatchType getBatchTypeByName(final ExecutionErrorAccumulator eea, final String batchTypeName) {
        var batchControl = (BatchControl)Session.getModelController(BatchControl.class);
        BatchType batchType = batchControl.getBatchTypeByName(batchTypeName);

        if(batchType == null) {
            handleExecutionError(UnknownBatchTypeNameException.class, eea, ExecutionErrors.UnknownBatchTypeName.name(), batchTypeName);
        }

        return batchType;
    }

    public BatchAliasType getBatchAliasTypeByName(final ExecutionErrorAccumulator eea, final BatchType batchType, final String batchAliasTypeName) {
        var batchControl = (BatchControl)Session.getModelController(BatchControl.class);
        BatchAliasType batchAliasType = batchControl.getBatchAliasTypeByName(batchType, batchAliasTypeName);

        if(batchAliasType == null) {
            handleExecutionError(UnknownBatchAliasTypeNameException.class, eea, ExecutionErrors.UnknownBatchAliasTypeName.name(),
                    batchType.getLastDetail().getBatchTypeName(), batchAliasTypeName);
        }

        return batchAliasType;
    }

    public Batch getBatchByName(final ExecutionErrorAccumulator eea, final String batchTypeName, final String batchName) {
        var batchControl = (BatchControl)Session.getModelController(BatchControl.class);
        BatchType batchType = getBatchTypeByName(eea, batchTypeName);
        Batch batch = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            batch = batchControl.getBatchByName(batchType, batchName);

            if(batch == null) {
                eea.addExecutionError(ExecutionErrors.UnknownBatchName.name(), batchTypeName, batchName);
            }
        }

        return batch;
    }

    public Batch getBatchByNameForUpdate(final ExecutionErrorAccumulator eea, final String batchTypeName, final String batchName) {
        var batchControl = (BatchControl)Session.getModelController(BatchControl.class);
        BatchType batchType = getBatchTypeByName(eea, batchTypeName);
        Batch batch = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            batch = batchControl.getBatchByNameForUpdate(batchType, batchName);

            if(batch == null) {
                eea.addExecutionError(ExecutionErrors.UnknownBatchName.name(), batchTypeName, batchName);
            }
        }

        return batch;
    }

    public BatchEntity createBatchEntity(final ExecutionErrorAccumulator eea, final BaseEntity baseEntity, final Batch batch, final BasePK createdBy) {
        return createBatchEntity(eea, getEntityInstanceByBaseEntity(baseEntity), batch, createdBy);
    }
    
    public BatchEntity createBatchEntity(final ExecutionErrorAccumulator eea, final EntityInstance entityInstance, final Batch batch, final BasePK createdBy) {
        var batchControl = (BatchControl)Session.getModelController(BatchControl.class);
        BatchType batchType = batch.getLastDetail().getBatchType();
        BatchEntity batchEntity = null;

        if(batchControl.countBatchTypeEntityTypesByBatchType(batchType) != 0) {
            EntityType entityType = entityInstance.getEntityType();

            if(!batchControl.getBatchTypeEntityTypeExists(batchType, entityType)) {
                EntityTypeDetail entityTypeDetail = entityType.getLastDetail();

                handleExecutionError(UnknownBatchTypeEntityTypeException.class, eea, ExecutionErrors.UnknownBatchTypeEntityType.name(),
                        batchType.getLastDetail().getBatchTypeName(), entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                        entityTypeDetail.getEntityTypeName());
            }
        }

        if(!hasExecutionErrors(eea)) {
            batchEntity = batchControl.createBatchEntity(entityInstance, batch, createdBy);
        }

        return batchEntity;
    }

    public boolean batchEntityExists(final BaseEntity baseEntity, final Batch batch) {
        return batchEntityExists(getEntityInstanceByBaseEntity(baseEntity), batch);
    }
    
    public boolean batchEntityExists(final EntityInstance entityInstance, final Batch batch) {
        var batchControl = (BatchControl)Session.getModelController(BatchControl.class);
        
        return batchControl.batchEntityExists(entityInstance, batch);
    }
    
    public void deleteBatchEntity(BatchEntity batchEntity, BasePK deletedBy) {
        var batchControl = (BatchControl)Session.getModelController(BatchControl.class);

        batchControl.deleteBatchEntity(batchEntity, deletedBy);
    }

}

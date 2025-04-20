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

package com.echothree.model.control.sales.server.logic;

import com.echothree.model.control.batch.common.BatchConstants;
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.control.batch.server.logic.BatchLogic;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.order.server.control.OrderBatchControl;
import com.echothree.model.control.order.server.control.OrderControl;
import com.echothree.model.control.sales.common.choice.SalesOrderBatchStatusChoicesBean;
import com.echothree.model.control.sales.common.exception.CannotDeleteSalesOrderBatchInUseException;
import com.echothree.model.control.sales.common.exception.IncorrectSalesOrderBatchAmountException;
import com.echothree.model.control.sales.common.exception.IncorrectSalesOrderBatchCountException;
import com.echothree.model.control.sales.common.exception.InvalidSalesOrderBatchStatusException;
import com.echothree.model.control.sales.common.exception.InvalidSalesOrderStatusException;
import com.echothree.model.control.sales.common.exception.UnknownSalesOrderBatchStatusChoiceException;
import com.echothree.model.control.sales.common.workflow.SalesOrderBatchStatusConstants;
import com.echothree.model.control.sales.common.workflow.SalesOrderStatusConstants;
import com.echothree.model.control.sales.server.control.SalesOrderBatchControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowDestinationLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowStepLogic;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.batch.server.entity.BatchEntity;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;
import java.util.ArrayList;
import java.util.List;

public class SalesOrderBatchLogic
        extends BaseLogic {

    private SalesOrderBatchLogic() {
        super();
    }

    private static class BatchLogicHolder {
        static SalesOrderBatchLogic instance = new SalesOrderBatchLogic();
    }

    public static SalesOrderBatchLogic getInstance() {
        return BatchLogicHolder.instance;
    }
    
    public Batch createBatch(final ExecutionErrorAccumulator eea, final Currency currency, final PaymentMethod paymentMethod, final Long count,
            final Long amount, final BasePK createdBy) {
        var salesOrderBatchControl = Session.getModelController(SalesOrderBatchControl.class);
        var batch = BatchLogic.getInstance().createBatch(eea, BatchConstants.BatchType_SALES_ORDER, createdBy);

        if(!eea.hasExecutionErrors()) {
            var orderBatchControl = Session.getModelController(OrderBatchControl.class);

            orderBatchControl.createOrderBatch(batch, currency, count, amount, createdBy);
            salesOrderBatchControl.createSalesOrderBatch(batch, paymentMethod, createdBy);
        }

        return batch;
    }
    
    public boolean checkBatchInWorkflowSteps(final ExecutionErrorAccumulator eea, final Batch batch, final String... workflowStepNames) {
        return !WorkflowStepLogic.getInstance().isEntityInWorkflowSteps(eea, SalesOrderBatchStatusConstants.Workflow_SALES_ORDER_BATCH_STATUS,
                getEntityInstanceByBaseEntity(batch), workflowStepNames).isEmpty();
    }

    public boolean checkBatchAvailableForEntry(final ExecutionErrorAccumulator eea, final Batch batch) {
        return checkBatchInWorkflowSteps(eea, batch, SalesOrderBatchStatusConstants.WorkflowStep_ENTRY);
    }
    
    public BatchEntity createBatchEntity(final ExecutionErrorAccumulator eea, final Order order, final Batch batch, final BasePK createdBy) {
        return BatchLogic.getInstance().createBatchEntity(eea, getEntityInstanceByBaseEntity(order), batch, createdBy);
    }

    public boolean batchEntryExists(final ExecutionErrorAccumulator eea, final Order order, final Batch batch, final String... workflowStepNames) {
        var result = false;
        
        if(workflowStepNames.length == 0) {
            result = BatchLogic.getInstance().batchEntityExists(order, batch);
        } else {
            if(!WorkflowStepLogic.getInstance().isEntityInWorkflowSteps(eea, SalesOrderBatchStatusConstants.Workflow_SALES_ORDER_BATCH_STATUS,
                    getEntityInstanceByBaseEntity(order), workflowStepNames).isEmpty()) {
                result = true;
            } else {
                handleExecutionError(InvalidSalesOrderBatchStatusException.class, eea, ExecutionErrors.InvalidSalesOrderBatchStatus.name(),
                        batch.getLastDetail().getBatchName());
            }
        }
        
        return result;
    }
    
    public void deleteBatch(final ExecutionErrorAccumulator eea, final Batch batch, final BasePK deletedBy) {
        var batchControl = Session.getModelController(BatchControl.class);

        if(batchControl.countBatchEntitiesByBatch(batch) == 0) {
            BatchLogic.getInstance().deleteBatch(eea, batch, deletedBy);

            if(eea == null || !eea.hasExecutionErrors()) {
                var orderBatchControl = Session.getModelController(OrderBatchControl.class);
                var salesOrderBatchControl = Session.getModelController(SalesOrderBatchControl.class);

                orderBatchControl.deleteOrderBatch(batch, deletedBy);
                salesOrderBatchControl.deleteSalesOrderBatch(batch, deletedBy);
            }
        } else {
            handleExecutionError(CannotDeleteSalesOrderBatchInUseException.class, eea, ExecutionErrors.CannotDeleteSalesOrderBatchInUse.name(),
                    batch.getLastDetail().getBatchName());
        }
    }

    public Batch getBatchByName(final ExecutionErrorAccumulator eea, final String batchName) {
        return BatchLogic.getInstance().getBatchByName(eea, BatchConstants.BatchType_SALES_ORDER, batchName);
    }

    public Batch getBatchByNameForUpdate(final ExecutionErrorAccumulator eea, final String batchName) {
        return BatchLogic.getInstance().getBatchByNameForUpdate(eea, BatchConstants.BatchType_SALES_ORDER, batchName);
    }

    public SalesOrderBatchStatusChoicesBean getSalesOrderBatchStatusChoices(String defaultSalesOrderBatchStatusChoice, Language language, boolean allowNullChoice,
            Batch batch, PartyPK partyPK) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var salesOrderBatchStatusChoicesBean = new SalesOrderBatchStatusChoicesBean();

        if(batch == null) {
            workflowControl.getWorkflowEntranceChoices(salesOrderBatchStatusChoicesBean, defaultSalesOrderBatchStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(SalesOrderBatchStatusConstants.Workflow_SALES_ORDER_BATCH_STATUS), partyPK);
        } else {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(batch.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(SalesOrderBatchStatusConstants.Workflow_SALES_ORDER_BATCH_STATUS,
                    entityInstance);

            workflowControl.getWorkflowDestinationChoices(salesOrderBatchStatusChoicesBean, defaultSalesOrderBatchStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }

        return salesOrderBatchStatusChoicesBean;
    }

    public void setSalesOrderBatchStatus(final Session session, ExecutionErrorAccumulator eea, Batch batch, String salesOrderBatchStatusChoice, PartyPK modifiedBy) {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var orderControl = Session.getModelController(OrderControl.class);
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflow = WorkflowLogic.getInstance().getWorkflowByName(eea, SalesOrderBatchStatusConstants.Workflow_SALES_ORDER_BATCH_STATUS);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(batch.getPrimaryKey());
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdate(workflow, entityInstance);
        var workflowDestination = salesOrderBatchStatusChoice == null ? null : workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), salesOrderBatchStatusChoice);

        if(workflowDestination != null || salesOrderBatchStatusChoice == null) {
            var workflowDestinationLogic = WorkflowDestinationLogic.getInstance();
            var currentWorkflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();
            var map = workflowDestinationLogic.getWorkflowDestinationsAsMap(workflowDestination);
            Long triggerTime = null;
            
            if(currentWorkflowStepName.equals(SalesOrderBatchStatusConstants.WorkflowStep_ENTRY)) {
                if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, SalesOrderBatchStatusConstants.Workflow_SALES_ORDER_BATCH_STATUS, SalesOrderBatchStatusConstants.WorkflowStep_AUDIT)) {
                    var batchControl = Session.getModelController(BatchControl.class);
                    var batchEntities = batchControl.getBatchEntitiesByBatch(batch);
                    
                    // Verify all orders are in BATCH_AUDIT.
                    batchEntities.stream().map((batchEntity) -> workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(SalesOrderStatusConstants.Workflow_SALES_ORDER_STATUS, batchEntity.getEntityInstance())).forEach((orderWorkflowEntityStatus) -> {
                        var orderEntityInstance = orderWorkflowEntityStatus.getEntityInstance();
                        var orderWorkflowStepName = orderWorkflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();
                        if (!orderWorkflowStepName.equals(SalesOrderStatusConstants.WorkflowStep_BATCH_AUDIT)) {
                            var order = orderControl.getOrderByEntityInstance(orderEntityInstance);
                            
                            handleExecutionError(InvalidSalesOrderStatusException.class, eea, ExecutionErrors.InvalidSalesOrderStatus.name(),
                                    order.getLastDetail().getOrderName(), orderWorkflowStepName);
                        }
                    });
                }
            } else if(currentWorkflowStepName.equals(SalesOrderBatchStatusConstants.WorkflowStep_AUDIT)) {
                if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, SalesOrderBatchStatusConstants.Workflow_SALES_ORDER_BATCH_STATUS, SalesOrderBatchStatusConstants.WorkflowStep_COMPLETE)) {
                    var orderBatchControl = Session.getModelController(OrderBatchControl.class);
                    var orderBatch = orderBatchControl.getOrderBatch(batch);
                    var count = orderBatch.getCount();
                    var amount = orderBatch.getAmount();

                    if(count != null) {
                        var batchControl = Session.getModelController(BatchControl.class);
                        Long batchCount = batchControl.countBatchEntitiesByBatch(batch);

                        if(!count.equals(batchCount)) {
                            handleExecutionError(IncorrectSalesOrderBatchCountException.class, eea, ExecutionErrors.IncorrectSalesOrderBatchCount.name(),
                                    count.toString(), batchCount.toString());
                        }
                    }

                    if(amount != null) {
                        var batchAmount = getBatchOrderTotalsWithAdjustments(batch);
                        
                        if(!amount.equals(batchAmount)) {
                            var currency = orderBatch.getCurrency();

                            handleExecutionError(IncorrectSalesOrderBatchAmountException.class, eea, ExecutionErrors.IncorrectSalesOrderBatchAmount.name(),
                                    AmountUtils.getInstance().formatPriceUnit(currency, amount),
                                    AmountUtils.getInstance().formatPriceUnit(currency, batchAmount));
                        }
                    }
                }
            }
            
            if(eea == null || !eea.hasExecutionErrors()) {
                workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, triggerTime, modifiedBy);
            }
        } else {
            handleExecutionError(UnknownSalesOrderBatchStatusChoiceException.class, eea, ExecutionErrors.UnknownSalesOrderBatchStatusChoice.name(), salesOrderBatchStatusChoice);
        }
    }

    public List<Order> getBatchOrders(final Batch batch) {
        var batchControl = Session.getModelController(BatchControl.class);
        var orderControl = Session.getModelController(OrderControl.class);
        var batchEntities = batchControl.getBatchEntitiesByBatch(batch);
        List<Order> orders = new ArrayList<>(batchEntities.size());

        batchEntities.forEach((batchEntity) -> {
            orders.add(orderControl.convertEntityInstanceToOrder(batchEntity.getEntityInstance()));
        });

        return orders;
    }

    public Long getBatchOrderTotalsWithAdjustments(Batch batch) {
        var salesOrderLineLogic = SalesOrderLineLogic.getInstance();
        var orders = getBatchOrders(batch);
        long total = 0;

        total = orders.stream().map((order) -> salesOrderLineLogic.getOrderTotalWithAdjustments(order)).reduce(total, (accumulator, _item) -> accumulator + _item);

        return total;
    }

}

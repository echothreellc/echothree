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

import com.echothree.control.user.search.common.form.SearchSalesOrderBatchesForm;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.model.control.accounting.server.logic.CurrencyLogic;
import com.echothree.model.control.batch.common.BatchConstants;
import com.echothree.model.control.batch.server.logic.BatchLogic;
import com.echothree.model.control.payment.server.logic.PaymentMethodLogic;
import com.echothree.model.control.sales.server.search.SalesOrderBatchSearchEvaluator;
import com.echothree.model.control.search.common.SearchKinds;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.control.sales.common.workflow.SalesOrderBatchStatusConstants;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import com.google.common.base.Splitter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SearchSalesOrderBatchesCommand
        extends BaseSimpleCommand<SearchSalesOrderBatchesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("BatchName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PaymentMethodName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SalesOrderBatchStatusChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("BatchAliasTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Alias", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CreatedSince", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("ModifiedSince", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("Fields", FieldType.STRING, false, null, null)
                ));
    }

    /** Creates a new instance of SearchSalesOrderBatchesCommand */
    public SearchSalesOrderBatchesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var searchLogic = SearchLogic.getInstance();
        var result = SearchResultFactory.getSearchSalesOrderBatchesResult();
        var searchKind = searchLogic.getSearchKindByName(this, SearchKinds.SALES_ORDER_BATCH.name());

        if(!hasExecutionErrors()) {
            var searchTypeName = form.getSearchTypeName();
            var searchType = searchLogic.getSearchTypeByName(this, searchKind, searchTypeName);

            if(!hasExecutionErrors()) {
                var currencyIsoName = form.getCurrencyIsoName();
                var currency = currencyIsoName == null ? null : CurrencyLogic.getInstance().getCurrencyByName(this, currencyIsoName);

                if(!hasExecutionErrors()) {
                    var paymentMethodName = form.getPaymentMethodName();
                    var paymentMethod = paymentMethodName == null ? null : PaymentMethodLogic.getInstance().getPaymentMethodByName(this, paymentMethodName);

                    if(!hasExecutionErrors()) {
                        var workflowControl = Session.getModelController(WorkflowControl.class);
                        var salesOrderBatchStatusChoice = form.getSalesOrderBatchStatusChoice();
                        var salesOrderBatchStatusWorkflowStep = salesOrderBatchStatusChoice == null ? null :
                            workflowControl.getWorkflowStepByName(workflowControl.getWorkflowByName(SalesOrderBatchStatusConstants.Workflow_SALES_ORDER_BATCH_STATUS), salesOrderBatchStatusChoice);

                        if(salesOrderBatchStatusChoice == null || salesOrderBatchStatusChoice != null) {
                            var batchLogic = BatchLogic.getInstance();
                            var batchType = batchLogic.getBatchTypeByName(this, BatchConstants.BatchType_SALES_ORDER);

                            if(!hasExecutionErrors()) {
                                var batchAliasTypeName = form.getBatchAliasTypeName();
                                var batchAliasType = batchAliasTypeName == null ? null : batchLogic.getBatchAliasTypeByName(this, batchType, batchAliasTypeName);

                                if(!hasExecutionErrors()) {
                                    var salesOrderBatchSearchEvaluator = new SalesOrderBatchSearchEvaluator(getUserVisit(), searchType, searchLogic.getDefaultSearchDefaultOperator(null), searchLogic.getDefaultSearchSortOrder(null, searchKind), searchLogic.getDefaultSearchSortDirection(null));
                                    var createdSince = form.getCreatedSince();
                                    var modifiedSince = form.getModifiedSince();
                                    var fields = form.getFields();

                                    salesOrderBatchSearchEvaluator.setCurrency(currency);
                                    salesOrderBatchSearchEvaluator.setPaymentMethod(paymentMethod);
                                    salesOrderBatchSearchEvaluator.setBatchStatusWorkflowStep(salesOrderBatchStatusWorkflowStep);
                                    salesOrderBatchSearchEvaluator.setBatchAliasType(batchAliasType);
                                    salesOrderBatchSearchEvaluator.setAlias(form.getAlias());
                                    salesOrderBatchSearchEvaluator.setCreatedSince(createdSince == null ? null : Long.valueOf(createdSince));
                                    salesOrderBatchSearchEvaluator.setModifiedSince(modifiedSince == null ? null : Long.valueOf(modifiedSince));
                                    salesOrderBatchSearchEvaluator.setFields(fields == null ? null : Splitter.on(':').trimResults().omitEmptyStrings().splitToList(fields).toArray(new String[0]));

                                    result.setCount(salesOrderBatchSearchEvaluator.execute(this));
                                }
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownSalesOrderBatchStatusChoice.name(), salesOrderBatchStatusChoice);
                        }                        
                    }
                }
            }
        }
        
        return result;
    }
}

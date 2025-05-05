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

import com.echothree.control.user.search.common.form.SearchSalesOrdersForm;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.model.control.order.common.OrderTypes;
import com.echothree.model.control.order.server.logic.OrderLogic;
import com.echothree.model.control.sales.common.workflow.SalesOrderStatusConstants;
import com.echothree.model.control.sales.server.search.SalesOrderSearchEvaluator;
import com.echothree.model.control.search.common.SearchKinds;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import com.google.common.base.Splitter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SearchSalesOrdersCommand
        extends BaseSimpleCommand<SearchSalesOrdersForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PaymentMethodName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SalesOrderStatusChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("OrderAliasTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Alias", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CreatedSince", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("ModifiedSince", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("Fields", FieldType.STRING, false, null, null)
                ));
    }

    /** Creates a new instance of SearchSalesOrdersCommand */
    public SearchSalesOrdersCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var searchLogic = SearchLogic.getInstance();
        var result = SearchResultFactory.getSearchSalesOrdersResult();
        var searchKind = searchLogic.getSearchKindByName(this, SearchKinds.SALES_ORDER.name());

        if(!hasExecutionErrors()) {
            var searchTypeName = form.getSearchTypeName();
            var searchType = searchLogic.getSearchTypeByName(this, searchKind, searchTypeName);

            if(!hasExecutionErrors()) {
                var workflowControl = Session.getModelController(WorkflowControl.class);
                var salesOrderStatusChoice = form.getSalesOrderStatusChoice();
                var salesOrderStatusWorkflowStep = salesOrderStatusChoice == null ? null :
                    workflowControl.getWorkflowStepByName(workflowControl.getWorkflowByName(SalesOrderStatusConstants.Workflow_SALES_ORDER_STATUS), salesOrderStatusChoice);

                if(salesOrderStatusChoice == null || salesOrderStatusChoice != null) {
                    var orderLogic = OrderLogic.getInstance();
                    var orderType = orderLogic.getOrderTypeByName(this, OrderTypes.SALES_ORDER.name());

                    if(!hasExecutionErrors()) {
                        var orderAliasTypeName = form.getOrderAliasTypeName();
                        var orderAliasType = orderAliasTypeName == null ? null : orderLogic.getOrderAliasTypeByName(this, orderType, orderAliasTypeName);

                        if(!hasExecutionErrors()) {
                            var salesOrderSearchEvaluator = new SalesOrderSearchEvaluator(getUserVisit(), searchType,
                                    searchLogic.getDefaultSearchDefaultOperator(null), searchLogic.getDefaultSearchSortOrder(null, searchKind),
                                    searchLogic.getDefaultSearchSortDirection(null));
                            var createdSince = form.getCreatedSince();
                            var modifiedSince = form.getModifiedSince();
                            var fields = form.getFields();

                            salesOrderSearchEvaluator.setOrderStatusWorkflowStep(salesOrderStatusWorkflowStep);
                            salesOrderSearchEvaluator.setOrderAliasType(orderAliasType);
                            salesOrderSearchEvaluator.setAlias(form.getAlias());
                            salesOrderSearchEvaluator.setCreatedSince(createdSince == null ? null : Long.valueOf(createdSince));
                            salesOrderSearchEvaluator.setModifiedSince(modifiedSince == null ? null : Long.valueOf(modifiedSince));
                            salesOrderSearchEvaluator.setFields(fields == null ? null : Splitter.on(':').trimResults().omitEmptyStrings().splitToList(fields).toArray(new String[0]));

                            result.setCount(salesOrderSearchEvaluator.execute(this));
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownSalesOrderStatusChoice.name(), salesOrderStatusChoice);
                }                        
            }
        }
        
        return result;
    }
}

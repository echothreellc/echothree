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

import com.echothree.control.user.search.common.form.SearchSalesOrdersForm;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.control.user.search.common.result.SearchSalesOrdersResult;
import com.echothree.model.control.order.common.OrderConstants;
import com.echothree.model.control.order.server.logic.OrderLogic;
import com.echothree.model.control.search.common.SearchConstants;
import com.echothree.model.control.sales.server.search.SalesOrderSearchEvaluator;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.control.sales.common.workflow.SalesOrderStatusConstants;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.order.server.entity.OrderAliasType;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.search.server.entity.SearchKind;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
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
    public SearchSalesOrdersCommand(UserVisitPK userVisitPK, SearchSalesOrdersForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        SearchLogic searchLogic = SearchLogic.getInstance();
        SearchSalesOrdersResult result = SearchResultFactory.getSearchSalesOrdersResult();
        SearchKind searchKind = searchLogic.getSearchKindByName(null, SearchConstants.SearchKind_SALES_ORDER);

        if(!hasExecutionErrors()) {
            String searchTypeName = form.getSearchTypeName();
            SearchType searchType = searchLogic.getSearchTypeByName(this, searchKind, searchTypeName);

            if(!hasExecutionErrors()) {
                WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
                String salesOrderStatusChoice = form.getSalesOrderStatusChoice();
                WorkflowStep salesOrderStatusWorkflowStep = salesOrderStatusChoice == null ? null :
                    workflowControl.getWorkflowStepByName(workflowControl.getWorkflowByName(SalesOrderStatusConstants.Workflow_SALES_ORDER_STATUS), salesOrderStatusChoice);

                if(salesOrderStatusChoice == null || salesOrderStatusChoice != null) {
                    OrderLogic orderLogic = OrderLogic.getInstance();
                    OrderType orderType = orderLogic.getOrderTypeByName(this, OrderConstants.OrderType_SALES_ORDER);

                    if(!hasExecutionErrors()) {
                        String orderAliasTypeName = form.getOrderAliasTypeName();
                        OrderAliasType orderAliasType = orderAliasTypeName == null ? null : orderLogic.getOrderAliasTypeByName(this, orderType, orderAliasTypeName);

                        if(!hasExecutionErrors()) {
                            SalesOrderSearchEvaluator salesOrderSearchEvaluator = new SalesOrderSearchEvaluator(getUserVisit(), searchType,
                                    searchLogic.getDefaultSearchDefaultOperator(null), searchLogic.getDefaultSearchSortOrder(null, searchKind),
                                    searchLogic.getDefaultSearchSortDirection(null));
                            String createdSince = form.getCreatedSince();
                            String modifiedSince = form.getModifiedSince();
                            String fields = form.getFields();

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

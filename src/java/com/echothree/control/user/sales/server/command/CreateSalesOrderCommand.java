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

package com.echothree.control.user.sales.server.command;

import com.echothree.control.user.sales.remote.form.CreateSalesOrderForm;
import com.echothree.control.user.sales.remote.result.CreateSalesOrderResult;
import com.echothree.control.user.sales.remote.result.SalesResultFactory;
import com.echothree.model.control.accounting.server.logic.CurrencyLogic;
import com.echothree.model.control.offer.server.logic.SourceLogic;
import com.echothree.model.control.order.server.OrderControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.sales.server.logic.SalesOrderBatchLogic;
import com.echothree.model.control.sales.server.logic.SalesOrderLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.term.server.logic.TermLogic;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.offer.server.entity.Source;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderPriority;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateSalesOrderCommand
        extends BaseSimpleCommand<CreateSalesOrderForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.SalesOrder.name(), SecurityRoles.Create.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("BatchName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SourceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("TermName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("BillToPartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("OrderPriorityName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("HoldUntilComplete", FieldType.BOOLEAN, false, null, null),
                new FieldDefinition("AllowBackorders", FieldType.BOOLEAN, false, null, null),
                new FieldDefinition("AllowSubstitutions", FieldType.BOOLEAN, false, null, null),
                new FieldDefinition("AllowCombiningShipments", FieldType.BOOLEAN, false, null, null),
                new FieldDefinition("Reference", FieldType.STRING, false, 1L, 40L),
                new FieldDefinition("Taxable", FieldType.BOOLEAN, false, null, null),
                new FieldDefinition("WorkflowEntranceName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }

    /** Creates a new instance of CreateSalesOrderCommand */
    public CreateSalesOrderCommand(UserVisitPK userVisitPK, CreateSalesOrderForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected BaseResult execute() {
        OrderControl orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        CreateSalesOrderResult result = SalesResultFactory.getCreateSalesOrderResult();
        Order order = null;
        String batchName = form.getBatchName();
        Batch batch = batchName == null ? null : SalesOrderBatchLogic.getInstance().getBatchByName(this, batchName);

        if(!hasExecutionErrors()) {
            String sourceName = form.getSourceName();
            Source source = sourceName == null ? null : SourceLogic.getInstance().getSourceByName(this, sourceName);

            if(!hasExecutionErrors()) {
                String billToPartyName = form.getBillToPartyName();
                Party billToParty = billToPartyName == null ? null : PartyLogic.getInstance().getPartyByName(this, billToPartyName, PartyConstants.PartyType_CUSTOMER);

                if(!hasExecutionErrors()) {
                    String orderPriorityName = form.getOrderPriorityName();
                    OrderPriority orderPriority = orderPriorityName == null ? null : SalesOrderLogic.getInstance().getOrderPriorityByName(this, orderPriorityName);

                    if(!hasExecutionErrors()) {
                        String currencyIsoName = form.getCurrencyIsoName();
                        Currency currency = currencyIsoName == null ? null : CurrencyLogic.getInstance().getCurrencyByName(this, currencyIsoName);

                        if(!hasExecutionErrors()) {
                            String termName = form.getTermName();
                            Term term = termName == null ? null : TermLogic.getInstance().getTermByName(this, termName);

                            if(!hasExecutionErrors()) {
                                String strHoldUntilComplete = form.getHoldUntilComplete();
                                Boolean holdUntilComplete = strHoldUntilComplete == null ? null : Boolean.valueOf(strHoldUntilComplete);
                                String strAllowBackorders = form.getAllowBackorders();
                                Boolean allowBackorders = strAllowBackorders == null ? null : Boolean.valueOf(strAllowBackorders);
                                String strAllowSubstitutions = form.getAllowSubstitutions();
                                Boolean allowSubstitutions = strAllowSubstitutions == null ? null : Boolean.valueOf(strAllowSubstitutions);
                                String strAllowCombiningShipments = form.getAllowCombiningShipments();
                                Boolean allowCombiningShipments = strAllowCombiningShipments == null ? null : Boolean.valueOf(strAllowCombiningShipments);
                                String reference = form.getReference();
                                String strTaxable = form.getTaxable();
                                Boolean taxable = strTaxable == null ? null : Boolean.valueOf(strTaxable);
                                String workflowEntranceName = form.getWorkflowEntranceName();

                                order = SalesOrderLogic.getInstance().createSalesOrder(session, this, getUserVisit(), batch, source, billToParty,
                                        orderPriority, currency, holdUntilComplete, allowBackorders, allowSubstitutions,
                                        allowCombiningShipments, reference, term, taxable, workflowEntranceName, getParty());
                            }
                        }
                    }
                }
            }
        }
        
        if(order != null) {
            result.setOrderName(order.getLastDetail().getOrderName());
            result.setEntityRef(order.getPrimaryKey().getEntityRef());
        }

        return result;
    }

}

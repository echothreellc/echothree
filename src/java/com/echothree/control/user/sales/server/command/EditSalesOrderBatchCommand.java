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

package com.echothree.control.user.sales.server.command;

import com.echothree.control.user.sales.common.edit.SalesEditFactory;
import com.echothree.control.user.sales.common.edit.SalesOrderBatchEdit;
import com.echothree.control.user.sales.common.form.EditSalesOrderBatchForm;
import com.echothree.control.user.sales.common.result.EditSalesOrderBatchResult;
import com.echothree.control.user.sales.common.result.SalesResultFactory;
import com.echothree.control.user.sales.common.spec.SalesOrderBatchSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.order.server.control.OrderBatchControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.payment.server.control.PaymentMethodControl;
import com.echothree.model.control.sales.server.control.SalesOrderBatchControl;
import com.echothree.model.control.sales.server.logic.SalesOrderBatchLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditSalesOrderBatchCommand
        extends BaseAbstractEditCommand<SalesOrderBatchSpec, SalesOrderBatchEdit, EditSalesOrderBatchResult, Batch, Batch> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SalesOrderBatch.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("BatchName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PaymentMethodName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Count", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("Amount:CurrencyIsoName,CurrencyIsoName", FieldType.UNSIGNED_PRICE_LINE, false, null, null)
                ));
    }

    /** Creates a new instance of EditSalesOrderBatchCommand */
    public EditSalesOrderBatchCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditSalesOrderBatchResult getResult() {
        return SalesResultFactory.getEditSalesOrderBatchResult();
    }

    @Override
    public SalesOrderBatchEdit getEdit() {
        return SalesEditFactory.getSalesOrderBatchEdit();
    }

    @Override
    public Batch getEntity(EditSalesOrderBatchResult result) {
        Batch batch;
        var batchName = spec.getBatchName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            batch = SalesOrderBatchLogic.getInstance().getBatchByName(this, batchName);
        } else { // EditMode.UPDATE
            batch = SalesOrderBatchLogic.getInstance().getBatchByNameForUpdate(this, batchName);
        }

        if(!hasExecutionErrors()) {
            var salesOrderBatchControl = Session.getModelController(SalesOrderBatchControl.class);
            
            result.setSalesOrderBatch(salesOrderBatchControl.getSalesOrderBatchTransfer(getUserVisit(), batch));
        }

        return batch;
    }

    @Override
    public Batch getLockEntity(Batch batch) {
        return batch;
    }

    @Override
    public void fillInResult(EditSalesOrderBatchResult result, Batch batch) {
        var salesOrderBatchControl = Session.getModelController(SalesOrderBatchControl.class);

        result.setSalesOrderBatch(salesOrderBatchControl.getSalesOrderBatchTransfer(getUserVisit(), batch));
    }

    @Override
    public void doLock(SalesOrderBatchEdit edit, Batch batch) {
        var orderBatchControl = Session.getModelController(OrderBatchControl.class);
        var salesOrderBatchControl = Session.getModelController(SalesOrderBatchControl.class);
        var orderBatch = orderBatchControl.getOrderBatch(batch);
        var salesOrderBatch = salesOrderBatchControl.getSalesOrderBatch(batch);
        var count = orderBatch.getCount();

        // TODO: currency and payment method should be editable only if the batch has no orders in it.
        edit.setCurrencyIsoName(orderBatch.getCurrency().getCurrencyIsoName());
        edit.setPaymentMethodName(salesOrderBatch.getPaymentMethod().getLastDetail().getPaymentMethodName());
        edit.setCount(count == null ? null : count.toString());
        edit.setAmount(AmountUtils.getInstance().formatCostUnit(orderBatch.getCurrency(), orderBatch.getAmount()));
    }

     Currency currency;
     PaymentMethod paymentMethod;

    @Override
    public void canUpdate(Batch batch) {
        // TODO: currency and payment method should be checked only if the batch has no orders in it.
        var accountingControl = Session.getModelController(AccountingControl.class);
        var currencyIsoName = edit.getCurrencyIsoName();

        currency = accountingControl.getCurrencyByIsoName(currencyIsoName);

        if(currency != null) {
            var paymentMethodControl = Session.getModelController(PaymentMethodControl.class);
            var paymentMethodName = edit.getPaymentMethodName();

            paymentMethod = paymentMethodControl.getPaymentMethodByName(paymentMethodName);

            if(paymentMethod == null) {
                addExecutionError(ExecutionErrors.UnknownPaymentMethodName.name(), paymentMethodName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), currencyIsoName);
        }
    }

    @Override
    public void doUpdate(Batch batch) {
        var salesOrderBatchControl = Session.getModelController(SalesOrderBatchControl.class);
        var orderBatchControl = Session.getModelController(OrderBatchControl.class);
        var partyPK = getPartyPK();
        var strCount = edit.getCount();
        var count = strCount == null ? null : Long.valueOf(strCount);
        var strAmount = edit.getAmount();
        var amount = strAmount == null ? null : Long.valueOf(strAmount);
        var orderBatchValue = orderBatchControl.getOrderBatchValueForUpdate(batch);
        var salesOrderBatchValue = salesOrderBatchControl.getSalesOrderBatchValueForUpdate(batch);

        if(currency != null) {
            orderBatchValue.setCurrencyPK(currency.getPrimaryKey());
        }
        orderBatchValue.setCount(count);
        orderBatchValue.setAmount(amount);
        if(paymentMethod != null) {
            salesOrderBatchValue.setPaymentMethodPK(paymentMethod.getPrimaryKey());
        }

        orderBatchControl.updateOrderBatchFromValue(orderBatchValue, partyPK);
        salesOrderBatchControl.updateSalesOrderBatchFromValue(salesOrderBatchValue, partyPK);
    }

}

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
// See the License for the specific paymentProcessorTypeCode governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.model.control.payment.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorTransactionCodeTransfer;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorTransactionCodeTransferCache;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTransaction;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTransactionCode;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeCode;
import com.echothree.model.data.payment.server.factory.PaymentProcessorTransactionCodeFactory;
import com.echothree.model.data.payment.server.value.PaymentProcessorTransactionCodeValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_PROCESSOR_TRANSACTIONS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_PROCESSOR_TRANSACTION_CODES_PAYMENT_PROCESSOR_TRANSACTION_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_PROCESSOR_TRANSACTION_CODES_PAYMENT_PROCESSOR_TYPE_CODE_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_PROCESSOR_TYPE_CODES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_PROCESSOR_TYPE_CODE_DETAILS_PAYMENT_PROCESSOR_TYPE_CODE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_PROCESSOR_TYPE_CODE_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorTransactionCodes.PaymentProcessorTransactionCodes;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorTransactionDetails.PaymentProcessorTransactionDetails;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorTransactions.PaymentProcessorTransactions;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorTypeCodeDetails.PaymentProcessorTypeCodeDetails;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorTypeCodeTypeDetails.PaymentProcessorTypeCodeTypeDetails;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorTypeCodeTypes.PaymentProcessorTypeCodeTypes;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorTypeCodes.PaymentProcessorTypeCodes;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;

@CommandScope
public class PaymentProcessorTransactionCodeControl
        extends BaseModelControl {

    /** Creates a new instance of PaymentProcessorTransactionCodeControl */
    protected PaymentProcessorTransactionCodeControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Payment Processor Transaction Codes
    // --------------------------------------------------------------------------------

    @Inject
    private PaymentProcessorTransactionCodeFactory paymentProcessorTransactionCodeFactory;

    @Inject
    private PaymentProcessorTransactionCodeTransferCache paymentProcessorTransactionCodeTransferCache;

    public PaymentProcessorTransactionCode createPaymentProcessorTransactionCode(final PaymentProcessorTransaction paymentProcessorTransaction,
            final PaymentProcessorTypeCode paymentProcessorTypeCode, final BasePK createdBy) {
        var paymentProcessorTransactionCode = paymentProcessorTransactionCodeFactory.create(paymentProcessorTransaction,
                paymentProcessorTypeCode, session.getStartTime(), Session.MAX_TIME);

        sendEvent(paymentProcessorTransaction.getPrimaryKey(), EventTypes.MODIFY, paymentProcessorTransactionCode.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return paymentProcessorTransactionCode;
    }

    public long countPaymentProcessorTransactionCodesByPaymentProcessorTransaction(final PaymentProcessorTransaction paymentProcessorTransaction) {
        return session.getDslContext()
                .selectCount()
                .from(PaymentProcessorTransactionCodes)
                .where(PaymentProcessorTransactionCodes.PAYMENT_PROCESSOR_TRANSACTION.eq(paymentProcessorTransaction.getPrimaryKey()),
                        PaymentProcessorTransactionCodes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPaymentProcessorTransactionCodesByPaymentProcessorTypeCode(final PaymentProcessorTypeCode paymentProcessorTypeCode) {
        return session.getDslContext()
                .selectCount()
                .from(PaymentProcessorTransactionCodes)
                .where(PaymentProcessorTransactionCodes.PAYMENT_PROCESSOR_TYPE_CODE.eq(paymentProcessorTypeCode.getPrimaryKey()),
                        PaymentProcessorTransactionCodes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }


    public PaymentProcessorTransactionCode getPaymentProcessorTransactionCode(final PaymentProcessorTransaction paymentProcessorTransaction,
            final PaymentProcessorTypeCode paymentProcessorTypeCode, final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentProcessorTransactionCodes.fields())
                .from(PaymentProcessorTransactionCodes)
                .where(PaymentProcessorTransactionCodes.PAYMENT_PROCESSOR_TRANSACTION.eq(paymentProcessorTransaction.getPrimaryKey()),
                        PaymentProcessorTransactionCodes.PAYMENT_PROCESSOR_TYPE_CODE.eq(paymentProcessorTypeCode.getPrimaryKey()),
                        PaymentProcessorTransactionCodes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentProcessorTransactionCodeFactory.getEntityFromQuery(entityPermission, query);
    }

    public PaymentProcessorTransactionCode getPaymentProcessorTransactionCode(final PaymentProcessorTransaction paymentProcessorTransaction,
            final PaymentProcessorTypeCode paymentProcessorTypeCode) {
        return getPaymentProcessorTransactionCode(paymentProcessorTransaction, paymentProcessorTypeCode, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorTransactionCode getPaymentProcessorTransactionCodeForUpdate(final PaymentProcessorTransaction paymentProcessorTransaction,
            final PaymentProcessorTypeCode paymentProcessorTypeCode) {
        return getPaymentProcessorTransactionCode(paymentProcessorTransaction, paymentProcessorTypeCode, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTransactionCodeValue getPaymentProcessorTransactionCodeValue(final PaymentProcessorTransactionCode paymentProcessorTransactionCode) {
        return paymentProcessorTransactionCode == null ? null: paymentProcessorTransactionCode.getPaymentProcessorTransactionCodeValue().clone();
    }

    public PaymentProcessorTransactionCodeValue getPaymentProcessorTransactionCodeValueForUpdate(final PaymentProcessorTransaction paymentProcessorTransaction,
            final PaymentProcessorTypeCode paymentProcessorTypeCode) {
        return getPaymentProcessorTransactionCodeValue(getPaymentProcessorTransactionCodeForUpdate(paymentProcessorTransaction, paymentProcessorTypeCode));
    }


    public List<PaymentProcessorTransactionCode> getPaymentProcessorTransactionCodesByPaymentProcessorTransaction(final PaymentProcessorTransaction paymentProcessorTransaction,
            final EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PaymentProcessorTransactionCodes.fields())
                    .from(PaymentProcessorTransactionCodes)
                    .join(PaymentProcessorTypeCodes).onKey(PAYMENT_PROCESSOR_TRANSACTION_CODES_PAYMENT_PROCESSOR_TYPE_CODE_FK)
                    .join(PaymentProcessorTypeCodeDetails).onKey(PAYMENT_PROCESSOR_TYPE_CODES_LAST_DETAIL_FK)
                    .join(PaymentProcessorTypeCodeTypes).onKey(PAYMENT_PROCESSOR_TYPE_CODE_DETAILS_PAYMENT_PROCESSOR_TYPE_CODE_TYPE_FK)
                    .join(PaymentProcessorTypeCodeTypeDetails).onKey(PAYMENT_PROCESSOR_TYPE_CODE_TYPES_LAST_DETAIL_FK)
                    .where(PaymentProcessorTransactionCodes.PAYMENT_PROCESSOR_TRANSACTION.eq(paymentProcessorTransaction.getPrimaryKey()),
                            PaymentProcessorTransactionCodes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PaymentProcessorTypeCodeDetails.SORT_ORDER, PaymentProcessorTypeCodeDetails.PAYMENT_PROCESSOR_TYPE_CODE_NAME, PaymentProcessorTypeCodeTypeDetails.SORT_ORDER, PaymentProcessorTypeCodeTypeDetails.PAYMENT_PROCESSOR_TYPE_CODE_TYPE_NAME),
                    PaymentProcessorTransactionCodeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PaymentProcessorTransactionCodes.fields())
                    .from(PaymentProcessorTransactionCodes)
                    .where(PaymentProcessorTransactionCodes.PAYMENT_PROCESSOR_TRANSACTION.eq(paymentProcessorTransaction.getPrimaryKey()),
                            PaymentProcessorTransactionCodes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return paymentProcessorTransactionCodeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PaymentProcessorTransactionCode> getPaymentProcessorTransactionCodesByPaymentProcessorTransaction(final PaymentProcessorTransaction paymentProcessorTransaction) {
        return getPaymentProcessorTransactionCodesByPaymentProcessorTransaction(paymentProcessorTransaction, EntityPermission.READ_ONLY);
    }

    public List<PaymentProcessorTransactionCode> getPaymentProcessorTransactionCodesByPaymentProcessorForUpdate(final PaymentProcessorTransaction paymentProcessorTransaction) {
        return getPaymentProcessorTransactionCodesByPaymentProcessorTransaction(paymentProcessorTransaction, EntityPermission.READ_WRITE);
    }


    public List<PaymentProcessorTransactionCode> getPaymentProcessorTransactionCodesByPaymentProcessorTypeCode(final PaymentProcessorTypeCode paymentProcessorTypeCode,
            final EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PaymentProcessorTransactionCodes.fields())
                    .from(PaymentProcessorTransactionCodes)
                    .join(PaymentProcessorTransactions).onKey(PAYMENT_PROCESSOR_TRANSACTION_CODES_PAYMENT_PROCESSOR_TRANSACTION_FK)
                    .join(PaymentProcessorTransactionDetails).onKey(PAYMENT_PROCESSOR_TRANSACTIONS_LAST_DETAIL_FK)
                    .where(PaymentProcessorTransactionCodes.PAYMENT_PROCESSOR_TYPE_CODE.eq(paymentProcessorTypeCode.getPrimaryKey()),
                            PaymentProcessorTransactionCodes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PaymentProcessorTransactionDetails.PAYMENT_PROCESSOR_TRANSACTION_NAME),
                    PaymentProcessorTransactionCodeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PaymentProcessorTransactionCodes.fields())
                    .from(PaymentProcessorTransactionCodes)
                    .where(PaymentProcessorTransactionCodes.PAYMENT_PROCESSOR_TYPE_CODE.eq(paymentProcessorTypeCode.getPrimaryKey()),
                            PaymentProcessorTransactionCodes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return paymentProcessorTransactionCodeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PaymentProcessorTransactionCode> getPaymentProcessorTransactionCodesByPaymentProcessorTypeCode(final PaymentProcessorTypeCode paymentProcessorTypeCode) {
        return getPaymentProcessorTransactionCodesByPaymentProcessorTypeCode(paymentProcessorTypeCode, EntityPermission.READ_ONLY);
    }

    public List<PaymentProcessorTransactionCode> getPaymentProcessorTransactionCodesByPaymentProcessorTypeCodeForUpdate(final PaymentProcessorTypeCode paymentProcessorTypeCode) {
        return getPaymentProcessorTransactionCodesByPaymentProcessorTypeCode(paymentProcessorTypeCode, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTransactionCodeTransfer getPaymentProcessorTransactionCodeTransfer(final UserVisit userVisit,
            final PaymentProcessorTransactionCode paymentProcessorTransactionCode) {
        return paymentProcessorTransactionCodeTransferCache.getTransfer(userVisit, paymentProcessorTransactionCode);
    }

    public List<PaymentProcessorTransactionCodeTransfer> getPaymentProcessorTransactionCodeTransfers(final UserVisit userVisit,
            final Collection<PaymentProcessorTransactionCode> paymentProcessorTransactionCodes) {
        var paymentProcessorTransactionCodeTransfers = new ArrayList<PaymentProcessorTransactionCodeTransfer>(paymentProcessorTransactionCodes.size());

        paymentProcessorTransactionCodes.forEach((paymentProcessorTransactionCode) ->
                paymentProcessorTransactionCodeTransfers.add(paymentProcessorTransactionCodeTransferCache.getTransfer(userVisit, paymentProcessorTransactionCode))
        );

        return paymentProcessorTransactionCodeTransfers;
    }

    public List<PaymentProcessorTransactionCodeTransfer> getPaymentProcessorTransactionCodeTransfersByPaymentProcessorTransaction(final UserVisit userVisit,
            final PaymentProcessorTransaction paymentProcessorTransaction) {
        return getPaymentProcessorTransactionCodeTransfers(userVisit, getPaymentProcessorTransactionCodesByPaymentProcessorTransaction(paymentProcessorTransaction));
    }

    public List<PaymentProcessorTransactionCodeTransfer> getPaymentProcessorTransactionCodeTransfersByPaymentProcessorTypeCode(final UserVisit userVisit,
            final PaymentProcessorTypeCode paymentProcessorTypeCode) {
        return getPaymentProcessorTransactionCodeTransfers(userVisit, getPaymentProcessorTransactionCodesByPaymentProcessorTypeCode(paymentProcessorTypeCode));
    }

    public void deletePaymentProcessorTransactionCode(final PaymentProcessorTransactionCode paymentProcessorTransactionCode, final BasePK deletedBy) {
        paymentProcessorTransactionCode.setThruTime(session.getStartTime());

        sendEvent(paymentProcessorTransactionCode.getPaymentProcessorTransactionPK(), EventTypes.MODIFY, paymentProcessorTransactionCode.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deletePaymentProcessorTransactionCodesByPaymentProcessorTransaction(final PaymentProcessorTransaction paymentProcessorTransaction, final BasePK deletedBy) {
        var paymentProcessorTransactionCodes = getPaymentProcessorTransactionCodesByPaymentProcessorForUpdate(paymentProcessorTransaction);

        paymentProcessorTransactionCodes.forEach((paymentProcessorTransactionCode) -> {
            deletePaymentProcessorTransactionCode(paymentProcessorTransactionCode, deletedBy);
        });
    }

    public void deletePaymentProcessorTransactionCodesByPaymentProcessorTypeCode(final PaymentProcessorTypeCode paymentProcessorTypeCode, final BasePK deletedBy) {
        var paymentProcessorTransactionCodes = getPaymentProcessorTransactionCodesByPaymentProcessorTypeCodeForUpdate(paymentProcessorTypeCode);

        paymentProcessorTransactionCodes.forEach((paymentProcessorTransactionCode) -> {
            deletePaymentProcessorTransactionCode(paymentProcessorTransactionCode, deletedBy);
        });
    }

}

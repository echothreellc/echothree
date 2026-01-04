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
import com.echothree.model.data.payment.server.entity.PaymentProcessorTransaction;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTransactionCode;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeCode;
import com.echothree.model.data.payment.server.factory.PaymentProcessorTransactionCodeFactory;
import com.echothree.model.data.payment.server.value.PaymentProcessorTransactionCodeValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import com.echothree.util.server.cdi.CommandScope;

@CommandScope
public class PaymentProcessorTransactionCodeControl
        extends BasePaymentControl {

    /** Creates a new instance of PaymentProcessorTransactionCodeControl */
    protected PaymentProcessorTransactionCodeControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Payment Processor Transaction Codes
    // --------------------------------------------------------------------------------

    public PaymentProcessorTransactionCode createPaymentProcessorTransactionCode(final PaymentProcessorTransaction paymentProcessorTransaction,
            final PaymentProcessorTypeCode paymentProcessorTypeCode, final BasePK createdBy) {
        var paymentProcessorTransactionCode = PaymentProcessorTransactionCodeFactory.getInstance().create(paymentProcessorTransaction,
                paymentProcessorTypeCode, session.getStartTime(), Session.MAX_TIME);

        sendEvent(paymentProcessorTransaction.getPrimaryKey(), EventTypes.MODIFY, paymentProcessorTransactionCode.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return paymentProcessorTransactionCode;
    }

    private static final Map<EntityPermission, String> getPaymentProcessorTransactionCodeQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortransactions " +
                    "WHERE pprctrxc_pprctrx_paymentprocessortransactionid = ? AND pprctrxc_pproctypc_paymentprocessortypecodeid = ? AND pprctrxc_thrutime = ?",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortransactions " +
                    "WHERE pprctrxc_pprctrx_paymentprocessortransactionid = ? AND pprctrxc_pproctypc_paymentprocessortypecodeid = ? AND pprctrxc_thrutime = ? " +
                    "FOR UPDATE");

    public PaymentProcessorTransactionCode getPaymentProcessorTransactionCode(final PaymentProcessorTransaction paymentProcessorTransaction,
            final PaymentProcessorTypeCode paymentProcessorTypeCode, final EntityPermission entityPermission) {
        return PaymentProcessorTransactionCodeFactory.getInstance().getEntityFromQuery(entityPermission, getPaymentProcessorTransactionCodeQueries,
                paymentProcessorTransaction, paymentProcessorTypeCode, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getPaymentProcessorTransactionCodesByPaymentProcessorTransactionQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortransactioncodes, paymentprocessortypecodes, paymentprocessortypecodedetails, paymentprocessortypecodetypes, paymentprocessortypecodetypedetails " +
                    "WHERE pprctrxc_pprctrx_paymentprocessortransactionid = ? AND pprctrxc_thrutime = ? " +
                    "AND pprctrxc_pproctypc_paymentprocessortypecodeid = pproctypc_paymentprocessortypecodeid AND pproctypc_lastdetailid = pproctypcdt_paymentprocessortypecodedetailid " +
                    "AND pproctypcdt_pproctypctyp_paymentprocessortypecodetypeid = pproctypctyp_paymentprocessortypecodetypeid AND pproctypctyp_lastdetailid = pproctypctypdt_paymentprocessortypecodetypedetailid " +
                    "ORDER BY pproctypcdt_sortorder, pproctypcdt_paymentprocessortypecodename, pproctypctypdt_sortorder, pproctypctypdt_paymentprocessortypecodetypename",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortransactioncodes " +
                    "WHERE pprctrxc_pprctrx_paymentprocessortransactionid = ? AND pprctrxc_thrutime = ? " +
                    "FOR UPDATE");

    public List<PaymentProcessorTransactionCode> getPaymentProcessorTransactionCodesByPaymentProcessorTransaction(final PaymentProcessorTransaction paymentProcessorTransaction,
            final EntityPermission entityPermission) {
        return PaymentProcessorTransactionCodeFactory.getInstance().getEntitiesFromQuery(entityPermission,
                getPaymentProcessorTransactionCodesByPaymentProcessorTransactionQueries,
                paymentProcessorTransaction, Session.MAX_TIME);
    }

    public List<PaymentProcessorTransactionCode> getPaymentProcessorTransactionCodesByPaymentProcessorTransaction(final PaymentProcessorTransaction paymentProcessorTransaction) {
        return getPaymentProcessorTransactionCodesByPaymentProcessorTransaction(paymentProcessorTransaction, EntityPermission.READ_ONLY);
    }

    public List<PaymentProcessorTransactionCode> getPaymentProcessorTransactionCodesByPaymentProcessorForUpdate(final PaymentProcessorTransaction paymentProcessorTransaction) {
        return getPaymentProcessorTransactionCodesByPaymentProcessorTransaction(paymentProcessorTransaction, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPaymentProcessorTransactionCodesByPaymentProcessorTypeCodeQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortransactioncodes, paymentprocessortransactions, paymentprocessortransactiondetails " +
                    "WHERE pprctrxc_pproctypc_paymentprocessortypecodeid = ? AND pprctrxc_thrutime = ? " +
                    "AND pprctrxc_pprctrx_paymentprocessortransactionid = pprctrx_paymentprocessortransactionid AND pprctrx_lastdetailid = pprctrxdt_paymentprocessortransactiondetailid " +
                    "ORDER BY pprctrxdt_paymentprocessortransactionname",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortransactioncodes " +
                    "WHERE pprctrxc_pproctypc_paymentprocessortypecodeid = ? AND pprctrxc_thrutime = ? " +
                    "FOR UPDATE");

    public List<PaymentProcessorTransactionCode> getPaymentProcessorTransactionCodesByPaymentProcessorTypeCode(final PaymentProcessorTypeCode paymentProcessorTypeCode,
            final EntityPermission entityPermission) {
        return PaymentProcessorTransactionCodeFactory.getInstance().getEntitiesFromQuery(entityPermission,
                getPaymentProcessorTransactionCodesByPaymentProcessorTypeCodeQueries,
                paymentProcessorTypeCode, Session.MAX_TIME);
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

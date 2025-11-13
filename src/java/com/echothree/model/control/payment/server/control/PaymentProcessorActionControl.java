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
// See the License for the specific paymentProcessorTypeAction governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.model.control.payment.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorActionTransfer;
import com.echothree.model.data.payment.server.entity.PaymentProcessor;
import com.echothree.model.data.payment.server.entity.PaymentProcessorAction;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeAction;
import com.echothree.model.data.payment.server.factory.PaymentProcessorActionFactory;
import com.echothree.model.data.payment.server.value.PaymentProcessorActionValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PaymentProcessorActionControl
        extends BasePaymentControl {

    /** Creates a new instance of PaymentProcessorActionControl */
    protected PaymentProcessorActionControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Payment Processor Actions
    // --------------------------------------------------------------------------------

    public PaymentProcessorAction createPaymentProcessorAction(final PaymentProcessor paymentProcessor,
            final PaymentProcessorTypeAction paymentProcessorTypeAction, final BasePK createdBy) {
        var paymentProcessorAction = PaymentProcessorActionFactory.getInstance().create(paymentProcessor,
                paymentProcessorTypeAction, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(paymentProcessor.getPrimaryKey(), EventTypes.MODIFY, paymentProcessorAction.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return paymentProcessorAction;
    }

    private static final Map<EntityPermission, String> getPaymentProcessorActionQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessoractions " +
                    "WHERE pprcact_pprc_paymentprocessorid = ? AND pprcact_pprctypact_paymentprocessortypeactionid = ? AND pprcact_thrutime = ?",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentprocessoractions " +
                    "WHERE pprcact_pprc_paymentprocessorid = ? AND pprcact_pprctypact_paymentprocessortypeactionid = ? AND pprcact_thrutime = ? " +
                    "FOR UPDATE");

    public PaymentProcessorAction getPaymentProcessorAction(final PaymentProcessor paymentProcessor,
            final PaymentProcessorTypeAction paymentProcessorTypeAction, final EntityPermission entityPermission) {
        return PaymentProcessorActionFactory.getInstance().getEntityFromQuery(entityPermission, getPaymentProcessorActionQueries,
                paymentProcessor, paymentProcessorTypeAction, Session.MAX_TIME);
    }

    public PaymentProcessorAction getPaymentProcessorAction(final PaymentProcessor paymentProcessor,
            final PaymentProcessorTypeAction paymentProcessorTypeAction) {
        return getPaymentProcessorAction(paymentProcessor, paymentProcessorTypeAction, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorAction getPaymentProcessorActionForUpdate(final PaymentProcessor paymentProcessor,
            final PaymentProcessorTypeAction paymentProcessorTypeAction) {
        return getPaymentProcessorAction(paymentProcessor, paymentProcessorTypeAction, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorActionValue getPaymentProcessorActionValue(final PaymentProcessorAction paymentProcessorAction) {
        return paymentProcessorAction == null ? null: paymentProcessorAction.getPaymentProcessorActionValue().clone();
    }

    public PaymentProcessorActionValue getPaymentProcessorActionValueForUpdate(final PaymentProcessor paymentProcessor,
            final PaymentProcessorTypeAction paymentProcessorTypeAction) {
        return getPaymentProcessorActionValue(getPaymentProcessorActionForUpdate(paymentProcessor, paymentProcessorTypeAction));
    }

    private static final Map<EntityPermission, String> getPaymentProcessorActionsByPaymentProcessorQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessoractions, paymentprocessortypeactions, paymentprocessortypeactiondetails, paymentprocessoractiontypes, paymentprocessoractiontypedetails " +
                    "WHERE pprcact_pprc_paymentprocessorid = ? AND pprcact_thrutime = ? " +
                    "AND pprcact_pprctypact_paymentprocessortypeactionid = pprctypact_paymentprocessortypeactionid AND pprctypact_lastdetailid = pprctypactdt_paymentprocessortypeactiondetailid " +
                    "AND pprctypactdt_pprcacttyp_paymentprocessoractiontypeid = pprcacttyp_paymentprocessoractiontypeid AND pprcacttyp_lastdetailid = pprcacttypdt_paymentprocessoractiontypedetailid " +
                    "ORDER BY pprctypactdt_sortorder, pprcacttypdt_sortorder, pprcacttypdt_paymentprocessoractiontypename",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentprocessoractions " +
                    "WHERE pprcact_pprc_paymentprocessorid = ? AND pprcact_thrutime = ? " +
                    "FOR UPDATE");

    public List<PaymentProcessorAction> getPaymentProcessorActionsByPaymentProcessor(final PaymentProcessor paymentProcessor,
            final EntityPermission entityPermission) {
        return PaymentProcessorActionFactory.getInstance().getEntitiesFromQuery(entityPermission,
                getPaymentProcessorActionsByPaymentProcessorQueries,
                paymentProcessor, Session.MAX_TIME);
    }

    public List<PaymentProcessorAction> getPaymentProcessorActionsByPaymentProcessor(final PaymentProcessor paymentProcessor) {
        return getPaymentProcessorActionsByPaymentProcessor(paymentProcessor, EntityPermission.READ_ONLY);
    }

    public List<PaymentProcessorAction> getPaymentProcessorActionsByPaymentProcessorForUpdate(final PaymentProcessor paymentProcessor) {
        return getPaymentProcessorActionsByPaymentProcessor(paymentProcessor, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPaymentProcessorActionsByPaymentProcessorTypeActionQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessoractions, paymentprocessors, paymentprocessordetails " +
                    "WHERE pprcact_pprctypact_paymentprocessortypeactionid = ? AND pprcact_thrutime = ? " +
                    "AND pprcact_pprc_paymentprocessorid = pprc_paymentprocessorid AND pprc_lastdetailid = pprcdt_paymentprocessordetailid " +
                    "ORDER BY pprcdt_sortorder, pprcdt_paymentprocessorname",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentprocessoractions " +
                    "WHERE pprcact_pprctypact_paymentprocessortypeactionid = ? AND pprcact_thrutime = ? " +
                    "FOR UPDATE");

    public List<PaymentProcessorAction> getPaymentProcessorActionsByPaymentProcessorTypeAction(final PaymentProcessorTypeAction paymentProcessorTypeAction,
            final EntityPermission entityPermission) {
        return PaymentProcessorActionFactory.getInstance().getEntitiesFromQuery(entityPermission,
                getPaymentProcessorActionsByPaymentProcessorTypeActionQueries,
                paymentProcessorTypeAction, Session.MAX_TIME);
    }

    public List<PaymentProcessorAction> getPaymentProcessorActionsByPaymentProcessorTypeAction(final PaymentProcessorTypeAction paymentProcessorTypeAction) {
        return getPaymentProcessorActionsByPaymentProcessorTypeAction(paymentProcessorTypeAction, EntityPermission.READ_ONLY);
    }

    public List<PaymentProcessorAction> getPaymentProcessorActionsByPaymentProcessorTypeActionForUpdate(final PaymentProcessorTypeAction paymentProcessorTypeAction) {
        return getPaymentProcessorActionsByPaymentProcessorTypeAction(paymentProcessorTypeAction, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorActionTransfer getPaymentProcessorActionTransfer(final UserVisit userVisit,
            final PaymentProcessorAction paymentProcessorAction) {
        return getPaymentTransferCaches().getPaymentProcessorActionTransferCache().getTransfer(userVisit, paymentProcessorAction);
    }

    public List<PaymentProcessorActionTransfer> getPaymentProcessorActionTransfers(final UserVisit userVisit,
            final List<PaymentProcessorAction> paymentProcessorActions) {
        var paymentProcessorActionTransfers = new ArrayList<PaymentProcessorActionTransfer>(paymentProcessorActions.size());
        var paymentProcessorActionTransferCache = getPaymentTransferCaches().getPaymentProcessorActionTransferCache();

        paymentProcessorActions.forEach((paymentProcessorAction) ->
                paymentProcessorActionTransfers.add(paymentProcessorActionTransferCache.getTransfer(userVisit, paymentProcessorAction))
        );

        return paymentProcessorActionTransfers;
    }

    public List<PaymentProcessorActionTransfer> getPaymentProcessorActionTransfersByPaymentProcessor(final UserVisit userVisit,
            final PaymentProcessor paymentProcessor) {
        return getPaymentProcessorActionTransfers(userVisit, getPaymentProcessorActionsByPaymentProcessor(paymentProcessor));
    }

    public List<PaymentProcessorActionTransfer> getPaymentProcessorActionTransfersByPaymentProcessorTypeAction(final UserVisit userVisit,
            final PaymentProcessorTypeAction paymentProcessorTypeAction) {
        return getPaymentProcessorActionTransfers(userVisit, getPaymentProcessorActionsByPaymentProcessorTypeAction(paymentProcessorTypeAction));
    }

    public void deletePaymentProcessorAction(final PaymentProcessorAction paymentProcessorAction, final BasePK deletedBy) {
        paymentProcessorAction.setThruTime(session.START_TIME_LONG);

        sendEvent(paymentProcessorAction.getPaymentProcessorPK(), EventTypes.MODIFY, paymentProcessorAction.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deletePaymentProcessorActionsByPaymentProcessor(final PaymentProcessor paymentProcessor, final BasePK deletedBy) {
        var paymentProcessorActions = getPaymentProcessorActionsByPaymentProcessorForUpdate(paymentProcessor);

        paymentProcessorActions.forEach((paymentProcessorAction) -> {
            deletePaymentProcessorAction(paymentProcessorAction, deletedBy);
        });
    }

    public void deletePaymentProcessorActionsByPaymentProcessorTypeAction(final PaymentProcessorTypeAction paymentProcessorTypeAction, final BasePK deletedBy) {
        var paymentProcessorActions = getPaymentProcessorActionsByPaymentProcessorTypeActionForUpdate(paymentProcessorTypeAction);

        paymentProcessorActions.forEach((paymentProcessorAction) -> {
            deletePaymentProcessorAction(paymentProcessorAction, deletedBy);
        });
    }

}

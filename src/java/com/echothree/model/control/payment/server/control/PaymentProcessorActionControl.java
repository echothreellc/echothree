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
// See the License for the specific paymentProcessorTypeAction governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.model.control.payment.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorActionTransfer;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorActionTransferCache;
import com.echothree.model.data.payment.server.entity.PaymentProcessor;
import com.echothree.model.data.payment.server.entity.PaymentProcessorAction;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeAction;
import com.echothree.model.data.payment.server.factory.PaymentProcessorActionFactory;
import com.echothree.model.data.payment.server.value.PaymentProcessorActionValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_PROCESSORS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_PROCESSOR_ACTIONS_PAYMENT_PROCESSOR_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_PROCESSOR_ACTIONS_PAYMENT_PROCESSOR_TYPE_ACTION_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_PROCESSOR_ACTION_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_PROCESSOR_TYPE_ACTIONS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_PROCESSOR_TYPE_ACTION_DETAILS_PAYMENT_PROCESSOR_ACTION_TYPE_FK;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorActionTypeDetails.PaymentProcessorActionTypeDetails;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorActionTypes.PaymentProcessorActionTypes;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorActions.PaymentProcessorActions;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorDetails.PaymentProcessorDetails;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorTypeActionDetails.PaymentProcessorTypeActionDetails;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorTypeActions.PaymentProcessorTypeActions;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessors.PaymentProcessors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

@CommandScope
public class PaymentProcessorActionControl
        extends BaseModelControl {

    /** Creates a new instance of PaymentProcessorActionControl */
    protected PaymentProcessorActionControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Payment Processor Actions
    // --------------------------------------------------------------------------------

    @Inject
    private PaymentProcessorActionFactory paymentProcessorActionFactory;

    @Inject
    private PaymentProcessorActionTransferCache paymentProcessorActionTransferCache;

    public PaymentProcessorAction createPaymentProcessorAction(final PaymentProcessor paymentProcessor,
            final PaymentProcessorTypeAction paymentProcessorTypeAction, final BasePK createdBy) {
        var paymentProcessorAction = paymentProcessorActionFactory.create(paymentProcessor,
                paymentProcessorTypeAction, session.getStartTime(), Session.MAX_TIME);

        sendEvent(paymentProcessor.getPrimaryKey(), EventTypes.MODIFY, paymentProcessorAction.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return paymentProcessorAction;
    }


    public PaymentProcessorAction getPaymentProcessorAction(final PaymentProcessor paymentProcessor,
            final PaymentProcessorTypeAction paymentProcessorTypeAction, final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentProcessorActions.fields())
                .from(PaymentProcessorActions)
                .where(PaymentProcessorActions.PAYMENT_PROCESSOR.eq(paymentProcessor.getPrimaryKey()),
                        PaymentProcessorActions.PAYMENT_PROCESSOR_TYPE_ACTION.eq(paymentProcessorTypeAction.getPrimaryKey()),
                        PaymentProcessorActions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentProcessorActionFactory.getEntityFromQuery(entityPermission, query);
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


    public List<PaymentProcessorAction> getPaymentProcessorActionsByPaymentProcessor(final PaymentProcessor paymentProcessor,
            final EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PaymentProcessorActions.fields())
                    .from(PaymentProcessorActions)
                    .join(PaymentProcessorTypeActions).onKey(PAYMENT_PROCESSOR_ACTIONS_PAYMENT_PROCESSOR_TYPE_ACTION_FK)
                    .join(PaymentProcessorTypeActionDetails).onKey(PAYMENT_PROCESSOR_TYPE_ACTIONS_LAST_DETAIL_FK)
                    .join(PaymentProcessorActionTypes).onKey(PAYMENT_PROCESSOR_TYPE_ACTION_DETAILS_PAYMENT_PROCESSOR_ACTION_TYPE_FK)
                    .join(PaymentProcessorActionTypeDetails).onKey(PAYMENT_PROCESSOR_ACTION_TYPES_LAST_DETAIL_FK)
                    .where(PaymentProcessorActions.PAYMENT_PROCESSOR.eq(paymentProcessor.getPrimaryKey()),
                            PaymentProcessorActions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PaymentProcessorTypeActionDetails.SORT_ORDER, PaymentProcessorActionTypeDetails.SORT_ORDER, PaymentProcessorActionTypeDetails.PAYMENT_PROCESSOR_ACTION_TYPE_NAME),
                    PaymentProcessorActionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PaymentProcessorActions.fields())
                    .from(PaymentProcessorActions)
                    .where(PaymentProcessorActions.PAYMENT_PROCESSOR.eq(paymentProcessor.getPrimaryKey()),
                            PaymentProcessorActions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return paymentProcessorActionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PaymentProcessorAction> getPaymentProcessorActionsByPaymentProcessor(final PaymentProcessor paymentProcessor) {
        return getPaymentProcessorActionsByPaymentProcessor(paymentProcessor, EntityPermission.READ_ONLY);
    }

    public List<PaymentProcessorAction> getPaymentProcessorActionsByPaymentProcessorForUpdate(final PaymentProcessor paymentProcessor) {
        return getPaymentProcessorActionsByPaymentProcessor(paymentProcessor, EntityPermission.READ_WRITE);
    }


    public List<PaymentProcessorAction> getPaymentProcessorActionsByPaymentProcessorTypeAction(final PaymentProcessorTypeAction paymentProcessorTypeAction,
            final EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PaymentProcessorActions.fields())
                    .from(PaymentProcessorActions)
                    .join(PaymentProcessors).onKey(PAYMENT_PROCESSOR_ACTIONS_PAYMENT_PROCESSOR_FK)
                    .join(PaymentProcessorDetails).onKey(PAYMENT_PROCESSORS_LAST_DETAIL_FK)
                    .where(PaymentProcessorActions.PAYMENT_PROCESSOR_TYPE_ACTION.eq(paymentProcessorTypeAction.getPrimaryKey()),
                            PaymentProcessorActions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PaymentProcessorDetails.SORT_ORDER, PaymentProcessorDetails.PAYMENT_PROCESSOR_NAME),
                    PaymentProcessorActionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PaymentProcessorActions.fields())
                    .from(PaymentProcessorActions)
                    .where(PaymentProcessorActions.PAYMENT_PROCESSOR_TYPE_ACTION.eq(paymentProcessorTypeAction.getPrimaryKey()),
                            PaymentProcessorActions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return paymentProcessorActionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PaymentProcessorAction> getPaymentProcessorActionsByPaymentProcessorTypeAction(final PaymentProcessorTypeAction paymentProcessorTypeAction) {
        return getPaymentProcessorActionsByPaymentProcessorTypeAction(paymentProcessorTypeAction, EntityPermission.READ_ONLY);
    }

    public List<PaymentProcessorAction> getPaymentProcessorActionsByPaymentProcessorTypeActionForUpdate(final PaymentProcessorTypeAction paymentProcessorTypeAction) {
        return getPaymentProcessorActionsByPaymentProcessorTypeAction(paymentProcessorTypeAction, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorActionTransfer getPaymentProcessorActionTransfer(final UserVisit userVisit,
            final PaymentProcessorAction paymentProcessorAction) {
        return paymentProcessorActionTransferCache.getTransfer(userVisit, paymentProcessorAction);
    }

    public List<PaymentProcessorActionTransfer> getPaymentProcessorActionTransfers(final UserVisit userVisit,
            final List<PaymentProcessorAction> paymentProcessorActions) {
        var paymentProcessorActionTransfers = new ArrayList<PaymentProcessorActionTransfer>(paymentProcessorActions.size());

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
        paymentProcessorAction.setThruTime(session.getStartTime());

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

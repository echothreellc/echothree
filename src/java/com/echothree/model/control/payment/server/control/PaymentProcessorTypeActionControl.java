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
// See the License for the specific language governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.model.control.payment.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.payment.common.choice.PaymentProcessorTypeActionChoicesBean;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorTypeActionTransfer;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorTypeActionTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.payment.common.pk.PaymentProcessorTypeActionPK;
import com.echothree.model.data.payment.server.entity.PaymentProcessorActionType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeAction;
import com.echothree.model.data.payment.server.factory.PaymentProcessorTypeActionDetailFactory;
import com.echothree.model.data.payment.server.factory.PaymentProcessorTypeActionFactory;
import com.echothree.model.data.payment.server.value.PaymentProcessorTypeActionDetailValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_PROCESSOR_ACTION_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_PROCESSOR_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_PROCESSOR_TYPE_ACTIONS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_PROCESSOR_TYPE_ACTION_DETAILS_PAYMENT_PROCESSOR_ACTION_TYPE_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_PROCESSOR_TYPE_ACTION_DETAILS_PAYMENT_PROCESSOR_TYPE_FK;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorActionTypeDetails.PaymentProcessorActionTypeDetails;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorActionTypes.PaymentProcessorActionTypes;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorTypeActionDetails.PaymentProcessorTypeActionDetails;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorTypeActions.PaymentProcessorTypeActions;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorTypeDetails.PaymentProcessorTypeDetails;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorTypes.PaymentProcessorTypes;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;

@CommandScope
public class PaymentProcessorTypeActionControl
        extends BaseModelControl {

    @Inject
    private PaymentProcessorActionControl paymentProcessorActionControl;

    @Inject
    protected PaymentProcessorActionTypeControl paymentProcessorActionTypeControl;

    /** Creates a new instance of PaymentProcessorTypeActionControl */
    protected PaymentProcessorTypeActionControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Payment Processor Type Actions
    // --------------------------------------------------------------------------------

    @Inject
    protected PaymentProcessorTypeActionDetailFactory paymentProcessorTypeActionDetailFactory;

    @Inject
    private PaymentProcessorTypeActionFactory paymentProcessorTypeActionFactory;

    @Inject
    private PaymentProcessorTypeActionTransferCache paymentProcessorTypeActionTransferCache;

    public PaymentProcessorTypeAction createPaymentProcessorTypeAction(final PaymentProcessorType paymentProcessorType,
            final PaymentProcessorActionType paymentProcessorActionType, Boolean isDefault, final Integer sortOrder, final BasePK createdBy) {
        var defaultPaymentProcessorTypeAction = getDefaultPaymentProcessorTypeAction(paymentProcessorType);
        var defaultFound = defaultPaymentProcessorTypeAction != null;

        if(defaultFound && isDefault) {
            var defaultPaymentProcessorTypeActionDetailValue = getDefaultPaymentProcessorTypeActionDetailValueForUpdate(paymentProcessorType);

            defaultPaymentProcessorTypeActionDetailValue.setIsDefault(false);
            updatePaymentProcessorTypeActionFromValue(defaultPaymentProcessorTypeActionDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var paymentProcessorTypeAction = paymentProcessorTypeActionFactory.create();
        var paymentProcessorTypeActionDetail = paymentProcessorTypeActionDetailFactory.create(
                paymentProcessorTypeAction, paymentProcessorType, paymentProcessorActionType, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        paymentProcessorTypeAction = paymentProcessorTypeActionFactory.getEntityFromPK(EntityPermission.READ_WRITE, paymentProcessorTypeAction.getPrimaryKey());
        paymentProcessorTypeAction.setActiveDetail(paymentProcessorTypeActionDetail);
        paymentProcessorTypeAction.setLastDetail(paymentProcessorTypeActionDetail);
        paymentProcessorTypeAction.store();

        sendEvent(paymentProcessorType.getPrimaryKey(), EventTypes.MODIFY, paymentProcessorTypeAction.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return paymentProcessorTypeAction;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PaymentProcessorTypeAction */
    public PaymentProcessorTypeAction getPaymentProcessorTypeActionByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new PaymentProcessorTypeActionPK(entityInstance.getEntityUniqueId());

        return paymentProcessorTypeActionFactory.getEntityFromPK(entityPermission, pk);
    }

    public PaymentProcessorTypeAction getPaymentProcessorTypeActionByEntityInstance(final EntityInstance entityInstance) {
        return getPaymentProcessorTypeActionByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorTypeAction getPaymentProcessorTypeActionByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getPaymentProcessorTypeActionByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }


    public PaymentProcessorTypeAction getPaymentProcessorTypeAction(final PaymentProcessorType paymentProcessorType,
            final PaymentProcessorActionType paymentProcessorActionType, final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentProcessorTypeActions.fields())
                .from(PaymentProcessorTypeActions)
                .join(PaymentProcessorTypeActionDetails).onKey(PAYMENT_PROCESSOR_TYPE_ACTIONS_ACTIVE_DETAIL_FK)
                .where(PaymentProcessorTypeActionDetails.PAYMENT_PROCESSOR_TYPE.eq(paymentProcessorType.getPrimaryKey()),
                        PaymentProcessorTypeActionDetails.PAYMENT_PROCESSOR_ACTION_TYPE.eq(paymentProcessorActionType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentProcessorTypeActionFactory.getEntityFromQuery(entityPermission, query);
    }

    public PaymentProcessorTypeAction getPaymentProcessorTypeAction(final PaymentProcessorType paymentProcessorType,
            final PaymentProcessorActionType paymentProcessorActionType) {
        return getPaymentProcessorTypeAction(paymentProcessorType, paymentProcessorActionType, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorTypeAction getPaymentProcessorTypeActionForUpdate(final PaymentProcessorType paymentProcessorType,
            final PaymentProcessorActionType paymentProcessorActionType) {
        return getPaymentProcessorTypeAction(paymentProcessorType, paymentProcessorActionType, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTypeActionDetailValue getPaymentProcessorTypeActionDetailValueForUpdate(final PaymentProcessorTypeAction paymentProcessorTypeAction) {
        return paymentProcessorTypeAction == null ? null : paymentProcessorTypeAction.getLastDetailForUpdate().getPaymentProcessorTypeActionDetailValue().clone();
    }

    public PaymentProcessorTypeActionDetailValue getPaymentProcessorTypeActionDetailValueForUpdate(final PaymentProcessorType paymentProcessorType,
            final PaymentProcessorActionType paymentProcessorActionType) {
        return getPaymentProcessorTypeActionDetailValueForUpdate(getPaymentProcessorTypeActionForUpdate(paymentProcessorType, paymentProcessorActionType));
    }


    public PaymentProcessorTypeAction getDefaultPaymentProcessorTypeAction(final PaymentProcessorType paymentProcessorType,
            final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentProcessorTypeActions.fields())
                .from(PaymentProcessorTypeActions)
                .join(PaymentProcessorTypeActionDetails).onKey(PAYMENT_PROCESSOR_TYPE_ACTIONS_ACTIVE_DETAIL_FK)
                .where(PaymentProcessorTypeActionDetails.PAYMENT_PROCESSOR_TYPE.eq(paymentProcessorType.getPrimaryKey()),
                        PaymentProcessorTypeActionDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentProcessorTypeActionFactory.getEntityFromQuery(entityPermission, query);
    }

    public PaymentProcessorTypeAction getDefaultPaymentProcessorTypeAction(final PaymentProcessorType paymentProcessorType) {
        return getDefaultPaymentProcessorTypeAction(paymentProcessorType, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorTypeAction getDefaultPaymentProcessorTypeActionForUpdate(final PaymentProcessorType paymentProcessorType) {
        return getDefaultPaymentProcessorTypeAction(paymentProcessorType, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTypeActionDetailValue getDefaultPaymentProcessorTypeActionDetailValueForUpdate(final PaymentProcessorType paymentProcessorType) {
        return getDefaultPaymentProcessorTypeActionForUpdate(paymentProcessorType).getLastDetailForUpdate().getPaymentProcessorTypeActionDetailValue().clone();
    }


    private List<PaymentProcessorTypeAction> getPaymentProcessorTypeActionsByPaymentProcessorType(final PaymentProcessorType paymentProcessorType, final EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PaymentProcessorTypeActions.fields())
                    .from(PaymentProcessorTypeActions)
                    .join(PaymentProcessorTypeActionDetails).onKey(PAYMENT_PROCESSOR_TYPE_ACTIONS_ACTIVE_DETAIL_FK)
                    .join(PaymentProcessorActionTypes).onKey(PAYMENT_PROCESSOR_TYPE_ACTION_DETAILS_PAYMENT_PROCESSOR_ACTION_TYPE_FK)
                    .join(PaymentProcessorActionTypeDetails).onKey(PAYMENT_PROCESSOR_ACTION_TYPES_ACTIVE_DETAIL_FK)
                    .where(PaymentProcessorTypeActionDetails.PAYMENT_PROCESSOR_TYPE.eq(paymentProcessorType.getPrimaryKey()))
                    .orderBy(PaymentProcessorActionTypeDetails.SORT_ORDER, PaymentProcessorActionTypeDetails.PAYMENT_PROCESSOR_ACTION_TYPE_NAME),
                    PaymentProcessorTypeActionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PaymentProcessorTypeActions.fields())
                    .from(PaymentProcessorTypeActions)
                    .join(PaymentProcessorTypeActionDetails).onKey(PAYMENT_PROCESSOR_TYPE_ACTIONS_ACTIVE_DETAIL_FK)
                    .where(PaymentProcessorTypeActionDetails.PAYMENT_PROCESSOR_TYPE.eq(paymentProcessorType.getPrimaryKey()))
                    .forUpdate();
        };

        return paymentProcessorTypeActionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PaymentProcessorTypeAction> getPaymentProcessorTypeActionsByPaymentProcessorType(final PaymentProcessorType paymentProcessorType) {
        return getPaymentProcessorTypeActionsByPaymentProcessorType(paymentProcessorType, EntityPermission.READ_ONLY);
    }

    public List<PaymentProcessorTypeAction> getPaymentProcessorTypeActionsByPaymentProcessorTypeForUpdate(final PaymentProcessorType paymentProcessorType) {
        return getPaymentProcessorTypeActionsByPaymentProcessorType(paymentProcessorType, EntityPermission.READ_WRITE);
    }


    private List<PaymentProcessorTypeAction> getPaymentProcessorTypeActionsByPaymentProcessorActionType(final PaymentProcessorActionType paymentProcessorActionType, final EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PaymentProcessorTypeActions.fields())
                    .from(PaymentProcessorTypeActions)
                    .join(PaymentProcessorTypeActionDetails).onKey(PAYMENT_PROCESSOR_TYPE_ACTIONS_ACTIVE_DETAIL_FK)
                    .join(PaymentProcessorTypes).onKey(PAYMENT_PROCESSOR_TYPE_ACTION_DETAILS_PAYMENT_PROCESSOR_TYPE_FK)
                    .join(PaymentProcessorTypeDetails).onKey(PAYMENT_PROCESSOR_TYPES_ACTIVE_DETAIL_FK)
                    .where(PaymentProcessorTypeActionDetails.PAYMENT_PROCESSOR_ACTION_TYPE.eq(paymentProcessorActionType.getPrimaryKey()))
                    .orderBy(PaymentProcessorTypeDetails.SORT_ORDER, PaymentProcessorTypeDetails.PAYMENT_PROCESSOR_TYPE_NAME),
                    PaymentProcessorTypeActionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PaymentProcessorTypeActions.fields())
                    .from(PaymentProcessorTypeActions)
                    .join(PaymentProcessorTypeActionDetails).onKey(PAYMENT_PROCESSOR_TYPE_ACTIONS_ACTIVE_DETAIL_FK)
                    .where(PaymentProcessorTypeActionDetails.PAYMENT_PROCESSOR_ACTION_TYPE.eq(paymentProcessorActionType.getPrimaryKey()))
                    .forUpdate();
        };

        return paymentProcessorTypeActionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PaymentProcessorTypeAction> getPaymentProcessorTypeActionsByPaymentProcessorActionType(final PaymentProcessorActionType paymentProcessorActionType) {
        return getPaymentProcessorTypeActionsByPaymentProcessorActionType(paymentProcessorActionType, EntityPermission.READ_ONLY);
    }

    public List<PaymentProcessorTypeAction> getPaymentProcessorTypeActionsByPaymentProcessorActionTypeForUpdate(final PaymentProcessorActionType paymentProcessorActionType) {
        return getPaymentProcessorTypeActionsByPaymentProcessorActionType(paymentProcessorActionType, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTypeActionTransfer getPaymentProcessorTypeActionTransfer(final UserVisit userVisit,
            final PaymentProcessorTypeAction paymentProcessorTypeAction) {
        return paymentProcessorTypeActionTransferCache.getTransfer(userVisit, paymentProcessorTypeAction);
    }

    public List<PaymentProcessorTypeActionTransfer> getPaymentProcessorTypeActionTransfers(final UserVisit userVisit,
            final Collection<PaymentProcessorTypeAction> paymentProcessorTypeActions) {
        var paymentProcessorTypeActionTransfers = new ArrayList<PaymentProcessorTypeActionTransfer>(paymentProcessorTypeActions.size());

        paymentProcessorTypeActions.forEach((paymentProcessorTypeAction) ->
                paymentProcessorTypeActionTransfers.add(paymentProcessorTypeActionTransferCache.getTransfer(userVisit, paymentProcessorTypeAction))
        );

        return paymentProcessorTypeActionTransfers;
    }

    public List<PaymentProcessorTypeActionTransfer> getPaymentProcessorTypeActionTransfersByPaymentProcessorType(final UserVisit userVisit,
            final PaymentProcessorType paymentProcessorType) {
        return getPaymentProcessorTypeActionTransfers(userVisit, getPaymentProcessorTypeActionsByPaymentProcessorType(paymentProcessorType));
    }

    public List<PaymentProcessorTypeActionTransfer> getPaymentProcessorTypeActionTransfers(final UserVisit userVisit,
            final PaymentProcessorActionType paymentProcessorActionType) {
        return getPaymentProcessorTypeActionTransfers(userVisit, getPaymentProcessorTypeActionsByPaymentProcessorActionType(paymentProcessorActionType));
    }

    public PaymentProcessorTypeActionChoicesBean getPaymentProcessorTypeActionChoices(final PaymentProcessorType paymentProcessorType,
            final String defaultPaymentProcessorTypeActionChoice, final Language language, final boolean allowNullChoice) {
        var paymentProcessorTypeActions = getPaymentProcessorTypeActionsByPaymentProcessorType(paymentProcessorType);
        var size = paymentProcessorTypeActions.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultPaymentProcessorTypeActionChoice == null) {
                defaultValue = "";
            }
        }

        for(var paymentProcessorTypeAction : paymentProcessorTypeActions) {
            var paymentProcessorTypeActionDetail = paymentProcessorTypeAction.getLastDetail();

            var label = paymentProcessorActionTypeControl.getBestPaymentProcessorActionTypeDescription(paymentProcessorTypeActionDetail.getPaymentProcessorActionType(), language);
            var value = paymentProcessorTypeActionDetail.getPaymentProcessorActionType().getLastDetail().getPaymentProcessorActionTypeName();

            labels.add(label == null ? value : label);
            values.add(value);

            var usingDefaultChoice = Objects.equals(defaultPaymentProcessorTypeActionChoice, value);
            if(usingDefaultChoice || (defaultValue == null && paymentProcessorTypeActionDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new PaymentProcessorTypeActionChoicesBean(labels, values, defaultValue);
    }

    private void updatePaymentProcessorTypeActionFromValue(final PaymentProcessorTypeActionDetailValue paymentProcessorTypeActionDetailValue,
            final boolean checkDefault, final BasePK updatedBy) {
        if(paymentProcessorTypeActionDetailValue.hasBeenModified()) {
            var paymentProcessorTypeAction = paymentProcessorTypeActionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    paymentProcessorTypeActionDetailValue.getPaymentProcessorTypeActionPK());
            var paymentProcessorTypeActionDetail = paymentProcessorTypeAction.getActiveDetailForUpdate();

            paymentProcessorTypeActionDetail.setThruTime(session.getStartTime());
            paymentProcessorTypeActionDetail.store();

            var paymentProcessorTypeActionPK = paymentProcessorTypeActionDetail.getPaymentProcessorTypeActionPK(); // R/O
            var paymentProcessorType = paymentProcessorTypeActionDetail.getPaymentProcessorType();
            var paymentProcessorTypePK = paymentProcessorType.getPrimaryKey(); // R/O
            var paymentProcessorActionType = paymentProcessorTypeActionDetail.getPaymentProcessorActionType();
            var paymentProcessorActionTypePK = paymentProcessorActionType.getPrimaryKey(); // R/O
            var isDefault = paymentProcessorTypeActionDetailValue.getIsDefault(); // R/W
            var sortOrder = paymentProcessorTypeActionDetailValue.getSortOrder(); // R/W

            if(checkDefault) {
                var defaultPaymentProcessorTypeAction = getDefaultPaymentProcessorTypeAction(paymentProcessorType);
                var defaultFound = defaultPaymentProcessorTypeAction != null && !defaultPaymentProcessorTypeAction.equals(paymentProcessorTypeAction);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPaymentProcessorTypeActionDetailValue = getDefaultPaymentProcessorTypeActionDetailValueForUpdate(paymentProcessorType);

                    defaultPaymentProcessorTypeActionDetailValue.setIsDefault(false);
                    updatePaymentProcessorTypeActionFromValue(defaultPaymentProcessorTypeActionDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            paymentProcessorTypeActionDetail = paymentProcessorTypeActionDetailFactory.create(paymentProcessorTypeActionPK,
                    paymentProcessorTypePK, paymentProcessorActionTypePK, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            paymentProcessorTypeAction.setActiveDetail(paymentProcessorTypeActionDetail);
            paymentProcessorTypeAction.setLastDetail(paymentProcessorTypeActionDetail);

            sendEvent(paymentProcessorTypePK, EventTypes.MODIFY, paymentProcessorTypeAction.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void updatePaymentProcessorTypeActionFromValue(final PaymentProcessorTypeActionDetailValue paymentProcessorTypeActionDetailValue,
            final BasePK updatedBy) {
        updatePaymentProcessorTypeActionFromValue(paymentProcessorTypeActionDetailValue, true, updatedBy);
    }

    public void deletePaymentProcessorTypeAction(final PaymentProcessorTypeAction paymentProcessorTypeAction, final BasePK deletedBy) {

        paymentProcessorActionControl.deletePaymentProcessorActionsByPaymentProcessorTypeAction(paymentProcessorTypeAction, deletedBy);
        
        var paymentProcessorTypeActionDetail = paymentProcessorTypeAction.getLastDetailForUpdate();
        paymentProcessorTypeActionDetail.setThruTime(session.getStartTime());
        paymentProcessorTypeActionDetail.store();
        paymentProcessorTypeAction.setActiveDetail(null);

        // Check for default, and pick one if necessary
        var paymentProcessorType = paymentProcessorTypeActionDetail.getPaymentProcessorType();
        var defaultPaymentProcessorTypeAction = getDefaultPaymentProcessorTypeAction(paymentProcessorType);
        if(defaultPaymentProcessorTypeAction == null) {
            var paymentProcessorTypeActions = getPaymentProcessorTypeActionsByPaymentProcessorTypeForUpdate(paymentProcessorType);

            if(!paymentProcessorTypeActions.isEmpty()) {
                var iter = paymentProcessorTypeActions.iterator();
                if(iter.hasNext()) {
                    defaultPaymentProcessorTypeAction = iter.next();
                }
                var paymentProcessorTypeActionDetailValue = Objects.requireNonNull(defaultPaymentProcessorTypeAction).getLastDetailForUpdate().getPaymentProcessorTypeActionDetailValue().clone();

                paymentProcessorTypeActionDetailValue.setIsDefault(true);
                updatePaymentProcessorTypeActionFromValue(paymentProcessorTypeActionDetailValue, false, deletedBy);
            }
        }

        sendEvent(paymentProcessorType.getPrimaryKey(), EventTypes.MODIFY, paymentProcessorTypeAction.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deletePaymentProcessorTypeActions(final Collection<PaymentProcessorTypeAction> paymentProcessorTypeActions, final BasePK deletedBy) {
        paymentProcessorTypeActions.forEach(paymentProcessorTypeAction -> deletePaymentProcessorTypeAction(paymentProcessorTypeAction, deletedBy));
    }

    public void deletePaymentProcessorTypeActionsByPaymentProcessorType(final PaymentProcessorType paymentProcessorType, final BasePK deletedBy) {
        deletePaymentProcessorTypeActions(getPaymentProcessorTypeActionsByPaymentProcessorTypeForUpdate(paymentProcessorType), deletedBy);
    }

    public void deletePaymentProcessorTypeActionsByPaymentProcessorActionType(final PaymentProcessorActionType paymentProcessorActionType, final BasePK deletedBy) {
        deletePaymentProcessorTypeActions(getPaymentProcessorTypeActionsByPaymentProcessorActionTypeForUpdate(paymentProcessorActionType), deletedBy);
    }

}

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

package com.echothree.model.control.payment.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.payment.common.choice.PaymentProcessorTypeActionChoicesBean;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorTypeActionTransfer;
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
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.echothree.util.server.cdi.CommandScope;

@CommandScope
public class PaymentProcessorTypeActionControl
        extends BasePaymentControl {

    /** Creates a new instance of PaymentProcessorTypeActionControl */
    protected PaymentProcessorTypeActionControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Payment Processor Type Actions
    // --------------------------------------------------------------------------------

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

        var paymentProcessorTypeAction = PaymentProcessorTypeActionFactory.getInstance().create();
        var paymentProcessorTypeActionDetail = PaymentProcessorTypeActionDetailFactory.getInstance().create(session,
                paymentProcessorTypeAction, paymentProcessorType, paymentProcessorActionType, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        paymentProcessorTypeAction = PaymentProcessorTypeActionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, paymentProcessorTypeAction.getPrimaryKey());
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

        return PaymentProcessorTypeActionFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public PaymentProcessorTypeAction getPaymentProcessorTypeActionByEntityInstance(final EntityInstance entityInstance) {
        return getPaymentProcessorTypeActionByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorTypeAction getPaymentProcessorTypeActionByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getPaymentProcessorTypeActionByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPaymentProcessorTypeActionQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypeactions, paymentprocessortypeactiondetails " +
                    "WHERE pprctypact_activedetailid = pprctypactdt_paymentprocessortypeactiondetailid " +
                    "AND pprctypactdt_pprctyp_paymentprocessortypeid = ? AND pprctypactdt_pprcacttyp_paymentprocessoractiontypeid = ?",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " + "FROM paymentprocessortypeactions, paymentprocessortypeactiondetails " +
                    "WHERE pprctypact_activedetailid = pprctypactdt_paymentprocessortypeactiondetailid " +
                    "AND pprctypactdt_pprctyp_paymentprocessortypeid = ? AND pprctypactdt_pprcacttyp_paymentprocessoractiontypeid = ? " +
                    "FOR UPDATE");

    public PaymentProcessorTypeAction getPaymentProcessorTypeAction(final PaymentProcessorType paymentProcessorType,
            final PaymentProcessorActionType paymentProcessorActionType, final EntityPermission entityPermission) {
        return PaymentProcessorTypeActionFactory.getInstance().getEntityFromQuery(entityPermission, getPaymentProcessorTypeActionQueries,
                paymentProcessorType, paymentProcessorActionType);
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

    private static final Map<EntityPermission, String> getDefaultPaymentProcessorTypeActionQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypeactions, paymentprocessortypeactiondetails " +
                    "WHERE pprctypact_activedetailid = pprctypactdt_paymentprocessortypeactiondetailid " +
                    "AND pprctypactdt_pprctyp_paymentprocessortypeid = ? AND pprctypactdt_isdefault = 1",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypeactions, paymentprocessortypeactiondetails " +
                    "WHERE pprctypact_activedetailid = pprctypactdt_paymentprocessortypeactiondetailid " +
                    "AND pprctypactdt_pprctyp_paymentprocessortypeid = ? AND pprctypactdt_isdefault = 1 " +
                    "FOR UPDATE");

    public PaymentProcessorTypeAction getDefaultPaymentProcessorTypeAction(final PaymentProcessorType paymentProcessorType,
            final EntityPermission entityPermission) {
        return PaymentProcessorTypeActionFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultPaymentProcessorTypeActionQueries,
                paymentProcessorType);
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

    private static final Map<EntityPermission, String> getPaymentProcessorTypeActionsByPaymentProcessorTypeQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypeactions, paymentprocessortypeactiondetails, paymentprocessoractiontypes, paymentprocessoractiontypedetails " +
                    "WHERE pprctypact_activedetailid = pprctypactdt_paymentprocessortypeactiondetailid " +
                    "AND pprctypactdt_pprcacttyp_paymentprocessoractiontypeid = pprcacttyp_paymentprocessoractiontypeid AND pprcacttyp_activedetailid = pprcacttypdt_paymentprocessoractiontypedetailid " +
                    "AND pprctypactdt_pprctyp_paymentprocessortypeid = ? " +
                    "ORDER BY pprcacttypdt_sortorder, pprcacttypdt_paymentprocessoractiontypename",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypeactions, paymentprocessortypeactiondetails " +
                    "WHERE pprctypact_activedetailid = pprctypactdt_paymentprocessortypeactiondetailid " +
                    "AND pprctypactdt_pprctyp_paymentprocessortypeid = ? " +
                    "FOR UPDATE");

    private List<PaymentProcessorTypeAction> getPaymentProcessorTypeActionsByPaymentProcessorType(final PaymentProcessorType paymentProcessorType, final EntityPermission entityPermission) {
        return PaymentProcessorTypeActionFactory.getInstance().getEntitiesFromQuery(entityPermission, getPaymentProcessorTypeActionsByPaymentProcessorTypeQueries,
                paymentProcessorType);
    }

    public List<PaymentProcessorTypeAction> getPaymentProcessorTypeActionsByPaymentProcessorType(final PaymentProcessorType paymentProcessorType) {
        return getPaymentProcessorTypeActionsByPaymentProcessorType(paymentProcessorType, EntityPermission.READ_ONLY);
    }

    public List<PaymentProcessorTypeAction> getPaymentProcessorTypeActionsByPaymentProcessorTypeForUpdate(final PaymentProcessorType paymentProcessorType) {
        return getPaymentProcessorTypeActionsByPaymentProcessorType(paymentProcessorType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPaymentProcessorTypeActionsByPaymentProcessorActionTypeQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypeactions, paymentprocessortypeactiondetails, paymentprocessortypes, paymentprocessortypedetails " +
                    "WHERE pprctypact_activedetailid = pprctypactdt_paymentprocessortypeactiondetailid " +
                    "AND pprctypactdt_pprctyp_paymentprocessortypeid = pprctyp_paymentprocessortypeid AND pprctyp_activedetailid = pprctypdt_paymentprocessortypedetailid " +
                    "AND pprctypactdt_pprcacttyp_paymentprocessoractiontypeid = ? " +
                    "ORDER BY pprctypdt_sortorder, pprctypdt_paymentprocessortypename",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypeactions, paymentprocessortypeactiondetails " +
                    "WHERE pprctypact_activedetailid = pprctypactdt_paymentprocessortypeactiondetailid " +
                    "AND pprctypactdt_pprcacttyp_paymentprocessoractiontypeid = ? " +
                    "FOR UPDATE");

    private List<PaymentProcessorTypeAction> getPaymentProcessorTypeActionsByPaymentProcessorActionType(final PaymentProcessorActionType paymentProcessorActionType, final EntityPermission entityPermission) {
        return PaymentProcessorTypeActionFactory.getInstance().getEntitiesFromQuery(entityPermission, getPaymentProcessorTypeActionsByPaymentProcessorActionTypeQueries,
                paymentProcessorActionType);
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
        var paymentProcessorActionTypeControl = Session.getModelController(PaymentProcessorActionTypeControl.class);
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
            var paymentProcessorTypeAction = PaymentProcessorTypeActionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    paymentProcessorTypeActionDetailValue.getPaymentProcessorTypeActionPK());
            var paymentProcessorTypeActionDetail = paymentProcessorTypeAction.getActiveDetailForUpdate();

            paymentProcessorTypeActionDetail.setThruTime(session.START_TIME_LONG);
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

            paymentProcessorTypeActionDetail = PaymentProcessorTypeActionDetailFactory.getInstance().create(paymentProcessorTypeActionPK,
                    paymentProcessorTypePK, paymentProcessorActionTypePK, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
        var paymentProcessorActionControl = Session.getModelController(PaymentProcessorActionControl.class);

        paymentProcessorActionControl.deletePaymentProcessorActionsByPaymentProcessorTypeAction(paymentProcessorTypeAction, deletedBy);
        
        var paymentProcessorTypeActionDetail = paymentProcessorTypeAction.getLastDetailForUpdate();
        paymentProcessorTypeActionDetail.setThruTime(session.START_TIME_LONG);
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

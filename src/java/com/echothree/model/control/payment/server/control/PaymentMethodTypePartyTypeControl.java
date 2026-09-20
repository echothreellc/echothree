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
import com.echothree.model.control.payment.common.choice.PaymentMethodTypePartyTypeChoicesBean;
import com.echothree.model.control.payment.common.transfer.PaymentMethodTypePartyTypeTransfer;
import com.echothree.model.control.payment.server.transfer.PaymentMethodTypePartyTypeTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.payment.common.pk.PaymentMethodTypePartyTypePK;
import com.echothree.model.data.payment.server.entity.PaymentMethodType;
import com.echothree.model.data.payment.server.entity.PaymentMethodTypePartyType;
import com.echothree.model.data.payment.server.factory.PaymentMethodTypePartyTypeDetailFactory;
import com.echothree.model.data.payment.server.factory.PaymentMethodTypePartyTypeFactory;
import com.echothree.model.data.payment.server.value.PaymentMethodTypePartyTypeDetailValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.Workflow;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_METHOD_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_METHOD_TYPE_PARTY_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_METHOD_TYPE_PARTY_TYPE_DETAILS_PARTY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_METHOD_TYPE_PARTY_TYPE_DETAILS_PAYMENT_METHOD_TYPE_FK;
import static com.echothree.model.jooq.server.tables.party.PartyTypes.PartyTypes;
import static com.echothree.model.jooq.server.tables.payment.PaymentMethodTypeDetails.PaymentMethodTypeDetails;
import static com.echothree.model.jooq.server.tables.payment.PaymentMethodTypePartyTypeDetails.PaymentMethodTypePartyTypeDetails;
import static com.echothree.model.jooq.server.tables.payment.PaymentMethodTypePartyTypes.PaymentMethodTypePartyTypes;
import static com.echothree.model.jooq.server.tables.payment.PaymentMethodTypes.PaymentMethodTypes;
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
public class PaymentMethodTypePartyTypeControl
        extends BaseModelControl {

    /** Creates a new instance of PaymentMethodTypePartyTypeControl */

    protected PaymentMethodTypePartyTypeControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Payment Method Type Party Types
    // --------------------------------------------------------------------------------

    @Inject
    protected PaymentMethodTypePartyTypeDetailFactory paymentMethodTypePartyTypeDetailFactory;

    @Inject
    private PaymentMethodTypePartyTypeFactory paymentMethodTypePartyTypeFactory;

    @Inject
    private PaymentMethodTypePartyTypeTransferCache paymentMethodTypePartyTypeTransferCache;

    public PaymentMethodTypePartyType createPaymentMethodTypePartyType(final PaymentMethodType paymentMethodType,
            final PartyType partyType, final Workflow partyPaymentMethodWorkflow, final Workflow partyContactMechanismWorkflow,
            Boolean isDefault, final Integer sortOrder, final BasePK createdBy) {
        var defaultPaymentMethodTypePartyType = getDefaultPaymentMethodTypePartyType(paymentMethodType);
        var defaultFound = defaultPaymentMethodTypePartyType != null;

        if(defaultFound && isDefault) {
            var defaultPaymentMethodTypePartyTypeDetailValue = getDefaultPaymentMethodTypePartyTypeDetailValueForUpdate(paymentMethodType);

            defaultPaymentMethodTypePartyTypeDetailValue.setIsDefault(false);
            updatePaymentMethodTypePartyTypeFromValue(defaultPaymentMethodTypePartyTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var paymentMethodTypePartyType = paymentMethodTypePartyTypeFactory.create();
        var paymentMethodTypePartyTypeDetail = paymentMethodTypePartyTypeDetailFactory.create(
                paymentMethodTypePartyType, paymentMethodType, partyType, partyPaymentMethodWorkflow,
                partyContactMechanismWorkflow, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        paymentMethodTypePartyType = paymentMethodTypePartyTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE, paymentMethodTypePartyType.getPrimaryKey());
        paymentMethodTypePartyType.setActiveDetail(paymentMethodTypePartyTypeDetail);
        paymentMethodTypePartyType.setLastDetail(paymentMethodTypePartyTypeDetail);
        paymentMethodTypePartyType.store();

        sendEvent(paymentMethodType.getPrimaryKey(), EventTypes.MODIFY, paymentMethodTypePartyType.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return paymentMethodTypePartyType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PaymentMethodTypePartyType */
    public PaymentMethodTypePartyType getPaymentMethodTypePartyTypeByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new PaymentMethodTypePartyTypePK(entityInstance.getEntityUniqueId());

        return paymentMethodTypePartyTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public PaymentMethodTypePartyType getPaymentMethodTypePartyTypeByEntityInstance(final EntityInstance entityInstance) {
        return getPaymentMethodTypePartyTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PaymentMethodTypePartyType getPaymentMethodTypePartyTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getPaymentMethodTypePartyTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }


    public PaymentMethodTypePartyType getPaymentMethodTypePartyType(final PaymentMethodType paymentMethodType,
            final PartyType partyType, final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentMethodTypePartyTypes.fields())
                .from(PaymentMethodTypePartyTypes)
                .join(PaymentMethodTypePartyTypeDetails).onKey(PAYMENT_METHOD_TYPE_PARTY_TYPES_ACTIVE_DETAIL_FK)
                .where(PaymentMethodTypePartyTypeDetails.PAYMENT_METHOD_TYPE.eq(paymentMethodType.getPrimaryKey()),
                        PaymentMethodTypePartyTypeDetails.PARTY_TYPE.eq(partyType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentMethodTypePartyTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public PaymentMethodTypePartyType getPaymentMethodTypePartyType(final PaymentMethodType paymentMethodType,
            final PartyType partyType) {
        return getPaymentMethodTypePartyType(paymentMethodType, partyType, EntityPermission.READ_ONLY);
    }

    public PaymentMethodTypePartyType getPaymentMethodTypePartyTypeForUpdate(final PaymentMethodType paymentMethodType,
            final PartyType partyType) {
        return getPaymentMethodTypePartyType(paymentMethodType, partyType, EntityPermission.READ_WRITE);
    }

    public PaymentMethodTypePartyTypeDetailValue getPaymentMethodTypePartyTypeDetailValueForUpdate(final PaymentMethodTypePartyType paymentMethodTypePartyType) {
        return paymentMethodTypePartyType == null ? null : paymentMethodTypePartyType.getLastDetailForUpdate().getPaymentMethodTypePartyTypeDetailValue().clone();
    }

    public PaymentMethodTypePartyTypeDetailValue getPaymentMethodTypePartyTypeDetailValueForUpdate(final PaymentMethodType paymentMethodType,
            final PartyType partyType) {
        return getPaymentMethodTypePartyTypeDetailValueForUpdate(getPaymentMethodTypePartyTypeForUpdate(paymentMethodType, partyType));
    }


    public PaymentMethodTypePartyType getDefaultPaymentMethodTypePartyType(final PaymentMethodType paymentMethodType,
            final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentMethodTypePartyTypes.fields())
                .from(PaymentMethodTypePartyTypes)
                .join(PaymentMethodTypePartyTypeDetails).onKey(PAYMENT_METHOD_TYPE_PARTY_TYPES_ACTIVE_DETAIL_FK)
                .where(PaymentMethodTypePartyTypeDetails.PAYMENT_METHOD_TYPE.eq(paymentMethodType.getPrimaryKey()),
                        PaymentMethodTypePartyTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentMethodTypePartyTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public PaymentMethodTypePartyType getDefaultPaymentMethodTypePartyType(final PaymentMethodType paymentMethodType) {
        return getDefaultPaymentMethodTypePartyType(paymentMethodType, EntityPermission.READ_ONLY);
    }

    public PaymentMethodTypePartyType getDefaultPaymentMethodTypePartyTypeForUpdate(final PaymentMethodType paymentMethodType) {
        return getDefaultPaymentMethodTypePartyType(paymentMethodType, EntityPermission.READ_WRITE);
    }

    public PaymentMethodTypePartyTypeDetailValue getDefaultPaymentMethodTypePartyTypeDetailValueForUpdate(final PaymentMethodType paymentMethodType) {
        return getDefaultPaymentMethodTypePartyTypeForUpdate(paymentMethodType).getLastDetailForUpdate().getPaymentMethodTypePartyTypeDetailValue().clone();
    }


    private List<PaymentMethodTypePartyType> getPaymentMethodTypePartyTypesByPaymentMethodType(final PaymentMethodType paymentMethodType, final EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PaymentMethodTypePartyTypes.fields())
                    .from(PaymentMethodTypePartyTypes)
                    .join(PaymentMethodTypePartyTypeDetails).onKey(PAYMENT_METHOD_TYPE_PARTY_TYPES_ACTIVE_DETAIL_FK)
                    .join(PartyTypes).onKey(PAYMENT_METHOD_TYPE_PARTY_TYPE_DETAILS_PARTY_TYPE_FK)
                    .where(PaymentMethodTypePartyTypeDetails.PAYMENT_METHOD_TYPE.eq(paymentMethodType.getPrimaryKey()))
                    .orderBy(PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME),
                    PaymentMethodTypePartyTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PaymentMethodTypePartyTypes.fields())
                    .from(PaymentMethodTypePartyTypes)
                    .join(PaymentMethodTypePartyTypeDetails).onKey(PAYMENT_METHOD_TYPE_PARTY_TYPES_ACTIVE_DETAIL_FK)
                    .where(PaymentMethodTypePartyTypeDetails.PAYMENT_METHOD_TYPE.eq(paymentMethodType.getPrimaryKey()))
                    .forUpdate();
        };

        return paymentMethodTypePartyTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PaymentMethodTypePartyType> getPaymentMethodTypePartyTypesByPaymentMethodType(final PaymentMethodType paymentMethodType) {
        return getPaymentMethodTypePartyTypesByPaymentMethodType(paymentMethodType, EntityPermission.READ_ONLY);
    }

    public List<PaymentMethodTypePartyType> getPaymentMethodTypePartyTypesByPaymentMethodTypeForUpdate(final PaymentMethodType paymentMethodType) {
        return getPaymentMethodTypePartyTypesByPaymentMethodType(paymentMethodType, EntityPermission.READ_WRITE);
    }


    private List<PaymentMethodTypePartyType> getPaymentMethodTypePartyTypesByPartyType(final PartyType partyType, final EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PaymentMethodTypePartyTypes.fields())
                    .from(PaymentMethodTypePartyTypes)
                    .join(PaymentMethodTypePartyTypeDetails).onKey(PAYMENT_METHOD_TYPE_PARTY_TYPES_ACTIVE_DETAIL_FK)
                    .join(PaymentMethodTypes).onKey(PAYMENT_METHOD_TYPE_PARTY_TYPE_DETAILS_PAYMENT_METHOD_TYPE_FK)
                    .join(PaymentMethodTypeDetails).onKey(PAYMENT_METHOD_TYPES_ACTIVE_DETAIL_FK)
                    .where(PaymentMethodTypePartyTypeDetails.PARTY_TYPE.eq(partyType.getPrimaryKey()))
                    .orderBy(PaymentMethodTypeDetails.SORT_ORDER, PaymentMethodTypeDetails.PAYMENT_METHOD_TYPE_NAME),
                    PaymentMethodTypePartyTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PaymentMethodTypePartyTypes.fields())
                    .from(PaymentMethodTypePartyTypes)
                    .join(PaymentMethodTypePartyTypeDetails).onKey(PAYMENT_METHOD_TYPE_PARTY_TYPES_ACTIVE_DETAIL_FK)
                    .where(PaymentMethodTypePartyTypeDetails.PARTY_TYPE.eq(partyType.getPrimaryKey()))
                    .forUpdate();
        };

        return paymentMethodTypePartyTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PaymentMethodTypePartyType> getPaymentMethodTypePartyTypesByPartyType(final PartyType partyType) {
        return getPaymentMethodTypePartyTypesByPartyType(partyType, EntityPermission.READ_ONLY);
    }

    public List<PaymentMethodTypePartyType> getPaymentMethodTypePartyTypesByPartyTypeForUpdate(final PartyType partyType) {
        return getPaymentMethodTypePartyTypesByPartyType(partyType, EntityPermission.READ_WRITE);
    }

    public PaymentMethodTypePartyTypeTransfer getPaymentMethodTypePartyTypeTransfer(final UserVisit userVisit,
            final PaymentMethodTypePartyType paymentMethodTypePartyType) {
        return paymentMethodTypePartyTypeTransferCache.getTransfer(userVisit, paymentMethodTypePartyType);
    }

    public List<PaymentMethodTypePartyTypeTransfer> getPaymentMethodTypePartyTypeTransfers(final UserVisit userVisit,
            final Collection<PaymentMethodTypePartyType> paymentMethodTypePartyTypes) {
        var paymentMethodTypePartyTypeTransfers = new ArrayList<PaymentMethodTypePartyTypeTransfer>(paymentMethodTypePartyTypes.size());

        paymentMethodTypePartyTypes.forEach((paymentMethodTypePartyType) ->
                paymentMethodTypePartyTypeTransfers.add(paymentMethodTypePartyTypeTransferCache.getTransfer(userVisit, paymentMethodTypePartyType))
        );

        return paymentMethodTypePartyTypeTransfers;
    }

    public List<PaymentMethodTypePartyTypeTransfer> getPaymentMethodTypePartyTypeTransfersByPaymentMethodType(final UserVisit userVisit,
            final PaymentMethodType paymentMethodType) {
        return getPaymentMethodTypePartyTypeTransfers(userVisit, getPaymentMethodTypePartyTypesByPaymentMethodType(paymentMethodType));
    }

    public List<PaymentMethodTypePartyTypeTransfer> getPaymentMethodTypePartyTypeTransfers(final UserVisit userVisit,
            final PartyType partyType) {
        return getPaymentMethodTypePartyTypeTransfers(userVisit, getPaymentMethodTypePartyTypesByPartyType(partyType));
    }

    public PaymentMethodTypePartyTypeChoicesBean getPaymentMethodTypePartyTypeChoices(final PaymentMethodType paymentMethodType,
            final String defaultPaymentMethodTypePartyTypeChoice, final Language language, final boolean allowNullChoice) {
        var paymentMethodTypePartyTypes = getPaymentMethodTypePartyTypesByPaymentMethodType(paymentMethodType);
        var size = paymentMethodTypePartyTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultPaymentMethodTypePartyTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var paymentMethodTypePartyType : paymentMethodTypePartyTypes) {
            var paymentMethodTypePartyTypeDetail = paymentMethodTypePartyType.getLastDetail();

            var label = partyControl.getBestPartyTypeDescription(paymentMethodTypePartyTypeDetail.getPartyType(), language);
            var value = paymentMethodTypePartyTypeDetail.getPartyType().getPartyTypeName();

            labels.add(label == null ? value : label);
            values.add(value);

            var usingDefaultChoice = Objects.equals(defaultPaymentMethodTypePartyTypeChoice, value);
            if(usingDefaultChoice || (defaultValue == null && paymentMethodTypePartyTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new PaymentMethodTypePartyTypeChoicesBean(labels, values, defaultValue);
    }

    private void updatePaymentMethodTypePartyTypeFromValue(final PaymentMethodTypePartyTypeDetailValue paymentMethodTypePartyTypeDetailValue,
            final boolean checkDefault, final BasePK updatedBy) {
        if(paymentMethodTypePartyTypeDetailValue.hasBeenModified()) {
            var paymentMethodTypePartyType = paymentMethodTypePartyTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    paymentMethodTypePartyTypeDetailValue.getPaymentMethodTypePartyTypePK());
            var paymentMethodTypePartyTypeDetail = paymentMethodTypePartyType.getActiveDetailForUpdate();

            paymentMethodTypePartyTypeDetail.setThruTime(session.getStartTime());
            paymentMethodTypePartyTypeDetail.store();

            var paymentMethodTypePartyTypePK = paymentMethodTypePartyTypeDetail.getPaymentMethodTypePartyTypePK(); // R/O
            var paymentMethodType = paymentMethodTypePartyTypeDetail.getPaymentMethodType();
            var paymentMethodTypePK = paymentMethodType.getPrimaryKey(); // R/O
            var partyType = paymentMethodTypePartyTypeDetail.getPartyType();
            var partyTypePK = partyType.getPrimaryKey(); // R/O
            var partyPaymentMethodWorkflowPK = paymentMethodTypePartyTypeDetailValue.getPartyPaymentMethodWorkflowPK(); // R/W
            var partyContactMechanismWorkflowPK = paymentMethodTypePartyTypeDetailValue.getPartyContactMechanismWorkflowPK(); // R/W
            var isDefault = paymentMethodTypePartyTypeDetailValue.getIsDefault(); // R/W
            var sortOrder = paymentMethodTypePartyTypeDetailValue.getSortOrder(); // R/W

            if(checkDefault) {
                var defaultPaymentMethodTypePartyType = getDefaultPaymentMethodTypePartyType(paymentMethodType);
                var defaultFound = defaultPaymentMethodTypePartyType != null && !defaultPaymentMethodTypePartyType.equals(paymentMethodTypePartyType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPaymentMethodTypePartyTypeDetailValue = getDefaultPaymentMethodTypePartyTypeDetailValueForUpdate(paymentMethodType);

                    defaultPaymentMethodTypePartyTypeDetailValue.setIsDefault(false);
                    updatePaymentMethodTypePartyTypeFromValue(defaultPaymentMethodTypePartyTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            paymentMethodTypePartyTypeDetail = paymentMethodTypePartyTypeDetailFactory.create(paymentMethodTypePartyTypePK,
                    paymentMethodTypePK, partyTypePK, partyPaymentMethodWorkflowPK, partyContactMechanismWorkflowPK,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            paymentMethodTypePartyType.setActiveDetail(paymentMethodTypePartyTypeDetail);
            paymentMethodTypePartyType.setLastDetail(paymentMethodTypePartyTypeDetail);

            sendEvent(paymentMethodTypePK, EventTypes.MODIFY, paymentMethodTypePartyType.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void updatePaymentMethodTypePartyTypeFromValue(final PaymentMethodTypePartyTypeDetailValue paymentMethodTypePartyTypeDetailValue,
            final BasePK updatedBy) {
        updatePaymentMethodTypePartyTypeFromValue(paymentMethodTypePartyTypeDetailValue, true, updatedBy);
    }

    public void deletePaymentMethodTypePartyType(final PaymentMethodTypePartyType paymentMethodTypePartyType, final BasePK deletedBy) {
        var paymentMethodTypePartyTypeDetail = paymentMethodTypePartyType.getLastDetailForUpdate();
        paymentMethodTypePartyTypeDetail.setThruTime(session.getStartTime());
        paymentMethodTypePartyTypeDetail.store();
        paymentMethodTypePartyType.setActiveDetail(null);

        // Check for default, and pick one if necessary
        var paymentMethodType = paymentMethodTypePartyTypeDetail.getPaymentMethodType();
        var defaultPaymentMethodTypePartyType = getDefaultPaymentMethodTypePartyType(paymentMethodType);
        if(defaultPaymentMethodTypePartyType == null) {
            var paymentMethodTypePartyTypes = getPaymentMethodTypePartyTypesByPaymentMethodTypeForUpdate(paymentMethodType);

            if(!paymentMethodTypePartyTypes.isEmpty()) {
                var iter = paymentMethodTypePartyTypes.iterator();
                if(iter.hasNext()) {
                    defaultPaymentMethodTypePartyType = iter.next();
                }
                var paymentMethodTypePartyTypeDetailValue = Objects.requireNonNull(defaultPaymentMethodTypePartyType).getLastDetailForUpdate().getPaymentMethodTypePartyTypeDetailValue().clone();

                paymentMethodTypePartyTypeDetailValue.setIsDefault(true);
                updatePaymentMethodTypePartyTypeFromValue(paymentMethodTypePartyTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(paymentMethodType.getPrimaryKey(), EventTypes.MODIFY, paymentMethodTypePartyType.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deletePaymentMethodTypePartyTypes(final Collection<PaymentMethodTypePartyType> paymentMethodTypePartyTypes, final BasePK deletedBy) {
        paymentMethodTypePartyTypes.forEach(paymentMethodTypePartyType -> deletePaymentMethodTypePartyType(paymentMethodTypePartyType, deletedBy));
    }

    public void deletePaymentMethodTypePartyTypesByPaymentMethodType(final PaymentMethodType paymentMethodType, final BasePK deletedBy) {
        deletePaymentMethodTypePartyTypes(getPaymentMethodTypePartyTypesByPaymentMethodTypeForUpdate(paymentMethodType), deletedBy);
    }

    public void deletePaymentMethodTypePartyTypesByPartyType(final PartyType partyType, final BasePK deletedBy) {
        deletePaymentMethodTypePartyTypes(getPaymentMethodTypePartyTypesByPartyTypeForUpdate(partyType), deletedBy);
    }

}

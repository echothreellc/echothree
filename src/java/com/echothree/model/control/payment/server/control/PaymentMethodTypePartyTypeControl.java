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
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.payment.common.choice.PaymentMethodTypePartyTypeChoicesBean;
import com.echothree.model.control.payment.common.transfer.PaymentMethodTypePartyTypeTransfer;
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
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PaymentMethodTypePartyTypeControl
        extends BasePaymentControl {

    /** Creates a new instance of PaymentMethodTypePartyTypeControl */
    protected PaymentMethodTypePartyTypeControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Payment Method Type Party Types
    // --------------------------------------------------------------------------------

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

        var paymentMethodTypePartyType = PaymentMethodTypePartyTypeFactory.getInstance().create();
        var paymentMethodTypePartyTypeDetail = PaymentMethodTypePartyTypeDetailFactory.getInstance().create(session,
                paymentMethodTypePartyType, paymentMethodType, partyType, partyPaymentMethodWorkflow,
                partyContactMechanismWorkflow, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        paymentMethodTypePartyType = PaymentMethodTypePartyTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, paymentMethodTypePartyType.getPrimaryKey());
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

        return PaymentMethodTypePartyTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public PaymentMethodTypePartyType getPaymentMethodTypePartyTypeByEntityInstance(final EntityInstance entityInstance) {
        return getPaymentMethodTypePartyTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PaymentMethodTypePartyType getPaymentMethodTypePartyTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getPaymentMethodTypePartyTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPaymentMethodTypePartyTypeQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentmethodtypepartytypes, paymentmethodtypepartytypedetails " +
                    "WHERE pmtypptyp_activedetailid = pmtypptypdt_paymentmethodtypepartytypedetailid " +
                    "AND pmtypptypdt_pmtyp_paymentmethodtypeid = ? AND pmtypptypdt_ptyp_partytypeid = ?",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " + "FROM paymentmethodtypepartytypes, paymentmethodtypepartytypedetails " +
                    "WHERE pmtypptyp_activedetailid = pmtypptypdt_paymentmethodtypepartytypedetailid " +
                    "AND pmtypptypdt_pmtyp_paymentmethodtypeid = ? AND pmtypptypdt_ptyp_partytypeid = ? " +
                    "FOR UPDATE");

    public PaymentMethodTypePartyType getPaymentMethodTypePartyType(final PaymentMethodType paymentMethodType,
            final PartyType partyType, final EntityPermission entityPermission) {
        return PaymentMethodTypePartyTypeFactory.getInstance().getEntityFromQuery(entityPermission, getPaymentMethodTypePartyTypeQueries,
                paymentMethodType, partyType);
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

    private static final Map<EntityPermission, String> getDefaultPaymentMethodTypePartyTypeQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentmethodtypepartytypes, paymentmethodtypepartytypedetails " +
                    "WHERE pmtypptyp_activedetailid = pmtypptypdt_paymentmethodtypepartytypedetailid " +
                    "AND pmtypptypdt_pmtyp_paymentmethodtypeid = ? AND pmtypptypdt_isdefault = 1",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentmethodtypepartytypes, paymentmethodtypepartytypedetails " +
                    "WHERE pmtypptyp_activedetailid = pmtypptypdt_paymentmethodtypepartytypedetailid " +
                    "AND pmtypptypdt_pmtyp_paymentmethodtypeid = ? AND pmtypptypdt_isdefault = 1 " +
                    "FOR UPDATE");

    public PaymentMethodTypePartyType getDefaultPaymentMethodTypePartyType(final PaymentMethodType paymentMethodType,
            final EntityPermission entityPermission) {
        return PaymentMethodTypePartyTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultPaymentMethodTypePartyTypeQueries,
                paymentMethodType);
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

    private static final Map<EntityPermission, String> getPaymentMethodTypePartyTypesByPaymentMethodTypeQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentmethodtypepartytypes, paymentmethodtypepartytypedetails, partytypes " +
                    "WHERE pmtypptyp_activedetailid = pmtypptypdt_paymentmethodtypepartytypedetailid " +
                    "AND pmtypptypdt_ptyp_partytypeid = ptyp_partytypeid " +
                    "AND pmtypptypdt_pmtyp_paymentmethodtypeid = ? " +
                    "ORDER BY ptyp_sortorder, ptyp_partytypename",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentmethodtypepartytypes, paymentmethodtypepartytypedetails " +
                    "WHERE pmtypptyp_activedetailid = pmtypptypdt_paymentmethodtypepartytypedetailid " +
                    "AND pmtypptypdt_pmtyp_paymentmethodtypeid = ? " +
                    "FOR UPDATE");

    private List<PaymentMethodTypePartyType> getPaymentMethodTypePartyTypesByPaymentMethodType(final PaymentMethodType paymentMethodType, final EntityPermission entityPermission) {
        return PaymentMethodTypePartyTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getPaymentMethodTypePartyTypesByPaymentMethodTypeQueries,
                paymentMethodType);
    }

    public List<PaymentMethodTypePartyType> getPaymentMethodTypePartyTypesByPaymentMethodType(final PaymentMethodType paymentMethodType) {
        return getPaymentMethodTypePartyTypesByPaymentMethodType(paymentMethodType, EntityPermission.READ_ONLY);
    }

    public List<PaymentMethodTypePartyType> getPaymentMethodTypePartyTypesByPaymentMethodTypeForUpdate(final PaymentMethodType paymentMethodType) {
        return getPaymentMethodTypePartyTypesByPaymentMethodType(paymentMethodType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPaymentMethodTypePartyTypesByPartyTypeQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentmethodtypepartytypes, paymentmethodtypepartytypedetails, paymentmethodtypes, paymentmethodtypedetails " +
                    "WHERE pmtypptyp_activedetailid = pmtypptypdt_paymentmethodtypepartytypedetailid " +
                    "AND pmtypptypdt_pmtyp_paymentmethodtypeid = pmtyp_paymentmethodtypeid AND pmtyp_activedetailid = pmtypdt_paymentmethodtypedetailid " +
                    "AND pmtypptypdt_ptyp_partytypeid = ? " +
                    "ORDER BY pmtypdt_sortorder, pmtypdt_paymentmethodtypename",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentmethodtypepartytypes, paymentmethodtypepartytypedetails " +
                    "WHERE pmtypptyp_activedetailid = pmtypptypdt_paymentmethodtypepartytypedetailid " +
                    "AND pmtypptypdt_ptyp_partytypeid = ? " +
                    "FOR UPDATE");

    private List<PaymentMethodTypePartyType> getPaymentMethodTypePartyTypesByPartyType(final PartyType partyType, final EntityPermission entityPermission) {
        return PaymentMethodTypePartyTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getPaymentMethodTypePartyTypesByPartyTypeQueries,
                partyType);
    }

    public List<PaymentMethodTypePartyType> getPaymentMethodTypePartyTypesByPartyType(final PartyType partyType) {
        return getPaymentMethodTypePartyTypesByPartyType(partyType, EntityPermission.READ_ONLY);
    }

    public List<PaymentMethodTypePartyType> getPaymentMethodTypePartyTypesByPartyTypeForUpdate(final PartyType partyType) {
        return getPaymentMethodTypePartyTypesByPartyType(partyType, EntityPermission.READ_WRITE);
    }

    public PaymentMethodTypePartyTypeTransfer getPaymentMethodTypePartyTypeTransfer(final UserVisit userVisit,
            final PaymentMethodTypePartyType paymentMethodTypePartyType) {
        return paymentTransferCaches.getPaymentMethodTypePartyTypeTransferCache().getTransfer(userVisit, paymentMethodTypePartyType);
    }

    public List<PaymentMethodTypePartyTypeTransfer> getPaymentMethodTypePartyTypeTransfers(final UserVisit userVisit,
            final Collection<PaymentMethodTypePartyType> paymentMethodTypePartyTypes) {
        var paymentMethodTypePartyTypeTransfers = new ArrayList<PaymentMethodTypePartyTypeTransfer>(paymentMethodTypePartyTypes.size());
        var paymentMethodTypePartyTypeTransferCache = paymentTransferCaches.getPaymentMethodTypePartyTypeTransferCache();

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
        var partyControl = Session.getModelController(PartyControl.class);
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
            var paymentMethodTypePartyType = PaymentMethodTypePartyTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    paymentMethodTypePartyTypeDetailValue.getPaymentMethodTypePartyTypePK());
            var paymentMethodTypePartyTypeDetail = paymentMethodTypePartyType.getActiveDetailForUpdate();

            paymentMethodTypePartyTypeDetail.setThruTime(session.START_TIME_LONG);
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

            paymentMethodTypePartyTypeDetail = PaymentMethodTypePartyTypeDetailFactory.getInstance().create(paymentMethodTypePartyTypePK,
                    paymentMethodTypePK, partyTypePK, partyPaymentMethodWorkflowPK, partyContactMechanismWorkflowPK,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
        paymentMethodTypePartyTypeDetail.setThruTime(session.START_TIME_LONG);
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

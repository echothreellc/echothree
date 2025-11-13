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
import com.echothree.model.control.payment.common.choice.PaymentMethodTypeChoicesBean;
import com.echothree.model.control.payment.common.transfer.PaymentMethodTypeDescriptionTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentMethodTypeTransfer;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.payment.common.pk.PaymentMethodTypePK;
import com.echothree.model.data.payment.server.entity.PaymentMethodType;
import com.echothree.model.data.payment.server.entity.PaymentMethodTypeDescription;
import com.echothree.model.data.payment.server.factory.PaymentMethodTypeDescriptionFactory;
import com.echothree.model.data.payment.server.factory.PaymentMethodTypeDetailFactory;
import com.echothree.model.data.payment.server.factory.PaymentMethodTypeFactory;
import com.echothree.model.data.payment.server.value.PaymentMethodTypeDescriptionValue;
import com.echothree.model.data.payment.server.value.PaymentMethodTypeDetailValue;
import com.echothree.model.data.user.server.entity.UserVisit;
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
public class PaymentMethodTypeControl
        extends BasePaymentControl {

    /** Creates a new instance of PaymentMethodTypeControl */
    protected PaymentMethodTypeControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Payment Method Types
    // --------------------------------------------------------------------------------

    public PaymentMethodType createPaymentMethodType(final String paymentMethodTypeName, Boolean isDefault,
            final Integer sortOrder, final BasePK createdBy) {
        var defaultPaymentMethodType = getDefaultPaymentMethodType();
        var defaultFound = defaultPaymentMethodType != null;

        if(defaultFound && isDefault) {
            var defaultPaymentMethodTypeDetailValue = getDefaultPaymentMethodTypeDetailValueForUpdate();

            defaultPaymentMethodTypeDetailValue.setIsDefault(false);
            updatePaymentMethodTypeFromValue(defaultPaymentMethodTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var paymentMethodType = PaymentMethodTypeFactory.getInstance().create();
        var paymentMethodTypeDetail = PaymentMethodTypeDetailFactory.getInstance().create(session,
                paymentMethodType, paymentMethodTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        paymentMethodType = PaymentMethodTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, paymentMethodType.getPrimaryKey());
        paymentMethodType.setActiveDetail(paymentMethodTypeDetail);
        paymentMethodType.setLastDetail(paymentMethodTypeDetail);
        paymentMethodType.store();

        sendEvent(paymentMethodType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return paymentMethodType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PaymentMethodType */
    public PaymentMethodType getPaymentMethodTypeByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new PaymentMethodTypePK(entityInstance.getEntityUniqueId());

        return PaymentMethodTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public PaymentMethodType getPaymentMethodTypeByEntityInstance(final EntityInstance entityInstance) {
        return getPaymentMethodTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PaymentMethodType getPaymentMethodTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getPaymentMethodTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPaymentMethodTypeByNameQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentmethodtypes, paymentmethodtypedetails " +
                    "WHERE pmtyp_paymentmethodtypeid = pmtypdt_pmtyp_paymentmethodtypeid AND pmtypdt_paymentmethodtypename = ? AND pmtypdt_thrutime = ?",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " + "FROM paymentmethodtypes, paymentmethodtypedetails " +
                    "WHERE pmtyp_paymentmethodtypeid = pmtypdt_pmtyp_paymentmethodtypeid AND pmtypdt_paymentmethodtypename = ? AND pmtypdt_thrutime = ? " +
                    "FOR UPDATE");

    public PaymentMethodType getPaymentMethodTypeByName(final String paymentMethodTypeName, final EntityPermission entityPermission) {
        return PaymentMethodTypeFactory.getInstance().getEntityFromQuery(entityPermission, getPaymentMethodTypeByNameQueries,
                paymentMethodTypeName, Session.MAX_TIME);
    }

    public PaymentMethodType getPaymentMethodTypeByName(final String paymentMethodTypeName) {
        return getPaymentMethodTypeByName(paymentMethodTypeName, EntityPermission.READ_ONLY);
    }

    public PaymentMethodType getPaymentMethodTypeByNameForUpdate(final String paymentMethodTypeName) {
        return getPaymentMethodTypeByName(paymentMethodTypeName, EntityPermission.READ_WRITE);
    }

    public PaymentMethodTypeDetailValue getPaymentMethodTypeDetailValueForUpdate(final PaymentMethodType paymentMethodType) {
        return paymentMethodType == null ? null: paymentMethodType.getLastDetailForUpdate().getPaymentMethodTypeDetailValue().clone();
    }

    public PaymentMethodTypeDetailValue getPaymentMethodTypeDetailValueByNameForUpdate(final String paymentMethodTypeName) {
        return getPaymentMethodTypeDetailValueForUpdate(getPaymentMethodTypeByNameForUpdate(paymentMethodTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultPaymentMethodTypeQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentmethodtypes, paymentmethodtypedetails " +
                    "WHERE pmtyp_paymentmethodtypeid = pmtypdt_pmtyp_paymentmethodtypeid AND pmtypdt_isdefault = 1 AND pmtypdt_thrutime = ?",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentmethodtypes, paymentmethodtypedetails " +
                    "WHERE pmtyp_paymentmethodtypeid = pmtypdt_pmtyp_paymentmethodtypeid AND pmtypdt_isdefault = 1 AND pmtypdt_thrutime = ? " +
                    "FOR UPDATE");

    public PaymentMethodType getDefaultPaymentMethodType(final EntityPermission entityPermission) {
        return PaymentMethodTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultPaymentMethodTypeQueries,
                Session.MAX_TIME);
    }

    public PaymentMethodType getDefaultPaymentMethodType() {
        return getDefaultPaymentMethodType(EntityPermission.READ_ONLY);
    }

    public PaymentMethodType getDefaultPaymentMethodTypeForUpdate() {
        return getDefaultPaymentMethodType(EntityPermission.READ_WRITE);
    }

    public PaymentMethodTypeDetailValue getDefaultPaymentMethodTypeDetailValueForUpdate() {
        return getDefaultPaymentMethodTypeForUpdate().getLastDetailForUpdate().getPaymentMethodTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getPaymentMethodTypesQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " + "FROM paymentmethodtypes, paymentmethodtypedetails " +
                    "WHERE pmtyp_paymentmethodtypeid = pmtypdt_pmtyp_paymentmethodtypeid AND pmtypdt_thrutime = ? " +
                    "ORDER BY pmtypdt_sortorder, pmtypdt_paymentmethodtypename",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " + "FROM paymentmethodtypes, paymentmethodtypedetails " +
                    "WHERE pmtyp_paymentmethodtypeid = pmtypdt_pmtyp_paymentmethodtypeid AND pmtypdt_thrutime = ? " +
                    "FOR UPDATE");

    private List<PaymentMethodType> getPaymentMethodTypes(final EntityPermission entityPermission) {
        return PaymentMethodTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getPaymentMethodTypesQueries,
                Session.MAX_TIME);
    }

    public List<PaymentMethodType> getPaymentMethodTypes() {
        return getPaymentMethodTypes(EntityPermission.READ_ONLY);
    }

    public List<PaymentMethodType> getPaymentMethodTypesForUpdate() {
        return getPaymentMethodTypes(EntityPermission.READ_WRITE);
    }

    public PaymentMethodTypeTransfer getPaymentMethodTypeTransfer(final UserVisit userVisit,
            final PaymentMethodType paymentMethodType) {
        return getPaymentTransferCaches().getPaymentMethodTypeTransferCache().getTransfer(userVisit, paymentMethodType);
    }

    public List<PaymentMethodTypeTransfer> getPaymentMethodTypeTransfers(final UserVisit userVisit,
            final Collection<PaymentMethodType> paymentMethodTypes) {
        var paymentMethodTypeTransfers = new ArrayList<PaymentMethodTypeTransfer>(paymentMethodTypes.size());
        var paymentMethodTypeTransferCache = getPaymentTransferCaches().getPaymentMethodTypeTransferCache();

        paymentMethodTypes.forEach((paymentMethodType) ->
                paymentMethodTypeTransfers.add(paymentMethodTypeTransferCache.getTransfer(userVisit, paymentMethodType))
        );

        return paymentMethodTypeTransfers;
    }

    public List<PaymentMethodTypeTransfer> getPaymentMethodTypeTransfers(final UserVisit userVisit) {
        return getPaymentMethodTypeTransfers(userVisit, getPaymentMethodTypes());
    }

    public PaymentMethodTypeChoicesBean getPaymentMethodTypeChoices(final String defaultPaymentMethodTypeChoice,
            final Language language, final boolean allowNullChoice) {
        var paymentMethodTypes = getPaymentMethodTypes();
        var size = paymentMethodTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultPaymentMethodTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var paymentMethodType : paymentMethodTypes) {
            var paymentMethodTypeDetail = paymentMethodType.getLastDetail();

            var label = getBestPaymentMethodTypeDescription(paymentMethodType, language);
            var value = paymentMethodTypeDetail.getPaymentMethodTypeName();

            labels.add(label == null ? value : label);
            values.add(value);

            var usingDefaultChoice = Objects.equals(defaultPaymentMethodTypeChoice, value);
            if(usingDefaultChoice || (defaultValue == null && paymentMethodTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new PaymentMethodTypeChoicesBean(labels, values, defaultValue);
    }

    private void updatePaymentMethodTypeFromValue(final PaymentMethodTypeDetailValue paymentMethodTypeDetailValue,
            final boolean checkDefault, final BasePK updatedBy) {
        if(paymentMethodTypeDetailValue.hasBeenModified()) {
            var paymentMethodType = PaymentMethodTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    paymentMethodTypeDetailValue.getPaymentMethodTypePK());
            var paymentMethodTypeDetail = paymentMethodType.getActiveDetailForUpdate();

            paymentMethodTypeDetail.setThruTime(session.START_TIME_LONG);
            paymentMethodTypeDetail.store();

            var paymentMethodTypePK = paymentMethodTypeDetail.getPaymentMethodTypePK();
            var paymentMethodTypeName = paymentMethodTypeDetailValue.getPaymentMethodTypeName();
            var isDefault = paymentMethodTypeDetailValue.getIsDefault();
            var sortOrder = paymentMethodTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultPaymentMethodType = getDefaultPaymentMethodType();
                var defaultFound = defaultPaymentMethodType != null && !defaultPaymentMethodType.equals(paymentMethodType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPaymentMethodTypeDetailValue = getDefaultPaymentMethodTypeDetailValueForUpdate();

                    defaultPaymentMethodTypeDetailValue.setIsDefault(false);
                    updatePaymentMethodTypeFromValue(defaultPaymentMethodTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            paymentMethodTypeDetail = PaymentMethodTypeDetailFactory.getInstance().create(paymentMethodTypePK,
                    paymentMethodTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            paymentMethodType.setActiveDetail(paymentMethodTypeDetail);
            paymentMethodType.setLastDetail(paymentMethodTypeDetail);

            sendEvent(paymentMethodTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updatePaymentMethodTypeFromValue(final PaymentMethodTypeDetailValue paymentMethodTypeDetailValue,
            final BasePK updatedBy) {
        updatePaymentMethodTypeFromValue(paymentMethodTypeDetailValue, true, updatedBy);
    }

    public void deletePaymentMethodType(final PaymentMethodType paymentMethodType, final BasePK deletedBy) {
        var paymentMethodTypePartyTypeControl = Session.getModelController(PaymentMethodTypePartyTypeControl.class);

        paymentMethodTypePartyTypeControl.deletePaymentMethodTypePartyTypesByPaymentMethodType(paymentMethodType, deletedBy);
        deletePaymentMethodTypeDescriptionsByPaymentMethodType(paymentMethodType, deletedBy);

        var paymentMethodTypeDetail = paymentMethodType.getLastDetailForUpdate();
        paymentMethodTypeDetail.setThruTime(session.START_TIME_LONG);
        paymentMethodTypeDetail.store();
        paymentMethodType.setActiveDetail(null);

        // Check for default, and pick one if necessary
        var defaultPaymentMethodType = getDefaultPaymentMethodType();
        if(defaultPaymentMethodType == null) {
            var paymentMethodTypes = getPaymentMethodTypesForUpdate();

            if(!paymentMethodTypes.isEmpty()) {
                var iter = paymentMethodTypes.iterator();
                if(iter.hasNext()) {
                    defaultPaymentMethodType = iter.next();
                }
                var paymentMethodTypeDetailValue = Objects.requireNonNull(defaultPaymentMethodType).getLastDetailForUpdate().getPaymentMethodTypeDetailValue().clone();

                paymentMethodTypeDetailValue.setIsDefault(true);
                updatePaymentMethodTypeFromValue(paymentMethodTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(paymentMethodType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Payment Method Type Descriptions
    // --------------------------------------------------------------------------------

    public PaymentMethodTypeDescription createPaymentMethodTypeDescription(final PaymentMethodType paymentMethodType,
            final Language language, final String description, final BasePK createdBy) {
        var paymentMethodTypeDescription = PaymentMethodTypeDescriptionFactory.getInstance().create(paymentMethodType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(paymentMethodType.getPrimaryKey(), EventTypes.MODIFY, paymentMethodTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return paymentMethodTypeDescription;
    }

    private static final Map<EntityPermission, String> getPaymentMethodTypeDescriptionQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentmethodtypedescriptions " +
                    "WHERE pmtypd_pmtyp_paymentmethodtypeid = ? AND pmtypd_lang_languageid = ? AND pmtypd_thrutime = ?",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentmethodtypedescriptions " +
                    "WHERE pmtypd_pmtyp_paymentmethodtypeid = ? AND pmtypd_lang_languageid = ? AND pmtypd_thrutime = ? " +
                    "FOR UPDATE");

    private PaymentMethodTypeDescription getPaymentMethodTypeDescription(final PaymentMethodType paymentMethodType,
            final Language language, final EntityPermission entityPermission) {
        return PaymentMethodTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getPaymentMethodTypeDescriptionQueries,
                paymentMethodType, language, Session.MAX_TIME);
    }

    public PaymentMethodTypeDescription getPaymentMethodTypeDescription(final PaymentMethodType paymentMethodType,
            final Language language) {
        return getPaymentMethodTypeDescription(paymentMethodType, language, EntityPermission.READ_ONLY);
    }

    public PaymentMethodTypeDescription getPaymentMethodTypeDescriptionForUpdate(final PaymentMethodType paymentMethodType,
            final Language language) {
        return getPaymentMethodTypeDescription(paymentMethodType, language, EntityPermission.READ_WRITE);
    }

    public PaymentMethodTypeDescriptionValue getPaymentMethodTypeDescriptionValue(final PaymentMethodTypeDescription paymentMethodTypeDescription) {
        return paymentMethodTypeDescription == null ? null: paymentMethodTypeDescription.getPaymentMethodTypeDescriptionValue().clone();
    }

    public PaymentMethodTypeDescriptionValue getPaymentMethodTypeDescriptionValueForUpdate(final PaymentMethodType paymentMethodType,
            final Language language) {
        return getPaymentMethodTypeDescriptionValue(getPaymentMethodTypeDescriptionForUpdate(paymentMethodType, language));
    }

    private static final Map<EntityPermission, String> getPaymentMethodTypeDescriptionsByPaymentMethodTypeQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentmethodtypedescriptions, languages " +
                    "WHERE pmtypd_pmtyp_paymentmethodtypeid = ? AND pmtypd_thrutime = ? AND pmtypd_lang_languageid = lang_languageid " +
                    "ORDER BY lang_sortorder, lang_languageisoname",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentmethodtypedescriptions " +
                    "WHERE pmtypd_pmtyp_paymentmethodtypeid = ? AND pmtypd_thrutime = ? " +
                    "FOR UPDATE");

    private List<PaymentMethodTypeDescription> getPaymentMethodTypeDescriptionsByPaymentMethodType(final PaymentMethodType paymentMethodType,
            final EntityPermission entityPermission) {
        return PaymentMethodTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission,
                getPaymentMethodTypeDescriptionsByPaymentMethodTypeQueries,
                paymentMethodType, Session.MAX_TIME);
    }

    public List<PaymentMethodTypeDescription> getPaymentMethodTypeDescriptionsByPaymentMethodType(final PaymentMethodType paymentMethodType) {
        return getPaymentMethodTypeDescriptionsByPaymentMethodType(paymentMethodType, EntityPermission.READ_ONLY);
    }

    public List<PaymentMethodTypeDescription> getPaymentMethodTypeDescriptionsByPaymentMethodTypeForUpdate(final PaymentMethodType paymentMethodType) {
        return getPaymentMethodTypeDescriptionsByPaymentMethodType(paymentMethodType, EntityPermission.READ_WRITE);
    }

    public String getBestPaymentMethodTypeDescription(final PaymentMethodType paymentMethodType, final Language language) {
        var paymentMethodTypeDescription = getPaymentMethodTypeDescription(paymentMethodType, language);
        String description;

        if(paymentMethodTypeDescription == null && !language.getIsDefault()) {
            paymentMethodTypeDescription = getPaymentMethodTypeDescription(paymentMethodType, partyControl.getDefaultLanguage());
        }

        if(paymentMethodTypeDescription == null) {
            description = paymentMethodType.getLastDetail().getPaymentMethodTypeName();
        } else {
            description = paymentMethodTypeDescription.getDescription();
        }

        return description;
    }

    public PaymentMethodTypeDescriptionTransfer getPaymentMethodTypeDescriptionTransfer(final UserVisit userVisit,
            final PaymentMethodTypeDescription paymentMethodTypeDescription) {
        return getPaymentTransferCaches().getPaymentMethodTypeDescriptionTransferCache().getTransfer(userVisit, paymentMethodTypeDescription);
    }

    public List<PaymentMethodTypeDescriptionTransfer> getPaymentMethodTypeDescriptionTransfersByPaymentMethodType(final UserVisit userVisit,
            final PaymentMethodType paymentMethodType) {
        var paymentMethodTypeDescriptions = getPaymentMethodTypeDescriptionsByPaymentMethodType(paymentMethodType);
        var paymentMethodTypeDescriptionTransfers = new ArrayList<PaymentMethodTypeDescriptionTransfer>(paymentMethodTypeDescriptions.size());
        var paymentMethodTypeDescriptionTransferCache = getPaymentTransferCaches().getPaymentMethodTypeDescriptionTransferCache();

        paymentMethodTypeDescriptions.forEach((paymentMethodTypeDescription) ->
                paymentMethodTypeDescriptionTransfers.add(paymentMethodTypeDescriptionTransferCache.getTransfer(userVisit, paymentMethodTypeDescription))
        );

        return paymentMethodTypeDescriptionTransfers;
    }

    public void updatePaymentMethodTypeDescriptionFromValue(final PaymentMethodTypeDescriptionValue paymentMethodTypeDescriptionValue,
            final BasePK updatedBy) {
        if(paymentMethodTypeDescriptionValue.hasBeenModified()) {
            var paymentMethodTypeDescription = PaymentMethodTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, paymentMethodTypeDescriptionValue.getPrimaryKey());

            paymentMethodTypeDescription.setThruTime(session.START_TIME_LONG);
            paymentMethodTypeDescription.store();

            var paymentMethodType = paymentMethodTypeDescription.getPaymentMethodType();
            var language = paymentMethodTypeDescription.getLanguage();
            var description = paymentMethodTypeDescriptionValue.getDescription();

            paymentMethodTypeDescription = PaymentMethodTypeDescriptionFactory.getInstance().create(paymentMethodType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(paymentMethodType.getPrimaryKey(), EventTypes.MODIFY, paymentMethodTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePaymentMethodTypeDescription(final PaymentMethodTypeDescription paymentMethodTypeDescription, final BasePK deletedBy) {
        paymentMethodTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(paymentMethodTypeDescription.getPaymentMethodTypePK(), EventTypes.MODIFY, paymentMethodTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deletePaymentMethodTypeDescriptionsByPaymentMethodType(final PaymentMethodType paymentMethodType, final BasePK deletedBy) {
        var paymentMethodTypeDescriptions = getPaymentMethodTypeDescriptionsByPaymentMethodTypeForUpdate(paymentMethodType);

        paymentMethodTypeDescriptions.forEach((paymentMethodTypeDescription) -> {
            deletePaymentMethodTypeDescription(paymentMethodTypeDescription, deletedBy);
        });
    }

}

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
import com.echothree.model.control.payment.common.choice.PaymentProcessorTypeCodeTypeChoicesBean;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorTypeCodeTypeDescriptionTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorTypeCodeTypeTransfer;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.payment.common.pk.PaymentProcessorTypeCodeTypePK;
import com.echothree.model.data.payment.server.entity.PaymentProcessorType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeCodeType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeCodeTypeDescription;
import com.echothree.model.data.payment.server.factory.PaymentProcessorTypeCodeTypeDescriptionFactory;
import com.echothree.model.data.payment.server.factory.PaymentProcessorTypeCodeTypeDetailFactory;
import com.echothree.model.data.payment.server.factory.PaymentProcessorTypeCodeTypeFactory;
import com.echothree.model.data.payment.server.value.PaymentProcessorTypeCodeTypeDescriptionValue;
import com.echothree.model.data.payment.server.value.PaymentProcessorTypeCodeTypeDetailValue;
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
public class PaymentProcessorTypeCodeTypeControl
        extends BasePaymentControl {

    /** Creates a new instance of PaymentProcessorTypeCodeTypeControl */
    protected PaymentProcessorTypeCodeTypeControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Payment Processor Type Code Types
    // --------------------------------------------------------------------------------

    public PaymentProcessorTypeCodeType createPaymentProcessorTypeCodeType(final PaymentProcessorType paymentProcessorType,
            final String paymentProcessorTypeCodeTypeName, Boolean isDefault, final Integer sortOrder, final BasePK createdBy) {
        var defaultPaymentProcessorTypeCodeType = getDefaultPaymentProcessorTypeCodeType(paymentProcessorType);
        var defaultFound = defaultPaymentProcessorTypeCodeType != null;

        if(defaultFound && isDefault) {
            var defaultPaymentProcessorTypeCodeTypeDetailValue = getDefaultPaymentProcessorTypeCodeTypeDetailValueForUpdate(paymentProcessorType);

            defaultPaymentProcessorTypeCodeTypeDetailValue.setIsDefault(false);
            updatePaymentProcessorTypeCodeTypeFromValue(defaultPaymentProcessorTypeCodeTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var paymentProcessorTypeCodeType = PaymentProcessorTypeCodeTypeFactory.getInstance().create();
        var paymentProcessorTypeCodeTypeDetail = PaymentProcessorTypeCodeTypeDetailFactory.getInstance().create(session,
                paymentProcessorTypeCodeType, paymentProcessorType, paymentProcessorTypeCodeTypeName, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME_LONG);

        // Convert to R/W
        paymentProcessorTypeCodeType = PaymentProcessorTypeCodeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, paymentProcessorTypeCodeType.getPrimaryKey());
        paymentProcessorTypeCodeType.setActiveDetail(paymentProcessorTypeCodeTypeDetail);
        paymentProcessorTypeCodeType.setLastDetail(paymentProcessorTypeCodeTypeDetail);
        paymentProcessorTypeCodeType.store();

        sendEvent(paymentProcessorTypeCodeType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return paymentProcessorTypeCodeType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PaymentProcessorTypeCodeType */
    public PaymentProcessorTypeCodeType getPaymentProcessorTypeCodeTypeByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new PaymentProcessorTypeCodeTypePK(entityInstance.getEntityUniqueId());

        return PaymentProcessorTypeCodeTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public PaymentProcessorTypeCodeType getPaymentProcessorTypeCodeTypeByEntityInstance(final EntityInstance entityInstance) {
        return getPaymentProcessorTypeCodeTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorTypeCodeType getPaymentProcessorTypeCodeTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getPaymentProcessorTypeCodeTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPaymentProcessorTypeCodeTypeByNameQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypecodetypes, paymentprocessortypecodetypedetails " +
                    "WHERE pproctypctyp_activedetailid = pproctypctypdt_paymentprocessortypecodetypedetailid " +
                    "AND pproctypctypdt_pprctyp_paymentprocessortypeid = ? AND pproctypctypdt_paymentprocessortypecodetypename = ?",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " + "FROM paymentprocessortypecodetypes, paymentprocessortypecodetypedetails " +
                    "WHERE pproctypctyp_activedetailid = pproctypctypdt_paymentprocessortypecodetypedetailid " +
                    "AND pproctypctypdt_pprctyp_paymentprocessortypeid = ? AND pproctypctypdt_paymentprocessortypecodetypename = ? " +
                    "FOR UPDATE");

    public PaymentProcessorTypeCodeType getPaymentProcessorTypeCodeTypeByName(final PaymentProcessorType paymentProcessorType,
            final String paymentProcessorTypeCodeTypeName, final EntityPermission entityPermission) {
        return PaymentProcessorTypeCodeTypeFactory.getInstance().getEntityFromQuery(entityPermission, getPaymentProcessorTypeCodeTypeByNameQueries,
                paymentProcessorType, paymentProcessorTypeCodeTypeName);
    }

    public PaymentProcessorTypeCodeType getPaymentProcessorTypeCodeTypeByName(final PaymentProcessorType paymentProcessorType,
            final String paymentProcessorTypeCodeTypeName) {
        return getPaymentProcessorTypeCodeTypeByName(paymentProcessorType, paymentProcessorTypeCodeTypeName, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorTypeCodeType getPaymentProcessorTypeCodeTypeByNameForUpdate(final PaymentProcessorType paymentProcessorType,
            final String paymentProcessorTypeCodeTypeName) {
        return getPaymentProcessorTypeCodeTypeByName(paymentProcessorType, paymentProcessorTypeCodeTypeName, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTypeCodeTypeDetailValue getPaymentProcessorTypeCodeTypeDetailValueForUpdate(final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType) {
        return paymentProcessorTypeCodeType == null ? null : paymentProcessorTypeCodeType.getLastDetailForUpdate().getPaymentProcessorTypeCodeTypeDetailValue().clone();
    }

    public PaymentProcessorTypeCodeTypeDetailValue getPaymentProcessorTypeCodeTypeDetailValueByNameForUpdate(final PaymentProcessorType paymentProcessorType,
            final String paymentProcessorTypeCodeTypeName) {
        return getPaymentProcessorTypeCodeTypeDetailValueForUpdate(getPaymentProcessorTypeCodeTypeByNameForUpdate(paymentProcessorType, paymentProcessorTypeCodeTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultPaymentProcessorTypeCodeTypeQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypecodetypes, paymentprocessortypecodetypedetails " +
                    "WHERE pproctypctyp_activedetailid = pproctypctypdt_paymentprocessortypecodetypedetailid " +
                    "AND pproctypctypdt_pprctyp_paymentprocessortypeid = ? AND pproctypctypdt_isdefault = 1",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypecodetypes, paymentprocessortypecodetypedetails " +
                    "WHERE pproctypctyp_activedetailid = pproctypctypdt_paymentprocessortypecodetypedetailid " +
                    "AND pproctypctypdt_pprctyp_paymentprocessortypeid = ? AND pproctypctypdt_isdefault = 1 " +
                    "FOR UPDATE");

    public PaymentProcessorTypeCodeType getDefaultPaymentProcessorTypeCodeType(final PaymentProcessorType paymentProcessorType, final EntityPermission entityPermission) {
        return PaymentProcessorTypeCodeTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultPaymentProcessorTypeCodeTypeQueries,
                paymentProcessorType);
    }

    public PaymentProcessorTypeCodeType getDefaultPaymentProcessorTypeCodeType(final PaymentProcessorType paymentProcessorType) {
        return getDefaultPaymentProcessorTypeCodeType(paymentProcessorType, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorTypeCodeType getDefaultPaymentProcessorTypeCodeTypeForUpdate(final PaymentProcessorType paymentProcessorType) {
        return getDefaultPaymentProcessorTypeCodeType(paymentProcessorType, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTypeCodeTypeDetailValue getDefaultPaymentProcessorTypeCodeTypeDetailValueForUpdate(PaymentProcessorType paymentProcessorType) {
        return getDefaultPaymentProcessorTypeCodeTypeForUpdate(paymentProcessorType).getLastDetailForUpdate().getPaymentProcessorTypeCodeTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getPaymentProcessorTypeCodeTypesQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypecodetypes, paymentprocessortypecodetypedetails " +
                    "WHERE pproctypctyp_activedetailid = pproctypctypdt_paymentprocessortypecodetypedetailid AND pproctypctypdt_pprctyp_paymentprocessortypeid = ? " +
                    "ORDER BY pproctypctypdt_sortorder, pproctypctypdt_paymentprocessortypecodetypename",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypecodetypes, paymentprocessortypecodetypedetails " +
                    "WHERE pproctypctyp_activedetailid = pproctypctypdt_paymentprocessortypecodetypedetailid AND pproctypctypdt_pprctyp_paymentprocessortypeid = ? " +
                    "FOR UPDATE");

    private List<PaymentProcessorTypeCodeType> getPaymentProcessorTypeCodeTypes(final PaymentProcessorType paymentProcessorType, final EntityPermission entityPermission) {
        return PaymentProcessorTypeCodeTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getPaymentProcessorTypeCodeTypesQueries,
                paymentProcessorType);
    }

    public List<PaymentProcessorTypeCodeType> getPaymentProcessorTypeCodeTypes(final PaymentProcessorType paymentProcessorType) {
        return getPaymentProcessorTypeCodeTypes(paymentProcessorType, EntityPermission.READ_ONLY);
    }

    public List<PaymentProcessorTypeCodeType> getPaymentProcessorTypeCodeTypesForUpdate(final PaymentProcessorType paymentProcessorType) {
        return getPaymentProcessorTypeCodeTypes(paymentProcessorType, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTypeCodeTypeTransfer getPaymentProcessorTypeCodeTypeTransfer(final UserVisit userVisit,
            final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType) {
        return paymentProcessorTypeCodeTypeTransferCache.getTransfer(userVisit, paymentProcessorTypeCodeType);
    }

    public List<PaymentProcessorTypeCodeTypeTransfer> getPaymentProcessorTypeCodeTypeTransfers(final UserVisit userVisit,
            final Collection<PaymentProcessorTypeCodeType> paymentProcessorTypeCodeTypes) {
        var paymentProcessorTypeCodeTypeTransfers = new ArrayList<PaymentProcessorTypeCodeTypeTransfer>(paymentProcessorTypeCodeTypes.size());

        paymentProcessorTypeCodeTypes.forEach((paymentProcessorTypeCodeType) ->
                paymentProcessorTypeCodeTypeTransfers.add(paymentProcessorTypeCodeTypeTransferCache.getTransfer(userVisit, paymentProcessorTypeCodeType))
        );

        return paymentProcessorTypeCodeTypeTransfers;
    }

    public List<PaymentProcessorTypeCodeTypeTransfer> getPaymentProcessorTypeCodeTypeTransfers(final UserVisit userVisit, PaymentProcessorType paymentProcessorType) {
        return getPaymentProcessorTypeCodeTypeTransfers(userVisit, getPaymentProcessorTypeCodeTypes(paymentProcessorType));
    }

    public PaymentProcessorTypeCodeTypeChoicesBean getPaymentProcessorTypeCodeTypeChoices(final PaymentProcessorType paymentProcessorType,
            final String defaultPaymentProcessorTypeCodeTypeChoice, final Language language, final boolean allowNullChoice) {
        var paymentProcessorTypeCodeTypes = getPaymentProcessorTypeCodeTypes(paymentProcessorType);
        var size = paymentProcessorTypeCodeTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultPaymentProcessorTypeCodeTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var paymentProcessorTypeCodeType : paymentProcessorTypeCodeTypes) {
            var paymentProcessorTypeCodeTypeDetail = paymentProcessorTypeCodeType.getLastDetail();

            var label = getBestPaymentProcessorTypeCodeTypeDescription(paymentProcessorTypeCodeType, language);
            var value = paymentProcessorTypeCodeTypeDetail.getPaymentProcessorTypeCodeTypeName();

            labels.add(label == null ? value : label);
            values.add(value);

            var usingDefaultChoice = Objects.equals(defaultPaymentProcessorTypeCodeTypeChoice, value);
            if(usingDefaultChoice || (defaultValue == null && paymentProcessorTypeCodeTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new PaymentProcessorTypeCodeTypeChoicesBean(labels, values, defaultValue);
    }

    private void updatePaymentProcessorTypeCodeTypeFromValue(final PaymentProcessorTypeCodeTypeDetailValue paymentProcessorTypeCodeTypeDetailValue,
            final boolean checkDefault, final BasePK updatedBy) {
        if(paymentProcessorTypeCodeTypeDetailValue.hasBeenModified()) {
            var paymentProcessorTypeCodeType = PaymentProcessorTypeCodeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    paymentProcessorTypeCodeTypeDetailValue.getPaymentProcessorTypeCodeTypePK());
            var paymentProcessorTypeCodeTypeDetail = paymentProcessorTypeCodeType.getActiveDetailForUpdate();

            paymentProcessorTypeCodeTypeDetail.setThruTime(session.getStartTime());
            paymentProcessorTypeCodeTypeDetail.store();

            var paymentProcessorTypeCodeTypePK = paymentProcessorTypeCodeTypeDetail.getPaymentProcessorTypeCodeTypePK(); // R/O
            var paymentProcessorType = paymentProcessorTypeCodeTypeDetail.getPaymentProcessorType();
            var paymentProcessorTypePK = paymentProcessorType.getPrimaryKey(); // R/O
            var paymentProcessorTypeCodeTypeName = paymentProcessorTypeCodeTypeDetailValue.getPaymentProcessorTypeCodeTypeName(); // R/W
            var isDefault = paymentProcessorTypeCodeTypeDetailValue.getIsDefault(); // R/W
            var sortOrder = paymentProcessorTypeCodeTypeDetailValue.getSortOrder(); // R/W

            if(checkDefault) {
                var defaultPaymentProcessorTypeCodeType = getDefaultPaymentProcessorTypeCodeType(paymentProcessorType);
                var defaultFound = defaultPaymentProcessorTypeCodeType != null && !defaultPaymentProcessorTypeCodeType.equals(paymentProcessorTypeCodeType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPaymentProcessorTypeCodeTypeDetailValue = getDefaultPaymentProcessorTypeCodeTypeDetailValueForUpdate(paymentProcessorType);

                    defaultPaymentProcessorTypeCodeTypeDetailValue.setIsDefault(false);
                    updatePaymentProcessorTypeCodeTypeFromValue(defaultPaymentProcessorTypeCodeTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            paymentProcessorTypeCodeTypeDetail = PaymentProcessorTypeCodeTypeDetailFactory.getInstance().create(paymentProcessorTypeCodeTypePK,
                    paymentProcessorTypePK, paymentProcessorTypeCodeTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME_LONG);

            paymentProcessorTypeCodeType.setActiveDetail(paymentProcessorTypeCodeTypeDetail);
            paymentProcessorTypeCodeType.setLastDetail(paymentProcessorTypeCodeTypeDetail);

            sendEvent(paymentProcessorTypeCodeTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updatePaymentProcessorTypeCodeTypeFromValue(final PaymentProcessorTypeCodeTypeDetailValue paymentProcessorTypeCodeTypeDetailValue,
            final BasePK updatedBy) {
        updatePaymentProcessorTypeCodeTypeFromValue(paymentProcessorTypeCodeTypeDetailValue, true, updatedBy);
    }

    public void deletePaymentProcessorTypeCodeType(final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType, final BasePK deletedBy) {
        var paymentProcessorTypeCodeControl = Session.getModelController(PaymentProcessorTypeCodeControl.class);

        paymentProcessorTypeCodeControl.deletePaymentProcessorTypeCodesByPaymentProcessorTypeCodeType(paymentProcessorTypeCodeType, deletedBy);
        deletePaymentProcessorTypeCodeTypeDescriptionsByPaymentProcessorTypeCodeType(paymentProcessorTypeCodeType, deletedBy);

        var paymentProcessorTypeCodeTypeDetail = paymentProcessorTypeCodeType.getLastDetailForUpdate();
        paymentProcessorTypeCodeTypeDetail.setThruTime(session.getStartTime());
        paymentProcessorTypeCodeTypeDetail.store();
        paymentProcessorTypeCodeType.setActiveDetail(null);

        // Check for default, and pick one if necessary
        var paymentProcessorType = paymentProcessorTypeCodeTypeDetail.getPaymentProcessorType();
        var defaultPaymentProcessorTypeCodeType = getDefaultPaymentProcessorTypeCodeType(paymentProcessorType);
        if(defaultPaymentProcessorTypeCodeType == null) {
            var paymentProcessorTypeCodeTypes = getPaymentProcessorTypeCodeTypesForUpdate(paymentProcessorType);

            if(!paymentProcessorTypeCodeTypes.isEmpty()) {
                var iter = paymentProcessorTypeCodeTypes.iterator();
                if(iter.hasNext()) {
                    defaultPaymentProcessorTypeCodeType = iter.next();
                }
                var paymentProcessorTypeCodeTypeDetailValue = Objects.requireNonNull(defaultPaymentProcessorTypeCodeType).getLastDetailForUpdate().getPaymentProcessorTypeCodeTypeDetailValue().clone();

                paymentProcessorTypeCodeTypeDetailValue.setIsDefault(true);
                updatePaymentProcessorTypeCodeTypeFromValue(paymentProcessorTypeCodeTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(paymentProcessorTypeCodeType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deletePaymentProcessorTypeCodeTypes(final Collection<PaymentProcessorTypeCodeType> paymentProcessorTypeCodeTypes, final BasePK deletedBy) {
        paymentProcessorTypeCodeTypes.forEach(paymentProcessorTypeCodeType -> deletePaymentProcessorTypeCodeType(paymentProcessorTypeCodeType, deletedBy));
    }

    public void deletePaymentProcessorTypeCodeTypesByPaymentProcessorType(final PaymentProcessorType paymentProcessorType, final BasePK deletedBy) {
        deletePaymentProcessorTypeCodeTypes(getPaymentProcessorTypeCodeTypesForUpdate(paymentProcessorType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Payment Processor Type Code Type Descriptions
    // --------------------------------------------------------------------------------

    public PaymentProcessorTypeCodeTypeDescription createPaymentProcessorTypeCodeTypeDescription(final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType,
            final Language language, final String description, final BasePK createdBy) {
        var paymentProcessorTypeCodeTypeDescription = PaymentProcessorTypeCodeTypeDescriptionFactory.getInstance().create(paymentProcessorTypeCodeType,
                language, description, session.getStartTime(), Session.MAX_TIME_LONG);

        sendEvent(paymentProcessorTypeCodeType.getPrimaryKey(), EventTypes.MODIFY, paymentProcessorTypeCodeTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return paymentProcessorTypeCodeTypeDescription;
    }

    private static final Map<EntityPermission, String> getPaymentProcessorTypeCodeTypeDescriptionQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypecodetypedescriptions " +
                    "WHERE pproctypctypd_pproctypctyp_paymentprocessortypecodetypeid = ? AND pproctypctypd_lang_languageid = ? AND pproctypctypd_thrutime = ?",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypecodetypedescriptions " +
                    "WHERE pproctypctypd_pproctypctyp_paymentprocessortypecodetypeid = ? AND pproctypctypd_lang_languageid = ? AND pproctypctypd_thrutime = ? " +
                    "FOR UPDATE");

    private PaymentProcessorTypeCodeTypeDescription getPaymentProcessorTypeCodeTypeDescription(final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType,
            final Language language, final EntityPermission entityPermission) {
        return PaymentProcessorTypeCodeTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getPaymentProcessorTypeCodeTypeDescriptionQueries,
                paymentProcessorTypeCodeType, language, Session.MAX_TIME);
    }

    public PaymentProcessorTypeCodeTypeDescription getPaymentProcessorTypeCodeTypeDescription(final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType,
            final Language language) {
        return getPaymentProcessorTypeCodeTypeDescription(paymentProcessorTypeCodeType, language, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorTypeCodeTypeDescription getPaymentProcessorTypeCodeTypeDescriptionForUpdate(final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType,
            final Language language) {
        return getPaymentProcessorTypeCodeTypeDescription(paymentProcessorTypeCodeType, language, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTypeCodeTypeDescriptionValue getPaymentProcessorTypeCodeTypeDescriptionValue(final PaymentProcessorTypeCodeTypeDescription paymentProcessorTypeCodeTypeDescription) {
        return paymentProcessorTypeCodeTypeDescription == null ? null : paymentProcessorTypeCodeTypeDescription.getPaymentProcessorTypeCodeTypeDescriptionValue().clone();
    }

    public PaymentProcessorTypeCodeTypeDescriptionValue getPaymentProcessorTypeCodeTypeDescriptionValueForUpdate(final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType,
            final Language language) {
        return getPaymentProcessorTypeCodeTypeDescriptionValue(getPaymentProcessorTypeCodeTypeDescriptionForUpdate(paymentProcessorTypeCodeType, language));
    }

    private static final Map<EntityPermission, String> getPaymentProcessorTypeCodeTypeDescriptionsByPaymentProcessorTypeCodeTypeQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypecodetypedescriptions, languages " +
                    "WHERE pproctypctypd_pproctypctyp_paymentprocessortypecodetypeid = ? AND pproctypctypd_thrutime = ? AND pproctypctypd_lang_languageid = lang_languageid " +
                    "ORDER BY lang_sortorder, lang_languageisoname",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypecodetypedescriptions " +
                    "WHERE pproctypctypd_pproctypctyp_paymentprocessortypecodetypeid = ? AND pproctypctypd_thrutime = ? " +
                    "FOR UPDATE");

    private List<PaymentProcessorTypeCodeTypeDescription> getPaymentProcessorTypeCodeTypeDescriptionsByPaymentProcessorTypeCodeType(final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType,
            final EntityPermission entityPermission) {
        return PaymentProcessorTypeCodeTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission,
                getPaymentProcessorTypeCodeTypeDescriptionsByPaymentProcessorTypeCodeTypeQueries,
                paymentProcessorTypeCodeType, Session.MAX_TIME);
    }

    public List<PaymentProcessorTypeCodeTypeDescription> getPaymentProcessorTypeCodeTypeDescriptionsByPaymentProcessorTypeCodeType(final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType) {
        return getPaymentProcessorTypeCodeTypeDescriptionsByPaymentProcessorTypeCodeType(paymentProcessorTypeCodeType, EntityPermission.READ_ONLY);
    }

    public List<PaymentProcessorTypeCodeTypeDescription> getPaymentProcessorTypeCodeTypeDescriptionsByPaymentProcessorTypeCodeTypeForUpdate(final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType) {
        return getPaymentProcessorTypeCodeTypeDescriptionsByPaymentProcessorTypeCodeType(paymentProcessorTypeCodeType, EntityPermission.READ_WRITE);
    }

    public String getBestPaymentProcessorTypeCodeTypeDescription(final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType, final Language language) {
        var paymentProcessorTypeCodeTypeDescription = getPaymentProcessorTypeCodeTypeDescription(paymentProcessorTypeCodeType, language);
        String description;

        if(paymentProcessorTypeCodeTypeDescription == null && !language.getIsDefault()) {
            paymentProcessorTypeCodeTypeDescription = getPaymentProcessorTypeCodeTypeDescription(paymentProcessorTypeCodeType, partyControl.getDefaultLanguage());
        }

        if(paymentProcessorTypeCodeTypeDescription == null) {
            description = paymentProcessorTypeCodeType.getLastDetail().getPaymentProcessorTypeCodeTypeName();
        } else {
            description = paymentProcessorTypeCodeTypeDescription.getDescription();
        }

        return description;
    }

    public PaymentProcessorTypeCodeTypeDescriptionTransfer getPaymentProcessorTypeCodeTypeDescriptionTransfer(final UserVisit userVisit,
            final PaymentProcessorTypeCodeTypeDescription paymentProcessorTypeCodeTypeDescription) {
        return paymentProcessorTypeCodeTypeDescriptionTransferCache.getTransfer(userVisit, paymentProcessorTypeCodeTypeDescription);
    }

    public List<PaymentProcessorTypeCodeTypeDescriptionTransfer> getPaymentProcessorTypeCodeTypeDescriptionTransfersByPaymentProcessorTypeCodeType(final UserVisit userVisit,
            final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType) {
        var paymentProcessorTypeCodeTypeDescriptions = getPaymentProcessorTypeCodeTypeDescriptionsByPaymentProcessorTypeCodeType(paymentProcessorTypeCodeType);
        var paymentProcessorTypeCodeTypeDescriptionTransfers = new ArrayList<PaymentProcessorTypeCodeTypeDescriptionTransfer>(paymentProcessorTypeCodeTypeDescriptions.size());

        paymentProcessorTypeCodeTypeDescriptions.forEach((paymentProcessorTypeCodeTypeDescription) ->
                paymentProcessorTypeCodeTypeDescriptionTransfers.add(paymentProcessorTypeCodeTypeDescriptionTransferCache.getTransfer(userVisit, paymentProcessorTypeCodeTypeDescription))
        );

        return paymentProcessorTypeCodeTypeDescriptionTransfers;
    }

    public void updatePaymentProcessorTypeCodeTypeDescriptionFromValue(final PaymentProcessorTypeCodeTypeDescriptionValue paymentProcessorTypeCodeTypeDescriptionValue,
            final BasePK updatedBy) {
        if(paymentProcessorTypeCodeTypeDescriptionValue.hasBeenModified()) {
            var paymentProcessorTypeCodeTypeDescription = PaymentProcessorTypeCodeTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, paymentProcessorTypeCodeTypeDescriptionValue.getPrimaryKey());

            paymentProcessorTypeCodeTypeDescription.setThruTime(session.getStartTime());
            paymentProcessorTypeCodeTypeDescription.store();

            var paymentProcessorTypeCodeType = paymentProcessorTypeCodeTypeDescription.getPaymentProcessorTypeCodeType();
            var language = paymentProcessorTypeCodeTypeDescription.getLanguage();
            var description = paymentProcessorTypeCodeTypeDescriptionValue.getDescription();

            paymentProcessorTypeCodeTypeDescription = PaymentProcessorTypeCodeTypeDescriptionFactory.getInstance().create(paymentProcessorTypeCodeType, language, description,
                    session.getStartTime(), Session.MAX_TIME_LONG);

            sendEvent(paymentProcessorTypeCodeType.getPrimaryKey(), EventTypes.MODIFY, paymentProcessorTypeCodeTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePaymentProcessorTypeCodeTypeDescription(final PaymentProcessorTypeCodeTypeDescription paymentProcessorTypeCodeTypeDescription, final BasePK deletedBy) {
        paymentProcessorTypeCodeTypeDescription.setThruTime(session.getStartTime());

        sendEvent(paymentProcessorTypeCodeTypeDescription.getPaymentProcessorTypeCodeTypePK(), EventTypes.MODIFY, paymentProcessorTypeCodeTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deletePaymentProcessorTypeCodeTypeDescriptionsByPaymentProcessorTypeCodeType(final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType, final BasePK deletedBy) {
        var paymentProcessorTypeCodeTypeDescriptions = getPaymentProcessorTypeCodeTypeDescriptionsByPaymentProcessorTypeCodeTypeForUpdate(paymentProcessorTypeCodeType);

        paymentProcessorTypeCodeTypeDescriptions.forEach((paymentProcessorTypeCodeTypeDescription) -> {
            deletePaymentProcessorTypeCodeTypeDescription(paymentProcessorTypeCodeTypeDescription, deletedBy);
        });
    }

}

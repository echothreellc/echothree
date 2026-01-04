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
import com.echothree.model.control.payment.common.choice.PaymentProcessorTypeCodeChoicesBean;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorTypeCodeDescriptionTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorTypeCodeTransfer;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.payment.common.pk.PaymentProcessorTypeCodePK;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeCode;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeCodeDescription;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeCodeType;
import com.echothree.model.data.payment.server.factory.PaymentProcessorTypeCodeDescriptionFactory;
import com.echothree.model.data.payment.server.factory.PaymentProcessorTypeCodeDetailFactory;
import com.echothree.model.data.payment.server.factory.PaymentProcessorTypeCodeFactory;
import com.echothree.model.data.payment.server.value.PaymentProcessorTypeCodeDescriptionValue;
import com.echothree.model.data.payment.server.value.PaymentProcessorTypeCodeDetailValue;
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
public class PaymentProcessorTypeCodeControl
        extends BasePaymentControl {

    /** Creates a new instance of PaymentProcessorTypeCodeControl */
    protected PaymentProcessorTypeCodeControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Payment Processor Type Code Types
    // --------------------------------------------------------------------------------

    public PaymentProcessorTypeCode createPaymentProcessorTypeCode(final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType,
            final String paymentProcessorTypeCodeName, Boolean isDefault, final Integer sortOrder, final BasePK createdBy) {
        var defaultPaymentProcessorTypeCode = getDefaultPaymentProcessorTypeCode(paymentProcessorTypeCodeType);
        var defaultFound = defaultPaymentProcessorTypeCode != null;

        if(defaultFound && isDefault) {
            var defaultPaymentProcessorTypeCodeDetailValue = getDefaultPaymentProcessorTypeCodeDetailValueForUpdate(paymentProcessorTypeCodeType);

            defaultPaymentProcessorTypeCodeDetailValue.setIsDefault(false);
            updatePaymentProcessorTypeCodeFromValue(defaultPaymentProcessorTypeCodeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var paymentProcessorTypeCode = PaymentProcessorTypeCodeFactory.getInstance().create();
        var paymentProcessorTypeCodeDetail = PaymentProcessorTypeCodeDetailFactory.getInstance().create(
                paymentProcessorTypeCode, paymentProcessorTypeCodeType, paymentProcessorTypeCodeName, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        paymentProcessorTypeCode = PaymentProcessorTypeCodeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, paymentProcessorTypeCode.getPrimaryKey());
        paymentProcessorTypeCode.setActiveDetail(paymentProcessorTypeCodeDetail);
        paymentProcessorTypeCode.setLastDetail(paymentProcessorTypeCodeDetail);
        paymentProcessorTypeCode.store();

        sendEvent(paymentProcessorTypeCode.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return paymentProcessorTypeCode;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PaymentProcessorTypeCode */
    public PaymentProcessorTypeCode getPaymentProcessorTypeCodeByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new PaymentProcessorTypeCodePK(entityInstance.getEntityUniqueId());

        return PaymentProcessorTypeCodeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public PaymentProcessorTypeCode getPaymentProcessorTypeCodeByEntityInstance(final EntityInstance entityInstance) {
        return getPaymentProcessorTypeCodeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorTypeCode getPaymentProcessorTypeCodeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getPaymentProcessorTypeCodeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPaymentProcessorTypeCodeByNameQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypecodes, paymentprocessortypecodedetails " +
                    "WHERE pproctypc_activedetailid = pproctypcdt_paymentprocessortypecodedetailid " +
                    "AND pproctypcdt_pproctypctyp_paymentprocessortypecodetypeid = ? AND pproctypcdt_paymentprocessortypecodename = ?",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " + "FROM paymentprocessortypecodes, paymentprocessortypecodedetails " +
                    "WHERE pproctypc_activedetailid = pproctypcdt_paymentprocessortypecodedetailid " +
                    "AND pproctypcdt_pproctypctyp_paymentprocessortypecodetypeid = ? AND pproctypcdt_paymentprocessortypecodename = ? " +
                    "FOR UPDATE");

    public PaymentProcessorTypeCode getPaymentProcessorTypeCodeByName(final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType,
            final String paymentProcessorTypeCodeName, final EntityPermission entityPermission) {
        return PaymentProcessorTypeCodeFactory.getInstance().getEntityFromQuery(entityPermission, getPaymentProcessorTypeCodeByNameQueries,
                paymentProcessorTypeCodeType, paymentProcessorTypeCodeName);
    }

    public PaymentProcessorTypeCode getPaymentProcessorTypeCodeByName(final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType,
            final String paymentProcessorTypeCodeName) {
        return getPaymentProcessorTypeCodeByName(paymentProcessorTypeCodeType, paymentProcessorTypeCodeName, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorTypeCode getPaymentProcessorTypeCodeByNameForUpdate(final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType,
            final String paymentProcessorTypeCodeName) {
        return getPaymentProcessorTypeCodeByName(paymentProcessorTypeCodeType, paymentProcessorTypeCodeName, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTypeCodeDetailValue getPaymentProcessorTypeCodeDetailValueForUpdate(final PaymentProcessorTypeCode paymentProcessorTypeCode) {
        return paymentProcessorTypeCode == null ? null : paymentProcessorTypeCode.getLastDetailForUpdate().getPaymentProcessorTypeCodeDetailValue().clone();
    }

    public PaymentProcessorTypeCodeDetailValue getPaymentProcessorTypeCodeDetailValueByNameForUpdate(final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType,
            final String paymentProcessorTypeCodeName) {
        return getPaymentProcessorTypeCodeDetailValueForUpdate(getPaymentProcessorTypeCodeByNameForUpdate(paymentProcessorTypeCodeType, paymentProcessorTypeCodeName));
    }

    private static final Map<EntityPermission, String> getDefaultPaymentProcessorTypeCodeQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypecodes, paymentprocessortypecodedetails " +
                    "WHERE pproctypc_activedetailid = pproctypcdt_paymentprocessortypecodedetailid " +
                    "AND pproctypcdt_pproctypctyp_paymentprocessortypecodetypeid = ? AND pproctypcdt_isdefault = 1",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypecodes, paymentprocessortypecodedetails " +
                    "WHERE pproctypc_activedetailid = pproctypcdt_paymentprocessortypecodedetailid " +
                    "AND pproctypcdt_pproctypctyp_paymentprocessortypecodetypeid = ? AND pproctypcdt_isdefault = 1 " +
                    "FOR UPDATE");

    public PaymentProcessorTypeCode getDefaultPaymentProcessorTypeCode(final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType, final EntityPermission entityPermission) {
        return PaymentProcessorTypeCodeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultPaymentProcessorTypeCodeQueries,
                paymentProcessorTypeCodeType);
    }

    public PaymentProcessorTypeCode getDefaultPaymentProcessorTypeCode(final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType) {
        return getDefaultPaymentProcessorTypeCode(paymentProcessorTypeCodeType, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorTypeCode getDefaultPaymentProcessorTypeCodeForUpdate(final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType) {
        return getDefaultPaymentProcessorTypeCode(paymentProcessorTypeCodeType, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTypeCodeDetailValue getDefaultPaymentProcessorTypeCodeDetailValueForUpdate(final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType) {
        return getDefaultPaymentProcessorTypeCodeForUpdate(paymentProcessorTypeCodeType).getLastDetailForUpdate().getPaymentProcessorTypeCodeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getPaymentProcessorTypeCodesQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypecodes, paymentprocessortypecodedetails " +
                    "WHERE pproctypc_activedetailid = pproctypcdt_paymentprocessortypecodedetailid AND pproctypcdt_pproctypctyp_paymentprocessortypecodetypeid = ? " +
                    "ORDER BY pproctypcdt_sortorder, pproctypcdt_paymentprocessortypecodename",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypecodes, paymentprocessortypecodedetails " +
                    "WHERE pproctypc_activedetailid = pproctypcdt_paymentprocessortypecodedetailid AND pproctypcdt_pproctypctyp_paymentprocessortypecodetypeid = ? " +
                    "FOR UPDATE");

    private List<PaymentProcessorTypeCode> getPaymentProcessorTypeCodes(final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType, final EntityPermission entityPermission) {
        return PaymentProcessorTypeCodeFactory.getInstance().getEntitiesFromQuery(entityPermission, getPaymentProcessorTypeCodesQueries,
                paymentProcessorTypeCodeType);
    }

    public List<PaymentProcessorTypeCode> getPaymentProcessorTypeCodes(final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType) {
        return getPaymentProcessorTypeCodes(paymentProcessorTypeCodeType, EntityPermission.READ_ONLY);
    }

    public List<PaymentProcessorTypeCode> getPaymentProcessorTypeCodesForUpdate(final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType) {
        return getPaymentProcessorTypeCodes(paymentProcessorTypeCodeType, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTypeCodeTransfer getPaymentProcessorTypeCodeTransfer(final UserVisit userVisit,
            final PaymentProcessorTypeCode paymentProcessorTypeCode) {
        return paymentProcessorTypeCodeTransferCache.getTransfer(userVisit, paymentProcessorTypeCode);
    }

    public List<PaymentProcessorTypeCodeTransfer> getPaymentProcessorTypeCodeTransfers(final UserVisit userVisit,
            final Collection<PaymentProcessorTypeCode> paymentProcessorTypeCodes) {
        var paymentProcessorTypeCodeTransfers = new ArrayList<PaymentProcessorTypeCodeTransfer>(paymentProcessorTypeCodes.size());

        paymentProcessorTypeCodes.forEach((paymentProcessorTypeCode) ->
                paymentProcessorTypeCodeTransfers.add(paymentProcessorTypeCodeTransferCache.getTransfer(userVisit, paymentProcessorTypeCode))
        );

        return paymentProcessorTypeCodeTransfers;
    }

    public List<PaymentProcessorTypeCodeTransfer> getPaymentProcessorTypeCodeTransfers(final UserVisit userVisit, final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType) {
        return getPaymentProcessorTypeCodeTransfers(userVisit, getPaymentProcessorTypeCodes(paymentProcessorTypeCodeType));
    }

    public PaymentProcessorTypeCodeChoicesBean getPaymentProcessorTypeCodeChoices(final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType,
            final String defaultPaymentProcessorTypeCodeChoice, final Language language, final boolean allowNullChoice) {
        var paymentProcessorTypeCodes = getPaymentProcessorTypeCodes(paymentProcessorTypeCodeType);
        var size = paymentProcessorTypeCodes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultPaymentProcessorTypeCodeChoice == null) {
                defaultValue = "";
            }
        }

        for(var paymentProcessorTypeCode : paymentProcessorTypeCodes) {
            var paymentProcessorTypeCodeDetail = paymentProcessorTypeCode.getLastDetail();

            var label = getBestPaymentProcessorTypeCodeDescription(paymentProcessorTypeCode, language);
            var value = paymentProcessorTypeCodeDetail.getPaymentProcessorTypeCodeName();

            labels.add(label == null ? value : label);
            values.add(value);

            var usingDefaultChoice = Objects.equals(defaultPaymentProcessorTypeCodeChoice, value);
            if(usingDefaultChoice || (defaultValue == null && paymentProcessorTypeCodeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new PaymentProcessorTypeCodeChoicesBean(labels, values, defaultValue);
    }

    private void updatePaymentProcessorTypeCodeFromValue(final PaymentProcessorTypeCodeDetailValue paymentProcessorTypeCodeDetailValue,
            final boolean checkDefault, final BasePK updatedBy) {
        if(paymentProcessorTypeCodeDetailValue.hasBeenModified()) {
            var paymentProcessorTypeCode = PaymentProcessorTypeCodeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    paymentProcessorTypeCodeDetailValue.getPaymentProcessorTypeCodePK());
            var paymentProcessorTypeCodeDetail = paymentProcessorTypeCode.getActiveDetailForUpdate();

            paymentProcessorTypeCodeDetail.setThruTime(session.getStartTime());
            paymentProcessorTypeCodeDetail.store();

            var paymentProcessorTypeCodePK = paymentProcessorTypeCodeDetail.getPaymentProcessorTypeCodePK(); // R/O
            var paymentProcessorTypeCodeType = paymentProcessorTypeCodeDetail.getPaymentProcessorTypeCodeType();
            var paymentProcessorTypeCodeTypePK = paymentProcessorTypeCodeType.getPrimaryKey(); // R/O
            var paymentProcessorTypeCodeName = paymentProcessorTypeCodeDetailValue.getPaymentProcessorTypeCodeName(); // R/W
            var isDefault = paymentProcessorTypeCodeDetailValue.getIsDefault(); // R/W
            var sortOrder = paymentProcessorTypeCodeDetailValue.getSortOrder(); // R/W

            if(checkDefault) {
                var defaultPaymentProcessorTypeCode = getDefaultPaymentProcessorTypeCode(paymentProcessorTypeCodeType);
                var defaultFound = defaultPaymentProcessorTypeCode != null && !defaultPaymentProcessorTypeCode.equals(paymentProcessorTypeCode);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPaymentProcessorTypeCodeDetailValue = getDefaultPaymentProcessorTypeCodeDetailValueForUpdate(paymentProcessorTypeCodeType);

                    defaultPaymentProcessorTypeCodeDetailValue.setIsDefault(false);
                    updatePaymentProcessorTypeCodeFromValue(defaultPaymentProcessorTypeCodeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            paymentProcessorTypeCodeDetail = PaymentProcessorTypeCodeDetailFactory.getInstance().create(paymentProcessorTypeCodePK,
                    paymentProcessorTypeCodeTypePK, paymentProcessorTypeCodeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            paymentProcessorTypeCode.setActiveDetail(paymentProcessorTypeCodeDetail);
            paymentProcessorTypeCode.setLastDetail(paymentProcessorTypeCodeDetail);

            sendEvent(paymentProcessorTypeCodePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updatePaymentProcessorTypeCodeFromValue(final PaymentProcessorTypeCodeDetailValue paymentProcessorTypeCodeDetailValue,
            final BasePK updatedBy) {
        updatePaymentProcessorTypeCodeFromValue(paymentProcessorTypeCodeDetailValue, true, updatedBy);
    }

    public void deletePaymentProcessorTypeCode(final PaymentProcessorTypeCode paymentProcessorTypeCode, final BasePK deletedBy) {
        var paymentProcessorTransactionCodeControl = Session.getModelController(PaymentProcessorTransactionCodeControl.class);

        paymentProcessorTransactionCodeControl.deletePaymentProcessorTransactionCodesByPaymentProcessorTypeCode(paymentProcessorTypeCode, deletedBy);
        deletePaymentProcessorTypeCodeDescriptionsByPaymentProcessorTypeCode(paymentProcessorTypeCode, deletedBy);

        var paymentProcessorTypeCodeDetail = paymentProcessorTypeCode.getLastDetailForUpdate();
        paymentProcessorTypeCodeDetail.setThruTime(session.getStartTime());
        paymentProcessorTypeCodeDetail.store();
        paymentProcessorTypeCode.setActiveDetail(null);

        // Check for default, and pick one if necessary
        var paymentProcessorTypeCodeType = paymentProcessorTypeCodeDetail.getPaymentProcessorTypeCodeType();
        var defaultPaymentProcessorTypeCode = getDefaultPaymentProcessorTypeCode(paymentProcessorTypeCodeType);
        if(defaultPaymentProcessorTypeCode == null) {
            var paymentProcessorTypeCodes = getPaymentProcessorTypeCodesForUpdate(paymentProcessorTypeCodeType);

            if(!paymentProcessorTypeCodes.isEmpty()) {
                var iter = paymentProcessorTypeCodes.iterator();
                if(iter.hasNext()) {
                    defaultPaymentProcessorTypeCode = iter.next();
                }
                var paymentProcessorTypeCodeDetailValue = Objects.requireNonNull(defaultPaymentProcessorTypeCode).getLastDetailForUpdate().getPaymentProcessorTypeCodeDetailValue().clone();

                paymentProcessorTypeCodeDetailValue.setIsDefault(true);
                updatePaymentProcessorTypeCodeFromValue(paymentProcessorTypeCodeDetailValue, false, deletedBy);
            }
        }

        sendEvent(paymentProcessorTypeCode.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deletePaymentProcessorTypeCodes(final Collection<PaymentProcessorTypeCode> paymentProcessorTypeCodes, final BasePK deletedBy) {
        paymentProcessorTypeCodes.forEach(paymentProcessorTypeCode -> deletePaymentProcessorTypeCode(paymentProcessorTypeCode, deletedBy));
    }

    public void deletePaymentProcessorTypeCodesByPaymentProcessorTypeCodeType(final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType, final BasePK deletedBy) {
        deletePaymentProcessorTypeCodes(getPaymentProcessorTypeCodesForUpdate(paymentProcessorTypeCodeType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Payment Processor Type Code Type Descriptions
    // --------------------------------------------------------------------------------

    public PaymentProcessorTypeCodeDescription createPaymentProcessorTypeCodeDescription(final PaymentProcessorTypeCode paymentProcessorTypeCode,
            final Language language, final String description, final BasePK createdBy) {
        var paymentProcessorTypeCodeDescription = PaymentProcessorTypeCodeDescriptionFactory.getInstance().create(paymentProcessorTypeCode,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(paymentProcessorTypeCode.getPrimaryKey(), EventTypes.MODIFY, paymentProcessorTypeCodeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return paymentProcessorTypeCodeDescription;
    }

    private static final Map<EntityPermission, String> getPaymentProcessorTypeCodeDescriptionQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypecodedescriptions " +
                    "WHERE pproctypcd_pproctypc_paymentprocessortypecodeid = ? AND pproctypcd_lang_languageid = ? AND pproctypcd_thrutime = ?",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypecodedescriptions " +
                    "WHERE pproctypcd_pproctypc_paymentprocessortypecodeid = ? AND pproctypcd_lang_languageid = ? AND pproctypcd_thrutime = ? " +
                    "FOR UPDATE");

    private PaymentProcessorTypeCodeDescription getPaymentProcessorTypeCodeDescription(final PaymentProcessorTypeCode paymentProcessorTypeCode,
            final Language language, final EntityPermission entityPermission) {
        return PaymentProcessorTypeCodeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getPaymentProcessorTypeCodeDescriptionQueries,
                paymentProcessorTypeCode, language, Session.MAX_TIME);
    }

    public PaymentProcessorTypeCodeDescription getPaymentProcessorTypeCodeDescription(final PaymentProcessorTypeCode paymentProcessorTypeCode,
            final Language language) {
        return getPaymentProcessorTypeCodeDescription(paymentProcessorTypeCode, language, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorTypeCodeDescription getPaymentProcessorTypeCodeDescriptionForUpdate(final PaymentProcessorTypeCode paymentProcessorTypeCode,
            final Language language) {
        return getPaymentProcessorTypeCodeDescription(paymentProcessorTypeCode, language, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTypeCodeDescriptionValue getPaymentProcessorTypeCodeDescriptionValue(final PaymentProcessorTypeCodeDescription paymentProcessorTypeCodeDescription) {
        return paymentProcessorTypeCodeDescription == null ? null : paymentProcessorTypeCodeDescription.getPaymentProcessorTypeCodeDescriptionValue().clone();
    }

    public PaymentProcessorTypeCodeDescriptionValue getPaymentProcessorTypeCodeDescriptionValueForUpdate(final PaymentProcessorTypeCode paymentProcessorTypeCode,
            final Language language) {
        return getPaymentProcessorTypeCodeDescriptionValue(getPaymentProcessorTypeCodeDescriptionForUpdate(paymentProcessorTypeCode, language));
    }

    private static final Map<EntityPermission, String> getPaymentProcessorTypeCodeDescriptionsByPaymentProcessorTypeCodeQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypecodedescriptions, languages " +
                    "WHERE pproctypcd_pproctypc_paymentprocessortypecodeid = ? AND pproctypcd_thrutime = ? AND pproctypcd_lang_languageid = lang_languageid " +
                    "ORDER BY lang_sortorder, lang_languageisoname",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypecodedescriptions " +
                    "WHERE pproctypcd_pproctypc_paymentprocessortypecodeid = ? AND pproctypcd_thrutime = ? " +
                    "FOR UPDATE");

    private List<PaymentProcessorTypeCodeDescription> getPaymentProcessorTypeCodeDescriptionsByPaymentProcessorTypeCode(final PaymentProcessorTypeCode paymentProcessorTypeCode,
            final EntityPermission entityPermission) {
        return PaymentProcessorTypeCodeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission,
                getPaymentProcessorTypeCodeDescriptionsByPaymentProcessorTypeCodeQueries,
                paymentProcessorTypeCode, Session.MAX_TIME);
    }

    public List<PaymentProcessorTypeCodeDescription> getPaymentProcessorTypeCodeDescriptionsByPaymentProcessorTypeCode(final PaymentProcessorTypeCode paymentProcessorTypeCode) {
        return getPaymentProcessorTypeCodeDescriptionsByPaymentProcessorTypeCode(paymentProcessorTypeCode, EntityPermission.READ_ONLY);
    }

    public List<PaymentProcessorTypeCodeDescription> getPaymentProcessorTypeCodeDescriptionsByPaymentProcessorTypeCodeForUpdate(final PaymentProcessorTypeCode paymentProcessorTypeCode) {
        return getPaymentProcessorTypeCodeDescriptionsByPaymentProcessorTypeCode(paymentProcessorTypeCode, EntityPermission.READ_WRITE);
    }

    public String getBestPaymentProcessorTypeCodeDescription(final PaymentProcessorTypeCode paymentProcessorTypeCode, final Language language) {
        var paymentProcessorTypeCodeDescription = getPaymentProcessorTypeCodeDescription(paymentProcessorTypeCode, language);
        String description;

        if(paymentProcessorTypeCodeDescription == null && !language.getIsDefault()) {
            paymentProcessorTypeCodeDescription = getPaymentProcessorTypeCodeDescription(paymentProcessorTypeCode, partyControl.getDefaultLanguage());
        }

        if(paymentProcessorTypeCodeDescription == null) {
            description = paymentProcessorTypeCode.getLastDetail().getPaymentProcessorTypeCodeName();
        } else {
            description = paymentProcessorTypeCodeDescription.getDescription();
        }

        return description;
    }

    public PaymentProcessorTypeCodeDescriptionTransfer getPaymentProcessorTypeCodeDescriptionTransfer(final UserVisit userVisit,
            final PaymentProcessorTypeCodeDescription paymentProcessorTypeCodeDescription) {
        return paymentProcessorTypeCodeDescriptionTransferCache.getTransfer(userVisit, paymentProcessorTypeCodeDescription);
    }

    public List<PaymentProcessorTypeCodeDescriptionTransfer> getPaymentProcessorTypeCodeDescriptionTransfersByPaymentProcessorTypeCode(final UserVisit userVisit,
            final PaymentProcessorTypeCode paymentProcessorTypeCode) {
        var paymentProcessorTypeCodeDescriptions = getPaymentProcessorTypeCodeDescriptionsByPaymentProcessorTypeCode(paymentProcessorTypeCode);
        var paymentProcessorTypeCodeDescriptionTransfers = new ArrayList<PaymentProcessorTypeCodeDescriptionTransfer>(paymentProcessorTypeCodeDescriptions.size());

        paymentProcessorTypeCodeDescriptions.forEach((paymentProcessorTypeCodeDescription) ->
                paymentProcessorTypeCodeDescriptionTransfers.add(paymentProcessorTypeCodeDescriptionTransferCache.getTransfer(userVisit, paymentProcessorTypeCodeDescription))
        );

        return paymentProcessorTypeCodeDescriptionTransfers;
    }

    public void updatePaymentProcessorTypeCodeDescriptionFromValue(final PaymentProcessorTypeCodeDescriptionValue paymentProcessorTypeCodeDescriptionValue,
            final BasePK updatedBy) {
        if(paymentProcessorTypeCodeDescriptionValue.hasBeenModified()) {
            var paymentProcessorTypeCodeDescription = PaymentProcessorTypeCodeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, paymentProcessorTypeCodeDescriptionValue.getPrimaryKey());

            paymentProcessorTypeCodeDescription.setThruTime(session.getStartTime());
            paymentProcessorTypeCodeDescription.store();

            var paymentProcessorTypeCode = paymentProcessorTypeCodeDescription.getPaymentProcessorTypeCode();
            var language = paymentProcessorTypeCodeDescription.getLanguage();
            var description = paymentProcessorTypeCodeDescriptionValue.getDescription();

            paymentProcessorTypeCodeDescription = PaymentProcessorTypeCodeDescriptionFactory.getInstance().create(paymentProcessorTypeCode, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(paymentProcessorTypeCode.getPrimaryKey(), EventTypes.MODIFY, paymentProcessorTypeCodeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePaymentProcessorTypeCodeDescription(final PaymentProcessorTypeCodeDescription paymentProcessorTypeCodeDescription, final BasePK deletedBy) {
        paymentProcessorTypeCodeDescription.setThruTime(session.getStartTime());

        sendEvent(paymentProcessorTypeCodeDescription.getPaymentProcessorTypeCodePK(), EventTypes.MODIFY, paymentProcessorTypeCodeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deletePaymentProcessorTypeCodeDescriptionsByPaymentProcessorTypeCode(final PaymentProcessorTypeCode paymentProcessorTypeCode, final BasePK deletedBy) {
        var paymentProcessorTypeCodeDescriptions = getPaymentProcessorTypeCodeDescriptionsByPaymentProcessorTypeCodeForUpdate(paymentProcessorTypeCode);

        paymentProcessorTypeCodeDescriptions.forEach((paymentProcessorTypeCodeDescription) -> {
            deletePaymentProcessorTypeCodeDescription(paymentProcessorTypeCodeDescription, deletedBy);
        });
    }

}

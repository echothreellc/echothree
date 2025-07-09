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
import com.echothree.model.control.payment.common.choice.PaymentProcessorResultCodeChoicesBean;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorResultCodeDescriptionTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorResultCodeTransfer;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.payment.common.pk.PaymentProcessorResultCodePK;
import com.echothree.model.data.payment.server.entity.PaymentProcessorResultCode;
import com.echothree.model.data.payment.server.entity.PaymentProcessorResultCodeDescription;
import com.echothree.model.data.payment.server.factory.PaymentProcessorResultCodeDescriptionFactory;
import com.echothree.model.data.payment.server.factory.PaymentProcessorResultCodeDetailFactory;
import com.echothree.model.data.payment.server.factory.PaymentProcessorResultCodeFactory;
import com.echothree.model.data.payment.server.value.PaymentProcessorResultCodeDescriptionValue;
import com.echothree.model.data.payment.server.value.PaymentProcessorResultCodeDetailValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PaymentProcessorResultCodeControl
        extends BasePaymentControl {

    /** Creates a new instance of PaymentProcessorResultCodeControl */
    public PaymentProcessorResultCodeControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Payment Processor Result Codes
    // --------------------------------------------------------------------------------

    public PaymentProcessorResultCode createPaymentProcessorResultCode(final String paymentProcessorResultCodeName, Boolean isDefault,
            final Integer sortOrder, final BasePK createdBy) {
        var defaultPaymentProcessorResultCode = getDefaultPaymentProcessorResultCode();
        var defaultFound = defaultPaymentProcessorResultCode != null;

        if(defaultFound && isDefault) {
            var defaultPaymentProcessorResultCodeDetailValue = getDefaultPaymentProcessorResultCodeDetailValueForUpdate();

            defaultPaymentProcessorResultCodeDetailValue.setIsDefault(false);
            updatePaymentProcessorResultCodeFromValue(defaultPaymentProcessorResultCodeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var paymentProcessorResultCode = PaymentProcessorResultCodeFactory.getInstance().create();
        var paymentProcessorResultCodeDetail = PaymentProcessorResultCodeDetailFactory.getInstance().create(session,
                paymentProcessorResultCode, paymentProcessorResultCodeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        paymentProcessorResultCode = PaymentProcessorResultCodeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, paymentProcessorResultCode.getPrimaryKey());
        paymentProcessorResultCode.setActiveDetail(paymentProcessorResultCodeDetail);
        paymentProcessorResultCode.setLastDetail(paymentProcessorResultCodeDetail);
        paymentProcessorResultCode.store();

        sendEvent(paymentProcessorResultCode.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return paymentProcessorResultCode;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PaymentProcessorResultCode */
    public PaymentProcessorResultCode getPaymentProcessorResultCodeByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new PaymentProcessorResultCodePK(entityInstance.getEntityUniqueId());

        return PaymentProcessorResultCodeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public PaymentProcessorResultCode getPaymentProcessorResultCodeByEntityInstance(final EntityInstance entityInstance) {
        return getPaymentProcessorResultCodeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorResultCode getPaymentProcessorResultCodeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getPaymentProcessorResultCodeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPaymentProcessorResultCodeByNameQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessorresultcodes, paymentprocessorresultcodedetails " +
                    "WHERE pprcrc_activedetailid = pprcrcdt_paymentprocessorresultcodedetailid AND pprcrcdt_paymentprocessorresultcodename = ?",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " + "FROM paymentprocessorresultcodes, paymentprocessorresultcodedetails " +
                    "WHERE pprcrc_activedetailid = pprcrcdt_paymentprocessorresultcodedetailid AND pprcrcdt_paymentprocessorresultcodename = ? " +
                    "FOR UPDATE");

    public PaymentProcessorResultCode getPaymentProcessorResultCodeByName(final String paymentProcessorResultCodeName, final EntityPermission entityPermission) {
        return PaymentProcessorResultCodeFactory.getInstance().getEntityFromQuery(entityPermission, getPaymentProcessorResultCodeByNameQueries,
                paymentProcessorResultCodeName);
    }

    public PaymentProcessorResultCode getPaymentProcessorResultCodeByName(final String paymentProcessorResultCodeName) {
        return getPaymentProcessorResultCodeByName(paymentProcessorResultCodeName, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorResultCode getPaymentProcessorResultCodeByNameForUpdate(final String paymentProcessorResultCodeName) {
        return getPaymentProcessorResultCodeByName(paymentProcessorResultCodeName, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorResultCodeDetailValue getPaymentProcessorResultCodeDetailValueForUpdate(final PaymentProcessorResultCode paymentProcessorResultCode) {
        return paymentProcessorResultCode == null ? null: paymentProcessorResultCode.getLastDetailForUpdate().getPaymentProcessorResultCodeDetailValue().clone();
    }

    public PaymentProcessorResultCodeDetailValue getPaymentProcessorResultCodeDetailValueByNameForUpdate(final String paymentProcessorResultCodeName) {
        return getPaymentProcessorResultCodeDetailValueForUpdate(getPaymentProcessorResultCodeByNameForUpdate(paymentProcessorResultCodeName));
    }

    private static final Map<EntityPermission, String> getDefaultPaymentProcessorResultCodeQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessorresultcodes, paymentprocessorresultcodedetails " +
                    "WHERE pprcrc_activedetailid = pprcrcdt_paymentprocessorresultcodedetailid AND pprcrcdt_isdefault = 1",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentprocessorresultcodes, paymentprocessorresultcodedetails " +
                    "WHERE pprcrc_activedetailid = pprcrcdt_paymentprocessorresultcodedetailid AND pprcrcdt_isdefault = 1 " +
                    "FOR UPDATE");

    public PaymentProcessorResultCode getDefaultPaymentProcessorResultCode(final EntityPermission entityPermission) {
        return PaymentProcessorResultCodeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultPaymentProcessorResultCodeQueries);
    }

    public PaymentProcessorResultCode getDefaultPaymentProcessorResultCode() {
        return getDefaultPaymentProcessorResultCode(EntityPermission.READ_ONLY);
    }

    public PaymentProcessorResultCode getDefaultPaymentProcessorResultCodeForUpdate() {
        return getDefaultPaymentProcessorResultCode(EntityPermission.READ_WRITE);
    }

    public PaymentProcessorResultCodeDetailValue getDefaultPaymentProcessorResultCodeDetailValueForUpdate() {
        return getDefaultPaymentProcessorResultCodeForUpdate().getLastDetailForUpdate().getPaymentProcessorResultCodeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getPaymentProcessorResultCodesQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " + "FROM paymentprocessorresultcodes, paymentprocessorresultcodedetails " +
                    "WHERE pprcrc_activedetailid = pprcrcdt_paymentprocessorresultcodedetailid " +
                    "ORDER BY pprcrcdt_sortorder, pprcrcdt_paymentprocessorresultcodename",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " + "FROM paymentprocessorresultcodes, paymentprocessorresultcodedetails " +
                    "WHERE pprcrc_activedetailid = pprcrcdt_paymentprocessorresultcodedetailid " +
                    "FOR UPDATE");

    private List<PaymentProcessorResultCode> getPaymentProcessorResultCodes(final EntityPermission entityPermission) {
        return PaymentProcessorResultCodeFactory.getInstance().getEntitiesFromQuery(entityPermission, getPaymentProcessorResultCodesQueries);
    }

    public List<PaymentProcessorResultCode> getPaymentProcessorResultCodes() {
        return getPaymentProcessorResultCodes(EntityPermission.READ_ONLY);
    }

    public List<PaymentProcessorResultCode> getPaymentProcessorResultCodesForUpdate() {
        return getPaymentProcessorResultCodes(EntityPermission.READ_WRITE);
    }

    public PaymentProcessorResultCodeTransfer getPaymentProcessorResultCodeTransfer(final UserVisit userVisit,
            final PaymentProcessorResultCode paymentProcessorResultCode) {
        return getPaymentTransferCaches(userVisit).getPaymentProcessorResultCodeTransferCache().getTransfer(paymentProcessorResultCode);
    }

    public List<PaymentProcessorResultCodeTransfer> getPaymentProcessorResultCodeTransfers(final UserVisit userVisit,
            final Collection<PaymentProcessorResultCode> paymentProcessorResultCodes) {
        var paymentProcessorResultCodeTransfers = new ArrayList<PaymentProcessorResultCodeTransfer>(paymentProcessorResultCodes.size());
        var paymentProcessorResultCodeTransferCache = getPaymentTransferCaches(userVisit).getPaymentProcessorResultCodeTransferCache();

        paymentProcessorResultCodes.forEach((paymentProcessorResultCode) ->
                paymentProcessorResultCodeTransfers.add(paymentProcessorResultCodeTransferCache.getTransfer(paymentProcessorResultCode))
        );

        return paymentProcessorResultCodeTransfers;
    }

    public List<PaymentProcessorResultCodeTransfer> getPaymentProcessorResultCodeTransfers(final UserVisit userVisit) {
        return getPaymentProcessorResultCodeTransfers(userVisit, getPaymentProcessorResultCodes());
    }

    public PaymentProcessorResultCodeChoicesBean getPaymentProcessorResultCodeChoices(final String defaultPaymentProcessorResultCodeChoice,
            final Language language, final boolean allowNullChoice) {
        var paymentProcessorResultCodes = getPaymentProcessorResultCodes();
        var size = paymentProcessorResultCodes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultPaymentProcessorResultCodeChoice == null) {
                defaultValue = "";
            }
        }

        for(var paymentProcessorResultCode : paymentProcessorResultCodes) {
            var paymentProcessorResultCodeDetail = paymentProcessorResultCode.getLastDetail();

            var label = getBestPaymentProcessorResultCodeDescription(paymentProcessorResultCode, language);
            var value = paymentProcessorResultCodeDetail.getPaymentProcessorResultCodeName();

            labels.add(label == null ? value : label);
            values.add(value);

            var usingDefaultChoice = Objects.equals(defaultPaymentProcessorResultCodeChoice, value);
            if(usingDefaultChoice || (defaultValue == null && paymentProcessorResultCodeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new PaymentProcessorResultCodeChoicesBean(labels, values, defaultValue);
    }

    private void updatePaymentProcessorResultCodeFromValue(final PaymentProcessorResultCodeDetailValue paymentProcessorResultCodeDetailValue,
            final boolean checkDefault, final BasePK updatedBy) {
        if(paymentProcessorResultCodeDetailValue.hasBeenModified()) {
            var paymentProcessorResultCode = PaymentProcessorResultCodeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    paymentProcessorResultCodeDetailValue.getPaymentProcessorResultCodePK());
            var paymentProcessorResultCodeDetail = paymentProcessorResultCode.getActiveDetailForUpdate();

            paymentProcessorResultCodeDetail.setThruTime(session.START_TIME_LONG);
            paymentProcessorResultCodeDetail.store();

            var paymentProcessorResultCodePK = paymentProcessorResultCodeDetail.getPaymentProcessorResultCodePK();
            var paymentProcessorResultCodeName = paymentProcessorResultCodeDetailValue.getPaymentProcessorResultCodeName();
            var isDefault = paymentProcessorResultCodeDetailValue.getIsDefault();
            var sortOrder = paymentProcessorResultCodeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultPaymentProcessorResultCode = getDefaultPaymentProcessorResultCode();
                var defaultFound = defaultPaymentProcessorResultCode != null && !defaultPaymentProcessorResultCode.equals(paymentProcessorResultCode);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPaymentProcessorResultCodeDetailValue = getDefaultPaymentProcessorResultCodeDetailValueForUpdate();

                    defaultPaymentProcessorResultCodeDetailValue.setIsDefault(false);
                    updatePaymentProcessorResultCodeFromValue(defaultPaymentProcessorResultCodeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            paymentProcessorResultCodeDetail = PaymentProcessorResultCodeDetailFactory.getInstance().create(paymentProcessorResultCodePK,
                    paymentProcessorResultCodeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            paymentProcessorResultCode.setActiveDetail(paymentProcessorResultCodeDetail);
            paymentProcessorResultCode.setLastDetail(paymentProcessorResultCodeDetail);

            sendEvent(paymentProcessorResultCodePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updatePaymentProcessorResultCodeFromValue(final PaymentProcessorResultCodeDetailValue paymentProcessorResultCodeDetailValue,
            final BasePK updatedBy) {
        updatePaymentProcessorResultCodeFromValue(paymentProcessorResultCodeDetailValue, true, updatedBy);
    }

    public void deletePaymentProcessorResultCode(final PaymentProcessorResultCode paymentProcessorResultCode, final BasePK deletedBy) {
        var paymentProcessorTransactionControl = Session.getModelController(PaymentProcessorTransactionControl.class);

        paymentProcessorTransactionControl.deletePaymentProcessorTransactionsByPaymentProcessorResultCode(paymentProcessorResultCode, deletedBy);
        deletePaymentProcessorResultCodeDescriptionsByPaymentProcessorResultCode(paymentProcessorResultCode, deletedBy);

        var paymentProcessorResultCodeDetail = paymentProcessorResultCode.getLastDetailForUpdate();
        paymentProcessorResultCodeDetail.setThruTime(session.START_TIME_LONG);
        paymentProcessorResultCodeDetail.store();
        paymentProcessorResultCode.setActiveDetail(null);

        // Check for default, and pick one if necessary
        var defaultPaymentProcessorResultCode = getDefaultPaymentProcessorResultCode();
        if(defaultPaymentProcessorResultCode == null) {
            var paymentProcessorResultCodes = getPaymentProcessorResultCodesForUpdate();

            if(!paymentProcessorResultCodes.isEmpty()) {
                var iter = paymentProcessorResultCodes.iterator();
                if(iter.hasNext()) {
                    defaultPaymentProcessorResultCode = iter.next();
                }
                var paymentProcessorResultCodeDetailValue = Objects.requireNonNull(defaultPaymentProcessorResultCode).getLastDetailForUpdate().getPaymentProcessorResultCodeDetailValue().clone();

                paymentProcessorResultCodeDetailValue.setIsDefault(true);
                updatePaymentProcessorResultCodeFromValue(paymentProcessorResultCodeDetailValue, false, deletedBy);
            }
        }

        sendEvent(paymentProcessorResultCode.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Payment Processor Result Code Descriptions
    // --------------------------------------------------------------------------------

    public PaymentProcessorResultCodeDescription createPaymentProcessorResultCodeDescription(final PaymentProcessorResultCode paymentProcessorResultCode,
            final Language language, final String description, final BasePK createdBy) {
        var paymentProcessorResultCodeDescription = PaymentProcessorResultCodeDescriptionFactory.getInstance().create(paymentProcessorResultCode,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(paymentProcessorResultCode.getPrimaryKey(), EventTypes.MODIFY, paymentProcessorResultCodeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return paymentProcessorResultCodeDescription;
    }

    private static final Map<EntityPermission, String> getPaymentProcessorResultCodeDescriptionQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessorresultcodedescriptions " +
                    "WHERE pprcrcd_pprcrc_paymentprocessorresultcodeid = ? AND pprcrcd_lang_languageid = ? AND pprcrcd_thrutime = ?",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentprocessorresultcodedescriptions " +
                    "WHERE pprcrcd_pprcrc_paymentprocessorresultcodeid = ? AND pprcrcd_lang_languageid = ? AND pprcrcd_thrutime = ? " +
                    "FOR UPDATE");

    private PaymentProcessorResultCodeDescription getPaymentProcessorResultCodeDescription(final PaymentProcessorResultCode paymentProcessorResultCode,
            final Language language, final EntityPermission entityPermission) {
        return PaymentProcessorResultCodeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getPaymentProcessorResultCodeDescriptionQueries,
                paymentProcessorResultCode, language, Session.MAX_TIME);
    }

    public PaymentProcessorResultCodeDescription getPaymentProcessorResultCodeDescription(final PaymentProcessorResultCode paymentProcessorResultCode,
            final Language language) {
        return getPaymentProcessorResultCodeDescription(paymentProcessorResultCode, language, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorResultCodeDescription getPaymentProcessorResultCodeDescriptionForUpdate(final PaymentProcessorResultCode paymentProcessorResultCode,
            final Language language) {
        return getPaymentProcessorResultCodeDescription(paymentProcessorResultCode, language, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorResultCodeDescriptionValue getPaymentProcessorResultCodeDescriptionValue(final PaymentProcessorResultCodeDescription paymentProcessorResultCodeDescription) {
        return paymentProcessorResultCodeDescription == null ? null: paymentProcessorResultCodeDescription.getPaymentProcessorResultCodeDescriptionValue().clone();
    }

    public PaymentProcessorResultCodeDescriptionValue getPaymentProcessorResultCodeDescriptionValueForUpdate(final PaymentProcessorResultCode paymentProcessorResultCode,
            final Language language) {
        return getPaymentProcessorResultCodeDescriptionValue(getPaymentProcessorResultCodeDescriptionForUpdate(paymentProcessorResultCode, language));
    }

    private static final Map<EntityPermission, String> getPaymentProcessorResultCodeDescriptionsByPaymentProcessorResultCodeQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessorresultcodedescriptions, languages " +
                    "WHERE pprcrcd_pprcrc_paymentprocessorresultcodeid = ? AND pprcrcd_thrutime = ? AND pprcrcd_lang_languageid = lang_languageid " +
                    "ORDER BY lang_sortorder, lang_languageisoname",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentprocessorresultcodedescriptions " +
                    "WHERE pprcrcd_pprcrc_paymentprocessorresultcodeid = ? AND pprcrcd_thrutime = ? " +
                    "FOR UPDATE");

    private List<PaymentProcessorResultCodeDescription> getPaymentProcessorResultCodeDescriptionsByPaymentProcessorResultCode(final PaymentProcessorResultCode paymentProcessorResultCode,
            final EntityPermission entityPermission) {
        return PaymentProcessorResultCodeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission,
                getPaymentProcessorResultCodeDescriptionsByPaymentProcessorResultCodeQueries,
                paymentProcessorResultCode, Session.MAX_TIME);
    }

    public List<PaymentProcessorResultCodeDescription> getPaymentProcessorResultCodeDescriptionsByPaymentProcessorResultCode(final PaymentProcessorResultCode paymentProcessorResultCode) {
        return getPaymentProcessorResultCodeDescriptionsByPaymentProcessorResultCode(paymentProcessorResultCode, EntityPermission.READ_ONLY);
    }

    public List<PaymentProcessorResultCodeDescription> getPaymentProcessorResultCodeDescriptionsByPaymentProcessorResultCodeForUpdate(final PaymentProcessorResultCode paymentProcessorResultCode) {
        return getPaymentProcessorResultCodeDescriptionsByPaymentProcessorResultCode(paymentProcessorResultCode, EntityPermission.READ_WRITE);
    }

    public String getBestPaymentProcessorResultCodeDescription(final PaymentProcessorResultCode paymentProcessorResultCode, final Language language) {
        var paymentProcessorResultCodeDescription = getPaymentProcessorResultCodeDescription(paymentProcessorResultCode, language);
        String description;

        if(paymentProcessorResultCodeDescription == null && !language.getIsDefault()) {
            paymentProcessorResultCodeDescription = getPaymentProcessorResultCodeDescription(paymentProcessorResultCode, getPartyControl().getDefaultLanguage());
        }

        if(paymentProcessorResultCodeDescription == null) {
            description = paymentProcessorResultCode.getLastDetail().getPaymentProcessorResultCodeName();
        } else {
            description = paymentProcessorResultCodeDescription.getDescription();
        }

        return description;
    }

    public PaymentProcessorResultCodeDescriptionTransfer getPaymentProcessorResultCodeDescriptionTransfer(final UserVisit userVisit,
            final PaymentProcessorResultCodeDescription paymentProcessorResultCodeDescription) {
        return getPaymentTransferCaches(userVisit).getPaymentProcessorResultCodeDescriptionTransferCache().getTransfer(paymentProcessorResultCodeDescription);
    }

    public List<PaymentProcessorResultCodeDescriptionTransfer> getPaymentProcessorResultCodeDescriptionTransfersByPaymentProcessorResultCode(final UserVisit userVisit,
            final PaymentProcessorResultCode paymentProcessorResultCode) {
        var paymentProcessorResultCodeDescriptions = getPaymentProcessorResultCodeDescriptionsByPaymentProcessorResultCode(paymentProcessorResultCode);
        var paymentProcessorResultCodeDescriptionTransfers = new ArrayList<PaymentProcessorResultCodeDescriptionTransfer>(paymentProcessorResultCodeDescriptions.size());
        var paymentProcessorResultCodeDescriptionTransferCache = getPaymentTransferCaches(userVisit).getPaymentProcessorResultCodeDescriptionTransferCache();

        paymentProcessorResultCodeDescriptions.forEach((paymentProcessorResultCodeDescription) ->
                paymentProcessorResultCodeDescriptionTransfers.add(paymentProcessorResultCodeDescriptionTransferCache.getTransfer(paymentProcessorResultCodeDescription))
        );

        return paymentProcessorResultCodeDescriptionTransfers;
    }

    public void updatePaymentProcessorResultCodeDescriptionFromValue(final PaymentProcessorResultCodeDescriptionValue paymentProcessorResultCodeDescriptionValue,
            final BasePK updatedBy) {
        if(paymentProcessorResultCodeDescriptionValue.hasBeenModified()) {
            var paymentProcessorResultCodeDescription = PaymentProcessorResultCodeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, paymentProcessorResultCodeDescriptionValue.getPrimaryKey());

            paymentProcessorResultCodeDescription.setThruTime(session.START_TIME_LONG);
            paymentProcessorResultCodeDescription.store();

            var paymentProcessorResultCode = paymentProcessorResultCodeDescription.getPaymentProcessorResultCode();
            var language = paymentProcessorResultCodeDescription.getLanguage();
            var description = paymentProcessorResultCodeDescriptionValue.getDescription();

            paymentProcessorResultCodeDescription = PaymentProcessorResultCodeDescriptionFactory.getInstance().create(paymentProcessorResultCode, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(paymentProcessorResultCode.getPrimaryKey(), EventTypes.MODIFY, paymentProcessorResultCodeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePaymentProcessorResultCodeDescription(final PaymentProcessorResultCodeDescription paymentProcessorResultCodeDescription, final BasePK deletedBy) {
        paymentProcessorResultCodeDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(paymentProcessorResultCodeDescription.getPaymentProcessorResultCodePK(), EventTypes.MODIFY, paymentProcessorResultCodeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deletePaymentProcessorResultCodeDescriptionsByPaymentProcessorResultCode(final PaymentProcessorResultCode paymentProcessorResultCode, final BasePK deletedBy) {
        var paymentProcessorResultCodeDescriptions = getPaymentProcessorResultCodeDescriptionsByPaymentProcessorResultCodeForUpdate(paymentProcessorResultCode);

        paymentProcessorResultCodeDescriptions.forEach((paymentProcessorResultCodeDescription) -> {
            deletePaymentProcessorResultCodeDescription(paymentProcessorResultCodeDescription, deletedBy);
        });
    }

}

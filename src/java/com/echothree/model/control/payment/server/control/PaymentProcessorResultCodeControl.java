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
import com.echothree.model.control.payment.common.choice.PaymentProcessorResultCodeChoicesBean;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorResultCodeDescriptionTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorResultCodeTransfer;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorResultCodeDescriptionTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorResultCodeTransferCache;
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
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_PROCESSOR_RESULT_CODES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_PROCESSOR_RESULT_CODE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorResultCodeDescriptions.PaymentProcessorResultCodeDescriptions;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorResultCodeDetails.PaymentProcessorResultCodeDetails;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorResultCodes.PaymentProcessorResultCodes;
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
public class PaymentProcessorResultCodeControl
        extends BaseModelControl {

    @Inject
    private PaymentProcessorTransactionControl paymentProcessorTransactionControl;

    /** Creates a new instance of PaymentProcessorResultCodeControl */
    protected PaymentProcessorResultCodeControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Payment Processor Result Codes
    // --------------------------------------------------------------------------------

    @Inject
    protected PaymentProcessorResultCodeDetailFactory paymentProcessorResultCodeDetailFactory;

    @Inject
    private PaymentProcessorResultCodeFactory paymentProcessorResultCodeFactory;

    @Inject
    private PaymentProcessorResultCodeTransferCache paymentProcessorResultCodeTransferCache;

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

        var paymentProcessorResultCode = paymentProcessorResultCodeFactory.create();
        var paymentProcessorResultCodeDetail = paymentProcessorResultCodeDetailFactory.create(
                paymentProcessorResultCode, paymentProcessorResultCodeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        paymentProcessorResultCode = paymentProcessorResultCodeFactory.getEntityFromPK(EntityPermission.READ_WRITE, paymentProcessorResultCode.getPrimaryKey());
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

        return paymentProcessorResultCodeFactory.getEntityFromPK(entityPermission, pk);
    }

    public PaymentProcessorResultCode getPaymentProcessorResultCodeByEntityInstance(final EntityInstance entityInstance) {
        return getPaymentProcessorResultCodeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorResultCode getPaymentProcessorResultCodeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getPaymentProcessorResultCodeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countPaymentProcessorResultCodes() {
        return session.getDslContext()
                .selectCount()
                .from(PaymentProcessorResultCodes)
                .join(PaymentProcessorResultCodeDetails).onKey(PAYMENT_PROCESSOR_RESULT_CODES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }


    public PaymentProcessorResultCode getPaymentProcessorResultCodeByName(final String paymentProcessorResultCodeName, final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentProcessorResultCodes.fields())
                .from(PaymentProcessorResultCodes)
                .join(PaymentProcessorResultCodeDetails).onKey(PAYMENT_PROCESSOR_RESULT_CODES_ACTIVE_DETAIL_FK)
                .where(PaymentProcessorResultCodeDetails.PAYMENT_PROCESSOR_RESULT_CODE_NAME.eq(paymentProcessorResultCodeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentProcessorResultCodeFactory.getEntityFromQuery(entityPermission, query);
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


    public PaymentProcessorResultCode getDefaultPaymentProcessorResultCode(final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentProcessorResultCodes.fields())
                .from(PaymentProcessorResultCodes)
                .join(PaymentProcessorResultCodeDetails).onKey(PAYMENT_PROCESSOR_RESULT_CODES_ACTIVE_DETAIL_FK)
                .where(PaymentProcessorResultCodeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentProcessorResultCodeFactory.getEntityFromQuery(entityPermission, query);
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


    private List<PaymentProcessorResultCode> getPaymentProcessorResultCodes(final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentProcessorResultCodes.fields())
                .from(PaymentProcessorResultCodes)
                .join(PaymentProcessorResultCodeDetails).onKey(PAYMENT_PROCESSOR_RESULT_CODES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PaymentProcessorResultCodeDetails.SORT_ORDER, PaymentProcessorResultCodeDetails.PAYMENT_PROCESSOR_RESULT_CODE_NAME),
                    PaymentProcessorResultCodeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentProcessorResultCodeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PaymentProcessorResultCode> getPaymentProcessorResultCodes() {
        return getPaymentProcessorResultCodes(EntityPermission.READ_ONLY);
    }

    public List<PaymentProcessorResultCode> getPaymentProcessorResultCodesForUpdate() {
        return getPaymentProcessorResultCodes(EntityPermission.READ_WRITE);
    }

    public PaymentProcessorResultCodeTransfer getPaymentProcessorResultCodeTransfer(final UserVisit userVisit,
            final PaymentProcessorResultCode paymentProcessorResultCode) {
        return paymentProcessorResultCodeTransferCache.getTransfer(userVisit, paymentProcessorResultCode);
    }

    public List<PaymentProcessorResultCodeTransfer> getPaymentProcessorResultCodeTransfers(final UserVisit userVisit,
            final Collection<PaymentProcessorResultCode> paymentProcessorResultCodes) {
        var paymentProcessorResultCodeTransfers = new ArrayList<PaymentProcessorResultCodeTransfer>(paymentProcessorResultCodes.size());

        paymentProcessorResultCodes.forEach((paymentProcessorResultCode) ->
                paymentProcessorResultCodeTransfers.add(paymentProcessorResultCodeTransferCache.getTransfer(userVisit, paymentProcessorResultCode))
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
            var paymentProcessorResultCode = paymentProcessorResultCodeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    paymentProcessorResultCodeDetailValue.getPaymentProcessorResultCodePK());
            var paymentProcessorResultCodeDetail = paymentProcessorResultCode.getActiveDetailForUpdate();

            paymentProcessorResultCodeDetail.setThruTime(session.getStartTime());
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

            paymentProcessorResultCodeDetail = paymentProcessorResultCodeDetailFactory.create(paymentProcessorResultCodePK,
                    paymentProcessorResultCodeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

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

        paymentProcessorTransactionControl.deletePaymentProcessorTransactionsByPaymentProcessorResultCode(paymentProcessorResultCode, deletedBy);
        deletePaymentProcessorResultCodeDescriptionsByPaymentProcessorResultCode(paymentProcessorResultCode, deletedBy);

        var paymentProcessorResultCodeDetail = paymentProcessorResultCode.getLastDetailForUpdate();
        paymentProcessorResultCodeDetail.setThruTime(session.getStartTime());
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

    @Inject
    private PaymentProcessorResultCodeDescriptionFactory paymentProcessorResultCodeDescriptionFactory;

    @Inject
    private PaymentProcessorResultCodeDescriptionTransferCache paymentProcessorResultCodeDescriptionTransferCache;

    public PaymentProcessorResultCodeDescription createPaymentProcessorResultCodeDescription(final PaymentProcessorResultCode paymentProcessorResultCode,
            final Language language, final String description, final BasePK createdBy) {
        var paymentProcessorResultCodeDescription = paymentProcessorResultCodeDescriptionFactory.create(paymentProcessorResultCode,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(paymentProcessorResultCode.getPrimaryKey(), EventTypes.MODIFY, paymentProcessorResultCodeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return paymentProcessorResultCodeDescription;
    }


    private PaymentProcessorResultCodeDescription getPaymentProcessorResultCodeDescription(final PaymentProcessorResultCode paymentProcessorResultCode,
            final Language language, final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentProcessorResultCodeDescriptions.fields())
                .from(PaymentProcessorResultCodeDescriptions)
                .where(PaymentProcessorResultCodeDescriptions.PAYMENT_PROCESSOR_RESULT_CODE.eq(paymentProcessorResultCode.getPrimaryKey()),
                        PaymentProcessorResultCodeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        PaymentProcessorResultCodeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentProcessorResultCodeDescriptionFactory.getEntityFromQuery(entityPermission, query);
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


    private List<PaymentProcessorResultCodeDescription> getPaymentProcessorResultCodeDescriptionsByPaymentProcessorResultCode(final PaymentProcessorResultCode paymentProcessorResultCode,
            final EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PaymentProcessorResultCodeDescriptions.fields())
                    .from(PaymentProcessorResultCodeDescriptions)
                    .join(Languages).onKey(PAYMENT_PROCESSOR_RESULT_CODE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(PaymentProcessorResultCodeDescriptions.PAYMENT_PROCESSOR_RESULT_CODE.eq(paymentProcessorResultCode.getPrimaryKey()),
                            PaymentProcessorResultCodeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    PaymentProcessorResultCodeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PaymentProcessorResultCodeDescriptions.fields())
                    .from(PaymentProcessorResultCodeDescriptions)
                    .where(PaymentProcessorResultCodeDescriptions.PAYMENT_PROCESSOR_RESULT_CODE.eq(paymentProcessorResultCode.getPrimaryKey()),
                            PaymentProcessorResultCodeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return paymentProcessorResultCodeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
            paymentProcessorResultCodeDescription = getPaymentProcessorResultCodeDescription(paymentProcessorResultCode, partyControl.getDefaultLanguage());
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
        return paymentProcessorResultCodeDescriptionTransferCache.getTransfer(userVisit, paymentProcessorResultCodeDescription);
    }

    public List<PaymentProcessorResultCodeDescriptionTransfer> getPaymentProcessorResultCodeDescriptionTransfersByPaymentProcessorResultCode(final UserVisit userVisit,
            final PaymentProcessorResultCode paymentProcessorResultCode) {
        var paymentProcessorResultCodeDescriptions = getPaymentProcessorResultCodeDescriptionsByPaymentProcessorResultCode(paymentProcessorResultCode);
        var paymentProcessorResultCodeDescriptionTransfers = new ArrayList<PaymentProcessorResultCodeDescriptionTransfer>(paymentProcessorResultCodeDescriptions.size());

        paymentProcessorResultCodeDescriptions.forEach((paymentProcessorResultCodeDescription) ->
                paymentProcessorResultCodeDescriptionTransfers.add(paymentProcessorResultCodeDescriptionTransferCache.getTransfer(userVisit, paymentProcessorResultCodeDescription))
        );

        return paymentProcessorResultCodeDescriptionTransfers;
    }

    public void updatePaymentProcessorResultCodeDescriptionFromValue(final PaymentProcessorResultCodeDescriptionValue paymentProcessorResultCodeDescriptionValue,
            final BasePK updatedBy) {
        if(paymentProcessorResultCodeDescriptionValue.hasBeenModified()) {
            var paymentProcessorResultCodeDescription = paymentProcessorResultCodeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, paymentProcessorResultCodeDescriptionValue.getPrimaryKey());

            paymentProcessorResultCodeDescription.setThruTime(session.getStartTime());
            paymentProcessorResultCodeDescription.store();

            var paymentProcessorResultCode = paymentProcessorResultCodeDescription.getPaymentProcessorResultCode();
            var language = paymentProcessorResultCodeDescription.getLanguage();
            var description = paymentProcessorResultCodeDescriptionValue.getDescription();

            paymentProcessorResultCodeDescription = paymentProcessorResultCodeDescriptionFactory.create(paymentProcessorResultCode, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(paymentProcessorResultCode.getPrimaryKey(), EventTypes.MODIFY, paymentProcessorResultCodeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePaymentProcessorResultCodeDescription(final PaymentProcessorResultCodeDescription paymentProcessorResultCodeDescription, final BasePK deletedBy) {
        paymentProcessorResultCodeDescription.setThruTime(session.getStartTime());

        sendEvent(paymentProcessorResultCodeDescription.getPaymentProcessorResultCodePK(), EventTypes.MODIFY, paymentProcessorResultCodeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deletePaymentProcessorResultCodeDescriptionsByPaymentProcessorResultCode(final PaymentProcessorResultCode paymentProcessorResultCode, final BasePK deletedBy) {
        var paymentProcessorResultCodeDescriptions = getPaymentProcessorResultCodeDescriptionsByPaymentProcessorResultCodeForUpdate(paymentProcessorResultCode);

        paymentProcessorResultCodeDescriptions.forEach((paymentProcessorResultCodeDescription) -> {
            deletePaymentProcessorResultCodeDescription(paymentProcessorResultCodeDescription, deletedBy);
        });
    }

}

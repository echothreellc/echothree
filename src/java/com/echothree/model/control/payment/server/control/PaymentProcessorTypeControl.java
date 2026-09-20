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
import com.echothree.model.control.payment.common.choice.PaymentProcessorTypeChoicesBean;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorTypeDescriptionTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorTypeTransfer;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorTypeDescriptionTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorTypeTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.payment.common.pk.PaymentProcessorTypePK;
import com.echothree.model.data.payment.server.entity.PaymentProcessorType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeDescription;
import com.echothree.model.data.payment.server.factory.PaymentProcessorTypeDescriptionFactory;
import com.echothree.model.data.payment.server.factory.PaymentProcessorTypeDetailFactory;
import com.echothree.model.data.payment.server.factory.PaymentProcessorTypeFactory;
import com.echothree.model.data.payment.server.value.PaymentProcessorTypeDescriptionValue;
import com.echothree.model.data.payment.server.value.PaymentProcessorTypeDetailValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_PROCESSOR_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_PROCESSOR_TYPE_DETAILS_PAYMENT_PROCESSOR_TYPE_FK;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorTypeDescriptions.PaymentProcessorTypeDescriptions;
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
public class PaymentProcessorTypeControl
        extends BaseModelControl {

    @Inject
    private PaymentProcessorTypeActionControl paymentProcessorTypeActionControl;

    @Inject
    protected PaymentProcessorTypeCodeTypeControl paymentProcessorTypeCodeTypeControl;

    /** Creates a new instance of PaymentProcessorTypeControl */
    protected PaymentProcessorTypeControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Payment Processor Types
    // --------------------------------------------------------------------------------

    @Inject
    protected PaymentProcessorTypeDetailFactory paymentProcessorTypeDetailFactory;

    @Inject
    private PaymentProcessorTypeFactory paymentProcessorTypeFactory;

    @Inject
    private PaymentProcessorTypeTransferCache paymentProcessorTypeTransferCache;

    public PaymentProcessorType createPaymentProcessorType(final String paymentProcessorTypeName, Boolean isDefault,
            final Integer sortOrder, final BasePK createdBy) {
        var defaultPaymentProcessorType = getDefaultPaymentProcessorType();
        var defaultFound = defaultPaymentProcessorType != null;

        if(defaultFound && isDefault) {
            var defaultPaymentProcessorTypeDetailValue = getDefaultPaymentProcessorTypeDetailValueForUpdate();

            defaultPaymentProcessorTypeDetailValue.setIsDefault(false);
            updatePaymentProcessorTypeFromValue(defaultPaymentProcessorTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var paymentProcessorType = paymentProcessorTypeFactory.create();
        var paymentProcessorTypeDetail = paymentProcessorTypeDetailFactory.create(
                paymentProcessorType, paymentProcessorTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        paymentProcessorType = paymentProcessorTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE, paymentProcessorType.getPrimaryKey());
        paymentProcessorType.setActiveDetail(paymentProcessorTypeDetail);
        paymentProcessorType.setLastDetail(paymentProcessorTypeDetail);
        paymentProcessorType.store();

        sendEvent(paymentProcessorType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return paymentProcessorType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PaymentProcessorType */
    public PaymentProcessorType getPaymentProcessorTypeByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new PaymentProcessorTypePK(entityInstance.getEntityUniqueId());

        return paymentProcessorTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public PaymentProcessorType getPaymentProcessorTypeByEntityInstance(final EntityInstance entityInstance) {
        return getPaymentProcessorTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorType getPaymentProcessorTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getPaymentProcessorTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countPaymentProcessorTypes() {
        return session.getDslContext()
                .selectCount()
                .from(PaymentProcessorTypes)
                .join(PaymentProcessorTypeDetails).onKey(PAYMENT_PROCESSOR_TYPE_DETAILS_PAYMENT_PROCESSOR_TYPE_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }


    public PaymentProcessorType getPaymentProcessorTypeByName(final String paymentProcessorTypeName, final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentProcessorTypes.fields())
                .from(PaymentProcessorTypes)
                .join(PaymentProcessorTypeDetails).onKey(PAYMENT_PROCESSOR_TYPE_DETAILS_PAYMENT_PROCESSOR_TYPE_FK)
                .where(PaymentProcessorTypeDetails.PAYMENT_PROCESSOR_TYPE_NAME.eq(paymentProcessorTypeName),
                        PaymentProcessorTypeDetails.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentProcessorTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public PaymentProcessorType getPaymentProcessorTypeByName(final String paymentProcessorTypeName) {
        return getPaymentProcessorTypeByName(paymentProcessorTypeName, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorType getPaymentProcessorTypeByNameForUpdate(final String paymentProcessorTypeName) {
        return getPaymentProcessorTypeByName(paymentProcessorTypeName, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTypeDetailValue getPaymentProcessorTypeDetailValueForUpdate(final PaymentProcessorType paymentProcessorType) {
        return paymentProcessorType == null ? null: paymentProcessorType.getLastDetailForUpdate().getPaymentProcessorTypeDetailValue().clone();
    }

    public PaymentProcessorTypeDetailValue getPaymentProcessorTypeDetailValueByNameForUpdate(final String paymentProcessorTypeName) {
        return getPaymentProcessorTypeDetailValueForUpdate(getPaymentProcessorTypeByNameForUpdate(paymentProcessorTypeName));
    }


    public PaymentProcessorType getDefaultPaymentProcessorType(final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentProcessorTypes.fields())
                .from(PaymentProcessorTypes)
                .join(PaymentProcessorTypeDetails).onKey(PAYMENT_PROCESSOR_TYPE_DETAILS_PAYMENT_PROCESSOR_TYPE_FK)
                .where(PaymentProcessorTypeDetails.IS_DEFAULT.eq(true),
                        PaymentProcessorTypeDetails.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentProcessorTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public PaymentProcessorType getDefaultPaymentProcessorType() {
        return getDefaultPaymentProcessorType(EntityPermission.READ_ONLY);
    }

    public PaymentProcessorType getDefaultPaymentProcessorTypeForUpdate() {
        return getDefaultPaymentProcessorType(EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTypeDetailValue getDefaultPaymentProcessorTypeDetailValueForUpdate() {
        return getDefaultPaymentProcessorTypeForUpdate().getLastDetailForUpdate().getPaymentProcessorTypeDetailValue().clone();
    }


    private List<PaymentProcessorType> getPaymentProcessorTypes(final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentProcessorTypes.fields())
                .from(PaymentProcessorTypes)
                .join(PaymentProcessorTypeDetails).onKey(PAYMENT_PROCESSOR_TYPE_DETAILS_PAYMENT_PROCESSOR_TYPE_FK)
                .where(PaymentProcessorTypeDetails.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PaymentProcessorTypeDetails.SORT_ORDER, PaymentProcessorTypeDetails.PAYMENT_PROCESSOR_TYPE_NAME),
                    PaymentProcessorTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentProcessorTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PaymentProcessorType> getPaymentProcessorTypes() {
        return getPaymentProcessorTypes(EntityPermission.READ_ONLY);
    }

    public List<PaymentProcessorType> getPaymentProcessorTypesForUpdate() {
        return getPaymentProcessorTypes(EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTypeTransfer getPaymentProcessorTypeTransfer(final UserVisit userVisit,
            final PaymentProcessorType paymentProcessorType) {
        return paymentProcessorTypeTransferCache.getTransfer(userVisit, paymentProcessorType);
    }

    public List<PaymentProcessorTypeTransfer> getPaymentProcessorTypeTransfers(final UserVisit userVisit,
            final Collection<PaymentProcessorType> paymentProcessorTypes) {
        var paymentProcessorTypeTransfers = new ArrayList<PaymentProcessorTypeTransfer>(paymentProcessorTypes.size());

        paymentProcessorTypes.forEach((paymentProcessorType) ->
                paymentProcessorTypeTransfers.add(paymentProcessorTypeTransferCache.getTransfer(userVisit, paymentProcessorType))
        );

        return paymentProcessorTypeTransfers;
    }

    public List<PaymentProcessorTypeTransfer> getPaymentProcessorTypeTransfers(final UserVisit userVisit) {
        return getPaymentProcessorTypeTransfers(userVisit, getPaymentProcessorTypes());
    }

    public PaymentProcessorTypeChoicesBean getPaymentProcessorTypeChoices(final String defaultPaymentProcessorTypeChoice,
            final Language language, final boolean allowNullChoice) {
        var paymentProcessorTypes = getPaymentProcessorTypes();
        var size = paymentProcessorTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultPaymentProcessorTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var paymentProcessorType : paymentProcessorTypes) {
            var paymentProcessorTypeDetail = paymentProcessorType.getLastDetail();

            var label = getBestPaymentProcessorTypeDescription(paymentProcessorType, language);
            var value = paymentProcessorTypeDetail.getPaymentProcessorTypeName();

            labels.add(label == null ? value : label);
            values.add(value);

            var usingDefaultChoice = Objects.equals(defaultPaymentProcessorTypeChoice, value);
            if(usingDefaultChoice || (defaultValue == null && paymentProcessorTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new PaymentProcessorTypeChoicesBean(labels, values, defaultValue);
    }

    private void updatePaymentProcessorTypeFromValue(final PaymentProcessorTypeDetailValue paymentProcessorTypeDetailValue,
            final boolean checkDefault, final BasePK updatedBy) {
        if(paymentProcessorTypeDetailValue.hasBeenModified()) {
            var paymentProcessorType = paymentProcessorTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    paymentProcessorTypeDetailValue.getPaymentProcessorTypePK());
            var paymentProcessorTypeDetail = paymentProcessorType.getActiveDetailForUpdate();

            paymentProcessorTypeDetail.setThruTime(session.getStartTime());
            paymentProcessorTypeDetail.store();

            var paymentProcessorTypePK = paymentProcessorTypeDetail.getPaymentProcessorTypePK();
            var paymentProcessorTypeName = paymentProcessorTypeDetailValue.getPaymentProcessorTypeName();
            var isDefault = paymentProcessorTypeDetailValue.getIsDefault();
            var sortOrder = paymentProcessorTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultPaymentProcessorType = getDefaultPaymentProcessorType();
                var defaultFound = defaultPaymentProcessorType != null && !defaultPaymentProcessorType.equals(paymentProcessorType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPaymentProcessorTypeDetailValue = getDefaultPaymentProcessorTypeDetailValueForUpdate();

                    defaultPaymentProcessorTypeDetailValue.setIsDefault(false);
                    updatePaymentProcessorTypeFromValue(defaultPaymentProcessorTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            paymentProcessorTypeDetail = paymentProcessorTypeDetailFactory.create(paymentProcessorTypePK,
                    paymentProcessorTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            paymentProcessorType.setActiveDetail(paymentProcessorTypeDetail);
            paymentProcessorType.setLastDetail(paymentProcessorTypeDetail);

            sendEvent(paymentProcessorTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updatePaymentProcessorTypeFromValue(final PaymentProcessorTypeDetailValue paymentProcessorTypeDetailValue,
            final BasePK updatedBy) {
        updatePaymentProcessorTypeFromValue(paymentProcessorTypeDetailValue, true, updatedBy);
    }

    public void deletePaymentProcessorType(final PaymentProcessorType paymentProcessorType, final BasePK deletedBy) {

        paymentProcessorTypeCodeTypeControl.deletePaymentProcessorTypeCodeTypesByPaymentProcessorType(paymentProcessorType, deletedBy);
        paymentProcessorTypeActionControl.deletePaymentProcessorTypeActionsByPaymentProcessorType(paymentProcessorType, deletedBy);
        deletePaymentProcessorTypeDescriptionsByPaymentProcessorType(paymentProcessorType, deletedBy);

        var paymentProcessorTypeDetail = paymentProcessorType.getLastDetailForUpdate();
        paymentProcessorTypeDetail.setThruTime(session.getStartTime());
        paymentProcessorTypeDetail.store();
        paymentProcessorType.setActiveDetail(null);

        // Check for default, and pick one if necessary
        var defaultPaymentProcessorType = getDefaultPaymentProcessorType();
        if(defaultPaymentProcessorType == null) {
            var paymentProcessorTypes = getPaymentProcessorTypesForUpdate();

            if(!paymentProcessorTypes.isEmpty()) {
                var iter = paymentProcessorTypes.iterator();
                if(iter.hasNext()) {
                    defaultPaymentProcessorType = iter.next();
                }
                var paymentProcessorTypeDetailValue = Objects.requireNonNull(defaultPaymentProcessorType).getLastDetailForUpdate().getPaymentProcessorTypeDetailValue().clone();

                paymentProcessorTypeDetailValue.setIsDefault(true);
                updatePaymentProcessorTypeFromValue(paymentProcessorTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(paymentProcessorType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Payment Processor Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    private PaymentProcessorTypeDescriptionFactory paymentProcessorTypeDescriptionFactory;

    @Inject
    private PaymentProcessorTypeDescriptionTransferCache paymentProcessorTypeDescriptionTransferCache;

    public PaymentProcessorTypeDescription createPaymentProcessorTypeDescription(final PaymentProcessorType paymentProcessorType,
            final Language language, final String description, final BasePK createdBy) {
        var paymentProcessorTypeDescription = paymentProcessorTypeDescriptionFactory.create(paymentProcessorType,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(paymentProcessorType.getPrimaryKey(), EventTypes.MODIFY, paymentProcessorTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return paymentProcessorTypeDescription;
    }


    private PaymentProcessorTypeDescription getPaymentProcessorTypeDescription(final PaymentProcessorType paymentProcessorType,
            final Language language, final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentProcessorTypeDescriptions.fields())
                .from(PaymentProcessorTypeDescriptions)
                .where(PaymentProcessorTypeDescriptions.PAYMENT_PROCESSOR_TYPE.eq(paymentProcessorType.getPrimaryKey()),
                        PaymentProcessorTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        PaymentProcessorTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentProcessorTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public PaymentProcessorTypeDescription getPaymentProcessorTypeDescription(final PaymentProcessorType paymentProcessorType,
            final Language language) {
        return getPaymentProcessorTypeDescription(paymentProcessorType, language, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorTypeDescription getPaymentProcessorTypeDescriptionForUpdate(final PaymentProcessorType paymentProcessorType,
            final Language language) {
        return getPaymentProcessorTypeDescription(paymentProcessorType, language, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTypeDescriptionValue getPaymentProcessorTypeDescriptionValue(final PaymentProcessorTypeDescription paymentProcessorTypeDescription) {
        return paymentProcessorTypeDescription == null ? null: paymentProcessorTypeDescription.getPaymentProcessorTypeDescriptionValue().clone();
    }

    public PaymentProcessorTypeDescriptionValue getPaymentProcessorTypeDescriptionValueForUpdate(final PaymentProcessorType paymentProcessorType,
            final Language language) {
        return getPaymentProcessorTypeDescriptionValue(getPaymentProcessorTypeDescriptionForUpdate(paymentProcessorType, language));
    }


    private List<PaymentProcessorTypeDescription> getPaymentProcessorTypeDescriptionsByPaymentProcessorType(final PaymentProcessorType paymentProcessorType,
            final EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PaymentProcessorTypeDescriptions.fields())
                    .from(PaymentProcessorTypeDescriptions)
                    .join(Languages).onKey(PAYMENT_PROCESSOR_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(PaymentProcessorTypeDescriptions.PAYMENT_PROCESSOR_TYPE.eq(paymentProcessorType.getPrimaryKey()),
                            PaymentProcessorTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    PaymentProcessorTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PaymentProcessorTypeDescriptions.fields())
                    .from(PaymentProcessorTypeDescriptions)
                    .where(PaymentProcessorTypeDescriptions.PAYMENT_PROCESSOR_TYPE.eq(paymentProcessorType.getPrimaryKey()),
                            PaymentProcessorTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return paymentProcessorTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PaymentProcessorTypeDescription> getPaymentProcessorTypeDescriptionsByPaymentProcessorType(final PaymentProcessorType paymentProcessorType) {
        return getPaymentProcessorTypeDescriptionsByPaymentProcessorType(paymentProcessorType, EntityPermission.READ_ONLY);
    }

    public List<PaymentProcessorTypeDescription> getPaymentProcessorTypeDescriptionsByPaymentProcessorTypeForUpdate(final PaymentProcessorType paymentProcessorType) {
        return getPaymentProcessorTypeDescriptionsByPaymentProcessorType(paymentProcessorType, EntityPermission.READ_WRITE);
    }

    public String getBestPaymentProcessorTypeDescription(final PaymentProcessorType paymentProcessorType, final Language language) {
        var paymentProcessorTypeDescription = getPaymentProcessorTypeDescription(paymentProcessorType, language);
        String description;

        if(paymentProcessorTypeDescription == null && !language.getIsDefault()) {
            paymentProcessorTypeDescription = getPaymentProcessorTypeDescription(paymentProcessorType, partyControl.getDefaultLanguage());
        }

        if(paymentProcessorTypeDescription == null) {
            description = paymentProcessorType.getLastDetail().getPaymentProcessorTypeName();
        } else {
            description = paymentProcessorTypeDescription.getDescription();
        }

        return description;
    }

    public PaymentProcessorTypeDescriptionTransfer getPaymentProcessorTypeDescriptionTransfer(final UserVisit userVisit,
            final PaymentProcessorTypeDescription paymentProcessorTypeDescription) {
        return paymentProcessorTypeDescriptionTransferCache.getTransfer(userVisit, paymentProcessorTypeDescription);
    }

    public List<PaymentProcessorTypeDescriptionTransfer> getPaymentProcessorTypeDescriptionTransfersByPaymentProcessorType(final UserVisit userVisit,
            final PaymentProcessorType paymentProcessorType) {
        var paymentProcessorTypeDescriptions = getPaymentProcessorTypeDescriptionsByPaymentProcessorType(paymentProcessorType);
        var paymentProcessorTypeDescriptionTransfers = new ArrayList<PaymentProcessorTypeDescriptionTransfer>(paymentProcessorTypeDescriptions.size());

        paymentProcessorTypeDescriptions.forEach((paymentProcessorTypeDescription) ->
                paymentProcessorTypeDescriptionTransfers.add(paymentProcessorTypeDescriptionTransferCache.getTransfer(userVisit, paymentProcessorTypeDescription))
        );

        return paymentProcessorTypeDescriptionTransfers;
    }

    public void updatePaymentProcessorTypeDescriptionFromValue(final PaymentProcessorTypeDescriptionValue paymentProcessorTypeDescriptionValue,
            final BasePK updatedBy) {
        if(paymentProcessorTypeDescriptionValue.hasBeenModified()) {
            var paymentProcessorTypeDescription = paymentProcessorTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, paymentProcessorTypeDescriptionValue.getPrimaryKey());

            paymentProcessorTypeDescription.setThruTime(session.getStartTime());
            paymentProcessorTypeDescription.store();

            var paymentProcessorType = paymentProcessorTypeDescription.getPaymentProcessorType();
            var language = paymentProcessorTypeDescription.getLanguage();
            var description = paymentProcessorTypeDescriptionValue.getDescription();

            paymentProcessorTypeDescription = paymentProcessorTypeDescriptionFactory.create(paymentProcessorType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(paymentProcessorType.getPrimaryKey(), EventTypes.MODIFY, paymentProcessorTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePaymentProcessorTypeDescription(final PaymentProcessorTypeDescription paymentProcessorTypeDescription, final BasePK deletedBy) {
        paymentProcessorTypeDescription.setThruTime(session.getStartTime());

        sendEvent(paymentProcessorTypeDescription.getPaymentProcessorTypePK(), EventTypes.MODIFY, paymentProcessorTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deletePaymentProcessorTypeDescriptionsByPaymentProcessorType(final PaymentProcessorType paymentProcessorType, final BasePK deletedBy) {
        var paymentProcessorTypeDescriptions = getPaymentProcessorTypeDescriptionsByPaymentProcessorTypeForUpdate(paymentProcessorType);

        paymentProcessorTypeDescriptions.forEach((paymentProcessorTypeDescription) -> {
            deletePaymentProcessorTypeDescription(paymentProcessorTypeDescription, deletedBy);
        });
    }

}

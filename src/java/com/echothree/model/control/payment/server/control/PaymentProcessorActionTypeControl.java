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
import com.echothree.model.control.payment.common.choice.PaymentProcessorActionTypeChoicesBean;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorActionTypeDescriptionTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorActionTypeTransfer;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorActionTypeDescriptionTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorActionTypeTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.payment.common.pk.PaymentProcessorActionTypePK;
import com.echothree.model.data.payment.server.entity.PaymentProcessorActionType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorActionTypeDescription;
import com.echothree.model.data.payment.server.factory.PaymentProcessorActionTypeDescriptionFactory;
import com.echothree.model.data.payment.server.factory.PaymentProcessorActionTypeDetailFactory;
import com.echothree.model.data.payment.server.factory.PaymentProcessorActionTypeFactory;
import com.echothree.model.data.payment.server.value.PaymentProcessorActionTypeDescriptionValue;
import com.echothree.model.data.payment.server.value.PaymentProcessorActionTypeDetailValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_PROCESSOR_ACTION_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_PROCESSOR_ACTION_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorActionTypeDescriptions.PaymentProcessorActionTypeDescriptions;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorActionTypeDetails.PaymentProcessorActionTypeDetails;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorActionTypes.PaymentProcessorActionTypes;
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
public class PaymentProcessorActionTypeControl
        extends BaseModelControl {

    @Inject
    private PaymentProcessorTransactionControl paymentProcessorTransactionControl;

    @Inject
    protected PaymentProcessorTypeActionControl paymentProcessorTypeActionControl;

    /** Creates a new instance of PaymentProcessorActionTypeControl */
    protected PaymentProcessorActionTypeControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Payment Processor Action Types
    // --------------------------------------------------------------------------------

    @Inject
    protected PaymentProcessorActionTypeDetailFactory paymentProcessorActionTypeDetailFactory;

    @Inject
    private PaymentProcessorActionTypeFactory paymentProcessorActionTypeFactory;

    @Inject
    private PaymentProcessorActionTypeTransferCache paymentProcessorActionTypeTransferCache;

    public PaymentProcessorActionType createPaymentProcessorActionType(final String paymentProcessorActionTypeName, Boolean isDefault,
            final Integer sortOrder, final BasePK createdBy) {
        var defaultPaymentProcessorActionType = getDefaultPaymentProcessorActionType();
        var defaultFound = defaultPaymentProcessorActionType != null;

        if(defaultFound && isDefault) {
            var defaultPaymentProcessorActionTypeDetailValue = getDefaultPaymentProcessorActionTypeDetailValueForUpdate();

            defaultPaymentProcessorActionTypeDetailValue.setIsDefault(false);
            updatePaymentProcessorActionTypeFromValue(defaultPaymentProcessorActionTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var paymentProcessorActionType = paymentProcessorActionTypeFactory.create();
        var paymentProcessorActionTypeDetail = paymentProcessorActionTypeDetailFactory.create(
                paymentProcessorActionType, paymentProcessorActionTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        paymentProcessorActionType = paymentProcessorActionTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE, paymentProcessorActionType.getPrimaryKey());
        paymentProcessorActionType.setActiveDetail(paymentProcessorActionTypeDetail);
        paymentProcessorActionType.setLastDetail(paymentProcessorActionTypeDetail);
        paymentProcessorActionType.store();

        sendEvent(paymentProcessorActionType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return paymentProcessorActionType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PaymentProcessorActionType */
    public PaymentProcessorActionType getPaymentProcessorActionTypeByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new PaymentProcessorActionTypePK(entityInstance.getEntityUniqueId());

        return paymentProcessorActionTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public PaymentProcessorActionType getPaymentProcessorActionTypeByEntityInstance(final EntityInstance entityInstance) {
        return getPaymentProcessorActionTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorActionType getPaymentProcessorActionTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getPaymentProcessorActionTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countPaymentProcessorActionTypes() {
        return session.getDslContext()
                .selectCount()
                .from(PaymentProcessorActionTypes)
                .join(PaymentProcessorActionTypeDetails).onKey(PAYMENT_PROCESSOR_ACTION_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }


    public PaymentProcessorActionType getPaymentProcessorActionTypeByName(final String paymentProcessorActionTypeName, final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentProcessorActionTypes.fields())
                .from(PaymentProcessorActionTypes)
                .join(PaymentProcessorActionTypeDetails).onKey(PAYMENT_PROCESSOR_ACTION_TYPES_ACTIVE_DETAIL_FK)
                .where(PaymentProcessorActionTypeDetails.PAYMENT_PROCESSOR_ACTION_TYPE_NAME.eq(paymentProcessorActionTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentProcessorActionTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public PaymentProcessorActionType getPaymentProcessorActionTypeByName(final String paymentProcessorActionTypeName) {
        return getPaymentProcessorActionTypeByName(paymentProcessorActionTypeName, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorActionType getPaymentProcessorActionTypeByNameForUpdate(final String paymentProcessorActionTypeName) {
        return getPaymentProcessorActionTypeByName(paymentProcessorActionTypeName, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorActionTypeDetailValue getPaymentProcessorActionTypeDetailValueForUpdate(final PaymentProcessorActionType paymentProcessorActionType) {
        return paymentProcessorActionType == null ? null: paymentProcessorActionType.getLastDetailForUpdate().getPaymentProcessorActionTypeDetailValue().clone();
    }

    public PaymentProcessorActionTypeDetailValue getPaymentProcessorActionTypeDetailValueByNameForUpdate(final String paymentProcessorActionTypeName) {
        return getPaymentProcessorActionTypeDetailValueForUpdate(getPaymentProcessorActionTypeByNameForUpdate(paymentProcessorActionTypeName));
    }


    public PaymentProcessorActionType getDefaultPaymentProcessorActionType(final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentProcessorActionTypes.fields())
                .from(PaymentProcessorActionTypes)
                .join(PaymentProcessorActionTypeDetails).onKey(PAYMENT_PROCESSOR_ACTION_TYPES_ACTIVE_DETAIL_FK)
                .where(PaymentProcessorActionTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentProcessorActionTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public PaymentProcessorActionType getDefaultPaymentProcessorActionType() {
        return getDefaultPaymentProcessorActionType(EntityPermission.READ_ONLY);
    }

    public PaymentProcessorActionType getDefaultPaymentProcessorActionTypeForUpdate() {
        return getDefaultPaymentProcessorActionType(EntityPermission.READ_WRITE);
    }

    public PaymentProcessorActionTypeDetailValue getDefaultPaymentProcessorActionTypeDetailValueForUpdate() {
        return getDefaultPaymentProcessorActionTypeForUpdate().getLastDetailForUpdate().getPaymentProcessorActionTypeDetailValue().clone();
    }


    private List<PaymentProcessorActionType> getPaymentProcessorActionTypes(final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentProcessorActionTypes.fields())
                .from(PaymentProcessorActionTypes)
                .join(PaymentProcessorActionTypeDetails).onKey(PAYMENT_PROCESSOR_ACTION_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PaymentProcessorActionTypeDetails.SORT_ORDER, PaymentProcessorActionTypeDetails.PAYMENT_PROCESSOR_ACTION_TYPE_NAME),
                    PaymentProcessorActionTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentProcessorActionTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PaymentProcessorActionType> getPaymentProcessorActionTypes() {
        return getPaymentProcessorActionTypes(EntityPermission.READ_ONLY);
    }

    public List<PaymentProcessorActionType> getPaymentProcessorActionTypesForUpdate() {
        return getPaymentProcessorActionTypes(EntityPermission.READ_WRITE);
    }

    public PaymentProcessorActionTypeTransfer getPaymentProcessorActionTypeTransfer(final UserVisit userVisit,
            final PaymentProcessorActionType paymentProcessorActionType) {
        return paymentProcessorActionTypeTransferCache.getTransfer(userVisit, paymentProcessorActionType);
    }

    public List<PaymentProcessorActionTypeTransfer> getPaymentProcessorActionTypeTransfers(final UserVisit userVisit,
            final Collection<PaymentProcessorActionType> paymentProcessorActionTypes) {
        var paymentProcessorActionTypeTransfers = new ArrayList<PaymentProcessorActionTypeTransfer>(paymentProcessorActionTypes.size());

        paymentProcessorActionTypes.forEach((paymentProcessorActionType) ->
                paymentProcessorActionTypeTransfers.add(paymentProcessorActionTypeTransferCache.getTransfer(userVisit, paymentProcessorActionType))
        );

        return paymentProcessorActionTypeTransfers;
    }

    public List<PaymentProcessorActionTypeTransfer> getPaymentProcessorActionTypeTransfers(final UserVisit userVisit) {
        return getPaymentProcessorActionTypeTransfers(userVisit, getPaymentProcessorActionTypes());
    }

    public PaymentProcessorActionTypeChoicesBean getPaymentProcessorActionTypeChoices(final String defaultPaymentProcessorActionTypeChoice,
            final Language language, final boolean allowNullChoice) {
        var paymentProcessorActionTypes = getPaymentProcessorActionTypes();
        var size = paymentProcessorActionTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultPaymentProcessorActionTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var paymentProcessorActionType : paymentProcessorActionTypes) {
            var paymentProcessorActionTypeDetail = paymentProcessorActionType.getLastDetail();

            var label = getBestPaymentProcessorActionTypeDescription(paymentProcessorActionType, language);
            var value = paymentProcessorActionTypeDetail.getPaymentProcessorActionTypeName();

            labels.add(label == null ? value : label);
            values.add(value);

            var usingDefaultChoice = Objects.equals(defaultPaymentProcessorActionTypeChoice, value);
            if(usingDefaultChoice || (defaultValue == null && paymentProcessorActionTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new PaymentProcessorActionTypeChoicesBean(labels, values, defaultValue);
    }

    private void updatePaymentProcessorActionTypeFromValue(final PaymentProcessorActionTypeDetailValue paymentProcessorActionTypeDetailValue,
            final boolean checkDefault, final BasePK updatedBy) {
        if(paymentProcessorActionTypeDetailValue.hasBeenModified()) {
            var paymentProcessorActionType = paymentProcessorActionTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    paymentProcessorActionTypeDetailValue.getPaymentProcessorActionTypePK());
            var paymentProcessorActionTypeDetail = paymentProcessorActionType.getActiveDetailForUpdate();

            paymentProcessorActionTypeDetail.setThruTime(session.getStartTime());
            paymentProcessorActionTypeDetail.store();

            var paymentProcessorActionTypePK = paymentProcessorActionTypeDetail.getPaymentProcessorActionTypePK();
            var paymentProcessorActionTypeName = paymentProcessorActionTypeDetailValue.getPaymentProcessorActionTypeName();
            var isDefault = paymentProcessorActionTypeDetailValue.getIsDefault();
            var sortOrder = paymentProcessorActionTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultPaymentProcessorActionType = getDefaultPaymentProcessorActionType();
                var defaultFound = defaultPaymentProcessorActionType != null && !defaultPaymentProcessorActionType.equals(paymentProcessorActionType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPaymentProcessorActionTypeDetailValue = getDefaultPaymentProcessorActionTypeDetailValueForUpdate();

                    defaultPaymentProcessorActionTypeDetailValue.setIsDefault(false);
                    updatePaymentProcessorActionTypeFromValue(defaultPaymentProcessorActionTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            paymentProcessorActionTypeDetail = paymentProcessorActionTypeDetailFactory.create(paymentProcessorActionTypePK,
                    paymentProcessorActionTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            paymentProcessorActionType.setActiveDetail(paymentProcessorActionTypeDetail);
            paymentProcessorActionType.setLastDetail(paymentProcessorActionTypeDetail);

            sendEvent(paymentProcessorActionTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updatePaymentProcessorActionTypeFromValue(final PaymentProcessorActionTypeDetailValue paymentProcessorActionTypeDetailValue,
            final BasePK updatedBy) {
        updatePaymentProcessorActionTypeFromValue(paymentProcessorActionTypeDetailValue, true, updatedBy);
    }

    public void deletePaymentProcessorActionType(final PaymentProcessorActionType paymentProcessorActionType, final BasePK deletedBy) {

        paymentProcessorTypeActionControl.deletePaymentProcessorTypeActionsByPaymentProcessorActionType(paymentProcessorActionType, deletedBy);
        paymentProcessorTransactionControl.deletePaymentProcessorTransactionsByPaymentProcessorActionType(paymentProcessorActionType, deletedBy);
        deletePaymentProcessorActionTypeDescriptionsByPaymentProcessorActionType(paymentProcessorActionType, deletedBy);

        var paymentProcessorActionTypeDetail = paymentProcessorActionType.getLastDetailForUpdate();
        paymentProcessorActionTypeDetail.setThruTime(session.getStartTime());
        paymentProcessorActionTypeDetail.store();
        paymentProcessorActionType.setActiveDetail(null);

        // Check for default, and pick one if necessary
        var defaultPaymentProcessorActionType = getDefaultPaymentProcessorActionType();
        if(defaultPaymentProcessorActionType == null) {
            var paymentProcessorActionTypes = getPaymentProcessorActionTypesForUpdate();

            if(!paymentProcessorActionTypes.isEmpty()) {
                var iter = paymentProcessorActionTypes.iterator();
                if(iter.hasNext()) {
                    defaultPaymentProcessorActionType = iter.next();
                }
                var paymentProcessorActionTypeDetailValue = Objects.requireNonNull(defaultPaymentProcessorActionType).getLastDetailForUpdate().getPaymentProcessorActionTypeDetailValue().clone();

                paymentProcessorActionTypeDetailValue.setIsDefault(true);
                updatePaymentProcessorActionTypeFromValue(paymentProcessorActionTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(paymentProcessorActionType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Payment Processor Action Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    private PaymentProcessorActionTypeDescriptionFactory paymentProcessorActionTypeDescriptionFactory;

    @Inject
    private PaymentProcessorActionTypeDescriptionTransferCache paymentProcessorActionTypeDescriptionTransferCache;

    public PaymentProcessorActionTypeDescription createPaymentProcessorActionTypeDescription(final PaymentProcessorActionType paymentProcessorActionType,
            final Language language, final String description, final BasePK createdBy) {
        var paymentProcessorActionTypeDescription = paymentProcessorActionTypeDescriptionFactory.create(paymentProcessorActionType,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(paymentProcessorActionType.getPrimaryKey(), EventTypes.MODIFY, paymentProcessorActionTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return paymentProcessorActionTypeDescription;
    }


    private PaymentProcessorActionTypeDescription getPaymentProcessorActionTypeDescription(final PaymentProcessorActionType paymentProcessorActionType,
            final Language language, final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentProcessorActionTypeDescriptions.fields())
                .from(PaymentProcessorActionTypeDescriptions)
                .where(PaymentProcessorActionTypeDescriptions.PAYMENT_PROCESSOR_ACTION_TYPE.eq(paymentProcessorActionType.getPrimaryKey()),
                        PaymentProcessorActionTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        PaymentProcessorActionTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentProcessorActionTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public PaymentProcessorActionTypeDescription getPaymentProcessorActionTypeDescription(final PaymentProcessorActionType paymentProcessorActionType,
            final Language language) {
        return getPaymentProcessorActionTypeDescription(paymentProcessorActionType, language, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorActionTypeDescription getPaymentProcessorActionTypeDescriptionForUpdate(final PaymentProcessorActionType paymentProcessorActionType,
            final Language language) {
        return getPaymentProcessorActionTypeDescription(paymentProcessorActionType, language, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorActionTypeDescriptionValue getPaymentProcessorActionTypeDescriptionValue(final PaymentProcessorActionTypeDescription paymentProcessorActionTypeDescription) {
        return paymentProcessorActionTypeDescription == null ? null: paymentProcessorActionTypeDescription.getPaymentProcessorActionTypeDescriptionValue().clone();
    }

    public PaymentProcessorActionTypeDescriptionValue getPaymentProcessorActionTypeDescriptionValueForUpdate(final PaymentProcessorActionType paymentProcessorActionType,
            final Language language) {
        return getPaymentProcessorActionTypeDescriptionValue(getPaymentProcessorActionTypeDescriptionForUpdate(paymentProcessorActionType, language));
    }


    private List<PaymentProcessorActionTypeDescription> getPaymentProcessorActionTypeDescriptionsByPaymentProcessorActionType(final PaymentProcessorActionType paymentProcessorActionType,
            final EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PaymentProcessorActionTypeDescriptions.fields())
                    .from(PaymentProcessorActionTypeDescriptions)
                    .join(Languages).onKey(PAYMENT_PROCESSOR_ACTION_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(PaymentProcessorActionTypeDescriptions.PAYMENT_PROCESSOR_ACTION_TYPE.eq(paymentProcessorActionType.getPrimaryKey()),
                            PaymentProcessorActionTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    PaymentProcessorActionTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PaymentProcessorActionTypeDescriptions.fields())
                    .from(PaymentProcessorActionTypeDescriptions)
                    .where(PaymentProcessorActionTypeDescriptions.PAYMENT_PROCESSOR_ACTION_TYPE.eq(paymentProcessorActionType.getPrimaryKey()),
                            PaymentProcessorActionTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return paymentProcessorActionTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PaymentProcessorActionTypeDescription> getPaymentProcessorActionTypeDescriptionsByPaymentProcessorActionType(final PaymentProcessorActionType paymentProcessorActionType) {
        return getPaymentProcessorActionTypeDescriptionsByPaymentProcessorActionType(paymentProcessorActionType, EntityPermission.READ_ONLY);
    }

    public List<PaymentProcessorActionTypeDescription> getPaymentProcessorActionTypeDescriptionsByPaymentProcessorActionTypeForUpdate(final PaymentProcessorActionType paymentProcessorActionType) {
        return getPaymentProcessorActionTypeDescriptionsByPaymentProcessorActionType(paymentProcessorActionType, EntityPermission.READ_WRITE);
    }

    public String getBestPaymentProcessorActionTypeDescription(final PaymentProcessorActionType paymentProcessorActionType, final Language language) {
        var paymentProcessorActionTypeDescription = getPaymentProcessorActionTypeDescription(paymentProcessorActionType, language);
        String description;

        if(paymentProcessorActionTypeDescription == null && !language.getIsDefault()) {
            paymentProcessorActionTypeDescription = getPaymentProcessorActionTypeDescription(paymentProcessorActionType, partyControl.getDefaultLanguage());
        }

        if(paymentProcessorActionTypeDescription == null) {
            description = paymentProcessorActionType.getLastDetail().getPaymentProcessorActionTypeName();
        } else {
            description = paymentProcessorActionTypeDescription.getDescription();
        }

        return description;
    }

    public PaymentProcessorActionTypeDescriptionTransfer getPaymentProcessorActionTypeDescriptionTransfer(final UserVisit userVisit,
            final PaymentProcessorActionTypeDescription paymentProcessorActionTypeDescription) {
        return paymentProcessorActionTypeDescriptionTransferCache.getTransfer(userVisit, paymentProcessorActionTypeDescription);
    }

    public List<PaymentProcessorActionTypeDescriptionTransfer> getPaymentProcessorActionTypeDescriptionTransfersByPaymentProcessorActionType(final UserVisit userVisit,
            final PaymentProcessorActionType paymentProcessorActionType) {
        var paymentProcessorActionTypeDescriptions = getPaymentProcessorActionTypeDescriptionsByPaymentProcessorActionType(paymentProcessorActionType);
        var paymentProcessorActionTypeDescriptionTransfers = new ArrayList<PaymentProcessorActionTypeDescriptionTransfer>(paymentProcessorActionTypeDescriptions.size());

        paymentProcessorActionTypeDescriptions.forEach((paymentProcessorActionTypeDescription) ->
                paymentProcessorActionTypeDescriptionTransfers.add(paymentProcessorActionTypeDescriptionTransferCache.getTransfer(userVisit, paymentProcessorActionTypeDescription))
        );

        return paymentProcessorActionTypeDescriptionTransfers;
    }

    public void updatePaymentProcessorActionTypeDescriptionFromValue(final PaymentProcessorActionTypeDescriptionValue paymentProcessorActionTypeDescriptionValue,
            final BasePK updatedBy) {
        if(paymentProcessorActionTypeDescriptionValue.hasBeenModified()) {
            var paymentProcessorActionTypeDescription = paymentProcessorActionTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, paymentProcessorActionTypeDescriptionValue.getPrimaryKey());

            paymentProcessorActionTypeDescription.setThruTime(session.getStartTime());
            paymentProcessorActionTypeDescription.store();

            var paymentProcessorActionType = paymentProcessorActionTypeDescription.getPaymentProcessorActionType();
            var language = paymentProcessorActionTypeDescription.getLanguage();
            var description = paymentProcessorActionTypeDescriptionValue.getDescription();

            paymentProcessorActionTypeDescription = paymentProcessorActionTypeDescriptionFactory.create(paymentProcessorActionType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(paymentProcessorActionType.getPrimaryKey(), EventTypes.MODIFY, paymentProcessorActionTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePaymentProcessorActionTypeDescription(final PaymentProcessorActionTypeDescription paymentProcessorActionTypeDescription, final BasePK deletedBy) {
        paymentProcessorActionTypeDescription.setThruTime(session.getStartTime());

        sendEvent(paymentProcessorActionTypeDescription.getPaymentProcessorActionTypePK(), EventTypes.MODIFY, paymentProcessorActionTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deletePaymentProcessorActionTypeDescriptionsByPaymentProcessorActionType(final PaymentProcessorActionType paymentProcessorActionType, final BasePK deletedBy) {
        var paymentProcessorActionTypeDescriptions = getPaymentProcessorActionTypeDescriptionsByPaymentProcessorActionTypeForUpdate(paymentProcessorActionType);

        paymentProcessorActionTypeDescriptions.forEach((paymentProcessorActionTypeDescription) -> {
            deletePaymentProcessorActionTypeDescription(paymentProcessorActionTypeDescription, deletedBy);
        });
    }

}

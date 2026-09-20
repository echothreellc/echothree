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
import com.echothree.model.control.payment.common.choice.PaymentProcessorTypeCodeTypeChoicesBean;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorTypeCodeTypeDescriptionTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorTypeCodeTypeTransfer;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorTypeCodeTypeDescriptionTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorTypeCodeTypeTransferCache;
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
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_PROCESSOR_TYPE_CODE_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_PROCESSOR_TYPE_CODE_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorTypeCodeTypeDescriptions.PaymentProcessorTypeCodeTypeDescriptions;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorTypeCodeTypeDetails.PaymentProcessorTypeCodeTypeDetails;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorTypeCodeTypes.PaymentProcessorTypeCodeTypes;
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
public class PaymentProcessorTypeCodeTypeControl
        extends BaseModelControl {

    @Inject
    private PaymentProcessorTypeCodeControl paymentProcessorTypeCodeControl;

    /** Creates a new instance of PaymentProcessorTypeCodeTypeControl */
    protected PaymentProcessorTypeCodeTypeControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Payment Processor Type Code Types
    // --------------------------------------------------------------------------------

    @Inject
    protected PaymentProcessorTypeCodeTypeDetailFactory paymentProcessorTypeCodeTypeDetailFactory;

    @Inject
    private PaymentProcessorTypeCodeTypeFactory paymentProcessorTypeCodeTypeFactory;

    @Inject
    private PaymentProcessorTypeCodeTypeTransferCache paymentProcessorTypeCodeTypeTransferCache;

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

        var paymentProcessorTypeCodeType = paymentProcessorTypeCodeTypeFactory.create();
        var paymentProcessorTypeCodeTypeDetail = paymentProcessorTypeCodeTypeDetailFactory.create(
                paymentProcessorTypeCodeType, paymentProcessorType, paymentProcessorTypeCodeTypeName, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        paymentProcessorTypeCodeType = paymentProcessorTypeCodeTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE, paymentProcessorTypeCodeType.getPrimaryKey());
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

        return paymentProcessorTypeCodeTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public PaymentProcessorTypeCodeType getPaymentProcessorTypeCodeTypeByEntityInstance(final EntityInstance entityInstance) {
        return getPaymentProcessorTypeCodeTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorTypeCodeType getPaymentProcessorTypeCodeTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getPaymentProcessorTypeCodeTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }


    public PaymentProcessorTypeCodeType getPaymentProcessorTypeCodeTypeByName(final PaymentProcessorType paymentProcessorType,
            final String paymentProcessorTypeCodeTypeName, final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentProcessorTypeCodeTypes.fields())
                .from(PaymentProcessorTypeCodeTypes)
                .join(PaymentProcessorTypeCodeTypeDetails).onKey(PAYMENT_PROCESSOR_TYPE_CODE_TYPES_ACTIVE_DETAIL_FK)
                .where(PaymentProcessorTypeCodeTypeDetails.PAYMENT_PROCESSOR_TYPE.eq(paymentProcessorType.getPrimaryKey()),
                        PaymentProcessorTypeCodeTypeDetails.PAYMENT_PROCESSOR_TYPE_CODE_TYPE_NAME.eq(paymentProcessorTypeCodeTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentProcessorTypeCodeTypeFactory.getEntityFromQuery(entityPermission, query);
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


    public PaymentProcessorTypeCodeType getDefaultPaymentProcessorTypeCodeType(final PaymentProcessorType paymentProcessorType, final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentProcessorTypeCodeTypes.fields())
                .from(PaymentProcessorTypeCodeTypes)
                .join(PaymentProcessorTypeCodeTypeDetails).onKey(PAYMENT_PROCESSOR_TYPE_CODE_TYPES_ACTIVE_DETAIL_FK)
                .where(PaymentProcessorTypeCodeTypeDetails.PAYMENT_PROCESSOR_TYPE.eq(paymentProcessorType.getPrimaryKey()),
                        PaymentProcessorTypeCodeTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentProcessorTypeCodeTypeFactory.getEntityFromQuery(entityPermission, query);
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


    private List<PaymentProcessorTypeCodeType> getPaymentProcessorTypeCodeTypes(final PaymentProcessorType paymentProcessorType, final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentProcessorTypeCodeTypes.fields())
                .from(PaymentProcessorTypeCodeTypes)
                .join(PaymentProcessorTypeCodeTypeDetails).onKey(PAYMENT_PROCESSOR_TYPE_CODE_TYPES_ACTIVE_DETAIL_FK)
                .where(PaymentProcessorTypeCodeTypeDetails.PAYMENT_PROCESSOR_TYPE.eq(paymentProcessorType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PaymentProcessorTypeCodeTypeDetails.SORT_ORDER, PaymentProcessorTypeCodeTypeDetails.PAYMENT_PROCESSOR_TYPE_CODE_TYPE_NAME),
                    PaymentProcessorTypeCodeTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentProcessorTypeCodeTypeFactory.getEntitiesFromQuery(entityPermission, query);
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
            var paymentProcessorTypeCodeType = paymentProcessorTypeCodeTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
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

            paymentProcessorTypeCodeTypeDetail = paymentProcessorTypeCodeTypeDetailFactory.create(paymentProcessorTypeCodeTypePK,
                    paymentProcessorTypePK, paymentProcessorTypeCodeTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

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

    @Inject
    private PaymentProcessorTypeCodeTypeDescriptionFactory paymentProcessorTypeCodeTypeDescriptionFactory;

    @Inject
    private PaymentProcessorTypeCodeTypeDescriptionTransferCache paymentProcessorTypeCodeTypeDescriptionTransferCache;

    public PaymentProcessorTypeCodeTypeDescription createPaymentProcessorTypeCodeTypeDescription(final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType,
            final Language language, final String description, final BasePK createdBy) {
        var paymentProcessorTypeCodeTypeDescription = paymentProcessorTypeCodeTypeDescriptionFactory.create(paymentProcessorTypeCodeType,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(paymentProcessorTypeCodeType.getPrimaryKey(), EventTypes.MODIFY, paymentProcessorTypeCodeTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return paymentProcessorTypeCodeTypeDescription;
    }


    private PaymentProcessorTypeCodeTypeDescription getPaymentProcessorTypeCodeTypeDescription(final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType,
            final Language language, final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentProcessorTypeCodeTypeDescriptions.fields())
                .from(PaymentProcessorTypeCodeTypeDescriptions)
                .where(PaymentProcessorTypeCodeTypeDescriptions.PAYMENT_PROCESSOR_TYPE_CODE_TYPE.eq(paymentProcessorTypeCodeType.getPrimaryKey()),
                        PaymentProcessorTypeCodeTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        PaymentProcessorTypeCodeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentProcessorTypeCodeTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
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


    private List<PaymentProcessorTypeCodeTypeDescription> getPaymentProcessorTypeCodeTypeDescriptionsByPaymentProcessorTypeCodeType(final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType,
            final EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PaymentProcessorTypeCodeTypeDescriptions.fields())
                    .from(PaymentProcessorTypeCodeTypeDescriptions)
                    .join(Languages).onKey(PAYMENT_PROCESSOR_TYPE_CODE_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(PaymentProcessorTypeCodeTypeDescriptions.PAYMENT_PROCESSOR_TYPE_CODE_TYPE.eq(paymentProcessorTypeCodeType.getPrimaryKey()),
                            PaymentProcessorTypeCodeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    PaymentProcessorTypeCodeTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PaymentProcessorTypeCodeTypeDescriptions.fields())
                    .from(PaymentProcessorTypeCodeTypeDescriptions)
                    .where(PaymentProcessorTypeCodeTypeDescriptions.PAYMENT_PROCESSOR_TYPE_CODE_TYPE.eq(paymentProcessorTypeCodeType.getPrimaryKey()),
                            PaymentProcessorTypeCodeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return paymentProcessorTypeCodeTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
            var paymentProcessorTypeCodeTypeDescription = paymentProcessorTypeCodeTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, paymentProcessorTypeCodeTypeDescriptionValue.getPrimaryKey());

            paymentProcessorTypeCodeTypeDescription.setThruTime(session.getStartTime());
            paymentProcessorTypeCodeTypeDescription.store();

            var paymentProcessorTypeCodeType = paymentProcessorTypeCodeTypeDescription.getPaymentProcessorTypeCodeType();
            var language = paymentProcessorTypeCodeTypeDescription.getLanguage();
            var description = paymentProcessorTypeCodeTypeDescriptionValue.getDescription();

            paymentProcessorTypeCodeTypeDescription = paymentProcessorTypeCodeTypeDescriptionFactory.create(paymentProcessorTypeCodeType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

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

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
import com.echothree.model.control.payment.common.choice.PaymentProcessorChoicesBean;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorDescriptionTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorTransfer;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorDescriptionTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.payment.common.pk.PaymentProcessorPK;
import com.echothree.model.data.payment.server.entity.PaymentProcessor;
import com.echothree.model.data.payment.server.entity.PaymentProcessorDescription;
import com.echothree.model.data.payment.server.entity.PaymentProcessorType;
import com.echothree.model.data.payment.server.factory.PaymentProcessorDescriptionFactory;
import com.echothree.model.data.payment.server.factory.PaymentProcessorDetailFactory;
import com.echothree.model.data.payment.server.factory.PaymentProcessorFactory;
import com.echothree.model.data.payment.server.value.PaymentProcessorDescriptionValue;
import com.echothree.model.data.payment.server.value.PaymentProcessorDetailValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_PROCESSORS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_PROCESSOR_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorDescriptions.PaymentProcessorDescriptions;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessorDetails.PaymentProcessorDetails;
import static com.echothree.model.jooq.server.tables.payment.PaymentProcessors.PaymentProcessors;
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
public class PaymentProcessorControl
        extends BaseModelControl {

    @Inject
    private PaymentMethodControl paymentMethodControl;

    @Inject
    protected PaymentProcessorActionControl paymentProcessorActionControl;

    @Inject
    private PaymentProcessorTransactionControl paymentProcessorTransactionControl;

    /** Creates a new instance of PaymentProcessorControl */
    protected PaymentProcessorControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Payment Processors
    // --------------------------------------------------------------------------------

    @Inject
    protected PaymentProcessorDetailFactory paymentProcessorDetailFactory;

    @Inject
    private PaymentProcessorFactory paymentProcessorFactory;

    @Inject
    private PaymentProcessorTransferCache paymentProcessorTransferCache;

    public PaymentProcessor createPaymentProcessor(String paymentProcessorName, PaymentProcessorType paymentProcessorType,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultPaymentProcessor = getDefaultPaymentProcessor();
        var defaultFound = defaultPaymentProcessor != null;
        
        if(defaultFound && isDefault) {
            var defaultPaymentProcessorDetailValue = getDefaultPaymentProcessorDetailValueForUpdate();
            
            defaultPaymentProcessorDetailValue.setIsDefault(false);
            updatePaymentProcessorFromValue(defaultPaymentProcessorDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var paymentProcessor = paymentProcessorFactory.create();
        var paymentProcessorDetail = paymentProcessorDetailFactory.create(
                paymentProcessor, paymentProcessorName, paymentProcessorType, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        paymentProcessor = paymentProcessorFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                paymentProcessor.getPrimaryKey());
        paymentProcessor.setActiveDetail(paymentProcessorDetail);
        paymentProcessor.setLastDetail(paymentProcessorDetail);
        paymentProcessor.store();
        
        sendEvent(paymentProcessor.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return paymentProcessor;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PaymentProcessor */
    public PaymentProcessor getPaymentProcessorByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new PaymentProcessorPK(entityInstance.getEntityUniqueId());

        return paymentProcessorFactory.getEntityFromPK(entityPermission, pk);
    }

    public PaymentProcessor getPaymentProcessorByEntityInstance(final EntityInstance entityInstance) {
        return getPaymentProcessorByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PaymentProcessor getPaymentProcessorByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getPaymentProcessorByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countPaymentProcessors() {
        return session.getDslContext()
                .selectCount()
                .from(PaymentProcessors)
                .join(PaymentProcessorDetails).onKey(PAYMENT_PROCESSORS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPaymentProcessorsByPaymentProcessorType(final PaymentProcessorType paymentProcessorType) {
        return session.getDslContext()
                .selectCount()
                .from(PaymentProcessors)
                .join(PaymentProcessorDetails).onKey(PAYMENT_PROCESSORS_ACTIVE_DETAIL_FK)
                .where(PaymentProcessorDetails.PAYMENT_PROCESSOR_TYPE.eq(paymentProcessorType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public PaymentProcessorDetailValue getPaymentProcessorDetailValueForUpdate(PaymentProcessor paymentProcessor) {
        return paymentProcessor.getLastDetailForUpdate().getPaymentProcessorDetailValue().clone();
    }

    public PaymentProcessor getPaymentProcessorByName(String paymentProcessorName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentProcessors.fields())
                .from(PaymentProcessors)
                .join(PaymentProcessorDetails).onKey(PAYMENT_PROCESSORS_ACTIVE_DETAIL_FK)
                .where(PaymentProcessorDetails.PAYMENT_PROCESSOR_NAME.eq(paymentProcessorName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentProcessorFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public PaymentProcessor getPaymentProcessorByName(String paymentProcessorName) {
        return getPaymentProcessorByName(paymentProcessorName, EntityPermission.READ_ONLY);
    }
    
    public PaymentProcessor getPaymentProcessorByNameForUpdate(String paymentProcessorName) {
        return getPaymentProcessorByName(paymentProcessorName, EntityPermission.READ_WRITE);
    }
    
    public PaymentProcessorDetailValue getPaymentProcessorDetailValueByNameForUpdate(String paymentProcessorName) {
        return getPaymentProcessorDetailValueForUpdate(getPaymentProcessorByNameForUpdate(paymentProcessorName));
    }

    private List<PaymentProcessor> getPaymentProcessors(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentProcessors.fields())
                .from(PaymentProcessors)
                .join(PaymentProcessorDetails).onKey(PAYMENT_PROCESSORS_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PaymentProcessorDetails.SORT_ORDER, PaymentProcessorDetails.PAYMENT_PROCESSOR_NAME),
                    PaymentProcessorFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentProcessorFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PaymentProcessor> getPaymentProcessors() {
        return getPaymentProcessors(EntityPermission.READ_ONLY);
    }

    public List<PaymentProcessor> getPaymentProcessorsForUpdate() {
        return getPaymentProcessors(EntityPermission.READ_WRITE);
    }

    private List<PaymentProcessor> getPaymentProcessorsByPaymentProcessorType(final PaymentProcessorType paymentProcessorType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PaymentProcessors.fields())
                    .from(PaymentProcessors)
                    .join(PaymentProcessorDetails).onKey(PAYMENT_PROCESSORS_ACTIVE_DETAIL_FK)
                    .where(PaymentProcessorDetails.PAYMENT_PROCESSOR_TYPE.eq(paymentProcessorType.getPrimaryKey()))
                    .orderBy(PaymentProcessorDetails.SORT_ORDER, PaymentProcessorDetails.PAYMENT_PROCESSOR_NAME),
                    PaymentProcessorFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PaymentProcessors.fields())
                    .from(PaymentProcessors)
                    .join(PaymentProcessorDetails).onKey(PAYMENT_PROCESSORS_ACTIVE_DETAIL_FK)
                    .where(PaymentProcessorDetails.PAYMENT_PROCESSOR_TYPE.eq(paymentProcessorType.getPrimaryKey()))
                    .forUpdate();
        };

        return paymentProcessorFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PaymentProcessor> getPaymentProcessorsByPaymentProcessorType(final PaymentProcessorType paymentProcessorType) {
        return getPaymentProcessorsByPaymentProcessorType(paymentProcessorType, EntityPermission.READ_ONLY);
    }

    public List<PaymentProcessor> getPaymentProcessorsByPaymentProcessorTypeForUpdate(final PaymentProcessorType paymentProcessorType) {
        return getPaymentProcessorsByPaymentProcessorType(paymentProcessorType, EntityPermission.READ_WRITE);
    }

    public PaymentProcessor getDefaultPaymentProcessor(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentProcessors.fields())
                .from(PaymentProcessors)
                .join(PaymentProcessorDetails).onKey(PAYMENT_PROCESSORS_ACTIVE_DETAIL_FK)
                .where(PaymentProcessorDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentProcessorFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public PaymentProcessor getDefaultPaymentProcessor() {
        return getDefaultPaymentProcessor(EntityPermission.READ_ONLY);
    }
    
    public PaymentProcessor getDefaultPaymentProcessorForUpdate() {
        return getDefaultPaymentProcessor(EntityPermission.READ_WRITE);
    }
    
    public PaymentProcessorDetailValue getDefaultPaymentProcessorDetailValueForUpdate() {
        return getPaymentProcessorDetailValueForUpdate(getDefaultPaymentProcessorForUpdate());
    }
    
    public PaymentProcessorChoicesBean getPaymentProcessorChoices(String defaultPaymentProcessorChoice, Language language,
            boolean allowNullChoice) {
        var paymentProcessors = getPaymentProcessors();
        var size = paymentProcessors.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
        }
        
        for(var paymentProcessor : paymentProcessors) {
            var paymentProcessorDetail = paymentProcessor.getLastDetail();
            var label = getBestPaymentProcessorDescription(paymentProcessor, language);
            var value = paymentProcessorDetail.getPaymentProcessorName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultPaymentProcessorChoice != null && defaultPaymentProcessorChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && paymentProcessorDetail.getIsDefault()))
                defaultValue = value;
        }
        
        return new PaymentProcessorChoicesBean(labels, values, defaultValue);
    }
    
    public PaymentProcessorTransfer getPaymentProcessorTransfer(UserVisit userVisit, PaymentProcessor paymentProcessor) {
        return paymentProcessorTransferCache.getTransfer(userVisit, paymentProcessor);
    }

    public List<PaymentProcessorTransfer> getPaymentProcessorTransfers(UserVisit userVisit, Collection<PaymentProcessor> paymentProcessors) {
        List<PaymentProcessorTransfer> paymentProcessorTransfers = new ArrayList<>(paymentProcessors.size());

        paymentProcessors.forEach((paymentProcessor) ->
                paymentProcessorTransfers.add(paymentProcessorTransferCache.getTransfer(userVisit, paymentProcessor))
        );

        return paymentProcessorTransfers;
    }

    public List<PaymentProcessorTransfer> getPaymentProcessorTransfers(UserVisit userVisit) {
        return getPaymentProcessorTransfers(userVisit, getPaymentProcessors());
    }

    private void updatePaymentProcessorFromValue(PaymentProcessorDetailValue paymentProcessorDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(paymentProcessorDetailValue.hasBeenModified()) {
            var paymentProcessor = paymentProcessorFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     paymentProcessorDetailValue.getPaymentProcessorPK());
            var paymentProcessorDetail = paymentProcessor.getActiveDetailForUpdate();
            
            paymentProcessorDetail.setThruTime(session.getStartTime());
            paymentProcessorDetail.store();

            var paymentProcessorPK = paymentProcessorDetail.getPaymentProcessorPK(); // Not updated
            var paymentProcessorName = paymentProcessorDetailValue.getPaymentProcessorName();
            var paymentProcessorTypePK = paymentProcessorDetail.getPaymentProcessorTypePK(); // Not updated
            var isDefault = paymentProcessorDetailValue.getIsDefault();
            var sortOrder = paymentProcessorDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultPaymentProcessor = getDefaultPaymentProcessor();
                var defaultFound = defaultPaymentProcessor != null && !defaultPaymentProcessor.equals(paymentProcessor);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPaymentProcessorDetailValue = getDefaultPaymentProcessorDetailValueForUpdate();
                    
                    defaultPaymentProcessorDetailValue.setIsDefault(false);
                    updatePaymentProcessorFromValue(defaultPaymentProcessorDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            paymentProcessorDetail = paymentProcessorDetailFactory.create(paymentProcessorPK,
                    paymentProcessorName, paymentProcessorTypePK, isDefault, sortOrder, session.getStartTime(),
                    Session.MAX_TIME);
            
            paymentProcessor.setActiveDetail(paymentProcessorDetail);
            paymentProcessor.setLastDetail(paymentProcessorDetail);
            
            sendEvent(paymentProcessorPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updatePaymentProcessorFromValue(PaymentProcessorDetailValue paymentProcessorDetailValue, BasePK updatedBy) {
        updatePaymentProcessorFromValue(paymentProcessorDetailValue, true, updatedBy);
    }
    
    public void deletePaymentProcessor(PaymentProcessor paymentProcessor, BasePK deletedBy) {

        paymentProcessorActionControl.deletePaymentProcessorActionsByPaymentProcessor(paymentProcessor, deletedBy);
        paymentMethodControl.deletePaymentMethodsByPaymentProcessor(paymentProcessor, deletedBy);
        paymentProcessorTransactionControl.deletePaymentProcessorTransactionsByPaymentProcessor(paymentProcessor, deletedBy);
        deletePaymentProcessorDescriptionsByPaymentProcessor(paymentProcessor, deletedBy);

        var paymentProcessorDetail = paymentProcessor.getLastDetailForUpdate();
        paymentProcessorDetail.setThruTime(session.getStartTime());
        paymentProcessorDetail.store();
        paymentProcessor.setActiveDetail(null);
        
        // Check for default, and pick one if necessary
        var defaultPaymentProcessor = getDefaultPaymentProcessor();
        if(defaultPaymentProcessor == null) {
            var paymentProcessors = getPaymentProcessorsForUpdate();
            
            if(!paymentProcessors.isEmpty()) {
                var iter = paymentProcessors.iterator();
                if(iter.hasNext()) {
                    defaultPaymentProcessor = iter.next();
                }
                var paymentProcessorDetailValue = Objects.requireNonNull(defaultPaymentProcessor).getLastDetailForUpdate().getPaymentProcessorDetailValue().clone();
                
                paymentProcessorDetailValue.setIsDefault(true);
                updatePaymentProcessorFromValue(paymentProcessorDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(paymentProcessor.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Payment Processor Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    private PaymentProcessorDescriptionFactory paymentProcessorDescriptionFactory;

    @Inject
    private PaymentProcessorDescriptionTransferCache paymentProcessorDescriptionTransferCache;

    public PaymentProcessorDescription createPaymentProcessorDescription(PaymentProcessor paymentProcessor, Language language,
            String description, BasePK createdBy) {
        var paymentProcessorDescription = paymentProcessorDescriptionFactory.create(
                paymentProcessor, language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(paymentProcessor.getPrimaryKey(), EventTypes.MODIFY, paymentProcessorDescription.getPrimaryKey(),
                EventTypes.CREATE, createdBy);
        
        return paymentProcessorDescription;
    }
    
    private PaymentProcessorDescription getPaymentProcessorDescription(PaymentProcessor paymentProcessor, Language language,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentProcessorDescriptions.fields())
                .from(PaymentProcessorDescriptions)
                .where(PaymentProcessorDescriptions.PAYMENT_PROCESSOR.eq(paymentProcessor.getPrimaryKey()),
                        PaymentProcessorDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        PaymentProcessorDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentProcessorDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public PaymentProcessorDescription getPaymentProcessorDescription(PaymentProcessor paymentProcessor, Language language) {
        return getPaymentProcessorDescription(paymentProcessor, language, EntityPermission.READ_ONLY);
    }
    
    public PaymentProcessorDescription getPaymentProcessorDescriptionForUpdate(PaymentProcessor paymentProcessor, Language language) {
        return getPaymentProcessorDescription(paymentProcessor, language, EntityPermission.READ_WRITE);
    }
    
    public PaymentProcessorDescriptionValue getPaymentProcessorDescriptionValue(PaymentProcessorDescription paymentProcessorDescription) {
        return paymentProcessorDescription == null? null: paymentProcessorDescription.getPaymentProcessorDescriptionValue().clone();
    }
    
    public PaymentProcessorDescriptionValue getPaymentProcessorDescriptionValueForUpdate(PaymentProcessor paymentProcessor, Language language) {
        return getPaymentProcessorDescriptionValue(getPaymentProcessorDescriptionForUpdate(paymentProcessor, language));
    }
    
    private List<PaymentProcessorDescription> getPaymentProcessorDescriptions(PaymentProcessor paymentProcessor, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PaymentProcessorDescriptions.fields())
                    .from(PaymentProcessorDescriptions)
                    .join(Languages).onKey(PAYMENT_PROCESSOR_DESCRIPTIONS_LANGUAGE_FK)
                    .where(PaymentProcessorDescriptions.PAYMENT_PROCESSOR.eq(paymentProcessor.getPrimaryKey()),
                            PaymentProcessorDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    PaymentProcessorDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PaymentProcessorDescriptions.fields())
                    .from(PaymentProcessorDescriptions)
                    .where(PaymentProcessorDescriptions.PAYMENT_PROCESSOR.eq(paymentProcessor.getPrimaryKey()),
                            PaymentProcessorDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return paymentProcessorDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PaymentProcessorDescription> getPaymentProcessorDescriptions(PaymentProcessor paymentProcessor) {
        return getPaymentProcessorDescriptions(paymentProcessor, EntityPermission.READ_ONLY);
    }
    
    public List<PaymentProcessorDescription> getPaymentProcessorDescriptionsForUpdate(PaymentProcessor paymentProcessor) {
        return getPaymentProcessorDescriptions(paymentProcessor, EntityPermission.READ_WRITE);
    }
    
    public String getBestPaymentProcessorDescription(PaymentProcessor paymentProcessor, Language language) {
        String description;
        var paymentProcessorDescription = getPaymentProcessorDescription(paymentProcessor, language);
        
        if(paymentProcessorDescription == null && !language.getIsDefault()) {
            paymentProcessorDescription = getPaymentProcessorDescription(paymentProcessor, partyControl.getDefaultLanguage());
        }
        
        if(paymentProcessorDescription == null) {
            description = paymentProcessor.getLastDetail().getPaymentProcessorName();
        } else {
            description = paymentProcessorDescription.getDescription();
        }
        
        return description;
    }
    
    public PaymentProcessorDescriptionTransfer getPaymentProcessorDescriptionTransfer(UserVisit userVisit, PaymentProcessorDescription paymentProcessorDescription) {
        return paymentProcessorDescriptionTransferCache.getTransfer(userVisit, paymentProcessorDescription);
    }
    
    public List<PaymentProcessorDescriptionTransfer> getPaymentProcessorDescriptionTransfers(UserVisit userVisit, PaymentProcessor paymentProcessor) {
        var paymentProcessorDescriptions = getPaymentProcessorDescriptions(paymentProcessor);
        List<PaymentProcessorDescriptionTransfer> paymentProcessorDescriptionTransfers = new ArrayList<>(paymentProcessorDescriptions.size());
        
        paymentProcessorDescriptions.forEach((paymentProcessorDescription) ->
                paymentProcessorDescriptionTransfers.add(paymentProcessorDescriptionTransferCache.getTransfer(userVisit, paymentProcessorDescription))
        );
        
        return paymentProcessorDescriptionTransfers;
    }
    
    public void updatePaymentProcessorDescriptionFromValue(PaymentProcessorDescriptionValue paymentProcessorDescriptionValue, BasePK updatedBy) {
        if(paymentProcessorDescriptionValue.hasBeenModified()) {
            var paymentProcessorDescription = paymentProcessorDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     paymentProcessorDescriptionValue.getPrimaryKey());
            
            paymentProcessorDescription.setThruTime(session.getStartTime());
            paymentProcessorDescription.store();

            var paymentProcessor = paymentProcessorDescription.getPaymentProcessor();
            var language = paymentProcessorDescription.getLanguage();
            var description = paymentProcessorDescriptionValue.getDescription();
            
            paymentProcessorDescription = paymentProcessorDescriptionFactory.create(paymentProcessor, language,
                    description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(paymentProcessor.getPrimaryKey(), EventTypes.MODIFY,
                    paymentProcessorDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePaymentProcessorDescription(PaymentProcessorDescription paymentProcessorDescription, BasePK deletedBy) {
        paymentProcessorDescription.setThruTime(session.getStartTime());
        
        sendEvent(paymentProcessorDescription.getPaymentProcessorPK(), EventTypes.MODIFY,
                paymentProcessorDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deletePaymentProcessorDescriptionsByPaymentProcessor(PaymentProcessor paymentProcessor, BasePK deletedBy) {
        var paymentProcessorDescriptions = getPaymentProcessorDescriptionsForUpdate(paymentProcessor);
        
        paymentProcessorDescriptions.forEach((paymentProcessorDescription) -> 
                deletePaymentProcessorDescription(paymentProcessorDescription, deletedBy)
        );
    }
    
}

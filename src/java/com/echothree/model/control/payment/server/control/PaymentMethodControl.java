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
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.order.server.control.OrderPaymentPreferenceControl;
import com.echothree.model.control.payment.common.choice.PaymentMethodChoicesBean;
import com.echothree.model.control.payment.common.transfer.PaymentMethodDescriptionTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentMethodTransfer;
import com.echothree.model.control.payment.server.transfer.PaymentMethodDescriptionTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentMethodTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.payment.common.pk.PaymentMethodPK;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.payment.server.entity.PaymentMethodCheck;
import com.echothree.model.data.payment.server.entity.PaymentMethodCreditCard;
import com.echothree.model.data.payment.server.entity.PaymentMethodDescription;
import com.echothree.model.data.payment.server.entity.PaymentMethodType;
import com.echothree.model.data.payment.server.entity.PaymentProcessor;
import com.echothree.model.data.payment.server.factory.PaymentMethodCheckFactory;
import com.echothree.model.data.payment.server.factory.PaymentMethodCreditCardFactory;
import com.echothree.model.data.payment.server.factory.PaymentMethodDescriptionFactory;
import com.echothree.model.data.payment.server.factory.PaymentMethodDetailFactory;
import com.echothree.model.data.payment.server.factory.PaymentMethodFactory;
import com.echothree.model.data.payment.server.value.PaymentMethodCheckValue;
import com.echothree.model.data.payment.server.value.PaymentMethodCreditCardValue;
import com.echothree.model.data.payment.server.value.PaymentMethodDescriptionValue;
import com.echothree.model.data.payment.server.value.PaymentMethodDetailValue;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_METHODS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_METHOD_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.payment.PaymentMethodChecks.PaymentMethodChecks;
import static com.echothree.model.jooq.server.tables.payment.PaymentMethodCreditCards.PaymentMethodCreditCards;
import static com.echothree.model.jooq.server.tables.payment.PaymentMethodDescriptions.PaymentMethodDescriptions;
import static com.echothree.model.jooq.server.tables.payment.PaymentMethodDetails.PaymentMethodDetails;
import static com.echothree.model.jooq.server.tables.payment.PaymentMethods.PaymentMethods;
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
public class PaymentMethodControl
        extends BaseModelControl {

    @Inject
    private CustomerControl customerControl;

    @Inject
    protected OrderPaymentPreferenceControl orderPaymentPreferenceControl;

    @Inject
    private PartyPaymentMethodControl partyPaymentMethodControl;

    /** Creates a new instance of PaymentMethodControl */
    protected PaymentMethodControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Payment Methods
    // --------------------------------------------------------------------------------

    @Inject
    protected PaymentMethodDetailFactory paymentMethodDetailFactory;

    @Inject
    private PaymentMethodFactory paymentMethodFactory;

    @Inject
    private PaymentMethodTransferCache paymentMethodTransferCache;

    public PaymentMethod createPaymentMethod(String paymentMethodName, PaymentMethodType paymentMethodType, PaymentProcessor paymentProcessor,
            Selector itemSelector, Selector salesOrderItemSelector, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultPaymentMethod = getDefaultPaymentMethod();
        var defaultFound = defaultPaymentMethod != null;
        
        if(defaultFound && isDefault) {
            var defaultPaymentMethodDetailValue = getDefaultPaymentMethodDetailValueForUpdate();
            
            defaultPaymentMethodDetailValue.setIsDefault(false);
            updatePaymentMethodFromValue(defaultPaymentMethodDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var paymentMethod = paymentMethodFactory.create();
        var paymentMethodDetail = paymentMethodDetailFactory.create(paymentMethod, paymentMethodName, paymentMethodType,
                paymentProcessor, itemSelector, salesOrderItemSelector, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        paymentMethod = paymentMethodFactory.getEntityFromPK(EntityPermission.READ_WRITE, paymentMethod.getPrimaryKey());
        paymentMethod.setActiveDetail(paymentMethodDetail);
        paymentMethod.setLastDetail(paymentMethodDetail);
        paymentMethod.store();
        
        sendEvent(paymentMethod.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return paymentMethod;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PaymentMethod */
    public PaymentMethod getPaymentMethodByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new PaymentMethodPK(entityInstance.getEntityUniqueId());

        return paymentMethodFactory.getEntityFromPK(entityPermission, pk);
    }

    public PaymentMethod getPaymentMethodByEntityInstance(EntityInstance entityInstance) {
        return getPaymentMethodByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PaymentMethod getPaymentMethodByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getPaymentMethodByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countPaymentMethods() {
        return session.getDslContext()
                .selectCount()
                .from(PaymentMethods)
                .join(PaymentMethodDetails).onKey(PAYMENT_METHODS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPaymentMethodsByPaymentMethodType(final PaymentMethodType paymentMethodType) {
        return session.getDslContext()
                .selectCount()
                .from(PaymentMethods)
                .join(PaymentMethodDetails).onKey(PAYMENT_METHODS_ACTIVE_DETAIL_FK)
                .where(PaymentMethodDetails.PAYMENT_METHOD_TYPE.eq(paymentMethodType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPaymentMethodsByPaymentProcessor(final PaymentProcessor paymentProcessor) {
        return session.getDslContext()
                .selectCount()
                .from(PaymentMethods)
                .join(PaymentMethodDetails).onKey(PAYMENT_METHODS_ACTIVE_DETAIL_FK)
                .where(PaymentMethodDetails.PAYMENT_PROCESSOR.eq(paymentProcessor.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPaymentMethodsByItemSelector(final Selector itemSelector) {
        return session.getDslContext()
                .selectCount()
                .from(PaymentMethods)
                .join(PaymentMethodDetails).onKey(PAYMENT_METHODS_ACTIVE_DETAIL_FK)
                .where(PaymentMethodDetails.ITEM_SELECTOR.eq(itemSelector.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPaymentMethodsBySalesOrderItemSelector(final Selector salesOrderItemSelector) {
        return session.getDslContext()
                .selectCount()
                .from(PaymentMethods)
                .join(PaymentMethodDetails).onKey(PAYMENT_METHODS_ACTIVE_DETAIL_FK)
                .where(PaymentMethodDetails.SALES_ORDER_ITEM_SELECTOR.eq(salesOrderItemSelector.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public PaymentMethodDetailValue getPaymentMethodDetailValueForUpdate(PaymentMethod paymentMethod) {
        return paymentMethod.getLastDetailForUpdate().getPaymentMethodDetailValue().clone();
    }
    
    public PaymentMethod getPaymentMethodByName(String paymentMethodName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentMethods.fields())
                .from(PaymentMethods)
                .join(PaymentMethodDetails).onKey(PAYMENT_METHODS_ACTIVE_DETAIL_FK)
                .where(PaymentMethodDetails.PAYMENT_METHOD_NAME.eq(paymentMethodName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentMethodFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public PaymentMethod getPaymentMethodByName(String paymentMethodName) {
        return getPaymentMethodByName(paymentMethodName, EntityPermission.READ_ONLY);
    }
    
    public PaymentMethod getPaymentMethodByNameForUpdate(String paymentMethodName) {
        return getPaymentMethodByName(paymentMethodName, EntityPermission.READ_WRITE);
    }
    
    public PaymentMethodDetailValue getPaymentMethodDetailValueByNameForUpdate(String paymentMethodName) {
        return getPaymentMethodDetailValueForUpdate(getPaymentMethodByNameForUpdate(paymentMethodName));
    }
    
    private List<PaymentMethod> getPaymentMethods(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentMethods.fields())
                .from(PaymentMethods)
                .join(PaymentMethodDetails).onKey(PAYMENT_METHODS_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PaymentMethodDetails.SORT_ORDER, PaymentMethodDetails.PAYMENT_METHOD_NAME),
                    PaymentMethodFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentMethodFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PaymentMethod> getPaymentMethods() {
        return getPaymentMethods(EntityPermission.READ_ONLY);
    }
    
    public List<PaymentMethod> getPaymentMethodsForUpdate() {
        return getPaymentMethods(EntityPermission.READ_WRITE);
    }
    
    private List<PaymentMethod> getPaymentMethodsByPaymentMethodType(PaymentMethodType paymentMethodType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentMethods.fields())
                .from(PaymentMethods)
                .join(PaymentMethodDetails).onKey(PAYMENT_METHODS_ACTIVE_DETAIL_FK)
                .where(PaymentMethodDetails.PAYMENT_METHOD_TYPE.eq(paymentMethodType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PaymentMethodDetails.SORT_ORDER, PaymentMethodDetails.PAYMENT_METHOD_NAME),
                    PaymentMethodFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentMethodFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PaymentMethod> getPaymentMethodsByPaymentMethodType(PaymentMethodType paymentMethodType) {
        return getPaymentMethodsByPaymentMethodType(paymentMethodType, EntityPermission.READ_ONLY);
    }

    public List<PaymentMethod> getPaymentMethodsByPaymentMethodTypeForUpdate(PaymentMethodType paymentMethodType) {
        return getPaymentMethodsByPaymentMethodType(paymentMethodType, EntityPermission.READ_WRITE);
    }

    private List<PaymentMethod> getPaymentMethodsByPaymentProcessor(PaymentProcessor paymentProcessor, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentMethods.fields())
                .from(PaymentMethods)
                .join(PaymentMethodDetails).onKey(PAYMENT_METHODS_ACTIVE_DETAIL_FK)
                .where(PaymentMethodDetails.PAYMENT_PROCESSOR.eq(paymentProcessor.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PaymentMethodDetails.SORT_ORDER, PaymentMethodDetails.PAYMENT_METHOD_NAME),
                    PaymentMethodFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentMethodFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PaymentMethod> getPaymentMethodsByPaymentProcessor(PaymentProcessor paymentProcessor) {
        return getPaymentMethodsByPaymentProcessor(paymentProcessor, EntityPermission.READ_ONLY);
    }

    public List<PaymentMethod> getPaymentMethodsByPaymentProcessorForUpdate(PaymentProcessor paymentProcessor) {
        return getPaymentMethodsByPaymentProcessor(paymentProcessor, EntityPermission.READ_WRITE);
    }

    private PaymentMethod getDefaultPaymentMethod(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentMethods.fields())
                .from(PaymentMethods)
                .join(PaymentMethodDetails).onKey(PAYMENT_METHODS_ACTIVE_DETAIL_FK)
                .where(PaymentMethodDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentMethodFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public PaymentMethod getDefaultPaymentMethod() {
        return getDefaultPaymentMethod(EntityPermission.READ_ONLY);
    }
    
    public PaymentMethod getDefaultPaymentMethodForUpdate() {
        return getDefaultPaymentMethod(EntityPermission.READ_WRITE);
    }
    
    public PaymentMethodDetailValue getDefaultPaymentMethodDetailValueForUpdate() {
        return getPaymentMethodDetailValueForUpdate(getDefaultPaymentMethodForUpdate());
    }
    
    public PaymentMethodChoicesBean getPaymentMethodChoices(String defaultPaymentMethodChoice, Language language, boolean allowNullChoice,
            List<PaymentMethod> paymentMethods) {
        var size = paymentMethods.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");
        }

        for(var paymentMethod : paymentMethods) {
            var paymentMethodDetail = paymentMethod.getLastDetail();
            var label = getBestPaymentMethodDescription(paymentMethod, language);
            var value = paymentMethodDetail.getPaymentMethodName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultPaymentMethodChoice != null && defaultPaymentMethodChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && paymentMethodDetail.getIsDefault()))
                defaultValue = value;
        }

        return new PaymentMethodChoicesBean(labels, values, defaultValue);
    }

    public PaymentMethodChoicesBean getPaymentMethodChoices(String defaultPaymentMethodChoice, Language language, boolean allowNullChoice) {
        return getPaymentMethodChoices(defaultPaymentMethodChoice, language, allowNullChoice, getPaymentMethods());
    }

    public PaymentMethodChoicesBean getPaymentMethodChoicesByPaymentMethodType(String defaultPaymentMethodChoice, Language language, boolean allowNullChoice,
            PaymentMethodType paymentMethodType) {
        return getPaymentMethodChoices(defaultPaymentMethodChoice, language, allowNullChoice, getPaymentMethodsByPaymentMethodType(paymentMethodType));
    }

    public PaymentMethodTransfer getPaymentMethodTransfer(UserVisit userVisit, PaymentMethod paymentMethod) {
        return paymentMethodTransferCache.getTransfer(userVisit, paymentMethod);
    }
    
    public List<PaymentMethodTransfer> getPaymentMethodTransfers(UserVisit userVisit, Collection<PaymentMethod> paymentMethods) {
        List<PaymentMethodTransfer> paymentMethodTransfers = new ArrayList<>(paymentMethods.size());

        paymentMethods.forEach((paymentMethod) ->
                paymentMethodTransfers.add(paymentMethodTransferCache.getTransfer(userVisit, paymentMethod))
        );

        return paymentMethodTransfers;
    }

    public List<PaymentMethodTransfer> getPaymentMethodTransfers(UserVisit userVisit) {
        return getPaymentMethodTransfers(userVisit, getPaymentMethods());
    }

    public List<PaymentMethodTransfer> getPaymentMethodTransfersByPaymentMethodType(UserVisit userVisit, PaymentMethodType paymentMethodType) {
        return getPaymentMethodTransfers(userVisit, getPaymentMethodsByPaymentMethodType(paymentMethodType));
    }

    private void updatePaymentMethodFromValue(PaymentMethodDetailValue paymentMethodDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(paymentMethodDetailValue.hasBeenModified()) {
            var paymentMethod = paymentMethodFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     paymentMethodDetailValue.getPaymentMethodPK());
            var paymentMethodDetail = paymentMethod.getActiveDetailForUpdate();
            
            paymentMethodDetail.setThruTime(session.getStartTime());
            paymentMethodDetail.store();

            var paymentMethodPK = paymentMethodDetail.getPaymentMethodPK(); // Not updated
            var paymentMethodName = paymentMethodDetailValue.getPaymentMethodName();
            var paymentMethodTypePK = paymentMethodDetail.getPaymentMethodTypePK(); // Not updated
            var paymentProcessorPK = paymentMethodDetailValue.getPaymentProcessorPK();
            var itemSelectorPK = paymentMethodDetailValue.getItemSelectorPK();
            var salesOrderItemSelectorPK = paymentMethodDetailValue.getSalesOrderItemSelectorPK();
            var isDefault = paymentMethodDetailValue.getIsDefault();
            var sortOrder = paymentMethodDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultPaymentMethod = getDefaultPaymentMethod();
                var defaultFound = defaultPaymentMethod != null && !defaultPaymentMethod.equals(paymentMethod);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPaymentMethodDetailValue = getDefaultPaymentMethodDetailValueForUpdate();
                    
                    defaultPaymentMethodDetailValue.setIsDefault(false);
                    updatePaymentMethodFromValue(defaultPaymentMethodDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            paymentMethodDetail = paymentMethodDetailFactory.create(paymentMethodPK, paymentMethodName, paymentMethodTypePK, paymentProcessorPK,
                    itemSelectorPK, salesOrderItemSelectorPK, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            paymentMethod.setActiveDetail(paymentMethodDetail);
            paymentMethod.setLastDetail(paymentMethodDetail);
            
            sendEvent(paymentMethodPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updatePaymentMethodFromValue(PaymentMethodDetailValue paymentMethodDetailValue, BasePK updatedBy) {
        updatePaymentMethodFromValue(paymentMethodDetailValue, true, updatedBy);
    }
    
    public void deletePaymentMethod(PaymentMethod paymentMethod, BasePK deletedBy) {

        customerControl.deleteCustomerTypePaymentMethodsByPaymentMethod(paymentMethod, deletedBy);
        orderPaymentPreferenceControl.deleteOrderPaymentPreferencesByPaymentMethod(paymentMethod, deletedBy);
        partyPaymentMethodControl.deletePartyPaymentMethodsByPaymentMethod(paymentMethod, deletedBy);
        deletePaymentMethodDescriptionsByPaymentMethod(paymentMethod, deletedBy);

        var paymentMethodDetail = paymentMethod.getLastDetailForUpdate();
        paymentMethodDetail.setThruTime(session.getStartTime());
        paymentMethodDetail.store();
        paymentMethod.setActiveDetail(null);
        
        // Check for default, and pick one if necessary
        var defaultPaymentMethod = getDefaultPaymentMethod();
        if(defaultPaymentMethod == null) {
            var paymentMethods = getPaymentMethodsForUpdate();
            
            if(!paymentMethods.isEmpty()) {
                var iter = paymentMethods.iterator();
                if(iter.hasNext()) {
                    defaultPaymentMethod = iter.next();
                }
                var paymentMethodDetailValue = Objects.requireNonNull(defaultPaymentMethod).getLastDetailForUpdate().getPaymentMethodDetailValue().clone();
                
                paymentMethodDetailValue.setIsDefault(true);
                updatePaymentMethodFromValue(paymentMethodDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(paymentMethod.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deletePaymentMethodsByPaymentProcessor(PaymentProcessor paymentProcessor, BasePK deletedBy) {
        var paymentMethods = getPaymentMethodsByPaymentProcessorForUpdate(paymentProcessor);
        
        paymentMethods.forEach((paymentMethod) -> 
                deletePaymentMethod(paymentMethod, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Payment Method Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    private PaymentMethodDescriptionFactory paymentMethodDescriptionFactory;

    @Inject
    private PaymentMethodDescriptionTransferCache paymentMethodDescriptionTransferCache;

    public PaymentMethodDescription createPaymentMethodDescription(PaymentMethod paymentMethod, Language language,
            String description, BasePK createdBy) {
        var paymentMethodDescription = paymentMethodDescriptionFactory.create(
                paymentMethod, language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(paymentMethod.getPrimaryKey(), EventTypes.MODIFY, paymentMethodDescription.getPrimaryKey(),
                EventTypes.CREATE, createdBy);
        
        return paymentMethodDescription;
    }
    
    private PaymentMethodDescription getPaymentMethodDescription(PaymentMethod paymentMethod, Language language,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentMethodDescriptions.fields())
                .from(PaymentMethodDescriptions)
                .where(PaymentMethodDescriptions.PAYMENT_METHOD.eq(paymentMethod.getPrimaryKey()),
                        PaymentMethodDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        PaymentMethodDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentMethodDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public PaymentMethodDescription getPaymentMethodDescription(PaymentMethod paymentMethod, Language language) {
        return getPaymentMethodDescription(paymentMethod, language, EntityPermission.READ_ONLY);
    }
    
    public PaymentMethodDescription getPaymentMethodDescriptionForUpdate(PaymentMethod paymentMethod, Language language) {
        return getPaymentMethodDescription(paymentMethod, language, EntityPermission.READ_WRITE);
    }
    
    public PaymentMethodDescriptionValue getPaymentMethodDescriptionValue(PaymentMethodDescription paymentMethodDescription) {
        return paymentMethodDescription == null? null: paymentMethodDescription.getPaymentMethodDescriptionValue().clone();
    }
    
    public PaymentMethodDescriptionValue getPaymentMethodDescriptionValueForUpdate(PaymentMethod paymentMethod, Language language) {
        return getPaymentMethodDescriptionValue(getPaymentMethodDescriptionForUpdate(paymentMethod, language));
    }
    
    private List<PaymentMethodDescription> getPaymentMethodDescriptions(PaymentMethod paymentMethod, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PaymentMethodDescriptions.fields())
                    .from(PaymentMethodDescriptions)
                    .join(Languages).onKey(PAYMENT_METHOD_DESCRIPTIONS_LANGUAGE_FK)
                    .where(PaymentMethodDescriptions.PAYMENT_METHOD.eq(paymentMethod.getPrimaryKey()),
                            PaymentMethodDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    PaymentMethodDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PaymentMethodDescriptions.fields())
                    .from(PaymentMethodDescriptions)
                    .where(PaymentMethodDescriptions.PAYMENT_METHOD.eq(paymentMethod.getPrimaryKey()),
                            PaymentMethodDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return paymentMethodDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PaymentMethodDescription> getPaymentMethodDescriptions(PaymentMethod paymentMethod) {
        return getPaymentMethodDescriptions(paymentMethod, EntityPermission.READ_ONLY);
    }
    
    public List<PaymentMethodDescription> getPaymentMethodDescriptionsForUpdate(PaymentMethod paymentMethod) {
        return getPaymentMethodDescriptions(paymentMethod, EntityPermission.READ_WRITE);
    }
    
    public String getBestPaymentMethodDescription(PaymentMethod paymentMethod, Language language) {
        String description;
        var paymentMethodDescription = getPaymentMethodDescription(paymentMethod, language);
        
        if(paymentMethodDescription == null && !language.getIsDefault()) {
            paymentMethodDescription = getPaymentMethodDescription(paymentMethod, partyControl.getDefaultLanguage());
        }
        
        if(paymentMethodDescription == null) {
            description = paymentMethod.getLastDetail().getPaymentMethodName();
        } else {
            description = paymentMethodDescription.getDescription();
        }
        
        return description;
    }
    
    public PaymentMethodDescriptionTransfer getPaymentMethodDescriptionTransfer(UserVisit userVisit, PaymentMethodDescription paymentMethodDescription) {
        return paymentMethodDescriptionTransferCache.getTransfer(userVisit, paymentMethodDescription);
    }
    
    public List<PaymentMethodDescriptionTransfer> getPaymentMethodDescriptionTransfers(UserVisit userVisit, PaymentMethod paymentMethod) {
        var paymentMethodDescriptions = getPaymentMethodDescriptions(paymentMethod);
        List<PaymentMethodDescriptionTransfer> paymentMethodDescriptionTransfers = new ArrayList<>(paymentMethodDescriptions.size());
        
        paymentMethodDescriptions.forEach((paymentMethodDescription) ->
                paymentMethodDescriptionTransfers.add(paymentMethodDescriptionTransferCache.getTransfer(userVisit, paymentMethodDescription))
        );
        
        return paymentMethodDescriptionTransfers;
    }
    
    public void updatePaymentMethodDescriptionFromValue(PaymentMethodDescriptionValue paymentMethodDescriptionValue, BasePK updatedBy) {
        if(paymentMethodDescriptionValue.hasBeenModified()) {
            var paymentMethodDescription = paymentMethodDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     paymentMethodDescriptionValue.getPrimaryKey());
            
            paymentMethodDescription.setThruTime(session.getStartTime());
            paymentMethodDescription.store();

            var paymentMethod = paymentMethodDescription.getPaymentMethod();
            var language = paymentMethodDescription.getLanguage();
            var description = paymentMethodDescriptionValue.getDescription();
            
            paymentMethodDescription = paymentMethodDescriptionFactory.create(paymentMethod, language,
                    description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(paymentMethod.getPrimaryKey(), EventTypes.MODIFY,
                    paymentMethodDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePaymentMethodDescription(PaymentMethodDescription paymentMethodDescription, BasePK deletedBy) {
        paymentMethodDescription.setThruTime(session.getStartTime());
        
        sendEvent(paymentMethodDescription.getPaymentMethodPK(), EventTypes.MODIFY,
                paymentMethodDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deletePaymentMethodDescriptionsByPaymentMethod(PaymentMethod paymentMethod, BasePK deletedBy) {
        var paymentMethodDescriptions = getPaymentMethodDescriptionsForUpdate(paymentMethod);
        
        paymentMethodDescriptions.forEach((paymentMethodDescription) -> 
                deletePaymentMethodDescription(paymentMethodDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Payment Method Checks
    // --------------------------------------------------------------------------------

    @Inject
    private PaymentMethodCheckFactory paymentMethodCheckFactory;

    public PaymentMethodCheck createPaymentMethodCheck(PaymentMethod paymentMethod, Integer holdDays, BasePK createdBy) {
        var paymentMethodCheck = paymentMethodCheckFactory.create(paymentMethod, holdDays,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(paymentMethod.getPrimaryKey(), EventTypes.MODIFY, paymentMethodCheck.getPrimaryKey(),
                EventTypes.CREATE, createdBy);
        
        return paymentMethodCheck;
    }
    
    private PaymentMethodCheck getPaymentMethodCheck(PaymentMethod paymentMethod, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentMethodChecks.fields())
                .from(PaymentMethodChecks)
                .where(PaymentMethodChecks.PAYMENT_METHOD.eq(paymentMethod.getPrimaryKey()),
                        PaymentMethodChecks.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentMethodCheckFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public PaymentMethodCheck getPaymentMethodCheck(PaymentMethod paymentMethod) {
        return getPaymentMethodCheck(paymentMethod, EntityPermission.READ_ONLY);
    }
    
    public PaymentMethodCheck getPaymentMethodCheckForUpdate(PaymentMethod paymentMethod) {
        return getPaymentMethodCheck(paymentMethod, EntityPermission.READ_WRITE);
    }
    
    public PaymentMethodCheckValue getPaymentMethodCheckValueForUpdate(PaymentMethod paymentMethod) {
        var paymentMethodCheck = getPaymentMethodCheckForUpdate(paymentMethod);
        
        return paymentMethodCheck == null? null: paymentMethodCheck.getPaymentMethodCheckValue().clone();
    }
    
    public void updatePaymentMethodCheckFromValue(PaymentMethodCheckValue paymentMethodCheckValue, BasePK updatedBy) {
        if(paymentMethodCheckValue.hasBeenModified()) {
            var paymentMethodCheck = paymentMethodCheckFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     paymentMethodCheckValue.getPrimaryKey());
            
            paymentMethodCheck.setThruTime(session.getStartTime());
            paymentMethodCheck.store();

            var paymentMethodPK = paymentMethodCheck.getPaymentMethodPK(); // Not updated
            var holdDays = paymentMethodCheckValue.getHoldDays();
            
            paymentMethodCheck = paymentMethodCheckFactory.create(paymentMethodPK, holdDays,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(paymentMethodPK, EventTypes.MODIFY, paymentMethodCheck.getPrimaryKey(),
                    EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePaymentMethodCheck(PaymentMethodCheck paymentMethodCheck, BasePK deletedBy) {
        paymentMethodCheck.setThruTime(session.getStartTime());
        
        sendEvent(paymentMethodCheck.getPaymentMethodPK(), EventTypes.MODIFY,
                paymentMethodCheck.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Payment Method Credit Cards
    // --------------------------------------------------------------------------------

    @Inject
    private PaymentMethodCreditCardFactory paymentMethodCreditCardFactory;

    public PaymentMethodCreditCard createPaymentMethodCreditCard(PaymentMethod paymentMethod, Boolean requestNameOnCard,
            Boolean requireNameOnCard, Boolean checkCardNumber, Boolean requestExpirationDate, Boolean requireExpirationDate,
            Boolean checkExpirationDate, Boolean requestSecurityCode, Boolean requireSecurityCode,
            String cardNumberValidationPattern, String securityCodeValidationPattern, Boolean retainCreditCard,
            Boolean retainSecurityCode, Boolean requestBilling, Boolean requireBilling, Boolean requestIssuer, Boolean requireIssuer, BasePK createdBy) {
        var paymentMethodCreditCard = paymentMethodCreditCardFactory.create(
                paymentMethod, requestNameOnCard, requireNameOnCard, checkCardNumber, requestExpirationDate, requireExpirationDate,
                checkExpirationDate, requestSecurityCode, requireSecurityCode, cardNumberValidationPattern,
                securityCodeValidationPattern, retainCreditCard, retainSecurityCode, requestBilling, requireBilling, requestIssuer, requireIssuer,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(paymentMethod.getPrimaryKey(), EventTypes.MODIFY, paymentMethodCreditCard.getPrimaryKey(),
                EventTypes.CREATE, createdBy);
        
        return paymentMethodCreditCard;
    }
    
    private PaymentMethodCreditCard getPaymentMethodCreditCard(PaymentMethod paymentMethod, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PaymentMethodCreditCards.fields())
                .from(PaymentMethodCreditCards)
                .where(PaymentMethodCreditCards.PAYMENT_METHOD.eq(paymentMethod.getPrimaryKey()),
                        PaymentMethodCreditCards.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return paymentMethodCreditCardFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public PaymentMethodCreditCard getPaymentMethodCreditCard(PaymentMethod paymentMethod) {
        return getPaymentMethodCreditCard(paymentMethod, EntityPermission.READ_ONLY);
    }
    
    public PaymentMethodCreditCard getPaymentMethodCreditCardForUpdate(PaymentMethod paymentMethod) {
        return getPaymentMethodCreditCard(paymentMethod, EntityPermission.READ_WRITE);
    }
    
    public PaymentMethodCreditCardValue getPaymentMethodCreditCardValueForUpdate(PaymentMethod paymentMethod) {
        var paymentMethodCreditCard = getPaymentMethodCreditCardForUpdate(paymentMethod);
        
        return paymentMethodCreditCard == null? null: paymentMethodCreditCard.getPaymentMethodCreditCardValue().clone();
    }
    
    public void updatePaymentMethodCreditCardFromValue(PaymentMethodCreditCardValue paymentMethodCreditCardValue, BasePK updatedBy) {
        if(paymentMethodCreditCardValue.hasBeenModified()) {
            var paymentMethodCreditCard = paymentMethodCreditCardFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     paymentMethodCreditCardValue.getPrimaryKey());
            
            paymentMethodCreditCard.setThruTime(session.getStartTime());
            paymentMethodCreditCard.store();

            var paymentMethodPK = paymentMethodCreditCard.getPaymentMethodPK(); // Not updated
            var requestNameOnCard = paymentMethodCreditCardValue.getRequestNameOnCard();
            var reqireNameOnCard = paymentMethodCreditCardValue.getRequireNameOnCard();
            var checkCardNumber = paymentMethodCreditCardValue.getCheckCardNumber();
            var requestExpirationDate = paymentMethodCreditCardValue.getRequireExpirationDate();
            var requireExpirationDate = paymentMethodCreditCardValue.getRequireExpirationDate();
            var checkExpirationDate = paymentMethodCreditCardValue.getCheckExpirationDate();
            var requestSecurityCode = paymentMethodCreditCardValue.getRequestSecurityCode();
            var requireSecurityCode = paymentMethodCreditCardValue.getRequireSecurityCode();
            var cardNumberValidationPattern = paymentMethodCreditCardValue.getCardNumberValidationPattern();
            var securityCodeValidationPattern = paymentMethodCreditCardValue.getSecurityCodeValidationPattern();
            var retainCreditCard = paymentMethodCreditCardValue.getRetainCreditCard();
            var retainSecurityCode = paymentMethodCreditCardValue.getRetainSecurityCode();
            var requestBilling = paymentMethodCreditCardValue.getRequestBilling();
            var requireBilling = paymentMethodCreditCardValue.getRequireBilling();
            var requestIssuer = paymentMethodCreditCardValue.getRequestIssuer();
            var requireIssuer = paymentMethodCreditCardValue.getRequireIssuer();
            
            paymentMethodCreditCard = paymentMethodCreditCardFactory.create(paymentMethodPK,
                    requestNameOnCard, reqireNameOnCard, checkCardNumber, requestExpirationDate, requireExpirationDate,
                    checkExpirationDate, requestSecurityCode, requireSecurityCode, cardNumberValidationPattern,
                    securityCodeValidationPattern, retainCreditCard, retainSecurityCode, requestBilling, requireBilling, requestIssuer, requireIssuer,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(paymentMethodPK, EventTypes.MODIFY, paymentMethodCreditCard.getPrimaryKey(),
                    EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePaymentMethodCreditCard(PaymentMethodCreditCard paymentMethodCreditCard, BasePK deletedBy) {
        paymentMethodCreditCard.setThruTime(session.getStartTime());
        
        sendEvent(paymentMethodCreditCard.getPaymentMethodPK(), EventTypes.MODIFY,
                paymentMethodCreditCard.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
 }

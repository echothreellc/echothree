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

package com.echothree.model.control.customer.server.control;

import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.customer.common.choice.CustomerCreditStatusChoicesBean;
import com.echothree.model.control.customer.common.choice.CustomerStatusChoicesBean;
import com.echothree.model.control.customer.common.choice.CustomerTypeChoicesBean;
import com.echothree.model.control.customer.common.transfer.CustomerResultTransfer;
import com.echothree.model.control.customer.common.transfer.CustomerTransfer;
import com.echothree.model.control.customer.common.transfer.CustomerTypeDescriptionTransfer;
import com.echothree.model.control.customer.common.transfer.CustomerTypePaymentMethodTransfer;
import com.echothree.model.control.customer.common.transfer.CustomerTypeShippingMethodTransfer;
import com.echothree.model.control.customer.common.transfer.CustomerTypeTransfer;
import com.echothree.model.control.customer.common.workflow.CustomerCreditStatusConstants;
import com.echothree.model.control.customer.common.workflow.CustomerStatusConstants;
import com.echothree.model.control.customer.server.graphql.CustomerObject;
import com.echothree.model.control.customer.server.transfer.CustomerTransferCache;
import com.echothree.model.control.customer.server.transfer.CustomerTypeDescriptionTransferCache;
import com.echothree.model.control.customer.server.transfer.CustomerTypePaymentMethodTransferCache;
import com.echothree.model.control.customer.server.transfer.CustomerTypeShippingMethodTransferCache;
import com.echothree.model.control.customer.server.transfer.CustomerTypeTransferCache;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.customer.common.pk.CustomerTypePK;
import com.echothree.model.data.customer.server.entity.Customer;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.customer.server.entity.CustomerTypeDescription;
import com.echothree.model.data.customer.server.entity.CustomerTypePaymentMethod;
import com.echothree.model.data.customer.server.entity.CustomerTypeShippingMethod;
import com.echothree.model.data.customer.server.factory.CustomerFactory;
import com.echothree.model.data.customer.server.factory.CustomerTypeDescriptionFactory;
import com.echothree.model.data.customer.server.factory.CustomerTypeDetailFactory;
import com.echothree.model.data.customer.server.factory.CustomerTypeFactory;
import com.echothree.model.data.customer.server.factory.CustomerTypePaymentMethodFactory;
import com.echothree.model.data.customer.server.factory.CustomerTypeShippingMethodFactory;
import com.echothree.model.data.customer.server.value.CustomerTypeDescriptionValue;
import com.echothree.model.data.customer.server.value.CustomerTypeDetailValue;
import com.echothree.model.data.customer.server.value.CustomerTypePaymentMethodValue;
import com.echothree.model.data.customer.server.value.CustomerTypeShippingMethodValue;
import com.echothree.model.data.customer.server.value.CustomerValue;
import com.echothree.model.data.inventory.server.entity.AllocationPriority;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.search.common.CachedExecutedSearchResultConstants;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.CachedExecutedSearchResultFactory;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.shipment.server.entity.FreeOnBoard;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import static com.echothree.model.jooq.server.keys.customer.CustomerForeignKeys.CUSTOMER_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.customer.CustomerForeignKeys.CUSTOMER_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.customer.CustomerForeignKeys.CUSTOMER_TYPE_PAYMENT_METHODS_CUSTOMER_TYPE_FK;
import static com.echothree.model.jooq.server.keys.customer.CustomerForeignKeys.CUSTOMER_TYPE_PAYMENT_METHODS_PAYMENT_METHOD_FK;
import static com.echothree.model.jooq.server.keys.customer.CustomerForeignKeys.CUSTOMER_TYPE_SHIPPING_METHODS_CUSTOMER_TYPE_FK;
import static com.echothree.model.jooq.server.keys.customer.CustomerForeignKeys.CUSTOMER_TYPE_SHIPPING_METHODS_SHIPPING_METHOD_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_METHODS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.search.SearchForeignKeys.CACHED_EXECUTED_SEARCH_RESULTS_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.search.SearchForeignKeys.SEARCH_RESULTS_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.shipping.ShippingForeignKeys.SHIPPING_METHODS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.customer.CustomerTypeDescriptions.CustomerTypeDescriptions;
import static com.echothree.model.jooq.server.tables.customer.CustomerTypeDetails.CustomerTypeDetails;
import static com.echothree.model.jooq.server.tables.customer.CustomerTypePaymentMethods.CustomerTypePaymentMethods;
import static com.echothree.model.jooq.server.tables.customer.CustomerTypeShippingMethods.CustomerTypeShippingMethods;
import static com.echothree.model.jooq.server.tables.customer.CustomerTypes.CustomerTypes;
import static com.echothree.model.jooq.server.tables.customer.Customers.Customers;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.payment.PaymentMethodDetails.PaymentMethodDetails;
import static com.echothree.model.jooq.server.tables.payment.PaymentMethods.PaymentMethods;
import static com.echothree.model.jooq.server.tables.search.CachedExecutedSearchResults.CachedExecutedSearchResults;
import static com.echothree.model.jooq.server.tables.search.SearchResults.SearchResults;
import static com.echothree.model.jooq.server.tables.shipping.ShippingMethodDetails.ShippingMethodDetails;
import static com.echothree.model.jooq.server.tables.shipping.ShippingMethods.ShippingMethods;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import org.jooq.Select;

@CommandScope
public class CustomerControl
        extends BaseModelControl {
    
    @Inject
    protected ContactListControl contactListControl;


    @Inject
    protected ItemControl itemControl;

    @Inject
    protected OfferControl offerControl;

    @Inject
    protected SearchControl searchControl;

    @Inject
    protected SequenceGeneratorLogic sequenceGeneratorLogic;

    /** Creates a new instance of CustomerControl */
    protected CustomerControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Customer Types
    // --------------------------------------------------------------------------------

    @Inject
    protected CustomerTypeDetailFactory customerTypeDetailFactory;

    @Inject
    protected CustomerTypeFactory customerTypeFactory;

    @Inject
    CustomerTypeTransferCache customerTypeTransferCache;

    public CustomerType createCustomerType(String customerTypeName, Sequence customerSequence, OfferUse defaultOfferUse,
            Term defaultTerm, FreeOnBoard defaultFreeOnBoard, CancellationPolicy defaultCancellationPolicy,
            ReturnPolicy defaultReturnPolicy, WorkflowEntrance defaultCustomerStatus, WorkflowEntrance defaultCustomerCreditStatus,
            GlAccount defaultArGlAccount, Boolean defaultHoldUntilComplete, Boolean defaultAllowBackorders,
            Boolean defaultAllowSubstitutions, Boolean defaultAllowCombiningShipments, Boolean defaultRequireReference,
            Boolean defaultAllowReferenceDuplicates, String defaultReferenceValidationPattern, Boolean defaultTaxable,
            AllocationPriority allocationPriority, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultCustomerType = getDefaultCustomerType();
        var defaultFound = defaultCustomerType != null;

        if(defaultFound && isDefault) {
            var defaultCustomerTypeDetailValue = getDefaultCustomerTypeDetailValueForUpdate();

            defaultCustomerTypeDetailValue.setIsDefault(false);
            updateCustomerTypeFromValue(defaultCustomerTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var customerType = customerTypeFactory.create();
        var customerTypeDetail = customerTypeDetailFactory.create(customerType, customerTypeName,
                customerSequence, defaultOfferUse, defaultTerm, defaultFreeOnBoard, defaultCancellationPolicy, defaultReturnPolicy,
                defaultCustomerStatus, defaultCustomerCreditStatus, defaultArGlAccount, defaultHoldUntilComplete, defaultAllowBackorders,
                defaultAllowSubstitutions, defaultAllowCombiningShipments, defaultRequireReference, defaultAllowReferenceDuplicates,
                defaultReferenceValidationPattern, defaultTaxable, allocationPriority, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);

        // Convert to R/W
        customerType = customerTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE, customerType.getPrimaryKey());
        customerType.setActiveDetail(customerTypeDetail);
        customerType.setLastDetail(customerTypeDetail);
        customerType.store();

        sendEvent(customerType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return customerType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.CustomerType */
    public CustomerType getCustomerTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new CustomerTypePK(entityInstance.getEntityUniqueId());

        return customerTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public CustomerType getCustomerTypeByEntityInstance(EntityInstance entityInstance) {
        return getCustomerTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public CustomerType getCustomerTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getCustomerTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countCustomerTypes() {
        return session.getDslContext()
                .selectCount()
                .from(CustomerTypes)
                .join(CustomerTypeDetails).onKey(CUSTOMER_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCustomerTypesByDefaultOfferUse(OfferUse defaultOfferUse) {
        return session.getDslContext()
                .selectCount()
                .from(CustomerTypes)
                .join(CustomerTypeDetails).onKey(CUSTOMER_TYPES_ACTIVE_DETAIL_FK)
                .where(CustomerTypeDetails.DEFAULT_OFFER_USE.eq(defaultOfferUse.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<CustomerType> getCustomerTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CustomerTypes.fields())
                .from(CustomerTypes)
                .join(CustomerTypeDetails).onKey(CUSTOMER_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(CustomerTypeDetails.SORT_ORDER, CustomerTypeDetails.CUSTOMER_TYPE_NAME),
                    CustomerTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return customerTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CustomerType> getCustomerTypes() {
        return getCustomerTypes(EntityPermission.READ_ONLY);
    }
    
    public List<CustomerType> getCustomerTypesForUpdate() {
        return getCustomerTypes(EntityPermission.READ_WRITE);
    }
    
    public CustomerType getDefaultCustomerType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CustomerTypes.fields())
                .from(CustomerTypes)
                .join(CustomerTypeDetails).onKey(CUSTOMER_TYPES_ACTIVE_DETAIL_FK)
                .where(CustomerTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return customerTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CustomerType getDefaultCustomerType() {
        return getDefaultCustomerType(EntityPermission.READ_ONLY);
    }
    
    public CustomerType getDefaultCustomerTypeForUpdate() {
        return getDefaultCustomerType(EntityPermission.READ_WRITE);
    }
    
    public CustomerTypeDetailValue getDefaultCustomerTypeDetailValueForUpdate() {
        return getDefaultCustomerTypeForUpdate().getLastDetailForUpdate().getCustomerTypeDetailValue().clone();
    }
    
    public CustomerType getCustomerTypeByName(String customerTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CustomerTypes.fields())
                .from(CustomerTypes)
                .join(CustomerTypeDetails).onKey(CUSTOMER_TYPES_ACTIVE_DETAIL_FK)
                .where(CustomerTypeDetails.CUSTOMER_TYPE_NAME.eq(customerTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return customerTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CustomerType getCustomerTypeByName(String customerTypeName) {
        return getCustomerTypeByName(customerTypeName, EntityPermission.READ_ONLY);
    }
    
    public CustomerType getCustomerTypeByNameForUpdate(String customerTypeName) {
        return getCustomerTypeByName(customerTypeName, EntityPermission.READ_WRITE);
    }
    
    public CustomerTypeDetailValue getCustomerTypeDetailValueForUpdate(CustomerType customerType) {
        return customerType == null? null: customerType.getLastDetailForUpdate().getCustomerTypeDetailValue().clone();
    }
    
    public CustomerTypeDetailValue getCustomerTypeDetailValueByNameForUpdate(String customerTypeName) {
        return getCustomerTypeDetailValueForUpdate(getCustomerTypeByNameForUpdate(customerTypeName));
    }
    
    public CustomerTypeChoicesBean getCustomerTypeChoices(String defaultCustomerTypeChoice, Language language,
            boolean allowNullChoice) {
        var customerTypes = getCustomerTypes();
        var size = customerTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultCustomerTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var customerType : customerTypes) {
            var customerTypeDetail = customerType.getLastDetail();
            var label = getBestCustomerTypeDescription(customerType, language);
            var value = customerTypeDetail.getCustomerTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultCustomerTypeChoice != null && defaultCustomerTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && customerTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new CustomerTypeChoicesBean(labels, values, defaultValue);
    }
    
    public CustomerTypeTransfer getCustomerTypeTransfer(UserVisit userVisit, CustomerType customerType) {
        return customerTypeTransferCache.getCustomerTypeTransfer(userVisit, customerType);
    }

    public List<CustomerTypeTransfer> getCustomerTypeTransfers(UserVisit userVisit, Collection<CustomerType> customerTypes) {
        List<CustomerTypeTransfer> customerTypeTransfers = null;

        if(customerTypes != null) {

            customerTypeTransfers = new ArrayList<>(customerTypes.size());

            for(var customerType : customerTypes) {
                customerTypeTransfers.add(customerTypeTransferCache.getCustomerTypeTransfer(userVisit, customerType));
            }
        }

        return customerTypeTransfers;
    }

    public List<CustomerTypeTransfer> getCustomerTypeTransfers(UserVisit userVisit) {
        return getCustomerTypeTransfers(userVisit, getCustomerTypes());
    }

    private void updateCustomerTypeFromValue(CustomerTypeDetailValue customerTypeDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(customerTypeDetailValue.hasBeenModified()) {
            var customerType = customerTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE, customerTypeDetailValue.getCustomerTypePK());
            var customerTypeDetail = customerType.getActiveDetailForUpdate();

            customerTypeDetail.setThruTime(session.getStartTime());
            customerTypeDetail.store();

            var customerTypePK = customerTypeDetail.getCustomerTypePK();
            var customerTypeName = customerTypeDetailValue.getCustomerTypeName();
            var customerSequencePK = customerTypeDetailValue.getCustomerSequencePK();
            var defaultOfferUsePK = customerTypeDetailValue.getDefaultOfferUsePK();
            var defaultTermPK = customerTypeDetailValue.getDefaultTermPK();
            var defaultFreeOnBoardPK = customerTypeDetailValue.getDefaultFreeOnBoardPK();
            var defaultCancellationPolicyPK = customerTypeDetailValue.getDefaultCancellationPolicyPK();
            var defaultReturnPolicyPK = customerTypeDetailValue.getDefaultReturnPolicyPK();
            var defaultCustomerStatusPK = customerTypeDetailValue.getDefaultCustomerStatusPK();
            var defaultCustomerCreditStatusPK = customerTypeDetailValue.getDefaultCustomerCreditStatusPK();
            var defaultArGlAccountPK = customerTypeDetailValue.getDefaultArGlAccountPK();
            var defaultHoldUntilComplete = customerTypeDetailValue.getDefaultHoldUntilComplete();
            var defaultAllowBackorders = customerTypeDetailValue.getDefaultAllowBackorders();
            var defaultAllowSubstitutions = customerTypeDetailValue.getDefaultAllowSubstitutions();
            var defaultAllowCombiningShipments = customerTypeDetailValue.getDefaultAllowCombiningShipments();
            var defaultRequireReference = customerTypeDetailValue.getDefaultRequireReference();
            var defaultAllowReferenceDuplicates = customerTypeDetailValue.getDefaultAllowReferenceDuplicates();
            var defaultReferenceValidationPattern = customerTypeDetailValue.getDefaultReferenceValidationPattern();
            var defaultTaxable = customerTypeDetailValue.getDefaultTaxable();
            var allocationPriority = customerTypeDetailValue.getAllocationPriorityPK();
            var isDefault = customerTypeDetailValue.getIsDefault();
            var sortOrder = customerTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultCustomerType = getDefaultCustomerType();
                var defaultFound = defaultCustomerType != null && !defaultCustomerType.equals(customerType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultCustomerTypeDetailValue = getDefaultCustomerTypeDetailValueForUpdate();

                    defaultCustomerTypeDetailValue.setIsDefault(false);
                    updateCustomerTypeFromValue(defaultCustomerTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            customerTypeDetail = customerTypeDetailFactory.create(customerTypePK, customerTypeName, customerSequencePK,
                    defaultOfferUsePK, defaultTermPK, defaultFreeOnBoardPK, defaultCancellationPolicyPK, defaultReturnPolicyPK,
                    defaultCustomerStatusPK, defaultCustomerCreditStatusPK, defaultArGlAccountPK, defaultHoldUntilComplete,
                    defaultAllowBackorders, defaultAllowSubstitutions, defaultAllowCombiningShipments, defaultRequireReference,
                    defaultAllowReferenceDuplicates, defaultReferenceValidationPattern, defaultTaxable, allocationPriority,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            customerType.setActiveDetail(customerTypeDetail);
            customerType.setLastDetail(customerTypeDetail);

            sendEvent(customerTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateCustomerTypeFromValue(CustomerTypeDetailValue customerTypeDetailValue, BasePK updatedBy) {
        updateCustomerTypeFromValue(customerTypeDetailValue, true, updatedBy);
    }
    
    public void deleteCustomerType(CustomerType customerType, BasePK deletedBy) {
        
        contactListControl.deleteCustomerTypeContactListGroupsByCustomerType(customerType, deletedBy);
        contactListControl.deleteCustomerTypeContactListsByCustomerType(customerType, deletedBy);
        itemControl.deleteItemShippingTimesByCustomerType(customerType, deletedBy);
        offerControl.deleteOfferCustomerTypesByCustomerType(customerType, deletedBy);
        deleteCustomerTypePaymentMethodsByCustomerType(customerType, deletedBy);
        deleteCustomerTypeDescriptionsByCustomerType(customerType, deletedBy);

        var customerTypeDetail = customerType.getLastDetailForUpdate();
        customerTypeDetail.setThruTime(session.getStartTime());
        customerType.setActiveDetail(null);
        customerType.store();
        
        // Check for default, and pick one if necessary
        var defaultCustomerType = getDefaultCustomerType();
        if(defaultCustomerType == null) {
            var customerTypes = getCustomerTypesForUpdate();
            
            if(!customerTypes.isEmpty()) {
                var iter = customerTypes.iterator();
                if(iter.hasNext()) {
                    defaultCustomerType = iter.next();
                }
                var customerTypeDetailValue = Objects.requireNonNull(defaultCustomerType).getLastDetailForUpdate().getCustomerTypeDetailValue().clone();
                
                customerTypeDetailValue.setIsDefault(true);
                updateCustomerTypeFromValue(customerTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(customerType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Customer Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected CustomerTypeDescriptionFactory customerTypeDescriptionFactory;

    @Inject
    CustomerTypeDescriptionTransferCache customerTypeDescriptionTransferCache;

    public CustomerTypeDescription createCustomerTypeDescription(CustomerType customerType, Language language, String description,
            BasePK createdBy) {
        var customerTypeDescription = customerTypeDescriptionFactory.create(customerType,
                language, description,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(customerType.getPrimaryKey(), EventTypes.MODIFY, customerTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return customerTypeDescription;
    }
    
    private CustomerTypeDescription getCustomerTypeDescription(CustomerType customerType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CustomerTypeDescriptions.fields())
                .from(CustomerTypeDescriptions)
                .where(CustomerTypeDescriptions.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                        CustomerTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        CustomerTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return customerTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CustomerTypeDescription getCustomerTypeDescription(CustomerType customerType, Language language) {
        return getCustomerTypeDescription(customerType, language, EntityPermission.READ_ONLY);
    }
    
    public CustomerTypeDescription getCustomerTypeDescriptionForUpdate(CustomerType customerType, Language language) {
        return getCustomerTypeDescription(customerType, language, EntityPermission.READ_WRITE);
    }
    
    public CustomerTypeDescriptionValue getCustomerTypeDescriptionValue(CustomerTypeDescription customerTypeDescription) {
        return customerTypeDescription == null? null: customerTypeDescription.getCustomerTypeDescriptionValue().clone();
    }
    
    public CustomerTypeDescriptionValue getCustomerTypeDescriptionValueForUpdate(CustomerType customerType, Language language) {
        return getCustomerTypeDescriptionValue(getCustomerTypeDescriptionForUpdate(customerType, language));
    }
    
    private List<CustomerTypeDescription> getCustomerTypeDescriptionsByCustomerType(CustomerType customerType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CustomerTypeDescriptions.fields())
                    .from(CustomerTypeDescriptions)
                    .join(Languages).onKey(CUSTOMER_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(CustomerTypeDescriptions.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                            CustomerTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    CustomerTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CustomerTypeDescriptions.fields())
                    .from(CustomerTypeDescriptions)
                    .where(CustomerTypeDescriptions.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                            CustomerTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return customerTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CustomerTypeDescription> getCustomerTypeDescriptionsByCustomerType(CustomerType customerType) {
        return getCustomerTypeDescriptionsByCustomerType(customerType, EntityPermission.READ_ONLY);
    }
    
    public List<CustomerTypeDescription> getCustomerTypeDescriptionsByCustomerTypeForUpdate(CustomerType customerType) {
        return getCustomerTypeDescriptionsByCustomerType(customerType, EntityPermission.READ_WRITE);
    }
    
    public String getBestCustomerTypeDescription(CustomerType customerType, Language language) {
        String description;
        var customerTypeDescription = getCustomerTypeDescription(customerType, language);
        
        if(customerTypeDescription == null && !language.getIsDefault()) {
            customerTypeDescription = getCustomerTypeDescription(customerType, partyControl.getDefaultLanguage());
        }
        
        if(customerTypeDescription == null) {
            description = customerType.getLastDetail().getCustomerTypeName();
        } else {
            description = customerTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public CustomerTypeDescriptionTransfer getCustomerTypeDescriptionTransfer(UserVisit userVisit, CustomerTypeDescription customerTypeDescription) {
        return customerTypeDescriptionTransferCache.getCustomerTypeDescriptionTransfer(userVisit, customerTypeDescription);
    }
    
    public List<CustomerTypeDescriptionTransfer> getCustomerTypeDescriptionTransfers(UserVisit userVisit, CustomerType customerType) {
        var customerTypeDescriptions = getCustomerTypeDescriptionsByCustomerType(customerType);
        List<CustomerTypeDescriptionTransfer> customerTypeDescriptionTransfers = null;
        
        if(customerTypeDescriptions != null) {
            
            customerTypeDescriptionTransfers = new ArrayList<>(customerTypeDescriptions.size());
            
            for(var customerTypeDescription : customerTypeDescriptions) {
                customerTypeDescriptionTransfers.add(customerTypeDescriptionTransferCache.getCustomerTypeDescriptionTransfer(userVisit, customerTypeDescription));
            }
        }
        
        return customerTypeDescriptionTransfers;
    }
    
    public void updateCustomerTypeDescriptionFromValue(CustomerTypeDescriptionValue customerTypeDescriptionValue, BasePK updatedBy) {
        if(customerTypeDescriptionValue.hasBeenModified()) {
            var customerTypeDescription = customerTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     customerTypeDescriptionValue.getPrimaryKey());
            
            customerTypeDescription.setThruTime(session.getStartTime());
            customerTypeDescription.store();

            var customerType = customerTypeDescription.getCustomerType();
            var language = customerTypeDescription.getLanguage();
            var description = customerTypeDescriptionValue.getDescription();
            
            customerTypeDescription = customerTypeDescriptionFactory.create(customerType, language,
                    description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(customerType.getPrimaryKey(), EventTypes.MODIFY, customerTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteCustomerTypeDescription(CustomerTypeDescription customerTypeDescription, BasePK deletedBy) {
        customerTypeDescription.setThruTime(session.getStartTime());
        
        sendEvent(customerTypeDescription.getCustomerTypePK(), EventTypes.MODIFY, customerTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteCustomerTypeDescriptionsByCustomerType(CustomerType customerType, BasePK deletedBy) {
        var customerTypeDescriptions = getCustomerTypeDescriptionsByCustomerTypeForUpdate(customerType);
        
        customerTypeDescriptions.forEach((customerTypeDescription) -> 
                deleteCustomerTypeDescription(customerTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Customers
    // --------------------------------------------------------------------------------

    @Inject
    protected CustomerFactory customerFactory;

    @Inject
    CustomerTransferCache customerTransferCache;

    public Customer createCustomer(Party party, CustomerType customerType, OfferUse initialOfferUse,
            CancellationPolicy cancellationPolicy, ReturnPolicy returnPolicy, GlAccount arGlAccount,
            Boolean holdUntilComplete, Boolean allowBackorders, Boolean allowSubstitutions,
            Boolean allowCombiningShipments, Boolean requireReference, Boolean allowReferenceDuplicates,
            String referenceValidationPattern, BasePK createdBy) {
        var customerName = sequenceGeneratorLogic.getNextSequenceValue(null, SequenceTypes.CUSTOMER.name());
        var customer = customerFactory.create(party, customerName, customerType, initialOfferUse,
                cancellationPolicy, returnPolicy, arGlAccount, holdUntilComplete, allowBackorders, allowSubstitutions,
                allowCombiningShipments, requireReference, allowReferenceDuplicates, referenceValidationPattern,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, customer.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return customer;
    }

    public long countCustomers() {
        return session.getDslContext()
                .selectCount()
                .from(Customers)
                .where(Customers.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCustomersByInitialOfferUse(OfferUse initialOfferUse) {
        return session.getDslContext()
                .selectCount()
                .from(Customers)
                .where(Customers.INITIAL_OFFER_USE.eq(initialOfferUse.getPrimaryKey()),
                        Customers.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<Customer> getCustomers(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Customers.fields())
                .from(Customers)
                .where(Customers.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(Customers.CUSTOMER_NAME),
                    CustomerFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return customerFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Customer> getCustomers() {
        return getCustomers(EntityPermission.READ_ONLY);
    }

    public List<Customer> getCustomersForUpdate() {
        return getCustomers(EntityPermission.READ_WRITE);
    }
    
    public Customer getCustomer(Party party, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Customers.fields())
                .from(Customers)
                .where(Customers.PARTY.eq(party.getPrimaryKey()),
                        Customers.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return customerFactory.getEntityFromQuery(entityPermission, query);
    }

    public Customer getCustomer(Party party) {
        return getCustomer(party, EntityPermission.READ_ONLY);
    }

    public Customer getCustomerForUpdate(Party party) {
        return getCustomer(party, EntityPermission.READ_WRITE);
    }
    
    private Customer getCustomerByName(String customerName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Customers.fields())
                .from(Customers)
                .where(Customers.CUSTOMER_NAME.eq(customerName),
                        Customers.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return customerFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public Customer getCustomerByName(String customerName) {
        return getCustomerByName(customerName, EntityPermission.READ_ONLY);
    }
    
    public Customer getCustomerByNameForUpdate(String customerName) {
        return getCustomerByName(customerName, EntityPermission.READ_WRITE);
    }
    
    public CustomerValue getCustomerValue(Customer customer) {
        return customer == null? null: customer.getCustomerValue().clone();
    }
    
    public CustomerValue getCustomerValueByNameForUpdate(String customerName) {
        return getCustomerValue(getCustomerByNameForUpdate(customerName));
    }
    
    public CustomerTransfer getCustomerTransfer(UserVisit userVisit, Customer customer) {
        return customerTransferCache.getTransfer(userVisit, customer);
    }
    
    public CustomerTransfer getCustomerTransfer(UserVisit userVisit, Party party) {
        return customerTransferCache.getTransfer(userVisit, party);
    }

    public List<CustomerTransfer> getCustomerTransfers(UserVisit userVisit, Collection<Customer> customers) {
        var customerTransfers = new ArrayList<CustomerTransfer>(customers.size());

        customers.forEach((customer) ->
                customerTransfers.add(customerTransferCache.getTransfer(userVisit, customer))
        );

        return customerTransfers;
    }

    public List<CustomerTransfer> getCustomerTransfers(UserVisit userVisit) {
        return getCustomerTransfers(userVisit, getCustomers());
    }

    public void updateCustomerFromValue(CustomerValue customerValue, BasePK updatedBy) {
        if(customerValue.hasBeenModified()) {
            var customer = customerFactory.getEntityFromPK(EntityPermission.READ_WRITE, customerValue.getPrimaryKey());

            customer.setThruTime(session.getStartTime());
            customer.store();

            var partyPK = customer.getPartyPK(); // Not updated
            var customerName = customerValue.getCustomerName();
            var customerTypePK = customerValue.getCustomerTypePK();
            var initialOfferUsePK = customerValue.getInitialOfferUsePK();
            var cancellationPolicyPK = customerValue.getCancellationPolicyPK();
            var returnPolicyPK = customerValue.getReturnPolicyPK();
            var arGlAccountPK = customerValue.getArGlAccountPK();
            var holdUntilComplete = customerValue.getHoldUntilComplete();
            var allowBackorders = customerValue.getAllowBackorders();
            var allowSubstitutions = customerValue.getAllowSubstitutions();
            var allowCombiningShipments = customerValue.getAllowCombiningShipments();
            var requireReference = customerValue.getRequireReference();
            var allowReferenceDuplicates = customerValue.getAllowReferenceDuplicates();
            var referenceValidationPattern = customerValue.getReferenceValidationPattern();

            customer = customerFactory.create(partyPK, customerName, customerTypePK, initialOfferUsePK, cancellationPolicyPK, returnPolicyPK,
                    arGlAccountPK, holdUntilComplete, allowBackorders, allowSubstitutions, allowCombiningShipments, requireReference, allowReferenceDuplicates,
                    referenceValidationPattern, session.getStartTime(), Session.MAX_TIME);

            sendEvent(partyPK, EventTypes.MODIFY, customer.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public CustomerStatusChoicesBean getCustomerStatusChoices(String defaultCustomerStatusChoice, Language language,
            boolean allowNullChoice, Party customerParty, PartyPK partyPK) {
        var customerStatusChoicesBean = new CustomerStatusChoicesBean();
        
        if(customerParty == null) {
            workflowControl.getWorkflowEntranceChoices(customerStatusChoicesBean, defaultCustomerStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(CustomerStatusConstants.Workflow_CUSTOMER_STATUS), partyPK);
        } else {
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(customerParty.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(CustomerStatusConstants.Workflow_CUSTOMER_STATUS,
                    entityInstance);
            
            workflowControl.getWorkflowDestinationChoices(customerStatusChoicesBean, defaultCustomerStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }
        
        return customerStatusChoicesBean;
    }
    
    public void setCustomerStatus(ExecutionErrorAccumulator eea, Party party, String customerStatusChoice, PartyPK modifiedBy) {
        var entityInstance = getEntityInstanceByBaseEntity(party);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(CustomerStatusConstants.Workflow_CUSTOMER_STATUS,
                entityInstance);
        var workflowDestination = customerStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), customerStatusChoice);
        
        if(workflowDestination != null || customerStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownCustomerStatusChoice.name(), customerStatusChoice);
        }
    }
    
    public CustomerCreditStatusChoicesBean getCustomerCreditStatusChoices(String defaultCustomerCreditStatusChoice, Language language,
            boolean allowNullChoice, Party customerParty, PartyPK partyPK) {
        var customerCreditStatusChoicesBean = new CustomerCreditStatusChoicesBean();
        
        if(customerParty == null) {
            workflowControl.getWorkflowEntranceChoices(customerCreditStatusChoicesBean, defaultCustomerCreditStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(CustomerCreditStatusConstants.Workflow_CUSTOMER_CREDIT_STATUS), partyPK);
        } else {
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(customerParty.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(CustomerCreditStatusConstants.Workflow_CUSTOMER_CREDIT_STATUS,
                    entityInstance);
            
            workflowControl.getWorkflowDestinationChoices(customerCreditStatusChoicesBean, defaultCustomerCreditStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }
        
        return customerCreditStatusChoicesBean;
    }
    
    public void setCustomerCreditStatus(ExecutionErrorAccumulator eea, Party party, String customerCreditStatusChoice, PartyPK modifiedBy) {
        var entityInstance = getEntityInstanceByBaseEntity(party);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(CustomerCreditStatusConstants.Workflow_CUSTOMER_CREDIT_STATUS,
                entityInstance);
        var workflowDestination = customerCreditStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), customerCreditStatusChoice);
        
        if(workflowDestination != null || customerCreditStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownCustomerCreditStatusChoice.name(), customerCreditStatusChoice);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Customer Type Payment Methods
    // --------------------------------------------------------------------------------

    @Inject
    protected CustomerTypePaymentMethodFactory customerTypePaymentMethodFactory;

    @Inject
    CustomerTypePaymentMethodTransferCache customerTypePaymentMethodTransferCache;

    public CustomerTypePaymentMethod createCustomerTypePaymentMethod(CustomerType customerType, PaymentMethod paymentMethod, Integer defaultSelectionPriority,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultCustomerTypePaymentMethod = getDefaultCustomerTypePaymentMethod(customerType);
        var defaultFound = defaultCustomerTypePaymentMethod != null;
        
        if(defaultFound && isDefault) {
            var defaultCustomerTypePaymentMethodValue = getDefaultCustomerTypePaymentMethodValueForUpdate(customerType);
            
            defaultCustomerTypePaymentMethodValue.setIsDefault(false);
            updateCustomerTypePaymentMethodFromValue(defaultCustomerTypePaymentMethodValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var customerTypePaymentMethod = customerTypePaymentMethodFactory.create( customerType, paymentMethod,
                defaultSelectionPriority, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(customerType.getPrimaryKey(), EventTypes.MODIFY, customerTypePaymentMethod.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return customerTypePaymentMethod;
    }
    
    public long countCustomerTypePaymentMethodsByCustomerType(CustomerType customerType) {
        return session.getDslContext()
                .selectCount()
                .from(CustomerTypePaymentMethods)
                .where(CustomerTypePaymentMethods.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                        CustomerTypePaymentMethods.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCustomerTypePaymentMethodsByPaymentMethod(PaymentMethod paymentMethod) {
        return session.getDslContext()
                .selectCount()
                .from(CustomerTypePaymentMethods)
                .where(CustomerTypePaymentMethods.PAYMENT_METHOD.eq(paymentMethod.getPrimaryKey()),
                        CustomerTypePaymentMethods.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public boolean getCustomerTypePaymentMethodExists(CustomerType customerType, PaymentMethod paymentMethod) {
        return session.getDslContext()
                .selectCount()
                .from(CustomerTypePaymentMethods)
                .where(CustomerTypePaymentMethods.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                        CustomerTypePaymentMethods.PAYMENT_METHOD.eq(paymentMethod.getPrimaryKey()),
                        CustomerTypePaymentMethods.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L) == 1L;
    }

    public CustomerTypePaymentMethod getCustomerTypePaymentMethod(CustomerType customerType, PaymentMethod paymentMethod,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CustomerTypePaymentMethods.fields())
                .from(CustomerTypePaymentMethods)
                .where(CustomerTypePaymentMethods.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                        CustomerTypePaymentMethods.PAYMENT_METHOD.eq(paymentMethod.getPrimaryKey()),
                        CustomerTypePaymentMethods.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return customerTypePaymentMethodFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CustomerTypePaymentMethod getCustomerTypePaymentMethod(CustomerType customerType, PaymentMethod paymentMethod) {
        return getCustomerTypePaymentMethod(customerType, paymentMethod, EntityPermission.READ_ONLY);
    }
    
    public CustomerTypePaymentMethod getCustomerTypePaymentMethodForUpdate(CustomerType customerType, PaymentMethod paymentMethod) {
        return getCustomerTypePaymentMethod(customerType, paymentMethod, EntityPermission.READ_WRITE);
    }
    
    public CustomerTypePaymentMethodValue getCustomerTypePaymentMethodValue(CustomerTypePaymentMethod customerTypePaymentMethod) {
        return customerTypePaymentMethod == null? null: customerTypePaymentMethod.getCustomerTypePaymentMethodValue().clone();
    }
    
    public CustomerTypePaymentMethodValue getCustomerTypePaymentMethodValueForUpdate(CustomerType customerType, PaymentMethod paymentMethod) {
        return getCustomerTypePaymentMethodValue(getCustomerTypePaymentMethodForUpdate(customerType, paymentMethod));
    }
    
    private CustomerTypePaymentMethod getDefaultCustomerTypePaymentMethod(CustomerType customerType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CustomerTypePaymentMethods.fields())
                .from(CustomerTypePaymentMethods)
                .where(CustomerTypePaymentMethods.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                        CustomerTypePaymentMethods.IS_DEFAULT.eq(true),
                        CustomerTypePaymentMethods.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return customerTypePaymentMethodFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CustomerTypePaymentMethod getDefaultCustomerTypePaymentMethod(CustomerType customerType) {
        return getDefaultCustomerTypePaymentMethod(customerType, EntityPermission.READ_ONLY);
    }
    
    public CustomerTypePaymentMethod getDefaultCustomerTypePaymentMethodForUpdate(CustomerType customerType) {
        return getDefaultCustomerTypePaymentMethod(customerType, EntityPermission.READ_WRITE);
    }
    
    public CustomerTypePaymentMethodValue getDefaultCustomerTypePaymentMethodValueForUpdate(CustomerType customerType) {
        return getDefaultCustomerTypePaymentMethodForUpdate(customerType).getCustomerTypePaymentMethodValue().clone();
    }
    
    private List<CustomerTypePaymentMethod> getCustomerTypePaymentMethodsByCustomerType(CustomerType customerType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CustomerTypePaymentMethods.fields())
                    .from(CustomerTypePaymentMethods)
                    .join(PaymentMethods).onKey(CUSTOMER_TYPE_PAYMENT_METHODS_PAYMENT_METHOD_FK)
                    .join(PaymentMethodDetails).onKey(PAYMENT_METHODS_ACTIVE_DETAIL_FK)
                    .where(CustomerTypePaymentMethods.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                            CustomerTypePaymentMethods.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PaymentMethodDetails.SORT_ORDER, PaymentMethodDetails.PAYMENT_METHOD_NAME),
                    CustomerTypePaymentMethodFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CustomerTypePaymentMethods.fields())
                    .from(CustomerTypePaymentMethods)
                    .where(CustomerTypePaymentMethods.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                            CustomerTypePaymentMethods.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return customerTypePaymentMethodFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CustomerTypePaymentMethod> getCustomerTypePaymentMethodsByCustomerType(CustomerType customerType) {
        return getCustomerTypePaymentMethodsByCustomerType(customerType, EntityPermission.READ_ONLY);
    }
    
    public List<CustomerTypePaymentMethod> getCustomerTypePaymentMethodsByCustomerTypeForUpdate(CustomerType customerType) {
        return getCustomerTypePaymentMethodsByCustomerType(customerType, EntityPermission.READ_WRITE);
    }
    
    private List<CustomerTypePaymentMethod> getCustomerTypePaymentMethodsByPaymentMethod(PaymentMethod paymentMethod,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CustomerTypePaymentMethods.fields())
                    .from(CustomerTypePaymentMethods)
                    .join(CustomerTypes).onKey(CUSTOMER_TYPE_PAYMENT_METHODS_CUSTOMER_TYPE_FK)
                    .join(CustomerTypeDetails).onKey(CUSTOMER_TYPES_ACTIVE_DETAIL_FK)
                    .where(CustomerTypePaymentMethods.PAYMENT_METHOD.eq(paymentMethod.getPrimaryKey()),
                            CustomerTypePaymentMethods.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(CustomerTypeDetails.SORT_ORDER, CustomerTypeDetails.CUSTOMER_TYPE_NAME),
                    CustomerTypePaymentMethodFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CustomerTypePaymentMethods.fields())
                    .from(CustomerTypePaymentMethods)
                    .where(CustomerTypePaymentMethods.PAYMENT_METHOD.eq(paymentMethod.getPrimaryKey()),
                            CustomerTypePaymentMethods.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return customerTypePaymentMethodFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CustomerTypePaymentMethod> getCustomerTypePaymentMethodsByPaymentMethod(PaymentMethod paymentMethod) {
        return getCustomerTypePaymentMethodsByPaymentMethod(paymentMethod, EntityPermission.READ_ONLY);
    }
    
    public List<CustomerTypePaymentMethod> getCustomerTypePaymentMethodsByPaymentMethodForUpdate(PaymentMethod paymentMethod) {
        return getCustomerTypePaymentMethodsByPaymentMethod(paymentMethod, EntityPermission.READ_WRITE);
    }
    
    public CustomerTypePaymentMethodTransfer getCustomerTypePaymentMethodTransfer(UserVisit userVisit, CustomerTypePaymentMethod customerTypePaymentMethod) {
        return customerTypePaymentMethodTransferCache.getCustomerTypePaymentMethodTransfer(userVisit, customerTypePaymentMethod);
    }
    
    public List<CustomerTypePaymentMethodTransfer> getCustomerTypePaymentMethodTransfers(UserVisit userVisit,
            Collection<CustomerTypePaymentMethod> customerTypePaymentMethods) {
        List<CustomerTypePaymentMethodTransfer> customerTypePaymentMethodTransfers = new ArrayList<>(customerTypePaymentMethods.size());

        for(var customerTypePaymentMethod : customerTypePaymentMethods) {
            customerTypePaymentMethodTransfers.add(customerTypePaymentMethodTransferCache.getCustomerTypePaymentMethodTransfer(userVisit, customerTypePaymentMethod));
        }

        return customerTypePaymentMethodTransfers;
    }
    
    public List<CustomerTypePaymentMethodTransfer> getCustomerTypePaymentMethodTransfersByPaymentMethod(UserVisit userVisit, PaymentMethod paymentMethod) {
        return getCustomerTypePaymentMethodTransfers(userVisit, getCustomerTypePaymentMethodsByPaymentMethod(paymentMethod));
    }
    
    public List<CustomerTypePaymentMethodTransfer> getCustomerTypePaymentMethodTransfersByCustomerType(UserVisit userVisit, CustomerType customerType) {
        return getCustomerTypePaymentMethodTransfers(userVisit, getCustomerTypePaymentMethodsByCustomerType(customerType));
    }
    
    private void updateCustomerTypePaymentMethodFromValue(CustomerTypePaymentMethodValue customerTypePaymentMethodValue,
            boolean checkDefault, BasePK updatedBy) {
        if(customerTypePaymentMethodValue.hasBeenModified()) {
            var customerTypePaymentMethod = customerTypePaymentMethodFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     customerTypePaymentMethodValue.getPrimaryKey());
            
            customerTypePaymentMethod.setThruTime(session.getStartTime());
            customerTypePaymentMethod.store();

            var customerTypePK = customerTypePaymentMethod.getCustomerTypePK();
            var customerType = customerTypePaymentMethod.getCustomerType();
            var paymentMethodPK = customerTypePaymentMethod.getPaymentMethodPK();
            var defaultSelectionPriority = customerTypePaymentMethodValue.getDefaultSelectionPriority();
            var isDefault = customerTypePaymentMethodValue.getIsDefault();
            var sortOrder = customerTypePaymentMethodValue.getSortOrder();
            
            if(checkDefault) {
                var defaultCustomerTypePaymentMethod = getDefaultCustomerTypePaymentMethod(customerType);
                var defaultFound = defaultCustomerTypePaymentMethod != null && !defaultCustomerTypePaymentMethod.equals(customerTypePaymentMethod);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultCustomerTypePaymentMethodValue = getDefaultCustomerTypePaymentMethodValueForUpdate(customerType);
                    
                    defaultCustomerTypePaymentMethodValue.setIsDefault(false);
                    updateCustomerTypePaymentMethodFromValue(defaultCustomerTypePaymentMethodValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            customerTypePaymentMethod = customerTypePaymentMethodFactory.create(customerTypePK, paymentMethodPK, defaultSelectionPriority,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(customerTypePK, EventTypes.MODIFY, customerTypePaymentMethod.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateCustomerTypePaymentMethodFromValue(CustomerTypePaymentMethodValue customerTypePaymentMethodValue, BasePK updatedBy) {
        updateCustomerTypePaymentMethodFromValue(customerTypePaymentMethodValue, true, updatedBy);
    }
    
    public void deleteCustomerTypePaymentMethod(CustomerTypePaymentMethod customerTypePaymentMethod, BasePK deletedBy) {
        customerTypePaymentMethod.setThruTime(session.getStartTime());
        customerTypePaymentMethod.store();
        
        // Check for default, and pick one if necessary
        var customerType = customerTypePaymentMethod.getCustomerType();
        var defaultCustomerTypePaymentMethod = getDefaultCustomerTypePaymentMethod(customerType);
        if(defaultCustomerTypePaymentMethod == null) {
            var customerTypePaymentMethods = getCustomerTypePaymentMethodsByCustomerTypeForUpdate(customerType);
            
            if(!customerTypePaymentMethods.isEmpty()) {
                var iter = customerTypePaymentMethods.iterator();
                if(iter.hasNext()) {
                    defaultCustomerTypePaymentMethod = iter.next();
                }
                var customerTypePaymentMethodValue = defaultCustomerTypePaymentMethod.getCustomerTypePaymentMethodValue().clone();
                
                customerTypePaymentMethodValue.setIsDefault(true);
                updateCustomerTypePaymentMethodFromValue(customerTypePaymentMethodValue, false, deletedBy);
            }
        }
        
        sendEvent(customerTypePaymentMethod.getCustomerTypePK(), EventTypes.MODIFY, customerTypePaymentMethod.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteCustomerTypePaymentMethods(List<CustomerTypePaymentMethod> customerTypePaymentMethods, BasePK deletedBy) {
        customerTypePaymentMethods.forEach((customerTypePaymentMethod) -> 
                deleteCustomerTypePaymentMethod(customerTypePaymentMethod, deletedBy)
        );
    }
    
    public void deleteCustomerTypePaymentMethodsByCustomerType(CustomerType customerType, BasePK deletedBy) {
        deleteCustomerTypePaymentMethods(getCustomerTypePaymentMethodsByCustomerTypeForUpdate(customerType), deletedBy);
    }
    
    public void deleteCustomerTypePaymentMethodsByPaymentMethod(PaymentMethod paymentMethod, BasePK deletedBy) {
        deleteCustomerTypePaymentMethods(getCustomerTypePaymentMethodsByPaymentMethodForUpdate(paymentMethod), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Customer Type Shipping Methods
    // --------------------------------------------------------------------------------

    @Inject
    protected CustomerTypeShippingMethodFactory customerTypeShippingMethodFactory;

    @Inject
    CustomerTypeShippingMethodTransferCache customerTypeShippingMethodTransferCache;

    public CustomerTypeShippingMethod createCustomerTypeShippingMethod(CustomerType customerType, ShippingMethod shippingMethod, Integer defaultSelectionPriority,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultCustomerTypeShippingMethod = getDefaultCustomerTypeShippingMethod(customerType);
        var defaultFound = defaultCustomerTypeShippingMethod != null;
        
        if(defaultFound && isDefault) {
            var defaultCustomerTypeShippingMethodValue = getDefaultCustomerTypeShippingMethodValueForUpdate(customerType);
            
            defaultCustomerTypeShippingMethodValue.setIsDefault(false);
            updateCustomerTypeShippingMethodFromValue(defaultCustomerTypeShippingMethodValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var customerTypeShippingMethod = customerTypeShippingMethodFactory.create( customerType, shippingMethod,
                defaultSelectionPriority, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(customerType.getPrimaryKey(), EventTypes.MODIFY, customerTypeShippingMethod.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return customerTypeShippingMethod;
    }
    
    public long countCustomerTypeShippingMethodsByCustomerType(CustomerType customerType) {
        return session.getDslContext()
                .selectCount()
                .from(CustomerTypeShippingMethods)
                .where(CustomerTypeShippingMethods.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                        CustomerTypeShippingMethods.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCustomerTypeShippingMethodsByShippingMethod(ShippingMethod shippingMethod) {
        return session.getDslContext()
                .selectCount()
                .from(CustomerTypeShippingMethods)
                .where(CustomerTypeShippingMethods.SHIPPING_METHOD.eq(shippingMethod.getPrimaryKey()),
                        CustomerTypeShippingMethods.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public boolean getCustomerTypeShippingMethodExists(CustomerType customerType, ShippingMethod shippingMethod) {
        return session.getDslContext()
                .selectCount()
                .from(CustomerTypeShippingMethods)
                .where(CustomerTypeShippingMethods.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                        CustomerTypeShippingMethods.SHIPPING_METHOD.eq(shippingMethod.getPrimaryKey()),
                        CustomerTypeShippingMethods.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L) == 1L;
    }

    public CustomerTypeShippingMethod getCustomerTypeShippingMethod(CustomerType customerType, ShippingMethod shippingMethod,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CustomerTypeShippingMethods.fields())
                .from(CustomerTypeShippingMethods)
                .where(CustomerTypeShippingMethods.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                        CustomerTypeShippingMethods.SHIPPING_METHOD.eq(shippingMethod.getPrimaryKey()),
                        CustomerTypeShippingMethods.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return customerTypeShippingMethodFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CustomerTypeShippingMethod getCustomerTypeShippingMethod(CustomerType customerType, ShippingMethod shippingMethod) {
        return getCustomerTypeShippingMethod(customerType, shippingMethod, EntityPermission.READ_ONLY);
    }
    
    public CustomerTypeShippingMethod getCustomerTypeShippingMethodForUpdate(CustomerType customerType, ShippingMethod shippingMethod) {
        return getCustomerTypeShippingMethod(customerType, shippingMethod, EntityPermission.READ_WRITE);
    }
    
    public CustomerTypeShippingMethodValue getCustomerTypeShippingMethodValue(CustomerTypeShippingMethod customerTypeShippingMethod) {
        return customerTypeShippingMethod == null? null: customerTypeShippingMethod.getCustomerTypeShippingMethodValue().clone();
    }
    
    public CustomerTypeShippingMethodValue getCustomerTypeShippingMethodValueForUpdate(CustomerType customerType, ShippingMethod shippingMethod) {
        return getCustomerTypeShippingMethodValue(getCustomerTypeShippingMethodForUpdate(customerType, shippingMethod));
    }
    
    private CustomerTypeShippingMethod getDefaultCustomerTypeShippingMethod(CustomerType customerType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CustomerTypeShippingMethods.fields())
                .from(CustomerTypeShippingMethods)
                .where(CustomerTypeShippingMethods.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                        CustomerTypeShippingMethods.IS_DEFAULT.eq(true),
                        CustomerTypeShippingMethods.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return customerTypeShippingMethodFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CustomerTypeShippingMethod getDefaultCustomerTypeShippingMethod(CustomerType customerType) {
        return getDefaultCustomerTypeShippingMethod(customerType, EntityPermission.READ_ONLY);
    }
    
    public CustomerTypeShippingMethod getDefaultCustomerTypeShippingMethodForUpdate(CustomerType customerType) {
        return getDefaultCustomerTypeShippingMethod(customerType, EntityPermission.READ_WRITE);
    }
    
    public CustomerTypeShippingMethodValue getDefaultCustomerTypeShippingMethodValueForUpdate(CustomerType customerType) {
        return getDefaultCustomerTypeShippingMethodForUpdate(customerType).getCustomerTypeShippingMethodValue().clone();
    }
    
    private List<CustomerTypeShippingMethod> getCustomerTypeShippingMethodsByCustomerType(CustomerType customerType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CustomerTypeShippingMethods.fields())
                    .from(CustomerTypeShippingMethods)
                    .join(ShippingMethods).onKey(CUSTOMER_TYPE_SHIPPING_METHODS_SHIPPING_METHOD_FK)
                    .join(ShippingMethodDetails).onKey(SHIPPING_METHODS_ACTIVE_DETAIL_FK)
                    .where(CustomerTypeShippingMethods.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                            CustomerTypeShippingMethods.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ShippingMethodDetails.SORT_ORDER, ShippingMethodDetails.SHIPPING_METHOD_NAME),
                    CustomerTypeShippingMethodFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CustomerTypeShippingMethods.fields())
                    .from(CustomerTypeShippingMethods)
                    .where(CustomerTypeShippingMethods.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                            CustomerTypeShippingMethods.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return customerTypeShippingMethodFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CustomerTypeShippingMethod> getCustomerTypeShippingMethodsByCustomerType(CustomerType customerType) {
        return getCustomerTypeShippingMethodsByCustomerType(customerType, EntityPermission.READ_ONLY);
    }
    
    public List<CustomerTypeShippingMethod> getCustomerTypeShippingMethodsByCustomerTypeForUpdate(CustomerType customerType) {
        return getCustomerTypeShippingMethodsByCustomerType(customerType, EntityPermission.READ_WRITE);
    }
    
    private List<CustomerTypeShippingMethod> getCustomerTypeShippingMethodsByShippingMethod(ShippingMethod shippingMethod,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CustomerTypeShippingMethods.fields())
                    .from(CustomerTypeShippingMethods)
                    .join(CustomerTypes).onKey(CUSTOMER_TYPE_SHIPPING_METHODS_CUSTOMER_TYPE_FK)
                    .join(CustomerTypeDetails).onKey(CUSTOMER_TYPES_ACTIVE_DETAIL_FK)
                    .where(CustomerTypeShippingMethods.SHIPPING_METHOD.eq(shippingMethod.getPrimaryKey()),
                            CustomerTypeShippingMethods.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(CustomerTypeDetails.SORT_ORDER, CustomerTypeDetails.CUSTOMER_TYPE_NAME),
                    CustomerTypeShippingMethodFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CustomerTypeShippingMethods.fields())
                    .from(CustomerTypeShippingMethods)
                    .where(CustomerTypeShippingMethods.SHIPPING_METHOD.eq(shippingMethod.getPrimaryKey()),
                            CustomerTypeShippingMethods.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return customerTypeShippingMethodFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CustomerTypeShippingMethod> getCustomerTypeShippingMethodsByShippingMethod(ShippingMethod shippingMethod) {
        return getCustomerTypeShippingMethodsByShippingMethod(shippingMethod, EntityPermission.READ_ONLY);
    }
    
    public List<CustomerTypeShippingMethod> getCustomerTypeShippingMethodsByShippingMethodForUpdate(ShippingMethod shippingMethod) {
        return getCustomerTypeShippingMethodsByShippingMethod(shippingMethod, EntityPermission.READ_WRITE);
    }
    
    public CustomerTypeShippingMethodTransfer getCustomerTypeShippingMethodTransfer(UserVisit userVisit, CustomerTypeShippingMethod customerTypeShippingMethod) {
        return customerTypeShippingMethodTransferCache.getCustomerTypeShippingMethodTransfer(userVisit, customerTypeShippingMethod);
    }
    
    public List<CustomerTypeShippingMethodTransfer> getCustomerTypeShippingMethodTransfers(UserVisit userVisit,
            Collection<CustomerTypeShippingMethod> customerTypeShippingMethods) {
        List<CustomerTypeShippingMethodTransfer> customerTypeShippingMethodTransfers = new ArrayList<>(customerTypeShippingMethods.size());

        for(var customerTypeShippingMethod : customerTypeShippingMethods) {
            customerTypeShippingMethodTransfers.add(customerTypeShippingMethodTransferCache.getCustomerTypeShippingMethodTransfer(userVisit, customerTypeShippingMethod));
        }

        return customerTypeShippingMethodTransfers;
    }
    
    public List<CustomerTypeShippingMethodTransfer> getCustomerTypeShippingMethodTransfersByShippingMethod(UserVisit userVisit, ShippingMethod shippingMethod) {
        return getCustomerTypeShippingMethodTransfers(userVisit, getCustomerTypeShippingMethodsByShippingMethod(shippingMethod));
    }
    
    public List<CustomerTypeShippingMethodTransfer> getCustomerTypeShippingMethodTransfersByCustomerType(UserVisit userVisit, CustomerType customerType) {
        return getCustomerTypeShippingMethodTransfers(userVisit, getCustomerTypeShippingMethodsByCustomerType(customerType));
    }
    
    private void updateCustomerTypeShippingMethodFromValue(CustomerTypeShippingMethodValue customerTypeShippingMethodValue,
            boolean checkDefault, BasePK updatedBy) {
        if(customerTypeShippingMethodValue.hasBeenModified()) {
            var customerTypeShippingMethod = customerTypeShippingMethodFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     customerTypeShippingMethodValue.getPrimaryKey());
            
            customerTypeShippingMethod.setThruTime(session.getStartTime());
            customerTypeShippingMethod.store();

            var customerTypePK = customerTypeShippingMethod.getCustomerTypePK();
            var customerType = customerTypeShippingMethod.getCustomerType();
            var shippingMethodPK = customerTypeShippingMethod.getShippingMethodPK();
            var defaultSelectionPriority = customerTypeShippingMethodValue.getDefaultSelectionPriority();
            var isDefault = customerTypeShippingMethodValue.getIsDefault();
            var sortOrder = customerTypeShippingMethodValue.getSortOrder();
            
            if(checkDefault) {
                var defaultCustomerTypeShippingMethod = getDefaultCustomerTypeShippingMethod(customerType);
                var defaultFound = defaultCustomerTypeShippingMethod != null && !defaultCustomerTypeShippingMethod.equals(customerTypeShippingMethod);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultCustomerTypeShippingMethodValue = getDefaultCustomerTypeShippingMethodValueForUpdate(customerType);
                    
                    defaultCustomerTypeShippingMethodValue.setIsDefault(false);
                    updateCustomerTypeShippingMethodFromValue(defaultCustomerTypeShippingMethodValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            customerTypeShippingMethod = customerTypeShippingMethodFactory.create(customerTypePK, shippingMethodPK, defaultSelectionPriority,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(customerTypePK, EventTypes.MODIFY, customerTypeShippingMethod.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateCustomerTypeShippingMethodFromValue(CustomerTypeShippingMethodValue customerTypeShippingMethodValue, BasePK updatedBy) {
        updateCustomerTypeShippingMethodFromValue(customerTypeShippingMethodValue, true, updatedBy);
    }
    
    public void deleteCustomerTypeShippingMethod(CustomerTypeShippingMethod customerTypeShippingMethod, BasePK deletedBy) {
        customerTypeShippingMethod.setThruTime(session.getStartTime());
        customerTypeShippingMethod.store();
        
        // Check for default, and pick one if necessary
        var customerType = customerTypeShippingMethod.getCustomerType();
        var defaultCustomerTypeShippingMethod = getDefaultCustomerTypeShippingMethod(customerType);
        if(defaultCustomerTypeShippingMethod == null) {
            var customerTypeShippingMethods = getCustomerTypeShippingMethodsByCustomerTypeForUpdate(customerType);
            
            if(!customerTypeShippingMethods.isEmpty()) {
                var iter = customerTypeShippingMethods.iterator();
                if(iter.hasNext()) {
                    defaultCustomerTypeShippingMethod = iter.next();
                }
                var customerTypeShippingMethodValue = defaultCustomerTypeShippingMethod.getCustomerTypeShippingMethodValue().clone();
                
                customerTypeShippingMethodValue.setIsDefault(true);
                updateCustomerTypeShippingMethodFromValue(customerTypeShippingMethodValue, false, deletedBy);
            }
        }
        
        sendEvent(customerTypeShippingMethod.getCustomerTypePK(), EventTypes.MODIFY, customerTypeShippingMethod.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteCustomerTypeShippingMethods(List<CustomerTypeShippingMethod> customerTypeShippingMethods, BasePK deletedBy) {
        customerTypeShippingMethods.forEach((customerTypeShippingMethod) -> 
                deleteCustomerTypeShippingMethod(customerTypeShippingMethod, deletedBy)
        );
    }
    
    public void deleteCustomerTypeShippingMethodsByCustomerType(CustomerType customerType, BasePK deletedBy) {
        deleteCustomerTypeShippingMethods(getCustomerTypeShippingMethodsByCustomerTypeForUpdate(customerType), deletedBy);
    }
    
    public void deleteCustomerTypeShippingMethodsByShippingMethod(ShippingMethod shippingMethod, BasePK deletedBy) {
        deleteCustomerTypeShippingMethods(getCustomerTypeShippingMethodsByShippingMethodForUpdate(shippingMethod), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Customer Searches
    // --------------------------------------------------------------------------------

    private List<Long> getCustomerIdsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
        var search = userVisitSearch.getSearch();
        var cachedSearch = search.getCachedSearch();
        Select<?> query;

        if(cachedSearch == null) {
            query = session.applyLimit(session.getDslContext()
                    .select(EntityInstances.ENTITY_UNIQUE)
                    .from(SearchResults)
                    .join(EntityInstances).onKey(SEARCH_RESULTS_ENTITY_INSTANCE_FK)
                    .where(SearchResults.SEARCH.eq(search.getPrimaryKey()))
                    .orderBy(SearchResults.SORT_ORDER, SearchResults.ENTITY_INSTANCE), SearchResultFactory.class);
        } else {
            var cachedExecutedSearch = searchControl.getCachedExecutedSearch(cachedSearch);

            session.copyLimit(SearchResultConstants.ENTITY_TYPE_NAME, CachedExecutedSearchResultConstants.ENTITY_TYPE_NAME);

            query = session.applyLimit(session.getDslContext()
                    .select(EntityInstances.ENTITY_UNIQUE)
                    .from(CachedExecutedSearchResults)
                    .join(EntityInstances).onKey(CACHED_EXECUTED_SEARCH_RESULTS_ENTITY_INSTANCE_FK)
                    .where(CachedExecutedSearchResults.CACHED_EXECUTED_SEARCH.eq(cachedExecutedSearch.getPrimaryKey()))
                    .orderBy(CachedExecutedSearchResults.SORT_ORDER, CachedExecutedSearchResults.ENTITY_INSTANCE),
                    CachedExecutedSearchResultFactory.class);
        }

        return query.fetch(EntityInstances.ENTITY_UNIQUE);
    }

    public List<CustomerResultTransfer> getCustomerResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var entityIds = getCustomerIdsFromUserVisitSearch(userVisitSearch);
        var customerResultTransfers = new ArrayList<CustomerResultTransfer>(entityIds.size());
        var options = session.getOptions();
        var includeCustomer = options != null && options.contains(SearchOptions.CustomerResultIncludeCustomer);

        for(var entityUniqueId : entityIds) {
            var party = partyControl.getPartyByPK(new PartyPK(entityUniqueId));

            customerResultTransfers.add(new CustomerResultTransfer(party.getLastDetail().getPartyName(),
                    includeCustomer ? getCustomerTransfer(userVisit, party) : null));
        }

        return customerResultTransfers;
    }

    public List<CustomerObject> getCustomerObjectsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
        var customerObjects = new ArrayList<CustomerObject>();

        for(var entityUniqueId : getCustomerIdsFromUserVisitSearch(userVisitSearch)) {
            var party = partyControl.getPartyByPK(new PartyPK(entityUniqueId));

            customerObjects.add(new CustomerObject(party));
        }

        return customerObjects;
    }

}

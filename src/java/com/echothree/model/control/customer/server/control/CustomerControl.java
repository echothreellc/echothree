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

package com.echothree.model.control.customer.server.control;

import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
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
import static com.echothree.model.control.search.server.control.SearchControl.ENI_ENTITYUNIQUEID_COLUMN_INDEX;
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
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.shipment.server.entity.FreeOnBoard;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.echothree.util.server.cdi.CommandScope;
import javax.inject.Inject;

@CommandScope
public class CustomerControl
        extends BaseModelControl {
    
    /** Creates a new instance of CustomerControl */
    protected CustomerControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Customer Transfer Caches
    // --------------------------------------------------------------------------------

    @Inject
    CustomerTypeTransferCache customerTypeTransferCache;

    @Inject
    CustomerTypeDescriptionTransferCache customerTypeDescriptionTransferCache;

    @Inject
    CustomerTransferCache customerTransferCache;

    @Inject
    CustomerTypePaymentMethodTransferCache customerTypePaymentMethodTransferCache;

    @Inject
    CustomerTypeShippingMethodTransferCache customerTypeShippingMethodTransferCache;

    // --------------------------------------------------------------------------------
    //   Customer Types
    // --------------------------------------------------------------------------------
    
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

        var customerType = CustomerTypeFactory.getInstance().create();
        var customerTypeDetail = CustomerTypeDetailFactory.getInstance().create(customerType, customerTypeName,
                customerSequence, defaultOfferUse, defaultTerm, defaultFreeOnBoard, defaultCancellationPolicy, defaultReturnPolicy,
                defaultCustomerStatus, defaultCustomerCreditStatus, defaultArGlAccount, defaultHoldUntilComplete, defaultAllowBackorders,
                defaultAllowSubstitutions, defaultAllowCombiningShipments, defaultRequireReference, defaultAllowReferenceDuplicates,
                defaultReferenceValidationPattern, defaultTaxable, allocationPriority, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME_LONG);

        // Convert to R/W
        customerType = CustomerTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, customerType.getPrimaryKey());
        customerType.setActiveDetail(customerTypeDetail);
        customerType.setLastDetail(customerTypeDetail);
        customerType.store();

        sendEvent(customerType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return customerType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.CustomerType */
    public CustomerType getCustomerTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new CustomerTypePK(entityInstance.getEntityUniqueId());

        return CustomerTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public CustomerType getCustomerTypeByEntityInstance(EntityInstance entityInstance) {
        return getCustomerTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public CustomerType getCustomerTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getCustomerTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countCustomerTypes() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM customertypes, customertypedetails " +
                        "WHERE cuty_activedetailid = cutydt_customertypedetailid");
    }

    public long countCustomerTypesByDefaultOfferUse(OfferUse defaultOfferUse) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM customertypes, customertypedetails " +
                        "WHERE cuty_activedetailid = cutydt_customertypedetailid " +
                        "AND cutydt_defaultofferuseid = ?",
                defaultOfferUse);
    }

    private List<CustomerType> getCustomerTypes(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM customertypes, customertypedetails " +
                    "WHERE cuty_activedetailid = cutydt_customertypedetailid " +
                    "ORDER BY cutydt_sortorder, cutydt_customertypename";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM customertypes, customertypedetails " +
                    "WHERE cuty_activedetailid = cutydt_customertypedetailid " +
                    "FOR UPDATE";
        }

        var ps = CustomerTypeFactory.getInstance().prepareStatement(query);
        
        return CustomerTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<CustomerType> getCustomerTypes() {
        return getCustomerTypes(EntityPermission.READ_ONLY);
    }
    
    public List<CustomerType> getCustomerTypesForUpdate() {
        return getCustomerTypes(EntityPermission.READ_WRITE);
    }
    
    public CustomerType getDefaultCustomerType(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM customertypes, customertypedetails " +
                    "WHERE cuty_activedetailid = cutydt_customertypedetailid AND cutydt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM customertypes, customertypedetails " +
                    "WHERE cuty_activedetailid = cutydt_customertypedetailid AND cutydt_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = CustomerTypeFactory.getInstance().prepareStatement(query);
        
        return CustomerTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
        CustomerType customerType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM customertypes, customertypedetails " +
                        "WHERE cuty_activedetailid = cutydt_customertypedetailid AND cutydt_customertypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM customertypes, customertypedetails " +
                        "WHERE cuty_activedetailid = cutydt_customertypedetailid AND cutydt_customertypename = ? " +
                        "FOR UPDATE";
            }

            var ps = CustomerTypeFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, customerTypeName);
            
            customerType = CustomerTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return customerType;
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
            var customerType = CustomerTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, customerTypeDetailValue.getCustomerTypePK());
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

            customerTypeDetail = CustomerTypeDetailFactory.getInstance().create(customerTypePK, customerTypeName, customerSequencePK,
                    defaultOfferUsePK, defaultTermPK, defaultFreeOnBoardPK, defaultCancellationPolicyPK, defaultReturnPolicyPK,
                    defaultCustomerStatusPK, defaultCustomerCreditStatusPK, defaultArGlAccountPK, defaultHoldUntilComplete,
                    defaultAllowBackorders, defaultAllowSubstitutions, defaultAllowCombiningShipments, defaultRequireReference,
                    defaultAllowReferenceDuplicates, defaultReferenceValidationPattern, defaultTaxable, allocationPriority,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME_LONG);

            customerType.setActiveDetail(customerTypeDetail);
            customerType.setLastDetail(customerTypeDetail);

            sendEvent(customerTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateCustomerTypeFromValue(CustomerTypeDetailValue customerTypeDetailValue, BasePK updatedBy) {
        updateCustomerTypeFromValue(customerTypeDetailValue, true, updatedBy);
    }
    
    public void deleteCustomerType(CustomerType customerType, BasePK deletedBy) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var itemControl = Session.getModelController(ItemControl.class);
        var offerControl = Session.getModelController(OfferControl.class);
        
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
    
    public CustomerTypeDescription createCustomerTypeDescription(CustomerType customerType, Language language, String description,
            BasePK createdBy) {
        var customerTypeDescription = CustomerTypeDescriptionFactory.getInstance().create(customerType,
                language, description,
                session.getStartTime(), Session.MAX_TIME_LONG);
        
        sendEvent(customerType.getPrimaryKey(), EventTypes.MODIFY, customerTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return customerTypeDescription;
    }
    
    private CustomerTypeDescription getCustomerTypeDescription(CustomerType customerType, Language language, EntityPermission entityPermission) {
        CustomerTypeDescription customerTypeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM customertypedescriptions " +
                        "WHERE cutyd_cuty_customertypeid = ? AND cutyd_lang_languageid = ? AND cutyd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM customertypedescriptions " +
                        "WHERE cutyd_cuty_customertypeid = ? AND cutyd_lang_languageid = ? AND cutyd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CustomerTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, customerType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            customerTypeDescription = CustomerTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return customerTypeDescription;
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
        List<CustomerTypeDescription> customerTypeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM customertypedescriptions, languages " +
                        "WHERE cutyd_cuty_customertypeid = ? AND cutyd_thrutime = ? AND cutyd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM customertypedescriptions " +
                        "WHERE cutyd_cuty_customertypeid = ? AND cutyd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CustomerTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, customerType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            customerTypeDescriptions = CustomerTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return customerTypeDescriptions;
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
            var customerTypeDescription = CustomerTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     customerTypeDescriptionValue.getPrimaryKey());
            
            customerTypeDescription.setThruTime(session.getStartTime());
            customerTypeDescription.store();

            var customerType = customerTypeDescription.getCustomerType();
            var language = customerTypeDescription.getLanguage();
            var description = customerTypeDescriptionValue.getDescription();
            
            customerTypeDescription = CustomerTypeDescriptionFactory.getInstance().create(customerType, language,
                    description, session.getStartTime(), Session.MAX_TIME_LONG);
            
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
    
    public Customer createCustomer(Party party, CustomerType customerType, OfferUse initialOfferUse,
            CancellationPolicy cancellationPolicy, ReturnPolicy returnPolicy, GlAccount arGlAccount,
            Boolean holdUntilComplete, Boolean allowBackorders, Boolean allowSubstitutions,
            Boolean allowCombiningShipments, Boolean requireReference, Boolean allowReferenceDuplicates,
            String referenceValidationPattern, BasePK createdBy) {
        var customerName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(null, SequenceTypes.CUSTOMER.name());
        var customer = CustomerFactory.getInstance().create(party, customerName, customerType, initialOfferUse,
                cancellationPolicy, returnPolicy, arGlAccount, holdUntilComplete, allowBackorders, allowSubstitutions,
                allowCombiningShipments, requireReference, allowReferenceDuplicates, referenceValidationPattern,
                session.getStartTime(), Session.MAX_TIME_LONG);

        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, customer.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return customer;
    }

    public long countCustomers() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM customers " +
                        "WHERE cu_thrutime = ?",
                Session.MAX_TIME);
    }

    public long countCustomersByInitialOfferUse(OfferUse initialOfferUse) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM customers " +
                        "WHERE cu_initialofferuseid = ? AND cu_thrutime = ?",
                initialOfferUse, Session.MAX_TIME);
    }

    private List<Customer> getCustomers(EntityPermission entityPermission) {
        List<Customer> customers;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM customers " +
                        "WHERE cu_thrutime = ? " +
                        "ORDER BY cu_customername " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM customers " +
                        "WHERE cu_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CustomerFactory.getInstance().prepareStatement(query);

            ps.setLong(1, Session.MAX_TIME);

            customers = CustomerFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return customers;
    }

    public List<Customer> getCustomers() {
        return getCustomers(EntityPermission.READ_ONLY);
    }

    public List<Customer> getCustomersForUpdate() {
        return getCustomers(EntityPermission.READ_WRITE);
    }
    
    public Customer getCustomer(Party party, EntityPermission entityPermission) {
        Customer customer;
        
        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM customers " +
                        "WHERE cu_par_partyid = ? AND cu_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM customers " +
                        "WHERE cu_par_partyid = ? AND cu_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CustomerFactory.getInstance().prepareStatement(query);

            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            customer = CustomerFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return customer;
    }

    public Customer getCustomer(Party party) {
        return getCustomer(party, EntityPermission.READ_ONLY);
    }

    public Customer getCustomerForUpdate(Party party) {
        return getCustomer(party, EntityPermission.READ_WRITE);
    }
    
    private Customer getCustomerByName(String customerName, EntityPermission entityPermission) {
        Customer customer;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM customers " +
                        "WHERE cu_customername = ? AND cu_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM customers " +
                        "WHERE cu_customername = ? AND cu_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CustomerFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, customerName);
            ps.setLong(2, Session.MAX_TIME);
            
            customer = CustomerFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return customer;
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
            var customer = CustomerFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, customerValue.getPrimaryKey());

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

            customer = CustomerFactory.getInstance().create(partyPK, customerName, customerTypePK, initialOfferUsePK, cancellationPolicyPK, returnPolicyPK,
                    arGlAccountPK, holdUntilComplete, allowBackorders, allowSubstitutions, allowCombiningShipments, requireReference, allowReferenceDuplicates,
                    referenceValidationPattern, session.getStartTime(), Session.MAX_TIME_LONG);

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
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
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
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
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

        var customerTypePaymentMethod = CustomerTypePaymentMethodFactory.getInstance().create(session, customerType, paymentMethod,
                defaultSelectionPriority, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME_LONG);
        
        sendEvent(customerType.getPrimaryKey(), EventTypes.MODIFY, customerTypePaymentMethod.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return customerTypePaymentMethod;
    }
    
    public long countCustomerTypePaymentMethodsByCustomerType(CustomerType customerType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM customertypepaymentmethods " +
                "WHERE cutypm_cuty_customertypeid = ? AND cutypm_thrutime = ?",
                customerType, Session.MAX_TIME_LONG);
    }

    public long countCustomerTypePaymentMethodsByPaymentMethod(PaymentMethod paymentMethod) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM customertypepaymentmethods " +
                "WHERE cutypm_pm_paymentmethodid = ? AND cutypm_thrutime = ?",
                paymentMethod, Session.MAX_TIME_LONG);
    }

    public boolean getCustomerTypePaymentMethodExists(CustomerType customerType, PaymentMethod paymentMethod) {
        return 1 == session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM customertypepaymentmethods " +
                "WHERE cutypm_cuty_customertypeid = ? AND cutypm_pm_paymentmethodid = ? AND cutypm_thrutime = ?",
                customerType, paymentMethod, Session.MAX_TIME_LONG);
    }

    private static final Map<EntityPermission, String> getCustomerTypePaymentMethodQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM customertypepaymentmethods " +
                "WHERE cutypm_cuty_customertypeid = ? AND cutypm_pm_paymentmethodid = ? AND cutypm_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM customertypepaymentmethods " +
                "WHERE cutypm_cuty_customertypeid = ? AND cutypm_pm_paymentmethodid = ? AND cutypm_thrutime = ? " +
                "FOR UPDATE");
        getCustomerTypePaymentMethodQueries = Collections.unmodifiableMap(queryMap);
    }

    private CustomerTypePaymentMethod getCustomerTypePaymentMethod(CustomerType customerType, PaymentMethod paymentMethod,
            EntityPermission entityPermission) {
        return CustomerTypePaymentMethodFactory.getInstance().getEntityFromQuery(entityPermission, getCustomerTypePaymentMethodQueries,
                customerType, paymentMethod, Session.MAX_TIME_LONG);
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
    
    private static final Map<EntityPermission, String> getDefaultCustomerTypePaymentMethodQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM customertypepaymentmethods " +
                "WHERE cutypm_cuty_customertypeid = ? AND cutypm_isdefault = 1 AND cutypm_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM customertypepaymentmethods " +
                "WHERE cutypm_cuty_customertypeid = ? AND cutypm_isdefault = 1 AND cutypm_thrutime = ? " +
                "FOR UPDATE");
        getDefaultCustomerTypePaymentMethodQueries = Collections.unmodifiableMap(queryMap);
    }

    private CustomerTypePaymentMethod getDefaultCustomerTypePaymentMethod(CustomerType customerType, EntityPermission entityPermission) {
        return CustomerTypePaymentMethodFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultCustomerTypePaymentMethodQueries,
                customerType, Session.MAX_TIME_LONG);
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
    
    private static final Map<EntityPermission, String> getCustomerTypePaymentMethodsByCustomerTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM customertypepaymentmethods, paymentmethods, paymentmethoddetails " +
                "WHERE cutypm_cuty_customertypeid = ? AND cutypm_thrutime = ? " +
                "AND cutypm_pm_paymentmethodid = pm_paymentmethodid AND pm_activedetailid = pmdt_paymentmethoddetailid " +
                "ORDER BY pmdt_sortorder, pmdt_paymentmethodname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM customertypepaymentmethods " +
                "WHERE cutypm_cuty_customertypeid = ? AND cutypm_thrutime = ? " +
                "FOR UPDATE");
        getCustomerTypePaymentMethodsByCustomerTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CustomerTypePaymentMethod> getCustomerTypePaymentMethodsByCustomerType(CustomerType customerType, EntityPermission entityPermission) {
        return CustomerTypePaymentMethodFactory.getInstance().getEntitiesFromQuery(entityPermission, getCustomerTypePaymentMethodsByCustomerTypeQueries,
                customerType, Session.MAX_TIME_LONG);
    }
    
    public List<CustomerTypePaymentMethod> getCustomerTypePaymentMethodsByCustomerType(CustomerType customerType) {
        return getCustomerTypePaymentMethodsByCustomerType(customerType, EntityPermission.READ_ONLY);
    }
    
    public List<CustomerTypePaymentMethod> getCustomerTypePaymentMethodsByCustomerTypeForUpdate(CustomerType customerType) {
        return getCustomerTypePaymentMethodsByCustomerType(customerType, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getCustomerTypePaymentMethodsByPaymentMethodQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM customertypepaymentmethods, customertypes, customertypedetails " +
                "WHERE cutypm_pm_paymentmethodid = ? AND cutypm_thrutime = ? " +
                "AND cutypm_cuty_customertypeid = cuty_customertypeid AND cuty_activedetailid = cutydt_customertypedetailid " +
                "ORDER BY cutydt_sortorder, cutydt_customertypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM customertypepaymentmethods " +
                "WHERE cutypm_pm_paymentmethodid = ? AND cutypm_thrutime = ? " +
                "FOR UPDATE");
        getCustomerTypePaymentMethodsByPaymentMethodQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CustomerTypePaymentMethod> getCustomerTypePaymentMethodsByPaymentMethod(PaymentMethod paymentMethod,
            EntityPermission entityPermission) {
        return CustomerTypePaymentMethodFactory.getInstance().getEntitiesFromQuery(entityPermission, getCustomerTypePaymentMethodsByPaymentMethodQueries,
                paymentMethod, Session.MAX_TIME_LONG);
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
    
    private List<CustomerTypePaymentMethodTransfer> getCustomerTypePaymentMethodTransfersByPaymentMethod(UserVisit userVisit,
            List<CustomerTypePaymentMethod> customerTypePaymentMethods) {
        List<CustomerTypePaymentMethodTransfer> customerTypePaymentMethodTransfers = new ArrayList<>(customerTypePaymentMethods.size());

        for(var customerTypePaymentMethod : customerTypePaymentMethods) {
            customerTypePaymentMethodTransfers.add(customerTypePaymentMethodTransferCache.getCustomerTypePaymentMethodTransfer(userVisit, customerTypePaymentMethod));
        }

        return customerTypePaymentMethodTransfers;
    }
    
    public List<CustomerTypePaymentMethodTransfer> getCustomerTypePaymentMethodTransfersByPaymentMethod(UserVisit userVisit, PaymentMethod paymentMethod) {
        return getCustomerTypePaymentMethodTransfersByPaymentMethod(userVisit, getCustomerTypePaymentMethodsByPaymentMethod(paymentMethod));
    }
    
    public List<CustomerTypePaymentMethodTransfer> getCustomerTypePaymentMethodTransfersByCustomerType(UserVisit userVisit, CustomerType customerType) {
        return getCustomerTypePaymentMethodTransfersByPaymentMethod(userVisit, getCustomerTypePaymentMethodsByCustomerType(customerType));
    }
    
    private void updateCustomerTypePaymentMethodFromValue(CustomerTypePaymentMethodValue customerTypePaymentMethodValue,
            boolean checkDefault, BasePK updatedBy) {
        if(customerTypePaymentMethodValue.hasBeenModified()) {
            var customerTypePaymentMethod = CustomerTypePaymentMethodFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
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
            
            customerTypePaymentMethod = CustomerTypePaymentMethodFactory.getInstance().create(customerTypePK, paymentMethodPK, defaultSelectionPriority,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME_LONG);
            
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

        var customerTypeShippingMethod = CustomerTypeShippingMethodFactory.getInstance().create(session, customerType, shippingMethod,
                defaultSelectionPriority, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME_LONG);
        
        sendEvent(customerType.getPrimaryKey(), EventTypes.MODIFY, customerTypeShippingMethod.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return customerTypeShippingMethod;
    }
    
    public long countCustomerTypeShippingMethodsByCustomerType(CustomerType customerType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM customertypeshippingmethods " +
                "WHERE cutyshm_cuty_customertypeid = ? AND cutyshm_thrutime = ?",
                customerType, Session.MAX_TIME_LONG);
    }

    public long countCustomerTypeShippingMethodsByShippingMethod(ShippingMethod shippingMethod) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM customertypeshippingmethods " +
                "WHERE cutyshm_shm_shippingmethodid = ? AND cutyshm_thrutime = ?",
                shippingMethod, Session.MAX_TIME_LONG);
    }

    public boolean getCustomerTypeShippingMethodExists(CustomerType customerType, ShippingMethod shippingMethod) {
        return 1 == session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM customertypeshippingmethods " +
                "WHERE cutyshm_cuty_customertypeid = ? AND cutyshm_shm_shippingmethodid = ? AND cutyshm_thrutime = ?",
                customerType, shippingMethod, Session.MAX_TIME_LONG);
    }

    private static final Map<EntityPermission, String> getCustomerTypeShippingMethodQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM customertypeshippingmethods " +
                "WHERE cutyshm_cuty_customertypeid = ? AND cutyshm_shm_shippingmethodid = ? AND cutyshm_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM customertypeshippingmethods " +
                "WHERE cutyshm_cuty_customertypeid = ? AND cutyshm_shm_shippingmethodid = ? AND cutyshm_thrutime = ? " +
                "FOR UPDATE");
        getCustomerTypeShippingMethodQueries = Collections.unmodifiableMap(queryMap);
    }

    private CustomerTypeShippingMethod getCustomerTypeShippingMethod(CustomerType customerType, ShippingMethod shippingMethod,
            EntityPermission entityPermission) {
        return CustomerTypeShippingMethodFactory.getInstance().getEntityFromQuery(entityPermission, getCustomerTypeShippingMethodQueries,
                customerType, shippingMethod, Session.MAX_TIME_LONG);
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
    
    private static final Map<EntityPermission, String> getDefaultCustomerTypeShippingMethodQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM customertypeshippingmethods " +
                "WHERE cutyshm_cuty_customertypeid = ? AND cutyshm_isdefault = 1 AND cutyshm_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM customertypeshippingmethods " +
                "WHERE cutyshm_cuty_customertypeid = ? AND cutyshm_isdefault = 1 AND cutyshm_thrutime = ? " +
                "FOR UPDATE");
        getDefaultCustomerTypeShippingMethodQueries = Collections.unmodifiableMap(queryMap);
    }

    private CustomerTypeShippingMethod getDefaultCustomerTypeShippingMethod(CustomerType customerType, EntityPermission entityPermission) {
        return CustomerTypeShippingMethodFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultCustomerTypeShippingMethodQueries,
                customerType, Session.MAX_TIME_LONG);
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
    
    private static final Map<EntityPermission, String> getCustomerTypeShippingMethodsByCustomerTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM customertypeshippingmethods, shippingmethods, shippingmethoddetails " +
                "WHERE cutyshm_cuty_customertypeid = ? AND cutyshm_thrutime = ? " +
                "AND cutyshm_shm_shippingmethodid = shm_shippingmethodid AND shm_activedetailid = shmdt_shippingmethoddetailid " +
                "ORDER BY shmdt_sortorder, shmdt_shippingmethodname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM customertypeshippingmethods " +
                "WHERE cutyshm_cuty_customertypeid = ? AND cutyshm_thrutime = ? " +
                "FOR UPDATE");
        getCustomerTypeShippingMethodsByCustomerTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CustomerTypeShippingMethod> getCustomerTypeShippingMethodsByCustomerType(CustomerType customerType, EntityPermission entityPermission) {
        return CustomerTypeShippingMethodFactory.getInstance().getEntitiesFromQuery(entityPermission, getCustomerTypeShippingMethodsByCustomerTypeQueries,
                customerType, Session.MAX_TIME_LONG);
    }
    
    public List<CustomerTypeShippingMethod> getCustomerTypeShippingMethodsByCustomerType(CustomerType customerType) {
        return getCustomerTypeShippingMethodsByCustomerType(customerType, EntityPermission.READ_ONLY);
    }
    
    public List<CustomerTypeShippingMethod> getCustomerTypeShippingMethodsByCustomerTypeForUpdate(CustomerType customerType) {
        return getCustomerTypeShippingMethodsByCustomerType(customerType, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getCustomerTypeShippingMethodsByShippingMethodQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM customertypeshippingmethods, customertypes, customertypedetails " +
                "WHERE cutyshm_shm_shippingmethodid = ? AND cutyshm_thrutime = ? " +
                "AND cutyshm_cuty_customertypeid = cuty_customertypeid AND cuty_activedetailid = cutydt_customertypedetailid " +
                "ORDER BY cutydt_sortorder, cutydt_customertypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM customertypeshippingmethods " +
                "WHERE cutyshm_shm_shippingmethodid = ? AND cutyshm_thrutime = ? " +
                "FOR UPDATE");
        getCustomerTypeShippingMethodsByShippingMethodQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CustomerTypeShippingMethod> getCustomerTypeShippingMethodsByShippingMethod(ShippingMethod shippingMethod,
            EntityPermission entityPermission) {
        return CustomerTypeShippingMethodFactory.getInstance().getEntitiesFromQuery(entityPermission, getCustomerTypeShippingMethodsByShippingMethodQueries,
                shippingMethod, Session.MAX_TIME_LONG);
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
    
    private List<CustomerTypeShippingMethodTransfer> getCustomerTypeShippingMethodTransfersByShippingMethod(UserVisit userVisit,
            List<CustomerTypeShippingMethod> customerTypeShippingMethods) {
        List<CustomerTypeShippingMethodTransfer> customerTypeShippingMethodTransfers = new ArrayList<>(customerTypeShippingMethods.size());

        for(var customerTypeShippingMethod : customerTypeShippingMethods) {
            customerTypeShippingMethodTransfers.add(customerTypeShippingMethodTransferCache.getCustomerTypeShippingMethodTransfer(userVisit, customerTypeShippingMethod));
        }

        return customerTypeShippingMethodTransfers;
    }
    
    public List<CustomerTypeShippingMethodTransfer> getCustomerTypeShippingMethodTransfersByShippingMethod(UserVisit userVisit, ShippingMethod shippingMethod) {
        return getCustomerTypeShippingMethodTransfersByShippingMethod(userVisit, getCustomerTypeShippingMethodsByShippingMethod(shippingMethod));
    }
    
    public List<CustomerTypeShippingMethodTransfer> getCustomerTypeShippingMethodTransfersByCustomerType(UserVisit userVisit, CustomerType customerType) {
        return getCustomerTypeShippingMethodTransfersByShippingMethod(userVisit, getCustomerTypeShippingMethodsByCustomerType(customerType));
    }
    
    private void updateCustomerTypeShippingMethodFromValue(CustomerTypeShippingMethodValue customerTypeShippingMethodValue,
            boolean checkDefault, BasePK updatedBy) {
        if(customerTypeShippingMethodValue.hasBeenModified()) {
            var customerTypeShippingMethod = CustomerTypeShippingMethodFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
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
            
            customerTypeShippingMethod = CustomerTypeShippingMethodFactory.getInstance().create(customerTypePK, shippingMethodPK, defaultSelectionPriority,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME_LONG);
            
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

    public List<CustomerResultTransfer> getCustomerResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var searchControl = Session.getModelController(SearchControl.class);
        var customerResultTransfers = new ArrayList<CustomerResultTransfer>();
        var includeCustomer = false;

        var options = session.getOptions();
        if(options != null) {
            includeCustomer = options.contains(SearchOptions.CustomerResultIncludeCustomer);
        }

        try (var rs = searchControl.getUserVisitSearchResultSet(userVisitSearch)) {
            var customerControl = Session.getModelController(CustomerControl.class);

            while(rs.next()) {
                var party = partyControl.getPartyByPK(new PartyPK(rs.getLong(ENI_ENTITYUNIQUEID_COLUMN_INDEX)));

                customerResultTransfers.add(new CustomerResultTransfer(party.getLastDetail().getPartyName(),
                        includeCustomer ? customerControl.getCustomerTransfer(userVisit, party) : null));
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return customerResultTransfers;
    }

    public List<CustomerObject> getCustomerObjectsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
        var searchControl = Session.getModelController(SearchControl.class);
        var customerObjects = new ArrayList<CustomerObject>();

        try (var rs = searchControl.getUserVisitSearchResultSet(userVisitSearch)) {
            while(rs.next()) {
                var party = partyControl.getPartyByPK(new PartyPK(rs.getLong(ENI_ENTITYUNIQUEID_COLUMN_INDEX)));

                customerObjects.add(new CustomerObject(party));
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return customerObjects;
    }

}

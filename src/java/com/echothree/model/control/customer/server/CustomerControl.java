// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.customer.server;

import com.echothree.model.control.contactlist.server.ContactListControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.customer.common.choice.CustomerCreditStatusChoicesBean;
import com.echothree.model.control.customer.common.choice.CustomerStatusChoicesBean;
import com.echothree.model.control.customer.common.choice.CustomerTypeChoicesBean;
import com.echothree.model.control.customer.common.transfer.CustomerTransfer;
import com.echothree.model.control.customer.common.transfer.CustomerTypeDescriptionTransfer;
import com.echothree.model.control.customer.common.transfer.CustomerTypePaymentMethodTransfer;
import com.echothree.model.control.customer.common.transfer.CustomerTypeShippingMethodTransfer;
import com.echothree.model.control.customer.common.transfer.CustomerTypeTransfer;
import com.echothree.model.control.customer.server.transfer.CustomerTransferCaches;
import com.echothree.model.control.customer.server.transfer.CustomerTypeDescriptionTransferCache;
import com.echothree.model.control.customer.server.transfer.CustomerTypePaymentMethodTransferCache;
import com.echothree.model.control.customer.server.transfer.CustomerTypeShippingMethodTransferCache;
import com.echothree.model.control.customer.server.transfer.CustomerTypeTransferCache;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.sequence.common.SequenceConstants;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.control.customer.common.workflow.CustomerCreditStatusConstants;
import com.echothree.model.control.customer.common.workflow.CustomerStatusConstants;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.accounting.common.pk.GlAccountPK;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.cancellationpolicy.common.pk.CancellationPolicyPK;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.customer.common.pk.CustomerTypePK;
import com.echothree.model.data.customer.server.entity.Customer;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.customer.server.entity.CustomerTypeDescription;
import com.echothree.model.data.customer.server.entity.CustomerTypeDetail;
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
import com.echothree.model.data.inventory.common.pk.AllocationPriorityPK;
import com.echothree.model.data.inventory.server.entity.AllocationPriority;
import com.echothree.model.data.offer.common.pk.OfferUsePK;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.payment.common.pk.PaymentMethodPK;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.returnpolicy.common.pk.ReturnPolicyPK;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.sequence.common.pk.SequencePK;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.shipping.common.pk.ShippingMethodPK;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.model.data.term.common.pk.TermPK;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.common.pk.WorkflowEntrancePK;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CustomerControl
        extends BaseModelControl {
    
    /** Creates a new instance of CustomerControl */
    public CustomerControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Customer Transfer Caches
    // --------------------------------------------------------------------------------
    
    private CustomerTransferCaches customerTransferCaches = null;
    
    public CustomerTransferCaches getCustomerTransferCaches(UserVisit userVisit) {
        if(customerTransferCaches == null) {
            customerTransferCaches = new CustomerTransferCaches(userVisit, this);
        }
        
        return customerTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Customer Types
    // --------------------------------------------------------------------------------
    
    public CustomerType createCustomerType(String customerTypeName, Sequence customerSequence, OfferUse defaultOfferUse, Term defaultTerm,
            CancellationPolicy defaultCancellationPolicy, ReturnPolicy defaultReturnPolicy, WorkflowEntrance defaultCustomerStatus,
            WorkflowEntrance defaultCustomerCreditStatus, GlAccount defaultArGlAccount, Boolean defaultHoldUntilComplete, Boolean defaultAllowBackorders,
            Boolean defaultAllowSubstitutions, Boolean defaultAllowCombiningShipments, Boolean defaultRequireReference, Boolean defaultAllowReferenceDuplicates,
            String defaultReferenceValidationPattern, Boolean defaultTaxable, AllocationPriority allocationPriority, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        CustomerType defaultCustomerType = getDefaultCustomerType();
        boolean defaultFound = defaultCustomerType != null;

        if(defaultFound && isDefault) {
            CustomerTypeDetailValue defaultCustomerTypeDetailValue = getDefaultCustomerTypeDetailValueForUpdate();

            defaultCustomerTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateCustomerTypeFromValue(defaultCustomerTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        CustomerType customerType = CustomerTypeFactory.getInstance().create();
        CustomerTypeDetail customerTypeDetail = CustomerTypeDetailFactory.getInstance().create(customerType, customerTypeName, customerSequence,
                defaultOfferUse, defaultTerm, defaultCancellationPolicy, defaultReturnPolicy, defaultCustomerStatus, defaultCustomerCreditStatus,
                defaultArGlAccount, defaultHoldUntilComplete, defaultAllowBackorders, defaultAllowSubstitutions, defaultAllowCombiningShipments,
                defaultRequireReference, defaultAllowReferenceDuplicates, defaultReferenceValidationPattern, defaultTaxable, allocationPriority, isDefault,
                sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        customerType = CustomerTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, customerType.getPrimaryKey());
        customerType.setActiveDetail(customerTypeDetail);
        customerType.setLastDetail(customerTypeDetail);
        customerType.store();

        sendEventUsingNames(customerType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return customerType;
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
        
        PreparedStatement ps = CustomerTypeFactory.getInstance().prepareStatement(query);
        
        return CustomerTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<CustomerType> getCustomerTypes() {
        return getCustomerTypes(EntityPermission.READ_ONLY);
    }
    
    public List<CustomerType> getCustomerTypesForUpdate() {
        return getCustomerTypes(EntityPermission.READ_WRITE);
    }
    
    private CustomerType getDefaultCustomerType(EntityPermission entityPermission) {
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
        
        PreparedStatement ps = CustomerTypeFactory.getInstance().prepareStatement(query);
        
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
    
    private CustomerType getCustomerTypeByName(String customerTypeName, EntityPermission entityPermission) {
        CustomerType customerType = null;
        
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
            
            PreparedStatement ps = CustomerTypeFactory.getInstance().prepareStatement(query);
            
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
        List<CustomerType> customerTypes = getCustomerTypes();
        int size = customerTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultCustomerTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(CustomerType customerType: customerTypes) {
            CustomerTypeDetail customerTypeDetail = customerType.getLastDetail();
            String label = getBestCustomerTypeDescription(customerType, language);
            String value = customerTypeDetail.getCustomerTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultCustomerTypeChoice == null? false: defaultCustomerTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && customerTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new CustomerTypeChoicesBean(labels, values, defaultValue);
    }
    
    public CustomerTypeTransfer getCustomerTypeTransfer(UserVisit userVisit, CustomerType customerType) {
        return getCustomerTransferCaches(userVisit).getCustomerTypeTransferCache().getCustomerTypeTransfer(customerType);
    }
    
    public List<CustomerTypeTransfer> getCustomerTypeTransfers(UserVisit userVisit) {
        List<CustomerType> customerTypes = getCustomerTypes();
        List<CustomerTypeTransfer> customerTypeTransfers = null;
        
        if(customerTypes != null) {
            CustomerTypeTransferCache customerTypeTransferCache = getCustomerTransferCaches(userVisit).getCustomerTypeTransferCache();
            
            customerTypeTransfers = new ArrayList<>(customerTypes.size());
            
            for(CustomerType customerType: customerTypes) {
                customerTypeTransfers.add(customerTypeTransferCache.getCustomerTypeTransfer(customerType));
            }
        }
        
        return customerTypeTransfers;
    }
    
    private void updateCustomerTypeFromValue(CustomerTypeDetailValue customerTypeDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(customerTypeDetailValue.hasBeenModified()) {
            CustomerType customerType = CustomerTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, customerTypeDetailValue.getCustomerTypePK());
            CustomerTypeDetail customerTypeDetail = customerType.getActiveDetailForUpdate();

            customerTypeDetail.setThruTime(session.START_TIME_LONG);
            customerTypeDetail.store();

            CustomerTypePK customerTypePK = customerTypeDetail.getCustomerTypePK();
            String customerTypeName = customerTypeDetailValue.getCustomerTypeName();
            SequencePK customerSequencePK = customerTypeDetailValue.getCustomerSequencePK();
            OfferUsePK defaultOfferUsePK = customerTypeDetailValue.getDefaultOfferUsePK();
            TermPK defaultTermPK = customerTypeDetailValue.getDefaultTermPK();
            CancellationPolicyPK defaultCancellationPolicyPK = customerTypeDetailValue.getDefaultCancellationPolicyPK();
            ReturnPolicyPK defaultReturnPolicyPK = customerTypeDetailValue.getDefaultReturnPolicyPK();
            WorkflowEntrancePK defaultCustomerStatusPK = customerTypeDetailValue.getDefaultCustomerStatusPK();
            WorkflowEntrancePK defaultCustomerCreditStatusPK = customerTypeDetailValue.getDefaultCustomerCreditStatusPK();
            GlAccountPK defaultArGlAccountPK = customerTypeDetailValue.getDefaultArGlAccountPK();
            Boolean defaultHoldUntilComplete = customerTypeDetailValue.getDefaultHoldUntilComplete();
            Boolean defaultAllowBackorders = customerTypeDetailValue.getDefaultAllowBackorders();
            Boolean defaultAllowSubstitutions = customerTypeDetailValue.getDefaultAllowSubstitutions();
            Boolean defaultAllowCombiningShipments = customerTypeDetailValue.getDefaultAllowCombiningShipments();
            Boolean defaultRequireReference = customerTypeDetailValue.getDefaultRequireReference();
            Boolean defaultAllowReferenceDuplicates = customerTypeDetailValue.getDefaultAllowReferenceDuplicates();
            String defaultReferenceValidationPattern = customerTypeDetailValue.getDefaultReferenceValidationPattern();
            Boolean defaultTaxable = customerTypeDetailValue.getDefaultTaxable();
            AllocationPriorityPK allocationPriority = customerTypeDetailValue.getAllocationPriorityPK();
            Boolean isDefault = customerTypeDetailValue.getIsDefault();
            Integer sortOrder = customerTypeDetailValue.getSortOrder();

            if(checkDefault) {
                CustomerType defaultCustomerType = getDefaultCustomerType();
                boolean defaultFound = defaultCustomerType != null && !defaultCustomerType.equals(customerType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    CustomerTypeDetailValue defaultCustomerTypeDetailValue = getDefaultCustomerTypeDetailValueForUpdate();

                    defaultCustomerTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateCustomerTypeFromValue(defaultCustomerTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            customerTypeDetail = CustomerTypeDetailFactory.getInstance().create(customerTypePK, customerTypeName, customerSequencePK, defaultOfferUsePK,
                    defaultTermPK, defaultCancellationPolicyPK, defaultReturnPolicyPK, defaultCustomerStatusPK, defaultCustomerCreditStatusPK,
                    defaultArGlAccountPK, defaultHoldUntilComplete, defaultAllowBackorders, defaultAllowSubstitutions, defaultAllowCombiningShipments,
                    defaultRequireReference, defaultAllowReferenceDuplicates, defaultReferenceValidationPattern, defaultTaxable, allocationPriority, isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            customerType.setActiveDetail(customerTypeDetail);
            customerType.setLastDetail(customerTypeDetail);

            sendEventUsingNames(customerTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateCustomerTypeFromValue(CustomerTypeDetailValue customerTypeDetailValue, BasePK updatedBy) {
        updateCustomerTypeFromValue(customerTypeDetailValue, true, updatedBy);
    }
    
    public void deleteCustomerType(CustomerType customerType, BasePK deletedBy) {
        var contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);
        var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        
        contactListControl.deleteCustomerTypeContactListGroupsByCustomerType(customerType, deletedBy);
        contactListControl.deleteCustomerTypeContactListsByCustomerType(customerType, deletedBy);
        itemControl.deleteItemShippingTimesByCustomerType(customerType, deletedBy);
        offerControl.deleteOfferCustomerTypesByCustomerType(customerType, deletedBy);
        deleteCustomerTypePaymentMethodsByCustomerType(customerType, deletedBy);
        deleteCustomerTypeDescriptionsByCustomerType(customerType, deletedBy);
        
        CustomerTypeDetail customerTypeDetail = customerType.getLastDetailForUpdate();
        customerTypeDetail.setThruTime(session.START_TIME_LONG);
        customerType.setActiveDetail(null);
        customerType.store();
        
        // Check for default, and pick one if necessary
        CustomerType defaultCustomerType = getDefaultCustomerType();
        if(defaultCustomerType == null) {
            List<CustomerType> customerTypes = getCustomerTypesForUpdate();
            
            if(!customerTypes.isEmpty()) {
                Iterator<CustomerType> iter = customerTypes.iterator();
                if(iter.hasNext()) {
                    defaultCustomerType = iter.next();
                }
                CustomerTypeDetailValue customerTypeDetailValue = defaultCustomerType.getLastDetailForUpdate().getCustomerTypeDetailValue().clone();
                
                customerTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateCustomerTypeFromValue(customerTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(customerType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Customer Type Descriptions
    // --------------------------------------------------------------------------------
    
    public CustomerTypeDescription createCustomerTypeDescription(CustomerType customerType, Language language, String description,
            BasePK createdBy) {
        CustomerTypeDescription customerTypeDescription = CustomerTypeDescriptionFactory.getInstance().create(customerType,
                language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(customerType.getPrimaryKey(), EventTypes.MODIFY.name(), customerTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return customerTypeDescription;
    }
    
    private CustomerTypeDescription getCustomerTypeDescription(CustomerType customerType, Language language, EntityPermission entityPermission) {
        CustomerTypeDescription customerTypeDescription = null;
        
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
            
            PreparedStatement ps = CustomerTypeDescriptionFactory.getInstance().prepareStatement(query);
            
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
        List<CustomerTypeDescription> customerTypeDescriptions = null;
        
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
            
            PreparedStatement ps = CustomerTypeDescriptionFactory.getInstance().prepareStatement(query);
            
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
        CustomerTypeDescription customerTypeDescription = getCustomerTypeDescription(customerType, language);
        
        if(customerTypeDescription == null && !language.getIsDefault()) {
            customerTypeDescription = getCustomerTypeDescription(customerType, getPartyControl().getDefaultLanguage());
        }
        
        if(customerTypeDescription == null) {
            description = customerType.getLastDetail().getCustomerTypeName();
        } else {
            description = customerTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public CustomerTypeDescriptionTransfer getCustomerTypeDescriptionTransfer(UserVisit userVisit, CustomerTypeDescription customerTypeDescription) {
        return getCustomerTransferCaches(userVisit).getCustomerTypeDescriptionTransferCache().getCustomerTypeDescriptionTransfer(customerTypeDescription);
    }
    
    public List<CustomerTypeDescriptionTransfer> getCustomerTypeDescriptionTransfers(UserVisit userVisit, CustomerType customerType) {
        List<CustomerTypeDescription> customerTypeDescriptions = getCustomerTypeDescriptionsByCustomerType(customerType);
        List<CustomerTypeDescriptionTransfer> customerTypeDescriptionTransfers = null;
        
        if(customerTypeDescriptions != null) {
            CustomerTypeDescriptionTransferCache customerTypeDescriptionTransferCache = getCustomerTransferCaches(userVisit).getCustomerTypeDescriptionTransferCache();
            
            customerTypeDescriptionTransfers = new ArrayList<>(customerTypeDescriptions.size());
            
            for(CustomerTypeDescription customerTypeDescription: customerTypeDescriptions) {
                customerTypeDescriptionTransfers.add(customerTypeDescriptionTransferCache.getCustomerTypeDescriptionTransfer(customerTypeDescription));
            }
        }
        
        return customerTypeDescriptionTransfers;
    }
    
    public void updateCustomerTypeDescriptionFromValue(CustomerTypeDescriptionValue customerTypeDescriptionValue, BasePK updatedBy) {
        if(customerTypeDescriptionValue.hasBeenModified()) {
            CustomerTypeDescription customerTypeDescription = CustomerTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     customerTypeDescriptionValue.getPrimaryKey());
            
            customerTypeDescription.setThruTime(session.START_TIME_LONG);
            customerTypeDescription.store();
            
            CustomerType customerType = customerTypeDescription.getCustomerType();
            Language language = customerTypeDescription.getLanguage();
            String description = customerTypeDescriptionValue.getDescription();
            
            customerTypeDescription = CustomerTypeDescriptionFactory.getInstance().create(customerType, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(customerType.getPrimaryKey(), EventTypes.MODIFY.name(), customerTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteCustomerTypeDescription(CustomerTypeDescription customerTypeDescription, BasePK deletedBy) {
        customerTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(customerTypeDescription.getCustomerTypePK(), EventTypes.MODIFY.name(), customerTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteCustomerTypeDescriptionsByCustomerType(CustomerType customerType, BasePK deletedBy) {
        List<CustomerTypeDescription> customerTypeDescriptions = getCustomerTypeDescriptionsByCustomerTypeForUpdate(customerType);
        
        customerTypeDescriptions.stream().forEach((customerTypeDescription) -> {
            deleteCustomerTypeDescription(customerTypeDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Customers
    // --------------------------------------------------------------------------------
    
    public Customer createCustomer(Party party, CustomerType customerType, OfferUse initialOfferUse, CancellationPolicy cancellationPolicy, ReturnPolicy returnPolicy, GlAccount arGlAccount, Boolean holdUntilComplete,
            Boolean allowBackorders, Boolean allowSubstitutions, Boolean allowCombiningShipments, Boolean requireReference, Boolean allowReferenceDuplicates,
            String referenceValidationPattern, BasePK createdBy) {
        Sequence sequence = customerType.getLastDetail().getCustomerSequence();
        var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);

        if(sequence == null) {
            SequenceType sequenceType = sequenceControl.getSequenceTypeByName(SequenceConstants.SequenceType_CUSTOMER);
            sequence = sequenceControl.getDefaultSequence(sequenceType);
        }

        String customerName = sequenceControl.getNextSequenceValue(sequence);
        Customer customer = CustomerFactory.getInstance().create(party, customerName, customerType, initialOfferUse, cancellationPolicy, returnPolicy, arGlAccount, holdUntilComplete,
                allowBackorders, allowSubstitutions, allowCombiningShipments, requireReference, allowReferenceDuplicates, referenceValidationPattern,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(party.getPrimaryKey(), EventTypes.MODIFY.name(), customer.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return customer;
    }
    
    public Customer getCustomer(Party party) {
        Customer customer = null;
        
        try {
            PreparedStatement ps = CustomerFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM customers " +
                    "WHERE cu_par_partyid = ? AND cu_thrutime = ?");
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            customer = CustomerFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return customer;
    }
    
    private Customer getCustomerByName(String customerName, EntityPermission entityPermission) {
        Customer customer = null;
        
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
            
            PreparedStatement ps = CustomerFactory.getInstance().prepareStatement(query);
            
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
        return getCustomerTransferCaches(userVisit).getCustomerTransferCache().getCustomerTransfer(customer);
    }
    
    public CustomerTransfer getCustomerTransfer(UserVisit userVisit, Party party) {
        return getCustomerTransferCaches(userVisit).getCustomerTransferCache().getCustomerTransfer(party);
    }
    
    public void updateCustomerFromValue(CustomerValue customerValue, BasePK updatedBy) {
        if(customerValue.hasBeenModified()) {
            Customer customer = CustomerFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, customerValue.getPrimaryKey());

            customer.setThruTime(session.START_TIME_LONG);
            customer.store();

            PartyPK partyPK = customer.getPartyPK(); // Not updated
            String customerName = customerValue.getCustomerName();
            CustomerTypePK customerTypePK = customerValue.getCustomerTypePK();
            OfferUsePK initialOfferUsePK = customerValue.getInitialOfferUsePK();
            CancellationPolicyPK cancellationPolicyPK = customerValue.getCancellationPolicyPK();
            ReturnPolicyPK returnPolicyPK = customerValue.getReturnPolicyPK();
            GlAccountPK arGlAccountPK = customerValue.getArGlAccountPK();
            Boolean holdUntilComplete = customerValue.getHoldUntilComplete();
            Boolean allowBackorders = customerValue.getAllowBackorders();
            Boolean allowSubstitutions = customerValue.getAllowSubstitutions();
            Boolean allowCombiningShipments = customerValue.getAllowCombiningShipments();
            Boolean requireReference = customerValue.getRequireReference();
            Boolean allowReferenceDuplicates = customerValue.getAllowReferenceDuplicates();
            String referenceValidationPattern = customerValue.getReferenceValidationPattern();

            customer = CustomerFactory.getInstance().create(partyPK, customerName, customerTypePK, initialOfferUsePK, cancellationPolicyPK, returnPolicyPK,
                    arGlAccountPK, holdUntilComplete, allowBackorders, allowSubstitutions, allowCombiningShipments, requireReference, allowReferenceDuplicates,
                    referenceValidationPattern, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(partyPK, EventTypes.MODIFY.name(), customer.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public CustomerStatusChoicesBean getCustomerStatusChoices(String defaultCustomerStatusChoice, Language language,
            boolean allowNullChoice, Party customerParty, PartyPK partyPK) {
        var workflowControl = getWorkflowControl();
        CustomerStatusChoicesBean customerStatusChoicesBean = new CustomerStatusChoicesBean();
        
        if(customerParty == null) {
            workflowControl.getWorkflowEntranceChoices(customerStatusChoicesBean, defaultCustomerStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(CustomerStatusConstants.Workflow_CUSTOMER_STATUS), partyPK);
        } else {
            EntityInstance entityInstance = getCoreControl().getEntityInstanceByBasePK(customerParty.getPrimaryKey());
            WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(CustomerStatusConstants.Workflow_CUSTOMER_STATUS,
                    entityInstance);
            
            workflowControl.getWorkflowDestinationChoices(customerStatusChoicesBean, defaultCustomerStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }
        
        return customerStatusChoicesBean;
    }
    
    public void setCustomerStatus(ExecutionErrorAccumulator eea, Party party, String customerStatusChoice, PartyPK modifiedBy) {
        var workflowControl = getWorkflowControl();
        EntityInstance entityInstance = getEntityInstanceByBaseEntity(party);
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(CustomerStatusConstants.Workflow_CUSTOMER_STATUS,
                entityInstance);
        WorkflowDestination workflowDestination = customerStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), customerStatusChoice);
        
        if(workflowDestination != null || customerStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownCustomerStatusChoice.name(), customerStatusChoice);
        }
    }
    
    public CustomerCreditStatusChoicesBean getCustomerCreditStatusChoices(String defaultCustomerCreditStatusChoice, Language language,
            boolean allowNullChoice, Party customerParty, PartyPK partyPK) {
        var workflowControl = getWorkflowControl();
        CustomerCreditStatusChoicesBean customerCreditStatusChoicesBean = new CustomerCreditStatusChoicesBean();
        
        if(customerParty == null) {
            workflowControl.getWorkflowEntranceChoices(customerCreditStatusChoicesBean, defaultCustomerCreditStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(CustomerCreditStatusConstants.Workflow_CUSTOMER_CREDIT_STATUS), partyPK);
        } else {
            EntityInstance entityInstance = getCoreControl().getEntityInstanceByBasePK(customerParty.getPrimaryKey());
            WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(CustomerCreditStatusConstants.Workflow_CUSTOMER_CREDIT_STATUS,
                    entityInstance);
            
            workflowControl.getWorkflowDestinationChoices(customerCreditStatusChoicesBean, defaultCustomerCreditStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }
        
        return customerCreditStatusChoicesBean;
    }
    
    public void setCustomerCreditStatus(ExecutionErrorAccumulator eea, Party party, String customerCreditStatusChoice, PartyPK modifiedBy) {
        var workflowControl = getWorkflowControl();
        EntityInstance entityInstance = getEntityInstanceByBaseEntity(party);
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(CustomerCreditStatusConstants.Workflow_CUSTOMER_CREDIT_STATUS,
                entityInstance);
        WorkflowDestination workflowDestination = customerCreditStatusChoice == null? null:
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
        CustomerTypePaymentMethod defaultCustomerTypePaymentMethod = getDefaultCustomerTypePaymentMethod(customerType);
        boolean defaultFound = defaultCustomerTypePaymentMethod != null;
        
        if(defaultFound && isDefault) {
            CustomerTypePaymentMethodValue defaultCustomerTypePaymentMethodValue = getDefaultCustomerTypePaymentMethodValueForUpdate(customerType);
            
            defaultCustomerTypePaymentMethodValue.setIsDefault(Boolean.FALSE);
            updateCustomerTypePaymentMethodFromValue(defaultCustomerTypePaymentMethodValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        CustomerTypePaymentMethod customerTypePaymentMethod = CustomerTypePaymentMethodFactory.getInstance().create(session, customerType, paymentMethod,
                defaultSelectionPriority, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(customerType.getPrimaryKey(), EventTypes.MODIFY.name(), customerTypePaymentMethod.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
        return getCustomerTransferCaches(userVisit).getCustomerTypePaymentMethodTransferCache().getCustomerTypePaymentMethodTransfer(customerTypePaymentMethod);
    }
    
    private List<CustomerTypePaymentMethodTransfer> getCustomerTypePaymentMethodTransfersByPaymentMethod(UserVisit userVisit,
            List<CustomerTypePaymentMethod> customerTypePaymentMethods) {
        List<CustomerTypePaymentMethodTransfer> customerTypePaymentMethodTransfers = customerTypePaymentMethodTransfers = new ArrayList<>(customerTypePaymentMethods.size());
        CustomerTypePaymentMethodTransferCache customerTypePaymentMethodTransferCache = getCustomerTransferCaches(userVisit).getCustomerTypePaymentMethodTransferCache();

        for(CustomerTypePaymentMethod customerTypePaymentMethod : customerTypePaymentMethods) {
            customerTypePaymentMethodTransfers.add(customerTypePaymentMethodTransferCache.getCustomerTypePaymentMethodTransfer(customerTypePaymentMethod));
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
            CustomerTypePaymentMethod customerTypePaymentMethod = CustomerTypePaymentMethodFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     customerTypePaymentMethodValue.getPrimaryKey());
            
            customerTypePaymentMethod.setThruTime(session.START_TIME_LONG);
            customerTypePaymentMethod.store();
            
            CustomerTypePK customerTypePK = customerTypePaymentMethod.getCustomerTypePK();
            CustomerType customerType = customerTypePaymentMethod.getCustomerType();
            PaymentMethodPK paymentMethodPK = customerTypePaymentMethod.getPaymentMethodPK();
            Integer defaultSelectionPriority = customerTypePaymentMethodValue.getDefaultSelectionPriority();
            Boolean isDefault = customerTypePaymentMethodValue.getIsDefault();
            Integer sortOrder = customerTypePaymentMethodValue.getSortOrder();
            
            if(checkDefault) {
                CustomerTypePaymentMethod defaultCustomerTypePaymentMethod = getDefaultCustomerTypePaymentMethod(customerType);
                boolean defaultFound = defaultCustomerTypePaymentMethod != null && !defaultCustomerTypePaymentMethod.equals(customerTypePaymentMethod);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    CustomerTypePaymentMethodValue defaultCustomerTypePaymentMethodValue = getDefaultCustomerTypePaymentMethodValueForUpdate(customerType);
                    
                    defaultCustomerTypePaymentMethodValue.setIsDefault(Boolean.FALSE);
                    updateCustomerTypePaymentMethodFromValue(defaultCustomerTypePaymentMethodValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            customerTypePaymentMethod = CustomerTypePaymentMethodFactory.getInstance().create(customerTypePK, paymentMethodPK, defaultSelectionPriority,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(customerTypePK, EventTypes.MODIFY.name(), customerTypePaymentMethod.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void updateCustomerTypePaymentMethodFromValue(CustomerTypePaymentMethodValue customerTypePaymentMethodValue, BasePK updatedBy) {
        updateCustomerTypePaymentMethodFromValue(customerTypePaymentMethodValue, true, updatedBy);
    }
    
    public void deleteCustomerTypePaymentMethod(CustomerTypePaymentMethod customerTypePaymentMethod, BasePK deletedBy) {
        customerTypePaymentMethod.setThruTime(session.START_TIME_LONG);
        customerTypePaymentMethod.store();
        
        // Check for default, and pick one if necessary
        CustomerType customerType = customerTypePaymentMethod.getCustomerType();
        CustomerTypePaymentMethod defaultCustomerTypePaymentMethod = getDefaultCustomerTypePaymentMethod(customerType);
        if(defaultCustomerTypePaymentMethod == null) {
            List<CustomerTypePaymentMethod> customerTypePaymentMethods = getCustomerTypePaymentMethodsByCustomerTypeForUpdate(customerType);
            
            if(!customerTypePaymentMethods.isEmpty()) {
                Iterator<CustomerTypePaymentMethod> iter = customerTypePaymentMethods.iterator();
                if(iter.hasNext()) {
                    defaultCustomerTypePaymentMethod = iter.next();
                }
                CustomerTypePaymentMethodValue customerTypePaymentMethodValue = defaultCustomerTypePaymentMethod.getCustomerTypePaymentMethodValue().clone();
                
                customerTypePaymentMethodValue.setIsDefault(Boolean.TRUE);
                updateCustomerTypePaymentMethodFromValue(customerTypePaymentMethodValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(customerTypePaymentMethod.getCustomerTypePK(), EventTypes.MODIFY.name(), customerTypePaymentMethod.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteCustomerTypePaymentMethods(List<CustomerTypePaymentMethod> customerTypePaymentMethods, BasePK deletedBy) {
        customerTypePaymentMethods.stream().forEach((customerTypePaymentMethod) -> {
            deleteCustomerTypePaymentMethod(customerTypePaymentMethod, deletedBy);
        });
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
        CustomerTypeShippingMethod defaultCustomerTypeShippingMethod = getDefaultCustomerTypeShippingMethod(customerType);
        boolean defaultFound = defaultCustomerTypeShippingMethod != null;
        
        if(defaultFound && isDefault) {
            CustomerTypeShippingMethodValue defaultCustomerTypeShippingMethodValue = getDefaultCustomerTypeShippingMethodValueForUpdate(customerType);
            
            defaultCustomerTypeShippingMethodValue.setIsDefault(Boolean.FALSE);
            updateCustomerTypeShippingMethodFromValue(defaultCustomerTypeShippingMethodValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        CustomerTypeShippingMethod customerTypeShippingMethod = CustomerTypeShippingMethodFactory.getInstance().create(session, customerType, shippingMethod,
                defaultSelectionPriority, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(customerType.getPrimaryKey(), EventTypes.MODIFY.name(), customerTypeShippingMethod.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
                "ORDER BY shmdt_sortorder, shmdt_shippingmethodname");
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
                "ORDER BY cutydt_sortorder, cutydt_customertypename");
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
        return getCustomerTransferCaches(userVisit).getCustomerTypeShippingMethodTransferCache().getCustomerTypeShippingMethodTransfer(customerTypeShippingMethod);
    }
    
    private List<CustomerTypeShippingMethodTransfer> getCustomerTypeShippingMethodTransfersByShippingMethod(UserVisit userVisit,
            List<CustomerTypeShippingMethod> customerTypeShippingMethods) {
        List<CustomerTypeShippingMethodTransfer> customerTypeShippingMethodTransfers = customerTypeShippingMethodTransfers = new ArrayList<>(customerTypeShippingMethods.size());
        CustomerTypeShippingMethodTransferCache customerTypeShippingMethodTransferCache = getCustomerTransferCaches(userVisit).getCustomerTypeShippingMethodTransferCache();

        for(CustomerTypeShippingMethod customerTypeShippingMethod : customerTypeShippingMethods) {
            customerTypeShippingMethodTransfers.add(customerTypeShippingMethodTransferCache.getCustomerTypeShippingMethodTransfer(customerTypeShippingMethod));
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
            CustomerTypeShippingMethod customerTypeShippingMethod = CustomerTypeShippingMethodFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     customerTypeShippingMethodValue.getPrimaryKey());
            
            customerTypeShippingMethod.setThruTime(session.START_TIME_LONG);
            customerTypeShippingMethod.store();
            
            CustomerTypePK customerTypePK = customerTypeShippingMethod.getCustomerTypePK();
            CustomerType customerType = customerTypeShippingMethod.getCustomerType();
            ShippingMethodPK shippingMethodPK = customerTypeShippingMethod.getShippingMethodPK();
            Integer defaultSelectionPriority = customerTypeShippingMethodValue.getDefaultSelectionPriority();
            Boolean isDefault = customerTypeShippingMethodValue.getIsDefault();
            Integer sortOrder = customerTypeShippingMethodValue.getSortOrder();
            
            if(checkDefault) {
                CustomerTypeShippingMethod defaultCustomerTypeShippingMethod = getDefaultCustomerTypeShippingMethod(customerType);
                boolean defaultFound = defaultCustomerTypeShippingMethod != null && !defaultCustomerTypeShippingMethod.equals(customerTypeShippingMethod);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    CustomerTypeShippingMethodValue defaultCustomerTypeShippingMethodValue = getDefaultCustomerTypeShippingMethodValueForUpdate(customerType);
                    
                    defaultCustomerTypeShippingMethodValue.setIsDefault(Boolean.FALSE);
                    updateCustomerTypeShippingMethodFromValue(defaultCustomerTypeShippingMethodValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            customerTypeShippingMethod = CustomerTypeShippingMethodFactory.getInstance().create(customerTypePK, shippingMethodPK, defaultSelectionPriority,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(customerTypePK, EventTypes.MODIFY.name(), customerTypeShippingMethod.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void updateCustomerTypeShippingMethodFromValue(CustomerTypeShippingMethodValue customerTypeShippingMethodValue, BasePK updatedBy) {
        updateCustomerTypeShippingMethodFromValue(customerTypeShippingMethodValue, true, updatedBy);
    }
    
    public void deleteCustomerTypeShippingMethod(CustomerTypeShippingMethod customerTypeShippingMethod, BasePK deletedBy) {
        customerTypeShippingMethod.setThruTime(session.START_TIME_LONG);
        customerTypeShippingMethod.store();
        
        // Check for default, and pick one if necessary
        CustomerType customerType = customerTypeShippingMethod.getCustomerType();
        CustomerTypeShippingMethod defaultCustomerTypeShippingMethod = getDefaultCustomerTypeShippingMethod(customerType);
        if(defaultCustomerTypeShippingMethod == null) {
            List<CustomerTypeShippingMethod> customerTypeShippingMethods = getCustomerTypeShippingMethodsByCustomerTypeForUpdate(customerType);
            
            if(!customerTypeShippingMethods.isEmpty()) {
                Iterator<CustomerTypeShippingMethod> iter = customerTypeShippingMethods.iterator();
                if(iter.hasNext()) {
                    defaultCustomerTypeShippingMethod = iter.next();
                }
                CustomerTypeShippingMethodValue customerTypeShippingMethodValue = defaultCustomerTypeShippingMethod.getCustomerTypeShippingMethodValue().clone();
                
                customerTypeShippingMethodValue.setIsDefault(Boolean.TRUE);
                updateCustomerTypeShippingMethodFromValue(customerTypeShippingMethodValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(customerTypeShippingMethod.getCustomerTypePK(), EventTypes.MODIFY.name(), customerTypeShippingMethod.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteCustomerTypeShippingMethods(List<CustomerTypeShippingMethod> customerTypeShippingMethods, BasePK deletedBy) {
        customerTypeShippingMethods.stream().forEach((customerTypeShippingMethod) -> {
            deleteCustomerTypeShippingMethod(customerTypeShippingMethod, deletedBy);
        });
    }
    
    public void deleteCustomerTypeShippingMethodsByCustomerType(CustomerType customerType, BasePK deletedBy) {
        deleteCustomerTypeShippingMethods(getCustomerTypeShippingMethodsByCustomerTypeForUpdate(customerType), deletedBy);
    }
    
    public void deleteCustomerTypeShippingMethodsByShippingMethod(ShippingMethod shippingMethod, BasePK deletedBy) {
        deleteCustomerTypeShippingMethods(getCustomerTypeShippingMethodsByShippingMethodForUpdate(shippingMethod), deletedBy);
    }
    
}

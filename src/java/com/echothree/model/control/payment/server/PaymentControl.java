// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.model.control.payment.server;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.customer.server.CustomerControl;
import com.echothree.model.control.order.server.OrderControl;
import com.echothree.model.control.payment.common.PaymentConstants;
import com.echothree.model.control.payment.common.choice.PartyPaymentMethodChoicesBean;
import com.echothree.model.control.payment.common.choice.PaymentMethodChoicesBean;
import com.echothree.model.control.payment.common.choice.PaymentMethodTypeChoicesBean;
import com.echothree.model.control.payment.common.choice.PaymentProcessorChoicesBean;
import com.echothree.model.control.payment.common.choice.PaymentProcessorTypeChoicesBean;
import com.echothree.model.control.payment.common.transfer.BillingAccountRoleTransfer;
import com.echothree.model.control.payment.common.transfer.BillingAccountRoleTypeTransfer;
import com.echothree.model.control.payment.common.transfer.BillingAccountTransfer;
import com.echothree.model.control.payment.common.transfer.PartyPaymentMethodContactMechanismTransfer;
import com.echothree.model.control.payment.common.transfer.PartyPaymentMethodTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentMethodDescriptionTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentMethodTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentMethodTypePartyTypeTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentMethodTypeTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorDescriptionTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorTypeTransfer;
import com.echothree.model.control.payment.server.transfer.BillingAccountRoleTransferCache;
import com.echothree.model.control.payment.server.transfer.BillingAccountRoleTypeTransferCache;
import com.echothree.model.control.payment.server.transfer.BillingAccountTransferCache;
import com.echothree.model.control.payment.server.transfer.PartyPaymentMethodContactMechanismTransferCache;
import com.echothree.model.control.payment.server.transfer.PartyPaymentMethodTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentMethodDescriptionTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentMethodTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentMethodTypePartyTypeTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentMethodTypeTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorDescriptionTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorTypeTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentTransferCaches;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.contact.common.pk.PartyContactMechanismPK;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.contact.server.entity.PartyContactMechanismPurpose;
import com.echothree.model.data.party.common.pk.NameSuffixPK;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.common.pk.PersonalTitlePK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.NameSuffix;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.entity.PersonalTitle;
import com.echothree.model.data.payment.common.pk.BillingAccountPK;
import com.echothree.model.data.payment.common.pk.BillingAccountRoleTypePK;
import com.echothree.model.data.payment.common.pk.PartyPaymentMethodPK;
import com.echothree.model.data.payment.common.pk.PaymentMethodPK;
import com.echothree.model.data.payment.common.pk.PaymentMethodTypePK;
import com.echothree.model.data.payment.common.pk.PaymentProcessorPK;
import com.echothree.model.data.payment.common.pk.PaymentProcessorTypePK;
import com.echothree.model.data.payment.server.entity.BillingAccount;
import com.echothree.model.data.payment.server.entity.BillingAccountDetail;
import com.echothree.model.data.payment.server.entity.BillingAccountRole;
import com.echothree.model.data.payment.server.entity.BillingAccountRoleType;
import com.echothree.model.data.payment.server.entity.BillingAccountRoleTypeDescription;
import com.echothree.model.data.payment.server.entity.BillingAccountStatus;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethod;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethodContactMechanism;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethodCreditCard;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethodCreditCardSecurityCode;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethodDetail;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.payment.server.entity.PaymentMethodCheck;
import com.echothree.model.data.payment.server.entity.PaymentMethodCreditCard;
import com.echothree.model.data.payment.server.entity.PaymentMethodDescription;
import com.echothree.model.data.payment.server.entity.PaymentMethodDetail;
import com.echothree.model.data.payment.server.entity.PaymentMethodType;
import com.echothree.model.data.payment.server.entity.PaymentMethodTypeDescription;
import com.echothree.model.data.payment.server.entity.PaymentMethodTypePartyType;
import com.echothree.model.data.payment.server.entity.PaymentProcessor;
import com.echothree.model.data.payment.server.entity.PaymentProcessorDescription;
import com.echothree.model.data.payment.server.entity.PaymentProcessorDetail;
import com.echothree.model.data.payment.server.entity.PaymentProcessorType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeDescription;
import com.echothree.model.data.payment.server.factory.BillingAccountDetailFactory;
import com.echothree.model.data.payment.server.factory.BillingAccountFactory;
import com.echothree.model.data.payment.server.factory.BillingAccountRoleFactory;
import com.echothree.model.data.payment.server.factory.BillingAccountRoleTypeDescriptionFactory;
import com.echothree.model.data.payment.server.factory.BillingAccountRoleTypeFactory;
import com.echothree.model.data.payment.server.factory.BillingAccountStatusFactory;
import com.echothree.model.data.payment.server.factory.PartyPaymentMethodContactMechanismFactory;
import com.echothree.model.data.payment.server.factory.PartyPaymentMethodCreditCardFactory;
import com.echothree.model.data.payment.server.factory.PartyPaymentMethodCreditCardSecurityCodeFactory;
import com.echothree.model.data.payment.server.factory.PartyPaymentMethodDetailFactory;
import com.echothree.model.data.payment.server.factory.PartyPaymentMethodFactory;
import com.echothree.model.data.payment.server.factory.PaymentMethodCheckFactory;
import com.echothree.model.data.payment.server.factory.PaymentMethodCreditCardFactory;
import com.echothree.model.data.payment.server.factory.PaymentMethodDescriptionFactory;
import com.echothree.model.data.payment.server.factory.PaymentMethodDetailFactory;
import com.echothree.model.data.payment.server.factory.PaymentMethodFactory;
import com.echothree.model.data.payment.server.factory.PaymentMethodTypeDescriptionFactory;
import com.echothree.model.data.payment.server.factory.PaymentMethodTypeFactory;
import com.echothree.model.data.payment.server.factory.PaymentMethodTypePartyTypeFactory;
import com.echothree.model.data.payment.server.factory.PaymentProcessorDescriptionFactory;
import com.echothree.model.data.payment.server.factory.PaymentProcessorDetailFactory;
import com.echothree.model.data.payment.server.factory.PaymentProcessorFactory;
import com.echothree.model.data.payment.server.factory.PaymentProcessorTypeDescriptionFactory;
import com.echothree.model.data.payment.server.factory.PaymentProcessorTypeFactory;
import com.echothree.model.data.payment.server.value.BillingAccountRoleValue;
import com.echothree.model.data.payment.server.value.PartyPaymentMethodCreditCardSecurityCodeValue;
import com.echothree.model.data.payment.server.value.PartyPaymentMethodCreditCardValue;
import com.echothree.model.data.payment.server.value.PartyPaymentMethodDetailValue;
import com.echothree.model.data.payment.server.value.PaymentMethodCheckValue;
import com.echothree.model.data.payment.server.value.PaymentMethodCreditCardValue;
import com.echothree.model.data.payment.server.value.PaymentMethodDescriptionValue;
import com.echothree.model.data.payment.server.value.PaymentMethodDetailValue;
import com.echothree.model.data.payment.server.value.PaymentProcessorDescriptionValue;
import com.echothree.model.data.payment.server.value.PaymentProcessorDetailValue;
import com.echothree.model.data.selector.common.pk.SelectorPK;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EncryptionUtils;
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

public class PaymentControl
        extends BasePaymentControl {
    
    /** Creates a new instance of PaymentControl */
    public PaymentControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Billing Account Role Types
    // --------------------------------------------------------------------------------
    
    public BillingAccountRoleType createBillingAccountRoleType(String billingAccountRoleTypeName, Integer sortOrder) {
        return BillingAccountRoleTypeFactory.getInstance().create(billingAccountRoleTypeName, sortOrder);
    }
    
    public List<BillingAccountRoleType> getBillingAccountRoleTypes() {
        PreparedStatement ps = BillingAccountRoleTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM billingaccountroletypes " +
                "ORDER BY bllactrtyp_sortorder, bllactrtyp_billingaccountroletypename");
        
        return BillingAccountRoleTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public BillingAccountRoleType getBillingAccountRoleTypeByName(String billingAccountRoleTypeName) {
        BillingAccountRoleType billingAccountRoleType = null;
        
        try {
            PreparedStatement ps = BillingAccountRoleTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM billingaccountroletypes " +
                    "WHERE bllactrtyp_billingaccountroletypename = ?");
            
            ps.setString(1, billingAccountRoleTypeName);
            
            
            billingAccountRoleType = BillingAccountRoleTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return billingAccountRoleType;
    }
    
    public BillingAccountRoleTypeTransfer getBillingAccountRoleTypeTransfer(UserVisit userVisit,
            BillingAccountRoleType billingAccountRoleType) {
        return getPaymentTransferCaches(userVisit).getBillingAccountRoleTypeTransferCache().getTransfer(billingAccountRoleType);
    }

    private List<BillingAccountRoleTypeTransfer> getBillingAccountRoleTypeTransfers(final UserVisit userVisit, final List<BillingAccountRoleType> billingAccountRoleTypes) {
        List<BillingAccountRoleTypeTransfer> billingAccountRoleTypeTransfers = new ArrayList<>(billingAccountRoleTypes.size());
        BillingAccountRoleTypeTransferCache billingAccountRoleTypeTransferCache = getPaymentTransferCaches(userVisit).getBillingAccountRoleTypeTransferCache();

        billingAccountRoleTypes.stream().forEach((billingAccountRoleType) -> {
            billingAccountRoleTypeTransfers.add(billingAccountRoleTypeTransferCache.getTransfer(billingAccountRoleType));
        });

            return billingAccountRoleTypeTransfers;
    }
    
    public List<BillingAccountRoleTypeTransfer> getBillingAccountRoleTypeTransfers(UserVisit userVisit) {
        return getBillingAccountRoleTypeTransfers(userVisit, getBillingAccountRoleTypes());
    }
    
    // --------------------------------------------------------------------------------
    //   Billing Account Role Type Description
    // --------------------------------------------------------------------------------
    
    public BillingAccountRoleTypeDescription createBillingAccountRoleTypeDescription(BillingAccountRoleType billingAccountRoleType, Language language, String description) {
        return BillingAccountRoleTypeDescriptionFactory.getInstance().create(billingAccountRoleType, language, description);
    }
    
    public BillingAccountRoleTypeDescription getBillingAccountRoleTypeDescription(BillingAccountRoleType billingAccountRoleType, Language language) {
        BillingAccountRoleTypeDescription billingAccountRoleTypeDescription = null;
        
        try {
            PreparedStatement ps = BillingAccountRoleTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM billingaccountroletypedescriptions " +
                    "WHERE bllactrtypd_bllactrtyp_billingaccountroletypeid = ? AND bllactrtypd_lang_languageid = ?");
            
            ps.setLong(1, billingAccountRoleType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            billingAccountRoleTypeDescription = BillingAccountRoleTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return billingAccountRoleTypeDescription;
    }
    
    public String getBestBillingAccountRoleTypeDescription(BillingAccountRoleType billingAccountRoleType, Language language) {
        String description;
        BillingAccountRoleTypeDescription billingAccountRoleTypeDescription = getBillingAccountRoleTypeDescription(billingAccountRoleType, language);
        
        if(billingAccountRoleTypeDescription == null && !language.getIsDefault()) {
            billingAccountRoleTypeDescription = getBillingAccountRoleTypeDescription(billingAccountRoleType, getPartyControl().getDefaultLanguage());
        }
        
        if(billingAccountRoleTypeDescription == null) {
            description = billingAccountRoleType.getBillingAccountRoleTypeName();
        } else {
            description = billingAccountRoleTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Payment Method Types
    // --------------------------------------------------------------------------------
    
    public PaymentMethodType createPaymentMethodType(String paymentMethodTypeName, Boolean isDefault, Integer sortOrder) {
        return PaymentMethodTypeFactory.getInstance().create(paymentMethodTypeName, isDefault, sortOrder);
    }
    
    public List<PaymentMethodType> getPaymentMethodTypes() {
        PreparedStatement ps = PaymentMethodTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM paymentmethodtypes " +
                "ORDER BY pmtyp_sortorder, pmtyp_paymentmethodtypename");
        
        return PaymentMethodTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public PaymentMethodType getPaymentMethodTypeByName(String paymentMethodTypeName) {
        PaymentMethodType paymentMethodType = null;
        
        try {
            PreparedStatement ps = PaymentMethodTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM paymentmethodtypes " +
                    "WHERE pmtyp_paymentmethodtypename = ?");
            
            ps.setString(1, paymentMethodTypeName);
            
            paymentMethodType = PaymentMethodTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return paymentMethodType;
    }
    
    public PaymentMethodTypeChoicesBean getPaymentMethodTypeChoices(String defaultPaymentMethodTypeChoice, Language language,
            boolean allowNullChoice) {
        List<PaymentMethodType> paymentMethodTypes = getPaymentMethodTypes();
        int size = paymentMethodTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
        }
        
        for(PaymentMethodType paymentMethodType: paymentMethodTypes) {
            String label = getBestPaymentMethodTypeDescription(paymentMethodType, language);
            String value = paymentMethodType.getPaymentMethodTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultPaymentMethodTypeChoice == null? false: defaultPaymentMethodTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && paymentMethodType.getIsDefault()))
                defaultValue = value;
        }
        
        return new PaymentMethodTypeChoicesBean(labels, values, defaultValue);
    }
    
    public PaymentMethodTypeTransfer getPaymentMethodTypeTransfer(UserVisit userVisit, PaymentMethodType paymentMethodType) {
        return getPaymentTransferCaches(userVisit).getPaymentMethodTypeTransferCache().getTransfer(paymentMethodType);
    }
    
    public List<PaymentMethodTypeTransfer> getPaymentMethodTypeTransfers(UserVisit userVisit) {
        List<PaymentMethodType> paymentMethodTypes = getPaymentMethodTypes();
        List<PaymentMethodTypeTransfer> paymentMethodTypeTransfers = new ArrayList<>(paymentMethodTypes.size());
        PaymentMethodTypeTransferCache paymentMethodTypeTransferCache = getPaymentTransferCaches(userVisit).getPaymentMethodTypeTransferCache();
        
        paymentMethodTypes.stream().forEach((paymentMethodType) -> {
            paymentMethodTypeTransfers.add(paymentMethodTypeTransferCache.getTransfer(paymentMethodType));
        });
        
        return paymentMethodTypeTransfers;
    }
    
    // --------------------------------------------------------------------------------
    //   Payment Method Type Descriptions
    // --------------------------------------------------------------------------------
    
    public PaymentMethodTypeDescription createPaymentMethodTypeDescription(PaymentMethodType paymentMethodType, Language language,
            String description) {
        return PaymentMethodTypeDescriptionFactory.getInstance().create(paymentMethodType, language, description);
    }
    
    public PaymentMethodTypeDescription getPaymentMethodTypeDescription(PaymentMethodType paymentMethodType, Language language) {
        PaymentMethodTypeDescription paymentMethodTypeDescription = null;
        
        try {
            PreparedStatement ps = PaymentMethodTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM paymentmethodtypedescriptions " +
                    "WHERE pmtypd_pmtyp_paymentmethodtypeid = ? AND pmtypd_lang_languageid = ?");
            
            ps.setLong(1, paymentMethodType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            paymentMethodTypeDescription = PaymentMethodTypeDescriptionFactory.getInstance().getEntityFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return paymentMethodTypeDescription;
    }
    
    public String getBestPaymentMethodTypeDescription(PaymentMethodType paymentMethodType, Language language) {
        String description;
        PaymentMethodTypeDescription paymentMethodTypeDescription = getPaymentMethodTypeDescription(paymentMethodType, language);
        
        if(paymentMethodTypeDescription == null && !language.getIsDefault()) {
            paymentMethodTypeDescription = getPaymentMethodTypeDescription(paymentMethodType, getPartyControl().getDefaultLanguage());
        }
        
        if(paymentMethodTypeDescription == null) {
            description = paymentMethodType.getPaymentMethodTypeName();
        } else {
            description = paymentMethodTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Payment Method Type Party Types
    // --------------------------------------------------------------------------------
    
    public PaymentMethodTypePartyType createPaymentMethodTypePartyType(PaymentMethodType paymentMethodType, PartyType partyType,
            Workflow partyPaymentMethodWorkflow, Workflow partyContactMechanismWorkflow) {
        return PaymentMethodTypePartyTypeFactory.getInstance().create(paymentMethodType, partyType, partyPaymentMethodWorkflow,
                partyContactMechanismWorkflow);
    }
    
    public PaymentMethodTypePartyType getPaymentMethodTypePartyType(PaymentMethodType paymentMethodType, PartyType partyType) {
        PaymentMethodTypePartyType paymentMethodTypePartyType = null;
        
        try {
            PreparedStatement ps = PaymentMethodTypePartyTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM paymentmethodtypepartytypes " +
                    "WHERE pmtypptyp_pmtyp_paymentmethodtypeid = ? AND pmtypptyp_ptyp_partytypeid = ?");
            
            ps.setLong(1, paymentMethodType.getPrimaryKey().getEntityId());
            ps.setLong(2, partyType.getPrimaryKey().getEntityId());
            
            paymentMethodTypePartyType = PaymentMethodTypePartyTypeFactory.getInstance().getEntityFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return paymentMethodTypePartyType;
    }
    
    private static final Map<EntityPermission, String> getPaymentMethodTypePartyTypesByPaymentMethodTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM paymentmethodtypepartytypes, partytypes " +
                "WHERE pmtypptyp_pmtyp_paymentmethodtypeid = ? " +
                "AND pmtypptyp_ptyp_partytypeid = ptyp_partytypeid " +
                "ORDER BY ptyp_sortorder, ptyp_partytypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM paymentmethodtypepartytypes " +
                "WHERE pmtypptyp_pmtyp_paymentmethodtypeid = ? " +
                "FOR UPDATE");
        getPaymentMethodTypePartyTypesByPaymentMethodTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PaymentMethodTypePartyType> getPaymentMethodTypePartyTypesByPaymentMethodType(PaymentMethodType paymentMethodType, EntityPermission entityPermission) {
        return PaymentMethodTypePartyTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getPaymentMethodTypePartyTypesByPaymentMethodTypeQueries,
                paymentMethodType);
    }

    public List<PaymentMethodTypePartyType> getPaymentMethodTypePartyTypesByPaymentMethodType(PaymentMethodType paymentMethodType) {
        return getPaymentMethodTypePartyTypesByPaymentMethodType(paymentMethodType, EntityPermission.READ_ONLY);
    }

    public List<PaymentMethodTypePartyType> getPaymentMethodTypePartyTypesByPaymentMethodTypeForUpdate(PaymentMethodType paymentMethodType) {
        return getPaymentMethodTypePartyTypesByPaymentMethodType(paymentMethodType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPaymentMethodTypePartyTypesByPartyTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM paymentmethodtypepartytypes, paymentmethodtypes " +
                "WHERE pmtypptyp_ptyp_partytypeid = ? " +
                "AND pmtypptyp_pmtyp_paymentmethodtypeid = pmtyp_paymentmethodtypeid " +
                "ORDER BY pmtyp_sortorder, pmtyp_paymentmethodtypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM paymentmethodtypepartytypes " +
                "WHERE pmtypptyp_ptyp_partytypeid = ? " +
                "FOR UPDATE");
        getPaymentMethodTypePartyTypesByPartyTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PaymentMethodTypePartyType> getPaymentMethodTypePartyTypesByPartyType(PaymentMethodType paymentMethodType, EntityPermission entityPermission) {
        return PaymentMethodTypePartyTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getPaymentMethodTypePartyTypesByPartyTypeQueries,
                paymentMethodType);
    }

    public List<PaymentMethodTypePartyType> getPaymentMethodTypePartyTypesByPartyType(PaymentMethodType paymentMethodType) {
        return getPaymentMethodTypePartyTypesByPartyType(paymentMethodType, EntityPermission.READ_ONLY);
    }

    public List<PaymentMethodTypePartyType> getPaymentMethodTypePartyTypesByPartyTypeForUpdate(PaymentMethodType paymentMethodType) {
        return getPaymentMethodTypePartyTypesByPartyType(paymentMethodType, EntityPermission.READ_WRITE);
    }

    public PaymentMethodTypePartyTypeTransfer getPaymentMethodTypePartyTypeTransfer(UserVisit userVisit, PaymentMethodTypePartyType paymentMethodTypePartyType) {
        return getPaymentTransferCaches(userVisit).getPaymentMethodTypePartyTypeTransferCache().getTransfer(paymentMethodTypePartyType);
    }

    public List<PaymentMethodTypePartyTypeTransfer> getPaymentMethodTypePartyTypeTransfers(UserVisit userVisit, List<PaymentMethodTypePartyType> paymentMethodTypePartyTypes) {
        List<PaymentMethodTypePartyTypeTransfer> paymentMethodTypePartyTypeTransfers = new ArrayList<>(paymentMethodTypePartyTypes.size());
        PaymentMethodTypePartyTypeTransferCache paymentMethodTypePartyTypeTransferCache = getPaymentTransferCaches(userVisit).getPaymentMethodTypePartyTypeTransferCache();

        paymentMethodTypePartyTypes.stream().forEach((paymentMethodTypePartyType) -> {
            paymentMethodTypePartyTypeTransfers.add(paymentMethodTypePartyTypeTransferCache.getTransfer(paymentMethodTypePartyType));
        });

        return paymentMethodTypePartyTypeTransfers;
    }

    public List<PaymentMethodTypePartyTypeTransfer> getPaymentMethodTypePartyTypeTransfersByPaymentMethodType(UserVisit userVisit, PaymentMethodType paymentMethodType) {
        return getPaymentMethodTypePartyTypeTransfers(userVisit, getPaymentMethodTypePartyTypesByPaymentMethodType(paymentMethodType));
    }

    public List<PaymentMethodTypePartyTypeTransfer> getPaymentMethodTypePartyTypeTransfersByPartyType(UserVisit userVisit, PaymentMethodType paymentMethodType) {
        return getPaymentMethodTypePartyTypeTransfers(userVisit, getPaymentMethodTypePartyTypesByPartyType(paymentMethodType));
    }

    // --------------------------------------------------------------------------------
    //   Payment Processor Types
    // --------------------------------------------------------------------------------
    
    public PaymentProcessorType createPaymentProcessorType(String paymentProcessorTypeName, Boolean isDefault, Integer sortOrder) {
        return PaymentProcessorTypeFactory.getInstance().create(paymentProcessorTypeName, isDefault, sortOrder);
    }
    
    public List<PaymentProcessorType> getPaymentProcessorTypes() {
        PreparedStatement ps = PaymentProcessorTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM paymentprocessortypes " +
                "ORDER BY pprctyp_sortorder, pprctyp_paymentprocessortypename");
        
        return PaymentProcessorTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public PaymentProcessorType getPaymentProcessorTypeByName(String paymentProcessorTypeName) {
        PaymentProcessorType paymentProcessorType = null;
        
        try {
            PreparedStatement ps = PaymentProcessorTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM paymentprocessortypes " +
                    "WHERE pprctyp_paymentprocessortypename = ?");
            
            ps.setString(1, paymentProcessorTypeName);
            
            paymentProcessorType = PaymentProcessorTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return paymentProcessorType;
    }
    
    public PaymentProcessorTypeChoicesBean getPaymentProcessorTypeChoices(String defaultPaymentProcessorTypeChoice, Language language,
            boolean allowNullChoice) {
        List<PaymentProcessorType> paymentProcessorTypes = getPaymentProcessorTypes();
        int size = paymentProcessorTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
        }
        
        for(PaymentProcessorType paymentProcessorType: paymentProcessorTypes) {
            String label = getBestPaymentProcessorTypeDescription(paymentProcessorType, language);
            String value = paymentProcessorType.getPaymentProcessorTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultPaymentProcessorTypeChoice == null? false: defaultPaymentProcessorTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && paymentProcessorType.getIsDefault()))
                defaultValue = value;
        }
        
        return new PaymentProcessorTypeChoicesBean(labels, values, defaultValue);
    }
    
    public PaymentProcessorTypeTransfer getPaymentProcessorTypeTransfer(UserVisit userVisit, PaymentProcessorType paymentProcessorType) {
        return getPaymentTransferCaches(userVisit).getPaymentProcessorTypeTransferCache().getTransfer(paymentProcessorType);
    }
    
    public List<PaymentProcessorTypeTransfer> getPaymentProcessorTypeTransfers(UserVisit userVisit) {
        List<PaymentProcessorType> paymentProcessorTypes = getPaymentProcessorTypes();
        List<PaymentProcessorTypeTransfer> paymentProcessorTypeTransfers = new ArrayList<>(paymentProcessorTypes.size());
        PaymentProcessorTypeTransferCache paymentProcessorTypeTransferCache = getPaymentTransferCaches(userVisit).getPaymentProcessorTypeTransferCache();
        
        paymentProcessorTypes.stream().forEach((paymentProcessorType) -> {
            paymentProcessorTypeTransfers.add(paymentProcessorTypeTransferCache.getTransfer(paymentProcessorType));
        });
        
        return paymentProcessorTypeTransfers;
    }
    
    // --------------------------------------------------------------------------------
    //   Payment Processor Type Descriptions
    // --------------------------------------------------------------------------------
    
    public PaymentProcessorTypeDescription createPaymentProcessorTypeDescription(PaymentProcessorType paymentProcessorType, Language language,
            String description) {
        return PaymentProcessorTypeDescriptionFactory.getInstance().create(paymentProcessorType, language, description);
    }
    
    public PaymentProcessorTypeDescription getPaymentProcessorTypeDescription(PaymentProcessorType paymentProcessorType, Language language) {
        PaymentProcessorTypeDescription paymentProcessorTypeDescription = null;
        
        try {
            PreparedStatement ps = PaymentProcessorTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM paymentprocessortypedescriptions " +
                    "WHERE pprctypd_pprctyp_paymentprocessortypeid = ? AND pprctypd_lang_languageid = ?");
            
            ps.setLong(1, paymentProcessorType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            paymentProcessorTypeDescription = PaymentProcessorTypeDescriptionFactory.getInstance().getEntityFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return paymentProcessorTypeDescription;
    }
    
    public String getBestPaymentProcessorTypeDescription(PaymentProcessorType paymentProcessorType, Language language) {
        String description;
        PaymentProcessorTypeDescription paymentProcessorTypeDescription = getPaymentProcessorTypeDescription(paymentProcessorType, language);
        
        if(paymentProcessorTypeDescription == null && !language.getIsDefault()) {
            paymentProcessorTypeDescription = getPaymentProcessorTypeDescription(paymentProcessorType, getPartyControl().getDefaultLanguage());
        }
        
        if(paymentProcessorTypeDescription == null) {
            description = paymentProcessorType.getPaymentProcessorTypeName();
        } else {
            description = paymentProcessorTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Payment Processors
    // --------------------------------------------------------------------------------
    
    public PaymentProcessor createPaymentProcessor(String paymentProcessorName, PaymentProcessorType paymentProcessorType,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        PaymentProcessor defaultPaymentProcessor = getDefaultPaymentProcessor();
        boolean defaultFound = defaultPaymentProcessor != null;
        
        if(defaultFound && isDefault) {
            PaymentProcessorDetailValue defaultPaymentProcessorDetailValue = getDefaultPaymentProcessorDetailValueForUpdate();
            
            defaultPaymentProcessorDetailValue.setIsDefault(Boolean.FALSE);
            updatePaymentProcessorFromValue(defaultPaymentProcessorDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        PaymentProcessor paymentProcessor = PaymentProcessorFactory.getInstance().create();
        PaymentProcessorDetail paymentProcessorDetail = PaymentProcessorDetailFactory.getInstance().create(session,
                paymentProcessor, paymentProcessorName, paymentProcessorType, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        paymentProcessor = PaymentProcessorFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                paymentProcessor.getPrimaryKey());
        paymentProcessor.setActiveDetail(paymentProcessorDetail);
        paymentProcessor.setLastDetail(paymentProcessorDetail);
        paymentProcessor.store();
        
        sendEventUsingNames(paymentProcessor.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return paymentProcessor;
    }
    
    public PaymentProcessorDetailValue getPaymentProcessorDetailValueForUpdate(PaymentProcessor paymentProcessor) {
        return paymentProcessor.getLastDetailForUpdate().getPaymentProcessorDetailValue().clone();
    }
    
    private PaymentProcessor getPaymentProcessorByName(String paymentProcessorName, EntityPermission entityPermission) {
        PaymentProcessor paymentProcessor = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentprocessors, paymentprocessordetails " +
                        "WHERE pprc_activedetailid = pprcdt_paymentprocessordetailid AND pprcdt_paymentprocessorname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentprocessors, paymentprocessordetails " +
                        "WHERE pprc_activedetailid = pprcdt_paymentprocessordetailid AND pprcdt_paymentprocessorname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PaymentProcessorFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, paymentProcessorName);
            
            paymentProcessor = PaymentProcessorFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return paymentProcessor;
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
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM paymentprocessors, paymentprocessordetails " +
                    "WHERE pprc_activedetailid = pprcdt_paymentprocessordetailid " +
                    "ORDER BY pprcdt_sortorder, pprcdt_paymentprocessorname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM paymentprocessors, paymentprocessordetails " +
                    "WHERE pprc_activedetailid = pprcdt_paymentprocessordetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = PaymentProcessorFactory.getInstance().prepareStatement(query);
        
        return PaymentProcessorFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<PaymentProcessor> getPaymentProcessors() {
        return getPaymentProcessors(EntityPermission.READ_ONLY);
    }
    
    public List<PaymentProcessor> getPaymentProcessorsForUpdate() {
        return getPaymentProcessors(EntityPermission.READ_WRITE);
    }
    
    private PaymentProcessor getDefaultPaymentProcessor(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM paymentprocessors, paymentprocessordetails " +
                    "WHERE pprc_activedetailid = pprcdt_paymentprocessordetailid AND pprcdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM paymentprocessors, paymentprocessordetails " +
                    "WHERE pprc_activedetailid = pprcdt_paymentprocessordetailid AND pprcdt_isdefault = 1 " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = PaymentProcessorFactory.getInstance().prepareStatement(query);
        
        return PaymentProcessorFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
        List<PaymentProcessor> paymentProcessors = getPaymentProcessors();
        int size = paymentProcessors.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
        }
        
        for(PaymentProcessor paymentProcessor: paymentProcessors) {
            PaymentProcessorDetail paymentProcessorDetail = paymentProcessor.getLastDetail();
            String label = getBestPaymentProcessorDescription(paymentProcessor, language);
            String value = paymentProcessorDetail.getPaymentProcessorName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultPaymentProcessorChoice == null? false: defaultPaymentProcessorChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && paymentProcessorDetail.getIsDefault()))
                defaultValue = value;
        }
        
        return new PaymentProcessorChoicesBean(labels, values, defaultValue);
    }
    
    public PaymentProcessorTransfer getPaymentProcessorTransfer(UserVisit userVisit, PaymentProcessor paymentProcessor) {
        return getPaymentTransferCaches(userVisit).getPaymentProcessorTransferCache().getTransfer(paymentProcessor);
    }
    
    public List<PaymentProcessorTransfer> getPaymentProcessorTransfers(UserVisit userVisit) {
        List<PaymentProcessor> paymentProcessors = getPaymentProcessors();
        List<PaymentProcessorTransfer> paymentProcessorTransfers = new ArrayList<>(paymentProcessors.size());
        PaymentProcessorTransferCache paymentProcessorTransferCache = getPaymentTransferCaches(userVisit).getPaymentProcessorTransferCache();
        
        paymentProcessors.stream().forEach((paymentProcessor) -> {
            paymentProcessorTransfers.add(paymentProcessorTransferCache.getTransfer(paymentProcessor));
        });
        
        return paymentProcessorTransfers;
    }
    
    private void updatePaymentProcessorFromValue(PaymentProcessorDetailValue paymentProcessorDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(paymentProcessorDetailValue.hasBeenModified()) {
            PaymentProcessor paymentProcessor = PaymentProcessorFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     paymentProcessorDetailValue.getPaymentProcessorPK());
            PaymentProcessorDetail paymentProcessorDetail = paymentProcessor.getActiveDetailForUpdate();
            
            paymentProcessorDetail.setThruTime(session.START_TIME_LONG);
            paymentProcessorDetail.store();
            
            PaymentProcessorPK paymentProcessorPK = paymentProcessorDetail.getPaymentProcessorPK(); // Not updated
            String paymentProcessorName = paymentProcessorDetailValue.getPaymentProcessorName();
            PaymentProcessorTypePK paymentProcessorTypePK = paymentProcessorDetail.getPaymentProcessorTypePK(); // Not updated
            Boolean isDefault = paymentProcessorDetailValue.getIsDefault();
            Integer sortOrder = paymentProcessorDetailValue.getSortOrder();
            
            if(checkDefault) {
                PaymentProcessor defaultPaymentProcessor = getDefaultPaymentProcessor();
                boolean defaultFound = defaultPaymentProcessor != null && !defaultPaymentProcessor.equals(paymentProcessor);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    PaymentProcessorDetailValue defaultPaymentProcessorDetailValue = getDefaultPaymentProcessorDetailValueForUpdate();
                    
                    defaultPaymentProcessorDetailValue.setIsDefault(Boolean.FALSE);
                    updatePaymentProcessorFromValue(defaultPaymentProcessorDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            paymentProcessorDetail = PaymentProcessorDetailFactory.getInstance().create(paymentProcessorPK,
                    paymentProcessorName, paymentProcessorTypePK, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            paymentProcessor.setActiveDetail(paymentProcessorDetail);
            paymentProcessor.setLastDetail(paymentProcessorDetail);
            
            sendEventUsingNames(paymentProcessorPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updatePaymentProcessorFromValue(PaymentProcessorDetailValue paymentProcessorDetailValue, BasePK updatedBy) {
        updatePaymentProcessorFromValue(paymentProcessorDetailValue, true, updatedBy);
    }
    
    public void deletePaymentProcessor(PaymentProcessor paymentProcessor, BasePK deletedBy) {
        deletePaymentMethodsByPaymentProcessor(paymentProcessor, deletedBy);
        deletePaymentProcessorDescriptionsByPaymentProcessor(paymentProcessor, deletedBy);
        
        PaymentProcessorDetail paymentProcessorDetail = paymentProcessor.getLastDetailForUpdate();
        paymentProcessorDetail.setThruTime(session.START_TIME_LONG);
        paymentProcessorDetail.store();
        paymentProcessor.setActiveDetail(null);
        
        // Check for default, and pick one if necessary
        PaymentProcessor defaultPaymentProcessor = getDefaultPaymentProcessor();
        if(defaultPaymentProcessor == null) {
            List<PaymentProcessor> paymentProcessors = getPaymentProcessorsForUpdate();
            
            if(!paymentProcessors.isEmpty()) {
                Iterator<PaymentProcessor> iter = paymentProcessors.iterator();
                if(iter.hasNext()) {
                    defaultPaymentProcessor = iter.next();
                }
                PaymentProcessorDetailValue paymentProcessorDetailValue = defaultPaymentProcessor.getLastDetailForUpdate().getPaymentProcessorDetailValue().clone();
                
                paymentProcessorDetailValue.setIsDefault(Boolean.TRUE);
                updatePaymentProcessorFromValue(paymentProcessorDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(paymentProcessor.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Payment Processor Descriptions
    // --------------------------------------------------------------------------------
    
    public PaymentProcessorDescription createPaymentProcessorDescription(PaymentProcessor paymentProcessor, Language language,
            String description, BasePK createdBy) {
        PaymentProcessorDescription paymentProcessorDescription = PaymentProcessorDescriptionFactory.getInstance().create(session,
                paymentProcessor, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(paymentProcessor.getPrimaryKey(), EventTypes.MODIFY.name(), paymentProcessorDescription.getPrimaryKey(),
                EventTypes.CREATE.name(), createdBy);
        
        return paymentProcessorDescription;
    }
    
    private PaymentProcessorDescription getPaymentProcessorDescription(PaymentProcessor paymentProcessor, Language language,
            EntityPermission entityPermission) {
        PaymentProcessorDescription paymentProcessorDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentprocessordescriptions " +
                        "WHERE pprcd_pprc_paymentprocessorid = ? AND pprcd_lang_languageid = ? AND pprcd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentprocessordescriptions " +
                        "WHERE pprcd_pprc_paymentprocessorid = ? AND pprcd_lang_languageid = ? AND pprcd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PaymentProcessorDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, paymentProcessor.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            paymentProcessorDescription = PaymentProcessorDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return paymentProcessorDescription;
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
        List<PaymentProcessorDescription> paymentProcessorDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentprocessordescriptions, languages " +
                        "WHERE pprcd_pprc_paymentprocessorid = ? AND pprcd_thrutime = ? AND pprcd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentprocessordescriptions " +
                        "WHERE pprcd_pprc_paymentprocessorid = ? AND pprcd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PaymentProcessorDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, paymentProcessor.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            paymentProcessorDescriptions = PaymentProcessorDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return paymentProcessorDescriptions;
    }
    
    public List<PaymentProcessorDescription> getPaymentProcessorDescriptions(PaymentProcessor paymentProcessor) {
        return getPaymentProcessorDescriptions(paymentProcessor, EntityPermission.READ_ONLY);
    }
    
    public List<PaymentProcessorDescription> getPaymentProcessorDescriptionsForUpdate(PaymentProcessor paymentProcessor) {
        return getPaymentProcessorDescriptions(paymentProcessor, EntityPermission.READ_WRITE);
    }
    
    public String getBestPaymentProcessorDescription(PaymentProcessor paymentProcessor, Language language) {
        String description;
        PaymentProcessorDescription paymentProcessorDescription = getPaymentProcessorDescription(paymentProcessor, language);
        
        if(paymentProcessorDescription == null && !language.getIsDefault()) {
            paymentProcessorDescription = getPaymentProcessorDescription(paymentProcessor, getPartyControl().getDefaultLanguage());
        }
        
        if(paymentProcessorDescription == null) {
            description = paymentProcessor.getLastDetail().getPaymentProcessorName();
        } else {
            description = paymentProcessorDescription.getDescription();
        }
        
        return description;
    }
    
    public PaymentProcessorDescriptionTransfer getPaymentProcessorDescriptionTransfer(UserVisit userVisit, PaymentProcessorDescription paymentProcessorDescription) {
        return getPaymentTransferCaches(userVisit).getPaymentProcessorDescriptionTransferCache().getTransfer(paymentProcessorDescription);
    }
    
    public List<PaymentProcessorDescriptionTransfer> getPaymentProcessorDescriptionTransfers(UserVisit userVisit, PaymentProcessor paymentProcessor) {
        List<PaymentProcessorDescription> paymentProcessorDescriptions = getPaymentProcessorDescriptions(paymentProcessor);
        List<PaymentProcessorDescriptionTransfer> paymentProcessorDescriptionTransfers = new ArrayList<>(paymentProcessorDescriptions.size());
        PaymentProcessorDescriptionTransferCache paymentProcessorDescriptionTransferCache = getPaymentTransferCaches(userVisit).getPaymentProcessorDescriptionTransferCache();
        
        paymentProcessorDescriptions.stream().forEach((paymentProcessorDescription) -> {
            paymentProcessorDescriptionTransfers.add(paymentProcessorDescriptionTransferCache.getTransfer(paymentProcessorDescription));
        });
        
        return paymentProcessorDescriptionTransfers;
    }
    
    public void updatePaymentProcessorDescriptionFromValue(PaymentProcessorDescriptionValue paymentProcessorDescriptionValue, BasePK updatedBy) {
        if(paymentProcessorDescriptionValue.hasBeenModified()) {
            PaymentProcessorDescription paymentProcessorDescription = PaymentProcessorDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     paymentProcessorDescriptionValue.getPrimaryKey());
            
            paymentProcessorDescription.setThruTime(session.START_TIME_LONG);
            paymentProcessorDescription.store();
            
            PaymentProcessor paymentProcessor = paymentProcessorDescription.getPaymentProcessor();
            Language language = paymentProcessorDescription.getLanguage();
            String description = paymentProcessorDescriptionValue.getDescription();
            
            paymentProcessorDescription = PaymentProcessorDescriptionFactory.getInstance().create(paymentProcessor, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(paymentProcessor.getPrimaryKey(), EventTypes.MODIFY.name(),
                    paymentProcessorDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deletePaymentProcessorDescription(PaymentProcessorDescription paymentProcessorDescription, BasePK deletedBy) {
        paymentProcessorDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(paymentProcessorDescription.getPaymentProcessorPK(), EventTypes.MODIFY.name(),
                paymentProcessorDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deletePaymentProcessorDescriptionsByPaymentProcessor(PaymentProcessor paymentProcessor, BasePK deletedBy) {
        List<PaymentProcessorDescription> paymentProcessorDescriptions = getPaymentProcessorDescriptionsForUpdate(paymentProcessor);
        
        paymentProcessorDescriptions.stream().forEach((paymentProcessorDescription) -> {
            deletePaymentProcessorDescription(paymentProcessorDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Payment Methods
    // --------------------------------------------------------------------------------
    
    public PaymentMethod createPaymentMethod(String paymentMethodName, PaymentMethodType paymentMethodType, PaymentProcessor paymentProcessor,
            Selector itemSelector, Selector salesOrderItemSelector, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        PaymentMethod defaultPaymentMethod = getDefaultPaymentMethod();
        boolean defaultFound = defaultPaymentMethod != null;
        
        if(defaultFound && isDefault) {
            PaymentMethodDetailValue defaultPaymentMethodDetailValue = getDefaultPaymentMethodDetailValueForUpdate();
            
            defaultPaymentMethodDetailValue.setIsDefault(Boolean.FALSE);
            updatePaymentMethodFromValue(defaultPaymentMethodDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        PaymentMethod paymentMethod = PaymentMethodFactory.getInstance().create();
        PaymentMethodDetail paymentMethodDetail = PaymentMethodDetailFactory.getInstance().create(paymentMethod, paymentMethodName, paymentMethodType,
                paymentProcessor, itemSelector, salesOrderItemSelector, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        paymentMethod = PaymentMethodFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, paymentMethod.getPrimaryKey());
        paymentMethod.setActiveDetail(paymentMethodDetail);
        paymentMethod.setLastDetail(paymentMethodDetail);
        paymentMethod.store();
        
        sendEventUsingNames(paymentMethod.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return paymentMethod;
    }
    
    public PaymentMethodDetailValue getPaymentMethodDetailValueForUpdate(PaymentMethod paymentMethod) {
        return paymentMethod.getLastDetailForUpdate().getPaymentMethodDetailValue().clone();
    }
    
    public PaymentMethod getPaymentMethodByName(String paymentMethodName, EntityPermission entityPermission) {
        PaymentMethod paymentMethod = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethods, paymentmethoddetails " +
                        "WHERE pm_activedetailid = pmdt_paymentmethoddetailid AND pmdt_paymentmethodname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethods, paymentmethoddetails " +
                        "WHERE pm_activedetailid = pmdt_paymentmethoddetailid AND pmdt_paymentmethodname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PaymentMethodFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, paymentMethodName);
            
            paymentMethod = PaymentMethodFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return paymentMethod;
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
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM paymentmethods, paymentmethoddetails " +
                    "WHERE pm_activedetailid = pmdt_paymentmethoddetailid " +
                    "ORDER BY pmdt_sortorder, pmdt_paymentmethodname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM paymentmethods, paymentmethoddetails " +
                    "WHERE pm_activedetailid = pmdt_paymentmethoddetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = PaymentMethodFactory.getInstance().prepareStatement(query);
        
        return PaymentMethodFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<PaymentMethod> getPaymentMethods() {
        return getPaymentMethods(EntityPermission.READ_ONLY);
    }
    
    public List<PaymentMethod> getPaymentMethodsForUpdate() {
        return getPaymentMethods(EntityPermission.READ_WRITE);
    }
    
    private List<PaymentMethod> getPaymentMethodsByPaymentMethodType(PaymentMethodType paymentMethodType, EntityPermission entityPermission) {
        List<PaymentMethod> paymentMethod = null;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethods, paymentmethoddetails " +
                        "WHERE pm_activedetailid = pmdt_paymentmethoddetailid AND pmdt_pmtyp_paymentmethodtypeid = ? " +
                        "ORDER BY pmdt_sortorder, pmdt_paymentmethodname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethods, paymentmethoddetails " +
                        "WHERE pm_activedetailid = pmdt_paymentmethoddetailid AND pmdt_pmtyp_paymentmethodtypeid = ? " +
                        "FOR UPDATE";
            }

            PreparedStatement ps = PaymentMethodFactory.getInstance().prepareStatement(query);

            ps.setLong(1, paymentMethodType.getPrimaryKey().getEntityId());

            paymentMethod = PaymentMethodFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return paymentMethod;
    }

    public List<PaymentMethod> getPaymentMethodsByPaymentMethodType(PaymentMethodType paymentMethodType) {
        return getPaymentMethodsByPaymentMethodType(paymentMethodType, EntityPermission.READ_ONLY);
    }

    public List<PaymentMethod> getPaymentMethodsByPaymentMethodTypeForUpdate(PaymentMethodType paymentMethodType) {
        return getPaymentMethodsByPaymentMethodType(paymentMethodType, EntityPermission.READ_WRITE);
    }

    private List<PaymentMethod> getPaymentMethodsByPaymentProcessor(PaymentProcessor paymentProcessor, EntityPermission entityPermission) {
        List<PaymentMethod> paymentMethod = null;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethods, paymentmethoddetails " +
                        "WHERE pm_activedetailid = pmdt_paymentmethoddetailid AND pmdt_pprc_paymentprocessorid = ? " +
                        "ORDER BY pmdt_sortorder, pmdt_paymentmethodname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethods, paymentmethoddetails " +
                        "WHERE pm_activedetailid = pmdt_paymentmethoddetailid AND pmdt_pprc_paymentprocessorid = ? " +
                        "FOR UPDATE";
            }

            PreparedStatement ps = PaymentMethodFactory.getInstance().prepareStatement(query);

            ps.setLong(1, paymentProcessor.getPrimaryKey().getEntityId());

            paymentMethod = PaymentMethodFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return paymentMethod;
    }

    public List<PaymentMethod> getPaymentMethodsByPaymentProcessor(PaymentProcessor paymentProcessor) {
        return getPaymentMethodsByPaymentProcessor(paymentProcessor, EntityPermission.READ_ONLY);
    }

    public List<PaymentMethod> getPaymentMethodsByPaymentProcessorForUpdate(PaymentProcessor paymentProcessor) {
        return getPaymentMethodsByPaymentProcessor(paymentProcessor, EntityPermission.READ_WRITE);
    }

    private PaymentMethod getDefaultPaymentMethod(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM paymentmethods, paymentmethoddetails " +
                    "WHERE pm_activedetailid = pmdt_paymentmethoddetailid AND pmdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM paymentmethods, paymentmethoddetails " +
                    "WHERE pm_activedetailid = pmdt_paymentmethoddetailid AND pmdt_isdefault = 1 " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = PaymentMethodFactory.getInstance().prepareStatement(query);
        
        return PaymentMethodFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
        int size = paymentMethods.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");
        }

        for(PaymentMethod paymentMethod: paymentMethods) {
            PaymentMethodDetail paymentMethodDetail = paymentMethod.getLastDetail();
            String label = getBestPaymentMethodDescription(paymentMethod, language);
            String value = paymentMethodDetail.getPaymentMethodName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultPaymentMethodChoice == null? false: defaultPaymentMethodChoice.equals(value);
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
        return getPaymentTransferCaches(userVisit).getPaymentMethodTransferCache().getTransfer(paymentMethod);
    }
    
    public List<PaymentMethodTransfer> getPaymentMethodTransfers(UserVisit userVisit, List<PaymentMethod> paymentMethods) {
        List<PaymentMethodTransfer> paymentMethodTransfers = new ArrayList<>(paymentMethods.size());
        PaymentMethodTransferCache paymentMethodTransferCache = getPaymentTransferCaches(userVisit).getPaymentMethodTransferCache();

        paymentMethods.stream().forEach((paymentMethod) -> {
            paymentMethodTransfers.add(paymentMethodTransferCache.getTransfer(paymentMethod));
        });

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
            PaymentMethod paymentMethod = PaymentMethodFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     paymentMethodDetailValue.getPaymentMethodPK());
            PaymentMethodDetail paymentMethodDetail = paymentMethod.getActiveDetailForUpdate();
            
            paymentMethodDetail.setThruTime(session.START_TIME_LONG);
            paymentMethodDetail.store();
            
            PaymentMethodPK paymentMethodPK = paymentMethodDetail.getPaymentMethodPK(); // Not updated
            String paymentMethodName = paymentMethodDetailValue.getPaymentMethodName();
            PaymentMethodTypePK paymentMethodTypePK = paymentMethodDetail.getPaymentMethodTypePK(); // Not updated
            PaymentProcessorPK paymentProcessorPK = paymentMethodDetailValue.getPaymentProcessorPK();
            SelectorPK itemSelectorPK = paymentMethodDetailValue.getItemSelectorPK();
            SelectorPK salesOrderItemSelectorPK = paymentMethodDetailValue.getSalesOrderItemSelectorPK();
            Boolean isDefault = paymentMethodDetailValue.getIsDefault();
            Integer sortOrder = paymentMethodDetailValue.getSortOrder();
            
            if(checkDefault) {
                PaymentMethod defaultPaymentMethod = getDefaultPaymentMethod();
                boolean defaultFound = defaultPaymentMethod != null && !defaultPaymentMethod.equals(paymentMethod);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    PaymentMethodDetailValue defaultPaymentMethodDetailValue = getDefaultPaymentMethodDetailValueForUpdate();
                    
                    defaultPaymentMethodDetailValue.setIsDefault(Boolean.FALSE);
                    updatePaymentMethodFromValue(defaultPaymentMethodDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            paymentMethodDetail = PaymentMethodDetailFactory.getInstance().create(paymentMethodPK, paymentMethodName, paymentMethodTypePK, paymentProcessorPK,
                    itemSelectorPK, salesOrderItemSelectorPK, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            paymentMethod.setActiveDetail(paymentMethodDetail);
            paymentMethod.setLastDetail(paymentMethodDetail);
            
            sendEventUsingNames(paymentMethodPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updatePaymentMethodFromValue(PaymentMethodDetailValue paymentMethodDetailValue, BasePK updatedBy) {
        updatePaymentMethodFromValue(paymentMethodDetailValue, true, updatedBy);
    }
    
    public void deletePaymentMethod(PaymentMethod paymentMethod, BasePK deletedBy) {
        var customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
        var orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        
        customerControl.deleteCustomerTypePaymentMethodsByPaymentMethod(paymentMethod, deletedBy);
        orderControl.deleteOrderPaymentPreferencesByPaymentMethod(paymentMethod, deletedBy);
        deletePartyPaymentMethodsByPaymentMethod(paymentMethod, deletedBy);
        deletePaymentMethodDescriptionsByPaymentMethod(paymentMethod, deletedBy);
        
        PaymentMethodDetail paymentMethodDetail = paymentMethod.getLastDetailForUpdate();
        paymentMethodDetail.setThruTime(session.START_TIME_LONG);
        paymentMethodDetail.store();
        paymentMethod.setActiveDetail(null);
        
        // Check for default, and pick one if necessary
        PaymentMethod defaultPaymentMethod = getDefaultPaymentMethod();
        if(defaultPaymentMethod == null) {
            List<PaymentMethod> paymentMethods = getPaymentMethodsForUpdate();
            
            if(!paymentMethods.isEmpty()) {
                Iterator<PaymentMethod> iter = paymentMethods.iterator();
                if(iter.hasNext()) {
                    defaultPaymentMethod = iter.next();
                }
                PaymentMethodDetailValue paymentMethodDetailValue = defaultPaymentMethod.getLastDetailForUpdate().getPaymentMethodDetailValue().clone();
                
                paymentMethodDetailValue.setIsDefault(Boolean.TRUE);
                updatePaymentMethodFromValue(paymentMethodDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(paymentMethod.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deletePaymentMethodsByPaymentProcessor(PaymentProcessor paymentProcessor, BasePK deletedBy) {
        List<PaymentMethod> paymentMethods = getPaymentMethodsByPaymentProcessorForUpdate(paymentProcessor);
        
        paymentMethods.stream().forEach((paymentMethod) -> {
            deletePaymentMethod(paymentMethod, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Payment Method Descriptions
    // --------------------------------------------------------------------------------
    
    public PaymentMethodDescription createPaymentMethodDescription(PaymentMethod paymentMethod, Language language,
            String description, BasePK createdBy) {
        PaymentMethodDescription paymentMethodDescription = PaymentMethodDescriptionFactory.getInstance().create(session,
                paymentMethod, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(paymentMethod.getPrimaryKey(), EventTypes.MODIFY.name(), paymentMethodDescription.getPrimaryKey(),
                EventTypes.CREATE.name(), createdBy);
        
        return paymentMethodDescription;
    }
    
    private PaymentMethodDescription getPaymentMethodDescription(PaymentMethod paymentMethod, Language language,
            EntityPermission entityPermission) {
        PaymentMethodDescription paymentMethodDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethoddescriptions " +
                        "WHERE pmd_pm_paymentmethodid = ? AND pmd_lang_languageid = ? AND pmd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethoddescriptions " +
                        "WHERE pmd_pm_paymentmethodid = ? AND pmd_lang_languageid = ? AND pmd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PaymentMethodDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, paymentMethod.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            paymentMethodDescription = PaymentMethodDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return paymentMethodDescription;
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
        List<PaymentMethodDescription> paymentMethodDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethoddescriptions, languages " +
                        "WHERE pmd_pm_paymentmethodid = ? AND pmd_thrutime = ? AND pmd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethoddescriptions " +
                        "WHERE pmd_pm_paymentmethodid = ? AND pmd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PaymentMethodDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, paymentMethod.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            paymentMethodDescriptions = PaymentMethodDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return paymentMethodDescriptions;
    }
    
    public List<PaymentMethodDescription> getPaymentMethodDescriptions(PaymentMethod paymentMethod) {
        return getPaymentMethodDescriptions(paymentMethod, EntityPermission.READ_ONLY);
    }
    
    public List<PaymentMethodDescription> getPaymentMethodDescriptionsForUpdate(PaymentMethod paymentMethod) {
        return getPaymentMethodDescriptions(paymentMethod, EntityPermission.READ_WRITE);
    }
    
    public String getBestPaymentMethodDescription(PaymentMethod paymentMethod, Language language) {
        String description;
        PaymentMethodDescription paymentMethodDescription = getPaymentMethodDescription(paymentMethod, language);
        
        if(paymentMethodDescription == null && !language.getIsDefault()) {
            paymentMethodDescription = getPaymentMethodDescription(paymentMethod, getPartyControl().getDefaultLanguage());
        }
        
        if(paymentMethodDescription == null) {
            description = paymentMethod.getLastDetail().getPaymentMethodName();
        } else {
            description = paymentMethodDescription.getDescription();
        }
        
        return description;
    }
    
    public PaymentMethodDescriptionTransfer getPaymentMethodDescriptionTransfer(UserVisit userVisit, PaymentMethodDescription paymentMethodDescription) {
        return getPaymentTransferCaches(userVisit).getPaymentMethodDescriptionTransferCache().getTransfer(paymentMethodDescription);
    }
    
    public List<PaymentMethodDescriptionTransfer> getPaymentMethodDescriptionTransfers(UserVisit userVisit, PaymentMethod paymentMethod) {
        List<PaymentMethodDescription> paymentMethodDescriptions = getPaymentMethodDescriptions(paymentMethod);
        List<PaymentMethodDescriptionTransfer> paymentMethodDescriptionTransfers = new ArrayList<>(paymentMethodDescriptions.size());
        PaymentMethodDescriptionTransferCache paymentMethodDescriptionTransferCache = getPaymentTransferCaches(userVisit).getPaymentMethodDescriptionTransferCache();
        
        paymentMethodDescriptions.stream().forEach((paymentMethodDescription) -> {
            paymentMethodDescriptionTransfers.add(paymentMethodDescriptionTransferCache.getTransfer(paymentMethodDescription));
        });
        
        return paymentMethodDescriptionTransfers;
    }
    
    public void updatePaymentMethodDescriptionFromValue(PaymentMethodDescriptionValue paymentMethodDescriptionValue, BasePK updatedBy) {
        if(paymentMethodDescriptionValue.hasBeenModified()) {
            PaymentMethodDescription paymentMethodDescription = PaymentMethodDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     paymentMethodDescriptionValue.getPrimaryKey());
            
            paymentMethodDescription.setThruTime(session.START_TIME_LONG);
            paymentMethodDescription.store();
            
            PaymentMethod paymentMethod = paymentMethodDescription.getPaymentMethod();
            Language language = paymentMethodDescription.getLanguage();
            String description = paymentMethodDescriptionValue.getDescription();
            
            paymentMethodDescription = PaymentMethodDescriptionFactory.getInstance().create(paymentMethod, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(paymentMethod.getPrimaryKey(), EventTypes.MODIFY.name(),
                    paymentMethodDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deletePaymentMethodDescription(PaymentMethodDescription paymentMethodDescription, BasePK deletedBy) {
        paymentMethodDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(paymentMethodDescription.getPaymentMethodPK(), EventTypes.MODIFY.name(),
                paymentMethodDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deletePaymentMethodDescriptionsByPaymentMethod(PaymentMethod paymentMethod, BasePK deletedBy) {
        List<PaymentMethodDescription> paymentMethodDescriptions = getPaymentMethodDescriptionsForUpdate(paymentMethod);
        
        paymentMethodDescriptions.stream().forEach((paymentMethodDescription) -> {
            deletePaymentMethodDescription(paymentMethodDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Payment Method Checks
    // --------------------------------------------------------------------------------
    
    public PaymentMethodCheck createPaymentMethodCheck(PaymentMethod paymentMethod, Integer holdDays, BasePK createdBy) {
        PaymentMethodCheck paymentMethodCheck = PaymentMethodCheckFactory.getInstance().create(paymentMethod, holdDays,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(paymentMethod.getPrimaryKey(), EventTypes.MODIFY.name(), paymentMethodCheck.getPrimaryKey(),
                EventTypes.CREATE.name(), createdBy);
        
        return paymentMethodCheck;
    }
    
    private PaymentMethodCheck getPaymentMethodCheck(PaymentMethod paymentMethod, EntityPermission entityPermission) {
        PaymentMethodCheck paymentMethodCheck = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethodchecks " +
                        "WHERE pmchk_pm_paymentmethodid = ? AND pmchk_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethodchecks " +
                        "WHERE pmchk_pm_paymentmethodid = ? AND pmchk_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PaymentMethodCheckFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, paymentMethod.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            paymentMethodCheck = PaymentMethodCheckFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return paymentMethodCheck;
    }
    
    public PaymentMethodCheck getPaymentMethodCheck(PaymentMethod paymentMethod) {
        return getPaymentMethodCheck(paymentMethod, EntityPermission.READ_ONLY);
    }
    
    public PaymentMethodCheck getPaymentMethodCheckForUpdate(PaymentMethod paymentMethod) {
        return getPaymentMethodCheck(paymentMethod, EntityPermission.READ_WRITE);
    }
    
    public PaymentMethodCheckValue getPaymentMethodCheckValueForUpdate(PaymentMethod paymentMethod) {
        PaymentMethodCheck paymentMethodCheck = getPaymentMethodCheckForUpdate(paymentMethod);
        
        return paymentMethodCheck == null? null: paymentMethodCheck.getPaymentMethodCheckValue().clone();
    }
    
    public void updatePaymentMethodCheckFromValue(PaymentMethodCheckValue paymentMethodCheckValue, BasePK updatedBy) {
        if(paymentMethodCheckValue.hasBeenModified()) {
            PaymentMethodCheck paymentMethodCheck = PaymentMethodCheckFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     paymentMethodCheckValue.getPrimaryKey());
            
            paymentMethodCheck.setThruTime(session.START_TIME_LONG);
            paymentMethodCheck.store();
            
            PaymentMethodPK paymentMethodPK = paymentMethodCheck.getPaymentMethodPK(); // Not updated
            Integer holdDays = paymentMethodCheckValue.getHoldDays();
            
            paymentMethodCheck = PaymentMethodCheckFactory.getInstance().create(paymentMethodPK, holdDays,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(paymentMethodPK, EventTypes.MODIFY.name(), paymentMethodCheck.getPrimaryKey(),
                    EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deletePaymentMethodCheck(PaymentMethodCheck paymentMethodCheck, BasePK deletedBy) {
        paymentMethodCheck.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(paymentMethodCheck.getPaymentMethodPK(), EventTypes.MODIFY.name(),
                paymentMethodCheck.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Payment Method Credit Cards
    // --------------------------------------------------------------------------------
    
    public PaymentMethodCreditCard createPaymentMethodCreditCard(PaymentMethod paymentMethod, Boolean requestNameOnCard,
            Boolean requireNameOnCard, Boolean checkCardNumber, Boolean requestExpirationDate, Boolean requireExpirationDate,
            Boolean checkExpirationDate, Boolean requestSecurityCode, Boolean requireSecurityCode,
            String cardNumberValidationPattern, String securityCodeValidationPattern, Boolean retainCreditCard,
            Boolean retainSecurityCode, Boolean requestBilling, Boolean requireBilling, Boolean requestIssuer, Boolean requireIssuer, BasePK createdBy) {
        PaymentMethodCreditCard paymentMethodCreditCard = PaymentMethodCreditCardFactory.getInstance().create(session,
                paymentMethod, requestNameOnCard, requireNameOnCard, checkCardNumber, requestExpirationDate, requireExpirationDate,
                checkExpirationDate, requestSecurityCode, requireSecurityCode, cardNumberValidationPattern,
                securityCodeValidationPattern, retainCreditCard, retainSecurityCode, requestBilling, requireBilling, requestIssuer, requireIssuer,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(paymentMethod.getPrimaryKey(), EventTypes.MODIFY.name(), paymentMethodCreditCard.getPrimaryKey(),
                EventTypes.CREATE.name(), createdBy);
        
        return paymentMethodCreditCard;
    }
    
    private PaymentMethodCreditCard getPaymentMethodCreditCard(PaymentMethod paymentMethod, EntityPermission entityPermission) {
        PaymentMethodCreditCard paymentMethodCreditCard = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethodcreditcards " +
                        "WHERE pmcc_pm_paymentmethodid = ? AND pmcc_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethodcreditcards " +
                        "WHERE pmcc_pm_paymentmethodid = ? AND pmcc_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PaymentMethodCreditCardFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, paymentMethod.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            paymentMethodCreditCard = PaymentMethodCreditCardFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return paymentMethodCreditCard;
    }
    
    public PaymentMethodCreditCard getPaymentMethodCreditCard(PaymentMethod paymentMethod) {
        return getPaymentMethodCreditCard(paymentMethod, EntityPermission.READ_ONLY);
    }
    
    public PaymentMethodCreditCard getPaymentMethodCreditCardForUpdate(PaymentMethod paymentMethod) {
        return getPaymentMethodCreditCard(paymentMethod, EntityPermission.READ_WRITE);
    }
    
    public PaymentMethodCreditCardValue getPaymentMethodCreditCardValueForUpdate(PaymentMethod paymentMethod) {
        PaymentMethodCreditCard paymentMethodCreditCard = getPaymentMethodCreditCardForUpdate(paymentMethod);
        
        return paymentMethodCreditCard == null? null: paymentMethodCreditCard.getPaymentMethodCreditCardValue().clone();
    }
    
    public void updatePaymentMethodCreditCardFromValue(PaymentMethodCreditCardValue paymentMethodCreditCardValue, BasePK updatedBy) {
        if(paymentMethodCreditCardValue.hasBeenModified()) {
            PaymentMethodCreditCard paymentMethodCreditCard = PaymentMethodCreditCardFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     paymentMethodCreditCardValue.getPrimaryKey());
            
            paymentMethodCreditCard.setThruTime(session.START_TIME_LONG);
            paymentMethodCreditCard.store();
            
            PaymentMethodPK paymentMethodPK = paymentMethodCreditCard.getPaymentMethodPK(); // Not updated
            Boolean requestNameOnCard = paymentMethodCreditCardValue.getRequestNameOnCard();
            Boolean reqireNameOnCard = paymentMethodCreditCardValue.getRequireNameOnCard();
            Boolean checkCardNumber = paymentMethodCreditCardValue.getCheckCardNumber();
            Boolean requestExpirationDate = paymentMethodCreditCardValue.getRequireExpirationDate();
            Boolean requireExpirationDate = paymentMethodCreditCardValue.getRequireExpirationDate();
            Boolean checkExpirationDate = paymentMethodCreditCardValue.getCheckExpirationDate();
            Boolean requestSecurityCode = paymentMethodCreditCardValue.getRequestSecurityCode();
            Boolean requireSecurityCode = paymentMethodCreditCardValue.getRequireSecurityCode();
            String cardNumberValidationPattern = paymentMethodCreditCardValue.getCardNumberValidationPattern();
            String securityCodeValidationPattern = paymentMethodCreditCardValue.getSecurityCodeValidationPattern();
            Boolean retainCreditCard = paymentMethodCreditCardValue.getRetainCreditCard();
            Boolean retainSecurityCode = paymentMethodCreditCardValue.getRetainSecurityCode();
            Boolean requestBilling = paymentMethodCreditCardValue.getRequestBilling();
            Boolean requireBilling = paymentMethodCreditCardValue.getRequireBilling();
            Boolean requestIssuer = paymentMethodCreditCardValue.getRequestIssuer();
            Boolean requireIssuer = paymentMethodCreditCardValue.getRequireIssuer();
            
            paymentMethodCreditCard = PaymentMethodCreditCardFactory.getInstance().create(paymentMethodPK,
                    requestNameOnCard, reqireNameOnCard, checkCardNumber, requestExpirationDate, requireExpirationDate,
                    checkExpirationDate, requestSecurityCode, requireSecurityCode, cardNumberValidationPattern,
                    securityCodeValidationPattern, retainCreditCard, retainSecurityCode, requestBilling, requireBilling, requestIssuer, requireIssuer,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(paymentMethodPK, EventTypes.MODIFY.name(), paymentMethodCreditCard.getPrimaryKey(),
                    EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deletePaymentMethodCreditCard(PaymentMethodCreditCard paymentMethodCreditCard, BasePK deletedBy) {
        paymentMethodCreditCard.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(paymentMethodCreditCard.getPaymentMethodPK(), EventTypes.MODIFY.name(),
                paymentMethodCreditCard.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Payment Methods
    // --------------------------------------------------------------------------------
    
    public PartyPaymentMethod createPartyPaymentMethod(Party party, String description, PaymentMethod paymentMethod,
            Boolean deleteWhenUnused, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
        SequenceType sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.PARTY_PAYMENT_METHOD.name());
        Sequence sequence = sequenceControl.getDefaultSequence(sequenceType);
        String partyPaymentMethodName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);

        return createPartyPaymentMethod(partyPaymentMethodName, party, description, paymentMethod, deleteWhenUnused, isDefault,
                sortOrder, createdBy);
    }

    public PartyPaymentMethod createPartyPaymentMethod(String partyPaymentMethodName, Party party, String description, PaymentMethod paymentMethod,
            Boolean deleteWhenUnused, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        PartyPaymentMethod defaultPartyPaymentMethod = getDefaultPartyPaymentMethod(party);
        boolean defaultFound = defaultPartyPaymentMethod != null;

        if(defaultFound && isDefault) {
            PartyPaymentMethodDetailValue defaultPartyPaymentMethodDetailValue = getDefaultPartyPaymentMethodDetailValueForUpdate(party);

            defaultPartyPaymentMethodDetailValue.setIsDefault(Boolean.FALSE);
            updatePartyPaymentMethodFromValue(defaultPartyPaymentMethodDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        PartyPaymentMethod partyPaymentMethod = PartyPaymentMethodFactory.getInstance().create();
        PartyPaymentMethodDetail partyPaymentMethodDetail = PartyPaymentMethodDetailFactory.getInstance().create(partyPaymentMethod, partyPaymentMethodName,
                party, description, paymentMethod, deleteWhenUnused, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        partyPaymentMethod = PartyPaymentMethodFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, partyPaymentMethod.getPrimaryKey());
        partyPaymentMethod.setActiveDetail(partyPaymentMethodDetail);
        partyPaymentMethod.setLastDetail(partyPaymentMethodDetail);
        partyPaymentMethod.store();

        sendEventUsingNames(partyPaymentMethod.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return partyPaymentMethod;
    }

    private static final Map<EntityPermission, String> getPartyPaymentMethodByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partypaymentmethods, partypaymentmethoddetails " +
                "WHERE parpm_activedetailid = parpmdt_partypaymentmethoddetailid AND parpmdt_partypaymentmethodname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partypaymentmethods, partypaymentmethoddetails " +
                "WHERE parpm_activedetailid = parpmdt_partypaymentmethoddetailid AND parpmdt_partypaymentmethodname = ? " +
                "FOR UPDATE");
        getPartyPaymentMethodByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public PartyPaymentMethod getPartyPaymentMethodByName(String partyPaymentMethodName, EntityPermission entityPermission) {
        return PartyPaymentMethodFactory.getInstance().getEntityFromQuery(entityPermission, getPartyPaymentMethodByNameQueries,
                partyPaymentMethodName);
    }

    public PartyPaymentMethod getPartyPaymentMethodByName(String partyPaymentMethodName) {
        return getPartyPaymentMethodByName(partyPaymentMethodName, EntityPermission.READ_ONLY);
    }

    public PartyPaymentMethod getPartyPaymentMethodByNameForUpdate(String partyPaymentMethodName) {
        return getPartyPaymentMethodByName(partyPaymentMethodName, EntityPermission.READ_WRITE);
    }

    public PartyPaymentMethodDetailValue getPartyPaymentMethodDetailValueForUpdate(PartyPaymentMethod partyPaymentMethod) {
        return partyPaymentMethod == null? null: partyPaymentMethod.getLastDetailForUpdate().getPartyPaymentMethodDetailValue().clone();
    }

    public PartyPaymentMethodDetailValue getPartyPaymentMethodDetailValueByNameForUpdate(String partyPaymentMethodName) {
        return getPartyPaymentMethodDetailValueForUpdate(getPartyPaymentMethodByNameForUpdate(partyPaymentMethodName));
    }

    private static final Map<EntityPermission, String> getDefaultPartyPaymentMethodQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partypaymentmethods, partypaymentmethoddetails " +
                "WHERE parpm_activedetailid = parpmdt_partypaymentmethoddetailid " +
                "AND parpmdt_par_partyid = ? AND parpmdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partypaymentmethods, partypaymentmethoddetails " +
                "WHERE parpm_activedetailid = parpmdt_partypaymentmethoddetailid " +
                "AND parpmdt_par_partyid = ? AND parpmdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultPartyPaymentMethodQueries = Collections.unmodifiableMap(queryMap);
    }

    private PartyPaymentMethod getDefaultPartyPaymentMethod(Party party, EntityPermission entityPermission) {
        return PartyPaymentMethodFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultPartyPaymentMethodQueries,
                party);
    }

    public PartyPaymentMethod getDefaultPartyPaymentMethod(Party party) {
        return getDefaultPartyPaymentMethod(party, EntityPermission.READ_ONLY);
    }

    public PartyPaymentMethod getDefaultPartyPaymentMethodForUpdate(Party party) {
        return getDefaultPartyPaymentMethod(party, EntityPermission.READ_WRITE);
    }

    public PartyPaymentMethodDetailValue getDefaultPartyPaymentMethodDetailValueForUpdate(Party party) {
        return getDefaultPartyPaymentMethodForUpdate(party).getLastDetailForUpdate().getPartyPaymentMethodDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getPartyPaymentMethodsByPartyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partypaymentmethods, partypaymentmethoddetails " +
                "WHERE parpm_activedetailid = parpmdt_partypaymentmethoddetailid AND parpmdt_par_partyid = ? " +
                "ORDER BY parpmdt_sortorder, parpmdt_partypaymentmethodname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partypaymentmethods, partypaymentmethoddetails " +
                "WHERE parpm_activedetailid = parpmdt_partypaymentmethoddetailid AND parpmdt_par_partyid = ? " +
                "FOR UPDATE");
        getPartyPaymentMethodsByPartyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyPaymentMethod> getPartyPaymentMethodsByParty(Party party, EntityPermission entityPermission) {
        return PartyPaymentMethodFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyPaymentMethodsByPartyQueries,
                party);
    }

    public List<PartyPaymentMethod> getPartyPaymentMethodsByParty(Party party) {
        return getPartyPaymentMethodsByParty(party, EntityPermission.READ_ONLY);
    }

    public List<PartyPaymentMethod> getPartyPaymentMethodsByPartyForUpdate(Party party) {
        return getPartyPaymentMethodsByParty(party, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPartyPaymentMethodsByPaymentMethodQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partypaymentmethods, partypaymentmethoddetails " +
                "WHERE parpm_activedetailid = parpmdt_partypaymentmethoddetailid AND parpmdt_pm_paymentmethodid = ? " +
                "ORDER BY parpmdt_sortorder, parpmdt_partypaymentmethodname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partypaymentmethods, partypaymentmethoddetails " +
                "WHERE parpm_activedetailid = parpmdt_partypaymentmethoddetailid AND parpmdt_pm_paymentmethodid = ? " +
                "FOR UPDATE");
        getPartyPaymentMethodsByPaymentMethodQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyPaymentMethod> getPartyPaymentMethodsByPaymentMethod(PaymentMethod paymentMethod, EntityPermission entityPermission) {
        return PartyPaymentMethodFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyPaymentMethodsByPaymentMethodQueries,
                paymentMethod);
    }

    public List<PartyPaymentMethod> getPartyPaymentMethodsByPaymentMethod(PaymentMethod paymentMethod) {
        return getPartyPaymentMethodsByPaymentMethod(paymentMethod, EntityPermission.READ_ONLY);
    }

    public List<PartyPaymentMethod> getPartyPaymentMethodsByPaymentMethodForUpdate(PaymentMethod paymentMethod) {
        return getPartyPaymentMethodsByPaymentMethod(paymentMethod, EntityPermission.READ_WRITE);
    }

    public PartyPaymentMethodTransfer getPartyPaymentMethodTransfer(UserVisit userVisit, PartyPaymentMethod partyPaymentMethod) {
        return getPaymentTransferCaches(userVisit).getPartyPaymentMethodTransferCache().getTransfer(partyPaymentMethod);
    }

    public List<PartyPaymentMethodTransfer> getPartyPaymentMethodTransfers(UserVisit userVisit, List<PartyPaymentMethod> partyPaymentMethods) {
        List<PartyPaymentMethodTransfer> partyPaymentMethodTransfers = new ArrayList<>(partyPaymentMethods.size());
        PartyPaymentMethodTransferCache partyPaymentMethodTransferCache = getPaymentTransferCaches(userVisit).getPartyPaymentMethodTransferCache();

        partyPaymentMethods.stream().forEach((partyPaymentMethod) -> {
            partyPaymentMethodTransfers.add(partyPaymentMethodTransferCache.getTransfer(partyPaymentMethod));
        });

        return partyPaymentMethodTransfers;
    }

    public List<PartyPaymentMethodTransfer> getPartyPaymentMethodTransfersByParty(UserVisit userVisit, Party party) {
        return getPartyPaymentMethodTransfers(userVisit, getPartyPaymentMethodsByParty(party));
    }

    public PartyPaymentMethodChoicesBean getPartyPaymentMethodChoices(String defaultPartyPaymentMethodChoice, Language language, boolean allowNullChoice,
            Party party) {
        List<PartyPaymentMethod> partyPaymentMethods = getPartyPaymentMethodsByParty(party);
        int size = partyPaymentMethods.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultPartyPaymentMethodChoice == null) {
                defaultValue = "";
            }
        }

        for(PartyPaymentMethod partyPaymentMethod: partyPaymentMethods) {
            PartyPaymentMethodDetail partyPaymentMethodDetail = partyPaymentMethod.getLastDetail();

            String label = partyPaymentMethodDetail.getDescription();
            String value = partyPaymentMethodDetail.getPartyPaymentMethodName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultPartyPaymentMethodChoice == null? false: defaultPartyPaymentMethodChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && partyPaymentMethodDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new PartyPaymentMethodChoicesBean(labels, values, defaultValue);
    }

    private void updatePartyPaymentMethodFromValue(PartyPaymentMethodDetailValue partyPaymentMethodDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(partyPaymentMethodDetailValue.hasBeenModified()) {
            PartyPaymentMethod partyPaymentMethod = PartyPaymentMethodFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyPaymentMethodDetailValue.getPartyPaymentMethodPK());
            PartyPaymentMethodDetail partyPaymentMethodDetail = partyPaymentMethod.getActiveDetailForUpdate();

            partyPaymentMethodDetail.setThruTime(session.START_TIME_LONG);
            partyPaymentMethodDetail.store();

            PartyPaymentMethodPK partyPaymentMethodPK = partyPaymentMethodDetail.getPartyPaymentMethodPK(); // Not updated
            String partyPaymentMethodName = partyPaymentMethodDetailValue.getPartyPaymentMethodName();
            Party party = partyPaymentMethodDetail.getParty(); // Not updated
            String description = partyPaymentMethodDetailValue.getDescription();
            PaymentMethodPK paymentMethodPK = partyPaymentMethodDetail.getPaymentMethodPK(); // Not updated
            Boolean deleteWhenUnused = partyPaymentMethodDetailValue.getDeleteWhenUnused();
            Boolean isDefault = partyPaymentMethodDetailValue.getIsDefault();
            Integer sortOrder = partyPaymentMethodDetailValue.getSortOrder();

            if(checkDefault) {
                PartyPaymentMethod defaultPartyPaymentMethod = getDefaultPartyPaymentMethod(party);
                boolean defaultFound = defaultPartyPaymentMethod != null && !defaultPartyPaymentMethod.equals(partyPaymentMethod);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    PartyPaymentMethodDetailValue defaultPartyPaymentMethodDetailValue = getDefaultPartyPaymentMethodDetailValueForUpdate(party);

                    defaultPartyPaymentMethodDetailValue.setIsDefault(Boolean.FALSE);
                    updatePartyPaymentMethodFromValue(defaultPartyPaymentMethodDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            partyPaymentMethodDetail = PartyPaymentMethodDetailFactory.getInstance().create(partyPaymentMethodPK, partyPaymentMethodName, party.getPrimaryKey(),
                    description, paymentMethodPK, deleteWhenUnused, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            partyPaymentMethod.setActiveDetail(partyPaymentMethodDetail);
            partyPaymentMethod.setLastDetail(partyPaymentMethodDetail);

            sendEventUsingNames(partyPaymentMethodPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updatePartyPaymentMethodFromValue(PartyPaymentMethodDetailValue partyPaymentMethodDetailValue, BasePK updatedBy) {
        updatePartyPaymentMethodFromValue(partyPaymentMethodDetailValue, true, updatedBy);
    }

    public void deletePartyPaymentMethod(PartyPaymentMethod partyPaymentMethod, BasePK deletedBy) {
        var orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        
        orderControl.deleteOrderPaymentPreferencesByPartyPaymentMethod(partyPaymentMethod, deletedBy);
        
        var partyPaymentMethodDetail = partyPaymentMethod.getLastDetailForUpdate();
        partyPaymentMethodDetail.setThruTime(session.START_TIME_LONG);
        partyPaymentMethod.setActiveDetail(null);
        partyPaymentMethod.store();

        var paymentMethodTypeName = partyPaymentMethodDetail.getPaymentMethod().getLastDetail().getPaymentMethodType().getPaymentMethodTypeName();
        if(paymentMethodTypeName.equals(PaymentConstants.PaymentMethodType_CREDIT_CARD)) {
            var partyPaymentMethodCreditCardSecurityCode = getPartyPaymentMethodCreditCardSecurityCodeForUpdate(partyPaymentMethod);

            deletePartyPaymentMethodCreditCard(getPartyPaymentMethodCreditCardForUpdate(partyPaymentMethod), deletedBy);

            if(partyPaymentMethodCreditCardSecurityCode != null) {
                deletePartyPaymentMethodCreditCardSecurityCode(partyPaymentMethodCreditCardSecurityCode, deletedBy);
            }
        }

        // Check for default, and pick one if necessary
        var party = partyPaymentMethodDetail.getParty();
        var defaultPartyPaymentMethod = getDefaultPartyPaymentMethod(party);
        if(defaultPartyPaymentMethod == null) {
            var partyPaymentMethods = getPartyPaymentMethodsByPartyForUpdate(party);

            if(!partyPaymentMethods.isEmpty()) {
                var iter = partyPaymentMethods.iterator();
                if(iter.hasNext()) {
                    defaultPartyPaymentMethod = iter.next();
                }
                var partyPaymentMethodDetailValue = defaultPartyPaymentMethod.getLastDetailForUpdate().getPartyPaymentMethodDetailValue().clone();

                partyPaymentMethodDetailValue.setIsDefault(Boolean.TRUE);
                updatePartyPaymentMethodFromValue(partyPaymentMethodDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(partyPaymentMethod.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deletePartyPaymentMethods(List<PartyPaymentMethod> partyPaymentMethods, BasePK deletedBy) {
        partyPaymentMethods.stream().forEach((partyPaymentMethod) -> {
            deletePartyPaymentMethod(partyPaymentMethod, deletedBy);
        });
    }

    public void deletePartyPaymentMethodsByPaymentMethod(PaymentMethod paymentMethod, BasePK deletedBy) {
        deletePartyPaymentMethods(getPartyPaymentMethodsByPaymentMethodForUpdate(paymentMethod), deletedBy);
    }

    public void deletePartyPaymentMethodsByParty(Party party, BasePK deletedBy) {
        deletePartyPaymentMethods(getPartyPaymentMethodsByPartyForUpdate(party), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Payment Method Credit Cards
    // --------------------------------------------------------------------------------
    
    public PartyPaymentMethodCreditCard createPartyPaymentMethodCreditCard(PartyPaymentMethod partyPaymentMethod, String number,
            Integer expirationMonth, Integer expirationYear, PersonalTitle personalTitle, String firstName, String firstNameSdx,
            String middleName, String middleNameSdx, String lastName, String lastNameSdx, NameSuffix nameSuffix, String name,
            PartyContactMechanism billingPartyContactMechanism, String issuerName, PartyContactMechanism issuerPartyContactMechanism,
            BasePK createdBy) {
        PartyPaymentMethodCreditCard partyPaymentMethodCreditCard = PartyPaymentMethodCreditCardFactory.getInstance().create(session,
                partyPaymentMethod, encodePartyPaymentMethodCreditCardNumber(number), expirationMonth, expirationYear, personalTitle,
                firstName, firstNameSdx, middleName, middleNameSdx, lastName, lastNameSdx, nameSuffix, name, billingPartyContactMechanism,
                issuerName, issuerPartyContactMechanism, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(partyPaymentMethod.getPrimaryKey(), EventTypes.MODIFY.name(),
                partyPaymentMethodCreditCard.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return partyPaymentMethodCreditCard;
    }

    public long countPartyPaymentMethodCreditCardsByPersonalTitle(PersonalTitle personalTitle) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM partypaymentmethodcreditcards "
                + "WHERE parpmcc_pert_personaltitleid = ? AND parpmcc_thrutime = ?",
                personalTitle, Session.MAX_TIME);
    }

    public long countPartyPaymentMethodCreditCardsByNameSuffix(NameSuffix nameSuffix) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM partypaymentmethodcreditcards "
                + "WHERE parpmcc_nsfx_namesuffixid = ? AND parpmcc_thrutime = ?",
                nameSuffix, Session.MAX_TIME);
    }

    public long countPartyPaymentMethodCreditCardsByBillingPartyContactMechanism(PartyContactMechanism billingPartyContactMechanism) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM partypaymentmethodcreditcards "
                + "WHERE parpmcc_billingpartycontactmechanismid = ? AND parpmcc_thrutime = ?",
                billingPartyContactMechanism, Session.MAX_TIME);
    }

    public long countPartyPaymentMethodCreditCardsByIssuerPartyContactMechanism(PartyContactMechanism issuerPartyContactMechanism) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM partypaymentmethodcreditcards "
                + "WHERE parpmcc_issuerpartycontactmechanismid = ? AND parpmcc_thrutime = ?",
                issuerPartyContactMechanism, Session.MAX_TIME);
    }

    private PartyPaymentMethodCreditCard getPartyPaymentMethodCreditCard(PartyPaymentMethod partyPaymentMethod,
            EntityPermission entityPermission) {
        PartyPaymentMethodCreditCard partyPaymentMethodCreditCard = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partypaymentmethodcreditcards " +
                        "WHERE parpmcc_parpm_partypaymentmethodid = ? AND parpmcc_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partypaymentmethodcreditcards " +
                        "WHERE parpmcc_parpm_partypaymentmethodid = ? AND parpmcc_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyPaymentMethodCreditCardFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, partyPaymentMethod.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyPaymentMethodCreditCard = PartyPaymentMethodCreditCardFactory.getInstance().getEntityFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyPaymentMethodCreditCard;
    }
    
    public PartyPaymentMethodCreditCard getPartyPaymentMethodCreditCard(PartyPaymentMethod partyPaymentMethod) {
        return getPartyPaymentMethodCreditCard(partyPaymentMethod, EntityPermission.READ_ONLY);
    }
    
    public PartyPaymentMethodCreditCard getPartyPaymentMethodCreditCardForUpdate(PartyPaymentMethod partyPaymentMethod) {
        return getPartyPaymentMethodCreditCard(partyPaymentMethod, EntityPermission.READ_WRITE);
    }
    
    private List<PartyPaymentMethodCreditCard> getPartyPaymentMethodCreditCardsByBillingPartyContactMechanism(PartyContactMechanism billingPartyContactMechanism,
            EntityPermission entityPermission) {
        List<PartyPaymentMethodCreditCard> partyPaymentMethodCreditCards = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partypaymentmethodcreditcards, partypaymentmethods, partypaymentmethoddetails, parties, partydetails, partytypes " +
                        "WHERE parpmcc_billingpartycontactmechanismid = ? AND parpmcc_thrutime = ? " +
                        "AND parpmcc_parpm_partypaymentmethodid = parpm_partypaymentmethodid AND parpm_lastdetailid = parpmdt_partypaymentmethoddetailid " +
                        "AND parpmdt_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                        "AND pardt_ptyp_partytypeid = ptyp_partytypeid " +
                        "ORDER BY parpmdt_partypaymentmethodname, pardt_partyname, ptyp_sortorder, ptyp_partytypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partypaymentmethodcreditcards " +
                        "WHERE parpmcc_billingpartycontactmechanismid = ? AND parpmcc_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyPaymentMethodCreditCardFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, billingPartyContactMechanism.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyPaymentMethodCreditCards = PartyPaymentMethodCreditCardFactory.getInstance().getEntitiesFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyPaymentMethodCreditCards;
    }
    
    public List<PartyPaymentMethodCreditCard> getPartyPaymentMethodCreditCardsByBillingPartyContactMechanism(PartyContactMechanism billingPartyContactMechanism) {
        return getPartyPaymentMethodCreditCardsByBillingPartyContactMechanism(billingPartyContactMechanism, EntityPermission.READ_ONLY);
    }
    
    public List<PartyPaymentMethodCreditCard> getPartyPaymentMethodCreditCardsByBillingPartyContactMechanismForUpdate(PartyContactMechanism billingPartyContactMechanism) {
        return getPartyPaymentMethodCreditCardsByBillingPartyContactMechanism(billingPartyContactMechanism, EntityPermission.READ_WRITE);
    }
    
    private List<PartyPaymentMethodCreditCard> getPartyPaymentMethodCreditCardsByIssuerPartyContactMechanism(PartyContactMechanism issuerPartyContactMechanism,
            EntityPermission entityPermission) {
        List<PartyPaymentMethodCreditCard> partyPaymentMethodCreditCards = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partypaymentmethodcreditcards, partypaymentmethods, partypaymentmethoddetails, parties, partydetails, partytypes " +
                        "WHERE parpmcc_issuerpartycontactmechanismid = ? AND parpmcc_thrutime = ? " +
                        "AND parpmcc_parpm_partypaymentmethodid = parpm_partypaymentmethodid AND parpm_lastdetailid = parpmdt_partypaymentmethoddetailid " +
                        "AND parpmdt_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                        "AND pardt_ptyp_partytypeid = ptyp_partytypeid " +
                        "ORDER BY parpmdt_partypaymentmethodname, pardt_partyname, ptyp_sortorder, ptyp_partytypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partypaymentmethodcreditcards " +
                        "WHERE parpmcc_issuerpartycontactmechanismid = ? AND parpmcc_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyPaymentMethodCreditCardFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, issuerPartyContactMechanism.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyPaymentMethodCreditCards = PartyPaymentMethodCreditCardFactory.getInstance().getEntitiesFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyPaymentMethodCreditCards;
    }
    
    public List<PartyPaymentMethodCreditCard> getPartyPaymentMethodCreditCardsByIssuerPartyContactMechanism(PartyContactMechanism issuerPartyContactMechanism) {
        return getPartyPaymentMethodCreditCardsByIssuerPartyContactMechanism(issuerPartyContactMechanism, EntityPermission.READ_ONLY);
    }
    
    public List<PartyPaymentMethodCreditCard> getPartyPaymentMethodCreditCardsByIssuerPartyContactMechanismForUpdate(PartyContactMechanism issuerPartyContactMechanism) {
        return getPartyPaymentMethodCreditCardsByIssuerPartyContactMechanism(issuerPartyContactMechanism, EntityPermission.READ_WRITE);
    }
    
    public PartyPaymentMethodCreditCardValue getPartyPaymentMethodCreditCardValueForUpdate(PartyPaymentMethod partyPaymentMethod) {
        PartyPaymentMethodCreditCard partyPaymentMethodCreditCard = getPartyPaymentMethodCreditCardForUpdate(partyPaymentMethod);
        
        return partyPaymentMethodCreditCard == null? null: partyPaymentMethodCreditCard.getPartyPaymentMethodCreditCardValue().clone();
    }
    
    public String getPartyPaymentMethodCreditCardNumber(PartyPaymentMethodCreditCard partyPaymentMethodCreditCard) {
        return EncryptionUtils.getInstance().decrypt(PartyPaymentMethodCreditCardFactory.getInstance().getEntityTypeName(),
                PartyPaymentMethodCreditCardFactory.PARPMCC_NUMBER, partyPaymentMethodCreditCard.getNumber());
    }
    
    public String encodePartyPaymentMethodCreditCardNumber(String number) {
        return EncryptionUtils.getInstance().encrypt(PartyPaymentMethodCreditCardFactory.getInstance().getEntityTypeName(),
                PartyPaymentMethodCreditCardFactory.PARPMCC_NUMBER, number);
    }
    
    public String decodePartyPaymentMethodCreditCardNumber(PartyPaymentMethodCreditCard partyPaymentMethodCreditCard) {
        return EncryptionUtils.getInstance().decrypt(PartyPaymentMethodCreditCardFactory.getInstance().getEntityTypeName(),
                PartyPaymentMethodCreditCardFactory.PARPMCC_NUMBER, partyPaymentMethodCreditCard.getNumber());
    }
    
    public void setPartyPaymentMethodCreditCardNumber(PartyPaymentMethodCreditCard person, String number) {
        person.setNumber(encodePartyPaymentMethodCreditCardNumber(number));
    }
    
    public void updatePartyPaymentMethodCreditCardFromValue(PartyPaymentMethodCreditCardValue partyPaymentMethodCreditCardValue,
            BasePK updatedBy) {
        if(partyPaymentMethodCreditCardValue.hasBeenModified()) {
            PartyPaymentMethodCreditCard partyPaymentMethodCreditCard = PartyPaymentMethodCreditCardFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyPaymentMethodCreditCardValue.getPrimaryKey());
            
            partyPaymentMethodCreditCard.setThruTime(session.START_TIME_LONG);
            partyPaymentMethodCreditCard.store();
            
            PartyPaymentMethodPK partyPaymentMethodPK = partyPaymentMethodCreditCard.getPartyPaymentMethodPK(); // Not updated
            String number = partyPaymentMethodCreditCardValue.getNumber();
            Integer expirationMonth = partyPaymentMethodCreditCardValue.getExpirationMonth();
            Integer expirationYear = partyPaymentMethodCreditCardValue.getExpirationYear();
            PersonalTitlePK personalTitlePK = partyPaymentMethodCreditCardValue.getPersonalTitlePK();
            String firstName = partyPaymentMethodCreditCardValue.getFirstName();
            String firstNameSdx = partyPaymentMethodCreditCardValue.getFirstNameSdx();
            String middleName = partyPaymentMethodCreditCardValue.getMiddleName();
            String middleNameSdx = partyPaymentMethodCreditCardValue.getMiddleNameSdx();
            String lastName = partyPaymentMethodCreditCardValue.getLastName();
            String lastNameSdx = partyPaymentMethodCreditCardValue.getLastNameSdx();
            NameSuffixPK nameSuffixPK = partyPaymentMethodCreditCardValue.getNameSuffixPK();
            String name = partyPaymentMethodCreditCardValue.getName();
            PartyContactMechanismPK billingPartyContactMechanismPK = partyPaymentMethodCreditCardValue.getBillingPartyContactMechanismPK();
            String issuerName = partyPaymentMethodCreditCardValue.getIssuerName();
            PartyContactMechanismPK issuerPartyContactMechanismPK = partyPaymentMethodCreditCardValue.getIssuerPartyContactMechanismPK();
            
            partyPaymentMethodCreditCard = PartyPaymentMethodCreditCardFactory.getInstance().create(partyPaymentMethodPK, number, expirationMonth,
                    expirationYear, personalTitlePK, firstName, firstNameSdx, middleName, middleNameSdx, lastName, lastNameSdx, nameSuffixPK, name,
                    billingPartyContactMechanismPK, issuerName, issuerPartyContactMechanismPK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(partyPaymentMethodPK, EventTypes.MODIFY.name(), partyPaymentMethodCreditCard.getPrimaryKey(),
                    EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deletePartyPaymentMethodCreditCard(PartyPaymentMethodCreditCard partyPaymentMethodCreditCard, BasePK deletedBy) {
        partyPaymentMethodCreditCard.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(partyPaymentMethodCreditCard.getPartyPaymentMethodPK(), EventTypes.MODIFY.name(), partyPaymentMethodCreditCard.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deletePartyPaymentMethodCreditCards(List<PartyPaymentMethodCreditCard> partyPaymentMethodCreditCards, BasePK deletedBy) {
        partyPaymentMethodCreditCards.stream().forEach((partyPaymentMethodCreditCard) -> {
            deletePartyPaymentMethodCreditCard(partyPaymentMethodCreditCard, deletedBy);
        });
    }
    
    public void deletePartyPaymentMethodCreditCardsByBillingPartyContactMechanism(PartyContactMechanism billingPartyContactMechanism, BasePK deletedBy) {
        deletePartyPaymentMethodCreditCards(getPartyPaymentMethodCreditCardsByBillingPartyContactMechanismForUpdate(billingPartyContactMechanism), deletedBy);
    }
    
    public void deletePartyPaymentMethodCreditCardsByIssuerPartyContactMechanism(PartyContactMechanism issuerPartyContactMechanism, BasePK deletedBy) {
        deletePartyPaymentMethodCreditCards(getPartyPaymentMethodCreditCardsByIssuerPartyContactMechanismForUpdate(issuerPartyContactMechanism), deletedBy);
    }
    
    public void deletePartyPaymentMethodCreditCardsByPartyContactMechanism(PartyContactMechanism partyContactMechanism, BasePK deletedBy) {
        deletePartyPaymentMethodCreditCardsByBillingPartyContactMechanism(partyContactMechanism, deletedBy);
        deletePartyPaymentMethodCreditCardsByIssuerPartyContactMechanism(partyContactMechanism, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Payment Method Credit Card Security Codes
    // --------------------------------------------------------------------------------
    
    public PartyPaymentMethodCreditCardSecurityCode createPartyPaymentMethodCreditCardSecurityCode(PartyPaymentMethod partyPaymentMethod,
            String securityCode, BasePK createdBy) {
        PartyPaymentMethodCreditCardSecurityCode partyPaymentMethodCreditCardSecurityCode = PartyPaymentMethodCreditCardSecurityCodeFactory.getInstance().create(session,
                partyPaymentMethod, encodePartyPaymentMethodCreditCardSecurityCodeSecurityCode(securityCode), session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(partyPaymentMethod.getPrimaryKey(), EventTypes.MODIFY.name(),
                partyPaymentMethodCreditCardSecurityCode.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return partyPaymentMethodCreditCardSecurityCode;
    }
    
    private PartyPaymentMethodCreditCardSecurityCode getPartyPaymentMethodCreditCardSecurityCode(PartyPaymentMethod partyPaymentMethod,
            EntityPermission entityPermission) {
        PartyPaymentMethodCreditCardSecurityCode partyPaymentMethodCreditCardSecurityCode = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partypaymentmethodcreditcardsecuritycodes " +
                        "WHERE parpmccsc_parpm_partypaymentmethodid = ? AND parpmccsc_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partypaymentmethodcreditcardsecuritycodes " +
                        "WHERE parpmccsc_parpm_partypaymentmethodid = ? AND parpmccsc_thrutime= ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyPaymentMethodCreditCardSecurityCodeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, partyPaymentMethod.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyPaymentMethodCreditCardSecurityCode = PartyPaymentMethodCreditCardSecurityCodeFactory.getInstance().getEntityFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyPaymentMethodCreditCardSecurityCode;
    }
    
    public PartyPaymentMethodCreditCardSecurityCode getPartyPaymentMethodCreditCardSecurityCode(PartyPaymentMethod partyPaymentMethod) {
        return getPartyPaymentMethodCreditCardSecurityCode(partyPaymentMethod, EntityPermission.READ_ONLY);
    }
    
    public PartyPaymentMethodCreditCardSecurityCode getPartyPaymentMethodCreditCardSecurityCodeForUpdate(PartyPaymentMethod partyPaymentMethod) {
        return getPartyPaymentMethodCreditCardSecurityCode(partyPaymentMethod, EntityPermission.READ_WRITE);
    }
    
    public PartyPaymentMethodCreditCardSecurityCodeValue getPartyPaymentMethodCreditCardSecurityCodeValueForUpdate(PartyPaymentMethod partyPaymentMethod) {
        PartyPaymentMethodCreditCardSecurityCode partyPaymentMethodCreditCardSecurityCode = getPartyPaymentMethodCreditCardSecurityCodeForUpdate(partyPaymentMethod);
        
        return partyPaymentMethodCreditCardSecurityCode == null? null: partyPaymentMethodCreditCardSecurityCode.getPartyPaymentMethodCreditCardSecurityCodeValue().clone();
    }
    
    public String getPartyPaymentMethodCreditCardSecurityCodeSecurityCode(PartyPaymentMethodCreditCardSecurityCode partyPaymentMethodCreditCardSecurityCode) {
        return EncryptionUtils.getInstance().decrypt(PartyPaymentMethodCreditCardSecurityCodeFactory.getInstance().getEntityTypeName(),
                PartyPaymentMethodCreditCardSecurityCodeFactory.PARPMCCSC_SECURITYCODE,
                partyPaymentMethodCreditCardSecurityCode.getSecurityCode());
    }
    
    public String encodePartyPaymentMethodCreditCardSecurityCodeSecurityCode(String securityCode) {
        return EncryptionUtils.getInstance().encrypt(PartyPaymentMethodCreditCardSecurityCodeFactory.getInstance().getEntityTypeName(),
                PartyPaymentMethodCreditCardSecurityCodeFactory.PARPMCCSC_SECURITYCODE, securityCode);
    }
    
    public String decodePartyPaymentMethodCreditCardSecurityCodeSecurityCode(PartyPaymentMethodCreditCardSecurityCode partyPaymentMethodCreditCardSecurityCode) {
        return EncryptionUtils.getInstance().decrypt(PartyPaymentMethodCreditCardSecurityCodeFactory.getInstance().getEntityTypeName(),
                PartyPaymentMethodCreditCardSecurityCodeFactory.PARPMCCSC_SECURITYCODE,
                partyPaymentMethodCreditCardSecurityCode.getSecurityCode());
    }
    
    public void setPartyPaymentMethodCreditCardSecurityCodeSecurityCode(PartyPaymentMethodCreditCardSecurityCode person,
            String securityCode) {
        person.setSecurityCode(encodePartyPaymentMethodCreditCardSecurityCodeSecurityCode(securityCode));
    }
    
    public void updatePartyPaymentMethodCreditCardSecurityCodeFromValue(PartyPaymentMethodCreditCardSecurityCodeValue partyPaymentMethodCreditCardSecurityCodeValue,
            BasePK updatedBy) {
        if(partyPaymentMethodCreditCardSecurityCodeValue.hasBeenModified()) {
            PartyPaymentMethodCreditCardSecurityCode partyPaymentMethodCreditCardSecurityCode = PartyPaymentMethodCreditCardSecurityCodeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyPaymentMethodCreditCardSecurityCodeValue.getPrimaryKey());
            
            partyPaymentMethodCreditCardSecurityCode.setThruTime(session.START_TIME_LONG);
            partyPaymentMethodCreditCardSecurityCode.store();
            
            PartyPaymentMethodPK partyPaymentMethodPK = partyPaymentMethodCreditCardSecurityCode.getPartyPaymentMethodPK(); // Not updated
            String securityCode = partyPaymentMethodCreditCardSecurityCodeValue.getSecurityCode();
            
            partyPaymentMethodCreditCardSecurityCode = PartyPaymentMethodCreditCardSecurityCodeFactory.getInstance().create(session,
                    partyPaymentMethodPK, securityCode, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(partyPaymentMethodPK, EventTypes.MODIFY.name(), partyPaymentMethodCreditCardSecurityCode.getPrimaryKey(),
                    EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deletePartyPaymentMethodCreditCardSecurityCode(PartyPaymentMethodCreditCardSecurityCode partyPaymentMethodCreditCardSecurityCode,
            BasePK deletedBy) {
        partyPaymentMethodCreditCardSecurityCode.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(partyPaymentMethodCreditCardSecurityCode.getPartyPaymentMethodPK(), EventTypes.MODIFY.name(),
                partyPaymentMethodCreditCardSecurityCode.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Payment Method Contact Mechanisms
    // --------------------------------------------------------------------------------
    
    public PartyPaymentMethodContactMechanism createPartyPaymentMethodContactMechanism(PartyPaymentMethod partyPaymentMethod,
            PartyContactMechanismPurpose partyContactMechanismPurpose, BasePK createdBy) {
        PartyPaymentMethodContactMechanism partyPaymentMethodContactMechanism = PartyPaymentMethodContactMechanismFactory.getInstance().create(session,
                partyPaymentMethod, partyContactMechanismPurpose, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(partyPaymentMethod.getPrimaryKey(), EventTypes.MODIFY.name(), partyPaymentMethodContactMechanism.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return partyPaymentMethodContactMechanism;
    }
    
    private PartyPaymentMethodContactMechanism getPartyPaymentMethodContactMechanism(PartyPaymentMethod partyPaymentMethod,
            PartyContactMechanismPurpose partyContactMechanismPurpose, EntityPermission entityPermission) {
        PartyPaymentMethodContactMechanism partyPaymentMethodContactMechanism = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partypaymentmethodcontactmechanisms " +
                        "WHERE parpmcmch_parpm_partypaymentmethodid = ? AND parpmcmch_pcmp_partycontactmechanismpurposeid = ? " +
                        "AND parpmcmch_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partypaymentmethodcontactmechanisms " +
                        "WHERE parpmcmch_parpm_partypaymentmethodid = ? AND parpmcmch_pcmp_partycontactmechanismpurposeid = ? " +
                        "AND parpmcmch_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyPaymentMethodContactMechanismFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, partyPaymentMethod.getPrimaryKey().getEntityId());
            ps.setLong(2, partyContactMechanismPurpose.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            partyPaymentMethodContactMechanism = PartyPaymentMethodContactMechanismFactory.getInstance().getEntityFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyPaymentMethodContactMechanism;
    }
    
    public PartyPaymentMethodContactMechanism getPartyPaymentMethodContactMechanism(PartyPaymentMethod partyPaymentMethod,
            PartyContactMechanismPurpose partyContactMechanismPurpose) {
        return getPartyPaymentMethodContactMechanism(partyPaymentMethod, partyContactMechanismPurpose, EntityPermission.READ_ONLY);
    }
    
    public PartyPaymentMethodContactMechanism getPartyPaymentMethodContactMechanismForUpdate(PartyPaymentMethod partyPaymentMethod,
            PartyContactMechanismPurpose partyContactMechanismPurpose) {
        return getPartyPaymentMethodContactMechanism(partyPaymentMethod, partyContactMechanismPurpose, EntityPermission.READ_WRITE);
    }
    
    private List<PartyPaymentMethodContactMechanism> getPartyPaymentMethodContactMechanismsByPartyPaymentMethod(PartyPaymentMethod partyPaymentMethod,
            EntityPermission entityPermission) {
        List<PartyPaymentMethodContactMechanism> partyPaymentMethodContactMechanisms = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partypaymentmethodcontactmechanisms " +
                        "WHERE parpmcmch_parpm_partypaymentmethodid = ? AND parpmcmch_thrutime = ?"; // TODO: ORDER BY
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partypaymentmethodcontactmechanisms " +
                        "WHERE parpmcmch_parpm_partypaymentmethodid = ? AND parpmcmch_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyPaymentMethodContactMechanismFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, partyPaymentMethod.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyPaymentMethodContactMechanisms = PartyPaymentMethodContactMechanismFactory.getInstance().getEntitiesFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyPaymentMethodContactMechanisms;
    }
    
    public List<PartyPaymentMethodContactMechanism> getPartyPaymentMethodContactMechanismsByPartyPaymentMethod(PartyPaymentMethod partyPaymentMethod) {
        return getPartyPaymentMethodContactMechanismsByPartyPaymentMethod(partyPaymentMethod, EntityPermission.READ_ONLY);
    }
    
    public List<PartyPaymentMethodContactMechanism> getPartyPaymentMethodContactMechanismsByPartyPaymentMethodForUpdate(PartyPaymentMethod partyPaymentMethod) {
        return getPartyPaymentMethodContactMechanismsByPartyPaymentMethod(partyPaymentMethod, EntityPermission.READ_WRITE);
    }
    
    private List<PartyPaymentMethodContactMechanism> getPartyPaymentMethodContactMechanismsByPartyContactMechanismPurpose(PartyContactMechanismPurpose partyContactMechanismPurpose,
            EntityPermission entityPermission) {
        List<PartyPaymentMethodContactMechanism> partyPaymentMethodContactMechanisms = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partypaymentmethodcontactmechanisms " +
                        "WHERE parpmcmch_pcmp_partycontactmechanismpurposeid = ? AND parpmcmch_thrutime = ?"; // TODO: ORDER BY
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partypaymentmethodcontactmechanisms " +
                        "WHERE parpmcmch_pcmp_partycontactmechanismpurposeid = ? AND parpmcmch_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyPaymentMethodContactMechanismFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, partyContactMechanismPurpose.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyPaymentMethodContactMechanisms = PartyPaymentMethodContactMechanismFactory.getInstance().getEntitiesFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyPaymentMethodContactMechanisms;
    }
    
    public List<PartyPaymentMethodContactMechanism> getPartyPaymentMethodContactMechanismsByPartyContactMechanismPurpose(PartyContactMechanismPurpose partyContactMechanismPurpose) {
        return getPartyPaymentMethodContactMechanismsByPartyContactMechanismPurpose(partyContactMechanismPurpose, EntityPermission.READ_ONLY);
    }
    
    public List<PartyPaymentMethodContactMechanism> getPartyPaymentMethodContactMechanismsByPartyContactMechanismPurposeForUpdate(PartyContactMechanismPurpose partyContactMechanismPurpose) {
        return getPartyPaymentMethodContactMechanismsByPartyContactMechanismPurpose(partyContactMechanismPurpose, EntityPermission.READ_WRITE);
    }
    
    public PartyPaymentMethodContactMechanismTransfer getPartyPaymentMethodContactMechanismTransfer(UserVisit userVisit,
            PartyPaymentMethodContactMechanism partyPaymentMethodContactMechanism) {
        return getPaymentTransferCaches(userVisit).getPartyPaymentMethodContactMechanismTransferCache().getTransfer(partyPaymentMethodContactMechanism);
    }
    
    public List<PartyPaymentMethodContactMechanismTransfer> getPartyPaymentMethodContactMechanismTransfers(UserVisit userVisit,
            List<PartyPaymentMethodContactMechanism> partyPaymentMethodContactMechanisms) {
        List<PartyPaymentMethodContactMechanismTransfer> partyPaymentMethodContactMechanismTransfers = new ArrayList<>(partyPaymentMethodContactMechanisms.size());
        PartyPaymentMethodContactMechanismTransferCache partyPaymentMethodContactMechanismTransferCache = getPaymentTransferCaches(userVisit).getPartyPaymentMethodContactMechanismTransferCache();
        
        partyPaymentMethodContactMechanisms.stream().forEach((partyPaymentMethodContactMechanism) -> {
            partyPaymentMethodContactMechanismTransfers.add(partyPaymentMethodContactMechanismTransferCache.getTransfer(partyPaymentMethodContactMechanism));
        });
        
        return partyPaymentMethodContactMechanismTransfers;
    }
    
    public List<PartyPaymentMethodContactMechanismTransfer> getPartyPaymentMethodContactMechanismTransfersByPartyPaymentMethod(UserVisit userVisit,
            PartyPaymentMethod partyPaymentMethod) {
        return getPartyPaymentMethodContactMechanismTransfers(userVisit, getPartyPaymentMethodContactMechanismsByPartyPaymentMethod(partyPaymentMethod));
    }
    
    public List<PartyPaymentMethodContactMechanismTransfer> getPartyPaymentMethodContactMechanismTransfersByPartyContactMechanismPurpose(UserVisit userVisit,
            PartyContactMechanismPurpose partyContactMechanismPurpose) {
        return getPartyPaymentMethodContactMechanismTransfers(userVisit, getPartyPaymentMethodContactMechanismsByPartyContactMechanismPurpose(partyContactMechanismPurpose));
    }
    
    // --------------------------------------------------------------------------------
    //   Billing Accounts
    // --------------------------------------------------------------------------------
    
    public BillingAccount createBillingAccount(final Party billFrom, final Currency currency, final String reference, final String description, final BasePK createdBy) {
        var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
        Sequence sequence = sequenceControl.getDefaultSequence(billFrom.getLastDetail().getPartyType().getBillingAccountSequenceType());
        String billingAccountName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);
        
         return createBillingAccount(billingAccountName, currency, reference, description, createdBy);
    }
    
    public BillingAccount createBillingAccount(String billingAccountName, Currency currency, String reference, String description, BasePK createdBy) {
        BillingAccount billingAccount = BillingAccountFactory.getInstance().create();
        BillingAccountDetail billingAccountDetail = BillingAccountDetailFactory.getInstance().create(billingAccount,
                billingAccountName, currency, reference, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        billingAccount = BillingAccountFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                billingAccount.getPrimaryKey());
        billingAccount.setActiveDetail(billingAccountDetail);
        billingAccount.setLastDetail(billingAccountDetail);
        billingAccount.store();
        
        sendEventUsingNames(billingAccount.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        createBillingAccountStatus(billingAccount, Long.valueOf(0), null);
        
        return billingAccount;
    }
    
    private BillingAccount getBillingAccount(Party billFrom, Party billTo, Currency currency, EntityPermission entityPermission) {
        BillingAccount billingAccount = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccounts, billingaccountdetails, billingaccountroletypes barta, billingaccountroles bara, billingaccountroletypes bartb, billingaccountroles barb " +
                        "WHERE bllact_activedetailid = bllactdt_billingaccountdetailid " +
                        "AND bllactdt_cur_currencyid = ? " +
                        "AND bllactdt_bllact_billingaccountid = bara.bllactr_bllact_billingaccountid AND bara.bllactr_par_partyid = ? AND barta.bllactrtyp_billingaccountroletypename = ? " +
                        "AND barta.bllactrtyp_billingaccountroletypeid = bara.bllactr_bllactrtyp_billingaccountroletypeid AND bara.bllactr_thrutime = ? " +
                        "AND bllactdt_bllact_billingaccountid = barb.bllactr_bllact_billingaccountid AND barb.bllactr_par_partyid = ? AND bartb.bllactrtyp_billingaccountroletypename = ? " +
                        "AND bartb.bllactrtyp_billingaccountroletypeid = barb.bllactr_bllactrtyp_billingaccountroletypeid AND barb.bllactr_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccounts, billingaccountdetails, billingaccountroletypes barta, billingaccountroles bara, billingaccountroletypes bartb, billingaccountroles barb " +
                        "WHERE bllact_activedetailid = bllactdt_billingaccountdetailid " +
                        "AND bllactdt_cur_currencyid = ? " +
                        "AND bllactdt_bllact_billingaccountid = bara.bllactr_bllact_billingaccountid AND bara.bllactr_par_partyid = ? AND barta.bllactrtyp_billingaccountroletypename = ? " +
                        "AND barta.bllactrtyp_billingaccountroletypeid = bara.bllactr_bllactrtyp_billingaccountroletypeid AND bara.bllactr_thrutime = ? " +
                        "AND bllactdt_bllact_billingaccountid = barb.bllactr_bllact_billingaccountid AND barb.bllactr_par_partyid = ? AND bartb.bllactrtyp_billingaccountroletypename = ? " +
                        "AND bartb.bllactrtyp_billingaccountroletypeid = barb.bllactr_bllactrtyp_billingaccountroletypeid AND barb.bllactr_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = BillingAccountFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, currency.getPrimaryKey().getEntityId());
            ps.setLong(2, billFrom.getPrimaryKey().getEntityId());
            ps.setString(3, PaymentConstants.BillingAccountRoleType_BILL_FROM);
            ps.setLong(4, Session.MAX_TIME);
            ps.setLong(5, billTo.getPrimaryKey().getEntityId());
            ps.setString(6, PaymentConstants.BillingAccountRoleType_BILL_TO);
            ps.setLong(7, Session.MAX_TIME);
            
            billingAccount = BillingAccountFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return billingAccount;
    }
    
    public BillingAccount getBillingAccount(Party billFrom, Party billTo, Currency currency) {
        return getBillingAccount(billFrom, billTo, currency, EntityPermission.READ_ONLY);
    }
    
    public BillingAccount getBillingAccountForUpdate(Party billFrom, Party billTo, Currency currency) {
        return getBillingAccount(billFrom, billTo, currency, EntityPermission.READ_WRITE);
    }
    
    private List<BillingAccount> getBillingAccountsByBillFrom(Party billFrom, EntityPermission entityPermission) {
        List<BillingAccount> billingAccounts = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccounts, billingaccountdetails, billingaccountroletypes barta, billingaccountroles bara, billingaccountroletypes bartb, billingaccountroles barb, currencies, parties, partydetails " +
                        "WHERE bllact_activedetailid = bllactdt_billingaccountdetailid " +
                        "AND bllactdt_bllact_billingaccountid = bara.bllactr_bllact_billingaccountid AND bara.bllactr_par_partyid = ? AND barta.bllactrtyp_billingaccountroletypename = ? " +
                        "AND barta.bllactrtyp_billingaccountroletypeid = bara.bllactr_bllactrtyp_billingaccountroletypeid AND bara.bllactr_thrutime = ? " +
                        "AND bllactdt_bllact_billingaccountid = barb.bllactr_bllact_billingaccountid AND bartb.bllactrtyp_billingaccountroletypename = ? " +
                        "AND bartb.bllactrtyp_billingaccountroletypeid = barb.bllactr_bllactrtyp_billingaccountroletypeid AND barb.bllactr_thrutime = ? " +
                        "AND bllactdt_cur_currencyid = cur_currencyid AND barb.bllactr_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                        "ORDER BY pardt_partyname, cur_sortorder, cur_currencyisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccounts, billingaccountdetails, billingaccountroletypes barta, billingaccountroles bara, billingaccountroletypes bartb, billingaccountroles barb " +
                        "WHERE bllact_activedetailid = bllactdt_billingaccountdetailid " +
                        "AND bllactdt_bllact_billingaccountid = bara.bllactr_bllact_billingaccountid AND bara.bllactr_par_partyid = ? AND barta.bllactrtyp_billingaccountroletypename = ? " +
                        "AND barta.bllactrtyp_billingaccountroletypeid = bara.bllactr_bllactrtyp_billingaccountroletypeid AND bara.bllactr_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = BillingAccountFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, billFrom.getPrimaryKey().getEntityId());
            ps.setString(2, PaymentConstants.BillingAccountRoleType_BILL_FROM);
            ps.setLong(3, Session.MAX_TIME);
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                ps.setString(4, PaymentConstants.BillingAccountRoleType_BILL_TO);
                ps.setLong(5, Session.MAX_TIME);
            }
            
            billingAccounts = BillingAccountFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return billingAccounts;
    }
    
    public List<BillingAccount> getBillingAccountsByBillFrom(Party billFrom) {
        return getBillingAccountsByBillFrom(billFrom, EntityPermission.READ_ONLY);
    }
    
    public List<BillingAccount> getBillingAccountsByBillFromForUpdate(Party billFrom) {
        return getBillingAccountsByBillFrom(billFrom, EntityPermission.READ_WRITE);
    }
    
    private List<BillingAccount> getBillingAccountsByBillTo(Party billTo, EntityPermission entityPermission) {
        List<BillingAccount> billingAccounts = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccounts, billingaccountdetails, billingaccountroletypes barta, billingaccountroles bara, billingaccountroletypes bartb, billingaccountroles barb, currencies, parties, partydetails " +
                        "WHERE bllact_activedetailid = bllactdt_billingaccountdetailid " +
                        "AND bllactdt_bllact_billingaccountid = bara.bllactr_bllact_billingaccountid AND bara.bllactr_par_partyid = ? AND barta.bllactrtyp_billingaccountroletypename = ? " +
                        "AND barta.bllactrtyp_billingaccountroletypeid = bara.bllactr_bllactrtyp_billingaccountroletypeid AND bara.bllactr_thrutime = ? " +
                        "AND bllactdt_bllact_billingaccountid = barb.bllactr_bllact_billingaccountid AND bartb.bllactrtyp_billingaccountroletypename = ? " +
                        "AND bartb.bllactrtyp_billingaccountroletypeid = barb.bllactr_bllactrtyp_billingaccountroletypeid AND barb.bllactr_thrutime = ? " +
                        "AND bllactdt_cur_currencyid = cur_currencyid AND barb.bllactr_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                        "ORDER BY pardt_partyname, cur_sortorder, cur_currencyisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccounts, billingaccountdetails, billingaccountroletypes barta, billingaccountroles bara, billingaccountroletypes bartb, billingaccountroles barb " +
                        "WHERE bllact_activedetailid = bllactdt_billingaccountdetailid " +
                        "AND bllactdt_bllact_billingaccountid = bara.bllactr_bllact_billingaccountid AND bara.bllactr_par_partyid = ? AND barta.bllactrtyp_billingaccountroletypename = ? " +
                        "AND barta.bllactrtyp_billingaccountroletypeid = bara.bllactr_bllactrtyp_billingaccountroletypeid AND bara.bllactr_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = BillingAccountFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, billTo.getPrimaryKey().getEntityId());
            ps.setString(2, PaymentConstants.BillingAccountRoleType_BILL_TO);
            ps.setLong(3, Session.MAX_TIME);
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                ps.setString(4, PaymentConstants.BillingAccountRoleType_BILL_FROM);
                ps.setLong(5, Session.MAX_TIME);
            }
            
            billingAccounts = BillingAccountFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return billingAccounts;
    }
    
    public List<BillingAccount> getBillingAccountsByBillTo(Party billTo) {
        return getBillingAccountsByBillTo(billTo, EntityPermission.READ_ONLY);
    }
    
    public List<BillingAccount> getBillingAccountsByBillToForUpdate(Party billTo) {
        return getBillingAccountsByBillTo(billTo, EntityPermission.READ_WRITE);
    }
    
    private BillingAccount getBillingAccountByName(String billingAccountName, EntityPermission entityPermission) {
        BillingAccount billingAccount = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccounts, billingaccountdetails " +
                        "WHERE bllact_activedetailid = bllactdt_billingaccountdetailid AND bllactdt_billingaccountname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccounts, billingaccountdetails " +
                        "WHERE bllact_activedetailid = bllactdt_billingaccountdetailid AND bllactdt_billingaccountname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = BillingAccountFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, billingAccountName);
            
            billingAccount = BillingAccountFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return billingAccount;
    }
    
    public BillingAccount getBillingAccountByName(String billingAccountName) {
        return getBillingAccountByName(billingAccountName, EntityPermission.READ_ONLY);
    }
    
    public BillingAccount getBillingAccountByNameForUpdate(String billingAccountName) {
        return getBillingAccountByName(billingAccountName, EntityPermission.READ_WRITE);
    }
    
    public BillingAccountTransfer getBillingAccountTransfer(UserVisit userVisit, BillingAccount billingAccount) {
        return getPaymentTransferCaches(userVisit).getBillingAccountTransferCache().getTransfer(billingAccount);
    }
    
    public List<BillingAccountTransfer> getBillingAccountTransfers(UserVisit userVisit, List<BillingAccount> billingAccounts) {
        List<BillingAccountTransfer> billingAccountTransfers = new ArrayList<>(billingAccounts.size());
        BillingAccountTransferCache billingAccountTransferCache = getPaymentTransferCaches(userVisit).getBillingAccountTransferCache();
        
        billingAccounts.stream().forEach((billingAccount) -> {
            billingAccountTransfers.add(billingAccountTransferCache.getTransfer(billingAccount));
        });
        
        return billingAccountTransfers;
    }
    
    public List<BillingAccountTransfer> getBillingAccountTransfersByBillFrom(UserVisit userVisit, Party billFrom) {
        return getBillingAccountTransfers(userVisit, getBillingAccountsByBillFrom(billFrom));
    }
    
    public List<BillingAccountTransfer> getBillingAccountTransfersByBillTo(UserVisit userVisit, Party billTo) {
        return getBillingAccountTransfers(userVisit, getBillingAccountsByBillTo(billTo));
    }
    
    // --------------------------------------------------------------------------------
    //   Billing Account Statuses
    // --------------------------------------------------------------------------------
    
    public BillingAccountStatus createBillingAccountStatus(BillingAccount billingAccount, Long creditLimit,
            Long potentialCreditLimit) {
        return BillingAccountStatusFactory.getInstance().create(billingAccount, creditLimit, potentialCreditLimit);
    }
    
    private BillingAccountStatus getBillingAccountStatus(BillingAccount billingAccount, EntityPermission entityPermission) {
        BillingAccountStatus billingAccountStatus = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccountstatuses " +
                        "WHERE bllactst_bllact_billingaccountid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccountstatuses " +
                        "WHERE bllactst_bllact_billingaccountid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = BillingAccountStatusFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, billingAccount.getPrimaryKey().getEntityId());
            
            billingAccountStatus = BillingAccountStatusFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return billingAccountStatus;
    }
    
    public BillingAccountStatus getBillingAccountStatus(BillingAccount billingAccount) {
        return getBillingAccountStatus(billingAccount, EntityPermission.READ_ONLY);
    }
    
    public BillingAccountStatus getBillingAccountStatusForUpdate(BillingAccount billingAccount) {
        return getBillingAccountStatus(billingAccount, EntityPermission.READ_WRITE);
    }
    
    // --------------------------------------------------------------------------------
    //   Billing Account Roles
    // --------------------------------------------------------------------------------
    
    public BillingAccountRole createBillingAccountRoleUsingNames(BillingAccount billingAccount, Party party, PartyContactMechanism partyContactMechanism,
            String billingAccountRoleTypeName, BasePK createdBy) {
        BillingAccountRoleType billingAccountRoleType = getBillingAccountRoleTypeByName(billingAccountRoleTypeName);
        
        return createBillingAccountRole(billingAccount, party, partyContactMechanism, billingAccountRoleType, createdBy);
    }
    
    public BillingAccountRole createBillingAccountRole(BillingAccount billingAccount, Party party, PartyContactMechanism partyContactMechanism,
            BillingAccountRoleType billingAccountRoleType, BasePK createdBy) {
        BillingAccountRole billingAccountRole = BillingAccountRoleFactory.getInstance().create(billingAccount, party, partyContactMechanism,
                billingAccountRoleType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(billingAccount.getPrimaryKey(), EventTypes.MODIFY.name(), billingAccountRole.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return billingAccountRole;
    }
    
    private BillingAccountRole getBillingAccountRole(BillingAccount billingAccount, BillingAccountRoleType billingAccountRoleType, EntityPermission entityPermission) {
        BillingAccountRole billingAccountRole = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccountroles " +
                        "WHERE bllactr_bllact_billingaccountid = ? AND bllactr_bllactrtyp_billingaccountroletypeid = ? AND bllactr_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccountroles " +
                        "WHERE bllactr_bllact_billingaccountid = ? AND bllactr_bllactrtyp_billingaccountroletypeid = ? AND bllactr_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = BillingAccountRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, billingAccount.getPrimaryKey().getEntityId());
            ps.setLong(2, billingAccountRoleType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            billingAccountRole = BillingAccountRoleFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return billingAccountRole;
    }
    
    public BillingAccountRole getBillingAccountRole(BillingAccount billingAccount, BillingAccountRoleType billingAccountRoleType) {
        return getBillingAccountRole(billingAccount, billingAccountRoleType, EntityPermission.READ_ONLY);
    }
    
    public BillingAccountRole getBillingAccountRoleUsingNames(BillingAccount billingAccount, String billingAccountRoleTypeName) {
        BillingAccountRoleType billingAccountRoleType = getBillingAccountRoleTypeByName(billingAccountRoleTypeName);
        
        return getBillingAccountRole(billingAccount, billingAccountRoleType);
    }
    
    public BillingAccountRole getBillingAccountRoleForUpdate(BillingAccount billingAccount, BillingAccountRoleType billingAccountRoleType) {
        return getBillingAccountRole(billingAccount, billingAccountRoleType, EntityPermission.READ_WRITE);
    }
    
    public BillingAccountRoleValue getBillingAccountRoleValue(BillingAccountRole billingAccountRole) {
        return billingAccountRole == null? null: billingAccountRole.getBillingAccountRoleValue().clone();
    }
    
    public BillingAccountRoleValue getBillingAccountRoleValueForUpdate(BillingAccount billingAccount, BillingAccountRoleType billingAccountRoleType) {
        return getBillingAccountRoleValue(getBillingAccountRoleForUpdate(billingAccount, billingAccountRoleType));
    }
    
    private List<BillingAccountRole> getBillingAccountRolesByBillingAccount(BillingAccount billingAccount, EntityPermission entityPermission) {
        List<BillingAccountRole> billingAccountRoles = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccountroles, billingaccountroletypes, parties, partydetails " +
                        "WHERE bllactr_bllact_billingaccountid = ? AND bllactr_thrutime = ? " +
                        "AND bllactr_bllactrtyp_billingaccountroletypeid = bllactrtyp_billingaccountroletypeid " +
                        "AND bllactr_par_partyid = par_partyid AND par_activedetailid = pardt_partydetailid " +
                        "ORDER BY bllactrtyp_sortorder, pardt_partyname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccountroles " +
                        "WHERE bllactr_bllact_billingAccountid = ? AND bllactr_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = BillingAccountRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, billingAccount.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            billingAccountRoles = BillingAccountRoleFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return billingAccountRoles;
    }
    
    public List<BillingAccountRole> getBillingAccountRolesByBillingAccount(BillingAccount billingAccount) {
        return getBillingAccountRolesByBillingAccount(billingAccount, EntityPermission.READ_ONLY);
    }
    
    public List<BillingAccountRole> getBillingAccountRolesByBillingAccountForUpdate(BillingAccount billingAccount) {
        return getBillingAccountRolesByBillingAccount(billingAccount, EntityPermission.READ_WRITE);
    }
    
    private List<BillingAccountRole> getBillingAccountRolesByPartyContactMechanism(PartyContactMechanism partyContactMechanism, EntityPermission entityPermission) {
        List<BillingAccountRole> billingAccountRoles = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccountroles, billingaccounts, billingaccountdetails, billingaccountroletypes " +
                        "WHERE bllactr_pcm_partycontactmechanismid = ? AND bllactr_thrutime = ? " +
                        "AND bllactr_bllact_billingaccountid = bllact_billingaccountid AND bllact_lastdetailid = bllactdt_billingaccountdetailid " +
                        "AND bllactr_bllactrtyp_billingaccountroletypeid = bllactrtyp_billingaccountroletypeid " +
                        "ORDER BY bllactdt_billingaccountname, bllactrtyp_sortorder, bllactrtyp_billingaccountroletypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccountroles " +
                        "WHERE bllactr_pcm_partycontactmechanismid = ? AND bllactr_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = BillingAccountRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, partyContactMechanism.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            billingAccountRoles = BillingAccountRoleFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return billingAccountRoles;
    }
    
    public List<BillingAccountRole> getBillingAccountRolesByPartyContactMechanism(PartyContactMechanism partyContactMechanism) {
        return getBillingAccountRolesByPartyContactMechanism(partyContactMechanism, EntityPermission.READ_ONLY);
    }
    
    public List<BillingAccountRole> getBillingAccountRolesByPartyContactMechanismForUpdate(PartyContactMechanism partyContactMechanism) {
        return getBillingAccountRolesByPartyContactMechanism(partyContactMechanism, EntityPermission.READ_WRITE);
    }
    
    public BillingAccountRoleTransfer getBillingAccountRoleTransfer(UserVisit userVisit, BillingAccountRole billingAccountRole) {
        return getPaymentTransferCaches(userVisit).getBillingAccountRoleTransferCache().getTransfer(billingAccountRole);
    }
    
    public List<BillingAccountRoleTransfer> getBillingAccountRoleTransfers(UserVisit userVisit, List<BillingAccountRole> billingAccountRoles) {
        List<BillingAccountRoleTransfer> billingAccountRoleTransfers = new ArrayList<>(billingAccountRoles.size());
        BillingAccountRoleTransferCache billingAccountRoleTransferCache = getPaymentTransferCaches(userVisit).getBillingAccountRoleTransferCache();
        
        billingAccountRoles.stream().forEach((billingAccountRole) -> {
            billingAccountRoleTransfers.add(billingAccountRoleTransferCache.getTransfer(billingAccountRole));
        });
        
        return billingAccountRoleTransfers;
    }
    
    public List<BillingAccountRoleTransfer> getBillingAccountRoleTransfersByBillingAccount(UserVisit userVisit, BillingAccount billingAccount) {
        return getBillingAccountRoleTransfers(userVisit, getBillingAccountRolesByBillingAccount(billingAccount));
    }
    
    public void updateBillingAccountRoleFromValue(BillingAccountRoleValue billingAccountRoleValue, BasePK updatedBy) {
        if(billingAccountRoleValue.hasBeenModified()) {
            BillingAccountRole billingAccountRole = BillingAccountRoleFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     billingAccountRoleValue.getPrimaryKey());
            
            billingAccountRole.setThruTime(session.START_TIME_LONG);
            billingAccountRole.store();
            
            BillingAccountPK billingAccountPK = billingAccountRole.getBillingAccountPK(); // Not updated
            PartyPK partyPK = billingAccountRole.getPartyPK(); // Not updated
            PartyContactMechanismPK partyContactMechanismPK = billingAccountRoleValue.getPartyContactMechanismPK();
            BillingAccountRoleTypePK billingAccountRoleTypePK = billingAccountRole.getBillingAccountRoleTypePK(); // Not updated
            
            billingAccountRole = BillingAccountRoleFactory.getInstance().create(billingAccountPK, partyPK, partyContactMechanismPK, billingAccountRoleTypePK,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(billingAccountPK, EventTypes.MODIFY.name(), billingAccountRole.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteBillingAccountRole(BillingAccountRole billingAccountRole, BasePK deletedBy) {
        billingAccountRole.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(billingAccountRole.getBillingAccountPK(), EventTypes.MODIFY.name(), billingAccountRole.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteBillingAccountRolesByBillingAccount(BillingAccount billingAccount, BasePK deletedBy) {
        getBillingAccountRolesByBillingAccountForUpdate(billingAccount).stream().forEach((billingAccountRole) -> {
            deleteBillingAccountRole(billingAccountRole, deletedBy);
        });
    }
    
    public void deleteBillingAccountRolesByPartyContactMechanism(PartyContactMechanism partyContactMechanism, BasePK deletedBy) {
        getBillingAccountRolesByPartyContactMechanismForUpdate(partyContactMechanism).stream().forEach((billingAccountRole) -> {
            deleteBillingAccountRole(billingAccountRole, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Payments
    // --------------------------------------------------------------------------------
    
    // --------------------------------------------------------------------------------
    //   Payment Checks
    // --------------------------------------------------------------------------------
    
    // --------------------------------------------------------------------------------
    //   Payment Credit Cards
    // --------------------------------------------------------------------------------
    
    // --------------------------------------------------------------------------------
    //   Payment Deduction Types
    // --------------------------------------------------------------------------------
    
    // --------------------------------------------------------------------------------
    //   Payment Deduction Type Descriptions
    // --------------------------------------------------------------------------------
    
    // --------------------------------------------------------------------------------
    //   Payment Deductions
    // --------------------------------------------------------------------------------
    
    // --------------------------------------------------------------------------------
    //   Payment Applications
    // --------------------------------------------------------------------------------
    
}

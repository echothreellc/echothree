// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.contact.server;

import com.echothree.model.control.associate.server.AssociateControl;
import com.echothree.model.control.communication.server.CommunicationControl;
import com.echothree.model.control.contact.common.ContactConstants;
import com.echothree.model.control.contact.common.workflow.EmailAddressStatusConstants;
import com.echothree.model.control.contact.common.workflow.EmailAddressVerificationConstants;
import com.echothree.model.control.contact.common.choice.ContactMechanismAliasTypeChoicesBean;
import com.echothree.model.control.contact.common.choice.ContactMechanismChoicesBean;
import com.echothree.model.control.contact.common.choice.ContactMechanismPurposeChoicesBean;
import com.echothree.model.control.contact.common.choice.ContactMechanismTypeChoicesBean;
import com.echothree.model.control.contact.common.choice.PostalAddressElementTypeChoicesBean;
import com.echothree.model.control.contact.common.choice.PostalAddressFormatChoicesBean;
import com.echothree.model.control.contact.common.choice.PostalAddressStatusChoicesBean;
import com.echothree.model.control.contact.common.transfer.ContactEmailAddressTransfer;
import com.echothree.model.control.contact.common.transfer.ContactInet4AddressTransfer;
import com.echothree.model.control.contact.common.transfer.ContactMechanismAliasTransfer;
import com.echothree.model.control.contact.common.transfer.ContactMechanismAliasTypeDescriptionTransfer;
import com.echothree.model.control.contact.common.transfer.ContactMechanismAliasTypeTransfer;
import com.echothree.model.control.contact.common.transfer.ContactMechanismPurposeTransfer;
import com.echothree.model.control.contact.common.transfer.ContactMechanismTransfer;
import com.echothree.model.control.contact.common.transfer.ContactMechanismTypeTransfer;
import com.echothree.model.control.contact.common.transfer.ContactPostalAddressTransfer;
import com.echothree.model.control.contact.common.transfer.ContactTelephoneTransfer;
import com.echothree.model.control.contact.common.transfer.ContactWebAddressTransfer;
import com.echothree.model.control.contact.common.transfer.PartyContactMechanismAliasTransfer;
import com.echothree.model.control.contact.common.transfer.PartyContactMechanismPurposeTransfer;
import com.echothree.model.control.contact.common.transfer.PartyContactMechanismRelationshipTransfer;
import com.echothree.model.control.contact.common.transfer.PartyContactMechanismTransfer;
import com.echothree.model.control.contact.common.transfer.PostalAddressElementTypeTransfer;
import com.echothree.model.control.contact.common.transfer.PostalAddressFormatDescriptionTransfer;
import com.echothree.model.control.contact.common.transfer.PostalAddressFormatTransfer;
import com.echothree.model.control.contact.common.transfer.PostalAddressLineElementTransfer;
import com.echothree.model.control.contact.common.transfer.PostalAddressLineTransfer;
import com.echothree.model.control.contact.server.transfer.ContactMechanismAliasTransferCache;
import com.echothree.model.control.contact.server.transfer.ContactMechanismAliasTypeDescriptionTransferCache;
import com.echothree.model.control.contact.server.transfer.ContactMechanismAliasTypeTransferCache;
import com.echothree.model.control.contact.server.transfer.ContactMechanismPurposeTransferCache;
import com.echothree.model.control.contact.server.transfer.ContactMechanismTransferCache;
import com.echothree.model.control.contact.server.transfer.ContactMechanismTypeTransferCache;
import com.echothree.model.control.contact.server.transfer.ContactTransferCaches;
import com.echothree.model.control.contact.server.transfer.PartyContactMechanismPurposeTransferCache;
import com.echothree.model.control.contact.server.transfer.PartyContactMechanismRelationshipTransferCache;
import com.echothree.model.control.contact.server.transfer.PartyContactMechanismTransferCache;
import com.echothree.model.control.contact.server.transfer.PostalAddressFormatDescriptionTransferCache;
import com.echothree.model.control.contact.server.transfer.PostalAddressFormatTransferCache;
import com.echothree.model.control.contact.server.transfer.PostalAddressLineElementTransferCache;
import com.echothree.model.control.contact.server.transfer.PostalAddressLineTransferCache;
import com.echothree.model.control.contactlist.server.ContactListControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.invoice.server.InvoiceControl;
import com.echothree.model.control.letter.server.LetterControl;
import com.echothree.model.control.order.server.OrderControl;
import com.echothree.model.control.payment.server.PaymentControl;
import com.echothree.model.control.shipment.server.ShipmentControl;
import com.echothree.model.control.contact.common.workflow.PostalAddressStatusConstants;
import com.echothree.model.control.contact.common.workflow.TelephoneStatusConstants;
import com.echothree.model.control.contact.common.workflow.WebAddressStatusConstants;
import com.echothree.model.control.contact.common.choice.EmailAddressStatusChoicesBean;
import com.echothree.model.control.contact.common.choice.EmailAddressVerificationChoicesBean;
import com.echothree.model.control.contact.common.choice.TelephoneStatusChoicesBean;
import com.echothree.model.control.contact.common.choice.WebAddressStatusChoicesBean;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.contact.common.pk.ContactMechanismAliasTypePK;
import com.echothree.model.data.contact.common.pk.ContactMechanismPK;
import com.echothree.model.data.contact.common.pk.ContactMechanismTypePK;
import com.echothree.model.data.contact.common.pk.PartyContactMechanismPK;
import com.echothree.model.data.contact.common.pk.PartyContactMechanismPurposePK;
import com.echothree.model.data.contact.common.pk.PostalAddressElementTypePK;
import com.echothree.model.data.contact.common.pk.PostalAddressFormatPK;
import com.echothree.model.data.contact.common.pk.PostalAddressLinePK;
import com.echothree.model.data.contact.server.entity.ContactEmailAddress;
import com.echothree.model.data.contact.server.entity.ContactInet4Address;
import com.echothree.model.data.contact.server.entity.ContactInet6Address;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.contact.server.entity.ContactMechanismAlias;
import com.echothree.model.data.contact.server.entity.ContactMechanismAliasType;
import com.echothree.model.data.contact.server.entity.ContactMechanismAliasTypeDescription;
import com.echothree.model.data.contact.server.entity.ContactMechanismAliasTypeDetail;
import com.echothree.model.data.contact.server.entity.ContactMechanismDetail;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurposeDescription;
import com.echothree.model.data.contact.server.entity.ContactMechanismType;
import com.echothree.model.data.contact.server.entity.ContactMechanismTypeDescription;
import com.echothree.model.data.contact.server.entity.ContactPostalAddress;
import com.echothree.model.data.contact.server.entity.ContactPostalAddressCorrection;
import com.echothree.model.data.contact.server.entity.ContactTelephone;
import com.echothree.model.data.contact.server.entity.ContactWebAddress;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.contact.server.entity.PartyContactMechanismAlias;
import com.echothree.model.data.contact.server.entity.PartyContactMechanismDetail;
import com.echothree.model.data.contact.server.entity.PartyContactMechanismPurpose;
import com.echothree.model.data.contact.server.entity.PartyContactMechanismPurposeDetail;
import com.echothree.model.data.contact.server.entity.PartyContactMechanismRelationship;
import com.echothree.model.data.contact.server.entity.PostalAddressElementType;
import com.echothree.model.data.contact.server.entity.PostalAddressElementTypeDescription;
import com.echothree.model.data.contact.server.entity.PostalAddressFormat;
import com.echothree.model.data.contact.server.entity.PostalAddressFormatDescription;
import com.echothree.model.data.contact.server.entity.PostalAddressFormatDetail;
import com.echothree.model.data.contact.server.entity.PostalAddressLine;
import com.echothree.model.data.contact.server.entity.PostalAddressLineDetail;
import com.echothree.model.data.contact.server.entity.PostalAddressLineElement;
import com.echothree.model.data.contact.server.factory.ContactEmailAddressFactory;
import com.echothree.model.data.contact.server.factory.ContactInet4AddressFactory;
import com.echothree.model.data.contact.server.factory.ContactInet6AddressFactory;
import com.echothree.model.data.contact.server.factory.ContactMechanismAliasFactory;
import com.echothree.model.data.contact.server.factory.ContactMechanismAliasTypeDescriptionFactory;
import com.echothree.model.data.contact.server.factory.ContactMechanismAliasTypeDetailFactory;
import com.echothree.model.data.contact.server.factory.ContactMechanismAliasTypeFactory;
import com.echothree.model.data.contact.server.factory.ContactMechanismDetailFactory;
import com.echothree.model.data.contact.server.factory.ContactMechanismFactory;
import com.echothree.model.data.contact.server.factory.ContactMechanismPurposeDescriptionFactory;
import com.echothree.model.data.contact.server.factory.ContactMechanismPurposeFactory;
import com.echothree.model.data.contact.server.factory.ContactMechanismTypeDescriptionFactory;
import com.echothree.model.data.contact.server.factory.ContactMechanismTypeFactory;
import com.echothree.model.data.contact.server.factory.ContactPostalAddressCorrectionFactory;
import com.echothree.model.data.contact.server.factory.ContactPostalAddressFactory;
import com.echothree.model.data.contact.server.factory.ContactTelephoneFactory;
import com.echothree.model.data.contact.server.factory.ContactWebAddressFactory;
import com.echothree.model.data.contact.server.factory.PartyContactMechanismAliasFactory;
import com.echothree.model.data.contact.server.factory.PartyContactMechanismDetailFactory;
import com.echothree.model.data.contact.server.factory.PartyContactMechanismFactory;
import com.echothree.model.data.contact.server.factory.PartyContactMechanismPurposeDetailFactory;
import com.echothree.model.data.contact.server.factory.PartyContactMechanismPurposeFactory;
import com.echothree.model.data.contact.server.factory.PartyContactMechanismRelationshipFactory;
import com.echothree.model.data.contact.server.factory.PostalAddressElementTypeDescriptionFactory;
import com.echothree.model.data.contact.server.factory.PostalAddressElementTypeFactory;
import com.echothree.model.data.contact.server.factory.PostalAddressFormatDescriptionFactory;
import com.echothree.model.data.contact.server.factory.PostalAddressFormatDetailFactory;
import com.echothree.model.data.contact.server.factory.PostalAddressFormatFactory;
import com.echothree.model.data.contact.server.factory.PostalAddressLineDetailFactory;
import com.echothree.model.data.contact.server.factory.PostalAddressLineElementFactory;
import com.echothree.model.data.contact.server.factory.PostalAddressLineFactory;
import com.echothree.model.data.contact.server.value.ContactEmailAddressValue;
import com.echothree.model.data.contact.server.value.ContactInet4AddressValue;
import com.echothree.model.data.contact.server.value.ContactInet6AddressValue;
import com.echothree.model.data.contact.server.value.ContactMechanismAliasTypeDescriptionValue;
import com.echothree.model.data.contact.server.value.ContactMechanismAliasTypeDetailValue;
import com.echothree.model.data.contact.server.value.ContactMechanismDetailValue;
import com.echothree.model.data.contact.server.value.ContactPostalAddressCorrectionValue;
import com.echothree.model.data.contact.server.value.ContactPostalAddressValue;
import com.echothree.model.data.contact.server.value.ContactTelephoneValue;
import com.echothree.model.data.contact.server.value.ContactWebAddressValue;
import com.echothree.model.data.contact.server.value.PartyContactMechanismDetailValue;
import com.echothree.model.data.contact.server.value.PartyContactMechanismPurposeDetailValue;
import com.echothree.model.data.contact.server.value.PostalAddressFormatDescriptionValue;
import com.echothree.model.data.contact.server.value.PostalAddressFormatDetailValue;
import com.echothree.model.data.contact.server.value.PostalAddressLineDetailValue;
import com.echothree.model.data.contact.server.value.PostalAddressLineElementValue;
import com.echothree.model.data.contactlist.server.entity.ContactList;
import com.echothree.model.data.contactlist.server.entity.ContactListContactMechanismPurpose;
import com.echothree.model.data.contactlist.server.entity.ContactListContactMechanismPurposeDetail;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.geo.common.pk.GeoCodePK;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.party.common.pk.NameSuffixPK;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.common.pk.PersonalTitlePK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.NameSuffix;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PersonalTitle;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
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

public class ContactControl
        extends BaseModelControl {
    
    /** Creates a new instance of ContactControl */
    public ContactControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Transfer Caches
    // --------------------------------------------------------------------------------
    
    private ContactTransferCaches contactTransferCaches = null;
    
    public ContactTransferCaches getContactTransferCaches(UserVisit userVisit) {
        if(contactTransferCaches == null) {
            contactTransferCaches = new ContactTransferCaches(userVisit, this);
        }
        
        return contactTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Types
    // --------------------------------------------------------------------------------
    
    public ContactMechanismType createContactMechanismType(String contactMechanismTypeName,
            ContactMechanismType parentContactMechanismType, Boolean isDefault, Integer sortOrder) {
        return ContactMechanismTypeFactory.getInstance().create(contactMechanismTypeName, parentContactMechanismType,
                isDefault, sortOrder);
    }
    
    public ContactMechanismType getContactMechanismTypeByName(String contactMechanismTypeName) {
        ContactMechanismType contactMechanismType = null;
        
        try {
            PreparedStatement ps = ContactMechanismTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM contactmechanismtypes " +
                    "WHERE cmt_contactmechanismtypename = ?");
            
            ps.setString(1, contactMechanismTypeName);
            
            contactMechanismType = ContactMechanismTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contactMechanismType;
    }
    
    public List<ContactMechanismType> getContactMechanismTypes() {
        PreparedStatement ps = ContactMechanismTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM contactmechanismtypes " +
                "ORDER BY cmt_sortorder, cmt_contactmechanismtypename");
        
        return ContactMechanismTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public ContactMechanismTypeChoicesBean getContactMechanismTypeChoices(String defaultContactMechanismTypeChoice, Language language, boolean allowNullChoice) {
        List<ContactMechanismType> contactMechanismTypes = getContactMechanismTypes();
        int size = contactMechanismTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
        }
        
        for(ContactMechanismType contactMechanismType: contactMechanismTypes) {
            String label = getBestContactMechanismTypeDescription(contactMechanismType, language);
            String value = contactMechanismType.getContactMechanismTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultContactMechanismTypeChoice == null? false: defaultContactMechanismTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && contactMechanismType.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new ContactMechanismTypeChoicesBean(labels, values, defaultValue);
    }
    
    public ContactMechanismTypeTransfer getContactMechanismTypeTransfer(UserVisit userVisit, ContactMechanismType contactMechanismType) {
        return getContactTransferCaches(userVisit).getContactMechanismTypeTransferCache().getContactMechanismTypeTransfer(contactMechanismType);
    }
    
    public List<ContactMechanismTypeTransfer> getContactMechanismTypeTransfers(UserVisit userVisit) {
        List<ContactMechanismType> entities = getContactMechanismTypes();
        List<ContactMechanismTypeTransfer> transfers = new ArrayList<>(entities.size());
        ContactMechanismTypeTransferCache cache = getContactTransferCaches(userVisit).getContactMechanismTypeTransferCache();
        
        entities.stream().forEach((entity) -> {
            transfers.add(cache.getContactMechanismTypeTransfer(entity));
        });
        
        return transfers;
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Type Descriptions
    // --------------------------------------------------------------------------------
    
    public ContactMechanismTypeDescription createContactMechanismTypeDescription(ContactMechanismType contactMechanismType,
            Language language, String description) {
        return ContactMechanismTypeDescriptionFactory.getInstance().create(contactMechanismType, language, description);
    }
    
    public ContactMechanismTypeDescription getContactMechanismTypeDescription(ContactMechanismType contactMechanismType,
            Language language) {
        ContactMechanismTypeDescription contactMechanismTypeDescription = null;
        
        try {
            PreparedStatement ps = ContactMechanismTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM contactmechanismtypedescriptions " +
                    "WHERE cmtd_cmt_contactmechanismtypeid = ? AND cmtd_lang_languageid = ?");
            
            ps.setLong(1, contactMechanismType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            contactMechanismTypeDescription = ContactMechanismTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contactMechanismTypeDescription;
    }
    
    public String getBestContactMechanismTypeDescription(ContactMechanismType contactMechanismType, Language language) {
        String description;
        ContactMechanismTypeDescription contactMechanismTypeDescription = getContactMechanismTypeDescription(contactMechanismType, language);
        
        if(contactMechanismTypeDescription == null && !language.getIsDefault()) {
            contactMechanismTypeDescription = getContactMechanismTypeDescription(contactMechanismType, getPartyControl().getDefaultLanguage());
        }
        
        if(contactMechanismTypeDescription == null) {
            description = contactMechanismType.getContactMechanismTypeName();
        } else {
            description = contactMechanismTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Alias Types
    // --------------------------------------------------------------------------------

    public ContactMechanismAliasType createContactMechanismAliasType(String contactMechanismAliasTypeName, String validationPattern, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        ContactMechanismAliasType defaultContactMechanismAliasType = getDefaultContactMechanismAliasType();
        boolean defaultFound = defaultContactMechanismAliasType != null;

        if(defaultFound && isDefault) {
            ContactMechanismAliasTypeDetailValue defaultContactMechanismAliasTypeDetailValue = getDefaultContactMechanismAliasTypeDetailValueForUpdate();

            defaultContactMechanismAliasTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateContactMechanismAliasTypeFromValue(defaultContactMechanismAliasTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        ContactMechanismAliasType contactMechanismAliasType = ContactMechanismAliasTypeFactory.getInstance().create();
        ContactMechanismAliasTypeDetail contactMechanismAliasTypeDetail = ContactMechanismAliasTypeDetailFactory.getInstance().create(contactMechanismAliasType,
                contactMechanismAliasTypeName, validationPattern, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        contactMechanismAliasType = ContactMechanismAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                contactMechanismAliasType.getPrimaryKey());
        contactMechanismAliasType.setActiveDetail(contactMechanismAliasTypeDetail);
        contactMechanismAliasType.setLastDetail(contactMechanismAliasTypeDetail);
        contactMechanismAliasType.store();

        sendEventUsingNames(contactMechanismAliasType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return contactMechanismAliasType;
    }

    private static final Map<EntityPermission, String> getContactMechanismAliasTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM contactmechanismaliastypes, contactmechanismaliastypedetails " +
                "WHERE cmchaltyp_activedetailid = cmchaltypdt_contactmechanismaliastypedetailid " +
                "AND cmchaltypdt_contactmechanismaliastypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM contactmechanismaliastypes, contactmechanismaliastypedetails " +
                "WHERE cmchaltyp_activedetailid = cmchaltypdt_contactmechanismaliastypedetailid " +
                "AND cmchaltypdt_contactmechanismaliastypename = ? " +
                "FOR UPDATE");
        getContactMechanismAliasTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private ContactMechanismAliasType getContactMechanismAliasTypeByName(String contactMechanismAliasTypeName, EntityPermission entityPermission) {
        return ContactMechanismAliasTypeFactory.getInstance().getEntityFromQuery(entityPermission, getContactMechanismAliasTypeByNameQueries, contactMechanismAliasTypeName);
    }

    public ContactMechanismAliasType getContactMechanismAliasTypeByName(String contactMechanismAliasTypeName) {
        return getContactMechanismAliasTypeByName(contactMechanismAliasTypeName, EntityPermission.READ_ONLY);
    }

    public ContactMechanismAliasType getContactMechanismAliasTypeByNameForUpdate(String contactMechanismAliasTypeName) {
        return getContactMechanismAliasTypeByName(contactMechanismAliasTypeName, EntityPermission.READ_WRITE);
    }

    public ContactMechanismAliasTypeDetailValue getContactMechanismAliasTypeDetailValueForUpdate(ContactMechanismAliasType contactMechanismAliasType) {
        return contactMechanismAliasType == null? null: contactMechanismAliasType.getLastDetailForUpdate().getContactMechanismAliasTypeDetailValue().clone();
    }

    public ContactMechanismAliasTypeDetailValue getContactMechanismAliasTypeDetailValueByNameForUpdate(String contactMechanismAliasTypeName) {
        return getContactMechanismAliasTypeDetailValueForUpdate(getContactMechanismAliasTypeByNameForUpdate(contactMechanismAliasTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultContactMechanismAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM contactmechanismaliastypes, contactmechanismaliastypedetails " +
                "WHERE cmchaltyp_activedetailid = cmchaltypdt_contactmechanismaliastypedetailid " +
                "AND cmchaltypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM contactmechanismaliastypes, contactmechanismaliastypedetails " +
                "WHERE cmchaltyp_activedetailid = cmchaltypdt_contactmechanismaliastypedetailid " +
                "AND cmchaltypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultContactMechanismAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private ContactMechanismAliasType getDefaultContactMechanismAliasType(EntityPermission entityPermission) {
        return ContactMechanismAliasTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultContactMechanismAliasTypeQueries);
    }

    public ContactMechanismAliasType getDefaultContactMechanismAliasType() {
        return getDefaultContactMechanismAliasType(EntityPermission.READ_ONLY);
    }

    public ContactMechanismAliasType getDefaultContactMechanismAliasTypeForUpdate() {
        return getDefaultContactMechanismAliasType(EntityPermission.READ_WRITE);
    }

    public ContactMechanismAliasTypeDetailValue getDefaultContactMechanismAliasTypeDetailValueForUpdate() {
        return getDefaultContactMechanismAliasTypeForUpdate().getLastDetailForUpdate().getContactMechanismAliasTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getContactMechanismAliasTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM contactmechanismaliastypes, contactmechanismaliastypedetails " +
                "WHERE cmchaltyp_activedetailid = cmchaltypdt_contactmechanismaliastypedetailid " +
                "ORDER BY cmchaltypdt_sortorder, cmchaltypdt_contactmechanismaliastypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM contactmechanismaliastypes, contactmechanismaliastypedetails " +
                "WHERE cmchaltyp_activedetailid = cmchaltypdt_contactmechanismaliastypedetailid " +
                "FOR UPDATE");
        getContactMechanismAliasTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ContactMechanismAliasType> getContactMechanismAliasTypes(EntityPermission entityPermission) {
        return ContactMechanismAliasTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getContactMechanismAliasTypesQueries);
    }

    public List<ContactMechanismAliasType> getContactMechanismAliasTypes() {
        return getContactMechanismAliasTypes(EntityPermission.READ_ONLY);
    }

    public List<ContactMechanismAliasType> getContactMechanismAliasTypesForUpdate() {
        return getContactMechanismAliasTypes(EntityPermission.READ_WRITE);
    }

    private List<ContactMechanismAliasType> getContactMechanismAliasTypesByParentContactMechanismAliasType(ContactMechanismAliasType parentContactMechanismAliasType,
            EntityPermission entityPermission) {
        List<ContactMechanismAliasType> contactMechanismAliasTypes = null;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contactmechanismaliastypes, contactmechanismaliastypedetails " +
                        "WHERE cmchaltyp_activedetailid = cmchaltypdt_contactmechanismaliastypedetailid AND cmchaltypdt_parentcontactmechanismaliastypeid = ? " +
                        "ORDER BY cmchaltypdt_sortorder, cmchaltypdt_contactmechanismaliastypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contactmechanismaliastypes, contactmechanismaliastypedetails " +
                        "WHERE cmchaltyp_activedetailid = cmchaltypdt_contactmechanismaliastypedetailid AND cmchaltypdt_parentcontactmechanismaliastypeid = ? " +
                        "FOR UPDATE";
            }

            PreparedStatement ps = ContactMechanismAliasTypeFactory.getInstance().prepareStatement(query);

            ps.setLong(1, parentContactMechanismAliasType.getPrimaryKey().getEntityId());

            contactMechanismAliasTypes = ContactMechanismAliasTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return contactMechanismAliasTypes;
    }

    public List<ContactMechanismAliasType> getContactMechanismAliasTypesByParentContactMechanismAliasType(ContactMechanismAliasType parentContactMechanismAliasType) {
        return getContactMechanismAliasTypesByParentContactMechanismAliasType(parentContactMechanismAliasType, EntityPermission.READ_ONLY);
    }

    public List<ContactMechanismAliasType> getContactMechanismAliasTypesByParentContactMechanismAliasTypeForUpdate(ContactMechanismAliasType parentContactMechanismAliasType) {
        return getContactMechanismAliasTypesByParentContactMechanismAliasType(parentContactMechanismAliasType, EntityPermission.READ_WRITE);
    }

    public ContactMechanismAliasTypeTransfer getContactMechanismAliasTypeTransfer(UserVisit userVisit, ContactMechanismAliasType contactMechanismAliasType) {
        return getContactTransferCaches(userVisit).getContactMechanismAliasTypeTransferCache().getContactMechanismAliasTypeTransfer(contactMechanismAliasType);
    }

    public List<ContactMechanismAliasTypeTransfer> getContactMechanismAliasTypeTransfers(UserVisit userVisit, List<ContactMechanismAliasType> contactMechanismAliasTypes) {
        List<ContactMechanismAliasTypeTransfer> contactMechanismAliasTypeTransfers = new ArrayList<>(contactMechanismAliasTypes.size());
        ContactMechanismAliasTypeTransferCache contactMechanismAliasTypeTransferCache = getContactTransferCaches(userVisit).getContactMechanismAliasTypeTransferCache();

        contactMechanismAliasTypes.stream().forEach((contactMechanismAliasType) -> {
            contactMechanismAliasTypeTransfers.add(contactMechanismAliasTypeTransferCache.getContactMechanismAliasTypeTransfer(contactMechanismAliasType));
        });

        return contactMechanismAliasTypeTransfers;
    }

    public List<ContactMechanismAliasTypeTransfer> getContactMechanismAliasTypeTransfers(UserVisit userVisit) {
        return getContactMechanismAliasTypeTransfers(userVisit, getContactMechanismAliasTypes());
    }

    public List<ContactMechanismAliasTypeTransfer> getContactMechanismAliasTypeTransfersByParentContactMechanismAliasType(UserVisit userVisit,
            ContactMechanismAliasType parentContactMechanismAliasType) {
        return getContactMechanismAliasTypeTransfers(userVisit, getContactMechanismAliasTypesByParentContactMechanismAliasType(parentContactMechanismAliasType));
    }

    public ContactMechanismAliasTypeChoicesBean getContactMechanismAliasTypeChoices(String defaultContactMechanismAliasTypeChoice, Language language, boolean allowNullChoice) {
        List<ContactMechanismAliasType> contactMechanismAliasTypes = getContactMechanismAliasTypes();
        int size = contactMechanismAliasTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultContactMechanismAliasTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(ContactMechanismAliasType contactMechanismAliasType: contactMechanismAliasTypes) {
            ContactMechanismAliasTypeDetail contactMechanismAliasTypeDetail = contactMechanismAliasType.getLastDetail();

            String label = getBestContactMechanismAliasTypeDescription(contactMechanismAliasType, language);
            String value = contactMechanismAliasTypeDetail.getContactMechanismAliasTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultContactMechanismAliasTypeChoice == null? false: defaultContactMechanismAliasTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && contactMechanismAliasTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ContactMechanismAliasTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateContactMechanismAliasTypeFromValue(ContactMechanismAliasTypeDetailValue contactMechanismAliasTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(contactMechanismAliasTypeDetailValue.hasBeenModified()) {
            ContactMechanismAliasType contactMechanismAliasType = ContactMechanismAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     contactMechanismAliasTypeDetailValue.getContactMechanismAliasTypePK());
            ContactMechanismAliasTypeDetail contactMechanismAliasTypeDetail = contactMechanismAliasType.getActiveDetailForUpdate();

            contactMechanismAliasTypeDetail.setThruTime(session.START_TIME_LONG);
            contactMechanismAliasTypeDetail.store();

            ContactMechanismAliasTypePK contactMechanismAliasTypePK = contactMechanismAliasTypeDetail.getContactMechanismAliasTypePK(); // Not updated
            String contactMechanismAliasTypeName = contactMechanismAliasTypeDetailValue.getContactMechanismAliasTypeName();
            String validationPattern = contactMechanismAliasTypeDetailValue.getValidationPattern();
            Boolean isDefault = contactMechanismAliasTypeDetailValue.getIsDefault();
            Integer sortOrder = contactMechanismAliasTypeDetailValue.getSortOrder();

            if(checkDefault) {
                ContactMechanismAliasType defaultContactMechanismAliasType = getDefaultContactMechanismAliasType();
                boolean defaultFound = defaultContactMechanismAliasType != null && !defaultContactMechanismAliasType.equals(contactMechanismAliasType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ContactMechanismAliasTypeDetailValue defaultContactMechanismAliasTypeDetailValue = getDefaultContactMechanismAliasTypeDetailValueForUpdate();

                    defaultContactMechanismAliasTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateContactMechanismAliasTypeFromValue(defaultContactMechanismAliasTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            contactMechanismAliasTypeDetail = ContactMechanismAliasTypeDetailFactory.getInstance().create(contactMechanismAliasTypePK,
                    contactMechanismAliasTypeName, validationPattern, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            contactMechanismAliasType.setActiveDetail(contactMechanismAliasTypeDetail);
            contactMechanismAliasType.setLastDetail(contactMechanismAliasTypeDetail);

            sendEventUsingNames(contactMechanismAliasTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateContactMechanismAliasTypeFromValue(ContactMechanismAliasTypeDetailValue contactMechanismAliasTypeDetailValue, BasePK updatedBy) {
        updateContactMechanismAliasTypeFromValue(contactMechanismAliasTypeDetailValue, true, updatedBy);
    }

    public void deleteContactMechanismAliasType(ContactMechanismAliasType contactMechanismAliasType, BasePK deletedBy) {
        deleteContactMechanismAliasTypeDescriptionsByContactMechanismAliasType(contactMechanismAliasType, deletedBy);
        deleteContactMechanismAliasesByContactMechanismAliasType(contactMechanismAliasType, deletedBy);

        ContactMechanismAliasTypeDetail contactMechanismAliasTypeDetail = contactMechanismAliasType.getLastDetailForUpdate();
        contactMechanismAliasTypeDetail.setThruTime(session.START_TIME_LONG);
        contactMechanismAliasType.setActiveDetail(null);
        contactMechanismAliasType.store();

        // Check for default, and pick one if necessary
        ContactMechanismAliasType defaultContactMechanismAliasType = getDefaultContactMechanismAliasType();
        if(defaultContactMechanismAliasType == null) {
            List<ContactMechanismAliasType> contactMechanismAliasTypes = getContactMechanismAliasTypesForUpdate();

            if(!contactMechanismAliasTypes.isEmpty()) {
                Iterator<ContactMechanismAliasType> iter = contactMechanismAliasTypes.iterator();
                if(iter.hasNext()) {
                    defaultContactMechanismAliasType = iter.next();
                }
                ContactMechanismAliasTypeDetailValue contactMechanismAliasTypeDetailValue = defaultContactMechanismAliasType.getLastDetailForUpdate().getContactMechanismAliasTypeDetailValue().clone();

                contactMechanismAliasTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateContactMechanismAliasTypeFromValue(contactMechanismAliasTypeDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(contactMechanismAliasType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Contact Mechanism Alias Type Descriptions
    // --------------------------------------------------------------------------------

    public ContactMechanismAliasTypeDescription createContactMechanismAliasTypeDescription(ContactMechanismAliasType contactMechanismAliasType,
            Language language, String description, BasePK createdBy) {
        ContactMechanismAliasTypeDescription contactMechanismAliasTypeDescription = ContactMechanismAliasTypeDescriptionFactory.getInstance().create(contactMechanismAliasType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(contactMechanismAliasType.getPrimaryKey(), EventTypes.MODIFY.name(), contactMechanismAliasTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return contactMechanismAliasTypeDescription;
    }

    private static final Map<EntityPermission, String> getContactMechanismAliasTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM contactmechanismaliastypedescriptions " +
                "WHERE cmchaltypd_cmchaltyp_contactmechanismaliastypeid = ? AND cmchaltypd_lang_languageid = ? AND cmchaltypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM contactmechanismaliastypedescriptions " +
                "WHERE cmchaltypd_cmchaltyp_contactmechanismaliastypeid = ? AND cmchaltypd_lang_languageid = ? AND cmchaltypd_thrutime = ? " +
                "FOR UPDATE");
        getContactMechanismAliasTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ContactMechanismAliasTypeDescription getContactMechanismAliasTypeDescription(ContactMechanismAliasType contactMechanismAliasType,
            Language language, EntityPermission entityPermission) {
        return ContactMechanismAliasTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getContactMechanismAliasTypeDescriptionQueries,
                contactMechanismAliasType, language, Session.MAX_TIME);
    }

    public ContactMechanismAliasTypeDescription getContactMechanismAliasTypeDescription(ContactMechanismAliasType contactMechanismAliasType, Language language) {
        return getContactMechanismAliasTypeDescription(contactMechanismAliasType, language, EntityPermission.READ_ONLY);
    }

    public ContactMechanismAliasTypeDescription getContactMechanismAliasTypeDescriptionForUpdate(ContactMechanismAliasType contactMechanismAliasType, Language language) {
        return getContactMechanismAliasTypeDescription(contactMechanismAliasType, language, EntityPermission.READ_WRITE);
    }

    public ContactMechanismAliasTypeDescriptionValue getContactMechanismAliasTypeDescriptionValue(ContactMechanismAliasTypeDescription contactMechanismAliasTypeDescription) {
        return contactMechanismAliasTypeDescription == null? null: contactMechanismAliasTypeDescription.getContactMechanismAliasTypeDescriptionValue().clone();
    }

    public ContactMechanismAliasTypeDescriptionValue getContactMechanismAliasTypeDescriptionValueForUpdate(ContactMechanismAliasType contactMechanismAliasType, Language language) {
        return getContactMechanismAliasTypeDescriptionValue(getContactMechanismAliasTypeDescriptionForUpdate(contactMechanismAliasType, language));
    }

    private static final Map<EntityPermission, String> getContactMechanismAliasTypeDescriptionsByContactMechanismAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM contactmechanismaliastypedescriptions, languages " +
                "WHERE cmchaltypd_cmchaltyp_contactmechanismaliastypeid = ? AND cmchaltypd_thrutime = ? AND cmchaltypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM contactmechanismaliastypedescriptions " +
                "WHERE cmchaltypd_cmchaltyp_contactmechanismaliastypeid = ? AND cmchaltypd_thrutime = ? " +
                "FOR UPDATE");
        getContactMechanismAliasTypeDescriptionsByContactMechanismAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ContactMechanismAliasTypeDescription> getContactMechanismAliasTypeDescriptionsByContactMechanismAliasType(ContactMechanismAliasType contactMechanismAliasType,
            EntityPermission entityPermission) {
        return ContactMechanismAliasTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getContactMechanismAliasTypeDescriptionsByContactMechanismAliasTypeQueries,
                contactMechanismAliasType, Session.MAX_TIME);
    }

    public List<ContactMechanismAliasTypeDescription> getContactMechanismAliasTypeDescriptionsByContactMechanismAliasType(ContactMechanismAliasType contactMechanismAliasType) {
        return getContactMechanismAliasTypeDescriptionsByContactMechanismAliasType(contactMechanismAliasType, EntityPermission.READ_ONLY);
    }

    public List<ContactMechanismAliasTypeDescription> getContactMechanismAliasTypeDescriptionsByContactMechanismAliasTypeForUpdate(ContactMechanismAliasType contactMechanismAliasType) {
        return getContactMechanismAliasTypeDescriptionsByContactMechanismAliasType(contactMechanismAliasType, EntityPermission.READ_WRITE);
    }

    public String getBestContactMechanismAliasTypeDescription(ContactMechanismAliasType contactMechanismAliasType, Language language) {
        String description;
        ContactMechanismAliasTypeDescription contactMechanismAliasTypeDescription = getContactMechanismAliasTypeDescription(contactMechanismAliasType, language);

        if(contactMechanismAliasTypeDescription == null && !language.getIsDefault()) {
            contactMechanismAliasTypeDescription = getContactMechanismAliasTypeDescription(contactMechanismAliasType, getPartyControl().getDefaultLanguage());
        }

        if(contactMechanismAliasTypeDescription == null) {
            description = contactMechanismAliasType.getLastDetail().getContactMechanismAliasTypeName();
        } else {
            description = contactMechanismAliasTypeDescription.getDescription();
        }

        return description;
    }

    public ContactMechanismAliasTypeDescriptionTransfer getContactMechanismAliasTypeDescriptionTransfer(UserVisit userVisit, ContactMechanismAliasTypeDescription contactMechanismAliasTypeDescription) {
        return getContactTransferCaches(userVisit).getContactMechanismAliasTypeDescriptionTransferCache().getContactMechanismAliasTypeDescriptionTransfer(contactMechanismAliasTypeDescription);
    }

    public List<ContactMechanismAliasTypeDescriptionTransfer> getContactMechanismAliasTypeDescriptionTransfersByContactMechanismAliasType(UserVisit userVisit, ContactMechanismAliasType contactMechanismAliasType) {
        List<ContactMechanismAliasTypeDescription> contactMechanismAliasTypeDescriptions = getContactMechanismAliasTypeDescriptionsByContactMechanismAliasType(contactMechanismAliasType);
        List<ContactMechanismAliasTypeDescriptionTransfer> contactMechanismAliasTypeDescriptionTransfers = new ArrayList<>(contactMechanismAliasTypeDescriptions.size());
        ContactMechanismAliasTypeDescriptionTransferCache contactMechanismAliasTypeDescriptionTransferCache = getContactTransferCaches(userVisit).getContactMechanismAliasTypeDescriptionTransferCache();

        contactMechanismAliasTypeDescriptions.stream().forEach((contactMechanismAliasTypeDescription) -> {
            contactMechanismAliasTypeDescriptionTransfers.add(contactMechanismAliasTypeDescriptionTransferCache.getContactMechanismAliasTypeDescriptionTransfer(contactMechanismAliasTypeDescription));
        });

        return contactMechanismAliasTypeDescriptionTransfers;
    }

    public void updateContactMechanismAliasTypeDescriptionFromValue(ContactMechanismAliasTypeDescriptionValue contactMechanismAliasTypeDescriptionValue, BasePK updatedBy) {
        if(contactMechanismAliasTypeDescriptionValue.hasBeenModified()) {
            ContactMechanismAliasTypeDescription contactMechanismAliasTypeDescription = ContactMechanismAliasTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    contactMechanismAliasTypeDescriptionValue.getPrimaryKey());

            contactMechanismAliasTypeDescription.setThruTime(session.START_TIME_LONG);
            contactMechanismAliasTypeDescription.store();

            ContactMechanismAliasType contactMechanismAliasType = contactMechanismAliasTypeDescription.getContactMechanismAliasType();
            Language language = contactMechanismAliasTypeDescription.getLanguage();
            String description = contactMechanismAliasTypeDescriptionValue.getDescription();

            contactMechanismAliasTypeDescription = ContactMechanismAliasTypeDescriptionFactory.getInstance().create(contactMechanismAliasType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(contactMechanismAliasType.getPrimaryKey(), EventTypes.MODIFY.name(), contactMechanismAliasTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteContactMechanismAliasTypeDescription(ContactMechanismAliasTypeDescription contactMechanismAliasTypeDescription, BasePK deletedBy) {
        contactMechanismAliasTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(contactMechanismAliasTypeDescription.getContactMechanismAliasTypePK(), EventTypes.MODIFY.name(), contactMechanismAliasTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteContactMechanismAliasTypeDescriptionsByContactMechanismAliasType(ContactMechanismAliasType contactMechanismAliasType, BasePK deletedBy) {
        List<ContactMechanismAliasTypeDescription> contactMechanismAliasTypeDescriptions = getContactMechanismAliasTypeDescriptionsByContactMechanismAliasTypeForUpdate(contactMechanismAliasType);

        contactMechanismAliasTypeDescriptions.stream().forEach((contactMechanismAliasTypeDescription) -> {
            deleteContactMechanismAliasTypeDescription(contactMechanismAliasTypeDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Contact Mechanism Purposes
    // --------------------------------------------------------------------------------
    
    public ContactMechanismPurpose createContactMechanismPurpose(String contactMechanismPurposeName,
            ContactMechanismType contactMechanismType, Boolean eventSubscriber, Boolean isDefault, Integer sortOrder) {
        return ContactMechanismPurposeFactory.getInstance().create(contactMechanismPurposeName, contactMechanismType,
                eventSubscriber, isDefault, sortOrder);
    }
    
    public ContactMechanismPurpose getContactMechanismPurposeByName(String contactMechanismPurposeName) {
        ContactMechanismPurpose contactMechanismPurpose = null;
        
        try {
            PreparedStatement ps = ContactMechanismPurposeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM contactmechanismpurposes " +
                    "WHERE cmpr_contactmechanismpurposename = ?");
            
            ps.setString(1, contactMechanismPurposeName);
            
            contactMechanismPurpose = ContactMechanismPurposeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contactMechanismPurpose;
    }
    
    public List<ContactMechanismPurpose> getContactMechanismPurposes() {
        PreparedStatement ps = ContactMechanismPurposeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM contactmechanismpurposes " +
                "ORDER BY cmpr_sortorder, cmpr_contactmechanismpurposename");
        
        return ContactMechanismPurposeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public List<ContactMechanismPurpose> getContactMechanismPurposesByContactMechanismType(ContactMechanismType contactMechanismType) {
        List<ContactMechanismPurpose> contactMechanismPurposes = null;
        
        try {
            PreparedStatement ps = ContactMechanismPurposeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM contactmechanismpurposes " +
                    "WHERE cmpr_cmt_contactmechanismtypeid = ? " +
                    "ORDER BY cmpr_sortorder, cmpr_contactmechanismpurposename");
            
            ps.setLong(1, contactMechanismType.getPrimaryKey().getEntityId());
            
            contactMechanismPurposes = ContactMechanismPurposeFactory.getInstance().getEntitiesFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contactMechanismPurposes;
    }
    
    public ContactMechanismPurposeChoicesBean getContactMechanismPurposeChoices(String defaultContactMechanismPurposeChoice, Language language,
            boolean allowNullChoice) {
        List<ContactMechanismPurpose> contactMechanismPurposes = getContactMechanismPurposes();
        int size = contactMechanismPurposes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
        }
        
        for(ContactMechanismPurpose contactMechanismPurpose: contactMechanismPurposes) {
            String label = getBestContactMechanismPurposeDescription(contactMechanismPurpose, language) + " ("
                    + getBestContactMechanismTypeDescription(contactMechanismPurpose.getContactMechanismType(), language) + ")";
            String value = contactMechanismPurpose.getContactMechanismPurposeName();
            
            labels.add(label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultContactMechanismPurposeChoice == null ? false : defaultContactMechanismPurposeChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null) {
                // Not completely accurate, since isDefault is within the scope of a ContactMechanismType, and we
                // just pick the first one in the List of possibilities here.
                defaultValue = value;
            }
        }
        
        return new ContactMechanismPurposeChoicesBean(labels, values, defaultValue);
    }
    
    public ContactMechanismPurposeChoicesBean getContactMechanismPurposeChoicesByContactMechanismType(ContactMechanismType contactMechanismType,
            String defaultContactMechanismPurposeChoice, Language language, boolean allowNullChoice) {
        List<ContactMechanismPurpose> contactMechanismPurposes = getContactMechanismPurposesByContactMechanismType(contactMechanismType);
        int size = contactMechanismPurposes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
        }
        
        for(ContactMechanismPurpose contactMechanismPurpose: contactMechanismPurposes) {
            String label = getBestContactMechanismPurposeDescription(contactMechanismPurpose, language);
            String value = contactMechanismPurpose.getContactMechanismPurposeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultContactMechanismPurposeChoice == null? false: defaultContactMechanismPurposeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && contactMechanismPurpose.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new ContactMechanismPurposeChoicesBean(labels, values, defaultValue);
    }
    
    public ContactMechanismPurposeChoicesBean getContactMechanismPurposeChoicesByContactList(String defaultContactListContactMechanismPurposeChoice, Language language, boolean allowNullChoice,
            ContactList contactList) {
        ContactListControl contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);
        List<ContactListContactMechanismPurpose> contactListContactMechanismPurposes = contactListControl.getContactListContactMechanismPurposesByContactList(contactList);
        int size = contactListContactMechanismPurposes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultContactListContactMechanismPurposeChoice == null) {
                defaultValue = "";
            }
        }

        for(ContactListContactMechanismPurpose contactListContactMechanismPurpose: contactListContactMechanismPurposes) {
            ContactListContactMechanismPurposeDetail contactListContactMechanismPurposeDetail = contactListContactMechanismPurpose.getLastDetail();
            ContactMechanismPurpose contactMechanismPurpose = contactListContactMechanismPurposeDetail.getContactMechanismPurpose();

            String label = getBestContactMechanismPurposeDescription(contactMechanismPurpose, language);
            String value = contactMechanismPurpose.getContactMechanismPurposeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultContactListContactMechanismPurposeChoice == null? false: defaultContactListContactMechanismPurposeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && contactListContactMechanismPurposeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ContactMechanismPurposeChoicesBean(labels, values, defaultValue);
    }
    
    public ContactMechanismPurposeTransfer getContactMechanismPurposeTransfer(UserVisit userVisit, ContactMechanismPurpose contactMechanismPurpose) {
        return getContactTransferCaches(userVisit).getContactMechanismPurposeTransferCache().getContactMechanismPurposeTransfer(contactMechanismPurpose);
    }
    
    public List<ContactMechanismPurposeTransfer> getContactMechanismPurposeTransfers(UserVisit userVisit, List<ContactMechanismPurpose> entities) {
        List<ContactMechanismPurposeTransfer> transfers = new ArrayList<>(entities.size());
        ContactMechanismPurposeTransferCache cache = getContactTransferCaches(userVisit).getContactMechanismPurposeTransferCache();
        
        entities.stream().forEach((entity) -> {
            transfers.add(cache.getContactMechanismPurposeTransfer(entity));
        });
        
        return transfers;
    }
    
    public List<ContactMechanismPurposeTransfer> getContactMechanismPurposeTransfers(UserVisit userVisit) {
        return getContactMechanismPurposeTransfers(userVisit, getContactMechanismPurposes());
    }
    
    public List<ContactMechanismPurposeTransfer> getContactMechanismPurposeTransfersByContactMechanismType(UserVisit userVisit, ContactMechanismType contactMechanismType) {
        return getContactMechanismPurposeTransfers(userVisit, getContactMechanismPurposesByContactMechanismType(contactMechanismType));
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Purpose Descriptions
    // --------------------------------------------------------------------------------
    
    public ContactMechanismPurposeDescription createContactMechanismPurposeDescription(ContactMechanismPurpose contactMechanismPurpose,
            Language language, String description) {
        return ContactMechanismPurposeDescriptionFactory.getInstance().create(contactMechanismPurpose, language, description);
    }
    
    public ContactMechanismPurposeDescription getContactMechanismPurposeDescription(ContactMechanismPurpose contactMechanismPurpose,
            Language language) {
        ContactMechanismPurposeDescription contactMechanismPurposeDescription = null;
        
        try {
            PreparedStatement ps = ContactMechanismPurposeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM contactmechanismpurposedescriptions " +
                    "WHERE cmprd_cmpr_contactmechanismpurposeid = ? AND cmprd_lang_languageid = ?");
            
            ps.setLong(1, contactMechanismPurpose.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            contactMechanismPurposeDescription = ContactMechanismPurposeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contactMechanismPurposeDescription;
    }
    
    public String getBestContactMechanismPurposeDescription(ContactMechanismPurpose contactMechanismPurpose, Language language) {
        String description;
        ContactMechanismPurposeDescription contactMechanismPurposeDescription = getContactMechanismPurposeDescription(contactMechanismPurpose, language);
        
        if(contactMechanismPurposeDescription == null && !language.getIsDefault()) {
            contactMechanismPurposeDescription = getContactMechanismPurposeDescription(contactMechanismPurpose, getPartyControl().getDefaultLanguage());
        }
        
        if(contactMechanismPurposeDescription == null) {
            description = contactMechanismPurpose.getContactMechanismPurposeName();
        } else {
            description = contactMechanismPurposeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanisms
    // --------------------------------------------------------------------------------
    
    public ContactMechanism createContactMechanism(String contactMechanismName, ContactMechanismType contactMechanismType,
            Boolean allowSolicitation, BasePK createdBy) {
        ContactMechanism contactMechanism = ContactMechanismFactory.getInstance().create();
        ContactMechanismDetail contactMechanismDetail = ContactMechanismDetailFactory.getInstance().create(contactMechanism,
                contactMechanismName, contactMechanismType, allowSolicitation, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        contactMechanism = ContactMechanismFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                contactMechanism.getPrimaryKey());
        contactMechanism.setActiveDetail(contactMechanismDetail);
        contactMechanism.setLastDetail(contactMechanismDetail);
        contactMechanism.store();
        
        sendEventUsingNames(contactMechanism.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return contactMechanism;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHOTHREE.ContactMechanism */
    public ContactMechanism getContactMechanismByEntityInstance(EntityInstance entityInstance) {
        ContactMechanismPK pk = new ContactMechanismPK(entityInstance.getEntityUniqueId());
        ContactMechanism contactMechanism = ContactMechanismFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
        
        return contactMechanism;
    }
    
    public long countContactMechanisms() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM contactmechanisms, contactmechanismdetails " +
                "WHERE cmch_activedetailid = cmchdt_contactmechanismdetailid");
    }

    private ContactMechanism getContactMechanismByName(String contactMechanismName, EntityPermission entityPermission) {
        ContactMechanism contactMechanism = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contactmechanisms, contactmechanismdetails " +
                        "WHERE cmch_activedetailid = cmchdt_contactmechanismdetailid AND cmchdt_contactmechanismname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contactmechanisms, contactmechanismdetails " +
                        "WHERE cmch_activedetailid = cmchdt_contactmechanismdetailid AND cmchdt_contactmechanismname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContactMechanismFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, contactMechanismName);
            
            contactMechanism = ContactMechanismFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contactMechanism;
    }
    
    public ContactMechanism getContactMechanismByName(String contactMechanismName) {
        return getContactMechanismByName(contactMechanismName, EntityPermission.READ_ONLY);
    }
    
    public ContactMechanism getContactMechanismByNameForUpdate(String contactMechanismName) {
        return getContactMechanismByName(contactMechanismName, EntityPermission.READ_WRITE);
    }
    
    public ContactMechanismDetailValue getContactMechanismDetailValue(ContactMechanismDetail contactMechanismDetail) {
        return contactMechanismDetail == null? null: contactMechanismDetail.getContactMechanismDetailValue().clone();
    }
    
    public ContactMechanismDetailValue getContactMechanismDetailValueByNameForUpdate(String contactMechanismName) {
        ContactMechanism contactMechanism = getContactMechanismByNameForUpdate(contactMechanismName);
        
        return contactMechanism == null? null: getContactMechanismDetailValue(contactMechanism.getLastDetailForUpdate());
    }
    
    public ContactMechanismTransfer getContactMechanismTransfer(UserVisit userVisit, ContactMechanism contactMechanism) {
        return getContactTransferCaches(userVisit).getContactMechanismTransferCache().getContactMechanismTransfer(contactMechanism);
    }
    
    public List<ContactMechanismTransfer> getContactMechanismTransfersByParty(UserVisit userVisit, Party party) {
        List<PartyContactMechanism> partyContactMechanisms = getPartyContactMechanismsByParty(party);
        List<ContactMechanismTransfer> contactMechanismTransfers = new ArrayList<>(partyContactMechanisms.size());
        ContactMechanismTransferCache contactMechanismTransferCache = getContactTransferCaches(userVisit).getContactMechanismTransferCache();
        
        partyContactMechanisms.stream().forEach((partyContactMechanism) -> {
            contactMechanismTransfers.add(contactMechanismTransferCache.getContactMechanismTransfer(partyContactMechanism.getLastDetail().getContactMechanism()));
        });
        
        return contactMechanismTransfers;
    }
    
    public EmailAddressStatusChoicesBean getEmailAddressStatusChoices(String defaultEmailAddressStatusChoice, Language language, boolean allowNullChoice, ContactMechanism contactMechanism,
            PartyPK partyPK) {
        WorkflowControl workflowControl = getWorkflowControl();
        EmailAddressStatusChoicesBean emailAddressStatusChoicesBean = new EmailAddressStatusChoicesBean();
        EntityInstance entityInstance = getEntityInstanceByBaseEntity(contactMechanism);
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(EmailAddressStatusConstants.Workflow_EMAIL_ADDRESS_STATUS,
                entityInstance);
        
        workflowControl.getWorkflowDestinationChoices(emailAddressStatusChoicesBean, defaultEmailAddressStatusChoice, language, allowNullChoice, workflowEntityStatus.getWorkflowStep(), partyPK);
        
        return emailAddressStatusChoicesBean;
    }
    
    public void setEmailAddressStatus(ExecutionErrorAccumulator eea,  ContactMechanism contactMechanism, String emailAddressStatusChoice, PartyPK modifiedBy) {
        WorkflowControl workflowControl = getWorkflowControl();
        EntityInstance entityInstance = getEntityInstanceByBaseEntity(contactMechanism);
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(EmailAddressStatusConstants.Workflow_EMAIL_ADDRESS_STATUS,
                entityInstance);
        WorkflowDestination workflowDestination = emailAddressStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), emailAddressStatusChoice);
        
        if(workflowDestination != null || emailAddressStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownEmailAddressStatusChoice.name(), emailAddressStatusChoice);
        }
    }
    
    public EmailAddressVerificationChoicesBean getEmailAddressVerificationChoices(String defaultEmailAddressVerificationChoice, Language language, boolean allowNullChoice, ContactMechanism contactMechanism,
            PartyPK partyPK) {
        WorkflowControl workflowControl = getWorkflowControl();
        EmailAddressVerificationChoicesBean emailAddressVerificationChoicesBean = new EmailAddressVerificationChoicesBean();
        EntityInstance entityInstance = getEntityInstanceByBaseEntity(contactMechanism);
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(EmailAddressVerificationConstants.Workflow_EMAIL_ADDRESS_VERIFICATION,
                entityInstance);
        
        workflowControl.getWorkflowDestinationChoices(emailAddressVerificationChoicesBean, defaultEmailAddressVerificationChoice, language, allowNullChoice, workflowEntityStatus.getWorkflowStep(), partyPK);
        
        return emailAddressVerificationChoicesBean;
    }
    
    public void setEmailAddressVerification(ExecutionErrorAccumulator eea,  ContactMechanism contactMechanism, String emailAddressVerificationChoice, PartyPK modifiedBy) {
        WorkflowControl workflowControl = getWorkflowControl();
        EntityInstance entityInstance = getEntityInstanceByBaseEntity(contactMechanism);
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(EmailAddressVerificationConstants.Workflow_EMAIL_ADDRESS_VERIFICATION,
                entityInstance);
        WorkflowDestination workflowDestination = emailAddressVerificationChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), emailAddressVerificationChoice);
        
        if(workflowDestination != null || emailAddressVerificationChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownEmailAddressVerificationChoice.name(), emailAddressVerificationChoice);
        }
    }
    
    public PostalAddressStatusChoicesBean getPostalAddressStatusChoices(String defaultPostalAddressStatusChoice, Language language, boolean allowNullChoice, ContactMechanism contactMechanism,
            PartyPK partyPK) {
        WorkflowControl workflowControl = getWorkflowControl();
        PostalAddressStatusChoicesBean postalAddressStatusChoicesBean = new PostalAddressStatusChoicesBean();
        EntityInstance entityInstance = getEntityInstanceByBaseEntity(contactMechanism);
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(PostalAddressStatusConstants.Workflow_POSTAL_ADDRESS_STATUS,
                entityInstance);
        
        workflowControl.getWorkflowDestinationChoices(postalAddressStatusChoicesBean, defaultPostalAddressStatusChoice, language, allowNullChoice, workflowEntityStatus.getWorkflowStep(), partyPK);
        
        return postalAddressStatusChoicesBean;
    }
    
    public void setPostalAddressStatus(ExecutionErrorAccumulator eea,  ContactMechanism contactMechanism, String postalAddressStatusChoice, PartyPK modifiedBy) {
        WorkflowControl workflowControl = getWorkflowControl();
        EntityInstance entityInstance = getEntityInstanceByBaseEntity(contactMechanism);
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(PostalAddressStatusConstants.Workflow_POSTAL_ADDRESS_STATUS,
                entityInstance);
        WorkflowDestination workflowDestination = postalAddressStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), postalAddressStatusChoice);
        
        if(workflowDestination != null || postalAddressStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownPostalAddressStatusChoice.name(), postalAddressStatusChoice);
        }
    }
    
    public TelephoneStatusChoicesBean getTelephoneStatusChoices(String defaultTelephoneStatusChoice, Language language, boolean allowNullChoice, ContactMechanism contactMechanism,
            PartyPK partyPK) {
        WorkflowControl workflowControl = getWorkflowControl();
        TelephoneStatusChoicesBean telephoneStatusChoicesBean = new TelephoneStatusChoicesBean();
        EntityInstance entityInstance = getEntityInstanceByBaseEntity(contactMechanism);
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(TelephoneStatusConstants.Workflow_TELEPHONE_STATUS,
                entityInstance);
        
        workflowControl.getWorkflowDestinationChoices(telephoneStatusChoicesBean, defaultTelephoneStatusChoice, language, allowNullChoice, workflowEntityStatus.getWorkflowStep(), partyPK);
        
        return telephoneStatusChoicesBean;
    }
    
    public void setTelephoneStatus(ExecutionErrorAccumulator eea,  ContactMechanism contactMechanism, String telephoneStatusChoice, PartyPK modifiedBy) {
        WorkflowControl workflowControl = getWorkflowControl();
        EntityInstance entityInstance = getEntityInstanceByBaseEntity(contactMechanism);
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(TelephoneStatusConstants.Workflow_TELEPHONE_STATUS,
                entityInstance);
        WorkflowDestination workflowDestination = telephoneStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), telephoneStatusChoice);
        
        if(workflowDestination != null || telephoneStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownTelephoneStatusChoice.name(), telephoneStatusChoice);
        }
    }
    
    public WebAddressStatusChoicesBean getWebAddressStatusChoices(String defaultWebAddressStatusChoice, Language language, boolean allowNullChoice, ContactMechanism contactMechanism,
            PartyPK partyPK) {
        WorkflowControl workflowControl = getWorkflowControl();
        WebAddressStatusChoicesBean webAddressStatusChoicesBean = new WebAddressStatusChoicesBean();
        EntityInstance entityInstance = getEntityInstanceByBaseEntity(contactMechanism);
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(WebAddressStatusConstants.Workflow_WEB_ADDRESS_STATUS,
                entityInstance);
        
        workflowControl.getWorkflowDestinationChoices(webAddressStatusChoicesBean, defaultWebAddressStatusChoice, language, allowNullChoice, workflowEntityStatus.getWorkflowStep(), partyPK);
        
        return webAddressStatusChoicesBean;
    }
    
    public void setWebAddressStatus(ExecutionErrorAccumulator eea,  ContactMechanism contactMechanism, String webAddressStatusChoice, PartyPK modifiedBy) {
        WorkflowControl workflowControl = getWorkflowControl();
        EntityInstance entityInstance = getEntityInstanceByBaseEntity(contactMechanism);
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(WebAddressStatusConstants.Workflow_WEB_ADDRESS_STATUS,
                entityInstance);
        WorkflowDestination workflowDestination = webAddressStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), webAddressStatusChoice);
        
        if(workflowDestination != null || webAddressStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownWebAddressStatusChoice.name(), webAddressStatusChoice);
        }
    }
    
    public void updateContactMechanismFromValue(ContactMechanismDetailValue contactMechanismDetailValue,  BasePK updatedBy) {
        if(contactMechanismDetailValue.hasBeenModified()) {
            ContactMechanism contactMechanism = ContactMechanismFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     contactMechanismDetailValue.getContactMechanismPK());
            ContactMechanismDetail contactMechanismDetail = contactMechanism.getActiveDetailForUpdate();
            
            contactMechanismDetail.setThruTime(session.START_TIME_LONG);
            contactMechanismDetail.store();
            
            ContactMechanismPK contactMechanismPK = contactMechanismDetail.getContactMechanismPK(); // Not updated
            String contactMechanismName = contactMechanismDetailValue.getContactMechanismName();
            ContactMechanismTypePK contactMechanismTypePK = contactMechanismDetail.getContactMechanismTypePK(); // Not updated
            Boolean allowSolicitation = contactMechanismDetailValue.getAllowSolicitation();
            
            contactMechanismDetail = ContactMechanismDetailFactory.getInstance().create(contactMechanismPK,
                    contactMechanismName, contactMechanismTypePK, allowSolicitation, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            contactMechanism.setActiveDetail(contactMechanismDetail);
            contactMechanism.setLastDetail(contactMechanismDetail);
            
            sendEventUsingNames(contactMechanismPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void deleteContactMechanism(ContactMechanism contactMechanism, BasePK deletedBy) {
        deletePartyContactMechanismsByContactMechanism(contactMechanism, deletedBy);
        deletePartyContactMechanismAliasesByContactMechanism(contactMechanism, deletedBy);
        deleteContactMechanismAliasesByContactMechanism(contactMechanism, deletedBy);
        
        ContactMechanismDetail contactMechanismDetail = contactMechanism.getLastDetailForUpdate();
        contactMechanismDetail.setThruTime(session.START_TIME_LONG);
        contactMechanism.setActiveDetail(null);
        contactMechanism.store();

        String contactMechanismTypeName = contactMechanismDetail.getContactMechanismType().getContactMechanismTypeName();
        if(contactMechanismTypeName.equals(ContactConstants.ContactMechanismType_EMAIL_ADDRESS)) {
            deleteContactEmailAddressByContactMechanism(contactMechanism, deletedBy);
        } else if(contactMechanismTypeName.equals(ContactConstants.ContactMechanismType_INET_4)) {
            deleteContactInet4AddressByContactMechanism(contactMechanism, deletedBy);
        } else if(contactMechanismTypeName.equals(ContactConstants.ContactMechanismType_INET_6)) {
            deleteContactInet6AddressByContactMechanism(contactMechanism, deletedBy);
        } else if(contactMechanismTypeName.equals(ContactConstants.ContactMechanismType_POSTAL_ADDRESS)) {
            deleteContactPostalAddressByContactMechanism(contactMechanism, deletedBy);
            deleteContactPostalAddressCorrectionByContactMechanism(contactMechanism, deletedBy);
        } else if(contactMechanismTypeName.equals(ContactConstants.ContactMechanismType_TELECOM_ADDRESS)) {
            deleteContactTelephoneByContactMechanism(contactMechanism, deletedBy);
        } else if(contactMechanismTypeName.equals(ContactConstants.ContactMechanismType_WEB_ADDRESS)) {
            deleteContactWebAddressByContactMechanism(contactMechanism, deletedBy);
        }
        
        sendEventUsingNames(contactMechanism.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Aliases
    // --------------------------------------------------------------------------------
    
    public ContactMechanismAlias createContactMechanismAlias(ContactMechanism contactMechanism,
            ContactMechanismAliasType contactMechanismAliasType, String alias, BasePK createdBy) {
        
        ContactMechanismAlias contactMechanismAlias = ContactMechanismAliasFactory.getInstance().create(contactMechanism,
                contactMechanismAliasType, alias, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(contactMechanism.getPrimaryKey(), EventTypes.MODIFY.name(), contactMechanismAlias.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return contactMechanismAlias;
    }
    
    private List<ContactMechanismAlias> getContactMechanismAliasesByContactMechanism(ContactMechanism contactMechanism,
            EntityPermission entityPermission) {
        List<ContactMechanismAlias> contactMechanismAliases = null;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contactmechanismaliases " +
                        "WHERE cmchal_cmch_contactmechanismid = ? AND cmchal_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contactmechanismaliases " +
                        "WHERE cmchal_cmch_contactmechanismid = ? AND cmchal_thrutime = ? " +
                        "FOR UPDATE";
            }

            PreparedStatement ps = ContactMechanismAliasFactory.getInstance().prepareStatement(query);

            ps.setLong(1, contactMechanism.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            contactMechanismAliases = ContactMechanismAliasFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return contactMechanismAliases;
    }

    public List<ContactMechanismAlias> getContactMechanismAliasesByContactMechanism(ContactMechanism contactMechanism) {
        return getContactMechanismAliasesByContactMechanism(contactMechanism, EntityPermission.READ_ONLY);
    }

    public List<ContactMechanismAlias> getContactMechanismAliasesByContactMechanismForUpdate(ContactMechanism contactMechanism) {
        return getContactMechanismAliasesByContactMechanism(contactMechanism, EntityPermission.READ_WRITE);
    }

    private List<ContactMechanismAlias> getContactMechanismAliasesByContactMechanismAliasType(ContactMechanismAliasType contactMechanismAliasType,
            EntityPermission entityPermission) {
        List<ContactMechanismAlias> contactMechanismAliases = null;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contactmechanismaliases " +
                        "WHERE cmchal_cmchaltyp_contactmechanismaliastypeid = ? AND cmchal_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contactmechanismaliases " +
                        "WHERE cmchal_cmchaltyp_contactmechanismaliastypeid = ? AND cmchal_thrutime = ? " +
                        "FOR UPDATE";
            }

            PreparedStatement ps = ContactMechanismAliasFactory.getInstance().prepareStatement(query);

            ps.setLong(1, contactMechanismAliasType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            contactMechanismAliases = ContactMechanismAliasFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return contactMechanismAliases;
    }

    public List<ContactMechanismAlias> getContactMechanismAliasesByContactMechanismAliasType(ContactMechanismAliasType contactMechanismAliasType) {
        return getContactMechanismAliasesByContactMechanismAliasType(contactMechanismAliasType, EntityPermission.READ_ONLY);
    }

    public List<ContactMechanismAlias> getContactMechanismAliasesByContactMechanismAliasTypeForUpdate(ContactMechanismAliasType contactMechanismAliasType) {
        return getContactMechanismAliasesByContactMechanismAliasType(contactMechanismAliasType, EntityPermission.READ_WRITE);
    }

    private ContactMechanismAlias getContactMechanismAliasByAlias(ContactMechanismAliasType contactMechanismAliasType,
            String alias, EntityPermission entityPermission) {
        ContactMechanismAlias contactMechanismAlias = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contactmechanismaliases " +
                        "WHERE cmchal_cmchaltyp_contactmechanismaliastypeid = ? AND cmchal_alias = ? AND cmchal_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contactmechanismaliases " +
                        "WHERE cmchal_cmchaltyp_contactmechanismaliastypeid = ? AND cmchal_alias = ? AND cmchal_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContactMechanismAliasFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contactMechanismAliasType.getPrimaryKey().getEntityId());
            ps.setString(2, alias);
            ps.setLong(3, Session.MAX_TIME);
            
            contactMechanismAlias = ContactMechanismAliasFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contactMechanismAlias;
    }
    
    public ContactMechanismAlias getContactMechanismAliasByAlias(ContactMechanismAliasType contactMechanismAliasType,
            String alias) {
        return getContactMechanismAliasByAlias(contactMechanismAliasType, alias, EntityPermission.READ_ONLY);
    }
    
    public ContactMechanismAlias getContactMechanismAliasByAliasForUpdate(ContactMechanismAliasType contactMechanismAliasType,
            String alias) {
        return getContactMechanismAliasByAlias(contactMechanismAliasType, alias, EntityPermission.READ_WRITE);
    }
    
    public ContactMechanismAliasTransfer getContactMechanismAliasTransfer(UserVisit userVisit, ContactMechanismAlias contactMechanismAlias) {
        return getContactTransferCaches(userVisit).getContactMechanismAliasTransferCache().getContactMechanismAliasTransfer(contactMechanismAlias);
    }
    
    public List<ContactMechanismAliasTransfer> getContactMechanismAliasTransfersByContactMechanism(UserVisit userVisit, ContactMechanism contactMechanism) {
        List<ContactMechanismAlias> entities = getContactMechanismAliasesByContactMechanism(contactMechanism);
        List<ContactMechanismAliasTransfer> transfers = new ArrayList<>(entities.size());
        ContactMechanismAliasTransferCache cache = getContactTransferCaches(userVisit).getContactMechanismAliasTransferCache();
        
        entities.stream().forEach((entity) -> {
            transfers.add(cache.getContactMechanismAliasTransfer(entity));
        });
        
        return transfers;
    }
    
    public void deleteContactMechanismAlias(ContactMechanismAlias contactMechanismAlias, BasePK deletedBy) {
        contactMechanismAlias.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(contactMechanismAlias.getContactMechanismPK(), EventTypes.MODIFY.name(), contactMechanismAlias.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteContactMechanismAliases(List<ContactMechanismAlias> contactMechanismAliases, BasePK deletedBy) {
        contactMechanismAliases.stream().forEach((contactMechanismAlias) -> {
            deleteContactMechanismAlias(contactMechanismAlias, deletedBy);
        });
    }

    public void deleteContactMechanismAliasesByContactMechanism(ContactMechanism contactMechanism, BasePK deletedBy) {
        deleteContactMechanismAliases(getContactMechanismAliasesByContactMechanismForUpdate(contactMechanism), deletedBy);
    }

    public void deleteContactMechanismAliasesByContactMechanismAliasType(ContactMechanismAliasType contactMechanismAliasType, BasePK deletedBy) {
        deleteContactMechanismAliases(getContactMechanismAliasesByContactMechanismAliasTypeForUpdate(contactMechanismAliasType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Contact Email Addresses
    // --------------------------------------------------------------------------------
    
    public ContactEmailAddress createContactEmailAddress(ContactMechanism contactMechanism, String emailAddress, BasePK createdBy) {
        ContactEmailAddress contactEmailAddress = ContactEmailAddressFactory.getInstance().create(contactMechanism,
                emailAddress, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(contactMechanism.getPrimaryKey(), EventTypes.MODIFY.name(), contactEmailAddress.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return contactEmailAddress;
    }
    
    private ContactEmailAddress getContactEmailAddress(ContactMechanism contactMechanism, EntityPermission entityPermission) {
        ContactEmailAddress contactEmailAddress = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contactemailaddresses " +
                        "WHERE ctea_cmch_contactmechanismid = ? AND ctea_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contactemailaddresses " +
                        "WHERE ctea_cmch_contactmechanismid = ? AND ctea_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContactEmailAddressFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contactMechanism.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contactEmailAddress = ContactEmailAddressFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contactEmailAddress;
    }
    
    public ContactEmailAddress getContactEmailAddress(ContactMechanism contactMechanism) {
        return getContactEmailAddress(contactMechanism, EntityPermission.READ_ONLY);
    }
    
    public ContactEmailAddress getContactEmailAddressForUpdate(ContactMechanism contactMechanism) {
        return getContactEmailAddress(contactMechanism, EntityPermission.READ_WRITE);
    }
    
    public ContactEmailAddressValue getContactEmailAddressValueForUpdate(ContactMechanism contactMechanism) {
        return getContactEmailAddressForUpdate(contactMechanism).getContactEmailAddressValue().clone();
    }
    
    public ContactEmailAddressTransfer getContactEmailAddressTransfer(UserVisit userVisit, ContactEmailAddress contactEmailAddress) {
        return getContactTransferCaches(userVisit).getContactEmailAddressTransferCache().getContactEmailAddressTransfer(contactEmailAddress);
    }
    
    public void updateContactEmailAddressFromValue(ContactEmailAddressValue contactEmailAddressValue, BasePK updatedBy) {
        if(contactEmailAddressValue.hasBeenModified()) {
            ContactEmailAddress contactEmailAddress = ContactEmailAddressFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contactEmailAddressValue.getPrimaryKey());
            
            contactEmailAddress.setThruTime(session.START_TIME_LONG);
            contactEmailAddress.store();
            
            ContactMechanismPK contactMechanismPK = contactEmailAddress.getContactMechanismPK(); // Not updated
            String emailAddress = contactEmailAddressValue.getEmailAddress();
            
            contactEmailAddress = ContactEmailAddressFactory.getInstance().create(contactMechanismPK, emailAddress,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(contactMechanismPK, EventTypes.MODIFY.name(), contactEmailAddress.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteContactEmailAddress(ContactEmailAddress contactEmailAddress, BasePK deletedBy) {
        contactEmailAddress.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(contactEmailAddress.getContactMechanismPK(), EventTypes.MODIFY.name(), contactEmailAddress.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteContactEmailAddressByContactMechanism(ContactMechanism contactMechanism, BasePK deletedBy) {
        deleteContactEmailAddress(getContactEmailAddressForUpdate(contactMechanism), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Inet 4 Addresses
    // --------------------------------------------------------------------------------
    
    public ContactInet4Address createContactInet4Address(ContactMechanism contactMechanism, Integer inet4Address, BasePK createdBy) {
        ContactInet4Address contactInet4Address = ContactInet4AddressFactory.getInstance().create(contactMechanism,
                inet4Address, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(contactMechanism.getPrimaryKey(), EventTypes.MODIFY.name(), contactInet4Address.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return contactInet4Address;
    }
    
    private ContactInet4Address getContactInet4Address(ContactMechanism contactMechanism, EntityPermission entityPermission) {
        ContactInet4Address contactInet4Address = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contactinet4addresses " +
                        "WHERE cti4a_cmch_contactmechanismid = ? AND cti4a_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contactinet4addresses " +
                        "WHERE cti4a_cmch_contactmechanismid = ? AND cti4a_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = session.prepareStatement(ContactInet4AddressFactory.class, query);
            
            ps.setLong(1, contactMechanism.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contactInet4Address = ContactInet4AddressFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contactInet4Address;
    }
    
    public ContactInet4Address getContactInet4Address(ContactMechanism contactMechanism) {
        return getContactInet4Address(contactMechanism, EntityPermission.READ_ONLY);
    }
    
    public ContactInet4Address getContactInet4AddressForUpdate(ContactMechanism contactMechanism) {
        return getContactInet4Address(contactMechanism, EntityPermission.READ_WRITE);
    }
    
    public ContactInet4AddressValue getContactInet4AddressValueForUpdate(ContactMechanism contactMechanism) {
        return getContactInet4AddressForUpdate(contactMechanism).getContactInet4AddressValue().clone();
    }
    
    public ContactInet4AddressTransfer getContactInet4AddressTransfer(UserVisit userVisit, ContactInet4Address contactInet4Address) {
        return getContactTransferCaches(userVisit).getContactInet4AddressTransferCache().getContactInet4AddressTransfer(contactInet4Address);
    }
    
    public void updateContactInet4AddressFromValue(ContactInet4AddressValue contactInet4AddressValue, BasePK updatedBy) {
        if(contactInet4AddressValue.hasBeenModified()) {
            ContactInet4Address contactInet4Address = ContactInet4AddressFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contactInet4AddressValue.getPrimaryKey());
            
            contactInet4Address.setThruTime(session.START_TIME_LONG);
            contactInet4Address.store();
            
            ContactMechanismPK contactMechanismPK = contactInet4Address.getContactMechanismPK(); // Not updated
            Integer inet4Address = contactInet4Address.getInet4Address();
            
            contactInet4Address = ContactInet4AddressFactory.getInstance().create(contactMechanismPK, inet4Address,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(contactMechanismPK, EventTypes.MODIFY.name(), contactInet4Address.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteContactInet4Address(ContactInet4Address contactInet4Address, BasePK deletedBy) {
        contactInet4Address.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(contactInet4Address.getContactMechanismPK(), EventTypes.MODIFY.name(), contactInet4Address.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteContactInet4AddressByContactMechanism(ContactMechanism contactMechanism, BasePK deletedBy) {
        deleteContactInet4Address(getContactInet4AddressForUpdate(contactMechanism), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Inet 6 Addresses
    // --------------------------------------------------------------------------------
    
    public ContactInet6Address createContactInet6Address(ContactMechanism contactMechanism, Long inet6AddressLow,
            Long inet6AddressHigh, BasePK createdBy) {
        ContactInet6Address contactInet6Address = ContactInet6AddressFactory.getInstance().create(contactMechanism,
                inet6AddressLow, inet6AddressHigh, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(contactMechanism.getPrimaryKey(), EventTypes.MODIFY.name(), contactInet6Address.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return contactInet6Address;
    }
    
    private ContactInet6Address getContactInet6Address(ContactMechanism contactMechanism, EntityPermission entityPermission) {
        ContactInet6Address contactInet6Address = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contactinet6addresses " +
                        "WHERE cti6a_cmch_contactmechanismid = ? AND cti6a_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contactinet6addresses " +
                        "WHERE cti6a_cmch_contactmechanismid = ? AND cti6a_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = session.prepareStatement(ContactInet6AddressFactory.class, query);
            
            ps.setLong(1, contactMechanism.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contactInet6Address = ContactInet6AddressFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contactInet6Address;
    }
    
    public ContactInet6Address getContactInet6Address(ContactMechanism contactMechanism) {
        return getContactInet6Address(contactMechanism, EntityPermission.READ_ONLY);
    }
    
    public ContactInet6Address getContactInet6AddressForUpdate(ContactMechanism contactMechanism) {
        return getContactInet6Address(contactMechanism, EntityPermission.READ_WRITE);
    }
    
    public ContactInet6AddressValue getContactInet6AddressValueForUpdate(ContactMechanism contactMechanism) {
        return getContactInet6AddressForUpdate(contactMechanism).getContactInet6AddressValue().clone();
    }
    
    public void updateContactInet6AddressFromValue(ContactInet6AddressValue contactInet6AddressValue, BasePK updatedBy) {
        if(contactInet6AddressValue.hasBeenModified()) {
            ContactInet6Address contactInet6Address = ContactInet6AddressFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contactInet6AddressValue.getPrimaryKey());
            
            contactInet6Address.setThruTime(session.START_TIME_LONG);
            contactInet6Address.store();
            
            ContactMechanismPK contactMechanismPK = contactInet6Address.getContactMechanismPK(); // Not updated
            Long inet6AddressLow = contactInet6Address.getInet6AddressLow();
            Long inet6AddressHigh = contactInet6Address.getInet6AddressHigh();
            
            contactInet6Address = ContactInet6AddressFactory.getInstance().create(contactMechanismPK, inet6AddressLow,
                    inet6AddressHigh, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(contactMechanismPK, EventTypes.MODIFY.name(), contactInet6Address.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteContactInet6Address(ContactInet6Address contactInet6Address, BasePK deletedBy) {
        contactInet6Address.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(contactInet6Address.getContactMechanismPK(), EventTypes.MODIFY.name(), contactInet6Address.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteContactInet6AddressByContactMechanism(ContactMechanism contactMechanism, BasePK deletedBy) {
        deleteContactInet6Address(getContactInet6AddressForUpdate(contactMechanism), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Postal Addresses
    // --------------------------------------------------------------------------------
    
    public ContactPostalAddress createContactPostalAddress(ContactMechanism contactMechanism, PersonalTitle personalTitle,
            String firstName, String firstNameSdx, String middleName, String middleNameSdx, String lastName, String lastNameSdx,
            NameSuffix nameSuffix, String companyName, String attention, String address1, String address2, String address3, String city,
            GeoCode cityGeoCode, GeoCode countyGeoCode, String state, GeoCode stateGeoCode, String postalCode,
            GeoCode postalCodeGeoCode, GeoCode countryGeoCode, Boolean isCommercial, BasePK createdBy) {
        ContactPostalAddress contactPostalAddress = ContactPostalAddressFactory.getInstance().create(contactMechanism,
                personalTitle, firstName, firstNameSdx, middleName, middleNameSdx, lastName, lastNameSdx, nameSuffix, companyName, attention,
                address1, address2, address3, city, cityGeoCode, countyGeoCode, state, stateGeoCode, postalCode, postalCodeGeoCode,
                countryGeoCode, isCommercial, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(contactMechanism.getPrimaryKey(), EventTypes.MODIFY.name(), contactPostalAddress.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return contactPostalAddress;
    }
    
    public long countContactPostalAddressesByCityGeoCode(GeoCode cityGeoCode) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM contactpostaladdresses " +
                "WHERE ctpa_citygeocodeid = ? AND ctpa_thrutime = ?",
                cityGeoCode, Session.MAX_TIME_LONG);
    }

    public long countContactPostalAddressesByCountyGeoCode(GeoCode countyGeoCode) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM contactpostaladdresses " +
                "WHERE ctpa_countygeocodeid = ? AND ctpa_thrutime = ?",
                countyGeoCode, Session.MAX_TIME_LONG);
    }

    public long countContactPostalAddressesByStateGeoCode(GeoCode stateGeoCode) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM contactpostaladdresses " +
                "WHERE ctpa_stategeocodeid = ? AND ctpa_thrutime = ?",
                stateGeoCode, Session.MAX_TIME_LONG);
    }

    public long countContactPostalAddressesByPostalCodeGeoCode(GeoCode postalCodeGeoCode) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM contactpostaladdresses " +
                "WHERE ctpa_postalcodegeocodeid = ? AND ctpa_thrutime = ?",
                postalCodeGeoCode, Session.MAX_TIME_LONG);
    }

    public long countContactPostalAddressesByCountryGeoCode(GeoCode countryGeoCode) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM contactpostaladdresses " +
                "WHERE ctpa_countrygeocodeid = ? AND ctpa_thrutime = ?",
                countryGeoCode, Session.MAX_TIME_LONG);
    }

    private ContactPostalAddress getContactPostalAddress(ContactMechanism contactMechanism, EntityPermission entityPermission) {
        ContactPostalAddress contactPostalAddress = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contactpostaladdresses " +
                        "WHERE ctpa_cmch_contactmechanismid = ? AND ctpa_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contactpostaladdresses " +
                        "WHERE ctpa_cmch_contactmechanismid = ? AND ctpa_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContactPostalAddressFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contactMechanism.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contactPostalAddress = ContactPostalAddressFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contactPostalAddress;
    }
    
    public ContactPostalAddress getContactPostalAddress(ContactMechanism contactMechanism) {
        return getContactPostalAddress(contactMechanism, EntityPermission.READ_ONLY);
    }
    
    public ContactPostalAddress getContactPostalAddressForUpdate(ContactMechanism contactMechanism) {
        return getContactPostalAddress(contactMechanism, EntityPermission.READ_WRITE);
    }
    
    public ContactPostalAddressValue getContactPostalAddressValueForUpdate(ContactMechanism contactMechanism) {
        return getContactPostalAddressForUpdate(contactMechanism).getContactPostalAddressValue().clone();
    }
    
    public ContactPostalAddressTransfer getContactPostalAddressTransfer(UserVisit userVisit, ContactPostalAddress contactPostalAddress) {
        return getContactTransferCaches(userVisit).getContactPostalAddressTransferCache().getContactPostalAddressTransfer(contactPostalAddress);
    }
    
    public void updateContactPostalAddressFromValue(ContactPostalAddressValue contactPostalAddressValue, BasePK updatedBy) {
        if(contactPostalAddressValue.hasBeenModified()) {
            ContactPostalAddress contactPostalAddress = ContactPostalAddressFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contactPostalAddressValue.getPrimaryKey());
            
            contactPostalAddress.setThruTime(session.START_TIME_LONG);
            contactPostalAddress.store();
            
            ContactMechanismPK contactMechanismPK = contactPostalAddress.getContactMechanismPK(); // Not updated
            PersonalTitlePK personalTitlePK = contactPostalAddressValue.getPersonalTitlePK();
            String firstName = contactPostalAddressValue.getFirstName();
            String firstNameSdx = contactPostalAddressValue.getFirstNameSdx();
            String middleName = contactPostalAddressValue.getMiddleName();
            String middleNameSdx = contactPostalAddressValue.getMiddleNameSdx();
            String lastName = contactPostalAddressValue.getLastName();
            String lastNameSdx = contactPostalAddressValue.getLastNameSdx();
            NameSuffixPK nameSuffixPK = contactPostalAddressValue.getNameSuffixPK();
            String companyName = contactPostalAddressValue.getCompanyName();
            String attention = contactPostalAddressValue.getAttention();
            String address1 = contactPostalAddressValue.getAddress1();
            String address2 = contactPostalAddressValue.getAddress2();
            String address3 = contactPostalAddressValue.getAddress3();
            String city = contactPostalAddressValue.getCity();
            GeoCodePK cityGeoCodePK = contactPostalAddressValue.getCityGeoCodePK();
            GeoCodePK countyGeoCodePK = contactPostalAddressValue.getCountyGeoCodePK();
            String state = contactPostalAddressValue.getState();
            GeoCodePK stateGeoCodePK = contactPostalAddressValue.getStateGeoCodePK();
            String postalCode = contactPostalAddressValue.getPostalCode();
            GeoCodePK postalCodeGeoCodePK = contactPostalAddressValue.getPostalCodeGeoCodePK();
            GeoCodePK countryGeoCodePK = contactPostalAddressValue.getCountryGeoCodePK();
            Boolean isCommercial = contactPostalAddressValue.getIsCommercial();
            
            contactPostalAddress = ContactPostalAddressFactory.getInstance().create(contactMechanismPK, personalTitlePK,
                    firstName, firstNameSdx, middleName, middleNameSdx, lastName, lastNameSdx, nameSuffixPK, companyName, attention, address1,
                    address2, address3, city, cityGeoCodePK, countyGeoCodePK, state, stateGeoCodePK, postalCode, postalCodeGeoCodePK,
                    countryGeoCodePK, isCommercial, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(contactMechanismPK, EventTypes.MODIFY.name(), contactPostalAddress.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteContactPostalAddress(ContactPostalAddress contactPostalAddress, BasePK deletedBy) {
        contactPostalAddress.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(contactPostalAddress.getContactMechanismPK(), EventTypes.MODIFY.name(), contactPostalAddress.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteContactPostalAddressByContactMechanism(ContactMechanism contactMechanism, BasePK deletedBy) {
        deleteContactPostalAddress(getContactPostalAddressForUpdate(contactMechanism), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Postal Address Corrections
    // --------------------------------------------------------------------------------
    
    public ContactPostalAddressCorrection createContactPostalAddressCorrection(ContactMechanism contactMechanism, String address1,
            String address2, String address3, String city, GeoCode cityGeoCode, GeoCode countyGeoCode, String state,
            GeoCode stateGeoCode, String postalCode, GeoCode postalCodeGeoCode, GeoCode countryGeoCode, BasePK createdBy) {
        ContactPostalAddressCorrection contactPostalAddressCorrection = ContactPostalAddressCorrectionFactory.getInstance().create(session,
                contactMechanism, address1, address2, address3, city, cityGeoCode, countyGeoCode, state, stateGeoCode, postalCode,
                postalCodeGeoCode, countryGeoCode, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(contactMechanism.getPrimaryKey(), EventTypes.MODIFY.name(), contactPostalAddressCorrection.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return contactPostalAddressCorrection;
    }
    
    public long countContactPostalAddressCorrectionsByCityGeoCode(GeoCode cityGeoCode) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM contactpostaladdresscorrections " +
                "WHERE ctpac_citygeocodeid = ? AND ctpac_thrutime = ?",
                cityGeoCode, Session.MAX_TIME_LONG);
    }

    public long countContactPostalAddressCorrectionsByCountyGeoCode(GeoCode countyGeoCode) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM contactpostaladdresscorrections " +
                "WHERE ctpac_countygeocodeid = ? AND ctpac_thrutime = ?",
                countyGeoCode, Session.MAX_TIME_LONG);
    }

    public long countContactPostalAddressCorrectionsByStateGeoCode(GeoCode stateGeoCode) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM contactpostaladdresscorrections " +
                "WHERE ctpac_stategeocodeid = ? AND ctpac_thrutime = ?",
                stateGeoCode, Session.MAX_TIME_LONG);
    }

    public long countContactPostalAddressCorrectionsByPostalCodeGeoCode(GeoCode postalCodeGeoCode) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM contactpostaladdresscorrections " +
                "WHERE ctpac_postalcodegeocodeid = ? AND ctpac_thrutime = ?",
                postalCodeGeoCode, Session.MAX_TIME_LONG);
    }

    public long countContactPostalAddressCorrectionsByCountryGeoCode(GeoCode countryGeoCode) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM contactpostaladdresscorrections " +
                "WHERE ctpac_countrygeocodeid = ? AND ctpac_thrutime = ?",
                countryGeoCode, Session.MAX_TIME_LONG);
    }

    private ContactPostalAddressCorrection getContactPostalAddressCorrection(ContactMechanism contactMechanism, EntityPermission entityPermission) {
        ContactPostalAddressCorrection contactPostalAddressCorrection = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contactpostaladdresscorrections " +
                        "WHERE ctpac_cmch_contactmechanismid = ? AND ctpac_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contactpostaladdresscorrections " +
                        "WHERE ctpac_cmch_contactmechanismid = ? AND ctpac_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContactPostalAddressCorrectionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contactMechanism.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contactPostalAddressCorrection = ContactPostalAddressCorrectionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contactPostalAddressCorrection;
    }
    
    public ContactPostalAddressCorrection getContactPostalAddressCorrection(ContactMechanism contactMechanism) {
        return getContactPostalAddressCorrection(contactMechanism, EntityPermission.READ_ONLY);
    }
    
    public ContactPostalAddressCorrection getContactPostalAddressCorrectionForUpdate(ContactMechanism contactMechanism) {
        return getContactPostalAddressCorrection(contactMechanism, EntityPermission.READ_WRITE);
    }
    
    public ContactPostalAddressCorrectionValue getContactPostalAddressCorrectionValueForUpdate(ContactMechanism contactMechanism) {
        return getContactPostalAddressCorrectionForUpdate(contactMechanism).getContactPostalAddressCorrectionValue().clone();
    }
    
    public void updateContactPostalAddressCorrectionFromValue(ContactPostalAddressCorrectionValue contactPostalAddressCorrectionValue, BasePK updatedBy) {
        if(contactPostalAddressCorrectionValue.hasBeenModified()) {
            ContactPostalAddressCorrection contactPostalAddressCorrection = ContactPostalAddressCorrectionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contactPostalAddressCorrectionValue.getPrimaryKey());
            
            contactPostalAddressCorrection.setThruTime(session.START_TIME_LONG);
            contactPostalAddressCorrection.store();
            
            ContactMechanismPK contactMechanismPK = contactPostalAddressCorrection.getContactMechanismPK(); // Not updated
            String address1 = contactPostalAddressCorrectionValue.getAddress1();
            String address2 = contactPostalAddressCorrectionValue.getAddress2();
            String address3 = contactPostalAddressCorrectionValue.getAddress3();
            String city = contactPostalAddressCorrectionValue.getCity();
            GeoCodePK cityGeoCodePK = contactPostalAddressCorrectionValue.getCityGeoCodePK();
            GeoCodePK countyGeoCodePK = contactPostalAddressCorrectionValue.getCountyGeoCodePK();
            String state = contactPostalAddressCorrectionValue.getState();
            GeoCodePK stateGeoCodePK = contactPostalAddressCorrectionValue.getStateGeoCodePK();
            String postalCode = contactPostalAddressCorrectionValue.getPostalCode();
            GeoCodePK postalCodeGeoCodePK = contactPostalAddressCorrectionValue.getPostalCodeGeoCodePK();
            GeoCodePK countryGeoCodePK = contactPostalAddressCorrectionValue.getCountryGeoCodePK();
            
            contactPostalAddressCorrection = ContactPostalAddressCorrectionFactory.getInstance().create(contactMechanismPK,
                    address1, address2, address3, city, cityGeoCodePK, countyGeoCodePK, state, stateGeoCodePK, postalCode,
                    postalCodeGeoCodePK, countryGeoCodePK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(contactMechanismPK, EventTypes.MODIFY.name(), contactPostalAddressCorrection.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteContactPostalAddressCorrection(ContactPostalAddressCorrection contactPostalAddressCorrection, BasePK deletedBy) {
        contactPostalAddressCorrection.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(contactPostalAddressCorrection.getContactMechanismPK(), EventTypes.MODIFY.name(), contactPostalAddressCorrection.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteContactPostalAddressCorrectionByContactMechanism(ContactMechanism contactMechanism, BasePK deletedBy) {
        ContactPostalAddressCorrection contactPostalAddressCorrection = getContactPostalAddressCorrectionForUpdate(contactMechanism);
        
        if(contactPostalAddressCorrection != null) {
            deleteContactPostalAddressCorrection(contactPostalAddressCorrection, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Telephones
    // --------------------------------------------------------------------------------
    
    public ContactTelephone createContactTelephone(ContactMechanism contactMechanism, GeoCode countryGeoCode, String areaCode,
            String telephoneNumber, String telephoneExtension, BasePK createdBy) {
        ContactTelephone contactTelephone = ContactTelephoneFactory.getInstance().create(contactMechanism, countryGeoCode,
                areaCode, telephoneNumber, telephoneExtension, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(contactMechanism.getPrimaryKey(), EventTypes.MODIFY.name(), contactTelephone.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return contactTelephone;
    }
    
    public long countContactTelephonesByCountryGeoCode(GeoCode countryGeoCode) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM contacttelephones " +
                "WHERE cttp_countrygeocodeid = ? AND cttp_thrutime = ?",
                countryGeoCode, Session.MAX_TIME_LONG);
    }

    private ContactTelephone getContactTelephone(ContactMechanism contactMechanism, EntityPermission entityPermission) {
        ContactTelephone contactTelephone = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contacttelephones " +
                        "WHERE cttp_cmch_contactmechanismid = ? AND cttp_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contacttelephones " +
                        "WHERE cttp_cmch_contactmechanismid = ? AND cttp_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContactTelephoneFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contactMechanism.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contactTelephone = ContactTelephoneFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contactTelephone;
    }
    
    public ContactTelephone getContactTelephone(ContactMechanism contactMechanism) {
        return getContactTelephone(contactMechanism, EntityPermission.READ_ONLY);
    }
    
    public ContactTelephone getContactTelephoneForUpdate(ContactMechanism contactMechanism) {
        return getContactTelephone(contactMechanism, EntityPermission.READ_WRITE);
    }
    
    public ContactTelephoneValue getContactTelephoneValueForUpdate(ContactMechanism contactMechanism) {
        return getContactTelephoneForUpdate(contactMechanism).getContactTelephoneValue().clone();
    }
    
    public ContactTelephoneTransfer getContactTelephoneTransfer(UserVisit userVisit, ContactTelephone contactTelephone) {
        return getContactTransferCaches(userVisit).getContactTelephoneTransferCache().getContactTelephoneTransfer(contactTelephone);
    }
    
    public void updateContactTelephoneFromValue(ContactTelephoneValue contactTelephoneValue, BasePK updatedBy) {
        if(contactTelephoneValue.hasBeenModified()) {
            ContactTelephone contactTelephone = ContactTelephoneFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contactTelephoneValue.getPrimaryKey());
            
            contactTelephone.setThruTime(session.START_TIME_LONG);
            contactTelephone.store();
            
            ContactMechanismPK contactMechanismPK = contactTelephone.getContactMechanismPK(); // Not updated
            GeoCodePK countryGeoCodePK = contactTelephoneValue.getCountryGeoCodePK();
            String areaCode = contactTelephoneValue.getAreaCode();
            String telephoneNumber = contactTelephoneValue.getTelephoneNumber();
            String telephoneExtension = contactTelephoneValue.getTelephoneExtension();
            
            contactTelephone = ContactTelephoneFactory.getInstance().create(contactMechanismPK, countryGeoCodePK,
                    areaCode, telephoneNumber, telephoneExtension, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(contactMechanismPK, EventTypes.MODIFY.name(), contactTelephone.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteContactTelephone(ContactTelephone contactTelephone, BasePK deletedBy) {
        contactTelephone.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(contactTelephone.getContactMechanismPK(), EventTypes.MODIFY.name(), contactTelephone.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteContactTelephoneByContactMechanism(ContactMechanism contactMechanism, BasePK deletedBy) {
        deleteContactTelephone(getContactTelephoneForUpdate(contactMechanism), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Web Addresses
    // --------------------------------------------------------------------------------
    
    public ContactWebAddress createContactWebAddress(ContactMechanism contactMechanism, String url, BasePK createdBy) {
        ContactWebAddress contactWebAddress = ContactWebAddressFactory.getInstance().create(contactMechanism, url,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(contactMechanism.getPrimaryKey(), EventTypes.MODIFY.name(), contactWebAddress.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return contactWebAddress;
    }
    
    private ContactWebAddress getContactWebAddress(ContactMechanism contactMechanism, EntityPermission entityPermission) {
        ContactWebAddress contactWebAddress = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contactwebaddresses " +
                        "WHERE ctwa_cmch_contactmechanismid = ? AND ctwa_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contactwebaddresses " +
                        "WHERE ctwa_cmch_contactmechanismid = ? AND ctwa_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContactWebAddressFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contactMechanism.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contactWebAddress = ContactWebAddressFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contactWebAddress;
    }
    
    public ContactWebAddress getContactWebAddress(ContactMechanism contactMechanism) {
        return getContactWebAddress(contactMechanism, EntityPermission.READ_ONLY);
    }
    
    public ContactWebAddress getContactWebAddressForUpdate(ContactMechanism contactMechanism) {
        return getContactWebAddress(contactMechanism, EntityPermission.READ_WRITE);
    }
    
    public ContactWebAddressValue getContactWebAddressValueForUpdate(ContactMechanism contactMechanism) {
        return getContactWebAddressForUpdate(contactMechanism).getContactWebAddressValue().clone();
    }
    
    public ContactWebAddressTransfer getContactWebAddressTransfer(UserVisit userVisit, ContactWebAddress contactWebAddress) {
        return getContactTransferCaches(userVisit).getContactWebAddressTransferCache().getContactWebAddressTransfer(contactWebAddress);
    }
    
    public void updateContactWebAddressFromValue(ContactWebAddressValue contactWebAddressValue, BasePK updatedBy) {
        if(contactWebAddressValue.hasBeenModified()) {
            ContactWebAddress contactWebAddress = ContactWebAddressFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contactWebAddressValue.getPrimaryKey());
            
            contactWebAddress.setThruTime(session.START_TIME_LONG);
            contactWebAddress.store();
            
            ContactMechanismPK contactMechanismPK = contactWebAddress.getContactMechanismPK(); // Not updated
            String url = contactWebAddressValue.getUrl();
            
            contactWebAddress = ContactWebAddressFactory.getInstance().create(contactMechanismPK, url, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEventUsingNames(contactMechanismPK, EventTypes.MODIFY.name(), contactWebAddress.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteContactWebAddress(ContactWebAddress contactWebAddress, BasePK deletedBy) {
        contactWebAddress.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(contactWebAddress.getContactMechanismPK(), EventTypes.MODIFY.name(), contactWebAddress.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteContactWebAddressByContactMechanism(ContactMechanism contactMechanism, BasePK deletedBy) {
        deleteContactWebAddress(getContactWebAddressForUpdate(contactMechanism), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Contact Mechanisms
    // --------------------------------------------------------------------------------
    
    public PartyContactMechanism createPartyContactMechanism(Party party, ContactMechanism contactMechanism, String description,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        PartyContactMechanism defaultPartyContactMechanism = getDefaultPartyContactMechanism(party);
        boolean defaultFound = defaultPartyContactMechanism != null;
        
        if(defaultFound && isDefault) {
            PartyContactMechanismDetailValue defaultPartyContactMechanismDetailValue = getDefaultPartyContactMechanismDetailValueForUpdate(party);
            
            defaultPartyContactMechanismDetailValue.setIsDefault(Boolean.FALSE);
            updatePartyContactMechanismFromValue(defaultPartyContactMechanismDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        PartyContactMechanism partyContactMechanism = PartyContactMechanismFactory.getInstance().create();
        PartyContactMechanismDetail partyContactMechanismDetail = PartyContactMechanismDetailFactory.getInstance().create(session,
                partyContactMechanism, party, contactMechanism, description, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        partyContactMechanism = PartyContactMechanismFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, partyContactMechanism.getPrimaryKey());
        partyContactMechanism.setActiveDetail(partyContactMechanismDetail);
        partyContactMechanism.setLastDetail(partyContactMechanismDetail);
        partyContactMechanism.store();
        
        sendEventUsingNames(party.getPrimaryKey(), EventTypes.MODIFY.name(), partyContactMechanism.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return partyContactMechanism;
    }
    
    private PartyContactMechanism getDefaultPartyContactMechanism(Party party, EntityPermission entityPermission) {
        PartyContactMechanism partyContactMechanism = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partycontactmechanisms, partycontactmechanismdetails " +
                        "WHERE pcm_activedetailid = pcmdt_partycontactmechanismdetailid AND pcmdt_par_partyid = ? AND pcmdt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partycontactmechanisms, partycontactmechanismdetails " +
                        "WHERE pcm_activedetailid = pcmdt_partycontactmechanismdetailid AND pcmdt_par_partyid = ? AND pcmdt_isdefault = 1 " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyContactMechanismFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            
            partyContactMechanism = PartyContactMechanismFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyContactMechanism;
    }
    
    public PartyContactMechanism getDefaultPartyContactMechanism(Party party) {
        return getDefaultPartyContactMechanism(party, EntityPermission.READ_ONLY);
    }
    
    public PartyContactMechanism getDefaultPartyContactMechanismForUpdate(Party party) {
        return getDefaultPartyContactMechanism(party, EntityPermission.READ_WRITE);
    }
    
    public PartyContactMechanismDetailValue getDefaultPartyContactMechanismDetailValueForUpdate(Party party) {
        return getDefaultPartyContactMechanismForUpdate(party).getLastDetailForUpdate().getPartyContactMechanismDetailValue().clone();
    }
    
    private PartyContactMechanism getPartyContactMechanism(Party party, ContactMechanism contactMechanism,
            EntityPermission entityPermission) {
        PartyContactMechanism partyContactMechanism = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partycontactmechanisms, partycontactmechanismdetails " +
                        "WHERE pcm_activedetailid = pcmdt_partycontactmechanismdetailid AND pcmdt_par_partyid = ? AND pcmdt_cmch_contactmechanismid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partycontactmechanisms, partycontactmechanismdetails " +
                        "WHERE pcm_activedetailid = pcmdt_partycontactmechanismdetailid AND pcmdt_par_partyid = ? AND pcmdt_cmch_contactmechanismid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyContactMechanismFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, contactMechanism.getPrimaryKey().getEntityId());
            
            partyContactMechanism = PartyContactMechanismFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyContactMechanism;
    }
    
    public PartyContactMechanism getPartyContactMechanism(Party party, ContactMechanism contactMechanism) {
        return getPartyContactMechanism(party, contactMechanism, EntityPermission.READ_ONLY);
    }
    
    public PartyContactMechanism getPartyContactMechanismForUpdate(Party party, ContactMechanism contactMechanism) {
        return getPartyContactMechanism(party, contactMechanism, EntityPermission.READ_WRITE);
    }
    
    public PartyContactMechanismDetailValue getPartyContactMechanismDetailValueForUpdate(PartyContactMechanism partyContactMechanism) {
        return partyContactMechanism == null? null: partyContactMechanism.getLastDetailForUpdate().getPartyContactMechanismDetailValue().clone();
    }
    
    public PartyContactMechanismDetailValue getPartyContactMechanismDetailValueForUpdate(Party party, ContactMechanism contactMechanism) {
        return getPartyContactMechanismDetailValueForUpdate(getPartyContactMechanismForUpdate(party, contactMechanism));
    }
    
    private List<PartyContactMechanism> getPartyContactMechanismsByParty(Party party, EntityPermission entityPermission) {
        List<PartyContactMechanism> partyContactMechanism = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partycontactmechanisms, partycontactmechanismdetails, contactmechanisms, contactmechanismdetails, contactmechanismtypes " +
                        "WHERE pcm_activedetailid = pcmdt_partycontactmechanismdetailid AND pcmdt_par_partyid = ? " +
                        "AND pcmdt_cmch_contactmechanismid = cmch_contactmechanismid " +
                        "AND cmch_activedetailid = cmchdt_contactmechanismdetailid AND cmchdt_cmt_contactmechanismtypeid = cmt_contactmechanismtypeid " +
                        "ORDER BY cmt_sortorder, cmt_contactmechanismtypename, cmchdt_contactmechanismname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partycontactmechanisms, partycontactmechanismdetails " +
                        "WHERE pcm_activedetailid = pcmdt_partycontactmechanismdetailid AND pcmdt_par_partyid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyContactMechanismFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            
            partyContactMechanism = PartyContactMechanismFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyContactMechanism;
    }
    
    public List<PartyContactMechanism> getPartyContactMechanismsByParty(Party party) {
        return getPartyContactMechanismsByParty(party, EntityPermission.READ_ONLY);
    }
    
    public List<PartyContactMechanism> getPartyContactMechanismsByPartyForUpdate(Party party) {
        return getPartyContactMechanismsByParty(party, EntityPermission.READ_WRITE);
    }
    
    private List<PartyContactMechanism> getPartyContactMechanismsByPartyAndContactMechanismType(Party party,
            ContactMechanismType contactMechanismType, EntityPermission entityPermission) {
        List<PartyContactMechanism> partyContactMechanism = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partycontactmechanisms, partycontactmechanismdetails, contactmechanisms, contactmechanismdetails " +
                        "WHERE pcm_activedetailid = pcmdt_partycontactmechanismdetailid AND pcmdt_par_partyid = ? " +
                        "AND pcmdt_cmch_contactmechanismid = cmch_contactmechanismid AND cmch_activedetailid = cmchdt_contactmechanismdetailid " +
                        "AND cmchdt_cmt_contactmechanismtypeid = ? " +
                        "ORDER BY cmchdt_contactmechanismname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partycontactmechanisms, partycontactmechanismdetails " +
                        "WHERE pcmdt_activedetailid = pcmdt_partycontactmechanismdetailid AND pcmdt_par_partyid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyContactMechanismFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, contactMechanismType.getPrimaryKey().getEntityId());
            
            partyContactMechanism = PartyContactMechanismFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyContactMechanism;
    }
    
    public List<PartyContactMechanism> getPartyContactMechanismsByPartyAndContactMechanismType(Party party,
            ContactMechanismType contactMechanismType) {
        return getPartyContactMechanismsByPartyAndContactMechanismType(party, contactMechanismType, EntityPermission.READ_ONLY);
    }
    
    public List<PartyContactMechanism> getPartyContactMechanismsByPartyAndContactMechanismTypeForUpdate(Party party,
            ContactMechanismType contactMechanismType) {
        return getPartyContactMechanismsByPartyAndContactMechanismType(party, contactMechanismType, EntityPermission.READ_WRITE);
    }
    
    private List<PartyContactMechanism> getPartyContactMechanismsByContactMechanism(ContactMechanism contactMechanism,
            EntityPermission entityPermission) {
        List<PartyContactMechanism> partyContactMechanism = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partycontactmechanisms, partycontactmechanismdetails, parties, partydetails, partytypes " +
                        "WHERE pcm_activedetailid = pcmdt_partycontactmechanismdetailid AND pcmdt_cmch_contactmechanismid = ? " +
                        "AND pcmdt_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                        "AND pardt_ptyp_partytypeid = ptyp_partytypeid " +
                        "ORDER BY pcmdt_sortorder, pardt_partyname, ptyp_sortorder, ptyp_partytypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partycontactmechanisms, partycontactmechanismdetails " +
                        "WHERE pcm_activedetailid = pcmdt_partycontactmechanismdetailid AND pcmdt_cmch_contactmechanismid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyContactMechanismFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contactMechanism.getPrimaryKey().getEntityId());
            
            partyContactMechanism = PartyContactMechanismFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyContactMechanism;
    }
    
    public List<PartyContactMechanism> getPartyContactMechanismsByContactMechanism(ContactMechanism contactMechanism) {
        return getPartyContactMechanismsByContactMechanism(contactMechanism, EntityPermission.READ_ONLY);
    }
    
    public List<PartyContactMechanism> getPartyContactMechanismsByContactMechanismForUpdate(ContactMechanism contactMechanism) {
        return getPartyContactMechanismsByContactMechanism(contactMechanism, EntityPermission.READ_WRITE);
    }
    
    public PartyContactMechanism getPartyContactMechanismByContactMechanismName(ExecutionErrorAccumulator eea, Party party, String contactMechanismName) {
        PartyContactMechanism partyContactMechanism = null;
        ContactMechanism contactMechanism = getContactMechanismByName(contactMechanismName);
        
        if(contactMechanism != null) {
            partyContactMechanism = getPartyContactMechanism(party, contactMechanism);
            
            if(partyContactMechanism == null) {
                eea.addExecutionError(ExecutionErrors.UnknownPartyContactMechanism.name(), party.getLastDetail().getPartyName(), contactMechanismName);
            }
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownContactMechanismName.name(), contactMechanismName);
        }
        
        return partyContactMechanism;
    }
    
    public ContactMechanismChoicesBean getContactMechanismChoicesByParty(Party party, String defaultContactMechanismChoice, Language language,
            boolean allowNullChoice) {
        List<PartyContactMechanism> partyContactMechanisms = getPartyContactMechanismsByParty(party);
        int size = partyContactMechanisms.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");
        }

        for(PartyContactMechanism partyContactMechanism : partyContactMechanisms) {
            PartyContactMechanismDetail partyContactMechanismDetail = partyContactMechanism.getLastDetail();
            String label = partyContactMechanismDetail.getDescription();
            String value = partyContactMechanismDetail.getContactMechanism().getLastDetail().getContactMechanismName();

            labels.add(label == null ? value : label);
            values.add(value);

            // Default choice isn't handled well, there may be no isDefault set since we're narrowing the complete list by type.
            boolean usingDefaultChoice = defaultContactMechanismChoice == null ? false : defaultContactMechanismChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && partyContactMechanismDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ContactMechanismChoicesBean(labels, values, defaultValue);
    }

    public ContactMechanismChoicesBean getContactMechanismChoicesByPartyAndContactMechanismType(Party party, ContactMechanismType contactMechanismType,
            String defaultContactMechanismChoice, Language language, boolean allowNullChoice) {
        List<PartyContactMechanism> partyContactMechanisms = getPartyContactMechanismsByPartyAndContactMechanismType(party, contactMechanismType);
        int size = partyContactMechanisms.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");
        }

        for(PartyContactMechanism partyContactMechanism : partyContactMechanisms) {
            PartyContactMechanismDetail partyContactMechanismDetail = partyContactMechanism.getLastDetail();
            String label = partyContactMechanismDetail.getDescription();
            String value = partyContactMechanismDetail.getContactMechanism().getLastDetail().getContactMechanismName();

            labels.add(label == null ? value : label);
            values.add(value);

            // Default choice isn't handled well, there may be no isDefault set since we're narrowing the complete list by type.
            boolean usingDefaultChoice = defaultContactMechanismChoice == null ? false : defaultContactMechanismChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && partyContactMechanismDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ContactMechanismChoicesBean(labels, values, defaultValue);
    }
    
    public PartyContactMechanismTransfer getPartyContactMechanismTransfer(UserVisit userVisit, PartyContactMechanism partyContactMechanism) {
        return getContactTransferCaches(userVisit).getPartyContactMechanismTransferCache().getPartyContactMechanismTransfer(partyContactMechanism);
    }
    
    public List<PartyContactMechanismTransfer> getPartyContactMechanismTransfersByParty(UserVisit userVisit, Party party) {
        List<PartyContactMechanism> entities = getPartyContactMechanismsByParty(party);
        List<PartyContactMechanismTransfer> transfers = new ArrayList<>(entities.size());
        PartyContactMechanismTransferCache cache = getContactTransferCaches(userVisit).getPartyContactMechanismTransferCache();
        
        entities.stream().forEach((entity) -> {
            transfers.add(cache.getPartyContactMechanismTransfer(entity));
        });
        
        return transfers;
    }
    
    private void updatePartyContactMechanismFromValue(PartyContactMechanismDetailValue partyContactMechanismDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(partyContactMechanismDetailValue.hasBeenModified()) {
            PartyContactMechanism partyContactMechanism = PartyContactMechanismFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyContactMechanismDetailValue.getPartyContactMechanismPK());
            PartyContactMechanismDetail partyContactMechanismDetail = partyContactMechanism.getActiveDetailForUpdate();
            
            partyContactMechanismDetail.setThruTime(session.START_TIME_LONG);
            partyContactMechanismDetail.store();
            
            PartyContactMechanismPK partyContactMechanismPK = partyContactMechanismDetail.getPartyContactMechanismPK();
            Party party = partyContactMechanismDetail.getParty(); // Not updated
            PartyPK partyPK = party.getPrimaryKey();
            ContactMechanismPK contactMechanismPK = partyContactMechanismDetail.getContactMechanismPK(); // Not updated
            String description = partyContactMechanismDetailValue.getDescription();
            Boolean isDefault = partyContactMechanismDetailValue.getIsDefault();
            Integer sortOrder = partyContactMechanismDetailValue.getSortOrder();
            
            if(checkDefault) {
                PartyContactMechanism defaultPartyContactMechanism = getDefaultPartyContactMechanism(party);
                boolean defaultFound = defaultPartyContactMechanism != null && !defaultPartyContactMechanism.equals(partyContactMechanism);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    PartyContactMechanismDetailValue defaultPartyContactMechanismDetailValue = getDefaultPartyContactMechanismDetailValueForUpdate(party);
                    
                    defaultPartyContactMechanismDetailValue.setIsDefault(Boolean.FALSE);
                    updatePartyContactMechanismFromValue(defaultPartyContactMechanismDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            partyContactMechanismDetail = PartyContactMechanismDetailFactory.getInstance().create(partyContactMechanismPK,
                    partyPK, contactMechanismPK, description, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            partyContactMechanism.setActiveDetail(partyContactMechanismDetail);
            partyContactMechanism.setLastDetail(partyContactMechanismDetail);
            
            sendEventUsingNames(partyPK, EventTypes.MODIFY.name(), partyContactMechanism.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void updatePartyContactMechanismFromValue(PartyContactMechanismDetailValue partyContactMechanismDetailValue, BasePK updatedBy) {
        updatePartyContactMechanismFromValue(partyContactMechanismDetailValue, true, updatedBy);
    }
    
    public void deletePartyContactMechanism(PartyContactMechanism partyContactMechanism, BasePK deletedBy) {
        AssociateControl associateControl = (AssociateControl)Session.getModelController(AssociateControl.class);
        CommunicationControl communicationControl = (CommunicationControl)Session.getModelController(CommunicationControl.class);
        InvoiceControl invoiceControl = (InvoiceControl)Session.getModelController(InvoiceControl.class);
        LetterControl letterControl = (LetterControl)Session.getModelController(LetterControl.class);
        OrderControl orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        PaymentControl paymentControl = (PaymentControl)Session.getModelController(PaymentControl.class);
        ShipmentControl shipmentControl = (ShipmentControl)Session.getModelController(ShipmentControl.class);
        
        deletePartyContactMechanismPurposesByPartyContactMechanism(partyContactMechanism, deletedBy);
        deletePartyContactMechanismRelationshipsByPartyContactMechanism(partyContactMechanism, deletedBy);
        
        associateControl.deleteAssociatePartyContactMechanismsByPartyContactMechanism(partyContactMechanism, deletedBy);
        communicationControl.deleteCommunicationEventsByPartyContactMechanism(partyContactMechanism, deletedBy);
        invoiceControl.deleteInvoiceRolesByPartyContactMechanism(partyContactMechanism, deletedBy);
        letterControl.deleteLetterSourcesByPartyContactMechanism(partyContactMechanism, deletedBy);
        orderControl.deleteOrderShipmentGroupsByPartyContactMechanism(partyContactMechanism, deletedBy);
        paymentControl.deletePartyPaymentMethodCreditCardsByPartyContactMechanism(partyContactMechanism, deletedBy);
        paymentControl.deleteBillingAccountRolesByPartyContactMechanism(partyContactMechanism, deletedBy);
        shipmentControl.deleteShipmentsByPartyContactMechanism(partyContactMechanism, deletedBy);
        
        PartyContactMechanismDetail partyContactMechanismDetail = partyContactMechanism.getLastDetailForUpdate();
        partyContactMechanismDetail.setThruTime(session.START_TIME_LONG);
        partyContactMechanism.setActiveDetail(null);
        partyContactMechanism.store();
        
        // Check for default, and pick one if necessary
        Party party = partyContactMechanismDetail.getParty();
        PartyContactMechanism defaultPartyContactMechanism = getDefaultPartyContactMechanism(party);
        if(defaultPartyContactMechanism == null) {
            List<PartyContactMechanism> partyContactMechanisms = getPartyContactMechanismsByPartyForUpdate(party);
            
            if(!partyContactMechanisms.isEmpty()) {
                Iterator<PartyContactMechanism> iter = partyContactMechanisms.iterator();
                if(iter.hasNext()) {
                    defaultPartyContactMechanism = iter.next();
                }
                PartyContactMechanismDetailValue partyContactMechanismDetailValue = defaultPartyContactMechanism.getLastDetailForUpdate().getPartyContactMechanismDetailValue().clone();
                
                partyContactMechanismDetailValue.setIsDefault(Boolean.TRUE);
                updatePartyContactMechanismFromValue(partyContactMechanismDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(partyContactMechanismDetail.getPartyPK(), EventTypes.MODIFY.name(), partyContactMechanism.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deletePartyContactMechanismsByParty(Party party, BasePK deletedBy) {
        List<PartyContactMechanism> partyContactMechanisms = getPartyContactMechanismsByPartyForUpdate(party);
        
        partyContactMechanisms.stream().forEach((partyContactMechanism) -> {
            deletePartyContactMechanism(partyContactMechanism, deletedBy);
        });
    }
    
    public void deletePartyContactMechanismsByContactMechanism(ContactMechanism contactMechanism, BasePK deletedBy) {
        List<PartyContactMechanism> partyContactMechanisms = getPartyContactMechanismsByContactMechanismForUpdate(contactMechanism);
        
        partyContactMechanisms.stream().forEach((partyContactMechanism) -> {
            deletePartyContactMechanism(partyContactMechanism, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Party Contact Mechanism Utilities
    // --------------------------------------------------------------------------------
    
    public PartyContactMechanism getPartyContactMechanismByInet4Address(Party party, Integer inet4Address) {
        PartyContactMechanism partyContactMechanism = null;
        
        try {
            PreparedStatement ps = PartyContactMechanismFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM partycontactmechanisms, partycontactmechanismdetails, contactmechanisms, contactmechanismdetails, contactinet4addresses " +
                    "WHERE pcm_activedetailid = pcmdt_partycontactmechanismdetailid AND pcmdt_par_partyid = ? " +
                    "AND pcmdt_cmch_contactmechanismid = cmch_contactmechanismid AND cmch_activedetailid = cmchdt_contactmechanismdetailid " +
                    "AND cmchdt_cmch_contactmechanismid = cti4a_cmch_contactmechanismid AND cti4a_thrutime = ? " +
                    "AND cti4a_inet4address = ?");
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            ps.setInt(3, inet4Address);
            
            partyContactMechanism = PartyContactMechanismFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyContactMechanism;
    }
    
    public PartyContactMechanism getPartyContactMechanismByEmailAddress(Party party, String emailAddress) {
        PartyContactMechanism partyContactMechanism = null;
        
        try {
            PreparedStatement ps = PartyContactMechanismFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM partycontactmechanisms, partycontactmechanismdetails, contactmechanisms, contactmechanismdetails, contactemailaddresses " +
                    "WHERE pcm_activedetailid = pcmdt_partycontactmechanismdetailid AND pcmdt_par_partyid = ? " +
                    "AND pcmdt_cmch_contactmechanismid = cmch_contactmechanismid AND cmch_activedetailid = cmchdt_contactmechanismdetailid " +
                    "AND cmchdt_cmch_contactmechanismid = ctea_cmch_contactmechanismid AND ctea_thrutime = ? " +
                    "AND ctea_emailaddress = ?");
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            ps.setString(3, emailAddress);
            
            partyContactMechanism = PartyContactMechanismFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyContactMechanism;
    }
    
    public List<PartyContactMechanism> getPartyContactMechanismsByEmailAddress(String emailAddress) {
        List<PartyContactMechanism> partyContactMechanisms = null;
        
        try {
            PreparedStatement ps = PartyContactMechanismFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM partycontactmechanisms, partycontactmechanismdetails, contactmechanisms, contactmechanismdetails, contactemailaddresses " +
                    "WHERE pcm_activedetailid = pcmdt_partycontactmechanismdetailid " +
                    "AND pcmdt_cmch_contactmechanismid = cmch_contactmechanismid AND cmch_activedetailid = cmchdt_contactmechanismdetailid " +
                    "AND cmchdt_cmch_contactmechanismid = ctea_cmch_contactmechanismid AND ctea_thrutime = ? " +
                    "AND ctea_emailaddress = ?");
            // TODO: 'ORDER BY' the Party's created time
            
            ps.setLong(1, Session.MAX_TIME);
            ps.setString(2, emailAddress);
            
            partyContactMechanisms = PartyContactMechanismFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyContactMechanisms;
    }
    
    // --------------------------------------------------------------------------------
    //   Party Contact Mechanism Aliases
    // --------------------------------------------------------------------------------
    
    public PartyContactMechanismAlias createPartyContactMechanismAlias(Party party, ContactMechanism contactMechanism,
            ContactMechanismAliasType contactMechanismAliasType, String alias, BasePK createdBy) {
        
        PartyContactMechanismAlias partyContactMechanismAlias = PartyContactMechanismAliasFactory.getInstance().create(session,
                party, contactMechanism, contactMechanismAliasType, alias, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(party.getPrimaryKey(), EventTypes.MODIFY.name(), partyContactMechanismAlias.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return partyContactMechanismAlias;
    }
    
    private List<PartyContactMechanismAlias> getPartyContactMechanismAliasesByParty(Party party,
            EntityPermission entityPermission) {
        List<PartyContactMechanismAlias> partyContactMechanismAliases = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partycontactmechanismaliases " +
                        "WHERE pcmchal_cmch_contactmechanismid = ? AND pcmchal_thrutime = ?"; // TODO: needs ORDER BY
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partycontactmechanismaliases " +
                        "WHERE pcmchal_cmch_contactmechanismid = ? AND pcmchal_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyContactMechanismAliasFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyContactMechanismAliases = PartyContactMechanismAliasFactory.getInstance().getEntitiesFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyContactMechanismAliases;
    }
    
    public List<PartyContactMechanismAlias> getPartyContactMechanismAliasesByParty(Party party) {
        return getPartyContactMechanismAliasesByParty(party, EntityPermission.READ_ONLY);
    }
    
    public List<PartyContactMechanismAlias> getPartyContactMechanismAliasByPartyForUpdate(Party party) {
        return getPartyContactMechanismAliasesByParty(party, EntityPermission.READ_WRITE);
    }
    
    private List<PartyContactMechanismAlias> getPartyContactMechanismAliasesByContactMechanism(ContactMechanism contactMechanism,
            EntityPermission entityPermission) {
        List<PartyContactMechanismAlias> partyContactMechanismAliases = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partycontactmechanismaliases, contactmechanismaliastypes, contactmechanismaliastypedetails " +
                        "WHERE pcmchal_cmch_contactmechanismid = ? AND pcmchal_thrutime = ? " +
                        "AND pcmchal_cmchaltyp_contactmechanismaliastypeid = cmchaltyp_contactmechanismaliastypeid AND cmchaltyp_lastdetailid = cmchaltypdt_contactmechanismaliastypedetailid " +
                        "ORDER BY cmchaltypdt_sortorder, cmchaltypdt_contactmechanismaliastypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partycontactmechanismaliases " +
                        "WHERE pcmchal_cmch_contactmechanismid = ? AND pcmchal_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyContactMechanismAliasFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contactMechanism.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyContactMechanismAliases = PartyContactMechanismAliasFactory.getInstance().getEntitiesFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyContactMechanismAliases;
    }
    
    public List<PartyContactMechanismAlias> getPartyContactMechanismAliasesByContactMechanism(ContactMechanism contactMechanism) {
        return getPartyContactMechanismAliasesByContactMechanism(contactMechanism, EntityPermission.READ_ONLY);
    }
    
    public List<PartyContactMechanismAlias> getPartyContactMechanismAliasesByContactMechanismForUpdate(ContactMechanism contactMechanism) {
        return getPartyContactMechanismAliasesByContactMechanism(contactMechanism, EntityPermission.READ_WRITE);
    }
    
    private PartyContactMechanismAlias getPartyContactMechanismAliasByAlias(Party party,
            ContactMechanismAliasType contactMechanismAliasType, String alias, EntityPermission entityPermission) {
        PartyContactMechanismAlias partyContactMechanismAlias = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partycontactmechanismaliases " +
                        "WHERE pcmchal_par_partyid = ? AND pcmchal_cmchaltyp_contactmechanismaliastypeid = ? " +
                        "AND pcmchal_alias = ? AND pcmchal_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partycontactmechanismaliases " +
                        "WHERE pcmchal_par_partyid = ? AND pcmchal_cmchaltyp_contactmechanismaliastypeid = ? " +
                        "AND pcmchal_alias = ? AND pcmchal_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyContactMechanismAliasFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, contactMechanismAliasType.getPrimaryKey().getEntityId());
            ps.setString(3, alias);
            ps.setLong(4, Session.MAX_TIME);
            
            partyContactMechanismAlias = PartyContactMechanismAliasFactory.getInstance().getEntityFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyContactMechanismAlias;
    }
    
    public PartyContactMechanismAlias getPartyContactMechanismAliasByAlias(Party party,
            ContactMechanismAliasType contactMechanismAliasType, String alias) {
        return getPartyContactMechanismAliasByAlias(party, contactMechanismAliasType, alias, EntityPermission.READ_ONLY);
    }
    
    public PartyContactMechanismAlias getPartyContactMechanismAliasByAliasForUpdate(Party party,
            ContactMechanismAliasType contactMechanismAliasType, String alias) {
        return getPartyContactMechanismAliasByAlias(party, contactMechanismAliasType, alias, EntityPermission.READ_WRITE);
    }
    
    public PartyContactMechanismAliasTransfer getPartyContactMechanismAliasTransfer(UserVisit userVisit, PartyContactMechanismAlias partyContactMechanismAlias) {
        return getContactTransferCaches(userVisit).getPartyContactMechanismAliasTransferCache().getPartyContactMechanismAliasTransfer(partyContactMechanismAlias);
    }
    
    public void deletePartyContactMechanismAlias(PartyContactMechanismAlias partyContactMechanismAlias, BasePK deletedBy) {
        partyContactMechanismAlias.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(partyContactMechanismAlias.getPartyPK(), EventTypes.MODIFY.name(), partyContactMechanismAlias.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deletePartyContactMechanismAliasesByParty(Party party, BasePK deletedBy) {
        List<PartyContactMechanismAlias> partyContactMechanismAliases = getPartyContactMechanismAliasesByParty(party);
        
        partyContactMechanismAliases.stream().forEach((partyContactMechanismAlias) -> {
            deletePartyContactMechanismAlias(partyContactMechanismAlias, deletedBy);
        });
    }
    
    public void deletePartyContactMechanismAliasesByContactMechanism(ContactMechanism contactMechanism, BasePK deletedBy) {
        List<PartyContactMechanismAlias> partyContactMechanismAliases = getPartyContactMechanismAliasesByContactMechanismForUpdate(contactMechanism);
        
        partyContactMechanismAliases.stream().forEach((partyContactMechanismAlias) -> {
            deletePartyContactMechanismAlias(partyContactMechanismAlias, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Party Contact Mechanism Purposes
    // --------------------------------------------------------------------------------
    
    public PartyContactMechanismPurpose createPartyContactMechanismPurpose(PartyContactMechanism partyContactMechanism,
            ContactMechanismPurpose contactMechanismPurpose, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        PartyContactMechanismPurpose defaultPartyContactMechanismPurpose = getDefaultPartyContactMechanismPurpose(partyContactMechanism, contactMechanismPurpose);
        boolean defaultFound = defaultPartyContactMechanismPurpose != null;
        
        if(defaultFound && isDefault) {
            PartyContactMechanismPurposeDetailValue defaultPartyContactMechanismPurposeDetailValue = getDefaultPartyContactMechanismPurposeDetailValueForUpdate(partyContactMechanism,
                    contactMechanismPurpose);
            
            defaultPartyContactMechanismPurposeDetailValue.setIsDefault(Boolean.FALSE);
            updatePartyContactMechanismPurposeFromValue(defaultPartyContactMechanismPurposeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        PartyContactMechanismPurpose partyContactMechanismPurpose = PartyContactMechanismPurposeFactory.getInstance().create();
        PartyContactMechanismPurposeDetail partyContactMechanismPurposeDetail = PartyContactMechanismPurposeDetailFactory.getInstance().create(session,
                partyContactMechanismPurpose, partyContactMechanism, contactMechanismPurpose, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        partyContactMechanismPurpose = PartyContactMechanismPurposeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, partyContactMechanismPurpose.getPrimaryKey());
        partyContactMechanismPurpose.setActiveDetail(partyContactMechanismPurposeDetail);
        partyContactMechanismPurpose.setLastDetail(partyContactMechanismPurposeDetail);
        partyContactMechanismPurpose.store();
        
        sendEventUsingNames(partyContactMechanism.getLastDetail().getPartyPK(), EventTypes.MODIFY.name(), partyContactMechanismPurpose.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return partyContactMechanismPurpose;
    }
    
    private PartyContactMechanismPurpose getDefaultPartyContactMechanismPurpose(PartyPK partyPK, ContactMechanismPurpose contactMechanismPurpose, EntityPermission entityPermission) {
        PartyContactMechanismPurpose partyContactMechanismPurpose = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partycontactmechanisms, partycontactmechanismdetails, partycontactmechanismpurposes, partycontactmechanismpurposedetails " +
                        "WHERE pcm_activedetailid = pcmdt_partycontactmechanismdetailid AND pcmdt_par_partyid = ? " +
                        "AND pcmp_activedetailid = pcmpdt_partycontactmechanismpurposedetailid " +
                        "AND pcm_partycontactmechanismid = pcmpdt_pcm_partycontactmechanismid AND pcmpdt_cmpr_contactmechanismpurposeid = ? AND pcmpdt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partycontactmechanisms, partycontactmechanismdetails, partycontactmechanismpurposes, partycontactmechanismpurposedetails " +
                        "WHERE pcm_activedetailid = pcmdt_partycontactmechanismdetailid AND pcmdt_par_partyid = ? " +
                        "AND pcmp_activedetailid = pcmpdt_partycontactmechanismpurposedetailid " +
                        "AND pcm_partycontactmechanismid = pcmpdt_pcm_partycontactmechanismid AND pcmpdt_cmpr_contactmechanismpurposeid = ? AND pcmpdt_isdefault = 1 " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyContactMechanismPurposeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, partyPK.getEntityId());
            ps.setLong(2, contactMechanismPurpose.getPrimaryKey().getEntityId());
            
            partyContactMechanismPurpose = PartyContactMechanismPurposeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyContactMechanismPurpose;
    }
    
    public PartyContactMechanismPurpose getDefaultPartyContactMechanismPurpose(Party party, ContactMechanismPurpose contactMechanismPurpose) {
        return getDefaultPartyContactMechanismPurpose(party.getPrimaryKey(), contactMechanismPurpose, EntityPermission.READ_ONLY);
    }
    
    public PartyContactMechanismPurpose getDefaultPartyContactMechanismPurposeUsingNames(Party party, String contactMechanismPurposeName) {
        ContactMechanismPurpose contactMechanismPurpose = getContactMechanismPurposeByName(contactMechanismPurposeName);
        
        return getDefaultPartyContactMechanismPurpose(party.getPrimaryKey(), contactMechanismPurpose, EntityPermission.READ_ONLY);
    }
    
    public PartyContactMechanismPurpose getDefaultPartyContactMechanismPurposeForUpdate(Party party, ContactMechanismPurpose contactMechanismPurpose) {
        return getDefaultPartyContactMechanismPurpose(party.getPrimaryKey(), contactMechanismPurpose, EntityPermission.READ_WRITE);
    }
    
    public PartyContactMechanismPurposeDetailValue getDefaultPartyContactMechanismPurposeDetailValueForUpdate(Party party, ContactMechanismPurpose contactMechanismPurpose) {
        return getDefaultPartyContactMechanismPurposeForUpdate(party, contactMechanismPurpose).getLastDetailForUpdate().getPartyContactMechanismPurposeDetailValue().clone();
    }
    
    public PartyContactMechanismPurpose getDefaultPartyContactMechanismPurpose(PartyContactMechanism partyContactMechanism, ContactMechanismPurpose contactMechanismPurpose) {
        return getDefaultPartyContactMechanismPurpose(partyContactMechanism.getLastDetail().getPartyPK(), contactMechanismPurpose, EntityPermission.READ_ONLY);
    }
    
    public PartyContactMechanismPurpose getDefaultPartyContactMechanismPurposeForUpdate(PartyContactMechanism partyContactMechanism, ContactMechanismPurpose contactMechanismPurpose) {
        return getDefaultPartyContactMechanismPurpose(partyContactMechanism.getLastDetail().getPartyPK(), contactMechanismPurpose, EntityPermission.READ_WRITE);
    }
    
    public PartyContactMechanismPurposeDetailValue getDefaultPartyContactMechanismPurposeDetailValueForUpdate(PartyContactMechanism partyContactMechanism,
            ContactMechanismPurpose contactMechanismPurpose) {
        return getDefaultPartyContactMechanismPurposeForUpdate(partyContactMechanism, contactMechanismPurpose).getLastDetailForUpdate().getPartyContactMechanismPurposeDetailValue().clone();
    }
    
    private PartyContactMechanismPurpose getPartyContactMechanismPurpose(PartyContactMechanism partyContactMechanism,
            ContactMechanismPurpose contactMechanismPurpose, EntityPermission entityPermission) {
        PartyContactMechanismPurpose partyContactMechanismPurpose = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partycontactmechanismpurposes, partycontactmechanismpurposedetails " +
                        "WHERE pcmp_activedetailid = pcmpdt_partycontactmechanismpurposedetailid AND pcmpdt_pcm_partycontactmechanismid = ? AND pcmpdt_cmpr_contactmechanismpurposeid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partycontactmechanismpurposes, partycontactmechanismpurposedetails " +
                        "WHERE pcmp_activedetailid = pcmpdt_partycontactmechanismpurposedetailid AND pcmpdt_pcm_partycontactmechanismid = ? AND pcmpdt_cmpr_contactmechanismpurposeid = ?  " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyContactMechanismPurposeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, partyContactMechanism.getPrimaryKey().getEntityId());
            ps.setLong(2, contactMechanismPurpose.getPrimaryKey().getEntityId());
            
            partyContactMechanismPurpose = PartyContactMechanismPurposeFactory.getInstance().getEntityFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyContactMechanismPurpose;
    }
    
    public PartyContactMechanismPurpose getPartyContactMechanismPurpose(PartyContactMechanism partyContactMechanism,
            ContactMechanismPurpose contactMechanismPurpose) {
        return getPartyContactMechanismPurpose(partyContactMechanism, contactMechanismPurpose, EntityPermission.READ_ONLY);
    }
    
    public PartyContactMechanismPurpose getPartyContactMechanismPurposeForUpdate(PartyContactMechanism partyContactMechanism,
            ContactMechanismPurpose contactMechanismPurpose) {
        return getPartyContactMechanismPurpose(partyContactMechanism, contactMechanismPurpose, EntityPermission.READ_WRITE);
    }
    
    public PartyContactMechanismPurposeDetailValue getPartyContactMechanismPurposeDetailValueForUpdate(PartyContactMechanism partyContactMechanism,
            ContactMechanismPurpose contactMechanismPurpose) {
        return getPartyContactMechanismPurposeForUpdate(partyContactMechanism, contactMechanismPurpose).getLastDetailForUpdate().getPartyContactMechanismPurposeDetailValue().clone();
    }
    
    private List<PartyContactMechanismPurpose> getPartyContactMechanismPurposesByContactMechanismPurposeAndParty(ContactMechanismPurpose contactMechanismPurpose,
            Party party, EntityPermission entityPermission) {
        List<PartyContactMechanismPurpose> partyContactMechanismPurposes = null;
        
        try {
            PreparedStatement ps = PartyContactMechanismPurposeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM partycontactmechanisms, partycontactmechanismdetails, partycontactmechanismpurposes, partycontactmechanismpurposedetails " +
                    "WHERE pcm_activedetailid = pcmdt_partycontactmechanismdetailid AND pcmdt_par_partyid = ? " +
                    "AND pcmp_activedetailid = pcmpdt_partycontactmechanismpurposedetailid " +
                    "AND pcm_partycontactmechanismid = pcmpdt_pcm_partycontactmechanismid AND pcmpdt_cmpr_contactmechanismpurposeid = ?");
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, contactMechanismPurpose.getPrimaryKey().getEntityId());
            
            partyContactMechanismPurposes = PartyContactMechanismPurposeFactory.getInstance().getEntitiesFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyContactMechanismPurposes;
    }
    
    public List<PartyContactMechanismPurpose> getPartyContactMechanismPurposesByContactMechanismPurposeAndParty(ContactMechanismPurpose contactMechanismPurpose,
            Party party) {
        return getPartyContactMechanismPurposesByContactMechanismPurposeAndParty(contactMechanismPurpose, party, EntityPermission.READ_ONLY);
    }
    
    public List<PartyContactMechanismPurpose> getPartyContactMechanismPurposesByContactMechanismPurposeAndPartyForUpdate(ContactMechanismPurpose contactMechanismPurpose,
            Party party) {
        return getPartyContactMechanismPurposesByContactMechanismPurposeAndParty(contactMechanismPurpose, party, EntityPermission.READ_WRITE);
    }
    
    public List<PartyContactMechanismPurpose> getPartyContactMechanismPurposesForUpdate(PartyContactMechanism partyContactMechanism,
            ContactMechanismPurpose contactMechanismPurpose) {
        return getPartyContactMechanismPurposesByContactMechanismPurposeAndPartyForUpdate(contactMechanismPurpose, partyContactMechanism.getLastDetail().getParty());
    }
    
    private List<PartyContactMechanismPurpose> getPartyContactMechanismPurposesByPartyContactMechanism(PartyContactMechanism partyContactMechanism,
            EntityPermission entityPermission) {
        List<PartyContactMechanismPurpose> partyContactMechanismAliases = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partycontactmechanismpurposes, partycontactmechanismpurposedetails, contactmechanismpurposes " +
                        "WHERE pcmp_activedetailid = pcmpdt_partycontactmechanismpurposedetailid AND pcmpdt_pcm_partycontactmechanismid = ? " +
                        "AND pcmpdt_cmpr_contactmechanismpurposeid = cmpr_contactmechanismpurposeid " +
                        "ORDER BY cmpr_contactmechanismpurposename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partycontactmechanismpurposes, partycontactmechanismpurposedetails, contactmechanismpurposes " +
                        "WHERE pcmp_activedetailid = pcmpdt_partycontactmechanismpurposedetailid AND pcmpdt_pcm_partycontactmechanismid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyContactMechanismPurposeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, partyContactMechanism.getPrimaryKey().getEntityId());
            
            partyContactMechanismAliases = PartyContactMechanismPurposeFactory.getInstance().getEntitiesFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyContactMechanismAliases;
    }
    
    public List<PartyContactMechanismPurpose> getPartyContactMechanismPurposesByPartyContactMechanism(PartyContactMechanism partyContactMechanism) {
        return getPartyContactMechanismPurposesByPartyContactMechanism(partyContactMechanism, EntityPermission.READ_ONLY);
    }
    
    public List<PartyContactMechanismPurpose> getPartyContactMechanismPurposesByPartyContactMechanismForUpdate(PartyContactMechanism partyContactMechanism) {
        return getPartyContactMechanismPurposesByPartyContactMechanism(partyContactMechanism, EntityPermission.READ_WRITE);
    }
    
    public PartyContactMechanismPurposeTransfer getPartyContactMechanismPurposeTransfer(UserVisit userVisit, PartyContactMechanismPurpose partyContactMechanismPurpose) {
        return getContactTransferCaches(userVisit).getPartyContactMechanismPurposeTransferCache().getPartyContactMechanismPurposeTransfer(partyContactMechanismPurpose);
    }
    
    public List<PartyContactMechanismPurposeTransfer> getPartyContactMechanismPurposeTransfersByPartyContactMechanism(UserVisit userVisit,
            PartyContactMechanism partyContactMechanism) {
        List<PartyContactMechanismPurpose> entities = getPartyContactMechanismPurposesByPartyContactMechanism(partyContactMechanism);
        List<PartyContactMechanismPurposeTransfer> transfers = new ArrayList<>(entities.size());
        PartyContactMechanismPurposeTransferCache cache = getContactTransferCaches(userVisit).getPartyContactMechanismPurposeTransferCache();
        
        entities.stream().forEach((entity) -> {
            transfers.add(cache.getPartyContactMechanismPurposeTransfer(entity));
        });
        
        return transfers;
    }
    
    private void updatePartyContactMechanismPurposeFromValue(PartyContactMechanismPurposeDetailValue partyContactMechanismPurposeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(partyContactMechanismPurposeDetailValue.hasBeenModified()) {
            PartyContactMechanismPurpose partyContactMechanismPurpose = PartyContactMechanismPurposeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyContactMechanismPurposeDetailValue.getPartyContactMechanismPurposePK());
            PartyContactMechanismPurposeDetail partyContactMechanismPurposeDetail = partyContactMechanismPurpose.getActiveDetailForUpdate();
            
            partyContactMechanismPurposeDetail.setThruTime(session.START_TIME_LONG);
            partyContactMechanismPurposeDetail.store();
            
            PartyContactMechanismPurposePK partyContactMechanismPurposePK = partyContactMechanismPurposeDetail.getPartyContactMechanismPurposePK();
            PartyContactMechanism partyContactMechanism = partyContactMechanismPurposeDetail.getPartyContactMechanism();
            ContactMechanismPurpose contactMechanismPurpose = partyContactMechanismPurposeDetail.getContactMechanismPurpose();
            Boolean isDefault = partyContactMechanismPurposeDetailValue.getIsDefault();
            Integer sortOrder = partyContactMechanismPurposeDetailValue.getSortOrder();
            
            if(checkDefault) {
                PartyContactMechanismPurpose defaultPartyContactMechanismPurpose = getDefaultPartyContactMechanismPurpose(partyContactMechanism, contactMechanismPurpose);
                boolean defaultFound = defaultPartyContactMechanismPurpose != null && !defaultPartyContactMechanismPurpose.equals(partyContactMechanismPurpose);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    PartyContactMechanismPurposeDetailValue defaultPartyContactMechanismPurposeDetailValue = getDefaultPartyContactMechanismPurposeDetailValueForUpdate(partyContactMechanism, contactMechanismPurpose);
                    
                    defaultPartyContactMechanismPurposeDetailValue.setIsDefault(Boolean.FALSE);
                    updatePartyContactMechanismPurposeFromValue(defaultPartyContactMechanismPurposeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            partyContactMechanismPurposeDetail = PartyContactMechanismPurposeDetailFactory.getInstance().create(session,
                    partyContactMechanismPurposePK, partyContactMechanism.getPrimaryKey(), contactMechanismPurpose.getPrimaryKey(),
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            partyContactMechanismPurpose.setActiveDetail(partyContactMechanismPurposeDetail);
            partyContactMechanismPurpose.setLastDetail(partyContactMechanismPurposeDetail);
            
            sendEventUsingNames(partyContactMechanism.getLastDetail().getPartyPK(), EventTypes.MODIFY.name(), partyContactMechanismPurpose.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void updatePartyContactMechanismPurposeFromValue(PartyContactMechanismPurposeDetailValue partyContactMechanismPurposeDetailValue, BasePK updatedBy) {
        updatePartyContactMechanismPurposeFromValue(partyContactMechanismPurposeDetailValue, true, updatedBy);
    }
    
    public void deletePartyContactMechanismPurpose(PartyContactMechanismPurpose partyContactMechanismPurpose, BasePK deletedBy) {
        PartyContactMechanismPurposeDetail partyContactMechanismPurposeDetail = partyContactMechanismPurpose.getLastDetailForUpdate();
        partyContactMechanismPurposeDetail.setThruTime(session.START_TIME_LONG);
        partyContactMechanismPurpose.setActiveDetail(null);
        partyContactMechanismPurpose.store();
        
        // Check for default, and pick one if necessary
        PartyContactMechanism partyContactMechanism = partyContactMechanismPurposeDetail.getPartyContactMechanism();
        ContactMechanismPurpose contactMechanismPurpose = partyContactMechanismPurposeDetail.getContactMechanismPurpose();
        PartyContactMechanismPurpose defaultPartyContactMechanismPurpose = getDefaultPartyContactMechanismPurpose(partyContactMechanism, contactMechanismPurpose);
        if(defaultPartyContactMechanismPurpose == null) {
            List<PartyContactMechanismPurpose> partyContactMechanismPurposes = getPartyContactMechanismPurposesForUpdate(partyContactMechanism, contactMechanismPurpose);
            
            if(!partyContactMechanismPurposes.isEmpty()) {
                Iterator<PartyContactMechanismPurpose> iter = partyContactMechanismPurposes.iterator();
                if(iter.hasNext()) {
                    defaultPartyContactMechanismPurpose = iter.next();
                }
                PartyContactMechanismPurposeDetailValue partyContactMechanismPurposeDetailValue = defaultPartyContactMechanismPurpose.getLastDetailForUpdate().getPartyContactMechanismPurposeDetailValue().clone();
                
                partyContactMechanismPurposeDetailValue.setIsDefault(Boolean.TRUE);
                updatePartyContactMechanismPurposeFromValue(partyContactMechanismPurposeDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(partyContactMechanism.getLastDetail().getPartyPK(), EventTypes.MODIFY.name(), partyContactMechanismPurpose.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deletePartyContactMechanismPurposesByPartyContactMechanism(PartyContactMechanism partyContactMechanism,
            BasePK deletedBy) {
        List<PartyContactMechanismPurpose> partyContactMechanismPurposes = getPartyContactMechanismPurposesByPartyContactMechanismForUpdate(partyContactMechanism);
        
        partyContactMechanismPurposes.stream().forEach((partyContactMechanismPurpose) -> {
            deletePartyContactMechanismPurpose(partyContactMechanismPurpose, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Party Contact Mechanism Relationships
    // --------------------------------------------------------------------------------
    
    public PartyContactMechanismRelationship createPartyContactMechanismRelationship(PartyContactMechanism fromPartyContactMechanism,
            PartyContactMechanism toPartyContactMechanism, BasePK createdBy) {
        PartyContactMechanismRelationship partyContactMechanismRelationship = PartyContactMechanismRelationshipFactory.getInstance().create(fromPartyContactMechanism,
                toPartyContactMechanism, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(fromPartyContactMechanism.getPrimaryKey(), EventTypes.MODIFY.name(), partyContactMechanismRelationship.getPrimaryKey(), null, createdBy);
        
        return partyContactMechanismRelationship;
    }
    
    public boolean partyContactMechanismRelationshipExists(PartyContactMechanism fromPartyContactMechanism, PartyContactMechanism toPartyContactMechanism) {
        return session.queryForInteger(
                "SELECT COUNT(*) "
                + "FROM partycontactmechanismrelationships "
                + "WHERE pcmr_frompartycontactmechanismid = ? AND pcmr_topartycontactmechanismid = ? AND pcmr_thrutime = ?",
                fromPartyContactMechanism, toPartyContactMechanism, Session.MAX_TIME_LONG) == 1;
    }

    public long countPartyContactMechanismRelationshipsByFromPartyContactMechanism(PartyContactMechanism fromPartyContactMechanism) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM partycontactmechanismrelationships "
                + "WHERE pcmr_frompartycontactmechanismid = ? AND pcmr_thrutime = ?",
                fromPartyContactMechanism, Session.MAX_TIME_LONG);
    }

    public long countPartyContactMechanismRelationshipsByToPartyContactMechanism(PartyContactMechanism toPartyContactMechanism) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM partycontactmechanismrelationships "
                + "WHERE pcmr_topartycontactmechanismid = ? AND pcmr_thrutime = ?",
                toPartyContactMechanism, Session.MAX_TIME_LONG);
    }

    private static final Map<EntityPermission, String> getPartyContactMechanismRelationshipQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM partycontactmechanismrelationships "
                + "WHERE pcmr_frompartycontactmechanismid = ? AND pcmr_topartycontactmechanismid = ? AND pcmr_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM partycontactmechanismrelationships "
                + "WHERE pcmr_frompartycontactmechanismid = ? AND pcmr_topartycontactmechanismid = ? AND pcmr_thrutime = ? "
                + "FOR UPDATE");
        getPartyContactMechanismRelationshipQueries = Collections.unmodifiableMap(queryMap);
    }

    public PartyContactMechanismRelationship getPartyContactMechanismRelationship(PartyContactMechanism fromPartyContactMechanism,
            PartyContactMechanism toPartyContactMechanism, EntityPermission entityPermission) {
        return PartyContactMechanismRelationshipFactory.getInstance().getEntityFromQuery(entityPermission, getPartyContactMechanismRelationshipQueries,
                fromPartyContactMechanism, toPartyContactMechanism, Session.MAX_TIME_LONG);
    }
    
    public PartyContactMechanismRelationship getPartyContactMechanismRelationship(PartyContactMechanism fromPartyContactMechanism,
            PartyContactMechanism toPartyContactMechanism) {
        return getPartyContactMechanismRelationship(fromPartyContactMechanism, toPartyContactMechanism, EntityPermission.READ_ONLY);
    }
    
    public PartyContactMechanismRelationship getPartyContactMechanismRelationshipForUpdate(PartyContactMechanism fromPartyContactMechanism,
            PartyContactMechanism toPartyContactMechanism) {
        return getPartyContactMechanismRelationship(fromPartyContactMechanism, toPartyContactMechanism, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getPartyContactMechanismRelationshipsByFromPartyContactMechanismQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM partycontactmechanismrelationships, partycontactmechanisms, partycontactmechanismdetails, contactmechanisms, contactmechanismdetails "
                + "WHERE pcmr_frompartycontactmechanismid = ? AND pcmr_thrutime = ? "
                + "AND pcmr_topartycontactmechanismid = pcm_partycontactmechanismid AND pcm_lastdetailid = pcmdt_partycontactmechanismdetailid "
                + "AND pcmdt_cmch_contactmechanismid = cmch_contactmechanismid AND cmch_lastdetailid = cmchdt_contactmechanismdetailid "
                + "ORDER BY pcmdt_sortorder, cmchdt_contactmechanismname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM partycontactmechanismrelationships "
                + "WHERE pcmr_frompartycontactmechanismid = ? AND pcmr_thrutime = ? "
                + "FOR UPDATE");
        getPartyContactMechanismRelationshipsByFromPartyContactMechanismQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<PartyContactMechanismRelationship> getPartyContactMechanismRelationshipsByFromPartyContactMechanism(PartyContactMechanism fromPartyContactMechanism, EntityPermission entityPermission) {
        return PartyContactMechanismRelationshipFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyContactMechanismRelationshipsByFromPartyContactMechanismQueries,
                fromPartyContactMechanism, Session.MAX_TIME_LONG);
    }

    public List<PartyContactMechanismRelationship> getPartyContactMechanismRelationshipsByFromPartyContactMechanism(PartyContactMechanism fromPartyContactMechanism) {
        return getPartyContactMechanismRelationshipsByFromPartyContactMechanism(fromPartyContactMechanism, EntityPermission.READ_ONLY);
    }

    public List<PartyContactMechanismRelationship> getPartyContactMechanismRelationshipsByFromPartyContactMechanismForUpdate(PartyContactMechanism fromPartyContactMechanism) {
        return getPartyContactMechanismRelationshipsByFromPartyContactMechanism(fromPartyContactMechanism, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPartyContactMechanismRelationshipsByToPartyContactMechanismQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM partycontactmechanismrelationships, partycontactmechanisms, partycontactmechanismdetails, contactmechanisms, contactmechanismdetails "
                + "WHERE pcmr_topartycontactmechanismid = ? AND pcmr_thrutime = ? "
                + "AND pcmr_frompartycontactmechanismid = pcm_partycontactmechanismid AND pcm_lastdetailid = pcmdt_partycontactmechanismdetailid "
                + "AND pcmdt_cmch_contactmechanismid = cmch_contactmechanismid AND cmch_lastdetailid = cmchdt_contactmechanismdetailid "
                + "ORDER BY pcmdt_sortorder, cmchdt_contactmechanismname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM partycontactmechanismrelationships "
                + "WHERE pcmr_topartycontactmechanismid = ? AND pcmr_thrutime = ? "
                + "FOR UPDATE");
        getPartyContactMechanismRelationshipsByToPartyContactMechanismQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<PartyContactMechanismRelationship> getPartyContactMechanismRelationshipsByToPartyContactMechanism(PartyContactMechanism toPartyContactMechanism,
            EntityPermission entityPermission) {
        return PartyContactMechanismRelationshipFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyContactMechanismRelationshipsByToPartyContactMechanismQueries,
                toPartyContactMechanism, Session.MAX_TIME_LONG);
    }

    public List<PartyContactMechanismRelationship> getPartyContactMechanismRelationshipsByToPartyContactMechanism(PartyContactMechanism toPartyContactMechanism) {
        return getPartyContactMechanismRelationshipsByToPartyContactMechanism(toPartyContactMechanism, EntityPermission.READ_ONLY);
    }

    public List<PartyContactMechanismRelationship> getPartyContactMechanismRelationshipsByToPartyContactMechanismForUpdate(PartyContactMechanism toPartyContactMechanism) {
        return getPartyContactMechanismRelationshipsByToPartyContactMechanism(toPartyContactMechanism, EntityPermission.READ_WRITE);
    }

    public PartyContactMechanismRelationshipTransfer getPartyContactMechanismRelationshipTransfer(UserVisit userVisit, PartyContactMechanismRelationship partyContactMechanismRelationship) {
        return getContactTransferCaches(userVisit).getPartyContactMechanismRelationshipTransferCache().getPartyContactMechanismRelationshipTransfer(partyContactMechanismRelationship);
    }

    public List<PartyContactMechanismRelationshipTransfer> getPartyContactMechanismRelationshipTransfers(UserVisit userVisit, List<PartyContactMechanismRelationship> partyContactMechanismRelationships) {
        List<PartyContactMechanismRelationshipTransfer> partyContactMechanismRelationshipTransfers = new ArrayList<>(partyContactMechanismRelationships.size());
        PartyContactMechanismRelationshipTransferCache partyContactMechanismRelationshipTransferCache = getContactTransferCaches(userVisit).getPartyContactMechanismRelationshipTransferCache();
        
        partyContactMechanismRelationships.stream().forEach((partyContactMechanismRelationship) -> {
            partyContactMechanismRelationshipTransfers.add(partyContactMechanismRelationshipTransferCache.getPartyContactMechanismRelationshipTransfer(partyContactMechanismRelationship));
        });
        
        return partyContactMechanismRelationshipTransfers;
    }
    
    public List<PartyContactMechanismRelationshipTransfer> getPartyContactMechanismRelationshipTransfersByFromPartyContactMechanism(UserVisit userVisit,
            PartyContactMechanism fromPartyContactMechanism) {
        return getPartyContactMechanismRelationshipTransfers(userVisit, getPartyContactMechanismRelationshipsByFromPartyContactMechanism(fromPartyContactMechanism));
    }
    
    public List<PartyContactMechanismRelationshipTransfer> getPartyContactMechanismRelationshipTransfersByToPartyContactMechanism(UserVisit userVisit,
            PartyContactMechanism toPartyContactMechanism) {
        return getPartyContactMechanismRelationshipTransfers(userVisit, getPartyContactMechanismRelationshipsByToPartyContactMechanism(toPartyContactMechanism));
    }
    
    public void deletePartyContactMechanismRelationship(PartyContactMechanismRelationship partyContactMechanismRelationship, BasePK deletedBy) {
        partyContactMechanismRelationship.setThruTime(session.START_TIME_LONG);
        partyContactMechanismRelationship.store();
    }

    public void deletePartyContactMechanismRelationships(List<PartyContactMechanismRelationship> partyContactMechanismRelationships, BasePK deletedBy) {
        partyContactMechanismRelationships.stream().forEach((partyContactMechanismRelationship) -> {
            deletePartyContactMechanismRelationship(partyContactMechanismRelationship, deletedBy);
        });
    }

    public void deletePartyContactMechanismRelationshipsByFromPartyContactMechanism(PartyContactMechanism fromPartyContactMechanism, BasePK deletedBy) {
        deletePartyContactMechanismRelationships(getPartyContactMechanismRelationshipsByFromPartyContactMechanismForUpdate(fromPartyContactMechanism), deletedBy);
    }

    public void deletePartyContactMechanismRelationshipsByToPartyContactMechanism(PartyContactMechanism toPartyContactMechanism, BasePK deletedBy) {
        deletePartyContactMechanismRelationships(getPartyContactMechanismRelationshipsByToPartyContactMechanismForUpdate(toPartyContactMechanism), deletedBy);
    }

    public void deletePartyContactMechanismRelationshipsByPartyContactMechanism(PartyContactMechanism partyContactMechanism, BasePK deletedBy) {
        deletePartyContactMechanismRelationshipsByFromPartyContactMechanism(partyContactMechanism, deletedBy);
        deletePartyContactMechanismRelationshipsByToPartyContactMechanism(partyContactMechanism, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Postal Address Element Types
    // --------------------------------------------------------------------------------
    
    public PostalAddressElementType createPostalAddressElementType(String postalAddressElementTypeName, Boolean isDefault,
            Integer sortOrder) {
        return PostalAddressElementTypeFactory.getInstance().create(postalAddressElementTypeName, isDefault, sortOrder);
    }
    
    public PostalAddressElementType getPostalAddressElementTypeByName(String postalAddressElementTypeName) {
        PostalAddressElementType postalAddressElementType = null;
        
        try {
            PreparedStatement ps = PostalAddressElementTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM postaladdresselementtypes " +
                    "WHERE pstaetyp_postaladdresselementtypename = ?");
            
            ps.setString(1, postalAddressElementTypeName);
            
            postalAddressElementType = PostalAddressElementTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return postalAddressElementType;
    }
    
    public List<PostalAddressElementType> getPostalAddressElementTypes() {
        PreparedStatement ps = PostalAddressElementTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM postaladdresselementtypes " +
                "ORDER BY pstaetyp_sortorder, pstaetyp_postaladdresselementtypename");
        
        return PostalAddressElementTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public PostalAddressElementTypeChoicesBean getPostalAddressElementTypeChoices(String defaultPostalAddressElementTypeChoice, Language language,
            boolean allowNullChoice) {
        List<PostalAddressElementType> postalAddressElementTypes = getPostalAddressElementTypes();
        int size = postalAddressElementTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
        }
        
        for(PostalAddressElementType postalAddressElementType: postalAddressElementTypes) {
            String label = getBestPostalAddressElementTypeDescription(postalAddressElementType, language);
            String value = postalAddressElementType.getPostalAddressElementTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultPostalAddressElementTypeChoice == null? false: defaultPostalAddressElementTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && postalAddressElementType.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new PostalAddressElementTypeChoicesBean(labels, values, defaultValue);
    }
    
    public PostalAddressElementTypeTransfer getPostalAddressElementTypeTransfer(UserVisit userVisit, PostalAddressElementType postalAddressElementType) {
        return getContactTransferCaches(userVisit).getPostalAddressElementTypeTransferCache().getPostalAddressElementTypeTransfer(postalAddressElementType);
    }
    
    // --------------------------------------------------------------------------------
    //   Postal Address Element Type Descriptions
    // --------------------------------------------------------------------------------
    
    public PostalAddressElementTypeDescription createPostalAddressElementTypeDescription(PostalAddressElementType postalAddressElementType,
            Language language, String description) {
        return PostalAddressElementTypeDescriptionFactory.getInstance().create(postalAddressElementType, language,
                description);
    }
    
    public PostalAddressElementTypeDescription getPostalAddressElementTypeDescription(PostalAddressElementType postalAddressElementType, Language language) {
        PostalAddressElementTypeDescription postalAddressElementTypeDescription = null;

        try {
            PreparedStatement ps = PostalAddressElementTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM postaladdresselementtypedescriptions " +
                    "WHERE pstaetypd_pstaetyp_postaladdresselementtypeid = ? AND pstaetypd_lang_languageid = ?");

            ps.setLong(1, postalAddressElementType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());

            postalAddressElementTypeDescription = PostalAddressElementTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch(SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return postalAddressElementTypeDescription;
    }
    
    public String getBestPostalAddressElementTypeDescription(PostalAddressElementType postalAddressElementType, Language language) {
        String description;
        PostalAddressElementTypeDescription postalAddressElementTypeDescription = getPostalAddressElementTypeDescription(postalAddressElementType, language);
        
        if(postalAddressElementTypeDescription == null && !language.getIsDefault()) {
            postalAddressElementTypeDescription = getPostalAddressElementTypeDescription(postalAddressElementType, getPartyControl().getDefaultLanguage());
        }
        
        if(postalAddressElementTypeDescription == null) {
            description = postalAddressElementType.getPostalAddressElementTypeName();
        } else {
            description = postalAddressElementTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Postal Address Formats
    // --------------------------------------------------------------------------------
    
    public PostalAddressFormat createPostalAddressFormat(String postalAddressFormatName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        PostalAddressFormat defaultPostalAddressFormat = getDefaultPostalAddressFormat();
        boolean defaultFound = defaultPostalAddressFormat != null;
        
        if(defaultFound && isDefault) {
            PostalAddressFormatDetailValue defaultPostalAddressFormatDetailValue = getDefaultPostalAddressFormatDetailValueForUpdate();
            
            defaultPostalAddressFormatDetailValue.setIsDefault(Boolean.FALSE);
            updatePostalAddressFormatFromValue(defaultPostalAddressFormatDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        PostalAddressFormat postalAddressFormat = PostalAddressFormatFactory.getInstance().create();
        PostalAddressFormatDetail postalAddressFormatDetail = PostalAddressFormatDetailFactory.getInstance().create(postalAddressFormat,
                postalAddressFormatName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        postalAddressFormat = PostalAddressFormatFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                postalAddressFormat.getPrimaryKey());
        postalAddressFormat.setActiveDetail(postalAddressFormatDetail);
        postalAddressFormat.setLastDetail(postalAddressFormatDetail);
        postalAddressFormat.store();
        
        sendEventUsingNames(postalAddressFormat.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return postalAddressFormat;
    }
    
    private List<PostalAddressFormat> getPostalAddressFormats(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM postaladdressformats, postaladdressformatdetails " +
                    "WHERE pstafmt_activedetailid = pstafmtdt_postaladdressformatdetailid " +
                    "ORDER BY pstafmtdt_sortorder, pstafmtdt_postaladdressformatname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM postaladdressformats, postaladdressformatdetails " +
                    "WHERE pstafmt_activedetailid = pstafmtdt_postaladdressformatdetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = PostalAddressFormatFactory.getInstance().prepareStatement(query);
        
        return PostalAddressFormatFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<PostalAddressFormat> getPostalAddressFormats() {
        return getPostalAddressFormats(EntityPermission.READ_ONLY);
    }
    
    public List<PostalAddressFormat> getPostalAddressFormatsForUpdate() {
        return getPostalAddressFormats(EntityPermission.READ_WRITE);
    }
    
    private PostalAddressFormat getDefaultPostalAddressFormat(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM postaladdressformats, postaladdressformatdetails " +
                    "WHERE pstafmt_activedetailid = pstafmtdt_postaladdressformatdetailid AND pstafmtdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM postaladdressformats, postaladdressformatdetails " +
                    "WHERE pstafmt_activedetailid = pstafmtdt_postaladdressformatdetailid AND pstafmtdt_isdefault = 1 " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = PostalAddressFormatFactory.getInstance().prepareStatement(query);
        
        return PostalAddressFormatFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public PostalAddressFormat getDefaultPostalAddressFormat() {
        return getDefaultPostalAddressFormat(EntityPermission.READ_ONLY);
    }
    
    public PostalAddressFormat getDefaultPostalAddressFormatForUpdate() {
        return getDefaultPostalAddressFormat(EntityPermission.READ_WRITE);
    }
    
    public PostalAddressFormatDetailValue getDefaultPostalAddressFormatDetailValueForUpdate() {
        return getDefaultPostalAddressFormatForUpdate().getLastDetailForUpdate().getPostalAddressFormatDetailValue().clone();
    }
    
    private PostalAddressFormat getPostalAddressFormatByName(String postalAddressFormatName, EntityPermission entityPermission) {
        PostalAddressFormat postalAddressFormat = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM postaladdressformats, postaladdressformatdetails " +
                        "WHERE pstafmt_activedetailid = pstafmtdt_postaladdressformatdetailid AND pstafmtdt_postaladdressformatname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM postaladdressformats, postaladdressformatdetails " +
                        "WHERE pstafmt_activedetailid = pstafmtdt_postaladdressformatdetailid AND pstafmtdt_postaladdressformatname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PostalAddressFormatFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, postalAddressFormatName);
            
            postalAddressFormat = PostalAddressFormatFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return postalAddressFormat;
    }
    
    public PostalAddressFormat getPostalAddressFormatByName(String postalAddressFormatName) {
        return getPostalAddressFormatByName(postalAddressFormatName, EntityPermission.READ_ONLY);
    }
    
    public PostalAddressFormat getPostalAddressFormatByNameForUpdate(String postalAddressFormatName) {
        return getPostalAddressFormatByName(postalAddressFormatName, EntityPermission.READ_WRITE);
    }
    
    public PostalAddressFormatDetailValue getPostalAddressFormatDetailValueForUpdate(PostalAddressFormat postalAddressFormat) {
        return postalAddressFormat == null? null: postalAddressFormat.getLastDetailForUpdate().getPostalAddressFormatDetailValue().clone();
    }
    
    public PostalAddressFormatDetailValue getPostalAddressFormatDetailValueByNameForUpdate(String postalAddressFormatName) {
        return getPostalAddressFormatDetailValueForUpdate(getPostalAddressFormatByNameForUpdate(postalAddressFormatName));
    }
    
    public PostalAddressFormatChoicesBean getPostalAddressFormatChoices(String defaultPostalAddressFormatChoice, Language language,
            boolean allowNullChoice) {
        List<PostalAddressFormat> postalAddressFormats = getPostalAddressFormats();
        int size = postalAddressFormats.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultPostalAddressFormatChoice == null) {
                defaultValue = "";
            }
        }
        
        for(PostalAddressFormat postalAddressFormat: postalAddressFormats) {
            PostalAddressFormatDetail postalAddressFormatDetail = postalAddressFormat.getLastDetail();
            String label = getBestPostalAddressFormatDescription(postalAddressFormat, language);
            String value = postalAddressFormatDetail.getPostalAddressFormatName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultPostalAddressFormatChoice == null? false: defaultPostalAddressFormatChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && postalAddressFormatDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new PostalAddressFormatChoicesBean(labels, values, defaultValue);
    }
    
    public PostalAddressFormatTransfer getPostalAddressFormatTransfer(UserVisit userVisit, PostalAddressFormat postalAddressFormat) {
        return getContactTransferCaches(userVisit).getPostalAddressFormatTransferCache().getPostalAddressFormatTransfer(postalAddressFormat);
    }
    
    public List<PostalAddressFormatTransfer> getPostalAddressFormatTransfers(UserVisit userVisit) {
        List<PostalAddressFormat> postalAddressFormats = getPostalAddressFormats();
        List<PostalAddressFormatTransfer> postalAddressFormatTransfers = new ArrayList<>(postalAddressFormats.size());
        PostalAddressFormatTransferCache postalAddressFormatTransferCache = getContactTransferCaches(userVisit).getPostalAddressFormatTransferCache();
        
        postalAddressFormats.stream().forEach((postalAddressFormat) -> {
            postalAddressFormatTransfers.add(postalAddressFormatTransferCache.getPostalAddressFormatTransfer(postalAddressFormat));
        });
        
        return postalAddressFormatTransfers;
    }
    
    private void updatePostalAddressFormatFromValue(PostalAddressFormatDetailValue postalAddressFormatDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(postalAddressFormatDetailValue.hasBeenModified()) {
            PostalAddressFormat postalAddressFormat = PostalAddressFormatFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     postalAddressFormatDetailValue.getPostalAddressFormatPK());
            PostalAddressFormatDetail postalAddressFormatDetail = postalAddressFormat.getActiveDetailForUpdate();
            
            postalAddressFormatDetail.setThruTime(session.START_TIME_LONG);
            postalAddressFormatDetail.store();
            
            PostalAddressFormatPK postalAddressFormatPK = postalAddressFormatDetail.getPostalAddressFormatPK();
            String postalAddressFormatName = postalAddressFormatDetailValue.getPostalAddressFormatName();
            Boolean isDefault = postalAddressFormatDetailValue.getIsDefault();
            Integer sortOrder = postalAddressFormatDetailValue.getSortOrder();
            
            if(checkDefault) {
                PostalAddressFormat defaultPostalAddressFormat = getDefaultPostalAddressFormat();
                boolean defaultFound = defaultPostalAddressFormat != null && !defaultPostalAddressFormat.equals(postalAddressFormat);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    PostalAddressFormatDetailValue defaultPostalAddressFormatDetailValue = getDefaultPostalAddressFormatDetailValueForUpdate();
                    
                    defaultPostalAddressFormatDetailValue.setIsDefault(Boolean.FALSE);
                    updatePostalAddressFormatFromValue(defaultPostalAddressFormatDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            postalAddressFormatDetail = PostalAddressFormatDetailFactory.getInstance().create(postalAddressFormatPK, postalAddressFormatName,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            postalAddressFormat.setActiveDetail(postalAddressFormatDetail);
            postalAddressFormat.setLastDetail(postalAddressFormatDetail);
            
            sendEventUsingNames(postalAddressFormatPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updatePostalAddressFormatFromValue(PostalAddressFormatDetailValue postalAddressFormatDetailValue, BasePK updatedBy) {
        updatePostalAddressFormatFromValue(postalAddressFormatDetailValue, true, updatedBy);
    }
    
    public void deletePostalAddressFormat(PostalAddressFormat postalAddressFormat, BasePK deletedBy) {
        deletePostalAddressFormatDescriptionsByPostalAddressFormat(postalAddressFormat, deletedBy);
        deletePostalAddressLinesByPostalAddressFormat(postalAddressFormat, deletedBy);
        
        PostalAddressFormatDetail postalAddressFormatDetail = postalAddressFormat.getLastDetailForUpdate();
        postalAddressFormatDetail.setThruTime(session.START_TIME_LONG);
        postalAddressFormat.setActiveDetail(null);
        postalAddressFormat.store();
        
        // Check for default, and pick one if necessary
        PostalAddressFormat defaultPostalAddressFormat = getDefaultPostalAddressFormat();
        if(defaultPostalAddressFormat == null) {
            List<PostalAddressFormat> postalAddressFormats = getPostalAddressFormatsForUpdate();
            
            if(!postalAddressFormats.isEmpty()) {
                Iterator<PostalAddressFormat> iter = postalAddressFormats.iterator();
                if(iter.hasNext()) {
                    defaultPostalAddressFormat = iter.next();
                }
                PostalAddressFormatDetailValue postalAddressFormatDetailValue = defaultPostalAddressFormat.getLastDetailForUpdate().getPostalAddressFormatDetailValue().clone();
                
                postalAddressFormatDetailValue.setIsDefault(Boolean.TRUE);
                updatePostalAddressFormatFromValue(postalAddressFormatDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(postalAddressFormat.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Postal Address Format Descriptions
    // --------------------------------------------------------------------------------
    
    public PostalAddressFormatDescription createPostalAddressFormatDescription(PostalAddressFormat postalAddressFormat,
            Language language, String description, BasePK createdBy) {
        PostalAddressFormatDescription postalAddressFormatDescription = PostalAddressFormatDescriptionFactory.getInstance().create(session,
                postalAddressFormat, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(postalAddressFormat.getPrimaryKey(), EventTypes.MODIFY.name(), postalAddressFormatDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return postalAddressFormatDescription;
    }
    
    private PostalAddressFormatDescription getPostalAddressFormatDescription(PostalAddressFormat postalAddressFormat, Language language, EntityPermission entityPermission) {
        PostalAddressFormatDescription postalAddressFormatDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM postaladdressformatdescriptions " +
                        "WHERE pstafmtd_pstafmt_postaladdressformatid = ? AND pstafmtd_lang_languageid = ? AND pstafmtd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM postaladdressformatdescriptions " +
                        "WHERE pstafmtd_pstafmt_postaladdressformatid = ? AND pstafmtd_lang_languageid = ? AND pstafmtd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PostalAddressFormatDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, postalAddressFormat.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            postalAddressFormatDescription = PostalAddressFormatDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return postalAddressFormatDescription;
    }
    
    public PostalAddressFormatDescription getPostalAddressFormatDescription(PostalAddressFormat postalAddressFormat, Language language) {
        return getPostalAddressFormatDescription(postalAddressFormat, language, EntityPermission.READ_ONLY);
    }
    
    public PostalAddressFormatDescription getPostalAddressFormatDescriptionForUpdate(PostalAddressFormat postalAddressFormat, Language language) {
        return getPostalAddressFormatDescription(postalAddressFormat, language, EntityPermission.READ_WRITE);
    }
    
    public PostalAddressFormatDescriptionValue getPostalAddressFormatDescriptionValue(PostalAddressFormatDescription postalAddressFormatDescription) {
        return postalAddressFormatDescription == null? null: postalAddressFormatDescription.getPostalAddressFormatDescriptionValue().clone();
    }
    
    public PostalAddressFormatDescriptionValue getPostalAddressFormatDescriptionValueForUpdate(PostalAddressFormat postalAddressFormat, Language language) {
        return getPostalAddressFormatDescriptionValue(getPostalAddressFormatDescriptionForUpdate(postalAddressFormat, language));
    }
    
    private List<PostalAddressFormatDescription> getPostalAddressFormatDescriptionsByPostalAddressFormat(PostalAddressFormat postalAddressFormat, EntityPermission entityPermission) {
        List<PostalAddressFormatDescription> postalAddressFormatDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM postaladdressformatdescriptions, languages " +
                        "WHERE pstafmtd_pstafmt_postaladdressformatid = ? AND pstafmtd_thrutime = ? AND pstafmtd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM postaladdressformatdescriptions " +
                        "WHERE pstafmtd_pstafmt_postaladdressformatid = ? AND pstafmtd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PostalAddressFormatDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, postalAddressFormat.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            postalAddressFormatDescriptions = PostalAddressFormatDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return postalAddressFormatDescriptions;
    }
    
    public List<PostalAddressFormatDescription> getPostalAddressFormatDescriptionsByPostalAddressFormat(PostalAddressFormat postalAddressFormat) {
        return getPostalAddressFormatDescriptionsByPostalAddressFormat(postalAddressFormat, EntityPermission.READ_ONLY);
    }
    
    public List<PostalAddressFormatDescription> getPostalAddressFormatDescriptionsByPostalAddressFormatForUpdate(PostalAddressFormat postalAddressFormat) {
        return getPostalAddressFormatDescriptionsByPostalAddressFormat(postalAddressFormat, EntityPermission.READ_WRITE);
    }
    
    public String getBestPostalAddressFormatDescription(PostalAddressFormat postalAddressFormat, Language language) {
        String description;
        PostalAddressFormatDescription postalAddressFormatDescription = getPostalAddressFormatDescription(postalAddressFormat, language);
        
        if(postalAddressFormatDescription == null && !language.getIsDefault()) {
            postalAddressFormatDescription = getPostalAddressFormatDescription(postalAddressFormat, getPartyControl().getDefaultLanguage());
        }
        
        if(postalAddressFormatDescription == null) {
            description = postalAddressFormat.getLastDetail().getPostalAddressFormatName();
        } else {
            description = postalAddressFormatDescription.getDescription();
        }
        
        return description;
    }
    
    public PostalAddressFormatDescriptionTransfer getPostalAddressFormatDescriptionTransfer(UserVisit userVisit, PostalAddressFormatDescription postalAddressFormatDescription) {
        return getContactTransferCaches(userVisit).getPostalAddressFormatDescriptionTransferCache().getPostalAddressFormatDescriptionTransfer(postalAddressFormatDescription);
    }
    
    public List<PostalAddressFormatDescriptionTransfer> getPostalAddressFormatDescriptionTransfersByPostalAddressFormat(UserVisit userVisit, PostalAddressFormat postalAddressFormat) {
        List<PostalAddressFormatDescription> postalAddressFormatDescriptions = getPostalAddressFormatDescriptionsByPostalAddressFormat(postalAddressFormat);
        List<PostalAddressFormatDescriptionTransfer> postalAddressFormatDescriptionTransfers = new ArrayList<>(postalAddressFormatDescriptions.size());
        PostalAddressFormatDescriptionTransferCache postalAddressFormatDescriptionTransferCache = getContactTransferCaches(userVisit).getPostalAddressFormatDescriptionTransferCache();
        
        postalAddressFormatDescriptions.stream().forEach((postalAddressFormatDescription) -> {
            postalAddressFormatDescriptionTransfers.add(postalAddressFormatDescriptionTransferCache.getPostalAddressFormatDescriptionTransfer(postalAddressFormatDescription));
        });
        
        return postalAddressFormatDescriptionTransfers;
    }
    
    public void updatePostalAddressFormatDescriptionFromValue(PostalAddressFormatDescriptionValue postalAddressFormatDescriptionValue, BasePK updatedBy) {
        if(postalAddressFormatDescriptionValue.hasBeenModified()) {
            PostalAddressFormatDescription postalAddressFormatDescription = PostalAddressFormatDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, postalAddressFormatDescriptionValue.getPrimaryKey());
            
            postalAddressFormatDescription.setThruTime(session.START_TIME_LONG);
            postalAddressFormatDescription.store();
            
            PostalAddressFormat postalAddressFormat = postalAddressFormatDescription.getPostalAddressFormat();
            Language language = postalAddressFormatDescription.getLanguage();
            String description = postalAddressFormatDescriptionValue.getDescription();
            
            postalAddressFormatDescription = PostalAddressFormatDescriptionFactory.getInstance().create(postalAddressFormat, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(postalAddressFormat.getPrimaryKey(), EventTypes.MODIFY.name(), postalAddressFormatDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deletePostalAddressFormatDescription(PostalAddressFormatDescription postalAddressFormatDescription, BasePK deletedBy) {
        postalAddressFormatDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(postalAddressFormatDescription.getPostalAddressFormatPK(), EventTypes.MODIFY.name(), postalAddressFormatDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deletePostalAddressFormatDescriptionsByPostalAddressFormat(PostalAddressFormat postalAddressFormat, BasePK deletedBy) {
        List<PostalAddressFormatDescription> postalAddressFormatDescriptions = getPostalAddressFormatDescriptionsByPostalAddressFormatForUpdate(postalAddressFormat);
        
        postalAddressFormatDescriptions.stream().forEach((postalAddressFormatDescription) -> {
            deletePostalAddressFormatDescription(postalAddressFormatDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Postal Address Lines
    // --------------------------------------------------------------------------------
    
    public PostalAddressLine createPostalAddressLine(PostalAddressFormat postalAddressFormat, Integer postalAddressLineSortOrder,
            String prefix, Boolean alwaysIncludePrefix, String suffix, Boolean alwaysIncludeSuffix, Boolean collapseIfEmpty,
            BasePK createdBy) {
        PostalAddressLine postalAddressLine = PostalAddressLineFactory.getInstance().create();
        PostalAddressLineDetail postalAddressLineDetail = PostalAddressLineDetailFactory.getInstance().create(session,
                postalAddressLine, postalAddressFormat, postalAddressLineSortOrder, prefix, alwaysIncludePrefix, suffix,
                alwaysIncludeSuffix, collapseIfEmpty, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        postalAddressLine = PostalAddressLineFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                postalAddressLine.getPrimaryKey());
        postalAddressLine.setActiveDetail(postalAddressLineDetail);
        postalAddressLine.setLastDetail(postalAddressLineDetail);
        postalAddressLine.store();
        
        sendEventUsingNames(postalAddressFormat.getPrimaryKey(), EventTypes.MODIFY.name(), postalAddressLine.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return postalAddressLine;
    }
    
    private PostalAddressLine getPostalAddressLine(PostalAddressFormat postalAddressFormat, Integer postalAddressLineSortOrder,
            EntityPermission entityPermission) {
        PostalAddressLine postalAddressLine = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM postaladdresslines, postaladdresslinedetails " +
                        "WHERE pstal_activedetailid = pstaldt_postaladdresslinedetailid " +
                        "AND pstaldt_pstafmt_postaladdressformatid = ? AND pstaldt_postaladdresslinesortorder = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM postaladdresslines, postaladdresslinedetails " +
                        "WHERE pstal_activedetailid = pstaldt_postaladdresslinedetailid " +
                        "AND pstaldt_pstafmt_postaladdressformatid = ? AND pstaldt_postaladdresslinesortorder = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PostalAddressLineFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, postalAddressFormat.getPrimaryKey().getEntityId());
            ps.setInt(2, postalAddressLineSortOrder);
            
            postalAddressLine = PostalAddressLineFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return postalAddressLine;
    }
    
    public PostalAddressLine getPostalAddressLine(PostalAddressFormat postalAddressFormat, Integer postalAddressLineSortOrder) {
        return getPostalAddressLine(postalAddressFormat, postalAddressLineSortOrder, EntityPermission.READ_ONLY);
    }
    
    public PostalAddressLine getPostalAddressLineForUpdate(PostalAddressFormat postalAddressFormat, Integer postalAddressLineSortOrder) {
        return getPostalAddressLine(postalAddressFormat, postalAddressLineSortOrder, EntityPermission.READ_WRITE);
    }
    
    public PostalAddressLineDetailValue getPostalAddressLineDetailValueForUpdate(PostalAddressLine postalAddressLine) {
        return postalAddressLine == null? null: postalAddressLine.getLastDetailForUpdate().getPostalAddressLineDetailValue().clone();
    }
    
    public PostalAddressLineDetailValue getPostalAddressLineDetailValueForUpdate(PostalAddressFormat postalAddressFormat, Integer postalAddressLineSortOrder) {
        return getPostalAddressLineDetailValueForUpdate(getPostalAddressLineForUpdate(postalAddressFormat, postalAddressLineSortOrder));
    }
    
    private List<PostalAddressLine> getPostalAddressLinesByPostalAddressFormat(PostalAddressFormat postalAddressFormat,
            EntityPermission entityPermission) {
        List<PostalAddressLine> postalAddressLines = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM postaladdresslines, postaladdresslinedetails " +
                        "WHERE pstal_activedetailid = pstaldt_postaladdresslinedetailid " +
                        "AND pstaldt_pstafmt_postaladdressformatid = ? " +
                        "ORDER BY pstaldt_postaladdresslinesortorder";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM postaladdresslines, postaladdresslinedetails " +
                        "WHERE pstal_activedetailid = pstaldt_postaladdresslinedetailid " +
                        "AND pstaldt_pstafmt_postaladdressformatid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PostalAddressLineFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, postalAddressFormat.getPrimaryKey().getEntityId());
            
            postalAddressLines = PostalAddressLineFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return postalAddressLines;
    }
    
    public List<PostalAddressLine> getPostalAddressLinesByPostalAddressFormat(PostalAddressFormat postalAddressFormat) {
        return getPostalAddressLinesByPostalAddressFormat(postalAddressFormat, EntityPermission.READ_ONLY);
    }
    
    public List<PostalAddressLine> getPostalAddressLinesByPostalAddressFormatForUpdate(PostalAddressFormat postalAddressFormat) {
        return getPostalAddressLinesByPostalAddressFormat(postalAddressFormat, EntityPermission.READ_WRITE);
    }
    
    public PostalAddressLineTransfer getPostalAddressLineTransfer(UserVisit userVisit, PostalAddressLine postalAddressLine) {
        return getContactTransferCaches(userVisit).getPostalAddressLineTransferCache().getPostalAddressLineTransfer(postalAddressLine);
    }
    
    public List<PostalAddressLineTransfer> getPostalAddressLineTransfersByPostalAddressFormat(UserVisit userVisit, PostalAddressFormat postalAddressFormat) {
        List<PostalAddressLine> postalAddressLines = getPostalAddressLinesByPostalAddressFormat(postalAddressFormat);
        List<PostalAddressLineTransfer> postalAddressLineTransfers = new ArrayList<>(postalAddressLines.size());
        PostalAddressLineTransferCache postalAddressLineTransferCache = getContactTransferCaches(userVisit).getPostalAddressLineTransferCache();
        
        postalAddressLines.stream().forEach((postalAddressLine) -> {
            postalAddressLineTransfers.add(postalAddressLineTransferCache.getPostalAddressLineTransfer(postalAddressLine));
        });
        
        return postalAddressLineTransfers;
    }
    
    public void updatePostalAddressLineFromValue(PostalAddressLineDetailValue postalAddressLineDetailValue, BasePK updatedBy) {
        if(postalAddressLineDetailValue.hasBeenModified()) {
            PostalAddressLine postalAddressLine = PostalAddressLineFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     postalAddressLineDetailValue.getPostalAddressLinePK());
            PostalAddressLineDetail postalAddressLineDetail = postalAddressLine.getActiveDetailForUpdate();
            
            postalAddressLineDetail.setThruTime(session.START_TIME_LONG);
            postalAddressLineDetail.store();
            
            PostalAddressLinePK postalAddressLinePK = postalAddressLineDetail.getPostalAddressLinePK();
            PostalAddressFormatPK postalAddressFormatPK = postalAddressLineDetail.getPostalAddressFormatPK();
            Integer postalAddressLineSortOrder = postalAddressLineDetailValue.getPostalAddressLineSortOrder();
            String prefix = postalAddressLineDetailValue.getPrefix();
            Boolean alwaysIncludePrefix = postalAddressLineDetailValue.getAlwaysIncludePrefix();
            String suffix = postalAddressLineDetailValue.getSuffix();
            Boolean alwaysIncludeSuffix = postalAddressLineDetailValue.getAlwaysIncludeSuffix();
            Boolean collapseIfEmpty = postalAddressLineDetailValue.getCollapseIfEmpty();
            
            postalAddressLineDetail = PostalAddressLineDetailFactory.getInstance().create(postalAddressLinePK,
                    postalAddressFormatPK, postalAddressLineSortOrder, prefix, alwaysIncludePrefix, suffix, alwaysIncludeSuffix,
                    collapseIfEmpty, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            postalAddressLine.setActiveDetail(postalAddressLineDetail);
            postalAddressLine.setLastDetail(postalAddressLineDetail);
            
            sendEventUsingNames(postalAddressFormatPK, EventTypes.MODIFY.name(), postalAddressLinePK, EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deletePostalAddressLine(PostalAddressLine postalAddressLine, BasePK deletedBy) {
        deletePostalAddressLineElementsByPostalAddressLine(postalAddressLine, deletedBy);
        
        PostalAddressLineDetail postalAddressLineDetail = postalAddressLine.getLastDetailForUpdate();
        postalAddressLineDetail.setThruTime(session.START_TIME_LONG);
        postalAddressLine.setActiveDetail(null);
        postalAddressLine.store();
        
        sendEventUsingNames(postalAddressLineDetail.getPostalAddressFormatPK(), EventTypes.MODIFY.name(), postalAddressLine.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deletePostalAddressLinesByPostalAddressFormat(PostalAddressFormat postalAddressFormat, BasePK deletedBy) {
        List<PostalAddressLine> postalAddressLines = getPostalAddressLinesByPostalAddressFormatForUpdate(postalAddressFormat);
        
        postalAddressLines.stream().forEach((postalAddressLine) -> {
            deletePostalAddressLine(postalAddressLine, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Postal Address Line Elements
    // --------------------------------------------------------------------------------
    
    public PostalAddressLineElement createPostalAddressLineElement(PostalAddressLine postalAddressLine,
            Integer postalAddressLineElementSortOrder, PostalAddressElementType postalAddressElementType, String prefix,
            Boolean alwaysIncludePrefix, String suffix, Boolean alwaysIncludeSuffix, BasePK createdBy) {
        
        PostalAddressLineElement postalAddressLineElement = PostalAddressLineElementFactory.getInstance().create(session,
                postalAddressLine, postalAddressLineElementSortOrder, postalAddressElementType, prefix, alwaysIncludePrefix, suffix,
                alwaysIncludeSuffix, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(postalAddressLine.getLastDetail().getPostalAddressFormatPK(), EventTypes.MODIFY.name(), postalAddressLineElement.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return postalAddressLineElement;
    }
    
    private PostalAddressLineElement getPostalAddressLineElement(PostalAddressLine postalAddressLine,
            Integer postalAddressLineElementSortOrder, EntityPermission entityPermission) {
        PostalAddressLineElement postalAddressLineElement = null;
        
        try {
            PreparedStatement ps = PostalAddressLineElementFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM postaladdresslineelements " +
                    "WHERE pstale_pstal_postaladdresslineid = ? AND pstale_postaladdresslineelementsortorder = ? " +
                    "AND pstale_thrutime = ?");
            
            ps.setLong(1, postalAddressLine.getPrimaryKey().getEntityId());
            ps.setInt(2, postalAddressLineElementSortOrder);
            ps.setLong(3, Session.MAX_TIME);
            
            postalAddressLineElement = PostalAddressLineElementFactory.getInstance().getEntityFromQuery(entityPermission,
                    ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return postalAddressLineElement;
    }
    
    public PostalAddressLineElement getPostalAddressLineElement(PostalAddressLine postalAddressLine,
            Integer postalAddressLineElementSortOrder) {
        return getPostalAddressLineElement(postalAddressLine, postalAddressLineElementSortOrder, EntityPermission.READ_ONLY);
    }
    
    public PostalAddressLineElement getPostalAddressLineElementForUpdate(PostalAddressLine postalAddressLine,
            Integer postalAddressLineElementSortOrder) {
        return getPostalAddressLineElement(postalAddressLine, postalAddressLineElementSortOrder, EntityPermission.READ_WRITE);
    }
    
    public PostalAddressLineElementValue getPostalAddressLineElementValueForUpdate(PostalAddressLineElement postalAddressLineElement) {
        return postalAddressLineElement == null? null: postalAddressLineElement.getPostalAddressLineElementValue().clone();
    }
    
    public PostalAddressLineElementValue getPostalAddressLineElementValueForUpdate(PostalAddressLine postalAddressLine,
            Integer postalAddressLineElementSortOrder) {
        return getPostalAddressLineElementValueForUpdate(getPostalAddressLineElementForUpdate(postalAddressLine, postalAddressLineElementSortOrder));
    }
    
    private List<PostalAddressLineElement> getPostalAddressLineElementsByPostalAddressLine(PostalAddressLine postalAddressLine,
            EntityPermission entityPermission) {
        List<PostalAddressLineElement> postalAddressLineElements = null;
        
        try {
            PreparedStatement ps = PostalAddressLineElementFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM postaladdresslineelements " +
                    "WHERE pstale_pstal_postaladdresslineid = ? AND pstale_thrutime = ? " +
                    "ORDER BY pstale_postaladdresslineelementsortorder");
            
            ps.setLong(1, postalAddressLine.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            postalAddressLineElements = PostalAddressLineElementFactory.getInstance().getEntitiesFromQuery(entityPermission,
                    ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return postalAddressLineElements;
    }
    
    public List<PostalAddressLineElement> getPostalAddressLineElementsByPostalAddressLine(PostalAddressLine postalAddressLine) {
        return getPostalAddressLineElementsByPostalAddressLine(postalAddressLine, EntityPermission.READ_ONLY);
    }
    
    public List<PostalAddressLineElement> getPostalAddressLineElementsByPostalAddressLineForUpdate(PostalAddressLine postalAddressLine) {
        return getPostalAddressLineElementsByPostalAddressLine(postalAddressLine, EntityPermission.READ_WRITE);
    }
    
    public PostalAddressLineElementTransfer getPostalAddressLineElementTransfer(UserVisit userVisit, PostalAddressLineElement postalAddressLineElement) {
        return getContactTransferCaches(userVisit).getPostalAddressLineElementTransferCache().getPostalAddressLineElementTransfer(postalAddressLineElement);
    }
    
    public List<PostalAddressLineElementTransfer> getPostalAddressLineElementTransfersByPostalAddressLine(UserVisit userVisit, PostalAddressLine postalAddressLine) {
        List<PostalAddressLineElement> postalAddressLineElements = getPostalAddressLineElementsByPostalAddressLine(postalAddressLine);
        List<PostalAddressLineElementTransfer> postalAddressLineElementTransfers = new ArrayList<>(postalAddressLineElements.size());
        PostalAddressLineElementTransferCache postalAddressLineElementTransferCache = getContactTransferCaches(userVisit).getPostalAddressLineElementTransferCache();
        
        postalAddressLineElements.stream().forEach((postalAddressLineElement) -> {
            postalAddressLineElementTransfers.add(postalAddressLineElementTransferCache.getPostalAddressLineElementTransfer(postalAddressLineElement));
        });
        
        return postalAddressLineElementTransfers;
    }
    
    public void updatePostalAddressLineElementFromValue(PostalAddressLineElementValue postalAddressLineElementValue, BasePK updatedBy) {
        if(postalAddressLineElementValue.hasBeenModified()) {
            PostalAddressLineElement postalAddressLineElement = PostalAddressLineElementFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     postalAddressLineElementValue.getPrimaryKey());
            
            postalAddressLineElement.setThruTime(session.START_TIME_LONG);
            postalAddressLineElement.store();
            
            PostalAddressLinePK postalAddressLinePK = postalAddressLineElement.getPostalAddressLinePK();
            Integer postalAddressLineElementSortOrder = postalAddressLineElementValue.getPostalAddressLineElementSortOrder();
            PostalAddressElementTypePK postalAddressElementTypePK = postalAddressLineElementValue.getPostalAddressElementTypePK();
            String prefix = postalAddressLineElementValue.getPrefix();
            Boolean alwaysIncludePrefix = postalAddressLineElementValue.getAlwaysIncludePrefix();
            String suffix = postalAddressLineElementValue.getSuffix();
            Boolean alwaysIncludeSuffix = postalAddressLineElementValue.getAlwaysIncludeSuffix();
            
            postalAddressLineElement = PostalAddressLineElementFactory.getInstance().create(postalAddressLinePK,
                    postalAddressLineElementSortOrder, postalAddressElementTypePK, prefix, alwaysIncludePrefix, suffix, alwaysIncludeSuffix,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(postalAddressLineElement.getPostalAddressLine().getLastDetail().getPostalAddressFormatPK(), EventTypes.MODIFY.name(), postalAddressLineElement.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deletePostalAddressLineElement(PostalAddressLineElement postalAddressLineElement, BasePK deletedBy) {
        postalAddressLineElement.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(postalAddressLineElement.getPostalAddressLine().getLastDetail().getPostalAddressFormatPK(), EventTypes.MODIFY.name(), postalAddressLineElement.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deletePostalAddressLineElementsByPostalAddressLine(PostalAddressLine postalAddressLine, BasePK deletedBy) {
        List<PostalAddressLineElement> postalAddressLineElements = getPostalAddressLineElementsByPostalAddressLineForUpdate(postalAddressLine);
        
        postalAddressLineElements.stream().forEach((postalAddressLineElement) -> {
            deletePostalAddressLineElement(postalAddressLineElement, deletedBy);
        });
    }
    
}

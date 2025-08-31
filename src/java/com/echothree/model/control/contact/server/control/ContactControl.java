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

package com.echothree.model.control.contact.server.control;

import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.contact.common.choice.ContactMechanismAliasTypeChoicesBean;
import com.echothree.model.control.contact.common.choice.ContactMechanismChoicesBean;
import com.echothree.model.control.contact.common.choice.ContactMechanismPurposeChoicesBean;
import com.echothree.model.control.contact.common.choice.ContactMechanismTypeChoicesBean;
import com.echothree.model.control.contact.common.choice.EmailAddressStatusChoicesBean;
import com.echothree.model.control.contact.common.choice.EmailAddressVerificationChoicesBean;
import com.echothree.model.control.contact.common.choice.PostalAddressElementTypeChoicesBean;
import com.echothree.model.control.contact.common.choice.PostalAddressFormatChoicesBean;
import com.echothree.model.control.contact.common.choice.PostalAddressStatusChoicesBean;
import com.echothree.model.control.contact.common.choice.TelephoneStatusChoicesBean;
import com.echothree.model.control.contact.common.choice.WebAddressStatusChoicesBean;
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
import com.echothree.model.control.contact.common.workflow.EmailAddressStatusConstants;
import com.echothree.model.control.contact.common.workflow.EmailAddressVerificationConstants;
import com.echothree.model.control.contact.common.workflow.PostalAddressStatusConstants;
import com.echothree.model.control.contact.common.workflow.TelephoneStatusConstants;
import com.echothree.model.control.contact.common.workflow.WebAddressStatusConstants;
import com.echothree.model.control.contact.server.transfer.ContactTransferCaches;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.control.order.server.control.OrderShipmentGroupControl;
import com.echothree.model.control.payment.server.control.BillingControl;
import com.echothree.model.control.payment.server.control.PartyPaymentMethodControl;
import com.echothree.model.control.shipment.server.ShipmentControl;
import com.echothree.model.data.contact.common.pk.ContactMechanismPK;
import com.echothree.model.data.contact.common.pk.ContactMechanismPurposePK;
import com.echothree.model.data.contact.server.entity.ContactEmailAddress;
import com.echothree.model.data.contact.server.entity.ContactInet4Address;
import com.echothree.model.data.contact.server.entity.ContactInet6Address;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.contact.server.entity.ContactMechanismAlias;
import com.echothree.model.data.contact.server.entity.ContactMechanismAliasType;
import com.echothree.model.data.contact.server.entity.ContactMechanismAliasTypeDescription;
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
import com.echothree.model.data.contact.server.entity.PartyContactMechanismPurpose;
import com.echothree.model.data.contact.server.entity.PartyContactMechanismRelationship;
import com.echothree.model.data.contact.server.entity.PostalAddressElementType;
import com.echothree.model.data.contact.server.entity.PostalAddressElementTypeDescription;
import com.echothree.model.data.contact.server.entity.PostalAddressFormat;
import com.echothree.model.data.contact.server.entity.PostalAddressFormatDescription;
import com.echothree.model.data.contact.server.entity.PostalAddressLine;
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
import com.echothree.model.data.core.common.pk.AppearancePK;
import com.echothree.model.data.core.server.entity.Appearance;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.factory.AppearanceFactory;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.NameSuffix;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PersonalTitle;
import com.echothree.model.data.user.server.entity.UserVisit;
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

public class ContactControl
        extends BaseModelControl {
    
    /** Creates a new instance of ContactControl */
    public ContactControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Transfer Caches
    // --------------------------------------------------------------------------------
    
    private ContactTransferCaches contactTransferCaches;
    
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
        ContactMechanismType contactMechanismType;
        
        try {
            var ps = ContactMechanismTypeFactory.getInstance().prepareStatement(
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
        var ps = ContactMechanismTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM contactmechanismtypes " +
                "ORDER BY cmt_sortorder, cmt_contactmechanismtypename");
        
        return ContactMechanismTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public ContactMechanismTypeChoicesBean getContactMechanismTypeChoices(String defaultContactMechanismTypeChoice, Language language, boolean allowNullChoice) {
        var contactMechanismTypes = getContactMechanismTypes();
        var size = contactMechanismTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
        }
        
        for(var contactMechanismType : contactMechanismTypes) {
            var label = getBestContactMechanismTypeDescription(contactMechanismType, language);
            var value = contactMechanismType.getContactMechanismTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultContactMechanismTypeChoice != null && defaultContactMechanismTypeChoice.equals(value);
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
        var entities = getContactMechanismTypes();
        List<ContactMechanismTypeTransfer> transfers = new ArrayList<>(entities.size());
        var cache = getContactTransferCaches(userVisit).getContactMechanismTypeTransferCache();
        
        entities.forEach((entity) ->
                transfers.add(cache.getContactMechanismTypeTransfer(entity))
        );
        
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
        ContactMechanismTypeDescription contactMechanismTypeDescription;
        
        try {
            var ps = ContactMechanismTypeDescriptionFactory.getInstance().prepareStatement(
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
        var contactMechanismTypeDescription = getContactMechanismTypeDescription(contactMechanismType, language);
        
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
        var defaultContactMechanismAliasType = getDefaultContactMechanismAliasType();
        var defaultFound = defaultContactMechanismAliasType != null;

        if(defaultFound && isDefault) {
            var defaultContactMechanismAliasTypeDetailValue = getDefaultContactMechanismAliasTypeDetailValueForUpdate();

            defaultContactMechanismAliasTypeDetailValue.setIsDefault(false);
            updateContactMechanismAliasTypeFromValue(defaultContactMechanismAliasTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var contactMechanismAliasType = ContactMechanismAliasTypeFactory.getInstance().create();
        var contactMechanismAliasTypeDetail = ContactMechanismAliasTypeDetailFactory.getInstance().create(contactMechanismAliasType,
                contactMechanismAliasTypeName, validationPattern, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        contactMechanismAliasType = ContactMechanismAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                contactMechanismAliasType.getPrimaryKey());
        contactMechanismAliasType.setActiveDetail(contactMechanismAliasTypeDetail);
        contactMechanismAliasType.setLastDetail(contactMechanismAliasTypeDetail);
        contactMechanismAliasType.store();

        sendEvent(contactMechanismAliasType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

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
        List<ContactMechanismAliasType> contactMechanismAliasTypes;

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

            var ps = ContactMechanismAliasTypeFactory.getInstance().prepareStatement(query);

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

    public List<ContactMechanismAliasTypeTransfer> getContactMechanismAliasTypeTransfers(UserVisit userVisit, Collection<ContactMechanismAliasType> contactMechanismAliasTypes) {
        List<ContactMechanismAliasTypeTransfer> contactMechanismAliasTypeTransfers = new ArrayList<>(contactMechanismAliasTypes.size());
        var contactMechanismAliasTypeTransferCache = getContactTransferCaches(userVisit).getContactMechanismAliasTypeTransferCache();

        contactMechanismAliasTypes.forEach((contactMechanismAliasType) ->
                contactMechanismAliasTypeTransfers.add(contactMechanismAliasTypeTransferCache.getContactMechanismAliasTypeTransfer(contactMechanismAliasType))
        );

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
        var contactMechanismAliasTypes = getContactMechanismAliasTypes();
        var size = contactMechanismAliasTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultContactMechanismAliasTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var contactMechanismAliasType : contactMechanismAliasTypes) {
            var contactMechanismAliasTypeDetail = contactMechanismAliasType.getLastDetail();

            var label = getBestContactMechanismAliasTypeDescription(contactMechanismAliasType, language);
            var value = contactMechanismAliasTypeDetail.getContactMechanismAliasTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultContactMechanismAliasTypeChoice != null && defaultContactMechanismAliasTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && contactMechanismAliasTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ContactMechanismAliasTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateContactMechanismAliasTypeFromValue(ContactMechanismAliasTypeDetailValue contactMechanismAliasTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(contactMechanismAliasTypeDetailValue.hasBeenModified()) {
            var contactMechanismAliasType = ContactMechanismAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     contactMechanismAliasTypeDetailValue.getContactMechanismAliasTypePK());
            var contactMechanismAliasTypeDetail = contactMechanismAliasType.getActiveDetailForUpdate();

            contactMechanismAliasTypeDetail.setThruTime(session.START_TIME_LONG);
            contactMechanismAliasTypeDetail.store();

            var contactMechanismAliasTypePK = contactMechanismAliasTypeDetail.getContactMechanismAliasTypePK(); // Not updated
            var contactMechanismAliasTypeName = contactMechanismAliasTypeDetailValue.getContactMechanismAliasTypeName();
            var validationPattern = contactMechanismAliasTypeDetailValue.getValidationPattern();
            var isDefault = contactMechanismAliasTypeDetailValue.getIsDefault();
            var sortOrder = contactMechanismAliasTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultContactMechanismAliasType = getDefaultContactMechanismAliasType();
                var defaultFound = defaultContactMechanismAliasType != null && !defaultContactMechanismAliasType.equals(contactMechanismAliasType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultContactMechanismAliasTypeDetailValue = getDefaultContactMechanismAliasTypeDetailValueForUpdate();

                    defaultContactMechanismAliasTypeDetailValue.setIsDefault(false);
                    updateContactMechanismAliasTypeFromValue(defaultContactMechanismAliasTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            contactMechanismAliasTypeDetail = ContactMechanismAliasTypeDetailFactory.getInstance().create(contactMechanismAliasTypePK,
                    contactMechanismAliasTypeName, validationPattern, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            contactMechanismAliasType.setActiveDetail(contactMechanismAliasTypeDetail);
            contactMechanismAliasType.setLastDetail(contactMechanismAliasTypeDetail);

            sendEvent(contactMechanismAliasTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateContactMechanismAliasTypeFromValue(ContactMechanismAliasTypeDetailValue contactMechanismAliasTypeDetailValue, BasePK updatedBy) {
        updateContactMechanismAliasTypeFromValue(contactMechanismAliasTypeDetailValue, true, updatedBy);
    }

    public void deleteContactMechanismAliasType(ContactMechanismAliasType contactMechanismAliasType, BasePK deletedBy) {
        deleteContactMechanismAliasTypeDescriptionsByContactMechanismAliasType(contactMechanismAliasType, deletedBy);
        deleteContactMechanismAliasesByContactMechanismAliasType(contactMechanismAliasType, deletedBy);

        var contactMechanismAliasTypeDetail = contactMechanismAliasType.getLastDetailForUpdate();
        contactMechanismAliasTypeDetail.setThruTime(session.START_TIME_LONG);
        contactMechanismAliasType.setActiveDetail(null);
        contactMechanismAliasType.store();

        // Check for default, and pick one if necessary
        var defaultContactMechanismAliasType = getDefaultContactMechanismAliasType();
        if(defaultContactMechanismAliasType == null) {
            var contactMechanismAliasTypes = getContactMechanismAliasTypesForUpdate();

            if(!contactMechanismAliasTypes.isEmpty()) {
                var iter = contactMechanismAliasTypes.iterator();
                if(iter.hasNext()) {
                    defaultContactMechanismAliasType = iter.next();
                }
                var contactMechanismAliasTypeDetailValue = Objects.requireNonNull(defaultContactMechanismAliasType).getLastDetailForUpdate().getContactMechanismAliasTypeDetailValue().clone();

                contactMechanismAliasTypeDetailValue.setIsDefault(true);
                updateContactMechanismAliasTypeFromValue(contactMechanismAliasTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(contactMechanismAliasType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Contact Mechanism Alias Type Descriptions
    // --------------------------------------------------------------------------------

    public ContactMechanismAliasTypeDescription createContactMechanismAliasTypeDescription(ContactMechanismAliasType contactMechanismAliasType,
            Language language, String description, BasePK createdBy) {
        var contactMechanismAliasTypeDescription = ContactMechanismAliasTypeDescriptionFactory.getInstance().create(contactMechanismAliasType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(contactMechanismAliasType.getPrimaryKey(), EventTypes.MODIFY, contactMechanismAliasTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

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
        var contactMechanismAliasTypeDescription = getContactMechanismAliasTypeDescription(contactMechanismAliasType, language);

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
        var contactMechanismAliasTypeDescriptions = getContactMechanismAliasTypeDescriptionsByContactMechanismAliasType(contactMechanismAliasType);
        List<ContactMechanismAliasTypeDescriptionTransfer> contactMechanismAliasTypeDescriptionTransfers = new ArrayList<>(contactMechanismAliasTypeDescriptions.size());
        var contactMechanismAliasTypeDescriptionTransferCache = getContactTransferCaches(userVisit).getContactMechanismAliasTypeDescriptionTransferCache();

        contactMechanismAliasTypeDescriptions.forEach((contactMechanismAliasTypeDescription) ->
                contactMechanismAliasTypeDescriptionTransfers.add(contactMechanismAliasTypeDescriptionTransferCache.getContactMechanismAliasTypeDescriptionTransfer(contactMechanismAliasTypeDescription))
        );

        return contactMechanismAliasTypeDescriptionTransfers;
    }

    public void updateContactMechanismAliasTypeDescriptionFromValue(ContactMechanismAliasTypeDescriptionValue contactMechanismAliasTypeDescriptionValue, BasePK updatedBy) {
        if(contactMechanismAliasTypeDescriptionValue.hasBeenModified()) {
            var contactMechanismAliasTypeDescription = ContactMechanismAliasTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    contactMechanismAliasTypeDescriptionValue.getPrimaryKey());

            contactMechanismAliasTypeDescription.setThruTime(session.START_TIME_LONG);
            contactMechanismAliasTypeDescription.store();

            var contactMechanismAliasType = contactMechanismAliasTypeDescription.getContactMechanismAliasType();
            var language = contactMechanismAliasTypeDescription.getLanguage();
            var description = contactMechanismAliasTypeDescriptionValue.getDescription();

            contactMechanismAliasTypeDescription = ContactMechanismAliasTypeDescriptionFactory.getInstance().create(contactMechanismAliasType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(contactMechanismAliasType.getPrimaryKey(), EventTypes.MODIFY, contactMechanismAliasTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteContactMechanismAliasTypeDescription(ContactMechanismAliasTypeDescription contactMechanismAliasTypeDescription, BasePK deletedBy) {
        contactMechanismAliasTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(contactMechanismAliasTypeDescription.getContactMechanismAliasTypePK(), EventTypes.MODIFY, contactMechanismAliasTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteContactMechanismAliasTypeDescriptionsByContactMechanismAliasType(ContactMechanismAliasType contactMechanismAliasType, BasePK deletedBy) {
        var contactMechanismAliasTypeDescriptions = getContactMechanismAliasTypeDescriptionsByContactMechanismAliasTypeForUpdate(contactMechanismAliasType);

        contactMechanismAliasTypeDescriptions.forEach((contactMechanismAliasTypeDescription) -> 
                deleteContactMechanismAliasTypeDescription(contactMechanismAliasTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Contact Mechanism Purposes
    // --------------------------------------------------------------------------------
    
    public ContactMechanismPurpose createContactMechanismPurpose(String contactMechanismPurposeName,
            ContactMechanismType contactMechanismType, Boolean eventSubscriber, Boolean isDefault, Integer sortOrder) {
        return ContactMechanismPurposeFactory.getInstance().create(contactMechanismPurposeName, contactMechanismType,
                eventSubscriber, isDefault, sortOrder);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ContactMechanismPurpose */
    public ContactMechanismPurpose getContactMechanismPurposeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ContactMechanismPurposePK(entityInstance.getEntityUniqueId());
        var contactMechanismPurpose = ContactMechanismPurposeFactory.getInstance().getEntityFromPK(entityPermission, pk);

        return contactMechanismPurpose;
    }

    public ContactMechanismPurpose getContactMechanismPurposeByEntityInstance(EntityInstance entityInstance) {
        return getContactMechanismPurposeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ContactMechanismPurpose getContactMechanismPurposeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getContactMechanismPurposeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countContactMechanismPurposes() {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM contactmechanismpurposes
                """);
    }

    public ContactMechanismPurpose getContactMechanismPurposeByName(String contactMechanismPurposeName) {
        ContactMechanismPurpose contactMechanismPurpose;
        
        try {
            var ps = ContactMechanismPurposeFactory.getInstance().prepareStatement(
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
        var ps = ContactMechanismPurposeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM contactmechanismpurposes " +
                "ORDER BY cmpr_sortorder, cmpr_contactmechanismpurposename");
        
        return ContactMechanismPurposeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public List<ContactMechanismPurpose> getContactMechanismPurposesByContactMechanismType(ContactMechanismType contactMechanismType) {
        List<ContactMechanismPurpose> contactMechanismPurposes;
        
        try {
            var ps = ContactMechanismPurposeFactory.getInstance().prepareStatement(
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
        var contactMechanismPurposes = getContactMechanismPurposes();
        var size = contactMechanismPurposes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
        }
        
        for(var contactMechanismPurpose : contactMechanismPurposes) {
            var label = getBestContactMechanismPurposeDescription(contactMechanismPurpose, language) + " ("
                    + getBestContactMechanismTypeDescription(contactMechanismPurpose.getContactMechanismType(), language) + ")";
            var value = contactMechanismPurpose.getContactMechanismPurposeName();
            
            labels.add(label);
            values.add(value);
            
            var usingDefaultChoice = defaultContactMechanismPurposeChoice == null ? false : defaultContactMechanismPurposeChoice.equals(value);
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
        var contactMechanismPurposes = getContactMechanismPurposesByContactMechanismType(contactMechanismType);
        var size = contactMechanismPurposes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
        }
        
        for(var contactMechanismPurpose : contactMechanismPurposes) {
            var label = getBestContactMechanismPurposeDescription(contactMechanismPurpose, language);
            var value = contactMechanismPurpose.getContactMechanismPurposeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultContactMechanismPurposeChoice != null && defaultContactMechanismPurposeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && contactMechanismPurpose.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new ContactMechanismPurposeChoicesBean(labels, values, defaultValue);
    }
    
    public ContactMechanismPurposeChoicesBean getContactMechanismPurposeChoicesByContactList(String defaultContactListContactMechanismPurposeChoice, Language language, boolean allowNullChoice,
            ContactList contactList) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var contactListContactMechanismPurposes = contactListControl.getContactListContactMechanismPurposesByContactList(contactList);
        var size = contactListContactMechanismPurposes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultContactListContactMechanismPurposeChoice == null) {
                defaultValue = "";
            }
        }

        for(var contactListContactMechanismPurpose : contactListContactMechanismPurposes) {
            var contactListContactMechanismPurposeDetail = contactListContactMechanismPurpose.getLastDetail();
            var contactMechanismPurpose = contactListContactMechanismPurposeDetail.getContactMechanismPurpose();

            var label = getBestContactMechanismPurposeDescription(contactMechanismPurpose, language);
            var value = contactMechanismPurpose.getContactMechanismPurposeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultContactListContactMechanismPurposeChoice != null && defaultContactListContactMechanismPurposeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && contactListContactMechanismPurposeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ContactMechanismPurposeChoicesBean(labels, values, defaultValue);
    }
    
    public ContactMechanismPurposeTransfer getContactMechanismPurposeTransfer(UserVisit userVisit, ContactMechanismPurpose contactMechanismPurpose) {
        return getContactTransferCaches(userVisit).getContactMechanismPurposeTransferCache().getContactMechanismPurposeTransfer(contactMechanismPurpose);
    }
    
    public List<ContactMechanismPurposeTransfer> getContactMechanismPurposeTransfers(UserVisit userVisit, Collection<ContactMechanismPurpose> entities) {
        List<ContactMechanismPurposeTransfer> transfers = new ArrayList<>(entities.size());
        var cache = getContactTransferCaches(userVisit).getContactMechanismPurposeTransferCache();
        
        entities.forEach((entity) ->
                transfers.add(cache.getContactMechanismPurposeTransfer(entity))
        );
        
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
        ContactMechanismPurposeDescription contactMechanismPurposeDescription;
        
        try {
            var ps = ContactMechanismPurposeDescriptionFactory.getInstance().prepareStatement(
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
        var contactMechanismPurposeDescription = getContactMechanismPurposeDescription(contactMechanismPurpose, language);
        
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
        var contactMechanism = ContactMechanismFactory.getInstance().create();
        var contactMechanismDetail = ContactMechanismDetailFactory.getInstance().create(contactMechanism,
                contactMechanismName, contactMechanismType, allowSolicitation, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        contactMechanism = ContactMechanismFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                contactMechanism.getPrimaryKey());
        contactMechanism.setActiveDetail(contactMechanismDetail);
        contactMechanism.setLastDetail(contactMechanismDetail);
        contactMechanism.store();
        
        sendEvent(contactMechanism.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return contactMechanism;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ContactMechanism */
    public ContactMechanism getContactMechanismByEntityInstance(EntityInstance entityInstance) {
        var pk = new ContactMechanismPK(entityInstance.getEntityUniqueId());
        var contactMechanism = ContactMechanismFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
        
        return contactMechanism;
    }
    
    public long countContactMechanisms() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM contactmechanisms, contactmechanismdetails " +
                "WHERE cmch_activedetailid = cmchdt_contactmechanismdetailid");
    }

    public ContactMechanism getContactMechanismByName(String contactMechanismName, EntityPermission entityPermission) {
        ContactMechanism contactMechanism;
        
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

            var ps = ContactMechanismFactory.getInstance().prepareStatement(query);
            
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
        var contactMechanism = getContactMechanismByNameForUpdate(contactMechanismName);
        
        return contactMechanism == null? null: getContactMechanismDetailValue(contactMechanism.getLastDetailForUpdate());
    }
    
    public ContactMechanismTransfer getContactMechanismTransfer(UserVisit userVisit, ContactMechanism contactMechanism) {
        return getContactTransferCaches(userVisit).getContactMechanismTransferCache().getContactMechanismTransfer(contactMechanism);
    }
    
    public List<ContactMechanismTransfer> getContactMechanismTransfersByParty(UserVisit userVisit, Party party) {
        var partyContactMechanisms = getPartyContactMechanismsByParty(party);
        List<ContactMechanismTransfer> contactMechanismTransfers = new ArrayList<>(partyContactMechanisms.size());
        var contactMechanismTransferCache = getContactTransferCaches(userVisit).getContactMechanismTransferCache();
        
        partyContactMechanisms.forEach((partyContactMechanism) -> {
            contactMechanismTransfers.add(contactMechanismTransferCache.getContactMechanismTransfer(partyContactMechanism.getLastDetail().getContactMechanism()));
        });
        
        return contactMechanismTransfers;
    }
    
    public EmailAddressStatusChoicesBean getEmailAddressStatusChoices(String defaultEmailAddressStatusChoice, Language language, boolean allowNullChoice, ContactMechanism contactMechanism,
            PartyPK partyPK) {
        var workflowControl = getWorkflowControl();
        var emailAddressStatusChoicesBean = new EmailAddressStatusChoicesBean();
        var entityInstance = getEntityInstanceByBaseEntity(contactMechanism);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(EmailAddressStatusConstants.Workflow_EMAIL_ADDRESS_STATUS,
                entityInstance);
        
        workflowControl.getWorkflowDestinationChoices(emailAddressStatusChoicesBean, defaultEmailAddressStatusChoice, language, allowNullChoice, workflowEntityStatus.getWorkflowStep(), partyPK);
        
        return emailAddressStatusChoicesBean;
    }
    
    public void setEmailAddressStatus(ExecutionErrorAccumulator eea,  ContactMechanism contactMechanism, String emailAddressStatusChoice, PartyPK modifiedBy) {
        var workflowControl = getWorkflowControl();
        var entityInstance = getEntityInstanceByBaseEntity(contactMechanism);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(EmailAddressStatusConstants.Workflow_EMAIL_ADDRESS_STATUS,
                entityInstance);
        var workflowDestination = emailAddressStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), emailAddressStatusChoice);
        
        if(workflowDestination != null || emailAddressStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownEmailAddressStatusChoice.name(), emailAddressStatusChoice);
        }
    }
    
    public EmailAddressVerificationChoicesBean getEmailAddressVerificationChoices(String defaultEmailAddressVerificationChoice, Language language, boolean allowNullChoice, ContactMechanism contactMechanism,
            PartyPK partyPK) {
        var workflowControl = getWorkflowControl();
        var emailAddressVerificationChoicesBean = new EmailAddressVerificationChoicesBean();
        var entityInstance = getEntityInstanceByBaseEntity(contactMechanism);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(EmailAddressVerificationConstants.Workflow_EMAIL_ADDRESS_VERIFICATION,
                entityInstance);
        
        workflowControl.getWorkflowDestinationChoices(emailAddressVerificationChoicesBean, defaultEmailAddressVerificationChoice, language, allowNullChoice, workflowEntityStatus.getWorkflowStep(), partyPK);
        
        return emailAddressVerificationChoicesBean;
    }
    
    public void setEmailAddressVerification(ExecutionErrorAccumulator eea,  ContactMechanism contactMechanism, String emailAddressVerificationChoice, PartyPK modifiedBy) {
        var workflowControl = getWorkflowControl();
        var entityInstance = getEntityInstanceByBaseEntity(contactMechanism);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(EmailAddressVerificationConstants.Workflow_EMAIL_ADDRESS_VERIFICATION,
                entityInstance);
        var workflowDestination = emailAddressVerificationChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), emailAddressVerificationChoice);
        
        if(workflowDestination != null || emailAddressVerificationChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownEmailAddressVerificationChoice.name(), emailAddressVerificationChoice);
        }
    }
    
    public PostalAddressStatusChoicesBean getPostalAddressStatusChoices(String defaultPostalAddressStatusChoice, Language language, boolean allowNullChoice, ContactMechanism contactMechanism,
            PartyPK partyPK) {
        var workflowControl = getWorkflowControl();
        var postalAddressStatusChoicesBean = new PostalAddressStatusChoicesBean();
        var entityInstance = getEntityInstanceByBaseEntity(contactMechanism);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(PostalAddressStatusConstants.Workflow_POSTAL_ADDRESS_STATUS,
                entityInstance);
        
        workflowControl.getWorkflowDestinationChoices(postalAddressStatusChoicesBean, defaultPostalAddressStatusChoice, language, allowNullChoice, workflowEntityStatus.getWorkflowStep(), partyPK);
        
        return postalAddressStatusChoicesBean;
    }
    
    public void setPostalAddressStatus(ExecutionErrorAccumulator eea,  ContactMechanism contactMechanism, String postalAddressStatusChoice, PartyPK modifiedBy) {
        var workflowControl = getWorkflowControl();
        var entityInstance = getEntityInstanceByBaseEntity(contactMechanism);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(PostalAddressStatusConstants.Workflow_POSTAL_ADDRESS_STATUS,
                entityInstance);
        var workflowDestination = postalAddressStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), postalAddressStatusChoice);
        
        if(workflowDestination != null || postalAddressStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownPostalAddressStatusChoice.name(), postalAddressStatusChoice);
        }
    }
    
    public TelephoneStatusChoicesBean getTelephoneStatusChoices(String defaultTelephoneStatusChoice, Language language, boolean allowNullChoice, ContactMechanism contactMechanism,
            PartyPK partyPK) {
        var workflowControl = getWorkflowControl();
        var telephoneStatusChoicesBean = new TelephoneStatusChoicesBean();
        var entityInstance = getEntityInstanceByBaseEntity(contactMechanism);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(TelephoneStatusConstants.Workflow_TELEPHONE_STATUS,
                entityInstance);
        
        workflowControl.getWorkflowDestinationChoices(telephoneStatusChoicesBean, defaultTelephoneStatusChoice, language, allowNullChoice, workflowEntityStatus.getWorkflowStep(), partyPK);
        
        return telephoneStatusChoicesBean;
    }
    
    public void setTelephoneStatus(ExecutionErrorAccumulator eea,  ContactMechanism contactMechanism, String telephoneStatusChoice, PartyPK modifiedBy) {
        var workflowControl = getWorkflowControl();
        var entityInstance = getEntityInstanceByBaseEntity(contactMechanism);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(TelephoneStatusConstants.Workflow_TELEPHONE_STATUS,
                entityInstance);
        var workflowDestination = telephoneStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), telephoneStatusChoice);
        
        if(workflowDestination != null || telephoneStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownTelephoneStatusChoice.name(), telephoneStatusChoice);
        }
    }
    
    public WebAddressStatusChoicesBean getWebAddressStatusChoices(String defaultWebAddressStatusChoice, Language language, boolean allowNullChoice, ContactMechanism contactMechanism,
            PartyPK partyPK) {
        var workflowControl = getWorkflowControl();
        var webAddressStatusChoicesBean = new WebAddressStatusChoicesBean();
        var entityInstance = getEntityInstanceByBaseEntity(contactMechanism);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(WebAddressStatusConstants.Workflow_WEB_ADDRESS_STATUS,
                entityInstance);
        
        workflowControl.getWorkflowDestinationChoices(webAddressStatusChoicesBean, defaultWebAddressStatusChoice, language, allowNullChoice, workflowEntityStatus.getWorkflowStep(), partyPK);
        
        return webAddressStatusChoicesBean;
    }
    
    public void setWebAddressStatus(ExecutionErrorAccumulator eea,  ContactMechanism contactMechanism, String webAddressStatusChoice, PartyPK modifiedBy) {
        var workflowControl = getWorkflowControl();
        var entityInstance = getEntityInstanceByBaseEntity(contactMechanism);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(WebAddressStatusConstants.Workflow_WEB_ADDRESS_STATUS,
                entityInstance);
        var workflowDestination = webAddressStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), webAddressStatusChoice);
        
        if(workflowDestination != null || webAddressStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownWebAddressStatusChoice.name(), webAddressStatusChoice);
        }
    }
    
    public void updateContactMechanismFromValue(ContactMechanismDetailValue contactMechanismDetailValue,  BasePK updatedBy) {
        if(contactMechanismDetailValue.hasBeenModified()) {
            var contactMechanism = ContactMechanismFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     contactMechanismDetailValue.getContactMechanismPK());
            var contactMechanismDetail = contactMechanism.getActiveDetailForUpdate();
            
            contactMechanismDetail.setThruTime(session.START_TIME_LONG);
            contactMechanismDetail.store();

            var contactMechanismPK = contactMechanismDetail.getContactMechanismPK(); // Not updated
            var contactMechanismName = contactMechanismDetailValue.getContactMechanismName();
            var contactMechanismTypePK = contactMechanismDetail.getContactMechanismTypePK(); // Not updated
            var allowSolicitation = contactMechanismDetailValue.getAllowSolicitation();
            
            contactMechanismDetail = ContactMechanismDetailFactory.getInstance().create(contactMechanismPK,
                    contactMechanismName, contactMechanismTypePK, allowSolicitation, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            contactMechanism.setActiveDetail(contactMechanismDetail);
            contactMechanism.setLastDetail(contactMechanismDetail);
            
            sendEvent(contactMechanismPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteContactMechanism(ContactMechanism contactMechanism, BasePK deletedBy) {
        deletePartyContactMechanismsByContactMechanism(contactMechanism, deletedBy);
        deletePartyContactMechanismAliasesByContactMechanism(contactMechanism, deletedBy);
        deleteContactMechanismAliasesByContactMechanism(contactMechanism, deletedBy);

        var contactMechanismDetail = contactMechanism.getLastDetailForUpdate();
        contactMechanismDetail.setThruTime(session.START_TIME_LONG);
        contactMechanism.setActiveDetail(null);
        contactMechanism.store();

        var contactMechanismTypeName = contactMechanismDetail.getContactMechanismType().getContactMechanismTypeName();
        if(contactMechanismTypeName.equals(ContactMechanismTypes.EMAIL_ADDRESS.name())) {
            deleteContactEmailAddressByContactMechanism(contactMechanism, deletedBy);
        } else if(contactMechanismTypeName.equals(ContactMechanismTypes.INET_4.name())) {
            deleteContactInet4AddressByContactMechanism(contactMechanism, deletedBy);
        } else if(contactMechanismTypeName.equals(ContactMechanismTypes.INET_6.name())) {
            deleteContactInet6AddressByContactMechanism(contactMechanism, deletedBy);
        } else if(contactMechanismTypeName.equals(ContactMechanismTypes.POSTAL_ADDRESS.name())) {
            deleteContactPostalAddressByContactMechanism(contactMechanism, deletedBy);
            deleteContactPostalAddressCorrectionByContactMechanism(contactMechanism, deletedBy);
        } else if(contactMechanismTypeName.equals(ContactMechanismTypes.TELECOM_ADDRESS.name())) {
            deleteContactTelephoneByContactMechanism(contactMechanism, deletedBy);
        } else if(contactMechanismTypeName.equals(ContactMechanismTypes.WEB_ADDRESS.name())) {
            deleteContactWebAddressByContactMechanism(contactMechanism, deletedBy);
        }
        
        sendEvent(contactMechanism.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Aliases
    // --------------------------------------------------------------------------------
    
    public ContactMechanismAlias createContactMechanismAlias(ContactMechanism contactMechanism,
            ContactMechanismAliasType contactMechanismAliasType, String alias, BasePK createdBy) {

        var contactMechanismAlias = ContactMechanismAliasFactory.getInstance().create(contactMechanism,
                contactMechanismAliasType, alias, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(contactMechanism.getPrimaryKey(), EventTypes.MODIFY, contactMechanismAlias.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contactMechanismAlias;
    }
    
    private List<ContactMechanismAlias> getContactMechanismAliasesByContactMechanism(ContactMechanism contactMechanism,
            EntityPermission entityPermission) {
        List<ContactMechanismAlias> contactMechanismAliases;

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

            var ps = ContactMechanismAliasFactory.getInstance().prepareStatement(query);

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
        List<ContactMechanismAlias> contactMechanismAliases;

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

            var ps = ContactMechanismAliasFactory.getInstance().prepareStatement(query);

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
        ContactMechanismAlias contactMechanismAlias;
        
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

            var ps = ContactMechanismAliasFactory.getInstance().prepareStatement(query);
            
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
        var entities = getContactMechanismAliasesByContactMechanism(contactMechanism);
        List<ContactMechanismAliasTransfer> transfers = new ArrayList<>(entities.size());
        var cache = getContactTransferCaches(userVisit).getContactMechanismAliasTransferCache();
        
        entities.forEach((entity) ->
                transfers.add(cache.getContactMechanismAliasTransfer(entity))
        );
        
        return transfers;
    }
    
    public void deleteContactMechanismAlias(ContactMechanismAlias contactMechanismAlias, BasePK deletedBy) {
        contactMechanismAlias.setThruTime(session.START_TIME_LONG);
        
        sendEvent(contactMechanismAlias.getContactMechanismPK(), EventTypes.MODIFY, contactMechanismAlias.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteContactMechanismAliases(List<ContactMechanismAlias> contactMechanismAliases, BasePK deletedBy) {
        contactMechanismAliases.forEach((contactMechanismAlias) -> 
                deleteContactMechanismAlias(contactMechanismAlias, deletedBy)
        );
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
        var contactEmailAddress = ContactEmailAddressFactory.getInstance().create(contactMechanism,
                emailAddress, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(contactMechanism.getPrimaryKey(), EventTypes.MODIFY, contactEmailAddress.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contactEmailAddress;
    }
    
    private ContactEmailAddress getContactEmailAddress(ContactMechanism contactMechanism, EntityPermission entityPermission) {
        ContactEmailAddress contactEmailAddress;
        
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

            var ps = ContactEmailAddressFactory.getInstance().prepareStatement(query);
            
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
            var contactEmailAddress = ContactEmailAddressFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contactEmailAddressValue.getPrimaryKey());
            
            contactEmailAddress.setThruTime(session.START_TIME_LONG);
            contactEmailAddress.store();

            var contactMechanismPK = contactEmailAddress.getContactMechanismPK(); // Not updated
            var emailAddress = contactEmailAddressValue.getEmailAddress();
            
            contactEmailAddress = ContactEmailAddressFactory.getInstance().create(contactMechanismPK, emailAddress,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(contactMechanismPK, EventTypes.MODIFY, contactEmailAddress.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteContactEmailAddress(ContactEmailAddress contactEmailAddress, BasePK deletedBy) {
        contactEmailAddress.setThruTime(session.START_TIME_LONG);
        
        sendEvent(contactEmailAddress.getContactMechanismPK(), EventTypes.MODIFY, contactEmailAddress.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteContactEmailAddressByContactMechanism(ContactMechanism contactMechanism, BasePK deletedBy) {
        deleteContactEmailAddress(getContactEmailAddressForUpdate(contactMechanism), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Inet 4 Addresses
    // --------------------------------------------------------------------------------
    
    public ContactInet4Address createContactInet4Address(ContactMechanism contactMechanism, Integer inet4Address, BasePK createdBy) {
        var contactInet4Address = ContactInet4AddressFactory.getInstance().create(contactMechanism,
                inet4Address, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(contactMechanism.getPrimaryKey(), EventTypes.MODIFY, contactInet4Address.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contactInet4Address;
    }
    
    private ContactInet4Address getContactInet4Address(ContactMechanism contactMechanism, EntityPermission entityPermission) {
        ContactInet4Address contactInet4Address;
        
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

            var ps = session.prepareStatement(ContactInet4AddressFactory.class, query);
            
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
            var contactInet4Address = ContactInet4AddressFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contactInet4AddressValue.getPrimaryKey());
            
            contactInet4Address.setThruTime(session.START_TIME_LONG);
            contactInet4Address.store();

            var contactMechanismPK = contactInet4Address.getContactMechanismPK(); // Not updated
            var inet4Address = contactInet4Address.getInet4Address();
            
            contactInet4Address = ContactInet4AddressFactory.getInstance().create(contactMechanismPK, inet4Address,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(contactMechanismPK, EventTypes.MODIFY, contactInet4Address.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteContactInet4Address(ContactInet4Address contactInet4Address, BasePK deletedBy) {
        contactInet4Address.setThruTime(session.START_TIME_LONG);
        
        sendEvent(contactInet4Address.getContactMechanismPK(), EventTypes.MODIFY, contactInet4Address.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteContactInet4AddressByContactMechanism(ContactMechanism contactMechanism, BasePK deletedBy) {
        deleteContactInet4Address(getContactInet4AddressForUpdate(contactMechanism), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Inet 6 Addresses
    // --------------------------------------------------------------------------------
    
    public ContactInet6Address createContactInet6Address(ContactMechanism contactMechanism, Long inet6AddressLow,
            Long inet6AddressHigh, BasePK createdBy) {
        var contactInet6Address = ContactInet6AddressFactory.getInstance().create(contactMechanism,
                inet6AddressLow, inet6AddressHigh, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(contactMechanism.getPrimaryKey(), EventTypes.MODIFY, contactInet6Address.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contactInet6Address;
    }
    
    private ContactInet6Address getContactInet6Address(ContactMechanism contactMechanism, EntityPermission entityPermission) {
        ContactInet6Address contactInet6Address;
        
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

            var ps = session.prepareStatement(ContactInet6AddressFactory.class, query);
            
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
            var contactInet6Address = ContactInet6AddressFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contactInet6AddressValue.getPrimaryKey());
            
            contactInet6Address.setThruTime(session.START_TIME_LONG);
            contactInet6Address.store();

            var contactMechanismPK = contactInet6Address.getContactMechanismPK(); // Not updated
            var inet6AddressLow = contactInet6Address.getInet6AddressLow();
            var inet6AddressHigh = contactInet6Address.getInet6AddressHigh();
            
            contactInet6Address = ContactInet6AddressFactory.getInstance().create(contactMechanismPK, inet6AddressLow,
                    inet6AddressHigh, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(contactMechanismPK, EventTypes.MODIFY, contactInet6Address.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteContactInet6Address(ContactInet6Address contactInet6Address, BasePK deletedBy) {
        contactInet6Address.setThruTime(session.START_TIME_LONG);
        
        sendEvent(contactInet6Address.getContactMechanismPK(), EventTypes.MODIFY, contactInet6Address.getPrimaryKey(), EventTypes.DELETE, deletedBy);
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
        var contactPostalAddress = ContactPostalAddressFactory.getInstance().create(contactMechanism,
                personalTitle, firstName, firstNameSdx, middleName, middleNameSdx, lastName, lastNameSdx, nameSuffix, companyName, attention,
                address1, address2, address3, city, cityGeoCode, countyGeoCode, state, stateGeoCode, postalCode, postalCodeGeoCode,
                countryGeoCode, isCommercial, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(contactMechanism.getPrimaryKey(), EventTypes.MODIFY, contactPostalAddress.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contactPostalAddress;
    }

    public long countContactPostalAddressesByPersonalTitle(PersonalTitle personalTitle) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM contactpostaladdresses " +
                "WHERE ctpa_pert_personaltitleid = ? AND ctpa_thrutime = ?",
                personalTitle, Session.MAX_TIME_LONG);
    }

    public long countContactPostalAddressesByNameSuffix(NameSuffix nameSuffix) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM contactpostaladdresses " +
                "WHERE ctpa_nsfx_namesuffixid = ? AND ctpa_thrutime = ?",
                nameSuffix, Session.MAX_TIME_LONG);
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
        ContactPostalAddress contactPostalAddress;
        
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

            var ps = ContactPostalAddressFactory.getInstance().prepareStatement(query);
            
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
            var contactPostalAddress = ContactPostalAddressFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contactPostalAddressValue.getPrimaryKey());
            
            contactPostalAddress.setThruTime(session.START_TIME_LONG);
            contactPostalAddress.store();

            var contactMechanismPK = contactPostalAddress.getContactMechanismPK(); // Not updated
            var personalTitlePK = contactPostalAddressValue.getPersonalTitlePK();
            var firstName = contactPostalAddressValue.getFirstName();
            var firstNameSdx = contactPostalAddressValue.getFirstNameSdx();
            var middleName = contactPostalAddressValue.getMiddleName();
            var middleNameSdx = contactPostalAddressValue.getMiddleNameSdx();
            var lastName = contactPostalAddressValue.getLastName();
            var lastNameSdx = contactPostalAddressValue.getLastNameSdx();
            var nameSuffixPK = contactPostalAddressValue.getNameSuffixPK();
            var companyName = contactPostalAddressValue.getCompanyName();
            var attention = contactPostalAddressValue.getAttention();
            var address1 = contactPostalAddressValue.getAddress1();
            var address2 = contactPostalAddressValue.getAddress2();
            var address3 = contactPostalAddressValue.getAddress3();
            var city = contactPostalAddressValue.getCity();
            var cityGeoCodePK = contactPostalAddressValue.getCityGeoCodePK();
            var countyGeoCodePK = contactPostalAddressValue.getCountyGeoCodePK();
            var state = contactPostalAddressValue.getState();
            var stateGeoCodePK = contactPostalAddressValue.getStateGeoCodePK();
            var postalCode = contactPostalAddressValue.getPostalCode();
            var postalCodeGeoCodePK = contactPostalAddressValue.getPostalCodeGeoCodePK();
            var countryGeoCodePK = contactPostalAddressValue.getCountryGeoCodePK();
            var isCommercial = contactPostalAddressValue.getIsCommercial();
            
            contactPostalAddress = ContactPostalAddressFactory.getInstance().create(contactMechanismPK, personalTitlePK,
                    firstName, firstNameSdx, middleName, middleNameSdx, lastName, lastNameSdx, nameSuffixPK, companyName, attention, address1,
                    address2, address3, city, cityGeoCodePK, countyGeoCodePK, state, stateGeoCodePK, postalCode, postalCodeGeoCodePK,
                    countryGeoCodePK, isCommercial, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(contactMechanismPK, EventTypes.MODIFY, contactPostalAddress.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteContactPostalAddress(ContactPostalAddress contactPostalAddress, BasePK deletedBy) {
        contactPostalAddress.setThruTime(session.START_TIME_LONG);
        
        sendEvent(contactPostalAddress.getContactMechanismPK(), EventTypes.MODIFY, contactPostalAddress.getPrimaryKey(), EventTypes.DELETE, deletedBy);
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
        var contactPostalAddressCorrection = ContactPostalAddressCorrectionFactory.getInstance().create(session,
                contactMechanism, address1, address2, address3, city, cityGeoCode, countyGeoCode, state, stateGeoCode, postalCode,
                postalCodeGeoCode, countryGeoCode, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(contactMechanism.getPrimaryKey(), EventTypes.MODIFY, contactPostalAddressCorrection.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
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
        ContactPostalAddressCorrection contactPostalAddressCorrection;
        
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

            var ps = ContactPostalAddressCorrectionFactory.getInstance().prepareStatement(query);
            
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
            var contactPostalAddressCorrection = ContactPostalAddressCorrectionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contactPostalAddressCorrectionValue.getPrimaryKey());
            
            contactPostalAddressCorrection.setThruTime(session.START_TIME_LONG);
            contactPostalAddressCorrection.store();

            var contactMechanismPK = contactPostalAddressCorrection.getContactMechanismPK(); // Not updated
            var address1 = contactPostalAddressCorrectionValue.getAddress1();
            var address2 = contactPostalAddressCorrectionValue.getAddress2();
            var address3 = contactPostalAddressCorrectionValue.getAddress3();
            var city = contactPostalAddressCorrectionValue.getCity();
            var cityGeoCodePK = contactPostalAddressCorrectionValue.getCityGeoCodePK();
            var countyGeoCodePK = contactPostalAddressCorrectionValue.getCountyGeoCodePK();
            var state = contactPostalAddressCorrectionValue.getState();
            var stateGeoCodePK = contactPostalAddressCorrectionValue.getStateGeoCodePK();
            var postalCode = contactPostalAddressCorrectionValue.getPostalCode();
            var postalCodeGeoCodePK = contactPostalAddressCorrectionValue.getPostalCodeGeoCodePK();
            var countryGeoCodePK = contactPostalAddressCorrectionValue.getCountryGeoCodePK();
            
            contactPostalAddressCorrection = ContactPostalAddressCorrectionFactory.getInstance().create(contactMechanismPK,
                    address1, address2, address3, city, cityGeoCodePK, countyGeoCodePK, state, stateGeoCodePK, postalCode,
                    postalCodeGeoCodePK, countryGeoCodePK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(contactMechanismPK, EventTypes.MODIFY, contactPostalAddressCorrection.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteContactPostalAddressCorrection(ContactPostalAddressCorrection contactPostalAddressCorrection, BasePK deletedBy) {
        contactPostalAddressCorrection.setThruTime(session.START_TIME_LONG);
        
        sendEvent(contactPostalAddressCorrection.getContactMechanismPK(), EventTypes.MODIFY, contactPostalAddressCorrection.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteContactPostalAddressCorrectionByContactMechanism(ContactMechanism contactMechanism, BasePK deletedBy) {
        var contactPostalAddressCorrection = getContactPostalAddressCorrectionForUpdate(contactMechanism);
        
        if(contactPostalAddressCorrection != null) {
            deleteContactPostalAddressCorrection(contactPostalAddressCorrection, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Telephones
    // --------------------------------------------------------------------------------
    
    public ContactTelephone createContactTelephone(ContactMechanism contactMechanism, GeoCode countryGeoCode, String areaCode,
            String telephoneNumber, String telephoneExtension, BasePK createdBy) {
        var contactTelephone = ContactTelephoneFactory.getInstance().create(contactMechanism, countryGeoCode,
                areaCode, telephoneNumber, telephoneExtension, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(contactMechanism.getPrimaryKey(), EventTypes.MODIFY, contactTelephone.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
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
        ContactTelephone contactTelephone;
        
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

            var ps = ContactTelephoneFactory.getInstance().prepareStatement(query);
            
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
            var contactTelephone = ContactTelephoneFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contactTelephoneValue.getPrimaryKey());
            
            contactTelephone.setThruTime(session.START_TIME_LONG);
            contactTelephone.store();

            var contactMechanismPK = contactTelephone.getContactMechanismPK(); // Not updated
            var countryGeoCodePK = contactTelephoneValue.getCountryGeoCodePK();
            var areaCode = contactTelephoneValue.getAreaCode();
            var telephoneNumber = contactTelephoneValue.getTelephoneNumber();
            var telephoneExtension = contactTelephoneValue.getTelephoneExtension();
            
            contactTelephone = ContactTelephoneFactory.getInstance().create(contactMechanismPK, countryGeoCodePK,
                    areaCode, telephoneNumber, telephoneExtension, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(contactMechanismPK, EventTypes.MODIFY, contactTelephone.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteContactTelephone(ContactTelephone contactTelephone, BasePK deletedBy) {
        contactTelephone.setThruTime(session.START_TIME_LONG);
        
        sendEvent(contactTelephone.getContactMechanismPK(), EventTypes.MODIFY, contactTelephone.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteContactTelephoneByContactMechanism(ContactMechanism contactMechanism, BasePK deletedBy) {
        deleteContactTelephone(getContactTelephoneForUpdate(contactMechanism), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Web Addresses
    // --------------------------------------------------------------------------------
    
    public ContactWebAddress createContactWebAddress(ContactMechanism contactMechanism, String url, BasePK createdBy) {
        var contactWebAddress = ContactWebAddressFactory.getInstance().create(contactMechanism, url,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(contactMechanism.getPrimaryKey(), EventTypes.MODIFY, contactWebAddress.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contactWebAddress;
    }
    
    private ContactWebAddress getContactWebAddress(ContactMechanism contactMechanism, EntityPermission entityPermission) {
        ContactWebAddress contactWebAddress;
        
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

            var ps = ContactWebAddressFactory.getInstance().prepareStatement(query);
            
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
            var contactWebAddress = ContactWebAddressFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contactWebAddressValue.getPrimaryKey());
            
            contactWebAddress.setThruTime(session.START_TIME_LONG);
            contactWebAddress.store();

            var contactMechanismPK = contactWebAddress.getContactMechanismPK(); // Not updated
            var url = contactWebAddressValue.getUrl();
            
            contactWebAddress = ContactWebAddressFactory.getInstance().create(contactMechanismPK, url, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEvent(contactMechanismPK, EventTypes.MODIFY, contactWebAddress.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteContactWebAddress(ContactWebAddress contactWebAddress, BasePK deletedBy) {
        contactWebAddress.setThruTime(session.START_TIME_LONG);
        
        sendEvent(contactWebAddress.getContactMechanismPK(), EventTypes.MODIFY, contactWebAddress.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteContactWebAddressByContactMechanism(ContactMechanism contactMechanism, BasePK deletedBy) {
        deleteContactWebAddress(getContactWebAddressForUpdate(contactMechanism), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Contact Mechanisms
    // --------------------------------------------------------------------------------
    
    public PartyContactMechanism createPartyContactMechanism(Party party, ContactMechanism contactMechanism, String description,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultPartyContactMechanism = getDefaultPartyContactMechanism(party);
        var defaultFound = defaultPartyContactMechanism != null;
        
        if(defaultFound && isDefault) {
            var defaultPartyContactMechanismDetailValue = getDefaultPartyContactMechanismDetailValueForUpdate(party);
            
            defaultPartyContactMechanismDetailValue.setIsDefault(false);
            updatePartyContactMechanismFromValue(defaultPartyContactMechanismDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var partyContactMechanism = PartyContactMechanismFactory.getInstance().create();
        var partyContactMechanismDetail = PartyContactMechanismDetailFactory.getInstance().create(session,
                partyContactMechanism, party, contactMechanism, description, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        partyContactMechanism = PartyContactMechanismFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, partyContactMechanism.getPrimaryKey());
        partyContactMechanism.setActiveDetail(partyContactMechanismDetail);
        partyContactMechanism.setLastDetail(partyContactMechanismDetail);
        partyContactMechanism.store();
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyContactMechanism.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyContactMechanism;
    }

    public long countPartyContactMechanismsByParty(Party party) {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM partycontactmechanisms
                        JOIN partycontactmechanismdetails ON pcm_activedetailid = pcmdt_partycontactmechanismdetailid
                        WHERE pcmdt_par_partyid = ?
                        """, party);
    }

    private PartyContactMechanism getDefaultPartyContactMechanism(Party party, EntityPermission entityPermission) {
        PartyContactMechanism partyContactMechanism;
        
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

            var ps = PartyContactMechanismFactory.getInstance().prepareStatement(query);
            
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
    
    public PartyContactMechanism getPartyContactMechanism(Party party, ContactMechanism contactMechanism,
            EntityPermission entityPermission) {
        PartyContactMechanism partyContactMechanism;
        
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

            var ps = PartyContactMechanismFactory.getInstance().prepareStatement(query);
            
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
        List<PartyContactMechanism> partyContactMechanism;
        
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

            var ps = PartyContactMechanismFactory.getInstance().prepareStatement(query);
            
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
        List<PartyContactMechanism> partyContactMechanism;
        
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

            var ps = PartyContactMechanismFactory.getInstance().prepareStatement(query);
            
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
        List<PartyContactMechanism> partyContactMechanism;
        
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

            var ps = PartyContactMechanismFactory.getInstance().prepareStatement(query);
            
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
        var contactMechanism = getContactMechanismByName(contactMechanismName);
        
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
        var partyContactMechanisms = getPartyContactMechanismsByParty(party);
        var size = partyContactMechanisms.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");
        }

        for(var partyContactMechanism : partyContactMechanisms) {
            var partyContactMechanismDetail = partyContactMechanism.getLastDetail();
            var label = partyContactMechanismDetail.getDescription();
            var value = partyContactMechanismDetail.getContactMechanism().getLastDetail().getContactMechanismName();

            labels.add(label == null ? value : label);
            values.add(value);

            // Default choice isn't handled well, there may be no isDefault set since we're narrowing the complete list by type.
            var usingDefaultChoice = defaultContactMechanismChoice == null ? false : defaultContactMechanismChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && partyContactMechanismDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ContactMechanismChoicesBean(labels, values, defaultValue);
    }

    public ContactMechanismChoicesBean getContactMechanismChoicesByPartyAndContactMechanismType(Party party, ContactMechanismType contactMechanismType,
            String defaultContactMechanismChoice, Language language, boolean allowNullChoice) {
        var partyContactMechanisms = getPartyContactMechanismsByPartyAndContactMechanismType(party, contactMechanismType);
        var size = partyContactMechanisms.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");
        }

        for(var partyContactMechanism : partyContactMechanisms) {
            var partyContactMechanismDetail = partyContactMechanism.getLastDetail();
            var label = partyContactMechanismDetail.getDescription();
            var value = partyContactMechanismDetail.getContactMechanism().getLastDetail().getContactMechanismName();

            labels.add(label == null ? value : label);
            values.add(value);

            // Default choice isn't handled well, there may be no isDefault set since we're narrowing the complete list by type.
            var usingDefaultChoice = defaultContactMechanismChoice == null ? false : defaultContactMechanismChoice.equals(value);
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
        var entities = getPartyContactMechanismsByParty(party);
        List<PartyContactMechanismTransfer> transfers = new ArrayList<>(entities.size());
        var cache = getContactTransferCaches(userVisit).getPartyContactMechanismTransferCache();
        
        entities.forEach((entity) ->
                transfers.add(cache.getPartyContactMechanismTransfer(entity))
        );
        
        return transfers;
    }
    
    private void updatePartyContactMechanismFromValue(PartyContactMechanismDetailValue partyContactMechanismDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(partyContactMechanismDetailValue.hasBeenModified()) {
            var partyContactMechanism = PartyContactMechanismFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyContactMechanismDetailValue.getPartyContactMechanismPK());
            var partyContactMechanismDetail = partyContactMechanism.getActiveDetailForUpdate();
            
            partyContactMechanismDetail.setThruTime(session.START_TIME_LONG);
            partyContactMechanismDetail.store();

            var partyContactMechanismPK = partyContactMechanismDetail.getPartyContactMechanismPK();
            var party = partyContactMechanismDetail.getParty(); // Not updated
            var partyPK = party.getPrimaryKey();
            var contactMechanismPK = partyContactMechanismDetail.getContactMechanismPK(); // Not updated
            var description = partyContactMechanismDetailValue.getDescription();
            var isDefault = partyContactMechanismDetailValue.getIsDefault();
            var sortOrder = partyContactMechanismDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultPartyContactMechanism = getDefaultPartyContactMechanism(party);
                var defaultFound = defaultPartyContactMechanism != null && !defaultPartyContactMechanism.equals(partyContactMechanism);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPartyContactMechanismDetailValue = getDefaultPartyContactMechanismDetailValueForUpdate(party);
                    
                    defaultPartyContactMechanismDetailValue.setIsDefault(false);
                    updatePartyContactMechanismFromValue(defaultPartyContactMechanismDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            partyContactMechanismDetail = PartyContactMechanismDetailFactory.getInstance().create(partyContactMechanismPK,
                    partyPK, contactMechanismPK, description, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            partyContactMechanism.setActiveDetail(partyContactMechanismDetail);
            partyContactMechanism.setLastDetail(partyContactMechanismDetail);
            
            sendEvent(partyPK, EventTypes.MODIFY, partyContactMechanism.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updatePartyContactMechanismFromValue(PartyContactMechanismDetailValue partyContactMechanismDetailValue, BasePK updatedBy) {
        updatePartyContactMechanismFromValue(partyContactMechanismDetailValue, true, updatedBy);
    }
    
    public void deletePartyContactMechanism(PartyContactMechanism partyContactMechanism, BasePK deletedBy) {
        var associateControl = Session.getModelController(AssociateControl.class);
        var billingControl = Session.getModelController(BillingControl.class);
        var communicationControl = Session.getModelController(CommunicationControl.class);
        var invoiceControl = Session.getModelController(InvoiceControl.class);
        var letterControl = Session.getModelController(LetterControl.class);
        var orderShipmentGroupControl = Session.getModelController(OrderShipmentGroupControl.class);
        var partyPaymentMethodControl = Session.getModelController(PartyPaymentMethodControl.class);
        var shipmentControl = Session.getModelController(ShipmentControl.class);
        
        deletePartyContactMechanismPurposesByPartyContactMechanism(partyContactMechanism, deletedBy);
        deletePartyContactMechanismRelationshipsByPartyContactMechanism(partyContactMechanism, deletedBy);
        
        associateControl.deleteAssociatePartyContactMechanismsByPartyContactMechanism(partyContactMechanism, deletedBy);
        billingControl.deleteBillingAccountRolesByPartyContactMechanism(partyContactMechanism, deletedBy);
        communicationControl.deleteCommunicationEventsByPartyContactMechanism(partyContactMechanism, deletedBy);
        invoiceControl.deleteInvoiceRolesByPartyContactMechanism(partyContactMechanism, deletedBy);
        letterControl.deleteLetterSourcesByPartyContactMechanism(partyContactMechanism, deletedBy);
        orderShipmentGroupControl.deleteOrderShipmentGroupsByPartyContactMechanism(partyContactMechanism, deletedBy);
        partyPaymentMethodControl.deletePartyPaymentMethodCreditCardsByPartyContactMechanism(partyContactMechanism, deletedBy);
        shipmentControl.deleteShipmentsByPartyContactMechanism(partyContactMechanism, deletedBy);

        var partyContactMechanismDetail = partyContactMechanism.getLastDetailForUpdate();
        partyContactMechanismDetail.setThruTime(session.START_TIME_LONG);
        partyContactMechanism.setActiveDetail(null);
        partyContactMechanism.store();
        
        // Check for default, and pick one if necessary
        var party = partyContactMechanismDetail.getParty();
        var defaultPartyContactMechanism = getDefaultPartyContactMechanism(party);
        if(defaultPartyContactMechanism == null) {
            var partyContactMechanisms = getPartyContactMechanismsByPartyForUpdate(party);
            
            if(!partyContactMechanisms.isEmpty()) {
                var iter = partyContactMechanisms.iterator();
                if(iter.hasNext()) {
                    defaultPartyContactMechanism = iter.next();
                }
                var partyContactMechanismDetailValue = Objects.requireNonNull(defaultPartyContactMechanism).getLastDetailForUpdate().getPartyContactMechanismDetailValue().clone();
                
                partyContactMechanismDetailValue.setIsDefault(true);
                updatePartyContactMechanismFromValue(partyContactMechanismDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(partyContactMechanismDetail.getPartyPK(), EventTypes.MODIFY, partyContactMechanism.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deletePartyContactMechanismsByParty(Party party, BasePK deletedBy) {
        var partyContactMechanisms = getPartyContactMechanismsByPartyForUpdate(party);
        
        partyContactMechanisms.forEach((partyContactMechanism) -> 
                deletePartyContactMechanism(partyContactMechanism, deletedBy)
        );
    }
    
    public void deletePartyContactMechanismsByContactMechanism(ContactMechanism contactMechanism, BasePK deletedBy) {
        var partyContactMechanisms = getPartyContactMechanismsByContactMechanismForUpdate(contactMechanism);
        
        partyContactMechanisms.forEach((partyContactMechanism) -> 
                deletePartyContactMechanism(partyContactMechanism, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Party Contact Mechanism Utilities
    // --------------------------------------------------------------------------------
    
    public PartyContactMechanism getPartyContactMechanismByInet4Address(Party party, Integer inet4Address) {
        PartyContactMechanism partyContactMechanism;
        
        try {
            var ps = PartyContactMechanismFactory.getInstance().prepareStatement(
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
        PartyContactMechanism partyContactMechanism;
        
        try {
            var ps = PartyContactMechanismFactory.getInstance().prepareStatement(
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
        List<PartyContactMechanism> partyContactMechanisms;
        
        try {
            var ps = PartyContactMechanismFactory.getInstance().prepareStatement(
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

        var partyContactMechanismAlias = PartyContactMechanismAliasFactory.getInstance().create(session,
                party, contactMechanism, contactMechanismAliasType, alias, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyContactMechanismAlias.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyContactMechanismAlias;
    }
    
    private List<PartyContactMechanismAlias> getPartyContactMechanismAliasesByParty(Party party,
            EntityPermission entityPermission) {
        List<PartyContactMechanismAlias> partyContactMechanismAliases;
        
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

            var ps = PartyContactMechanismAliasFactory.getInstance().prepareStatement(query);
            
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
        List<PartyContactMechanismAlias> partyContactMechanismAliases;
        
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

            var ps = PartyContactMechanismAliasFactory.getInstance().prepareStatement(query);
            
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
        PartyContactMechanismAlias partyContactMechanismAlias;
        
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

            var ps = PartyContactMechanismAliasFactory.getInstance().prepareStatement(query);
            
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
        
        sendEvent(partyContactMechanismAlias.getPartyPK(), EventTypes.MODIFY, partyContactMechanismAlias.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deletePartyContactMechanismAliasesByParty(Party party, BasePK deletedBy) {
        var partyContactMechanismAliases = getPartyContactMechanismAliasesByParty(party);
        
        partyContactMechanismAliases.forEach((partyContactMechanismAlias) -> 
                deletePartyContactMechanismAlias(partyContactMechanismAlias, deletedBy)
        );
    }
    
    public void deletePartyContactMechanismAliasesByContactMechanism(ContactMechanism contactMechanism, BasePK deletedBy) {
        var partyContactMechanismAliases = getPartyContactMechanismAliasesByContactMechanismForUpdate(contactMechanism);
        
        partyContactMechanismAliases.forEach((partyContactMechanismAlias) -> 
                deletePartyContactMechanismAlias(partyContactMechanismAlias, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Party Contact Mechanism Purposes
    // --------------------------------------------------------------------------------
    
    public PartyContactMechanismPurpose createPartyContactMechanismPurpose(PartyContactMechanism partyContactMechanism,
            ContactMechanismPurpose contactMechanismPurpose, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultPartyContactMechanismPurpose = getDefaultPartyContactMechanismPurpose(partyContactMechanism, contactMechanismPurpose);
        var defaultFound = defaultPartyContactMechanismPurpose != null;
        
        if(defaultFound && isDefault) {
            var defaultPartyContactMechanismPurposeDetailValue = getDefaultPartyContactMechanismPurposeDetailValueForUpdate(partyContactMechanism,
                    contactMechanismPurpose);
            
            defaultPartyContactMechanismPurposeDetailValue.setIsDefault(false);
            updatePartyContactMechanismPurposeFromValue(defaultPartyContactMechanismPurposeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var partyContactMechanismPurpose = PartyContactMechanismPurposeFactory.getInstance().create();
        var partyContactMechanismPurposeDetail = PartyContactMechanismPurposeDetailFactory.getInstance().create(session,
                partyContactMechanismPurpose, partyContactMechanism, contactMechanismPurpose, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        partyContactMechanismPurpose = PartyContactMechanismPurposeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, partyContactMechanismPurpose.getPrimaryKey());
        partyContactMechanismPurpose.setActiveDetail(partyContactMechanismPurposeDetail);
        partyContactMechanismPurpose.setLastDetail(partyContactMechanismPurposeDetail);
        partyContactMechanismPurpose.store();
        
        sendEvent(partyContactMechanism.getLastDetail().getPartyPK(), EventTypes.MODIFY, partyContactMechanismPurpose.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyContactMechanismPurpose;
    }

    public long countPartyContactMechanismPurposesByPartyContactMechanism(PartyContactMechanism partyContactMechanism) {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM partycontactmechanismpurposes
                        JOIN partycontactmechanismpurposedetails ON pcmp_activedetailid = pcmpdt_partycontactmechanismpurposedetailid
                        WHERE pcmpdt_cmpr_contactmechanismpurposeid = ?
                        """, partyContactMechanism);
    }

    private PartyContactMechanismPurpose getDefaultPartyContactMechanismPurpose(PartyPK partyPK, ContactMechanismPurpose contactMechanismPurpose, EntityPermission entityPermission) {
        PartyContactMechanismPurpose partyContactMechanismPurpose;
        
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

            var ps = PartyContactMechanismPurposeFactory.getInstance().prepareStatement(query);
            
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
        var contactMechanismPurpose = getContactMechanismPurposeByName(contactMechanismPurposeName);
        
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
        PartyContactMechanismPurpose partyContactMechanismPurpose;
        
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

            var ps = PartyContactMechanismPurposeFactory.getInstance().prepareStatement(query);
            
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
        List<PartyContactMechanismPurpose> partyContactMechanismPurposes;
        
        try {
            var ps = PartyContactMechanismPurposeFactory.getInstance().prepareStatement(
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
        List<PartyContactMechanismPurpose> partyContactMechanismAliases;
        
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
                        "FROM partycontactmechanismpurposes, partycontactmechanismpurposedetails " +
                        "WHERE pcmp_activedetailid = pcmpdt_partycontactmechanismpurposedetailid AND pcmpdt_pcm_partycontactmechanismid = ? " +
                        "FOR UPDATE";
            }

            var ps = PartyContactMechanismPurposeFactory.getInstance().prepareStatement(query);
            
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
        var entities = getPartyContactMechanismPurposesByPartyContactMechanism(partyContactMechanism);
        List<PartyContactMechanismPurposeTransfer> transfers = new ArrayList<>(entities.size());
        var cache = getContactTransferCaches(userVisit).getPartyContactMechanismPurposeTransferCache();
        
        entities.forEach((entity) ->
                transfers.add(cache.getPartyContactMechanismPurposeTransfer(entity))
        );
        
        return transfers;
    }
    
    private void updatePartyContactMechanismPurposeFromValue(PartyContactMechanismPurposeDetailValue partyContactMechanismPurposeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(partyContactMechanismPurposeDetailValue.hasBeenModified()) {
            var partyContactMechanismPurpose = PartyContactMechanismPurposeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyContactMechanismPurposeDetailValue.getPartyContactMechanismPurposePK());
            var partyContactMechanismPurposeDetail = partyContactMechanismPurpose.getActiveDetailForUpdate();
            
            partyContactMechanismPurposeDetail.setThruTime(session.START_TIME_LONG);
            partyContactMechanismPurposeDetail.store();

            var partyContactMechanismPurposePK = partyContactMechanismPurposeDetail.getPartyContactMechanismPurposePK();
            var partyContactMechanism = partyContactMechanismPurposeDetail.getPartyContactMechanism();
            var contactMechanismPurpose = partyContactMechanismPurposeDetail.getContactMechanismPurpose();
            var isDefault = partyContactMechanismPurposeDetailValue.getIsDefault();
            var sortOrder = partyContactMechanismPurposeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultPartyContactMechanismPurpose = getDefaultPartyContactMechanismPurpose(partyContactMechanism, contactMechanismPurpose);
                var defaultFound = defaultPartyContactMechanismPurpose != null && !defaultPartyContactMechanismPurpose.equals(partyContactMechanismPurpose);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPartyContactMechanismPurposeDetailValue = getDefaultPartyContactMechanismPurposeDetailValueForUpdate(partyContactMechanism, contactMechanismPurpose);
                    
                    defaultPartyContactMechanismPurposeDetailValue.setIsDefault(false);
                    updatePartyContactMechanismPurposeFromValue(defaultPartyContactMechanismPurposeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            partyContactMechanismPurposeDetail = PartyContactMechanismPurposeDetailFactory.getInstance().create(session,
                    partyContactMechanismPurposePK, partyContactMechanism.getPrimaryKey(), contactMechanismPurpose.getPrimaryKey(),
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            partyContactMechanismPurpose.setActiveDetail(partyContactMechanismPurposeDetail);
            partyContactMechanismPurpose.setLastDetail(partyContactMechanismPurposeDetail);
            
            sendEvent(partyContactMechanism.getLastDetail().getPartyPK(), EventTypes.MODIFY, partyContactMechanismPurpose.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updatePartyContactMechanismPurposeFromValue(PartyContactMechanismPurposeDetailValue partyContactMechanismPurposeDetailValue, BasePK updatedBy) {
        updatePartyContactMechanismPurposeFromValue(partyContactMechanismPurposeDetailValue, true, updatedBy);
    }
    
    public void deletePartyContactMechanismPurpose(PartyContactMechanismPurpose partyContactMechanismPurpose, BasePK deletedBy) {
        var partyContactMechanismPurposeDetail = partyContactMechanismPurpose.getLastDetailForUpdate();
        partyContactMechanismPurposeDetail.setThruTime(session.START_TIME_LONG);
        partyContactMechanismPurpose.setActiveDetail(null);
        partyContactMechanismPurpose.store();
        
        // Check for default, and pick one if necessary
        var partyContactMechanism = partyContactMechanismPurposeDetail.getPartyContactMechanism();
        var contactMechanismPurpose = partyContactMechanismPurposeDetail.getContactMechanismPurpose();
        var defaultPartyContactMechanismPurpose = getDefaultPartyContactMechanismPurpose(partyContactMechanism, contactMechanismPurpose);
        if(defaultPartyContactMechanismPurpose == null) {
            var partyContactMechanismPurposes = getPartyContactMechanismPurposesForUpdate(partyContactMechanism, contactMechanismPurpose);
            
            if(!partyContactMechanismPurposes.isEmpty()) {
                var iter = partyContactMechanismPurposes.iterator();
                if(iter.hasNext()) {
                    defaultPartyContactMechanismPurpose = iter.next();
                }
                var partyContactMechanismPurposeDetailValue = Objects.requireNonNull(defaultPartyContactMechanismPurpose).getLastDetailForUpdate().getPartyContactMechanismPurposeDetailValue().clone();
                
                partyContactMechanismPurposeDetailValue.setIsDefault(true);
                updatePartyContactMechanismPurposeFromValue(partyContactMechanismPurposeDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(partyContactMechanism.getLastDetail().getPartyPK(), EventTypes.MODIFY, partyContactMechanismPurpose.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deletePartyContactMechanismPurposesByPartyContactMechanism(PartyContactMechanism partyContactMechanism,
            BasePK deletedBy) {
        var partyContactMechanismPurposes = getPartyContactMechanismPurposesByPartyContactMechanismForUpdate(partyContactMechanism);
        
        partyContactMechanismPurposes.forEach((partyContactMechanismPurpose) -> 
                deletePartyContactMechanismPurpose(partyContactMechanismPurpose, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Party Contact Mechanism Relationships
    // --------------------------------------------------------------------------------
    
    public PartyContactMechanismRelationship createPartyContactMechanismRelationship(PartyContactMechanism fromPartyContactMechanism,
            PartyContactMechanism toPartyContactMechanism, BasePK createdBy) {
        var partyContactMechanismRelationship = PartyContactMechanismRelationshipFactory.getInstance().create(fromPartyContactMechanism,
                toPartyContactMechanism, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(fromPartyContactMechanism.getPrimaryKey(), EventTypes.MODIFY, partyContactMechanismRelationship.getPrimaryKey(), null, createdBy);
        
        return partyContactMechanismRelationship;
    }
    
    public boolean partyContactMechanismRelationshipExists(PartyContactMechanism fromPartyContactMechanism, PartyContactMechanism toPartyContactMechanism) {
        return session.queryForLong(
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

    public List<PartyContactMechanismRelationshipTransfer> getPartyContactMechanismRelationshipTransfers(UserVisit userVisit, Collection<PartyContactMechanismRelationship> partyContactMechanismRelationships) {
        List<PartyContactMechanismRelationshipTransfer> partyContactMechanismRelationshipTransfers = new ArrayList<>(partyContactMechanismRelationships.size());
        var partyContactMechanismRelationshipTransferCache = getContactTransferCaches(userVisit).getPartyContactMechanismRelationshipTransferCache();
        
        partyContactMechanismRelationships.forEach((partyContactMechanismRelationship) ->
                partyContactMechanismRelationshipTransfers.add(partyContactMechanismRelationshipTransferCache.getPartyContactMechanismRelationshipTransfer(partyContactMechanismRelationship))
        );
        
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
        partyContactMechanismRelationships.forEach((partyContactMechanismRelationship) -> 
                deletePartyContactMechanismRelationship(partyContactMechanismRelationship, deletedBy)
        );
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
        PostalAddressElementType postalAddressElementType;
        
        try {
            var ps = PostalAddressElementTypeFactory.getInstance().prepareStatement(
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
        var ps = PostalAddressElementTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM postaladdresselementtypes " +
                "ORDER BY pstaetyp_sortorder, pstaetyp_postaladdresselementtypename");
        
        return PostalAddressElementTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public PostalAddressElementTypeChoicesBean getPostalAddressElementTypeChoices(String defaultPostalAddressElementTypeChoice, Language language,
            boolean allowNullChoice) {
        var postalAddressElementTypes = getPostalAddressElementTypes();
        var size = postalAddressElementTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
        }
        
        for(var postalAddressElementType : postalAddressElementTypes) {
            var label = getBestPostalAddressElementTypeDescription(postalAddressElementType, language);
            var value = postalAddressElementType.getPostalAddressElementTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultPostalAddressElementTypeChoice != null && defaultPostalAddressElementTypeChoice.equals(value);
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
        PostalAddressElementTypeDescription postalAddressElementTypeDescription;

        try {
            var ps = PostalAddressElementTypeDescriptionFactory.getInstance().prepareStatement(
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
        var postalAddressElementTypeDescription = getPostalAddressElementTypeDescription(postalAddressElementType, language);
        
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
        var defaultPostalAddressFormat = getDefaultPostalAddressFormat();
        var defaultFound = defaultPostalAddressFormat != null;
        
        if(defaultFound && isDefault) {
            var defaultPostalAddressFormatDetailValue = getDefaultPostalAddressFormatDetailValueForUpdate();
            
            defaultPostalAddressFormatDetailValue.setIsDefault(false);
            updatePostalAddressFormatFromValue(defaultPostalAddressFormatDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var postalAddressFormat = PostalAddressFormatFactory.getInstance().create();
        var postalAddressFormatDetail = PostalAddressFormatDetailFactory.getInstance().create(postalAddressFormat,
                postalAddressFormatName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        postalAddressFormat = PostalAddressFormatFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                postalAddressFormat.getPrimaryKey());
        postalAddressFormat.setActiveDetail(postalAddressFormatDetail);
        postalAddressFormat.setLastDetail(postalAddressFormatDetail);
        postalAddressFormat.store();
        
        sendEvent(postalAddressFormat.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
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

        var ps = PostalAddressFormatFactory.getInstance().prepareStatement(query);
        
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

        var ps = PostalAddressFormatFactory.getInstance().prepareStatement(query);
        
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
        PostalAddressFormat postalAddressFormat;
        
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

            var ps = PostalAddressFormatFactory.getInstance().prepareStatement(query);
            
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
        var postalAddressFormats = getPostalAddressFormats();
        var size = postalAddressFormats.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultPostalAddressFormatChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var postalAddressFormat : postalAddressFormats) {
            var postalAddressFormatDetail = postalAddressFormat.getLastDetail();
            var label = getBestPostalAddressFormatDescription(postalAddressFormat, language);
            var value = postalAddressFormatDetail.getPostalAddressFormatName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultPostalAddressFormatChoice != null && defaultPostalAddressFormatChoice.equals(value);
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
        var postalAddressFormats = getPostalAddressFormats();
        List<PostalAddressFormatTransfer> postalAddressFormatTransfers = new ArrayList<>(postalAddressFormats.size());
        var postalAddressFormatTransferCache = getContactTransferCaches(userVisit).getPostalAddressFormatTransferCache();
        
        postalAddressFormats.forEach((postalAddressFormat) ->
                postalAddressFormatTransfers.add(postalAddressFormatTransferCache.getPostalAddressFormatTransfer(postalAddressFormat))
        );
        
        return postalAddressFormatTransfers;
    }
    
    private void updatePostalAddressFormatFromValue(PostalAddressFormatDetailValue postalAddressFormatDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(postalAddressFormatDetailValue.hasBeenModified()) {
            var postalAddressFormat = PostalAddressFormatFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     postalAddressFormatDetailValue.getPostalAddressFormatPK());
            var postalAddressFormatDetail = postalAddressFormat.getActiveDetailForUpdate();
            
            postalAddressFormatDetail.setThruTime(session.START_TIME_LONG);
            postalAddressFormatDetail.store();

            var postalAddressFormatPK = postalAddressFormatDetail.getPostalAddressFormatPK();
            var postalAddressFormatName = postalAddressFormatDetailValue.getPostalAddressFormatName();
            var isDefault = postalAddressFormatDetailValue.getIsDefault();
            var sortOrder = postalAddressFormatDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultPostalAddressFormat = getDefaultPostalAddressFormat();
                var defaultFound = defaultPostalAddressFormat != null && !defaultPostalAddressFormat.equals(postalAddressFormat);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPostalAddressFormatDetailValue = getDefaultPostalAddressFormatDetailValueForUpdate();
                    
                    defaultPostalAddressFormatDetailValue.setIsDefault(false);
                    updatePostalAddressFormatFromValue(defaultPostalAddressFormatDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            postalAddressFormatDetail = PostalAddressFormatDetailFactory.getInstance().create(postalAddressFormatPK, postalAddressFormatName,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            postalAddressFormat.setActiveDetail(postalAddressFormatDetail);
            postalAddressFormat.setLastDetail(postalAddressFormatDetail);
            
            sendEvent(postalAddressFormatPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updatePostalAddressFormatFromValue(PostalAddressFormatDetailValue postalAddressFormatDetailValue, BasePK updatedBy) {
        updatePostalAddressFormatFromValue(postalAddressFormatDetailValue, true, updatedBy);
    }
    
    public void deletePostalAddressFormat(PostalAddressFormat postalAddressFormat, BasePK deletedBy) {
        deletePostalAddressFormatDescriptionsByPostalAddressFormat(postalAddressFormat, deletedBy);
        deletePostalAddressLinesByPostalAddressFormat(postalAddressFormat, deletedBy);

        var postalAddressFormatDetail = postalAddressFormat.getLastDetailForUpdate();
        postalAddressFormatDetail.setThruTime(session.START_TIME_LONG);
        postalAddressFormat.setActiveDetail(null);
        postalAddressFormat.store();
        
        // Check for default, and pick one if necessary
        var defaultPostalAddressFormat = getDefaultPostalAddressFormat();
        if(defaultPostalAddressFormat == null) {
            var postalAddressFormats = getPostalAddressFormatsForUpdate();
            
            if(!postalAddressFormats.isEmpty()) {
                var iter = postalAddressFormats.iterator();
                if(iter.hasNext()) {
                    defaultPostalAddressFormat = iter.next();
                }
                var postalAddressFormatDetailValue = Objects.requireNonNull(defaultPostalAddressFormat).getLastDetailForUpdate().getPostalAddressFormatDetailValue().clone();
                
                postalAddressFormatDetailValue.setIsDefault(true);
                updatePostalAddressFormatFromValue(postalAddressFormatDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(postalAddressFormat.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Postal Address Format Descriptions
    // --------------------------------------------------------------------------------
    
    public PostalAddressFormatDescription createPostalAddressFormatDescription(PostalAddressFormat postalAddressFormat,
            Language language, String description, BasePK createdBy) {
        var postalAddressFormatDescription = PostalAddressFormatDescriptionFactory.getInstance().create(session,
                postalAddressFormat, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(postalAddressFormat.getPrimaryKey(), EventTypes.MODIFY, postalAddressFormatDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return postalAddressFormatDescription;
    }
    
    private PostalAddressFormatDescription getPostalAddressFormatDescription(PostalAddressFormat postalAddressFormat, Language language, EntityPermission entityPermission) {
        PostalAddressFormatDescription postalAddressFormatDescription;
        
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

            var ps = PostalAddressFormatDescriptionFactory.getInstance().prepareStatement(query);
            
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
        List<PostalAddressFormatDescription> postalAddressFormatDescriptions;
        
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

            var ps = PostalAddressFormatDescriptionFactory.getInstance().prepareStatement(query);
            
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
        var postalAddressFormatDescription = getPostalAddressFormatDescription(postalAddressFormat, language);
        
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
        var postalAddressFormatDescriptions = getPostalAddressFormatDescriptionsByPostalAddressFormat(postalAddressFormat);
        List<PostalAddressFormatDescriptionTransfer> postalAddressFormatDescriptionTransfers = new ArrayList<>(postalAddressFormatDescriptions.size());
        var postalAddressFormatDescriptionTransferCache = getContactTransferCaches(userVisit).getPostalAddressFormatDescriptionTransferCache();
        
        postalAddressFormatDescriptions.forEach((postalAddressFormatDescription) ->
                postalAddressFormatDescriptionTransfers.add(postalAddressFormatDescriptionTransferCache.getPostalAddressFormatDescriptionTransfer(postalAddressFormatDescription))
        );
        
        return postalAddressFormatDescriptionTransfers;
    }
    
    public void updatePostalAddressFormatDescriptionFromValue(PostalAddressFormatDescriptionValue postalAddressFormatDescriptionValue, BasePK updatedBy) {
        if(postalAddressFormatDescriptionValue.hasBeenModified()) {
            var postalAddressFormatDescription = PostalAddressFormatDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, postalAddressFormatDescriptionValue.getPrimaryKey());
            
            postalAddressFormatDescription.setThruTime(session.START_TIME_LONG);
            postalAddressFormatDescription.store();

            var postalAddressFormat = postalAddressFormatDescription.getPostalAddressFormat();
            var language = postalAddressFormatDescription.getLanguage();
            var description = postalAddressFormatDescriptionValue.getDescription();
            
            postalAddressFormatDescription = PostalAddressFormatDescriptionFactory.getInstance().create(postalAddressFormat, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(postalAddressFormat.getPrimaryKey(), EventTypes.MODIFY, postalAddressFormatDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePostalAddressFormatDescription(PostalAddressFormatDescription postalAddressFormatDescription, BasePK deletedBy) {
        postalAddressFormatDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(postalAddressFormatDescription.getPostalAddressFormatPK(), EventTypes.MODIFY, postalAddressFormatDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deletePostalAddressFormatDescriptionsByPostalAddressFormat(PostalAddressFormat postalAddressFormat, BasePK deletedBy) {
        var postalAddressFormatDescriptions = getPostalAddressFormatDescriptionsByPostalAddressFormatForUpdate(postalAddressFormat);
        
        postalAddressFormatDescriptions.forEach((postalAddressFormatDescription) -> 
                deletePostalAddressFormatDescription(postalAddressFormatDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Postal Address Lines
    // --------------------------------------------------------------------------------
    
    public PostalAddressLine createPostalAddressLine(PostalAddressFormat postalAddressFormat, Integer postalAddressLineSortOrder,
            String prefix, Boolean alwaysIncludePrefix, String suffix, Boolean alwaysIncludeSuffix, Boolean collapseIfEmpty,
            BasePK createdBy) {
        var postalAddressLine = PostalAddressLineFactory.getInstance().create();
        var postalAddressLineDetail = PostalAddressLineDetailFactory.getInstance().create(session,
                postalAddressLine, postalAddressFormat, postalAddressLineSortOrder, prefix, alwaysIncludePrefix, suffix,
                alwaysIncludeSuffix, collapseIfEmpty, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        postalAddressLine = PostalAddressLineFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                postalAddressLine.getPrimaryKey());
        postalAddressLine.setActiveDetail(postalAddressLineDetail);
        postalAddressLine.setLastDetail(postalAddressLineDetail);
        postalAddressLine.store();
        
        sendEvent(postalAddressFormat.getPrimaryKey(), EventTypes.MODIFY, postalAddressLine.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return postalAddressLine;
    }
    
    private PostalAddressLine getPostalAddressLine(PostalAddressFormat postalAddressFormat, Integer postalAddressLineSortOrder,
            EntityPermission entityPermission) {
        PostalAddressLine postalAddressLine;
        
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

            var ps = PostalAddressLineFactory.getInstance().prepareStatement(query);
            
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
        List<PostalAddressLine> postalAddressLines;
        
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

            var ps = PostalAddressLineFactory.getInstance().prepareStatement(query);
            
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
        var postalAddressLines = getPostalAddressLinesByPostalAddressFormat(postalAddressFormat);
        List<PostalAddressLineTransfer> postalAddressLineTransfers = new ArrayList<>(postalAddressLines.size());
        var postalAddressLineTransferCache = getContactTransferCaches(userVisit).getPostalAddressLineTransferCache();
        
        postalAddressLines.forEach((postalAddressLine) ->
                postalAddressLineTransfers.add(postalAddressLineTransferCache.getPostalAddressLineTransfer(postalAddressLine))
        );
        
        return postalAddressLineTransfers;
    }
    
    public void updatePostalAddressLineFromValue(PostalAddressLineDetailValue postalAddressLineDetailValue, BasePK updatedBy) {
        if(postalAddressLineDetailValue.hasBeenModified()) {
            var postalAddressLine = PostalAddressLineFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     postalAddressLineDetailValue.getPostalAddressLinePK());
            var postalAddressLineDetail = postalAddressLine.getActiveDetailForUpdate();
            
            postalAddressLineDetail.setThruTime(session.START_TIME_LONG);
            postalAddressLineDetail.store();

            var postalAddressLinePK = postalAddressLineDetail.getPostalAddressLinePK();
            var postalAddressFormatPK = postalAddressLineDetail.getPostalAddressFormatPK();
            var postalAddressLineSortOrder = postalAddressLineDetailValue.getPostalAddressLineSortOrder();
            var prefix = postalAddressLineDetailValue.getPrefix();
            var alwaysIncludePrefix = postalAddressLineDetailValue.getAlwaysIncludePrefix();
            var suffix = postalAddressLineDetailValue.getSuffix();
            var alwaysIncludeSuffix = postalAddressLineDetailValue.getAlwaysIncludeSuffix();
            var collapseIfEmpty = postalAddressLineDetailValue.getCollapseIfEmpty();
            
            postalAddressLineDetail = PostalAddressLineDetailFactory.getInstance().create(postalAddressLinePK,
                    postalAddressFormatPK, postalAddressLineSortOrder, prefix, alwaysIncludePrefix, suffix, alwaysIncludeSuffix,
                    collapseIfEmpty, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            postalAddressLine.setActiveDetail(postalAddressLineDetail);
            postalAddressLine.setLastDetail(postalAddressLineDetail);
            
            sendEvent(postalAddressFormatPK, EventTypes.MODIFY, postalAddressLinePK, EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePostalAddressLine(PostalAddressLine postalAddressLine, BasePK deletedBy) {
        deletePostalAddressLineElementsByPostalAddressLine(postalAddressLine, deletedBy);

        var postalAddressLineDetail = postalAddressLine.getLastDetailForUpdate();
        postalAddressLineDetail.setThruTime(session.START_TIME_LONG);
        postalAddressLine.setActiveDetail(null);
        postalAddressLine.store();
        
        sendEvent(postalAddressLineDetail.getPostalAddressFormatPK(), EventTypes.MODIFY, postalAddressLine.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deletePostalAddressLinesByPostalAddressFormat(PostalAddressFormat postalAddressFormat, BasePK deletedBy) {
        var postalAddressLines = getPostalAddressLinesByPostalAddressFormatForUpdate(postalAddressFormat);
        
        postalAddressLines.forEach((postalAddressLine) -> 
                deletePostalAddressLine(postalAddressLine, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Postal Address Line Elements
    // --------------------------------------------------------------------------------
    
    public PostalAddressLineElement createPostalAddressLineElement(PostalAddressLine postalAddressLine,
            Integer postalAddressLineElementSortOrder, PostalAddressElementType postalAddressElementType, String prefix,
            Boolean alwaysIncludePrefix, String suffix, Boolean alwaysIncludeSuffix, BasePK createdBy) {

        var postalAddressLineElement = PostalAddressLineElementFactory.getInstance().create(session,
                postalAddressLine, postalAddressLineElementSortOrder, postalAddressElementType, prefix, alwaysIncludePrefix, suffix,
                alwaysIncludeSuffix, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(postalAddressLine.getLastDetail().getPostalAddressFormatPK(), EventTypes.MODIFY, postalAddressLineElement.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return postalAddressLineElement;
    }
    
    private PostalAddressLineElement getPostalAddressLineElement(PostalAddressLine postalAddressLine,
            Integer postalAddressLineElementSortOrder, EntityPermission entityPermission) {
        PostalAddressLineElement postalAddressLineElement;
        
        try {
            var ps = PostalAddressLineElementFactory.getInstance().prepareStatement(
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
        List<PostalAddressLineElement> postalAddressLineElements;
        
        try {
            var ps = PostalAddressLineElementFactory.getInstance().prepareStatement(
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
        var postalAddressLineElements = getPostalAddressLineElementsByPostalAddressLine(postalAddressLine);
        List<PostalAddressLineElementTransfer> postalAddressLineElementTransfers = new ArrayList<>(postalAddressLineElements.size());
        var postalAddressLineElementTransferCache = getContactTransferCaches(userVisit).getPostalAddressLineElementTransferCache();
        
        postalAddressLineElements.forEach((postalAddressLineElement) ->
                postalAddressLineElementTransfers.add(postalAddressLineElementTransferCache.getPostalAddressLineElementTransfer(postalAddressLineElement))
        );
        
        return postalAddressLineElementTransfers;
    }
    
    public void updatePostalAddressLineElementFromValue(PostalAddressLineElementValue postalAddressLineElementValue, BasePK updatedBy) {
        if(postalAddressLineElementValue.hasBeenModified()) {
            var postalAddressLineElement = PostalAddressLineElementFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     postalAddressLineElementValue.getPrimaryKey());
            
            postalAddressLineElement.setThruTime(session.START_TIME_LONG);
            postalAddressLineElement.store();

            var postalAddressLinePK = postalAddressLineElement.getPostalAddressLinePK();
            var postalAddressLineElementSortOrder = postalAddressLineElementValue.getPostalAddressLineElementSortOrder();
            var postalAddressElementTypePK = postalAddressLineElementValue.getPostalAddressElementTypePK();
            var prefix = postalAddressLineElementValue.getPrefix();
            var alwaysIncludePrefix = postalAddressLineElementValue.getAlwaysIncludePrefix();
            var suffix = postalAddressLineElementValue.getSuffix();
            var alwaysIncludeSuffix = postalAddressLineElementValue.getAlwaysIncludeSuffix();
            
            postalAddressLineElement = PostalAddressLineElementFactory.getInstance().create(postalAddressLinePK,
                    postalAddressLineElementSortOrder, postalAddressElementTypePK, prefix, alwaysIncludePrefix, suffix, alwaysIncludeSuffix,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(postalAddressLineElement.getPostalAddressLine().getLastDetail().getPostalAddressFormatPK(), EventTypes.MODIFY, postalAddressLineElement.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePostalAddressLineElement(PostalAddressLineElement postalAddressLineElement, BasePK deletedBy) {
        postalAddressLineElement.setThruTime(session.START_TIME_LONG);
        
        sendEvent(postalAddressLineElement.getPostalAddressLine().getLastDetail().getPostalAddressFormatPK(), EventTypes.MODIFY, postalAddressLineElement.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deletePostalAddressLineElementsByPostalAddressLine(PostalAddressLine postalAddressLine, BasePK deletedBy) {
        var postalAddressLineElements = getPostalAddressLineElementsByPostalAddressLineForUpdate(postalAddressLine);
        
        postalAddressLineElements.forEach((postalAddressLineElement) -> 
                deletePostalAddressLineElement(postalAddressLineElement, deletedBy)
        );
    }
    
}

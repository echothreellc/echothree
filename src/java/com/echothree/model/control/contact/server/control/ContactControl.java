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

package com.echothree.model.control.contact.server.control;

import static com.echothree.model.jooq.server.keys.contact.ContactForeignKeys.CONTACT_MECHANISM_ALIASES_CONTACT_MECHANISM_ALIAS_TYPE_FK;
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
import com.echothree.model.control.contact.server.transfer.ContactEmailAddressTransferCache;
import com.echothree.model.control.contact.server.transfer.ContactInet4AddressTransferCache;
import com.echothree.model.control.contact.server.transfer.ContactMechanismAliasTransferCache;
import com.echothree.model.control.contact.server.transfer.ContactMechanismAliasTypeDescriptionTransferCache;
import com.echothree.model.control.contact.server.transfer.ContactMechanismAliasTypeTransferCache;
import com.echothree.model.control.contact.server.transfer.ContactMechanismPurposeTransferCache;
import com.echothree.model.control.contact.server.transfer.ContactMechanismTransferCache;
import com.echothree.model.control.contact.server.transfer.ContactMechanismTypeTransferCache;
import com.echothree.model.control.contact.server.transfer.ContactPostalAddressTransferCache;
import com.echothree.model.control.contact.server.transfer.ContactTelephoneTransferCache;
import com.echothree.model.control.contact.server.transfer.ContactWebAddressTransferCache;
import com.echothree.model.control.contact.server.transfer.PartyContactMechanismAliasTransferCache;
import com.echothree.model.control.contact.server.transfer.PartyContactMechanismPurposeTransferCache;
import com.echothree.model.control.contact.server.transfer.PartyContactMechanismRelationshipTransferCache;
import com.echothree.model.control.contact.server.transfer.PartyContactMechanismTransferCache;
import com.echothree.model.control.contact.server.transfer.PostalAddressElementTypeTransferCache;
import com.echothree.model.control.contact.server.transfer.PostalAddressFormatDescriptionTransferCache;
import com.echothree.model.control.contact.server.transfer.PostalAddressFormatTransferCache;
import com.echothree.model.control.contact.server.transfer.PostalAddressLineElementTransferCache;
import com.echothree.model.control.contact.server.transfer.PostalAddressLineTransferCache;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.control.order.server.control.OrderShipmentGroupControl;
import com.echothree.model.control.payment.server.control.BillingControl;
import com.echothree.model.control.payment.server.control.PartyPaymentMethodControl;
import com.echothree.model.control.shipment.server.control.ShipmentControl;
import com.echothree.model.data.contact.common.pk.ContactMechanismAliasTypePK;
import com.echothree.model.data.contact.common.pk.ContactMechanismPK;
import com.echothree.model.data.contact.common.pk.ContactMechanismPurposePK;
import com.echothree.model.data.contact.common.pk.ContactMechanismTypePK;
import com.echothree.model.data.contact.common.pk.PartyContactMechanismPK;
import com.echothree.model.data.contact.common.pk.PartyContactMechanismPurposePK;
import com.echothree.model.data.contact.common.pk.PostalAddressFormatPK;
import com.echothree.model.data.contact.common.pk.PostalAddressLinePK;
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
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.NameSuffix;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PersonalTitle;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.contact.ContactForeignKeys.CONTACT_MECHANISMS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.contact.ContactForeignKeys.CONTACT_MECHANISMS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.contact.ContactForeignKeys.CONTACT_MECHANISM_ALIAS_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.contact.ContactForeignKeys.CONTACT_MECHANISM_ALIAS_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.contact.ContactForeignKeys.CONTACT_MECHANISM_ALIAS_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.contact.ContactForeignKeys.CONTACT_MECHANISM_DETAILS_CONTACT_MECHANISM_TYPE_FK;
import static com.echothree.model.jooq.server.keys.contact.ContactForeignKeys.PARTY_CONTACT_MECHANISMS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.contact.ContactForeignKeys.PARTY_CONTACT_MECHANISMS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.contact.ContactForeignKeys.PARTY_CONTACT_MECHANISM_ALIASES_CONTACT_MECHANISM_ALIAS_TYPE_FK;
import static com.echothree.model.jooq.server.keys.contact.ContactForeignKeys.PARTY_CONTACT_MECHANISM_DETAILS_CONTACT_MECHANISM_FK;
import static com.echothree.model.jooq.server.keys.contact.ContactForeignKeys.PARTY_CONTACT_MECHANISM_DETAILS_PARTY_FK;
import static com.echothree.model.jooq.server.keys.contact.ContactForeignKeys.PARTY_CONTACT_MECHANISM_PURPOSES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.contact.ContactForeignKeys.PARTY_CONTACT_MECHANISM_PURPOSE_DETAILS_CONTACT_MECHANISM_PURPOSE_FK;
import static com.echothree.model.jooq.server.keys.contact.ContactForeignKeys.PARTY_CONTACT_MECHANISM_PURPOSE_DETAILS_PARTY_CONTACT_MECHANISM_FK;
import static com.echothree.model.jooq.server.keys.contact.ContactForeignKeys.PARTY_CONTACT_MECHANISM_RELATIONSHIPS_FROM_PARTY_CONTACT_MECHANISM_FK;
import static com.echothree.model.jooq.server.keys.contact.ContactForeignKeys.PARTY_CONTACT_MECHANISM_RELATIONSHIPS_TO_PARTY_CONTACT_MECHANISM_FK;
import static com.echothree.model.jooq.server.keys.contact.ContactForeignKeys.POSTAL_ADDRESS_FORMATS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.contact.ContactForeignKeys.POSTAL_ADDRESS_FORMAT_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.contact.ContactForeignKeys.POSTAL_ADDRESS_LINES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTIES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTY_DETAILS_PARTY_TYPE_FK;
import static com.echothree.model.jooq.server.tables.contact.ContactEmailAddresses.ContactEmailAddresses;
import static com.echothree.model.jooq.server.tables.contact.ContactInet4Addresses.ContactInet4Addresses;
import static com.echothree.model.jooq.server.tables.contact.ContactInet6Addresses.ContactInet6Addresses;
import static com.echothree.model.jooq.server.tables.contact.ContactMechanismAliasTypeDescriptions.ContactMechanismAliasTypeDescriptions;
import static com.echothree.model.jooq.server.tables.contact.ContactMechanismAliasTypeDetails.ContactMechanismAliasTypeDetails;
import static com.echothree.model.jooq.server.tables.contact.ContactMechanismAliasTypes.ContactMechanismAliasTypes;
import static com.echothree.model.jooq.server.tables.contact.ContactMechanismAliases.ContactMechanismAliases;
import static com.echothree.model.jooq.server.tables.contact.ContactMechanismDetails.ContactMechanismDetails;
import static com.echothree.model.jooq.server.tables.contact.ContactMechanismPurposeDescriptions.ContactMechanismPurposeDescriptions;
import static com.echothree.model.jooq.server.tables.contact.ContactMechanismPurposes.ContactMechanismPurposes;
import static com.echothree.model.jooq.server.tables.contact.ContactMechanismTypeDescriptions.ContactMechanismTypeDescriptions;
import static com.echothree.model.jooq.server.tables.contact.ContactMechanismTypes.ContactMechanismTypes;
import static com.echothree.model.jooq.server.tables.contact.ContactMechanisms.ContactMechanisms;
import static com.echothree.model.jooq.server.tables.contact.ContactPostalAddressCorrections.ContactPostalAddressCorrections;
import static com.echothree.model.jooq.server.tables.contact.ContactPostalAddresses.ContactPostalAddresses;
import static com.echothree.model.jooq.server.tables.contact.ContactTelephones.ContactTelephones;
import static com.echothree.model.jooq.server.tables.contact.ContactWebAddresses.ContactWebAddresses;
import static com.echothree.model.jooq.server.tables.contact.PartyContactMechanismAliases.PartyContactMechanismAliases;
import static com.echothree.model.jooq.server.tables.contact.PartyContactMechanismDetails.PartyContactMechanismDetails;
import static com.echothree.model.jooq.server.tables.contact.PartyContactMechanismPurposeDetails.PartyContactMechanismPurposeDetails;
import static com.echothree.model.jooq.server.tables.contact.PartyContactMechanismPurposes.PartyContactMechanismPurposes;
import static com.echothree.model.jooq.server.tables.contact.PartyContactMechanismRelationships.PartyContactMechanismRelationships;
import static com.echothree.model.jooq.server.tables.contact.PartyContactMechanisms.PartyContactMechanisms;
import static com.echothree.model.jooq.server.tables.contact.PostalAddressElementTypeDescriptions.PostalAddressElementTypeDescriptions;
import static com.echothree.model.jooq.server.tables.contact.PostalAddressElementTypes.PostalAddressElementTypes;
import static com.echothree.model.jooq.server.tables.contact.PostalAddressFormatDescriptions.PostalAddressFormatDescriptions;
import static com.echothree.model.jooq.server.tables.contact.PostalAddressFormatDetails.PostalAddressFormatDetails;
import static com.echothree.model.jooq.server.tables.contact.PostalAddressFormats.PostalAddressFormats;
import static com.echothree.model.jooq.server.tables.contact.PostalAddressLineDetails.PostalAddressLineDetails;
import static com.echothree.model.jooq.server.tables.contact.PostalAddressLineElements.PostalAddressLineElements;
import static com.echothree.model.jooq.server.tables.contact.PostalAddressLines.PostalAddressLines;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.party.Parties.Parties;
import static com.echothree.model.jooq.server.tables.party.PartyDetails.PartyDetails;
import static com.echothree.model.jooq.server.tables.party.PartyTypes.PartyTypes;
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

@CommandScope
public class ContactControl
        extends BaseModelControl {
    
    @Inject
    protected AssociateControl associateControl;

    @Inject
    protected BillingControl billingControl;

    @Inject
    protected CommunicationControl communicationControl;

    @Inject
    protected ContactListControl contactListControl;

    @Inject
    protected InvoiceControl invoiceControl;

    @Inject
    protected LetterControl letterControl;

    @Inject
    protected OrderShipmentGroupControl orderShipmentGroupControl;

    @Inject
    protected PartyPaymentMethodControl partyPaymentMethodControl;

    @Inject
    protected ShipmentControl shipmentControl;

    /** Creates a new instance of ContactControl */
    protected ContactControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Types
    // --------------------------------------------------------------------------------

    @Inject
    protected ContactMechanismTypeFactory contactMechanismTypeFactory;

    @Inject
    ContactMechanismTypeTransferCache contactMechanismTypeTransferCache;

    public ContactMechanismType createContactMechanismType(String contactMechanismTypeName,
            ContactMechanismType parentContactMechanismType, Boolean isDefault, Integer sortOrder) {
        return contactMechanismTypeFactory.create(contactMechanismTypeName, parentContactMechanismType,
                isDefault, sortOrder);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ContactMechanismType */
    public ContactMechanismType getContactMechanismTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ContactMechanismTypePK(entityInstance.getEntityUniqueId());

        return contactMechanismTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ContactMechanismType getContactMechanismTypeByEntityInstance(EntityInstance entityInstance) {
        return getContactMechanismTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ContactMechanismType getContactMechanismTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getContactMechanismTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countContactMechanismTypes() {
        return session.getDslContext()
                .selectCount()
                .from(ContactMechanismTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public ContactMechanismType getContactMechanismTypeByName(String contactMechanismTypeName) {
        var query = session.getDslContext()
                .select(ContactMechanismTypes.fields())
                .from(ContactMechanismTypes)
                .where(ContactMechanismTypes.CONTACT_MECHANISM_TYPE_NAME.eq(contactMechanismTypeName));

        return contactMechanismTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public List<ContactMechanismType> getContactMechanismTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(ContactMechanismTypes.fields())
                .from(ContactMechanismTypes)
                .orderBy(ContactMechanismTypes.SORT_ORDER, ContactMechanismTypes.CONTACT_MECHANISM_TYPE_NAME),
                ContactMechanismTypeFactory.class);

        return contactMechanismTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
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
        return contactMechanismTypeTransferCache.getContactMechanismTypeTransfer(userVisit, contactMechanismType);
    }
    
    public List<ContactMechanismTypeTransfer> getContactMechanismTypeTransfers(UserVisit userVisit, Collection<ContactMechanismType> contactMechanismTypes) {
        List<ContactMechanismTypeTransfer> transfers = new ArrayList<>(contactMechanismTypes.size());

        contactMechanismTypes.forEach((contactMechanismType) -> {
            transfers.add(contactMechanismTypeTransferCache.getContactMechanismTypeTransfer(userVisit, contactMechanismType));
        });

        return transfers;
    }

    public List<ContactMechanismTypeTransfer> getContactMechanismTypeTransfers(UserVisit userVisit) {
        return getContactMechanismTypeTransfers(userVisit, getContactMechanismTypes());
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ContactMechanismTypeDescriptionFactory contactMechanismTypeDescriptionFactory;

    public ContactMechanismTypeDescription createContactMechanismTypeDescription(ContactMechanismType contactMechanismType,
            Language language, String description) {
        return contactMechanismTypeDescriptionFactory.create(contactMechanismType, language, description);
    }
    
    public ContactMechanismTypeDescription getContactMechanismTypeDescription(ContactMechanismType contactMechanismType,
            Language language) {
        var query = session.getDslContext()
                .select(ContactMechanismTypeDescriptions.fields())
                .from(ContactMechanismTypeDescriptions)
                .where(ContactMechanismTypeDescriptions.CONTACT_MECHANISM_TYPE.eq(contactMechanismType.getPrimaryKey()),
                        ContactMechanismTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return contactMechanismTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public String getBestContactMechanismTypeDescription(ContactMechanismType contactMechanismType, Language language) {
        String description;
        var contactMechanismTypeDescription = getContactMechanismTypeDescription(contactMechanismType, language);
        
        if(contactMechanismTypeDescription == null && !language.getIsDefault()) {
            contactMechanismTypeDescription = getContactMechanismTypeDescription(contactMechanismType, partyControl.getDefaultLanguage());
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

    @Inject
    protected ContactMechanismAliasTypeDetailFactory contactMechanismAliasTypeDetailFactory;

    @Inject
    protected ContactMechanismAliasTypeFactory contactMechanismAliasTypeFactory;

    @Inject
    ContactMechanismAliasTypeTransferCache contactMechanismAliasTypeTransferCache;

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

        var contactMechanismAliasType = contactMechanismAliasTypeFactory.create();
        var contactMechanismAliasTypeDetail = contactMechanismAliasTypeDetailFactory.create(contactMechanismAliasType,
                contactMechanismAliasTypeName, validationPattern, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        contactMechanismAliasType = contactMechanismAliasTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                contactMechanismAliasType.getPrimaryKey());
        contactMechanismAliasType.setActiveDetail(contactMechanismAliasTypeDetail);
        contactMechanismAliasType.setLastDetail(contactMechanismAliasTypeDetail);
        contactMechanismAliasType.store();

        sendEvent(contactMechanismAliasType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return contactMechanismAliasType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ContactMechanismAliasType */
    public ContactMechanismAliasType getContactMechanismAliasTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ContactMechanismAliasTypePK(entityInstance.getEntityUniqueId());

        return contactMechanismAliasTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ContactMechanismAliasType getContactMechanismAliasTypeByEntityInstance(EntityInstance entityInstance) {
        return getContactMechanismAliasTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ContactMechanismAliasType getContactMechanismAliasTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getContactMechanismAliasTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countContactMechanismAliasTypes() {
        return session.getDslContext()
                .selectCount()
                .from(ContactMechanismAliasTypes)
                .join(ContactMechanismAliasTypeDetails).onKey(CONTACT_MECHANISM_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public ContactMechanismAliasType getContactMechanismAliasTypeByName(String contactMechanismAliasTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactMechanismAliasTypes.fields())
                .from(ContactMechanismAliasTypes)
                .join(ContactMechanismAliasTypeDetails).onKey(CONTACT_MECHANISM_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .where(ContactMechanismAliasTypeDetails.CONTACT_MECHANISM_ALIAS_TYPE_NAME.eq(contactMechanismAliasTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactMechanismAliasTypeFactory.getEntityFromQuery(entityPermission, query);
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

    public ContactMechanismAliasType getDefaultContactMechanismAliasType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactMechanismAliasTypes.fields())
                .from(ContactMechanismAliasTypes)
                .join(ContactMechanismAliasTypeDetails).onKey(CONTACT_MECHANISM_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .where(ContactMechanismAliasTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactMechanismAliasTypeFactory.getEntityFromQuery(entityPermission, query);
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

    private List<ContactMechanismAliasType> getContactMechanismAliasTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactMechanismAliasTypes.fields())
                .from(ContactMechanismAliasTypes)
                .join(ContactMechanismAliasTypeDetails).onKey(CONTACT_MECHANISM_ALIAS_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ContactMechanismAliasTypeDetails.SORT_ORDER, ContactMechanismAliasTypeDetails.CONTACT_MECHANISM_ALIAS_TYPE_NAME),
                    ContactMechanismAliasTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactMechanismAliasTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ContactMechanismAliasType> getContactMechanismAliasTypes() {
        return getContactMechanismAliasTypes(EntityPermission.READ_ONLY);
    }

    public List<ContactMechanismAliasType> getContactMechanismAliasTypesForUpdate() {
        return getContactMechanismAliasTypes(EntityPermission.READ_WRITE);
    }







    public ContactMechanismAliasTypeTransfer getContactMechanismAliasTypeTransfer(UserVisit userVisit, ContactMechanismAliasType contactMechanismAliasType) {
        return contactMechanismAliasTypeTransferCache.getContactMechanismAliasTypeTransfer(userVisit, contactMechanismAliasType);
    }

    public List<ContactMechanismAliasTypeTransfer> getContactMechanismAliasTypeTransfers(UserVisit userVisit, Collection<ContactMechanismAliasType> contactMechanismAliasTypes) {
        List<ContactMechanismAliasTypeTransfer> contactMechanismAliasTypeTransfers = new ArrayList<>(contactMechanismAliasTypes.size());

        contactMechanismAliasTypes.forEach((contactMechanismAliasType) ->
                contactMechanismAliasTypeTransfers.add(contactMechanismAliasTypeTransferCache.getContactMechanismAliasTypeTransfer(userVisit, contactMechanismAliasType))
        );

        return contactMechanismAliasTypeTransfers;
    }

    public List<ContactMechanismAliasTypeTransfer> getContactMechanismAliasTypeTransfers(UserVisit userVisit) {
        return getContactMechanismAliasTypeTransfers(userVisit, getContactMechanismAliasTypes());
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
            var contactMechanismAliasType = contactMechanismAliasTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     contactMechanismAliasTypeDetailValue.getContactMechanismAliasTypePK());
            var contactMechanismAliasTypeDetail = contactMechanismAliasType.getActiveDetailForUpdate();

            contactMechanismAliasTypeDetail.setThruTime(session.getStartTime());
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

            contactMechanismAliasTypeDetail = contactMechanismAliasTypeDetailFactory.create(contactMechanismAliasTypePK,
                    contactMechanismAliasTypeName, validationPattern, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

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
        contactMechanismAliasTypeDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected ContactMechanismAliasTypeDescriptionFactory contactMechanismAliasTypeDescriptionFactory;

    @Inject
    ContactMechanismAliasTypeDescriptionTransferCache contactMechanismAliasTypeDescriptionTransferCache;

    public ContactMechanismAliasTypeDescription createContactMechanismAliasTypeDescription(ContactMechanismAliasType contactMechanismAliasType,
            Language language, String description, BasePK createdBy) {
        var contactMechanismAliasTypeDescription = contactMechanismAliasTypeDescriptionFactory.create(contactMechanismAliasType,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(contactMechanismAliasType.getPrimaryKey(), EventTypes.MODIFY, contactMechanismAliasTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return contactMechanismAliasTypeDescription;
    }

    private ContactMechanismAliasTypeDescription getContactMechanismAliasTypeDescription(ContactMechanismAliasType contactMechanismAliasType,
            Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactMechanismAliasTypeDescriptions.fields())
                .from(ContactMechanismAliasTypeDescriptions)
                .where(ContactMechanismAliasTypeDescriptions.CONTACT_MECHANISM_ALIAS_TYPE.eq(contactMechanismAliasType.getPrimaryKey()),
                        ContactMechanismAliasTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ContactMechanismAliasTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactMechanismAliasTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
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

    private List<ContactMechanismAliasTypeDescription> getContactMechanismAliasTypeDescriptionsByContactMechanismAliasType(ContactMechanismAliasType contactMechanismAliasType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContactMechanismAliasTypeDescriptions.fields())
                    .from(ContactMechanismAliasTypeDescriptions)
                    .join(Languages).onKey(CONTACT_MECHANISM_ALIAS_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ContactMechanismAliasTypeDescriptions.CONTACT_MECHANISM_ALIAS_TYPE.eq(contactMechanismAliasType.getPrimaryKey()),
                            ContactMechanismAliasTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ContactMechanismAliasTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContactMechanismAliasTypeDescriptions.fields())
                    .from(ContactMechanismAliasTypeDescriptions)
                    .where(ContactMechanismAliasTypeDescriptions.CONTACT_MECHANISM_ALIAS_TYPE.eq(contactMechanismAliasType.getPrimaryKey()),
                            ContactMechanismAliasTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return contactMechanismAliasTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
            contactMechanismAliasTypeDescription = getContactMechanismAliasTypeDescription(contactMechanismAliasType, partyControl.getDefaultLanguage());
        }

        if(contactMechanismAliasTypeDescription == null) {
            description = contactMechanismAliasType.getLastDetail().getContactMechanismAliasTypeName();
        } else {
            description = contactMechanismAliasTypeDescription.getDescription();
        }

        return description;
    }

    public ContactMechanismAliasTypeDescriptionTransfer getContactMechanismAliasTypeDescriptionTransfer(UserVisit userVisit, ContactMechanismAliasTypeDescription contactMechanismAliasTypeDescription) {
        return contactMechanismAliasTypeDescriptionTransferCache.getContactMechanismAliasTypeDescriptionTransfer(userVisit, contactMechanismAliasTypeDescription);
    }

    public List<ContactMechanismAliasTypeDescriptionTransfer> getContactMechanismAliasTypeDescriptionTransfersByContactMechanismAliasType(UserVisit userVisit, ContactMechanismAliasType contactMechanismAliasType) {
        var contactMechanismAliasTypeDescriptions = getContactMechanismAliasTypeDescriptionsByContactMechanismAliasType(contactMechanismAliasType);
        List<ContactMechanismAliasTypeDescriptionTransfer> contactMechanismAliasTypeDescriptionTransfers = new ArrayList<>(contactMechanismAliasTypeDescriptions.size());

        contactMechanismAliasTypeDescriptions.forEach((contactMechanismAliasTypeDescription) ->
                contactMechanismAliasTypeDescriptionTransfers.add(contactMechanismAliasTypeDescriptionTransferCache.getContactMechanismAliasTypeDescriptionTransfer(userVisit, contactMechanismAliasTypeDescription))
        );

        return contactMechanismAliasTypeDescriptionTransfers;
    }

    public void updateContactMechanismAliasTypeDescriptionFromValue(ContactMechanismAliasTypeDescriptionValue contactMechanismAliasTypeDescriptionValue, BasePK updatedBy) {
        if(contactMechanismAliasTypeDescriptionValue.hasBeenModified()) {
            var contactMechanismAliasTypeDescription = contactMechanismAliasTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    contactMechanismAliasTypeDescriptionValue.getPrimaryKey());

            contactMechanismAliasTypeDescription.setThruTime(session.getStartTime());
            contactMechanismAliasTypeDescription.store();

            var contactMechanismAliasType = contactMechanismAliasTypeDescription.getContactMechanismAliasType();
            var language = contactMechanismAliasTypeDescription.getLanguage();
            var description = contactMechanismAliasTypeDescriptionValue.getDescription();

            contactMechanismAliasTypeDescription = contactMechanismAliasTypeDescriptionFactory.create(contactMechanismAliasType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(contactMechanismAliasType.getPrimaryKey(), EventTypes.MODIFY, contactMechanismAliasTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteContactMechanismAliasTypeDescription(ContactMechanismAliasTypeDescription contactMechanismAliasTypeDescription, BasePK deletedBy) {
        contactMechanismAliasTypeDescription.setThruTime(session.getStartTime());

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

    @Inject
    protected ContactMechanismPurposeFactory contactMechanismPurposeFactory;

    @Inject
    ContactMechanismPurposeTransferCache contactMechanismPurposeTransferCache;

    public ContactMechanismPurpose createContactMechanismPurpose(String contactMechanismPurposeName,
            ContactMechanismType contactMechanismType, Boolean eventSubscriber, Boolean isDefault, Integer sortOrder) {
        return contactMechanismPurposeFactory.create(contactMechanismPurposeName, contactMechanismType,
                eventSubscriber, isDefault, sortOrder);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ContactMechanismPurpose */
    public ContactMechanismPurpose getContactMechanismPurposeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ContactMechanismPurposePK(entityInstance.getEntityUniqueId());

        return contactMechanismPurposeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ContactMechanismPurpose getContactMechanismPurposeByEntityInstance(EntityInstance entityInstance) {
        return getContactMechanismPurposeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ContactMechanismPurpose getContactMechanismPurposeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getContactMechanismPurposeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countContactMechanismPurposes() {
        return session.getDslContext()
                .selectCount()
                .from(ContactMechanismPurposes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public ContactMechanismPurpose getContactMechanismPurposeByName(String contactMechanismPurposeName) {
        var query = session.getDslContext()
                .select(ContactMechanismPurposes.fields())
                .from(ContactMechanismPurposes)
                .where(ContactMechanismPurposes.CONTACT_MECHANISM_PURPOSE_NAME.eq(contactMechanismPurposeName));

        return contactMechanismPurposeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public List<ContactMechanismPurpose> getContactMechanismPurposes() {
        var query = session.applyLimit(session.getDslContext()
                .select(ContactMechanismPurposes.fields())
                .from(ContactMechanismPurposes)
                .orderBy(ContactMechanismPurposes.SORT_ORDER, ContactMechanismPurposes.CONTACT_MECHANISM_PURPOSE_NAME),
                ContactMechanismPurposeFactory.class);

        return contactMechanismPurposeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public List<ContactMechanismPurpose> getContactMechanismPurposesByContactMechanismType(ContactMechanismType contactMechanismType) {
        var query = session.applyLimit(session.getDslContext()
                .select(ContactMechanismPurposes.fields())
                .from(ContactMechanismPurposes)
                .where(ContactMechanismPurposes.CONTACT_MECHANISM_TYPE.eq(contactMechanismType.getPrimaryKey()))
                .orderBy(ContactMechanismPurposes.SORT_ORDER, ContactMechanismPurposes.CONTACT_MECHANISM_PURPOSE_NAME),
                ContactMechanismPurposeFactory.class);

        return contactMechanismPurposeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
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
        return contactMechanismPurposeTransferCache.getContactMechanismPurposeTransfer(userVisit, contactMechanismPurpose);
    }
    
    public List<ContactMechanismPurposeTransfer> getContactMechanismPurposeTransfers(UserVisit userVisit, Collection<ContactMechanismPurpose> entities) {
        List<ContactMechanismPurposeTransfer> transfers = new ArrayList<>(entities.size());

        entities.forEach((entity) ->
                transfers.add(contactMechanismPurposeTransferCache.getContactMechanismPurposeTransfer(userVisit, entity))
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

    @Inject
    protected ContactMechanismPurposeDescriptionFactory contactMechanismPurposeDescriptionFactory;

    public ContactMechanismPurposeDescription createContactMechanismPurposeDescription(ContactMechanismPurpose contactMechanismPurpose,
            Language language, String description) {
        return contactMechanismPurposeDescriptionFactory.create(contactMechanismPurpose, language, description);
    }
    
    public ContactMechanismPurposeDescription getContactMechanismPurposeDescription(ContactMechanismPurpose contactMechanismPurpose,
            Language language) {
        var query = session.getDslContext()
                .select(ContactMechanismPurposeDescriptions.fields())
                .from(ContactMechanismPurposeDescriptions)
                .where(ContactMechanismPurposeDescriptions.CONTACT_MECHANISM_PURPOSE.eq(contactMechanismPurpose.getPrimaryKey()),
                        ContactMechanismPurposeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return contactMechanismPurposeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public String getBestContactMechanismPurposeDescription(ContactMechanismPurpose contactMechanismPurpose, Language language) {
        String description;
        var contactMechanismPurposeDescription = getContactMechanismPurposeDescription(contactMechanismPurpose, language);
        
        if(contactMechanismPurposeDescription == null && !language.getIsDefault()) {
            contactMechanismPurposeDescription = getContactMechanismPurposeDescription(contactMechanismPurpose, partyControl.getDefaultLanguage());
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

    @Inject
    protected ContactMechanismDetailFactory contactMechanismDetailFactory;

    @Inject
    protected ContactMechanismFactory contactMechanismFactory;

    @Inject
    ContactMechanismTransferCache contactMechanismTransferCache;

    public ContactMechanism createContactMechanism(String contactMechanismName, ContactMechanismType contactMechanismType,
            Boolean allowSolicitation, BasePK createdBy) {
        var contactMechanism = contactMechanismFactory.create();
        var contactMechanismDetail = contactMechanismDetailFactory.create(contactMechanism,
                contactMechanismName, contactMechanismType, allowSolicitation, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        contactMechanism = contactMechanismFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                contactMechanism.getPrimaryKey());
        contactMechanism.setActiveDetail(contactMechanismDetail);
        contactMechanism.setLastDetail(contactMechanismDetail);
        contactMechanism.store();
        
        sendEvent(contactMechanism.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return contactMechanism;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ContactMechanism */
    public ContactMechanism getContactMechanismByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ContactMechanismPK(entityInstance.getEntityUniqueId());

        return contactMechanismFactory.getEntityFromPK(entityPermission, pk);
    }

    public ContactMechanism getContactMechanismByEntityInstance(EntityInstance entityInstance) {
        return getContactMechanismByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ContactMechanism getContactMechanismByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getContactMechanismByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countContactMechanisms() {
        return session.getDslContext()
                .selectCount()
                .from(ContactMechanisms)
                .join(ContactMechanismDetails).onKey(CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countContactMechanismsByContactMechanismType(final ContactMechanismType contactMechanismType) {
        return session.getDslContext()
                .selectCount()
                .from(ContactMechanisms)
                .join(ContactMechanismDetails).onKey(CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                .where(ContactMechanismDetails.CONTACT_MECHANISM_TYPE.eq(contactMechanismType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public ContactMechanism getContactMechanismByName(String contactMechanismName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactMechanisms.fields())
                .from(ContactMechanisms)
                .join(ContactMechanismDetails).onKey(CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                .where(ContactMechanismDetails.CONTACT_MECHANISM_NAME.eq(contactMechanismName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactMechanismFactory.getEntityFromQuery(entityPermission, query);
    }

    public ContactMechanism getContactMechanismByName(String contactMechanismName) {
        return getContactMechanismByName(contactMechanismName, EntityPermission.READ_ONLY);
    }

    public ContactMechanism getContactMechanismByNameForUpdate(String contactMechanismName) {
        return getContactMechanismByName(contactMechanismName, EntityPermission.READ_WRITE);
    }

    public List<ContactMechanism> getContactMechanisms(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactMechanisms.fields())
                .from(ContactMechanisms)
                .join(ContactMechanismDetails).onKey(CONTACT_MECHANISMS_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ContactMechanismDetails.CONTACT_MECHANISM_NAME),
                    ContactMechanismFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactMechanismFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ContactMechanism> getContactMechanism() {
        return getContactMechanisms(EntityPermission.READ_ONLY);
    }

    public List<ContactMechanism> getContactMechanismForUpdate() {
        return getContactMechanisms(EntityPermission.READ_WRITE);
    }

    public ContactMechanismDetailValue getContactMechanismDetailValue(ContactMechanismDetail contactMechanismDetail) {
        return contactMechanismDetail == null? null: contactMechanismDetail.getContactMechanismDetailValue().clone();
    }
    
    public ContactMechanismDetailValue getContactMechanismDetailValueByNameForUpdate(String contactMechanismName) {
        var contactMechanism = getContactMechanismByNameForUpdate(contactMechanismName);
        
        return contactMechanism == null? null: getContactMechanismDetailValue(contactMechanism.getLastDetailForUpdate());
    }
    
    public ContactMechanismTransfer getContactMechanismTransfer(UserVisit userVisit, ContactMechanism contactMechanism) {
        return contactMechanismTransferCache.getContactMechanismTransfer(userVisit, contactMechanism);
    }
    
    public List<ContactMechanismTransfer> getContactMechanismTransfers(UserVisit userVisit, Collection<ContactMechanism> contactMechanisms) {
        List<ContactMechanismTransfer> contactMechanismTransfers = new ArrayList<>(contactMechanisms.size());
        
        contactMechanisms.forEach((contactMechanism) -> {
            contactMechanismTransfers.add(contactMechanismTransferCache.getContactMechanismTransfer(userVisit, contactMechanism));
        });
        
        return contactMechanismTransfers;
    }

    public EmailAddressStatusChoicesBean getEmailAddressStatusChoices(String defaultEmailAddressStatusChoice, Language language, boolean allowNullChoice, ContactMechanism contactMechanism,
            PartyPK partyPK) {
        var emailAddressStatusChoicesBean = new EmailAddressStatusChoicesBean();
        var entityInstance = getEntityInstanceByBaseEntity(contactMechanism);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(EmailAddressStatusConstants.Workflow_EMAIL_ADDRESS_STATUS,
                entityInstance);
        
        workflowControl.getWorkflowDestinationChoices(emailAddressStatusChoicesBean, defaultEmailAddressStatusChoice, language, allowNullChoice, workflowEntityStatus.getWorkflowStep(), partyPK);
        
        return emailAddressStatusChoicesBean;
    }
    
    public void setEmailAddressStatus(ExecutionErrorAccumulator eea,  ContactMechanism contactMechanism, String emailAddressStatusChoice, PartyPK modifiedBy) {
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
        var emailAddressVerificationChoicesBean = new EmailAddressVerificationChoicesBean();
        var entityInstance = getEntityInstanceByBaseEntity(contactMechanism);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(EmailAddressVerificationConstants.Workflow_EMAIL_ADDRESS_VERIFICATION,
                entityInstance);
        
        workflowControl.getWorkflowDestinationChoices(emailAddressVerificationChoicesBean, defaultEmailAddressVerificationChoice, language, allowNullChoice, workflowEntityStatus.getWorkflowStep(), partyPK);
        
        return emailAddressVerificationChoicesBean;
    }
    
    public void setEmailAddressVerification(ExecutionErrorAccumulator eea,  ContactMechanism contactMechanism, String emailAddressVerificationChoice, PartyPK modifiedBy) {
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
        var postalAddressStatusChoicesBean = new PostalAddressStatusChoicesBean();
        var entityInstance = getEntityInstanceByBaseEntity(contactMechanism);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(PostalAddressStatusConstants.Workflow_POSTAL_ADDRESS_STATUS,
                entityInstance);
        
        workflowControl.getWorkflowDestinationChoices(postalAddressStatusChoicesBean, defaultPostalAddressStatusChoice, language, allowNullChoice, workflowEntityStatus.getWorkflowStep(), partyPK);
        
        return postalAddressStatusChoicesBean;
    }
    
    public void setPostalAddressStatus(ExecutionErrorAccumulator eea,  ContactMechanism contactMechanism, String postalAddressStatusChoice, PartyPK modifiedBy) {
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
        var telephoneStatusChoicesBean = new TelephoneStatusChoicesBean();
        var entityInstance = getEntityInstanceByBaseEntity(contactMechanism);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(TelephoneStatusConstants.Workflow_TELEPHONE_STATUS,
                entityInstance);
        
        workflowControl.getWorkflowDestinationChoices(telephoneStatusChoicesBean, defaultTelephoneStatusChoice, language, allowNullChoice, workflowEntityStatus.getWorkflowStep(), partyPK);
        
        return telephoneStatusChoicesBean;
    }
    
    public void setTelephoneStatus(ExecutionErrorAccumulator eea,  ContactMechanism contactMechanism, String telephoneStatusChoice, PartyPK modifiedBy) {
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
        var webAddressStatusChoicesBean = new WebAddressStatusChoicesBean();
        var entityInstance = getEntityInstanceByBaseEntity(contactMechanism);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(WebAddressStatusConstants.Workflow_WEB_ADDRESS_STATUS,
                entityInstance);
        
        workflowControl.getWorkflowDestinationChoices(webAddressStatusChoicesBean, defaultWebAddressStatusChoice, language, allowNullChoice, workflowEntityStatus.getWorkflowStep(), partyPK);
        
        return webAddressStatusChoicesBean;
    }
    
    public void setWebAddressStatus(ExecutionErrorAccumulator eea,  ContactMechanism contactMechanism, String webAddressStatusChoice, PartyPK modifiedBy) {
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
            var contactMechanism = contactMechanismFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     contactMechanismDetailValue.getContactMechanismPK());
            var contactMechanismDetail = contactMechanism.getActiveDetailForUpdate();
            
            contactMechanismDetail.setThruTime(session.getStartTime());
            contactMechanismDetail.store();

            var contactMechanismPK = contactMechanismDetail.getContactMechanismPK(); // Not updated
            var contactMechanismName = contactMechanismDetailValue.getContactMechanismName();
            var contactMechanismTypePK = contactMechanismDetail.getContactMechanismTypePK(); // Not updated
            var allowSolicitation = contactMechanismDetailValue.getAllowSolicitation();
            
            contactMechanismDetail = contactMechanismDetailFactory.create(contactMechanismPK,
                    contactMechanismName, contactMechanismTypePK, allowSolicitation, session.getStartTime(),
                    Session.MAX_TIME);
            
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
        contactMechanismDetail.setThruTime(session.getStartTime());
        contactMechanism.setActiveDetail(null);
        contactMechanism.store();

        var contactMechanismTypeName = contactMechanismDetail.getContactMechanismType().getContactMechanismTypeName();
        if(contactMechanismTypeName.equals(com.echothree.model.control.contact.common.ContactMechanismTypes.EMAIL_ADDRESS.name())) {
            deleteContactEmailAddressByContactMechanism(contactMechanism, deletedBy);
        } else if(contactMechanismTypeName.equals(com.echothree.model.control.contact.common.ContactMechanismTypes.INET_4.name())) {
            deleteContactInet4AddressByContactMechanism(contactMechanism, deletedBy);
        } else if(contactMechanismTypeName.equals(com.echothree.model.control.contact.common.ContactMechanismTypes.INET_6.name())) {
            deleteContactInet6AddressByContactMechanism(contactMechanism, deletedBy);
        } else if(contactMechanismTypeName.equals(com.echothree.model.control.contact.common.ContactMechanismTypes.POSTAL_ADDRESS.name())) {
            deleteContactPostalAddressByContactMechanism(contactMechanism, deletedBy);
            deleteContactPostalAddressCorrectionByContactMechanism(contactMechanism, deletedBy);
        } else if(contactMechanismTypeName.equals(com.echothree.model.control.contact.common.ContactMechanismTypes.TELECOM_ADDRESS.name())) {
            deleteContactTelephoneByContactMechanism(contactMechanism, deletedBy);
        } else if(contactMechanismTypeName.equals(com.echothree.model.control.contact.common.ContactMechanismTypes.WEB_ADDRESS.name())) {
            deleteContactWebAddressByContactMechanism(contactMechanism, deletedBy);
        }
        
        sendEvent(contactMechanism.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Aliases
    // --------------------------------------------------------------------------------

    @Inject
    protected ContactMechanismAliasFactory contactMechanismAliasFactory;

    @Inject
    ContactMechanismAliasTransferCache contactMechanismAliasTransferCache;

    public ContactMechanismAlias createContactMechanismAlias(ContactMechanism contactMechanism,
            ContactMechanismAliasType contactMechanismAliasType, String alias, BasePK createdBy) {

        var contactMechanismAlias = contactMechanismAliasFactory.create(contactMechanism,
                contactMechanismAliasType, alias, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(contactMechanism.getPrimaryKey(), EventTypes.MODIFY, contactMechanismAlias.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contactMechanismAlias;
    }

    public long countContactMechanismAliasBy(final ContactMechanism contactMechanism) {
        return session.getDslContext()
                .selectCount()
                .from(ContactMechanismAliases)
                .where(ContactMechanismAliases.CONTACT_MECHANISM.eq(contactMechanism.getPrimaryKey()),
                        ContactMechanismAliases.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countContactMechanismAliasBy(final ContactMechanismAliasType contactMechanismAliasType) {
        return session.getDslContext()
                .selectCount()
                .from(ContactMechanismAliases)
                .where(ContactMechanismAliases.CONTACT_MECHANISM_ALIAS_TYPE.eq(contactMechanismAliasType.getPrimaryKey()),
                        ContactMechanismAliases.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<ContactMechanismAlias> getContactMechanismAliasesByContactMechanism(ContactMechanism contactMechanism,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactMechanismAliases.fields())
                .from(ContactMechanismAliases)
                .where(ContactMechanismAliases.CONTACT_MECHANISM.eq(contactMechanism.getPrimaryKey()),
                        ContactMechanismAliases.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContactMechanismAliases.fields())
                    .from(ContactMechanismAliases)
                    .join(ContactMechanismAliasTypes).onKey(CONTACT_MECHANISM_ALIASES_CONTACT_MECHANISM_ALIAS_TYPE_FK)
                    .join(ContactMechanismAliasTypeDetails).onKey(CONTACT_MECHANISM_ALIAS_TYPES_LAST_DETAIL_FK)
                    .where(ContactMechanismAliases.CONTACT_MECHANISM.eq(contactMechanism.getPrimaryKey()),
                            ContactMechanismAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ContactMechanismAliasTypeDetails.SORT_ORDER, ContactMechanismAliasTypeDetails.CONTACT_MECHANISM_ALIAS_TYPE_NAME, ContactMechanismAliases.ALIAS),
                    ContactMechanismAliasFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactMechanismAliasFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ContactMechanismAlias> getContactMechanismAliasesByContactMechanism(ContactMechanism contactMechanism) {
        return getContactMechanismAliasesByContactMechanism(contactMechanism, EntityPermission.READ_ONLY);
    }

    public List<ContactMechanismAlias> getContactMechanismAliasesByContactMechanismForUpdate(ContactMechanism contactMechanism) {
        return getContactMechanismAliasesByContactMechanism(contactMechanism, EntityPermission.READ_WRITE);
    }

    private List<ContactMechanismAlias> getContactMechanismAliasesByContactMechanismAliasType(ContactMechanismAliasType contactMechanismAliasType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactMechanismAliases.fields())
                .from(ContactMechanismAliases)
                .where(ContactMechanismAliases.CONTACT_MECHANISM_ALIAS_TYPE.eq(contactMechanismAliasType.getPrimaryKey()),
                        ContactMechanismAliases.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ContactMechanismAliases.ALIAS),
                    ContactMechanismAliasFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactMechanismAliasFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ContactMechanismAlias> getContactMechanismAliasesByContactMechanismAliasType(ContactMechanismAliasType contactMechanismAliasType) {
        return getContactMechanismAliasesByContactMechanismAliasType(contactMechanismAliasType, EntityPermission.READ_ONLY);
    }

    public List<ContactMechanismAlias> getContactMechanismAliasesByContactMechanismAliasTypeForUpdate(ContactMechanismAliasType contactMechanismAliasType) {
        return getContactMechanismAliasesByContactMechanismAliasType(contactMechanismAliasType, EntityPermission.READ_WRITE);
    }

    private ContactMechanismAlias getContactMechanismAliasByAlias(ContactMechanismAliasType contactMechanismAliasType,
            String alias, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactMechanismAliases.fields())
                .from(ContactMechanismAliases)
                .where(ContactMechanismAliases.CONTACT_MECHANISM_ALIAS_TYPE.eq(contactMechanismAliasType.getPrimaryKey()),
                        ContactMechanismAliases.ALIAS.eq(alias),
                        ContactMechanismAliases.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactMechanismAliasFactory.getEntityFromQuery(entityPermission, query);
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
        return contactMechanismAliasTransferCache.getContactMechanismAliasTransfer(userVisit, contactMechanismAlias);
    }
    
    public List<ContactMechanismAliasTransfer> getContactMechanismAliasTransfersByContactMechanism(UserVisit userVisit, ContactMechanism contactMechanism) {
        var entities = getContactMechanismAliasesByContactMechanism(contactMechanism);
        List<ContactMechanismAliasTransfer> transfers = new ArrayList<>(entities.size());

        entities.forEach((entity) ->
                transfers.add(contactMechanismAliasTransferCache.getContactMechanismAliasTransfer(userVisit, entity))
        );
        
        return transfers;
    }
    
    public void deleteContactMechanismAlias(ContactMechanismAlias contactMechanismAlias, BasePK deletedBy) {
        contactMechanismAlias.setThruTime(session.getStartTime());
        
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

    @Inject
    protected ContactEmailAddressFactory contactEmailAddressFactory;

    @Inject
    ContactEmailAddressTransferCache contactEmailAddressTransferCache;

    public ContactEmailAddress createContactEmailAddress(ContactMechanism contactMechanism, String emailAddress, BasePK createdBy) {
        var contactEmailAddress = contactEmailAddressFactory.create(contactMechanism,
                emailAddress, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(contactMechanism.getPrimaryKey(), EventTypes.MODIFY, contactEmailAddress.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contactEmailAddress;
    }
    
    private ContactEmailAddress getContactEmailAddress(ContactMechanism contactMechanism, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactEmailAddresses.fields())
                .from(ContactEmailAddresses)
                .where(ContactEmailAddresses.CONTACT_MECHANISM.eq(contactMechanism.getPrimaryKey()),
                        ContactEmailAddresses.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactEmailAddressFactory.getEntityFromQuery(entityPermission, query);
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
        return contactEmailAddressTransferCache.getContactEmailAddressTransfer(userVisit, contactEmailAddress);
    }
    
    public void updateContactEmailAddressFromValue(ContactEmailAddressValue contactEmailAddressValue, BasePK updatedBy) {
        if(contactEmailAddressValue.hasBeenModified()) {
            var contactEmailAddress = contactEmailAddressFactory.getEntityFromPK(EntityPermission.READ_WRITE, contactEmailAddressValue.getPrimaryKey());
            
            contactEmailAddress.setThruTime(session.getStartTime());
            contactEmailAddress.store();

            var contactMechanismPK = contactEmailAddress.getContactMechanismPK(); // Not updated
            var emailAddress = contactEmailAddressValue.getEmailAddress();
            
            contactEmailAddress = contactEmailAddressFactory.create(contactMechanismPK, emailAddress,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(contactMechanismPK, EventTypes.MODIFY, contactEmailAddress.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteContactEmailAddress(ContactEmailAddress contactEmailAddress, BasePK deletedBy) {
        contactEmailAddress.setThruTime(session.getStartTime());
        
        sendEvent(contactEmailAddress.getContactMechanismPK(), EventTypes.MODIFY, contactEmailAddress.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteContactEmailAddressByContactMechanism(ContactMechanism contactMechanism, BasePK deletedBy) {
        deleteContactEmailAddress(getContactEmailAddressForUpdate(contactMechanism), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Inet 4 Addresses
    // --------------------------------------------------------------------------------

    @Inject
    protected ContactInet4AddressFactory contactInet4AddressFactory;

    @Inject
    ContactInet4AddressTransferCache contactInet4AddressTransferCache;

    public ContactInet4Address createContactInet4Address(ContactMechanism contactMechanism, Integer inet4Address, BasePK createdBy) {
        var contactInet4Address = contactInet4AddressFactory.create(contactMechanism,
                inet4Address, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(contactMechanism.getPrimaryKey(), EventTypes.MODIFY, contactInet4Address.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contactInet4Address;
    }
    
    private ContactInet4Address getContactInet4Address(ContactMechanism contactMechanism, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactInet4Addresses.fields())
                .from(ContactInet4Addresses)
                .where(ContactInet4Addresses.CONTACT_MECHANISM.eq(contactMechanism.getPrimaryKey()),
                        ContactInet4Addresses.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactInet4AddressFactory.getEntityFromQuery(entityPermission, query);
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
        return contactInet4AddressTransferCache.getContactInet4AddressTransfer(userVisit, contactInet4Address);
    }
    
    public void updateContactInet4AddressFromValue(ContactInet4AddressValue contactInet4AddressValue, BasePK updatedBy) {
        if(contactInet4AddressValue.hasBeenModified()) {
            var contactInet4Address = contactInet4AddressFactory.getEntityFromPK(EntityPermission.READ_WRITE, contactInet4AddressValue.getPrimaryKey());
            
            contactInet4Address.setThruTime(session.getStartTime());
            contactInet4Address.store();

            var contactMechanismPK = contactInet4Address.getContactMechanismPK(); // Not updated
            var inet4Address = contactInet4Address.getInet4Address();
            
            contactInet4Address = contactInet4AddressFactory.create(contactMechanismPK, inet4Address,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(contactMechanismPK, EventTypes.MODIFY, contactInet4Address.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteContactInet4Address(ContactInet4Address contactInet4Address, BasePK deletedBy) {
        contactInet4Address.setThruTime(session.getStartTime());
        
        sendEvent(contactInet4Address.getContactMechanismPK(), EventTypes.MODIFY, contactInet4Address.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteContactInet4AddressByContactMechanism(ContactMechanism contactMechanism, BasePK deletedBy) {
        deleteContactInet4Address(getContactInet4AddressForUpdate(contactMechanism), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Inet 6 Addresses
    // --------------------------------------------------------------------------------

    @Inject
    protected ContactInet6AddressFactory contactInet6AddressFactory;

    public ContactInet6Address createContactInet6Address(ContactMechanism contactMechanism, Long inet6AddressLow,
            Long inet6AddressHigh, BasePK createdBy) {
        var contactInet6Address = contactInet6AddressFactory.create(contactMechanism,
                inet6AddressLow, inet6AddressHigh, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(contactMechanism.getPrimaryKey(), EventTypes.MODIFY, contactInet6Address.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contactInet6Address;
    }
    
    private ContactInet6Address getContactInet6Address(ContactMechanism contactMechanism, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactInet6Addresses.fields())
                .from(ContactInet6Addresses)
                .where(ContactInet6Addresses.CONTACT_MECHANISM.eq(contactMechanism.getPrimaryKey()),
                        ContactInet6Addresses.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactInet6AddressFactory.getEntityFromQuery(entityPermission, query);
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
            var contactInet6Address = contactInet6AddressFactory.getEntityFromPK(EntityPermission.READ_WRITE, contactInet6AddressValue.getPrimaryKey());
            
            contactInet6Address.setThruTime(session.getStartTime());
            contactInet6Address.store();

            var contactMechanismPK = contactInet6Address.getContactMechanismPK(); // Not updated
            var inet6AddressLow = contactInet6Address.getInet6AddressLow();
            var inet6AddressHigh = contactInet6Address.getInet6AddressHigh();
            
            contactInet6Address = contactInet6AddressFactory.create(contactMechanismPK, inet6AddressLow,
                    inet6AddressHigh, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(contactMechanismPK, EventTypes.MODIFY, contactInet6Address.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteContactInet6Address(ContactInet6Address contactInet6Address, BasePK deletedBy) {
        contactInet6Address.setThruTime(session.getStartTime());
        
        sendEvent(contactInet6Address.getContactMechanismPK(), EventTypes.MODIFY, contactInet6Address.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteContactInet6AddressByContactMechanism(ContactMechanism contactMechanism, BasePK deletedBy) {
        deleteContactInet6Address(getContactInet6AddressForUpdate(contactMechanism), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Postal Addresses
    // --------------------------------------------------------------------------------

    @Inject
    protected ContactPostalAddressFactory contactPostalAddressFactory;

    @Inject
    ContactPostalAddressTransferCache contactPostalAddressTransferCache;

    public ContactPostalAddress createContactPostalAddress(ContactMechanism contactMechanism, PersonalTitle personalTitle,
            String firstName, String firstNameSdx, String middleName, String middleNameSdx, String lastName, String lastNameSdx,
            NameSuffix nameSuffix, String companyName, String attention, String address1, String address2, String address3, String city,
            GeoCode cityGeoCode, GeoCode countyGeoCode, String state, GeoCode stateGeoCode, String postalCode,
            GeoCode postalCodeGeoCode, GeoCode countryGeoCode, Boolean isCommercial, BasePK createdBy) {
        var contactPostalAddress = contactPostalAddressFactory.create(contactMechanism,
                personalTitle, firstName, firstNameSdx, middleName, middleNameSdx, lastName, lastNameSdx, nameSuffix, companyName, attention,
                address1, address2, address3, city, cityGeoCode, countyGeoCode, state, stateGeoCode, postalCode, postalCodeGeoCode,
                countryGeoCode, isCommercial, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(contactMechanism.getPrimaryKey(), EventTypes.MODIFY, contactPostalAddress.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contactPostalAddress;
    }

    public long countContactPostalAddressesByPersonalTitle(PersonalTitle personalTitle) {
        return session.getDslContext()
                .selectCount()
                .from(ContactPostalAddresses)
                .where(ContactPostalAddresses.PERSONAL_TITLE.eq(personalTitle.getPrimaryKey()),
                        ContactPostalAddresses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countContactPostalAddressesByNameSuffix(NameSuffix nameSuffix) {
        return session.getDslContext()
                .selectCount()
                .from(ContactPostalAddresses)
                .where(ContactPostalAddresses.NAME_SUFFIX.eq(nameSuffix.getPrimaryKey()),
                        ContactPostalAddresses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countContactPostalAddressesByCityGeoCode(GeoCode cityGeoCode) {
        return session.getDslContext()
                .selectCount()
                .from(ContactPostalAddresses)
                .where(ContactPostalAddresses.CITY_GEO_CODE.eq(cityGeoCode.getPrimaryKey()),
                        ContactPostalAddresses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countContactPostalAddressesByCountyGeoCode(GeoCode countyGeoCode) {
        return session.getDslContext()
                .selectCount()
                .from(ContactPostalAddresses)
                .where(ContactPostalAddresses.COUNTY_GEO_CODE.eq(countyGeoCode.getPrimaryKey()),
                        ContactPostalAddresses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countContactPostalAddressesByStateGeoCode(GeoCode stateGeoCode) {
        return session.getDslContext()
                .selectCount()
                .from(ContactPostalAddresses)
                .where(ContactPostalAddresses.STATE_GEO_CODE.eq(stateGeoCode.getPrimaryKey()),
                        ContactPostalAddresses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countContactPostalAddressesByPostalCodeGeoCode(GeoCode postalCodeGeoCode) {
        return session.getDslContext()
                .selectCount()
                .from(ContactPostalAddresses)
                .where(ContactPostalAddresses.POSTAL_CODE_GEO_CODE.eq(postalCodeGeoCode.getPrimaryKey()),
                        ContactPostalAddresses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countContactPostalAddressesByCountryGeoCode(GeoCode countryGeoCode) {
        return session.getDslContext()
                .selectCount()
                .from(ContactPostalAddresses)
                .where(ContactPostalAddresses.COUNTRY_GEO_CODE.eq(countryGeoCode.getPrimaryKey()),
                        ContactPostalAddresses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ContactPostalAddress getContactPostalAddress(ContactMechanism contactMechanism, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactPostalAddresses.fields())
                .from(ContactPostalAddresses)
                .where(ContactPostalAddresses.CONTACT_MECHANISM.eq(contactMechanism.getPrimaryKey()),
                        ContactPostalAddresses.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactPostalAddressFactory.getEntityFromQuery(entityPermission, query);
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
        return contactPostalAddressTransferCache.getContactPostalAddressTransfer(userVisit, contactPostalAddress);
    }
    
    public void updateContactPostalAddressFromValue(ContactPostalAddressValue contactPostalAddressValue, BasePK updatedBy) {
        if(contactPostalAddressValue.hasBeenModified()) {
            var contactPostalAddress = contactPostalAddressFactory.getEntityFromPK(EntityPermission.READ_WRITE, contactPostalAddressValue.getPrimaryKey());
            
            contactPostalAddress.setThruTime(session.getStartTime());
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
            
            contactPostalAddress = contactPostalAddressFactory.create(contactMechanismPK, personalTitlePK,
                    firstName, firstNameSdx, middleName, middleNameSdx, lastName, lastNameSdx, nameSuffixPK, companyName, attention, address1,
                    address2, address3, city, cityGeoCodePK, countyGeoCodePK, state, stateGeoCodePK, postalCode, postalCodeGeoCodePK,
                    countryGeoCodePK, isCommercial, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(contactMechanismPK, EventTypes.MODIFY, contactPostalAddress.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteContactPostalAddress(ContactPostalAddress contactPostalAddress, BasePK deletedBy) {
        contactPostalAddress.setThruTime(session.getStartTime());
        
        sendEvent(contactPostalAddress.getContactMechanismPK(), EventTypes.MODIFY, contactPostalAddress.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteContactPostalAddressByContactMechanism(ContactMechanism contactMechanism, BasePK deletedBy) {
        deleteContactPostalAddress(getContactPostalAddressForUpdate(contactMechanism), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Postal Address Corrections
    // --------------------------------------------------------------------------------

    @Inject
    protected ContactPostalAddressCorrectionFactory contactPostalAddressCorrectionFactory;

    public ContactPostalAddressCorrection createContactPostalAddressCorrection(ContactMechanism contactMechanism, String address1,
            String address2, String address3, String city, GeoCode cityGeoCode, GeoCode countyGeoCode, String state,
            GeoCode stateGeoCode, String postalCode, GeoCode postalCodeGeoCode, GeoCode countryGeoCode, BasePK createdBy) {
        var contactPostalAddressCorrection = contactPostalAddressCorrectionFactory.create(
                contactMechanism, address1, address2, address3, city, cityGeoCode, countyGeoCode, state, stateGeoCode, postalCode,
                postalCodeGeoCode, countryGeoCode, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(contactMechanism.getPrimaryKey(), EventTypes.MODIFY, contactPostalAddressCorrection.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contactPostalAddressCorrection;
    }
    
    public long countContactPostalAddressCorrectionsByCityGeoCode(GeoCode cityGeoCode) {
        return session.getDslContext()
                .selectCount()
                .from(ContactPostalAddressCorrections)
                .where(ContactPostalAddressCorrections.CITY_GEO_CODE.eq(cityGeoCode.getPrimaryKey()),
                        ContactPostalAddressCorrections.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countContactPostalAddressCorrectionsByCountyGeoCode(GeoCode countyGeoCode) {
        return session.getDslContext()
                .selectCount()
                .from(ContactPostalAddressCorrections)
                .where(ContactPostalAddressCorrections.COUNTY_GEO_CODE.eq(countyGeoCode.getPrimaryKey()),
                        ContactPostalAddressCorrections.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countContactPostalAddressCorrectionsByStateGeoCode(GeoCode stateGeoCode) {
        return session.getDslContext()
                .selectCount()
                .from(ContactPostalAddressCorrections)
                .where(ContactPostalAddressCorrections.STATE_GEO_CODE.eq(stateGeoCode.getPrimaryKey()),
                        ContactPostalAddressCorrections.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countContactPostalAddressCorrectionsByPostalCodeGeoCode(GeoCode postalCodeGeoCode) {
        return session.getDslContext()
                .selectCount()
                .from(ContactPostalAddressCorrections)
                .where(ContactPostalAddressCorrections.POSTAL_CODE_GEO_CODE.eq(postalCodeGeoCode.getPrimaryKey()),
                        ContactPostalAddressCorrections.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countContactPostalAddressCorrectionsByCountryGeoCode(GeoCode countryGeoCode) {
        return session.getDslContext()
                .selectCount()
                .from(ContactPostalAddressCorrections)
                .where(ContactPostalAddressCorrections.COUNTRY_GEO_CODE.eq(countryGeoCode.getPrimaryKey()),
                        ContactPostalAddressCorrections.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ContactPostalAddressCorrection getContactPostalAddressCorrection(ContactMechanism contactMechanism, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactPostalAddressCorrections.fields())
                .from(ContactPostalAddressCorrections)
                .where(ContactPostalAddressCorrections.CONTACT_MECHANISM.eq(contactMechanism.getPrimaryKey()),
                        ContactPostalAddressCorrections.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactPostalAddressCorrectionFactory.getEntityFromQuery(entityPermission, query);
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
            var contactPostalAddressCorrection = contactPostalAddressCorrectionFactory.getEntityFromPK(EntityPermission.READ_WRITE, contactPostalAddressCorrectionValue.getPrimaryKey());
            
            contactPostalAddressCorrection.setThruTime(session.getStartTime());
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
            
            contactPostalAddressCorrection = contactPostalAddressCorrectionFactory.create(contactMechanismPK,
                    address1, address2, address3, city, cityGeoCodePK, countyGeoCodePK, state, stateGeoCodePK, postalCode,
                    postalCodeGeoCodePK, countryGeoCodePK, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(contactMechanismPK, EventTypes.MODIFY, contactPostalAddressCorrection.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteContactPostalAddressCorrection(ContactPostalAddressCorrection contactPostalAddressCorrection, BasePK deletedBy) {
        contactPostalAddressCorrection.setThruTime(session.getStartTime());
        
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

    @Inject
    protected ContactTelephoneFactory contactTelephoneFactory;

    @Inject
    ContactTelephoneTransferCache contactTelephoneTransferCache;

    public ContactTelephone createContactTelephone(ContactMechanism contactMechanism, GeoCode countryGeoCode, String areaCode,
            String telephoneNumber, String telephoneExtension, BasePK createdBy) {
        var contactTelephone = contactTelephoneFactory.create(contactMechanism, countryGeoCode,
                areaCode, telephoneNumber, telephoneExtension, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(contactMechanism.getPrimaryKey(), EventTypes.MODIFY, contactTelephone.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contactTelephone;
    }
    
    public long countContactTelephonesByCountryGeoCode(GeoCode countryGeoCode) {
        return session.getDslContext()
                .selectCount()
                .from(ContactTelephones)
                .where(ContactTelephones.COUNTRY_GEO_CODE.eq(countryGeoCode.getPrimaryKey()),
                        ContactTelephones.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ContactTelephone getContactTelephone(ContactMechanism contactMechanism, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactTelephones.fields())
                .from(ContactTelephones)
                .where(ContactTelephones.CONTACT_MECHANISM.eq(contactMechanism.getPrimaryKey()),
                        ContactTelephones.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactTelephoneFactory.getEntityFromQuery(entityPermission, query);
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
        return contactTelephoneTransferCache.getContactTelephoneTransfer(userVisit, contactTelephone);
    }
    
    public void updateContactTelephoneFromValue(ContactTelephoneValue contactTelephoneValue, BasePK updatedBy) {
        if(contactTelephoneValue.hasBeenModified()) {
            var contactTelephone = contactTelephoneFactory.getEntityFromPK(EntityPermission.READ_WRITE, contactTelephoneValue.getPrimaryKey());
            
            contactTelephone.setThruTime(session.getStartTime());
            contactTelephone.store();

            var contactMechanismPK = contactTelephone.getContactMechanismPK(); // Not updated
            var countryGeoCodePK = contactTelephoneValue.getCountryGeoCodePK();
            var areaCode = contactTelephoneValue.getAreaCode();
            var telephoneNumber = contactTelephoneValue.getTelephoneNumber();
            var telephoneExtension = contactTelephoneValue.getTelephoneExtension();
            
            contactTelephone = contactTelephoneFactory.create(contactMechanismPK, countryGeoCodePK,
                    areaCode, telephoneNumber, telephoneExtension, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(contactMechanismPK, EventTypes.MODIFY, contactTelephone.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteContactTelephone(ContactTelephone contactTelephone, BasePK deletedBy) {
        contactTelephone.setThruTime(session.getStartTime());
        
        sendEvent(contactTelephone.getContactMechanismPK(), EventTypes.MODIFY, contactTelephone.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteContactTelephoneByContactMechanism(ContactMechanism contactMechanism, BasePK deletedBy) {
        deleteContactTelephone(getContactTelephoneForUpdate(contactMechanism), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Web Addresses
    // --------------------------------------------------------------------------------

    @Inject
    protected ContactWebAddressFactory contactWebAddressFactory;

    @Inject
    ContactWebAddressTransferCache contactWebAddressTransferCache;

    public ContactWebAddress createContactWebAddress(ContactMechanism contactMechanism, String url, BasePK createdBy) {
        var contactWebAddress = contactWebAddressFactory.create(contactMechanism, url,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(contactMechanism.getPrimaryKey(), EventTypes.MODIFY, contactWebAddress.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contactWebAddress;
    }
    
    private ContactWebAddress getContactWebAddress(ContactMechanism contactMechanism, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactWebAddresses.fields())
                .from(ContactWebAddresses)
                .where(ContactWebAddresses.CONTACT_MECHANISM.eq(contactMechanism.getPrimaryKey()),
                        ContactWebAddresses.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactWebAddressFactory.getEntityFromQuery(entityPermission, query);
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
        return contactWebAddressTransferCache.getContactWebAddressTransfer(userVisit, contactWebAddress);
    }
    
    public void updateContactWebAddressFromValue(ContactWebAddressValue contactWebAddressValue, BasePK updatedBy) {
        if(contactWebAddressValue.hasBeenModified()) {
            var contactWebAddress = contactWebAddressFactory.getEntityFromPK(EntityPermission.READ_WRITE, contactWebAddressValue.getPrimaryKey());
            
            contactWebAddress.setThruTime(session.getStartTime());
            contactWebAddress.store();

            var contactMechanismPK = contactWebAddress.getContactMechanismPK(); // Not updated
            var url = contactWebAddressValue.getUrl();
            
            contactWebAddress = contactWebAddressFactory.create(contactMechanismPK, url, session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(contactMechanismPK, EventTypes.MODIFY, contactWebAddress.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteContactWebAddress(ContactWebAddress contactWebAddress, BasePK deletedBy) {
        contactWebAddress.setThruTime(session.getStartTime());
        
        sendEvent(contactWebAddress.getContactMechanismPK(), EventTypes.MODIFY, contactWebAddress.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteContactWebAddressByContactMechanism(ContactMechanism contactMechanism, BasePK deletedBy) {
        deleteContactWebAddress(getContactWebAddressForUpdate(contactMechanism), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Contact Mechanisms
    // --------------------------------------------------------------------------------

    @Inject
    protected PartyContactMechanismDetailFactory partyContactMechanismDetailFactory;

    @Inject
    protected PartyContactMechanismFactory partyContactMechanismFactory;

    @Inject
    PartyContactMechanismTransferCache partyContactMechanismTransferCache;

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

        var partyContactMechanism = partyContactMechanismFactory.create();
        var partyContactMechanismDetail = partyContactMechanismDetailFactory.create(
                partyContactMechanism, party, contactMechanism, description, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        partyContactMechanism = partyContactMechanismFactory.getEntityFromPK(EntityPermission.READ_WRITE, partyContactMechanism.getPrimaryKey());
        partyContactMechanism.setActiveDetail(partyContactMechanismDetail);
        partyContactMechanism.setLastDetail(partyContactMechanismDetail);
        partyContactMechanism.store();
        
        sendEvent(partyContactMechanism.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return partyContactMechanism;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PartyContactMechanism */
    public PartyContactMechanism getPartyContactMechanismByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new PartyContactMechanismPK(entityInstance.getEntityUniqueId());

        return partyContactMechanismFactory.getEntityFromPK(entityPermission, pk);
    }

    public PartyContactMechanism getPartyContactMechanismByEntityInstance(EntityInstance entityInstance) {
        return getPartyContactMechanismByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PartyContactMechanism getPartyContactMechanismByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getPartyContactMechanismByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countPartyContactMechanisms() {
        return session.getDslContext()
                .selectCount()
                .from(PartyContactMechanisms)
                .join(PartyContactMechanismDetails).onKey(PARTY_CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyContactMechanismsByParty(final Party party) {
        return session.getDslContext()
                .selectCount()
                .from(PartyContactMechanisms)
                .join(PartyContactMechanismDetails).onKey(PARTY_CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                .where(PartyContactMechanismDetails.PARTY.eq(party.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyContactMechanismsByContactMechanism(final ContactMechanism contactMechanism) {
        return session.getDslContext()
                .selectCount()
                .from(PartyContactMechanisms)
                .join(PartyContactMechanismDetails).onKey(PARTY_CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                .where(PartyContactMechanismDetails.CONTACT_MECHANISM.eq(contactMechanism.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PartyContactMechanism getDefaultPartyContactMechanism(Party party, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyContactMechanisms.fields())
                .from(PartyContactMechanisms)
                .join(PartyContactMechanismDetails).onKey(PARTY_CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                .where(PartyContactMechanismDetails.PARTY.eq(party.getPrimaryKey()),
                        PartyContactMechanismDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyContactMechanismFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(PartyContactMechanisms.fields())
                .from(PartyContactMechanisms)
                .join(PartyContactMechanismDetails).onKey(PARTY_CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                .where(PartyContactMechanismDetails.PARTY.eq(party.getPrimaryKey()),
                        PartyContactMechanismDetails.CONTACT_MECHANISM.eq(contactMechanism.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyContactMechanismFactory.getEntityFromQuery(entityPermission, query);
    }

    public PartyContactMechanism getPartyContactMechanism(Party party, ContactMechanism contactMechanism) {
        return getPartyContactMechanism(party, contactMechanism, EntityPermission.READ_ONLY);
    }

    public PartyContactMechanism getPartyContactMechanismForUpdate(Party party, ContactMechanism contactMechanism) {
        return getPartyContactMechanism(party, contactMechanism, EntityPermission.READ_WRITE);
    }

    public List<PartyContactMechanism> getPartyContactMechanisms(EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyContactMechanisms.fields())
                    .from(PartyContactMechanisms)
                    .join(PartyContactMechanismDetails).onKey(PARTY_CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                    .join(ContactMechanisms).onKey(PARTY_CONTACT_MECHANISM_DETAILS_CONTACT_MECHANISM_FK)
                    .join(ContactMechanismDetails).onKey(CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                    .orderBy(ContactMechanismDetails.CONTACT_MECHANISM_NAME),
                    PartyContactMechanismFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyContactMechanisms.fields())
                    .from(PartyContactMechanisms)
                    .join(PartyContactMechanismDetails).onKey(PARTY_CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                    .forUpdate();
        };

        return partyContactMechanismFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyContactMechanism> getPartyContactMechanisms() {
        return getPartyContactMechanisms(EntityPermission.READ_ONLY);
    }

    public List<PartyContactMechanism> getPartyContactMechanismsForUpdate() {
        return getPartyContactMechanisms(EntityPermission.READ_WRITE);
    }

    public PartyContactMechanismDetailValue getPartyContactMechanismDetailValueForUpdate(PartyContactMechanism partyContactMechanism) {
        return partyContactMechanism == null? null: partyContactMechanism.getLastDetailForUpdate().getPartyContactMechanismDetailValue().clone();
    }
    
    public PartyContactMechanismDetailValue getPartyContactMechanismDetailValueForUpdate(Party party, ContactMechanism contactMechanism) {
        return getPartyContactMechanismDetailValueForUpdate(getPartyContactMechanismForUpdate(party, contactMechanism));
    }
    
    private List<PartyContactMechanism> getPartyContactMechanismsByParty(Party party, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyContactMechanisms.fields())
                    .from(PartyContactMechanisms)
                    .join(PartyContactMechanismDetails).onKey(PARTY_CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                    .join(ContactMechanisms).onKey(PARTY_CONTACT_MECHANISM_DETAILS_CONTACT_MECHANISM_FK)
                    .join(ContactMechanismDetails).onKey(CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                    .join(ContactMechanismTypes).onKey(CONTACT_MECHANISM_DETAILS_CONTACT_MECHANISM_TYPE_FK)
                    .where(PartyContactMechanismDetails.PARTY.eq(party.getPrimaryKey()))
                    .orderBy(ContactMechanismTypes.SORT_ORDER, ContactMechanismTypes.CONTACT_MECHANISM_TYPE_NAME, ContactMechanismDetails.CONTACT_MECHANISM_NAME),
                    PartyContactMechanismFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyContactMechanisms.fields())
                    .from(PartyContactMechanisms)
                    .join(PartyContactMechanismDetails).onKey(PARTY_CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                    .where(PartyContactMechanismDetails.PARTY.eq(party.getPrimaryKey()))
                    .forUpdate();
        };

        return partyContactMechanismFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartyContactMechanism> getPartyContactMechanismsByParty(Party party) {
        return getPartyContactMechanismsByParty(party, EntityPermission.READ_ONLY);
    }
    
    public List<PartyContactMechanism> getPartyContactMechanismsByPartyForUpdate(Party party) {
        return getPartyContactMechanismsByParty(party, EntityPermission.READ_WRITE);
    }
    
    private List<PartyContactMechanism> getPartyContactMechanismsByPartyAndContactMechanismType(Party party,
            ContactMechanismType contactMechanismType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyContactMechanisms.fields())
                .from(PartyContactMechanisms)
                .join(PartyContactMechanismDetails).onKey(PARTY_CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                .join(ContactMechanisms).onKey(PARTY_CONTACT_MECHANISM_DETAILS_CONTACT_MECHANISM_FK)
                .join(ContactMechanismDetails).onKey(CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                .where(PartyContactMechanismDetails.PARTY.eq(party.getPrimaryKey()),
                        ContactMechanismDetails.CONTACT_MECHANISM_TYPE.eq(contactMechanismType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ContactMechanismDetails.CONTACT_MECHANISM_NAME),
                    PartyContactMechanismFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyContactMechanismFactory.getEntitiesFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyContactMechanisms.fields())
                    .from(PartyContactMechanisms)
                    .join(PartyContactMechanismDetails).onKey(PARTY_CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                    .join(Parties).onKey(PARTY_CONTACT_MECHANISM_DETAILS_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .join(PartyTypes).onKey(PARTY_DETAILS_PARTY_TYPE_FK)
                    .where(PartyContactMechanismDetails.CONTACT_MECHANISM.eq(contactMechanism.getPrimaryKey()))
                    .orderBy(PartyContactMechanismDetails.SORT_ORDER, PartyDetails.PARTY_NAME, PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME),
                    PartyContactMechanismFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyContactMechanisms.fields())
                    .from(PartyContactMechanisms)
                    .join(PartyContactMechanismDetails).onKey(PARTY_CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                    .where(PartyContactMechanismDetails.CONTACT_MECHANISM.eq(contactMechanism.getPrimaryKey()))
                    .forUpdate();
        };

        return partyContactMechanismFactory.getEntitiesFromQuery(entityPermission, query);
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
        return partyContactMechanismTransferCache.getPartyContactMechanismTransfer(userVisit, partyContactMechanism);
    }
    
    public List<PartyContactMechanismTransfer> getPartyContactMechanismTransfers(UserVisit userVisit, Collection<PartyContactMechanism> partyContactMechanisms) {
        List<PartyContactMechanismTransfer> transfers = new ArrayList<>(partyContactMechanisms.size());

        partyContactMechanisms.forEach((partyContactMechanism) -> {
            transfers.add(partyContactMechanismTransferCache.getPartyContactMechanismTransfer(userVisit, partyContactMechanism));
        });

        return transfers;
    }
    
    public List<PartyContactMechanismTransfer> getPartyContactMechanismTransfersByParty(UserVisit userVisit, Party party) {
        return getPartyContactMechanismTransfers(userVisit, getPartyContactMechanismsByParty(party));
    }
    
    private void updatePartyContactMechanismFromValue(PartyContactMechanismDetailValue partyContactMechanismDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(partyContactMechanismDetailValue.hasBeenModified()) {
            var partyContactMechanism = partyContactMechanismFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     partyContactMechanismDetailValue.getPartyContactMechanismPK());
            var partyContactMechanismDetail = partyContactMechanism.getActiveDetailForUpdate();
            
            partyContactMechanismDetail.setThruTime(session.getStartTime());
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
            
            partyContactMechanismDetail = partyContactMechanismDetailFactory.create(partyContactMechanismPK,
                    partyPK, contactMechanismPK, description, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            partyContactMechanism.setActiveDetail(partyContactMechanismDetail);
            partyContactMechanism.setLastDetail(partyContactMechanismDetail);
            
            sendEvent(partyContactMechanism.getPrimaryKey(), EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updatePartyContactMechanismFromValue(PartyContactMechanismDetailValue partyContactMechanismDetailValue, BasePK updatedBy) {
        updatePartyContactMechanismFromValue(partyContactMechanismDetailValue, true, updatedBy);
    }
    
    public void deletePartyContactMechanism(PartyContactMechanism partyContactMechanism, BasePK deletedBy) {
        
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
        partyContactMechanismDetail.setThruTime(session.getStartTime());
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
        
        sendEvent(partyContactMechanism.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
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
        var query = session.getDslContext()
                .select(PartyContactMechanisms.fields())
                .from(PartyContactMechanisms)
                .join(PartyContactMechanismDetails).onKey(PARTY_CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                .join(ContactMechanisms).onKey(PARTY_CONTACT_MECHANISM_DETAILS_CONTACT_MECHANISM_FK)
                .join(ContactMechanismDetails).onKey(CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                .join(ContactInet4Addresses).on(ContactMechanismDetails.CONTACT_MECHANISM.eq(ContactInet4Addresses.CONTACT_MECHANISM))
                .where(PartyContactMechanismDetails.PARTY.eq(party.getPrimaryKey()),
                        ContactInet4Addresses.THRU_TIME.eq(Session.MAX_TIME),
                        ContactInet4Addresses.INET4_ADDRESS.eq(inet4Address));

        return partyContactMechanismFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public PartyContactMechanism getPartyContactMechanismByEmailAddress(Party party, String emailAddress) {
        var query = session.getDslContext()
                .select(PartyContactMechanisms.fields())
                .from(PartyContactMechanisms)
                .join(PartyContactMechanismDetails).onKey(PARTY_CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                .join(ContactMechanisms).onKey(PARTY_CONTACT_MECHANISM_DETAILS_CONTACT_MECHANISM_FK)
                .join(ContactMechanismDetails).onKey(CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                .join(ContactEmailAddresses).on(ContactMechanismDetails.CONTACT_MECHANISM.eq(ContactEmailAddresses.CONTACT_MECHANISM))
                .where(PartyContactMechanismDetails.PARTY.eq(party.getPrimaryKey()),
                        ContactEmailAddresses.THRU_TIME.eq(Session.MAX_TIME),
                        ContactEmailAddresses.EMAIL_ADDRESS.eq(emailAddress));

        return partyContactMechanismFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public List<PartyContactMechanism> getPartyContactMechanismsByEmailAddress(String emailAddress) {
        var query = session.applyLimit(session.getDslContext()
                .select(PartyContactMechanisms.fields())
                .from(PartyContactMechanisms)
                .join(PartyContactMechanismDetails).onKey(PARTY_CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                .join(ContactMechanisms).onKey(PARTY_CONTACT_MECHANISM_DETAILS_CONTACT_MECHANISM_FK)
                .join(ContactMechanismDetails).onKey(CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                .join(ContactEmailAddresses).on(ContactMechanismDetails.CONTACT_MECHANISM.eq(ContactEmailAddresses.CONTACT_MECHANISM))
                .where(ContactEmailAddresses.THRU_TIME.eq(Session.MAX_TIME),
                        ContactEmailAddresses.EMAIL_ADDRESS.eq(emailAddress)),
                PartyContactMechanismFactory.class);

        return partyContactMechanismFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Contact Mechanism Aliases
    // --------------------------------------------------------------------------------

    @Inject
    protected PartyContactMechanismAliasFactory partyContactMechanismAliasFactory;

    @Inject
    PartyContactMechanismAliasTransferCache partyContactMechanismAliasTransferCache;

    public PartyContactMechanismAlias createPartyContactMechanismAlias(Party party, ContactMechanism contactMechanism,
            ContactMechanismAliasType contactMechanismAliasType, String alias, BasePK createdBy) {

        var partyContactMechanismAlias = partyContactMechanismAliasFactory.create(
                party, contactMechanism, contactMechanismAliasType, alias, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyContactMechanismAlias.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyContactMechanismAlias;
    }

    public long countPartyContactMechanismAliasByParty(final Party party) {
        return session.getDslContext()
                .selectCount()
                .from(PartyContactMechanismAliases)
                .where(PartyContactMechanismAliases.PARTY.eq(party.getPrimaryKey()),
                        PartyContactMechanismAliases.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyContactMechanismAliasByContactMechanism(final ContactMechanism contactMechanism) {
        return session.getDslContext()
                .selectCount()
                .from(PartyContactMechanismAliases)
                .where(PartyContactMechanismAliases.CONTACT_MECHANISM.eq(contactMechanism.getPrimaryKey()),
                        PartyContactMechanismAliases.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyContactMechanismAliasByContactMechanismAliasType(final ContactMechanismAliasType contactMechanismAliasType) {
        return session.getDslContext()
                .selectCount()
                .from(PartyContactMechanismAliases)
                .where(PartyContactMechanismAliases.CONTACT_MECHANISM_ALIAS_TYPE.eq(contactMechanismAliasType.getPrimaryKey()),
                        PartyContactMechanismAliases.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<PartyContactMechanismAlias> getPartyContactMechanismAliasesByParty(Party party,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyContactMechanismAliases.fields())
                .from(PartyContactMechanismAliases)
                .where(PartyContactMechanismAliases.PARTY.eq(party.getPrimaryKey()),
                        PartyContactMechanismAliases.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyContactMechanismAliases.fields())
                    .from(PartyContactMechanismAliases)
                    .join(ContactMechanismAliasTypes).onKey(PARTY_CONTACT_MECHANISM_ALIASES_CONTACT_MECHANISM_ALIAS_TYPE_FK)
                    .join(ContactMechanismAliasTypeDetails).onKey(CONTACT_MECHANISM_ALIAS_TYPES_LAST_DETAIL_FK)
                    .where(PartyContactMechanismAliases.PARTY.eq(party.getPrimaryKey()),
                            PartyContactMechanismAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ContactMechanismAliasTypeDetails.SORT_ORDER, ContactMechanismAliasTypeDetails.CONTACT_MECHANISM_ALIAS_TYPE_NAME, PartyContactMechanismAliases.ALIAS),
                    PartyContactMechanismAliasFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyContactMechanismAliasFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartyContactMechanismAlias> getPartyContactMechanismAliasesByParty(Party party) {
        return getPartyContactMechanismAliasesByParty(party, EntityPermission.READ_ONLY);
    }
    
    public List<PartyContactMechanismAlias> getPartyContactMechanismAliasByPartyForUpdate(Party party) {
        return getPartyContactMechanismAliasesByParty(party, EntityPermission.READ_WRITE);
    }
    
    private List<PartyContactMechanismAlias> getPartyContactMechanismAliasesByContactMechanism(ContactMechanism contactMechanism,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyContactMechanismAliases.fields())
                    .from(PartyContactMechanismAliases)
                    .join(ContactMechanismAliasTypes).onKey(PARTY_CONTACT_MECHANISM_ALIASES_CONTACT_MECHANISM_ALIAS_TYPE_FK)
                    .join(ContactMechanismAliasTypeDetails).onKey(CONTACT_MECHANISM_ALIAS_TYPES_LAST_DETAIL_FK)
                    .where(PartyContactMechanismAliases.CONTACT_MECHANISM.eq(contactMechanism.getPrimaryKey()),
                            PartyContactMechanismAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ContactMechanismAliasTypeDetails.SORT_ORDER, ContactMechanismAliasTypeDetails.CONTACT_MECHANISM_ALIAS_TYPE_NAME),
                    PartyContactMechanismAliasFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyContactMechanismAliases.fields())
                    .from(PartyContactMechanismAliases)
                    .where(PartyContactMechanismAliases.CONTACT_MECHANISM.eq(contactMechanism.getPrimaryKey()),
                            PartyContactMechanismAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyContactMechanismAliasFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartyContactMechanismAlias> getPartyContactMechanismAliasesByContactMechanism(ContactMechanism contactMechanism) {
        return getPartyContactMechanismAliasesByContactMechanism(contactMechanism, EntityPermission.READ_ONLY);
    }
    
    public List<PartyContactMechanismAlias> getPartyContactMechanismAliasesByContactMechanismForUpdate(ContactMechanism contactMechanism) {
        return getPartyContactMechanismAliasesByContactMechanism(contactMechanism, EntityPermission.READ_WRITE);
    }
    
    private PartyContactMechanismAlias getPartyContactMechanismAliasByAlias(Party party,
            ContactMechanismAliasType contactMechanismAliasType, String alias, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyContactMechanismAliases.fields())
                .from(PartyContactMechanismAliases)
                .where(PartyContactMechanismAliases.PARTY.eq(party.getPrimaryKey()),
                        PartyContactMechanismAliases.CONTACT_MECHANISM_ALIAS_TYPE.eq(contactMechanismAliasType.getPrimaryKey()),
                        PartyContactMechanismAliases.ALIAS.eq(alias),
                        PartyContactMechanismAliases.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyContactMechanismAliasFactory.getEntityFromQuery(entityPermission, query);
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
        return partyContactMechanismAliasTransferCache.getPartyContactMechanismAliasTransfer(userVisit, partyContactMechanismAlias);
    }
    
    public void deletePartyContactMechanismAlias(PartyContactMechanismAlias partyContactMechanismAlias, BasePK deletedBy) {
        partyContactMechanismAlias.setThruTime(session.getStartTime());
        
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

    @Inject
    protected PartyContactMechanismPurposeDetailFactory partyContactMechanismPurposeDetailFactory;

    @Inject
    protected PartyContactMechanismPurposeFactory partyContactMechanismPurposeFactory;

    @Inject
    PartyContactMechanismPurposeTransferCache partyContactMechanismPurposeTransferCache;

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

        var partyContactMechanismPurpose = partyContactMechanismPurposeFactory.create();
        var partyContactMechanismPurposeDetail = partyContactMechanismPurposeDetailFactory.create(
                partyContactMechanismPurpose, partyContactMechanism, contactMechanismPurpose, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        partyContactMechanismPurpose = partyContactMechanismPurposeFactory.getEntityFromPK(EntityPermission.READ_WRITE, partyContactMechanismPurpose.getPrimaryKey());
        partyContactMechanismPurpose.setActiveDetail(partyContactMechanismPurposeDetail);
        partyContactMechanismPurpose.setLastDetail(partyContactMechanismPurposeDetail);
        partyContactMechanismPurpose.store();
        
        sendEvent(partyContactMechanism.getPrimaryKey(), EventTypes.MODIFY, partyContactMechanismPurpose.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyContactMechanismPurpose;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PartyContactMechanismPurpose */
    public PartyContactMechanismPurpose getPartyContactMechanismPurposeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new PartyContactMechanismPurposePK(entityInstance.getEntityUniqueId());

        return partyContactMechanismPurposeFactory.getEntityFromPK(entityPermission, pk);
    }

    public PartyContactMechanismPurpose getPartyContactMechanismPurposeByEntityInstance(EntityInstance entityInstance) {
        return getPartyContactMechanismPurposeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PartyContactMechanismPurpose getPartyContactMechanismPurposeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getPartyContactMechanismPurposeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countPartyContactMechanismPurposesByPartyContactMechanism(final PartyContactMechanism partyContactMechanism) {
        return session.getDslContext()
                .selectCount()
                .from(PartyContactMechanismPurposes)
                .join(PartyContactMechanismPurposeDetails).onKey(PARTY_CONTACT_MECHANISM_PURPOSES_ACTIVE_DETAIL_FK)
                .where(PartyContactMechanismPurposeDetails.PARTY_CONTACT_MECHANISM.eq(partyContactMechanism.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyContactMechanismPurposesByContactMechanismPurpose(final ContactMechanismPurpose contactMechanismPurpose) {
        return session.getDslContext()
                .selectCount()
                .from(PartyContactMechanismPurposes)
                .join(PartyContactMechanismPurposeDetails).onKey(PARTY_CONTACT_MECHANISM_PURPOSES_ACTIVE_DETAIL_FK)
                .where(PartyContactMechanismPurposeDetails.CONTACT_MECHANISM_PURPOSE.eq(contactMechanismPurpose.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PartyContactMechanismPurpose getDefaultPartyContactMechanismPurpose(PartyPK partyPK, ContactMechanismPurpose contactMechanismPurpose, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyContactMechanismPurposes.fields())
                .from(PartyContactMechanismPurposes)
                .join(PartyContactMechanismPurposeDetails).onKey(PARTY_CONTACT_MECHANISM_PURPOSES_ACTIVE_DETAIL_FK)
                .join(PartyContactMechanisms).onKey(PARTY_CONTACT_MECHANISM_PURPOSE_DETAILS_PARTY_CONTACT_MECHANISM_FK)
                .join(PartyContactMechanismDetails).onKey(PARTY_CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                .where(PartyContactMechanismDetails.PARTY.eq(partyPK),
                        PartyContactMechanismPurposeDetails.CONTACT_MECHANISM_PURPOSE.eq(contactMechanismPurpose.getPrimaryKey()),
                        PartyContactMechanismPurposeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyContactMechanismPurposeFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(PartyContactMechanismPurposes.fields())
                .from(PartyContactMechanismPurposes)
                .join(PartyContactMechanismPurposeDetails).onKey(PARTY_CONTACT_MECHANISM_PURPOSES_ACTIVE_DETAIL_FK)
                .where(PartyContactMechanismPurposeDetails.PARTY_CONTACT_MECHANISM.eq(partyContactMechanism.getPrimaryKey()),
                        PartyContactMechanismPurposeDetails.CONTACT_MECHANISM_PURPOSE.eq(contactMechanismPurpose.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyContactMechanismPurposeFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(PartyContactMechanismPurposes.fields())
                .from(PartyContactMechanismPurposes)
                .join(PartyContactMechanismPurposeDetails).onKey(PARTY_CONTACT_MECHANISM_PURPOSES_ACTIVE_DETAIL_FK)
                .join(PartyContactMechanisms).onKey(PARTY_CONTACT_MECHANISM_PURPOSE_DETAILS_PARTY_CONTACT_MECHANISM_FK)
                .join(PartyContactMechanismDetails).onKey(PARTY_CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                .where(PartyContactMechanismDetails.PARTY.eq(party.getPrimaryKey()),
                        PartyContactMechanismPurposeDetails.CONTACT_MECHANISM_PURPOSE.eq(contactMechanismPurpose.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyContactMechanismPurposes.fields())
                    .from(PartyContactMechanismPurposes)
                    .join(PartyContactMechanismPurposeDetails).onKey(PARTY_CONTACT_MECHANISM_PURPOSES_ACTIVE_DETAIL_FK)
                    .join(PartyContactMechanisms).onKey(PARTY_CONTACT_MECHANISM_PURPOSE_DETAILS_PARTY_CONTACT_MECHANISM_FK)
                    .join(PartyContactMechanismDetails).onKey(PARTY_CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                    .join(ContactMechanisms).onKey(PARTY_CONTACT_MECHANISM_DETAILS_CONTACT_MECHANISM_FK)
                    .join(ContactMechanismDetails).onKey(CONTACT_MECHANISMS_LAST_DETAIL_FK)
                    .where(PartyContactMechanismDetails.PARTY.eq(party.getPrimaryKey()),
                            PartyContactMechanismPurposeDetails.CONTACT_MECHANISM_PURPOSE.eq(contactMechanismPurpose.getPrimaryKey()))
                    .orderBy(PartyContactMechanismPurposeDetails.SORT_ORDER, ContactMechanismDetails.CONTACT_MECHANISM_NAME),
                    PartyContactMechanismPurposeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyContactMechanismPurposeFactory.getEntitiesFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyContactMechanismPurposes.fields())
                    .from(PartyContactMechanismPurposes)
                    .join(PartyContactMechanismPurposeDetails).onKey(PARTY_CONTACT_MECHANISM_PURPOSES_ACTIVE_DETAIL_FK)
                    .join(ContactMechanismPurposes).onKey(PARTY_CONTACT_MECHANISM_PURPOSE_DETAILS_CONTACT_MECHANISM_PURPOSE_FK)
                    .where(PartyContactMechanismPurposeDetails.PARTY_CONTACT_MECHANISM.eq(partyContactMechanism.getPrimaryKey()))
                    .orderBy(ContactMechanismPurposes.CONTACT_MECHANISM_PURPOSE_NAME),
                    PartyContactMechanismPurposeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyContactMechanismPurposes.fields())
                    .from(PartyContactMechanismPurposes)
                    .join(PartyContactMechanismPurposeDetails).onKey(PARTY_CONTACT_MECHANISM_PURPOSES_ACTIVE_DETAIL_FK)
                    .where(PartyContactMechanismPurposeDetails.PARTY_CONTACT_MECHANISM.eq(partyContactMechanism.getPrimaryKey()))
                    .forUpdate();
        };

        return partyContactMechanismPurposeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartyContactMechanismPurpose> getPartyContactMechanismPurposesByPartyContactMechanism(PartyContactMechanism partyContactMechanism) {
        return getPartyContactMechanismPurposesByPartyContactMechanism(partyContactMechanism, EntityPermission.READ_ONLY);
    }
    
    public List<PartyContactMechanismPurpose> getPartyContactMechanismPurposesByPartyContactMechanismForUpdate(PartyContactMechanism partyContactMechanism) {
        return getPartyContactMechanismPurposesByPartyContactMechanism(partyContactMechanism, EntityPermission.READ_WRITE);
    }
    
    public PartyContactMechanismPurposeTransfer getPartyContactMechanismPurposeTransfer(UserVisit userVisit, PartyContactMechanismPurpose partyContactMechanismPurpose) {
        return partyContactMechanismPurposeTransferCache.getPartyContactMechanismPurposeTransfer(userVisit, partyContactMechanismPurpose);
    }
    
    public List<PartyContactMechanismPurposeTransfer> getPartyContactMechanismPurposeTransfersByPartyContactMechanism(UserVisit userVisit,
            PartyContactMechanism partyContactMechanism) {
        var entities = getPartyContactMechanismPurposesByPartyContactMechanism(partyContactMechanism);
        List<PartyContactMechanismPurposeTransfer> transfers = new ArrayList<>(entities.size());

        entities.forEach((entity) ->
                transfers.add(partyContactMechanismPurposeTransferCache.getPartyContactMechanismPurposeTransfer(userVisit, entity))
        );
        
        return transfers;
    }
    
    private void updatePartyContactMechanismPurposeFromValue(PartyContactMechanismPurposeDetailValue partyContactMechanismPurposeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(partyContactMechanismPurposeDetailValue.hasBeenModified()) {
            var partyContactMechanismPurpose = partyContactMechanismPurposeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     partyContactMechanismPurposeDetailValue.getPartyContactMechanismPurposePK());
            var partyContactMechanismPurposeDetail = partyContactMechanismPurpose.getActiveDetailForUpdate();
            
            partyContactMechanismPurposeDetail.setThruTime(session.getStartTime());
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
            
            partyContactMechanismPurposeDetail = partyContactMechanismPurposeDetailFactory.create(
                    partyContactMechanismPurposePK, partyContactMechanism.getPrimaryKey(), contactMechanismPurpose.getPrimaryKey(),
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            partyContactMechanismPurpose.setActiveDetail(partyContactMechanismPurposeDetail);
            partyContactMechanismPurpose.setLastDetail(partyContactMechanismPurposeDetail);
            
            sendEvent(partyContactMechanism.getPrimaryKey(), EventTypes.MODIFY, partyContactMechanismPurpose.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updatePartyContactMechanismPurposeFromValue(PartyContactMechanismPurposeDetailValue partyContactMechanismPurposeDetailValue, BasePK updatedBy) {
        updatePartyContactMechanismPurposeFromValue(partyContactMechanismPurposeDetailValue, true, updatedBy);
    }
    
    public void deletePartyContactMechanismPurpose(PartyContactMechanismPurpose partyContactMechanismPurpose, BasePK deletedBy) {
        var partyContactMechanismPurposeDetail = partyContactMechanismPurpose.getLastDetailForUpdate();
        partyContactMechanismPurposeDetail.setThruTime(session.getStartTime());
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
        
        sendEvent(partyContactMechanism.getPrimaryKey(), EventTypes.MODIFY, partyContactMechanismPurpose.getPrimaryKey(), EventTypes.DELETE, deletedBy);
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

    @Inject
    protected PartyContactMechanismRelationshipFactory partyContactMechanismRelationshipFactory;

    @Inject
    PartyContactMechanismRelationshipTransferCache partyContactMechanismRelationshipTransferCache;

    public PartyContactMechanismRelationship createPartyContactMechanismRelationship(PartyContactMechanism fromPartyContactMechanism,
            PartyContactMechanism toPartyContactMechanism, BasePK createdBy) {
        var partyContactMechanismRelationship = partyContactMechanismRelationshipFactory.create(fromPartyContactMechanism,
                toPartyContactMechanism, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(fromPartyContactMechanism.getPrimaryKey(), EventTypes.MODIFY, partyContactMechanismRelationship.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyContactMechanismRelationship;
    }
    
    public boolean partyContactMechanismRelationshipExists(PartyContactMechanism fromPartyContactMechanism, PartyContactMechanism toPartyContactMechanism) {
        return session.getDslContext()
                .selectCount()
                .from(PartyContactMechanismRelationships)
                .where(PartyContactMechanismRelationships.FROM_PARTY_CONTACT_MECHANISM.eq(fromPartyContactMechanism.getPrimaryKey()),
                        PartyContactMechanismRelationships.TO_PARTY_CONTACT_MECHANISM.eq(toPartyContactMechanism.getPrimaryKey()),
                        PartyContactMechanismRelationships.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L) == 1L;
    }

    public long countPartyContactMechanismRelationshipsByFromPartyContactMechanism(PartyContactMechanism fromPartyContactMechanism) {
        return session.getDslContext()
                .selectCount()
                .from(PartyContactMechanismRelationships)
                .where(PartyContactMechanismRelationships.FROM_PARTY_CONTACT_MECHANISM.eq(fromPartyContactMechanism.getPrimaryKey()),
                        PartyContactMechanismRelationships.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyContactMechanismRelationshipsByToPartyContactMechanism(PartyContactMechanism toPartyContactMechanism) {
        return session.getDslContext()
                .selectCount()
                .from(PartyContactMechanismRelationships)
                .where(PartyContactMechanismRelationships.TO_PARTY_CONTACT_MECHANISM.eq(toPartyContactMechanism.getPrimaryKey()),
                        PartyContactMechanismRelationships.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public PartyContactMechanismRelationship getPartyContactMechanismRelationship(PartyContactMechanism fromPartyContactMechanism,
            PartyContactMechanism toPartyContactMechanism, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyContactMechanismRelationships.fields())
                .from(PartyContactMechanismRelationships)
                .where(PartyContactMechanismRelationships.FROM_PARTY_CONTACT_MECHANISM.eq(fromPartyContactMechanism.getPrimaryKey()),
                        PartyContactMechanismRelationships.TO_PARTY_CONTACT_MECHANISM.eq(toPartyContactMechanism.getPrimaryKey()),
                        PartyContactMechanismRelationships.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyContactMechanismRelationshipFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public PartyContactMechanismRelationship getPartyContactMechanismRelationship(PartyContactMechanism fromPartyContactMechanism,
            PartyContactMechanism toPartyContactMechanism) {
        return getPartyContactMechanismRelationship(fromPartyContactMechanism, toPartyContactMechanism, EntityPermission.READ_ONLY);
    }
    
    public PartyContactMechanismRelationship getPartyContactMechanismRelationshipForUpdate(PartyContactMechanism fromPartyContactMechanism,
            PartyContactMechanism toPartyContactMechanism) {
        return getPartyContactMechanismRelationship(fromPartyContactMechanism, toPartyContactMechanism, EntityPermission.READ_WRITE);
    }
    
    public List<PartyContactMechanismRelationship> getPartyContactMechanismRelationshipsByFromPartyContactMechanism(PartyContactMechanism fromPartyContactMechanism, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyContactMechanismRelationships.fields())
                    .from(PartyContactMechanismRelationships)
                    .join(PartyContactMechanisms).onKey(PARTY_CONTACT_MECHANISM_RELATIONSHIPS_TO_PARTY_CONTACT_MECHANISM_FK)
                    .join(PartyContactMechanismDetails).onKey(PARTY_CONTACT_MECHANISMS_LAST_DETAIL_FK)
                    .join(ContactMechanisms).onKey(PARTY_CONTACT_MECHANISM_DETAILS_CONTACT_MECHANISM_FK)
                    .join(ContactMechanismDetails).onKey(CONTACT_MECHANISMS_LAST_DETAIL_FK)
                    .where(PartyContactMechanismRelationships.FROM_PARTY_CONTACT_MECHANISM.eq(fromPartyContactMechanism.getPrimaryKey()),
                            PartyContactMechanismRelationships.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyContactMechanismDetails.SORT_ORDER, ContactMechanismDetails.CONTACT_MECHANISM_NAME),
                    PartyContactMechanismRelationshipFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyContactMechanismRelationships.fields())
                    .from(PartyContactMechanismRelationships)
                    .where(PartyContactMechanismRelationships.FROM_PARTY_CONTACT_MECHANISM.eq(fromPartyContactMechanism.getPrimaryKey()),
                            PartyContactMechanismRelationships.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyContactMechanismRelationshipFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyContactMechanismRelationship> getPartyContactMechanismRelationshipsByFromPartyContactMechanism(PartyContactMechanism fromPartyContactMechanism) {
        return getPartyContactMechanismRelationshipsByFromPartyContactMechanism(fromPartyContactMechanism, EntityPermission.READ_ONLY);
    }

    public List<PartyContactMechanismRelationship> getPartyContactMechanismRelationshipsByFromPartyContactMechanismForUpdate(PartyContactMechanism fromPartyContactMechanism) {
        return getPartyContactMechanismRelationshipsByFromPartyContactMechanism(fromPartyContactMechanism, EntityPermission.READ_WRITE);
    }

    public List<PartyContactMechanismRelationship> getPartyContactMechanismRelationshipsByToPartyContactMechanism(PartyContactMechanism toPartyContactMechanism,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyContactMechanismRelationships.fields())
                    .from(PartyContactMechanismRelationships)
                    .join(PartyContactMechanisms).onKey(PARTY_CONTACT_MECHANISM_RELATIONSHIPS_FROM_PARTY_CONTACT_MECHANISM_FK)
                    .join(PartyContactMechanismDetails).onKey(PARTY_CONTACT_MECHANISMS_LAST_DETAIL_FK)
                    .join(ContactMechanisms).onKey(PARTY_CONTACT_MECHANISM_DETAILS_CONTACT_MECHANISM_FK)
                    .join(ContactMechanismDetails).onKey(CONTACT_MECHANISMS_LAST_DETAIL_FK)
                    .where(PartyContactMechanismRelationships.TO_PARTY_CONTACT_MECHANISM.eq(toPartyContactMechanism.getPrimaryKey()),
                            PartyContactMechanismRelationships.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyContactMechanismDetails.SORT_ORDER, ContactMechanismDetails.CONTACT_MECHANISM_NAME),
                    PartyContactMechanismRelationshipFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyContactMechanismRelationships.fields())
                    .from(PartyContactMechanismRelationships)
                    .where(PartyContactMechanismRelationships.TO_PARTY_CONTACT_MECHANISM.eq(toPartyContactMechanism.getPrimaryKey()),
                            PartyContactMechanismRelationships.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyContactMechanismRelationshipFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyContactMechanismRelationship> getPartyContactMechanismRelationshipsByToPartyContactMechanism(PartyContactMechanism toPartyContactMechanism) {
        return getPartyContactMechanismRelationshipsByToPartyContactMechanism(toPartyContactMechanism, EntityPermission.READ_ONLY);
    }

    public List<PartyContactMechanismRelationship> getPartyContactMechanismRelationshipsByToPartyContactMechanismForUpdate(PartyContactMechanism toPartyContactMechanism) {
        return getPartyContactMechanismRelationshipsByToPartyContactMechanism(toPartyContactMechanism, EntityPermission.READ_WRITE);
    }

    public PartyContactMechanismRelationshipTransfer getPartyContactMechanismRelationshipTransfer(UserVisit userVisit, PartyContactMechanismRelationship partyContactMechanismRelationship) {
        return partyContactMechanismRelationshipTransferCache.getPartyContactMechanismRelationshipTransfer(userVisit, partyContactMechanismRelationship);
    }

    public List<PartyContactMechanismRelationshipTransfer> getPartyContactMechanismRelationshipTransfers(UserVisit userVisit, Collection<PartyContactMechanismRelationship> partyContactMechanismRelationships) {
        List<PartyContactMechanismRelationshipTransfer> partyContactMechanismRelationshipTransfers = new ArrayList<>(partyContactMechanismRelationships.size());
        
        partyContactMechanismRelationships.forEach((partyContactMechanismRelationship) ->
                partyContactMechanismRelationshipTransfers.add(partyContactMechanismRelationshipTransferCache.getPartyContactMechanismRelationshipTransfer(userVisit, partyContactMechanismRelationship))
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
        partyContactMechanismRelationship.setThruTime(session.getStartTime());
        partyContactMechanismRelationship.store();

        sendEvent(partyContactMechanismRelationship.getFromPartyContactMechanism().getPrimaryKey(), EventTypes.MODIFY, partyContactMechanismRelationship.getPrimaryKey(), EventTypes.DELETE, deletedBy);
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

    @Inject
    protected PostalAddressElementTypeFactory postalAddressElementTypeFactory;

    @Inject
    PostalAddressElementTypeTransferCache postalAddressElementTypeTransferCache;

    public PostalAddressElementType createPostalAddressElementType(String postalAddressElementTypeName, Boolean isDefault,
            Integer sortOrder) {
        return postalAddressElementTypeFactory.create(postalAddressElementTypeName, isDefault, sortOrder);
    }

    public long countPostalAddressElementType() {
        return session.getDslContext()
                .selectCount()
                .from(PostalAddressElementTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public PostalAddressElementType getPostalAddressElementTypeByName(String postalAddressElementTypeName) {
        var query = session.getDslContext()
                .select(PostalAddressElementTypes.fields())
                .from(PostalAddressElementTypes)
                .where(PostalAddressElementTypes.POSTAL_ADDRESS_ELEMENT_TYPE_NAME.eq(postalAddressElementTypeName));

        return postalAddressElementTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public List<PostalAddressElementType> getPostalAddressElementTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(PostalAddressElementTypes.fields())
                .from(PostalAddressElementTypes)
                .orderBy(PostalAddressElementTypes.SORT_ORDER, PostalAddressElementTypes.POSTAL_ADDRESS_ELEMENT_TYPE_NAME),
                PostalAddressElementTypeFactory.class);

        return postalAddressElementTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
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
        return postalAddressElementTypeTransferCache.getPostalAddressElementTypeTransfer(userVisit, postalAddressElementType);
    }
    
    // --------------------------------------------------------------------------------
    //   Postal Address Element Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected PostalAddressElementTypeDescriptionFactory postalAddressElementTypeDescriptionFactory;

    public PostalAddressElementTypeDescription createPostalAddressElementTypeDescription(PostalAddressElementType postalAddressElementType,
            Language language, String description) {
        return postalAddressElementTypeDescriptionFactory.create(postalAddressElementType, language,
                description);
    }
    
    public PostalAddressElementTypeDescription getPostalAddressElementTypeDescription(PostalAddressElementType postalAddressElementType, Language language) {
        var query = session.getDslContext()
                .select(PostalAddressElementTypeDescriptions.fields())
                .from(PostalAddressElementTypeDescriptions)
                .where(PostalAddressElementTypeDescriptions.POSTAL_ADDRESS_ELEMENT_TYPE.eq(postalAddressElementType.getPrimaryKey()),
                        PostalAddressElementTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return postalAddressElementTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public String getBestPostalAddressElementTypeDescription(PostalAddressElementType postalAddressElementType, Language language) {
        String description;
        var postalAddressElementTypeDescription = getPostalAddressElementTypeDescription(postalAddressElementType, language);
        
        if(postalAddressElementTypeDescription == null && !language.getIsDefault()) {
            postalAddressElementTypeDescription = getPostalAddressElementTypeDescription(postalAddressElementType, partyControl.getDefaultLanguage());
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

    @Inject
    protected PostalAddressFormatDetailFactory postalAddressFormatDetailFactory;

    @Inject
    protected PostalAddressFormatFactory postalAddressFormatFactory;

    @Inject
    PostalAddressFormatTransferCache postalAddressFormatTransferCache;

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

        var postalAddressFormat = postalAddressFormatFactory.create();
        var postalAddressFormatDetail = postalAddressFormatDetailFactory.create(postalAddressFormat,
                postalAddressFormatName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        postalAddressFormat = postalAddressFormatFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                postalAddressFormat.getPrimaryKey());
        postalAddressFormat.setActiveDetail(postalAddressFormatDetail);
        postalAddressFormat.setLastDetail(postalAddressFormatDetail);
        postalAddressFormat.store();
        
        sendEvent(postalAddressFormat.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return postalAddressFormat;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PostalAddressFormat */
    public PostalAddressFormat getPostalAddressFormatByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new PostalAddressFormatPK(entityInstance.getEntityUniqueId());

        return postalAddressFormatFactory.getEntityFromPK(entityPermission, pk);
    }

    public PostalAddressFormat getPostalAddressFormatByEntityInstance(EntityInstance entityInstance) {
        return getPostalAddressFormatByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PostalAddressFormat getPostalAddressFormatByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getPostalAddressFormatByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countPostalAddressFormats() {
        return session.getDslContext()
                .selectCount()
                .from(PostalAddressFormats)
                .join(PostalAddressFormatDetails).onKey(POSTAL_ADDRESS_FORMATS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<PostalAddressFormat> getPostalAddressFormats(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PostalAddressFormats.fields())
                .from(PostalAddressFormats)
                .join(PostalAddressFormatDetails).onKey(POSTAL_ADDRESS_FORMATS_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PostalAddressFormatDetails.SORT_ORDER, PostalAddressFormatDetails.POSTAL_ADDRESS_FORMAT_NAME),
                    PostalAddressFormatFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return postalAddressFormatFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PostalAddressFormat> getPostalAddressFormats() {
        return getPostalAddressFormats(EntityPermission.READ_ONLY);
    }
    
    public List<PostalAddressFormat> getPostalAddressFormatsForUpdate() {
        return getPostalAddressFormats(EntityPermission.READ_WRITE);
    }
    
    private PostalAddressFormat getDefaultPostalAddressFormat(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PostalAddressFormats.fields())
                .from(PostalAddressFormats)
                .join(PostalAddressFormatDetails).onKey(POSTAL_ADDRESS_FORMATS_ACTIVE_DETAIL_FK)
                .where(PostalAddressFormatDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return postalAddressFormatFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(PostalAddressFormats.fields())
                .from(PostalAddressFormats)
                .join(PostalAddressFormatDetails).onKey(POSTAL_ADDRESS_FORMATS_ACTIVE_DETAIL_FK)
                .where(PostalAddressFormatDetails.POSTAL_ADDRESS_FORMAT_NAME.eq(postalAddressFormatName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return postalAddressFormatFactory.getEntityFromQuery(entityPermission, query);
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
        return postalAddressFormatTransferCache.getPostalAddressFormatTransfer(userVisit, postalAddressFormat);
    }
    
    public List<PostalAddressFormatTransfer> getPostalAddressFormatTransfers(UserVisit userVisit, Collection<PostalAddressFormat> postalAddressFormats) {
        List<PostalAddressFormatTransfer> postalAddressFormatTransfers = new ArrayList<>(postalAddressFormats.size());

        postalAddressFormats.forEach((postalAddressFormat) ->
                postalAddressFormatTransfers.add(postalAddressFormatTransferCache.getPostalAddressFormatTransfer(userVisit, postalAddressFormat))
        );

        return postalAddressFormatTransfers;
    }

    public List<PostalAddressFormatTransfer> getPostalAddressFormatTransfers(UserVisit userVisit) {
        return getPostalAddressFormatTransfers(userVisit, getPostalAddressFormats());
    }
    
    private void updatePostalAddressFormatFromValue(PostalAddressFormatDetailValue postalAddressFormatDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(postalAddressFormatDetailValue.hasBeenModified()) {
            var postalAddressFormat = postalAddressFormatFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     postalAddressFormatDetailValue.getPostalAddressFormatPK());
            var postalAddressFormatDetail = postalAddressFormat.getActiveDetailForUpdate();
            
            postalAddressFormatDetail.setThruTime(session.getStartTime());
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
            
            postalAddressFormatDetail = postalAddressFormatDetailFactory.create(postalAddressFormatPK, postalAddressFormatName,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
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
        postalAddressFormatDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected PostalAddressFormatDescriptionFactory postalAddressFormatDescriptionFactory;

    @Inject
    PostalAddressFormatDescriptionTransferCache postalAddressFormatDescriptionTransferCache;

    public PostalAddressFormatDescription createPostalAddressFormatDescription(PostalAddressFormat postalAddressFormat,
            Language language, String description, BasePK createdBy) {
        var postalAddressFormatDescription = postalAddressFormatDescriptionFactory.create(
                postalAddressFormat, language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(postalAddressFormat.getPrimaryKey(), EventTypes.MODIFY, postalAddressFormatDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return postalAddressFormatDescription;
    }
    
    private PostalAddressFormatDescription getPostalAddressFormatDescription(PostalAddressFormat postalAddressFormat, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PostalAddressFormatDescriptions.fields())
                .from(PostalAddressFormatDescriptions)
                .where(PostalAddressFormatDescriptions.POSTAL_ADDRESS_FORMAT.eq(postalAddressFormat.getPrimaryKey()),
                        PostalAddressFormatDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        PostalAddressFormatDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return postalAddressFormatDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PostalAddressFormatDescriptions.fields())
                    .from(PostalAddressFormatDescriptions)
                    .join(Languages).onKey(POSTAL_ADDRESS_FORMAT_DESCRIPTIONS_LANGUAGE_FK)
                    .where(PostalAddressFormatDescriptions.POSTAL_ADDRESS_FORMAT.eq(postalAddressFormat.getPrimaryKey()),
                            PostalAddressFormatDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    PostalAddressFormatDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PostalAddressFormatDescriptions.fields())
                    .from(PostalAddressFormatDescriptions)
                    .where(PostalAddressFormatDescriptions.POSTAL_ADDRESS_FORMAT.eq(postalAddressFormat.getPrimaryKey()),
                            PostalAddressFormatDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return postalAddressFormatDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
            postalAddressFormatDescription = getPostalAddressFormatDescription(postalAddressFormat, partyControl.getDefaultLanguage());
        }
        
        if(postalAddressFormatDescription == null) {
            description = postalAddressFormat.getLastDetail().getPostalAddressFormatName();
        } else {
            description = postalAddressFormatDescription.getDescription();
        }
        
        return description;
    }
    
    public PostalAddressFormatDescriptionTransfer getPostalAddressFormatDescriptionTransfer(UserVisit userVisit, PostalAddressFormatDescription postalAddressFormatDescription) {
        return postalAddressFormatDescriptionTransferCache.getPostalAddressFormatDescriptionTransfer(userVisit, postalAddressFormatDescription);
    }
    
    public List<PostalAddressFormatDescriptionTransfer> getPostalAddressFormatDescriptionTransfersByPostalAddressFormat(UserVisit userVisit, PostalAddressFormat postalAddressFormat) {
        var postalAddressFormatDescriptions = getPostalAddressFormatDescriptionsByPostalAddressFormat(postalAddressFormat);
        List<PostalAddressFormatDescriptionTransfer> postalAddressFormatDescriptionTransfers = new ArrayList<>(postalAddressFormatDescriptions.size());
        
        postalAddressFormatDescriptions.forEach((postalAddressFormatDescription) ->
                postalAddressFormatDescriptionTransfers.add(postalAddressFormatDescriptionTransferCache.getPostalAddressFormatDescriptionTransfer(userVisit, postalAddressFormatDescription))
        );
        
        return postalAddressFormatDescriptionTransfers;
    }
    
    public void updatePostalAddressFormatDescriptionFromValue(PostalAddressFormatDescriptionValue postalAddressFormatDescriptionValue, BasePK updatedBy) {
        if(postalAddressFormatDescriptionValue.hasBeenModified()) {
            var postalAddressFormatDescription = postalAddressFormatDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, postalAddressFormatDescriptionValue.getPrimaryKey());
            
            postalAddressFormatDescription.setThruTime(session.getStartTime());
            postalAddressFormatDescription.store();

            var postalAddressFormat = postalAddressFormatDescription.getPostalAddressFormat();
            var language = postalAddressFormatDescription.getLanguage();
            var description = postalAddressFormatDescriptionValue.getDescription();
            
            postalAddressFormatDescription = postalAddressFormatDescriptionFactory.create(postalAddressFormat, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(postalAddressFormat.getPrimaryKey(), EventTypes.MODIFY, postalAddressFormatDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePostalAddressFormatDescription(PostalAddressFormatDescription postalAddressFormatDescription, BasePK deletedBy) {
        postalAddressFormatDescription.setThruTime(session.getStartTime());
        
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

    @Inject
    protected PostalAddressLineDetailFactory postalAddressLineDetailFactory;

    @Inject
    protected PostalAddressLineFactory postalAddressLineFactory;

    @Inject
    PostalAddressLineTransferCache postalAddressLineTransferCache;

    public PostalAddressLine createPostalAddressLine(PostalAddressFormat postalAddressFormat, Integer postalAddressLineSortOrder,
            String prefix, Boolean alwaysIncludePrefix, String suffix, Boolean alwaysIncludeSuffix, Boolean collapseIfEmpty,
            BasePK createdBy) {
        var postalAddressLine = postalAddressLineFactory.create();
        var postalAddressLineDetail = postalAddressLineDetailFactory.create(
                postalAddressLine, postalAddressFormat, postalAddressLineSortOrder, prefix, alwaysIncludePrefix, suffix,
                alwaysIncludeSuffix, collapseIfEmpty, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        postalAddressLine = postalAddressLineFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                postalAddressLine.getPrimaryKey());
        postalAddressLine.setActiveDetail(postalAddressLineDetail);
        postalAddressLine.setLastDetail(postalAddressLineDetail);
        postalAddressLine.store();
        
        sendEvent(postalAddressFormat.getPrimaryKey(), EventTypes.MODIFY, postalAddressLine.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return postalAddressLine;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PostalAddressLine */
    public PostalAddressLine getPostalAddressLineByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new PostalAddressLinePK(entityInstance.getEntityUniqueId());

        return postalAddressLineFactory.getEntityFromPK(entityPermission, pk);
    }

    public PostalAddressLine getPostalAddressLineByEntityInstance(EntityInstance entityInstance) {
        return getPostalAddressLineByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PostalAddressLine getPostalAddressLineByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getPostalAddressLineByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countPostalAddressLinesByPostalAddressFormat(final PostalAddressFormat postalAddressFormat) {
        return session.getDslContext()
                .selectCount()
                .from(PostalAddressLines)
                .join(PostalAddressLineDetails).onKey(POSTAL_ADDRESS_LINES_ACTIVE_DETAIL_FK)
                .where(PostalAddressLineDetails.POSTAL_ADDRESS_FORMAT.eq(postalAddressFormat.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PostalAddressLine getPostalAddressLine(PostalAddressFormat postalAddressFormat, Integer postalAddressLineSortOrder,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PostalAddressLines.fields())
                .from(PostalAddressLines)
                .join(PostalAddressLineDetails).onKey(POSTAL_ADDRESS_LINES_ACTIVE_DETAIL_FK)
                .where(PostalAddressLineDetails.POSTAL_ADDRESS_FORMAT.eq(postalAddressFormat.getPrimaryKey()),
                        PostalAddressLineDetails.POSTAL_ADDRESS_LINE_SORT_ORDER.eq(postalAddressLineSortOrder));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return postalAddressLineFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(PostalAddressLines.fields())
                .from(PostalAddressLines)
                .join(PostalAddressLineDetails).onKey(POSTAL_ADDRESS_LINES_ACTIVE_DETAIL_FK)
                .where(PostalAddressLineDetails.POSTAL_ADDRESS_FORMAT.eq(postalAddressFormat.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PostalAddressLineDetails.POSTAL_ADDRESS_LINE_SORT_ORDER),
                    PostalAddressLineFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return postalAddressLineFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PostalAddressLine> getPostalAddressLinesByPostalAddressFormat(PostalAddressFormat postalAddressFormat) {
        return getPostalAddressLinesByPostalAddressFormat(postalAddressFormat, EntityPermission.READ_ONLY);
    }
    
    public List<PostalAddressLine> getPostalAddressLinesByPostalAddressFormatForUpdate(PostalAddressFormat postalAddressFormat) {
        return getPostalAddressLinesByPostalAddressFormat(postalAddressFormat, EntityPermission.READ_WRITE);
    }
    
    public PostalAddressLineTransfer getPostalAddressLineTransfer(UserVisit userVisit, PostalAddressLine postalAddressLine) {
        return postalAddressLineTransferCache.getPostalAddressLineTransfer(userVisit, postalAddressLine);
    }

    public List<PostalAddressLineTransfer> getPostalAddressLineTransfers(UserVisit userVisit, Collection<PostalAddressLine> postalAddressLines) {
        List<PostalAddressLineTransfer> postalAddressLineTransfers = new ArrayList<>(postalAddressLines.size());

        postalAddressLines.forEach((postalAddressLine) -> {
            postalAddressLineTransfers.add(postalAddressLineTransferCache.getPostalAddressLineTransfer(userVisit, postalAddressLine));
        });

        return postalAddressLineTransfers;
    }

    public List<PostalAddressLineTransfer> getPostalAddressLineTransfersByPostalAddressFormat(UserVisit userVisit, PostalAddressFormat postalAddressFormat) {
        return getPostalAddressLineTransfers(userVisit, getPostalAddressLinesByPostalAddressFormat(postalAddressFormat));
    }
    
    public void updatePostalAddressLineFromValue(PostalAddressLineDetailValue postalAddressLineDetailValue, BasePK updatedBy) {
        if(postalAddressLineDetailValue.hasBeenModified()) {
            var postalAddressLine = postalAddressLineFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     postalAddressLineDetailValue.getPostalAddressLinePK());
            var postalAddressLineDetail = postalAddressLine.getActiveDetailForUpdate();
            
            postalAddressLineDetail.setThruTime(session.getStartTime());
            postalAddressLineDetail.store();

            var postalAddressLinePK = postalAddressLineDetail.getPostalAddressLinePK();
            var postalAddressFormatPK = postalAddressLineDetail.getPostalAddressFormatPK();
            var postalAddressLineSortOrder = postalAddressLineDetailValue.getPostalAddressLineSortOrder();
            var prefix = postalAddressLineDetailValue.getPrefix();
            var alwaysIncludePrefix = postalAddressLineDetailValue.getAlwaysIncludePrefix();
            var suffix = postalAddressLineDetailValue.getSuffix();
            var alwaysIncludeSuffix = postalAddressLineDetailValue.getAlwaysIncludeSuffix();
            var collapseIfEmpty = postalAddressLineDetailValue.getCollapseIfEmpty();
            
            postalAddressLineDetail = postalAddressLineDetailFactory.create(postalAddressLinePK,
                    postalAddressFormatPK, postalAddressLineSortOrder, prefix, alwaysIncludePrefix, suffix, alwaysIncludeSuffix,
                    collapseIfEmpty, session.getStartTime(), Session.MAX_TIME);
            
            postalAddressLine.setActiveDetail(postalAddressLineDetail);
            postalAddressLine.setLastDetail(postalAddressLineDetail);
            
            sendEvent(postalAddressFormatPK, EventTypes.MODIFY, postalAddressLinePK, EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePostalAddressLine(PostalAddressLine postalAddressLine, BasePK deletedBy) {
        deletePostalAddressLineElementsByPostalAddressLine(postalAddressLine, deletedBy);

        var postalAddressLineDetail = postalAddressLine.getLastDetailForUpdate();
        postalAddressLineDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected PostalAddressLineElementFactory postalAddressLineElementFactory;

    @Inject
    PostalAddressLineElementTransferCache postalAddressLineElementTransferCache;

    public PostalAddressLineElement createPostalAddressLineElement(PostalAddressLine postalAddressLine,
            Integer postalAddressLineElementSortOrder, PostalAddressElementType postalAddressElementType, String prefix,
            Boolean alwaysIncludePrefix, String suffix, Boolean alwaysIncludeSuffix, BasePK createdBy) {

        var postalAddressLineElement = postalAddressLineElementFactory.create(
                postalAddressLine, postalAddressLineElementSortOrder, postalAddressElementType, prefix, alwaysIncludePrefix, suffix,
                alwaysIncludeSuffix, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(postalAddressLine.getLastDetail().getPostalAddressFormatPK(), EventTypes.MODIFY, postalAddressLineElement.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return postalAddressLineElement;
    }

    public long countPostalAddressLineElementsByPostalAddressLine(final PostalAddressLine postalAddressLine) {
        return session.getDslContext()
                .selectCount()
                .from(PostalAddressLineElements)
                .where(PostalAddressLineElements.POSTAL_ADDRESS_LINE.eq(postalAddressLine.getPrimaryKey()),
                        PostalAddressLineElements.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPostalAddressLineElementsByPostalAddressElementType(final PostalAddressElementType postalAddressElementType) {
        return session.getDslContext()
                .selectCount()
                .from(PostalAddressLineElements)
                .where(PostalAddressLineElements.POSTAL_ADDRESS_ELEMENT_TYPE.eq(postalAddressElementType.getPrimaryKey()),
                        PostalAddressLineElements.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PostalAddressLineElement getPostalAddressLineElement(PostalAddressLine postalAddressLine,
            Integer postalAddressLineElementSortOrder, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PostalAddressLineElements.fields())
                .from(PostalAddressLineElements)
                .where(PostalAddressLineElements.POSTAL_ADDRESS_LINE.eq(postalAddressLine.getPrimaryKey()),
                        PostalAddressLineElements.POSTAL_ADDRESS_LINE_ELEMENT_SORT_ORDER.eq(postalAddressLineElementSortOrder),
                        PostalAddressLineElements.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return postalAddressLineElementFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PostalAddressLineElements.fields())
                    .from(PostalAddressLineElements)
                    .where(PostalAddressLineElements.POSTAL_ADDRESS_LINE.eq(postalAddressLine.getPrimaryKey()),
                            PostalAddressLineElements.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PostalAddressLineElements.POSTAL_ADDRESS_LINE_ELEMENT_SORT_ORDER),
                    PostalAddressLineElementFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PostalAddressLineElements.fields())
                    .from(PostalAddressLineElements)
                    .where(PostalAddressLineElements.POSTAL_ADDRESS_LINE.eq(postalAddressLine.getPrimaryKey()),
                            PostalAddressLineElements.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PostalAddressLineElements.POSTAL_ADDRESS_LINE_ELEMENT_SORT_ORDER)
                    .forUpdate();
        };

        return postalAddressLineElementFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PostalAddressLineElement> getPostalAddressLineElementsByPostalAddressLine(PostalAddressLine postalAddressLine) {
        return getPostalAddressLineElementsByPostalAddressLine(postalAddressLine, EntityPermission.READ_ONLY);
    }
    
    public List<PostalAddressLineElement> getPostalAddressLineElementsByPostalAddressLineForUpdate(PostalAddressLine postalAddressLine) {
        return getPostalAddressLineElementsByPostalAddressLine(postalAddressLine, EntityPermission.READ_WRITE);
    }
    
    public PostalAddressLineElementTransfer getPostalAddressLineElementTransfer(UserVisit userVisit, PostalAddressLineElement postalAddressLineElement) {
        return postalAddressLineElementTransferCache.getPostalAddressLineElementTransfer(userVisit, postalAddressLineElement);
    }
    
    public List<PostalAddressLineElementTransfer> getPostalAddressLineElementTransfersByPostalAddressLine(UserVisit userVisit, PostalAddressLine postalAddressLine) {
        var postalAddressLineElements = getPostalAddressLineElementsByPostalAddressLine(postalAddressLine);
        List<PostalAddressLineElementTransfer> postalAddressLineElementTransfers = new ArrayList<>(postalAddressLineElements.size());
        
        postalAddressLineElements.forEach((postalAddressLineElement) ->
                postalAddressLineElementTransfers.add(postalAddressLineElementTransferCache.getPostalAddressLineElementTransfer(userVisit, postalAddressLineElement))
        );
        
        return postalAddressLineElementTransfers;
    }
    
    public void updatePostalAddressLineElementFromValue(PostalAddressLineElementValue postalAddressLineElementValue, BasePK updatedBy) {
        if(postalAddressLineElementValue.hasBeenModified()) {
            var postalAddressLineElement = postalAddressLineElementFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     postalAddressLineElementValue.getPrimaryKey());
            
            postalAddressLineElement.setThruTime(session.getStartTime());
            postalAddressLineElement.store();

            var postalAddressLinePK = postalAddressLineElement.getPostalAddressLinePK();
            var postalAddressLineElementSortOrder = postalAddressLineElementValue.getPostalAddressLineElementSortOrder();
            var postalAddressElementTypePK = postalAddressLineElementValue.getPostalAddressElementTypePK();
            var prefix = postalAddressLineElementValue.getPrefix();
            var alwaysIncludePrefix = postalAddressLineElementValue.getAlwaysIncludePrefix();
            var suffix = postalAddressLineElementValue.getSuffix();
            var alwaysIncludeSuffix = postalAddressLineElementValue.getAlwaysIncludeSuffix();
            
            postalAddressLineElement = postalAddressLineElementFactory.create(postalAddressLinePK,
                    postalAddressLineElementSortOrder, postalAddressElementTypePK, prefix, alwaysIncludePrefix, suffix, alwaysIncludeSuffix,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(postalAddressLineElement.getPostalAddressLine().getLastDetail().getPostalAddressFormatPK(), EventTypes.MODIFY, postalAddressLineElement.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePostalAddressLineElement(PostalAddressLineElement postalAddressLineElement, BasePK deletedBy) {
        postalAddressLineElement.setThruTime(session.getStartTime());
        
        sendEvent(postalAddressLineElement.getPostalAddressLine().getLastDetail().getPostalAddressFormatPK(), EventTypes.MODIFY, postalAddressLineElement.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deletePostalAddressLineElementsByPostalAddressLine(PostalAddressLine postalAddressLine, BasePK deletedBy) {
        var postalAddressLineElements = getPostalAddressLineElementsByPostalAddressLineForUpdate(postalAddressLine);
        
        postalAddressLineElements.forEach((postalAddressLineElement) -> 
                deletePostalAddressLineElement(postalAddressLineElement, deletedBy)
        );
    }
    
}

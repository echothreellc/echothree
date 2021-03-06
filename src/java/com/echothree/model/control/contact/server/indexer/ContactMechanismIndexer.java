// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.model.control.contact.server.indexer;

import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.index.common.IndexConstants;
import com.echothree.model.control.index.server.analysis.ContactMechanismAnalyzer;
import com.echothree.model.control.index.server.indexer.BaseIndexer;
import com.echothree.model.control.index.server.indexer.FieldTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.contact.server.entity.ContactEmailAddress;
import com.echothree.model.data.contact.server.entity.ContactInet4Address;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.contact.server.entity.ContactMechanismDetail;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.model.data.contact.server.entity.ContactPostalAddress;
import com.echothree.model.data.contact.server.entity.ContactTelephone;
import com.echothree.model.data.contact.server.entity.ContactWebAddress;
import com.echothree.model.data.contact.server.entity.PartyContactMechanismDetail;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.NameSuffix;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.entity.PersonalTitle;
import com.echothree.util.common.string.Inet4AddressUtils;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.HashSet;
import java.util.Set;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.util.BytesRef;

public class ContactMechanismIndexer
        extends BaseIndexer<ContactMechanism> {
    
    ContactControl contactControl = Session.getModelController(ContactControl.class);
    GeoControl geoControl = Session.getModelController(GeoControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);

    /** Creates a new instance of ContactMechanismIndexer */
    public ContactMechanismIndexer(final ExecutionErrorAccumulator eea, final Index index) {
        super(eea, index);
    }

    @Override
    protected Analyzer getAnalyzer() {
        return new ContactMechanismAnalyzer(eea, language, entityType, entityAttributes, tagScopes);
    }
    
    @Override
    protected ContactMechanism getEntity(final EntityInstance entityInstance) {
        return contactControl.getContactMechanismByEntityInstance(entityInstance);
    }
    
    private void addPartyNamesToDocument(Document document, Set<Party> parties) {
        StringBuilder partyNamesBuilder = new StringBuilder();

        parties.forEach((party) -> {
            if(partyNamesBuilder.length() != 0) {
                partyNamesBuilder.append(' ');
            }

            partyNamesBuilder.append(party.getLastDetail().getPartyName());
        });

        document.add(new Field(IndexConstants.IndexField_PartyNames, partyNamesBuilder.toString(), FieldTypes.NOT_STORED_TOKENIZED));
    }
    
    private void addPartyTypeNamesToDocument(Document document, Set<PartyType> partyTypes) {
        StringBuilder partyTypeNamesBuilder = new StringBuilder();

        partyTypes.forEach((partyType) -> {
            if(partyTypeNamesBuilder.length() != 0) {
                partyTypeNamesBuilder.append(' ');
            }

            partyTypeNamesBuilder.append(partyType.getPartyTypeName());
        });

        document.add(new Field(IndexConstants.IndexField_PartyTypeNames, partyTypeNamesBuilder.toString(), FieldTypes.NOT_STORED_TOKENIZED));
    }
    
    private void addContactMechanismPurposeNamesToDocument(Document document, Set<ContactMechanismPurpose> contactMechanismPurposes) {
        StringBuilder contactMechanismPurposesNamesBuilder = new StringBuilder();

        contactMechanismPurposes.forEach((contactMechanismPurpose) -> {
            if(contactMechanismPurposesNamesBuilder.length() != 0) {
                contactMechanismPurposesNamesBuilder.append(' ');
            }

            contactMechanismPurposesNamesBuilder.append(contactMechanismPurpose.getContactMechanismPurposeName());
        });

        document.add(new Field(IndexConstants.IndexField_ContactMechanismPurposeNames, contactMechanismPurposesNamesBuilder.toString(), FieldTypes.NOT_STORED_TOKENIZED));
    }
    
    private void addPartiesToDocument(final Document document, final ContactMechanism contactMechanism) {
        Set<Party> parties = new HashSet<>();
        Set<PartyType> partyTypes = new HashSet<>();
        Set<ContactMechanismPurpose> contactMechanismPurposes = new HashSet<>();
        
        contactControl.getPartyContactMechanismsByContactMechanism(contactMechanism).stream().forEach((partyContactMechanism) -> {
            PartyContactMechanismDetail partyContactMechanismDetail = partyContactMechanism.getLastDetail();
            Party party = partyContactMechanismDetail.getParty();
            parties.add(party);
            partyTypes.add(party.getLastDetail().getPartyType());
            contactControl.getPartyContactMechanismPurposesByPartyContactMechanism(partyContactMechanism).stream().forEach((partyContactMechanismPurpose) -> {
                contactMechanismPurposes.add(partyContactMechanismPurpose.getLastDetail().getContactMechanismPurpose());
            });
        });
        
        addPartyNamesToDocument(document, parties);
        addPartyTypeNamesToDocument(document, partyTypes);
        addContactMechanismPurposeNamesToDocument(document, contactMechanismPurposes);
    }
    
    private void addGeoCodeToDocument(final Document document, final Language language, final GeoCode geoCode, String description, final String geoCodeIndexField, final String descriptionIndexField) {
        if(geoCode != null) {
            description = geoControl.getBestGeoCodeDescription(geoCode, language);

            document.add(new Field(geoCodeIndexField, geoCode.getLastDetail().getGeoCodeName(), FieldTypes.NOT_STORED_TOKENIZED));
        }

        if(description != null) {
            document.add(new Field(descriptionIndexField, description, FieldTypes.NOT_STORED_TOKENIZED));
        }
    }
    
    private void addContactEmailAddressToDocument(final Document document, final ContactMechanism contactMechanism) {
        ContactEmailAddress contactEmailAddress = contactControl.getContactEmailAddress(contactMechanism);
        
        document.add(new Field(IndexConstants.IndexField_EmailAddress, contactEmailAddress.getEmailAddress(), FieldTypes.NOT_STORED_TOKENIZED));
    }

    private void addContactInet4AddressToDocument(final Document document, final ContactMechanism contactMechanism) {
        ContactInet4Address contactInet4Address = contactControl.getContactInet4Address(contactMechanism);
        
        document.add(new Field(IndexConstants.IndexField_Inet4Address, Inet4AddressUtils.getInstance().formatInet4Address(contactInet4Address.getInet4Address()), FieldTypes.NOT_STORED_TOKENIZED));
    }

    private void addContactPostalAddressToDocument(final Document document, final ContactMechanism contactMechanism, final Language language) {
        ContactPostalAddress contactPostalAddress = contactControl.getContactPostalAddress(contactMechanism);
        PersonalTitle personalTitle = contactPostalAddress.getPersonalTitle();
        String firstName = contactPostalAddress.getFirstName();
        String middleName = contactPostalAddress.getMiddleName();
        String lastName = contactPostalAddress.getLastName();
        NameSuffix nameSuffix = contactPostalAddress.getNameSuffix();
        String companyName = contactPostalAddress.getCompanyName();
        String attention = contactPostalAddress.getAttention();
        String address2 = contactPostalAddress.getAddress2();
        String address3 = contactPostalAddress.getAddress3();
        
        if(personalTitle != null) {
            document.add(new Field(IndexConstants.IndexField_PersonalTitle, personalTitle.getLastDetail().getDescription(), FieldTypes.NOT_STORED_TOKENIZED));
        }
        
        if(firstName != null) {
            document.add(new Field(IndexConstants.IndexField_FirstName, firstName, FieldTypes.NOT_STORED_TOKENIZED));
        }
        
        if(middleName != null) {
            document.add(new Field(IndexConstants.IndexField_MiddleName, middleName, FieldTypes.NOT_STORED_TOKENIZED));
        }
        
        if(lastName != null) {
            document.add(new Field(IndexConstants.IndexField_LastName, lastName, FieldTypes.NOT_STORED_TOKENIZED));
        }
        
        if(nameSuffix != null) {
            document.add(new Field(IndexConstants.IndexField_NameSuffix, nameSuffix.getLastDetail().getDescription(), FieldTypes.NOT_STORED_TOKENIZED));
        }
        
        if(companyName != null) {
            document.add(new Field(IndexConstants.IndexField_CompanyName, companyName, FieldTypes.NOT_STORED_TOKENIZED));
        }
        
        if(attention != null) {
            document.add(new Field(IndexConstants.IndexField_Attention, attention, FieldTypes.NOT_STORED_TOKENIZED));
        }
        
        document.add(new Field(IndexConstants.IndexField_Address1, contactPostalAddress.getAddress1(), FieldTypes.NOT_STORED_TOKENIZED));
        
        if(address2 != null) {
            document.add(new Field(IndexConstants.IndexField_Address2, address2, FieldTypes.NOT_STORED_TOKENIZED));
        }
        
        if(address3 != null) {
            document.add(new Field(IndexConstants.IndexField_Address3, address3, FieldTypes.NOT_STORED_TOKENIZED));
        }
        
        addGeoCodeToDocument(document, language, contactPostalAddress.getCityGeoCode(), contactPostalAddress.getCity(), IndexConstants.IndexField_CityGeoCodeName, IndexConstants.IndexField_City);
        addGeoCodeToDocument(document, language, contactPostalAddress.getCountyGeoCode(), null, IndexConstants.IndexField_CountyGeoCodeName, IndexConstants.IndexField_County);
        addGeoCodeToDocument(document, language, contactPostalAddress.getStateGeoCode(), contactPostalAddress.getState(), IndexConstants.IndexField_StateGeoCodeName, IndexConstants.IndexField_State);
        addGeoCodeToDocument(document, language, contactPostalAddress.getPostalCodeGeoCode(), contactPostalAddress.getPostalCode(), IndexConstants.IndexField_PostalCodeGeoCodeName, IndexConstants.IndexField_PostalCode);
        addGeoCodeToDocument(document, language, contactPostalAddress.getCountryGeoCode(), null, IndexConstants.IndexField_CountryGeoCodeName, IndexConstants.IndexField_Country);
        
        document.add(new Field(IndexConstants.IndexField_IsCommercial, contactPostalAddress.getIsCommercial().toString(), FieldTypes.NOT_STORED_TOKENIZED));
    }

    private void addContactTelephoneToDocument(final Document document, final ContactMechanism contactMechanism) {
        ContactTelephone contactTelephone = contactControl.getContactTelephone(contactMechanism);
        String areaCode = contactTelephone.getAreaCode();
        String telephoneExtension = contactTelephone.getTelephoneExtension();
        
        document.add(new Field(IndexConstants.IndexField_CountryGeoCodeName, contactTelephone.getCountryGeoCode().getLastDetail().getGeoCodeName(), FieldTypes.NOT_STORED_TOKENIZED));
        
        if(areaCode != null) {
            document.add(new Field(IndexConstants.IndexField_AreaCode, areaCode, FieldTypes.NOT_STORED_TOKENIZED));
        }
        
        document.add(new Field(IndexConstants.IndexField_TelephoneNumber, contactTelephone.getTelephoneNumber(), FieldTypes.NOT_STORED_TOKENIZED));
        
        if(telephoneExtension != null) {
            document.add(new Field(IndexConstants.IndexField_TelephoneExtension, telephoneExtension, FieldTypes.NOT_STORED_TOKENIZED));
        }
    }

    private void addContactWebAddressToDocument(final Document document, final ContactMechanism contactMechanism) {
        ContactWebAddress contactWebAddress = contactControl.getContactWebAddress(contactMechanism);
        
        document.add(new Field(IndexConstants.IndexField_Url, contactWebAddress.getUrl(), FieldTypes.NOT_STORED_TOKENIZED));
    }
    
    private void addContactMechanismToDocument(final Document document, final ContactMechanism contactMechanism, final Language language) {
        ContactMechanismDetail contactMechanismDetail = contactMechanism.getLastDetail();
        String contactMechanismTypeName = contactMechanismDetail.getContactMechanismType().getContactMechanismTypeName();
        
        document.add(new Field(IndexConstants.IndexField_ContactMechanismName, contactMechanismDetail.getContactMechanismName(), FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new SortedDocValuesField(IndexConstants.IndexField_ContactMechanismName + IndexConstants.IndexFieldVariationSeparator + IndexConstants.IndexFieldVariation_Sortable,
                new BytesRef(contactMechanismDetail.getContactMechanismName())));

        document.add(new Field(IndexConstants.IndexField_ContactMechanismTypeName, contactMechanismTypeName, FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new Field(IndexConstants.IndexField_AllowSolicitation, contactMechanismDetail.getAllowSolicitation().toString(), FieldTypes.NOT_STORED_TOKENIZED));
        
        if(contactMechanismTypeName.equals(ContactMechanismTypes.EMAIL_ADDRESS.name())) {
            addContactEmailAddressToDocument(document, contactMechanism);
        } else if(contactMechanismTypeName.equals(ContactMechanismTypes.INET_4.name())) {
            addContactInet4AddressToDocument(document, contactMechanism);
        } else if(contactMechanismTypeName.equals(ContactMechanismTypes.INET_6.name())) {
            // TODO
        } else if(contactMechanismTypeName.equals(ContactMechanismTypes.POSTAL_ADDRESS.name())) {
            addContactPostalAddressToDocument(document, contactMechanism, language);
        } else if(contactMechanismTypeName.equals(ContactMechanismTypes.TELECOM_ADDRESS.name())) {
            addContactTelephoneToDocument(document, contactMechanism);
        } else if(contactMechanismTypeName.equals(ContactMechanismTypes.WEB_ADDRESS.name())) {
            addContactWebAddressToDocument(document, contactMechanism);
        }
    }
    
    @Override
    protected Document convertToDocument(final EntityInstance entityInstance, final ContactMechanism contactMechanism) {
        Document document = new Document();

        document.add(new Field(IndexConstants.IndexField_EntityRef, contactMechanism.getPrimaryKey().getEntityRef(), FieldTypes.STORED_NOT_TOKENIZED));
        document.add(new Field(IndexConstants.IndexField_EntityInstanceId, entityInstance.getPrimaryKey().getEntityId().toString(), FieldTypes.STORED_NOT_TOKENIZED));
        
        addPartiesToDocument(document, contactMechanism);
        addContactMechanismToDocument(document, contactMechanism, language);
        
        indexWorkflowEntityStatuses(document, entityInstance);
        indexEntityTimes(document, entityInstance);
        indexEntityAttributes(document, entityInstance);
        indexEntityTags(document, entityInstance);

        return document;
    }

}

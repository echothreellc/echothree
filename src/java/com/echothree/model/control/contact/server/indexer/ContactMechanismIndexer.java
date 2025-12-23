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

package com.echothree.model.control.contact.server.indexer;

import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.index.common.IndexConstants;
import com.echothree.model.control.index.common.IndexFieldVariations;
import com.echothree.model.control.index.common.IndexFields;
import com.echothree.model.control.contact.server.analyzer.ContactMechanismAnalyzer;
import com.echothree.model.control.index.server.indexer.BaseIndexer;
import com.echothree.model.control.index.server.indexer.FieldTypes;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyType;
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

    /** Creates a new instance of ContactMechanismIndexer */
    public ContactMechanismIndexer(final ExecutionErrorAccumulator eea, final Index index) {
        super(eea, index);
    }

    @Override
    protected Analyzer getAnalyzer() {
        return new ContactMechanismAnalyzer(eea, language, entityType, entityAliasTypes, entityAttributes, tagScopes);
    }
    
    @Override
    protected ContactMechanism getEntity(final EntityInstance entityInstance) {
        return contactControl.getContactMechanismByEntityInstance(entityInstance);
    }
    
    private void addPartyNamesToDocument(Document document, Set<Party> parties) {
        var partyNamesBuilder = new StringBuilder();

        parties.forEach((party) -> {
            if(partyNamesBuilder.length() != 0) {
                partyNamesBuilder.append(' ');
            }

            partyNamesBuilder.append(party.getLastDetail().getPartyName());
        });

        document.add(new Field(IndexFields.partyNames.name(), partyNamesBuilder.toString(), FieldTypes.NOT_STORED_TOKENIZED));
    }
    
    private void addPartyTypeNamesToDocument(Document document, Set<PartyType> partyTypes) {
        var partyTypeNamesBuilder = new StringBuilder();

        partyTypes.forEach((partyType) -> {
            if(partyTypeNamesBuilder.length() != 0) {
                partyTypeNamesBuilder.append(' ');
            }

            partyTypeNamesBuilder.append(partyType.getPartyTypeName());
        });

        document.add(new Field(IndexFields.partyTypeNames.name(), partyTypeNamesBuilder.toString(), FieldTypes.NOT_STORED_TOKENIZED));
    }
    
    private void addContactMechanismPurposeNamesToDocument(Document document, Set<ContactMechanismPurpose> contactMechanismPurposes) {
        var contactMechanismPurposesNamesBuilder = new StringBuilder();

        contactMechanismPurposes.forEach((contactMechanismPurpose) -> {
            if(contactMechanismPurposesNamesBuilder.length() != 0) {
                contactMechanismPurposesNamesBuilder.append(' ');
            }

            contactMechanismPurposesNamesBuilder.append(contactMechanismPurpose.getContactMechanismPurposeName());
        });

        document.add(new Field(IndexFields.contactMechanismPurposeNames.name(), contactMechanismPurposesNamesBuilder.toString(), FieldTypes.NOT_STORED_TOKENIZED));
    }
    
    private void addPartiesToDocument(final Document document, final ContactMechanism contactMechanism) {
        Set<Party> parties = new HashSet<>();
        Set<PartyType> partyTypes = new HashSet<>();
        Set<ContactMechanismPurpose> contactMechanismPurposes = new HashSet<>();
        
        contactControl.getPartyContactMechanismsByContactMechanism(contactMechanism).forEach((partyContactMechanism) -> {
            var partyContactMechanismDetail = partyContactMechanism.getLastDetail();
            var party = partyContactMechanismDetail.getParty();
            parties.add(party);
            partyTypes.add(party.getLastDetail().getPartyType());
            contactControl.getPartyContactMechanismPurposesByPartyContactMechanism(partyContactMechanism).forEach((partyContactMechanismPurpose) -> {
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
        var contactEmailAddress = contactControl.getContactEmailAddress(contactMechanism);

        if(contactEmailAddress != null) {
            document.add(new Field(IndexFields.emailAddress.name(), contactEmailAddress.getEmailAddress(), FieldTypes.NOT_STORED_TOKENIZED));
        }
    }

    private void addContactInet4AddressToDocument(final Document document, final ContactMechanism contactMechanism) {
        var contactInet4Address = contactControl.getContactInet4Address(contactMechanism);

        if(contactInet4Address != null) {
            document.add(new Field(IndexFields.inet4Address.name(), Inet4AddressUtils.getInstance().formatInet4Address(contactInet4Address.getInet4Address()), FieldTypes.NOT_STORED_TOKENIZED));
        }
    }

    private void addContactPostalAddressToDocument(final Document document, final ContactMechanism contactMechanism, final Language language) {
        var contactPostalAddress = contactControl.getContactPostalAddress(contactMechanism);

        if(contactPostalAddress != null) {
            var personalTitle = contactPostalAddress.getPersonalTitle();
            var firstName = contactPostalAddress.getFirstName();
            var middleName = contactPostalAddress.getMiddleName();
            var lastName = contactPostalAddress.getLastName();
            var nameSuffix = contactPostalAddress.getNameSuffix();
            var companyName = contactPostalAddress.getCompanyName();
            var attention = contactPostalAddress.getAttention();
            var address2 = contactPostalAddress.getAddress2();
            var address3 = contactPostalAddress.getAddress3();

            if(personalTitle != null) {
                document.add(new Field(IndexFields.personalTitle.name(), personalTitle.getLastDetail().getDescription(), FieldTypes.NOT_STORED_TOKENIZED));
            }

            if(firstName != null) {
                document.add(new Field(IndexFields.firstName.name(), firstName, FieldTypes.NOT_STORED_TOKENIZED));
            }

            if(middleName != null) {
                document.add(new Field(IndexFields.middleName.name(), middleName, FieldTypes.NOT_STORED_TOKENIZED));
            }

            if(lastName != null) {
                document.add(new Field(IndexFields.lastName.name(), lastName, FieldTypes.NOT_STORED_TOKENIZED));
            }

            if(nameSuffix != null) {
                document.add(new Field(IndexFields.nameSuffix.name(), nameSuffix.getLastDetail().getDescription(), FieldTypes.NOT_STORED_TOKENIZED));
            }

            if(companyName != null) {
                document.add(new Field(IndexFields.companyName.name(), companyName, FieldTypes.NOT_STORED_TOKENIZED));
            }

            if(attention != null) {
                document.add(new Field(IndexFields.attention.name(), attention, FieldTypes.NOT_STORED_TOKENIZED));
            }

            document.add(new Field(IndexFields.address1.name(), contactPostalAddress.getAddress1(), FieldTypes.NOT_STORED_TOKENIZED));

            if(address2 != null) {
                document.add(new Field(IndexFields.address2.name(), address2, FieldTypes.NOT_STORED_TOKENIZED));
            }

            if(address3 != null) {
                document.add(new Field(IndexFields.address3.name(), address3, FieldTypes.NOT_STORED_TOKENIZED));
            }

            addGeoCodeToDocument(document, language, contactPostalAddress.getCityGeoCode(), contactPostalAddress.getCity(), IndexFields.cityGeoCodeName.name(), IndexFields.city.name());
            addGeoCodeToDocument(document, language, contactPostalAddress.getCountyGeoCode(), null, IndexFields.countyGeoCodeName.name(), IndexFields.county.name());
            addGeoCodeToDocument(document, language, contactPostalAddress.getStateGeoCode(), contactPostalAddress.getState(), IndexFields.stateGeoCodeName.name(), IndexFields.state.name());
            addGeoCodeToDocument(document, language, contactPostalAddress.getPostalCodeGeoCode(), contactPostalAddress.getPostalCode(), IndexFields.postalCodeGeoCodeName.name(), IndexFields.postalCode.name());
            addGeoCodeToDocument(document, language, contactPostalAddress.getCountryGeoCode(), null, IndexFields.countryGeoCodeName.name(), IndexFields.country.name());

            document.add(new Field(IndexFields.isCommercial.name(), contactPostalAddress.getIsCommercial().toString(), FieldTypes.NOT_STORED_TOKENIZED));
        }
    }

    private void addContactTelephoneToDocument(final Document document, final ContactMechanism contactMechanism) {
        var contactTelephone = contactControl.getContactTelephone(contactMechanism);

        if(contactTelephone != null) {
            var areaCode = contactTelephone.getAreaCode();
            var telephoneExtension = contactTelephone.getTelephoneExtension();

            document.add(new Field(IndexFields.countryGeoCodeName.name(), contactTelephone.getCountryGeoCode().getLastDetail().getGeoCodeName(), FieldTypes.NOT_STORED_TOKENIZED));

            if(areaCode != null) {
                document.add(new Field(IndexFields.areaCode.name(), areaCode, FieldTypes.NOT_STORED_TOKENIZED));
            }

            document.add(new Field(IndexFields.telephoneNumber.name(), contactTelephone.getTelephoneNumber(), FieldTypes.NOT_STORED_TOKENIZED));

            if(telephoneExtension != null) {
                document.add(new Field(IndexFields.telephoneExtension.name(), telephoneExtension, FieldTypes.NOT_STORED_TOKENIZED));
            }
        }
    }

    private void addContactWebAddressToDocument(final Document document, final ContactMechanism contactMechanism) {
        var contactWebAddress = contactControl.getContactWebAddress(contactMechanism);

        if(contactWebAddress != null) {
            document.add(new Field(IndexFields.url.name(), contactWebAddress.getUrl(), FieldTypes.NOT_STORED_TOKENIZED));
        }
    }
    
    private void addContactMechanismToDocument(final Document document, final ContactMechanism contactMechanism, final Language language) {
        var contactMechanismDetail = contactMechanism.getLastDetail();
        var contactMechanismTypeName = contactMechanismDetail.getContactMechanismType().getContactMechanismTypeName();
        
        document.add(new Field(IndexFields.contactMechanismName.name(), contactMechanismDetail.getContactMechanismName(), FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new SortedDocValuesField(IndexFields.contactMechanismName.name() + IndexConstants.INDEX_FIELD_VARIATION_SEPARATOR + IndexFieldVariations.sortable.name(),
                new BytesRef(contactMechanismDetail.getContactMechanismName())));

        document.add(new Field(IndexFields.contactMechanismTypeName.name(), contactMechanismTypeName, FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new Field(IndexFields.allowSolicitation.name(), contactMechanismDetail.getAllowSolicitation().toString(), FieldTypes.NOT_STORED_TOKENIZED));
        
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
        var document = newDocumentWithEntityInstanceFields(entityInstance, contactMechanism.getPrimaryKey());

        addPartiesToDocument(document, contactMechanism);
        addContactMechanismToDocument(document, contactMechanism, language);

        return document;
    }

}

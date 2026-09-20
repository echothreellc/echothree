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

package com.echothree.model.control.contactlist.server.control;

import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.contactlist.common.choice.ContactListChoicesBean;
import com.echothree.model.control.contactlist.common.choice.ContactListContactMechanismPurposeChoicesBean;
import com.echothree.model.control.contactlist.common.choice.ContactListFrequencyChoicesBean;
import com.echothree.model.control.contactlist.common.choice.ContactListGroupChoicesBean;
import com.echothree.model.control.contactlist.common.choice.ContactListTypeChoicesBean;
import com.echothree.model.control.contactlist.common.choice.PartyContactListStatusChoicesBean;
import com.echothree.model.control.contactlist.common.transfer.ContactListContactMechanismPurposeTransfer;
import com.echothree.model.control.contactlist.common.transfer.ContactListDescriptionTransfer;
import com.echothree.model.control.contactlist.common.transfer.ContactListFrequencyDescriptionTransfer;
import com.echothree.model.control.contactlist.common.transfer.ContactListFrequencyTransfer;
import com.echothree.model.control.contactlist.common.transfer.ContactListGroupDescriptionTransfer;
import com.echothree.model.control.contactlist.common.transfer.ContactListGroupTransfer;
import com.echothree.model.control.contactlist.common.transfer.ContactListTransfer;
import com.echothree.model.control.contactlist.common.transfer.ContactListTypeDescriptionTransfer;
import com.echothree.model.control.contactlist.common.transfer.ContactListTypeTransfer;
import com.echothree.model.control.contactlist.common.transfer.CustomerTypeContactListGroupTransfer;
import com.echothree.model.control.contactlist.common.transfer.CustomerTypeContactListTransfer;
import com.echothree.model.control.contactlist.common.transfer.PartyContactListTransfer;
import com.echothree.model.control.contactlist.common.transfer.PartyTypeContactListGroupTransfer;
import com.echothree.model.control.contactlist.common.transfer.PartyTypeContactListTransfer;
import com.echothree.model.control.contactlist.common.workflow.PartyContactListStatusConstants;
import com.echothree.model.control.contactlist.server.transfer.ContactListContactMechanismPurposeTransferCache;
import com.echothree.model.control.contactlist.server.transfer.ContactListDescriptionTransferCache;
import com.echothree.model.control.contactlist.server.transfer.ContactListFrequencyDescriptionTransferCache;
import com.echothree.model.control.contactlist.server.transfer.ContactListFrequencyTransferCache;
import com.echothree.model.control.contactlist.server.transfer.ContactListGroupDescriptionTransferCache;
import com.echothree.model.control.contactlist.server.transfer.ContactListGroupTransferCache;
import com.echothree.model.control.contactlist.server.transfer.ContactListTransferCache;
import com.echothree.model.control.contactlist.server.transfer.ContactListTypeDescriptionTransferCache;
import com.echothree.model.control.contactlist.server.transfer.ContactListTypeTransferCache;
import com.echothree.model.control.contactlist.server.transfer.CustomerTypeContactListGroupTransferCache;
import com.echothree.model.control.contactlist.server.transfer.CustomerTypeContactListTransferCache;
import com.echothree.model.control.contactlist.server.transfer.PartyContactListTransferCache;
import com.echothree.model.control.contactlist.server.transfer.PartyTypeContactListGroupTransferCache;
import com.echothree.model.control.contactlist.server.transfer.PartyTypeContactListTransferCache;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.data.chain.server.entity.Chain;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.model.data.contactlist.common.pk.ContactListContactMechanismPurposePK;
import com.echothree.model.data.contactlist.common.pk.ContactListFrequencyPK;
import com.echothree.model.data.contactlist.common.pk.ContactListGroupPK;
import com.echothree.model.data.contactlist.common.pk.ContactListPK;
import com.echothree.model.data.contactlist.common.pk.ContactListTypePK;
import com.echothree.model.data.contactlist.common.pk.PartyContactListPK;
import com.echothree.model.data.contactlist.server.entity.ContactList;
import com.echothree.model.data.contactlist.server.entity.ContactListContactMechanismPurpose;
import com.echothree.model.data.contactlist.server.entity.ContactListDescription;
import com.echothree.model.data.contactlist.server.entity.ContactListFrequency;
import com.echothree.model.data.contactlist.server.entity.ContactListFrequencyDescription;
import com.echothree.model.data.contactlist.server.entity.ContactListGroup;
import com.echothree.model.data.contactlist.server.entity.ContactListGroupDescription;
import com.echothree.model.data.contactlist.server.entity.ContactListType;
import com.echothree.model.data.contactlist.server.entity.ContactListTypeDescription;
import com.echothree.model.data.contactlist.server.entity.CustomerTypeContactList;
import com.echothree.model.data.contactlist.server.entity.CustomerTypeContactListGroup;
import com.echothree.model.data.contactlist.server.entity.PartyContactList;
import com.echothree.model.data.contactlist.server.entity.PartyTypeContactList;
import com.echothree.model.data.contactlist.server.entity.PartyTypeContactListGroup;
import com.echothree.model.data.contactlist.server.factory.ContactListContactMechanismPurposeDetailFactory;
import com.echothree.model.data.contactlist.server.factory.ContactListContactMechanismPurposeFactory;
import com.echothree.model.data.contactlist.server.factory.ContactListDescriptionFactory;
import com.echothree.model.data.contactlist.server.factory.ContactListDetailFactory;
import com.echothree.model.data.contactlist.server.factory.ContactListFactory;
import com.echothree.model.data.contactlist.server.factory.ContactListFrequencyDescriptionFactory;
import com.echothree.model.data.contactlist.server.factory.ContactListFrequencyDetailFactory;
import com.echothree.model.data.contactlist.server.factory.ContactListFrequencyFactory;
import com.echothree.model.data.contactlist.server.factory.ContactListGroupDescriptionFactory;
import com.echothree.model.data.contactlist.server.factory.ContactListGroupDetailFactory;
import com.echothree.model.data.contactlist.server.factory.ContactListGroupFactory;
import com.echothree.model.data.contactlist.server.factory.ContactListTypeDescriptionFactory;
import com.echothree.model.data.contactlist.server.factory.ContactListTypeDetailFactory;
import com.echothree.model.data.contactlist.server.factory.ContactListTypeFactory;
import com.echothree.model.data.contactlist.server.factory.CustomerTypeContactListFactory;
import com.echothree.model.data.contactlist.server.factory.CustomerTypeContactListGroupFactory;
import com.echothree.model.data.contactlist.server.factory.PartyContactListDetailFactory;
import com.echothree.model.data.contactlist.server.factory.PartyContactListFactory;
import com.echothree.model.data.contactlist.server.factory.PartyTypeContactListFactory;
import com.echothree.model.data.contactlist.server.factory.PartyTypeContactListGroupFactory;
import com.echothree.model.data.contactlist.server.value.ContactListContactMechanismPurposeDetailValue;
import com.echothree.model.data.contactlist.server.value.ContactListDescriptionValue;
import com.echothree.model.data.contactlist.server.value.ContactListDetailValue;
import com.echothree.model.data.contactlist.server.value.ContactListFrequencyDescriptionValue;
import com.echothree.model.data.contactlist.server.value.ContactListFrequencyDetailValue;
import com.echothree.model.data.contactlist.server.value.ContactListGroupDescriptionValue;
import com.echothree.model.data.contactlist.server.value.ContactListGroupDetailValue;
import com.echothree.model.data.contactlist.server.value.ContactListTypeDescriptionValue;
import com.echothree.model.data.contactlist.server.value.ContactListTypeDetailValue;
import com.echothree.model.data.contactlist.server.value.CustomerTypeContactListGroupValue;
import com.echothree.model.data.contactlist.server.value.CustomerTypeContactListValue;
import com.echothree.model.data.contactlist.server.value.PartyContactListDetailValue;
import com.echothree.model.data.contactlist.server.value.PartyTypeContactListGroupValue;
import com.echothree.model.data.contactlist.server.value.PartyTypeContactListValue;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import static com.echothree.model.jooq.server.keys.contactlist.ContactListForeignKeys.CONTACT_LISTS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.contactlist.ContactListForeignKeys.CONTACT_LISTS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.contactlist.ContactListForeignKeys.CONTACT_LIST_CONTACT_MECHANISM_PURPOSES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.contactlist.ContactListForeignKeys.CONTACT_LIST_CONTACT_MECHANISM_PURPOSE_DETAILS_CONTACT_LIST_FK;
import static com.echothree.model.jooq.server.keys.contactlist.ContactListForeignKeys.CONTACT_LIST_CONTACT_MECHANISM_PURPOSE_DETAILS_CONTACT_MECHANISM_PURPOSE_FK;
import static com.echothree.model.jooq.server.keys.contactlist.ContactListForeignKeys.CONTACT_LIST_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.contactlist.ContactListForeignKeys.CONTACT_LIST_FREQUENCIES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.contactlist.ContactListForeignKeys.CONTACT_LIST_FREQUENCY_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.contactlist.ContactListForeignKeys.CONTACT_LIST_GROUPS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.contactlist.ContactListForeignKeys.CONTACT_LIST_GROUPS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.contactlist.ContactListForeignKeys.CONTACT_LIST_GROUP_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.contactlist.ContactListForeignKeys.CONTACT_LIST_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.contactlist.ContactListForeignKeys.CONTACT_LIST_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.contactlist.ContactListForeignKeys.CUSTOMER_TYPE_CONTACT_LISTS_CONTACT_LIST_FK;
import static com.echothree.model.jooq.server.keys.contactlist.ContactListForeignKeys.CUSTOMER_TYPE_CONTACT_LISTS_CUSTOMER_TYPE_FK;
import static com.echothree.model.jooq.server.keys.contactlist.ContactListForeignKeys.CUSTOMER_TYPE_CONTACT_LIST_GROUPS_CONTACT_LIST_GROUP_FK;
import static com.echothree.model.jooq.server.keys.contactlist.ContactListForeignKeys.CUSTOMER_TYPE_CONTACT_LIST_GROUPS_CUSTOMER_TYPE_FK;
import static com.echothree.model.jooq.server.keys.contactlist.ContactListForeignKeys.PARTY_CONTACT_LISTS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.contactlist.ContactListForeignKeys.PARTY_CONTACT_LIST_DETAILS_CONTACT_LIST_FK;
import static com.echothree.model.jooq.server.keys.contactlist.ContactListForeignKeys.PARTY_CONTACT_LIST_DETAILS_PARTY_FK;
import static com.echothree.model.jooq.server.keys.contactlist.ContactListForeignKeys.PARTY_TYPE_CONTACT_LISTS_CONTACT_LIST_FK;
import static com.echothree.model.jooq.server.keys.contactlist.ContactListForeignKeys.PARTY_TYPE_CONTACT_LISTS_PARTY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.contactlist.ContactListForeignKeys.PARTY_TYPE_CONTACT_LIST_GROUPS_CONTACT_LIST_GROUP_FK;
import static com.echothree.model.jooq.server.keys.contactlist.ContactListForeignKeys.PARTY_TYPE_CONTACT_LIST_GROUPS_PARTY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.customer.CustomerForeignKeys.CUSTOMER_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTIES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTY_DETAILS_PARTY_TYPE_FK;
import static com.echothree.model.jooq.server.tables.contact.ContactMechanismPurposes.ContactMechanismPurposes;
import static com.echothree.model.jooq.server.tables.contactlist.ContactListContactMechanismPurposeDetails.ContactListContactMechanismPurposeDetails;
import static com.echothree.model.jooq.server.tables.contactlist.ContactListContactMechanismPurposes.ContactListContactMechanismPurposes;
import static com.echothree.model.jooq.server.tables.contactlist.ContactListDescriptions.ContactListDescriptions;
import static com.echothree.model.jooq.server.tables.contactlist.ContactListDetails.ContactListDetails;
import static com.echothree.model.jooq.server.tables.contactlist.ContactListFrequencies.ContactListFrequencies;
import static com.echothree.model.jooq.server.tables.contactlist.ContactListFrequencyDescriptions.ContactListFrequencyDescriptions;
import static com.echothree.model.jooq.server.tables.contactlist.ContactListFrequencyDetails.ContactListFrequencyDetails;
import static com.echothree.model.jooq.server.tables.contactlist.ContactListGroupDescriptions.ContactListGroupDescriptions;
import static com.echothree.model.jooq.server.tables.contactlist.ContactListGroupDetails.ContactListGroupDetails;
import static com.echothree.model.jooq.server.tables.contactlist.ContactListGroups.ContactListGroups;
import static com.echothree.model.jooq.server.tables.contactlist.ContactListTypeDescriptions.ContactListTypeDescriptions;
import static com.echothree.model.jooq.server.tables.contactlist.ContactListTypeDetails.ContactListTypeDetails;
import static com.echothree.model.jooq.server.tables.contactlist.ContactListTypes.ContactListTypes;
import static com.echothree.model.jooq.server.tables.contactlist.ContactLists.ContactLists;
import static com.echothree.model.jooq.server.tables.contactlist.CustomerTypeContactListGroups.CustomerTypeContactListGroups;
import static com.echothree.model.jooq.server.tables.contactlist.CustomerTypeContactLists.CustomerTypeContactLists;
import static com.echothree.model.jooq.server.tables.contactlist.PartyContactListDetails.PartyContactListDetails;
import static com.echothree.model.jooq.server.tables.contactlist.PartyContactLists.PartyContactLists;
import static com.echothree.model.jooq.server.tables.contactlist.PartyTypeContactListGroups.PartyTypeContactListGroups;
import static com.echothree.model.jooq.server.tables.contactlist.PartyTypeContactLists.PartyTypeContactLists;
import static com.echothree.model.jooq.server.tables.customer.CustomerTypeDetails.CustomerTypeDetails;
import static com.echothree.model.jooq.server.tables.customer.CustomerTypes.CustomerTypes;
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
public class ContactListControl
        extends BaseModelControl {
    
    @Inject
    protected ContactControl contactControl;


    @Inject
    protected LetterControl letterControl;

    /** Creates a new instance of ContactListControl */
    protected ContactListControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Contact List Types
    // --------------------------------------------------------------------------------

    @Inject
    protected ContactListTypeDetailFactory contactListTypeDetailFactory;

    @Inject
    protected ContactListTypeFactory contactListTypeFactory;

    @Inject
    ContactListTypeTransferCache contactListTypeTransferCache;

    public ContactListType createContactListType(String contactListTypeName, Chain confirmationRequestChain, Chain subscribeChain, Chain unsubscribeChain,
            Boolean usedForSolicitation, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultContactListType = getDefaultContactListType();
        var defaultFound = defaultContactListType != null;

        if(defaultFound && isDefault) {
            var defaultContactListTypeDetailValue = getDefaultContactListTypeDetailValueForUpdate();

            defaultContactListTypeDetailValue.setIsDefault(false);
            updateContactListTypeFromValue(defaultContactListTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var contactListType = contactListTypeFactory.create();
        var contactListTypeDetail = contactListTypeDetailFactory.create(contactListType, contactListTypeName,
                confirmationRequestChain, subscribeChain, unsubscribeChain, usedForSolicitation, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);

        // Convert to R/W
        contactListType = contactListTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                contactListType.getPrimaryKey());
        contactListType.setActiveDetail(contactListTypeDetail);
        contactListType.setLastDetail(contactListTypeDetail);
        contactListType.store();

        sendEvent(contactListType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return contactListType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ContactListType */
    public ContactListType getContactListTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ContactListTypePK(entityInstance.getEntityUniqueId());

        return contactListTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ContactListType getContactListTypeByEntityInstance(EntityInstance entityInstance) {
        return getContactListTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ContactListType getContactListTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getContactListTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countContactListTypes() {
        return session.getDslContext()
                .selectCount()
                .from(ContactListTypes)
                .join(ContactListTypeDetails).onKey(CONTACT_LIST_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public ContactListType getContactListTypeByName(String contactListTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactListTypes.fields())
                .from(ContactListTypes)
                .join(ContactListTypeDetails).onKey(CONTACT_LIST_TYPES_ACTIVE_DETAIL_FK)
                .where(ContactListTypeDetails.CONTACT_LIST_TYPE_NAME.eq(contactListTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactListTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ContactListType getContactListTypeByName(String contactListTypeName) {
        return getContactListTypeByName(contactListTypeName, EntityPermission.READ_ONLY);
    }

    public ContactListType getContactListTypeByNameForUpdate(String contactListTypeName) {
        return getContactListTypeByName(contactListTypeName, EntityPermission.READ_WRITE);
    }

    public ContactListTypeDetailValue getContactListTypeDetailValueForUpdate(ContactListType contactListType) {
        return contactListType == null? null: contactListType.getLastDetailForUpdate().getContactListTypeDetailValue().clone();
    }

    public ContactListTypeDetailValue getContactListTypeDetailValueByNameForUpdate(String contactListTypeName) {
        return getContactListTypeDetailValueForUpdate(getContactListTypeByNameForUpdate(contactListTypeName));
    }

    public ContactListType getDefaultContactListType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactListTypes.fields())
                .from(ContactListTypes)
                .join(ContactListTypeDetails).onKey(CONTACT_LIST_TYPES_ACTIVE_DETAIL_FK)
                .where(ContactListTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactListTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ContactListType getDefaultContactListType() {
        return getDefaultContactListType(EntityPermission.READ_ONLY);
    }

    public ContactListType getDefaultContactListTypeForUpdate() {
        return getDefaultContactListType(EntityPermission.READ_WRITE);
    }

    public ContactListTypeDetailValue getDefaultContactListTypeDetailValueForUpdate() {
        return getDefaultContactListType(EntityPermission.READ_WRITE).getLastDetailForUpdate().getContactListTypeDetailValue();
    }

    private List<ContactListType> getContactListTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactListTypes.fields())
                .from(ContactListTypes)
                .join(ContactListTypeDetails).onKey(CONTACT_LIST_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ContactListTypeDetails.SORT_ORDER, ContactListTypeDetails.CONTACT_LIST_TYPE_NAME),
                    ContactListTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactListTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ContactListType> getContactListTypes() {
        return getContactListTypes(EntityPermission.READ_ONLY);
    }

    public List<ContactListType> getContactListTypesForUpdate() {
        return getContactListTypes(EntityPermission.READ_WRITE);
    }

    private List<ContactListType> getContactListTypesByConfirmationRequestChain(Chain confirmationRequestChain, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactListTypes.fields())
                .from(ContactListTypes)
                .join(ContactListTypeDetails).onKey(CONTACT_LIST_TYPES_ACTIVE_DETAIL_FK)
                .where(ContactListTypeDetails.CONFIRMATION_REQUEST_CHAIN.eq(confirmationRequestChain.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ContactListTypeDetails.SORT_ORDER, ContactListTypeDetails.CONTACT_LIST_TYPE_NAME),
                    ContactListTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactListTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ContactListType> getContactListTypesByConfirmationRequestChain(Chain confirmationRequestChain) {
        return getContactListTypesByConfirmationRequestChain(confirmationRequestChain, EntityPermission.READ_ONLY);
    }

    public List<ContactListType> getContactListTypesByConfirmationRequestChainForUpdate(Chain confirmationRequestChain) {
        return getContactListTypesByConfirmationRequestChain(confirmationRequestChain, EntityPermission.READ_WRITE);
    }

    private List<ContactListType> getContactListTypesBySubscribeChain(Chain subscribeChain, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactListTypes.fields())
                .from(ContactListTypes)
                .join(ContactListTypeDetails).onKey(CONTACT_LIST_TYPES_ACTIVE_DETAIL_FK)
                .where(ContactListTypeDetails.SUBSCRIBE_CHAIN.eq(subscribeChain.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ContactListTypeDetails.SORT_ORDER, ContactListTypeDetails.CONTACT_LIST_TYPE_NAME),
                    ContactListTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactListTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ContactListType> getContactListTypesBySubscribeChain(Chain subscribeChain) {
        return getContactListTypesBySubscribeChain(subscribeChain, EntityPermission.READ_ONLY);
    }

    public List<ContactListType> getContactListTypesBySubscribeChainForUpdate(Chain subscribeChain) {
        return getContactListTypesBySubscribeChain(subscribeChain, EntityPermission.READ_WRITE);
    }

    private List<ContactListType> getContactListTypesByUnsubscribeChain(Chain unsubscribeChain, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactListTypes.fields())
                .from(ContactListTypes)
                .join(ContactListTypeDetails).onKey(CONTACT_LIST_TYPES_ACTIVE_DETAIL_FK)
                .where(ContactListTypeDetails.UNSUBSCRIBE_CHAIN.eq(unsubscribeChain.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ContactListTypeDetails.SORT_ORDER, ContactListTypeDetails.CONTACT_LIST_TYPE_NAME),
                    ContactListTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactListTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ContactListType> getContactListTypesByUnsubscribeChain(Chain unsubscribeChain) {
        return getContactListTypesByUnsubscribeChain(unsubscribeChain, EntityPermission.READ_ONLY);
    }

    public List<ContactListType> getContactListTypesByUnsubscribeChainForUpdate(Chain unsubscribeChain) {
        return getContactListTypesByUnsubscribeChain(unsubscribeChain, EntityPermission.READ_WRITE);
    }

    public ContactListTypeChoicesBean getContactListTypeChoices(String defaultContactListTypeChoice, Language language, boolean allowNullChoice) {
        var contactListTypes = getContactListTypes();
        var size = contactListTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultContactListTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var contactListType : contactListTypes) {
            var contactListTypeDetail = contactListType.getLastDetail();

            var label = getBestContactListTypeDescription(contactListType, language);
            var value = contactListTypeDetail.getContactListTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultContactListTypeChoice != null && defaultContactListTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && contactListTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ContactListTypeChoicesBean(labels, values, defaultValue);
    }

    public ContactListTypeTransfer getContactListTypeTransfer(UserVisit userVisit, ContactListType contactListType) {
        return contactListTypeTransferCache.getContactListTypeTransfer(userVisit, contactListType);
    }

    public List<ContactListTypeTransfer> getContactListTypeTransfers(UserVisit userVisit, Collection<ContactListType> contactListTypes) {
        List<ContactListTypeTransfer> contactListTypeTransfers = new ArrayList<>(contactListTypes.size());

        contactListTypes.forEach((contactListType) ->
                contactListTypeTransfers.add(contactListTypeTransferCache.getContactListTypeTransfer(userVisit, contactListType))
        );

        return contactListTypeTransfers;
    }

    public List<ContactListTypeTransfer> getContactListTypeTransfers(UserVisit userVisit) {
        return getContactListTypeTransfers(userVisit, getContactListTypes());
    }

    private void updateContactListTypeFromValue(ContactListTypeDetailValue contactListTypeDetailValue, boolean checkDefault, BasePK updatedBy) {
        var contactListType = contactListTypeFactory.getEntityFromPK(
                EntityPermission.READ_WRITE, contactListTypeDetailValue.getContactListTypePK());
        var contactListTypeDetail = contactListType.getActiveDetailForUpdate();

        contactListTypeDetail.setThruTime(session.getStartTime());
        contactListTypeDetail.store();

        var contactListTypePK = contactListTypeDetail.getContactListTypePK();
        var contactListTypeName = contactListTypeDetailValue.getContactListTypeName();
        var confirmationRequestChainPK = contactListTypeDetailValue.getConfirmationRequestChainPK();
        var subscribeChainPK = contactListTypeDetailValue.getSubscribeChainPK();
        var unsubscribeChainPK = contactListTypeDetailValue.getUnsubscribeChainPK();
        var usedForSolicitation = contactListTypeDetailValue.getUsedForSolicitation();
        var isDefault = contactListTypeDetailValue.getIsDefault();
        var sortOrder = contactListTypeDetailValue.getSortOrder();

        if(checkDefault) {
            var defaultContactListType = getDefaultContactListType();
            var defaultFound = defaultContactListType != null && !defaultContactListType.equals(contactListType);

            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                var defaultContactListTypeDetailValue = getDefaultContactListTypeDetailValueForUpdate();

                defaultContactListTypeDetailValue.setIsDefault(false);
                updateContactListTypeFromValue(defaultContactListTypeDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = true;
            }
        }

        contactListTypeDetail = contactListTypeDetailFactory.create(contactListTypePK, contactListTypeName, confirmationRequestChainPK,
                subscribeChainPK, unsubscribeChainPK, usedForSolicitation, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        contactListType.setActiveDetail(contactListTypeDetail);
        contactListType.setLastDetail(contactListTypeDetail);
        contactListType.store();

        sendEvent(contactListTypePK, EventTypes.MODIFY, null, null, updatedBy);
    }

    public void updateContactListTypeFromValue(ContactListTypeDetailValue contactListTypeDetailValue, BasePK updatedBy) {
        updateContactListTypeFromValue(contactListTypeDetailValue, true, updatedBy);
    }

    public void deleteContactListType(ContactListType contactListType, BasePK deletedBy) {
        deleteContactListsByContactListType(contactListType, deletedBy);
        deleteContactListTypeDescriptionsByContactListType(contactListType, deletedBy);

        var contactListTypeDetail = contactListType.getLastDetailForUpdate();
        contactListTypeDetail.setThruTime(session.getStartTime());
        contactListType.setActiveDetail(null);
        contactListType.store();

        // Check for default, and pick one if necessary
        var defaultContactListType = getDefaultContactListType();
        if(defaultContactListType == null) {
            var contactListTypes = getContactListTypesForUpdate();

            if(!contactListTypes.isEmpty()) {
                var iter = contactListTypes.iterator();
                if(iter.hasNext()) {
                    defaultContactListType = iter.next();
                }
                var contactListTypeDetailValue = Objects.requireNonNull(defaultContactListType).getLastDetailForUpdate().getContactListTypeDetailValue().clone();

                contactListTypeDetailValue.setIsDefault(true);
                updateContactListTypeFromValue(contactListTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(contactListType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteContactListTypes(List<ContactListType> contactListTypes, BasePK deletedBy) {
        contactListTypes.forEach((contactListType) -> 
                deleteContactListType(contactListType, deletedBy)
        );
    }

    public void deleteContactListTypesByConfirmationRequestChain(Chain confirmationRequestChain, BasePK deletedBy) {
        deleteContactListTypes(getContactListTypesByConfirmationRequestChainForUpdate(confirmationRequestChain), deletedBy);
    }

    public void deleteContactListTypesBySubscribeChain(Chain subscribeChain, BasePK deletedBy) {
        deleteContactListTypes(getContactListTypesBySubscribeChainForUpdate(subscribeChain), deletedBy);
    }

    public void deleteContactListTypesByUnsubscribeChain(Chain unsubscribeChain, BasePK deletedBy) {
        deleteContactListTypes(getContactListTypesByUnsubscribeChainForUpdate(unsubscribeChain), deletedBy);
    }

    public void deleteContactListTypesByChain(Chain chain, BasePK deletedBy) {
        deleteContactListTypesByConfirmationRequestChain(chain, deletedBy);
        deleteContactListTypesBySubscribeChain(chain, deletedBy);
        deleteContactListTypesByUnsubscribeChain(chain, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Contact List Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ContactListTypeDescriptionFactory contactListTypeDescriptionFactory;

    @Inject
    ContactListTypeDescriptionTransferCache contactListTypeDescriptionTransferCache;

    public ContactListTypeDescription createContactListTypeDescription(ContactListType contactListType, Language language, String description,
            BasePK createdBy) {
        var contactListTypeDescription = contactListTypeDescriptionFactory.create(contactListType,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(contactListType.getPrimaryKey(), EventTypes.MODIFY, contactListTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return contactListTypeDescription;
    }

    private ContactListTypeDescription getContactListTypeDescription(ContactListType contactListType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactListTypeDescriptions.fields())
                .from(ContactListTypeDescriptions)
                .where(ContactListTypeDescriptions.CONTACT_LIST_TYPE.eq(contactListType.getPrimaryKey()),
                        ContactListTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ContactListTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactListTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public ContactListTypeDescription getContactListTypeDescription(ContactListType contactListType, Language language) {
        return getContactListTypeDescription(contactListType, language, EntityPermission.READ_ONLY);
    }

    public ContactListTypeDescription getContactListTypeDescriptionForUpdate(ContactListType contactListType, Language language) {
        return getContactListTypeDescription(contactListType, language, EntityPermission.READ_WRITE);
    }

    public ContactListTypeDescriptionValue getContactListTypeDescriptionValue(ContactListTypeDescription contactListTypeDescription) {
        return contactListTypeDescription == null? null: contactListTypeDescription.getContactListTypeDescriptionValue().clone();
    }

    public ContactListTypeDescriptionValue getContactListTypeDescriptionValueForUpdate(ContactListType contactListType, Language language) {
        return getContactListTypeDescriptionValue(getContactListTypeDescriptionForUpdate(contactListType, language));
    }

    private List<ContactListTypeDescription> getContactListTypeDescriptionsByContactListType(ContactListType contactListType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContactListTypeDescriptions.fields())
                    .from(ContactListTypeDescriptions)
                    .join(Languages).onKey(CONTACT_LIST_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ContactListTypeDescriptions.CONTACT_LIST_TYPE.eq(contactListType.getPrimaryKey()),
                            ContactListTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ContactListTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContactListTypeDescriptions.fields())
                    .from(ContactListTypeDescriptions)
                    .where(ContactListTypeDescriptions.CONTACT_LIST_TYPE.eq(contactListType.getPrimaryKey()),
                            ContactListTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return contactListTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ContactListTypeDescription> getContactListTypeDescriptionsByContactListType(ContactListType contactListType) {
        return getContactListTypeDescriptionsByContactListType(contactListType, EntityPermission.READ_ONLY);
    }

    public List<ContactListTypeDescription> getContactListTypeDescriptionsByContactListTypeForUpdate(ContactListType contactListType) {
        return getContactListTypeDescriptionsByContactListType(contactListType, EntityPermission.READ_WRITE);
    }

    public String getBestContactListTypeDescription(ContactListType contactListType, Language language) {
        String description;
        var contactListTypeDescription = getContactListTypeDescription(contactListType, language);

        if(contactListTypeDescription == null && !language.getIsDefault()) {
            contactListTypeDescription = getContactListTypeDescription(contactListType, partyControl.getDefaultLanguage());
        }

        if(contactListTypeDescription == null) {
            description = contactListType.getLastDetail().getContactListTypeName();
        } else {
            description = contactListTypeDescription.getDescription();
        }

        return description;
    }

    public ContactListTypeDescriptionTransfer getContactListTypeDescriptionTransfer(UserVisit userVisit, ContactListTypeDescription contactListTypeDescription) {
        return contactListTypeDescriptionTransferCache.getContactListTypeDescriptionTransfer(userVisit, contactListTypeDescription);
    }

    public List<ContactListTypeDescriptionTransfer> getContactListTypeDescriptionTransfersByContactListType(UserVisit userVisit, ContactListType contactListType) {
        var contactListTypeDescriptions = getContactListTypeDescriptionsByContactListType(contactListType);
        List<ContactListTypeDescriptionTransfer> contactListTypeDescriptionTransfers = new ArrayList<>(contactListTypeDescriptions.size());

        contactListTypeDescriptions.forEach((contactListTypeDescription) -> {
            contactListTypeDescriptionTransfers.add(contactListTypeDescriptionTransferCache.getContactListTypeDescriptionTransfer(userVisit, contactListTypeDescription));
        });

        return contactListTypeDescriptionTransfers;
    }

    public void updateContactListTypeDescriptionFromValue(ContactListTypeDescriptionValue contactListTypeDescriptionValue, BasePK updatedBy) {
        if(contactListTypeDescriptionValue.hasBeenModified()) {
            var contactListTypeDescription = contactListTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     contactListTypeDescriptionValue.getPrimaryKey());

            contactListTypeDescription.setThruTime(session.getStartTime());
            contactListTypeDescription.store();

            var contactListType = contactListTypeDescription.getContactListType();
            var language = contactListTypeDescription.getLanguage();
            var description = contactListTypeDescriptionValue.getDescription();

            contactListTypeDescription = contactListTypeDescriptionFactory.create(contactListType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(contactListType.getPrimaryKey(), EventTypes.MODIFY, contactListTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteContactListTypeDescription(ContactListTypeDescription contactListTypeDescription, BasePK deletedBy) {
        contactListTypeDescription.setThruTime(session.getStartTime());

        sendEvent(contactListTypeDescription.getContactListTypePK(), EventTypes.MODIFY, contactListTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteContactListTypeDescriptionsByContactListType(ContactListType contactListType, BasePK deletedBy) {
        var contactListTypeDescriptions = getContactListTypeDescriptionsByContactListTypeForUpdate(contactListType);

        contactListTypeDescriptions.forEach((contactListTypeDescription) -> 
                deleteContactListTypeDescription(contactListTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Contact List Groups
    // --------------------------------------------------------------------------------

    @Inject
    protected ContactListGroupDetailFactory contactListGroupDetailFactory;

    @Inject
    protected ContactListGroupFactory contactListGroupFactory;

    @Inject
    ContactListGroupTransferCache contactListGroupTransferCache;

    public ContactListGroup createContactListGroup(String contactListGroupName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultContactListGroup = getDefaultContactListGroup();
        var defaultFound = defaultContactListGroup != null;

        if(defaultFound && isDefault) {
            var defaultContactListGroupDetailValue = getDefaultContactListGroupDetailValueForUpdate();

            defaultContactListGroupDetailValue.setIsDefault(false);
            updateContactListGroupFromValue(defaultContactListGroupDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var contactListGroup = contactListGroupFactory.create();
        var contactListGroupDetail = contactListGroupDetailFactory.create(contactListGroup, contactListGroupName, isDefault,
                sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        contactListGroup = contactListGroupFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                contactListGroup.getPrimaryKey());
        contactListGroup.setActiveDetail(contactListGroupDetail);
        contactListGroup.setLastDetail(contactListGroupDetail);
        contactListGroup.store();

        sendEvent(contactListGroup.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return contactListGroup;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ContactListGroup */
    public ContactListGroup getContactListGroupByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ContactListGroupPK(entityInstance.getEntityUniqueId());

        return contactListGroupFactory.getEntityFromPK(entityPermission, pk);
    }

    public ContactListGroup getContactListGroupByEntityInstance(EntityInstance entityInstance) {
        return getContactListGroupByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ContactListGroup getContactListGroupByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getContactListGroupByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countContactListGroups() {
        return session.getDslContext()
                .selectCount()
                .from(ContactListGroups)
                .join(ContactListGroupDetails).onKey(CONTACT_LIST_GROUPS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public ContactListGroup getContactListGroupByName(String contactListGroupName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactListGroups.fields())
                .from(ContactListGroups)
                .join(ContactListGroupDetails).onKey(CONTACT_LIST_GROUPS_ACTIVE_DETAIL_FK)
                .where(ContactListGroupDetails.CONTACT_LIST_GROUP_NAME.eq(contactListGroupName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactListGroupFactory.getEntityFromQuery(entityPermission, query);
    }

    public ContactListGroup getContactListGroupByName(String contactListGroupName) {
        return getContactListGroupByName(contactListGroupName, EntityPermission.READ_ONLY);
    }

    public ContactListGroup getContactListGroupByNameForUpdate(String contactListGroupName) {
        return getContactListGroupByName(contactListGroupName, EntityPermission.READ_WRITE);
    }

    public ContactListGroupDetailValue getContactListGroupDetailValueForUpdate(ContactListGroup contactListGroup) {
        return contactListGroup == null? null: contactListGroup.getLastDetailForUpdate().getContactListGroupDetailValue().clone();
    }

    public ContactListGroupDetailValue getContactListGroupDetailValueByNameForUpdate(String contactListGroupName) {
        return getContactListGroupDetailValueForUpdate(getContactListGroupByNameForUpdate(contactListGroupName));
    }

    public ContactListGroup getDefaultContactListGroup(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactListGroups.fields())
                .from(ContactListGroups)
                .join(ContactListGroupDetails).onKey(CONTACT_LIST_GROUPS_ACTIVE_DETAIL_FK)
                .where(ContactListGroupDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactListGroupFactory.getEntityFromQuery(entityPermission, query);
    }

    public ContactListGroup getDefaultContactListGroup() {
        return getDefaultContactListGroup(EntityPermission.READ_ONLY);
    }

    public ContactListGroup getDefaultContactListGroupForUpdate() {
        return getDefaultContactListGroup(EntityPermission.READ_WRITE);
    }

    public ContactListGroupDetailValue getDefaultContactListGroupDetailValueForUpdate() {
        return getDefaultContactListGroup(EntityPermission.READ_WRITE).getLastDetailForUpdate().getContactListGroupDetailValue();
    }

    private List<ContactListGroup> getContactListGroups(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactListGroups.fields())
                .from(ContactListGroups)
                .join(ContactListGroupDetails).onKey(CONTACT_LIST_GROUPS_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ContactListGroupDetails.SORT_ORDER, ContactListGroupDetails.CONTACT_LIST_GROUP_NAME),
                    ContactListGroupFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactListGroupFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ContactListGroup> getContactListGroups() {
        return getContactListGroups(EntityPermission.READ_ONLY);
    }

    public List<ContactListGroup> getContactListGroupsForUpdate() {
        return getContactListGroups(EntityPermission.READ_WRITE);
    }

    public ContactListGroupChoicesBean getContactListGroupChoices(String defaultContactListGroupChoice, Language language, boolean allowNullChoice) {
        var contactListGroups = getContactListGroups();
        var size = contactListGroups.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultContactListGroupChoice == null) {
                defaultValue = "";
            }
        }

        for(var contactListGroup : contactListGroups) {
            var contactListGroupDetail = contactListGroup.getLastDetail();

            var label = getBestContactListGroupDescription(contactListGroup, language);
            var value = contactListGroupDetail.getContactListGroupName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultContactListGroupChoice != null && defaultContactListGroupChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && contactListGroupDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ContactListGroupChoicesBean(labels, values, defaultValue);
    }

    public ContactListGroupTransfer getContactListGroupTransfer(UserVisit userVisit, ContactListGroup contactListGroup) {
        return contactListGroupTransferCache.getContactListGroupTransfer(userVisit, contactListGroup);
    }

    public List<ContactListGroupTransfer> getContactListGroupTransfers(UserVisit userVisit, Collection<ContactListGroup> contactListGroups) {
        List<ContactListGroupTransfer> contactListGroupTransfers = new ArrayList<>(contactListGroups.size());

        contactListGroups.forEach((contactListGroup) ->
                contactListGroupTransfers.add(contactListGroupTransferCache.getContactListGroupTransfer(userVisit, contactListGroup))
        );

        return contactListGroupTransfers;
    }

    public List<ContactListGroupTransfer> getContactListGroupTransfers(UserVisit userVisit) {
        return getContactListGroupTransfers(userVisit, getContactListGroups());
    }

    private void updateContactListGroupFromValue(ContactListGroupDetailValue contactListGroupDetailValue, boolean checkDefault, BasePK updatedBy) {
        var contactListGroup = contactListGroupFactory.getEntityFromPK(
                EntityPermission.READ_WRITE, contactListGroupDetailValue.getContactListGroupPK());
        var contactListGroupDetail = contactListGroup.getActiveDetailForUpdate();

        contactListGroupDetail.setThruTime(session.getStartTime());
        contactListGroupDetail.store();

        var contactListGroupPK = contactListGroupDetail.getContactListGroupPK();
        var contactListGroupName = contactListGroupDetailValue.getContactListGroupName();
        var isDefault = contactListGroupDetailValue.getIsDefault();
        var sortOrder = contactListGroupDetailValue.getSortOrder();

        if(checkDefault) {
            var defaultContactListGroup = getDefaultContactListGroup();
            var defaultFound = defaultContactListGroup != null && !defaultContactListGroup.equals(contactListGroup);

            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                var defaultContactListGroupDetailValue = getDefaultContactListGroupDetailValueForUpdate();

                defaultContactListGroupDetailValue.setIsDefault(false);
                updateContactListGroupFromValue(defaultContactListGroupDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = true;
            }
        }

        contactListGroupDetail = contactListGroupDetailFactory.create(contactListGroupPK, contactListGroupName, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);

        contactListGroup.setActiveDetail(contactListGroupDetail);
        contactListGroup.setLastDetail(contactListGroupDetail);
        contactListGroup.store();

        sendEvent(contactListGroupPK, EventTypes.MODIFY, null, null, updatedBy);
    }

    public void updateContactListGroupFromValue(ContactListGroupDetailValue contactListGroupDetailValue, BasePK updatedBy) {
        updateContactListGroupFromValue(contactListGroupDetailValue, true, updatedBy);
    }

    public void deleteContactListGroup(ContactListGroup contactListGroup, BasePK deletedBy) {
        deleteContactListsByContactListGroup(contactListGroup, deletedBy);
        deletePartyTypeContactListGroupsByContactListGroup(contactListGroup, deletedBy);
        deleteCustomerTypeContactListGroupsByContactListGroup(contactListGroup, deletedBy);
        deleteCustomerTypeContactListGroupsByContactListGroup(contactListGroup, deletedBy);
        deleteContactListGroupDescriptionsByContactListGroup(contactListGroup, deletedBy);

        var contactListGroupDetail = contactListGroup.getLastDetailForUpdate();
        contactListGroupDetail.setThruTime(session.getStartTime());
        contactListGroup.setActiveDetail(null);
        contactListGroup.store();

        // Check for default, and pick one if necessary
        var defaultContactListGroup = getDefaultContactListGroup();
        if(defaultContactListGroup == null) {
            var contactListGroups = getContactListGroupsForUpdate();

            if(!contactListGroups.isEmpty()) {
                var iter = contactListGroups.iterator();
                if(iter.hasNext()) {
                    defaultContactListGroup = iter.next();
                }
                var contactListGroupDetailValue = Objects.requireNonNull(defaultContactListGroup).getLastDetailForUpdate().getContactListGroupDetailValue().clone();

                contactListGroupDetailValue.setIsDefault(true);
                updateContactListGroupFromValue(contactListGroupDetailValue, false, deletedBy);
            }
        }

        sendEvent(contactListGroup.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Contact List Group Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ContactListGroupDescriptionFactory contactListGroupDescriptionFactory;

    @Inject
    ContactListGroupDescriptionTransferCache contactListGroupDescriptionTransferCache;

    public ContactListGroupDescription createContactListGroupDescription(ContactListGroup contactListGroup, Language language, String description,
            BasePK createdBy) {
        var contactListGroupDescription = contactListGroupDescriptionFactory.create(contactListGroup,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(contactListGroup.getPrimaryKey(), EventTypes.MODIFY, contactListGroupDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return contactListGroupDescription;
    }

    private ContactListGroupDescription getContactListGroupDescription(ContactListGroup contactListGroup, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactListGroupDescriptions.fields())
                .from(ContactListGroupDescriptions)
                .where(ContactListGroupDescriptions.CONTACT_LIST_GROUP.eq(contactListGroup.getPrimaryKey()),
                        ContactListGroupDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ContactListGroupDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactListGroupDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public ContactListGroupDescription getContactListGroupDescription(ContactListGroup contactListGroup, Language language) {
        return getContactListGroupDescription(contactListGroup, language, EntityPermission.READ_ONLY);
    }

    public ContactListGroupDescription getContactListGroupDescriptionForUpdate(ContactListGroup contactListGroup, Language language) {
        return getContactListGroupDescription(contactListGroup, language, EntityPermission.READ_WRITE);
    }

    public ContactListGroupDescriptionValue getContactListGroupDescriptionValue(ContactListGroupDescription contactListGroupDescription) {
        return contactListGroupDescription == null? null: contactListGroupDescription.getContactListGroupDescriptionValue().clone();
    }

    public ContactListGroupDescriptionValue getContactListGroupDescriptionValueForUpdate(ContactListGroup contactListGroup, Language language) {
        return getContactListGroupDescriptionValue(getContactListGroupDescriptionForUpdate(contactListGroup, language));
    }

    private List<ContactListGroupDescription> getContactListGroupDescriptionsByContactListGroup(ContactListGroup contactListGroup, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContactListGroupDescriptions.fields())
                    .from(ContactListGroupDescriptions)
                    .join(Languages).onKey(CONTACT_LIST_GROUP_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ContactListGroupDescriptions.CONTACT_LIST_GROUP.eq(contactListGroup.getPrimaryKey()),
                            ContactListGroupDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ContactListGroupDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContactListGroupDescriptions.fields())
                    .from(ContactListGroupDescriptions)
                    .where(ContactListGroupDescriptions.CONTACT_LIST_GROUP.eq(contactListGroup.getPrimaryKey()),
                            ContactListGroupDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return contactListGroupDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ContactListGroupDescription> getContactListGroupDescriptionsByContactListGroup(ContactListGroup contactListGroup) {
        return getContactListGroupDescriptionsByContactListGroup(contactListGroup, EntityPermission.READ_ONLY);
    }

    public List<ContactListGroupDescription> getContactListGroupDescriptionsByContactListGroupForUpdate(ContactListGroup contactListGroup) {
        return getContactListGroupDescriptionsByContactListGroup(contactListGroup, EntityPermission.READ_WRITE);
    }

    public String getBestContactListGroupDescription(ContactListGroup contactListGroup, Language language) {
        String description;
        var contactListGroupDescription = getContactListGroupDescription(contactListGroup, language);

        if(contactListGroupDescription == null && !language.getIsDefault()) {
            contactListGroupDescription = getContactListGroupDescription(contactListGroup, partyControl.getDefaultLanguage());
        }

        if(contactListGroupDescription == null) {
            description = contactListGroup.getLastDetail().getContactListGroupName();
        } else {
            description = contactListGroupDescription.getDescription();
        }

        return description;
    }

    public ContactListGroupDescriptionTransfer getContactListGroupDescriptionTransfer(UserVisit userVisit, ContactListGroupDescription contactListGroupDescription) {
        return contactListGroupDescriptionTransferCache.getContactListGroupDescriptionTransfer(userVisit, contactListGroupDescription);
    }

    public List<ContactListGroupDescriptionTransfer> getContactListGroupDescriptionTransfersByContactListGroup(UserVisit userVisit, ContactListGroup contactListGroup) {
        var contactListGroupDescriptions = getContactListGroupDescriptionsByContactListGroup(contactListGroup);
        List<ContactListGroupDescriptionTransfer> contactListGroupDescriptionTransfers = new ArrayList<>(contactListGroupDescriptions.size());

        contactListGroupDescriptions.forEach((contactListGroupDescription) -> {
            contactListGroupDescriptionTransfers.add(contactListGroupDescriptionTransferCache.getContactListGroupDescriptionTransfer(userVisit, contactListGroupDescription));
        });

        return contactListGroupDescriptionTransfers;
    }

    public void updateContactListGroupDescriptionFromValue(ContactListGroupDescriptionValue contactListGroupDescriptionValue, BasePK updatedBy) {
        if(contactListGroupDescriptionValue.hasBeenModified()) {
            var contactListGroupDescription = contactListGroupDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     contactListGroupDescriptionValue.getPrimaryKey());

            contactListGroupDescription.setThruTime(session.getStartTime());
            contactListGroupDescription.store();

            var contactListGroup = contactListGroupDescription.getContactListGroup();
            var language = contactListGroupDescription.getLanguage();
            var description = contactListGroupDescriptionValue.getDescription();

            contactListGroupDescription = contactListGroupDescriptionFactory.create(contactListGroup, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(contactListGroup.getPrimaryKey(), EventTypes.MODIFY, contactListGroupDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteContactListGroupDescription(ContactListGroupDescription contactListGroupDescription, BasePK deletedBy) {
        contactListGroupDescription.setThruTime(session.getStartTime());

        sendEvent(contactListGroupDescription.getContactListGroupPK(), EventTypes.MODIFY, contactListGroupDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteContactListGroupDescriptionsByContactListGroup(ContactListGroup contactListGroup, BasePK deletedBy) {
        var contactListGroupDescriptions = getContactListGroupDescriptionsByContactListGroupForUpdate(contactListGroup);

        contactListGroupDescriptions.forEach((contactListGroupDescription) -> 
                deleteContactListGroupDescription(contactListGroupDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Contact List Frequencies
    // --------------------------------------------------------------------------------

    @Inject
    protected ContactListFrequencyDetailFactory contactListFrequencyDetailFactory;

    @Inject
    protected ContactListFrequencyFactory contactListFrequencyFactory;

    @Inject
    ContactListFrequencyTransferCache contactListFrequencyTransferCache;

    public ContactListFrequency createContactListFrequency(String contactListFrequencyName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultContactListFrequency = getDefaultContactListFrequency();
        var defaultFound = defaultContactListFrequency != null;

        if(defaultFound && isDefault) {
            var defaultContactListFrequencyDetailValue = getDefaultContactListFrequencyDetailValueForUpdate();

            defaultContactListFrequencyDetailValue.setIsDefault(false);
            updateContactListFrequencyFromValue(defaultContactListFrequencyDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var contactListFrequency = contactListFrequencyFactory.create();
        var contactListFrequencyDetail = contactListFrequencyDetailFactory.create(contactListFrequency, contactListFrequencyName, isDefault,
                sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        contactListFrequency = contactListFrequencyFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                contactListFrequency.getPrimaryKey());
        contactListFrequency.setActiveDetail(contactListFrequencyDetail);
        contactListFrequency.setLastDetail(contactListFrequencyDetail);
        contactListFrequency.store();

        sendEvent(contactListFrequency.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return contactListFrequency;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ContactListFrequency */
    public ContactListFrequency getContactListFrequencyByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ContactListFrequencyPK(entityInstance.getEntityUniqueId());

        return contactListFrequencyFactory.getEntityFromPK(entityPermission, pk);
    }

    public ContactListFrequency getContactListFrequencyByEntityInstance(EntityInstance entityInstance) {
        return getContactListFrequencyByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ContactListFrequency getContactListFrequencyByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getContactListFrequencyByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countContactListFrequencies() {
        return session.getDslContext()
                .selectCount()
                .from(ContactListFrequencies)
                .join(ContactListFrequencyDetails).onKey(CONTACT_LIST_FREQUENCIES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public ContactListFrequency getContactListFrequencyByName(String contactListFrequencyName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactListFrequencies.fields())
                .from(ContactListFrequencies)
                .join(ContactListFrequencyDetails).onKey(CONTACT_LIST_FREQUENCIES_ACTIVE_DETAIL_FK)
                .where(ContactListFrequencyDetails.CONTACT_LIST_FREQUENCY_NAME.eq(contactListFrequencyName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactListFrequencyFactory.getEntityFromQuery(entityPermission, query);
    }

    public ContactListFrequency getContactListFrequencyByName(String contactListFrequencyName) {
        return getContactListFrequencyByName(contactListFrequencyName, EntityPermission.READ_ONLY);
    }

    public ContactListFrequency getContactListFrequencyByNameForUpdate(String contactListFrequencyName) {
        return getContactListFrequencyByName(contactListFrequencyName, EntityPermission.READ_WRITE);
    }

    public ContactListFrequencyDetailValue getContactListFrequencyDetailValueForUpdate(ContactListFrequency contactListFrequency) {
        return contactListFrequency == null? null: contactListFrequency.getLastDetailForUpdate().getContactListFrequencyDetailValue().clone();
    }

    public ContactListFrequencyDetailValue getContactListFrequencyDetailValueByNameForUpdate(String contactListFrequencyName) {
        return getContactListFrequencyDetailValueForUpdate(getContactListFrequencyByNameForUpdate(contactListFrequencyName));
    }

    public ContactListFrequency getDefaultContactListFrequency(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactListFrequencies.fields())
                .from(ContactListFrequencies)
                .join(ContactListFrequencyDetails).onKey(CONTACT_LIST_FREQUENCIES_ACTIVE_DETAIL_FK)
                .where(ContactListFrequencyDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactListFrequencyFactory.getEntityFromQuery(entityPermission, query);
    }

    public ContactListFrequency getDefaultContactListFrequency() {
        return getDefaultContactListFrequency(EntityPermission.READ_ONLY);
    }

    public ContactListFrequency getDefaultContactListFrequencyForUpdate() {
        return getDefaultContactListFrequency(EntityPermission.READ_WRITE);
    }

    public ContactListFrequencyDetailValue getDefaultContactListFrequencyDetailValueForUpdate() {
        return getDefaultContactListFrequency(EntityPermission.READ_WRITE).getLastDetailForUpdate().getContactListFrequencyDetailValue();
    }

    private List<ContactListFrequency> getContactListFrequencies(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactListFrequencies.fields())
                .from(ContactListFrequencies)
                .join(ContactListFrequencyDetails).onKey(CONTACT_LIST_FREQUENCIES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ContactListFrequencyDetails.SORT_ORDER, ContactListFrequencyDetails.CONTACT_LIST_FREQUENCY_NAME),
                    ContactListFrequencyFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactListFrequencyFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ContactListFrequency> getContactListFrequencies() {
        return getContactListFrequencies(EntityPermission.READ_ONLY);
    }

    public List<ContactListFrequency> getContactListFrequenciesForUpdate() {
        return getContactListFrequencies(EntityPermission.READ_WRITE);
    }

    public ContactListFrequencyChoicesBean getContactListFrequencyChoices(String defaultContactListFrequencyChoice, Language language, boolean allowNullChoice) {
        var contactListFrequencies = getContactListFrequencies();
        var size = contactListFrequencies.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultContactListFrequencyChoice == null) {
                defaultValue = "";
            }
        }

        for(var contactListFrequency : contactListFrequencies) {
            var contactListFrequencyDetail = contactListFrequency.getLastDetail();

            var label = getBestContactListFrequencyDescription(contactListFrequency, language);
            var value = contactListFrequencyDetail.getContactListFrequencyName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultContactListFrequencyChoice != null && defaultContactListFrequencyChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && contactListFrequencyDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ContactListFrequencyChoicesBean(labels, values, defaultValue);
    }

    public ContactListFrequencyTransfer getContactListFrequencyTransfer(UserVisit userVisit, ContactListFrequency contactListFrequency) {
        return contactListFrequencyTransferCache.getContactListFrequencyTransfer(userVisit, contactListFrequency);
    }

    public List<ContactListFrequencyTransfer> getContactListFrequencyTransfers(UserVisit userVisit, Collection<ContactListFrequency> contactListFrequencies) {
        List<ContactListFrequencyTransfer> contactListFrequencyTransfers = new ArrayList<>(contactListFrequencies.size());

        contactListFrequencies.forEach((contactListFrequency) ->
                contactListFrequencyTransfers.add(contactListFrequencyTransferCache.getContactListFrequencyTransfer(userVisit, contactListFrequency))
        );

        return contactListFrequencyTransfers;
    }

    public List<ContactListFrequencyTransfer> getContactListFrequencyTransfers(UserVisit userVisit) {
        return getContactListFrequencyTransfers(userVisit, getContactListFrequencies());
    }

    private void updateContactListFrequencyFromValue(ContactListFrequencyDetailValue contactListFrequencyDetailValue, boolean checkDefault, BasePK updatedBy) {
        var contactListFrequency = contactListFrequencyFactory.getEntityFromPK(
                EntityPermission.READ_WRITE, contactListFrequencyDetailValue.getContactListFrequencyPK());
        var contactListFrequencyDetail = contactListFrequency.getActiveDetailForUpdate();

        contactListFrequencyDetail.setThruTime(session.getStartTime());
        contactListFrequencyDetail.store();

        var contactListFrequencyPK = contactListFrequencyDetail.getContactListFrequencyPK();
        var contactListFrequencyName = contactListFrequencyDetailValue.getContactListFrequencyName();
        var isDefault = contactListFrequencyDetailValue.getIsDefault();
        var sortOrder = contactListFrequencyDetailValue.getSortOrder();

        if(checkDefault) {
            var defaultContactListFrequency = getDefaultContactListFrequency();
            var defaultFound = defaultContactListFrequency != null && !defaultContactListFrequency.equals(contactListFrequency);

            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                var defaultContactListFrequencyDetailValue = getDefaultContactListFrequencyDetailValueForUpdate();

                defaultContactListFrequencyDetailValue.setIsDefault(false);
                updateContactListFrequencyFromValue(defaultContactListFrequencyDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = true;
            }
        }

        contactListFrequencyDetail = contactListFrequencyDetailFactory.create(contactListFrequencyPK, contactListFrequencyName, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);

        contactListFrequency.setActiveDetail(contactListFrequencyDetail);
        contactListFrequency.setLastDetail(contactListFrequencyDetail);
        contactListFrequency.store();

        sendEvent(contactListFrequencyPK, EventTypes.MODIFY, null, null, updatedBy);
    }

    public void updateContactListFrequencyFromValue(ContactListFrequencyDetailValue contactListFrequencyDetailValue, BasePK updatedBy) {
        updateContactListFrequencyFromValue(contactListFrequencyDetailValue, true, updatedBy);
    }

    public void deleteContactListFrequency(ContactListFrequency contactListFrequency, BasePK deletedBy) {
        deleteContactListsByContactListFrequency(contactListFrequency, deletedBy);
        deleteContactListFrequencyDescriptionsByContactListFrequency(contactListFrequency, deletedBy);

        var contactListFrequencyDetail = contactListFrequency.getLastDetailForUpdate();
        contactListFrequencyDetail.setThruTime(session.getStartTime());
        contactListFrequency.setActiveDetail(null);
        contactListFrequency.store();

        // Check for default, and pick one if necessary
        var defaultContactListFrequency = getDefaultContactListFrequency();
        if(defaultContactListFrequency == null) {
            var contactListFrequencies = getContactListFrequenciesForUpdate();

            if(!contactListFrequencies.isEmpty()) {
                var iter = contactListFrequencies.iterator();
                if(iter.hasNext()) {
                    defaultContactListFrequency = iter.next();
                }
                var contactListFrequencyDetailValue = Objects.requireNonNull(defaultContactListFrequency).getLastDetailForUpdate().getContactListFrequencyDetailValue().clone();

                contactListFrequencyDetailValue.setIsDefault(true);
                updateContactListFrequencyFromValue(contactListFrequencyDetailValue, false, deletedBy);
            }
        }

        sendEvent(contactListFrequency.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Contact List Frequency Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ContactListFrequencyDescriptionFactory contactListFrequencyDescriptionFactory;

    @Inject
    ContactListFrequencyDescriptionTransferCache contactListFrequencyDescriptionTransferCache;

    public ContactListFrequencyDescription createContactListFrequencyDescription(ContactListFrequency contactListFrequency, Language language, String description,
            BasePK createdBy) {
        var contactListFrequencyDescription = contactListFrequencyDescriptionFactory.create(contactListFrequency,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(contactListFrequency.getPrimaryKey(), EventTypes.MODIFY, contactListFrequencyDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return contactListFrequencyDescription;
    }

    private ContactListFrequencyDescription getContactListFrequencyDescription(ContactListFrequency contactListFrequency, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactListFrequencyDescriptions.fields())
                .from(ContactListFrequencyDescriptions)
                .where(ContactListFrequencyDescriptions.CONTACT_LIST_FREQUENCY.eq(contactListFrequency.getPrimaryKey()),
                        ContactListFrequencyDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ContactListFrequencyDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactListFrequencyDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public ContactListFrequencyDescription getContactListFrequencyDescription(ContactListFrequency contactListFrequency, Language language) {
        return getContactListFrequencyDescription(contactListFrequency, language, EntityPermission.READ_ONLY);
    }

    public ContactListFrequencyDescription getContactListFrequencyDescriptionForUpdate(ContactListFrequency contactListFrequency, Language language) {
        return getContactListFrequencyDescription(contactListFrequency, language, EntityPermission.READ_WRITE);
    }

    public ContactListFrequencyDescriptionValue getContactListFrequencyDescriptionValue(ContactListFrequencyDescription contactListFrequencyDescription) {
        return contactListFrequencyDescription == null? null: contactListFrequencyDescription.getContactListFrequencyDescriptionValue().clone();
    }

    public ContactListFrequencyDescriptionValue getContactListFrequencyDescriptionValueForUpdate(ContactListFrequency contactListFrequency, Language language) {
        return getContactListFrequencyDescriptionValue(getContactListFrequencyDescriptionForUpdate(contactListFrequency, language));
    }

    private List<ContactListFrequencyDescription> getContactListFrequencyDescriptionsByContactListFrequency(ContactListFrequency contactListFrequency, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContactListFrequencyDescriptions.fields())
                    .from(ContactListFrequencyDescriptions)
                    .join(Languages).onKey(CONTACT_LIST_FREQUENCY_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ContactListFrequencyDescriptions.CONTACT_LIST_FREQUENCY.eq(contactListFrequency.getPrimaryKey()),
                            ContactListFrequencyDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ContactListFrequencyDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContactListFrequencyDescriptions.fields())
                    .from(ContactListFrequencyDescriptions)
                    .where(ContactListFrequencyDescriptions.CONTACT_LIST_FREQUENCY.eq(contactListFrequency.getPrimaryKey()),
                            ContactListFrequencyDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return contactListFrequencyDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ContactListFrequencyDescription> getContactListFrequencyDescriptionsByContactListFrequency(ContactListFrequency contactListFrequency) {
        return getContactListFrequencyDescriptionsByContactListFrequency(contactListFrequency, EntityPermission.READ_ONLY);
    }

    public List<ContactListFrequencyDescription> getContactListFrequencyDescriptionsByContactListFrequencyForUpdate(ContactListFrequency contactListFrequency) {
        return getContactListFrequencyDescriptionsByContactListFrequency(contactListFrequency, EntityPermission.READ_WRITE);
    }

    public String getBestContactListFrequencyDescription(ContactListFrequency contactListFrequency, Language language) {
        String description;
        var contactListFrequencyDescription = getContactListFrequencyDescription(contactListFrequency, language);

        if(contactListFrequencyDescription == null && !language.getIsDefault()) {
            contactListFrequencyDescription = getContactListFrequencyDescription(contactListFrequency, partyControl.getDefaultLanguage());
        }

        if(contactListFrequencyDescription == null) {
            description = contactListFrequency.getLastDetail().getContactListFrequencyName();
        } else {
            description = contactListFrequencyDescription.getDescription();
        }

        return description;
    }

    public ContactListFrequencyDescriptionTransfer getContactListFrequencyDescriptionTransfer(UserVisit userVisit, ContactListFrequencyDescription contactListFrequencyDescription) {
        return contactListFrequencyDescriptionTransferCache.getContactListFrequencyDescriptionTransfer(userVisit, contactListFrequencyDescription);
    }

    public List<ContactListFrequencyDescriptionTransfer> getContactListFrequencyDescriptionTransfersByContactListFrequency(UserVisit userVisit, ContactListFrequency contactListFrequency) {
        var contactListFrequencyDescriptions = getContactListFrequencyDescriptionsByContactListFrequency(contactListFrequency);
        List<ContactListFrequencyDescriptionTransfer> contactListFrequencyDescriptionTransfers = new ArrayList<>(contactListFrequencyDescriptions.size());

        contactListFrequencyDescriptions.forEach((contactListFrequencyDescription) -> {
            contactListFrequencyDescriptionTransfers.add(contactListFrequencyDescriptionTransferCache.getContactListFrequencyDescriptionTransfer(userVisit, contactListFrequencyDescription));
        });

        return contactListFrequencyDescriptionTransfers;
    }

    public void updateContactListFrequencyDescriptionFromValue(ContactListFrequencyDescriptionValue contactListFrequencyDescriptionValue, BasePK updatedBy) {
        if(contactListFrequencyDescriptionValue.hasBeenModified()) {
            var contactListFrequencyDescription = contactListFrequencyDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     contactListFrequencyDescriptionValue.getPrimaryKey());

            contactListFrequencyDescription.setThruTime(session.getStartTime());
            contactListFrequencyDescription.store();

            var contactListFrequency = contactListFrequencyDescription.getContactListFrequency();
            var language = contactListFrequencyDescription.getLanguage();
            var description = contactListFrequencyDescriptionValue.getDescription();

            contactListFrequencyDescription = contactListFrequencyDescriptionFactory.create(contactListFrequency, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(contactListFrequency.getPrimaryKey(), EventTypes.MODIFY, contactListFrequencyDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteContactListFrequencyDescription(ContactListFrequencyDescription contactListFrequencyDescription, BasePK deletedBy) {
        contactListFrequencyDescription.setThruTime(session.getStartTime());

        sendEvent(contactListFrequencyDescription.getContactListFrequencyPK(), EventTypes.MODIFY, contactListFrequencyDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteContactListFrequencyDescriptionsByContactListFrequency(ContactListFrequency contactListFrequency, BasePK deletedBy) {
        var contactListFrequencyDescriptions = getContactListFrequencyDescriptionsByContactListFrequencyForUpdate(contactListFrequency);

        contactListFrequencyDescriptions.forEach((contactListFrequencyDescription) -> 
                deleteContactListFrequencyDescription(contactListFrequencyDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Contact Lists
    // --------------------------------------------------------------------------------

    @Inject
    protected ContactListDetailFactory contactListDetailFactory;

    @Inject
    protected ContactListFactory contactListFactory;

    @Inject
    ContactListTransferCache contactListTransferCache;

    public ContactList createContactList(String contactListName, ContactListGroup contactListGroup, ContactListType contactListType,
            ContactListFrequency contactListFrequency, WorkflowEntrance defaultPartyContactListStatus, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultContactList = getDefaultContactList();
        var defaultFound = defaultContactList != null;

        if(defaultFound && isDefault) {
            var defaultContactListDetailValue = getDefaultContactListDetailValueForUpdate();

            defaultContactListDetailValue.setIsDefault(false);
            updateContactListFromValue(defaultContactListDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var contactList = contactListFactory.create();
        var contactListDetail = contactListDetailFactory.create(contactList, contactListName, contactListGroup, contactListType,
            contactListFrequency, defaultPartyContactListStatus, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        contactList = contactListFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                contactList.getPrimaryKey());
        contactList.setActiveDetail(contactListDetail);
        contactList.setLastDetail(contactListDetail);
        contactList.store();

        sendEvent(contactList.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return contactList;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ContactList */
    public ContactList getContactListByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ContactListPK(entityInstance.getEntityUniqueId());

        return contactListFactory.getEntityFromPK(entityPermission, pk);
    }

    public ContactList getContactListByEntityInstance(EntityInstance entityInstance) {
        return getContactListByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ContactList getContactListByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getContactListByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countContactLists() {
        return session.getDslContext()
                .selectCount()
                .from(ContactLists)
                .join(ContactListDetails).onKey(CONTACT_LISTS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countContactListsByContactListGroup(final ContactListGroup contactListGroup) {
        return session.getDslContext()
                .selectCount()
                .from(ContactLists)
                .join(ContactListDetails).onKey(CONTACT_LISTS_ACTIVE_DETAIL_FK)
                .where(ContactListDetails.CONTACT_LIST_GROUP.eq(contactListGroup.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countContactListsByContactListType(final ContactListType contactListType) {
        return session.getDslContext()
                .selectCount()
                .from(ContactLists)
                .join(ContactListDetails).onKey(CONTACT_LISTS_ACTIVE_DETAIL_FK)
                .where(ContactListDetails.CONTACT_LIST_TYPE.eq(contactListType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countContactListsByContactListFrequency(final ContactListFrequency contactListFrequency) {
        return session.getDslContext()
                .selectCount()
                .from(ContactLists)
                .join(ContactListDetails).onKey(CONTACT_LISTS_ACTIVE_DETAIL_FK)
                .where(ContactListDetails.CONTACT_LIST_FREQUENCY.eq(contactListFrequency.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countContactListsByDefaultPartyContactListStatus(final WorkflowEntrance defaultPartyContactListStatus) {
        return session.getDslContext()
                .selectCount()
                .from(ContactLists)
                .join(ContactListDetails).onKey(CONTACT_LISTS_ACTIVE_DETAIL_FK)
                .where(ContactListDetails.DEFAULT_PARTY_CONTACT_LIST_STATUS.eq(defaultPartyContactListStatus.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public ContactList getContactListByName(String contactListName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactLists.fields())
                .from(ContactLists)
                .join(ContactListDetails).onKey(CONTACT_LISTS_ACTIVE_DETAIL_FK)
                .where(ContactListDetails.CONTACT_LIST_NAME.eq(contactListName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactListFactory.getEntityFromQuery(entityPermission, query);
    }

    public ContactList getContactListByName(String contactListName) {
        return getContactListByName(contactListName, EntityPermission.READ_ONLY);
    }

    public ContactList getContactListByNameForUpdate(String contactListName) {
        return getContactListByName(contactListName, EntityPermission.READ_WRITE);
    }

    public ContactListDetailValue getContactListDetailValueForUpdate(ContactList contactList) {
        return contactList == null? null: contactList.getLastDetailForUpdate().getContactListDetailValue().clone();
    }

    public ContactListDetailValue getContactListDetailValueByNameForUpdate(String contactListName) {
        return getContactListDetailValueForUpdate(getContactListByNameForUpdate(contactListName));
    }

    public ContactList getDefaultContactList(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactLists.fields())
                .from(ContactLists)
                .join(ContactListDetails).onKey(CONTACT_LISTS_ACTIVE_DETAIL_FK)
                .where(ContactListDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactListFactory.getEntityFromQuery(entityPermission, query);
    }

    public ContactList getDefaultContactList() {
        return getDefaultContactList(EntityPermission.READ_ONLY);
    }

    public ContactList getDefaultContactListForUpdate() {
        return getDefaultContactList(EntityPermission.READ_WRITE);
    }

    public ContactListDetailValue getDefaultContactListDetailValueForUpdate() {
        return getDefaultContactList(EntityPermission.READ_WRITE).getLastDetailForUpdate().getContactListDetailValue();
    }

    private List<ContactList> getContactLists(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactLists.fields())
                .from(ContactLists)
                .join(ContactListDetails).onKey(CONTACT_LISTS_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ContactListDetails.SORT_ORDER, ContactListDetails.CONTACT_LIST_NAME),
                    ContactListFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactListFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ContactList> getContactLists() {
        return getContactLists(EntityPermission.READ_ONLY);
    }

    public List<ContactList> getContactListsForUpdate() {
        return getContactLists(EntityPermission.READ_WRITE);
    }

    private List<ContactList> getContactListsByContactListGroup(ContactListGroup contactListGroup, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactLists.fields())
                .from(ContactLists)
                .join(ContactListDetails).onKey(CONTACT_LISTS_ACTIVE_DETAIL_FK)
                .where(ContactListDetails.CONTACT_LIST_GROUP.eq(contactListGroup.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ContactListDetails.SORT_ORDER, ContactListDetails.CONTACT_LIST_NAME),
                    ContactListFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactListFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ContactList> getContactListsByContactListGroup(ContactListGroup contactListGroup) {
        return getContactListsByContactListGroup(contactListGroup, EntityPermission.READ_ONLY);
    }

    public List<ContactList> getContactListsByContactListGroupForUpdate(ContactListGroup contactListGroup) {
        return getContactListsByContactListGroup(contactListGroup, EntityPermission.READ_WRITE);
    }

    private List<ContactList> getContactListsByContactListType(ContactListType contactListType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactLists.fields())
                .from(ContactLists)
                .join(ContactListDetails).onKey(CONTACT_LISTS_ACTIVE_DETAIL_FK)
                .where(ContactListDetails.CONTACT_LIST_TYPE.eq(contactListType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ContactListDetails.SORT_ORDER, ContactListDetails.CONTACT_LIST_NAME),
                    ContactListFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactListFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ContactList> getContactListsByContactListType(ContactListType contactListType) {
        return getContactListsByContactListType(contactListType, EntityPermission.READ_ONLY);
    }

    public List<ContactList> getContactListsByContactListTypeForUpdate(ContactListType contactListType) {
        return getContactListsByContactListType(contactListType, EntityPermission.READ_WRITE);
    }

    private List<ContactList> getContactListsByContactListFrequency(ContactListFrequency contactListFrequency, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactLists.fields())
                .from(ContactLists)
                .join(ContactListDetails).onKey(CONTACT_LISTS_ACTIVE_DETAIL_FK)
                .where(ContactListDetails.CONTACT_LIST_FREQUENCY.eq(contactListFrequency.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ContactListDetails.SORT_ORDER, ContactListDetails.CONTACT_LIST_NAME),
                    ContactListFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactListFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ContactList> getContactListsByContactListFrequency(ContactListFrequency contactListFrequency) {
        return getContactListsByContactListFrequency(contactListFrequency, EntityPermission.READ_ONLY);
    }

    public List<ContactList> getContactListsByContactListFrequencyForUpdate(ContactListFrequency contactListFrequency) {
        return getContactListsByContactListFrequency(contactListFrequency, EntityPermission.READ_WRITE);
    }

    private List<ContactList> getContactListsByWorkflowEntrance(WorkflowEntrance workflowEntrance, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactLists.fields())
                .from(ContactLists)
                .join(ContactListDetails).onKey(CONTACT_LISTS_ACTIVE_DETAIL_FK)
                .where(ContactListDetails.DEFAULT_PARTY_CONTACT_LIST_STATUS.eq(workflowEntrance.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ContactListDetails.SORT_ORDER, ContactListDetails.CONTACT_LIST_NAME),
                    ContactListFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactListFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ContactList> getContactListsByWorkflowEntrance(WorkflowEntrance workflowEntrance) {
        return getContactListsByWorkflowEntrance(workflowEntrance, EntityPermission.READ_ONLY);
    }

    public List<ContactList> getContactListsByWorkflowEntranceForUpdate(WorkflowEntrance workflowEntrance) {
        return getContactListsByWorkflowEntrance(workflowEntrance, EntityPermission.READ_WRITE);
    }

    public ContactListChoicesBean getContactListChoices(String defaultContactListChoice, Language language, boolean allowNullChoice) {
        var contactLists = getContactLists();
        var size = contactLists.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultContactListChoice == null) {
                defaultValue = "";
            }
        }

        for(var contactList : contactLists) {
            var contactListDetail = contactList.getLastDetail();

            var label = getBestContactListDescription(contactList, language);
            var value = contactListDetail.getContactListName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultContactListChoice != null && defaultContactListChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && contactListDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ContactListChoicesBean(labels, values, defaultValue);
    }

    public ContactListTransfer getContactListTransfer(UserVisit userVisit, ContactList contactList) {
        return contactListTransferCache.getContactListTransfer(userVisit, contactList);
    }

    public List<ContactListTransfer> getContactListTransfers(UserVisit userVisit, Collection<ContactList> contactLists) {
        List<ContactListTransfer> contactListTransfers = new ArrayList<>(contactLists.size());

        contactLists.forEach((contactList) ->
                contactListTransfers.add(contactListTransferCache.getContactListTransfer(userVisit, contactList))
        );

        return contactListTransfers;
    }

    public List<ContactListTransfer> getContactListTransfers(UserVisit userVisit) {
        return getContactListTransfers(userVisit, getContactLists());
    }

    private void updateContactListFromValue(ContactListDetailValue contactListDetailValue, boolean checkDefault, BasePK updatedBy) {
        var contactList = contactListFactory.getEntityFromPK(
                EntityPermission.READ_WRITE, contactListDetailValue.getContactListPK());
        var contactListDetail = contactList.getActiveDetailForUpdate();

        contactListDetail.setThruTime(session.getStartTime());
        contactListDetail.store();

        var contactListPK = contactListDetail.getContactListPK();
        var contactListName = contactListDetailValue.getContactListName();
        var contactListGroupPK = contactListDetailValue.getContactListGroupPK();
        var contactListTypePK = contactListDetailValue.getContactListTypePK();
        var contactListFrequencyPK = contactListDetailValue.getContactListFrequencyPK();
        var defaultPartyContactListStatusPK = contactListDetailValue.getDefaultPartyContactListStatusPK();
        var isDefault = contactListDetailValue.getIsDefault();
        var sortOrder = contactListDetailValue.getSortOrder();

        if(checkDefault) {
            var defaultContactList = getDefaultContactList();
            var defaultFound = defaultContactList != null && !defaultContactList.equals(contactList);

            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                var defaultContactListDetailValue = getDefaultContactListDetailValueForUpdate();

                defaultContactListDetailValue.setIsDefault(false);
                updateContactListFromValue(defaultContactListDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = true;
            }
        }

        contactListDetail = contactListDetailFactory.create(contactListPK, contactListName, contactListGroupPK, contactListTypePK,
                contactListFrequencyPK, defaultPartyContactListStatusPK, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        contactList.setActiveDetail(contactListDetail);
        contactList.setLastDetail(contactListDetail);
        contactList.store();

        sendEvent(contactListPK, EventTypes.MODIFY, null, null, updatedBy);
    }

    public void updateContactListFromValue(ContactListDetailValue contactListDetailValue, BasePK updatedBy) {
        updateContactListFromValue(contactListDetailValue, true, updatedBy);
    }

    public void deleteContactList(ContactList contactList, BasePK deletedBy) {
        
        letterControl.deleteLettersByContactList(contactList, deletedBy);
        deletePartyContactListsByContactList(contactList, deletedBy);
        deletePartyTypeContactListsByContactList(contactList, deletedBy);
        deleteCustomerTypeContactListsByContactList(contactList, deletedBy);
        deleteContactListContactMechanismPurposesByContactList(contactList, deletedBy);
        deleteContactListDescriptionsByContactList(contactList, deletedBy);

        var contactListDetail = contactList.getLastDetailForUpdate();
        contactListDetail.setThruTime(session.getStartTime());
        contactList.setActiveDetail(null);
        contactList.store();

        // Check for default, and pick one if necessary
        var defaultContactList = getDefaultContactList();
        if(defaultContactList == null) {
            var contactLists = getContactListsForUpdate();

            if(!contactLists.isEmpty()) {
                var iter = contactLists.iterator();
                if(iter.hasNext()) {
                    defaultContactList = iter.next();
                }
                var contactListDetailValue = Objects.requireNonNull(defaultContactList).getLastDetailForUpdate().getContactListDetailValue().clone();

                contactListDetailValue.setIsDefault(true);
                updateContactListFromValue(contactListDetailValue, false, deletedBy);
            }
        }

        sendEvent(contactList.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteContactLists(List<ContactList> contactLists, BasePK deletedBy) {
        contactLists.forEach((contactList) -> 
                deleteContactList(contactList, deletedBy)
        );
    }

    public void deleteContactListsByContactListGroup(ContactListGroup contactListGroup, BasePK deletedBy) {
        deleteContactLists(getContactListsByContactListGroupForUpdate(contactListGroup), deletedBy);
    }

    public void deleteContactListsByContactListType(ContactListType contactListType, BasePK deletedBy) {
        deleteContactLists(getContactListsByContactListTypeForUpdate(contactListType), deletedBy);
    }

    public void deleteContactListsByContactListFrequency(ContactListFrequency contactListFrequency, BasePK deletedBy) {
        deleteContactLists(getContactListsByContactListFrequencyForUpdate(contactListFrequency), deletedBy);
    }

    public void deleteContactListsByWorkflowEntrance(WorkflowEntrance workflowEntrance, BasePK deletedBy) {
        deleteContactLists(getContactListsByWorkflowEntranceForUpdate(workflowEntrance), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Contact List Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ContactListDescriptionFactory contactListDescriptionFactory;

    @Inject
    ContactListDescriptionTransferCache contactListDescriptionTransferCache;

    public ContactListDescription createContactListDescription(ContactList contactList, Language language, String description,
            BasePK createdBy) {
        var contactListDescription = contactListDescriptionFactory.create(contactList,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(contactList.getPrimaryKey(), EventTypes.MODIFY, contactListDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return contactListDescription;
    }

    private ContactListDescription getContactListDescription(ContactList contactList, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactListDescriptions.fields())
                .from(ContactListDescriptions)
                .where(ContactListDescriptions.CONTACT_LIST.eq(contactList.getPrimaryKey()),
                        ContactListDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ContactListDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactListDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public ContactListDescription getContactListDescription(ContactList contactList, Language language) {
        return getContactListDescription(contactList, language, EntityPermission.READ_ONLY);
    }

    public ContactListDescription getContactListDescriptionForUpdate(ContactList contactList, Language language) {
        return getContactListDescription(contactList, language, EntityPermission.READ_WRITE);
    }

    public ContactListDescriptionValue getContactListDescriptionValue(ContactListDescription contactListDescription) {
        return contactListDescription == null? null: contactListDescription.getContactListDescriptionValue().clone();
    }

    public ContactListDescriptionValue getContactListDescriptionValueForUpdate(ContactList contactList, Language language) {
        return getContactListDescriptionValue(getContactListDescriptionForUpdate(contactList, language));
    }

    private List<ContactListDescription> getContactListDescriptionsByContactList(ContactList contactList, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContactListDescriptions.fields())
                    .from(ContactListDescriptions)
                    .join(Languages).onKey(CONTACT_LIST_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ContactListDescriptions.CONTACT_LIST.eq(contactList.getPrimaryKey()),
                            ContactListDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ContactListDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContactListDescriptions.fields())
                    .from(ContactListDescriptions)
                    .where(ContactListDescriptions.CONTACT_LIST.eq(contactList.getPrimaryKey()),
                            ContactListDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return contactListDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ContactListDescription> getContactListDescriptionsByContactList(ContactList contactList) {
        return getContactListDescriptionsByContactList(contactList, EntityPermission.READ_ONLY);
    }

    public List<ContactListDescription> getContactListDescriptionsByContactListForUpdate(ContactList contactList) {
        return getContactListDescriptionsByContactList(contactList, EntityPermission.READ_WRITE);
    }

    public String getBestContactListDescription(ContactList contactList, Language language) {
        String description;
        var contactListDescription = getContactListDescription(contactList, language);

        if(contactListDescription == null && !language.getIsDefault()) {
            contactListDescription = getContactListDescription(contactList, partyControl.getDefaultLanguage());
        }

        if(contactListDescription == null) {
            description = contactList.getLastDetail().getContactListName();
        } else {
            description = contactListDescription.getDescription();
        }

        return description;
    }

    public ContactListDescriptionTransfer getContactListDescriptionTransfer(UserVisit userVisit, ContactListDescription contactListDescription) {
        return contactListDescriptionTransferCache.getContactListDescriptionTransfer(userVisit, contactListDescription);
    }

    public List<ContactListDescriptionTransfer> getContactListDescriptionTransfersByContactList(UserVisit userVisit, ContactList contactList) {
        var contactListDescriptions = getContactListDescriptionsByContactList(contactList);
        List<ContactListDescriptionTransfer> contactListDescriptionTransfers = new ArrayList<>(contactListDescriptions.size());

        contactListDescriptions.forEach((contactListDescription) -> {
            contactListDescriptionTransfers.add(contactListDescriptionTransferCache.getContactListDescriptionTransfer(userVisit, contactListDescription));
        });

        return contactListDescriptionTransfers;
    }

    public void updateContactListDescriptionFromValue(ContactListDescriptionValue contactListDescriptionValue, BasePK updatedBy) {
        if(contactListDescriptionValue.hasBeenModified()) {
            var contactListDescription = contactListDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     contactListDescriptionValue.getPrimaryKey());

            contactListDescription.setThruTime(session.getStartTime());
            contactListDescription.store();

            var contactList = contactListDescription.getContactList();
            var language = contactListDescription.getLanguage();
            var description = contactListDescriptionValue.getDescription();

            contactListDescription = contactListDescriptionFactory.create(contactList, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(contactList.getPrimaryKey(), EventTypes.MODIFY, contactListDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteContactListDescription(ContactListDescription contactListDescription, BasePK deletedBy) {
        contactListDescription.setThruTime(session.getStartTime());

        sendEvent(contactListDescription.getContactListPK(), EventTypes.MODIFY, contactListDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteContactListDescriptionsByContactList(ContactList contactList, BasePK deletedBy) {
        var contactListDescriptions = getContactListDescriptionsByContactListForUpdate(contactList);

        contactListDescriptions.forEach((contactListDescription) -> 
                deleteContactListDescription(contactListDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Party Contact Lists
    // --------------------------------------------------------------------------------

    @Inject
    protected PartyContactListDetailFactory partyContactListDetailFactory;

    @Inject
    protected PartyContactListFactory partyContactListFactory;

    @Inject
    PartyContactListTransferCache partyContactListTransferCache;

    public PartyContactList createPartyContactList(Party party, ContactList contactList, ContactListContactMechanismPurpose preferredContactListContactMechanismPurpose,
            BasePK createdBy) {
        var partyContactList = partyContactListFactory.create();
        var partyContactListDetail = partyContactListDetailFactory.create( partyContactList, party, contactList,
                preferredContactListContactMechanismPurpose, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        partyContactList = partyContactListFactory.getEntityFromPK(EntityPermission.READ_WRITE, partyContactList.getPrimaryKey());
        partyContactList.setActiveDetail(partyContactListDetail);
        partyContactList.setLastDetail(partyContactListDetail);
        partyContactList.store();
        
        sendEvent(partyContactList.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return partyContactList;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PartyContactList */
    public PartyContactList getPartyContactListByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new PartyContactListPK(entityInstance.getEntityUniqueId());

        return partyContactListFactory.getEntityFromPK(entityPermission, pk);
    }

    public PartyContactList getPartyContactListByEntityInstance(EntityInstance entityInstance) {
        return getPartyContactListByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PartyContactList getPartyContactListByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getPartyContactListByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countPartyContactListsByParty(final Party party) {
        return session.getDslContext()
                .selectCount()
                .from(PartyContactLists)
                .join(PartyContactListDetails).onKey(PARTY_CONTACT_LISTS_ACTIVE_DETAIL_FK)
                .where(PartyContactListDetails.PARTY.eq(party.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyContactListsByContactList(final ContactList contactList) {
        return session.getDslContext()
                .selectCount()
                .from(PartyContactLists)
                .join(PartyContactListDetails).onKey(PARTY_CONTACT_LISTS_ACTIVE_DETAIL_FK)
                .where(PartyContactListDetails.CONTACT_LIST.eq(contactList.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyContactListsByPreferredContactListContactMechanismPurpose(final ContactListContactMechanismPurpose preferredContactListContactMechanismPurpose) {
        return session.getDslContext()
                .selectCount()
                .from(PartyContactLists)
                .join(PartyContactListDetails).onKey(PARTY_CONTACT_LISTS_ACTIVE_DETAIL_FK)
                .where(PartyContactListDetails.PREFERRED_CONTACT_LIST_CONTACT_MECHANISM_PURPOSE.eq(preferredContactListContactMechanismPurpose.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public PartyContactList getPartyContactList(Party party, ContactList contactList, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyContactLists.fields())
                .from(PartyContactLists)
                .join(PartyContactListDetails).onKey(PARTY_CONTACT_LISTS_ACTIVE_DETAIL_FK)
                .where(PartyContactListDetails.PARTY.eq(party.getPrimaryKey()),
                        PartyContactListDetails.CONTACT_LIST.eq(contactList.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyContactListFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public PartyContactList getPartyContactList(Party party, ContactList contactList) {
        return getPartyContactList(party, contactList, EntityPermission.READ_ONLY);
    }
    
    public PartyContactList getPartyContactListForUpdate(Party party, ContactList contactList) {
        return getPartyContactList(party, contactList, EntityPermission.READ_WRITE);
    }
    
    public PartyContactListDetailValue getPartyContactListDetailValueForUpdate(PartyContactList partyContactList) {
        return partyContactList == null? null: partyContactList.getLastDetailForUpdate().getPartyContactListDetailValue().clone();
    }
    
    public PartyContactListDetailValue getPartyContactListDetailValueForUpdate(Party party, ContactList contactList) {
        return getPartyContactListDetailValueForUpdate(getPartyContactListForUpdate(party, contactList));
    }
    
    private List<PartyContactList> getPartyContactListsByParty(Party party, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyContactLists.fields())
                    .from(PartyContactLists)
                    .join(PartyContactListDetails).onKey(PARTY_CONTACT_LISTS_ACTIVE_DETAIL_FK)
                    .join(ContactLists).onKey(PARTY_CONTACT_LIST_DETAILS_CONTACT_LIST_FK)
                    .join(ContactListDetails).onKey(CONTACT_LISTS_ACTIVE_DETAIL_FK)
                    .where(PartyContactListDetails.PARTY.eq(party.getPrimaryKey()))
                    .orderBy(ContactListDetails.SORT_ORDER, ContactListDetails.CONTACT_LIST_NAME),
                    PartyContactListFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyContactLists.fields())
                    .from(PartyContactLists)
                    .join(PartyContactListDetails).onKey(PARTY_CONTACT_LISTS_ACTIVE_DETAIL_FK)
                    .where(PartyContactListDetails.PARTY.eq(party.getPrimaryKey()))
                    .forUpdate();
        };

        return partyContactListFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyContactList> getPartyContactListsByParty(Party party) {
        return getPartyContactListsByParty(party, EntityPermission.READ_ONLY);
    }
    
    public List<PartyContactList> getPartyContactListsByPartyForUpdate(Party party) {
        return getPartyContactListsByParty(party, EntityPermission.READ_WRITE);
    }
    
    private List<PartyContactList> getPartyContactListsByContactList(ContactList contactList, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyContactLists.fields())
                    .from(PartyContactLists)
                    .join(PartyContactListDetails).onKey(PARTY_CONTACT_LISTS_ACTIVE_DETAIL_FK)
                    .join(Parties).onKey(PARTY_CONTACT_LIST_DETAILS_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .join(PartyTypes).onKey(PARTY_DETAILS_PARTY_TYPE_FK)
                    .where(PartyContactListDetails.CONTACT_LIST.eq(contactList.getPrimaryKey()))
                    .orderBy(PartyDetails.PARTY_NAME, PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME),
                    PartyContactListFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyContactLists.fields())
                    .from(PartyContactLists)
                    .join(PartyContactListDetails).onKey(PARTY_CONTACT_LISTS_ACTIVE_DETAIL_FK)
                    .where(PartyContactListDetails.CONTACT_LIST.eq(contactList.getPrimaryKey()))
                    .forUpdate();
        };

        return partyContactListFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyContactList> getPartyContactListsByContactList(ContactList contactList) {
        return getPartyContactListsByContactList(contactList, EntityPermission.READ_ONLY);
    }
    
    public List<PartyContactList> getPartyContactListsByContactListForUpdate(ContactList contactList) {
        return getPartyContactListsByContactList(contactList, EntityPermission.READ_WRITE);
    }
    
    private List<PartyContactList> getPartyContactListsByPreferredContactListContactMechanismPurpose(ContactListContactMechanismPurpose preferredContactListContactMechanismPurpose,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyContactLists.fields())
                .from(PartyContactLists)
                .join(PartyContactListDetails).onKey(PARTY_CONTACT_LISTS_ACTIVE_DETAIL_FK)
                .where(PartyContactListDetails.PREFERRED_CONTACT_LIST_CONTACT_MECHANISM_PURPOSE.eq(preferredContactListContactMechanismPurpose.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyContactLists.fields())
                    .from(PartyContactLists)
                    .join(PartyContactListDetails).onKey(PARTY_CONTACT_LISTS_ACTIVE_DETAIL_FK)
                    .join(Parties).onKey(PARTY_CONTACT_LIST_DETAILS_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .join(ContactLists).onKey(PARTY_CONTACT_LIST_DETAILS_CONTACT_LIST_FK)
                    .join(ContactListDetails).onKey(CONTACT_LISTS_LAST_DETAIL_FK)
                    .where(PartyContactListDetails.PREFERRED_CONTACT_LIST_CONTACT_MECHANISM_PURPOSE.eq(preferredContactListContactMechanismPurpose.getPrimaryKey()))
                    .orderBy(PartyDetails.PARTY_NAME, ContactListDetails.SORT_ORDER, ContactListDetails.CONTACT_LIST_NAME),
                    PartyContactListFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyContactListFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyContactList> getPartyContactListsByPreferredContactListContactMechanismPurpose(ContactListContactMechanismPurpose preferredContactListContactMechanismPurpose) {
        return getPartyContactListsByPreferredContactListContactMechanismPurpose(preferredContactListContactMechanismPurpose, EntityPermission.READ_ONLY);
    }
    
    public List<PartyContactList> getPartyContactListsByPreferredContactListContactMechanismPurposeForUpdate(ContactListContactMechanismPurpose preferredContactListContactMechanismPurpose) {
        return getPartyContactListsByPreferredContactListContactMechanismPurpose(preferredContactListContactMechanismPurpose, EntityPermission.READ_WRITE);
    }
    
    public List<PartyContactListTransfer> getPartyContactListTransfers(UserVisit userVisit, Collection<PartyContactList> partyContactLists) {
        List<PartyContactListTransfer> partyContactListTransfers = new ArrayList<>(partyContactLists.size());
        
        partyContactLists.forEach((partyContactList) ->
                partyContactListTransfers.add(partyContactListTransferCache.getPartyContactListTransfer(userVisit, partyContactList))
        );
        
        return partyContactListTransfers;
    }
    
    public List<PartyContactListTransfer> getPartyContactListTransfersByParty(UserVisit userVisit, Party party) {
        return getPartyContactListTransfers(userVisit, getPartyContactListsByParty(party));
    }
    
    public List<PartyContactListTransfer> getPartyContactListTransfersByContactList(UserVisit userVisit, ContactList contactList) {
        return getPartyContactListTransfers(userVisit, getPartyContactListsByContactList(contactList));
    }
    
    public PartyContactListTransfer getPartyContactListTransfer(UserVisit userVisit, PartyContactList partyContactList) {
        return partyContactListTransferCache.getPartyContactListTransfer(userVisit, partyContactList);
    }
    
    public PartyContactListStatusChoicesBean getPartyContactListStatusChoices(String defaultPartyContactListStatusChoice, Language language,
            boolean allowNullChoice, PartyContactList partyContactList, PartyPK partyPK) {
        var partyContactListStatusChoicesBean = new PartyContactListStatusChoicesBean();

        if(partyContactList == null) {
            workflowControl.getWorkflowEntranceChoices(partyContactListStatusChoicesBean, defaultPartyContactListStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(PartyContactListStatusConstants.Workflow_PARTY_CONTACT_LIST_STATUS), partyPK);
        } else {
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(partyContactList.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(PartyContactListStatusConstants.Workflow_PARTY_CONTACT_LIST_STATUS,
                    entityInstance);

            workflowControl.getWorkflowDestinationChoices(partyContactListStatusChoicesBean, defaultPartyContactListStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }

        return partyContactListStatusChoicesBean;
    }

    public void setPartyContactListStatus(ExecutionErrorAccumulator eea, PartyContactList partyContactList, String partyContactListStatusChoice,
            PartyPK modifiedBy) {
        var entityInstance = getEntityInstanceByBaseEntity(partyContactList);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(PartyContactListStatusConstants.Workflow_PARTY_CONTACT_LIST_STATUS,
                entityInstance);
        var workflowDestination = partyContactListStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), partyContactListStatusChoice);

        if(workflowDestination != null || partyContactListStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownPartyContactListStatusChoice.name(), partyContactListStatusChoice);
        }
    }

    public void updatePartyContactListFromValue(PartyContactListDetailValue partyContactListDetailValue, BasePK updatedBy) {
        if(partyContactListDetailValue.hasBeenModified()) {
            var partyContactList = partyContactListFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     partyContactListDetailValue.getPartyContactListPK());
            var partyContactListDetail = partyContactList.getActiveDetailForUpdate();
            
            partyContactListDetail.setThruTime(session.getStartTime());
            partyContactListDetail.store();

            var partyContactListPK = partyContactListDetail.getPartyContactListPK();
            var partyPK = partyContactListDetail.getPartyPK(); // Not updated
            var contactListPK = partyContactListDetail.getContactListPK(); // Not updated
            var preferredContactListContactMechanismPurposePK = partyContactListDetailValue.getPreferredContactListContactMechanismPurposePK();
            
            partyContactListDetail = partyContactListDetailFactory.create(partyContactListPK, partyPK, contactListPK,
                    preferredContactListContactMechanismPurposePK, session.getStartTime(), Session.MAX_TIME);
            
            partyContactList.setActiveDetail(partyContactListDetail);
            partyContactList.setLastDetail(partyContactListDetail);
            
            sendEvent(partyContactList.getPrimaryKey(), EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void clearContactListContactMechanismPurposeFromPartyContactLists(ContactListContactMechanismPurpose preferredContactListContactMechanismPurpose, BasePK clearedBy) {
        getPartyContactListsByPreferredContactListContactMechanismPurposeForUpdate(preferredContactListContactMechanismPurpose).stream().map((partyContactList) -> getPartyContactListDetailValueForUpdate(partyContactList)).map((partyContactListDetailValue) -> {
            partyContactListDetailValue.setPreferredContactListContactMechanismPurposePK(null);
            return partyContactListDetailValue;
        }).forEach((partyContactListDetailValue) -> {
            updatePartyContactListFromValue(partyContactListDetailValue, clearedBy);
        });
    }
    
    public void deletePartyContactList(PartyContactList partyContactList, BasePK deletedBy) {
        var partyContactListDetail = partyContactList.getLastDetailForUpdate();
        partyContactListDetail.setThruTime(session.getStartTime());
        partyContactList.setActiveDetail(null);
        partyContactList.store();
        
        sendEvent(partyContactList.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deletePartyContactListsByParty(Party party, BasePK deletedBy) {
        var partyContactLists = getPartyContactListsByPartyForUpdate(party);
        
        partyContactLists.forEach((partyContactList) -> 
                deletePartyContactList(partyContactList, deletedBy)
        );
    }
    
    public void deletePartyContactListsByContactList(ContactList contactList, BasePK deletedBy) {
        var partyContactLists = getPartyContactListsByContactListForUpdate(contactList);
        
        partyContactLists.forEach((partyContactList) -> 
                deletePartyContactList(partyContactList, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Party Type Contact List Groups
    // --------------------------------------------------------------------------------

    @Inject
    protected PartyTypeContactListGroupFactory partyTypeContactListGroupFactory;

    @Inject
    PartyTypeContactListGroupTransferCache partyTypeContactListGroupTransferCache;

    public PartyTypeContactListGroup createPartyTypeContactListGroup(PartyType partyType, ContactListGroup contactListGroup,
            Boolean addWhenCreated, BasePK createdBy) {
        var partyTypeContactListGroup = partyTypeContactListGroupFactory.create(
                partyType, contactListGroup, addWhenCreated, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(contactListGroup.getPrimaryKey(), EventTypes.MODIFY, partyTypeContactListGroup.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyTypeContactListGroup;
    }

    public long countPartyTypeContactListGroupsByPartyType(final PartyType partyType) {
        return session.getDslContext()
                .selectCount()
                .from(PartyTypeContactListGroups)
                .where(PartyTypeContactListGroups.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                        PartyTypeContactListGroups.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyTypeContactListGroupsByContactListGroup(final ContactListGroup contactListGroup) {
        return session.getDslContext()
                .selectCount()
                .from(PartyTypeContactListGroups)
                .where(PartyTypeContactListGroups.CONTACT_LIST_GROUP.eq(contactListGroup.getPrimaryKey()),
                        PartyTypeContactListGroups.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PartyTypeContactListGroup getPartyTypeContactListGroup(PartyType partyType, ContactListGroup contactListGroup, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyTypeContactListGroups.fields())
                .from(PartyTypeContactListGroups)
                .where(PartyTypeContactListGroups.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                        PartyTypeContactListGroups.CONTACT_LIST_GROUP.eq(contactListGroup.getPrimaryKey()),
                        PartyTypeContactListGroups.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyTypeContactListGroupFactory.getEntityFromQuery(entityPermission, query);
    }

    public PartyTypeContactListGroup getPartyTypeContactListGroup(PartyType partyType, ContactListGroup contactListGroup) {
        return getPartyTypeContactListGroup(partyType, contactListGroup, EntityPermission.READ_ONLY);
    }
    
    public PartyTypeContactListGroup getPartyTypeContactListGroupForUpdate(PartyType partyType, ContactListGroup contactListGroup) {
        return getPartyTypeContactListGroup(partyType, contactListGroup, EntityPermission.READ_WRITE);
    }
    
    public PartyTypeContactListGroupValue getPartyTypeContactListGroupValue(PartyTypeContactListGroup partyTypeContactListGroup) {
        return partyTypeContactListGroup == null? null: partyTypeContactListGroup.getPartyTypeContactListGroupValue().clone();
    }
    
    public PartyTypeContactListGroupValue getPartyTypeContactListGroupValueForUpdate(PartyType partyType, ContactListGroup contactListGroup) {
        return getPartyTypeContactListGroupValue(getPartyTypeContactListGroupForUpdate(partyType, contactListGroup));
    }
    
    private List<PartyTypeContactListGroup> getPartyTypeContactListGroupsByPartyType(PartyType partyType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyTypeContactListGroups.fields())
                    .from(PartyTypeContactListGroups)
                    .join(ContactListGroups).onKey(PARTY_TYPE_CONTACT_LIST_GROUPS_CONTACT_LIST_GROUP_FK)
                    .join(ContactListGroupDetails).onKey(CONTACT_LIST_GROUPS_LAST_DETAIL_FK)
                    .where(PartyTypeContactListGroups.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                            PartyTypeContactListGroups.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ContactListGroupDetails.SORT_ORDER, ContactListGroupDetails.CONTACT_LIST_GROUP_NAME),
                    PartyTypeContactListGroupFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyTypeContactListGroups.fields())
                    .from(PartyTypeContactListGroups)
                    .where(PartyTypeContactListGroups.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                            PartyTypeContactListGroups.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyTypeContactListGroupFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyTypeContactListGroup> getPartyTypeContactListGroupsByPartyType(PartyType partyType) {
        return getPartyTypeContactListGroupsByPartyType(partyType, EntityPermission.READ_ONLY);
    }
    
    public List<PartyTypeContactListGroup> getPartyTypeContactListGroupsByPartyTypeForUpdate(PartyType partyType) {
        return getPartyTypeContactListGroupsByPartyType(partyType, EntityPermission.READ_WRITE);
    }
    
    private List<PartyTypeContactListGroup> getPartyTypeContactListGroupsByContactListGroup(ContactListGroup contactListGroup, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyTypeContactListGroups.fields())
                    .from(PartyTypeContactListGroups)
                    .join(PartyTypes).onKey(PARTY_TYPE_CONTACT_LIST_GROUPS_PARTY_TYPE_FK)
                    .where(PartyTypeContactListGroups.CONTACT_LIST_GROUP.eq(contactListGroup.getPrimaryKey()),
                            PartyTypeContactListGroups.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME),
                    PartyTypeContactListGroupFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyTypeContactListGroups.fields())
                    .from(PartyTypeContactListGroups)
                    .where(PartyTypeContactListGroups.CONTACT_LIST_GROUP.eq(contactListGroup.getPrimaryKey()),
                            PartyTypeContactListGroups.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyTypeContactListGroupFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyTypeContactListGroup> getPartyTypeContactListGroupsByContactListGroup(ContactListGroup contactListGroup) {
        return getPartyTypeContactListGroupsByContactListGroup(contactListGroup, EntityPermission.READ_ONLY);
    }
    
    public List<PartyTypeContactListGroup> getPartyTypeContactListGroupsByContactListGroupForUpdate(ContactListGroup contactListGroup) {
        return getPartyTypeContactListGroupsByContactListGroup(contactListGroup, EntityPermission.READ_WRITE);
    }
    
    public List<PartyTypeContactListGroupTransfer> getPartyTypeContactListGroupTransfers(UserVisit userVisit, Collection<PartyTypeContactListGroup> partyTypeContactListGroups) {
        List<PartyTypeContactListGroupTransfer> partyTypeContactListGroupTransfers = new ArrayList<>(partyTypeContactListGroups.size());
        
        partyTypeContactListGroups.forEach((partyTypeContactListGroup) ->
                partyTypeContactListGroupTransfers.add(partyTypeContactListGroupTransferCache.getPartyTypeContactListGroupTransfer(userVisit, partyTypeContactListGroup))
        );
        
        return partyTypeContactListGroupTransfers;
    }
    
    public List<PartyTypeContactListGroupTransfer> getPartyTypeContactListGroupTransfersByPartyType(UserVisit userVisit, PartyType partyType) {
        return getPartyTypeContactListGroupTransfers(userVisit, getPartyTypeContactListGroupsByPartyType(partyType));
    }
    
    public List<PartyTypeContactListGroupTransfer> getPartyTypeContactListGroupTransfersByContactListGroup(UserVisit userVisit, ContactListGroup contactListGroup) {
        return getPartyTypeContactListGroupTransfers(userVisit, getPartyTypeContactListGroupsByContactListGroup(contactListGroup));
    }
    
    public PartyTypeContactListGroupTransfer getPartyTypeContactListGroupTransfer(UserVisit userVisit, PartyTypeContactListGroup partyTypeContactListGroup) {
        return partyTypeContactListGroupTransferCache.getPartyTypeContactListGroupTransfer(userVisit, partyTypeContactListGroup);
    }
    
    public void updatePartyTypeContactListGroupFromValue(PartyTypeContactListGroupValue partyTypeContactListGroupValue, BasePK updatedBy) {
        if(partyTypeContactListGroupValue.hasBeenModified()) {
            var partyTypeContactListGroup = partyTypeContactListGroupFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     partyTypeContactListGroupValue.getPrimaryKey());
            
            partyTypeContactListGroup.setThruTime(session.getStartTime());
            partyTypeContactListGroup.store();

            var partyTypePK = partyTypeContactListGroup.getPartyType().getPrimaryKey(); // Not Updated
            var contactListGroupPK = partyTypeContactListGroup.getContactListGroupPK(); // Not Updated
            var addWhenCreated = partyTypeContactListGroupValue.getAddWhenCreated();
            
            partyTypeContactListGroup = partyTypeContactListGroupFactory.create(partyTypePK, contactListGroupPK, addWhenCreated,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(contactListGroupPK, EventTypes.MODIFY, partyTypeContactListGroup.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePartyTypeContactListGroup(PartyTypeContactListGroup partyTypeContactListGroup, BasePK deletedBy) {
        partyTypeContactListGroup.setThruTime(session.getStartTime());
        partyTypeContactListGroup.store();
        
        sendEvent(partyTypeContactListGroup.getContactListGroupPK(), EventTypes.MODIFY, partyTypeContactListGroup.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deletePartyTypeContactListGroups(List<PartyTypeContactListGroup> partyTypeContactListGroups, BasePK deletedBy) {
        partyTypeContactListGroups.forEach((partyTypeContactListGroup) -> 
                deletePartyTypeContactListGroup(partyTypeContactListGroup, deletedBy)
        );
    }
    
    public void deletePartyTypeContactListGroupsByPartyType(PartyType partyType, BasePK deletedBy) {
        deletePartyTypeContactListGroups(getPartyTypeContactListGroupsByPartyTypeForUpdate(partyType), deletedBy);
    }
    
    public void deletePartyTypeContactListGroupsByContactListGroup(ContactListGroup contactListGroup, BasePK deletedBy) {
        deletePartyTypeContactListGroups(getPartyTypeContactListGroupsByContactListGroupForUpdate(contactListGroup), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Type Contact Lists
    // --------------------------------------------------------------------------------

    @Inject
    protected PartyTypeContactListFactory partyTypeContactListFactory;

    @Inject
    PartyTypeContactListTransferCache partyTypeContactListTransferCache;

    public PartyTypeContactList createPartyTypeContactList(PartyType partyType, ContactList contactList, Boolean addWhenCreated, BasePK createdBy) {
        var partyTypeContactList = partyTypeContactListFactory.create( partyType, contactList, addWhenCreated,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(contactList.getPrimaryKey(), EventTypes.MODIFY, partyTypeContactList.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyTypeContactList;
    }

    public long countPartyTypeContactListsByPartyType(final PartyType partyType) {
        return session.getDslContext()
                .selectCount()
                .from(PartyTypeContactLists)
                .where(PartyTypeContactLists.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                        PartyTypeContactLists.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyTypeContactListsByContactList(final ContactList contactList) {
        return session.getDslContext()
                .selectCount()
                .from(PartyTypeContactLists)
                .where(PartyTypeContactLists.CONTACT_LIST.eq(contactList.getPrimaryKey()),
                        PartyTypeContactLists.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PartyTypeContactList getPartyTypeContactList(PartyType partyType, ContactList contactList, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyTypeContactLists.fields())
                .from(PartyTypeContactLists)
                .where(PartyTypeContactLists.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                        PartyTypeContactLists.CONTACT_LIST.eq(contactList.getPrimaryKey()),
                        PartyTypeContactLists.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyTypeContactListFactory.getEntityFromQuery(entityPermission, query);
    }

    public PartyTypeContactList getPartyTypeContactList(PartyType partyType, ContactList contactList) {
        return getPartyTypeContactList(partyType, contactList, EntityPermission.READ_ONLY);
    }
    
    public PartyTypeContactList getPartyTypeContactListForUpdate(PartyType partyType, ContactList contactList) {
        return getPartyTypeContactList(partyType, contactList, EntityPermission.READ_WRITE);
    }
    
    public PartyTypeContactListValue getPartyTypeContactListValue(PartyTypeContactList partyTypeContactList) {
        return partyTypeContactList == null? null: partyTypeContactList.getPartyTypeContactListValue().clone();
    }
    
    public PartyTypeContactListValue getPartyTypeContactListValueForUpdate(PartyType partyType, ContactList contactList) {
        return getPartyTypeContactListValue(getPartyTypeContactListForUpdate(partyType, contactList));
    }
    
    private List<PartyTypeContactList> getPartyTypeContactListsByPartyType(PartyType partyType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyTypeContactLists.fields())
                    .from(PartyTypeContactLists)
                    .join(ContactLists).onKey(PARTY_TYPE_CONTACT_LISTS_CONTACT_LIST_FK)
                    .join(ContactListDetails).onKey(CONTACT_LISTS_LAST_DETAIL_FK)
                    .where(PartyTypeContactLists.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                            PartyTypeContactLists.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ContactListDetails.SORT_ORDER, ContactListDetails.CONTACT_LIST_NAME),
                    PartyTypeContactListFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyTypeContactLists.fields())
                    .from(PartyTypeContactLists)
                    .where(PartyTypeContactLists.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                            PartyTypeContactLists.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyTypeContactListFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyTypeContactList> getPartyTypeContactListsByPartyType(PartyType partyType) {
        return getPartyTypeContactListsByPartyType(partyType, EntityPermission.READ_ONLY);
    }
    
    public List<PartyTypeContactList> getPartyTypeContactListsByPartyTypeForUpdate(PartyType partyType) {
        return getPartyTypeContactListsByPartyType(partyType, EntityPermission.READ_WRITE);
    }
    
    private List<PartyTypeContactList> getPartyTypeContactListsByContactList(ContactList contactList, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyTypeContactLists.fields())
                    .from(PartyTypeContactLists)
                    .join(PartyTypes).onKey(PARTY_TYPE_CONTACT_LISTS_PARTY_TYPE_FK)
                    .where(PartyTypeContactLists.CONTACT_LIST.eq(contactList.getPrimaryKey()),
                            PartyTypeContactLists.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME),
                    PartyTypeContactListFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyTypeContactLists.fields())
                    .from(PartyTypeContactLists)
                    .where(PartyTypeContactLists.CONTACT_LIST.eq(contactList.getPrimaryKey()),
                            PartyTypeContactLists.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyTypeContactListFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyTypeContactList> getPartyTypeContactListsByContactList(ContactList contactList) {
        return getPartyTypeContactListsByContactList(contactList, EntityPermission.READ_ONLY);
    }
    
    public List<PartyTypeContactList> getPartyTypeContactListsByContactListForUpdate(ContactList contactList) {
        return getPartyTypeContactListsByContactList(contactList, EntityPermission.READ_WRITE);
    }
    
    public List<PartyTypeContactListTransfer> getPartyTypeContactListTransfers(UserVisit userVisit, Collection<PartyTypeContactList> partyTypeContactLists) {
        List<PartyTypeContactListTransfer> partyTypeContactListTransfers = new ArrayList<>(partyTypeContactLists.size());
        
        partyTypeContactLists.forEach((partyTypeContactList) ->
                partyTypeContactListTransfers.add(partyTypeContactListTransferCache.getPartyTypeContactListTransfer(userVisit, partyTypeContactList))
        );
        
        return partyTypeContactListTransfers;
    }
    
    public List<PartyTypeContactListTransfer> getPartyTypeContactListTransfersByPartyType(UserVisit userVisit, PartyType partyType) {
        return getPartyTypeContactListTransfers(userVisit, getPartyTypeContactListsByPartyType(partyType));
    }
    
    public List<PartyTypeContactListTransfer> getPartyTypeContactListTransfersByContactList(UserVisit userVisit, ContactList contactList) {
        return getPartyTypeContactListTransfers(userVisit, getPartyTypeContactListsByContactList(contactList));
    }
    
    public PartyTypeContactListTransfer getPartyTypeContactListTransfer(UserVisit userVisit, PartyTypeContactList partyTypeContactList) {
        return partyTypeContactListTransferCache.getPartyTypeContactListTransfer(userVisit, partyTypeContactList);
    }
    
    public void updatePartyTypeContactListFromValue(PartyTypeContactListValue partyTypeContactListValue, BasePK updatedBy) {
        if(partyTypeContactListValue.hasBeenModified()) {
            var partyTypeContactList = partyTypeContactListFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     partyTypeContactListValue.getPrimaryKey());
            
            partyTypeContactList.setThruTime(session.getStartTime());
            partyTypeContactList.store();

            var partyTypePK = partyTypeContactList.getPartyType().getPrimaryKey(); // Not Updated
            var contactListPK = partyTypeContactList.getContactListPK(); // Not Updated
            var addWhenCreated = partyTypeContactListValue.getAddWhenCreated();
            
            partyTypeContactList = partyTypeContactListFactory.create(partyTypePK, contactListPK, addWhenCreated, session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(contactListPK, EventTypes.MODIFY, partyTypeContactList.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePartyTypeContactList(PartyTypeContactList partyTypeContactList, BasePK deletedBy) {
        partyTypeContactList.setThruTime(session.getStartTime());
        partyTypeContactList.store();
        
        sendEvent(partyTypeContactList.getContactListPK(), EventTypes.MODIFY, partyTypeContactList.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deletePartyTypeContactLists(List<PartyTypeContactList> partyTypeContactLists, BasePK deletedBy) {
        partyTypeContactLists.forEach((partyTypeContactList) -> 
                deletePartyTypeContactList(partyTypeContactList, deletedBy)
        );
    }
    
    public void deletePartyTypeContactListsByPartyType(PartyType partyType, BasePK deletedBy) {
        deletePartyTypeContactLists(getPartyTypeContactListsByPartyTypeForUpdate(partyType), deletedBy);
    }
    
    public void deletePartyTypeContactListsByContactList(ContactList contactList, BasePK deletedBy) {
        deletePartyTypeContactLists(getPartyTypeContactListsByContactListForUpdate(contactList), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Customer Type Contact List Groups
    // --------------------------------------------------------------------------------

    @Inject
    protected CustomerTypeContactListGroupFactory customerTypeContactListGroupFactory;

    @Inject
    CustomerTypeContactListGroupTransferCache customerTypeContactListGroupTransferCache;

    public CustomerTypeContactListGroup createCustomerTypeContactListGroup(CustomerType customerType, ContactListGroup contactListGroup,
            Boolean addWhenCreated, BasePK createdBy) {
        var customerTypeContactListGroup = customerTypeContactListGroupFactory.create(
                customerType, contactListGroup, addWhenCreated, session.getStartTime(), Session.MAX_TIME);

        sendEvent(contactListGroup.getPrimaryKey(), EventTypes.MODIFY, customerTypeContactListGroup.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return customerTypeContactListGroup;
    }

    public long countCustomerTypeContactListGroupsByCustomerType(final CustomerType customerType) {
        return session.getDslContext()
                .selectCount()
                .from(CustomerTypeContactListGroups)
                .where(CustomerTypeContactListGroups.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                        CustomerTypeContactListGroups.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCustomerTypeContactListGroupsByContactListGroup(final ContactListGroup contactListGroup) {
        return session.getDslContext()
                .selectCount()
                .from(CustomerTypeContactListGroups)
                .where(CustomerTypeContactListGroups.CONTACT_LIST_GROUP.eq(contactListGroup.getPrimaryKey()),
                        CustomerTypeContactListGroups.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private CustomerTypeContactListGroup getCustomerTypeContactListGroup(CustomerType customerType, ContactListGroup contactListGroup, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CustomerTypeContactListGroups.fields())
                .from(CustomerTypeContactListGroups)
                .where(CustomerTypeContactListGroups.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                        CustomerTypeContactListGroups.CONTACT_LIST_GROUP.eq(contactListGroup.getPrimaryKey()),
                        CustomerTypeContactListGroups.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return customerTypeContactListGroupFactory.getEntityFromQuery(entityPermission, query);
    }

    public CustomerTypeContactListGroup getCustomerTypeContactListGroup(CustomerType customerType, ContactListGroup contactListGroup) {
        return getCustomerTypeContactListGroup(customerType, contactListGroup, EntityPermission.READ_ONLY);
    }

    public CustomerTypeContactListGroup getCustomerTypeContactListGroupForUpdate(CustomerType customerType, ContactListGroup contactListGroup) {
        return getCustomerTypeContactListGroup(customerType, contactListGroup, EntityPermission.READ_WRITE);
    }

    public CustomerTypeContactListGroupValue getCustomerTypeContactListGroupValue(CustomerTypeContactListGroup customerTypeContactListGroup) {
        return customerTypeContactListGroup == null? null: customerTypeContactListGroup.getCustomerTypeContactListGroupValue().clone();
    }

    public CustomerTypeContactListGroupValue getCustomerTypeContactListGroupValueForUpdate(CustomerType customerType, ContactListGroup contactListGroup) {
        return getCustomerTypeContactListGroupValue(getCustomerTypeContactListGroupForUpdate(customerType, contactListGroup));
    }

    private List<CustomerTypeContactListGroup> getCustomerTypeContactListGroupsByCustomerType(CustomerType customerType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CustomerTypeContactListGroups.fields())
                    .from(CustomerTypeContactListGroups)
                    .join(ContactListGroups).onKey(CUSTOMER_TYPE_CONTACT_LIST_GROUPS_CONTACT_LIST_GROUP_FK)
                    .join(ContactListGroupDetails).onKey(CONTACT_LIST_GROUPS_LAST_DETAIL_FK)
                    .where(CustomerTypeContactListGroups.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                            CustomerTypeContactListGroups.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ContactListGroupDetails.SORT_ORDER, ContactListGroupDetails.CONTACT_LIST_GROUP_NAME),
                    CustomerTypeContactListGroupFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CustomerTypeContactListGroups.fields())
                    .from(CustomerTypeContactListGroups)
                    .where(CustomerTypeContactListGroups.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                            CustomerTypeContactListGroups.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return customerTypeContactListGroupFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<CustomerTypeContactListGroup> getCustomerTypeContactListGroupsByCustomerType(CustomerType customerType) {
        return getCustomerTypeContactListGroupsByCustomerType(customerType, EntityPermission.READ_ONLY);
    }

    public List<CustomerTypeContactListGroup> getCustomerTypeContactListGroupsByCustomerTypeForUpdate(CustomerType customerType) {
        return getCustomerTypeContactListGroupsByCustomerType(customerType, EntityPermission.READ_WRITE);
    }

    private List<CustomerTypeContactListGroup> getCustomerTypeContactListGroupsByContactListGroup(ContactListGroup contactListGroup, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CustomerTypeContactListGroups.fields())
                    .from(CustomerTypeContactListGroups)
                    .join(CustomerTypes).onKey(CUSTOMER_TYPE_CONTACT_LIST_GROUPS_CUSTOMER_TYPE_FK)
                    .join(CustomerTypeDetails).onKey(CUSTOMER_TYPES_LAST_DETAIL_FK)
                    .where(CustomerTypeContactListGroups.CONTACT_LIST_GROUP.eq(contactListGroup.getPrimaryKey()),
                            CustomerTypeContactListGroups.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(CustomerTypeDetails.SORT_ORDER, CustomerTypeDetails.CUSTOMER_TYPE_NAME),
                    CustomerTypeContactListGroupFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CustomerTypeContactListGroups.fields())
                    .from(CustomerTypeContactListGroups)
                    .where(CustomerTypeContactListGroups.CONTACT_LIST_GROUP.eq(contactListGroup.getPrimaryKey()),
                            CustomerTypeContactListGroups.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return customerTypeContactListGroupFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<CustomerTypeContactListGroup> getCustomerTypeContactListGroupsByContactListGroup(ContactListGroup contactListGroup) {
        return getCustomerTypeContactListGroupsByContactListGroup(contactListGroup, EntityPermission.READ_ONLY);
    }

    public List<CustomerTypeContactListGroup> getCustomerTypeContactListGroupsByContactListGroupForUpdate(ContactListGroup contactListGroup) {
        return getCustomerTypeContactListGroupsByContactListGroup(contactListGroup, EntityPermission.READ_WRITE);
    }

    public List<CustomerTypeContactListGroupTransfer> getCustomerTypeContactListGroupTransfers(UserVisit userVisit, Collection<CustomerTypeContactListGroup> customerTypeContactListGroups) {
        List<CustomerTypeContactListGroupTransfer> customerTypeContactListGroupTransfers = new ArrayList<>(customerTypeContactListGroups.size());

        customerTypeContactListGroups.forEach((customerTypeContactListGroup) ->
                customerTypeContactListGroupTransfers.add(customerTypeContactListGroupTransferCache.getCustomerTypeContactListGroupTransfer(userVisit, customerTypeContactListGroup))
        );

        return customerTypeContactListGroupTransfers;
    }

    public List<CustomerTypeContactListGroupTransfer> getCustomerTypeContactListGroupTransfersByCustomerType(UserVisit userVisit, CustomerType customerType) {
        return getCustomerTypeContactListGroupTransfers(userVisit, getCustomerTypeContactListGroupsByCustomerType(customerType));
    }

    public List<CustomerTypeContactListGroupTransfer> getCustomerTypeContactListGroupTransfersByContactListGroup(UserVisit userVisit, ContactListGroup contactListGroup) {
        return getCustomerTypeContactListGroupTransfers(userVisit, getCustomerTypeContactListGroupsByContactListGroup(contactListGroup));
    }

    public CustomerTypeContactListGroupTransfer getCustomerTypeContactListGroupTransfer(UserVisit userVisit, CustomerTypeContactListGroup customerTypeContactListGroup) {
        return customerTypeContactListGroupTransferCache.getCustomerTypeContactListGroupTransfer(userVisit, customerTypeContactListGroup);
    }

    public void updateCustomerTypeContactListGroupFromValue(CustomerTypeContactListGroupValue customerTypeContactListGroupValue, BasePK updatedBy) {
        if(customerTypeContactListGroupValue.hasBeenModified()) {
            var customerTypeContactListGroup = customerTypeContactListGroupFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     customerTypeContactListGroupValue.getPrimaryKey());

            customerTypeContactListGroup.setThruTime(session.getStartTime());
            customerTypeContactListGroup.store();

            var customerTypePK = customerTypeContactListGroup.getCustomerType().getPrimaryKey(); // Not Updated
            var contactListGroupPK = customerTypeContactListGroup.getContactListGroupPK(); // Not Updated
            var addWhenCreated = customerTypeContactListGroupValue.getAddWhenCreated();

            customerTypeContactListGroup = customerTypeContactListGroupFactory.create(customerTypePK, contactListGroupPK, addWhenCreated,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(contactListGroupPK, EventTypes.MODIFY, customerTypeContactListGroup.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteCustomerTypeContactListGroup(CustomerTypeContactListGroup customerTypeContactListGroup, BasePK deletedBy) {
        customerTypeContactListGroup.setThruTime(session.getStartTime());
        customerTypeContactListGroup.store();

        sendEvent(customerTypeContactListGroup.getContactListGroupPK(), EventTypes.MODIFY, customerTypeContactListGroup.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteCustomerTypeContactListGroups(List<CustomerTypeContactListGroup> customerTypeContactListGroups, BasePK deletedBy) {
        customerTypeContactListGroups.forEach((customerTypeContactListGroup) -> 
                deleteCustomerTypeContactListGroup(customerTypeContactListGroup, deletedBy)
        );
    }

    public void deleteCustomerTypeContactListGroupsByCustomerType(CustomerType customerType, BasePK deletedBy) {
        deleteCustomerTypeContactListGroups(getCustomerTypeContactListGroupsByCustomerTypeForUpdate(customerType), deletedBy);
    }

    public void deleteCustomerTypeContactListGroupsByContactListGroup(ContactListGroup contactListGroup, BasePK deletedBy) {
        deleteCustomerTypeContactListGroups(getCustomerTypeContactListGroupsByContactListGroupForUpdate(contactListGroup), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Customer Type Contact Lists
    // --------------------------------------------------------------------------------

    @Inject
    protected CustomerTypeContactListFactory customerTypeContactListFactory;

    @Inject
    CustomerTypeContactListTransferCache customerTypeContactListTransferCache;

    public CustomerTypeContactList createCustomerTypeContactList(CustomerType customerType, ContactList contactList, Boolean addWhenCreated, BasePK createdBy) {
        var customerTypeContactList = customerTypeContactListFactory.create( customerType, contactList, addWhenCreated,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(contactList.getPrimaryKey(), EventTypes.MODIFY, customerTypeContactList.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return customerTypeContactList;
    }

    public long countCustomerTypeContactListsByCustomerType(final CustomerType customerType) {
        return session.getDslContext()
                .selectCount()
                .from(CustomerTypeContactLists)
                .where(CustomerTypeContactLists.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                        CustomerTypeContactLists.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCustomerTypeContactListsByContactList(final ContactList contactList) {
        return session.getDslContext()
                .selectCount()
                .from(CustomerTypeContactLists)
                .where(CustomerTypeContactLists.CONTACT_LIST.eq(contactList.getPrimaryKey()),
                        CustomerTypeContactLists.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private CustomerTypeContactList getCustomerTypeContactList(CustomerType customerType, ContactList contactList, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CustomerTypeContactLists.fields())
                .from(CustomerTypeContactLists)
                .where(CustomerTypeContactLists.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                        CustomerTypeContactLists.CONTACT_LIST.eq(contactList.getPrimaryKey()),
                        CustomerTypeContactLists.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return customerTypeContactListFactory.getEntityFromQuery(entityPermission, query);
    }

    public CustomerTypeContactList getCustomerTypeContactList(CustomerType customerType, ContactList contactList) {
        return getCustomerTypeContactList(customerType, contactList, EntityPermission.READ_ONLY);
    }

    public CustomerTypeContactList getCustomerTypeContactListForUpdate(CustomerType customerType, ContactList contactList) {
        return getCustomerTypeContactList(customerType, contactList, EntityPermission.READ_WRITE);
    }

    public CustomerTypeContactListValue getCustomerTypeContactListValue(CustomerTypeContactList customerTypeContactList) {
        return customerTypeContactList == null? null: customerTypeContactList.getCustomerTypeContactListValue().clone();
    }

    public CustomerTypeContactListValue getCustomerTypeContactListValueForUpdate(CustomerType customerType, ContactList contactList) {
        return getCustomerTypeContactListValue(getCustomerTypeContactListForUpdate(customerType, contactList));
    }

    private List<CustomerTypeContactList> getCustomerTypeContactListsByCustomerType(CustomerType customerType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CustomerTypeContactLists.fields())
                    .from(CustomerTypeContactLists)
                    .join(ContactLists).onKey(CUSTOMER_TYPE_CONTACT_LISTS_CONTACT_LIST_FK)
                    .join(ContactListDetails).onKey(CONTACT_LISTS_LAST_DETAIL_FK)
                    .where(CustomerTypeContactLists.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                            CustomerTypeContactLists.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ContactListDetails.SORT_ORDER, ContactListDetails.CONTACT_LIST_NAME),
                    CustomerTypeContactListFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CustomerTypeContactLists.fields())
                    .from(CustomerTypeContactLists)
                    .where(CustomerTypeContactLists.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                            CustomerTypeContactLists.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return customerTypeContactListFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<CustomerTypeContactList> getCustomerTypeContactListsByCustomerType(CustomerType customerType) {
        return getCustomerTypeContactListsByCustomerType(customerType, EntityPermission.READ_ONLY);
    }

    public List<CustomerTypeContactList> getCustomerTypeContactListsByCustomerTypeForUpdate(CustomerType customerType) {
        return getCustomerTypeContactListsByCustomerType(customerType, EntityPermission.READ_WRITE);
    }

    private List<CustomerTypeContactList> getCustomerTypeContactListsByContactList(ContactList contactList, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CustomerTypeContactLists.fields())
                    .from(CustomerTypeContactLists)
                    .join(CustomerTypes).onKey(CUSTOMER_TYPE_CONTACT_LISTS_CUSTOMER_TYPE_FK)
                    .join(CustomerTypeDetails).onKey(CUSTOMER_TYPES_LAST_DETAIL_FK)
                    .where(CustomerTypeContactLists.CONTACT_LIST.eq(contactList.getPrimaryKey()),
                            CustomerTypeContactLists.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(CustomerTypeDetails.SORT_ORDER, CustomerTypeDetails.CUSTOMER_TYPE_NAME),
                    CustomerTypeContactListFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CustomerTypeContactLists.fields())
                    .from(CustomerTypeContactLists)
                    .where(CustomerTypeContactLists.CONTACT_LIST.eq(contactList.getPrimaryKey()),
                            CustomerTypeContactLists.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return customerTypeContactListFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<CustomerTypeContactList> getCustomerTypeContactListsByContactList(ContactList contactList) {
        return getCustomerTypeContactListsByContactList(contactList, EntityPermission.READ_ONLY);
    }

    public List<CustomerTypeContactList> getCustomerTypeContactListsByContactListForUpdate(ContactList contactList) {
        return getCustomerTypeContactListsByContactList(contactList, EntityPermission.READ_WRITE);
    }

    public List<CustomerTypeContactListTransfer> getCustomerTypeContactListTransfers(UserVisit userVisit, Collection<CustomerTypeContactList> customerTypeContactLists) {
        List<CustomerTypeContactListTransfer> customerTypeContactListTransfers = new ArrayList<>(customerTypeContactLists.size());

        customerTypeContactLists.forEach((customerTypeContactList) ->
                customerTypeContactListTransfers.add(customerTypeContactListTransferCache.getCustomerTypeContactListTransfer(userVisit, customerTypeContactList))
        );

        return customerTypeContactListTransfers;
    }

    public List<CustomerTypeContactListTransfer> getCustomerTypeContactListTransfersByCustomerType(UserVisit userVisit, CustomerType customerType) {
        return getCustomerTypeContactListTransfers(userVisit, getCustomerTypeContactListsByCustomerType(customerType));
    }

    public List<CustomerTypeContactListTransfer> getCustomerTypeContactListTransfersByContactList(UserVisit userVisit, ContactList contactList) {
        return getCustomerTypeContactListTransfers(userVisit, getCustomerTypeContactListsByContactList(contactList));
    }

    public CustomerTypeContactListTransfer getCustomerTypeContactListTransfer(UserVisit userVisit, CustomerTypeContactList customerTypeContactList) {
        return customerTypeContactListTransferCache.getCustomerTypeContactListTransfer(userVisit, customerTypeContactList);
    }

    public void updateCustomerTypeContactListFromValue(CustomerTypeContactListValue customerTypeContactListValue, BasePK updatedBy) {
        if(customerTypeContactListValue.hasBeenModified()) {
            var customerTypeContactList = customerTypeContactListFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     customerTypeContactListValue.getPrimaryKey());

            customerTypeContactList.setThruTime(session.getStartTime());
            customerTypeContactList.store();

            var customerTypePK = customerTypeContactList.getCustomerType().getPrimaryKey(); // Not Updated
            var contactListPK = customerTypeContactList.getContactListPK(); // Not Updated
            var addWhenCreated = customerTypeContactListValue.getAddWhenCreated();

            customerTypeContactList = customerTypeContactListFactory.create(customerTypePK, contactListPK, addWhenCreated, session.getStartTime(),
                    Session.MAX_TIME);

            sendEvent(contactListPK, EventTypes.MODIFY, customerTypeContactList.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteCustomerTypeContactList(CustomerTypeContactList customerTypeContactList, BasePK deletedBy) {
        customerTypeContactList.setThruTime(session.getStartTime());
        customerTypeContactList.store();

        sendEvent(customerTypeContactList.getContactListPK(), EventTypes.MODIFY, customerTypeContactList.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteCustomerTypeContactLists(List<CustomerTypeContactList> customerTypeContactLists, BasePK deletedBy) {
        customerTypeContactLists.forEach((customerTypeContactList) -> 
                deleteCustomerTypeContactList(customerTypeContactList, deletedBy)
        );
    }

    public void deleteCustomerTypeContactListsByCustomerType(CustomerType customerType, BasePK deletedBy) {
        deleteCustomerTypeContactLists(getCustomerTypeContactListsByCustomerTypeForUpdate(customerType), deletedBy);
    }

    public void deleteCustomerTypeContactListsByContactList(ContactList contactList, BasePK deletedBy) {
        deleteCustomerTypeContactLists(getCustomerTypeContactListsByContactListForUpdate(contactList), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Contact List Contact Mechanism Purposes
    // --------------------------------------------------------------------------------

    @Inject
    protected ContactListContactMechanismPurposeDetailFactory contactListContactMechanismPurposeDetailFactory;

    @Inject
    protected ContactListContactMechanismPurposeFactory contactListContactMechanismPurposeFactory;

    @Inject
    ContactListContactMechanismPurposeTransferCache contactListContactMechanismPurposeTransferCache;

    public ContactListContactMechanismPurpose createContactListContactMechanismPurpose(ContactList contactList, ContactMechanismPurpose contactMechanismPurpose, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultContactListContactMechanismPurpose = getDefaultContactListContactMechanismPurpose(contactList);
        var defaultFound = defaultContactListContactMechanismPurpose != null;

        if(defaultFound && isDefault) {
            var defaultContactListContactMechanismPurposeDetailValue = getDefaultContactListContactMechanismPurposeDetailValueForUpdate(contactList);

            defaultContactListContactMechanismPurposeDetailValue.setIsDefault(false);
            updateContactListContactMechanismPurposeFromValue(defaultContactListContactMechanismPurposeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var contactListContactMechanismPurpose = contactListContactMechanismPurposeFactory.create();
        var contactListContactMechanismPurposeDetail = contactListContactMechanismPurposeDetailFactory.create(contactListContactMechanismPurpose, contactList, contactMechanismPurpose, isDefault,
                sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        contactListContactMechanismPurpose = contactListContactMechanismPurposeFactory.getEntityFromPK(EntityPermission.READ_WRITE, contactListContactMechanismPurpose.getPrimaryKey());
        contactListContactMechanismPurpose.setActiveDetail(contactListContactMechanismPurposeDetail);
        contactListContactMechanismPurpose.setLastDetail(contactListContactMechanismPurposeDetail);
        contactListContactMechanismPurpose.store();

        sendEvent(contactListContactMechanismPurpose.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return contactListContactMechanismPurpose;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ContactListContactMechanismPurpose */
    public ContactListContactMechanismPurpose getContactListContactMechanismPurposeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ContactListContactMechanismPurposePK(entityInstance.getEntityUniqueId());

        return contactListContactMechanismPurposeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ContactListContactMechanismPurpose getContactListContactMechanismPurposeByEntityInstance(EntityInstance entityInstance) {
        return getContactListContactMechanismPurposeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ContactListContactMechanismPurpose getContactListContactMechanismPurposeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getContactListContactMechanismPurposeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countContactListContactMechanismPurposesByContactList(final ContactList contactList) {
        return session.getDslContext()
                .selectCount()
                .from(ContactListContactMechanismPurposes)
                .join(ContactListContactMechanismPurposeDetails).onKey(CONTACT_LIST_CONTACT_MECHANISM_PURPOSES_ACTIVE_DETAIL_FK)
                .where(ContactListContactMechanismPurposeDetails.CONTACT_LIST.eq(contactList.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countContactListContactMechanismPurposesByContactMechanismPurpose(final ContactMechanismPurpose contactMechanismPurpose) {
        return session.getDslContext()
                .selectCount()
                .from(ContactListContactMechanismPurposes)
                .join(ContactListContactMechanismPurposeDetails).onKey(CONTACT_LIST_CONTACT_MECHANISM_PURPOSES_ACTIVE_DETAIL_FK)
                .where(ContactListContactMechanismPurposeDetails.CONTACT_MECHANISM_PURPOSE.eq(contactMechanismPurpose.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ContactListContactMechanismPurpose getContactListContactMechanismPurpose(ContactList contactList, ContactMechanismPurpose contactMechanismPurpose, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactListContactMechanismPurposes.fields())
                .from(ContactListContactMechanismPurposes)
                .join(ContactListContactMechanismPurposeDetails).onKey(CONTACT_LIST_CONTACT_MECHANISM_PURPOSES_ACTIVE_DETAIL_FK)
                .where(ContactListContactMechanismPurposeDetails.CONTACT_LIST.eq(contactList.getPrimaryKey()),
                        ContactListContactMechanismPurposeDetails.CONTACT_MECHANISM_PURPOSE.eq(contactMechanismPurpose.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactListContactMechanismPurposeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ContactListContactMechanismPurpose getContactListContactMechanismPurpose(ContactList contactList, ContactMechanismPurpose contactMechanismPurpose) {
        return getContactListContactMechanismPurpose(contactList, contactMechanismPurpose, EntityPermission.READ_ONLY);
    }

    public ContactListContactMechanismPurpose getContactListContactMechanismPurposeForUpdate(ContactList contactList, ContactMechanismPurpose contactMechanismPurpose) {
        return getContactListContactMechanismPurpose(contactList, contactMechanismPurpose, EntityPermission.READ_WRITE);
    }

    public ContactListContactMechanismPurposeDetailValue getContactListContactMechanismPurposeDetailValueForUpdate(ContactListContactMechanismPurpose contactListContactMechanismPurpose) {
        return contactListContactMechanismPurpose == null? null: contactListContactMechanismPurpose.getLastDetailForUpdate().getContactListContactMechanismPurposeDetailValue().clone();
    }

    public ContactListContactMechanismPurposeDetailValue getContactListContactMechanismPurposeDetailValueForUpdate(ContactList contactList, ContactMechanismPurpose contactMechanismPurpose) {
        return getContactListContactMechanismPurposeDetailValueForUpdate(getContactListContactMechanismPurposeForUpdate(contactList, contactMechanismPurpose));
    }

    private ContactListContactMechanismPurpose getDefaultContactListContactMechanismPurpose(ContactList contactList, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContactListContactMechanismPurposes.fields())
                .from(ContactListContactMechanismPurposes)
                .join(ContactListContactMechanismPurposeDetails).onKey(CONTACT_LIST_CONTACT_MECHANISM_PURPOSES_ACTIVE_DETAIL_FK)
                .where(ContactListContactMechanismPurposeDetails.CONTACT_LIST.eq(contactList.getPrimaryKey()),
                        ContactListContactMechanismPurposeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contactListContactMechanismPurposeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ContactListContactMechanismPurpose getDefaultContactListContactMechanismPurpose(ContactList contactList) {
        return getDefaultContactListContactMechanismPurpose(contactList, EntityPermission.READ_ONLY);
    }

    public ContactListContactMechanismPurpose getDefaultContactListContactMechanismPurposeForUpdate(ContactList contactList) {
        return getDefaultContactListContactMechanismPurpose(contactList, EntityPermission.READ_WRITE);
    }

    public ContactListContactMechanismPurposeDetailValue getDefaultContactListContactMechanismPurposeDetailValueForUpdate(ContactList contactList) {
        return getDefaultContactListContactMechanismPurposeForUpdate(contactList).getLastDetailForUpdate().getContactListContactMechanismPurposeDetailValue().clone();
    }

    private List<ContactListContactMechanismPurpose> getContactListContactMechanismPurposesByContactList(ContactList contactList, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContactListContactMechanismPurposes.fields())
                    .from(ContactListContactMechanismPurposes)
                    .join(ContactListContactMechanismPurposeDetails).onKey(CONTACT_LIST_CONTACT_MECHANISM_PURPOSES_ACTIVE_DETAIL_FK)
                    .join(ContactMechanismPurposes).onKey(CONTACT_LIST_CONTACT_MECHANISM_PURPOSE_DETAILS_CONTACT_MECHANISM_PURPOSE_FK)
                    .where(ContactListContactMechanismPurposeDetails.CONTACT_LIST.eq(contactList.getPrimaryKey()))
                    .orderBy(ContactMechanismPurposes.SORT_ORDER, ContactMechanismPurposes.CONTACT_MECHANISM_PURPOSE_NAME),
                    ContactListContactMechanismPurposeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContactListContactMechanismPurposes.fields())
                    .from(ContactListContactMechanismPurposes)
                    .join(ContactListContactMechanismPurposeDetails).onKey(CONTACT_LIST_CONTACT_MECHANISM_PURPOSES_ACTIVE_DETAIL_FK)
                    .where(ContactListContactMechanismPurposeDetails.CONTACT_LIST.eq(contactList.getPrimaryKey()))
                    .forUpdate();
        };

        return contactListContactMechanismPurposeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ContactListContactMechanismPurpose> getContactListContactMechanismPurposesByContactList(ContactList contactList) {
        return getContactListContactMechanismPurposesByContactList(contactList, EntityPermission.READ_ONLY);
    }

    public List<ContactListContactMechanismPurpose> getContactListContactMechanismPurposesByContactListForUpdate(ContactList contactList) {
        return getContactListContactMechanismPurposesByContactList(contactList, EntityPermission.READ_WRITE);
    }

    private List<ContactListContactMechanismPurpose> getContactListContactMechanismPurposesByContactMechanismPurpose(ContactMechanismPurpose contactMechanismPurpose, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContactListContactMechanismPurposes.fields())
                    .from(ContactListContactMechanismPurposes)
                    .join(ContactListContactMechanismPurposeDetails).onKey(CONTACT_LIST_CONTACT_MECHANISM_PURPOSES_ACTIVE_DETAIL_FK)
                    .join(ContactLists).onKey(CONTACT_LIST_CONTACT_MECHANISM_PURPOSE_DETAILS_CONTACT_LIST_FK)
                    .join(ContactListDetails).onKey(CONTACT_LISTS_LAST_DETAIL_FK)
                    .where(ContactListContactMechanismPurposeDetails.CONTACT_MECHANISM_PURPOSE.eq(contactMechanismPurpose.getPrimaryKey()))
                    .orderBy(ContactListDetails.SORT_ORDER, ContactListDetails.CONTACT_LIST_NAME),
                    ContactListContactMechanismPurposeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContactListContactMechanismPurposes.fields())
                    .from(ContactListContactMechanismPurposes)
                    .join(ContactListContactMechanismPurposeDetails).onKey(CONTACT_LIST_CONTACT_MECHANISM_PURPOSES_ACTIVE_DETAIL_FK)
                    .where(ContactListContactMechanismPurposeDetails.CONTACT_MECHANISM_PURPOSE.eq(contactMechanismPurpose.getPrimaryKey()))
                    .forUpdate();
        };

        return contactListContactMechanismPurposeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ContactListContactMechanismPurpose> getContactListContactMechanismPurposesByContactMechanismPurpose(ContactMechanismPurpose contactMechanismPurpose) {
        return getContactListContactMechanismPurposesByContactMechanismPurpose(contactMechanismPurpose, EntityPermission.READ_ONLY);
    }

    public List<ContactListContactMechanismPurpose> getContactListContactMechanismPurposesByContactMechanismPurposeForUpdate(ContactMechanismPurpose contactMechanismPurpose) {
        return getContactListContactMechanismPurposesByContactMechanismPurpose(contactMechanismPurpose, EntityPermission.READ_WRITE);
    }

   public ContactListContactMechanismPurposeTransfer getContactListContactMechanismPurposeTransfer(UserVisit userVisit, ContactListContactMechanismPurpose contactListContactMechanismPurpose) {
        return contactListContactMechanismPurposeTransferCache.getContactListContactMechanismPurposeTransfer(userVisit, contactListContactMechanismPurpose);
    }

    public List<ContactListContactMechanismPurposeTransfer> getContactListContactMechanismPurposeTransfers(List<ContactListContactMechanismPurpose> contactListContactMechanismPurposes, UserVisit userVisit) {
        List<ContactListContactMechanismPurposeTransfer> contactListContactMechanismPurposeTransfers = new ArrayList<>(contactListContactMechanismPurposes.size());

        contactListContactMechanismPurposes.forEach((contactListContactMechanismPurpose) ->
                contactListContactMechanismPurposeTransfers.add(contactListContactMechanismPurposeTransferCache.getContactListContactMechanismPurposeTransfer(userVisit, contactListContactMechanismPurpose))
        );

        return contactListContactMechanismPurposeTransfers;
    }

    public List<ContactListContactMechanismPurposeTransfer> getContactListContactMechanismPurposeTransfersByContactList(UserVisit userVisit, ContactList contactList) {
        return getContactListContactMechanismPurposeTransfers(getContactListContactMechanismPurposesByContactList(contactList), userVisit);
    }
    
    public List<ContactListContactMechanismPurposeTransfer> getContactListContactMechanismPurposeTransfersByContactMechanismPurpose(UserVisit userVisit, ContactMechanismPurpose contactMechanismPurpose) {
        return getContactListContactMechanismPurposeTransfers(getContactListContactMechanismPurposesByContactMechanismPurpose(contactMechanismPurpose), userVisit);
    }
    
    public ContactListContactMechanismPurposeChoicesBean getContactListContactMechanismPurposeChoices(String defaultContactListContactMechanismPurposeChoice, Language language, boolean allowNullChoice,
            ContactList contactList) {
        var contactListContactMechanismPurposes = getContactListContactMechanismPurposesByContactList(contactList);
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

            var label = contactControl.getBestContactMechanismPurposeDescription(contactMechanismPurpose, language);
            var value = contactMechanismPurpose.getContactMechanismPurposeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultContactListContactMechanismPurposeChoice != null && defaultContactListContactMechanismPurposeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && contactListContactMechanismPurposeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ContactListContactMechanismPurposeChoicesBean(labels, values, defaultValue);
    }

    private void updateContactListContactMechanismPurposeFromValue(ContactListContactMechanismPurposeDetailValue contactListContactMechanismPurposeDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(contactListContactMechanismPurposeDetailValue.hasBeenModified()) {
            var contactListContactMechanismPurpose = contactListContactMechanismPurposeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     contactListContactMechanismPurposeDetailValue.getContactListContactMechanismPurposePK());
            var contactListContactMechanismPurposeDetail = contactListContactMechanismPurpose.getActiveDetailForUpdate();

            contactListContactMechanismPurposeDetail.setThruTime(session.getStartTime());
            contactListContactMechanismPurposeDetail.store();

            var contactListContactMechanismPurposePK = contactListContactMechanismPurposeDetail.getContactListContactMechanismPurposePK(); // Not updated
            var contactList = contactListContactMechanismPurposeDetail.getContactList();
            var contactListPK = contactList.getPrimaryKey(); // Not updated
            var contactMechanismPurposePK = contactListContactMechanismPurposeDetail.getContactMechanismPurposePK(); // Not updated
            var isDefault = contactListContactMechanismPurposeDetailValue.getIsDefault();
            var sortOrder = contactListContactMechanismPurposeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultContactListContactMechanismPurpose = getDefaultContactListContactMechanismPurpose(contactList);
                var defaultFound = defaultContactListContactMechanismPurpose != null && !defaultContactListContactMechanismPurpose.equals(contactListContactMechanismPurpose);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultContactListContactMechanismPurposeDetailValue = getDefaultContactListContactMechanismPurposeDetailValueForUpdate(contactList);

                    defaultContactListContactMechanismPurposeDetailValue.setIsDefault(false);
                    updateContactListContactMechanismPurposeFromValue(defaultContactListContactMechanismPurposeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            contactListContactMechanismPurposeDetail = contactListContactMechanismPurposeDetailFactory.create(contactListContactMechanismPurposePK, contactListPK, contactMechanismPurposePK, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);

            contactListContactMechanismPurpose.setActiveDetail(contactListContactMechanismPurposeDetail);
            contactListContactMechanismPurpose.setLastDetail(contactListContactMechanismPurposeDetail);

            sendEvent(contactListContactMechanismPurposePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateContactListContactMechanismPurposeFromValue(ContactListContactMechanismPurposeDetailValue contactListContactMechanismPurposeDetailValue, BasePK updatedBy) {
        updateContactListContactMechanismPurposeFromValue(contactListContactMechanismPurposeDetailValue, true, updatedBy);
    }

    private void deleteContactListContactMechanismPurpose(ContactListContactMechanismPurpose contactListContactMechanismPurpose, boolean checkDefault, BasePK deletedBy) {
        var contactListContactMechanismPurposeDetail = contactListContactMechanismPurpose.getLastDetailForUpdate();
        var contactList = contactListContactMechanismPurposeDetail.getContactList();

        clearContactListContactMechanismPurposeFromPartyContactLists(contactListContactMechanismPurpose, deletedBy);
        
        contactListContactMechanismPurposeDetail.setThruTime(session.getStartTime());
        contactListContactMechanismPurpose.setActiveDetail(null);
        contactListContactMechanismPurpose.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultContactListContactMechanismPurpose = getDefaultContactListContactMechanismPurpose(contactList);

            if(defaultContactListContactMechanismPurpose == null) {
                var contactListContactMechanismPurposes = getContactListContactMechanismPurposesByContactListForUpdate(contactList);

                if(!contactListContactMechanismPurposes.isEmpty()) {
                    var iter = contactListContactMechanismPurposes.iterator();
                    if(iter.hasNext()) {
                        defaultContactListContactMechanismPurpose = iter.next();
                    }
                    var contactListContactMechanismPurposeDetailValue = Objects.requireNonNull(defaultContactListContactMechanismPurpose).getLastDetailForUpdate().getContactListContactMechanismPurposeDetailValue().clone();

                    contactListContactMechanismPurposeDetailValue.setIsDefault(true);
                    updateContactListContactMechanismPurposeFromValue(contactListContactMechanismPurposeDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(contactListContactMechanismPurpose.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteContactListContactMechanismPurpose(ContactListContactMechanismPurpose contactListContactMechanismPurpose, BasePK deletedBy) {
        deleteContactListContactMechanismPurpose(contactListContactMechanismPurpose, true, deletedBy);
    }

    private void deleteContactListContactMechanismPurposes(List<ContactListContactMechanismPurpose> contactListContactMechanismPurposes, boolean checkDefault, BasePK deletedBy) {
        contactListContactMechanismPurposes.forEach((contactListContactMechanismPurpose) -> deleteContactListContactMechanismPurpose(contactListContactMechanismPurpose, checkDefault, deletedBy));
    }

    public void deleteContactListContactMechanismPurposes(List<ContactListContactMechanismPurpose> contactListContactMechanismPurposes, BasePK deletedBy) {
        deleteContactListContactMechanismPurposes(contactListContactMechanismPurposes, true, deletedBy);
    }
    
    public void deleteContactListContactMechanismPurposesByContactList(ContactList contactList, BasePK deletedBy) {
        deleteContactListContactMechanismPurposes(getContactListContactMechanismPurposesByContactListForUpdate(contactList), false, deletedBy);
    }
    
    public void deleteContactListContactMechanismPurposesByContactMechanismPurpose(ContactMechanismPurpose contactMechanismPurpose, BasePK deletedBy) {
        deleteContactListContactMechanismPurposes(getContactListContactMechanismPurposesByContactMechanismPurposeForUpdate(contactMechanismPurpose), deletedBy);
    }
    
}

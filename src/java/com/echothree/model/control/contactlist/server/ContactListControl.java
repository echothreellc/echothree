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

package com.echothree.model.control.contactlist.server;

import com.echothree.model.control.contact.server.ContactControl;
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
import com.echothree.model.control.contactlist.server.transfer.ContactListContactMechanismPurposeTransferCache;
import com.echothree.model.control.contactlist.server.transfer.ContactListFrequencyTransferCache;
import com.echothree.model.control.contactlist.server.transfer.ContactListGroupTransferCache;
import com.echothree.model.control.contactlist.server.transfer.ContactListTransferCache;
import com.echothree.model.control.contactlist.server.transfer.ContactListTransferCaches;
import com.echothree.model.control.contactlist.server.transfer.ContactListTypeTransferCache;
import com.echothree.model.control.contactlist.server.transfer.CustomerTypeContactListGroupTransferCache;
import com.echothree.model.control.contactlist.server.transfer.CustomerTypeContactListTransferCache;
import com.echothree.model.control.contactlist.server.transfer.PartyContactListTransferCache;
import com.echothree.model.control.contactlist.server.transfer.PartyTypeContactListGroupTransferCache;
import com.echothree.model.control.contactlist.server.transfer.PartyTypeContactListTransferCache;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.letter.server.LetterControl;
import com.echothree.model.control.contactlist.common.workflow.PartyContactListStatusConstants;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.chain.common.pk.ChainPK;
import com.echothree.model.data.chain.server.entity.Chain;
import com.echothree.model.data.contact.common.pk.ContactMechanismPurposePK;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.model.data.contactlist.common.pk.ContactListContactMechanismPurposePK;
import com.echothree.model.data.contactlist.common.pk.ContactListFrequencyPK;
import com.echothree.model.data.contactlist.common.pk.ContactListGroupPK;
import com.echothree.model.data.contactlist.common.pk.ContactListPK;
import com.echothree.model.data.contactlist.common.pk.ContactListTypePK;
import com.echothree.model.data.contactlist.common.pk.PartyContactListPK;
import com.echothree.model.data.contactlist.server.entity.ContactList;
import com.echothree.model.data.contactlist.server.entity.ContactListContactMechanismPurpose;
import com.echothree.model.data.contactlist.server.entity.ContactListContactMechanismPurposeDetail;
import com.echothree.model.data.contactlist.server.entity.ContactListDescription;
import com.echothree.model.data.contactlist.server.entity.ContactListDetail;
import com.echothree.model.data.contactlist.server.entity.ContactListFrequency;
import com.echothree.model.data.contactlist.server.entity.ContactListFrequencyDescription;
import com.echothree.model.data.contactlist.server.entity.ContactListFrequencyDetail;
import com.echothree.model.data.contactlist.server.entity.ContactListGroup;
import com.echothree.model.data.contactlist.server.entity.ContactListGroupDescription;
import com.echothree.model.data.contactlist.server.entity.ContactListGroupDetail;
import com.echothree.model.data.contactlist.server.entity.ContactListType;
import com.echothree.model.data.contactlist.server.entity.ContactListTypeDescription;
import com.echothree.model.data.contactlist.server.entity.ContactListTypeDetail;
import com.echothree.model.data.contactlist.server.entity.CustomerTypeContactList;
import com.echothree.model.data.contactlist.server.entity.CustomerTypeContactListGroup;
import com.echothree.model.data.contactlist.server.entity.PartyContactList;
import com.echothree.model.data.contactlist.server.entity.PartyContactListDetail;
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
import com.echothree.model.data.customer.common.pk.CustomerTypePK;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.common.pk.PartyTypePK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.common.pk.WorkflowEntrancePK;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ContactListControl
        extends BaseModelControl {
    
    /** Creates a new instance of ContactListControl */
    public ContactListControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Contact List Transfer Caches
    // --------------------------------------------------------------------------------
    
    private ContactListTransferCaches contactListTransferCaches = null;
    
    public ContactListTransferCaches getContactListTransferCaches(UserVisit userVisit) {
        if(contactListTransferCaches == null) {
            contactListTransferCaches = new ContactListTransferCaches(userVisit, this);
        }
        
        return contactListTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Contact List Types
    // --------------------------------------------------------------------------------

    public ContactListType createContactListType(String contactListTypeName, Chain confirmationRequestChain, Chain subscribeChain, Chain unsubscribeChain,
            Boolean usedForSolicitation, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        ContactListType defaultContactListType = getDefaultContactListType();
        boolean defaultFound = defaultContactListType != null;

        if(defaultFound && isDefault) {
            ContactListTypeDetailValue defaultContactListTypeDetailValue = getDefaultContactListTypeDetailValueForUpdate();

            defaultContactListTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateContactListTypeFromValue(defaultContactListTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        ContactListType contactListType = ContactListTypeFactory.getInstance().create();
        ContactListTypeDetail contactListTypeDetail = ContactListTypeDetailFactory.getInstance().create(contactListType, contactListTypeName,
                confirmationRequestChain, subscribeChain, unsubscribeChain, usedForSolicitation, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        contactListType = ContactListTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                contactListType.getPrimaryKey());
        contactListType.setActiveDetail(contactListTypeDetail);
        contactListType.setLastDetail(contactListTypeDetail);
        contactListType.store();

        sendEventUsingNames(contactListType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return contactListType;
    }

    private static final Map<EntityPermission, String> getContactListTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlisttypes, contactlisttypedetails "
                + "WHERE clsttyp_activedetailid = clsttypdt_contactlisttypedetailid AND clsttypdt_contactlisttypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlisttypes, contactlisttypedetails "
                + "WHERE clsttyp_activedetailid = clsttypdt_contactlisttypedetailid AND clsttypdt_contactlisttypename = ? "
                + "FOR UPDATE");
        getContactListTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private ContactListType getContactListTypeByName(String contactListTypeName, EntityPermission entityPermission) {
        return ContactListTypeFactory.getInstance().getEntityFromQuery(entityPermission, getContactListTypeByNameQueries,
                contactListTypeName);
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

    private static final Map<EntityPermission, String> getDefaultContactListTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlisttypes, contactlisttypedetails "
                + "WHERE clsttyp_activedetailid = clsttypdt_contactlisttypedetailid AND clsttypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlisttypes, contactlisttypedetails "
                + "WHERE clsttyp_activedetailid = clsttypdt_contactlisttypedetailid AND clsttypdt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultContactListTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private ContactListType getDefaultContactListType(EntityPermission entityPermission) {
        return ContactListTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultContactListTypeQueries);
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

    private static final Map<EntityPermission, String> getContactListTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlisttypes, contactlisttypedetails "
                + "WHERE clsttyp_activedetailid = clsttypdt_contactlisttypedetailid "
                + "ORDER BY clsttypdt_sortorder, clsttypdt_contactlisttypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlisttypes, contactlisttypedetails "
                + "WHERE clsttyp_activedetailid = clsttypdt_contactlisttypedetailid "
                + "FOR UPDATE");
        getContactListTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ContactListType> getContactListTypes(EntityPermission entityPermission) {
        return ContactListTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getContactListTypesQueries);
    }

    public List<ContactListType> getContactListTypes() {
        return getContactListTypes(EntityPermission.READ_ONLY);
    }

    public List<ContactListType> getContactListTypesForUpdate() {
        return getContactListTypes(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getContactListTypesByConfirmationRequestChainQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlisttypes, contactlisttypedetails "
                + "WHERE clsttyp_activedetailid = clsttypdt_contactlisttypedetailid AND clsttypdt_confirmationrequestchainid = ? "
                + "ORDER BY clsttypdt_sortorder, clsttypdt_contactlisttypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlisttypes, contactlisttypedetails "
                + "WHERE clsttyp_activedetailid = clsttypdt_contactlisttypedetailid AND clsttypdt_confirmationrequestchainid = ? "
                + "FOR UPDATE");
        getContactListTypesByConfirmationRequestChainQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ContactListType> getContactListTypesByConfirmationRequestChain(Chain confirmationRequestChain, EntityPermission entityPermission) {
        return ContactListTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getContactListTypesByConfirmationRequestChainQueries,
                confirmationRequestChain);
    }

    public List<ContactListType> getContactListTypesByConfirmationRequestChain(Chain confirmationRequestChain) {
        return getContactListTypesByConfirmationRequestChain(confirmationRequestChain, EntityPermission.READ_ONLY);
    }

    public List<ContactListType> getContactListTypesByConfirmationRequestChainForUpdate(Chain confirmationRequestChain) {
        return getContactListTypesByConfirmationRequestChain(confirmationRequestChain, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getContactListTypesBySubscribeChainQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlisttypes, contactlisttypedetails "
                + "WHERE clsttyp_activedetailid = clsttypdt_contactlisttypedetailid AND clsttypdt_subscribechainid = ? "
                + "ORDER BY clsttypdt_sortorder, clsttypdt_contactlisttypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlisttypes, contactlisttypedetails "
                + "WHERE clsttyp_activedetailid = clsttypdt_contactlisttypedetailid AND clsttypdt_subscribechainid = ? "
                + "FOR UPDATE");
        getContactListTypesBySubscribeChainQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ContactListType> getContactListTypesBySubscribeChain(Chain subscribeChain, EntityPermission entityPermission) {
        return ContactListTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getContactListTypesBySubscribeChainQueries,
                subscribeChain);
    }

    public List<ContactListType> getContactListTypesBySubscribeChain(Chain subscribeChain) {
        return getContactListTypesBySubscribeChain(subscribeChain, EntityPermission.READ_ONLY);
    }

    public List<ContactListType> getContactListTypesBySubscribeChainForUpdate(Chain subscribeChain) {
        return getContactListTypesBySubscribeChain(subscribeChain, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getContactListTypesByUnsubscribeChainQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlisttypes, contactlisttypedetails "
                + "WHERE clsttyp_activedetailid = clsttypdt_contactlisttypedetailid AND clsttypdt_unsubscribechainid = ? "
                + "ORDER BY clsttypdt_sortorder, clsttypdt_contactlisttypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlisttypes, contactlisttypedetails "
                + "WHERE clsttyp_activedetailid = clsttypdt_contactlisttypedetailid AND clsttypdt_unsubscribechainid = ? "
                + "FOR UPDATE");
        getContactListTypesByUnsubscribeChainQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ContactListType> getContactListTypesByUnsubscribeChain(Chain unsubscribeChain, EntityPermission entityPermission) {
        return ContactListTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getContactListTypesByUnsubscribeChainQueries,
                unsubscribeChain);
    }

    public List<ContactListType> getContactListTypesByUnsubscribeChain(Chain unsubscribeChain) {
        return getContactListTypesByUnsubscribeChain(unsubscribeChain, EntityPermission.READ_ONLY);
    }

    public List<ContactListType> getContactListTypesByUnsubscribeChainForUpdate(Chain unsubscribeChain) {
        return getContactListTypesByUnsubscribeChain(unsubscribeChain, EntityPermission.READ_WRITE);
    }

    public ContactListTypeChoicesBean getContactListTypeChoices(String defaultContactListTypeChoice, Language language, boolean allowNullChoice) {
        List<ContactListType> contactListTypes = getContactListTypes();
        int size = contactListTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultContactListTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(ContactListType contactListType: contactListTypes) {
            ContactListTypeDetail contactListTypeDetail = contactListType.getLastDetail();

            String label = getBestContactListTypeDescription(contactListType, language);
            String value = contactListTypeDetail.getContactListTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultContactListTypeChoice == null? false: defaultContactListTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && contactListTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ContactListTypeChoicesBean(labels, values, defaultValue);
    }

    public ContactListTypeTransfer getContactListTypeTransfer(UserVisit userVisit, ContactListType contactListType) {
        return getContactListTransferCaches(userVisit).getContactListTypeTransferCache().getContactListTypeTransfer(contactListType);
    }

    public List<ContactListTypeTransfer> getContactListTypeTransfers(UserVisit userVisit) {
        List<ContactListType> contactListTypes = getContactListTypes();
        List<ContactListTypeTransfer> contactListTypeTransfers = new ArrayList<>(contactListTypes.size());
        ContactListTypeTransferCache contactListTypeTransferCache = getContactListTransferCaches(userVisit).getContactListTypeTransferCache();

        contactListTypes.stream().forEach((contactListType) -> {
            contactListTypeTransfers.add(contactListTypeTransferCache.getContactListTypeTransfer(contactListType));
        });

        return contactListTypeTransfers;
    }

    private void updateContactListTypeFromValue(ContactListTypeDetailValue contactListTypeDetailValue, boolean checkDefault, BasePK updatedBy) {
        ContactListType contactListType = ContactListTypeFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, contactListTypeDetailValue.getContactListTypePK());
        ContactListTypeDetail contactListTypeDetail = contactListType.getActiveDetailForUpdate();

        contactListTypeDetail.setThruTime(session.START_TIME_LONG);
        contactListTypeDetail.store();

        ContactListTypePK contactListTypePK = contactListTypeDetail.getContactListTypePK();
        String contactListTypeName = contactListTypeDetailValue.getContactListTypeName();
        ChainPK confirmationRequestChainPK = contactListTypeDetailValue.getConfirmationRequestChainPK();
        ChainPK subscribeChainPK = contactListTypeDetailValue.getSubscribeChainPK();
        ChainPK unsubscribeChainPK = contactListTypeDetailValue.getUnsubscribeChainPK();
        Boolean usedForSolicitation = contactListTypeDetailValue.getUsedForSolicitation();
        Boolean isDefault = contactListTypeDetailValue.getIsDefault();
        Integer sortOrder = contactListTypeDetailValue.getSortOrder();

        if(checkDefault) {
            ContactListType defaultContactListType = getDefaultContactListType();
            boolean defaultFound = defaultContactListType != null && !defaultContactListType.equals(contactListType);

            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                ContactListTypeDetailValue defaultContactListTypeDetailValue = getDefaultContactListTypeDetailValueForUpdate();

                defaultContactListTypeDetailValue.setIsDefault(Boolean.FALSE);
                updateContactListTypeFromValue(defaultContactListTypeDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = Boolean.TRUE;
            }
        }

        contactListTypeDetail = ContactListTypeDetailFactory.getInstance().create(contactListTypePK, contactListTypeName, confirmationRequestChainPK,
                subscribeChainPK, unsubscribeChainPK, usedForSolicitation, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        contactListType.setActiveDetail(contactListTypeDetail);
        contactListType.setLastDetail(contactListTypeDetail);
        contactListType.store();

        sendEventUsingNames(contactListTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
    }

    public void updateContactListTypeFromValue(ContactListTypeDetailValue contactListTypeDetailValue, BasePK updatedBy) {
        updateContactListTypeFromValue(contactListTypeDetailValue, true, updatedBy);
    }

    public void deleteContactListType(ContactListType contactListType, BasePK deletedBy) {
        deleteContactListsByContactListType(contactListType, deletedBy);
        deleteContactListTypeDescriptionsByContactListType(contactListType, deletedBy);

        ContactListTypeDetail contactListTypeDetail = contactListType.getLastDetailForUpdate();
        contactListTypeDetail.setThruTime(session.START_TIME_LONG);
        contactListType.setActiveDetail(null);
        contactListType.store();

        // Check for default, and pick one if necessary
        ContactListType defaultContactListType = getDefaultContactListType();
        if(defaultContactListType == null) {
            List<ContactListType> contactListTypes = getContactListTypesForUpdate();

            if(!contactListTypes.isEmpty()) {
                Iterator<ContactListType> iter = contactListTypes.iterator();
                if(iter.hasNext()) {
                    defaultContactListType = iter.next();
                }
                ContactListTypeDetailValue contactListTypeDetailValue = defaultContactListType.getLastDetailForUpdate().getContactListTypeDetailValue().clone();

                contactListTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateContactListTypeFromValue(contactListTypeDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(contactListType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteContactListTypes(List<ContactListType> contactListTypes, BasePK deletedBy) {
        contactListTypes.stream().forEach((contactListType) -> {
            deleteContactListType(contactListType, deletedBy);
        });
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

    public ContactListTypeDescription createContactListTypeDescription(ContactListType contactListType, Language language, String description,
            BasePK createdBy) {
        ContactListTypeDescription contactListTypeDescription = ContactListTypeDescriptionFactory.getInstance().create(contactListType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(contactListType.getPrimaryKey(), EventTypes.MODIFY.name(), contactListTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return contactListTypeDescription;
    }

    private static final Map<EntityPermission, String> getContactListTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlisttypedescriptions "
                + "WHERE clsttypd_clsttyp_contactlisttypeid = ? AND clsttypd_lang_languageid = ? AND clsttypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlisttypedescriptions "
                + "WHERE clsttypd_clsttyp_contactlisttypeid = ? AND clsttypd_lang_languageid = ? AND clsttypd_thrutime = ? "
                + "FOR UPDATE");
        getContactListTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ContactListTypeDescription getContactListTypeDescription(ContactListType contactListType, Language language, EntityPermission entityPermission) {
        return ContactListTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getContactListTypeDescriptionQueries,
                contactListType, language, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getContactListTypeDescriptionsByContactListTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlisttypedescriptions, languages "
                + "WHERE clsttypd_clsttyp_contactlisttypeid = ? AND clsttypd_thrutime = ? AND clsttypd_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlisttypedescriptions "
                + "WHERE clsttypd_clsttyp_contactlisttypeid = ? AND clsttypd_thrutime = ? "
                + "FOR UPDATE");
        getContactListTypeDescriptionsByContactListTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ContactListTypeDescription> getContactListTypeDescriptionsByContactListType(ContactListType contactListType, EntityPermission entityPermission) {
        return ContactListTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getContactListTypeDescriptionsByContactListTypeQueries,
                contactListType, Session.MAX_TIME);
    }

    public List<ContactListTypeDescription> getContactListTypeDescriptionsByContactListType(ContactListType contactListType) {
        return getContactListTypeDescriptionsByContactListType(contactListType, EntityPermission.READ_ONLY);
    }

    public List<ContactListTypeDescription> getContactListTypeDescriptionsByContactListTypeForUpdate(ContactListType contactListType) {
        return getContactListTypeDescriptionsByContactListType(contactListType, EntityPermission.READ_WRITE);
    }

    public String getBestContactListTypeDescription(ContactListType contactListType, Language language) {
        String description;
        ContactListTypeDescription contactListTypeDescription = getContactListTypeDescription(contactListType, language);

        if(contactListTypeDescription == null && !language.getIsDefault()) {
            contactListTypeDescription = getContactListTypeDescription(contactListType, getPartyControl().getDefaultLanguage());
        }

        if(contactListTypeDescription == null) {
            description = contactListType.getLastDetail().getContactListTypeName();
        } else {
            description = contactListTypeDescription.getDescription();
        }

        return description;
    }

    public ContactListTypeDescriptionTransfer getContactListTypeDescriptionTransfer(UserVisit userVisit, ContactListTypeDescription contactListTypeDescription) {
        return getContactListTransferCaches(userVisit).getContactListTypeDescriptionTransferCache().getContactListTypeDescriptionTransfer(contactListTypeDescription);
    }

    public List<ContactListTypeDescriptionTransfer> getContactListTypeDescriptionTransfersByContactListType(UserVisit userVisit, ContactListType contactListType) {
        List<ContactListTypeDescription> contactListTypeDescriptions = getContactListTypeDescriptionsByContactListType(contactListType);
        List<ContactListTypeDescriptionTransfer> contactListTypeDescriptionTransfers = new ArrayList<>(contactListTypeDescriptions.size());

        contactListTypeDescriptions.stream().forEach((contactListTypeDescription) -> {
            contactListTypeDescriptionTransfers.add(getContactListTransferCaches(userVisit).getContactListTypeDescriptionTransferCache().getContactListTypeDescriptionTransfer(contactListTypeDescription));
        });

        return contactListTypeDescriptionTransfers;
    }

    public void updateContactListTypeDescriptionFromValue(ContactListTypeDescriptionValue contactListTypeDescriptionValue, BasePK updatedBy) {
        if(contactListTypeDescriptionValue.hasBeenModified()) {
            ContactListTypeDescription contactListTypeDescription = ContactListTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     contactListTypeDescriptionValue.getPrimaryKey());

            contactListTypeDescription.setThruTime(session.START_TIME_LONG);
            contactListTypeDescription.store();

            ContactListType contactListType = contactListTypeDescription.getContactListType();
            Language language = contactListTypeDescription.getLanguage();
            String description = contactListTypeDescriptionValue.getDescription();

            contactListTypeDescription = ContactListTypeDescriptionFactory.getInstance().create(contactListType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(contactListType.getPrimaryKey(), EventTypes.MODIFY.name(), contactListTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteContactListTypeDescription(ContactListTypeDescription contactListTypeDescription, BasePK deletedBy) {
        contactListTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(contactListTypeDescription.getContactListTypePK(), EventTypes.MODIFY.name(), contactListTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteContactListTypeDescriptionsByContactListType(ContactListType contactListType, BasePK deletedBy) {
        List<ContactListTypeDescription> contactListTypeDescriptions = getContactListTypeDescriptionsByContactListTypeForUpdate(contactListType);

        contactListTypeDescriptions.stream().forEach((contactListTypeDescription) -> {
            deleteContactListTypeDescription(contactListTypeDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Contact List Groups
    // --------------------------------------------------------------------------------

    public ContactListGroup createContactListGroup(String contactListGroupName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        ContactListGroup defaultContactListGroup = getDefaultContactListGroup();
        boolean defaultFound = defaultContactListGroup != null;

        if(defaultFound && isDefault) {
            ContactListGroupDetailValue defaultContactListGroupDetailValue = getDefaultContactListGroupDetailValueForUpdate();

            defaultContactListGroupDetailValue.setIsDefault(Boolean.FALSE);
            updateContactListGroupFromValue(defaultContactListGroupDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        ContactListGroup contactListGroup = ContactListGroupFactory.getInstance().create();
        ContactListGroupDetail contactListGroupDetail = ContactListGroupDetailFactory.getInstance().create(contactListGroup, contactListGroupName, isDefault,
                sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        contactListGroup = ContactListGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                contactListGroup.getPrimaryKey());
        contactListGroup.setActiveDetail(contactListGroupDetail);
        contactListGroup.setLastDetail(contactListGroupDetail);
        contactListGroup.store();

        sendEventUsingNames(contactListGroup.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return contactListGroup;
    }

    private static final Map<EntityPermission, String> getContactListGroupByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlistgroups, contactlistgroupdetails "
                + "WHERE clstgrp_activedetailid = clstgrpdt_contactlistgroupdetailid AND clstgrpdt_contactlistgroupname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlistgroups, contactlistgroupdetails "
                + "WHERE clstgrp_activedetailid = clstgrpdt_contactlistgroupdetailid AND clstgrpdt_contactlistgroupname = ? "
                + "FOR UPDATE");
        getContactListGroupByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private ContactListGroup getContactListGroupByName(String contactListGroupName, EntityPermission entityPermission) {
        return ContactListGroupFactory.getInstance().getEntityFromQuery(entityPermission, getContactListGroupByNameQueries,
                contactListGroupName);
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

    private static final Map<EntityPermission, String> getDefaultContactListGroupQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlistgroups, contactlistgroupdetails "
                + "WHERE clstgrp_activedetailid = clstgrpdt_contactlistgroupdetailid AND clstgrpdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlistgroups, contactlistgroupdetails "
                + "WHERE clstgrp_activedetailid = clstgrpdt_contactlistgroupdetailid AND clstgrpdt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultContactListGroupQueries = Collections.unmodifiableMap(queryMap);
    }

    private ContactListGroup getDefaultContactListGroup(EntityPermission entityPermission) {
        return ContactListGroupFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultContactListGroupQueries);
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

    private static final Map<EntityPermission, String> getContactListGroupsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlistgroups, contactlistgroupdetails "
                + "WHERE clstgrp_activedetailid = clstgrpdt_contactlistgroupdetailid "
                + "ORDER BY clstgrpdt_sortorder, clstgrpdt_contactlistgroupname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlistgroups, contactlistgroupdetails "
                + "WHERE clstgrp_activedetailid = clstgrpdt_contactlistgroupdetailid "
                + "FOR UPDATE");
        getContactListGroupsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ContactListGroup> getContactListGroups(EntityPermission entityPermission) {
        return ContactListGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, getContactListGroupsQueries);
    }

    public List<ContactListGroup> getContactListGroups() {
        return getContactListGroups(EntityPermission.READ_ONLY);
    }

    public List<ContactListGroup> getContactListGroupsForUpdate() {
        return getContactListGroups(EntityPermission.READ_WRITE);
    }

    public ContactListGroupChoicesBean getContactListGroupChoices(String defaultContactListGroupChoice, Language language, boolean allowNullChoice) {
        List<ContactListGroup> contactListGroups = getContactListGroups();
        int size = contactListGroups.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultContactListGroupChoice == null) {
                defaultValue = "";
            }
        }

        for(ContactListGroup contactListGroup: contactListGroups) {
            ContactListGroupDetail contactListGroupDetail = contactListGroup.getLastDetail();

            String label = getBestContactListGroupDescription(contactListGroup, language);
            String value = contactListGroupDetail.getContactListGroupName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultContactListGroupChoice == null? false: defaultContactListGroupChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && contactListGroupDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ContactListGroupChoicesBean(labels, values, defaultValue);
    }

    public ContactListGroupTransfer getContactListGroupTransfer(UserVisit userVisit, ContactListGroup contactListGroup) {
        return getContactListTransferCaches(userVisit).getContactListGroupTransferCache().getContactListGroupTransfer(contactListGroup);
    }

    public List<ContactListGroupTransfer> getContactListGroupTransfers(UserVisit userVisit) {
        List<ContactListGroup> contactListGroups = getContactListGroups();
        List<ContactListGroupTransfer> contactListGroupTransfers = new ArrayList<>(contactListGroups.size());
        ContactListGroupTransferCache contactListGroupTransferCache = getContactListTransferCaches(userVisit).getContactListGroupTransferCache();

        contactListGroups.stream().forEach((contactListGroup) -> {
            contactListGroupTransfers.add(contactListGroupTransferCache.getContactListGroupTransfer(contactListGroup));
        });

        return contactListGroupTransfers;
    }

    private void updateContactListGroupFromValue(ContactListGroupDetailValue contactListGroupDetailValue, boolean checkDefault, BasePK updatedBy) {
        ContactListGroup contactListGroup = ContactListGroupFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, contactListGroupDetailValue.getContactListGroupPK());
        ContactListGroupDetail contactListGroupDetail = contactListGroup.getActiveDetailForUpdate();

        contactListGroupDetail.setThruTime(session.START_TIME_LONG);
        contactListGroupDetail.store();

        ContactListGroupPK contactListGroupPK = contactListGroupDetail.getContactListGroupPK();
        String contactListGroupName = contactListGroupDetailValue.getContactListGroupName();
        Boolean isDefault = contactListGroupDetailValue.getIsDefault();
        Integer sortOrder = contactListGroupDetailValue.getSortOrder();

        if(checkDefault) {
            ContactListGroup defaultContactListGroup = getDefaultContactListGroup();
            boolean defaultFound = defaultContactListGroup != null && !defaultContactListGroup.equals(contactListGroup);

            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                ContactListGroupDetailValue defaultContactListGroupDetailValue = getDefaultContactListGroupDetailValueForUpdate();

                defaultContactListGroupDetailValue.setIsDefault(Boolean.FALSE);
                updateContactListGroupFromValue(defaultContactListGroupDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = Boolean.TRUE;
            }
        }

        contactListGroupDetail = ContactListGroupDetailFactory.getInstance().create(contactListGroupPK, contactListGroupName, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        contactListGroup.setActiveDetail(contactListGroupDetail);
        contactListGroup.setLastDetail(contactListGroupDetail);
        contactListGroup.store();

        sendEventUsingNames(contactListGroupPK, EventTypes.MODIFY.name(), null, null, updatedBy);
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

        ContactListGroupDetail contactListGroupDetail = contactListGroup.getLastDetailForUpdate();
        contactListGroupDetail.setThruTime(session.START_TIME_LONG);
        contactListGroup.setActiveDetail(null);
        contactListGroup.store();

        // Check for default, and pick one if necessary
        ContactListGroup defaultContactListGroup = getDefaultContactListGroup();
        if(defaultContactListGroup == null) {
            List<ContactListGroup> contactListGroups = getContactListGroupsForUpdate();

            if(!contactListGroups.isEmpty()) {
                Iterator<ContactListGroup> iter = contactListGroups.iterator();
                if(iter.hasNext()) {
                    defaultContactListGroup = iter.next();
                }
                ContactListGroupDetailValue contactListGroupDetailValue = defaultContactListGroup.getLastDetailForUpdate().getContactListGroupDetailValue().clone();

                contactListGroupDetailValue.setIsDefault(Boolean.TRUE);
                updateContactListGroupFromValue(contactListGroupDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(contactListGroup.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Contact List Group Descriptions
    // --------------------------------------------------------------------------------

    public ContactListGroupDescription createContactListGroupDescription(ContactListGroup contactListGroup, Language language, String description,
            BasePK createdBy) {
        ContactListGroupDescription contactListGroupDescription = ContactListGroupDescriptionFactory.getInstance().create(contactListGroup,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(contactListGroup.getPrimaryKey(), EventTypes.MODIFY.name(), contactListGroupDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return contactListGroupDescription;
    }

    private static final Map<EntityPermission, String> getContactListGroupDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlistgroupdescriptions "
                + "WHERE clstgrpd_clstgrp_contactlistgroupid = ? AND clstgrpd_lang_languageid = ? AND clstgrpd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlistgroupdescriptions "
                + "WHERE clstgrpd_clstgrp_contactlistgroupid = ? AND clstgrpd_lang_languageid = ? AND clstgrpd_thrutime = ? "
                + "FOR UPDATE");
        getContactListGroupDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ContactListGroupDescription getContactListGroupDescription(ContactListGroup contactListGroup, Language language, EntityPermission entityPermission) {
        return ContactListGroupDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getContactListGroupDescriptionQueries,
                contactListGroup, language, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getContactListGroupDescriptionsByContactListGroupQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlistgroupdescriptions, languages "
                + "WHERE clstgrpd_clstgrp_contactlistgroupid = ? AND clstgrpd_thrutime = ? AND clstgrpd_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlistgroupdescriptions "
                + "WHERE clstgrpd_clstgrp_contactlistgroupid = ? AND clstgrpd_thrutime = ? "
                + "FOR UPDATE");
        getContactListGroupDescriptionsByContactListGroupQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ContactListGroupDescription> getContactListGroupDescriptionsByContactListGroup(ContactListGroup contactListGroup, EntityPermission entityPermission) {
        return ContactListGroupDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getContactListGroupDescriptionsByContactListGroupQueries,
                contactListGroup, Session.MAX_TIME);
    }

    public List<ContactListGroupDescription> getContactListGroupDescriptionsByContactListGroup(ContactListGroup contactListGroup) {
        return getContactListGroupDescriptionsByContactListGroup(contactListGroup, EntityPermission.READ_ONLY);
    }

    public List<ContactListGroupDescription> getContactListGroupDescriptionsByContactListGroupForUpdate(ContactListGroup contactListGroup) {
        return getContactListGroupDescriptionsByContactListGroup(contactListGroup, EntityPermission.READ_WRITE);
    }

    public String getBestContactListGroupDescription(ContactListGroup contactListGroup, Language language) {
        String description;
        ContactListGroupDescription contactListGroupDescription = getContactListGroupDescription(contactListGroup, language);

        if(contactListGroupDescription == null && !language.getIsDefault()) {
            contactListGroupDescription = getContactListGroupDescription(contactListGroup, getPartyControl().getDefaultLanguage());
        }

        if(contactListGroupDescription == null) {
            description = contactListGroup.getLastDetail().getContactListGroupName();
        } else {
            description = contactListGroupDescription.getDescription();
        }

        return description;
    }

    public ContactListGroupDescriptionTransfer getContactListGroupDescriptionTransfer(UserVisit userVisit, ContactListGroupDescription contactListGroupDescription) {
        return getContactListTransferCaches(userVisit).getContactListGroupDescriptionTransferCache().getContactListGroupDescriptionTransfer(contactListGroupDescription);
    }

    public List<ContactListGroupDescriptionTransfer> getContactListGroupDescriptionTransfersByContactListGroup(UserVisit userVisit, ContactListGroup contactListGroup) {
        List<ContactListGroupDescription> contactListGroupDescriptions = getContactListGroupDescriptionsByContactListGroup(contactListGroup);
        List<ContactListGroupDescriptionTransfer> contactListGroupDescriptionTransfers = new ArrayList<>(contactListGroupDescriptions.size());

        contactListGroupDescriptions.stream().forEach((contactListGroupDescription) -> {
            contactListGroupDescriptionTransfers.add(getContactListTransferCaches(userVisit).getContactListGroupDescriptionTransferCache().getContactListGroupDescriptionTransfer(contactListGroupDescription));
        });

        return contactListGroupDescriptionTransfers;
    }

    public void updateContactListGroupDescriptionFromValue(ContactListGroupDescriptionValue contactListGroupDescriptionValue, BasePK updatedBy) {
        if(contactListGroupDescriptionValue.hasBeenModified()) {
            ContactListGroupDescription contactListGroupDescription = ContactListGroupDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     contactListGroupDescriptionValue.getPrimaryKey());

            contactListGroupDescription.setThruTime(session.START_TIME_LONG);
            contactListGroupDescription.store();

            ContactListGroup contactListGroup = contactListGroupDescription.getContactListGroup();
            Language language = contactListGroupDescription.getLanguage();
            String description = contactListGroupDescriptionValue.getDescription();

            contactListGroupDescription = ContactListGroupDescriptionFactory.getInstance().create(contactListGroup, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(contactListGroup.getPrimaryKey(), EventTypes.MODIFY.name(), contactListGroupDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteContactListGroupDescription(ContactListGroupDescription contactListGroupDescription, BasePK deletedBy) {
        contactListGroupDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(contactListGroupDescription.getContactListGroupPK(), EventTypes.MODIFY.name(), contactListGroupDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteContactListGroupDescriptionsByContactListGroup(ContactListGroup contactListGroup, BasePK deletedBy) {
        List<ContactListGroupDescription> contactListGroupDescriptions = getContactListGroupDescriptionsByContactListGroupForUpdate(contactListGroup);

        contactListGroupDescriptions.stream().forEach((contactListGroupDescription) -> {
            deleteContactListGroupDescription(contactListGroupDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Contact List Frequencies
    // --------------------------------------------------------------------------------

    public ContactListFrequency createContactListFrequency(String contactListFrequencyName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        ContactListFrequency defaultContactListFrequency = getDefaultContactListFrequency();
        boolean defaultFound = defaultContactListFrequency != null;

        if(defaultFound && isDefault) {
            ContactListFrequencyDetailValue defaultContactListFrequencyDetailValue = getDefaultContactListFrequencyDetailValueForUpdate();

            defaultContactListFrequencyDetailValue.setIsDefault(Boolean.FALSE);
            updateContactListFrequencyFromValue(defaultContactListFrequencyDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        ContactListFrequency contactListFrequency = ContactListFrequencyFactory.getInstance().create();
        ContactListFrequencyDetail contactListFrequencyDetail = ContactListFrequencyDetailFactory.getInstance().create(contactListFrequency, contactListFrequencyName, isDefault,
                sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        contactListFrequency = ContactListFrequencyFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                contactListFrequency.getPrimaryKey());
        contactListFrequency.setActiveDetail(contactListFrequencyDetail);
        contactListFrequency.setLastDetail(contactListFrequencyDetail);
        contactListFrequency.store();

        sendEventUsingNames(contactListFrequency.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return contactListFrequency;
    }

    private static final Map<EntityPermission, String> getContactListFrequencyByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlistfrequencies, contactlistfrequencydetails "
                + "WHERE clstfrq_activedetailid = clstfrqdt_contactlistfrequencydetailid AND clstfrqdt_contactlistfrequencyname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlistfrequencies, contactlistfrequencydetails "
                + "WHERE clstfrq_activedetailid = clstfrqdt_contactlistfrequencydetailid AND clstfrqdt_contactlistfrequencyname = ? "
                + "FOR UPDATE");
        getContactListFrequencyByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private ContactListFrequency getContactListFrequencyByName(String contactListFrequencyName, EntityPermission entityPermission) {
        return ContactListFrequencyFactory.getInstance().getEntityFromQuery(entityPermission, getContactListFrequencyByNameQueries,
                contactListFrequencyName);
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

    private static final Map<EntityPermission, String> getDefaultContactListFrequencyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlistfrequencies, contactlistfrequencydetails "
                + "WHERE clstfrq_activedetailid = clstfrqdt_contactlistfrequencydetailid AND clstfrqdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlistfrequencies, contactlistfrequencydetails "
                + "WHERE clstfrq_activedetailid = clstfrqdt_contactlistfrequencydetailid AND clstfrqdt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultContactListFrequencyQueries = Collections.unmodifiableMap(queryMap);
    }

    private ContactListFrequency getDefaultContactListFrequency(EntityPermission entityPermission) {
        return ContactListFrequencyFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultContactListFrequencyQueries);
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

    private static final Map<EntityPermission, String> getContactListFrequenciesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlistfrequencies, contactlistfrequencydetails "
                + "WHERE clstfrq_activedetailid = clstfrqdt_contactlistfrequencydetailid "
                + "ORDER BY clstfrqdt_sortorder, clstfrqdt_contactlistfrequencyname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlistfrequencies, contactlistfrequencydetails "
                + "WHERE clstfrq_activedetailid = clstfrqdt_contactlistfrequencydetailid "
                + "FOR UPDATE");
        getContactListFrequenciesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ContactListFrequency> getContactListFrequencies(EntityPermission entityPermission) {
        return ContactListFrequencyFactory.getInstance().getEntitiesFromQuery(entityPermission, getContactListFrequenciesQueries);
    }

    public List<ContactListFrequency> getContactListFrequencies() {
        return getContactListFrequencies(EntityPermission.READ_ONLY);
    }

    public List<ContactListFrequency> getContactListFrequenciesForUpdate() {
        return getContactListFrequencies(EntityPermission.READ_WRITE);
    }

    public ContactListFrequencyChoicesBean getContactListFrequencyChoices(String defaultContactListFrequencyChoice, Language language, boolean allowNullChoice) {
        List<ContactListFrequency> contactListFrequencies = getContactListFrequencies();
        int size = contactListFrequencies.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultContactListFrequencyChoice == null) {
                defaultValue = "";
            }
        }

        for(ContactListFrequency contactListFrequency: contactListFrequencies) {
            ContactListFrequencyDetail contactListFrequencyDetail = contactListFrequency.getLastDetail();

            String label = getBestContactListFrequencyDescription(contactListFrequency, language);
            String value = contactListFrequencyDetail.getContactListFrequencyName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultContactListFrequencyChoice == null? false: defaultContactListFrequencyChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && contactListFrequencyDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ContactListFrequencyChoicesBean(labels, values, defaultValue);
    }

    public ContactListFrequencyTransfer getContactListFrequencyTransfer(UserVisit userVisit, ContactListFrequency contactListFrequency) {
        return getContactListTransferCaches(userVisit).getContactListFrequencyTransferCache().getContactListFrequencyTransfer(contactListFrequency);
    }

    public List<ContactListFrequencyTransfer> getContactListFrequencyTransfers(UserVisit userVisit) {
        List<ContactListFrequency> contactListFrequencies = getContactListFrequencies();
        List<ContactListFrequencyTransfer> contactListFrequencyTransfers = new ArrayList<>(contactListFrequencies.size());
        ContactListFrequencyTransferCache contactListFrequencyTransferCache = getContactListTransferCaches(userVisit).getContactListFrequencyTransferCache();

        contactListFrequencies.stream().forEach((contactListFrequency) -> {
            contactListFrequencyTransfers.add(contactListFrequencyTransferCache.getContactListFrequencyTransfer(contactListFrequency));
        });

        return contactListFrequencyTransfers;
    }

    private void updateContactListFrequencyFromValue(ContactListFrequencyDetailValue contactListFrequencyDetailValue, boolean checkDefault, BasePK updatedBy) {
        ContactListFrequency contactListFrequency = ContactListFrequencyFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, contactListFrequencyDetailValue.getContactListFrequencyPK());
        ContactListFrequencyDetail contactListFrequencyDetail = contactListFrequency.getActiveDetailForUpdate();

        contactListFrequencyDetail.setThruTime(session.START_TIME_LONG);
        contactListFrequencyDetail.store();

        ContactListFrequencyPK contactListFrequencyPK = contactListFrequencyDetail.getContactListFrequencyPK();
        String contactListFrequencyName = contactListFrequencyDetailValue.getContactListFrequencyName();
        Boolean isDefault = contactListFrequencyDetailValue.getIsDefault();
        Integer sortOrder = contactListFrequencyDetailValue.getSortOrder();

        if(checkDefault) {
            ContactListFrequency defaultContactListFrequency = getDefaultContactListFrequency();
            boolean defaultFound = defaultContactListFrequency != null && !defaultContactListFrequency.equals(contactListFrequency);

            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                ContactListFrequencyDetailValue defaultContactListFrequencyDetailValue = getDefaultContactListFrequencyDetailValueForUpdate();

                defaultContactListFrequencyDetailValue.setIsDefault(Boolean.FALSE);
                updateContactListFrequencyFromValue(defaultContactListFrequencyDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = Boolean.TRUE;
            }
        }

        contactListFrequencyDetail = ContactListFrequencyDetailFactory.getInstance().create(contactListFrequencyPK, contactListFrequencyName, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        contactListFrequency.setActiveDetail(contactListFrequencyDetail);
        contactListFrequency.setLastDetail(contactListFrequencyDetail);
        contactListFrequency.store();

        sendEventUsingNames(contactListFrequencyPK, EventTypes.MODIFY.name(), null, null, updatedBy);
    }

    public void updateContactListFrequencyFromValue(ContactListFrequencyDetailValue contactListFrequencyDetailValue, BasePK updatedBy) {
        updateContactListFrequencyFromValue(contactListFrequencyDetailValue, true, updatedBy);
    }

    public void deleteContactListFrequency(ContactListFrequency contactListFrequency, BasePK deletedBy) {
        deleteContactListsByContactListFrequency(contactListFrequency, deletedBy);
        deleteContactListFrequencyDescriptionsByContactListFrequency(contactListFrequency, deletedBy);

        ContactListFrequencyDetail contactListFrequencyDetail = contactListFrequency.getLastDetailForUpdate();
        contactListFrequencyDetail.setThruTime(session.START_TIME_LONG);
        contactListFrequency.setActiveDetail(null);
        contactListFrequency.store();

        // Check for default, and pick one if necessary
        ContactListFrequency defaultContactListFrequency = getDefaultContactListFrequency();
        if(defaultContactListFrequency == null) {
            List<ContactListFrequency> contactListFrequencies = getContactListFrequenciesForUpdate();

            if(!contactListFrequencies.isEmpty()) {
                Iterator<ContactListFrequency> iter = contactListFrequencies.iterator();
                if(iter.hasNext()) {
                    defaultContactListFrequency = iter.next();
                }
                ContactListFrequencyDetailValue contactListFrequencyDetailValue = defaultContactListFrequency.getLastDetailForUpdate().getContactListFrequencyDetailValue().clone();

                contactListFrequencyDetailValue.setIsDefault(Boolean.TRUE);
                updateContactListFrequencyFromValue(contactListFrequencyDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(contactListFrequency.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Contact List Frequency Descriptions
    // --------------------------------------------------------------------------------

    public ContactListFrequencyDescription createContactListFrequencyDescription(ContactListFrequency contactListFrequency, Language language, String description,
            BasePK createdBy) {
        ContactListFrequencyDescription contactListFrequencyDescription = ContactListFrequencyDescriptionFactory.getInstance().create(contactListFrequency,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(contactListFrequency.getPrimaryKey(), EventTypes.MODIFY.name(), contactListFrequencyDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return contactListFrequencyDescription;
    }

    private static final Map<EntityPermission, String> getContactListFrequencyDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlistfrequencydescriptions "
                + "WHERE clstfrqd_clstfrq_contactlistfrequencyid = ? AND clstfrqd_lang_languageid = ? AND clstfrqd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlistfrequencydescriptions "
                + "WHERE clstfrqd_clstfrq_contactlistfrequencyid = ? AND clstfrqd_lang_languageid = ? AND clstfrqd_thrutime = ? "
                + "FOR UPDATE");
        getContactListFrequencyDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ContactListFrequencyDescription getContactListFrequencyDescription(ContactListFrequency contactListFrequency, Language language, EntityPermission entityPermission) {
        return ContactListFrequencyDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getContactListFrequencyDescriptionQueries,
                contactListFrequency, language, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getContactListFrequencyDescriptionsByContactListFrequencyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlistfrequencydescriptions, languages "
                + "WHERE clstfrqd_clstfrq_contactlistfrequencyid = ? AND clstfrqd_thrutime = ? AND clstfrqd_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlistfrequencydescriptions "
                + "WHERE clstfrqd_clstfrq_contactlistfrequencyid = ? AND clstfrqd_thrutime = ? "
                + "FOR UPDATE");
        getContactListFrequencyDescriptionsByContactListFrequencyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ContactListFrequencyDescription> getContactListFrequencyDescriptionsByContactListFrequency(ContactListFrequency contactListFrequency, EntityPermission entityPermission) {
        return ContactListFrequencyDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getContactListFrequencyDescriptionsByContactListFrequencyQueries,
                contactListFrequency, Session.MAX_TIME);
    }

    public List<ContactListFrequencyDescription> getContactListFrequencyDescriptionsByContactListFrequency(ContactListFrequency contactListFrequency) {
        return getContactListFrequencyDescriptionsByContactListFrequency(contactListFrequency, EntityPermission.READ_ONLY);
    }

    public List<ContactListFrequencyDescription> getContactListFrequencyDescriptionsByContactListFrequencyForUpdate(ContactListFrequency contactListFrequency) {
        return getContactListFrequencyDescriptionsByContactListFrequency(contactListFrequency, EntityPermission.READ_WRITE);
    }

    public String getBestContactListFrequencyDescription(ContactListFrequency contactListFrequency, Language language) {
        String description;
        ContactListFrequencyDescription contactListFrequencyDescription = getContactListFrequencyDescription(contactListFrequency, language);

        if(contactListFrequencyDescription == null && !language.getIsDefault()) {
            contactListFrequencyDescription = getContactListFrequencyDescription(contactListFrequency, getPartyControl().getDefaultLanguage());
        }

        if(contactListFrequencyDescription == null) {
            description = contactListFrequency.getLastDetail().getContactListFrequencyName();
        } else {
            description = contactListFrequencyDescription.getDescription();
        }

        return description;
    }

    public ContactListFrequencyDescriptionTransfer getContactListFrequencyDescriptionTransfer(UserVisit userVisit, ContactListFrequencyDescription contactListFrequencyDescription) {
        return getContactListTransferCaches(userVisit).getContactListFrequencyDescriptionTransferCache().getContactListFrequencyDescriptionTransfer(contactListFrequencyDescription);
    }

    public List<ContactListFrequencyDescriptionTransfer> getContactListFrequencyDescriptionTransfersByContactListFrequency(UserVisit userVisit, ContactListFrequency contactListFrequency) {
        List<ContactListFrequencyDescription> contactListFrequencyDescriptions = getContactListFrequencyDescriptionsByContactListFrequency(contactListFrequency);
        List<ContactListFrequencyDescriptionTransfer> contactListFrequencyDescriptionTransfers = new ArrayList<>(contactListFrequencyDescriptions.size());

        contactListFrequencyDescriptions.stream().forEach((contactListFrequencyDescription) -> {
            contactListFrequencyDescriptionTransfers.add(getContactListTransferCaches(userVisit).getContactListFrequencyDescriptionTransferCache().getContactListFrequencyDescriptionTransfer(contactListFrequencyDescription));
        });

        return contactListFrequencyDescriptionTransfers;
    }

    public void updateContactListFrequencyDescriptionFromValue(ContactListFrequencyDescriptionValue contactListFrequencyDescriptionValue, BasePK updatedBy) {
        if(contactListFrequencyDescriptionValue.hasBeenModified()) {
            ContactListFrequencyDescription contactListFrequencyDescription = ContactListFrequencyDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     contactListFrequencyDescriptionValue.getPrimaryKey());

            contactListFrequencyDescription.setThruTime(session.START_TIME_LONG);
            contactListFrequencyDescription.store();

            ContactListFrequency contactListFrequency = contactListFrequencyDescription.getContactListFrequency();
            Language language = contactListFrequencyDescription.getLanguage();
            String description = contactListFrequencyDescriptionValue.getDescription();

            contactListFrequencyDescription = ContactListFrequencyDescriptionFactory.getInstance().create(contactListFrequency, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(contactListFrequency.getPrimaryKey(), EventTypes.MODIFY.name(), contactListFrequencyDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteContactListFrequencyDescription(ContactListFrequencyDescription contactListFrequencyDescription, BasePK deletedBy) {
        contactListFrequencyDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(contactListFrequencyDescription.getContactListFrequencyPK(), EventTypes.MODIFY.name(), contactListFrequencyDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteContactListFrequencyDescriptionsByContactListFrequency(ContactListFrequency contactListFrequency, BasePK deletedBy) {
        List<ContactListFrequencyDescription> contactListFrequencyDescriptions = getContactListFrequencyDescriptionsByContactListFrequencyForUpdate(contactListFrequency);

        contactListFrequencyDescriptions.stream().forEach((contactListFrequencyDescription) -> {
            deleteContactListFrequencyDescription(contactListFrequencyDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Contact Lists
    // --------------------------------------------------------------------------------

    public ContactList createContactList(String contactListName, ContactListGroup contactListGroup, ContactListType contactListType,
            ContactListFrequency contactListFrequency, WorkflowEntrance defaultPartyContactListStatus, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        ContactList defaultContactList = getDefaultContactList();
        boolean defaultFound = defaultContactList != null;

        if(defaultFound && isDefault) {
            ContactListDetailValue defaultContactListDetailValue = getDefaultContactListDetailValueForUpdate();

            defaultContactListDetailValue.setIsDefault(Boolean.FALSE);
            updateContactListFromValue(defaultContactListDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        ContactList contactList = ContactListFactory.getInstance().create();
        ContactListDetail contactListDetail = ContactListDetailFactory.getInstance().create(contactList, contactListName, contactListGroup, contactListType,
            contactListFrequency, defaultPartyContactListStatus, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        contactList = ContactListFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                contactList.getPrimaryKey());
        contactList.setActiveDetail(contactListDetail);
        contactList.setLastDetail(contactListDetail);
        contactList.store();

        sendEventUsingNames(contactList.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return contactList;
    }

    private static final Map<EntityPermission, String> getContactListByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlists, contactlistdetails "
                + "WHERE clst_activedetailid = clstdt_contactlistdetailid AND clstdt_contactlistname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlists, contactlistdetails "
                + "WHERE clst_activedetailid = clstdt_contactlistdetailid AND clstdt_contactlistname = ? "
                + "FOR UPDATE");
        getContactListByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private ContactList getContactListByName(String contactListName, EntityPermission entityPermission) {
        return ContactListFactory.getInstance().getEntityFromQuery(entityPermission, getContactListByNameQueries,
                contactListName);
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

    private static final Map<EntityPermission, String> getDefaultContactListQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlists, contactlistdetails "
                + "WHERE clst_activedetailid = clstdt_contactlistdetailid AND clstdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlists, contactlistdetails "
                + "WHERE clst_activedetailid = clstdt_contactlistdetailid AND clstdt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultContactListQueries = Collections.unmodifiableMap(queryMap);
    }

    private ContactList getDefaultContactList(EntityPermission entityPermission) {
        return ContactListFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultContactListQueries);
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

    private static final Map<EntityPermission, String> getContactListsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlists, contactlistdetails "
                + "WHERE clst_activedetailid = clstdt_contactlistdetailid "
                + "ORDER BY clstdt_sortorder, clstdt_contactlistname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlists, contactlistdetails "
                + "WHERE clst_activedetailid = clstdt_contactlistdetailid "
                + "FOR UPDATE");
        getContactListsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ContactList> getContactLists(EntityPermission entityPermission) {
        return ContactListFactory.getInstance().getEntitiesFromQuery(entityPermission, getContactListsQueries);
    }

    public List<ContactList> getContactLists() {
        return getContactLists(EntityPermission.READ_ONLY);
    }

    public List<ContactList> getContactListsForUpdate() {
        return getContactLists(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getContactListsByContactListGroupQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlists, contactlistdetails "
                + "WHERE clst_activedetailid = clstdt_contactlistdetailid "
                + "AND clstdt_clstgrp_contactlistgroupid = ? "
                + "ORDER BY clstdt_sortorder, clstdt_contactlistname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlists, contactlistdetails "
                + "WHERE clst_activedetailid = clstdt_contactlistdetailid "
                + "AND clstdt_clstgrp_contactlistgroupid = ? "
                + "FOR UPDATE");
        getContactListsByContactListGroupQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ContactList> getContactListsByContactListGroup(ContactListGroup contactListGroup, EntityPermission entityPermission) {
        return ContactListFactory.getInstance().getEntitiesFromQuery(entityPermission, getContactListsByContactListGroupQueries,
                contactListGroup);
    }

    public List<ContactList> getContactListsByContactListGroup(ContactListGroup contactListGroup) {
        return getContactListsByContactListGroup(contactListGroup, EntityPermission.READ_ONLY);
    }

    public List<ContactList> getContactListsByContactListGroupForUpdate(ContactListGroup contactListGroup) {
        return getContactListsByContactListGroup(contactListGroup, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getContactListsByContactListTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlists, contactlistdetails "
                + "WHERE clst_activedetailid = clstdt_contactlistdetailid "
                + "AND clstdt_clsttyp_contactlisttypeid = ? "
                + "ORDER BY clstdt_sortorder, clstdt_contactlistname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlists, contactlistdetails "
                + "WHERE clst_activedetailid = clstdt_contactlistdetailid "
                + "AND clstdt_clsttyp_contactlisttypeid = ? "
                + "FOR UPDATE");
        getContactListsByContactListTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ContactList> getContactListsByContactListType(ContactListType contactListType, EntityPermission entityPermission) {
        return ContactListFactory.getInstance().getEntitiesFromQuery(entityPermission, getContactListsByContactListTypeQueries,
                contactListType);
    }

    public List<ContactList> getContactListsByContactListType(ContactListType contactListType) {
        return getContactListsByContactListType(contactListType, EntityPermission.READ_ONLY);
    }

    public List<ContactList> getContactListsByContactListTypeForUpdate(ContactListType contactListType) {
        return getContactListsByContactListType(contactListType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getContactListsByContactListFrequencyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlists, contactlistdetails "
                + "WHERE clst_activedetailid = clstdt_contactlistdetailid "
                + "AND clstdt_clstfrq_contactlistfrequencyid = ? "
                + "ORDER BY clstdt_sortorder, clstdt_contactlistname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlists, contactlistdetails "
                + "WHERE clst_activedetailid = clstdt_contactlistdetailid "
                + "AND clstdt_clstfrq_contactlistfrequencyid = ? "
                + "FOR UPDATE");
        getContactListsByContactListFrequencyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ContactList> getContactListsByContactListFrequency(ContactListFrequency contactListFrequency, EntityPermission entityPermission) {
        return ContactListFactory.getInstance().getEntitiesFromQuery(entityPermission, getContactListsByContactListFrequencyQueries,
                contactListFrequency);
    }

    public List<ContactList> getContactListsByContactListFrequency(ContactListFrequency contactListFrequency) {
        return getContactListsByContactListFrequency(contactListFrequency, EntityPermission.READ_ONLY);
    }

    public List<ContactList> getContactListsByContactListFrequencyForUpdate(ContactListFrequency contactListFrequency) {
        return getContactListsByContactListFrequency(contactListFrequency, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getContactListsByWorkflowEntranceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlists, contactlistdetails "
                + "WHERE clst_activedetailid = clstdt_contactlistdetailid "
                + "AND clstdt_wkflen_workflowentranceid = ? "
                + "ORDER BY clstdt_sortorder, clstdt_contactlistname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlists, contactlistdetails "
                + "WHERE clst_activedetailid = clstdt_contactlistdetailid "
                + "AND clstdt_wkflen_workflowentranceid = ? "
                + "FOR UPDATE");
        getContactListsByWorkflowEntranceQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ContactList> getContactListsByWorkflowEntrance(WorkflowEntrance workflowEntrance, EntityPermission entityPermission) {
        return ContactListFactory.getInstance().getEntitiesFromQuery(entityPermission, getContactListsByWorkflowEntranceQueries,
                workflowEntrance);
    }

    public List<ContactList> getContactListsByWorkflowEntrance(WorkflowEntrance workflowEntrance) {
        return getContactListsByWorkflowEntrance(workflowEntrance, EntityPermission.READ_ONLY);
    }

    public List<ContactList> getContactListsByWorkflowEntranceForUpdate(WorkflowEntrance workflowEntrance) {
        return getContactListsByWorkflowEntrance(workflowEntrance, EntityPermission.READ_WRITE);
    }

    public ContactListChoicesBean getContactListChoices(String defaultContactListChoice, Language language, boolean allowNullChoice) {
        List<ContactList> contactLists = getContactLists();
        int size = contactLists.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultContactListChoice == null) {
                defaultValue = "";
            }
        }

        for(ContactList contactList: contactLists) {
            ContactListDetail contactListDetail = contactList.getLastDetail();

            String label = getBestContactListDescription(contactList, language);
            String value = contactListDetail.getContactListName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultContactListChoice == null? false: defaultContactListChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && contactListDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ContactListChoicesBean(labels, values, defaultValue);
    }

    public ContactListTransfer getContactListTransfer(UserVisit userVisit, ContactList contactList) {
        return getContactListTransferCaches(userVisit).getContactListTransferCache().getContactListTransfer(contactList);
    }

    public List<ContactListTransfer> getContactListTransfers(UserVisit userVisit) {
        List<ContactList> contactLists = getContactLists();
        List<ContactListTransfer> contactListTransfers = new ArrayList<>(contactLists.size());
        ContactListTransferCache contactListTransferCache = getContactListTransferCaches(userVisit).getContactListTransferCache();

        contactLists.stream().forEach((contactList) -> {
            contactListTransfers.add(contactListTransferCache.getContactListTransfer(contactList));
        });

        return contactListTransfers;
    }

    private void updateContactListFromValue(ContactListDetailValue contactListDetailValue, boolean checkDefault, BasePK updatedBy) {
        ContactList contactList = ContactListFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, contactListDetailValue.getContactListPK());
        ContactListDetail contactListDetail = contactList.getActiveDetailForUpdate();

        contactListDetail.setThruTime(session.START_TIME_LONG);
        contactListDetail.store();

        ContactListPK contactListPK = contactListDetail.getContactListPK();
        String contactListName = contactListDetailValue.getContactListName();
        ContactListGroupPK contactListGroupPK = contactListDetailValue.getContactListGroupPK();
        ContactListTypePK contactListTypePK = contactListDetailValue.getContactListTypePK();
        ContactListFrequencyPK contactListFrequencyPK = contactListDetailValue.getContactListFrequencyPK();
        WorkflowEntrancePK defaultPartyContactListStatusPK = contactListDetailValue.getDefaultPartyContactListStatusPK();
        Boolean isDefault = contactListDetailValue.getIsDefault();
        Integer sortOrder = contactListDetailValue.getSortOrder();

        if(checkDefault) {
            ContactList defaultContactList = getDefaultContactList();
            boolean defaultFound = defaultContactList != null && !defaultContactList.equals(contactList);

            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                ContactListDetailValue defaultContactListDetailValue = getDefaultContactListDetailValueForUpdate();

                defaultContactListDetailValue.setIsDefault(Boolean.FALSE);
                updateContactListFromValue(defaultContactListDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = Boolean.TRUE;
            }
        }

        contactListDetail = ContactListDetailFactory.getInstance().create(contactListPK, contactListName, contactListGroupPK, contactListTypePK,
                contactListFrequencyPK, defaultPartyContactListStatusPK, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        contactList.setActiveDetail(contactListDetail);
        contactList.setLastDetail(contactListDetail);
        contactList.store();

        sendEventUsingNames(contactListPK, EventTypes.MODIFY.name(), null, null, updatedBy);
    }

    public void updateContactListFromValue(ContactListDetailValue contactListDetailValue, BasePK updatedBy) {
        updateContactListFromValue(contactListDetailValue, true, updatedBy);
    }

    public void deleteContactList(ContactList contactList, BasePK deletedBy) {
        LetterControl letterControl = (LetterControl)Session.getModelController(LetterControl.class);
        
        letterControl.deleteLettersByContactList(contactList, deletedBy);
        deletePartyContactListsByContactList(contactList, deletedBy);
        deletePartyTypeContactListsByContactList(contactList, deletedBy);
        deleteCustomerTypeContactListsByContactList(contactList, deletedBy);
        deleteContactListContactMechanismPurposesByContactList(contactList, deletedBy);
        deleteContactListDescriptionsByContactList(contactList, deletedBy);

        ContactListDetail contactListDetail = contactList.getLastDetailForUpdate();
        contactListDetail.setThruTime(session.START_TIME_LONG);
        contactList.setActiveDetail(null);
        contactList.store();

        // Check for default, and pick one if necessary
        ContactList defaultContactList = getDefaultContactList();
        if(defaultContactList == null) {
            List<ContactList> contactLists = getContactListsForUpdate();

            if(!contactLists.isEmpty()) {
                Iterator<ContactList> iter = contactLists.iterator();
                if(iter.hasNext()) {
                    defaultContactList = iter.next();
                }
                ContactListDetailValue contactListDetailValue = defaultContactList.getLastDetailForUpdate().getContactListDetailValue().clone();

                contactListDetailValue.setIsDefault(Boolean.TRUE);
                updateContactListFromValue(contactListDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(contactList.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteContactLists(List<ContactList> contactLists, BasePK deletedBy) {
        contactLists.stream().forEach((contactList) -> {
            deleteContactList(contactList, deletedBy);
        });
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

    public ContactListDescription createContactListDescription(ContactList contactList, Language language, String description,
            BasePK createdBy) {
        ContactListDescription contactListDescription = ContactListDescriptionFactory.getInstance().create(contactList,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(contactList.getPrimaryKey(), EventTypes.MODIFY.name(), contactListDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return contactListDescription;
    }

    private static final Map<EntityPermission, String> getContactListDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlistdescriptions "
                + "WHERE clstd_clst_contactlistid = ? AND clstd_lang_languageid = ? AND clstd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlistdescriptions "
                + "WHERE clstd_clst_contactlistid = ? AND clstd_lang_languageid = ? AND clstd_thrutime = ? "
                + "FOR UPDATE");
        getContactListDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ContactListDescription getContactListDescription(ContactList contactList, Language language, EntityPermission entityPermission) {
        return ContactListDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getContactListDescriptionQueries,
                contactList, language, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getContactListDescriptionsByContactListQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlistdescriptions, languages "
                + "WHERE clstd_clst_contactlistid = ? AND clstd_thrutime = ? AND clstd_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlistdescriptions "
                + "WHERE clstd_clst_contactlistid = ? AND clstd_thrutime = ? "
                + "FOR UPDATE");
        getContactListDescriptionsByContactListQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ContactListDescription> getContactListDescriptionsByContactList(ContactList contactList, EntityPermission entityPermission) {
        return ContactListDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getContactListDescriptionsByContactListQueries,
                contactList, Session.MAX_TIME);
    }

    public List<ContactListDescription> getContactListDescriptionsByContactList(ContactList contactList) {
        return getContactListDescriptionsByContactList(contactList, EntityPermission.READ_ONLY);
    }

    public List<ContactListDescription> getContactListDescriptionsByContactListForUpdate(ContactList contactList) {
        return getContactListDescriptionsByContactList(contactList, EntityPermission.READ_WRITE);
    }

    public String getBestContactListDescription(ContactList contactList, Language language) {
        String description;
        ContactListDescription contactListDescription = getContactListDescription(contactList, language);

        if(contactListDescription == null && !language.getIsDefault()) {
            contactListDescription = getContactListDescription(contactList, getPartyControl().getDefaultLanguage());
        }

        if(contactListDescription == null) {
            description = contactList.getLastDetail().getContactListName();
        } else {
            description = contactListDescription.getDescription();
        }

        return description;
    }

    public ContactListDescriptionTransfer getContactListDescriptionTransfer(UserVisit userVisit, ContactListDescription contactListDescription) {
        return getContactListTransferCaches(userVisit).getContactListDescriptionTransferCache().getContactListDescriptionTransfer(contactListDescription);
    }

    public List<ContactListDescriptionTransfer> getContactListDescriptionTransfersByContactList(UserVisit userVisit, ContactList contactList) {
        List<ContactListDescription> contactListDescriptions = getContactListDescriptionsByContactList(contactList);
        List<ContactListDescriptionTransfer> contactListDescriptionTransfers = new ArrayList<>(contactListDescriptions.size());

        contactListDescriptions.stream().forEach((contactListDescription) -> {
            contactListDescriptionTransfers.add(getContactListTransferCaches(userVisit).getContactListDescriptionTransferCache().getContactListDescriptionTransfer(contactListDescription));
        });

        return contactListDescriptionTransfers;
    }

    public void updateContactListDescriptionFromValue(ContactListDescriptionValue contactListDescriptionValue, BasePK updatedBy) {
        if(contactListDescriptionValue.hasBeenModified()) {
            ContactListDescription contactListDescription = ContactListDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     contactListDescriptionValue.getPrimaryKey());

            contactListDescription.setThruTime(session.START_TIME_LONG);
            contactListDescription.store();

            ContactList contactList = contactListDescription.getContactList();
            Language language = contactListDescription.getLanguage();
            String description = contactListDescriptionValue.getDescription();

            contactListDescription = ContactListDescriptionFactory.getInstance().create(contactList, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(contactList.getPrimaryKey(), EventTypes.MODIFY.name(), contactListDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteContactListDescription(ContactListDescription contactListDescription, BasePK deletedBy) {
        contactListDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(contactListDescription.getContactListPK(), EventTypes.MODIFY.name(), contactListDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteContactListDescriptionsByContactList(ContactList contactList, BasePK deletedBy) {
        List<ContactListDescription> contactListDescriptions = getContactListDescriptionsByContactListForUpdate(contactList);

        contactListDescriptions.stream().forEach((contactListDescription) -> {
            deleteContactListDescription(contactListDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Party Contact Lists
    // --------------------------------------------------------------------------------
    
    public PartyContactList createPartyContactList(Party party, ContactList contactList, ContactListContactMechanismPurpose preferredContactListContactMechanismPurpose,
            BasePK createdBy) {
        PartyContactList partyContactList = PartyContactListFactory.getInstance().create();
        PartyContactListDetail partyContactListDetail = PartyContactListDetailFactory.getInstance().create(session, partyContactList, party, contactList,
                preferredContactListContactMechanismPurpose, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        partyContactList = PartyContactListFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, partyContactList.getPrimaryKey());
        partyContactList.setActiveDetail(partyContactListDetail);
        partyContactList.setLastDetail(partyContactListDetail);
        partyContactList.store();
        
        sendEventUsingNames(partyContactList.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return partyContactList;
    }
    
    private static final Map<EntityPermission, String> getPartyContactListQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM partycontactlists, partycontactlistdetails "
                + "WHERE parclst_activedetailid = parclstdt_partycontactlistdetailid AND parclstdt_par_partyid = ? AND parclstdt_clst_contactlistid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM partycontactlists, partycontactlistdetails "
                + "WHERE parclst_activedetailid = parclstdt_partycontactlistdetailid AND parclstdt_par_partyid = ? AND parclstdt_clst_contactlistid = ? "
                + "FOR UPDATE");
        getPartyContactListQueries = Collections.unmodifiableMap(queryMap);
    }

    private PartyContactList getPartyContactList(Party party, ContactList contactList, EntityPermission entityPermission) {
        return PartyContactListFactory.getInstance().getEntityFromQuery(entityPermission, getPartyContactListQueries,
                party, contactList);
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
    
    private static final Map<EntityPermission, String> getPartyContactListsByPartyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM partycontactlists, partycontactlistdetails, contactlists, contactlistdetails "
                + "WHERE parclst_activedetailid = parclstdt_partycontactlistdetailid AND parclstdt_par_partyid = ? "
                + "AND parclstdt_clst_contactlistid = clst_contactlistid "
                + "AND clst_activedetailid = clstdt_contactlistdetailid "
                + "ORDER BY clstdt_sortorder, clstdt_contactlistname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM partycontactlists, partycontactlistdetails "
                + "WHERE parclst_activedetailid = parclstdt_partycontactlistdetailid AND parclstdt_par_partyid = ? "
                + "FOR UPDATE");
        getPartyContactListsByPartyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyContactList> getPartyContactListsByParty(Party party, EntityPermission entityPermission) {
        return PartyContactListFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyContactListsByPartyQueries,
                party);
    }

    public List<PartyContactList> getPartyContactListsByParty(Party party) {
        return getPartyContactListsByParty(party, EntityPermission.READ_ONLY);
    }
    
    public List<PartyContactList> getPartyContactListsByPartyForUpdate(Party party) {
        return getPartyContactListsByParty(party, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getPartyContactListsByContactListQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM partycontactlists, partycontactlistdetails, parties, partydetails, partytypes "
                + "WHERE parclst_activedetailid = parclstdt_partycontactlistdetailid AND parclstdt_clst_contactlistid = ? "
                + "AND parclstdt_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid "
                + "AND pardt_ptyp_partytypeid = ptyp_partytypeid "
                + "ORDER BY parclstdt_sortorder, pardt_partyname, ptyp_sortorder, ptyp_partytypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM partycontactlists, partycontactlistdetails "
                + "WHERE parclst_activedetailid = parclstdt_partycontactlistdetailid AND parclstdt_clst_contactlistid = ? "
                + "FOR UPDATE");
        getPartyContactListsByContactListQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyContactList> getPartyContactListsByContactList(ContactList contactList, EntityPermission entityPermission) {
        return PartyContactListFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyContactListsByContactListQueries,
                contactList);
    }

    public List<PartyContactList> getPartyContactListsByContactList(ContactList contactList) {
        return getPartyContactListsByContactList(contactList, EntityPermission.READ_ONLY);
    }
    
    public List<PartyContactList> getPartyContactListsByContactListForUpdate(ContactList contactList) {
        return getPartyContactListsByContactList(contactList, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getPartyContactListsByPreferredContactListContactMechanismPurposeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM partycontactlists, partycontactlistdetails "
                + "WHERE parclst_activedetailid = parclstdt_partycontactlistdetailid AND parclstdt_preferredcontactlistcontactmechanismpurposeid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM partycontactlists, partycontactlistdetails "
                + "WHERE parclst_activedetailid = parclstdt_partycontactlistdetailid AND parclstdt_preferredcontactlistcontactmechanismpurposeid = ? "
                + "FOR UPDATE");
        getPartyContactListsByPreferredContactListContactMechanismPurposeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyContactList> getPartyContactListsByPreferredContactListContactMechanismPurpose(ContactListContactMechanismPurpose preferredContactListContactMechanismPurpose,
            EntityPermission entityPermission) {
        return PartyContactListFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyContactListsByPreferredContactListContactMechanismPurposeQueries,
                preferredContactListContactMechanismPurpose);
    }

    public List<PartyContactList> getPartyContactListsByPreferredContactListContactMechanismPurpose(ContactListContactMechanismPurpose preferredContactListContactMechanismPurpose) {
        return getPartyContactListsByPreferredContactListContactMechanismPurpose(preferredContactListContactMechanismPurpose, EntityPermission.READ_ONLY);
    }
    
    public List<PartyContactList> getPartyContactListsByPreferredContactListContactMechanismPurposeForUpdate(ContactListContactMechanismPurpose preferredContactListContactMechanismPurpose) {
        return getPartyContactListsByPreferredContactListContactMechanismPurpose(preferredContactListContactMechanismPurpose, EntityPermission.READ_WRITE);
    }
    
    public List<PartyContactListTransfer> getPartyContactListTransfers(UserVisit userVisit, List<PartyContactList> partyContactLists) {
        List<PartyContactListTransfer> partyContactListTransfers = new ArrayList<>(partyContactLists.size());
        PartyContactListTransferCache partyContactListTransferCache = getContactListTransferCaches(userVisit).getPartyContactListTransferCache();
        
        partyContactLists.stream().forEach((partyContactList) -> {
            partyContactListTransfers.add(partyContactListTransferCache.getPartyContactListTransfer(partyContactList));
        });
        
        return partyContactListTransfers;
    }
    
    public List<PartyContactListTransfer> getPartyContactListTransfersByParty(UserVisit userVisit, Party party) {
        return getPartyContactListTransfers(userVisit, getPartyContactListsByParty(party));
    }
    
    public List<PartyContactListTransfer> getPartyContactListTransfersByContactList(UserVisit userVisit, ContactList contactList) {
        return getPartyContactListTransfers(userVisit, getPartyContactListsByContactList(contactList));
    }
    
    public PartyContactListTransfer getPartyContactListTransfer(UserVisit userVisit, PartyContactList partyContactList) {
        return getContactListTransferCaches(userVisit).getPartyContactListTransferCache().getPartyContactListTransfer(partyContactList);
    }
    
    public PartyContactListStatusChoicesBean getPartyContactListStatusChoices(String defaultPartyContactListStatusChoice, Language language,
            boolean allowNullChoice, PartyContactList partyContactList, PartyPK partyPK) {
        WorkflowControl workflowControl = getWorkflowControl();
        PartyContactListStatusChoicesBean partyContactListStatusChoicesBean = new PartyContactListStatusChoicesBean();

        if(partyContactList == null) {
            workflowControl.getWorkflowEntranceChoices(partyContactListStatusChoicesBean, defaultPartyContactListStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(PartyContactListStatusConstants.Workflow_PARTY_CONTACT_LIST_STATUS), partyPK);
        } else {
            EntityInstance entityInstance = getCoreControl().getEntityInstanceByBasePK(partyContactList.getPrimaryKey());
            WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(PartyContactListStatusConstants.Workflow_PARTY_CONTACT_LIST_STATUS,
                    entityInstance);

            workflowControl.getWorkflowDestinationChoices(partyContactListStatusChoicesBean, defaultPartyContactListStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }

        return partyContactListStatusChoicesBean;
    }

    public void setPartyContactListStatus(ExecutionErrorAccumulator eea, PartyContactList partyContactList, String partyContactListStatusChoice,
            PartyPK modifiedBy) {
        WorkflowControl workflowControl = getWorkflowControl();
        EntityInstance entityInstance = getEntityInstanceByBaseEntity(partyContactList);
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(PartyContactListStatusConstants.Workflow_PARTY_CONTACT_LIST_STATUS,
                entityInstance);
        WorkflowDestination workflowDestination = partyContactListStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), partyContactListStatusChoice);

        if(workflowDestination != null || partyContactListStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownPartyContactListStatusChoice.name(), partyContactListStatusChoice);
        }
    }

    public void updatePartyContactListFromValue(PartyContactListDetailValue partyContactListDetailValue, BasePK updatedBy) {
        if(partyContactListDetailValue.hasBeenModified()) {
            PartyContactList partyContactList = PartyContactListFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyContactListDetailValue.getPartyContactListPK());
            PartyContactListDetail partyContactListDetail = partyContactList.getActiveDetailForUpdate();
            
            partyContactListDetail.setThruTime(session.START_TIME_LONG);
            partyContactListDetail.store();
            
            PartyContactListPK partyContactListPK = partyContactListDetail.getPartyContactListPK();
            PartyPK partyPK = partyContactListDetail.getPartyPK(); // Not updated
            ContactListPK contactListPK = partyContactListDetail.getContactListPK(); // Not updated
            ContactListContactMechanismPurposePK preferredContactListContactMechanismPurposePK = partyContactListDetailValue.getPreferredContactListContactMechanismPurposePK();
            
            partyContactListDetail = PartyContactListDetailFactory.getInstance().create(partyContactListPK, partyPK, contactListPK,
                    preferredContactListContactMechanismPurposePK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            partyContactList.setActiveDetail(partyContactListDetail);
            partyContactList.setLastDetail(partyContactListDetail);
            
            sendEventUsingNames(partyContactList.getPrimaryKey(), EventTypes.MODIFY.name(), null, null, updatedBy);
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
        PartyContactListDetail partyContactListDetail = partyContactList.getLastDetailForUpdate();
        partyContactListDetail.setThruTime(session.START_TIME_LONG);
        partyContactList.setActiveDetail(null);
        partyContactList.store();
        
        sendEventUsingNames(partyContactList.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deletePartyContactListsByParty(Party party, BasePK deletedBy) {
        List<PartyContactList> partyContactLists = getPartyContactListsByPartyForUpdate(party);
        
        partyContactLists.stream().forEach((partyContactList) -> {
            deletePartyContactList(partyContactList, deletedBy);
        });
    }
    
    public void deletePartyContactListsByContactList(ContactList contactList, BasePK deletedBy) {
        List<PartyContactList> partyContactLists = getPartyContactListsByContactListForUpdate(contactList);
        
        partyContactLists.stream().forEach((partyContactList) -> {
            deletePartyContactList(partyContactList, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Party Type Contact List Groups
    // --------------------------------------------------------------------------------
    
    public PartyTypeContactListGroup createPartyTypeContactListGroup(PartyType partyType, ContactListGroup contactListGroup,
            Boolean addWhenCreated, BasePK createdBy) {
        PartyTypeContactListGroup partyTypeContactListGroup = PartyTypeContactListGroupFactory.getInstance().create(session,
                partyType, contactListGroup, addWhenCreated, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(contactListGroup.getPrimaryKey(), EventTypes.MODIFY.name(), partyTypeContactListGroup.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return partyTypeContactListGroup;
    }
    
    private static final Map<EntityPermission, String> getPartyTypeContactListGroupQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM partytypecontactlistgroups "
                + "WHERE ptypclstgrp_ptyp_partytypeid = ? AND ptypclstgrp_clstgrp_contactlistgroupid = ? AND ptypclstgrp_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM partytypecontactlistgroups "
                + "WHERE ptypclstgrp_ptyp_partytypeid = ? AND ptypclstgrp_clstgrp_contactlistgroupid = ? AND ptypclstgrp_thrutime = ? "
                + "FOR UPDATE");
        getPartyTypeContactListGroupQueries = Collections.unmodifiableMap(queryMap);
    }

    private PartyTypeContactListGroup getPartyTypeContactListGroup(PartyType partyType, ContactListGroup contactListGroup, EntityPermission entityPermission) {
        return PartyTypeContactListGroupFactory.getInstance().getEntityFromQuery(entityPermission, getPartyTypeContactListGroupQueries,
                partyType, contactListGroup, Session.MAX_TIME);
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
    
    private static final Map<EntityPermission, String> getPartyTypeContactListGroupsByPartyTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM partytypecontactlistgroups, contactlistgroups, contactlistgroupdetails "
                + "WHERE ptypclstgrp_ptyp_partytypeid = ? AND ptypclstgrp_thrutime = ? "
                + "AND ptypclstgrp_clstgrp_contactlistgroupid = clstgrp_contactlistgroupid AND clstgrp_lastdetailid = clstgrpdt_contactlistgroupdetailid "
                + "ORDER BY clstgrpdt_sortorder, clstgrpdt_contactlistgroupname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM partytypecontactlistgroups "
                + "WHERE ptypclstgrp_ptyp_partytypeid = ? AND ptypclstgrp_thrutime = ? "
                + "FOR UPDATE");
        getPartyTypeContactListGroupsByPartyTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyTypeContactListGroup> getPartyTypeContactListGroupsByPartyType(PartyType partyType, EntityPermission entityPermission) {
        return PartyTypeContactListGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyTypeContactListGroupsByPartyTypeQueries,
                partyType, Session.MAX_TIME);
    }

    public List<PartyTypeContactListGroup> getPartyTypeContactListGroupsByPartyType(PartyType partyType) {
        return getPartyTypeContactListGroupsByPartyType(partyType, EntityPermission.READ_ONLY);
    }
    
    public List<PartyTypeContactListGroup> getPartyTypeContactListGroupsByPartyTypeForUpdate(PartyType partyType) {
        return getPartyTypeContactListGroupsByPartyType(partyType, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getPartyTypeContactListGroupsByContactListGroupQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM partytypecontactlistgroups, partytypes "
                + "WHERE ptypclstgrp_clstgrp_contactlistgroupid = ? AND ptypclstgrp_thrutime = ? "
                + "AND ptypclstgrp_ptyp_partytypeid = ptyp_partytypeid "
                + "ORDER BY ptyp_sortorder, ptyp_partytypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM partytypecontactlistgroups "
                + "WHERE ptypclstgrp_clstgrp_contactlistgroupid = ? AND ptypclstgrp_thrutime = ? "
                + "FOR UPDATE");
        getPartyTypeContactListGroupsByContactListGroupQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyTypeContactListGroup> getPartyTypeContactListGroupsByContactListGroup(ContactListGroup contactListGroup, EntityPermission entityPermission) {
        return PartyTypeContactListGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyTypeContactListGroupsByContactListGroupQueries,
                contactListGroup, Session.MAX_TIME);
    }

    public List<PartyTypeContactListGroup> getPartyTypeContactListGroupsByContactListGroup(ContactListGroup contactListGroup) {
        return getPartyTypeContactListGroupsByContactListGroup(contactListGroup, EntityPermission.READ_ONLY);
    }
    
    public List<PartyTypeContactListGroup> getPartyTypeContactListGroupsByContactListGroupForUpdate(ContactListGroup contactListGroup) {
        return getPartyTypeContactListGroupsByContactListGroup(contactListGroup, EntityPermission.READ_WRITE);
    }
    
    public List<PartyTypeContactListGroupTransfer> getPartyTypeContactListGroupTransfers(UserVisit userVisit, List<PartyTypeContactListGroup> partyTypeContactListGroups) {
        List<PartyTypeContactListGroupTransfer> partyTypeContactListGroupTransfers = new ArrayList<>(partyTypeContactListGroups.size());
        PartyTypeContactListGroupTransferCache partyTypeContactListGroupTransferCache = getContactListTransferCaches(userVisit).getPartyTypeContactListGroupTransferCache();
        
        partyTypeContactListGroups.stream().forEach((partyTypeContactListGroup) -> {
            partyTypeContactListGroupTransfers.add(partyTypeContactListGroupTransferCache.getPartyTypeContactListGroupTransfer(partyTypeContactListGroup));
        });
        
        return partyTypeContactListGroupTransfers;
    }
    
    public List<PartyTypeContactListGroupTransfer> getPartyTypeContactListGroupTransfersByPartyType(UserVisit userVisit, PartyType partyType) {
        return getPartyTypeContactListGroupTransfers(userVisit, getPartyTypeContactListGroupsByPartyType(partyType));
    }
    
    public List<PartyTypeContactListGroupTransfer> getPartyTypeContactListGroupTransfersByContactListGroup(UserVisit userVisit, ContactListGroup contactListGroup) {
        return getPartyTypeContactListGroupTransfers(userVisit, getPartyTypeContactListGroupsByContactListGroup(contactListGroup));
    }
    
    public PartyTypeContactListGroupTransfer getPartyTypeContactListGroupTransfer(UserVisit userVisit, PartyTypeContactListGroup partyTypeContactListGroup) {
        return getContactListTransferCaches(userVisit).getPartyTypeContactListGroupTransferCache().getPartyTypeContactListGroupTransfer(partyTypeContactListGroup);
    }
    
    public void updatePartyTypeContactListGroupFromValue(PartyTypeContactListGroupValue partyTypeContactListGroupValue, BasePK updatedBy) {
        if(partyTypeContactListGroupValue.hasBeenModified()) {
            PartyTypeContactListGroup partyTypeContactListGroup = PartyTypeContactListGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyTypeContactListGroupValue.getPrimaryKey());
            
            partyTypeContactListGroup.setThruTime(session.START_TIME_LONG);
            partyTypeContactListGroup.store();
            
            PartyTypePK partyTypePK = partyTypeContactListGroup.getPartyType().getPrimaryKey(); // Not Updated
            ContactListGroupPK contactListGroupPK = partyTypeContactListGroup.getContactListGroupPK(); // Not Updated
            Boolean addWhenCreated = partyTypeContactListGroupValue.getAddWhenCreated();
            
            partyTypeContactListGroup = PartyTypeContactListGroupFactory.getInstance().create(partyTypePK, contactListGroupPK, addWhenCreated,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(contactListGroupPK, EventTypes.MODIFY.name(), partyTypeContactListGroup.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deletePartyTypeContactListGroup(PartyTypeContactListGroup partyTypeContactListGroup, BasePK deletedBy) {
        partyTypeContactListGroup.setThruTime(session.START_TIME_LONG);
        partyTypeContactListGroup.store();
        
        sendEventUsingNames(partyTypeContactListGroup.getContactListGroupPK(), EventTypes.MODIFY.name(), partyTypeContactListGroup.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deletePartyTypeContactListGroups(List<PartyTypeContactListGroup> partyTypeContactListGroups, BasePK deletedBy) {
        partyTypeContactListGroups.stream().forEach((partyTypeContactListGroup) -> {
            deletePartyTypeContactListGroup(partyTypeContactListGroup, deletedBy);
        });
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
    
    public PartyTypeContactList createPartyTypeContactList(PartyType partyType, ContactList contactList, Boolean addWhenCreated, BasePK createdBy) {
        PartyTypeContactList partyTypeContactList = PartyTypeContactListFactory.getInstance().create(session, partyType, contactList, addWhenCreated,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(contactList.getPrimaryKey(), EventTypes.MODIFY.name(), partyTypeContactList.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return partyTypeContactList;
    }
    
    private static final Map<EntityPermission, String> getPartyTypeContactListQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM partytypecontactlists "
                + "WHERE ptypclst_ptyp_partytypeid = ? AND ptypclst_clst_contactlistid = ? AND ptypclst_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM partytypecontactlists "
                + "WHERE ptypclst_ptyp_partytypeid = ? AND ptypclst_clst_contactlistid = ? AND ptypclst_thrutime = ? "
                + "FOR UPDATE");
        getPartyTypeContactListQueries = Collections.unmodifiableMap(queryMap);
    }

    private PartyTypeContactList getPartyTypeContactList(PartyType partyType, ContactList contactList, EntityPermission entityPermission) {
        return PartyTypeContactListFactory.getInstance().getEntityFromQuery(entityPermission, getPartyTypeContactListQueries,
                partyType, contactList, Session.MAX_TIME);
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
    
    private static final Map<EntityPermission, String> getPartyTypeContactListsByPartyTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM partytypecontactlists, contactlists, contactlistdetails "
                + "WHERE ptypclst_ptyp_partytypeid = ? AND ptypclst_thrutime = ? "
                + "AND ptypclst_clst_contactlistid = clst_contactlistid AND clst_lastdetailid = clstdt_contactlistdetailid "
                + "ORDER BY clstdt_sortorder, clstdt_contactlistname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM partytypecontactlists "
                + "WHERE ptypclst_ptyp_partytypeid = ? AND ptypclst_thrutime = ? "
                + "FOR UPDATE");
        getPartyTypeContactListsByPartyTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyTypeContactList> getPartyTypeContactListsByPartyType(PartyType partyType, EntityPermission entityPermission) {
        return PartyTypeContactListFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyTypeContactListsByPartyTypeQueries,
                partyType, Session.MAX_TIME);
    }

    public List<PartyTypeContactList> getPartyTypeContactListsByPartyType(PartyType partyType) {
        return getPartyTypeContactListsByPartyType(partyType, EntityPermission.READ_ONLY);
    }
    
    public List<PartyTypeContactList> getPartyTypeContactListsByPartyTypeForUpdate(PartyType partyType) {
        return getPartyTypeContactListsByPartyType(partyType, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getPartyTypeContactListsByContactListQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM partytypecontactlists, partytypes "
                + "WHERE ptypclst_clst_contactlistid = ? AND ptypclst_thrutime = ? "
                + "AND ptypclst_ptyp_partytypeid = ptyp_partytypeid "
                + "ORDER BY ptyp_sortorder, ptyp_partytypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM partytypecontactlists "
                + "WHERE ptypclst_clst_contactlistid = ? AND ptypclst_thrutime = ? "
                + "FOR UPDATE");
        getPartyTypeContactListsByContactListQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyTypeContactList> getPartyTypeContactListsByContactList(ContactList contactList, EntityPermission entityPermission) {
        return PartyTypeContactListFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyTypeContactListsByContactListQueries,
                contactList, Session.MAX_TIME);
    }

    public List<PartyTypeContactList> getPartyTypeContactListsByContactList(ContactList contactList) {
        return getPartyTypeContactListsByContactList(contactList, EntityPermission.READ_ONLY);
    }
    
    public List<PartyTypeContactList> getPartyTypeContactListsByContactListForUpdate(ContactList contactList) {
        return getPartyTypeContactListsByContactList(contactList, EntityPermission.READ_WRITE);
    }
    
    public List<PartyTypeContactListTransfer> getPartyTypeContactListTransfers(UserVisit userVisit, List<PartyTypeContactList> partyTypeContactLists) {
        List<PartyTypeContactListTransfer> partyTypeContactListTransfers = new ArrayList<>(partyTypeContactLists.size());
        PartyTypeContactListTransferCache partyTypeContactListTransferCache = getContactListTransferCaches(userVisit).getPartyTypeContactListTransferCache();
        
        partyTypeContactLists.stream().forEach((partyTypeContactList) -> {
            partyTypeContactListTransfers.add(partyTypeContactListTransferCache.getPartyTypeContactListTransfer(partyTypeContactList));
        });
        
        return partyTypeContactListTransfers;
    }
    
    public List<PartyTypeContactListTransfer> getPartyTypeContactListTransfersByPartyType(UserVisit userVisit, PartyType partyType) {
        return getPartyTypeContactListTransfers(userVisit, getPartyTypeContactListsByPartyType(partyType));
    }
    
    public List<PartyTypeContactListTransfer> getPartyTypeContactListTransfersByContactList(UserVisit userVisit, ContactList contactList) {
        return getPartyTypeContactListTransfers(userVisit, getPartyTypeContactListsByContactList(contactList));
    }
    
    public PartyTypeContactListTransfer getPartyTypeContactListTransfer(UserVisit userVisit, PartyTypeContactList partyTypeContactList) {
        return getContactListTransferCaches(userVisit).getPartyTypeContactListTransferCache().getPartyTypeContactListTransfer(partyTypeContactList);
    }
    
    public void updatePartyTypeContactListFromValue(PartyTypeContactListValue partyTypeContactListValue, BasePK updatedBy) {
        if(partyTypeContactListValue.hasBeenModified()) {
            PartyTypeContactList partyTypeContactList = PartyTypeContactListFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyTypeContactListValue.getPrimaryKey());
            
            partyTypeContactList.setThruTime(session.START_TIME_LONG);
            partyTypeContactList.store();
            
            PartyTypePK partyTypePK = partyTypeContactList.getPartyType().getPrimaryKey(); // Not Updated
            ContactListPK contactListPK = partyTypeContactList.getContactListPK(); // Not Updated
            Boolean addWhenCreated = partyTypeContactListValue.getAddWhenCreated();
            
            partyTypeContactList = PartyTypeContactListFactory.getInstance().create(partyTypePK, contactListPK, addWhenCreated, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEventUsingNames(contactListPK, EventTypes.MODIFY.name(), partyTypeContactList.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deletePartyTypeContactList(PartyTypeContactList partyTypeContactList, BasePK deletedBy) {
        partyTypeContactList.setThruTime(session.START_TIME_LONG);
        partyTypeContactList.store();
        
        sendEventUsingNames(partyTypeContactList.getContactListPK(), EventTypes.MODIFY.name(), partyTypeContactList.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deletePartyTypeContactLists(List<PartyTypeContactList> partyTypeContactLists, BasePK deletedBy) {
        partyTypeContactLists.stream().forEach((partyTypeContactList) -> {
            deletePartyTypeContactList(partyTypeContactList, deletedBy);
        });
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

    public CustomerTypeContactListGroup createCustomerTypeContactListGroup(CustomerType customerType, ContactListGroup contactListGroup,
            Boolean addWhenCreated, BasePK createdBy) {
        CustomerTypeContactListGroup customerTypeContactListGroup = CustomerTypeContactListGroupFactory.getInstance().create(session,
                customerType, contactListGroup, addWhenCreated, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(contactListGroup.getPrimaryKey(), EventTypes.MODIFY.name(), customerTypeContactListGroup.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return customerTypeContactListGroup;
    }

    private static final Map<EntityPermission, String> getCustomerTypeContactListGroupQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM customertypecontactlistgroups "
                + "WHERE cutyclstgrp_cuty_customertypeid = ? AND cutyclstgrp_clstgrp_contactlistgroupid = ? AND cutyclstgrp_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM customertypecontactlistgroups "
                + "WHERE cutyclstgrp_cuty_customertypeid = ? AND cutyclstgrp_clstgrp_contactlistgroupid = ? AND cutyclstgrp_thrutime = ? "
                + "FOR UPDATE");
        getCustomerTypeContactListGroupQueries = Collections.unmodifiableMap(queryMap);
    }

    private CustomerTypeContactListGroup getCustomerTypeContactListGroup(CustomerType customerType, ContactListGroup contactListGroup, EntityPermission entityPermission) {
        return CustomerTypeContactListGroupFactory.getInstance().getEntityFromQuery(entityPermission, getCustomerTypeContactListGroupQueries,
                customerType, contactListGroup, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getCustomerTypeContactListGroupsByCustomerTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM customertypecontactlistgroups, contactlistgroups, contactlistgroupdetails "
                + "WHERE cutyclstgrp_cuty_customertypeid = ? AND cutyclstgrp_thrutime = ? "
                + "AND cutyclstgrp_clstgrp_contactlistgroupid = clstgrp_contactlistgroupid AND clstgrp_lastdetailid = clstgrpdt_contactlistgroupdetailid "
                + "ORDER BY clstgrpdt_sortorder, clstgrpdt_contactlistgroupname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM customertypecontactlistgroups "
                + "WHERE cutyclstgrp_cuty_customertypeid = ? AND cutyclstgrp_thrutime = ? "
                + "FOR UPDATE");
        getCustomerTypeContactListGroupsByCustomerTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CustomerTypeContactListGroup> getCustomerTypeContactListGroupsByCustomerType(CustomerType customerType, EntityPermission entityPermission) {
        return CustomerTypeContactListGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, getCustomerTypeContactListGroupsByCustomerTypeQueries,
                customerType, Session.MAX_TIME);
    }

    public List<CustomerTypeContactListGroup> getCustomerTypeContactListGroupsByCustomerType(CustomerType customerType) {
        return getCustomerTypeContactListGroupsByCustomerType(customerType, EntityPermission.READ_ONLY);
    }

    public List<CustomerTypeContactListGroup> getCustomerTypeContactListGroupsByCustomerTypeForUpdate(CustomerType customerType) {
        return getCustomerTypeContactListGroupsByCustomerType(customerType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getCustomerTypeContactListGroupsByContactListGroupQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM customertypecontactlistgroups, customertypes, customertypedetails "
                + "WHERE cutyclstgrp_clstgrp_contactlistgroupid = ? AND cutyclstgrp_thrutime = ? "
                + "AND cutyclstgrp_cuty_customertypeid = cuty_customertypeid AND cuty_lastdetailid = cutydt_customertypedetailid "
                + "ORDER BY cutydt_sortorder, cutydt_customertypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM customertypecontactlistgroups "
                + "WHERE cutyclstgrp_clstgrp_contactlistgroupid = ? AND cutyclstgrp_thrutime = ? "
                + "FOR UPDATE");
        getCustomerTypeContactListGroupsByContactListGroupQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CustomerTypeContactListGroup> getCustomerTypeContactListGroupsByContactListGroup(ContactListGroup contactListGroup, EntityPermission entityPermission) {
        return CustomerTypeContactListGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, getCustomerTypeContactListGroupsByContactListGroupQueries,
                contactListGroup, Session.MAX_TIME);
    }

    public List<CustomerTypeContactListGroup> getCustomerTypeContactListGroupsByContactListGroup(ContactListGroup contactListGroup) {
        return getCustomerTypeContactListGroupsByContactListGroup(contactListGroup, EntityPermission.READ_ONLY);
    }

    public List<CustomerTypeContactListGroup> getCustomerTypeContactListGroupsByContactListGroupForUpdate(ContactListGroup contactListGroup) {
        return getCustomerTypeContactListGroupsByContactListGroup(contactListGroup, EntityPermission.READ_WRITE);
    }

    public List<CustomerTypeContactListGroupTransfer> getCustomerTypeContactListGroupTransfers(UserVisit userVisit, List<CustomerTypeContactListGroup> customerTypeContactListGroups) {
        List<CustomerTypeContactListGroupTransfer> customerTypeContactListGroupTransfers = new ArrayList<>(customerTypeContactListGroups.size());
        CustomerTypeContactListGroupTransferCache customerTypeContactListGroupTransferCache = getContactListTransferCaches(userVisit).getCustomerTypeContactListGroupTransferCache();

        customerTypeContactListGroups.stream().forEach((customerTypeContactListGroup) -> {
            customerTypeContactListGroupTransfers.add(customerTypeContactListGroupTransferCache.getCustomerTypeContactListGroupTransfer(customerTypeContactListGroup));
        });

        return customerTypeContactListGroupTransfers;
    }

    public List<CustomerTypeContactListGroupTransfer> getCustomerTypeContactListGroupTransfersByCustomerType(UserVisit userVisit, CustomerType customerType) {
        return getCustomerTypeContactListGroupTransfers(userVisit, getCustomerTypeContactListGroupsByCustomerType(customerType));
    }

    public List<CustomerTypeContactListGroupTransfer> getCustomerTypeContactListGroupTransfersByContactListGroup(UserVisit userVisit, ContactListGroup contactListGroup) {
        return getCustomerTypeContactListGroupTransfers(userVisit, getCustomerTypeContactListGroupsByContactListGroup(contactListGroup));
    }

    public CustomerTypeContactListGroupTransfer getCustomerTypeContactListGroupTransfer(UserVisit userVisit, CustomerTypeContactListGroup customerTypeContactListGroup) {
        return getContactListTransferCaches(userVisit).getCustomerTypeContactListGroupTransferCache().getCustomerTypeContactListGroupTransfer(customerTypeContactListGroup);
    }

    public void updateCustomerTypeContactListGroupFromValue(CustomerTypeContactListGroupValue customerTypeContactListGroupValue, BasePK updatedBy) {
        if(customerTypeContactListGroupValue.hasBeenModified()) {
            CustomerTypeContactListGroup customerTypeContactListGroup = CustomerTypeContactListGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     customerTypeContactListGroupValue.getPrimaryKey());

            customerTypeContactListGroup.setThruTime(session.START_TIME_LONG);
            customerTypeContactListGroup.store();

            CustomerTypePK customerTypePK = customerTypeContactListGroup.getCustomerType().getPrimaryKey(); // Not Updated
            ContactListGroupPK contactListGroupPK = customerTypeContactListGroup.getContactListGroupPK(); // Not Updated
            Boolean addWhenCreated = customerTypeContactListGroupValue.getAddWhenCreated();

            customerTypeContactListGroup = CustomerTypeContactListGroupFactory.getInstance().create(customerTypePK, contactListGroupPK, addWhenCreated,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(contactListGroupPK, EventTypes.MODIFY.name(), customerTypeContactListGroup.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteCustomerTypeContactListGroup(CustomerTypeContactListGroup customerTypeContactListGroup, BasePK deletedBy) {
        customerTypeContactListGroup.setThruTime(session.START_TIME_LONG);
        customerTypeContactListGroup.store();

        sendEventUsingNames(customerTypeContactListGroup.getContactListGroupPK(), EventTypes.MODIFY.name(), customerTypeContactListGroup.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }

    public void deleteCustomerTypeContactListGroups(List<CustomerTypeContactListGroup> customerTypeContactListGroups, BasePK deletedBy) {
        customerTypeContactListGroups.stream().forEach((customerTypeContactListGroup) -> {
            deleteCustomerTypeContactListGroup(customerTypeContactListGroup, deletedBy);
        });
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

    public CustomerTypeContactList createCustomerTypeContactList(CustomerType customerType, ContactList contactList, Boolean addWhenCreated, BasePK createdBy) {
        CustomerTypeContactList customerTypeContactList = CustomerTypeContactListFactory.getInstance().create(session, customerType, contactList, addWhenCreated,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(contactList.getPrimaryKey(), EventTypes.MODIFY.name(), customerTypeContactList.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return customerTypeContactList;
    }

    private static final Map<EntityPermission, String> getCustomerTypeContactListQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM customertypecontactlists "
                + "WHERE cutyclst_cuty_customertypeid = ? AND cutyclst_clst_contactlistid = ? AND cutyclst_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM customertypecontactlists "
                + "WHERE cutyclst_cuty_customertypeid = ? AND cutyclst_clst_contactlistid = ? AND cutyclst_thrutime = ? "
                + "FOR UPDATE");
        getCustomerTypeContactListQueries = Collections.unmodifiableMap(queryMap);
    }

    private CustomerTypeContactList getCustomerTypeContactList(CustomerType customerType, ContactList contactList, EntityPermission entityPermission) {
        return CustomerTypeContactListFactory.getInstance().getEntityFromQuery(entityPermission, getCustomerTypeContactListQueries,
                customerType, contactList, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getCustomerTypeContactListsByCustomerTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM customertypecontactlists, contactlists, contactlistdetails "
                + "WHERE cutyclst_cuty_customertypeid = ? AND cutyclst_thrutime = ? "
                + "AND cutyclst_clst_contactlistid = clst_contactlistid AND clst_lastdetailid = clstdt_contactlistdetailid "
                + "ORDER BY clstdt_sortorder, clstdt_contactlistname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM customertypecontactlists "
                + "WHERE cutyclst_cuty_customertypeid = ? AND cutyclst_thrutime = ? "
                + "FOR UPDATE");
        getCustomerTypeContactListsByCustomerTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CustomerTypeContactList> getCustomerTypeContactListsByCustomerType(CustomerType customerType, EntityPermission entityPermission) {
        return CustomerTypeContactListFactory.getInstance().getEntitiesFromQuery(entityPermission, getCustomerTypeContactListsByCustomerTypeQueries,
                customerType, Session.MAX_TIME);
    }

    public List<CustomerTypeContactList> getCustomerTypeContactListsByCustomerType(CustomerType customerType) {
        return getCustomerTypeContactListsByCustomerType(customerType, EntityPermission.READ_ONLY);
    }

    public List<CustomerTypeContactList> getCustomerTypeContactListsByCustomerTypeForUpdate(CustomerType customerType) {
        return getCustomerTypeContactListsByCustomerType(customerType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getCustomerTypeContactListsByContactListQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM customertypecontactlists, customertypes, customertypedetails "
                + "WHERE cutyclst_clst_contactlistid = ? AND cutyclst_thrutime = ? "
                + "AND cutyclst_cuty_customertypeid = cuty_customertypeid AND cuty_lastdetailid = cutydt_customertypedetailid "
                + "ORDER BY cutydt_sortorder, cutydt_customertypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM customertypecontactlists "
                + "WHERE cutyclst_clst_contactlistid = ? AND cutyclst_thrutime = ? "
                + "FOR UPDATE");
        getCustomerTypeContactListsByContactListQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CustomerTypeContactList> getCustomerTypeContactListsByContactList(ContactList contactList, EntityPermission entityPermission) {
        return CustomerTypeContactListFactory.getInstance().getEntitiesFromQuery(entityPermission, getCustomerTypeContactListsByContactListQueries,
                contactList, Session.MAX_TIME);
    }

    public List<CustomerTypeContactList> getCustomerTypeContactListsByContactList(ContactList contactList) {
        return getCustomerTypeContactListsByContactList(contactList, EntityPermission.READ_ONLY);
    }

    public List<CustomerTypeContactList> getCustomerTypeContactListsByContactListForUpdate(ContactList contactList) {
        return getCustomerTypeContactListsByContactList(contactList, EntityPermission.READ_WRITE);
    }

    public List<CustomerTypeContactListTransfer> getCustomerTypeContactListTransfers(UserVisit userVisit, List<CustomerTypeContactList> customerTypeContactLists) {
        List<CustomerTypeContactListTransfer> customerTypeContactListTransfers = new ArrayList<>(customerTypeContactLists.size());
        CustomerTypeContactListTransferCache customerTypeContactListTransferCache = getContactListTransferCaches(userVisit).getCustomerTypeContactListTransferCache();

        customerTypeContactLists.stream().forEach((customerTypeContactList) -> {
            customerTypeContactListTransfers.add(customerTypeContactListTransferCache.getCustomerTypeContactListTransfer(customerTypeContactList));
        });

        return customerTypeContactListTransfers;
    }

    public List<CustomerTypeContactListTransfer> getCustomerTypeContactListTransfersByCustomerType(UserVisit userVisit, CustomerType customerType) {
        return getCustomerTypeContactListTransfers(userVisit, getCustomerTypeContactListsByCustomerType(customerType));
    }

    public List<CustomerTypeContactListTransfer> getCustomerTypeContactListTransfersByContactList(UserVisit userVisit, ContactList contactList) {
        return getCustomerTypeContactListTransfers(userVisit, getCustomerTypeContactListsByContactList(contactList));
    }

    public CustomerTypeContactListTransfer getCustomerTypeContactListTransfer(UserVisit userVisit, CustomerTypeContactList customerTypeContactList) {
        return getContactListTransferCaches(userVisit).getCustomerTypeContactListTransferCache().getCustomerTypeContactListTransfer(customerTypeContactList);
    }

    public void updateCustomerTypeContactListFromValue(CustomerTypeContactListValue customerTypeContactListValue, BasePK updatedBy) {
        if(customerTypeContactListValue.hasBeenModified()) {
            CustomerTypeContactList customerTypeContactList = CustomerTypeContactListFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     customerTypeContactListValue.getPrimaryKey());

            customerTypeContactList.setThruTime(session.START_TIME_LONG);
            customerTypeContactList.store();

            CustomerTypePK customerTypePK = customerTypeContactList.getCustomerType().getPrimaryKey(); // Not Updated
            ContactListPK contactListPK = customerTypeContactList.getContactListPK(); // Not Updated
            Boolean addWhenCreated = customerTypeContactListValue.getAddWhenCreated();

            customerTypeContactList = CustomerTypeContactListFactory.getInstance().create(customerTypePK, contactListPK, addWhenCreated, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEventUsingNames(contactListPK, EventTypes.MODIFY.name(), customerTypeContactList.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteCustomerTypeContactList(CustomerTypeContactList customerTypeContactList, BasePK deletedBy) {
        customerTypeContactList.setThruTime(session.START_TIME_LONG);
        customerTypeContactList.store();

        sendEventUsingNames(customerTypeContactList.getContactListPK(), EventTypes.MODIFY.name(), customerTypeContactList.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }

    public void deleteCustomerTypeContactLists(List<CustomerTypeContactList> customerTypeContactLists, BasePK deletedBy) {
        customerTypeContactLists.stream().forEach((customerTypeContactList) -> {
            deleteCustomerTypeContactList(customerTypeContactList, deletedBy);
        });
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

    public ContactListContactMechanismPurpose createContactListContactMechanismPurpose(ContactList contactList, ContactMechanismPurpose contactMechanismPurpose, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        ContactListContactMechanismPurpose defaultContactListContactMechanismPurpose = getDefaultContactListContactMechanismPurpose(contactList);
        boolean defaultFound = defaultContactListContactMechanismPurpose != null;

        if(defaultFound && isDefault) {
            ContactListContactMechanismPurposeDetailValue defaultContactListContactMechanismPurposeDetailValue = getDefaultContactListContactMechanismPurposeDetailValueForUpdate(contactList);

            defaultContactListContactMechanismPurposeDetailValue.setIsDefault(Boolean.FALSE);
            updateContactListContactMechanismPurposeFromValue(defaultContactListContactMechanismPurposeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        ContactListContactMechanismPurpose contactListContactMechanismPurpose = ContactListContactMechanismPurposeFactory.getInstance().create();
        ContactListContactMechanismPurposeDetail contactListContactMechanismPurposeDetail = ContactListContactMechanismPurposeDetailFactory.getInstance().create(contactListContactMechanismPurpose, contactList, contactMechanismPurpose, isDefault,
                sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        contactListContactMechanismPurpose = ContactListContactMechanismPurposeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contactListContactMechanismPurpose.getPrimaryKey());
        contactListContactMechanismPurpose.setActiveDetail(contactListContactMechanismPurposeDetail);
        contactListContactMechanismPurpose.setLastDetail(contactListContactMechanismPurposeDetail);
        contactListContactMechanismPurpose.store();

        sendEventUsingNames(contactListContactMechanismPurpose.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return contactListContactMechanismPurpose;
    }

    private static final Map<EntityPermission, String> getContactListContactMechanismPurposeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlistcontactmechanismpurposes, contactlistcontactmechanismpurposedetails "
                + "WHERE clstcmpr_activedetailid = clstcmprdt_contactlistcontactmechanismpurposedetailid "
                + "AND clstcmprdt_clst_contactlistid = ? AND clstcmprdt_cmpr_contactmechanismpurposeid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlistcontactmechanismpurposes, contactlistcontactmechanismpurposedetails "
                + "WHERE clstcmpr_activedetailid = clstcmprdt_contactlistcontactmechanismpurposedetailid "
                + "AND clstcmprdt_clst_contactlistid = ? AND clstcmprdt_cmpr_contactmechanismpurposeid = ? "
                + "FOR UPDATE");
        getContactListContactMechanismPurposeQueries = Collections.unmodifiableMap(queryMap);
    }

    private ContactListContactMechanismPurpose getContactListContactMechanismPurpose(ContactList contactList, ContactMechanismPurpose contactMechanismPurpose, EntityPermission entityPermission) {
        return ContactListContactMechanismPurposeFactory.getInstance().getEntityFromQuery(entityPermission, getContactListContactMechanismPurposeQueries,
                contactList, contactMechanismPurpose);
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

    private static final Map<EntityPermission, String> getDefaultContactListContactMechanismPurposeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlistcontactmechanismpurposes, contactlistcontactmechanismpurposedetails "
                + "WHERE clstcmpr_activedetailid = clstcmprdt_contactlistcontactmechanismpurposedetailid "
                + "AND clstcmprdt_clst_contactlistid = ? AND clstcmprdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlistcontactmechanismpurposes, contactlistcontactmechanismpurposedetails "
                + "WHERE clstcmpr_activedetailid = clstcmprdt_contactlistcontactmechanismpurposedetailid "
                + "AND clstcmprdt_clst_contactlistid = ? AND clstcmprdt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultContactListContactMechanismPurposeQueries = Collections.unmodifiableMap(queryMap);
    }

    private ContactListContactMechanismPurpose getDefaultContactListContactMechanismPurpose(ContactList contactList, EntityPermission entityPermission) {
        return ContactListContactMechanismPurposeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultContactListContactMechanismPurposeQueries,
                contactList);
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

    private static final Map<EntityPermission, String> getContactListContactMechanismPurposesByContactListQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlistcontactmechanismpurposes, contactlistcontactmechanismpurposedetails, contactmechanismpurposes "
                + "WHERE clstcmpr_activedetailid = clstcmprdt_contactlistcontactmechanismpurposedetailid AND clstcmprdt_clst_contactlistid = ? "
                + "AND clstcmprdt_cmpr_contactmechanismpurposeid = cmpr_contactmechanismpurposeid "
                + "ORDER BY cmpr_sortorder, cmpr_contactmechanismpurposename "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlistcontactmechanismpurposes, contactlistcontactmechanismpurposedetails "
                + "WHERE clstcmpr_activedetailid = clstcmprdt_contactlistcontactmechanismpurposedetailid AND clstcmprdt_clst_contactlistid = ? "
                + "FOR UPDATE");
        getContactListContactMechanismPurposesByContactListQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ContactListContactMechanismPurpose> getContactListContactMechanismPurposesByContactList(ContactList contactList, EntityPermission entityPermission) {
        return ContactListContactMechanismPurposeFactory.getInstance().getEntitiesFromQuery(entityPermission, getContactListContactMechanismPurposesByContactListQueries,
                contactList);
    }

    public List<ContactListContactMechanismPurpose> getContactListContactMechanismPurposesByContactList(ContactList contactList) {
        return getContactListContactMechanismPurposesByContactList(contactList, EntityPermission.READ_ONLY);
    }

    public List<ContactListContactMechanismPurpose> getContactListContactMechanismPurposesByContactListForUpdate(ContactList contactList) {
        return getContactListContactMechanismPurposesByContactList(contactList, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getContactListContactMechanismPurposesByContactMechanismPurposeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contactlistcontactmechanismpurposes, contactlistcontactmechanismpurposedetails, contactLists, contactlistdetails "
                + "WHERE clstcmpr_activedetailid = clstcmprdt_contactlistcontactmechanismpurposedetailid AND clstcmprdt_cmpr_contactmechanismpurposeid = ? "
                + "AND clstcmprdt_clst_contactlistid = clst_contactlistid AND clst_lastdetailid = clstdt_contactlistdetailid "
                + "ORDER BY clstdt_sortorder, clstdt_contactlistname "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contactlistcontactmechanismpurposes, contactlistcontactmechanismpurposedetails "
                + "WHERE clstcmpr_activedetailid = clstcmprdt_contactlistcontactmechanismpurposedetailid AND clstcmprdt_cmpr_contactmechanismpurposeid = ? "
                + "FOR UPDATE");
        getContactListContactMechanismPurposesByContactMechanismPurposeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ContactListContactMechanismPurpose> getContactListContactMechanismPurposesByContactMechanismPurpose(ContactMechanismPurpose contactMechanismPurpose, EntityPermission entityPermission) {
        return ContactListContactMechanismPurposeFactory.getInstance().getEntitiesFromQuery(entityPermission, getContactListContactMechanismPurposesByContactMechanismPurposeQueries,
                contactMechanismPurpose);
    }

    public List<ContactListContactMechanismPurpose> getContactListContactMechanismPurposesByContactMechanismPurpose(ContactMechanismPurpose contactMechanismPurpose) {
        return getContactListContactMechanismPurposesByContactMechanismPurpose(contactMechanismPurpose, EntityPermission.READ_ONLY);
    }

    public List<ContactListContactMechanismPurpose> getContactListContactMechanismPurposesByContactMechanismPurposeForUpdate(ContactMechanismPurpose contactMechanismPurpose) {
        return getContactListContactMechanismPurposesByContactMechanismPurpose(contactMechanismPurpose, EntityPermission.READ_WRITE);
    }

   public ContactListContactMechanismPurposeTransfer getContactListContactMechanismPurposeTransfer(UserVisit userVisit, ContactListContactMechanismPurpose contactListContactMechanismPurpose) {
        return getContactListTransferCaches(userVisit).getContactListContactMechanismPurposeTransferCache().getContactListContactMechanismPurposeTransfer(contactListContactMechanismPurpose);
    }

    public List<ContactListContactMechanismPurposeTransfer> getContactListContactMechanismPurposeTransfers(List<ContactListContactMechanismPurpose> contactListContactMechanismPurposes, UserVisit userVisit) {
        List<ContactListContactMechanismPurposeTransfer> contactListContactMechanismPurposeTransfers = new ArrayList<>(contactListContactMechanismPurposes.size());
        ContactListContactMechanismPurposeTransferCache contactListContactMechanismPurposeTransferCache = getContactListTransferCaches(userVisit).getContactListContactMechanismPurposeTransferCache();

        contactListContactMechanismPurposes.stream().forEach((contactListContactMechanismPurpose) -> {
            contactListContactMechanismPurposeTransfers.add(contactListContactMechanismPurposeTransferCache.getContactListContactMechanismPurposeTransfer(contactListContactMechanismPurpose));
        });

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
        ContactControl contactControl = (ContactControl)Session.getModelController(ContactControl.class);
        List<ContactListContactMechanismPurpose> contactListContactMechanismPurposes = getContactListContactMechanismPurposesByContactList(contactList);
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

            String label = contactControl.getBestContactMechanismPurposeDescription(contactMechanismPurpose, language);
            String value = contactMechanismPurpose.getContactMechanismPurposeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultContactListContactMechanismPurposeChoice == null? false: defaultContactListContactMechanismPurposeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && contactListContactMechanismPurposeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ContactListContactMechanismPurposeChoicesBean(labels, values, defaultValue);
    }

    private void updateContactListContactMechanismPurposeFromValue(ContactListContactMechanismPurposeDetailValue contactListContactMechanismPurposeDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(contactListContactMechanismPurposeDetailValue.hasBeenModified()) {
            ContactListContactMechanismPurpose contactListContactMechanismPurpose = ContactListContactMechanismPurposeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     contactListContactMechanismPurposeDetailValue.getContactListContactMechanismPurposePK());
            ContactListContactMechanismPurposeDetail contactListContactMechanismPurposeDetail = contactListContactMechanismPurpose.getActiveDetailForUpdate();

            contactListContactMechanismPurposeDetail.setThruTime(session.START_TIME_LONG);
            contactListContactMechanismPurposeDetail.store();

            ContactListContactMechanismPurposePK contactListContactMechanismPurposePK = contactListContactMechanismPurposeDetail.getContactListContactMechanismPurposePK(); // Not updated
            ContactList contactList = contactListContactMechanismPurposeDetail.getContactList();
            ContactListPK contactListPK = contactList.getPrimaryKey(); // Not updated
            ContactMechanismPurposePK contactMechanismPurposePK = contactListContactMechanismPurposeDetail.getContactMechanismPurposePK(); // Not updated
            Boolean isDefault = contactListContactMechanismPurposeDetailValue.getIsDefault();
            Integer sortOrder = contactListContactMechanismPurposeDetailValue.getSortOrder();

            if(checkDefault) {
                ContactListContactMechanismPurpose defaultContactListContactMechanismPurpose = getDefaultContactListContactMechanismPurpose(contactList);
                boolean defaultFound = defaultContactListContactMechanismPurpose != null && !defaultContactListContactMechanismPurpose.equals(contactListContactMechanismPurpose);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ContactListContactMechanismPurposeDetailValue defaultContactListContactMechanismPurposeDetailValue = getDefaultContactListContactMechanismPurposeDetailValueForUpdate(contactList);

                    defaultContactListContactMechanismPurposeDetailValue.setIsDefault(Boolean.FALSE);
                    updateContactListContactMechanismPurposeFromValue(defaultContactListContactMechanismPurposeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            contactListContactMechanismPurposeDetail = ContactListContactMechanismPurposeDetailFactory.getInstance().create(contactListContactMechanismPurposePK, contactListPK, contactMechanismPurposePK, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            contactListContactMechanismPurpose.setActiveDetail(contactListContactMechanismPurposeDetail);
            contactListContactMechanismPurpose.setLastDetail(contactListContactMechanismPurposeDetail);

            sendEventUsingNames(contactListContactMechanismPurposePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateContactListContactMechanismPurposeFromValue(ContactListContactMechanismPurposeDetailValue contactListContactMechanismPurposeDetailValue, BasePK updatedBy) {
        updateContactListContactMechanismPurposeFromValue(contactListContactMechanismPurposeDetailValue, true, updatedBy);
    }

    private void deleteContactListContactMechanismPurpose(ContactListContactMechanismPurpose contactListContactMechanismPurpose, boolean checkDefault, BasePK deletedBy) {
        ContactListContactMechanismPurposeDetail contactListContactMechanismPurposeDetail = contactListContactMechanismPurpose.getLastDetailForUpdate();
        ContactList contactList = contactListContactMechanismPurposeDetail.getContactList();

        clearContactListContactMechanismPurposeFromPartyContactLists(contactListContactMechanismPurpose, deletedBy);
        
        contactListContactMechanismPurposeDetail.setThruTime(session.START_TIME_LONG);
        contactListContactMechanismPurpose.setActiveDetail(null);
        contactListContactMechanismPurpose.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            ContactListContactMechanismPurpose defaultContactListContactMechanismPurpose = getDefaultContactListContactMechanismPurpose(contactList);

            if(defaultContactListContactMechanismPurpose == null) {
                List<ContactListContactMechanismPurpose> contactListContactMechanismPurposes = getContactListContactMechanismPurposesByContactListForUpdate(contactList);

                if(!contactListContactMechanismPurposes.isEmpty()) {
                    Iterator<ContactListContactMechanismPurpose> iter = contactListContactMechanismPurposes.iterator();
                    if(iter.hasNext()) {
                        defaultContactListContactMechanismPurpose = iter.next();
                    }
                    ContactListContactMechanismPurposeDetailValue contactListContactMechanismPurposeDetailValue = defaultContactListContactMechanismPurpose.getLastDetailForUpdate().getContactListContactMechanismPurposeDetailValue().clone();

                    contactListContactMechanismPurposeDetailValue.setIsDefault(Boolean.TRUE);
                    updateContactListContactMechanismPurposeFromValue(contactListContactMechanismPurposeDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(contactListContactMechanismPurpose.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteContactListContactMechanismPurpose(ContactListContactMechanismPurpose contactListContactMechanismPurpose, BasePK deletedBy) {
        deleteContactListContactMechanismPurpose(contactListContactMechanismPurpose, true, deletedBy);
    }

    private void deleteContactListContactMechanismPurposes(List<ContactListContactMechanismPurpose> contactListContactMechanismPurposes, boolean checkDefault, BasePK deletedBy) {
        contactListContactMechanismPurposes.stream().forEach((contactListContactMechanismPurpose) -> {
            deleteContactListContactMechanismPurpose(contactListContactMechanismPurpose, checkDefault, deletedBy);
        });
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

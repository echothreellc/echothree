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

package com.echothree.model.control.payment.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.order.server.control.OrderPaymentPreferenceControl;
import com.echothree.model.control.payment.common.PaymentMethodTypes;
import com.echothree.model.control.payment.common.choice.PartyPaymentMethodChoicesBean;
import com.echothree.model.control.payment.common.transfer.PartyPaymentMethodContactMechanismTransfer;
import com.echothree.model.control.payment.common.transfer.PartyPaymentMethodTransfer;
import com.echothree.model.control.payment.server.transfer.PartyPaymentMethodContactMechanismTransferCache;
import com.echothree.model.control.payment.server.transfer.PartyPaymentMethodTransferCache;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.contact.server.entity.PartyContactMechanismPurpose;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.NameSuffix;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PersonalTitle;
import com.echothree.model.data.payment.common.pk.PartyPaymentMethodPK;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethod;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethodContactMechanism;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethodCreditCard;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethodCreditCardSecurityCode;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.payment.server.factory.PartyPaymentMethodContactMechanismFactory;
import com.echothree.model.data.payment.server.factory.PartyPaymentMethodCreditCardFactory;
import com.echothree.model.data.payment.server.factory.PartyPaymentMethodCreditCardSecurityCodeFactory;
import com.echothree.model.data.payment.server.factory.PartyPaymentMethodDetailFactory;
import com.echothree.model.data.payment.server.factory.PartyPaymentMethodFactory;
import com.echothree.model.data.payment.server.value.PartyPaymentMethodCreditCardSecurityCodeValue;
import com.echothree.model.data.payment.server.value.PartyPaymentMethodCreditCardValue;
import com.echothree.model.data.payment.server.value.PartyPaymentMethodDetailValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.contact.ContactForeignKeys.PARTY_CONTACT_MECHANISM_PURPOSES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.contact.ContactForeignKeys.PARTY_CONTACT_MECHANISM_PURPOSE_DETAILS_CONTACT_MECHANISM_PURPOSE_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTIES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTY_DETAILS_PARTY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PARTY_PAYMENT_METHODS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PARTY_PAYMENT_METHODS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PARTY_PAYMENT_METHOD_CONTACT_MECHANISMS_PARTY_CONTACT_MECHANISM_PURPOSE_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PARTY_PAYMENT_METHOD_CONTACT_MECHANISMS_PARTY_PAYMENT_METHOD_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PARTY_PAYMENT_METHOD_CREDIT_CARDS_PARTY_PAYMENT_METHOD_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PARTY_PAYMENT_METHOD_DETAILS_PARTY_FK;
import static com.echothree.model.jooq.server.tables.contact.ContactMechanismPurposes.ContactMechanismPurposes;
import static com.echothree.model.jooq.server.tables.contact.PartyContactMechanismPurposeDetails.PartyContactMechanismPurposeDetails;
import static com.echothree.model.jooq.server.tables.contact.PartyContactMechanismPurposes.PartyContactMechanismPurposes;
import static com.echothree.model.jooq.server.tables.party.Parties.Parties;
import static com.echothree.model.jooq.server.tables.party.PartyDetails.PartyDetails;
import static com.echothree.model.jooq.server.tables.party.PartyTypes.PartyTypes;
import static com.echothree.model.jooq.server.tables.payment.PartyPaymentMethodContactMechanisms.PartyPaymentMethodContactMechanisms;
import static com.echothree.model.jooq.server.tables.payment.PartyPaymentMethodCreditCardSecurityCodes.PartyPaymentMethodCreditCardSecurityCodes;
import static com.echothree.model.jooq.server.tables.payment.PartyPaymentMethodCreditCards.PartyPaymentMethodCreditCards;
import static com.echothree.model.jooq.server.tables.payment.PartyPaymentMethodDetails.PartyPaymentMethodDetails;
import static com.echothree.model.jooq.server.tables.payment.PartyPaymentMethods.PartyPaymentMethods;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EncryptionUtils;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.HmacSha256Utils;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;

@CommandScope
public class PartyPaymentMethodControl
        extends BaseModelControl {

    @Inject
    private OrderPaymentPreferenceControl orderPaymentPreferenceControl;

    @Inject
    protected SequenceControl sequenceControl;

    @Inject
    private SequenceGeneratorLogic sequenceGeneratorLogic;
    
    @Inject
    EncryptionUtils encryptionUtils;
    
    @Inject
    HmacSha256Utils hmacSha256Utils;

    /** Creates a new instance of PartyPaymentMethodControl */
    protected PartyPaymentMethodControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Party Payment Methods
    // --------------------------------------------------------------------------------

    @Inject
    protected PartyPaymentMethodDetailFactory partyPaymentMethodDetailFactory;

    @Inject
    private PartyPaymentMethodFactory partyPaymentMethodFactory;

    @Inject
    private PartyPaymentMethodTransferCache partyPaymentMethodTransferCache;

    public PartyPaymentMethod createPartyPaymentMethod(Party party, String description, PaymentMethod paymentMethod,
            Boolean deleteWhenUnused, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.PARTY_PAYMENT_METHOD.name());
        var sequence = sequenceControl.getDefaultSequence(sequenceType);
        var partyPaymentMethodName = sequenceGeneratorLogic.getNextSequenceValue(sequence);

        return createPartyPaymentMethod(partyPaymentMethodName, party, description, paymentMethod, deleteWhenUnused, isDefault,
                sortOrder, createdBy);
    }

    public PartyPaymentMethod createPartyPaymentMethod(String partyPaymentMethodName, Party party, String description, PaymentMethod paymentMethod,
            Boolean deleteWhenUnused, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultPartyPaymentMethod = getDefaultPartyPaymentMethod(party);
        var defaultFound = defaultPartyPaymentMethod != null;

        if(defaultFound && isDefault) {
            var defaultPartyPaymentMethodDetailValue = getDefaultPartyPaymentMethodDetailValueForUpdate(party);

            defaultPartyPaymentMethodDetailValue.setIsDefault(false);
            updatePartyPaymentMethodFromValue(defaultPartyPaymentMethodDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var partyPaymentMethod = partyPaymentMethodFactory.create();
        var partyPaymentMethodDetail = partyPaymentMethodDetailFactory.create(partyPaymentMethod, partyPaymentMethodName,
                party, description, paymentMethod, deleteWhenUnused, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        partyPaymentMethod = partyPaymentMethodFactory.getEntityFromPK(EntityPermission.READ_WRITE, partyPaymentMethod.getPrimaryKey());
        partyPaymentMethod.setActiveDetail(partyPaymentMethodDetail);
        partyPaymentMethod.setLastDetail(partyPaymentMethodDetail);
        partyPaymentMethod.store();

        sendEvent(partyPaymentMethod.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return partyPaymentMethod;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PartyPaymentMethod */
    public PartyPaymentMethod getPartyPaymentMethodByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new PartyPaymentMethodPK(entityInstance.getEntityUniqueId());

        return partyPaymentMethodFactory.getEntityFromPK(entityPermission, pk);
    }

    public PartyPaymentMethod getPartyPaymentMethodByEntityInstance(EntityInstance entityInstance) {
        return getPartyPaymentMethodByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PartyPaymentMethod getPartyPaymentMethodByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getPartyPaymentMethodByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countPartyPaymentMethodsByParty(final Party party) {
        return session.getDslContext()
                .selectCount()
                .from(PartyPaymentMethods)
                .join(PartyPaymentMethodDetails).onKey(PARTY_PAYMENT_METHODS_ACTIVE_DETAIL_FK)
                .where(PartyPaymentMethodDetails.PARTY.eq(party.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyPaymentMethodsByPaymentMethod(final PaymentMethod paymentMethod) {
        return session.getDslContext()
                .selectCount()
                .from(PartyPaymentMethods)
                .join(PartyPaymentMethodDetails).onKey(PARTY_PAYMENT_METHODS_ACTIVE_DETAIL_FK)
                .where(PartyPaymentMethodDetails.PAYMENT_METHOD.eq(paymentMethod.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public PartyPaymentMethod getPartyPaymentMethodByName(String partyPaymentMethodName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyPaymentMethods.fields())
                .from(PartyPaymentMethods)
                .join(PartyPaymentMethodDetails).onKey(PARTY_PAYMENT_METHODS_ACTIVE_DETAIL_FK)
                .where(PartyPaymentMethodDetails.PARTY_PAYMENT_METHOD_NAME.eq(partyPaymentMethodName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyPaymentMethodFactory.getEntityFromQuery(entityPermission, query);
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

    private PartyPaymentMethod getDefaultPartyPaymentMethod(Party party, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyPaymentMethods.fields())
                .from(PartyPaymentMethods)
                .join(PartyPaymentMethodDetails).onKey(PARTY_PAYMENT_METHODS_ACTIVE_DETAIL_FK)
                .where(PartyPaymentMethodDetails.PARTY.eq(party.getPrimaryKey()),
                        PartyPaymentMethodDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyPaymentMethodFactory.getEntityFromQuery(entityPermission, query);
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

    private List<PartyPaymentMethod> getPartyPaymentMethodsByParty(Party party, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyPaymentMethods.fields())
                .from(PartyPaymentMethods)
                .join(PartyPaymentMethodDetails).onKey(PARTY_PAYMENT_METHODS_ACTIVE_DETAIL_FK)
                .where(PartyPaymentMethodDetails.PARTY.eq(party.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PartyPaymentMethodDetails.SORT_ORDER, PartyPaymentMethodDetails.PARTY_PAYMENT_METHOD_NAME),
                    PartyPaymentMethodFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyPaymentMethodFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyPaymentMethod> getPartyPaymentMethodsByParty(Party party) {
        return getPartyPaymentMethodsByParty(party, EntityPermission.READ_ONLY);
    }

    public List<PartyPaymentMethod> getPartyPaymentMethodsByPartyForUpdate(Party party) {
        return getPartyPaymentMethodsByParty(party, EntityPermission.READ_WRITE);
    }

    private List<PartyPaymentMethod> getPartyPaymentMethodsByPaymentMethod(PaymentMethod paymentMethod, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyPaymentMethods.fields())
                .from(PartyPaymentMethods)
                .join(PartyPaymentMethodDetails).onKey(PARTY_PAYMENT_METHODS_ACTIVE_DETAIL_FK)
                .where(PartyPaymentMethodDetails.PAYMENT_METHOD.eq(paymentMethod.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PartyPaymentMethodDetails.SORT_ORDER, PartyPaymentMethodDetails.PARTY_PAYMENT_METHOD_NAME),
                    PartyPaymentMethodFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyPaymentMethodFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyPaymentMethod> getPartyPaymentMethodsByPaymentMethod(PaymentMethod paymentMethod) {
        return getPartyPaymentMethodsByPaymentMethod(paymentMethod, EntityPermission.READ_ONLY);
    }

    public List<PartyPaymentMethod> getPartyPaymentMethodsByPaymentMethodForUpdate(PaymentMethod paymentMethod) {
        return getPartyPaymentMethodsByPaymentMethod(paymentMethod, EntityPermission.READ_WRITE);
    }

    public PartyPaymentMethodTransfer getPartyPaymentMethodTransfer(UserVisit userVisit, PartyPaymentMethod partyPaymentMethod) {
        return partyPaymentMethodTransferCache.getTransfer(userVisit, partyPaymentMethod);
    }

    public List<PartyPaymentMethodTransfer> getPartyPaymentMethodTransfers(UserVisit userVisit, Collection<PartyPaymentMethod> partyPaymentMethods) {
        List<PartyPaymentMethodTransfer> partyPaymentMethodTransfers = new ArrayList<>(partyPaymentMethods.size());

        partyPaymentMethods.forEach((partyPaymentMethod) ->
                partyPaymentMethodTransfers.add(partyPaymentMethodTransferCache.getTransfer(userVisit, partyPaymentMethod))
        );

        return partyPaymentMethodTransfers;
    }

    public List<PartyPaymentMethodTransfer> getPartyPaymentMethodTransfersByParty(UserVisit userVisit, Party party) {
        return getPartyPaymentMethodTransfers(userVisit, getPartyPaymentMethodsByParty(party));
    }

    public PartyPaymentMethodChoicesBean getPartyPaymentMethodChoices(String defaultPartyPaymentMethodChoice, Language language, boolean allowNullChoice,
            Party party) {
        var partyPaymentMethods = getPartyPaymentMethodsByParty(party);
        var size = partyPaymentMethods.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultPartyPaymentMethodChoice == null) {
                defaultValue = "";
            }
        }

        for(var partyPaymentMethod : partyPaymentMethods) {
            var partyPaymentMethodDetail = partyPaymentMethod.getLastDetail();

            var label = partyPaymentMethodDetail.getDescription();
            var value = partyPaymentMethodDetail.getPartyPaymentMethodName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultPartyPaymentMethodChoice != null && defaultPartyPaymentMethodChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && partyPaymentMethodDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new PartyPaymentMethodChoicesBean(labels, values, defaultValue);
    }

    private void updatePartyPaymentMethodFromValue(PartyPaymentMethodDetailValue partyPaymentMethodDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(partyPaymentMethodDetailValue.hasBeenModified()) {
            var partyPaymentMethod = partyPaymentMethodFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     partyPaymentMethodDetailValue.getPartyPaymentMethodPK());
            var partyPaymentMethodDetail = partyPaymentMethod.getActiveDetailForUpdate();

            partyPaymentMethodDetail.setThruTime(session.getStartTime());
            partyPaymentMethodDetail.store();

            var partyPaymentMethodPK = partyPaymentMethodDetail.getPartyPaymentMethodPK(); // Not updated
            var partyPaymentMethodName = partyPaymentMethodDetailValue.getPartyPaymentMethodName();
            var party = partyPaymentMethodDetail.getParty(); // Not updated
            var description = partyPaymentMethodDetailValue.getDescription();
            var paymentMethodPK = partyPaymentMethodDetail.getPaymentMethodPK(); // Not updated
            var deleteWhenUnused = partyPaymentMethodDetailValue.getDeleteWhenUnused();
            var isDefault = partyPaymentMethodDetailValue.getIsDefault();
            var sortOrder = partyPaymentMethodDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultPartyPaymentMethod = getDefaultPartyPaymentMethod(party);
                var defaultFound = defaultPartyPaymentMethod != null && !defaultPartyPaymentMethod.equals(partyPaymentMethod);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPartyPaymentMethodDetailValue = getDefaultPartyPaymentMethodDetailValueForUpdate(party);

                    defaultPartyPaymentMethodDetailValue.setIsDefault(false);
                    updatePartyPaymentMethodFromValue(defaultPartyPaymentMethodDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            partyPaymentMethodDetail = partyPaymentMethodDetailFactory.create(partyPaymentMethodPK, partyPaymentMethodName, party.getPrimaryKey(),
                    description, paymentMethodPK, deleteWhenUnused, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            partyPaymentMethod.setActiveDetail(partyPaymentMethodDetail);
            partyPaymentMethod.setLastDetail(partyPaymentMethodDetail);

            sendEvent(partyPaymentMethodPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updatePartyPaymentMethodFromValue(PartyPaymentMethodDetailValue partyPaymentMethodDetailValue, BasePK updatedBy) {
        updatePartyPaymentMethodFromValue(partyPaymentMethodDetailValue, true, updatedBy);
    }

    public void deletePartyPaymentMethod(PartyPaymentMethod partyPaymentMethod, BasePK deletedBy) {

        orderPaymentPreferenceControl.deleteOrderPaymentPreferencesByPartyPaymentMethod(partyPaymentMethod, deletedBy);
        
        var partyPaymentMethodDetail = partyPaymentMethod.getLastDetailForUpdate();
        partyPaymentMethodDetail.setThruTime(session.getStartTime());
        partyPaymentMethod.setActiveDetail(null);
        partyPaymentMethod.store();

        var paymentMethodTypeName = partyPaymentMethodDetail.getPaymentMethod().getLastDetail().getPaymentMethodType().getLastDetail().getPaymentMethodTypeName();
        if(paymentMethodTypeName.equals(PaymentMethodTypes.CREDIT_CARD.name())) {
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
                var partyPaymentMethodDetailValue = Objects.requireNonNull(defaultPartyPaymentMethod).getLastDetailForUpdate().getPartyPaymentMethodDetailValue().clone();

                partyPaymentMethodDetailValue.setIsDefault(true);
                updatePartyPaymentMethodFromValue(partyPaymentMethodDetailValue, false, deletedBy);
            }
        }

        sendEvent(partyPaymentMethod.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deletePartyPaymentMethods(List<PartyPaymentMethod> partyPaymentMethods, BasePK deletedBy) {
        partyPaymentMethods.forEach((partyPaymentMethod) -> 
                deletePartyPaymentMethod(partyPaymentMethod, deletedBy)
        );
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

    @Inject
    private PartyPaymentMethodCreditCardFactory partyPaymentMethodCreditCardFactory;

    public PartyPaymentMethodCreditCard createPartyPaymentMethodCreditCard(PartyPaymentMethod partyPaymentMethod, String number,
            Integer expirationMonth, Integer expirationYear, PersonalTitle personalTitle, String firstName, String firstNameSdx,
            String middleName, String middleNameSdx, String lastName, String lastNameSdx, NameSuffix nameSuffix, String name,
            PartyContactMechanism billingPartyContactMechanism, String issuerName, PartyContactMechanism issuerPartyContactMechanism,
            BasePK createdBy) {
        var initializationVector = encryptionUtils.generateRowInitializationVector();
        var numberHash = hmacSha256Utils.hash(partyPaymentMethodCreditCardFactory, number);
        var partyPaymentMethodCreditCard = partyPaymentMethodCreditCardFactory.create(
                partyPaymentMethod, encodePartyPaymentMethodCreditCardNumber(initializationVector, number), numberHash, expirationMonth, expirationYear, personalTitle,
                firstName, firstNameSdx, middleName, middleNameSdx, lastName, lastNameSdx, nameSuffix, name, billingPartyContactMechanism,
                issuerName, issuerPartyContactMechanism, initializationVector, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(partyPaymentMethod.getPrimaryKey(), EventTypes.MODIFY,
                partyPaymentMethodCreditCard.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyPaymentMethodCreditCard;
    }

    public long countPartyPaymentMethodCreditCardsByPersonalTitle(PersonalTitle personalTitle) {
        return session.getDslContext()
                .selectCount()
                .from(PartyPaymentMethodCreditCards)
                .where(PartyPaymentMethodCreditCards.PERSONAL_TITLE.eq(personalTitle.getPrimaryKey()),
                        PartyPaymentMethodCreditCards.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyPaymentMethodCreditCardsByNameSuffix(NameSuffix nameSuffix) {
        return session.getDslContext()
                .selectCount()
                .from(PartyPaymentMethodCreditCards)
                .where(PartyPaymentMethodCreditCards.NAME_SUFFIX.eq(nameSuffix.getPrimaryKey()),
                        PartyPaymentMethodCreditCards.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyPaymentMethodCreditCardsByBillingPartyContactMechanism(PartyContactMechanism billingPartyContactMechanism) {
        return session.getDslContext()
                .selectCount()
                .from(PartyPaymentMethodCreditCards)
                .where(PartyPaymentMethodCreditCards.BILLING_PARTY_CONTACT_MECHANISM.eq(billingPartyContactMechanism.getPrimaryKey()),
                        PartyPaymentMethodCreditCards.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyPaymentMethodCreditCardsByIssuerPartyContactMechanism(PartyContactMechanism issuerPartyContactMechanism) {
        return session.getDslContext()
                .selectCount()
                .from(PartyPaymentMethodCreditCards)
                .where(PartyPaymentMethodCreditCards.ISSUER_PARTY_CONTACT_MECHANISM.eq(issuerPartyContactMechanism.getPrimaryKey()),
                        PartyPaymentMethodCreditCards.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PartyPaymentMethodCreditCard getPartyPaymentMethodCreditCard(PartyPaymentMethod partyPaymentMethod,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyPaymentMethodCreditCards.fields())
                .from(PartyPaymentMethodCreditCards)
                .where(PartyPaymentMethodCreditCards.PARTY_PAYMENT_METHOD.eq(partyPaymentMethod.getPrimaryKey()),
                        PartyPaymentMethodCreditCards.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyPaymentMethodCreditCardFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public PartyPaymentMethodCreditCard getPartyPaymentMethodCreditCard(PartyPaymentMethod partyPaymentMethod) {
        return getPartyPaymentMethodCreditCard(partyPaymentMethod, EntityPermission.READ_ONLY);
    }
    
    public PartyPaymentMethodCreditCard getPartyPaymentMethodCreditCardForUpdate(PartyPaymentMethod partyPaymentMethod) {
        return getPartyPaymentMethodCreditCard(partyPaymentMethod, EntityPermission.READ_WRITE);
    }
    
    private List<PartyPaymentMethodCreditCard> getPartyPaymentMethodCreditCardsByBillingPartyContactMechanism(PartyContactMechanism billingPartyContactMechanism,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyPaymentMethodCreditCards.fields())
                    .from(PartyPaymentMethodCreditCards)
                    .join(PartyPaymentMethods).onKey(PARTY_PAYMENT_METHOD_CREDIT_CARDS_PARTY_PAYMENT_METHOD_FK)
                    .join(PartyPaymentMethodDetails).onKey(PARTY_PAYMENT_METHODS_LAST_DETAIL_FK)
                    .join(Parties).onKey(PARTY_PAYMENT_METHOD_DETAILS_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .join(PartyTypes).onKey(PARTY_DETAILS_PARTY_TYPE_FK)
                    .where(PartyPaymentMethodCreditCards.BILLING_PARTY_CONTACT_MECHANISM.eq(billingPartyContactMechanism.getPrimaryKey()),
                            PartyPaymentMethodCreditCards.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyPaymentMethodDetails.PARTY_PAYMENT_METHOD_NAME, PartyDetails.PARTY_NAME, PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME),
                    PartyPaymentMethodCreditCardFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyPaymentMethodCreditCards.fields())
                    .from(PartyPaymentMethodCreditCards)
                    .where(PartyPaymentMethodCreditCards.BILLING_PARTY_CONTACT_MECHANISM.eq(billingPartyContactMechanism.getPrimaryKey()),
                            PartyPaymentMethodCreditCards.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyPaymentMethodCreditCardFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartyPaymentMethodCreditCard> getPartyPaymentMethodCreditCardsByBillingPartyContactMechanism(PartyContactMechanism billingPartyContactMechanism) {
        return getPartyPaymentMethodCreditCardsByBillingPartyContactMechanism(billingPartyContactMechanism, EntityPermission.READ_ONLY);
    }
    
    public List<PartyPaymentMethodCreditCard> getPartyPaymentMethodCreditCardsByBillingPartyContactMechanismForUpdate(PartyContactMechanism billingPartyContactMechanism) {
        return getPartyPaymentMethodCreditCardsByBillingPartyContactMechanism(billingPartyContactMechanism, EntityPermission.READ_WRITE);
    }
    
    private List<PartyPaymentMethodCreditCard> getPartyPaymentMethodCreditCardsByIssuerPartyContactMechanism(PartyContactMechanism issuerPartyContactMechanism,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyPaymentMethodCreditCards.fields())
                    .from(PartyPaymentMethodCreditCards)
                    .join(PartyPaymentMethods).onKey(PARTY_PAYMENT_METHOD_CREDIT_CARDS_PARTY_PAYMENT_METHOD_FK)
                    .join(PartyPaymentMethodDetails).onKey(PARTY_PAYMENT_METHODS_LAST_DETAIL_FK)
                    .join(Parties).onKey(PARTY_PAYMENT_METHOD_DETAILS_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .join(PartyTypes).onKey(PARTY_DETAILS_PARTY_TYPE_FK)
                    .where(PartyPaymentMethodCreditCards.ISSUER_PARTY_CONTACT_MECHANISM.eq(issuerPartyContactMechanism.getPrimaryKey()),
                            PartyPaymentMethodCreditCards.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyPaymentMethodDetails.PARTY_PAYMENT_METHOD_NAME, PartyDetails.PARTY_NAME, PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME),
                    PartyPaymentMethodCreditCardFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyPaymentMethodCreditCards.fields())
                    .from(PartyPaymentMethodCreditCards)
                    .where(PartyPaymentMethodCreditCards.ISSUER_PARTY_CONTACT_MECHANISM.eq(issuerPartyContactMechanism.getPrimaryKey()),
                            PartyPaymentMethodCreditCards.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyPaymentMethodCreditCardFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartyPaymentMethodCreditCard> getPartyPaymentMethodCreditCardsByIssuerPartyContactMechanism(PartyContactMechanism issuerPartyContactMechanism) {
        return getPartyPaymentMethodCreditCardsByIssuerPartyContactMechanism(issuerPartyContactMechanism, EntityPermission.READ_ONLY);
    }
    
    public List<PartyPaymentMethodCreditCard> getPartyPaymentMethodCreditCardsByIssuerPartyContactMechanismForUpdate(PartyContactMechanism issuerPartyContactMechanism) {
        return getPartyPaymentMethodCreditCardsByIssuerPartyContactMechanism(issuerPartyContactMechanism, EntityPermission.READ_WRITE);
    }
    
    public PartyPaymentMethodCreditCardValue getPartyPaymentMethodCreditCardValueForUpdate(PartyPaymentMethod partyPaymentMethod) {
        var partyPaymentMethodCreditCard = getPartyPaymentMethodCreditCardForUpdate(partyPaymentMethod);
        
        return partyPaymentMethodCreditCard == null? null: partyPaymentMethodCreditCard.getPartyPaymentMethodCreditCardValue().clone();
    }
    
    public String encodePartyPaymentMethodCreditCardNumber(PartyPaymentMethodCreditCard partyPaymentMethodCreditCard, String number) {
        return encryptionUtils.encrypt(partyPaymentMethodCreditCardFactory,
                partyPaymentMethodCreditCard.getInitializationVector(), number);
    }

    public String encodePartyPaymentMethodCreditCardNumber(String initializationVector, String number) {
        return encryptionUtils.encrypt(partyPaymentMethodCreditCardFactory,
                initializationVector, number);
    }

    public String decodePartyPaymentMethodCreditCardNumber(PartyPaymentMethodCreditCard partyPaymentMethodCreditCard) {
        return encryptionUtils.decrypt(partyPaymentMethodCreditCardFactory,
                partyPaymentMethodCreditCard.getInitializationVector(), partyPaymentMethodCreditCard.getNumber());
    }

    public String decodePartyPaymentMethodCreditCardNumber(String initializationVector, String number) {
        return encryptionUtils.decrypt(partyPaymentMethodCreditCardFactory,
                initializationVector, number);
    }

    public void setPartyPaymentMethodCreditCardNumber(PartyPaymentMethodCreditCard partyPaymentMethodCreditCard, String number) {
        partyPaymentMethodCreditCard.setNumber(encodePartyPaymentMethodCreditCardNumber(partyPaymentMethodCreditCard, number));
    }
    
    public void updatePartyPaymentMethodCreditCardFromValue(PartyPaymentMethodCreditCardValue partyPaymentMethodCreditCardValue,
            BasePK updatedBy) {
        if(partyPaymentMethodCreditCardValue.hasBeenModified()) {
            var partyPaymentMethodCreditCard = partyPaymentMethodCreditCardFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     partyPaymentMethodCreditCardValue.getPrimaryKey());
            
            partyPaymentMethodCreditCard.setThruTime(session.getStartTime());
            partyPaymentMethodCreditCard.store();

            // Always generate a new initialization vector on updates.
            var decodedNumber = decodePartyPaymentMethodCreditCardNumber(partyPaymentMethodCreditCardValue.getInitializationVector(),
                    partyPaymentMethodCreditCardValue.getNumber());
            var numberHash = hmacSha256Utils.hash(partyPaymentMethodCreditCardFactory, decodedNumber);
            var initializationVector = encryptionUtils.generateRowInitializationVector();

            var partyPaymentMethodPK = partyPaymentMethodCreditCard.getPartyPaymentMethodPK(); // Not updated
            var number = encodePartyPaymentMethodCreditCardNumber(initializationVector, decodedNumber);
            var expirationMonth = partyPaymentMethodCreditCardValue.getExpirationMonth();
            var expirationYear = partyPaymentMethodCreditCardValue.getExpirationYear();
            var personalTitlePK = partyPaymentMethodCreditCardValue.getPersonalTitlePK();
            var firstName = partyPaymentMethodCreditCardValue.getFirstName();
            var firstNameSdx = partyPaymentMethodCreditCardValue.getFirstNameSdx();
            var middleName = partyPaymentMethodCreditCardValue.getMiddleName();
            var middleNameSdx = partyPaymentMethodCreditCardValue.getMiddleNameSdx();
            var lastName = partyPaymentMethodCreditCardValue.getLastName();
            var lastNameSdx = partyPaymentMethodCreditCardValue.getLastNameSdx();
            var nameSuffixPK = partyPaymentMethodCreditCardValue.getNameSuffixPK();
            var name = partyPaymentMethodCreditCardValue.getName();
            var billingPartyContactMechanismPK = partyPaymentMethodCreditCardValue.getBillingPartyContactMechanismPK();
            var issuerName = partyPaymentMethodCreditCardValue.getIssuerName();
            var issuerPartyContactMechanismPK = partyPaymentMethodCreditCardValue.getIssuerPartyContactMechanismPK();

            partyPaymentMethodCreditCard = partyPaymentMethodCreditCardFactory.create(partyPaymentMethodPK, number, numberHash, expirationMonth,
                    expirationYear, personalTitlePK, firstName, firstNameSdx, middleName, middleNameSdx, lastName, lastNameSdx, nameSuffixPK, name,
                    billingPartyContactMechanismPK, issuerName, issuerPartyContactMechanismPK, initializationVector, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(partyPaymentMethodPK, EventTypes.MODIFY, partyPaymentMethodCreditCard.getPrimaryKey(),
                    EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePartyPaymentMethodCreditCard(PartyPaymentMethodCreditCard partyPaymentMethodCreditCard, BasePK deletedBy) {
        partyPaymentMethodCreditCard.setThruTime(session.getStartTime());
        
        sendEvent(partyPaymentMethodCreditCard.getPartyPaymentMethodPK(), EventTypes.MODIFY, partyPaymentMethodCreditCard.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deletePartyPaymentMethodCreditCards(List<PartyPaymentMethodCreditCard> partyPaymentMethodCreditCards, BasePK deletedBy) {
        partyPaymentMethodCreditCards.forEach((partyPaymentMethodCreditCard) -> 
                deletePartyPaymentMethodCreditCard(partyPaymentMethodCreditCard, deletedBy)
        );
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

    @Inject
    private PartyPaymentMethodCreditCardSecurityCodeFactory partyPaymentMethodCreditCardSecurityCodeFactory;

    public PartyPaymentMethodCreditCardSecurityCode createPartyPaymentMethodCreditCardSecurityCode(PartyPaymentMethod partyPaymentMethod,
            String securityCode, BasePK createdBy) {
        var initializationVector = encryptionUtils.generateRowInitializationVector();
        var partyPaymentMethodCreditCardSecurityCode = partyPaymentMethodCreditCardSecurityCodeFactory.create(
                partyPaymentMethod, encodePartyPaymentMethodCreditCardSecurityCodeSecurityCode(initializationVector, securityCode), initializationVector, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(partyPaymentMethod.getPrimaryKey(), EventTypes.MODIFY,
                partyPaymentMethodCreditCardSecurityCode.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyPaymentMethodCreditCardSecurityCode;
    }
    
    private PartyPaymentMethodCreditCardSecurityCode getPartyPaymentMethodCreditCardSecurityCode(PartyPaymentMethod partyPaymentMethod,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyPaymentMethodCreditCardSecurityCodes.fields())
                .from(PartyPaymentMethodCreditCardSecurityCodes)
                .where(PartyPaymentMethodCreditCardSecurityCodes.PARTY_PAYMENT_METHOD.eq(partyPaymentMethod.getPrimaryKey()),
                        PartyPaymentMethodCreditCardSecurityCodes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyPaymentMethodCreditCardSecurityCodeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public PartyPaymentMethodCreditCardSecurityCode getPartyPaymentMethodCreditCardSecurityCode(PartyPaymentMethod partyPaymentMethod) {
        return getPartyPaymentMethodCreditCardSecurityCode(partyPaymentMethod, EntityPermission.READ_ONLY);
    }
    
    public PartyPaymentMethodCreditCardSecurityCode getPartyPaymentMethodCreditCardSecurityCodeForUpdate(PartyPaymentMethod partyPaymentMethod) {
        return getPartyPaymentMethodCreditCardSecurityCode(partyPaymentMethod, EntityPermission.READ_WRITE);
    }
    
    public PartyPaymentMethodCreditCardSecurityCodeValue getPartyPaymentMethodCreditCardSecurityCodeValueForUpdate(PartyPaymentMethod partyPaymentMethod) {
        var partyPaymentMethodCreditCardSecurityCode = getPartyPaymentMethodCreditCardSecurityCodeForUpdate(partyPaymentMethod);
        
        return partyPaymentMethodCreditCardSecurityCode == null? null: partyPaymentMethodCreditCardSecurityCode.getPartyPaymentMethodCreditCardSecurityCodeValue().clone();
    }
    
    public String encodePartyPaymentMethodCreditCardSecurityCodeSecurityCode(PartyPaymentMethodCreditCardSecurityCode partyPaymentMethodCreditCardSecurityCode, String securityCode) {
        return encryptionUtils.encrypt(partyPaymentMethodCreditCardSecurityCodeFactory,
                partyPaymentMethodCreditCardSecurityCode.getInitializationVector(), securityCode);
    }

    public String encodePartyPaymentMethodCreditCardSecurityCodeSecurityCode(String initializationVector, String securityCode) {
        return encryptionUtils.encrypt(partyPaymentMethodCreditCardSecurityCodeFactory,
                initializationVector, securityCode);
    }

    public String decodePartyPaymentMethodCreditCardSecurityCodeSecurityCode(PartyPaymentMethodCreditCardSecurityCode partyPaymentMethodCreditCardSecurityCode) {
        return encryptionUtils.decrypt(partyPaymentMethodCreditCardSecurityCodeFactory,
                partyPaymentMethodCreditCardSecurityCode.getInitializationVector(),
                partyPaymentMethodCreditCardSecurityCode.getSecurityCode());
    }

    public String decodePartyPaymentMethodCreditCardSecurityCodeSecurityCode(String initializationVector, String securityCode) {
        return encryptionUtils.decrypt(partyPaymentMethodCreditCardSecurityCodeFactory,
                initializationVector, securityCode);
    }

    public void setPartyPaymentMethodCreditCardSecurityCodeSecurityCode(PartyPaymentMethodCreditCardSecurityCode partyPaymentMethodCreditCardSecurityCode,
            String securityCode) {
        partyPaymentMethodCreditCardSecurityCode.setSecurityCode(encodePartyPaymentMethodCreditCardSecurityCodeSecurityCode(partyPaymentMethodCreditCardSecurityCode, securityCode));
    }
    
    public void updatePartyPaymentMethodCreditCardSecurityCodeFromValue(PartyPaymentMethodCreditCardSecurityCodeValue partyPaymentMethodCreditCardSecurityCodeValue,
            BasePK updatedBy) {
        if(partyPaymentMethodCreditCardSecurityCodeValue.hasBeenModified()) {
            var partyPaymentMethodCreditCardSecurityCode = partyPaymentMethodCreditCardSecurityCodeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     partyPaymentMethodCreditCardSecurityCodeValue.getPrimaryKey());
            
            partyPaymentMethodCreditCardSecurityCode.setThruTime(session.getStartTime());
            partyPaymentMethodCreditCardSecurityCode.store();

            // Always generate a new initialization vector on updates.
            var decodedSecurityCode = decodePartyPaymentMethodCreditCardSecurityCodeSecurityCode(partyPaymentMethodCreditCardSecurityCodeValue.getInitializationVector(),
                    partyPaymentMethodCreditCardSecurityCodeValue.getSecurityCode());
            var initializationVector = encryptionUtils.generateRowInitializationVector();

            var partyPaymentMethodPK = partyPaymentMethodCreditCardSecurityCode.getPartyPaymentMethodPK(); // Not updated
            var securityCode = encodePartyPaymentMethodCreditCardSecurityCodeSecurityCode(initializationVector, decodedSecurityCode);

            partyPaymentMethodCreditCardSecurityCode = partyPaymentMethodCreditCardSecurityCodeFactory.create(
                    partyPaymentMethodPK, securityCode, initializationVector, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(partyPaymentMethodPK, EventTypes.MODIFY, partyPaymentMethodCreditCardSecurityCode.getPrimaryKey(),
                    EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePartyPaymentMethodCreditCardSecurityCode(PartyPaymentMethodCreditCardSecurityCode partyPaymentMethodCreditCardSecurityCode,
            BasePK deletedBy) {
        partyPaymentMethodCreditCardSecurityCode.setThruTime(session.getStartTime());
        
        sendEvent(partyPaymentMethodCreditCardSecurityCode.getPartyPaymentMethodPK(), EventTypes.MODIFY,
                partyPaymentMethodCreditCardSecurityCode.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Payment Method Contact Mechanisms
    // --------------------------------------------------------------------------------

    @Inject
    private PartyPaymentMethodContactMechanismFactory partyPaymentMethodContactMechanismFactory;

    @Inject
    private PartyPaymentMethodContactMechanismTransferCache partyPaymentMethodContactMechanismTransferCache;

    public PartyPaymentMethodContactMechanism createPartyPaymentMethodContactMechanism(PartyPaymentMethod partyPaymentMethod,
            PartyContactMechanismPurpose partyContactMechanismPurpose, BasePK createdBy) {
        var partyPaymentMethodContactMechanism = partyPaymentMethodContactMechanismFactory.create(
                partyPaymentMethod, partyContactMechanismPurpose, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(partyPaymentMethod.getPrimaryKey(), EventTypes.MODIFY, partyPaymentMethodContactMechanism.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyPaymentMethodContactMechanism;
    }
    
    private PartyPaymentMethodContactMechanism getPartyPaymentMethodContactMechanism(PartyPaymentMethod partyPaymentMethod,
            PartyContactMechanismPurpose partyContactMechanismPurpose, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyPaymentMethodContactMechanisms.fields())
                .from(PartyPaymentMethodContactMechanisms)
                .where(PartyPaymentMethodContactMechanisms.PARTY_PAYMENT_METHOD.eq(partyPaymentMethod.getPrimaryKey()),
                        PartyPaymentMethodContactMechanisms.PARTY_CONTACT_MECHANISM_PURPOSE.eq(partyContactMechanismPurpose.getPrimaryKey()),
                        PartyPaymentMethodContactMechanisms.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyPaymentMethodContactMechanismFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(PartyPaymentMethodContactMechanisms.fields())
                .from(PartyPaymentMethodContactMechanisms)
                .where(PartyPaymentMethodContactMechanisms.PARTY_PAYMENT_METHOD.eq(partyPaymentMethod.getPrimaryKey()),
                        PartyPaymentMethodContactMechanisms.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyPaymentMethodContactMechanisms.fields())
                    .from(PartyPaymentMethodContactMechanisms)
                    .join(PartyContactMechanismPurposes).onKey(PARTY_PAYMENT_METHOD_CONTACT_MECHANISMS_PARTY_CONTACT_MECHANISM_PURPOSE_FK)
                    .join(PartyContactMechanismPurposeDetails).onKey(PARTY_CONTACT_MECHANISM_PURPOSES_LAST_DETAIL_FK)
                    .join(ContactMechanismPurposes).onKey(PARTY_CONTACT_MECHANISM_PURPOSE_DETAILS_CONTACT_MECHANISM_PURPOSE_FK)
                    .where(PartyPaymentMethodContactMechanisms.PARTY_PAYMENT_METHOD.eq(partyPaymentMethod.getPrimaryKey()),
                            PartyPaymentMethodContactMechanisms.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ContactMechanismPurposes.SORT_ORDER, ContactMechanismPurposes.CONTACT_MECHANISM_PURPOSE_NAME),
                    PartyPaymentMethodContactMechanismFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyPaymentMethodContactMechanismFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartyPaymentMethodContactMechanism> getPartyPaymentMethodContactMechanismsByPartyPaymentMethod(PartyPaymentMethod partyPaymentMethod) {
        return getPartyPaymentMethodContactMechanismsByPartyPaymentMethod(partyPaymentMethod, EntityPermission.READ_ONLY);
    }
    
    public List<PartyPaymentMethodContactMechanism> getPartyPaymentMethodContactMechanismsByPartyPaymentMethodForUpdate(PartyPaymentMethod partyPaymentMethod) {
        return getPartyPaymentMethodContactMechanismsByPartyPaymentMethod(partyPaymentMethod, EntityPermission.READ_WRITE);
    }
    
    private List<PartyPaymentMethodContactMechanism> getPartyPaymentMethodContactMechanismsByPartyContactMechanismPurpose(PartyContactMechanismPurpose partyContactMechanismPurpose,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyPaymentMethodContactMechanisms.fields())
                .from(PartyPaymentMethodContactMechanisms)
                .where(PartyPaymentMethodContactMechanisms.PARTY_CONTACT_MECHANISM_PURPOSE.eq(partyContactMechanismPurpose.getPrimaryKey()),
                        PartyPaymentMethodContactMechanisms.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyPaymentMethodContactMechanisms.fields())
                    .from(PartyPaymentMethodContactMechanisms)
                    .join(PartyPaymentMethods).onKey(PARTY_PAYMENT_METHOD_CONTACT_MECHANISMS_PARTY_PAYMENT_METHOD_FK)
                    .join(PartyPaymentMethodDetails).onKey(PARTY_PAYMENT_METHODS_LAST_DETAIL_FK)
                    .where(PartyPaymentMethodContactMechanisms.PARTY_CONTACT_MECHANISM_PURPOSE.eq(partyContactMechanismPurpose.getPrimaryKey()),
                            PartyPaymentMethodContactMechanisms.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyPaymentMethodDetails.SORT_ORDER, PartyPaymentMethodDetails.PARTY_PAYMENT_METHOD_NAME),
                    PartyPaymentMethodContactMechanismFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyPaymentMethodContactMechanismFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartyPaymentMethodContactMechanism> getPartyPaymentMethodContactMechanismsByPartyContactMechanismPurpose(PartyContactMechanismPurpose partyContactMechanismPurpose) {
        return getPartyPaymentMethodContactMechanismsByPartyContactMechanismPurpose(partyContactMechanismPurpose, EntityPermission.READ_ONLY);
    }
    
    public List<PartyPaymentMethodContactMechanism> getPartyPaymentMethodContactMechanismsByPartyContactMechanismPurposeForUpdate(PartyContactMechanismPurpose partyContactMechanismPurpose) {
        return getPartyPaymentMethodContactMechanismsByPartyContactMechanismPurpose(partyContactMechanismPurpose, EntityPermission.READ_WRITE);
    }
    
    public PartyPaymentMethodContactMechanismTransfer getPartyPaymentMethodContactMechanismTransfer(UserVisit userVisit,
            PartyPaymentMethodContactMechanism partyPaymentMethodContactMechanism) {
        return partyPaymentMethodContactMechanismTransferCache.getTransfer(userVisit, partyPaymentMethodContactMechanism);
    }
    
    public List<PartyPaymentMethodContactMechanismTransfer> getPartyPaymentMethodContactMechanismTransfers(UserVisit userVisit,
            List<PartyPaymentMethodContactMechanism> partyPaymentMethodContactMechanisms) {
        List<PartyPaymentMethodContactMechanismTransfer> partyPaymentMethodContactMechanismTransfers = new ArrayList<>(partyPaymentMethodContactMechanisms.size());
        
        partyPaymentMethodContactMechanisms.forEach((partyPaymentMethodContactMechanism) ->
                partyPaymentMethodContactMechanismTransfers.add(partyPaymentMethodContactMechanismTransferCache.getTransfer(userVisit, partyPaymentMethodContactMechanism))
        );
        
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

}

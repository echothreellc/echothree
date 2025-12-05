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

package com.echothree.model.control.payment.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.order.server.control.OrderPaymentPreferenceControl;
import com.echothree.model.control.payment.common.PaymentMethodTypes;
import com.echothree.model.control.payment.common.choice.PartyPaymentMethodChoicesBean;
import com.echothree.model.control.payment.common.transfer.PartyPaymentMethodContactMechanismTransfer;
import com.echothree.model.control.payment.common.transfer.PartyPaymentMethodTransfer;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.contact.server.entity.PartyContactMechanismPurpose;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.NameSuffix;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PersonalTitle;
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
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EncryptionUtils;
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
import com.echothree.util.server.cdi.CommandScope;

@CommandScope
public class PartyPaymentMethodControl
        extends BasePaymentControl {

    /** Creates a new instance of PartyPaymentMethodControl */
    protected PartyPaymentMethodControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Party Payment Methods
    // --------------------------------------------------------------------------------
    
    public PartyPaymentMethod createPartyPaymentMethod(Party party, String description, PaymentMethod paymentMethod,
            Boolean deleteWhenUnused, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.PARTY_PAYMENT_METHOD.name());
        var sequence = sequenceControl.getDefaultSequence(sequenceType);
        var partyPaymentMethodName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);

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

        var partyPaymentMethod = PartyPaymentMethodFactory.getInstance().create();
        var partyPaymentMethodDetail = PartyPaymentMethodDetailFactory.getInstance().create(partyPaymentMethod, partyPaymentMethodName,
                party, description, paymentMethod, deleteWhenUnused, isDefault, sortOrder, session.getStartTimeLong(), Session.MAX_TIME_LONG);

        // Convert to R/W
        partyPaymentMethod = PartyPaymentMethodFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, partyPaymentMethod.getPrimaryKey());
        partyPaymentMethod.setActiveDetail(partyPaymentMethodDetail);
        partyPaymentMethod.setLastDetail(partyPaymentMethodDetail);
        partyPaymentMethod.store();

        sendEvent(partyPaymentMethod.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

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
            var partyPaymentMethod = PartyPaymentMethodFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyPaymentMethodDetailValue.getPartyPaymentMethodPK());
            var partyPaymentMethodDetail = partyPaymentMethod.getActiveDetailForUpdate();

            partyPaymentMethodDetail.setThruTime(session.getStartTimeLong());
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

            partyPaymentMethodDetail = PartyPaymentMethodDetailFactory.getInstance().create(partyPaymentMethodPK, partyPaymentMethodName, party.getPrimaryKey(),
                    description, paymentMethodPK, deleteWhenUnused, isDefault, sortOrder, session.getStartTimeLong(), Session.MAX_TIME_LONG);

            partyPaymentMethod.setActiveDetail(partyPaymentMethodDetail);
            partyPaymentMethod.setLastDetail(partyPaymentMethodDetail);

            sendEvent(partyPaymentMethodPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updatePartyPaymentMethodFromValue(PartyPaymentMethodDetailValue partyPaymentMethodDetailValue, BasePK updatedBy) {
        updatePartyPaymentMethodFromValue(partyPaymentMethodDetailValue, true, updatedBy);
    }

    public void deletePartyPaymentMethod(PartyPaymentMethod partyPaymentMethod, BasePK deletedBy) {
        var orderPaymentPreferenceControl = Session.getModelController(OrderPaymentPreferenceControl.class);

        orderPaymentPreferenceControl.deleteOrderPaymentPreferencesByPartyPaymentMethod(partyPaymentMethod, deletedBy);
        
        var partyPaymentMethodDetail = partyPaymentMethod.getLastDetailForUpdate();
        partyPaymentMethodDetail.setThruTime(session.getStartTimeLong());
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
    
    public PartyPaymentMethodCreditCard createPartyPaymentMethodCreditCard(PartyPaymentMethod partyPaymentMethod, String number,
            Integer expirationMonth, Integer expirationYear, PersonalTitle personalTitle, String firstName, String firstNameSdx,
            String middleName, String middleNameSdx, String lastName, String lastNameSdx, NameSuffix nameSuffix, String name,
            PartyContactMechanism billingPartyContactMechanism, String issuerName, PartyContactMechanism issuerPartyContactMechanism,
            BasePK createdBy) {
        var partyPaymentMethodCreditCard = PartyPaymentMethodCreditCardFactory.getInstance().create(session,
                partyPaymentMethod, encodePartyPaymentMethodCreditCardNumber(number), expirationMonth, expirationYear, personalTitle,
                firstName, firstNameSdx, middleName, middleNameSdx, lastName, lastNameSdx, nameSuffix, name, billingPartyContactMechanism,
                issuerName, issuerPartyContactMechanism, session.getStartTimeLong(), Session.MAX_TIME_LONG);
        
        sendEvent(partyPaymentMethod.getPrimaryKey(), EventTypes.MODIFY,
                partyPaymentMethodCreditCard.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
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
        PartyPaymentMethodCreditCard partyPaymentMethodCreditCard;
        
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

            var ps = PartyPaymentMethodCreditCardFactory.getInstance().prepareStatement(query);
            
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
        List<PartyPaymentMethodCreditCard> partyPaymentMethodCreditCards;
        
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

            var ps = PartyPaymentMethodCreditCardFactory.getInstance().prepareStatement(query);
            
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
        List<PartyPaymentMethodCreditCard> partyPaymentMethodCreditCards;
        
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

            var ps = PartyPaymentMethodCreditCardFactory.getInstance().prepareStatement(query);
            
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
        var partyPaymentMethodCreditCard = getPartyPaymentMethodCreditCardForUpdate(partyPaymentMethod);
        
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
            var partyPaymentMethodCreditCard = PartyPaymentMethodCreditCardFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyPaymentMethodCreditCardValue.getPrimaryKey());
            
            partyPaymentMethodCreditCard.setThruTime(session.getStartTimeLong());
            partyPaymentMethodCreditCard.store();

            var partyPaymentMethodPK = partyPaymentMethodCreditCard.getPartyPaymentMethodPK(); // Not updated
            var number = partyPaymentMethodCreditCardValue.getNumber();
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
            
            partyPaymentMethodCreditCard = PartyPaymentMethodCreditCardFactory.getInstance().create(partyPaymentMethodPK, number, expirationMonth,
                    expirationYear, personalTitlePK, firstName, firstNameSdx, middleName, middleNameSdx, lastName, lastNameSdx, nameSuffixPK, name,
                    billingPartyContactMechanismPK, issuerName, issuerPartyContactMechanismPK, session.getStartTimeLong(), Session.MAX_TIME_LONG);
            
            sendEvent(partyPaymentMethodPK, EventTypes.MODIFY, partyPaymentMethodCreditCard.getPrimaryKey(),
                    EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePartyPaymentMethodCreditCard(PartyPaymentMethodCreditCard partyPaymentMethodCreditCard, BasePK deletedBy) {
        partyPaymentMethodCreditCard.setThruTime(session.getStartTimeLong());
        
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
    
    public PartyPaymentMethodCreditCardSecurityCode createPartyPaymentMethodCreditCardSecurityCode(PartyPaymentMethod partyPaymentMethod,
            String securityCode, BasePK createdBy) {
        var partyPaymentMethodCreditCardSecurityCode = PartyPaymentMethodCreditCardSecurityCodeFactory.getInstance().create(session,
                partyPaymentMethod, encodePartyPaymentMethodCreditCardSecurityCodeSecurityCode(securityCode), session.getStartTimeLong(),
                Session.MAX_TIME_LONG);
        
        sendEvent(partyPaymentMethod.getPrimaryKey(), EventTypes.MODIFY,
                partyPaymentMethodCreditCardSecurityCode.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyPaymentMethodCreditCardSecurityCode;
    }
    
    private PartyPaymentMethodCreditCardSecurityCode getPartyPaymentMethodCreditCardSecurityCode(PartyPaymentMethod partyPaymentMethod,
            EntityPermission entityPermission) {
        PartyPaymentMethodCreditCardSecurityCode partyPaymentMethodCreditCardSecurityCode;
        
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

            var ps = PartyPaymentMethodCreditCardSecurityCodeFactory.getInstance().prepareStatement(query);
            
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
        var partyPaymentMethodCreditCardSecurityCode = getPartyPaymentMethodCreditCardSecurityCodeForUpdate(partyPaymentMethod);
        
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
            var partyPaymentMethodCreditCardSecurityCode = PartyPaymentMethodCreditCardSecurityCodeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyPaymentMethodCreditCardSecurityCodeValue.getPrimaryKey());
            
            partyPaymentMethodCreditCardSecurityCode.setThruTime(session.getStartTimeLong());
            partyPaymentMethodCreditCardSecurityCode.store();

            var partyPaymentMethodPK = partyPaymentMethodCreditCardSecurityCode.getPartyPaymentMethodPK(); // Not updated
            var securityCode = partyPaymentMethodCreditCardSecurityCodeValue.getSecurityCode();
            
            partyPaymentMethodCreditCardSecurityCode = PartyPaymentMethodCreditCardSecurityCodeFactory.getInstance().create(session,
                    partyPaymentMethodPK, securityCode, session.getStartTimeLong(), Session.MAX_TIME_LONG);
            
            sendEvent(partyPaymentMethodPK, EventTypes.MODIFY, partyPaymentMethodCreditCardSecurityCode.getPrimaryKey(),
                    EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePartyPaymentMethodCreditCardSecurityCode(PartyPaymentMethodCreditCardSecurityCode partyPaymentMethodCreditCardSecurityCode,
            BasePK deletedBy) {
        partyPaymentMethodCreditCardSecurityCode.setThruTime(session.getStartTimeLong());
        
        sendEvent(partyPaymentMethodCreditCardSecurityCode.getPartyPaymentMethodPK(), EventTypes.MODIFY,
                partyPaymentMethodCreditCardSecurityCode.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Payment Method Contact Mechanisms
    // --------------------------------------------------------------------------------
    
    public PartyPaymentMethodContactMechanism createPartyPaymentMethodContactMechanism(PartyPaymentMethod partyPaymentMethod,
            PartyContactMechanismPurpose partyContactMechanismPurpose, BasePK createdBy) {
        var partyPaymentMethodContactMechanism = PartyPaymentMethodContactMechanismFactory.getInstance().create(session,
                partyPaymentMethod, partyContactMechanismPurpose, session.getStartTimeLong(), Session.MAX_TIME_LONG);
        
        sendEvent(partyPaymentMethod.getPrimaryKey(), EventTypes.MODIFY, partyPaymentMethodContactMechanism.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyPaymentMethodContactMechanism;
    }
    
    private PartyPaymentMethodContactMechanism getPartyPaymentMethodContactMechanism(PartyPaymentMethod partyPaymentMethod,
            PartyContactMechanismPurpose partyContactMechanismPurpose, EntityPermission entityPermission) {
        PartyPaymentMethodContactMechanism partyPaymentMethodContactMechanism;
        
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

            var ps = PartyPaymentMethodContactMechanismFactory.getInstance().prepareStatement(query);
            
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
        List<PartyPaymentMethodContactMechanism> partyPaymentMethodContactMechanisms;
        
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

            var ps = PartyPaymentMethodContactMechanismFactory.getInstance().prepareStatement(query);
            
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
        List<PartyPaymentMethodContactMechanism> partyPaymentMethodContactMechanisms;
        
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

            var ps = PartyPaymentMethodContactMechanismFactory.getInstance().prepareStatement(query);
            
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

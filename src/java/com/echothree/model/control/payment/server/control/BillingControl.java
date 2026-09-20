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
import com.echothree.model.control.payment.common.BillingAccountRoleTypes;
import com.echothree.model.control.payment.common.transfer.BillingAccountRoleTransfer;
import com.echothree.model.control.payment.common.transfer.BillingAccountRoleTypeTransfer;
import com.echothree.model.control.payment.common.transfer.BillingAccountTransfer;
import com.echothree.model.control.payment.server.transfer.BillingAccountRoleTransferCache;
import com.echothree.model.control.payment.server.transfer.BillingAccountRoleTypeTransferCache;
import com.echothree.model.control.payment.server.transfer.BillingAccountTransferCache;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.payment.server.entity.BillingAccount;
import com.echothree.model.data.payment.server.entity.BillingAccountRole;
import com.echothree.model.data.payment.server.entity.BillingAccountRoleType;
import com.echothree.model.data.payment.server.entity.BillingAccountRoleTypeDescription;
import com.echothree.model.data.payment.server.entity.BillingAccountStatus;
import com.echothree.model.data.payment.server.factory.BillingAccountDetailFactory;
import com.echothree.model.data.payment.server.factory.BillingAccountFactory;
import com.echothree.model.data.payment.server.factory.BillingAccountRoleFactory;
import com.echothree.model.data.payment.server.factory.BillingAccountRoleTypeDescriptionFactory;
import com.echothree.model.data.payment.server.factory.BillingAccountRoleTypeFactory;
import com.echothree.model.data.payment.server.factory.BillingAccountStatusFactory;
import com.echothree.model.data.payment.server.value.BillingAccountRoleValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTIES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.BILLING_ACCOUNTS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.BILLING_ACCOUNTS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.BILLING_ACCOUNT_DETAILS_CURRENCY_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.BILLING_ACCOUNT_ROLES_BILLING_ACCOUNT_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.BILLING_ACCOUNT_ROLES_BILLING_ACCOUNT_ROLE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.BILLING_ACCOUNT_ROLES_PARTY_FK;
import static com.echothree.model.jooq.server.tables.accounting.Currencies.Currencies;
import static com.echothree.model.jooq.server.tables.party.Parties.Parties;
import static com.echothree.model.jooq.server.tables.party.PartyDetails.PartyDetails;
import static com.echothree.model.jooq.server.tables.payment.BillingAccountDetails.BillingAccountDetails;
import static com.echothree.model.jooq.server.tables.payment.BillingAccountRoleTypeDescriptions.BillingAccountRoleTypeDescriptions;
import static com.echothree.model.jooq.server.tables.payment.BillingAccountRoleTypes.BillingAccountRoleTypes;
import static com.echothree.model.jooq.server.tables.payment.BillingAccountRoles.BillingAccountRoles;
import static com.echothree.model.jooq.server.tables.payment.BillingAccountStatuses.BillingAccountStatuses;
import static com.echothree.model.jooq.server.tables.payment.BillingAccounts.BillingAccounts;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;

@CommandScope
public class BillingControl
        extends BaseModelControl {

    @Inject
    private SequenceControl sequenceControl;

    @Inject
    protected SequenceGeneratorLogic sequenceGeneratorLogic;

    /** Creates a new instance of BillingControl */
    protected BillingControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Billing Account Role Types
    // --------------------------------------------------------------------------------

    @Inject
    private BillingAccountRoleTypeFactory billingAccountRoleTypeFactory;

    @Inject
    private BillingAccountRoleTypeTransferCache billingAccountRoleTypeTransferCache;

    public BillingAccountRoleType createBillingAccountRoleType(String billingAccountRoleTypeName, Integer sortOrder) {
        return billingAccountRoleTypeFactory.create(billingAccountRoleTypeName, sortOrder);
    }
    
    public List<BillingAccountRoleType> getBillingAccountRoleTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(BillingAccountRoleTypes.fields())
                .from(BillingAccountRoleTypes)
                .orderBy(BillingAccountRoleTypes.SORT_ORDER, BillingAccountRoleTypes.BILLING_ACCOUNT_ROLE_TYPE_NAME),
                BillingAccountRoleTypeFactory.class);

        return billingAccountRoleTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public BillingAccountRoleType getBillingAccountRoleTypeByName(String billingAccountRoleTypeName) {
        var query = session.getDslContext()
                .select(BillingAccountRoleTypes.fields())
                .from(BillingAccountRoleTypes)
                .where(BillingAccountRoleTypes.BILLING_ACCOUNT_ROLE_TYPE_NAME.eq(billingAccountRoleTypeName));

        return billingAccountRoleTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public BillingAccountRoleTypeTransfer getBillingAccountRoleTypeTransfer(UserVisit userVisit,
            BillingAccountRoleType billingAccountRoleType) {
        return billingAccountRoleTypeTransferCache.getTransfer(userVisit, billingAccountRoleType);
    }

    private List<BillingAccountRoleTypeTransfer> getBillingAccountRoleTypeTransfers(final UserVisit userVisit, final List<BillingAccountRoleType> billingAccountRoleTypes) {
        List<BillingAccountRoleTypeTransfer> billingAccountRoleTypeTransfers = new ArrayList<>(billingAccountRoleTypes.size());

        billingAccountRoleTypes.forEach((billingAccountRoleType) ->
                billingAccountRoleTypeTransfers.add(billingAccountRoleTypeTransferCache.getTransfer(userVisit, billingAccountRoleType))
        );

            return billingAccountRoleTypeTransfers;
    }
    
    public List<BillingAccountRoleTypeTransfer> getBillingAccountRoleTypeTransfers(UserVisit userVisit) {
        return getBillingAccountRoleTypeTransfers(userVisit, getBillingAccountRoleTypes());
    }
    
    // --------------------------------------------------------------------------------
    //   Billing Account Role Type Description
    // --------------------------------------------------------------------------------

    @Inject
    private BillingAccountRoleTypeDescriptionFactory billingAccountRoleTypeDescriptionFactory;

    public BillingAccountRoleTypeDescription createBillingAccountRoleTypeDescription(BillingAccountRoleType billingAccountRoleType, Language language, String description) {
        return billingAccountRoleTypeDescriptionFactory.create(billingAccountRoleType, language, description);
    }
    
    public BillingAccountRoleTypeDescription getBillingAccountRoleTypeDescription(BillingAccountRoleType billingAccountRoleType, Language language) {
        var query = session.getDslContext()
                .select(BillingAccountRoleTypeDescriptions.fields())
                .from(BillingAccountRoleTypeDescriptions)
                .where(BillingAccountRoleTypeDescriptions.BILLING_ACCOUNT_ROLE_TYPE.eq(billingAccountRoleType.getPrimaryKey()),
                        BillingAccountRoleTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return billingAccountRoleTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public String getBestBillingAccountRoleTypeDescription(BillingAccountRoleType billingAccountRoleType, Language language) {
        String description;
        var billingAccountRoleTypeDescription = getBillingAccountRoleTypeDescription(billingAccountRoleType, language);
        
        if(billingAccountRoleTypeDescription == null && !language.getIsDefault()) {
            billingAccountRoleTypeDescription = getBillingAccountRoleTypeDescription(billingAccountRoleType, partyControl.getDefaultLanguage());
        }
        
        if(billingAccountRoleTypeDescription == null) {
            description = billingAccountRoleType.getBillingAccountRoleTypeName();
        } else {
            description = billingAccountRoleTypeDescription.getDescription();
        }
        
        return description;
    }

    // --------------------------------------------------------------------------------
    //   Billing Accounts
    // --------------------------------------------------------------------------------

    @Inject
    protected BillingAccountDetailFactory billingAccountDetailFactory;

    @Inject
    private BillingAccountFactory billingAccountFactory;

    @Inject
    private BillingAccountTransferCache billingAccountTransferCache;

    public BillingAccount createBillingAccount(final Party billFrom, final Currency currency, final String reference, final String description, final BasePK createdBy) {
        var sequence = sequenceControl.getDefaultSequence(billFrom.getLastDetail().getPartyType().getBillingAccountSequenceType());
        var billingAccountName = sequenceGeneratorLogic.getNextSequenceValue(sequence);
        
         return createBillingAccount(billingAccountName, currency, reference, description, createdBy);
    }
    
    public BillingAccount createBillingAccount(String billingAccountName, Currency currency, String reference, String description, BasePK createdBy) {
        var billingAccount = billingAccountFactory.create();
        var billingAccountDetail = billingAccountDetailFactory.create(billingAccount,
                billingAccountName, currency, reference, description, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        billingAccount = billingAccountFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                billingAccount.getPrimaryKey());
        billingAccount.setActiveDetail(billingAccountDetail);
        billingAccount.setLastDetail(billingAccountDetail);
        billingAccount.store();
        
        sendEvent(billingAccount.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        createBillingAccountStatus(billingAccount, Long.valueOf(0), null);
        
        return billingAccount;
    }
    
    private BillingAccount getBillingAccount(Party billFrom, Party billTo, Currency currency, EntityPermission entityPermission) {
        var selectedRole = BillingAccountRoles.as("selectedRole");
        var selectedRoleType = BillingAccountRoleTypes.as("selectedRoleType");
        var counterpartRole = BillingAccountRoles.as("counterpartRole");
        var counterpartRoleType = BillingAccountRoleTypes.as("counterpartRoleType");
        var baseQuery = session.getDslContext()
                .select(BillingAccounts.fields())
                .from(BillingAccounts)
                .join(BillingAccountDetails).onKey(BILLING_ACCOUNTS_ACTIVE_DETAIL_FK)
                .join(selectedRole).on(selectedRole.BILLING_ACCOUNT.eq(BillingAccounts.BILLING_ACCOUNT))
                .join(selectedRoleType).on(selectedRoleType.BILLING_ACCOUNT_ROLE_TYPE.eq(selectedRole.BILLING_ACCOUNT_ROLE_TYPE))
                .join(counterpartRole).on(counterpartRole.BILLING_ACCOUNT.eq(BillingAccounts.BILLING_ACCOUNT))
                .join(counterpartRoleType).on(counterpartRoleType.BILLING_ACCOUNT_ROLE_TYPE.eq(counterpartRole.BILLING_ACCOUNT_ROLE_TYPE))
                .where(selectedRole.PARTY.eq(billFrom.getPrimaryKey()),
                        selectedRoleType.BILLING_ACCOUNT_ROLE_TYPE_NAME.eq(com.echothree.model.control.payment.common.BillingAccountRoleTypes.BILL_FROM.name()),
                        selectedRole.THRU_TIME.eq(Session.MAX_TIME),
                        counterpartRole.PARTY.eq(billTo.getPrimaryKey()),
                        counterpartRoleType.BILLING_ACCOUNT_ROLE_TYPE_NAME.eq(com.echothree.model.control.payment.common.BillingAccountRoleTypes.BILL_TO.name()),
                        counterpartRole.THRU_TIME.eq(Session.MAX_TIME),
                        BillingAccountDetails.CURRENCY.eq(currency.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return billingAccountFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public BillingAccount getBillingAccount(Party billFrom, Party billTo, Currency currency) {
        return getBillingAccount(billFrom, billTo, currency, EntityPermission.READ_ONLY);
    }
    
    public BillingAccount getBillingAccountForUpdate(Party billFrom, Party billTo, Currency currency) {
        return getBillingAccount(billFrom, billTo, currency, EntityPermission.READ_WRITE);
    }
    
    private List<BillingAccount> getBillingAccountsByBillFrom(Party billFrom, EntityPermission entityPermission) {
        var selectedRole = BillingAccountRoles.as("selectedRole");
        var selectedRoleType = BillingAccountRoleTypes.as("selectedRoleType");
        var counterpartRole = BillingAccountRoles.as("counterpartRole");
        var counterpartRoleType = BillingAccountRoleTypes.as("counterpartRoleType");
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                .select(BillingAccounts.fields())
                .from(BillingAccounts)
                .join(BillingAccountDetails).onKey(BILLING_ACCOUNTS_ACTIVE_DETAIL_FK)
                .join(selectedRole).on(selectedRole.BILLING_ACCOUNT.eq(BillingAccounts.BILLING_ACCOUNT))
                .join(selectedRoleType).on(selectedRoleType.BILLING_ACCOUNT_ROLE_TYPE.eq(selectedRole.BILLING_ACCOUNT_ROLE_TYPE))
                .join(counterpartRole).on(counterpartRole.BILLING_ACCOUNT.eq(BillingAccounts.BILLING_ACCOUNT))
                .join(counterpartRoleType).on(counterpartRoleType.BILLING_ACCOUNT_ROLE_TYPE.eq(counterpartRole.BILLING_ACCOUNT_ROLE_TYPE))
                .join(Currencies).onKey(BILLING_ACCOUNT_DETAILS_CURRENCY_FK)
                .join(Parties).on(Parties.PARTY.eq(counterpartRole.PARTY))
                .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                .where(selectedRole.PARTY.eq(billFrom.getPrimaryKey()),
                        selectedRoleType.BILLING_ACCOUNT_ROLE_TYPE_NAME.eq(com.echothree.model.control.payment.common.BillingAccountRoleTypes.BILL_FROM.name()),
                        selectedRole.THRU_TIME.eq(Session.MAX_TIME),
                        counterpartRoleType.BILLING_ACCOUNT_ROLE_TYPE_NAME.eq(com.echothree.model.control.payment.common.BillingAccountRoleTypes.BILL_TO.name()),
                        counterpartRole.THRU_TIME.eq(Session.MAX_TIME))
                .orderBy(PartyDetails.PARTY_NAME, Currencies.SORT_ORDER, Currencies.CURRENCY_ISO_NAME), BillingAccountFactory.class);
            case READ_WRITE -> session.getDslContext()
                .select(BillingAccounts.fields())
                .from(BillingAccounts)
                .join(BillingAccountDetails).onKey(BILLING_ACCOUNTS_ACTIVE_DETAIL_FK)
                .join(selectedRole).on(selectedRole.BILLING_ACCOUNT.eq(BillingAccounts.BILLING_ACCOUNT))
                .join(selectedRoleType).on(selectedRoleType.BILLING_ACCOUNT_ROLE_TYPE.eq(selectedRole.BILLING_ACCOUNT_ROLE_TYPE))
                .where(selectedRole.PARTY.eq(billFrom.getPrimaryKey()),
                        selectedRoleType.BILLING_ACCOUNT_ROLE_TYPE_NAME.eq(com.echothree.model.control.payment.common.BillingAccountRoleTypes.BILL_FROM.name()),
                        selectedRole.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        };

        return billingAccountFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<BillingAccount> getBillingAccountsByBillFrom(Party billFrom) {
        return getBillingAccountsByBillFrom(billFrom, EntityPermission.READ_ONLY);
    }
    
    public List<BillingAccount> getBillingAccountsByBillFromForUpdate(Party billFrom) {
        return getBillingAccountsByBillFrom(billFrom, EntityPermission.READ_WRITE);
    }
    
    private List<BillingAccount> getBillingAccountsByBillTo(Party billTo, EntityPermission entityPermission) {
        var selectedRole = BillingAccountRoles.as("selectedRole");
        var selectedRoleType = BillingAccountRoleTypes.as("selectedRoleType");
        var counterpartRole = BillingAccountRoles.as("counterpartRole");
        var counterpartRoleType = BillingAccountRoleTypes.as("counterpartRoleType");
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                .select(BillingAccounts.fields())
                .from(BillingAccounts)
                .join(BillingAccountDetails).onKey(BILLING_ACCOUNTS_ACTIVE_DETAIL_FK)
                .join(selectedRole).on(selectedRole.BILLING_ACCOUNT.eq(BillingAccounts.BILLING_ACCOUNT))
                .join(selectedRoleType).on(selectedRoleType.BILLING_ACCOUNT_ROLE_TYPE.eq(selectedRole.BILLING_ACCOUNT_ROLE_TYPE))
                .join(counterpartRole).on(counterpartRole.BILLING_ACCOUNT.eq(BillingAccounts.BILLING_ACCOUNT))
                .join(counterpartRoleType).on(counterpartRoleType.BILLING_ACCOUNT_ROLE_TYPE.eq(counterpartRole.BILLING_ACCOUNT_ROLE_TYPE))
                .join(Currencies).onKey(BILLING_ACCOUNT_DETAILS_CURRENCY_FK)
                .join(Parties).on(Parties.PARTY.eq(counterpartRole.PARTY))
                .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                .where(selectedRole.PARTY.eq(billTo.getPrimaryKey()),
                        selectedRoleType.BILLING_ACCOUNT_ROLE_TYPE_NAME.eq(com.echothree.model.control.payment.common.BillingAccountRoleTypes.BILL_TO.name()),
                        selectedRole.THRU_TIME.eq(Session.MAX_TIME),
                        counterpartRoleType.BILLING_ACCOUNT_ROLE_TYPE_NAME.eq(com.echothree.model.control.payment.common.BillingAccountRoleTypes.BILL_FROM.name()),
                        counterpartRole.THRU_TIME.eq(Session.MAX_TIME))
                .orderBy(PartyDetails.PARTY_NAME, Currencies.SORT_ORDER, Currencies.CURRENCY_ISO_NAME), BillingAccountFactory.class);
            case READ_WRITE -> session.getDslContext()
                .select(BillingAccounts.fields())
                .from(BillingAccounts)
                .join(BillingAccountDetails).onKey(BILLING_ACCOUNTS_ACTIVE_DETAIL_FK)
                .join(selectedRole).on(selectedRole.BILLING_ACCOUNT.eq(BillingAccounts.BILLING_ACCOUNT))
                .join(selectedRoleType).on(selectedRoleType.BILLING_ACCOUNT_ROLE_TYPE.eq(selectedRole.BILLING_ACCOUNT_ROLE_TYPE))
                .where(selectedRole.PARTY.eq(billTo.getPrimaryKey()),
                        selectedRoleType.BILLING_ACCOUNT_ROLE_TYPE_NAME.eq(com.echothree.model.control.payment.common.BillingAccountRoleTypes.BILL_TO.name()),
                        selectedRole.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        };

        return billingAccountFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<BillingAccount> getBillingAccountsByBillTo(Party billTo) {
        return getBillingAccountsByBillTo(billTo, EntityPermission.READ_ONLY);
    }
    
    public List<BillingAccount> getBillingAccountsByBillToForUpdate(Party billTo) {
        return getBillingAccountsByBillTo(billTo, EntityPermission.READ_WRITE);
    }
    
    private BillingAccount getBillingAccountByName(String billingAccountName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(BillingAccounts.fields())
                .from(BillingAccounts)
                .join(BillingAccountDetails).onKey(BILLING_ACCOUNTS_ACTIVE_DETAIL_FK)
                .where(BillingAccountDetails.BILLING_ACCOUNT_NAME.eq(billingAccountName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return billingAccountFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public BillingAccount getBillingAccountByName(String billingAccountName) {
        return getBillingAccountByName(billingAccountName, EntityPermission.READ_ONLY);
    }
    
    public BillingAccount getBillingAccountByNameForUpdate(String billingAccountName) {
        return getBillingAccountByName(billingAccountName, EntityPermission.READ_WRITE);
    }
    
    public BillingAccountTransfer getBillingAccountTransfer(UserVisit userVisit, BillingAccount billingAccount) {
        return billingAccountTransferCache.getTransfer(userVisit, billingAccount);
    }
    
    public List<BillingAccountTransfer> getBillingAccountTransfers(UserVisit userVisit, Collection<BillingAccount> billingAccounts) {
        List<BillingAccountTransfer> billingAccountTransfers = new ArrayList<>(billingAccounts.size());
        
        billingAccounts.forEach((billingAccount) ->
                billingAccountTransfers.add(billingAccountTransferCache.getTransfer(userVisit, billingAccount))
        );
        
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

    @Inject
    private BillingAccountStatusFactory billingAccountStatusFactory;

    public BillingAccountStatus createBillingAccountStatus(BillingAccount billingAccount, Long creditLimit,
            Long potentialCreditLimit) {
        return billingAccountStatusFactory.create(billingAccount, creditLimit, potentialCreditLimit);
    }
    
    private BillingAccountStatus getBillingAccountStatus(BillingAccount billingAccount, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(BillingAccountStatuses.fields())
                .from(BillingAccountStatuses)
                .where(BillingAccountStatuses.BILLING_ACCOUNT.eq(billingAccount.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return billingAccountStatusFactory.getEntityFromQuery(entityPermission, query);
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

    @Inject
    private BillingAccountRoleFactory billingAccountRoleFactory;

    @Inject
    private BillingAccountRoleTransferCache billingAccountRoleTransferCache;

    public BillingAccountRole createBillingAccountRoleUsingNames(BillingAccount billingAccount, Party party, PartyContactMechanism partyContactMechanism,
            String billingAccountRoleTypeName, BasePK createdBy) {
        var billingAccountRoleType = getBillingAccountRoleTypeByName(billingAccountRoleTypeName);
        
        return createBillingAccountRole(billingAccount, party, partyContactMechanism, billingAccountRoleType, createdBy);
    }
    
    public BillingAccountRole createBillingAccountRole(BillingAccount billingAccount, Party party, PartyContactMechanism partyContactMechanism,
            BillingAccountRoleType billingAccountRoleType, BasePK createdBy) {
        var billingAccountRole = billingAccountRoleFactory.create(billingAccount, party, partyContactMechanism,
                billingAccountRoleType, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(billingAccount.getPrimaryKey(), EventTypes.MODIFY, billingAccountRole.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return billingAccountRole;
    }
    
    private BillingAccountRole getBillingAccountRole(BillingAccount billingAccount, BillingAccountRoleType billingAccountRoleType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(BillingAccountRoles.fields())
                .from(BillingAccountRoles)
                .where(BillingAccountRoles.BILLING_ACCOUNT.eq(billingAccount.getPrimaryKey()),
                        BillingAccountRoles.BILLING_ACCOUNT_ROLE_TYPE.eq(billingAccountRoleType.getPrimaryKey()),
                        BillingAccountRoles.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return billingAccountRoleFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public BillingAccountRole getBillingAccountRole(BillingAccount billingAccount, BillingAccountRoleType billingAccountRoleType) {
        return getBillingAccountRole(billingAccount, billingAccountRoleType, EntityPermission.READ_ONLY);
    }
    
    public BillingAccountRole getBillingAccountRoleUsingNames(BillingAccount billingAccount, String billingAccountRoleTypeName) {
        var billingAccountRoleType = getBillingAccountRoleTypeByName(billingAccountRoleTypeName);
        
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(BillingAccountRoles.fields())
                    .from(BillingAccountRoles)
                    .join(BillingAccountRoleTypes).onKey(BILLING_ACCOUNT_ROLES_BILLING_ACCOUNT_ROLE_TYPE_FK)
                    .join(Parties).onKey(BILLING_ACCOUNT_ROLES_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(BillingAccountRoles.BILLING_ACCOUNT.eq(billingAccount.getPrimaryKey()),
                            BillingAccountRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(BillingAccountRoleTypes.SORT_ORDER, PartyDetails.PARTY_NAME),
                    BillingAccountRoleFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(BillingAccountRoles.fields())
                    .from(BillingAccountRoles)
                    .where(BillingAccountRoles.BILLING_ACCOUNT.eq(billingAccount.getPrimaryKey()),
                            BillingAccountRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return billingAccountRoleFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<BillingAccountRole> getBillingAccountRolesByBillingAccount(BillingAccount billingAccount) {
        return getBillingAccountRolesByBillingAccount(billingAccount, EntityPermission.READ_ONLY);
    }
    
    public List<BillingAccountRole> getBillingAccountRolesByBillingAccountForUpdate(BillingAccount billingAccount) {
        return getBillingAccountRolesByBillingAccount(billingAccount, EntityPermission.READ_WRITE);
    }
    
    private List<BillingAccountRole> getBillingAccountRolesByPartyContactMechanism(PartyContactMechanism partyContactMechanism, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(BillingAccountRoles.fields())
                    .from(BillingAccountRoles)
                    .join(BillingAccounts).onKey(BILLING_ACCOUNT_ROLES_BILLING_ACCOUNT_FK)
                    .join(BillingAccountDetails).onKey(BILLING_ACCOUNTS_LAST_DETAIL_FK)
                    .join(BillingAccountRoleTypes).onKey(BILLING_ACCOUNT_ROLES_BILLING_ACCOUNT_ROLE_TYPE_FK)
                    .where(BillingAccountRoles.PARTY_CONTACT_MECHANISM.eq(partyContactMechanism.getPrimaryKey()),
                            BillingAccountRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(BillingAccountDetails.BILLING_ACCOUNT_NAME, BillingAccountRoleTypes.SORT_ORDER, BillingAccountRoleTypes.BILLING_ACCOUNT_ROLE_TYPE_NAME),
                    BillingAccountRoleFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(BillingAccountRoles.fields())
                    .from(BillingAccountRoles)
                    .where(BillingAccountRoles.PARTY_CONTACT_MECHANISM.eq(partyContactMechanism.getPrimaryKey()),
                            BillingAccountRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return billingAccountRoleFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<BillingAccountRole> getBillingAccountRolesByPartyContactMechanism(PartyContactMechanism partyContactMechanism) {
        return getBillingAccountRolesByPartyContactMechanism(partyContactMechanism, EntityPermission.READ_ONLY);
    }
    
    public List<BillingAccountRole> getBillingAccountRolesByPartyContactMechanismForUpdate(PartyContactMechanism partyContactMechanism) {
        return getBillingAccountRolesByPartyContactMechanism(partyContactMechanism, EntityPermission.READ_WRITE);
    }
    
    public BillingAccountRoleTransfer getBillingAccountRoleTransfer(UserVisit userVisit, BillingAccountRole billingAccountRole) {
        return billingAccountRoleTransferCache.getTransfer(userVisit, billingAccountRole);
    }
    
    public List<BillingAccountRoleTransfer> getBillingAccountRoleTransfers(UserVisit userVisit, Collection<BillingAccountRole> billingAccountRoles) {
        List<BillingAccountRoleTransfer> billingAccountRoleTransfers = new ArrayList<>(billingAccountRoles.size());
        
        billingAccountRoles.forEach((billingAccountRole) ->
                billingAccountRoleTransfers.add(billingAccountRoleTransferCache.getTransfer(userVisit, billingAccountRole))
        );
        
        return billingAccountRoleTransfers;
    }
    
    public List<BillingAccountRoleTransfer> getBillingAccountRoleTransfersByBillingAccount(UserVisit userVisit, BillingAccount billingAccount) {
        return getBillingAccountRoleTransfers(userVisit, getBillingAccountRolesByBillingAccount(billingAccount));
    }
    
    public void updateBillingAccountRoleFromValue(BillingAccountRoleValue billingAccountRoleValue, BasePK updatedBy) {
        if(billingAccountRoleValue.hasBeenModified()) {
            var billingAccountRole = billingAccountRoleFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     billingAccountRoleValue.getPrimaryKey());
            
            billingAccountRole.setThruTime(session.getStartTime());
            billingAccountRole.store();

            var billingAccountPK = billingAccountRole.getBillingAccountPK(); // Not updated
            var partyPK = billingAccountRole.getPartyPK(); // Not updated
            var partyContactMechanismPK = billingAccountRoleValue.getPartyContactMechanismPK();
            var billingAccountRoleTypePK = billingAccountRole.getBillingAccountRoleTypePK(); // Not updated
            
            billingAccountRole = billingAccountRoleFactory.create(billingAccountPK, partyPK, partyContactMechanismPK, billingAccountRoleTypePK,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(billingAccountPK, EventTypes.MODIFY, billingAccountRole.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteBillingAccountRole(BillingAccountRole billingAccountRole, BasePK deletedBy) {
        billingAccountRole.setThruTime(session.getStartTime());
        
        sendEvent(billingAccountRole.getBillingAccountPK(), EventTypes.MODIFY, billingAccountRole.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteBillingAccountRolesByBillingAccount(BillingAccount billingAccount, BasePK deletedBy) {
        getBillingAccountRolesByBillingAccountForUpdate(billingAccount).forEach((billingAccountRole) -> {
            deleteBillingAccountRole(billingAccountRole, deletedBy);
        });
    }
    
    public void deleteBillingAccountRolesByPartyContactMechanism(PartyContactMechanism partyContactMechanism, BasePK deletedBy) {
        getBillingAccountRolesByPartyContactMechanismForUpdate(partyContactMechanism).forEach((billingAccountRole) -> {
            deleteBillingAccountRole(billingAccountRole, deletedBy);
        });
    }

}

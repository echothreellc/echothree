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

package com.echothree.model.control.financial.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.financial.common.choice.FinancialAccountAliasTypeChoicesBean;
import com.echothree.model.control.financial.common.choice.FinancialAccountTransactionTypeChoicesBean;
import com.echothree.model.control.financial.common.choice.FinancialAccountTypeChoicesBean;
import com.echothree.model.control.financial.common.transfer.FinancialAccountAliasTransfer;
import com.echothree.model.control.financial.common.transfer.FinancialAccountAliasTypeDescriptionTransfer;
import com.echothree.model.control.financial.common.transfer.FinancialAccountAliasTypeTransfer;
import com.echothree.model.control.financial.common.transfer.FinancialAccountRoleTransfer;
import com.echothree.model.control.financial.common.transfer.FinancialAccountRoleTypeTransfer;
import com.echothree.model.control.financial.common.transfer.FinancialAccountTransactionTransfer;
import com.echothree.model.control.financial.common.transfer.FinancialAccountTransactionTypeDescriptionTransfer;
import com.echothree.model.control.financial.common.transfer.FinancialAccountTransactionTypeTransfer;
import com.echothree.model.control.financial.common.transfer.FinancialAccountTransfer;
import com.echothree.model.control.financial.common.transfer.FinancialAccountTypeDescriptionTransfer;
import com.echothree.model.control.financial.common.transfer.FinancialAccountTypeTransfer;
import com.echothree.model.control.financial.server.transfer.FinancialAccountAliasTransferCache;
import com.echothree.model.control.financial.server.transfer.FinancialAccountAliasTypeDescriptionTransferCache;
import com.echothree.model.control.financial.server.transfer.FinancialAccountAliasTypeTransferCache;
import com.echothree.model.control.financial.server.transfer.FinancialAccountRoleTransferCache;
import com.echothree.model.control.financial.server.transfer.FinancialAccountRoleTypeTransferCache;
import com.echothree.model.control.financial.server.transfer.FinancialAccountTransactionTransferCache;
import com.echothree.model.control.financial.server.transfer.FinancialAccountTransactionTypeDescriptionTransferCache;
import com.echothree.model.control.financial.server.transfer.FinancialAccountTransactionTypeTransferCache;
import com.echothree.model.control.financial.server.transfer.FinancialAccountTransferCache;
import com.echothree.model.control.financial.server.transfer.FinancialAccountTypeDescriptionTransferCache;
import com.echothree.model.control.financial.server.transfer.FinancialAccountTypeTransferCache;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.financial.server.entity.FinancialAccount;
import com.echothree.model.data.financial.server.entity.FinancialAccountAlias;
import com.echothree.model.data.financial.server.entity.FinancialAccountAliasType;
import com.echothree.model.data.financial.server.entity.FinancialAccountAliasTypeDescription;
import com.echothree.model.data.financial.server.entity.FinancialAccountRole;
import com.echothree.model.data.financial.server.entity.FinancialAccountRoleType;
import com.echothree.model.data.financial.server.entity.FinancialAccountRoleTypeDescription;
import com.echothree.model.data.financial.server.entity.FinancialAccountStatus;
import com.echothree.model.data.financial.server.entity.FinancialAccountTransaction;
import com.echothree.model.data.financial.server.entity.FinancialAccountTransactionType;
import com.echothree.model.data.financial.server.entity.FinancialAccountTransactionTypeDescription;
import com.echothree.model.data.financial.server.entity.FinancialAccountType;
import com.echothree.model.data.financial.server.entity.FinancialAccountTypeDescription;
import com.echothree.model.data.financial.server.factory.FinancialAccountAliasFactory;
import com.echothree.model.data.financial.server.factory.FinancialAccountAliasTypeDescriptionFactory;
import com.echothree.model.data.financial.server.factory.FinancialAccountAliasTypeDetailFactory;
import com.echothree.model.data.financial.server.factory.FinancialAccountAliasTypeFactory;
import com.echothree.model.data.financial.server.factory.FinancialAccountDetailFactory;
import com.echothree.model.data.financial.server.factory.FinancialAccountFactory;
import com.echothree.model.data.financial.server.factory.FinancialAccountRoleFactory;
import com.echothree.model.data.financial.server.factory.FinancialAccountRoleTypeDescriptionFactory;
import com.echothree.model.data.financial.server.factory.FinancialAccountRoleTypeFactory;
import com.echothree.model.data.financial.server.factory.FinancialAccountStatusFactory;
import com.echothree.model.data.financial.server.factory.FinancialAccountTransactionDetailFactory;
import com.echothree.model.data.financial.server.factory.FinancialAccountTransactionFactory;
import com.echothree.model.data.financial.server.factory.FinancialAccountTransactionTypeDescriptionFactory;
import com.echothree.model.data.financial.server.factory.FinancialAccountTransactionTypeDetailFactory;
import com.echothree.model.data.financial.server.factory.FinancialAccountTransactionTypeFactory;
import com.echothree.model.data.financial.server.factory.FinancialAccountTypeDescriptionFactory;
import com.echothree.model.data.financial.server.factory.FinancialAccountTypeDetailFactory;
import com.echothree.model.data.financial.server.factory.FinancialAccountTypeFactory;
import com.echothree.model.data.financial.server.value.FinancialAccountAliasTypeDescriptionValue;
import com.echothree.model.data.financial.server.value.FinancialAccountAliasTypeDetailValue;
import com.echothree.model.data.financial.server.value.FinancialAccountAliasValue;
import com.echothree.model.data.financial.server.value.FinancialAccountDetailValue;
import com.echothree.model.data.financial.server.value.FinancialAccountRoleValue;
import com.echothree.model.data.financial.server.value.FinancialAccountTransactionDetailValue;
import com.echothree.model.data.financial.server.value.FinancialAccountTransactionTypeDescriptionValue;
import com.echothree.model.data.financial.server.value.FinancialAccountTransactionTypeDetailValue;
import com.echothree.model.data.financial.server.value.FinancialAccountTypeDescriptionValue;
import com.echothree.model.data.financial.server.value.FinancialAccountTypeDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import static com.echothree.model.jooq.server.keys.financial.FinancialForeignKeys.FINANCIAL_ACCOUNTS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.financial.FinancialForeignKeys.FINANCIAL_ACCOUNTS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.financial.FinancialForeignKeys.FINANCIAL_ACCOUNT_ALIASES_FINANCIAL_ACCOUNT_ALIAS_TYPE_FK;
import static com.echothree.model.jooq.server.keys.financial.FinancialForeignKeys.FINANCIAL_ACCOUNT_ALIASES_FINANCIAL_ACCOUNT_FK;
import static com.echothree.model.jooq.server.keys.financial.FinancialForeignKeys.FINANCIAL_ACCOUNT_ALIAS_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.financial.FinancialForeignKeys.FINANCIAL_ACCOUNT_ALIAS_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.financial.FinancialForeignKeys.FINANCIAL_ACCOUNT_ALIAS_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.financial.FinancialForeignKeys.FINANCIAL_ACCOUNT_DETAILS_FINANCIAL_ACCOUNT_TYPE_FK;
import static com.echothree.model.jooq.server.keys.financial.FinancialForeignKeys.FINANCIAL_ACCOUNT_ROLES_FINANCIAL_ACCOUNT_ROLE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.financial.FinancialForeignKeys.FINANCIAL_ACCOUNT_ROLES_PARTY_FK;
import static com.echothree.model.jooq.server.keys.financial.FinancialForeignKeys.FINANCIAL_ACCOUNT_TRANSACTIONS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.financial.FinancialForeignKeys.FINANCIAL_ACCOUNT_TRANSACTION_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.financial.FinancialForeignKeys.FINANCIAL_ACCOUNT_TRANSACTION_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.financial.FinancialForeignKeys.FINANCIAL_ACCOUNT_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.financial.FinancialForeignKeys.FINANCIAL_ACCOUNT_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.financial.FinancialForeignKeys.FINANCIAL_ACCOUNT_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTIES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.financial.FinancialAccountAliasTypeDescriptions.FinancialAccountAliasTypeDescriptions;
import static com.echothree.model.jooq.server.tables.financial.FinancialAccountAliasTypeDetails.FinancialAccountAliasTypeDetails;
import static com.echothree.model.jooq.server.tables.financial.FinancialAccountAliasTypes.FinancialAccountAliasTypes;
import static com.echothree.model.jooq.server.tables.financial.FinancialAccountAliases.FinancialAccountAliases;
import static com.echothree.model.jooq.server.tables.financial.FinancialAccountDetails.FinancialAccountDetails;
import static com.echothree.model.jooq.server.tables.financial.FinancialAccountRoleTypeDescriptions.FinancialAccountRoleTypeDescriptions;
import static com.echothree.model.jooq.server.tables.financial.FinancialAccountRoleTypes.FinancialAccountRoleTypes;
import static com.echothree.model.jooq.server.tables.financial.FinancialAccountRoles.FinancialAccountRoles;
import static com.echothree.model.jooq.server.tables.financial.FinancialAccountStatuses.FinancialAccountStatuses;
import static com.echothree.model.jooq.server.tables.financial.FinancialAccountTransactionDetails.FinancialAccountTransactionDetails;
import static com.echothree.model.jooq.server.tables.financial.FinancialAccountTransactionTypeDescriptions.FinancialAccountTransactionTypeDescriptions;
import static com.echothree.model.jooq.server.tables.financial.FinancialAccountTransactionTypeDetails.FinancialAccountTransactionTypeDetails;
import static com.echothree.model.jooq.server.tables.financial.FinancialAccountTransactionTypes.FinancialAccountTransactionTypes;
import static com.echothree.model.jooq.server.tables.financial.FinancialAccountTransactions.FinancialAccountTransactions;
import static com.echothree.model.jooq.server.tables.financial.FinancialAccountTypeDescriptions.FinancialAccountTypeDescriptions;
import static com.echothree.model.jooq.server.tables.financial.FinancialAccountTypeDetails.FinancialAccountTypeDetails;
import static com.echothree.model.jooq.server.tables.financial.FinancialAccountTypes.FinancialAccountTypes;
import static com.echothree.model.jooq.server.tables.financial.FinancialAccounts.FinancialAccounts;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.party.Parties.Parties;
import static com.echothree.model.jooq.server.tables.party.PartyDetails.PartyDetails;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.inject.Inject;

@CommandScope
public class FinancialControl
        extends BaseModelControl {
    
    /** Creates a new instance of FinancialControl */
    protected FinancialControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Financial Account Role Types
    // --------------------------------------------------------------------------------

    @Inject
    protected FinancialAccountRoleTypeFactory financialAccountRoleTypeFactory;

    @Inject
    FinancialAccountRoleTypeTransferCache financialAccountRoleTypeTransferCache;

    public FinancialAccountRoleType createFinancialAccountRoleType(String financialAccountRoleTypeName, Integer sortOrder) {
        return financialAccountRoleTypeFactory.create(financialAccountRoleTypeName, sortOrder);
    }
    
    private List<FinancialAccountRoleType> getFinancialAccountRoleTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FinancialAccountRoleTypes.fields())
                .from(FinancialAccountRoleTypes);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(FinancialAccountRoleTypes.SORT_ORDER, FinancialAccountRoleTypes.FINANCIAL_ACCOUNT_ROLE_TYPE_NAME),
                    FinancialAccountRoleTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return financialAccountRoleTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<FinancialAccountRoleType> getFinancialAccountRoleTypes() {
        return getFinancialAccountRoleTypes(EntityPermission.READ_ONLY);
    }
    
    public List<FinancialAccountRoleType> getFinancialAccountRoleTypesForUpdate() {
        return getFinancialAccountRoleTypes(EntityPermission.READ_WRITE);
    }
    
    private FinancialAccountRoleType getFinancialAccountRoleTypeByName(String financialAccountRoleTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FinancialAccountRoleTypes.fields())
                .from(FinancialAccountRoleTypes)
                .where(FinancialAccountRoleTypes.FINANCIAL_ACCOUNT_ROLE_TYPE_NAME.eq(financialAccountRoleTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return financialAccountRoleTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public FinancialAccountRoleType getFinancialAccountRoleTypeByName(String financialAccountRoleTypeName) {
        return getFinancialAccountRoleTypeByName(financialAccountRoleTypeName, EntityPermission.READ_ONLY);
    }
    
    public FinancialAccountRoleType getFinancialAccountRoleTypeByNameForUpdate(String financialAccountRoleTypeName) {
        return getFinancialAccountRoleTypeByName(financialAccountRoleTypeName, EntityPermission.READ_WRITE);
    }
    
    public FinancialAccountRoleTypeTransfer getFinancialAccountRoleTypeTransfer(UserVisit userVisit,
            FinancialAccountRoleType financialAccountRoleType) {
        return financialAccountRoleTypeTransferCache.getFinancialAccountRoleTypeTransfer(userVisit, financialAccountRoleType);
    }
    
    private List<FinancialAccountRoleTypeTransfer> getFinancialAccountRoleTypeTransfers(final UserVisit userVisit, final List<FinancialAccountRoleType> financialAccountRoleTypes) {
        List<FinancialAccountRoleTypeTransfer> financialAccountRoleTypeTransfers = new ArrayList<>(financialAccountRoleTypes.size());
        
        financialAccountRoleTypes.forEach((financialAccountRoleType) ->
                financialAccountRoleTypeTransfers.add(financialAccountRoleTypeTransferCache.getFinancialAccountRoleTypeTransfer(userVisit, financialAccountRoleType))
        );

            return financialAccountRoleTypeTransfers;
    }
    
    public List<FinancialAccountRoleTypeTransfer> getFinancialAccountRoleTypeTransfers(UserVisit userVisit) {
        return getFinancialAccountRoleTypeTransfers(userVisit, getFinancialAccountRoleTypes());
    }
    
    // --------------------------------------------------------------------------------
    //   Financial Account Role Type Description
    // --------------------------------------------------------------------------------

    @Inject
    protected FinancialAccountRoleTypeDescriptionFactory financialAccountRoleTypeDescriptionFactory;

    public FinancialAccountRoleTypeDescription createFinancialAccountRoleTypeDescription(FinancialAccountRoleType financialAccountRoleType, Language language, String description) {
        return financialAccountRoleTypeDescriptionFactory.create(financialAccountRoleType, language, description);
    }
    
    private FinancialAccountRoleTypeDescription getFinancialAccountRoleTypeDescription(FinancialAccountRoleType financialAccountRoleType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FinancialAccountRoleTypeDescriptions.fields())
                .from(FinancialAccountRoleTypeDescriptions)
                .where(FinancialAccountRoleTypeDescriptions.FINANCIAL_ACCOUNT_ROLE_TYPE.eq(financialAccountRoleType.getPrimaryKey()),
                        FinancialAccountRoleTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return financialAccountRoleTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public FinancialAccountRoleTypeDescription getFinancialAccountRoleTypeDescription(FinancialAccountRoleType financialAccountRoleType, Language language) {
        return getFinancialAccountRoleTypeDescription(financialAccountRoleType, language, EntityPermission.READ_ONLY);
    }
    
    public FinancialAccountRoleTypeDescription getFinancialAccountRoleTypeDescriptionForUpdate(FinancialAccountRoleType financialAccountRoleType, Language language) {
        return getFinancialAccountRoleTypeDescription(financialAccountRoleType, language, EntityPermission.READ_WRITE);
    }
    
    public String getBestFinancialAccountRoleTypeDescription(FinancialAccountRoleType financialAccountRoleType, Language language) {
        String description;
        var financialAccountRoleTypeDescription = getFinancialAccountRoleTypeDescription(financialAccountRoleType, language);
        
        if(financialAccountRoleTypeDescription == null && !language.getIsDefault()) {
            financialAccountRoleTypeDescription = getFinancialAccountRoleTypeDescription(financialAccountRoleType, partyControl.getDefaultLanguage());
        }
        
        if(financialAccountRoleTypeDescription == null) {
            description = financialAccountRoleType.getFinancialAccountRoleTypeName();
        } else {
            description = financialAccountRoleTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Financial Account Types
    // --------------------------------------------------------------------------------

    @Inject
    protected FinancialAccountTypeDetailFactory financialAccountTypeDetailFactory;

    @Inject
    protected FinancialAccountTypeFactory financialAccountTypeFactory;

    @Inject
    FinancialAccountTypeTransferCache financialAccountTypeTransferCache;

    public FinancialAccountType createFinancialAccountType(String financialAccountTypeName, FinancialAccountType parentFinancialAccountType,
            GlAccount defaultGlAccount, SequenceType financialAccountSequenceType, SequenceType financialAccountTransactionSequenceType,
            Workflow financialAccountWorkflow, WorkflowEntrance financialAccountWorkflowEntrance, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultFinancialAccountType = getDefaultFinancialAccountType();
        var defaultFound = defaultFinancialAccountType != null;
        
        if(defaultFound && isDefault) {
            var defaultFinancialAccountTypeDetailValue = getDefaultFinancialAccountTypeDetailValueForUpdate();
            
            defaultFinancialAccountTypeDetailValue.setIsDefault(false);
            updateFinancialAccountTypeFromValue(defaultFinancialAccountTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var financialAccountType = financialAccountTypeFactory.create();
        var financialAccountTypeDetail = financialAccountTypeDetailFactory.create(financialAccountType,
                financialAccountTypeName, parentFinancialAccountType, defaultGlAccount, financialAccountSequenceType, financialAccountTransactionSequenceType,
                financialAccountWorkflow, financialAccountWorkflowEntrance, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        financialAccountType = financialAccountTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                financialAccountType.getPrimaryKey());
        financialAccountType.setActiveDetail(financialAccountTypeDetail);
        financialAccountType.setLastDetail(financialAccountTypeDetail);
        financialAccountType.store();
        
        sendEvent(financialAccountType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return financialAccountType;
    }
    
    private FinancialAccountType getFinancialAccountTypeByName(String financialAccountTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FinancialAccountTypes.fields())
                .from(FinancialAccountTypes)
                .join(FinancialAccountTypeDetails).onKey(FINANCIAL_ACCOUNT_TYPES_ACTIVE_DETAIL_FK)
                .where(FinancialAccountTypeDetails.FINANCIAL_ACCOUNT_TYPE_NAME.eq(financialAccountTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return financialAccountTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public FinancialAccountType getFinancialAccountTypeByName(String financialAccountTypeName) {
        return getFinancialAccountTypeByName(financialAccountTypeName, EntityPermission.READ_ONLY);
    }

    public FinancialAccountType getFinancialAccountTypeByNameForUpdate(String financialAccountTypeName) {
        return getFinancialAccountTypeByName(financialAccountTypeName, EntityPermission.READ_WRITE);
    }

    public FinancialAccountTypeDetailValue getFinancialAccountTypeDetailValueForUpdate(FinancialAccountType financialAccountType) {
        return financialAccountType == null? null: financialAccountType.getLastDetailForUpdate().getFinancialAccountTypeDetailValue().clone();
    }

    public FinancialAccountTypeDetailValue getFinancialAccountTypeDetailValueByNameForUpdate(String financialAccountTypeName) {
        return getFinancialAccountTypeDetailValueForUpdate(getFinancialAccountTypeByNameForUpdate(financialAccountTypeName));
    }

    private FinancialAccountType getDefaultFinancialAccountType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FinancialAccountTypes.fields())
                .from(FinancialAccountTypes)
                .join(FinancialAccountTypeDetails).onKey(FINANCIAL_ACCOUNT_TYPES_ACTIVE_DETAIL_FK)
                .where(FinancialAccountTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return financialAccountTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public FinancialAccountType getDefaultFinancialAccountType() {
        return getDefaultFinancialAccountType(EntityPermission.READ_ONLY);
    }

    public FinancialAccountType getDefaultFinancialAccountTypeForUpdate() {
        return getDefaultFinancialAccountType(EntityPermission.READ_WRITE);
    }

    public FinancialAccountTypeDetailValue getDefaultFinancialAccountTypeDetailValueForUpdate() {
        return getDefaultFinancialAccountTypeForUpdate().getLastDetailForUpdate().getFinancialAccountTypeDetailValue().clone();
    }

    private List<FinancialAccountType> getFinancialAccountTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FinancialAccountTypes.fields())
                .from(FinancialAccountTypes)
                .join(FinancialAccountTypeDetails).onKey(FINANCIAL_ACCOUNT_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(FinancialAccountTypeDetails.SORT_ORDER, FinancialAccountTypeDetails.FINANCIAL_ACCOUNT_TYPE_NAME),
                    FinancialAccountTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return financialAccountTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<FinancialAccountType> getFinancialAccountTypes() {
        return getFinancialAccountTypes(EntityPermission.READ_ONLY);
    }

    public List<FinancialAccountType> getFinancialAccountTypesForUpdate() {
        return getFinancialAccountTypes(EntityPermission.READ_WRITE);
    }

    private List<FinancialAccountType> getFinancialAccountTypesByParentFinancialAccountType(FinancialAccountType parentFinancialAccountType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FinancialAccountTypes.fields())
                .from(FinancialAccountTypes)
                .join(FinancialAccountTypeDetails).onKey(FINANCIAL_ACCOUNT_TYPES_ACTIVE_DETAIL_FK)
                .where(FinancialAccountTypeDetails.PARENT_FINANCIAL_ACCOUNT_TYPE.eq(parentFinancialAccountType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(FinancialAccountTypeDetails.SORT_ORDER, FinancialAccountTypeDetails.FINANCIAL_ACCOUNT_TYPE_NAME),
                    FinancialAccountTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return financialAccountTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<FinancialAccountType> getFinancialAccountTypesByParentFinancialAccountType(FinancialAccountType parentFinancialAccountType) {
        return getFinancialAccountTypesByParentFinancialAccountType(parentFinancialAccountType, EntityPermission.READ_ONLY);
    }

    public List<FinancialAccountType> getFinancialAccountTypesByParentFinancialAccountTypeForUpdate(FinancialAccountType parentFinancialAccountType) {
        return getFinancialAccountTypesByParentFinancialAccountType(parentFinancialAccountType, EntityPermission.READ_WRITE);
    }

    public FinancialAccountTypeTransfer getFinancialAccountTypeTransfer(UserVisit userVisit, FinancialAccountType financialAccountType) {
        return financialAccountTypeTransferCache.getFinancialAccountTypeTransfer(userVisit, financialAccountType);
    }
    
    public List<FinancialAccountTypeTransfer> getFinancialAccountTypeTransfers(UserVisit userVisit) {
        var financialAccountTypes = getFinancialAccountTypes();
        List<FinancialAccountTypeTransfer> financialAccountTypeTransfers = new ArrayList<>(financialAccountTypes.size());
        
        financialAccountTypes.forEach((financialAccountType) ->
                financialAccountTypeTransfers.add(financialAccountTypeTransferCache.getFinancialAccountTypeTransfer(userVisit, financialAccountType))
        );
        
        return financialAccountTypeTransfers;
    }
    
    public FinancialAccountTypeChoicesBean getFinancialAccountTypeChoices(String defaultFinancialAccountTypeChoice,
            Language language, boolean allowNullChoice) {
        var financialAccountTypes = getFinancialAccountTypes();
        var size = financialAccountTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultFinancialAccountTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var financialAccountType : financialAccountTypes) {
            var financialAccountTypeDetail = financialAccountType.getLastDetail();
            
            var label = getBestFinancialAccountTypeDescription(financialAccountType, language);
            var value = financialAccountTypeDetail.getFinancialAccountTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultFinancialAccountTypeChoice != null && defaultFinancialAccountTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && financialAccountTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new FinancialAccountTypeChoicesBean(labels, values, defaultValue);
    }
    
    public boolean isParentFinancialAccountTypeSafe(FinancialAccountType financialAccountType,
            FinancialAccountType parentFinancialAccountType) {
        var safe = true;
        
        if(parentFinancialAccountType != null) {
            Set<FinancialAccountType> parentFinancialAccountTypes = new HashSet<>();
            
            parentFinancialAccountTypes.add(financialAccountType);
            do {
                if(parentFinancialAccountTypes.contains(parentFinancialAccountType)) {
                    safe = false;
                    break;
                }
                
                parentFinancialAccountTypes.add(parentFinancialAccountType);
                parentFinancialAccountType = parentFinancialAccountType.getLastDetail().getParentFinancialAccountType();
            } while(parentFinancialAccountType != null);
        }
        
        return safe;
    }
    
    private void updateFinancialAccountTypeFromValue(FinancialAccountTypeDetailValue financialAccountTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(financialAccountTypeDetailValue.hasBeenModified()) {
            var financialAccountType = financialAccountTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     financialAccountTypeDetailValue.getFinancialAccountTypePK());
            var financialAccountTypeDetail = financialAccountType.getActiveDetailForUpdate();
            
            financialAccountTypeDetail.setThruTime(session.getStartTime());
            financialAccountTypeDetail.store();

            var financialAccountTypePK = financialAccountTypeDetail.getFinancialAccountTypePK(); // Not updated
            var financialAccountTypeName = financialAccountTypeDetailValue.getFinancialAccountTypeName();
            var parentFinancialAccountTypePK = financialAccountTypeDetailValue.getParentFinancialAccountTypePK();
            var defaultGlAccountPK = financialAccountTypeDetailValue.getDefaultGlAccountPK();
            var financialAccountSequenceTypePK = financialAccountTypeDetailValue.getFinancialAccountSequenceTypePK();
            var financialAccountTransactionSequenceTypePK = financialAccountTypeDetailValue.getFinancialAccountTransactionSequenceTypePK();
            var financialAccountWorkflowPK = financialAccountTypeDetailValue.getFinancialAccountWorkflowPK();
            var financialAccountWorkflowEntrancePK = financialAccountTypeDetailValue.getFinancialAccountWorkflowEntrancePK();
            var isDefault = financialAccountTypeDetailValue.getIsDefault();
            var sortOrder = financialAccountTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultFinancialAccountType = getDefaultFinancialAccountType();
                var defaultFound = defaultFinancialAccountType != null && !defaultFinancialAccountType.equals(financialAccountType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultFinancialAccountTypeDetailValue = getDefaultFinancialAccountTypeDetailValueForUpdate();
                    
                    defaultFinancialAccountTypeDetailValue.setIsDefault(false);
                    updateFinancialAccountTypeFromValue(defaultFinancialAccountTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            financialAccountTypeDetail = financialAccountTypeDetailFactory.create(financialAccountTypePK, financialAccountTypeName,
                    parentFinancialAccountTypePK, defaultGlAccountPK, financialAccountSequenceTypePK, financialAccountTransactionSequenceTypePK,
                    financialAccountWorkflowPK, financialAccountWorkflowEntrancePK, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            financialAccountType.setActiveDetail(financialAccountTypeDetail);
            financialAccountType.setLastDetail(financialAccountTypeDetail);
            
            sendEvent(financialAccountTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateFinancialAccountTypeFromValue(FinancialAccountTypeDetailValue financialAccountTypeDetailValue, BasePK updatedBy) {
        updateFinancialAccountTypeFromValue(financialAccountTypeDetailValue, true, updatedBy);
    }
    
    private void deleteFinancialAccountType(FinancialAccountType financialAccountType, boolean checkDefault, BasePK deletedBy) {
        var financialAccountTypeDetail = financialAccountType.getLastDetailForUpdate();

        deleteFinancialAccountTypesByParentFinancialAccountType(financialAccountType, deletedBy);
        deleteFinancialAccountTypeDescriptionsByFinancialAccountType(financialAccountType, deletedBy);
        deleteFinancialAccountsByFinancialAccountType(financialAccountType, deletedBy);
        deleteFinancialAccountAliasTypesByFinancialAccountType(financialAccountType, deletedBy);
        
        financialAccountTypeDetail.setThruTime(session.getStartTime());
        financialAccountType.setActiveDetail(null);
        financialAccountType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultFinancialAccountType = getDefaultFinancialAccountType();

            if(defaultFinancialAccountType == null) {
                var financialAccountTypes = getFinancialAccountTypesForUpdate();

                if(!financialAccountTypes.isEmpty()) {
                    var iter = financialAccountTypes.iterator();
                    if(iter.hasNext()) {
                        defaultFinancialAccountType = iter.next();
                    }
                    var financialAccountTypeDetailValue = Objects.requireNonNull(defaultFinancialAccountType).getLastDetailForUpdate().getFinancialAccountTypeDetailValue().clone();

                    financialAccountTypeDetailValue.setIsDefault(true);
                    updateFinancialAccountTypeFromValue(financialAccountTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(financialAccountType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteFinancialAccountType(FinancialAccountType financialAccountType, BasePK deletedBy) {
        deleteFinancialAccountType(financialAccountType, true, deletedBy);
    }

    private void deleteFinancialAccountTypes(List<FinancialAccountType> financialAccountTypes, boolean checkDefault, BasePK deletedBy) {
        financialAccountTypes.forEach((financialAccountType) -> deleteFinancialAccountType(financialAccountType, checkDefault, deletedBy));
    }

    public void deleteFinancialAccountTypes(List<FinancialAccountType> financialAccountTypes, BasePK deletedBy) {
        deleteFinancialAccountTypes(financialAccountTypes, true, deletedBy);
    }

    private void deleteFinancialAccountTypesByParentFinancialAccountType(FinancialAccountType parentFinancialAccountType, BasePK deletedBy) {
        deleteFinancialAccountTypes(getFinancialAccountTypesByParentFinancialAccountTypeForUpdate(parentFinancialAccountType), false, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Financial Account Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected FinancialAccountTypeDescriptionFactory financialAccountTypeDescriptionFactory;

    @Inject
    FinancialAccountTypeDescriptionTransferCache financialAccountTypeDescriptionTransferCache;

    public FinancialAccountTypeDescription createFinancialAccountTypeDescription(FinancialAccountType financialAccountType, Language language, String description, BasePK createdBy) {
        var financialAccountTypeDescription = financialAccountTypeDescriptionFactory.create(financialAccountType, language, description,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(financialAccountType.getPrimaryKey(), EventTypes.MODIFY, financialAccountTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return financialAccountTypeDescription;
    }
    
    private FinancialAccountTypeDescription getFinancialAccountTypeDescription(FinancialAccountType financialAccountType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FinancialAccountTypeDescriptions.fields())
                .from(FinancialAccountTypeDescriptions)
                .where(FinancialAccountTypeDescriptions.FINANCIAL_ACCOUNT_TYPE.eq(financialAccountType.getPrimaryKey()),
                        FinancialAccountTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        FinancialAccountTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return financialAccountTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public FinancialAccountTypeDescription getFinancialAccountTypeDescription(FinancialAccountType financialAccountType, Language language) {
        return getFinancialAccountTypeDescription(financialAccountType, language, EntityPermission.READ_ONLY);
    }
    
    public FinancialAccountTypeDescription getFinancialAccountTypeDescriptionForUpdate(FinancialAccountType financialAccountType, Language language) {
        return getFinancialAccountTypeDescription(financialAccountType, language, EntityPermission.READ_WRITE);
    }
    
    public FinancialAccountTypeDescriptionValue getFinancialAccountTypeDescriptionValue(FinancialAccountTypeDescription financialAccountTypeDescription) {
        return financialAccountTypeDescription == null? null: financialAccountTypeDescription.getFinancialAccountTypeDescriptionValue().clone();
    }
    
    public FinancialAccountTypeDescriptionValue getFinancialAccountTypeDescriptionValueForUpdate(FinancialAccountType financialAccountType, Language language) {
        return getFinancialAccountTypeDescriptionValue(getFinancialAccountTypeDescriptionForUpdate(financialAccountType, language));
    }
    
    private List<FinancialAccountTypeDescription> getFinancialAccountTypeDescriptionsByFinancialAccountType(FinancialAccountType financialAccountType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(FinancialAccountTypeDescriptions.fields())
                    .from(FinancialAccountTypeDescriptions)
                    .join(Languages).onKey(FINANCIAL_ACCOUNT_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(FinancialAccountTypeDescriptions.FINANCIAL_ACCOUNT_TYPE.eq(financialAccountType.getPrimaryKey()),
                            FinancialAccountTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    FinancialAccountTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(FinancialAccountTypeDescriptions.fields())
                    .from(FinancialAccountTypeDescriptions)
                    .where(FinancialAccountTypeDescriptions.FINANCIAL_ACCOUNT_TYPE.eq(financialAccountType.getPrimaryKey()),
                            FinancialAccountTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return financialAccountTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<FinancialAccountTypeDescription> getFinancialAccountTypeDescriptionsByFinancialAccountType(FinancialAccountType financialAccountType) {
        return getFinancialAccountTypeDescriptionsByFinancialAccountType(financialAccountType, EntityPermission.READ_ONLY);
    }
    
    public List<FinancialAccountTypeDescription> getFinancialAccountTypeDescriptionsByFinancialAccountTypeForUpdate(FinancialAccountType financialAccountType) {
        return getFinancialAccountTypeDescriptionsByFinancialAccountType(financialAccountType, EntityPermission.READ_WRITE);
    }
    
    public String getBestFinancialAccountTypeDescription(FinancialAccountType financialAccountType, Language language) {
        String description;
        var financialAccountTypeDescription = getFinancialAccountTypeDescription(financialAccountType, language);
        
        if(financialAccountTypeDescription == null && !language.getIsDefault()) {
            financialAccountTypeDescription = getFinancialAccountTypeDescription(financialAccountType, partyControl.getDefaultLanguage());
        }
        
        if(financialAccountTypeDescription == null) {
            description = financialAccountType.getLastDetail().getFinancialAccountTypeName();
        } else {
            description = financialAccountTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public FinancialAccountTypeDescriptionTransfer getFinancialAccountTypeDescriptionTransfer(UserVisit userVisit, FinancialAccountTypeDescription financialAccountTypeDescription) {
        return financialAccountTypeDescriptionTransferCache.getFinancialAccountTypeDescriptionTransfer(userVisit, financialAccountTypeDescription);
    }
    
    public List<FinancialAccountTypeDescriptionTransfer> getFinancialAccountTypeDescriptionTransfersByFinancialAccountType(UserVisit userVisit, FinancialAccountType financialAccountType) {
        var financialAccountTypeDescriptions = getFinancialAccountTypeDescriptionsByFinancialAccountType(financialAccountType);
        List<FinancialAccountTypeDescriptionTransfer> financialAccountTypeDescriptionTransfers = new ArrayList<>(financialAccountTypeDescriptions.size());
        
        financialAccountTypeDescriptions.forEach((financialAccountTypeDescription) ->
                financialAccountTypeDescriptionTransfers.add(financialAccountTypeDescriptionTransferCache.getFinancialAccountTypeDescriptionTransfer(userVisit, financialAccountTypeDescription))
        );
        
        return financialAccountTypeDescriptionTransfers;
    }
    
    public void updateFinancialAccountTypeDescriptionFromValue(FinancialAccountTypeDescriptionValue financialAccountTypeDescriptionValue, BasePK updatedBy) {
        if(financialAccountTypeDescriptionValue.hasBeenModified()) {
            var financialAccountTypeDescription = financialAccountTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    financialAccountTypeDescriptionValue.getPrimaryKey());
            
            financialAccountTypeDescription.setThruTime(session.getStartTime());
            financialAccountTypeDescription.store();

            var financialAccountType = financialAccountTypeDescription.getFinancialAccountType();
            var language = financialAccountTypeDescription.getLanguage();
            var description = financialAccountTypeDescriptionValue.getDescription();
            
            financialAccountTypeDescription = financialAccountTypeDescriptionFactory.create(financialAccountType, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(financialAccountType.getPrimaryKey(), EventTypes.MODIFY, financialAccountTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteFinancialAccountTypeDescription(FinancialAccountTypeDescription financialAccountTypeDescription, BasePK deletedBy) {
        financialAccountTypeDescription.setThruTime(session.getStartTime());
        
        sendEvent(financialAccountTypeDescription.getFinancialAccountTypePK(), EventTypes.MODIFY, financialAccountTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteFinancialAccountTypeDescriptionsByFinancialAccountType(FinancialAccountType financialAccountType, BasePK deletedBy) {
        var financialAccountTypeDescriptions = getFinancialAccountTypeDescriptionsByFinancialAccountTypeForUpdate(financialAccountType);
        
        financialAccountTypeDescriptions.forEach((financialAccountTypeDescription) -> 
                deleteFinancialAccountTypeDescription(financialAccountTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Financial Account Transaction Types
    // --------------------------------------------------------------------------------

    @Inject
    protected FinancialAccountTransactionTypeDetailFactory financialAccountTransactionTypeDetailFactory;

    @Inject
    protected FinancialAccountTransactionTypeFactory financialAccountTransactionTypeFactory;

    @Inject
    FinancialAccountTransactionTypeTransferCache financialAccountTransactionTypeTransferCache;

    public FinancialAccountTransactionType createFinancialAccountTransactionType(FinancialAccountType financialAccountType,
            String financialAccountTransactionTypeName, FinancialAccountTransactionType parentFinancialAccountTransactionType, GlAccount glAccount,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultFinancialAccountTransactionType = getDefaultFinancialAccountTransactionType(financialAccountType);
        var defaultFound = defaultFinancialAccountTransactionType != null;
        
        if(defaultFound && isDefault) {
            var defaultFinancialAccountTransactionTypeDetailValue = getDefaultFinancialAccountTransactionTypeDetailValueForUpdate(financialAccountType);
            
            defaultFinancialAccountTransactionTypeDetailValue.setIsDefault(false);
            updateFinancialAccountTransactionTypeFromValue(defaultFinancialAccountTransactionTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var financialAccountTransactionType = financialAccountTransactionTypeFactory.create();
        var financialAccountTransactionTypeDetail = financialAccountTransactionTypeDetailFactory.create(
                financialAccountTransactionType, financialAccountType, financialAccountTransactionTypeName, parentFinancialAccountTransactionType, glAccount,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        financialAccountTransactionType = financialAccountTransactionTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                financialAccountTransactionType.getPrimaryKey());
        financialAccountTransactionType.setActiveDetail(financialAccountTransactionTypeDetail);
        financialAccountTransactionType.setLastDetail(financialAccountTransactionTypeDetail);
        financialAccountTransactionType.store();
        
        sendEvent(financialAccountTransactionType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return financialAccountTransactionType;
    }
    
    private FinancialAccountTransactionType getFinancialAccountTransactionTypeByName(FinancialAccountType financialAccountType,
            String financialAccountTransactionTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FinancialAccountTransactionTypes.fields())
                .from(FinancialAccountTransactionTypes)
                .join(FinancialAccountTransactionTypeDetails).onKey(FINANCIAL_ACCOUNT_TRANSACTION_TYPES_ACTIVE_DETAIL_FK)
                .where(FinancialAccountTransactionTypeDetails.FINANCIAL_ACCOUNT_TYPE.eq(financialAccountType.getPrimaryKey()),
                        FinancialAccountTransactionTypeDetails.FINANCIAL_ACCOUNT_TRANSACTION_TYPE_NAME.eq(financialAccountTransactionTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return financialAccountTransactionTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public FinancialAccountTransactionType getFinancialAccountTransactionTypeByName(FinancialAccountType financialAccountType,
            String financialAccountTransactionTypeName) {
        return getFinancialAccountTransactionTypeByName(financialAccountType, financialAccountTransactionTypeName, EntityPermission.READ_ONLY);
    }
    
    public FinancialAccountTransactionType getFinancialAccountTransactionTypeByNameForUpdate(FinancialAccountType financialAccountType,
            String financialAccountTransactionTypeName) {
        return getFinancialAccountTransactionTypeByName(financialAccountType, financialAccountTransactionTypeName, EntityPermission.READ_WRITE);
    }
    
    public FinancialAccountTransactionTypeDetailValue getFinancialAccountTransactionTypeDetailValueForUpdate(FinancialAccountTransactionType financialAccountTransactionType) {
        return financialAccountTransactionType == null? null: financialAccountTransactionType.getLastDetailForUpdate().getFinancialAccountTransactionTypeDetailValue().clone();
    }
    
    public FinancialAccountTransactionTypeDetailValue getFinancialAccountTransactionTypeDetailValueByNameForUpdate(FinancialAccountType financialAccountType,
            String financialAccountTransactionTypeName) {
        return getFinancialAccountTransactionTypeDetailValueForUpdate(getFinancialAccountTransactionTypeByNameForUpdate(financialAccountType,
                financialAccountTransactionTypeName));
    }
    
    private FinancialAccountTransactionType getDefaultFinancialAccountTransactionType(FinancialAccountType financialAccountType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FinancialAccountTransactionTypes.fields())
                .from(FinancialAccountTransactionTypes)
                .join(FinancialAccountTransactionTypeDetails).onKey(FINANCIAL_ACCOUNT_TRANSACTION_TYPES_ACTIVE_DETAIL_FK)
                .where(FinancialAccountTransactionTypeDetails.FINANCIAL_ACCOUNT_TYPE.eq(financialAccountType.getPrimaryKey()),
                        FinancialAccountTransactionTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return financialAccountTransactionTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public FinancialAccountTransactionType getDefaultFinancialAccountTransactionType(FinancialAccountType financialAccountType) {
        return getDefaultFinancialAccountTransactionType(financialAccountType, EntityPermission.READ_ONLY);
    }
    
    public FinancialAccountTransactionType getDefaultFinancialAccountTransactionTypeForUpdate(FinancialAccountType financialAccountType) {
        return getDefaultFinancialAccountTransactionType(financialAccountType, EntityPermission.READ_WRITE);
    }
    
    public FinancialAccountTransactionTypeDetailValue getDefaultFinancialAccountTransactionTypeDetailValueForUpdate(FinancialAccountType financialAccountType) {
        return getDefaultFinancialAccountTransactionTypeForUpdate(financialAccountType).getLastDetailForUpdate().getFinancialAccountTransactionTypeDetailValue().clone();
    }
    
    private List<FinancialAccountTransactionType> getFinancialAccountTransactionTypes(FinancialAccountType financialAccountType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FinancialAccountTransactionTypes.fields())
                .from(FinancialAccountTransactionTypes)
                .join(FinancialAccountTransactionTypeDetails).onKey(FINANCIAL_ACCOUNT_TRANSACTION_TYPES_ACTIVE_DETAIL_FK)
                .where(FinancialAccountTransactionTypeDetails.FINANCIAL_ACCOUNT_TYPE.eq(financialAccountType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(FinancialAccountTransactionTypeDetails.SORT_ORDER, FinancialAccountTransactionTypeDetails.FINANCIAL_ACCOUNT_TRANSACTION_TYPE_NAME),
                    FinancialAccountTransactionTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return financialAccountTransactionTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<FinancialAccountTransactionType> getFinancialAccountTransactionTypes(FinancialAccountType financialAccountType) {
        return getFinancialAccountTransactionTypes(financialAccountType, EntityPermission.READ_ONLY);
    }
    
    public List<FinancialAccountTransactionType> getFinancialAccountTransactionTypesForUpdate(FinancialAccountType financialAccountType) {
        return getFinancialAccountTransactionTypes(financialAccountType, EntityPermission.READ_WRITE);
    }
    
    private List<FinancialAccountTransactionType> getFinancialAccountTransactionTypesByParentFinancialAccountTransactionType(FinancialAccountTransactionType parentFinancialAccountTransactionType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FinancialAccountTransactionTypes.fields())
                .from(FinancialAccountTransactionTypes)
                .join(FinancialAccountTransactionTypeDetails).onKey(FINANCIAL_ACCOUNT_TRANSACTION_TYPES_ACTIVE_DETAIL_FK)
                .where(FinancialAccountTransactionTypeDetails.PARENT_FINANCIAL_ACCOUNT_TRANSACTION_TYPE.eq(parentFinancialAccountTransactionType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(FinancialAccountTransactionTypeDetails.SORT_ORDER, FinancialAccountTransactionTypeDetails.FINANCIAL_ACCOUNT_TRANSACTION_TYPE_NAME),
                    FinancialAccountTransactionTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return financialAccountTransactionTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<FinancialAccountTransactionType> getFinancialAccountTransactionTypesByParentFinancialAccountTransactionType(FinancialAccountTransactionType parentFinancialAccountTransactionType) {
        return getFinancialAccountTransactionTypesByParentFinancialAccountTransactionType(parentFinancialAccountTransactionType, EntityPermission.READ_ONLY);
    }

    public List<FinancialAccountTransactionType> getFinancialAccountTransactionTypesByParentFinancialAccountTransactionTypeForUpdate(FinancialAccountTransactionType parentFinancialAccountTransactionType) {
        return getFinancialAccountTransactionTypesByParentFinancialAccountTransactionType(parentFinancialAccountTransactionType, EntityPermission.READ_WRITE);
    }

    public FinancialAccountTransactionTypeTransfer getFinancialAccountTransactionTypeTransfer(UserVisit userVisit,
            FinancialAccountTransactionType financialAccountTransactionType) {
        return financialAccountTransactionTypeTransferCache.getFinancialAccountTransactionTypeTransfer(userVisit, financialAccountTransactionType);
    }
    
    public List<FinancialAccountTransactionTypeTransfer> getFinancialAccountTransactionTypeTransfers(UserVisit userVisit,
            FinancialAccountType financialAccountType) {
        var financialAccountTransactionTypes = getFinancialAccountTransactionTypes(financialAccountType);
        List<FinancialAccountTransactionTypeTransfer> financialAccountTransactionTypeTransfers = new ArrayList<>(financialAccountTransactionTypes.size());
        
        financialAccountTransactionTypes.forEach((financialAccountTransactionType) ->
                financialAccountTransactionTypeTransfers.add(financialAccountTransactionTypeTransferCache.getFinancialAccountTransactionTypeTransfer(userVisit, financialAccountTransactionType))
        );
        
        return financialAccountTransactionTypeTransfers;
    }
    
    public FinancialAccountTransactionTypeChoicesBean getFinancialAccountTransactionTypeChoices(FinancialAccountType financialAccountType,
            String defaultFinancialAccountTransactionTypeChoice, Language language, boolean allowNullChoice) {
        var financialAccountTransactionTypes = getFinancialAccountTransactionTypes(financialAccountType);
        var size = financialAccountTransactionTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultFinancialAccountTransactionTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var financialAccountTransactionType : financialAccountTransactionTypes) {
            var financialAccountTransactionTypeDetail = financialAccountTransactionType.getLastDetail();
            
            var label = getBestFinancialAccountTransactionTypeDescription(financialAccountTransactionType, language);
            var value = financialAccountTransactionTypeDetail.getFinancialAccountTransactionTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultFinancialAccountTransactionTypeChoice != null && defaultFinancialAccountTransactionTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && financialAccountTransactionTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new FinancialAccountTransactionTypeChoicesBean(labels, values, defaultValue);
    }
    
    public boolean isParentFinancialAccountTransactionTypeSafe(FinancialAccountTransactionType financialAccountTransactionType,
            FinancialAccountTransactionType parentFinancialAccountTransactionType) {
        var safe = true;
        
        if(parentFinancialAccountTransactionType != null) {
            Set<FinancialAccountTransactionType> parentFinancialAccountTransactionTypes = new HashSet<>();
            
            parentFinancialAccountTransactionTypes.add(financialAccountTransactionType);
            do {
                if(parentFinancialAccountTransactionTypes.contains(parentFinancialAccountTransactionType)) {
                    safe = false;
                    break;
                }
                
                parentFinancialAccountTransactionTypes.add(parentFinancialAccountTransactionType);
                parentFinancialAccountTransactionType = parentFinancialAccountTransactionType.getLastDetail().getParentFinancialAccountTransactionType();
            } while(parentFinancialAccountTransactionType != null);
        }
        
        return safe;
    }
    
    private void updateFinancialAccountTransactionTypeFromValue(FinancialAccountTransactionTypeDetailValue financialAccountTransactionTypeDetailValue,
            boolean checkDefault, BasePK updatedBy) {
        if(financialAccountTransactionTypeDetailValue.hasBeenModified()) {
            var financialAccountTransactionType = financialAccountTransactionTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     financialAccountTransactionTypeDetailValue.getFinancialAccountTransactionTypePK());
            var financialAccountTransactionTypeDetail = financialAccountTransactionType.getActiveDetailForUpdate();
            
            financialAccountTransactionTypeDetail.setThruTime(session.getStartTime());
            financialAccountTransactionTypeDetail.store();

            var financialAccountTransactionTypePK = financialAccountTransactionTypeDetail.getFinancialAccountTransactionTypePK(); // Not updated
            var financialAccountType = financialAccountTransactionTypeDetail.getFinancialAccountType(); // Not updated
            var financialAccountTypePK = financialAccountType.getPrimaryKey(); // Not updated
            var financialAccountTransactionTypeName = financialAccountTransactionTypeDetailValue.getFinancialAccountTransactionTypeName();
            var parentFinancialAccountTransactionTypePK = financialAccountTransactionTypeDetailValue.getParentFinancialAccountTransactionTypePK();
            var glAccountPK = financialAccountTransactionTypeDetailValue.getGlAccountPK();
            var isDefault = financialAccountTransactionTypeDetailValue.getIsDefault();
            var sortOrder = financialAccountTransactionTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultFinancialAccountTransactionType = getDefaultFinancialAccountTransactionType(financialAccountType);
                var defaultFound = defaultFinancialAccountTransactionType != null && !defaultFinancialAccountTransactionType.equals(financialAccountTransactionType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultFinancialAccountTransactionTypeDetailValue = getDefaultFinancialAccountTransactionTypeDetailValueForUpdate(financialAccountType);
                    
                    defaultFinancialAccountTransactionTypeDetailValue.setIsDefault(false);
                    updateFinancialAccountTransactionTypeFromValue(defaultFinancialAccountTransactionTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            financialAccountTransactionTypeDetail = financialAccountTransactionTypeDetailFactory.create(
                    financialAccountTransactionTypePK, financialAccountTypePK, financialAccountTransactionTypeName, parentFinancialAccountTransactionTypePK,
                    glAccountPK, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            financialAccountTransactionType.setActiveDetail(financialAccountTransactionTypeDetail);
            financialAccountTransactionType.setLastDetail(financialAccountTransactionTypeDetail);
            
            sendEvent(financialAccountTransactionTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateFinancialAccountTransactionTypeFromValue(FinancialAccountTransactionTypeDetailValue financialAccountTransactionTypeDetailValue,
            BasePK updatedBy) {
        updateFinancialAccountTransactionTypeFromValue(financialAccountTransactionTypeDetailValue, true, updatedBy);
    }
    
    private void deleteFinancialAccountTransactionType(FinancialAccountTransactionType financialAccountTransactionType, boolean checkDefault, BasePK deletedBy) {
        var financialAccountTransactionTypeDetail = financialAccountTransactionType.getLastDetailForUpdate();

        deleteFinancialAccountTransactionTypesByParentFinancialAccountTransactionType(financialAccountTransactionType, deletedBy);
        deleteFinancialAccountTransactionTypeDescriptionsByFinancialAccountTransactionType(financialAccountTransactionType, deletedBy);
        deleteFinancialAccountTransactionsByFinancialAccountTransactionType(financialAccountTransactionType, deletedBy);
        
        financialAccountTransactionTypeDetail.setThruTime(session.getStartTime());
        financialAccountTransactionType.setActiveDetail(null);
        financialAccountTransactionType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var financialAccountType = financialAccountTransactionTypeDetail.getFinancialAccountType();
            var defaultFinancialAccountTransactionType = getDefaultFinancialAccountTransactionType(financialAccountType);

            if(defaultFinancialAccountTransactionType == null) {
                var financialAccountTransactionTypes = getFinancialAccountTransactionTypesForUpdate(financialAccountType);

                if(!financialAccountTransactionTypes.isEmpty()) {
                    var iter = financialAccountTransactionTypes.iterator();
                    if(iter.hasNext()) {
                        defaultFinancialAccountTransactionType = iter.next();
                    }
                    var financialAccountTransactionTypeDetailValue = Objects.requireNonNull(defaultFinancialAccountTransactionType).getLastDetailForUpdate().getFinancialAccountTransactionTypeDetailValue().clone();

                    financialAccountTransactionTypeDetailValue.setIsDefault(true);
                    updateFinancialAccountTransactionTypeFromValue(financialAccountTransactionTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(financialAccountTransactionType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteFinancialAccountTransactionType(FinancialAccountTransactionType financialAccountTransactionType, BasePK deletedBy) {
        deleteFinancialAccountTransactionType(financialAccountTransactionType, true, deletedBy);
    }

    private void deleteFinancialAccountTransactionTypes(List<FinancialAccountTransactionType> financialAccountTransactionTypes, boolean checkDefault, BasePK deletedBy) {
        financialAccountTransactionTypes.forEach((financialAccountTransactionType) -> deleteFinancialAccountTransactionType(financialAccountTransactionType, checkDefault, deletedBy));
    }

    public void deleteFinancialAccountTransactionTypes(List<FinancialAccountTransactionType> financialAccountTransactionTypes, BasePK deletedBy) {
        deleteFinancialAccountTransactionTypes(financialAccountTransactionTypes, true, deletedBy);
    }

    private void deleteFinancialAccountTransactionTypesByParentFinancialAccountTransactionType(FinancialAccountTransactionType parentFinancialAccountTransactionType, BasePK deletedBy) {
        deleteFinancialAccountTransactionTypes(getFinancialAccountTransactionTypesByParentFinancialAccountTransactionTypeForUpdate(parentFinancialAccountTransactionType), false, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Financial Account Transaction Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected FinancialAccountTransactionTypeDescriptionFactory financialAccountTransactionTypeDescriptionFactory;

    @Inject
    FinancialAccountTransactionTypeDescriptionTransferCache financialAccountTransactionTypeDescriptionTransferCache;

    public FinancialAccountTransactionTypeDescription createFinancialAccountTransactionTypeDescription(FinancialAccountTransactionType financialAccountTransactionType,
            Language language, String description, BasePK createdBy) {
        var financialAccountTransactionTypeDescription = financialAccountTransactionTypeDescriptionFactory.create(
                financialAccountTransactionType, language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(financialAccountTransactionType.getPrimaryKey(), EventTypes.MODIFY, financialAccountTransactionTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return financialAccountTransactionTypeDescription;
    }
    
    private FinancialAccountTransactionTypeDescription getFinancialAccountTransactionTypeDescription(FinancialAccountTransactionType financialAccountTransactionType,
            Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FinancialAccountTransactionTypeDescriptions.fields())
                .from(FinancialAccountTransactionTypeDescriptions)
                .where(FinancialAccountTransactionTypeDescriptions.FINANCIAL_ACCOUNT_TRANSACTION_TYPE.eq(financialAccountTransactionType.getPrimaryKey()),
                        FinancialAccountTransactionTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        FinancialAccountTransactionTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return financialAccountTransactionTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public FinancialAccountTransactionTypeDescription getFinancialAccountTransactionTypeDescription(FinancialAccountTransactionType financialAccountTransactionType,
            Language language) {
        return getFinancialAccountTransactionTypeDescription(financialAccountTransactionType, language, EntityPermission.READ_ONLY);
    }
    
    public FinancialAccountTransactionTypeDescription getFinancialAccountTransactionTypeDescriptionForUpdate(FinancialAccountTransactionType financialAccountTransactionType,
            Language language) {
        return getFinancialAccountTransactionTypeDescription(financialAccountTransactionType, language, EntityPermission.READ_WRITE);
    }
    
    public FinancialAccountTransactionTypeDescriptionValue getFinancialAccountTransactionTypeDescriptionValue(FinancialAccountTransactionTypeDescription financialAccountTransactionTypeDescription) {
        return financialAccountTransactionTypeDescription == null? null: financialAccountTransactionTypeDescription.getFinancialAccountTransactionTypeDescriptionValue().clone();
    }
    
    public FinancialAccountTransactionTypeDescriptionValue getFinancialAccountTransactionTypeDescriptionValueForUpdate(FinancialAccountTransactionType financialAccountTransactionType,
            Language language) {
        return getFinancialAccountTransactionTypeDescriptionValue(getFinancialAccountTransactionTypeDescriptionForUpdate(financialAccountTransactionType, language));
    }
    
    private List<FinancialAccountTransactionTypeDescription> getFinancialAccountTransactionTypeDescriptionsByFinancialAccountTransactionType(FinancialAccountTransactionType financialAccountTransactionType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(FinancialAccountTransactionTypeDescriptions.fields())
                    .from(FinancialAccountTransactionTypeDescriptions)
                    .join(Languages).onKey(FINANCIAL_ACCOUNT_TRANSACTION_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(FinancialAccountTransactionTypeDescriptions.FINANCIAL_ACCOUNT_TRANSACTION_TYPE.eq(financialAccountTransactionType.getPrimaryKey()),
                            FinancialAccountTransactionTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    FinancialAccountTransactionTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(FinancialAccountTransactionTypeDescriptions.fields())
                    .from(FinancialAccountTransactionTypeDescriptions)
                    .where(FinancialAccountTransactionTypeDescriptions.FINANCIAL_ACCOUNT_TRANSACTION_TYPE.eq(financialAccountTransactionType.getPrimaryKey()),
                            FinancialAccountTransactionTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return financialAccountTransactionTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<FinancialAccountTransactionTypeDescription> getFinancialAccountTransactionTypeDescriptionsByFinancialAccountTransactionType(FinancialAccountTransactionType financialAccountTransactionType) {
        return getFinancialAccountTransactionTypeDescriptionsByFinancialAccountTransactionType(financialAccountTransactionType, EntityPermission.READ_ONLY);
    }
    
    public List<FinancialAccountTransactionTypeDescription> getFinancialAccountTransactionTypeDescriptionsByFinancialAccountTransactionTypeForUpdate(FinancialAccountTransactionType financialAccountTransactionType) {
        return getFinancialAccountTransactionTypeDescriptionsByFinancialAccountTransactionType(financialAccountTransactionType, EntityPermission.READ_WRITE);
    }
    
    public String getBestFinancialAccountTransactionTypeDescription(FinancialAccountTransactionType financialAccountTransactionType, Language language) {
        String description;
        var financialAccountTransactionTypeDescription = getFinancialAccountTransactionTypeDescription(financialAccountTransactionType, language);
        
        if(financialAccountTransactionTypeDescription == null && !language.getIsDefault()) {
            financialAccountTransactionTypeDescription = getFinancialAccountTransactionTypeDescription(financialAccountTransactionType, partyControl.getDefaultLanguage());
        }
        
        if(financialAccountTransactionTypeDescription == null) {
            description = financialAccountTransactionType.getLastDetail().getFinancialAccountTransactionTypeName();
        } else {
            description = financialAccountTransactionTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public FinancialAccountTransactionTypeDescriptionTransfer getFinancialAccountTransactionTypeDescriptionTransfer(UserVisit userVisit,
            FinancialAccountTransactionTypeDescription financialAccountTransactionTypeDescription) {
        return financialAccountTransactionTypeDescriptionTransferCache.getFinancialAccountTransactionTypeDescriptionTransfer(userVisit, financialAccountTransactionTypeDescription);
    }
    
    public List<FinancialAccountTransactionTypeDescriptionTransfer> getFinancialAccountTransactionTypeDescriptionTransfersByFinancialAccountTransactionType(UserVisit userVisit,
            FinancialAccountTransactionType financialAccountTransactionType) {
        var financialAccountTransactionTypeDescriptions = getFinancialAccountTransactionTypeDescriptionsByFinancialAccountTransactionType(financialAccountTransactionType);
        List<FinancialAccountTransactionTypeDescriptionTransfer> financialAccountTransactionTypeDescriptionTransfers = new ArrayList<>(financialAccountTransactionTypeDescriptions.size());
        
        financialAccountTransactionTypeDescriptions.forEach((financialAccountTransactionTypeDescription) ->
                financialAccountTransactionTypeDescriptionTransfers.add(financialAccountTransactionTypeDescriptionTransferCache.getFinancialAccountTransactionTypeDescriptionTransfer(userVisit, financialAccountTransactionTypeDescription))
        );
        
        return financialAccountTransactionTypeDescriptionTransfers;
    }
    
    public void updateFinancialAccountTransactionTypeDescriptionFromValue(FinancialAccountTransactionTypeDescriptionValue financialAccountTransactionTypeDescriptionValue,
            BasePK updatedBy) {
        if(financialAccountTransactionTypeDescriptionValue.hasBeenModified()) {
            var financialAccountTransactionTypeDescription = financialAccountTransactionTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    financialAccountTransactionTypeDescriptionValue.getPrimaryKey());
            
            financialAccountTransactionTypeDescription.setThruTime(session.getStartTime());
            financialAccountTransactionTypeDescription.store();

            var financialAccountTransactionType = financialAccountTransactionTypeDescription.getFinancialAccountTransactionType();
            var language = financialAccountTransactionTypeDescription.getLanguage();
            var description = financialAccountTransactionTypeDescriptionValue.getDescription();
            
            financialAccountTransactionTypeDescription = financialAccountTransactionTypeDescriptionFactory.create(
                    financialAccountTransactionType, language, description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(financialAccountTransactionType.getPrimaryKey(), EventTypes.MODIFY, financialAccountTransactionTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteFinancialAccountTransactionTypeDescription(FinancialAccountTransactionTypeDescription financialAccountTransactionTypeDescription, BasePK deletedBy) {
        financialAccountTransactionTypeDescription.setThruTime(session.getStartTime());
        
        sendEvent(financialAccountTransactionTypeDescription.getFinancialAccountTransactionTypePK(), EventTypes.MODIFY, financialAccountTransactionTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteFinancialAccountTransactionTypeDescriptionsByFinancialAccountTransactionType(FinancialAccountTransactionType financialAccountTransactionType, BasePK deletedBy) {
        var financialAccountTransactionTypeDescriptions = getFinancialAccountTransactionTypeDescriptionsByFinancialAccountTransactionTypeForUpdate(financialAccountTransactionType);
        
        financialAccountTransactionTypeDescriptions.forEach((financialAccountTransactionTypeDescription) -> 
                deleteFinancialAccountTransactionTypeDescription(financialAccountTransactionTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Financial Account Alias Types
    // --------------------------------------------------------------------------------

    @Inject
    protected FinancialAccountAliasTypeDetailFactory financialAccountAliasTypeDetailFactory;

    @Inject
    protected FinancialAccountAliasTypeFactory financialAccountAliasTypeFactory;

    @Inject
    FinancialAccountAliasTypeTransferCache financialAccountAliasTypeTransferCache;

    public FinancialAccountAliasType createFinancialAccountAliasType(FinancialAccountType financialAccountType, String financialAccountAliasTypeName,
            String validationPattern, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultFinancialAccountAliasType = getDefaultFinancialAccountAliasType(financialAccountType);
        var defaultFound = defaultFinancialAccountAliasType != null;
        
        if(defaultFound && isDefault) {
            var defaultFinancialAccountAliasTypeDetailValue = getDefaultFinancialAccountAliasTypeDetailValueForUpdate(financialAccountType);
            
            defaultFinancialAccountAliasTypeDetailValue.setIsDefault(false);
            updateFinancialAccountAliasTypeFromValue(defaultFinancialAccountAliasTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var financialAccountAliasType = financialAccountAliasTypeFactory.create();
        var financialAccountAliasTypeDetail = financialAccountAliasTypeDetailFactory.create(
                financialAccountAliasType, financialAccountType, financialAccountAliasTypeName, validationPattern, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        financialAccountAliasType = financialAccountAliasTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE, financialAccountAliasType.getPrimaryKey());
        financialAccountAliasType.setActiveDetail(financialAccountAliasTypeDetail);
        financialAccountAliasType.setLastDetail(financialAccountAliasTypeDetail);
        financialAccountAliasType.store();
        
        sendEvent(financialAccountAliasType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return financialAccountAliasType;
    }
    
    private FinancialAccountAliasType getFinancialAccountAliasTypeByName(FinancialAccountType financialAccountType, String financialAccountAliasTypeName,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FinancialAccountAliasTypes.fields())
                .from(FinancialAccountAliasTypes)
                .join(FinancialAccountAliasTypeDetails).onKey(FINANCIAL_ACCOUNT_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .where(FinancialAccountAliasTypeDetails.FINANCIAL_ACCOUNT_TYPE.eq(financialAccountType.getPrimaryKey()),
                        FinancialAccountAliasTypeDetails.FINANCIAL_ACCOUNT_ALIAS_TYPE_NAME.eq(financialAccountAliasTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return financialAccountAliasTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public FinancialAccountAliasType getFinancialAccountAliasTypeByName(FinancialAccountType financialAccountType, String financialAccountAliasTypeName) {
        return getFinancialAccountAliasTypeByName(financialAccountType, financialAccountAliasTypeName, EntityPermission.READ_ONLY);
    }
    
    public FinancialAccountAliasType getFinancialAccountAliasTypeByNameForUpdate(FinancialAccountType financialAccountType, String financialAccountAliasTypeName) {
        return getFinancialAccountAliasTypeByName(financialAccountType, financialAccountAliasTypeName, EntityPermission.READ_WRITE);
    }
    
    public FinancialAccountAliasTypeDetailValue getFinancialAccountAliasTypeDetailValueForUpdate(FinancialAccountAliasType financialAccountAliasType) {
        return financialAccountAliasType == null? null: financialAccountAliasType.getLastDetailForUpdate().getFinancialAccountAliasTypeDetailValue().clone();
    }
    
    public FinancialAccountAliasTypeDetailValue getFinancialAccountAliasTypeDetailValueByNameForUpdate(FinancialAccountType financialAccountType,
            String financialAccountAliasTypeName) {
        return getFinancialAccountAliasTypeDetailValueForUpdate(getFinancialAccountAliasTypeByNameForUpdate(financialAccountType, financialAccountAliasTypeName));
    }
    
    private FinancialAccountAliasType getDefaultFinancialAccountAliasType(FinancialAccountType financialAccountType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FinancialAccountAliasTypes.fields())
                .from(FinancialAccountAliasTypes)
                .join(FinancialAccountAliasTypeDetails).onKey(FINANCIAL_ACCOUNT_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .where(FinancialAccountAliasTypeDetails.FINANCIAL_ACCOUNT_TYPE.eq(financialAccountType.getPrimaryKey()),
                        FinancialAccountAliasTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return financialAccountAliasTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public FinancialAccountAliasType getDefaultFinancialAccountAliasType(FinancialAccountType financialAccountType) {
        return getDefaultFinancialAccountAliasType(financialAccountType, EntityPermission.READ_ONLY);
    }
    
    public FinancialAccountAliasType getDefaultFinancialAccountAliasTypeForUpdate(FinancialAccountType financialAccountType) {
        return getDefaultFinancialAccountAliasType(financialAccountType, EntityPermission.READ_WRITE);
    }
    
    public FinancialAccountAliasTypeDetailValue getDefaultFinancialAccountAliasTypeDetailValueForUpdate(FinancialAccountType financialAccountType) {
        return getDefaultFinancialAccountAliasTypeForUpdate(financialAccountType).getLastDetailForUpdate().getFinancialAccountAliasTypeDetailValue().clone();
    }
    
    private List<FinancialAccountAliasType> getFinancialAccountAliasTypes(FinancialAccountType financialAccountType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FinancialAccountAliasTypes.fields())
                .from(FinancialAccountAliasTypes)
                .join(FinancialAccountAliasTypeDetails).onKey(FINANCIAL_ACCOUNT_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .where(FinancialAccountAliasTypeDetails.FINANCIAL_ACCOUNT_TYPE.eq(financialAccountType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(FinancialAccountAliasTypeDetails.SORT_ORDER, FinancialAccountAliasTypeDetails.FINANCIAL_ACCOUNT_ALIAS_TYPE_NAME),
                    FinancialAccountAliasTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return financialAccountAliasTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<FinancialAccountAliasType> getFinancialAccountAliasTypes(FinancialAccountType financialAccountType) {
        return getFinancialAccountAliasTypes(financialAccountType, EntityPermission.READ_ONLY);
    }
    
    public List<FinancialAccountAliasType> getFinancialAccountAliasTypesForUpdate(FinancialAccountType financialAccountType) {
        return getFinancialAccountAliasTypes(financialAccountType, EntityPermission.READ_WRITE);
    }
    
    public FinancialAccountAliasTypeTransfer getFinancialAccountAliasTypeTransfer(UserVisit userVisit, FinancialAccountAliasType financialAccountAliasType) {
        return financialAccountAliasTypeTransferCache.getFinancialAccountAliasTypeTransfer(userVisit, financialAccountAliasType);
    }
    
    public List<FinancialAccountAliasTypeTransfer> getFinancialAccountAliasTypeTransfers(UserVisit userVisit, FinancialAccountType financialAccountType) {
        var financialAccountAliasTypes = getFinancialAccountAliasTypes(financialAccountType);
        List<FinancialAccountAliasTypeTransfer> financialAccountAliasTypeTransfers = new ArrayList<>(financialAccountAliasTypes.size());
        
        financialAccountAliasTypes.forEach((financialAccountAliasType) ->
                financialAccountAliasTypeTransfers.add(financialAccountAliasTypeTransferCache.getFinancialAccountAliasTypeTransfer(userVisit, financialAccountAliasType))
        );
        
        return financialAccountAliasTypeTransfers;
    }
    
    public FinancialAccountAliasTypeChoicesBean getFinancialAccountAliasTypeChoices(String defaultFinancialAccountAliasTypeChoice, Language language,
            boolean allowNullChoice, FinancialAccountType financialAccountType) {
        var financialAccountAliasTypes = getFinancialAccountAliasTypes(financialAccountType);
        var size = financialAccountAliasTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultFinancialAccountAliasTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var financialAccountAliasType : financialAccountAliasTypes) {
            var financialAccountAliasTypeDetail = financialAccountAliasType.getLastDetail();
            
            var label = getBestFinancialAccountAliasTypeDescription(financialAccountAliasType, language);
            var value = financialAccountAliasTypeDetail.getFinancialAccountAliasTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultFinancialAccountAliasTypeChoice != null && defaultFinancialAccountAliasTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && financialAccountAliasTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new FinancialAccountAliasTypeChoicesBean(labels, values, defaultValue);
    }
    
    private void updateFinancialAccountAliasTypeFromValue(FinancialAccountAliasTypeDetailValue financialAccountAliasTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(financialAccountAliasTypeDetailValue.hasBeenModified()) {
            var financialAccountAliasType = financialAccountAliasTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    financialAccountAliasTypeDetailValue.getFinancialAccountAliasTypePK());
            var financialAccountAliasTypeDetail = financialAccountAliasType.getActiveDetailForUpdate();
            
            financialAccountAliasTypeDetail.setThruTime(session.getStartTime());
            financialAccountAliasTypeDetail.store();

            var financialAccountAliasTypePK = financialAccountAliasTypeDetail.getFinancialAccountAliasTypePK();
            var financialAccountType = financialAccountAliasTypeDetail.getFinancialAccountType();
            var financialAccountTypePK = financialAccountType.getPrimaryKey();
            var financialAccountAliasTypeName = financialAccountAliasTypeDetailValue.getFinancialAccountAliasTypeName();
            var validationPattern = financialAccountAliasTypeDetailValue.getValidationPattern();
            var isDefault = financialAccountAliasTypeDetailValue.getIsDefault();
            var sortOrder = financialAccountAliasTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultFinancialAccountAliasType = getDefaultFinancialAccountAliasType(financialAccountType);
                var defaultFound = defaultFinancialAccountAliasType != null && !defaultFinancialAccountAliasType.equals(financialAccountAliasType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultFinancialAccountAliasTypeDetailValue = getDefaultFinancialAccountAliasTypeDetailValueForUpdate(financialAccountType);
                    
                    defaultFinancialAccountAliasTypeDetailValue.setIsDefault(false);
                    updateFinancialAccountAliasTypeFromValue(defaultFinancialAccountAliasTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            financialAccountAliasTypeDetail = financialAccountAliasTypeDetailFactory.create(financialAccountAliasTypePK,
                    financialAccountTypePK, financialAccountAliasTypeName, validationPattern, isDefault, sortOrder, session.getStartTime(),
                    Session.MAX_TIME);
            
            financialAccountAliasType.setActiveDetail(financialAccountAliasTypeDetail);
            financialAccountAliasType.setLastDetail(financialAccountAliasTypeDetail);
            
            sendEvent(financialAccountAliasTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateFinancialAccountAliasTypeFromValue(FinancialAccountAliasTypeDetailValue financialAccountAliasTypeDetailValue, BasePK updatedBy) {
        updateFinancialAccountAliasTypeFromValue(financialAccountAliasTypeDetailValue, true, updatedBy);
    }
    
    public void deleteFinancialAccountAliasType(FinancialAccountAliasType financialAccountAliasType, BasePK deletedBy) {
        deleteFinancialAccountAliasesByFinancialAccountAliasType(financialAccountAliasType, deletedBy);
        deleteFinancialAccountAliasTypeDescriptionsByFinancialAccountAliasType(financialAccountAliasType, deletedBy);

        var financialAccountAliasTypeDetail = financialAccountAliasType.getLastDetailForUpdate();
        financialAccountAliasTypeDetail.setThruTime(session.getStartTime());
        financialAccountAliasType.setActiveDetail(null);
        financialAccountAliasType.store();
        
        // Check for default, and pick one if necessary
        var financialAccountType = financialAccountAliasTypeDetail.getFinancialAccountType();
        var defaultFinancialAccountAliasType = getDefaultFinancialAccountAliasType(financialAccountType);
        if(defaultFinancialAccountAliasType == null) {
            var financialAccountAliasTypes = getFinancialAccountAliasTypesForUpdate(financialAccountType);
            
            if(!financialAccountAliasTypes.isEmpty()) {
                var iter = financialAccountAliasTypes.iterator();
                if(iter.hasNext()) {
                    defaultFinancialAccountAliasType = iter.next();
                }
                var financialAccountAliasTypeDetailValue = Objects.requireNonNull(defaultFinancialAccountAliasType).getLastDetailForUpdate().getFinancialAccountAliasTypeDetailValue().clone();
                
                financialAccountAliasTypeDetailValue.setIsDefault(true);
                updateFinancialAccountAliasTypeFromValue(financialAccountAliasTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(financialAccountAliasType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteFinancialAccountAliasTypes(List<FinancialAccountAliasType> financialAccountAliasTypes, BasePK deletedBy) {
        financialAccountAliasTypes.forEach((financialAccountAliasType) -> 
                deleteFinancialAccountAliasType(financialAccountAliasType, deletedBy)
        );
    }
    
    public void deleteFinancialAccountAliasTypesByFinancialAccountType(FinancialAccountType financialAccountType, BasePK deletedBy) {
        deleteFinancialAccountAliasTypes(getFinancialAccountAliasTypesForUpdate(financialAccountType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Financial Account Alias Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected FinancialAccountAliasTypeDescriptionFactory financialAccountAliasTypeDescriptionFactory;

    @Inject
    FinancialAccountAliasTypeDescriptionTransferCache financialAccountAliasTypeDescriptionTransferCache;

    public FinancialAccountAliasTypeDescription createFinancialAccountAliasTypeDescription(FinancialAccountAliasType financialAccountAliasType,
            Language language, String description, BasePK createdBy) {
        var financialAccountAliasTypeDescription = financialAccountAliasTypeDescriptionFactory.create(
                financialAccountAliasType, language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(financialAccountAliasType.getPrimaryKey(), EventTypes.MODIFY, financialAccountAliasTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return financialAccountAliasTypeDescription;
    }
    
    private FinancialAccountAliasTypeDescription getFinancialAccountAliasTypeDescription(FinancialAccountAliasType financialAccountAliasType, Language language,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FinancialAccountAliasTypeDescriptions.fields())
                .from(FinancialAccountAliasTypeDescriptions)
                .where(FinancialAccountAliasTypeDescriptions.FINANCIAL_ACCOUNT_ALIAS_TYPE.eq(financialAccountAliasType.getPrimaryKey()),
                        FinancialAccountAliasTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        FinancialAccountAliasTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return financialAccountAliasTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public FinancialAccountAliasTypeDescription getFinancialAccountAliasTypeDescription(FinancialAccountAliasType financialAccountAliasType, Language language) {
        return getFinancialAccountAliasTypeDescription(financialAccountAliasType, language, EntityPermission.READ_ONLY);
    }
    
    public FinancialAccountAliasTypeDescription getFinancialAccountAliasTypeDescriptionForUpdate(FinancialAccountAliasType financialAccountAliasType,
            Language language) {
        return getFinancialAccountAliasTypeDescription(financialAccountAliasType, language, EntityPermission.READ_WRITE);
    }
    
    public FinancialAccountAliasTypeDescriptionValue getFinancialAccountAliasTypeDescriptionValue(FinancialAccountAliasTypeDescription financialAccountAliasTypeDescription) {
        return financialAccountAliasTypeDescription == null? null: financialAccountAliasTypeDescription.getFinancialAccountAliasTypeDescriptionValue().clone();
    }
    
    public FinancialAccountAliasTypeDescriptionValue getFinancialAccountAliasTypeDescriptionValueForUpdate(FinancialAccountAliasType financialAccountAliasType,
            Language language) {
        return getFinancialAccountAliasTypeDescriptionValue(getFinancialAccountAliasTypeDescriptionForUpdate(financialAccountAliasType, language));
    }
    
    private List<FinancialAccountAliasTypeDescription> getFinancialAccountAliasTypeDescriptionsByFinancialAccountAliasType(FinancialAccountAliasType financialAccountAliasType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(FinancialAccountAliasTypeDescriptions.fields())
                    .from(FinancialAccountAliasTypeDescriptions)
                    .join(Languages).onKey(FINANCIAL_ACCOUNT_ALIAS_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(FinancialAccountAliasTypeDescriptions.FINANCIAL_ACCOUNT_ALIAS_TYPE.eq(financialAccountAliasType.getPrimaryKey()),
                            FinancialAccountAliasTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    FinancialAccountAliasTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(FinancialAccountAliasTypeDescriptions.fields())
                    .from(FinancialAccountAliasTypeDescriptions)
                    .where(FinancialAccountAliasTypeDescriptions.FINANCIAL_ACCOUNT_ALIAS_TYPE.eq(financialAccountAliasType.getPrimaryKey()),
                            FinancialAccountAliasTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return financialAccountAliasTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<FinancialAccountAliasTypeDescription> getFinancialAccountAliasTypeDescriptionsByFinancialAccountAliasType(FinancialAccountAliasType financialAccountAliasType) {
        return getFinancialAccountAliasTypeDescriptionsByFinancialAccountAliasType(financialAccountAliasType, EntityPermission.READ_ONLY);
    }
    
    public List<FinancialAccountAliasTypeDescription> getFinancialAccountAliasTypeDescriptionsByFinancialAccountAliasTypeForUpdate(FinancialAccountAliasType financialAccountAliasType) {
        return getFinancialAccountAliasTypeDescriptionsByFinancialAccountAliasType(financialAccountAliasType, EntityPermission.READ_WRITE);
    }
    
    public String getBestFinancialAccountAliasTypeDescription(FinancialAccountAliasType financialAccountAliasType, Language language) {
        String description;
        var financialAccountAliasTypeDescription = getFinancialAccountAliasTypeDescription(financialAccountAliasType, language);
        
        if(financialAccountAliasTypeDescription == null && !language.getIsDefault()) {
            financialAccountAliasTypeDescription = getFinancialAccountAliasTypeDescription(financialAccountAliasType, partyControl.getDefaultLanguage());
        }
        
        if(financialAccountAliasTypeDescription == null) {
            description = financialAccountAliasType.getLastDetail().getFinancialAccountAliasTypeName();
        } else {
            description = financialAccountAliasTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public FinancialAccountAliasTypeDescriptionTransfer getFinancialAccountAliasTypeDescriptionTransfer(UserVisit userVisit,
            FinancialAccountAliasTypeDescription financialAccountAliasTypeDescription) {
        return financialAccountAliasTypeDescriptionTransferCache.getFinancialAccountAliasTypeDescriptionTransfer(userVisit, financialAccountAliasTypeDescription);
    }
    
    public List<FinancialAccountAliasTypeDescriptionTransfer> getFinancialAccountAliasTypeDescriptionTransfersByFinancialAccountAliasType(UserVisit userVisit,
            FinancialAccountAliasType financialAccountAliasType) {
        var financialAccountAliasTypeDescriptions = getFinancialAccountAliasTypeDescriptionsByFinancialAccountAliasType(financialAccountAliasType);
        List<FinancialAccountAliasTypeDescriptionTransfer> financialAccountAliasTypeDescriptionTransfers = new ArrayList<>(financialAccountAliasTypeDescriptions.size());
        
        financialAccountAliasTypeDescriptions.forEach((financialAccountAliasTypeDescription) ->
                financialAccountAliasTypeDescriptionTransfers.add(financialAccountAliasTypeDescriptionTransferCache.getFinancialAccountAliasTypeDescriptionTransfer(userVisit, financialAccountAliasTypeDescription))
        );
        
        return financialAccountAliasTypeDescriptionTransfers;
    }
    
    public void updateFinancialAccountAliasTypeDescriptionFromValue(FinancialAccountAliasTypeDescriptionValue financialAccountAliasTypeDescriptionValue,
            BasePK updatedBy) {
        if(financialAccountAliasTypeDescriptionValue.hasBeenModified()) {
            var financialAccountAliasTypeDescription = financialAccountAliasTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     financialAccountAliasTypeDescriptionValue.getPrimaryKey());
            
            financialAccountAliasTypeDescription.setThruTime(session.getStartTime());
            financialAccountAliasTypeDescription.store();

            var financialAccountAliasType = financialAccountAliasTypeDescription.getFinancialAccountAliasType();
            var language = financialAccountAliasTypeDescription.getLanguage();
            var description = financialAccountAliasTypeDescriptionValue.getDescription();
            
            financialAccountAliasTypeDescription = financialAccountAliasTypeDescriptionFactory.create(financialAccountAliasType,
                    language, description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(financialAccountAliasType.getPrimaryKey(), EventTypes.MODIFY, financialAccountAliasTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteFinancialAccountAliasTypeDescription(FinancialAccountAliasTypeDescription financialAccountAliasTypeDescription, BasePK deletedBy) {
        financialAccountAliasTypeDescription.setThruTime(session.getStartTime());
        
        sendEvent(financialAccountAliasTypeDescription.getFinancialAccountAliasTypePK(), EventTypes.MODIFY, financialAccountAliasTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteFinancialAccountAliasTypeDescriptionsByFinancialAccountAliasType(FinancialAccountAliasType financialAccountAliasType, BasePK deletedBy) {
        var financialAccountAliasTypeDescriptions = getFinancialAccountAliasTypeDescriptionsByFinancialAccountAliasTypeForUpdate(financialAccountAliasType);
        
        financialAccountAliasTypeDescriptions.forEach((financialAccountAliasTypeDescription) -> 
                deleteFinancialAccountAliasTypeDescription(financialAccountAliasTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Financial Account Roles
    // --------------------------------------------------------------------------------

    @Inject
    protected FinancialAccountRoleFactory financialAccountRoleFactory;

    @Inject
    FinancialAccountRoleTransferCache financialAccountRoleTransferCache;

    public FinancialAccountRole createFinancialAccountRoleUsingNames(FinancialAccount financialAccount, Party party,
            PartyContactMechanism partyContactMechanism, String financialAccountRoleTypeName, BasePK createdBy) {
        var financialAccountRoleType = getFinancialAccountRoleTypeByName(financialAccountRoleTypeName);
        
        return createFinancialAccountRole(financialAccount, party, partyContactMechanism, financialAccountRoleType, createdBy);
    }
    
    public FinancialAccountRole createFinancialAccountRole(FinancialAccount financialAccount, Party party, PartyContactMechanism partyContactMechanism,
            FinancialAccountRoleType financialAccountRoleType, BasePK createdBy) {
        var financialAccountRole = financialAccountRoleFactory.create(financialAccount, party, financialAccountRoleType,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(financialAccount.getPrimaryKey(), EventTypes.MODIFY, financialAccountRole.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return financialAccountRole;
    }
    
    private FinancialAccountRole getFinancialAccountRole(FinancialAccount financialAccount, FinancialAccountRoleType financialAccountRoleType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FinancialAccountRoles.fields())
                .from(FinancialAccountRoles)
                .where(FinancialAccountRoles.FINANCIAL_ACCOUNT.eq(financialAccount.getPrimaryKey()),
                        FinancialAccountRoles.FINANCIAL_ACCOUNT_ROLE_TYPE.eq(financialAccountRoleType.getPrimaryKey()),
                        FinancialAccountRoles.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return financialAccountRoleFactory.getEntityFromQuery(entityPermission, query);
    }
    
     public FinancialAccountRole getFinancialAccountRole(FinancialAccount financialAccount, FinancialAccountRoleType financialAccountRoleType) {
        return getFinancialAccountRole(financialAccount, financialAccountRoleType, EntityPermission.READ_ONLY);
    }
    
    public FinancialAccountRole getFinancialAccountRoleUsingNames(FinancialAccount financialAccount, String financialAccountRoleTypeName) {
        var financialAccountRoleType = getFinancialAccountRoleTypeByName(financialAccountRoleTypeName);
        
        return getFinancialAccountRole(financialAccount, financialAccountRoleType);
    }
    
    public FinancialAccountRole getFinancialAccountRoleForUpdate(FinancialAccount financialAccount, Party party, FinancialAccountRoleType financialAccountRoleType) {
        return getFinancialAccountRole(financialAccount, financialAccountRoleType, EntityPermission.READ_WRITE);
    }
    
    public FinancialAccountRoleValue getFinancialAccountRoleValue(FinancialAccountRole financialAccountRole) {
        return financialAccountRole == null? null: financialAccountRole.getFinancialAccountRoleValue().clone();
    }
    
    public FinancialAccountRoleValue getFinancialAccountRoleValueForUpdate(FinancialAccount financialAccount, Party party,
            FinancialAccountRoleType financialAccountRoleType) {
        return getFinancialAccountRoleValue(getFinancialAccountRoleForUpdate(financialAccount, party, financialAccountRoleType));
    }
    
    private List<FinancialAccountRole> getFinancialAccountRolesByFinancialAccount(FinancialAccount financialAccount, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(FinancialAccountRoles.fields())
                    .from(FinancialAccountRoles)
                    .join(FinancialAccountRoleTypes).onKey(FINANCIAL_ACCOUNT_ROLES_FINANCIAL_ACCOUNT_ROLE_TYPE_FK)
                    .join(Parties).onKey(FINANCIAL_ACCOUNT_ROLES_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_ACTIVE_DETAIL_FK)
                    .where(FinancialAccountRoles.FINANCIAL_ACCOUNT.eq(financialAccount.getPrimaryKey()),
                            FinancialAccountRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(FinancialAccountRoleTypes.SORT_ORDER, FinancialAccountRoleTypes.FINANCIAL_ACCOUNT_ROLE_TYPE_NAME, PartyDetails.PARTY_NAME),
                    FinancialAccountRoleFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(FinancialAccountRoles.fields())
                    .from(FinancialAccountRoles)
                    .where(FinancialAccountRoles.FINANCIAL_ACCOUNT.eq(financialAccount.getPrimaryKey()),
                            FinancialAccountRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return financialAccountRoleFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<FinancialAccountRole> getFinancialAccountRolesByFinancialAccount(FinancialAccount financialAccount) {
        return getFinancialAccountRolesByFinancialAccount(financialAccount, EntityPermission.READ_ONLY);
    }
    
    public List<FinancialAccountRole> getFinancialAccountRolesByFinancialAccountForUpdate(FinancialAccount financialAccount) {
        return getFinancialAccountRolesByFinancialAccount(financialAccount, EntityPermission.READ_WRITE);
    }
    
    public List<FinancialAccountRoleTransfer> getFinancialAccountRoleTransfers(UserVisit userVisit, Collection<FinancialAccountRole> financialAccountRoles) {
        List<FinancialAccountRoleTransfer> financialAccountRoleTransfers = new ArrayList<>(financialAccountRoles.size());
        
        financialAccountRoles.forEach((financialAccountRole) ->
                financialAccountRoleTransfers.add(financialAccountRoleTransferCache.getFinancialAccountRoleTransfer(userVisit, financialAccountRole))
        );
        
        return financialAccountRoleTransfers;
    }
    
    public List<FinancialAccountRoleTransfer> getFinancialAccountRoleTransfersByFinancialAccount(UserVisit userVisit, FinancialAccount financialAccount) {
        return getFinancialAccountRoleTransfers(userVisit, getFinancialAccountRolesByFinancialAccount(financialAccount));
    }
    
    public void updateFinancialAccountRoleFromValue(FinancialAccountRoleValue financialAccountRoleValue, BasePK updatedBy) {
        if(financialAccountRoleValue.hasBeenModified()) {
            var financialAccountRole = financialAccountRoleFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     financialAccountRoleValue.getPrimaryKey());
            
            financialAccountRole.setThruTime(session.getStartTime());
            financialAccountRole.store();

            var financialAccountPK = financialAccountRole.getFinancialAccountPK(); // Not updated
            var partyPK = financialAccountRole.getPartyPK(); // Not updated
            var financialAccountRoleTypePK = financialAccountRole.getFinancialAccountRoleTypePK(); // Not updated
            
            financialAccountRole = financialAccountRoleFactory.create(financialAccountPK, partyPK, financialAccountRoleTypePK,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(financialAccountPK, EventTypes.MODIFY, financialAccountRole.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteFinancialAccountRole(FinancialAccountRole financialAccountRole, BasePK deletedBy) {
        financialAccountRole.setThruTime(session.getStartTime());
        
        sendEvent(financialAccountRole.getFinancialAccountPK(), EventTypes.MODIFY, financialAccountRole.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteFinancialAccountRolesByFinancialAccount(FinancialAccount financialAccount, BasePK deletedBy) {
        getFinancialAccountRolesByFinancialAccountForUpdate(financialAccount).forEach((financialAccountRole) -> {
            deleteFinancialAccountRole(financialAccountRole, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Financial Accounts
    // --------------------------------------------------------------------------------

    @Inject
    protected FinancialAccountDetailFactory financialAccountDetailFactory;

    @Inject
    protected FinancialAccountFactory financialAccountFactory;

    @Inject
    FinancialAccountTransferCache financialAccountTransferCache;

    public FinancialAccount createFinancialAccount(FinancialAccountType financialAccountType, String financialAccountName, Currency currency,
            GlAccount glAccount, String reference, String description, BasePK createdBy) {
        var financialAccount = financialAccountFactory.create();
        var financialAccountDetail = financialAccountDetailFactory.create(financialAccount, financialAccountType,
                financialAccountName, currency, glAccount, reference, description, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        financialAccount = financialAccountFactory.getEntityFromPK(EntityPermission.READ_WRITE, financialAccount.getPrimaryKey());
        financialAccount.setActiveDetail(financialAccountDetail);
        financialAccount.setLastDetail(financialAccountDetail);
        financialAccount.store();
        
        sendEvent(financialAccount.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        createFinancialAccountStatus(financialAccount);
        
        return financialAccount;
    }
    
    private FinancialAccount getFinancialAccountByName(FinancialAccountType financialAccountType, String financialAccountName,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FinancialAccounts.fields())
                .from(FinancialAccounts)
                .join(FinancialAccountDetails).onKey(FINANCIAL_ACCOUNTS_ACTIVE_DETAIL_FK)
                .where(FinancialAccountDetails.FINANCIAL_ACCOUNT_TYPE.eq(financialAccountType.getPrimaryKey()),
                        FinancialAccountDetails.FINANCIAL_ACCOUNT_NAME.eq(financialAccountName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return financialAccountFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public FinancialAccount getFinancialAccountByName(FinancialAccountType financialAccountType, String financialAccountName) {
        return getFinancialAccountByName(financialAccountType, financialAccountName, EntityPermission.READ_ONLY);
    }
    
    public FinancialAccount getFinancialAccountByNameForUpdate(FinancialAccountType financialAccountType, String financialAccountName) {
        return getFinancialAccountByName(financialAccountType, financialAccountName, EntityPermission.READ_WRITE);
    }
    
    public FinancialAccountDetailValue getFinancialAccountDetailValueForUpdate(FinancialAccount financialAccount) {
        return financialAccount == null? null: financialAccount.getLastDetailForUpdate().getFinancialAccountDetailValue().clone();
    }
    
    public FinancialAccountDetailValue getFinancialAccountDetailValueByNameForUpdate(FinancialAccountType financialAccountType,
            String financialAccountName) {
        return getFinancialAccountDetailValueForUpdate(getFinancialAccountByNameForUpdate(financialAccountType, financialAccountName));
    }
    
    private List<FinancialAccount> getFinancialAccounts(FinancialAccountType financialAccountType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FinancialAccounts.fields())
                .from(FinancialAccounts)
                .join(FinancialAccountDetails).onKey(FINANCIAL_ACCOUNTS_ACTIVE_DETAIL_FK)
                .where(FinancialAccountDetails.FINANCIAL_ACCOUNT_TYPE.eq(financialAccountType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(FinancialAccountDetails.FINANCIAL_ACCOUNT_NAME),
                    FinancialAccountFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return financialAccountFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<FinancialAccount> getFinancialAccounts(FinancialAccountType financialAccountType) {
        return getFinancialAccounts(financialAccountType, EntityPermission.READ_ONLY);
    }
    
    public List<FinancialAccount> getFinancialAccountsForUpdate(FinancialAccountType financialAccountType) {
        return getFinancialAccounts(financialAccountType, EntityPermission.READ_WRITE);
    }
    
    private List<FinancialAccount> getFinancialAccountsByGlAccount(GlAccount glAccount, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(FinancialAccounts.fields())
                    .from(FinancialAccounts)
                    .join(FinancialAccountDetails).onKey(FINANCIAL_ACCOUNTS_ACTIVE_DETAIL_FK)
                    .join(FinancialAccountTypes).onKey(FINANCIAL_ACCOUNT_DETAILS_FINANCIAL_ACCOUNT_TYPE_FK)
                    .join(FinancialAccountTypeDetails).onKey(FINANCIAL_ACCOUNT_TYPES_LAST_DETAIL_FK)
                    .where(FinancialAccountDetails.GL_ACCOUNT.eq(glAccount.getPrimaryKey()))
                    .orderBy(FinancialAccountTypeDetails.SORT_ORDER, FinancialAccountTypeDetails.FINANCIAL_ACCOUNT_TYPE_NAME, FinancialAccountDetails.FINANCIAL_ACCOUNT_NAME),
                    FinancialAccountFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(FinancialAccounts.fields())
                    .from(FinancialAccounts)
                    .join(FinancialAccountDetails).onKey(FINANCIAL_ACCOUNTS_ACTIVE_DETAIL_FK)
                    .where(FinancialAccountDetails.GL_ACCOUNT.eq(glAccount.getPrimaryKey()))
                    .forUpdate();
        };

        return financialAccountFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<FinancialAccount> getFinancialAccountsByGlAccount(GlAccount glAccount) {
        return getFinancialAccountsByGlAccount(glAccount, EntityPermission.READ_ONLY);
    }
    
    public List<FinancialAccount> getFinancialAccountsByGlAccountForUpdate(GlAccount glAccount) {
        return getFinancialAccountsByGlAccount(glAccount, EntityPermission.READ_WRITE);
    }
    
    public FinancialAccountTransfer getFinancialAccountTransfer(UserVisit userVisit, FinancialAccount financialAccount) {
        return financialAccountTransferCache.getFinancialAccountTransfer(userVisit, financialAccount);
    }
    
    public List<FinancialAccountTransfer> getFinancialAccountTransfers(UserVisit userVisit, FinancialAccountType financialAccountType) {
        var financialAccounts = getFinancialAccounts(financialAccountType);
        List<FinancialAccountTransfer> financialAccountTransfers = new ArrayList<>(financialAccounts.size());
        
        financialAccounts.forEach((financialAccount) ->
                financialAccountTransfers.add(financialAccountTransferCache.getFinancialAccountTransfer(userVisit, financialAccount))
        );
        
        return financialAccountTransfers;
    }
    
    public void updateFinancialAccountFromValue(FinancialAccountDetailValue financialAccountDetailValue, BasePK updatedBy) {
        if(financialAccountDetailValue.hasBeenModified()) {
            var financialAccount = financialAccountFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    financialAccountDetailValue.getFinancialAccountPK());
            var financialAccountDetail = financialAccount.getActiveDetailForUpdate();
            
            financialAccountDetail.setThruTime(session.getStartTime());
            financialAccountDetail.store();

            var financialAccountPK = financialAccountDetail.getFinancialAccountPK();
            var financialAccountType = financialAccountDetail.getFinancialAccountType();
            var financialAccountTypePK = financialAccountType.getPrimaryKey();
            var financialAccountName = financialAccountDetailValue.getFinancialAccountName();
            var currencyPK = financialAccountDetailValue.getCurrencyPK();
            var glAccountPK = financialAccountDetailValue.getGlAccountPK();
            var reference = financialAccountDetailValue.getReference();
            var description = financialAccountDetailValue.getDescription();
            
            financialAccountDetail = financialAccountDetailFactory.create(financialAccountPK, financialAccountTypePK,
                    financialAccountName, currencyPK, glAccountPK, reference, description, session.getStartTime(), Session.MAX_TIME);
            
            financialAccount.setActiveDetail(financialAccountDetail);
            financialAccount.setLastDetail(financialAccountDetail);
            
            sendEvent(financialAccountPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteFinancialAccount(FinancialAccount financialAccount, BasePK deletedBy) {
        deleteFinancialAccountRolesByFinancialAccount(financialAccount, deletedBy);
        deleteFinancialAccountAliasesByFinancialAccount(financialAccount, deletedBy);
        deleteFinancialAccountTransactionsByFinancialAccount(financialAccount, deletedBy);
        removeFinancialAccountStatusByFinancialAccount(financialAccount);

        var financialAccountDetail = financialAccount.getLastDetailForUpdate();
        financialAccountDetail.setThruTime(session.getStartTime());
        financialAccount.setActiveDetail(null);
        financialAccount.store();
        
        sendEvent(financialAccount.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteFinancialAccounts(List<FinancialAccount> financialAccounts, BasePK deletedBy) {
        financialAccounts.forEach((financialAccount) -> 
                deleteFinancialAccount(financialAccount, deletedBy)
        );
    }
    
    public void deleteFinancialAccountsByFinancialAccountType(FinancialAccountType financialAccountType, BasePK deletedBy) {
        deleteFinancialAccounts(getFinancialAccountsForUpdate(financialAccountType), deletedBy);
    }
    
    public void deleteFinancialAccountsByGlAccount(GlAccount glAccount, BasePK deletedBy) {
        deleteFinancialAccounts(getFinancialAccountsByGlAccountForUpdate(glAccount), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Financial Account Statuses
    // --------------------------------------------------------------------------------

    @Inject
    protected FinancialAccountStatusFactory financialAccountStatusFactory;

    public FinancialAccountStatus createFinancialAccountStatus(FinancialAccount financialAccount) {
        return financialAccountStatusFactory.create(financialAccount, Long.valueOf(0), Long.valueOf(0));
    }
    
    private FinancialAccountStatus getFinancialAccountStatus(FinancialAccount financialAccount, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FinancialAccountStatuses.fields())
                .from(FinancialAccountStatuses)
                .where(FinancialAccountStatuses.FINANCIAL_ACCOUNT.eq(financialAccount.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return financialAccountStatusFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public FinancialAccountStatus getFinancialAccountStatus(FinancialAccount financialAccount) {
        return getFinancialAccountStatus(financialAccount, EntityPermission.READ_ONLY);
    }
    
    public FinancialAccountStatus getFinancialAccountStatusForUpdate(FinancialAccount financialAccount) {
        return getFinancialAccountStatus(financialAccount, EntityPermission.READ_WRITE);
    }
    
    public void removeFinancialAccountStatusByFinancialAccount(FinancialAccount financialAccount) {
        var financialAccountStatus = getFinancialAccountStatusForUpdate(financialAccount);
        
        if(financialAccountStatus != null) {
            financialAccountStatus.remove();
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Financial Account Aliases
    // --------------------------------------------------------------------------------

    @Inject
    protected FinancialAccountAliasFactory financialAccountAliasFactory;

    @Inject
    FinancialAccountAliasTransferCache financialAccountAliasTransferCache;

    public FinancialAccountAlias createFinancialAccountAlias(FinancialAccount financialAccount, FinancialAccountAliasType financialAccountAliasType,
            String alias, BasePK createdBy) {
        var financialAccountAlias = financialAccountAliasFactory.create(financialAccount, financialAccountAliasType,
                alias, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(financialAccount.getPrimaryKey(), EventTypes.MODIFY, financialAccountAlias.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return financialAccountAlias;
    }
    
    private FinancialAccountAlias getFinancialAccountAlias(FinancialAccount financialAccount, FinancialAccountAliasType financialAccountAliasType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FinancialAccountAliases.fields())
                .from(FinancialAccountAliases)
                .where(FinancialAccountAliases.FINANCIAL_ACCOUNT.eq(financialAccount.getPrimaryKey()),
                        FinancialAccountAliases.FINANCIAL_ACCOUNT_ALIAS_TYPE.eq(financialAccountAliasType.getPrimaryKey()),
                        FinancialAccountAliases.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return financialAccountAliasFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public FinancialAccountAlias getFinancialAccountAlias(FinancialAccount financialAccount, FinancialAccountAliasType financialAccountAliasType) {
        return getFinancialAccountAlias(financialAccount, financialAccountAliasType, EntityPermission.READ_ONLY);
    }
    
    public FinancialAccountAlias getFinancialAccountAliasForUpdate(FinancialAccount financialAccount, FinancialAccountAliasType financialAccountAliasType) {
        return getFinancialAccountAlias(financialAccount, financialAccountAliasType, EntityPermission.READ_WRITE);
    }
    
    public FinancialAccountAliasValue getFinancialAccountAliasValue(FinancialAccountAlias financialAccountAlias) {
        return financialAccountAlias == null? null: financialAccountAlias.getFinancialAccountAliasValue().clone();
    }
    
    public FinancialAccountAliasValue getFinancialAccountAliasValueForUpdate(FinancialAccount financialAccount,
            FinancialAccountAliasType financialAccountAliasType) {
        return getFinancialAccountAliasValue(getFinancialAccountAliasForUpdate(financialAccount, financialAccountAliasType));
    }
    
    private List<FinancialAccountAlias> getFinancialAccountAliasesByFinancialAccount(FinancialAccount financialAccount, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(FinancialAccountAliases.fields())
                    .from(FinancialAccountAliases)
                    .join(FinancialAccountAliasTypes).onKey(FINANCIAL_ACCOUNT_ALIASES_FINANCIAL_ACCOUNT_ALIAS_TYPE_FK)
                    .join(FinancialAccountAliasTypeDetails).onKey(FINANCIAL_ACCOUNT_ALIAS_TYPES_LAST_DETAIL_FK)
                    .where(FinancialAccountAliases.FINANCIAL_ACCOUNT.eq(financialAccount.getPrimaryKey()),
                            FinancialAccountAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(FinancialAccountAliasTypeDetails.SORT_ORDER, FinancialAccountAliasTypeDetails.FINANCIAL_ACCOUNT_ALIAS_TYPE_NAME),
                    FinancialAccountAliasFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(FinancialAccountAliases.fields())
                    .from(FinancialAccountAliases)
                    .where(FinancialAccountAliases.FINANCIAL_ACCOUNT.eq(financialAccount.getPrimaryKey()),
                            FinancialAccountAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return financialAccountAliasFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<FinancialAccountAlias> getFinancialAccountAliasesByFinancialAccount(FinancialAccount financialAccount) {
        return getFinancialAccountAliasesByFinancialAccount(financialAccount, EntityPermission.READ_ONLY);
    }
    
    public List<FinancialAccountAlias> getFinancialAccountAliasesByFinancialAccountForUpdate(FinancialAccount financialAccount) {
        return getFinancialAccountAliasesByFinancialAccount(financialAccount, EntityPermission.READ_WRITE);
    }
    
    private List<FinancialAccountAlias> getFinancialAccountAliasesByFinancialAccountAliasType(FinancialAccountAliasType financialAccountAliasType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(FinancialAccountAliases.fields())
                    .from(FinancialAccountAliases)
                    .join(FinancialAccounts).onKey(FINANCIAL_ACCOUNT_ALIASES_FINANCIAL_ACCOUNT_FK)
                    .join(FinancialAccountDetails).onKey(FINANCIAL_ACCOUNTS_LAST_DETAIL_FK)
                    .where(FinancialAccountAliases.FINANCIAL_ACCOUNT_ALIAS_TYPE.eq(financialAccountAliasType.getPrimaryKey()),
                            FinancialAccountAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(FinancialAccountDetails.FINANCIAL_ACCOUNT_NAME),
                    FinancialAccountAliasFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(FinancialAccountAliases.fields())
                    .from(FinancialAccountAliases)
                    .where(FinancialAccountAliases.FINANCIAL_ACCOUNT_ALIAS_TYPE.eq(financialAccountAliasType.getPrimaryKey()),
                            FinancialAccountAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return financialAccountAliasFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<FinancialAccountAlias> getFinancialAccountAliasesByFinancialAccountAliasType(FinancialAccountAliasType financialAccountAliasType) {
        return getFinancialAccountAliasesByFinancialAccountAliasType(financialAccountAliasType, EntityPermission.READ_ONLY);
    }
    
    public List<FinancialAccountAlias> getFinancialAccountAliasesByFinancialAccountAliasTypeForUpdate(FinancialAccountAliasType financialAccountAliasType) {
        return getFinancialAccountAliasesByFinancialAccountAliasType(financialAccountAliasType, EntityPermission.READ_WRITE);
    }
    
    public FinancialAccountAliasTransfer getFinancialAccountAliasTransfer(UserVisit userVisit, FinancialAccountAlias financialAccountAlias) {
        return financialAccountAliasTransferCache.getFinancialAccountAliasTransfer(userVisit, financialAccountAlias);
    }
    
    public List<FinancialAccountAliasTransfer> getFinancialAccountAliasTransfersByFinancialAccount(UserVisit userVisit, FinancialAccount financialAccount) {
        var financialaccountaliases = getFinancialAccountAliasesByFinancialAccount(financialAccount);
        List<FinancialAccountAliasTransfer> financialAccountAliasTransfers = new ArrayList<>(financialaccountaliases.size());
        
        financialaccountaliases.forEach((financialAccountAlias) ->
                financialAccountAliasTransfers.add(financialAccountAliasTransferCache.getFinancialAccountAliasTransfer(userVisit, financialAccountAlias))
        );
        
        return financialAccountAliasTransfers;
    }
    
    public void updateFinancialAccountAliasFromValue(FinancialAccountAliasValue financialAccountAliasValue, BasePK updatedBy) {
        if(financialAccountAliasValue.hasBeenModified()) {
            var financialAccountAlias = financialAccountAliasFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    financialAccountAliasValue.getPrimaryKey());
            
            financialAccountAlias.setThruTime(session.getStartTime());
            financialAccountAlias.store();

            var financialAccountPK = financialAccountAlias.getFinancialAccountPK();
            var financialAccountAliasTypePK = financialAccountAlias.getFinancialAccountAliasTypePK();
            var alias  = financialAccountAliasValue.getAlias();
            
            financialAccountAlias = financialAccountAliasFactory.create(financialAccountPK, financialAccountAliasTypePK, alias,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(financialAccountPK, EventTypes.MODIFY, financialAccountAlias.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteFinancialAccountAlias(FinancialAccountAlias financialAccountAlias, BasePK deletedBy) {
        financialAccountAlias.setThruTime(session.getStartTime());
        
        sendEvent(financialAccountAlias.getFinancialAccountPK(), EventTypes.MODIFY, financialAccountAlias.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteFinancialAccountAliasesByFinancialAccountAliasType(FinancialAccountAliasType financialAccountAliasType, BasePK deletedBy) {
        var financialaccountaliases = getFinancialAccountAliasesByFinancialAccountAliasTypeForUpdate(financialAccountAliasType);
        
        financialaccountaliases.forEach((financialAccountAlias) -> 
                deleteFinancialAccountAlias(financialAccountAlias, deletedBy)
        );
    }
    
    public void deleteFinancialAccountAliasesByFinancialAccount(FinancialAccount financialAccount, BasePK deletedBy) {
        var financialaccountaliases = getFinancialAccountAliasesByFinancialAccountForUpdate(financialAccount);
        
        financialaccountaliases.forEach((financialAccountAlias) -> 
                deleteFinancialAccountAlias(financialAccountAlias, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Financial AccountTransactions
    // --------------------------------------------------------------------------------

    @Inject
    protected FinancialAccountTransactionDetailFactory financialAccountTransactionDetailFactory;

    @Inject
    protected FinancialAccountTransactionFactory financialAccountTransactionFactory;

    @Inject
    FinancialAccountTransactionTransferCache financialAccountTransactionTransferCache;

    public FinancialAccountTransaction createFinancialAccountTransaction(String financialAccountTransactionName, FinancialAccount financialAccount,
            FinancialAccountTransactionType financialAccountTransactionType, Long amount, String comment, BasePK createdBy) {
        var financialAccountTransaction = financialAccountTransactionFactory.create();
        var financialAccountTransactionDetail = financialAccountTransactionDetailFactory.create(
                financialAccountTransaction, financialAccountTransactionName, financialAccount, financialAccountTransactionType, amount, comment,
                session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        financialAccountTransaction = financialAccountTransactionFactory.getEntityFromPK(EntityPermission.READ_WRITE, financialAccountTransaction.getPrimaryKey());
        financialAccountTransaction.setActiveDetail(financialAccountTransactionDetail);
        financialAccountTransaction.setLastDetail(financialAccountTransactionDetail);
        financialAccountTransaction.store();
        
        sendEvent(financialAccountTransaction.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return financialAccountTransaction;
    }
    
    private FinancialAccountTransaction getFinancialAccountTransactionByName(String financialAccountTransactionName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FinancialAccountTransactions.fields())
                .from(FinancialAccountTransactions)
                .join(FinancialAccountTransactionDetails).onKey(FINANCIAL_ACCOUNT_TRANSACTIONS_ACTIVE_DETAIL_FK)
                .where(FinancialAccountTransactionDetails.FINANCIAL_ACCOUNT_TRANSACTION_NAME.eq(financialAccountTransactionName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return financialAccountTransactionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public FinancialAccountTransaction getFinancialAccountTransactionByName(String financialAccountTransactionName) {
        return getFinancialAccountTransactionByName(financialAccountTransactionName, EntityPermission.READ_ONLY);
    }
    
    public FinancialAccountTransaction getFinancialAccountTransactionByNameForUpdate(String financialAccountTransactionName) {
        return getFinancialAccountTransactionByName(financialAccountTransactionName, EntityPermission.READ_WRITE);
    }
    
    public FinancialAccountTransactionDetailValue getFinancialAccountTransactionDetailValueForUpdate(FinancialAccountTransaction financialAccountTransaction) {
        return financialAccountTransaction == null? null: financialAccountTransaction.getLastDetailForUpdate().getFinancialAccountTransactionDetailValue().clone();
    }
    
    public FinancialAccountTransactionDetailValue getFinancialAccountTransactionDetailValueByNameForUpdate(String financialAccountTransactionName) {
        return getFinancialAccountTransactionDetailValueForUpdate(getFinancialAccountTransactionByNameForUpdate(financialAccountTransactionName));
    }
    
    private List<FinancialAccountTransaction> getFinancialAccountTransactionsByFinancialAccount(FinancialAccount financialAccount, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FinancialAccountTransactions.fields())
                .from(FinancialAccountTransactions)
                .join(FinancialAccountTransactionDetails).onKey(FINANCIAL_ACCOUNT_TRANSACTIONS_ACTIVE_DETAIL_FK)
                .where(FinancialAccountTransactionDetails.FINANCIAL_ACCOUNT.eq(financialAccount.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(FinancialAccountTransactionDetails.FINANCIAL_ACCOUNT_TRANSACTION_NAME),
                    FinancialAccountTransactionFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return financialAccountTransactionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<FinancialAccountTransaction> getFinancialAccountTransactionsByFinancialAccount(FinancialAccount financialAccount) {
        return getFinancialAccountTransactionsByFinancialAccount(financialAccount, EntityPermission.READ_ONLY);
    }
    
    public List<FinancialAccountTransaction> getFinancialAccountTransactionsByFinancialAccountForUpdate(FinancialAccount financialAccount) {
        return getFinancialAccountTransactionsByFinancialAccount(financialAccount, EntityPermission.READ_WRITE);
    }
    
    private List<FinancialAccountTransaction> getFinancialAccountTransactionsByFinancialAccountTransactionType(FinancialAccountTransactionType financialAccountTransactionType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FinancialAccountTransactions.fields())
                .from(FinancialAccountTransactions)
                .join(FinancialAccountTransactionDetails).onKey(FINANCIAL_ACCOUNT_TRANSACTIONS_ACTIVE_DETAIL_FK)
                .where(FinancialAccountTransactionDetails.FINANCIAL_ACCOUNT_TRANSACTION_TYPE.eq(financialAccountTransactionType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(FinancialAccountTransactionDetails.FINANCIAL_ACCOUNT_TRANSACTION_NAME),
                    FinancialAccountTransactionFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return financialAccountTransactionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<FinancialAccountTransaction> getFinancialAccountTransactionsByFinancialAccountTransactionType(FinancialAccountTransactionType financialAccountTransactionType) {
        return getFinancialAccountTransactionsByFinancialAccountTransactionType(financialAccountTransactionType, EntityPermission.READ_ONLY);
    }
    
    public List<FinancialAccountTransaction> getFinancialAccountTransactionsByFinancialAccountTransactionTypeForUpdate(FinancialAccountTransactionType financialAccountTransactionType) {
        return getFinancialAccountTransactionsByFinancialAccountTransactionType(financialAccountTransactionType, EntityPermission.READ_WRITE);
    }
    
    public FinancialAccountTransactionTransfer getFinancialAccountTransactionTransfer(UserVisit userVisit, FinancialAccountTransaction financialAccountTransaction) {
        return financialAccountTransactionTransferCache.getFinancialAccountTransactionTransfer(userVisit, financialAccountTransaction);
    }
    
    public List<FinancialAccountTransactionTransfer> getFinancialAccountTransactionTransfersByFinancialAccount(UserVisit userVisit, FinancialAccount financialAccount) {
        var financialAccountTransactions = getFinancialAccountTransactionsByFinancialAccount(financialAccount);
        List<FinancialAccountTransactionTransfer> financialAccountTransactionTransfers = new ArrayList<>(financialAccountTransactions.size());
        
        financialAccountTransactions.forEach((financialAccountTransaction) ->
                financialAccountTransactionTransfers.add(financialAccountTransactionTransferCache.getFinancialAccountTransactionTransfer(userVisit, financialAccountTransaction))
        );
        
        return financialAccountTransactionTransfers;
    }
    
    public void updateFinancialAccountTransactionFromValue(FinancialAccountTransactionDetailValue financialAccountTransactionDetailValue, BasePK updatedBy) {
        if(financialAccountTransactionDetailValue.hasBeenModified()) {
            var financialAccountTransaction = financialAccountTransactionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    financialAccountTransactionDetailValue.getFinancialAccountTransactionPK());
            var financialAccountTransactionDetail = financialAccountTransaction.getActiveDetailForUpdate();
            
            financialAccountTransactionDetail.setThruTime(session.getStartTime());
            financialAccountTransactionDetail.store();

            var financialAccountTransactionPK = financialAccountTransactionDetail.getFinancialAccountTransactionPK();
            var financialAccountTransactionName = financialAccountTransactionDetailValue.getFinancialAccountTransactionName();
            var financialAccountPK = financialAccountTransactionDetailValue.getFinancialAccountPK();
            var financialAccountTransactionTypePK = financialAccountTransactionDetailValue.getFinancialAccountTransactionTypePK();
            var amount = financialAccountTransactionDetailValue.getAmount();
            var comment = financialAccountTransactionDetailValue.getComment();
            
            financialAccountTransactionDetail = financialAccountTransactionDetailFactory.create(financialAccountTransactionPK,
                    financialAccountTransactionName, financialAccountPK, financialAccountTransactionTypePK, amount, comment, session.getStartTime(),
                    Session.MAX_TIME);
            
            financialAccountTransaction.setActiveDetail(financialAccountTransactionDetail);
            financialAccountTransaction.setLastDetail(financialAccountTransactionDetail);
            
            sendEvent(financialAccountTransactionPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteFinancialAccountTransaction(FinancialAccountTransaction financialAccountTransaction, BasePK deletedBy) {
        var financialAccountTransactionDetail = financialAccountTransaction.getLastDetailForUpdate();
        financialAccountTransactionDetail.setThruTime(session.getStartTime());
        financialAccountTransaction.setActiveDetail(null);
        financialAccountTransaction.store();
        
        sendEvent(financialAccountTransaction.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteFinancialAccountTransactions(List<FinancialAccountTransaction> financialAccountTransactions, BasePK deletedBy) {
        financialAccountTransactions.forEach((financialAccountTransaction) -> 
                deleteFinancialAccountTransaction(financialAccountTransaction, deletedBy)
        );
    }
    
    public void deleteFinancialAccountTransactionsByFinancialAccount(FinancialAccount financialAccount, BasePK deletedBy) {
        deleteFinancialAccountTransactions(getFinancialAccountTransactionsByFinancialAccountForUpdate(financialAccount), deletedBy);
    }
    
    public void deleteFinancialAccountTransactionsByFinancialAccountTransactionType(FinancialAccountTransactionType financialAccountTransactionType, BasePK deletedBy) {
        deleteFinancialAccountTransactions(getFinancialAccountTransactionsByFinancialAccountTransactionTypeForUpdate(financialAccountTransactionType), deletedBy);
    }
    
}

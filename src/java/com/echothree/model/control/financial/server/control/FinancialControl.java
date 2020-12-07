// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.model.control.financial.server.transfer.FinancialTransferCaches;
import com.echothree.model.data.accounting.common.pk.CurrencyPK;
import com.echothree.model.data.accounting.common.pk.GlAccountPK;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.financial.common.pk.FinancialAccountAliasTypePK;
import com.echothree.model.data.financial.common.pk.FinancialAccountPK;
import com.echothree.model.data.financial.common.pk.FinancialAccountRoleTypePK;
import com.echothree.model.data.financial.common.pk.FinancialAccountTransactionPK;
import com.echothree.model.data.financial.common.pk.FinancialAccountTransactionTypePK;
import com.echothree.model.data.financial.common.pk.FinancialAccountTypePK;
import com.echothree.model.data.financial.server.entity.FinancialAccount;
import com.echothree.model.data.financial.server.entity.FinancialAccountAlias;
import com.echothree.model.data.financial.server.entity.FinancialAccountAliasType;
import com.echothree.model.data.financial.server.entity.FinancialAccountAliasTypeDescription;
import com.echothree.model.data.financial.server.entity.FinancialAccountAliasTypeDetail;
import com.echothree.model.data.financial.server.entity.FinancialAccountDetail;
import com.echothree.model.data.financial.server.entity.FinancialAccountRole;
import com.echothree.model.data.financial.server.entity.FinancialAccountRoleType;
import com.echothree.model.data.financial.server.entity.FinancialAccountRoleTypeDescription;
import com.echothree.model.data.financial.server.entity.FinancialAccountStatus;
import com.echothree.model.data.financial.server.entity.FinancialAccountTransaction;
import com.echothree.model.data.financial.server.entity.FinancialAccountTransactionDetail;
import com.echothree.model.data.financial.server.entity.FinancialAccountTransactionType;
import com.echothree.model.data.financial.server.entity.FinancialAccountTransactionTypeDescription;
import com.echothree.model.data.financial.server.entity.FinancialAccountTransactionTypeDetail;
import com.echothree.model.data.financial.server.entity.FinancialAccountType;
import com.echothree.model.data.financial.server.entity.FinancialAccountTypeDescription;
import com.echothree.model.data.financial.server.entity.FinancialAccountTypeDetail;
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
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.sequence.common.pk.SequenceTypePK;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.common.pk.WorkflowEntrancePK;
import com.echothree.model.data.workflow.common.pk.WorkflowPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class FinancialControl
        extends BaseModelControl {
    
    /** Creates a new instance of FinancialControl */
    public FinancialControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Financial Transfer Caches
    // --------------------------------------------------------------------------------
    
    private FinancialTransferCaches financialTransferCaches;
    
    public FinancialTransferCaches getFinancialTransferCaches(UserVisit userVisit) {
        if(financialTransferCaches == null) {
            financialTransferCaches = new FinancialTransferCaches(userVisit, this);
        }
        
        return financialTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Financial Account Role Types
    // --------------------------------------------------------------------------------
    
    public FinancialAccountRoleType createFinancialAccountRoleType(String financialAccountRoleTypeName, Integer sortOrder) {
        return FinancialAccountRoleTypeFactory.getInstance().create(financialAccountRoleTypeName, sortOrder);
    }
    
    private static final Map<EntityPermission, String> getFinancialAccountRoleTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccountroletypes " +
                "ORDER BY finatyp_sortorder, finatyp_financialaccountroletypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccountroletypes " +
                "FOR UPDATE");
        getFinancialAccountRoleTypesQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<FinancialAccountRoleType> getFinancialAccountRoleTypes(EntityPermission entityPermission) {
        return FinancialAccountRoleTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getFinancialAccountRoleTypesQueries);
    }
    
    public List<FinancialAccountRoleType> getFinancialAccountRoleTypes() {
        return getFinancialAccountRoleTypes(EntityPermission.READ_ONLY);
    }
    
    public List<FinancialAccountRoleType> getFinancialAccountRoleTypesForUpdate() {
        return getFinancialAccountRoleTypes(EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getFinancialAccountRoleTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccountroletypes " +
                "WHERE finatyp_financialaccountroletypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccountroletypes " +
                "WHERE finatyp_financialaccountroletypename = ? " +
                "FOR UPDATE");
        getFinancialAccountRoleTypeQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private FinancialAccountRoleType getFinancialAccountRoleTypeByName(String financialAccountRoleTypeName, EntityPermission entityPermission) {
        return FinancialAccountRoleTypeFactory.getInstance().getEntityFromQuery(entityPermission, getFinancialAccountRoleTypeQueries,
                financialAccountRoleTypeName);
    }
    
    public FinancialAccountRoleType getFinancialAccountRoleTypeByName(String financialAccountRoleTypeName) {
        return getFinancialAccountRoleTypeByName(financialAccountRoleTypeName, EntityPermission.READ_ONLY);
    }
    
    public FinancialAccountRoleType getFinancialAccountRoleTypeByNameForUpdate(String financialAccountRoleTypeName) {
        return getFinancialAccountRoleTypeByName(financialAccountRoleTypeName, EntityPermission.READ_WRITE);
    }
    
    public FinancialAccountRoleTypeTransfer getFinancialAccountRoleTypeTransfer(UserVisit userVisit,
            FinancialAccountRoleType financialAccountRoleType) {
        return getFinancialTransferCaches(userVisit).getFinancialAccountRoleTypeTransferCache().getFinancialAccountRoleTypeTransfer(financialAccountRoleType);
    }
    
    private List<FinancialAccountRoleTypeTransfer> getFinancialAccountRoleTypeTransfers(final UserVisit userVisit, final List<FinancialAccountRoleType> financialAccountRoleTypes) {
        List<FinancialAccountRoleTypeTransfer> financialAccountRoleTypeTransfers = new ArrayList<>(financialAccountRoleTypes.size());
        FinancialAccountRoleTypeTransferCache financialAccountRoleTypeTransferCache = getFinancialTransferCaches(userVisit).getFinancialAccountRoleTypeTransferCache();
        
        financialAccountRoleTypes.forEach((financialAccountRoleType) ->
                financialAccountRoleTypeTransfers.add(financialAccountRoleTypeTransferCache.getFinancialAccountRoleTypeTransfer(financialAccountRoleType))
        );

            return financialAccountRoleTypeTransfers;
    }
    
    public List<FinancialAccountRoleTypeTransfer> getFinancialAccountRoleTypeTransfers(UserVisit userVisit) {
        return getFinancialAccountRoleTypeTransfers(userVisit, getFinancialAccountRoleTypes());
    }
    
    // --------------------------------------------------------------------------------
    //   Financial Account Role Type Description
    // --------------------------------------------------------------------------------
    
    public FinancialAccountRoleTypeDescription createFinancialAccountRoleTypeDescription(FinancialAccountRoleType financialAccountRoleType, Language language, String description) {
        return FinancialAccountRoleTypeDescriptionFactory.getInstance().create(financialAccountRoleType, language, description);
    }
    
    private static final Map<EntityPermission, String> getFinancialAccountRoleTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccountroletypedescriptions " +
                "WHERE finatypd_finatyp_financialaccountroletypeid = ? AND finatypd_lang_languageid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccountroletypedescriptions " +
                "WHERE finatypd_finatyp_financialaccountroletypeid = ? AND finatypd_lang_languageid = ? " +
                "FOR UPDATE");
        getFinancialAccountRoleTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private FinancialAccountRoleTypeDescription getFinancialAccountRoleTypeDescription(FinancialAccountRoleType financialAccountRoleType, Language language, EntityPermission entityPermission) {
        return FinancialAccountRoleTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getFinancialAccountRoleTypeDescriptionQueries,
                financialAccountRoleType, language);
    }
    
    public FinancialAccountRoleTypeDescription getFinancialAccountRoleTypeDescription(FinancialAccountRoleType financialAccountRoleType, Language language) {
        return getFinancialAccountRoleTypeDescription(financialAccountRoleType, language, EntityPermission.READ_ONLY);
    }
    
    public FinancialAccountRoleTypeDescription getFinancialAccountRoleTypeDescriptionForUpdate(FinancialAccountRoleType financialAccountRoleType, Language language) {
        return getFinancialAccountRoleTypeDescription(financialAccountRoleType, language, EntityPermission.READ_WRITE);
    }
    
    public String getBestFinancialAccountRoleTypeDescription(FinancialAccountRoleType financialAccountRoleType, Language language) {
        String description;
        FinancialAccountRoleTypeDescription financialAccountRoleTypeDescription = getFinancialAccountRoleTypeDescription(financialAccountRoleType, language);
        
        if(financialAccountRoleTypeDescription == null && !language.getIsDefault()) {
            financialAccountRoleTypeDescription = getFinancialAccountRoleTypeDescription(financialAccountRoleType, getPartyControl().getDefaultLanguage());
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
    
    public FinancialAccountType createFinancialAccountType(String financialAccountTypeName, FinancialAccountType parentFinancialAccountType,
            GlAccount defaultGlAccount, SequenceType financialAccountSequenceType, SequenceType financialAccountTransactionSequenceType,
            Workflow financialAccountWorkflow, WorkflowEntrance financialAccountWorkflowEntrance, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        FinancialAccountType defaultFinancialAccountType = getDefaultFinancialAccountType();
        boolean defaultFound = defaultFinancialAccountType != null;
        
        if(defaultFound && isDefault) {
            FinancialAccountTypeDetailValue defaultFinancialAccountTypeDetailValue = getDefaultFinancialAccountTypeDetailValueForUpdate();
            
            defaultFinancialAccountTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateFinancialAccountTypeFromValue(defaultFinancialAccountTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        FinancialAccountType financialAccountType = FinancialAccountTypeFactory.getInstance().create();
        FinancialAccountTypeDetail financialAccountTypeDetail = FinancialAccountTypeDetailFactory.getInstance().create(financialAccountType,
                financialAccountTypeName, parentFinancialAccountType, defaultGlAccount, financialAccountSequenceType, financialAccountTransactionSequenceType,
                financialAccountWorkflow, financialAccountWorkflowEntrance, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        financialAccountType = FinancialAccountTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                financialAccountType.getPrimaryKey());
        financialAccountType.setActiveDetail(financialAccountTypeDetail);
        financialAccountType.setLastDetail(financialAccountTypeDetail);
        financialAccountType.store();
        
        sendEventUsingNames(financialAccountType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return financialAccountType;
    }
    
    private static final Map<EntityPermission, String> getFinancialAccountTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccounttypes, financialaccounttypedetails " +
                "WHERE fnatyp_activedetailid = fnatypdt_financialaccounttypedetailid " +
                "AND fnatypdt_financialaccounttypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccounttypes, financialaccounttypedetails " +
                "WHERE fnatyp_activedetailid = fnatypdt_financialaccounttypedetailid " +
                "AND fnatypdt_financialaccounttypename = ? " +
                "FOR UPDATE");
        getFinancialAccountTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private FinancialAccountType getFinancialAccountTypeByName(String financialAccountTypeName, EntityPermission entityPermission) {
        return FinancialAccountTypeFactory.getInstance().getEntityFromQuery(entityPermission, getFinancialAccountTypeByNameQueries, financialAccountTypeName);
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

    private static final Map<EntityPermission, String> getDefaultFinancialAccountTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccounttypes, financialaccounttypedetails " +
                "WHERE fnatyp_activedetailid = fnatypdt_financialaccounttypedetailid " +
                "AND fnatypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccounttypes, financialaccounttypedetails " +
                "WHERE fnatyp_activedetailid = fnatypdt_financialaccounttypedetailid " +
                "AND fnatypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultFinancialAccountTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private FinancialAccountType getDefaultFinancialAccountType(EntityPermission entityPermission) {
        return FinancialAccountTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultFinancialAccountTypeQueries);
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

    private static final Map<EntityPermission, String> getFinancialAccountTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccounttypes, financialaccounttypedetails " +
                "WHERE fnatyp_activedetailid = fnatypdt_financialaccounttypedetailid " +
                "ORDER BY fnatypdt_sortorder, fnatypdt_financialaccounttypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccounttypes, financialaccounttypedetails " +
                "WHERE fnatyp_activedetailid = fnatypdt_financialaccounttypedetailid " +
                "FOR UPDATE");
        getFinancialAccountTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<FinancialAccountType> getFinancialAccountTypes(EntityPermission entityPermission) {
        return FinancialAccountTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getFinancialAccountTypesQueries);
    }

    public List<FinancialAccountType> getFinancialAccountTypes() {
        return getFinancialAccountTypes(EntityPermission.READ_ONLY);
    }

    public List<FinancialAccountType> getFinancialAccountTypesForUpdate() {
        return getFinancialAccountTypes(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getFinancialAccountTypesByParentFinancialAccountTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccounttypes, financialaccounttypedetails " +
                "WHERE fnatyp_activedetailid = fnatypdt_financialaccounttypedetailid AND fnatypdt_parentfinancialaccounttypeid = ? " +
                "ORDER BY fnatypdt_sortorder, fnatypdt_financialaccounttypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccounttypes, financialaccounttypedetails " +
                "WHERE fnatyp_activedetailid = fnatypdt_financialaccounttypedetailid AND fnatypdt_parentfinancialaccounttypeid = ? " +
                "FOR UPDATE");
        getFinancialAccountTypesByParentFinancialAccountTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<FinancialAccountType> getFinancialAccountTypesByParentFinancialAccountType(FinancialAccountType parentFinancialAccountType,
            EntityPermission entityPermission) {
        return FinancialAccountTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getFinancialAccountTypesByParentFinancialAccountTypeQueries,
                parentFinancialAccountType);
    }

    public List<FinancialAccountType> getFinancialAccountTypesByParentFinancialAccountType(FinancialAccountType parentFinancialAccountType) {
        return getFinancialAccountTypesByParentFinancialAccountType(parentFinancialAccountType, EntityPermission.READ_ONLY);
    }

    public List<FinancialAccountType> getFinancialAccountTypesByParentFinancialAccountTypeForUpdate(FinancialAccountType parentFinancialAccountType) {
        return getFinancialAccountTypesByParentFinancialAccountType(parentFinancialAccountType, EntityPermission.READ_WRITE);
    }

    public FinancialAccountTypeTransfer getFinancialAccountTypeTransfer(UserVisit userVisit, FinancialAccountType financialAccountType) {
        return getFinancialTransferCaches(userVisit).getFinancialAccountTypeTransferCache().getFinancialAccountTypeTransfer(financialAccountType);
    }
    
    public List<FinancialAccountTypeTransfer> getFinancialAccountTypeTransfers(UserVisit userVisit) {
        List<FinancialAccountType> financialAccountTypes = getFinancialAccountTypes();
        List<FinancialAccountTypeTransfer> financialAccountTypeTransfers = new ArrayList<>(financialAccountTypes.size());
        FinancialAccountTypeTransferCache financialAccountTypeTransferCache = getFinancialTransferCaches(userVisit).getFinancialAccountTypeTransferCache();
        
        financialAccountTypes.forEach((financialAccountType) ->
                financialAccountTypeTransfers.add(financialAccountTypeTransferCache.getFinancialAccountTypeTransfer(financialAccountType))
        );
        
        return financialAccountTypeTransfers;
    }
    
    public FinancialAccountTypeChoicesBean getFinancialAccountTypeChoices(String defaultFinancialAccountTypeChoice,
            Language language, boolean allowNullChoice) {
        List<FinancialAccountType> financialAccountTypes = getFinancialAccountTypes();
        int size = financialAccountTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultFinancialAccountTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var financialAccountType : financialAccountTypes) {
            FinancialAccountTypeDetail financialAccountTypeDetail = financialAccountType.getLastDetail();
            
            String label = getBestFinancialAccountTypeDescription(financialAccountType, language);
            String value = financialAccountTypeDetail.getFinancialAccountTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultFinancialAccountTypeChoice != null && defaultFinancialAccountTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && financialAccountTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new FinancialAccountTypeChoicesBean(labels, values, defaultValue);
    }
    
    public boolean isParentFinancialAccountTypeSafe(FinancialAccountType financialAccountType,
            FinancialAccountType parentFinancialAccountType) {
        boolean safe = true;
        
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
            FinancialAccountType financialAccountType = FinancialAccountTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     financialAccountTypeDetailValue.getFinancialAccountTypePK());
            FinancialAccountTypeDetail financialAccountTypeDetail = financialAccountType.getActiveDetailForUpdate();
            
            financialAccountTypeDetail.setThruTime(session.START_TIME_LONG);
            financialAccountTypeDetail.store();
            
            FinancialAccountTypePK financialAccountTypePK = financialAccountTypeDetail.getFinancialAccountTypePK(); // Not updated
            String financialAccountTypeName = financialAccountTypeDetailValue.getFinancialAccountTypeName();
            FinancialAccountTypePK parentFinancialAccountTypePK = financialAccountTypeDetailValue.getParentFinancialAccountTypePK();
            GlAccountPK defaultGlAccountPK = financialAccountTypeDetailValue.getDefaultGlAccountPK();
            SequenceTypePK financialAccountSequenceTypePK = financialAccountTypeDetailValue.getFinancialAccountSequenceTypePK();
            SequenceTypePK financialAccountTransactionSequenceTypePK = financialAccountTypeDetailValue.getFinancialAccountTransactionSequenceTypePK();
            WorkflowPK financialAccountWorkflowPK = financialAccountTypeDetailValue.getFinancialAccountWorkflowPK();
            WorkflowEntrancePK financialAccountWorkflowEntrancePK = financialAccountTypeDetailValue.getFinancialAccountWorkflowEntrancePK();
            Boolean isDefault = financialAccountTypeDetailValue.getIsDefault();
            Integer sortOrder = financialAccountTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                FinancialAccountType defaultFinancialAccountType = getDefaultFinancialAccountType();
                boolean defaultFound = defaultFinancialAccountType != null && !defaultFinancialAccountType.equals(financialAccountType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    FinancialAccountTypeDetailValue defaultFinancialAccountTypeDetailValue = getDefaultFinancialAccountTypeDetailValueForUpdate();
                    
                    defaultFinancialAccountTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateFinancialAccountTypeFromValue(defaultFinancialAccountTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            financialAccountTypeDetail = FinancialAccountTypeDetailFactory.getInstance().create(financialAccountTypePK, financialAccountTypeName,
                    parentFinancialAccountTypePK, defaultGlAccountPK, financialAccountSequenceTypePK, financialAccountTransactionSequenceTypePK,
                    financialAccountWorkflowPK, financialAccountWorkflowEntrancePK, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            financialAccountType.setActiveDetail(financialAccountTypeDetail);
            financialAccountType.setLastDetail(financialAccountTypeDetail);
            
            sendEventUsingNames(financialAccountTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateFinancialAccountTypeFromValue(FinancialAccountTypeDetailValue financialAccountTypeDetailValue, BasePK updatedBy) {
        updateFinancialAccountTypeFromValue(financialAccountTypeDetailValue, true, updatedBy);
    }
    
    private void deleteFinancialAccountType(FinancialAccountType financialAccountType, boolean checkDefault, BasePK deletedBy) {
        FinancialAccountTypeDetail financialAccountTypeDetail = financialAccountType.getLastDetailForUpdate();

        deleteFinancialAccountTypesByParentFinancialAccountType(financialAccountType, deletedBy);
        deleteFinancialAccountTypeDescriptionsByFinancialAccountType(financialAccountType, deletedBy);
        deleteFinancialAccountsByFinancialAccountType(financialAccountType, deletedBy);
        deleteFinancialAccountAliasTypesByFinancialAccountType(financialAccountType, deletedBy);
        
        financialAccountTypeDetail.setThruTime(session.START_TIME_LONG);
        financialAccountType.setActiveDetail(null);
        financialAccountType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            FinancialAccountType defaultFinancialAccountType = getDefaultFinancialAccountType();

            if(defaultFinancialAccountType == null) {
                List<FinancialAccountType> financialAccountTypes = getFinancialAccountTypesForUpdate();

                if(!financialAccountTypes.isEmpty()) {
                    Iterator<FinancialAccountType> iter = financialAccountTypes.iterator();
                    if(iter.hasNext()) {
                        defaultFinancialAccountType = iter.next();
                    }
                    FinancialAccountTypeDetailValue financialAccountTypeDetailValue = Objects.requireNonNull(defaultFinancialAccountType).getLastDetailForUpdate().getFinancialAccountTypeDetailValue().clone();

                    financialAccountTypeDetailValue.setIsDefault(Boolean.TRUE);
                    updateFinancialAccountTypeFromValue(financialAccountTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(financialAccountType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
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
    
    public FinancialAccountTypeDescription createFinancialAccountTypeDescription(FinancialAccountType financialAccountType, Language language, String description, BasePK createdBy) {
        FinancialAccountTypeDescription financialAccountTypeDescription = FinancialAccountTypeDescriptionFactory.getInstance().create(financialAccountType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(financialAccountType.getPrimaryKey(), EventTypes.MODIFY.name(), financialAccountTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return financialAccountTypeDescription;
    }
    
    private static final Map<EntityPermission, String> getFinancialAccountTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccounttypedescriptions " +
                "WHERE fnatypd_fnatyp_financialaccounttypeid = ? AND fnatypd_lang_languageid = ? AND fnatypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccounttypedescriptions " +
                "WHERE fnatypd_fnatyp_financialaccounttypeid = ? AND fnatypd_lang_languageid = ? AND fnatypd_thrutime = ? " +
                "FOR UPDATE");
        getFinancialAccountTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private FinancialAccountTypeDescription getFinancialAccountTypeDescription(FinancialAccountType financialAccountType, Language language, EntityPermission entityPermission) {
        return FinancialAccountTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getFinancialAccountTypeDescriptionQueries,
                financialAccountType, language, Session.MAX_TIME);
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
    
    private static final Map<EntityPermission, String> getFinancialAccountTypeDescriptionsByFinancialAccountTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccounttypedescriptions, languages " +
                "WHERE fnatypd_fnatyp_financialaccounttypeid = ? AND fnatypd_thrutime = ? AND fnatypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccounttypedescriptions " +
                "WHERE fnatypd_fnatyp_financialaccounttypeid = ? AND fnatypd_thrutime = ? " +
                "FOR UPDATE");
        getFinancialAccountTypeDescriptionsByFinancialAccountTypeQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<FinancialAccountTypeDescription> getFinancialAccountTypeDescriptionsByFinancialAccountType(FinancialAccountType financialAccountType, EntityPermission entityPermission) {
        return FinancialAccountTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getFinancialAccountTypeDescriptionsByFinancialAccountTypeQueries,
                financialAccountType, Session.MAX_TIME);
    }
    
    public List<FinancialAccountTypeDescription> getFinancialAccountTypeDescriptionsByFinancialAccountType(FinancialAccountType financialAccountType) {
        return getFinancialAccountTypeDescriptionsByFinancialAccountType(financialAccountType, EntityPermission.READ_ONLY);
    }
    
    public List<FinancialAccountTypeDescription> getFinancialAccountTypeDescriptionsByFinancialAccountTypeForUpdate(FinancialAccountType financialAccountType) {
        return getFinancialAccountTypeDescriptionsByFinancialAccountType(financialAccountType, EntityPermission.READ_WRITE);
    }
    
    public String getBestFinancialAccountTypeDescription(FinancialAccountType financialAccountType, Language language) {
        String description;
        FinancialAccountTypeDescription financialAccountTypeDescription = getFinancialAccountTypeDescription(financialAccountType, language);
        
        if(financialAccountTypeDescription == null && !language.getIsDefault()) {
            financialAccountTypeDescription = getFinancialAccountTypeDescription(financialAccountType, getPartyControl().getDefaultLanguage());
        }
        
        if(financialAccountTypeDescription == null) {
            description = financialAccountType.getLastDetail().getFinancialAccountTypeName();
        } else {
            description = financialAccountTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public FinancialAccountTypeDescriptionTransfer getFinancialAccountTypeDescriptionTransfer(UserVisit userVisit, FinancialAccountTypeDescription financialAccountTypeDescription) {
        return getFinancialTransferCaches(userVisit).getFinancialAccountTypeDescriptionTransferCache().getFinancialAccountTypeDescriptionTransfer(financialAccountTypeDescription);
    }
    
    public List<FinancialAccountTypeDescriptionTransfer> getFinancialAccountTypeDescriptionTransfersByFinancialAccountType(UserVisit userVisit, FinancialAccountType financialAccountType) {
        List<FinancialAccountTypeDescription> financialAccountTypeDescriptions = getFinancialAccountTypeDescriptionsByFinancialAccountType(financialAccountType);
        List<FinancialAccountTypeDescriptionTransfer> financialAccountTypeDescriptionTransfers = new ArrayList<>(financialAccountTypeDescriptions.size());
        FinancialAccountTypeDescriptionTransferCache financialAccountTypeDescriptionTransferCache = getFinancialTransferCaches(userVisit).getFinancialAccountTypeDescriptionTransferCache();
        
        financialAccountTypeDescriptions.forEach((financialAccountTypeDescription) ->
                financialAccountTypeDescriptionTransfers.add(financialAccountTypeDescriptionTransferCache.getFinancialAccountTypeDescriptionTransfer(financialAccountTypeDescription))
        );
        
        return financialAccountTypeDescriptionTransfers;
    }
    
    public void updateFinancialAccountTypeDescriptionFromValue(FinancialAccountTypeDescriptionValue financialAccountTypeDescriptionValue, BasePK updatedBy) {
        if(financialAccountTypeDescriptionValue.hasBeenModified()) {
            FinancialAccountTypeDescription financialAccountTypeDescription = FinancialAccountTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    financialAccountTypeDescriptionValue.getPrimaryKey());
            
            financialAccountTypeDescription.setThruTime(session.START_TIME_LONG);
            financialAccountTypeDescription.store();
            
            FinancialAccountType financialAccountType = financialAccountTypeDescription.getFinancialAccountType();
            Language language = financialAccountTypeDescription.getLanguage();
            String description = financialAccountTypeDescriptionValue.getDescription();
            
            financialAccountTypeDescription = FinancialAccountTypeDescriptionFactory.getInstance().create(financialAccountType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(financialAccountType.getPrimaryKey(), EventTypes.MODIFY.name(), financialAccountTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteFinancialAccountTypeDescription(FinancialAccountTypeDescription financialAccountTypeDescription, BasePK deletedBy) {
        financialAccountTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(financialAccountTypeDescription.getFinancialAccountTypePK(), EventTypes.MODIFY.name(), financialAccountTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteFinancialAccountTypeDescriptionsByFinancialAccountType(FinancialAccountType financialAccountType, BasePK deletedBy) {
        List<FinancialAccountTypeDescription> financialAccountTypeDescriptions = getFinancialAccountTypeDescriptionsByFinancialAccountTypeForUpdate(financialAccountType);
        
        financialAccountTypeDescriptions.forEach((financialAccountTypeDescription) -> 
                deleteFinancialAccountTypeDescription(financialAccountTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Financial Account Transaction Types
    // --------------------------------------------------------------------------------
    
    public FinancialAccountTransactionType createFinancialAccountTransactionType(FinancialAccountType financialAccountType,
            String financialAccountTransactionTypeName, FinancialAccountTransactionType parentFinancialAccountTransactionType, GlAccount glAccount,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        FinancialAccountTransactionType defaultFinancialAccountTransactionType = getDefaultFinancialAccountTransactionType(financialAccountType);
        boolean defaultFound = defaultFinancialAccountTransactionType != null;
        
        if(defaultFound && isDefault) {
            FinancialAccountTransactionTypeDetailValue defaultFinancialAccountTransactionTypeDetailValue = getDefaultFinancialAccountTransactionTypeDetailValueForUpdate(financialAccountType);
            
            defaultFinancialAccountTransactionTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateFinancialAccountTransactionTypeFromValue(defaultFinancialAccountTransactionTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        FinancialAccountTransactionType financialAccountTransactionType = FinancialAccountTransactionTypeFactory.getInstance().create();
        FinancialAccountTransactionTypeDetail financialAccountTransactionTypeDetail = FinancialAccountTransactionTypeDetailFactory.getInstance().create(session,
                financialAccountTransactionType, financialAccountType, financialAccountTransactionTypeName, parentFinancialAccountTransactionType, glAccount,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        financialAccountTransactionType = FinancialAccountTransactionTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                financialAccountTransactionType.getPrimaryKey());
        financialAccountTransactionType.setActiveDetail(financialAccountTransactionTypeDetail);
        financialAccountTransactionType.setLastDetail(financialAccountTransactionTypeDetail);
        financialAccountTransactionType.store();
        
        sendEventUsingNames(financialAccountTransactionType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return financialAccountTransactionType;
    }
    
    private static final Map<EntityPermission, String> getFinancialAccountTransactionTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccounttransactiontypes, financialaccounttransactiontypedetails " +
                "WHERE fnatrxtyp_activedetailid = fnatrxtypdt_financialaccounttransactiontypedetailid " +
                "AND fnatrxtypdt_fnatyp_financialaccounttypeid = ? AND fnatrxtypdt_financialaccounttransactiontypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccounttransactiontypes, financialaccounttransactiontypedetails " +
                "WHERE fnatrxtyp_activedetailid = fnatrxtypdt_financialaccounttransactiontypedetailid " +
                "AND fnatrxtypdt_fnatyp_financialaccounttypeid = ? AND fnatrxtypdt_financialaccounttransactiontypename = ? " +
                "FOR UPDATE");
        getFinancialAccountTransactionTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private FinancialAccountTransactionType getFinancialAccountTransactionTypeByName(FinancialAccountType financialAccountType,
            String financialAccountTransactionTypeName, EntityPermission entityPermission) {
        return FinancialAccountTransactionTypeFactory.getInstance().getEntityFromQuery(entityPermission,
                getFinancialAccountTransactionTypeByNameQueries, financialAccountType, financialAccountTransactionTypeName);
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
    
    private static final Map<EntityPermission, String> getDefaultFinancialAccountTransactionTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccounttransactiontypes, financialaccounttransactiontypedetails " +
                "WHERE fnatrxtyp_activedetailid = fnatrxtypdt_financialaccounttransactiontypedetailid " +
                "AND fnatrxtypdt_fnatyp_financialaccounttypeid = ? AND fnatrxtypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccounttransactiontypes, financialaccounttransactiontypedetails " +
                "WHERE fnatrxtyp_activedetailid = fnatrxtypdt_financialaccounttransactiontypedetailid " +
                "AND fnatrxtypdt_fnatyp_financialaccounttypeid = ? AND fnatrxtypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultFinancialAccountTransactionTypeQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private FinancialAccountTransactionType getDefaultFinancialAccountTransactionType(FinancialAccountType financialAccountType,
            EntityPermission entityPermission) {
        return FinancialAccountTransactionTypeFactory.getInstance().getEntityFromQuery(entityPermission,
                getDefaultFinancialAccountTransactionTypeQueries, financialAccountType);
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
    
    private static final Map<EntityPermission, String> getFinancialAccountTransactionTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccounttransactiontypes, financialaccounttransactiontypedetails " +
                "WHEREfnatrxtyp_activedetailid = fnatrxtypdt_financialaccounttransactiontypedetailid " +
                "AND fnatrxtypdt_fnatyp_financialaccounttypeid = ? " +
                "ORDER BY fnatrxtypdt_sortorder, fnatrxtypdt_financialaccounttransactiontypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccounttransactiontypes, financialaccounttransactiontypedetails " +
                "WHERE fnatrxtyp_activedetailid = fnatrxtypdt_financialaccounttransactiontypedetailid " +
                "AND fnatrxtypdt_fnatyp_financialaccounttypeid = ? " +
                "FOR UPDATE");
        getFinancialAccountTransactionTypesQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<FinancialAccountTransactionType> getFinancialAccountTransactionTypes(FinancialAccountType financialAccountType,
            EntityPermission entityPermission) {
        return FinancialAccountTransactionTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getFinancialAccountTransactionTypesQueries,
                financialAccountType);
    }
    
    public List<FinancialAccountTransactionType> getFinancialAccountTransactionTypes(FinancialAccountType financialAccountType) {
        return getFinancialAccountTransactionTypes(financialAccountType, EntityPermission.READ_ONLY);
    }
    
    public List<FinancialAccountTransactionType> getFinancialAccountTransactionTypesForUpdate(FinancialAccountType financialAccountType) {
        return getFinancialAccountTransactionTypes(financialAccountType, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getFinancialAccountTransactionTypesByParentFinancialAccountTransactionTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccounttransactiontypes, financialaccounttransactiontypedetails " +
                "WHERE fnatrxtyp_activedetailid = fnatrxtypdt_financialaccounttransactiontypedetailid AND fnatrxtypdt_parentfinancialaccounttransactiontypeid = ? " +
                "ORDER BY fnatrxtypdt_sortorder, fnatrxtypdt_financialaccounttransactiontypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccounttransactiontypes, financialaccounttransactiontypedetails " +
                "WHERE fnatrxtyp_activedetailid = fnatrxtypdt_financialaccounttransactiontypedetailid AND fnatrxtypdt_parentfinancialaccounttransactiontypeid = ? " +
                "FOR UPDATE");
        getFinancialAccountTransactionTypesByParentFinancialAccountTransactionTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<FinancialAccountTransactionType> getFinancialAccountTransactionTypesByParentFinancialAccountTransactionType(FinancialAccountTransactionType parentFinancialAccountTransactionType,
            EntityPermission entityPermission) {
        return FinancialAccountTransactionTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getFinancialAccountTransactionTypesByParentFinancialAccountTransactionTypeQueries,
                parentFinancialAccountTransactionType);
    }

    public List<FinancialAccountTransactionType> getFinancialAccountTransactionTypesByParentFinancialAccountTransactionType(FinancialAccountTransactionType parentFinancialAccountTransactionType) {
        return getFinancialAccountTransactionTypesByParentFinancialAccountTransactionType(parentFinancialAccountTransactionType, EntityPermission.READ_ONLY);
    }

    public List<FinancialAccountTransactionType> getFinancialAccountTransactionTypesByParentFinancialAccountTransactionTypeForUpdate(FinancialAccountTransactionType parentFinancialAccountTransactionType) {
        return getFinancialAccountTransactionTypesByParentFinancialAccountTransactionType(parentFinancialAccountTransactionType, EntityPermission.READ_WRITE);
    }

    public FinancialAccountTransactionTypeTransfer getFinancialAccountTransactionTypeTransfer(UserVisit userVisit,
            FinancialAccountTransactionType financialAccountTransactionType) {
        return getFinancialTransferCaches(userVisit).getFinancialAccountTransactionTypeTransferCache().getFinancialAccountTransactionTypeTransfer(financialAccountTransactionType);
    }
    
    public List<FinancialAccountTransactionTypeTransfer> getFinancialAccountTransactionTypeTransfers(UserVisit userVisit,
            FinancialAccountType financialAccountType) {
        List<FinancialAccountTransactionType> financialAccountTransactionTypes = getFinancialAccountTransactionTypes(financialAccountType);
        List<FinancialAccountTransactionTypeTransfer> financialAccountTransactionTypeTransfers = new ArrayList<>(financialAccountTransactionTypes.size());
        FinancialAccountTransactionTypeTransferCache financialAccountTransactionTypeTransferCache = getFinancialTransferCaches(userVisit).getFinancialAccountTransactionTypeTransferCache();
        
        financialAccountTransactionTypes.forEach((financialAccountTransactionType) ->
                financialAccountTransactionTypeTransfers.add(financialAccountTransactionTypeTransferCache.getFinancialAccountTransactionTypeTransfer(financialAccountTransactionType))
        );
        
        return financialAccountTransactionTypeTransfers;
    }
    
    public FinancialAccountTransactionTypeChoicesBean getFinancialAccountTransactionTypeChoices(FinancialAccountType financialAccountType,
            String defaultFinancialAccountTransactionTypeChoice, Language language, boolean allowNullChoice) {
        List<FinancialAccountTransactionType> financialAccountTransactionTypes = getFinancialAccountTransactionTypes(financialAccountType);
        int size = financialAccountTransactionTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultFinancialAccountTransactionTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var financialAccountTransactionType : financialAccountTransactionTypes) {
            FinancialAccountTransactionTypeDetail financialAccountTransactionTypeDetail = financialAccountTransactionType.getLastDetail();
            
            String label = getBestFinancialAccountTransactionTypeDescription(financialAccountTransactionType, language);
            String value = financialAccountTransactionTypeDetail.getFinancialAccountTransactionTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultFinancialAccountTransactionTypeChoice != null && defaultFinancialAccountTransactionTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && financialAccountTransactionTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new FinancialAccountTransactionTypeChoicesBean(labels, values, defaultValue);
    }
    
    public boolean isParentFinancialAccountTransactionTypeSafe(FinancialAccountTransactionType financialAccountTransactionType,
            FinancialAccountTransactionType parentFinancialAccountTransactionType) {
        boolean safe = true;
        
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
            FinancialAccountTransactionType financialAccountTransactionType = FinancialAccountTransactionTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     financialAccountTransactionTypeDetailValue.getFinancialAccountTransactionTypePK());
            FinancialAccountTransactionTypeDetail financialAccountTransactionTypeDetail = financialAccountTransactionType.getActiveDetailForUpdate();
            
            financialAccountTransactionTypeDetail.setThruTime(session.START_TIME_LONG);
            financialAccountTransactionTypeDetail.store();
            
            FinancialAccountTransactionTypePK financialAccountTransactionTypePK = financialAccountTransactionTypeDetail.getFinancialAccountTransactionTypePK(); // Not updated
            FinancialAccountType financialAccountType = financialAccountTransactionTypeDetail.getFinancialAccountType(); // Not updated
            FinancialAccountTypePK financialAccountTypePK = financialAccountType.getPrimaryKey(); // Not updated
            String financialAccountTransactionTypeName = financialAccountTransactionTypeDetailValue.getFinancialAccountTransactionTypeName();
            FinancialAccountTransactionTypePK parentFinancialAccountTransactionTypePK = financialAccountTransactionTypeDetailValue.getParentFinancialAccountTransactionTypePK();
            GlAccountPK glAccountPK = financialAccountTransactionTypeDetailValue.getGlAccountPK();
            Boolean isDefault = financialAccountTransactionTypeDetailValue.getIsDefault();
            Integer sortOrder = financialAccountTransactionTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                FinancialAccountTransactionType defaultFinancialAccountTransactionType = getDefaultFinancialAccountTransactionType(financialAccountType);
                boolean defaultFound = defaultFinancialAccountTransactionType != null && !defaultFinancialAccountTransactionType.equals(financialAccountTransactionType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    FinancialAccountTransactionTypeDetailValue defaultFinancialAccountTransactionTypeDetailValue = getDefaultFinancialAccountTransactionTypeDetailValueForUpdate(financialAccountType);
                    
                    defaultFinancialAccountTransactionTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateFinancialAccountTransactionTypeFromValue(defaultFinancialAccountTransactionTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            financialAccountTransactionTypeDetail = FinancialAccountTransactionTypeDetailFactory.getInstance().create(session,
                    financialAccountTransactionTypePK, financialAccountTypePK, financialAccountTransactionTypeName, parentFinancialAccountTransactionTypePK,
                    glAccountPK, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            financialAccountTransactionType.setActiveDetail(financialAccountTransactionTypeDetail);
            financialAccountTransactionType.setLastDetail(financialAccountTransactionTypeDetail);
            
            sendEventUsingNames(financialAccountTransactionTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateFinancialAccountTransactionTypeFromValue(FinancialAccountTransactionTypeDetailValue financialAccountTransactionTypeDetailValue,
            BasePK updatedBy) {
        updateFinancialAccountTransactionTypeFromValue(financialAccountTransactionTypeDetailValue, true, updatedBy);
    }
    
    private void deleteFinancialAccountTransactionType(FinancialAccountTransactionType financialAccountTransactionType, boolean checkDefault, BasePK deletedBy) {
        FinancialAccountTransactionTypeDetail financialAccountTransactionTypeDetail = financialAccountTransactionType.getLastDetailForUpdate();

        deleteFinancialAccountTransactionTypesByParentFinancialAccountTransactionType(financialAccountTransactionType, deletedBy);
        deleteFinancialAccountTransactionTypeDescriptionsByFinancialAccountTransactionType(financialAccountTransactionType, deletedBy);
        deleteFinancialAccountTransactionsByFinancialAccountTransactionType(financialAccountTransactionType, deletedBy);
        
        financialAccountTransactionTypeDetail.setThruTime(session.START_TIME_LONG);
        financialAccountTransactionType.setActiveDetail(null);
        financialAccountTransactionType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            FinancialAccountType financialAccountType = financialAccountTransactionTypeDetail.getFinancialAccountType();
            FinancialAccountTransactionType defaultFinancialAccountTransactionType = getDefaultFinancialAccountTransactionType(financialAccountType);

            if(defaultFinancialAccountTransactionType == null) {
                List<FinancialAccountTransactionType> financialAccountTransactionTypes = getFinancialAccountTransactionTypesForUpdate(financialAccountType);

                if(!financialAccountTransactionTypes.isEmpty()) {
                    Iterator<FinancialAccountTransactionType> iter = financialAccountTransactionTypes.iterator();
                    if(iter.hasNext()) {
                        defaultFinancialAccountTransactionType = iter.next();
                    }
                    FinancialAccountTransactionTypeDetailValue financialAccountTransactionTypeDetailValue = Objects.requireNonNull(defaultFinancialAccountTransactionType).getLastDetailForUpdate().getFinancialAccountTransactionTypeDetailValue().clone();

                    financialAccountTransactionTypeDetailValue.setIsDefault(Boolean.TRUE);
                    updateFinancialAccountTransactionTypeFromValue(financialAccountTransactionTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(financialAccountTransactionType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
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
    
    public FinancialAccountTransactionTypeDescription createFinancialAccountTransactionTypeDescription(FinancialAccountTransactionType financialAccountTransactionType,
            Language language, String description, BasePK createdBy) {
        FinancialAccountTransactionTypeDescription financialAccountTransactionTypeDescription = FinancialAccountTransactionTypeDescriptionFactory.getInstance().create(session,
                financialAccountTransactionType, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(financialAccountTransactionType.getPrimaryKey(), EventTypes.MODIFY.name(), financialAccountTransactionTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return financialAccountTransactionTypeDescription;
    }
    
    private static final Map<EntityPermission, String> getFinancialAccountTransactionTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccounttransactiontypedescriptions " +
                "WHERE fnatrxtypd_fnatrxtyp_financialaccounttransactiontypeid = ? AND fnatrxtypd_lang_languageid = ? AND fnatrxtypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccounttransactiontypedescriptions " +
                "WHERE fnatrxtypd_fnatrxtyp_financialaccounttransactiontypeid = ? AND fnatrxtypd_lang_languageid = ? AND fnatrxtypd_thrutime = ? " +
                "FOR UPDATE");
        getFinancialAccountTransactionTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private FinancialAccountTransactionTypeDescription getFinancialAccountTransactionTypeDescription(FinancialAccountTransactionType financialAccountTransactionType,
            Language language, EntityPermission entityPermission) {
        return FinancialAccountTransactionTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getFinancialAccountTransactionTypeDescriptionQueries,
                financialAccountTransactionType, language, Session.MAX_TIME);
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
    
    private static final Map<EntityPermission, String> getFinancialAccountTransactionTypeDescriptionsByFinancialAccountTransactionTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccounttransactiontypedescriptions, languages " +
                "WHERE fnatrxtypd_fnatrxtyp_financialaccounttransactiontypeid = ? AND fnatrxtypd_thrutime = ? AND fnatrxtypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccounttransactiontypedescriptions " +
                "WHERE fnatrxtypd_fnatrxtyp_financialaccounttransactiontypeid = ? AND fnatrxtypd_thrutime = ? " +
                "FOR UPDATE");
        getFinancialAccountTransactionTypeDescriptionsByFinancialAccountTransactionTypeQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<FinancialAccountTransactionTypeDescription> getFinancialAccountTransactionTypeDescriptionsByFinancialAccountTransactionType(FinancialAccountTransactionType financialAccountTransactionType,
            EntityPermission entityPermission) {
        return FinancialAccountTransactionTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getFinancialAccountTransactionTypeDescriptionsByFinancialAccountTransactionTypeQueries,
                financialAccountTransactionType, Session.MAX_TIME);
    }
    
    public List<FinancialAccountTransactionTypeDescription> getFinancialAccountTransactionTypeDescriptionsByFinancialAccountTransactionType(FinancialAccountTransactionType financialAccountTransactionType) {
        return getFinancialAccountTransactionTypeDescriptionsByFinancialAccountTransactionType(financialAccountTransactionType, EntityPermission.READ_ONLY);
    }
    
    public List<FinancialAccountTransactionTypeDescription> getFinancialAccountTransactionTypeDescriptionsByFinancialAccountTransactionTypeForUpdate(FinancialAccountTransactionType financialAccountTransactionType) {
        return getFinancialAccountTransactionTypeDescriptionsByFinancialAccountTransactionType(financialAccountTransactionType, EntityPermission.READ_WRITE);
    }
    
    public String getBestFinancialAccountTransactionTypeDescription(FinancialAccountTransactionType financialAccountTransactionType, Language language) {
        String description;
        FinancialAccountTransactionTypeDescription financialAccountTransactionTypeDescription = getFinancialAccountTransactionTypeDescription(financialAccountTransactionType, language);
        
        if(financialAccountTransactionTypeDescription == null && !language.getIsDefault()) {
            financialAccountTransactionTypeDescription = getFinancialAccountTransactionTypeDescription(financialAccountTransactionType, getPartyControl().getDefaultLanguage());
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
        return getFinancialTransferCaches(userVisit).getFinancialAccountTransactionTypeDescriptionTransferCache().getFinancialAccountTransactionTypeDescriptionTransfer(financialAccountTransactionTypeDescription);
    }
    
    public List<FinancialAccountTransactionTypeDescriptionTransfer> getFinancialAccountTransactionTypeDescriptionTransfersByFinancialAccountTransactionType(UserVisit userVisit,
            FinancialAccountTransactionType financialAccountTransactionType) {
        List<FinancialAccountTransactionTypeDescription> financialAccountTransactionTypeDescriptions = getFinancialAccountTransactionTypeDescriptionsByFinancialAccountTransactionType(financialAccountTransactionType);
        List<FinancialAccountTransactionTypeDescriptionTransfer> financialAccountTransactionTypeDescriptionTransfers = new ArrayList<>(financialAccountTransactionTypeDescriptions.size());
        FinancialAccountTransactionTypeDescriptionTransferCache financialAccountTransactionTypeDescriptionTransferCache = getFinancialTransferCaches(userVisit).getFinancialAccountTransactionTypeDescriptionTransferCache();
        
        financialAccountTransactionTypeDescriptions.forEach((financialAccountTransactionTypeDescription) ->
                financialAccountTransactionTypeDescriptionTransfers.add(financialAccountTransactionTypeDescriptionTransferCache.getFinancialAccountTransactionTypeDescriptionTransfer(financialAccountTransactionTypeDescription))
        );
        
        return financialAccountTransactionTypeDescriptionTransfers;
    }
    
    public void updateFinancialAccountTransactionTypeDescriptionFromValue(FinancialAccountTransactionTypeDescriptionValue financialAccountTransactionTypeDescriptionValue,
            BasePK updatedBy) {
        if(financialAccountTransactionTypeDescriptionValue.hasBeenModified()) {
            FinancialAccountTransactionTypeDescription financialAccountTransactionTypeDescription = FinancialAccountTransactionTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    financialAccountTransactionTypeDescriptionValue.getPrimaryKey());
            
            financialAccountTransactionTypeDescription.setThruTime(session.START_TIME_LONG);
            financialAccountTransactionTypeDescription.store();
            
            FinancialAccountTransactionType financialAccountTransactionType = financialAccountTransactionTypeDescription.getFinancialAccountTransactionType();
            Language language = financialAccountTransactionTypeDescription.getLanguage();
            String description = financialAccountTransactionTypeDescriptionValue.getDescription();
            
            financialAccountTransactionTypeDescription = FinancialAccountTransactionTypeDescriptionFactory.getInstance().create(session,
                    financialAccountTransactionType, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(financialAccountTransactionType.getPrimaryKey(), EventTypes.MODIFY.name(), financialAccountTransactionTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteFinancialAccountTransactionTypeDescription(FinancialAccountTransactionTypeDescription financialAccountTransactionTypeDescription, BasePK deletedBy) {
        financialAccountTransactionTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(financialAccountTransactionTypeDescription.getFinancialAccountTransactionTypePK(), EventTypes.MODIFY.name(), financialAccountTransactionTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteFinancialAccountTransactionTypeDescriptionsByFinancialAccountTransactionType(FinancialAccountTransactionType financialAccountTransactionType, BasePK deletedBy) {
        List<FinancialAccountTransactionTypeDescription> financialAccountTransactionTypeDescriptions = getFinancialAccountTransactionTypeDescriptionsByFinancialAccountTransactionTypeForUpdate(financialAccountTransactionType);
        
        financialAccountTransactionTypeDescriptions.forEach((financialAccountTransactionTypeDescription) -> 
                deleteFinancialAccountTransactionTypeDescription(financialAccountTransactionTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Financial Account Alias Types
    // --------------------------------------------------------------------------------
    
    public FinancialAccountAliasType createFinancialAccountAliasType(FinancialAccountType financialAccountType, String financialAccountAliasTypeName,
            String validationPattern, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        FinancialAccountAliasType defaultFinancialAccountAliasType = getDefaultFinancialAccountAliasType(financialAccountType);
        boolean defaultFound = defaultFinancialAccountAliasType != null;
        
        if(defaultFound && isDefault) {
            FinancialAccountAliasTypeDetailValue defaultFinancialAccountAliasTypeDetailValue = getDefaultFinancialAccountAliasTypeDetailValueForUpdate(financialAccountType);
            
            defaultFinancialAccountAliasTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateFinancialAccountAliasTypeFromValue(defaultFinancialAccountAliasTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        FinancialAccountAliasType financialAccountAliasType = FinancialAccountAliasTypeFactory.getInstance().create();
        FinancialAccountAliasTypeDetail financialAccountAliasTypeDetail = FinancialAccountAliasTypeDetailFactory.getInstance().create(session,
                financialAccountAliasType, financialAccountType, financialAccountAliasTypeName, validationPattern, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        financialAccountAliasType = FinancialAccountAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, financialAccountAliasType.getPrimaryKey());
        financialAccountAliasType.setActiveDetail(financialAccountAliasTypeDetail);
        financialAccountAliasType.setLastDetail(financialAccountAliasTypeDetail);
        financialAccountAliasType.store();
        
        sendEventUsingNames(financialAccountAliasType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return financialAccountAliasType;
    }
    
    private static final Map<EntityPermission, String> getFinancialAccountAliasTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccountaliastypes, financialaccountaliastypedetails " +
                "WHERE finaat_activedetailid = finaatdt_financialaccountaliastypedetailid AND finaatdt_geot_financialaccounttypeid = ? " +
                "AND finaatdt_financialaccountaliastypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccountaliastypes, financialaccountaliastypedetails " +
                "WHERE finaat_activedetailid = finaatdt_financialaccountaliastypedetailid AND finaatdt_geot_financialaccounttypeid = ? " +
                "AND finaatdt_financialaccountaliastypename = ? " +
                "FOR UPDATE");
        getFinancialAccountAliasTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private FinancialAccountAliasType getFinancialAccountAliasTypeByName(FinancialAccountType financialAccountType, String financialAccountAliasTypeName,
            EntityPermission entityPermission) {
        return FinancialAccountAliasTypeFactory.getInstance().getEntityFromQuery(entityPermission, getFinancialAccountAliasTypeByNameQueries,
                financialAccountType, financialAccountAliasTypeName);
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
    
    private static final Map<EntityPermission, String> getDefaultFinancialAccountAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccountaliastypes, financialaccountaliastypedetails " +
                "WHERE finaat_activedetailid = finaatdt_financialaccountaliastypedetailid AND finaatdt_geot_financialaccounttypeid = ? " +
                "AND finaatdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccountaliastypes, financialaccountaliastypedetails " +
                "WHERE finaat_activedetailid = finaatdt_financialaccountaliastypedetailid AND finaatdt_geot_financialaccounttypeid = ? " +
                "AND finaatdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultFinancialAccountAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private FinancialAccountAliasType getDefaultFinancialAccountAliasType(FinancialAccountType financialAccountType, EntityPermission entityPermission) {
        return FinancialAccountAliasTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultFinancialAccountAliasTypeQueries, financialAccountType);
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
    
    private static final Map<EntityPermission, String> getFinancialAccountAliasTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccountaliastypes, financialaccountaliastypedetails " +
                "WHERE finaat_activedetailid = finaatdt_financialaccountaliastypedetailid AND finaatdt_geot_financialaccounttypeid = ? " +
                "ORDER BY finaatdt_sortorder, finaatdt_financialaccountaliastypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccountaliastypes, financialaccountaliastypedetails " +
                "WHERE finaat_activedetailid = finaatdt_financialaccountaliastypedetailid AND finaatdt_geot_financialaccounttypeid = ? " +
                "FOR UPDATE");
        getFinancialAccountAliasTypesQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<FinancialAccountAliasType> getFinancialAccountAliasTypes(FinancialAccountType financialAccountType, EntityPermission entityPermission) {
        return FinancialAccountAliasTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getFinancialAccountAliasTypesQueries, financialAccountType);
    }
    
    public List<FinancialAccountAliasType> getFinancialAccountAliasTypes(FinancialAccountType financialAccountType) {
        return getFinancialAccountAliasTypes(financialAccountType, EntityPermission.READ_ONLY);
    }
    
    public List<FinancialAccountAliasType> getFinancialAccountAliasTypesForUpdate(FinancialAccountType financialAccountType) {
        return getFinancialAccountAliasTypes(financialAccountType, EntityPermission.READ_WRITE);
    }
    
    public FinancialAccountAliasTypeTransfer getFinancialAccountAliasTypeTransfer(UserVisit userVisit, FinancialAccountAliasType financialAccountAliasType) {
        return getFinancialTransferCaches(userVisit).getFinancialAccountAliasTypeTransferCache().getFinancialAccountAliasTypeTransfer(financialAccountAliasType);
    }
    
    public List<FinancialAccountAliasTypeTransfer> getFinancialAccountAliasTypeTransfers(UserVisit userVisit, FinancialAccountType financialAccountType) {
        List<FinancialAccountAliasType> financialAccountAliasTypes = getFinancialAccountAliasTypes(financialAccountType);
        List<FinancialAccountAliasTypeTransfer> financialAccountAliasTypeTransfers = new ArrayList<>(financialAccountAliasTypes.size());
        FinancialAccountAliasTypeTransferCache financialAccountAliasTypeTransferCache = getFinancialTransferCaches(userVisit).getFinancialAccountAliasTypeTransferCache();
        
        financialAccountAliasTypes.forEach((financialAccountAliasType) ->
                financialAccountAliasTypeTransfers.add(financialAccountAliasTypeTransferCache.getFinancialAccountAliasTypeTransfer(financialAccountAliasType))
        );
        
        return financialAccountAliasTypeTransfers;
    }
    
    public FinancialAccountAliasTypeChoicesBean getFinancialAccountAliasTypeChoices(String defaultFinancialAccountAliasTypeChoice, Language language,
            boolean allowNullChoice, FinancialAccountType financialAccountType) {
        List<FinancialAccountAliasType> financialAccountAliasTypes = getFinancialAccountAliasTypes(financialAccountType);
        int size = financialAccountAliasTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultFinancialAccountAliasTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var financialAccountAliasType : financialAccountAliasTypes) {
            FinancialAccountAliasTypeDetail financialAccountAliasTypeDetail = financialAccountAliasType.getLastDetail();
            
            String label = getBestFinancialAccountAliasTypeDescription(financialAccountAliasType, language);
            String value = financialAccountAliasTypeDetail.getFinancialAccountAliasTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultFinancialAccountAliasTypeChoice != null && defaultFinancialAccountAliasTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && financialAccountAliasTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new FinancialAccountAliasTypeChoicesBean(labels, values, defaultValue);
    }
    
    private void updateFinancialAccountAliasTypeFromValue(FinancialAccountAliasTypeDetailValue financialAccountAliasTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(financialAccountAliasTypeDetailValue.hasBeenModified()) {
            FinancialAccountAliasType financialAccountAliasType = FinancialAccountAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    financialAccountAliasTypeDetailValue.getFinancialAccountAliasTypePK());
            FinancialAccountAliasTypeDetail financialAccountAliasTypeDetail = financialAccountAliasType.getActiveDetailForUpdate();
            
            financialAccountAliasTypeDetail.setThruTime(session.START_TIME_LONG);
            financialAccountAliasTypeDetail.store();
            
            FinancialAccountAliasTypePK financialAccountAliasTypePK = financialAccountAliasTypeDetail.getFinancialAccountAliasTypePK();
            FinancialAccountType financialAccountType = financialAccountAliasTypeDetail.getFinancialAccountType();
            FinancialAccountTypePK financialAccountTypePK = financialAccountType.getPrimaryKey();
            String financialAccountAliasTypeName = financialAccountAliasTypeDetailValue.getFinancialAccountAliasTypeName();
            String validationPattern = financialAccountAliasTypeDetailValue.getValidationPattern();
            Boolean isDefault = financialAccountAliasTypeDetailValue.getIsDefault();
            Integer sortOrder = financialAccountAliasTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                FinancialAccountAliasType defaultFinancialAccountAliasType = getDefaultFinancialAccountAliasType(financialAccountType);
                boolean defaultFound = defaultFinancialAccountAliasType != null && !defaultFinancialAccountAliasType.equals(financialAccountAliasType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    FinancialAccountAliasTypeDetailValue defaultFinancialAccountAliasTypeDetailValue = getDefaultFinancialAccountAliasTypeDetailValueForUpdate(financialAccountType);
                    
                    defaultFinancialAccountAliasTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateFinancialAccountAliasTypeFromValue(defaultFinancialAccountAliasTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            financialAccountAliasTypeDetail = FinancialAccountAliasTypeDetailFactory.getInstance().create(financialAccountAliasTypePK,
                    financialAccountTypePK, financialAccountAliasTypeName, validationPattern, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            financialAccountAliasType.setActiveDetail(financialAccountAliasTypeDetail);
            financialAccountAliasType.setLastDetail(financialAccountAliasTypeDetail);
            
            sendEventUsingNames(financialAccountAliasTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateFinancialAccountAliasTypeFromValue(FinancialAccountAliasTypeDetailValue financialAccountAliasTypeDetailValue, BasePK updatedBy) {
        updateFinancialAccountAliasTypeFromValue(financialAccountAliasTypeDetailValue, true, updatedBy);
    }
    
    public void deleteFinancialAccountAliasType(FinancialAccountAliasType financialAccountAliasType, BasePK deletedBy) {
        deleteFinancialAccountAliasesByFinancialAccountAliasType(financialAccountAliasType, deletedBy);
        deleteFinancialAccountAliasTypeDescriptionsByFinancialAccountAliasType(financialAccountAliasType, deletedBy);
        
        FinancialAccountAliasTypeDetail financialAccountAliasTypeDetail = financialAccountAliasType.getLastDetailForUpdate();
        financialAccountAliasTypeDetail.setThruTime(session.START_TIME_LONG);
        financialAccountAliasType.setActiveDetail(null);
        financialAccountAliasType.store();
        
        // Check for default, and pick one if necessary
        FinancialAccountType financialAccountType = financialAccountAliasTypeDetail.getFinancialAccountType();
        FinancialAccountAliasType defaultFinancialAccountAliasType = getDefaultFinancialAccountAliasType(financialAccountType);
        if(defaultFinancialAccountAliasType == null) {
            List<FinancialAccountAliasType> financialAccountAliasTypes = getFinancialAccountAliasTypesForUpdate(financialAccountType);
            
            if(!financialAccountAliasTypes.isEmpty()) {
                Iterator<FinancialAccountAliasType> iter = financialAccountAliasTypes.iterator();
                if(iter.hasNext()) {
                    defaultFinancialAccountAliasType = iter.next();
                }
                FinancialAccountAliasTypeDetailValue financialAccountAliasTypeDetailValue = Objects.requireNonNull(defaultFinancialAccountAliasType).getLastDetailForUpdate().getFinancialAccountAliasTypeDetailValue().clone();
                
                financialAccountAliasTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateFinancialAccountAliasTypeFromValue(financialAccountAliasTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(financialAccountAliasType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
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
    
    public FinancialAccountAliasTypeDescription createFinancialAccountAliasTypeDescription(FinancialAccountAliasType financialAccountAliasType,
            Language language, String description, BasePK createdBy) {
        FinancialAccountAliasTypeDescription financialAccountAliasTypeDescription = FinancialAccountAliasTypeDescriptionFactory.getInstance().create(session,
                financialAccountAliasType, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(financialAccountAliasType.getPrimaryKey(), EventTypes.MODIFY.name(), financialAccountAliasTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return financialAccountAliasTypeDescription;
    }
    
    private static final Map<EntityPermission, String> getFinancialAccountAliasTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccountaliastypedescriptions " +
                "WHERE finaatd_finaat_financialaccountaliastypeid = ? AND finaatd_lang_languageid = ? AND finaatd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccountaliastypedescriptions " +
                "WHERE finaatd_finaat_financialaccountaliastypeid = ? AND finaatd_lang_languageid = ? AND finaatd_thrutime = ? " +
                "FOR UPDATE");
        getFinancialAccountAliasTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private FinancialAccountAliasTypeDescription getFinancialAccountAliasTypeDescription(FinancialAccountAliasType financialAccountAliasType, Language language,
            EntityPermission entityPermission) {
        return FinancialAccountAliasTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getFinancialAccountAliasTypeDescriptionQueries,
                financialAccountAliasType, language, Session.MAX_TIME);
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
    
    private static final Map<EntityPermission, String> getFinancialAccountAliasTypeDescriptionsByFinancialAccountAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccountaliastypedescriptions, languages " +
                "WHERE finaatd_finaat_financialaccountaliastypeid = ? AND finaatd_thrutime = ? AND finaatd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccountaliastypedescriptions " +
                "WHERE finaatd_finaat_financialaccountaliastypeid = ? AND finaatd_thrutime = ? " +
                "FOR UPDATE");
        getFinancialAccountAliasTypeDescriptionsByFinancialAccountAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<FinancialAccountAliasTypeDescription> getFinancialAccountAliasTypeDescriptionsByFinancialAccountAliasType(FinancialAccountAliasType financialAccountAliasType,
            EntityPermission entityPermission) {
        return FinancialAccountAliasTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getFinancialAccountAliasTypeDescriptionsByFinancialAccountAliasTypeQueries,
                financialAccountAliasType, Session.MAX_TIME);
    }
    
    public List<FinancialAccountAliasTypeDescription> getFinancialAccountAliasTypeDescriptionsByFinancialAccountAliasType(FinancialAccountAliasType financialAccountAliasType) {
        return getFinancialAccountAliasTypeDescriptionsByFinancialAccountAliasType(financialAccountAliasType, EntityPermission.READ_ONLY);
    }
    
    public List<FinancialAccountAliasTypeDescription> getFinancialAccountAliasTypeDescriptionsByFinancialAccountAliasTypeForUpdate(FinancialAccountAliasType financialAccountAliasType) {
        return getFinancialAccountAliasTypeDescriptionsByFinancialAccountAliasType(financialAccountAliasType, EntityPermission.READ_WRITE);
    }
    
    public String getBestFinancialAccountAliasTypeDescription(FinancialAccountAliasType financialAccountAliasType, Language language) {
        String description;
        FinancialAccountAliasTypeDescription financialAccountAliasTypeDescription = getFinancialAccountAliasTypeDescription(financialAccountAliasType, language);
        
        if(financialAccountAliasTypeDescription == null && !language.getIsDefault()) {
            financialAccountAliasTypeDescription = getFinancialAccountAliasTypeDescription(financialAccountAliasType, getPartyControl().getDefaultLanguage());
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
        return getFinancialTransferCaches(userVisit).getFinancialAccountAliasTypeDescriptionTransferCache().getFinancialAccountAliasTypeDescriptionTransfer(financialAccountAliasTypeDescription);
    }
    
    public List<FinancialAccountAliasTypeDescriptionTransfer> getFinancialAccountAliasTypeDescriptionTransfersByFinancialAccountAliasType(UserVisit userVisit,
            FinancialAccountAliasType financialAccountAliasType) {
        List<FinancialAccountAliasTypeDescription> financialAccountAliasTypeDescriptions = getFinancialAccountAliasTypeDescriptionsByFinancialAccountAliasType(financialAccountAliasType);
        List<FinancialAccountAliasTypeDescriptionTransfer> financialAccountAliasTypeDescriptionTransfers = new ArrayList<>(financialAccountAliasTypeDescriptions.size());
        FinancialAccountAliasTypeDescriptionTransferCache financialAccountAliasTypeDescriptionTransferCache = getFinancialTransferCaches(userVisit).getFinancialAccountAliasTypeDescriptionTransferCache();
        
        financialAccountAliasTypeDescriptions.forEach((financialAccountAliasTypeDescription) ->
                financialAccountAliasTypeDescriptionTransfers.add(financialAccountAliasTypeDescriptionTransferCache.getFinancialAccountAliasTypeDescriptionTransfer(financialAccountAliasTypeDescription))
        );
        
        return financialAccountAliasTypeDescriptionTransfers;
    }
    
    public void updateFinancialAccountAliasTypeDescriptionFromValue(FinancialAccountAliasTypeDescriptionValue financialAccountAliasTypeDescriptionValue,
            BasePK updatedBy) {
        if(financialAccountAliasTypeDescriptionValue.hasBeenModified()) {
            FinancialAccountAliasTypeDescription financialAccountAliasTypeDescription = FinancialAccountAliasTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     financialAccountAliasTypeDescriptionValue.getPrimaryKey());
            
            financialAccountAliasTypeDescription.setThruTime(session.START_TIME_LONG);
            financialAccountAliasTypeDescription.store();
            
            FinancialAccountAliasType financialAccountAliasType = financialAccountAliasTypeDescription.getFinancialAccountAliasType();
            Language language = financialAccountAliasTypeDescription.getLanguage();
            String description = financialAccountAliasTypeDescriptionValue.getDescription();
            
            financialAccountAliasTypeDescription = FinancialAccountAliasTypeDescriptionFactory.getInstance().create(financialAccountAliasType,
                    language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(financialAccountAliasType.getPrimaryKey(), EventTypes.MODIFY.name(), financialAccountAliasTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteFinancialAccountAliasTypeDescription(FinancialAccountAliasTypeDescription financialAccountAliasTypeDescription, BasePK deletedBy) {
        financialAccountAliasTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(financialAccountAliasTypeDescription.getFinancialAccountAliasTypePK(), EventTypes.MODIFY.name(), financialAccountAliasTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteFinancialAccountAliasTypeDescriptionsByFinancialAccountAliasType(FinancialAccountAliasType financialAccountAliasType, BasePK deletedBy) {
        List<FinancialAccountAliasTypeDescription> financialAccountAliasTypeDescriptions = getFinancialAccountAliasTypeDescriptionsByFinancialAccountAliasTypeForUpdate(financialAccountAliasType);
        
        financialAccountAliasTypeDescriptions.forEach((financialAccountAliasTypeDescription) -> 
                deleteFinancialAccountAliasTypeDescription(financialAccountAliasTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Financial Account Roles
    // --------------------------------------------------------------------------------
    
    public FinancialAccountRole createFinancialAccountRoleUsingNames(FinancialAccount financialAccount, Party party,
            PartyContactMechanism partyContactMechanism, String financialAccountRoleTypeName, BasePK createdBy) {
        FinancialAccountRoleType financialAccountRoleType = getFinancialAccountRoleTypeByName(financialAccountRoleTypeName);
        
        return createFinancialAccountRole(financialAccount, party, partyContactMechanism, financialAccountRoleType, createdBy);
    }
    
    public FinancialAccountRole createFinancialAccountRole(FinancialAccount financialAccount, Party party, PartyContactMechanism partyContactMechanism,
            FinancialAccountRoleType financialAccountRoleType, BasePK createdBy) {
        FinancialAccountRole financialAccountRole = FinancialAccountRoleFactory.getInstance().create(financialAccount, party, financialAccountRoleType,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(financialAccount.getPrimaryKey(), EventTypes.MODIFY.name(), financialAccountRole.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return financialAccountRole;
    }
    
    private static final Map<EntityPermission, String> getFinancialAccountRoleQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccountroles " +
                "WHERE fnar_fina_financialaccountid = ? AND fnar_finatyp_financialaccountroletypeid = ? AND fnar_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccountroles " +
                "WHERE fnar_fina_financialaccountid = ? AND fnar_finatyp_financialaccountroletypeid = ? AND fnar_thrutime = ? " +
                "FOR UPDATE");
        getFinancialAccountRoleQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private FinancialAccountRole getFinancialAccountRole(FinancialAccount financialAccount, FinancialAccountRoleType financialAccountRoleType,
            EntityPermission entityPermission) {
        return FinancialAccountRoleFactory.getInstance().getEntityFromQuery(entityPermission, getFinancialAccountRoleQueries,
                financialAccount, financialAccountRoleType, Session.MAX_TIME);
    }
    
     public FinancialAccountRole getFinancialAccountRole(FinancialAccount financialAccount, FinancialAccountRoleType financialAccountRoleType) {
        return getFinancialAccountRole(financialAccount, financialAccountRoleType, EntityPermission.READ_ONLY);
    }
    
    public FinancialAccountRole getFinancialAccountRoleUsingNames(FinancialAccount financialAccount, String financialAccountRoleTypeName) {
        FinancialAccountRoleType financialAccountRoleType = getFinancialAccountRoleTypeByName(financialAccountRoleTypeName);
        
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
    
    private static final Map<EntityPermission, String> getFinancialAccountRolesByFinancialAccountQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccountroles, financialaccountroletypes, parties, partydetails " +
                "WHERE fnar_fina_financialaccountid = ? AND fnar_thrutime = ? " +
                "AND fnar_finatyp_financialaccountroletypeid = finatyp_financialaccountroletypeid " +
                "AND fnar_par_partyid = par_partyid AND par_activedetailid = pardt_partydetailid " +
                "ORDER BY finatyp_sortorder, finatyp_financialaccountroletypename, pardt_partyname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccountroles " +
                "WHERE fnar_fina_financialaccountid = ? AND fnar_thrutime = ? " +
                "FOR UPDATE");
        getFinancialAccountRolesByFinancialAccountQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<FinancialAccountRole> getFinancialAccountRolesByFinancialAccount(FinancialAccount financialAccount, EntityPermission entityPermission) {
        return FinancialAccountRoleFactory.getInstance().getEntitiesFromQuery(entityPermission, getFinancialAccountRolesByFinancialAccountQueries,
                financialAccount, Session.MAX_TIME);
    }
    
    public List<FinancialAccountRole> getFinancialAccountRolesByFinancialAccount(FinancialAccount financialAccount) {
        return getFinancialAccountRolesByFinancialAccount(financialAccount, EntityPermission.READ_ONLY);
    }
    
    public List<FinancialAccountRole> getFinancialAccountRolesByFinancialAccountForUpdate(FinancialAccount financialAccount) {
        return getFinancialAccountRolesByFinancialAccount(financialAccount, EntityPermission.READ_WRITE);
    }
    
    public List<FinancialAccountRoleTransfer> getFinancialAccountRoleTransfers(UserVisit userVisit, List<FinancialAccountRole> financialAccountRoles) {
        List<FinancialAccountRoleTransfer> financialAccountRoleTransfers = new ArrayList<>(financialAccountRoles.size());
        FinancialAccountRoleTransferCache financialAccountRoleTransferCache = getFinancialTransferCaches(userVisit).getFinancialAccountRoleTransferCache();
        
        financialAccountRoles.forEach((financialAccountRole) ->
                financialAccountRoleTransfers.add(financialAccountRoleTransferCache.getFinancialAccountRoleTransfer(financialAccountRole))
        );
        
        return financialAccountRoleTransfers;
    }
    
    public List<FinancialAccountRoleTransfer> getFinancialAccountRoleTransfersByFinancialAccount(UserVisit userVisit, FinancialAccount financialAccount) {
        return getFinancialAccountRoleTransfers(userVisit, getFinancialAccountRolesByFinancialAccount(financialAccount));
    }
    
    public void updateFinancialAccountRoleFromValue(FinancialAccountRoleValue financialAccountRoleValue, BasePK updatedBy) {
        if(financialAccountRoleValue.hasBeenModified()) {
            FinancialAccountRole financialAccountRole = FinancialAccountRoleFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     financialAccountRoleValue.getPrimaryKey());
            
            financialAccountRole.setThruTime(session.START_TIME_LONG);
            financialAccountRole.store();
            
            FinancialAccountPK financialAccountPK = financialAccountRole.getFinancialAccountPK(); // Not updated
            PartyPK partyPK = financialAccountRole.getPartyPK(); // Not updated
            FinancialAccountRoleTypePK financialAccountRoleTypePK = financialAccountRole.getFinancialAccountRoleTypePK(); // Not updated
            
            financialAccountRole = FinancialAccountRoleFactory.getInstance().create(financialAccountPK, partyPK, financialAccountRoleTypePK,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(financialAccountPK, EventTypes.MODIFY.name(), financialAccountRole.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteFinancialAccountRole(FinancialAccountRole financialAccountRole, BasePK deletedBy) {
        financialAccountRole.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(financialAccountRole.getFinancialAccountPK(), EventTypes.MODIFY.name(), financialAccountRole.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteFinancialAccountRolesByFinancialAccount(FinancialAccount financialAccount, BasePK deletedBy) {
        getFinancialAccountRolesByFinancialAccountForUpdate(financialAccount).stream().forEach((financialAccountRole) -> {
            deleteFinancialAccountRole(financialAccountRole, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Financial Accounts
    // --------------------------------------------------------------------------------
    
    public FinancialAccount createFinancialAccount(FinancialAccountType financialAccountType, String financialAccountName, Currency currency,
            GlAccount glAccount, String reference, String description, BasePK createdBy) {
        FinancialAccount financialAccount = FinancialAccountFactory.getInstance().create();
        FinancialAccountDetail financialAccountDetail = FinancialAccountDetailFactory.getInstance().create(financialAccount, financialAccountType,
                financialAccountName, currency, glAccount, reference, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        financialAccount = FinancialAccountFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, financialAccount.getPrimaryKey());
        financialAccount.setActiveDetail(financialAccountDetail);
        financialAccount.setLastDetail(financialAccountDetail);
        financialAccount.store();
        
        sendEventUsingNames(financialAccount.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        createFinancialAccountStatus(financialAccount);
        
        return financialAccount;
    }
    
    private static final Map<EntityPermission, String> getFinancialAccountByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccounts, financialaccountdetails " +
                "WHERE fina_activedetailid = finadt_financialaccountdetailid AND finadt_fnatyp_financialaccounttypeid = ? " +
                "AND btchdt_financialAccountname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccounts, financialaccountdetails " +
                "WHERE fina_activedetailid = finadt_financialaccountdetailid AND finadt_fnatyp_financialaccounttypeid = ? " +
                "AND btchdt_financialAccountname = ? " +
                "FOR UPDATE");
        getFinancialAccountByNameQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private FinancialAccount getFinancialAccountByName(FinancialAccountType financialAccountType, String financialAccountName,
            EntityPermission entityPermission) {
        return FinancialAccountFactory.getInstance().getEntityFromQuery(entityPermission, getFinancialAccountByNameQueries,
                financialAccountType, financialAccountName);
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
    
    private static final Map<EntityPermission, String> getFinancialAccountsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccounts, financialaccountdetails " +
                "WHERE fina_activedetailid = finadt_financialaccountdetailid AND finadt_fnatyp_financialaccounttypeid = ? " +
                "ORDER BY finadt_financialaccountname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccounts, financialaccountdetails " +
                "WHERE fina_activedetailid = finadt_financialaccountdetailid AND finadt_fnatyp_financialaccounttypeid = ? " +
                "FOR UPDATE");
        getFinancialAccountsQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<FinancialAccount> getFinancialAccounts(FinancialAccountType financialAccountType, EntityPermission entityPermission) {
        return FinancialAccountFactory.getInstance().getEntitiesFromQuery(entityPermission, getFinancialAccountsQueries,
                financialAccountType);
    }
    
    public List<FinancialAccount> getFinancialAccounts(FinancialAccountType financialAccountType) {
        return getFinancialAccounts(financialAccountType, EntityPermission.READ_ONLY);
    }
    
    public List<FinancialAccount> getFinancialAccountsForUpdate(FinancialAccountType financialAccountType) {
        return getFinancialAccounts(financialAccountType, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getFinancialAccountsByGlAccountQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccounts, financialaccountdetails, financialaccounttypes, financialaccounttypedetails " +
                "WHERE fina_activedetailid = finadt_financialaccountdetailid AND finadt_gla_glaccountid = ? " +
                "finadt_fnatyp_financialaccounttypeid = fnatyp_financialaccounttypeid AND fnatyp_lastdetailid = fnatypdt_financialaccounttypedetailid " +
                "ORDER BY fnatypdt_sortorder, fnatypdt_financialaccounttypename, finadt_financialaccountname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccounts, financialaccountdetails " +
                "WHERE fina_activedetailid = finadt_financialaccountdetailid AND finadt_gla_glaccountid = ? " +
                "FOR UPDATE");
        getFinancialAccountsByGlAccountQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<FinancialAccount> getFinancialAccountsByGlAccount(GlAccount glAccount, EntityPermission entityPermission) {
        return FinancialAccountFactory.getInstance().getEntitiesFromQuery(entityPermission, getFinancialAccountsByGlAccountQueries,
                glAccount);
    }
    
    public List<FinancialAccount> getFinancialAccountsByGlAccount(GlAccount glAccount) {
        return getFinancialAccountsByGlAccount(glAccount, EntityPermission.READ_ONLY);
    }
    
    public List<FinancialAccount> getFinancialAccountsByGlAccountForUpdate(GlAccount glAccount) {
        return getFinancialAccountsByGlAccount(glAccount, EntityPermission.READ_WRITE);
    }
    
    public FinancialAccountTransfer getFinancialAccountTransfer(UserVisit userVisit, FinancialAccount financialAccount) {
        return getFinancialTransferCaches(userVisit).getFinancialAccountTransferCache().getFinancialAccountTransfer(financialAccount);
    }
    
    public List<FinancialAccountTransfer> getFinancialAccountTransfers(UserVisit userVisit, FinancialAccountType financialAccountType) {
        List<FinancialAccount> financialAccounts = getFinancialAccounts(financialAccountType);
        List<FinancialAccountTransfer> financialAccountTransfers = new ArrayList<>(financialAccounts.size());
        FinancialAccountTransferCache financialAccountTransferCache = getFinancialTransferCaches(userVisit).getFinancialAccountTransferCache();
        
        financialAccounts.forEach((financialAccount) ->
                financialAccountTransfers.add(financialAccountTransferCache.getFinancialAccountTransfer(financialAccount))
        );
        
        return financialAccountTransfers;
    }
    
    public void updateFinancialAccountFromValue(FinancialAccountDetailValue financialAccountDetailValue, BasePK updatedBy) {
        if(financialAccountDetailValue.hasBeenModified()) {
            FinancialAccount financialAccount = FinancialAccountFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    financialAccountDetailValue.getFinancialAccountPK());
            FinancialAccountDetail financialAccountDetail = financialAccount.getActiveDetailForUpdate();
            
            financialAccountDetail.setThruTime(session.START_TIME_LONG);
            financialAccountDetail.store();
            
            FinancialAccountPK financialAccountPK = financialAccountDetail.getFinancialAccountPK();
            FinancialAccountType financialAccountType = financialAccountDetail.getFinancialAccountType();
            FinancialAccountTypePK financialAccountTypePK = financialAccountType.getPrimaryKey();
            String financialAccountName = financialAccountDetailValue.getFinancialAccountName();
            CurrencyPK currencyPK = financialAccountDetailValue.getCurrencyPK();
            GlAccountPK glAccountPK = financialAccountDetailValue.getGlAccountPK();
            String reference = financialAccountDetailValue.getReference();
            String description = financialAccountDetailValue.getDescription();
            
            financialAccountDetail = FinancialAccountDetailFactory.getInstance().create(financialAccountPK, financialAccountTypePK,
                    financialAccountName, currencyPK, glAccountPK, reference, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            financialAccount.setActiveDetail(financialAccountDetail);
            financialAccount.setLastDetail(financialAccountDetail);
            
            sendEventUsingNames(financialAccountPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void deleteFinancialAccount(FinancialAccount financialAccount, BasePK deletedBy) {
        deleteFinancialAccountRolesByFinancialAccount(financialAccount, deletedBy);
        deleteFinancialAccountAliasesByFinancialAccount(financialAccount, deletedBy);
        deleteFinancialAccountTransactionsByFinancialAccount(financialAccount, deletedBy);
        removeFinancialAccountStatusByFinancialAccount(financialAccount);
        
        FinancialAccountDetail financialAccountDetail = financialAccount.getLastDetailForUpdate();
        financialAccountDetail.setThruTime(session.START_TIME_LONG);
        financialAccount.setActiveDetail(null);
        financialAccount.store();
        
        sendEventUsingNames(financialAccount.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
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
    
    public FinancialAccountStatus createFinancialAccountStatus(FinancialAccount financialAccount) {
        return FinancialAccountStatusFactory.getInstance().create(financialAccount, Long.valueOf(0), Long.valueOf(0));
    }
    
    private static final Map<EntityPermission, String> getFinancialAccountStatusQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccountstatuses " +
                "WHERE finast_fina_financialaccountid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccountstatuses " +
                "WHERE finast_fina_financialaccountid = ? " +
                "FOR UPDATE");
        getFinancialAccountStatusQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private FinancialAccountStatus getFinancialAccountStatus(FinancialAccount financialAccount, EntityPermission entityPermission) {
        return FinancialAccountStatusFactory.getInstance().getEntityFromQuery(entityPermission, getFinancialAccountStatusQueries,
                financialAccount);
    }
    
    public FinancialAccountStatus getFinancialAccountStatus(FinancialAccount financialAccount) {
        return getFinancialAccountStatus(financialAccount, EntityPermission.READ_ONLY);
    }
    
    public FinancialAccountStatus getFinancialAccountStatusForUpdate(FinancialAccount financialAccount) {
        return getFinancialAccountStatus(financialAccount, EntityPermission.READ_WRITE);
    }
    
    public void removeFinancialAccountStatusByFinancialAccount(FinancialAccount financialAccount) {
        FinancialAccountStatus financialAccountStatus = getFinancialAccountStatusForUpdate(financialAccount);
        
        if(financialAccountStatus != null) {
            financialAccountStatus.remove();
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Financial Account Aliases
    // --------------------------------------------------------------------------------
    
    public FinancialAccountAlias createFinancialAccountAlias(FinancialAccount financialAccount, FinancialAccountAliasType financialAccountAliasType,
            String alias, BasePK createdBy) {
        FinancialAccountAlias financialAccountAlias = FinancialAccountAliasFactory.getInstance().create(financialAccount, financialAccountAliasType,
                alias, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(financialAccount.getPrimaryKey(), EventTypes.MODIFY.name(), financialAccountAlias.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return financialAccountAlias;
    }
    
    private static final Map<EntityPermission, String> getFinancialAccountAliasQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccountaliases " +
                "WHERE finaal_finaa_financialAccountid = ? AND finaal_finaat_financialaccountaliastypeid = ? AND finaal_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccountaliases " +
                "WHERE finaal_finaa_financialAccountid = ? AND finaal_finaat_financialaccountaliastypeid = ? AND finaal_thrutime = ? " +
                "FOR UPDATE");
        getFinancialAccountAliasQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private FinancialAccountAlias getFinancialAccountAlias(FinancialAccount financialAccount, FinancialAccountAliasType financialAccountAliasType,
            EntityPermission entityPermission) {
        return FinancialAccountAliasFactory.getInstance().getEntityFromQuery(entityPermission, getFinancialAccountAliasQueries,
                financialAccount, financialAccountAliasType, Session.MAX_TIME);
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
    
    private static final Map<EntityPermission, String> getFinancialAccountAliasesByFinancialAccountQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccountaliases, financialaccountaliastypes, financialaccountaliastypedetails " +
                "WHERE finaal_finaa_financialAccountid = ? AND finaal_thrutime = ? " +
                "AND finaal_finaat_financialaccountaliastypeid = finaat_financialaccountaliastypeid AND finaat_lastdetailid = finaatdt_financialaccountaliastypedetailid" +
                "ORDER BY finaatdt_sortorder, finaatdt_financialaccountaliastypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccountaliases " +
                "WHERE finaal_finaa_financialAccountid = ? AND finaal_thrutime = ? " +
                "FOR UPDATE");
        getFinancialAccountAliasesByFinancialAccountQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<FinancialAccountAlias> getFinancialAccountAliasesByFinancialAccount(FinancialAccount financialAccount, EntityPermission entityPermission) {
        return FinancialAccountAliasFactory.getInstance().getEntitiesFromQuery(entityPermission, getFinancialAccountAliasesByFinancialAccountQueries,
                financialAccount, Session.MAX_TIME);
    }
    
    public List<FinancialAccountAlias> getFinancialAccountAliasesByFinancialAccount(FinancialAccount financialAccount) {
        return getFinancialAccountAliasesByFinancialAccount(financialAccount, EntityPermission.READ_ONLY);
    }
    
    public List<FinancialAccountAlias> getFinancialAccountAliasesByFinancialAccountForUpdate(FinancialAccount financialAccount) {
        return getFinancialAccountAliasesByFinancialAccount(financialAccount, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getFinancialAccountAliasesByFinancialAccountAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccountaliases, financialaccounts, financialaccountdetails " +
                "WHERE finaal_finaat_financialaccountaliastypeid = ? AND finaal_thrutime = ? " +
                "AND finaal_finaa_financialAccountid = finaa_financialAccountid AND finaa_lastdetailid = finaadt_financialAccountdetailid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccountaliases " +
                "WHERE finaal_finaat_financialaccountaliastypeid = ? AND finaal_thrutime = ? " +
                "FOR UPDATE");
        getFinancialAccountAliasesByFinancialAccountAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<FinancialAccountAlias> getFinancialAccountAliasesByFinancialAccountAliasType(FinancialAccountAliasType financialAccountAliasType,
            EntityPermission entityPermission) {
        return FinancialAccountAliasFactory.getInstance().getEntitiesFromQuery(entityPermission, getFinancialAccountAliasesByFinancialAccountAliasTypeQueries,
                financialAccountAliasType, Session.MAX_TIME);
    }
    
    public List<FinancialAccountAlias> getFinancialAccountAliasesByFinancialAccountAliasType(FinancialAccountAliasType financialAccountAliasType) {
        return getFinancialAccountAliasesByFinancialAccountAliasType(financialAccountAliasType, EntityPermission.READ_ONLY);
    }
    
    public List<FinancialAccountAlias> getFinancialAccountAliasesByFinancialAccountAliasTypeForUpdate(FinancialAccountAliasType financialAccountAliasType) {
        return getFinancialAccountAliasesByFinancialAccountAliasType(financialAccountAliasType, EntityPermission.READ_WRITE);
    }
    
    public FinancialAccountAliasTransfer getFinancialAccountAliasTransfer(UserVisit userVisit, FinancialAccountAlias financialAccountAlias) {
        return getFinancialTransferCaches(userVisit).getFinancialAccountAliasTransferCache().getFinancialAccountAliasTransfer(financialAccountAlias);
    }
    
    public List<FinancialAccountAliasTransfer> getFinancialAccountAliasTransfersByFinancialAccount(UserVisit userVisit, FinancialAccount financialAccount) {
        List<FinancialAccountAlias> financialaccountaliases = getFinancialAccountAliasesByFinancialAccount(financialAccount);
        List<FinancialAccountAliasTransfer> financialAccountAliasTransfers = new ArrayList<>(financialaccountaliases.size());
        FinancialAccountAliasTransferCache financialAccountAliasTransferCache = getFinancialTransferCaches(userVisit).getFinancialAccountAliasTransferCache();
        
        financialaccountaliases.forEach((financialAccountAlias) ->
                financialAccountAliasTransfers.add(financialAccountAliasTransferCache.getFinancialAccountAliasTransfer(financialAccountAlias))
        );
        
        return financialAccountAliasTransfers;
    }
    
    public void updateFinancialAccountAliasFromValue(FinancialAccountAliasValue financialAccountAliasValue, BasePK updatedBy) {
        if(financialAccountAliasValue.hasBeenModified()) {
            FinancialAccountAlias financialAccountAlias = FinancialAccountAliasFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    financialAccountAliasValue.getPrimaryKey());
            
            financialAccountAlias.setThruTime(session.START_TIME_LONG);
            financialAccountAlias.store();
            
            FinancialAccountPK financialAccountPK = financialAccountAlias.getFinancialAccountPK();
            FinancialAccountAliasTypePK financialAccountAliasTypePK = financialAccountAlias.getFinancialAccountAliasTypePK();
            String alias  = financialAccountAliasValue.getAlias();
            
            financialAccountAlias = FinancialAccountAliasFactory.getInstance().create(financialAccountPK, financialAccountAliasTypePK, alias,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(financialAccountPK, EventTypes.MODIFY.name(), financialAccountAlias.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteFinancialAccountAlias(FinancialAccountAlias financialAccountAlias, BasePK deletedBy) {
        financialAccountAlias.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(financialAccountAlias.getFinancialAccountPK(), EventTypes.MODIFY.name(), financialAccountAlias.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteFinancialAccountAliasesByFinancialAccountAliasType(FinancialAccountAliasType financialAccountAliasType, BasePK deletedBy) {
        List<FinancialAccountAlias> financialaccountaliases = getFinancialAccountAliasesByFinancialAccountAliasTypeForUpdate(financialAccountAliasType);
        
        financialaccountaliases.forEach((financialAccountAlias) -> 
                deleteFinancialAccountAlias(financialAccountAlias, deletedBy)
        );
    }
    
    public void deleteFinancialAccountAliasesByFinancialAccount(FinancialAccount financialAccount, BasePK deletedBy) {
        List<FinancialAccountAlias> financialaccountaliases = getFinancialAccountAliasesByFinancialAccountForUpdate(financialAccount);
        
        financialaccountaliases.forEach((financialAccountAlias) -> 
                deleteFinancialAccountAlias(financialAccountAlias, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Financial AccountTransactions
    // --------------------------------------------------------------------------------
    
    public FinancialAccountTransaction createFinancialAccountTransaction(String financialAccountTransactionName, FinancialAccount financialAccount,
            FinancialAccountTransactionType financialAccountTransactionType, Long amount, String comment, BasePK createdBy) {
        FinancialAccountTransaction financialAccountTransaction = FinancialAccountTransactionFactory.getInstance().create();
        FinancialAccountTransactionDetail financialAccountTransactionDetail = FinancialAccountTransactionDetailFactory.getInstance().create(session,
                financialAccountTransaction, financialAccountTransactionName, financialAccount, financialAccountTransactionType, amount, comment,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        financialAccountTransaction = FinancialAccountTransactionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, financialAccountTransaction.getPrimaryKey());
        financialAccountTransaction.setActiveDetail(financialAccountTransactionDetail);
        financialAccountTransaction.setLastDetail(financialAccountTransactionDetail);
        financialAccountTransaction.store();
        
        sendEventUsingNames(financialAccountTransaction.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return financialAccountTransaction;
    }
    
    private static final Map<EntityPermission, String> getFinancialAccountTransactionByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccounttransactions, financialaccounttransactiondetails " +
                "WHERE finatrx_activedetailid = finatrxdt_financialaccounttransactiondetailid AND finatrxdt_financialaccounttransactionname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccounttransactions, financialaccounttransactiondetails " +
                "WHERE finatrx_activedetailid = finatrxdt_financialaccounttransactiondetailid AND finatrxdt_financialaccounttransactionname = ? " +
                "FOR UPDATE");
        getFinancialAccountTransactionByNameQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private FinancialAccountTransaction getFinancialAccountTransactionByName(String financialAccountTransactionName, EntityPermission entityPermission) {
        return FinancialAccountTransactionFactory.getInstance().getEntityFromQuery(entityPermission, getFinancialAccountTransactionByNameQueries,
                financialAccountTransactionName);
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
    
    private static final Map<EntityPermission, String> getFinancialAccountTransactionsByFinancialAccountQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccounttransactions, financialaccounttransactiondetails " +
                "WHERE finatrx_activedetailid = finatrxdt_financialaccounttransactiondetailid AND finatrxdt_fnatrxtyp_financialaccounttransactiontypeid = ? " +
                "ORDER BY finatrxdt_financialaccounttransactionname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccounttransactions, financialaccounttransactiondetails " +
                "WHERE finatrx_activedetailid = finatrxdt_financialaccounttransactiondetailid AND finatrxdt_fnatrxtyp_financialaccounttransactiontypeid = ? " +
                "FOR UPDATE");
        getFinancialAccountTransactionsByFinancialAccountQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<FinancialAccountTransaction> getFinancialAccountTransactionsByFinancialAccount(FinancialAccount financialAccount, EntityPermission entityPermission) {
        return FinancialAccountTransactionFactory.getInstance().getEntitiesFromQuery(entityPermission, getFinancialAccountTransactionsByFinancialAccountQueries,
                financialAccount);
    }
    
    public List<FinancialAccountTransaction> getFinancialAccountTransactionsByFinancialAccount(FinancialAccount financialAccount) {
        return getFinancialAccountTransactionsByFinancialAccount(financialAccount, EntityPermission.READ_ONLY);
    }
    
    public List<FinancialAccountTransaction> getFinancialAccountTransactionsByFinancialAccountForUpdate(FinancialAccount financialAccount) {
        return getFinancialAccountTransactionsByFinancialAccount(financialAccount, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getFinancialAccountTransactionsByFinancialAccountTransactionTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM financialaccounttransactions, financialaccounttransactiondetails " +
                "WHERE finatrx_activedetailid = finatrxdt_financialaccounttransactiondetailid AND finatrxdt_fnatrxtyp_financialaccounttransactiontypeid = ? " +
                "ORDER BY finatrxdt_financialaccounttransactionname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM financialaccounttransactions, financialaccounttransactiondetails " +
                "WHERE finatrx_activedetailid = finatrxdt_financialaccounttransactiondetailid AND finatrxdt_fnatrxtyp_financialaccounttransactiontypeid = ? " +
                "FOR UPDATE");
        getFinancialAccountTransactionsByFinancialAccountTransactionTypeQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<FinancialAccountTransaction> getFinancialAccountTransactionsByFinancialAccountTransactionType(FinancialAccountTransactionType financialAccountTransactionType, EntityPermission entityPermission) {
        return FinancialAccountTransactionFactory.getInstance().getEntitiesFromQuery(entityPermission, getFinancialAccountTransactionsByFinancialAccountTransactionTypeQueries,
                financialAccountTransactionType);
    }
    
    public List<FinancialAccountTransaction> getFinancialAccountTransactionsByFinancialAccountTransactionType(FinancialAccountTransactionType financialAccountTransactionType) {
        return getFinancialAccountTransactionsByFinancialAccountTransactionType(financialAccountTransactionType, EntityPermission.READ_ONLY);
    }
    
    public List<FinancialAccountTransaction> getFinancialAccountTransactionsByFinancialAccountTransactionTypeForUpdate(FinancialAccountTransactionType financialAccountTransactionType) {
        return getFinancialAccountTransactionsByFinancialAccountTransactionType(financialAccountTransactionType, EntityPermission.READ_WRITE);
    }
    
    public FinancialAccountTransactionTransfer getFinancialAccountTransactionTransfer(UserVisit userVisit, FinancialAccountTransaction financialAccountTransaction) {
        return getFinancialTransferCaches(userVisit).getFinancialAccountTransactionTransferCache().getFinancialAccountTransactionTransfer(financialAccountTransaction);
    }
    
    public List<FinancialAccountTransactionTransfer> getFinancialAccountTransactionTransfersByFinancialAccount(UserVisit userVisit, FinancialAccount financialAccount) {
        List<FinancialAccountTransaction> financialAccountTransactions = getFinancialAccountTransactionsByFinancialAccount(financialAccount);
        List<FinancialAccountTransactionTransfer> financialAccountTransactionTransfers = new ArrayList<>(financialAccountTransactions.size());
        FinancialAccountTransactionTransferCache financialAccountTransactionTransferCache = getFinancialTransferCaches(userVisit).getFinancialAccountTransactionTransferCache();
        
        financialAccountTransactions.forEach((financialAccountTransaction) ->
                financialAccountTransactionTransfers.add(financialAccountTransactionTransferCache.getFinancialAccountTransactionTransfer(financialAccountTransaction))
        );
        
        return financialAccountTransactionTransfers;
    }
    
    public void updateFinancialAccountTransactionFromValue(FinancialAccountTransactionDetailValue financialAccountTransactionDetailValue, BasePK updatedBy) {
        if(financialAccountTransactionDetailValue.hasBeenModified()) {
            FinancialAccountTransaction financialAccountTransaction = FinancialAccountTransactionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    financialAccountTransactionDetailValue.getFinancialAccountTransactionPK());
            FinancialAccountTransactionDetail financialAccountTransactionDetail = financialAccountTransaction.getActiveDetailForUpdate();
            
            financialAccountTransactionDetail.setThruTime(session.START_TIME_LONG);
            financialAccountTransactionDetail.store();
            
            FinancialAccountTransactionPK financialAccountTransactionPK = financialAccountTransactionDetail.getFinancialAccountTransactionPK();
            String financialAccountTransactionName = financialAccountTransactionDetailValue.getFinancialAccountTransactionName();
            FinancialAccountPK financialAccountPK = financialAccountTransactionDetailValue.getFinancialAccountPK();
            FinancialAccountTransactionTypePK financialAccountTransactionTypePK = financialAccountTransactionDetailValue.getFinancialAccountTransactionTypePK();
            Long amount = financialAccountTransactionDetailValue.getAmount();
            String comment = financialAccountTransactionDetailValue.getComment();
            
            financialAccountTransactionDetail = FinancialAccountTransactionDetailFactory.getInstance().create(financialAccountTransactionPK,
                    financialAccountTransactionName, financialAccountPK, financialAccountTransactionTypePK, amount, comment, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            financialAccountTransaction.setActiveDetail(financialAccountTransactionDetail);
            financialAccountTransaction.setLastDetail(financialAccountTransactionDetail);
            
            sendEventUsingNames(financialAccountTransactionPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void deleteFinancialAccountTransaction(FinancialAccountTransaction financialAccountTransaction, BasePK deletedBy) {
        FinancialAccountTransactionDetail financialAccountTransactionDetail = financialAccountTransaction.getLastDetailForUpdate();
        financialAccountTransactionDetail.setThruTime(session.START_TIME_LONG);
        financialAccountTransaction.setActiveDetail(null);
        financialAccountTransaction.store();
        
        sendEventUsingNames(financialAccountTransaction.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
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

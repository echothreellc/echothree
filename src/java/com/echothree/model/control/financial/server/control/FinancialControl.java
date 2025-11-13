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
import com.echothree.model.control.financial.server.transfer.FinancialTransferCaches;
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
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class FinancialControl
        extends BaseModelControl {
    
    /** Creates a new instance of FinancialControl */
    protected FinancialControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Financial Transfer Caches
    // --------------------------------------------------------------------------------
    
    private FinancialTransferCaches financialTransferCaches;
    
    public FinancialTransferCaches getFinancialTransferCaches() {
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
        return getFinancialTransferCaches().getFinancialAccountRoleTypeTransferCache().getFinancialAccountRoleTypeTransfer(userVisit, financialAccountRoleType);
    }
    
    private List<FinancialAccountRoleTypeTransfer> getFinancialAccountRoleTypeTransfers(final UserVisit userVisit, final List<FinancialAccountRoleType> financialAccountRoleTypes) {
        List<FinancialAccountRoleTypeTransfer> financialAccountRoleTypeTransfers = new ArrayList<>(financialAccountRoleTypes.size());
        var financialAccountRoleTypeTransferCache = getFinancialTransferCaches(userVisit).getFinancialAccountRoleTypeTransferCache();
        
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

        var financialAccountType = FinancialAccountTypeFactory.getInstance().create();
        var financialAccountTypeDetail = FinancialAccountTypeDetailFactory.getInstance().create(financialAccountType,
                financialAccountTypeName, parentFinancialAccountType, defaultGlAccount, financialAccountSequenceType, financialAccountTransactionSequenceType,
                financialAccountWorkflow, financialAccountWorkflowEntrance, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        financialAccountType = FinancialAccountTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                financialAccountType.getPrimaryKey());
        financialAccountType.setActiveDetail(financialAccountTypeDetail);
        financialAccountType.setLastDetail(financialAccountTypeDetail);
        financialAccountType.store();
        
        sendEvent(financialAccountType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
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
        return getFinancialTransferCaches().getFinancialAccountTypeTransferCache().getFinancialAccountTypeTransfer(userVisit, financialAccountType);
    }
    
    public List<FinancialAccountTypeTransfer> getFinancialAccountTypeTransfers(UserVisit userVisit) {
        var financialAccountTypes = getFinancialAccountTypes();
        List<FinancialAccountTypeTransfer> financialAccountTypeTransfers = new ArrayList<>(financialAccountTypes.size());
        var financialAccountTypeTransferCache = getFinancialTransferCaches(userVisit).getFinancialAccountTypeTransferCache();
        
        financialAccountTypes.forEach((financialAccountType) ->
                financialAccountTypeTransfers.add(financialAccountTypeTransferCache.getFinancialAccountTypeTransfer(financialAccountType))
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
            var financialAccountType = FinancialAccountTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     financialAccountTypeDetailValue.getFinancialAccountTypePK());
            var financialAccountTypeDetail = financialAccountType.getActiveDetailForUpdate();
            
            financialAccountTypeDetail.setThruTime(session.START_TIME_LONG);
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
            
            financialAccountTypeDetail = FinancialAccountTypeDetailFactory.getInstance().create(financialAccountTypePK, financialAccountTypeName,
                    parentFinancialAccountTypePK, defaultGlAccountPK, financialAccountSequenceTypePK, financialAccountTransactionSequenceTypePK,
                    financialAccountWorkflowPK, financialAccountWorkflowEntrancePK, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
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
        
        financialAccountTypeDetail.setThruTime(session.START_TIME_LONG);
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
    
    public FinancialAccountTypeDescription createFinancialAccountTypeDescription(FinancialAccountType financialAccountType, Language language, String description, BasePK createdBy) {
        var financialAccountTypeDescription = FinancialAccountTypeDescriptionFactory.getInstance().create(financialAccountType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(financialAccountType.getPrimaryKey(), EventTypes.MODIFY, financialAccountTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
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
        return getFinancialTransferCaches().getFinancialAccountTypeDescriptionTransferCache().getFinancialAccountTypeDescriptionTransfer(userVisit, financialAccountTypeDescription);
    }
    
    public List<FinancialAccountTypeDescriptionTransfer> getFinancialAccountTypeDescriptionTransfersByFinancialAccountType(UserVisit userVisit, FinancialAccountType financialAccountType) {
        var financialAccountTypeDescriptions = getFinancialAccountTypeDescriptionsByFinancialAccountType(financialAccountType);
        List<FinancialAccountTypeDescriptionTransfer> financialAccountTypeDescriptionTransfers = new ArrayList<>(financialAccountTypeDescriptions.size());
        var financialAccountTypeDescriptionTransferCache = getFinancialTransferCaches(userVisit).getFinancialAccountTypeDescriptionTransferCache();
        
        financialAccountTypeDescriptions.forEach((financialAccountTypeDescription) ->
                financialAccountTypeDescriptionTransfers.add(financialAccountTypeDescriptionTransferCache.getFinancialAccountTypeDescriptionTransfer(financialAccountTypeDescription))
        );
        
        return financialAccountTypeDescriptionTransfers;
    }
    
    public void updateFinancialAccountTypeDescriptionFromValue(FinancialAccountTypeDescriptionValue financialAccountTypeDescriptionValue, BasePK updatedBy) {
        if(financialAccountTypeDescriptionValue.hasBeenModified()) {
            var financialAccountTypeDescription = FinancialAccountTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    financialAccountTypeDescriptionValue.getPrimaryKey());
            
            financialAccountTypeDescription.setThruTime(session.START_TIME_LONG);
            financialAccountTypeDescription.store();

            var financialAccountType = financialAccountTypeDescription.getFinancialAccountType();
            var language = financialAccountTypeDescription.getLanguage();
            var description = financialAccountTypeDescriptionValue.getDescription();
            
            financialAccountTypeDescription = FinancialAccountTypeDescriptionFactory.getInstance().create(financialAccountType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(financialAccountType.getPrimaryKey(), EventTypes.MODIFY, financialAccountTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteFinancialAccountTypeDescription(FinancialAccountTypeDescription financialAccountTypeDescription, BasePK deletedBy) {
        financialAccountTypeDescription.setThruTime(session.START_TIME_LONG);
        
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

        var financialAccountTransactionType = FinancialAccountTransactionTypeFactory.getInstance().create();
        var financialAccountTransactionTypeDetail = FinancialAccountTransactionTypeDetailFactory.getInstance().create(session,
                financialAccountTransactionType, financialAccountType, financialAccountTransactionTypeName, parentFinancialAccountTransactionType, glAccount,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        financialAccountTransactionType = FinancialAccountTransactionTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                financialAccountTransactionType.getPrimaryKey());
        financialAccountTransactionType.setActiveDetail(financialAccountTransactionTypeDetail);
        financialAccountTransactionType.setLastDetail(financialAccountTransactionTypeDetail);
        financialAccountTransactionType.store();
        
        sendEvent(financialAccountTransactionType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
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
        return getFinancialTransferCaches().getFinancialAccountTransactionTypeTransferCache().getFinancialAccountTransactionTypeTransfer(userVisit, financialAccountTransactionType);
    }
    
    public List<FinancialAccountTransactionTypeTransfer> getFinancialAccountTransactionTypeTransfers(UserVisit userVisit,
            FinancialAccountType financialAccountType) {
        var financialAccountTransactionTypes = getFinancialAccountTransactionTypes(financialAccountType);
        List<FinancialAccountTransactionTypeTransfer> financialAccountTransactionTypeTransfers = new ArrayList<>(financialAccountTransactionTypes.size());
        var financialAccountTransactionTypeTransferCache = getFinancialTransferCaches(userVisit).getFinancialAccountTransactionTypeTransferCache();
        
        financialAccountTransactionTypes.forEach((financialAccountTransactionType) ->
                financialAccountTransactionTypeTransfers.add(financialAccountTransactionTypeTransferCache.getFinancialAccountTransactionTypeTransfer(financialAccountTransactionType))
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
            var financialAccountTransactionType = FinancialAccountTransactionTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     financialAccountTransactionTypeDetailValue.getFinancialAccountTransactionTypePK());
            var financialAccountTransactionTypeDetail = financialAccountTransactionType.getActiveDetailForUpdate();
            
            financialAccountTransactionTypeDetail.setThruTime(session.START_TIME_LONG);
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
            
            financialAccountTransactionTypeDetail = FinancialAccountTransactionTypeDetailFactory.getInstance().create(session,
                    financialAccountTransactionTypePK, financialAccountTypePK, financialAccountTransactionTypeName, parentFinancialAccountTransactionTypePK,
                    glAccountPK, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
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
        
        financialAccountTransactionTypeDetail.setThruTime(session.START_TIME_LONG);
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
    
    public FinancialAccountTransactionTypeDescription createFinancialAccountTransactionTypeDescription(FinancialAccountTransactionType financialAccountTransactionType,
            Language language, String description, BasePK createdBy) {
        var financialAccountTransactionTypeDescription = FinancialAccountTransactionTypeDescriptionFactory.getInstance().create(session,
                financialAccountTransactionType, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(financialAccountTransactionType.getPrimaryKey(), EventTypes.MODIFY, financialAccountTransactionTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
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
        return getFinancialTransferCaches().getFinancialAccountTransactionTypeDescriptionTransferCache().getFinancialAccountTransactionTypeDescriptionTransfer(userVisit, financialAccountTransactionTypeDescription);
    }
    
    public List<FinancialAccountTransactionTypeDescriptionTransfer> getFinancialAccountTransactionTypeDescriptionTransfersByFinancialAccountTransactionType(UserVisit userVisit,
            FinancialAccountTransactionType financialAccountTransactionType) {
        var financialAccountTransactionTypeDescriptions = getFinancialAccountTransactionTypeDescriptionsByFinancialAccountTransactionType(financialAccountTransactionType);
        List<FinancialAccountTransactionTypeDescriptionTransfer> financialAccountTransactionTypeDescriptionTransfers = new ArrayList<>(financialAccountTransactionTypeDescriptions.size());
        var financialAccountTransactionTypeDescriptionTransferCache = getFinancialTransferCaches(userVisit).getFinancialAccountTransactionTypeDescriptionTransferCache();
        
        financialAccountTransactionTypeDescriptions.forEach((financialAccountTransactionTypeDescription) ->
                financialAccountTransactionTypeDescriptionTransfers.add(financialAccountTransactionTypeDescriptionTransferCache.getFinancialAccountTransactionTypeDescriptionTransfer(financialAccountTransactionTypeDescription))
        );
        
        return financialAccountTransactionTypeDescriptionTransfers;
    }
    
    public void updateFinancialAccountTransactionTypeDescriptionFromValue(FinancialAccountTransactionTypeDescriptionValue financialAccountTransactionTypeDescriptionValue,
            BasePK updatedBy) {
        if(financialAccountTransactionTypeDescriptionValue.hasBeenModified()) {
            var financialAccountTransactionTypeDescription = FinancialAccountTransactionTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    financialAccountTransactionTypeDescriptionValue.getPrimaryKey());
            
            financialAccountTransactionTypeDescription.setThruTime(session.START_TIME_LONG);
            financialAccountTransactionTypeDescription.store();

            var financialAccountTransactionType = financialAccountTransactionTypeDescription.getFinancialAccountTransactionType();
            var language = financialAccountTransactionTypeDescription.getLanguage();
            var description = financialAccountTransactionTypeDescriptionValue.getDescription();
            
            financialAccountTransactionTypeDescription = FinancialAccountTransactionTypeDescriptionFactory.getInstance().create(session,
                    financialAccountTransactionType, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(financialAccountTransactionType.getPrimaryKey(), EventTypes.MODIFY, financialAccountTransactionTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteFinancialAccountTransactionTypeDescription(FinancialAccountTransactionTypeDescription financialAccountTransactionTypeDescription, BasePK deletedBy) {
        financialAccountTransactionTypeDescription.setThruTime(session.START_TIME_LONG);
        
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

        var financialAccountAliasType = FinancialAccountAliasTypeFactory.getInstance().create();
        var financialAccountAliasTypeDetail = FinancialAccountAliasTypeDetailFactory.getInstance().create(session,
                financialAccountAliasType, financialAccountType, financialAccountAliasTypeName, validationPattern, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        financialAccountAliasType = FinancialAccountAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, financialAccountAliasType.getPrimaryKey());
        financialAccountAliasType.setActiveDetail(financialAccountAliasTypeDetail);
        financialAccountAliasType.setLastDetail(financialAccountAliasTypeDetail);
        financialAccountAliasType.store();
        
        sendEvent(financialAccountAliasType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
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
        return getFinancialTransferCaches().getFinancialAccountAliasTypeTransferCache().getFinancialAccountAliasTypeTransfer(userVisit, financialAccountAliasType);
    }
    
    public List<FinancialAccountAliasTypeTransfer> getFinancialAccountAliasTypeTransfers(UserVisit userVisit, FinancialAccountType financialAccountType) {
        var financialAccountAliasTypes = getFinancialAccountAliasTypes(financialAccountType);
        List<FinancialAccountAliasTypeTransfer> financialAccountAliasTypeTransfers = new ArrayList<>(financialAccountAliasTypes.size());
        var financialAccountAliasTypeTransferCache = getFinancialTransferCaches(userVisit).getFinancialAccountAliasTypeTransferCache();
        
        financialAccountAliasTypes.forEach((financialAccountAliasType) ->
                financialAccountAliasTypeTransfers.add(financialAccountAliasTypeTransferCache.getFinancialAccountAliasTypeTransfer(financialAccountAliasType))
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
            var financialAccountAliasType = FinancialAccountAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    financialAccountAliasTypeDetailValue.getFinancialAccountAliasTypePK());
            var financialAccountAliasTypeDetail = financialAccountAliasType.getActiveDetailForUpdate();
            
            financialAccountAliasTypeDetail.setThruTime(session.START_TIME_LONG);
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
            
            financialAccountAliasTypeDetail = FinancialAccountAliasTypeDetailFactory.getInstance().create(financialAccountAliasTypePK,
                    financialAccountTypePK, financialAccountAliasTypeName, validationPattern, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
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
        financialAccountAliasTypeDetail.setThruTime(session.START_TIME_LONG);
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
    
    public FinancialAccountAliasTypeDescription createFinancialAccountAliasTypeDescription(FinancialAccountAliasType financialAccountAliasType,
            Language language, String description, BasePK createdBy) {
        var financialAccountAliasTypeDescription = FinancialAccountAliasTypeDescriptionFactory.getInstance().create(session,
                financialAccountAliasType, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(financialAccountAliasType.getPrimaryKey(), EventTypes.MODIFY, financialAccountAliasTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
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
        return getFinancialTransferCaches().getFinancialAccountAliasTypeDescriptionTransferCache().getFinancialAccountAliasTypeDescriptionTransfer(userVisit, financialAccountAliasTypeDescription);
    }
    
    public List<FinancialAccountAliasTypeDescriptionTransfer> getFinancialAccountAliasTypeDescriptionTransfersByFinancialAccountAliasType(UserVisit userVisit,
            FinancialAccountAliasType financialAccountAliasType) {
        var financialAccountAliasTypeDescriptions = getFinancialAccountAliasTypeDescriptionsByFinancialAccountAliasType(financialAccountAliasType);
        List<FinancialAccountAliasTypeDescriptionTransfer> financialAccountAliasTypeDescriptionTransfers = new ArrayList<>(financialAccountAliasTypeDescriptions.size());
        var financialAccountAliasTypeDescriptionTransferCache = getFinancialTransferCaches(userVisit).getFinancialAccountAliasTypeDescriptionTransferCache();
        
        financialAccountAliasTypeDescriptions.forEach((financialAccountAliasTypeDescription) ->
                financialAccountAliasTypeDescriptionTransfers.add(financialAccountAliasTypeDescriptionTransferCache.getFinancialAccountAliasTypeDescriptionTransfer(financialAccountAliasTypeDescription))
        );
        
        return financialAccountAliasTypeDescriptionTransfers;
    }
    
    public void updateFinancialAccountAliasTypeDescriptionFromValue(FinancialAccountAliasTypeDescriptionValue financialAccountAliasTypeDescriptionValue,
            BasePK updatedBy) {
        if(financialAccountAliasTypeDescriptionValue.hasBeenModified()) {
            var financialAccountAliasTypeDescription = FinancialAccountAliasTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     financialAccountAliasTypeDescriptionValue.getPrimaryKey());
            
            financialAccountAliasTypeDescription.setThruTime(session.START_TIME_LONG);
            financialAccountAliasTypeDescription.store();

            var financialAccountAliasType = financialAccountAliasTypeDescription.getFinancialAccountAliasType();
            var language = financialAccountAliasTypeDescription.getLanguage();
            var description = financialAccountAliasTypeDescriptionValue.getDescription();
            
            financialAccountAliasTypeDescription = FinancialAccountAliasTypeDescriptionFactory.getInstance().create(financialAccountAliasType,
                    language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(financialAccountAliasType.getPrimaryKey(), EventTypes.MODIFY, financialAccountAliasTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteFinancialAccountAliasTypeDescription(FinancialAccountAliasTypeDescription financialAccountAliasTypeDescription, BasePK deletedBy) {
        financialAccountAliasTypeDescription.setThruTime(session.START_TIME_LONG);
        
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
    
    public FinancialAccountRole createFinancialAccountRoleUsingNames(FinancialAccount financialAccount, Party party,
            PartyContactMechanism partyContactMechanism, String financialAccountRoleTypeName, BasePK createdBy) {
        var financialAccountRoleType = getFinancialAccountRoleTypeByName(financialAccountRoleTypeName);
        
        return createFinancialAccountRole(financialAccount, party, partyContactMechanism, financialAccountRoleType, createdBy);
    }
    
    public FinancialAccountRole createFinancialAccountRole(FinancialAccount financialAccount, Party party, PartyContactMechanism partyContactMechanism,
            FinancialAccountRoleType financialAccountRoleType, BasePK createdBy) {
        var financialAccountRole = FinancialAccountRoleFactory.getInstance().create(financialAccount, party, financialAccountRoleType,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(financialAccount.getPrimaryKey(), EventTypes.MODIFY, financialAccountRole.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
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
    
    public List<FinancialAccountRoleTransfer> getFinancialAccountRoleTransfers(UserVisit userVisit, Collection<FinancialAccountRole> financialAccountRoles) {
        List<FinancialAccountRoleTransfer> financialAccountRoleTransfers = new ArrayList<>(financialAccountRoles.size());
        var financialAccountRoleTransferCache = getFinancialTransferCaches(userVisit).getFinancialAccountRoleTransferCache();
        
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
            var financialAccountRole = FinancialAccountRoleFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     financialAccountRoleValue.getPrimaryKey());
            
            financialAccountRole.setThruTime(session.START_TIME_LONG);
            financialAccountRole.store();

            var financialAccountPK = financialAccountRole.getFinancialAccountPK(); // Not updated
            var partyPK = financialAccountRole.getPartyPK(); // Not updated
            var financialAccountRoleTypePK = financialAccountRole.getFinancialAccountRoleTypePK(); // Not updated
            
            financialAccountRole = FinancialAccountRoleFactory.getInstance().create(financialAccountPK, partyPK, financialAccountRoleTypePK,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(financialAccountPK, EventTypes.MODIFY, financialAccountRole.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteFinancialAccountRole(FinancialAccountRole financialAccountRole, BasePK deletedBy) {
        financialAccountRole.setThruTime(session.START_TIME_LONG);
        
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
    
    public FinancialAccount createFinancialAccount(FinancialAccountType financialAccountType, String financialAccountName, Currency currency,
            GlAccount glAccount, String reference, String description, BasePK createdBy) {
        var financialAccount = FinancialAccountFactory.getInstance().create();
        var financialAccountDetail = FinancialAccountDetailFactory.getInstance().create(financialAccount, financialAccountType,
                financialAccountName, currency, glAccount, reference, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        financialAccount = FinancialAccountFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, financialAccount.getPrimaryKey());
        financialAccount.setActiveDetail(financialAccountDetail);
        financialAccount.setLastDetail(financialAccountDetail);
        financialAccount.store();
        
        sendEvent(financialAccount.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
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
        return getFinancialTransferCaches().getFinancialAccountTransferCache().getFinancialAccountTransfer(userVisit, financialAccount);
    }
    
    public List<FinancialAccountTransfer> getFinancialAccountTransfers(UserVisit userVisit, FinancialAccountType financialAccountType) {
        var financialAccounts = getFinancialAccounts(financialAccountType);
        List<FinancialAccountTransfer> financialAccountTransfers = new ArrayList<>(financialAccounts.size());
        var financialAccountTransferCache = getFinancialTransferCaches(userVisit).getFinancialAccountTransferCache();
        
        financialAccounts.forEach((financialAccount) ->
                financialAccountTransfers.add(financialAccountTransferCache.getFinancialAccountTransfer(financialAccount))
        );
        
        return financialAccountTransfers;
    }
    
    public void updateFinancialAccountFromValue(FinancialAccountDetailValue financialAccountDetailValue, BasePK updatedBy) {
        if(financialAccountDetailValue.hasBeenModified()) {
            var financialAccount = FinancialAccountFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    financialAccountDetailValue.getFinancialAccountPK());
            var financialAccountDetail = financialAccount.getActiveDetailForUpdate();
            
            financialAccountDetail.setThruTime(session.START_TIME_LONG);
            financialAccountDetail.store();

            var financialAccountPK = financialAccountDetail.getFinancialAccountPK();
            var financialAccountType = financialAccountDetail.getFinancialAccountType();
            var financialAccountTypePK = financialAccountType.getPrimaryKey();
            var financialAccountName = financialAccountDetailValue.getFinancialAccountName();
            var currencyPK = financialAccountDetailValue.getCurrencyPK();
            var glAccountPK = financialAccountDetailValue.getGlAccountPK();
            var reference = financialAccountDetailValue.getReference();
            var description = financialAccountDetailValue.getDescription();
            
            financialAccountDetail = FinancialAccountDetailFactory.getInstance().create(financialAccountPK, financialAccountTypePK,
                    financialAccountName, currencyPK, glAccountPK, reference, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
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
        financialAccountDetail.setThruTime(session.START_TIME_LONG);
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
        var financialAccountStatus = getFinancialAccountStatusForUpdate(financialAccount);
        
        if(financialAccountStatus != null) {
            financialAccountStatus.remove();
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Financial Account Aliases
    // --------------------------------------------------------------------------------
    
    public FinancialAccountAlias createFinancialAccountAlias(FinancialAccount financialAccount, FinancialAccountAliasType financialAccountAliasType,
            String alias, BasePK createdBy) {
        var financialAccountAlias = FinancialAccountAliasFactory.getInstance().create(financialAccount, financialAccountAliasType,
                alias, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(financialAccount.getPrimaryKey(), EventTypes.MODIFY, financialAccountAlias.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
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
        return getFinancialTransferCaches().getFinancialAccountAliasTransferCache().getFinancialAccountAliasTransfer(userVisit, financialAccountAlias);
    }
    
    public List<FinancialAccountAliasTransfer> getFinancialAccountAliasTransfersByFinancialAccount(UserVisit userVisit, FinancialAccount financialAccount) {
        var financialaccountaliases = getFinancialAccountAliasesByFinancialAccount(financialAccount);
        List<FinancialAccountAliasTransfer> financialAccountAliasTransfers = new ArrayList<>(financialaccountaliases.size());
        var financialAccountAliasTransferCache = getFinancialTransferCaches(userVisit).getFinancialAccountAliasTransferCache();
        
        financialaccountaliases.forEach((financialAccountAlias) ->
                financialAccountAliasTransfers.add(financialAccountAliasTransferCache.getFinancialAccountAliasTransfer(financialAccountAlias))
        );
        
        return financialAccountAliasTransfers;
    }
    
    public void updateFinancialAccountAliasFromValue(FinancialAccountAliasValue financialAccountAliasValue, BasePK updatedBy) {
        if(financialAccountAliasValue.hasBeenModified()) {
            var financialAccountAlias = FinancialAccountAliasFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    financialAccountAliasValue.getPrimaryKey());
            
            financialAccountAlias.setThruTime(session.START_TIME_LONG);
            financialAccountAlias.store();

            var financialAccountPK = financialAccountAlias.getFinancialAccountPK();
            var financialAccountAliasTypePK = financialAccountAlias.getFinancialAccountAliasTypePK();
            var alias  = financialAccountAliasValue.getAlias();
            
            financialAccountAlias = FinancialAccountAliasFactory.getInstance().create(financialAccountPK, financialAccountAliasTypePK, alias,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(financialAccountPK, EventTypes.MODIFY, financialAccountAlias.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteFinancialAccountAlias(FinancialAccountAlias financialAccountAlias, BasePK deletedBy) {
        financialAccountAlias.setThruTime(session.START_TIME_LONG);
        
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
    
    public FinancialAccountTransaction createFinancialAccountTransaction(String financialAccountTransactionName, FinancialAccount financialAccount,
            FinancialAccountTransactionType financialAccountTransactionType, Long amount, String comment, BasePK createdBy) {
        var financialAccountTransaction = FinancialAccountTransactionFactory.getInstance().create();
        var financialAccountTransactionDetail = FinancialAccountTransactionDetailFactory.getInstance().create(session,
                financialAccountTransaction, financialAccountTransactionName, financialAccount, financialAccountTransactionType, amount, comment,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        financialAccountTransaction = FinancialAccountTransactionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, financialAccountTransaction.getPrimaryKey());
        financialAccountTransaction.setActiveDetail(financialAccountTransactionDetail);
        financialAccountTransaction.setLastDetail(financialAccountTransactionDetail);
        financialAccountTransaction.store();
        
        sendEvent(financialAccountTransaction.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
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
        return getFinancialTransferCaches().getFinancialAccountTransactionTransferCache().getFinancialAccountTransactionTransfer(userVisit, financialAccountTransaction);
    }
    
    public List<FinancialAccountTransactionTransfer> getFinancialAccountTransactionTransfersByFinancialAccount(UserVisit userVisit, FinancialAccount financialAccount) {
        var financialAccountTransactions = getFinancialAccountTransactionsByFinancialAccount(financialAccount);
        List<FinancialAccountTransactionTransfer> financialAccountTransactionTransfers = new ArrayList<>(financialAccountTransactions.size());
        var financialAccountTransactionTransferCache = getFinancialTransferCaches(userVisit).getFinancialAccountTransactionTransferCache();
        
        financialAccountTransactions.forEach((financialAccountTransaction) ->
                financialAccountTransactionTransfers.add(financialAccountTransactionTransferCache.getFinancialAccountTransactionTransfer(financialAccountTransaction))
        );
        
        return financialAccountTransactionTransfers;
    }
    
    public void updateFinancialAccountTransactionFromValue(FinancialAccountTransactionDetailValue financialAccountTransactionDetailValue, BasePK updatedBy) {
        if(financialAccountTransactionDetailValue.hasBeenModified()) {
            var financialAccountTransaction = FinancialAccountTransactionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    financialAccountTransactionDetailValue.getFinancialAccountTransactionPK());
            var financialAccountTransactionDetail = financialAccountTransaction.getActiveDetailForUpdate();
            
            financialAccountTransactionDetail.setThruTime(session.START_TIME_LONG);
            financialAccountTransactionDetail.store();

            var financialAccountTransactionPK = financialAccountTransactionDetail.getFinancialAccountTransactionPK();
            var financialAccountTransactionName = financialAccountTransactionDetailValue.getFinancialAccountTransactionName();
            var financialAccountPK = financialAccountTransactionDetailValue.getFinancialAccountPK();
            var financialAccountTransactionTypePK = financialAccountTransactionDetailValue.getFinancialAccountTransactionTypePK();
            var amount = financialAccountTransactionDetailValue.getAmount();
            var comment = financialAccountTransactionDetailValue.getComment();
            
            financialAccountTransactionDetail = FinancialAccountTransactionDetailFactory.getInstance().create(financialAccountTransactionPK,
                    financialAccountTransactionName, financialAccountPK, financialAccountTransactionTypePK, amount, comment, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            financialAccountTransaction.setActiveDetail(financialAccountTransactionDetail);
            financialAccountTransaction.setLastDetail(financialAccountTransactionDetail);
            
            sendEvent(financialAccountTransactionPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteFinancialAccountTransaction(FinancialAccountTransaction financialAccountTransaction, BasePK deletedBy) {
        var financialAccountTransactionDetail = financialAccountTransaction.getLastDetailForUpdate();
        financialAccountTransactionDetail.setThruTime(session.START_TIME_LONG);
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

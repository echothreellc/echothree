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

package com.echothree.model.control.accounting.server.control;

import com.echothree.model.control.accounting.common.TransactionTimeTypes;
import com.echothree.model.control.accounting.common.choice.CurrencyChoicesBean;
import com.echothree.model.control.accounting.common.choice.GlAccountCategoryChoicesBean;
import com.echothree.model.control.accounting.common.choice.GlAccountChoicesBean;
import com.echothree.model.control.accounting.common.choice.GlAccountClassChoicesBean;
import com.echothree.model.control.accounting.common.choice.GlAccountTypeChoicesBean;
import com.echothree.model.control.accounting.common.choice.GlResourceTypeChoicesBean;
import com.echothree.model.control.accounting.common.choice.ItemAccountingCategoryChoicesBean;
import com.echothree.model.control.accounting.common.choice.SymbolPositionChoicesBean;
import com.echothree.model.control.accounting.common.choice.TransactionGroupStatusChoicesBean;
import com.echothree.model.control.accounting.common.transfer.CurrencyDescriptionTransfer;
import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.accounting.common.transfer.GlAccountCategoryDescriptionTransfer;
import com.echothree.model.control.accounting.common.transfer.GlAccountCategoryTransfer;
import com.echothree.model.control.accounting.common.transfer.GlAccountClassDescriptionTransfer;
import com.echothree.model.control.accounting.common.transfer.GlAccountClassTransfer;
import com.echothree.model.control.accounting.common.transfer.GlAccountDescriptionTransfer;
import com.echothree.model.control.accounting.common.transfer.GlAccountTransfer;
import com.echothree.model.control.accounting.common.transfer.GlAccountTypeTransfer;
import com.echothree.model.control.accounting.common.transfer.GlResourceTypeDescriptionTransfer;
import com.echothree.model.control.accounting.common.transfer.GlResourceTypeTransfer;
import com.echothree.model.control.accounting.common.transfer.ItemAccountingCategoryDescriptionTransfer;
import com.echothree.model.control.accounting.common.transfer.ItemAccountingCategoryTransfer;
import com.echothree.model.control.accounting.common.transfer.SymbolPositionDescriptionTransfer;
import com.echothree.model.control.accounting.common.transfer.SymbolPositionTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionEntityRoleTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionEntityRoleTypeDescriptionTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionEntityRoleTypeTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionGlAccountCategoryDescriptionTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionGlAccountCategoryTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionGlAccountTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionGlEntryTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionGroupTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionTypeDescriptionTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionTypeTransfer;
import static com.echothree.model.control.accounting.common.workflow.TransactionGroupStatusConstants.WorkflowStep_TRANSACTION_GROUP_STATUS_ACTIVE;
import static com.echothree.model.control.accounting.common.workflow.TransactionGroupStatusConstants.Workflow_TRANSACTION_GROUP_STATUS;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.financial.server.control.FinancialControl;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.accounting.common.pk.CurrencyPK;
import com.echothree.model.data.accounting.common.pk.GlAccountCategoryPK;
import com.echothree.model.data.accounting.common.pk.GlAccountClassPK;
import com.echothree.model.data.accounting.common.pk.GlAccountPK;
import com.echothree.model.data.accounting.common.pk.GlAccountTypePK;
import com.echothree.model.data.accounting.common.pk.GlResourceTypePK;
import com.echothree.model.data.accounting.common.pk.ItemAccountingCategoryPK;
import com.echothree.model.data.accounting.common.pk.SymbolPositionPK;
import com.echothree.model.data.accounting.common.pk.TransactionEntityRoleTypePK;
import com.echothree.model.data.accounting.common.pk.TransactionGlAccountCategoryPK;
import com.echothree.model.data.accounting.common.pk.TransactionGroupPK;
import com.echothree.model.data.accounting.common.pk.TransactionPK;
import com.echothree.model.data.accounting.common.pk.TransactionTypePK;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.entity.CurrencyDescription;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.accounting.server.entity.GlAccountCategory;
import com.echothree.model.data.accounting.server.entity.GlAccountCategoryDescription;
import com.echothree.model.data.accounting.server.entity.GlAccountClass;
import com.echothree.model.data.accounting.server.entity.GlAccountClassDescription;
import com.echothree.model.data.accounting.server.entity.GlAccountDescription;
import com.echothree.model.data.accounting.server.entity.GlAccountSummary;
import com.echothree.model.data.accounting.server.entity.GlAccountType;
import com.echothree.model.data.accounting.server.entity.GlAccountTypeDescription;
import com.echothree.model.data.accounting.server.entity.GlResourceType;
import com.echothree.model.data.accounting.server.entity.GlResourceTypeDescription;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategoryDescription;
import com.echothree.model.data.accounting.server.entity.SymbolPosition;
import com.echothree.model.data.accounting.server.entity.SymbolPositionDescription;
import com.echothree.model.data.accounting.server.entity.Transaction;
import com.echothree.model.data.accounting.server.entity.TransactionEntityRole;
import com.echothree.model.data.accounting.server.entity.TransactionEntityRoleType;
import com.echothree.model.data.accounting.server.entity.TransactionEntityRoleTypeDescription;
import com.echothree.model.data.accounting.server.entity.TransactionGlAccount;
import com.echothree.model.data.accounting.server.entity.TransactionGlAccountCategory;
import com.echothree.model.data.accounting.server.entity.TransactionGlAccountCategoryDescription;
import com.echothree.model.data.accounting.server.entity.TransactionGlEntry;
import com.echothree.model.data.accounting.server.entity.TransactionGroup;
import com.echothree.model.data.accounting.server.entity.TransactionStatus;
import com.echothree.model.data.accounting.server.entity.TransactionType;
import com.echothree.model.data.accounting.server.entity.TransactionTypeDescription;
import com.echothree.model.data.accounting.server.factory.CurrencyDescriptionFactory;
import com.echothree.model.data.accounting.server.factory.CurrencyFactory;
import com.echothree.model.data.accounting.server.factory.GlAccountCategoryDescriptionFactory;
import com.echothree.model.data.accounting.server.factory.GlAccountCategoryDetailFactory;
import com.echothree.model.data.accounting.server.factory.GlAccountCategoryFactory;
import com.echothree.model.data.accounting.server.factory.GlAccountClassDescriptionFactory;
import com.echothree.model.data.accounting.server.factory.GlAccountClassDetailFactory;
import com.echothree.model.data.accounting.server.factory.GlAccountClassFactory;
import com.echothree.model.data.accounting.server.factory.GlAccountDescriptionFactory;
import com.echothree.model.data.accounting.server.factory.GlAccountDetailFactory;
import com.echothree.model.data.accounting.server.factory.GlAccountFactory;
import com.echothree.model.data.accounting.server.factory.GlAccountSummaryFactory;
import com.echothree.model.data.accounting.server.factory.GlAccountTypeDescriptionFactory;
import com.echothree.model.data.accounting.server.factory.GlAccountTypeFactory;
import com.echothree.model.data.accounting.server.factory.GlResourceTypeDescriptionFactory;
import com.echothree.model.data.accounting.server.factory.GlResourceTypeDetailFactory;
import com.echothree.model.data.accounting.server.factory.GlResourceTypeFactory;
import com.echothree.model.data.accounting.server.factory.ItemAccountingCategoryDescriptionFactory;
import com.echothree.model.data.accounting.server.factory.ItemAccountingCategoryDetailFactory;
import com.echothree.model.data.accounting.server.factory.ItemAccountingCategoryFactory;
import com.echothree.model.data.accounting.server.factory.SymbolPositionDescriptionFactory;
import com.echothree.model.data.accounting.server.factory.SymbolPositionDetailFactory;
import com.echothree.model.data.accounting.server.factory.SymbolPositionFactory;
import com.echothree.model.data.accounting.server.factory.TransactionDetailFactory;
import com.echothree.model.data.accounting.server.factory.TransactionEntityRoleFactory;
import com.echothree.model.data.accounting.server.factory.TransactionEntityRoleTypeDescriptionFactory;
import com.echothree.model.data.accounting.server.factory.TransactionEntityRoleTypeDetailFactory;
import com.echothree.model.data.accounting.server.factory.TransactionEntityRoleTypeFactory;
import com.echothree.model.data.accounting.server.factory.TransactionFactory;
import com.echothree.model.data.accounting.server.factory.TransactionGlAccountCategoryDescriptionFactory;
import com.echothree.model.data.accounting.server.factory.TransactionGlAccountCategoryDetailFactory;
import com.echothree.model.data.accounting.server.factory.TransactionGlAccountCategoryFactory;
import com.echothree.model.data.accounting.server.factory.TransactionGlAccountFactory;
import com.echothree.model.data.accounting.server.factory.TransactionGlEntryFactory;
import com.echothree.model.data.accounting.server.factory.TransactionGroupDetailFactory;
import com.echothree.model.data.accounting.server.factory.TransactionGroupFactory;
import com.echothree.model.data.accounting.server.factory.TransactionStatusFactory;
import com.echothree.model.data.accounting.server.factory.TransactionTypeDescriptionFactory;
import com.echothree.model.data.accounting.server.factory.TransactionTypeDetailFactory;
import com.echothree.model.data.accounting.server.factory.TransactionTypeFactory;
import com.echothree.model.data.accounting.server.value.GlAccountCategoryDescriptionValue;
import com.echothree.model.data.accounting.server.value.GlAccountCategoryDetailValue;
import com.echothree.model.data.accounting.server.value.GlAccountClassDescriptionValue;
import com.echothree.model.data.accounting.server.value.GlAccountClassDetailValue;
import com.echothree.model.data.accounting.server.value.GlAccountDescriptionValue;
import com.echothree.model.data.accounting.server.value.GlAccountDetailValue;
import com.echothree.model.data.accounting.server.value.GlResourceTypeDescriptionValue;
import com.echothree.model.data.accounting.server.value.GlResourceTypeDetailValue;
import com.echothree.model.data.accounting.server.value.ItemAccountingCategoryDescriptionValue;
import com.echothree.model.data.accounting.server.value.ItemAccountingCategoryDetailValue;
import com.echothree.model.data.accounting.server.value.SymbolPositionDescriptionValue;
import com.echothree.model.data.accounting.server.value.SymbolPositionDetailValue;
import com.echothree.model.data.accounting.server.value.TransactionDetailValue;
import com.echothree.model.data.accounting.server.value.TransactionEntityRoleTypeDescriptionValue;
import com.echothree.model.data.accounting.server.value.TransactionEntityRoleTypeDetailValue;
import com.echothree.model.data.accounting.server.value.TransactionGlAccountCategoryDescriptionValue;
import com.echothree.model.data.accounting.server.value.TransactionGlAccountCategoryDetailValue;
import com.echothree.model.data.accounting.server.value.TransactionGlAccountValue;
import com.echothree.model.data.accounting.server.value.TransactionGroupDetailValue;
import com.echothree.model.data.accounting.server.value.TransactionTypeDescriptionValue;
import com.echothree.model.data.accounting.server.value.TransactionTypeDetailValue;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.period.server.entity.Period;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class AccountingControl
        extends BaseAccountingControl {
    
    /** Creates a new instance of AccountingControl */
    public AccountingControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    // Currencies
    // --------------------------------------------------------------------------------
    
    public Currency createCurrency(String currencyIsoName, String symbol, SymbolPosition symbolPosition, Boolean symbolOnListStart,
            Boolean symbolOnListMember, Boolean symbolOnSubtotal, Boolean symbolOnTotal, String groupingSeparator, Integer groupingSize,
            String fractionSeparator, Integer defaultFractionDigits, Integer priceUnitFractionDigits, Integer priceLineFractionDigits,
            Integer costUnitFractionDigits, Integer costLineFractionDigits, String minusSign, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultCurrency = getDefaultCurrencyForUpdate();
        
        if(defaultCurrency == null) {
            isDefault = Boolean.TRUE;
        } else if(isDefault) {
            defaultCurrency.setIsDefault(Boolean.FALSE);
        }

        var currency = CurrencyFactory.getInstance().create(currencyIsoName, symbol, symbolPosition, symbolOnListStart,
                symbolOnListMember, symbolOnSubtotal, symbolOnTotal, groupingSeparator, groupingSize, fractionSeparator,
                defaultFractionDigits, priceUnitFractionDigits, priceLineFractionDigits, costUnitFractionDigits,
                costLineFractionDigits, minusSign, isDefault, sortOrder);
        
        sendEvent(currency.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return currency;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Currency */
    public Currency getCurrencyByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new CurrencyPK(entityInstance.getEntityUniqueId());

        return CurrencyFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public Currency getCurrencyByEntityInstance(EntityInstance entityInstance) {
        return getCurrencyByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Currency getCurrencyByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getCurrencyByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countCurrencies() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM currencies");
    }

    public List<Currency> getCurrencies() {
        var ps = CurrencyFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM currencies " +
                "ORDER BY cur_sortorder, cur_currencyisoname " +
                "_LIMIT_");
        
        return CurrencyFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    private Currency getDefaultCurrency(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM currencies " +
                    "WHERE cur_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM currencies " +
                    "WHERE cur_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = CurrencyFactory.getInstance().prepareStatement(query);
        
        return CurrencyFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public Currency getDefaultCurrency() {
        return getDefaultCurrency(EntityPermission.READ_ONLY);
    }
    
    public Currency getDefaultCurrencyForUpdate() {
        return getDefaultCurrency(EntityPermission.READ_WRITE);
    }
    
    private Currency getCurrencyByIsoName(String currencyIsoName, EntityPermission entityPermission) {
        Currency currency;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM currencies " +
                        "WHERE cur_currencyisoname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM currencies " +
                        "WHERE cur_currencyisoname = ? " +
                        "FOR UPDATE";
            }

            var ps = CurrencyFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, currencyIsoName);
            
            currency = CurrencyFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return currency;
    }
    
    public Currency getCurrencyByIsoName(String currencyIsoName) {
        return getCurrencyByIsoName(currencyIsoName, EntityPermission.READ_ONLY);
    }
    
    public Currency getCurrencyByIsoNameForUpdate(String currencyIsoName) {
        return getCurrencyByIsoName(currencyIsoName, EntityPermission.READ_WRITE);
    }
    
    public CurrencyChoicesBean getCurrencyChoices(String defaultCurrencyChoice, Language language, boolean allowNullChoice) {
        var currencies = getCurrencies();
        var size = currencies.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultCurrencyChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var currency : currencies) {
            var label = getBestCurrencyDescription(currency, language);
            var value = currency.getCurrencyIsoName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultCurrencyChoice != null && defaultCurrencyChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && currency.getIsDefault()))
                defaultValue = value;
        }
        
        return new CurrencyChoicesBean(labels, values, defaultValue);
    }
    
    public CurrencyTransfer getCurrencyTransfer(UserVisit userVisit, Currency currency) {
        return getAccountingTransferCaches(userVisit).getCurrencyTransferCache().getTransfer(currency);
    }
    
    public List<CurrencyTransfer> getCurrencyTransfers(UserVisit userVisit, Collection<Currency> currencies) {
        List<CurrencyTransfer> currencyTransfers = null;
        
        if(currencies != null) {
            currencyTransfers = new ArrayList<>(currencies.size());
            
            for(var currency : currencies) {
                currencyTransfers.add(getAccountingTransferCaches(userVisit).getCurrencyTransferCache().getTransfer(currency));
            }
        }
        
        return currencyTransfers;
    }
    
    public List<CurrencyTransfer> getCurrencyTransfers(UserVisit userVisit) {
        return getCurrencyTransfers(userVisit, getCurrencies());
    }
    
    // --------------------------------------------------------------------------------
    // Currency Descriptions
    // --------------------------------------------------------------------------------
    
    public CurrencyDescription createCurrencyDescription(Currency currency, Language language, String description, BasePK createdBy) {
        var currencyDescription = CurrencyDescriptionFactory.getInstance().create(currency, language, description);
        
        sendEvent(currency.getPrimaryKey(), EventTypes.MODIFY, currencyDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return currencyDescription;
    }
    
    public CurrencyDescription getCurrencyDescription(Currency currency, Language language) {
        CurrencyDescription currencyDescription;
        
        try {
            var ps = CurrencyDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM currencydescriptions " +
                    "WHERE curd_cur_currencyid = ? AND curd_lang_languageid = ?");
            
            ps.setLong(1, currency.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            currencyDescription = CurrencyDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return currencyDescription;
    }
    
    private List<CurrencyDescription> getCurrencyDescriptionsByCurrency(Currency currency, EntityPermission entityPermission) {
        List<CurrencyDescription> currencyDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM currencydescriptions, languages " +
                        "WHERE curd_cur_currencyid = ? AND curd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM currencydescriptions " +
                        "WHERE curd_cur_currencyid = ? " +
                        "FOR UPDATE";
            }

            var ps = CurrencyDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, currency.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            currencyDescriptions = CurrencyDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return currencyDescriptions;
    }
    
    public List<CurrencyDescription> getCurrencyDescriptionsByCurrency(Currency currency) {
        return getCurrencyDescriptionsByCurrency(currency, EntityPermission.READ_ONLY);
    }
    
    public List<CurrencyDescription> getCurrencyDescriptionsByCurrencyForUpdate(Currency currency) {
        return getCurrencyDescriptionsByCurrency(currency, EntityPermission.READ_WRITE);
    }
    
    public String getBestCurrencyDescription(Currency currency, Language language) {
        String description;
        var currencyDescription = getCurrencyDescription(currency, language);
        
        if(currencyDescription == null && !language.getIsDefault()) {
            currencyDescription = getCurrencyDescription(currency, getPartyControl().getDefaultLanguage());
        }
        
        if(currencyDescription == null) {
            description = currency.getCurrencyIsoName();
        } else {
            description = currencyDescription.getDescription();
        }
        
        return description;
    }
    
    public List<CurrencyDescriptionTransfer> getCurrencyDescriptionTransfers(UserVisit userVisit, Currency currency) {
        var currencyDescriptions = getCurrencyDescriptionsByCurrency(currency);
        List<CurrencyDescriptionTransfer> currencyDescriptionTransfers = new ArrayList<>(currencyDescriptions.size());
        var currencyDescriptionTransferCache = getAccountingTransferCaches(userVisit).getCurrencyDescriptionTransferCache();
        
        currencyDescriptions.forEach((currencyDescription) ->
                currencyDescriptionTransfers.add(currencyDescriptionTransferCache.getTransfer(currencyDescription))
        );
        
        return currencyDescriptionTransfers;
    }
    
    // --------------------------------------------------------------------------------
    //   Item Accounting Categories
    // --------------------------------------------------------------------------------
    
    public ItemAccountingCategory createItemAccountingCategory(String itemAccountingCategoryName,
            ItemAccountingCategory parentItemAccountingCategory, GlAccount inventoryGlAccount, GlAccount salesGlAccount,
            GlAccount returnsGlAccount, GlAccount cogsGlAccount, GlAccount returnsCogsGlAccount, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultItemAccountingCategory = getDefaultItemAccountingCategory();
        var defaultFound = defaultItemAccountingCategory != null;
        
        if(defaultFound && isDefault) {
            var defaultItemAccountingCategoryDetailValue = getDefaultItemAccountingCategoryDetailValueForUpdate();
            
            defaultItemAccountingCategoryDetailValue.setIsDefault(Boolean.FALSE);
            updateItemAccountingCategoryFromValue(defaultItemAccountingCategoryDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        var itemAccountingCategory = ItemAccountingCategoryFactory.getInstance().create();
        var itemAccountingCategoryDetail = ItemAccountingCategoryDetailFactory.getInstance().create(session,
                itemAccountingCategory, itemAccountingCategoryName, parentItemAccountingCategory, inventoryGlAccount,
                salesGlAccount, returnsGlAccount, cogsGlAccount, returnsCogsGlAccount, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        itemAccountingCategory = ItemAccountingCategoryFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                itemAccountingCategory.getPrimaryKey());
        itemAccountingCategory.setActiveDetail(itemAccountingCategoryDetail);
        itemAccountingCategory.setLastDetail(itemAccountingCategoryDetail);
        itemAccountingCategory.store();
        
        sendEvent(itemAccountingCategory.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return itemAccountingCategory;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ItemAccountingCategory */
    public ItemAccountingCategory getItemAccountingCategoryByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ItemAccountingCategoryPK(entityInstance.getEntityUniqueId());

        return ItemAccountingCategoryFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public ItemAccountingCategory getItemAccountingCategoryByEntityInstance(EntityInstance entityInstance) {
        return getItemAccountingCategoryByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ItemAccountingCategory getItemAccountingCategoryByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getItemAccountingCategoryByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countItemAccountingCategories() {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM itemaccountingcategories, itemaccountingcategorydetails
                WHERE iactgc_activedetailid = iactgcdt_itemaccountingcategorydetailid
                """);
    }

    private static final Map<EntityPermission, String> getItemAccountingCategoryByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemaccountingcategories, itemaccountingcategorydetails " +
                "WHERE iactgc_activedetailid = iactgcdt_itemaccountingcategorydetailid " +
                "AND iactgcdt_itemaccountingcategoryname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemaccountingcategories, itemaccountingcategorydetails " +
                "WHERE iactgc_activedetailid = iactgcdt_itemaccountingcategorydetailid " +
                "AND iactgcdt_itemaccountingcategoryname = ? " +
                "FOR UPDATE");
        getItemAccountingCategoryByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public ItemAccountingCategory getItemAccountingCategoryByName(String itemAccountingCategoryName, EntityPermission entityPermission) {
        return ItemAccountingCategoryFactory.getInstance().getEntityFromQuery(entityPermission, getItemAccountingCategoryByNameQueries, itemAccountingCategoryName);
    }

    public ItemAccountingCategory getItemAccountingCategoryByName(String itemAccountingCategoryName) {
        return getItemAccountingCategoryByName(itemAccountingCategoryName, EntityPermission.READ_ONLY);
    }

    public ItemAccountingCategory getItemAccountingCategoryByNameForUpdate(String itemAccountingCategoryName) {
        return getItemAccountingCategoryByName(itemAccountingCategoryName, EntityPermission.READ_WRITE);
    }

    public ItemAccountingCategoryDetailValue getItemAccountingCategoryDetailValueForUpdate(ItemAccountingCategory itemAccountingCategory) {
        return itemAccountingCategory == null? null: itemAccountingCategory.getLastDetailForUpdate().getItemAccountingCategoryDetailValue().clone();
    }

    public ItemAccountingCategoryDetailValue getItemAccountingCategoryDetailValueByNameForUpdate(String itemAccountingCategoryName) {
        return getItemAccountingCategoryDetailValueForUpdate(getItemAccountingCategoryByNameForUpdate(itemAccountingCategoryName));
    }

    private static final Map<EntityPermission, String> getDefaultItemAccountingCategoryQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemaccountingcategories, itemaccountingcategorydetails " +
                "WHERE iactgc_activedetailid = iactgcdt_itemaccountingcategorydetailid " +
                "AND iactgcdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemaccountingcategories, itemaccountingcategorydetails " +
                "WHERE iactgc_activedetailid = iactgcdt_itemaccountingcategorydetailid " +
                "AND iactgcdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultItemAccountingCategoryQueries = Collections.unmodifiableMap(queryMap);
    }

    public ItemAccountingCategory getDefaultItemAccountingCategory(EntityPermission entityPermission) {
        return ItemAccountingCategoryFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultItemAccountingCategoryQueries);
    }

    public ItemAccountingCategory getDefaultItemAccountingCategory() {
        return getDefaultItemAccountingCategory(EntityPermission.READ_ONLY);
    }

    public ItemAccountingCategory getDefaultItemAccountingCategoryForUpdate() {
        return getDefaultItemAccountingCategory(EntityPermission.READ_WRITE);
    }

    public ItemAccountingCategoryDetailValue getDefaultItemAccountingCategoryDetailValueForUpdate() {
        return getDefaultItemAccountingCategoryForUpdate().getLastDetailForUpdate().getItemAccountingCategoryDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getItemAccountingCategoriesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemaccountingcategories, itemaccountingcategorydetails " +
                "WHERE iactgc_activedetailid = iactgcdt_itemaccountingcategorydetailid " +
                "ORDER BY iactgcdt_sortorder, iactgcdt_itemaccountingcategoryname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemaccountingcategories, itemaccountingcategorydetails " +
                "WHERE iactgc_activedetailid = iactgcdt_itemaccountingcategorydetailid " +
                "FOR UPDATE");
        getItemAccountingCategoriesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ItemAccountingCategory> getItemAccountingCategories(EntityPermission entityPermission) {
        return ItemAccountingCategoryFactory.getInstance().getEntitiesFromQuery(entityPermission, getItemAccountingCategoriesQueries);
    }

    public List<ItemAccountingCategory> getItemAccountingCategories() {
        return getItemAccountingCategories(EntityPermission.READ_ONLY);
    }

    public List<ItemAccountingCategory> getItemAccountingCategoriesForUpdate() {
        return getItemAccountingCategories(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getItemAccountingCategoriesByParentItemAccountingCategoryQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemaccountingcategories, itemaccountingcategorydetails " +
                "WHERE iactgc_activedetailid = iactgcdt_itemaccountingcategorydetailid AND iactgcdt_parentitemaccountingcategoryid = ? " +
                "ORDER BY iactgcdt_sortorder, iactgcdt_itemaccountingcategoryname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemaccountingcategories, itemaccountingcategorydetails " +
                "WHERE iactgc_activedetailid = iactgcdt_itemaccountingcategorydetailid AND iactgcdt_parentitemaccountingcategoryid = ? " +
                "FOR UPDATE");
        getItemAccountingCategoriesByParentItemAccountingCategoryQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ItemAccountingCategory> getItemAccountingCategoriesByParentItemAccountingCategory(ItemAccountingCategory parentItemAccountingCategory,
            EntityPermission entityPermission) {
        return ItemAccountingCategoryFactory.getInstance().getEntitiesFromQuery(entityPermission, getItemAccountingCategoriesByParentItemAccountingCategoryQueries,
                parentItemAccountingCategory);
    }

    public List<ItemAccountingCategory> getItemAccountingCategoriesByParentItemAccountingCategory(ItemAccountingCategory parentItemAccountingCategory) {
        return getItemAccountingCategoriesByParentItemAccountingCategory(parentItemAccountingCategory, EntityPermission.READ_ONLY);
    }

    public List<ItemAccountingCategory> getItemAccountingCategoriesByParentItemAccountingCategoryForUpdate(ItemAccountingCategory parentItemAccountingCategory) {
        return getItemAccountingCategoriesByParentItemAccountingCategory(parentItemAccountingCategory, EntityPermission.READ_WRITE);
    }

    public ItemAccountingCategoryTransfer getItemAccountingCategoryTransfer(UserVisit userVisit, ItemAccountingCategory itemAccountingCategory) {
        return getAccountingTransferCaches(userVisit).getItemAccountingCategoryTransferCache().getTransfer(itemAccountingCategory);
    }

    public List<ItemAccountingCategoryTransfer> getItemAccountingCategoryTransfers(UserVisit userVisit, Collection<ItemAccountingCategory> itemAccountingCategories) {
        var itemAccountingCategoryTransfers = new ArrayList<ItemAccountingCategoryTransfer>(itemAccountingCategories.size());
        var itemAccountingCategoryTransferCache = getAccountingTransferCaches(userVisit).getItemAccountingCategoryTransferCache();

        itemAccountingCategories.forEach((itemAccountingCategory) ->
                itemAccountingCategoryTransfers.add(itemAccountingCategoryTransferCache.getTransfer(itemAccountingCategory))
        );

        return itemAccountingCategoryTransfers;
    }

    public List<ItemAccountingCategoryTransfer> getItemAccountingCategoryTransfers(UserVisit userVisit) {
        return getItemAccountingCategoryTransfers(userVisit, getItemAccountingCategories());
    }

    public ItemAccountingCategoryChoicesBean getItemAccountingCategoryChoices(String defaultItemAccountingCategoryChoice,
            Language language, boolean allowNullChoice) {
        var itemAccountingCategories = getItemAccountingCategories();
        var size = itemAccountingCategories.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultItemAccountingCategoryChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var itemAccountingCategory : itemAccountingCategories) {
            var itemAccountingCategoryDetail = itemAccountingCategory.getLastDetail();
            
            var label = getBestItemAccountingCategoryDescription(itemAccountingCategory, language);
            var value = itemAccountingCategoryDetail.getItemAccountingCategoryName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultItemAccountingCategoryChoice != null && defaultItemAccountingCategoryChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && itemAccountingCategoryDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new ItemAccountingCategoryChoicesBean(labels, values, defaultValue);
    }
    
    public boolean isParentItemAccountingCategorySafe(ItemAccountingCategory itemAccountingCategory,
            ItemAccountingCategory parentItemAccountingCategory) {
        var safe = true;
        
        if(parentItemAccountingCategory != null) {
            Set<ItemAccountingCategory> parentItemAccountingCategories = new HashSet<>();
            
            parentItemAccountingCategories.add(itemAccountingCategory);
            do {
                if(parentItemAccountingCategories.contains(parentItemAccountingCategory)) {
                    safe = false;
                    break;
                }
                
                parentItemAccountingCategories.add(parentItemAccountingCategory);
                parentItemAccountingCategory = parentItemAccountingCategory.getLastDetail().getParentItemAccountingCategory();
            } while(parentItemAccountingCategory != null);
        }
        
        return safe;
    }
    
    private void updateItemAccountingCategoryFromValue(ItemAccountingCategoryDetailValue itemAccountingCategoryDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(itemAccountingCategoryDetailValue.hasBeenModified()) {
            var itemAccountingCategory = ItemAccountingCategoryFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemAccountingCategoryDetailValue.getItemAccountingCategoryPK());
            var itemAccountingCategoryDetail = itemAccountingCategory.getActiveDetailForUpdate();
            
            itemAccountingCategoryDetail.setThruTime(session.START_TIME_LONG);
            itemAccountingCategoryDetail.store();

            var itemAccountingCategoryPK = itemAccountingCategoryDetail.getItemAccountingCategoryPK();
            var itemAccountingCategoryName = itemAccountingCategoryDetailValue.getItemAccountingCategoryName();
            var parentItemAccountingCategoryPK = itemAccountingCategoryDetailValue.getParentItemAccountingCategoryPK();
            var inventoryGlAccountPK = itemAccountingCategoryDetailValue.getInventoryGlAccountPK();
            var salesGlAccountPK = itemAccountingCategoryDetailValue.getSalesGlAccountPK();
            var returnsGlAccountPK = itemAccountingCategoryDetailValue.getReturnsCogsGlAccountPK();
            var cogsGlAccountPK = itemAccountingCategoryDetailValue.getCogsGlAccountPK();
            var returnsCogsGlAccountPK = itemAccountingCategoryDetailValue.getReturnsCogsGlAccountPK();
            var isDefault = itemAccountingCategoryDetailValue.getIsDefault();
            var sortOrder = itemAccountingCategoryDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultItemAccountingCategory = getDefaultItemAccountingCategory();
                var defaultFound = defaultItemAccountingCategory != null && !defaultItemAccountingCategory.equals(itemAccountingCategory);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultItemAccountingCategoryDetailValue = getDefaultItemAccountingCategoryDetailValueForUpdate();
                    
                    defaultItemAccountingCategoryDetailValue.setIsDefault(Boolean.FALSE);
                    updateItemAccountingCategoryFromValue(defaultItemAccountingCategoryDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            itemAccountingCategoryDetail = ItemAccountingCategoryDetailFactory.getInstance().create(itemAccountingCategoryPK,
                    itemAccountingCategoryName, parentItemAccountingCategoryPK, inventoryGlAccountPK, salesGlAccountPK,
                    returnsGlAccountPK, cogsGlAccountPK, returnsCogsGlAccountPK, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            itemAccountingCategory.setActiveDetail(itemAccountingCategoryDetail);
            itemAccountingCategory.setLastDetail(itemAccountingCategoryDetail);
            
            sendEvent(itemAccountingCategoryPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateItemAccountingCategoryFromValue(ItemAccountingCategoryDetailValue itemAccountingCategoryDetailValue, BasePK updatedBy) {
        updateItemAccountingCategoryFromValue(itemAccountingCategoryDetailValue, true, updatedBy);
    }
    
    private void deleteItemAccountingCategory(ItemAccountingCategory itemAccountingCategory, boolean checkDefault, BasePK deletedBy) {
        var inventoryControl = Session.getModelController(InventoryControl.class);
        var itemAccountingCategoryDetail = itemAccountingCategory.getLastDetailForUpdate();
        
        deleteItemAccountingCategoriesByParentItemAccountingCategory(itemAccountingCategory, deletedBy);
        deleteItemAccountingCategoryDescriptionsByItemAccountingCategory(itemAccountingCategory, deletedBy);
        inventoryControl.deleteInventoryConditionGlAccountsByItemAccountingCategory(itemAccountingCategory, deletedBy);
        
        itemAccountingCategoryDetail.setThruTime(session.START_TIME_LONG);
        itemAccountingCategory.setActiveDetail(null);
        itemAccountingCategory.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultItemAccountingCategory = getDefaultItemAccountingCategory();

            if(defaultItemAccountingCategory == null) {
                var itemAccountingCategories = getItemAccountingCategoriesForUpdate();

                if(!itemAccountingCategories.isEmpty()) {
                    var iter = itemAccountingCategories.iterator();
                    if(iter.hasNext()) {
                        defaultItemAccountingCategory = iter.next();
                    }
                    var itemAccountingCategoryDetailValue = Objects.requireNonNull(defaultItemAccountingCategory).getLastDetailForUpdate().getItemAccountingCategoryDetailValue().clone();

                    itemAccountingCategoryDetailValue.setIsDefault(Boolean.TRUE);
                    updateItemAccountingCategoryFromValue(itemAccountingCategoryDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(itemAccountingCategory.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteItemAccountingCategory(ItemAccountingCategory itemAccountingCategory, BasePK deletedBy) {
        deleteItemAccountingCategory(itemAccountingCategory, true, deletedBy);
    }

    private void deleteItemAccountingCategories(List<ItemAccountingCategory> itemAccountingCategories, boolean checkDefault, BasePK deletedBy) {
        itemAccountingCategories.forEach((itemAccountingCategory) -> deleteItemAccountingCategory(itemAccountingCategory, checkDefault, deletedBy));
    }

    public void deleteItemAccountingCategories(List<ItemAccountingCategory> itemAccountingCategories, BasePK deletedBy) {
        deleteItemAccountingCategories(itemAccountingCategories, true, deletedBy);
    }

    private void deleteItemAccountingCategoriesByParentItemAccountingCategory(ItemAccountingCategory parentItemAccountingCategory, BasePK deletedBy) {
        deleteItemAccountingCategories(getItemAccountingCategoriesByParentItemAccountingCategoryForUpdate(parentItemAccountingCategory), false, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Item Accounting Category Descriptions
    // --------------------------------------------------------------------------------
    
    public ItemAccountingCategoryDescription createItemAccountingCategoryDescription(ItemAccountingCategory itemAccountingCategory,
            Language language, String description, BasePK createdBy) {
        var itemAccountingCategoryDescription = ItemAccountingCategoryDescriptionFactory.getInstance().create(itemAccountingCategory, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEvent(itemAccountingCategory.getPrimaryKey(), EventTypes.MODIFY, itemAccountingCategoryDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemAccountingCategoryDescription;
    }
    
    private ItemAccountingCategoryDescription getItemAccountingCategoryDescription(ItemAccountingCategory itemAccountingCategory,
            Language language, EntityPermission entityPermission) {
        ItemAccountingCategoryDescription itemAccountingCategoryDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaccountingcategorydescriptions " +
                        "WHERE iactgcd_iactgc_itemaccountingcategoryid = ? AND iactgcd_lang_languageid = ? AND iactgcd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaccountingcategorydescriptions " +
                        "WHERE iactgcd_iactgc_itemaccountingcategoryid = ? AND iactgcd_lang_languageid = ? AND iactgcd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemAccountingCategoryDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, itemAccountingCategory.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            itemAccountingCategoryDescription = ItemAccountingCategoryDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemAccountingCategoryDescription;
    }
    
    public ItemAccountingCategoryDescription getItemAccountingCategoryDescription(ItemAccountingCategory itemAccountingCategory,
            Language language) {
        return getItemAccountingCategoryDescription(itemAccountingCategory, language, EntityPermission.READ_ONLY);
    }
    
    public ItemAccountingCategoryDescription getItemAccountingCategoryDescriptionForUpdate(ItemAccountingCategory itemAccountingCategory,
            Language language) {
        return getItemAccountingCategoryDescription(itemAccountingCategory, language, EntityPermission.READ_WRITE);
    }
    
    public ItemAccountingCategoryDescriptionValue getItemAccountingCategoryDescriptionValue(ItemAccountingCategoryDescription itemAccountingCategoryDescription) {
        return itemAccountingCategoryDescription == null? null: itemAccountingCategoryDescription.getItemAccountingCategoryDescriptionValue().clone();
    }
    
    public ItemAccountingCategoryDescriptionValue getItemAccountingCategoryDescriptionValueForUpdate(ItemAccountingCategory itemAccountingCategory, Language language) {
        return getItemAccountingCategoryDescriptionValue(getItemAccountingCategoryDescriptionForUpdate(itemAccountingCategory, language));
    }
    
    private List<ItemAccountingCategoryDescription> getItemAccountingCategoryDescriptionsByItemAccountingCategory(ItemAccountingCategory itemAccountingCategory,
            EntityPermission entityPermission) {
        List<ItemAccountingCategoryDescription> itemAccountingCategoryDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaccountingcategorydescriptions, languages " +
                        "WHERE iactgcd_iactgc_itemaccountingcategoryid = ? AND iactgcd_thrutime = ? AND iactgcd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaccountingcategorydescriptions " +
                        "WHERE iactgcd_iactgc_itemaccountingcategoryid = ? AND iactgcd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemAccountingCategoryDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, itemAccountingCategory.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemAccountingCategoryDescriptions = ItemAccountingCategoryDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemAccountingCategoryDescriptions;
    }
    
    public List<ItemAccountingCategoryDescription> getItemAccountingCategoryDescriptionsByItemAccountingCategory(ItemAccountingCategory itemAccountingCategory) {
        return getItemAccountingCategoryDescriptionsByItemAccountingCategory(itemAccountingCategory, EntityPermission.READ_ONLY);
    }
    
    public List<ItemAccountingCategoryDescription> getItemAccountingCategoryDescriptionsByItemAccountingCategoryForUpdate(ItemAccountingCategory itemAccountingCategory) {
        return getItemAccountingCategoryDescriptionsByItemAccountingCategory(itemAccountingCategory, EntityPermission.READ_WRITE);
    }
    
    public String getBestItemAccountingCategoryDescription(ItemAccountingCategory itemAccountingCategory, Language language) {
        String description;
        var itemAccountingCategoryDescription = getItemAccountingCategoryDescription(itemAccountingCategory, language);
        
        if(itemAccountingCategoryDescription == null && !language.getIsDefault()) {
            itemAccountingCategoryDescription = getItemAccountingCategoryDescription(itemAccountingCategory, getPartyControl().getDefaultLanguage());
        }
        
        if(itemAccountingCategoryDescription == null) {
            description = itemAccountingCategory.getLastDetail().getItemAccountingCategoryName();
        } else {
            description = itemAccountingCategoryDescription.getDescription();
        }
        
        return description;
    }
    
    public ItemAccountingCategoryDescriptionTransfer getItemAccountingCategoryDescriptionTransfer(UserVisit userVisit,
            ItemAccountingCategoryDescription itemAccountingCategoryDescription) {
        return getAccountingTransferCaches(userVisit).getItemAccountingCategoryDescriptionTransferCache().getTransfer(itemAccountingCategoryDescription);
    }
    
    public List<ItemAccountingCategoryDescriptionTransfer> getItemAccountingCategoryDescriptionTransfersByItemAccountingCategory(UserVisit userVisit,
            ItemAccountingCategory itemAccountingCategory) {
        var itemAccountingCategoryDescriptions = getItemAccountingCategoryDescriptionsByItemAccountingCategory(itemAccountingCategory);
        List<ItemAccountingCategoryDescriptionTransfer> itemAccountingCategoryDescriptionTransfers = new ArrayList<>(itemAccountingCategoryDescriptions.size());
        var itemAccountingCategoryDescriptionTransferCache = getAccountingTransferCaches(userVisit).getItemAccountingCategoryDescriptionTransferCache();
        
        itemAccountingCategoryDescriptions.forEach((itemAccountingCategoryDescription) ->
                itemAccountingCategoryDescriptionTransfers.add(itemAccountingCategoryDescriptionTransferCache.getTransfer(itemAccountingCategoryDescription))
        );
        
        return itemAccountingCategoryDescriptionTransfers;
    }
    
    public void updateItemAccountingCategoryDescriptionFromValue(ItemAccountingCategoryDescriptionValue itemAccountingCategoryDescriptionValue,
            BasePK updatedBy) {
        if(itemAccountingCategoryDescriptionValue.hasBeenModified()) {
            var itemAccountingCategoryDescription = ItemAccountingCategoryDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, itemAccountingCategoryDescriptionValue.getPrimaryKey());
            
            itemAccountingCategoryDescription.setThruTime(session.START_TIME_LONG);
            itemAccountingCategoryDescription.store();

            var itemAccountingCategory = itemAccountingCategoryDescription.getItemAccountingCategory();
            var language = itemAccountingCategoryDescription.getLanguage();
            var description = itemAccountingCategoryDescriptionValue.getDescription();
            
            itemAccountingCategoryDescription = ItemAccountingCategoryDescriptionFactory.getInstance().create(itemAccountingCategory, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(itemAccountingCategory.getPrimaryKey(), EventTypes.MODIFY, itemAccountingCategoryDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteItemAccountingCategoryDescription(ItemAccountingCategoryDescription itemAccountingCategoryDescription,
            BasePK deletedBy) {
        itemAccountingCategoryDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(itemAccountingCategoryDescription.getItemAccountingCategoryPK(), EventTypes.MODIFY, itemAccountingCategoryDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteItemAccountingCategoryDescriptionsByItemAccountingCategory(ItemAccountingCategory itemAccountingCategory,
            BasePK deletedBy) {
        var itemAccountingCategoryDescriptions = getItemAccountingCategoryDescriptionsByItemAccountingCategoryForUpdate(itemAccountingCategory);
        
        itemAccountingCategoryDescriptions.forEach((itemAccountingCategoryDescription) -> 
                deleteItemAccountingCategoryDescription(itemAccountingCategoryDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Types
    // --------------------------------------------------------------------------------
    
    public GlAccountType createGlAccountType(String glAccountTypeName, Boolean isDefault, Integer sortOrder) {
        return GlAccountTypeFactory.getInstance().create(glAccountTypeName, isDefault, sortOrder);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.GlAccountType */
    public GlAccountType getGlAccountTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new GlAccountTypePK(entityInstance.getEntityUniqueId());

        return GlAccountTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public GlAccountType getGlAccountTypeByEntityInstance(EntityInstance entityInstance) {
        return getGlAccountTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public GlAccountType getGlAccountTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getGlAccountTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countGlAccountTypes() {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM glaccounttypes
                        """);
    }

    public GlAccountType getGlAccountTypeByName(String glAccountTypeName) {
        GlAccountType glAccountType;

        try {
            var ps = GlAccountTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM glaccounttypes " +
                    "WHERE glatyp_glaccounttypename = ?");

            ps.setString(1, glAccountTypeName);

            glAccountType = GlAccountTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return glAccountType;
    }

    public GlAccountType getDefaultGlAccountType() {
        var ps = GlAccountTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM glaccounttypes " +
                "WHERE glatyp_isdefault = 1");

        return GlAccountTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
    }

    public List<GlAccountType> getGlAccountTypes() {
        var ps = GlAccountTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM glaccounttypes " +
                "ORDER BY glatyp_sortorder, glatyp_glaccounttypename " +
                "_LIMIT_");
        
        return GlAccountTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public GlAccountTypeChoicesBean getGlAccountTypeChoices(String defaultGlAccountTypeChoice, Language language, boolean allowNullChoice) {
        var glAccountTypes = getGlAccountTypes();
        var size = glAccountTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultGlAccountTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var glAccountType : glAccountTypes) {
            var label = getBestGlAccountTypeDescription(glAccountType, language);
            var value = glAccountType.getGlAccountTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultGlAccountTypeChoice != null && defaultGlAccountTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && glAccountType.getIsDefault()))
                defaultValue = value;
        }
        
        return new GlAccountTypeChoicesBean(labels, values, defaultValue);
    }
    
    public GlAccountTypeTransfer getGlAccountTypeTransfer(UserVisit userVisit, GlAccountType glAccountType) {
        return getAccountingTransferCaches(userVisit).getGlAccountTypeTransferCache().getTransfer(glAccountType);
    }

    public List<GlAccountTypeTransfer> getGlAccountTypeTransfers(UserVisit userVisit, Collection<GlAccountType> glAccountTypes) {
        List<GlAccountTypeTransfer> glAccountTypeTransfers = new ArrayList<>(glAccountTypes.size());
        var glAccountTypeTransferCache = getAccountingTransferCaches(userVisit).getGlAccountTypeTransferCache();

        glAccountTypes.forEach((glAccountType) ->
                glAccountTypeTransfers.add(glAccountTypeTransferCache.getTransfer(glAccountType))
        );

        return glAccountTypeTransfers;
    }

    public List<GlAccountTypeTransfer> getGlAccountTypeTransfers(UserVisit userVisit) {
        return getGlAccountTypeTransfers(userVisit, getGlAccountTypes());
    }

    // --------------------------------------------------------------------------------
    //   Gl Account Type Descriptions
    // --------------------------------------------------------------------------------
    
    public GlAccountTypeDescription createGlAccountTypeDescription(GlAccountType glAccountType, Language language, String description) {
        return GlAccountTypeDescriptionFactory.getInstance().create(glAccountType, language, description);
    }
    
    public GlAccountTypeDescription getGlAccountTypeDescription(GlAccountType glAccountType, Language language) {
        GlAccountTypeDescription glAccountTypeDescription;
        
        try {
            var ps = GlAccountTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM glaccounttypedescriptions " +
                    "WHERE glatypd_glatyp_glaccounttypeid = ? AND glatypd_lang_languageid = ?");
            
            ps.setLong(1, glAccountType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            glAccountTypeDescription = GlAccountTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glAccountTypeDescription;
    }
    
    public String getBestGlAccountTypeDescription(GlAccountType glAccountType, Language language) {
        String description;
        var glAccountTypeDescription = getGlAccountTypeDescription(glAccountType, language);
        
        if(glAccountTypeDescription == null && !language.getIsDefault()) {
            glAccountTypeDescription = getGlAccountTypeDescription(glAccountType, getPartyControl().getDefaultLanguage());
        }
        
        if(glAccountTypeDescription == null) {
            description = glAccountType.getGlAccountTypeName();
        } else {
            description = glAccountTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Classes
    // --------------------------------------------------------------------------------
    
    public GlAccountClass createGlAccountClass(String glAccountClassName, GlAccountClass parentGlAccountClass, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultGlAccountClass = getDefaultGlAccountClass();
        var defaultFound = defaultGlAccountClass != null;
        
        if(defaultFound && isDefault) {
            var defaultGlAccountClassDetailValue = getDefaultGlAccountClassDetailValueForUpdate();
            
            defaultGlAccountClassDetailValue.setIsDefault(Boolean.FALSE);
            updateGlAccountClassFromValue(defaultGlAccountClassDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        var glAccountClass = GlAccountClassFactory.getInstance().create();
        var glAccountClassDetail = GlAccountClassDetailFactory.getInstance().create(session,
                glAccountClass, glAccountClassName, parentGlAccountClass, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        glAccountClass = GlAccountClassFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, glAccountClass.getPrimaryKey());
        glAccountClass.setActiveDetail(glAccountClassDetail);
        glAccountClass.setLastDetail(glAccountClassDetail);
        glAccountClass.store();
        
        sendEvent(glAccountClass.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return glAccountClass;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.GlAccountClass */
    public GlAccountClass getGlAccountClassByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new GlAccountClassPK(entityInstance.getEntityUniqueId());

        return GlAccountClassFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public GlAccountClass getGlAccountClassByEntityInstance(EntityInstance entityInstance) {
        return getGlAccountClassByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public GlAccountClass getGlAccountClassByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getGlAccountClassByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countGlAccountClasses() {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM glaccountclasses, glaccountclassdetails
                WHERE glacls_activedetailid = glaclsdt_glaccountclassdetailid
                """);
    }

    private static final Map<EntityPermission, String> getGlAccountClassByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM glaccountclasses, glaccountclassdetails " +
                "WHERE glacls_activedetailid = glaclsdt_glaccountclassdetailid " +
                "AND glaclsdt_glaccountclassname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM glaccountclasses, glaccountclassdetails " +
                "WHERE glacls_activedetailid = glaclsdt_glaccountclassdetailid " +
                "AND glaclsdt_glaccountclassname = ? " +
                "FOR UPDATE");
        getGlAccountClassByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public GlAccountClass getGlAccountClassByName(String glAccountClassName, EntityPermission entityPermission) {
        return GlAccountClassFactory.getInstance().getEntityFromQuery(entityPermission, getGlAccountClassByNameQueries, glAccountClassName);
    }

    public GlAccountClass getGlAccountClassByName(String glAccountClassName) {
        return getGlAccountClassByName(glAccountClassName, EntityPermission.READ_ONLY);
    }

    public GlAccountClass getGlAccountClassByNameForUpdate(String glAccountClassName) {
        return getGlAccountClassByName(glAccountClassName, EntityPermission.READ_WRITE);
    }

    public GlAccountClassDetailValue getGlAccountClassDetailValueForUpdate(GlAccountClass glAccountClass) {
        return glAccountClass == null? null: glAccountClass.getLastDetailForUpdate().getGlAccountClassDetailValue().clone();
    }

    public GlAccountClassDetailValue getGlAccountClassDetailValueByNameForUpdate(String glAccountClassName) {
        return getGlAccountClassDetailValueForUpdate(getGlAccountClassByNameForUpdate(glAccountClassName));
    }

    private static final Map<EntityPermission, String> getDefaultGlAccountClassQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM glaccountclasses, glaccountclassdetails " +
                "WHERE glacls_activedetailid = glaclsdt_glaccountclassdetailid " +
                "AND glaclsdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM glaccountclasses, glaccountclassdetails " +
                "WHERE glacls_activedetailid = glaclsdt_glaccountclassdetailid " +
                "AND glaclsdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultGlAccountClassQueries = Collections.unmodifiableMap(queryMap);
    }

    public GlAccountClass getDefaultGlAccountClass(EntityPermission entityPermission) {
        return GlAccountClassFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultGlAccountClassQueries);
    }

    public GlAccountClass getDefaultGlAccountClass() {
        return getDefaultGlAccountClass(EntityPermission.READ_ONLY);
    }

    public GlAccountClass getDefaultGlAccountClassForUpdate() {
        return getDefaultGlAccountClass(EntityPermission.READ_WRITE);
    }

    public GlAccountClassDetailValue getDefaultGlAccountClassDetailValueForUpdate() {
        return getDefaultGlAccountClassForUpdate().getLastDetailForUpdate().getGlAccountClassDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getGlAccountClassesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM glaccountclasses, glaccountclassdetails " +
                "WHERE glacls_activedetailid = glaclsdt_glaccountclassdetailid " +
                "ORDER BY glaclsdt_sortorder, glaclsdt_glaccountclassname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM glaccountclasses, glaccountclassdetails " +
                "WHERE glacls_activedetailid = glaclsdt_glaccountclassdetailid " +
                "FOR UPDATE");
        getGlAccountClassesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<GlAccountClass> getGlAccountClasses(EntityPermission entityPermission) {
        return GlAccountClassFactory.getInstance().getEntitiesFromQuery(entityPermission, getGlAccountClassesQueries);
    }

    public List<GlAccountClass> getGlAccountClasses() {
        return getGlAccountClasses(EntityPermission.READ_ONLY);
    }

    public List<GlAccountClass> getGlAccountClassesForUpdate() {
        return getGlAccountClasses(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getGlAccountClassesByParentGlAccountClassQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM glaccountclasses, glaccountclassdetails " +
                "WHERE glacls_activedetailid = glaclsdt_glaccountclassdetailid AND glaclsdt_parentglaccountclassid = ? " +
                "ORDER BY glaclsdt_sortorder, glaclsdt_glaccountclassname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM glaccountclasses, glaccountclassdetails " +
                "WHERE glacls_activedetailid = glaclsdt_glaccountclassdetailid AND glaclsdt_parentglaccountclassid = ? " +
                "FOR UPDATE");
        getGlAccountClassesByParentGlAccountClassQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<GlAccountClass> getGlAccountClassesByParentGlAccountClass(GlAccountClass parentGlAccountClass,
            EntityPermission entityPermission) {
        return GlAccountClassFactory.getInstance().getEntitiesFromQuery(entityPermission, getGlAccountClassesByParentGlAccountClassQueries,
                parentGlAccountClass);
    }

    public List<GlAccountClass> getGlAccountClassesByParentGlAccountClass(GlAccountClass parentGlAccountClass) {
        return getGlAccountClassesByParentGlAccountClass(parentGlAccountClass, EntityPermission.READ_ONLY);
    }

    public List<GlAccountClass> getGlAccountClassesByParentGlAccountClassForUpdate(GlAccountClass parentGlAccountClass) {
        return getGlAccountClassesByParentGlAccountClass(parentGlAccountClass, EntityPermission.READ_WRITE);
    }

    public GlAccountClassTransfer getGlAccountClassTransfer(UserVisit userVisit, GlAccountClass glAccountClass) {
        return getAccountingTransferCaches(userVisit).getGlAccountClassTransferCache().getTransfer(glAccountClass);
    }

    public List<GlAccountClassTransfer> getGlAccountClassTransfers(UserVisit userVisit, Collection<GlAccountClass> glAccountClasses) {
        List<GlAccountClassTransfer> glAccountClassTransfers = new ArrayList<>(glAccountClasses.size());
        var glAccountClassTransferCache = getAccountingTransferCaches(userVisit).getGlAccountClassTransferCache();

        glAccountClasses.forEach((glAccountClass) ->
                glAccountClassTransfers.add(glAccountClassTransferCache.getTransfer(glAccountClass))
        );

        return glAccountClassTransfers;
    }

    public List<GlAccountClassTransfer> getGlAccountClassTransfers(UserVisit userVisit) {
        return getGlAccountClassTransfers(userVisit, getGlAccountClasses());
    }

    public GlAccountClassChoicesBean getGlAccountClassChoices(String defaultGlAccountClassChoice, Language language,
            boolean allowNullChoice) {
        var glAccountClasses = getGlAccountClasses();
        var size = glAccountClasses.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultGlAccountClassChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var glAccountClass : glAccountClasses) {
            var glAccountClassDetail = glAccountClass.getLastDetail();
            
            var label = getBestGlAccountClassDescription(glAccountClass, language);
            var value = glAccountClassDetail.getGlAccountClassName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultGlAccountClassChoice != null && defaultGlAccountClassChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && glAccountClassDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new GlAccountClassChoicesBean(labels, values, defaultValue);
    }
    
    public boolean isParentGlAccountClassSafe(GlAccountClass glAccountClass, GlAccountClass parentGlAccountClass) {
        var safe = true;
        
        if(parentGlAccountClass != null) {
            Set<GlAccountClass> parentItemPurchasingCategories = new HashSet<>();
            
            parentItemPurchasingCategories.add(glAccountClass);
            do {
                if(parentItemPurchasingCategories.contains(parentGlAccountClass)) {
                    safe = false;
                    break;
                }
                
                parentItemPurchasingCategories.add(parentGlAccountClass);
                parentGlAccountClass = parentGlAccountClass.getLastDetail().getParentGlAccountClass();
            } while(parentGlAccountClass != null);
        }
        
        return safe;
    }
    
    private void updateGlAccountClassFromValue(GlAccountClassDetailValue glAccountClassDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(glAccountClassDetailValue.hasBeenModified()) {
            var glAccountClass = GlAccountClassFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     glAccountClassDetailValue.getGlAccountClassPK());
            var glAccountClassDetail = glAccountClass.getActiveDetailForUpdate();
            
            glAccountClassDetail.setThruTime(session.START_TIME_LONG);
            glAccountClassDetail.store();

            var glAccountClassPK = glAccountClassDetail.getGlAccountClassPK();
            var glAccountClassName = glAccountClassDetailValue.getGlAccountClassName();
            var parentGlAccountClassPK = glAccountClassDetailValue.getParentGlAccountClassPK();
            var isDefault = glAccountClassDetailValue.getIsDefault();
            var sortOrder = glAccountClassDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultGlAccountClass = getDefaultGlAccountClass();
                var defaultFound = defaultGlAccountClass != null && !defaultGlAccountClass.equals(glAccountClass);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultGlAccountClassDetailValue = getDefaultGlAccountClassDetailValueForUpdate();
                    
                    defaultGlAccountClassDetailValue.setIsDefault(Boolean.FALSE);
                    updateGlAccountClassFromValue(defaultGlAccountClassDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            glAccountClassDetail = GlAccountClassDetailFactory.getInstance().create(glAccountClassPK,
                    glAccountClassName, parentGlAccountClassPK, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            glAccountClass.setActiveDetail(glAccountClassDetail);
            glAccountClass.setLastDetail(glAccountClassDetail);
            
            sendEvent(glAccountClassPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateGlAccountClassFromValue(GlAccountClassDetailValue glAccountClassDetailValue, BasePK updatedBy) {
        updateGlAccountClassFromValue(glAccountClassDetailValue, true, updatedBy);
    }
    
    private void deleteGlAccountClass(GlAccountClass glAccountClass, boolean checkDefault, BasePK deletedBy) {
        var glAccountClassDetail = glAccountClass.getLastDetailForUpdate();

        deleteGlAccountClassesByParentGlAccountClass(glAccountClass, deletedBy);
        deleteGlAccountsByGlAccountClass(glAccountClass, deletedBy);
        deleteGlAccountClassDescriptionsByGlAccountClass(glAccountClass, deletedBy);
        
        glAccountClassDetail.setThruTime(session.START_TIME_LONG);
        glAccountClass.setActiveDetail(null);
        glAccountClass.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultGlAccountClass = getDefaultGlAccountClass();

            if(defaultGlAccountClass == null) {
                var glAccountClasses = getGlAccountClassesForUpdate();

                if(!glAccountClasses.isEmpty()) {
                    var iter = glAccountClasses.iterator();
                    if(iter.hasNext()) {
                        defaultGlAccountClass = iter.next();
                    }
                    var glAccountClassDetailValue = Objects.requireNonNull(defaultGlAccountClass).getLastDetailForUpdate().getGlAccountClassDetailValue().clone();

                    glAccountClassDetailValue.setIsDefault(Boolean.TRUE);
                    updateGlAccountClassFromValue(glAccountClassDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(glAccountClass.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteGlAccountClass(GlAccountClass glAccountClass, BasePK deletedBy) {
        deleteGlAccountClass(glAccountClass, true, deletedBy);
    }

    private void deleteGlAccountClasses(List<GlAccountClass> glAccountClasses, boolean checkDefault, BasePK deletedBy) {
        glAccountClasses.forEach((glAccountClass) -> deleteGlAccountClass(glAccountClass, checkDefault, deletedBy));
    }

    public void deleteGlAccountClasses(List<GlAccountClass> glAccountClasses, BasePK deletedBy) {
        deleteGlAccountClasses(glAccountClasses, true, deletedBy);
    }

    private void deleteGlAccountClassesByParentGlAccountClass(GlAccountClass parentGlAccountClass, BasePK deletedBy) {
        deleteGlAccountClasses(getGlAccountClassesByParentGlAccountClassForUpdate(parentGlAccountClass), false, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Gl Account Class Descriptions
    // --------------------------------------------------------------------------------
    
    public GlAccountClassDescription createGlAccountClassDescription(GlAccountClass glAccountClass, Language language, String description, BasePK createdBy) {
        var glAccountClassDescription = GlAccountClassDescriptionFactory.getInstance().create(glAccountClass, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEvent(glAccountClass.getPrimaryKey(), EventTypes.MODIFY, glAccountClassDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return glAccountClassDescription;
    }
    
    private GlAccountClassDescription getGlAccountClassDescription(GlAccountClass glAccountClass, Language language, EntityPermission entityPermission) {
        GlAccountClassDescription glAccountClassDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccountclassdescriptions " +
                        "WHERE glaclsd_glacls_glaccountclassid = ? AND glaclsd_lang_languageid = ? AND glaclsd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccountclassdescriptions " +
                        "WHERE glaclsd_glacls_glaccountclassid = ? AND glaclsd_lang_languageid = ? AND glaclsd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GlAccountClassDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, glAccountClass.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            glAccountClassDescription = GlAccountClassDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glAccountClassDescription;
    }
    
    public GlAccountClassDescription getGlAccountClassDescription(GlAccountClass glAccountClass, Language language) {
        return getGlAccountClassDescription(glAccountClass, language, EntityPermission.READ_ONLY);
    }
    
    public GlAccountClassDescription getGlAccountClassDescriptionForUpdate(GlAccountClass glAccountClass, Language language) {
        return getGlAccountClassDescription(glAccountClass, language, EntityPermission.READ_WRITE);
    }
    
    public GlAccountClassDescriptionValue getGlAccountClassDescriptionValue(GlAccountClassDescription glAccountClassDescription) {
        return glAccountClassDescription == null? null: glAccountClassDescription.getGlAccountClassDescriptionValue().clone();
    }
    
    public GlAccountClassDescriptionValue getGlAccountClassDescriptionValueForUpdate(GlAccountClass glAccountClass, Language language) {
        return getGlAccountClassDescriptionValue(getGlAccountClassDescriptionForUpdate(glAccountClass, language));
    }
    
    private List<GlAccountClassDescription> getGlAccountClassDescriptionsByGlAccountClass(GlAccountClass glAccountClass, EntityPermission entityPermission) {
        List<GlAccountClassDescription> glAccountClassDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccountclassdescriptions, languages " +
                        "WHERE glaclsd_glacls_glaccountclassid = ? AND glaclsd_thrutime = ? AND glaclsd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccountclassdescriptions " +
                        "WHERE glaclsd_glacls_glaccountclassid = ? AND glaclsd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GlAccountClassDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, glAccountClass.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            glAccountClassDescriptions = GlAccountClassDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glAccountClassDescriptions;
    }
    
    public List<GlAccountClassDescription> getGlAccountClassDescriptionsByGlAccountClass(GlAccountClass glAccountClass) {
        return getGlAccountClassDescriptionsByGlAccountClass(glAccountClass, EntityPermission.READ_ONLY);
    }
    
    public List<GlAccountClassDescription> getGlAccountClassDescriptionsByGlAccountClassForUpdate(GlAccountClass glAccountClass) {
        return getGlAccountClassDescriptionsByGlAccountClass(glAccountClass, EntityPermission.READ_WRITE);
    }
    
    public String getBestGlAccountClassDescription(GlAccountClass glAccountClass, Language language) {
        String description;
        var glAccountClassDescription = getGlAccountClassDescription(glAccountClass, language);
        
        if(glAccountClassDescription == null && !language.getIsDefault()) {
            glAccountClassDescription = getGlAccountClassDescription(glAccountClass, getPartyControl().getDefaultLanguage());
        }
        
        if(glAccountClassDescription == null) {
            description = glAccountClass.getLastDetail().getGlAccountClassName();
        } else {
            description = glAccountClassDescription.getDescription();
        }
        
        return description;
    }
    
    public GlAccountClassDescriptionTransfer getGlAccountClassDescriptionTransfer(UserVisit userVisit, GlAccountClassDescription glAccountClassDescription) {
        return getAccountingTransferCaches(userVisit).getGlAccountClassDescriptionTransferCache().getTransfer(glAccountClassDescription);
    }
    
    public List<GlAccountClassDescriptionTransfer> getGlAccountClassDescriptionTransfersByGlAccountClass(UserVisit userVisit, GlAccountClass glAccountClass) {
        var glAccountClassDescriptions = getGlAccountClassDescriptionsByGlAccountClass(glAccountClass);
        List<GlAccountClassDescriptionTransfer> glAccountClassDescriptionTransfers = new ArrayList<>(glAccountClassDescriptions.size());
        var glAccountClassDescriptionTransferCache = getAccountingTransferCaches(userVisit).getGlAccountClassDescriptionTransferCache();
        
        glAccountClassDescriptions.forEach((glAccountClassDescription) ->
                glAccountClassDescriptionTransfers.add(glAccountClassDescriptionTransferCache.getTransfer(glAccountClassDescription))
        );
        
        return glAccountClassDescriptionTransfers;
    }
    
    public void updateGlAccountClassDescriptionFromValue(GlAccountClassDescriptionValue glAccountClassDescriptionValue, BasePK updatedBy) {
        if(glAccountClassDescriptionValue.hasBeenModified()) {
            var glAccountClassDescription = GlAccountClassDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, glAccountClassDescriptionValue.getPrimaryKey());
            
            glAccountClassDescription.setThruTime(session.START_TIME_LONG);
            glAccountClassDescription.store();

            var glAccountClass = glAccountClassDescription.getGlAccountClass();
            var language = glAccountClassDescription.getLanguage();
            var description = glAccountClassDescriptionValue.getDescription();
            
            glAccountClassDescription = GlAccountClassDescriptionFactory.getInstance().create(glAccountClass, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(glAccountClass.getPrimaryKey(), EventTypes.MODIFY, glAccountClassDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteGlAccountClassDescription(GlAccountClassDescription glAccountClassDescription, BasePK deletedBy) {
        glAccountClassDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(glAccountClassDescription.getGlAccountClassPK(), EventTypes.MODIFY, glAccountClassDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteGlAccountClassDescriptionsByGlAccountClass(GlAccountClass glAccountClass, BasePK deletedBy) {
        var glAccountClassDescriptions = getGlAccountClassDescriptionsByGlAccountClassForUpdate(glAccountClass);
        
        glAccountClassDescriptions.forEach((glAccountClassDescription) -> 
                deleteGlAccountClassDescription(glAccountClassDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Categories
    // --------------------------------------------------------------------------------
    
    public GlAccountCategory createGlAccountCategory(String glAccountCategoryName, GlAccountCategory parentGlAccountCategory, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultGlAccountCategory = getDefaultGlAccountCategory();
        var defaultFound = defaultGlAccountCategory != null;
        
        if(defaultFound && isDefault) {
            var defaultGlAccountCategoryDetailValue = getDefaultGlAccountCategoryDetailValueForUpdate();
            
            defaultGlAccountCategoryDetailValue.setIsDefault(Boolean.FALSE);
            updateGlAccountCategoryFromValue(defaultGlAccountCategoryDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        var glAccountCategory = GlAccountCategoryFactory.getInstance().create();
        var glAccountCategoryDetail = GlAccountCategoryDetailFactory.getInstance().create(session,
                glAccountCategory, glAccountCategoryName, parentGlAccountCategory, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        glAccountCategory = GlAccountCategoryFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                glAccountCategory.getPrimaryKey());
        glAccountCategory.setActiveDetail(glAccountCategoryDetail);
        glAccountCategory.setLastDetail(glAccountCategoryDetail);
        glAccountCategory.store();
        
        sendEvent(glAccountCategory.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return glAccountCategory;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.GlAccountCategory */
    public GlAccountCategory getGlAccountCategoryByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new GlAccountCategoryPK(entityInstance.getEntityUniqueId());

        return GlAccountCategoryFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public GlAccountCategory getGlAccountCategoryByEntityInstance(EntityInstance entityInstance) {
        return getGlAccountCategoryByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public GlAccountCategory getGlAccountCategoryByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getGlAccountCategoryByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countGlAccountCategories() {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM glaccountcategories, glaccountcategorydetails
                WHERE glac_activedetailid = glacdt_glaccountcategorydetailid
                """);
    }

    private static final Map<EntityPermission, String> getGlAccountCategoryByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM glaccountcategories, glaccountcategorydetails " +
                "WHERE glac_activedetailid = glacdt_glaccountcategorydetailid " +
                "AND glacdt_glaccountcategoryname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM glaccountcategories, glaccountcategorydetails " +
                "WHERE glac_activedetailid = glacdt_glaccountcategorydetailid " +
                "AND glacdt_glaccountcategoryname = ? " +
                "FOR UPDATE");
        getGlAccountCategoryByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public GlAccountCategory getGlAccountCategoryByName(String glAccountCategoryName, EntityPermission entityPermission) {
        return GlAccountCategoryFactory.getInstance().getEntityFromQuery(entityPermission, getGlAccountCategoryByNameQueries, glAccountCategoryName);
    }

    public GlAccountCategory getGlAccountCategoryByName(String glAccountCategoryName) {
        return getGlAccountCategoryByName(glAccountCategoryName, EntityPermission.READ_ONLY);
    }

    public GlAccountCategory getGlAccountCategoryByNameForUpdate(String glAccountCategoryName) {
        return getGlAccountCategoryByName(glAccountCategoryName, EntityPermission.READ_WRITE);
    }

    public GlAccountCategoryDetailValue getGlAccountCategoryDetailValueForUpdate(GlAccountCategory glAccountCategory) {
        return glAccountCategory == null? null: glAccountCategory.getLastDetailForUpdate().getGlAccountCategoryDetailValue().clone();
    }

    public GlAccountCategoryDetailValue getGlAccountCategoryDetailValueByNameForUpdate(String glAccountCategoryName) {
        return getGlAccountCategoryDetailValueForUpdate(getGlAccountCategoryByNameForUpdate(glAccountCategoryName));
    }

    private static final Map<EntityPermission, String> getDefaultGlAccountCategoryQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM glaccountcategories, glaccountcategorydetails " +
                "WHERE glac_activedetailid = glacdt_glaccountcategorydetailid " +
                "AND glacdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM glaccountcategories, glaccountcategorydetails " +
                "WHERE glac_activedetailid = glacdt_glaccountcategorydetailid " +
                "AND glacdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultGlAccountCategoryQueries = Collections.unmodifiableMap(queryMap);
    }

    public GlAccountCategory getDefaultGlAccountCategory(EntityPermission entityPermission) {
        return GlAccountCategoryFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultGlAccountCategoryQueries);
    }

    public GlAccountCategory getDefaultGlAccountCategory() {
        return getDefaultGlAccountCategory(EntityPermission.READ_ONLY);
    }

    public GlAccountCategory getDefaultGlAccountCategoryForUpdate() {
        return getDefaultGlAccountCategory(EntityPermission.READ_WRITE);
    }

    public GlAccountCategoryDetailValue getDefaultGlAccountCategoryDetailValueForUpdate() {
        return getDefaultGlAccountCategoryForUpdate().getLastDetailForUpdate().getGlAccountCategoryDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getGlAccountCategoriesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM glaccountcategories, glaccountcategorydetails " +
                "WHERE glac_activedetailid = glacdt_glaccountcategorydetailid " +
                "ORDER BY glacdt_sortorder, glacdt_glaccountcategoryname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM glaccountcategories, glaccountcategorydetails " +
                "WHERE glac_activedetailid = glacdt_glaccountcategorydetailid " +
                "FOR UPDATE");
        getGlAccountCategoriesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<GlAccountCategory> getGlAccountCategories(EntityPermission entityPermission) {
        return GlAccountCategoryFactory.getInstance().getEntitiesFromQuery(entityPermission, getGlAccountCategoriesQueries);
    }

    public List<GlAccountCategory> getGlAccountCategories() {
        return getGlAccountCategories(EntityPermission.READ_ONLY);
    }

    public List<GlAccountCategory> getGlAccountCategoriesForUpdate() {
        return getGlAccountCategories(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getGlAccountCategoriesByParentGlAccountCategoryQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM glaccountcategories, glaccountcategorydetails " +
                "WHERE glac_activedetailid = glacdt_glaccountcategorydetailid AND glacdt_parentglaccountcategoryid = ? " +
                "ORDER BY glacdt_sortorder, glacdt_glaccountcategoryname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM glaccountcategories, glaccountcategorydetails " +
                "WHERE glac_activedetailid = glacdt_glaccountcategorydetailid AND glacdt_parentglaccountcategoryid = ? " +
                "FOR UPDATE");
        getGlAccountCategoriesByParentGlAccountCategoryQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<GlAccountCategory> getGlAccountCategoriesByParentGlAccountCategory(GlAccountCategory parentGlAccountCategory,
            EntityPermission entityPermission) {
        return GlAccountCategoryFactory.getInstance().getEntitiesFromQuery(entityPermission, getGlAccountCategoriesByParentGlAccountCategoryQueries,
                parentGlAccountCategory);
    }

    public List<GlAccountCategory> getGlAccountCategoriesByParentGlAccountCategory(GlAccountCategory parentGlAccountCategory) {
        return getGlAccountCategoriesByParentGlAccountCategory(parentGlAccountCategory, EntityPermission.READ_ONLY);
    }

    public List<GlAccountCategory> getGlAccountCategoriesByParentGlAccountCategoryForUpdate(GlAccountCategory parentGlAccountCategory) {
        return getGlAccountCategoriesByParentGlAccountCategory(parentGlAccountCategory, EntityPermission.READ_WRITE);
    }

    public GlAccountCategoryTransfer getGlAccountCategoryTransfer(UserVisit userVisit, GlAccountCategory glAccountCategory) {
        return getAccountingTransferCaches(userVisit).getGlAccountCategoryTransferCache().getTransfer(glAccountCategory);
    }

    public List<GlAccountCategoryTransfer> getGlAccountCategoryTransfers(UserVisit userVisit, Collection<GlAccountCategory> glAccountCategories) {
        List<GlAccountCategoryTransfer> glAccountCategoryTransfers = new ArrayList<>(glAccountCategories.size());
        var glAccountCategoryTransferCache = getAccountingTransferCaches(userVisit).getGlAccountCategoryTransferCache();

        glAccountCategories.forEach((glAccountCategory) ->
                glAccountCategoryTransfers.add(glAccountCategoryTransferCache.getTransfer(glAccountCategory))
        );

        return glAccountCategoryTransfers;
    }

    public List<GlAccountCategoryTransfer> getGlAccountCategoryTransfers(UserVisit userVisit) {
        return getGlAccountCategoryTransfers(userVisit, getGlAccountCategories());
    }

    public GlAccountCategoryChoicesBean getGlAccountCategoryChoices(String defaultGlAccountCategoryChoice, Language language,
            boolean allowNullChoice) {
        var glAccountCategories = getGlAccountCategories();
        var size = glAccountCategories.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultGlAccountCategoryChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var glAccountCategory : glAccountCategories) {
            var glAccountCategoryDetail = glAccountCategory.getLastDetail();
            
            var label = getBestGlAccountCategoryDescription(glAccountCategory, language);
            var value = glAccountCategoryDetail.getGlAccountCategoryName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultGlAccountCategoryChoice != null && defaultGlAccountCategoryChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && glAccountCategoryDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new GlAccountCategoryChoicesBean(labels, values, defaultValue);
    }
    
    public boolean isParentGlAccountCategorySafe(GlAccountCategory glAccountCategory, GlAccountCategory parentGlAccountCategory) {
        var safe = true;
        
        if(parentGlAccountCategory != null) {
            Set<GlAccountCategory> parentItemPurchasingCategories = new HashSet<>();
            
            parentItemPurchasingCategories.add(glAccountCategory);
            do {
                if(parentItemPurchasingCategories.contains(parentGlAccountCategory)) {
                    safe = false;
                    break;
                }
                
                parentItemPurchasingCategories.add(parentGlAccountCategory);
                parentGlAccountCategory = parentGlAccountCategory.getLastDetail().getParentGlAccountCategory();
            } while(parentGlAccountCategory != null);
        }
        
        return safe;
    }
    
    private void updateGlAccountCategoryFromValue(GlAccountCategoryDetailValue glAccountCategoryDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(glAccountCategoryDetailValue.hasBeenModified()) {
            var glAccountCategory = GlAccountCategoryFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     glAccountCategoryDetailValue.getGlAccountCategoryPK());
            var glAccountCategoryDetail = glAccountCategory.getActiveDetailForUpdate();
            
            glAccountCategoryDetail.setThruTime(session.START_TIME_LONG);
            glAccountCategoryDetail.store();

            var glAccountCategoryPK = glAccountCategoryDetail.getGlAccountCategoryPK();
            var glAccountCategoryName = glAccountCategoryDetailValue.getGlAccountCategoryName();
            var parentGlAccountCategoryPK = glAccountCategoryDetailValue.getParentGlAccountCategoryPK();
            var isDefault = glAccountCategoryDetailValue.getIsDefault();
            var sortOrder = glAccountCategoryDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultGlAccountCategory = getDefaultGlAccountCategory();
                var defaultFound = defaultGlAccountCategory != null && !defaultGlAccountCategory.equals(glAccountCategory);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultGlAccountCategoryDetailValue = getDefaultGlAccountCategoryDetailValueForUpdate();
                    
                    defaultGlAccountCategoryDetailValue.setIsDefault(Boolean.FALSE);
                    updateGlAccountCategoryFromValue(defaultGlAccountCategoryDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            glAccountCategoryDetail = GlAccountCategoryDetailFactory.getInstance().create(glAccountCategoryPK,
                    glAccountCategoryName, parentGlAccountCategoryPK, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            glAccountCategory.setActiveDetail(glAccountCategoryDetail);
            glAccountCategory.setLastDetail(glAccountCategoryDetail);
            
            sendEvent(glAccountCategoryPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateGlAccountCategoryFromValue(GlAccountCategoryDetailValue glAccountCategoryDetailValue, BasePK updatedBy) {
        updateGlAccountCategoryFromValue(glAccountCategoryDetailValue, true, updatedBy);
    }
    
    private void deleteGlAccountCategory(GlAccountCategory glAccountCategory, boolean checkDefault, BasePK deletedBy) {
        var glAccountCategoryDetail = glAccountCategory.getLastDetailForUpdate();
        
        deleteGlAccountCategoriesByParentGlAccountCategory(glAccountCategory, deletedBy);
        // TODO: deleteTransactionGlAccountCategoriesByGlAccountCategory(glAccountCategory, deletedBy);
        deleteGlAccountsByGlAccountCategory(glAccountCategory, deletedBy);
        deleteGlAccountCategoryDescriptionsByGlAccountCategory(glAccountCategory, deletedBy);
        
        glAccountCategoryDetail.setThruTime(session.START_TIME_LONG);
        glAccountCategory.setActiveDetail(null);
        glAccountCategory.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultGlAccountCategory = getDefaultGlAccountCategory();

            if(defaultGlAccountCategory == null) {
                var glAccountCategories = getGlAccountCategoriesForUpdate();

                if(!glAccountCategories.isEmpty()) {
                    var iter = glAccountCategories.iterator();
                    if(iter.hasNext()) {
                        defaultGlAccountCategory = iter.next();
                    }
                    var glAccountCategoryDetailValue = Objects.requireNonNull(defaultGlAccountCategory).getLastDetailForUpdate().getGlAccountCategoryDetailValue().clone();

                    glAccountCategoryDetailValue.setIsDefault(Boolean.TRUE);
                    updateGlAccountCategoryFromValue(glAccountCategoryDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(glAccountCategory.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteGlAccountCategory(GlAccountCategory glAccountCategory, BasePK deletedBy) {
        deleteGlAccountCategory(glAccountCategory, true, deletedBy);
    }

    private void deleteGlAccountCategories(List<GlAccountCategory> glAccountCategories, boolean checkDefault, BasePK deletedBy) {
        glAccountCategories.forEach((glAccountCategory) -> deleteGlAccountCategory(glAccountCategory, checkDefault, deletedBy));
    }

    public void deleteGlAccountCategories(List<GlAccountCategory> glAccountCategories, BasePK deletedBy) {
        deleteGlAccountCategories(glAccountCategories, true, deletedBy);
    }

    private void deleteGlAccountCategoriesByParentGlAccountCategory(GlAccountCategory parentGlAccountCategory, BasePK deletedBy) {
        deleteGlAccountCategories(getGlAccountCategoriesByParentGlAccountCategoryForUpdate(parentGlAccountCategory), false, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Gl Account Category Descriptions
    // --------------------------------------------------------------------------------
    
    public GlAccountCategoryDescription createGlAccountCategoryDescription(GlAccountCategory glAccountCategory, Language language, String description, BasePK createdBy) {
        var glAccountCategoryDescription = GlAccountCategoryDescriptionFactory.getInstance().create(glAccountCategory, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEvent(glAccountCategory.getPrimaryKey(), EventTypes.MODIFY, glAccountCategoryDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return glAccountCategoryDescription;
    }
    
    private GlAccountCategoryDescription getGlAccountCategoryDescription(GlAccountCategory glAccountCategory, Language language, EntityPermission entityPermission) {
        GlAccountCategoryDescription glAccountCategoryDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccountcategorydescriptions " +
                        "WHERE glacd_glac_glaccountcategoryid = ? AND glacd_lang_languageid = ? AND glacd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccountcategorydescriptions " +
                        "WHERE glacd_glac_glaccountcategoryid = ? AND glacd_lang_languageid = ? AND glacd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GlAccountCategoryDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, glAccountCategory.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            glAccountCategoryDescription = GlAccountCategoryDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glAccountCategoryDescription;
    }
    
    public GlAccountCategoryDescription getGlAccountCategoryDescription(GlAccountCategory glAccountCategory, Language language) {
        return getGlAccountCategoryDescription(glAccountCategory, language, EntityPermission.READ_ONLY);
    }
    
    public GlAccountCategoryDescription getGlAccountCategoryDescriptionForUpdate(GlAccountCategory glAccountCategory, Language language) {
        return getGlAccountCategoryDescription(glAccountCategory, language, EntityPermission.READ_WRITE);
    }
    
    public GlAccountCategoryDescriptionValue getGlAccountCategoryDescriptionValue(GlAccountCategoryDescription glAccountCategoryDescription) {
        return glAccountCategoryDescription == null? null: glAccountCategoryDescription.getGlAccountCategoryDescriptionValue().clone();
    }
    
    public GlAccountCategoryDescriptionValue getGlAccountCategoryDescriptionValueForUpdate(GlAccountCategory glAccountCategory, Language language) {
        return getGlAccountCategoryDescriptionValue(getGlAccountCategoryDescriptionForUpdate(glAccountCategory, language));
    }
    
    private List<GlAccountCategoryDescription> getGlAccountCategoryDescriptionsByGlAccountCategory(GlAccountCategory glAccountCategory, EntityPermission entityPermission) {
        List<GlAccountCategoryDescription> glAccountCategoryDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccountcategorydescriptions, languages " +
                        "WHERE glacd_glac_glaccountcategoryid = ? AND glacd_thrutime = ? AND glacd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccountcategorydescriptions " +
                        "WHERE glacd_glac_glaccountcategoryid = ? AND glacd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GlAccountCategoryDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, glAccountCategory.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            glAccountCategoryDescriptions = GlAccountCategoryDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glAccountCategoryDescriptions;
    }
    
    public List<GlAccountCategoryDescription> getGlAccountCategoryDescriptionsByGlAccountCategory(GlAccountCategory glAccountCategory) {
        return getGlAccountCategoryDescriptionsByGlAccountCategory(glAccountCategory, EntityPermission.READ_ONLY);
    }
    
    public List<GlAccountCategoryDescription> getGlAccountCategoryDescriptionsByGlAccountCategoryForUpdate(GlAccountCategory glAccountCategory) {
        return getGlAccountCategoryDescriptionsByGlAccountCategory(glAccountCategory, EntityPermission.READ_WRITE);
    }
    
    public String getBestGlAccountCategoryDescription(GlAccountCategory glAccountCategory, Language language) {
        String description;
        var glAccountCategoryDescription = getGlAccountCategoryDescription(glAccountCategory, language);
        
        if(glAccountCategoryDescription == null && !language.getIsDefault()) {
            glAccountCategoryDescription = getGlAccountCategoryDescription(glAccountCategory, getPartyControl().getDefaultLanguage());
        }
        
        if(glAccountCategoryDescription == null) {
            description = glAccountCategory.getLastDetail().getGlAccountCategoryName();
        } else {
            description = glAccountCategoryDescription.getDescription();
        }
        
        return description;
    }
    
    public GlAccountCategoryDescriptionTransfer getGlAccountCategoryDescriptionTransfer(UserVisit userVisit, GlAccountCategoryDescription glAccountCategoryDescription) {
        return getAccountingTransferCaches(userVisit).getGlAccountCategoryDescriptionTransferCache().getTransfer(glAccountCategoryDescription);
    }
    
    public List<GlAccountCategoryDescriptionTransfer> getGlAccountCategoryDescriptionTransfersByGlAccountCategory(UserVisit userVisit, GlAccountCategory glAccountCategory) {
        var glAccountCategoryDescriptions = getGlAccountCategoryDescriptionsByGlAccountCategory(glAccountCategory);
        List<GlAccountCategoryDescriptionTransfer> glAccountCategoryDescriptionTransfers = new ArrayList<>(glAccountCategoryDescriptions.size());
        var glAccountCategoryDescriptionTransferCache = getAccountingTransferCaches(userVisit).getGlAccountCategoryDescriptionTransferCache();
        
        glAccountCategoryDescriptions.forEach((glAccountCategoryDescription) ->
                glAccountCategoryDescriptionTransfers.add(glAccountCategoryDescriptionTransferCache.getTransfer(glAccountCategoryDescription))
        );
        
        return glAccountCategoryDescriptionTransfers;
    }
    
    public void updateGlAccountCategoryDescriptionFromValue(GlAccountCategoryDescriptionValue glAccountCategoryDescriptionValue, BasePK updatedBy) {
        if(glAccountCategoryDescriptionValue.hasBeenModified()) {
            var glAccountCategoryDescription = GlAccountCategoryDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, glAccountCategoryDescriptionValue.getPrimaryKey());
            
            glAccountCategoryDescription.setThruTime(session.START_TIME_LONG);
            glAccountCategoryDescription.store();

            var glAccountCategory = glAccountCategoryDescription.getGlAccountCategory();
            var language = glAccountCategoryDescription.getLanguage();
            var description = glAccountCategoryDescriptionValue.getDescription();
            
            glAccountCategoryDescription = GlAccountCategoryDescriptionFactory.getInstance().create(glAccountCategory, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(glAccountCategory.getPrimaryKey(), EventTypes.MODIFY, glAccountCategoryDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteGlAccountCategoryDescription(GlAccountCategoryDescription glAccountCategoryDescription, BasePK deletedBy) {
        glAccountCategoryDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(glAccountCategoryDescription.getGlAccountCategoryPK(), EventTypes.MODIFY, glAccountCategoryDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteGlAccountCategoryDescriptionsByGlAccountCategory(GlAccountCategory glAccountCategory, BasePK deletedBy) {
        var glAccountCategoryDescriptions = getGlAccountCategoryDescriptionsByGlAccountCategoryForUpdate(glAccountCategory);
        
        glAccountCategoryDescriptions.forEach((glAccountCategoryDescription) -> 
                deleteGlAccountCategoryDescription(glAccountCategoryDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Resource Type
    // --------------------------------------------------------------------------------
    
    public GlResourceType createGlResourceType(String glResourceTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultGlResourceType = getDefaultGlResourceType();
        var defaultFound = defaultGlResourceType != null;
        
        if(defaultFound && isDefault) {
            var defaultGlResourceTypeDetailValue = getDefaultGlResourceTypeDetailValueForUpdate();
            
            defaultGlResourceTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateGlResourceTypeFromValue(defaultGlResourceTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        var glResourceType = GlResourceTypeFactory.getInstance().create();
        var glResourceTypeDetail = GlResourceTypeDetailFactory.getInstance().create(session,
                glResourceType, glResourceTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        glResourceType = GlResourceTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, glResourceType.getPrimaryKey());
        glResourceType.setActiveDetail(glResourceTypeDetail);
        glResourceType.setLastDetail(glResourceTypeDetail);
        glResourceType.store();
        
        sendEvent(glResourceType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return glResourceType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.GlResourceType */
    public GlResourceType getGlResourceTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new GlResourceTypePK(entityInstance.getEntityUniqueId());

        return GlResourceTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public GlResourceType getGlResourceTypeByEntityInstance(EntityInstance entityInstance) {
        return getGlResourceTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public GlResourceType getGlResourceTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getGlResourceTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countGlResourceTypes() {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM glresourcetypes, glresourcetypedetails
                        WHERE glrtyp_activedetailid = glrtypdt_glresourcetypedetailid
                        """);
    }

    public GlResourceType getGlResourceTypeByName(String glResourceTypeName, EntityPermission entityPermission) {
        GlResourceType glResourceType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glresourcetypes, glresourcetypedetails " +
                        "WHERE glrtyp_glresourcetypeid = glrtypdt_glrtyp_glresourcetypeid AND glrtypdt_glresourcetypename = ? AND glrtypdt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glresourcetypes, glresourcetypedetails " +
                        "WHERE glrtyp_glresourcetypeid = glrtypdt_glrtyp_glresourcetypeid AND glrtypdt_glresourcetypename = ? AND glrtypdt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GlResourceTypeFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, glResourceTypeName);
            ps.setLong(2, Session.MAX_TIME);
            
            glResourceType = GlResourceTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glResourceType;
    }
    
    public GlResourceType getGlResourceTypeByName(String glResourceTypeName) {
        return getGlResourceTypeByName(glResourceTypeName, EntityPermission.READ_ONLY);
    }
    
    public GlResourceType getGlResourceTypeByNameForUpdate(String glResourceTypeName) {
        return getGlResourceTypeByName(glResourceTypeName, EntityPermission.READ_WRITE);
    }
    
    public GlResourceTypeDetailValue getGlResourceTypeDetailValueForUpdate(GlResourceType glResourceType) {
        return glResourceType == null? null: glResourceType.getLastDetailForUpdate().getGlResourceTypeDetailValue().clone();
    }
    
    public GlResourceTypeDetailValue getGlResourceTypeDetailValueByNameForUpdate(String glResourceTypeName) {
        return getGlResourceTypeDetailValueForUpdate(getGlResourceTypeByNameForUpdate(glResourceTypeName));
    }
    
    public GlResourceType getDefaultGlResourceType(EntityPermission entityPermission) {
        GlResourceType glResourceType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glresourcetypes, glresourcetypedetails " +
                        "WHERE glrtyp_glresourcetypeid = glrtypdt_glrtyp_glresourcetypeid AND glrtypdt_isdefault = 1 AND glrtypdt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glresourcetypes, glresourcetypedetails " +
                        "WHERE glrtyp_glresourcetypeid = glrtypdt_glrtyp_glresourcetypeid AND glrtypdt_isdefault = 1 AND glrtypdt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GlResourceTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, Session.MAX_TIME);
            
            glResourceType = GlResourceTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glResourceType;
    }
    
    public GlResourceType getDefaultGlResourceType() {
        return getDefaultGlResourceType(EntityPermission.READ_ONLY);
    }
    
    public GlResourceType getDefaultGlResourceTypeForUpdate() {
        return getDefaultGlResourceType(EntityPermission.READ_WRITE);
    }
    
    public GlResourceTypeDetailValue getDefaultGlResourceTypeDetailValueForUpdate() {
        return getDefaultGlResourceTypeForUpdate().getLastDetailForUpdate().getGlResourceTypeDetailValue().clone();
    }
    
    private List<GlResourceType> getGlResourceTypes(EntityPermission entityPermission) {
        List<GlResourceType> glResourceTypes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glresourcetypes, glresourcetypedetails " +
                        "WHERE glrtyp_glresourcetypeid = glrtypdt_glrtyp_glresourcetypeid AND glrtypdt_thrutime = ? " +
                        "ORDER BY glrtypdt_sortorder, glrtypdt_glresourcetypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glresourcetypes, glresourcetypedetails " +
                        "WHERE glrtyp_glresourcetypeid = glrtypdt_glrtyp_glresourcetypeid AND glrtypdt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GlResourceTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, Session.MAX_TIME);
            
            glResourceTypes = GlResourceTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glResourceTypes;
    }
    
    public List<GlResourceType> getGlResourceTypes() {
        return getGlResourceTypes(EntityPermission.READ_ONLY);
    }
    
    public List<GlResourceType> getGlResourceTypesForUpdate() {
        return getGlResourceTypes(EntityPermission.READ_WRITE);
    }
    
    public GlResourceTypeTransfer getGlResourceTypeTransfer(UserVisit userVisit, GlResourceType glResourceType) {
        return getAccountingTransferCaches(userVisit).getGlResourceTypeTransferCache().getTransfer(glResourceType);
    }

    public List<GlResourceTypeTransfer> getGlResourceTypeTransfers(UserVisit userVisit, Collection<GlResourceType> glResourceTypes) {
        List<GlResourceTypeTransfer> glResourceTypeTransfers = new ArrayList<>(glResourceTypes.size());
        var glResourceTypeTransferCache = getAccountingTransferCaches(userVisit).getGlResourceTypeTransferCache();

        glResourceTypes.forEach((glResourceType) ->
                glResourceTypeTransfers.add(glResourceTypeTransferCache.getTransfer(glResourceType))
        );

        return glResourceTypeTransfers;
    }

    public List<GlResourceTypeTransfer> getGlResourceTypeTransfers(UserVisit userVisit) {
        return getGlResourceTypeTransfers(userVisit, getGlResourceTypes());
    }

    public GlResourceTypeChoicesBean getGlResourceTypeChoices(String defaultGlResourceTypeChoice, Language language,
            boolean allowNullChoice) {
        var glResourceTypes = getGlResourceTypes();
        var size = glResourceTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultGlResourceTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var glResourceType : glResourceTypes) {
            var glResourceTypeDetail = glResourceType.getLastDetail();
            
            var label = getBestGlResourceTypeDescription(glResourceType, language);
            var value = glResourceTypeDetail.getGlResourceTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultGlResourceTypeChoice != null && defaultGlResourceTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && glResourceTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new GlResourceTypeChoicesBean(labels, values, defaultValue);
    }
    
    private void updateGlResourceTypeFromValue(GlResourceTypeDetailValue glResourceTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(glResourceTypeDetailValue.hasBeenModified()) {
            var glResourceType = GlResourceTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     glResourceTypeDetailValue.getGlResourceTypePK());
            var glResourceTypeDetail = glResourceType.getActiveDetailForUpdate();
            
            glResourceTypeDetail.setThruTime(session.START_TIME_LONG);
            glResourceTypeDetail.store();

            var glResourceTypePK = glResourceTypeDetail.getGlResourceTypePK();
            var glResourceTypeName = glResourceTypeDetailValue.getGlResourceTypeName();
            var isDefault = glResourceTypeDetailValue.getIsDefault();
            var sortOrder = glResourceTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultGlResourceType = getDefaultGlResourceType();
                var defaultFound = defaultGlResourceType != null && !defaultGlResourceType.equals(glResourceType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultGlResourceTypeDetailValue = getDefaultGlResourceTypeDetailValueForUpdate();
                    
                    defaultGlResourceTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateGlResourceTypeFromValue(defaultGlResourceTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            glResourceTypeDetail = GlResourceTypeDetailFactory.getInstance().create(glResourceTypePK,
                    glResourceTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            glResourceType.setActiveDetail(glResourceTypeDetail);
            glResourceType.setLastDetail(glResourceTypeDetail);
            
            sendEvent(glResourceTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateGlResourceTypeFromValue(GlResourceTypeDetailValue glResourceTypeDetailValue, BasePK updatedBy) {
        updateGlResourceTypeFromValue(glResourceTypeDetailValue, true, updatedBy);
    }
    
    public void deleteGlResourceType(GlResourceType glResourceType, BasePK deletedBy) {
        deleteGlAccountsByGlResourceType(glResourceType, deletedBy);
        deleteGlResourceTypeDescriptionsByGlResourceType(glResourceType, deletedBy);

        var glResourceTypeDetail = glResourceType.getLastDetailForUpdate();
        glResourceTypeDetail.setThruTime(session.START_TIME_LONG);
        glResourceTypeDetail.store();
        glResourceType.setActiveDetail(null);
        
        // Check for default, and pick one if necessary
        var defaultGlResourceType = getDefaultGlResourceType();
        if(defaultGlResourceType == null) {
            var glResourceTypes = getGlResourceTypesForUpdate();
            
            if(!glResourceTypes.isEmpty()) {
                var iter = glResourceTypes.iterator();
                if(iter.hasNext()) {
                    defaultGlResourceType = iter.next();
                }
                var glResourceTypeDetailValue = Objects.requireNonNull(defaultGlResourceType).getLastDetailForUpdate().getGlResourceTypeDetailValue().clone();
                
                glResourceTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateGlResourceTypeFromValue(glResourceTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(glResourceType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Resource Type Descriptions
    // --------------------------------------------------------------------------------
    
    public GlResourceTypeDescription createGlResourceTypeDescription(GlResourceType glResourceType, Language language, String description, BasePK createdBy) {
        var glResourceTypeDescription = GlResourceTypeDescriptionFactory.getInstance().create(glResourceType, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEvent(glResourceType.getPrimaryKey(), EventTypes.MODIFY, glResourceTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return glResourceTypeDescription;
    }
    
    private GlResourceTypeDescription getGlResourceTypeDescription(GlResourceType glResourceType, Language language, EntityPermission entityPermission) {
        GlResourceTypeDescription glResourceTypeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glresourcetypedescriptions " +
                        "WHERE glrtypd_glrtyp_glresourcetypeid = ? AND glrtypd_lang_languageid = ? AND glrtypd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glresourcetypedescriptions " +
                        "WHERE glrtypd_glrtyp_glresourcetypeid = ? AND glrtypd_lang_languageid = ? AND glrtypd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GlResourceTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, glResourceType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            glResourceTypeDescription = GlResourceTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glResourceTypeDescription;
    }
    
    public GlResourceTypeDescription getGlResourceTypeDescription(GlResourceType glResourceType, Language language) {
        return getGlResourceTypeDescription(glResourceType, language, EntityPermission.READ_ONLY);
    }
    
    public GlResourceTypeDescription getGlResourceTypeDescriptionForUpdate(GlResourceType glResourceType, Language language) {
        return getGlResourceTypeDescription(glResourceType, language, EntityPermission.READ_WRITE);
    }
    
    public GlResourceTypeDescriptionValue getGlResourceTypeDescriptionValue(GlResourceTypeDescription glResourceTypeDescription) {
        return glResourceTypeDescription == null? null: glResourceTypeDescription.getGlResourceTypeDescriptionValue().clone();
    }
    
    public GlResourceTypeDescriptionValue getGlResourceTypeDescriptionValueForUpdate(GlResourceType glResourceType, Language language) {
        return getGlResourceTypeDescriptionValue(getGlResourceTypeDescriptionForUpdate(glResourceType, language));
    }
    
    private List<GlResourceTypeDescription> getGlResourceTypeDescriptionsByGlResourceType(GlResourceType glResourceType, EntityPermission entityPermission) {
        List<GlResourceTypeDescription> glResourceTypeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glresourcetypedescriptions, languages " +
                        "WHERE glrtypd_glrtyp_glresourcetypeid = ? AND glrtypd_thrutime = ? AND glrtypd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glresourcetypedescriptions " +
                        "WHERE glrtypd_glrtyp_glresourcetypeid = ? AND glrtypd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GlResourceTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, glResourceType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            glResourceTypeDescriptions = GlResourceTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glResourceTypeDescriptions;
    }
    
    public List<GlResourceTypeDescription> getGlResourceTypeDescriptionsByGlResourceType(GlResourceType glResourceType) {
        return getGlResourceTypeDescriptionsByGlResourceType(glResourceType, EntityPermission.READ_ONLY);
    }
    
    public List<GlResourceTypeDescription> getGlResourceTypeDescriptionsByGlResourceTypeForUpdate(GlResourceType glResourceType) {
        return getGlResourceTypeDescriptionsByGlResourceType(glResourceType, EntityPermission.READ_WRITE);
    }
    
    public String getBestGlResourceTypeDescription(GlResourceType glResourceType, Language language) {
        String description;
        var glResourceTypeDescription = getGlResourceTypeDescription(glResourceType, language);
        
        if(glResourceTypeDescription == null && !language.getIsDefault()) {
            glResourceTypeDescription = getGlResourceTypeDescription(glResourceType, getPartyControl().getDefaultLanguage());
        }
        
        if(glResourceTypeDescription == null) {
            description = glResourceType.getLastDetail().getGlResourceTypeName();
        } else {
            description = glResourceTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public GlResourceTypeDescriptionTransfer getGlResourceTypeDescriptionTransfer(UserVisit userVisit, GlResourceTypeDescription glResourceTypeDescription) {
        return getAccountingTransferCaches(userVisit).getGlResourceTypeDescriptionTransferCache().getTransfer(glResourceTypeDescription);
    }
    
    public List<GlResourceTypeDescriptionTransfer> getGlResourceTypeDescriptionTransfersByGlResourceType(UserVisit userVisit, GlResourceType glResourceType) {
        var glResourceTypeDescriptions = getGlResourceTypeDescriptionsByGlResourceType(glResourceType);
        List<GlResourceTypeDescriptionTransfer> glResourceTypeDescriptionTransfers = new ArrayList<>(glResourceTypeDescriptions.size());
        var glResourceTypeDescriptionTransferCache = getAccountingTransferCaches(userVisit).getGlResourceTypeDescriptionTransferCache();
        
        glResourceTypeDescriptions.forEach((glResourceTypeDescription) ->
                glResourceTypeDescriptionTransfers.add(glResourceTypeDescriptionTransferCache.getTransfer(glResourceTypeDescription))
        );
        
        return glResourceTypeDescriptionTransfers;
    }
    
    public void updateGlResourceTypeDescriptionFromValue(GlResourceTypeDescriptionValue glResourceTypeDescriptionValue, BasePK updatedBy) {
        if(glResourceTypeDescriptionValue.hasBeenModified()) {
            var glResourceTypeDescription = GlResourceTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, glResourceTypeDescriptionValue.getPrimaryKey());
            
            glResourceTypeDescription.setThruTime(session.START_TIME_LONG);
            glResourceTypeDescription.store();

            var glResourceType = glResourceTypeDescription.getGlResourceType();
            var language = glResourceTypeDescription.getLanguage();
            var description = glResourceTypeDescriptionValue.getDescription();
            
            glResourceTypeDescription = GlResourceTypeDescriptionFactory.getInstance().create(glResourceType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(glResourceType.getPrimaryKey(), EventTypes.MODIFY, glResourceTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteGlResourceTypeDescription(GlResourceTypeDescription glResourceTypeDescription, BasePK deletedBy) {
        glResourceTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(glResourceTypeDescription.getGlResourceTypePK(), EventTypes.MODIFY, glResourceTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteGlResourceTypeDescriptionsByGlResourceType(GlResourceType glResourceType, BasePK deletedBy) {
        var glResourceTypeDescriptions = getGlResourceTypeDescriptionsByGlResourceTypeForUpdate(glResourceType);
        
        glResourceTypeDescriptions.forEach((glResourceTypeDescription) -> 
                deleteGlResourceTypeDescription(glResourceTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Accounts
    // --------------------------------------------------------------------------------
    
    public GlAccount createGlAccount(String glAccountName, GlAccount parentGlAccount, GlAccountType glAccountType,
            GlAccountClass glAccountClass, GlAccountCategory glAccountCategory, GlResourceType glResourceType, Currency currency,
            Boolean isDefault, BasePK createdBy) {
        
        if(glAccountCategory != null) {
            var defaultGlAccount = getDefaultGlAccount(glAccountCategory);
            var defaultFound = defaultGlAccount != null;
            
            if(defaultFound && isDefault) {
                var defaultGlAccountDetailValue = getDefaultGlAccountDetailValueForUpdate(glAccountCategory);
                
                defaultGlAccountDetailValue.setIsDefault(Boolean.FALSE);
                updateGlAccountFromValue(defaultGlAccountDetailValue, false, createdBy);
            } else if(!defaultFound) {
                isDefault = Boolean.TRUE;
            }
        } else {
            isDefault = null;
        }

        var glAccount = GlAccountFactory.getInstance().create();
        var glAccountDetail = GlAccountDetailFactory.getInstance().create(glAccount, glAccountName,
                parentGlAccount, glAccountType, glAccountClass, glAccountCategory, glResourceType, currency, isDefault,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        glAccount = GlAccountFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, glAccount.getPrimaryKey());
        glAccount.setActiveDetail(glAccountDetail);
        glAccount.setLastDetail(glAccountDetail);
        glAccount.store();
        
        sendEvent(glAccount.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return glAccount;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.GlAccount */
    public GlAccount getGlAccountByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new GlAccountPK(entityInstance.getEntityUniqueId());

        return GlAccountFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public GlAccount getGlAccountByEntityInstance(EntityInstance entityInstance) {
        return getGlAccountByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public GlAccount getGlAccountByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getGlAccountByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countGlAccounts() {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM glaccounts, glaccountdetails
                WHERE gla_activedetailid = gladt_glaccountdetailid
                """);
    }

    public long countGlAccountsByGlAccountType(GlAccountType glAccountType) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM glaccounts, glaccountdetails
                WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_glatyp_glaccounttypeid = ?
                """, glAccountType);
    }

    public long countGlAccountsByGlAccountClass(GlAccountClass glAccountClass) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM glaccounts, glaccountdetails
                WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_glacls_glaccountclassid = ?
                """, glAccountClass);
    }

    public long countGlAccountsByGlAccountCategory(GlAccountCategory glAccountCategory) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM glaccounts, glaccountdetails
                WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_glac_glaccountcategoryid = ?
                """, glAccountCategory);
    }

    public long countGlAccountsByGlResourceType(GlResourceType glResourceType) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM glaccounts, glaccountdetails
                WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_glrtyp_glresourcetypeid = ?
                """, glResourceType);
    }

    public GlAccount getGlAccountByName(String glAccountName, EntityPermission entityPermission) {
        GlAccount glAccount;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccounts, glaccountdetails " +
                        "WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_glaccountname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccounts, glaccountdetails " +
                        "WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_glaccountname = ? " +
                        "FOR UPDATE";
            }

            var ps = GlAccountFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, glAccountName);
            
            glAccount = GlAccountFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glAccount;
    }
    
    public GlAccount getGlAccountByName(String glAccountName) {
        return getGlAccountByName(glAccountName, EntityPermission.READ_ONLY);
    }
    
    public GlAccount getGlAccountByNameForUpdate(String glAccountName) {
        return getGlAccountByName(glAccountName, EntityPermission.READ_WRITE);
    }
    
    public GlAccountDetailValue getGlAccountDetailValueByNameForUpdate(String glAccountName) {
        return getGlAccountDetailValueForUpdate(getGlAccountByNameForUpdate(glAccountName));
    }
    
    private GlAccount getDefaultGlAccount(GlAccountCategoryPK glAccountCategoryPK, EntityPermission entityPermission) {
        GlAccount glAccount;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccounts, glaccountdetails " +
                        "WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_glac_glaccountcategoryid = ? AND gladt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccounts, glaccountdetails " +
                        "WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_glac_glaccountcategoryid = ? AND gladt_isdefault = 1 " +
                        "FOR UPDATE";
            }

            var ps = GlAccountFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, glAccountCategoryPK.getEntityId());
            
            glAccount = GlAccountFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glAccount;
    }
    
    public GlAccount getDefaultGlAccount(GlAccountCategory glAccountCategory, EntityPermission entityPermission) {
        return getDefaultGlAccount(glAccountCategory.getPrimaryKey(), entityPermission);
    }
    
    public GlAccount getDefaultGlAccount(GlAccountCategory glAccountCategory) {
        return getDefaultGlAccount(glAccountCategory, EntityPermission.READ_ONLY);
    }
    
    public GlAccount getDefaultGlAccount(GlAccountCategoryPK glAccountCategoryPK) {
        return getDefaultGlAccount(glAccountCategoryPK, EntityPermission.READ_ONLY);
    }
    
    public GlAccount getDefaultGlAccountForUpdate(GlAccountCategory glAccountCategory) {
        return getDefaultGlAccount(glAccountCategory, EntityPermission.READ_WRITE);
    }
    
    public GlAccount getDefaultGlAccountForUpdate(GlAccountCategoryPK glAccountCategoryPK) {
        return getDefaultGlAccount(glAccountCategoryPK, EntityPermission.READ_WRITE);
    }
    
    public GlAccountDetailValue getDefaultGlAccountDetailValueForUpdate(GlAccountCategory glAccountCategory) {
        return getDefaultGlAccountForUpdate(glAccountCategory).getLastDetailForUpdate().getGlAccountDetailValue().clone();
    }
    
    public GlAccountDetailValue getDefaultGlAccountDetailValueForUpdate(GlAccountCategoryPK glAccountCategoryPK) {
        return getDefaultGlAccountForUpdate(glAccountCategoryPK).getLastDetailForUpdate().getGlAccountDetailValue().clone();
    }
    
    public GlAccountDetailValue getGlAccountDetailValueForUpdate(GlAccount glAccount) {
        return glAccount == null? null: glAccount.getLastDetailForUpdate().getGlAccountDetailValue().clone();
    }
    
    private List<GlAccount> getGlAccounts(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM glaccounts, glaccountdetails " +
                    "WHERE gla_activedetailid = gladt_glaccountdetailid " +
                    "ORDER BY gladt_glaccountname " +
                    "_LIMIT_";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM glaccounts, glaccountdetails " +
                    "WHERE gla_activedetailid = gladt_glaccountdetailid " +
                    "FOR UPDATE";
        }

        var ps = GlAccountFactory.getInstance().prepareStatement(query);
        
        return GlAccountFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<GlAccount> getGlAccounts() {
        return getGlAccounts(EntityPermission.READ_ONLY);
    }
    
    public List<GlAccount> getGlAccountsForUpdate() {
        return getGlAccounts(EntityPermission.READ_WRITE);
    }

    private List<GlAccount> getGlAccountsByGlAccountType(GlAccountType glAccountType, EntityPermission entityPermission) {
        List<GlAccount> glAccounts;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccounts, glaccountdetails " +
                        "WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_glatyp_glaccounttypeid = ? " +
                        "ORDER BY gladt_glaccountname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccounts, glaccountdetails " +
                        "WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_glatyp_glaccounttypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = GlAccountFactory.getInstance().prepareStatement(query);

            ps.setLong(1, glAccountType.getPrimaryKey().getEntityId());

            glAccounts = GlAccountFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return glAccounts;
    }

    public List<GlAccount> getGlAccountsByGlAccountType(GlAccountType glAccountType) {
        return getGlAccountsByGlAccountType(glAccountType, EntityPermission.READ_ONLY);
    }

    public List<GlAccount> getGlAccountsByGlAccountTypeForUpdate(GlAccountType glAccountType) {
        return getGlAccountsByGlAccountType(glAccountType, EntityPermission.READ_WRITE);
    }

    private List<GlAccount> getGlAccountsByGlAccountClass(GlAccountClass glAccountClass, EntityPermission entityPermission) {
        List<GlAccount> glAccounts;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccounts, glaccountdetails " +
                        "WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_glacls_glaccountclassid = ? " +
                        "ORDER BY gladt_glaccountname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccounts, glaccountdetails " +
                        "WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_glacls_glaccountclassid = ? " +
                        "FOR UPDATE";
            }

            var ps = GlAccountFactory.getInstance().prepareStatement(query);

            ps.setLong(1, glAccountClass.getPrimaryKey().getEntityId());

            glAccounts = GlAccountFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return glAccounts;
    }

    public List<GlAccount> getGlAccountsByGlAccountClass(GlAccountClass glAccountClass) {
        return getGlAccountsByGlAccountClass(glAccountClass, EntityPermission.READ_ONLY);
    }

    public List<GlAccount> getGlAccountsByGlAccountClassForUpdate(GlAccountClass glAccountClass) {
        return getGlAccountsByGlAccountClass(glAccountClass, EntityPermission.READ_WRITE);
    }

    private List<GlAccount> getGlAccountsByGlAccountCategory(GlAccountCategoryPK glAccountCategoryPK, EntityPermission entityPermission) {
        List<GlAccount> glAccounts;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccounts, glaccountdetails " +
                        "WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_glac_glaccountcategoryid = ? " +
                        "ORDER BY gladt_glaccountname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccounts, glaccountdetails " +
                        "WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_glac_glaccountcategoryid = ? " +
                        "FOR UPDATE";
            }

            var ps = GlAccountFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, glAccountCategoryPK.getEntityId());
            
            glAccounts = GlAccountFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glAccounts;
    }
    
    private List<GlAccount> getGlAccountsByGlAccountCategory(GlAccountCategory glAccountCategory, EntityPermission entityPermission) {
        return getGlAccountsByGlAccountCategory(glAccountCategory.getPrimaryKey(), entityPermission);
    }
    
    public List<GlAccount> getGlAccountsByGlAccountCategory(GlAccountCategory glAccountCategory) {
        return getGlAccountsByGlAccountCategory(glAccountCategory, EntityPermission.READ_ONLY);
    }
    
    public List<GlAccount> getGlAccountsByGlAccountCategory(GlAccountCategoryPK glAccountCategoryPK) {
        return getGlAccountsByGlAccountCategory(glAccountCategoryPK, EntityPermission.READ_ONLY);
    }
    
    public List<GlAccount> getGlAccountsByGlAccountCategoryForUpdate(GlAccountCategory glAccountCategory) {
        return getGlAccountsByGlAccountCategory(glAccountCategory, EntityPermission.READ_WRITE);
    }
    
    public List<GlAccount> getGlAccountsByGlAccountCategoryForUpdate(GlAccountCategoryPK glAccountCategoryPK) {
        return getGlAccountsByGlAccountCategory(glAccountCategoryPK, EntityPermission.READ_WRITE);
    }
    
    private List<GlAccount> getGlAccountsByGlResourceType(GlResourceType glResourceType, EntityPermission entityPermission) {
        List<GlAccount> glAccounts;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccounts, glaccountdetails " +
                        "WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_glrtyp_glresourcetypeid = ? " +
                        "ORDER BY gladt_glaccountname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccounts, glaccountdetails " +
                        "WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_glrtyp_glresourcetypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = GlAccountFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, glResourceType.getPrimaryKey().getEntityId());
            
            glAccounts = GlAccountFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glAccounts;
    }
    
    public List<GlAccount> getGlAccountsByGlResourceType(GlResourceType glResourceType) {
        return getGlAccountsByGlResourceType(glResourceType, EntityPermission.READ_ONLY);
    }
    
    public List<GlAccount> getGlAccountsByGlResourceTypeForUpdate(GlResourceType glResourceType) {
        return getGlAccountsByGlResourceType(glResourceType, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getGlAccountsByParentGlAccountQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM glaccounts, glaccountdetails " +
                "WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_parentglaccountid = ? " +
                "ORDER BY gladt_sortorder, gladt_glaccountname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM glaccounts, glaccountdetails " +
                "WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_parentglaccountid = ? " +
                "FOR UPDATE");
        getGlAccountsByParentGlAccountQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<GlAccount> getGlAccountsByParentGlAccount(GlAccount parentGlAccount,
            EntityPermission entityPermission) {
        return GlAccountFactory.getInstance().getEntitiesFromQuery(entityPermission, getGlAccountsByParentGlAccountQueries,
                parentGlAccount);
    }

    public List<GlAccount> getGlAccountsByParentGlAccount(GlAccount parentGlAccount) {
        return getGlAccountsByParentGlAccount(parentGlAccount, EntityPermission.READ_ONLY);
    }

    public List<GlAccount> getGlAccountsByParentGlAccountForUpdate(GlAccount parentGlAccount) {
        return getGlAccountsByParentGlAccount(parentGlAccount, EntityPermission.READ_WRITE);
    }

    public GlAccountTransfer getGlAccountTransfer(UserVisit userVisit, GlAccount glAccount) {
        return getAccountingTransferCaches(userVisit).getGlAccountTransferCache().getTransfer(glAccount);
    }
    
    public List<GlAccountTransfer> getGlAccountTransfers(UserVisit userVisit, Collection<GlAccount> glAccounts) {
        List<GlAccountTransfer> glAccountTransfers = new ArrayList<>(glAccounts.size());
        var glAccountTransferCache = getAccountingTransferCaches(userVisit).getGlAccountTransferCache();
        
        glAccounts.forEach((glAccount) ->
                glAccountTransfers.add(glAccountTransferCache.getTransfer(glAccount))
        );
        
        return glAccountTransfers;
    }
    
    public List<GlAccountTransfer> getGlAccountTransfers(UserVisit userVisit) {
        return getGlAccountTransfers(userVisit, getGlAccounts());
    }
    
    public List<GlAccountTransfer> getGlAccountTransfersByGlAccountClass(UserVisit userVisit, GlAccountClass glAccountClass) {
        return getGlAccountTransfers(userVisit, getGlAccountsByGlAccountClass(glAccountClass));
    }
    
    public List<GlAccountTransfer> getGlAccountTransfersByGlAccountCategory(UserVisit userVisit, GlAccountCategory glAccountCategory) {
        return getGlAccountTransfers(userVisit, getGlAccountsByGlAccountCategory(glAccountCategory));
    }
    
    public List<GlAccountTransfer> getGlAccountTransfersByGlResourceType(UserVisit userVisit, GlResourceType glResourceType) {
        return getGlAccountTransfers(userVisit, getGlAccountsByGlResourceType(glResourceType));
    }
    
    public GlAccountChoicesBean getGlAccountChoices(String defaultGlAccountChoice, Language language, boolean allowNullChoice) {
        var glAccounts = getGlAccounts();
        var size = glAccounts.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultGlAccountChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var glAccount : glAccounts) {
            var glAccountDetail = glAccount.getLastDetail();
            
            var label = getBestGlAccountDescription(glAccount, language);
            var value = glAccountDetail.getGlAccountName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultGlAccountChoice != null && defaultGlAccountChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null) {
                defaultValue = value;
            }
        }
        
        return new GlAccountChoicesBean(labels, values, defaultValue);
    }
    
    public GlAccountChoicesBean getGlAccountChoicesByGlAccountCategory(String defaultGlAccountChoice, Language language,
            GlAccountCategory glAccountCategory, boolean allowNullChoice) {
        var glAccounts = getGlAccountsByGlAccountCategory(glAccountCategory);
        var size = glAccounts.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultGlAccountChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var glAccount : glAccounts) {
            var glAccountDetail = glAccount.getLastDetail();
            
            var label = getBestGlAccountDescription(glAccount, language);
            var value = glAccountDetail.getGlAccountName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultGlAccountChoice != null && defaultGlAccountChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && glAccountDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new GlAccountChoicesBean(labels, values, defaultValue);
    }
    
    public boolean isParentGlAccountSafe(GlAccount glAccount, GlAccount parentGlAccount) {
        var safe = true;
        
        if(parentGlAccount != null) {
            Set<GlAccount> parentItemPurchasingCategories = new HashSet<>();
            
            parentItemPurchasingCategories.add(glAccount);
            do {
                if(parentItemPurchasingCategories.contains(parentGlAccount)) {
                    safe = false;
                    break;
                }
                
                parentItemPurchasingCategories.add(parentGlAccount);
                parentGlAccount = parentGlAccount.getLastDetail().getParentGlAccount();
            } while(parentGlAccount != null);
        }
        
        return safe;
    }
    
    private void pickDefaultGlAccount(final GlAccountCategoryPK glAccountCategoryPK, final BasePK updatedBy) {
        var defaultGlAccount = getDefaultGlAccount(glAccountCategoryPK);
            
        if(defaultGlAccount == null) {
            var glAccounts = getGlAccountsByGlAccountCategoryForUpdate(glAccountCategoryPK);
            
            if(!glAccounts.isEmpty()) {
                var iter = glAccounts.iterator();
                if(iter.hasNext()) {
                    defaultGlAccount = iter.next();
                }
                var glAccountDetailValue = Objects.requireNonNull(defaultGlAccount).getLastDetailForUpdate().getGlAccountDetailValue().clone();
                
                glAccountDetailValue.setIsDefault(Boolean.TRUE);
                updateGlAccountFromValue(glAccountDetailValue, false, updatedBy);
            }
        }
    }
    
    private void updateGlAccountFromValue(GlAccountDetailValue glAccountDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(glAccountDetailValue.hasBeenModified()) {
            var glAccount = GlAccountFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     glAccountDetailValue.getGlAccountPK());
            var glAccountDetail = glAccount.getActiveDetailForUpdate();
            
            glAccountDetail.setThruTime(session.START_TIME_LONG);
            glAccountDetail.store();

            var glAccountPK = glAccountDetail.getGlAccountPK(); // Not updated
            var glAccountName = glAccountDetailValue.getGlAccountName();
            var parentGlAccountPK = glAccountDetailValue.getParentGlAccountPK();
            var glAccountTypePK = glAccountDetail.getGlAccountTypePK(); // Not updated
            var glAccountClassPK = glAccountDetailValue.getGlAccountClassPK();
            var glAccountCategoryPK = glAccountDetailValue.getGlAccountCategoryPK();
            var glResourceTypePK = glAccountDetailValue.getGlResourceTypePK();
            var currencyPK = glAccountDetail.getCurrencyPK(); // Not updated
            var isDefault = glAccountDetailValue.getIsDefault();
            
            if(checkDefault) {
                if(glAccountCategoryPK != null) {
                    var defaultGlAccount = getDefaultGlAccount(glAccountCategoryPK);
                    var defaultFound = defaultGlAccount != null && !defaultGlAccount.equals(glAccount);
                    
                    if(isDefault && defaultFound) {
                        // If I'm the default, and a default already existed...
                        var defaultGlAccountDetailValue = getDefaultGlAccountDetailValueForUpdate(glAccountCategoryPK);
                        
                        defaultGlAccountDetailValue.setIsDefault(Boolean.FALSE);
                        updateGlAccountFromValue(defaultGlAccountDetailValue, false, updatedBy);
                    } else if(!isDefault && !defaultFound) {
                        // If I'm not the default, and no other default exists...
                        isDefault = Boolean.TRUE;
                    }
                } else {
                    if(glAccountDetail.getIsDefault() != null) {
                        // If it was set, but is now going to be null, then we need to pick a new default
                        // by going back to the pre-modification entity to get the GL Account Category.
                        pickDefaultGlAccount(glAccountDetail.getGlAccountCategoryPK(), updatedBy);
                    } else {
                        isDefault = null;
                    }
                }
            }
            
            glAccountDetail = GlAccountDetailFactory.getInstance().create(glAccountPK, glAccountName,
                    parentGlAccountPK, glAccountTypePK, glAccountClassPK, glAccountCategoryPK, glResourceTypePK, currencyPK,
                    isDefault, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            glAccount.setActiveDetail(glAccountDetail);
            glAccount.setLastDetail(glAccountDetail);
            
            sendEvent(glAccountPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateGlAccountFromValue(GlAccountDetailValue glAccountDetailValue, BasePK updatedBy) {
        updateGlAccountFromValue(glAccountDetailValue, true, updatedBy);
    }
    
    private void deleteGlAccount(GlAccount glAccount, boolean checkDefault, BasePK deletedBy) {
        var financialControl  = Session.getModelController(FinancialControl.class);
        
        deleteGlAccountsByParentGlAccount(glAccount, deletedBy);
        deleteTransactionGlAccountByGlAccount(glAccount, deletedBy);
        // TODO: deleteTransactionGlAccountByGlAccount(glAccount, deletedBy);
        deleteGlAccountDescriptionsByGlAccount(glAccount, deletedBy);
        financialControl.deleteFinancialAccountsByGlAccount(glAccount, deletedBy);

        var glAccountDetail = glAccount.getLastDetailForUpdate();
        glAccountDetail.setThruTime(session.START_TIME_LONG);
        glAccount.setActiveDetail(null);
        glAccount.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var glAccountCategoryPK = glAccountDetail.getGlAccountCategoryPK();

            if(glAccountCategoryPK != null) {
                pickDefaultGlAccount(glAccountCategoryPK, deletedBy);
            }
        }

        sendEvent(glAccount.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteGlAccount(GlAccount glAccount, BasePK deletedBy) {
        deleteGlAccount(glAccount, true, deletedBy);
    }

    private void deleteGlAccounts(List<GlAccount> glAccounts, boolean checkDefault, BasePK deletedBy) {
        glAccounts.forEach((glAccount) -> deleteGlAccount(glAccount, checkDefault, deletedBy));
    }

    public void deleteGlAccounts(List<GlAccount> glAccounts, BasePK deletedBy) {
        deleteGlAccounts(glAccounts, true, deletedBy);
    }

    private void deleteGlAccountsByParentGlAccount(GlAccount parentGlAccount, BasePK deletedBy) {
        deleteGlAccounts(getGlAccountsByParentGlAccountForUpdate(parentGlAccount), false, deletedBy);
    }

    public void deleteGlAccountsByGlAccountClass(GlAccountClass glAccountClass, BasePK deletedBy) {
        deleteGlAccounts(getGlAccountsByGlAccountClassForUpdate(glAccountClass), deletedBy);
    }
    
    public void deleteGlAccountsByGlAccountCategory(GlAccountCategory glAccountCategory, BasePK deletedBy) {
        deleteGlAccounts(getGlAccountsByGlAccountCategoryForUpdate(glAccountCategory), deletedBy);
    }
    
    public void deleteGlAccountsByGlResourceType(GlResourceType glResourceType, BasePK deletedBy) {
        deleteGlAccounts(getGlAccountsByGlResourceTypeForUpdate(glResourceType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Descriptions
    // --------------------------------------------------------------------------------
    
    public GlAccountDescription createGlAccountDescription(GlAccount glAccount, Language language, String description, BasePK createdBy) {
        var glAccountDescription = GlAccountDescriptionFactory.getInstance().create(glAccount, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEvent(glAccount.getPrimaryKey(), EventTypes.MODIFY, glAccountDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return glAccountDescription;
    }
    
    private GlAccountDescription getGlAccountDescription(GlAccount glAccount, Language language, EntityPermission entityPermission) {
        GlAccountDescription glAccountDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccountdescriptions " +
                        "WHERE glad_gla_glaccountid = ? AND glad_lang_languageid = ? AND glad_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccountdescriptions " +
                        "WHERE glad_gla_glaccountid = ? AND glad_lang_languageid = ? AND glad_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GlAccountDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, glAccount.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            glAccountDescription = GlAccountDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glAccountDescription;
    }
    
    public GlAccountDescription getGlAccountDescription(GlAccount glAccount, Language language) {
        return getGlAccountDescription(glAccount, language, EntityPermission.READ_ONLY);
    }
    
    public GlAccountDescription getGlAccountDescriptionForUpdate(GlAccount glAccount, Language language) {
        return getGlAccountDescription(glAccount, language, EntityPermission.READ_WRITE);
    }
    
    public GlAccountDescriptionValue getGlAccountDescriptionValue(GlAccountDescription glAccountDescription) {
        return glAccountDescription == null? null: glAccountDescription.getGlAccountDescriptionValue().clone();
    }
    
    public GlAccountDescriptionValue getGlAccountDescriptionValueForUpdate(GlAccount glAccount, Language language) {
        return getGlAccountDescriptionValue(getGlAccountDescriptionForUpdate(glAccount, language));
    }
    
    private List<GlAccountDescription> getGlAccountDescriptionsByGlAccount(GlAccount glAccount, EntityPermission entityPermission) {
        List<GlAccountDescription> glAccountDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccountdescriptions, languages " +
                        "WHERE glad_gla_glaccountid = ? AND glad_thrutime = ? AND glad_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccountdescriptions " +
                        "WHERE glad_gla_glaccountid = ? AND glad_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GlAccountDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, glAccount.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            glAccountDescriptions = GlAccountDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glAccountDescriptions;
    }
    
    public List<GlAccountDescription> getGlAccountDescriptionsByGlAccount(GlAccount glAccount) {
        return getGlAccountDescriptionsByGlAccount(glAccount, EntityPermission.READ_ONLY);
    }
    
    public List<GlAccountDescription> getGlAccountDescriptionsByGlAccountForUpdate(GlAccount glAccount) {
        return getGlAccountDescriptionsByGlAccount(glAccount, EntityPermission.READ_WRITE);
    }
    
    public String getBestGlAccountDescription(GlAccount glAccount, Language language) {
        String description;
        var glAccountDescription = getGlAccountDescription(glAccount, language);
        
        if(glAccountDescription == null && !language.getIsDefault()) {
            glAccountDescription = getGlAccountDescription(glAccount, getPartyControl().getDefaultLanguage());
        }
        
        if(glAccountDescription == null) {
            description = glAccount.getLastDetail().getGlAccountName();
        } else {
            description = glAccountDescription.getDescription();
        }
        
        return description;
    }
    
    public GlAccountDescriptionTransfer getGlAccountDescriptionTransfer(UserVisit userVisit, GlAccountDescription glAccountDescription) {
        return getAccountingTransferCaches(userVisit).getGlAccountDescriptionTransferCache().getTransfer(glAccountDescription);
    }
    
    public List<GlAccountDescriptionTransfer> getGlAccountDescriptionTransfersByGlAccount(UserVisit userVisit, GlAccount glAccount) {
        var glAccountDescriptions = getGlAccountDescriptionsByGlAccount(glAccount);
        List<GlAccountDescriptionTransfer> glAccountDescriptionTransfers = new ArrayList<>(glAccountDescriptions.size());
        var glAccountDescriptionTransferCache = getAccountingTransferCaches(userVisit).getGlAccountDescriptionTransferCache();
        
        glAccountDescriptions.forEach((glAccountDescription) ->
                glAccountDescriptionTransfers.add(glAccountDescriptionTransferCache.getTransfer(glAccountDescription))
        );
        
        return glAccountDescriptionTransfers;
    }
    
    public void updateGlAccountDescriptionFromValue(GlAccountDescriptionValue glAccountDescriptionValue, BasePK updatedBy) {
        if(glAccountDescriptionValue.hasBeenModified()) {
            var glAccountDescription = GlAccountDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, glAccountDescriptionValue.getPrimaryKey());
            
            glAccountDescription.setThruTime(session.START_TIME_LONG);
            glAccountDescription.store();

            var glAccount = glAccountDescription.getGlAccount();
            var language = glAccountDescription.getLanguage();
            var description = glAccountDescriptionValue.getDescription();
            
            glAccountDescription = GlAccountDescriptionFactory.getInstance().create(glAccount, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(glAccount.getPrimaryKey(), EventTypes.MODIFY, glAccountDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteGlAccountDescription(GlAccountDescription glAccountDescription, BasePK deletedBy) {
        glAccountDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(glAccountDescription.getGlAccountPK(), EventTypes.MODIFY, glAccountDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteGlAccountDescriptionsByGlAccount(GlAccount glAccount, BasePK deletedBy) {
        var glAccountDescriptions = getGlAccountDescriptionsByGlAccountForUpdate(glAccount);
        
        glAccountDescriptions.forEach((glAccountDescription) -> 
                deleteGlAccountDescription(glAccountDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Summaries
    // --------------------------------------------------------------------------------
    
    public GlAccountSummary createGlAccountSummary(GlAccount glAccount, Party groupParty, Period period, Long debitTotal,
            Long creditTotal, Long balance) {
        return GlAccountSummaryFactory.getInstance().create(glAccount, groupParty, period, debitTotal, creditTotal, balance);
    }
    
    private GlAccountSummary getGlAccountSummary(GlAccount glAccount, Party groupParty, Period period, EntityPermission entityPermission) {
        GlAccountSummary glAccountSummary;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccountsummaries " +
                        "WHERE glasmy_gla_glaccountid = ? AND glasmy_grouppartyid = ? AND glasmy_prd_periodid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccountsummaries " +
                        "WHERE glasmy_gla_glaccountid = ? AND glasmy_grouppartyid = ? AND glasmy_prd_periodid = ? " +
                        "FOR UPDATE";
            }

            var ps = GlAccountSummaryFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, glAccount.getPrimaryKey().getEntityId());
            ps.setLong(2, groupParty.getPrimaryKey().getEntityId());
            ps.setLong(3, period.getPrimaryKey().getEntityId());
            
            glAccountSummary = GlAccountSummaryFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glAccountSummary;
    }
    
    public GlAccountSummary getGlAccountSummary(GlAccount glAccount, Party groupParty, Period period) {
        return getGlAccountSummary(glAccount, groupParty, period, EntityPermission.READ_ONLY);
    }
    
    public GlAccountSummary getGlAccountSummaryForUpdate(GlAccount glAccount, Party groupParty, Period period) {
        return getGlAccountSummary(glAccount, groupParty, period, EntityPermission.READ_WRITE);
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Types
    // --------------------------------------------------------------------------------
    
    public TransactionType createTransactionType(String transactionTypeName, Integer sortOrder, BasePK createdBy) {
        var transactionType = TransactionTypeFactory.getInstance().create();
        var transactionTypeDetail = TransactionTypeDetailFactory.getInstance().create(transactionType, transactionTypeName, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        transactionType = TransactionTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, transactionType.getPrimaryKey());
        transactionType.setActiveDetail(transactionTypeDetail);
        transactionType.setLastDetail(transactionTypeDetail);
        transactionType.store();
        
        sendEvent(transactionType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return transactionType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.TransactionType */
    public TransactionType getTransactionTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new TransactionTypePK(entityInstance.getEntityUniqueId());

        return TransactionTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public TransactionType getTransactionTypeByEntityInstance(EntityInstance entityInstance) {
        return getTransactionTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public TransactionType getTransactionTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getTransactionTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countTransactionTypes() {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM transactiontypes
                        JOIN transactiontypedetails ON trxtyp_activedetailid = trxtypdt_transactiontypedetailid
                        """);
    }

    public TransactionType getTransactionTypeByName(String transactionTypeName, EntityPermission entityPermission) {
        TransactionType transactionType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactiontypes, transactiontypedetails " +
                        "WHERE trxtyp_activedetailid = trxtypdt_transactiontypedetailid AND trxtypdt_transactiontypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactiontypes, transactiontypedetails " +
                        "WHERE trxtyp_activedetailid = trxtypdt_transactiontypedetailid AND trxtypdt_transactiontypename = ? " +
                        "FOR UPDATE";
            }

            var ps = TransactionTypeFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, transactionTypeName);
            
            transactionType = TransactionTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionType;
    }
    
    public TransactionType getTransactionTypeByName(String transactionTypeName) {
        return getTransactionTypeByName(transactionTypeName, EntityPermission.READ_ONLY);
    }
    
    public TransactionType getTransactionTypeByNameForUpdate(String transactionTypeName) {
        return getTransactionTypeByName(transactionTypeName, EntityPermission.READ_WRITE);
    }
    
    public TransactionTypeDetailValue getTransactionTypeDetailValueByNameForUpdate(String transactionTypeName) {
        return getTransactionTypeDetailValueForUpdate(getTransactionTypeByNameForUpdate(transactionTypeName));
    }
    
    public TransactionTypeDetailValue getTransactionTypeDetailValueForUpdate(TransactionType transactionType) {
        return transactionType == null? null: transactionType.getLastDetailForUpdate().getTransactionTypeDetailValue().clone();
    }
    
    private List<TransactionType> getTransactionTypes(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM transactiontypes, transactiontypedetails " +
                    "WHERE trxtyp_activedetailid = trxtypdt_transactiontypedetailid " +
                    "ORDER BY trxtypdt_transactiontypename " +
                    "_LIMIT_";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM transactiontypes, transactiontypedetails " +
                    "WHERE trxtyp_activedetailid = trxtypdt_transactiontypedetailid " +
                    "FOR UPDATE";
        }

        var ps = TransactionTypeFactory.getInstance().prepareStatement(query);
        
        return TransactionTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<TransactionType> getTransactionTypes() {
        return getTransactionTypes(EntityPermission.READ_ONLY);
    }
    
    public List<TransactionType> getTransactionTypesForUpdate() {
        return getTransactionTypes(EntityPermission.READ_WRITE);
    }
    
    public TransactionTypeTransfer getTransactionTypeTransfer(UserVisit userVisit, TransactionType transactionType) {
        return getAccountingTransferCaches(userVisit).getTransactionTypeTransferCache().getTransfer(transactionType);
    }
    
    public List<TransactionTypeTransfer> getTransactionTypeTransfers(UserVisit userVisit, Collection<TransactionType> transactionTypes) {
        List<TransactionTypeTransfer> transactionTypeTransfers = new ArrayList<>(transactionTypes.size());
        var transactionTypeTransferCache = getAccountingTransferCaches(userVisit).getTransactionTypeTransferCache();
        
        transactionTypes.forEach((transactionType) ->
                transactionTypeTransfers.add(transactionTypeTransferCache.getTransfer(transactionType))
        );
        
        return transactionTypeTransfers;
    }
    
    public List<TransactionTypeTransfer> getTransactionTypeTransfers(UserVisit userVisit) {
        return getTransactionTypeTransfers(userVisit, getTransactionTypes());
    }
    
    public void updateTransactionTypeFromValue(TransactionTypeDetailValue transactionTypeDetailValue, BasePK updatedBy) {
        var transactionType = TransactionTypeFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, transactionTypeDetailValue.getTransactionTypePK());
        var transactionTypeDetail = transactionType.getActiveDetailForUpdate();

        transactionTypeDetail.setThruTime(session.START_TIME_LONG);
        transactionTypeDetail.store();

        var transactionTypePK = transactionTypeDetail.getTransactionTypePK(); // Not updated
        var transactionTypeName = transactionTypeDetailValue.getTransactionTypeName();
        var sortOrder = transactionTypeDetailValue.getSortOrder();

        transactionTypeDetail = TransactionTypeDetailFactory.getInstance().create(transactionTypePK, transactionTypeName, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        transactionType.setActiveDetail(transactionTypeDetail);
        transactionType.setLastDetail(transactionTypeDetail);

        sendEvent(transactionTypePK, EventTypes.MODIFY, null, null, updatedBy);
    }
    
    public void deleteTransactionType(TransactionType transactionType, BasePK deletedBy) {
        deleteTransactionGlAccountCategoriesByTransactionType(transactionType, deletedBy);
        deleteTransactionEntityRoleTypesByTransactionType(transactionType, deletedBy);
        deleteTransactionTypeDescriptionsByTransactionType(transactionType, deletedBy);

        var transactionTypeDetail = transactionType.getLastDetailForUpdate();
        transactionTypeDetail.setThruTime(session.START_TIME_LONG);
        transactionType.setActiveDetail(null);
        transactionType.store();
        
        sendEvent(transactionType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteTransactionTypes(List<TransactionType> transactionTypes, BasePK deletedBy) {
        transactionTypes.forEach((transactionType) -> 
                deleteTransactionType(transactionType, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Type Descriptions
    // --------------------------------------------------------------------------------
    
    public TransactionTypeDescription createTransactionTypeDescription(TransactionType transactionType, Language language, String description, BasePK createdBy) {
        var transactionTypeDescription = TransactionTypeDescriptionFactory.getInstance().create(transactionType, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEvent(transactionType.getPrimaryKey(), EventTypes.MODIFY, transactionTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return transactionTypeDescription;
    }
    
    private TransactionTypeDescription getTransactionTypeDescription(TransactionType transactionType, Language language, EntityPermission entityPermission) {
        TransactionTypeDescription transactionTypeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactiontypedescriptions " +
                        "WHERE trxtypd_trxtyp_transactiontypeid = ? AND trxtypd_lang_languageid = ? AND trxtypd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactiontypedescriptions " +
                        "WHERE trxtypd_trxtyp_transactiontypeid = ? AND trxtypd_lang_languageid = ? AND trxtypd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = TransactionTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, transactionType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            transactionTypeDescription = TransactionTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionTypeDescription;
    }
    
    public TransactionTypeDescription getTransactionTypeDescription(TransactionType transactionType, Language language) {
        return getTransactionTypeDescription(transactionType, language, EntityPermission.READ_ONLY);
    }
    
    public TransactionTypeDescription getTransactionTypeDescriptionForUpdate(TransactionType transactionType, Language language) {
        return getTransactionTypeDescription(transactionType, language, EntityPermission.READ_WRITE);
    }
    
    public TransactionTypeDescriptionValue getTransactionTypeDescriptionValue(TransactionTypeDescription transactionTypeDescription) {
        return transactionTypeDescription == null? null: transactionTypeDescription.getTransactionTypeDescriptionValue().clone();
    }
    
    public TransactionTypeDescriptionValue getTransactionTypeDescriptionValueForUpdate(TransactionType transactionType, Language language) {
        return getTransactionTypeDescriptionValue(getTransactionTypeDescriptionForUpdate(transactionType, language));
    }
    
    private List<TransactionTypeDescription> getTransactionTypeDescriptionsByTransactionType(TransactionType transactionType, EntityPermission entityPermission) {
        List<TransactionTypeDescription> transactionTypeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactiontypedescriptions, languages " +
                        "WHERE trxtypd_trxtyp_transactiontypeid = ? AND trxtypd_thrutime = ? AND trxtypd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactiontypedescriptions " +
                        "WHERE trxtypd_trxtyp_transactiontypeid = ? AND trxtypd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = TransactionTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, transactionType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            transactionTypeDescriptions = TransactionTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionTypeDescriptions;
    }
    
    public List<TransactionTypeDescription> getTransactionTypeDescriptionsByTransactionType(TransactionType transactionType) {
        return getTransactionTypeDescriptionsByTransactionType(transactionType, EntityPermission.READ_ONLY);
    }
    
    public List<TransactionTypeDescription> getTransactionTypeDescriptionsByTransactionTypeForUpdate(TransactionType transactionType) {
        return getTransactionTypeDescriptionsByTransactionType(transactionType, EntityPermission.READ_WRITE);
    }
    
    public String getBestTransactionTypeDescription(TransactionType transactionType, Language language) {
        String description;
        var transactionTypeDescription = getTransactionTypeDescription(transactionType, language);
        
        if(transactionTypeDescription == null && !language.getIsDefault()) {
            transactionTypeDescription = getTransactionTypeDescription(transactionType, getPartyControl().getDefaultLanguage());
        }
        
        if(transactionTypeDescription == null) {
            description = transactionType.getLastDetail().getTransactionTypeName();
        } else {
            description = transactionTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public TransactionTypeDescriptionTransfer getTransactionTypeDescriptionTransfer(UserVisit userVisit, TransactionTypeDescription transactionTypeDescription) {
        return getAccountingTransferCaches(userVisit).getTransactionTypeDescriptionTransferCache().getTransfer(transactionTypeDescription);
    }
    
    public List<TransactionTypeDescriptionTransfer> getTransactionTypeDescriptionTransfersByTransactionType(UserVisit userVisit, TransactionType transactionType) {
        var transactionTypeDescriptions = getTransactionTypeDescriptionsByTransactionType(transactionType);
        List<TransactionTypeDescriptionTransfer> transactionTypeDescriptionTransfers = new ArrayList<>(transactionTypeDescriptions.size());
        var transactionTypeDescriptionTransferCache = getAccountingTransferCaches(userVisit).getTransactionTypeDescriptionTransferCache();
        
        transactionTypeDescriptions.forEach((transactionTypeDescription) ->
                transactionTypeDescriptionTransfers.add(transactionTypeDescriptionTransferCache.getTransfer(transactionTypeDescription))
        );
        
        return transactionTypeDescriptionTransfers;
    }
    
    public void updateTransactionTypeDescriptionFromValue(TransactionTypeDescriptionValue transactionTypeDescriptionValue, BasePK updatedBy) {
        if(transactionTypeDescriptionValue.hasBeenModified()) {
            var transactionTypeDescription = TransactionTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, transactionTypeDescriptionValue.getPrimaryKey());
            
            transactionTypeDescription.setThruTime(session.START_TIME_LONG);
            transactionTypeDescription.store();

            var transactionType = transactionTypeDescription.getTransactionType();
            var language = transactionTypeDescription.getLanguage();
            var description = transactionTypeDescriptionValue.getDescription();
            
            transactionTypeDescription = TransactionTypeDescriptionFactory.getInstance().create(transactionType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(transactionType.getPrimaryKey(), EventTypes.MODIFY, transactionTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteTransactionTypeDescription(TransactionTypeDescription transactionTypeDescription, BasePK deletedBy) {
        transactionTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(transactionTypeDescription.getTransactionTypePK(), EventTypes.MODIFY, transactionTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteTransactionTypeDescriptionsByTransactionType(TransactionType transactionType, BasePK deletedBy) {
        var transactionTypeDescriptions = getTransactionTypeDescriptionsByTransactionTypeForUpdate(transactionType);
        
        transactionTypeDescriptions.forEach((transactionTypeDescription) -> 
                deleteTransactionTypeDescription(transactionTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Gl Account Categories
    // --------------------------------------------------------------------------------
    
    public TransactionGlAccountCategory createTransactionGlAccountCategory(TransactionType transactionType, String transactionGlAccountCategoryName, GlAccountCategory glAccountCategory,
            Integer sortOrder, BasePK createdBy) {
        var transactionGlAccountCategory = TransactionGlAccountCategoryFactory.getInstance().create();
        var transactionGlAccountCategoryDetail = TransactionGlAccountCategoryDetailFactory.getInstance().create(transactionGlAccountCategory, transactionType,
                transactionGlAccountCategoryName, glAccountCategory, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        transactionGlAccountCategory = TransactionGlAccountCategoryFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, transactionGlAccountCategory.getPrimaryKey());
        transactionGlAccountCategory.setActiveDetail(transactionGlAccountCategoryDetail);
        transactionGlAccountCategory.setLastDetail(transactionGlAccountCategoryDetail);
        transactionGlAccountCategory.store();
        
        sendEvent(transactionGlAccountCategory.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return transactionGlAccountCategory;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.TransactionGlAccountCategory */
    public TransactionGlAccountCategory getTransactionGlAccountCategoryByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new TransactionGlAccountCategoryPK(entityInstance.getEntityUniqueId());

        return TransactionGlAccountCategoryFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public TransactionGlAccountCategory getTransactionGlAccountCategoryByEntityInstance(EntityInstance entityInstance) {
        return getTransactionGlAccountCategoryByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public TransactionGlAccountCategory getTransactionGlAccountCategoryByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getTransactionGlAccountCategoryByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countTransactionGlAccountCategoriesByTransactionType(final TransactionType transactionType) {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM transactionglaccountcategories
                        JOIN transactionglaccountcategorydetails ON trxglac_activedetailid = trxglacdt_transactionglaccountcategorydetailid
                        WHERE trxglacdt_trxtyp_transactiontypeid = ?
                        """, transactionType);
    }
    
    public TransactionGlAccountCategory getTransactionGlAccountCategoryByName(TransactionType transactionType, String transactionGlAccountCategoryName, EntityPermission entityPermission) {
        TransactionGlAccountCategory transactionGlAccountCategory;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionglaccountcategories, transactionglaccountcategorydetails " +
                        "WHERE trxglac_activedetailid = trxglacdt_transactionglaccountcategorydetailid AND trxglacdt_trxtyp_transactiontypeid = ? AND trxglacdt_transactionglaccountcategoryname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionglaccountcategories, transactionglaccountcategorydetails " +
                        "WHERE trxglac_activedetailid = trxglacdt_transactionglaccountcategorydetailid AND trxglacdt_trxtyp_transactiontypeid = ? AND trxglacdt_transactionglaccountcategoryname = ? " +
                        "FOR UPDATE";
            }

            var ps = TransactionGlAccountCategoryFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, transactionType.getPrimaryKey().getEntityId());
            ps.setString(2, transactionGlAccountCategoryName);
            
            transactionGlAccountCategory = TransactionGlAccountCategoryFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionGlAccountCategory;
    }
    
    public TransactionGlAccountCategory getTransactionGlAccountCategoryByName(TransactionType transactionType, String transactionGlAccountCategoryName) {
        return getTransactionGlAccountCategoryByName(transactionType, transactionGlAccountCategoryName, EntityPermission.READ_ONLY);
    }
    
    public TransactionGlAccountCategory getTransactionGlAccountCategoryByNameForUpdate(TransactionType transactionType, String transactionGlAccountCategoryName) {
        return getTransactionGlAccountCategoryByName(transactionType, transactionGlAccountCategoryName, EntityPermission.READ_WRITE);
    }
    
    public TransactionGlAccountCategoryDetailValue getTransactionGlAccountCategoryDetailValueByNameForUpdate(TransactionType transactionType, String transactionGlAccountCategoryName) {
        return getTransactionGlAccountCategoryDetailValueForUpdate(getTransactionGlAccountCategoryByNameForUpdate(transactionType, transactionGlAccountCategoryName));
    }
    
    public TransactionGlAccountCategoryDetailValue getTransactionGlAccountCategoryDetailValueForUpdate(TransactionGlAccountCategory transactionGlAccountCategory) {
        return transactionGlAccountCategory == null? null: transactionGlAccountCategory.getLastDetailForUpdate().getTransactionGlAccountCategoryDetailValue().clone();
    }
    
    private List<TransactionGlAccountCategory> getTransactionGlAccountCategoriesByTransactionType(TransactionType transactionType, EntityPermission entityPermission) {
        List<TransactionGlAccountCategory> transactionGlAccountCategories;
        
        try {
            String query = null;
            
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM transactionglaccountcategories, transactionglaccountcategorydetails " +
                    "WHERE trxglac_activedetailid = trxglacdt_transactionglaccountcategorydetailid AND trxglacdt_trxtyp_transactiontypeid = ? " +
                    "ORDER BY trxglacdt_sortorder, trxglacdt_transactionglaccountcategoryname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM transactionglaccountcategories, transactionglaccountcategorydetails " +
                    "WHERE trxglac_activedetailid = trxglacdt_transactionglaccountcategorydetailid AND trxglacdt_trxtyp_transactiontypeid = ? " +
                    "FOR UPDATE";
        }

            var ps = TransactionGlAccountCategoryFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, transactionType.getPrimaryKey().getEntityId());
            
            transactionGlAccountCategories = TransactionGlAccountCategoryFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionGlAccountCategories;
    }
    
    public List<TransactionGlAccountCategory> getTransactionGlAccountCategoriesByTransactionType(TransactionType transactionType) {
        return getTransactionGlAccountCategoriesByTransactionType(transactionType, EntityPermission.READ_ONLY);
    }
    
    public List<TransactionGlAccountCategory> getTransactionGlAccountCategoriesByTransactionTypeForUpdate(TransactionType transactionType) {
        return getTransactionGlAccountCategoriesByTransactionType(transactionType, EntityPermission.READ_WRITE);
    }
    
    public TransactionGlAccountCategoryTransfer getTransactionGlAccountCategoryTransfer(UserVisit userVisit, TransactionGlAccountCategory transactionGlAccountCategory) {
        return getAccountingTransferCaches(userVisit).getTransactionGlAccountCategoryTransferCache().getTransfer(transactionGlAccountCategory);
    }
    
    public List<TransactionGlAccountCategoryTransfer> getTransactionGlAccountCategoryTransfers(UserVisit userVisit, Collection<TransactionGlAccountCategory> transactionGlAccountCategories) {
        List<TransactionGlAccountCategoryTransfer> transactionGlAccountCategoryTransfers = new ArrayList<>(transactionGlAccountCategories.size());
        var transactionGlAccountCategoryTransferCache = getAccountingTransferCaches(userVisit).getTransactionGlAccountCategoryTransferCache();
        
        transactionGlAccountCategories.forEach((transactionGlAccountCategory) ->
                transactionGlAccountCategoryTransfers.add(transactionGlAccountCategoryTransferCache.getTransfer(transactionGlAccountCategory))
        );
        
        return transactionGlAccountCategoryTransfers;
    }
    
    public List<TransactionGlAccountCategoryTransfer> getTransactionGlAccountCategoryTransfersByTransactionType(UserVisit userVisit, TransactionType transactionType) {
        return getTransactionGlAccountCategoryTransfers(userVisit, getTransactionGlAccountCategoriesByTransactionType(transactionType));
    }
    
    public void updateTransactionGlAccountCategoryFromValue(TransactionGlAccountCategoryDetailValue transactionGlAccountCategoryDetailValue, BasePK updatedBy) {
        var transactionGlAccountCategory = TransactionGlAccountCategoryFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, transactionGlAccountCategoryDetailValue.getTransactionGlAccountCategoryPK());
        var transactionGlAccountCategoryDetail = transactionGlAccountCategory.getActiveDetailForUpdate();

        transactionGlAccountCategoryDetail.setThruTime(session.START_TIME_LONG);
        transactionGlAccountCategoryDetail.store();

        var transactionGlAccountCategoryPK = transactionGlAccountCategoryDetail.getTransactionGlAccountCategoryPK(); // Not updated
        var transactionTypePK = transactionGlAccountCategoryDetail.getTransactionTypePK(); // Not updated
        var transactionGlAccountCategoryName = transactionGlAccountCategoryDetailValue.getTransactionGlAccountCategoryName();
        var glAccountCategoryPK = transactionGlAccountCategoryDetailValue.getGlAccountCategoryPK();
        var sortOrder = transactionGlAccountCategoryDetailValue.getSortOrder();

        transactionGlAccountCategoryDetail = TransactionGlAccountCategoryDetailFactory.getInstance().create(transactionGlAccountCategoryPK, transactionTypePK,
                transactionGlAccountCategoryName, glAccountCategoryPK, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        transactionGlAccountCategory.setActiveDetail(transactionGlAccountCategoryDetail);
        transactionGlAccountCategory.setLastDetail(transactionGlAccountCategoryDetail);

        sendEvent(transactionGlAccountCategoryPK, EventTypes.MODIFY, null, null, updatedBy);
    }
    
    public void deleteTransactionGlAccountCategory(TransactionGlAccountCategory transactionGlAccountCategory, BasePK deletedBy) {
        // TODO: deleteTransactionGlAccountByTransactionGlAccountCategory(transactionGlAccountCategory, deletedBy);
        deleteTransactionGlAccountCategoryDescriptionsByTransactionGlAccountCategory(transactionGlAccountCategory, deletedBy);

        var transactionGlAccountCategoryDetail = transactionGlAccountCategory.getLastDetailForUpdate();
        transactionGlAccountCategoryDetail.setThruTime(session.START_TIME_LONG);
        transactionGlAccountCategory.setActiveDetail(null);
        transactionGlAccountCategory.store();
        
        sendEvent(transactionGlAccountCategory.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteTransactionGlAccountCategories(List<TransactionGlAccountCategory> transactionGlAccountCategories, BasePK deletedBy) {
        transactionGlAccountCategories.forEach((transactionGlAccountCategory) -> 
                deleteTransactionGlAccountCategory(transactionGlAccountCategory, deletedBy)
        );
    }
    
    public void deleteTransactionGlAccountCategoriesByTransactionType(TransactionType transactionType, BasePK deletedBy) {
        deleteTransactionGlAccountCategories(getTransactionGlAccountCategoriesByTransactionTypeForUpdate(transactionType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Gl Account Category Descriptions
    // --------------------------------------------------------------------------------
    
     public TransactionGlAccountCategoryDescription createTransactionGlAccountCategoryDescription(TransactionGlAccountCategory transactionGlAccountCategory, Language language, String description,
             BasePK createdBy) {
         var transactionGlAccountCategoryDescription = TransactionGlAccountCategoryDescriptionFactory.getInstance().create(transactionGlAccountCategory,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(transactionGlAccountCategory.getPrimaryKey(), EventTypes.MODIFY, transactionGlAccountCategoryDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return transactionGlAccountCategoryDescription;
    }
    
    private TransactionGlAccountCategoryDescription getTransactionGlAccountCategoryDescription(TransactionGlAccountCategory transactionGlAccountCategory, Language language, EntityPermission entityPermission) {
        TransactionGlAccountCategoryDescription transactionGlAccountCategoryDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionglaccountcategorydescriptions " +
                        "WHERE trxglacd_trxglac_transactionglaccountcategoryid = ? AND trxglacd_lang_languageid = ? AND trxglacd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionglaccountcategorydescriptions " +
                        "WHERE trxglacd_trxglac_transactionglaccountcategoryid = ? AND trxglacd_lang_languageid = ? AND trxglacd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = TransactionGlAccountCategoryDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, transactionGlAccountCategory.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            transactionGlAccountCategoryDescription = TransactionGlAccountCategoryDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionGlAccountCategoryDescription;
    }
    
    public TransactionGlAccountCategoryDescription getTransactionGlAccountCategoryDescription(TransactionGlAccountCategory transactionGlAccountCategory, Language language) {
        return getTransactionGlAccountCategoryDescription(transactionGlAccountCategory, language, EntityPermission.READ_ONLY);
    }
    
    public TransactionGlAccountCategoryDescription getTransactionGlAccountCategoryDescriptionForUpdate(TransactionGlAccountCategory transactionGlAccountCategory, Language language) {
        return getTransactionGlAccountCategoryDescription(transactionGlAccountCategory, language, EntityPermission.READ_WRITE);
    }
    
    public TransactionGlAccountCategoryDescriptionValue getTransactionGlAccountCategoryDescriptionValue(TransactionGlAccountCategoryDescription transactionGlAccountCategoryDescription) {
        return transactionGlAccountCategoryDescription == null? null: transactionGlAccountCategoryDescription.getTransactionGlAccountCategoryDescriptionValue().clone();
    }
    
    public TransactionGlAccountCategoryDescriptionValue getTransactionGlAccountCategoryDescriptionValueForUpdate(TransactionGlAccountCategory transactionGlAccountCategory, Language language) {
        return getTransactionGlAccountCategoryDescriptionValue(getTransactionGlAccountCategoryDescriptionForUpdate(transactionGlAccountCategory, language));
    }
    
    private List<TransactionGlAccountCategoryDescription> getTransactionGlAccountCategoryDescriptionsByTransactionGlAccountCategory(TransactionGlAccountCategory transactionGlAccountCategory, EntityPermission entityPermission) {
        List<TransactionGlAccountCategoryDescription> transactionGlAccountCategoryDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionglaccountcategorydescriptions, languages " +
                        "WHERE trxglacd_trxglac_transactionglaccountcategoryid = ? AND trxglacd_thrutime = ? AND trxglacd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionglaccountcategorydescriptions " +
                        "WHERE trxglacd_trxglac_transactionglaccountcategoryid = ? AND trxglacd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = TransactionGlAccountCategoryDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, transactionGlAccountCategory.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            transactionGlAccountCategoryDescriptions = TransactionGlAccountCategoryDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionGlAccountCategoryDescriptions;
    }
    
    public List<TransactionGlAccountCategoryDescription> getTransactionGlAccountCategoryDescriptionsByTransactionGlAccountCategory(TransactionGlAccountCategory transactionGlAccountCategory) {
        return getTransactionGlAccountCategoryDescriptionsByTransactionGlAccountCategory(transactionGlAccountCategory, EntityPermission.READ_ONLY);
    }
    
    public List<TransactionGlAccountCategoryDescription> getTransactionGlAccountCategoryDescriptionsByTransactionGlAccountCategoryForUpdate(TransactionGlAccountCategory transactionGlAccountCategory) {
        return getTransactionGlAccountCategoryDescriptionsByTransactionGlAccountCategory(transactionGlAccountCategory, EntityPermission.READ_WRITE);
    }
    
    public String getBestTransactionGlAccountCategoryDescription(TransactionGlAccountCategory transactionGlAccountCategory, Language language) {
        String description;
        var transactionGlAccountCategoryDescription = getTransactionGlAccountCategoryDescription(transactionGlAccountCategory, language);
        
        if(transactionGlAccountCategoryDescription == null && !language.getIsDefault()) {
            transactionGlAccountCategoryDescription = getTransactionGlAccountCategoryDescription(transactionGlAccountCategory, getPartyControl().getDefaultLanguage());
        }
        
        if(transactionGlAccountCategoryDescription == null) {
            description = transactionGlAccountCategory.getLastDetail().getTransactionGlAccountCategoryName();
        } else {
            description = transactionGlAccountCategoryDescription.getDescription();
        }
        
        return description;
    }
    
    public TransactionGlAccountCategoryDescriptionTransfer getTransactionGlAccountCategoryDescriptionTransfer(UserVisit userVisit, TransactionGlAccountCategoryDescription transactionGlAccountCategoryDescription) {
        return getAccountingTransferCaches(userVisit).getTransactionGlAccountCategoryDescriptionTransferCache().getTransfer(transactionGlAccountCategoryDescription);
    }
    
    public List<TransactionGlAccountCategoryDescriptionTransfer> getTransactionGlAccountCategoryDescriptionTransfersByTransactionGlAccountCategory(UserVisit userVisit, TransactionGlAccountCategory transactionGlAccountCategory) {
        var transactionGlAccountCategoryDescriptions = getTransactionGlAccountCategoryDescriptionsByTransactionGlAccountCategory(transactionGlAccountCategory);
        List<TransactionGlAccountCategoryDescriptionTransfer> transactionGlAccountCategoryDescriptionTransfers = new ArrayList<>(transactionGlAccountCategoryDescriptions.size());
        var transactionGlAccountCategoryDescriptionTransferCache = getAccountingTransferCaches(userVisit).getTransactionGlAccountCategoryDescriptionTransferCache();
        
        transactionGlAccountCategoryDescriptions.forEach((transactionGlAccountCategoryDescription) ->
                transactionGlAccountCategoryDescriptionTransfers.add(transactionGlAccountCategoryDescriptionTransferCache.getTransfer(transactionGlAccountCategoryDescription))
        );
        
        return transactionGlAccountCategoryDescriptionTransfers;
    }
    
    public void updateTransactionGlAccountCategoryDescriptionFromValue(TransactionGlAccountCategoryDescriptionValue transactionGlAccountCategoryDescriptionValue, BasePK updatedBy) {
        if(transactionGlAccountCategoryDescriptionValue.hasBeenModified()) {
            var transactionGlAccountCategoryDescription = TransactionGlAccountCategoryDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, transactionGlAccountCategoryDescriptionValue.getPrimaryKey());
            
            transactionGlAccountCategoryDescription.setThruTime(session.START_TIME_LONG);
            transactionGlAccountCategoryDescription.store();

            var transactionGlAccountCategory = transactionGlAccountCategoryDescription.getTransactionGlAccountCategory();
            var language = transactionGlAccountCategoryDescription.getLanguage();
            var description = transactionGlAccountCategoryDescriptionValue.getDescription();
            
            transactionGlAccountCategoryDescription = TransactionGlAccountCategoryDescriptionFactory.getInstance().create(transactionGlAccountCategory, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(transactionGlAccountCategory.getPrimaryKey(), EventTypes.MODIFY, transactionGlAccountCategoryDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteTransactionGlAccountCategoryDescription(TransactionGlAccountCategoryDescription transactionGlAccountCategoryDescription, BasePK deletedBy) {
        transactionGlAccountCategoryDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(transactionGlAccountCategoryDescription.getTransactionGlAccountCategoryPK(), EventTypes.MODIFY, transactionGlAccountCategoryDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteTransactionGlAccountCategoryDescriptionsByTransactionGlAccountCategory(TransactionGlAccountCategory transactionGlAccountCategory, BasePK deletedBy) {
        var transactionGlAccountCategoryDescriptions = getTransactionGlAccountCategoryDescriptionsByTransactionGlAccountCategoryForUpdate(transactionGlAccountCategory);
        
        transactionGlAccountCategoryDescriptions.forEach((transactionGlAccountCategoryDescription) -> 
                deleteTransactionGlAccountCategoryDescription(transactionGlAccountCategoryDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Entity Role Types
    // --------------------------------------------------------------------------------
    
     public TransactionEntityRoleType createTransactionEntityRoleType(TransactionType transactionType, String transactionEntityRoleTypeName, EntityType entityType,
            Integer sortOrder, BasePK createdBy) {
         var transactionEntityRoleType = TransactionEntityRoleTypeFactory.getInstance().create();
         var transactionEntityRoleTypeDetail = TransactionEntityRoleTypeDetailFactory.getInstance().create(transactionEntityRoleType, transactionType,
                transactionEntityRoleTypeName, entityType, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        transactionEntityRoleType = TransactionEntityRoleTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, transactionEntityRoleType.getPrimaryKey());
        transactionEntityRoleType.setActiveDetail(transactionEntityRoleTypeDetail);
        transactionEntityRoleType.setLastDetail(transactionEntityRoleTypeDetail);
        transactionEntityRoleType.store();
        
        sendEvent(transactionEntityRoleType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return transactionEntityRoleType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.TransactionEntityRoleType */
    public TransactionEntityRoleType getTransactionEntityRoleTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new TransactionEntityRoleTypePK(entityInstance.getEntityUniqueId());

        return TransactionEntityRoleTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public TransactionEntityRoleType getTransactionEntityRoleTypeByEntityInstance(EntityInstance entityInstance) {
        return getTransactionEntityRoleTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public TransactionEntityRoleType getTransactionEntityRoleTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getTransactionEntityRoleTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countTransactionEntityRoleTypesByTransactionType(final TransactionType transactionType) {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM transactionentityroletypes
                        JOIN transactionentityroletypedetails ON trxertyp_activedetailid = trxertypdt_transactionentityroletypedetailid
                        WHERE trxertypdt_trxtyp_transactiontypeid = ?
                        """, transactionType);
    }
    
    public TransactionEntityRoleType getTransactionEntityRoleTypeByName(TransactionType transactionType, String transactionEntityRoleTypeName, EntityPermission entityPermission) {
        TransactionEntityRoleType transactionEntityRoleType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionentityroletypes, transactionentityroletypedetails " +
                        "WHERE trxertyp_activedetailid = trxertypdt_transactionentityroletypedetailid AND trxertypdt_trxtyp_transactiontypeid = ? AND trxertypdt_transactionentityroletypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionentityroletypes, transactionentityroletypedetails " +
                        "WHERE trxertyp_activedetailid = trxertypdt_transactionentityroletypedetailid AND trxertypdt_trxtyp_transactiontypeid = ? AND trxertypdt_transactionentityroletypename = ? " +
                        "FOR UPDATE";
            }

            var ps = TransactionEntityRoleTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, transactionType.getPrimaryKey().getEntityId());
            ps.setString(2, transactionEntityRoleTypeName);
            
            transactionEntityRoleType = TransactionEntityRoleTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionEntityRoleType;
    }
    
    public TransactionEntityRoleType getTransactionEntityRoleTypeByName(TransactionType transactionType, String transactionEntityRoleTypeName) {
        return getTransactionEntityRoleTypeByName(transactionType, transactionEntityRoleTypeName, EntityPermission.READ_ONLY);
    }
    
    public TransactionEntityRoleType getTransactionEntityRoleTypeByNameForUpdate(TransactionType transactionType, String transactionEntityRoleTypeName) {
        return getTransactionEntityRoleTypeByName(transactionType, transactionEntityRoleTypeName, EntityPermission.READ_WRITE);
    }
    
    public TransactionEntityRoleTypeDetailValue getTransactionEntityRoleTypeDetailValueByNameForUpdate(TransactionType transactionType, String transactionEntityRoleTypeName) {
        return getTransactionEntityRoleTypeDetailValueForUpdate(getTransactionEntityRoleTypeByNameForUpdate(transactionType, transactionEntityRoleTypeName));
    }
    
    public TransactionEntityRoleTypeDetailValue getTransactionEntityRoleTypeDetailValueForUpdate(TransactionEntityRoleType transactionEntityRoleType) {
        return transactionEntityRoleType == null? null: transactionEntityRoleType.getLastDetailForUpdate().getTransactionEntityRoleTypeDetailValue().clone();
    }
    
    private List<TransactionEntityRoleType> getTransactionEntityRoleTypesByTransactionType(TransactionType transactionType, EntityPermission entityPermission) {
        List<TransactionEntityRoleType> transactionEntityRoleTypes;
        
        try {
            String query = null;
            
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM transactionentityroletypes, transactionentityroletypedetails " +
                    "WHERE trxertyp_activedetailid = trxertypdt_transactionentityroletypedetailid AND trxertypdt_trxtyp_transactiontypeid = ? " +
                    "ORDER BY trxertypdt_sortorder, trxertypdt_transactionentityroletypename " +
                    "_LIMIT_";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM transactionentityroletypes, transactionentityroletypedetails " +
                    "WHERE trxertyp_activedetailid = trxertypdt_transactionentityroletypedetailid AND trxertypdt_trxtyp_transactiontypeid = ? " +
                    "FOR UPDATE";
        }

            var ps = TransactionEntityRoleTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, transactionType.getPrimaryKey().getEntityId());
            
            transactionEntityRoleTypes = TransactionEntityRoleTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionEntityRoleTypes;
    }
    
    public List<TransactionEntityRoleType> getTransactionEntityRoleTypesByTransactionType(TransactionType transactionType) {
        return getTransactionEntityRoleTypesByTransactionType(transactionType, EntityPermission.READ_ONLY);
    }
    
    public List<TransactionEntityRoleType> getTransactionEntityRoleTypesByTransactionTypeForUpdate(TransactionType transactionType) {
        return getTransactionEntityRoleTypesByTransactionType(transactionType, EntityPermission.READ_WRITE);
    }
    
    private List<TransactionEntityRoleType> getTransactionEntityRoleTypesByEntityType(EntityType entityType, EntityPermission entityPermission) {
        List<TransactionEntityRoleType> transactionEntityRoleTypes;
        
        try {
            String query = null;
            
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM transactionentityroletypes, transactionentityroletypedetails, transactions, transactiondetails " +
                    "WHERE trxertyp_activedetailid = trxertypdt_transactionentityroletypedetailid AND trxertypdt_ent_entitytypeid = ? " +
                    "AND trxertypdt_trxtyp_transactiontypeid = trx_transactionid AND trx_lastdetailid = trxdt_transactiondetailid " +
                    "ORDER BY trxertypdt_sortorder, trxertypdt_transactionentityroletypename, trxdt_transactionname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM transactionentityroletypes, transactionentityroletypedetails " +
                    "WHERE trxertyp_activedetailid = trxertypdt_transactionentityroletypedetailid AND trxertypdt_trxtyp_transactiontypeid = ? " +
                    "FOR UPDATE";
        }

            var ps = TransactionEntityRoleTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            
            transactionEntityRoleTypes = TransactionEntityRoleTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionEntityRoleTypes;
    }
    
    public List<TransactionEntityRoleType> getTransactionEntityRoleTypesByEntityType(EntityType entityType) {
        return getTransactionEntityRoleTypesByEntityType(entityType, EntityPermission.READ_ONLY);
    }
    
    public List<TransactionEntityRoleType> getTransactionEntityRoleTypesByEntityTypeForUpdate(EntityType entityType) {
        return getTransactionEntityRoleTypesByEntityType(entityType, EntityPermission.READ_WRITE);
    }
    
    public TransactionEntityRoleTypeTransfer getTransactionEntityRoleTypeTransfer(UserVisit userVisit, TransactionEntityRoleType transactionEntityRoleType) {
        return getAccountingTransferCaches(userVisit).getTransactionEntityRoleTypeTransferCache().getTransfer(transactionEntityRoleType);
    }
    
    public List<TransactionEntityRoleTypeTransfer> getTransactionEntityRoleTypeTransfers(UserVisit userVisit, Collection<TransactionEntityRoleType> transactionEntityRoleTypes) {
        var transactionEntityRoleTypeTransfers = new ArrayList<TransactionEntityRoleTypeTransfer>(transactionEntityRoleTypes.size());
        var transactionEntityRoleTypeTransferCache = getAccountingTransferCaches(userVisit).getTransactionEntityRoleTypeTransferCache();
        
        transactionEntityRoleTypes.forEach((transactionEntityRoleType) ->
                transactionEntityRoleTypeTransfers.add(transactionEntityRoleTypeTransferCache.getTransfer(transactionEntityRoleType))
        );
        
        return transactionEntityRoleTypeTransfers;
    }
    
    public List<TransactionEntityRoleTypeTransfer> getTransactionEntityRoleTypeTransfersByTransactionType(UserVisit userVisit, TransactionType transactionType) {
        return getTransactionEntityRoleTypeTransfers(userVisit, getTransactionEntityRoleTypesByTransactionType(transactionType));
    }
    
    public List<TransactionEntityRoleTypeTransfer> getTransactionEntityRoleTypeTransfersByEntityType(UserVisit userVisit, EntityType entityType) {
        return getTransactionEntityRoleTypeTransfers(userVisit, getTransactionEntityRoleTypesByEntityType(entityType));
    }
    
    public void updateTransactionEntityRoleTypeFromValue(TransactionEntityRoleTypeDetailValue transactionEntityRoleTypeDetailValue, BasePK updatedBy) {
        var transactionEntityRoleType = TransactionEntityRoleTypeFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, transactionEntityRoleTypeDetailValue.getTransactionEntityRoleTypePK());
        var transactionEntityRoleTypeDetail = transactionEntityRoleType.getActiveDetailForUpdate();

        transactionEntityRoleTypeDetail.setThruTime(session.START_TIME_LONG);
        transactionEntityRoleTypeDetail.store();

        var transactionEntityRoleTypePK = transactionEntityRoleTypeDetail.getTransactionEntityRoleTypePK(); // Not updated
        var transactionTypePK = transactionEntityRoleTypeDetail.getTransactionTypePK(); // Not updated
        var transactionEntityRoleTypeName = transactionEntityRoleTypeDetailValue.getTransactionEntityRoleTypeName();
        var entityTypePK = transactionEntityRoleTypeDetailValue.getEntityTypePK();
        var sortOrder = transactionEntityRoleTypeDetailValue.getSortOrder();

        transactionEntityRoleTypeDetail = TransactionEntityRoleTypeDetailFactory.getInstance().create(transactionEntityRoleTypePK, transactionTypePK,
                transactionEntityRoleTypeName, entityTypePK, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        transactionEntityRoleType.setActiveDetail(transactionEntityRoleTypeDetail);
        transactionEntityRoleType.setLastDetail(transactionEntityRoleTypeDetail);

        sendEvent(transactionEntityRoleTypePK, EventTypes.MODIFY, null, null, updatedBy);
    }
    
    public void deleteTransactionEntityRoleType(TransactionEntityRoleType transactionEntityRoleType, BasePK deletedBy) {
        deleteTransactionEntityRoleTypeDescriptionsByTransactionEntityRoleType(transactionEntityRoleType, deletedBy);

        var transactionEntityRoleTypeDetail = transactionEntityRoleType.getLastDetailForUpdate();
        transactionEntityRoleTypeDetail.setThruTime(session.START_TIME_LONG);
        transactionEntityRoleType.setActiveDetail(null);
        transactionEntityRoleType.store();
        
        sendEvent(transactionEntityRoleType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteTransactionEntityRoleTypes(List<TransactionEntityRoleType> transactionEntityRoleTypes, BasePK deletedBy) {
        transactionEntityRoleTypes.forEach((transactionEntityRoleType) -> 
                deleteTransactionEntityRoleType(transactionEntityRoleType, deletedBy)
        );
    }
    
    public void deleteTransactionEntityRoleTypesByTransactionType(TransactionType transactionType, BasePK deletedBy) {
        deleteTransactionEntityRoleTypes(getTransactionEntityRoleTypesByTransactionTypeForUpdate(transactionType), deletedBy);
    }
    
    public void deleteTransactionEntityRoleTypesByEntityType(EntityType entityType, BasePK deletedBy) {
        deleteTransactionEntityRoleTypes(getTransactionEntityRoleTypesByEntityTypeForUpdate(entityType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Entity Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    public TransactionEntityRoleTypeDescription createTransactionEntityRoleTypeDescription(TransactionEntityRoleType transactionEntityRoleType, Language language, String description, BasePK createdBy) {
        var transactionEntityRoleTypeDescription = TransactionEntityRoleTypeDescriptionFactory.getInstance().create(transactionEntityRoleType, language,
                description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(transactionEntityRoleType.getPrimaryKey(), EventTypes.MODIFY, transactionEntityRoleTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return transactionEntityRoleTypeDescription;
    }
    
    private TransactionEntityRoleTypeDescription getTransactionEntityRoleTypeDescription(TransactionEntityRoleType transactionEntityRoleType, Language language, EntityPermission entityPermission) {
        TransactionEntityRoleTypeDescription transactionEntityRoleTypeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionentityroletypedescriptions " +
                        "WHERE trxertypd_trxertyp_transactionentityroletypeid = ? AND trxertypd_lang_languageid = ? AND trxertypd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionentityroletypedescriptions " +
                        "WHERE trxertypd_trxertyp_transactionentityroletypeid = ? AND trxertypd_lang_languageid = ? AND trxertypd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = TransactionEntityRoleTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, transactionEntityRoleType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            transactionEntityRoleTypeDescription = TransactionEntityRoleTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionEntityRoleTypeDescription;
    }
    
    public TransactionEntityRoleTypeDescription getTransactionEntityRoleTypeDescription(TransactionEntityRoleType transactionEntityRoleType, Language language) {
        return getTransactionEntityRoleTypeDescription(transactionEntityRoleType, language, EntityPermission.READ_ONLY);
    }
    
    public TransactionEntityRoleTypeDescription getTransactionEntityRoleTypeDescriptionForUpdate(TransactionEntityRoleType transactionEntityRoleType, Language language) {
        return getTransactionEntityRoleTypeDescription(transactionEntityRoleType, language, EntityPermission.READ_WRITE);
    }
    
    public TransactionEntityRoleTypeDescriptionValue getTransactionEntityRoleTypeDescriptionValue(TransactionEntityRoleTypeDescription transactionEntityRoleTypeDescription) {
        return transactionEntityRoleTypeDescription == null? null: transactionEntityRoleTypeDescription.getTransactionEntityRoleTypeDescriptionValue().clone();
    }
    
    public TransactionEntityRoleTypeDescriptionValue getTransactionEntityRoleTypeDescriptionValueForUpdate(TransactionEntityRoleType transactionEntityRoleType, Language language) {
        return getTransactionEntityRoleTypeDescriptionValue(getTransactionEntityRoleTypeDescriptionForUpdate(transactionEntityRoleType, language));
    }
    
    private List<TransactionEntityRoleTypeDescription> getTransactionEntityRoleTypeDescriptionsByTransactionEntityRoleType(TransactionEntityRoleType transactionEntityRoleType, EntityPermission entityPermission) {
        List<TransactionEntityRoleTypeDescription> transactionEntityRoleTypeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionentityroletypedescriptions, languages " +
                        "WHERE trxertypd_trxertyp_transactionentityroletypeid = ? AND trxertypd_thrutime = ? AND trxertypd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionentityroletypedescriptions " +
                        "WHERE trxertypd_trxertyp_transactionentityroletypeid = ? AND trxertypd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = TransactionEntityRoleTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, transactionEntityRoleType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            transactionEntityRoleTypeDescriptions = TransactionEntityRoleTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionEntityRoleTypeDescriptions;
    }
    
    public List<TransactionEntityRoleTypeDescription> getTransactionEntityRoleTypeDescriptionsByTransactionEntityRoleType(TransactionEntityRoleType transactionEntityRoleType) {
        return getTransactionEntityRoleTypeDescriptionsByTransactionEntityRoleType(transactionEntityRoleType, EntityPermission.READ_ONLY);
    }
    
    public List<TransactionEntityRoleTypeDescription> getTransactionEntityRoleTypeDescriptionsByTransactionEntityRoleTypeForUpdate(TransactionEntityRoleType transactionEntityRoleType) {
        return getTransactionEntityRoleTypeDescriptionsByTransactionEntityRoleType(transactionEntityRoleType, EntityPermission.READ_WRITE);
    }
    
    public String getBestTransactionEntityRoleTypeDescription(TransactionEntityRoleType transactionEntityRoleType, Language language) {
        String description;
        var transactionEntityRoleTypeDescription = getTransactionEntityRoleTypeDescription(transactionEntityRoleType, language);
        
        if(transactionEntityRoleTypeDescription == null && !language.getIsDefault()) {
            transactionEntityRoleTypeDescription = getTransactionEntityRoleTypeDescription(transactionEntityRoleType, getPartyControl().getDefaultLanguage());
        }
        
        if(transactionEntityRoleTypeDescription == null) {
            description = transactionEntityRoleType.getLastDetail().getTransactionEntityRoleTypeName();
        } else {
            description = transactionEntityRoleTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public TransactionEntityRoleTypeDescriptionTransfer getTransactionEntityRoleTypeDescriptionTransfer(UserVisit userVisit, TransactionEntityRoleTypeDescription transactionEntityRoleTypeDescription) {
        return getAccountingTransferCaches(userVisit).getTransactionEntityRoleTypeDescriptionTransferCache().getTransfer(transactionEntityRoleTypeDescription);
    }
    
    public List<TransactionEntityRoleTypeDescriptionTransfer> getTransactionEntityRoleTypeDescriptionTransfersByTransactionEntityRoleType(UserVisit userVisit, TransactionEntityRoleType transactionEntityRoleType) {
        var transactionEntityRoleTypeDescriptions = getTransactionEntityRoleTypeDescriptionsByTransactionEntityRoleType(transactionEntityRoleType);
        List<TransactionEntityRoleTypeDescriptionTransfer> transactionEntityRoleTypeDescriptionTransfers = new ArrayList<>(transactionEntityRoleTypeDescriptions.size());
        var transactionEntityRoleTypeDescriptionTransferCache = getAccountingTransferCaches(userVisit).getTransactionEntityRoleTypeDescriptionTransferCache();
        
        transactionEntityRoleTypeDescriptions.forEach((transactionEntityRoleTypeDescription) ->
                transactionEntityRoleTypeDescriptionTransfers.add(transactionEntityRoleTypeDescriptionTransferCache.getTransfer(transactionEntityRoleTypeDescription))
        );
        
        return transactionEntityRoleTypeDescriptionTransfers;
    }
    
    public void updateTransactionEntityRoleTypeDescriptionFromValue(TransactionEntityRoleTypeDescriptionValue transactionEntityRoleTypeDescriptionValue, BasePK updatedBy) {
        if(transactionEntityRoleTypeDescriptionValue.hasBeenModified()) {
            var transactionEntityRoleTypeDescription = TransactionEntityRoleTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, transactionEntityRoleTypeDescriptionValue.getPrimaryKey());
            
            transactionEntityRoleTypeDescription.setThruTime(session.START_TIME_LONG);
            transactionEntityRoleTypeDescription.store();

            var transactionEntityRoleType = transactionEntityRoleTypeDescription.getTransactionEntityRoleType();
            var language = transactionEntityRoleTypeDescription.getLanguage();
            var description = transactionEntityRoleTypeDescriptionValue.getDescription();
            
            transactionEntityRoleTypeDescription = TransactionEntityRoleTypeDescriptionFactory.getInstance().create(transactionEntityRoleType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(transactionEntityRoleType.getPrimaryKey(), EventTypes.MODIFY, transactionEntityRoleTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteTransactionEntityRoleTypeDescription(TransactionEntityRoleTypeDescription transactionEntityRoleTypeDescription, BasePK deletedBy) {
        transactionEntityRoleTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(transactionEntityRoleTypeDescription.getTransactionEntityRoleTypePK(), EventTypes.MODIFY, transactionEntityRoleTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteTransactionEntityRoleTypeDescriptionsByTransactionEntityRoleType(TransactionEntityRoleType transactionEntityRoleType, BasePK deletedBy) {
        var transactionEntityRoleTypeDescriptions = getTransactionEntityRoleTypeDescriptionsByTransactionEntityRoleTypeForUpdate(transactionEntityRoleType);
        
        transactionEntityRoleTypeDescriptions.forEach((transactionEntityRoleTypeDescription) -> 
                deleteTransactionEntityRoleTypeDescription(transactionEntityRoleTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Gl Accounts
    // --------------------------------------------------------------------------------
    
     public TransactionGlAccount createTransactionGlAccount(TransactionGlAccountCategory transactionGlAccountCategory, GlAccount glAccount, BasePK createdBy) {
         var transactionGlAccount = TransactionGlAccountFactory.getInstance().create(transactionGlAccountCategory, glAccount, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(transactionGlAccountCategory.getPrimaryKey(), EventTypes.MODIFY, transactionGlAccount.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return transactionGlAccount;
    }
    
    private List<TransactionGlAccount> getTransactionGlAccountsByGlAccount(GlAccount glAccount, EntityPermission entityPermission) {
        List<TransactionGlAccount> transactionGlAccounts;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionglaccounts, transactionglaccountcategories, transactionglaccountcategorydetails, transactiontypes, transactiontypedetails " +
                        "WHERE trxgla_gla_glaccountid = ? AND trxgla_thrutime = ? " +
                        "AND trxgla_trxglac_transactionglaccountcategoryid = trxglac_transactionglaccountcategoryid AND trxglac_lastdetailid = trxglacdt_transactionglaccountcategorydetailid " +
                        "AND trxglacdt_trxtyp_transactiontypeid = trxtyp_transactiontypeid AND trxtyp_lastdetailid = trxtypdt_transactiontypedetailid " +
                        "ORDER BY trxglacdt_sortorder, trxglacdt_transactionglaccountcategoryname, trxtypdt_sortorder, trxtypdt_transactiontypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionglaccounts " +
                        "WHERE trxgla_gla_glaccountid = ? AND trxgla_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = TransactionGlAccountFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, glAccount.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            transactionGlAccounts = TransactionGlAccountFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionGlAccounts;
    }
    
    public List<TransactionGlAccount> getTransactionGlAccountsByGlAccount(GlAccount glAccount) {
        return getTransactionGlAccountsByGlAccount(glAccount, EntityPermission.READ_ONLY);
    }
    
    public List<TransactionGlAccount> getTransactionGlAccountsByGlAccountForUpdate(GlAccount glAccount) {
        return getTransactionGlAccountsByGlAccount(glAccount, EntityPermission.READ_WRITE);
    }
    
    private TransactionGlAccount getTransactionGlAccount(TransactionGlAccountCategory transactionGlAccountCategory, EntityPermission entityPermission) {
        TransactionGlAccount transactionGlAccount;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionglaccounts " +
                        "WHERE trxgla_trxglac_transactionglaccountcategoryid = ? AND trxgla_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionglaccounts " +
                        "WHERE trxgla_trxglac_transactionglaccountcategoryid = ? AND trxgla_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = TransactionGlAccountFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, transactionGlAccountCategory.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            transactionGlAccount = TransactionGlAccountFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionGlAccount;
    }
    
    public TransactionGlAccount getTransactionGlAccount(TransactionGlAccountCategory transactionGlAccountCategory) {
        return getTransactionGlAccount(transactionGlAccountCategory, EntityPermission.READ_ONLY);
    }
    
    public TransactionGlAccount getTransactionGlAccountForUpdate(TransactionGlAccountCategory transactionGlAccountCategory) {
        return getTransactionGlAccount(transactionGlAccountCategory, EntityPermission.READ_WRITE);
    }
    
    public TransactionGlAccountValue getTransactionGlAccountValue(TransactionGlAccount transactionGlAccount) {
        return transactionGlAccount == null? null: transactionGlAccount.getTransactionGlAccountValue().clone();
    }
    
    public TransactionGlAccountValue getTransactionGlAccountValueForUpdate(TransactionGlAccountCategory transactionGlAccountCategory) {
        return getTransactionGlAccountValue(getTransactionGlAccountForUpdate(transactionGlAccountCategory));
    }
    
    public TransactionGlAccountTransfer getTransactionGlAccountTransfer(UserVisit userVisit, TransactionGlAccount transactionGlAccount) {
        return getAccountingTransferCaches(userVisit).getTransactionGlAccountTransferCache().getTransfer(transactionGlAccount);
    }
    
    public void updateTransactionGlAccountFromValue(TransactionGlAccountValue transactionGlAccountValue, BasePK updatedBy) {
        if(transactionGlAccountValue.hasBeenModified()) {
            var transactionGlAccount = TransactionGlAccountFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, transactionGlAccountValue.getPrimaryKey());
            
            transactionGlAccount.setThruTime(session.START_TIME_LONG);
            transactionGlAccount.store();

            var transactionGlAccountCategoryPK = transactionGlAccount.getTransactionGlAccountCategoryPK();
            var glAccountPK = transactionGlAccountValue.getGlAccountPK();
            
            transactionGlAccount = TransactionGlAccountFactory.getInstance().create(transactionGlAccountCategoryPK, glAccountPK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(transactionGlAccountCategoryPK, EventTypes.MODIFY, transactionGlAccount.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteTransactionGlAccount(TransactionGlAccount transactionGlAccount, BasePK deletedBy) {
        transactionGlAccount.setThruTime(session.START_TIME_LONG);
        
        sendEvent(transactionGlAccount.getTransactionGlAccountCategoryPK(), EventTypes.MODIFY, transactionGlAccount.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteTransactionGlAccountByTransactionGlAccountCategory(TransactionGlAccountCategory transactionGlAccountCategory, BasePK deletedBy) {
        var transactionGlAccount = getTransactionGlAccount(transactionGlAccountCategory);
        
        if(transactionGlAccount != null) {
            deleteTransactionGlAccount(transactionGlAccount, deletedBy);
        }
    }
    
    public void deleteTransactionGlAccounts(List<TransactionGlAccount> transactionGlAccounts, BasePK deletedBy) {
        transactionGlAccounts.forEach((transactionGlAccount) -> 
                deleteTransactionGlAccount(transactionGlAccount, deletedBy)
        );
    }
    
    public void deleteTransactionGlAccountByGlAccount(GlAccount glAccount, BasePK deletedBy) {
        deleteTransactionGlAccounts(getTransactionGlAccountsByGlAccountForUpdate(glAccount), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Groups
    // --------------------------------------------------------------------------------
    
    public TransactionGroup getActiveTransactionGroup(BasePK createdBy) {
        var workflowStep = getWorkflowControl().getWorkflowStepUsingNames(Workflow_TRANSACTION_GROUP_STATUS,
                WorkflowStep_TRANSACTION_GROUP_STATUS_ACTIVE);
        TransactionGroup transactionGroup = null;
        
        if(workflowStep != null) {
            List<TransactionGroup> transactionGroups;
            
            try {
                var ps = TransactionGroupFactory.getInstance().prepareStatement(
                        "SELECT _ALL_ " +
                        "FROM componentvendors, componentvendordetails, entitytypes, entitytypedetails, entityinstances, " +
                        "transactiongroups, transactiongroupdetails, workflowentitystatuses, entitytimes " +
                        "WHERE trxgrp_activedetailid = trxgrpdt_transactiongroupdetailid " +
                        "AND cvnd_activedetailid = cvndd_componentvendordetailid AND cvndd_componentvendorname = ? " +
                        "AND ent_activedetailid = entdt_entitytypedetailid " +
                        "AND cvnd_componentvendorid = entdt_cvnd_componentvendorid " +
                        "AND entdt_entitytypename = ? " +
                        "AND ent_entitytypeid = eni_ent_entitytypeid AND trxgrp_transactiongroupid = eni_entityuniqueid " +
                        "AND eni_entityinstanceid = wkfles_eni_entityinstanceid AND wkfles_wkfls_workflowstepid = ? AND wkfles_thrutime = ? " +
                        "AND eni_entityinstanceid = etim_eni_entityinstanceid " +
                        "ORDER BY etim_createdtime DESC");
                
                ps.setString(1, ComponentVendors.ECHO_THREE.name());
                ps.setString(2, EntityTypes.TransactionGroup.name());
                ps.setLong(3, workflowStep.getPrimaryKey().getEntityId());
                ps.setLong(4, Session.MAX_TIME);
                
                transactionGroups = TransactionGroupFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
            
            if(transactionGroups != null && !transactionGroups.isEmpty()) {
                transactionGroup = transactionGroups.iterator().next();
            } else {
                transactionGroup = createTransactionGroup(createdBy);
            }
        }
        
        return transactionGroup;
    }
    
    public TransactionGroup createTransactionGroup(BasePK createdBy) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.TRANSACTION_GROUP.name());
        var transactionGroupName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);

        var transactionGroup = createTransactionGroup(transactionGroupName, createdBy);

        var entityInstance = getEntityInstanceByBaseEntity(transactionGroup);
        getWorkflowControl().addEntityToWorkflowUsingNames(null, Workflow_TRANSACTION_GROUP_STATUS, entityInstance, null, null,createdBy);
        
        return transactionGroup;
    }
    
    public TransactionGroup createTransactionGroup(String transactionGroupName, BasePK createdBy) {
        var transactionGroup = TransactionGroupFactory.getInstance().create();
        var transactionGroupDetail = TransactionGroupDetailFactory.getInstance().create(transactionGroup,
                transactionGroupName, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        transactionGroup = TransactionGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                transactionGroup.getPrimaryKey());
        transactionGroup.setActiveDetail(transactionGroupDetail);
        transactionGroup.setLastDetail(transactionGroupDetail);
        transactionGroup.store();
        
        sendEvent(transactionGroup.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return transactionGroup;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.TransactionGroup */
    public TransactionGroup getTransactionGroupByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new TransactionGroupPK(entityInstance.getEntityUniqueId());

        return TransactionGroupFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public TransactionGroup getTransactionGroupByEntityInstance(EntityInstance entityInstance) {
        return getTransactionGroupByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public TransactionGroup getTransactionGroupByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getTransactionGroupByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countTransactionGroups() {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM transactiongroups
                        JOIN transactiongroupdetails ON trxgrp_activedetailid = trxgrpdt_transactiongroupdetailid
                        """);
    }

    public TransactionGroupDetailValue getTransactionGroupDetailValueForUpdate(TransactionGroup transactionGroup) {
        return transactionGroup.getLastDetailForUpdate().getTransactionGroupDetailValue().clone();
    }
    
    public TransactionGroup getTransactionGroupByName(String transactionGroupName, EntityPermission entityPermission) {
        TransactionGroup transactionGroup;
        
        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactiongroups, transactiongroupdetails " +
                        "WHERE trxgrp_activedetailid = trxgrpdt_transactiongroupdetailid AND trxgrpdt_transactiongroupname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactiongroups, transactiongroupdetails " +
                        "WHERE trxgrp_activedetailid = trxgrpdt_transactiongroupdetailid AND trxgrpdt_transactiongroupname = ? " +
                        "FOR UPDATE";
            }

            var ps = TransactionGroupFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, transactionGroupName);
            
            transactionGroup = TransactionGroupFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionGroup;
    }

    public TransactionGroup getTransactionGroupByName(String transactionGroupName) {
        return getTransactionGroupByName(transactionGroupName, EntityPermission.READ_ONLY);
    }
    
    public TransactionGroup getTransactionGroupByNameForUpdate(String transactionGroupName) {
        return getTransactionGroupByName(transactionGroupName, EntityPermission.READ_WRITE);
    }
    
    public TransactionGroupDetailValue getTransactionGroupDetailValueByNameForUpdate(String transactionGroupName) {
        return getTransactionGroupDetailValueForUpdate(getTransactionGroupByNameForUpdate(transactionGroupName));
    }
    
    private List<TransactionGroup> getTransactionGroups(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM transactiongroups, transactiongroupdetails " +
                    "WHERE trxgrp_activedetailid = trxgrpdt_transactiongroupdetailid " +
                    "ORDER BY trxgrpdt_transactiongroupname " +
                    "_LIMIT_";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM transactiongroups, transactiongroupdetails " +
                    "WHERE trxgrp_activedetailid = trxgrpdt_transactiongroupdetailid " +
                    "FOR UPDATE";
        }

        var ps = TransactionGroupFactory.getInstance().prepareStatement(query);
        
        return TransactionGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<TransactionGroup> getTransactionGroups() {
        return getTransactionGroups(EntityPermission.READ_ONLY);
    }
    
    public List<TransactionGroup> getTransactionGroupsForUpdate() {
        return getTransactionGroups(EntityPermission.READ_WRITE);
    }
    
    public TransactionGroupTransfer getTransactionGroupTransfer(UserVisit userVisit, TransactionGroup transactionGroup) {
        return getAccountingTransferCaches(userVisit).getTransactionGroupTransferCache().getTransfer(transactionGroup);
    }
    
    public List<TransactionGroupTransfer> getTransactionGroupTransfers(UserVisit userVisit, Collection<TransactionGroup> transactionGroups) {
        List<TransactionGroupTransfer> transactionGroupTransfers = new ArrayList<>(transactionGroups.size());
        var transactionGroupTransferCache = getAccountingTransferCaches(userVisit).getTransactionGroupTransferCache();
        
        transactionGroups.forEach((transactionGroup) ->
                transactionGroupTransfers.add(transactionGroupTransferCache.getTransfer(transactionGroup))
        );
        
        return transactionGroupTransfers;
    }
    
    public List<TransactionGroupTransfer> getTransactionGroupTransfers(UserVisit userVisit) {
        return getTransactionGroupTransfers(userVisit, getTransactionGroups());
    }
    
    public TransactionGroupStatusChoicesBean getTransactionGroupStatusChoices(String defaultTransactionGroupStatusChoice, Language language, boolean allowNullChoice,
            TransactionGroup transactionGroup, PartyPK partyPK) {
        var workflowControl = getWorkflowControl();
        var transactionGroupStatusChoicesBean = new TransactionGroupStatusChoicesBean();
        
        if(transactionGroup == null) {
            workflowControl.getWorkflowEntranceChoices(transactionGroupStatusChoicesBean, defaultTransactionGroupStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(Workflow_TRANSACTION_GROUP_STATUS), partyPK);
        } else {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(transactionGroup.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(Workflow_TRANSACTION_GROUP_STATUS,
                    entityInstance);
            
            workflowControl.getWorkflowDestinationChoices(transactionGroupStatusChoicesBean, defaultTransactionGroupStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }
        
        return transactionGroupStatusChoicesBean;
    }
    
    public void setTransactionGroupStatus(ExecutionErrorAccumulator eea, TransactionGroup transactionGroup, String transactionGroupStatusChoice, PartyPK modifiedBy) {
        var workflowControl = getWorkflowControl();
        var entityInstance = getEntityInstanceByBaseEntity(transactionGroup);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(Workflow_TRANSACTION_GROUP_STATUS, entityInstance);
        var workflowDestination = transactionGroupStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), transactionGroupStatusChoice);
        
        if(workflowDestination != null || transactionGroupStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownTransactionGroupStatusChoice.name(), transactionGroupStatusChoice);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Transactions
    // --------------------------------------------------------------------------------
    
    public Transaction createTransaction(Party groupParty, TransactionType transactionType, BasePK createdBy) {
        return createTransaction(groupParty, getActiveTransactionGroup(createdBy), transactionType, createdBy);
    }
    
    public Transaction createTransaction(Party groupParty, TransactionGroup transactionGroup, TransactionType transactionType,
            BasePK createdBy) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.TRANSACTION.name());
        var transactionName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);
        
        return createTransaction(transactionName, groupParty, transactionGroup, transactionType, createdBy);
    }
    
    public Transaction createTransaction(String transactionName, Party groupParty, TransactionGroup transactionGroup,
            TransactionType transactionType, BasePK createdBy) {
        var transaction = TransactionFactory.getInstance().create();
        var transactionDetail = TransactionDetailFactory.getInstance().create(transaction, transactionName,
                groupParty, transactionGroup, transactionType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        transaction = TransactionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                transaction.getPrimaryKey());
        transaction.setActiveDetail(transactionDetail);
        transaction.setLastDetail(transactionDetail);
        transaction.store();
        
        sendEvent(transaction.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        createTransactionStatus(transaction);
        
        return transaction;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Transaction */
    public Transaction getTransactionByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new TransactionPK(entityInstance.getEntityUniqueId());

        return TransactionFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public Transaction getTransactionByEntityInstance(EntityInstance entityInstance) {
        return getTransactionByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Transaction getTransactionByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getTransactionByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countTransactions() {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM transactions
                        JOIN transactiondetails ON trx_activedetailid = trxdt_transactiondetailid
                        """);
    }

    public long countTransactionsByTransactionGroup(Party groupParty) {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM transactions
                        JOIN transactiondetails ON trx_activedetailid = trxdt_transactiondetailid
                        WHERE trxdt_grouppartyid = ?
                        """, groupParty);
    }

    public long countTransactionsByTransactionGroup(TransactionGroup transactionGroup) {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM transactions
                        JOIN transactiondetails ON trx_activedetailid = trxdt_transactiondetailid
                        WHERE trxdt_trxgrp_transactiongroupid = ?
                        """, transactionGroup);
    }

    public long countTransactionsByTransactionType(TransactionType transactionType) {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM transactions
                        JOIN transactiondetails ON trx_activedetailid = trxdt_transactiondetailid
                        WHERE trxdt_trxtyp_transactiontypeid = ?
                        """, transactionType);
    }

    public TransactionDetailValue getTransactionDetailValueForUpdate(Transaction transaction) {
        return transaction.getLastDetailForUpdate().getTransactionDetailValue().clone();
    }

    public Transaction getTransactionByName(String transactionName, EntityPermission entityPermission) {
        Transaction transaction;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactions, transactiondetails " +
                        "WHERE trx_activedetailid = trxdt_transactiondetailid AND trxdt_transactionname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactions, transactiondetails " +
                        "WHERE trx_activedetailid = trxdt_transactiondetailid AND trxdt_transactionname = ? " +
                        "FOR UPDATE";
            }

            var ps = TransactionFactory.getInstance().prepareStatement(query);

            ps.setString(1, transactionName);

            transaction = TransactionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return transaction;
    }

    public Transaction getTransactionByName(String transactionName) {
        return getTransactionByName(transactionName, EntityPermission.READ_ONLY);
    }

    public Transaction getTransactionByNameForUpdate(String transactionName) {
        return getTransactionByName(transactionName, EntityPermission.READ_WRITE);
    }

    public TransactionDetailValue getTransactionDetailValueByNameForUpdate(String transactionName) {
        return getTransactionDetailValueForUpdate(getTransactionByNameForUpdate(transactionName));
    }

    public List<Transaction> getTransactions() {
        var ps = TransactionFactory.getInstance().prepareStatement("""
                        SELECT _ALL_
                        FROM transactions, transactiondetails
                        WHERE trx_activedetailid = trxdt_transactiondetailid
                        ORDER BY trxdt_transactionname
                        _LIMIT_
                        """);

        return TransactionFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }

    public List<Transaction> getTransactionsByTransactionGroup(TransactionGroup transactionGroup) {
        List<Transaction> transactions;

        try {
            var ps = TransactionFactory.getInstance().prepareStatement("""
                            SELECT _ALL_
                            FROM transactions, transactiondetails
                            WHERE trx_activedetailid = trxdt_transactiondetailid AND trxdt_trxgrp_transactiongroupid = ?
                            ORDER BY trxdt_transactionname
                            _LIMIT_
                            """);

            ps.setLong(1, transactionGroup.getPrimaryKey().getEntityId());

            transactions = TransactionFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return transactions;
    }

    public List<Transaction> getTransactionsByTransactionType(TransactionType transactionType) {
        List<Transaction> transactions;
        
        try {
            var ps = TransactionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM transactions, transactiondetails " +
                    "WHERE trx_activedetailid = trxdt_transactiondetailid AND trxdt_trxtyp_transactiontypeid = ? " +
                    "ORDER BY trxdt_transactionname " +
                    "_LIMIT_");
            
            ps.setLong(1, transactionType.getPrimaryKey().getEntityId());
            
            transactions = TransactionFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactions;
    }
    
    public TransactionTransfer getTransactionTransfer(UserVisit userVisit, Transaction transaction) {
        return getAccountingTransferCaches(userVisit).getTransactionTransferCache().getTransfer(transaction);
    }
    
    public List<TransactionTransfer> getTransactionTransfers(UserVisit userVisit, Collection<Transaction> transactions) {
        List<TransactionTransfer> transactionTransfers = new ArrayList<>(transactions.size());
        var transactionTransferCache = getAccountingTransferCaches(userVisit).getTransactionTransferCache();
        
        transactions.forEach((transaction) ->
                transactionTransfers.add(transactionTransferCache.getTransfer(transaction))
        );
        
        return transactionTransfers;
    }
    
    public List<TransactionTransfer> getTransactionTransfersByTransactionGroup(UserVisit userVisit, TransactionGroup transactionGroup) {
        return getTransactionTransfers(userVisit, getTransactionsByTransactionGroup(transactionGroup));
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Statuses
    // --------------------------------------------------------------------------------
    
    public TransactionStatus createTransactionStatus(Transaction transaction) {
        return TransactionStatusFactory.getInstance().create(transaction, 0);
    }
    
    private TransactionStatus getTransactionStatus(Transaction transaction, EntityPermission entityPermission) {
        TransactionStatus transactionStatus;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionstatuses " +
                        "WHERE trxst_trx_transactionid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionstatuses " +
                        "WHERE trxst_trx_transactionid = ? " +
                        "FOR UPDATE";
            }

            var ps = TransactionStatusFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, transaction.getPrimaryKey().getEntityId());
            
            transactionStatus = TransactionStatusFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionStatus;
    }
    
    public TransactionStatus getTransactionStatus(Transaction transaction) {
        return getTransactionStatus(transaction, EntityPermission.READ_ONLY);
    }
    
    public TransactionStatus getTransactionStatusForUpdate(Transaction transaction) {
        return getTransactionStatus(transaction, EntityPermission.READ_WRITE);
    }
    
    public void removeTransactionStatusByTransaction(Transaction transaction) {
        var transactionStatus = getTransactionStatusForUpdate(transaction);
        
        if(transactionStatus != null) {
            transactionStatus.remove();
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Gl Account Entries
    // --------------------------------------------------------------------------------
    
    public TransactionGlEntry createTransactionGlEntry(Transaction transaction, Integer transactionGlEntrySequence,
            Party groupParty, TransactionGlAccountCategory transactionGlAccountCategory, GlAccount glAccount,
            Currency originalCurrency, Long originalDebit, Long originalCredit, Long debit, Long credit,
            BasePK createdBy) {
        var transactionGlEntry = TransactionGlEntryFactory.getInstance().create(transaction, transactionGlEntrySequence,
                groupParty, transactionGlAccountCategory, glAccount, originalCurrency, originalDebit,
                originalCredit, debit, credit, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(transaction.getPrimaryKey(), EventTypes.MODIFY, transactionGlEntry.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return transactionGlEntry;
    }

    public long countTransactionGlEntryByTransaction(Transaction transaction) {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM transactionglentries
                        WHERE trxglent_trx_transactionid = ? AND trxglent_thrutime = ?
                        """, transaction, Session.MAX_TIME_LONG);
    }

    public long countTransactionGlEntryByGlAccount(GlAccount glAccount) {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM transactionglentries
                        WHERE trxgla_gla_glaccountid = ? AND trxglent_thrutime = ?
                        """, glAccount, Session.MAX_TIME_LONG);
    }

    public List<TransactionGlEntry> getTransactionGlEntriesByTransaction(Transaction transaction) {
        List<TransactionGlEntry> transactionGlEntries;
        
        try {
            var ps = TransactionGlEntryFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM transactionglentries " +
                    "WHERE trxglent_trx_transactionid = ? AND trxglent_thrutime = ? " +
                    "ORDER BY trxglent_transactionglentrysequence " +
                    "_LIMIT_");
            
            ps.setLong(1, transaction.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            transactionGlEntries = TransactionGlEntryFactory.getInstance().getEntitiesFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionGlEntries;
    }
    
    public List<TransactionGlEntry> getTransactionGlEntriesByGlAccount(GlAccount glAccount) {
        List<TransactionGlEntry> transactionGlEntries;
        
        try {
            var ps = TransactionGlEntryFactory.getInstance().prepareStatement("""
                    SELECT _ALL_
                    FROM transactionglentries
                    JOIN transactiontimes ON trxglent_trx_transactionid = txntim_trx_transactionid AND txntim_thrutime
                    JOIN transactiontimetypes ON txntim_txntimtyp_transactiontimetypeid = txntimtyp_transactiontimetypeid
                    JOIN transactiontimetypedetails ON txntimtyp_lastdetailid = txntimtypdt_transactiontimetypedetailid
                    WHERE trxglent_gla_glaccountid = ? AND trxglent_thrutime = ? AND txntimtypdt_transactiontimetypename = ?
                    ORDER BY txntim_time
                    _LIMIT_
                    """);

            ps.setLong(1, glAccount.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            ps.setString(3, TransactionTimeTypes.TRANSACTION_TIME.name());

            transactionGlEntries = TransactionGlEntryFactory.getInstance().getEntitiesFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionGlEntries;
    }
    
    public TransactionGlEntryTransfer getTransactionGlEntryTransfer(UserVisit userVisit, TransactionGlEntry transactionGlEntry) {
        return getAccountingTransferCaches(userVisit).getTransactionGlEntryTransferCache().getTransfer(transactionGlEntry);
    }
    
    public List<TransactionGlEntryTransfer> getTransactionGlEntryTransfers(UserVisit userVisit, Collection<TransactionGlEntry> transactionGlEntries) {
        List<TransactionGlEntryTransfer> transactionGlEntryTransfers = new ArrayList<>(transactionGlEntries.size());
        var transactionGlEntryTransferCache = getAccountingTransferCaches(userVisit).getTransactionGlEntryTransferCache();
        
        transactionGlEntries.forEach((transactionGlEntry) ->
                transactionGlEntryTransfers.add(transactionGlEntryTransferCache.getTransfer(transactionGlEntry))
        );
        
        return transactionGlEntryTransfers;
    }
    
    public List<TransactionGlEntryTransfer> getTransactionGlEntryTransfersByTransaction(UserVisit userVisit, Transaction transaction) {
        return getTransactionGlEntryTransfers(userVisit, getTransactionGlEntriesByTransaction(transaction));
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Entity Roles
    // --------------------------------------------------------------------------------
    
    public TransactionEntityRole createTransactionEntityRole(Transaction transaction, TransactionEntityRoleType transactionEntityRoleType, EntityInstance entityInstance, BasePK createdBy) {
        var transactionEntityRole = TransactionEntityRoleFactory.getInstance().create(transaction, transactionEntityRoleType, entityInstance, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEvent(transaction.getPrimaryKey(), EventTypes.MODIFY, transactionEntityRole.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return transactionEntityRole;
    }

    public long countTransactionEntityRolesByTransaction(final Transaction transaction) {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM transactionentityroles
                        WHERE trxer_trx_transactionid = ? AND trxer_thrutime = ?
                        """, transaction, Session.MAX_TIME_LONG);
    }

    public TransactionEntityRole getTransactionEntityRole(Transaction transaction, TransactionEntityRoleType transactionEntityRoleType) {
        TransactionEntityRole transactionEntityRole;
        
        try {
            var ps = TransactionEntityRoleFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM transactionentityroles " +
                    "WHERE trxer_trx_transactionid = ? AND trxer_trxertyp_transactionentityroletypeid = ? AND trxer_thrutime = ?");
            
            ps.setLong(1, transaction.getPrimaryKey().getEntityId());
            ps.setLong(2, transactionEntityRoleType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            transactionEntityRole = TransactionEntityRoleFactory.getInstance().getEntityFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionEntityRole;
    }
    
    private List<TransactionEntityRole> getTransactionEntityRolesByTransaction(Transaction transaction, EntityPermission entityPermission) {
        List<TransactionEntityRole> transactionEntityRoles;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionentityroles, transactionentityroletypes, transactionentityroletypedetails " +
                        "WHERE trxer_trx_transactionid = ? AND trxer_thrutime = ? " +
                        "AND trxer_trxertyp_transactionentityroletypeid = trxertyp_transactionentityroletypeid AND trxertyp_lastdetailid = trxertypdt_transactionentityroletypedetailid " +
                        "ORDER BY trxertypdt_sortorder, trxertypdt_transactionentityroletypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionentityroles " +
                        "WHERE trxer_trx_transactionid = ? AND trxer_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = TransactionEntityRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, transaction.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            transactionEntityRoles = TransactionEntityRoleFactory.getInstance().getEntitiesFromQuery( entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionEntityRoles;
    }
    
    public List<TransactionEntityRole> getTransactionEntityRolesByTransaction(Transaction transaction) {
        return getTransactionEntityRolesByTransaction(transaction, EntityPermission.READ_ONLY);
    }
    
    public List<TransactionEntityRole> getTransactionEntityRolesByTransactionForUpdate(Transaction transaction) {
        return getTransactionEntityRolesByTransaction(transaction, EntityPermission.READ_WRITE);
    }
    
    private List<TransactionEntityRole> getTransactionEntityRolesByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        List<TransactionEntityRole> transactionEntityRoles;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionentityroles " +
                        "WHERE trxer_eni_entityinstanceid = ? AND trxer_thrutime = ? " +
                        "ORDER BY trxer_transactionentityroleid"; // TODO: this should probably be ordered by something else
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionentityroles " +
                        "WHERE trxer_eni_entityinstanceid = ? AND trxer_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = TransactionEntityRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            transactionEntityRoles = TransactionEntityRoleFactory.getInstance().getEntitiesFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionEntityRoles;
    }
    
    public List<TransactionEntityRole> getTransactionEntityRolesByEntityInstance(EntityInstance entityInstance) {
        return getTransactionEntityRolesByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }
    
    public List<TransactionEntityRole> getTransactionEntityRolesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getTransactionEntityRolesByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public TransactionEntityRoleTransfer getTransactionEntityRoleTransfer(UserVisit userVisit, TransactionEntityRole transactionEntityRole) {
        return getAccountingTransferCaches(userVisit).getTransactionEntityRoleTransferCache().getTransfer(transactionEntityRole);
    }
    
    public List<TransactionEntityRoleTransfer> getTransactionEntityRoleTransfers(UserVisit userVisit, Collection<TransactionEntityRole> transactionEntityRoles) {
        List<TransactionEntityRoleTransfer> transactionEntityRoleTransfers = new ArrayList<>(transactionEntityRoles.size());
        var transactionEntityRoleTransferCache = getAccountingTransferCaches(userVisit).getTransactionEntityRoleTransferCache();
        
        transactionEntityRoles.forEach((transactionEntityRole) ->
                transactionEntityRoleTransfers.add(transactionEntityRoleTransferCache.getTransfer(transactionEntityRole))
        );
        
        return transactionEntityRoleTransfers;
    }
    
    public List<TransactionEntityRoleTransfer> getTransactionEntityRoleTransfersByTransaction(UserVisit userVisit, Transaction transaction) {
        return getTransactionEntityRoleTransfers(userVisit, getTransactionEntityRolesByTransaction(transaction));
    }
    
    public void deleteTransactionEntityRole(TransactionEntityRole transactionEntityRole, BasePK deletedBy) {
        transactionEntityRole.setThruTime(session.START_TIME_LONG);
        
        sendEvent(transactionEntityRole.getTransactionPK(), EventTypes.MODIFY, transactionEntityRole.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteTransactionEntityRoles(List<TransactionEntityRole> transactionEntityRoles, BasePK deletedBy) {
        transactionEntityRoles.forEach((transactionEntityRole) -> 
                deleteTransactionEntityRole(transactionEntityRole, deletedBy)
        );
    }
    
    public void deleteTransactionEntityRolesByTransaction(Transaction transaction, BasePK deletedBy) {
        deleteTransactionEntityRoles(getTransactionEntityRolesByTransactionForUpdate(transaction), deletedBy);
    }
    
    public void deleteTransactionEntityRolesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteTransactionEntityRoles(getTransactionEntityRolesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Symbol Position
    // --------------------------------------------------------------------------------
    
    public SymbolPosition createSymbolPosition(String symbolPositionName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultSymbolPosition = getDefaultSymbolPosition();
        var defaultFound = defaultSymbolPosition != null;
        
        if(defaultFound && isDefault) {
            var defaultSymbolPositionDetailValue = getDefaultSymbolPositionDetailValueForUpdate();
            
            defaultSymbolPositionDetailValue.setIsDefault(Boolean.FALSE);
            updateSymbolPositionFromValue(defaultSymbolPositionDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        var symbolPosition = SymbolPositionFactory.getInstance().create();
        var symbolPositionDetail = SymbolPositionDetailFactory.getInstance().create(session,
                symbolPosition, symbolPositionName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        symbolPosition = SymbolPositionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, symbolPosition.getPrimaryKey());
        symbolPosition.setActiveDetail(symbolPositionDetail);
        symbolPosition.setLastDetail(symbolPositionDetail);
        symbolPosition.store();
        
        sendEvent(symbolPosition.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return symbolPosition;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.SymbolPosition */
    public SymbolPosition getSymbolPositionByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new SymbolPositionPK(entityInstance.getEntityUniqueId());

        return SymbolPositionFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public SymbolPosition getSymbolPositionByEntityInstance(EntityInstance entityInstance) {
        return getSymbolPositionByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public SymbolPosition getSymbolPositionByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getSymbolPositionByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countSymbolPositions() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM symbolpositions, symbolpositiondetails " +
                        "WHERE sympos_activedetailid = symposdt_symbolpositiondetailid");
    }

    private SymbolPosition getSymbolPositionByName(String symbolPositionName, EntityPermission entityPermission) {
        SymbolPosition symbolPosition;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM symbolpositions, symbolpositiondetails " +
                        "WHERE sympos_symbolpositionid = symposdt_sympos_symbolpositionid AND symposdt_symbolpositionname = ? AND symposdt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM symbolpositions, symbolpositiondetails " +
                        "WHERE sympos_symbolpositionid = symposdt_sympos_symbolpositionid AND symposdt_symbolpositionname = ? AND symposdt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = SymbolPositionFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, symbolPositionName);
            ps.setLong(2, Session.MAX_TIME);
            
            symbolPosition = SymbolPositionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return symbolPosition;
    }
    
    public SymbolPosition getSymbolPositionByName(String symbolPositionName) {
        return getSymbolPositionByName(symbolPositionName, EntityPermission.READ_ONLY);
    }
    
    public SymbolPosition getSymbolPositionByNameForUpdate(String symbolPositionName) {
        return getSymbolPositionByName(symbolPositionName, EntityPermission.READ_WRITE);
    }
    
    public SymbolPositionDetailValue getSymbolPositionDetailValueForUpdate(SymbolPosition symbolPosition) {
        return symbolPosition == null? null: symbolPosition.getLastDetailForUpdate().getSymbolPositionDetailValue().clone();
    }
    
    public SymbolPositionDetailValue getSymbolPositionDetailValueByNameForUpdate(String symbolPositionName) {
        return getSymbolPositionDetailValueForUpdate(getSymbolPositionByNameForUpdate(symbolPositionName));
    }
    
    private SymbolPosition getDefaultSymbolPosition(EntityPermission entityPermission) {
        SymbolPosition symbolPosition;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM symbolpositions, symbolpositiondetails " +
                        "WHERE sympos_symbolpositionid = symposdt_sympos_symbolpositionid AND symposdt_isdefault = 1 AND symposdt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM symbolpositions, symbolpositiondetails " +
                        "WHERE sympos_symbolpositionid = symposdt_sympos_symbolpositionid AND symposdt_isdefault = 1 AND symposdt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = SymbolPositionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, Session.MAX_TIME);
            
            symbolPosition = SymbolPositionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return symbolPosition;
    }
    
    public SymbolPosition getDefaultSymbolPosition() {
        return getDefaultSymbolPosition(EntityPermission.READ_ONLY);
    }
    
    public SymbolPosition getDefaultSymbolPositionForUpdate() {
        return getDefaultSymbolPosition(EntityPermission.READ_WRITE);
    }
    
    public SymbolPositionDetailValue getDefaultSymbolPositionDetailValueForUpdate() {
        return getDefaultSymbolPositionForUpdate().getLastDetailForUpdate().getSymbolPositionDetailValue().clone();
    }
    
    private List<SymbolPosition> getSymbolPositions(EntityPermission entityPermission) {
        List<SymbolPosition> symbolPositions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM symbolpositions, symbolpositiondetails " +
                        "WHERE sympos_symbolpositionid = symposdt_sympos_symbolpositionid AND symposdt_thrutime = ? " +
                        "ORDER BY symposdt_sortorder, symposdt_symbolpositionname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM symbolpositions, symbolpositiondetails " +
                        "WHERE sympos_symbolpositionid = symposdt_sympos_symbolpositionid AND symposdt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = SymbolPositionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, Session.MAX_TIME);
            
            symbolPositions = SymbolPositionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return symbolPositions;
    }
    
    public List<SymbolPosition> getSymbolPositions() {
        return getSymbolPositions(EntityPermission.READ_ONLY);
    }
    
    public List<SymbolPosition> getSymbolPositionsForUpdate() {
        return getSymbolPositions(EntityPermission.READ_WRITE);
    }
    
    public SymbolPositionTransfer getSymbolPositionTransfer(UserVisit userVisit, SymbolPosition symbolPosition) {
        return getAccountingTransferCaches(userVisit).getSymbolPositionTransferCache().getTransfer(symbolPosition);
    }
    
    public List<SymbolPositionTransfer> getSymbolPositionTransfers(UserVisit userVisit, Collection<SymbolPosition> symbolPositions) {
        List<SymbolPositionTransfer> symbolPositionTransfers = new ArrayList<>(symbolPositions.size());
        var symbolPositionTransferCache = getAccountingTransferCaches(userVisit).getSymbolPositionTransferCache();
        
        symbolPositions.forEach((symbolPosition) ->
                symbolPositionTransfers.add(symbolPositionTransferCache.getTransfer(symbolPosition))
        );
        
        return symbolPositionTransfers;
    }
    
    public List<SymbolPositionTransfer> getSymbolPositionTransfers(UserVisit userVisit) {
        return getSymbolPositionTransfers(userVisit, getSymbolPositions());
    }
    
    public SymbolPositionChoicesBean getSymbolPositionChoices(String defaultSymbolPositionChoice, Language language,
            boolean allowNullChoice) {
        var symbolPositions = getSymbolPositions();
        var size = symbolPositions.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultSymbolPositionChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var symbolPosition : symbolPositions) {
            var symbolPositionDetail = symbolPosition.getLastDetail();
            
            var label = getBestSymbolPositionDescription(symbolPosition, language);
            var value = symbolPositionDetail.getSymbolPositionName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultSymbolPositionChoice != null && defaultSymbolPositionChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && symbolPositionDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new SymbolPositionChoicesBean(labels, values, defaultValue);
    }
    
    private void updateSymbolPositionFromValue(SymbolPositionDetailValue symbolPositionDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(symbolPositionDetailValue.hasBeenModified()) {
            var symbolPosition = SymbolPositionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     symbolPositionDetailValue.getSymbolPositionPK());
            var symbolPositionDetail = symbolPosition.getActiveDetailForUpdate();
            
            symbolPositionDetail.setThruTime(session.START_TIME_LONG);
            symbolPositionDetail.store();

            var symbolPositionPK = symbolPositionDetail.getSymbolPositionPK();
            var symbolPositionName = symbolPositionDetailValue.getSymbolPositionName();
            var isDefault = symbolPositionDetailValue.getIsDefault();
            var sortOrder = symbolPositionDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultSymbolPosition = getDefaultSymbolPosition();
                var defaultFound = defaultSymbolPosition != null && !defaultSymbolPosition.equals(symbolPosition);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultSymbolPositionDetailValue = getDefaultSymbolPositionDetailValueForUpdate();
                    
                    defaultSymbolPositionDetailValue.setIsDefault(Boolean.FALSE);
                    updateSymbolPositionFromValue(defaultSymbolPositionDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            symbolPositionDetail = SymbolPositionDetailFactory.getInstance().create(symbolPositionPK,
                    symbolPositionName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            symbolPosition.setActiveDetail(symbolPositionDetail);
            symbolPosition.setLastDetail(symbolPositionDetail);
            
            sendEvent(symbolPositionPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateSymbolPositionFromValue(SymbolPositionDetailValue symbolPositionDetailValue, BasePK updatedBy) {
        updateSymbolPositionFromValue(symbolPositionDetailValue, true, updatedBy);
    }
    
    public void deleteSymbolPosition(SymbolPosition symbolPosition, BasePK deletedBy) {
        deleteSymbolPositionDescriptionsBySymbolPosition(symbolPosition, deletedBy);

        var symbolPositionDetail = symbolPosition.getLastDetailForUpdate();
        symbolPositionDetail.setThruTime(session.START_TIME_LONG);
        symbolPositionDetail.store();
        symbolPosition.setActiveDetail(null);
        
        // Check for default, and pick one if necessary
        var defaultSymbolPosition = getDefaultSymbolPosition();
        if(defaultSymbolPosition == null) {
            var symbolPositions = getSymbolPositionsForUpdate();
            
            if(!symbolPositions.isEmpty()) {
                var iter = symbolPositions.iterator();
                if(iter.hasNext()) {
                    defaultSymbolPosition = iter.next();
                }
                var symbolPositionDetailValue = Objects.requireNonNull(defaultSymbolPosition).getLastDetailForUpdate().getSymbolPositionDetailValue().clone();
                
                symbolPositionDetailValue.setIsDefault(Boolean.TRUE);
                updateSymbolPositionFromValue(symbolPositionDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(symbolPosition.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Symbol Position Descriptions
    // --------------------------------------------------------------------------------
    
    public SymbolPositionDescription createSymbolPositionDescription(SymbolPosition symbolPosition, Language language, String description, BasePK createdBy) {
        var symbolPositionDescription = SymbolPositionDescriptionFactory.getInstance().create(symbolPosition, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEvent(symbolPosition.getPrimaryKey(), EventTypes.MODIFY, symbolPositionDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return symbolPositionDescription;
    }
    
    private SymbolPositionDescription getSymbolPositionDescription(SymbolPosition symbolPosition, Language language, EntityPermission entityPermission) {
        SymbolPositionDescription symbolPositionDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM symbolpositiondescriptions " +
                        "WHERE symposd_sympos_symbolpositionid = ? AND symposd_lang_languageid = ? AND symposd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM symbolpositiondescriptions " +
                        "WHERE symposd_sympos_symbolpositionid = ? AND symposd_lang_languageid = ? AND symposd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = SymbolPositionDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, symbolPosition.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            symbolPositionDescription = SymbolPositionDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return symbolPositionDescription;
    }
    
    public SymbolPositionDescription getSymbolPositionDescription(SymbolPosition symbolPosition, Language language) {
        return getSymbolPositionDescription(symbolPosition, language, EntityPermission.READ_ONLY);
    }
    
    public SymbolPositionDescription getSymbolPositionDescriptionForUpdate(SymbolPosition symbolPosition, Language language) {
        return getSymbolPositionDescription(symbolPosition, language, EntityPermission.READ_WRITE);
    }
    
    public SymbolPositionDescriptionValue getSymbolPositionDescriptionValue(SymbolPositionDescription symbolPositionDescription) {
        return symbolPositionDescription == null? null: symbolPositionDescription.getSymbolPositionDescriptionValue().clone();
    }
    
    public SymbolPositionDescriptionValue getSymbolPositionDescriptionValueForUpdate(SymbolPosition symbolPosition, Language language) {
        return getSymbolPositionDescriptionValue(getSymbolPositionDescriptionForUpdate(symbolPosition, language));
    }
    
    private List<SymbolPositionDescription> getSymbolPositionDescriptionsBySymbolPosition(SymbolPosition symbolPosition, EntityPermission entityPermission) {
        List<SymbolPositionDescription> symbolPositionDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM symbolpositiondescriptions, languages " +
                        "WHERE symposd_sympos_symbolpositionid = ? AND symposd_thrutime = ? AND symposd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM symbolpositiondescriptions " +
                        "WHERE symposd_sympos_symbolpositionid = ? AND symposd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = SymbolPositionDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, symbolPosition.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            symbolPositionDescriptions = SymbolPositionDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return symbolPositionDescriptions;
    }
    
    public List<SymbolPositionDescription> getSymbolPositionDescriptionsBySymbolPosition(SymbolPosition symbolPosition) {
        return getSymbolPositionDescriptionsBySymbolPosition(symbolPosition, EntityPermission.READ_ONLY);
    }
    
    public List<SymbolPositionDescription> getSymbolPositionDescriptionsBySymbolPositionForUpdate(SymbolPosition symbolPosition) {
        return getSymbolPositionDescriptionsBySymbolPosition(symbolPosition, EntityPermission.READ_WRITE);
    }
    
    public String getBestSymbolPositionDescription(SymbolPosition symbolPosition, Language language) {
        String description;
        var symbolPositionDescription = getSymbolPositionDescription(symbolPosition, language);
        
        if(symbolPositionDescription == null && !language.getIsDefault()) {
            symbolPositionDescription = getSymbolPositionDescription(symbolPosition, getPartyControl().getDefaultLanguage());
        }
        
        if(symbolPositionDescription == null) {
            description = symbolPosition.getLastDetail().getSymbolPositionName();
        } else {
            description = symbolPositionDescription.getDescription();
        }
        
        return description;
    }
    
    public SymbolPositionDescriptionTransfer getSymbolPositionDescriptionTransfer(UserVisit userVisit, SymbolPositionDescription symbolPositionDescription) {
        return getAccountingTransferCaches(userVisit).getSymbolPositionDescriptionTransferCache().getTransfer(symbolPositionDescription);
    }
    
    public List<SymbolPositionDescriptionTransfer> getSymbolPositionDescriptionTransfersBySymbolPosition(UserVisit userVisit, SymbolPosition symbolPosition) {
        var symbolPositionDescriptions = getSymbolPositionDescriptionsBySymbolPosition(symbolPosition);
        List<SymbolPositionDescriptionTransfer> symbolPositionDescriptionTransfers = new ArrayList<>(symbolPositionDescriptions.size());
        var symbolPositionDescriptionTransferCache = getAccountingTransferCaches(userVisit).getSymbolPositionDescriptionTransferCache();
        
        symbolPositionDescriptions.forEach((symbolPositionDescription) ->
                symbolPositionDescriptionTransfers.add(symbolPositionDescriptionTransferCache.getTransfer(symbolPositionDescription))
        );
        
        return symbolPositionDescriptionTransfers;
    }
    
    public void updateSymbolPositionDescriptionFromValue(SymbolPositionDescriptionValue symbolPositionDescriptionValue, BasePK updatedBy) {
        if(symbolPositionDescriptionValue.hasBeenModified()) {
            var symbolPositionDescription = SymbolPositionDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, symbolPositionDescriptionValue.getPrimaryKey());
            
            symbolPositionDescription.setThruTime(session.START_TIME_LONG);
            symbolPositionDescription.store();

            var symbolPosition = symbolPositionDescription.getSymbolPosition();
            var language = symbolPositionDescription.getLanguage();
            var description = symbolPositionDescriptionValue.getDescription();
            
            symbolPositionDescription = SymbolPositionDescriptionFactory.getInstance().create(symbolPosition, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(symbolPosition.getPrimaryKey(), EventTypes.MODIFY, symbolPositionDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteSymbolPositionDescription(SymbolPositionDescription symbolPositionDescription, BasePK deletedBy) {
        symbolPositionDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(symbolPositionDescription.getSymbolPositionPK(), EventTypes.MODIFY, symbolPositionDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteSymbolPositionDescriptionsBySymbolPosition(SymbolPosition symbolPosition, BasePK deletedBy) {
        var symbolPositionDescriptions = getSymbolPositionDescriptionsBySymbolPositionForUpdate(symbolPosition);
        
        symbolPositionDescriptions.forEach((symbolPositionDescription) -> 
                deleteSymbolPositionDescription(symbolPositionDescription, deletedBy)
        );
    }
    
}

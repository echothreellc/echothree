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

package com.echothree.control.user.accounting.common;

import com.echothree.control.user.accounting.common.form.*;
import com.echothree.control.user.accounting.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface AccountingService
        extends AccountingForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Currencies
    // -------------------------------------------------------------------------
    
    CommandResult<?> createCurrency(UserVisitPK userVisitPK, CreateCurrencyForm form);
    
    CommandResult<?> setDefaultCurrency(UserVisitPK userVisitPK, SetDefaultCurrencyForm form);
    
    CommandResult<GetCurrencyChoicesResult> getCurrencyChoices(UserVisitPK userVisitPK, GetCurrencyChoicesForm form);
    
    CommandResult<GetCurrenciesResult> getCurrencies(UserVisitPK userVisitPK, GetCurrenciesForm form);
    
    CommandResult<GetCurrencyResult> getCurrency(UserVisitPK userVisitPK, GetCurrencyForm form);

    CommandResult<GetPreferredCurrencyResult> getPreferredCurrency(UserVisitPK userVisitPK, GetPreferredCurrencyForm form);

    // -------------------------------------------------------------------------
    //   Currency Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createCurrencyDescription(UserVisitPK userVisitPK, CreateCurrencyDescriptionForm form);
    
    CommandResult<GetCurrencyDescriptionsResult> getCurrencyDescriptions(UserVisitPK userVisitPK, GetCurrencyDescriptionsForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Accounting Categories
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createItemAccountingCategory(UserVisitPK userVisitPK, CreateItemAccountingCategoryForm form);
    
    CommandResult<GetItemAccountingCategoryChoicesResult> getItemAccountingCategoryChoices(UserVisitPK userVisitPK, GetItemAccountingCategoryChoicesForm form);
    
    CommandResult<GetItemAccountingCategoryResult> getItemAccountingCategory(UserVisitPK userVisitPK, GetItemAccountingCategoryForm form);
    
    CommandResult<GetItemAccountingCategoriesResult> getItemAccountingCategories(UserVisitPK userVisitPK, GetItemAccountingCategoriesForm form);
    
    CommandResult<?> setDefaultItemAccountingCategory(UserVisitPK userVisitPK, SetDefaultItemAccountingCategoryForm form);
    
    CommandResult<EditItemAccountingCategoryResult> editItemAccountingCategory(UserVisitPK userVisitPK, EditItemAccountingCategoryForm form);
    
    CommandResult<?> deleteItemAccountingCategory(UserVisitPK userVisitPK, DeleteItemAccountingCategoryForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Accounting Category Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createItemAccountingCategoryDescription(UserVisitPK userVisitPK, CreateItemAccountingCategoryDescriptionForm form);
    
    CommandResult<GetItemAccountingCategoryDescriptionResult> getItemAccountingCategoryDescription(UserVisitPK userVisitPK, GetItemAccountingCategoryDescriptionForm form);
    
    CommandResult<GetItemAccountingCategoryDescriptionsResult> getItemAccountingCategoryDescriptions(UserVisitPK userVisitPK, GetItemAccountingCategoryDescriptionsForm form);
    
    CommandResult<EditItemAccountingCategoryDescriptionResult> editItemAccountingCategoryDescription(UserVisitPK userVisitPK, EditItemAccountingCategoryDescriptionForm form);
    
    CommandResult<?> deleteItemAccountingCategoryDescription(UserVisitPK userVisitPK, DeleteItemAccountingCategoryDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Gl Account Types
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createGlAccountType(UserVisitPK userVisitPK, CreateGlAccountTypeForm form);
    
    CommandResult<GetGlAccountTypeResult> getGlAccountType(UserVisitPK userVisitPK, GetGlAccountTypeForm form);
    
    CommandResult<GetGlAccountTypesResult> getGlAccountTypes(UserVisitPK userVisitPK, GetGlAccountTypesForm form);
    
    CommandResult<GetGlAccountTypeChoicesResult> getGlAccountTypeChoices(UserVisitPK userVisitPK, GetGlAccountTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Gl Account Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createGlAccountTypeDescription(UserVisitPK userVisitPK, CreateGlAccountTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Gl Account Classes
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createGlAccountClass(UserVisitPK userVisitPK, CreateGlAccountClassForm form);
    
    CommandResult<GetGlAccountClassChoicesResult> getGlAccountClassChoices(UserVisitPK userVisitPK, GetGlAccountClassChoicesForm form);
    
    CommandResult<GetGlAccountClassResult> getGlAccountClass(UserVisitPK userVisitPK, GetGlAccountClassForm form);
    
    CommandResult<GetGlAccountClassesResult> getGlAccountClasses(UserVisitPK userVisitPK, GetGlAccountClassesForm form);
    
    CommandResult<?> setDefaultGlAccountClass(UserVisitPK userVisitPK, SetDefaultGlAccountClassForm form);
    
    CommandResult<EditGlAccountClassResult> editGlAccountClass(UserVisitPK userVisitPK, EditGlAccountClassForm form);
    
    CommandResult<?> deleteGlAccountClass(UserVisitPK userVisitPK, DeleteGlAccountClassForm form);
    
    // --------------------------------------------------------------------------------
    //   Gl Account Class Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createGlAccountClassDescription(UserVisitPK userVisitPK, CreateGlAccountClassDescriptionForm form);
    
    CommandResult<GetGlAccountClassDescriptionResult> getGlAccountClassDescription(UserVisitPK userVisitPK, GetGlAccountClassDescriptionForm form);
    
    CommandResult<GetGlAccountClassDescriptionsResult> getGlAccountClassDescriptions(UserVisitPK userVisitPK, GetGlAccountClassDescriptionsForm form);
    
    CommandResult<EditGlAccountClassDescriptionResult> editGlAccountClassDescription(UserVisitPK userVisitPK, EditGlAccountClassDescriptionForm form);
    
    CommandResult<?> deleteGlAccountClassDescription(UserVisitPK userVisitPK, DeleteGlAccountClassDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Gl Account Categories
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createGlAccountCategory(UserVisitPK userVisitPK, CreateGlAccountCategoryForm form);
    
    CommandResult<GetGlAccountCategoryChoicesResult> getGlAccountCategoryChoices(UserVisitPK userVisitPK, GetGlAccountCategoryChoicesForm form);
    
    CommandResult<GetGlAccountCategoryResult> getGlAccountCategory(UserVisitPK userVisitPK, GetGlAccountCategoryForm form);
    
    CommandResult<GetGlAccountCategoriesResult> getGlAccountCategories(UserVisitPK userVisitPK, GetGlAccountCategoriesForm form);
    
    CommandResult<?> setDefaultGlAccountCategory(UserVisitPK userVisitPK, SetDefaultGlAccountCategoryForm form);
    
    CommandResult<EditGlAccountCategoryResult> editGlAccountCategory(UserVisitPK userVisitPK, EditGlAccountCategoryForm form);
    
    CommandResult<?> deleteGlAccountCategory(UserVisitPK userVisitPK, DeleteGlAccountCategoryForm form);
    
    // --------------------------------------------------------------------------------
    //   Gl Account Category Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createGlAccountCategoryDescription(UserVisitPK userVisitPK, CreateGlAccountCategoryDescriptionForm form);
    
    CommandResult<GetGlAccountCategoryDescriptionResult> getGlAccountCategoryDescription(UserVisitPK userVisitPK, GetGlAccountCategoryDescriptionForm form);
    
    CommandResult<GetGlAccountCategoryDescriptionsResult> getGlAccountCategoryDescriptions(UserVisitPK userVisitPK, GetGlAccountCategoryDescriptionsForm form);
    
    CommandResult<EditGlAccountCategoryDescriptionResult> editGlAccountCategoryDescription(UserVisitPK userVisitPK, EditGlAccountCategoryDescriptionForm form);
    
    CommandResult<?> deleteGlAccountCategoryDescription(UserVisitPK userVisitPK, DeleteGlAccountCategoryDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Gl Resource Types
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createGlResourceType(UserVisitPK userVisitPK, CreateGlResourceTypeForm form);
    
    CommandResult<GetGlResourceTypeChoicesResult> getGlResourceTypeChoices(UserVisitPK userVisitPK, GetGlResourceTypeChoicesForm form);
    
    CommandResult<GetGlResourceTypeResult> getGlResourceType(UserVisitPK userVisitPK, GetGlResourceTypeForm form);
    
    CommandResult<GetGlResourceTypesResult> getGlResourceTypes(UserVisitPK userVisitPK, GetGlResourceTypesForm form);
    
    CommandResult<?> setDefaultGlResourceType(UserVisitPK userVisitPK, SetDefaultGlResourceTypeForm form);
    
    CommandResult<EditGlResourceTypeResult> editGlResourceType(UserVisitPK userVisitPK, EditGlResourceTypeForm form);
    
    CommandResult<?> deleteGlResourceType(UserVisitPK userVisitPK, DeleteGlResourceTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Gl Resource Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createGlResourceTypeDescription(UserVisitPK userVisitPK, CreateGlResourceTypeDescriptionForm form);
    
    CommandResult<GetGlResourceTypeDescriptionResult> getGlResourceTypeDescription(UserVisitPK userVisitPK, GetGlResourceTypeDescriptionForm form);
    
    CommandResult<GetGlResourceTypeDescriptionsResult> getGlResourceTypeDescriptions(UserVisitPK userVisitPK, GetGlResourceTypeDescriptionsForm form);
    
    CommandResult<EditGlResourceTypeDescriptionResult> editGlResourceTypeDescription(UserVisitPK userVisitPK, EditGlResourceTypeDescriptionForm form);
    
    CommandResult<?> deleteGlResourceTypeDescription(UserVisitPK userVisitPK, DeleteGlResourceTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Gl Accounts
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createGlAccount(UserVisitPK userVisitPK, CreateGlAccountForm form);
    
    CommandResult<GetGlAccountChoicesResult> getGlAccountChoices(UserVisitPK userVisitPK, GetGlAccountChoicesForm form);
    
    CommandResult<GetGlAccountResult> getGlAccount(UserVisitPK userVisitPK, GetGlAccountForm form);
    
    CommandResult<GetGlAccountsResult> getGlAccounts(UserVisitPK userVisitPK, GetGlAccountsForm form);
    
    CommandResult<EditGlAccountResult> editGlAccount(UserVisitPK userVisitPK, EditGlAccountForm form);
    
    CommandResult<?> deleteGlAccount(UserVisitPK userVisitPK, DeleteGlAccountForm form);
    
    // --------------------------------------------------------------------------------
    //   Gl Account Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createGlAccountDescription(UserVisitPK userVisitPK, CreateGlAccountDescriptionForm form);
    
    CommandResult<GetGlAccountDescriptionResult> getGlAccountDescription(UserVisitPK userVisitPK, GetGlAccountDescriptionForm form);
    
    CommandResult<GetGlAccountDescriptionsResult> getGlAccountDescriptions(UserVisitPK userVisitPK, GetGlAccountDescriptionsForm form);
    
    CommandResult<EditGlAccountDescriptionResult> editGlAccountDescription(UserVisitPK userVisitPK, EditGlAccountDescriptionForm form);
    
    CommandResult<?> deleteGlAccountDescription(UserVisitPK userVisitPK, DeleteGlAccountDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Transaction Time Types
    // --------------------------------------------------------------------------------

    CommandResult<?> createTransactionTimeType(UserVisitPK userVisitPK, CreateTransactionTimeTypeForm form);

    CommandResult<GetTransactionTimeTypeChoicesResult> getTransactionTimeTypeChoices(UserVisitPK userVisitPK, GetTransactionTimeTypeChoicesForm form);

    CommandResult<GetTransactionTimeTypeResult> getTransactionTimeType(UserVisitPK userVisitPK, GetTransactionTimeTypeForm form);

    CommandResult<GetTransactionTimeTypesResult> getTransactionTimeTypes(UserVisitPK userVisitPK, GetTransactionTimeTypesForm form);

    CommandResult<?> setDefaultTransactionTimeType(UserVisitPK userVisitPK, SetDefaultTransactionTimeTypeForm form);

    CommandResult<EditTransactionTimeTypeResult> editTransactionTimeType(UserVisitPK userVisitPK, EditTransactionTimeTypeForm form);

    CommandResult<?> deleteTransactionTimeType(UserVisitPK userVisitPK, DeleteTransactionTimeTypeForm form);

    // --------------------------------------------------------------------------------
    //   Transaction Time Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<?> createTransactionTimeTypeDescription(UserVisitPK userVisitPK, CreateTransactionTimeTypeDescriptionForm form);

    CommandResult<GetTransactionTimeTypeDescriptionResult> getTransactionTimeTypeDescription(UserVisitPK userVisitPK, GetTransactionTimeTypeDescriptionForm form);

    CommandResult<GetTransactionTimeTypeDescriptionsResult> getTransactionTimeTypeDescriptions(UserVisitPK userVisitPK, GetTransactionTimeTypeDescriptionsForm form);

    CommandResult<EditTransactionTimeTypeDescriptionResult> editTransactionTimeTypeDescription(UserVisitPK userVisitPK, EditTransactionTimeTypeDescriptionForm form);

    CommandResult<?> deleteTransactionTimeTypeDescription(UserVisitPK userVisitPK, DeleteTransactionTimeTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Transaction Types
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createTransactionType(UserVisitPK userVisitPK, CreateTransactionTypeForm form);
    
    CommandResult<GetTransactionTypeResult> getTransactionType(UserVisitPK userVisitPK, GetTransactionTypeForm form);
    
    CommandResult<GetTransactionTypesResult> getTransactionTypes(UserVisitPK userVisitPK, GetTransactionTypesForm form);
    
    CommandResult<EditTransactionTypeResult> editTransactionType(UserVisitPK userVisitPK, EditTransactionTypeForm form);
    
    CommandResult<?> deleteTransactionType(UserVisitPK userVisitPK, DeleteTransactionTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Transaction Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createTransactionTypeDescription(UserVisitPK userVisitPK, CreateTransactionTypeDescriptionForm form);
    
    CommandResult<GetTransactionTypeDescriptionResult> getTransactionTypeDescription(UserVisitPK userVisitPK, GetTransactionTypeDescriptionForm form);
    
    CommandResult<GetTransactionTypeDescriptionsResult> getTransactionTypeDescriptions(UserVisitPK userVisitPK, GetTransactionTypeDescriptionsForm form);
    
    CommandResult<EditTransactionTypeDescriptionResult> editTransactionTypeDescription(UserVisitPK userVisitPK, EditTransactionTypeDescriptionForm form);
    
    CommandResult<?> deleteTransactionTypeDescription(UserVisitPK userVisitPK, DeleteTransactionTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Transaction Gl Account Categories
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createTransactionGlAccountCategory(UserVisitPK userVisitPK, CreateTransactionGlAccountCategoryForm form);
    
    CommandResult<GetTransactionGlAccountCategoryResult> getTransactionGlAccountCategory(UserVisitPK userVisitPK, GetTransactionGlAccountCategoryForm form);
    
    CommandResult<GetTransactionGlAccountCategoriesResult> getTransactionGlAccountCategories(UserVisitPK userVisitPK, GetTransactionGlAccountCategoriesForm form);
    
    CommandResult<EditTransactionGlAccountCategoryResult> editTransactionGlAccountCategory(UserVisitPK userVisitPK, EditTransactionGlAccountCategoryForm form);
    
    CommandResult<?> deleteTransactionGlAccountCategory(UserVisitPK userVisitPK, DeleteTransactionGlAccountCategoryForm form);
    
    // --------------------------------------------------------------------------------
    //   Transaction Gl Account Category Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createTransactionGlAccountCategoryDescription(UserVisitPK userVisitPK, CreateTransactionGlAccountCategoryDescriptionForm form);
    
    CommandResult<GetTransactionGlAccountCategoryDescriptionResult> getTransactionGlAccountCategoryDescription(UserVisitPK userVisitPK, GetTransactionGlAccountCategoryDescriptionForm form);
    
    CommandResult<GetTransactionGlAccountCategoryDescriptionsResult> getTransactionGlAccountCategoryDescriptions(UserVisitPK userVisitPK, GetTransactionGlAccountCategoryDescriptionsForm form);
    
    CommandResult<EditTransactionGlAccountCategoryDescriptionResult> editTransactionGlAccountCategoryDescription(UserVisitPK userVisitPK, EditTransactionGlAccountCategoryDescriptionForm form);
    
    CommandResult<?> deleteTransactionGlAccountCategoryDescription(UserVisitPK userVisitPK, DeleteTransactionGlAccountCategoryDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Transaction Entity Role Types
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createTransactionEntityRoleType(UserVisitPK userVisitPK, CreateTransactionEntityRoleTypeForm form);
    
    CommandResult<GetTransactionEntityRoleTypeResult> getTransactionEntityRoleType(UserVisitPK userVisitPK, GetTransactionEntityRoleTypeForm form);
    
    CommandResult<GetTransactionEntityRoleTypesResult> getTransactionEntityRoleTypes(UserVisitPK userVisitPK, GetTransactionEntityRoleTypesForm form);
    
    CommandResult<EditTransactionEntityRoleTypeResult> editTransactionEntityRoleType(UserVisitPK userVisitPK, EditTransactionEntityRoleTypeForm form);
    
    CommandResult<?> deleteTransactionEntityRoleType(UserVisitPK userVisitPK, DeleteTransactionEntityRoleTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Transaction Entity Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createTransactionEntityRoleTypeDescription(UserVisitPK userVisitPK, CreateTransactionEntityRoleTypeDescriptionForm form);
    
    CommandResult<GetTransactionEntityRoleTypeDescriptionResult> getTransactionEntityRoleTypeDescription(UserVisitPK userVisitPK, GetTransactionEntityRoleTypeDescriptionForm form);
    
    CommandResult<GetTransactionEntityRoleTypeDescriptionsResult> getTransactionEntityRoleTypeDescriptions(UserVisitPK userVisitPK, GetTransactionEntityRoleTypeDescriptionsForm form);
    
    CommandResult<EditTransactionEntityRoleTypeDescriptionResult> editTransactionEntityRoleTypeDescription(UserVisitPK userVisitPK, EditTransactionEntityRoleTypeDescriptionForm form);
    
    CommandResult<?> deleteTransactionEntityRoleTypeDescription(UserVisitPK userVisitPK, DeleteTransactionEntityRoleTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Transaction Groups
    // --------------------------------------------------------------------------------
    
    CommandResult<GetTransactionGroupsResult> getTransactionGroups(UserVisitPK userVisitPK, GetTransactionGroupsForm form);
    
    CommandResult<GetTransactionGroupResult> getTransactionGroup(UserVisitPK userVisitPK, GetTransactionGroupForm form);
    
    CommandResult<GetTransactionGroupStatusChoicesResult> getTransactionGroupStatusChoices(UserVisitPK userVisitPK, GetTransactionGroupStatusChoicesForm form);
    
    CommandResult<?> setTransactionGroupStatus(UserVisitPK userVisitPK, SetTransactionGroupStatusForm form);

    // --------------------------------------------------------------------------------
    //   Transactions
    // --------------------------------------------------------------------------------
    
    CommandResult<GetTransactionsResult> getTransactions(UserVisitPK userVisitPK, GetTransactionsForm form);
    
    CommandResult<GetTransactionResult> getTransaction(UserVisitPK userVisitPK, GetTransactionForm form);
    
    // --------------------------------------------------------------------------------
    //   Symbol Positions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createSymbolPosition(UserVisitPK userVisitPK, CreateSymbolPositionForm form);
    
    CommandResult<GetSymbolPositionChoicesResult> getSymbolPositionChoices(UserVisitPK userVisitPK, GetSymbolPositionChoicesForm form);
    
    CommandResult<GetSymbolPositionResult> getSymbolPosition(UserVisitPK userVisitPK, GetSymbolPositionForm form);
    
    CommandResult<GetSymbolPositionsResult> getSymbolPositions(UserVisitPK userVisitPK, GetSymbolPositionsForm form);
    
    CommandResult<?> setDefaultSymbolPosition(UserVisitPK userVisitPK, SetDefaultSymbolPositionForm form);
    
    CommandResult<EditSymbolPositionResult> editSymbolPosition(UserVisitPK userVisitPK, EditSymbolPositionForm form);
    
    CommandResult<?> deleteSymbolPosition(UserVisitPK userVisitPK, DeleteSymbolPositionForm form);
    
    // --------------------------------------------------------------------------------
    //   Symbol Position Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createSymbolPositionDescription(UserVisitPK userVisitPK, CreateSymbolPositionDescriptionForm form);
    
    CommandResult<GetSymbolPositionDescriptionResult> getSymbolPositionDescription(UserVisitPK userVisitPK, GetSymbolPositionDescriptionForm form);
    
    CommandResult<GetSymbolPositionDescriptionsResult> getSymbolPositionDescriptions(UserVisitPK userVisitPK, GetSymbolPositionDescriptionsForm form);
    
    CommandResult<EditSymbolPositionDescriptionResult> editSymbolPositionDescription(UserVisitPK userVisitPK, EditSymbolPositionDescriptionForm form);
    
    CommandResult<?> deleteSymbolPositionDescription(UserVisitPK userVisitPK, DeleteSymbolPositionDescriptionForm form);
    
}

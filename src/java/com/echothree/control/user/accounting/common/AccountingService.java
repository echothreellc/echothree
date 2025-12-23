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
import com.echothree.control.user.order.common.form.CreateOrderTimeTypeDescriptionForm;
import com.echothree.control.user.order.common.form.CreateOrderTimeTypeForm;
import com.echothree.control.user.order.common.form.DeleteOrderTimeTypeDescriptionForm;
import com.echothree.control.user.order.common.form.DeleteOrderTimeTypeForm;
import com.echothree.control.user.order.common.form.EditOrderTimeTypeDescriptionForm;
import com.echothree.control.user.order.common.form.EditOrderTimeTypeForm;
import com.echothree.control.user.order.common.form.GetOrderTimeTypeChoicesForm;
import com.echothree.control.user.order.common.form.GetOrderTimeTypeDescriptionForm;
import com.echothree.control.user.order.common.form.GetOrderTimeTypeDescriptionsForm;
import com.echothree.control.user.order.common.form.GetOrderTimeTypeForm;
import com.echothree.control.user.order.common.form.GetOrderTimeTypesForm;
import com.echothree.control.user.order.common.form.SetDefaultOrderTimeTypeForm;
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
    
    CommandResult createCurrency(UserVisitPK userVisitPK, CreateCurrencyForm form);
    
    CommandResult setDefaultCurrency(UserVisitPK userVisitPK, SetDefaultCurrencyForm form);
    
    CommandResult getCurrencyChoices(UserVisitPK userVisitPK, GetCurrencyChoicesForm form);
    
    CommandResult getCurrencies(UserVisitPK userVisitPK, GetCurrenciesForm form);
    
    CommandResult getCurrency(UserVisitPK userVisitPK, GetCurrencyForm form);

    CommandResult getPreferredCurrency(UserVisitPK userVisitPK, GetPreferredCurrencyForm form);

    // -------------------------------------------------------------------------
    //   Currency Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createCurrencyDescription(UserVisitPK userVisitPK, CreateCurrencyDescriptionForm form);
    
    CommandResult getCurrencyDescriptions(UserVisitPK userVisitPK, GetCurrencyDescriptionsForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Accounting Categories
    // --------------------------------------------------------------------------------
    
    CommandResult createItemAccountingCategory(UserVisitPK userVisitPK, CreateItemAccountingCategoryForm form);
    
    CommandResult getItemAccountingCategoryChoices(UserVisitPK userVisitPK, GetItemAccountingCategoryChoicesForm form);
    
    CommandResult getItemAccountingCategory(UserVisitPK userVisitPK, GetItemAccountingCategoryForm form);
    
    CommandResult getItemAccountingCategories(UserVisitPK userVisitPK, GetItemAccountingCategoriesForm form);
    
    CommandResult setDefaultItemAccountingCategory(UserVisitPK userVisitPK, SetDefaultItemAccountingCategoryForm form);
    
    CommandResult editItemAccountingCategory(UserVisitPK userVisitPK, EditItemAccountingCategoryForm form);
    
    CommandResult deleteItemAccountingCategory(UserVisitPK userVisitPK, DeleteItemAccountingCategoryForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Accounting Category Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createItemAccountingCategoryDescription(UserVisitPK userVisitPK, CreateItemAccountingCategoryDescriptionForm form);
    
    CommandResult getItemAccountingCategoryDescription(UserVisitPK userVisitPK, GetItemAccountingCategoryDescriptionForm form);
    
    CommandResult getItemAccountingCategoryDescriptions(UserVisitPK userVisitPK, GetItemAccountingCategoryDescriptionsForm form);
    
    CommandResult editItemAccountingCategoryDescription(UserVisitPK userVisitPK, EditItemAccountingCategoryDescriptionForm form);
    
    CommandResult deleteItemAccountingCategoryDescription(UserVisitPK userVisitPK, DeleteItemAccountingCategoryDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Gl Account Types
    // --------------------------------------------------------------------------------
    
    CommandResult createGlAccountType(UserVisitPK userVisitPK, CreateGlAccountTypeForm form);
    
    CommandResult getGlAccountType(UserVisitPK userVisitPK, GetGlAccountTypeForm form);
    
    CommandResult getGlAccountTypes(UserVisitPK userVisitPK, GetGlAccountTypesForm form);
    
    CommandResult getGlAccountTypeChoices(UserVisitPK userVisitPK, GetGlAccountTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Gl Account Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createGlAccountTypeDescription(UserVisitPK userVisitPK, CreateGlAccountTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Gl Account Classes
    // --------------------------------------------------------------------------------
    
    CommandResult createGlAccountClass(UserVisitPK userVisitPK, CreateGlAccountClassForm form);
    
    CommandResult getGlAccountClassChoices(UserVisitPK userVisitPK, GetGlAccountClassChoicesForm form);
    
    CommandResult getGlAccountClass(UserVisitPK userVisitPK, GetGlAccountClassForm form);
    
    CommandResult getGlAccountClasses(UserVisitPK userVisitPK, GetGlAccountClassesForm form);
    
    CommandResult setDefaultGlAccountClass(UserVisitPK userVisitPK, SetDefaultGlAccountClassForm form);
    
    CommandResult editGlAccountClass(UserVisitPK userVisitPK, EditGlAccountClassForm form);
    
    CommandResult deleteGlAccountClass(UserVisitPK userVisitPK, DeleteGlAccountClassForm form);
    
    // --------------------------------------------------------------------------------
    //   Gl Account Class Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createGlAccountClassDescription(UserVisitPK userVisitPK, CreateGlAccountClassDescriptionForm form);
    
    CommandResult getGlAccountClassDescription(UserVisitPK userVisitPK, GetGlAccountClassDescriptionForm form);
    
    CommandResult getGlAccountClassDescriptions(UserVisitPK userVisitPK, GetGlAccountClassDescriptionsForm form);
    
    CommandResult editGlAccountClassDescription(UserVisitPK userVisitPK, EditGlAccountClassDescriptionForm form);
    
    CommandResult deleteGlAccountClassDescription(UserVisitPK userVisitPK, DeleteGlAccountClassDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Gl Account Categories
    // --------------------------------------------------------------------------------
    
    CommandResult createGlAccountCategory(UserVisitPK userVisitPK, CreateGlAccountCategoryForm form);
    
    CommandResult getGlAccountCategoryChoices(UserVisitPK userVisitPK, GetGlAccountCategoryChoicesForm form);
    
    CommandResult getGlAccountCategory(UserVisitPK userVisitPK, GetGlAccountCategoryForm form);
    
    CommandResult getGlAccountCategories(UserVisitPK userVisitPK, GetGlAccountCategoriesForm form);
    
    CommandResult setDefaultGlAccountCategory(UserVisitPK userVisitPK, SetDefaultGlAccountCategoryForm form);
    
    CommandResult editGlAccountCategory(UserVisitPK userVisitPK, EditGlAccountCategoryForm form);
    
    CommandResult deleteGlAccountCategory(UserVisitPK userVisitPK, DeleteGlAccountCategoryForm form);
    
    // --------------------------------------------------------------------------------
    //   Gl Account Category Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createGlAccountCategoryDescription(UserVisitPK userVisitPK, CreateGlAccountCategoryDescriptionForm form);
    
    CommandResult getGlAccountCategoryDescription(UserVisitPK userVisitPK, GetGlAccountCategoryDescriptionForm form);
    
    CommandResult getGlAccountCategoryDescriptions(UserVisitPK userVisitPK, GetGlAccountCategoryDescriptionsForm form);
    
    CommandResult editGlAccountCategoryDescription(UserVisitPK userVisitPK, EditGlAccountCategoryDescriptionForm form);
    
    CommandResult deleteGlAccountCategoryDescription(UserVisitPK userVisitPK, DeleteGlAccountCategoryDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Gl Resource Types
    // --------------------------------------------------------------------------------
    
    CommandResult createGlResourceType(UserVisitPK userVisitPK, CreateGlResourceTypeForm form);
    
    CommandResult getGlResourceTypeChoices(UserVisitPK userVisitPK, GetGlResourceTypeChoicesForm form);
    
    CommandResult getGlResourceType(UserVisitPK userVisitPK, GetGlResourceTypeForm form);
    
    CommandResult getGlResourceTypes(UserVisitPK userVisitPK, GetGlResourceTypesForm form);
    
    CommandResult setDefaultGlResourceType(UserVisitPK userVisitPK, SetDefaultGlResourceTypeForm form);
    
    CommandResult editGlResourceType(UserVisitPK userVisitPK, EditGlResourceTypeForm form);
    
    CommandResult deleteGlResourceType(UserVisitPK userVisitPK, DeleteGlResourceTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Gl Resource Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createGlResourceTypeDescription(UserVisitPK userVisitPK, CreateGlResourceTypeDescriptionForm form);
    
    CommandResult getGlResourceTypeDescription(UserVisitPK userVisitPK, GetGlResourceTypeDescriptionForm form);
    
    CommandResult getGlResourceTypeDescriptions(UserVisitPK userVisitPK, GetGlResourceTypeDescriptionsForm form);
    
    CommandResult editGlResourceTypeDescription(UserVisitPK userVisitPK, EditGlResourceTypeDescriptionForm form);
    
    CommandResult deleteGlResourceTypeDescription(UserVisitPK userVisitPK, DeleteGlResourceTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Gl Accounts
    // --------------------------------------------------------------------------------
    
    CommandResult createGlAccount(UserVisitPK userVisitPK, CreateGlAccountForm form);
    
    CommandResult getGlAccountChoices(UserVisitPK userVisitPK, GetGlAccountChoicesForm form);
    
    CommandResult getGlAccount(UserVisitPK userVisitPK, GetGlAccountForm form);
    
    CommandResult getGlAccounts(UserVisitPK userVisitPK, GetGlAccountsForm form);
    
    CommandResult editGlAccount(UserVisitPK userVisitPK, EditGlAccountForm form);
    
    CommandResult deleteGlAccount(UserVisitPK userVisitPK, DeleteGlAccountForm form);
    
    // --------------------------------------------------------------------------------
    //   Gl Account Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createGlAccountDescription(UserVisitPK userVisitPK, CreateGlAccountDescriptionForm form);
    
    CommandResult getGlAccountDescription(UserVisitPK userVisitPK, GetGlAccountDescriptionForm form);
    
    CommandResult getGlAccountDescriptions(UserVisitPK userVisitPK, GetGlAccountDescriptionsForm form);
    
    CommandResult editGlAccountDescription(UserVisitPK userVisitPK, EditGlAccountDescriptionForm form);
    
    CommandResult deleteGlAccountDescription(UserVisitPK userVisitPK, DeleteGlAccountDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Transaction Time Types
    // --------------------------------------------------------------------------------

    CommandResult createTransactionTimeType(UserVisitPK userVisitPK, CreateTransactionTimeTypeForm form);

    CommandResult getTransactionTimeTypeChoices(UserVisitPK userVisitPK, GetTransactionTimeTypeChoicesForm form);

    CommandResult getTransactionTimeType(UserVisitPK userVisitPK, GetTransactionTimeTypeForm form);

    CommandResult getTransactionTimeTypes(UserVisitPK userVisitPK, GetTransactionTimeTypesForm form);

    CommandResult setDefaultTransactionTimeType(UserVisitPK userVisitPK, SetDefaultTransactionTimeTypeForm form);

    CommandResult editTransactionTimeType(UserVisitPK userVisitPK, EditTransactionTimeTypeForm form);

    CommandResult deleteTransactionTimeType(UserVisitPK userVisitPK, DeleteTransactionTimeTypeForm form);

    // --------------------------------------------------------------------------------
    //   Transaction Time Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createTransactionTimeTypeDescription(UserVisitPK userVisitPK, CreateTransactionTimeTypeDescriptionForm form);

    CommandResult getTransactionTimeTypeDescription(UserVisitPK userVisitPK, GetTransactionTimeTypeDescriptionForm form);

    CommandResult getTransactionTimeTypeDescriptions(UserVisitPK userVisitPK, GetTransactionTimeTypeDescriptionsForm form);

    CommandResult editTransactionTimeTypeDescription(UserVisitPK userVisitPK, EditTransactionTimeTypeDescriptionForm form);

    CommandResult deleteTransactionTimeTypeDescription(UserVisitPK userVisitPK, DeleteTransactionTimeTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Transaction Types
    // --------------------------------------------------------------------------------
    
    CommandResult createTransactionType(UserVisitPK userVisitPK, CreateTransactionTypeForm form);
    
    CommandResult getTransactionType(UserVisitPK userVisitPK, GetTransactionTypeForm form);
    
    CommandResult getTransactionTypes(UserVisitPK userVisitPK, GetTransactionTypesForm form);
    
    CommandResult editTransactionType(UserVisitPK userVisitPK, EditTransactionTypeForm form);
    
    CommandResult deleteTransactionType(UserVisitPK userVisitPK, DeleteTransactionTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Transaction Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createTransactionTypeDescription(UserVisitPK userVisitPK, CreateTransactionTypeDescriptionForm form);
    
    CommandResult getTransactionTypeDescription(UserVisitPK userVisitPK, GetTransactionTypeDescriptionForm form);
    
    CommandResult getTransactionTypeDescriptions(UserVisitPK userVisitPK, GetTransactionTypeDescriptionsForm form);
    
    CommandResult editTransactionTypeDescription(UserVisitPK userVisitPK, EditTransactionTypeDescriptionForm form);
    
    CommandResult deleteTransactionTypeDescription(UserVisitPK userVisitPK, DeleteTransactionTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Transaction Gl Account Categories
    // --------------------------------------------------------------------------------
    
    CommandResult createTransactionGlAccountCategory(UserVisitPK userVisitPK, CreateTransactionGlAccountCategoryForm form);
    
    CommandResult getTransactionGlAccountCategory(UserVisitPK userVisitPK, GetTransactionGlAccountCategoryForm form);
    
    CommandResult getTransactionGlAccountCategories(UserVisitPK userVisitPK, GetTransactionGlAccountCategoriesForm form);
    
    CommandResult editTransactionGlAccountCategory(UserVisitPK userVisitPK, EditTransactionGlAccountCategoryForm form);
    
    CommandResult deleteTransactionGlAccountCategory(UserVisitPK userVisitPK, DeleteTransactionGlAccountCategoryForm form);
    
    // --------------------------------------------------------------------------------
    //   Transaction Gl Account Category Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createTransactionGlAccountCategoryDescription(UserVisitPK userVisitPK, CreateTransactionGlAccountCategoryDescriptionForm form);
    
    CommandResult getTransactionGlAccountCategoryDescription(UserVisitPK userVisitPK, GetTransactionGlAccountCategoryDescriptionForm form);
    
    CommandResult getTransactionGlAccountCategoryDescriptions(UserVisitPK userVisitPK, GetTransactionGlAccountCategoryDescriptionsForm form);
    
    CommandResult editTransactionGlAccountCategoryDescription(UserVisitPK userVisitPK, EditTransactionGlAccountCategoryDescriptionForm form);
    
    CommandResult deleteTransactionGlAccountCategoryDescription(UserVisitPK userVisitPK, DeleteTransactionGlAccountCategoryDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Transaction Entity Role Types
    // --------------------------------------------------------------------------------
    
    CommandResult createTransactionEntityRoleType(UserVisitPK userVisitPK, CreateTransactionEntityRoleTypeForm form);
    
    CommandResult getTransactionEntityRoleType(UserVisitPK userVisitPK, GetTransactionEntityRoleTypeForm form);
    
    CommandResult getTransactionEntityRoleTypes(UserVisitPK userVisitPK, GetTransactionEntityRoleTypesForm form);
    
    CommandResult editTransactionEntityRoleType(UserVisitPK userVisitPK, EditTransactionEntityRoleTypeForm form);
    
    CommandResult deleteTransactionEntityRoleType(UserVisitPK userVisitPK, DeleteTransactionEntityRoleTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Transaction Entity Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createTransactionEntityRoleTypeDescription(UserVisitPK userVisitPK, CreateTransactionEntityRoleTypeDescriptionForm form);
    
    CommandResult getTransactionEntityRoleTypeDescription(UserVisitPK userVisitPK, GetTransactionEntityRoleTypeDescriptionForm form);
    
    CommandResult getTransactionEntityRoleTypeDescriptions(UserVisitPK userVisitPK, GetTransactionEntityRoleTypeDescriptionsForm form);
    
    CommandResult editTransactionEntityRoleTypeDescription(UserVisitPK userVisitPK, EditTransactionEntityRoleTypeDescriptionForm form);
    
    CommandResult deleteTransactionEntityRoleTypeDescription(UserVisitPK userVisitPK, DeleteTransactionEntityRoleTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Transaction Groups
    // --------------------------------------------------------------------------------
    
    CommandResult getTransactionGroups(UserVisitPK userVisitPK, GetTransactionGroupsForm form);
    
    CommandResult getTransactionGroup(UserVisitPK userVisitPK, GetTransactionGroupForm form);
    
    CommandResult getTransactionGroupStatusChoices(UserVisitPK userVisitPK, GetTransactionGroupStatusChoicesForm form);
    
    CommandResult setTransactionGroupStatus(UserVisitPK userVisitPK, SetTransactionGroupStatusForm form);

    // --------------------------------------------------------------------------------
    //   Transactions
    // --------------------------------------------------------------------------------
    
    CommandResult getTransactions(UserVisitPK userVisitPK, GetTransactionsForm form);
    
    CommandResult getTransaction(UserVisitPK userVisitPK, GetTransactionForm form);
    
    // --------------------------------------------------------------------------------
    //   Symbol Positions
    // --------------------------------------------------------------------------------
    
    CommandResult createSymbolPosition(UserVisitPK userVisitPK, CreateSymbolPositionForm form);
    
    CommandResult getSymbolPositionChoices(UserVisitPK userVisitPK, GetSymbolPositionChoicesForm form);
    
    CommandResult getSymbolPosition(UserVisitPK userVisitPK, GetSymbolPositionForm form);
    
    CommandResult getSymbolPositions(UserVisitPK userVisitPK, GetSymbolPositionsForm form);
    
    CommandResult setDefaultSymbolPosition(UserVisitPK userVisitPK, SetDefaultSymbolPositionForm form);
    
    CommandResult editSymbolPosition(UserVisitPK userVisitPK, EditSymbolPositionForm form);
    
    CommandResult deleteSymbolPosition(UserVisitPK userVisitPK, DeleteSymbolPositionForm form);
    
    // --------------------------------------------------------------------------------
    //   Symbol Position Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createSymbolPositionDescription(UserVisitPK userVisitPK, CreateSymbolPositionDescriptionForm form);
    
    CommandResult getSymbolPositionDescription(UserVisitPK userVisitPK, GetSymbolPositionDescriptionForm form);
    
    CommandResult getSymbolPositionDescriptions(UserVisitPK userVisitPK, GetSymbolPositionDescriptionsForm form);
    
    CommandResult editSymbolPositionDescription(UserVisitPK userVisitPK, EditSymbolPositionDescriptionForm form);
    
    CommandResult deleteSymbolPositionDescription(UserVisitPK userVisitPK, DeleteSymbolPositionDescriptionForm form);
    
}

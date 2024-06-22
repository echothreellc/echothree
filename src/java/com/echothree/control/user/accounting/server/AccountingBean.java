// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.control.user.accounting.server;

import com.echothree.control.user.accounting.common.AccountingRemote;
import com.echothree.control.user.accounting.common.form.*;
import com.echothree.control.user.accounting.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class AccountingBean
        extends AccountingFormsImpl
        implements AccountingRemote, AccountingLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "AccountingBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Currencies
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCurrency(UserVisitPK userVisitPK, CreateCurrencyForm form) {
        return new CreateCurrencyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultCurrency(UserVisitPK userVisitPK, SetDefaultCurrencyForm form) {
        return new SetDefaultCurrencyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCurrencyChoices(UserVisitPK userVisitPK, GetCurrencyChoicesForm form) {
        return new GetCurrencyChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCurrencies(UserVisitPK userVisitPK, GetCurrenciesForm form) {
        return new GetCurrenciesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCurrency(UserVisitPK userVisitPK, GetCurrencyForm form) {
        return new GetCurrencyCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPreferredCurrency(UserVisitPK userVisitPK, GetPreferredCurrencyForm form) {
        return new GetPreferredCurrencyCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Currency Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCurrencyDescription(UserVisitPK userVisitPK, CreateCurrencyDescriptionForm form) {
        return new CreateCurrencyDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCurrencyDescriptions(UserVisitPK userVisitPK, GetCurrencyDescriptionsForm form) {
        return new GetCurrencyDescriptionsCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Accounting Categories
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemAccountingCategory(UserVisitPK userVisitPK, CreateItemAccountingCategoryForm form) {
        return new CreateItemAccountingCategoryCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemAccountingCategoryChoices(UserVisitPK userVisitPK, GetItemAccountingCategoryChoicesForm form) {
        return new GetItemAccountingCategoryChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemAccountingCategory(UserVisitPK userVisitPK, GetItemAccountingCategoryForm form) {
        return new GetItemAccountingCategoryCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemAccountingCategories(UserVisitPK userVisitPK, GetItemAccountingCategoriesForm form) {
        return new GetItemAccountingCategoriesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultItemAccountingCategory(UserVisitPK userVisitPK, SetDefaultItemAccountingCategoryForm form) {
        return new SetDefaultItemAccountingCategoryCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editItemAccountingCategory(UserVisitPK userVisitPK, EditItemAccountingCategoryForm form) {
        return new EditItemAccountingCategoryCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteItemAccountingCategory(UserVisitPK userVisitPK, DeleteItemAccountingCategoryForm form) {
        return new DeleteItemAccountingCategoryCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Accounting Category Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemAccountingCategoryDescription(UserVisitPK userVisitPK, CreateItemAccountingCategoryDescriptionForm form) {
        return new CreateItemAccountingCategoryDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemAccountingCategoryDescription(UserVisitPK userVisitPK, GetItemAccountingCategoryDescriptionForm form) {
        return new GetItemAccountingCategoryDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemAccountingCategoryDescriptions(UserVisitPK userVisitPK, GetItemAccountingCategoryDescriptionsForm form) {
        return new GetItemAccountingCategoryDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editItemAccountingCategoryDescription(UserVisitPK userVisitPK, EditItemAccountingCategoryDescriptionForm form) {
        return new EditItemAccountingCategoryDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteItemAccountingCategoryDescription(UserVisitPK userVisitPK, DeleteItemAccountingCategoryDescriptionForm form) {
        return new DeleteItemAccountingCategoryDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createGlAccountType(UserVisitPK userVisitPK, CreateGlAccountTypeForm form) {
        return new CreateGlAccountTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGlAccountType(UserVisitPK userVisitPK, GetGlAccountTypeForm form) {
        return new GetGlAccountTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGlAccountTypes(UserVisitPK userVisitPK, GetGlAccountTypesForm form) {
        return new GetGlAccountTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGlAccountTypeChoices(UserVisitPK userVisitPK, GetGlAccountTypeChoicesForm form) {
        return new GetGlAccountTypeChoicesCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createGlAccountTypeDescription(UserVisitPK userVisitPK, CreateGlAccountTypeDescriptionForm form) {
        return new CreateGlAccountTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Classes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createGlAccountClass(UserVisitPK userVisitPK, CreateGlAccountClassForm form) {
        return new CreateGlAccountClassCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGlAccountClassChoices(UserVisitPK userVisitPK, GetGlAccountClassChoicesForm form) {
        return new GetGlAccountClassChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGlAccountClass(UserVisitPK userVisitPK, GetGlAccountClassForm form) {
        return new GetGlAccountClassCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGlAccountClasses(UserVisitPK userVisitPK, GetGlAccountClassesForm form) {
        return new GetGlAccountClassesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultGlAccountClass(UserVisitPK userVisitPK, SetDefaultGlAccountClassForm form) {
        return new SetDefaultGlAccountClassCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editGlAccountClass(UserVisitPK userVisitPK, EditGlAccountClassForm form) {
        return new EditGlAccountClassCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteGlAccountClass(UserVisitPK userVisitPK, DeleteGlAccountClassForm form) {
        return new DeleteGlAccountClassCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Class Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createGlAccountClassDescription(UserVisitPK userVisitPK, CreateGlAccountClassDescriptionForm form) {
        return new CreateGlAccountClassDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGlAccountClassDescription(UserVisitPK userVisitPK, GetGlAccountClassDescriptionForm form) {
        return new GetGlAccountClassDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGlAccountClassDescriptions(UserVisitPK userVisitPK, GetGlAccountClassDescriptionsForm form) {
        return new GetGlAccountClassDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editGlAccountClassDescription(UserVisitPK userVisitPK, EditGlAccountClassDescriptionForm form) {
        return new EditGlAccountClassDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteGlAccountClassDescription(UserVisitPK userVisitPK, DeleteGlAccountClassDescriptionForm form) {
        return new DeleteGlAccountClassDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Categories
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createGlAccountCategory(UserVisitPK userVisitPK, CreateGlAccountCategoryForm form) {
        return new CreateGlAccountCategoryCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGlAccountCategoryChoices(UserVisitPK userVisitPK, GetGlAccountCategoryChoicesForm form) {
        return new GetGlAccountCategoryChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGlAccountCategory(UserVisitPK userVisitPK, GetGlAccountCategoryForm form) {
        return new GetGlAccountCategoryCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGlAccountCategories(UserVisitPK userVisitPK, GetGlAccountCategoriesForm form) {
        return new GetGlAccountCategoriesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultGlAccountCategory(UserVisitPK userVisitPK, SetDefaultGlAccountCategoryForm form) {
        return new SetDefaultGlAccountCategoryCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editGlAccountCategory(UserVisitPK userVisitPK, EditGlAccountCategoryForm form) {
        return new EditGlAccountCategoryCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteGlAccountCategory(UserVisitPK userVisitPK, DeleteGlAccountCategoryForm form) {
        return new DeleteGlAccountCategoryCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Category Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createGlAccountCategoryDescription(UserVisitPK userVisitPK, CreateGlAccountCategoryDescriptionForm form) {
        return new CreateGlAccountCategoryDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGlAccountCategoryDescription(UserVisitPK userVisitPK, GetGlAccountCategoryDescriptionForm form) {
        return new GetGlAccountCategoryDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGlAccountCategoryDescriptions(UserVisitPK userVisitPK, GetGlAccountCategoryDescriptionsForm form) {
        return new GetGlAccountCategoryDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editGlAccountCategoryDescription(UserVisitPK userVisitPK, EditGlAccountCategoryDescriptionForm form) {
        return new EditGlAccountCategoryDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteGlAccountCategoryDescription(UserVisitPK userVisitPK, DeleteGlAccountCategoryDescriptionForm form) {
        return new DeleteGlAccountCategoryDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Resource Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createGlResourceType(UserVisitPK userVisitPK, CreateGlResourceTypeForm form) {
        return new CreateGlResourceTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGlResourceTypeChoices(UserVisitPK userVisitPK, GetGlResourceTypeChoicesForm form) {
        return new GetGlResourceTypeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGlResourceType(UserVisitPK userVisitPK, GetGlResourceTypeForm form) {
        return new GetGlResourceTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGlResourceTypes(UserVisitPK userVisitPK, GetGlResourceTypesForm form) {
        return new GetGlResourceTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultGlResourceType(UserVisitPK userVisitPK, SetDefaultGlResourceTypeForm form) {
        return new SetDefaultGlResourceTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editGlResourceType(UserVisitPK userVisitPK, EditGlResourceTypeForm form) {
        return new EditGlResourceTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteGlResourceType(UserVisitPK userVisitPK, DeleteGlResourceTypeForm form) {
        return new DeleteGlResourceTypeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Resource Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createGlResourceTypeDescription(UserVisitPK userVisitPK, CreateGlResourceTypeDescriptionForm form) {
        return new CreateGlResourceTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGlResourceTypeDescription(UserVisitPK userVisitPK, GetGlResourceTypeDescriptionForm form) {
        return new GetGlResourceTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGlResourceTypeDescriptions(UserVisitPK userVisitPK, GetGlResourceTypeDescriptionsForm form) {
        return new GetGlResourceTypeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editGlResourceTypeDescription(UserVisitPK userVisitPK, EditGlResourceTypeDescriptionForm form) {
        return new EditGlResourceTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteGlResourceTypeDescription(UserVisitPK userVisitPK, DeleteGlResourceTypeDescriptionForm form) {
        return new DeleteGlResourceTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Accounts
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createGlAccount(UserVisitPK userVisitPK, CreateGlAccountForm form) {
        return new CreateGlAccountCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGlAccountChoices(UserVisitPK userVisitPK, GetGlAccountChoicesForm form) {
        return new GetGlAccountChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGlAccount(UserVisitPK userVisitPK, GetGlAccountForm form) {
        return new GetGlAccountCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGlAccounts(UserVisitPK userVisitPK, GetGlAccountsForm form) {
        return new GetGlAccountsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editGlAccount(UserVisitPK userVisitPK, EditGlAccountForm form) {
        return new EditGlAccountCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteGlAccount(UserVisitPK userVisitPK, DeleteGlAccountForm form) {
        return new DeleteGlAccountCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createGlAccountDescription(UserVisitPK userVisitPK, CreateGlAccountDescriptionForm form) {
        return new CreateGlAccountDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGlAccountDescription(UserVisitPK userVisitPK, GetGlAccountDescriptionForm form) {
        return new GetGlAccountDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGlAccountDescriptions(UserVisitPK userVisitPK, GetGlAccountDescriptionsForm form) {
        return new GetGlAccountDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editGlAccountDescription(UserVisitPK userVisitPK, EditGlAccountDescriptionForm form) {
        return new EditGlAccountDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteGlAccountDescription(UserVisitPK userVisitPK, DeleteGlAccountDescriptionForm form) {
        return new DeleteGlAccountDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTransactionType(UserVisitPK userVisitPK, CreateTransactionTypeForm form) {
        return new CreateTransactionTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTransactionType(UserVisitPK userVisitPK, GetTransactionTypeForm form) {
        return new GetTransactionTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTransactionTypes(UserVisitPK userVisitPK, GetTransactionTypesForm form) {
        return new GetTransactionTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTransactionType(UserVisitPK userVisitPK, EditTransactionTypeForm form) {
        return new EditTransactionTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTransactionType(UserVisitPK userVisitPK, DeleteTransactionTypeForm form) {
        return new DeleteTransactionTypeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTransactionTypeDescription(UserVisitPK userVisitPK, CreateTransactionTypeDescriptionForm form) {
        return new CreateTransactionTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTransactionTypeDescription(UserVisitPK userVisitPK, GetTransactionTypeDescriptionForm form) {
        return new GetTransactionTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTransactionTypeDescriptions(UserVisitPK userVisitPK, GetTransactionTypeDescriptionsForm form) {
        return new GetTransactionTypeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTransactionTypeDescription(UserVisitPK userVisitPK, EditTransactionTypeDescriptionForm form) {
        return new EditTransactionTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTransactionTypeDescription(UserVisitPK userVisitPK, DeleteTransactionTypeDescriptionForm form) {
        return new DeleteTransactionTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Gl Account Categories
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTransactionGlAccountCategory(UserVisitPK userVisitPK, CreateTransactionGlAccountCategoryForm form) {
        return new CreateTransactionGlAccountCategoryCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTransactionGlAccountCategory(UserVisitPK userVisitPK, GetTransactionGlAccountCategoryForm form) {
        return new GetTransactionGlAccountCategoryCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTransactionGlAccountCategories(UserVisitPK userVisitPK, GetTransactionGlAccountCategoriesForm form) {
        return new GetTransactionGlAccountCategoriesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTransactionGlAccountCategory(UserVisitPK userVisitPK, EditTransactionGlAccountCategoryForm form) {
        return new EditTransactionGlAccountCategoryCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTransactionGlAccountCategory(UserVisitPK userVisitPK, DeleteTransactionGlAccountCategoryForm form) {
        return new DeleteTransactionGlAccountCategoryCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Gl Account Category Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTransactionGlAccountCategoryDescription(UserVisitPK userVisitPK, CreateTransactionGlAccountCategoryDescriptionForm form) {
        return new CreateTransactionGlAccountCategoryDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTransactionGlAccountCategoryDescription(UserVisitPK userVisitPK, GetTransactionGlAccountCategoryDescriptionForm form) {
        return new GetTransactionGlAccountCategoryDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTransactionGlAccountCategoryDescriptions(UserVisitPK userVisitPK, GetTransactionGlAccountCategoryDescriptionsForm form) {
        return new GetTransactionGlAccountCategoryDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTransactionGlAccountCategoryDescription(UserVisitPK userVisitPK, EditTransactionGlAccountCategoryDescriptionForm form) {
        return new EditTransactionGlAccountCategoryDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTransactionGlAccountCategoryDescription(UserVisitPK userVisitPK, DeleteTransactionGlAccountCategoryDescriptionForm form) {
        return new DeleteTransactionGlAccountCategoryDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Entity Role Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTransactionEntityRoleType(UserVisitPK userVisitPK, CreateTransactionEntityRoleTypeForm form) {
        return new CreateTransactionEntityRoleTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTransactionEntityRoleType(UserVisitPK userVisitPK, GetTransactionEntityRoleTypeForm form) {
        return new GetTransactionEntityRoleTypeCommand(userVisitPK, form).run();
    }
    @Override
    public CommandResult getTransactionEntityRoleTypes(UserVisitPK userVisitPK, GetTransactionEntityRoleTypesForm form) {
        return new GetTransactionEntityRoleTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTransactionEntityRoleType(UserVisitPK userVisitPK, EditTransactionEntityRoleTypeForm form) {
        return new EditTransactionEntityRoleTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTransactionEntityRoleType(UserVisitPK userVisitPK, DeleteTransactionEntityRoleTypeForm form) {
        return new DeleteTransactionEntityRoleTypeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Entity Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTransactionEntityRoleTypeDescription(UserVisitPK userVisitPK, CreateTransactionEntityRoleTypeDescriptionForm form) {
        return new CreateTransactionEntityRoleTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTransactionEntityRoleTypeDescription(UserVisitPK userVisitPK, GetTransactionEntityRoleTypeDescriptionForm form) {
        return new GetTransactionEntityRoleTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTransactionEntityRoleTypeDescriptions(UserVisitPK userVisitPK, GetTransactionEntityRoleTypeDescriptionsForm form) {
        return new GetTransactionEntityRoleTypeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTransactionEntityRoleTypeDescription(UserVisitPK userVisitPK, EditTransactionEntityRoleTypeDescriptionForm form) {
        return new EditTransactionEntityRoleTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTransactionEntityRoleTypeDescription(UserVisitPK userVisitPK, DeleteTransactionEntityRoleTypeDescriptionForm form) {
        return new DeleteTransactionEntityRoleTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Groups
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult getTransactionGroup(UserVisitPK userVisitPK, GetTransactionGroupForm form) {
        return new GetTransactionGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTransactionGroups(UserVisitPK userVisitPK, GetTransactionGroupsForm form) {
        return new GetTransactionGroupsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTransactionGroupStatusChoices(UserVisitPK userVisitPK, GetTransactionGroupStatusChoicesForm form) {
        return new GetTransactionGroupStatusChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setTransactionGroupStatus(UserVisitPK userVisitPK, SetTransactionGroupStatusForm form) {
        return new SetTransactionGroupStatusCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Transactions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult getTransactions(UserVisitPK userVisitPK, GetTransactionsForm form) {
        return new GetTransactionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTransaction(UserVisitPK userVisitPK, GetTransactionForm form) {
        return new GetTransactionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Symbol Positions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSymbolPosition(UserVisitPK userVisitPK, CreateSymbolPositionForm form) {
        return new CreateSymbolPositionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSymbolPositionChoices(UserVisitPK userVisitPK, GetSymbolPositionChoicesForm form) {
        return new GetSymbolPositionChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSymbolPosition(UserVisitPK userVisitPK, GetSymbolPositionForm form) {
        return new GetSymbolPositionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSymbolPositions(UserVisitPK userVisitPK, GetSymbolPositionsForm form) {
        return new GetSymbolPositionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultSymbolPosition(UserVisitPK userVisitPK, SetDefaultSymbolPositionForm form) {
        return new SetDefaultSymbolPositionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editSymbolPosition(UserVisitPK userVisitPK, EditSymbolPositionForm form) {
        return new EditSymbolPositionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteSymbolPosition(UserVisitPK userVisitPK, DeleteSymbolPositionForm form) {
        return new DeleteSymbolPositionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Symbol Position Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSymbolPositionDescription(UserVisitPK userVisitPK, CreateSymbolPositionDescriptionForm form) {
        return new CreateSymbolPositionDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSymbolPositionDescription(UserVisitPK userVisitPK, GetSymbolPositionDescriptionForm form) {
        return new GetSymbolPositionDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSymbolPositionDescriptions(UserVisitPK userVisitPK, GetSymbolPositionDescriptionsForm form) {
        return new GetSymbolPositionDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editSymbolPositionDescription(UserVisitPK userVisitPK, EditSymbolPositionDescriptionForm form) {
        return new EditSymbolPositionDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteSymbolPositionDescription(UserVisitPK userVisitPK, DeleteSymbolPositionDescriptionForm form) {
        return new DeleteSymbolPositionDescriptionCommand(userVisitPK, form).run();
    }
    
}

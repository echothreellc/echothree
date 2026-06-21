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

package com.echothree.control.user.accounting.server;

import com.echothree.control.user.accounting.common.AccountingRemote;
import com.echothree.control.user.accounting.common.form.*;
import com.echothree.control.user.accounting.common.result.*;
import com.echothree.control.user.accounting.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

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
    public CommandResult<?> createCurrency(UserVisitPK userVisitPK, CreateCurrencyForm form) {
        return CDI.current().select(CreateCurrencyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> setDefaultCurrency(UserVisitPK userVisitPK, SetDefaultCurrencyForm form) {
        return CDI.current().select(SetDefaultCurrencyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetCurrencyChoicesResult> getCurrencyChoices(UserVisitPK userVisitPK, GetCurrencyChoicesForm form) {
        return CDI.current().select(GetCurrencyChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetCurrenciesResult> getCurrencies(UserVisitPK userVisitPK, GetCurrenciesForm form) {
        return CDI.current().select(GetCurrenciesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetCurrencyResult> getCurrency(UserVisitPK userVisitPK, GetCurrencyForm form) {
        return CDI.current().select(GetCurrencyCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetPreferredCurrencyResult> getPreferredCurrency(UserVisitPK userVisitPK, GetPreferredCurrencyForm form) {
        return CDI.current().select(GetPreferredCurrencyCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Currency Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createCurrencyDescription(UserVisitPK userVisitPK, CreateCurrencyDescriptionForm form) {
        return CDI.current().select(CreateCurrencyDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetCurrencyDescriptionsResult> getCurrencyDescriptions(UserVisitPK userVisitPK, GetCurrencyDescriptionsForm form) {
        return CDI.current().select(GetCurrencyDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Accounting Categories
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createItemAccountingCategory(UserVisitPK userVisitPK, CreateItemAccountingCategoryForm form) {
        return CDI.current().select(CreateItemAccountingCategoryCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemAccountingCategoryChoicesResult> getItemAccountingCategoryChoices(UserVisitPK userVisitPK, GetItemAccountingCategoryChoicesForm form) {
        return CDI.current().select(GetItemAccountingCategoryChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemAccountingCategoryResult> getItemAccountingCategory(UserVisitPK userVisitPK, GetItemAccountingCategoryForm form) {
        return CDI.current().select(GetItemAccountingCategoryCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemAccountingCategoriesResult> getItemAccountingCategories(UserVisitPK userVisitPK, GetItemAccountingCategoriesForm form) {
        return CDI.current().select(GetItemAccountingCategoriesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> setDefaultItemAccountingCategory(UserVisitPK userVisitPK, SetDefaultItemAccountingCategoryForm form) {
        return CDI.current().select(SetDefaultItemAccountingCategoryCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditItemAccountingCategoryResult> editItemAccountingCategory(UserVisitPK userVisitPK, EditItemAccountingCategoryForm form) {
        return CDI.current().select(EditItemAccountingCategoryCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteItemAccountingCategory(UserVisitPK userVisitPK, DeleteItemAccountingCategoryForm form) {
        return CDI.current().select(DeleteItemAccountingCategoryCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Accounting Category Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createItemAccountingCategoryDescription(UserVisitPK userVisitPK, CreateItemAccountingCategoryDescriptionForm form) {
        return CDI.current().select(CreateItemAccountingCategoryDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemAccountingCategoryDescriptionResult> getItemAccountingCategoryDescription(UserVisitPK userVisitPK, GetItemAccountingCategoryDescriptionForm form) {
        return CDI.current().select(GetItemAccountingCategoryDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemAccountingCategoryDescriptionsResult> getItemAccountingCategoryDescriptions(UserVisitPK userVisitPK, GetItemAccountingCategoryDescriptionsForm form) {
        return CDI.current().select(GetItemAccountingCategoryDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditItemAccountingCategoryDescriptionResult> editItemAccountingCategoryDescription(UserVisitPK userVisitPK, EditItemAccountingCategoryDescriptionForm form) {
        return CDI.current().select(EditItemAccountingCategoryDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteItemAccountingCategoryDescription(UserVisitPK userVisitPK, DeleteItemAccountingCategoryDescriptionForm form) {
        return CDI.current().select(DeleteItemAccountingCategoryDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createGlAccountType(UserVisitPK userVisitPK, CreateGlAccountTypeForm form) {
        return CDI.current().select(CreateGlAccountTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetGlAccountTypeResult> getGlAccountType(UserVisitPK userVisitPK, GetGlAccountTypeForm form) {
        return CDI.current().select(GetGlAccountTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetGlAccountTypesResult> getGlAccountTypes(UserVisitPK userVisitPK, GetGlAccountTypesForm form) {
        return CDI.current().select(GetGlAccountTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetGlAccountTypeChoicesResult> getGlAccountTypeChoices(UserVisitPK userVisitPK, GetGlAccountTypeChoicesForm form) {
        return CDI.current().select(GetGlAccountTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createGlAccountTypeDescription(UserVisitPK userVisitPK, CreateGlAccountTypeDescriptionForm form) {
        return CDI.current().select(CreateGlAccountTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Classes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createGlAccountClass(UserVisitPK userVisitPK, CreateGlAccountClassForm form) {
        return CDI.current().select(CreateGlAccountClassCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetGlAccountClassChoicesResult> getGlAccountClassChoices(UserVisitPK userVisitPK, GetGlAccountClassChoicesForm form) {
        return CDI.current().select(GetGlAccountClassChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetGlAccountClassResult> getGlAccountClass(UserVisitPK userVisitPK, GetGlAccountClassForm form) {
        return CDI.current().select(GetGlAccountClassCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetGlAccountClassesResult> getGlAccountClasses(UserVisitPK userVisitPK, GetGlAccountClassesForm form) {
        return CDI.current().select(GetGlAccountClassesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> setDefaultGlAccountClass(UserVisitPK userVisitPK, SetDefaultGlAccountClassForm form) {
        return CDI.current().select(SetDefaultGlAccountClassCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditGlAccountClassResult> editGlAccountClass(UserVisitPK userVisitPK, EditGlAccountClassForm form) {
        return CDI.current().select(EditGlAccountClassCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteGlAccountClass(UserVisitPK userVisitPK, DeleteGlAccountClassForm form) {
        return CDI.current().select(DeleteGlAccountClassCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Class Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createGlAccountClassDescription(UserVisitPK userVisitPK, CreateGlAccountClassDescriptionForm form) {
        return CDI.current().select(CreateGlAccountClassDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetGlAccountClassDescriptionResult> getGlAccountClassDescription(UserVisitPK userVisitPK, GetGlAccountClassDescriptionForm form) {
        return CDI.current().select(GetGlAccountClassDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetGlAccountClassDescriptionsResult> getGlAccountClassDescriptions(UserVisitPK userVisitPK, GetGlAccountClassDescriptionsForm form) {
        return CDI.current().select(GetGlAccountClassDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditGlAccountClassDescriptionResult> editGlAccountClassDescription(UserVisitPK userVisitPK, EditGlAccountClassDescriptionForm form) {
        return CDI.current().select(EditGlAccountClassDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteGlAccountClassDescription(UserVisitPK userVisitPK, DeleteGlAccountClassDescriptionForm form) {
        return CDI.current().select(DeleteGlAccountClassDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Categories
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createGlAccountCategory(UserVisitPK userVisitPK, CreateGlAccountCategoryForm form) {
        return CDI.current().select(CreateGlAccountCategoryCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetGlAccountCategoryChoicesResult> getGlAccountCategoryChoices(UserVisitPK userVisitPK, GetGlAccountCategoryChoicesForm form) {
        return CDI.current().select(GetGlAccountCategoryChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetGlAccountCategoryResult> getGlAccountCategory(UserVisitPK userVisitPK, GetGlAccountCategoryForm form) {
        return CDI.current().select(GetGlAccountCategoryCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetGlAccountCategoriesResult> getGlAccountCategories(UserVisitPK userVisitPK, GetGlAccountCategoriesForm form) {
        return CDI.current().select(GetGlAccountCategoriesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> setDefaultGlAccountCategory(UserVisitPK userVisitPK, SetDefaultGlAccountCategoryForm form) {
        return CDI.current().select(SetDefaultGlAccountCategoryCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditGlAccountCategoryResult> editGlAccountCategory(UserVisitPK userVisitPK, EditGlAccountCategoryForm form) {
        return CDI.current().select(EditGlAccountCategoryCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteGlAccountCategory(UserVisitPK userVisitPK, DeleteGlAccountCategoryForm form) {
        return CDI.current().select(DeleteGlAccountCategoryCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Category Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createGlAccountCategoryDescription(UserVisitPK userVisitPK, CreateGlAccountCategoryDescriptionForm form) {
        return CDI.current().select(CreateGlAccountCategoryDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetGlAccountCategoryDescriptionResult> getGlAccountCategoryDescription(UserVisitPK userVisitPK, GetGlAccountCategoryDescriptionForm form) {
        return CDI.current().select(GetGlAccountCategoryDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetGlAccountCategoryDescriptionsResult> getGlAccountCategoryDescriptions(UserVisitPK userVisitPK, GetGlAccountCategoryDescriptionsForm form) {
        return CDI.current().select(GetGlAccountCategoryDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditGlAccountCategoryDescriptionResult> editGlAccountCategoryDescription(UserVisitPK userVisitPK, EditGlAccountCategoryDescriptionForm form) {
        return CDI.current().select(EditGlAccountCategoryDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteGlAccountCategoryDescription(UserVisitPK userVisitPK, DeleteGlAccountCategoryDescriptionForm form) {
        return CDI.current().select(DeleteGlAccountCategoryDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Resource Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createGlResourceType(UserVisitPK userVisitPK, CreateGlResourceTypeForm form) {
        return CDI.current().select(CreateGlResourceTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetGlResourceTypeChoicesResult> getGlResourceTypeChoices(UserVisitPK userVisitPK, GetGlResourceTypeChoicesForm form) {
        return CDI.current().select(GetGlResourceTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetGlResourceTypeResult> getGlResourceType(UserVisitPK userVisitPK, GetGlResourceTypeForm form) {
        return CDI.current().select(GetGlResourceTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetGlResourceTypesResult> getGlResourceTypes(UserVisitPK userVisitPK, GetGlResourceTypesForm form) {
        return CDI.current().select(GetGlResourceTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> setDefaultGlResourceType(UserVisitPK userVisitPK, SetDefaultGlResourceTypeForm form) {
        return CDI.current().select(SetDefaultGlResourceTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditGlResourceTypeResult> editGlResourceType(UserVisitPK userVisitPK, EditGlResourceTypeForm form) {
        return CDI.current().select(EditGlResourceTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteGlResourceType(UserVisitPK userVisitPK, DeleteGlResourceTypeForm form) {
        return CDI.current().select(DeleteGlResourceTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Resource Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createGlResourceTypeDescription(UserVisitPK userVisitPK, CreateGlResourceTypeDescriptionForm form) {
        return CDI.current().select(CreateGlResourceTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetGlResourceTypeDescriptionResult> getGlResourceTypeDescription(UserVisitPK userVisitPK, GetGlResourceTypeDescriptionForm form) {
        return CDI.current().select(GetGlResourceTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetGlResourceTypeDescriptionsResult> getGlResourceTypeDescriptions(UserVisitPK userVisitPK, GetGlResourceTypeDescriptionsForm form) {
        return CDI.current().select(GetGlResourceTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditGlResourceTypeDescriptionResult> editGlResourceTypeDescription(UserVisitPK userVisitPK, EditGlResourceTypeDescriptionForm form) {
        return CDI.current().select(EditGlResourceTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteGlResourceTypeDescription(UserVisitPK userVisitPK, DeleteGlResourceTypeDescriptionForm form) {
        return CDI.current().select(DeleteGlResourceTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Accounts
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createGlAccount(UserVisitPK userVisitPK, CreateGlAccountForm form) {
        return CDI.current().select(CreateGlAccountCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetGlAccountChoicesResult> getGlAccountChoices(UserVisitPK userVisitPK, GetGlAccountChoicesForm form) {
        return CDI.current().select(GetGlAccountChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetGlAccountResult> getGlAccount(UserVisitPK userVisitPK, GetGlAccountForm form) {
        return CDI.current().select(GetGlAccountCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetGlAccountsResult> getGlAccounts(UserVisitPK userVisitPK, GetGlAccountsForm form) {
        return CDI.current().select(GetGlAccountsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditGlAccountResult> editGlAccount(UserVisitPK userVisitPK, EditGlAccountForm form) {
        return CDI.current().select(EditGlAccountCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteGlAccount(UserVisitPK userVisitPK, DeleteGlAccountForm form) {
        return CDI.current().select(DeleteGlAccountCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createGlAccountDescription(UserVisitPK userVisitPK, CreateGlAccountDescriptionForm form) {
        return CDI.current().select(CreateGlAccountDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetGlAccountDescriptionResult> getGlAccountDescription(UserVisitPK userVisitPK, GetGlAccountDescriptionForm form) {
        return CDI.current().select(GetGlAccountDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetGlAccountDescriptionsResult> getGlAccountDescriptions(UserVisitPK userVisitPK, GetGlAccountDescriptionsForm form) {
        return CDI.current().select(GetGlAccountDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditGlAccountDescriptionResult> editGlAccountDescription(UserVisitPK userVisitPK, EditGlAccountDescriptionForm form) {
        return CDI.current().select(EditGlAccountDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteGlAccountDescription(UserVisitPK userVisitPK, DeleteGlAccountDescriptionForm form) {
        return CDI.current().select(DeleteGlAccountDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Transaction Time Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<?> createTransactionTimeType(UserVisitPK userVisitPK, CreateTransactionTimeTypeForm form) {
        return CDI.current().select(CreateTransactionTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetTransactionTimeTypeChoicesResult> getTransactionTimeTypeChoices(UserVisitPK userVisitPK, GetTransactionTimeTypeChoicesForm form) {
        return CDI.current().select(GetTransactionTimeTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetTransactionTimeTypeResult> getTransactionTimeType(UserVisitPK userVisitPK, GetTransactionTimeTypeForm form) {
        return CDI.current().select(GetTransactionTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetTransactionTimeTypesResult> getTransactionTimeTypes(UserVisitPK userVisitPK, GetTransactionTimeTypesForm form) {
        return CDI.current().select(GetTransactionTimeTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> setDefaultTransactionTimeType(UserVisitPK userVisitPK, SetDefaultTransactionTimeTypeForm form) {
        return CDI.current().select(SetDefaultTransactionTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditTransactionTimeTypeResult> editTransactionTimeType(UserVisitPK userVisitPK, EditTransactionTimeTypeForm form) {
        return CDI.current().select(EditTransactionTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteTransactionTimeType(UserVisitPK userVisitPK, DeleteTransactionTimeTypeForm form) {
        return CDI.current().select(DeleteTransactionTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Transaction Time Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<?> createTransactionTimeTypeDescription(UserVisitPK userVisitPK, CreateTransactionTimeTypeDescriptionForm form) {
        return CDI.current().select(CreateTransactionTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetTransactionTimeTypeDescriptionResult> getTransactionTimeTypeDescription(UserVisitPK userVisitPK, GetTransactionTimeTypeDescriptionForm form) {
        return CDI.current().select(GetTransactionTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetTransactionTimeTypeDescriptionsResult> getTransactionTimeTypeDescriptions(UserVisitPK userVisitPK, GetTransactionTimeTypeDescriptionsForm form) {
        return CDI.current().select(GetTransactionTimeTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditTransactionTimeTypeDescriptionResult> editTransactionTimeTypeDescription(UserVisitPK userVisitPK, EditTransactionTimeTypeDescriptionForm form) {
        return CDI.current().select(EditTransactionTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteTransactionTimeTypeDescription(UserVisitPK userVisitPK, DeleteTransactionTimeTypeDescriptionForm form) {
        return CDI.current().select(DeleteTransactionTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Transaction Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createTransactionType(UserVisitPK userVisitPK, CreateTransactionTypeForm form) {
        return CDI.current().select(CreateTransactionTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetTransactionTypeResult> getTransactionType(UserVisitPK userVisitPK, GetTransactionTypeForm form) {
        return CDI.current().select(GetTransactionTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetTransactionTypesResult> getTransactionTypes(UserVisitPK userVisitPK, GetTransactionTypesForm form) {
        return CDI.current().select(GetTransactionTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditTransactionTypeResult> editTransactionType(UserVisitPK userVisitPK, EditTransactionTypeForm form) {
        return CDI.current().select(EditTransactionTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteTransactionType(UserVisitPK userVisitPK, DeleteTransactionTypeForm form) {
        return CDI.current().select(DeleteTransactionTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createTransactionTypeDescription(UserVisitPK userVisitPK, CreateTransactionTypeDescriptionForm form) {
        return CDI.current().select(CreateTransactionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetTransactionTypeDescriptionResult> getTransactionTypeDescription(UserVisitPK userVisitPK, GetTransactionTypeDescriptionForm form) {
        return CDI.current().select(GetTransactionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetTransactionTypeDescriptionsResult> getTransactionTypeDescriptions(UserVisitPK userVisitPK, GetTransactionTypeDescriptionsForm form) {
        return CDI.current().select(GetTransactionTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditTransactionTypeDescriptionResult> editTransactionTypeDescription(UserVisitPK userVisitPK, EditTransactionTypeDescriptionForm form) {
        return CDI.current().select(EditTransactionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteTransactionTypeDescription(UserVisitPK userVisitPK, DeleteTransactionTypeDescriptionForm form) {
        return CDI.current().select(DeleteTransactionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Gl Account Categories
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createTransactionGlAccountCategory(UserVisitPK userVisitPK, CreateTransactionGlAccountCategoryForm form) {
        return CDI.current().select(CreateTransactionGlAccountCategoryCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetTransactionGlAccountCategoryResult> getTransactionGlAccountCategory(UserVisitPK userVisitPK, GetTransactionGlAccountCategoryForm form) {
        return CDI.current().select(GetTransactionGlAccountCategoryCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetTransactionGlAccountCategoriesResult> getTransactionGlAccountCategories(UserVisitPK userVisitPK, GetTransactionGlAccountCategoriesForm form) {
        return CDI.current().select(GetTransactionGlAccountCategoriesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditTransactionGlAccountCategoryResult> editTransactionGlAccountCategory(UserVisitPK userVisitPK, EditTransactionGlAccountCategoryForm form) {
        return CDI.current().select(EditTransactionGlAccountCategoryCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteTransactionGlAccountCategory(UserVisitPK userVisitPK, DeleteTransactionGlAccountCategoryForm form) {
        return CDI.current().select(DeleteTransactionGlAccountCategoryCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Gl Account Category Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createTransactionGlAccountCategoryDescription(UserVisitPK userVisitPK, CreateTransactionGlAccountCategoryDescriptionForm form) {
        return CDI.current().select(CreateTransactionGlAccountCategoryDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetTransactionGlAccountCategoryDescriptionResult> getTransactionGlAccountCategoryDescription(UserVisitPK userVisitPK, GetTransactionGlAccountCategoryDescriptionForm form) {
        return CDI.current().select(GetTransactionGlAccountCategoryDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetTransactionGlAccountCategoryDescriptionsResult> getTransactionGlAccountCategoryDescriptions(UserVisitPK userVisitPK, GetTransactionGlAccountCategoryDescriptionsForm form) {
        return CDI.current().select(GetTransactionGlAccountCategoryDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditTransactionGlAccountCategoryDescriptionResult> editTransactionGlAccountCategoryDescription(UserVisitPK userVisitPK, EditTransactionGlAccountCategoryDescriptionForm form) {
        return CDI.current().select(EditTransactionGlAccountCategoryDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteTransactionGlAccountCategoryDescription(UserVisitPK userVisitPK, DeleteTransactionGlAccountCategoryDescriptionForm form) {
        return CDI.current().select(DeleteTransactionGlAccountCategoryDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Entity Role Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createTransactionEntityRoleType(UserVisitPK userVisitPK, CreateTransactionEntityRoleTypeForm form) {
        return CDI.current().select(CreateTransactionEntityRoleTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetTransactionEntityRoleTypeResult> getTransactionEntityRoleType(UserVisitPK userVisitPK, GetTransactionEntityRoleTypeForm form) {
        return CDI.current().select(GetTransactionEntityRoleTypeCommand.class).get().run(userVisitPK, form);
    }
    @Override
    public CommandResult<GetTransactionEntityRoleTypesResult> getTransactionEntityRoleTypes(UserVisitPK userVisitPK, GetTransactionEntityRoleTypesForm form) {
        return CDI.current().select(GetTransactionEntityRoleTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditTransactionEntityRoleTypeResult> editTransactionEntityRoleType(UserVisitPK userVisitPK, EditTransactionEntityRoleTypeForm form) {
        return CDI.current().select(EditTransactionEntityRoleTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteTransactionEntityRoleType(UserVisitPK userVisitPK, DeleteTransactionEntityRoleTypeForm form) {
        return CDI.current().select(DeleteTransactionEntityRoleTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Entity Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createTransactionEntityRoleTypeDescription(UserVisitPK userVisitPK, CreateTransactionEntityRoleTypeDescriptionForm form) {
        return CDI.current().select(CreateTransactionEntityRoleTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetTransactionEntityRoleTypeDescriptionResult> getTransactionEntityRoleTypeDescription(UserVisitPK userVisitPK, GetTransactionEntityRoleTypeDescriptionForm form) {
        return CDI.current().select(GetTransactionEntityRoleTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetTransactionEntityRoleTypeDescriptionsResult> getTransactionEntityRoleTypeDescriptions(UserVisitPK userVisitPK, GetTransactionEntityRoleTypeDescriptionsForm form) {
        return CDI.current().select(GetTransactionEntityRoleTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditTransactionEntityRoleTypeDescriptionResult> editTransactionEntityRoleTypeDescription(UserVisitPK userVisitPK, EditTransactionEntityRoleTypeDescriptionForm form) {
        return CDI.current().select(EditTransactionEntityRoleTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteTransactionEntityRoleTypeDescription(UserVisitPK userVisitPK, DeleteTransactionEntityRoleTypeDescriptionForm form) {
        return CDI.current().select(DeleteTransactionEntityRoleTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Groups
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<GetTransactionGroupResult> getTransactionGroup(UserVisitPK userVisitPK, GetTransactionGroupForm form) {
        return CDI.current().select(GetTransactionGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetTransactionGroupsResult> getTransactionGroups(UserVisitPK userVisitPK, GetTransactionGroupsForm form) {
        return CDI.current().select(GetTransactionGroupsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetTransactionGroupStatusChoicesResult> getTransactionGroupStatusChoices(UserVisitPK userVisitPK, GetTransactionGroupStatusChoicesForm form) {
        return CDI.current().select(GetTransactionGroupStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> setTransactionGroupStatus(UserVisitPK userVisitPK, SetTransactionGroupStatusForm form) {
        return CDI.current().select(SetTransactionGroupStatusCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Transactions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<GetTransactionsResult> getTransactions(UserVisitPK userVisitPK, GetTransactionsForm form) {
        return CDI.current().select(GetTransactionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetTransactionResult> getTransaction(UserVisitPK userVisitPK, GetTransactionForm form) {
        return CDI.current().select(GetTransactionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Symbol Positions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createSymbolPosition(UserVisitPK userVisitPK, CreateSymbolPositionForm form) {
        return CDI.current().select(CreateSymbolPositionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetSymbolPositionChoicesResult> getSymbolPositionChoices(UserVisitPK userVisitPK, GetSymbolPositionChoicesForm form) {
        return CDI.current().select(GetSymbolPositionChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetSymbolPositionResult> getSymbolPosition(UserVisitPK userVisitPK, GetSymbolPositionForm form) {
        return CDI.current().select(GetSymbolPositionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetSymbolPositionsResult> getSymbolPositions(UserVisitPK userVisitPK, GetSymbolPositionsForm form) {
        return CDI.current().select(GetSymbolPositionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> setDefaultSymbolPosition(UserVisitPK userVisitPK, SetDefaultSymbolPositionForm form) {
        return CDI.current().select(SetDefaultSymbolPositionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditSymbolPositionResult> editSymbolPosition(UserVisitPK userVisitPK, EditSymbolPositionForm form) {
        return CDI.current().select(EditSymbolPositionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteSymbolPosition(UserVisitPK userVisitPK, DeleteSymbolPositionForm form) {
        return CDI.current().select(DeleteSymbolPositionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Symbol Position Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createSymbolPositionDescription(UserVisitPK userVisitPK, CreateSymbolPositionDescriptionForm form) {
        return CDI.current().select(CreateSymbolPositionDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetSymbolPositionDescriptionResult> getSymbolPositionDescription(UserVisitPK userVisitPK, GetSymbolPositionDescriptionForm form) {
        return CDI.current().select(GetSymbolPositionDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetSymbolPositionDescriptionsResult> getSymbolPositionDescriptions(UserVisitPK userVisitPK, GetSymbolPositionDescriptionsForm form) {
        return CDI.current().select(GetSymbolPositionDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditSymbolPositionDescriptionResult> editSymbolPositionDescription(UserVisitPK userVisitPK, EditSymbolPositionDescriptionForm form) {
        return CDI.current().select(EditSymbolPositionDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteSymbolPositionDescription(UserVisitPK userVisitPK, DeleteSymbolPositionDescriptionForm form) {
        return CDI.current().select(DeleteSymbolPositionDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
}

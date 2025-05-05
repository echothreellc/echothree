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

package com.echothree.control.user.accounting.server;

import com.echothree.control.user.accounting.common.AccountingRemote;
import com.echothree.control.user.accounting.common.form.*;
import com.echothree.control.user.accounting.server.command.*;
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
import com.echothree.control.user.order.server.command.CreateOrderTimeTypeCommand;
import com.echothree.control.user.order.server.command.CreateOrderTimeTypeDescriptionCommand;
import com.echothree.control.user.order.server.command.DeleteOrderTimeTypeCommand;
import com.echothree.control.user.order.server.command.DeleteOrderTimeTypeDescriptionCommand;
import com.echothree.control.user.order.server.command.EditOrderTimeTypeCommand;
import com.echothree.control.user.order.server.command.EditOrderTimeTypeDescriptionCommand;
import com.echothree.control.user.order.server.command.GetOrderTimeTypeChoicesCommand;
import com.echothree.control.user.order.server.command.GetOrderTimeTypeCommand;
import com.echothree.control.user.order.server.command.GetOrderTimeTypeDescriptionCommand;
import com.echothree.control.user.order.server.command.GetOrderTimeTypeDescriptionsCommand;
import com.echothree.control.user.order.server.command.GetOrderTimeTypesCommand;
import com.echothree.control.user.order.server.command.SetDefaultOrderTimeTypeCommand;
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
        return new CreateCurrencyCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCurrency(UserVisitPK userVisitPK, SetDefaultCurrencyForm form) {
        return new SetDefaultCurrencyCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCurrencyChoices(UserVisitPK userVisitPK, GetCurrencyChoicesForm form) {
        return new GetCurrencyChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCurrencies(UserVisitPK userVisitPK, GetCurrenciesForm form) {
        return new GetCurrenciesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCurrency(UserVisitPK userVisitPK, GetCurrencyForm form) {
        return new GetCurrencyCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPreferredCurrency(UserVisitPK userVisitPK, GetPreferredCurrencyForm form) {
        return new GetPreferredCurrencyCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Currency Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCurrencyDescription(UserVisitPK userVisitPK, CreateCurrencyDescriptionForm form) {
        return new CreateCurrencyDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCurrencyDescriptions(UserVisitPK userVisitPK, GetCurrencyDescriptionsForm form) {
        return new GetCurrencyDescriptionsCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Accounting Categories
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemAccountingCategory(UserVisitPK userVisitPK, CreateItemAccountingCategoryForm form) {
        return new CreateItemAccountingCategoryCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemAccountingCategoryChoices(UserVisitPK userVisitPK, GetItemAccountingCategoryChoicesForm form) {
        return new GetItemAccountingCategoryChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemAccountingCategory(UserVisitPK userVisitPK, GetItemAccountingCategoryForm form) {
        return new GetItemAccountingCategoryCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemAccountingCategories(UserVisitPK userVisitPK, GetItemAccountingCategoriesForm form) {
        return new GetItemAccountingCategoriesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultItemAccountingCategory(UserVisitPK userVisitPK, SetDefaultItemAccountingCategoryForm form) {
        return new SetDefaultItemAccountingCategoryCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editItemAccountingCategory(UserVisitPK userVisitPK, EditItemAccountingCategoryForm form) {
        return new EditItemAccountingCategoryCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteItemAccountingCategory(UserVisitPK userVisitPK, DeleteItemAccountingCategoryForm form) {
        return new DeleteItemAccountingCategoryCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Accounting Category Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemAccountingCategoryDescription(UserVisitPK userVisitPK, CreateItemAccountingCategoryDescriptionForm form) {
        return new CreateItemAccountingCategoryDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemAccountingCategoryDescription(UserVisitPK userVisitPK, GetItemAccountingCategoryDescriptionForm form) {
        return new GetItemAccountingCategoryDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemAccountingCategoryDescriptions(UserVisitPK userVisitPK, GetItemAccountingCategoryDescriptionsForm form) {
        return new GetItemAccountingCategoryDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editItemAccountingCategoryDescription(UserVisitPK userVisitPK, EditItemAccountingCategoryDescriptionForm form) {
        return new EditItemAccountingCategoryDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteItemAccountingCategoryDescription(UserVisitPK userVisitPK, DeleteItemAccountingCategoryDescriptionForm form) {
        return new DeleteItemAccountingCategoryDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createGlAccountType(UserVisitPK userVisitPK, CreateGlAccountTypeForm form) {
        return new CreateGlAccountTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGlAccountType(UserVisitPK userVisitPK, GetGlAccountTypeForm form) {
        return new GetGlAccountTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGlAccountTypes(UserVisitPK userVisitPK, GetGlAccountTypesForm form) {
        return new GetGlAccountTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGlAccountTypeChoices(UserVisitPK userVisitPK, GetGlAccountTypeChoicesForm form) {
        return new GetGlAccountTypeChoicesCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createGlAccountTypeDescription(UserVisitPK userVisitPK, CreateGlAccountTypeDescriptionForm form) {
        return new CreateGlAccountTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Classes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createGlAccountClass(UserVisitPK userVisitPK, CreateGlAccountClassForm form) {
        return new CreateGlAccountClassCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGlAccountClassChoices(UserVisitPK userVisitPK, GetGlAccountClassChoicesForm form) {
        return new GetGlAccountClassChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGlAccountClass(UserVisitPK userVisitPK, GetGlAccountClassForm form) {
        return new GetGlAccountClassCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGlAccountClasses(UserVisitPK userVisitPK, GetGlAccountClassesForm form) {
        return new GetGlAccountClassesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultGlAccountClass(UserVisitPK userVisitPK, SetDefaultGlAccountClassForm form) {
        return new SetDefaultGlAccountClassCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editGlAccountClass(UserVisitPK userVisitPK, EditGlAccountClassForm form) {
        return new EditGlAccountClassCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteGlAccountClass(UserVisitPK userVisitPK, DeleteGlAccountClassForm form) {
        return new DeleteGlAccountClassCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Class Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createGlAccountClassDescription(UserVisitPK userVisitPK, CreateGlAccountClassDescriptionForm form) {
        return new CreateGlAccountClassDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGlAccountClassDescription(UserVisitPK userVisitPK, GetGlAccountClassDescriptionForm form) {
        return new GetGlAccountClassDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGlAccountClassDescriptions(UserVisitPK userVisitPK, GetGlAccountClassDescriptionsForm form) {
        return new GetGlAccountClassDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editGlAccountClassDescription(UserVisitPK userVisitPK, EditGlAccountClassDescriptionForm form) {
        return new EditGlAccountClassDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteGlAccountClassDescription(UserVisitPK userVisitPK, DeleteGlAccountClassDescriptionForm form) {
        return new DeleteGlAccountClassDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Categories
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createGlAccountCategory(UserVisitPK userVisitPK, CreateGlAccountCategoryForm form) {
        return new CreateGlAccountCategoryCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGlAccountCategoryChoices(UserVisitPK userVisitPK, GetGlAccountCategoryChoicesForm form) {
        return new GetGlAccountCategoryChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGlAccountCategory(UserVisitPK userVisitPK, GetGlAccountCategoryForm form) {
        return new GetGlAccountCategoryCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGlAccountCategories(UserVisitPK userVisitPK, GetGlAccountCategoriesForm form) {
        return new GetGlAccountCategoriesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultGlAccountCategory(UserVisitPK userVisitPK, SetDefaultGlAccountCategoryForm form) {
        return new SetDefaultGlAccountCategoryCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editGlAccountCategory(UserVisitPK userVisitPK, EditGlAccountCategoryForm form) {
        return new EditGlAccountCategoryCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteGlAccountCategory(UserVisitPK userVisitPK, DeleteGlAccountCategoryForm form) {
        return new DeleteGlAccountCategoryCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Category Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createGlAccountCategoryDescription(UserVisitPK userVisitPK, CreateGlAccountCategoryDescriptionForm form) {
        return new CreateGlAccountCategoryDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGlAccountCategoryDescription(UserVisitPK userVisitPK, GetGlAccountCategoryDescriptionForm form) {
        return new GetGlAccountCategoryDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGlAccountCategoryDescriptions(UserVisitPK userVisitPK, GetGlAccountCategoryDescriptionsForm form) {
        return new GetGlAccountCategoryDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editGlAccountCategoryDescription(UserVisitPK userVisitPK, EditGlAccountCategoryDescriptionForm form) {
        return new EditGlAccountCategoryDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteGlAccountCategoryDescription(UserVisitPK userVisitPK, DeleteGlAccountCategoryDescriptionForm form) {
        return new DeleteGlAccountCategoryDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Resource Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createGlResourceType(UserVisitPK userVisitPK, CreateGlResourceTypeForm form) {
        return new CreateGlResourceTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGlResourceTypeChoices(UserVisitPK userVisitPK, GetGlResourceTypeChoicesForm form) {
        return new GetGlResourceTypeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGlResourceType(UserVisitPK userVisitPK, GetGlResourceTypeForm form) {
        return new GetGlResourceTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGlResourceTypes(UserVisitPK userVisitPK, GetGlResourceTypesForm form) {
        return new GetGlResourceTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultGlResourceType(UserVisitPK userVisitPK, SetDefaultGlResourceTypeForm form) {
        return new SetDefaultGlResourceTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editGlResourceType(UserVisitPK userVisitPK, EditGlResourceTypeForm form) {
        return new EditGlResourceTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteGlResourceType(UserVisitPK userVisitPK, DeleteGlResourceTypeForm form) {
        return new DeleteGlResourceTypeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Resource Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createGlResourceTypeDescription(UserVisitPK userVisitPK, CreateGlResourceTypeDescriptionForm form) {
        return new CreateGlResourceTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGlResourceTypeDescription(UserVisitPK userVisitPK, GetGlResourceTypeDescriptionForm form) {
        return new GetGlResourceTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGlResourceTypeDescriptions(UserVisitPK userVisitPK, GetGlResourceTypeDescriptionsForm form) {
        return new GetGlResourceTypeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editGlResourceTypeDescription(UserVisitPK userVisitPK, EditGlResourceTypeDescriptionForm form) {
        return new EditGlResourceTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteGlResourceTypeDescription(UserVisitPK userVisitPK, DeleteGlResourceTypeDescriptionForm form) {
        return new DeleteGlResourceTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Accounts
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createGlAccount(UserVisitPK userVisitPK, CreateGlAccountForm form) {
        return new CreateGlAccountCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGlAccountChoices(UserVisitPK userVisitPK, GetGlAccountChoicesForm form) {
        return new GetGlAccountChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGlAccount(UserVisitPK userVisitPK, GetGlAccountForm form) {
        return new GetGlAccountCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGlAccounts(UserVisitPK userVisitPK, GetGlAccountsForm form) {
        return new GetGlAccountsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editGlAccount(UserVisitPK userVisitPK, EditGlAccountForm form) {
        return new EditGlAccountCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteGlAccount(UserVisitPK userVisitPK, DeleteGlAccountForm form) {
        return new DeleteGlAccountCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createGlAccountDescription(UserVisitPK userVisitPK, CreateGlAccountDescriptionForm form) {
        return new CreateGlAccountDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGlAccountDescription(UserVisitPK userVisitPK, GetGlAccountDescriptionForm form) {
        return new GetGlAccountDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGlAccountDescriptions(UserVisitPK userVisitPK, GetGlAccountDescriptionsForm form) {
        return new GetGlAccountDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editGlAccountDescription(UserVisitPK userVisitPK, EditGlAccountDescriptionForm form) {
        return new EditGlAccountDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteGlAccountDescription(UserVisitPK userVisitPK, DeleteGlAccountDescriptionForm form) {
        return new DeleteGlAccountDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Transaction Time Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createTransactionTimeType(UserVisitPK userVisitPK, CreateTransactionTimeTypeForm form) {
        return new CreateTransactionTimeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getTransactionTimeTypeChoices(UserVisitPK userVisitPK, GetTransactionTimeTypeChoicesForm form) {
        return new GetTransactionTimeTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getTransactionTimeType(UserVisitPK userVisitPK, GetTransactionTimeTypeForm form) {
        return new GetTransactionTimeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getTransactionTimeTypes(UserVisitPK userVisitPK, GetTransactionTimeTypesForm form) {
        return new GetTransactionTimeTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultTransactionTimeType(UserVisitPK userVisitPK, SetDefaultTransactionTimeTypeForm form) {
        return new SetDefaultTransactionTimeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editTransactionTimeType(UserVisitPK userVisitPK, EditTransactionTimeTypeForm form) {
        return new EditTransactionTimeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteTransactionTimeType(UserVisitPK userVisitPK, DeleteTransactionTimeTypeForm form) {
        return new DeleteTransactionTimeTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Transaction Time Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createTransactionTimeTypeDescription(UserVisitPK userVisitPK, CreateTransactionTimeTypeDescriptionForm form) {
        return new CreateTransactionTimeTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getTransactionTimeTypeDescription(UserVisitPK userVisitPK, GetTransactionTimeTypeDescriptionForm form) {
        return new GetTransactionTimeTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getTransactionTimeTypeDescriptions(UserVisitPK userVisitPK, GetTransactionTimeTypeDescriptionsForm form) {
        return new GetTransactionTimeTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editTransactionTimeTypeDescription(UserVisitPK userVisitPK, EditTransactionTimeTypeDescriptionForm form) {
        return new EditTransactionTimeTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteTransactionTimeTypeDescription(UserVisitPK userVisitPK, DeleteTransactionTimeTypeDescriptionForm form) {
        return new DeleteTransactionTimeTypeDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Transaction Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTransactionType(UserVisitPK userVisitPK, CreateTransactionTypeForm form) {
        return new CreateTransactionTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTransactionType(UserVisitPK userVisitPK, GetTransactionTypeForm form) {
        return new GetTransactionTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTransactionTypes(UserVisitPK userVisitPK, GetTransactionTypesForm form) {
        return new GetTransactionTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTransactionType(UserVisitPK userVisitPK, EditTransactionTypeForm form) {
        return new EditTransactionTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTransactionType(UserVisitPK userVisitPK, DeleteTransactionTypeForm form) {
        return new DeleteTransactionTypeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTransactionTypeDescription(UserVisitPK userVisitPK, CreateTransactionTypeDescriptionForm form) {
        return new CreateTransactionTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTransactionTypeDescription(UserVisitPK userVisitPK, GetTransactionTypeDescriptionForm form) {
        return new GetTransactionTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTransactionTypeDescriptions(UserVisitPK userVisitPK, GetTransactionTypeDescriptionsForm form) {
        return new GetTransactionTypeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTransactionTypeDescription(UserVisitPK userVisitPK, EditTransactionTypeDescriptionForm form) {
        return new EditTransactionTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTransactionTypeDescription(UserVisitPK userVisitPK, DeleteTransactionTypeDescriptionForm form) {
        return new DeleteTransactionTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Gl Account Categories
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTransactionGlAccountCategory(UserVisitPK userVisitPK, CreateTransactionGlAccountCategoryForm form) {
        return new CreateTransactionGlAccountCategoryCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTransactionGlAccountCategory(UserVisitPK userVisitPK, GetTransactionGlAccountCategoryForm form) {
        return new GetTransactionGlAccountCategoryCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTransactionGlAccountCategories(UserVisitPK userVisitPK, GetTransactionGlAccountCategoriesForm form) {
        return new GetTransactionGlAccountCategoriesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTransactionGlAccountCategory(UserVisitPK userVisitPK, EditTransactionGlAccountCategoryForm form) {
        return new EditTransactionGlAccountCategoryCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTransactionGlAccountCategory(UserVisitPK userVisitPK, DeleteTransactionGlAccountCategoryForm form) {
        return new DeleteTransactionGlAccountCategoryCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Gl Account Category Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTransactionGlAccountCategoryDescription(UserVisitPK userVisitPK, CreateTransactionGlAccountCategoryDescriptionForm form) {
        return new CreateTransactionGlAccountCategoryDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTransactionGlAccountCategoryDescription(UserVisitPK userVisitPK, GetTransactionGlAccountCategoryDescriptionForm form) {
        return new GetTransactionGlAccountCategoryDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTransactionGlAccountCategoryDescriptions(UserVisitPK userVisitPK, GetTransactionGlAccountCategoryDescriptionsForm form) {
        return new GetTransactionGlAccountCategoryDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTransactionGlAccountCategoryDescription(UserVisitPK userVisitPK, EditTransactionGlAccountCategoryDescriptionForm form) {
        return new EditTransactionGlAccountCategoryDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTransactionGlAccountCategoryDescription(UserVisitPK userVisitPK, DeleteTransactionGlAccountCategoryDescriptionForm form) {
        return new DeleteTransactionGlAccountCategoryDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Entity Role Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTransactionEntityRoleType(UserVisitPK userVisitPK, CreateTransactionEntityRoleTypeForm form) {
        return new CreateTransactionEntityRoleTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTransactionEntityRoleType(UserVisitPK userVisitPK, GetTransactionEntityRoleTypeForm form) {
        return new GetTransactionEntityRoleTypeCommand().run(userVisitPK, form);
    }
    @Override
    public CommandResult getTransactionEntityRoleTypes(UserVisitPK userVisitPK, GetTransactionEntityRoleTypesForm form) {
        return new GetTransactionEntityRoleTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTransactionEntityRoleType(UserVisitPK userVisitPK, EditTransactionEntityRoleTypeForm form) {
        return new EditTransactionEntityRoleTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTransactionEntityRoleType(UserVisitPK userVisitPK, DeleteTransactionEntityRoleTypeForm form) {
        return new DeleteTransactionEntityRoleTypeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Entity Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTransactionEntityRoleTypeDescription(UserVisitPK userVisitPK, CreateTransactionEntityRoleTypeDescriptionForm form) {
        return new CreateTransactionEntityRoleTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTransactionEntityRoleTypeDescription(UserVisitPK userVisitPK, GetTransactionEntityRoleTypeDescriptionForm form) {
        return new GetTransactionEntityRoleTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTransactionEntityRoleTypeDescriptions(UserVisitPK userVisitPK, GetTransactionEntityRoleTypeDescriptionsForm form) {
        return new GetTransactionEntityRoleTypeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTransactionEntityRoleTypeDescription(UserVisitPK userVisitPK, EditTransactionEntityRoleTypeDescriptionForm form) {
        return new EditTransactionEntityRoleTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTransactionEntityRoleTypeDescription(UserVisitPK userVisitPK, DeleteTransactionEntityRoleTypeDescriptionForm form) {
        return new DeleteTransactionEntityRoleTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Groups
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult getTransactionGroup(UserVisitPK userVisitPK, GetTransactionGroupForm form) {
        return new GetTransactionGroupCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTransactionGroups(UserVisitPK userVisitPK, GetTransactionGroupsForm form) {
        return new GetTransactionGroupsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTransactionGroupStatusChoices(UserVisitPK userVisitPK, GetTransactionGroupStatusChoicesForm form) {
        return new GetTransactionGroupStatusChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setTransactionGroupStatus(UserVisitPK userVisitPK, SetTransactionGroupStatusForm form) {
        return new SetTransactionGroupStatusCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Transactions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult getTransactions(UserVisitPK userVisitPK, GetTransactionsForm form) {
        return new GetTransactionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTransaction(UserVisitPK userVisitPK, GetTransactionForm form) {
        return new GetTransactionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Symbol Positions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSymbolPosition(UserVisitPK userVisitPK, CreateSymbolPositionForm form) {
        return new CreateSymbolPositionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSymbolPositionChoices(UserVisitPK userVisitPK, GetSymbolPositionChoicesForm form) {
        return new GetSymbolPositionChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSymbolPosition(UserVisitPK userVisitPK, GetSymbolPositionForm form) {
        return new GetSymbolPositionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSymbolPositions(UserVisitPK userVisitPK, GetSymbolPositionsForm form) {
        return new GetSymbolPositionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultSymbolPosition(UserVisitPK userVisitPK, SetDefaultSymbolPositionForm form) {
        return new SetDefaultSymbolPositionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSymbolPosition(UserVisitPK userVisitPK, EditSymbolPositionForm form) {
        return new EditSymbolPositionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSymbolPosition(UserVisitPK userVisitPK, DeleteSymbolPositionForm form) {
        return new DeleteSymbolPositionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Symbol Position Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSymbolPositionDescription(UserVisitPK userVisitPK, CreateSymbolPositionDescriptionForm form) {
        return new CreateSymbolPositionDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSymbolPositionDescription(UserVisitPK userVisitPK, GetSymbolPositionDescriptionForm form) {
        return new GetSymbolPositionDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSymbolPositionDescriptions(UserVisitPK userVisitPK, GetSymbolPositionDescriptionsForm form) {
        return new GetSymbolPositionDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSymbolPositionDescription(UserVisitPK userVisitPK, EditSymbolPositionDescriptionForm form) {
        return new EditSymbolPositionDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSymbolPositionDescription(UserVisitPK userVisitPK, DeleteSymbolPositionDescriptionForm form) {
        return new DeleteSymbolPositionDescriptionCommand().run(userVisitPK, form);
    }
    
}

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

package com.echothree.control.user.vendor.server;

import com.echothree.control.user.vendor.common.VendorRemote;
import com.echothree.control.user.vendor.common.form.*;
import com.echothree.control.user.vendor.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class VendorBean
        extends VendorFormsImpl
        implements VendorRemote, VendorLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "VendorBean is alive!";
    }
    
    // --------------------------------------------------------------------------------
    //   Vendor Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createVendorType(UserVisitPK userVisitPK, CreateVendorTypeForm form) {
        return new CreateVendorTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getVendorTypeChoices(UserVisitPK userVisitPK, GetVendorTypeChoicesForm form) {
        return new GetVendorTypeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getVendorType(UserVisitPK userVisitPK, GetVendorTypeForm form) {
        return new GetVendorTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getVendorTypes(UserVisitPK userVisitPK, GetVendorTypesForm form) {
        return new GetVendorTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultVendorType(UserVisitPK userVisitPK, SetDefaultVendorTypeForm form) {
        return new SetDefaultVendorTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editVendorType(UserVisitPK userVisitPK, EditVendorTypeForm form) {
        return new EditVendorTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteVendorType(UserVisitPK userVisitPK, DeleteVendorTypeForm form) {
        return new DeleteVendorTypeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Vendor Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createVendorTypeDescription(UserVisitPK userVisitPK, CreateVendorTypeDescriptionForm form) {
        return new CreateVendorTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getVendorTypeDescription(UserVisitPK userVisitPK, GetVendorTypeDescriptionForm form) {
        return new GetVendorTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getVendorTypeDescriptions(UserVisitPK userVisitPK, GetVendorTypeDescriptionsForm form) {
        return new GetVendorTypeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editVendorTypeDescription(UserVisitPK userVisitPK, EditVendorTypeDescriptionForm form) {
        return new EditVendorTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteVendorTypeDescription(UserVisitPK userVisitPK, DeleteVendorTypeDescriptionForm form) {
        return new DeleteVendorTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Vendors
    // -------------------------------------------------------------------------

    @Override
    public CommandResult getVendor(UserVisitPK userVisitPK, GetVendorForm form) {
        return new GetVendorCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getVendors(UserVisitPK userVisitPK, GetVendorsForm form) {
        return new GetVendorsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editVendor(UserVisitPK userVisitPK, EditVendorForm form) {
        return new EditVendorCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Vendor Items
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createVendorItem(UserVisitPK userVisitPK, CreateVendorItemForm form) {
        return new CreateVendorItemCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getVendorItem(UserVisitPK userVisitPK, GetVendorItemForm form) {
        return new GetVendorItemCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getVendorItems(UserVisitPK userVisitPK, GetVendorItemsForm form) {
        return new GetVendorItemsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getVendorItemStatusChoices(UserVisitPK userVisitPK, GetVendorItemStatusChoicesForm form) {
        return new GetVendorItemStatusChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setVendorItemStatus(UserVisitPK userVisitPK, SetVendorItemStatusForm form) {
        return new SetVendorItemStatusCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editVendorItem(UserVisitPK userVisitPK, EditVendorItemForm form) {
        return new EditVendorItemCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteVendorItem(UserVisitPK userVisitPK, DeleteVendorItemForm form) {
        return new DeleteVendorItemCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Vendor Item Costs
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createVendorItemCost(UserVisitPK userVisitPK, CreateVendorItemCostForm form) {
        return new CreateVendorItemCostCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getVendorItemCost(UserVisitPK userVisitPK, GetVendorItemCostForm form) {
        return new GetVendorItemCostCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getVendorItemCosts(UserVisitPK userVisitPK, GetVendorItemCostsForm form) {
        return new GetVendorItemCostsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editVendorItemCost(UserVisitPK userVisitPK, EditVendorItemCostForm form) {
        return new EditVendorItemCostCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteVendorItemCost(UserVisitPK userVisitPK, DeleteVendorItemCostForm form) {
        return new DeleteVendorItemCostCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Purchasing Categories
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemPurchasingCategory(UserVisitPK userVisitPK, CreateItemPurchasingCategoryForm form) {
        return new CreateItemPurchasingCategoryCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemPurchasingCategoryChoices(UserVisitPK userVisitPK, GetItemPurchasingCategoryChoicesForm form) {
        return new GetItemPurchasingCategoryChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemPurchasingCategory(UserVisitPK userVisitPK, GetItemPurchasingCategoryForm form) {
        return new GetItemPurchasingCategoryCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemPurchasingCategories(UserVisitPK userVisitPK, GetItemPurchasingCategoriesForm form) {
        return new GetItemPurchasingCategoriesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultItemPurchasingCategory(UserVisitPK userVisitPK, SetDefaultItemPurchasingCategoryForm form) {
        return new SetDefaultItemPurchasingCategoryCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editItemPurchasingCategory(UserVisitPK userVisitPK, EditItemPurchasingCategoryForm form) {
        return new EditItemPurchasingCategoryCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteItemPurchasingCategory(UserVisitPK userVisitPK, DeleteItemPurchasingCategoryForm form) {
        return new DeleteItemPurchasingCategoryCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Vendor Category Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemPurchasingCategoryDescription(UserVisitPK userVisitPK, CreateItemPurchasingCategoryDescriptionForm form) {
        return new CreateItemPurchasingCategoryDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemPurchasingCategoryDescription(UserVisitPK userVisitPK, GetItemPurchasingCategoryDescriptionForm form) {
        return new GetItemPurchasingCategoryDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemPurchasingCategoryDescriptions(UserVisitPK userVisitPK, GetItemPurchasingCategoryDescriptionsForm form) {
        return new GetItemPurchasingCategoryDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editItemPurchasingCategoryDescription(UserVisitPK userVisitPK, EditItemPurchasingCategoryDescriptionForm form) {
        return new EditItemPurchasingCategoryDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteItemPurchasingCategoryDescription(UserVisitPK userVisitPK, DeleteItemPurchasingCategoryDescriptionForm form) {
        return new DeleteItemPurchasingCategoryDescriptionCommand(userVisitPK, form).run();
    }
    
}

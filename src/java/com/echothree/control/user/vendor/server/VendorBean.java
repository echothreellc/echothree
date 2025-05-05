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
        return new CreateVendorTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getVendorTypeChoices(UserVisitPK userVisitPK, GetVendorTypeChoicesForm form) {
        return new GetVendorTypeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getVendorType(UserVisitPK userVisitPK, GetVendorTypeForm form) {
        return new GetVendorTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getVendorTypes(UserVisitPK userVisitPK, GetVendorTypesForm form) {
        return new GetVendorTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultVendorType(UserVisitPK userVisitPK, SetDefaultVendorTypeForm form) {
        return new SetDefaultVendorTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editVendorType(UserVisitPK userVisitPK, EditVendorTypeForm form) {
        return new EditVendorTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteVendorType(UserVisitPK userVisitPK, DeleteVendorTypeForm form) {
        return new DeleteVendorTypeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Vendor Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createVendorTypeDescription(UserVisitPK userVisitPK, CreateVendorTypeDescriptionForm form) {
        return new CreateVendorTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getVendorTypeDescription(UserVisitPK userVisitPK, GetVendorTypeDescriptionForm form) {
        return new GetVendorTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getVendorTypeDescriptions(UserVisitPK userVisitPK, GetVendorTypeDescriptionsForm form) {
        return new GetVendorTypeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editVendorTypeDescription(UserVisitPK userVisitPK, EditVendorTypeDescriptionForm form) {
        return new EditVendorTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteVendorTypeDescription(UserVisitPK userVisitPK, DeleteVendorTypeDescriptionForm form) {
        return new DeleteVendorTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Vendors
    // -------------------------------------------------------------------------

    @Override
    public CommandResult getVendor(UserVisitPK userVisitPK, GetVendorForm form) {
        return new GetVendorCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getVendors(UserVisitPK userVisitPK, GetVendorsForm form) {
        return new GetVendorsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editVendor(UserVisitPK userVisitPK, EditVendorForm form) {
        return new EditVendorCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Vendor Items
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createVendorItem(UserVisitPK userVisitPK, CreateVendorItemForm form) {
        return new CreateVendorItemCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getVendorItem(UserVisitPK userVisitPK, GetVendorItemForm form) {
        return new GetVendorItemCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getVendorItems(UserVisitPK userVisitPK, GetVendorItemsForm form) {
        return new GetVendorItemsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getVendorItemStatusChoices(UserVisitPK userVisitPK, GetVendorItemStatusChoicesForm form) {
        return new GetVendorItemStatusChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setVendorItemStatus(UserVisitPK userVisitPK, SetVendorItemStatusForm form) {
        return new SetVendorItemStatusCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editVendorItem(UserVisitPK userVisitPK, EditVendorItemForm form) {
        return new EditVendorItemCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteVendorItem(UserVisitPK userVisitPK, DeleteVendorItemForm form) {
        return new DeleteVendorItemCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Vendor Item Costs
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createVendorItemCost(UserVisitPK userVisitPK, CreateVendorItemCostForm form) {
        return new CreateVendorItemCostCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getVendorItemCost(UserVisitPK userVisitPK, GetVendorItemCostForm form) {
        return new GetVendorItemCostCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getVendorItemCosts(UserVisitPK userVisitPK, GetVendorItemCostsForm form) {
        return new GetVendorItemCostsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editVendorItemCost(UserVisitPK userVisitPK, EditVendorItemCostForm form) {
        return new EditVendorItemCostCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteVendorItemCost(UserVisitPK userVisitPK, DeleteVendorItemCostForm form) {
        return new DeleteVendorItemCostCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Purchasing Categories
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemPurchasingCategory(UserVisitPK userVisitPK, CreateItemPurchasingCategoryForm form) {
        return new CreateItemPurchasingCategoryCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemPurchasingCategoryChoices(UserVisitPK userVisitPK, GetItemPurchasingCategoryChoicesForm form) {
        return new GetItemPurchasingCategoryChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemPurchasingCategory(UserVisitPK userVisitPK, GetItemPurchasingCategoryForm form) {
        return new GetItemPurchasingCategoryCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemPurchasingCategories(UserVisitPK userVisitPK, GetItemPurchasingCategoriesForm form) {
        return new GetItemPurchasingCategoriesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultItemPurchasingCategory(UserVisitPK userVisitPK, SetDefaultItemPurchasingCategoryForm form) {
        return new SetDefaultItemPurchasingCategoryCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editItemPurchasingCategory(UserVisitPK userVisitPK, EditItemPurchasingCategoryForm form) {
        return new EditItemPurchasingCategoryCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteItemPurchasingCategory(UserVisitPK userVisitPK, DeleteItemPurchasingCategoryForm form) {
        return new DeleteItemPurchasingCategoryCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Vendor Category Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemPurchasingCategoryDescription(UserVisitPK userVisitPK, CreateItemPurchasingCategoryDescriptionForm form) {
        return new CreateItemPurchasingCategoryDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemPurchasingCategoryDescription(UserVisitPK userVisitPK, GetItemPurchasingCategoryDescriptionForm form) {
        return new GetItemPurchasingCategoryDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemPurchasingCategoryDescriptions(UserVisitPK userVisitPK, GetItemPurchasingCategoryDescriptionsForm form) {
        return new GetItemPurchasingCategoryDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editItemPurchasingCategoryDescription(UserVisitPK userVisitPK, EditItemPurchasingCategoryDescriptionForm form) {
        return new EditItemPurchasingCategoryDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteItemPurchasingCategoryDescription(UserVisitPK userVisitPK, DeleteItemPurchasingCategoryDescriptionForm form) {
        return new DeleteItemPurchasingCategoryDescriptionCommand().run(userVisitPK, form);
    }
    
}

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

package com.echothree.control.user.vendor.server;

import com.echothree.control.user.vendor.common.VendorRemote;
import com.echothree.control.user.vendor.common.form.*;
import com.echothree.control.user.vendor.common.result.*;
import com.echothree.control.user.vendor.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

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
    public CommandResult<CreateVendorTypeResult> createVendorType(UserVisitPK userVisitPK, CreateVendorTypeForm form) {
        return CDI.current().select(CreateVendorTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetVendorTypeChoicesResult> getVendorTypeChoices(UserVisitPK userVisitPK, GetVendorTypeChoicesForm form) {
        return CDI.current().select(GetVendorTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetVendorTypeResult> getVendorType(UserVisitPK userVisitPK, GetVendorTypeForm form) {
        return CDI.current().select(GetVendorTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetVendorTypesResult> getVendorTypes(UserVisitPK userVisitPK, GetVendorTypesForm form) {
        return CDI.current().select(GetVendorTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setDefaultVendorType(UserVisitPK userVisitPK, SetDefaultVendorTypeForm form) {
        return CDI.current().select(SetDefaultVendorTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditVendorTypeResult> editVendorType(UserVisitPK userVisitPK, EditVendorTypeForm form) {
        return CDI.current().select(EditVendorTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteVendorType(UserVisitPK userVisitPK, DeleteVendorTypeForm form) {
        return CDI.current().select(DeleteVendorTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Vendor Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createVendorTypeDescription(UserVisitPK userVisitPK, CreateVendorTypeDescriptionForm form) {
        return CDI.current().select(CreateVendorTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetVendorTypeDescriptionResult> getVendorTypeDescription(UserVisitPK userVisitPK, GetVendorTypeDescriptionForm form) {
        return CDI.current().select(GetVendorTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetVendorTypeDescriptionsResult> getVendorTypeDescriptions(UserVisitPK userVisitPK, GetVendorTypeDescriptionsForm form) {
        return CDI.current().select(GetVendorTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditVendorTypeDescriptionResult> editVendorTypeDescription(UserVisitPK userVisitPK, EditVendorTypeDescriptionForm form) {
        return CDI.current().select(EditVendorTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteVendorTypeDescription(UserVisitPK userVisitPK, DeleteVendorTypeDescriptionForm form) {
        return CDI.current().select(DeleteVendorTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Vendors
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<GetVendorResult> getVendor(UserVisitPK userVisitPK, GetVendorForm form) {
        return CDI.current().select(GetVendorCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetVendorsResult> getVendors(UserVisitPK userVisitPK, GetVendorsForm form) {
        return CDI.current().select(GetVendorsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditVendorResult> editVendor(UserVisitPK userVisitPK, EditVendorForm form) {
        return CDI.current().select(EditVendorCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Vendor Items
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<CreateVendorItemResult> createVendorItem(UserVisitPK userVisitPK, CreateVendorItemForm form) {
        return CDI.current().select(CreateVendorItemCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetVendorItemResult> getVendorItem(UserVisitPK userVisitPK, GetVendorItemForm form) {
        return CDI.current().select(GetVendorItemCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetVendorItemsResult> getVendorItems(UserVisitPK userVisitPK, GetVendorItemsForm form) {
        return CDI.current().select(GetVendorItemsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetVendorItemStatusChoicesResult> getVendorItemStatusChoices(UserVisitPK userVisitPK, GetVendorItemStatusChoicesForm form) {
        return CDI.current().select(GetVendorItemStatusChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> setVendorItemStatus(UserVisitPK userVisitPK, SetVendorItemStatusForm form) {
        return CDI.current().select(SetVendorItemStatusCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditVendorItemResult> editVendorItem(UserVisitPK userVisitPK, EditVendorItemForm form) {
        return CDI.current().select(EditVendorItemCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteVendorItem(UserVisitPK userVisitPK, DeleteVendorItemForm form) {
        return CDI.current().select(DeleteVendorItemCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Vendor Item Costs
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createVendorItemCost(UserVisitPK userVisitPK, CreateVendorItemCostForm form) {
        return CDI.current().select(CreateVendorItemCostCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetVendorItemCostResult> getVendorItemCost(UserVisitPK userVisitPK, GetVendorItemCostForm form) {
        return CDI.current().select(GetVendorItemCostCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetVendorItemCostsResult> getVendorItemCosts(UserVisitPK userVisitPK, GetVendorItemCostsForm form) {
        return CDI.current().select(GetVendorItemCostsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditVendorItemCostResult> editVendorItemCost(UserVisitPK userVisitPK, EditVendorItemCostForm form) {
        return CDI.current().select(EditVendorItemCostCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteVendorItemCost(UserVisitPK userVisitPK, DeleteVendorItemCostForm form) {
        return CDI.current().select(DeleteVendorItemCostCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Purchasing Categories
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<CreateItemPurchasingCategoryResult> createItemPurchasingCategory(UserVisitPK userVisitPK, CreateItemPurchasingCategoryForm form) {
        return CDI.current().select(CreateItemPurchasingCategoryCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemPurchasingCategoryChoicesResult> getItemPurchasingCategoryChoices(UserVisitPK userVisitPK, GetItemPurchasingCategoryChoicesForm form) {
        return CDI.current().select(GetItemPurchasingCategoryChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemPurchasingCategoryResult> getItemPurchasingCategory(UserVisitPK userVisitPK, GetItemPurchasingCategoryForm form) {
        return CDI.current().select(GetItemPurchasingCategoryCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemPurchasingCategoriesResult> getItemPurchasingCategories(UserVisitPK userVisitPK, GetItemPurchasingCategoriesForm form) {
        return CDI.current().select(GetItemPurchasingCategoriesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setDefaultItemPurchasingCategory(UserVisitPK userVisitPK, SetDefaultItemPurchasingCategoryForm form) {
        return CDI.current().select(SetDefaultItemPurchasingCategoryCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditItemPurchasingCategoryResult> editItemPurchasingCategory(UserVisitPK userVisitPK, EditItemPurchasingCategoryForm form) {
        return CDI.current().select(EditItemPurchasingCategoryCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteItemPurchasingCategory(UserVisitPK userVisitPK, DeleteItemPurchasingCategoryForm form) {
        return CDI.current().select(DeleteItemPurchasingCategoryCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Vendor Category Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createItemPurchasingCategoryDescription(UserVisitPK userVisitPK, CreateItemPurchasingCategoryDescriptionForm form) {
        return CDI.current().select(CreateItemPurchasingCategoryDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemPurchasingCategoryDescriptionResult> getItemPurchasingCategoryDescription(UserVisitPK userVisitPK, GetItemPurchasingCategoryDescriptionForm form) {
        return CDI.current().select(GetItemPurchasingCategoryDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemPurchasingCategoryDescriptionsResult> getItemPurchasingCategoryDescriptions(UserVisitPK userVisitPK, GetItemPurchasingCategoryDescriptionsForm form) {
        return CDI.current().select(GetItemPurchasingCategoryDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditItemPurchasingCategoryDescriptionResult> editItemPurchasingCategoryDescription(UserVisitPK userVisitPK, EditItemPurchasingCategoryDescriptionForm form) {
        return CDI.current().select(EditItemPurchasingCategoryDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteItemPurchasingCategoryDescription(UserVisitPK userVisitPK, DeleteItemPurchasingCategoryDescriptionForm form) {
        return CDI.current().select(DeleteItemPurchasingCategoryDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
}

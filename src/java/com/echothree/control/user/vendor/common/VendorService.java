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

package com.echothree.control.user.vendor.common;

import com.echothree.control.user.vendor.common.form.*;
import com.echothree.control.user.vendor.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface VendorService
        extends VendorForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Vendor Types
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateVendorTypeResult> createVendorType(UserVisitPK userVisitPK, CreateVendorTypeForm form);
    
    CommandResult<GetVendorTypeChoicesResult> getVendorTypeChoices(UserVisitPK userVisitPK, GetVendorTypeChoicesForm form);
    
    CommandResult<GetVendorTypeResult> getVendorType(UserVisitPK userVisitPK, GetVendorTypeForm form);
    
    CommandResult<GetVendorTypesResult> getVendorTypes(UserVisitPK userVisitPK, GetVendorTypesForm form);
    
    CommandResult<?> setDefaultVendorType(UserVisitPK userVisitPK, SetDefaultVendorTypeForm form);
    
    CommandResult<EditVendorTypeResult> editVendorType(UserVisitPK userVisitPK, EditVendorTypeForm form);
    
    CommandResult<?> deleteVendorType(UserVisitPK userVisitPK, DeleteVendorTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Vendor Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createVendorTypeDescription(UserVisitPK userVisitPK, CreateVendorTypeDescriptionForm form);
    
    CommandResult<GetVendorTypeDescriptionResult> getVendorTypeDescription(UserVisitPK userVisitPK, GetVendorTypeDescriptionForm form);
    
    CommandResult<GetVendorTypeDescriptionsResult> getVendorTypeDescriptions(UserVisitPK userVisitPK, GetVendorTypeDescriptionsForm form);
    
    CommandResult<EditVendorTypeDescriptionResult> editVendorTypeDescription(UserVisitPK userVisitPK, EditVendorTypeDescriptionForm form);
    
    CommandResult<?> deleteVendorTypeDescription(UserVisitPK userVisitPK, DeleteVendorTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Vendors
    // -------------------------------------------------------------------------

    CommandResult<GetVendorResult> getVendor(UserVisitPK userVisitPK, GetVendorForm form);

    CommandResult<GetVendorsResult> getVendors(UserVisitPK userVisitPK, GetVendorsForm form);

    CommandResult<EditVendorResult> editVendor(UserVisitPK userVisitPK, EditVendorForm form);
    
    // -------------------------------------------------------------------------
    //   Vendor Items
    // -------------------------------------------------------------------------
    
    CommandResult<CreateVendorItemResult> createVendorItem(UserVisitPK userVisitPK, CreateVendorItemForm form);
    
    CommandResult<GetVendorItemResult> getVendorItem(UserVisitPK userVisitPK, GetVendorItemForm form);

    CommandResult<GetVendorItemsResult> getVendorItems(UserVisitPK userVisitPK, GetVendorItemsForm form);

    CommandResult<GetVendorItemStatusChoicesResult> getVendorItemStatusChoices(UserVisitPK userVisitPK, GetVendorItemStatusChoicesForm form);

    CommandResult<?> setVendorItemStatus(UserVisitPK userVisitPK, SetVendorItemStatusForm form);

    CommandResult<EditVendorItemResult> editVendorItem(UserVisitPK userVisitPK, EditVendorItemForm form);
    
    CommandResult<?> deleteVendorItem(UserVisitPK userVisitPK, DeleteVendorItemForm form);
    
    // -------------------------------------------------------------------------
    //   Vendor Item Costs
    // -------------------------------------------------------------------------
    
    CommandResult<?> createVendorItemCost(UserVisitPK userVisitPK, CreateVendorItemCostForm form);
    
    CommandResult<GetVendorItemCostResult> getVendorItemCost(UserVisitPK userVisitPK, GetVendorItemCostForm form);
    
    CommandResult<GetVendorItemCostsResult> getVendorItemCosts(UserVisitPK userVisitPK, GetVendorItemCostsForm form);
    
    CommandResult<EditVendorItemCostResult> editVendorItemCost(UserVisitPK userVisitPK, EditVendorItemCostForm form);
    
    CommandResult<?> deleteVendorItemCost(UserVisitPK userVisitPK, DeleteVendorItemCostForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Purchasing Categories
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateItemPurchasingCategoryResult> createItemPurchasingCategory(UserVisitPK userVisitPK, CreateItemPurchasingCategoryForm form);
    
    CommandResult<GetItemPurchasingCategoryChoicesResult> getItemPurchasingCategoryChoices(UserVisitPK userVisitPK, GetItemPurchasingCategoryChoicesForm form);
    
    CommandResult<GetItemPurchasingCategoryResult> getItemPurchasingCategory(UserVisitPK userVisitPK, GetItemPurchasingCategoryForm form);
    
    CommandResult<GetItemPurchasingCategoriesResult> getItemPurchasingCategories(UserVisitPK userVisitPK, GetItemPurchasingCategoriesForm form);
    
    CommandResult<?> setDefaultItemPurchasingCategory(UserVisitPK userVisitPK, SetDefaultItemPurchasingCategoryForm form);
    
    CommandResult<EditItemPurchasingCategoryResult> editItemPurchasingCategory(UserVisitPK userVisitPK, EditItemPurchasingCategoryForm form);
    
    CommandResult<?> deleteItemPurchasingCategory(UserVisitPK userVisitPK, DeleteItemPurchasingCategoryForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Vendor Category Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createItemPurchasingCategoryDescription(UserVisitPK userVisitPK, CreateItemPurchasingCategoryDescriptionForm form);
    
    CommandResult<GetItemPurchasingCategoryDescriptionResult> getItemPurchasingCategoryDescription(UserVisitPK userVisitPK, GetItemPurchasingCategoryDescriptionForm form);
    
    CommandResult<GetItemPurchasingCategoryDescriptionsResult> getItemPurchasingCategoryDescriptions(UserVisitPK userVisitPK, GetItemPurchasingCategoryDescriptionsForm form);
    
    CommandResult<EditItemPurchasingCategoryDescriptionResult> editItemPurchasingCategoryDescription(UserVisitPK userVisitPK, EditItemPurchasingCategoryDescriptionForm form);
    
    CommandResult<?> deleteItemPurchasingCategoryDescription(UserVisitPK userVisitPK, DeleteItemPurchasingCategoryDescriptionForm form);
    
}

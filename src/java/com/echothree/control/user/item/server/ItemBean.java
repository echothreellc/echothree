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

package com.echothree.control.user.item.server;

import com.echothree.control.user.item.common.ItemRemote;
import com.echothree.control.user.item.common.form.*;
import com.echothree.control.user.item.common.result.*;
import com.echothree.control.user.item.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

@Stateless
public class ItemBean
        extends ItemFormsImpl
        implements ItemRemote, ItemLocal {
    
    // --------------------------------------------------------------------------------
    //   Testing
    // --------------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "ItemBean is alive!";
    }
    
    // --------------------------------------------------------------------------------
    //   Item Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createItemType(UserVisitPK userVisitPK, CreateItemTypeForm form) {
        return CDI.current().select(CreateItemTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemTypeResult> getItemType(UserVisitPK userVisitPK, GetItemTypeForm form) {
        return CDI.current().select(GetItemTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemTypesResult> getItemTypes(UserVisitPK userVisitPK, GetItemTypesForm form) {
        return CDI.current().select(GetItemTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemTypeChoicesResult> getItemTypeChoices(UserVisitPK userVisitPK, GetItemTypeChoicesForm form) {
        return CDI.current().select(GetItemTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createItemTypeDescription(UserVisitPK userVisitPK, CreateItemTypeDescriptionForm form) {
        return CDI.current().select(CreateItemTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Delivery Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createItemDeliveryType(UserVisitPK userVisitPK, CreateItemDeliveryTypeForm form) {
        return CDI.current().select(CreateItemDeliveryTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemDeliveryTypeResult> getItemDeliveryType(UserVisitPK userVisitPK, GetItemDeliveryTypeForm form) {
        return CDI.current().select(GetItemDeliveryTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemDeliveryTypesResult> getItemDeliveryTypes(UserVisitPK userVisitPK, GetItemDeliveryTypesForm form) {
        return CDI.current().select(GetItemDeliveryTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemDeliveryTypeChoicesResult> getItemDeliveryTypeChoices(UserVisitPK userVisitPK, GetItemDeliveryTypeChoicesForm form) {
        return CDI.current().select(GetItemDeliveryTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Delivery Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createItemDeliveryTypeDescription(UserVisitPK userVisitPK, CreateItemDeliveryTypeDescriptionForm form) {
        return CDI.current().select(CreateItemDeliveryTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Inventory Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createItemInventoryType(UserVisitPK userVisitPK, CreateItemInventoryTypeForm form) {
        return CDI.current().select(CreateItemInventoryTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemInventoryTypeResult> getItemInventoryType(UserVisitPK userVisitPK, GetItemInventoryTypeForm form) {
        return CDI.current().select(GetItemInventoryTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemInventoryTypesResult> getItemInventoryTypes(UserVisitPK userVisitPK, GetItemInventoryTypesForm form) {
        return CDI.current().select(GetItemInventoryTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemInventoryTypeChoicesResult> getItemInventoryTypeChoices(UserVisitPK userVisitPK, GetItemInventoryTypeChoicesForm form) {
        return CDI.current().select(GetItemInventoryTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Inventory Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createItemInventoryTypeDescription(UserVisitPK userVisitPK, CreateItemInventoryTypeDescriptionForm form) {
        return CDI.current().select(CreateItemInventoryTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Use Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createItemUseType(UserVisitPK userVisitPK, CreateItemUseTypeForm form) {
        return CDI.current().select(CreateItemUseTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemUseTypeResult> getItemUseType(UserVisitPK userVisitPK, GetItemUseTypeForm form) {
        return CDI.current().select(GetItemUseTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemUseTypesResult> getItemUseTypes(UserVisitPK userVisitPK, GetItemUseTypesForm form) {
        return CDI.current().select(GetItemUseTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemUseTypeChoicesResult> getItemUseTypeChoices(UserVisitPK userVisitPK, GetItemUseTypeChoicesForm form) {
        return CDI.current().select(GetItemUseTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createItemUseTypeDescription(UserVisitPK userVisitPK, CreateItemUseTypeDescriptionForm form) {
        return CDI.current().select(CreateItemUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Categories
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createItemCategory(UserVisitPK userVisitPK, CreateItemCategoryForm form) {
        return CDI.current().select(CreateItemCategoryCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemCategoryChoicesResult> getItemCategoryChoices(UserVisitPK userVisitPK, GetItemCategoryChoicesForm form) {
        return CDI.current().select(GetItemCategoryChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemCategoryResult> getItemCategory(UserVisitPK userVisitPK, GetItemCategoryForm form) {
        return CDI.current().select(GetItemCategoryCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemCategoriesResult> getItemCategories(UserVisitPK userVisitPK, GetItemCategoriesForm form) {
        return CDI.current().select(GetItemCategoriesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> setDefaultItemCategory(UserVisitPK userVisitPK, SetDefaultItemCategoryForm form) {
        return CDI.current().select(SetDefaultItemCategoryCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditItemCategoryResult> editItemCategory(UserVisitPK userVisitPK, EditItemCategoryForm form) {
        return CDI.current().select(EditItemCategoryCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteItemCategory(UserVisitPK userVisitPK, DeleteItemCategoryForm form) {
        return CDI.current().select(DeleteItemCategoryCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Category Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createItemCategoryDescription(UserVisitPK userVisitPK, CreateItemCategoryDescriptionForm form) {
        return CDI.current().select(CreateItemCategoryDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemCategoryDescriptionResult> getItemCategoryDescription(UserVisitPK userVisitPK, GetItemCategoryDescriptionForm form) {
        return CDI.current().select(GetItemCategoryDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemCategoryDescriptionsResult> getItemCategoryDescriptions(UserVisitPK userVisitPK, GetItemCategoryDescriptionsForm form) {
        return CDI.current().select(GetItemCategoryDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditItemCategoryDescriptionResult> editItemCategoryDescription(UserVisitPK userVisitPK, EditItemCategoryDescriptionForm form) {
        return CDI.current().select(EditItemCategoryDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteItemCategoryDescription(UserVisitPK userVisitPK, DeleteItemCategoryDescriptionForm form) {
        return CDI.current().select(DeleteItemCategoryDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Item Alias Checksum Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<?> createItemAliasChecksumType(UserVisitPK userVisitPK, CreateItemAliasChecksumTypeForm form) {
        return CDI.current().select(CreateItemAliasChecksumTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemAliasChecksumTypesResult> getItemAliasChecksumTypes(UserVisitPK userVisitPK, GetItemAliasChecksumTypesForm form) {
        return CDI.current().select(GetItemAliasChecksumTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemAliasChecksumTypeResult> getItemAliasChecksumType(UserVisitPK userVisitPK, GetItemAliasChecksumTypeForm form) {
        return CDI.current().select(GetItemAliasChecksumTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemAliasChecksumTypeChoicesResult> getItemAliasChecksumTypeChoices(UserVisitPK userVisitPK, GetItemAliasChecksumTypeChoicesForm form) {
        return CDI.current().select(GetItemAliasChecksumTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Item Alias Checksum Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<?> createItemAliasChecksumTypeDescription(UserVisitPK userVisitPK, CreateItemAliasChecksumTypeDescriptionForm form) {
        return CDI.current().select(CreateItemAliasChecksumTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Alias Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<CreateItemAliasTypeResult> createItemAliasType(UserVisitPK userVisitPK, CreateItemAliasTypeForm form) {
        return CDI.current().select(CreateItemAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemAliasTypeChoicesResult> getItemAliasTypeChoices(UserVisitPK userVisitPK, GetItemAliasTypeChoicesForm form) {
        return CDI.current().select(GetItemAliasTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemAliasTypeResult> getItemAliasType(UserVisitPK userVisitPK, GetItemAliasTypeForm form) {
        return CDI.current().select(GetItemAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemAliasTypesResult> getItemAliasTypes(UserVisitPK userVisitPK, GetItemAliasTypesForm form) {
        return CDI.current().select(GetItemAliasTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> setDefaultItemAliasType(UserVisitPK userVisitPK, SetDefaultItemAliasTypeForm form) {
        return CDI.current().select(SetDefaultItemAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditItemAliasTypeResult> editItemAliasType(UserVisitPK userVisitPK, EditItemAliasTypeForm form) {
        return CDI.current().select(EditItemAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteItemAliasType(UserVisitPK userVisitPK, DeleteItemAliasTypeForm form) {
        return CDI.current().select(DeleteItemAliasTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Alias Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createItemAliasTypeDescription(UserVisitPK userVisitPK, CreateItemAliasTypeDescriptionForm form) {
        return CDI.current().select(CreateItemAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemAliasTypeDescriptionResult> getItemAliasTypeDescription(UserVisitPK userVisitPK, GetItemAliasTypeDescriptionForm form) {
        return CDI.current().select(GetItemAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemAliasTypeDescriptionsResult> getItemAliasTypeDescriptions(UserVisitPK userVisitPK, GetItemAliasTypeDescriptionsForm form) {
        return CDI.current().select(GetItemAliasTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditItemAliasTypeDescriptionResult> editItemAliasTypeDescription(UserVisitPK userVisitPK, EditItemAliasTypeDescriptionForm form) {
        return CDI.current().select(EditItemAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteItemAliasTypeDescription(UserVisitPK userVisitPK, DeleteItemAliasTypeDescriptionForm form) {
        return CDI.current().select(DeleteItemAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Description Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<?> createItemDescriptionType(UserVisitPK userVisitPK, CreateItemDescriptionTypeForm form) {
        return CDI.current().select(CreateItemDescriptionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemDescriptionTypeChoicesResult> getItemDescriptionTypeChoices(UserVisitPK userVisitPK, GetItemDescriptionTypeChoicesForm form) {
        return CDI.current().select(GetItemDescriptionTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemDescriptionTypeResult> getItemDescriptionType(UserVisitPK userVisitPK, GetItemDescriptionTypeForm form) {
        return CDI.current().select(GetItemDescriptionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemDescriptionTypesResult> getItemDescriptionTypes(UserVisitPK userVisitPK, GetItemDescriptionTypesForm form) {
        return CDI.current().select(GetItemDescriptionTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> setDefaultItemDescriptionType(UserVisitPK userVisitPK, SetDefaultItemDescriptionTypeForm form) {
        return CDI.current().select(SetDefaultItemDescriptionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditItemDescriptionTypeResult> editItemDescriptionType(UserVisitPK userVisitPK, EditItemDescriptionTypeForm form) {
        return CDI.current().select(EditItemDescriptionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteItemDescriptionType(UserVisitPK userVisitPK, DeleteItemDescriptionTypeForm form) {
        return CDI.current().select(DeleteItemDescriptionTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Description Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<?> createItemDescriptionTypeDescription(UserVisitPK userVisitPK, CreateItemDescriptionTypeDescriptionForm form) {
        return CDI.current().select(CreateItemDescriptionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemDescriptionTypeDescriptionResult> getItemDescriptionTypeDescription(UserVisitPK userVisitPK, GetItemDescriptionTypeDescriptionForm form) {
        return CDI.current().select(GetItemDescriptionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemDescriptionTypeDescriptionsResult> getItemDescriptionTypeDescriptions(UserVisitPK userVisitPK, GetItemDescriptionTypeDescriptionsForm form) {
        return CDI.current().select(GetItemDescriptionTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditItemDescriptionTypeDescriptionResult> editItemDescriptionTypeDescription(UserVisitPK userVisitPK, EditItemDescriptionTypeDescriptionForm form) {
        return CDI.current().select(EditItemDescriptionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteItemDescriptionTypeDescription(UserVisitPK userVisitPK, DeleteItemDescriptionTypeDescriptionForm form) {
        return CDI.current().select(DeleteItemDescriptionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Description Type Use Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<CreateItemDescriptionTypeUseTypeResult> createItemDescriptionTypeUseType(UserVisitPK userVisitPK, CreateItemDescriptionTypeUseTypeForm form) {
        return CDI.current().select(CreateItemDescriptionTypeUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemDescriptionTypeUseTypeChoicesResult> getItemDescriptionTypeUseTypeChoices(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypeChoicesForm form) {
        return CDI.current().select(GetItemDescriptionTypeUseTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemDescriptionTypeUseTypeResult> getItemDescriptionTypeUseType(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypeForm form) {
        return CDI.current().select(GetItemDescriptionTypeUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemDescriptionTypeUseTypesResult> getItemDescriptionTypeUseTypes(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypesForm form) {
        return CDI.current().select(GetItemDescriptionTypeUseTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> setDefaultItemDescriptionTypeUseType(UserVisitPK userVisitPK, SetDefaultItemDescriptionTypeUseTypeForm form) {
        return CDI.current().select(SetDefaultItemDescriptionTypeUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditItemDescriptionTypeUseTypeResult> editItemDescriptionTypeUseType(UserVisitPK userVisitPK, EditItemDescriptionTypeUseTypeForm form) {
        return CDI.current().select(EditItemDescriptionTypeUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteItemDescriptionTypeUseType(UserVisitPK userVisitPK, DeleteItemDescriptionTypeUseTypeForm form) {
        return CDI.current().select(DeleteItemDescriptionTypeUseTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Description Type Use Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<?> createItemDescriptionTypeUseTypeDescription(UserVisitPK userVisitPK, CreateItemDescriptionTypeUseTypeDescriptionForm form) {
        return CDI.current().select(CreateItemDescriptionTypeUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemDescriptionTypeUseTypeDescriptionResult> getItemDescriptionTypeUseTypeDescription(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypeDescriptionForm form) {
        return CDI.current().select(GetItemDescriptionTypeUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemDescriptionTypeUseTypeDescriptionsResult> getItemDescriptionTypeUseTypeDescriptions(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypeDescriptionsForm form) {
        return CDI.current().select(GetItemDescriptionTypeUseTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditItemDescriptionTypeUseTypeDescriptionResult> editItemDescriptionTypeUseTypeDescription(UserVisitPK userVisitPK, EditItemDescriptionTypeUseTypeDescriptionForm form) {
        return CDI.current().select(EditItemDescriptionTypeUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteItemDescriptionTypeUseTypeDescription(UserVisitPK userVisitPK, DeleteItemDescriptionTypeUseTypeDescriptionForm form) {
        return CDI.current().select(DeleteItemDescriptionTypeUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Description Type Uses
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<?> createItemDescriptionTypeUse(UserVisitPK userVisitPK, CreateItemDescriptionTypeUseForm form) {
        return CDI.current().select(CreateItemDescriptionTypeUseCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemDescriptionTypeUseResult> getItemDescriptionTypeUse(UserVisitPK userVisitPK, GetItemDescriptionTypeUseForm form) {
        return CDI.current().select(GetItemDescriptionTypeUseCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemDescriptionTypeUsesResult> getItemDescriptionTypeUses(UserVisitPK userVisitPK, GetItemDescriptionTypeUsesForm form) {
        return CDI.current().select(GetItemDescriptionTypeUsesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteItemDescriptionTypeUse(UserVisitPK userVisitPK, DeleteItemDescriptionTypeUseForm form) {
        return CDI.current().select(DeleteItemDescriptionTypeUseCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Image Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<CreateItemImageTypeResult> createItemImageType(UserVisitPK userVisitPK, CreateItemImageTypeForm form) {
        return CDI.current().select(CreateItemImageTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemImageTypeChoicesResult> getItemImageTypeChoices(UserVisitPK userVisitPK, GetItemImageTypeChoicesForm form) {
        return CDI.current().select(GetItemImageTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemImageTypeResult> getItemImageType(UserVisitPK userVisitPK, GetItemImageTypeForm form) {
        return CDI.current().select(GetItemImageTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemImageTypesResult> getItemImageTypes(UserVisitPK userVisitPK, GetItemImageTypesForm form) {
        return CDI.current().select(GetItemImageTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> setDefaultItemImageType(UserVisitPK userVisitPK, SetDefaultItemImageTypeForm form) {
        return CDI.current().select(SetDefaultItemImageTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditItemImageTypeResult> editItemImageType(UserVisitPK userVisitPK, EditItemImageTypeForm form) {
        return CDI.current().select(EditItemImageTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteItemImageType(UserVisitPK userVisitPK, DeleteItemImageTypeForm form) {
        return CDI.current().select(DeleteItemImageTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Image Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<?> createItemImageTypeDescription(UserVisitPK userVisitPK, CreateItemImageTypeDescriptionForm form) {
        return CDI.current().select(CreateItemImageTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemImageTypeDescriptionResult> getItemImageTypeDescription(UserVisitPK userVisitPK, GetItemImageTypeDescriptionForm form) {
        return CDI.current().select(GetItemImageTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemImageTypeDescriptionsResult> getItemImageTypeDescriptions(UserVisitPK userVisitPK, GetItemImageTypeDescriptionsForm form) {
        return CDI.current().select(GetItemImageTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditItemImageTypeDescriptionResult> editItemImageTypeDescription(UserVisitPK userVisitPK, EditItemImageTypeDescriptionForm form) {
        return CDI.current().select(EditItemImageTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteItemImageTypeDescription(UserVisitPK userVisitPK, DeleteItemImageTypeDescriptionForm form) {
        return CDI.current().select(DeleteItemImageTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Items
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<CreateItemResult> createItem(UserVisitPK userVisitPK, CreateItemForm form) {
        return CDI.current().select(CreateItemCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemStatusChoicesResult> getItemStatusChoices(UserVisitPK userVisitPK, GetItemStatusChoicesForm form) {
        return CDI.current().select(GetItemStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> setItemStatus(UserVisitPK userVisitPK, SetItemStatusForm form) {
        return CDI.current().select(SetItemStatusCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemResult> getItem(UserVisitPK userVisitPK, GetItemForm form) {
        return CDI.current().select(GetItemCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemsResult> getItems(UserVisitPK userVisitPK, GetItemsForm form) {
        return CDI.current().select(GetItemsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditItemResult> editItem(UserVisitPK userVisitPK, EditItemForm form) {
        return CDI.current().select(EditItemCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Item Unit Of Measure Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createItemUnitOfMeasureType(UserVisitPK userVisitPK, CreateItemUnitOfMeasureTypeForm form) {
        return CDI.current().select(CreateItemUnitOfMeasureTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemUnitOfMeasureTypeResult> getItemUnitOfMeasureType(UserVisitPK userVisitPK, GetItemUnitOfMeasureTypeForm form) {
        return CDI.current().select(GetItemUnitOfMeasureTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemUnitOfMeasureTypesResult> getItemUnitOfMeasureTypes(UserVisitPK userVisitPK, GetItemUnitOfMeasureTypesForm form) {
        return CDI.current().select(GetItemUnitOfMeasureTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> setDefaultItemUnitOfMeasureType(UserVisitPK userVisitPK, SetDefaultItemUnitOfMeasureTypeForm form) {
        return CDI.current().select(SetDefaultItemUnitOfMeasureTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditItemUnitOfMeasureTypeResult> editItemUnitOfMeasureType(UserVisitPK userVisitPK, EditItemUnitOfMeasureTypeForm form) {
        return CDI.current().select(EditItemUnitOfMeasureTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteItemUnitOfMeasureType(UserVisitPK userVisitPK, DeleteItemUnitOfMeasureTypeForm form) {
        return CDI.current().select(DeleteItemUnitOfMeasureTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Aliases
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createItemAlias(UserVisitPK userVisitPK, CreateItemAliasForm form) {
        return CDI.current().select(CreateItemAliasCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemAliasResult> getItemAlias(UserVisitPK userVisitPK, GetItemAliasForm form) {
        return CDI.current().select(GetItemAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemAliasesResult> getItemAliases(UserVisitPK userVisitPK, GetItemAliasesForm form) {
        return CDI.current().select(GetItemAliasesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditItemAliasResult> editItemAlias(UserVisitPK userVisitPK, EditItemAliasForm form) {
        return CDI.current().select(EditItemAliasCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteItemAlias(UserVisitPK userVisitPK, DeleteItemAliasForm form) {
        return CDI.current().select(DeleteItemAliasCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createItemDescription(UserVisitPK userVisitPK, CreateItemDescriptionForm form) {
        return CDI.current().select(CreateItemDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemDescriptionResult> getItemDescription(UserVisitPK userVisitPK, GetItemDescriptionForm form) {
        return CDI.current().select(GetItemDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemDescriptionsResult> getItemDescriptions(UserVisitPK userVisitPK, GetItemDescriptionsForm form) {
        return CDI.current().select(GetItemDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditItemDescriptionResult> editItemDescription(UserVisitPK userVisitPK, EditItemDescriptionForm form) {
        return CDI.current().select(EditItemDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteItemDescription(UserVisitPK userVisitPK, DeleteItemDescriptionForm form) {
        return CDI.current().select(DeleteItemDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Price Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createItemPriceType(UserVisitPK userVisitPK, CreateItemPriceTypeForm form) {
        return CDI.current().select(CreateItemPriceTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemPriceTypeResult> getItemPriceType(UserVisitPK userVisitPK, GetItemPriceTypeForm form) {
        return CDI.current().select(GetItemPriceTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemPriceTypesResult> getItemPriceTypes(UserVisitPK userVisitPK, GetItemPriceTypesForm form) {
        return CDI.current().select(GetItemPriceTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemPriceTypeChoicesResult> getItemPriceTypeChoices(UserVisitPK userVisitPK, GetItemPriceTypeChoicesForm form) {
        return CDI.current().select(GetItemPriceTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Price Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createItemPriceTypeDescription(UserVisitPK userVisitPK, CreateItemPriceTypeDescriptionForm form) {
        return CDI.current().select(CreateItemPriceTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Prices
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createItemPrice(UserVisitPK userVisitPK, CreateItemPriceForm form) {
        return CDI.current().select(CreateItemPriceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemPriceResult> getItemPrice(UserVisitPK userVisitPK, GetItemPriceForm form) {
        return CDI.current().select(GetItemPriceCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemPricesResult> getItemPrices(UserVisitPK userVisitPK, GetItemPricesForm form) {
        return CDI.current().select(GetItemPricesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditItemPriceResult> editItemPrice(UserVisitPK userVisitPK, EditItemPriceForm form) {
        return CDI.current().select(EditItemPriceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteItemPrice(UserVisitPK userVisitPK, DeleteItemPriceForm form) {
        return CDI.current().select(DeleteItemPriceCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Volume Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<CreateItemVolumeTypeResult> createItemVolumeType(UserVisitPK userVisitPK, CreateItemVolumeTypeForm form) {
        return CDI.current().select(CreateItemVolumeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemVolumeTypeChoicesResult> getItemVolumeTypeChoices(UserVisitPK userVisitPK, GetItemVolumeTypeChoicesForm form) {
        return CDI.current().select(GetItemVolumeTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemVolumeTypeResult> getItemVolumeType(UserVisitPK userVisitPK, GetItemVolumeTypeForm form) {
        return CDI.current().select(GetItemVolumeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemVolumeTypesResult> getItemVolumeTypes(UserVisitPK userVisitPK, GetItemVolumeTypesForm form) {
        return CDI.current().select(GetItemVolumeTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> setDefaultItemVolumeType(UserVisitPK userVisitPK, SetDefaultItemVolumeTypeForm form) {
        return CDI.current().select(SetDefaultItemVolumeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditItemVolumeTypeResult> editItemVolumeType(UserVisitPK userVisitPK, EditItemVolumeTypeForm form) {
        return CDI.current().select(EditItemVolumeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteItemVolumeType(UserVisitPK userVisitPK, DeleteItemVolumeTypeForm form) {
        return CDI.current().select(DeleteItemVolumeTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Volume Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<?> createItemVolumeTypeDescription(UserVisitPK userVisitPK, CreateItemVolumeTypeDescriptionForm form) {
        return CDI.current().select(CreateItemVolumeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemVolumeTypeDescriptionResult> getItemVolumeTypeDescription(UserVisitPK userVisitPK, GetItemVolumeTypeDescriptionForm form) {
        return CDI.current().select(GetItemVolumeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemVolumeTypeDescriptionsResult> getItemVolumeTypeDescriptions(UserVisitPK userVisitPK, GetItemVolumeTypeDescriptionsForm form) {
        return CDI.current().select(GetItemVolumeTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditItemVolumeTypeDescriptionResult> editItemVolumeTypeDescription(UserVisitPK userVisitPK, EditItemVolumeTypeDescriptionForm form) {
        return CDI.current().select(EditItemVolumeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteItemVolumeTypeDescription(UserVisitPK userVisitPK, DeleteItemVolumeTypeDescriptionForm form) {
        return CDI.current().select(DeleteItemVolumeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Volumes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createItemVolume(UserVisitPK userVisitPK, CreateItemVolumeForm form) {
        return CDI.current().select(CreateItemVolumeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemVolumeResult> getItemVolume(UserVisitPK userVisitPK, GetItemVolumeForm form) {
        return CDI.current().select(GetItemVolumeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemVolumesResult> getItemVolumes(UserVisitPK userVisitPK, GetItemVolumesForm form) {
        return CDI.current().select(GetItemVolumesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditItemVolumeResult> editItemVolume(UserVisitPK userVisitPK, EditItemVolumeForm form) {
        return CDI.current().select(EditItemVolumeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteItemVolume(UserVisitPK userVisitPK, DeleteItemVolumeForm form) {
        return CDI.current().select(DeleteItemVolumeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Weight Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<CreateItemWeightTypeResult> createItemWeightType(UserVisitPK userVisitPK, CreateItemWeightTypeForm form) {
        return CDI.current().select(CreateItemWeightTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemWeightTypeChoicesResult> getItemWeightTypeChoices(UserVisitPK userVisitPK, GetItemWeightTypeChoicesForm form) {
        return CDI.current().select(GetItemWeightTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemWeightTypeResult> getItemWeightType(UserVisitPK userVisitPK, GetItemWeightTypeForm form) {
        return CDI.current().select(GetItemWeightTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemWeightTypesResult> getItemWeightTypes(UserVisitPK userVisitPK, GetItemWeightTypesForm form) {
        return CDI.current().select(GetItemWeightTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> setDefaultItemWeightType(UserVisitPK userVisitPK, SetDefaultItemWeightTypeForm form) {
        return CDI.current().select(SetDefaultItemWeightTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditItemWeightTypeResult> editItemWeightType(UserVisitPK userVisitPK, EditItemWeightTypeForm form) {
        return CDI.current().select(EditItemWeightTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteItemWeightType(UserVisitPK userVisitPK, DeleteItemWeightTypeForm form) {
        return CDI.current().select(DeleteItemWeightTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Weight Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<?> createItemWeightTypeDescription(UserVisitPK userVisitPK, CreateItemWeightTypeDescriptionForm form) {
        return CDI.current().select(CreateItemWeightTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemWeightTypeDescriptionResult> getItemWeightTypeDescription(UserVisitPK userVisitPK, GetItemWeightTypeDescriptionForm form) {
        return CDI.current().select(GetItemWeightTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemWeightTypeDescriptionsResult> getItemWeightTypeDescriptions(UserVisitPK userVisitPK, GetItemWeightTypeDescriptionsForm form) {
        return CDI.current().select(GetItemWeightTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditItemWeightTypeDescriptionResult> editItemWeightTypeDescription(UserVisitPK userVisitPK, EditItemWeightTypeDescriptionForm form) {
        return CDI.current().select(EditItemWeightTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteItemWeightTypeDescription(UserVisitPK userVisitPK, DeleteItemWeightTypeDescriptionForm form) {
        return CDI.current().select(DeleteItemWeightTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Weights
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createItemWeight(UserVisitPK userVisitPK, CreateItemWeightForm form) {
        return CDI.current().select(CreateItemWeightCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemWeightResult> getItemWeight(UserVisitPK userVisitPK, GetItemWeightForm form) {
        return CDI.current().select(GetItemWeightCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemWeightsResult> getItemWeights(UserVisitPK userVisitPK, GetItemWeightsForm form) {
        return CDI.current().select(GetItemWeightsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditItemWeightResult> editItemWeight(UserVisitPK userVisitPK, EditItemWeightForm form) {
        return CDI.current().select(EditItemWeightCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteItemWeight(UserVisitPK userVisitPK, DeleteItemWeightForm form) {
        return CDI.current().select(DeleteItemWeightCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Country Of Origins
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createItemCountryOfOrigin(UserVisitPK userVisitPK, CreateItemCountryOfOriginForm form) {
        return CDI.current().select(CreateItemCountryOfOriginCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemCountryOfOriginResult> getItemCountryOfOrigin(UserVisitPK userVisitPK, GetItemCountryOfOriginForm form) {
        return CDI.current().select(GetItemCountryOfOriginCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemCountryOfOriginsResult> getItemCountryOfOrigins(UserVisitPK userVisitPK, GetItemCountryOfOriginsForm form) {
        return CDI.current().select(GetItemCountryOfOriginsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditItemCountryOfOriginResult> editItemCountryOfOrigin(UserVisitPK userVisitPK, EditItemCountryOfOriginForm form) {
        return CDI.current().select(EditItemCountryOfOriginCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteItemCountryOfOrigin(UserVisitPK userVisitPK, DeleteItemCountryOfOriginForm form) {
        return CDI.current().select(DeleteItemCountryOfOriginCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Kit Members
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createItemKitMember(UserVisitPK userVisitPK, CreateItemKitMemberForm form) {
        return CDI.current().select(CreateItemKitMemberCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemKitMemberResult> getItemKitMember(UserVisitPK userVisitPK, GetItemKitMemberForm form) {
        return CDI.current().select(GetItemKitMemberCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemKitMembersResult> getItemKitMembers(UserVisitPK userVisitPK, GetItemKitMembersForm form) {
        return CDI.current().select(GetItemKitMembersCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteItemKitMember(UserVisitPK userVisitPK, DeleteItemKitMemberForm form) {
        return CDI.current().select(DeleteItemKitMemberCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Pack Check Requirements
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createItemPackCheckRequirement(UserVisitPK userVisitPK, CreateItemPackCheckRequirementForm form) {
        return CDI.current().select(CreateItemPackCheckRequirementCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemPackCheckRequirementResult> getItemPackCheckRequirement(UserVisitPK userVisitPK, GetItemPackCheckRequirementForm form) {
        return CDI.current().select(GetItemPackCheckRequirementCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemPackCheckRequirementsResult> getItemPackCheckRequirements(UserVisitPK userVisitPK, GetItemPackCheckRequirementsForm form) {
        return CDI.current().select(GetItemPackCheckRequirementsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditItemPackCheckRequirementResult> editItemPackCheckRequirement(UserVisitPK userVisitPK, EditItemPackCheckRequirementForm form) {
        return CDI.current().select(EditItemPackCheckRequirementCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteItemPackCheckRequirement(UserVisitPK userVisitPK, DeleteItemPackCheckRequirementForm form) {
        return CDI.current().select(DeleteItemPackCheckRequirementCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Shipping Times
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<GetItemShippingTimeResult> getItemShippingTime(UserVisitPK userVisitPK, GetItemShippingTimeForm form) {
        return CDI.current().select(GetItemShippingTimeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemShippingTimesResult> getItemShippingTimes(UserVisitPK userVisitPK, GetItemShippingTimesForm form) {
        return CDI.current().select(GetItemShippingTimesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditItemShippingTimeResult> editItemShippingTime(UserVisitPK userVisitPK, EditItemShippingTimeForm form) {
        return CDI.current().select(EditItemShippingTimeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Unit Customer Type Limits
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createItemUnitCustomerTypeLimit(UserVisitPK userVisitPK, CreateItemUnitCustomerTypeLimitForm form) {
        return CDI.current().select(CreateItemUnitCustomerTypeLimitCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemUnitCustomerTypeLimitResult> getItemUnitCustomerTypeLimit(UserVisitPK userVisitPK, GetItemUnitCustomerTypeLimitForm form) {
        return CDI.current().select(GetItemUnitCustomerTypeLimitCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemUnitCustomerTypeLimitsResult> getItemUnitCustomerTypeLimits(UserVisitPK userVisitPK, GetItemUnitCustomerTypeLimitsForm form) {
        return CDI.current().select(GetItemUnitCustomerTypeLimitsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditItemUnitCustomerTypeLimitResult> editItemUnitCustomerTypeLimit(UserVisitPK userVisitPK, EditItemUnitCustomerTypeLimitForm form) {
        return CDI.current().select(EditItemUnitCustomerTypeLimitCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteItemUnitCustomerTypeLimit(UserVisitPK userVisitPK, DeleteItemUnitCustomerTypeLimitForm form) {
        return CDI.current().select(DeleteItemUnitCustomerTypeLimitCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Unit Limits
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createItemUnitLimit(UserVisitPK userVisitPK, CreateItemUnitLimitForm form) {
        return CDI.current().select(CreateItemUnitLimitCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemUnitLimitResult> getItemUnitLimit(UserVisitPK userVisitPK, GetItemUnitLimitForm form) {
        return CDI.current().select(GetItemUnitLimitCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemUnitLimitsResult> getItemUnitLimits(UserVisitPK userVisitPK, GetItemUnitLimitsForm form) {
        return CDI.current().select(GetItemUnitLimitsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditItemUnitLimitResult> editItemUnitLimit(UserVisitPK userVisitPK, EditItemUnitLimitForm form) {
        return CDI.current().select(EditItemUnitLimitCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteItemUnitLimit(UserVisitPK userVisitPK, DeleteItemUnitLimitForm form) {
        return CDI.current().select(DeleteItemUnitLimitCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Unit Price Limits
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createItemUnitPriceLimit(UserVisitPK userVisitPK, CreateItemUnitPriceLimitForm form) {
        return CDI.current().select(CreateItemUnitPriceLimitCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetItemUnitPriceLimitResult> getItemUnitPriceLimit(UserVisitPK userVisitPK, GetItemUnitPriceLimitForm form) {
        return CDI.current().select(GetItemUnitPriceLimitCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemUnitPriceLimitsResult> getItemUnitPriceLimits(UserVisitPK userVisitPK, GetItemUnitPriceLimitsForm form) {
        return CDI.current().select(GetItemUnitPriceLimitsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditItemUnitPriceLimitResult> editItemUnitPriceLimit(UserVisitPK userVisitPK, EditItemUnitPriceLimitForm form) {
        return CDI.current().select(EditItemUnitPriceLimitCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteItemUnitPriceLimit(UserVisitPK userVisitPK, DeleteItemUnitPriceLimitForm form) {
        return CDI.current().select(DeleteItemUnitPriceLimitCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Related Item Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<?> createRelatedItemType(UserVisitPK userVisitPK, CreateRelatedItemTypeForm form) {
        return CDI.current().select(CreateRelatedItemTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetRelatedItemTypeChoicesResult> getRelatedItemTypeChoices(UserVisitPK userVisitPK, GetRelatedItemTypeChoicesForm form) {
        return CDI.current().select(GetRelatedItemTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetRelatedItemTypeResult> getRelatedItemType(UserVisitPK userVisitPK, GetRelatedItemTypeForm form) {
        return CDI.current().select(GetRelatedItemTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetRelatedItemTypesResult> getRelatedItemTypes(UserVisitPK userVisitPK, GetRelatedItemTypesForm form) {
        return CDI.current().select(GetRelatedItemTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> setDefaultRelatedItemType(UserVisitPK userVisitPK, SetDefaultRelatedItemTypeForm form) {
        return CDI.current().select(SetDefaultRelatedItemTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditRelatedItemTypeResult> editRelatedItemType(UserVisitPK userVisitPK, EditRelatedItemTypeForm form) {
        return CDI.current().select(EditRelatedItemTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteRelatedItemType(UserVisitPK userVisitPK, DeleteRelatedItemTypeForm form) {
        return CDI.current().select(DeleteRelatedItemTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Related Item Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<?> createRelatedItemTypeDescription(UserVisitPK userVisitPK, CreateRelatedItemTypeDescriptionForm form) {
        return CDI.current().select(CreateRelatedItemTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetRelatedItemTypeDescriptionResult> getRelatedItemTypeDescription(UserVisitPK userVisitPK, GetRelatedItemTypeDescriptionForm form) {
        return CDI.current().select(GetRelatedItemTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetRelatedItemTypeDescriptionsResult> getRelatedItemTypeDescriptions(UserVisitPK userVisitPK, GetRelatedItemTypeDescriptionsForm form) {
        return CDI.current().select(GetRelatedItemTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditRelatedItemTypeDescriptionResult> editRelatedItemTypeDescription(UserVisitPK userVisitPK, EditRelatedItemTypeDescriptionForm form) {
        return CDI.current().select(EditRelatedItemTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteRelatedItemTypeDescription(UserVisitPK userVisitPK, DeleteRelatedItemTypeDescriptionForm form) {
        return CDI.current().select(DeleteRelatedItemTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Related Items
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createRelatedItem(UserVisitPK userVisitPK, CreateRelatedItemForm form) {
        return CDI.current().select(CreateRelatedItemCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetRelatedItemResult> getRelatedItem(UserVisitPK userVisitPK, GetRelatedItemForm form) {
        return CDI.current().select(GetRelatedItemCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetRelatedItemsResult> getRelatedItems(UserVisitPK userVisitPK, GetRelatedItemsForm form) {
        return CDI.current().select(GetRelatedItemsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditRelatedItemResult> editRelatedItem(UserVisitPK userVisitPK, EditRelatedItemForm form) {
        return CDI.current().select(EditRelatedItemCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteRelatedItem(UserVisitPK userVisitPK, DeleteRelatedItemForm form) {
        return CDI.current().select(DeleteRelatedItemCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Codes
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<?> createHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeForm form) {
        return CDI.current().select(CreateHarmonizedTariffScheduleCodeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetHarmonizedTariffScheduleCodesResult> getHarmonizedTariffScheduleCodes(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodesForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetHarmonizedTariffScheduleCodeResult> getHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetHarmonizedTariffScheduleCodeChoicesResult> getHarmonizedTariffScheduleCodeChoices(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeChoicesForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> setDefaultHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, SetDefaultHarmonizedTariffScheduleCodeForm form) {
        return CDI.current().select(SetDefaultHarmonizedTariffScheduleCodeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditHarmonizedTariffScheduleCodeResult> editHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeForm form) {
        return CDI.current().select(EditHarmonizedTariffScheduleCodeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeForm form) {
        return CDI.current().select(DeleteHarmonizedTariffScheduleCodeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<?> createHarmonizedTariffScheduleCodeTranslation(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeTranslationForm form) {
        return CDI.current().select(CreateHarmonizedTariffScheduleCodeTranslationCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetHarmonizedTariffScheduleCodeTranslationsResult> getHarmonizedTariffScheduleCodeTranslations(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeTranslationsForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeTranslationsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetHarmonizedTariffScheduleCodeTranslationResult> getHarmonizedTariffScheduleCodeTranslation(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeTranslationForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeTranslationCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditHarmonizedTariffScheduleCodeTranslationResult> editHarmonizedTariffScheduleCodeTranslation(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeTranslationForm form) {
        return CDI.current().select(EditHarmonizedTariffScheduleCodeTranslationCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteHarmonizedTariffScheduleCodeTranslation(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeTranslationForm form) {
        return CDI.current().select(DeleteHarmonizedTariffScheduleCodeTranslationCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Units
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<?> createHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUnitForm form) {
        return CDI.current().select(CreateHarmonizedTariffScheduleCodeUnitCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetHarmonizedTariffScheduleCodeUnitsResult> getHarmonizedTariffScheduleCodeUnits(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitsForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeUnitsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetHarmonizedTariffScheduleCodeUnitResult> getHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeUnitCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetHarmonizedTariffScheduleCodeUnitChoicesResult> getHarmonizedTariffScheduleCodeUnitChoices(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitChoicesForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeUnitChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> setDefaultHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, SetDefaultHarmonizedTariffScheduleCodeUnitForm form) {
        return CDI.current().select(SetDefaultHarmonizedTariffScheduleCodeUnitCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditHarmonizedTariffScheduleCodeUnitResult> editHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeUnitForm form) {
        return CDI.current().select(EditHarmonizedTariffScheduleCodeUnitCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUnitForm form) {
        return CDI.current().select(DeleteHarmonizedTariffScheduleCodeUnitCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Unit Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<?> createHarmonizedTariffScheduleCodeUnitDescription(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUnitDescriptionForm form) {
        return CDI.current().select(CreateHarmonizedTariffScheduleCodeUnitDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetHarmonizedTariffScheduleCodeUnitDescriptionsResult> getHarmonizedTariffScheduleCodeUnitDescriptions(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitDescriptionsForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeUnitDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetHarmonizedTariffScheduleCodeUnitDescriptionResult> getHarmonizedTariffScheduleCodeUnitDescription(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitDescriptionForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeUnitDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditHarmonizedTariffScheduleCodeUnitDescriptionResult> editHarmonizedTariffScheduleCodeUnitDescription(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeUnitDescriptionForm form) {
        return CDI.current().select(EditHarmonizedTariffScheduleCodeUnitDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteHarmonizedTariffScheduleCodeUnitDescription(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUnitDescriptionForm form) {
        return CDI.current().select(DeleteHarmonizedTariffScheduleCodeUnitDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Use Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<?> createHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUseTypeForm form) {
        return CDI.current().select(CreateHarmonizedTariffScheduleCodeUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetHarmonizedTariffScheduleCodeUseTypesResult> getHarmonizedTariffScheduleCodeUseTypes(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypesForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeUseTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetHarmonizedTariffScheduleCodeUseTypeResult> getHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypeForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetHarmonizedTariffScheduleCodeUseTypeChoicesResult> getHarmonizedTariffScheduleCodeUseTypeChoices(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypeChoicesForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeUseTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> setDefaultHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, SetDefaultHarmonizedTariffScheduleCodeUseTypeForm form) {
        return CDI.current().select(SetDefaultHarmonizedTariffScheduleCodeUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditHarmonizedTariffScheduleCodeUseTypeResult> editHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeUseTypeForm form) {
        return CDI.current().select(EditHarmonizedTariffScheduleCodeUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUseTypeForm form) {
        return CDI.current().select(DeleteHarmonizedTariffScheduleCodeUseTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Use Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<?> createHarmonizedTariffScheduleCodeUseTypeDescription(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUseTypeDescriptionForm form) {
        return CDI.current().select(CreateHarmonizedTariffScheduleCodeUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetHarmonizedTariffScheduleCodeUseTypeDescriptionsResult> getHarmonizedTariffScheduleCodeUseTypeDescriptions(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypeDescriptionsForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeUseTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetHarmonizedTariffScheduleCodeUseTypeDescriptionResult> getHarmonizedTariffScheduleCodeUseTypeDescription(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypeDescriptionForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditHarmonizedTariffScheduleCodeUseTypeDescriptionResult> editHarmonizedTariffScheduleCodeUseTypeDescription(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeUseTypeDescriptionForm form) {
        return CDI.current().select(EditHarmonizedTariffScheduleCodeUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteHarmonizedTariffScheduleCodeUseTypeDescription(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUseTypeDescriptionForm form) {
        return CDI.current().select(DeleteHarmonizedTariffScheduleCodeUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Uses
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<?> createHarmonizedTariffScheduleCodeUse(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUseForm form) {
        return CDI.current().select(CreateHarmonizedTariffScheduleCodeUseCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetHarmonizedTariffScheduleCodeUsesResult> getHarmonizedTariffScheduleCodeUses(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUsesForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeUsesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetHarmonizedTariffScheduleCodeUseResult> getHarmonizedTariffScheduleCodeUse(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeUseCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteHarmonizedTariffScheduleCodeUse(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUseForm form) {
        return CDI.current().select(DeleteHarmonizedTariffScheduleCodeUseCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Item Harmonized Tariff Schedule Codes
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<?> createItemHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, CreateItemHarmonizedTariffScheduleCodeForm form) {
        return CDI.current().select(CreateItemHarmonizedTariffScheduleCodeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemHarmonizedTariffScheduleCodesResult> getItemHarmonizedTariffScheduleCodes(UserVisitPK userVisitPK, GetItemHarmonizedTariffScheduleCodesForm form) {
        return CDI.current().select(GetItemHarmonizedTariffScheduleCodesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetItemHarmonizedTariffScheduleCodeResult> getItemHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, GetItemHarmonizedTariffScheduleCodeForm form) {
        return CDI.current().select(GetItemHarmonizedTariffScheduleCodeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditItemHarmonizedTariffScheduleCodeResult> editItemHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, EditItemHarmonizedTariffScheduleCodeForm form) {
        return CDI.current().select(EditItemHarmonizedTariffScheduleCodeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteItemHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, DeleteItemHarmonizedTariffScheduleCodeForm form) {
        return CDI.current().select(DeleteItemHarmonizedTariffScheduleCodeCommand.class).get().run(userVisitPK, form);
    }

}

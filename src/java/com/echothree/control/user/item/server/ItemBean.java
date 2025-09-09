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

package com.echothree.control.user.item.server;

import com.echothree.control.user.item.common.ItemRemote;
import com.echothree.control.user.item.common.form.*;
import com.echothree.control.user.item.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

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
    public CommandResult createItemType(UserVisitPK userVisitPK, CreateItemTypeForm form) {
        return new CreateItemTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemType(UserVisitPK userVisitPK, GetItemTypeForm form) {
        return new GetItemTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemTypes(UserVisitPK userVisitPK, GetItemTypesForm form) {
        return new GetItemTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemTypeChoices(UserVisitPK userVisitPK, GetItemTypeChoicesForm form) {
        return new GetItemTypeChoicesCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemTypeDescription(UserVisitPK userVisitPK, CreateItemTypeDescriptionForm form) {
        return new CreateItemTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Delivery Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemDeliveryType(UserVisitPK userVisitPK, CreateItemDeliveryTypeForm form) {
        return new CreateItemDeliveryTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemDeliveryType(UserVisitPK userVisitPK, GetItemDeliveryTypeForm form) {
        return new GetItemDeliveryTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemDeliveryTypes(UserVisitPK userVisitPK, GetItemDeliveryTypesForm form) {
        return new GetItemDeliveryTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemDeliveryTypeChoices(UserVisitPK userVisitPK, GetItemDeliveryTypeChoicesForm form) {
        return new GetItemDeliveryTypeChoicesCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Delivery Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemDeliveryTypeDescription(UserVisitPK userVisitPK, CreateItemDeliveryTypeDescriptionForm form) {
        return new CreateItemDeliveryTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Inventory Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemInventoryType(UserVisitPK userVisitPK, CreateItemInventoryTypeForm form) {
        return new CreateItemInventoryTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemInventoryType(UserVisitPK userVisitPK, GetItemInventoryTypeForm form) {
        return new GetItemInventoryTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemInventoryTypes(UserVisitPK userVisitPK, GetItemInventoryTypesForm form) {
        return new GetItemInventoryTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemInventoryTypeChoices(UserVisitPK userVisitPK, GetItemInventoryTypeChoicesForm form) {
        return new GetItemInventoryTypeChoicesCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Inventory Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemInventoryTypeDescription(UserVisitPK userVisitPK, CreateItemInventoryTypeDescriptionForm form) {
        return new CreateItemInventoryTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Use Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemUseType(UserVisitPK userVisitPK, CreateItemUseTypeForm form) {
        return new CreateItemUseTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemUseType(UserVisitPK userVisitPK, GetItemUseTypeForm form) {
        return new GetItemUseTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemUseTypes(UserVisitPK userVisitPK, GetItemUseTypesForm form) {
        return new GetItemUseTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemUseTypeChoices(UserVisitPK userVisitPK, GetItemUseTypeChoicesForm form) {
        return new GetItemUseTypeChoicesCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemUseTypeDescription(UserVisitPK userVisitPK, CreateItemUseTypeDescriptionForm form) {
        return new CreateItemUseTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Categories
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemCategory(UserVisitPK userVisitPK, CreateItemCategoryForm form) {
        return new CreateItemCategoryCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemCategoryChoices(UserVisitPK userVisitPK, GetItemCategoryChoicesForm form) {
        return new GetItemCategoryChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemCategory(UserVisitPK userVisitPK, GetItemCategoryForm form) {
        return new GetItemCategoryCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemCategories(UserVisitPK userVisitPK, GetItemCategoriesForm form) {
        return new GetItemCategoriesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultItemCategory(UserVisitPK userVisitPK, SetDefaultItemCategoryForm form) {
        return new SetDefaultItemCategoryCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemCategory(UserVisitPK userVisitPK, EditItemCategoryForm form) {
        return new EditItemCategoryCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemCategory(UserVisitPK userVisitPK, DeleteItemCategoryForm form) {
        return new DeleteItemCategoryCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Category Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemCategoryDescription(UserVisitPK userVisitPK, CreateItemCategoryDescriptionForm form) {
        return new CreateItemCategoryDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemCategoryDescription(UserVisitPK userVisitPK, GetItemCategoryDescriptionForm form) {
        return new GetItemCategoryDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemCategoryDescriptions(UserVisitPK userVisitPK, GetItemCategoryDescriptionsForm form) {
        return new GetItemCategoryDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemCategoryDescription(UserVisitPK userVisitPK, EditItemCategoryDescriptionForm form) {
        return new EditItemCategoryDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemCategoryDescription(UserVisitPK userVisitPK, DeleteItemCategoryDescriptionForm form) {
        return new DeleteItemCategoryDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Item Alias Checksum Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createItemAliasChecksumType(UserVisitPK userVisitPK, CreateItemAliasChecksumTypeForm form) {
        return new CreateItemAliasChecksumTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemAliasChecksumTypes(UserVisitPK userVisitPK, GetItemAliasChecksumTypesForm form) {
        return new GetItemAliasChecksumTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemAliasChecksumType(UserVisitPK userVisitPK, GetItemAliasChecksumTypeForm form) {
        return new GetItemAliasChecksumTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemAliasChecksumTypeChoices(UserVisitPK userVisitPK, GetItemAliasChecksumTypeChoicesForm form) {
        return new GetItemAliasChecksumTypeChoicesCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Item Alias Checksum Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createItemAliasChecksumTypeDescription(UserVisitPK userVisitPK, CreateItemAliasChecksumTypeDescriptionForm form) {
        return new CreateItemAliasChecksumTypeDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Alias Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemAliasType(UserVisitPK userVisitPK, CreateItemAliasTypeForm form) {
        return new CreateItemAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemAliasTypeChoices(UserVisitPK userVisitPK, GetItemAliasTypeChoicesForm form) {
        return new GetItemAliasTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemAliasType(UserVisitPK userVisitPK, GetItemAliasTypeForm form) {
        return new GetItemAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemAliasTypes(UserVisitPK userVisitPK, GetItemAliasTypesForm form) {
        return new GetItemAliasTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultItemAliasType(UserVisitPK userVisitPK, SetDefaultItemAliasTypeForm form) {
        return new SetDefaultItemAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemAliasType(UserVisitPK userVisitPK, EditItemAliasTypeForm form) {
        return new EditItemAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemAliasType(UserVisitPK userVisitPK, DeleteItemAliasTypeForm form) {
        return new DeleteItemAliasTypeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Alias Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemAliasTypeDescription(UserVisitPK userVisitPK, CreateItemAliasTypeDescriptionForm form) {
        return new CreateItemAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemAliasTypeDescription(UserVisitPK userVisitPK, GetItemAliasTypeDescriptionForm form) {
        return new GetItemAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemAliasTypeDescriptions(UserVisitPK userVisitPK, GetItemAliasTypeDescriptionsForm form) {
        return new GetItemAliasTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemAliasTypeDescription(UserVisitPK userVisitPK, EditItemAliasTypeDescriptionForm form) {
        return new EditItemAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemAliasTypeDescription(UserVisitPK userVisitPK, DeleteItemAliasTypeDescriptionForm form) {
        return new DeleteItemAliasTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Description Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createItemDescriptionType(UserVisitPK userVisitPK, CreateItemDescriptionTypeForm form) {
        return new CreateItemDescriptionTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemDescriptionTypeChoices(UserVisitPK userVisitPK, GetItemDescriptionTypeChoicesForm form) {
        return new GetItemDescriptionTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemDescriptionType(UserVisitPK userVisitPK, GetItemDescriptionTypeForm form) {
        return new GetItemDescriptionTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemDescriptionTypes(UserVisitPK userVisitPK, GetItemDescriptionTypesForm form) {
        return new GetItemDescriptionTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultItemDescriptionType(UserVisitPK userVisitPK, SetDefaultItemDescriptionTypeForm form) {
        return new SetDefaultItemDescriptionTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemDescriptionType(UserVisitPK userVisitPK, EditItemDescriptionTypeForm form) {
        return new EditItemDescriptionTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemDescriptionType(UserVisitPK userVisitPK, DeleteItemDescriptionTypeForm form) {
        return new DeleteItemDescriptionTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Description Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createItemDescriptionTypeDescription(UserVisitPK userVisitPK, CreateItemDescriptionTypeDescriptionForm form) {
        return new CreateItemDescriptionTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemDescriptionTypeDescription(UserVisitPK userVisitPK, GetItemDescriptionTypeDescriptionForm form) {
        return new GetItemDescriptionTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemDescriptionTypeDescriptions(UserVisitPK userVisitPK, GetItemDescriptionTypeDescriptionsForm form) {
        return new GetItemDescriptionTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemDescriptionTypeDescription(UserVisitPK userVisitPK, EditItemDescriptionTypeDescriptionForm form) {
        return new EditItemDescriptionTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemDescriptionTypeDescription(UserVisitPK userVisitPK, DeleteItemDescriptionTypeDescriptionForm form) {
        return new DeleteItemDescriptionTypeDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Description Type Use Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createItemDescriptionTypeUseType(UserVisitPK userVisitPK, CreateItemDescriptionTypeUseTypeForm form) {
        return new CreateItemDescriptionTypeUseTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemDescriptionTypeUseTypeChoices(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypeChoicesForm form) {
        return new GetItemDescriptionTypeUseTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemDescriptionTypeUseType(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypeForm form) {
        return new GetItemDescriptionTypeUseTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemDescriptionTypeUseTypes(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypesForm form) {
        return new GetItemDescriptionTypeUseTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultItemDescriptionTypeUseType(UserVisitPK userVisitPK, SetDefaultItemDescriptionTypeUseTypeForm form) {
        return new SetDefaultItemDescriptionTypeUseTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemDescriptionTypeUseType(UserVisitPK userVisitPK, EditItemDescriptionTypeUseTypeForm form) {
        return new EditItemDescriptionTypeUseTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemDescriptionTypeUseType(UserVisitPK userVisitPK, DeleteItemDescriptionTypeUseTypeForm form) {
        return new DeleteItemDescriptionTypeUseTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Description Type Use Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createItemDescriptionTypeUseTypeDescription(UserVisitPK userVisitPK, CreateItemDescriptionTypeUseTypeDescriptionForm form) {
        return new CreateItemDescriptionTypeUseTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemDescriptionTypeUseTypeDescription(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypeDescriptionForm form) {
        return new GetItemDescriptionTypeUseTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemDescriptionTypeUseTypeDescriptions(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypeDescriptionsForm form) {
        return new GetItemDescriptionTypeUseTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemDescriptionTypeUseTypeDescription(UserVisitPK userVisitPK, EditItemDescriptionTypeUseTypeDescriptionForm form) {
        return new EditItemDescriptionTypeUseTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemDescriptionTypeUseTypeDescription(UserVisitPK userVisitPK, DeleteItemDescriptionTypeUseTypeDescriptionForm form) {
        return new DeleteItemDescriptionTypeUseTypeDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Description Type Uses
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createItemDescriptionTypeUse(UserVisitPK userVisitPK, CreateItemDescriptionTypeUseForm form) {
        return new CreateItemDescriptionTypeUseCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemDescriptionTypeUse(UserVisitPK userVisitPK, GetItemDescriptionTypeUseForm form) {
        return new GetItemDescriptionTypeUseCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemDescriptionTypeUses(UserVisitPK userVisitPK, GetItemDescriptionTypeUsesForm form) {
        return new GetItemDescriptionTypeUsesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemDescriptionTypeUse(UserVisitPK userVisitPK, DeleteItemDescriptionTypeUseForm form) {
        return new DeleteItemDescriptionTypeUseCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Image Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createItemImageType(UserVisitPK userVisitPK, CreateItemImageTypeForm form) {
        return new CreateItemImageTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemImageTypeChoices(UserVisitPK userVisitPK, GetItemImageTypeChoicesForm form) {
        return new GetItemImageTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemImageType(UserVisitPK userVisitPK, GetItemImageTypeForm form) {
        return new GetItemImageTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemImageTypes(UserVisitPK userVisitPK, GetItemImageTypesForm form) {
        return new GetItemImageTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultItemImageType(UserVisitPK userVisitPK, SetDefaultItemImageTypeForm form) {
        return new SetDefaultItemImageTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemImageType(UserVisitPK userVisitPK, EditItemImageTypeForm form) {
        return new EditItemImageTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemImageType(UserVisitPK userVisitPK, DeleteItemImageTypeForm form) {
        return new DeleteItemImageTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Image Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createItemImageTypeDescription(UserVisitPK userVisitPK, CreateItemImageTypeDescriptionForm form) {
        return new CreateItemImageTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemImageTypeDescription(UserVisitPK userVisitPK, GetItemImageTypeDescriptionForm form) {
        return new GetItemImageTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemImageTypeDescriptions(UserVisitPK userVisitPK, GetItemImageTypeDescriptionsForm form) {
        return new GetItemImageTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemImageTypeDescription(UserVisitPK userVisitPK, EditItemImageTypeDescriptionForm form) {
        return new EditItemImageTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemImageTypeDescription(UserVisitPK userVisitPK, DeleteItemImageTypeDescriptionForm form) {
        return new DeleteItemImageTypeDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Items
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItem(UserVisitPK userVisitPK, CreateItemForm form) {
        return new CreateItemCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemStatusChoices(UserVisitPK userVisitPK, GetItemStatusChoicesForm form) {
        return new GetItemStatusChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setItemStatus(UserVisitPK userVisitPK, SetItemStatusForm form) {
        return new SetItemStatusCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItem(UserVisitPK userVisitPK, GetItemForm form) {
        return new GetItemCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItems(UserVisitPK userVisitPK, GetItemsForm form) {
        return new GetItemsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItem(UserVisitPK userVisitPK, EditItemForm form) {
        return new EditItemCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Item Unit Of Measure Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemUnitOfMeasureType(UserVisitPK userVisitPK, CreateItemUnitOfMeasureTypeForm form) {
        return new CreateItemUnitOfMeasureTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemUnitOfMeasureType(UserVisitPK userVisitPK, GetItemUnitOfMeasureTypeForm form) {
        return new GetItemUnitOfMeasureTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemUnitOfMeasureTypes(UserVisitPK userVisitPK, GetItemUnitOfMeasureTypesForm form) {
        return new GetItemUnitOfMeasureTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultItemUnitOfMeasureType(UserVisitPK userVisitPK, SetDefaultItemUnitOfMeasureTypeForm form) {
        return new SetDefaultItemUnitOfMeasureTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editItemUnitOfMeasureType(UserVisitPK userVisitPK, EditItemUnitOfMeasureTypeForm form) {
        return new EditItemUnitOfMeasureTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteItemUnitOfMeasureType(UserVisitPK userVisitPK, DeleteItemUnitOfMeasureTypeForm form) {
        return new DeleteItemUnitOfMeasureTypeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Aliases
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemAlias(UserVisitPK userVisitPK, CreateItemAliasForm form) {
        return new CreateItemAliasCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemAlias(UserVisitPK userVisitPK, GetItemAliasForm form) {
        return new GetItemAliasCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemAliases(UserVisitPK userVisitPK, GetItemAliasesForm form) {
        return new GetItemAliasesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemAlias(UserVisitPK userVisitPK, EditItemAliasForm form) {
        return new EditItemAliasCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteItemAlias(UserVisitPK userVisitPK, DeleteItemAliasForm form) {
        return new DeleteItemAliasCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemDescription(UserVisitPK userVisitPK, CreateItemDescriptionForm form) {
        return new CreateItemDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemDescription(UserVisitPK userVisitPK, GetItemDescriptionForm form) {
        return new GetItemDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemDescriptions(UserVisitPK userVisitPK, GetItemDescriptionsForm form) {
        return new GetItemDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editItemDescription(UserVisitPK userVisitPK, EditItemDescriptionForm form) {
        return new EditItemDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteItemDescription(UserVisitPK userVisitPK, DeleteItemDescriptionForm form) {
        return new DeleteItemDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Price Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemPriceType(UserVisitPK userVisitPK, CreateItemPriceTypeForm form) {
        return new CreateItemPriceTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemPriceType(UserVisitPK userVisitPK, GetItemPriceTypeForm form) {
        return new GetItemPriceTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemPriceTypes(UserVisitPK userVisitPK, GetItemPriceTypesForm form) {
        return new GetItemPriceTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemPriceTypeChoices(UserVisitPK userVisitPK, GetItemPriceTypeChoicesForm form) {
        return new GetItemPriceTypeChoicesCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Price Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemPriceTypeDescription(UserVisitPK userVisitPK, CreateItemPriceTypeDescriptionForm form) {
        return new CreateItemPriceTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Prices
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemPrice(UserVisitPK userVisitPK, CreateItemPriceForm form) {
        return new CreateItemPriceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemPrice(UserVisitPK userVisitPK, GetItemPriceForm form) {
        return new GetItemPriceCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemPrices(UserVisitPK userVisitPK, GetItemPricesForm form) {
        return new GetItemPricesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemPrice(UserVisitPK userVisitPK, EditItemPriceForm form) {
        return new EditItemPriceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteItemPrice(UserVisitPK userVisitPK, DeleteItemPriceForm form) {
        return new DeleteItemPriceCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Volumes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemVolume(UserVisitPK userVisitPK, CreateItemVolumeForm form) {
        return new CreateItemVolumeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemVolume(UserVisitPK userVisitPK, GetItemVolumeForm form) {
        return new GetItemVolumeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemVolumes(UserVisitPK userVisitPK, GetItemVolumesForm form) {
        return new GetItemVolumesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemVolume(UserVisitPK userVisitPK, EditItemVolumeForm form) {
        return new EditItemVolumeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteItemVolume(UserVisitPK userVisitPK, DeleteItemVolumeForm form) {
        return new DeleteItemVolumeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Weight Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createItemWeightType(UserVisitPK userVisitPK, CreateItemWeightTypeForm form) {
        return new CreateItemWeightTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemWeightTypeChoices(UserVisitPK userVisitPK, GetItemWeightTypeChoicesForm form) {
        return new GetItemWeightTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemWeightType(UserVisitPK userVisitPK, GetItemWeightTypeForm form) {
        return new GetItemWeightTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemWeightTypes(UserVisitPK userVisitPK, GetItemWeightTypesForm form) {
        return new GetItemWeightTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultItemWeightType(UserVisitPK userVisitPK, SetDefaultItemWeightTypeForm form) {
        return new SetDefaultItemWeightTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemWeightType(UserVisitPK userVisitPK, EditItemWeightTypeForm form) {
        return new EditItemWeightTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemWeightType(UserVisitPK userVisitPK, DeleteItemWeightTypeForm form) {
        return new DeleteItemWeightTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Weight Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createItemWeightTypeDescription(UserVisitPK userVisitPK, CreateItemWeightTypeDescriptionForm form) {
        return new CreateItemWeightTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemWeightTypeDescription(UserVisitPK userVisitPK, GetItemWeightTypeDescriptionForm form) {
        return new GetItemWeightTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemWeightTypeDescriptions(UserVisitPK userVisitPK, GetItemWeightTypeDescriptionsForm form) {
        return new GetItemWeightTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemWeightTypeDescription(UserVisitPK userVisitPK, EditItemWeightTypeDescriptionForm form) {
        return new EditItemWeightTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemWeightTypeDescription(UserVisitPK userVisitPK, DeleteItemWeightTypeDescriptionForm form) {
        return new DeleteItemWeightTypeDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Weights
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemWeight(UserVisitPK userVisitPK, CreateItemWeightForm form) {
        return new CreateItemWeightCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemWeight(UserVisitPK userVisitPK, GetItemWeightForm form) {
        return new GetItemWeightCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemWeights(UserVisitPK userVisitPK, GetItemWeightsForm form) {
        return new GetItemWeightsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemWeight(UserVisitPK userVisitPK, EditItemWeightForm form) {
        return new EditItemWeightCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteItemWeight(UserVisitPK userVisitPK, DeleteItemWeightForm form) {
        return new DeleteItemWeightCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Country Of Origins
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemCountryOfOrigin(UserVisitPK userVisitPK, CreateItemCountryOfOriginForm form) {
        return new CreateItemCountryOfOriginCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemCountryOfOrigin(UserVisitPK userVisitPK, GetItemCountryOfOriginForm form) {
        return new GetItemCountryOfOriginCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemCountryOfOrigins(UserVisitPK userVisitPK, GetItemCountryOfOriginsForm form) {
        return new GetItemCountryOfOriginsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemCountryOfOrigin(UserVisitPK userVisitPK, EditItemCountryOfOriginForm form) {
        return new EditItemCountryOfOriginCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteItemCountryOfOrigin(UserVisitPK userVisitPK, DeleteItemCountryOfOriginForm form) {
        return new DeleteItemCountryOfOriginCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Kit Members
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemKitMember(UserVisitPK userVisitPK, CreateItemKitMemberForm form) {
        return new CreateItemKitMemberCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemKitMember(UserVisitPK userVisitPK, GetItemKitMemberForm form) {
        return new GetItemKitMemberCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemKitMembers(UserVisitPK userVisitPK, GetItemKitMembersForm form) {
        return new GetItemKitMembersCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemKitMember(UserVisitPK userVisitPK, DeleteItemKitMemberForm form) {
        return new DeleteItemKitMemberCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Pack Check Requirements
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemPackCheckRequirement(UserVisitPK userVisitPK, CreateItemPackCheckRequirementForm form) {
        return new CreateItemPackCheckRequirementCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemPackCheckRequirement(UserVisitPK userVisitPK, GetItemPackCheckRequirementForm form) {
        return new GetItemPackCheckRequirementCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemPackCheckRequirements(UserVisitPK userVisitPK, GetItemPackCheckRequirementsForm form) {
        return new GetItemPackCheckRequirementsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemPackCheckRequirement(UserVisitPK userVisitPK, EditItemPackCheckRequirementForm form) {
        return new EditItemPackCheckRequirementCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemPackCheckRequirement(UserVisitPK userVisitPK, DeleteItemPackCheckRequirementForm form) {
        return new DeleteItemPackCheckRequirementCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Shipping Times
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult getItemShippingTime(UserVisitPK userVisitPK, GetItemShippingTimeForm form) {
        return new GetItemShippingTimeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemShippingTimes(UserVisitPK userVisitPK, GetItemShippingTimesForm form) {
        return new GetItemShippingTimesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemShippingTime(UserVisitPK userVisitPK, EditItemShippingTimeForm form) {
        return new EditItemShippingTimeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Unit Customer Type Limits
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemUnitCustomerTypeLimit(UserVisitPK userVisitPK, CreateItemUnitCustomerTypeLimitForm form) {
        return new CreateItemUnitCustomerTypeLimitCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemUnitCustomerTypeLimit(UserVisitPK userVisitPK, GetItemUnitCustomerTypeLimitForm form) {
        return new GetItemUnitCustomerTypeLimitCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemUnitCustomerTypeLimits(UserVisitPK userVisitPK, GetItemUnitCustomerTypeLimitsForm form) {
        return new GetItemUnitCustomerTypeLimitsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemUnitCustomerTypeLimit(UserVisitPK userVisitPK, EditItemUnitCustomerTypeLimitForm form) {
        return new EditItemUnitCustomerTypeLimitCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemUnitCustomerTypeLimit(UserVisitPK userVisitPK, DeleteItemUnitCustomerTypeLimitForm form) {
        return new DeleteItemUnitCustomerTypeLimitCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Unit Limits
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemUnitLimit(UserVisitPK userVisitPK, CreateItemUnitLimitForm form) {
        return new CreateItemUnitLimitCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemUnitLimit(UserVisitPK userVisitPK, GetItemUnitLimitForm form) {
        return new GetItemUnitLimitCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemUnitLimits(UserVisitPK userVisitPK, GetItemUnitLimitsForm form) {
        return new GetItemUnitLimitsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemUnitLimit(UserVisitPK userVisitPK, EditItemUnitLimitForm form) {
        return new EditItemUnitLimitCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemUnitLimit(UserVisitPK userVisitPK, DeleteItemUnitLimitForm form) {
        return new DeleteItemUnitLimitCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Unit Price Limits
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemUnitPriceLimit(UserVisitPK userVisitPK, CreateItemUnitPriceLimitForm form) {
        return new CreateItemUnitPriceLimitCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemUnitPriceLimit(UserVisitPK userVisitPK, GetItemUnitPriceLimitForm form) {
        return new GetItemUnitPriceLimitCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemUnitPriceLimits(UserVisitPK userVisitPK, GetItemUnitPriceLimitsForm form) {
        return new GetItemUnitPriceLimitsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemUnitPriceLimit(UserVisitPK userVisitPK, EditItemUnitPriceLimitForm form) {
        return new EditItemUnitPriceLimitCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemUnitPriceLimit(UserVisitPK userVisitPK, DeleteItemUnitPriceLimitForm form) {
        return new DeleteItemUnitPriceLimitCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Related Item Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createRelatedItemType(UserVisitPK userVisitPK, CreateRelatedItemTypeForm form) {
        return new CreateRelatedItemTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getRelatedItemTypeChoices(UserVisitPK userVisitPK, GetRelatedItemTypeChoicesForm form) {
        return new GetRelatedItemTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getRelatedItemType(UserVisitPK userVisitPK, GetRelatedItemTypeForm form) {
        return new GetRelatedItemTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getRelatedItemTypes(UserVisitPK userVisitPK, GetRelatedItemTypesForm form) {
        return new GetRelatedItemTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultRelatedItemType(UserVisitPK userVisitPK, SetDefaultRelatedItemTypeForm form) {
        return new SetDefaultRelatedItemTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editRelatedItemType(UserVisitPK userVisitPK, EditRelatedItemTypeForm form) {
        return new EditRelatedItemTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteRelatedItemType(UserVisitPK userVisitPK, DeleteRelatedItemTypeForm form) {
        return new DeleteRelatedItemTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Related Item Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createRelatedItemTypeDescription(UserVisitPK userVisitPK, CreateRelatedItemTypeDescriptionForm form) {
        return new CreateRelatedItemTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getRelatedItemTypeDescription(UserVisitPK userVisitPK, GetRelatedItemTypeDescriptionForm form) {
        return new GetRelatedItemTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getRelatedItemTypeDescriptions(UserVisitPK userVisitPK, GetRelatedItemTypeDescriptionsForm form) {
        return new GetRelatedItemTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editRelatedItemTypeDescription(UserVisitPK userVisitPK, EditRelatedItemTypeDescriptionForm form) {
        return new EditRelatedItemTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteRelatedItemTypeDescription(UserVisitPK userVisitPK, DeleteRelatedItemTypeDescriptionForm form) {
        return new DeleteRelatedItemTypeDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Related Items
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createRelatedItem(UserVisitPK userVisitPK, CreateRelatedItemForm form) {
        return new CreateRelatedItemCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getRelatedItem(UserVisitPK userVisitPK, GetRelatedItemForm form) {
        return new GetRelatedItemCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getRelatedItems(UserVisitPK userVisitPK, GetRelatedItemsForm form) {
        return new GetRelatedItemsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editRelatedItem(UserVisitPK userVisitPK, EditRelatedItemForm form) {
        return new EditRelatedItemCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteRelatedItem(UserVisitPK userVisitPK, DeleteRelatedItemForm form) {
        return new DeleteRelatedItemCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Codes
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeForm form) {
        return new CreateHarmonizedTariffScheduleCodeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodes(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodesForm form) {
        return new GetHarmonizedTariffScheduleCodesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeForm form) {
        return new GetHarmonizedTariffScheduleCodeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeChoices(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeChoicesForm form) {
        return new GetHarmonizedTariffScheduleCodeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, SetDefaultHarmonizedTariffScheduleCodeForm form) {
        return new SetDefaultHarmonizedTariffScheduleCodeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeForm form) {
        return new EditHarmonizedTariffScheduleCodeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeForm form) {
        return new DeleteHarmonizedTariffScheduleCodeCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createHarmonizedTariffScheduleCodeTranslation(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeTranslationForm form) {
        return new CreateHarmonizedTariffScheduleCodeTranslationCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeTranslations(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeTranslationsForm form) {
        return new GetHarmonizedTariffScheduleCodeTranslationsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeTranslation(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeTranslationForm form) {
        return new GetHarmonizedTariffScheduleCodeTranslationCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editHarmonizedTariffScheduleCodeTranslation(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeTranslationForm form) {
        return new EditHarmonizedTariffScheduleCodeTranslationCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteHarmonizedTariffScheduleCodeTranslation(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeTranslationForm form) {
        return new DeleteHarmonizedTariffScheduleCodeTranslationCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Units
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUnitForm form) {
        return new CreateHarmonizedTariffScheduleCodeUnitCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUnits(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitsForm form) {
        return new GetHarmonizedTariffScheduleCodeUnitsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitForm form) {
        return new GetHarmonizedTariffScheduleCodeUnitCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUnitChoices(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitChoicesForm form) {
        return new GetHarmonizedTariffScheduleCodeUnitChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, SetDefaultHarmonizedTariffScheduleCodeUnitForm form) {
        return new SetDefaultHarmonizedTariffScheduleCodeUnitCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeUnitForm form) {
        return new EditHarmonizedTariffScheduleCodeUnitCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUnitForm form) {
        return new DeleteHarmonizedTariffScheduleCodeUnitCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Unit Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createHarmonizedTariffScheduleCodeUnitDescription(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUnitDescriptionForm form) {
        return new CreateHarmonizedTariffScheduleCodeUnitDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUnitDescriptions(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitDescriptionsForm form) {
        return new GetHarmonizedTariffScheduleCodeUnitDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUnitDescription(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitDescriptionForm form) {
        return new GetHarmonizedTariffScheduleCodeUnitDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editHarmonizedTariffScheduleCodeUnitDescription(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeUnitDescriptionForm form) {
        return new EditHarmonizedTariffScheduleCodeUnitDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteHarmonizedTariffScheduleCodeUnitDescription(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUnitDescriptionForm form) {
        return new DeleteHarmonizedTariffScheduleCodeUnitDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Use Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUseTypeForm form) {
        return new CreateHarmonizedTariffScheduleCodeUseTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUseTypes(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypesForm form) {
        return new GetHarmonizedTariffScheduleCodeUseTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypeForm form) {
        return new GetHarmonizedTariffScheduleCodeUseTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUseTypeChoices(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypeChoicesForm form) {
        return new GetHarmonizedTariffScheduleCodeUseTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, SetDefaultHarmonizedTariffScheduleCodeUseTypeForm form) {
        return new SetDefaultHarmonizedTariffScheduleCodeUseTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeUseTypeForm form) {
        return new EditHarmonizedTariffScheduleCodeUseTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUseTypeForm form) {
        return new DeleteHarmonizedTariffScheduleCodeUseTypeCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Use Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createHarmonizedTariffScheduleCodeUseTypeDescription(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUseTypeDescriptionForm form) {
        return new CreateHarmonizedTariffScheduleCodeUseTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUseTypeDescriptions(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypeDescriptionsForm form) {
        return new GetHarmonizedTariffScheduleCodeUseTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUseTypeDescription(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypeDescriptionForm form) {
        return new GetHarmonizedTariffScheduleCodeUseTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editHarmonizedTariffScheduleCodeUseTypeDescription(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeUseTypeDescriptionForm form) {
        return new EditHarmonizedTariffScheduleCodeUseTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteHarmonizedTariffScheduleCodeUseTypeDescription(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUseTypeDescriptionForm form) {
        return new DeleteHarmonizedTariffScheduleCodeUseTypeDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Uses
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createHarmonizedTariffScheduleCodeUse(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUseForm form) {
        return new CreateHarmonizedTariffScheduleCodeUseCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUses(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUsesForm form) {
        return new GetHarmonizedTariffScheduleCodeUsesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUse(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseForm form) {
        return new GetHarmonizedTariffScheduleCodeUseCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteHarmonizedTariffScheduleCodeUse(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUseForm form) {
        return new DeleteHarmonizedTariffScheduleCodeUseCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Item Harmonized Tariff Schedule Codes
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createItemHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, CreateItemHarmonizedTariffScheduleCodeForm form) {
        return new CreateItemHarmonizedTariffScheduleCodeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemHarmonizedTariffScheduleCodes(UserVisitPK userVisitPK, GetItemHarmonizedTariffScheduleCodesForm form) {
        return new GetItemHarmonizedTariffScheduleCodesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, GetItemHarmonizedTariffScheduleCodeForm form) {
        return new GetItemHarmonizedTariffScheduleCodeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, EditItemHarmonizedTariffScheduleCodeForm form) {
        return new EditItemHarmonizedTariffScheduleCodeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, DeleteItemHarmonizedTariffScheduleCodeForm form) {
        return new DeleteItemHarmonizedTariffScheduleCodeCommand().run(userVisitPK, form);
    }

}

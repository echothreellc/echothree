// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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
        return new CreateItemTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemType(UserVisitPK userVisitPK, GetItemTypeForm form) {
        return new GetItemTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemTypes(UserVisitPK userVisitPK, GetItemTypesForm form) {
        return new GetItemTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemTypeChoices(UserVisitPK userVisitPK, GetItemTypeChoicesForm form) {
        return new GetItemTypeChoicesCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemTypeDescription(UserVisitPK userVisitPK, CreateItemTypeDescriptionForm form) {
        return new CreateItemTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Delivery Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemDeliveryType(UserVisitPK userVisitPK, CreateItemDeliveryTypeForm form) {
        return new CreateItemDeliveryTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemDeliveryType(UserVisitPK userVisitPK, GetItemDeliveryTypeForm form) {
        return new GetItemDeliveryTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemDeliveryTypes(UserVisitPK userVisitPK, GetItemDeliveryTypesForm form) {
        return new GetItemDeliveryTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemDeliveryTypeChoices(UserVisitPK userVisitPK, GetItemDeliveryTypeChoicesForm form) {
        return new GetItemDeliveryTypeChoicesCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Delivery Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemDeliveryTypeDescription(UserVisitPK userVisitPK, CreateItemDeliveryTypeDescriptionForm form) {
        return new CreateItemDeliveryTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Inventory Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemInventoryType(UserVisitPK userVisitPK, CreateItemInventoryTypeForm form) {
        return new CreateItemInventoryTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemInventoryType(UserVisitPK userVisitPK, GetItemInventoryTypeForm form) {
        return new GetItemInventoryTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemInventoryTypes(UserVisitPK userVisitPK, GetItemInventoryTypesForm form) {
        return new GetItemInventoryTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemInventoryTypeChoices(UserVisitPK userVisitPK, GetItemInventoryTypeChoicesForm form) {
        return new GetItemInventoryTypeChoicesCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Inventory Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemInventoryTypeDescription(UserVisitPK userVisitPK, CreateItemInventoryTypeDescriptionForm form) {
        return new CreateItemInventoryTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Use Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemUseType(UserVisitPK userVisitPK, CreateItemUseTypeForm form) {
        return new CreateItemUseTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemUseType(UserVisitPK userVisitPK, GetItemUseTypeForm form) {
        return new GetItemUseTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemUseTypes(UserVisitPK userVisitPK, GetItemUseTypesForm form) {
        return new GetItemUseTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemUseTypeChoices(UserVisitPK userVisitPK, GetItemUseTypeChoicesForm form) {
        return new GetItemUseTypeChoicesCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemUseTypeDescription(UserVisitPK userVisitPK, CreateItemUseTypeDescriptionForm form) {
        return new CreateItemUseTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Categories
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemCategory(UserVisitPK userVisitPK, CreateItemCategoryForm form) {
        return new CreateItemCategoryCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemCategoryChoices(UserVisitPK userVisitPK, GetItemCategoryChoicesForm form) {
        return new GetItemCategoryChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemCategory(UserVisitPK userVisitPK, GetItemCategoryForm form) {
        return new GetItemCategoryCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemCategories(UserVisitPK userVisitPK, GetItemCategoriesForm form) {
        return new GetItemCategoriesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultItemCategory(UserVisitPK userVisitPK, SetDefaultItemCategoryForm form) {
        return new SetDefaultItemCategoryCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editItemCategory(UserVisitPK userVisitPK, EditItemCategoryForm form) {
        return new EditItemCategoryCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteItemCategory(UserVisitPK userVisitPK, DeleteItemCategoryForm form) {
        return new DeleteItemCategoryCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Category Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemCategoryDescription(UserVisitPK userVisitPK, CreateItemCategoryDescriptionForm form) {
        return new CreateItemCategoryDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemCategoryDescription(UserVisitPK userVisitPK, GetItemCategoryDescriptionForm form) {
        return new GetItemCategoryDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemCategoryDescriptions(UserVisitPK userVisitPK, GetItemCategoryDescriptionsForm form) {
        return new GetItemCategoryDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editItemCategoryDescription(UserVisitPK userVisitPK, EditItemCategoryDescriptionForm form) {
        return new EditItemCategoryDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteItemCategoryDescription(UserVisitPK userVisitPK, DeleteItemCategoryDescriptionForm form) {
        return new DeleteItemCategoryDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Item Alias Checksum Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createItemAliasChecksumType(UserVisitPK userVisitPK, CreateItemAliasChecksumTypeForm form) {
        return new CreateItemAliasChecksumTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemAliasChecksumTypeChoices(UserVisitPK userVisitPK, GetItemAliasChecksumTypeChoicesForm form) {
        return new GetItemAliasChecksumTypeChoicesCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Item Alias Checksum Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createItemAliasChecksumTypeDescription(UserVisitPK userVisitPK, CreateItemAliasChecksumTypeDescriptionForm form) {
        return new CreateItemAliasChecksumTypeDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Item Alias Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemAliasType(UserVisitPK userVisitPK, CreateItemAliasTypeForm form) {
        return new CreateItemAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemAliasTypeChoices(UserVisitPK userVisitPK, GetItemAliasTypeChoicesForm form) {
        return new GetItemAliasTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemAliasType(UserVisitPK userVisitPK, GetItemAliasTypeForm form) {
        return new GetItemAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemAliasTypes(UserVisitPK userVisitPK, GetItemAliasTypesForm form) {
        return new GetItemAliasTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultItemAliasType(UserVisitPK userVisitPK, SetDefaultItemAliasTypeForm form) {
        return new SetDefaultItemAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editItemAliasType(UserVisitPK userVisitPK, EditItemAliasTypeForm form) {
        return new EditItemAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteItemAliasType(UserVisitPK userVisitPK, DeleteItemAliasTypeForm form) {
        return new DeleteItemAliasTypeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Alias Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemAliasTypeDescription(UserVisitPK userVisitPK, CreateItemAliasTypeDescriptionForm form) {
        return new CreateItemAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemAliasTypeDescription(UserVisitPK userVisitPK, GetItemAliasTypeDescriptionForm form) {
        return new GetItemAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemAliasTypeDescriptions(UserVisitPK userVisitPK, GetItemAliasTypeDescriptionsForm form) {
        return new GetItemAliasTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editItemAliasTypeDescription(UserVisitPK userVisitPK, EditItemAliasTypeDescriptionForm form) {
        return new EditItemAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteItemAliasTypeDescription(UserVisitPK userVisitPK, DeleteItemAliasTypeDescriptionForm form) {
        return new DeleteItemAliasTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Description Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createItemDescriptionType(UserVisitPK userVisitPK, CreateItemDescriptionTypeForm form) {
        return new CreateItemDescriptionTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemDescriptionTypeChoices(UserVisitPK userVisitPK, GetItemDescriptionTypeChoicesForm form) {
        return new GetItemDescriptionTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemDescriptionType(UserVisitPK userVisitPK, GetItemDescriptionTypeForm form) {
        return new GetItemDescriptionTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemDescriptionTypes(UserVisitPK userVisitPK, GetItemDescriptionTypesForm form) {
        return new GetItemDescriptionTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultItemDescriptionType(UserVisitPK userVisitPK, SetDefaultItemDescriptionTypeForm form) {
        return new SetDefaultItemDescriptionTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editItemDescriptionType(UserVisitPK userVisitPK, EditItemDescriptionTypeForm form) {
        return new EditItemDescriptionTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteItemDescriptionType(UserVisitPK userVisitPK, DeleteItemDescriptionTypeForm form) {
        return new DeleteItemDescriptionTypeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Item Description Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createItemDescriptionTypeDescription(UserVisitPK userVisitPK, CreateItemDescriptionTypeDescriptionForm form) {
        return new CreateItemDescriptionTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemDescriptionTypeDescription(UserVisitPK userVisitPK, GetItemDescriptionTypeDescriptionForm form) {
        return new GetItemDescriptionTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemDescriptionTypeDescriptions(UserVisitPK userVisitPK, GetItemDescriptionTypeDescriptionsForm form) {
        return new GetItemDescriptionTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editItemDescriptionTypeDescription(UserVisitPK userVisitPK, EditItemDescriptionTypeDescriptionForm form) {
        return new EditItemDescriptionTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteItemDescriptionTypeDescription(UserVisitPK userVisitPK, DeleteItemDescriptionTypeDescriptionForm form) {
        return new DeleteItemDescriptionTypeDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Item Description Type Use Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createItemDescriptionTypeUseType(UserVisitPK userVisitPK, CreateItemDescriptionTypeUseTypeForm form) {
        return new CreateItemDescriptionTypeUseTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemDescriptionTypeUseTypeChoices(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypeChoicesForm form) {
        return new GetItemDescriptionTypeUseTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemDescriptionTypeUseType(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypeForm form) {
        return new GetItemDescriptionTypeUseTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemDescriptionTypeUseTypes(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypesForm form) {
        return new GetItemDescriptionTypeUseTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultItemDescriptionTypeUseType(UserVisitPK userVisitPK, SetDefaultItemDescriptionTypeUseTypeForm form) {
        return new SetDefaultItemDescriptionTypeUseTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editItemDescriptionTypeUseType(UserVisitPK userVisitPK, EditItemDescriptionTypeUseTypeForm form) {
        return new EditItemDescriptionTypeUseTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteItemDescriptionTypeUseType(UserVisitPK userVisitPK, DeleteItemDescriptionTypeUseTypeForm form) {
        return new DeleteItemDescriptionTypeUseTypeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Item Description Type Use Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createItemDescriptionTypeUseTypeDescription(UserVisitPK userVisitPK, CreateItemDescriptionTypeUseTypeDescriptionForm form) {
        return new CreateItemDescriptionTypeUseTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemDescriptionTypeUseTypeDescription(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypeDescriptionForm form) {
        return new GetItemDescriptionTypeUseTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemDescriptionTypeUseTypeDescriptions(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypeDescriptionsForm form) {
        return new GetItemDescriptionTypeUseTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editItemDescriptionTypeUseTypeDescription(UserVisitPK userVisitPK, EditItemDescriptionTypeUseTypeDescriptionForm form) {
        return new EditItemDescriptionTypeUseTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteItemDescriptionTypeUseTypeDescription(UserVisitPK userVisitPK, DeleteItemDescriptionTypeUseTypeDescriptionForm form) {
        return new DeleteItemDescriptionTypeUseTypeDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Item Description Type Uses
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createItemDescriptionTypeUse(UserVisitPK userVisitPK, CreateItemDescriptionTypeUseForm form) {
        return new CreateItemDescriptionTypeUseCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemDescriptionTypeUse(UserVisitPK userVisitPK, GetItemDescriptionTypeUseForm form) {
        return new GetItemDescriptionTypeUseCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemDescriptionTypeUses(UserVisitPK userVisitPK, GetItemDescriptionTypeUsesForm form) {
        return new GetItemDescriptionTypeUsesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteItemDescriptionTypeUse(UserVisitPK userVisitPK, DeleteItemDescriptionTypeUseForm form) {
        return new DeleteItemDescriptionTypeUseCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Item Image Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createItemImageType(UserVisitPK userVisitPK, CreateItemImageTypeForm form) {
        return new CreateItemImageTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemImageTypeChoices(UserVisitPK userVisitPK, GetItemImageTypeChoicesForm form) {
        return new GetItemImageTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemImageType(UserVisitPK userVisitPK, GetItemImageTypeForm form) {
        return new GetItemImageTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemImageTypes(UserVisitPK userVisitPK, GetItemImageTypesForm form) {
        return new GetItemImageTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultItemImageType(UserVisitPK userVisitPK, SetDefaultItemImageTypeForm form) {
        return new SetDefaultItemImageTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editItemImageType(UserVisitPK userVisitPK, EditItemImageTypeForm form) {
        return new EditItemImageTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteItemImageType(UserVisitPK userVisitPK, DeleteItemImageTypeForm form) {
        return new DeleteItemImageTypeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Item Image Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createItemImageTypeDescription(UserVisitPK userVisitPK, CreateItemImageTypeDescriptionForm form) {
        return new CreateItemImageTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemImageTypeDescription(UserVisitPK userVisitPK, GetItemImageTypeDescriptionForm form) {
        return new GetItemImageTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemImageTypeDescriptions(UserVisitPK userVisitPK, GetItemImageTypeDescriptionsForm form) {
        return new GetItemImageTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editItemImageTypeDescription(UserVisitPK userVisitPK, EditItemImageTypeDescriptionForm form) {
        return new EditItemImageTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteItemImageTypeDescription(UserVisitPK userVisitPK, DeleteItemImageTypeDescriptionForm form) {
        return new DeleteItemImageTypeDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Items
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItem(UserVisitPK userVisitPK, CreateItemForm form) {
        return new CreateItemCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemStatusChoices(UserVisitPK userVisitPK, GetItemStatusChoicesForm form) {
        return new GetItemStatusChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setItemStatus(UserVisitPK userVisitPK, SetItemStatusForm form) {
        return new SetItemStatusCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItem(UserVisitPK userVisitPK, GetItemForm form) {
        return new GetItemCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editItem(UserVisitPK userVisitPK, EditItemForm form) {
        return new EditItemCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Item Unit Of Measure Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemUnitOfMeasureType(UserVisitPK userVisitPK, CreateItemUnitOfMeasureTypeForm form) {
        return new CreateItemUnitOfMeasureTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemUnitOfMeasureType(UserVisitPK userVisitPK, GetItemUnitOfMeasureTypeForm form) {
        return new GetItemUnitOfMeasureTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemUnitOfMeasureTypes(UserVisitPK userVisitPK, GetItemUnitOfMeasureTypesForm form) {
        return new GetItemUnitOfMeasureTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultItemUnitOfMeasureType(UserVisitPK userVisitPK, SetDefaultItemUnitOfMeasureTypeForm form) {
        return new SetDefaultItemUnitOfMeasureTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editItemUnitOfMeasureType(UserVisitPK userVisitPK, EditItemUnitOfMeasureTypeForm form) {
        return new EditItemUnitOfMeasureTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteItemUnitOfMeasureType(UserVisitPK userVisitPK, DeleteItemUnitOfMeasureTypeForm form) {
        return new DeleteItemUnitOfMeasureTypeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Aliases
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemAlias(UserVisitPK userVisitPK, CreateItemAliasForm form) {
        return new CreateItemAliasCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemAlias(UserVisitPK userVisitPK, GetItemAliasForm form) {
        return new GetItemAliasCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemAliases(UserVisitPK userVisitPK, GetItemAliasesForm form) {
        return new GetItemAliasesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editItemAlias(UserVisitPK userVisitPK, EditItemAliasForm form) {
        return new EditItemAliasCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteItemAlias(UserVisitPK userVisitPK, DeleteItemAliasForm form) {
        return new DeleteItemAliasCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemDescription(UserVisitPK userVisitPK, CreateItemDescriptionForm form) {
        return new CreateItemDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemDescription(UserVisitPK userVisitPK, GetItemDescriptionForm form) {
        return new GetItemDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemDescriptions(UserVisitPK userVisitPK, GetItemDescriptionsForm form) {
        return new GetItemDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editItemDescription(UserVisitPK userVisitPK, EditItemDescriptionForm form) {
        return new EditItemDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteItemDescription(UserVisitPK userVisitPK, DeleteItemDescriptionForm form) {
        return new DeleteItemDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Price Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemPriceType(UserVisitPK userVisitPK, CreateItemPriceTypeForm form) {
        return new CreateItemPriceTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemPriceType(UserVisitPK userVisitPK, GetItemPriceTypeForm form) {
        return new GetItemPriceTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemPriceTypes(UserVisitPK userVisitPK, GetItemPriceTypesForm form) {
        return new GetItemPriceTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemPriceTypeChoices(UserVisitPK userVisitPK, GetItemPriceTypeChoicesForm form) {
        return new GetItemPriceTypeChoicesCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Price Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemPriceTypeDescription(UserVisitPK userVisitPK, CreateItemPriceTypeDescriptionForm form) {
        return new CreateItemPriceTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Prices
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemPrice(UserVisitPK userVisitPK, CreateItemPriceForm form) {
        return new CreateItemPriceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemPrice(UserVisitPK userVisitPK, GetItemPriceForm form) {
        return new GetItemPriceCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemPrices(UserVisitPK userVisitPK, GetItemPricesForm form) {
        return new GetItemPricesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editItemPrice(UserVisitPK userVisitPK, EditItemPriceForm form) {
        return new EditItemPriceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteItemPrice(UserVisitPK userVisitPK, DeleteItemPriceForm form) {
        return new DeleteItemPriceCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Volumes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemVolume(UserVisitPK userVisitPK, CreateItemVolumeForm form) {
        return new CreateItemVolumeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemVolume(UserVisitPK userVisitPK, GetItemVolumeForm form) {
        return new GetItemVolumeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemVolumes(UserVisitPK userVisitPK, GetItemVolumesForm form) {
        return new GetItemVolumesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editItemVolume(UserVisitPK userVisitPK, EditItemVolumeForm form) {
        return new EditItemVolumeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteItemVolume(UserVisitPK userVisitPK, DeleteItemVolumeForm form) {
        return new DeleteItemVolumeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Weights
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemWeight(UserVisitPK userVisitPK, CreateItemWeightForm form) {
        return new CreateItemWeightCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemWeight(UserVisitPK userVisitPK, GetItemWeightForm form) {
        return new GetItemWeightCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemWeights(UserVisitPK userVisitPK, GetItemWeightsForm form) {
        return new GetItemWeightsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editItemWeight(UserVisitPK userVisitPK, EditItemWeightForm form) {
        return new EditItemWeightCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteItemWeight(UserVisitPK userVisitPK, DeleteItemWeightForm form) {
        return new DeleteItemWeightCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Country Of Origins
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemCountryOfOrigin(UserVisitPK userVisitPK, CreateItemCountryOfOriginForm form) {
        return new CreateItemCountryOfOriginCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemCountryOfOrigin(UserVisitPK userVisitPK, GetItemCountryOfOriginForm form) {
        return new GetItemCountryOfOriginCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemCountryOfOrigins(UserVisitPK userVisitPK, GetItemCountryOfOriginsForm form) {
        return new GetItemCountryOfOriginsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editItemCountryOfOrigin(UserVisitPK userVisitPK, EditItemCountryOfOriginForm form) {
        return new EditItemCountryOfOriginCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteItemCountryOfOrigin(UserVisitPK userVisitPK, DeleteItemCountryOfOriginForm form) {
        return new DeleteItemCountryOfOriginCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Kit Members
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemKitMember(UserVisitPK userVisitPK, CreateItemKitMemberForm form) {
        return new CreateItemKitMemberCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemKitMember(UserVisitPK userVisitPK, GetItemKitMemberForm form) {
        return new GetItemKitMemberCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemKitMembers(UserVisitPK userVisitPK, GetItemKitMembersForm form) {
        return new GetItemKitMembersCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteItemKitMember(UserVisitPK userVisitPK, DeleteItemKitMemberForm form) {
        return new DeleteItemKitMemberCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Pack Check Requirements
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemPackCheckRequirement(UserVisitPK userVisitPK, CreateItemPackCheckRequirementForm form) {
        return new CreateItemPackCheckRequirementCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemPackCheckRequirement(UserVisitPK userVisitPK, GetItemPackCheckRequirementForm form) {
        return new GetItemPackCheckRequirementCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemPackCheckRequirements(UserVisitPK userVisitPK, GetItemPackCheckRequirementsForm form) {
        return new GetItemPackCheckRequirementsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editItemPackCheckRequirement(UserVisitPK userVisitPK, EditItemPackCheckRequirementForm form) {
        return new EditItemPackCheckRequirementCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteItemPackCheckRequirement(UserVisitPK userVisitPK, DeleteItemPackCheckRequirementForm form) {
        return new DeleteItemPackCheckRequirementCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Shipping Times
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult getItemShippingTime(UserVisitPK userVisitPK, GetItemShippingTimeForm form) {
        return new GetItemShippingTimeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemShippingTimes(UserVisitPK userVisitPK, GetItemShippingTimesForm form) {
        return new GetItemShippingTimesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editItemShippingTime(UserVisitPK userVisitPK, EditItemShippingTimeForm form) {
        return new EditItemShippingTimeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Unit Customer Type Limits
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemUnitCustomerTypeLimit(UserVisitPK userVisitPK, CreateItemUnitCustomerTypeLimitForm form) {
        return new CreateItemUnitCustomerTypeLimitCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemUnitCustomerTypeLimit(UserVisitPK userVisitPK, GetItemUnitCustomerTypeLimitForm form) {
        return new GetItemUnitCustomerTypeLimitCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemUnitCustomerTypeLimits(UserVisitPK userVisitPK, GetItemUnitCustomerTypeLimitsForm form) {
        return new GetItemUnitCustomerTypeLimitsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editItemUnitCustomerTypeLimit(UserVisitPK userVisitPK, EditItemUnitCustomerTypeLimitForm form) {
        return new EditItemUnitCustomerTypeLimitCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteItemUnitCustomerTypeLimit(UserVisitPK userVisitPK, DeleteItemUnitCustomerTypeLimitForm form) {
        return new DeleteItemUnitCustomerTypeLimitCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Item Unit Limits
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemUnitLimit(UserVisitPK userVisitPK, CreateItemUnitLimitForm form) {
        return new CreateItemUnitLimitCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemUnitLimit(UserVisitPK userVisitPK, GetItemUnitLimitForm form) {
        return new GetItemUnitLimitCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemUnitLimits(UserVisitPK userVisitPK, GetItemUnitLimitsForm form) {
        return new GetItemUnitLimitsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editItemUnitLimit(UserVisitPK userVisitPK, EditItemUnitLimitForm form) {
        return new EditItemUnitLimitCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteItemUnitLimit(UserVisitPK userVisitPK, DeleteItemUnitLimitForm form) {
        return new DeleteItemUnitLimitCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Item Unit Price Limits
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemUnitPriceLimit(UserVisitPK userVisitPK, CreateItemUnitPriceLimitForm form) {
        return new CreateItemUnitPriceLimitCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemUnitPriceLimit(UserVisitPK userVisitPK, GetItemUnitPriceLimitForm form) {
        return new GetItemUnitPriceLimitCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemUnitPriceLimits(UserVisitPK userVisitPK, GetItemUnitPriceLimitsForm form) {
        return new GetItemUnitPriceLimitsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editItemUnitPriceLimit(UserVisitPK userVisitPK, EditItemUnitPriceLimitForm form) {
        return new EditItemUnitPriceLimitCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteItemUnitPriceLimit(UserVisitPK userVisitPK, DeleteItemUnitPriceLimitForm form) {
        return new DeleteItemUnitPriceLimitCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Related Item Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createRelatedItemType(UserVisitPK userVisitPK, CreateRelatedItemTypeForm form) {
        return new CreateRelatedItemTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getRelatedItemTypeChoices(UserVisitPK userVisitPK, GetRelatedItemTypeChoicesForm form) {
        return new GetRelatedItemTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getRelatedItemType(UserVisitPK userVisitPK, GetRelatedItemTypeForm form) {
        return new GetRelatedItemTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getRelatedItemTypes(UserVisitPK userVisitPK, GetRelatedItemTypesForm form) {
        return new GetRelatedItemTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultRelatedItemType(UserVisitPK userVisitPK, SetDefaultRelatedItemTypeForm form) {
        return new SetDefaultRelatedItemTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editRelatedItemType(UserVisitPK userVisitPK, EditRelatedItemTypeForm form) {
        return new EditRelatedItemTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteRelatedItemType(UserVisitPK userVisitPK, DeleteRelatedItemTypeForm form) {
        return new DeleteRelatedItemTypeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Related Item Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createRelatedItemTypeDescription(UserVisitPK userVisitPK, CreateRelatedItemTypeDescriptionForm form) {
        return new CreateRelatedItemTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getRelatedItemTypeDescription(UserVisitPK userVisitPK, GetRelatedItemTypeDescriptionForm form) {
        return new GetRelatedItemTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getRelatedItemTypeDescriptions(UserVisitPK userVisitPK, GetRelatedItemTypeDescriptionsForm form) {
        return new GetRelatedItemTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editRelatedItemTypeDescription(UserVisitPK userVisitPK, EditRelatedItemTypeDescriptionForm form) {
        return new EditRelatedItemTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteRelatedItemTypeDescription(UserVisitPK userVisitPK, DeleteRelatedItemTypeDescriptionForm form) {
        return new DeleteRelatedItemTypeDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Related Items
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createRelatedItem(UserVisitPK userVisitPK, CreateRelatedItemForm form) {
        return new CreateRelatedItemCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getRelatedItem(UserVisitPK userVisitPK, GetRelatedItemForm form) {
        return new GetRelatedItemCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getRelatedItems(UserVisitPK userVisitPK, GetRelatedItemsForm form) {
        return new GetRelatedItemsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editRelatedItem(UserVisitPK userVisitPK, EditRelatedItemForm form) {
        return new EditRelatedItemCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteRelatedItem(UserVisitPK userVisitPK, DeleteRelatedItemForm form) {
        return new DeleteRelatedItemCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Codes
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeForm form) {
        return new CreateHarmonizedTariffScheduleCodeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodes(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodesForm form) {
        return new GetHarmonizedTariffScheduleCodesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeForm form) {
        return new GetHarmonizedTariffScheduleCodeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeChoices(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeChoicesForm form) {
        return new GetHarmonizedTariffScheduleCodeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, SetDefaultHarmonizedTariffScheduleCodeForm form) {
        return new SetDefaultHarmonizedTariffScheduleCodeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeForm form) {
        return new EditHarmonizedTariffScheduleCodeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeForm form) {
        return new DeleteHarmonizedTariffScheduleCodeCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createHarmonizedTariffScheduleCodeTranslation(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeTranslationForm form) {
        return new CreateHarmonizedTariffScheduleCodeTranslationCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeTranslations(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeTranslationsForm form) {
        return new GetHarmonizedTariffScheduleCodeTranslationsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeTranslation(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeTranslationForm form) {
        return new GetHarmonizedTariffScheduleCodeTranslationCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editHarmonizedTariffScheduleCodeTranslation(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeTranslationForm form) {
        return new EditHarmonizedTariffScheduleCodeTranslationCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteHarmonizedTariffScheduleCodeTranslation(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeTranslationForm form) {
        return new DeleteHarmonizedTariffScheduleCodeTranslationCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Units
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUnitForm form) {
        return new CreateHarmonizedTariffScheduleCodeUnitCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUnits(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitsForm form) {
        return new GetHarmonizedTariffScheduleCodeUnitsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitForm form) {
        return new GetHarmonizedTariffScheduleCodeUnitCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUnitChoices(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitChoicesForm form) {
        return new GetHarmonizedTariffScheduleCodeUnitChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, SetDefaultHarmonizedTariffScheduleCodeUnitForm form) {
        return new SetDefaultHarmonizedTariffScheduleCodeUnitCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeUnitForm form) {
        return new EditHarmonizedTariffScheduleCodeUnitCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUnitForm form) {
        return new DeleteHarmonizedTariffScheduleCodeUnitCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Unit Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createHarmonizedTariffScheduleCodeUnitDescription(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUnitDescriptionForm form) {
        return new CreateHarmonizedTariffScheduleCodeUnitDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUnitDescriptions(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitDescriptionsForm form) {
        return new GetHarmonizedTariffScheduleCodeUnitDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUnitDescription(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitDescriptionForm form) {
        return new GetHarmonizedTariffScheduleCodeUnitDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editHarmonizedTariffScheduleCodeUnitDescription(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeUnitDescriptionForm form) {
        return new EditHarmonizedTariffScheduleCodeUnitDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteHarmonizedTariffScheduleCodeUnitDescription(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUnitDescriptionForm form) {
        return new DeleteHarmonizedTariffScheduleCodeUnitDescriptionCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Use Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUseTypeForm form) {
        return new CreateHarmonizedTariffScheduleCodeUseTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUseTypes(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypesForm form) {
        return new GetHarmonizedTariffScheduleCodeUseTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypeForm form) {
        return new GetHarmonizedTariffScheduleCodeUseTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUseTypeChoices(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypeChoicesForm form) {
        return new GetHarmonizedTariffScheduleCodeUseTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, SetDefaultHarmonizedTariffScheduleCodeUseTypeForm form) {
        return new SetDefaultHarmonizedTariffScheduleCodeUseTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeUseTypeForm form) {
        return new EditHarmonizedTariffScheduleCodeUseTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUseTypeForm form) {
        return new DeleteHarmonizedTariffScheduleCodeUseTypeCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Use Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createHarmonizedTariffScheduleCodeUseTypeDescription(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUseTypeDescriptionForm form) {
        return new CreateHarmonizedTariffScheduleCodeUseTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUseTypeDescriptions(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypeDescriptionsForm form) {
        return new GetHarmonizedTariffScheduleCodeUseTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUseTypeDescription(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypeDescriptionForm form) {
        return new GetHarmonizedTariffScheduleCodeUseTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editHarmonizedTariffScheduleCodeUseTypeDescription(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeUseTypeDescriptionForm form) {
        return new EditHarmonizedTariffScheduleCodeUseTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteHarmonizedTariffScheduleCodeUseTypeDescription(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUseTypeDescriptionForm form) {
        return new DeleteHarmonizedTariffScheduleCodeUseTypeDescriptionCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Uses
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createHarmonizedTariffScheduleCodeUse(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUseForm form) {
        return new CreateHarmonizedTariffScheduleCodeUseCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUses(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUsesForm form) {
        return new GetHarmonizedTariffScheduleCodeUsesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUse(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseForm form) {
        return new GetHarmonizedTariffScheduleCodeUseCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteHarmonizedTariffScheduleCodeUse(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUseForm form) {
        return new DeleteHarmonizedTariffScheduleCodeUseCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Item Harmonized Tariff Schedule Codes
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createItemHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, CreateItemHarmonizedTariffScheduleCodeForm form) {
        return new CreateItemHarmonizedTariffScheduleCodeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemHarmonizedTariffScheduleCodes(UserVisitPK userVisitPK, GetItemHarmonizedTariffScheduleCodesForm form) {
        return new GetItemHarmonizedTariffScheduleCodesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, GetItemHarmonizedTariffScheduleCodeForm form) {
        return new GetItemHarmonizedTariffScheduleCodeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editItemHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, EditItemHarmonizedTariffScheduleCodeForm form) {
        return new EditItemHarmonizedTariffScheduleCodeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteItemHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, DeleteItemHarmonizedTariffScheduleCodeForm form) {
        return new DeleteItemHarmonizedTariffScheduleCodeCommand(userVisitPK, form).run();
    }

}

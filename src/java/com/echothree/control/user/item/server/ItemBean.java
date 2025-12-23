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
    public CommandResult createItemType(UserVisitPK userVisitPK, CreateItemTypeForm form) {
        return CDI.current().select(CreateItemTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemType(UserVisitPK userVisitPK, GetItemTypeForm form) {
        return CDI.current().select(GetItemTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemTypes(UserVisitPK userVisitPK, GetItemTypesForm form) {
        return CDI.current().select(GetItemTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemTypeChoices(UserVisitPK userVisitPK, GetItemTypeChoicesForm form) {
        return CDI.current().select(GetItemTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemTypeDescription(UserVisitPK userVisitPK, CreateItemTypeDescriptionForm form) {
        return CDI.current().select(CreateItemTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Delivery Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemDeliveryType(UserVisitPK userVisitPK, CreateItemDeliveryTypeForm form) {
        return CDI.current().select(CreateItemDeliveryTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemDeliveryType(UserVisitPK userVisitPK, GetItemDeliveryTypeForm form) {
        return CDI.current().select(GetItemDeliveryTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemDeliveryTypes(UserVisitPK userVisitPK, GetItemDeliveryTypesForm form) {
        return CDI.current().select(GetItemDeliveryTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemDeliveryTypeChoices(UserVisitPK userVisitPK, GetItemDeliveryTypeChoicesForm form) {
        return CDI.current().select(GetItemDeliveryTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Delivery Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemDeliveryTypeDescription(UserVisitPK userVisitPK, CreateItemDeliveryTypeDescriptionForm form) {
        return CDI.current().select(CreateItemDeliveryTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Inventory Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemInventoryType(UserVisitPK userVisitPK, CreateItemInventoryTypeForm form) {
        return CDI.current().select(CreateItemInventoryTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemInventoryType(UserVisitPK userVisitPK, GetItemInventoryTypeForm form) {
        return CDI.current().select(GetItemInventoryTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemInventoryTypes(UserVisitPK userVisitPK, GetItemInventoryTypesForm form) {
        return CDI.current().select(GetItemInventoryTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemInventoryTypeChoices(UserVisitPK userVisitPK, GetItemInventoryTypeChoicesForm form) {
        return CDI.current().select(GetItemInventoryTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Inventory Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemInventoryTypeDescription(UserVisitPK userVisitPK, CreateItemInventoryTypeDescriptionForm form) {
        return CDI.current().select(CreateItemInventoryTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Use Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemUseType(UserVisitPK userVisitPK, CreateItemUseTypeForm form) {
        return CDI.current().select(CreateItemUseTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemUseType(UserVisitPK userVisitPK, GetItemUseTypeForm form) {
        return CDI.current().select(GetItemUseTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemUseTypes(UserVisitPK userVisitPK, GetItemUseTypesForm form) {
        return CDI.current().select(GetItemUseTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemUseTypeChoices(UserVisitPK userVisitPK, GetItemUseTypeChoicesForm form) {
        return CDI.current().select(GetItemUseTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemUseTypeDescription(UserVisitPK userVisitPK, CreateItemUseTypeDescriptionForm form) {
        return CDI.current().select(CreateItemUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Categories
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemCategory(UserVisitPK userVisitPK, CreateItemCategoryForm form) {
        return CDI.current().select(CreateItemCategoryCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemCategoryChoices(UserVisitPK userVisitPK, GetItemCategoryChoicesForm form) {
        return CDI.current().select(GetItemCategoryChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemCategory(UserVisitPK userVisitPK, GetItemCategoryForm form) {
        return CDI.current().select(GetItemCategoryCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemCategories(UserVisitPK userVisitPK, GetItemCategoriesForm form) {
        return CDI.current().select(GetItemCategoriesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultItemCategory(UserVisitPK userVisitPK, SetDefaultItemCategoryForm form) {
        return CDI.current().select(SetDefaultItemCategoryCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemCategory(UserVisitPK userVisitPK, EditItemCategoryForm form) {
        return CDI.current().select(EditItemCategoryCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemCategory(UserVisitPK userVisitPK, DeleteItemCategoryForm form) {
        return CDI.current().select(DeleteItemCategoryCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Category Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemCategoryDescription(UserVisitPK userVisitPK, CreateItemCategoryDescriptionForm form) {
        return CDI.current().select(CreateItemCategoryDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemCategoryDescription(UserVisitPK userVisitPK, GetItemCategoryDescriptionForm form) {
        return CDI.current().select(GetItemCategoryDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemCategoryDescriptions(UserVisitPK userVisitPK, GetItemCategoryDescriptionsForm form) {
        return CDI.current().select(GetItemCategoryDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemCategoryDescription(UserVisitPK userVisitPK, EditItemCategoryDescriptionForm form) {
        return CDI.current().select(EditItemCategoryDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemCategoryDescription(UserVisitPK userVisitPK, DeleteItemCategoryDescriptionForm form) {
        return CDI.current().select(DeleteItemCategoryDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Item Alias Checksum Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createItemAliasChecksumType(UserVisitPK userVisitPK, CreateItemAliasChecksumTypeForm form) {
        return CDI.current().select(CreateItemAliasChecksumTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemAliasChecksumTypes(UserVisitPK userVisitPK, GetItemAliasChecksumTypesForm form) {
        return CDI.current().select(GetItemAliasChecksumTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemAliasChecksumType(UserVisitPK userVisitPK, GetItemAliasChecksumTypeForm form) {
        return CDI.current().select(GetItemAliasChecksumTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemAliasChecksumTypeChoices(UserVisitPK userVisitPK, GetItemAliasChecksumTypeChoicesForm form) {
        return CDI.current().select(GetItemAliasChecksumTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Item Alias Checksum Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createItemAliasChecksumTypeDescription(UserVisitPK userVisitPK, CreateItemAliasChecksumTypeDescriptionForm form) {
        return CDI.current().select(CreateItemAliasChecksumTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Alias Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemAliasType(UserVisitPK userVisitPK, CreateItemAliasTypeForm form) {
        return CDI.current().select(CreateItemAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemAliasTypeChoices(UserVisitPK userVisitPK, GetItemAliasTypeChoicesForm form) {
        return CDI.current().select(GetItemAliasTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemAliasType(UserVisitPK userVisitPK, GetItemAliasTypeForm form) {
        return CDI.current().select(GetItemAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemAliasTypes(UserVisitPK userVisitPK, GetItemAliasTypesForm form) {
        return CDI.current().select(GetItemAliasTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultItemAliasType(UserVisitPK userVisitPK, SetDefaultItemAliasTypeForm form) {
        return CDI.current().select(SetDefaultItemAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemAliasType(UserVisitPK userVisitPK, EditItemAliasTypeForm form) {
        return CDI.current().select(EditItemAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemAliasType(UserVisitPK userVisitPK, DeleteItemAliasTypeForm form) {
        return CDI.current().select(DeleteItemAliasTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Alias Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemAliasTypeDescription(UserVisitPK userVisitPK, CreateItemAliasTypeDescriptionForm form) {
        return CDI.current().select(CreateItemAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemAliasTypeDescription(UserVisitPK userVisitPK, GetItemAliasTypeDescriptionForm form) {
        return CDI.current().select(GetItemAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemAliasTypeDescriptions(UserVisitPK userVisitPK, GetItemAliasTypeDescriptionsForm form) {
        return CDI.current().select(GetItemAliasTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemAliasTypeDescription(UserVisitPK userVisitPK, EditItemAliasTypeDescriptionForm form) {
        return CDI.current().select(EditItemAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemAliasTypeDescription(UserVisitPK userVisitPK, DeleteItemAliasTypeDescriptionForm form) {
        return CDI.current().select(DeleteItemAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Description Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createItemDescriptionType(UserVisitPK userVisitPK, CreateItemDescriptionTypeForm form) {
        return CDI.current().select(CreateItemDescriptionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemDescriptionTypeChoices(UserVisitPK userVisitPK, GetItemDescriptionTypeChoicesForm form) {
        return CDI.current().select(GetItemDescriptionTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemDescriptionType(UserVisitPK userVisitPK, GetItemDescriptionTypeForm form) {
        return CDI.current().select(GetItemDescriptionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemDescriptionTypes(UserVisitPK userVisitPK, GetItemDescriptionTypesForm form) {
        return CDI.current().select(GetItemDescriptionTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultItemDescriptionType(UserVisitPK userVisitPK, SetDefaultItemDescriptionTypeForm form) {
        return CDI.current().select(SetDefaultItemDescriptionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemDescriptionType(UserVisitPK userVisitPK, EditItemDescriptionTypeForm form) {
        return CDI.current().select(EditItemDescriptionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemDescriptionType(UserVisitPK userVisitPK, DeleteItemDescriptionTypeForm form) {
        return CDI.current().select(DeleteItemDescriptionTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Description Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createItemDescriptionTypeDescription(UserVisitPK userVisitPK, CreateItemDescriptionTypeDescriptionForm form) {
        return CDI.current().select(CreateItemDescriptionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemDescriptionTypeDescription(UserVisitPK userVisitPK, GetItemDescriptionTypeDescriptionForm form) {
        return CDI.current().select(GetItemDescriptionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemDescriptionTypeDescriptions(UserVisitPK userVisitPK, GetItemDescriptionTypeDescriptionsForm form) {
        return CDI.current().select(GetItemDescriptionTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemDescriptionTypeDescription(UserVisitPK userVisitPK, EditItemDescriptionTypeDescriptionForm form) {
        return CDI.current().select(EditItemDescriptionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemDescriptionTypeDescription(UserVisitPK userVisitPK, DeleteItemDescriptionTypeDescriptionForm form) {
        return CDI.current().select(DeleteItemDescriptionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Description Type Use Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createItemDescriptionTypeUseType(UserVisitPK userVisitPK, CreateItemDescriptionTypeUseTypeForm form) {
        return CDI.current().select(CreateItemDescriptionTypeUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemDescriptionTypeUseTypeChoices(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypeChoicesForm form) {
        return CDI.current().select(GetItemDescriptionTypeUseTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemDescriptionTypeUseType(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypeForm form) {
        return CDI.current().select(GetItemDescriptionTypeUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemDescriptionTypeUseTypes(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypesForm form) {
        return CDI.current().select(GetItemDescriptionTypeUseTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultItemDescriptionTypeUseType(UserVisitPK userVisitPK, SetDefaultItemDescriptionTypeUseTypeForm form) {
        return CDI.current().select(SetDefaultItemDescriptionTypeUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemDescriptionTypeUseType(UserVisitPK userVisitPK, EditItemDescriptionTypeUseTypeForm form) {
        return CDI.current().select(EditItemDescriptionTypeUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemDescriptionTypeUseType(UserVisitPK userVisitPK, DeleteItemDescriptionTypeUseTypeForm form) {
        return CDI.current().select(DeleteItemDescriptionTypeUseTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Description Type Use Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createItemDescriptionTypeUseTypeDescription(UserVisitPK userVisitPK, CreateItemDescriptionTypeUseTypeDescriptionForm form) {
        return CDI.current().select(CreateItemDescriptionTypeUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemDescriptionTypeUseTypeDescription(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypeDescriptionForm form) {
        return CDI.current().select(GetItemDescriptionTypeUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemDescriptionTypeUseTypeDescriptions(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypeDescriptionsForm form) {
        return CDI.current().select(GetItemDescriptionTypeUseTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemDescriptionTypeUseTypeDescription(UserVisitPK userVisitPK, EditItemDescriptionTypeUseTypeDescriptionForm form) {
        return CDI.current().select(EditItemDescriptionTypeUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemDescriptionTypeUseTypeDescription(UserVisitPK userVisitPK, DeleteItemDescriptionTypeUseTypeDescriptionForm form) {
        return CDI.current().select(DeleteItemDescriptionTypeUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Description Type Uses
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createItemDescriptionTypeUse(UserVisitPK userVisitPK, CreateItemDescriptionTypeUseForm form) {
        return CDI.current().select(CreateItemDescriptionTypeUseCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemDescriptionTypeUse(UserVisitPK userVisitPK, GetItemDescriptionTypeUseForm form) {
        return CDI.current().select(GetItemDescriptionTypeUseCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemDescriptionTypeUses(UserVisitPK userVisitPK, GetItemDescriptionTypeUsesForm form) {
        return CDI.current().select(GetItemDescriptionTypeUsesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemDescriptionTypeUse(UserVisitPK userVisitPK, DeleteItemDescriptionTypeUseForm form) {
        return CDI.current().select(DeleteItemDescriptionTypeUseCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Image Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createItemImageType(UserVisitPK userVisitPK, CreateItemImageTypeForm form) {
        return CDI.current().select(CreateItemImageTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemImageTypeChoices(UserVisitPK userVisitPK, GetItemImageTypeChoicesForm form) {
        return CDI.current().select(GetItemImageTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemImageType(UserVisitPK userVisitPK, GetItemImageTypeForm form) {
        return CDI.current().select(GetItemImageTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemImageTypes(UserVisitPK userVisitPK, GetItemImageTypesForm form) {
        return CDI.current().select(GetItemImageTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultItemImageType(UserVisitPK userVisitPK, SetDefaultItemImageTypeForm form) {
        return CDI.current().select(SetDefaultItemImageTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemImageType(UserVisitPK userVisitPK, EditItemImageTypeForm form) {
        return CDI.current().select(EditItemImageTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemImageType(UserVisitPK userVisitPK, DeleteItemImageTypeForm form) {
        return CDI.current().select(DeleteItemImageTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Image Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createItemImageTypeDescription(UserVisitPK userVisitPK, CreateItemImageTypeDescriptionForm form) {
        return CDI.current().select(CreateItemImageTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemImageTypeDescription(UserVisitPK userVisitPK, GetItemImageTypeDescriptionForm form) {
        return CDI.current().select(GetItemImageTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemImageTypeDescriptions(UserVisitPK userVisitPK, GetItemImageTypeDescriptionsForm form) {
        return CDI.current().select(GetItemImageTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemImageTypeDescription(UserVisitPK userVisitPK, EditItemImageTypeDescriptionForm form) {
        return CDI.current().select(EditItemImageTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemImageTypeDescription(UserVisitPK userVisitPK, DeleteItemImageTypeDescriptionForm form) {
        return CDI.current().select(DeleteItemImageTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Items
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItem(UserVisitPK userVisitPK, CreateItemForm form) {
        return CDI.current().select(CreateItemCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemStatusChoices(UserVisitPK userVisitPK, GetItemStatusChoicesForm form) {
        return CDI.current().select(GetItemStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setItemStatus(UserVisitPK userVisitPK, SetItemStatusForm form) {
        return CDI.current().select(SetItemStatusCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItem(UserVisitPK userVisitPK, GetItemForm form) {
        return CDI.current().select(GetItemCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItems(UserVisitPK userVisitPK, GetItemsForm form) {
        return CDI.current().select(GetItemsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItem(UserVisitPK userVisitPK, EditItemForm form) {
        return CDI.current().select(EditItemCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Item Unit Of Measure Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemUnitOfMeasureType(UserVisitPK userVisitPK, CreateItemUnitOfMeasureTypeForm form) {
        return CDI.current().select(CreateItemUnitOfMeasureTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemUnitOfMeasureType(UserVisitPK userVisitPK, GetItemUnitOfMeasureTypeForm form) {
        return CDI.current().select(GetItemUnitOfMeasureTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemUnitOfMeasureTypes(UserVisitPK userVisitPK, GetItemUnitOfMeasureTypesForm form) {
        return CDI.current().select(GetItemUnitOfMeasureTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultItemUnitOfMeasureType(UserVisitPK userVisitPK, SetDefaultItemUnitOfMeasureTypeForm form) {
        return CDI.current().select(SetDefaultItemUnitOfMeasureTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editItemUnitOfMeasureType(UserVisitPK userVisitPK, EditItemUnitOfMeasureTypeForm form) {
        return CDI.current().select(EditItemUnitOfMeasureTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteItemUnitOfMeasureType(UserVisitPK userVisitPK, DeleteItemUnitOfMeasureTypeForm form) {
        return CDI.current().select(DeleteItemUnitOfMeasureTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Aliases
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemAlias(UserVisitPK userVisitPK, CreateItemAliasForm form) {
        return CDI.current().select(CreateItemAliasCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemAlias(UserVisitPK userVisitPK, GetItemAliasForm form) {
        return CDI.current().select(GetItemAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemAliases(UserVisitPK userVisitPK, GetItemAliasesForm form) {
        return CDI.current().select(GetItemAliasesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemAlias(UserVisitPK userVisitPK, EditItemAliasForm form) {
        return CDI.current().select(EditItemAliasCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteItemAlias(UserVisitPK userVisitPK, DeleteItemAliasForm form) {
        return CDI.current().select(DeleteItemAliasCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemDescription(UserVisitPK userVisitPK, CreateItemDescriptionForm form) {
        return CDI.current().select(CreateItemDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemDescription(UserVisitPK userVisitPK, GetItemDescriptionForm form) {
        return CDI.current().select(GetItemDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemDescriptions(UserVisitPK userVisitPK, GetItemDescriptionsForm form) {
        return CDI.current().select(GetItemDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editItemDescription(UserVisitPK userVisitPK, EditItemDescriptionForm form) {
        return CDI.current().select(EditItemDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteItemDescription(UserVisitPK userVisitPK, DeleteItemDescriptionForm form) {
        return CDI.current().select(DeleteItemDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Price Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemPriceType(UserVisitPK userVisitPK, CreateItemPriceTypeForm form) {
        return CDI.current().select(CreateItemPriceTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemPriceType(UserVisitPK userVisitPK, GetItemPriceTypeForm form) {
        return CDI.current().select(GetItemPriceTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemPriceTypes(UserVisitPK userVisitPK, GetItemPriceTypesForm form) {
        return CDI.current().select(GetItemPriceTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemPriceTypeChoices(UserVisitPK userVisitPK, GetItemPriceTypeChoicesForm form) {
        return CDI.current().select(GetItemPriceTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Price Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemPriceTypeDescription(UserVisitPK userVisitPK, CreateItemPriceTypeDescriptionForm form) {
        return CDI.current().select(CreateItemPriceTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Prices
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemPrice(UserVisitPK userVisitPK, CreateItemPriceForm form) {
        return CDI.current().select(CreateItemPriceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemPrice(UserVisitPK userVisitPK, GetItemPriceForm form) {
        return CDI.current().select(GetItemPriceCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemPrices(UserVisitPK userVisitPK, GetItemPricesForm form) {
        return CDI.current().select(GetItemPricesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemPrice(UserVisitPK userVisitPK, EditItemPriceForm form) {
        return CDI.current().select(EditItemPriceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteItemPrice(UserVisitPK userVisitPK, DeleteItemPriceForm form) {
        return CDI.current().select(DeleteItemPriceCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Volume Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createItemVolumeType(UserVisitPK userVisitPK, CreateItemVolumeTypeForm form) {
        return CDI.current().select(CreateItemVolumeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemVolumeTypeChoices(UserVisitPK userVisitPK, GetItemVolumeTypeChoicesForm form) {
        return CDI.current().select(GetItemVolumeTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemVolumeType(UserVisitPK userVisitPK, GetItemVolumeTypeForm form) {
        return CDI.current().select(GetItemVolumeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemVolumeTypes(UserVisitPK userVisitPK, GetItemVolumeTypesForm form) {
        return CDI.current().select(GetItemVolumeTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultItemVolumeType(UserVisitPK userVisitPK, SetDefaultItemVolumeTypeForm form) {
        return CDI.current().select(SetDefaultItemVolumeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemVolumeType(UserVisitPK userVisitPK, EditItemVolumeTypeForm form) {
        return CDI.current().select(EditItemVolumeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemVolumeType(UserVisitPK userVisitPK, DeleteItemVolumeTypeForm form) {
        return CDI.current().select(DeleteItemVolumeTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Volume Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createItemVolumeTypeDescription(UserVisitPK userVisitPK, CreateItemVolumeTypeDescriptionForm form) {
        return CDI.current().select(CreateItemVolumeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemVolumeTypeDescription(UserVisitPK userVisitPK, GetItemVolumeTypeDescriptionForm form) {
        return CDI.current().select(GetItemVolumeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemVolumeTypeDescriptions(UserVisitPK userVisitPK, GetItemVolumeTypeDescriptionsForm form) {
        return CDI.current().select(GetItemVolumeTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemVolumeTypeDescription(UserVisitPK userVisitPK, EditItemVolumeTypeDescriptionForm form) {
        return CDI.current().select(EditItemVolumeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemVolumeTypeDescription(UserVisitPK userVisitPK, DeleteItemVolumeTypeDescriptionForm form) {
        return CDI.current().select(DeleteItemVolumeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Volumes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemVolume(UserVisitPK userVisitPK, CreateItemVolumeForm form) {
        return CDI.current().select(CreateItemVolumeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemVolume(UserVisitPK userVisitPK, GetItemVolumeForm form) {
        return CDI.current().select(GetItemVolumeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemVolumes(UserVisitPK userVisitPK, GetItemVolumesForm form) {
        return CDI.current().select(GetItemVolumesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemVolume(UserVisitPK userVisitPK, EditItemVolumeForm form) {
        return CDI.current().select(EditItemVolumeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteItemVolume(UserVisitPK userVisitPK, DeleteItemVolumeForm form) {
        return CDI.current().select(DeleteItemVolumeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Weight Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createItemWeightType(UserVisitPK userVisitPK, CreateItemWeightTypeForm form) {
        return CDI.current().select(CreateItemWeightTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemWeightTypeChoices(UserVisitPK userVisitPK, GetItemWeightTypeChoicesForm form) {
        return CDI.current().select(GetItemWeightTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemWeightType(UserVisitPK userVisitPK, GetItemWeightTypeForm form) {
        return CDI.current().select(GetItemWeightTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemWeightTypes(UserVisitPK userVisitPK, GetItemWeightTypesForm form) {
        return CDI.current().select(GetItemWeightTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultItemWeightType(UserVisitPK userVisitPK, SetDefaultItemWeightTypeForm form) {
        return CDI.current().select(SetDefaultItemWeightTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemWeightType(UserVisitPK userVisitPK, EditItemWeightTypeForm form) {
        return CDI.current().select(EditItemWeightTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemWeightType(UserVisitPK userVisitPK, DeleteItemWeightTypeForm form) {
        return CDI.current().select(DeleteItemWeightTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Weight Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createItemWeightTypeDescription(UserVisitPK userVisitPK, CreateItemWeightTypeDescriptionForm form) {
        return CDI.current().select(CreateItemWeightTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemWeightTypeDescription(UserVisitPK userVisitPK, GetItemWeightTypeDescriptionForm form) {
        return CDI.current().select(GetItemWeightTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemWeightTypeDescriptions(UserVisitPK userVisitPK, GetItemWeightTypeDescriptionsForm form) {
        return CDI.current().select(GetItemWeightTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemWeightTypeDescription(UserVisitPK userVisitPK, EditItemWeightTypeDescriptionForm form) {
        return CDI.current().select(EditItemWeightTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemWeightTypeDescription(UserVisitPK userVisitPK, DeleteItemWeightTypeDescriptionForm form) {
        return CDI.current().select(DeleteItemWeightTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Weights
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemWeight(UserVisitPK userVisitPK, CreateItemWeightForm form) {
        return CDI.current().select(CreateItemWeightCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemWeight(UserVisitPK userVisitPK, GetItemWeightForm form) {
        return CDI.current().select(GetItemWeightCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemWeights(UserVisitPK userVisitPK, GetItemWeightsForm form) {
        return CDI.current().select(GetItemWeightsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemWeight(UserVisitPK userVisitPK, EditItemWeightForm form) {
        return CDI.current().select(EditItemWeightCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteItemWeight(UserVisitPK userVisitPK, DeleteItemWeightForm form) {
        return CDI.current().select(DeleteItemWeightCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Country Of Origins
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemCountryOfOrigin(UserVisitPK userVisitPK, CreateItemCountryOfOriginForm form) {
        return CDI.current().select(CreateItemCountryOfOriginCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemCountryOfOrigin(UserVisitPK userVisitPK, GetItemCountryOfOriginForm form) {
        return CDI.current().select(GetItemCountryOfOriginCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemCountryOfOrigins(UserVisitPK userVisitPK, GetItemCountryOfOriginsForm form) {
        return CDI.current().select(GetItemCountryOfOriginsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemCountryOfOrigin(UserVisitPK userVisitPK, EditItemCountryOfOriginForm form) {
        return CDI.current().select(EditItemCountryOfOriginCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteItemCountryOfOrigin(UserVisitPK userVisitPK, DeleteItemCountryOfOriginForm form) {
        return CDI.current().select(DeleteItemCountryOfOriginCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Kit Members
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemKitMember(UserVisitPK userVisitPK, CreateItemKitMemberForm form) {
        return CDI.current().select(CreateItemKitMemberCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemKitMember(UserVisitPK userVisitPK, GetItemKitMemberForm form) {
        return CDI.current().select(GetItemKitMemberCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemKitMembers(UserVisitPK userVisitPK, GetItemKitMembersForm form) {
        return CDI.current().select(GetItemKitMembersCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemKitMember(UserVisitPK userVisitPK, DeleteItemKitMemberForm form) {
        return CDI.current().select(DeleteItemKitMemberCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Pack Check Requirements
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemPackCheckRequirement(UserVisitPK userVisitPK, CreateItemPackCheckRequirementForm form) {
        return CDI.current().select(CreateItemPackCheckRequirementCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemPackCheckRequirement(UserVisitPK userVisitPK, GetItemPackCheckRequirementForm form) {
        return CDI.current().select(GetItemPackCheckRequirementCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemPackCheckRequirements(UserVisitPK userVisitPK, GetItemPackCheckRequirementsForm form) {
        return CDI.current().select(GetItemPackCheckRequirementsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemPackCheckRequirement(UserVisitPK userVisitPK, EditItemPackCheckRequirementForm form) {
        return CDI.current().select(EditItemPackCheckRequirementCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemPackCheckRequirement(UserVisitPK userVisitPK, DeleteItemPackCheckRequirementForm form) {
        return CDI.current().select(DeleteItemPackCheckRequirementCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Shipping Times
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult getItemShippingTime(UserVisitPK userVisitPK, GetItemShippingTimeForm form) {
        return CDI.current().select(GetItemShippingTimeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemShippingTimes(UserVisitPK userVisitPK, GetItemShippingTimesForm form) {
        return CDI.current().select(GetItemShippingTimesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemShippingTime(UserVisitPK userVisitPK, EditItemShippingTimeForm form) {
        return CDI.current().select(EditItemShippingTimeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Unit Customer Type Limits
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemUnitCustomerTypeLimit(UserVisitPK userVisitPK, CreateItemUnitCustomerTypeLimitForm form) {
        return CDI.current().select(CreateItemUnitCustomerTypeLimitCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemUnitCustomerTypeLimit(UserVisitPK userVisitPK, GetItemUnitCustomerTypeLimitForm form) {
        return CDI.current().select(GetItemUnitCustomerTypeLimitCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemUnitCustomerTypeLimits(UserVisitPK userVisitPK, GetItemUnitCustomerTypeLimitsForm form) {
        return CDI.current().select(GetItemUnitCustomerTypeLimitsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemUnitCustomerTypeLimit(UserVisitPK userVisitPK, EditItemUnitCustomerTypeLimitForm form) {
        return CDI.current().select(EditItemUnitCustomerTypeLimitCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemUnitCustomerTypeLimit(UserVisitPK userVisitPK, DeleteItemUnitCustomerTypeLimitForm form) {
        return CDI.current().select(DeleteItemUnitCustomerTypeLimitCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Unit Limits
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemUnitLimit(UserVisitPK userVisitPK, CreateItemUnitLimitForm form) {
        return CDI.current().select(CreateItemUnitLimitCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemUnitLimit(UserVisitPK userVisitPK, GetItemUnitLimitForm form) {
        return CDI.current().select(GetItemUnitLimitCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemUnitLimits(UserVisitPK userVisitPK, GetItemUnitLimitsForm form) {
        return CDI.current().select(GetItemUnitLimitsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemUnitLimit(UserVisitPK userVisitPK, EditItemUnitLimitForm form) {
        return CDI.current().select(EditItemUnitLimitCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemUnitLimit(UserVisitPK userVisitPK, DeleteItemUnitLimitForm form) {
        return CDI.current().select(DeleteItemUnitLimitCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Item Unit Price Limits
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createItemUnitPriceLimit(UserVisitPK userVisitPK, CreateItemUnitPriceLimitForm form) {
        return CDI.current().select(CreateItemUnitPriceLimitCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemUnitPriceLimit(UserVisitPK userVisitPK, GetItemUnitPriceLimitForm form) {
        return CDI.current().select(GetItemUnitPriceLimitCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemUnitPriceLimits(UserVisitPK userVisitPK, GetItemUnitPriceLimitsForm form) {
        return CDI.current().select(GetItemUnitPriceLimitsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemUnitPriceLimit(UserVisitPK userVisitPK, EditItemUnitPriceLimitForm form) {
        return CDI.current().select(EditItemUnitPriceLimitCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemUnitPriceLimit(UserVisitPK userVisitPK, DeleteItemUnitPriceLimitForm form) {
        return CDI.current().select(DeleteItemUnitPriceLimitCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Related Item Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createRelatedItemType(UserVisitPK userVisitPK, CreateRelatedItemTypeForm form) {
        return CDI.current().select(CreateRelatedItemTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getRelatedItemTypeChoices(UserVisitPK userVisitPK, GetRelatedItemTypeChoicesForm form) {
        return CDI.current().select(GetRelatedItemTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getRelatedItemType(UserVisitPK userVisitPK, GetRelatedItemTypeForm form) {
        return CDI.current().select(GetRelatedItemTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getRelatedItemTypes(UserVisitPK userVisitPK, GetRelatedItemTypesForm form) {
        return CDI.current().select(GetRelatedItemTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultRelatedItemType(UserVisitPK userVisitPK, SetDefaultRelatedItemTypeForm form) {
        return CDI.current().select(SetDefaultRelatedItemTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editRelatedItemType(UserVisitPK userVisitPK, EditRelatedItemTypeForm form) {
        return CDI.current().select(EditRelatedItemTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteRelatedItemType(UserVisitPK userVisitPK, DeleteRelatedItemTypeForm form) {
        return CDI.current().select(DeleteRelatedItemTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Related Item Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createRelatedItemTypeDescription(UserVisitPK userVisitPK, CreateRelatedItemTypeDescriptionForm form) {
        return CDI.current().select(CreateRelatedItemTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getRelatedItemTypeDescription(UserVisitPK userVisitPK, GetRelatedItemTypeDescriptionForm form) {
        return CDI.current().select(GetRelatedItemTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getRelatedItemTypeDescriptions(UserVisitPK userVisitPK, GetRelatedItemTypeDescriptionsForm form) {
        return CDI.current().select(GetRelatedItemTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editRelatedItemTypeDescription(UserVisitPK userVisitPK, EditRelatedItemTypeDescriptionForm form) {
        return CDI.current().select(EditRelatedItemTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteRelatedItemTypeDescription(UserVisitPK userVisitPK, DeleteRelatedItemTypeDescriptionForm form) {
        return CDI.current().select(DeleteRelatedItemTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Related Items
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createRelatedItem(UserVisitPK userVisitPK, CreateRelatedItemForm form) {
        return CDI.current().select(CreateRelatedItemCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getRelatedItem(UserVisitPK userVisitPK, GetRelatedItemForm form) {
        return CDI.current().select(GetRelatedItemCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getRelatedItems(UserVisitPK userVisitPK, GetRelatedItemsForm form) {
        return CDI.current().select(GetRelatedItemsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editRelatedItem(UserVisitPK userVisitPK, EditRelatedItemForm form) {
        return CDI.current().select(EditRelatedItemCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteRelatedItem(UserVisitPK userVisitPK, DeleteRelatedItemForm form) {
        return CDI.current().select(DeleteRelatedItemCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Codes
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeForm form) {
        return CDI.current().select(CreateHarmonizedTariffScheduleCodeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodes(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodesForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeChoices(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeChoicesForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, SetDefaultHarmonizedTariffScheduleCodeForm form) {
        return CDI.current().select(SetDefaultHarmonizedTariffScheduleCodeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeForm form) {
        return CDI.current().select(EditHarmonizedTariffScheduleCodeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeForm form) {
        return CDI.current().select(DeleteHarmonizedTariffScheduleCodeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createHarmonizedTariffScheduleCodeTranslation(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeTranslationForm form) {
        return CDI.current().select(CreateHarmonizedTariffScheduleCodeTranslationCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeTranslations(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeTranslationsForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeTranslationsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeTranslation(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeTranslationForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeTranslationCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editHarmonizedTariffScheduleCodeTranslation(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeTranslationForm form) {
        return CDI.current().select(EditHarmonizedTariffScheduleCodeTranslationCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteHarmonizedTariffScheduleCodeTranslation(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeTranslationForm form) {
        return CDI.current().select(DeleteHarmonizedTariffScheduleCodeTranslationCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Units
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUnitForm form) {
        return CDI.current().select(CreateHarmonizedTariffScheduleCodeUnitCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUnits(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitsForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeUnitsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeUnitCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUnitChoices(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitChoicesForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeUnitChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, SetDefaultHarmonizedTariffScheduleCodeUnitForm form) {
        return CDI.current().select(SetDefaultHarmonizedTariffScheduleCodeUnitCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeUnitForm form) {
        return CDI.current().select(EditHarmonizedTariffScheduleCodeUnitCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUnitForm form) {
        return CDI.current().select(DeleteHarmonizedTariffScheduleCodeUnitCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Unit Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createHarmonizedTariffScheduleCodeUnitDescription(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUnitDescriptionForm form) {
        return CDI.current().select(CreateHarmonizedTariffScheduleCodeUnitDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUnitDescriptions(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitDescriptionsForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeUnitDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUnitDescription(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitDescriptionForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeUnitDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editHarmonizedTariffScheduleCodeUnitDescription(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeUnitDescriptionForm form) {
        return CDI.current().select(EditHarmonizedTariffScheduleCodeUnitDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteHarmonizedTariffScheduleCodeUnitDescription(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUnitDescriptionForm form) {
        return CDI.current().select(DeleteHarmonizedTariffScheduleCodeUnitDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Use Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUseTypeForm form) {
        return CDI.current().select(CreateHarmonizedTariffScheduleCodeUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUseTypes(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypesForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeUseTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypeForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUseTypeChoices(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypeChoicesForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeUseTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, SetDefaultHarmonizedTariffScheduleCodeUseTypeForm form) {
        return CDI.current().select(SetDefaultHarmonizedTariffScheduleCodeUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeUseTypeForm form) {
        return CDI.current().select(EditHarmonizedTariffScheduleCodeUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUseTypeForm form) {
        return CDI.current().select(DeleteHarmonizedTariffScheduleCodeUseTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Use Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createHarmonizedTariffScheduleCodeUseTypeDescription(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUseTypeDescriptionForm form) {
        return CDI.current().select(CreateHarmonizedTariffScheduleCodeUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUseTypeDescriptions(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypeDescriptionsForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeUseTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUseTypeDescription(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypeDescriptionForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editHarmonizedTariffScheduleCodeUseTypeDescription(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeUseTypeDescriptionForm form) {
        return CDI.current().select(EditHarmonizedTariffScheduleCodeUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteHarmonizedTariffScheduleCodeUseTypeDescription(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUseTypeDescriptionForm form) {
        return CDI.current().select(DeleteHarmonizedTariffScheduleCodeUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Uses
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createHarmonizedTariffScheduleCodeUse(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUseForm form) {
        return CDI.current().select(CreateHarmonizedTariffScheduleCodeUseCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUses(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUsesForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeUsesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getHarmonizedTariffScheduleCodeUse(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeUseCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteHarmonizedTariffScheduleCodeUse(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUseForm form) {
        return CDI.current().select(DeleteHarmonizedTariffScheduleCodeUseCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Item Harmonized Tariff Schedule Codes
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createItemHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, CreateItemHarmonizedTariffScheduleCodeForm form) {
        return CDI.current().select(CreateItemHarmonizedTariffScheduleCodeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemHarmonizedTariffScheduleCodes(UserVisitPK userVisitPK, GetItemHarmonizedTariffScheduleCodesForm form) {
        return CDI.current().select(GetItemHarmonizedTariffScheduleCodesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, GetItemHarmonizedTariffScheduleCodeForm form) {
        return CDI.current().select(GetItemHarmonizedTariffScheduleCodeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, EditItemHarmonizedTariffScheduleCodeForm form) {
        return CDI.current().select(EditItemHarmonizedTariffScheduleCodeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, DeleteItemHarmonizedTariffScheduleCodeForm form) {
        return CDI.current().select(DeleteItemHarmonizedTariffScheduleCodeCommand.class).get().run(userVisitPK, form);
    }

}

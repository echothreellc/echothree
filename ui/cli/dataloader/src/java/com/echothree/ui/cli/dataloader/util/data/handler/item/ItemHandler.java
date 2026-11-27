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

package com.echothree.ui.cli.dataloader.util.data.handler.item;

import com.echothree.control.user.inventory.common.InventoryService;
import com.echothree.control.user.inventory.common.InventoryUtil;
import com.echothree.control.user.inventory.common.form.InventoryFormFactory;
import com.echothree.control.user.item.common.ItemService;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.form.ItemFormFactory;
import com.echothree.control.user.item.common.spec.ItemSpecFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.comment.CommentsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.EntityAliasesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.EntityAttributesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.rating.RatingsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.tag.EntityTagsHandler;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.type.ByteArray;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ItemHandler
        extends BaseHandler {
    
    InventoryService inventoryService;
    ItemService itemService;
    String itemName;
    String companyName;
    String entityRef;
    
    boolean inItemDescription = false;
    private Map<String, Object> itemDescriptionAttrs;
    char []clobDescription = null;
    
    /** Creates a new instance of ItemHandler */
    public ItemHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String itemName, String companyName,
            String entityRef)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        inventoryService = lookupService(InventoryService.class, InventoryUtil::getHome);
        itemService = lookupService(ItemService.class, ItemUtil::getHome);
        
        this.itemName = itemName;
        this.companyName = companyName;
        this.entityRef = entityRef;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("itemAlias")) {
            var commandForm = ItemFormFactory.getCreateItemAliasForm();
            
            commandForm.setItemName(itemName);
            commandForm.set(getAttrsMap(attrs));
            
            checkCommandResult(commandForm, itemService.createItemAlias(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("itemDescription")) {
            itemDescriptionAttrs = getAttrsMap(attrs);
            itemDescriptionAttrs.put("ItemName", itemName);
            itemDescriptionAttrs.remove("BlobDescription");

            inItemDescription = true;
        } else if(localName.equals("itemUnitOfMeasureType")) {
            var commandForm = ItemFormFactory.getCreateItemUnitOfMeasureTypeForm();
            
            commandForm.setItemName(itemName);
            commandForm.set(getAttrsMap(attrs));
            
            checkCommandResult(commandForm, itemService.createItemUnitOfMeasureType(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("itemPrice")) {
            var commandForm = ItemFormFactory.getCreateItemPriceForm();
            
            commandForm.setItemName(itemName);
            commandForm.set(getAttrsMap(attrs));
            
            checkCommandResult(commandForm, itemService.createItemPrice(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("itemVolume")) {
            var commandForm = ItemFormFactory.getCreateItemVolumeForm();
            
            commandForm.setItemName(itemName);
            commandForm.set(getAttrsMap(attrs));
            
            checkCommandResult(commandForm, itemService.createItemVolume(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("itemWeight")) {
            var commandForm = ItemFormFactory.getCreateItemWeightForm();
            
            commandForm.setItemName(itemName);
            commandForm.set(getAttrsMap(attrs));
            
            checkCommandResult(commandForm, itemService.createItemWeight(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("itemCountryOfOrigin")) {
            var commandForm = ItemFormFactory.getCreateItemCountryOfOriginForm();
            
            commandForm.setItemName(itemName);
            commandForm.set(getAttrsMap(attrs));
            
            checkCommandResult(commandForm, itemService.createItemCountryOfOrigin(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("itemPackCheckRequirement")) {
            var commandForm = ItemFormFactory.getCreateItemPackCheckRequirementForm();
            
            commandForm.setItemName(itemName);
            commandForm.set(getAttrsMap(attrs));
            
            checkCommandResult(commandForm, itemService.createItemPackCheckRequirement(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("itemUnitPriceLimit")) {
            var commandForm = ItemFormFactory.getCreateItemUnitPriceLimitForm();
            
            commandForm.setItemName(itemName);
            commandForm.set(getAttrsMap(attrs));
            
            checkCommandResult(commandForm, itemService.createItemUnitPriceLimit(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("itemUnitLimit")) {
            var commandForm = ItemFormFactory.getCreateItemUnitLimitForm();
            
            commandForm.setItemName(itemName);
            commandForm.set(getAttrsMap(attrs));
            
            checkCommandResult(commandForm, itemService.createItemUnitLimit(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("itemKitMember")) {
            var commandForm = ItemFormFactory.getCreateItemKitMemberForm();
            
            commandForm.setItemName(itemName);
            commandForm.set(getAttrsMap(attrs));
            
            checkCommandResult(commandForm, itemService.createItemKitMember(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("itemUnitCustomerTypeLimit")) {
            var commandForm = ItemFormFactory.getCreateItemUnitCustomerTypeLimitForm();
            
            commandForm.setItemName(itemName);
            commandForm.set(getAttrsMap(attrs));
            
            checkCommandResult(commandForm, itemService.createItemUnitCustomerTypeLimit(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("partyInventoryLevel")) {
            var commandForm = InventoryFormFactory.getCreatePartyInventoryLevelForm();
            
            commandForm.setItemName(itemName);
            commandForm.set(getAttrsMap(attrs));
            
            if(commandForm.getWarehouseName() == null) {
                commandForm.setCompanyName(companyName);
            }
            
            checkCommandResult(commandForm, inventoryService.createPartyInventoryLevel(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("relatedItem")) {
            var commandForm = ItemFormFactory.getCreateRelatedItemForm();
            
            commandForm.setFromItemName(itemName);
            commandForm.set(getAttrsMap(attrs));
            
            checkCommandResult(commandForm, itemService.createRelatedItem(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("comments")) {
            initialDataParser.pushHandler(new CommentsHandler(initialDataParser, this, entityRef));
        } else if(localName.equals("ratings")) {
            initialDataParser.pushHandler(new RatingsHandler(initialDataParser, this, entityRef));
        } else if(localName.equals("entityAliases")) {
            initialDataParser.pushHandler(new EntityAliasesHandler(initialDataParser, this, entityRef));
        } else if(localName.equals("entityAttributes")) {
            initialDataParser.pushHandler(new EntityAttributesHandler(initialDataParser, this, entityRef));
        } else if(localName.equals("entityTags")) {
            initialDataParser.pushHandler(new EntityTagsHandler(initialDataParser, this, entityRef));
        }
    }
    
    @Override
    public void characters(char ch[], int start, int length)
            throws SAXException {
        if(inItemDescription) {
            var oldLength = clobDescription != null? clobDescription.length: 0;
            var newClob = new char[oldLength + length];
            
            if(clobDescription != null) {
                System.arraycopy(clobDescription, 0, newClob, 0, clobDescription.length);
            }
            
            System.arraycopy(ch, start, newClob, oldLength, length);
            clobDescription = newClob;
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("itemDescription")) {
            loadItemDescription();
            inItemDescription = false;
            itemDescriptionAttrs = null;
            clobDescription = null;
        } else if(localName.equals("item")) {
            initialDataParser.popHandler();
        }
    }
    
    private void loadItemDescription() throws SAXException {
        // An item description replaces its previous content and media attributes.
        itemDescriptionAttrs.putIfAbsent("MimeTypeName", null);
        itemDescriptionAttrs.putIfAbsent("ItemImageTypeName", null);
        itemDescriptionAttrs.putIfAbsent("StringDescription", null);
        itemDescriptionAttrs.put("ClobDescription", clobDescription == null ? null : new String(clobDescription));

        var fileName = (String)itemDescriptionAttrs.remove("Path");
        if(fileName != null) {
            try {
                var path = Path.of(fileName);
                if(Files.size(path) < Integer.MAX_VALUE) {
                    itemDescriptionAttrs.put("BlobDescription", new ByteArray(Files.readAllBytes(path)));
                }
            } catch(IOException | InvalidPathException failure) {
                throw new SAXException("Could not read item description file " + fileName, failure);
            }
        }

        editOrCreate(itemDescriptionAttrs, ItemSpecFactory.getItemDescriptionSpec(),
                ItemFormFactory.getEditItemDescriptionForm(), itemService::editItemDescription,
                ItemFormFactory.getCreateItemDescriptionForm(), itemService::createItemDescription,
                ExecutionErrors.UnknownItemDescription);
    }

}

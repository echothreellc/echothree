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

import com.echothree.control.user.inventory.common.InventoryUtil;
import com.echothree.control.user.inventory.common.InventoryService;
import com.echothree.control.user.inventory.common.form.InventoryFormFactory;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.ItemService;
import com.echothree.control.user.item.common.edit.ItemDescriptionEdit;
import com.echothree.control.user.item.common.form.ItemFormFactory;
import com.echothree.control.user.item.common.result.EditItemDescriptionResult;
import com.echothree.control.user.item.common.spec.ItemSpecFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.comment.CommentsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.EntityAliasesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.EntityAttributesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.rating.RatingsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.tag.EntityTagsHandler;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.persistence.type.ByteArray;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.naming.NamingException;
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
    String itemDescriptionTypeName = null;
    String languageIsoName = null;
    String mimeTypeName = null;
    String itemImageTypeName = null;
    String stringDescription = null;
    char []clobDescription = null;
    String path = null;
    
    /** Creates a new instance of ItemHandler */
    public ItemHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String itemName, String companyName,
            String entityRef) {
        super(initialDataParser, parentHandler);
        
        try {
            inventoryService = InventoryUtil.getHome();
            itemService = ItemUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
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
            
            itemService.createItemAlias(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("itemDescription")) {
            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("itemDescriptionTypeName")) {
                    itemDescriptionTypeName = attrs.getValue(i);
                } else if(attrs.getQName(i).equals("languageIsoName")) {
                    languageIsoName = attrs.getValue(i);
                } else if(attrs.getQName(i).equals("mimeTypeName")) {
                    mimeTypeName = attrs.getValue(i);
                } else if(attrs.getQName(i).equals("itemImageTypeName")) {
                    itemImageTypeName = attrs.getValue(i);
                } else if(attrs.getQName(i).equals("stringDescription")) {
                    stringDescription = attrs.getValue(i);
                } else if(attrs.getQName(i).equals("path")) {
                    path = attrs.getValue(i);
                }
            }
            
            inItemDescription = true;
        } else if(localName.equals("itemUnitOfMeasureType")) {
            var commandForm = ItemFormFactory.getCreateItemUnitOfMeasureTypeForm();
            
            commandForm.setItemName(itemName);
            commandForm.set(getAttrsMap(attrs));
            
            itemService.createItemUnitOfMeasureType(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("itemPrice")) {
            var commandForm = ItemFormFactory.getCreateItemPriceForm();
            
            commandForm.setItemName(itemName);
            commandForm.set(getAttrsMap(attrs));
            
            itemService.createItemPrice(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("itemVolume")) {
            var commandForm = ItemFormFactory.getCreateItemVolumeForm();
            
            commandForm.setItemName(itemName);
            commandForm.set(getAttrsMap(attrs));
            
            itemService.createItemVolume(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("itemWeight")) {
            var commandForm = ItemFormFactory.getCreateItemWeightForm();
            
            commandForm.setItemName(itemName);
            commandForm.set(getAttrsMap(attrs));
            
            itemService.createItemWeight(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("itemCountryOfOrigin")) {
            var commandForm = ItemFormFactory.getCreateItemCountryOfOriginForm();
            
            commandForm.setItemName(itemName);
            commandForm.set(getAttrsMap(attrs));
            
            itemService.createItemCountryOfOrigin(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("itemPackCheckRequirement")) {
            var commandForm = ItemFormFactory.getCreateItemPackCheckRequirementForm();
            
            commandForm.setItemName(itemName);
            commandForm.set(getAttrsMap(attrs));
            
            itemService.createItemPackCheckRequirement(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("itemUnitPriceLimit")) {
            var commandForm = ItemFormFactory.getCreateItemUnitPriceLimitForm();
            
            commandForm.setItemName(itemName);
            commandForm.set(getAttrsMap(attrs));
            
            itemService.createItemUnitPriceLimit(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("itemUnitLimit")) {
            var commandForm = ItemFormFactory.getCreateItemUnitLimitForm();
            
            commandForm.setItemName(itemName);
            commandForm.set(getAttrsMap(attrs));
            
            itemService.createItemUnitLimit(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("itemKitMember")) {
            var commandForm = ItemFormFactory.getCreateItemKitMemberForm();
            
            commandForm.setItemName(itemName);
            commandForm.set(getAttrsMap(attrs));
            
            itemService.createItemKitMember(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("itemUnitCustomerTypeLimit")) {
            var commandForm = ItemFormFactory.getCreateItemUnitCustomerTypeLimitForm();
            
            commandForm.setItemName(itemName);
            commandForm.set(getAttrsMap(attrs));
            
            itemService.createItemUnitCustomerTypeLimit(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("partyInventoryLevel")) {
            var commandForm = InventoryFormFactory.getCreatePartyInventoryLevelForm();
            
            commandForm.setItemName(itemName);
            commandForm.set(getAttrsMap(attrs));
            
            if(commandForm.getWarehouseName() == null) {
                commandForm.setCompanyName(companyName);
            }
            
            inventoryService.createPartyInventoryLevel(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("relatedItem")) {
            var commandForm = ItemFormFactory.getCreateRelatedItemForm();
            
            commandForm.setFromItemName(itemName);
            commandForm.set(getAttrsMap(attrs));
            
            itemService.createRelatedItem(initialDataParser.getUserVisit(), commandForm);
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
            var spec = ItemSpecFactory.getItemDescriptionSpec();
            var editForm = ItemFormFactory.getEditItemDescriptionForm();

            spec.setItemName(itemName);
            spec.setItemDescriptionTypeName(itemDescriptionTypeName);
            spec.setLanguageIsoName(languageIsoName);

            editForm.setSpec(spec);
            editForm.setEditMode(EditMode.LOCK);

            var commandResult = itemService.editItemDescription(initialDataParser.getUserVisit(), editForm);
            
            if(commandResult.hasErrors()) {
                if(commandResult.containsExecutionError(ExecutionErrors.UnknownItemDescription.name())) {
                    var createForm = ItemFormFactory.getCreateItemDescriptionForm();

                    createForm.setItemName(itemName);
                    createForm.setItemDescriptionTypeName(itemDescriptionTypeName);
                    createForm.setLanguageIsoName(languageIsoName);
                    createForm.setMimeTypeName(mimeTypeName);
                    createForm.setItemImageTypeName(itemImageTypeName);
                    createForm.setClobDescription(clobDescription == null ? null : new String(clobDescription));
                    createForm.setStringDescription(stringDescription);

                    if(path != null) {
                        var file = new File(path);
                        var length = file.length();

                        if(length < Integer.MAX_VALUE) {
                            try {
                                InputStream is = new FileInputStream(file);
                                var bytes = new byte[(int)length];
                                var offset = 0;
                                var numRead = 0;

                                while(offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                                    offset += numRead;
                                }

                                // Ensure all the bytes have been read in
                                if(offset < bytes.length) {
                                    throw new SAXException("Could not completely read file " + file.getName());
                                }

                                // Close the input stream and return bytes
                                is.close();

                                createForm.setBlobDescription(new ByteArray(bytes));
                            } catch(FileNotFoundException fnfe) {
                                throw new SAXException(fnfe);
                            } catch(IOException ioe) {
                                throw new SAXException(ioe);
                            }
                        }
                    }

                    commandResult = itemService.createItemDescription(initialDataParser.getUserVisit(), createForm);
                    
                    if(commandResult.hasErrors()) {
                        getLogger().error(commandResult.toString());
                    }
                } else {
                    getLogger().error(commandResult.toString());
                }
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditItemDescriptionResult)executionResult.getResult();

                if(result != null) {
                    var edit = (ItemDescriptionEdit)result.getEdit();
                    var changed = false;

                    var currentMimeTypeName = edit.getMimeTypeName();
                    if((currentMimeTypeName == null && mimeTypeName != null)
                            || (currentMimeTypeName != null && mimeTypeName == null)
                            || ((currentMimeTypeName != null && mimeTypeName != null ) && !currentMimeTypeName.equals(mimeTypeName))) {
                        edit.setMimeTypeName(mimeTypeName);
                        changed = true;
                    }

                    var currentItemImageTypeName = edit.getItemImageTypeName();
                    if((currentItemImageTypeName == null && itemImageTypeName != null)
                            || (currentItemImageTypeName != null && itemImageTypeName == null)
                            || ((currentItemImageTypeName != null && itemImageTypeName != null ) && !currentItemImageTypeName.equals(itemImageTypeName))) {
                        edit.setItemImageTypeName(itemImageTypeName);
                        changed = true;
                    }

                    var currentClobDescription = edit.getClobDescription();
                    var wrappedClobDescription = clobDescription == null ? null : new String(clobDescription);
                    if((currentClobDescription == null && wrappedClobDescription != null)
                            || (currentClobDescription != null && wrappedClobDescription == null)
                            || ((currentClobDescription != null && wrappedClobDescription != null ) && !currentClobDescription.equals(wrappedClobDescription))) {
                        edit.setClobDescription(wrappedClobDescription);
                        changed = true;
                    }

                    var currentStringDescription = edit.getStringDescription();
                    if((currentStringDescription == null && stringDescription != null)
                            || (currentStringDescription != null && stringDescription == null)
                            || ((currentStringDescription != null && stringDescription != null ) && !currentStringDescription.equals(stringDescription))) {
                        edit.setStringDescription(stringDescription);
                        changed = true;
                    }

                    // If there's a BLOB, we'll always assume it's changed. They're not returned as
                    // part of the edit.
                    if(path != null) {
                        var file = new File(path);
                        var length = file.length();

                        if(length < Integer.MAX_VALUE) {
                            try {
                                InputStream is = new FileInputStream(file);
                                var bytes = new byte[(int)length];
                                var offset = 0;
                                var numRead = 0;

                                while(offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                                    offset += numRead;
                                }

                                // Ensure all the bytes have been read in
                                if(offset < bytes.length) {
                                    throw new SAXException("Could not completely read file " + file.getName());
                                }

                                // Close the input stream and return bytes
                                is.close();

                                edit.setBlobDescription(new ByteArray(bytes));
                                changed = true;
                            } catch(FileNotFoundException fnfe) {
                                throw new SAXException(fnfe);
                            } catch(IOException ioe) {
                                throw new SAXException(ioe);
                            }
                        }
                    }
                    
                    if(changed) {
                        editForm.setEdit(edit);
                        editForm.setEditMode(EditMode.UPDATE);
                        
                        commandResult = itemService.editItemDescription(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLogger().error(commandResult.toString());
                        }
                    } else {
                        editForm.setEdit(null);
                        editForm.setEditMode(EditMode.ABANDON);
                        
                        commandResult = itemService.editItemDescription(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLogger().error(commandResult.toString());
                        }
                    }
                }
            }
            
            inItemDescription = false;
            itemDescriptionTypeName = null;
            languageIsoName = null;
            mimeTypeName = null;
            itemImageTypeName = null;
            stringDescription = null;
            clobDescription = null;
        } else if(localName.equals("item")) {
            initialDataParser.popHandler();
        }
    }
    
}

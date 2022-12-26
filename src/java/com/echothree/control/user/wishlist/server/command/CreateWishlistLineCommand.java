// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.control.user.wishlist.server.command;

import com.echothree.control.user.wishlist.common.form.CreateWishlistLineForm;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.inventory.common.InventoryConstants;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.offer.server.control.OfferItemControl;
import com.echothree.model.control.offer.server.control.SourceControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.control.wishlist.server.logic.WishlistLogic;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.InventoryConditionUse;
import com.echothree.model.data.inventory.server.entity.InventoryConditionUseType;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferItem;
import com.echothree.model.data.offer.server.entity.OfferItemPrice;
import com.echothree.model.data.offer.server.entity.Source;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.wishlist.server.entity.WishlistType;
import com.echothree.model.data.wishlist.server.entity.WishlistTypePriority;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateWishlistLineCommand
        extends BaseSimpleCommand<CreateWishlistLineForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("WishlistTypeName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("WishlistTypePriorityName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("SourceName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("Quantity", FieldType.UNSIGNED_LONG, false, null, null),
            new FieldDefinition("Comment", FieldType.STRING, false, 1L, 80L)
        ));
    }
    
    /** Creates a new instance of CreateWishlistLineCommand */
    public CreateWishlistLineCommand(UserVisitPK userVisitPK, CreateWishlistLineForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        String partyName = form.getPartyName();
        Party party = partyName == null? getParty(): partyControl.getPartyByName(partyName);
        
        if(party != null) {
            String partyTypeName = party.getLastDetail().getPartyType().getPartyTypeName();
            
            if(partyTypeName.equals(PartyTypes.CUSTOMER.name())) {
                var wishlistControl = Session.getModelController(WishlistControl.class);
                String wishlistTypeName = form.getWishlistTypeName();
                WishlistType wishlistType = wishlistTypeName == null? wishlistControl.getDefaultWishlistType():
                    wishlistControl.getWishlistTypeByName(wishlistTypeName);
                
                if(wishlistType != null) {
                    String wishlistTypePriorityName = form.getWishlistTypePriorityName();
                    WishlistTypePriority wishlistTypePriority = wishlistTypePriorityName == null? wishlistControl.getDefaultWishlistTypePriority(wishlistType):
                        wishlistControl.getWishlistTypePriorityByName(wishlistType, wishlistTypePriorityName);
                    
                    if(wishlistTypePriority != null) {
                        var accountingControl = Session.getModelController(AccountingControl.class);
                        String currencyIsoName = form.getCurrencyIsoName();
                        Currency currency = currencyIsoName == null? accountingControl.getDefaultCurrency():
                            accountingControl.getCurrencyByIsoName(currencyIsoName);
                        
                        if(currency != null) {
                            var sourceControl = Session.getModelController(SourceControl.class);
                            String sourceName = form.getSourceName();
                            Source source = sourceName == null? sourceControl.getDefaultSource(): sourceControl.getSourceByName(sourceName);
                            
                            if(source != null) {
                                var itemControl = Session.getModelController(ItemControl.class);
                                String itemName = form.getItemName();
                                Item item = itemControl.getItemByName(itemName);
                                
                                if(item != null) {
                                    var offerItemControl = Session.getModelController(OfferItemControl.class);
                                    Offer offer = source.getLastDetail().getOfferUse().getLastDetail().getOffer();
                                    OfferItem offerItem = offerItemControl.getOfferItem(offer, item);
                                    
                                    if(offerItem != null) {
                                        var inventoryControl = Session.getModelController(InventoryControl.class);
                                        String inventoryConditionName = form.getInventoryConditionName();
                                        InventoryConditionUseType inventoryConditionUseType = inventoryControl.getInventoryConditionUseTypeByName(InventoryConstants.InventoryConditionUseType_SALES_ORDER);
                                        InventoryCondition inventoryCondition = null;
                                        
                                        if(inventoryConditionName == null) {
                                            InventoryConditionUse inventoryConditionUse = inventoryControl.getDefaultInventoryConditionUse(inventoryConditionUseType);
                                            
                                            if(inventoryConditionUse == null) {
                                                addExecutionError(ExecutionErrors.MissingDefaultInventoryConditionUse.name());
                                            } else {
                                                inventoryCondition = inventoryConditionUse.getInventoryCondition();
                                            }
                                        } else {
                                            inventoryCondition = inventoryControl.getInventoryConditionByName(inventoryConditionName);
                                            
                                            if(inventoryControl.getInventoryConditionUse(inventoryConditionUseType, inventoryCondition) == null) {
                                                addExecutionError(ExecutionErrors.InvalidInventoryCondition.name());
                                            }
                                        }
                                        
                                        if(!hasExecutionErrors()) {
                                            var uomControl = Session.getModelController(UomControl.class);
                                            UnitOfMeasureKind unitOfMeasureKind = item.getLastDetail().getUnitOfMeasureKind();
                                            String unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
                                            UnitOfMeasureType unitOfMeasureType = unitOfMeasureTypeName == null? uomControl.getDefaultUnitOfMeasureType(unitOfMeasureKind):
                                                uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);
                                            
                                            if(unitOfMeasureType != null) {
                                                OfferItemPrice offerItemPrice = offerItemControl.getOfferItemPrice(offerItem, inventoryCondition, unitOfMeasureType, currency);
                                                
                                                if(offerItemPrice != null) {
                                                    String strQuantity = form.getQuantity();
                                                    Long quantity = strQuantity == null ? (long) 1 : Long.valueOf(strQuantity);
                                                    String comment = form.getComment();

                                                    WishlistLogic.getInstance().createWishlistLine(session, this, getUserVisit(), party, source, offerItemPrice,
                                                            wishlistType, wishlistTypePriority, quantity, comment, getPartyPK());
                                                } else {
                                                    addExecutionError(ExecutionErrors.UnknownOfferItemPrice.name(),
                                                            offerItem.getOffer().getLastDetail().getOfferName(),
                                                            item.getLastDetail().getItemName(),
                                                            inventoryCondition.getLastDetail().getInventoryConditionName(),
                                                            unitOfMeasureType.getLastDetail().getUnitOfMeasureTypeName(),
                                                            currency.getCurrencyIsoName());
                                                }
                                            } else {
                                                addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
                                            }
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownOfferItem.name(), offer.getLastDetail().getOfferName(), itemName);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownSourceName.name(), sourceName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), currencyIsoName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownWishlistTypePriorityName.name(), wishlistTypePriorityName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownWishlistTypeName.name(), wishlistTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.InvalidPartyType.name(), partyTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }
        
        return null;
    }

}

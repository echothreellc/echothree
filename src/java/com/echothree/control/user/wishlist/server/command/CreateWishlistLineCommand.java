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
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class CreateWishlistLineCommand
        extends BaseSimpleCommand<CreateWishlistLineForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("WishlistTypeName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("WishlistPriorityName", FieldType.ENTITY_NAME, false, null, null),
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
    public CreateWishlistLineCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var partyName = form.getPartyName();
        var party = partyName == null? getParty(): partyControl.getPartyByName(partyName);
        
        if(party != null) {
            var partyTypeName = party.getLastDetail().getPartyType().getPartyTypeName();
            
            if(partyTypeName.equals(PartyTypes.CUSTOMER.name())) {
                var wishlistControl = Session.getModelController(WishlistControl.class);
                var wishlistTypeName = form.getWishlistTypeName();
                var wishlistType = wishlistTypeName == null? wishlistControl.getDefaultWishlistType():
                    wishlistControl.getWishlistTypeByName(wishlistTypeName);
                
                if(wishlistType != null) {
                    var wishlistPriorityName = form.getWishlistPriorityName();
                    var wishlistPriority = wishlistPriorityName == null? wishlistControl.getDefaultWishlistPriority(wishlistType):
                        wishlistControl.getWishlistPriorityByName(wishlistType, wishlistPriorityName);
                    
                    if(wishlistPriority != null) {
                        var accountingControl = Session.getModelController(AccountingControl.class);
                        var currencyIsoName = form.getCurrencyIsoName();
                        var currency = currencyIsoName == null? accountingControl.getDefaultCurrency():
                            accountingControl.getCurrencyByIsoName(currencyIsoName);
                        
                        if(currency != null) {
                            var sourceControl = Session.getModelController(SourceControl.class);
                            var sourceName = form.getSourceName();
                            var source = sourceName == null? sourceControl.getDefaultSource(): sourceControl.getSourceByName(sourceName);
                            
                            if(source != null) {
                                var itemControl = Session.getModelController(ItemControl.class);
                                var itemName = form.getItemName();
                                var item = itemControl.getItemByName(itemName);
                                
                                if(item != null) {
                                    var offerItemControl = Session.getModelController(OfferItemControl.class);
                                    var offer = source.getLastDetail().getOfferUse().getLastDetail().getOffer();
                                    var offerItem = offerItemControl.getOfferItem(offer, item);
                                    
                                    if(offerItem != null) {
                                        var inventoryControl = Session.getModelController(InventoryControl.class);
                                        var inventoryConditionName = form.getInventoryConditionName();
                                        var inventoryConditionUseType = inventoryControl.getInventoryConditionUseTypeByName(InventoryConstants.InventoryConditionUseType_SALES_ORDER);
                                        InventoryCondition inventoryCondition = null;
                                        
                                        if(inventoryConditionName == null) {
                                            var inventoryConditionUse = inventoryControl.getDefaultInventoryConditionUse(inventoryConditionUseType);
                                            
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
                                            var unitOfMeasureKind = item.getLastDetail().getUnitOfMeasureKind();
                                            var unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
                                            var unitOfMeasureType = unitOfMeasureTypeName == null? uomControl.getDefaultUnitOfMeasureType(unitOfMeasureKind):
                                                uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);
                                            
                                            if(unitOfMeasureType != null) {
                                                var offerItemPrice = offerItemControl.getOfferItemPrice(offerItem, inventoryCondition, unitOfMeasureType, currency);
                                                
                                                if(offerItemPrice != null) {
                                                    var strQuantity = form.getQuantity();
                                                    Long quantity = strQuantity == null ? 1L : Long.valueOf(strQuantity);
                                                    var comment = form.getComment();

                                                    WishlistLogic.getInstance().createWishlistLine(session, this, getUserVisit(), party, source, offerItemPrice,
                                                            wishlistType, wishlistPriority, quantity, comment, getPartyPK());
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
                        addExecutionError(ExecutionErrors.UnknownWishlistPriorityName.name(), wishlistPriorityName);
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

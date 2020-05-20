// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.control.user.offer.server.command;

import com.echothree.control.user.offer.common.form.DeleteOfferItemPriceForm;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.offer.server.logic.OfferLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferItem;
import com.echothree.model.data.offer.server.entity.OfferItemPrice;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DeleteOfferItemPriceCommand
        extends BaseSimpleCommand<DeleteOfferItemPriceForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.OfferItemPrice.name(), SecurityRoles.Delete.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OfferName", FieldType.ENTITY_NAME, true, null, 20L),
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteOfferItemPriceCommand */
    public DeleteOfferItemPriceCommand(UserVisitPK userVisitPK, DeleteOfferItemPriceForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        String offerName = form.getOfferName();
        Offer offer = offerControl.getOfferByName(offerName);
        
        if(offer != null) {
            var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
            String itemName = form.getItemName();
            Item item = itemControl.getItemByName(itemName);
            if(item != null) {
                OfferItem offerItem = offerControl.getOfferItem(offer, item);
                
                if(offerItem != null) {
                    var inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
                    String inventoryConditionName = form.getInventoryConditionName();
                    InventoryCondition inventoryCondition = inventoryControl.getInventoryConditionByName(inventoryConditionName);
                    
                    if(inventoryCondition != null) {
                        var uomControl = (UomControl)Session.getModelController(UomControl.class);
                        UnitOfMeasureKind unitOfMeasureKind = item.getLastDetail().getUnitOfMeasureKind();
                        String unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
                        UnitOfMeasureType unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);
                        
                        if(unitOfMeasureType != null) {
                            var accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
                            String currencyIsoName = form.getCurrencyIsoName();
                            Currency currency = accountingControl.getCurrencyByIsoName(currencyIsoName);
                            
                            if(currency != null) {
                                OfferItemPrice offerItemPrice = offerControl.getOfferItemPriceForUpdate(offerItem, inventoryCondition, unitOfMeasureType,
                                        currency);
                                
                                if(offerItemPrice != null) {
                                    OfferLogic.getInstance().deleteOfferItemPrice(offerItemPrice, getPartyPK());
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownOfferItemPrice.name(), offerName, itemName, inventoryConditionName,
                                            unitOfMeasureTypeName, currencyIsoName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), currencyIsoName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureKind.getLastDetail().getUnitOfMeasureKindName(),
                                    unitOfMeasureTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownInventoryConditionName.name(), inventoryConditionName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownOfferItem.name(), offerName, itemName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOfferName.name(), offerName);
        }
        
        return null;
    }
    
}

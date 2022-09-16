// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.control.user.offer.common.form.CreateOfferItemPriceForm;
import com.echothree.model.control.accounting.server.logic.CurrencyLogic;
import com.echothree.model.control.inventory.server.logic.InventoryConditionLogic;
import com.echothree.model.control.item.common.ItemPriceTypes;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.control.offer.server.control.OfferItemControl;
import com.echothree.model.control.offer.server.logic.OfferItemLogic;
import com.echothree.model.control.offer.server.logic.OfferLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateOfferItemPriceCommand
        extends BaseSimpleCommand<CreateOfferItemPriceForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.OfferItemPrice.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OfferName", FieldType.ENTITY_NAME, true, null, 20L),
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitPrice:CurrencyIsoName,CurrencyIsoName", FieldType.UNSIGNED_PRICE_UNIT, false, null, null),
                new FieldDefinition("MinimumUnitPrice:CurrencyIsoName,CurrencyIsoName", FieldType.UNSIGNED_PRICE_UNIT, false, null, null),
                new FieldDefinition("MaximumUnitPrice:CurrencyIsoName,CurrencyIsoName", FieldType.UNSIGNED_PRICE_UNIT, false, null, null),
                new FieldDefinition("UnitPriceIncrement:CurrencyIsoName,CurrencyIsoName", FieldType.UNSIGNED_PRICE_UNIT, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateOfferItemPriceCommand */
    public CreateOfferItemPriceCommand(UserVisitPK userVisitPK, CreateOfferItemPriceForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var offerName = form.getOfferName();
        var itemName = form.getItemName();
        var inventoryConditionName = form.getInventoryConditionName();
        var unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
        var currencyIsoName = form.getCurrencyIsoName();
        var strUnitPrice = form.getUnitPrice();
        var strMinimumUnitPrice = form.getMinimumUnitPrice();
        var strMaximumUnitPrice = form.getMaximumUnitPrice();
        var strUnitPriceIncrement = form.getUnitPriceIncrement();
        var offer = OfferLogic.getInstance().getOfferByName(this, offerName);
        var item = ItemLogic.getInstance().getItemByName(this, itemName);
        var inventoryCondition = InventoryConditionLogic.getInstance().getInventoryConditionByName(this, inventoryConditionName);
        var currency = CurrencyLogic.getInstance().getCurrencyByName(this, currencyIsoName);

        if(!hasExecutionErrors()) {
            var uomControl = Session.getModelController(UomControl.class);
            var itemDetail = item.getLastDetail();
            var unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(itemDetail.getUnitOfMeasureKind(),
                    unitOfMeasureTypeName);

            if(unitOfMeasureType != null) {
                var offerItemControl = Session.getModelController(OfferItemControl.class);
                var offerItem = offerItemControl.getOfferItem(offer, item);

                if(offerItem != null) {
                    var itemControl = Session.getModelController(ItemControl.class);
                    var itemPrice = itemControl.getItemPrice(item, inventoryCondition, unitOfMeasureType, currency);

                    if(itemPrice != null) {
                        var offerItemPrice = offerItemControl.getOfferItemPrice(offerItem, inventoryCondition,
                                unitOfMeasureType, currency);

                        if(offerItemPrice == null) {
                            var itemPriceTypeName = itemDetail.getItemPriceType().getItemPriceTypeName();
                            var createdBy = getPartyPK();

                            if(itemPriceTypeName.equals(ItemPriceTypes.FIXED.name())) {
                                if(strUnitPrice != null) {
                                    var unitPrice = Long.valueOf(strUnitPrice);

                                    offerItemPrice = OfferItemLogic.getInstance().createOfferItemPrice(offerItem, inventoryCondition,
                                            unitOfMeasureType, currency, createdBy);
                                    OfferItemLogic.getInstance().createOfferItemFixedPrice(offerItemPrice, unitPrice, createdBy);
                                } else {
                                    addExecutionError(ExecutionErrors.MissingUnitPrice.name());
                                }
                            } else if(itemPriceTypeName.equals(ItemPriceTypes.VARIABLE.name())) {
                                Long minimumUnitPrice = null;
                                Long maximumUnitPrice = null;
                                Long unitPriceIncrement = null;

                                if(strMinimumUnitPrice != null) {
                                    minimumUnitPrice = Long.valueOf(strMinimumUnitPrice);
                                } else {
                                    addExecutionError(ExecutionErrors.MissingMinimumUnitPrice.name());
                                }

                                if(strMaximumUnitPrice != null) {
                                    maximumUnitPrice = Long.valueOf(strMaximumUnitPrice);
                                } else {
                                    addExecutionError(ExecutionErrors.MissingMaximumUnitPrice.name());
                                }

                                if(strUnitPriceIncrement != null) {
                                    unitPriceIncrement = Long.valueOf(strUnitPriceIncrement);
                                } else {
                                    addExecutionError(ExecutionErrors.MissingUnitPriceIncrement.name());
                                }

                                if(minimumUnitPrice != null && maximumUnitPrice != null && unitPriceIncrement != null) {
                                    offerItemPrice = OfferItemLogic.getInstance().createOfferItemPrice(offerItem, inventoryCondition,
                                            unitOfMeasureType, currency, createdBy);
                                    OfferItemLogic.getInstance().createOfferItemVariablePrice(offerItemPrice, minimumUnitPrice, maximumUnitPrice,
                                            unitPriceIncrement, createdBy);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownItemPriceType.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateOfferItemPrice.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownItemPrice.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownOfferItem.name(), offerName, itemName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
            }
        }
        
        return null;
    }
    
}

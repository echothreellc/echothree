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

package com.echothree.control.user.offer.server.command;

import com.echothree.control.user.offer.common.form.GetOfferItemPriceForm;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.control.OfferItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.logic.SecurityRoleLogic;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.offer.server.entity.OfferItemPrice;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetOfferItemPriceCommand
        extends BaseSingleEntityCommand<OfferItemPrice, GetOfferItemPriceForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.OfferItemPrice.name(), SecurityRoles.Review.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OfferName", FieldType.ENTITY_NAME, true, null, 20L),
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IncludeHistory", FieldType.BOOLEAN, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetOfferItemPriceCommand */
    public GetOfferItemPriceCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected boolean checkOptionalSecurityRoles() {
        // This occurs before validation, and parseBoolean is more lax than our validation of what's permitted for a FieldType.BOOLEAN.
        return Boolean.parseBoolean(form.getIncludeHistory()) ? SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(this, getParty(),
                SecurityRoleGroups.OfferItemPrice.name(), SecurityRoles.History.name()) : true;
    }
    
    @Override
    protected OfferItemPrice getEntity() {
        var offerControl = Session.getModelController(OfferControl.class);
        var offerName = form.getOfferName();
        var offer = offerControl.getOfferByName(offerName);
        OfferItemPrice offerItemPrice = null;

        if(offer != null) {
            var itemControl = Session.getModelController(ItemControl.class);
            var itemName = form.getItemName();
            var item = itemControl.getItemByName(itemName);
            
            if(item != null) {
                var offerItemControl = Session.getModelController(OfferItemControl.class);
                var offerItem = offerItemControl.getOfferItem(offer, item);

                if(offerItem != null) {
                    var inventoryControl = Session.getModelController(InventoryControl.class);
                    var inventoryConditionName = form.getInventoryConditionName();
                    var inventoryCondition = inventoryControl.getInventoryConditionByName(inventoryConditionName);

                    if(inventoryCondition != null) {
                        var uomControl = Session.getModelController(UomControl.class);
                        var unitOfMeasureKind = item.getLastDetail().getUnitOfMeasureKind();
                        var unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
                        var unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);

                        if(unitOfMeasureType != null) {
                            var accountingControl = Session.getModelController(AccountingControl.class);
                            var currencyIsoName = form.getCurrencyIsoName();
                            var currency = accountingControl.getCurrencyByIsoName(currencyIsoName);

                            if(currency != null) {
                                offerItemPrice = offerItemControl.getOfferItemPrice(offerItem, inventoryCondition, unitOfMeasureType, currency);

                                if(offerItemPrice == null) {
                                    addExecutionError(ExecutionErrors.UnknownOfferItemPrice.name(), offer.getLastDetail().getOfferName(),
                                            item.getLastDetail().getItemName(), inventoryCondition.getLastDetail().getInventoryConditionName(),
                                            unitOfMeasureType.getLastDetail().getUnitOfMeasureTypeName(), currency.getCurrencyIsoName());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), currencyIsoName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(),
                                    unitOfMeasureKind.getLastDetail().getUnitOfMeasureKindName(), unitOfMeasureTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownInventoryConditionName.name(), inventoryConditionName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownOfferItem.name(), offer.getLastDetail().getOfferName(),
                            item.getLastDetail().getItemName());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOfferName.name(), offerName);
        }
        
        return offerItemPrice;
    }
    
    @Override
    protected BaseResult getResult(OfferItemPrice offerItemPrice) {
        var result = OfferResultFactory.getGetOfferItemPriceResult();

        if(offerItemPrice != null) {
            var offerItemControl = Session.getModelController(OfferItemControl.class);
            var userVisit = getUserVisit();

            result.setOfferItemPrice(offerItemControl.getOfferItemPriceTransfer(userVisit, offerItemPrice));

            if(Boolean.parseBoolean(form.getIncludeHistory())) {
                result.setHistory(offerItemControl.getOfferItemPriceHistory(userVisit, offerItemPrice));
            }
        }
        
        return result;
    }
    
}

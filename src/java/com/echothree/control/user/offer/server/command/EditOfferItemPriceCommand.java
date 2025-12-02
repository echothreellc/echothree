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

package com.echothree.control.user.offer.server.command;

import com.echothree.control.user.offer.common.edit.OfferEditFactory;
import com.echothree.control.user.offer.common.edit.OfferItemPriceEdit;
import com.echothree.control.user.offer.common.form.EditOfferItemPriceForm;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.control.user.offer.common.spec.OfferItemPriceSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.accounting.server.logic.CurrencyLogic;
import com.echothree.model.control.inventory.server.logic.InventoryConditionLogic;
import com.echothree.model.control.item.common.ItemPriceTypes;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.control.offer.server.control.OfferItemControl;
import com.echothree.model.control.offer.server.logic.OfferItemLogic;
import com.echothree.model.control.offer.server.logic.OfferLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;
import com.echothree.util.server.validation.Validator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditOfferItemPriceCommand
        extends BaseEditCommand<OfferItemPriceSpec, OfferItemPriceEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.OfferItemPrice.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OfferName", FieldType.ENTITY_NAME, true, null, 20L),
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UnitPrice", FieldType.UNSIGNED_PRICE_UNIT, false, null, null),
                new FieldDefinition("MinimumUnitPrice", FieldType.UNSIGNED_PRICE_UNIT, false, null, null),
                new FieldDefinition("MaximumUnitPrice", FieldType.UNSIGNED_PRICE_UNIT, false, null, null),
                new FieldDefinition("UnitPriceIncrement", FieldType.UNSIGNED_PRICE_UNIT, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditOfferItemPriceCommand */
    public EditOfferItemPriceCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected void setupValidatorForEdit(Validator validator, BaseForm specForm) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var currencyIsoName = spec.getCurrencyIsoName();
        
        validator.setCurrency(accountingControl.getCurrencyByIsoName(currencyIsoName));
    }
    
    @Override
    protected BaseResult execute() {
        var result = OfferResultFactory.getEditOfferItemPriceResult();
        var offerName = spec.getOfferName();
        var offer = OfferLogic.getInstance().getOfferByName(this, offerName);

        if(!hasExecutionErrors()) {
            final var offerDetail = offer.getLastDetail();

            if(offerDetail.getOfferItemPriceFilter() == null) {
            var itemName = spec.getItemName();
            var inventoryConditionName = spec.getInventoryConditionName();
            var unitOfMeasureTypeName = spec.getUnitOfMeasureTypeName();
            var currencyIsoName = spec.getCurrencyIsoName();
            var item = ItemLogic.getInstance().getItemByName(this, itemName);
            var inventoryCondition = InventoryConditionLogic.getInstance().getInventoryConditionByName(this, inventoryConditionName);
            var currency = CurrencyLogic.getInstance().getCurrencyByName(this, currencyIsoName);

            if(!hasExecutionErrors()) {
                var offerItemControl = Session.getModelController(OfferItemControl.class);
                var offerItem = offerItemControl.getOfferItem(offer, item);

                if(offerItem != null) {
                    var itemDetail = item.getLastDetail();
                    var unitOfMeasureKind = item.getLastDetail().getUnitOfMeasureKind();
                    var unitOfMeasureType = UnitOfMeasureTypeLogic.getInstance().getUnitOfMeasureTypeByName(this,
                            unitOfMeasureKind, unitOfMeasureTypeName);

                    if(!hasExecutionErrors()) {
                        var offerItemPrice = offerItemControl.getOfferItemPrice(offerItem, inventoryCondition,
                                unitOfMeasureType, currency);

                        if(offerItemPrice != null) {
                            var itemPriceTypeName = itemDetail.getItemPriceType().getItemPriceTypeName();

                            if(editMode.equals(EditMode.LOCK)) {
                                result.setOfferItemPrice(offerItemControl.getOfferItemPriceTransfer(getUserVisit(), offerItemPrice));

                                if(lockEntity(offerItem)) {
                                    var edit = OfferEditFactory.getOfferItemPriceEdit();

                                    result.setEdit(edit);

                                    if(itemPriceTypeName.equals(ItemPriceTypes.FIXED.name())) {
                                        var offerItemFixedPrice = offerItemControl.getOfferItemFixedPrice(offerItemPrice);

                                        edit.setUnitPrice(AmountUtils.getInstance().formatPriceUnit(currency, offerItemFixedPrice.getUnitPrice()));
                                    } else if(itemPriceTypeName.equals(ItemPriceTypes.VARIABLE.name())) {
                                        var offerItemVariablePrice = offerItemControl.getOfferItemVariablePrice(offerItemPrice);

                                        edit.setMinimumUnitPrice(AmountUtils.getInstance().formatPriceUnit(currency, offerItemVariablePrice.getMinimumUnitPrice()));
                                        edit.setMaximumUnitPrice(AmountUtils.getInstance().formatPriceUnit(currency, offerItemVariablePrice.getMaximumUnitPrice()));
                                        edit.setUnitPriceIncrement(AmountUtils.getInstance().formatPriceUnit(currency, offerItemVariablePrice.getUnitPriceIncrement()));
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownItemPriceType.name(), itemPriceTypeName);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                                }

                                result.setEntityLock(getEntityLockTransfer(offerItem));
                            } else if(editMode.equals(EditMode.UPDATE)) {
                                if(itemPriceTypeName.equals(ItemPriceTypes.FIXED.name())) {
                                    var strUnitPrice = edit.getUnitPrice();

                                    if(strUnitPrice != null) {
                                        var unitPrice = Long.valueOf(strUnitPrice);

                                        if(lockEntityForUpdate(offerItem)) {
                                            try {
                                                var offerItemFixedPriceValue = offerItemControl.getOfferItemFixedPriceValueForUpdate(offerItemPrice);

                                                offerItemFixedPriceValue.setUnitPrice(unitPrice);

                                                OfferItemLogic.getInstance().updateOfferItemFixedPriceFromValue(offerItemFixedPriceValue, getPartyPK());
                                            } finally {
                                                unlockEntity(offerItem);
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.MissingUnitPrice.name());
                                    }
                                } else if(itemPriceTypeName.equals(ItemPriceTypes.VARIABLE.name())) {
                                    var strMinimumUnitPrice = edit.getMinimumUnitPrice();
                                    Long minimumUnitPrice = null;
                                    var strMaximumUnitPrice = edit.getMaximumUnitPrice();
                                    Long maximumUnitPrice = null;
                                    var strUnitPriceIncrement = edit.getUnitPriceIncrement();
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
                                        if(lockEntityForUpdate(offerItem)) {
                                            try {
                                                var offerItemVariablePriceValue = offerItemControl.getOfferItemVariablePriceValueForUpdate(offerItemPrice);

                                                offerItemVariablePriceValue.setMinimumUnitPrice(minimumUnitPrice);
                                                offerItemVariablePriceValue.setMaximumUnitPrice(maximumUnitPrice);
                                                offerItemVariablePriceValue.setUnitPriceIncrement(unitPriceIncrement);

                                                OfferItemLogic.getInstance().updateOfferItemVariablePriceFromValue(offerItemVariablePriceValue, getPartyPK());
                                            } finally {
                                                unlockEntity(offerItem);
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                                        }
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownItemPriceType.name(), itemPriceTypeName);
                                }
                            }
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateOfferItemPrice.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownOfferItem.name(), offerName, itemName);
                }
            }
            } else {
                addExecutionError(ExecutionErrors.CannotManuallyEditOfferItemPriceWhenOfferItemPriceFilterSet.name(),
                        offerDetail.getOfferName());
            }
        }
        
        return result;
    }
    
}

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

import com.echothree.control.user.offer.common.edit.OfferEditFactory;
import com.echothree.control.user.offer.common.edit.OfferItemPriceEdit;
import com.echothree.control.user.offer.common.result.EditOfferItemPriceResult;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.control.user.offer.common.spec.OfferItemPriceSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.item.common.ItemPriceTypes;
import com.echothree.model.control.offer.server.control.OfferItemControl;
import com.echothree.model.control.offer.server.logic.OfferItemLogic;
import com.echothree.model.control.offer.server.logic.OfferItemPriceLogic;
import com.echothree.model.control.offer.server.logic.OfferLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.offer.server.entity.OfferItem;
import com.echothree.model.data.offer.server.entity.OfferItemPrice;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.string.AmountUtils;
import com.echothree.util.server.validation.Validator;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditOfferItemPriceCommand
        extends BaseAbstractEditCommand<OfferItemPriceSpec, OfferItemPriceEdit, EditOfferItemPriceResult, OfferItemPrice, OfferItem> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.OfferItemPrice.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("OfferName", FieldType.ENTITY_NAME, true, null, 20L),
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("UnitPrice", FieldType.UNSIGNED_PRICE_UNIT, false, null, null),
                new FieldDefinition("MinimumUnitPrice", FieldType.UNSIGNED_PRICE_UNIT, false, null, null),
                new FieldDefinition("MaximumUnitPrice", FieldType.UNSIGNED_PRICE_UNIT, false, null, null),
                new FieldDefinition("UnitPriceIncrement", FieldType.UNSIGNED_PRICE_UNIT, false, null, null)
        );
    }

    @Inject
    AccountingControl accountingControl;

    @Inject
    OfferItemControl offerItemControl;

    @Inject
    OfferItemLogic offerItemLogic;

    @Inject
    OfferItemPriceLogic offerItemPriceLogic;

    @Inject
    OfferLogic offerLogic;

    /** Creates a new instance of EditOfferItemPriceCommand */
    public EditOfferItemPriceCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    protected void setupValidatorForEdit(Validator validator, BaseForm specForm) {
        var currencyIsoName = spec.getCurrencyIsoName();

        validator.setCurrency(accountingControl.getCurrencyByIsoName(currencyIsoName));
    }

    @Override
    public EditOfferItemPriceResult getResult() {
        return OfferResultFactory.getEditOfferItemPriceResult();
    }

    @Override
    public OfferItemPriceEdit getEdit() {
        return OfferEditFactory.getOfferItemPriceEdit();
    }

    @Override
    public OfferItemPrice getEntity(EditOfferItemPriceResult result) {
        OfferItemPrice offerItemPrice = null;
        var offer = offerLogic.getOfferByName(this, spec.getOfferName());

        if(!hasExecutionErrors()) {
            if(editMode != EditMode.ABANDON && offer.getLastDetail().getOfferItemPriceFilter() != null) {
                addExecutionError(ExecutionErrors.CannotManuallyEditOfferItemPriceWhenOfferItemPriceFilterSet.name(), spec.getOfferName());
            } else {
                offerItemPrice = offerItemPriceLogic.getOfferItemPriceBySpec(this, spec, editModeToEntityPermission(editMode));
            }
        }

        return offerItemPrice;
    }

    @Override
    public OfferItem getLockEntity(OfferItemPrice offerItemPrice) {
        return offerItemPrice.getOfferItem();
    }

    @Override
    public void fillInResult(EditOfferItemPriceResult result, OfferItemPrice offerItemPrice) {
        result.setOfferItemPrice(offerItemControl.getOfferItemPriceTransfer(getUserVisit(), offerItemPrice));
    }

    private String getItemPriceTypeName(OfferItemPrice offerItemPrice) {
        return offerItemPrice.getOfferItem().getItem().getLastDetail().getItemPriceType().getItemPriceTypeName();
    }

    @Override
    public void doLock(OfferItemPriceEdit edit, OfferItemPrice offerItemPrice) {
        var itemPriceTypeName = getItemPriceTypeName(offerItemPrice);
        var currency = offerItemPrice.getCurrency();
        var amountUtils = AmountUtils.getInstance();

        if(itemPriceTypeName.equals(ItemPriceTypes.FIXED.name())) {
            var offerItemFixedPrice = offerItemControl.getOfferItemFixedPrice(offerItemPrice);

            edit.setUnitPrice(amountUtils.formatPriceUnit(currency, offerItemFixedPrice.getUnitPrice()));
        } else if(itemPriceTypeName.equals(ItemPriceTypes.VARIABLE.name())) {
            var offerItemVariablePrice = offerItemControl.getOfferItemVariablePrice(offerItemPrice);

            edit.setMinimumUnitPrice(amountUtils.formatPriceUnit(currency, offerItemVariablePrice.getMinimumUnitPrice()));
            edit.setMaximumUnitPrice(amountUtils.formatPriceUnit(currency, offerItemVariablePrice.getMaximumUnitPrice()));
            edit.setUnitPriceIncrement(amountUtils.formatPriceUnit(currency, offerItemVariablePrice.getUnitPriceIncrement()));
        } else {
            addExecutionError(ExecutionErrors.UnknownItemPriceType.name(), itemPriceTypeName);
        }
    }

    @Override
    public void canUpdate(OfferItemPrice offerItemPrice) {
        var itemPriceTypeName = getItemPriceTypeName(offerItemPrice);

        if(itemPriceTypeName.equals(ItemPriceTypes.FIXED.name())) {
            if(edit.getUnitPrice() == null) {
                addExecutionError(ExecutionErrors.MissingUnitPrice.name());
            }
        } else if(itemPriceTypeName.equals(ItemPriceTypes.VARIABLE.name())) {
            if(edit.getMinimumUnitPrice() == null) {
                addExecutionError(ExecutionErrors.MissingMinimumUnitPrice.name());
            }
            if(edit.getMaximumUnitPrice() == null) {
                addExecutionError(ExecutionErrors.MissingMaximumUnitPrice.name());
            }
            if(edit.getUnitPriceIncrement() == null) {
                addExecutionError(ExecutionErrors.MissingUnitPriceIncrement.name());
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemPriceType.name(), itemPriceTypeName);
        }
    }

    @Override
    public void doUpdate(OfferItemPrice offerItemPrice) {
        if(getItemPriceTypeName(offerItemPrice).equals(ItemPriceTypes.FIXED.name())) {
            var offerItemFixedPriceValue = offerItemControl.getOfferItemFixedPriceValueForUpdate(offerItemPrice);

            offerItemFixedPriceValue.setUnitPrice(Long.valueOf(edit.getUnitPrice()));

            offerItemLogic.updateOfferItemFixedPriceFromValue(offerItemFixedPriceValue, getPartyPK());
        } else {
            var offerItemVariablePriceValue = offerItemControl.getOfferItemVariablePriceValueForUpdate(offerItemPrice);

            offerItemVariablePriceValue.setMinimumUnitPrice(Long.valueOf(edit.getMinimumUnitPrice()));
            offerItemVariablePriceValue.setMaximumUnitPrice(Long.valueOf(edit.getMaximumUnitPrice()));
            offerItemVariablePriceValue.setUnitPriceIncrement(Long.valueOf(edit.getUnitPriceIncrement()));

            offerItemLogic.updateOfferItemVariablePriceFromValue(offerItemVariablePriceValue, getPartyPK());
        }
    }

}

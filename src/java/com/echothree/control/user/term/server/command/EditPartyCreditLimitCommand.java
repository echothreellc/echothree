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

package com.echothree.control.user.term.server.command;

import com.echothree.control.user.term.common.edit.PartyCreditLimitEdit;
import com.echothree.control.user.term.common.edit.TermEditFactory;
import com.echothree.control.user.term.common.result.EditPartyCreditLimitResult;
import com.echothree.control.user.term.common.result.TermResultFactory;
import com.echothree.control.user.term.common.spec.PartyCreditLimitSpec;
import com.echothree.model.control.accounting.server.logic.CurrencyLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.PartyChainLogic;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.term.server.entity.PartyCreditLimit;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.string.AmountUtils;
import com.echothree.util.server.validation.Validator;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditPartyCreditLimitCommand
        extends BaseAbstractEditCommand<PartyCreditLimitSpec, PartyCreditLimitEdit, EditPartyCreditLimitResult, PartyCreditLimit, Party> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CreditLimit", FieldType.UNSIGNED_PRICE_LINE, false, null, null),
                new FieldDefinition("PotentialCreditLimit", FieldType.UNSIGNED_PRICE_LINE, false, null, null)
        );
    }

    private Currency currency;
    private boolean currencyResolved;

    @Inject
    TermControl termControl;

    @Inject
    CurrencyLogic currencyLogic;

    @Inject
    PartyChainLogic partyChainLogic;

    @Inject
    PartyLogic partyLogic;

    /** Creates a new instance of EditPartyCreditLimitCommand */
    public EditPartyCreditLimitCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    private Currency resolveCurrency() {
        if(!currencyResolved) {
            currency = currencyLogic.getCurrencyByName(this, spec.getCurrencyIsoName());
            currencyResolved = true;
        }

        return currency;
    }

    @Override
    protected void setupValidatorForEdit(Validator validator, BaseForm specForm) {
        validator.setCurrency(resolveCurrency());
    }

    @Override
    public EditPartyCreditLimitResult getResult() {
        return TermResultFactory.getEditPartyCreditLimitResult();
    }

    @Override
    public PartyCreditLimitEdit getEdit() {
        return TermEditFactory.getPartyCreditLimitEdit();
    }

    @Override
    public PartyCreditLimit getEntity(EditPartyCreditLimitResult result) {
        PartyCreditLimit partyCreditLimit = null;
        var partyName = spec.getPartyName();
        var currencyIsoName = spec.getCurrencyIsoName();
        var party = partyLogic.getPartyByName(this, partyName);
        var currency = resolveCurrency();

        if(!hasExecutionErrors()) {
            partyCreditLimit = termControl.getPartyCreditLimit(party, currency, editModeToEntityPermission(editMode));

            if(partyCreditLimit == null) {
                addExecutionError(ExecutionErrors.UnknownPartyCreditLimit.name(), partyName, currencyIsoName);
            }
        }

        return partyCreditLimit;
    }

    @Override
    public Party getLockEntity(PartyCreditLimit partyCreditLimit) {
        return partyCreditLimit.getParty();
    }

    @Override
    public void fillInResult(EditPartyCreditLimitResult result, PartyCreditLimit partyCreditLimit) {
        result.setPartyCreditLimit(termControl.getPartyCreditLimitTransfer(getUserVisit(), partyCreditLimit));
    }

    @Override
    public void doLock(PartyCreditLimitEdit edit, PartyCreditLimit partyCreditLimit) {
        var currency = partyCreditLimit.getCurrency();
        var amountUtils = AmountUtils.getInstance();

        edit.setCreditLimit(amountUtils.formatPriceLine(currency, partyCreditLimit.getCreditLimit()));
        edit.setPotentialCreditLimit(amountUtils.formatPriceLine(currency, partyCreditLimit.getPotentialCreditLimit()));
    }

    @Override
    public void doUpdate(PartyCreditLimit partyCreditLimit) {
        var partyCreditLimitValue = termControl.getPartyCreditLimitValue(partyCreditLimit);
        var creditLimit = edit.getCreditLimit();
        var potentialCreditLimit = edit.getPotentialCreditLimit();

        partyCreditLimitValue.setCreditLimit(creditLimit == null ? null : Long.valueOf(creditLimit));
        partyCreditLimitValue.setPotentialCreditLimit(potentialCreditLimit == null ? null : Long.valueOf(potentialCreditLimit));

        if(partyCreditLimitValue.hasBeenModified()) {
            var party = partyCreditLimit.getParty();
            var partyTypeName = party.getLastDetail().getPartyType().getPartyTypeName();
            var updatedBy = getPartyPK();

            termControl.updatePartyCreditLimitFromValue(partyCreditLimitValue, updatedBy);

            if(PartyTypes.CUSTOMER.name().equals(partyTypeName)) {
                // Throw on chain errors so that the transaction is rolled back.
                partyChainLogic.createPartyCreditLimitChangedChainInstance(null, party, updatedBy);
            }
        }
    }

}

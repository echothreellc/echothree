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

package com.echothree.control.user.term.server.command;

import com.echothree.control.user.term.common.edit.PartyCreditLimitEdit;
import com.echothree.control.user.term.common.edit.TermEditFactory;
import com.echothree.control.user.term.common.form.EditPartyCreditLimitForm;
import com.echothree.control.user.term.common.result.TermResultFactory;
import com.echothree.control.user.term.common.spec.PartyCreditLimitSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyChainLogic;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;
import com.echothree.util.server.validation.Validator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditPartyCreditLimitCommand
        extends BaseEditCommand<PartyCreditLimitSpec, PartyCreditLimitEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CreditLimit", FieldType.UNSIGNED_PRICE_LINE, false, null, null),
                new FieldDefinition("PotentialCreditLimit", FieldType.UNSIGNED_PRICE_LINE, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditPartyCreditLimitCommand */
    public EditPartyCreditLimitCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected void setupValidatorForEdit(Validator validator, BaseForm specForm) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var currencyIsoName = spec.getCurrencyIsoName();
        validator.setCurrency(accountingControl.getCurrencyByIsoName(currencyIsoName));
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var result = TermResultFactory.getEditPartyCreditLimitResult();
        var partyName = spec.getPartyName();
        var party = partyControl.getPartyByName(partyName);
        
        if(party != null) {
            var accountingControl = Session.getModelController(AccountingControl.class);
            var currencyIsoName = spec.getCurrencyIsoName();
            var currency = accountingControl.getCurrencyByIsoName(currencyIsoName);
            
            if(currency != null) {
                var termControl = Session.getModelController(TermControl.class);
                
                if(editMode.equals(EditMode.LOCK)) {
                    var partyCreditLimit = termControl.getPartyCreditLimit(party, currency);
                    
                    if(partyCreditLimit != null) {
                        result.setPartyCreditLimit(termControl.getPartyCreditLimitTransfer(getUserVisit(), partyCreditLimit));
                        
                        if(lockEntity(party)) {
                            var edit = TermEditFactory.getPartyCreditLimitEdit();
                            
                            result.setEdit(edit);
                            edit.setCreditLimit(AmountUtils.getInstance().formatPriceLine(currency, partyCreditLimit.getCreditLimit()));
                            edit.setPotentialCreditLimit(AmountUtils.getInstance().formatPriceLine(currency, partyCreditLimit.getPotentialCreditLimit()));
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(party));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPartyCreditLimit.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    var partyCreditLimitValue = termControl.getPartyCreditLimitValueForUpdate(party, currency);
                    
                    if(partyCreditLimitValue != null) {
                        if(lockEntityForUpdate(party)) {
                            var strCreditLimit = edit.getCreditLimit();
                            var creditLimit = strCreditLimit == null? null: Long.valueOf(strCreditLimit);
                            var strPotentialCreditLimit = edit.getPotentialCreditLimit();
                            var potentialCreditLimit = strPotentialCreditLimit == null? null: Long.valueOf(strPotentialCreditLimit);
                            
                            try {
                                partyCreditLimitValue.setCreditLimit(creditLimit);
                                partyCreditLimitValue.setPotentialCreditLimit(potentialCreditLimit);
                                
                                if(partyCreditLimitValue.hasBeenModified()) {
                                    var partyTypeName = party.getLastDetail().getPartyType().getPartyTypeName();
                                    var updatedBy = getPartyPK();
                                    
                                    termControl.updatePartyCreditLimitFromValue(partyCreditLimitValue, updatedBy);
                                    
                                    if(partyTypeName.equals(PartyTypes.CUSTOMER.name())) {
                                        // ExecutionErrorAccumulator is passed in as null so that an Exception will be thrown if there is an error.
                                        PartyChainLogic.getInstance().createPartyCreditLimitChangedChainInstance(null, party, updatedBy);
                                    }
                                }
                            } finally {
                                unlockEntity(party);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPartyCreditLimit.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), currencyIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }
        
        return result;
    }
    
}

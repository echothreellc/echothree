// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.control.user.term.common.result.EditPartyCreditLimitResult;
import com.echothree.control.user.term.common.result.TermResultFactory;
import com.echothree.control.user.term.common.spec.PartyCreditLimitSpec;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.party.server.logic.PartyChainLogic;
import com.echothree.model.control.term.server.TermControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.term.server.entity.PartyCreditLimit;
import com.echothree.model.data.term.server.value.PartyCreditLimitValue;
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
    public EditPartyCreditLimitCommand(UserVisitPK userVisitPK, EditPartyCreditLimitForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected void setupValidatorForEdit(Validator validator, BaseForm specForm) {
        var accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
        String currencyIsoName = spec.getCurrencyIsoName();
        validator.setCurrency(accountingControl.getCurrencyByIsoName(currencyIsoName));
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        EditPartyCreditLimitResult result = TermResultFactory.getEditPartyCreditLimitResult();
        String partyName = spec.getPartyName();
        Party party = partyControl.getPartyByName(partyName);
        
        if(party != null) {
            var accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
            String currencyIsoName = spec.getCurrencyIsoName();
            Currency currency = accountingControl.getCurrencyByIsoName(currencyIsoName);
            
            if(currency != null) {
                var termControl = (TermControl)Session.getModelController(TermControl.class);
                
                if(editMode.equals(EditMode.LOCK)) {
                    PartyCreditLimit partyCreditLimit = termControl.getPartyCreditLimit(party, currency);
                    
                    if(partyCreditLimit != null) {
                        result.setPartyCreditLimit(termControl.getPartyCreditLimitTransfer(getUserVisit(), partyCreditLimit));
                        
                        if(lockEntity(party)) {
                            PartyCreditLimitEdit edit = TermEditFactory.getPartyCreditLimitEdit();
                            
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
                    PartyCreditLimitValue partyCreditLimitValue = termControl.getPartyCreditLimitValueForUpdate(party, currency);
                    
                    if(partyCreditLimitValue != null) {
                        if(lockEntityForUpdate(party)) {
                            String strCreditLimit = edit.getCreditLimit();
                            Long creditLimit = strCreditLimit == null? null: Long.valueOf(strCreditLimit);
                            String strPotentialCreditLimit = edit.getPotentialCreditLimit();
                            Long potentialCreditLimit = strPotentialCreditLimit == null? null: Long.valueOf(strPotentialCreditLimit);
                            
                            try {
                                partyCreditLimitValue.setCreditLimit(creditLimit);
                                partyCreditLimitValue.setPotentialCreditLimit(potentialCreditLimit);
                                
                                if(partyCreditLimitValue.hasBeenModified()) {
                                    String partyTypeName = party.getLastDetail().getPartyType().getPartyTypeName();
                                    PartyPK updatedBy = getPartyPK();
                                    
                                    termControl.updatePartyCreditLimitFromValue(partyCreditLimitValue, updatedBy);
                                    
                                    if(partyTypeName.equals(PartyConstants.PartyType_CUSTOMER)) {
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

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

import com.echothree.control.user.term.common.edit.PartyTermEdit;
import com.echothree.control.user.term.common.edit.TermEditFactory;
import com.echothree.control.user.term.common.form.EditPartyTermForm;
import com.echothree.control.user.term.common.result.TermResultFactory;
import com.echothree.control.user.term.common.spec.PartyTermSpec;
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
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditPartyTermCommand
        extends BaseEditCommand<PartyTermSpec, PartyTermEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TermName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Taxable", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditPartyTermCommand */
    public EditPartyTermCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var result = TermResultFactory.getEditPartyTermResult();
        var partyName = spec.getPartyName();
        var party = partyControl.getPartyByName(partyName);
        
        if(party != null) {
            var termControl = Session.getModelController(TermControl.class);
            
            if(editMode.equals(EditMode.LOCK)) {
                var partyTerm = termControl.getPartyTerm(party);
                
                if(partyTerm != null) {
                    result.setPartyTerm(termControl.getPartyTermTransfer(getUserVisit(), partyTerm));
                    
                    if(lockEntity(party)) {
                        var edit = TermEditFactory.getPartyTermEdit();
                        
                        result.setEdit(edit);
                        edit.setTermName(partyTerm.getTerm().getLastDetail().getTermName());
                        edit.setTaxable(partyTerm.getTaxable().toString());
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                    }
                    
                    result.setEntityLock(getEntityLockTransfer(party));
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyTerm.name());
                }
            } else if(editMode.equals(EditMode.UPDATE)) {
                var partyTermValue = termControl.getPartyTermValueForUpdate(party);
                
                if(partyTermValue != null) {
                    if(lockEntityForUpdate(party)) {
                        var termName = edit.getTermName();
                        var term = termControl.getTermByName(termName);
                        
                        if(term != null) {
                            var taxable = Boolean.valueOf(edit.getTaxable());
                            
                            try {
                                partyTermValue.setTermPK(term.getPrimaryKey());
                                partyTermValue.setTaxable(taxable);
                                
                                if(partyTermValue.hasBeenModified()) {
                                    var partyTypeName = party.getLastDetail().getPartyType().getPartyTypeName();
                                    var updatedBy = getPartyPK();
                                    
                                    termControl.updatePartyTermFromValue(partyTermValue, updatedBy);
                                    
                                    if(partyTypeName.equals(PartyTypes.CUSTOMER.name())) {
                                        // ExecutionErrorAccumulator is passed in as null so that an Exception will be thrown if there is an error.
                                        PartyChainLogic.getInstance().createPartyTermChangedChainInstance(null, party, updatedBy);
                                    }
                                }
                            } finally {
                                unlockEntity(party);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownTermName.name(), termName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyTerm.name());
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }
        
        return result;
    }
    
}

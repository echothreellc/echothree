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

import com.echothree.control.user.term.common.edit.PartyTermEdit;
import com.echothree.control.user.term.common.edit.TermEditFactory;
import com.echothree.control.user.term.common.result.EditPartyTermResult;
import com.echothree.control.user.term.common.result.TermResultFactory;
import com.echothree.control.user.term.common.spec.PartyTermSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.PartyChainLogic;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.control.term.server.logic.TermLogic;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.term.server.entity.PartyTerm;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditPartyTermCommand
        extends BaseAbstractEditCommand<PartyTermSpec, PartyTermEdit, EditPartyTermResult, PartyTerm, Party> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> CUSTOMER_EDIT_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> VENDOR_EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null)
        );

        CUSTOMER_EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("TermName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Taxable", FieldType.BOOLEAN, true, null, null)
        );

        VENDOR_EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("TermName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Taxable", FieldType.NULL, false, null, null)
        );
    }

    private Party party;
    private boolean partyResolved;
    private Term term;

    @Inject
    TermControl termControl;

    @Inject
    PartyChainLogic partyChainLogic;

    @Inject
    PartyLogic partyLogic;

    @Inject
    TermLogic termLogic;

    /** Creates a new instance of EditPartyTermCommand */
    public EditPartyTermCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, null);
    }

    private Party resolveParty() {
        if(!partyResolved) {
            party = partyLogic.getPartyByName(this, spec.getPartyName(), PartyTypes.CUSTOMER.name(), PartyTypes.VENDOR.name());
            partyResolved = true;
        }

        return party;
    }

    @Override
    protected List<FieldDefinition> getEditFieldDefinitions() {
        var party = resolveParty();

        return party != null && PartyTypes.VENDOR.name().equals(party.getLastDetail().getPartyType().getPartyTypeName())
                ? VENDOR_EDIT_FIELD_DEFINITIONS : CUSTOMER_EDIT_FIELD_DEFINITIONS;
    }

    @Override
    public EditPartyTermResult getResult() {
        return TermResultFactory.getEditPartyTermResult();
    }

    @Override
    public PartyTermEdit getEdit() {
        return TermEditFactory.getPartyTermEdit();
    }

    @Override
    public PartyTerm getEntity(EditPartyTermResult result) {
        var party = resolveParty();
        PartyTerm partyTerm = null;

        if(!hasExecutionErrors()) {
            partyTerm = termLogic.getPartyTerm(this, party, editModeToEntityPermission(editMode));
        }

        return partyTerm;
    }

    @Override
    public Party getLockEntity(PartyTerm partyTerm) {
        return partyTerm.getParty();
    }

    @Override
    public void fillInResult(EditPartyTermResult result, PartyTerm partyTerm) {
        result.setPartyTerm(termControl.getPartyTermTransfer(getUserVisit(), partyTerm));
    }

    @Override
    public void doLock(PartyTermEdit edit, PartyTerm partyTerm) {
        var taxable = partyTerm.getTaxable();

        edit.setTermName(partyTerm.getTerm().getLastDetail().getTermName());
        edit.setTaxable(taxable == null ? null : taxable.toString());
    }

    @Override
    public void canUpdate(PartyTerm partyTerm) {
        term = termLogic.getTermByName(this, edit.getTermName());
    }

    @Override
    public void doUpdate(PartyTerm partyTerm) {
        var partyTermValue = termControl.getPartyTermValue(partyTerm);
        var taxable = edit.getTaxable();

        partyTermValue.setTermPK(term.getPrimaryKey());
        partyTermValue.setTaxable(taxable == null ? null : Boolean.valueOf(taxable));

        if(partyTermValue.hasBeenModified()) {
            var party = partyTerm.getParty();
            var partyTypeName = party.getLastDetail().getPartyType().getPartyTypeName();
            var updatedBy = getPartyPK();

            termControl.updatePartyTermFromValue(partyTermValue, updatedBy);

            if(PartyTypes.CUSTOMER.name().equals(partyTypeName)) {
                // Throw on chain errors so that the transaction is rolled back.
                partyChainLogic.createPartyTermChangedChainInstance(null, party, updatedBy);
            }
        }
    }
    
}

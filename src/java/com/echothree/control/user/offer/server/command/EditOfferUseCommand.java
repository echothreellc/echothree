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
import com.echothree.control.user.offer.common.edit.OfferUseEdit;
import com.echothree.control.user.offer.common.result.EditOfferUseResult;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.control.user.offer.common.spec.OfferUseSpec;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.control.offer.server.logic.OfferUseLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.logic.SequenceLogic;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditOfferUseCommand
        extends BaseAbstractEditCommand<OfferUseSpec, OfferUseEdit, EditOfferUseResult, OfferUse, OfferUse> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.OfferUse.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("OfferName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UseName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("SalesOrderSequenceName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    @Inject
    OfferUseControl offerUseControl;

    @Inject
    OfferUseLogic offerUseLogic;

    @Inject
    SequenceLogic sequenceLogic;

    /** Creates a new instance of EditOfferUseCommand */
    public EditOfferUseCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditOfferUseResult getResult() {
        return OfferResultFactory.getEditOfferUseResult();
    }

    @Override
    public OfferUseEdit getEdit() {
        return OfferEditFactory.getOfferUseEdit();
    }

    @Override
    public OfferUse getEntity(EditOfferUseResult result) {
        return offerUseLogic.getOfferUseByName(this, spec.getOfferName(), spec.getUseName(), editModeToEntityPermission(editMode));
    }

    @Override
    public OfferUse getLockEntity(OfferUse offerUse) {
        return offerUse;
    }

    @Override
    public void fillInResult(EditOfferUseResult result, OfferUse offerUse) {
        result.setOfferUse(offerUseControl.getOfferUseTransfer(getUserVisit(), offerUse));
    }

    private Sequence salesOrderSequence;

    @Override
    public void doLock(OfferUseEdit edit, OfferUse offerUse) {
        var salesOrderSequence = offerUse.getLastDetail().getSalesOrderSequence();

        edit.setSalesOrderSequenceName(salesOrderSequence == null? null: salesOrderSequence.getLastDetail().getSequenceName());
    }

    @Override
    public void canUpdate(OfferUse offerUse) {
        var salesOrderSequenceName = edit.getSalesOrderSequenceName();

        salesOrderSequence = salesOrderSequenceName == null? null: sequenceLogic.getSequenceByName(this,
                SequenceTypes.SALES_ORDER.name(), salesOrderSequenceName);
    }

    @Override
    public void doUpdate(OfferUse offerUse) {
        var offerUseDetailValue = offerUseControl.getOfferUseDetailValueForUpdate(offerUse);

        offerUseDetailValue.setSalesOrderSequencePK(salesOrderSequence == null? null: salesOrderSequence.getPrimaryKey());

        offerUseControl.updateOfferUseFromValue(offerUseDetailValue, getPartyPK());
    }

}

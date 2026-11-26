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

package com.echothree.control.user.associate.server.command;

import com.echothree.control.user.associate.common.edit.AssociateEditFactory;
import com.echothree.control.user.associate.common.edit.AssociateProgramEdit;
import com.echothree.control.user.associate.common.result.AssociateResultFactory;
import com.echothree.control.user.associate.common.result.EditAssociateProgramResult;
import com.echothree.control.user.associate.common.spec.AssociateProgramUniversalSpec;
import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.control.associate.server.logic.AssociateProgramLogic;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.logic.SequenceLogic;
import com.echothree.model.data.associate.server.entity.AssociateProgram;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.string.PercentUtils;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditAssociateProgramCommand
        extends BaseEditCommand<AssociateProgramUniversalSpec, AssociateProgramEdit, EditAssociateProgramResult, AssociateProgram, AssociateProgram> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("AssociateProgramName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("AssociateProgramName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("AssociateSequenceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AssociatePartyContactMechanismSequenceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AssociateReferralSequenceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ItemIndirectSalePercent", FieldType.FRACTIONAL_PERCENT, false, null, null),
                new FieldDefinition("ItemDirectSalePercent", FieldType.FRACTIONAL_PERCENT, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    AssociateControl associateControl;

    @Inject
    AssociateProgramLogic associateProgramLogic;

    @Inject
    SequenceLogic sequenceLogic;

    private Sequence associateSequence;
    private Sequence associatePartyContactMechanismSequence;
    private Sequence associateReferralSequence;

    /** Creates a new instance of EditAssociateProgramCommand */
    public EditAssociateProgramCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditAssociateProgramResult getResult() {
        return AssociateResultFactory.getEditAssociateProgramResult();
    }

    @Override
    public AssociateProgramEdit getEdit() {
        return AssociateEditFactory.getAssociateProgramEdit();
    }

    @Override
    public AssociateProgram getEntity(EditAssociateProgramResult result) {
        return associateProgramLogic.getAssociateProgramByUniversalSpec(this, spec, false, editModeToEntityPermission(editMode));
    }

    @Override
    public AssociateProgram getLockEntity(AssociateProgram associateProgram) {
        return associateProgram;
    }

    @Override
    public void fillInResult(EditAssociateProgramResult result, AssociateProgram associateProgram) {
        result.setAssociateProgram(associateControl.getAssociateProgramTransfer(getUserVisit(), associateProgram));
    }

    @Override
    public void doLock(AssociateProgramEdit edit, AssociateProgram associateProgram) {
        var associateProgramDescription = associateControl.getAssociateProgramDescription(associateProgram, getPreferredLanguage());
        var associateProgramDetail = associateProgram.getLastDetail();
        var associateSequence = associateProgramDetail.getAssociateSequence();
        var associatePartyContactMechanismSequence = associateProgramDetail.getAssociatePartyContactMechanismSequence();
        var associateReferralSequence = associateProgramDetail.getAssociateReferralSequence();

        edit.setAssociateProgramName(associateProgramDetail.getAssociateProgramName());
        edit.setAssociateSequenceName(associateSequence == null? null: associateSequence.getLastDetail().getSequenceName());
        edit.setAssociatePartyContactMechanismSequenceName(associatePartyContactMechanismSequence == null? null: associatePartyContactMechanismSequence.getLastDetail().getSequenceName());
        edit.setAssociateReferralSequenceName(associateReferralSequence == null? null: associateReferralSequence.getLastDetail().getSequenceName());
        edit.setItemIndirectSalePercent(PercentUtils.getInstance().formatFractionalPercent(associateProgramDetail.getItemIndirectSalePercent()));
        edit.setItemDirectSalePercent(PercentUtils.getInstance().formatFractionalPercent(associateProgramDetail.getItemDirectSalePercent()));
        edit.setIsDefault(associateProgramDetail.getIsDefault().toString());
        edit.setSortOrder(associateProgramDetail.getSortOrder().toString());

        if(associateProgramDescription != null) {
            edit.setDescription(associateProgramDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(AssociateProgram associateProgram) {
        var associateProgramName = edit.getAssociateProgramName();
        var duplicateAssociateProgram = associateControl.getAssociateProgramByName(associateProgramName);
        var associateSequenceName = edit.getAssociateSequenceName();
        var associatePartyContactMechanismSequenceName = edit.getAssociatePartyContactMechanismSequenceName();
        var associateReferralSequenceName = edit.getAssociateReferralSequenceName();

        if(duplicateAssociateProgram != null && !associateProgram.equals(duplicateAssociateProgram)) {
            addExecutionError(ExecutionErrors.DuplicateAssociateProgramName.name(), associateProgramName);
        }

        associateSequence = associateSequenceName == null ? null : sequenceLogic.getSequenceByName(this,
                SequenceTypes.ASSOCIATE.name(), associateSequenceName);
        associatePartyContactMechanismSequence = associatePartyContactMechanismSequenceName == null ? null : sequenceLogic.getSequenceByName(this,
                SequenceTypes.ASSOCIATE_PARTY_CONTACT_MECHANISM.name(), associatePartyContactMechanismSequenceName);
        associateReferralSequence = associateReferralSequenceName == null ? null : sequenceLogic.getSequenceByName(this,
                SequenceTypes.ASSOCIATE_REFERRAL.name(), associateReferralSequenceName);
    }

    @Override
    public void doUpdate(AssociateProgram associateProgram) {
        var partyPK = getPartyPK();
        var itemIndirectSalePercent = edit.getItemIndirectSalePercent();
        var itemDirectSalePercent = edit.getItemDirectSalePercent();
        var associateProgramDetailValue = associateControl.getAssociateProgramDetailValueForUpdate(associateProgram);
        var associateProgramDescription = associateControl.getAssociateProgramDescriptionForUpdate(associateProgram, getPreferredLanguage());
        var description = edit.getDescription();

        associateProgramDetailValue.setAssociateProgramName(edit.getAssociateProgramName());
        associateProgramDetailValue.setAssociateSequencePK(associateSequence == null? null: associateSequence.getPrimaryKey());
        associateProgramDetailValue.setAssociatePartyContactMechanismSequencePK(associatePartyContactMechanismSequence == null? null: associatePartyContactMechanismSequence.getPrimaryKey());
        associateProgramDetailValue.setAssociateReferralSequencePK(associateReferralSequence == null? null: associateReferralSequence.getPrimaryKey());
        associateProgramDetailValue.setItemIndirectSalePercent(itemIndirectSalePercent == null? null: Integer.valueOf(itemIndirectSalePercent));
        associateProgramDetailValue.setItemDirectSalePercent(itemDirectSalePercent == null? null: Integer.valueOf(itemDirectSalePercent));
        associateProgramDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        associateProgramDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        associateControl.updateAssociateProgramFromValue(associateProgramDetailValue, partyPK);

        if(associateProgramDescription == null && description != null) {
            associateControl.createAssociateProgramDescription(associateProgram, getPreferredLanguage(), description, partyPK);
        } else if(associateProgramDescription != null && description == null) {
            associateControl.deleteAssociateProgramDescription(associateProgramDescription, partyPK);
        } else if(associateProgramDescription != null && description != null) {
            var associateProgramDescriptionValue = associateControl.getAssociateProgramDescriptionValue(associateProgramDescription);

            associateProgramDescriptionValue.setDescription(description);
            associateControl.updateAssociateProgramDescriptionFromValue(associateProgramDescriptionValue, partyPK);
        }
    }

}

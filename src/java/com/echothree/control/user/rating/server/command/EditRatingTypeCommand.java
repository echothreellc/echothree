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

package com.echothree.control.user.rating.server.command;

import com.echothree.control.user.rating.common.edit.RatingEditFactory;
import com.echothree.control.user.rating.common.edit.RatingTypeEdit;
import com.echothree.control.user.rating.common.result.EditRatingTypeResult;
import com.echothree.control.user.rating.common.result.RatingResultFactory;
import com.echothree.control.user.rating.common.spec.RatingTypeUniversalSpec;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.control.rating.server.logic.RatingTypeLogic;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.logic.SequenceLogic;
import com.echothree.model.data.rating.server.entity.RatingType;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditRatingTypeCommand
        extends BaseEditCommand<RatingTypeUniversalSpec, RatingTypeEdit, EditRatingTypeResult, RatingType, RatingType> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null),
                new FieldDefinition("RatingTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("RatingTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("RatingSequenceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    private Sequence ratingSequence;

    @Inject
    RatingControl ratingControl;

    @Inject
    RatingTypeLogic ratingTypeLogic;

    @Inject
    SequenceLogic sequenceLogic;
    
    /** Creates a new instance of EditRatingTypeCommand */
    public EditRatingTypeCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditRatingTypeResult getResult() {
        return RatingResultFactory.getEditRatingTypeResult();
    }

    @Override
    public RatingTypeEdit getEdit() {
        return RatingEditFactory.getRatingTypeEdit();
    }

    @Override
    public RatingType getEntity(EditRatingTypeResult result) {
        return ratingTypeLogic.getRatingTypeByUniversalSpec(this, spec, editModeToEntityPermission(editMode));
    }

    @Override
    public RatingType getLockEntity(RatingType ratingType) {
        return ratingType;
    }

    @Override
    public void fillInResult(EditRatingTypeResult result, RatingType ratingType) {
        result.setRatingType(ratingControl.getRatingTypeTransfer(getUserVisit(), ratingType));
    }

    @Override
    public void doLock(RatingTypeEdit edit, RatingType ratingType) {
        var ratingTypeDescription = ratingControl.getRatingTypeDescription(ratingType, getPreferredLanguage());
        var ratingTypeDetail = ratingType.getLastDetail();
        var ratingSequence = ratingTypeDetail.getRatingSequence();

        edit.setRatingTypeName(ratingTypeDetail.getRatingTypeName());
        edit.setRatingSequenceName(ratingSequence == null? null: ratingSequence.getLastDetail().getSequenceName());
        edit.setSortOrder(ratingTypeDetail.getSortOrder().toString());

        if(ratingTypeDescription != null) {
            edit.setDescription(ratingTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(RatingType ratingType) {
        var entityType = ratingType.getLastDetail().getEntityType();
        var ratingTypeName = edit.getRatingTypeName();
        var duplicateRatingType = ratingControl.getRatingTypeByName(entityType, ratingTypeName);
        var ratingSequenceName = edit.getRatingSequenceName();

        if(duplicateRatingType != null && !ratingType.equals(duplicateRatingType)) {
            var entityTypeDetail = entityType.getLastDetail();

            addExecutionError(ExecutionErrors.DuplicateRatingTypeName.name(),
                    entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    entityTypeDetail.getEntityTypeName(), ratingTypeName);
        }

        ratingSequence = ratingSequenceName == null ? null : sequenceLogic.getSequenceByName(this,
                SequenceTypes.RATING.name(), ratingSequenceName);
    }

    @Override
    public void doUpdate(RatingType ratingType) {
        var partyPK = getPartyPK();
        var ratingTypeDetailValue = ratingControl.getRatingTypeDetailValueForUpdate(ratingType);
        var ratingTypeDescription = ratingControl.getRatingTypeDescriptionForUpdate(ratingType, getPreferredLanguage());
        var description = edit.getDescription();

        ratingTypeDetailValue.setRatingTypeName(edit.getRatingTypeName());
        ratingTypeDetailValue.setRatingSequencePK(ratingSequence == null ? null : ratingSequence.getPrimaryKey());
        ratingTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        ratingControl.updateRatingTypeFromValue(ratingTypeDetailValue, partyPK);

        if(ratingTypeDescription == null && description != null) {
            ratingControl.createRatingTypeDescription(ratingType, getPreferredLanguage(), description, partyPK);
        } else if(ratingTypeDescription != null && description == null) {
            ratingControl.deleteRatingTypeDescription(ratingTypeDescription, partyPK);
        } else if(ratingTypeDescription != null && description != null) {
            var ratingTypeDescriptionValue = ratingControl.getRatingTypeDescriptionValue(ratingTypeDescription);

            ratingTypeDescriptionValue.setDescription(description);
            ratingControl.updateRatingTypeDescriptionFromValue(ratingTypeDescriptionValue, partyPK);
        }
    }

}

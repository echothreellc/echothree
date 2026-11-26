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

import com.echothree.control.user.rating.common.edit.RatingEdit;
import com.echothree.control.user.rating.common.edit.RatingEditFactory;
import com.echothree.control.user.rating.common.result.EditRatingResult;
import com.echothree.control.user.rating.common.result.RatingResultFactory;
import com.echothree.control.user.rating.common.spec.RatingUniversalSpec;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.control.rating.server.logic.RatingLogic;
import com.echothree.model.control.rating.server.logic.RatingTypeListItemLogic;
import com.echothree.model.data.rating.server.entity.Rating;
import com.echothree.model.data.rating.server.entity.RatingTypeListItem;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditRatingCommand
        extends BaseEditCommand<RatingUniversalSpec, RatingEdit, EditRatingResult, Rating, Rating> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("RatingName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("RatingTypeListItemName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    private RatingTypeListItem ratingTypeListItem;

    @Inject
    RatingControl ratingControl;

    @Inject
    RatingLogic ratingLogic;

    @Inject
    RatingTypeListItemLogic ratingTypeListItemLogic;

    /** Creates a new instance of EditRatingCommand */
    public EditRatingCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditRatingResult getResult() {
        return RatingResultFactory.getEditRatingResult();
    }

    @Override
    public RatingEdit getEdit() {
        return RatingEditFactory.getRatingEdit();
    }

    @Override
    public Rating getEntity(EditRatingResult result) {
        return ratingLogic.getRatingByUniversalSpec(this, spec, editModeToEntityPermission(editMode));
    }

    @Override
    public Rating getLockEntity(Rating rating) {
        return rating;
    }

    @Override
    public void fillInResult(EditRatingResult result, Rating rating) {
        result.setRating(ratingControl.getRatingTransfer(getUserVisit(), rating));
    }

    @Override
    public void doLock(RatingEdit edit, Rating rating) {
        edit.setRatingTypeListItemName(rating.getLastDetail().getRatingTypeListItem().getLastDetail().getRatingTypeListItemName());
    }

    @Override
    public void canUpdate(Rating rating) {
        var ratingType = rating.getLastDetail().getRatingTypeListItem().getLastDetail().getRatingType();

        ratingTypeListItem = ratingTypeListItemLogic.getRatingTypeListItemByName(this, ratingType, edit.getRatingTypeListItemName());
    }

    @Override
    public void doUpdate(Rating rating) {
        var ratingDetailValue = ratingControl.getRatingDetailValueForUpdate(rating);

        ratingDetailValue.setRatingTypeListItemPK(ratingTypeListItem.getPrimaryKey());

        ratingControl.updateRatingFromValue(ratingDetailValue, getPartyPK());
    }

}

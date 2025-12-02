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

package com.echothree.control.user.rating.server.command;

import com.echothree.control.user.rating.common.edit.RatingEdit;
import com.echothree.control.user.rating.common.edit.RatingEditFactory;
import com.echothree.control.user.rating.common.form.EditRatingForm;
import com.echothree.control.user.rating.common.result.RatingResultFactory;
import com.echothree.control.user.rating.common.spec.RatingSpec;
import com.echothree.model.control.rating.server.control.RatingControl;
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
public class EditRatingCommand
        extends BaseEditCommand<RatingSpec, RatingEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("RatingName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("RatingTypeListItemName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditRatingCommand */
    public EditRatingCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var ratingControl = Session.getModelController(RatingControl.class);
        var result = RatingResultFactory.getEditRatingResult();
        var ratingName = spec.getRatingName();
        var rating = ratingControl.getRatingByName(ratingName);

        if(rating != null) {
            if(editMode.equals(EditMode.LOCK)) {
                if(lockEntity(rating)) {
                    var edit = RatingEditFactory.getRatingEdit();
                    var ratingDetail = rating.getLastDetail();

                    result.setEdit(edit);

                    edit.setRatingTypeListItemName(ratingDetail.getRatingTypeListItem().getLastDetail().getRatingTypeListItemName());
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }

                result.setRating(ratingControl.getRatingTransfer(getUserVisit(), rating));
                result.setEntityLock(getEntityLockTransfer(rating));
            } else if(editMode.equals(EditMode.ABANDON)) {
                unlockEntity(rating);
            } else if(editMode.equals(EditMode.UPDATE)) {
                var ratingTypeListItemName = edit.getRatingTypeListItemName();
                var ratingTypeListItem = ratingControl.getRatingTypeListItemByName(rating.getLastDetail().getRatingTypeListItem().getLastDetail().getRatingType(),
                        ratingTypeListItemName);

                if(ratingTypeListItem != null) {
                    if(lockEntityForUpdate(rating)) {
                        try {
                            var partyPK = getPartyPK();
                            var ratingDetailValue = ratingControl.getRatingDetailValueForUpdate(rating);

                            ratingDetailValue.setRatingTypeListItemPK(ratingTypeListItem.getPrimaryKey());

                            ratingControl.updateRatingFromValue(ratingDetailValue, partyPK);
                        } finally {
                            unlockEntity(rating);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownRatingTypeListItemName.name(), ratingTypeListItemName);
                }
                
                if(hasExecutionErrors()) {
                    result.setRating(ratingControl.getRatingTransfer(getUserVisit(), rating));
                    result.setEntityLock(getEntityLockTransfer(rating));
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownRatingName.name(), ratingName);
        }

        return result;
    }
    
}

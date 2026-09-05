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

import com.echothree.control.user.rating.common.form.GetRatingForm;
import com.echothree.control.user.rating.common.result.RatingResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.control.rating.server.logic.RatingLogic;
import com.echothree.model.data.rating.server.entity.Rating;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetRatingCommand
        extends BaseSingleEntityCommand<Rating, GetRatingForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("RatingName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    RatingControl ratingControl;

    @Inject
    RatingLogic ratingLogic;
    
    /** Creates a new instance of GetRatingCommand */
    public GetRatingCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected Rating getEntity() {
        var rating = ratingLogic.getRatingByName(this, form.getRatingName());

        if(rating != null) {
            sendEvent(rating.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return rating;
    }

    @Override
    protected BaseResult getResult(Rating rating) {
        var result = RatingResultFactory.getGetRatingResult();

        if(rating != null) {
            result.setRating(ratingControl.getRatingTransfer(getUserVisit(), rating));
        }

        return result;
    }
    
}

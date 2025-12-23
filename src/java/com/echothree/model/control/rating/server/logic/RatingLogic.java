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

package com.echothree.model.control.rating.server.logic;

import com.echothree.model.control.rating.common.exception.UnknownRatingNameException;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.data.rating.server.entity.Rating;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class RatingLogic
        extends BaseLogic {

    protected RatingLogic() {
        super();
    }

    public static RatingLogic getInstance() {
        return CDI.current().select(RatingLogic.class).get();
    }
    
    public Rating getRatingByName(final ExecutionErrorAccumulator eea, final String ratingName) {
        var ratingControl = Session.getModelController(RatingControl.class);
        var rating = ratingControl.getRatingByName(ratingName);

        if(rating == null) {
            handleExecutionError(UnknownRatingNameException.class, eea, ExecutionErrors.UnknownRatingName.name(), ratingName);
        }

        return rating;
    }
    
}

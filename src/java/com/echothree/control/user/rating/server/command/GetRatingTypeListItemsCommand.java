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

import com.echothree.control.user.rating.common.form.GetRatingTypeListItemsForm;
import com.echothree.control.user.rating.common.result.RatingResultFactory;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.control.rating.server.logic.RatingTypeLogic;
import com.echothree.model.data.rating.server.entity.RatingType;
import com.echothree.model.data.rating.server.entity.RatingTypeListItem;
import com.echothree.model.data.rating.server.factory.RatingTypeListItemFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetRatingTypeListItemsCommand
        extends BasePaginatedMultipleEntitiesCommand<RatingTypeListItem, GetRatingTypeListItemsForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("RatingTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    @Inject
    RatingControl ratingControl;

    @Inject
    RatingTypeLogic ratingTypeLogic;

    /** Creates a new instance of GetRatingTypeListItemsCommand */
    public GetRatingTypeListItemsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    RatingType ratingType;

    @Override
    protected void handleForm() {
        var componentVendorName = form.getComponentVendorName();
        var entityTypeName = form.getEntityTypeName();
        var ratingTypeName = form.getRatingTypeName();

        ratingType = ratingTypeLogic.getRatingTypeByName(this, componentVendorName, entityTypeName, ratingTypeName);
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : ratingControl.countRatingTypeListItemsByRatingType(ratingType);
    }

    @Override
    protected Collection<RatingTypeListItem> getEntities() {
        return hasExecutionErrors() ? null : ratingControl.getRatingTypeListItems(ratingType);
    }

    @Override
    protected BaseResult getResult(Collection<RatingTypeListItem> entities) {
        var result = RatingResultFactory.getGetRatingTypeListItemsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setRatingType(ratingControl.getRatingTypeTransfer(userVisit, ratingType));

            if(session.hasLimit(RatingTypeListItemFactory.class)) {
                result.setRatingTypeListItemCount(getTotalEntities());
            }

            result.setRatingTypeListItems(ratingControl.getRatingTypeListItemTransfers(userVisit, entities));
        }

        return result;
    }

}

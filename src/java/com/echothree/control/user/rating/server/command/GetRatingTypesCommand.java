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

import com.echothree.control.user.rating.common.form.GetRatingTypesForm;
import com.echothree.control.user.rating.common.result.RatingResultFactory;
import com.echothree.model.control.core.server.control.ComponentControl;
import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.rating.server.entity.RatingType;
import com.echothree.model.data.rating.server.factory.RatingTypeFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetRatingTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<RatingType, GetRatingTypesForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null)
        );
    }

    @Inject
    RatingControl ratingControl;

    @Inject
    EntityTypeLogic entityTypeLogic;

    /** Creates a new instance of GetRatingTypesCommand */
    public GetRatingTypesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    private EntityType entityType;

    @Override
    protected void handleForm() {
        entityType = entityTypeLogic.getEntityTypeByName(this, form.getComponentVendorName(), form.getEntityTypeName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : ratingControl.countRatingTypesByEntityType(entityType);
    }

    @Override
    protected Collection<RatingType> getEntities() {
        return hasExecutionErrors() ? null : ratingControl.getRatingTypes(entityType);
    }

    @Override
    protected BaseResult getResult(Collection<RatingType> entities) {
        var result = RatingResultFactory.getGetRatingTypesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(session.hasLimit(RatingTypeFactory.class)) {
                result.setRatingTypeCount(getTotalEntities());
            }

            result.setEntityType(entityTypeControl.getEntityTypeTransfer(userVisit, entityType));
            result.setRatingTypes(ratingControl.getRatingTypeTransfers(userVisit, entities));
        }

        return result;
    }

}

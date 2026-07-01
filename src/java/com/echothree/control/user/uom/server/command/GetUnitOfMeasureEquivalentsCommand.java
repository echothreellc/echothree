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

package com.echothree.control.user.uom.server.command;

import com.echothree.control.user.uom.common.form.GetUnitOfMeasureEquivalentsForm;
import com.echothree.control.user.uom.common.result.UomResultFactory;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureKindLogic;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureEquivalent;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.factory.UnitOfMeasureEquivalentFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetUnitOfMeasureEquivalentsCommand
        extends BasePaginatedMultipleEntitiesCommand<UnitOfMeasureEquivalent, GetUnitOfMeasureEquivalentsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    @Inject
    UomControl uomControl;

    @Inject
    UnitOfMeasureKindLogic unitOfMeasureKindLogic;

    /** Creates a new instance of GetUnitOfMeasureEquivalentsCommand */
    public GetUnitOfMeasureEquivalentsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    UnitOfMeasureKind unitOfMeasureKind;

    @Override
    protected void handleForm() {
        unitOfMeasureKind = unitOfMeasureKindLogic.getUnitOfMeasureKindByName(this, form.getUnitOfMeasureKindName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : uomControl.countUnitOfMeasureEquivalentsByUnitOfMeasureKind(unitOfMeasureKind);
    }

    @Override
    protected Collection<UnitOfMeasureEquivalent> getEntities() {
        return hasExecutionErrors() ? null : uomControl.getUnitOfMeasureEquivalentsByUnitOfMeasureKind(unitOfMeasureKind);
    }

    @Override
    protected BaseResult getResult(Collection<UnitOfMeasureEquivalent> entities) {
        var result = UomResultFactory.getGetUnitOfMeasureEquivalentsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setUnitOfMeasureKind(uomControl.getUnitOfMeasureKindTransfer(userVisit, unitOfMeasureKind));

            if(session.hasLimit(UnitOfMeasureEquivalentFactory.class)) {
                result.setUnitOfMeasureEquivalentCount(getTotalEntities());
            }

            result.setUnitOfMeasureEquivalents(uomControl.getUnitOfMeasureEquivalentTransfers(userVisit, entities));
        }

        return result;
    }
    
}

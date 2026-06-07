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

package com.echothree.control.user.batch.server.command;

import com.echothree.control.user.batch.common.form.GetBatchTypeEntityTypesForm;
import com.echothree.control.user.batch.common.result.BatchResultFactory;
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.control.batch.server.logic.BatchLogic;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.data.batch.server.entity.BatchType;
import com.echothree.model.data.batch.server.entity.BatchTypeEntityType;
import com.echothree.model.data.batch.server.factory.BatchTypeEntityTypeFactory;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetBatchTypeEntityTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<BatchTypeEntityType, GetBatchTypeEntityTypesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("BatchTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null)
        );
    }

    @Inject
    BatchControl batchControl;

    @Inject
    BatchLogic batchLogic;

    @Inject
    EntityTypeLogic entityTypeLogic;

    /** Creates a new instance of GetBatchTypeEntityTypesCommand */
    public GetBatchTypeEntityTypesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    BatchType batchType;
    EntityType entityType;

    @Override
    protected void handleForm() {
        var batchTypeName = form.getBatchTypeName();
        var componentVendorName = form.getComponentVendorName();
        var entityTypeName = form.getEntityTypeName();
        var parameterCount = (batchTypeName == null ? 0 : 1) + (componentVendorName == null && entityTypeName == null ? 0 : 1);

        if(parameterCount == 1) {
            if(batchTypeName != null) {
                batchType = batchLogic.getBatchTypeByName(this, batchTypeName);
            } else {
                entityType = entityTypeLogic.getEntityTypeByName(this, componentVendorName, entityTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null :
                batchType != null ? batchControl.countBatchTypeEntityTypesByBatchType(batchType) :
                batchControl.countBatchTypeEntityTypesByEntityType(entityType);
    }

    @Override
    protected Collection<BatchTypeEntityType> getEntities() {
        return hasExecutionErrors() ? null :
                batchType != null ? batchControl.getBatchTypeEntityTypesByBatchType(batchType) :
                batchControl.getBatchTypeEntityTypesByEntityType(entityType);
    }

    @Override
    protected BaseResult getResult(Collection<BatchTypeEntityType> entities) {
        var result = BatchResultFactory.getGetBatchTypeEntityTypesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(batchType != null) {
                result.setBatchType(batchControl.getBatchTypeTransfer(userVisit, batchType));
            }

            if(entityType != null) {
                result.setEntityType(entityTypeControl.getEntityTypeTransfer(userVisit, entityType));
            }

            if(session.hasLimit(BatchTypeEntityTypeFactory.class)) {
                result.setBatchTypeEntityTypeCount(getTotalEntities());
            }

            result.setBatchTypeEntityTypes(batchControl.getBatchTypeEntityTypeTransfers(userVisit, entities));
        }

        return result;
    }
    
}

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

import com.echothree.control.user.batch.common.form.GetBatchTypeEntityTypeForm;
import com.echothree.control.user.batch.common.result.BatchResultFactory;
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.control.batch.server.logic.BatchLogic;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetBatchTypeEntityTypeCommand
        extends BaseSimpleCommand<GetBatchTypeEntityTypeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("BatchTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null)
        );
    }
    
    @Inject
    BatchControl batchControl;

    @Inject
    BatchLogic batchLogic;

    @Inject
    EntityTypeLogic entityTypeLogic;

    /** Creates a new instance of GetBatchTypeEntityTypeCommand */
    public GetBatchTypeEntityTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var result = BatchResultFactory.getGetBatchTypeEntityTypeResult();
        var batchTypeName = form.getBatchTypeName();
        var batchType = batchLogic.getBatchTypeByName(this, batchTypeName);
        
        if(!hasExecutionErrors()) {
            var componentVendorName = form.getComponentVendorName();
            var entityTypeName = form.getEntityTypeName();
            var entityType = entityTypeLogic.getEntityTypeByName(this, componentVendorName, entityTypeName);

            if(!hasExecutionErrors()) {
                var batchTypeEntityType = batchControl.getBatchTypeEntityType(batchType, entityType);

                if(batchTypeEntityType != null) {
                    result.setBatchTypeEntityType(batchControl.getBatchTypeEntityTypeTransfer(getUserVisit(), batchTypeEntityType));
                } else {
                    addExecutionError(ExecutionErrors.UnknownBatchTypeEntityType.name(), batchTypeName, componentVendorName, entityTypeName);
                }
            }
        }
        
        return result;
    }
    
}

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

package com.echothree.control.user.batch.server.command;

import com.echothree.control.user.batch.common.form.GetBatchTypeEntityTypesForm;
import com.echothree.control.user.batch.common.result.BatchResultFactory;
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetBatchTypeEntityTypesCommand
        extends BaseSimpleCommand<GetBatchTypeEntityTypesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("BatchTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetBatchTypeEntityTypesCommand */
    public GetBatchTypeEntityTypesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var result = BatchResultFactory.getGetBatchTypeEntityTypesResult();
        var batchTypeName = form.getBatchTypeName();
        var componentVendorName = form.getComponentVendorName();
        var entityTypeName = form.getEntityTypeName();
        var parameterCount = (batchTypeName == null ? 0 : 1) + (componentVendorName == null && entityTypeName == null ? 0 : 1);

        if(parameterCount == 1) {
            var batchControl = Session.getModelController(BatchControl.class);

            if(batchTypeName != null) {
                var batchType = batchControl.getBatchTypeByName(batchTypeName);

                if(batchType != null) {
                    result.setBatchType(batchControl.getBatchTypeTransfer(getUserVisit(), batchType));
                    result.setBatchTypeEntityTypes(batchControl.getBatchTypeEntityTypeTransfersByBatchType(getUserVisit(), batchType));
                } else {
                    addExecutionError(ExecutionErrors.UnknownBatchTypeName.name(), batchTypeName);
                }
            } else {
                var componentVendor = componentControl.getComponentVendorByName(componentVendorName);

                if(componentVendor != null) {
                    var entityType = entityTypeControl.getEntityTypeByName(componentVendor, entityTypeName);

                    if(entityType != null) {
                        result.setEntityType(entityTypeControl.getEntityTypeTransfer(getUserVisit(), entityType));
                        result.setBatchTypeEntityTypes(batchControl.getBatchTypeEntityTypeTransfersByEntityType(getUserVisit(), entityType));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), componentVendorName, entityTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}

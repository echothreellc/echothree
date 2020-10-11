// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.batch.common.form.CreateBatchTypeEntityTypeForm;
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.data.batch.server.entity.BatchType;
import com.echothree.model.data.batch.server.entity.BatchTypeEntityType;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateBatchTypeEntityTypeCommand
        extends BaseSimpleCommand<CreateBatchTypeEntityTypeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("BatchTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateBatchTypeEntityTypeCommand */
    public CreateBatchTypeEntityTypeCommand(UserVisitPK userVisitPK, CreateBatchTypeEntityTypeForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var batchControl = (BatchControl)Session.getModelController(BatchControl.class);
        String batchTypeName = form.getBatchTypeName();
        BatchType batchType = batchControl.getBatchTypeByName(batchTypeName);
        
        if(batchType != null) {
            var coreControl = getCoreControl();
            String componentVendorName = form.getComponentVendorName();
            ComponentVendor componentVendor = coreControl.getComponentVendorByName(componentVendorName);
            
            if(componentVendor != null) {
                String entityTypeName = form.getEntityTypeName();
                EntityType entityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);
                
                if(entityType != null) {
                    BatchTypeEntityType batchTypeEntityType = batchControl.getBatchTypeEntityType(batchType, entityType);
                    
                    if(batchTypeEntityType == null) {
                        batchControl.createBatchTypeEntityType(batchType, entityType, getPartyPK());
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateBatchTypeEntityType.name(), batchTypeName, componentVendorName, entityTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), componentVendorName, entityTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownBatchTypeName.name(), batchTypeName);
        }
        
        return null;
    }
    
}

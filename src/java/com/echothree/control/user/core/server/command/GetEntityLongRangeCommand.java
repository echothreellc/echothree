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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.GetEntityLongRangeForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.data.core.server.entity.EntityLongRange;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetEntityLongRangeCommand
        extends BaseSingleEntityCommand<EntityLongRange, GetEntityLongRangeForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityLongRangeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    /** Creates a new instance of GetEntityLongRangeCommand */
    public GetEntityLongRangeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }


    @Override
    protected EntityLongRange getEntity() {
        var coreControl = getCoreControl();
        EntityLongRange entityLongRange = null;
        var componentVendorName = form.getComponentVendorName();
        var componentVendor = getComponentControl().getComponentVendorByName(componentVendorName);

        if(componentVendor != null) {
            var entityTypeName = form.getEntityTypeName();
            var entityType = getEntityTypeControl().getEntityTypeByName(componentVendor, entityTypeName);

            if(entityType != null) {
                var entityAttributeName = form.getEntityAttributeName();
                var entityAttribute = coreControl.getEntityAttributeByName(entityType, entityAttributeName);

                if(entityAttribute != null) {
                    var entityAttributeType = entityAttribute.getLastDetail().getEntityAttributeType();
                    var entityAttributeTypeName = entityAttributeType.getEntityAttributeTypeName();

                    if(entityAttributeTypeName.equals(EntityAttributeTypes.LONG.name())) {
                        var entityLongRangeName = form.getEntityLongRangeName();

                        entityLongRange = coreControl.getEntityLongRangeByName(entityAttribute, entityLongRangeName);

                        if(entityLongRange == null) {
                            addExecutionError(ExecutionErrors.UnknownEntityLongRangeName.name(), componentVendorName, entityTypeName, entityAttributeName, entityLongRangeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.InvalidEntityAttributeType.name(), componentVendorName, entityTypeName, entityAttributeName, entityAttributeTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownEntityAttributeName.name(), componentVendorName, entityTypeName, entityAttributeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), componentVendorName, entityTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
        }

        return entityLongRange;
    }

    @Override
    protected BaseResult getResult(EntityLongRange entityLongRange) {
        var result = CoreResultFactory.getGetEntityLongRangeResult();

        if(entityLongRange != null) {
            var coreControl = getCoreControl();

            result.setEntityLongRange(coreControl.getEntityLongRangeTransfer(getUserVisit(), entityLongRange, null));
        }

        return result;
    }

}

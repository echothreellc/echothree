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

import com.echothree.control.user.core.common.form.GetEntityAttributeTypeForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.data.core.server.entity.EntityAttributeType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetEntityAttributeTypeCommand
        extends BaseSingleEntityCommand<EntityAttributeType, GetEntityAttributeTypeForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityAttributeTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetEntityAttributeTypeCommand */
    public GetEntityAttributeTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    
    @Override
    protected EntityAttributeType getEntity() {
        var entityAttributeTypeName = form.getEntityAttributeTypeName();
        
        return EntityAttributeLogic.getInstance().getEntityAttributeTypeByName(this, entityAttributeTypeName);
    }
    
    @Override
    protected BaseResult getResult(EntityAttributeType entityAttributeType) {
        var result = CoreResultFactory.getGetEntityAttributeTypeResult();

        if(entityAttributeType != null) {
            sendEvent(entityAttributeType.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());

            result.setEntityAttributeType(coreControl.getEntityAttributeTypeTransfer(getUserVisit(), entityAttributeType));
        }

        return result;
    }
    
}

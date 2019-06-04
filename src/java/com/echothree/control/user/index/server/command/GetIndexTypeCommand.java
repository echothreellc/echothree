// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.control.user.index.server.command;

import com.echothree.control.user.index.common.form.GetIndexTypeForm;
import com.echothree.control.user.index.common.result.GetIndexTypeResult;
import com.echothree.control.user.index.common.result.IndexResultFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.index.server.IndexControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.index.server.entity.IndexType;
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

public class GetIndexTypeCommand
        extends BaseSimpleCommand<GetIndexTypeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IndexTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Key", FieldType.KEY, false, null, null),
                new FieldDefinition("Guid", FieldType.GUID, false, null, null),
                new FieldDefinition("Ulid", FieldType.ULID, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetIndexTypeCommand */
    public GetIndexTypeCommand(UserVisitPK userVisitPK, GetIndexTypeForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        GetIndexTypeResult result = IndexResultFactory.getGetIndexTypeResult();
        String indexTypeName = form.getIndexTypeName();
        int parameterCount = (indexTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        if(parameterCount == 1) {
            var indexControl = (IndexControl)Session.getModelController(IndexControl.class);
            IndexType indexType = null;

            if(indexTypeName == null) {
                EntityInstance entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form, ComponentVendors.ECHOTHREE.name(),
                        EntityTypes.IndexType.name());
                
                if(!hasExecutionErrors()) {
                    indexType = indexControl.getIndexTypeByEntityInstance(entityInstance);
                }
            } else {
                indexType = indexControl.getIndexTypeByName(indexTypeName);

                if(indexType == null) {
                    addExecutionError(ExecutionErrors.UnknownIndexTypeName.name(), indexTypeName);
                }
            }

            if(!hasExecutionErrors()) {
                result.setIndexType(indexControl.getIndexTypeTransfer(getUserVisit(), indexType));
                sendEventUsingNames(indexType.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}

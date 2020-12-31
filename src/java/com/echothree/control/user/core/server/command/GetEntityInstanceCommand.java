// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.control.user.core.common.form.GetEntityInstanceForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetEntityInstanceCommand
        extends BaseSingleEntityCommand<EntityInstance, GetEntityInstanceForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Key", FieldType.KEY, false, null, null),
                new FieldDefinition("Guid", FieldType.GUID, false, null, null),
                new FieldDefinition("Ulid", FieldType.ULID, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetEntityInstanceCommand */
    public GetEntityInstanceCommand(UserVisitPK userVisitPK, GetEntityInstanceForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected EntityInstance getEntity() {
        var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form);

        return entityInstance;
    }

    @Override
    protected BaseResult getTransfer(EntityInstance entityInstance) {
        var result = CoreResultFactory.getGetEntityInstanceResult();

        if(entityInstance != null) {
            result.setEntityInstance(getCoreControl().getEntityInstanceTransfer(getUserVisit(), entityInstance,
                    false, false, false, false, false));
        }

        return result;
    }

}

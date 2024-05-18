// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.control.user.core.common.form.DeleteEntityAliasForm;
import com.echothree.control.user.core.common.form.GetEntityAliasForm;
import com.echothree.control.user.core.common.form.GetEntityAliasTypeForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.logic.EntityAliasTypeLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.data.core.server.entity.EntityAlias;
import com.echothree.model.data.core.server.entity.EntityAliasType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import java.util.List;

public class GetEntityAliasCommand
        extends BaseSingleEntityCommand<EntityAlias, GetEntityAliasForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), null)
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Key", FieldType.KEY, false, null, null),
                new FieldDefinition("Guid", FieldType.GUID, false, null, null),
                new FieldDefinition("Ulid", FieldType.ULID, false, null, null),
                new FieldDefinition("EntityAliasTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityAliasTypeUlid", FieldType.ULID, false, null, null)
        );
    }

    /** Creates a new instance of DeleteEntityAliasCommand */
    public GetEntityAliasCommand(UserVisitPK userVisitPK, GetEntityAliasForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected EntityAlias getEntity() {
        var parameterCount = EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);
        EntityAlias entityAlias = null;

        if(parameterCount == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form);

            if(!hasExecutionErrors()) {
                var entityAliasTypeName = form.getEntityAliasTypeName();
                var entityAliasTypeUlid = form.getEntityAliasTypeUlid();

                parameterCount = (entityAliasTypeName == null ? 0 : 1) + (entityAliasTypeUlid == null ? 0 : 1);

                if(parameterCount == 1) {
                    var entityAliasType = entityAliasTypeName == null ?
                            EntityAliasTypeLogic.getInstance().getEntityAliasTypeByUlid(this, entityAliasTypeUlid) :
                            EntityAliasTypeLogic.getInstance().getEntityAliasTypeByName(this, entityInstance.getEntityType(), entityAliasTypeName);

                    if(!hasExecutionErrors()) {
                        if(entityInstance.getEntityType().equals(entityAliasType.getLastDetail().getEntityType())) {
                            var coreControl = getCoreControl();

                            entityAlias = coreControl.getEntityAliasForUpdate(entityInstance, entityAliasType);

                            if(entityAlias == null) {
                                addExecutionError(ExecutionErrors.UnknownEntityAlias.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.MismatchedEntityType.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return entityAlias;
    }

    @Override
    protected BaseResult getTransfer(EntityAlias entityAlias) {
        var coreControl = getCoreControl();
        var result = CoreResultFactory.getGetEntityAliasResult();

        if(entityAlias != null) {
            result.setEntityAlias(coreControl.getEntityAliasTransfer(getUserVisit(), entityAlias));
        }

        return result;
    }
    
}

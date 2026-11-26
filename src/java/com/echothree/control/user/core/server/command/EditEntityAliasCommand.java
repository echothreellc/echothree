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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.edit.EntityAliasEdit;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityAliasResult;
import com.echothree.control.user.core.common.spec.EntityAliasSpec;
import com.echothree.model.control.core.server.control.EntityAliasControl;
import com.echothree.model.control.core.server.logic.EntityAliasTypeLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityAlias;
import com.echothree.model.data.core.server.entity.EntityAliasType;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.PersistenceUtils;
import com.google.re2j.Pattern;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditEntityAliasCommand
        extends BaseEditCommand<EntityAliasSpec, EntityAliasEdit, EditEntityAliasResult, EntityAlias, EntityInstance> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityAlias.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null),
                new FieldDefinition("EntityAliasTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityAliasTypeUuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Alias", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    EntityAliasControl entityAliasControl;

    @Inject
    EntityAliasTypeLogic entityAliasTypeLogic;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    private EntityInstance entityInstance;
    private EntityAliasType entityAliasType;

    /** Creates a new instance of EditEntityAliasCommand */
    public EditEntityAliasCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditEntityAliasResult getResult() {
        return CoreResultFactory.getEditEntityAliasResult();
    }

    @Override
    public EntityAliasEdit getEdit() {
        return CoreEditFactory.getEntityAliasEdit();
    }

    @Override
    public EntityAlias getEntity(EditEntityAliasResult result) {
        EntityAlias entity = null;

        entityInstance = entityInstanceLogic.getEntityInstance(this, spec);

        if(!hasExecutionErrors()) {
            entityAliasType = entityAliasTypeLogic.getEntityAliasType(this, entityInstance, spec, spec);
        }

        if(!hasExecutionErrors()) {
            entity = entityAliasTypeLogic.getEntityAlias(this, entityInstance, entityAliasType, editModeToEntityPermission(editMode));
        }

        return entity;
    }

    @Override
    public EntityInstance getLockEntity(EntityAlias entityAlias) {
        return entityInstance;
    }

    @Override
    protected BasePK getLockTargetPK(BaseEntity lockTarget) {
        return PersistenceUtils.getInstance().getBasePKFromEntityInstance((EntityInstance)lockTarget);
    }

    @Override
    public void fillInResult(EditEntityAliasResult result, EntityAlias entityAlias) {
        result.setEntityAlias(entityAliasControl.getEntityAliasTransfer(getUserVisit(), entityAlias));
    }

    @Override
    public void doLock(EntityAliasEdit edit, EntityAlias entityAlias) {
        edit.setAlias(entityAlias.getAlias());
    }

    @Override
    protected void canUpdate(EntityAlias entityAlias) {
        var validationPattern = entityAliasType.getLastDetail().getValidationPattern();
        var alias = edit.getAlias();

        if(validationPattern != null) {
            var pattern = Pattern.compile(validationPattern);
            var matcher = pattern.matcher(alias);

            if(!matcher.matches()) {
                addExecutionError(ExecutionErrors.InvalidAlias.name(), alias);
            }
        }

        if(!hasExecutionErrors()) {
            var duplicateEntityAlias = entityAliasControl.getEntityAliasByEntityAliasTypeAndAlias(entityAliasType, alias);

            if(duplicateEntityAlias != null && !entityAlias.equals(duplicateEntityAlias)) {
                addExecutionError(ExecutionErrors.DuplicateEntityAlias.name(),
                        entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance),
                        entityAliasType.getLastDetail().getEntityAliasTypeName(), alias);
            }
        }
    }

    @Override
    public void doUpdate(EntityAlias entityAlias) {
        var entityAliasValue = entityAliasControl.getEntityAliasValueForUpdate(entityAlias);

        entityAliasValue.setAlias(edit.getAlias());

        entityAliasControl.updateEntityAliasFromValue(entityAliasValue, getPartyPK());
    }

}

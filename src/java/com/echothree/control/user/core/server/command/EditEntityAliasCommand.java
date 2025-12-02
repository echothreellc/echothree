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

import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.edit.EntityAliasEdit;
import com.echothree.control.user.core.common.form.EditEntityAliasForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.spec.EntityAliasSpec;
import com.echothree.model.control.core.server.control.EntityAliasControl;
import com.echothree.model.control.core.server.logic.EntityAliasTypeLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityAlias;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.PersistenceUtils;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import java.util.regex.Pattern;
import javax.enterprise.context.Dependent;

@Dependent
public class EditEntityAliasCommand
        extends BaseEditCommand<EntityAliasSpec, EntityAliasEdit> {

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

    /** Creates a new instance of EditEntityAliasCommand */
    public EditEntityAliasCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var result = CoreResultFactory.getEditEntityAliasResult();
        var parameterCount = EntityInstanceLogic.getInstance().countPossibleEntitySpecs(spec);

        if(parameterCount == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, spec);

            if(!hasExecutionErrors()) {
                var entityAliasTypeName = spec.getEntityAliasTypeName();
                var entityAliasTypeUuid = spec.getEntityAliasTypeUuid();

                parameterCount = (entityAliasTypeName == null ? 0 : 1) + (entityAliasTypeUuid == null ? 0 : 1);

                if(parameterCount == 1) {
                    var entityAliasType = entityAliasTypeName == null ?
                            EntityAliasTypeLogic.getInstance().getEntityAliasTypeByUuid(this, entityAliasTypeUuid) :
                            EntityAliasTypeLogic.getInstance().getEntityAliasTypeByName(this, entityInstance.getEntityType(), entityAliasTypeName);

                    if(!hasExecutionErrors()) {
                        if(entityInstance.getEntityType().equals(entityAliasType.getLastDetail().getEntityType())) {
                            var entityAliasControl = Session.getModelController(EntityAliasControl.class);
                            EntityAlias entityAlias = null;
                            var basePK = PersistenceUtils.getInstance().getBasePKFromEntityInstance(entityInstance);

                            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                                entityAlias = entityAliasControl.getEntityAlias(entityInstance, entityAliasType);

                                if(entityAlias != null) {
                                    if(editMode.equals(EditMode.LOCK)) {
                                        result.setEntityAlias(entityAliasControl.getEntityAliasTransfer(getUserVisit(), entityAlias));

                                        if(lockEntity(basePK)) {
                                            var edit = CoreEditFactory.getEntityAliasEdit();

                                            result.setEdit(edit);
                                            edit.setAlias(entityAlias.getAlias());
                                        } else {
                                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                                        }
                                    } else { // EditMode.ABANDON
                                        unlockEntity(basePK);
                                        basePK = null;
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownEntityAlias.name(),
                                            EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance), entityAliasTypeName);
                                }
                            } else if(editMode.equals(EditMode.UPDATE)) {
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
                                    entityAlias = entityAliasControl.getEntityAliasForUpdate(entityInstance, entityAliasType);

                                    if(entityAlias != null) {

                                        var duplicateEntityAlias = entityAliasControl.getEntityAliasByEntityAliasTypeAndAlias(entityAliasType, alias);

                                        if(duplicateEntityAlias != null && !entityAlias.equals(duplicateEntityAlias)) {
                                            addExecutionError(ExecutionErrors.DuplicateEntityAlias.name(),
                                                    EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance),
                                                    entityAliasType.getLastDetail().getEntityAliasTypeName(), alias);
                                        } else {
                                            if(lockEntityForUpdate(basePK)) {
                                                try {
                                                    var entityAliasValue = entityAliasControl.getEntityAliasValueForUpdate(entityAlias);

                                                    entityAliasValue.setAlias(alias);

                                                    entityAliasControl.updateEntityAliasFromValue(entityAliasValue, getPartyPK());
                                                } finally {
                                                    unlockEntity(basePK);
                                                    basePK = null;
                                                }
                                            } else {
                                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                                            }
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownEntityAlias.name(),
                                                EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance), entityAliasTypeName);
                                    }
                                }
                            }

                            if(basePK != null) {
                                result.setEntityLock(getEntityLockTransfer(basePK));
                            }

                            if(entityAlias != null) {
                                result.setEntityAlias(entityAliasControl.getEntityAliasTransfer(getUserVisit(), entityAlias));
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

        return result;
    }
    
}

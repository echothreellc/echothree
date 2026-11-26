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
import com.echothree.control.user.core.common.edit.EntityAppearanceEdit;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityAppearanceResult;
import com.echothree.control.user.core.common.spec.EntityRefSpec;
import com.echothree.model.control.core.server.control.AppearanceControl;
import com.echothree.model.control.core.server.logic.AppearanceLogic;
import com.echothree.model.control.core.server.logic.EntityDataLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Appearance;
import com.echothree.model.data.core.server.entity.EntityAppearance;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.PersistenceUtils;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditEntityAppearanceCommand
        extends BaseAbstractEditCommand<EntityRefSpec, EntityAppearanceEdit, EditEntityAppearanceResult, EntityAppearance, EntityInstance> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityAppearance.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("AppearanceName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    AppearanceControl appearanceControl;

    @Inject
    AppearanceLogic appearanceLogic;

    @Inject
    EntityDataLogic entityDataLogic;

    private EntityInstance entityInstance;
    private Appearance appearance;

    /** Creates a new instance of EditEntityAppearanceCommand */
    public EditEntityAppearanceCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditEntityAppearanceResult getResult() {
        return CoreResultFactory.getEditEntityAppearanceResult();
    }

    @Override
    public EntityAppearanceEdit getEdit() {
        return CoreEditFactory.getEntityAppearanceEdit();
    }

    @Override
    public EntityAppearance getEntity(EditEntityAppearanceResult result) {
        EntityAppearance entity = null;

        entityInstance = entityDataLogic.getEntityInstanceByEntityRef(this, spec.getEntityRef());

        var ownerResolved = !hasExecutionErrors();

        if(!hasExecutionErrors() && editMode == EditMode.UPDATE) {
            appearance = appearanceLogic.getAppearanceByName(this, edit.getAppearanceName());
        }

        if(!hasExecutionErrors()) {
            entity = entityDataLogic.getEntityAppearance(this, entityInstance, editModeToEntityPermission(editMode));
        }

        if(entity != null && editMode == EditMode.LOCK) {
            fillInResult(result, entity);
        }

        // Legacy results include owner lock information even when the value lookup or replacement validation fails.
        if(hasExecutionErrors() && ownerResolved) {
            result.setEntityLock(getEntityLockTransfer(PersistenceUtils.getInstance().getBasePKFromEntityInstance(entityInstance)));
        }

        return entity;
    }

    @Override
    public EntityInstance getLockEntity(EntityAppearance entityAppearance) {
        return entityInstance;
    }

    @Override
    protected BasePK getLockTargetPK(BaseEntity lockTarget) {
        return PersistenceUtils.getInstance().getBasePKFromEntityInstance((EntityInstance)lockTarget);
    }

    @Override
    public void fillInResult(EditEntityAppearanceResult result, EntityAppearance entityAppearance) {
        result.setEntityAppearance(appearanceControl.getEntityAppearanceTransfer(getUserVisit(), entityAppearance));
    }

    @Override
    public void doLock(EntityAppearanceEdit edit, EntityAppearance entityAppearance) {
        edit.setAppearanceName(entityAppearance.getAppearance().getLastDetail().getAppearanceName());
    }

    @Override
    public void doUpdate(EntityAppearance entityAppearance) {
        var entityAppearanceValue = appearanceControl.getEntityAppearanceValue(entityAppearance);

        entityAppearanceValue.setAppearancePK(appearance.getPrimaryKey());

        appearanceControl.updateEntityAppearanceFromValue(entityAppearanceValue, getPartyPK());
    }

    @Override
    protected boolean includeResultAfterSuccessfulEdit() {
        return true;
    }

    @Override
    protected void saveResultAfterEditValidatorErrors() {
        // Preserve the existing absence of a result when edit field validation fails.
    }

}

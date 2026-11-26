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
import com.echothree.control.user.core.common.edit.EntityBooleanAttributeEdit;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityBooleanAttributeResult;
import com.echothree.control.user.core.common.spec.EntityBooleanAttributeSpec;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityDataLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityBooleanAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.PersistenceUtils;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditEntityBooleanAttributeCommand
        extends BaseAbstractEditCommand<EntityBooleanAttributeSpec, EntityBooleanAttributeEdit, EditEntityBooleanAttributeResult, EntityBooleanAttribute, EntityInstance> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), null)
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityAttributeUuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("BooleanAttribute", FieldType.BOOLEAN, true, null, null)
        );
    }

    @Inject
    EntityAttributeLogic entityAttributeLogic;

    @Inject
    EntityDataLogic entityDataLogic;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    private EntityInstance entityInstance;
    private EntityAttribute entityAttribute;

    /** Creates a new instance of EditEntityBooleanAttributeCommand */
    public EditEntityBooleanAttributeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditEntityBooleanAttributeResult getResult() {
        return CoreResultFactory.getEditEntityBooleanAttributeResult();
    }

    @Override
    public EntityBooleanAttributeEdit getEdit() {
        return CoreEditFactory.getEntityBooleanAttributeEdit();
    }

    @Override
    public EntityBooleanAttribute getEntity(EditEntityBooleanAttributeResult result) {
        EntityBooleanAttribute entity = null;

        entityInstance = entityInstanceLogic.getEntityInstance(this, spec);

        if(!hasExecutionErrors()) {
            entityAttribute = entityAttributeLogic.getEntityAttribute(this, entityInstance, spec, spec);
        }

        var ownerResolved = !hasExecutionErrors();

        if(!hasExecutionErrors()) {
            entity = entityDataLogic.getEntityBooleanAttribute(this, entityAttribute, entityInstance, editModeToEntityPermission(editMode));
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
    public EntityInstance getLockEntity(EntityBooleanAttribute entityBooleanAttribute) {
        return entityInstance;
    }

    @Override
    protected BasePK getLockTargetPK(BaseEntity lockTarget) {
        return PersistenceUtils.getInstance().getBasePKFromEntityInstance((EntityInstance)lockTarget);
    }

    @Override
    public void fillInResult(EditEntityBooleanAttributeResult result, EntityBooleanAttribute entityBooleanAttribute) {
        result.setEntityBooleanAttribute(coreControl.getEntityBooleanAttributeTransfer(getUserVisit(), entityBooleanAttribute, entityInstance));
    }

    @Override
    public void doLock(EntityBooleanAttributeEdit edit, EntityBooleanAttribute entityBooleanAttribute) {
        edit.setBooleanAttribute(entityBooleanAttribute.getBooleanAttribute().toString());
    }

    @Override
    public void doUpdate(EntityBooleanAttribute entityBooleanAttribute) {
        var entityBooleanAttributeValue = coreControl.getEntityBooleanAttributeValueForUpdate(entityBooleanAttribute);

        entityBooleanAttributeValue.setBooleanAttribute(Boolean.valueOf(edit.getBooleanAttribute()));

        coreControl.updateEntityBooleanAttributeFromValue(entityBooleanAttributeValue, getPartyPK());
    }

    @Override
    protected void saveResultAfterEditValidatorErrors() {
        // Preserve the existing absence of a result when edit field validation fails.
    }

}

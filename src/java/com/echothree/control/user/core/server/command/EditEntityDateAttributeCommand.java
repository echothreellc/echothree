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
import com.echothree.control.user.core.common.edit.EntityDateAttributeEdit;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityDateAttributeResult;
import com.echothree.control.user.core.common.spec.EntityDateAttributeSpec;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityDataLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityDateAttribute;
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
import com.echothree.util.server.string.DateUtils;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditEntityDateAttributeCommand
        extends BaseAbstractEditCommand<EntityDateAttributeSpec, EntityDateAttributeEdit, EditEntityDateAttributeResult, EntityDateAttribute, EntityInstance> {

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
                new FieldDefinition("DateAttribute", FieldType.DATE, true, null, null)
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

    /** Creates a new instance of EditEntityDateAttributeCommand */
    public EditEntityDateAttributeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditEntityDateAttributeResult getResult() {
        return CoreResultFactory.getEditEntityDateAttributeResult();
    }

    @Override
    public EntityDateAttributeEdit getEdit() {
        return CoreEditFactory.getEntityDateAttributeEdit();
    }

    @Override
    public EntityDateAttribute getEntity(EditEntityDateAttributeResult result) {
        EntityDateAttribute entity = null;

        entityInstance = entityInstanceLogic.getEntityInstance(this, spec);

        if(!hasExecutionErrors()) {
            entityAttribute = entityAttributeLogic.getEntityAttribute(this, entityInstance, spec, spec);
        }

        if(!hasExecutionErrors()) {
            entity = entityDataLogic.getEntityDateAttribute(this, entityAttribute, entityInstance, editModeToEntityPermission(editMode));
        }

        if(entity != null && editMode == EditMode.LOCK) {
            fillInResult(result, entity);
        }

        return entity;
    }

    @Override
    public EntityInstance getLockEntity(EntityDateAttribute entityDateAttribute) {
        return entityInstance;
    }

    @Override
    protected BasePK getLockTargetPK(BaseEntity lockTarget) {
        return PersistenceUtils.getInstance().getBasePKFromEntityInstance((EntityInstance)lockTarget);
    }

    @Override
    public void fillInResult(EditEntityDateAttributeResult result, EntityDateAttribute entityDateAttribute) {
        result.setEntityDateAttribute(coreControl.getEntityDateAttributeTransfer(getUserVisit(), entityDateAttribute, entityInstance));
    }

    @Override
    public void doLock(EntityDateAttributeEdit edit, EntityDateAttribute entityDateAttribute) {
        edit.setDateAttribute(DateUtils.getInstance().formatDate(getUserVisit(), entityDateAttribute.getDateAttribute()));
    }

    @Override
    public void doUpdate(EntityDateAttribute entityDateAttribute) {
        var entityDateAttributeValue = coreControl.getEntityDateAttributeValueForUpdate(entityDateAttribute);

        entityDateAttributeValue.setDateAttribute(Integer.valueOf(edit.getDateAttribute()));

        coreControl.updateEntityDateAttributeFromValue(entityDateAttributeValue, getPartyPK());
    }

}

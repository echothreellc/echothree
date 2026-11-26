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
import com.echothree.control.user.core.common.edit.EntityTimeAttributeEdit;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityTimeAttributeResult;
import com.echothree.control.user.core.common.spec.EntityTimeAttributeSpec;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.core.server.logic.EntityTimeAttributeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityTimeAttribute;
import com.echothree.util.common.message.ExecutionErrors;
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
public class EditEntityTimeAttributeCommand
        extends BaseAbstractEditCommand<EntityTimeAttributeSpec, EntityTimeAttributeEdit, EditEntityTimeAttributeResult, EntityTimeAttribute, EntityInstance> {

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
                new FieldDefinition("TimeAttribute", FieldType.DATE_TIME, true, null, null)
        );
    }

    @Inject
    EntityAttributeLogic entityAttributeLogic;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    EntityTimeAttributeLogic entityTimeAttributeLogic;

    private EntityInstance entityInstance;
    private EntityAttribute entityAttribute;

    /** Creates a new instance of EditEntityTimeAttributeCommand */
    public EditEntityTimeAttributeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditEntityTimeAttributeResult getResult() {
        return CoreResultFactory.getEditEntityTimeAttributeResult();
    }

    @Override
    public EntityTimeAttributeEdit getEdit() {
        return CoreEditFactory.getEntityTimeAttributeEdit();
    }

    @Override
    public EntityTimeAttribute getEntity(EditEntityTimeAttributeResult result) {
        EntityTimeAttribute entity = null;

        entityInstance = entityInstanceLogic.getEntityInstance(this, spec);

        if(!hasExecutionErrors()) {
            entityAttribute = entityAttributeLogic.getEntityAttribute(this, entityInstance, spec, spec);
        }

        if(!hasExecutionErrors()) {
            entity = entityTimeAttributeLogic.getEntityTimeAttribute(this, entityAttribute, entityInstance, editModeToEntityPermission(editMode));
        }

        return entity;
    }

    @Override
    public EntityInstance getLockEntity(EntityTimeAttribute entityTimeAttribute) {
        return entityInstance;
    }

    @Override
    protected BasePK getLockTargetPK(BaseEntity lockTarget) {
        return PersistenceUtils.getInstance().getBasePKFromEntityInstance((EntityInstance)lockTarget);
    }

    @Override
    public void fillInResult(EditEntityTimeAttributeResult result, EntityTimeAttribute entityTimeAttribute) {
        result.setEntityTimeAttribute(coreControl.getEntityTimeAttributeTransfer(getUserVisit(), entityTimeAttribute, entityInstance));
    }

    @Override
    public void doLock(EntityTimeAttributeEdit edit, EntityTimeAttribute entityTimeAttribute) {
        edit.setTimeAttribute(DateUtils.getInstance().formatTypicalDateTime(getUserVisit(), entityTimeAttribute.getTimeAttribute()));
    }

    @Override
    public void doUpdate(EntityTimeAttribute entityTimeAttribute) {
        var entityTimeAttributeValue = coreControl.getEntityTimeAttributeValueForUpdate(entityTimeAttribute);

        entityTimeAttributeValue.setTimeAttribute(Long.valueOf(edit.getTimeAttribute()));

        coreControl.updateEntityTimeAttributeFromValue(entityTimeAttributeValue, getPartyPK());
    }

}

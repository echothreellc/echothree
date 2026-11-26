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
import com.echothree.control.user.core.common.edit.EntityIntegerAttributeEdit;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityIntegerAttributeResult;
import com.echothree.control.user.core.common.spec.EntityIntegerAttributeSpec;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.core.server.logic.EntityIntegerAttributeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityIntegerAttribute;
import com.echothree.util.common.message.ExecutionErrors;
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
public class EditEntityIntegerAttributeCommand
        extends BaseAbstractEditCommand<EntityIntegerAttributeSpec, EntityIntegerAttributeEdit, EditEntityIntegerAttributeResult, EntityIntegerAttribute, EntityInstance> {

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
                new FieldDefinition("IntegerAttribute", FieldType.SIGNED_INTEGER, true, null, null)
        );
    }

    @Inject
    EntityAttributeLogic entityAttributeLogic;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    EntityIntegerAttributeLogic entityIntegerAttributeLogic;

    private EntityInstance entityInstance;
    private EntityAttribute entityAttribute;
    private Integer integerAttribute;

    /** Creates a new instance of EditEntityIntegerAttributeCommand */
    public EditEntityIntegerAttributeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditEntityIntegerAttributeResult getResult() {
        return CoreResultFactory.getEditEntityIntegerAttributeResult();
    }

    @Override
    public EntityIntegerAttributeEdit getEdit() {
        return CoreEditFactory.getEntityIntegerAttributeEdit();
    }

    @Override
    public EntityIntegerAttribute getEntity(EditEntityIntegerAttributeResult result) {
        EntityIntegerAttribute entity = null;

        entityInstance = entityInstanceLogic.getEntityInstance(this, spec);

        if(!hasExecutionErrors()) {
            entityAttribute = entityAttributeLogic.getEntityAttribute(this, entityInstance, spec, spec);
        }

        if(!hasExecutionErrors()) {
            entity = entityIntegerAttributeLogic.getEntityIntegerAttribute(this, entityAttribute, entityInstance, editModeToEntityPermission(editMode));
        }

        return entity;
    }

    @Override
    public EntityInstance getLockEntity(EntityIntegerAttribute entityIntegerAttribute) {
        return entityInstance;
    }

    @Override
    protected BasePK getLockTargetPK(BaseEntity lockTarget) {
        return PersistenceUtils.getInstance().getBasePKFromEntityInstance((EntityInstance)lockTarget);
    }

    @Override
    public void fillInResult(EditEntityIntegerAttributeResult result, EntityIntegerAttribute entityIntegerAttribute) {
        result.setEntityIntegerAttribute(coreControl.getEntityIntegerAttributeTransfer(getUserVisit(), entityIntegerAttribute, entityInstance));
    }

    @Override
    public void doLock(EntityIntegerAttributeEdit edit, EntityIntegerAttribute entityIntegerAttribute) {
        edit.setIntegerAttribute(entityIntegerAttribute.getIntegerAttribute().toString());
    }

    @Override
    protected void canUpdate(EntityIntegerAttribute entityIntegerAttribute) {
        var entityAttributeInteger = coreControl.getEntityAttributeInteger(entityAttribute);
        integerAttribute = Integer.valueOf(edit.getIntegerAttribute());

        if(entityAttributeInteger != null) {
            var upperRangeIntegerValue = entityAttributeInteger.getUpperRangeIntegerValue();
            var lowerRangeIntegerValue = entityAttributeInteger.getLowerRangeIntegerValue();

            if(upperRangeIntegerValue != null && integerAttribute > upperRangeIntegerValue) {
                addExecutionError(ExecutionErrors.UpperRangeExceeded.name(),
                        upperRangeIntegerValue, integerAttribute);
            }

            if(lowerRangeIntegerValue != null && integerAttribute < lowerRangeIntegerValue) {
                addExecutionError(ExecutionErrors.LowerRangeExceeded.name(),
                        lowerRangeIntegerValue, integerAttribute);
            }
        }
    }

    @Override
    public void doUpdate(EntityIntegerAttribute entityIntegerAttribute) {
        var entityIntegerAttributeValue = coreControl.getEntityIntegerAttributeValueForUpdate(entityIntegerAttribute);

        entityIntegerAttributeValue.setIntegerAttribute(integerAttribute);

        entityIntegerAttributeLogic.updateEntityIntegerAttributeFromValue(this, entityIntegerAttributeValue, getPartyPK());
    }

}

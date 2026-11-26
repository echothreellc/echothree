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
import com.echothree.control.user.core.common.edit.EntityEntityAttributeEdit;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityEntityAttributeResult;
import com.echothree.control.user.core.common.spec.EntityEntityAttributeSpec;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityDataLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityEntityAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.util.common.command.EditMode;
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
public class EditEntityEntityAttributeCommand
        extends BaseAbstractEditCommand<EntityEntityAttributeSpec, EntityEntityAttributeEdit, EditEntityEntityAttributeResult, EntityEntityAttribute, EntityInstance> {

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
                new FieldDefinition("EntityRefAttribute", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("UuidAttribute", FieldType.UUID, false, null, null)
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
    private EntityInstance entityInstanceAttribute;

    /** Creates a new instance of EditEntityEntityAttributeCommand */
    public EditEntityEntityAttributeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditEntityEntityAttributeResult getResult() {
        return CoreResultFactory.getEditEntityEntityAttributeResult();
    }

    @Override
    public EntityEntityAttributeEdit getEdit() {
        return CoreEditFactory.getEntityEntityAttributeEdit();
    }

    @Override
    public EntityEntityAttribute getEntity(EditEntityEntityAttributeResult result) {
        EntityEntityAttribute entity = null;

        entityInstance = entityInstanceLogic.getEntityInstance(this, spec);

        if(!hasExecutionErrors()) {
            entityAttribute = entityAttributeLogic.getEntityAttribute(this, entityInstance, spec, spec, EntityAttributeTypes.ENTITY);
        }

        var ownerResolved = !hasExecutionErrors();

        if(!hasExecutionErrors() && editMode == EditMode.UPDATE) {
            entityInstanceAttribute = entityAttributeLogic.getEntityInstanceAttribute(this, edit);

            if(!hasExecutionErrors() && coreControl.countEntityAttributeEntityTypesByEntityAttribute(entityAttribute) > 0) {
                var allowedEntityType = entityInstanceAttribute.getEntityType();

                if(!coreControl.entityAttributeEntityTypeExists(entityAttribute, allowedEntityType)) {
                    var entityAttributeDetail = entityAttribute.getLastDetail();
                    var entityTypeDetail = entityAttributeDetail.getEntityType().getLastDetail();
                    var allowedEntityTypeDetail = allowedEntityType.getLastDetail();

                    addExecutionError(ExecutionErrors.UnknownEntityAttributeEntityType.name(),
                            entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                            entityTypeDetail.getEntityTypeName(), entityAttributeDetail.getEntityAttributeName(),
                            allowedEntityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                            allowedEntityTypeDetail.getEntityTypeName());
                }
            }
        }

        if(!hasExecutionErrors()) {
            entity = entityDataLogic.getEntityEntityAttribute(this, entityAttribute, entityInstance, editModeToEntityPermission(editMode));
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
    public EntityInstance getLockEntity(EntityEntityAttribute entityEntityAttribute) {
        return entityInstance;
    }

    @Override
    protected BasePK getLockTargetPK(BaseEntity lockTarget) {
        return PersistenceUtils.getInstance().getBasePKFromEntityInstance((EntityInstance)lockTarget);
    }

    @Override
    public void fillInResult(EditEntityEntityAttributeResult result, EntityEntityAttribute entityEntityAttribute) {
        result.setEntityEntityAttribute(coreControl.getEntityEntityAttributeTransfer(getUserVisit(), entityEntityAttribute, entityInstance));
    }

    @Override
    public void doLock(EntityEntityAttributeEdit edit, EntityEntityAttribute entityEntityAttribute) {
        edit.setEntityRefAttribute(entityInstanceLogic.getEntityRefFromEntityInstance(entityEntityAttribute.getEntityInstanceAttribute()));
    }

    @Override
    public void doUpdate(EntityEntityAttribute entityEntityAttribute) {
        var entityEntityAttributeValue = coreControl.getEntityEntityAttributeValueForUpdate(entityEntityAttribute);

        entityEntityAttributeValue.setEntityInstanceAttributePK(entityInstanceAttribute.getPrimaryKey());

        coreControl.updateEntityEntityAttributeFromValue(entityEntityAttributeValue, getPartyPK());
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

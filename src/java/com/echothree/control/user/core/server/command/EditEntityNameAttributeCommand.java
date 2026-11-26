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
import com.echothree.control.user.core.common.edit.EntityNameAttributeEdit;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityNameAttributeResult;
import com.echothree.control.user.core.common.spec.EntityNameAttributeSpec;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityNameAttribute;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.PersistenceUtils;
import com.google.re2j.Pattern;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditEntityNameAttributeCommand
        extends BaseAbstractEditCommand<EntityNameAttributeSpec, EntityNameAttributeEdit, EditEntityNameAttributeResult, EntityNameAttribute, EntityInstance> {

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
                new FieldDefinition("NameAttribute", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    EntityAttributeLogic entityAttributeLogic;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    private EntityInstance entityInstance;
    private EntityAttribute entityAttribute;
    private String stringAttribute;

    /** Creates a new instance of EditEntityNameAttributeCommand */
    public EditEntityNameAttributeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditEntityNameAttributeResult getResult() {
        return CoreResultFactory.getEditEntityNameAttributeResult();
    }

    @Override
    public EntityNameAttributeEdit getEdit() {
        return CoreEditFactory.getEntityNameAttributeEdit();
    }

    @Override
    public EntityNameAttribute getEntity(EditEntityNameAttributeResult result) {
        EntityNameAttribute entity = null;

        entityInstance = entityInstanceLogic.getEntityInstance(this, spec);

        if(!hasExecutionErrors()) {
            entityAttribute = entityAttributeLogic.getEntityAttribute(this, entityInstance, spec, spec);
        }

        if(!hasExecutionErrors()) {
            entity = coreControl.getEntityNameAttribute(entityAttribute, entityInstance, editModeToEntityPermission(editMode));

            if(entity == null) {
                var entityTypeDetail = entityAttribute.getLastDetail().getEntityType().getLastDetail();

                addExecutionError(ExecutionErrors.UnknownEntityNameAttribute.name(),
                        entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance), entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                        entityTypeDetail.getEntityTypeName(), entityAttribute.getLastDetail().getEntityAttributeName());
            }
        }

        return entity;
    }

    @Override
    public EntityInstance getLockEntity(EntityNameAttribute entityNameAttribute) {
        return entityInstance;
    }

    @Override
    protected BasePK getLockTargetPK(BaseEntity lockTarget) {
        return PersistenceUtils.getInstance().getBasePKFromEntityInstance((EntityInstance)lockTarget);
    }

    @Override
    public void fillInResult(EditEntityNameAttributeResult result, EntityNameAttribute entityNameAttribute) {
        result.setEntityNameAttribute(coreControl.getEntityNameAttributeTransfer(getUserVisit(), entityNameAttribute, entityInstance));
    }

    @Override
    public void doLock(EntityNameAttributeEdit edit, EntityNameAttribute entityNameAttribute) {
        edit.setNameAttribute(entityNameAttribute.getNameAttribute());
    }

    @Override
    protected void canUpdate(EntityNameAttribute entityNameAttribute) {
        var entityAttributeString = coreControl.getEntityAttributeString(entityAttribute);
        var validationPattern = entityAttributeString == null ? null : entityAttributeString.getValidationPattern();
        stringAttribute = edit.getNameAttribute();

        if(validationPattern != null) {
            var pattern = Pattern.compile(validationPattern);
            var matcher = pattern.matcher(stringAttribute);

            if(!matcher.matches()) {
                addExecutionError(ExecutionErrors.InvalidNameAttribute.name(), stringAttribute);
            }
        }
    }

    @Override
    public void doUpdate(EntityNameAttribute entityNameAttribute) {
        var entityNameAttributeValue = coreControl.getEntityNameAttributeValueForUpdate(entityNameAttribute);

        entityNameAttributeValue.setNameAttribute(stringAttribute);

        coreControl.updateEntityNameAttributeFromValue(entityNameAttributeValue, getPartyPK());
    }

}

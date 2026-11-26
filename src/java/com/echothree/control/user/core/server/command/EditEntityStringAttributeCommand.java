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
import com.echothree.control.user.core.common.edit.EntityStringAttributeEdit;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityStringAttributeResult;
import com.echothree.control.user.core.common.spec.EntityStringAttributeSpec;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityStringAttribute;
import com.echothree.model.data.party.server.entity.Language;
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
public class EditEntityStringAttributeCommand
        extends BaseAbstractEditCommand<EntityStringAttributeSpec, EntityStringAttributeEdit, EditEntityStringAttributeResult, EntityStringAttribute, EntityInstance> {

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
                new FieldDefinition("EntityAttributeUuid", FieldType.UUID, false, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LanguageUuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("StringAttribute", FieldType.STRING, true, 1L, 512L)
        );
    }

    @Inject
    EntityAttributeLogic entityAttributeLogic;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    LanguageLogic languageLogic;

    private EntityInstance entityInstance;
    private EntityAttribute entityAttribute;
    private Language language;
    private String stringAttribute;

    /** Creates a new instance of EditEntityStringAttributeCommand */
    public EditEntityStringAttributeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditEntityStringAttributeResult getResult() {
        return CoreResultFactory.getEditEntityStringAttributeResult();
    }

    @Override
    public EntityStringAttributeEdit getEdit() {
        return CoreEditFactory.getEntityStringAttributeEdit();
    }

    @Override
    public EntityStringAttribute getEntity(EditEntityStringAttributeResult result) {
        EntityStringAttribute entity = null;

        entityInstance = entityInstanceLogic.getEntityInstance(this, spec);

        if(!hasExecutionErrors()) {
            entityAttribute = entityAttributeLogic.getEntityAttribute(this, entityInstance, spec, spec);
        }

        if(!hasExecutionErrors()) {
            language = languageLogic.getLanguage(this, spec, spec);
        }

        if(!hasExecutionErrors()) {
            entity = entityAttributeLogic.getEntityStringAttribute(this, entityAttribute, entityInstance, language, editModeToEntityPermission(editMode));
        }

        return entity;
    }

    @Override
    public EntityInstance getLockEntity(EntityStringAttribute entityStringAttribute) {
        return entityInstance;
    }

    @Override
    protected BasePK getLockTargetPK(BaseEntity lockTarget) {
        return PersistenceUtils.getInstance().getBasePKFromEntityInstance((EntityInstance)lockTarget);
    }

    @Override
    public void fillInResult(EditEntityStringAttributeResult result, EntityStringAttribute entityStringAttribute) {
        result.setEntityStringAttribute(coreControl.getEntityStringAttributeTransfer(getUserVisit(), entityStringAttribute, entityInstance));
    }

    @Override
    public void doLock(EntityStringAttributeEdit edit, EntityStringAttribute entityStringAttribute) {
        edit.setStringAttribute(entityStringAttribute.getStringAttribute());
    }

    @Override
    protected void canUpdate(EntityStringAttribute entityStringAttribute) {
        var entityAttributeString = coreControl.getEntityAttributeString(entityAttribute);
        var validationPattern = entityAttributeString == null ? null : entityAttributeString.getValidationPattern();
        stringAttribute = edit.getStringAttribute();

        if(validationPattern != null) {
            var pattern = Pattern.compile(validationPattern);
            var m = pattern.matcher(stringAttribute);

            if(!m.matches()) {
                addExecutionError(ExecutionErrors.InvalidStringAttribute.name(), stringAttribute);
            }
        }
    }

    @Override
    public void doUpdate(EntityStringAttribute entityStringAttribute) {
        var entityStringAttributeValue = coreControl.getEntityStringAttributeValueForUpdate(entityStringAttribute);

        entityStringAttributeValue.setStringAttribute(stringAttribute);

        coreControl.updateEntityStringAttributeFromValue(entityStringAttributeValue, getPartyPK());
    }

}

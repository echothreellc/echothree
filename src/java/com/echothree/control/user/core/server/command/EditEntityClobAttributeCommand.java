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
import com.echothree.control.user.core.common.edit.EntityClobAttributeEdit;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityClobAttributeResult;
import com.echothree.control.user.core.common.spec.EntityClobAttributeSpec;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityDataLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityClobAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.party.server.entity.Language;
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
public class EditEntityClobAttributeCommand
        extends BaseAbstractEditCommand<EntityClobAttributeSpec, EntityClobAttributeEdit, EditEntityClobAttributeResult, EntityClobAttribute, EntityInstance> {

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
                new FieldDefinition("ClobAttribute", FieldType.STRING, true, 1L, null),
                new FieldDefinition("MimeTypeName", FieldType.MIME_TYPE, true, null, null)
        );
    }

    @Inject
    EntityAttributeLogic entityAttributeLogic;

    @Inject
    EntityDataLogic entityDataLogic;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    LanguageLogic languageLogic;

    @Inject
    MimeTypeLogic mimeTypeLogic;

    private EntityInstance entityInstance;
    private EntityAttribute entityAttribute;
    private Language language;
    private MimeType mimeType;

    /** Creates a new instance of EditEntityClobAttributeCommand */
    public EditEntityClobAttributeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditEntityClobAttributeResult getResult() {
        return CoreResultFactory.getEditEntityClobAttributeResult();
    }

    @Override
    public EntityClobAttributeEdit getEdit() {
        return CoreEditFactory.getEntityClobAttributeEdit();
    }

    @Override
    public EntityClobAttribute getEntity(EditEntityClobAttributeResult result) {
        EntityClobAttribute entity = null;

        entityInstance = entityInstanceLogic.getEntityInstance(this, spec);

        if(!hasExecutionErrors()) {
            language = languageLogic.getLanguage(this, spec, spec);
            entityAttribute = entityAttributeLogic.getEntityAttribute(this, entityInstance, spec, spec, EntityAttributeTypes.CLOB);
        }

        var ownerResolved = !hasExecutionErrors();

        if(!hasExecutionErrors()) {
            entity = entityDataLogic.getEntityClobAttribute(this, entityAttribute, entityInstance, language, editModeToEntityPermission(editMode));
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
    public EntityInstance getLockEntity(EntityClobAttribute entityClobAttribute) {
        return entityInstance;
    }

    @Override
    protected BasePK getLockTargetPK(BaseEntity lockTarget) {
        return PersistenceUtils.getInstance().getBasePKFromEntityInstance((EntityInstance)lockTarget);
    }

    @Override
    public void fillInResult(EditEntityClobAttributeResult result, EntityClobAttribute entityClobAttribute) {
        result.setEntityClobAttribute(coreControl.getEntityClobAttributeTransfer(getUserVisit(), entityClobAttribute, entityInstance));
    }

    @Override
    public void doLock(EntityClobAttributeEdit edit, EntityClobAttribute entityClobAttribute) {
        edit.setClobAttribute(entityClobAttribute.getClobAttribute());
        edit.setMimeTypeName(entityClobAttribute.getMimeType().getLastDetail().getMimeTypeName());
    }

    @Override
    protected void canUpdate(EntityClobAttribute entityClobAttribute) {
        mimeType = mimeTypeLogic.getMimeTypeByName(this, edit.getMimeTypeName());

        if(!hasExecutionErrors()) {
            if(!mimeType.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName().equals(EntityAttributeTypes.CLOB.name())) {
                addExecutionError(ExecutionErrors.InvalidMimeType.name(), mimeType.getLastDetail().getMimeTypeName());
            }
        }
    }

    @Override
    public void doUpdate(EntityClobAttribute entityClobAttribute) {
        var entityClobAttributeValue = coreControl.getEntityClobAttributeValueForUpdate(entityClobAttribute);

        entityClobAttributeValue.setClobAttribute(edit.getClobAttribute());
        entityClobAttributeValue.setMimeTypePK(mimeType.getPrimaryKey());

        coreControl.updateEntityClobAttributeFromValue(entityClobAttributeValue, getPartyPK());
    }

    @Override
    protected void saveResultAfterEditValidatorErrors() {
        // Preserve the existing absence of a result when edit field validation fails.
    }

}

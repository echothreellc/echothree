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
import com.echothree.control.user.core.common.edit.EntityBlobAttributeEdit;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityBlobAttributeResult;
import com.echothree.control.user.core.common.spec.EntityBlobAttributeSpec;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityBlobAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityBlobAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.MimeType;
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
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditEntityBlobAttributeCommand
        extends BaseAbstractEditCommand<EntityBlobAttributeSpec, EntityBlobAttributeEdit, EditEntityBlobAttributeResult, EntityBlobAttribute, EntityInstance> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), null)
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, true, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("MimeTypeName", FieldType.MIME_TYPE, true, null, null)
        );
    }

    @Inject
    EntityAttributeLogic entityAttributeLogic;

    @Inject
    EntityBlobAttributeLogic entityBlobAttributeLogic;

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

    /** Creates a new instance of EditEntityBlobAttributeCommand */
    public EditEntityBlobAttributeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditEntityBlobAttributeResult getResult() {
        return CoreResultFactory.getEditEntityBlobAttributeResult();
    }

    @Override
    public EntityBlobAttributeEdit getEdit() {
        return CoreEditFactory.getEntityBlobAttributeEdit();
    }

    @Override
    public EntityBlobAttribute getEntity(EditEntityBlobAttributeResult result) {
        EntityBlobAttribute entity = null;

        entityInstance = entityInstanceLogic.getEntityInstanceByEntityRef(this, spec.getEntityRef());

        if(!hasExecutionErrors()) {
            entityAttribute = entityAttributeLogic.getEntityAttributeByName(this, entityInstance.getEntityType(), spec.getEntityAttributeName());
        }

        if(!hasExecutionErrors()) {
            language = languageLogic.getLanguageByName(this, spec.getLanguageIsoName());
        }

        if(!hasExecutionErrors()) {
            entity = entityBlobAttributeLogic.getEntityBlobAttribute(this, entityAttribute, entityInstance, language, editModeToEntityPermission(editMode));
        }

        return entity;
    }

    @Override
    public EntityInstance getLockEntity(EntityBlobAttribute entityBlobAttribute) {
        return entityInstance;
    }

    @Override
    protected BasePK getLockTargetPK(BaseEntity lockTarget) {
        return PersistenceUtils.getInstance().getBasePKFromEntityInstance((EntityInstance)lockTarget);
    }

    @Override
    public void fillInResult(EditEntityBlobAttributeResult result, EntityBlobAttribute entityBlobAttribute) {
        result.setEntityBlobAttribute(coreControl.getEntityBlobAttributeTransfer(getUserVisit(), entityBlobAttribute, entityInstance));
    }

    @Override
    public void doLock(EntityBlobAttributeEdit edit, EntityBlobAttribute entityBlobAttribute) {
        edit.setMimeTypeName(entityBlobAttribute.getMimeType().getLastDetail().getMimeTypeName());
    }

    @Override
    protected void canUpdate(EntityBlobAttribute entityBlobAttribute) {
        mimeType = mimeTypeLogic.getMimeTypeByName(this, edit.getMimeTypeName());

        if(!hasExecutionErrors()) {
            if(!mimeType.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName().equals(EntityAttributeTypes.BLOB.name())) {
                addExecutionError(ExecutionErrors.InvalidMimeType.name(), mimeType.getLastDetail().getMimeTypeName());
            } else if(edit.getBlobAttribute() == null) {
                addExecutionError(ExecutionErrors.MissingBlobAttribute.name());
            }
        }
    }

    @Override
    public void doUpdate(EntityBlobAttribute entityBlobAttribute) {
        var entityBlobAttributeValue = coreControl.getEntityBlobAttributeValueForUpdate(entityBlobAttribute);

        entityBlobAttributeValue.setBlobAttribute(edit.getBlobAttribute());
        entityBlobAttributeValue.setMimeTypePK(mimeType.getPrimaryKey());

        coreControl.updateEntityBlobAttributeFromValue(entityBlobAttributeValue, getPartyPK());
    }

}

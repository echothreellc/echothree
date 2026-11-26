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
import com.echothree.control.user.core.common.edit.EntityListItemAttributeEdit;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityListItemDefaultResult;
import com.echothree.control.user.core.common.spec.EntityListItemDefaultSpec;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityListItemAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityListItemLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityListItem;
import com.echothree.model.data.core.server.entity.EntityListItemDefault;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditEntityListItemDefaultCommand
        extends BaseEditCommand<EntityListItemDefaultSpec, EntityListItemAttributeEdit, EditEntityListItemDefaultResult, EntityListItemDefault, EntityAttribute> {

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
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EntityListItemName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityListItemUuid", FieldType.UUID, false, null, null)
        );
    }

    @Inject
    EntityAttributeLogic entityAttributeLogic;

    @Inject
    EntityListItemAttributeLogic entityListItemAttributeLogic;

    @Inject
    EntityListItemLogic entityListItemLogic;

    private EntityAttribute entityAttribute;
    private EntityListItem entityListItem;

    /** Creates a new instance of EditEntityListItemDefaultCommand */
    public EditEntityListItemDefaultCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditEntityListItemDefaultResult getResult() {
        return CoreResultFactory.getEditEntityListItemDefaultResult();
    }

    @Override
    public EntityListItemAttributeEdit getEdit() {
        return CoreEditFactory.getEntityListItemAttributeEdit();
    }

    @Override
    public EntityListItemDefault getEntity(EditEntityListItemDefaultResult result) {
        EntityListItemDefault entity = null;

        entityAttribute = entityAttributeLogic.getEntityAttributeByUniversalSpec(this, spec);

        if(!hasExecutionErrors()) {
            entity = entityListItemAttributeLogic.getEntityListItemDefault(this, entityAttribute, editModeToEntityPermission(editMode));
        }

        return entity;
    }

    @Override
    public EntityAttribute getLockEntity(EntityListItemDefault entityListItemDefault) {
        return entityAttribute;
    }

    @Override
    public void fillInResult(EditEntityListItemDefaultResult result, EntityListItemDefault entityListItemDefault) {
        result.setEntityListItemDefault(coreControl.getEntityListItemDefaultTransfer(getUserVisit(), entityListItemDefault));
    }

    @Override
    public void doLock(EntityListItemAttributeEdit edit, EntityListItemDefault entityListItemDefault) {
        edit.setEntityListItemName(entityListItemDefault.getEntityListItem().getLastDetail().getEntityListItemName());
    }

    @Override
    protected void canUpdate(EntityListItemDefault entityListItemDefault) {
        entityListItem = entityListItemLogic.getEntityListItem(this, entityAttribute, edit);
    }

    @Override
    public void doUpdate(EntityListItemDefault entityListItemDefault) {
        var entityListItemDefaultValue = coreControl.getEntityListItemDefaultValueForUpdate(entityListItemDefault);

        entityListItemDefaultValue.setEntityListItemPK(entityListItem.getPrimaryKey());

        entityListItemAttributeLogic.updateEntityListItemDefaultFromValue(this, entityListItemDefaultValue, getPartyPK());
    }

}

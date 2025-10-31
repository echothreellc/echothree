// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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
import com.echothree.control.user.core.common.form.EditEntityListItemAttributeForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.spec.EntityListItemAttributeSpec;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.data.core.server.entity.EntityListItemAttribute;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.persistence.PersistenceUtils;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditEntityListItemAttributeCommand
        extends BaseEditCommand<EntityListItemAttributeSpec, EntityListItemAttributeEdit> {

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
                new FieldDefinition("EntityListItemName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityListItemUuid", FieldType.UUID, false, null, null)
        );
    }
    
    /** Creates a new instance of EditEntityListItemAttributeCommand */
    public EditEntityListItemAttributeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var coreControl = getCoreControl();
        var result = CoreResultFactory.getEditEntityListItemAttributeResult();
        var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, spec);

        if(!hasExecutionErrors()) {
            var entityAttribute = EntityAttributeLogic.getInstance().getEntityAttribute(this, entityInstance, spec, spec,
                    EntityAttributeTypes.LISTITEM);

            if(!hasExecutionErrors()) {
                EntityListItemAttribute entityListItemAttribute = null;
                var basePK = PersistenceUtils.getInstance().getBasePKFromEntityInstance(entityInstance);

                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    entityListItemAttribute = coreControl.getEntityListItemAttribute(entityAttribute, entityInstance);

                    if(entityListItemAttribute != null) {
                        if(editMode.equals(EditMode.LOCK)) {
                            result.setEntityListItemAttribute(coreControl.getEntityListItemAttributeTransfer(getUserVisit(), entityListItemAttribute, entityInstance));

                            if(lockEntity(basePK)) {
                                var edit = CoreEditFactory.getEntityListItemAttributeEdit();

                                result.setEdit(edit);
                                edit.setEntityListItemName(entityListItemAttribute.getEntityListItem().getLastDetail().getEntityListItemName());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                        } else { // EditMode.ABANDON
                            unlockEntity(basePK);
                            basePK = null;
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownEntityListItemAttribute.name(),
                                EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance),
                                entityAttribute.getLastDetail().getEntityAttributeName());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    var entityListItem = EntityAttributeLogic.getInstance().getEntityListItem(this, entityAttribute, edit);

                    if(!hasExecutionErrors()) {
                        entityListItemAttribute = coreControl.getEntityListItemAttributeForUpdate(entityAttribute, entityInstance);

                        if(entityListItemAttribute != null) {
                            if(lockEntityForUpdate(basePK)) {
                                try {
                                    var entityListItemAttributeValue = coreControl.getEntityListItemAttributeValueForUpdate(entityListItemAttribute);

                                    entityListItemAttributeValue.setEntityListItemPK(entityListItem.getPrimaryKey());

                                    coreControl.updateEntityListItemAttributeFromValue(entityListItemAttributeValue, getPartyPK());
                                } finally {
                                    unlockEntity(basePK);
                                    basePK = null;
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownEntityListItemAttribute.name(),
                                    EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance),
                                    entityAttribute.getLastDetail().getEntityAttributeName());
                        }
                    }
                }

                if(basePK != null) {
                    result.setEntityLock(getEntityLockTransfer(basePK));
                }

                if(entityListItemAttribute != null) {
                    result.setEntityListItemAttribute(coreControl.getEntityListItemAttributeTransfer(getUserVisit(), entityListItemAttribute, entityInstance));
                }
            }
        }

        return result;
    }
    
}

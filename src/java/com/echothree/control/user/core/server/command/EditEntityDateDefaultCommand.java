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
import com.echothree.control.user.core.common.edit.EntityDateDefaultEdit;
import com.echothree.control.user.core.common.form.EditEntityDateDefaultForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityDateDefaultResult;
import com.echothree.control.user.core.common.spec.EntityDateDefaultSpec;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityDateDefault;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditEntityDateDefaultCommand
        extends BaseAbstractEditCommand<EntityDateDefaultSpec, EntityDateDefaultEdit, EditEntityDateDefaultResult, EntityDateDefault, EntityAttribute> {
    
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
                new FieldDefinition("DateAttribute", FieldType.DATE, true, null, null)
        );
    }
    
    /** Creates a new instance of EditEntityDateDefaultCommand */
    public EditEntityDateDefaultCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditEntityDateDefaultResult getResult() {
        return CoreResultFactory.getEditEntityDateDefaultResult();
    }

    @Override
    public EntityDateDefaultEdit getEdit() {
        return CoreEditFactory.getEntityDateDefaultEdit();
    }

    @Override
    public EntityDateDefault getEntity(EditEntityDateDefaultResult result) {
        var entityAttribute = EntityAttributeLogic.getInstance().getEntityAttributeByUniversalSpec(this, spec);
        EntityDateDefault entityDateDefault = null;

        if(!hasExecutionErrors()) {

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                entityDateDefault = coreControl.getEntityDateDefault(entityAttribute);
            } else { // EditMode.UPDATE
                entityDateDefault = coreControl.getEntityDateDefaultForUpdate(entityAttribute);
            }

            if(entityDateDefault == null) {
                addExecutionError(ExecutionErrors.UnknownEntityDateDefault.name());
            }
        }

        return entityDateDefault;
    }

    @Override
    public EntityAttribute getLockEntity(EntityDateDefault entityDateDefault) {
        return entityDateDefault.getEntityAttribute();
    }

    @Override
    public void fillInResult(EditEntityDateDefaultResult result, EntityDateDefault entityDateDefault) {

        result.setEntityDateDefault(coreControl.getEntityDateDefaultTransfer(getUserVisit(), entityDateDefault));
    }

    @Override
    public void doLock(EntityDateDefaultEdit edit, EntityDateDefault entityDateDefault) {
        edit.setDateAttribute(entityDateDefault.getDateAttribute().toString());
    }

    @Override
    public void doUpdate(EntityDateDefault entityDateDefault) {
        var entityDateDefaultValue = coreControl.getEntityDateDefaultValueForUpdate(entityDateDefault);

        entityDateDefaultValue.setDateAttribute(Integer.valueOf(edit.getDateAttribute()));

        coreControl.updateEntityDateDefaultFromValue(entityDateDefaultValue, getPartyPK());
    }
    
}

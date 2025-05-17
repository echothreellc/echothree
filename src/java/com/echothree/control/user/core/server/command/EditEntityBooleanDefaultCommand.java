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
import com.echothree.control.user.core.common.edit.EntityBooleanDefaultEdit;
import com.echothree.control.user.core.common.form.EditEntityBooleanDefaultForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityBooleanDefaultResult;
import com.echothree.control.user.core.common.spec.EntityBooleanDefaultSpec;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityBooleanDefault;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import java.util.List;

public class EditEntityBooleanDefaultCommand
        extends BaseAbstractEditCommand<EntityBooleanDefaultSpec, EntityBooleanDefaultEdit, EditEntityBooleanDefaultResult, EntityBooleanDefault, EntityAttribute> {
    
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
                new FieldDefinition("BooleanAttribute", FieldType.BOOLEAN, true, null, null)
        );
    }
    
    /** Creates a new instance of EditEntityBooleanDefaultCommand */
    public EditEntityBooleanDefaultCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditEntityBooleanDefaultResult getResult() {
        return CoreResultFactory.getEditEntityBooleanDefaultResult();
    }

    @Override
    public EntityBooleanDefaultEdit getEdit() {
        return CoreEditFactory.getEntityBooleanDefaultEdit();
    }

    @Override
    public EntityBooleanDefault getEntity(EditEntityBooleanDefaultResult result) {
        var entityAttribute = EntityAttributeLogic.getInstance().getEntityAttributeByUniversalSpec(this, spec);
        EntityBooleanDefault entityBooleanDefault = null;

        if(!hasExecutionErrors()) {
            var coreControl = getCoreControl();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                entityBooleanDefault = coreControl.getEntityBooleanDefault(entityAttribute);
            } else { // EditMode.UPDATE
                entityBooleanDefault = coreControl.getEntityBooleanDefaultForUpdate(entityAttribute);
            }

            if(entityBooleanDefault == null) {
                addExecutionError(ExecutionErrors.UnknownEntityBooleanDefault.name());
            }
        }

        return entityBooleanDefault;
    }

    @Override
    public EntityAttribute getLockEntity(EntityBooleanDefault entityBooleanDefault) {
        return entityBooleanDefault.getEntityAttribute();
    }

    @Override
    public void fillInResult(EditEntityBooleanDefaultResult result, EntityBooleanDefault entityBooleanDefault) {
        var coreControl = getCoreControl();

        result.setEntityBooleanDefault(coreControl.getEntityBooleanDefaultTransfer(getUserVisit(), entityBooleanDefault));
    }

    @Override
    public void doLock(EntityBooleanDefaultEdit edit, EntityBooleanDefault entityBooleanDefault) {
        edit.setBooleanAttribute(entityBooleanDefault.getBooleanAttribute().toString());
    }

    @Override
    public void doUpdate(EntityBooleanDefault entityBooleanDefault) {
        var coreControl = getCoreControl();
        var entityBooleanDefaultValue = coreControl.getEntityBooleanDefaultValueForUpdate(entityBooleanDefault);

        entityBooleanDefaultValue.setBooleanAttribute(Boolean.valueOf(edit.getBooleanAttribute()));

        coreControl.updateEntityBooleanDefaultFromValue(entityBooleanDefaultValue, getPartyPK());
    }
    
}

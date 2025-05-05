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
import com.echothree.control.user.core.common.edit.EntityIntegerDefaultEdit;
import com.echothree.control.user.core.common.form.EditEntityIntegerDefaultForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityIntegerDefaultResult;
import com.echothree.control.user.core.common.spec.EntityIntegerDefaultSpec;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityIntegerDefault;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import java.util.List;

public class EditEntityIntegerDefaultCommand
        extends BaseAbstractEditCommand<EntityIntegerDefaultSpec, EntityIntegerDefaultEdit, EditEntityIntegerDefaultResult, EntityIntegerDefault, EntityAttribute> {
    
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
                new FieldDefinition("IntegerAttribute", FieldType.SIGNED_INTEGER, true, null, null)
        );
    }
    
    /** Creates a new instance of EditEntityIntegerDefaultCommand */
    public EditEntityIntegerDefaultCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditEntityIntegerDefaultResult getResult() {
        return CoreResultFactory.getEditEntityIntegerDefaultResult();
    }

    @Override
    public EntityIntegerDefaultEdit getEdit() {
        return CoreEditFactory.getEntityIntegerDefaultEdit();
    }

    @Override
    public EntityIntegerDefault getEntity(EditEntityIntegerDefaultResult result) {
        var entityAttribute = EntityAttributeLogic.getInstance().getEntityAttributeByUniversalSpec(this, spec);
        EntityIntegerDefault entityIntegerDefault = null;

        if(!hasExecutionErrors()) {
            var coreControl = getCoreControl();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                entityIntegerDefault = coreControl.getEntityIntegerDefault(entityAttribute);
            } else { // EditMode.UPDATE
                entityIntegerDefault = coreControl.getEntityIntegerDefaultForUpdate(entityAttribute);
            }

            if(entityIntegerDefault == null) {
                addExecutionError(ExecutionErrors.UnknownEntityIntegerDefault.name());
            }
        }

        return entityIntegerDefault;
    }

    @Override
    public EntityAttribute getLockEntity(EntityIntegerDefault entityIntegerDefault) {
        return entityIntegerDefault.getEntityAttribute();
    }

    @Override
    public void fillInResult(EditEntityIntegerDefaultResult result, EntityIntegerDefault entityIntegerDefault) {
        var coreControl = getCoreControl();

        result.setEntityIntegerDefault(coreControl.getEntityIntegerDefaultTransfer(getUserVisit(), entityIntegerDefault));
    }

    @Override
    public void doLock(EntityIntegerDefaultEdit edit, EntityIntegerDefault entityIntegerDefault) {
        edit.setIntegerAttribute(entityIntegerDefault.getIntegerAttribute().toString());
    }

    @Override
    public void doUpdate(EntityIntegerDefault entityIntegerDefault) {
        var coreControl = getCoreControl();
        var entityIntegerDefaultValue = coreControl.getEntityIntegerDefaultValueForUpdate(entityIntegerDefault);

        entityIntegerDefaultValue.setIntegerAttribute(Integer.valueOf(edit.getIntegerAttribute()));

        coreControl.updateEntityIntegerDefaultFromValue(entityIntegerDefaultValue, getPartyPK());
    }
    
}

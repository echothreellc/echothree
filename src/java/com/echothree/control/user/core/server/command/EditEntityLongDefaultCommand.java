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
import com.echothree.control.user.core.common.edit.EntityLongDefaultEdit;
import com.echothree.control.user.core.common.form.EditEntityLongDefaultForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityLongDefaultResult;
import com.echothree.control.user.core.common.spec.EntityLongDefaultSpec;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityLongDefault;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditEntityLongDefaultCommand
        extends BaseAbstractEditCommand<EntityLongDefaultSpec, EntityLongDefaultEdit, EditEntityLongDefaultResult, EntityLongDefault, EntityAttribute> {
    
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
                new FieldDefinition("LongAttribute", FieldType.SIGNED_LONG, true, null, null)
        );
    }
    
    /** Creates a new instance of EditEntityLongDefaultCommand */
    public EditEntityLongDefaultCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditEntityLongDefaultResult getResult() {
        return CoreResultFactory.getEditEntityLongDefaultResult();
    }

    @Override
    public EntityLongDefaultEdit getEdit() {
        return CoreEditFactory.getEntityLongDefaultEdit();
    }

    @Override
    public EntityLongDefault getEntity(EditEntityLongDefaultResult result) {
        var entityAttribute = EntityAttributeLogic.getInstance().getEntityAttributeByUniversalSpec(this, spec);
        EntityLongDefault entityLongDefault = null;

        if(!hasExecutionErrors()) {

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                entityLongDefault = coreControl.getEntityLongDefault(entityAttribute);
            } else { // EditMode.UPDATE
                entityLongDefault = coreControl.getEntityLongDefaultForUpdate(entityAttribute);
            }

            if(entityLongDefault == null) {
                addExecutionError(ExecutionErrors.UnknownEntityLongDefault.name());
            }
        }

        return entityLongDefault;
    }

    @Override
    public EntityAttribute getLockEntity(EntityLongDefault entityLongDefault) {
        return entityLongDefault.getEntityAttribute();
    }

    @Override
    public void fillInResult(EditEntityLongDefaultResult result, EntityLongDefault entityLongDefault) {

        result.setEntityLongDefault(coreControl.getEntityLongDefaultTransfer(getUserVisit(), entityLongDefault));
    }

    @Override
    public void doLock(EntityLongDefaultEdit edit, EntityLongDefault entityLongDefault) {
        edit.setLongAttribute(entityLongDefault.getLongAttribute().toString());
    }

    @Override
    public void doUpdate(EntityLongDefault entityLongDefault) {
        var entityLongDefaultValue = coreControl.getEntityLongDefaultValueForUpdate(entityLongDefault);

        entityLongDefaultValue.setLongAttribute(Long.valueOf(edit.getLongAttribute()));

        coreControl.updateEntityLongDefaultFromValue(entityLongDefaultValue, getPartyPK());
    }
    
}

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
import com.echothree.control.user.core.common.edit.EntityTimeDefaultEdit;
import com.echothree.control.user.core.common.form.EditEntityTimeDefaultForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityTimeDefaultResult;
import com.echothree.control.user.core.common.spec.EntityTimeDefaultSpec;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityTimeDefault;
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
public class EditEntityTimeDefaultCommand
        extends BaseAbstractEditCommand<EntityTimeDefaultSpec, EntityTimeDefaultEdit, EditEntityTimeDefaultResult, EntityTimeDefault, EntityAttribute> {
    
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
                new FieldDefinition("TimeAttribute", FieldType.DATE_TIME, true, null, null)
        );
    }
    
    /** Creates a new instance of EditEntityTimeDefaultCommand */
    public EditEntityTimeDefaultCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditEntityTimeDefaultResult getResult() {
        return CoreResultFactory.getEditEntityTimeDefaultResult();
    }

    @Override
    public EntityTimeDefaultEdit getEdit() {
        return CoreEditFactory.getEntityTimeDefaultEdit();
    }

    @Override
    public EntityTimeDefault getEntity(EditEntityTimeDefaultResult result) {
        var entityAttribute = EntityAttributeLogic.getInstance().getEntityAttributeByUniversalSpec(this, spec);
        EntityTimeDefault entityTimeDefault = null;

        if(!hasExecutionErrors()) {

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                entityTimeDefault = coreControl.getEntityTimeDefault(entityAttribute);
            } else { // EditMode.UPDATE
                entityTimeDefault = coreControl.getEntityTimeDefaultForUpdate(entityAttribute);
            }

            if(entityTimeDefault == null) {
                addExecutionError(ExecutionErrors.UnknownEntityTimeDefault.name());
            }
        }

        return entityTimeDefault;
    }

    @Override
    public EntityAttribute getLockEntity(EntityTimeDefault entityTimeDefault) {
        return entityTimeDefault.getEntityAttribute();
    }

    @Override
    public void fillInResult(EditEntityTimeDefaultResult result, EntityTimeDefault entityTimeDefault) {

        result.setEntityTimeDefault(coreControl.getEntityTimeDefaultTransfer(getUserVisit(), entityTimeDefault));
    }

    @Override
    public void doLock(EntityTimeDefaultEdit edit, EntityTimeDefault entityTimeDefault) {
        edit.setTimeAttribute(entityTimeDefault.getTimeAttribute().toString());
    }

    @Override
    public void doUpdate(EntityTimeDefault entityTimeDefault) {
        var entityTimeDefaultValue = coreControl.getEntityTimeDefaultValueForUpdate(entityTimeDefault);

        entityTimeDefaultValue.setTimeAttribute(Long.valueOf(edit.getTimeAttribute()));

        coreControl.updateEntityTimeDefaultFromValue(entityTimeDefaultValue, getPartyPK());
    }
    
}

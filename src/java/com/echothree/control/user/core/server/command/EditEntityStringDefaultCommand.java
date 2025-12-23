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
import com.echothree.control.user.core.common.edit.EntityStringDefaultEdit;
import com.echothree.control.user.core.common.form.EditEntityStringDefaultForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityStringDefaultResult;
import com.echothree.control.user.core.common.spec.EntityStringDefaultSpec;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityStringDefault;
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
public class EditEntityStringDefaultCommand
        extends BaseAbstractEditCommand<EntityStringDefaultSpec, EntityStringDefaultEdit, EditEntityStringDefaultResult, EntityStringDefault, EntityAttribute> {
    
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
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LanguageUuid", FieldType.ENTITY_NAME, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("StringAttribute", FieldType.STRING, true, 1L, 512L)
        );
    }
    
    /** Creates a new instance of EditEntityStringDefaultCommand */
    public EditEntityStringDefaultCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditEntityStringDefaultResult getResult() {
        return CoreResultFactory.getEditEntityStringDefaultResult();
    }

    @Override
    public EntityStringDefaultEdit getEdit() {
        return CoreEditFactory.getEntityStringDefaultEdit();
    }

    @Override
    public EntityStringDefault getEntity(EditEntityStringDefaultResult result) {
        var entityAttribute = EntityAttributeLogic.getInstance().getEntityAttributeByUniversalSpec(this, spec);
        var language = LanguageLogic.getInstance().getLanguage(this, spec, spec);
        EntityStringDefault entityStringDefault = null;

        if(!hasExecutionErrors()) {

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                entityStringDefault = coreControl.getEntityStringDefault(entityAttribute, language);
            } else { // EditMode.UPDATE
                entityStringDefault = coreControl.getEntityStringDefaultForUpdate(entityAttribute, language);
            }

            if(entityStringDefault == null) {
                addExecutionError(ExecutionErrors.UnknownEntityStringDefault.name());
            }
        }

        return entityStringDefault;
    }

    @Override
    public EntityAttribute getLockEntity(EntityStringDefault entityStringDefault) {
        return entityStringDefault.getEntityAttribute();
    }

    @Override
    public void fillInResult(EditEntityStringDefaultResult result, EntityStringDefault entityStringDefault) {

        result.setEntityStringDefault(coreControl.getEntityStringDefaultTransfer(getUserVisit(), entityStringDefault));
    }

    @Override
    public void doLock(EntityStringDefaultEdit edit, EntityStringDefault entityStringDefault) {
        edit.setStringAttribute(entityStringDefault.getStringAttribute());
    }

    @Override
    public void doUpdate(EntityStringDefault entityStringDefault) {
        var entityStringDefaultValue = coreControl.getEntityStringDefaultValueForUpdate(entityStringDefault);

        entityStringDefaultValue.setStringAttribute(String.valueOf(edit.getStringAttribute()));

        coreControl.updateEntityStringDefaultFromValue(entityStringDefaultValue, getPartyPK());
    }
    
}

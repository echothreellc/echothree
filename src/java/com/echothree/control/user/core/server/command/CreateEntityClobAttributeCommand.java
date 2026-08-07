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

import com.echothree.control.user.core.common.form.CreateEntityClobAttributeForm;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class CreateEntityClobAttributeCommand
        extends BaseSimpleCommand<CreateEntityClobAttributeForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), null)
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityAttributeUuid", FieldType.UUID, false, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LanguageUuid", FieldType.UUID, false, null, null),
                new FieldDefinition("ClobAttribute", FieldType.STRING, true, 1L, null),
                new FieldDefinition("MimeTypeName", FieldType.MIME_TYPE, true, null, null)
                );
    }

    @Inject
    EntityAttributeLogic entityAttributeLogic;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    LanguageLogic languageLogic;

    @Inject
    MimeTypeLogic mimeTypeLogic;

    
    /** Creates a new instance of CreateEntityClobAttributeCommand */
    public CreateEntityClobAttributeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var entityInstance = entityInstanceLogic.getEntityInstance(this, form);
        var language = languageLogic.getLanguage(this, form, form);
        var entityAttribute = entityAttributeLogic.getEntityAttribute(this, entityInstance, form, form,
                EntityAttributeTypes.CLOB);
        var mimeType = mimeTypeLogic.getMimeTypeByName(this, form.getMimeTypeName());

        if(!hasExecutionErrors()) {
            entityAttributeLogic.createEntityClobAttribute(this, entityAttribute, entityInstance, language,
                    form.getClobAttribute(), mimeType, getPartyPK());
        }

        return null;
    }
    
}

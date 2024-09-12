// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.control.user.core.common.form.DeleteEntityStringAttributeForm;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DeleteEntityStringAttributeCommand
        extends BaseSimpleCommand<DeleteEntityStringAttributeForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), null)
        ));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Guid", FieldType.GUID, false, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityAttributeGuid", FieldType.ULID, false, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LanguageGuid", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteEntityStringAttributeCommand */
    public DeleteEntityStringAttributeCommand(UserVisitPK userVisitPK, DeleteEntityStringAttributeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var parameterCount = EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        if(parameterCount == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form);

            if(!hasExecutionErrors()) {
                var entityAttributeName = form.getEntityAttributeName();
                var entityAttributeGuid = form.getEntityAttributeGuid();
                
                parameterCount = (entityAttributeName == null ? 0 : 1) + (entityAttributeGuid == null ? 0 : 1);
                
                if(parameterCount == 1) {
                    var entityAttribute = entityAttributeName == null ?
                            EntityAttributeLogic.getInstance().getEntityAttributeByGuid(this, entityAttributeGuid) :
                            EntityAttributeLogic.getInstance().getEntityAttributeByName(this, entityInstance.getEntityType(), entityAttributeName);

                    if(!hasExecutionErrors()) {
                        if(entityInstance.getEntityType().equals(entityAttribute.getLastDetail().getEntityType())) {
                            var languageIsoName = form.getLanguageIsoName();
                            var languageGuid = form.getLanguageGuid();
                            
                            parameterCount = (languageIsoName == null ? 0 : 1) + (languageGuid == null ? 0 : 1);

                            if(parameterCount == 1) {
                                var language = languageIsoName == null ?
                                        LanguageLogic.getInstance().getLanguageByGuid(this, languageGuid) :
                                        LanguageLogic.getInstance().getLanguageByName(this, languageIsoName);

                                if(!hasExecutionErrors()) {
                                    var coreControl = getCoreControl();
                                    var entityStringAttribute = coreControl.getEntityStringAttributeForUpdate(entityAttribute, entityInstance, language);

                                    if(entityStringAttribute != null) {
                                        coreControl.deleteEntityStringAttribute(entityStringAttribute, getPartyPK());
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownEntityStringAttribute.name());
                                    }
                                }
                            } else {
                                addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                            }
                      } else {
                            addExecutionError(ExecutionErrors.MismatchedEntityType.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return null;
    }
    
}

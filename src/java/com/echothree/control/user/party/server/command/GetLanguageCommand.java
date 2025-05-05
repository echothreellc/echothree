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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.form.GetLanguageForm;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetLanguageCommand
        extends BaseSingleEntityCommand<Language, GetLanguageForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetLanguageCommand */
    public GetLanguageCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected Language getEntity() {
        var partyControl = Session.getModelController(PartyControl.class);
        Language language = null;
        var languageIsoName = form.getLanguageIsoName();
        var parameterCount = (languageIsoName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        switch(parameterCount) {
            case 0:
                language = partyControl.getDefaultLanguage();
                break;
            case 1:
                if(languageIsoName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.Language.name());
                    
                    if(!hasExecutionErrors()) {
                        language = partyControl.getLanguageByEntityInstance(entityInstance);
                    }
                } else {
                    language = LanguageLogic.getInstance().getLanguageByName(this, languageIsoName);
                }
                break;
            default:
                addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                break;
        }
        
        if(language != null) {
            sendEvent(language.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return language;
    }
    
    @Override
    protected BaseResult getResult(Language language) {
        var partyControl = Session.getModelController(PartyControl.class);
        var result = PartyResultFactory.getGetLanguageResult();

        if(language != null) {
            result.setLanguage(partyControl.getLanguageTransfer(getUserVisit(), language));
        }

        return result;
    }
    
}

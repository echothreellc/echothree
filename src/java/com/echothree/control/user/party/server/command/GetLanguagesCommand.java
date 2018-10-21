// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.party.remote.form.GetLanguagesForm;
import com.echothree.control.user.party.remote.result.GetLanguagesResult;
import com.echothree.control.user.party.remote.result.PartyResultFactory;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetLanguagesCommand
        extends BaseMultipleEntitiesCommand<Language, GetLanguagesForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                ));
    }
    
    /** Creates a new instance of GetLanguagesCommand */
    public GetLanguagesCommand(UserVisitPK userVisitPK, GetLanguagesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected Collection<Language> getEntities() {
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        
        return partyControl.getLanguages();
    }
    
    @Override
    protected BaseResult getTransfers(Collection<Language> entities) {
        GetLanguagesResult result = PartyResultFactory.getGetLanguagesResult();
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        
        result.setLanguages(partyControl.getLanguageTransfers(getUserVisit(), entities));
        
        return result;
    }
    
}

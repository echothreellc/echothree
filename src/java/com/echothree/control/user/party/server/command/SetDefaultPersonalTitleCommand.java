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

import com.echothree.control.user.party.common.form.SetDefaultPersonalTitleForm;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetDefaultPersonalTitleCommand
        extends BaseSimpleCommand<SetDefaultPersonalTitleForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("PersonalTitleId", FieldType.ID, true, null, null)
        ));
    }
    
    /** Creates a new instance of SetDefaultPersonalTitleCommand */
    public SetDefaultPersonalTitleCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var personalTitleId = form.getPersonalTitleId();
        var personalTitlePK = partyControl.convertPersonalTitleIdToPK(personalTitleId);
        
        if(personalTitlePK != null) {
            var personalTitleDetailValue = partyControl.getPersonalTitleDetailValueByPKForUpdate(personalTitlePK);
             
             personalTitleDetailValue.setIsDefault(true);
             partyControl.updatePersonalTitleFromValue(personalTitleDetailValue, getPartyPK());
        } else {
            addExecutionError(ExecutionErrors.UnknownPersonalTitleId.name(), personalTitleId);
        }
        
        return null;
    }
    
}

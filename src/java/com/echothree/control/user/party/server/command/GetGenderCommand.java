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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.form.GetGenderForm;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.Gender;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetGenderCommand
        extends BaseSingleEntityCommand<Gender, GetGenderForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("GenderName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    PartyControl partyControl;
    
    /** Creates a new instance of GetGenderCommand */
    public GetGenderCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected Gender getEntity() {
        var genderName = form.getGenderName();
        var gender = partyControl.getGenderByName(genderName);
        
        if(gender != null) {
            sendEvent(gender.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        } else {
            addExecutionError(ExecutionErrors.UnknownGenderName.name(), genderName);
        }

        return gender;
    }

    @Override
    protected BaseResult getResult(Gender gender) {
        var result = PartyResultFactory.getGetGenderResult();

        if(gender != null) {
            result.setGender(partyControl.getGenderTransfer(getUserVisit(), gender));
        }
        
        return result;
    }
    
}

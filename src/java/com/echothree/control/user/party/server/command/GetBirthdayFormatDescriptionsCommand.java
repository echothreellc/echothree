// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.party.common.form.GetBirthdayFormatDescriptionsForm;
import com.echothree.control.user.party.common.result.GetBirthdayFormatDescriptionsResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.server.entity.BirthdayFormat;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetBirthdayFormatDescriptionsCommand
        extends BaseSimpleCommand<GetBirthdayFormatDescriptionsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.BirthdayFormat.name(), SecurityRoles.Description.name())
                        )))
                )));
                
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("BirthdayFormatName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetBirthdayFormatDescriptionsCommand */
    public GetBirthdayFormatDescriptionsCommand(UserVisitPK userVisitPK, GetBirthdayFormatDescriptionsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);

        GetBirthdayFormatDescriptionsResult result = PartyResultFactory.getGetBirthdayFormatDescriptionsResult();
        String birthdayFormatName = form.getBirthdayFormatName();
        BirthdayFormat birthdayFormat = partyControl.getBirthdayFormatByName(birthdayFormatName);
        
        if(birthdayFormat != null) {
            result.setBirthdayFormat(partyControl.getBirthdayFormatTransfer(getUserVisit(), birthdayFormat));
            result.setBirthdayFormatDescriptions(partyControl.getBirthdayFormatDescriptionTransfers(getUserVisit(), birthdayFormat));
        } else {
            addExecutionError(ExecutionErrors.UnknownBirthdayFormatName.name(), birthdayFormatName);
        }
        
        return result;
    }
    
}

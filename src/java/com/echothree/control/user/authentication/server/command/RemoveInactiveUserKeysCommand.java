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

package com.echothree.control.user.authentication.server.command;

import com.echothree.control.user.authentication.common.form.RemoveInactiveUserKeysForm;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.user.server.logic.UserKeyLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RemoveInactiveUserKeysCommand
        extends BaseSimpleCommand<RemoveInactiveUserKeysForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null)
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("InactiveTime", FieldType.UNSIGNED_LONG, true, null, null)
                ));
    }
    
    /** Creates a new instance of RemoveInactiveUserKeysCommand */
    public RemoveInactiveUserKeysCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        Long maximumTime = 2L * 60 * 1000; // 2 minutes
        var inactiveTime = Long.valueOf(form.getInactiveTime());
        
        UserKeyLogic.getInstance().removeInactiveUserKeys(maximumTime, inactiveTime);
        
        return null;
    }
    
}

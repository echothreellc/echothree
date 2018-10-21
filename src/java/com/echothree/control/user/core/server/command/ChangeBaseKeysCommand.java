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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.remote.form.ChangeBaseKeysForm;
import com.echothree.control.user.core.remote.result.ChangeBaseKeysResult;
import com.echothree.control.user.core.remote.result.CoreResultFactory;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.persistence.EncryptionUtils;
import java.util.Arrays;
import java.util.Collections;

public class ChangeBaseKeysCommand
        extends BaseSimpleCommand<ChangeBaseKeysForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null)
                )));
    }
    
    /** Creates a new instance of ChangeBaseKeysCommand */
    public ChangeBaseKeysCommand(UserVisitPK userVisitPK, ChangeBaseKeysForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, null, false);
    }
    
    @Override
    protected BaseResult execute() {
        ChangeBaseKeysResult result = CoreResultFactory.getChangeBaseKeysResult();
        
        result.setBaseKeys(EncryptionUtils.getInstance().changeBaseKeys(this, form.getBaseKeys(), getPartyPK()));
        
        return result;
    }
    
}

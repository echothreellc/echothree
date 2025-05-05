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

import com.echothree.control.user.authentication.common.form.CustomerLoginForm;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LockoutPolicyLogic;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.user.server.logic.UserLoginLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CustomerLoginCommand
        extends BaseLoginCommand<CustomerLoginForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Username", FieldType.STRING, true, 1L, 80L),
                new FieldDefinition("Password", FieldType.STRING, true, 1L, 40L),
                new FieldDefinition("RemoteInet4Address", FieldType.INET_4_ADDRESS, false, null, null)
                ));
    }
    
    /** Creates a new instance of CustomerLoginCommand */
    public CustomerLoginCommand() {
        super(null, FORM_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var userLogin = UserLoginLogic.getInstance().getUserLoginByUsername(this, form.getUsername());
        
        if(!hasExecutionErrors()) {
            var party = userLogin.getParty();

            PartyLogic.getInstance().checkPartyType(this, party, PartyTypes.CUSTOMER.name());

            if(!hasExecutionErrors()) {
                var userControl = getUserControl();
                var userLoginStatus = userControl.getUserLoginStatusForUpdate(party);

                LockoutPolicyLogic.getInstance().checkUserLogin(session, this, party, userLoginStatus);
                
                if(!hasExecutionErrors()) {
                    if(checkPasswords(userLoginStatus, form.getPassword(), party, true)) {
                        var strRemoteInet4Address = form.getRemoteInet4Address();
                        var remoteInet4Address = strRemoteInet4Address == null ? null : Integer.valueOf(form.getRemoteInet4Address());
                        
                        successfulLogin(userLoginStatus, party, null, remoteInet4Address);
                    }
                }
                
                if(hasExecutionErrors()) {
                    unsuccessfulLogin(userLoginStatus);
                }
            }
        }
        
        return null;
    }
    
}

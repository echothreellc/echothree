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

package com.echothree.control.user.authentication.server;

import com.echothree.control.user.authentication.common.AuthenticationRemote;
import com.echothree.control.user.authentication.common.form.*;
import com.echothree.control.user.authentication.server.command.*;
import com.echothree.model.control.party.common.PartyNames;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.ThreadUtils;
import javax.ejb.Stateless;

@Stateless
public class AuthenticationBean
        extends AuthenticationFormsImpl
        implements AuthenticationRemote, AuthenticationLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "AuthenticationBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   User Visits and Sessions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult getJobUserVisit(GetJobUserVisitForm form) {
        return new GetJobUserVisitCommand().run(null, form);
    }
    
    @Override
    public UserVisitPK getDataLoaderUserVisit() {
        var preservedState = ThreadUtils.preserveState();

        UserVisitPK userVisitPK = null;
        
        try {
            var userControl = Session.getModelController(UserControl.class);
            var userVisit = userControl.createUserVisit(null, null, null, null, null, null, null, null);
            var partyControl = Session.getModelController(PartyControl.class);
            var party = partyControl.getPartyByName(PartyNames.DATA_LOADER.name());
            
            if(party == null) {
                var partyType = partyControl.getPartyTypeByName(PartyTypes.UTILITY.name());
                
                if(partyType != null) {
                    party = partyControl.createParty(PartyNames.DATA_LOADER.name(), partyType, null, null, null, null, null);
                }
            }
            
            if(party == null) {
                userControl.removeUserVisit(userVisit);
            } else {
                userControl.associatePartyToUserVisit(userVisit, party, null, null);
                userVisitPK = userVisit.getPrimaryKey();
            }
        } catch (PersistenceDatabaseException pde) {
            throw pde;
        } finally {
            ThreadUtils.close();
        }

        ThreadUtils.restoreState(preservedState);

        return userVisitPK;
    }
    
    @Override
    public CommandResult getUserVisit(GetUserVisitForm form) {
        return new GetUserVisitCommand().run(null, form);
    }
    
    @Override
    public void invalidateUserSession(UserVisitPK userVisitPK) {
        new InvalidateUserSessionCommand().run(userVisitPK);
    }
    
    @Override
    public void invalidateUserVisit(UserVisitPK userVisitPK) {
        new InvalidateUserVisitCommand().run(userVisitPK);
    }
    
    @Override
    public CommandResult invalidateAbandonedUserVisits(UserVisitPK userVisitPK, InvalidateAbandonedUserVisitsForm form) {
        return new InvalidateAbandonedUserVisitsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult removeInactiveUserKeys(UserVisitPK userVisitPK, RemoveInactiveUserKeysForm form) {
        return new RemoveInactiveUserKeysCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult removeInvalidatedUserVisits(UserVisitPK userVisitPK) {
        return new RemoveInvalidatedUserVisitsCommand().run(userVisitPK);
    }
    
    // -------------------------------------------------------------------------
    //   Logins
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult getCustomerLoginDefaults(UserVisitPK userVisitPK, GetCustomerLoginDefaultsForm form) {
        return new GetCustomerLoginDefaultsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult customerLogin(UserVisitPK userVisitPK, CustomerLoginForm form) {
        return new CustomerLoginCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEmployeeLoginDefaults(UserVisitPK userVisitPK, GetEmployeeLoginDefaultsForm form) {
        return new GetEmployeeLoginDefaultsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult employeeLogin(UserVisitPK userVisitPK, EmployeeLoginForm form) {
        return new EmployeeLoginCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getVendorLoginDefaults(UserVisitPK userVisitPK, GetVendorLoginDefaultsForm form) {
        return new GetVendorLoginDefaultsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult vendorLogin(UserVisitPK userVisitPK, VendorLoginForm form) {
        return new VendorLoginCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setPassword(UserVisitPK userVisitPK, SetPasswordForm form) {
        return new SetPasswordCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult recoverPassword(UserVisitPK userVisitPK, RecoverPasswordForm form) {
        return new RecoverPasswordCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult idle(UserVisitPK userVisitPK) {
        return new IdleCommand().run(userVisitPK);
    }
    
    @Override
    public CommandResult logout(UserVisitPK userVisitPK) {
        return new LogoutCommand().run(userVisitPK);
    }
    
}

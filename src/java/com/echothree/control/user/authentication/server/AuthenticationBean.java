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
import com.echothree.util.server.cdi.CommandScopeExtension;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

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
        return CDI.current().select(GetJobUserVisitCommand.class).get().run(null, form);
    }

    @Inject
    UserControl userControl;

    @Inject
    PartyControl partyControl;

    @Override
    public UserVisitPK getDataLoaderUserVisit() {
        if(CommandScopeExtension.getCommandScopeContext().isActive()) {
            CommandScopeExtension.getCommandScopeContext().push();
        } else {
            CommandScopeExtension.getCommandScopeContext().activate();
        }

        UserVisitPK userVisitPK = null;
        
        try {
            var userVisit = userControl.createUserVisit(null, null, null, null, null, null, null, null);
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
            CommandScopeExtension.getCommandScopeContext().pop();
        }

        return userVisitPK;
    }
    
    @Override
    public CommandResult getUserVisit(GetUserVisitForm form) {
        return CDI.current().select(GetUserVisitCommand.class).get().run(null, form);
    }
    
    @Override
    public void invalidateUserSession(UserVisitPK userVisitPK) {
        CDI.current().select(InvalidateUserSessionCommand.class).get().run(userVisitPK);
    }
    
    @Override
    public void invalidateUserVisit(UserVisitPK userVisitPK) {
        CDI.current().select(InvalidateUserVisitCommand.class).get().run(userVisitPK);
    }
    
    @Override
    public CommandResult invalidateAbandonedUserVisits(UserVisitPK userVisitPK, InvalidateAbandonedUserVisitsForm form) {
        return CDI.current().select(InvalidateAbandonedUserVisitsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult removeInactiveUserKeys(UserVisitPK userVisitPK, RemoveInactiveUserKeysForm form) {
        return CDI.current().select(RemoveInactiveUserKeysCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult removeInvalidatedUserVisits(UserVisitPK userVisitPK) {
        return CDI.current().select(RemoveInvalidatedUserVisitsCommand.class).get().run(userVisitPK);
    }
    
    // -------------------------------------------------------------------------
    //   Logins
    // -------------------------------------------------------------------------

    @Override
    public CommandResult getCustomerLoginDefaults(UserVisitPK userVisitPK, GetCustomerLoginDefaultsForm form) {
        return CDI.current().select(GetCustomerLoginDefaultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult customerLogin(UserVisitPK userVisitPK, CustomerLoginForm form) {
        return CDI.current().select(CustomerLoginCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEmployeeLoginDefaults(UserVisitPK userVisitPK, GetEmployeeLoginDefaultsForm form) {
        return CDI.current().select(GetEmployeeLoginDefaultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult employeeLogin(UserVisitPK userVisitPK, EmployeeLoginForm form) {
        return CDI.current().select(EmployeeLoginCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getVendorLoginDefaults(UserVisitPK userVisitPK, GetVendorLoginDefaultsForm form) {
        return CDI.current().select(GetVendorLoginDefaultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult vendorLogin(UserVisitPK userVisitPK, VendorLoginForm form) {
        return CDI.current().select(VendorLoginCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setPassword(UserVisitPK userVisitPK, SetPasswordForm form) {
        return CDI.current().select(SetPasswordCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult recoverPassword(UserVisitPK userVisitPK, RecoverPasswordForm form) {
        return CDI.current().select(RecoverPasswordCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult idle(UserVisitPK userVisitPK) {
        return CDI.current().select(IdleCommand.class).get().run(userVisitPK);
    }

    @Override
    public CommandResult logout(UserVisitPK userVisitPK) {
        return CDI.current().select(LogoutCommand.class).get().run(userVisitPK);
    }
    
}

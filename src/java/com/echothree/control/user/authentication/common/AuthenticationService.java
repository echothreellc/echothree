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

package com.echothree.control.user.authentication.common;

import com.echothree.control.user.authentication.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface AuthenticationService
        extends AuthenticationForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   User Visits and Sessions
    // -------------------------------------------------------------------------
    
    CommandResult getJobUserVisit(GetJobUserVisitForm form);
    
    UserVisitPK getDataLoaderUserVisit();
    
    CommandResult getUserVisit(GetUserVisitForm form);

    void invalidateUserSession(UserVisitPK userVisitPK);
    
    void invalidateUserVisit(UserVisitPK userVisitPK);
    
    CommandResult invalidateAbandonedUserVisits(UserVisitPK userVisitPK, InvalidateAbandonedUserVisitsForm form);
    
    CommandResult removeInactiveUserKeys(UserVisitPK userVisitPK, RemoveInactiveUserKeysForm form);
    
    CommandResult removeInvalidatedUserVisits(UserVisitPK userVisitPK);
    
    // -------------------------------------------------------------------------
    //   Logins
    // -------------------------------------------------------------------------
    
    CommandResult getCustomerLoginDefaults(UserVisitPK userVisitPK, GetCustomerLoginDefaultsForm form);
    
    CommandResult customerLogin(UserVisitPK userVisitPK, CustomerLoginForm form);
    
    CommandResult getEmployeeLoginDefaults(UserVisitPK userVisitPK, GetEmployeeLoginDefaultsForm form);
    
    CommandResult employeeLogin(UserVisitPK userVisitPK, EmployeeLoginForm form);
    
    CommandResult getVendorLoginDefaults(UserVisitPK userVisitPK, GetVendorLoginDefaultsForm form);
    
    CommandResult vendorLogin(UserVisitPK userVisitPK, VendorLoginForm form);
    
    CommandResult setPassword(UserVisitPK userVisitPK, SetPasswordForm form);
    
    CommandResult recoverPassword(UserVisitPK userVisitPK, RecoverPasswordForm form);
    
    CommandResult idle(UserVisitPK userVisitPK);
    
    CommandResult logout(UserVisitPK userVisitPK);
    
}

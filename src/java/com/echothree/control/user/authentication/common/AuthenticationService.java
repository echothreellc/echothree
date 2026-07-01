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
import com.echothree.control.user.authentication.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;

public interface AuthenticationService
        extends AuthenticationForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   User Visits and Sessions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> getJobUserVisit(GetJobUserVisitForm form);
    
    UserVisitPK getDataLoaderUserVisit();
    
    CommandResult<VoidResult> getUserVisit(GetUserVisitForm form);

    void invalidateUserSession(UserVisitPK userVisitPK);
    
    void invalidateUserVisit(UserVisitPK userVisitPK);
    
    CommandResult<VoidResult> invalidateAbandonedUserVisits(UserVisitPK userVisitPK, InvalidateAbandonedUserVisitsForm form);
    
    CommandResult<VoidResult> removeInactiveUserKeys(UserVisitPK userVisitPK, RemoveInactiveUserKeysForm form);
    
    CommandResult<VoidResult> removeInvalidatedUserVisits(UserVisitPK userVisitPK);
    
    // -------------------------------------------------------------------------
    //   Logins
    // -------------------------------------------------------------------------
    
    CommandResult<GetCustomerLoginDefaultsResult> getCustomerLoginDefaults(UserVisitPK userVisitPK, GetCustomerLoginDefaultsForm form);
    
    CommandResult<VoidResult> customerLogin(UserVisitPK userVisitPK, CustomerLoginForm form);
    
    CommandResult<GetEmployeeLoginDefaultsResult> getEmployeeLoginDefaults(UserVisitPK userVisitPK, GetEmployeeLoginDefaultsForm form);
    
    CommandResult<VoidResult> employeeLogin(UserVisitPK userVisitPK, EmployeeLoginForm form);
    
    CommandResult<GetVendorLoginDefaultsResult> getVendorLoginDefaults(UserVisitPK userVisitPK, GetVendorLoginDefaultsForm form);
    
    CommandResult<VoidResult> vendorLogin(UserVisitPK userVisitPK, VendorLoginForm form);
    
    CommandResult<VoidResult> setPassword(UserVisitPK userVisitPK, SetPasswordForm form);
    
    CommandResult<VoidResult> recoverPassword(UserVisitPK userVisitPK, RecoverPasswordForm form);
    
    CommandResult<VoidResult> idle(UserVisitPK userVisitPK);
    
    CommandResult<VoidResult> logout(UserVisitPK userVisitPK);
    
}

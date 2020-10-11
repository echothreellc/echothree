// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.control.user.user.server.command;

import com.echothree.control.user.authentication.server.command.BaseLoginCommand;
import com.echothree.control.user.user.common.form.ResetLockoutForm;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.vendor.server.VendorControl;
import com.echothree.model.data.customer.server.entity.Customer;
import com.echothree.model.data.employee.server.entity.PartyEmployee;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserLoginStatus;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ResetLockoutCommand
        extends BaseLoginCommand<ResetLockoutForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EmployeeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CustomerName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("VendorName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of ResetLockoutCommand */
    public ResetLockoutCommand(UserVisitPK userVisitPK, ResetLockoutForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        String partyName = form.getPartyName();
        String employeeName = form.getEmployeeName();
        String customerName = form.getCustomerName();
        String vendorName = form.getVendorName();
        int parameterCount = (partyName == null ? 0 : 1) + (employeeName == null ? 0 : 1) + (customerName == null ? 0 : 1) + (vendorName == null ? 0 : 1);
        
        if(parameterCount == 1) {
            UserControl userControl = getUserControl();
            Party party = null;
            
            if(employeeName != null) {
                var employeeControl = (EmployeeControl)Session.getModelController(EmployeeControl.class);
                PartyEmployee partyEmployee = employeeControl.getPartyEmployeeByName(employeeName);
                
                if(partyEmployee != null) {
                    party = partyEmployee.getParty();
                } else {
                    addExecutionError(ExecutionErrors.UnknownEmployeeName.name(), employeeName);
                }
            } else if(partyName != null) {
                var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                
                party = partyControl.getPartyByName(partyName);
                if(party == null) {
                    addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
                }
            } else if(customerName != null) {
                var customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
                Customer customer = customerControl.getCustomerByName(customerName);
                
                if(customer != null) {
                    party = customer.getParty();
                } else {
                    addExecutionError(ExecutionErrors.UnknownCustomerName.name(), customerName);
                }
            } else if(vendorName != null) {
                var vendorControl = (VendorControl)Session.getModelController(VendorControl.class);
                Vendor vendor = vendorControl.getVendorByName(vendorName);
                
                if(vendor != null) {
                    party = vendor.getParty();
                } else {
                    addExecutionError(ExecutionErrors.UnknownVendorName.name(), vendorName);
                }
            } else {
                party = getParty();
            }
            
            if(!hasExecutionErrors()) {
                PartyLogic.getInstance().checkPartyType(this, party, PartyTypes.EMPLOYEE.name(), PartyTypes.CUSTOMER.name(),
                        PartyTypes.VENDOR.name());

                if(!hasExecutionErrors()) {
                    UserLoginStatus userLoginStatus = userControl.getUserLoginStatusForUpdate(party);

                    if(userLoginStatus != null) {
                        clearLoginFailures(userLoginStatus);

                        // TODO: Create audit trail
                    } else {
                        addExecutionError(ExecutionErrors.UnknownUserLogin.name());
                    }
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return null;
    }
    
}

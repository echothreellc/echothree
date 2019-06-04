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

package com.echothree.control.user.authentication.server.command;

import com.echothree.control.user.authentication.common.form.SetPasswordForm;
import com.echothree.model.control.customer.server.CustomerControl;
import com.echothree.model.control.employee.server.EmployeeControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.party.server.logic.PasswordStringPolicyLogic;
import com.echothree.model.control.user.common.UserConstants;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.control.vendor.server.VendorControl;
import com.echothree.model.data.customer.server.entity.Customer;
import com.echothree.model.data.employee.server.entity.PartyEmployee;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyTypePasswordStringPolicy;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserLoginPassword;
import com.echothree.model.data.user.server.entity.UserLoginPasswordType;
import com.echothree.model.data.user.server.entity.UserLoginStatus;
import com.echothree.model.data.user.server.value.UserLoginPasswordStringValue;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetPasswordCommand
        extends BaseLoginCommand<SetPasswordForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EmployeeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CustomerName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("VendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("OldPassword", FieldType.STRING, false, 1L, 40L),
                new FieldDefinition("NewPassword1", FieldType.STRING, true, 1L, 40L),
                new FieldDefinition("NewPassword2", FieldType.STRING, true, 1L, 40L)
                ));
    }
    
    /** Creates a new instance of SetPasswordCommand */
    public SetPasswordCommand(UserVisitPK userVisitPK, SetPasswordForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        String partyName = form.getPartyName();
        String employeeName = form.getEmployeeName();
        String customerName = form.getCustomerName();
        String vendorName = form.getVendorName();
        int parameterCount = (partyName == null ? 0 : 1) + (employeeName == null ? 0 : 1) + (customerName == null ? 0 : 1) + (vendorName == null ? 0 : 1);
        
        if(parameterCount < 2) {
            UserControl userControl = getUserControl();
            Party self = getParty();
            Party party = null;
            
            if(partyName != null) {
                var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                
                party = partyControl.getPartyByName(partyName);
                if(party == null) {
                    addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
                }
            } else if(employeeName != null) {
                var employeeControl = (EmployeeControl)Session.getModelController(EmployeeControl.class);
                PartyEmployee partyEmployee = employeeControl.getPartyEmployeeByName(employeeName);
                
                if(partyEmployee != null) {
                    party = partyEmployee.getParty();
                } else {
                    addExecutionError(ExecutionErrors.UnknownEmployeeName.name(), employeeName);
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
                party = self;
            }
            
            if(!hasExecutionErrors()) {
                if(party != null) {
                    PartyLogic.getInstance().checkPartyType(this, party, PartyConstants.PartyType_EMPLOYEE, PartyConstants.PartyType_CUSTOMER,
                            PartyConstants.PartyType_VENDOR);

                    if(!hasExecutionErrors()) {
                        UserLoginPasswordType userLoginPasswordType = userControl.getUserLoginPasswordTypeByName(UserConstants.UserLoginPasswordType_STRING);
                        UserLoginPassword userLoginPassword = userControl.getUserLoginPassword(party, userLoginPasswordType);

                        if(userLoginPassword != null) {
                            String newPassword1 = form.getNewPassword1();
                            String newPassword2 = form.getNewPassword2();

                            if(newPassword1.equals(newPassword2)) {
                                UserLoginStatus userLoginStatus = userControl.getUserLoginStatusForUpdate(party);
                                String oldPassword = form.getOldPassword();

                                if(oldPassword != null) {
                                    if(!checkPasswords(userLoginStatus, oldPassword, party, false)) {
                                        addExecutionError(ExecutionErrors.IncorrectPassword.name());
                                    }
                                } else if(party.equals(self)) {
                                    addExecutionError(ExecutionErrors.MissingOldPassword.name());
                                }

                                if(!hasExecutionErrors()) {
                                    UserLoginPasswordStringValue userLoginPasswordStringValue = userControl.getUserLoginPasswordStringValueForUpdate(userLoginPassword);
                                    PartyTypePasswordStringPolicy partyTypePasswordStringPolicy = PasswordStringPolicyLogic.getInstance().checkStringPassword(session,
                                            getUserVisit(), this, party, userLoginPassword, userLoginPasswordStringValue, newPassword1);

                                    if(!hasExecutionErrors()) {
                                        boolean changingForSelf = self.equals(party);

                                        userLoginPasswordStringValue.setPassword(newPassword1);
                                        userLoginPasswordStringValue.setChangedTime(session.START_TIME_LONG);
                                        userLoginPasswordStringValue.setWasReset(!changingForSelf);

                                        userControl.updateUserLoginPasswordStringFromValue(userLoginPasswordStringValue, self.getPrimaryKey());

                                        userLoginStatus.setExpiredCount(0);
                                        userLoginStatus.setForceChange(changingForSelf ? Boolean.FALSE : partyTypePasswordStringPolicy == null? Boolean.FALSE
                                                : partyTypePasswordStringPolicy.getLastDetail().getForceChangeAfterReset());
                                    }
                                }
                            } else {
                                addExecutionError(ExecutionErrors.MismatchedPasswords.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownUserLoginPassword.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownParty.name());
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return null;
    }
    
}

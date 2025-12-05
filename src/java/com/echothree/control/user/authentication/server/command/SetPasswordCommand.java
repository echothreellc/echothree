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

import com.echothree.control.user.authentication.common.form.SetPasswordForm;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.party.server.logic.PasswordStringPolicyLogic;
import com.echothree.model.control.user.common.UserConstants;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
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
    public SetPasswordCommand() {
        super(null, FORM_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var partyName = form.getPartyName();
        var employeeName = form.getEmployeeName();
        var customerName = form.getCustomerName();
        var vendorName = form.getVendorName();
        var parameterCount = (partyName == null ? 0 : 1) + (employeeName == null ? 0 : 1) + (customerName == null ? 0 : 1) + (vendorName == null ? 0 : 1);
        
        if(parameterCount < 2) {
            var userControl = getUserControl();
            var self = getParty();
            Party party = null;
            
            if(partyName != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                
                party = partyControl.getPartyByName(partyName);
                if(party == null) {
                    addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
                }
            } else if(employeeName != null) {
                var employeeControl = Session.getModelController(EmployeeControl.class);
                var partyEmployee = employeeControl.getPartyEmployeeByName(employeeName);
                
                if(partyEmployee != null) {
                    party = partyEmployee.getParty();
                } else {
                    addExecutionError(ExecutionErrors.UnknownEmployeeName.name(), employeeName);
                }
            } else if(customerName != null) {
                var customerControl = Session.getModelController(CustomerControl.class);
                var customer = customerControl.getCustomerByName(customerName);
                
                if(customer != null) {
                    party = customer.getParty();
                } else {
                    addExecutionError(ExecutionErrors.UnknownCustomerName.name(), customerName);
                }
            } else if(vendorName != null) {
                var vendorControl = Session.getModelController(VendorControl.class);
                var vendor = vendorControl.getVendorByName(vendorName);
                
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
                    PartyLogic.getInstance().checkPartyType(this, party, PartyTypes.EMPLOYEE.name(), PartyTypes.CUSTOMER.name(),
                            PartyTypes.VENDOR.name());

                    if(!hasExecutionErrors()) {
                        var userLoginPasswordType = userControl.getUserLoginPasswordTypeByName(UserConstants.UserLoginPasswordType_STRING);
                        var userLoginPassword = userControl.getUserLoginPassword(party, userLoginPasswordType);

                        if(userLoginPassword != null) {
                            var newPassword1 = form.getNewPassword1();
                            var newPassword2 = form.getNewPassword2();

                            if(newPassword1.equals(newPassword2)) {
                                var userLoginStatus = userControl.getUserLoginStatusForUpdate(party);
                                var oldPassword = form.getOldPassword();

                                if(oldPassword != null) {
                                    if(!checkPasswords(userLoginStatus, oldPassword, party, false)) {
                                        addExecutionError(ExecutionErrors.IncorrectPassword.name());
                                    }
                                } else if(party.equals(self)) {
                                    addExecutionError(ExecutionErrors.MissingOldPassword.name());
                                }

                                if(!hasExecutionErrors()) {
                                    var userLoginPasswordStringValue = userControl.getUserLoginPasswordStringValueForUpdate(userLoginPassword);
                                    var partyTypePasswordStringPolicy = PasswordStringPolicyLogic.getInstance().checkStringPassword(session,
                                            getUserVisit(), this, party, userLoginPassword, userLoginPasswordStringValue, newPassword1);

                                    if(!hasExecutionErrors()) {
                                        var changingForSelf = self.equals(party);

                                        userLoginPasswordStringValue.setPassword(newPassword1);
                                        userLoginPasswordStringValue.setChangedTime(session.getStartTime());
                                        userLoginPasswordStringValue.setWasReset(!changingForSelf);

                                        userControl.updateUserLoginPasswordStringFromValue(userLoginPasswordStringValue, self.getPrimaryKey());

                                        userLoginStatus.setExpiredCount(0);
                                        userLoginStatus.setForceChange(changingForSelf ? false : partyTypePasswordStringPolicy == null? false
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

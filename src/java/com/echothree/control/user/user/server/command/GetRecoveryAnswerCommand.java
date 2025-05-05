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

package com.echothree.control.user.user.server.command;


import com.echothree.control.user.user.common.form.GetRecoveryAnswerForm;
import com.echothree.control.user.user.common.result.UserResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetRecoveryAnswerCommand
        extends BaseSimpleCommand<GetRecoveryAnswerForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EmployeeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CustomerName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("VendorName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetRecoveryAnswerCommand */
    public GetRecoveryAnswerCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var result = UserResultFactory.getGetRecoveryAnswerResult();
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
                if(self != null) {
                    party = self;
                } else {
                    addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                }
            }
            
            if(!hasExecutionErrors()) {
                var recoveryAnswer = userControl.getRecoveryAnswer(party);
                
                if(recoveryAnswer != null) {
                    result.setRecoveryAnswer(userControl.getRecoveryAnswerTransfer(getUserVisit(), recoveryAnswer));
                    
                    sendEvent(recoveryAnswer.getPrimaryKey(), EventTypes.READ, null, null, self.getPrimaryKey());
                } else {
                    addExecutionError(ExecutionErrors.UnknownRecoveryAnswer.name());
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}

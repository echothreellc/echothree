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

package com.echothree.control.user.user.server.command;


import com.echothree.control.user.user.common.form.GetRecoveryAnswerForm;
import com.echothree.control.user.user.common.result.UserResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.customer.server.logic.CustomerLogic;
import com.echothree.model.control.party.server.logic.EmployeeLogic;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.vendor.server.logic.VendorLogic;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.server.entity.RecoveryAnswer;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetRecoveryAnswerCommand
        extends BaseSingleEntityCommand<RecoveryAnswer, GetRecoveryAnswerForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EmployeeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CustomerName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("VendorName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    @Inject
    CustomerLogic customerLogic;

    @Inject
    EmployeeLogic employeeLogic;

    @Inject
    PartyLogic partyLogic;

    @Inject
    VendorLogic vendorLogic;
    
    /** Creates a new instance of GetRecoveryAnswerCommand */
    public GetRecoveryAnswerCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected RecoveryAnswer getEntity() {
        RecoveryAnswer recoveryAnswer = null;
        var partyName = form.getPartyName();
        var employeeName = form.getEmployeeName();
        var customerName = form.getCustomerName();
        var vendorName = form.getVendorName();
        var parameterCount = (partyName == null ? 0 : 1) + (employeeName == null ? 0 : 1) + (customerName == null ? 0 : 1) + (vendorName == null ? 0 : 1);
        
        if(parameterCount < 2) {
            var self = getParty();
            Party party = null;
            
            if(partyName != null) {
                party = partyLogic.getPartyByName(this, partyName);
            } else if(employeeName != null) {
                var partyEmployee = employeeLogic.getPartyEmployeeByName(this, employeeName, null);
                
                if(partyEmployee != null) {
                    party = partyEmployee.getParty();
                }
            } else if(customerName != null) {
                var customer = customerLogic.getCustomerByName(this, customerName, null, null);
                
                if(customer != null) {
                    party = customer.getParty();
                }
            } else if(vendorName != null) {
                var vendor = vendorLogic.getVendorByName(this, vendorName, null, null);
                
                if(vendor != null) {
                    party = vendor.getParty();
                }
            } else {
                if(self != null) {
                    party = self;
                } else {
                    addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                }
            }
            
            if(!hasExecutionErrors()) {
                recoveryAnswer = userControl.getRecoveryAnswer(party);
                
                if(recoveryAnswer != null) {
                    sendEvent(recoveryAnswer.getPrimaryKey(), EventTypes.READ, null, null, self.getPrimaryKey());
                } else {
                    addExecutionError(ExecutionErrors.UnknownRecoveryAnswer.name());
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return recoveryAnswer;
    }

    @Override
    protected BaseResult getResult(RecoveryAnswer recoveryAnswer) {
        var result = UserResultFactory.getGetRecoveryAnswerResult();

        if(recoveryAnswer != null) {
            result.setRecoveryAnswer(userControl.getRecoveryAnswerTransfer(getUserVisit(), recoveryAnswer));
        }
        
        return result;
    }
    
}

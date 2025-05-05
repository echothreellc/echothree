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

import com.echothree.control.user.user.common.form.GetRecoveryQuestionForm;
import com.echothree.control.user.user.common.result.UserResultFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.user.server.logic.RecoveryQuestionLogic;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.RecoveryQuestion;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetRecoveryQuestionCommand
        extends BaseSingleEntityCommand<RecoveryQuestion, GetRecoveryQuestionForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("RecoveryQuestionName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null),
                new FieldDefinition("Username", FieldType.STRING, false, 1L, 80L),
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EmployeeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CustomerName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("VendorName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetRecoveryQuestionCommand */
    public GetRecoveryQuestionCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected RecoveryQuestion getEntity() {
        RecoveryQuestion recoveryQuestion = null;
        var recoveryQuestionName = form.getRecoveryQuestionName();
        var username = form.getUsername();
        var partyName = form.getPartyName();
        var employeeName = form.getEmployeeName();
        var customerName = form.getCustomerName();
        var vendorName = form.getVendorName();
        var nameOrEntitySpecsCount = (recoveryQuestionName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);
        var parameterCount = nameOrEntitySpecsCount + (username == null ? 0 : 1) + (partyName == null ? 0 : 1)
                + (employeeName == null ? 0 : 1) + (customerName == null ? 0 : 1) + (vendorName == null ? 0 : 1);

        if(parameterCount < 2) {
            var userControl = getUserControl();
            var self = getParty();
            
            if(nameOrEntitySpecsCount == 1) {
                if(recoveryQuestionName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.RecoveryQuestion.name());

                    if(!hasExecutionErrors()) {
                        recoveryQuestion = userControl.getRecoveryQuestionByEntityInstance(entityInstance);
                    }
                } else {
                    recoveryQuestion = RecoveryQuestionLogic.getInstance().getRecoveryQuestionByName(this, recoveryQuestionName);
                }

                if(recoveryQuestion != null) {
                    sendEvent(recoveryQuestion.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
                }
            } else {
                Party party = null;
                
                if(username != null) {
                    var userLogin = userControl.getUserLoginByUsername(username);
                    
                    if(userLogin != null) {
                        party = userLogin.getParty();
                    } else {
                        addExecutionError(ExecutionErrors.UnknownUsername.name(), username);
                    }
                } else if(partyName != null) {
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
                        var recoveryAnswer = userControl.getRecoveryAnswer(party);

                        if(recoveryAnswer != null) {
                            recoveryQuestion = recoveryAnswer.getLastDetail().getRecoveryQuestion();
                        }
                    }
                    
                    if(recoveryQuestion == null) {
                        addExecutionError(ExecutionErrors.UnknownRecoveryAnswer.name());
                    }
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return recoveryQuestion;
    }
    
    @Override
    protected BaseResult getResult(RecoveryQuestion recoveryQuestion) {
        var userControl = getUserControl();
        var result = UserResultFactory.getGetRecoveryQuestionResult();

        if(recoveryQuestion != null) {
            var self = getParty();

            result.setRecoveryQuestion(userControl.getRecoveryQuestionTransfer(getUserVisit(), recoveryQuestion));
                
            if(self != null) {
                sendEvent(recoveryQuestion.getPrimaryKey(), EventTypes.READ, null, null, self.getPrimaryKey());
            }
        }

        return result;
    }
    
}

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

import com.echothree.control.user.user.common.form.GetRecoveryQuestionForm;
import com.echothree.control.user.user.common.result.GetRecoveryQuestionResult;
import com.echothree.control.user.user.common.result.UserResultFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.user.server.logic.RecoveryQuestionLogic;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.customer.server.entity.Customer;
import com.echothree.model.data.employee.server.entity.PartyEmployee;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.RecoveryAnswer;
import com.echothree.model.data.user.server.entity.RecoveryQuestion;
import com.echothree.model.data.user.server.entity.UserLogin;
import com.echothree.model.data.vendor.server.entity.Vendor;
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
                new FieldDefinition("Key", FieldType.KEY, false, null, null),
                new FieldDefinition("Guid", FieldType.GUID, false, null, null),
                new FieldDefinition("Ulid", FieldType.ULID, false, null, null),
                new FieldDefinition("Username", FieldType.STRING, false, 1L, 80L),
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EmployeeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CustomerName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("VendorName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetRecoveryQuestionCommand */
    public GetRecoveryQuestionCommand(UserVisitPK userVisitPK, GetRecoveryQuestionForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected RecoveryQuestion getEntity() {
        RecoveryQuestion recoveryQuestion = null;
        String recoveryQuestionName = form.getRecoveryQuestionName();
        String username = form.getUsername();
        String partyName = form.getPartyName();
        String employeeName = form.getEmployeeName();
        String customerName = form.getCustomerName();
        String vendorName = form.getVendorName();
        int nameOrEntitySpecsCount = (recoveryQuestionName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);
        int parameterCount = nameOrEntitySpecsCount + (username == null ? 0 : 1) + (partyName == null ? 0 : 1)
                + (employeeName == null ? 0 : 1) + (customerName == null ? 0 : 1) + (vendorName == null ? 0 : 1);

        if(parameterCount < 2) {
            UserControl userControl = getUserControl();
            Party self = getParty();
            
            if(nameOrEntitySpecsCount == 1) {
                if(recoveryQuestionName == null) {
                    EntityInstance entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form,
                            ComponentVendors.ECHOTHREE.name(), EntityTypes.RecoveryQuestion.name());

                    if(!hasExecutionErrors()) {
                        recoveryQuestion = userControl.getRecoveryQuestionByEntityInstance(entityInstance);
                    }
                } else {
                    recoveryQuestion = RecoveryQuestionLogic.getInstance().getRecoveryQuestionByName(this, recoveryQuestionName);
                }

                if(recoveryQuestion != null) {
                    sendEventUsingNames(recoveryQuestion.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
                }
            } else {
                Party party = null;
                
                if(username != null) {
                    UserLogin userLogin = userControl.getUserLoginByUsername(username);
                    
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
                    PartyEmployee partyEmployee = employeeControl.getPartyEmployeeByName(employeeName);
                    
                    if(partyEmployee != null) {
                        party = partyEmployee.getParty();
                    } else {
                        addExecutionError(ExecutionErrors.UnknownEmployeeName.name(), employeeName);
                    }
                } else if(customerName != null) {
                    var customerControl = Session.getModelController(CustomerControl.class);
                    Customer customer = customerControl.getCustomerByName(customerName);
                    
                    if(customer != null) {
                        party = customer.getParty();
                    } else {
                        addExecutionError(ExecutionErrors.UnknownCustomerName.name(), customerName);
                    }
                } else if(vendorName != null) {
                    var vendorControl = Session.getModelController(VendorControl.class);
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
                        RecoveryAnswer recoveryAnswer = userControl.getRecoveryAnswer(party);

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
    protected BaseResult getTransfer(RecoveryQuestion recoveryQuestion) {
        UserControl userControl = getUserControl();
        GetRecoveryQuestionResult result = UserResultFactory.getGetRecoveryQuestionResult();

        if(recoveryQuestion != null) {
            Party self = getParty();

            result.setRecoveryQuestion(userControl.getRecoveryQuestionTransfer(getUserVisit(), recoveryQuestion));
                
            if(self != null) {
                sendEventUsingNames(recoveryQuestion.getPrimaryKey(), EventTypes.READ.name(), null, null, self.getPrimaryKey());
            }
        }

        return result;
    }
    
}

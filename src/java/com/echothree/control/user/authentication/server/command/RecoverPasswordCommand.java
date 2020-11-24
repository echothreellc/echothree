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

package com.echothree.control.user.authentication.server.command;

import com.echothree.control.user.authentication.common.form.RecoverPasswordForm;
import com.echothree.control.user.party.common.result.GetPartyResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.model.control.party.server.logic.PartyChainLogic;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.user.common.UserConstants;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.user.server.logic.UserLoginLogic;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.RecoveryAnswer;
import com.echothree.model.data.user.server.entity.UserLogin;
import com.echothree.model.data.user.server.entity.UserLoginPassword;
import com.echothree.model.data.user.server.entity.UserLoginPasswordType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.PasswordGeneratorUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RecoverPasswordCommand
        extends BaseSimpleCommand<RecoverPasswordForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Username", FieldType.STRING, false, 1L, 80L),
                new FieldDefinition("Answer", FieldType.STRING, false, 1L, 40L)
                ));
    }

    /** Creates a new instance of RecoverPasswordCommand */
    public RecoverPasswordCommand(UserVisitPK userVisitPK, RecoverPasswordForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected BaseResult execute() {
        GetPartyResult result = PartyResultFactory.getGetPartyResult();
        String partyName = form.getPartyName();
        String username = form.getUsername();
        int parameterCount = (partyName == null ? 0 : 1) + (username == null ? 0 : 1);

        if(parameterCount == 1) {
            Party party = null;

            if(partyName != null) {
                party = PartyLogic.getInstance().getPartyByName(this, partyName);
            }

            if(username != null) {
                UserLogin userLogin = UserLoginLogic.getInstance().getUserLoginByUsername(this, username);

                if(!hasExecutionErrors()) {
                    party = userLogin.getParty();
                }
            }

            if(!hasExecutionErrors()) {
                var userControl = Session.getModelController(UserControl.class);
                RecoveryAnswer recoveryAnswer = userControl.getRecoveryAnswer(party);
                String answer = form.getAnswer();
                
                if(recoveryAnswer == null) {
                    if(answer != null) {
                        addExecutionError(ExecutionErrors.MissingRequiredAnswer.name());
                    }
                } else {
                    if(answer == null) {
                        addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                    } else if(!answer.equals(recoveryAnswer.getLastDetail().getAnswer())) {
                        addExecutionError(ExecutionErrors.IncorrectAnswer.name());
                    }
                }
                
                if(!hasExecutionErrors()) {
                    UserLoginPasswordType userLoginPasswordType = userControl.getUserLoginPasswordTypeByName(UserConstants.UserLoginPasswordType_RECOVERED_STRING);
                    String password = PasswordGeneratorUtils.getInstance().getPassword(party.getLastDetail().getPartyType());
                    PartyPK createdBy = getPartyPK();

                    // If it already exists, delete the previous attempt at recovery.
                    if(userControl.countUserLoginPasswords(party, userLoginPasswordType) != 0) {
                        userControl.deleteUserLoginPassword(userControl.getUserLoginPasswordForUpdate(party, userLoginPasswordType), createdBy);
                    }
                    
                    UserLoginPassword userLoginPassword = userControl.createUserLoginPassword(party, userLoginPasswordType, createdBy);
                    userControl.createUserLoginPasswordString(userLoginPassword, password, session.START_TIME_LONG, Boolean.FALSE, createdBy);
                    
                    // ExecutionErrorAccumulator is passed in as null so that an Exception will be thrown if there is an error.
                    PartyChainLogic.getInstance().createPartyPasswordRecoveryChainInstance(null, party, createdBy);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }

}

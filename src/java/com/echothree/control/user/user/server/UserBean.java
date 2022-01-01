// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.control.user.user.server;

import com.echothree.control.user.user.common.UserRemote;
import com.echothree.control.user.user.common.form.*;
import com.echothree.control.user.user.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class UserBean
        extends UserFormsImpl
        implements UserRemote, UserLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "UserBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Recovery Questions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createRecoveryQuestion(UserVisitPK userVisitPK, CreateRecoveryQuestionForm form) {
        return new CreateRecoveryQuestionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getRecoveryQuestions(UserVisitPK userVisitPK, GetRecoveryQuestionsForm form) {
        return new GetRecoveryQuestionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getRecoveryQuestion(UserVisitPK userVisitPK, GetRecoveryQuestionForm form) {
        return new GetRecoveryQuestionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getRecoveryQuestionChoices(UserVisitPK userVisitPK, GetRecoveryQuestionChoicesForm form) {
        return new GetRecoveryQuestionChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultRecoveryQuestion(UserVisitPK userVisitPK, SetDefaultRecoveryQuestionForm form) {
        return new SetDefaultRecoveryQuestionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editRecoveryQuestion(UserVisitPK userVisitPK, EditRecoveryQuestionForm form) {
        return new EditRecoveryQuestionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteRecoveryQuestion(UserVisitPK userVisitPK, DeleteRecoveryQuestionForm form) {
        return new DeleteRecoveryQuestionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Recovery Question Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createRecoveryQuestionDescription(UserVisitPK userVisitPK, CreateRecoveryQuestionDescriptionForm form) {
        return new CreateRecoveryQuestionDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getRecoveryQuestionDescriptions(UserVisitPK userVisitPK, GetRecoveryQuestionDescriptionsForm form) {
        return new GetRecoveryQuestionDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editRecoveryQuestionDescription(UserVisitPK userVisitPK, EditRecoveryQuestionDescriptionForm form) {
        return new EditRecoveryQuestionDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteRecoveryQuestionDescription(UserVisitPK userVisitPK, DeleteRecoveryQuestionDescriptionForm form) {
        return new DeleteRecoveryQuestionDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Recovery Answers
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult getRecoveryAnswer(UserVisitPK userVisitPK, GetRecoveryAnswerForm form) {
        return new GetRecoveryAnswerCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editRecoveryAnswer(UserVisitPK userVisitPK, EditRecoveryAnswerForm form) {
        return new EditRecoveryAnswerCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   User Login Password Encoder Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUserLoginPasswordEncoderType(UserVisitPK userVisitPK, CreateUserLoginPasswordEncoderTypeForm form) {
        return new CreateUserLoginPasswordEncoderTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   User Login Password Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUserLoginPasswordEncoderTypeDescription(UserVisitPK userVisitPK, CreateUserLoginPasswordEncoderTypeDescriptionForm form) {
        return new CreateUserLoginPasswordEncoderTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   User Login Password Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUserLoginPasswordType(UserVisitPK userVisitPK, CreateUserLoginPasswordTypeForm form) {
        return new CreateUserLoginPasswordTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   User Login Password Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUserLoginPasswordTypeDescription(UserVisitPK userVisitPK, CreateUserLoginPasswordTypeDescriptionForm form) {
        return new CreateUserLoginPasswordTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   User Logins
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUserLogin(UserVisitPK userVisitPK, CreateUserLoginForm form) {
        return new CreateUserLoginCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUserLogin(UserVisitPK userVisitPK, GetUserLoginForm form) {
        return new GetUserLoginCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editUserLogin(UserVisitPK userVisitPK, EditUserLoginForm form) {
        return new EditUserLoginCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteUserLogin(UserVisitPK userVisitPK, DeleteUserLoginForm form) {
        return new DeleteUserLoginCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   User Login Statuses
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult resetLockout(UserVisitPK userVisitPK, ResetLockoutForm form) {
        return new ResetLockoutCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   User Visit Groups
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult getUserVisitGroup(UserVisitPK userVisitPK, GetUserVisitGroupForm form) {
        return new GetUserVisitGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUserVisitGroups(UserVisitPK userVisitPK, GetUserVisitGroupsForm form) {
        return new GetUserVisitGroupsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUserVisitGroupStatusChoices(UserVisitPK userVisitPK, GetUserVisitGroupStatusChoicesForm form) {
        return new GetUserVisitGroupStatusChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setUserVisitGroupStatus(UserVisitPK userVisitPK, SetUserVisitGroupStatusForm form) {
        return new SetUserVisitGroupStatusCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   User Visits
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult setUserVisitPreferredLanguage(UserVisitPK userVisitPK, SetUserVisitPreferredLanguageForm form) {
        return new SetUserVisitPreferredLanguageCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setUserVisitPreferredCurrency(UserVisitPK userVisitPK, SetUserVisitPreferredCurrencyForm form) {
        return new SetUserVisitPreferredCurrencyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setUserVisitPreferredTimeZone(UserVisitPK userVisitPK, SetUserVisitPreferredTimeZoneForm form) {
        return new SetUserVisitPreferredTimeZoneCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setUserVisitPreferredDateTimeFormat(UserVisitPK userVisitPK, SetUserVisitPreferredDateTimeFormatForm form) {
        return new SetUserVisitPreferredDateTimeFormatCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   User Sessions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult getUserSession(UserVisitPK userVisitPK, GetUserSessionForm form) {
        return new GetUserSessionCommand(userVisitPK, form).run();
    }
    
}

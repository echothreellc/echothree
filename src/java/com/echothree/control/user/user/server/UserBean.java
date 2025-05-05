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
        return new CreateRecoveryQuestionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getRecoveryQuestions(UserVisitPK userVisitPK, GetRecoveryQuestionsForm form) {
        return new GetRecoveryQuestionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getRecoveryQuestion(UserVisitPK userVisitPK, GetRecoveryQuestionForm form) {
        return new GetRecoveryQuestionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getRecoveryQuestionChoices(UserVisitPK userVisitPK, GetRecoveryQuestionChoicesForm form) {
        return new GetRecoveryQuestionChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultRecoveryQuestion(UserVisitPK userVisitPK, SetDefaultRecoveryQuestionForm form) {
        return new SetDefaultRecoveryQuestionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editRecoveryQuestion(UserVisitPK userVisitPK, EditRecoveryQuestionForm form) {
        return new EditRecoveryQuestionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteRecoveryQuestion(UserVisitPK userVisitPK, DeleteRecoveryQuestionForm form) {
        return new DeleteRecoveryQuestionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Recovery Question Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createRecoveryQuestionDescription(UserVisitPK userVisitPK, CreateRecoveryQuestionDescriptionForm form) {
        return new CreateRecoveryQuestionDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getRecoveryQuestionDescriptions(UserVisitPK userVisitPK, GetRecoveryQuestionDescriptionsForm form) {
        return new GetRecoveryQuestionDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editRecoveryQuestionDescription(UserVisitPK userVisitPK, EditRecoveryQuestionDescriptionForm form) {
        return new EditRecoveryQuestionDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteRecoveryQuestionDescription(UserVisitPK userVisitPK, DeleteRecoveryQuestionDescriptionForm form) {
        return new DeleteRecoveryQuestionDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Recovery Answers
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult getRecoveryAnswer(UserVisitPK userVisitPK, GetRecoveryAnswerForm form) {
        return new GetRecoveryAnswerCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editRecoveryAnswer(UserVisitPK userVisitPK, EditRecoveryAnswerForm form) {
        return new EditRecoveryAnswerCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   User Login Password Encoder Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUserLoginPasswordEncoderType(UserVisitPK userVisitPK, CreateUserLoginPasswordEncoderTypeForm form) {
        return new CreateUserLoginPasswordEncoderTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   User Login Password Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUserLoginPasswordEncoderTypeDescription(UserVisitPK userVisitPK, CreateUserLoginPasswordEncoderTypeDescriptionForm form) {
        return new CreateUserLoginPasswordEncoderTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   User Login Password Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUserLoginPasswordType(UserVisitPK userVisitPK, CreateUserLoginPasswordTypeForm form) {
        return new CreateUserLoginPasswordTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   User Login Password Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUserLoginPasswordTypeDescription(UserVisitPK userVisitPK, CreateUserLoginPasswordTypeDescriptionForm form) {
        return new CreateUserLoginPasswordTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   User Logins
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUserLogin(UserVisitPK userVisitPK, CreateUserLoginForm form) {
        return new CreateUserLoginCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUserLogin(UserVisitPK userVisitPK, GetUserLoginForm form) {
        return new GetUserLoginCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editUserLogin(UserVisitPK userVisitPK, EditUserLoginForm form) {
        return new EditUserLoginCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUserLogin(UserVisitPK userVisitPK, DeleteUserLoginForm form) {
        return new DeleteUserLoginCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   User Login Statuses
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult resetLockout(UserVisitPK userVisitPK, ResetLockoutForm form) {
        return new ResetLockoutCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   User Visit Groups
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult getUserVisitGroup(UserVisitPK userVisitPK, GetUserVisitGroupForm form) {
        return new GetUserVisitGroupCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUserVisitGroups(UserVisitPK userVisitPK, GetUserVisitGroupsForm form) {
        return new GetUserVisitGroupsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUserVisitGroupStatusChoices(UserVisitPK userVisitPK, GetUserVisitGroupStatusChoicesForm form) {
        return new GetUserVisitGroupStatusChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setUserVisitGroupStatus(UserVisitPK userVisitPK, SetUserVisitGroupStatusForm form) {
        return new SetUserVisitGroupStatusCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   User Visits
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult setUserVisitPreferredLanguage(UserVisitPK userVisitPK, SetUserVisitPreferredLanguageForm form) {
        return new SetUserVisitPreferredLanguageCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setUserVisitPreferredCurrency(UserVisitPK userVisitPK, SetUserVisitPreferredCurrencyForm form) {
        return new SetUserVisitPreferredCurrencyCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setUserVisitPreferredTimeZone(UserVisitPK userVisitPK, SetUserVisitPreferredTimeZoneForm form) {
        return new SetUserVisitPreferredTimeZoneCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setUserVisitPreferredDateTimeFormat(UserVisitPK userVisitPK, SetUserVisitPreferredDateTimeFormatForm form) {
        return new SetUserVisitPreferredDateTimeFormatCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   User Sessions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult getUserSession(UserVisitPK userVisitPK, GetUserSessionForm form) {
        return new GetUserSessionCommand().run(userVisitPK, form);
    }
    
}

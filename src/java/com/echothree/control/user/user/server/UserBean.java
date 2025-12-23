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

package com.echothree.control.user.user.server;

import com.echothree.control.user.user.common.UserRemote;
import com.echothree.control.user.user.common.form.*;
import com.echothree.control.user.user.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateRecoveryQuestionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getRecoveryQuestions(UserVisitPK userVisitPK, GetRecoveryQuestionsForm form) {
        return CDI.current().select(GetRecoveryQuestionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getRecoveryQuestion(UserVisitPK userVisitPK, GetRecoveryQuestionForm form) {
        return CDI.current().select(GetRecoveryQuestionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getRecoveryQuestionChoices(UserVisitPK userVisitPK, GetRecoveryQuestionChoicesForm form) {
        return CDI.current().select(GetRecoveryQuestionChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultRecoveryQuestion(UserVisitPK userVisitPK, SetDefaultRecoveryQuestionForm form) {
        return CDI.current().select(SetDefaultRecoveryQuestionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editRecoveryQuestion(UserVisitPK userVisitPK, EditRecoveryQuestionForm form) {
        return CDI.current().select(EditRecoveryQuestionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteRecoveryQuestion(UserVisitPK userVisitPK, DeleteRecoveryQuestionForm form) {
        return CDI.current().select(DeleteRecoveryQuestionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Recovery Question Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createRecoveryQuestionDescription(UserVisitPK userVisitPK, CreateRecoveryQuestionDescriptionForm form) {
        return CDI.current().select(CreateRecoveryQuestionDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getRecoveryQuestionDescriptions(UserVisitPK userVisitPK, GetRecoveryQuestionDescriptionsForm form) {
        return CDI.current().select(GetRecoveryQuestionDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editRecoveryQuestionDescription(UserVisitPK userVisitPK, EditRecoveryQuestionDescriptionForm form) {
        return CDI.current().select(EditRecoveryQuestionDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteRecoveryQuestionDescription(UserVisitPK userVisitPK, DeleteRecoveryQuestionDescriptionForm form) {
        return CDI.current().select(DeleteRecoveryQuestionDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Recovery Answers
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult getRecoveryAnswer(UserVisitPK userVisitPK, GetRecoveryAnswerForm form) {
        return CDI.current().select(GetRecoveryAnswerCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editRecoveryAnswer(UserVisitPK userVisitPK, EditRecoveryAnswerForm form) {
        return CDI.current().select(EditRecoveryAnswerCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   User Login Password Encoder Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUserLoginPasswordEncoderType(UserVisitPK userVisitPK, CreateUserLoginPasswordEncoderTypeForm form) {
        return CDI.current().select(CreateUserLoginPasswordEncoderTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   User Login Password Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUserLoginPasswordEncoderTypeDescription(UserVisitPK userVisitPK, CreateUserLoginPasswordEncoderTypeDescriptionForm form) {
        return CDI.current().select(CreateUserLoginPasswordEncoderTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   User Login Password Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUserLoginPasswordType(UserVisitPK userVisitPK, CreateUserLoginPasswordTypeForm form) {
        return CDI.current().select(CreateUserLoginPasswordTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   User Login Password Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUserLoginPasswordTypeDescription(UserVisitPK userVisitPK, CreateUserLoginPasswordTypeDescriptionForm form) {
        return CDI.current().select(CreateUserLoginPasswordTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   User Logins
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUserLogin(UserVisitPK userVisitPK, CreateUserLoginForm form) {
        return CDI.current().select(CreateUserLoginCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUserLogin(UserVisitPK userVisitPK, GetUserLoginForm form) {
        return CDI.current().select(GetUserLoginCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editUserLogin(UserVisitPK userVisitPK, EditUserLoginForm form) {
        return CDI.current().select(EditUserLoginCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUserLogin(UserVisitPK userVisitPK, DeleteUserLoginForm form) {
        return CDI.current().select(DeleteUserLoginCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   User Login Statuses
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult resetLockout(UserVisitPK userVisitPK, ResetLockoutForm form) {
        return CDI.current().select(ResetLockoutCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   User Visit Groups
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult getUserVisitGroup(UserVisitPK userVisitPK, GetUserVisitGroupForm form) {
        return CDI.current().select(GetUserVisitGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUserVisitGroups(UserVisitPK userVisitPK, GetUserVisitGroupsForm form) {
        return CDI.current().select(GetUserVisitGroupsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUserVisitGroupStatusChoices(UserVisitPK userVisitPK, GetUserVisitGroupStatusChoicesForm form) {
        return CDI.current().select(GetUserVisitGroupStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setUserVisitGroupStatus(UserVisitPK userVisitPK, SetUserVisitGroupStatusForm form) {
        return CDI.current().select(SetUserVisitGroupStatusCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   User Visits
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult setUserVisitPreferredLanguage(UserVisitPK userVisitPK, SetUserVisitPreferredLanguageForm form) {
        return CDI.current().select(SetUserVisitPreferredLanguageCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setUserVisitPreferredCurrency(UserVisitPK userVisitPK, SetUserVisitPreferredCurrencyForm form) {
        return CDI.current().select(SetUserVisitPreferredCurrencyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setUserVisitPreferredTimeZone(UserVisitPK userVisitPK, SetUserVisitPreferredTimeZoneForm form) {
        return CDI.current().select(SetUserVisitPreferredTimeZoneCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setUserVisitPreferredDateTimeFormat(UserVisitPK userVisitPK, SetUserVisitPreferredDateTimeFormatForm form) {
        return CDI.current().select(SetUserVisitPreferredDateTimeFormatCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   User Sessions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult getUserSession(UserVisitPK userVisitPK, GetUserSessionForm form) {
        return CDI.current().select(GetUserSessionCommand.class).get().run(userVisitPK, form);
    }
    
}

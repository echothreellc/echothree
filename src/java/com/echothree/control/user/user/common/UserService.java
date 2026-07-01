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

package com.echothree.control.user.user.common;

import com.echothree.control.user.user.common.form.*;
import com.echothree.control.user.user.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;

public interface UserService
        extends UserForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Recovery Questions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createRecoveryQuestion(UserVisitPK userVisitPK, CreateRecoveryQuestionForm form);
    
    CommandResult<GetRecoveryQuestionsResult> getRecoveryQuestions(UserVisitPK userVisitPK, GetRecoveryQuestionsForm form);
    
    CommandResult<GetRecoveryQuestionResult> getRecoveryQuestion(UserVisitPK userVisitPK, GetRecoveryQuestionForm form);
    
    CommandResult<GetRecoveryQuestionChoicesResult> getRecoveryQuestionChoices(UserVisitPK userVisitPK, GetRecoveryQuestionChoicesForm form);
    
    CommandResult<VoidResult> setDefaultRecoveryQuestion(UserVisitPK userVisitPK, SetDefaultRecoveryQuestionForm form);
    
    CommandResult<EditRecoveryQuestionResult> editRecoveryQuestion(UserVisitPK userVisitPK, EditRecoveryQuestionForm form);
    
    CommandResult<VoidResult> deleteRecoveryQuestion(UserVisitPK userVisitPK, DeleteRecoveryQuestionForm form);
    
    // -------------------------------------------------------------------------
    //   Recovery Question Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createRecoveryQuestionDescription(UserVisitPK userVisitPK, CreateRecoveryQuestionDescriptionForm form);
    
    CommandResult<GetRecoveryQuestionDescriptionsResult> getRecoveryQuestionDescriptions(UserVisitPK userVisitPK, GetRecoveryQuestionDescriptionsForm form);
    
    CommandResult<EditRecoveryQuestionDescriptionResult> editRecoveryQuestionDescription(UserVisitPK userVisitPK, EditRecoveryQuestionDescriptionForm form);
    
    CommandResult<VoidResult> deleteRecoveryQuestionDescription(UserVisitPK userVisitPK, DeleteRecoveryQuestionDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Recovery Answers
    // -------------------------------------------------------------------------
    
    CommandResult<GetRecoveryAnswerResult> getRecoveryAnswer(UserVisitPK userVisitPK, GetRecoveryAnswerForm form);
    
    CommandResult<EditRecoveryAnswerResult> editRecoveryAnswer(UserVisitPK userVisitPK, EditRecoveryAnswerForm form);
    
    // -------------------------------------------------------------------------
    //   User Login Password Encoder Types
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createUserLoginPasswordEncoderType(UserVisitPK userVisitPK, CreateUserLoginPasswordEncoderTypeForm form);
    
    // -------------------------------------------------------------------------
    //   User Login Password Encoder Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createUserLoginPasswordEncoderTypeDescription(UserVisitPK userVisitPK, CreateUserLoginPasswordEncoderTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   User Login Password Types
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createUserLoginPasswordType(UserVisitPK userVisitPK, CreateUserLoginPasswordTypeForm form);
    
    // -------------------------------------------------------------------------
    //   User Login Password Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createUserLoginPasswordTypeDescription(UserVisitPK userVisitPK, CreateUserLoginPasswordTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   User Logins
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createUserLogin(UserVisitPK userVisitPK, CreateUserLoginForm form);
    
    CommandResult<GetUserLoginResult> getUserLogin(UserVisitPK userVisitPK, GetUserLoginForm form);
    
    CommandResult<EditUserLoginResult> editUserLogin(UserVisitPK userVisitPK, EditUserLoginForm form);
    
    CommandResult<VoidResult> deleteUserLogin(UserVisitPK userVisitPK, DeleteUserLoginForm form);
    
    // -------------------------------------------------------------------------
    //   User Login Statuses
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> resetLockout(UserVisitPK userVisitPK, ResetLockoutForm form);
    
    // --------------------------------------------------------------------------------
    //   User Visit Groups
    // --------------------------------------------------------------------------------
    
    CommandResult<GetUserVisitGroupResult> getUserVisitGroup(UserVisitPK userVisitPK, GetUserVisitGroupForm form);
    
    CommandResult<GetUserVisitGroupsResult> getUserVisitGroups(UserVisitPK userVisitPK, GetUserVisitGroupsForm form);
    
    CommandResult<GetUserVisitGroupStatusChoicesResult> getUserVisitGroupStatusChoices(UserVisitPK userVisitPK, GetUserVisitGroupStatusChoicesForm form);
    
    CommandResult<VoidResult> setUserVisitGroupStatus(UserVisitPK userVisitPK, SetUserVisitGroupStatusForm form);
    
    // -------------------------------------------------------------------------
    //   User Visits
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> setUserVisitPreferredLanguage(UserVisitPK userVisitPK, SetUserVisitPreferredLanguageForm form);

    CommandResult<VoidResult> setUserVisitPreferredCurrency(UserVisitPK userVisitPK, SetUserVisitPreferredCurrencyForm form);

    CommandResult<VoidResult> setUserVisitPreferredTimeZone(UserVisitPK userVisitPK, SetUserVisitPreferredTimeZoneForm form);

    CommandResult<VoidResult> setUserVisitPreferredDateTimeFormat(UserVisitPK userVisitPK, SetUserVisitPreferredDateTimeFormatForm form);

    // -------------------------------------------------------------------------
    //   User Sessions
    // -------------------------------------------------------------------------

    CommandResult<GetUserSessionResult> getUserSession(UserVisitPK userVisitPK, GetUserSessionForm form);
    
}

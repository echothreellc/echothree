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

package com.echothree.control.user.training.common;

import com.echothree.control.user.training.common.form.*;
import com.echothree.control.user.training.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface TrainingService
        extends TrainingForms {
    
    // -------------------------------------------------------------------------
    //   Training Classes
    // -------------------------------------------------------------------------
    
    CommandResult<?> createTrainingClass(UserVisitPK userVisitPK, CreateTrainingClassForm form);
    
    CommandResult<GetTrainingClassesResult> getTrainingClasses(UserVisitPK userVisitPK, GetTrainingClassesForm form);
    
    CommandResult<GetTrainingClassResult> getTrainingClass(UserVisitPK userVisitPK, GetTrainingClassForm form);
    
    CommandResult<GetTrainingClassChoicesResult> getTrainingClassChoices(UserVisitPK userVisitPK, GetTrainingClassChoicesForm form);
    
    CommandResult<?> setDefaultTrainingClass(UserVisitPK userVisitPK, SetDefaultTrainingClassForm form);
    
    CommandResult<EditTrainingClassResult> editTrainingClass(UserVisitPK userVisitPK, EditTrainingClassForm form);
    
    CommandResult<?> deleteTrainingClass(UserVisitPK userVisitPK, DeleteTrainingClassForm form);
    
    // -------------------------------------------------------------------------
    //   Training Class Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createTrainingClassTranslation(UserVisitPK userVisitPK, CreateTrainingClassTranslationForm form);
    
    CommandResult<GetTrainingClassTranslationResult> getTrainingClassTranslation(UserVisitPK userVisitPK, GetTrainingClassTranslationForm form);
    
    CommandResult<GetTrainingClassTranslationsResult> getTrainingClassTranslations(UserVisitPK userVisitPK, GetTrainingClassTranslationsForm form);
    
    CommandResult<EditTrainingClassTranslationResult> editTrainingClassTranslation(UserVisitPK userVisitPK, EditTrainingClassTranslationForm form);
    
    CommandResult<?> deleteTrainingClassTranslation(UserVisitPK userVisitPK, DeleteTrainingClassTranslationForm form);
    
    // -------------------------------------------------------------------------
    //   Training Class Sections
    // -------------------------------------------------------------------------
    
    CommandResult<?> createTrainingClassSection(UserVisitPK userVisitPK, CreateTrainingClassSectionForm form);
    
    CommandResult<GetTrainingClassSectionsResult> getTrainingClassSections(UserVisitPK userVisitPK, GetTrainingClassSectionsForm form);
    
    CommandResult<GetTrainingClassSectionResult> getTrainingClassSection(UserVisitPK userVisitPK, GetTrainingClassSectionForm form);
    
    CommandResult<EditTrainingClassSectionResult> editTrainingClassSection(UserVisitPK userVisitPK, EditTrainingClassSectionForm form);
    
    CommandResult<?> deleteTrainingClassSection(UserVisitPK userVisitPK, DeleteTrainingClassSectionForm form);
    
    // -------------------------------------------------------------------------
    //   Training Class Section Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createTrainingClassSectionTranslation(UserVisitPK userVisitPK, CreateTrainingClassSectionTranslationForm form);
    
    CommandResult<GetTrainingClassSectionTranslationResult> getTrainingClassSectionTranslation(UserVisitPK userVisitPK, GetTrainingClassSectionTranslationForm form);
    
    CommandResult<GetTrainingClassSectionTranslationsResult> getTrainingClassSectionTranslations(UserVisitPK userVisitPK, GetTrainingClassSectionTranslationsForm form);
    
    CommandResult<EditTrainingClassSectionTranslationResult> editTrainingClassSectionTranslation(UserVisitPK userVisitPK, EditTrainingClassSectionTranslationForm form);
    
    CommandResult<?> deleteTrainingClassSectionTranslation(UserVisitPK userVisitPK, DeleteTrainingClassSectionTranslationForm form);
    
    // -------------------------------------------------------------------------
    //   Training Class Pages
    // -------------------------------------------------------------------------
    
    CommandResult<?> createTrainingClassPage(UserVisitPK userVisitPK, CreateTrainingClassPageForm form);
    
    CommandResult<GetTrainingClassPagesResult> getTrainingClassPages(UserVisitPK userVisitPK, GetTrainingClassPagesForm form);
    
    CommandResult<GetTrainingClassPageResult> getTrainingClassPage(UserVisitPK userVisitPK, GetTrainingClassPageForm form);
    
    CommandResult<EditTrainingClassPageResult> editTrainingClassPage(UserVisitPK userVisitPK, EditTrainingClassPageForm form);
    
    CommandResult<?> deleteTrainingClassPage(UserVisitPK userVisitPK, DeleteTrainingClassPageForm form);
    
    // -------------------------------------------------------------------------
    //   Training Class Page Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createTrainingClassPageTranslation(UserVisitPK userVisitPK, CreateTrainingClassPageTranslationForm form);
    
    CommandResult<GetTrainingClassPageTranslationResult> getTrainingClassPageTranslation(UserVisitPK userVisitPK, GetTrainingClassPageTranslationForm form);
    
    CommandResult<GetTrainingClassPageTranslationsResult> getTrainingClassPageTranslations(UserVisitPK userVisitPK, GetTrainingClassPageTranslationsForm form);
    
    CommandResult<EditTrainingClassPageTranslationResult> editTrainingClassPageTranslation(UserVisitPK userVisitPK, EditTrainingClassPageTranslationForm form);
    
    CommandResult<?> deleteTrainingClassPageTranslation(UserVisitPK userVisitPK, DeleteTrainingClassPageTranslationForm form);
    
    // -------------------------------------------------------------------------
    //   Training Class Questions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createTrainingClassQuestion(UserVisitPK userVisitPK, CreateTrainingClassQuestionForm form);
    
    CommandResult<GetTrainingClassQuestionsResult> getTrainingClassQuestions(UserVisitPK userVisitPK, GetTrainingClassQuestionsForm form);
    
    CommandResult<GetTrainingClassQuestionResult> getTrainingClassQuestion(UserVisitPK userVisitPK, GetTrainingClassQuestionForm form);
    
    CommandResult<EditTrainingClassQuestionResult> editTrainingClassQuestion(UserVisitPK userVisitPK, EditTrainingClassQuestionForm form);
    
    CommandResult<?> deleteTrainingClassQuestion(UserVisitPK userVisitPK, DeleteTrainingClassQuestionForm form);
    
    // -------------------------------------------------------------------------
    //   Training Class Question Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createTrainingClassQuestionTranslation(UserVisitPK userVisitPK, CreateTrainingClassQuestionTranslationForm form);
    
    CommandResult<GetTrainingClassQuestionTranslationResult> getTrainingClassQuestionTranslation(UserVisitPK userVisitPK, GetTrainingClassQuestionTranslationForm form);
    
    CommandResult<GetTrainingClassQuestionTranslationsResult> getTrainingClassQuestionTranslations(UserVisitPK userVisitPK, GetTrainingClassQuestionTranslationsForm form);
    
    CommandResult<EditTrainingClassQuestionTranslationResult> editTrainingClassQuestionTranslation(UserVisitPK userVisitPK, EditTrainingClassQuestionTranslationForm form);
    
    CommandResult<?> deleteTrainingClassQuestionTranslation(UserVisitPK userVisitPK, DeleteTrainingClassQuestionTranslationForm form);
    
    // -------------------------------------------------------------------------
    //   Training Class Answers
    // -------------------------------------------------------------------------
    
    CommandResult<?> createTrainingClassAnswer(UserVisitPK userVisitPK, CreateTrainingClassAnswerForm form);
    
    CommandResult<GetTrainingClassAnswersResult> getTrainingClassAnswers(UserVisitPK userVisitPK, GetTrainingClassAnswersForm form);
    
    CommandResult<GetTrainingClassAnswerResult> getTrainingClassAnswer(UserVisitPK userVisitPK, GetTrainingClassAnswerForm form);
    
    CommandResult<EditTrainingClassAnswerResult> editTrainingClassAnswer(UserVisitPK userVisitPK, EditTrainingClassAnswerForm form);
    
    CommandResult<?> deleteTrainingClassAnswer(UserVisitPK userVisitPK, DeleteTrainingClassAnswerForm form);
    
    // -------------------------------------------------------------------------
    //   Training Class Answer Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createTrainingClassAnswerTranslation(UserVisitPK userVisitPK, CreateTrainingClassAnswerTranslationForm form);
    
    CommandResult<GetTrainingClassAnswerTranslationResult> getTrainingClassAnswerTranslation(UserVisitPK userVisitPK, GetTrainingClassAnswerTranslationForm form);
    
    CommandResult<GetTrainingClassAnswerTranslationsResult> getTrainingClassAnswerTranslations(UserVisitPK userVisitPK, GetTrainingClassAnswerTranslationsForm form);
    
    CommandResult<EditTrainingClassAnswerTranslationResult> editTrainingClassAnswerTranslation(UserVisitPK userVisitPK, EditTrainingClassAnswerTranslationForm form);
    
    CommandResult<?> deleteTrainingClassAnswerTranslation(UserVisitPK userVisitPK, DeleteTrainingClassAnswerTranslationForm form);
    
    // -------------------------------------------------------------------------
    //   Party Training Classes
    // -------------------------------------------------------------------------

    CommandResult<?> createPartyTrainingClass(UserVisitPK userVisitPK, CreatePartyTrainingClassForm form);

    CommandResult<GetPartyTrainingClassStatusChoicesResult> getPartyTrainingClassStatusChoices(UserVisitPK userVisitPK, GetPartyTrainingClassStatusChoicesForm form);

    CommandResult<?> setPartyTrainingClassStatus(UserVisitPK userVisitPK, SetPartyTrainingClassStatusForm form);

    CommandResult<GetPartyTrainingClassResult> getPartyTrainingClass(UserVisitPK userVisitPK, GetPartyTrainingClassForm form);

    CommandResult<GetPartyTrainingClassesResult> getPartyTrainingClasses(UserVisitPK userVisitPK, GetPartyTrainingClassesForm form);

    CommandResult<EditPartyTrainingClassResult> editPartyTrainingClass(UserVisitPK userVisitPK, EditPartyTrainingClassForm form);

    CommandResult<?> deletePartyTrainingClass(UserVisitPK userVisitPK, DeletePartyTrainingClassForm form);

    // -------------------------------------------------------------------------
    //   Party Training Class Sessions
    // -------------------------------------------------------------------------
    
    CommandResult<GetPartyTrainingClassSessionResult> getPartyTrainingClassSession(UserVisitPK userVisitPK, GetPartyTrainingClassSessionForm form);

    CommandResult<GetPartyTrainingClassSessionsResult> getPartyTrainingClassSessions(UserVisitPK userVisitPK, GetPartyTrainingClassSessionsForm form);

    CommandResult<?> deletePartyTrainingClassSession(UserVisitPK userVisitPK, DeletePartyTrainingClassSessionForm form);

    // -------------------------------------------------------------------------
    //   Party Training Class Session Pages
    // -------------------------------------------------------------------------
    
    CommandResult<EditPartyTrainingClassSessionPageResult> editPartyTrainingClassSessionPage(UserVisitPK userVisitPK, EditPartyTrainingClassSessionPageForm form);

    // -------------------------------------------------------------------------
    //   Party Training Class Session Answers
    // -------------------------------------------------------------------------
    
    CommandResult<EditPartyTrainingClassSessionAnswerResult> editPartyTrainingClassSessionAnswer(UserVisitPK userVisitPK, EditPartyTrainingClassSessionAnswerForm form);

    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
        
}

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
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface TrainingService
        extends TrainingForms {
    
    // -------------------------------------------------------------------------
    //   Training Classes
    // -------------------------------------------------------------------------
    
    CommandResult createTrainingClass(UserVisitPK userVisitPK, CreateTrainingClassForm form);
    
    CommandResult getTrainingClasses(UserVisitPK userVisitPK, GetTrainingClassesForm form);
    
    CommandResult getTrainingClass(UserVisitPK userVisitPK, GetTrainingClassForm form);
    
    CommandResult getTrainingClassChoices(UserVisitPK userVisitPK, GetTrainingClassChoicesForm form);
    
    CommandResult setDefaultTrainingClass(UserVisitPK userVisitPK, SetDefaultTrainingClassForm form);
    
    CommandResult editTrainingClass(UserVisitPK userVisitPK, EditTrainingClassForm form);
    
    CommandResult deleteTrainingClass(UserVisitPK userVisitPK, DeleteTrainingClassForm form);
    
    // -------------------------------------------------------------------------
    //   Training Class Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createTrainingClassTranslation(UserVisitPK userVisitPK, CreateTrainingClassTranslationForm form);
    
    CommandResult getTrainingClassTranslation(UserVisitPK userVisitPK, GetTrainingClassTranslationForm form);
    
    CommandResult getTrainingClassTranslations(UserVisitPK userVisitPK, GetTrainingClassTranslationsForm form);
    
    CommandResult editTrainingClassTranslation(UserVisitPK userVisitPK, EditTrainingClassTranslationForm form);
    
    CommandResult deleteTrainingClassTranslation(UserVisitPK userVisitPK, DeleteTrainingClassTranslationForm form);
    
    // -------------------------------------------------------------------------
    //   Training Class Sections
    // -------------------------------------------------------------------------
    
    CommandResult createTrainingClassSection(UserVisitPK userVisitPK, CreateTrainingClassSectionForm form);
    
    CommandResult getTrainingClassSections(UserVisitPK userVisitPK, GetTrainingClassSectionsForm form);
    
    CommandResult getTrainingClassSection(UserVisitPK userVisitPK, GetTrainingClassSectionForm form);
    
    CommandResult editTrainingClassSection(UserVisitPK userVisitPK, EditTrainingClassSectionForm form);
    
    CommandResult deleteTrainingClassSection(UserVisitPK userVisitPK, DeleteTrainingClassSectionForm form);
    
    // -------------------------------------------------------------------------
    //   Training Class Section Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createTrainingClassSectionTranslation(UserVisitPK userVisitPK, CreateTrainingClassSectionTranslationForm form);
    
    CommandResult getTrainingClassSectionTranslation(UserVisitPK userVisitPK, GetTrainingClassSectionTranslationForm form);
    
    CommandResult getTrainingClassSectionTranslations(UserVisitPK userVisitPK, GetTrainingClassSectionTranslationsForm form);
    
    CommandResult editTrainingClassSectionTranslation(UserVisitPK userVisitPK, EditTrainingClassSectionTranslationForm form);
    
    CommandResult deleteTrainingClassSectionTranslation(UserVisitPK userVisitPK, DeleteTrainingClassSectionTranslationForm form);
    
    // -------------------------------------------------------------------------
    //   Training Class Pages
    // -------------------------------------------------------------------------
    
    CommandResult createTrainingClassPage(UserVisitPK userVisitPK, CreateTrainingClassPageForm form);
    
    CommandResult getTrainingClassPages(UserVisitPK userVisitPK, GetTrainingClassPagesForm form);
    
    CommandResult getTrainingClassPage(UserVisitPK userVisitPK, GetTrainingClassPageForm form);
    
    CommandResult editTrainingClassPage(UserVisitPK userVisitPK, EditTrainingClassPageForm form);
    
    CommandResult deleteTrainingClassPage(UserVisitPK userVisitPK, DeleteTrainingClassPageForm form);
    
    // -------------------------------------------------------------------------
    //   Training Class Page Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createTrainingClassPageTranslation(UserVisitPK userVisitPK, CreateTrainingClassPageTranslationForm form);
    
    CommandResult getTrainingClassPageTranslation(UserVisitPK userVisitPK, GetTrainingClassPageTranslationForm form);
    
    CommandResult getTrainingClassPageTranslations(UserVisitPK userVisitPK, GetTrainingClassPageTranslationsForm form);
    
    CommandResult editTrainingClassPageTranslation(UserVisitPK userVisitPK, EditTrainingClassPageTranslationForm form);
    
    CommandResult deleteTrainingClassPageTranslation(UserVisitPK userVisitPK, DeleteTrainingClassPageTranslationForm form);
    
    // -------------------------------------------------------------------------
    //   Training Class Questions
    // -------------------------------------------------------------------------
    
    CommandResult createTrainingClassQuestion(UserVisitPK userVisitPK, CreateTrainingClassQuestionForm form);
    
    CommandResult getTrainingClassQuestions(UserVisitPK userVisitPK, GetTrainingClassQuestionsForm form);
    
    CommandResult getTrainingClassQuestion(UserVisitPK userVisitPK, GetTrainingClassQuestionForm form);
    
    CommandResult editTrainingClassQuestion(UserVisitPK userVisitPK, EditTrainingClassQuestionForm form);
    
    CommandResult deleteTrainingClassQuestion(UserVisitPK userVisitPK, DeleteTrainingClassQuestionForm form);
    
    // -------------------------------------------------------------------------
    //   Training Class Question Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createTrainingClassQuestionTranslation(UserVisitPK userVisitPK, CreateTrainingClassQuestionTranslationForm form);
    
    CommandResult getTrainingClassQuestionTranslation(UserVisitPK userVisitPK, GetTrainingClassQuestionTranslationForm form);
    
    CommandResult getTrainingClassQuestionTranslations(UserVisitPK userVisitPK, GetTrainingClassQuestionTranslationsForm form);
    
    CommandResult editTrainingClassQuestionTranslation(UserVisitPK userVisitPK, EditTrainingClassQuestionTranslationForm form);
    
    CommandResult deleteTrainingClassQuestionTranslation(UserVisitPK userVisitPK, DeleteTrainingClassQuestionTranslationForm form);
    
    // -------------------------------------------------------------------------
    //   Training Class Answers
    // -------------------------------------------------------------------------
    
    CommandResult createTrainingClassAnswer(UserVisitPK userVisitPK, CreateTrainingClassAnswerForm form);
    
    CommandResult getTrainingClassAnswers(UserVisitPK userVisitPK, GetTrainingClassAnswersForm form);
    
    CommandResult getTrainingClassAnswer(UserVisitPK userVisitPK, GetTrainingClassAnswerForm form);
    
    CommandResult editTrainingClassAnswer(UserVisitPK userVisitPK, EditTrainingClassAnswerForm form);
    
    CommandResult deleteTrainingClassAnswer(UserVisitPK userVisitPK, DeleteTrainingClassAnswerForm form);
    
    // -------------------------------------------------------------------------
    //   Training Class Answer Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createTrainingClassAnswerTranslation(UserVisitPK userVisitPK, CreateTrainingClassAnswerTranslationForm form);
    
    CommandResult getTrainingClassAnswerTranslation(UserVisitPK userVisitPK, GetTrainingClassAnswerTranslationForm form);
    
    CommandResult getTrainingClassAnswerTranslations(UserVisitPK userVisitPK, GetTrainingClassAnswerTranslationsForm form);
    
    CommandResult editTrainingClassAnswerTranslation(UserVisitPK userVisitPK, EditTrainingClassAnswerTranslationForm form);
    
    CommandResult deleteTrainingClassAnswerTranslation(UserVisitPK userVisitPK, DeleteTrainingClassAnswerTranslationForm form);
    
    // -------------------------------------------------------------------------
    //   Party Training Classes
    // -------------------------------------------------------------------------

    CommandResult createPartyTrainingClass(UserVisitPK userVisitPK, CreatePartyTrainingClassForm form);

    CommandResult getPartyTrainingClassStatusChoices(UserVisitPK userVisitPK, GetPartyTrainingClassStatusChoicesForm form);

    CommandResult setPartyTrainingClassStatus(UserVisitPK userVisitPK, SetPartyTrainingClassStatusForm form);

    CommandResult getPartyTrainingClass(UserVisitPK userVisitPK, GetPartyTrainingClassForm form);

    CommandResult getPartyTrainingClasses(UserVisitPK userVisitPK, GetPartyTrainingClassesForm form);

    CommandResult editPartyTrainingClass(UserVisitPK userVisitPK, EditPartyTrainingClassForm form);

    CommandResult deletePartyTrainingClass(UserVisitPK userVisitPK, DeletePartyTrainingClassForm form);

    // -------------------------------------------------------------------------
    //   Party Training Class Sessions
    // -------------------------------------------------------------------------
    
    CommandResult getPartyTrainingClassSession(UserVisitPK userVisitPK, GetPartyTrainingClassSessionForm form);

    CommandResult getPartyTrainingClassSessions(UserVisitPK userVisitPK, GetPartyTrainingClassSessionsForm form);

    CommandResult deletePartyTrainingClassSession(UserVisitPK userVisitPK, DeletePartyTrainingClassSessionForm form);

    // -------------------------------------------------------------------------
    //   Party Training Class Session Pages
    // -------------------------------------------------------------------------
    
    CommandResult editPartyTrainingClassSessionPage(UserVisitPK userVisitPK, EditPartyTrainingClassSessionPageForm form);

    // -------------------------------------------------------------------------
    //   Party Training Class Session Answers
    // -------------------------------------------------------------------------
    
    CommandResult editPartyTrainingClassSessionAnswer(UserVisitPK userVisitPK, EditPartyTrainingClassSessionAnswerForm form);

    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
        
}

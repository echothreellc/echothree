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

package com.echothree.control.user.training.server;

import com.echothree.control.user.training.common.TrainingRemote;
import com.echothree.control.user.training.common.form.*;
import com.echothree.control.user.training.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class TrainingBean
        extends TrainingFormsImpl
        implements TrainingRemote, TrainingLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "TrainingBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Training Classes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrainingClass(UserVisitPK userVisitPK, CreateTrainingClassForm form) {
        return new CreateTrainingClassCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClasses(UserVisitPK userVisitPK, GetTrainingClassesForm form) {
        return new GetTrainingClassesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClass(UserVisitPK userVisitPK, GetTrainingClassForm form) {
        return new GetTrainingClassCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassChoices(UserVisitPK userVisitPK, GetTrainingClassChoicesForm form) {
        return new GetTrainingClassChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultTrainingClass(UserVisitPK userVisitPK, SetDefaultTrainingClassForm form) {
        return new SetDefaultTrainingClassCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTrainingClass(UserVisitPK userVisitPK, EditTrainingClassForm form) {
        return new EditTrainingClassCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTrainingClass(UserVisitPK userVisitPK, DeleteTrainingClassForm form) {
        return new DeleteTrainingClassCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Training Class Translations
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrainingClassTranslation(UserVisitPK userVisitPK, CreateTrainingClassTranslationForm form) {
        return new CreateTrainingClassTranslationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassTranslation(UserVisitPK userVisitPK, GetTrainingClassTranslationForm form) {
        return new GetTrainingClassTranslationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassTranslations(UserVisitPK userVisitPK, GetTrainingClassTranslationsForm form) {
        return new GetTrainingClassTranslationsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTrainingClassTranslation(UserVisitPK userVisitPK, EditTrainingClassTranslationForm form) {
        return new EditTrainingClassTranslationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTrainingClassTranslation(UserVisitPK userVisitPK, DeleteTrainingClassTranslationForm form) {
        return new DeleteTrainingClassTranslationCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Training Class Sections
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrainingClassSection(UserVisitPK userVisitPK, CreateTrainingClassSectionForm form) {
        return new CreateTrainingClassSectionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassSections(UserVisitPK userVisitPK, GetTrainingClassSectionsForm form) {
        return new GetTrainingClassSectionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassSection(UserVisitPK userVisitPK, GetTrainingClassSectionForm form) {
        return new GetTrainingClassSectionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTrainingClassSection(UserVisitPK userVisitPK, EditTrainingClassSectionForm form) {
        return new EditTrainingClassSectionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTrainingClassSection(UserVisitPK userVisitPK, DeleteTrainingClassSectionForm form) {
        return new DeleteTrainingClassSectionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Training Class Section Translations
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrainingClassSectionTranslation(UserVisitPK userVisitPK, CreateTrainingClassSectionTranslationForm form) {
        return new CreateTrainingClassSectionTranslationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassSectionTranslation(UserVisitPK userVisitPK, GetTrainingClassSectionTranslationForm form) {
        return new GetTrainingClassSectionTranslationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassSectionTranslations(UserVisitPK userVisitPK, GetTrainingClassSectionTranslationsForm form) {
        return new GetTrainingClassSectionTranslationsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTrainingClassSectionTranslation(UserVisitPK userVisitPK, EditTrainingClassSectionTranslationForm form) {
        return new EditTrainingClassSectionTranslationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTrainingClassSectionTranslation(UserVisitPK userVisitPK, DeleteTrainingClassSectionTranslationForm form) {
        return new DeleteTrainingClassSectionTranslationCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Training Class Pages
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrainingClassPage(UserVisitPK userVisitPK, CreateTrainingClassPageForm form) {
        return new CreateTrainingClassPageCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassPages(UserVisitPK userVisitPK, GetTrainingClassPagesForm form) {
        return new GetTrainingClassPagesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassPage(UserVisitPK userVisitPK, GetTrainingClassPageForm form) {
        return new GetTrainingClassPageCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTrainingClassPage(UserVisitPK userVisitPK, EditTrainingClassPageForm form) {
        return new EditTrainingClassPageCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTrainingClassPage(UserVisitPK userVisitPK, DeleteTrainingClassPageForm form) {
        return new DeleteTrainingClassPageCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Training Class Page Translations
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrainingClassPageTranslation(UserVisitPK userVisitPK, CreateTrainingClassPageTranslationForm form) {
        return new CreateTrainingClassPageTranslationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassPageTranslation(UserVisitPK userVisitPK, GetTrainingClassPageTranslationForm form) {
        return new GetTrainingClassPageTranslationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassPageTranslations(UserVisitPK userVisitPK, GetTrainingClassPageTranslationsForm form) {
        return new GetTrainingClassPageTranslationsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTrainingClassPageTranslation(UserVisitPK userVisitPK, EditTrainingClassPageTranslationForm form) {
        return new EditTrainingClassPageTranslationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTrainingClassPageTranslation(UserVisitPK userVisitPK, DeleteTrainingClassPageTranslationForm form) {
        return new DeleteTrainingClassPageTranslationCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Training Class Questions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrainingClassQuestion(UserVisitPK userVisitPK, CreateTrainingClassQuestionForm form) {
        return new CreateTrainingClassQuestionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassQuestions(UserVisitPK userVisitPK, GetTrainingClassQuestionsForm form) {
        return new GetTrainingClassQuestionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassQuestion(UserVisitPK userVisitPK, GetTrainingClassQuestionForm form) {
        return new GetTrainingClassQuestionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTrainingClassQuestion(UserVisitPK userVisitPK, EditTrainingClassQuestionForm form) {
        return new EditTrainingClassQuestionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTrainingClassQuestion(UserVisitPK userVisitPK, DeleteTrainingClassQuestionForm form) {
        return new DeleteTrainingClassQuestionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Training Class Question Translations
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrainingClassQuestionTranslation(UserVisitPK userVisitPK, CreateTrainingClassQuestionTranslationForm form) {
        return new CreateTrainingClassQuestionTranslationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassQuestionTranslation(UserVisitPK userVisitPK, GetTrainingClassQuestionTranslationForm form) {
        return new GetTrainingClassQuestionTranslationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassQuestionTranslations(UserVisitPK userVisitPK, GetTrainingClassQuestionTranslationsForm form) {
        return new GetTrainingClassQuestionTranslationsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTrainingClassQuestionTranslation(UserVisitPK userVisitPK, EditTrainingClassQuestionTranslationForm form) {
        return new EditTrainingClassQuestionTranslationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTrainingClassQuestionTranslation(UserVisitPK userVisitPK, DeleteTrainingClassQuestionTranslationForm form) {
        return new DeleteTrainingClassQuestionTranslationCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Training Class Answers
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrainingClassAnswer(UserVisitPK userVisitPK, CreateTrainingClassAnswerForm form) {
        return new CreateTrainingClassAnswerCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassAnswers(UserVisitPK userVisitPK, GetTrainingClassAnswersForm form) {
        return new GetTrainingClassAnswersCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassAnswer(UserVisitPK userVisitPK, GetTrainingClassAnswerForm form) {
        return new GetTrainingClassAnswerCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTrainingClassAnswer(UserVisitPK userVisitPK, EditTrainingClassAnswerForm form) {
        return new EditTrainingClassAnswerCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTrainingClassAnswer(UserVisitPK userVisitPK, DeleteTrainingClassAnswerForm form) {
        return new DeleteTrainingClassAnswerCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Training Class Answer Translations
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrainingClassAnswerTranslation(UserVisitPK userVisitPK, CreateTrainingClassAnswerTranslationForm form) {
        return new CreateTrainingClassAnswerTranslationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassAnswerTranslation(UserVisitPK userVisitPK, GetTrainingClassAnswerTranslationForm form) {
        return new GetTrainingClassAnswerTranslationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassAnswerTranslations(UserVisitPK userVisitPK, GetTrainingClassAnswerTranslationsForm form) {
        return new GetTrainingClassAnswerTranslationsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTrainingClassAnswerTranslation(UserVisitPK userVisitPK, EditTrainingClassAnswerTranslationForm form) {
        return new EditTrainingClassAnswerTranslationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTrainingClassAnswerTranslation(UserVisitPK userVisitPK, DeleteTrainingClassAnswerTranslationForm form) {
        return new DeleteTrainingClassAnswerTranslationCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Training Classes
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPartyTrainingClass(UserVisitPK userVisitPK, CreatePartyTrainingClassForm form) {
        return new CreatePartyTrainingClassCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyTrainingClassStatusChoices(UserVisitPK userVisitPK, GetPartyTrainingClassStatusChoicesForm form) {
        return new GetPartyTrainingClassStatusChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setPartyTrainingClassStatus(UserVisitPK userVisitPK, SetPartyTrainingClassStatusForm form) {
        return new SetPartyTrainingClassStatusCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyTrainingClass(UserVisitPK userVisitPK, GetPartyTrainingClassForm form) {
        return new GetPartyTrainingClassCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyTrainingClasses(UserVisitPK userVisitPK, GetPartyTrainingClassesForm form) {
        return new GetPartyTrainingClassesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPartyTrainingClass(UserVisitPK userVisitPK, EditPartyTrainingClassForm form) {
        return new EditPartyTrainingClassCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyTrainingClass(UserVisitPK userVisitPK, DeletePartyTrainingClassForm form) {
        return new DeletePartyTrainingClassCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Party Training Class Sessions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult getPartyTrainingClassSession(UserVisitPK userVisitPK, GetPartyTrainingClassSessionForm form) {
        return new GetPartyTrainingClassSessionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyTrainingClassSessions(UserVisitPK userVisitPK, GetPartyTrainingClassSessionsForm form) {
        return new GetPartyTrainingClassSessionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyTrainingClassSession(UserVisitPK userVisitPK, DeletePartyTrainingClassSessionForm form) {
        return new DeletePartyTrainingClassSessionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Party Training Class Session Pages
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult editPartyTrainingClassSessionPage(UserVisitPK userVisitPK, EditPartyTrainingClassSessionPageForm form) {
        return new EditPartyTrainingClassSessionPageCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Party Training Class Session Answers
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult editPartyTrainingClassSessionAnswer(UserVisitPK userVisitPK, EditPartyTrainingClassSessionAnswerForm form) {
        return new EditPartyTrainingClassSessionAnswerCommand().run(userVisitPK, form);
    }

}

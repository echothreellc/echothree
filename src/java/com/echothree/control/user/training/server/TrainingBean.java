// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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
        return new CreateTrainingClassCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTrainingClasses(UserVisitPK userVisitPK, GetTrainingClassesForm form) {
        return new GetTrainingClassesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTrainingClass(UserVisitPK userVisitPK, GetTrainingClassForm form) {
        return new GetTrainingClassCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTrainingClassChoices(UserVisitPK userVisitPK, GetTrainingClassChoicesForm form) {
        return new GetTrainingClassChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultTrainingClass(UserVisitPK userVisitPK, SetDefaultTrainingClassForm form) {
        return new SetDefaultTrainingClassCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTrainingClass(UserVisitPK userVisitPK, EditTrainingClassForm form) {
        return new EditTrainingClassCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTrainingClass(UserVisitPK userVisitPK, DeleteTrainingClassForm form) {
        return new DeleteTrainingClassCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Training Class Translations
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrainingClassTranslation(UserVisitPK userVisitPK, CreateTrainingClassTranslationForm form) {
        return new CreateTrainingClassTranslationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTrainingClassTranslation(UserVisitPK userVisitPK, GetTrainingClassTranslationForm form) {
        return new GetTrainingClassTranslationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTrainingClassTranslations(UserVisitPK userVisitPK, GetTrainingClassTranslationsForm form) {
        return new GetTrainingClassTranslationsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTrainingClassTranslation(UserVisitPK userVisitPK, EditTrainingClassTranslationForm form) {
        return new EditTrainingClassTranslationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTrainingClassTranslation(UserVisitPK userVisitPK, DeleteTrainingClassTranslationForm form) {
        return new DeleteTrainingClassTranslationCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Training Class Sections
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrainingClassSection(UserVisitPK userVisitPK, CreateTrainingClassSectionForm form) {
        return new CreateTrainingClassSectionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTrainingClassSections(UserVisitPK userVisitPK, GetTrainingClassSectionsForm form) {
        return new GetTrainingClassSectionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTrainingClassSection(UserVisitPK userVisitPK, GetTrainingClassSectionForm form) {
        return new GetTrainingClassSectionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTrainingClassSection(UserVisitPK userVisitPK, EditTrainingClassSectionForm form) {
        return new EditTrainingClassSectionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTrainingClassSection(UserVisitPK userVisitPK, DeleteTrainingClassSectionForm form) {
        return new DeleteTrainingClassSectionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Training Class Section Translations
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrainingClassSectionTranslation(UserVisitPK userVisitPK, CreateTrainingClassSectionTranslationForm form) {
        return new CreateTrainingClassSectionTranslationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTrainingClassSectionTranslation(UserVisitPK userVisitPK, GetTrainingClassSectionTranslationForm form) {
        return new GetTrainingClassSectionTranslationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTrainingClassSectionTranslations(UserVisitPK userVisitPK, GetTrainingClassSectionTranslationsForm form) {
        return new GetTrainingClassSectionTranslationsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTrainingClassSectionTranslation(UserVisitPK userVisitPK, EditTrainingClassSectionTranslationForm form) {
        return new EditTrainingClassSectionTranslationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTrainingClassSectionTranslation(UserVisitPK userVisitPK, DeleteTrainingClassSectionTranslationForm form) {
        return new DeleteTrainingClassSectionTranslationCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Training Class Pages
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrainingClassPage(UserVisitPK userVisitPK, CreateTrainingClassPageForm form) {
        return new CreateTrainingClassPageCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTrainingClassPages(UserVisitPK userVisitPK, GetTrainingClassPagesForm form) {
        return new GetTrainingClassPagesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTrainingClassPage(UserVisitPK userVisitPK, GetTrainingClassPageForm form) {
        return new GetTrainingClassPageCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTrainingClassPage(UserVisitPK userVisitPK, EditTrainingClassPageForm form) {
        return new EditTrainingClassPageCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTrainingClassPage(UserVisitPK userVisitPK, DeleteTrainingClassPageForm form) {
        return new DeleteTrainingClassPageCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Training Class Page Translations
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrainingClassPageTranslation(UserVisitPK userVisitPK, CreateTrainingClassPageTranslationForm form) {
        return new CreateTrainingClassPageTranslationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTrainingClassPageTranslation(UserVisitPK userVisitPK, GetTrainingClassPageTranslationForm form) {
        return new GetTrainingClassPageTranslationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTrainingClassPageTranslations(UserVisitPK userVisitPK, GetTrainingClassPageTranslationsForm form) {
        return new GetTrainingClassPageTranslationsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTrainingClassPageTranslation(UserVisitPK userVisitPK, EditTrainingClassPageTranslationForm form) {
        return new EditTrainingClassPageTranslationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTrainingClassPageTranslation(UserVisitPK userVisitPK, DeleteTrainingClassPageTranslationForm form) {
        return new DeleteTrainingClassPageTranslationCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Training Class Questions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrainingClassQuestion(UserVisitPK userVisitPK, CreateTrainingClassQuestionForm form) {
        return new CreateTrainingClassQuestionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTrainingClassQuestions(UserVisitPK userVisitPK, GetTrainingClassQuestionsForm form) {
        return new GetTrainingClassQuestionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTrainingClassQuestion(UserVisitPK userVisitPK, GetTrainingClassQuestionForm form) {
        return new GetTrainingClassQuestionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTrainingClassQuestion(UserVisitPK userVisitPK, EditTrainingClassQuestionForm form) {
        return new EditTrainingClassQuestionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTrainingClassQuestion(UserVisitPK userVisitPK, DeleteTrainingClassQuestionForm form) {
        return new DeleteTrainingClassQuestionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Training Class Question Translations
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrainingClassQuestionTranslation(UserVisitPK userVisitPK, CreateTrainingClassQuestionTranslationForm form) {
        return new CreateTrainingClassQuestionTranslationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTrainingClassQuestionTranslation(UserVisitPK userVisitPK, GetTrainingClassQuestionTranslationForm form) {
        return new GetTrainingClassQuestionTranslationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTrainingClassQuestionTranslations(UserVisitPK userVisitPK, GetTrainingClassQuestionTranslationsForm form) {
        return new GetTrainingClassQuestionTranslationsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTrainingClassQuestionTranslation(UserVisitPK userVisitPK, EditTrainingClassQuestionTranslationForm form) {
        return new EditTrainingClassQuestionTranslationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTrainingClassQuestionTranslation(UserVisitPK userVisitPK, DeleteTrainingClassQuestionTranslationForm form) {
        return new DeleteTrainingClassQuestionTranslationCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Training Class Answers
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrainingClassAnswer(UserVisitPK userVisitPK, CreateTrainingClassAnswerForm form) {
        return new CreateTrainingClassAnswerCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTrainingClassAnswers(UserVisitPK userVisitPK, GetTrainingClassAnswersForm form) {
        return new GetTrainingClassAnswersCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTrainingClassAnswer(UserVisitPK userVisitPK, GetTrainingClassAnswerForm form) {
        return new GetTrainingClassAnswerCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTrainingClassAnswer(UserVisitPK userVisitPK, EditTrainingClassAnswerForm form) {
        return new EditTrainingClassAnswerCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTrainingClassAnswer(UserVisitPK userVisitPK, DeleteTrainingClassAnswerForm form) {
        return new DeleteTrainingClassAnswerCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Training Class Answer Translations
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrainingClassAnswerTranslation(UserVisitPK userVisitPK, CreateTrainingClassAnswerTranslationForm form) {
        return new CreateTrainingClassAnswerTranslationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTrainingClassAnswerTranslation(UserVisitPK userVisitPK, GetTrainingClassAnswerTranslationForm form) {
        return new GetTrainingClassAnswerTranslationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTrainingClassAnswerTranslations(UserVisitPK userVisitPK, GetTrainingClassAnswerTranslationsForm form) {
        return new GetTrainingClassAnswerTranslationsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTrainingClassAnswerTranslation(UserVisitPK userVisitPK, EditTrainingClassAnswerTranslationForm form) {
        return new EditTrainingClassAnswerTranslationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTrainingClassAnswerTranslation(UserVisitPK userVisitPK, DeleteTrainingClassAnswerTranslationForm form) {
        return new DeleteTrainingClassAnswerTranslationCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Party Training Classes
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPartyTrainingClass(UserVisitPK userVisitPK, CreatePartyTrainingClassForm form) {
        return new CreatePartyTrainingClassCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyTrainingClassStatusChoices(UserVisitPK userVisitPK, GetPartyTrainingClassStatusChoicesForm form) {
        return new GetPartyTrainingClassStatusChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setPartyTrainingClassStatus(UserVisitPK userVisitPK, SetPartyTrainingClassStatusForm form) {
        return new SetPartyTrainingClassStatusCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyTrainingClass(UserVisitPK userVisitPK, GetPartyTrainingClassForm form) {
        return new GetPartyTrainingClassCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyTrainingClasses(UserVisitPK userVisitPK, GetPartyTrainingClassesForm form) {
        return new GetPartyTrainingClassesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editPartyTrainingClass(UserVisitPK userVisitPK, EditPartyTrainingClassForm form) {
        return new EditPartyTrainingClassCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePartyTrainingClass(UserVisitPK userVisitPK, DeletePartyTrainingClassForm form) {
        return new DeletePartyTrainingClassCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Party Training Class Sessions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult getPartyTrainingClassSession(UserVisitPK userVisitPK, GetPartyTrainingClassSessionForm form) {
        return new GetPartyTrainingClassSessionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyTrainingClassSessions(UserVisitPK userVisitPK, GetPartyTrainingClassSessionsForm form) {
        return new GetPartyTrainingClassSessionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePartyTrainingClassSession(UserVisitPK userVisitPK, DeletePartyTrainingClassSessionForm form) {
        return new DeletePartyTrainingClassSessionCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Party Training Class Session Pages
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult editPartyTrainingClassSessionPage(UserVisitPK userVisitPK, EditPartyTrainingClassSessionPageForm form) {
        return new EditPartyTrainingClassSessionPageCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Party Training Class Session Answers
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult editPartyTrainingClassSessionAnswer(UserVisitPK userVisitPK, EditPartyTrainingClassSessionAnswerForm form) {
        return new EditPartyTrainingClassSessionAnswerCommand(userVisitPK, form).run();
    }

}

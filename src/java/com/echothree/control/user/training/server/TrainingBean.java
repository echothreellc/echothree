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
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateTrainingClassCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClasses(UserVisitPK userVisitPK, GetTrainingClassesForm form) {
        return CDI.current().select(GetTrainingClassesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClass(UserVisitPK userVisitPK, GetTrainingClassForm form) {
        return CDI.current().select(GetTrainingClassCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassChoices(UserVisitPK userVisitPK, GetTrainingClassChoicesForm form) {
        return CDI.current().select(GetTrainingClassChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultTrainingClass(UserVisitPK userVisitPK, SetDefaultTrainingClassForm form) {
        return CDI.current().select(SetDefaultTrainingClassCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTrainingClass(UserVisitPK userVisitPK, EditTrainingClassForm form) {
        return CDI.current().select(EditTrainingClassCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTrainingClass(UserVisitPK userVisitPK, DeleteTrainingClassForm form) {
        return CDI.current().select(DeleteTrainingClassCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Training Class Translations
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrainingClassTranslation(UserVisitPK userVisitPK, CreateTrainingClassTranslationForm form) {
        return CDI.current().select(CreateTrainingClassTranslationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassTranslation(UserVisitPK userVisitPK, GetTrainingClassTranslationForm form) {
        return CDI.current().select(GetTrainingClassTranslationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassTranslations(UserVisitPK userVisitPK, GetTrainingClassTranslationsForm form) {
        return CDI.current().select(GetTrainingClassTranslationsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTrainingClassTranslation(UserVisitPK userVisitPK, EditTrainingClassTranslationForm form) {
        return CDI.current().select(EditTrainingClassTranslationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTrainingClassTranslation(UserVisitPK userVisitPK, DeleteTrainingClassTranslationForm form) {
        return CDI.current().select(DeleteTrainingClassTranslationCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Training Class Sections
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrainingClassSection(UserVisitPK userVisitPK, CreateTrainingClassSectionForm form) {
        return CDI.current().select(CreateTrainingClassSectionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassSections(UserVisitPK userVisitPK, GetTrainingClassSectionsForm form) {
        return CDI.current().select(GetTrainingClassSectionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassSection(UserVisitPK userVisitPK, GetTrainingClassSectionForm form) {
        return CDI.current().select(GetTrainingClassSectionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTrainingClassSection(UserVisitPK userVisitPK, EditTrainingClassSectionForm form) {
        return CDI.current().select(EditTrainingClassSectionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTrainingClassSection(UserVisitPK userVisitPK, DeleteTrainingClassSectionForm form) {
        return CDI.current().select(DeleteTrainingClassSectionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Training Class Section Translations
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrainingClassSectionTranslation(UserVisitPK userVisitPK, CreateTrainingClassSectionTranslationForm form) {
        return CDI.current().select(CreateTrainingClassSectionTranslationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassSectionTranslation(UserVisitPK userVisitPK, GetTrainingClassSectionTranslationForm form) {
        return CDI.current().select(GetTrainingClassSectionTranslationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassSectionTranslations(UserVisitPK userVisitPK, GetTrainingClassSectionTranslationsForm form) {
        return CDI.current().select(GetTrainingClassSectionTranslationsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTrainingClassSectionTranslation(UserVisitPK userVisitPK, EditTrainingClassSectionTranslationForm form) {
        return CDI.current().select(EditTrainingClassSectionTranslationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTrainingClassSectionTranslation(UserVisitPK userVisitPK, DeleteTrainingClassSectionTranslationForm form) {
        return CDI.current().select(DeleteTrainingClassSectionTranslationCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Training Class Pages
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrainingClassPage(UserVisitPK userVisitPK, CreateTrainingClassPageForm form) {
        return CDI.current().select(CreateTrainingClassPageCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassPages(UserVisitPK userVisitPK, GetTrainingClassPagesForm form) {
        return CDI.current().select(GetTrainingClassPagesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassPage(UserVisitPK userVisitPK, GetTrainingClassPageForm form) {
        return CDI.current().select(GetTrainingClassPageCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTrainingClassPage(UserVisitPK userVisitPK, EditTrainingClassPageForm form) {
        return CDI.current().select(EditTrainingClassPageCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTrainingClassPage(UserVisitPK userVisitPK, DeleteTrainingClassPageForm form) {
        return CDI.current().select(DeleteTrainingClassPageCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Training Class Page Translations
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrainingClassPageTranslation(UserVisitPK userVisitPK, CreateTrainingClassPageTranslationForm form) {
        return CDI.current().select(CreateTrainingClassPageTranslationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassPageTranslation(UserVisitPK userVisitPK, GetTrainingClassPageTranslationForm form) {
        return CDI.current().select(GetTrainingClassPageTranslationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassPageTranslations(UserVisitPK userVisitPK, GetTrainingClassPageTranslationsForm form) {
        return CDI.current().select(GetTrainingClassPageTranslationsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTrainingClassPageTranslation(UserVisitPK userVisitPK, EditTrainingClassPageTranslationForm form) {
        return CDI.current().select(EditTrainingClassPageTranslationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTrainingClassPageTranslation(UserVisitPK userVisitPK, DeleteTrainingClassPageTranslationForm form) {
        return CDI.current().select(DeleteTrainingClassPageTranslationCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Training Class Questions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrainingClassQuestion(UserVisitPK userVisitPK, CreateTrainingClassQuestionForm form) {
        return CDI.current().select(CreateTrainingClassQuestionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassQuestions(UserVisitPK userVisitPK, GetTrainingClassQuestionsForm form) {
        return CDI.current().select(GetTrainingClassQuestionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassQuestion(UserVisitPK userVisitPK, GetTrainingClassQuestionForm form) {
        return CDI.current().select(GetTrainingClassQuestionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTrainingClassQuestion(UserVisitPK userVisitPK, EditTrainingClassQuestionForm form) {
        return CDI.current().select(EditTrainingClassQuestionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTrainingClassQuestion(UserVisitPK userVisitPK, DeleteTrainingClassQuestionForm form) {
        return CDI.current().select(DeleteTrainingClassQuestionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Training Class Question Translations
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrainingClassQuestionTranslation(UserVisitPK userVisitPK, CreateTrainingClassQuestionTranslationForm form) {
        return CDI.current().select(CreateTrainingClassQuestionTranslationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassQuestionTranslation(UserVisitPK userVisitPK, GetTrainingClassQuestionTranslationForm form) {
        return CDI.current().select(GetTrainingClassQuestionTranslationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassQuestionTranslations(UserVisitPK userVisitPK, GetTrainingClassQuestionTranslationsForm form) {
        return CDI.current().select(GetTrainingClassQuestionTranslationsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTrainingClassQuestionTranslation(UserVisitPK userVisitPK, EditTrainingClassQuestionTranslationForm form) {
        return CDI.current().select(EditTrainingClassQuestionTranslationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTrainingClassQuestionTranslation(UserVisitPK userVisitPK, DeleteTrainingClassQuestionTranslationForm form) {
        return CDI.current().select(DeleteTrainingClassQuestionTranslationCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Training Class Answers
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrainingClassAnswer(UserVisitPK userVisitPK, CreateTrainingClassAnswerForm form) {
        return CDI.current().select(CreateTrainingClassAnswerCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassAnswers(UserVisitPK userVisitPK, GetTrainingClassAnswersForm form) {
        return CDI.current().select(GetTrainingClassAnswersCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassAnswer(UserVisitPK userVisitPK, GetTrainingClassAnswerForm form) {
        return CDI.current().select(GetTrainingClassAnswerCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTrainingClassAnswer(UserVisitPK userVisitPK, EditTrainingClassAnswerForm form) {
        return CDI.current().select(EditTrainingClassAnswerCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTrainingClassAnswer(UserVisitPK userVisitPK, DeleteTrainingClassAnswerForm form) {
        return CDI.current().select(DeleteTrainingClassAnswerCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Training Class Answer Translations
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrainingClassAnswerTranslation(UserVisitPK userVisitPK, CreateTrainingClassAnswerTranslationForm form) {
        return CDI.current().select(CreateTrainingClassAnswerTranslationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassAnswerTranslation(UserVisitPK userVisitPK, GetTrainingClassAnswerTranslationForm form) {
        return CDI.current().select(GetTrainingClassAnswerTranslationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrainingClassAnswerTranslations(UserVisitPK userVisitPK, GetTrainingClassAnswerTranslationsForm form) {
        return CDI.current().select(GetTrainingClassAnswerTranslationsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTrainingClassAnswerTranslation(UserVisitPK userVisitPK, EditTrainingClassAnswerTranslationForm form) {
        return CDI.current().select(EditTrainingClassAnswerTranslationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTrainingClassAnswerTranslation(UserVisitPK userVisitPK, DeleteTrainingClassAnswerTranslationForm form) {
        return CDI.current().select(DeleteTrainingClassAnswerTranslationCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Training Classes
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPartyTrainingClass(UserVisitPK userVisitPK, CreatePartyTrainingClassForm form) {
        return CDI.current().select(CreatePartyTrainingClassCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyTrainingClassStatusChoices(UserVisitPK userVisitPK, GetPartyTrainingClassStatusChoicesForm form) {
        return CDI.current().select(GetPartyTrainingClassStatusChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setPartyTrainingClassStatus(UserVisitPK userVisitPK, SetPartyTrainingClassStatusForm form) {
        return CDI.current().select(SetPartyTrainingClassStatusCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyTrainingClass(UserVisitPK userVisitPK, GetPartyTrainingClassForm form) {
        return CDI.current().select(GetPartyTrainingClassCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyTrainingClasses(UserVisitPK userVisitPK, GetPartyTrainingClassesForm form) {
        return CDI.current().select(GetPartyTrainingClassesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPartyTrainingClass(UserVisitPK userVisitPK, EditPartyTrainingClassForm form) {
        return CDI.current().select(EditPartyTrainingClassCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyTrainingClass(UserVisitPK userVisitPK, DeletePartyTrainingClassForm form) {
        return CDI.current().select(DeletePartyTrainingClassCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Party Training Class Sessions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult getPartyTrainingClassSession(UserVisitPK userVisitPK, GetPartyTrainingClassSessionForm form) {
        return CDI.current().select(GetPartyTrainingClassSessionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyTrainingClassSessions(UserVisitPK userVisitPK, GetPartyTrainingClassSessionsForm form) {
        return CDI.current().select(GetPartyTrainingClassSessionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyTrainingClassSession(UserVisitPK userVisitPK, DeletePartyTrainingClassSessionForm form) {
        return CDI.current().select(DeletePartyTrainingClassSessionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Party Training Class Session Pages
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult editPartyTrainingClassSessionPage(UserVisitPK userVisitPK, EditPartyTrainingClassSessionPageForm form) {
        return CDI.current().select(EditPartyTrainingClassSessionPageCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Party Training Class Session Answers
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult editPartyTrainingClassSessionAnswer(UserVisitPK userVisitPK, EditPartyTrainingClassSessionAnswerForm form) {
        return CDI.current().select(EditPartyTrainingClassSessionAnswerCommand.class).get().run(userVisitPK, form);
    }

}

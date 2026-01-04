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

package com.echothree.model.control.training.server.logic;

import com.echothree.model.control.training.common.exception.UnknownTrainingClassNameException;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.model.data.training.server.entity.TrainingClassAnswer;
import com.echothree.model.data.training.server.entity.TrainingClassPage;
import com.echothree.model.data.training.server.entity.TrainingClassQuestion;
import com.echothree.model.data.training.server.entity.TrainingClassSection;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class TrainingClassLogic
        extends BaseLogic {

    protected TrainingClassLogic() {
        super();
    }

    public static TrainingClassLogic getInstance() {
        return CDI.current().select(TrainingClassLogic.class).get();
    }
    
    private TrainingClass getTrainingClassByName(final ExecutionErrorAccumulator eea, final String trainingClassName, EntityPermission entityPermission) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        var trainingClass = trainingControl.getTrainingClassByName(trainingClassName, entityPermission);

        if(trainingClass == null) {
            handleExecutionError(UnknownTrainingClassNameException.class, eea, ExecutionErrors.UnknownTrainingClassName.name(), trainingClassName);
        }

        return trainingClass;
    }
    
    public TrainingClass getTrainingClassByName(final ExecutionErrorAccumulator eea, final String trainingClassName) {
        return getTrainingClassByName(eea, trainingClassName, EntityPermission.READ_ONLY);
    }
    
    public TrainingClass getTrainingClassByNameForUpdate(final ExecutionErrorAccumulator eea, final String trainingClassName) {
        return getTrainingClassByName(eea, trainingClassName, EntityPermission.READ_WRITE);
    }
    
    private TrainingClassSection getTrainingClassSectionByName(final ExecutionErrorAccumulator eea, final TrainingClass trainingClass,
            final String trainingClassSectionName, EntityPermission entityPermission) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        var trainingClassSection = trainingControl.getTrainingClassSectionByName(trainingClass, trainingClassSectionName, entityPermission);

        if(trainingClassSection == null) {
            var trainingClassDetail = trainingClass.getLastDetail();
            
            handleExecutionError(UnknownTrainingClassNameException.class, eea, ExecutionErrors.UnknownTrainingClassSectionName.name(),
                    trainingClassDetail.getTrainingClassName(), trainingClassSectionName);
        }

        return trainingClassSection;
    }
    
    public TrainingClassSection getTrainingClassSectionByName(final ExecutionErrorAccumulator eea, final TrainingClass trainingClass,
            final String trainingClassSectionName) {
        return getTrainingClassSectionByName(eea, trainingClass, trainingClassSectionName, EntityPermission.READ_ONLY);
    }
    
    public TrainingClassSection getTrainingClassSectionByNameForUpdate(final ExecutionErrorAccumulator eea, final TrainingClass trainingClass,
            final String trainingClassSectionName) {
        return getTrainingClassSectionByName(eea, trainingClass, trainingClassSectionName, EntityPermission.READ_WRITE);
    }
    
    private TrainingClassPage getTrainingClassPageByName(final ExecutionErrorAccumulator eea, final TrainingClassSection trainingClassSection,
            final String trainingClassPageName, EntityPermission entityPermission) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        var trainingClassPage = trainingControl.getTrainingClassPageByName(trainingClassSection, trainingClassPageName, entityPermission);

        if(trainingClassPage == null) {
            var trainingClassSectionDetail = trainingClassSection.getLastDetail();
            var trainingClassDetail = trainingClassSectionDetail.getTrainingClass().getLastDetail();
            
            handleExecutionError(UnknownTrainingClassNameException.class, eea, ExecutionErrors.UnknownTrainingClassPageName.name(),
                    trainingClassDetail.getTrainingClassName(), trainingClassSectionDetail.getTrainingClassSectionName(), trainingClassPageName);
        }

        return trainingClassPage;
    }
    
    public TrainingClassPage getTrainingClassPageByName(final ExecutionErrorAccumulator eea, final TrainingClassSection trainingClassSection,
            final String trainingClassPageName) {
        return getTrainingClassPageByName(eea, trainingClassSection, trainingClassPageName, EntityPermission.READ_ONLY);
    }
    
    public TrainingClassPage getTrainingClassPageByNameForUpdate(final ExecutionErrorAccumulator eea, final TrainingClassSection trainingClassSection,
            final String trainingClassPageName) {
        return getTrainingClassPageByName(eea, trainingClassSection, trainingClassPageName, EntityPermission.READ_WRITE);
    }
    
    private TrainingClassQuestion getTrainingClassQuestionByName(final ExecutionErrorAccumulator eea, final TrainingClassSection trainingClassSection,
            final String trainingClassQuestionName, EntityPermission entityPermission) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        var trainingClassQuestion = trainingControl.getTrainingClassQuestionByName(trainingClassSection, trainingClassQuestionName, entityPermission);

        if(trainingClassQuestion == null) {
            var trainingClassSectionDetail = trainingClassSection.getLastDetail();
            var trainingClassDetail = trainingClassSectionDetail.getTrainingClass().getLastDetail();
            
            handleExecutionError(UnknownTrainingClassNameException.class, eea, ExecutionErrors.UnknownTrainingClassQuestionName.name(),
                    trainingClassDetail.getTrainingClassName(), trainingClassSectionDetail.getTrainingClassSectionName(), trainingClassQuestionName);
        }

        return trainingClassQuestion;
    }
    
    public TrainingClassQuestion getTrainingClassQuestionByName(final ExecutionErrorAccumulator eea, final TrainingClassSection trainingClassSection,
            final String trainingClassQuestionName) {
        return getTrainingClassQuestionByName(eea, trainingClassSection, trainingClassQuestionName, EntityPermission.READ_ONLY);
    }
    
    public TrainingClassQuestion getTrainingClassQuestionByNameForUpdate(final ExecutionErrorAccumulator eea, final TrainingClassSection trainingClassSection,
            final String trainingClassQuestionName) {
        return getTrainingClassQuestionByName(eea, trainingClassSection, trainingClassQuestionName, EntityPermission.READ_WRITE);
    }
    
    private TrainingClassAnswer getTrainingClassAnswerByName(final ExecutionErrorAccumulator eea, final TrainingClassQuestion trainingClassQuestion,
            final String trainingClassAnswerName, EntityPermission entityPermission) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        var trainingClassAnswer = trainingControl.getTrainingClassAnswerByName(trainingClassQuestion, trainingClassAnswerName, entityPermission);

        if(trainingClassAnswer == null) {
            var trainingClassQuestionDetail = trainingClassQuestion.getLastDetail();
            var trainingClassSectionDetail = trainingClassQuestionDetail.getTrainingClassSection().getLastDetail();
            var trainingClassDetail = trainingClassSectionDetail.getTrainingClass().getLastDetail();
            
            handleExecutionError(UnknownTrainingClassNameException.class, eea, ExecutionErrors.UnknownTrainingClassAnswerName.name(),
                    trainingClassDetail.getTrainingClassName(), trainingClassSectionDetail.getTrainingClassSectionName(),
                    trainingClassQuestionDetail.getTrainingClassQuestionName(), trainingClassAnswerName);
        }

        return trainingClassAnswer;
    }
    
    public TrainingClassAnswer getTrainingClassAnswerByName(final ExecutionErrorAccumulator eea, final TrainingClassQuestion trainingClassQuestion,
            final String trainingClassAnswerName) {
        return getTrainingClassAnswerByName(eea, trainingClassQuestion, trainingClassAnswerName, EntityPermission.READ_ONLY);
    }
    
    public TrainingClassAnswer getTrainingClassAnswerByNameForUpdate(final ExecutionErrorAccumulator eea, final TrainingClassQuestion trainingClassQuestion,
            final String trainingClassAnswerName) {
        return getTrainingClassAnswerByName(eea, trainingClassQuestion, trainingClassAnswerName, EntityPermission.READ_WRITE);
    }
    
}

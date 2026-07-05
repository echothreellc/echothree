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

import com.echothree.model.control.training.common.exception.UnknownTrainingClassAnswerNameException;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.training.server.entity.TrainingClassAnswer;
import com.echothree.model.data.training.server.entity.TrainingClassQuestion;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class TrainingClassAnswerLogic
        extends BaseLogic {

    @Inject
    private TrainingControl trainingControl;

    protected TrainingClassAnswerLogic() {
        super();
    }

    public static TrainingClassAnswerLogic getInstance() {
        return CDI.current().select(TrainingClassAnswerLogic.class).get();
    }

    private TrainingClassAnswer getTrainingClassAnswerByName(final ExecutionErrorAccumulator eea, final TrainingClassQuestion trainingClassQuestion,
            final String trainingClassAnswerName, EntityPermission entityPermission) {
        var trainingClassAnswer = trainingControl.getTrainingClassAnswerByName(trainingClassQuestion, trainingClassAnswerName, entityPermission);

        if(trainingClassAnswer == null) {
            var trainingClassQuestionDetail = trainingClassQuestion.getLastDetail();
            var trainingClassSectionDetail = trainingClassQuestionDetail.getTrainingClassSection().getLastDetail();
            var trainingClassDetail = trainingClassSectionDetail.getTrainingClass().getLastDetail();

            handleExecutionError(UnknownTrainingClassAnswerNameException.class, eea, ExecutionErrors.UnknownTrainingClassAnswerName.name(),
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

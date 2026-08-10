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

import com.echothree.model.control.training.common.exception.UnknownTrainingClassQuestionNameException;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.training.server.entity.TrainingClassQuestion;
import com.echothree.model.data.training.server.entity.TrainingClassSection;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class TrainingClassQuestionLogic
        extends BaseLogic {

    @Inject
    private TrainingControl trainingControl;

    protected TrainingClassQuestionLogic() {
        super();
    }

    public static TrainingClassQuestionLogic getInstance() {
        return CDI.current().select(TrainingClassQuestionLogic.class).get();
    }

    private TrainingClassQuestion getTrainingClassQuestionByName(final ExecutionErrorAccumulator eea, final TrainingClassSection trainingClassSection,
            final String trainingClassQuestionName, EntityPermission entityPermission) {
        var trainingClassQuestion = trainingControl.getTrainingClassQuestionByName(trainingClassSection, trainingClassQuestionName, entityPermission);

        if(trainingClassQuestion == null) {
            var trainingClassSectionDetail = trainingClassSection.getLastDetail();
            var trainingClassDetail = trainingClassSectionDetail.getTrainingClass().getLastDetail();

            handleExecutionError(UnknownTrainingClassQuestionNameException.class, eea, ExecutionErrors.UnknownTrainingClassQuestionName.name(),
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

}

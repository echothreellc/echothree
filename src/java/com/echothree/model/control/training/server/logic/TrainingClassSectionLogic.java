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

import com.echothree.model.control.training.common.exception.UnknownTrainingClassSectionNameException;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.model.data.training.server.entity.TrainingClassSection;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class TrainingClassSectionLogic
        extends BaseLogic {

    @Inject
    private TrainingControl trainingControl;

    protected TrainingClassSectionLogic() {
        super();
    }

    public static TrainingClassSectionLogic getInstance() {
        return CDI.current().select(TrainingClassSectionLogic.class).get();
    }

    private TrainingClassSection getTrainingClassSectionByName(final ExecutionErrorAccumulator eea, final TrainingClass trainingClass,
            final String trainingClassSectionName, EntityPermission entityPermission) {
        var trainingClassSection = trainingControl.getTrainingClassSectionByName(trainingClass, trainingClassSectionName, entityPermission);

        if(trainingClassSection == null) {
            var trainingClassDetail = trainingClass.getLastDetail();

            handleExecutionError(UnknownTrainingClassSectionNameException.class, eea, ExecutionErrors.UnknownTrainingClassSectionName.name(),
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

}

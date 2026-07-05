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
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class TrainingClassLogic
        extends BaseLogic {

    @Inject
    private TrainingControl trainingControl;

    protected TrainingClassLogic() {
        super();
    }

    public static TrainingClassLogic getInstance() {
        return CDI.current().select(TrainingClassLogic.class).get();
    }
    
    private TrainingClass getTrainingClassByName(final ExecutionErrorAccumulator eea, final String trainingClassName, EntityPermission entityPermission) {
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
    
}

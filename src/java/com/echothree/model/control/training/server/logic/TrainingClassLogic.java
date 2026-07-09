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

import com.echothree.control.user.training.common.spec.TrainingClassUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.training.common.exception.CannotDeleteTrainingClassInUseException;
import com.echothree.model.control.training.common.exception.DuplicateTrainingClassNameException;
import com.echothree.model.control.training.common.exception.UnknownDefaultTrainingClassException;
import com.echothree.model.control.training.common.exception.UnknownTrainingClassNameException;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScope;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
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

    public TrainingClass createTrainingClass(final ExecutionErrorAccumulator eea, final String trainingClassName,
            final Long estimatedReadingTime, final Long readingTimeAllowed, final Long estimatedTestingTime,
            final Long testingTimeAllowed, final Long requiredCompletionTime, final WorkEffortScope workEffortScope,
            final Integer defaultPercentageToPass, final Integer overallQuestionCount, final Long testingValidityTime,
            final Long expiredRetentionTime, final Boolean alwaysReassignOnExpiration, final Boolean isDefault,
            final Integer sortOrder, final Language language, final String description,
            final MimeType overviewMimeType, final String overview,
            final MimeType introductionMimeType, final String introduction,
            final BasePK createdBy) {
        var trainingClass = trainingControl.getTrainingClassByName(trainingClassName);

        if(trainingClass == null) {
            trainingClass = trainingControl.createTrainingClass(trainingClassName, estimatedReadingTime,
                    readingTimeAllowed, estimatedTestingTime, testingTimeAllowed, requiredCompletionTime,
                    workEffortScope, defaultPercentageToPass, overallQuestionCount, testingValidityTime,
                    expiredRetentionTime, alwaysReassignOnExpiration, isDefault, sortOrder, createdBy);

            if(description != null || overview != null || introduction != null) {
                trainingControl.createTrainingClassTranslation(trainingClass, language, description,
                        overviewMimeType, overview, introductionMimeType, introduction, createdBy);
            }
        } else {
            handleExecutionError(DuplicateTrainingClassNameException.class, eea, ExecutionErrors.DuplicateTrainingClassName.name(), trainingClassName);
        }

        return trainingClass;
    }

    private TrainingClass getTrainingClassByName(final ExecutionErrorAccumulator eea, final String trainingClassName,
            final boolean allowDefault, final EntityPermission entityPermission) {
        TrainingClass trainingClass = null;

        if(trainingClassName == null) {
            if(allowDefault) {
                trainingClass = trainingControl.getDefaultTrainingClass(entityPermission);

                if(trainingClass == null) {
                    handleExecutionError(UnknownDefaultTrainingClassException.class, eea, ExecutionErrors.UnknownDefaultTrainingClass.name());
                }
            } else {
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
            }
        } else {
            trainingClass = trainingControl.getTrainingClassByName(trainingClassName, entityPermission);

            if(trainingClass == null) {
                handleExecutionError(UnknownTrainingClassNameException.class, eea, ExecutionErrors.UnknownTrainingClassName.name(), trainingClassName);
            }
        }

        return trainingClass;
    }

    public TrainingClass getTrainingClassByName(final ExecutionErrorAccumulator eea, final String trainingClassName,
            final boolean allowDefault) {
        return getTrainingClassByName(eea, trainingClassName, allowDefault, EntityPermission.READ_ONLY);
    }

    public TrainingClass getTrainingClassByNameForUpdate(final ExecutionErrorAccumulator eea, final String trainingClassName,
            final boolean allowDefault) {
        return getTrainingClassByName(eea, trainingClassName, allowDefault, EntityPermission.READ_WRITE);
    }

    public TrainingClass getTrainingClassByUniversalSpec(final ExecutionErrorAccumulator eea,
            final TrainingClassUniversalSpec universalSpec, final boolean allowDefault, final EntityPermission entityPermission) {
        TrainingClass trainingClass = null;
        var trainingClassName = universalSpec.getTrainingClassName();
        var parameterCount = (trainingClassName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> trainingClass = getTrainingClassByName(eea, null, allowDefault, entityPermission);
            case 1 -> {
                if(trainingClassName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.TrainingClass.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        trainingClass = trainingControl.getTrainingClassByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    trainingClass = getTrainingClassByName(eea, trainingClassName, false, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return trainingClass;
    }

    public TrainingClass getTrainingClassByUniversalSpec(final ExecutionErrorAccumulator eea,
            final TrainingClassUniversalSpec universalSpec, final boolean allowDefault) {
        return getTrainingClassByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public TrainingClass getTrainingClassByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final TrainingClassUniversalSpec universalSpec, final boolean allowDefault) {
        return getTrainingClassByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteTrainingClass(final ExecutionErrorAccumulator eea, final TrainingClass trainingClass,
            final BasePK deletedBy) {
        var trainingClassSectionCount = trainingControl.countTrainingClassSectionsByTrainingClass(trainingClass);

        if(trainingClassSectionCount == 0) {
            trainingControl.deleteTrainingClass(trainingClass, deletedBy);
        } else {
            handleExecutionError(CannotDeleteTrainingClassInUseException.class, eea, ExecutionErrors.CannotDeleteTrainingClassInUse.name(),
                    trainingClass.getLastDetail().getTrainingClassName());
        }
    }
    
}

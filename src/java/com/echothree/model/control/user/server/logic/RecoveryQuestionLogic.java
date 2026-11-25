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

package com.echothree.model.control.user.server.logic;

import com.echothree.control.user.user.common.spec.RecoveryQuestionUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.user.common.exception.UnknownRecoveryQuestionNameException;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.user.server.entity.RecoveryQuestion;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class RecoveryQuestionLogic
        extends BaseLogic {

    @Inject
    UserControl userControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    protected RecoveryQuestionLogic() {
        super();
    }

    public RecoveryQuestion getRecoveryQuestionByName(final ExecutionErrorAccumulator eea, final String recoveryQuestionName,
            final EntityPermission entityPermission) {
        var recoveryQuestion = userControl.getRecoveryQuestionByName(recoveryQuestionName, entityPermission);

        if(recoveryQuestion == null) {
            handleExecutionError(UnknownRecoveryQuestionNameException.class, eea, ExecutionErrors.UnknownRecoveryQuestionName.name(), recoveryQuestionName);
        }

        return recoveryQuestion;
    }

    public RecoveryQuestion getRecoveryQuestionByName(final ExecutionErrorAccumulator eea, final String recoveryQuestionName) {
        return getRecoveryQuestionByName(eea, recoveryQuestionName, EntityPermission.READ_ONLY);
    }

    public RecoveryQuestion getRecoveryQuestionByNameForUpdate(final ExecutionErrorAccumulator eea, final String recoveryQuestionName) {
        return getRecoveryQuestionByName(eea, recoveryQuestionName, EntityPermission.READ_WRITE);
    }

    public RecoveryQuestion getRecoveryQuestionByUniversalSpec(final ExecutionErrorAccumulator eea,
            final RecoveryQuestionUniversalSpec universalSpec, final EntityPermission entityPermission) {
        RecoveryQuestion recoveryQuestion = null;
        var recoveryQuestionName = universalSpec.getRecoveryQuestionName();
        var parameterCount = (recoveryQuestionName == null ? 0 : 1) + entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        if(parameterCount == 1) {
            if(recoveryQuestionName == null) {
                var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                        ComponentVendors.ECHO_THREE.name(), EntityTypes.RecoveryQuestion.name());

                if(eea == null || !eea.hasExecutionErrors()) {
                    recoveryQuestion = userControl.getRecoveryQuestionByEntityInstance(entityInstance, entityPermission);
                }
            } else {
                recoveryQuestion = getRecoveryQuestionByName(eea, recoveryQuestionName, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return recoveryQuestion;
    }

    public RecoveryQuestion getRecoveryQuestionByUniversalSpec(final ExecutionErrorAccumulator eea,
            final RecoveryQuestionUniversalSpec universalSpec) {
        return getRecoveryQuestionByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public RecoveryQuestion getRecoveryQuestionByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final RecoveryQuestionUniversalSpec universalSpec) {
        return getRecoveryQuestionByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

}

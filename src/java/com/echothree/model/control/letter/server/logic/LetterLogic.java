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

package com.echothree.model.control.letter.server.logic;

import com.echothree.control.user.letter.common.spec.LetterUniversalSpec;
import com.echothree.model.control.chain.server.logic.ChainTypeLogic;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.letter.common.exception.UnknownLetterNameException;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.letter.server.entity.Letter;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class LetterLogic
        extends BaseLogic {

    @Inject
    LetterControl letterControl;

    @Inject
    ChainTypeLogic chainTypeLogic;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    protected LetterLogic() {
        super();
    }

    public Letter getLetterByName(final ExecutionErrorAccumulator eea, final ChainType chainType, final String letterName,
            final EntityPermission entityPermission) {
        var letter = letterControl.getLetterByName(chainType, letterName, entityPermission);

        if(letter == null) {
            var chainTypeDetail = chainType.getLastDetail();

            handleExecutionError(UnknownLetterNameException.class, eea, ExecutionErrors.UnknownLetterName.name(),
                    chainTypeDetail.getChainKind().getLastDetail().getChainKindName(), chainTypeDetail.getChainTypeName(), letterName);
        }

        return letter;
    }

    public Letter getLetterByName(final ExecutionErrorAccumulator eea, final ChainType chainType, final String letterName) {
        return getLetterByName(eea, chainType, letterName, EntityPermission.READ_ONLY);
    }

    public Letter getLetterByNameForUpdate(final ExecutionErrorAccumulator eea, final ChainType chainType, final String letterName) {
        return getLetterByName(eea, chainType, letterName, EntityPermission.READ_WRITE);
    }

    public Letter getLetterByName(final ExecutionErrorAccumulator eea, final String chainKindName, final String chainTypeName,
            final String letterName, final EntityPermission entityPermission) {
        var chainType = chainTypeLogic.getChainTypeByName(eea, chainKindName, chainTypeName);
        Letter letter = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            letter = getLetterByName(eea, chainType, letterName, entityPermission);
        }

        return letter;
    }

    public Letter getLetterByName(final ExecutionErrorAccumulator eea, final String chainKindName, final String chainTypeName,
            final String letterName) {
        return getLetterByName(eea, chainKindName, chainTypeName, letterName, EntityPermission.READ_ONLY);
    }

    public Letter getLetterByNameForUpdate(final ExecutionErrorAccumulator eea, final String chainKindName, final String chainTypeName,
            final String letterName) {
        return getLetterByName(eea, chainKindName, chainTypeName, letterName, EntityPermission.READ_WRITE);
    }

    public Letter getLetterByUniversalSpec(final ExecutionErrorAccumulator eea, final LetterUniversalSpec universalSpec,
            final EntityPermission entityPermission) {
        Letter letter = null;
        var chainKindName = universalSpec.getChainKindName();
        var chainTypeName = universalSpec.getChainTypeName();
        var letterName = universalSpec.getLetterName();
        var nameParameterCount = (chainKindName == null ? 0 : 1) + (chainTypeName == null ? 0 : 1) + (letterName == null ? 0 : 1);
        var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        if(nameParameterCount == 3 && possibleEntitySpecs == 0) {
            letter = getLetterByName(eea, chainKindName, chainTypeName, letterName, entityPermission);
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.Letter.name());

            if(!hasExecutionErrors(eea)) {
                letter = letterControl.getLetterByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return letter;
    }

    public Letter getLetterByUniversalSpec(final ExecutionErrorAccumulator eea, final LetterUniversalSpec universalSpec) {
        return getLetterByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public Letter getLetterByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final LetterUniversalSpec universalSpec) {
        return getLetterByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

}

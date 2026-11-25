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

import com.echothree.model.control.letter.common.exception.UnknownLetterContactMechanismPurposeException;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.data.letter.server.entity.Letter;
import com.echothree.model.data.letter.server.entity.LetterContactMechanismPurpose;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class LetterContactMechanismPurposeLogic
        extends BaseLogic {

    @Inject
    LetterControl letterControl;

    @Inject
    LetterLogic letterLogic;

    protected LetterContactMechanismPurposeLogic() {
        super();
    }

    public LetterContactMechanismPurpose getLetterContactMechanismPurpose(final ExecutionErrorAccumulator eea, final Letter letter,
            final Integer priority, final EntityPermission entityPermission) {
        var letterContactMechanismPurpose = letterControl.getLetterContactMechanismPurpose(letter, priority, entityPermission);

        if(letterContactMechanismPurpose == null) {
            var letterDetail = letter.getLastDetail();
            var chainTypeDetail = letterDetail.getChainType().getLastDetail();

            handleExecutionError(UnknownLetterContactMechanismPurposeException.class, eea,
                    ExecutionErrors.UnknownLetterContactMechanismPurpose.name(), chainTypeDetail.getChainKind().getLastDetail().getChainKindName(),
                    chainTypeDetail.getChainTypeName(), letterDetail.getLetterName(), priority.toString());
        }

        return letterContactMechanismPurpose;
    }

    public LetterContactMechanismPurpose getLetterContactMechanismPurpose(final ExecutionErrorAccumulator eea, final Letter letter,
            final Integer priority) {
        return getLetterContactMechanismPurpose(eea, letter, priority, EntityPermission.READ_ONLY);
    }

    public LetterContactMechanismPurpose getLetterContactMechanismPurposeForUpdate(final ExecutionErrorAccumulator eea, final Letter letter,
            final Integer priority) {
        return getLetterContactMechanismPurpose(eea, letter, priority, EntityPermission.READ_WRITE);
    }

    public LetterContactMechanismPurpose getLetterContactMechanismPurpose(final ExecutionErrorAccumulator eea, final String chainKindName,
            final String chainTypeName, final String letterName, final Integer priority, final EntityPermission entityPermission) {
        var letter = letterLogic.getLetterByName(eea, chainKindName, chainTypeName, letterName);
        LetterContactMechanismPurpose letterContactMechanismPurpose = null;

        if(!hasExecutionErrors(eea)) {
            letterContactMechanismPurpose = getLetterContactMechanismPurpose(eea, letter, priority, entityPermission);
        }

        return letterContactMechanismPurpose;
    }

    public LetterContactMechanismPurpose getLetterContactMechanismPurpose(final ExecutionErrorAccumulator eea, final String chainKindName,
            final String chainTypeName, final String letterName, final Integer priority) {
        return getLetterContactMechanismPurpose(eea, chainKindName, chainTypeName, letterName, priority, EntityPermission.READ_ONLY);
    }

    public LetterContactMechanismPurpose getLetterContactMechanismPurposeForUpdate(final ExecutionErrorAccumulator eea, final String chainKindName,
            final String chainTypeName, final String letterName, final Integer priority) {
        return getLetterContactMechanismPurpose(eea, chainKindName, chainTypeName, letterName, priority, EntityPermission.READ_WRITE);
    }

}

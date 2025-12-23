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

package com.echothree.model.control.term.server.logic;

import com.echothree.control.user.term.common.spec.TermTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.term.common.exception.DuplicateTermTypeNameException;
import com.echothree.model.control.term.common.exception.UnknownDefaultTermTypeException;
import com.echothree.model.control.term.common.exception.UnknownTermTypeNameException;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.term.server.entity.TermType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class TermTypeLogic
        extends BaseLogic {

    protected TermTypeLogic() {
        super();
    }

    public static TermTypeLogic getInstance() {
        return CDI.current().select(TermTypeLogic.class).get();
    }

    public TermType createTermType(final ExecutionErrorAccumulator eea, final String termTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var termControl = Session.getModelController(TermControl.class);
        var termType = termControl.getTermTypeByName(termTypeName);

        if(termType == null) {
            termType = termControl.createTermType(termTypeName, isDefault, sortOrder, createdBy);

            if(description != null) {
                termControl.createTermTypeDescription(termType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateTermTypeNameException.class, eea, ExecutionErrors.DuplicateTermTypeName.name(), termTypeName);
        }

        return termType;
    }

    public TermType getTermTypeByName(final ExecutionErrorAccumulator eea, final String termTypeName,
            final EntityPermission entityPermission) {
        var termControl = Session.getModelController(TermControl.class);
        var termType = termControl.getTermTypeByName(termTypeName, entityPermission);

        if(termType == null) {
            handleExecutionError(UnknownTermTypeNameException.class, eea, ExecutionErrors.UnknownTermTypeName.name(), termTypeName);
        }

        return termType;
    }

    public TermType getTermTypeByName(final ExecutionErrorAccumulator eea, final String termTypeName) {
        return getTermTypeByName(eea, termTypeName, EntityPermission.READ_ONLY);
    }

    public TermType getTermTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String termTypeName) {
        return getTermTypeByName(eea, termTypeName, EntityPermission.READ_WRITE);
    }

    public TermType getTermTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final TermTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        TermType termType = null;
        var termControl = Session.getModelController(TermControl.class);
        var termTypeName = universalSpec.getTermTypeName();
        var parameterCount = (termTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    termType = termControl.getDefaultTermType(entityPermission);

                    if(termType == null) {
                        handleExecutionError(UnknownDefaultTermTypeException.class, eea, ExecutionErrors.UnknownDefaultTermType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(termTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.TermType.name());

                    if(!eea.hasExecutionErrors()) {
                        termType = termControl.getTermTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    termType = getTermTypeByName(eea, termTypeName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return termType;
    }

    public TermType getTermTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final TermTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getTermTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public TermType getTermTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final TermTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getTermTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

}

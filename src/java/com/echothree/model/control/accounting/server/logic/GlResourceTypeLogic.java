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

package com.echothree.model.control.accounting.server.logic;

import com.echothree.control.user.accounting.common.spec.GlResourceTypeUniversalSpec;
import com.echothree.model.control.accounting.common.exception.DuplicateGlResourceTypeNameException;
import com.echothree.model.control.accounting.common.exception.UnknownDefaultGlResourceTypeException;
import com.echothree.model.control.accounting.common.exception.UnknownGlResourceTypeNameException;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.accounting.server.entity.GlResourceType;
import com.echothree.model.data.accounting.server.value.GlResourceTypeDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class GlResourceTypeLogic
        extends BaseLogic {

    @Inject
    AccountingControl accountingControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    protected GlResourceTypeLogic() {
        super();
    }

    public static GlResourceTypeLogic getInstance() {
        return CDI.current().select(GlResourceTypeLogic.class).get();
    }

    public GlResourceType createGlResourceType(final ExecutionErrorAccumulator eea, final String glResourceTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language,
            final String description, final BasePK createdBy) {
        var glResourceType = accountingControl.getGlResourceTypeByName(glResourceTypeName);

        if(glResourceType == null) {
            glResourceType = accountingControl.createGlResourceType(glResourceTypeName,
                    isDefault, sortOrder, createdBy);

            if(description != null) {
                accountingControl.createGlResourceTypeDescription(glResourceType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateGlResourceTypeNameException.class, eea, ExecutionErrors.DuplicateGlResourceTypeName.name(),
                    glResourceTypeName);
        }

        return glResourceType;
    }

    public GlResourceType getGlResourceTypeByName(final ExecutionErrorAccumulator eea, final String glResourceTypeName,
            final EntityPermission entityPermission) {
        var glResourceType = accountingControl.getGlResourceTypeByName(glResourceTypeName, entityPermission);

        if(glResourceType == null) {
            handleExecutionError(UnknownGlResourceTypeNameException.class, eea, ExecutionErrors.UnknownGlResourceTypeName.name(), glResourceTypeName);
        }

        return glResourceType;
    }

    public GlResourceType getGlResourceTypeByName(final ExecutionErrorAccumulator eea, final String glResourceTypeName) {
        return getGlResourceTypeByName(eea, glResourceTypeName, EntityPermission.READ_ONLY);
    }

    public GlResourceType getGlResourceTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String glResourceTypeName) {
        return getGlResourceTypeByName(eea, glResourceTypeName, EntityPermission.READ_WRITE);
    }

    public GlResourceType getGlResourceTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final GlResourceTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        GlResourceType glResourceType = null;
        var glResourceTypeName = universalSpec.getGlResourceTypeName();
        var parameterCount = (glResourceTypeName == null ? 0 : 1) + entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    glResourceType = accountingControl.getDefaultGlResourceType(entityPermission);

                    if(glResourceType == null) {
                        handleExecutionError(UnknownDefaultGlResourceTypeException.class, eea, ExecutionErrors.UnknownDefaultGlResourceType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(glResourceTypeName == null) {
                    var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.GlResourceType.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        glResourceType = accountingControl.getGlResourceTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    glResourceType = getGlResourceTypeByName(eea, glResourceTypeName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return glResourceType;
    }

    public GlResourceType getGlResourceTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final GlResourceTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getGlResourceTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public GlResourceType getGlResourceTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final GlResourceTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getGlResourceTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateGlResourceTypeFromValue(GlResourceTypeDetailValue glResourceTypeDetailValue, BasePK updatedBy) {
        accountingControl.updateGlResourceTypeFromValue(glResourceTypeDetailValue, updatedBy);
    }

    public void deleteGlResourceType(final ExecutionErrorAccumulator eea, final GlResourceType glResourceType, final BasePK deletedBy) {
        accountingControl.deleteGlResourceType(glResourceType, deletedBy);
    }

}

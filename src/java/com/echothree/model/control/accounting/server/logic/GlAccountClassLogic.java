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

import com.echothree.control.user.accounting.common.spec.GlAccountClassUniversalSpec;
import com.echothree.model.control.accounting.common.exception.DuplicateGlAccountClassNameException;
import com.echothree.model.control.accounting.common.exception.UnknownDefaultGlAccountClassException;
import com.echothree.model.control.accounting.common.exception.UnknownGlAccountClassNameException;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.accounting.server.entity.GlAccountClass;
import com.echothree.model.data.accounting.server.value.GlAccountClassDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class GlAccountClassLogic
        extends BaseLogic {

    protected GlAccountClassLogic() {
        super();
    }

    public static GlAccountClassLogic getInstance() {
        return CDI.current().select(GlAccountClassLogic.class).get();
    }

    public GlAccountClass createGlAccountClass(final ExecutionErrorAccumulator eea, final String glAccountClassName,
            final GlAccountClass parentGlAccountClass, final Boolean isDefault, final Integer sortOrder, final Language language,
            final String description, final BasePK createdBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var glAccountClass = accountingControl.getGlAccountClassByName(glAccountClassName);

        if(glAccountClass == null) {
            glAccountClass = accountingControl.createGlAccountClass(glAccountClassName,
                    parentGlAccountClass, isDefault, sortOrder, createdBy);

            if(description != null) {
                accountingControl.createGlAccountClassDescription(glAccountClass, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateGlAccountClassNameException.class, eea, ExecutionErrors.DuplicateGlAccountClassName.name(),
                    glAccountClassName);
        }

        return glAccountClass;
    }

    public GlAccountClass getGlAccountClassByName(final ExecutionErrorAccumulator eea, final String glAccountClassName,
            final EntityPermission entityPermission) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var glAccountClass = accountingControl.getGlAccountClassByName(glAccountClassName, entityPermission);

        if(glAccountClass == null) {
            handleExecutionError(UnknownGlAccountClassNameException.class, eea, ExecutionErrors.UnknownGlAccountClassName.name(), glAccountClassName);
        }

        return glAccountClass;
    }

    public GlAccountClass getGlAccountClassByName(final ExecutionErrorAccumulator eea, final String glAccountClassName) {
        return getGlAccountClassByName(eea, glAccountClassName, EntityPermission.READ_ONLY);
    }

    public GlAccountClass getGlAccountClassByNameForUpdate(final ExecutionErrorAccumulator eea, final String glAccountClassName) {
        return getGlAccountClassByName(eea, glAccountClassName, EntityPermission.READ_WRITE);
    }

    public GlAccountClass getGlAccountClassByUniversalSpec(final ExecutionErrorAccumulator eea,
            final GlAccountClassUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        GlAccountClass glAccountClass = null;
        var accountingControl = Session.getModelController(AccountingControl.class);
        var glAccountClassName = universalSpec.getGlAccountClassName();
        var parameterCount = (glAccountClassName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    glAccountClass = accountingControl.getDefaultGlAccountClass(entityPermission);

                    if(glAccountClass == null) {
                        handleExecutionError(UnknownDefaultGlAccountClassException.class, eea, ExecutionErrors.UnknownDefaultGlAccountClass.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(glAccountClassName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.GlAccountClass.name());

                    if(!eea.hasExecutionErrors()) {
                        glAccountClass = accountingControl.getGlAccountClassByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    glAccountClass = getGlAccountClassByName(eea, glAccountClassName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return glAccountClass;
    }

    public GlAccountClass getGlAccountClassByUniversalSpec(final ExecutionErrorAccumulator eea,
            final GlAccountClassUniversalSpec universalSpec, boolean allowDefault) {
        return getGlAccountClassByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public GlAccountClass getGlAccountClassByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final GlAccountClassUniversalSpec universalSpec, boolean allowDefault) {
        return getGlAccountClassByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateGlAccountClassFromValue(GlAccountClassDetailValue glAccountClassDetailValue, BasePK updatedBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);

        accountingControl.updateGlAccountClassFromValue(glAccountClassDetailValue, updatedBy);
    }

    public void deleteGlAccountClass(final ExecutionErrorAccumulator eea, final GlAccountClass glAccountClass, final BasePK deletedBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);

        accountingControl.deleteGlAccountClass(glAccountClass, deletedBy);
    }

}

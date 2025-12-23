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

import com.echothree.control.user.accounting.common.spec.GlAccountTypeUniversalSpec;
import com.echothree.model.control.accounting.common.exception.DuplicateGlAccountTypeNameException;
import com.echothree.model.control.accounting.common.exception.UnknownDefaultGlAccountTypeException;
import com.echothree.model.control.accounting.common.exception.UnknownGlAccountTypeNameException;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.accounting.server.entity.GlAccountType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class GlAccountTypeLogic
        extends BaseLogic {

    protected GlAccountTypeLogic() {
        super();
    }

    public static GlAccountTypeLogic getInstance() {
        return CDI.current().select(GlAccountTypeLogic.class).get();
    }

    public GlAccountType createGlAccountType(final ExecutionErrorAccumulator eea, final String glAccountTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language,
            final String description) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var glAccountType = accountingControl.getGlAccountTypeByName(glAccountTypeName);

        if(glAccountType == null) {
            glAccountType = accountingControl.createGlAccountType(glAccountTypeName,
                    isDefault, sortOrder);

            if(description != null) {
                accountingControl.createGlAccountTypeDescription(glAccountType, language, description);
            }
        } else {
            handleExecutionError(DuplicateGlAccountTypeNameException.class, eea, ExecutionErrors.DuplicateGlAccountTypeName.name(),
                    glAccountTypeName);
        }

        return glAccountType;
    }

    public GlAccountType getGlAccountTypeByName(final ExecutionErrorAccumulator eea, final String glAccountTypeName) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var glAccountType = accountingControl.getGlAccountTypeByName(glAccountTypeName);

        if(glAccountType == null) {
            handleExecutionError(UnknownGlAccountTypeNameException.class, eea, ExecutionErrors.UnknownGlAccountTypeName.name(), glAccountTypeName);
        }

        return glAccountType;
    }

    public GlAccountType getGlAccountTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final GlAccountTypeUniversalSpec universalSpec, boolean allowDefault) {
        GlAccountType glAccountType = null;
        var accountingControl = Session.getModelController(AccountingControl.class);
        var glAccountTypeName = universalSpec.getGlAccountTypeName();
        var parameterCount = (glAccountTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    glAccountType = accountingControl.getDefaultGlAccountType();

                    if(glAccountType == null) {
                        handleExecutionError(UnknownDefaultGlAccountTypeException.class, eea, ExecutionErrors.UnknownDefaultGlAccountType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(glAccountTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.GlAccountType.name());

                    if(!eea.hasExecutionErrors()) {
                        glAccountType = accountingControl.getGlAccountTypeByEntityInstance(entityInstance);
                    }
                } else {
                    glAccountType = getGlAccountTypeByName(eea, glAccountTypeName);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return glAccountType;
    }

}

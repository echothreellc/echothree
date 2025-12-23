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

import com.echothree.control.user.accounting.common.spec.GlAccountCategoryUniversalSpec;
import com.echothree.model.control.accounting.common.exception.DuplicateGlAccountCategoryNameException;
import com.echothree.model.control.accounting.common.exception.UnknownDefaultGlAccountCategoryException;
import com.echothree.model.control.accounting.common.exception.UnknownGlAccountCategoryNameException;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.accounting.server.entity.GlAccountCategory;
import com.echothree.model.data.accounting.server.value.GlAccountCategoryDetailValue;
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
public class GlAccountCategoryLogic
        extends BaseLogic {

    protected GlAccountCategoryLogic() {
        super();
    }

    public static GlAccountCategoryLogic getInstance() {
        return CDI.current().select(GlAccountCategoryLogic.class).get();
    }

    public GlAccountCategory createGlAccountCategory(final ExecutionErrorAccumulator eea, final String glAccountCategoryName,
            final GlAccountCategory parentGlAccountCategory, final Boolean isDefault, final Integer sortOrder, final Language language,
            final String description, final BasePK createdBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var glAccountCategory = accountingControl.getGlAccountCategoryByName(glAccountCategoryName);

        if(glAccountCategory == null) {
            glAccountCategory = accountingControl.createGlAccountCategory(glAccountCategoryName,
                    parentGlAccountCategory, isDefault, sortOrder, createdBy);

            if(description != null) {
                accountingControl.createGlAccountCategoryDescription(glAccountCategory, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateGlAccountCategoryNameException.class, eea, ExecutionErrors.DuplicateGlAccountCategoryName.name(),
                    glAccountCategoryName);
        }

        return glAccountCategory;
    }

    public GlAccountCategory getGlAccountCategoryByName(final ExecutionErrorAccumulator eea, final String glAccountCategoryName,
            final EntityPermission entityPermission) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var glAccountCategory = accountingControl.getGlAccountCategoryByName(glAccountCategoryName, entityPermission);

        if(glAccountCategory == null) {
            handleExecutionError(UnknownGlAccountCategoryNameException.class, eea, ExecutionErrors.UnknownGlAccountCategoryName.name(), glAccountCategoryName);
        }

        return glAccountCategory;
    }

    public GlAccountCategory getGlAccountCategoryByName(final ExecutionErrorAccumulator eea, final String glAccountCategoryName) {
        return getGlAccountCategoryByName(eea, glAccountCategoryName, EntityPermission.READ_ONLY);
    }

    public GlAccountCategory getGlAccountCategoryByNameForUpdate(final ExecutionErrorAccumulator eea, final String glAccountCategoryName) {
        return getGlAccountCategoryByName(eea, glAccountCategoryName, EntityPermission.READ_WRITE);
    }

    public GlAccountCategory getGlAccountCategoryByUniversalSpec(final ExecutionErrorAccumulator eea,
            final GlAccountCategoryUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        GlAccountCategory glAccountCategory = null;
        var accountingControl = Session.getModelController(AccountingControl.class);
        var glAccountCategoryName = universalSpec.getGlAccountCategoryName();
        var parameterCount = (glAccountCategoryName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    glAccountCategory = accountingControl.getDefaultGlAccountCategory(entityPermission);

                    if(glAccountCategory == null) {
                        handleExecutionError(UnknownDefaultGlAccountCategoryException.class, eea, ExecutionErrors.UnknownDefaultGlAccountCategory.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(glAccountCategoryName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.GlAccountCategory.name());

                    if(!eea.hasExecutionErrors()) {
                        glAccountCategory = accountingControl.getGlAccountCategoryByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    glAccountCategory = getGlAccountCategoryByName(eea, glAccountCategoryName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return glAccountCategory;
    }

    public GlAccountCategory getGlAccountCategoryByUniversalSpec(final ExecutionErrorAccumulator eea,
            final GlAccountCategoryUniversalSpec universalSpec, boolean allowDefault) {
        return getGlAccountCategoryByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public GlAccountCategory getGlAccountCategoryByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final GlAccountCategoryUniversalSpec universalSpec, boolean allowDefault) {
        return getGlAccountCategoryByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateGlAccountCategoryFromValue(GlAccountCategoryDetailValue glAccountCategoryDetailValue, BasePK updatedBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);

        accountingControl.updateGlAccountCategoryFromValue(glAccountCategoryDetailValue, updatedBy);
    }

    public void deleteGlAccountCategory(final ExecutionErrorAccumulator eea, final GlAccountCategory glAccountCategory, final BasePK deletedBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);

        accountingControl.deleteGlAccountCategory(glAccountCategory, deletedBy);
    }

}

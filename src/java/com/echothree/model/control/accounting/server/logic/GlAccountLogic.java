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

import com.echothree.control.user.accounting.common.spec.GlAccountUniversalSpec;
import com.echothree.model.control.accounting.common.exception.DuplicateGlAccountNameException;
import com.echothree.model.control.accounting.common.exception.UnknownDefaultGlAccountException;
import com.echothree.model.control.accounting.common.exception.UnknownGlAccountNameException;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.accounting.server.entity.GlAccountCategory;
import com.echothree.model.data.accounting.server.entity.GlAccountClass;
import com.echothree.model.data.accounting.server.entity.GlAccountType;
import com.echothree.model.data.accounting.server.entity.GlResourceType;
import com.echothree.model.data.accounting.server.value.GlAccountDetailValue;
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
public class GlAccountLogic
        extends BaseLogic {

    protected GlAccountLogic() {
        super();
    }

    public static GlAccountLogic getInstance() {
        return CDI.current().select(GlAccountLogic.class).get();
    }

    public GlAccount createGlAccount(final ExecutionErrorAccumulator eea, final String glAccountName,
            final GlAccount parentGlAccount, final GlAccountType glAccountType, final GlAccountClass glAccountClass,
            final GlAccountCategory glAccountCategory, final GlResourceType glResourceType, final Currency currency,
            final Boolean isDefault, final Language language, final String description, final BasePK createdBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var glAccount = accountingControl.getGlAccountByName(glAccountName);

        if(glAccount == null) {
            glAccount = accountingControl.createGlAccount(glAccountName, parentGlAccount, glAccountType,
                    glAccountClass, glAccountCategory, glResourceType, currency, isDefault, createdBy);

            if(description != null) {
                accountingControl.createGlAccountDescription(glAccount, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateGlAccountNameException.class, eea, ExecutionErrors.DuplicateGlAccountName.name(),
                    glAccountName);
        }

        return glAccount;
    }

    public GlAccount getGlAccountByName(final ExecutionErrorAccumulator eea, final String glAccountName,
            final EntityPermission entityPermission) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var glAccount = accountingControl.getGlAccountByName(glAccountName, entityPermission);

        if(glAccount == null) {
            handleExecutionError(UnknownGlAccountNameException.class, eea, ExecutionErrors.UnknownGlAccountName.name(), glAccountName);
        }

        return glAccount;
    }

    public GlAccount getGlAccountByName(final ExecutionErrorAccumulator eea, final String glAccountName) {
        return getGlAccountByName(eea, glAccountName, EntityPermission.READ_ONLY);
    }

    public GlAccount getGlAccountByNameForUpdate(final ExecutionErrorAccumulator eea, final String glAccountName) {
        return getGlAccountByName(eea, glAccountName, EntityPermission.READ_WRITE);
    }

    public GlAccount getGlAccountByUniversalSpec(final ExecutionErrorAccumulator eea, final GlAccountUniversalSpec universalSpec,
            final boolean allowDefault, final GlAccountCategory glAccountCategory, final EntityPermission entityPermission) {
        GlAccount glAccount = null;
        var accountingControl = Session.getModelController(AccountingControl.class);
        var glAccountName = universalSpec.getGlAccountName();
        var parameterCount = (glAccountName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault && glAccountCategory != null) {
                    glAccount = accountingControl.getDefaultGlAccount(glAccountCategory, entityPermission);

                    if(glAccount == null) {
                        handleExecutionError(UnknownDefaultGlAccountException.class, eea, ExecutionErrors.UnknownDefaultGlAccount.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(glAccountName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.GlAccount.name());

                    if(!eea.hasExecutionErrors()) {
                        glAccount = accountingControl.getGlAccountByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    glAccount = getGlAccountByName(eea, glAccountName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return glAccount;
    }

    public GlAccount getGlAccountByUniversalSpec(final ExecutionErrorAccumulator eea, final GlAccountUniversalSpec universalSpec,
            final boolean allowDefault, final GlAccountCategory glAccountCategory) {
        return getGlAccountByUniversalSpec(eea, universalSpec, allowDefault, glAccountCategory, EntityPermission.READ_ONLY);
    }

    public GlAccount getGlAccountByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final GlAccountUniversalSpec universalSpec,
            final boolean allowDefault, final GlAccountCategory glAccountCategory) {
        return getGlAccountByUniversalSpec(eea, universalSpec, allowDefault, glAccountCategory, EntityPermission.READ_WRITE);
    }

    /**
     * Obtain a GL Account fitting within a given GL Account Category.
     *
     * @param eea The ExecutionErrorAccumulator that any errors that are encountered should be added to.
     * @param glAccounts A list of GlAccounts that will be drawn from to find a result.
     * @param glAccountCategoryName If no glAccount is found in glAccounts, this will be used to find a default.
     * @param unknownDefaultGlAccountError If no default is found, this error will be added to the ExecutionErrorAccumulator.
     * @return The glAccount to use, unless null.
     */
    public GlAccount getDefaultGlAccountByCategory(final ExecutionErrorAccumulator eea, final GlAccount[] glAccounts,
            final String glAccountCategoryName, final String unknownDefaultGlAccountError) {
        GlAccount glAccount = null;

        for(var i = 0; glAccount == null && i < glAccounts.length ; i++) {
            glAccount = glAccounts[i];
        }

        if(glAccount == null) {
            var glAccountCategory = GlAccountCategoryLogic.getInstance().getGlAccountCategoryByName(eea, glAccountCategoryName);

            if(eea == null || !eea.hasExecutionErrors()) {
                var accountingControl = Session.getModelController(AccountingControl.class);

                glAccount = accountingControl.getDefaultGlAccount(glAccountCategory);

                if(glAccount == null) {
                    eea.addExecutionError(unknownDefaultGlAccountError);
                }
            }
        }

        return glAccount;
    }

    public void updateGlAccountFromValue(GlAccountDetailValue glAccountDetailValue, BasePK updatedBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);

        accountingControl.updateGlAccountFromValue(glAccountDetailValue, updatedBy);
    }

    public void deleteGlAccount(final ExecutionErrorAccumulator eea, final GlAccount glAccount, final BasePK deletedBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);

        accountingControl.deleteGlAccount(glAccount, deletedBy);
    }

}

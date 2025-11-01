// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.control.user.accounting.common.spec.TransactionGlAccountCategoryUniversalSpec;
import com.echothree.model.control.accounting.common.exception.DuplicateTransactionGlAccountCategoryNameException;
import com.echothree.model.control.accounting.common.exception.UnknownTransactionGlAccountCategoryNameException;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.accounting.server.entity.GlAccountCategory;
import com.echothree.model.data.accounting.server.entity.TransactionGlAccountCategory;
import com.echothree.model.data.accounting.server.entity.TransactionType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.ParameterUtils;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class TransactionGlAccountCategoryLogic
        extends BaseLogic {

    protected TransactionGlAccountCategoryLogic() {
        super();
    }

    public static TransactionGlAccountCategoryLogic getInstance() {
        return CDI.current().select(TransactionGlAccountCategoryLogic.class).get();
    }

    public TransactionGlAccountCategory createTransactionGlAccountCategory(final ExecutionErrorAccumulator eea, final String transactionTypeName,
            final String transactionGlAccountCategoryName, final GlAccountCategory glAccountCategory, final Integer sortOrder,
            final Language language, final String description, final BasePK createdBy) {
        var transactionType = TransactionTypeLogic.getInstance().getTransactionTypeByName(eea, transactionTypeName);
        TransactionGlAccountCategory transactionGlAccountCategory = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            transactionGlAccountCategory = createTransactionGlAccountCategory(eea, transactionType, transactionGlAccountCategoryName,
                    glAccountCategory, sortOrder, language, description, createdBy);
        }

        return transactionGlAccountCategory;
    }

    public TransactionGlAccountCategory createTransactionGlAccountCategory(final ExecutionErrorAccumulator eea, final TransactionType transactionType,
            final String transactionGlAccountCategoryName, final GlAccountCategory glAccountCategory, final Integer sortOrder, final Language language,
            final String description, final BasePK createdBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var transactionGlAccountCategory = accountingControl.getTransactionGlAccountCategoryByName(transactionType, transactionGlAccountCategoryName);

        if(transactionGlAccountCategory == null) {
            transactionGlAccountCategory = accountingControl.createTransactionGlAccountCategory(transactionType, transactionGlAccountCategoryName,
                    glAccountCategory, sortOrder, createdBy);

            if(description != null) {
                accountingControl.createTransactionGlAccountCategoryDescription(transactionGlAccountCategory, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateTransactionGlAccountCategoryNameException.class, eea, ExecutionErrors.DuplicateTransactionGlAccountCategoryName.name(), transactionGlAccountCategoryName);
        }
        return transactionGlAccountCategory;
    }

    public TransactionGlAccountCategory getTransactionGlAccountCategoryByName(final ExecutionErrorAccumulator eea,
            final TransactionType transactionType, final String transactionGlAccountCategoryName,
            final EntityPermission entityPermission) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var transactionGlAccountCategory = accountingControl.getTransactionGlAccountCategoryByName(transactionType, transactionGlAccountCategoryName, entityPermission);

        if(transactionGlAccountCategory == null) {
            handleExecutionError(UnknownTransactionGlAccountCategoryNameException.class, eea, ExecutionErrors.UnknownTransactionGlAccountCategoryName.name(),
                    transactionType.getLastDetail().getTransactionTypeName(), transactionGlAccountCategoryName);
        }

        return transactionGlAccountCategory;
    }

    public TransactionGlAccountCategory getTransactionGlAccountCategoryByName(final ExecutionErrorAccumulator eea,
            final TransactionType transactionType, final String transactionGlAccountCategoryName) {
        return getTransactionGlAccountCategoryByName(eea, transactionType, transactionGlAccountCategoryName, EntityPermission.READ_ONLY);
    }

    public TransactionGlAccountCategory getTransactionGlAccountCategoryByNameForUpdate(final ExecutionErrorAccumulator eea,
            final TransactionType transactionType, final String transactionGlAccountCategoryName) {
        return getTransactionGlAccountCategoryByName(eea, transactionType, transactionGlAccountCategoryName, EntityPermission.READ_WRITE);
    }

    public TransactionGlAccountCategory getTransactionGlAccountCategoryByName(final ExecutionErrorAccumulator eea,
            final String transactionTypeName, final String transactionGlAccountCategoryName,
            final EntityPermission entityPermission) {
        var transactionType = TransactionTypeLogic.getInstance().getTransactionTypeByName(eea, transactionTypeName);
        TransactionGlAccountCategory transactionGlAccountCategory = null;

        if(!eea.hasExecutionErrors()) {
            transactionGlAccountCategory = getTransactionGlAccountCategoryByName(eea, transactionType, transactionGlAccountCategoryName, entityPermission);
        }

        return transactionGlAccountCategory;
    }

    public TransactionGlAccountCategory getTransactionGlAccountCategoryByName(final ExecutionErrorAccumulator eea,
            final String transactionTypeName, final String transactionGlAccountCategoryName) {
        return getTransactionGlAccountCategoryByName(eea, transactionTypeName, transactionGlAccountCategoryName, EntityPermission.READ_ONLY);
    }

    public TransactionGlAccountCategory getTransactionGlAccountCategoryByNameForUpdate(final ExecutionErrorAccumulator eea,
            final String transactionTypeName, final String transactionGlAccountCategoryName) {
        return getTransactionGlAccountCategoryByName(eea, transactionTypeName, transactionGlAccountCategoryName, EntityPermission.READ_WRITE);
    }

    public TransactionGlAccountCategory getTransactionGlAccountCategoryByUniversalSpec(final ExecutionErrorAccumulator eea,
            final TransactionGlAccountCategoryUniversalSpec universalSpec, final EntityPermission entityPermission) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var transactionTypeName = universalSpec.getTransactionTypeName();
        var transactionGlAccountCategoryName = universalSpec.getTransactionGlAccountCategoryName();
        var nameParameterCount= ParameterUtils.getInstance().countNonNullParameters(transactionTypeName, transactionGlAccountCategoryName);
        var possibleEntitySpecs= EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        TransactionGlAccountCategory transactionGlAccountCategory = null;

        if(nameParameterCount == 2 && possibleEntitySpecs == 0) {
            var transactionType = TransactionTypeLogic.getInstance().getTransactionTypeByName(eea, transactionTypeName);

            if(!eea.hasExecutionErrors()) {
                transactionGlAccountCategory = getTransactionGlAccountCategoryByName(eea, transactionType, transactionGlAccountCategoryName, entityPermission);
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.TransactionGlAccountCategory.name());

            if(!eea.hasExecutionErrors()) {
                transactionGlAccountCategory = accountingControl.getTransactionGlAccountCategoryByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return transactionGlAccountCategory;
    }

    public TransactionGlAccountCategory getTransactionGlAccountCategoryByUniversalSpec(final ExecutionErrorAccumulator eea,
            final TransactionGlAccountCategoryUniversalSpec universalSpec) {
        return getTransactionGlAccountCategoryByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public TransactionGlAccountCategory getTransactionGlAccountCategoryByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final TransactionGlAccountCategoryUniversalSpec universalSpec) {
        return getTransactionGlAccountCategoryByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

    public void deleteTransactionGlAccountCategory(final ExecutionErrorAccumulator eea, final TransactionGlAccountCategory transactionGlAccountCategory,
            final BasePK deletedBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);

        accountingControl.deleteTransactionGlAccountCategory(transactionGlAccountCategory, deletedBy);
    }

}

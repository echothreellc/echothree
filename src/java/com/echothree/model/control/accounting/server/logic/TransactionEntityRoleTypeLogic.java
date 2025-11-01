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

import com.echothree.control.user.accounting.common.spec.TransactionEntityRoleTypeUniversalSpec;
import com.echothree.model.control.accounting.common.exception.DuplicateTransactionEntityRoleTypeNameException;
import com.echothree.model.control.accounting.common.exception.UnknownTransactionEntityRoleTypeNameException;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.accounting.server.entity.TransactionEntityRoleType;
import com.echothree.model.data.accounting.server.entity.TransactionType;
import com.echothree.model.data.core.server.entity.EntityType;
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
public class TransactionEntityRoleTypeLogic
        extends BaseLogic {

    protected TransactionEntityRoleTypeLogic() {
        super();
    }

    public static TransactionEntityRoleTypeLogic getInstance() {
        return CDI.current().select(TransactionEntityRoleTypeLogic.class).get();
    }

    public TransactionEntityRoleType createTransactionEntityRoleType(final ExecutionErrorAccumulator eea, final String transactionTypeName,
            final String transactionEntityRoleTypeName, final EntityType entityType, final Integer sortOrder,
            final Language language, final String description, final BasePK createdBy) {
        var transactionType = TransactionTypeLogic.getInstance().getTransactionTypeByName(eea, transactionTypeName);
        TransactionEntityRoleType transactionEntityRoleType = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            transactionEntityRoleType = createTransactionEntityRoleType(eea, transactionType, transactionEntityRoleTypeName,
                    entityType, sortOrder, language, description, createdBy);
        }

        return transactionEntityRoleType;
    }

    public TransactionEntityRoleType createTransactionEntityRoleType(final ExecutionErrorAccumulator eea, final TransactionType transactionType,
            final String transactionEntityRoleTypeName, final EntityType entityType, final Integer sortOrder, final Language language,
            final String description, final BasePK createdBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var transactionEntityRoleType = accountingControl.getTransactionEntityRoleTypeByName(transactionType, transactionEntityRoleTypeName);

        if(transactionEntityRoleType == null) {
            transactionEntityRoleType = accountingControl.createTransactionEntityRoleType(transactionType, transactionEntityRoleTypeName,
                    entityType, sortOrder, createdBy);

            if(description != null) {
                accountingControl.createTransactionEntityRoleTypeDescription(transactionEntityRoleType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateTransactionEntityRoleTypeNameException.class, eea, ExecutionErrors.DuplicateTransactionEntityRoleTypeName.name(), transactionEntityRoleTypeName);
        }
        return transactionEntityRoleType;
    }

    public TransactionEntityRoleType getTransactionEntityRoleTypeByName(final ExecutionErrorAccumulator eea,
            final TransactionType transactionType, final String transactionEntityRoleTypeName,
            final EntityPermission entityPermission) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var transactionEntityRoleType = accountingControl.getTransactionEntityRoleTypeByName(transactionType, transactionEntityRoleTypeName, entityPermission);

        if(transactionEntityRoleType == null) {
            handleExecutionError(UnknownTransactionEntityRoleTypeNameException.class, eea, ExecutionErrors.UnknownTransactionEntityRoleTypeName.name(),
                    transactionType.getLastDetail().getTransactionTypeName(), transactionEntityRoleTypeName);
        }

        return transactionEntityRoleType;
    }

    public TransactionEntityRoleType getTransactionEntityRoleTypeByName(final ExecutionErrorAccumulator eea,
            final TransactionType transactionType, final String transactionEntityRoleTypeName) {
        return getTransactionEntityRoleTypeByName(eea, transactionType, transactionEntityRoleTypeName, EntityPermission.READ_ONLY);
    }

    public TransactionEntityRoleType getTransactionEntityRoleTypeByNameForUpdate(final ExecutionErrorAccumulator eea,
            final TransactionType transactionType, final String transactionEntityRoleTypeName) {
        return getTransactionEntityRoleTypeByName(eea, transactionType, transactionEntityRoleTypeName, EntityPermission.READ_WRITE);
    }

    public TransactionEntityRoleType getTransactionEntityRoleTypeByName(final ExecutionErrorAccumulator eea,
            final String transactionTypeName, final String transactionEntityRoleTypeName,
            final EntityPermission entityPermission) {
        var transactionType = TransactionTypeLogic.getInstance().getTransactionTypeByName(eea, transactionTypeName);
        TransactionEntityRoleType transactionEntityRoleType = null;

        if(!eea.hasExecutionErrors()) {
            transactionEntityRoleType = getTransactionEntityRoleTypeByName(eea, transactionType, transactionEntityRoleTypeName, entityPermission);
        }

        return transactionEntityRoleType;
    }

    public TransactionEntityRoleType getTransactionEntityRoleTypeByName(final ExecutionErrorAccumulator eea,
            final String transactionTypeName, final String transactionEntityRoleTypeName) {
        return getTransactionEntityRoleTypeByName(eea, transactionTypeName, transactionEntityRoleTypeName, EntityPermission.READ_ONLY);
    }

    public TransactionEntityRoleType getTransactionEntityRoleTypeByNameForUpdate(final ExecutionErrorAccumulator eea,
            final String transactionTypeName, final String transactionEntityRoleTypeName) {
        return getTransactionEntityRoleTypeByName(eea, transactionTypeName, transactionEntityRoleTypeName, EntityPermission.READ_WRITE);
    }

    public TransactionEntityRoleType getTransactionEntityRoleTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final TransactionEntityRoleTypeUniversalSpec universalSpec, final EntityPermission entityPermission) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var transactionTypeName = universalSpec.getTransactionTypeName();
        var transactionEntityRoleTypeName = universalSpec.getTransactionEntityRoleTypeName();
        var nameParameterCount= ParameterUtils.getInstance().countNonNullParameters(transactionTypeName, transactionEntityRoleTypeName);
        var possibleEntitySpecs= EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        TransactionEntityRoleType transactionEntityRoleType = null;

        if(nameParameterCount == 2 && possibleEntitySpecs == 0) {
            var transactionType = TransactionTypeLogic.getInstance().getTransactionTypeByName(eea, transactionTypeName);

            if(!eea.hasExecutionErrors()) {
                transactionEntityRoleType = getTransactionEntityRoleTypeByName(eea, transactionType, transactionEntityRoleTypeName, entityPermission);
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.TransactionEntityRoleType.name());

            if(!eea.hasExecutionErrors()) {
                transactionEntityRoleType = accountingControl.getTransactionEntityRoleTypeByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return transactionEntityRoleType;
    }

    public TransactionEntityRoleType getTransactionEntityRoleTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final TransactionEntityRoleTypeUniversalSpec universalSpec) {
        return getTransactionEntityRoleTypeByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public TransactionEntityRoleType getTransactionEntityRoleTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final TransactionEntityRoleTypeUniversalSpec universalSpec) {
        return getTransactionEntityRoleTypeByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

    public void deleteTransactionEntityRoleType(final ExecutionErrorAccumulator eea, final TransactionEntityRoleType transactionEntityRoleType,
            final BasePK deletedBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);

        accountingControl.deleteTransactionEntityRoleType(transactionEntityRoleType, deletedBy);
    }

}

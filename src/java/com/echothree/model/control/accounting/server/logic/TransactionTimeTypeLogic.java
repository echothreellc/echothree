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

import com.echothree.control.user.accounting.common.spec.TransactionTimeTypeUniversalSpec;
import com.echothree.model.control.accounting.common.exception.DuplicateTransactionTimeTypeNameException;
import com.echothree.model.control.accounting.common.exception.UnknownDefaultTransactionTimeTypeException;
import com.echothree.model.control.accounting.common.exception.UnknownTransactionTimeTypeNameException;
import com.echothree.model.control.accounting.server.control.TransactionTimeControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.accounting.server.entity.TransactionTimeType;
import com.echothree.model.data.accounting.server.value.TransactionTimeTypeDetailValue;
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
public class TransactionTimeTypeLogic
        extends BaseLogic {

    protected TransactionTimeTypeLogic() {
        super();
    }

    public static TransactionTimeTypeLogic getInstance() {
        return CDI.current().select(TransactionTimeTypeLogic.class).get();
    }

    public TransactionTimeType createTransactionTimeType(final ExecutionErrorAccumulator eea, final String transactionTimeTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var transactionTimeControl = Session.getModelController(TransactionTimeControl.class);
        var transactionTimeType = transactionTimeControl.getTransactionTimeTypeByName(transactionTimeTypeName);

        if(transactionTimeType == null) {
            transactionTimeType = transactionTimeControl.createTransactionTimeType(transactionTimeTypeName, isDefault, sortOrder, createdBy);

            if(description != null) {
                transactionTimeControl.createTransactionTimeTypeDescription(transactionTimeType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateTransactionTimeTypeNameException.class, eea, ExecutionErrors.DuplicateTransactionTimeTypeName.name(),
                    transactionTimeTypeName);
        }

        return transactionTimeType;
    }

    public TransactionTimeType getTransactionTimeTypeByName(final ExecutionErrorAccumulator eea, final String transactionTimeTypeName,
            final EntityPermission entityPermission) {
        var transactionTimeControl = Session.getModelController(TransactionTimeControl.class);
        var transactionTimeType = transactionTimeControl.getTransactionTimeTypeByName(transactionTimeTypeName, entityPermission);

        if(transactionTimeType == null) {
            handleExecutionError(UnknownTransactionTimeTypeNameException.class, eea, ExecutionErrors.UnknownTransactionTimeTypeName.name(),
                    transactionTimeTypeName);
        }

        return transactionTimeType;
    }

    public TransactionTimeType getTransactionTimeTypeByName(final ExecutionErrorAccumulator eea, final String transactionTimeTypeName) {
        return getTransactionTimeTypeByName(eea, transactionTimeTypeName, EntityPermission.READ_ONLY);
    }

    public TransactionTimeType getTransactionTimeTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String transactionTimeTypeName) {
        return getTransactionTimeTypeByName(eea, transactionTimeTypeName, EntityPermission.READ_WRITE);
    }

    public TransactionTimeType getTransactionTimeTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final TransactionTimeTypeUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var transactionTimeControl = Session.getModelController(TransactionTimeControl.class);
        var transactionTimeTypeName = universalSpec.getTransactionTimeTypeName();
        var nameParameterCount = ParameterUtils.getInstance().countNonNullParameters(transactionTimeTypeName);
        var possibleEntitySpecs = EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        TransactionTimeType transactionTimeType = null;

        if(nameParameterCount == 1 && possibleEntitySpecs == 0) {
            if(transactionTimeTypeName == null) {
                if(allowDefault) {
                    transactionTimeType = transactionTimeControl.getDefaultTransactionTimeType(entityPermission);

                    if(transactionTimeType == null) {
                        handleExecutionError(UnknownDefaultTransactionTimeTypeException.class, eea, ExecutionErrors.UnknownDefaultTransactionTimeType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            } else {
                transactionTimeType = getTransactionTimeTypeByName(eea, transactionTimeTypeName, entityPermission);
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.TransactionTimeType.name());

            if(!eea.hasExecutionErrors()) {
                transactionTimeType = transactionTimeControl.getTransactionTimeTypeByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return transactionTimeType;
    }

    public TransactionTimeType getTransactionTimeTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final TransactionTimeTypeUniversalSpec universalSpec,
            boolean allowDefault) {
        return getTransactionTimeTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public TransactionTimeType getTransactionTimeTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final TransactionTimeTypeUniversalSpec universalSpec,
            boolean allowDefault) {
        return getTransactionTimeTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateTransactionTimeTypeFromValue(final ExecutionErrorAccumulator eea, TransactionTimeTypeDetailValue transactionTimeTypeDetailValue,
            BasePK updatedBy) {
        var transactionTimeControl = Session.getModelController(TransactionTimeControl.class);

        transactionTimeControl.updateTransactionTimeTypeFromValue(transactionTimeTypeDetailValue, updatedBy);
    }

    public void deleteTransactionTimeType(final ExecutionErrorAccumulator eea, final TransactionTimeType transactionTimeType,
            final BasePK deletedBy) {
        var transactionTimeControl = Session.getModelController(TransactionTimeControl.class);

        transactionTimeControl.deleteTransactionTimeType(transactionTimeType, deletedBy);
    }

}

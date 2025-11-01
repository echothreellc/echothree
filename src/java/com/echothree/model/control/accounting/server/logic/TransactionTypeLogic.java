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

import com.echothree.control.user.accounting.common.spec.TransactionTypeUniversalSpec;
import com.echothree.model.control.accounting.common.exception.UnknownTransactionTypeNameException;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.accounting.server.entity.TransactionType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class TransactionTypeLogic
        extends BaseLogic {

    protected TransactionTypeLogic() {
        super();
    }

    public static TransactionTypeLogic getInstance() {
        return CDI.current().select(TransactionTypeLogic.class).get();
    }

    public TransactionType getTransactionTypeByName(final ExecutionErrorAccumulator eea, final String transactionTypeName,
            final EntityPermission entityPermission) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var transactionType = accountingControl.getTransactionTypeByName(transactionTypeName, entityPermission);

        if(transactionType == null) {
            handleExecutionError(UnknownTransactionTypeNameException.class, eea, ExecutionErrors.UnknownTransactionTypeName.name(), transactionTypeName);
        }

        return transactionType;
    }

    public TransactionType getTransactionTypeByName(final ExecutionErrorAccumulator eea, final String transactionTypeName) {
        return getTransactionTypeByName(eea, transactionTypeName, EntityPermission.READ_ONLY);
    }

    public TransactionType getTransactionTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String transactionTypeName) {
        return getTransactionTypeByName(eea, transactionTypeName, EntityPermission.READ_WRITE);
    }

    public TransactionType getTransactionTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final TransactionTypeUniversalSpec universalSpec, final EntityPermission entityPermission) {
        TransactionType transactionType = null;
        var accountingControl = Session.getModelController(AccountingControl.class);
        var transactionTypeName = universalSpec.getTransactionTypeName();
        var parameterCount = (transactionTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 1:
                if(transactionTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.TransactionType.name());

                    if(!eea.hasExecutionErrors()) {
                        transactionType = accountingControl.getTransactionTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    transactionType = getTransactionTypeByName(eea, transactionTypeName, entityPermission);
                }
                break;
            default:
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                break;
        }

        return transactionType;
    }

    public TransactionType getTransactionTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final TransactionTypeUniversalSpec universalSpec) {
        return getTransactionTypeByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public TransactionType getTransactionTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final TransactionTypeUniversalSpec universalSpec) {
        return getTransactionTypeByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

}

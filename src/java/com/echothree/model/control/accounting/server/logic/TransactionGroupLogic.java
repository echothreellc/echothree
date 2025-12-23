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

import com.echothree.control.user.accounting.common.spec.TransactionGroupUniversalSpec;
import com.echothree.model.control.accounting.common.exception.UnknownTransactionGroupNameException;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.accounting.server.entity.TransactionGroup;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class TransactionGroupLogic
        extends BaseLogic {

    protected TransactionGroupLogic() {
        super();
    }

    public static TransactionGroupLogic getInstance() {
        return CDI.current().select(TransactionGroupLogic.class).get();
    }

    public TransactionGroup getTransactionGroupByName(final ExecutionErrorAccumulator eea, final String transactionGroupName,
            final EntityPermission entityPermission) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var transactionGroup = accountingControl.getTransactionGroupByName(transactionGroupName, entityPermission);

        if(transactionGroup == null) {
            handleExecutionError(UnknownTransactionGroupNameException.class, eea, ExecutionErrors.UnknownTransactionGroupName.name(), transactionGroupName);
        }

        return transactionGroup;
    }

    public TransactionGroup getTransactionGroupByName(final ExecutionErrorAccumulator eea, final String transactionGroupName) {
        return getTransactionGroupByName(eea, transactionGroupName, EntityPermission.READ_ONLY);
    }

    public TransactionGroup getTransactionGroupByNameForUpdate(final ExecutionErrorAccumulator eea, final String transactionGroupName) {
        return getTransactionGroupByName(eea, transactionGroupName, EntityPermission.READ_WRITE);
    }

    public TransactionGroup getTransactionGroupByUniversalSpec(final ExecutionErrorAccumulator eea,
            final TransactionGroupUniversalSpec universalSpec, final EntityPermission entityPermission) {
        TransactionGroup transactionGroup = null;
        var accountingControl = Session.getModelController(AccountingControl.class);
        var transactionGroupName = universalSpec.getTransactionGroupName();
        var parameterCount = (transactionGroupName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        if(parameterCount == 1) {
            if(transactionGroupName == null) {
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                        ComponentVendors.ECHO_THREE.name(), EntityTypes.TransactionGroup.name());

                if(!eea.hasExecutionErrors()) {
                    transactionGroup = accountingControl.getTransactionGroupByEntityInstance(entityInstance, entityPermission);
                }
            } else {
                transactionGroup = getTransactionGroupByName(eea, transactionGroupName, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return transactionGroup;
    }

    public TransactionGroup getTransactionGroupByUniversalSpec(final ExecutionErrorAccumulator eea,
            final TransactionGroupUniversalSpec universalSpec) {
        return getTransactionGroupByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public TransactionGroup getTransactionGroupByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final TransactionGroupUniversalSpec universalSpec) {
        return getTransactionGroupByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

}

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

import com.echothree.model.control.accounting.common.exception.DuplicateTransactionTimeException;
import com.echothree.model.control.accounting.common.exception.UnknownTransactionTimeException;
import com.echothree.model.control.accounting.common.exception.UnknownTransactionTimeTypeNameException;
import com.echothree.model.control.accounting.common.transfer.TransactionTimeTransfer;
import com.echothree.model.control.accounting.server.control.TransactionTimeControl;
import com.echothree.model.data.accounting.server.entity.Transaction;
import com.echothree.model.data.accounting.server.entity.TransactionTime;
import com.echothree.model.data.accounting.server.entity.TransactionTimeType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class TransactionTimeLogic
        extends BaseLogic {

    protected TransactionTimeLogic() {
        super();
    }

    public static TransactionTimeLogic getInstance() {
        return CDI.current().select(TransactionTimeLogic.class).get();
    }

    public TransactionTimeType getTransactionTimeTypeByName(final ExecutionErrorAccumulator eea, final String transactionTimeTypeName) {
        var transactionTimeControl = Session.getModelController(TransactionTimeControl.class);
        var transactionTimeType = transactionTimeControl.getTransactionTimeTypeByName(transactionTimeTypeName);

        if(transactionTimeType == null) {
            handleExecutionError(UnknownTransactionTimeTypeNameException.class, eea, ExecutionErrors.UnknownTransactionTimeTypeName.name(), transactionTimeTypeName);
        }

        return transactionTimeType;
    }

    public void createTransactionTime(final ExecutionErrorAccumulator eea, final Transaction transaction, final String transactionTimeTypeName, final Long time, final BasePK partyPK) {
        var transactionTimeControl = Session.getModelController(TransactionTimeControl.class);
        var transactionTimeType = transactionTimeControl.getTransactionTimeTypeByName(transactionTimeTypeName);

        if(eea == null || !eea.hasExecutionErrors()) {
            if(transactionTimeControl.transactionTimeExists(transaction, transactionTimeType)) {
                handleExecutionError(DuplicateTransactionTimeException.class, eea, ExecutionErrors.DuplicateTransactionTime.name(),
                        transaction.getLastDetail().getTransactionName(), transactionTimeTypeName);
            } else {
                transactionTimeControl.createTransactionTime(transaction, transactionTimeType, time, partyPK);
            }
        }
    }

    public void updateTransactionTime(final ExecutionErrorAccumulator eea, final Transaction transaction, final String transactionTimeTypeName, final Long time, final BasePK partyPK) {
        var transactionTimeControl = Session.getModelController(TransactionTimeControl.class);
        var transactionTimeType = transactionTimeControl.getTransactionTimeTypeByName(transactionTimeTypeName);

        if(eea == null || !eea.hasExecutionErrors()) {
            var transactionTimeValue = transactionTimeControl.getTransactionTimeValueForUpdate(transaction, transactionTimeType);

            if(transactionTimeValue == null) {
                handleExecutionError(UnknownTransactionTimeException.class, eea, ExecutionErrors.UnknownTransactionTime.name(),
                        transaction.getLastDetail().getTransactionName(), transactionTimeTypeName);
            } else {
                transactionTimeValue.setTime(time);
                transactionTimeControl.updateTransactionTimeFromValue(transactionTimeValue, partyPK);
            }
        }
    }

    public void createOrUpdateTransactionTimeIfNotNull(final ExecutionErrorAccumulator eea, final Transaction transaction, final String transactionTimeTypeName, final Long time,
            final BasePK partyPK) {
        if(time != null) {
            createOrUpdateTransactionTime(eea, transaction, transactionTimeTypeName, time, partyPK);
        }
    }

    public void createOrUpdateTransactionTime(final ExecutionErrorAccumulator eea, final Transaction transaction, final String transactionTimeTypeName, final Long time,
            final BasePK partyPK) {
        var transactionTimeControl = Session.getModelController(TransactionTimeControl.class);
        var transactionTimeType = getTransactionTimeTypeByName(eea, transactionTimeTypeName);

        if(eea == null || !eea.hasExecutionErrors()) {
            var transactionTimeValue = transactionTimeControl.getTransactionTimeValueForUpdate(transaction, transactionTimeType);

            if(transactionTimeValue == null) {
                transactionTimeControl.createTransactionTime(transaction, transactionTimeType, time, partyPK);
            } else {
                transactionTimeValue.setTime(time);
                transactionTimeControl.updateTransactionTimeFromValue(transactionTimeValue, partyPK);
            }
        }
    }

    private TransactionTime getTransactionTimeEntity(final ExecutionErrorAccumulator eea, final Transaction transaction, final String transactionTimeTypeName) {
        var transactionTimeControl = Session.getModelController(TransactionTimeControl.class);
        var transactionTimeType = getTransactionTimeTypeByName(eea, transactionTimeTypeName);
        TransactionTime result = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            result = transactionTimeControl.getTransactionTimeForUpdate(transaction, transactionTimeType);

            if(result == null) {
                handleExecutionError(UnknownTransactionTimeException.class, eea, ExecutionErrors.UnknownTransactionTime.name(),
                        transaction.getLastDetail().getTransactionName(), transactionTimeTypeName);
            }
        }

        return result;
    }

    public Long getTransactionTime(final ExecutionErrorAccumulator eea, final Transaction transaction, final String transactionTimeTypeName) {
        var transactionTime = getTransactionTimeEntity(eea, transaction, transactionTimeTypeName);
        
        return transactionTime == null ? null : transactionTime.getTime();
    }

    public TransactionTimeTransfer getTransactionTimeTransfer(final ExecutionErrorAccumulator eea, final UserVisit userVisit, final Transaction transaction, final String transactionTimeTypeName) {
        var transactionTime = getTransactionTimeEntity(eea, transaction, transactionTimeTypeName);
        
        return transactionTime == null ? null : Session.getModelController(TransactionTimeControl.class).getTransactionTimeTransfer(userVisit, transactionTime);
    }

    public List<TransactionTimeTransfer> getTransactionTimeTransfersByOrder(final ExecutionErrorAccumulator eea, final UserVisit userVisit, final Transaction transaction) {
        return Session.getModelController(TransactionTimeControl.class).getTransactionTimeTransfersByTransaction(userVisit, transaction);
    }

    public void deleteTransactionTime(final ExecutionErrorAccumulator eea, final Transaction transaction, final String transactionTimeTypeName, final BasePK deletedBy) {
        var transactionTimeControl = Session.getModelController(TransactionTimeControl.class);
        var transactionTimeType = getTransactionTimeTypeByName(eea, transactionTimeTypeName);

        if(eea == null || !eea.hasExecutionErrors()) {
            var transactionTime = transactionTimeControl.getTransactionTimeForUpdate(transaction, transactionTimeType);

            if(transactionTime == null) {
                handleExecutionError(UnknownTransactionTimeException.class, eea, ExecutionErrors.UnknownTransactionTime.name(),
                        transaction.getLastDetail().getTransactionName(), transactionTimeTypeName);
            } else {
                transactionTimeControl.deleteTransactionTime(transactionTime, deletedBy);
            }
        }
    }

}

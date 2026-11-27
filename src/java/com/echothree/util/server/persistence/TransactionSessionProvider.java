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

package com.echothree.util.server.persistence;

import com.echothree.util.server.cdi.CommandScope;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.transaction.Status;
import javax.transaction.Synchronization;
import javax.transaction.TransactionSynchronizationRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** One persistence session per JTA transaction, shared by nested commands. */
@ApplicationScoped
public class TransactionSessionProvider {

    private static final Object SESSION_RESOURCE_KEY = new Object();
    private static final Logger log = LoggerFactory.getLogger(TransactionSessionProvider.class);

    @Resource(lookup = "java:comp/TransactionSynchronizationRegistry")
    protected TransactionSynchronizationRegistry transactionRegistry;

    @Produces
    @CommandScope
    public Session getSession() {
        if(transactionRegistry.getTransactionKey() == null) {
            throw new IllegalStateException("Persistence sessions require a JTA transaction");
        }

        var session = (Session)transactionRegistry.getResource(SESSION_RESOURCE_KEY);

        if(session == null) {
            if(transactionRegistry.getTransactionStatus() != Status.STATUS_ACTIVE) {
                throw new IllegalStateException("Cannot create a persistence session in an inactive transaction");
            }

            var transactionSession = createSession();
            transactionSession.bindTransaction(transactionRegistry);

            try {
                transactionRegistry.registerInterposedSynchronization(new Synchronization() {
                    @Override
                    public void beforeCompletion() {
                        // Do not flush here: cache synchronizations may already be preparing.
                        // Every successful command must have flushed while still executing.
                        if(transactionSession.hasPendingChanges()) {
                            transactionRegistry.setRollbackOnly();
                            throw new IllegalStateException("Transaction completed with unflushed persistence changes");
                        }
                    }

                    @Override
                    public void afterCompletion(final int status) {
                        try {
                            // The concrete instance remains valid after command scopes have ended.
                            // Closing resources never flushes, on either commit or rollback.
                            transactionSession.close();
                        } catch(RuntimeException exception) {
                            log.error("Unable to close persistence session after transaction completion, status=" + status, exception);
                        }
                    }
                });

                transactionRegistry.putResource(SESSION_RESOURCE_KEY, transactionSession);

                session = transactionSession;
            } catch(RuntimeException | Error failure) {
                try {
                    transactionSession.close();
                } catch(RuntimeException closeFailure) {
                    failure.addSuppressed(closeFailure);
                }

                throw failure;
            }
        }

        return session;
    }

    protected Session createSession() {
        var session = new Session();

        // Session is a producer product, so lifecycle callbacks must be invoked explicitly.
        try {
            session.init();
        } catch(RuntimeException | Error failure) {
            try {
                session.close();
            } catch(RuntimeException closeFailure) {
                failure.addSuppressed(closeFailure);
            }
            throw failure;
        }

        return session;
    }

}

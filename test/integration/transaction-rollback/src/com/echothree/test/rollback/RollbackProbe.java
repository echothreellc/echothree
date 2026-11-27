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

package com.echothree.test.rollback;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Startup
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class RollbackProbe {
    private static final Logger log = LoggerFactory.getLogger(RollbackProbe.class);

    @EJB
    RollbackProbeWorker worker;

    private void expectRollback(Runnable action, RollbackProbeWorker.Evidence evidence, int children) {
        var rejected = false;
        try {
            action.run();
        } catch(RuntimeException | Error expected) {
            rejected = true;
            log.info("ROLLBACK_PROBE boundary rejection: {}", expected.toString());
        }
        RollbackProbeWorker.require(rejected && evidence.reachedFailure && evidence.successfulChildren == children,
                "Did not reach intended rollback after successful child flushes");
    }

    @PostConstruct
    public void run() {
        var prefix = "RB" + Long.toString(System.currentTimeMillis(), 36).toUpperCase();
        var baseline = new RollbackProbeWorker.Evidence();
        var baselineCommitted = false;
        try {
            for(var fatalError : new boolean[] { false, true }) {
                var evidence = new RollbackProbeWorker.Evidence();
                expectRollback(() -> worker.outerFailure(evidence, prefix + (fatalError ? "E" : "R"), fatalError), evidence, 2);
                worker.verifyAbsent(evidence);
                log.info("ROLLBACK_PROBE PASS outer {}: SQL/details/cache/factory absent, parties={}",
                        fatalError ? "Error" : "RuntimeException", evidence.parties);
            }
            var graphQlEvidence = new RollbackProbeWorker.Evidence();
            // A rollback-only EJB may return normally. SQL/cache verification is decisive.
            try {
                worker.graphQlFailure(graphQlEvidence, prefix + "G");
            } catch(RuntimeException expected) {
                log.info("ROLLBACK_PROBE GraphQL boundary rejection: {}", expected.toString());
            }
            RollbackProbeWorker.require(graphQlEvidence.reachedFailure && graphQlEvidence.successfulChildren == 2,
                    "GraphQL did not execute both child flushes before failing");
            RollbackProbeWorker.require(graphQlEvidence.graphQlErrors == 1, "GraphQL exception was not captured");
            worker.verifyAbsent(graphQlEvidence);
            log.info("ROLLBACK_PROBE PASS GraphQL caught child: SQL/details/cache/factory absent, parties={}", graphQlEvidence.parties);

            worker.baseline(baseline, prefix + "BASE");
            baselineCommitted = true;
            worker.verifyOriginal(baseline);
            expectRollback(() -> worker.editFailure(baseline), baseline, 1);
            worker.verifyOriginal(baseline);
            log.info("ROLLBACK_PROBE PASS existing Party edit: SQL/cache/name restored, parties={}, originalDetail={}, rolledBackDetail={}",
                    baseline.parties, baseline.originalDetail, baseline.editedDetail);
            log.info("ROLLBACK_PROBE ALL PASS prefix={}", prefix);
        } catch(Throwable failure) {
            log.error("ROLLBACK_PROBE FAILED prefix=" + prefix, failure);
        } finally {
            if(baselineCommitted) {
                try {
                    worker.cleanup(baseline);
                    log.info("ROLLBACK_PROBE CLEANUP PASS baseline={}", baseline.parties);
                } catch(Throwable failure) {
                    log.error("ROLLBACK_PROBE CLEANUP FAILED baseline=" + baseline.parties, failure);
                }
            }
            log.info("ROLLBACK_PROBE FINISHED prefix={}", prefix);
        }
    }
}

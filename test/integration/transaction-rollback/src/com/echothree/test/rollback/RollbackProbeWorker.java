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

import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.value.PartyValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.SecurityResult;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.server.control.BaseCommand;
import graphql.GraphQL;
import graphql.Scalars;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.transaction.Status;
import javax.transaction.TransactionSynchronizationRegistry;
import org.infinispan.Cache;

/** Deployment-only probe. Never packaged by the production build. */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class RollbackProbeWorker {
    @Inject
    PartyControl partyControl;

    @Resource(lookup = "java:/EchoThreeDS")
    DataSource dataSource;

    @Resource
    TransactionSynchronizationRegistry transactionRegistry;

    @Resource(lookup = "java:jboss/infinispan/cache/echothree/data")
    Cache<String, Object> dataCache;

    public static class Evidence {
        public final List<PartyPK> parties = new ArrayList<>();
        public int successfulChildren;
        public int graphQlErrors;
        public boolean reachedFailure;
        public Object transactionKey;
        public String originalName;
        public String editedName;
        public Long originalDetail;
        public Long editedDetail;
    }

    private class Command extends BaseCommand {
        private final Runnable action;

        Command(Runnable action) {
            super(null);
            this.action = action;
            setUpdateLastCommandTime(false);
        }

        @Override public UserVisit getUserVisit() { return null; }
        @Override protected SecurityResult security() { return null; }
        @Override protected ValidationResult validate() { return null; }
        @Override protected BaseResult execute() { action.run(); return null; }
    }

    static void require(boolean condition, String message) {
        if(!condition) {
            throw new IllegalStateException("ROLLBACK_PROBE_ASSERTION: " + message);
        }
    }

    private Party create(Evidence evidence, String name) {
        var party = partyControl.createParty(name, partyControl.getPartyTypeByName("UTILITY"),
                null, null, null, null, null);
        require(party != null, "Party was not created");
        evidence.parties.add(party.getPrimaryKey());
        return party;
    }

    private PartyValue cached(PartyPK partyPK) {
        return (PartyValue)dataCache.get("//ECHO_THREE/Party/" + partyPK.getEntityId() + "/value");
    }

    private List<String> sql(String query, PartyPK partyPK) {
        var values = new ArrayList<String>();
        try(var connection = dataSource.getConnection();
                var statement = connection.prepareStatement(query)) {
            statement.setLong(1, partyPK.getEntityId());
            try(var rows = statement.executeQuery()) {
                while(rows.next()) {
                    for(int column = 1; column <= rows.getMetaData().getColumnCount(); column++) {
                        values.add(rows.getString(column));
                    }
                }
            }
        } catch(java.sql.SQLException failure) {
            throw new IllegalStateException(failure);
        }
        return values;
    }

    private void child(Evidence evidence, String name) {
        new Command(() -> {
            require(evidence.transactionKey.equals(transactionRegistry.getTransactionKey()), "Nested transaction changed");
            create(evidence, name);
        }).run(null);
        var partyPK = evidence.parties.get(evidence.parties.size() - 1);
        var value = cached(partyPK);
        require(value != null && value.getLastDetailPK() != null, "Child did not flush completed cache value");
        require(sql("SELECT par_lastdetailid FROM parties WHERE par_partyid=?", partyPK)
                .equals(List.of(value.getLastDetailPK().getEntityId().toString())), "SQL/cache differ before rollback");
        evidence.successfulChildren++;
    }

    public void outerFailure(Evidence evidence, String prefix, boolean fatalError) {
        evidence.transactionKey = transactionRegistry.getTransactionKey();
        new Command(() -> {
            child(evidence, prefix + "A");
            new Command(() -> child(evidence, prefix + "B")).run(null);
            evidence.reachedFailure = true;
            if(fatalError) {
                throw new AssertionError("ROLLBACK_PROBE_EXPECTED outer Error");
            }
            throw new IllegalStateException("ROLLBACK_PROBE_EXPECTED outer failure");
        }).run(null);
    }

    public void graphQlFailure(Evidence evidence, String prefix) {
        evidence.transactionKey = transactionRegistry.getTransactionKey();
        new Command(() -> {
            var query = GraphQLObjectType.newObject().name("Query")
                    .field(GraphQLFieldDefinition.newFieldDefinition().name("ok").type(Scalars.GraphQLString)
                            .dataFetcher(environment -> "ok")).build();
            var mutation = GraphQLObjectType.newObject().name("Mutation")
                    .field(GraphQLFieldDefinition.newFieldDefinition().name("first").type(Scalars.GraphQLString)
                            .dataFetcher(environment -> { child(evidence, prefix + "A"); return "flushed"; }))
                    .field(GraphQLFieldDefinition.newFieldDefinition().name("second").type(Scalars.GraphQLString)
                            .dataFetcher(environment -> {
                                new Command(() -> {
                                    child(evidence, prefix + "B");
                                    evidence.reachedFailure = true;
                                    throw new IllegalStateException("ROLLBACK_PROBE_EXPECTED GraphQL child failure");
                                }).run(null);
                                return "unreachable";
                            })).build();
            var result = GraphQL.newGraphQL(GraphQLSchema.newSchema().query(query).mutation(mutation).build())
                    .build().execute("mutation { first second }");
            evidence.graphQlErrors = result.getErrors().size();
            require(evidence.graphQlErrors == 1, "GraphQL did not catch exactly one child failure");
            require(transactionRegistry.getTransactionStatus() == Status.STATUS_MARKED_ROLLBACK,
                    "GraphQL caught exception but transaction is not rollback-only");
        }).run(null);
    }

    public void baseline(Evidence evidence, String name) {
        new Command(() -> {
            var party = create(evidence, name);
            evidence.originalName = name;
            evidence.originalDetail = party.getLastDetailPK().getEntityId();
        }).run(null);
    }

    public void editFailure(Evidence evidence) {
        evidence.transactionKey = transactionRegistry.getTransactionKey();
        new Command(() -> {
            new Command(() -> {
                var party = partyControl.getPartyByNameForUpdate(evidence.originalName);
                var detail = partyControl.getPartyDetailValueForUpdate(party);
                evidence.editedName = evidence.originalName + "EDIT";
                detail.setPartyName(evidence.editedName);
                partyControl.updatePartyFromValue(detail, party.getPrimaryKey());
                evidence.editedDetail = party.getLastDetailPK().getEntityId();
            }).run(null);
            var partyPK = evidence.parties.get(0);
            require(!evidence.originalDetail.equals(evidence.editedDetail), "Edit did not create a new detail");
            require(cached(partyPK).getLastDetailPK().getEntityId().equals(evidence.editedDetail), "Edit did not flush cache");
            require(sql("SELECT par_lastdetailid FROM parties WHERE par_partyid=?", partyPK)
                    .equals(List.of(evidence.editedDetail.toString())), "Edit did not flush SQL");
            evidence.successfulChildren++;
            evidence.reachedFailure = true;
            throw new IllegalStateException("ROLLBACK_PROBE_EXPECTED edit rollback");
        }).run(null);
    }

    public void verifyAbsent(Evidence evidence) {
        for(var partyPK : evidence.parties) {
            require(sql("SELECT par_partyid FROM parties WHERE par_partyid=?", partyPK).isEmpty(), "Party survived rollback: " + partyPK);
            require(sql("SELECT pardt_partydetailid FROM partydetails WHERE pardt_par_partyid=?", partyPK).isEmpty(), "Detail survived rollback: " + partyPK);
            // Read the shared cache before a factory miss can populate or evict anything.
            require(cached(partyPK) == null, "Shared cache value survived rollback: " + partyPK);
            new Command(() -> require(partyControl.getPartyByPK(partyPK) == null, "Factory still resolves rolled-back Party")).run(null);
        }
    }

    public void verifyOriginal(Evidence evidence) {
        var partyPK = evidence.parties.get(0);
        require(sql("SELECT par_activedetailid,par_lastdetailid FROM parties WHERE par_partyid=?", partyPK)
                .equals(List.of(evidence.originalDetail.toString(), evidence.originalDetail.toString())), "Original SQL pointers changed");
        require(sql("SELECT pardt_partyname,pardt_thrutime FROM partydetails WHERE pardt_par_partyid=?", partyPK)
                .equals(List.of(evidence.originalName, Long.toString(Long.MAX_VALUE))), "Original detail changed or edited detail survived");
        var value = cached(partyPK);
        require(value != null, "Committed baseline was not cached");
        require(value.getActiveDetailPK().getEntityId().equals(evidence.originalDetail)
                && value.getLastDetailPK().getEntityId().equals(evidence.originalDetail), "Original cached pointers changed");
        new Command(() -> {
            var party = partyControl.getPartyByPK(partyPK);
            require(party.getLastDetail().getPartyName().equals(evidence.originalName), "Factory sees edited name after rollback");
        }).run(null);
    }

    public void cleanup(Evidence evidence) {
        new Command(() -> {
            var party = partyControl.getPartyByNameForUpdate(evidence.originalName);
            require(party != null, "Cleanup baseline not found");
            partyControl.deleteParty(party, party.getPrimaryKey());
        }).run(null);
    }
}

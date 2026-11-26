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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.CoreFormFactory;
import com.echothree.model.control.core.server.control.AppearanceControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.control.EventControl;
import com.echothree.model.control.core.server.logic.AppearanceLogic;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.core.server.entity.Appearance;
import com.echothree.model.data.core.server.entity.EntityAppearance;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityBlobAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityTime;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.value.EntityTimeValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.Messages;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/** Exercise the shared deletion-aware resolver through attached-data mutation commands. */
public class EntityOwnerMutationTest {

    private static class OwnerEnvironment {
        final EntityEditFixture fixture;
        final EntityInstanceLogic logic = new EntityInstanceLogic() { };
        int ownerLookups;
        int deletionChecks;
        int dependentLookups;
        int writes;

        OwnerEnvironment(String state) {
            this(state, new EntityEditFixture("EntityAppearance", EditMode.LOCK));
        }

        OwnerEnvironment(String state, EntityEditFixture fixture) {
            this.fixture = fixture;
            var instances = new EntityInstanceControl() {
                @Override
                public EntityInstance getEntityInstanceByEntityRef(String entityRef) {
                    assertEquals("ECHO_THREE.Item.777", entityRef);
                    ownerLookups++;
                    return state.equals("unknown") ? null : fixture.entityInstance;
                }

                @Override
                public EntityInstance getEntityInstanceByUuid(String uuid) {
                    assertEquals("11111111-1111-4111-8111-111111111111", uuid);
                    ownerLookups++;
                    return state.equals("unknown") ? null : fixture.entityInstance;
                }
            };
            var events = new EventControl() {
                @Override
                public EntityTime getEntityTime(EntityInstance entityInstance) {
                    assertSame(fixture.entityInstance, entityInstance);
                    deletionChecks++;
                    return state.equals("noTime") ? null
                            : new EntityTime(fixture.value(EntityTimeValue.class), EntityPermission.READ_ONLY) {
                        @Override
                        public Long getDeletedTime() {
                            return state.equals("deleted") ? 1000L : null;
                        }
                    };
                }
            };
            EntityEditFixture.inject(logic, "entityInstanceControl", instances);
            EntityEditFixture.inject(logic, "eventControl", events);
        }

        boolean rejected(String state) {
            return state.equals("unknown") || state.equals("deleted");
        }

        void assertError(ExecutionErrorAccumulator command, String state, boolean uuid) {
            assertEquals((state.equals("unknown") ? "Unknown" : "Deleted") + (uuid ? "Uuid" : "EntityRef"),
                    command.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
            assertEquals(state.equals("unknown") ? 0 : 1, deletionChecks);
        }
    }

    static Stream<Arguments> edits() {
        return Stream.of("EntityAppearance", "EntityBlobAttribute").flatMap(kind ->
                Stream.of(EditMode.values()).flatMap(mode ->
                        Stream.of("unknown", "deleted", "active", "noTime").map(state -> Arguments.of(kind, mode, state))));
    }

    @ParameterizedTest
    @MethodSource("edits")
    public void editsUseRealDeletionCheckBeforeValueLookupOrLock(String kind, EditMode mode, String state) {
        var fixture = new EntityEditFixture(kind, mode);
        var environment = new OwnerEnvironment(state, fixture);
        EntityEditFixture.inject(fixture.command, "entityInstanceLogic", environment.logic);
        fixture.execute();
        if(environment.rejected(state)) {
            environment.assertError(fixture.command, state, false);
            assertEquals(0, fixture.valueLookups);
            assertEquals(0, fixture.updates);
            assertEquals(0, fixture.transfers);
            assertTrue(fixture.locks.isEmpty());
        } else {
            assertFalse(fixture.command.hasExecutionErrors());
            assertEquals(1, fixture.valueLookups);
            assertEquals(mode == EditMode.UPDATE ? 1 : 0, fixture.updates);
            assertEquals(1, environment.deletionChecks);
        }
        assertEquals(1, environment.ownerLookups);
    }

    static Stream<Arguments> simpleMutations() {
        return Stream.of("createAppearance", "deleteAppearance", "deleteBlob").flatMap(operation ->
                Stream.of("unknown", "deleted", "active", "noTime").map(state -> Arguments.of(operation, state)));
    }

    @ParameterizedTest
    @MethodSource("simpleMutations")
    public void createAndDeleteRejectDeletedOwnersAndPreserveActiveOwnerWrites(String operation, String state) {
        var environment = new OwnerEnvironment(state);
        var fixture = environment.fixture;
        var appearances = new AppearanceControl() {
            @Override
            public EntityAppearance getEntityAppearance(EntityInstance entityInstance, EntityPermission entityPermission) {
                assertEquals(EntityPermission.READ_WRITE, entityPermission);
                return getEntityAppearanceForUpdate(entityInstance);
            }

            @Override
            public EntityAppearance getEntityAppearance(EntityInstance entityInstance) {
                assertSame(fixture.entityInstance, entityInstance);
                environment.dependentLookups++;
                return null;
            }

            @Override
            public EntityAppearance getEntityAppearanceForUpdate(EntityInstance entityInstance) {
                assertSame(fixture.entityInstance, entityInstance);
                environment.dependentLookups++;
                return fixture.entityAppearance;
            }

            @Override
            public EntityAppearance createEntityAppearance(EntityInstance entityInstance, Appearance appearance, BasePK createdBy) {
                assertSame(fixture.entityInstance, entityInstance);
                assertSame(fixture.appearance, appearance);
                environment.writes++;
                return fixture.entityAppearance;
            }

            @Override
            public void deleteEntityAppearance(EntityAppearance entityAppearance, BasePK deletedBy) {
                assertSame(fixture.entityAppearance, entityAppearance);
                environment.writes++;
            }
        };
        BaseSimpleCommand<?> command;
        Runnable execute;
        switch(operation) {
            case "createAppearance" -> {
                var create = new CreateEntityAppearanceCommand() {
                    @Override
                    public PartyPK getPartyPK() {
                        return fixture.updatedBy;
                    }
                };
                var form = CoreFormFactory.getCreateEntityAppearanceForm();
                form.setEntityRef("ECHO_THREE.Item.777");
                form.setAppearanceName("Original");
                EntityEditFixture.inject(create, "form", form);
                EntityEditFixture.inject(create, "appearanceLogic", new AppearanceLogic() {
                    @Override
                    public Appearance getAppearanceByName(ExecutionErrorAccumulator eea, String appearanceName) {
                        return fixture.appearance;
                    }
                });
                command = create;
                execute = create::execute;
            }
            case "deleteAppearance" -> {
                var delete = new DeleteEntityAppearanceCommand() {
                    @Override
                    public PartyPK getPartyPK() {
                        return fixture.updatedBy;
                    }
                };
                var form = CoreFormFactory.getDeleteEntityAppearanceForm();
                form.setEntityRef("ECHO_THREE.Item.777");
                EntityEditFixture.inject(delete, "form", form);
                command = delete;
                execute = delete::execute;
            }
            case "deleteBlob" -> {
                var delete = new DeleteEntityBlobAttributeCommand() {
                    @Override
                    public PartyPK getPartyPK() {
                        return fixture.updatedBy;
                    }
                };
                var form = CoreFormFactory.getDeleteEntityBlobAttributeForm();
                form.setEntityRef("ECHO_THREE.Item.777");
                form.setEntityAttributeName("ATTRIBUTE");
                form.setLanguageIsoName("en");
                EntityEditFixture.inject(delete, "form", form);
                var core = new CoreControl() {
                    @Override
                    public EntityBlobAttribute getEntityBlobAttribute(EntityAttribute attribute, EntityInstance entityInstance,
                            Language language, EntityPermission entityPermission) {
                        assertEquals(EntityPermission.READ_WRITE, entityPermission);
                        return getEntityBlobAttributeForUpdate(attribute, entityInstance, language);
                    }

                    @Override
                    public EntityAttribute getEntityAttributeByName(EntityType entityType, String entityAttributeName) {
                        environment.dependentLookups++;
                        return fixture.entityAttribute;
                    }

                    @Override
                    public EntityBlobAttribute getEntityBlobAttributeForUpdate(EntityAttribute attribute, EntityInstance entityInstance, Language language) {
                        return fixture.entityBlobAttribute;
                    }

                    @Override
                    public void deleteEntityBlobAttribute(EntityBlobAttribute entityBlobAttribute, BasePK deletedBy) {
                        assertSame(fixture.entityBlobAttribute, entityBlobAttribute);
                        environment.writes++;
                    }
                };
                EntityEditFixture.inject(delete, "coreControl", core);
                var attributes = new EntityAttributeLogic() { };
                EntityEditFixture.inject(attributes, "coreControl", core);
                EntityEditFixture.inject(attributes, "entityInstanceLogic", environment.logic);
                EntityEditFixture.inject(delete, "entityAttributeLogic", attributes);
                EntityEditFixture.inject(delete, "partyControl", new PartyControl() {
                    @Override
                    public Language getLanguageByIsoName(String languageIsoName) {
                        return fixture.language;
                    }
                });
                command = delete;
                execute = delete::execute;
            }
            default -> throw new IllegalArgumentException(operation);
        }
        EntityEditFixture.inject(command, "entityInstanceLogic", environment.logic);
        if(!operation.equals("deleteBlob")) {
            EntityEditFixture.inject(command, "appearanceControl", appearances);
            if(operation.equals("deleteAppearance")) {
                var appearanceLogic = new AppearanceLogic() { };
                EntityEditFixture.inject(appearanceLogic, "appearanceControl", appearances);
                EntityEditFixture.inject(appearanceLogic, "entityInstanceLogic", environment.logic);
                EntityEditFixture.inject(command, "appearanceLogic", appearanceLogic);
            }
        }
        execute.run();
        if(environment.rejected(state)) {
            environment.assertError(command, state, false);
            assertEquals(0, environment.dependentLookups);
            assertEquals(0, environment.writes);
        } else {
            assertFalse(command.hasExecutionErrors());
            assertEquals(1, environment.dependentLookups);
            assertEquals(1, environment.writes);
        }
        assertEquals(1, environment.ownerLookups);
    }

    static Stream<Arguments> blobCreationFailures() {
        return Stream.of(false, true).flatMap(uuid ->
                Stream.of("unknown", "deleted").map(state -> Arguments.of(uuid, state)));
    }

    @ParameterizedTest
    @MethodSource("blobCreationFailures")
    public void blobCreationAlreadyRejectsDeletedOwnersByEitherIdentifier(boolean uuid, String state) {
        var environment = new OwnerEnvironment(state);
        var command = new CreateEntityBlobAttributeCommand();
        var form = CoreFormFactory.getCreateEntityBlobAttributeForm();
        if(uuid) {
            form.setUuid("11111111-1111-4111-8111-111111111111");
        } else {
            form.setEntityRef("ECHO_THREE.Item.777");
        }
        EntityEditFixture.inject(command, "form", form);
        EntityEditFixture.inject(command, "entityInstanceLogic", environment.logic);
        command.execute();
        environment.assertError(command, state, uuid);
        assertEquals(0, environment.dependentLookups);
        assertEquals(0, environment.writes);
    }
}


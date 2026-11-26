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

import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.edit.EntityAppearanceEdit;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityAppearanceResult;
import com.echothree.control.user.core.common.spec.CoreSpecFactory;
import com.echothree.control.user.core.common.spec.EntityRefSpec;
import com.echothree.model.control.core.common.transfer.EntityLockTransfer;
import com.echothree.model.control.core.server.control.EntityLockControl;
import com.echothree.model.data.core.server.entity.EntityAppearance;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.PersistenceUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Exercise the actual BaseEditCommand-to-Control routing, without overriding lock operations.
 */
public class EntityEditLifecycleTest {

    private static class TestCommand extends BaseAbstractEditCommand<EntityRefSpec, EntityAppearanceEdit,
            EditEntityAppearanceResult, EntityAppearance, EntityInstance> {

        final EntityEditFixture fixture = new EntityEditFixture("EntityAppearance", EditMode.ABANDON);
        final List<String> operations = new ArrayList<>();
        final boolean translated;
        boolean lockAllowed = true;
        boolean failUpdate;
        boolean rejectUpdate;
        int results;

        TestCommand(EditMode mode, String representedType) {
            super(null, List.of(), List.of());
            this.translated = representedType != null;
            if(translated) {
                fixture.property("EntityTypeDetail", "entityTypeName", representedType);
            }
            editMode = mode;
            spec = CoreSpecFactory.getEntityRefSpec();
            edit = CoreEditFactory.getEntityAppearanceEdit();
            EntityEditFixture.inject(this, "entityLockControl", new EntityLockControl() {
                private void check(String operation, BasePK target, BasePK actor) {
                    assertEquals(translated ? "ECHO_THREE." + representedType + ".777"
                            : fixture.entityInstance.getPrimaryKey().getEntityRef(), target.getEntityRef());
                    assertEquals(new PartyPK(99L), actor);
                    operations.add(operation);
                }

                @Override
                public long lockEntity(BasePK target, BasePK actor) {
                    check("lock", target, actor);
                    return lockAllowed ? 1000L : 0L;
                }

                @Override
                public boolean lockEntityForUpdate(BasePK target, BasePK actor) {
                    check("update", target, actor);
                    return lockAllowed;
                }

                @Override
                public boolean unlockEntity(BasePK target, BasePK actor) {
                    check("unlock", target, actor);
                    return true;
                }

                @Override
                public boolean isEntityLocked(BasePK target, BasePK actor) {
                    check("isLocked", target, actor);
                    return true;
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(UserVisit visit, BasePK target) {
                    check("transfer", target, new PartyPK(99L));
                    return null;
                }
            });
        }

        @Override
        protected BasePK getLockTargetPK(BaseEntity target) {
            return translated ? PersistenceUtils.getInstance().getBasePKFromEntityInstance((EntityInstance)target)
                    : super.getLockTargetPK(target);
        }

        @Override
        public PartyPK getPartyPK() {
            return new PartyPK(99L);
        }

        @Override
        public UserVisit getUserVisit() {
            return null;
        }

        @Override
        protected EditEntityAppearanceResult getResult() {
            return CoreResultFactory.getEditEntityAppearanceResult();
        }

        @Override
        protected EntityAppearanceEdit getEdit() {
            return CoreEditFactory.getEntityAppearanceEdit();
        }

        @Override
        protected EntityAppearance getEntity(EditEntityAppearanceResult result) {
            return fixture.entityAppearance;
        }

        @Override
        protected EntityInstance getLockEntity(EntityAppearance entity) {
            return fixture.entityInstance;
        }

        @Override
        protected void fillInResult(EditEntityAppearanceResult result, EntityAppearance entity) {
            results++;
        }

        @Override
        protected void doLock(EntityAppearanceEdit edit, EntityAppearance entity) {
            operations.add("copy");
        }

        @Override
        protected void canUpdate(EntityAppearance entity) {
            if(rejectUpdate) {
                addExecutionError("Rejected");
            }
        }

        @Override
        protected void doUpdate(EntityAppearance entity) {
            operations.add("write");
            if(failUpdate) {
                throw new IllegalStateException("write failed");
            }
        }

        void executeEdit() {
            execute();
        }

        void recoverValidatorResult() {
            saveResultAfterEditValidatorErrors();
        }

    }

    static Stream<Arguments> modes() {
        return Stream.of("Item", "Party", "default").flatMap(type -> Stream.of(EditMode.values()).map(mode -> Arguments.of(type, mode)));
    }

    @ParameterizedTest
    @MethodSource("modes")
    public void routesAllModesToOneKeyAndRetainsDefaultResultContract(String type, EditMode mode) {
        var command = new TestCommand(mode, type.equals("default") ? null : type);
        command.executeEdit();
        assertEquals(switch(mode) {
            case LOCK -> List.of("lock", "copy", "transfer");
            case UPDATE -> List.of("update", "write", "unlock");
            case ABANDON -> List.of("unlock");
        }, command.operations);
        assertEquals(mode == EditMode.LOCK ? 1 : 0, command.results);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Item", "Party", "default"})
    public void failedAndStaleLocksAndUpdateRejectionReturnCorrectLock(String type) {
        for(var mode : List.of(EditMode.LOCK, EditMode.UPDATE)) {
            var command = new TestCommand(mode, type.equals("default") ? null : type);
            command.lockAllowed = false;
            command.executeEdit();
            assertEquals(List.of(mode == EditMode.LOCK ? "lock" : "update", "transfer"), command.operations);
            assertTrue(command.hasExecutionErrors());
            assertEquals(1, command.results);
        }
        var rejected = new TestCommand(EditMode.UPDATE, type.equals("default") ? null : type);
        rejected.rejectUpdate = true;
        rejected.executeEdit();
        assertEquals(List.of("transfer"), rejected.operations);
        assertEquals(1, rejected.results);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Item", "Party", "default"})
    public void persistenceFailureAlwaysReleasesTheSameKey(String type) {
        var command = new TestCommand(EditMode.UPDATE, type.equals("default") ? null : type);
        command.failUpdate = true;
        assertThrows(IllegalStateException.class, command::executeEdit);
        assertEquals(List.of("update", "write", "unlock"), command.operations);
        assertEquals(0, command.results);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Item", "Party", "default"})
    public void validatorRecoveryAndLockInspectionUseTheSameKey(String type) {
        var command = new TestCommand(EditMode.UPDATE, type.equals("default") ? null : type);
        command.recoverValidatorResult();
        assertTrue(command.isEntityLocked(command.fixture.entityInstance));
        assertEquals(List.of("transfer", "isLocked"), command.operations);
        assertEquals(1, command.results);
    }

}

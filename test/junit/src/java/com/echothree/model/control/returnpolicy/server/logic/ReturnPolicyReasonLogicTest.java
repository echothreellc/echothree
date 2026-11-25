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

package com.echothree.model.control.returnpolicy.server.logic;

import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.data.returnpolicy.common.pk.ReturnKindDetailPK;
import com.echothree.model.data.returnpolicy.common.pk.ReturnKindPK;
import com.echothree.model.data.returnpolicy.common.pk.ReturnPolicyDetailPK;
import com.echothree.model.data.returnpolicy.common.pk.ReturnPolicyPK;
import com.echothree.model.data.returnpolicy.common.pk.ReturnPolicyReasonPK;
import com.echothree.model.data.returnpolicy.common.pk.ReturnReasonDetailPK;
import com.echothree.model.data.returnpolicy.common.pk.ReturnReasonPK;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKindDetail;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicyDetail;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicyReason;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReason;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReasonDetail;
import com.echothree.model.data.returnpolicy.server.value.ReturnKindDetailValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnKindValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnPolicyDetailValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnPolicyReasonValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnPolicyValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnReasonDetailValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnReasonValue;
import com.echothree.model.data.sequence.common.pk.SequenceTypePK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.message.DummyExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class ReturnPolicyReasonLogicTest {

    enum MissingEntity { KIND, POLICY, REASON, RELATIONSHIP }

    private static class TestLogic extends ReturnPolicyReasonLogic {
        private final ReturnKind returnKind = new ReturnKind(new ReturnKindValue(new ReturnKindPK(1L), null, null), EntityPermission.READ_ONLY) {
            @Override
            public ReturnKindDetail getLastDetail() {
                return new ReturnKindDetail(new ReturnKindDetailValue(new ReturnKindDetailPK(2L), getPrimaryKey(), "KIND",
                        new SequenceTypePK(1L), false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
            }
        };
        private final ReturnPolicy returnPolicy = new ReturnPolicy(new ReturnPolicyValue(new ReturnPolicyPK(3L), null, null), EntityPermission.READ_ONLY) {
            @Override
            public ReturnPolicyDetail getLastDetail() {
                return new ReturnPolicyDetail(new ReturnPolicyDetailValue(new ReturnPolicyDetailPK(4L), getPrimaryKey(),
                        returnKind.getPrimaryKey(), "POLICY", false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                    @Override
                    public ReturnKind getReturnKind() {
                        return returnKind;
                    }
                };
            }
        };
        private final ReturnReason returnReason = new ReturnReason(new ReturnReasonValue(new ReturnReasonPK(5L), null, null), EntityPermission.READ_ONLY) {
            @Override
            public ReturnReasonDetail getLastDetail() {
                return new ReturnReasonDetail(new ReturnReasonDetailValue(new ReturnReasonDetailPK(6L), getPrimaryKey(),
                        returnKind.getPrimaryKey(), "REASON", false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
            }
        };
        private final ReturnPolicyReason relationship = new ReturnPolicyReason(new ReturnPolicyReasonValue(new ReturnPolicyReasonPK(7L),
                returnPolicy.getPrimaryKey(), returnReason.getPrimaryKey(), true, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_WRITE);
        private int kindLookups;
        private int policyLookups;
        private int reasonLookups;
        private int relationshipLookups;

        TestLogic(MissingEntity missingEntity, EntityPermission expectedPermission) {
            returnPolicyControl = new ReturnPolicyControl() {
                @Override
                public ReturnKind getReturnKindByName(String returnKindName, EntityPermission entityPermission) {
                    kindLookups++;
                    assertEquals("KIND", returnKindName);
                    assertEquals(EntityPermission.READ_ONLY, entityPermission);
                    return missingEntity == MissingEntity.KIND ? null : returnKind;
                }

                @Override
                public ReturnPolicy getReturnPolicyByName(ReturnKind selectedReturnKind, String returnPolicyName, EntityPermission entityPermission) {
                    policyLookups++;
                    assertSame(returnKind, selectedReturnKind);
                    assertEquals("POLICY", returnPolicyName);
                    assertEquals(EntityPermission.READ_ONLY, entityPermission);
                    return missingEntity == MissingEntity.POLICY ? null : returnPolicy;
                }

                @Override
                public ReturnReason getReturnReasonByName(ReturnKind selectedReturnKind, String returnReasonName, EntityPermission entityPermission) {
                    reasonLookups++;
                    assertSame(returnKind, selectedReturnKind);
                    assertEquals("REASON", returnReasonName);
                    assertEquals(EntityPermission.READ_ONLY, entityPermission);
                    return missingEntity == MissingEntity.REASON ? null : returnReason;
                }

                @Override
                public ReturnPolicyReason getReturnPolicyReason(ReturnPolicy selectedReturnPolicy, ReturnReason selectedReturnReason, EntityPermission entityPermission) {
                    relationshipLookups++;
                    assertSame(returnPolicy, selectedReturnPolicy);
                    assertSame(returnReason, selectedReturnReason);
                    assertEquals(expectedPermission, entityPermission);
                    return missingEntity == MissingEntity.RELATIONSHIP ? null : relationship;
                }
            };
            returnKindLogic = new ReturnKindLogic();
            returnKindLogic.returnPolicyControl = returnPolicyControl;
            returnPolicyLogic = new ReturnPolicyLogic();
            returnPolicyLogic.returnPolicyControl = returnPolicyControl;
            returnReasonLogic = new ReturnReasonLogic();
            returnReasonLogic.returnPolicyControl = returnPolicyControl;
            returnReasonLogic.returnKindLogic = returnKindLogic;
        }
    }

    @ParameterizedTest
    @EnumSource(EntityPermission.class)
    public void resolvesNamesWithReadOnlyParentsAndRequestedRelationshipPermission(EntityPermission entityPermission) {
        var logic = new TestLogic(null, entityPermission);
        var errors = new DummyExecutionErrorAccumulator();

        var relationship = entityPermission == EntityPermission.READ_ONLY
                ? logic.getReturnPolicyReason(errors, "KIND", "POLICY", "REASON")
                : logic.getReturnPolicyReasonForUpdate(errors, "KIND", "POLICY", "REASON");

        assertSame(logic.relationship, relationship);
        assertFalse(errors.hasExecutionErrors());
        assertEquals(1, logic.kindLookups);
        assertEquals(1, logic.policyLookups);
        assertEquals(1, logic.reasonLookups);
        assertEquals(1, logic.relationshipLookups);
    }

    @ParameterizedTest
    @EnumSource(MissingEntity.class)
    public void missingEntitiesReportCompleteKeysAndStopDependentLookups(MissingEntity missingEntity) {
        var logic = new TestLogic(missingEntity, EntityPermission.READ_WRITE);
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getReturnPolicyReasonForUpdate(errors, "KIND", "POLICY", "REASON"));
        var error = errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        var expectedError = switch(missingEntity) {
            case KIND -> ExecutionErrors.UnknownReturnKindName;
            case POLICY -> ExecutionErrors.UnknownReturnPolicyName;
            case REASON -> ExecutionErrors.UnknownReturnReasonName;
            case RELATIONSHIP -> ExecutionErrors.UnknownReturnPolicyReason;
        };
        var expectedNames = switch(missingEntity) {
            case KIND -> new Object[] { "KIND" };
            case POLICY -> new Object[] { "KIND", "POLICY" };
            case REASON -> new Object[] { "KIND", "REASON" };
            case RELATIONSHIP -> new Object[] { "KIND", "POLICY", "REASON" };
        };
        assertEquals(expectedError.name(), error.getKey());
        assertArrayEquals(expectedNames, error.getValues());
        assertEquals(missingEntity == MissingEntity.KIND ? 0 : 1, logic.policyLookups);
        assertEquals(missingEntity == MissingEntity.KIND ? 0 : 1, logic.reasonLookups);
        assertEquals(missingEntity == MissingEntity.RELATIONSHIP ? 1 : 0, logic.relationshipLookups);
    }

    @ParameterizedTest
    @EnumSource(EntityPermission.class)
    public void returnReasonNameOverloadsPreserveRequestedPermission(EntityPermission entityPermission) {
        var logic = new TestLogic(null, entityPermission);
        logic.returnReasonLogic.returnPolicyControl = new ReturnPolicyControl() {
            @Override
            public ReturnReason getReturnReasonByName(ReturnKind returnKind, String returnReasonName, EntityPermission permission) {
                assertSame(logic.returnKind, returnKind);
                assertEquals("REASON", returnReasonName);
                assertEquals(entityPermission, permission);
                return logic.returnReason;
            }
        };
        var errors = new DummyExecutionErrorAccumulator();
        var returnReason = entityPermission == EntityPermission.READ_ONLY
                ? logic.returnReasonLogic.getReturnReasonByName(errors, "KIND", "REASON")
                : logic.returnReasonLogic.getReturnReasonByNameForUpdate(errors, "KIND", "REASON");

        assertSame(logic.returnReason, returnReason);
        assertFalse(errors.hasExecutionErrors());
    }

}

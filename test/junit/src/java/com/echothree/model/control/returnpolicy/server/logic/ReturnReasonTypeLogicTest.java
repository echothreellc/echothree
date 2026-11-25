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
import com.echothree.model.data.returnpolicy.common.pk.ReturnReasonDetailPK;
import com.echothree.model.data.returnpolicy.common.pk.ReturnReasonPK;
import com.echothree.model.data.returnpolicy.common.pk.ReturnReasonTypePK;
import com.echothree.model.data.returnpolicy.common.pk.ReturnTypeDetailPK;
import com.echothree.model.data.returnpolicy.common.pk.ReturnTypePK;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKindDetail;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReason;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReasonDetail;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReasonType;
import com.echothree.model.data.returnpolicy.server.entity.ReturnType;
import com.echothree.model.data.returnpolicy.server.entity.ReturnTypeDetail;
import com.echothree.model.data.returnpolicy.server.value.ReturnKindDetailValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnKindValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnReasonDetailValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnReasonTypeValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnReasonValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnTypeDetailValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnTypeValue;
import com.echothree.model.data.sequence.common.pk.SequenceTypePK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.message.DummyExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class ReturnReasonTypeLogicTest {

    enum MissingEntity { KIND, REASON, TYPE, RELATIONSHIP }

    private static class TestLogic extends ReturnReasonTypeLogic {
        private final ReturnKind returnKind = new ReturnKind(new ReturnKindValue(new ReturnKindPK(1L), null, null), EntityPermission.READ_ONLY) {
            @Override
            public ReturnKindDetail getLastDetail() {
                return new ReturnKindDetail(new ReturnKindDetailValue(new ReturnKindDetailPK(2L), getPrimaryKey(), "KIND",
                        new SequenceTypePK(1L), false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
            }
        };
        private final ReturnReason returnReason = new ReturnReason(new ReturnReasonValue(new ReturnReasonPK(3L), null, null), EntityPermission.READ_ONLY) {
            @Override
            public ReturnReasonDetail getLastDetail() {
                return new ReturnReasonDetail(new ReturnReasonDetailValue(new ReturnReasonDetailPK(4L), getPrimaryKey(),
                        returnKind.getPrimaryKey(), "REASON", false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                    @Override
                    public ReturnKind getReturnKind() {
                        return returnKind;
                    }
                };
            }
        };
        private final ReturnType returnType = new ReturnType(new ReturnTypeValue(new ReturnTypePK(5L), null, null), EntityPermission.READ_ONLY) {
            @Override
            public ReturnTypeDetail getLastDetail() {
                return new ReturnTypeDetail(new ReturnTypeDetailValue(new ReturnTypeDetailPK(6L), getPrimaryKey(),
                        returnKind.getPrimaryKey(), "TYPE", null, false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
            }
        };
        private final ReturnReasonType relationship = new ReturnReasonType(new ReturnReasonTypeValue(new ReturnReasonTypePK(7L),
                returnReason.getPrimaryKey(), returnType.getPrimaryKey(), true, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_WRITE);
        private int kindLookups;
        private int reasonLookups;
        private int typeLookups;
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
                public ReturnReason getReturnReasonByName(ReturnKind selectedReturnKind, String returnReasonName, EntityPermission entityPermission) {
                    reasonLookups++;
                    assertSame(returnKind, selectedReturnKind);
                    assertEquals("REASON", returnReasonName);
                    assertEquals(EntityPermission.READ_ONLY, entityPermission);
                    return missingEntity == MissingEntity.REASON ? null : returnReason;
                }

                @Override
                public ReturnType getReturnTypeByName(ReturnKind selectedReturnKind, String returnTypeName, EntityPermission entityPermission) {
                    typeLookups++;
                    assertSame(returnKind, selectedReturnKind);
                    assertEquals("TYPE", returnTypeName);
                    assertEquals(EntityPermission.READ_ONLY, entityPermission);
                    return missingEntity == MissingEntity.TYPE ? null : returnType;
                }

                @Override
                public ReturnReasonType getReturnReasonType(ReturnReason selectedReturnReason, ReturnType selectedReturnType, EntityPermission entityPermission) {
                    relationshipLookups++;
                    assertSame(returnReason, selectedReturnReason);
                    assertSame(returnType, selectedReturnType);
                    assertEquals(expectedPermission, entityPermission);
                    return missingEntity == MissingEntity.RELATIONSHIP ? null : relationship;
                }
            };
            returnKindLogic = new ReturnKindLogic();
            returnKindLogic.returnPolicyControl = returnPolicyControl;
            returnReasonLogic = new ReturnReasonLogic();
            returnReasonLogic.returnPolicyControl = returnPolicyControl;
            returnTypeLogic = new ReturnTypeLogic();
            returnTypeLogic.returnPolicyControl = returnPolicyControl;
            returnTypeLogic.returnKindLogic = returnKindLogic;
        }
    }

    @ParameterizedTest
    @EnumSource(EntityPermission.class)
    public void resolvesNamesWithReadOnlyParentsAndRequestedRelationshipPermission(EntityPermission entityPermission) {
        var logic = new TestLogic(null, entityPermission);
        var errors = new DummyExecutionErrorAccumulator();

        var relationship = entityPermission == EntityPermission.READ_ONLY
                ? logic.getReturnReasonType(errors, "KIND", "REASON", "TYPE")
                : logic.getReturnReasonTypeForUpdate(errors, "KIND", "REASON", "TYPE");

        assertSame(logic.relationship, relationship);
        assertFalse(errors.hasExecutionErrors());
        assertEquals(1, logic.kindLookups);
        assertEquals(1, logic.reasonLookups);
        assertEquals(1, logic.typeLookups);
        assertEquals(1, logic.relationshipLookups);
    }

    @ParameterizedTest
    @EnumSource(MissingEntity.class)
    public void missingEntitiesReportCompleteKeysAndStopDependentLookups(MissingEntity missingEntity) {
        var logic = new TestLogic(missingEntity, EntityPermission.READ_WRITE);
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getReturnReasonTypeForUpdate(errors, "KIND", "REASON", "TYPE"));
        var error = errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        var expectedError = switch(missingEntity) {
            case KIND -> ExecutionErrors.UnknownReturnKindName;
            case REASON -> ExecutionErrors.UnknownReturnReasonName;
            case TYPE -> ExecutionErrors.UnknownReturnTypeName;
            case RELATIONSHIP -> ExecutionErrors.UnknownReturnReasonType;
        };
        var expectedNames = switch(missingEntity) {
            case KIND -> new Object[] { "KIND" };
            case REASON -> new Object[] { "KIND", "REASON" };
            case TYPE -> new Object[] { "KIND", "TYPE" };
            case RELATIONSHIP -> new Object[] { "KIND", "REASON", "TYPE" };
        };
        assertEquals(expectedError.name(), error.getKey());
        assertArrayEquals(expectedNames, error.getValues());
        assertEquals(missingEntity == MissingEntity.KIND ? 0 : 1, logic.reasonLookups);
        assertEquals(missingEntity == MissingEntity.KIND ? 0 : 1, logic.typeLookups);
        assertEquals(missingEntity == MissingEntity.RELATIONSHIP ? 1 : 0, logic.relationshipLookups);
    }

    @ParameterizedTest
    @EnumSource(EntityPermission.class)
    public void returnTypeNameOverloadsPreserveRequestedPermission(EntityPermission entityPermission) {
        var logic = new TestLogic(null, entityPermission);
        logic.returnTypeLogic.returnPolicyControl = new ReturnPolicyControl() {
            @Override
            public ReturnType getReturnTypeByName(ReturnKind returnKind, String returnTypeName, EntityPermission permission) {
                assertSame(logic.returnKind, returnKind);
                assertEquals("TYPE", returnTypeName);
                assertEquals(entityPermission, permission);
                return logic.returnType;
            }
        };
        var errors = new DummyExecutionErrorAccumulator();
        var returnType = entityPermission == EntityPermission.READ_ONLY
                ? logic.returnTypeLogic.getReturnTypeByName(errors, "KIND", "TYPE")
                : logic.returnTypeLogic.getReturnTypeByNameForUpdate(errors, "KIND", "TYPE");

        assertSame(logic.returnType, returnType);
        assertFalse(errors.hasExecutionErrors());
    }

}

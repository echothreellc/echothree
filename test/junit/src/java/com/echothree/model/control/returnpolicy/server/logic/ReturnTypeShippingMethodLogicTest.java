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
import com.echothree.model.control.shipping.server.logic.ShippingMethodLogic;
import com.echothree.model.data.returnpolicy.common.pk.ReturnKindDetailPK;
import com.echothree.model.data.returnpolicy.common.pk.ReturnKindPK;
import com.echothree.model.data.returnpolicy.common.pk.ReturnTypeDetailPK;
import com.echothree.model.data.returnpolicy.common.pk.ReturnTypePK;
import com.echothree.model.data.returnpolicy.common.pk.ReturnTypeShippingMethodPK;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKindDetail;
import com.echothree.model.data.returnpolicy.server.entity.ReturnType;
import com.echothree.model.data.returnpolicy.server.entity.ReturnTypeDetail;
import com.echothree.model.data.returnpolicy.server.entity.ReturnTypeShippingMethod;
import com.echothree.model.data.returnpolicy.server.value.ReturnKindDetailValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnKindValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnTypeDetailValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnTypeShippingMethodValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnTypeValue;
import com.echothree.model.data.sequence.common.pk.SequenceTypePK;
import com.echothree.model.data.shipping.common.pk.ShippingMethodDetailPK;
import com.echothree.model.data.shipping.common.pk.ShippingMethodPK;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.model.data.shipping.server.entity.ShippingMethodDetail;
import com.echothree.model.data.shipping.server.value.ShippingMethodDetailValue;
import com.echothree.model.data.shipping.server.value.ShippingMethodValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.message.DummyExecutionErrorAccumulator;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class ReturnTypeShippingMethodLogicTest {

    enum MissingEntity { KIND, TYPE, SHIPPING, RELATIONSHIP }

    private static class TestLogic extends ReturnTypeShippingMethodLogic {
        private final ReturnKind returnKind = new ReturnKind(new ReturnKindValue(new ReturnKindPK(1L), null, null), EntityPermission.READ_ONLY) {
            @Override
            public ReturnKindDetail getLastDetail() {
                return new ReturnKindDetail(new ReturnKindDetailValue(new ReturnKindDetailPK(2L), getPrimaryKey(), "KIND",
                        new SequenceTypePK(1L), false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
            }
        };
        private final ReturnType returnType = new ReturnType(new ReturnTypeValue(new ReturnTypePK(3L), null, null), EntityPermission.READ_ONLY) {
            @Override
            public ReturnTypeDetail getLastDetail() {
                return new ReturnTypeDetail(new ReturnTypeDetailValue(new ReturnTypeDetailPK(4L), getPrimaryKey(),
                        returnKind.getPrimaryKey(), "TYPE", null, false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                    @Override
                    public ReturnKind getReturnKind() {
                        return returnKind;
                    }
                };
            }
        };
        private final ShippingMethod shippingMethod = new ShippingMethod(new ShippingMethodValue(new ShippingMethodPK(5L), null, null), EntityPermission.READ_ONLY) {
            @Override
            public ShippingMethodDetail getLastDetail() {
                return new ShippingMethodDetail(new ShippingMethodDetailValue(new ShippingMethodDetailPK(6L), getPrimaryKey(),
                        "SHIPPING", null, null, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
            }
        };
        private final ReturnTypeShippingMethod relationship = new ReturnTypeShippingMethod(new ReturnTypeShippingMethodValue(
                new ReturnTypeShippingMethodPK(7L), returnType.getPrimaryKey(), shippingMethod.getPrimaryKey(), true, 0, 0L, Long.MAX_VALUE),
                EntityPermission.READ_WRITE);
        private int typeLookups;
        private int shippingLookups;
        private int relationshipLookups;

        TestLogic(MissingEntity missingEntity, EntityPermission expectedPermission) {
            returnPolicyControl = new ReturnPolicyControl() {
                @Override
                public ReturnKind getReturnKindByName(String returnKindName, EntityPermission entityPermission) {
                    assertEquals("KIND", returnKindName);
                    assertEquals(EntityPermission.READ_ONLY, entityPermission);
                    return missingEntity == MissingEntity.KIND ? null : returnKind;
                }

                @Override
                public ReturnType getReturnTypeByName(ReturnKind selectedReturnKind, String returnTypeName, EntityPermission entityPermission) {
                    typeLookups++;
                    assertSame(returnKind, selectedReturnKind);
                    assertEquals("TYPE", returnTypeName);
                    assertEquals(expectedPermission, entityPermission);
                    return missingEntity == MissingEntity.TYPE ? null : returnType;
                }

                @Override
                public ReturnTypeShippingMethod getReturnTypeShippingMethod(ReturnType selectedReturnType,
                        ShippingMethod selectedShippingMethod, EntityPermission entityPermission) {
                    relationshipLookups++;
                    assertSame(returnType, selectedReturnType);
                    assertSame(shippingMethod, selectedShippingMethod);
                    assertEquals(expectedPermission, entityPermission);
                    return missingEntity == MissingEntity.RELATIONSHIP ? null : relationship;
                }
            };
            returnTypeLogic = new ReturnTypeLogic();
            returnTypeLogic.returnPolicyControl = returnPolicyControl;
            returnTypeLogic.returnKindLogic = new ReturnKindLogic();
            returnTypeLogic.returnKindLogic.returnPolicyControl = returnPolicyControl;
            shippingMethodLogic = new ShippingMethodLogic() {
                @Override
                public ShippingMethod getShippingMethodByName(ExecutionErrorAccumulator eea, String shippingMethodName) {
                    shippingLookups++;
                    assertEquals("SHIPPING", shippingMethodName);
                    if(missingEntity == MissingEntity.SHIPPING) {
                        eea.addExecutionError(ExecutionErrors.UnknownShippingMethodName.name(), shippingMethodName);
                    }
                    return missingEntity == MissingEntity.SHIPPING ? null : shippingMethod;
                }
            };
        }
    }

    @ParameterizedTest
    @EnumSource(EntityPermission.class)
    public void resolvesNamesAndUsesRequestedRelationshipPermission(EntityPermission entityPermission) {
        var logic = new TestLogic(null, EntityPermission.READ_ONLY);
        // Parent lookups remain read-only even when updating the relationship.
        logic.returnPolicyControl = new ReturnPolicyControl() {
            @Override
            public ReturnTypeShippingMethod getReturnTypeShippingMethod(ReturnType returnType, ShippingMethod shippingMethod, EntityPermission permission) {
                assertSame(logic.returnType, returnType);
                assertSame(logic.shippingMethod, shippingMethod);
                assertEquals(entityPermission, permission);
                return logic.relationship;
            }
        };
        var errors = new DummyExecutionErrorAccumulator();
        var relationship = entityPermission == EntityPermission.READ_ONLY
                ? logic.getReturnTypeShippingMethod(errors, "KIND", "TYPE", "SHIPPING")
                : logic.getReturnTypeShippingMethodForUpdate(errors, "KIND", "TYPE", "SHIPPING");

        assertSame(logic.relationship, relationship);
        assertFalse(errors.hasExecutionErrors());
        assertEquals(1, logic.typeLookups);
        assertEquals(1, logic.shippingLookups);
    }

    @ParameterizedTest
    @EnumSource(MissingEntity.class)
    public void missingEntitiesReportCompleteKeysAndStopDependentLookups(MissingEntity missingEntity) {
        var logic = new TestLogic(missingEntity, EntityPermission.READ_ONLY);
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getReturnTypeShippingMethod(errors, "KIND", "TYPE", "SHIPPING"));
        var error = errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        var expectedError = switch(missingEntity) {
            case KIND -> ExecutionErrors.UnknownReturnKindName;
            case TYPE -> ExecutionErrors.UnknownReturnTypeName;
            case SHIPPING -> ExecutionErrors.UnknownShippingMethodName;
            case RELATIONSHIP -> ExecutionErrors.UnknownReturnTypeShippingMethod;
        };
        var expectedNames = switch(missingEntity) {
            case KIND -> new Object[] { "KIND" };
            case TYPE -> new Object[] { "KIND", "TYPE" };
            case SHIPPING -> new Object[] { "SHIPPING" };
            case RELATIONSHIP -> new Object[] { "KIND", "TYPE", "SHIPPING" };
        };
        assertEquals(expectedError.name(), error.getKey());
        assertArrayEquals(expectedNames, error.getValues());
        assertEquals(missingEntity == MissingEntity.KIND ? 0 : 1, logic.typeLookups);
        assertEquals(1, logic.shippingLookups);
        assertEquals(missingEntity == MissingEntity.RELATIONSHIP ? 1 : 0, logic.relationshipLookups);
    }

    @ParameterizedTest
    @EnumSource(EntityPermission.class)
    public void returnTypeNameOverloadsPreserveRequestedPermission(EntityPermission entityPermission) {
        var logic = new TestLogic(null, entityPermission);
        var errors = new DummyExecutionErrorAccumulator();
        var returnType = entityPermission == EntityPermission.READ_ONLY
                ? logic.returnTypeLogic.getReturnTypeByName(errors, "KIND", "TYPE")
                : logic.returnTypeLogic.getReturnTypeByNameForUpdate(errors, "KIND", "TYPE");

        assertSame(logic.returnType, returnType);
        assertFalse(errors.hasExecutionErrors());
    }

}

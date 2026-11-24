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

package com.echothree.control.user.shipment.server.command;

import com.echothree.control.user.shipment.common.edit.ShipmentEditFactory;
import com.echothree.control.user.shipment.common.result.EditShipmentTypeShippingMethodResult;
import com.echothree.control.user.shipment.common.spec.ShipmentSpecFactory;
import com.echothree.model.control.core.common.transfer.EntityLockTransfer;
import com.echothree.model.control.shipment.common.transfer.ShipmentTypeShippingMethodTransfer;
import com.echothree.model.control.shipment.server.control.ShipmentControl;
import com.echothree.model.control.shipment.server.logic.ShipmentTypeLogic;
import com.echothree.model.control.shipping.server.logic.ShippingMethodLogic;
import com.echothree.model.data.shipment.common.pk.ShipmentTypePK;
import com.echothree.model.data.shipment.common.pk.ShipmentTypeShippingMethodPK;
import com.echothree.model.data.shipment.server.entity.ShipmentType;
import com.echothree.model.data.shipment.server.entity.ShipmentTypeShippingMethod;
import com.echothree.model.data.shipment.server.value.ShipmentTypeValue;
import com.echothree.model.data.shipment.server.value.ShipmentTypeShippingMethodValue;
import com.echothree.model.data.shipping.common.pk.ShippingMethodPK;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.model.data.shipping.server.value.ShippingMethodValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class EditShipmentTypeShippingMethodCommandTest {

    public enum MissingEntity {
        SHIPMENT_TYPE, SHIPPING_METHOD, RELATIONSHIP
    }

    private static class TestCommand extends EditShipmentTypeShippingMethodCommand {
        private final ShipmentType shipmentType = new ShipmentType(
                new ShipmentTypeValue(new ShipmentTypePK(1L), null, null), EntityPermission.READ_ONLY);
        private final ShippingMethod shippingMethod = new ShippingMethod(
                new ShippingMethodValue(new ShippingMethodPK(2L), null, null), EntityPermission.READ_ONLY);
        private final ShipmentTypeShippingMethod relationship = new ShipmentTypeShippingMethod(
                new ShipmentTypeShippingMethodValue(new ShipmentTypeShippingMethodPK(3L), shipmentType.getPrimaryKey(),
                        shippingMethod.getPrimaryKey(), true, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_WRITE) {
            @Override
            public ShippingMethod getShippingMethod() {
                return shippingMethod;
            }
        };
        private final ShipmentTypeShippingMethodTransfer transfer = new ShipmentTypeShippingMethodTransfer(null, null, true, 0);
        private final EntityLockTransfer lockTransfer = new EntityLockTransfer(null, null, 0L, "0", 1L, "1");
        private int relationshipLookups;
        private int transferLookups;
        private int lockLookups;

        TestCommand(MissingEntity missingEntity, String isDefault, String sortOrder) {
            editMode = EditMode.UPDATE;
            spec = ShipmentSpecFactory.getShipmentTypeShippingMethodSpec();
            spec.setShipmentTypeName("SHIPMENT");
            spec.setShippingMethodName("SHIPPING");
            edit = ShipmentEditFactory.getShipmentTypeShippingMethodEdit();
            edit.setIsDefault(isDefault);
            edit.setSortOrder(sortOrder);

            shipmentTypeLogic = new ShipmentTypeLogic() {
                @Override
                public ShipmentType getShipmentTypeByName(ExecutionErrorAccumulator eea, String shipmentTypeName) {
                    assertEquals("SHIPMENT", shipmentTypeName);
                    if(missingEntity == MissingEntity.SHIPMENT_TYPE) {
                        eea.addExecutionError(ExecutionErrors.UnknownShipmentTypeName.name(), shipmentTypeName);
                    }
                    return missingEntity == MissingEntity.SHIPMENT_TYPE ? null : shipmentType;
                }
            };
            shippingMethodLogic = new ShippingMethodLogic() {
                @Override
                public ShippingMethod getShippingMethodByName(ExecutionErrorAccumulator eea, String shippingMethodName) {
                    assertEquals("SHIPPING", shippingMethodName);
                    if(missingEntity == MissingEntity.SHIPPING_METHOD) {
                        eea.addExecutionError(ExecutionErrors.UnknownShippingMethodName.name(), shippingMethodName);
                    }
                    return missingEntity == MissingEntity.SHIPPING_METHOD ? null : shippingMethod;
                }
            };
            shipmentControl = new ShipmentControl() {
                @Override
                public ShipmentTypeShippingMethod getShipmentTypeShippingMethod(ShipmentType selectedShipmentType,
                        ShippingMethod selectedShippingMethod, EntityPermission entityPermission) {
                    relationshipLookups++;
                    assertSame(shipmentType, selectedShipmentType);
                    assertSame(shippingMethod, selectedShippingMethod);
                    assertEquals(EntityPermission.READ_WRITE, entityPermission);
                    return missingEntity == MissingEntity.RELATIONSHIP ? null : relationship;
                }

                @Override
                public ShipmentTypeShippingMethodTransfer getShipmentTypeShippingMethodTransfer(UserVisit userVisit,
                        ShipmentTypeShippingMethod shipmentTypeShippingMethod) {
                    transferLookups++;
                    assertSame(relationship, shipmentTypeShippingMethod);
                    return transfer;
                }
            };
        }

        @Override
        public UserVisit getUserVisit() {
            return null;
        }

        @Override
        public EntityLockTransfer getEntityLockTransfer(BaseEntity lockTarget) {
            lockLookups++;
            assertSame(shippingMethod, lockTarget);
            return lockTransfer;
        }

        ValidationResult validateRequest() {
            return validate();
        }

        void clearShipmentTypeName() {
            spec.setShipmentTypeName(null);
        }

        EditShipmentTypeShippingMethodResult savedResult() {
            return (EditShipmentTypeShippingMethodResult)getBaseResultAfterErrors();
        }
    }

    @ParameterizedTest
    @EnumSource(MissingEntity.class)
    public void invalidEditAndMissingEntityPreserveErrorsWithoutAccessingTransfers(MissingEntity missingEntity) {
        var command = new TestCommand(missingEntity, "true", "invalid");
        var validationResult = assertDoesNotThrow(command::validateRequest);

        assertTrue(validationResult.getValidationMessages().containsKey("SortOrder", Validator.ERROR_INVALID_FORMAT));
        assertTrue(command.hasExecutionErrors());
        var executionError = command.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        var expectedError = switch(missingEntity) {
            case SHIPMENT_TYPE -> ExecutionErrors.UnknownShipmentTypeName;
            case SHIPPING_METHOD -> ExecutionErrors.UnknownShippingMethodName;
            case RELATIONSHIP -> ExecutionErrors.UnknownShipmentTypeShippingMethod;
        };
        var expectedNames = switch(missingEntity) {
            case SHIPMENT_TYPE -> new Object[] { "SHIPMENT" };
            case SHIPPING_METHOD -> new Object[] { "SHIPPING" };
            case RELATIONSHIP -> new Object[] { "SHIPMENT", "SHIPPING" };
        };
        assertEquals(expectedError.name(), executionError.getKey());
        assertArrayEquals(expectedNames, executionError.getValues());
        assertNotNull(command.savedResult());
        assertNull(command.savedResult().getShipmentTypeShippingMethod());
        assertNull(command.savedResult().getEntityLock());
        assertEquals(missingEntity == MissingEntity.RELATIONSHIP ? 1 : 0, command.relationshipLookups);
        assertEquals(0, command.transferLookups);
        assertEquals(0, command.lockLookups);
    }

    @Test
    public void invalidEditWithExistingEntityRetainsTransferAndShippingMethodLock() {
        var command = new TestCommand(null, "true", "invalid");
        var validationResult = command.validateRequest();

        assertTrue(validationResult.getValidationMessages().containsKey("SortOrder", Validator.ERROR_INVALID_FORMAT));
        assertFalse(command.hasExecutionErrors());
        assertSame(command.transfer, command.savedResult().getShipmentTypeShippingMethod());
        assertSame(command.lockTransfer, command.savedResult().getEntityLock());
        assertEquals(1, command.relationshipLookups);
        assertEquals(1, command.transferLookups);
        assertEquals(1, command.lockLookups);
    }

    @Test
    public void missingRequiredEditValueAlsoRetainsNonNullErrorResult() {
        var command = new TestCommand(MissingEntity.RELATIONSHIP, null, "0");
        var validationResult = assertDoesNotThrow(command::validateRequest);

        assertTrue(validationResult.getValidationMessages().containsKey("IsDefault", Validator.ERROR_REQUIRED_FIELD));
        assertTrue(command.hasExecutionErrors());
        assertNotNull(command.savedResult());
        assertNull(command.savedResult().getEntityLock());
    }

    @Test
    public void validEditDoesNotPopulateValidationErrorResult() {
        var command = new TestCommand(null, "true", "0");

        assertFalse(command.validateRequest().getHasErrors());
        assertNull(command.savedResult());
        assertFalse(command.hasExecutionErrors());
        assertEquals(0, command.relationshipLookups);
        assertEquals(0, command.transferLookups);
        assertEquals(0, command.lockLookups);
    }

    @Test
    public void invalidSpecStopsBeforeEntityLookup() {
        var command = new TestCommand(null, "true", "invalid");
        command.clearShipmentTypeName();
        var validationResult = command.validateRequest();

        assertTrue(validationResult.getValidationMessages().containsKey("ShipmentTypeName", Validator.ERROR_REQUIRED_FIELD));
        assertFalse(validationResult.getValidationMessages().containsKey("SortOrder", Validator.ERROR_INVALID_FORMAT));
        assertNull(command.savedResult());
        assertEquals(0, command.relationshipLookups);
        assertEquals(0, command.transferLookups);
        assertEquals(0, command.lockLookups);
    }

}

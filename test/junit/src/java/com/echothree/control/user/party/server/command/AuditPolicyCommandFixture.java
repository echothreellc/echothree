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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.result.EditPartyTypeAuditPolicyResult;
import com.echothree.control.user.party.common.spec.PartySpecFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.transfer.EntityLockTransfer;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.control.EventControl;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.transfer.PartyTypeTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyTypeLogic;
import com.echothree.model.control.party.server.logic.PartyTypeAuditPolicyLogic;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureKindLogic;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.data.accounting.common.pk.SymbolPositionPK;
import com.echothree.model.data.core.common.pk.ComponentVendorDetailPK;
import com.echothree.model.data.core.common.pk.ComponentVendorPK;
import com.echothree.model.data.core.common.pk.EntityInstancePK;
import com.echothree.model.data.core.common.pk.EntityTimePK;
import com.echothree.model.data.core.common.pk.EntityTypeDetailPK;
import com.echothree.model.data.core.common.pk.EntityTypePK;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.ComponentVendorDetail;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityTime;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.EntityTypeDetail;
import com.echothree.model.data.core.server.value.ComponentVendorDetailValue;
import com.echothree.model.data.core.server.value.ComponentVendorValue;
import com.echothree.model.data.core.server.value.EntityInstanceValue;
import com.echothree.model.data.core.server.value.EntityTimeValue;
import com.echothree.model.data.core.server.value.EntityTypeDetailValue;
import com.echothree.model.data.core.server.value.EntityTypeValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.common.pk.PartyTypePK;
import com.echothree.model.data.party.common.pk.PartyTypeAuditPolicyDetailPK;
import com.echothree.model.data.party.common.pk.PartyTypeAuditPolicyPK;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.entity.PartyTypeAuditPolicy;
import com.echothree.model.data.party.server.entity.PartyTypeAuditPolicyDetail;
import com.echothree.model.data.party.server.value.PartyTypeAuditPolicyDetailValue;
import com.echothree.model.data.party.server.value.PartyTypeAuditPolicyValue;
import com.echothree.model.data.party.server.value.PartyTypeValue;
import com.echothree.model.data.uom.common.pk.UnitOfMeasureEquivalentPK;
import com.echothree.model.data.uom.common.pk.UnitOfMeasureKindDetailPK;
import com.echothree.model.data.uom.common.pk.UnitOfMeasureKindPK;
import com.echothree.model.data.uom.common.pk.UnitOfMeasureTypeDetailPK;
import com.echothree.model.data.uom.common.pk.UnitOfMeasureTypePK;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureEquivalent;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindDetail;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureTypeDetail;
import com.echothree.model.data.uom.server.value.UnitOfMeasureEquivalentValue;
import com.echothree.model.data.uom.server.value.UnitOfMeasureKindDetailValue;
import com.echothree.model.data.uom.server.value.UnitOfMeasureKindValue;
import com.echothree.model.data.uom.server.value.UnitOfMeasureTypeDetailValue;
import com.echothree.model.data.uom.server.value.UnitOfMeasureTypeValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.EntityPermission;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

/** Runs real lookup Logic and time Conversion with in-memory persistence boundaries. */
@SuppressWarnings("UnnecessaryAssignment")
class AuditPolicyCommandFixture extends EditPartyTypeAuditPolicyCommand {
    static final String UUID = "c20fef36-8c15-49cc-8d5b-97b9d851e86a";
    static final String ENTITY_REF = "ECHO_THREE.PartyType.1";
    static final List<String> TIME_FIELDS = List.of("RetainUserVisitsTime");
    static final Map<String, Object> INITIAL = initialValues();
    final PartyType partyType = new PartyType(new PartyTypeValue(new PartyTypePK(1L), "CUSTOMER", null, null,
            true, false, false, 0), EntityPermission.READ_ONLY);
    final PartyTypeAuditPolicy policy;
    final UnitOfMeasureKind timeKind;
    final List<UnitOfMeasureType> units = new ArrayList<>();
    final List<UnitOfMeasureEquivalent> equivalents = new ArrayList<>();
    final List<EntityPermission> policyPermissions = new ArrayList<>();
    final List<String> unitLookups = new ArrayList<>();
    int parentLookups;
    int policyLookups;
    int kindLookups;
    int locks;
    int unlocks;
    int transfers;
    int writes;
    int updateCalls;
    boolean lockSucceeds = true;
    boolean populated = true;
    boolean failUpdate;
    final Map<String, Object> initialOverrides = new LinkedHashMap<>();
    PartyTypeAuditPolicyDetailValue savedValue;

    static Map<String, Object> initialValues() {
        var values = new LinkedHashMap<String, Object>();
        values.put("AuditCommands", true);
        values.put("RetainUserVisitsTime", 3600000L);
        return values;
    }

    static Object read(Object bean, String property) throws Exception {
        return bean.getClass().getMethod("get" + property).invoke(bean);
    }

    // Missing fields are allowed only so compatibility tests can also run against the original command.
    static void inject(Object bean, String name, Object value) throws Exception {
        Class<?> type = bean.getClass();
        while(type != null) {
            try {
                var field = type.getDeclaredField(name);
                field.setAccessible(true);
                field.set(bean, value);
                break;
            } catch(NoSuchFieldException exception) {
                type = type.getSuperclass();
            }
        }
    }

    AuditPolicyCommandFixture(EditMode mode, String failure) throws Exception {
        editMode = mode;
        spec = PartySpecFactory.getPartyTypeUniversalSpec();
        spec.setPartyTypeName("CUSTOMER");
        edit = PartyEditFactory.getPartyTypeAuditPolicyEdit();
        initialEdit();
        timeKind = new UnitOfMeasureKind(new UnitOfMeasureKindValue(new UnitOfMeasureKindPK(10L), null, null), EntityPermission.READ_ONLY) {
            @Override public UnitOfMeasureKindDetail getLastDetail() {
                return new UnitOfMeasureKindDetail(new UnitOfMeasureKindDetailValue(new UnitOfMeasureKindDetailPK(10L), getPrimaryKey(),
                        "TIME", 0, false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
            }
        };
        var names = List.of("MS", "SECOND", "MINUTE", "HOUR");
        for(var index = 0; index < names.size(); index++) {
            var name = names.get(index);
            var identifier = 20L + index;
            units.add(new UnitOfMeasureType(new UnitOfMeasureTypeValue(new UnitOfMeasureTypePK(identifier), null, null), EntityPermission.READ_ONLY) {
                @Override public UnitOfMeasureTypeDetail getLastDetail() {
                    return new UnitOfMeasureTypeDetail(new UnitOfMeasureTypeDetailValue(new UnitOfMeasureTypeDetailPK(identifier), getPrimaryKey(),
                            timeKind.getPrimaryKey(), name, new SymbolPositionPK(1L), false, false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
                }
            });
        }
        for(var index = 1; index < units.size(); index++) {
            var from = units.get(index);
            var to = units.get(index - 1);
            equivalents.add(new UnitOfMeasureEquivalent(new UnitOfMeasureEquivalentValue(new UnitOfMeasureEquivalentPK(30L + index),
                    from.getPrimaryKey(), to.getPrimaryKey(), index == 1 ? 1000L : 60L, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                @Override public UnitOfMeasureType getFromUnitOfMeasureType() { return from; }
                @Override public UnitOfMeasureType getToUnitOfMeasureType() { return to; }
            });
        }
        policy = new PartyTypeAuditPolicy(new PartyTypeAuditPolicyValue(new PartyTypeAuditPolicyPK(2L),
                new PartyTypeAuditPolicyDetailPK(3L), new PartyTypeAuditPolicyDetailPK(3L)), EntityPermission.READ_ONLY) {
            @Override public PartyTypeAuditPolicyDetail getLastDetail() {
                return new PartyTypeAuditPolicyDetail(detailValue(), EntityPermission.READ_ONLY) {
                    @Override public PartyType getPartyType() { return partyType; }
                };
            }
        };
        var selectedPartyControl = new PartyControl() {
            @Override public PartyType getPartyTypeByName(String name, EntityPermission permission) {
                assertEquals("CUSTOMER", name);
                assertEquals(EntityPermission.READ_ONLY, permission);
                parentLookups++;
                return "parent".equals(failure) ? null : partyType;
            }
            @Override public PartyType getPartyTypeByEntityInstance(EntityInstance instance, EntityPermission permission) {
                assertEquals(1L, instance.getEntityUniqueId());
                assertEquals(EntityPermission.READ_ONLY, permission);
                parentLookups++;
                return partyType;
            }
            @Override public PartyTypeAuditPolicy getPartyTypeAuditPolicy(PartyType parent, EntityPermission permission) {
                assertSame(partyType, parent);
                policyLookups++;
                policyPermissions.add(permission);
                return "policy".equals(failure) ? null : policy;
            }
            @Override public PartyTypeAuditPolicyDetailValue getPartyTypeAuditPolicyDetailValueForUpdate(PartyTypeAuditPolicy selectedPolicy) {
                assertSame(policy, selectedPolicy);
                return detailValue();
            }
            @Override public void updatePartyTypeAuditPolicyFromValue(PartyTypeAuditPolicyDetailValue value, BasePK updatedBy) {
                assertEquals(new PartyPK(99L), updatedBy);
                updateCalls++;
                savedValue = value;
                if(failUpdate) { throw new IllegalStateException("Store failure"); }
                if(value.hasBeenModified()) { writes++; }
            }
            @Override public PartyTypeTransfer getPartyTypeTransfer(UserVisit visit, PartyType selectedPartyType) {
                assertSame(partyType, selectedPartyType);
                transfers++;
                return null;
            }
        };
        var selectedUomControl = new UomControl() {
            @Override public UnitOfMeasureKind getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(String name) {
                assertEquals(UomConstants.UnitOfMeasureKindUseType_TIME, name);
                kindLookups++;
                return "kind".equals(failure) ? null : timeKind;
            }
            @Override public UnitOfMeasureType getUnitOfMeasureTypeByName(UnitOfMeasureKind kind, String name) {
                assertSame(timeKind, kind);
                unitLookups.add(name);
                return units.stream().filter(unit -> unit.getLastDetail().getUnitOfMeasureTypeName().equals(name)).findFirst().orElse(null);
            }
            @Override public List<UnitOfMeasureType> getUnitOfMeasureTypesByUnitOfMeasureKind(UnitOfMeasureKind kind) {
                assertSame(timeKind, kind);
                return units;
            }
            @Override public List<UnitOfMeasureEquivalent> getUnitOfMeasureEquivalentsByFromUnitOfMeasureType(UnitOfMeasureType unit) {
                return equivalents.stream().filter(equivalent -> equivalent.getFromUnitOfMeasureType().equals(unit)).toList();
            }
            @Override public List<UnitOfMeasureEquivalent> getUnitOfMeasureEquivalentsByToUnitOfMeasureType(UnitOfMeasureType unit) {
                return equivalents.stream().filter(equivalent -> equivalent.getToUnitOfMeasureType().equals(unit)).toList();
            }
        };
        var vendorName = "entity:InvalidVendor".equals(failure) ? "OTHER" : ComponentVendors.ECHO_THREE.name();
        var entityTypeName = "entity:InvalidEntityType".equals(failure) ? "PartyTypeAuditPolicy" : EntityTypes.PartyType.name();
        var componentVendor = new ComponentVendor(new ComponentVendorValue(new ComponentVendorPK(50L), null, null), EntityPermission.READ_ONLY) {
            @Override public ComponentVendorDetail getLastDetail() {
                return new ComponentVendorDetail(new ComponentVendorDetailValue(new ComponentVendorDetailPK(51L), getPrimaryKey(),
                        vendorName, "Vendor", 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
            }
        };
        var entityType = new EntityType(new EntityTypeValue(new EntityTypePK(41L), null, null), EntityPermission.READ_ONLY) {
            @Override public EntityTypeDetail getLastDetail() {
                return new EntityTypeDetail(new EntityTypeDetailValue(new EntityTypeDetailPK(42L), getPrimaryKey(), componentVendor.getPrimaryKey(),
                        entityTypeName, false, 1000L, false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                    @Override public ComponentVendor getComponentVendor() { return componentVendor; }
                };
            }
        };
        var entityInstance = new EntityInstance(new EntityInstanceValue(new EntityInstancePK(40L), entityType.getPrimaryKey(), 1L, UUID), EntityPermission.READ_ONLY) {
            @Override public EntityType getEntityType() { return entityType; }
        };
        var selectedInstanceControl = new EntityInstanceControl() {
            @Override public EntityInstance getEntityInstanceByUuid(String uuid) {
                assertEquals(UUID, uuid);
                return "entity:UnknownUuid".equals(failure) ? null : entityInstance;
            }
            @Override public EntityInstance getEntityInstanceByEntityRef(String entityRef) {
                assertEquals(ENTITY_REF, entityRef);
                return "entity:UnknownEntityRef".equals(failure) ? null : entityInstance;
            }
        };
        var selectedEventControl = new EventControl() {
            @Override public EntityTime getEntityTime(EntityInstance instance) {
                assertSame(entityInstance, instance);
                return new EntityTime(new EntityTimeValue(new EntityTimePK(43L), instance.getPrimaryKey(), 0L, null,
                        failure != null && failure.startsWith("entity:Deleted") ? 1000L : null), EntityPermission.READ_ONLY);
            }
        };
        var selectedEntityLogic = new EntityInstanceLogic() {};
        inject(selectedEntityLogic, "entityInstanceControl", selectedInstanceControl);
        inject(selectedEntityLogic, "eventControl", selectedEventControl);
        var selectedPartyTypeLogic = new PartyTypeLogic() {};
        inject(selectedPartyTypeLogic, "partyControl", selectedPartyControl);
        inject(selectedPartyTypeLogic, "entityInstanceLogic", selectedEntityLogic);
        var selectedPolicyLogic = new PartyTypeAuditPolicyLogic() {};
        inject(selectedPolicyLogic, "partyControl", selectedPartyControl);
        var selectedKindLogic = new UnitOfMeasureKindLogic() {};
        inject(selectedKindLogic, "uomControl", selectedUomControl);
        var selectedTypeLogic = new UnitOfMeasureTypeLogic() {};
        inject(selectedTypeLogic, "uomControl", selectedUomControl);
        inject(this, "partyControl", selectedPartyControl);
        inject(this, "uomControl", selectedUomControl);
        inject(this, "partyTypeLogic", selectedPartyTypeLogic);
        inject(this, "partyTypeAuditPolicyLogic", selectedPolicyLogic);
        inject(this, "unitOfMeasureKindLogic", selectedKindLogic);
        inject(this, "unitOfMeasureTypeLogic", selectedTypeLogic);
    }

    private Object stored(String name) {
        var value = initialOverrides.getOrDefault(name, INITIAL.get(name));
        return populated || value instanceof Boolean ? value : null;
    }

    PartyTypeAuditPolicyDetailValue detailValue() {
        return new PartyTypeAuditPolicyDetailValue(new PartyTypeAuditPolicyDetailPK(3L), policy.getPrimaryKey(), partyType.getPrimaryKey(),
                (Boolean)stored("AuditCommands"), (Long)stored("RetainUserVisitsTime"), 0L, Long.MAX_VALUE);
    }

    void initialEdit() {
        INITIAL.forEach((name, value) -> edit.set(name, value.toString()));
        for(var field : TIME_FIELDS) { edit.set(field + "UnitOfMeasureTypeName", "MS"); }
    }
    void clearOptionalEdit() {
        INITIAL.forEach((name, value) -> { if(!(value instanceof Boolean)) { edit.set(name, null); } });
        TIME_FIELDS.forEach(field -> edit.set(field + "UnitOfMeasureTypeName", null));
    }
    void set(String name, String value) { edit.set(name, value); }
    void identifierMask(int mask) {
        spec.setPartyTypeName((mask & 1) == 0 ? null : "CUSTOMER");
        spec.set("Uuid", (mask & 2) == 0 ? null : UUID);
        spec.set("EntityRef", (mask & 4) == 0 ? null : ENTITY_REF);
    }
    EditPartyTypeAuditPolicyResult executeEdit() { return (EditPartyTypeAuditPolicyResult)execute(); }
    void validateRequest() { assertFalse(validate().getHasErrors()); }
    void verifyValidationErrorResult(String name, String value) {
        edit.set(name, value);
        assertTrue(validate().getHasErrors());
        assertNotNull(getBaseResultAfterErrors());
    }
    @Override public PartyPK getPartyPK() { return new PartyPK(99L); }
    @Override public UserVisit getUserVisit() { return null; }
    @Override public boolean lockEntity(BaseEntity target) { assertSame(policy, target); locks++; return lockSucceeds; }
    @Override public boolean lockEntityForUpdate(BaseEntity target) { assertSame(policy, target); locks++; return lockSucceeds; }
    @Override public boolean unlockEntity(BaseEntity target) { assertSame(policy, target); unlocks++; return true; }
    @Override public EntityLockTransfer getEntityLockTransfer(BaseEntity target) { assertSame(policy, target); return null; }
}

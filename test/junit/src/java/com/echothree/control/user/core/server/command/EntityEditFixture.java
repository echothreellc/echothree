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
import com.echothree.control.user.core.common.spec.CoreSpecFactory;
import com.echothree.control.user.core.common.spec.UniversalEntitySpec;
import com.echothree.model.control.core.common.transfer.EntityAliasTransfer;
import com.echothree.model.control.core.common.transfer.EntityAppearanceTransfer;
import com.echothree.model.control.core.common.transfer.EntityBlobAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityBooleanAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityClobAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityDateAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityEntityAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityGeoPointAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityIntegerAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityListItemAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityListItemDefaultTransfer;
import com.echothree.model.control.core.common.transfer.EntityLockTransfer;
import com.echothree.model.control.core.common.transfer.EntityLongAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityNameAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityStringAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityTimeAttributeTransfer;
import com.echothree.model.control.core.server.control.AppearanceControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EntityAliasControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.logic.AppearanceLogic;
import com.echothree.model.control.core.server.logic.EntityAliasTypeLogic;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityBlobAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityBooleanAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityClobAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityDateAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityEntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityGeoPointAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.core.server.logic.EntityIntegerAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityListItemAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityListItemLogic;
import com.echothree.model.control.core.server.logic.EntityLongAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityNameAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityStringAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityTimeAttributeLogic;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.core.common.pk.EntityAliasPK;
import com.echothree.model.data.core.common.pk.EntityAttributePK;
import com.echothree.model.data.core.common.pk.EntityAttributeTypePK;
import com.echothree.model.data.core.common.pk.EntityTypePK;
import com.echothree.model.data.core.server.entity.Appearance;
import com.echothree.model.data.core.server.entity.AppearanceDetail;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.ComponentVendorDetail;
import com.echothree.model.data.core.server.entity.EntityAlias;
import com.echothree.model.data.core.server.entity.EntityAliasType;
import com.echothree.model.data.core.server.entity.EntityAliasTypeDetail;
import com.echothree.model.data.core.server.entity.EntityAppearance;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeDetail;
import com.echothree.model.data.core.server.entity.EntityAttributeInteger;
import com.echothree.model.data.core.server.entity.EntityAttributeLong;
import com.echothree.model.data.core.server.entity.EntityAttributeString;
import com.echothree.model.data.core.server.entity.EntityAttributeType;
import com.echothree.model.data.core.server.entity.EntityBlobAttribute;
import com.echothree.model.data.core.server.entity.EntityBooleanAttribute;
import com.echothree.model.data.core.server.entity.EntityClobAttribute;
import com.echothree.model.data.core.server.entity.EntityDateAttribute;
import com.echothree.model.data.core.server.entity.EntityEntityAttribute;
import com.echothree.model.data.core.server.entity.EntityGeoPointAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityIntegerAttribute;
import com.echothree.model.data.core.server.entity.EntityListItem;
import com.echothree.model.data.core.server.entity.EntityListItemAttribute;
import com.echothree.model.data.core.server.entity.EntityListItemDefault;
import com.echothree.model.data.core.server.entity.EntityListItemDetail;
import com.echothree.model.data.core.server.entity.EntityLongAttribute;
import com.echothree.model.data.core.server.entity.EntityNameAttribute;
import com.echothree.model.data.core.server.entity.EntityStringAttribute;
import com.echothree.model.data.core.server.entity.EntityTimeAttribute;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.EntityTypeDetail;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeDetail;
import com.echothree.model.data.core.server.value.AppearanceDetailValue;
import com.echothree.model.data.core.server.value.AppearanceValue;
import com.echothree.model.data.core.server.value.ComponentVendorDetailValue;
import com.echothree.model.data.core.server.value.ComponentVendorValue;
import com.echothree.model.data.core.server.value.EntityAliasTypeDetailValue;
import com.echothree.model.data.core.server.value.EntityAliasTypeValue;
import com.echothree.model.data.core.server.value.EntityAliasValue;
import com.echothree.model.data.core.server.value.EntityAppearanceValue;
import com.echothree.model.data.core.server.value.EntityAttributeDetailValue;
import com.echothree.model.data.core.server.value.EntityAttributeIntegerValue;
import com.echothree.model.data.core.server.value.EntityAttributeLongValue;
import com.echothree.model.data.core.server.value.EntityAttributeStringValue;
import com.echothree.model.data.core.server.value.EntityAttributeTypeValue;
import com.echothree.model.data.core.server.value.EntityAttributeValue;
import com.echothree.model.data.core.server.value.EntityBlobAttributeValue;
import com.echothree.model.data.core.server.value.EntityBooleanAttributeValue;
import com.echothree.model.data.core.server.value.EntityClobAttributeValue;
import com.echothree.model.data.core.server.value.EntityDateAttributeValue;
import com.echothree.model.data.core.server.value.EntityEntityAttributeValue;
import com.echothree.model.data.core.server.value.EntityGeoPointAttributeValue;
import com.echothree.model.data.core.server.value.EntityInstanceValue;
import com.echothree.model.data.core.server.value.EntityIntegerAttributeValue;
import com.echothree.model.data.core.server.value.EntityListItemAttributeValue;
import com.echothree.model.data.core.server.value.EntityListItemDefaultValue;
import com.echothree.model.data.core.server.value.EntityListItemDetailValue;
import com.echothree.model.data.core.server.value.EntityListItemValue;
import com.echothree.model.data.core.server.value.EntityLongAttributeValue;
import com.echothree.model.data.core.server.value.EntityNameAttributeValue;
import com.echothree.model.data.core.server.value.EntityStringAttributeValue;
import com.echothree.model.data.core.server.value.EntityTimeAttributeValue;
import com.echothree.model.data.core.server.value.EntityTypeDetailValue;
import com.echothree.model.data.core.server.value.EntityTypeValue;
import com.echothree.model.data.core.server.value.MimeTypeDetailValue;
import com.echothree.model.data.core.server.value.MimeTypeValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.value.LanguageValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.exception.BaseException;
import com.echothree.util.common.form.BaseEdit;
import com.echothree.util.common.form.BaseSpec;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.BaseCommand;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.BaseValue;
import com.echothree.util.server.persistence.EntityPermission;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Runs real command lifecycles and Logic resolution against in-memory persistence boundaries.
 */
@SuppressWarnings({"UnnecessaryAssignment", "unchecked", "rawtypes"})
class EntityEditFixture {

    final String kind;
    BaseAbstractEditCommand command;
    Object lookupLogic;
    final List<String> deletions = new ArrayList<>();
    Map<String, Object> dependencies;
    BaseSpec spec;
    BaseEdit edit;
    boolean missingValue;
    boolean failUpdate;
    boolean failTransfer;
    boolean allowLock = true;
    boolean duplicateAlias;
    int duplicateAliasLookups;
    final EntityLockTransfer ownerLockTransfer = new EntityLockTransfer(null, null, 100L, "locked", 1000L, "expires");
    boolean sameAlias;
    boolean wrongListItem;
    boolean missingOwner;
    boolean deletedOwner;
    boolean missingAttribute;
    boolean missingLanguage;
    boolean missingAppearance;
    boolean missingMimeType;
    String mimeStorageType;
    boolean wrongAttributeOwner;
    boolean wrongAliasOwner;
    boolean restrictReferencedTypes;
    boolean referencedTypeAllowed;
    final List<String> persistenceOperations = new ArrayList<>();
    int updates;
    int transfers;
    int valueLookups;
    int readWriteLookups;
    final List<String> locks = new ArrayList<>();
    BaseValue writtenValue;
    final PartyPK updatedBy = new PartyPK(99L);
    final Map<String, Object> values = new HashMap<>();

    // Null-accumulator tests isolate server-backed exception message localization.
    static void throwLookupException(Class<? extends BaseException> exceptionClass, String key) {
        BaseException exception;
        try {
            exception = exceptionClass.getConstructor(String.class).newInstance(key);
        } catch(ReflectiveOperationException reflectionException) {
            throw new LinkageError(reflectionException.getMessage(), reflectionException);
        }
        throw exception;
    }

    void injectCommandDependencies(Object target) {
        for(var type = target.getClass(); type != null; type = type.getSuperclass()) {
            for(var field : type.getDeclaredFields()) {
                if(dependencies.containsKey(field.getName())) {
                    inject(target, field.getName(), dependencies.get(field.getName()));
                }
            }
        }
    }

    static void inject(Object target, String name, Object value) {
        Class<?> type = target.getClass();
        boolean assigned = false;
        while(type != null && !assigned) {
            try {
                var field = type.getDeclaredField(name);
                field.setAccessible(true);
                field.set(target, value);
                assigned = true;
            } catch(NoSuchFieldException exception) {
                type = type.getSuperclass();
            } catch(ReflectiveOperationException exception) {
                throw new LinkageError(exception.getMessage(), exception);
            }
        }
        if(!assigned) {
            throw new AssertionError("Missing dependency: " + name);
        }
    }

    static <T> T construct(Class<T> type) {
        try {
            var constructor = Arrays.stream(type.getConstructors()).max(Comparator.comparingInt(Constructor::getParameterCount)).orElseThrow();
            var arguments = Arrays.stream(constructor.getParameterTypes()).map(parameter -> {
                Object value;
                try {
                    if(BasePK.class.isAssignableFrom(parameter)) {
                        value = parameter.getConstructor(Long.class).newInstance(1L);
                    } else if(parameter == Long.class) {
                        value = 10L;
                    } else if(parameter == Integer.class) {
                        value = 10;
                    } else if(parameter == Boolean.class) {
                        value = false;
                    } else if(parameter == String.class) {
                        value = "Original";
                    } else if(parameter == ByteArray.class) {
                        value = new ByteArray(new byte[]{1, 2});
                    } else {
                        value = null;
                    }
                } catch(ReflectiveOperationException exception) {
                    throw new LinkageError(exception.getMessage(), exception);
                }
                return value;
            }).toArray();
            return (T)constructor.newInstance(arguments);
        } catch(ReflectiveOperationException exception) {
            throw new LinkageError(exception.getMessage(), exception);
        }
    }

    <T extends BaseValue> T value(Class<T> type) {
        T value = construct(type);
        values.put(type.getSimpleName(), value);
        return value;
    }

    void property(String valueType, String property, Object value) {
        inject(values.get(valueType + "Value"), property, value);
    }

    String expectedLock() {
        return kind.equals("EntityListItemDefault") ? entityAttribute.getPrimaryKey().getEntityRef() : "ECHO_THREE.Item.777";
    }

    boolean lock(String operation, BasePK key) {
        assertEquals(expectedLock(), key.getEntityRef());
        locks.add(operation);
        return allowLock;
    }

    BasePK lockKey(BaseEntity entity) {
        try {
            var method = BaseAbstractEditCommand.class.getDeclaredMethod("getLockTargetPK", BaseEntity.class);
            method.setAccessible(true);
            return (BasePK)method.invoke(command, entity);
        } catch(NoSuchMethodException exception) {
            return entity.getPrimaryKey(); // Original implementation baseline.
        } catch(ReflectiveOperationException exception) {
            throw new LinkageError(exception.getMessage(), exception);
        }
    }

    BaseEntity lookup(BaseEntity entity, EntityPermission permission) {
        valueLookups++;
        if(permission == EntityPermission.READ_WRITE) {
            readWriteLookups++;
        }
        return missingValue ? null : entity;
    }

    void write(BaseValue value, BasePK actor) {
        assertEquals(updatedBy, actor);
        updates++;
        writtenValue = value;
        if(failUpdate) {
            throw new IllegalStateException("persistence failure");
        }
    }

    Object transfer(Class<?> type) {
        transfers++;
        if(failTransfer) {
            throw new IllegalStateException("transfer failure");
        }
        return construct(type);
    }

    static Object call(Object target, String method, Object... arguments) {
        try {
            Method candidate = Arrays.stream(target.getClass().getMethods()).filter(m -> m.getName().equals(method)
                    && m.getParameterCount() == arguments.length).findFirst().orElseThrow();
            return candidate.invoke(target, arguments);
        } catch(InvocationTargetException exception) {
            if(exception.getCause() instanceof RuntimeException runtimeException) {
                throw runtimeException;
            }
            if(exception.getCause() instanceof Error error) {
                throw error;
            }
            throw new AssertionError(exception.getCause());
        } catch(ReflectiveOperationException exception) {
            throw new LinkageError(exception.getMessage(), exception);
        }
    }

    BaseResult execute() {
        try {
            var method = BaseCommand.class.getDeclaredMethod("execute");
            method.setAccessible(true);
            return (BaseResult)method.invoke(command);
        } catch(InvocationTargetException exception) {
            if(exception.getCause() instanceof RuntimeException runtimeException) {
                throw runtimeException;
            }
            if(exception.getCause() instanceof Error error) {
                throw error;
            }
            throw new AssertionError(exception.getCause());
        } catch(ReflectiveOperationException exception) {
            throw new LinkageError(exception.getMessage(), exception);
        }
    }

    final ComponentVendorDetail componentVendorDetail = new ComponentVendorDetail(value(ComponentVendorDetailValue.class), EntityPermission.READ_WRITE) {
        @Override
        public ComponentVendor getComponentVendor() {
            return EntityEditFixture.this.componentVendor;
        }
    };
    final ComponentVendor componentVendor = new ComponentVendor(value(ComponentVendorValue.class), EntityPermission.READ_WRITE) {
        @Override
        public ComponentVendorDetail getActiveDetail() {
            return EntityEditFixture.this.componentVendorDetail;
        }

        @Override
        public ComponentVendorDetail getLastDetail() {
            return EntityEditFixture.this.componentVendorDetail;
        }
    };
    final EntityTypeDetail entityTypeDetail = new EntityTypeDetail(value(EntityTypeDetailValue.class), EntityPermission.READ_WRITE) {
        @Override
        public EntityType getEntityType() {
            return EntityEditFixture.this.entityType;
        }

        @Override
        public ComponentVendor getComponentVendor() {
            return EntityEditFixture.this.componentVendor;
        }
    };
    final EntityType entityType = new EntityType(value(EntityTypeValue.class), EntityPermission.READ_WRITE) {
        @Override
        public EntityTypeDetail getActiveDetail() {
            return EntityEditFixture.this.entityTypeDetail;
        }

        @Override
        public EntityTypeDetail getLastDetail() {
            return EntityEditFixture.this.entityTypeDetail;
        }
    };
    final EntityInstance entityInstance = new EntityInstance(value(EntityInstanceValue.class), EntityPermission.READ_WRITE) {
        @Override
        public EntityType getEntityType() {
            return EntityEditFixture.this.entityType;
        }
    };
    final EntityAttributeType entityAttributeType = new EntityAttributeType(value(EntityAttributeTypeValue.class), EntityPermission.READ_WRITE) {
    };
    final EntityAttributeDetail entityAttributeDetail = new EntityAttributeDetail(value(EntityAttributeDetailValue.class), EntityPermission.READ_WRITE) {
        @Override
        public EntityAttribute getEntityAttribute() {
            return EntityEditFixture.this.entityAttribute;
        }

        @Override
        public EntityType getEntityType() {
            return wrongAttributeOwner ? otherEntityType : EntityEditFixture.this.entityType;
        }

        @Override
        public EntityAttributeType getEntityAttributeType() {
            return EntityEditFixture.this.entityAttributeType;
        }
    };
    final EntityAttribute entityAttribute = new EntityAttribute(value(EntityAttributeValue.class), EntityPermission.READ_WRITE) {
        @Override
        public EntityAttributeDetail getActiveDetail() {
            return EntityEditFixture.this.entityAttributeDetail;
        }

        @Override
        public EntityAttributeDetail getLastDetail() {
            return EntityEditFixture.this.entityAttributeDetail;
        }
    };
    final EntityAliasTypeDetail entityAliasTypeDetail = new EntityAliasTypeDetail(value(EntityAliasTypeDetailValue.class), EntityPermission.READ_WRITE) {
        @Override
        public EntityAliasType getEntityAliasType() {
            return EntityEditFixture.this.entityAliasType;
        }

        @Override
        public EntityType getEntityType() {
            return wrongAliasOwner ? otherEntityType : EntityEditFixture.this.entityType;
        }
    };
    final EntityAliasType entityAliasType = new EntityAliasType(value(EntityAliasTypeValue.class), EntityPermission.READ_WRITE) {
        @Override
        public EntityAliasTypeDetail getActiveDetail() {
            return EntityEditFixture.this.entityAliasTypeDetail;
        }

        @Override
        public EntityAliasTypeDetail getLastDetail() {
            return EntityEditFixture.this.entityAliasTypeDetail;
        }
    };
    final AppearanceDetail appearanceDetail = new AppearanceDetail(value(AppearanceDetailValue.class), EntityPermission.READ_WRITE) {
        @Override
        public Appearance getAppearance() {
            return EntityEditFixture.this.appearance;
        }
    };
    final Appearance appearance = new Appearance(value(AppearanceValue.class), EntityPermission.READ_WRITE) {
        @Override
        public AppearanceDetail getActiveDetail() {
            return EntityEditFixture.this.appearanceDetail;
        }

        @Override
        public AppearanceDetail getLastDetail() {
            return EntityEditFixture.this.appearanceDetail;
        }
    };
    final MimeTypeDetail mimeTypeDetail = new MimeTypeDetail(value(MimeTypeDetailValue.class), EntityPermission.READ_WRITE) {
        @Override
        public MimeType getMimeType() {
            return EntityEditFixture.this.mimeType;
        }

        @Override
        public EntityAttributeType getEntityAttributeType() {
            return mimeStorageType == null ? EntityEditFixture.this.entityAttributeType : new EntityAttributeType(new EntityAttributeTypeValue(new EntityAttributeTypePK(999L), mimeStorageType), EntityPermission.READ_ONLY);
        }
    };
    final MimeType mimeType = new MimeType(value(MimeTypeValue.class), EntityPermission.READ_WRITE) {
        @Override
        public MimeTypeDetail getActiveDetail() {
            return EntityEditFixture.this.mimeTypeDetail;
        }

        @Override
        public MimeTypeDetail getLastDetail() {
            return EntityEditFixture.this.mimeTypeDetail;
        }
    };
    final EntityListItemDetail entityListItemDetail = new EntityListItemDetail(value(EntityListItemDetailValue.class), EntityPermission.READ_WRITE) {
        @Override
        public EntityListItem getEntityListItem() {
            return EntityEditFixture.this.entityListItem;
        }

        @Override
        public EntityAttribute getEntityAttribute() {
            return wrongListItem ? otherAttribute : EntityEditFixture.this.entityAttribute;
        }
    };
    final EntityListItem entityListItem = new EntityListItem(value(EntityListItemValue.class), EntityPermission.READ_WRITE) {
        @Override
        public EntityListItemDetail getActiveDetail() {
            return EntityEditFixture.this.entityListItemDetail;
        }

        @Override
        public EntityListItemDetail getLastDetail() {
            return EntityEditFixture.this.entityListItemDetail;
        }
    };
    final Language language = new Language(value(LanguageValue.class), EntityPermission.READ_WRITE) {
    };
    final EntityAttributeInteger entityAttributeInteger = new EntityAttributeInteger(value(EntityAttributeIntegerValue.class), EntityPermission.READ_WRITE) {
        @Override
        public EntityAttribute getEntityAttribute() {
            return EntityEditFixture.this.entityAttribute;
        }
    };
    final EntityAttributeLong entityAttributeLong = new EntityAttributeLong(value(EntityAttributeLongValue.class), EntityPermission.READ_WRITE) {
        @Override
        public EntityAttribute getEntityAttribute() {
            return EntityEditFixture.this.entityAttribute;
        }
    };
    final EntityAttributeString entityAttributeString = new EntityAttributeString(value(EntityAttributeStringValue.class), EntityPermission.READ_WRITE) {
        @Override
        public EntityAttribute getEntityAttribute() {
            return EntityEditFixture.this.entityAttribute;
        }
    };
    final EntityAppearance entityAppearance = new EntityAppearance(value(EntityAppearanceValue.class), EntityPermission.READ_WRITE) {
        @Override
        public EntityInstance getEntityInstance() {
            return EntityEditFixture.this.entityInstance;
        }

        @Override
        public Appearance getAppearance() {
            return EntityEditFixture.this.appearance;
        }

        @Override
        public void store() {
            persistenceOperations.add("expire");
        }

        @Override
        public void remove() {
            persistenceOperations.add("remove");
        }
    };
    final EntityAlias entityAlias = new EntityAlias(value(EntityAliasValue.class), EntityPermission.READ_WRITE) {
        @Override
        public EntityInstance getEntityInstance() {
            return EntityEditFixture.this.entityInstance;
        }

        @Override
        public EntityAliasType getEntityAliasType() {
            return EntityEditFixture.this.entityAliasType;
        }

        @Override
        public void store() {
            persistenceOperations.add("expire");
        }

        @Override
        public void remove() {
            persistenceOperations.add("remove");
        }
    };
    final EntityListItemDefault entityListItemDefault = new EntityListItemDefault(value(EntityListItemDefaultValue.class), EntityPermission.READ_WRITE) {
        @Override
        public EntityAttribute getEntityAttribute() {
            return EntityEditFixture.this.entityAttribute;
        }

        @Override
        public EntityListItem getEntityListItem() {
            return EntityEditFixture.this.entityListItem;
        }

        @Override
        public void store() {
            persistenceOperations.add("expire");
        }

        @Override
        public void remove() {
            persistenceOperations.add("remove");
        }
    };
    final EntityBooleanAttribute entityBooleanAttribute = new EntityBooleanAttribute(value(EntityBooleanAttributeValue.class), EntityPermission.READ_WRITE) {
        @Override
        public EntityAttribute getEntityAttribute() {
            return EntityEditFixture.this.entityAttribute;
        }

        @Override
        public EntityInstance getEntityInstance() {
            return EntityEditFixture.this.entityInstance;
        }

        @Override
        public void store() {
            persistenceOperations.add("expire");
        }

        @Override
        public void remove() {
            persistenceOperations.add("remove");
        }
    };
    final EntityIntegerAttribute entityIntegerAttribute = new EntityIntegerAttribute(value(EntityIntegerAttributeValue.class), EntityPermission.READ_WRITE) {
        @Override
        public EntityAttribute getEntityAttribute() {
            return EntityEditFixture.this.entityAttribute;
        }

        @Override
        public EntityInstance getEntityInstance() {
            return EntityEditFixture.this.entityInstance;
        }

        @Override
        public void store() {
            persistenceOperations.add("expire");
        }

        @Override
        public void remove() {
            persistenceOperations.add("remove");
        }
    };
    final EntityLongAttribute entityLongAttribute = new EntityLongAttribute(value(EntityLongAttributeValue.class), EntityPermission.READ_WRITE) {
        @Override
        public EntityAttribute getEntityAttribute() {
            return EntityEditFixture.this.entityAttribute;
        }

        @Override
        public EntityInstance getEntityInstance() {
            return EntityEditFixture.this.entityInstance;
        }

        @Override
        public void store() {
            persistenceOperations.add("expire");
        }

        @Override
        public void remove() {
            persistenceOperations.add("remove");
        }
    };
    final EntityNameAttribute entityNameAttribute = new EntityNameAttribute(value(EntityNameAttributeValue.class), EntityPermission.READ_WRITE) {
        @Override
        public EntityAttribute getEntityAttribute() {
            return EntityEditFixture.this.entityAttribute;
        }

        @Override
        public EntityInstance getEntityInstance() {
            return EntityEditFixture.this.entityInstance;
        }

        @Override
        public void store() {
            persistenceOperations.add("expire");
        }

        @Override
        public void remove() {
            persistenceOperations.add("remove");
        }
    };
    final EntityStringAttribute entityStringAttribute = new EntityStringAttribute(value(EntityStringAttributeValue.class), EntityPermission.READ_WRITE) {
        @Override
        public EntityAttribute getEntityAttribute() {
            return EntityEditFixture.this.entityAttribute;
        }

        @Override
        public EntityInstance getEntityInstance() {
            return EntityEditFixture.this.entityInstance;
        }

        @Override
        public Language getLanguage() {
            return EntityEditFixture.this.language;
        }

        @Override
        public void store() {
            persistenceOperations.add("expire");
        }

        @Override
        public void remove() {
            persistenceOperations.add("remove");
        }
    };
    final EntityDateAttribute entityDateAttribute = new EntityDateAttribute(value(EntityDateAttributeValue.class), EntityPermission.READ_WRITE) {
        @Override
        public EntityAttribute getEntityAttribute() {
            return EntityEditFixture.this.entityAttribute;
        }

        @Override
        public EntityInstance getEntityInstance() {
            return EntityEditFixture.this.entityInstance;
        }

        @Override
        public void store() {
            persistenceOperations.add("expire");
        }

        @Override
        public void remove() {
            persistenceOperations.add("remove");
        }
    };
    final EntityTimeAttribute entityTimeAttribute = new EntityTimeAttribute(value(EntityTimeAttributeValue.class), EntityPermission.READ_WRITE) {
        @Override
        public EntityAttribute getEntityAttribute() {
            return EntityEditFixture.this.entityAttribute;
        }

        @Override
        public EntityInstance getEntityInstance() {
            return EntityEditFixture.this.entityInstance;
        }

        @Override
        public void store() {
            persistenceOperations.add("expire");
        }

        @Override
        public void remove() {
            persistenceOperations.add("remove");
        }
    };
    final EntityBlobAttribute entityBlobAttribute = new EntityBlobAttribute(value(EntityBlobAttributeValue.class), EntityPermission.READ_WRITE) {
        @Override
        public EntityAttribute getEntityAttribute() {
            return EntityEditFixture.this.entityAttribute;
        }

        @Override
        public EntityInstance getEntityInstance() {
            return EntityEditFixture.this.entityInstance;
        }

        @Override
        public Language getLanguage() {
            return EntityEditFixture.this.language;
        }

        @Override
        public MimeType getMimeType() {
            return EntityEditFixture.this.mimeType;
        }

        @Override
        public void store() {
            persistenceOperations.add("expire");
        }

        @Override
        public void remove() {
            persistenceOperations.add("remove");
        }
    };
    final EntityClobAttribute entityClobAttribute = new EntityClobAttribute(value(EntityClobAttributeValue.class), EntityPermission.READ_WRITE) {
        @Override
        public EntityAttribute getEntityAttribute() {
            return EntityEditFixture.this.entityAttribute;
        }

        @Override
        public EntityInstance getEntityInstance() {
            return EntityEditFixture.this.entityInstance;
        }

        @Override
        public Language getLanguage() {
            return EntityEditFixture.this.language;
        }

        @Override
        public MimeType getMimeType() {
            return EntityEditFixture.this.mimeType;
        }

        @Override
        public void store() {
            persistenceOperations.add("expire");
        }

        @Override
        public void remove() {
            persistenceOperations.add("remove");
        }
    };
    final EntityEntityAttribute entityEntityAttribute = new EntityEntityAttribute(value(EntityEntityAttributeValue.class), EntityPermission.READ_WRITE) {
        @Override
        public EntityAttribute getEntityAttribute() {
            return EntityEditFixture.this.entityAttribute;
        }

        @Override
        public EntityInstance getEntityInstance() {
            return EntityEditFixture.this.entityInstance;
        }

        @Override
        public EntityInstance getEntityInstanceAttribute() {
            return EntityEditFixture.this.entityInstance;
        }

        @Override
        public void store() {
            persistenceOperations.add("expire");
        }

        @Override
        public void remove() {
            persistenceOperations.add("remove");
        }
    };
    final EntityListItemAttribute entityListItemAttribute = new EntityListItemAttribute(value(EntityListItemAttributeValue.class), EntityPermission.READ_WRITE) {
        @Override
        public EntityAttribute getEntityAttribute() {
            return EntityEditFixture.this.entityAttribute;
        }

        @Override
        public EntityInstance getEntityInstance() {
            return EntityEditFixture.this.entityInstance;
        }

        @Override
        public EntityListItem getEntityListItem() {
            return EntityEditFixture.this.entityListItem;
        }

        @Override
        public void store() {
            persistenceOperations.add("expire");
        }

        @Override
        public void remove() {
            persistenceOperations.add("remove");
        }
    };
    final EntityGeoPointAttribute entityGeoPointAttribute = new EntityGeoPointAttribute(value(EntityGeoPointAttributeValue.class), EntityPermission.READ_WRITE) {
        @Override
        public EntityAttribute getEntityAttribute() {
            return EntityEditFixture.this.entityAttribute;
        }

        @Override
        public EntityInstance getEntityInstance() {
            return EntityEditFixture.this.entityInstance;
        }

        @Override
        public void store() {
            persistenceOperations.add("expire");
        }

        @Override
        public void remove() {
            persistenceOperations.add("remove");
        }
    };

    final EntityType otherEntityType = new EntityType(new EntityTypeValue(new EntityTypePK(999L), null, null), EntityPermission.READ_ONLY) {
        @Override
        public EntityTypeDetail getLastDetail() {
            return entityTypeDetail;
        }
    };
    final EntityAttribute otherAttribute = new EntityAttribute(new EntityAttributeValue(new EntityAttributePK(999L), null, null), EntityPermission.READ_ONLY) {
        @Override
        public EntityAttributeDetail getLastDetail() {
            return entityAttributeDetail;
        }
    };

    final CoreControl core = new CoreControl() {
        @Override
        public EntityAttribute getEntityAttributeByName(EntityType type, String name, EntityPermission permission) {
            assertSame(entityType, type);
            return missingAttribute ? null : entityAttribute;
        }

        @Override
        public EntityAttributeInteger getEntityAttributeInteger(EntityAttribute attribute) {
            return entityAttributeInteger;
        }

        @Override
        public EntityAttributeLong getEntityAttributeLong(EntityAttribute attribute) {
            return entityAttributeLong;
        }

        @Override
        public EntityAttributeString getEntityAttributeString(EntityAttribute attribute) {
            return entityAttributeString;
        }

        @Override
        public boolean entityAttributeEntityTypeExists(EntityAttribute attribute, EntityType type) {
            return referencedTypeAllowed;
        }

        @Override
        public EntityAttribute getEntityAttributeByEntityInstance(EntityInstance instance, EntityPermission permission) {
            return entityAttribute;
        }

        @Override
        public long countEntityAttributeEntityTypesByEntityAttribute(EntityAttribute attribute) {
            return restrictReferencedTypes ? 1L : 0L;
        }

        @Override
        public EntityListItemDefault getEntityListItemDefault(EntityAttribute attribute, EntityPermission permission) {
            return permission == EntityPermission.READ_WRITE ? getEntityListItemDefaultForUpdate(attribute) : getEntityListItemDefault(attribute);
        }

        @Override
        public EntityListItemDefault getEntityListItemDefault(EntityAttribute attribute) {
            return (EntityListItemDefault)lookup(entityListItemDefault, EntityPermission.READ_ONLY);
        }

        @Override
        public EntityListItemDefault getEntityListItemDefaultForUpdate(EntityAttribute attribute) {
            return (EntityListItemDefault)lookup(entityListItemDefault, EntityPermission.READ_WRITE);
        }

        @Override
        public void updateEntityListItemDefaultFromValue(EntityListItemDefaultValue value, BasePK actor) {
            write(value, actor);
        }

        @Override
        public EntityListItemDefaultTransfer getEntityListItemDefaultTransfer(UserVisit visit, EntityListItemDefault entity) {
            return (EntityListItemDefaultTransfer)transfer(EntityListItemDefaultTransfer.class);
        }

        @Override
        public EntityBooleanAttribute getEntityBooleanAttribute(EntityAttribute attribute, EntityInstance owner, EntityPermission permission) {
            return permission == EntityPermission.READ_WRITE ? getEntityBooleanAttributeForUpdate(attribute, owner) : getEntityBooleanAttribute(attribute, owner);
        }

        @Override
        public EntityBooleanAttribute getEntityBooleanAttribute(EntityAttribute attribute, EntityInstance owner) {
            return (EntityBooleanAttribute)lookup(entityBooleanAttribute, EntityPermission.READ_ONLY);
        }

        @Override
        public EntityBooleanAttribute getEntityBooleanAttributeForUpdate(EntityAttribute attribute, EntityInstance owner) {
            return (EntityBooleanAttribute)lookup(entityBooleanAttribute, EntityPermission.READ_WRITE);
        }

        @Override
        public void updateEntityBooleanAttributeFromValue(EntityBooleanAttributeValue value, BasePK actor) {
            write(value, actor);
        }

        @Override
        public EntityBooleanAttributeTransfer getEntityBooleanAttributeTransfer(UserVisit visit, EntityBooleanAttribute entity, EntityInstance owner) {
            return (EntityBooleanAttributeTransfer)transfer(EntityBooleanAttributeTransfer.class);
        }

        @Override
        public EntityIntegerAttribute getEntityIntegerAttribute(EntityAttribute attribute, EntityInstance owner, EntityPermission permission) {
            return permission == EntityPermission.READ_WRITE ? getEntityIntegerAttributeForUpdate(attribute, owner) : getEntityIntegerAttribute(attribute, owner);
        }

        @Override
        public EntityIntegerAttribute getEntityIntegerAttribute(EntityAttribute attribute, EntityInstance owner) {
            return (EntityIntegerAttribute)lookup(entityIntegerAttribute, EntityPermission.READ_ONLY);
        }

        @Override
        public EntityIntegerAttribute getEntityIntegerAttributeForUpdate(EntityAttribute attribute, EntityInstance owner) {
            return (EntityIntegerAttribute)lookup(entityIntegerAttribute, EntityPermission.READ_WRITE);
        }

        @Override
        public void updateEntityIntegerAttributeFromValue(EntityIntegerAttributeValue value, BasePK actor) {
            write(value, actor);
        }

        @Override
        public EntityIntegerAttributeTransfer getEntityIntegerAttributeTransfer(UserVisit visit, EntityIntegerAttribute entity, EntityInstance owner) {
            return (EntityIntegerAttributeTransfer)transfer(EntityIntegerAttributeTransfer.class);
        }

        @Override
        public EntityLongAttribute getEntityLongAttribute(EntityAttribute attribute, EntityInstance owner, EntityPermission permission) {
            return permission == EntityPermission.READ_WRITE ? getEntityLongAttributeForUpdate(attribute, owner) : getEntityLongAttribute(attribute, owner);
        }

        @Override
        public EntityLongAttribute getEntityLongAttribute(EntityAttribute attribute, EntityInstance owner) {
            return (EntityLongAttribute)lookup(entityLongAttribute, EntityPermission.READ_ONLY);
        }

        @Override
        public EntityLongAttribute getEntityLongAttributeForUpdate(EntityAttribute attribute, EntityInstance owner) {
            return (EntityLongAttribute)lookup(entityLongAttribute, EntityPermission.READ_WRITE);
        }

        @Override
        public void updateEntityLongAttributeFromValue(EntityLongAttributeValue value, BasePK actor) {
            write(value, actor);
        }

        @Override
        public EntityLongAttributeTransfer getEntityLongAttributeTransfer(UserVisit visit, EntityLongAttribute entity, EntityInstance owner) {
            return (EntityLongAttributeTransfer)transfer(EntityLongAttributeTransfer.class);
        }

        @Override
        public EntityNameAttribute getEntityNameAttribute(EntityAttribute attribute, EntityInstance owner, EntityPermission permission) {
            return permission == EntityPermission.READ_WRITE ? getEntityNameAttributeForUpdate(attribute, owner) : getEntityNameAttribute(attribute, owner);
        }

        @Override
        public EntityNameAttribute getEntityNameAttribute(EntityAttribute attribute, EntityInstance owner) {
            return (EntityNameAttribute)lookup(entityNameAttribute, EntityPermission.READ_ONLY);
        }

        @Override
        public EntityNameAttribute getEntityNameAttributeForUpdate(EntityAttribute attribute, EntityInstance owner) {
            return (EntityNameAttribute)lookup(entityNameAttribute, EntityPermission.READ_WRITE);
        }

        @Override
        public void updateEntityNameAttributeFromValue(EntityNameAttributeValue value, BasePK actor) {
            write(value, actor);
        }

        @Override
        public EntityNameAttributeTransfer getEntityNameAttributeTransfer(UserVisit visit, EntityNameAttribute entity, EntityInstance owner) {
            return (EntityNameAttributeTransfer)transfer(EntityNameAttributeTransfer.class);
        }

        @Override
        public EntityStringAttribute getEntityStringAttribute(EntityAttribute attribute, EntityInstance owner, Language language, EntityPermission permission) {
            return permission == EntityPermission.READ_WRITE ? getEntityStringAttributeForUpdate(attribute, owner, language) : getEntityStringAttribute(attribute, owner, language);
        }

        @Override
        public EntityStringAttribute getEntityStringAttribute(EntityAttribute attribute, EntityInstance owner, Language language) {
            return (EntityStringAttribute)lookup(entityStringAttribute, EntityPermission.READ_ONLY);
        }

        @Override
        public EntityStringAttribute getEntityStringAttributeForUpdate(EntityAttribute attribute, EntityInstance owner, Language language) {
            return (EntityStringAttribute)lookup(entityStringAttribute, EntityPermission.READ_WRITE);
        }

        @Override
        public void updateEntityStringAttributeFromValue(EntityStringAttributeValue value, BasePK actor) {
            write(value, actor);
        }

        @Override
        public EntityStringAttributeTransfer getEntityStringAttributeTransfer(UserVisit visit, EntityStringAttribute entity, EntityInstance owner) {
            return (EntityStringAttributeTransfer)transfer(EntityStringAttributeTransfer.class);
        }

        @Override
        public EntityDateAttribute getEntityDateAttribute(EntityAttribute attribute, EntityInstance owner, EntityPermission permission) {
            return permission == EntityPermission.READ_WRITE ? getEntityDateAttributeForUpdate(attribute, owner) : getEntityDateAttribute(attribute, owner);
        }

        @Override
        public EntityDateAttribute getEntityDateAttribute(EntityAttribute attribute, EntityInstance owner) {
            return (EntityDateAttribute)lookup(entityDateAttribute, EntityPermission.READ_ONLY);
        }

        @Override
        public EntityDateAttribute getEntityDateAttributeForUpdate(EntityAttribute attribute, EntityInstance owner) {
            return (EntityDateAttribute)lookup(entityDateAttribute, EntityPermission.READ_WRITE);
        }

        @Override
        public void updateEntityDateAttributeFromValue(EntityDateAttributeValue value, BasePK actor) {
            write(value, actor);
        }

        @Override
        public EntityDateAttributeTransfer getEntityDateAttributeTransfer(UserVisit visit, EntityDateAttribute entity, EntityInstance owner) {
            return (EntityDateAttributeTransfer)transfer(EntityDateAttributeTransfer.class);
        }

        @Override
        public EntityTimeAttribute getEntityTimeAttribute(EntityAttribute attribute, EntityInstance owner, EntityPermission permission) {
            return permission == EntityPermission.READ_WRITE ? getEntityTimeAttributeForUpdate(attribute, owner) : getEntityTimeAttribute(attribute, owner);
        }

        @Override
        public EntityTimeAttribute getEntityTimeAttribute(EntityAttribute attribute, EntityInstance owner) {
            return (EntityTimeAttribute)lookup(entityTimeAttribute, EntityPermission.READ_ONLY);
        }

        @Override
        public EntityTimeAttribute getEntityTimeAttributeForUpdate(EntityAttribute attribute, EntityInstance owner) {
            return (EntityTimeAttribute)lookup(entityTimeAttribute, EntityPermission.READ_WRITE);
        }

        @Override
        public void updateEntityTimeAttributeFromValue(EntityTimeAttributeValue value, BasePK actor) {
            write(value, actor);
        }

        @Override
        public EntityTimeAttributeTransfer getEntityTimeAttributeTransfer(UserVisit visit, EntityTimeAttribute entity, EntityInstance owner) {
            return (EntityTimeAttributeTransfer)transfer(EntityTimeAttributeTransfer.class);
        }

        @Override
        public EntityBlobAttribute getEntityBlobAttribute(EntityAttribute attribute, EntityInstance owner, Language language, EntityPermission permission) {
            return permission == EntityPermission.READ_WRITE ? getEntityBlobAttributeForUpdate(attribute, owner, language) : getEntityBlobAttribute(attribute, owner, language);
        }

        @Override
        public EntityBlobAttribute getEntityBlobAttribute(EntityAttribute attribute, EntityInstance owner, Language language) {
            return (EntityBlobAttribute)lookup(entityBlobAttribute, EntityPermission.READ_ONLY);
        }

        @Override
        public EntityBlobAttribute getEntityBlobAttributeForUpdate(EntityAttribute attribute, EntityInstance owner, Language language) {
            return (EntityBlobAttribute)lookup(entityBlobAttribute, EntityPermission.READ_WRITE);
        }

        @Override
        public void updateEntityBlobAttributeFromValue(EntityBlobAttributeValue value, BasePK actor) {
            write(value, actor);
        }

        @Override
        public EntityBlobAttributeTransfer getEntityBlobAttributeTransfer(UserVisit visit, EntityBlobAttribute entity, EntityInstance owner) {
            return (EntityBlobAttributeTransfer)transfer(EntityBlobAttributeTransfer.class);
        }

        @Override
        public EntityClobAttribute getEntityClobAttribute(EntityAttribute attribute, EntityInstance owner, Language language, EntityPermission permission) {
            return permission == EntityPermission.READ_WRITE ? getEntityClobAttributeForUpdate(attribute, owner, language) : getEntityClobAttribute(attribute, owner, language);
        }

        @Override
        public EntityClobAttribute getEntityClobAttribute(EntityAttribute attribute, EntityInstance owner, Language language) {
            return (EntityClobAttribute)lookup(entityClobAttribute, EntityPermission.READ_ONLY);
        }

        @Override
        public EntityClobAttribute getEntityClobAttributeForUpdate(EntityAttribute attribute, EntityInstance owner, Language language) {
            return (EntityClobAttribute)lookup(entityClobAttribute, EntityPermission.READ_WRITE);
        }

        @Override
        public void updateEntityClobAttributeFromValue(EntityClobAttributeValue value, BasePK actor) {
            write(value, actor);
        }

        @Override
        public EntityClobAttributeTransfer getEntityClobAttributeTransfer(UserVisit visit, EntityClobAttribute entity, EntityInstance owner) {
            return (EntityClobAttributeTransfer)transfer(EntityClobAttributeTransfer.class);
        }

        @Override
        public EntityEntityAttribute getEntityEntityAttribute(EntityAttribute attribute, EntityInstance owner, EntityPermission permission) {
            return permission == EntityPermission.READ_WRITE ? getEntityEntityAttributeForUpdate(attribute, owner) : getEntityEntityAttribute(attribute, owner);
        }

        @Override
        public EntityEntityAttribute getEntityEntityAttribute(EntityAttribute attribute, EntityInstance owner) {
            return (EntityEntityAttribute)lookup(entityEntityAttribute, EntityPermission.READ_ONLY);
        }

        @Override
        public EntityEntityAttribute getEntityEntityAttributeForUpdate(EntityAttribute attribute, EntityInstance owner) {
            return (EntityEntityAttribute)lookup(entityEntityAttribute, EntityPermission.READ_WRITE);
        }

        @Override
        public void updateEntityEntityAttributeFromValue(EntityEntityAttributeValue value, BasePK actor) {
            write(value, actor);
        }

        @Override
        public EntityEntityAttributeTransfer getEntityEntityAttributeTransfer(UserVisit visit, EntityEntityAttribute entity, EntityInstance owner) {
            return (EntityEntityAttributeTransfer)transfer(EntityEntityAttributeTransfer.class);
        }

        @Override
        public EntityListItemAttribute getEntityListItemAttribute(EntityAttribute attribute, EntityInstance owner, EntityPermission permission) {
            return permission == EntityPermission.READ_WRITE ? getEntityListItemAttributeForUpdate(attribute, owner) : getEntityListItemAttribute(attribute, owner);
        }

        @Override
        public EntityListItemAttribute getEntityListItemAttribute(EntityAttribute attribute, EntityInstance owner) {
            return (EntityListItemAttribute)lookup(entityListItemAttribute, EntityPermission.READ_ONLY);
        }

        @Override
        public EntityListItemAttribute getEntityListItemAttributeForUpdate(EntityAttribute attribute, EntityInstance owner) {
            return (EntityListItemAttribute)lookup(entityListItemAttribute, EntityPermission.READ_WRITE);
        }

        @Override
        public void updateEntityListItemAttributeFromValue(EntityListItemAttributeValue value, BasePK actor) {
            write(value, actor);
        }

        @Override
        public EntityListItemAttributeTransfer getEntityListItemAttributeTransfer(UserVisit visit, EntityListItemAttribute entity, EntityInstance owner) {
            return (EntityListItemAttributeTransfer)transfer(EntityListItemAttributeTransfer.class);
        }

        @Override
        public EntityGeoPointAttribute getEntityGeoPointAttribute(EntityAttribute attribute, EntityInstance owner, EntityPermission permission) {
            return permission == EntityPermission.READ_WRITE ? getEntityGeoPointAttributeForUpdate(attribute, owner) : getEntityGeoPointAttribute(attribute, owner);
        }

        @Override
        public EntityGeoPointAttribute getEntityGeoPointAttribute(EntityAttribute attribute, EntityInstance owner) {
            return (EntityGeoPointAttribute)lookup(entityGeoPointAttribute, EntityPermission.READ_ONLY);
        }

        @Override
        public EntityGeoPointAttribute getEntityGeoPointAttributeForUpdate(EntityAttribute attribute, EntityInstance owner) {
            return (EntityGeoPointAttribute)lookup(entityGeoPointAttribute, EntityPermission.READ_WRITE);
        }

        @Override
        public void updateEntityGeoPointAttributeFromValue(EntityGeoPointAttributeValue value, BasePK actor) {
            write(value, actor);
        }

        @Override
        public EntityGeoPointAttributeTransfer getEntityGeoPointAttributeTransfer(UserVisit visit, EntityGeoPointAttribute entity, EntityInstance owner) {
            return (EntityGeoPointAttributeTransfer)transfer(EntityGeoPointAttributeTransfer.class);
        }

        @Override
        public void deleteEntityBooleanAttribute(EntityBooleanAttribute entity, BasePK deletedBy) {
            assertSame(entityBooleanAttribute, entity);
            assertEquals(updatedBy, deletedBy);
            deletions.add("EntityBooleanAttribute");
        }

        @Override
        public void deleteEntityIntegerAttribute(EntityIntegerAttribute entity, BasePK deletedBy) {
            assertSame(entityIntegerAttribute, entity);
            assertEquals(updatedBy, deletedBy);
            deletions.add("EntityIntegerAttribute");
        }

        @Override
        public void deleteEntityLongAttribute(EntityLongAttribute entity, BasePK deletedBy) {
            assertSame(entityLongAttribute, entity);
            assertEquals(updatedBy, deletedBy);
            deletions.add("EntityLongAttribute");
        }

        @Override
        public void deleteEntityNameAttribute(EntityNameAttribute entity, BasePK deletedBy) {
            assertSame(entityNameAttribute, entity);
            assertEquals(updatedBy, deletedBy);
            deletions.add("EntityNameAttribute");
        }

        @Override
        public void deleteEntityStringAttribute(EntityStringAttribute entity, BasePK deletedBy) {
            assertSame(entityStringAttribute, entity);
            assertEquals(updatedBy, deletedBy);
            deletions.add("EntityStringAttribute");
        }

        @Override
        public void deleteEntityDateAttribute(EntityDateAttribute entity, BasePK deletedBy) {
            assertSame(entityDateAttribute, entity);
            assertEquals(updatedBy, deletedBy);
            deletions.add("EntityDateAttribute");
        }

        @Override
        public void deleteEntityTimeAttribute(EntityTimeAttribute entity, BasePK deletedBy) {
            assertSame(entityTimeAttribute, entity);
            assertEquals(updatedBy, deletedBy);
            deletions.add("EntityTimeAttribute");
        }

        @Override
        public void deleteEntityBlobAttribute(EntityBlobAttribute entity, BasePK deletedBy) {
            assertSame(entityBlobAttribute, entity);
            assertEquals(updatedBy, deletedBy);
            deletions.add("EntityBlobAttribute");
        }

        @Override
        public void deleteEntityClobAttribute(EntityClobAttribute entity, BasePK deletedBy) {
            assertSame(entityClobAttribute, entity);
            assertEquals(updatedBy, deletedBy);
            deletions.add("EntityClobAttribute");
        }

        @Override
        public void deleteEntityEntityAttribute(EntityEntityAttribute entity, BasePK deletedBy) {
            assertSame(entityEntityAttribute, entity);
            assertEquals(updatedBy, deletedBy);
            deletions.add("EntityEntityAttribute");
        }

        @Override
        public void deleteEntityListItemAttribute(EntityListItemAttribute entity, BasePK deletedBy) {
            assertSame(entityListItemAttribute, entity);
            assertEquals(updatedBy, deletedBy);
            deletions.add("EntityListItemAttribute");
        }

        @Override
        public void deleteEntityGeoPointAttribute(EntityGeoPointAttribute entity, BasePK deletedBy) {
            assertSame(entityGeoPointAttribute, entity);
            assertEquals(updatedBy, deletedBy);
            deletions.add("EntityGeoPointAttribute");
        }

        @Override
        public void deleteEntityListItemDefault(EntityListItemDefault entity, BasePK deletedBy) {
            assertSame(entityListItemDefault, entity);
            assertEquals(updatedBy, deletedBy);
            deletions.add("EntityListItemDefault");
        }
    };

    final AppearanceControl appearances = new AppearanceControl() {
        @Override
        public EntityAppearance getEntityAppearance(EntityInstance owner, EntityPermission permission) {
            return permission == EntityPermission.READ_WRITE ? getEntityAppearanceForUpdate(owner) : getEntityAppearance(owner);
        }

        @Override
        public EntityAppearance getEntityAppearance(EntityInstance owner) {
            return (EntityAppearance)lookup(entityAppearance, EntityPermission.READ_ONLY);
        }

        @Override
        public EntityAppearance getEntityAppearanceForUpdate(EntityInstance owner) {
            return (EntityAppearance)lookup(entityAppearance, EntityPermission.READ_WRITE);
        }

        @Override
        public Appearance getAppearanceByName(String name, EntityPermission permission) {
            return missingAppearance ? null : appearance;
        }

        @Override
        public void updateEntityAppearanceFromValue(EntityAppearanceValue value, BasePK actor) {
            write(value, actor);
        }

        @Override
        public EntityAppearanceTransfer getEntityAppearanceTransfer(UserVisit visit, EntityAppearance entity) {
            return (EntityAppearanceTransfer)transfer(EntityAppearanceTransfer.class);
        }

        @Override
        public void deleteEntityAppearance(EntityAppearance entity, BasePK deletedBy) {
            assertSame(entityAppearance, entity);
            assertEquals(updatedBy, deletedBy);
            deletions.add("EntityAppearance");
        }

    };
    final EntityAliasControl aliases = new EntityAliasControl() {
        @Override
        public EntityAlias getEntityAlias(EntityInstance owner, EntityAliasType type, EntityPermission permission) {
            return permission == EntityPermission.READ_WRITE ? getEntityAliasForUpdate(owner, type) : getEntityAlias(owner, type);
        }

        @Override
        public EntityAlias getEntityAlias(EntityInstance owner, EntityAliasType type) {
            return (EntityAlias)lookup(entityAlias, EntityPermission.READ_ONLY);
        }

        @Override
        public EntityAlias getEntityAliasForUpdate(EntityInstance owner, EntityAliasType type) {
            return (EntityAlias)lookup(entityAlias, EntityPermission.READ_WRITE);
        }

        @Override
        public EntityAliasType getEntityAliasTypeByName(EntityType type, String name, EntityPermission permission) {
            return entityAliasType;
        }

        @Override
        public EntityAlias getEntityAliasByEntityAliasTypeAndAlias(EntityAliasType type, String alias) {
            duplicateAliasLookups++;
            assertSame(entityAliasType, type);
            return sameAlias ? entityAlias : duplicateAlias ? new EntityAlias(new EntityAliasValue(new EntityAliasPK(999L),
                    entityInstance.getPrimaryKey(), entityAliasType.getPrimaryKey(), alias, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) : null;
        }

        @Override
        public void updateEntityAliasFromValue(EntityAliasValue value, BasePK actor) {
            write(value, actor);
        }

        @Override
        public EntityAliasTransfer getEntityAliasTransfer(UserVisit visit, EntityAlias entity) {
            return (EntityAliasTransfer)transfer(EntityAliasTransfer.class);
        }

        @Override
        public void deleteEntityAlias(EntityAlias entity, BasePK deletedBy) {
            assertSame(entityAlias, entity);
            assertEquals(updatedBy, deletedBy);
            deletions.add("EntityAlias");
        }

    };

    EntityEditFixture(String kind, EditMode mode) {
        this.kind = kind;
        property("ComponentVendorDetail", "componentVendorName", "ECHO_THREE");
        property("EntityTypeDetail", "entityTypeName", "Item");
        property("EntityInstance", "entityUniqueId", 777L);
        property("EntityAttributeDetail", "entityAttributeName", "ATTRIBUTE");
        property("EntityAliasTypeDetail", "entityAliasTypeName", "ALIAS_TYPE");
        property("EntityAliasTypeDetail", "validationPattern", null);
        property("EntityAttributeString", "validationPattern", null);
        property("EntityAttributeInteger", "lowerRangeIntegerValue", null);
        property("EntityAttributeInteger", "upperRangeIntegerValue", null);
        property("EntityAttributeLong", "lowerRangeLongValue", null);
        property("EntityAttributeLong", "upperRangeLongValue", null);
        property("EntityAttributeType", "entityAttributeTypeName", kind.equals("EntityListItemDefault") ? "LISTITEM" : kind.startsWith("Entity") && kind.endsWith("Attribute") ? kind.substring(6, kind.length() - 9).toUpperCase(java.util.Locale.ROOT) : "STRING");
        property("Language", "languageIsoName", "en");
        property("AppearanceDetail", "appearanceName", "Original");
        property("EntityListItemDetail", "entityListItemName", "Original");
        property("MimeTypeDetail", "mimeTypeName", "text/plain");

        command = switch(kind) {
            case "EntityAppearance" -> new EditEntityAppearanceCommand() {
                @Override
                public UserVisit getUserVisit() {
                    return null;
                }

                @Override
                public PartyPK getPartyPK() {
                    return updatedBy;
                }

                @Override
                public boolean lockEntity(BasePK key) {
                    return lock("lock", key);
                }

                @Override
                public boolean lockEntityForUpdate(BasePK key) {
                    return lock("update", key);
                }

                @Override
                public boolean unlockEntity(BasePK key) {
                    lock("unlock", key);
                    return true;
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BasePK key) {
                    lock("transfer", key);
                    return EntityEditFixture.this.ownerLockTransfer;
                }

                @Override
                public boolean lockEntity(BaseEntity entity) {
                    return lockEntity(lockKey(entity));
                }

                @Override
                public boolean lockEntityForUpdate(BaseEntity entity) {
                    return lockEntityForUpdate(lockKey(entity));
                }

                @Override
                public boolean unlockEntity(BaseEntity entity) {
                    return unlockEntity(lockKey(entity));
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BaseEntity entity) {
                    return getEntityLockTransfer(lockKey(entity));
                }
            };
            case "EntityAlias" -> new EditEntityAliasCommand() {
                @Override
                public UserVisit getUserVisit() {
                    return null;
                }

                @Override
                public PartyPK getPartyPK() {
                    return updatedBy;
                }

                @Override
                public boolean lockEntity(BasePK key) {
                    return lock("lock", key);
                }

                @Override
                public boolean lockEntityForUpdate(BasePK key) {
                    return lock("update", key);
                }

                @Override
                public boolean unlockEntity(BasePK key) {
                    lock("unlock", key);
                    return true;
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BasePK key) {
                    lock("transfer", key);
                    return EntityEditFixture.this.ownerLockTransfer;
                }

                @Override
                public boolean lockEntity(BaseEntity entity) {
                    return lockEntity(lockKey(entity));
                }

                @Override
                public boolean lockEntityForUpdate(BaseEntity entity) {
                    return lockEntityForUpdate(lockKey(entity));
                }

                @Override
                public boolean unlockEntity(BaseEntity entity) {
                    return unlockEntity(lockKey(entity));
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BaseEntity entity) {
                    return getEntityLockTransfer(lockKey(entity));
                }
            };
            case "EntityListItemDefault" -> new EditEntityListItemDefaultCommand() {
                @Override
                public UserVisit getUserVisit() {
                    return null;
                }

                @Override
                public PartyPK getPartyPK() {
                    return updatedBy;
                }

                @Override
                public boolean lockEntity(BasePK key) {
                    return lock("lock", key);
                }

                @Override
                public boolean lockEntityForUpdate(BasePK key) {
                    return lock("update", key);
                }

                @Override
                public boolean unlockEntity(BasePK key) {
                    lock("unlock", key);
                    return true;
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BasePK key) {
                    lock("transfer", key);
                    return EntityEditFixture.this.ownerLockTransfer;
                }

                @Override
                public boolean lockEntity(BaseEntity entity) {
                    return lockEntity(lockKey(entity));
                }

                @Override
                public boolean lockEntityForUpdate(BaseEntity entity) {
                    return lockEntityForUpdate(lockKey(entity));
                }

                @Override
                public boolean unlockEntity(BaseEntity entity) {
                    return unlockEntity(lockKey(entity));
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BaseEntity entity) {
                    return getEntityLockTransfer(lockKey(entity));
                }
            };
            case "EntityBooleanAttribute" -> new EditEntityBooleanAttributeCommand() {
                @Override
                public UserVisit getUserVisit() {
                    return null;
                }

                @Override
                public PartyPK getPartyPK() {
                    return updatedBy;
                }

                @Override
                public boolean lockEntity(BasePK key) {
                    return lock("lock", key);
                }

                @Override
                public boolean lockEntityForUpdate(BasePK key) {
                    return lock("update", key);
                }

                @Override
                public boolean unlockEntity(BasePK key) {
                    lock("unlock", key);
                    return true;
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BasePK key) {
                    lock("transfer", key);
                    return EntityEditFixture.this.ownerLockTransfer;
                }

                @Override
                public boolean lockEntity(BaseEntity entity) {
                    return lockEntity(lockKey(entity));
                }

                @Override
                public boolean lockEntityForUpdate(BaseEntity entity) {
                    return lockEntityForUpdate(lockKey(entity));
                }

                @Override
                public boolean unlockEntity(BaseEntity entity) {
                    return unlockEntity(lockKey(entity));
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BaseEntity entity) {
                    return getEntityLockTransfer(lockKey(entity));
                }
            };
            case "EntityIntegerAttribute" -> new EditEntityIntegerAttributeCommand() {
                @Override
                public UserVisit getUserVisit() {
                    return null;
                }

                @Override
                public PartyPK getPartyPK() {
                    return updatedBy;
                }

                @Override
                public boolean lockEntity(BasePK key) {
                    return lock("lock", key);
                }

                @Override
                public boolean lockEntityForUpdate(BasePK key) {
                    return lock("update", key);
                }

                @Override
                public boolean unlockEntity(BasePK key) {
                    lock("unlock", key);
                    return true;
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BasePK key) {
                    lock("transfer", key);
                    return EntityEditFixture.this.ownerLockTransfer;
                }

                @Override
                public boolean lockEntity(BaseEntity entity) {
                    return lockEntity(lockKey(entity));
                }

                @Override
                public boolean lockEntityForUpdate(BaseEntity entity) {
                    return lockEntityForUpdate(lockKey(entity));
                }

                @Override
                public boolean unlockEntity(BaseEntity entity) {
                    return unlockEntity(lockKey(entity));
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BaseEntity entity) {
                    return getEntityLockTransfer(lockKey(entity));
                }
            };
            case "EntityLongAttribute" -> new EditEntityLongAttributeCommand() {
                @Override
                public UserVisit getUserVisit() {
                    return null;
                }

                @Override
                public PartyPK getPartyPK() {
                    return updatedBy;
                }

                @Override
                public boolean lockEntity(BasePK key) {
                    return lock("lock", key);
                }

                @Override
                public boolean lockEntityForUpdate(BasePK key) {
                    return lock("update", key);
                }

                @Override
                public boolean unlockEntity(BasePK key) {
                    lock("unlock", key);
                    return true;
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BasePK key) {
                    lock("transfer", key);
                    return EntityEditFixture.this.ownerLockTransfer;
                }

                @Override
                public boolean lockEntity(BaseEntity entity) {
                    return lockEntity(lockKey(entity));
                }

                @Override
                public boolean lockEntityForUpdate(BaseEntity entity) {
                    return lockEntityForUpdate(lockKey(entity));
                }

                @Override
                public boolean unlockEntity(BaseEntity entity) {
                    return unlockEntity(lockKey(entity));
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BaseEntity entity) {
                    return getEntityLockTransfer(lockKey(entity));
                }
            };
            case "EntityNameAttribute" -> new EditEntityNameAttributeCommand() {
                @Override
                public UserVisit getUserVisit() {
                    return null;
                }

                @Override
                public PartyPK getPartyPK() {
                    return updatedBy;
                }

                @Override
                public boolean lockEntity(BasePK key) {
                    return lock("lock", key);
                }

                @Override
                public boolean lockEntityForUpdate(BasePK key) {
                    return lock("update", key);
                }

                @Override
                public boolean unlockEntity(BasePK key) {
                    lock("unlock", key);
                    return true;
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BasePK key) {
                    lock("transfer", key);
                    return EntityEditFixture.this.ownerLockTransfer;
                }

                @Override
                public boolean lockEntity(BaseEntity entity) {
                    return lockEntity(lockKey(entity));
                }

                @Override
                public boolean lockEntityForUpdate(BaseEntity entity) {
                    return lockEntityForUpdate(lockKey(entity));
                }

                @Override
                public boolean unlockEntity(BaseEntity entity) {
                    return unlockEntity(lockKey(entity));
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BaseEntity entity) {
                    return getEntityLockTransfer(lockKey(entity));
                }
            };
            case "EntityStringAttribute" -> new EditEntityStringAttributeCommand() {
                @Override
                public UserVisit getUserVisit() {
                    return null;
                }

                @Override
                public PartyPK getPartyPK() {
                    return updatedBy;
                }

                @Override
                public boolean lockEntity(BasePK key) {
                    return lock("lock", key);
                }

                @Override
                public boolean lockEntityForUpdate(BasePK key) {
                    return lock("update", key);
                }

                @Override
                public boolean unlockEntity(BasePK key) {
                    lock("unlock", key);
                    return true;
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BasePK key) {
                    lock("transfer", key);
                    return EntityEditFixture.this.ownerLockTransfer;
                }

                @Override
                public boolean lockEntity(BaseEntity entity) {
                    return lockEntity(lockKey(entity));
                }

                @Override
                public boolean lockEntityForUpdate(BaseEntity entity) {
                    return lockEntityForUpdate(lockKey(entity));
                }

                @Override
                public boolean unlockEntity(BaseEntity entity) {
                    return unlockEntity(lockKey(entity));
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BaseEntity entity) {
                    return getEntityLockTransfer(lockKey(entity));
                }
            };
            case "EntityDateAttribute" -> new EditEntityDateAttributeCommand() {
                @Override
                public UserVisit getUserVisit() {
                    return null;
                }

                @Override
                public PartyPK getPartyPK() {
                    return updatedBy;
                }

                @Override
                public boolean lockEntity(BasePK key) {
                    return lock("lock", key);
                }

                @Override
                public boolean lockEntityForUpdate(BasePK key) {
                    return lock("update", key);
                }

                @Override
                public boolean unlockEntity(BasePK key) {
                    lock("unlock", key);
                    return true;
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BasePK key) {
                    lock("transfer", key);
                    return EntityEditFixture.this.ownerLockTransfer;
                }

                @Override
                public boolean lockEntity(BaseEntity entity) {
                    return lockEntity(lockKey(entity));
                }

                @Override
                public boolean lockEntityForUpdate(BaseEntity entity) {
                    return lockEntityForUpdate(lockKey(entity));
                }

                @Override
                public boolean unlockEntity(BaseEntity entity) {
                    return unlockEntity(lockKey(entity));
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BaseEntity entity) {
                    return getEntityLockTransfer(lockKey(entity));
                }
            };
            case "EntityTimeAttribute" -> new EditEntityTimeAttributeCommand() {
                @Override
                public UserVisit getUserVisit() {
                    return null;
                }

                @Override
                public PartyPK getPartyPK() {
                    return updatedBy;
                }

                @Override
                public boolean lockEntity(BasePK key) {
                    return lock("lock", key);
                }

                @Override
                public boolean lockEntityForUpdate(BasePK key) {
                    return lock("update", key);
                }

                @Override
                public boolean unlockEntity(BasePK key) {
                    lock("unlock", key);
                    return true;
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BasePK key) {
                    lock("transfer", key);
                    return EntityEditFixture.this.ownerLockTransfer;
                }

                @Override
                public boolean lockEntity(BaseEntity entity) {
                    return lockEntity(lockKey(entity));
                }

                @Override
                public boolean lockEntityForUpdate(BaseEntity entity) {
                    return lockEntityForUpdate(lockKey(entity));
                }

                @Override
                public boolean unlockEntity(BaseEntity entity) {
                    return unlockEntity(lockKey(entity));
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BaseEntity entity) {
                    return getEntityLockTransfer(lockKey(entity));
                }
            };
            case "EntityBlobAttribute" -> new EditEntityBlobAttributeCommand() {
                @Override
                public UserVisit getUserVisit() {
                    return null;
                }

                @Override
                public PartyPK getPartyPK() {
                    return updatedBy;
                }

                @Override
                public boolean lockEntity(BasePK key) {
                    return lock("lock", key);
                }

                @Override
                public boolean lockEntityForUpdate(BasePK key) {
                    return lock("update", key);
                }

                @Override
                public boolean unlockEntity(BasePK key) {
                    lock("unlock", key);
                    return true;
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BasePK key) {
                    lock("transfer", key);
                    return EntityEditFixture.this.ownerLockTransfer;
                }

                @Override
                public boolean lockEntity(BaseEntity entity) {
                    return lockEntity(lockKey(entity));
                }

                @Override
                public boolean lockEntityForUpdate(BaseEntity entity) {
                    return lockEntityForUpdate(lockKey(entity));
                }

                @Override
                public boolean unlockEntity(BaseEntity entity) {
                    return unlockEntity(lockKey(entity));
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BaseEntity entity) {
                    return getEntityLockTransfer(lockKey(entity));
                }
            };
            case "EntityClobAttribute" -> new EditEntityClobAttributeCommand() {
                @Override
                public UserVisit getUserVisit() {
                    return null;
                }

                @Override
                public PartyPK getPartyPK() {
                    return updatedBy;
                }

                @Override
                public boolean lockEntity(BasePK key) {
                    return lock("lock", key);
                }

                @Override
                public boolean lockEntityForUpdate(BasePK key) {
                    return lock("update", key);
                }

                @Override
                public boolean unlockEntity(BasePK key) {
                    lock("unlock", key);
                    return true;
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BasePK key) {
                    lock("transfer", key);
                    return EntityEditFixture.this.ownerLockTransfer;
                }

                @Override
                public boolean lockEntity(BaseEntity entity) {
                    return lockEntity(lockKey(entity));
                }

                @Override
                public boolean lockEntityForUpdate(BaseEntity entity) {
                    return lockEntityForUpdate(lockKey(entity));
                }

                @Override
                public boolean unlockEntity(BaseEntity entity) {
                    return unlockEntity(lockKey(entity));
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BaseEntity entity) {
                    return getEntityLockTransfer(lockKey(entity));
                }
            };
            case "EntityEntityAttribute" -> new EditEntityEntityAttributeCommand() {
                @Override
                public UserVisit getUserVisit() {
                    return null;
                }

                @Override
                public PartyPK getPartyPK() {
                    return updatedBy;
                }

                @Override
                public boolean lockEntity(BasePK key) {
                    return lock("lock", key);
                }

                @Override
                public boolean lockEntityForUpdate(BasePK key) {
                    return lock("update", key);
                }

                @Override
                public boolean unlockEntity(BasePK key) {
                    lock("unlock", key);
                    return true;
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BasePK key) {
                    lock("transfer", key);
                    return EntityEditFixture.this.ownerLockTransfer;
                }

                @Override
                public boolean lockEntity(BaseEntity entity) {
                    return lockEntity(lockKey(entity));
                }

                @Override
                public boolean lockEntityForUpdate(BaseEntity entity) {
                    return lockEntityForUpdate(lockKey(entity));
                }

                @Override
                public boolean unlockEntity(BaseEntity entity) {
                    return unlockEntity(lockKey(entity));
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BaseEntity entity) {
                    return getEntityLockTransfer(lockKey(entity));
                }
            };
            case "EntityListItemAttribute" -> new EditEntityListItemAttributeCommand() {
                @Override
                public UserVisit getUserVisit() {
                    return null;
                }

                @Override
                public PartyPK getPartyPK() {
                    return updatedBy;
                }

                @Override
                public boolean lockEntity(BasePK key) {
                    return lock("lock", key);
                }

                @Override
                public boolean lockEntityForUpdate(BasePK key) {
                    return lock("update", key);
                }

                @Override
                public boolean unlockEntity(BasePK key) {
                    lock("unlock", key);
                    return true;
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BasePK key) {
                    lock("transfer", key);
                    return EntityEditFixture.this.ownerLockTransfer;
                }

                @Override
                public boolean lockEntity(BaseEntity entity) {
                    return lockEntity(lockKey(entity));
                }

                @Override
                public boolean lockEntityForUpdate(BaseEntity entity) {
                    return lockEntityForUpdate(lockKey(entity));
                }

                @Override
                public boolean unlockEntity(BaseEntity entity) {
                    return unlockEntity(lockKey(entity));
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BaseEntity entity) {
                    return getEntityLockTransfer(lockKey(entity));
                }
            };
            case "EntityGeoPointAttribute" -> new EditEntityGeoPointAttributeCommand() {
                @Override
                public UserVisit getUserVisit() {
                    return null;
                }

                @Override
                public PartyPK getPartyPK() {
                    return updatedBy;
                }

                @Override
                public boolean lockEntity(BasePK key) {
                    return lock("lock", key);
                }

                @Override
                public boolean lockEntityForUpdate(BasePK key) {
                    return lock("update", key);
                }

                @Override
                public boolean unlockEntity(BasePK key) {
                    lock("unlock", key);
                    return true;
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BasePK key) {
                    lock("transfer", key);
                    return EntityEditFixture.this.ownerLockTransfer;
                }

                @Override
                public boolean lockEntity(BaseEntity entity) {
                    return lockEntity(lockKey(entity));
                }

                @Override
                public boolean lockEntityForUpdate(BaseEntity entity) {
                    return lockEntityForUpdate(lockKey(entity));
                }

                @Override
                public boolean unlockEntity(BaseEntity entity) {
                    return unlockEntity(lockKey(entity));
                }

                @Override
                public EntityLockTransfer getEntityLockTransfer(BaseEntity entity) {
                    return getEntityLockTransfer(lockKey(entity));
                }
            };
            default -> throw new IllegalArgumentException(kind);
        };
        try {
            spec = (BaseSpec)CoreSpecFactory.class.getMethod("get" + (kind.equals("EntityAppearance") ? "EntityRef" : kind) + "Spec").invoke(null);
            edit = (BaseEdit)CoreEditFactory.class.getMethod("get" + (kind.equals("EntityListItemDefault") ? "EntityListItemAttribute" : kind) + "Edit").invoke(null);
        } catch(ReflectiveOperationException exception) {
            throw new LinkageError(exception.getMessage(), exception);
        }
        call(spec, "setEntityRef", kind.equals("EntityListItemDefault") ? "ECHO_THREE.EntityAttribute.1" : "ECHO_THREE.Item.777");
        if(!kind.equals("EntityAppearance") && !kind.equals("EntityAlias")) {
            call(spec, "setEntityAttributeName", "ATTRIBUTE");
        }
        if(kind.equals("EntityListItemDefault")) {
            call(spec, "setEntityRef", (Object)null);
            call(spec, "setComponentVendorName", "ECHO_THREE");
            call(spec, "setEntityTypeName", "Item");
        }
        if(kind.equals("EntityAlias")) {
            call(spec, "setEntityAliasTypeName", "ALIAS_TYPE");
        }
        if(kind.equals("EntityStringAttribute") || kind.equals("EntityBlobAttribute") || kind.equals("EntityClobAttribute")) {
            call(spec, "setLanguageIsoName", "en");
        }
        inject(command, "spec", spec);
        inject(command, "edit", edit);
        inject(command, "editMode", mode);
        var instanceLogic = new EntityInstanceLogic() {
            @Override
            public EntityInstance getEntityInstanceByEntityRef(ExecutionErrorAccumulator eea, String ref) {
                if(missingOwner || deletedOwner) {
                    eea.addExecutionError(deletedOwner ? "DeletedEntityRef" : "UnknownEntityRef", ref);
                }
                return missingOwner || deletedOwner ? null : entityInstance;
            }

            @Override
            public EntityInstance getEntityInstanceByUuid(ExecutionErrorAccumulator eea, String uuid) {
                if(missingOwner || deletedOwner) {
                    eea.addExecutionError(deletedOwner ? "DeletedUuid" : "UnknownUuid", uuid);
                }
                return missingOwner || deletedOwner ? null : entityInstance;
            }
        };
        var attributes = new EntityAttributeLogic() {
            @Override
            public void handleExecutionError(Class<? extends BaseException> exceptionClass, ExecutionErrorAccumulator eea, String key, Object... arguments) {
                if(eea == null) {
                    throwLookupException(exceptionClass, key);
                } else {
                    super.handleExecutionError(exceptionClass, eea, key, arguments);
                }
            }

            @Override
            public EntityAttribute getEntityAttributeByUuid(ExecutionErrorAccumulator eea, String uuid, EntityPermission permission) {
                return entityAttribute;
            }

            @Override
            public EntityAttribute getEntityAttributeByName(ExecutionErrorAccumulator eea, String vendor, String type, String name, EntityPermission permission) {
                assertEquals("ECHO_THREE", vendor);
                assertEquals("Item", type);
                assertEquals("ATTRIBUTE", name);
                return entityAttribute;
            }


        };
        inject(attributes, "coreControl", core);
        inject(attributes, "entityInstanceLogic", new EntityInstanceLogic() {
            @Override
            public EntityInstance getEntityInstance(ExecutionErrorAccumulator eea, UniversalEntitySpec identifier, String vendor, String... types) {
                assertEquals("ECHO_THREE", vendor);
                assertArrayEquals(new String[]{"EntityAttribute"}, types);
                return entityInstance;
            }

            @Override
            public EntityInstance getEntityInstance(ExecutionErrorAccumulator eea, String ref, String uuid, String vendor, String... types) {
                return instanceLogic.getEntityInstance(eea, ref, uuid, vendor, types);
            }
        });
        var listItems = new EntityListItemLogic() {
            @Override
            public EntityListItem getEntityListItemByName(ExecutionErrorAccumulator eea, EntityAttribute attribute, String name, EntityPermission permission) {
                assertSame(entityAttribute, attribute);
                return entityListItem;
            }

            @Override
            public EntityListItem getEntityListItemByUuid(ExecutionErrorAccumulator eea, String uuid, EntityPermission permission) {
                return entityListItem;
            }

        };
        inject(listItems, "coreControl", core);
        inject(listItems, "entityAttributeLogic", attributes);
        inject(listItems, "entityInstanceLogic", instanceLogic);
        var attributeValues = new HashMap<String, BaseLogic>();
        var entityBooleanAttributeLogic = new EntityBooleanAttributeLogic() {
            @Override
            public void handleExecutionError(Class<? extends BaseException> exceptionClass, ExecutionErrorAccumulator eea, String key, Object... arguments) {
                if(eea == null) {
                    throwLookupException(exceptionClass, key);
                } else {
                    super.handleExecutionError(exceptionClass, eea, key, arguments);
                }
            }
        };
        inject(entityBooleanAttributeLogic, "coreControl", core);
        inject(entityBooleanAttributeLogic, "entityInstanceLogic", instanceLogic);
        attributeValues.put("entityBooleanAttributeLogic", entityBooleanAttributeLogic);
        var entityIntegerAttributeLogic = new EntityIntegerAttributeLogic() {
            @Override
            public void handleExecutionError(Class<? extends BaseException> exceptionClass, ExecutionErrorAccumulator eea, String key, Object... arguments) {
                if(eea == null) {
                    throwLookupException(exceptionClass, key);
                } else {
                    super.handleExecutionError(exceptionClass, eea, key, arguments);
                }
            }
        };
        inject(entityIntegerAttributeLogic, "coreControl", core);
        inject(entityIntegerAttributeLogic, "entityInstanceLogic", instanceLogic);
        attributeValues.put("entityIntegerAttributeLogic", entityIntegerAttributeLogic);
        var entityLongAttributeLogic = new EntityLongAttributeLogic() {
            @Override
            public void handleExecutionError(Class<? extends BaseException> exceptionClass, ExecutionErrorAccumulator eea, String key, Object... arguments) {
                if(eea == null) {
                    throwLookupException(exceptionClass, key);
                } else {
                    super.handleExecutionError(exceptionClass, eea, key, arguments);
                }
            }
        };
        inject(entityLongAttributeLogic, "coreControl", core);
        inject(entityLongAttributeLogic, "entityInstanceLogic", instanceLogic);
        attributeValues.put("entityLongAttributeLogic", entityLongAttributeLogic);
        var entityNameAttributeLogic = new EntityNameAttributeLogic() {
            @Override
            public void handleExecutionError(Class<? extends BaseException> exceptionClass, ExecutionErrorAccumulator eea, String key, Object... arguments) {
                if(eea == null) {
                    throwLookupException(exceptionClass, key);
                } else {
                    super.handleExecutionError(exceptionClass, eea, key, arguments);
                }
            }
        };
        inject(entityNameAttributeLogic, "coreControl", core);
        inject(entityNameAttributeLogic, "entityInstanceLogic", instanceLogic);
        attributeValues.put("entityNameAttributeLogic", entityNameAttributeLogic);
        var entityStringAttributeLogic = new EntityStringAttributeLogic() {
            @Override
            public void handleExecutionError(Class<? extends BaseException> exceptionClass, ExecutionErrorAccumulator eea, String key, Object... arguments) {
                if(eea == null) {
                    throwLookupException(exceptionClass, key);
                } else {
                    super.handleExecutionError(exceptionClass, eea, key, arguments);
                }
            }
        };
        inject(entityStringAttributeLogic, "coreControl", core);
        inject(entityStringAttributeLogic, "entityInstanceLogic", instanceLogic);
        attributeValues.put("entityStringAttributeLogic", entityStringAttributeLogic);
        var entityDateAttributeLogic = new EntityDateAttributeLogic() {
            @Override
            public void handleExecutionError(Class<? extends BaseException> exceptionClass, ExecutionErrorAccumulator eea, String key, Object... arguments) {
                if(eea == null) {
                    throwLookupException(exceptionClass, key);
                } else {
                    super.handleExecutionError(exceptionClass, eea, key, arguments);
                }
            }
        };
        inject(entityDateAttributeLogic, "coreControl", core);
        inject(entityDateAttributeLogic, "entityInstanceLogic", instanceLogic);
        attributeValues.put("entityDateAttributeLogic", entityDateAttributeLogic);
        var entityTimeAttributeLogic = new EntityTimeAttributeLogic() {
            @Override
            public void handleExecutionError(Class<? extends BaseException> exceptionClass, ExecutionErrorAccumulator eea, String key, Object... arguments) {
                if(eea == null) {
                    throwLookupException(exceptionClass, key);
                } else {
                    super.handleExecutionError(exceptionClass, eea, key, arguments);
                }
            }
        };
        inject(entityTimeAttributeLogic, "coreControl", core);
        inject(entityTimeAttributeLogic, "entityInstanceLogic", instanceLogic);
        attributeValues.put("entityTimeAttributeLogic", entityTimeAttributeLogic);
        var entityBlobAttributeLogic = new EntityBlobAttributeLogic() {
            @Override
            public void handleExecutionError(Class<? extends BaseException> exceptionClass, ExecutionErrorAccumulator eea, String key, Object... arguments) {
                if(eea == null) {
                    throwLookupException(exceptionClass, key);
                } else {
                    super.handleExecutionError(exceptionClass, eea, key, arguments);
                }
            }
        };
        inject(entityBlobAttributeLogic, "coreControl", core);
        inject(entityBlobAttributeLogic, "entityInstanceLogic", instanceLogic);
        attributeValues.put("entityBlobAttributeLogic", entityBlobAttributeLogic);
        var entityClobAttributeLogic = new EntityClobAttributeLogic() {
            @Override
            public void handleExecutionError(Class<? extends BaseException> exceptionClass, ExecutionErrorAccumulator eea, String key, Object... arguments) {
                if(eea == null) {
                    throwLookupException(exceptionClass, key);
                } else {
                    super.handleExecutionError(exceptionClass, eea, key, arguments);
                }
            }
        };
        inject(entityClobAttributeLogic, "coreControl", core);
        inject(entityClobAttributeLogic, "entityInstanceLogic", instanceLogic);
        attributeValues.put("entityClobAttributeLogic", entityClobAttributeLogic);
        var entityEntityAttributeLogic = new EntityEntityAttributeLogic() {
            @Override
            public void handleExecutionError(Class<? extends BaseException> exceptionClass, ExecutionErrorAccumulator eea, String key, Object... arguments) {
                if(eea == null) {
                    throwLookupException(exceptionClass, key);
                } else {
                    super.handleExecutionError(exceptionClass, eea, key, arguments);
                }
            }
        };
        inject(entityEntityAttributeLogic, "coreControl", core);
        inject(entityEntityAttributeLogic, "entityInstanceLogic", instanceLogic);
        attributeValues.put("entityEntityAttributeLogic", entityEntityAttributeLogic);
        var entityListItemAttributeLogic = new EntityListItemAttributeLogic() {
            @Override
            public void handleExecutionError(Class<? extends BaseException> exceptionClass, ExecutionErrorAccumulator eea, String key, Object... arguments) {
                if(eea == null) {
                    throwLookupException(exceptionClass, key);
                } else {
                    super.handleExecutionError(exceptionClass, eea, key, arguments);
                }
            }
        };
        inject(entityListItemAttributeLogic, "coreControl", core);
        inject(entityListItemAttributeLogic, "entityInstanceLogic", instanceLogic);
        attributeValues.put("entityListItemAttributeLogic", entityListItemAttributeLogic);
        var entityGeoPointAttributeLogic = new EntityGeoPointAttributeLogic() {
            @Override
            public void handleExecutionError(Class<? extends BaseException> exceptionClass, ExecutionErrorAccumulator eea, String key, Object... arguments) {
                if(eea == null) {
                    throwLookupException(exceptionClass, key);
                } else {
                    super.handleExecutionError(exceptionClass, eea, key, arguments);
                }
            }
        };
        inject(entityGeoPointAttributeLogic, "coreControl", core);
        inject(entityGeoPointAttributeLogic, "entityInstanceLogic", instanceLogic);
        attributeValues.put("entityGeoPointAttributeLogic", entityGeoPointAttributeLogic);
        var aliasTypes = new EntityAliasTypeLogic() {
            @Override
            public void handleExecutionError(Class<? extends BaseException> exceptionClass, ExecutionErrorAccumulator eea, String key, Object... arguments) {
                if(eea == null) {
                    throwLookupException(exceptionClass, key);
                } else {
                    super.handleExecutionError(exceptionClass, eea, key, arguments);
                }
            }

            @Override
            public EntityAliasType getEntityAliasTypeByUuid(ExecutionErrorAccumulator eea, String uuid, EntityPermission permission) {
                return entityAliasType;
            }
        };
        inject(aliasTypes, "entityAliasControl", aliases);
        inject(aliasTypes, "entityInstanceLogic", instanceLogic);
        var languages = new LanguageLogic() {
            @Override
            public Language getLanguageByName(ExecutionErrorAccumulator eea, String name, EntityPermission permission) {
                if(missingLanguage) {
                    eea.addExecutionError("UnknownLanguageIsoName", name);
                }
                return missingLanguage ? null : language;
            }

            @Override
            public Language getLanguageByUuid(ExecutionErrorAccumulator eea, String uuid, EntityPermission permission) {
                return language;
            }
        };
        var mimeTypes = new MimeTypeLogic() {
            @Override
            public MimeType getMimeTypeByName(ExecutionErrorAccumulator eea, String name) {
                if(missingMimeType) {
                    eea.addExecutionError("UnknownMimeTypeName", name);
                }
                return missingMimeType ? null : mimeType;
            }
        };
        var appearanceLogic = new AppearanceLogic() {
            @Override
            public void handleExecutionError(Class<? extends BaseException> exceptionClass, ExecutionErrorAccumulator eea, String key, Object... arguments) {
                if(eea == null) {
                    throwLookupException(exceptionClass, key);
                } else {
                    super.handleExecutionError(exceptionClass, eea, key, arguments);
                }
            }

        };
        inject(appearanceLogic, "appearanceControl", appearances);
        inject(appearanceLogic, "entityInstanceLogic", instanceLogic);
        var instances = new EntityInstanceControl() {
            @Override
            public EntityInstance getEntityInstanceByEntityRef(String ref) {
                return missingOwner ? null : entityInstance;
            }
        };
        var parties = new PartyControl() {
            @Override
            public Language getLanguageByIsoName(String name) {
                return language;
            }
        };
        dependencies = new HashMap<>(Map.of("entityAttributeLogic", attributes, "entityInstanceLogic", instanceLogic,
                "entityAliasTypeLogic", aliasTypes, "languageLogic", languages, "mimeTypeLogic", mimeTypes,
                "appearanceLogic", appearanceLogic, "entityInstanceControl", instances, "partyControl", parties,
                "appearanceControl", appearances, "entityAliasControl", aliases));
        dependencies.put("entityListItemLogic", listItems);
        dependencies.putAll(attributeValues);
        dependencies.put("coreControl", core);
        lookupLogic = switch(kind) {
            case "EntityAppearance" -> appearanceLogic;
            case "EntityAlias" -> aliasTypes;
            default -> attributeValues.get("entity" + kind.substring(6).replace("Default", "Attribute") + "Logic");
        };
        dependencies.put("unitOfMeasureTypeLogic", EntityEditUnits.logic());
        injectCommandDependencies(command);
        String property = switch(kind) {
            case "EntityAppearance" -> "AppearanceName";
            case "EntityAlias" -> "Alias";
            case "EntityListItemDefault", "EntityListItemAttribute" -> "EntityListItemName";
            case "EntityEntityAttribute" -> "EntityRefAttribute";
            default -> kind.substring(6);
        };
        if(kind.equals("EntityBlobAttribute")) {
            call(edit, "setBlobAttribute", new ByteArray(new byte[]{3, 4}));
        } else if(kind.equals("EntityGeoPointAttribute")) {
            call(edit, "setLatitude", "30");
            call(edit, "setLongitude", "40");
        } else {
            String newValue = switch(kind) {
                case "EntityBooleanAttribute" -> "true";
                case "EntityIntegerAttribute", "EntityLongAttribute", "EntityTimeAttribute" -> "20";
                case "EntityDateAttribute" -> "20261125";
                default -> "Updated";
            };
            call(edit, "set" + property, newValue);
        }
        if(kind.equals("EntityBlobAttribute") || kind.equals("EntityClobAttribute")) {
            call(edit, "setMimeTypeName", "text/plain");
        }
    }

}


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

package com.echothree.model.control.core.server.logic;

import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.common.pk.ComponentVendorPK;
import com.echothree.model.data.core.common.pk.EntityAttributePK;
import com.echothree.model.data.core.common.pk.EntityAttributeTypePK;
import com.echothree.model.data.core.common.pk.EntityListItemPK;
import com.echothree.model.data.core.common.pk.EntityTypePK;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.ComponentVendorDetail;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeDetail;
import com.echothree.model.data.core.server.entity.EntityAttributeInteger;
import com.echothree.model.data.core.server.entity.EntityAttributeLong;
import com.echothree.model.data.core.server.entity.EntityAttributeType;
import com.echothree.model.data.core.server.entity.EntityBooleanDefault;
import com.echothree.model.data.core.server.entity.EntityDateDefault;
import com.echothree.model.data.core.server.entity.EntityGeoPointDefault;
import com.echothree.model.data.core.server.entity.EntityIntegerDefault;
import com.echothree.model.data.core.server.entity.EntityListItem;
import com.echothree.model.data.core.server.entity.EntityListItemDefault;
import com.echothree.model.data.core.server.entity.EntityListItemDetail;
import com.echothree.model.data.core.server.entity.EntityLongDefault;
import com.echothree.model.data.core.server.entity.EntityMultipleListItemDefault;
import com.echothree.model.data.core.server.entity.EntityStringDefault;
import com.echothree.model.data.core.server.entity.EntityTimeDefault;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.EntityTypeDetail;
import com.echothree.model.data.core.server.value.ComponentVendorDetailValue;
import com.echothree.model.data.core.server.value.ComponentVendorValue;
import com.echothree.model.data.core.server.value.EntityAttributeDetailValue;
import com.echothree.model.data.core.server.value.EntityAttributeTypeValue;
import com.echothree.model.data.core.server.value.EntityAttributeValue;
import com.echothree.model.data.core.server.value.EntityListItemDetailValue;
import com.echothree.model.data.core.server.value.EntityListItemValue;
import com.echothree.model.data.core.server.value.EntityTypeDetailValue;
import com.echothree.model.data.core.server.value.EntityTypeValue;
import com.echothree.model.data.party.common.pk.LanguagePK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.value.LanguageValue;
import com.echothree.util.common.message.Messages;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.DummyExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import static org.junit.jupiter.api.Assertions.*;

public class EntityDefaultErrorKeysTest {

    private static class Fixture {
        private final EntityAttribute entityAttribute;
        private final Language language = new Language(new LanguageValue(new LanguagePK(1L), "en", false, 0), EntityPermission.READ_ONLY);
        private final EntityListItem entityListItem = new EntityListItem(new EntityListItemValue(new EntityListItemPK(1L), null, null), EntityPermission.READ_ONLY) {
            @Override
            public EntityListItemDetail getLastDetail() {
                return new EntityListItemDetail(new EntityListItemDetailValue(new EntityListItemPK(1L), new EntityAttributePK(1L), "LIST_ITEM", false, 0, 1L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
            }
        };
        private final boolean duplicate;
        private final CoreControl coreControl;

        Fixture(EntityAttributeTypes attributeType, boolean duplicate) {
            this.duplicate = duplicate;
            var componentVendorPK = new ComponentVendorPK(1L);
            var entityTypePK = new EntityTypePK(1L);
            var entityAttributePK = new EntityAttributePK(1L);
            var componentVendorDetail = new ComponentVendorDetail(new ComponentVendorDetailValue(componentVendorPK, "VENDOR", "Vendor", 1L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
            var componentVendor = new ComponentVendor(new ComponentVendorValue(componentVendorPK, null, null), EntityPermission.READ_ONLY) {
                @Override
                public ComponentVendorDetail getLastDetail() {
                    return componentVendorDetail;
                }
            };
            var entityTypeDetail = new EntityTypeDetail(new EntityTypeDetailValue(entityTypePK, componentVendorPK, "TYPE", false, 0L, true, 0, 1L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                @Override
                public ComponentVendor getComponentVendor() {
                    return componentVendor;
                }
            };
            var entityType = new EntityType(new EntityTypeValue(entityTypePK, null, null), EntityPermission.READ_ONLY) {
                @Override
                public EntityTypeDetail getLastDetail() {
                    return entityTypeDetail;
                }
            };
            var entityAttributeDetail = new EntityAttributeDetail(new EntityAttributeDetailValue(entityAttributePK, entityTypePK, "ATTRIBUTE", new EntityAttributeTypePK(1L), false, 0, 1L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                @Override
                public EntityType getEntityType() {
                    return entityType;
                }

                @Override
                public EntityAttributeType getEntityAttributeType() {
                    return new EntityAttributeType(new EntityAttributeTypeValue(new EntityAttributeTypePK(1L), attributeType.name()), EntityPermission.READ_ONLY);
                }
            };
            entityAttribute = new EntityAttribute(new EntityAttributeValue(entityAttributePK, null, null), EntityPermission.READ_ONLY) {
                @Override
                public EntityAttributeDetail getLastDetail() {
                    return entityAttributeDetail;
                }
            };
            coreControl = new CoreControl() {
                @Override
                public EntityAttributeInteger getEntityAttributeInteger(EntityAttribute attribute) {
                    return null;
                }

                @Override
                public EntityAttributeLong getEntityAttributeLong(EntityAttribute attribute) {
                    return null;
                }

                @Override
                public EntityBooleanDefault getEntityBooleanDefault(EntityAttribute attribute) {
                    return Fixture.this.duplicate ? row(EntityBooleanDefault.class) : null;
                }

                @Override
                public EntityBooleanDefault getEntityBooleanDefaultForUpdate(EntityAttribute attribute) {
                    return Fixture.this.duplicate ? row(EntityBooleanDefault.class) : null;
                }

                @Override
                public EntityIntegerDefault getEntityIntegerDefault(EntityAttribute attribute) {
                    return Fixture.this.duplicate ? row(EntityIntegerDefault.class) : null;
                }

                @Override
                public EntityIntegerDefault getEntityIntegerDefaultForUpdate(EntityAttribute attribute) {
                    return Fixture.this.duplicate ? row(EntityIntegerDefault.class) : null;
                }

                @Override
                public EntityLongDefault getEntityLongDefault(EntityAttribute attribute) {
                    return Fixture.this.duplicate ? row(EntityLongDefault.class) : null;
                }

                @Override
                public EntityLongDefault getEntityLongDefaultForUpdate(EntityAttribute attribute) {
                    return Fixture.this.duplicate ? row(EntityLongDefault.class) : null;
                }

                @Override
                public EntityStringDefault getEntityStringDefault(EntityAttribute attribute, Language resolvedLanguage) {
                    return Fixture.this.duplicate ? row(EntityStringDefault.class) : null;
                }

                @Override
                public EntityStringDefault getEntityStringDefaultForUpdate(EntityAttribute attribute, Language resolvedLanguage) {
                    return Fixture.this.duplicate ? row(EntityStringDefault.class) : null;
                }

                @Override
                public EntityDateDefault getEntityDateDefault(EntityAttribute attribute) {
                    return Fixture.this.duplicate ? row(EntityDateDefault.class) : null;
                }

                @Override
                public EntityDateDefault getEntityDateDefaultForUpdate(EntityAttribute attribute) {
                    return Fixture.this.duplicate ? row(EntityDateDefault.class) : null;
                }

                @Override
                public EntityTimeDefault getEntityTimeDefault(EntityAttribute attribute) {
                    return Fixture.this.duplicate ? row(EntityTimeDefault.class) : null;
                }

                @Override
                public EntityTimeDefault getEntityTimeDefaultForUpdate(EntityAttribute attribute) {
                    return Fixture.this.duplicate ? row(EntityTimeDefault.class) : null;
                }

                @Override
                public EntityGeoPointDefault getEntityGeoPointDefault(EntityAttribute attribute) {
                    return Fixture.this.duplicate ? row(EntityGeoPointDefault.class) : null;
                }

                @Override
                public EntityGeoPointDefault getEntityGeoPointDefaultForUpdate(EntityAttribute attribute) {
                    return Fixture.this.duplicate ? row(EntityGeoPointDefault.class) : null;
                }

                @Override
                public EntityListItemDefault getEntityListItemDefault(EntityAttribute attribute) {
                    return Fixture.this.duplicate ? row(EntityListItemDefault.class) : null;
                }

                @Override
                public EntityListItemDefault getEntityListItemDefaultForUpdate(EntityAttribute attribute) {
                    return Fixture.this.duplicate ? row(EntityListItemDefault.class) : null;
                }

                @Override
                public EntityMultipleListItemDefault getEntityMultipleListItemDefault(EntityAttribute attribute, EntityListItem listItem) {
                    return Fixture.this.duplicate ? row(EntityMultipleListItemDefault.class) : null;
                }

                @Override
                public EntityMultipleListItemDefault getEntityMultipleListItemDefaultForUpdate(EntityAttribute attribute, EntityListItem listItem) {
                    return Fixture.this.duplicate ? row(EntityMultipleListItemDefault.class) : null;
                }

                @Override
                public EntityListItemDefault getEntityListItemDefault(EntityAttribute attribute, EntityPermission permission) {
                    return Fixture.this.duplicate ? row(EntityListItemDefault.class) : null;
                }
            };
        }
    }

    private static <T> T row(Class<T> entityClass) {
        try {
            var valueClass = Class.forName("com.echothree.model.data.core.server.value." + entityClass.getSimpleName() + "Value");
            for(var constructor : valueClass.getConstructors()) {
                var parameterTypes = constructor.getParameterTypes();
                if(!parameterTypes[0].getSimpleName().equals(entityClass.getSimpleName() + "PK")) {
                    continue;
                }
                var arguments = new Object[parameterTypes.length];
                for(int i = 0; i < arguments.length; i++) {
                    var parameterType = parameterTypes[i];
                    if(parameterType == String.class) {
                        arguments[i] = "LIST_ITEM";
                    } else if(parameterType == Long.class) {
                        arguments[i] = 1L;
                    } else if(parameterType == Integer.class) {
                        arguments[i] = 1;
                    } else if(parameterType == Boolean.class) {
                        arguments[i] = false;
                    } else {
                        arguments[i] = parameterType.getConstructor(Long.class).newInstance(1L);
                    }
                }
                return entityClass.getConstructor(valueClass, EntityPermission.class).newInstance(constructor.newInstance(arguments), EntityPermission.READ_ONLY);
            }
            throw new AssertionError("No primary-key value constructor for " + entityClass);
        } catch(ReflectiveOperationException exception) {
            throw new LinkageError(exception.getMessage(), exception);
        }
    }

    @ParameterizedTest
    @EnumSource(value = EntityAttributeTypes.class, names = { "BOOLEAN", "INTEGER", "LONG", "STRING", "DATE", "TIME", "GEOPOINT", "LISTITEM", "MULTIPLELISTITEM" })
    void duplicateDefaultIncludesCompleteKey(EntityAttributeTypes attributeType) throws Exception {
        checkError(attributeType, true);
    }

    @ParameterizedTest
    @EnumSource(value = EntityAttributeTypes.class, names = { "BOOLEAN", "INTEGER", "LONG", "STRING", "DATE", "TIME", "GEOPOINT", "LISTITEM", "MULTIPLELISTITEM" })
    void missingDefaultIncludesCompleteKey(EntityAttributeTypes attributeType) throws Exception {
        checkError(attributeType, false);
    }

    private void checkError(EntityAttributeTypes attributeType, boolean duplicate) throws Exception {
        var typeName = switch(attributeType) {
            case BOOLEAN -> "Boolean";
            case INTEGER -> "Integer";
            case LONG -> "Long";
            case STRING -> "String";
            case DATE -> "Date";
            case TIME -> "Time";
            case GEOPOINT -> "GeoPoint";
            case LISTITEM -> "ListItem";
            case MULTIPLELISTITEM -> "MultipleListItem";
            default -> throw new IllegalArgumentException(attributeType.name());
        };
        var fixture = new Fixture(attributeType, duplicate);
        var logicClass = Class.forName("com.echothree.model.control.core.server.logic.Entity" + typeName + "AttributeLogic");
        var logic = logicClass.getDeclaredConstructor().newInstance();
        var controlField = BaseLogic.class.getDeclaredField("coreControl");
        controlField.setAccessible(true);
        controlField.set(logic, fixture.coreControl);
        var errors = new DummyExecutionErrorAccumulator();
        var methodName = (duplicate ? "create" : "delete") + "Entity" + typeName + "Default";
        for(var method : logicClass.getMethods()) {
            if(!method.getName().equals(methodName)) {
                continue;
            }
            List<Object> arguments = new ArrayList<>();
            for(var parameterType : method.getParameterTypes()) {
                if(parameterType == EntityAttribute.class) {
                    arguments.add(fixture.entityAttribute);
                } else if(parameterType == Language.class) {
                    arguments.add(fixture.language);
                } else if(parameterType == EntityListItem.class) {
                    arguments.add(fixture.entityListItem);
                } else if(parameterType == Integer.class) {
                    arguments.add(1);
                } else if(parameterType == Long.class) {
                    arguments.add(1L);
                } else if(parameterType == Boolean.class || parameterType == boolean.class) {
                    arguments.add(false);
                } else if(parameterType == String.class) {
                    arguments.add("Value");
                } else if(parameterType == BasePK.class) {
                    arguments.add(null);
                } else {
                    arguments.add(errors);
                }
            }
            try {
                method.invoke(logic, arguments.toArray());
            } catch(InvocationTargetException exception) {
                throw new AssertionError(exception.getCause());
            }
            var error = errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
            assertEquals((duplicate ? "Duplicate" : "Unknown") + "Entity" + typeName + "Default", error.getKey());
            List<Object> expected = new ArrayList<>(List.of("VENDOR", "TYPE", "ATTRIBUTE"));
            if(attributeType == EntityAttributeTypes.STRING) {
                expected.add("en");
            } else if(attributeType == EntityAttributeTypes.MULTIPLELISTITEM) {
                expected.add("LIST_ITEM");
            }
            assertArrayEquals(expected.toArray(), error.getValues());
            return;
        }
        fail("No method " + methodName);
    }

}

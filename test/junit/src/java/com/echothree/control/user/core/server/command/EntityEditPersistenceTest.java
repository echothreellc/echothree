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

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.AppearanceControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EntityAliasControl;
import com.echothree.model.data.core.common.pk.AppearancePK;
import com.echothree.model.data.core.common.pk.EntityAliasPK;
import com.echothree.model.data.core.common.pk.EntityAliasTypePK;
import com.echothree.model.data.core.common.pk.EntityAppearancePK;
import com.echothree.model.data.core.common.pk.EntityAttributePK;
import com.echothree.model.data.core.common.pk.EntityBlobAttributePK;
import com.echothree.model.data.core.common.pk.EntityBooleanAttributePK;
import com.echothree.model.data.core.common.pk.EntityClobAttributePK;
import com.echothree.model.data.core.common.pk.EntityDateAttributePK;
import com.echothree.model.data.core.common.pk.EntityEntityAttributePK;
import com.echothree.model.data.core.common.pk.EntityGeoPointAttributePK;
import com.echothree.model.data.core.common.pk.EntityInstancePK;
import com.echothree.model.data.core.common.pk.EntityIntegerAttributePK;
import com.echothree.model.data.core.common.pk.EntityListItemAttributePK;
import com.echothree.model.data.core.common.pk.EntityListItemDefaultPK;
import com.echothree.model.data.core.common.pk.EntityListItemPK;
import com.echothree.model.data.core.common.pk.EntityLongAttributePK;
import com.echothree.model.data.core.common.pk.EntityNameAttributePK;
import com.echothree.model.data.core.common.pk.EntityStringAttributePK;
import com.echothree.model.data.core.common.pk.EntityTimeAttributePK;
import com.echothree.model.data.core.common.pk.MimeTypePK;
import com.echothree.model.data.core.server.entity.EntityAlias;
import com.echothree.model.data.core.server.entity.EntityAppearance;
import com.echothree.model.data.core.server.entity.EntityBlobAttribute;
import com.echothree.model.data.core.server.entity.EntityBooleanAttribute;
import com.echothree.model.data.core.server.entity.EntityClobAttribute;
import com.echothree.model.data.core.server.entity.EntityDateAttribute;
import com.echothree.model.data.core.server.entity.EntityEntityAttribute;
import com.echothree.model.data.core.server.entity.EntityGeoPointAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityIntegerAttribute;
import com.echothree.model.data.core.server.entity.EntityListItemAttribute;
import com.echothree.model.data.core.server.entity.EntityListItemDefault;
import com.echothree.model.data.core.server.entity.EntityLongAttribute;
import com.echothree.model.data.core.server.entity.EntityNameAttribute;
import com.echothree.model.data.core.server.entity.EntityStringAttribute;
import com.echothree.model.data.core.server.entity.EntityTimeAttribute;
import com.echothree.model.data.core.server.entity.Event;
import com.echothree.model.data.core.server.factory.EntityAliasFactory;
import com.echothree.model.data.core.server.factory.EntityAppearanceFactory;
import com.echothree.model.data.core.server.factory.EntityBlobAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityBooleanAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityClobAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityDateAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityEntityAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityGeoPointAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityIntegerAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityListItemAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityListItemDefaultFactory;
import com.echothree.model.data.core.server.factory.EntityLongAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityNameAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityStringAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityTimeAttributeFactory;
import com.echothree.model.data.core.server.value.EntityAliasValue;
import com.echothree.model.data.core.server.value.EntityAppearanceValue;
import com.echothree.model.data.core.server.value.EntityBlobAttributeValue;
import com.echothree.model.data.core.server.value.EntityBooleanAttributeValue;
import com.echothree.model.data.core.server.value.EntityClobAttributeValue;
import com.echothree.model.data.core.server.value.EntityDateAttributeValue;
import com.echothree.model.data.core.server.value.EntityEntityAttributeValue;
import com.echothree.model.data.core.server.value.EntityGeoPointAttributeValue;
import com.echothree.model.data.core.server.value.EntityIntegerAttributeValue;
import com.echothree.model.data.core.server.value.EntityListItemAttributeValue;
import com.echothree.model.data.core.server.value.EntityListItemDefaultValue;
import com.echothree.model.data.core.server.value.EntityLongAttributeValue;
import com.echothree.model.data.core.server.value.EntityNameAttributeValue;
import com.echothree.model.data.core.server.value.EntityStringAttributeValue;
import com.echothree.model.data.core.server.value.EntityTimeAttributeValue;
import com.echothree.model.data.party.common.pk.LanguagePK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.persistence.BaseValue;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Exercises actual persistence methods; only factories, clock, and event delivery are substituted.
 */
@ResourceLock("ValueCacheProvider")
public class EntityEditPersistenceTest {

    private static class Persistence {

        final EntityEditFixture fixture;
        final String kind;
        BaseValue created;
        BasePK related;
        EventTypes relatedType;
        int events;

        Persistence(String kind) {
            this.kind = kind;
            fixture = new EntityEditFixture(kind, EditMode.UPDATE);
        }

        Event event(EntityInstance owner, EventTypes type, BasePK detail, EventTypes detailType, BasePK actor) {
            assertSame(fixture.entityInstance, owner);
            assertEquals(EventTypes.MODIFY, type);
            assertEquals(fixture.updatedBy, actor);
            related = detail;
            relatedType = detailType;
            events++;
            fixture.persistenceOperations.add("event");
            return null;
        }

        Event event(BasePK owner, EventTypes type, BasePK detail, EventTypes detailType, BasePK actor) {
            assertEquals(fixture.entityAttribute.getPrimaryKey(), owner);
            assertEquals(EventTypes.MODIFY, type);
            assertEquals(fixture.updatedBy, actor);
            related = detail;
            relatedType = detailType;
            events++;
            fixture.persistenceOperations.add("event");
            return null;
        }

        Object control() {
            Object control = switch(kind) {
                case "EntityAppearance" -> new AppearanceControl() {
                    @Override
                    protected Event sendEvent(EntityInstance owner, EventTypes type, BasePK detail, EventTypes detailType, BasePK actor) {
                        return event(owner, type, detail, detailType, actor);
                    }
                };
                case "EntityAlias" -> new EntityAliasControl() {
                    @Override
                    protected Event sendEvent(EntityInstance owner, EventTypes type, BasePK detail, EventTypes detailType, BasePK actor) {
                        return event(owner, type, detail, detailType, actor);
                    }
                };
                default -> new CoreControl() {
                    @Override
                    protected Event sendEvent(EntityInstance owner, EventTypes type, BasePK detail, EventTypes detailType, BasePK actor) {
                        return event(owner, type, detail, detailType, actor);
                    }

                    @Override
                    protected Event sendEvent(BasePK owner, EventTypes type, BasePK detail, EventTypes detailType, BasePK actor) {
                        return event(owner, type, detail, detailType, actor);
                    }
                };
            };
            EntityEditFixture.inject(control, "session", EntityEditTestSession.create());
            Object factory = switch(kind) {
                case "EntityAppearance" -> new EntityAppearanceFactory() {
                    @Override
                    public EntityAppearance getEntityFromPK(EntityPermission permission, EntityAppearancePK key) {
                        assertEquals(EntityPermission.READ_WRITE, permission);
                        assertEquals(fixture.entityAppearance.getPrimaryKey(), key);
                        fixture.persistenceOperations.add("load");
                        return fixture.entityAppearance;
                    }

                    @Override
                    public EntityAppearance create(EntityInstancePK entityInstancePK, AppearancePK appearancePK, Long fromTime, Long thruTime) {
                        assertEquals(1000L, fromTime);
                        assertEquals(Session.MAX_TIME, thruTime);
                        fixture.persistenceOperations.add("create");
                        var value = new EntityAppearanceValue(new EntityAppearancePK(999L), entityInstancePK, appearancePK, fromTime, thruTime);
                        created = value;
                        return new EntityAppearance(value, EntityPermission.READ_WRITE);
                    }
                };
                case "EntityAlias" -> new EntityAliasFactory() {
                    @Override
                    public EntityAlias getEntityFromPK(EntityPermission permission, EntityAliasPK key) {
                        assertEquals(EntityPermission.READ_WRITE, permission);
                        assertEquals(fixture.entityAlias.getPrimaryKey(), key);
                        fixture.persistenceOperations.add("load");
                        return fixture.entityAlias;
                    }

                    @Override
                    public EntityAlias create(EntityInstancePK entityInstancePK, EntityAliasTypePK entityAliasTypePK, String alias, Long fromTime, Long thruTime) {
                        assertEquals(1000L, fromTime);
                        assertEquals(Session.MAX_TIME, thruTime);
                        fixture.persistenceOperations.add("create");
                        var value = new EntityAliasValue(new EntityAliasPK(999L), entityInstancePK, entityAliasTypePK, alias, fromTime, thruTime);
                        created = value;
                        return new EntityAlias(value, EntityPermission.READ_WRITE);
                    }
                };
                case "EntityListItemDefault" -> new EntityListItemDefaultFactory() {
                    @Override
                    public EntityListItemDefault getEntityFromPK(EntityPermission permission, EntityListItemDefaultPK key) {
                        assertEquals(EntityPermission.READ_WRITE, permission);
                        assertEquals(fixture.entityListItemDefault.getPrimaryKey(), key);
                        fixture.persistenceOperations.add("load");
                        return fixture.entityListItemDefault;
                    }

                    @Override
                    public EntityListItemDefault create(EntityAttributePK entityAttributePK, EntityListItemPK entityListItemPK, Long fromTime, Long thruTime) {
                        assertEquals(1000L, fromTime);
                        assertEquals(Session.MAX_TIME, thruTime);
                        fixture.persistenceOperations.add("create");
                        var value = new EntityListItemDefaultValue(new EntityListItemDefaultPK(999L), entityAttributePK, entityListItemPK, fromTime, thruTime);
                        created = value;
                        return new EntityListItemDefault(value, EntityPermission.READ_WRITE);
                    }
                };
                case "EntityBooleanAttribute" -> new EntityBooleanAttributeFactory() {
                    @Override
                    public EntityBooleanAttribute getEntityFromPK(EntityPermission permission, EntityBooleanAttributePK key) {
                        assertEquals(EntityPermission.READ_WRITE, permission);
                        assertEquals(fixture.entityBooleanAttribute.getPrimaryKey(), key);
                        fixture.persistenceOperations.add("load");
                        return fixture.entityBooleanAttribute;
                    }

                    @Override
                    public EntityBooleanAttribute create(EntityAttributePK entityAttributePK, EntityInstancePK entityInstancePK, Boolean booleanAttribute, Long fromTime, Long thruTime) {
                        assertEquals(1000L, fromTime);
                        assertEquals(Session.MAX_TIME, thruTime);
                        fixture.persistenceOperations.add("create");
                        var value = new EntityBooleanAttributeValue(new EntityBooleanAttributePK(999L), entityAttributePK, entityInstancePK, booleanAttribute, fromTime, thruTime);
                        created = value;
                        return new EntityBooleanAttribute(value, EntityPermission.READ_WRITE);
                    }
                };
                case "EntityIntegerAttribute" -> new EntityIntegerAttributeFactory() {
                    @Override
                    public EntityIntegerAttribute getEntityFromPK(EntityPermission permission, EntityIntegerAttributePK key) {
                        assertEquals(EntityPermission.READ_WRITE, permission);
                        assertEquals(fixture.entityIntegerAttribute.getPrimaryKey(), key);
                        fixture.persistenceOperations.add("load");
                        return fixture.entityIntegerAttribute;
                    }

                    @Override
                    public EntityIntegerAttribute create(EntityAttributePK entityAttributePK, EntityInstancePK entityInstancePK, Integer integerAttribute, Long fromTime, Long thruTime) {
                        assertEquals(1000L, fromTime);
                        assertEquals(Session.MAX_TIME, thruTime);
                        fixture.persistenceOperations.add("create");
                        var value = new EntityIntegerAttributeValue(new EntityIntegerAttributePK(999L), entityAttributePK, entityInstancePK, integerAttribute, fromTime, thruTime);
                        created = value;
                        return new EntityIntegerAttribute(value, EntityPermission.READ_WRITE);
                    }
                };
                case "EntityLongAttribute" -> new EntityLongAttributeFactory() {
                    @Override
                    public EntityLongAttribute getEntityFromPK(EntityPermission permission, EntityLongAttributePK key) {
                        assertEquals(EntityPermission.READ_WRITE, permission);
                        assertEquals(fixture.entityLongAttribute.getPrimaryKey(), key);
                        fixture.persistenceOperations.add("load");
                        return fixture.entityLongAttribute;
                    }

                    @Override
                    public EntityLongAttribute create(EntityAttributePK entityAttributePK, EntityInstancePK entityInstancePK, Long longAttribute, Long fromTime, Long thruTime) {
                        assertEquals(1000L, fromTime);
                        assertEquals(Session.MAX_TIME, thruTime);
                        fixture.persistenceOperations.add("create");
                        var value = new EntityLongAttributeValue(new EntityLongAttributePK(999L), entityAttributePK, entityInstancePK, longAttribute, fromTime, thruTime);
                        created = value;
                        return new EntityLongAttribute(value, EntityPermission.READ_WRITE);
                    }
                };
                case "EntityNameAttribute" -> new EntityNameAttributeFactory() {
                    @Override
                    public EntityNameAttribute getEntityFromPK(EntityPermission permission, EntityNameAttributePK key) {
                        assertEquals(EntityPermission.READ_WRITE, permission);
                        assertEquals(fixture.entityNameAttribute.getPrimaryKey(), key);
                        fixture.persistenceOperations.add("load");
                        return fixture.entityNameAttribute;
                    }

                    @Override
                    public EntityNameAttribute create(EntityAttributePK entityAttributePK, String nameAttribute, EntityInstancePK entityInstancePK, Long fromTime, Long thruTime) {
                        assertEquals(1000L, fromTime);
                        assertEquals(Session.MAX_TIME, thruTime);
                        fixture.persistenceOperations.add("create");
                        var value = new EntityNameAttributeValue(new EntityNameAttributePK(999L), entityAttributePK, nameAttribute, entityInstancePK, fromTime, thruTime);
                        created = value;
                        return new EntityNameAttribute(value, EntityPermission.READ_WRITE);
                    }
                };
                case "EntityStringAttribute" -> new EntityStringAttributeFactory() {
                    @Override
                    public EntityStringAttribute getEntityFromPK(EntityPermission permission, EntityStringAttributePK key) {
                        assertEquals(EntityPermission.READ_WRITE, permission);
                        assertEquals(fixture.entityStringAttribute.getPrimaryKey(), key);
                        fixture.persistenceOperations.add("load");
                        return fixture.entityStringAttribute;
                    }

                    @Override
                    public EntityStringAttribute create(EntityAttributePK entityAttributePK, EntityInstancePK entityInstancePK, LanguagePK languagePK, String stringAttribute, Long fromTime, Long thruTime) {
                        assertEquals(1000L, fromTime);
                        assertEquals(Session.MAX_TIME, thruTime);
                        fixture.persistenceOperations.add("create");
                        var value = new EntityStringAttributeValue(new EntityStringAttributePK(999L), entityAttributePK, entityInstancePK, languagePK, stringAttribute, fromTime, thruTime);
                        created = value;
                        return new EntityStringAttribute(value, EntityPermission.READ_WRITE);
                    }
                };
                case "EntityDateAttribute" -> new EntityDateAttributeFactory() {
                    @Override
                    public EntityDateAttribute getEntityFromPK(EntityPermission permission, EntityDateAttributePK key) {
                        assertEquals(EntityPermission.READ_WRITE, permission);
                        assertEquals(fixture.entityDateAttribute.getPrimaryKey(), key);
                        fixture.persistenceOperations.add("load");
                        return fixture.entityDateAttribute;
                    }

                    @Override
                    public EntityDateAttribute create(EntityAttributePK entityAttributePK, EntityInstancePK entityInstancePK, Integer dateAttribute, Long fromTime, Long thruTime) {
                        assertEquals(1000L, fromTime);
                        assertEquals(Session.MAX_TIME, thruTime);
                        fixture.persistenceOperations.add("create");
                        var value = new EntityDateAttributeValue(new EntityDateAttributePK(999L), entityAttributePK, entityInstancePK, dateAttribute, fromTime, thruTime);
                        created = value;
                        return new EntityDateAttribute(value, EntityPermission.READ_WRITE);
                    }
                };
                case "EntityTimeAttribute" -> new EntityTimeAttributeFactory() {
                    @Override
                    public EntityTimeAttribute getEntityFromPK(EntityPermission permission, EntityTimeAttributePK key) {
                        assertEquals(EntityPermission.READ_WRITE, permission);
                        assertEquals(fixture.entityTimeAttribute.getPrimaryKey(), key);
                        fixture.persistenceOperations.add("load");
                        return fixture.entityTimeAttribute;
                    }

                    @Override
                    public EntityTimeAttribute create(EntityAttributePK entityAttributePK, EntityInstancePK entityInstancePK, Long timeAttribute, Long fromTime, Long thruTime) {
                        assertEquals(1000L, fromTime);
                        assertEquals(Session.MAX_TIME, thruTime);
                        fixture.persistenceOperations.add("create");
                        var value = new EntityTimeAttributeValue(new EntityTimeAttributePK(999L), entityAttributePK, entityInstancePK, timeAttribute, fromTime, thruTime);
                        created = value;
                        return new EntityTimeAttribute(value, EntityPermission.READ_WRITE);
                    }
                };
                case "EntityBlobAttribute" -> new EntityBlobAttributeFactory() {
                    @Override
                    public EntityBlobAttribute getEntityFromPK(EntityPermission permission, EntityBlobAttributePK key) {
                        assertEquals(EntityPermission.READ_WRITE, permission);
                        assertEquals(fixture.entityBlobAttribute.getPrimaryKey(), key);
                        fixture.persistenceOperations.add("load");
                        return fixture.entityBlobAttribute;
                    }

                    @Override
                    public EntityBlobAttribute create(EntityAttributePK entityAttributePK, EntityInstancePK entityInstancePK, LanguagePK languagePK, ByteArray blobAttribute, MimeTypePK mimeTypePK, Long fromTime, Long thruTime) {
                        assertEquals(1000L, fromTime);
                        assertEquals(Session.MAX_TIME, thruTime);
                        fixture.persistenceOperations.add("create");
                        var value = new EntityBlobAttributeValue(new EntityBlobAttributePK(999L), entityAttributePK, entityInstancePK, languagePK, blobAttribute, mimeTypePK, fromTime, thruTime);
                        created = value;
                        return new EntityBlobAttribute(value, EntityPermission.READ_WRITE);
                    }
                };
                case "EntityClobAttribute" -> new EntityClobAttributeFactory() {
                    @Override
                    public EntityClobAttribute getEntityFromPK(EntityPermission permission, EntityClobAttributePK key) {
                        assertEquals(EntityPermission.READ_WRITE, permission);
                        assertEquals(fixture.entityClobAttribute.getPrimaryKey(), key);
                        fixture.persistenceOperations.add("load");
                        return fixture.entityClobAttribute;
                    }

                    @Override
                    public EntityClobAttribute create(EntityAttributePK entityAttributePK, EntityInstancePK entityInstancePK, LanguagePK languagePK, String clobAttribute, MimeTypePK mimeTypePK, Long fromTime, Long thruTime) {
                        assertEquals(1000L, fromTime);
                        assertEquals(Session.MAX_TIME, thruTime);
                        fixture.persistenceOperations.add("create");
                        var value = new EntityClobAttributeValue(new EntityClobAttributePK(999L), entityAttributePK, entityInstancePK, languagePK, clobAttribute, mimeTypePK, fromTime, thruTime);
                        created = value;
                        return new EntityClobAttribute(value, EntityPermission.READ_WRITE);
                    }
                };
                case "EntityEntityAttribute" -> new EntityEntityAttributeFactory() {
                    @Override
                    public EntityEntityAttribute getEntityFromPK(EntityPermission permission, EntityEntityAttributePK key) {
                        assertEquals(EntityPermission.READ_WRITE, permission);
                        assertEquals(fixture.entityEntityAttribute.getPrimaryKey(), key);
                        fixture.persistenceOperations.add("load");
                        return fixture.entityEntityAttribute;
                    }

                    @Override
                    public EntityEntityAttribute create(EntityAttributePK entityAttributePK, EntityInstancePK entityInstancePK, EntityInstancePK entityInstanceAttributePK, Long fromTime, Long thruTime) {
                        assertEquals(1000L, fromTime);
                        assertEquals(Session.MAX_TIME, thruTime);
                        fixture.persistenceOperations.add("create");
                        var value = new EntityEntityAttributeValue(new EntityEntityAttributePK(999L), entityAttributePK, entityInstancePK, entityInstanceAttributePK, fromTime, thruTime);
                        created = value;
                        return new EntityEntityAttribute(value, EntityPermission.READ_WRITE);
                    }
                };
                case "EntityListItemAttribute" -> new EntityListItemAttributeFactory() {
                    @Override
                    public EntityListItemAttribute getEntityFromPK(EntityPermission permission, EntityListItemAttributePK key) {
                        assertEquals(EntityPermission.READ_WRITE, permission);
                        assertEquals(fixture.entityListItemAttribute.getPrimaryKey(), key);
                        fixture.persistenceOperations.add("load");
                        return fixture.entityListItemAttribute;
                    }

                    @Override
                    public EntityListItemAttribute create(EntityAttributePK entityAttributePK, EntityInstancePK entityInstancePK, EntityListItemPK entityListItemPK, Long fromTime, Long thruTime) {
                        assertEquals(1000L, fromTime);
                        assertEquals(Session.MAX_TIME, thruTime);
                        fixture.persistenceOperations.add("create");
                        var value = new EntityListItemAttributeValue(new EntityListItemAttributePK(999L), entityAttributePK, entityInstancePK, entityListItemPK, fromTime, thruTime);
                        created = value;
                        return new EntityListItemAttribute(value, EntityPermission.READ_WRITE);
                    }
                };
                case "EntityGeoPointAttribute" -> new EntityGeoPointAttributeFactory() {
                    @Override
                    public EntityGeoPointAttribute getEntityFromPK(EntityPermission permission, EntityGeoPointAttributePK key) {
                        assertEquals(EntityPermission.READ_WRITE, permission);
                        assertEquals(fixture.entityGeoPointAttribute.getPrimaryKey(), key);
                        fixture.persistenceOperations.add("load");
                        return fixture.entityGeoPointAttribute;
                    }

                    @Override
                    public EntityGeoPointAttribute create(EntityAttributePK entityAttributePK, EntityInstancePK entityInstancePK, Integer latitude, Integer longitude, Long elevation, Long altitude, Long fromTime, Long thruTime) {
                        assertEquals(1000L, fromTime);
                        assertEquals(Session.MAX_TIME, thruTime);
                        fixture.persistenceOperations.add("create");
                        var value = new EntityGeoPointAttributeValue(new EntityGeoPointAttributePK(999L), entityAttributePK, entityInstancePK, latitude, longitude, elevation, altitude, fromTime, thruTime);
                        created = value;
                        return new EntityGeoPointAttribute(value, EntityPermission.READ_WRITE);
                    }
                };
                default -> throw new IllegalArgumentException(kind);
            };
            EntityEditFixture.inject(control, Character.toLowerCase(kind.charAt(0)) + kind.substring(1) + "Factory", factory);
            return control;
        }

        BaseValue original() {
            return (BaseValue)EntityEditFixture.call(fixture.values.get(kind + "Value"), "clone");
        }

    }

    static Stream<Arguments> variants() {
        return EntityEditCompatibilityTest.kinds().flatMap(kind -> Stream.of(Arguments.of(kind, false), Arguments.of(kind, true)));
    }

    static Stream<String> kinds() {
        return EntityEditCompatibilityTest.kinds();
    }

    private static void change(BaseValue value, String kind) {
        String property;
        Object replacement;
        switch(kind) {
            case "EntityAppearance" -> {
                property = "AppearancePK";
                replacement = new AppearancePK(2L);
            }
            case "EntityAlias" -> {
                property = "Alias";
                replacement = "Updated";
            }
            case "EntityListItemDefault", "EntityListItemAttribute" -> {
                property = "EntityListItemPK";
                replacement = new EntityListItemPK(2L);
            }
            case "EntityEntityAttribute" -> {
                property = "EntityInstanceAttributePK";
                replacement = new EntityInstancePK(2L);
            }
            case "EntityBooleanAttribute" -> {
                property = "BooleanAttribute";
                replacement = true;
            }
            case "EntityIntegerAttribute" -> {
                property = "IntegerAttribute";
                replacement = 20;
            }
            case "EntityLongAttribute" -> {
                property = "LongAttribute";
                replacement = 20L;
            }
            case "EntityDateAttribute" -> {
                property = "DateAttribute";
                replacement = 20261125;
            }
            case "EntityTimeAttribute" -> {
                property = "TimeAttribute";
                replacement = 20L;
            }
            case "EntityGeoPointAttribute" -> {
                property = "Latitude";
                replacement = 30;
            }
            case "EntityBlobAttribute" -> {
                property = "BlobAttribute";
                replacement = new ByteArray(new byte[]{3, 4});
            }
            default -> {
                property = kind.substring(6);
                replacement = "Updated";
            }
        }
        EntityEditFixture.call(value, "set" + property, replacement);
        if(kind.equals("EntityGeoPointAttribute")) {
            EntityEditFixture.call(value, "setElevation", (Object)null);
            EntityEditFixture.call(value, "setAltitude", (Object)null);
        }
    }

    @ParameterizedTest
    @MethodSource("variants")
    public void changedValuesPreserveHistoryOwnershipAndEvents(String kind, boolean trackRevisions)
            throws Exception {
        var persistence = new Persistence(kind);
        var fixture = persistence.fixture;
        fixture.property("EntityAttributeDetail", "trackRevisions", trackRevisions);
        var value = persistence.original();
        change(value, kind);
        var control = persistence.control();
        EntityEditFixture.call(control, "update" + kind + "FromValue", value, fixture.updatedBy);
        assertNotNull(persistence.created);
        assertEquals(List.of("load", trackRevisions || kind.equals("EntityAppearance") || kind.equals("EntityAlias") ? "expire" : "remove", "create", "event"), fixture.persistenceOperations);
        assertEquals(1, persistence.events);
        for(var field : persistence.created.getClass().getDeclaredFields()) {
            if(field.getName().endsWith("HasBeenModified") || field.getName().startsWith("_") || java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            if(field.getName().equals("fromTime")) {
                assertEquals(1000L, field.get(persistence.created));
            } else if(field.getName().equals("thruTime")) {
                assertEquals(Session.MAX_TIME, field.get(persistence.created));
            } else {
                assertEquals(field.get(value), field.get(persistence.created), kind + "." + field.getName());
            }
        }
        assertEquals(kind.equals("EntityListItemDefault") ? EventTypes.DELETE : EventTypes.MODIFY, persistence.relatedType);
        assertEquals(switch(kind) {
            case "EntityAppearance", "EntityListItemDefault" -> persistence.created.getPrimaryKey();
            case "EntityAlias" -> fixture.entityAliasType.getPrimaryKey();
            default -> fixture.entityAttribute.getPrimaryKey();
        }, persistence.related);
    }

    @ParameterizedTest
    @MethodSource("kinds")
    public void unchangedValuesDoNotLoadStoreCreateOrSendEvents(String kind) {
        var persistence = new Persistence(kind);
        var control = persistence.control();
        var value = persistence.original();
        assertFalse(value.hasBeenModified());
        EntityEditFixture.call(control, "update" + kind + "FromValue", value, persistence.fixture.updatedBy);
        assertTrue(persistence.fixture.persistenceOperations.isEmpty());
        assertNull(persistence.created);
        assertEquals(0, persistence.events);
    }

    @ParameterizedTest
    @MethodSource("kinds")
    public void ownershipCannotBeMovedByAnIncomingValue(String kind)
            throws Exception {
        var persistence = new Persistence(kind);
        var value = persistence.original();
        change(value, kind);
        var ownerFields = List.of("entityAttributePK", "entityInstancePK", "languagePK", "entityAliasTypePK");
        for(var field : value.getClass().getDeclaredFields()) {
            if(ownerFields.contains(field.getName())) {
                var foreignKey = field.getType().getConstructor(Long.class).newInstance(999L);
                var name = field.getName();
                EntityEditFixture.call(value, "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1), foreignKey);
            }
        }
        EntityEditFixture.call(persistence.control(), "update" + kind + "FromValue", value, persistence.fixture.updatedBy);
        var original = persistence.fixture.values.get(kind + "Value");
        for(var field : value.getClass().getDeclaredFields()) {
            if(ownerFields.contains(field.getName())) {
                field.setAccessible(true);
                assertEquals(field.get(original), field.get(persistence.created), kind + "." + field.getName());
            }
        }
    }

}

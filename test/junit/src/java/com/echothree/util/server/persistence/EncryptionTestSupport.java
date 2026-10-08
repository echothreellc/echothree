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

package com.echothree.util.server.persistence;

import com.echothree.model.control.core.server.control.EncryptionKeyControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.core.common.pk.BaseEncryptionKeyPK;
import com.echothree.model.data.core.common.pk.EntityEncryptionKeyPK;
import com.echothree.model.data.core.common.pk.EntityInstancePK;
import com.echothree.model.data.core.common.pk.EntityTypePK;
import com.echothree.model.data.core.server.entity.BaseEncryptionKey;
import com.echothree.model.data.core.server.entity.EntityEncryptionKey;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.value.BaseEncryptionKeyValue;
import com.echothree.model.data.core.server.value.EntityEncryptionKeyValue;
import com.echothree.model.data.core.server.value.EntityInstanceValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.workflow.common.pk.WorkflowEntityStatusPK;
import com.echothree.model.data.workflow.common.pk.WorkflowPK;
import com.echothree.model.data.workflow.common.pk.WorkflowStepDetailPK;
import com.echothree.model.data.workflow.common.pk.WorkflowStepPK;
import com.echothree.model.data.workflow.common.pk.WorkflowStepTypePK;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.model.data.workflow.server.entity.WorkflowStepDetail;
import com.echothree.model.data.workflow.server.value.WorkflowEntityStatusValue;
import com.echothree.model.data.workflow.server.value.WorkflowStepDetailValue;
import com.echothree.model.data.workflow.server.value.WorkflowStepValue;
import com.echothree.util.common.persistence.BaseKey;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.inject.spi.CDIProvider;
import javax.enterprise.util.TypeLiteral;
import org.infinispan.Cache;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.parallel.ResourceLock;
import static com.echothree.model.control.core.common.workflow.BaseEncryptionKeyStatusConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@ResourceLock("CDIProvider")
abstract class EncryptionTestSupport {

    protected final Map<String, Object> securityCache = new HashMap<>();
    protected final MemoryKeyControl control = new MemoryKeyControl();
    private TestCdi cdi;
    protected BaseKey firstBaseKey;
    protected BaseKey secondBaseKey;
    protected String workflowStepName = WorkflowStep_BASE_ENCRYPTION_KEY_STATUS_ACTIVE;

    @BeforeEach
    public void setUp() {
        firstBaseKey = new BaseKey(new SecretKeySpec(new byte[32], "AES"), new byte[16]);
        secondBaseKey = new BaseKey(new SecretKeySpec(HexFormat.of().parseHex("01".repeat(32)), "AES"), new byte[16]);
        securityCache.put("/com/echothree/security/base/BaseKey1", firstBaseKey);
        securityCache.put("/com/echothree/security/base/BaseKey2", secondBaseKey);
        var caches = new Caches();
        caches.securityCache = cache(securityCache);
        caches.dataCache = cache(new HashMap<>());
        cdi = new TestCdi();
        cdi.beans.put(Caches.class, caches);
        cdi.beans.put(EncryptionKeyControl.class, control);
        cdi.beans.put(EntityInstanceControl.class, new EntityInstanceControl() {
            @Override public EntityInstance getEntityInstanceByBasePK(BasePK pk) {
                return new EntityInstance(new EntityInstanceValue(new EntityInstancePK(1L), new EntityTypePK(1L), 1L, null), EntityPermission.READ_ONLY);
            }
        });
        cdi.beans.put(WorkflowControl.class, new WorkflowControl() {
            @Override public WorkflowEntityStatus getWorkflowEntityStatusByEntityInstanceUsingNames(String name, EntityInstance instance) {
                assertEquals(Workflow_BASE_ENCRYPTION_KEY_STATUS, name);
                return new WorkflowEntityStatus(new WorkflowEntityStatusValue(new WorkflowEntityStatusPK(1L), new EntityInstancePK(1L),
                        new WorkflowStepPK(1L), null, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                    @Override public WorkflowStep getWorkflowStep() {
                        return new WorkflowStep(new WorkflowStepValue(new WorkflowStepPK(1L), new WorkflowStepDetailPK(1L),
                                new WorkflowStepDetailPK(1L)), EntityPermission.READ_ONLY) {
                            @Override public WorkflowStepDetail getLastDetail() {
                                return new WorkflowStepDetail(new WorkflowStepDetailValue(new WorkflowStepDetailPK(1L), new WorkflowStepPK(1L),
                                        new WorkflowPK(1L), workflowStepName, new WorkflowStepTypePK(1L), false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
                            }
                        };
                    }
                };
            }
        });
        cdi.install();
        cdi.beans.put(Session.class, new Session());
    }

    @AfterEach
    public void tearDown() {
        if(cdi != null) {
            cdi.restore();
        }
    }

    @SuppressWarnings("unchecked")
    private Cache<String, Object> cache(Map<String, Object> values) {
        return (Cache<String, Object>)Proxy.newProxyInstance(Cache.class.getClassLoader(), new Class<?>[] {Cache.class},
                (proxy, method, args) -> switch(method.getName()) {
                    case "get" -> values.get(args[0]);
                    case "put" -> values.put((String)args[0], args[1]);
                    default -> throw new UnsupportedOperationException(method.getName());
                });
    }

    protected BaseFactory<?, ?> factory(String vendor, String entity) {
        return (BaseFactory<?, ?>)Proxy.newProxyInstance(BaseFactory.class.getClassLoader(), new Class<?>[] {BaseFactory.class},
                (proxy, method, args) -> switch(method.getName()) {
                    case "getComponentVendorName" -> vendor;
                    case "getEntityTypeName" -> entity;
                    default -> throw new UnsupportedOperationException(method.getName());
                });
    }

    protected String keyNameHash(String vendor, String entity) throws Exception {
        var name = vendor + '.' + entity + ".#HMAC_SHA256#";
        return Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-256").digest(name.getBytes(StandardCharsets.UTF_8)));
    }

    protected byte[] transform(BaseKey key, byte[] bytes, int mode) throws Exception {
        var cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(mode, key.getKey(), new IvParameterSpec(key.getIv()));
        return cipher.doFinal(bytes);
    }

    protected BaseFactory<?, ?> seed(String vendor, String entity, String keyHex) throws Exception {
        var bytes = HexFormat.of().parseHex(keyHex);
        var wrapped = transform(secondBaseKey, transform(firstBaseKey, bytes, Cipher.ENCRYPT_MODE), Cipher.ENCRYPT_MODE);
        control.put(keyNameHash(vendor, entity), Base64.getEncoder().encodeToString(wrapped));
        return factory(vendor, entity);
    }

    protected static class MemoryKeyControl extends EncryptionKeyControl {
        final Map<String, EntityEncryptionKey> entries = new HashMap<>();
        int creates;
        int baseCreates;
        final Map<String, BaseEncryptionKey> baseEntries = new HashMap<>();

        @Override
        public long countEntityEncryptionKeys() { return entries.size(); }

        @Override
        public List<EntityEncryptionKey> getEntityEncryptionKeysForUpdate() { return new ArrayList<>(entries.values()); }

        @Override
        public BaseEncryptionKey createBaseEncryptionKey(ExecutionErrorAccumulator eea, BaseKey first, BaseKey second, PartyPK createdBy) {
            var hash = Sha1Utils.getInstance().encode(first, second);
            var entity = new BaseEncryptionKey(new BaseEncryptionKeyValue(new BaseEncryptionKeyPK((long)++baseCreates),
                    "TestBaseKey" + baseCreates, hash), EntityPermission.READ_ONLY);
            baseEntries.put(hash, entity);
            return entity;
        }

        @Override
        public BaseEncryptionKey getBaseEncryptionKeyBySha1Hash(String hash) { return baseEntries.get(hash); }


        EntityEncryptionKey put(String nameHash, String secretKey) {
            var entity = new EntityEncryptionKey(new EntityEncryptionKeyValue(new EntityEncryptionKeyPK((long)entries.size() + 1),
                    nameHash, secretKey), EntityPermission.READ_WRITE);
            entries.put(nameHash, entity);
            return entity;
        }

        @Override
        public EntityEncryptionKey getEntityEncryptionKeyByNameHash(String nameHash) {
            return entries.get(nameHash);
        }

        @Override
        public EntityEncryptionKey createEntityEncryptionKey(String nameHash, String secretKey) {
            creates++;
            assertFalse(entries.containsKey(nameHash));
            return put(nameHash, secretKey);
        }
    }

    // Supply only the CDI services needed by the real EncryptionUtils key-loading path.
    private static class TestCdi extends CDI<Object> {
        final Map<Class<?>, Object> beans = new HashMap<>();
        private CDIProvider previous;

        void install() {
            previous = configuredProvider;
            setCDIProvider(() -> this);
        }

        void restore() {
            configuredProvider = previous;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <U> Instance<U> select(Class<U> type, Annotation... qualifiers) {
            return (Instance<U>)Proxy.newProxyInstance(Instance.class.getClassLoader(), new Class<?>[] {Instance.class},
                    (proxy, method, args) -> {
                        if(method.getName().equals("get")) {
                            return type.cast(java.util.Objects.requireNonNull(beans.get(type), type.getName()));
                        }
                        throw new UnsupportedOperationException(method.getName());
                    });
        }

        @Override public BeanManager getBeanManager() { throw new UnsupportedOperationException(); }
        @Override public Instance<Object> select(Annotation... qualifiers) { throw new UnsupportedOperationException(); }
        @Override public <U> Instance<U> select(TypeLiteral<U> type, Annotation... qualifiers) { throw new UnsupportedOperationException(); }
        @Override public boolean isUnsatisfied() { return false; }
        @Override public boolean isAmbiguous() { return false; }
        @Override public void destroy(Object instance) { throw new UnsupportedOperationException(); }
        @Override public Object get() { throw new UnsupportedOperationException(); }
        @Override public Iterator<Object> iterator() { return beans.values().iterator(); }
    }


}

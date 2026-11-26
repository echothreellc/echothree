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

import com.echothree.model.control.core.server.control.CommandControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.core.common.pk.CommandMessageTypePK;
import com.echothree.model.data.core.server.entity.CommandMessage;
import com.echothree.model.data.core.server.entity.CommandMessageType;
import com.echothree.model.data.core.server.value.CommandMessageTypeValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.valuecache.NullValueCacheImpl;
import com.echothree.util.server.persistence.valuecache.ValueCacheProvider;
import com.echothree.util.server.persistence.valuecache.ValueCacheProviderImpl;
import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.Iterator;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.inject.spi.CDIProvider;
import javax.enterprise.util.TypeLiteral;
import org.junit.jupiter.api.function.Executable;
import static org.junit.jupiter.api.Assertions.*;

/** Isolate localization dependencies while exercising real Logic exception construction. */
final class LogicExceptionTestSupport implements AutoCloseable {

    private final TestCdi cdi;
    private String localizedKey;

    LogicExceptionTestSupport() {
        var commandMessageType = new CommandMessageType(new CommandMessageTypeValue(new CommandMessageTypePK(1L), null, null), EntityPermission.READ_ONLY);
        var commandControl = new CommandControl() {
            @Override
            public CommandMessageType getCommandMessageTypeByName(String name) {
                assertEquals("Error", name);
                return commandMessageType;
            }

            @Override
            public CommandMessage getCommandMessageByKey(CommandMessageType type, String key) {
                assertSame(commandMessageType, type);
                localizedKey = key;
                return null;
            }
        };
        var partyControl = new PartyControl() {
            @Override
            public Language getDefaultLanguage() {
                return null;
            }
        };
        Session session;
        // Session construction normally creates a server cache. Match existing test-session isolation.
        try {
            var holder = Class.forName(ValueCacheProviderImpl.class.getName() + "$ValueCacheProviderHolder");
            var field = holder.getDeclaredField("instance");
            field.setAccessible(true);
            var original = field.get(null);
            try {
                field.set(null, (ValueCacheProvider)NullValueCacheImpl::new);
                session = new Session() {
                    @Override
                    public <T extends BaseModelControl> T getSessionModelController(Class<T> type) {
                        if(type == CommandControl.class) {
                            return type.cast(commandControl);
                        }
                        if(type == PartyControl.class) {
                            return type.cast(partyControl);
                        }
                        throw new AssertionError("Unexpected controller: " + type);
                    }
                };
            } finally {
                field.set(null, original);
            }
        } catch(ReflectiveOperationException exception) {
            throw new LinkageError("Unable to construct exception-localization test session", exception);
        }
        cdi = new TestCdi(session);
        cdi.install();
    }

    void assertException(Class<? extends RuntimeException> expected, String key, Executable operation) {
        var exception = assertThrowsExactly(expected, operation);
        assertEquals(key, localizedKey);
        assertEquals("??" + key + "??", exception.getMessage());
    }

    @Override
    public void close() {
        cdi.restore();
    }

    private static class TestCdi extends CDI<Object> {
        private final Session session;
        private CDIProvider previous;

        TestCdi(Session session) {
            this.session = session;
        }

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
            assertSame(Session.class, type);
            return (Instance<U>)Proxy.newProxyInstance(Instance.class.getClassLoader(), new Class<?>[] {Instance.class},
                    (proxy, method, arguments) -> switch(method.getName()) {
                        case "get" -> type.cast(session);
                        default -> throw new UnsupportedOperationException(method.getName());
                    });
        }

        @Override public BeanManager getBeanManager() { throw new UnsupportedOperationException(); }
        @Override public Instance<Object> select(Annotation... qualifiers) { throw new UnsupportedOperationException(); }
        @Override public <U> Instance<U> select(TypeLiteral<U> type, Annotation... qualifiers) { throw new UnsupportedOperationException(); }
        @Override public boolean isUnsatisfied() { return false; }
        @Override public boolean isAmbiguous() { return false; }
        @Override public void destroy(Object instance) { throw new UnsupportedOperationException(); }
        @Override public Object get() { throw new UnsupportedOperationException(); }
        @Override public Iterator<Object> iterator() { return Collections.singleton((Object)session).iterator(); }
    }

}

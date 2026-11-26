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

import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.DateTimeFormatDetail;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.party.server.entity.TimeZoneDetail;
import com.echothree.model.data.party.server.value.DateTimeFormatDetailValue;
import com.echothree.model.data.party.server.value.DateTimeFormatValue;
import com.echothree.model.data.party.server.value.TimeZoneDetailValue;
import com.echothree.model.data.party.server.value.TimeZoneValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.Iterator;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.inject.spi.CDIProvider;
import javax.enterprise.util.TypeLiteral;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Supplies only the user formatting preferences needed by the real DateUtils.
 */
class EntityEditFormattingEnvironment extends CDI<Object> implements AutoCloseable {

    private final CDIProvider previous = configuredProvider;
    private final Session session = EntityEditTestSession.create();
    private final DateTimeFormatDetail dateDetail = new DateTimeFormatDetail(EntityEditFixture.construct(DateTimeFormatDetailValue.class), EntityPermission.READ_ONLY);
    private final TimeZoneDetail zoneDetail = new TimeZoneDetail(EntityEditFixture.construct(TimeZoneDetailValue.class), EntityPermission.READ_ONLY);
    private final DateTimeFormat format = new DateTimeFormat(EntityEditFixture.construct(DateTimeFormatValue.class), EntityPermission.READ_ONLY) {
        @Override
        public DateTimeFormatDetail getLastDetail() {
            return dateDetail;
        }
    };
    private final TimeZone zone = new TimeZone(EntityEditFixture.construct(TimeZoneValue.class), EntityPermission.READ_ONLY) {
        @Override
        public TimeZoneDetail getLastDetail() {
            return zoneDetail;
        }
    };
    private final UserControl users = new UserControl() {
        @Override
        public DateTimeFormat getPreferredDateTimeFormatFromUserVisit(UserVisit visit) {
            return format;
        }

        @Override
        public TimeZone getPreferredTimeZoneFromUserVisit(UserVisit visit) {
            return zone;
        }
    };

    EntityEditFormattingEnvironment() {
        EntityEditFixture.inject(dateDetail.getDateTimeFormatDetailValue(), "javaShortDateFormat", "yyyy-MM-dd");
        EntityEditFixture.inject(dateDetail.getDateTimeFormatDetailValue(), "javaTimeFormatSeconds", "HH:mm:ss");
        EntityEditFixture.inject(zoneDetail.getTimeZoneDetailValue(), "javaTimeZoneName", "UTC");
        setCDIProvider(() -> this);
    }

    @Override
    public void close() {
        configuredProvider = previous;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U> Instance<U> select(Class<U> type, Annotation... qualifiers) {
        assertTrue(type == UserControl.class || type == Session.class);
        var selected = type == Session.class ? session : users;
        return (Instance<U>)Proxy.newProxyInstance(Instance.class.getClassLoader(), new Class<?>[]{Instance.class},
                (proxy, method, args) -> {
                    if(method.getName().equals("get")) {
                        return selected;
                    }
                    throw new UnsupportedOperationException(method.getName());
                });
    }

    @Override
    public BeanManager getBeanManager() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Instance<Object> select(Annotation... qualifiers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <U> Instance<U> select(TypeLiteral<U> type, Annotation... qualifiers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isUnsatisfied() {
        return false;
    }

    @Override
    public boolean isAmbiguous() {
        return false;
    }

    @Override
    public void destroy(Object instance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object get() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<Object> iterator() {
        return Collections.singleton((Object)users).iterator();
    }

}

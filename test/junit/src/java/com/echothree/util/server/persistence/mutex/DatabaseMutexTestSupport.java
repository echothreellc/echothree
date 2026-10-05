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

package com.echothree.util.server.persistence.mutex;

import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.valuecache.NullValueCacheImpl;
import com.echothree.util.server.persistence.valuecache.ValueCacheProvider;
import com.echothree.util.server.persistence.valuecache.ValueCacheProviderImpl;
import org.jooq.DSLContext;

final class DatabaseMutexTestSupport {

    private DatabaseMutexTestSupport() {
    }

    static synchronized Session sessionFor(final DSLContext ctx) {
        // Session eagerly creates a value cache through CDI. These JDBC unit tests
        // need no cache or container, so substitute the no-op provider only during
        // construction and restore the original provider even if construction fails.
        try {
            var holder = Class.forName(ValueCacheProviderImpl.class.getName() + "$ValueCacheProviderHolder");
            var field = holder.getDeclaredField("instance");
            field.setAccessible(true);
            var original = field.get(null);

            try {
                field.set(null, (ValueCacheProvider)NullValueCacheImpl::new);
                return new Session() {
                    @Override
                    public DSLContext getDslContext() {
                        return ctx;
                    }
                };
            } finally {
                field.set(null, original);
            }
        } catch(ReflectiveOperationException e) {
            throw new LinkageError("Unable to construct the mutex test session", e);
        }
    }

}

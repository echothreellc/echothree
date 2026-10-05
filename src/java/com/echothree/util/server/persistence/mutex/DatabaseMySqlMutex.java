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
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.jooq.impl.DSL;

@ApplicationScoped
@DatabaseMutexFlavor(DatabaseMutexFlavor.Flavor.MYSQL)
public class DatabaseMySqlMutex
        implements DatabaseMutex {

    @Inject
    Session session;

    @Override
    public boolean acquireLock(final String lockName, final int timeoutSeconds) {
        var result = session.getDslContext()
                .select(DSL.function("GET_LOCK", Integer.class, DSL.val(lockName), DSL.val(timeoutSeconds)))
                .fetchOne(0, Integer.class);

        return Integer.valueOf(1).equals(result);
    }

    @Override
    public boolean releaseLock(final String lockName) {
        var result = session.getDslContext()
                .select(DSL.function("RELEASE_LOCK", Integer.class, DSL.val(lockName)))
                .fetchOne(0, Integer.class);

        return Integer.valueOf(1).equals(result);
    }

}

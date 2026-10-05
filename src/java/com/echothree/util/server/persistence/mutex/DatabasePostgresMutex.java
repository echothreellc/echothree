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
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.commons.codec.digest.DigestUtils;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

/**
 * Postgres session advisory locks keyed by the first 64 bits of SHA-256.
 * Hash collisions cause otherwise distinct names to share a lock.
 */
@ApplicationScoped
@DatabaseFlavor(DatabaseFlavor.Flavor.POSTGRES)
public class DatabasePostgresMutex
        implements DatabaseMutex {

    private static final long POLL_INTERVAL_NANOS = TimeUnit.MILLISECONDS.toNanos(50);

    @Inject
    Session session;

    private long lockKey(final String lockName) {
        var bytes = Objects.requireNonNull(lockName, "lockName").getBytes(StandardCharsets.UTF_8);

        // ByteBuffer reads in big-endian order; keep this mapping stable across callers.
        return ByteBuffer.wrap(DigestUtils.sha256(bytes)).getLong();
    }

    @Override
    public boolean acquireLock(final String lockName, final int timeoutSeconds) {
        var key = lockKey(lockName);
        var timeoutNanos = TimeUnit.SECONDS.toNanos(timeoutSeconds);
        var started = System.nanoTime();
        var acquired = false;

        do {
            if(Thread.currentThread().isInterrupted()) {
                break;
            }

            var result = session.getDslContext()
                    .select(DSL.function("pg_try_advisory_lock", Boolean.class, DSL.val(key)))
                    .fetchOne(0, Boolean.class);

            acquired = Boolean.TRUE.equals(result);

            if(acquired || result == null) {
                break;
            }

            var remaining = timeoutSeconds < 0 ? Long.MAX_VALUE : timeoutNanos - (System.nanoTime() - started);
            if(remaining <= 0) {
                break;
            }

            try {
                TimeUnit.NANOSECONDS.sleep(Math.min(POLL_INTERVAL_NANOS, remaining));
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        } while(timeoutSeconds < 0 || System.nanoTime() - started < timeoutNanos);

        return acquired;
    }

    @Override
    public boolean releaseLock(final String lockName) {
        var result = session.getDslContext()
                .select(DSL.function("pg_advisory_unlock", Boolean.class, DSL.val(lockKey(lockName))))
                .fetchOne(0, Boolean.class);

        return Boolean.TRUE.equals(result);
    }

}

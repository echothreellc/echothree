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

/**
 * A named, connection-scoped database mutex.
 *
 * <p>Locks use the injected Session's connection. Always release in a finally
 * block after successful acquisition, within the same session; committing or
 * rolling back does not release the lock.
 *
 * <p>Inject the generic interface to select the implementation for the session's
 * database flavor, then run protected work only after successful acquisition:
 *
 * <pre>{@code
 * @Inject
 * DatabaseMutex databaseMutex;
 *
 * public void performProtectedWork() {
 *     if(databaseMutex.acquireLock("lockName")) {
 *         try {
 *             // Protected work
 *         } finally {
 *             if(!databaseMutex.releaseLock("lockName")) {
 *                 // Handle an unsuccessful release.
 *             }
 *         }
 *     } else {
 *         // Lock was not acquired; skip or retry the work.
 *     }
 * }
 * }</pre>
 */
public interface DatabaseMutex {

    /** Default acquisition timeout, in seconds. */
    int DEFAULT_TIMEOUT_SECONDS = 15;

    /**
     * Attempts to acquire the named lock using {@link #DEFAULT_TIMEOUT_SECONDS}.
     *
     * @param lockName the name of the lock
     * @return true only when the lock was acquired
     */
    default boolean acquireLock(String lockName) {
        return acquireLock(lockName, DEFAULT_TIMEOUT_SECONDS);
    }

    /**
     * Attempts to acquire the named lock within the specified timeout.
     *
     * @param timeoutSeconds zero to try once, positive to wait up to that many
     * seconds, or negative to wait indefinitely
     * @return true only when the lock was acquired; false on timeout or a null
     * database result. Postgres also returns false when interrupted, preserving
     * the interrupt flag. SQL execution errors propagate to the caller.
     */
    boolean acquireLock(String lockName, int timeoutSeconds);

    /**
     * Releases one acquisition of the named lock on the session's connection.
     *
     * @param lockName the name of the lock
     * @return true only when the database confirms release; false when the lock
     * is not owned by this session, does not exist, or the result is null.
     * SQL execution errors propagate to the caller.
     */
    boolean releaseLock(String lockName);

}

// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ThreadSession {
    
    private static Log log = LogFactory.getLog(ThreadSession.class);
    private static final ThreadLocal<Session> sessions = new ThreadLocal<>();

    private ThreadSession() {}

    public static Session currentSession() {
        var session = sessions.get();
        
        if(session == null) {
            session = SessionFactory.getInstance().getSession();
            sessions.set(session);
            
            if(PersistenceDebugFlags.LogThreads) {
                log.info("Created Session for Thread " + Thread.currentThread().getName());
            }
        }
        
        return session;
    }

    public static void pushSessionEntityCache() {
        currentSession().pushSessionEntityCache();
    }

    public static void popSessionEntityCache() {
        currentSession().popSessionEntityCache();
    }

    static class PreservedSession {
        private Session session;

        private PreservedSession(Session session) {
            this.session = session;
        }
    }

    // Utilize via ThreadUtils
    static PreservedSession preserve() {
        var session = sessions.get();

        if(session != null) {
            sessions.remove();

            if(PersistenceDebugFlags.LogThreads) {
                log.info("Preserved Session for Thread " + Thread.currentThread().getName());
            }
        }

        return new PreservedSession(session);
    }

    // Utilize via ThreadUtils
    static void restore(PreservedSession preservedSession) {
        var session = preservedSession.session;

        if(session != null) {
            sessions.set(session);

            if(PersistenceDebugFlags.LogThreads) {
                log.info("Restored Session for Thread " + Thread.currentThread().getName());
            }
        }
    }

    // Utilize via ThreadUtils
    static void close() {
        var session = sessions.get();
        
        if(session != null) {
            session.close();
            sessions.remove();
            
            if(PersistenceDebugFlags.LogThreads) {
                log.info("Closed Session for Thread " + Thread.currentThread().getName());
            }
        }
    }
    
}

// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

public class ThreadCaches {
    
    private static Log log = LogFactory.getLog(ThreadCaches.class);
    private static final ThreadLocal<Caches> cacheses = new ThreadLocal<>();

    private ThreadCaches() {}

    public static Caches currentCaches() {
        Caches caches = cacheses.get();
        
        if(caches == null) {
            caches = CachesFactory.getInstance().getCaches();
            cacheses.set(caches);
            
            if(PersistenceDebugFlags.LogThreads) {
                log.info("Created Caches for Thread " + Thread.currentThread().getName());
            }
        }
        
        return caches;
    }

    static class PreservedCaches {
        private Caches caches;

        private PreservedCaches(Caches caches) {
            this.caches = caches;
        }
    }

    // Utilize via ThreadUtils
    static ThreadCaches.PreservedCaches preserve() {
        Caches caches = cacheses.get();

        if(caches != null) {
            cacheses.remove();

            if(PersistenceDebugFlags.LogThreads) {
                log.info("Preserved Caches for Thread " + Thread.currentThread().getName());
            }
        }

        return new ThreadCaches.PreservedCaches(caches);
    }

    // Utilize via ThreadUtils
    static void restore(ThreadCaches.PreservedCaches preservedCaches) {
        var caches = preservedCaches.caches;

        if(caches != null) {
            cacheses.set(caches);

            if(PersistenceDebugFlags.LogThreads) {
                log.info("Restored Caches for Thread " + Thread.currentThread().getName());
            }
        }
    }

    // Utilize via ThreadUtils
    public static void close() {
        Caches caches = cacheses.get();
        
        if(caches != null) {
            caches.close();
            cacheses.remove();
            
            if(PersistenceDebugFlags.LogThreads) {
                log.info("Closed Caches for Thread " + Thread.currentThread().getName());
            }
        }
    }
    
}

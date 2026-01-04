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

package com.echothree.util.server.persistence.valuecache;

import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.BaseValue;
import com.echothree.util.server.persistence.PersistenceDebugFlags;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoggingValueCacheImpl
        implements ValueCache {

    private Log log = LogFactory.getLog(this.getClass());
    private String threadName = Thread.currentThread().getName();
    private ValueCache wrappedValueCache;

    private int putCount;
    private int getCount;
    private int getCacheHitCount;
    private int removeCount;

    /** Creates a new instance of LoggingValueCacheImpl */
    public LoggingValueCacheImpl(ValueCache wrappedValueCache) {
        this.wrappedValueCache = wrappedValueCache;
    }

    @Override
    public void put(BaseValue baseValue) {
        if(PersistenceDebugFlags.LogValueCacheActions) {
            log.info(threadName + " put " + baseValue.getPrimaryKey());
        }
        
        wrappedValueCache.put(baseValue);
        putCount++;
    }

    @Override
    public BaseValue get(BasePK basePK) {
        if(PersistenceDebugFlags.LogValueCacheActions) {
            log.info(threadName + " get " + basePK);
        }

        var baseValue = wrappedValueCache.get(basePK);
        getCount++;
        if(baseValue != null) {
            getCacheHitCount++;
        }
        
        return baseValue;
    }

    @Override
    public void remove(BasePK basePK) {
        if(PersistenceDebugFlags.LogValueCacheActions) {
            log.info(threadName + " remove " + basePK);
        }
        
        wrappedValueCache.remove(basePK);
        removeCount++;
    }

    @Override
    public String toString() {
        return threadName + " putCount = " + putCount + ", getCount = " +
                getCount + ", getCacheHitCount = " + getCacheHitCount + ", removeCount = " + removeCount;
    }

}

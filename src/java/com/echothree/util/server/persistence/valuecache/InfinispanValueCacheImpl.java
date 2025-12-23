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
import com.echothree.util.server.persistence.ThreadCaches;
import org.infinispan.Cache;
import org.infinispan.context.Flag;

public class InfinispanValueCacheImpl
        implements ValueCache {

    private static final String FQN_PREFIX = "/";
    private static final String VALUE = "value";

    private Cache<String, Object> dataCache;

    /** Creates a new instance of InfinispanValueCacheImpl */
    public InfinispanValueCacheImpl() {
        dataCache = ThreadCaches.currentCaches().getDataCache();
    }

    private String getFqn(BasePK basePK) {
        return FQN_PREFIX +
                '/' + basePK.getComponentVendorName() +
                '/' + basePK.getEntityTypeName() +
                '/' + basePK.getEntityId();
    }

    @Override
    public void put(BaseValue baseValue) {
        var basePK = baseValue.getPrimaryKey();

        dataCache.getAdvancedCache().withFlags(Flag.IGNORE_RETURN_VALUES).put(getFqn(basePK) + "/" + VALUE, baseValue);
    }

    @Override
    public BaseValue get(BasePK basePK) {
        return (BaseValue)dataCache.get(getFqn(basePK) + "/" + VALUE);
    }

    @Override
    public void remove(BasePK basePK) {
        dataCache.getAdvancedCache().withFlags(Flag.IGNORE_RETURN_VALUES).remove(getFqn(basePK) + "/" + VALUE);
    }

}

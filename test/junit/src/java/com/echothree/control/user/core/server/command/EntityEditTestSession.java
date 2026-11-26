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

import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.valuecache.NullValueCacheImpl;
import com.echothree.util.server.persistence.valuecache.ValueCacheProvider;
import com.echothree.util.server.persistence.valuecache.ValueCacheProviderImpl;

class EntityEditTestSession extends Session {

    @Override
    public long getStartTime() {
        return 1000L;
    }

    static EntityEditTestSession create() {
        try {
            var holder = Class.forName(ValueCacheProviderImpl.class.getName() + "$ValueCacheProviderHolder");
            var field = holder.getDeclaredField("instance");
            field.setAccessible(true);
            var previous = field.get(null);
            try {
                field.set(null, (ValueCacheProvider)NullValueCacheImpl::new);
                return new EntityEditTestSession();
            } finally {
                field.set(null, previous);
            }
        } catch(ReflectiveOperationException exception) {
            throw new LinkageError(exception.getMessage(), exception);
        }
    }

}

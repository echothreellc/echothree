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

import com.echothree.util.server.cdi.CommandScope;
import javax.annotation.Resource;
import org.infinispan.Cache;

@CommandScope
public class Caches {

    @Resource(lookup = "java:app/infinispan/echothree/security")
    Cache<String, Object> securityCache;

    @Resource(lookup = "java:app/infinispan/echothree/data")
    Cache<String, Object> dataCache;

    /** Creates a new instance of Caches */
    public Caches() {
    }

    public Cache<String, Object> getSecurityCache() {
        return securityCache;
    }

    public Cache<String, Object> getDataCache() {
        return dataCache;
    }

}

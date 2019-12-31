// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import javax.enterprise.inject.spi.Unmanaged;
import org.infinispan.Cache;

public class Caches {

    private static Unmanaged<SecurityCacheBean> unmanagedSecurityCacheBean = new Unmanaged<>(SecurityCacheBean.class);
    private static Unmanaged<DataCacheBean> unmanagedDataCacheBean = new Unmanaged<>(DataCacheBean.class);

    private Unmanaged.UnmanagedInstance<SecurityCacheBean> securityCacheBeanInstance = null;
    private Cache<String, Object> securityCache = null;
    private Unmanaged.UnmanagedInstance<DataCacheBean> dataCacheBeanInstance = null;
    private Cache<String, Object> dataCache = null;

    /** Creates a new instance of Caches */
    public Caches() {
    }

    public Cache<String, Object> getSecurityCache() {
        if(securityCache == null) {
            securityCacheBeanInstance = unmanagedSecurityCacheBean.newInstance();
            SecurityCacheBean securityCacheBean = securityCacheBeanInstance.produce().inject().postConstruct().get();
            securityCache = securityCacheBean.getCache();
        }

        return securityCache;
    }

    public Cache<String, Object> getDataCache() {
        if(dataCache == null) {
            dataCacheBeanInstance = unmanagedDataCacheBean.newInstance();
            DataCacheBean dataCacheBean = dataCacheBeanInstance.produce().inject().postConstruct().get();
            dataCache = dataCacheBean.getCache();
        }

        return dataCache;
    }

    @SuppressWarnings("Finally")
    public void close() {
        if(securityCacheBeanInstance != null) {
            securityCacheBeanInstance.preDestroy().dispose();
            securityCacheBeanInstance = null;
            securityCache = null;
        }

        if(dataCacheBeanInstance != null) {
            dataCacheBeanInstance.preDestroy().dispose();
            dataCacheBeanInstance = null;
            dataCache = null;
        }
    }
}

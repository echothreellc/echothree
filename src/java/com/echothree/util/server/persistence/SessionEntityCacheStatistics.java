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

package com.echothree.util.server.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionEntityCacheStatistics {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    
    public long readOnlyUpgradedToReadWrite;
    public long putReadOnlyEntity;
    public long putReadWriteEntityToParent;
    public long putReadWriteEntity;
    public long gotEntityFromReadWrite;
    public long gotEntityFromReadOnly;
    public long gotEntityFromParent;
    public long entityNotGotten;
    public long finalReadWriteCount;
    public long finalReadOnlyCount;
    
    public long getCacheHits() {
        return gotEntityFromReadWrite + gotEntityFromReadOnly + gotEntityFromParent;
    }
    
    public long getCacheAttempts() {
        return getCacheHits() + entityNotGotten;
    }
    
    public void Add(SessionEntityCacheStatistics sessionEntityCacheStatistics) {
        this.readOnlyUpgradedToReadWrite += sessionEntityCacheStatistics.readOnlyUpgradedToReadWrite;
        this.putReadOnlyEntity += sessionEntityCacheStatistics.putReadOnlyEntity;
        this.putReadWriteEntityToParent += sessionEntityCacheStatistics.putReadWriteEntityToParent;
        this.putReadWriteEntity += sessionEntityCacheStatistics.putReadWriteEntity;
        this.gotEntityFromReadWrite += sessionEntityCacheStatistics.gotEntityFromReadWrite;
        this.gotEntityFromReadOnly += sessionEntityCacheStatistics.gotEntityFromReadOnly;
        this.gotEntityFromParent += sessionEntityCacheStatistics.gotEntityFromParent;
        this.entityNotGotten += sessionEntityCacheStatistics.entityNotGotten;
        this.finalReadWriteCount += sessionEntityCacheStatistics.finalReadWriteCount;
        this.finalReadOnlyCount += sessionEntityCacheStatistics.finalReadOnlyCount;
    }

    public void dumpStats() {
        log.info("ReadOnlyUpgradedToReadWrite = " + readOnlyUpgradedToReadWrite);
        log.info("PutReadOnlyEntity = " + putReadOnlyEntity);
        log.info("PutReadWriteEntityToParent = " + putReadWriteEntityToParent);
        log.info("PutReadWriteEntity = " + putReadWriteEntity);
        log.info("GotEntityFromReadWrite = " + gotEntityFromReadWrite);
        log.info("GotEntityFromReadOnly = " + gotEntityFromReadOnly);
        log.info("GotEntityFromParent = " + gotEntityFromParent);
        log.info("EntityNotGotten = " + entityNotGotten);
        log.info("CacheHits = " + getCacheHits());
        log.info("CacheAttempts = " + getCacheAttempts());
        log.info("FinalReadWriteCount = " + finalReadWriteCount);
        log.info("FinalReadOnlyCount = " + finalReadOnlyCount);
    }
    
}

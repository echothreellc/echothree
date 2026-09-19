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

package com.echothree.model.control.core.server.control;

import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.transfer.CacheEntryDependencyTransfer;
import com.echothree.model.control.core.common.transfer.CacheEntryTransfer;
import com.echothree.model.control.core.server.transfer.CacheEntryDependencyTransferCache;
import com.echothree.model.control.core.server.transfer.CacheEntryTransferCache;
import com.echothree.model.data.core.common.pk.CacheEntryPK;
import com.echothree.model.data.core.server.entity.CacheBlobEntry;
import com.echothree.model.data.core.server.entity.CacheClobEntry;
import com.echothree.model.data.core.server.entity.CacheEntry;
import com.echothree.model.data.core.server.entity.CacheEntryDependency;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.factory.CacheBlobEntryFactory;
import com.echothree.model.data.core.server.factory.CacheClobEntryFactory;
import com.echothree.model.data.core.server.factory.CacheEntryDependencyFactory;
import com.echothree.model.data.core.server.factory.CacheEntryFactory;
import com.echothree.model.data.core.server.value.CacheEntryDependencyValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.CACHE_ENTRY_DEPENDENCIES_CACHE_ENTRY_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.CACHE_ENTRY_DEPENDENCIES_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.COMPONENT_VENDORS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_INSTANCES_ENTITY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK;
import static com.echothree.model.jooq.server.tables.core.CacheBlobEntries.CacheBlobEntries;
import static com.echothree.model.jooq.server.tables.core.CacheClobEntries.CacheClobEntries;
import static com.echothree.model.jooq.server.tables.core.CacheEntries.CacheEntries;
import static com.echothree.model.jooq.server.tables.core.CacheEntryDependencies.CacheEntryDependencies;
import static com.echothree.model.jooq.server.tables.core.ComponentVendorDetails.ComponentVendorDetails;
import static com.echothree.model.jooq.server.tables.core.ComponentVendors.ComponentVendors;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.core.EntityTypeDetails.EntityTypeDetails;
import static com.echothree.model.jooq.server.tables.core.EntityTypes.EntityTypes;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;

@CommandScope
public class CacheEntryControl
        extends BaseModelControl {

    /** Creates a new instance of CacheEntryControl */

    protected CacheEntryControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Cache Entries
    // --------------------------------------------------------------------------------

    @Inject
    protected CacheEntryFactory cacheEntryFactory;

    @Inject
    CacheEntryTransferCache cacheEntryTransferCache;

    public CacheEntry createCacheEntry(String cacheEntryKey, MimeType mimeType, Long createdTime, Long validUntilTime, String clob, ByteArray blob,
            Set<String> entityRefs) {
        var cacheEntry = createCacheEntry(cacheEntryKey, mimeType, createdTime, validUntilTime);
        var entityAttributeTypeName = mimeType.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();

        if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
            createCacheClobEntry(cacheEntry, clob);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
            createCacheBlobEntry(cacheEntry, blob);
        }

        if(entityRefs != null) {
            createCacheEntryDependencies(cacheEntry, entityRefs);
        }

        return cacheEntry;
    }

    public CacheEntry createCacheEntry(String cacheEntryKey, MimeType mimeType, Long createdTime, Long validUntilTime) {
        return cacheEntryFactory.create(cacheEntryKey, mimeType, createdTime, validUntilTime);
    }

    public long countCacheEntries() {
        return session.getDslContext()
                .selectCount()
                .from(CacheEntries)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private CacheEntry getCacheEntryByCacheEntryKey(String cacheEntryKey, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CacheEntries.fields())
                .from(CacheEntries)
                .where(CacheEntries.CACHE_ENTRY_KEY.eq(cacheEntryKey));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return cacheEntryFactory.getEntityFromQuery(entityPermission, query);
    }

    public CacheEntry getCacheEntryByCacheEntryKey(String cacheEntryKey) {
        return getCacheEntryByCacheEntryKey(cacheEntryKey, EntityPermission.READ_ONLY);
    }

    public CacheEntry getCacheEntryByCacheEntryKeyForUpdate(String cacheEntryKey) {
        return getCacheEntryByCacheEntryKey(cacheEntryKey, EntityPermission.READ_WRITE);
    }

    private List<CacheEntry> getCacheEntries(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CacheEntries.fields())
                .from(CacheEntries);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(CacheEntries.CACHE_ENTRY_KEY),
                    CacheEntryFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return cacheEntryFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<CacheEntry> getCacheEntries() {
        return getCacheEntries(EntityPermission.READ_ONLY);
    }

    public List<CacheEntry> getCacheEntriesForUpdate() {
        return getCacheEntries(EntityPermission.READ_WRITE);
    }

    public CacheEntryTransfer getCacheEntryTransfer(UserVisit userVisit, CacheEntry cacheEntry) {
        return cacheEntryTransferCache.getCacheEntryTransfer(userVisit, cacheEntry);
    }

    public CacheEntryTransfer getCacheEntryTransferByCacheEntryKey(UserVisit userVisit, String cacheEntryKey) {
        var cacheEntry = getCacheEntryByCacheEntryKey(cacheEntryKey);
        CacheEntryTransfer cacheEntryTransfer = null;

        if(cacheEntry != null) {
            var validUntilTime = cacheEntry.getValidUntilTime();

            if(validUntilTime != null && validUntilTime < session.getStartTime()) {
                removeCacheEntry(cacheEntry);
            } else {
                cacheEntryTransfer = getCacheEntryTransfer(userVisit, cacheEntry);
            }
        }

        return cacheEntryTransfer;
    }

    public List<CacheEntryTransfer> getCacheEntryTransfers(UserVisit userVisit, Collection<CacheEntry> cacheEntries) {
        List<CacheEntryTransfer> cacheEntryTransfers = new ArrayList<>(cacheEntries.size());

        cacheEntries.forEach((cacheEntry) ->
                cacheEntryTransfers.add(cacheEntryTransferCache.getCacheEntryTransfer(userVisit, cacheEntry))
        );

        return cacheEntryTransfers;
    }

    public List<CacheEntryTransfer> getCacheEntryTransfers(UserVisit userVisit) {
        return getCacheEntryTransfers(userVisit, getCacheEntries());
    }

    public void removeCacheEntry(CacheEntry cacheEntry) {
        cacheEntry.remove();
    }

    public void removeCacheEntries(List<CacheEntry> cacheEntries) {
        cacheEntries.forEach(this::removeCacheEntry);
    }

    public void removeCacheEntries() {
        removeCacheEntries(getCacheEntriesForUpdate());
    }

    private List<CacheEntryPK> getCacheEntryPKsByEntityInstance(final EntityInstance entityInstance) {
        return session.getDslContext()
                .select(CacheEntries.CACHE_ENTRY)
                .from(CacheEntries)
                .join(CacheEntryDependencies).onKey(CACHE_ENTRY_DEPENDENCIES_CACHE_ENTRY_FK)
                .where(CacheEntryDependencies.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                .fetch(CacheEntries.CACHE_ENTRY);
    }

    public void removeCacheEntriesByEntityInstance(final EntityInstance entityInstance) {
        cacheEntryFactory.remove(getCacheEntryPKsByEntityInstance(entityInstance));
    }

    // --------------------------------------------------------------------------------
    //   Cache Clob Entries
    // --------------------------------------------------------------------------------

    @Inject
    protected CacheClobEntryFactory cacheClobEntryFactory;

    public CacheClobEntry createCacheClobEntry(CacheEntry cacheEntry, String clob) {
        return cacheClobEntryFactory.create(cacheEntry, clob);
    }

    private CacheClobEntry getCacheClobEntryByCacheEntry(CacheEntry cacheEntry, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CacheClobEntries.fields())
                .from(CacheClobEntries)
                .where(CacheClobEntries.CACHE_ENTRY.eq(cacheEntry.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return cacheClobEntryFactory.getEntityFromQuery(entityPermission, query);
    }

    public CacheClobEntry getCacheClobEntryByCacheEntry(CacheEntry cacheEntry) {
        return getCacheClobEntryByCacheEntry(cacheEntry, EntityPermission.READ_ONLY);
    }

    public CacheClobEntry getCacheClobEntryByCacheEntryForUpdate(CacheEntry cacheEntry) {
        return getCacheClobEntryByCacheEntry(cacheEntry, EntityPermission.READ_WRITE);
    }

    // --------------------------------------------------------------------------------
    //   Cache Blob Entries
    // --------------------------------------------------------------------------------

    @Inject
    protected CacheBlobEntryFactory cacheBlobEntryFactory;

    public CacheBlobEntry createCacheBlobEntry(CacheEntry cacheEntry, ByteArray blob) {
        return cacheBlobEntryFactory.create(cacheEntry, blob);
    }

    private CacheBlobEntry getCacheBlobEntryByCacheEntry(CacheEntry cacheEntry, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CacheBlobEntries.fields())
                .from(CacheBlobEntries)
                .where(CacheBlobEntries.CACHE_ENTRY.eq(cacheEntry.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return cacheBlobEntryFactory.getEntityFromQuery(entityPermission, query);
    }

    public CacheBlobEntry getCacheBlobEntryByCacheEntry(CacheEntry cacheEntry) {
        return getCacheBlobEntryByCacheEntry(cacheEntry, EntityPermission.READ_ONLY);
    }

    public CacheBlobEntry getCacheBlobEntryByCacheEntryForUpdate(CacheEntry cacheEntry) {
        return getCacheBlobEntryByCacheEntry(cacheEntry, EntityPermission.READ_WRITE);
    }

    // --------------------------------------------------------------------------------
    //   Cache Entry Dependencies
    // --------------------------------------------------------------------------------

    @Inject
    protected CacheEntryDependencyFactory cacheEntryDependencyFactory;

    @Inject
    CacheEntryDependencyTransferCache cacheEntryDependencyTransferCache;

    public void createCacheEntryDependencies(CacheEntry cacheEntry, Set<String> entityRefs) {
        List<CacheEntryDependencyValue> cacheEntryDependencyValues = new ArrayList<>(entityRefs.size());

        for(var entityRef : entityRefs) {
            var entityInstance = entityInstanceControl.getEntityInstanceByEntityRef(entityRef);

            if(entityInstance != null) {
                cacheEntryDependencyValues.add(new CacheEntryDependencyValue(cacheEntry.getPrimaryKey(), entityInstance.getPrimaryKey()));
            }
        }

        cacheEntryDependencyFactory.create(cacheEntryDependencyValues);
    }

    public long countCacheEntryDependenciesByCacheEntry(final CacheEntry cacheEntry) {
        return session.getDslContext()
                .selectCount()
                .from(CacheEntryDependencies)
                .where(CacheEntryDependencies.CACHE_ENTRY.eq(cacheEntry.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCacheEntryDependenciesByEntityInstance(final EntityInstance entityInstance) {
        return session.getDslContext()
                .selectCount()
                .from(CacheEntryDependencies)
                .where(CacheEntryDependencies.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public CacheEntryDependency createCacheEntryDependency(CacheEntry cacheEntry, EntityInstance entityInstance) {
        return cacheEntryDependencyFactory.create(cacheEntry, entityInstance);
    }

    private List<CacheEntryDependency> getCacheEntryDependenciesByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CacheEntryDependencies.fields())
                    .from(CacheEntryDependencies)
                    .join(CacheEntries).onKey(CACHE_ENTRY_DEPENDENCIES_CACHE_ENTRY_FK)
                    .where(CacheEntryDependencies.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                    .orderBy(CacheEntries.CACHE_ENTRY_KEY),
                    CacheEntryDependencyFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CacheEntryDependencies.fields())
                    .from(CacheEntryDependencies)
                    .where(CacheEntryDependencies.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                    .forUpdate();
        };

        return cacheEntryDependencyFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<CacheEntryDependency> getCacheEntryDependenciesByEntityInstance(EntityInstance entityInstance) {
        return getCacheEntryDependenciesByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public List<CacheEntryDependency> getCacheEntryDependenciesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getCacheEntryDependenciesByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private List<CacheEntryDependency> getCacheEntryDependenciesByCacheEntry(CacheEntry cacheEntry, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CacheEntryDependencies.fields())
                    .from(CacheEntryDependencies)
                    .join(EntityInstances).onKey(CACHE_ENTRY_DEPENDENCIES_ENTITY_INSTANCE_FK)
                    .join(EntityTypes).onKey(ENTITY_INSTANCES_ENTITY_TYPE_FK)
                    .join(EntityTypeDetails).onKey(ENTITY_TYPES_LAST_DETAIL_FK)
                    .join(ComponentVendors).onKey(ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK)
                    .join(ComponentVendorDetails).onKey(COMPONENT_VENDORS_LAST_DETAIL_FK)
                    .where(CacheEntryDependencies.CACHE_ENTRY.eq(cacheEntry.getPrimaryKey()))
                    .orderBy(ComponentVendorDetails.COMPONENT_VENDOR_NAME, EntityTypeDetails.SORT_ORDER, EntityTypeDetails.ENTITY_TYPE_NAME, EntityInstances.ENTITY_UNIQUE),
                    CacheEntryDependencyFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CacheEntryDependencies.fields())
                    .from(CacheEntryDependencies)
                    .where(CacheEntryDependencies.CACHE_ENTRY.eq(cacheEntry.getPrimaryKey()))
                    .forUpdate();
        };

        return cacheEntryDependencyFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<CacheEntryDependency> getCacheEntryDependenciesByCacheEntry(CacheEntry cacheEntry) {
        return getCacheEntryDependenciesByCacheEntry(cacheEntry, EntityPermission.READ_ONLY);
    }

    public List<CacheEntryDependency> getCacheEntryDependenciesByCacheEntryForUpdate(CacheEntry cacheEntry) {
        return getCacheEntryDependenciesByCacheEntry(cacheEntry, EntityPermission.READ_WRITE);
    }

    public CacheEntryDependencyTransfer getCacheEntryDependencyTransfer(UserVisit userVisit, CacheEntryDependency cacheEntryDependency) {
        return cacheEntryDependencyTransferCache.getCacheEntryDependencyTransfer(userVisit, cacheEntryDependency);
    }

    public List<CacheEntryDependencyTransfer> getCacheEntryDependencyTransfers(UserVisit userVisit, Collection<CacheEntryDependency> cacheEntries) {
        List<CacheEntryDependencyTransfer> cacheEntryDependencyTransfers = new ArrayList<>(cacheEntries.size());

        cacheEntries.forEach((cacheEntryDependency) ->
                cacheEntryDependencyTransfers.add(cacheEntryDependencyTransferCache.getCacheEntryDependencyTransfer(userVisit, cacheEntryDependency))
        );

        return cacheEntryDependencyTransfers;
    }

    public List<CacheEntryDependencyTransfer> getCacheEntryDependencyTransfersByCacheEntry(UserVisit userVisit, CacheEntry cacheEntry) {
        return getCacheEntryDependencyTransfers(userVisit, getCacheEntryDependenciesByCacheEntry(cacheEntry));
    }

}

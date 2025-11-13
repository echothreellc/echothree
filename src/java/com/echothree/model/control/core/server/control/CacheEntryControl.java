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

package com.echothree.model.control.core.server.control;

import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.transfer.CacheEntryDependencyTransfer;
import com.echothree.model.control.core.common.transfer.CacheEntryTransfer;
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
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CacheEntryControl
        extends BaseCoreControl {

    /** Creates a new instance of CacheEntryControl */
    protected CacheEntryControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Cache Entries
    // --------------------------------------------------------------------------------

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
        return CacheEntryFactory.getInstance().create(cacheEntryKey, mimeType, createdTime, validUntilTime);
    }

    public long countCacheEntries() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM cacheentries");
    }

    private static final Map<EntityPermission, String> getCacheEntryByCacheEntryKeyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM cacheentries " +
                        "WHERE cent_cacheentrykey = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM cacheentries " +
                        "WHERE cent_cacheentrykey = ? " +
                        "FOR UPDATE");
        getCacheEntryByCacheEntryKeyQueries = Collections.unmodifiableMap(queryMap);
    }

    private CacheEntry getCacheEntryByCacheEntryKey(String cacheEntryKey, EntityPermission entityPermission) {
        return CacheEntryFactory.getInstance().getEntityFromQuery(entityPermission, getCacheEntryByCacheEntryKeyQueries,
                cacheEntryKey);
    }

    public CacheEntry getCacheEntryByCacheEntryKey(String cacheEntryKey) {
        return getCacheEntryByCacheEntryKey(cacheEntryKey, EntityPermission.READ_ONLY);
    }

    public CacheEntry getCacheEntryByCacheEntryKeyForUpdate(String cacheEntryKey) {
        return getCacheEntryByCacheEntryKey(cacheEntryKey, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getCacheEntriesByCacheEntryKeyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM cacheentries " +
                        "ORDER BY cent_cacheentrykey " +
                        "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM cacheentries " +
                        "FOR UPDATE");
        getCacheEntriesByCacheEntryKeyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CacheEntry> getCacheEntries(EntityPermission entityPermission) {
        return CacheEntryFactory.getInstance().getEntitiesFromQuery(entityPermission, getCacheEntriesByCacheEntryKeyQueries);
    }

    public List<CacheEntry> getCacheEntries() {
        return getCacheEntries(EntityPermission.READ_ONLY);
    }

    public List<CacheEntry> getCacheEntriesForUpdate() {
        return getCacheEntries(EntityPermission.READ_WRITE);
    }

    public CacheEntryTransfer getCacheEntryTransfer(UserVisit userVisit, CacheEntry cacheEntry) {
        return getCoreTransferCaches().getCacheEntryTransferCache().getCacheEntryTransfer(userVisit, cacheEntry);
    }

    public CacheEntryTransfer getCacheEntryTransferByCacheEntryKey(UserVisit userVisit, String cacheEntryKey) {
        var cacheEntry = getCacheEntryByCacheEntryKey(cacheEntryKey);
        CacheEntryTransfer cacheEntryTransfer = null;

        if(cacheEntry != null) {
            var validUntilTime = cacheEntry.getValidUntilTime();

            if(validUntilTime != null && validUntilTime < session.START_TIME) {
                removeCacheEntry(cacheEntry);
            } else {
                cacheEntryTransfer = getCacheEntryTransfer(userVisit, cacheEntry);
            }
        }

        return cacheEntryTransfer;
    }

    public List<CacheEntryTransfer> getCacheEntryTransfers(UserVisit userVisit, Collection<CacheEntry> cacheEntries) {
        List<CacheEntryTransfer> cacheEntryTransfers = new ArrayList<>(cacheEntries.size());
        var cacheEntryTransferCache = getCoreTransferCaches().getCacheEntryTransferCache();

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
        final var instance = CacheEntryFactory.getInstance();

        return instance.getPKsFromQueryAsList(
                instance.prepareStatement(
                        "SELECT _PK_ "
                                + "FROM cacheentrydependencies, cacheentries "
                                + "WHERE centd_eni_entityinstanceid = ? "
                                + "AND centd_cent_cacheentryid = cent_cacheentryid"),
                entityInstance);
    }

    public void removeCacheEntriesByEntityInstance(final EntityInstance entityInstance) {
        CacheEntryFactory.getInstance().remove(getCacheEntryPKsByEntityInstance(entityInstance));
    }

    // --------------------------------------------------------------------------------
    //   Cache Clob Entries
    // --------------------------------------------------------------------------------

    public CacheClobEntry createCacheClobEntry(CacheEntry cacheEntry, String clob) {
        return CacheClobEntryFactory.getInstance().create(cacheEntry, clob);
    }

    private static final Map<EntityPermission, String> getCacheClobEntryByCacheEntryQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM cacheclobentries " +
                        "WHERE ccent_cent_cacheentryid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM cacheclobentries " +
                        "WHERE ccent_cent_cacheentryid = ? " +
                        "FOR UPDATE");
        getCacheClobEntryByCacheEntryQueries = Collections.unmodifiableMap(queryMap);
    }

    private CacheClobEntry getCacheClobEntryByCacheEntry(CacheEntry cacheEntry, EntityPermission entityPermission) {
        return CacheClobEntryFactory.getInstance().getEntityFromQuery(entityPermission, getCacheClobEntryByCacheEntryQueries,
                cacheEntry);
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

    public CacheBlobEntry createCacheBlobEntry(CacheEntry cacheEntry, ByteArray blob) {
        return CacheBlobEntryFactory.getInstance().create(cacheEntry, blob);
    }

    private static final Map<EntityPermission, String> getCacheBlobEntryByCacheEntryQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM cacheblobentries " +
                        "WHERE ccent_cent_cacheentryid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM cacheblobentries " +
                        "WHERE ccent_cent_cacheentryid = ? " +
                        "FOR UPDATE");
        getCacheBlobEntryByCacheEntryQueries = Collections.unmodifiableMap(queryMap);
    }

    private CacheBlobEntry getCacheBlobEntryByCacheEntry(CacheEntry cacheEntry, EntityPermission entityPermission) {
        return CacheBlobEntryFactory.getInstance().getEntityFromQuery(entityPermission, getCacheBlobEntryByCacheEntryQueries,
                cacheEntry);
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

    public void createCacheEntryDependencies(CacheEntry cacheEntry, Set<String> entityRefs) {
        List<CacheEntryDependencyValue> cacheEntryDependencyValues = new ArrayList<>(entityRefs.size());

        for(var entityRef : entityRefs) {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var entityInstance = entityInstanceControl.getEntityInstanceByEntityRef(entityRef);

            if(entityInstance != null) {
                cacheEntryDependencyValues.add(new CacheEntryDependencyValue(cacheEntry.getPrimaryKey(), entityInstance.getPrimaryKey()));
            }
        }

        CacheEntryDependencyFactory.getInstance().create(cacheEntryDependencyValues);
    }

    public CacheEntryDependency createCacheEntryDependency(CacheEntry cacheEntry, EntityInstance entityInstance) {
        return CacheEntryDependencyFactory.getInstance().create(cacheEntry, entityInstance);
    }

    private static final Map<EntityPermission, String> getCacheEntryDependenciesByEntityInstanceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM cacheentrydependencies, cacheentries "
                        + "WHERE centd_eni_entityinstanceid = ? "
                        + "AND centd_cent_cacheentryid = cent_cacheentryid "
                        + "ORDER BY cent_cacheentrykey");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM cacheentrydependencies "
                        + "WHERE centd_eni_entityinstanceid = ? "
                        + "FOR UPDATE");
        getCacheEntryDependenciesByEntityInstanceQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CacheEntryDependency> getCacheEntryDependenciesByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        return CacheEntryDependencyFactory.getInstance().getEntitiesFromQuery(entityPermission, getCacheEntryDependenciesByEntityInstanceQueries,
                entityInstance);
    }

    public List<CacheEntryDependency> getCacheEntryDependenciesByEntityInstance(EntityInstance entityInstance) {
        return getCacheEntryDependenciesByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public List<CacheEntryDependency> getCacheEntryDependenciesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getCacheEntryDependenciesByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getCacheEntryDependenciesByCacheEntryQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM cacheentrydependencies, entityinstances, entitytypes, entitytypedetails, componentvendors, componentvendordetails "
                        + "WHERE centd_cent_cacheentryid = ? "
                        + "AND centd_eni_entityinstanceid = eni_entityinstanceid "
                        + "AND eni_ent_entitytypeid = ent_entitytypeid AND ent_lastdetailid = entdt_entitytypedetailid "
                        + "AND entdt_cvnd_componentvendorid = cvnd_componentvendorid AND cvnd_lastdetailid = cvndd_componentvendordetailid "
                        + "ORDER BY cvndd_componentvendorname, entdt_sortorder, entdt_entitytypename, eni_entityuniqueid");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM cacheentrydependencies "
                        + "WHERE centd_cent_cacheentryid = ? "
                        + "FOR UPDATE");
        getCacheEntryDependenciesByCacheEntryQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CacheEntryDependency> getCacheEntryDependenciesByCacheEntry(CacheEntry cacheEntry, EntityPermission entityPermission) {
        return CacheEntryDependencyFactory.getInstance().getEntitiesFromQuery(entityPermission, getCacheEntryDependenciesByCacheEntryQueries,
                cacheEntry);
    }

    public List<CacheEntryDependency> getCacheEntryDependenciesByCacheEntry(CacheEntry cacheEntry) {
        return getCacheEntryDependenciesByCacheEntry(cacheEntry, EntityPermission.READ_ONLY);
    }

    public List<CacheEntryDependency> getCacheEntryDependenciesByCacheEntryForUpdate(CacheEntry cacheEntry) {
        return getCacheEntryDependenciesByCacheEntry(cacheEntry, EntityPermission.READ_WRITE);
    }

    public CacheEntryDependencyTransfer getCacheEntryDependencyTransfer(UserVisit userVisit, CacheEntryDependency cacheEntryDependency) {
        return getCoreTransferCaches().getCacheEntryDependencyTransferCache().getCacheEntryDependencyTransfer(userVisit, cacheEntryDependency);
    }

    public List<CacheEntryDependencyTransfer> getCacheEntryDependencyTransfers(UserVisit userVisit, Collection<CacheEntryDependency> cacheEntries) {
        List<CacheEntryDependencyTransfer> cacheEntryDependencyTransfers = new ArrayList<>(cacheEntries.size());
        var cacheEntryDependencyTransferCache = getCoreTransferCaches().getCacheEntryDependencyTransferCache();

        cacheEntries.forEach((cacheEntryDependency) ->
                cacheEntryDependencyTransfers.add(cacheEntryDependencyTransferCache.getCacheEntryDependencyTransfer(userVisit, cacheEntryDependency))
        );

        return cacheEntryDependencyTransfers;
    }

    public List<CacheEntryDependencyTransfer> getCacheEntryDependencyTransfersByCacheEntry(UserVisit userVisit, CacheEntry cacheEntry) {
        return getCacheEntryDependencyTransfers(userVisit, getCacheEntryDependenciesByCacheEntry(cacheEntry));
    }

}

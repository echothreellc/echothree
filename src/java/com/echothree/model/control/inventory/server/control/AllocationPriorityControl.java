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

package com.echothree.model.control.inventory.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.inventory.common.choice.AllocationPriorityChoicesBean;
import com.echothree.model.control.inventory.common.transfer.AllocationPriorityDescriptionTransfer;
import com.echothree.model.control.inventory.common.transfer.AllocationPriorityTransfer;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.common.pk.AllocationPriorityPK;
import com.echothree.model.data.inventory.server.entity.AllocationPriority;
import com.echothree.model.data.inventory.server.entity.AllocationPriorityDescription;
import com.echothree.model.data.inventory.server.factory.AllocationPriorityDescriptionFactory;
import com.echothree.model.data.inventory.server.factory.AllocationPriorityDetailFactory;
import com.echothree.model.data.inventory.server.factory.AllocationPriorityFactory;
import com.echothree.model.data.inventory.server.value.AllocationPriorityDescriptionValue;
import com.echothree.model.data.inventory.server.value.AllocationPriorityDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.ALLOCATION_PRIORITIES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.inventory.AllocationPriorities.AllocationPriorities;
import static com.echothree.model.jooq.server.tables.inventory.AllocationPriorityDetails.AllocationPriorityDetails;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.inject.Inject;

@CommandScope
public class AllocationPriorityControl
        extends BaseInventoryControl {

    /** Creates a new instance of AllocationPriorityControl */
    protected AllocationPriorityControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Allocation Priorities
    // --------------------------------------------------------------------------------

    @Inject
    protected AllocationPriorityFactory allocationPriorityFactory;

    @Inject
    protected AllocationPriorityDetailFactory allocationPriorityDetailFactory;

    public AllocationPriority createAllocationPriority(String allocationPriorityName, Integer priority, Boolean isDefault, Integer sortPriority,
            BasePK createdBy) {
        var defaultAllocationPriority = getDefaultAllocationPriority();
        var defaultFound = defaultAllocationPriority != null;

        if(defaultFound && isDefault) {
            var defaultAllocationPriorityDetailValue = getDefaultAllocationPriorityDetailValueForUpdate();

            defaultAllocationPriorityDetailValue.setIsDefault(false);
            updateAllocationPriorityFromValue(defaultAllocationPriorityDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var allocationPriority = allocationPriorityFactory.create();
        var allocationPriorityDetail = allocationPriorityDetailFactory.create(allocationPriority, allocationPriorityName,
                priority, isDefault, sortPriority, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        allocationPriority = allocationPriorityFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                allocationPriority.getPrimaryKey());
        allocationPriority.setActiveDetail(allocationPriorityDetail);
        allocationPriority.setLastDetail(allocationPriorityDetail);
        allocationPriority.store();

        sendEvent(allocationPriority.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return allocationPriority;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.AllocationPriority */
    public AllocationPriority getAllocationPriorityByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new AllocationPriorityPK(entityInstance.getEntityUniqueId());

        return allocationPriorityFactory.getEntityFromPK(entityPermission, pk);
    }

    public AllocationPriority getAllocationPriorityByEntityInstance(final EntityInstance entityInstance) {
        return getAllocationPriorityByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public AllocationPriority getAllocationPriorityByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getAllocationPriorityByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countAllocationPriorities() {
        return session.getDslContext().selectCount()
                .from(AllocationPriorities)
                .join(AllocationPriorityDetails)
                .onKey(ALLOCATION_PRIORITIES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public AllocationPriority getAllocationPriorityByName(final String allocationPriorityName,
            final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(AllocationPriorities.fields())
                .from(AllocationPriorities)
                .join(AllocationPriorityDetails)
                .onKey(ALLOCATION_PRIORITIES_ACTIVE_DETAIL_FK)
                .where(AllocationPriorityDetails.AllocationPriorityName.eq(allocationPriorityName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return allocationPriorityFactory.getEntityFromQuery(entityPermission,
                allocationPriorityFactory.prepareStatement(query.getSQL()), query.getBindValues().toArray());
    }

    public AllocationPriority getAllocationPriorityByName(String allocationPriorityName) {
        return getAllocationPriorityByName(allocationPriorityName, EntityPermission.READ_ONLY);
    }

    public AllocationPriority getAllocationPriorityByNameForUpdate(String allocationPriorityName) {
        return getAllocationPriorityByName(allocationPriorityName, EntityPermission.READ_WRITE);
    }

    public AllocationPriorityDetailValue getAllocationPriorityDetailValueForUpdate(AllocationPriority allocationPriority) {
        return allocationPriority == null? null: allocationPriority.getLastDetailForUpdate().getAllocationPriorityDetailValue().clone();
    }

    public AllocationPriorityDetailValue getAllocationPriorityDetailValueByNameForUpdate(String allocationPriorityName) {
        return getAllocationPriorityDetailValueForUpdate(getAllocationPriorityByNameForUpdate(allocationPriorityName));
    }

    public AllocationPriority getDefaultAllocationPriority(final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(AllocationPriorities.fields())
                .from(AllocationPriorities)
                .join(AllocationPriorityDetails)
                .onKey(ALLOCATION_PRIORITIES_ACTIVE_DETAIL_FK)
                .where(AllocationPriorityDetails.IsDefault.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return allocationPriorityFactory.getEntityFromQuery(entityPermission,
                allocationPriorityFactory.prepareStatement(query.getSQL()), query.getBindValues().toArray());
    }

    public AllocationPriority getDefaultAllocationPriority() {
        return getDefaultAllocationPriority(EntityPermission.READ_ONLY);
    }

    public AllocationPriority getDefaultAllocationPriorityForUpdate() {
        return getDefaultAllocationPriority(EntityPermission.READ_WRITE);
    }

    public AllocationPriorityDetailValue getDefaultAllocationPriorityDetailValueForUpdate() {
        return getDefaultAllocationPriorityForUpdate().getLastDetailForUpdate().getAllocationPriorityDetailValue().clone();
    }

    private List<AllocationPriority> getAllocationPriorities(final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(AllocationPriorities.fields())
                .from(AllocationPriorities)
                .join(AllocationPriorityDetails)
                .onKey(ALLOCATION_PRIORITIES_ACTIVE_DETAIL_FK);

        var sql = switch(entityPermission) {
            case READ_ONLY -> baseQuery
                    .orderBy(AllocationPriorityDetails.SortOrder, AllocationPriorityDetails.AllocationPriorityName)
                    .getSQL() + " _LIMIT_";
            case READ_WRITE -> baseQuery
                    .forUpdate()
                    .getSQL();
        };

        return allocationPriorityFactory.getEntitiesFromQuery(entityPermission,
                allocationPriorityFactory.prepareStatement(sql));
    }

    public List<AllocationPriority> getAllocationPriorities() {
        return getAllocationPriorities(EntityPermission.READ_ONLY);
    }

    public List<AllocationPriority> getAllocationPrioritiesForUpdate() {
        return getAllocationPriorities(EntityPermission.READ_WRITE);
    }

    public AllocationPriorityTransfer getAllocationPriorityTransfer(UserVisit userVisit, AllocationPriority allocationPriority) {
        return allocationPriorityTransferCache.getTransfer(userVisit, allocationPriority);
    }

    public List<AllocationPriorityTransfer> getAllocationPriorityTransfers(UserVisit userVisit, Collection<AllocationPriority> allocationPriorities) {
        var allocationPriorityTransfers = new ArrayList<AllocationPriorityTransfer>(allocationPriorities.size());

        allocationPriorities.forEach((allocationPriority) ->
                allocationPriorityTransfers.add(allocationPriorityTransferCache.getTransfer(userVisit, allocationPriority))
        );

        return allocationPriorityTransfers;
    }

    public List<AllocationPriorityTransfer> getAllocationPriorityTransfers(UserVisit userVisit) {
        return getAllocationPriorityTransfers(userVisit, getAllocationPriorities());
    }

    public AllocationPriorityChoicesBean getAllocationPriorityChoices(String defaultAllocationPriorityChoice, Language language, boolean allowNullChoice) {
        var allocationPriorities = getAllocationPriorities();
        var size = allocationPriorities.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultAllocationPriorityChoice == null) {
                defaultValue = "";
            }
        }

        for(var allocationPriority : allocationPriorities) {
            var allocationPriorityDetail = allocationPriority.getLastDetail();

            var label = getBestAllocationPriorityDescription(allocationPriority, language);
            var value = allocationPriorityDetail.getAllocationPriorityName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultAllocationPriorityChoice != null && defaultAllocationPriorityChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && allocationPriorityDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new AllocationPriorityChoicesBean(labels, values, defaultValue);
    }

    private void updateAllocationPriorityFromValue(AllocationPriorityDetailValue allocationPriorityDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(allocationPriorityDetailValue.hasBeenModified()) {
            var allocationPriority = allocationPriorityFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     allocationPriorityDetailValue.getAllocationPriorityPK());
            var allocationPriorityDetail = allocationPriority.getActiveDetailForUpdate();

            allocationPriorityDetail.setThruTime(session.getStartTime());
            allocationPriorityDetail.store();

            var allocationPriorityPK = allocationPriorityDetail.getAllocationPriorityPK(); // Not updated
            var allocationPriorityName = allocationPriorityDetailValue.getAllocationPriorityName();
            var priority = allocationPriorityDetailValue.getPriority();
            var isDefault = allocationPriorityDetailValue.getIsDefault();
            var sortOrder = allocationPriorityDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultAllocationPriority = getDefaultAllocationPriority();
                var defaultFound = defaultAllocationPriority != null && !defaultAllocationPriority.equals(allocationPriority);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultAllocationPriorityDetailValue = getDefaultAllocationPriorityDetailValueForUpdate();

                    defaultAllocationPriorityDetailValue.setIsDefault(false);
                    updateAllocationPriorityFromValue(defaultAllocationPriorityDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            allocationPriorityDetail = allocationPriorityDetailFactory.create(allocationPriorityPK, allocationPriorityName, priority, isDefault,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);

            allocationPriority.setActiveDetail(allocationPriorityDetail);
            allocationPriority.setLastDetail(allocationPriorityDetail);

            sendEvent(allocationPriorityPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateAllocationPriorityFromValue(AllocationPriorityDetailValue allocationPriorityDetailValue, BasePK updatedBy) {
        updateAllocationPriorityFromValue(allocationPriorityDetailValue, true, updatedBy);
    }

    public void deleteAllocationPriority(AllocationPriority allocationPriority, BasePK deletedBy) {
        deleteAllocationPriorityDescriptionsByAllocationPriority(allocationPriority, deletedBy);

        var allocationPriorityDetail = allocationPriority.getLastDetailForUpdate();
        allocationPriorityDetail.setThruTime(session.getStartTime());
        allocationPriority.setActiveDetail(null);
        allocationPriority.store();

        // Check for default, and pick one if necessary
        var defaultAllocationPriority = getDefaultAllocationPriority();
        if(defaultAllocationPriority == null) {
            var allocationPriorities = getAllocationPrioritiesForUpdate();

            if(!allocationPriorities.isEmpty()) {
                var iter = allocationPriorities.iterator();
                if(iter.hasNext()) {
                    defaultAllocationPriority = iter.next();
                }
                var allocationPriorityDetailValue = Objects.requireNonNull(defaultAllocationPriority).getLastDetailForUpdate().getAllocationPriorityDetailValue().clone();

                allocationPriorityDetailValue.setIsDefault(true);
                updateAllocationPriorityFromValue(allocationPriorityDetailValue, false, deletedBy);
            }
        }

        sendEvent(allocationPriority.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Allocation Priority Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected AllocationPriorityDescriptionFactory allocationPriorityDescriptionFactory;

    public AllocationPriorityDescription createAllocationPriorityDescription(AllocationPriority allocationPriority, Language language, String description, BasePK createdBy) {
        var allocationPriorityDescription = allocationPriorityDescriptionFactory.create(allocationPriority, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(allocationPriority.getPrimaryKey(), EventTypes.MODIFY, allocationPriorityDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return allocationPriorityDescription;
    }

    private static final Map<EntityPermission, String> getAllocationPriorityDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM allocationprioritydescriptions
                WHERE allocprd_allocpr_allocationpriorityid = ? AND allocprd_lang_languageid = ? AND allocprd_thrutime = ?
                """);
        queryMap.put(EntityPermission.READ_WRITE, """
                SELECT _ALL_
                FROM allocationprioritydescriptions
                WHERE allocprd_allocpr_allocationpriorityid = ? AND allocprd_lang_languageid = ? AND allocprd_thrutime = ?
                FOR UPDATE
                """);
        getAllocationPriorityDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private AllocationPriorityDescription getAllocationPriorityDescription(AllocationPriority allocationPriority, Language language, EntityPermission entityPermission) {
        return allocationPriorityDescriptionFactory.getEntityFromQuery(entityPermission, getAllocationPriorityDescriptionQueries,
                allocationPriority, language, Session.MAX_TIME);
    }

    public AllocationPriorityDescription getAllocationPriorityDescription(AllocationPriority allocationPriority, Language language) {
        return getAllocationPriorityDescription(allocationPriority, language, EntityPermission.READ_ONLY);
    }

    public AllocationPriorityDescription getAllocationPriorityDescriptionForUpdate(AllocationPriority allocationPriority, Language language) {
        return getAllocationPriorityDescription(allocationPriority, language, EntityPermission.READ_WRITE);
    }

    public AllocationPriorityDescriptionValue getAllocationPriorityDescriptionValue(AllocationPriorityDescription allocationPriorityDescription) {
        return allocationPriorityDescription == null? null: allocationPriorityDescription.getAllocationPriorityDescriptionValue().clone();
    }

    public AllocationPriorityDescriptionValue getAllocationPriorityDescriptionValueForUpdate(AllocationPriority allocationPriority, Language language) {
        return getAllocationPriorityDescriptionValue(getAllocationPriorityDescriptionForUpdate(allocationPriority, language));
    }

    private static final Map<EntityPermission, String> getAllocationPriorityDescriptionsByAllocationPriorityQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM allocationprioritydescriptions, languages
                WHERE allocprd_allocpr_allocationpriorityid = ? AND allocprd_thrutime = ? AND allocprd_lang_languageid = lang_languageid
                ORDER BY lang_sortallocation, lang_languageisoname
                _LIMIT_
                """);
        queryMap.put(EntityPermission.READ_WRITE, """
                SELECT _ALL_
                FROM allocationprioritydescriptions
                WHERE allocprd_allocpr_allocationpriorityid = ? AND allocprd_thrutime = ?
                FOR UPDATE
                """);
        getAllocationPriorityDescriptionsByAllocationPriorityQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<AllocationPriorityDescription> getAllocationPriorityDescriptionsByAllocationPriority(AllocationPriority allocationPriority, EntityPermission entityPermission) {
        return allocationPriorityDescriptionFactory.getEntitiesFromQuery(entityPermission, getAllocationPriorityDescriptionsByAllocationPriorityQueries,
                allocationPriority, Session.MAX_TIME);
    }

    public List<AllocationPriorityDescription> getAllocationPriorityDescriptionsByAllocationPriority(AllocationPriority allocationPriority) {
        return getAllocationPriorityDescriptionsByAllocationPriority(allocationPriority, EntityPermission.READ_ONLY);
    }

    public List<AllocationPriorityDescription> getAllocationPriorityDescriptionsByAllocationPriorityForUpdate(AllocationPriority allocationPriority) {
        return getAllocationPriorityDescriptionsByAllocationPriority(allocationPriority, EntityPermission.READ_WRITE);
    }

    public String getBestAllocationPriorityDescription(AllocationPriority allocationPriority, Language language) {
        String description;
        var allocationPriorityDescription = getAllocationPriorityDescription(allocationPriority, language);

        if(allocationPriorityDescription == null && !language.getIsDefault()) {
            allocationPriorityDescription = getAllocationPriorityDescription(allocationPriority, partyControl.getDefaultLanguage());
        }

        if(allocationPriorityDescription == null) {
            description = allocationPriority.getLastDetail().getAllocationPriorityName();
        } else {
            description = allocationPriorityDescription.getDescription();
        }

        return description;
    }

    public AllocationPriorityDescriptionTransfer getAllocationPriorityDescriptionTransfer(UserVisit userVisit, AllocationPriorityDescription allocationPriorityDescription) {
        return allocationPriorityDescriptionTransferCache.getTransfer(userVisit, allocationPriorityDescription);
    }

    public List<AllocationPriorityDescriptionTransfer> getAllocationPriorityDescriptionTransfersByAllocationPriority(UserVisit userVisit, AllocationPriority allocationPriority) {
        var allocationPriorityDescriptions = getAllocationPriorityDescriptionsByAllocationPriority(allocationPriority);
        List<AllocationPriorityDescriptionTransfer> allocationPriorityDescriptionTransfers = new ArrayList<>(allocationPriorityDescriptions.size());

        allocationPriorityDescriptions.forEach((allocationPriorityDescription) ->
                allocationPriorityDescriptionTransfers.add(allocationPriorityDescriptionTransferCache.getTransfer(userVisit, allocationPriorityDescription))
        );

        return allocationPriorityDescriptionTransfers;
    }

    public void updateAllocationPriorityDescriptionFromValue(AllocationPriorityDescriptionValue allocationPriorityDescriptionValue, BasePK updatedBy) {
        if(allocationPriorityDescriptionValue.hasBeenModified()) {
            var allocationPriorityDescription = allocationPriorityDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    allocationPriorityDescriptionValue.getPrimaryKey());

            allocationPriorityDescription.setThruTime(session.getStartTime());
            allocationPriorityDescription.store();

            var allocationPriority = allocationPriorityDescription.getAllocationPriority();
            var language = allocationPriorityDescription.getLanguage();
            var description = allocationPriorityDescriptionValue.getDescription();

            allocationPriorityDescription = allocationPriorityDescriptionFactory.create(allocationPriority, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(allocationPriority.getPrimaryKey(), EventTypes.MODIFY, allocationPriorityDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteAllocationPriorityDescription(AllocationPriorityDescription allocationPriorityDescription, BasePK deletedBy) {
        allocationPriorityDescription.setThruTime(session.getStartTime());

        sendEvent(allocationPriorityDescription.getAllocationPriorityPK(), EventTypes.MODIFY, allocationPriorityDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteAllocationPriorityDescriptionsByAllocationPriority(AllocationPriority allocationPriority, BasePK deletedBy) {
        var allocationPriorityDescriptions = getAllocationPriorityDescriptionsByAllocationPriorityForUpdate(allocationPriority);

        allocationPriorityDescriptions.forEach((allocationPriorityDescription) -> 
                deleteAllocationPriorityDescription(allocationPriorityDescription, deletedBy)
        );
    }


}

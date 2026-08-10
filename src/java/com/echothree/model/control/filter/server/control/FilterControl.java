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

package com.echothree.model.control.filter.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.filter.common.choice.FilterChoicesBean;
import com.echothree.model.control.filter.common.transfer.FilterDescriptionTransfer;
import com.echothree.model.control.filter.common.transfer.FilterTransfer;
import com.echothree.model.control.filter.server.transfer.FilterDescriptionTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.filter.common.pk.FilterPK;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.filter.server.entity.FilterAdjustment;
import com.echothree.model.data.filter.server.entity.FilterDescription;
import com.echothree.model.data.filter.server.entity.FilterType;
import com.echothree.model.data.filter.server.factory.FilterDescriptionFactory;
import com.echothree.model.data.filter.server.factory.FilterDetailFactory;
import com.echothree.model.data.filter.server.factory.FilterFactory;
import com.echothree.model.data.filter.server.value.FilterDescriptionValue;
import com.echothree.model.data.filter.server.value.FilterDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.inject.Inject;

@CommandScope
public class FilterControl
        extends BaseModelControl {
    
    /** Creates a new instance of FilterControl */
    protected FilterControl() {
        super();
    }

    @Inject
    FilterStepControl filterStepControl;
    
    @Inject
    FilterTransferCache filterTransferCache;

    @Inject
    FilterDescriptionTransferCache filterDescriptionTransferCache;

    // --------------------------------------------------------------------------------
    //   Filters
    // --------------------------------------------------------------------------------

    @Inject
    protected FilterFactory filterFactory;

    @Inject
    protected FilterDetailFactory filterDetailFactory;

    public Filter createFilter(FilterType filterType, String filterName, FilterAdjustment initialFilterAdjustment,
            Selector filterItemSelector, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultFilter = getDefaultFilter(filterType);
        var defaultFound = defaultFilter != null;
        
        if(defaultFound && isDefault) {
            var defaultFilterDetailValue = getDefaultFilterDetailValueForUpdate(filterType);
            
            defaultFilterDetailValue.setIsDefault(false);
            updateFilterFromValue(defaultFilterDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var filter = filterFactory.create();
        var filterDetail = filterDetailFactory.create(filter, filterType, filterName,
                initialFilterAdjustment, filterItemSelector, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        filter = filterFactory.getEntityFromPK(EntityPermission.READ_WRITE, filter.getPrimaryKey());
        filter.setActiveDetail(filterDetail);
        filter.setLastDetail(filterDetail);
        filter.store();
        
        sendEvent(filter.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return filter;
    }

    public long countFiltersByFilterType(FilterType filterType) {
        return session.queryForLong(
                """
                SELECT COUNT(*)
                FROM filters, filterdetails
                WHERE flt_activedetailid = fltdt_filterdetailid AND fltdt_flttyp_filtertypeid = ?
                """,
                filterType);
    }

    public long countFiltersBySelector(Selector selector) {
        return session.queryForLong(
                """
                SELECT COUNT(*)
                FROM filters, filterdetails
                WHERE flt_activedetailid = fltdt_filterdetailid AND fltdt_filteritemselectorid = ?
                """,
                selector);
    }

    public long countFiltersByFilterAdjustment(FilterAdjustment filterAdjustment) {
        return session.queryForLong(
                """
                SELECT COUNT(*)
                FROM filters, filterdetails
                WHERE flt_activedetailid = fltdt_filterdetailid AND fltdt_initialfilteradjustmentid = ?
                """,
                filterAdjustment);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Filter */
    public Filter getFilterByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new FilterPK(entityInstance.getEntityUniqueId());

        return filterFactory.getEntityFromPK(entityPermission, pk);
    }

    public Filter getFilterByEntityInstance(EntityInstance entityInstance) {
        return getFilterByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Filter getFilterByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getFilterByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private List<Filter> getFilters(FilterType filterType, EntityPermission entityPermission) {
        List<Filter> filters;
        
        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM filters, filterdetails
                        WHERE flt_activedetailid = fltdt_filterdetailid AND fltdt_flttyp_filtertypeid = ?
                        ORDER BY fltdt_sortorder, fltdt_filtername
                        _LIMIT_
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM filters, filterdetails
                        WHERE flt_activedetailid = fltdt_filterdetailid AND fltdt_flttyp_filtertypeid = ?
                        FOR UPDATE
                        """;
            }

            var ps = filterFactory.prepareStatement(query);

            ps.setLong(1, filterType.getPrimaryKey().getEntityId());
            
            filters = filterFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filters;
    }
    
    public List<Filter> getFilters(FilterType filterType) {
        return getFilters(filterType, EntityPermission.READ_ONLY);
    }
    
    public List<Filter> getFiltersForUpdate(FilterType filterType) {
        return getFilters(filterType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getFiltersByInitialFilterAdjustmentQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM filters
                JOIN filterdetails ON fltdt_filterdetailid = flt_activedetailid
                JOIN filtertypes ON flttyp_filtertypeid = fltdt_flttyp_filtertypeid
                JOIN filtertypedetails ON flttypdt_filtertypedetailid = flttyp_lastdetailid
                JOIN filterkinds ON fltk_filterkindid = flttypdt_fltk_filterkindid
                JOIN filterkinddetails ON fltkdt_filterkinddetailid = fltk_lastdetailid
                WHERE fltdt_initialfilteradjustmentid = ?
                ORDER BY fltkdt_sortorder, fltkdt_filterkindname, flttypdt_sortorder, flttypdt_filtertypename, fltdt_sortorder, fltdt_filtername
                _LIMIT_
                """);
        queryMap.put(EntityPermission.READ_WRITE, """
                SELECT _ALL_
                FROM filters
                JOIN filterdetails ON fltdt_filterdetailid = flt_activedetailid
                WHERE fltdt_initialfilteradjustmentid = ?
                FOR UPDATE
                """);
        getFiltersByInitialFilterAdjustmentQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Filter> getFiltersByInitialFilterAdjustment(FilterAdjustment initialFilterAdjustment, EntityPermission entityPermission) {
        return filterFactory.getEntitiesFromQuery(entityPermission, getFiltersByInitialFilterAdjustmentQueries,
                initialFilterAdjustment);
    }

    public List<Filter> getFiltersByInitialFilterAdjustment(FilterAdjustment initialFilterAdjustment) {
        return getFiltersByInitialFilterAdjustment(initialFilterAdjustment, EntityPermission.READ_ONLY);
    }

    public List<Filter> getFiltersByInitialFilterAdjustmentForUpdate(FilterAdjustment initialFilterAdjustment) {
        return getFiltersByInitialFilterAdjustment(initialFilterAdjustment, EntityPermission.READ_WRITE);
    }

    public Filter getDefaultFilter(FilterType filterType, EntityPermission entityPermission) {
        Filter filter;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM filters, filterdetails
                        WHERE flt_activedetailid = fltdt_filterdetailid AND fltdt_flttyp_filtertypeid = ? AND fltdt_isdefault = 1
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM filters, filterdetails
                        WHERE flt_activedetailid = fltdt_filterdetailid AND fltdt_flttyp_filtertypeid = ? AND fltdt_isdefault = 1
                        FOR UPDATE
                        """;
            }

            var ps = filterFactory.prepareStatement(query);
            
            ps.setLong(1, filterType.getPrimaryKey().getEntityId());
            
            filter = filterFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filter;
    }
    
    public Filter getDefaultFilter(FilterType filterType) {
        return getDefaultFilter(filterType, EntityPermission.READ_ONLY);
    }
    
    public Filter getDefaultFilterForUpdate(FilterType filterType) {
        return getDefaultFilter(filterType, EntityPermission.READ_WRITE);
    }
    
    public FilterDetailValue getDefaultFilterDetailValueForUpdate(FilterType filterType) {
        return getDefaultFilterForUpdate(filterType).getLastDetailForUpdate().getFilterDetailValue().clone();
    }
    
    public Filter getFilterByName(FilterType filterType, String filterName, EntityPermission entityPermission) {
        Filter filter;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM filters, filterdetails
                        WHERE flt_activedetailid = fltdt_filterdetailid AND fltdt_flttyp_filtertypeid = ? AND fltdt_filtername = ?
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM filters, filterdetails
                        WHERE flt_activedetailid = fltdt_filterdetailid AND fltdt_flttyp_filtertypeid = ? AND fltdt_filtername = ?
                        FOR UPDATE
                        """;
            }

            var ps = filterFactory.prepareStatement(query);
            
            ps.setLong(1, filterType.getPrimaryKey().getEntityId());
            ps.setString(2, filterName);
            
            filter = filterFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filter;
    }
    
    public Filter getFilterByName(FilterType filterType, String filterName) {
        return getFilterByName(filterType, filterName, EntityPermission.READ_ONLY);
    }
    
    public Filter getFilterByNameForUpdate(FilterType filterType, String filterName) {
        return getFilterByName(filterType, filterName, EntityPermission.READ_WRITE);
    }
    
    public FilterDetailValue getFilterDetailValueForUpdate(Filter filter) {
        return filter == null? null: filter.getLastDetailForUpdate().getFilterDetailValue().clone();
    }
    
    public FilterDetailValue getFilterDetailValueByNameForUpdate(FilterType filterType, String filterName) {
        return getFilterDetailValueForUpdate(getFilterByNameForUpdate(filterType, filterName));
    }
    
    public FilterChoicesBean getFilterChoices(String defaultFilterChoice, Language language, boolean allowNullChoice,
            FilterType filterType) {
        var filters = getFilters(filterType);
        var size = filters.size() + (allowNullChoice? 1: 0);
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultFilterChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var filter : filters) {
            var filterDetail = filter.getLastDetail();
            var label = getBestFilterDescription(filter, language);
            var value = filterDetail.getFilterName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultFilterChoice != null && defaultFilterChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && filterDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new FilterChoicesBean(labels, values, defaultValue);
    }
    
    public FilterTransfer getFilterTransfer(UserVisit userVisit, Filter filter) {
        return filterTransferCache.getTransfer(userVisit, filter);
    }
    
    public List<FilterTransfer> getFilterTransfers(UserVisit userVisit, Collection<Filter> filters) {
        List<FilterTransfer> filterTransfers = new ArrayList<>(filters.size());
        
        filters.forEach((filter) ->
                filterTransfers.add(filterTransferCache.getTransfer(userVisit, filter))
        );
        
        return filterTransfers;
    }
    
    public List<FilterTransfer> getFilterTransfers(UserVisit userVisit, FilterType filterType) {
        return getFilterTransfers(userVisit, getFilters(filterType));
    }
    
    private void updateFilterFromValue(FilterDetailValue filterDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(filterDetailValue.hasBeenModified()) {
            var filter = filterFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     filterDetailValue.getFilterPK());
            var filterDetail = filter.getActiveDetailForUpdate();
            
            filterDetail.setThruTime(session.getStartTime());
            filterDetail.store();

            var filterPK = filterDetail.getFilterPK();
            var filterType = filterDetail.getFilterType();
            var filterTypePK = filterType.getPrimaryKey();
            var filterName = filterDetailValue.getFilterName();
            var initialFilterAdjustmentPK = filterDetailValue.getInitialFilterAdjustmentPK();
            var filterItemSelectorPK = filterDetailValue.getFilterItemSelectorPK();
            var isDefault = filterDetailValue.getIsDefault();
            var sortOrder = filterDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultFilter = getDefaultFilter(filterType);
                var defaultFound = defaultFilter != null && !defaultFilter.equals(filter);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultFilterDetailValue = getDefaultFilterDetailValueForUpdate(filterType);
                    
                    defaultFilterDetailValue.setIsDefault(false);
                    updateFilterFromValue(defaultFilterDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            filterDetail = filterDetailFactory.create(filterPK, filterTypePK, filterName,
                    initialFilterAdjustmentPK, filterItemSelectorPK,  isDefault, sortOrder, session.getStartTime(),
                    Session.MAX_TIME);
            
            filter.setActiveDetail(filterDetail);
            filter.setLastDetail(filterDetail);
            
            sendEvent(filterPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateFilterFromValue(FilterDetailValue filterDetailValue, BasePK updatedBy) {
        updateFilterFromValue(filterDetailValue, true, updatedBy);
    }
    
    public void deleteFilter(Filter filter, BasePK deletedBy) {
        filterStepControl.deleteFilterStepsByFilter(filter, deletedBy);
        deleteFilterDescriptionsByFilter(filter, deletedBy);

        var filterDetail = filter.getLastDetailForUpdate();
        filterDetail.setThruTime(session.getStartTime());
        filter.setActiveDetail(null);
        filter.store();
        
        // Check for default, and pick one if necessary
        var filterType = filterDetail.getFilterType();
        var defaultFilter = getDefaultFilter(filterType);
        if(defaultFilter == null) {
            var Filters = getFiltersForUpdate(filterType);
            
            if(!Filters.isEmpty()) {
                var iter = Filters.iterator();
                if(iter.hasNext()) {
                    defaultFilter = iter.next();
                }
                var filterDetailValue = Objects.requireNonNull(defaultFilter).getLastDetailForUpdate().getFilterDetailValue().clone();
                
                filterDetailValue.setIsDefault(true);
                updateFilterFromValue(filterDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(filter.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteFiltersByFilterType(FilterType filterType, BasePK deletedBy) {
        var filters = getFiltersForUpdate(filterType);

        filters.forEach((filter) ->
                deleteFilter(filter, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Filter Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected FilterDescriptionFactory filterDescriptionFactory;

    public FilterDescription createFilterDescription(Filter filter, Language language, String description, BasePK createdBy) {
        var filterDescription = filterDescriptionFactory.create(filter, language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(filter.getPrimaryKey(), EventTypes.MODIFY, filterDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return filterDescription;
    }
    
    private FilterDescription getFilterDescription(Filter filter, Language language, EntityPermission entityPermission) {
        FilterDescription filterDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM filterdescriptions
                        WHERE fltd_flt_filterid = ? AND fltd_lang_languageid = ? AND fltd_thrutime = ?
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM filterdescriptions
                        WHERE fltd_flt_filterid = ? AND fltd_lang_languageid = ? AND fltd_thrutime = ?
                        FOR UPDATE
                        """;
            }

            var ps = filterDescriptionFactory.prepareStatement(query);
            
            ps.setLong(1, filter.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            filterDescription = filterDescriptionFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterDescription;
    }
    
    public FilterDescription getFilterDescription(Filter filter, Language language) {
        return getFilterDescription(filter, language, EntityPermission.READ_ONLY);
    }
    
    public FilterDescription getFilterDescriptionForUpdate(Filter filter, Language language) {
        return getFilterDescription(filter, language, EntityPermission.READ_WRITE);
    }
    
    public FilterDescriptionValue getFilterDescriptionValue(FilterDescription filterDescription) {
        return filterDescription == null? null: filterDescription.getFilterDescriptionValue().clone();
    }
    
    public FilterDescriptionValue getFilterDescriptionValueForUpdate(Filter filter, Language language) {
        var filterDescription = getFilterDescriptionForUpdate(filter, language);
        
        return filterDescription == null? null: filterDescription.getFilterDescriptionValue().clone();
    }
    
    private List<FilterDescription> getFilterDescriptions(Filter filter, EntityPermission entityPermission) {
        List<FilterDescription> filterDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM filterdescriptions, languages
                        WHERE fltd_flt_filterid = ? AND fltd_thrutime = ? AND fltd_lang_languageid = lang_languageid
                        ORDER BY lang_sortorder, lang_languageisoname
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM filterdescriptions
                        WHERE fltd_flt_filterid = ? AND fltd_thrutime = ?
                        FOR UPDATE
                        """;
            }

            var ps = filterDescriptionFactory.prepareStatement(query);
            
            ps.setLong(1, filter.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            filterDescriptions = filterDescriptionFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterDescriptions;
    }
    
    public List<FilterDescription> getFilterDescriptions(Filter filter) {
        return getFilterDescriptions(filter, EntityPermission.READ_ONLY);
    }
    
    public List<FilterDescription> getFilterDescriptionsForUpdate(Filter filter) {
        return getFilterDescriptions(filter, EntityPermission.READ_WRITE);
    }
    
    public String getBestFilterDescription(Filter filter, Language language) {
        String description;
        var filterDescription = getFilterDescription(filter, language);
        
        if(filterDescription == null && !language.getIsDefault()) {
            filterDescription = getFilterDescription(filter, partyControl.getDefaultLanguage());
        }
        
        if(filterDescription == null) {
            description = filter.getLastDetail().getFilterName();
        } else {
            description = filterDescription.getDescription();
        }
        
        return description;
    }
    
    public FilterDescriptionTransfer getFilterDescriptionTransfer(UserVisit userVisit, FilterDescription filterDescription) {
        return filterDescriptionTransferCache.getTransfer(userVisit, filterDescription);
    }
    
    public List<FilterDescriptionTransfer> getFilterDescriptionTransfers(UserVisit userVisit, Filter filter) {
        var filterDescriptions = getFilterDescriptions(filter);
        List<FilterDescriptionTransfer> filterDescriptionTransfers = new ArrayList<>(filterDescriptions.size());
        
        filterDescriptions.forEach((filterDescription) ->
                filterDescriptionTransfers.add(filterDescriptionTransferCache.getTransfer(userVisit, filterDescription))
        );
        
        return filterDescriptionTransfers;
    }
    
    public void updateFilterDescriptionFromValue(FilterDescriptionValue filterDescriptionValue, BasePK updatedBy) {
        if(filterDescriptionValue.hasBeenModified()) {
            var filterDescription = filterDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, filterDescriptionValue.getPrimaryKey());
            
            filterDescription.setThruTime(session.getStartTime());
            filterDescription.store();

            var filter = filterDescription.getFilter();
            var language = filterDescription.getLanguage();
            var description = filterDescriptionValue.getDescription();
            
            filterDescription = filterDescriptionFactory.create(filter, language, description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(filter.getPrimaryKey(), EventTypes.MODIFY, filterDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteFilterDescription(FilterDescription filterDescription, BasePK deletedBy) {
        filterDescription.setThruTime(session.getStartTime());
        
        sendEvent(filterDescription.getFilterPK(), EventTypes.MODIFY, filterDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteFilterDescriptionsByFilter(Filter filter, BasePK deletedBy) {
        var filterDescriptions = getFilterDescriptionsForUpdate(filter);
        
        filterDescriptions.forEach((filterDescription) -> 
                deleteFilterDescription(filterDescription, deletedBy)
        );
    }
    
}

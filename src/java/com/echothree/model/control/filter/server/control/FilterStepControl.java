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
import com.echothree.model.control.filter.common.choice.FilterStepChoicesBean;
import com.echothree.model.control.filter.common.transfer.FilterEntranceStepTransfer;
import com.echothree.model.control.filter.common.transfer.FilterStepDescriptionTransfer;
import com.echothree.model.control.filter.common.transfer.FilterStepDestinationTransfer;
import com.echothree.model.control.filter.common.transfer.FilterStepTransfer;
import com.echothree.model.control.filter.server.transfer.FilterEntranceStepTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterStepDescriptionTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterStepDestinationTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterStepTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.filter.common.pk.FilterStepPK;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.filter.server.entity.FilterEntranceStep;
import com.echothree.model.data.filter.server.entity.FilterStep;
import com.echothree.model.data.filter.server.entity.FilterStepDescription;
import com.echothree.model.data.filter.server.entity.FilterStepDestination;
import com.echothree.model.data.filter.server.factory.FilterEntranceStepFactory;
import com.echothree.model.data.filter.server.factory.FilterStepDescriptionFactory;
import com.echothree.model.data.filter.server.factory.FilterStepDestinationFactory;
import com.echothree.model.data.filter.server.factory.FilterStepDetailFactory;
import com.echothree.model.data.filter.server.factory.FilterStepFactory;
import com.echothree.model.data.filter.server.value.FilterStepDescriptionValue;
import com.echothree.model.data.filter.server.value.FilterStepDetailValue;
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
import java.util.List;
import javax.inject.Inject;

@CommandScope
public class FilterStepControl
        extends BaseModelControl {
    
    /** Creates a new instance of FilterStepControl */
    protected FilterStepControl() {
        super();
    }

    @Inject
    FilterStepElementControl filterStepElementControl;

    @Inject
    FilterEntranceStepTransferCache filterEntranceStepTransferCache;

    @Inject
    FilterStepDescriptionTransferCache filterStepDescriptionTransferCache;

    @Inject
    FilterStepDestinationTransferCache filterStepDestinationTransferCache;

    @Inject
    FilterStepTransferCache filterStepTransferCache;

    // --------------------------------------------------------------------------------
    //   Filter Steps
    // --------------------------------------------------------------------------------

    @Inject
    protected FilterStepFactory filterStepFactory;

    @Inject
    protected FilterStepDetailFactory filterStepDetailFactory;

    public FilterStep createFilterStep(Filter filter, String filterStepName, Selector filterItemSelector, BasePK createdBy) {
        var filterStep = filterStepFactory.create();
        var filterStepDetail = filterStepDetailFactory.create(filterStep, filter, filterStepName, filterItemSelector, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        filterStep = filterStepFactory.getEntityFromPK(EntityPermission.READ_WRITE, filterStep.getPrimaryKey());
        filterStep.setActiveDetail(filterStepDetail);
        filterStep.setLastDetail(filterStepDetail);
        filterStep.store();
        
        sendEvent(filterStep.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return filterStep;
    }

    public long countFilterStepsByFilter(Filter filter) {
        return session.queryForLong(
                """
                SELECT COUNT(*)
                FROM filtersteps, filterstepdetails
                WHERE fltstp_activedetailid = fltstpdt_filterstepdetailid AND fltstpdt_flt_filterid = ?
                """,
                filter);
    }

    public long countFilterStepsBySelector(Selector selector) {
        return session.queryForLong(
                """
                SELECT COUNT(*)
                FROM filtersteps, filterstepdetails
                WHERE fltstp_activedetailid = fltstpdt_filterstepdetailid AND fltstpdt_filteritemselectorid = ?
                """,
                selector);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.FilterStep */
    public FilterStep getFilterStepByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new FilterStepPK(entityInstance.getEntityUniqueId());

        return filterStepFactory.getEntityFromPK(entityPermission, pk);
    }

    public FilterStep getFilterStepByEntityInstance(EntityInstance entityInstance) {
        return getFilterStepByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public FilterStep getFilterStepByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getFilterStepByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public FilterStep getFilterStepByName(Filter filter, String filterStepName, EntityPermission entityPermission) {
        FilterStep filterStep;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM filtersteps, filterstepdetails
                        WHERE fltstp_activedetailid = fltstpdt_filterstepdetailid AND fltstpdt_flt_filterid = ?
                        AND fltstpdt_filterstepname = ?
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM filtersteps, filterstepdetails
                        WHERE fltstp_activedetailid = fltstpdt_filterstepdetailid AND fltstpdt_flt_filterid = ?
                        AND fltstpdt_filterstepname = ?
                        FOR UPDATE
                        """;
            }

            var ps = filterStepFactory.prepareStatement(query);
            
            ps.setLong(1, filter.getPrimaryKey().getEntityId());
            ps.setString(2, filterStepName);
            
            filterStep = filterStepFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterStep;
    }
    
    public FilterStep getFilterStepByName(Filter filter, String filterStepName) {
        return getFilterStepByName(filter, filterStepName, EntityPermission.READ_ONLY);
    }
    
    public FilterStep getFilterStepByNameForUpdate(Filter filter, String filterStepName) {
        return getFilterStepByName(filter, filterStepName, EntityPermission.READ_WRITE);
    }
    
    public FilterStepDetailValue getFilterStepDetailValueForUpdate(FilterStep filterStep) {
        return filterStep == null? null: filterStep.getLastDetailForUpdate().getFilterStepDetailValue().clone();
    }
    
    public FilterStepDetailValue getFilterStepDetailValueByNameForUpdate(Filter filter, String filterStepName) {
        return getFilterStepDetailValueForUpdate(getFilterStepByNameForUpdate(filter, filterStepName));
    }
    
    private List<FilterStep> getFilterStepsByFilter(Filter filter, EntityPermission entityPermission) {
        List<FilterStep> filterSteps;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM filtersteps, filterstepdetails
                        WHERE fltstp_activedetailid = fltstpdt_filterstepdetailid AND fltstpdt_flt_filterid = ?
                        ORDER BY fltstpdt_filterstepname
                        _LIMIT_
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM filtersteps, filterstepdetails
                        WHERE fltstp_activedetailid = fltstpdt_filterstepdetailid AND fltstpdt_flt_filterid = ?
                        FOR UPDATE
                        """;
            }

            var ps = filterStepFactory.prepareStatement(query);
            
            ps.setLong(1, filter.getPrimaryKey().getEntityId());
            
            filterSteps = filterStepFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterSteps;
    }
    
    public List<FilterStep> getFilterStepsByFilter(Filter filter) {
        return getFilterStepsByFilter(filter, EntityPermission.READ_ONLY);
    }
    
    public List<FilterStep> getFilterStepsByFilterForUpdate(Filter filter) {
        return getFilterStepsByFilter(filter, EntityPermission.READ_WRITE);
    }
    
    public FilterStepChoicesBean getFilterStepChoices(String defaultFilterStepChoice, Language language, boolean allowNullChoice,
            Filter filter) {
        var filterSteps = getFilterStepsByFilter(filter);
        var size = filterSteps.size() + (allowNullChoice? 1: 0);
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultFilterStepChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var filterStep : filterSteps) {
            var filterStepDetail = filterStep.getLastDetail();
            var label = getBestFilterStepDescription(filterStep, language);
            var value = filterStepDetail.getFilterStepName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultFilterStepChoice != null && defaultFilterStepChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null) {
                defaultValue = value;
            }
        }
        
        return new FilterStepChoicesBean(labels, values, defaultValue);
    }
    
    public List<FilterStepTransfer> getFilterStepTransfers(UserVisit userVisit, Collection<FilterStep> filterSteps) {
        List<FilterStepTransfer> filterStepTransfers = new ArrayList<>(filterSteps.size());
        
        filterSteps.forEach((filterStep) ->
                filterStepTransfers.add(filterStepTransferCache.getTransfer(userVisit, filterStep))
        );
        
        return filterStepTransfers;
    }
    
    public List<FilterStepTransfer> getFilterStepTransfersByFilter(UserVisit userVisit, Filter filter) {
        return getFilterStepTransfers(userVisit, getFilterStepsByFilter(filter));
    }
    
    public FilterStepTransfer getFilterStepTransfer(UserVisit userVisit, FilterStep filterStep) {
        return filterStepTransferCache.getTransfer(userVisit, filterStep);
    }
    
    public void updateFilterStepFromValue(FilterStepDetailValue filterStepDetailValue, BasePK updatedBy) {
        if(filterStepDetailValue.hasBeenModified()) {
            var filterStep = filterStepFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     filterStepDetailValue.getFilterStepPK());
            var filterStepDetail = filterStep.getActiveDetailForUpdate();
            
            filterStepDetail.setThruTime(session.getStartTime());
            filterStepDetail.store();

            var filterStepPK = filterStepDetail.getFilterStepPK();
            var filter = filterStepDetail.getFilter();
            var filterPK = filter.getPrimaryKey();
            var filterStepName = filterStepDetailValue.getFilterStepName();
            var filterItemSelectorPK = filterStepDetailValue.getFilterItemSelectorPK();
            
            filterStepDetail = filterStepDetailFactory.create(filterStepPK, filterPK, filterStepName,
                    filterItemSelectorPK, session.getStartTime(), Session.MAX_TIME);
            
            filterStep.setActiveDetail(filterStepDetail);
            filterStep.setLastDetail(filterStepDetail);
            
            sendEvent(filterStepPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteFilterStep(FilterStep filterStep, BasePK deletedBy) {
        filterStepElementControl.deleteFilterStepElementsByFilterStep(filterStep, deletedBy);
        deleteFilterStepDestinationsByFilterStep(filterStep, deletedBy);
        deleteFilterEntranceStepsByFilterStep(filterStep, deletedBy);
        deleteFilterStepDescriptionsByFilterStep(filterStep, deletedBy);

        var filterStepDetail = filterStep.getLastDetailForUpdate();
        filterStepDetail.setThruTime(session.getStartTime());
        filterStep.setActiveDetail(null);
        filterStep.store();
        
        sendEvent(filterStep.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteFilterStepsByFilter(Filter filter, BasePK deletedBy) {
        var filterSteps = getFilterStepsByFilterForUpdate(filter);
        
        filterSteps.forEach((filterStep) -> 
                deleteFilterStep(filterStep, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Step Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected FilterStepDescriptionFactory filterStepDescriptionFactory;

    public FilterStepDescription createFilterStepDescription(FilterStep filterStep, Language language, String description, BasePK createdBy) {
        var filterStepDescription = filterStepDescriptionFactory.create(filterStep, language,
                description, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(filterStep.getPrimaryKey(), EventTypes.MODIFY,
                filterStepDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return filterStepDescription;
    }
    
    private FilterStepDescription getFilterStepDescription(FilterStep filterStep, Language language, EntityPermission entityPermission) {
        FilterStepDescription filterStepDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM filterstepdescriptions
                        WHERE fltstpd_fltstp_filterstepid = ? AND fltstpd_lang_languageid = ? AND fltstpd_thrutime = ?
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM filterstepdescriptions
                        WHERE fltstpd_fltstp_filterstepid = ? AND fltstpd_lang_languageid = ? AND fltstpd_thrutime = ?
                        FOR UPDATE
                        """;
            }

            var ps = filterStepDescriptionFactory.prepareStatement(query);
            
            ps.setLong(1, filterStep.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            filterStepDescription = filterStepDescriptionFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterStepDescription;
    }
    
    public FilterStepDescription getFilterStepDescription(FilterStep filterStep, Language language) {
        return getFilterStepDescription(filterStep, language, EntityPermission.READ_ONLY);
    }
    
    public FilterStepDescription getFilterStepDescriptionForUpdate(FilterStep filterStep, Language language) {
        return getFilterStepDescription(filterStep, language, EntityPermission.READ_WRITE);
    }
    
    public FilterStepDescriptionValue getFilterStepDescriptionValue(FilterStepDescription filterStepDescription) {
        return filterStepDescription == null? null: filterStepDescription.getFilterStepDescriptionValue().clone();
    }
    
    public FilterStepDescriptionValue getFilterStepDescriptionValueForUpdate(FilterStep filterStep, Language language) {
        var filterStepDescription = getFilterStepDescriptionForUpdate(filterStep, language);
        
        return filterStepDescription == null? null: filterStepDescription.getFilterStepDescriptionValue().clone();
    }
    
    private List<FilterStepDescription> getFilterStepDescriptions(FilterStep filterStep, EntityPermission entityPermission) {
        List<FilterStepDescription> filterStepDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM filterstepdescriptions, languages
                        WHERE fltstpd_fltstp_filterstepid = ? AND fltstpd_thrutime = ? AND fltstpd_lang_languageid = lang_languageid
                        ORDER BY lang_sortorder, lang_languageisoname
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM filterstepdescriptions
                        WHERE fltstpd_fltstp_filterstepid = ? AND fltstpd_thrutime = ?
                        FOR UPDATE
                        """;
            }

            var ps = filterStepDescriptionFactory.prepareStatement(query);
            
            ps.setLong(1, filterStep.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            filterStepDescriptions = filterStepDescriptionFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterStepDescriptions;
    }
    
    public List<FilterStepDescription> getFilterStepDescriptions(FilterStep filterStep) {
        return getFilterStepDescriptions(filterStep, EntityPermission.READ_ONLY);
    }
    
    public List<FilterStepDescription> getFilterStepDescriptionsForUpdate(FilterStep filterStep) {
        return getFilterStepDescriptions(filterStep, EntityPermission.READ_WRITE);
    }
    
    public String getBestFilterStepDescription(FilterStep filterStep, Language language) {
        String description;
        var filterStepDescription = getFilterStepDescription(filterStep, language);
        
        if(filterStepDescription == null && !language.getIsDefault()) {
            filterStepDescription = getFilterStepDescription(filterStep, partyControl.getDefaultLanguage());
        }
        
        if(filterStepDescription == null) {
            description = filterStep.getLastDetail().getFilterStepName();
        } else {
            description = filterStepDescription.getDescription();
        }
        
        return description;
    }
    
    public FilterStepDescriptionTransfer getFilterStepDescriptionTransfer(UserVisit userVisit, FilterStepDescription filterStepDescription) {
        return filterStepDescriptionTransferCache.getTransfer(userVisit, filterStepDescription);
    }
    
    public List<FilterStepDescriptionTransfer> getFilterStepDescriptionTransfers(UserVisit userVisit, FilterStep filterStep) {
        var filterStepDescriptions = getFilterStepDescriptions(filterStep);
        List<FilterStepDescriptionTransfer> filterStepDescriptionTransfers = new ArrayList<>(filterStepDescriptions.size());
        
        filterStepDescriptions.forEach((filterStepDescription) ->
                filterStepDescriptionTransfers.add(filterStepDescriptionTransferCache.getTransfer(userVisit, filterStepDescription))
        );
        
        return filterStepDescriptionTransfers;
    }
    
    public void updateFilterStepDescriptionFromValue(FilterStepDescriptionValue filterStepDescriptionValue, BasePK updatedBy) {
        if(filterStepDescriptionValue.hasBeenModified()) {
            var filterStepDescription = filterStepDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     filterStepDescriptionValue.getPrimaryKey());
            
            filterStepDescription.setThruTime(session.getStartTime());
            filterStepDescription.store();

            var filterStep = filterStepDescription.getFilterStep();
            var language = filterStepDescription.getLanguage();
            var description = filterStepDescriptionValue.getDescription();
            
            filterStepDescription = filterStepDescriptionFactory.create(filterStep, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(filterStep.getPrimaryKey(), EventTypes.MODIFY, filterStepDescription.getPrimaryKey(),
                    EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteFilterStepDescription(FilterStepDescription filterStepDescription, BasePK deletedBy) {
        filterStepDescription.setThruTime(session.getStartTime());
        
        sendEvent(filterStepDescription.getFilterStep().getPrimaryKey(), EventTypes.MODIFY,
                filterStepDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteFilterStepDescriptionsByFilterStep(FilterStep filterStep, BasePK deletedBy) {
        var filterStepDescriptions = getFilterStepDescriptionsForUpdate(filterStep);
        
        filterStepDescriptions.forEach((filterStepDescription) -> 
                deleteFilterStepDescription(filterStepDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Entrance Steps
    // --------------------------------------------------------------------------------

    @Inject
    protected FilterEntranceStepFactory filterEntranceStepFactory;

    public FilterEntranceStep createFilterEntranceStep(Filter filter, FilterStep filterStep, BasePK createdBy) {
        var filterEntranceStep = filterEntranceStepFactory.create(filter, filterStep, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(filter.getPrimaryKey(), EventTypes.MODIFY, filterEntranceStep.getPrimaryKey(),
                EventTypes.CREATE, createdBy);
        
        return filterEntranceStep;
    }

    public long countFilterEntranceStepsByFilter(Filter filter) {
        return session.queryForLong(
                """
                SELECT COUNT(*)
                FROM filterentrancesteps
                WHERE fltens_flt_filterid = ? AND fltens_thrutime = ?
                """,
                filter, Session.MAX_TIME);
    }

    public long countFilterEntranceStepsByFilterStep(FilterStep filterStep) {
        return session.queryForLong(
                """
                SELECT COUNT(*)
                FROM filterentrancesteps
                WHERE fltens_fltstp_filterstepid = ? AND fltens_thrutime = ?
                """,
                filterStep, Session.MAX_TIME);
    }

    private FilterEntranceStep getFilterEntranceStep(Filter filter, FilterStep filterStep, EntityPermission entityPermission) {
        FilterEntranceStep filterEntranceStep;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM filterentrancesteps
                        WHERE fltens_flt_filterid = ? AND fltens_fltstp_filterstepid = ? AND fltens_thrutime = ?
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM filterentrancesteps
                        WHERE fltens_flt_filterid = ? AND fltens_fltstp_filterstepid = ? AND fltens_thrutime = ?
                        FOR UPDATE
                        """;
            }

            var ps = filterEntranceStepFactory.prepareStatement(query);
            
            ps.setLong(1, filter.getPrimaryKey().getEntityId());
            ps.setLong(2, filterStep.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            filterEntranceStep = filterEntranceStepFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterEntranceStep;
    }
    
    public FilterEntranceStep getFilterEntranceStep(Filter filter, FilterStep filterStep) {
        return getFilterEntranceStep(filter, filterStep, EntityPermission.READ_ONLY);
    }
    
    public FilterEntranceStep getFilterEntranceStepForUpdate(Filter filter, FilterStep filterStep) {
        return getFilterEntranceStep(filter, filterStep, EntityPermission.READ_WRITE);
    }
    
    private List<FilterEntranceStep> getFilterEntranceStepsByFilter(Filter filter, EntityPermission entityPermission) {
        List<FilterEntranceStep> filterEntranceSteps;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM filterentrancesteps, filterstepdetails
                        WHERE fltens_flt_filterid = ? AND fltens_thrutime = ?
                        AND fltens_fltstp_filterstepid = fltstpdt_fltstp_filterstepid AND fltstpdt_thrutime = ?
                        ORDER BY fltstpdt_filterstepname
                        _LIMIT_
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM filterentrancesteps
                        WHERE fltens_flt_filterid = ? AND fltens_thrutime = ?
                        FOR UPDATE
                        """;
            }

            var ps = filterEntranceStepFactory.prepareStatement(query);
            
            ps.setLong(1, filter.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                ps.setLong(3, Session.MAX_TIME);
            }
            
            filterEntranceSteps = filterEntranceStepFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterEntranceSteps;
    }
    
    public List<FilterEntranceStep> getFilterEntranceStepsByFilter(Filter filter) {
        return getFilterEntranceStepsByFilter(filter, EntityPermission.READ_ONLY);
    }
    
    public List<FilterEntranceStep> getFilterEntranceStepsByFilterForUpdate(Filter filter) {
        return getFilterEntranceStepsByFilter(filter, EntityPermission.READ_WRITE);
    }
    
    private List<FilterEntranceStep> getFilterEntranceStepsByFilterStep(FilterStep filterStep, EntityPermission entityPermission) {
        List<FilterEntranceStep> filterEntranceSteps;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM filterentrancesteps, filterstepdetails
                        WHERE fltens_fltstp_filterstepid = ? AND fltens_thrutime = ?
                        AND fltens_fltstp_filterstepid = fltstpdt_fltstp_filterstepid AND fltstpdt_thrutime = ?
                        ORDER BY fltstpdt_filterstepname
                        _LIMIT_
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM filterentrancesteps
                        WHERE fltens_fltstp_filterstepid = ? AND fltens_thrutime = ?
                        FOR UPDATE
                        """;
            }

            var ps = filterEntranceStepFactory.prepareStatement(query);
            
            ps.setLong(1, filterStep.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                ps.setLong(3, Session.MAX_TIME);
            }
            
            filterEntranceSteps = filterEntranceStepFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterEntranceSteps;
    }
    
    public List<FilterEntranceStep> getFilterEntranceStepsByFilterStep(FilterStep filterStep) {
        return getFilterEntranceStepsByFilterStep(filterStep, EntityPermission.READ_ONLY);
    }
    
    public List<FilterEntranceStep> getFilterEntranceStepsByFilterStepForUpdate(FilterStep filterStep) {
        return getFilterEntranceStepsByFilterStep(filterStep, EntityPermission.READ_WRITE);
    }
    
    public List<FilterEntranceStepTransfer> getFilterEntranceStepTransfers(UserVisit userVisit, Collection<FilterEntranceStep> filterEntranceSteps) {
        List<FilterEntranceStepTransfer> filterEntranceStepTransfers = new ArrayList<>(filterEntranceSteps.size());
        
        filterEntranceSteps.forEach((filterEntranceStep) ->
                filterEntranceStepTransfers.add(filterEntranceStepTransferCache.getTransfer(userVisit, filterEntranceStep))
        );
        
        return filterEntranceStepTransfers;
    }
    
    public List<FilterEntranceStepTransfer> getFilterEntranceStepTransfersByFilter(UserVisit userVisit, Filter filter) {
        return getFilterEntranceStepTransfers(userVisit, getFilterEntranceStepsByFilter(filter));
    }
    
    public FilterEntranceStepTransfer getFilterEntranceStepTransfer(UserVisit userVisit, FilterEntranceStep filterEntranceStep) {
        return filterEntranceStepTransferCache.getTransfer(userVisit, filterEntranceStep);
    }
    
    public void deleteFilterEntranceStep(FilterEntranceStep filterEntranceStep, BasePK deletedBy) {
        filterEntranceStep.setThruTime(session.getStartTime());
        
        sendEvent(filterEntranceStep.getFilter().getPrimaryKey(), EventTypes.MODIFY,
                filterEntranceStep.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteFilterEntranceStepsByFilterStep(FilterStep filterStep, BasePK deletedBy) {
        var filterEntranceSteps = getFilterEntranceStepsByFilterStepForUpdate(filterStep);
        
        filterEntranceSteps.forEach((filterEntranceStep) -> 
                deleteFilterEntranceStep(filterEntranceStep, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Step Destinations
    // --------------------------------------------------------------------------------

    @Inject
    protected FilterStepDestinationFactory filterStepDestinationFactory;

    public FilterStepDestination createFilterStepDestination(FilterStep fromFilterStep, FilterStep toFilterStep, BasePK createdBy) {
        var filterStepDestination = filterStepDestinationFactory.create(fromFilterStep,
                toFilterStep, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(fromFilterStep.getPrimaryKey(), EventTypes.MODIFY,
                filterStepDestination.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return filterStepDestination;
    }

    public long countFilterStepDestinationsByFromFilterStep(FilterStep fromFilterStep) {
        return session.queryForLong(
                """
                SELECT COUNT(*)
                FROM filterstepdestinations
                WHERE fltstpdn_fromfilterstepid = ? AND fltstpdn_thrutime = ?
                """,
                fromFilterStep, Session.MAX_TIME);
    }

    public long countFilterStepDestinationsByToFilterStep(FilterStep toFilterStep) {
        return session.queryForLong(
                """
                SELECT COUNT(*)
                FROM filterstepdestinations
                WHERE fltstpdn_tofilterstepid = ? AND fltstpdn_thrutime = ?
                """,
                toFilterStep, Session.MAX_TIME);
    }

    private FilterStepDestination getFilterStepDestination(FilterStep fromFilterStep, FilterStep toFilterStep,
            EntityPermission entityPermission) {
        FilterStepDestination filterStepDestination;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM filterstepdestinations
                        WHERE fltstpdn_fromfilterstepid = ? AND fltstpdn_tofilterstepid = ? AND fltstpdn_thrutime = ?
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM filterstepdestinations
                        WHERE fltstpdn_fromfilterstepid = ? AND fltstpdn_tofilterstepid = ? AND fltstpdn_thrutime = ?
                        FOR UPDATE
                        """;
            }

            var ps = filterStepDestinationFactory.prepareStatement(query);
            
            ps.setLong(1, fromFilterStep.getPrimaryKey().getEntityId());
            ps.setLong(2, toFilterStep.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            filterStepDestination = filterStepDestinationFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterStepDestination;
    }
    
    public FilterStepDestination getFilterStepDestination(FilterStep fromFilterStep, FilterStep toFilterStep) {
        return getFilterStepDestination(fromFilterStep, toFilterStep, EntityPermission.READ_ONLY);
    }
    
    public FilterStepDestination getFilterStepDestinationForUpdate(FilterStep fromFilterStep, FilterStep toFilterStep) {
        return getFilterStepDestination(fromFilterStep, toFilterStep, EntityPermission.READ_WRITE);
    }
    
    private List<FilterStepDestination> getFilterStepDestinationsByFromFilterStep(FilterStep fromFilterStep, EntityPermission entityPermission) {
        List<FilterStepDestination> filterStepDestinations;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM filterstepdestinations, filterstepdetails
                        WHERE fltstpdn_fromfilterstepid = ? AND fltstpdn_thrutime = ?
                        AND fltstpdn_tofilterstepid = fltstpdt_fltstp_filterstepid AND fltstpdt_thrutime = ?
                        ORDER BY fltstpdt_filterstepname
                        _LIMIT_
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM filterstepdestinations
                        WHERE fltstpdn_fromfilterstepid = ? AND fltstpdn_thrutime = ?
                        FOR UPDATE
                        """;
            }

            var ps = filterStepDestinationFactory.prepareStatement(query);
            
            ps.setLong(1, fromFilterStep.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                ps.setLong(3, Session.MAX_TIME);
            }
            
            filterStepDestinations = filterStepDestinationFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterStepDestinations;
    }
    
    public List<FilterStepDestination> getFilterStepDestinationsByFromFilterStep(FilterStep fromFilterStep) {
        return getFilterStepDestinationsByFromFilterStep(fromFilterStep, EntityPermission.READ_ONLY);
    }
    
    public List<FilterStepDestination> getFilterStepDestinationsByFromFilterStepForUpdate(FilterStep fromFilterStep) {
        return getFilterStepDestinationsByFromFilterStep(fromFilterStep, EntityPermission.READ_WRITE);
    }
    
    private List<FilterStepDestination> getFilterStepDestinationsByToFilterStep(FilterStep toFilterStep, EntityPermission entityPermission) {
        List<FilterStepDestination> filterStepDestinations;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM filterstepdestinations, filterstepdetails
                        WHERE fltstpdn_tofilterstepid = ? AND fltstpdn_thrutime = ?
                        AND fltstpdn_tofilterstepid = fltstpdt_fltstp_filterstepid AND fltstpdt_thrutime = ?
                        ORDER BY fltstpdt_filterstepname
                        _LIMIT_
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM filterstepdestinations
                        WHERE fltstpdn_tofilterstepid = ? AND fltstpdn_thrutime = ?
                        FOR UPDATE
                        """;
            }

            var ps = filterStepDestinationFactory.prepareStatement(query);
            
            ps.setLong(1, toFilterStep.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                ps.setLong(3, Session.MAX_TIME);
            }
            
            filterStepDestinations = filterStepDestinationFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterStepDestinations;
    }
    
    public List<FilterStepDestination> getFilterStepDestinationsByToFilterStep(FilterStep toFilterStep) {
        return getFilterStepDestinationsByToFilterStep(toFilterStep, EntityPermission.READ_ONLY);
    }
    
    public List<FilterStepDestination> getFilterStepDestinationsByToFilterStepForUpdate(FilterStep toFilterStep) {
        return getFilterStepDestinationsByToFilterStep(toFilterStep, EntityPermission.READ_WRITE);
    }
    
    public List<FilterStepDestinationTransfer> getFilterStepDestinationTransfers(UserVisit userVisit, Collection<FilterStepDestination> filterStepDestinations) {
        List<FilterStepDestinationTransfer> filterStepDestinationTransfers = new ArrayList<>(filterStepDestinations.size());
        
        filterStepDestinations.forEach((filterStepDestination) ->
                filterStepDestinationTransfers.add(filterStepDestinationTransferCache.getTransfer(userVisit, filterStepDestination))
        );
        
        return filterStepDestinationTransfers;
    }
    
    public List<FilterStepDestinationTransfer> getFilterStepDestinationTransfersByFromFilterStep(UserVisit userVisit,
            FilterStep fromFilterStep) {
        return getFilterStepDestinationTransfers(userVisit, getFilterStepDestinationsByFromFilterStep(fromFilterStep));
    }
    
    public List<FilterStepDestinationTransfer> getFilterStepDestinationTransfersByToFilterStep(UserVisit userVisit,
            FilterStep toFilterStep) {
        return getFilterStepDestinationTransfers(userVisit, getFilterStepDestinationsByFromFilterStep(toFilterStep));
    }
    
    public FilterStepDestinationTransfer getFilterStepDestinationTransfer(UserVisit userVisit, FilterStepDestination filterStepDestination) {
        return filterStepDestinationTransferCache.getTransfer(userVisit, filterStepDestination);
    }
    
    public void deleteFilterStepDestination(FilterStepDestination filterStepDestination, BasePK deletedBy) {
        filterStepDestination.setThruTime(session.getStartTime());
        filterStepDestination.store();
        
        sendEvent(filterStepDestination.getFromFilterStep().getPrimaryKey(),
                EventTypes.MODIFY, filterStepDestination.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteFilterStepDestinationsByFromFilterStep(FilterStep fromFilterStep, BasePK deletedBy) {
        var filterStepDestinations = getFilterStepDestinationsByFromFilterStepForUpdate(fromFilterStep);
        
        filterStepDestinations.forEach((filterStepDestination) -> 
                deleteFilterStepDestination(filterStepDestination, deletedBy)
        );
    }
    
    public void deleteFilterStepDestinationsByToFilterStep(FilterStep toFilterStep, BasePK deletedBy) {
        var filterStepDestinations = getFilterStepDestinationsByToFilterStepForUpdate(toFilterStep);
        
        filterStepDestinations.forEach((filterStepDestination) -> 
                deleteFilterStepDestination(filterStepDestination, deletedBy)
        );
    }
    
    public void deleteFilterStepDestinationsByFilterStep(FilterStep filterStep, BasePK deletedBy) {
        deleteFilterStepDestinationsByFromFilterStep(filterStep, deletedBy);
        deleteFilterStepDestinationsByToFilterStep(filterStep, deletedBy);
    }
    
}

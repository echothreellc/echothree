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
import com.echothree.model.control.filter.common.transfer.FilterStepElementDescriptionTransfer;
import com.echothree.model.control.filter.common.transfer.FilterStepElementTransfer;
import com.echothree.model.control.filter.server.transfer.FilterStepElementDescriptionTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterStepElementTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.filter.common.pk.FilterStepElementPK;
import com.echothree.model.data.filter.server.entity.FilterAdjustment;
import com.echothree.model.data.filter.server.entity.FilterStep;
import com.echothree.model.data.filter.server.entity.FilterStepElement;
import com.echothree.model.data.filter.server.entity.FilterStepElementDescription;
import com.echothree.model.data.filter.server.factory.FilterStepElementDescriptionFactory;
import com.echothree.model.data.filter.server.factory.FilterStepElementDetailFactory;
import com.echothree.model.data.filter.server.factory.FilterStepElementFactory;
import com.echothree.model.data.filter.server.value.FilterStepElementDescriptionValue;
import com.echothree.model.data.filter.server.value.FilterStepElementDetailValue;
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
import javax.inject.Inject;

@CommandScope
public class FilterStepElementControl
        extends BaseModelControl {
    
    /** Creates a new instance of FilterStepElementControl */
    protected FilterStepElementControl() {
        super();
    }

    @Inject
    FilterStepElementDescriptionTransferCache filterStepElementDescriptionTransferCache;

    @Inject
    FilterStepElementTransferCache filterStepElementTransferCache;

    // --------------------------------------------------------------------------------
    //   Filter Step Elements
    // --------------------------------------------------------------------------------

    @Inject
    protected FilterStepElementDetailFactory filterStepElementDetailFactory;

    @Inject
    protected FilterStepElementFactory filterStepElementFactory;

    public FilterStepElement createFilterStepElement(FilterStep filterStep, String filterStepElementName, Selector filterItemSelector,
            FilterAdjustment filterAdjustment, BasePK createdBy) {
        var filterStepElement = filterStepElementFactory.create();
        var filterStepElementDetail = filterStepElementDetailFactory.create(filterStepElement, filterStep,
                filterStepElementName, filterItemSelector, filterAdjustment, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        filterStepElement = filterStepElementFactory.getEntityFromPK(EntityPermission.READ_WRITE, filterStepElement.getPrimaryKey());
        filterStepElement.setActiveDetail(filterStepElementDetail);
        filterStepElement.setLastDetail(filterStepElementDetail);
        filterStepElement.store();
        
        sendEvent(filterStepElement.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return filterStepElement;
    }

    public long countFilterStepElementsByFilterStep(FilterStep filterStep) {
        return session.queryForLong(
                """
                SELECT COUNT(*)
                FROM filterstepelements, filterstepelementdetails
                WHERE fltstpe_activedetailid = fltstpedt_filterstepelementdetailid AND fltstpedt_fltstp_filterstepid = ?
                """,
                filterStep);
    }

    public long countFilterStepElementsBySelector(Selector selector) {
        return session.queryForLong(
                """
                SELECT COUNT(*)
                FROM filterstepelements, filterstepelementdetails
                WHERE fltstpe_activedetailid = fltstpedt_filterstepelementdetailid AND fltstpedt_filteritemselectorid = ?
                """,
                selector);
    }

    public long countFilterStepElementsByFilterAdjustment(FilterAdjustment filterAdjustment) {
        return session.queryForLong(
                """
                SELECT COUNT(*)
                FROM filterstepelements, filterstepelementdetails
                WHERE fltstpe_activedetailid = fltstpedt_filterstepelementdetailid AND fltstpedt_flta_filteradjustmentid = ?
                """,
                filterAdjustment);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.FilterStepElement */
    public FilterStepElement getFilterStepElementByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new FilterStepElementPK(entityInstance.getEntityUniqueId());

        return filterStepElementFactory.getEntityFromPK(entityPermission, pk);
    }

    public FilterStepElement getFilterStepElementByEntityInstance(EntityInstance entityInstance) {
        return getFilterStepElementByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public FilterStepElement getFilterStepElementByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getFilterStepElementByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public FilterStepElement getFilterStepElementByName(FilterStep filterStep, String filterStepElementName,
            EntityPermission entityPermission) {
        FilterStepElement filterStepElement;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM filterstepelements, filterstepelementdetails
                        WHERE fltstpe_activedetailid = fltstpedt_filterstepelementdetailid
                        AND fltstpedt_fltstp_filterstepid = ? AND fltstpedt_filterstepelementname = ?
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM filterstepelements, filterstepelementdetails
                        WHERE fltstpe_activedetailid = fltstpedt_filterstepelementdetailid
                        AND fltstpedt_fltstp_filterstepid = ? AND fltstpedt_filterstepelementname = ?
                        FOR UPDATE
                        """;
            }

            var ps = filterStepElementFactory.prepareStatement(query);
            
            ps.setLong(1, filterStep.getPrimaryKey().getEntityId());
            ps.setString(2, filterStepElementName);
            filterStepElement = filterStepElementFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterStepElement;
    }
    
    public FilterStepElement getFilterStepElementByName(FilterStep filterStep, String filterStepElementName) {
        return getFilterStepElementByName(filterStep, filterStepElementName, EntityPermission.READ_ONLY);
    }
    
    public FilterStepElement getFilterStepElementByNameForUpdate(FilterStep filterStep, String filterStepElementName) {
        return getFilterStepElementByName(filterStep, filterStepElementName, EntityPermission.READ_WRITE);
    }
    
    public FilterStepElementDetailValue getFilterStepElementDetailValueForUpdate(FilterStepElement filterStepElement) {
        return filterStepElement == null? null: filterStepElement.getLastDetailForUpdate().getFilterStepElementDetailValue().clone();
    }
    
    public FilterStepElementDetailValue getFilterStepElementDetailValueByNameForUpdate(FilterStep filterStep, String filterStepElementName) {
        return getFilterStepElementDetailValueForUpdate(getFilterStepElementByNameForUpdate(filterStep, filterStepElementName));
    }
    
    private List<FilterStepElement> getFilterStepElementsByFilterStep(FilterStep filterStep, EntityPermission entityPermission) {
        List<FilterStepElement> filterStepElements;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM filterstepelements, filterstepelementdetails
                        WHERE fltstpe_activedetailid = fltstpedt_filterstepelementdetailid AND fltstpedt_fltstp_filterstepid = ?
                        ORDER BY fltstpedt_filterstepelementname
                        _LIMIT_
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM filterstepelements, filterstepelementdetails
                        WHERE fltstpe_activedetailid = fltstpedt_filterstepelementdetailid AND fltstpedt_fltstp_filterstepid = ?
                        FOR UPDATE
                        """;
            }

            var ps = filterStepElementFactory.prepareStatement(query);
            
            ps.setLong(1, filterStep.getPrimaryKey().getEntityId());
            
            filterStepElements = filterStepElementFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterStepElements;
    }
    
    public List<FilterStepElement> getFilterStepElementsByFilterStep(FilterStep filterStep) {
        return getFilterStepElementsByFilterStep(filterStep, EntityPermission.READ_ONLY);
    }
    
    public List<FilterStepElement> getFilterStepElementsByFilterStepForUpdate(FilterStep filterStep) {
        return getFilterStepElementsByFilterStep(filterStep, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getFilterStepElementsByFilterAdjustmentQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM filterstepelements
                JOIN filterstepelementdetails ON fltstpedt_filterstepelementdetailid = fltstpe_activedetailid
                JOIN filtersteps ON fltstp_filterstepid = fltstpedt_fltstp_filterstepid
                JOIN filterstepdetails ON fltstpdt_filterstepdetailid = fltstp_lastdetailid
                JOIN filters ON flt_filterid = fltstpdt_flt_filterid
                JOIN filterdetails ON fltdt_filterdetailid = flt_lastdetailid
                JOIN filtertypes on flttyp_filtertypeid = fltdt_flttyp_filtertypeid
                JOIN filtertypedetails on flttypdt_filtertypedetailid = flttyp_lastdetailid
                JOIN filterkinds ON fltk_filterkindid = flttypdt_fltk_filterkindid
                JOIN filterkinddetails ON fltkdt_filterkinddetailid = fltk_lastdetailid
                WHERE fltstpedt_flta_filteradjustmentid = ?
                ORDER BY fltkdt_sortorder, fltkdt_filterkindname, flttypdt_sortorder, flttypdt_filtertypename, fltdt_sortorder, fltdt_filtername, fltstpdt_filterstepname, fltstpedt_filterstepelementname
                _LIMIT_
                """);
        queryMap.put(EntityPermission.READ_WRITE, """
                SELECT _ALL_
                FROM filterstepelements
                JOIN filterstepelementdetails ON fltstpedt_filterstepelementdetailid = fltstpe_lastdetailid
                WHERE fltstpedt_flta_filteradjustmentid = ?
                FOR UPDATE
                """);
        getFilterStepElementsByFilterAdjustmentQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<FilterStepElement> getFilterStepElementsByFilterAdjustment(FilterAdjustment filterAdjustment, EntityPermission entityPermission) {
        return filterStepElementFactory.getEntitiesFromQuery(entityPermission, getFilterStepElementsByFilterAdjustmentQueries,
                filterAdjustment);
    }

    public List<FilterStepElement> getFilterStepElementsByFilterAdjustment(FilterAdjustment filterAdjustment) {
        return getFilterStepElementsByFilterAdjustment(filterAdjustment, EntityPermission.READ_ONLY);
    }

    public List<FilterStepElement> getFilterStepElementsByFilterAdjustmentForUpdate(FilterAdjustment filterAdjustment) {
        return getFilterStepElementsByFilterAdjustment(filterAdjustment, EntityPermission.READ_WRITE);
    }

    public List<FilterStepElementTransfer> getFilterStepElementTransfers(UserVisit userVisit, Collection<FilterStepElement> filterStepElements) {
        List<FilterStepElementTransfer> filterStepElementTransfers = new ArrayList<>(filterStepElements.size());
        
        filterStepElements.forEach((filterStepElement) ->
                filterStepElementTransfers.add(filterStepElementTransferCache.getTransfer(userVisit, filterStepElement))
        );
        
        return filterStepElementTransfers;
    }
    
    public List<FilterStepElementTransfer> getFilterStepElementTransfersByFilterStep(UserVisit userVisit, FilterStep filterStep) {
        return getFilterStepElementTransfers(userVisit, getFilterStepElementsByFilterStep(filterStep));
    }
    
    public FilterStepElementTransfer getFilterStepElementTransfer(UserVisit userVisit, FilterStepElement filterStepElement) {
        return filterStepElementTransferCache.getTransfer(userVisit, filterStepElement);
    }
    
    public void updateFilterStepElementFromValue(FilterStepElementDetailValue filterStepElementDetailValue, BasePK updatedBy) {
        if(filterStepElementDetailValue.hasBeenModified()) {
            var filterStepElement = filterStepElementFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     filterStepElementDetailValue.getFilterStepElementPK());
            var filterStepElementDetail = filterStepElement.getActiveDetailForUpdate();
            
            filterStepElementDetail.setThruTime(session.getStartTime());
            filterStepElementDetail.store();

            var filterStepElementPK = filterStepElementDetail.getFilterStepElementPK();
            var filterStep = filterStepElementDetail.getFilterStep();
            var filterStepPK = filterStep.getPrimaryKey();
            var filterStepElementName = filterStepElementDetailValue.getFilterStepElementName();
            var filterItemSelectorPK = filterStepElementDetailValue.getFilterItemSelectorPK();
            var filterAdjustmentPK = filterStepElementDetailValue.getFilterAdjustmentPK();
            
            filterStepElementDetail = filterStepElementDetailFactory.create(filterStepElementPK,
                    filterStepPK, filterStepElementName, filterItemSelectorPK, filterAdjustmentPK, session.getStartTime(),
                    Session.MAX_TIME);
            
            filterStepElement.setActiveDetail(filterStepElementDetail);
            filterStepElement.setLastDetail(filterStepElementDetail);
            
            sendEvent(filterStepElementPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteFilterStepElement(FilterStepElement filterStepElement, BasePK deletedBy) {
        deleteFilterStepElementDescriptionsByFilterStepElement(filterStepElement, deletedBy);

        var filterStepElementDetail = filterStepElement.getLastDetailForUpdate();
        filterStepElementDetail.setThruTime(session.getStartTime());
        filterStepElement.setActiveDetail(null);
        filterStepElement.store();
        
        sendEvent(filterStepElement.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteFilterStepElementsByFilterStep(FilterStep filterStep, BasePK deletedBy) {
        var filterStepElements = getFilterStepElementsByFilterStepForUpdate(filterStep);
        
        filterStepElements.forEach((filterStepElement) -> 
                deleteFilterStepElement(filterStepElement, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Step Element Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected FilterStepElementDescriptionFactory filterStepElementDescriptionFactory;

    public FilterStepElementDescription createFilterStepElementDescription(FilterStepElement filterStepElement, Language language,
            String description, BasePK createdBy) {
        var filterStepElementDescription = filterStepElementDescriptionFactory.create(
                filterStepElement, language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(filterStepElement.getPrimaryKey(), EventTypes.MODIFY, filterStepElementDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return filterStepElementDescription;
    }
    
    private FilterStepElementDescription getFilterStepElementDescription(FilterStepElement filterStepElement, Language language,
            EntityPermission entityPermission) {
        FilterStepElementDescription filterStepElementDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM filterstepelementdescriptions
                        WHERE fltstped_fltstpe_filterstepelementid = ? AND fltstped_lang_languageid = ? AND fltstped_thrutime = ?
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM filterstepelementdescriptions
                        WHERE fltstped_fltstpe_filterstepelementid = ? AND fltstped_lang_languageid = ? AND fltstped_thrutime = ?
                        FOR UPDATE
                        """;
            }

            var ps = filterStepElementDescriptionFactory.prepareStatement(query);
            
            ps.setLong(1, filterStepElement.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            filterStepElementDescription = filterStepElementDescriptionFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterStepElementDescription;
    }
    
    public FilterStepElementDescription getFilterStepElementDescription(FilterStepElement filterStepElement, Language language) {
        return getFilterStepElementDescription(filterStepElement, language, EntityPermission.READ_ONLY);
    }
    
    public FilterStepElementDescription getFilterStepElementDescriptionForUpdate(FilterStepElement filterStepElement, Language language) {
        return getFilterStepElementDescription(filterStepElement, language, EntityPermission.READ_WRITE);
    }
    
    public FilterStepElementDescriptionValue getFilterStepElementDescriptionValue(FilterStepElementDescription filterStepElementDescription) {
        return filterStepElementDescription == null? null: filterStepElementDescription.getFilterStepElementDescriptionValue().clone();
    }
    
    public FilterStepElementDescriptionValue getFilterStepElementDescriptionValueForUpdate(FilterStepElement filterStepElement, Language language) {
        var filterStepElementDescription = getFilterStepElementDescriptionForUpdate(filterStepElement, language);
        
        return filterStepElementDescription == null? null: filterStepElementDescription.getFilterStepElementDescriptionValue().clone();
    }
    
    private List<FilterStepElementDescription> getFilterStepElementDescriptions(FilterStepElement filterStepElement, EntityPermission entityPermission) {
        List<FilterStepElementDescription> filterStepElementDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM filterstepelementdescriptions, languages
                        WHERE fltstped_fltstpe_filterstepelementid = ? AND fltstped_thrutime = ? AND fltstped_lang_languageid = lang_languageid
                        ORDER BY lang_sortorder, lang_languageisoname
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM filterstepelementdescriptions
                        WHERE fltstped_fltstpe_filterstepelementid = ? AND fltstped_thrutime = ?
                        FOR UPDATE
                        """;
            }

            var ps = filterStepElementDescriptionFactory.prepareStatement(query);
            
            ps.setLong(1, filterStepElement.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            filterStepElementDescriptions = filterStepElementDescriptionFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterStepElementDescriptions;
    }
    
    public List<FilterStepElementDescription> getFilterStepElementDescriptions(FilterStepElement filterStepElement) {
        return getFilterStepElementDescriptions(filterStepElement, EntityPermission.READ_ONLY);
    }
    
    public List<FilterStepElementDescription> getFilterStepElementDescriptionsForUpdate(FilterStepElement filterStepElement) {
        return getFilterStepElementDescriptions(filterStepElement, EntityPermission.READ_WRITE);
    }
    
    public String getBestFilterStepElementDescription(FilterStepElement filterStepElement, Language language) {
        String description;
        var filterStepElementDescription = getFilterStepElementDescription(filterStepElement, language);
        
        if(filterStepElementDescription == null && !language.getIsDefault()) {
            filterStepElementDescription = getFilterStepElementDescription(filterStepElement, partyControl.getDefaultLanguage());
        }
        
        if(filterStepElementDescription == null) {
            description = filterStepElement.getLastDetail().getFilterStepElementName();
        } else {
            description = filterStepElementDescription.getDescription();
        }
        
        return description;
    }
    
    public FilterStepElementDescriptionTransfer getFilterStepElementDescriptionTransfer(UserVisit userVisit, FilterStepElementDescription filterStepElementDescription) {
        return filterStepElementDescriptionTransferCache.getTransfer(userVisit, filterStepElementDescription);
    }
    
    public List<FilterStepElementDescriptionTransfer> getFilterStepElementDescriptionTransfers(UserVisit userVisit, FilterStepElement filterStepElement) {
        var filterStepElementDescriptions = getFilterStepElementDescriptions(filterStepElement);
        List<FilterStepElementDescriptionTransfer> filterStepElementDescriptionTransfers = new ArrayList<>(filterStepElementDescriptions.size());
        
        filterStepElementDescriptions.forEach((filterStepElementDescription) ->
                filterStepElementDescriptionTransfers.add(filterStepElementDescriptionTransferCache.getTransfer(userVisit, filterStepElementDescription))
        );
        
        return filterStepElementDescriptionTransfers;
    }
    
    public void updateFilterStepElementDescriptionFromValue(FilterStepElementDescriptionValue filterStepElementDescriptionValue, BasePK updatedBy) {
        if(filterStepElementDescriptionValue.hasBeenModified()) {
            var filterStepElementDescription = filterStepElementDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, filterStepElementDescriptionValue.getPrimaryKey());
            
            filterStepElementDescription.setThruTime(session.getStartTime());
            filterStepElementDescription.store();

            var filterStepElement = filterStepElementDescription.getFilterStepElement();
            var language = filterStepElementDescription.getLanguage();
            var description = filterStepElementDescriptionValue.getDescription();
            
            filterStepElementDescription = filterStepElementDescriptionFactory.create(filterStepElement,
                    language, description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(filterStepElement.getPrimaryKey(), EventTypes.MODIFY, filterStepElementDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteFilterStepElementDescription(FilterStepElementDescription filterStepElementDescription, BasePK deletedBy) {
        filterStepElementDescription.setThruTime(session.getStartTime());
        
        sendEvent(filterStepElementDescription.getFilterStepElement().getPrimaryKey(), EventTypes.MODIFY, filterStepElementDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteFilterStepElementDescriptionsByFilterStepElement(FilterStepElement filterStepElement, BasePK deletedBy) {
        var filterStepElementDescriptions = getFilterStepElementDescriptionsForUpdate(filterStepElement);
        
        filterStepElementDescriptions.forEach((filterStepElementDescription) -> 
                deleteFilterStepElementDescription(filterStepElementDescription, deletedBy)
        );
    }
    
}

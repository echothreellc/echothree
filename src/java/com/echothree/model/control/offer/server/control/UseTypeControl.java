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

package com.echothree.model.control.offer.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.offer.common.choice.UseTypeChoicesBean;
import com.echothree.model.control.offer.common.transfer.UseTypeDescriptionTransfer;
import com.echothree.model.control.offer.common.transfer.UseTypeResultTransfer;
import com.echothree.model.control.offer.common.transfer.UseTypeTransfer;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.offer.common.pk.UseTypePK;
import com.echothree.model.data.offer.server.entity.UseType;
import com.echothree.model.data.offer.server.entity.UseTypeDescription;
import com.echothree.model.data.offer.server.factory.UseTypeDescriptionFactory;
import com.echothree.model.data.offer.server.factory.UseTypeDetailFactory;
import com.echothree.model.data.offer.server.factory.UseTypeFactory;
import com.echothree.model.data.offer.server.value.UseTypeDescriptionValue;
import com.echothree.model.data.offer.server.value.UseTypeDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import com.echothree.util.server.cdi.CommandScope;

@CommandScope
public class UseTypeControl
        extends BaseOfferControl {

    /** Creates a new instance of UseTypeControl */
    protected UseTypeControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Use Types
    // --------------------------------------------------------------------------------
    
    public UseType createUseType(String useTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultUseType = getDefaultUseType();
        var defaultFound = defaultUseType != null;
        
        if(defaultFound && isDefault) {
            var defaultUseTypeDetailValue = getDefaultUseTypeDetailValueForUpdate();
            
            defaultUseTypeDetailValue.setIsDefault(false);
            updateUseTypeFromValue(defaultUseTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var useType = UseTypeFactory.getInstance().create();
        var useTypeDetail = UseTypeDetailFactory.getInstance().create(useType, useTypeName, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        useType = UseTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, useType.getPrimaryKey());
        useType.setActiveDetail(useTypeDetail);
        useType.setLastDetail(useTypeDetail);
        useType.store();
        
        sendEvent(useType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return useType;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.UseType */
    public UseType getUseTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new UseTypePK(entityInstance.getEntityUniqueId());
        var useType = UseTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);

        return useType;
    }

    public UseType getUseTypeByEntityInstance(EntityInstance entityInstance) {
        return getUseTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public UseType getUseTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getUseTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countUseTypes() {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM usetypes, usetypedetails
                WHERE usetyp_activedetailid = usetypdt_usetypedetailid
                """);
    }

    private List<UseType> getUseTypes(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ "
                    + "FROM usetypes, usetypedetails "
                    + "WHERE usetyp_activedetailid = usetypdt_usetypedetailid "
                    + "ORDER BY usetypdt_sortorder, usetypdt_usetypename "
                    + "_LIMIT_";
        } else if (entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ "
                    + "FROM usetypes, usetypedetails "
                    + "WHERE usetyp_activedetailid = usetypdt_usetypedetailid "
                    + "FOR UPDATE";
        }

        var ps = UseTypeFactory.getInstance().prepareStatement(query);
        
        return UseTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<UseType> getUseTypes() {
        return getUseTypes(EntityPermission.READ_ONLY);
    }
    
    public List<UseType> getUseTypesForUpdate() {
        return getUseTypes(EntityPermission.READ_WRITE);
    }

    public UseType getUseTypeByName(String useTypeName, EntityPermission entityPermission) {
        UseType useType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM usetypes, usetypedetails " +
                        "WHERE usetyp_activedetailid = usetypdt_usetypedetailid AND usetypdt_usetypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM usetypes, usetypedetails " +
                        "WHERE usetyp_activedetailid = usetypdt_usetypedetailid AND usetypdt_usetypename = ? " +
                        "FOR UPDATE";
            }

            var ps = UseTypeFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, useTypeName);
            
            useType = UseTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return useType;
    }
    
    public UseType getUseTypeByName(String useTypeName) {
        return getUseTypeByName(useTypeName, EntityPermission.READ_ONLY);
    }
    
    public UseType getUseTypeByNameForUpdate(String useTypeName) {
        return getUseTypeByName(useTypeName, EntityPermission.READ_WRITE);
    }
    
    public UseTypeDetailValue getUseTypeDetailValueForUpdate(UseType useType) {
        return useType == null? null: useType.getLastDetailForUpdate().getUseTypeDetailValue().clone();
    }
    
    public UseTypeDetailValue getUseTypeDetailValueByNameForUpdate(String useTypeName) {
        return getUseTypeDetailValueForUpdate(getUseTypeByNameForUpdate(useTypeName));
    }
    
    public UseType getDefaultUseType(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM usetypes, usetypedetails " +
                    "WHERE usetyp_activedetailid = usetypdt_usetypedetailid AND usetypdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM usetypes, usetypedetails " +
                    "WHERE usetyp_activedetailid = usetypdt_usetypedetailid AND usetypdt_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = UseTypeFactory.getInstance().prepareStatement(query);
        
        return UseTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public UseType getDefaultUseType() {
        return getDefaultUseType(EntityPermission.READ_ONLY);
    }
    
    public UseType getDefaultUseTypeForUpdate() {
        return getDefaultUseType(EntityPermission.READ_WRITE);
    }
    
    public UseTypeDetailValue getDefaultUseTypeDetailValueForUpdate() {
        return getDefaultUseTypeForUpdate().getLastDetailForUpdate().getUseTypeDetailValue().clone();
    }
    
    public List<UseTypeTransfer> getUseTypeTransfers(UserVisit userVisit, Collection<UseType> useTypes) {
        List<UseTypeTransfer> useTypeTransfers = new ArrayList<>(useTypes.size());
        
        useTypes.forEach((useType) -> {
            useTypeTransfers.add(useTypeTransferCache.getUseTypeTransfer(userVisit, useType));
        });
        
        return useTypeTransfers;
    }
    
    public List<UseTypeTransfer> getUseTypeTransfers(UserVisit userVisit) {
        return getUseTypeTransfers(userVisit, getUseTypes());
    }
    
    public UseTypeTransfer getUseTypeTransfer(UserVisit userVisit, UseType useType) {
        return useTypeTransferCache.getUseTypeTransfer(userVisit, useType);
    }
    
    public UseTypeChoicesBean getUseTypeChoices(String defaultUseTypeChoice, Language language, boolean allowNullChoice) {
        var useTypes = getUseTypes();
        var size = useTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultUseTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var useType : useTypes) {
            var useTypeDetail = useType.getLastDetail();
            
            var label = getBestUseTypeDescription(useType, language);
            var value = useTypeDetail.getUseTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultUseTypeChoice != null && defaultUseTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && useTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new UseTypeChoicesBean(labels, values, defaultValue);
    }
    
    private void updateUseTypeFromValue(UseTypeDetailValue useTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(useTypeDetailValue.hasBeenModified()) {
            var useType = UseTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     useTypeDetailValue.getUseTypePK());
            var useTypeDetail = useType.getActiveDetailForUpdate();
            
            useTypeDetail.setThruTime(session.START_TIME_LONG);
            useTypeDetail.store();

            var useTypePK = useTypeDetail.getUseTypePK();
            var useTypeName = useTypeDetailValue.getUseTypeName();
            var isDefault = useTypeDetailValue.getIsDefault();
            var sortOrder = useTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultUseType = getDefaultUseType();
                var defaultFound = defaultUseType != null && !defaultUseType.equals(useType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultUseTypeDetailValue = getDefaultUseTypeDetailValueForUpdate();
                    
                    defaultUseTypeDetailValue.setIsDefault(false);
                    updateUseTypeFromValue(defaultUseTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            useTypeDetail = UseTypeDetailFactory.getInstance().create(useTypePK, useTypeName, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            useType.setActiveDetail(useTypeDetail);
            useType.setLastDetail(useTypeDetail);
            
            sendEvent(useTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateUseTypeFromValue(UseTypeDetailValue useTypeDetailValue, BasePK updatedBy) {
        updateUseTypeFromValue(useTypeDetailValue, true, updatedBy);
    }
    
    public void deleteUseType(UseType useType, BasePK deletedBy) {
        var useControl = Session.getModelController(UseControl.class);

        deleteUseTypeDescriptionsByUseType(useType, deletedBy);
        useControl.deleteUsesByUseType(useType, deletedBy);

        var useTypeDetail = useType.getLastDetailForUpdate();
        useTypeDetail.setThruTime(session.START_TIME_LONG);
        useType.setActiveDetail(null);
        useType.store();
        
        // Check for default, and pick one if necessary
        var defaultUseType = getDefaultUseType();
        if(defaultUseType == null) {
            var useTypes = getUseTypesForUpdate();
            
            if(!useTypes.isEmpty()) {
                var iter = useTypes.iterator();
                if(iter.hasNext()) {
                    defaultUseType = iter.next();
                }
                var useTypeDetailValue = Objects.requireNonNull(defaultUseType).getLastDetailForUpdate().getUseTypeDetailValue().clone();
                
                useTypeDetailValue.setIsDefault(true);
                updateUseTypeFromValue(useTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(useType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    public UseTypeDescription createUseTypeDescription(UseType useType, Language language, String description, BasePK createdBy) {
        var useTypeDescription = UseTypeDescriptionFactory.getInstance().create(useType, language,
                description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(useType.getPrimaryKey(), EventTypes.MODIFY, useTypeDescription.getPrimaryKey(),
                EventTypes.CREATE, createdBy);
        
        return useTypeDescription;
    }
    
    private UseTypeDescription getUseTypeDescription(UseType useType, Language language, EntityPermission entityPermission) {
        UseTypeDescription useTypeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM usetypedescriptions " +
                        "WHERE usetypd_usetyp_usetypeid = ? AND usetypd_lang_languageid = ? AND usetypd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM usetypedescriptions " +
                        "WHERE usetypd_usetyp_usetypeid = ? AND usetypd_lang_languageid = ? AND usetypd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = UseTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, useType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            useTypeDescription = UseTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return useTypeDescription;
    }
    
    public UseTypeDescription getUseTypeDescription(UseType useType, Language language) {
        return getUseTypeDescription(useType, language, EntityPermission.READ_ONLY);
    }
    
    public UseTypeDescription getUseTypeDescriptionForUpdate(UseType useType, Language language) {
        return getUseTypeDescription(useType, language, EntityPermission.READ_WRITE);
    }
    
    public UseTypeDescriptionValue getUseTypeDescriptionValue(UseTypeDescription useTypeDescription) {
        return useTypeDescription == null? null: useTypeDescription.getUseTypeDescriptionValue().clone();
    }
    
    public UseTypeDescriptionValue getUseTypeDescriptionValueForUpdate(UseType useType, Language language) {
        return getUseTypeDescriptionValue(getUseTypeDescriptionForUpdate(useType, language));
    }
    
    private List<UseTypeDescription> getUseTypeDescriptionsByUseType(UseType useType, EntityPermission entityPermission) {
        List<UseTypeDescription> useTypeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM usetypedescriptions, languages " +
                        "WHERE usetypd_usetyp_usetypeid = ? AND usetypd_thrutime = ? AND usetypd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM usetypedescriptions " +
                        "WHERE usetypd_usetyp_usetypeid = ? AND usetypd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = UseTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, useType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            useTypeDescriptions = UseTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return useTypeDescriptions;
    }
    
    public List<UseTypeDescription> getUseTypeDescriptionsByUseType(UseType useType) {
        return getUseTypeDescriptionsByUseType(useType, EntityPermission.READ_ONLY);
    }
    
    public List<UseTypeDescription> getUseTypeDescriptionsByUseTypeForUpdate(UseType useType) {
        return getUseTypeDescriptionsByUseType(useType, EntityPermission.READ_WRITE);
    }
    
    public String getBestUseTypeDescription(UseType useType, Language language) {
        String description;
        var useTypeDescription = getUseTypeDescription(useType, language);
        
        if(useTypeDescription == null && !language.getIsDefault()) {
            useTypeDescription = getUseTypeDescription(useType, partyControl.getDefaultLanguage());
        }
        
        if(useTypeDescription == null) {
            description = useType.getLastDetail().getUseTypeName();
        } else {
            description = useTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public UseTypeDescriptionTransfer getUseTypeDescriptionTransfer(UserVisit userVisit, UseTypeDescription useTypeDescription) {
        return useTypeDescriptionTransferCache.getUseTypeDescriptionTransfer(userVisit, useTypeDescription);
    }
    
    public List<UseTypeDescriptionTransfer> getUseTypeDescriptionTransfersByUseType(UserVisit userVisit, UseType useType) {
        var useTypeDescriptions = getUseTypeDescriptionsByUseType(useType);
        List<UseTypeDescriptionTransfer> useTypeDescriptionTransfers = new ArrayList<>(useTypeDescriptions.size());
        
        useTypeDescriptions.forEach((useTypeDescription) -> {
            useTypeDescriptionTransfers.add(useTypeDescriptionTransferCache.getUseTypeDescriptionTransfer(userVisit, useTypeDescription));
        });
        
        return useTypeDescriptionTransfers;
    }
    
    public void updateUseTypeDescriptionFromValue(UseTypeDescriptionValue useTypeDescriptionValue, BasePK updatedBy) {
        if(useTypeDescriptionValue.hasBeenModified()) {
            var useTypeDescription = UseTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     useTypeDescriptionValue.getPrimaryKey());
            
            useTypeDescription.setThruTime(session.START_TIME_LONG);
            useTypeDescription.store();

            var useType = useTypeDescription.getUseType();
            var language = useTypeDescription.getLanguage();
            var description = useTypeDescriptionValue.getDescription();
            
            useTypeDescription = UseTypeDescriptionFactory.getInstance().create(useType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(useType.getPrimaryKey(), EventTypes.MODIFY, useTypeDescription.getPrimaryKey(),
                    EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteUseTypeDescription(UseTypeDescription useTypeDescription, BasePK deletedBy) {
        useTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(useTypeDescription.getUseTypePK(), EventTypes.MODIFY,
                useTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteUseTypeDescriptionsByUseType(UseType useType, BasePK deletedBy) {
        var useTypeDescriptions = getUseTypeDescriptionsByUseTypeForUpdate(useType);
        
        useTypeDescriptions.forEach((useTypeDescription) -> 
                deleteUseTypeDescription(useTypeDescription, deletedBy)
        );
    }


    // --------------------------------------------------------------------------------
    //   Use Type Searches
    // --------------------------------------------------------------------------------

    public List<UseTypeResultTransfer> getUseTypeResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var search = userVisitSearch.getSearch();
        var useTypeResultTransfers = new ArrayList<UseTypeResultTransfer>();
        var includeUseType = false;

        var options = session.getOptions();
        if(options != null) {
            includeUseType = options.contains(SearchOptions.UseTypeResultIncludeUseType);
        }

        try {
            var useTypeControl = Session.getModelController(UseTypeControl.class);
            var ps = SearchResultFactory.getInstance().prepareStatement(
                    "SELECT eni_entityuniqueid " +
                            "FROM searchresults, entityinstances " +
                            "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid " +
                            "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid " +
                            "_LIMIT_");

            ps.setLong(1, search.getPrimaryKey().getEntityId());

            try (var rs = ps.executeQuery()) {
                while(rs.next()) {
                    var useType = UseTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new UseTypePK(rs.getLong(1)));
                    var useTypeDetail = useType.getLastDetail();

                    useTypeResultTransfers.add(new UseTypeResultTransfer(useTypeDetail.getUseTypeName(),
                            includeUseType ? useTypeControl.getUseTypeTransfer(userVisit, useType) : null));
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return useTypeResultTransfers;
    }
}

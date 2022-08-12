// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.rating.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.rating.common.choice.RatingTypeListItemChoicesBean;
import com.echothree.model.control.rating.common.transfer.RatingTransfer;
import com.echothree.model.control.rating.common.transfer.RatingTypeDescriptionTransfer;
import com.echothree.model.control.rating.common.transfer.RatingTypeListItemDescriptionTransfer;
import com.echothree.model.control.rating.common.transfer.RatingTypeListItemTransfer;
import com.echothree.model.control.rating.common.transfer.RatingTypeTransfer;
import com.echothree.model.control.rating.server.transfer.RatingTransferCache;
import com.echothree.model.control.rating.server.transfer.RatingTransferCaches;
import com.echothree.model.control.rating.server.transfer.RatingTypeDescriptionTransferCache;
import com.echothree.model.control.rating.server.transfer.RatingTypeListItemDescriptionTransferCache;
import com.echothree.model.control.rating.server.transfer.RatingTypeListItemTransferCache;
import com.echothree.model.control.rating.server.transfer.RatingTypeTransferCache;
import com.echothree.model.data.core.common.pk.EntityInstancePK;
import com.echothree.model.data.core.common.pk.EntityTypePK;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.rating.common.pk.RatingPK;
import com.echothree.model.data.rating.common.pk.RatingTypeListItemPK;
import com.echothree.model.data.rating.common.pk.RatingTypePK;
import com.echothree.model.data.rating.server.entity.Rating;
import com.echothree.model.data.rating.server.entity.RatingDetail;
import com.echothree.model.data.rating.server.entity.RatingType;
import com.echothree.model.data.rating.server.entity.RatingTypeDescription;
import com.echothree.model.data.rating.server.entity.RatingTypeDetail;
import com.echothree.model.data.rating.server.entity.RatingTypeListItem;
import com.echothree.model.data.rating.server.entity.RatingTypeListItemDescription;
import com.echothree.model.data.rating.server.entity.RatingTypeListItemDetail;
import com.echothree.model.data.rating.server.factory.RatingDetailFactory;
import com.echothree.model.data.rating.server.factory.RatingFactory;
import com.echothree.model.data.rating.server.factory.RatingTypeDescriptionFactory;
import com.echothree.model.data.rating.server.factory.RatingTypeDetailFactory;
import com.echothree.model.data.rating.server.factory.RatingTypeFactory;
import com.echothree.model.data.rating.server.factory.RatingTypeListItemDescriptionFactory;
import com.echothree.model.data.rating.server.factory.RatingTypeListItemDetailFactory;
import com.echothree.model.data.rating.server.factory.RatingTypeListItemFactory;
import com.echothree.model.data.rating.server.value.RatingDetailValue;
import com.echothree.model.data.rating.server.value.RatingTypeDescriptionValue;
import com.echothree.model.data.rating.server.value.RatingTypeDetailValue;
import com.echothree.model.data.rating.server.value.RatingTypeListItemDescriptionValue;
import com.echothree.model.data.rating.server.value.RatingTypeListItemDetailValue;
import com.echothree.model.data.sequence.common.pk.SequencePK;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class RatingControl
        extends BaseModelControl {
    
    /** Creates a new instance of RatingControl */
    public RatingControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Rating Transfer Caches
    // --------------------------------------------------------------------------------
    
    private RatingTransferCaches ratingTransferCaches;
    
    public RatingTransferCaches getRatingTransferCaches(UserVisit userVisit) {
        if(ratingTransferCaches == null) {
            ratingTransferCaches = new RatingTransferCaches(userVisit, this);
        }
        
        return ratingTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Rating Types
    // --------------------------------------------------------------------------------
    
    public RatingType createRatingType(EntityType entityType, String ratingTypeName, Sequence ratingSequence, Integer sortOrder,
            BasePK createdBy) {
        RatingType ratingType = RatingTypeFactory.getInstance().create();
        RatingTypeDetail ratingTypeDetail = RatingTypeDetailFactory.getInstance().create(ratingType, entityType,
                ratingTypeName, ratingSequence, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        ratingType = RatingTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                ratingType.getPrimaryKey());
        ratingType.setActiveDetail(ratingTypeDetail);
        ratingType.setLastDetail(ratingTypeDetail);
        ratingType.store();
        
        sendEventUsingNames(ratingType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return ratingType;
    }
    
    private List<RatingType> getRatingTypes(EntityType entityType, EntityPermission entityPermission) {
        List<RatingType> ratingTypes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM ratingtypes, ratingtypedetails " +
                        "WHERE rtgtyp_activedetailid = rtgtypdt_ratingtypedetailid AND rtgtypdt_ent_entitytypeid = ? " +
                        "ORDER BY rtgtypdt_sortorder, rtgtypdt_ratingtypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM ratingtypes, ratingtypedetails " +
                        "WHERE rtgtyp_activedetailid = rtgtypdt_ratingtypedetailid AND rtgtypdt_ent_entitytypeid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = RatingTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            
            ratingTypes = RatingTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return ratingTypes;
    }
    
    public List<RatingType> getRatingTypes(EntityType entityType) {
        return getRatingTypes(entityType, EntityPermission.READ_ONLY);
    }
    
    public List<RatingType> getRatingTypesForUpdate(EntityType entityType) {
        return getRatingTypes(entityType, EntityPermission.READ_WRITE);
    }
    
    private RatingType getRatingTypeByName(EntityType entityType, String ratingTypeName, EntityPermission entityPermission) {
        RatingType ratingType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM ratingtypes, ratingtypedetails " +
                        "WHERE rtgtyp_activedetailid = rtgtypdt_ratingtypedetailid AND rtgtypdt_ent_entitytypeid = ? " +
                        "AND rtgtypdt_ratingtypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM ratingtypes, ratingtypedetails " +
                        "WHERE rtgtyp_activedetailid = rtgtypdt_ratingtypedetailid AND rtgtypdt_ent_entitytypeid = ? " +
                        "AND rtgtypdt_ratingtypename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = RatingTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setString(2, ratingTypeName);
            
            ratingType = RatingTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return ratingType;
    }
    
    public RatingType getRatingTypeByName(EntityType entityType, String ratingTypeName) {
        return getRatingTypeByName(entityType, ratingTypeName, EntityPermission.READ_ONLY);
    }
    
    public RatingType getRatingTypeByNameForUpdate(EntityType entityType, String ratingTypeName) {
        return getRatingTypeByName(entityType, ratingTypeName, EntityPermission.READ_WRITE);
    }
    
    public RatingTypeDetailValue getRatingTypeDetailValueForUpdate(RatingType ratingType) {
        return ratingType == null? null: ratingType.getLastDetailForUpdate().getRatingTypeDetailValue().clone();
    }
    
    public RatingTypeDetailValue getRatingTypeDetailValueByNameForUpdate(EntityType entityType, String ratingTypeName) {
        return getRatingTypeDetailValueForUpdate(getRatingTypeByNameForUpdate(entityType, ratingTypeName));
    }
    
    public RatingTypeTransfer getRatingTypeTransfer(UserVisit userVisit, RatingType ratingType) {
        return getRatingTransferCaches(userVisit).getRatingTypeTransferCache().getRatingTypeTransfer(ratingType);
    }
    
    public List<RatingTypeTransfer> getRatingTypeTransfers(UserVisit userVisit, EntityType entityType) {
        List<RatingType> ratingTypes = getRatingTypes(entityType);
        List<RatingTypeTransfer> ratingTypeTransfers = new ArrayList<>(ratingTypes.size());
        RatingTypeTransferCache ratingTypeTransferCache = getRatingTransferCaches(userVisit).getRatingTypeTransferCache();
        
        ratingTypes.forEach((ratingType) ->
                ratingTypeTransfers.add(ratingTypeTransferCache.getRatingTypeTransfer(ratingType))
        );
        
        return ratingTypeTransfers;
    }
    
    public void updateRatingTypeFromValue(RatingTypeDetailValue ratingTypeDetailValue, BasePK updatedBy) {
        if(ratingTypeDetailValue.hasBeenModified()) {
            RatingType ratingType = RatingTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     ratingTypeDetailValue.getRatingTypePK());
            RatingTypeDetail ratingTypeDetail = ratingType.getActiveDetailForUpdate();
            
            ratingTypeDetail.setThruTime(session.START_TIME_LONG);
            ratingTypeDetail.store();
            
            RatingTypePK ratingTypePK = ratingTypeDetail.getRatingTypePK(); // Not updated
            EntityTypePK entityTypePK = ratingTypeDetail.getEntityTypePK(); // Not updated
            String ratingTypeName = ratingTypeDetailValue.getRatingTypeName();
            SequencePK ratingSequencePK = ratingTypeDetailValue.getRatingSequencePK();
            Integer sortOrder = ratingTypeDetailValue.getSortOrder();
            
            ratingTypeDetail = RatingTypeDetailFactory.getInstance().create(ratingTypePK, entityTypePK, ratingTypeName,
                    ratingSequencePK, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            ratingType.setActiveDetail(ratingTypeDetail);
            ratingType.setLastDetail(ratingTypeDetail);
            
            sendEventUsingNames(ratingTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteRatingType(RatingType ratingType, BasePK deletedBy) {
        deleteRatingTypeDescriptionsByRatingType(ratingType, deletedBy);
        deleteRatingTypeListItemsByRatingType(ratingType, deletedBy);
        
        RatingTypeDetail ratingTypeDetail = ratingType.getLastDetailForUpdate();
        ratingTypeDetail.setThruTime(session.START_TIME_LONG);
        ratingType.setActiveDetail(null);
        ratingType.store();
        
        sendEventUsingNames(ratingType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteRatingTypes(List<RatingType> ratingTypes, BasePK deletedBy) {
        ratingTypes.forEach((ratingType) -> 
                deleteRatingType(ratingType, deletedBy)
        );
    }
    
    public void deleteRatingTypesByEntityType(EntityType entityType, BasePK deletedBy) {
        deleteRatingTypes(getRatingTypesForUpdate(entityType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Rating Type Descriptions
    // --------------------------------------------------------------------------------
    
    public RatingTypeDescription createRatingTypeDescription(RatingType ratingType, Language language, String description,
            BasePK createdBy) {
        RatingTypeDescription ratingTypeDescription = RatingTypeDescriptionFactory.getInstance().create(ratingType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(ratingType.getPrimaryKey(), EventTypes.MODIFY, ratingTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return ratingTypeDescription;
    }
    
    private RatingTypeDescription getRatingTypeDescription(RatingType ratingType, Language language, EntityPermission entityPermission) {
        RatingTypeDescription ratingTypeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM ratingtypedescriptions " +
                        "WHERE rtgtypd_rtgtyp_ratingtypeid = ? AND rtgtypd_lang_languageid = ? AND rtgtypd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM ratingtypedescriptions " +
                        "WHERE rtgtypd_rtgtyp_ratingtypeid = ? AND rtgtypd_lang_languageid = ? AND rtgtypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = RatingTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, ratingType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            ratingTypeDescription = RatingTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return ratingTypeDescription;
    }
    
    public RatingTypeDescription getRatingTypeDescription(RatingType ratingType, Language language) {
        return getRatingTypeDescription(ratingType, language, EntityPermission.READ_ONLY);
    }
    
    public RatingTypeDescription getRatingTypeDescriptionForUpdate(RatingType ratingType, Language language) {
        return getRatingTypeDescription(ratingType, language, EntityPermission.READ_WRITE);
    }
    
    public RatingTypeDescriptionValue getRatingTypeDescriptionValue(RatingTypeDescription ratingTypeDescription) {
        return ratingTypeDescription == null? null: ratingTypeDescription.getRatingTypeDescriptionValue().clone();
    }
    
    public RatingTypeDescriptionValue getRatingTypeDescriptionValueForUpdate(RatingType ratingType, Language language) {
        return getRatingTypeDescriptionValue(getRatingTypeDescriptionForUpdate(ratingType, language));
    }
    
    private List<RatingTypeDescription> getRatingTypeDescriptionsByRatingType(RatingType ratingType, EntityPermission entityPermission) {
        List<RatingTypeDescription> ratingTypeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM ratingtypedescriptions, languages " +
                        "WHERE rtgtypd_rtgtyp_ratingtypeid = ? AND rtgtypd_thrutime = ? AND rtgtypd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM ratingtypedescriptions " +
                        "WHERE rtgtypd_rtgtyp_ratingtypeid = ? AND rtgtypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = RatingTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, ratingType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            ratingTypeDescriptions = RatingTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return ratingTypeDescriptions;
    }
    
    public List<RatingTypeDescription> getRatingTypeDescriptionsByRatingType(RatingType ratingType) {
        return getRatingTypeDescriptionsByRatingType(ratingType, EntityPermission.READ_ONLY);
    }
    
    public List<RatingTypeDescription> getRatingTypeDescriptionsByRatingTypeForUpdate(RatingType ratingType) {
        return getRatingTypeDescriptionsByRatingType(ratingType, EntityPermission.READ_WRITE);
    }
    
    public String getBestRatingTypeDescription(RatingType ratingType, Language language) {
        String description;
        RatingTypeDescription ratingTypeDescription = getRatingTypeDescription(ratingType, language);
        
        if(ratingTypeDescription == null && !language.getIsDefault()) {
            ratingTypeDescription = getRatingTypeDescription(ratingType, getPartyControl().getDefaultLanguage());
        }
        
        if(ratingTypeDescription == null) {
            description = ratingType.getLastDetail().getRatingTypeName();
        } else {
            description = ratingTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public RatingTypeDescriptionTransfer getRatingTypeDescriptionTransfer(UserVisit userVisit, RatingTypeDescription ratingTypeDescription) {
        return getRatingTransferCaches(userVisit).getRatingTypeDescriptionTransferCache().getRatingTypeDescriptionTransfer(ratingTypeDescription);
    }
    
    public List<RatingTypeDescriptionTransfer> getRatingTypeDescriptionTransfers(UserVisit userVisit, RatingType ratingType) {
        List<RatingTypeDescription> ratingTypeDescriptions = getRatingTypeDescriptionsByRatingType(ratingType);
        List<RatingTypeDescriptionTransfer> ratingTypeDescriptionTransfers = new ArrayList<>(ratingTypeDescriptions.size());
        RatingTypeDescriptionTransferCache ratingTypeDescriptionTransferCache = getRatingTransferCaches(userVisit).getRatingTypeDescriptionTransferCache();
        
        ratingTypeDescriptions.forEach((ratingTypeDescription) ->
                ratingTypeDescriptionTransfers.add(ratingTypeDescriptionTransferCache.getRatingTypeDescriptionTransfer(ratingTypeDescription))
        );
        
        return ratingTypeDescriptionTransfers;
    }
    
    public void updateRatingTypeDescriptionFromValue(RatingTypeDescriptionValue ratingTypeDescriptionValue, BasePK updatedBy) {
        if(ratingTypeDescriptionValue.hasBeenModified()) {
            RatingTypeDescription ratingTypeDescription = RatingTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, ratingTypeDescriptionValue.getPrimaryKey());
            
            ratingTypeDescription.setThruTime(session.START_TIME_LONG);
            ratingTypeDescription.store();
            
            RatingType ratingType = ratingTypeDescription.getRatingType();
            Language language = ratingTypeDescription.getLanguage();
            String description = ratingTypeDescriptionValue.getDescription();
            
            ratingTypeDescription = RatingTypeDescriptionFactory.getInstance().create(ratingType, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(ratingType.getPrimaryKey(), EventTypes.MODIFY, ratingTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteRatingTypeDescription(RatingTypeDescription ratingTypeDescription, BasePK deletedBy) {
        ratingTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(ratingTypeDescription.getRatingTypePK(), EventTypes.MODIFY, ratingTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteRatingTypeDescriptionsByRatingType(RatingType ratingType, BasePK deletedBy) {
        List<RatingTypeDescription> ratingTypeDescriptions = getRatingTypeDescriptionsByRatingTypeForUpdate(ratingType);
        
        ratingTypeDescriptions.forEach((ratingTypeDescription) -> 
                deleteRatingTypeDescription(ratingTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Rating Type List Items
    // --------------------------------------------------------------------------------
    
    public RatingTypeListItem createRatingTypeListItem(RatingType ratingType, String ratingTypeListItemName, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        RatingTypeListItem defaultRatingTypeListItem = getDefaultRatingTypeListItem(ratingType);
        boolean defaultFound = defaultRatingTypeListItem != null;
        
        if(defaultFound && isDefault) {
            RatingTypeListItemDetailValue defaultRatingTypeListItemDetailValue = getDefaultRatingTypeListItemDetailValueForUpdate(ratingType);
            
            defaultRatingTypeListItemDetailValue.setIsDefault(Boolean.FALSE);
            updateRatingTypeListItemFromValue(defaultRatingTypeListItemDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        RatingTypeListItem ratingTypeListItem = RatingTypeListItemFactory.getInstance().create();
        RatingTypeListItemDetail ratingTypeListItemDetail = RatingTypeListItemDetailFactory.getInstance().create(session,
                ratingTypeListItem, ratingType, ratingTypeListItemName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        ratingTypeListItem = RatingTypeListItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                ratingTypeListItem.getPrimaryKey());
        ratingTypeListItem.setActiveDetail(ratingTypeListItemDetail);
        ratingTypeListItem.setLastDetail(ratingTypeListItemDetail);
        ratingTypeListItem.store();
        
        sendEventUsingNames(ratingTypeListItem.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return ratingTypeListItem;
    }
    
    private RatingTypeListItem getRatingTypeListItemByName(RatingType ratingType, String ratingTypeListItemName,
            EntityPermission entityPermission) {
        RatingTypeListItem ratingTypeListItem;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM ratingtypelistitems, ratingtypelistitemdetails " +
                        "WHERE rtgtypli_activedetailid = rtgtyplidt_ratingtypelistitemdetailid " +
                        "AND rtgtyplidt_rtgtyp_ratingtypeid = ? AND rtgtyplidt_ratingtypelistitemname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM ratingtypelistitems, ratingtypelistitemdetails " +
                        "WHERE rtgtypli_activedetailid = rtgtyplidt_ratingtypelistitemdetailid " +
                        "AND rtgtyplidt_rtgtyp_ratingtypeid = ? AND rtgtyplidt_ratingtypelistitemname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = RatingTypeListItemFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, ratingType.getPrimaryKey().getEntityId());
            ps.setString(2, ratingTypeListItemName);
            
            ratingTypeListItem = RatingTypeListItemFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return ratingTypeListItem;
    }
    
    public RatingTypeListItem getRatingTypeListItemByName(RatingType ratingType, String ratingTypeListItemName) {
        return getRatingTypeListItemByName(ratingType, ratingTypeListItemName, EntityPermission.READ_ONLY);
    }
    
    public RatingTypeListItem getRatingTypeListItemByNameForUpdate(RatingType ratingType, String ratingTypeListItemName) {
        return getRatingTypeListItemByName(ratingType, ratingTypeListItemName, EntityPermission.READ_WRITE);
    }
    
    public RatingTypeListItemDetailValue getRatingTypeListItemDetailValueForUpdate(RatingTypeListItem ratingTypeListItem) {
        return ratingTypeListItem == null? null: ratingTypeListItem.getLastDetailForUpdate().getRatingTypeListItemDetailValue().clone();
    }
    
    public RatingTypeListItemDetailValue getRatingTypeListItemDetailValueByNameForUpdate(RatingType ratingType, String ratingTypeListItemName) {
        return getRatingTypeListItemDetailValueForUpdate(getRatingTypeListItemByNameForUpdate(ratingType, ratingTypeListItemName));
    }
    
    private RatingTypeListItem getDefaultRatingTypeListItem(RatingType ratingType, EntityPermission entityPermission) {
        RatingTypeListItem ratingTypeListItem;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM ratingtypelistitems, ratingtypelistitemdetails " +
                        "WHERE rtgtypli_activedetailid = rtgtyplidt_ratingtypelistitemdetailid " +
                        "AND rtgtyplidt_rtgtyp_ratingtypeid = ? AND rtgtyplidt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM ratingtypelistitems, ratingtypelistitemdetails " +
                        "WHERE rtgtypli_activedetailid = rtgtyplidt_ratingtypelistitemdetailid " +
                        "AND rtgtyplidt_rtgtyp_ratingtypeid = ? AND rtgtyplidt_isdefault = 1 " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = RatingTypeListItemFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, ratingType.getPrimaryKey().getEntityId());
            
            ratingTypeListItem = RatingTypeListItemFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return ratingTypeListItem;
    }
    
    public RatingTypeListItem getDefaultRatingTypeListItem(RatingType ratingType) {
        return getDefaultRatingTypeListItem(ratingType, EntityPermission.READ_ONLY);
    }
    
    public RatingTypeListItem getDefaultRatingTypeListItemForUpdate(RatingType ratingType) {
        return getDefaultRatingTypeListItem(ratingType, EntityPermission.READ_WRITE);
    }
    
    public RatingTypeListItemDetailValue getDefaultRatingTypeListItemDetailValueForUpdate(RatingType ratingType) {
        return getDefaultRatingTypeListItemForUpdate(ratingType).getLastDetailForUpdate().getRatingTypeListItemDetailValue().clone();
    }
    
    private List<RatingTypeListItem> getRatingTypeListItems(RatingType ratingType, EntityPermission entityPermission) {
        List<RatingTypeListItem> ratingTypeListItems;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM ratingtypelistitems, ratingtypelistitemdetails " +
                        "WHERE rtgtypli_activedetailid = rtgtyplidt_ratingtypelistitemdetailid " +
                        "AND rtgtyplidt_rtgtyp_ratingtypeid = ? " +
                        "ORDER BY rtgtyplidt_sortorder, rtgtyplidt_ratingtypelistitemname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM ratingtypelistitems, ratingtypelistitemdetails " +
                        "WHERE rtgtypli_activedetailid = rtgtyplidt_ratingtypelistitemdetailid " +
                        "AND rtgtyplidt_rtgtyp_ratingtypeid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = RatingTypeListItemFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, ratingType.getPrimaryKey().getEntityId());
            
            ratingTypeListItems = RatingTypeListItemFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return ratingTypeListItems;
    }
    
    public List<RatingTypeListItem> getRatingTypeListItems(RatingType ratingType) {
        return getRatingTypeListItems(ratingType, EntityPermission.READ_ONLY);
    }
    
    public List<RatingTypeListItem> getRatingTypeListItemsForUpdate(RatingType ratingType) {
        return getRatingTypeListItems(ratingType, EntityPermission.READ_WRITE);
    }
    
    public RatingTypeListItemTransfer getRatingTypeListItemTransfer(UserVisit userVisit, RatingTypeListItem ratingTypeListItem) {
        return getRatingTransferCaches(userVisit).getRatingTypeListItemTransferCache().getRatingTypeListItemTransfer(ratingTypeListItem);
    }
    
    public List<RatingTypeListItemTransfer> getRatingTypeListItemTransfers(UserVisit userVisit, RatingType ratingType) {
        List<RatingTypeListItem> ratingTypeListItems = getRatingTypeListItems(ratingType);
        List<RatingTypeListItemTransfer> ratingTypeListItemTransfers = new ArrayList<>(ratingTypeListItems.size());
        RatingTypeListItemTransferCache ratingTypeListItemTransferCache = getRatingTransferCaches(userVisit).getRatingTypeListItemTransferCache();
        
        ratingTypeListItems.forEach((ratingTypeListItem) ->
                ratingTypeListItemTransfers.add(ratingTypeListItemTransferCache.getRatingTypeListItemTransfer(ratingTypeListItem))
        );
        
        return ratingTypeListItemTransfers;
    }
    
    private void updateRatingTypeListItemFromValue(RatingTypeListItemDetailValue ratingTypeListItemDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(ratingTypeListItemDetailValue.hasBeenModified()) {
            RatingTypeListItem ratingTypeListItem = RatingTypeListItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     ratingTypeListItemDetailValue.getRatingTypeListItemPK());
            RatingTypeListItemDetail ratingTypeListItemDetail = ratingTypeListItem.getActiveDetailForUpdate();
            
            ratingTypeListItemDetail.setThruTime(session.START_TIME_LONG);
            ratingTypeListItemDetail.store();
            
            RatingTypeListItemPK ratingTypeListItemPK = ratingTypeListItemDetail.getRatingTypeListItemPK();
            RatingType ratingType = ratingTypeListItemDetail.getRatingType();
            RatingTypePK ratingTypePK = ratingType.getPrimaryKey();
            String ratingTypeListItemName = ratingTypeListItemDetailValue.getRatingTypeListItemName();
            Boolean isDefault = ratingTypeListItemDetailValue.getIsDefault();
            Integer sortOrder = ratingTypeListItemDetailValue.getSortOrder();
            
            if(checkDefault) {
                RatingTypeListItem defaultRatingTypeListItem = getDefaultRatingTypeListItem(ratingType);
                boolean defaultFound = defaultRatingTypeListItem != null && !defaultRatingTypeListItem.equals(ratingTypeListItem);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    RatingTypeListItemDetailValue defaultRatingTypeListItemDetailValue = getDefaultRatingTypeListItemDetailValueForUpdate(ratingType);
                    
                    defaultRatingTypeListItemDetailValue.setIsDefault(Boolean.FALSE);
                    updateRatingTypeListItemFromValue(defaultRatingTypeListItemDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            ratingTypeListItemDetail = RatingTypeListItemDetailFactory.getInstance().create(ratingTypeListItemPK,
                    ratingTypePK, ratingTypeListItemName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            ratingTypeListItem.setActiveDetail(ratingTypeListItemDetail);
            ratingTypeListItem.setLastDetail(ratingTypeListItemDetail);
            
            sendEventUsingNames(ratingTypeListItemPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateRatingTypeListItemFromValue(RatingTypeListItemDetailValue ratingTypeListItemDetailValue, BasePK updatedBy) {
        updateRatingTypeListItemFromValue(ratingTypeListItemDetailValue, true, updatedBy);
    }
    
    public RatingTypeListItemChoicesBean getRatingTypeListItemChoices(String defaultRatingTypeListItemChoice, Language language,
            boolean allowNullChoice, RatingType ratingType) {
        List<RatingTypeListItem> ratingTypeListItems = getRatingTypeListItems(ratingType);
        var size = ratingTypeListItems.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultRatingTypeListItemChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var ratingTypeListItem : ratingTypeListItems) {
            RatingTypeListItemDetail ratingTypeListItemDetail = ratingTypeListItem.getLastDetail();
            var label = getBestRatingTypeListItemDescription(ratingTypeListItem, language);
            var value = ratingTypeListItemDetail.getRatingTypeListItemName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultRatingTypeListItemChoice != null && defaultRatingTypeListItemChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && ratingTypeListItemDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new RatingTypeListItemChoicesBean(labels, values, defaultValue);
    }
    
    public void deleteRatingTypeListItem(RatingTypeListItem ratingTypeListItem, BasePK deletedBy) {
        deleteRatingTypeListItemDescriptionsByRatingTypeListItem(ratingTypeListItem, deletedBy);
        deleteRatingsByRatingTypeListItem(ratingTypeListItem, deletedBy);
        
        RatingTypeListItemDetail ratingTypeListItemDetail = ratingTypeListItem.getLastDetailForUpdate();
        ratingTypeListItemDetail.setThruTime(session.START_TIME_LONG);
        ratingTypeListItem.setActiveDetail(null);
        ratingTypeListItem.store();
        
        // Check for default, and pick one if necessary
        RatingType ratingType = ratingTypeListItemDetail.getRatingType();
        RatingTypeListItem defaultRatingTypeListItem = getDefaultRatingTypeListItem(ratingType);
        if(defaultRatingTypeListItem == null) {
            List<RatingTypeListItem> ratingTypePriorities = getRatingTypeListItemsForUpdate(ratingType);
            
            if(!ratingTypePriorities.isEmpty()) {
                Iterator<RatingTypeListItem> iter = ratingTypePriorities.iterator();
                if(iter.hasNext()) {
                    defaultRatingTypeListItem = iter.next();
                }
                RatingTypeListItemDetailValue ratingTypeListItemDetailValue = Objects.requireNonNull(defaultRatingTypeListItem).getLastDetailForUpdate().getRatingTypeListItemDetailValue().clone();
                
                ratingTypeListItemDetailValue.setIsDefault(Boolean.TRUE);
                updateRatingTypeListItemFromValue(ratingTypeListItemDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(ratingTypeListItem.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteRatingTypeListItems(List<RatingTypeListItem> ratingTypeListItems, BasePK deletedBy) {
        ratingTypeListItems.forEach((ratingTypeListItem) -> 
                deleteRatingTypeListItem(ratingTypeListItem, deletedBy)
        );
    }
    
    public void deleteRatingTypeListItemsByRatingType(RatingType ratingType, BasePK deletedBy) {
        deleteRatingTypeListItems(getRatingTypeListItemsForUpdate(ratingType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Rating Type List Item Descriptions
    // --------------------------------------------------------------------------------
    
    public RatingTypeListItemDescription createRatingTypeListItemDescription(RatingTypeListItem ratingTypeListItem, Language language,
            String description, BasePK createdBy) {
        RatingTypeListItemDescription ratingTypeListItemDescription = RatingTypeListItemDescriptionFactory.getInstance().create(session,
                ratingTypeListItem, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(ratingTypeListItem.getPrimaryKey(), EventTypes.MODIFY, ratingTypeListItemDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return ratingTypeListItemDescription;
    }
    
    private RatingTypeListItemDescription getRatingTypeListItemDescription(RatingTypeListItem ratingTypeListItem, Language language, EntityPermission entityPermission) {
        RatingTypeListItemDescription ratingTypeListItemDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM ratingtypelistitemdescriptions " +
                        "WHERE rtgtyplid_rtgtypli_ratingtypelistitemid = ? AND rtgtyplid_lang_languageid = ? AND rtgtyplid_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM ratingtypelistitemdescriptions " +
                        "WHERE rtgtyplid_rtgtypli_ratingtypelistitemid = ? AND rtgtyplid_lang_languageid = ? AND rtgtyplid_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = RatingTypeListItemDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, ratingTypeListItem.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            ratingTypeListItemDescription = RatingTypeListItemDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return ratingTypeListItemDescription;
    }
    
    public RatingTypeListItemDescription getRatingTypeListItemDescription(RatingTypeListItem ratingTypeListItem, Language language) {
        return getRatingTypeListItemDescription(ratingTypeListItem, language, EntityPermission.READ_ONLY);
    }
    
    public RatingTypeListItemDescription getRatingTypeListItemDescriptionForUpdate(RatingTypeListItem ratingTypeListItem, Language language) {
        return getRatingTypeListItemDescription(ratingTypeListItem, language, EntityPermission.READ_WRITE);
    }
    
    public RatingTypeListItemDescriptionValue getRatingTypeListItemDescriptionValue(RatingTypeListItemDescription ratingTypeListItemDescription) {
        return ratingTypeListItemDescription == null? null: ratingTypeListItemDescription.getRatingTypeListItemDescriptionValue().clone();
    }
    
    public RatingTypeListItemDescriptionValue getRatingTypeListItemDescriptionValueForUpdate(RatingTypeListItem ratingTypeListItem, Language language) {
        return getRatingTypeListItemDescriptionValue(getRatingTypeListItemDescriptionForUpdate(ratingTypeListItem, language));
    }
    
    private List<RatingTypeListItemDescription> getRatingTypeListItemDescriptionsByRatingTypeListItem(RatingTypeListItem ratingTypeListItem, EntityPermission entityPermission) {
        List<RatingTypeListItemDescription> ratingTypeListItemDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM ratingtypelistitemdescriptions, languages " +
                        "WHERE rtgtyplid_rtgtypli_ratingtypelistitemid = ? AND rtgtyplid_thrutime = ? AND rtgtyplid_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM ratingtypelistitemdescriptions " +
                        "WHERE rtgtyplid_rtgtypli_ratingtypelistitemid = ? AND rtgtyplid_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = RatingTypeListItemDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, ratingTypeListItem.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            ratingTypeListItemDescriptions = RatingTypeListItemDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return ratingTypeListItemDescriptions;
    }
    
    public List<RatingTypeListItemDescription> getRatingTypeListItemDescriptionsByRatingTypeListItem(RatingTypeListItem ratingTypeListItem) {
        return getRatingTypeListItemDescriptionsByRatingTypeListItem(ratingTypeListItem, EntityPermission.READ_ONLY);
    }
    
    public List<RatingTypeListItemDescription> getRatingTypeListItemDescriptionsByRatingTypeListItemForUpdate(RatingTypeListItem ratingTypeListItem) {
        return getRatingTypeListItemDescriptionsByRatingTypeListItem(ratingTypeListItem, EntityPermission.READ_WRITE);
    }
    
    public String getBestRatingTypeListItemDescription(RatingTypeListItem ratingTypeListItem, Language language) {
        String description;
        RatingTypeListItemDescription ratingTypeListItemDescription = getRatingTypeListItemDescription(ratingTypeListItem, language);
        
        if(ratingTypeListItemDescription == null && !language.getIsDefault()) {
            ratingTypeListItemDescription = getRatingTypeListItemDescription(ratingTypeListItem, getPartyControl().getDefaultLanguage());
        }
        
        if(ratingTypeListItemDescription == null) {
            description = ratingTypeListItem.getLastDetail().getRatingTypeListItemName();
        } else {
            description = ratingTypeListItemDescription.getDescription();
        }
        
        return description;
    }
    
    public RatingTypeListItemDescriptionTransfer getRatingTypeListItemDescriptionTransfer(UserVisit userVisit, RatingTypeListItemDescription ratingTypeListItemDescription) {
        return getRatingTransferCaches(userVisit).getRatingTypeListItemDescriptionTransferCache().getRatingTypeListItemDescriptionTransfer(ratingTypeListItemDescription);
    }
    
    public List<RatingTypeListItemDescriptionTransfer> getRatingTypeListItemDescriptionTransfers(UserVisit userVisit, RatingTypeListItem ratingTypeListItem) {
        List<RatingTypeListItemDescription> ratingTypeListItemDescriptions = getRatingTypeListItemDescriptionsByRatingTypeListItem(ratingTypeListItem);
        List<RatingTypeListItemDescriptionTransfer> ratingTypeListItemDescriptionTransfers = new ArrayList<>(ratingTypeListItemDescriptions.size());
        RatingTypeListItemDescriptionTransferCache ratingTypeListItemDescriptionTransferCache = getRatingTransferCaches(userVisit).getRatingTypeListItemDescriptionTransferCache();
        
        ratingTypeListItemDescriptions.forEach((ratingTypeListItemDescription) ->
                ratingTypeListItemDescriptionTransfers.add(ratingTypeListItemDescriptionTransferCache.getRatingTypeListItemDescriptionTransfer(ratingTypeListItemDescription))
        );
        
        return ratingTypeListItemDescriptionTransfers;
    }
    
    public void updateRatingTypeListItemDescriptionFromValue(RatingTypeListItemDescriptionValue ratingTypeListItemDescriptionValue, BasePK updatedBy) {
        if(ratingTypeListItemDescriptionValue.hasBeenModified()) {
            RatingTypeListItemDescription ratingTypeListItemDescription = RatingTypeListItemDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, ratingTypeListItemDescriptionValue.getPrimaryKey());
            
            ratingTypeListItemDescription.setThruTime(session.START_TIME_LONG);
            ratingTypeListItemDescription.store();
            
            RatingTypeListItem ratingTypeListItem = ratingTypeListItemDescription.getRatingTypeListItem();
            Language language = ratingTypeListItemDescription.getLanguage();
            String description = ratingTypeListItemDescriptionValue.getDescription();
            
            ratingTypeListItemDescription = RatingTypeListItemDescriptionFactory.getInstance().create(ratingTypeListItem, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(ratingTypeListItem.getPrimaryKey(), EventTypes.MODIFY, ratingTypeListItemDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteRatingTypeListItemDescription(RatingTypeListItemDescription ratingTypeListItemDescription, BasePK deletedBy) {
        ratingTypeListItemDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(ratingTypeListItemDescription.getRatingTypeListItemPK(), EventTypes.MODIFY, ratingTypeListItemDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteRatingTypeListItemDescriptionsByRatingTypeListItem(RatingTypeListItem ratingTypeListItem, BasePK deletedBy) {
        List<RatingTypeListItemDescription> ratingTypeListItemDescriptions = getRatingTypeListItemDescriptionsByRatingTypeListItemForUpdate(ratingTypeListItem);
        
        ratingTypeListItemDescriptions.forEach((ratingTypeListItemDescription) -> 
                deleteRatingTypeListItemDescription(ratingTypeListItemDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Ratings
    // --------------------------------------------------------------------------------
    
    public Rating createRating(String ratingName, RatingTypeListItem ratingTypeListItem, EntityInstance ratedEntityInstance,
            EntityInstance ratedByEntityInstance, BasePK createdBy) {
        Rating rating = RatingFactory.getInstance().create();
        RatingDetail ratingDetail = RatingDetailFactory.getInstance().create(rating, ratingName, ratingTypeListItem,
                ratedEntityInstance, ratedByEntityInstance, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        rating = RatingFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, rating.getPrimaryKey());
        rating.setActiveDetail(ratingDetail);
        rating.setLastDetail(ratingDetail);
        rating.store();
        
        sendEventUsingNames(rating.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        sendEventUsingNames(ratedEntityInstance, EventTypes.TOUCH, rating.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return rating;
    }
    
    private Rating getRatingByName(String ratingName, EntityPermission entityPermission) {
        Rating rating;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM ratings, ratingdetails " +
                        "WHERE rtg_activedetailid = rtgdt_ratingdetailid AND rtgdt_ratingname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM ratings, ratingdetails " +
                        "WHERE rtg_activedetailid = rtgdt_ratingdetailid AND rtgdt_ratingname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = RatingFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, ratingName);
            
            rating = RatingFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return rating;
    }
    
    public Rating getRatingByName(String ratingName) {
        return getRatingByName(ratingName, EntityPermission.READ_ONLY);
    }
    
    public Rating getRatingByNameForUpdate(String ratingName) {
        return getRatingByName(ratingName, EntityPermission.READ_WRITE);
    }
    
    public RatingDetailValue getRatingDetailValue(RatingDetail ratingDetail) {
        return ratingDetail == null? null: ratingDetail.getRatingDetailValue().clone();
    }
    
    public RatingDetailValue getRatingDetailValueByNameForUpdate(String ratingName) {
        Rating rating = getRatingByNameForUpdate(ratingName);
        
        return rating == null? null: getRatingDetailValue(rating.getLastDetailForUpdate());
    }
    
    private Rating getRating(EntityInstance ratedEntityInstance, EntityInstance ratedByEntityInstance,
            EntityPermission entityPermission) {
        Rating rating;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM ratings, ratingdetails " +
                        "WHERE rtg_activedetailid = rtgdt_ratingdetailid AND rtgdt_ratedentityinstanceid = ? " +
                        "AND rtgdt_ratedbyentityinstanceid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM ratings, ratingdetails " +
                        "WHERE rtg_activedetailid = rtgdt_ratingdetailid AND rtgdt_ratedentityinstanceid = ? " +
                        "AND rtgdt_ratedbyentityinstanceid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = RatingFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, ratedEntityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, ratedByEntityInstance.getPrimaryKey().getEntityId());
            
            rating = RatingFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return rating;
    }
    
    public Rating getRating(EntityInstance ratedEntityInstance, EntityInstance ratedByEntityInstance) {
        return getRating(ratedEntityInstance, ratedByEntityInstance, EntityPermission.READ_ONLY);
    }
    
    public Rating getRatingForUpdate(EntityInstance ratedEntityInstance, EntityInstance ratedByEntityInstance) {
        return getRating(ratedEntityInstance, ratedByEntityInstance, EntityPermission.READ_WRITE);
    }
    
    public RatingDetailValue getRatingDetailValueForUpdate(Rating rating) {
        return rating == null ? null : rating.getLastDetailForUpdate().getRatingDetailValue().clone();
    }
    
    private List<Rating> getRatingsByRatedEntityInstance(EntityInstance ratedEntityInstance, EntityPermission entityPermission) {
        List<Rating> ratings;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM ratings, ratingdetails " +
                        "WHERE rtg_activedetailid = rtgdt_ratingdetailid AND rtgdt_ratedentityinstanceid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM ratings, ratingdetails " +
                        "WHERE rtg_activedetailid = rtgdt_ratingdetailid AND rtgdt_ratedentityinstanceid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = RatingFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, ratedEntityInstance.getPrimaryKey().getEntityId());
            
            ratings = RatingFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return ratings;
    }
    
    
    public List<Rating> getRatingsByRatedEntityInstance(EntityInstance ratedEntityInstance) {
        return getRatingsByRatedEntityInstance(ratedEntityInstance, EntityPermission.READ_ONLY);
    }
    
    public List<Rating> getRatingsByRatedEntityInstanceForUpdate(EntityInstance ratedEntityInstance) {
        return getRatingsByRatedEntityInstance(ratedEntityInstance, EntityPermission.READ_WRITE);
    }
    
    private List<Rating> getRatingsByRatedByEntityInstance(EntityInstance ratedByEntityInstance, EntityPermission entityPermission) {
        List<Rating> ratings;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM ratings, ratingdetails " +
                        "WHERE rtg_activedetailid = rtgdt_ratingdetailid AND rtgdt_ratedbyentityinstanceid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM ratings, ratingdetails " +
                        "WHERE rtg_activedetailid = rtgdt_ratingdetailid AND rtgdt_ratedbyentityinstanceid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = RatingFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, ratedByEntityInstance.getPrimaryKey().getEntityId());
            
            ratings = RatingFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return ratings;
    }
    
    public List<Rating> getRatingsByRatedByEntityInstance(EntityInstance ratedByEntityInstance) {
        return getRatingsByRatedByEntityInstance(ratedByEntityInstance, EntityPermission.READ_ONLY);
    }
    
    public List<Rating> getRatingsByRatedByEntityInstanceForUpdate(EntityInstance ratedByEntityInstance) {
        return getRatingsByRatedByEntityInstance(ratedByEntityInstance, EntityPermission.READ_WRITE);
    }
    
    private List<Rating> getRatingsByRatingTypeListItem(RatingTypeListItem ratingTypeListItem, EntityPermission entityPermission) {
        List<Rating> ratings;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM ratings, ratingdetails " +
                        "WHERE rtg_activedetailid = rtgdt_ratingdetailid AND rtgdt_rtgtypli_ratingtypelistitemid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM ratings, ratingdetails " +
                        "WHERE rtg_activedetailid = rtgdt_ratingdetailid AND rtgdt_rtgtypli_ratingtypelistitemid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = RatingFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, ratingTypeListItem.getPrimaryKey().getEntityId());
            
            ratings = RatingFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return ratings;
    }
    
    public List<Rating> getRatingsByRatingTypeListItem(RatingTypeListItem ratingTypeListItem) {
        return getRatingsByRatingTypeListItem(ratingTypeListItem, EntityPermission.READ_ONLY);
    }
    
    public List<Rating> getRatingsByRatingTypeListItemForUpdate(RatingTypeListItem ratingTypeListItem) {
        return getRatingsByRatingTypeListItem(ratingTypeListItem, EntityPermission.READ_WRITE);
    }
    
    private List<Rating> getRatingsByRatedEntityInstanceAndRatingType(EntityInstance ratedEntityInstance, RatingType ratingType,
            EntityPermission entityPermission) {
        List<Rating> ratings;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM ratings, ratingdetails, ratingtypelistitems, ratingtypelistitemdetails " +
                        "WHERE rtg_activedetailid = rtgdt_ratingdetailid " +
                        "AND rtgdt_ratedentityinstanceid = ? AND rtgdt_rtgtypli_ratingtypelistitemid = rtgtypli_ratingtypelistitemid " +
                        "AND rtgtypli_activedetailid = rtgtyplidt_ratingtypelistitemdetailid AND rtgtyplidt_rtgtyp_ratingtypeid = ? " +
                        "ORDER BY rtgtyplidt_sortorder, rtgtyplidt_ratingtypelistitemname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM ratings, ratingdetails, ratingtypelistitems, ratingtypelistitemdetails " +
                        "WHERE rtg_activedetailid = rtgdt_ratingdetailid " +
                        "AND rtgdt_ratedentityinstanceid = ? AND rtgdt_rtgtypli_ratingtypelistitemid = rtgtypli_ratingtypelistitemid " +
                        "AND rtgtypli_activedetailid = rtgtyplidt_ratingtypelistitemdetailid AND rtgtyplidt_rtgtyp_ratingtypeid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = RatingFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, ratedEntityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, ratingType.getPrimaryKey().getEntityId());
            
            ratings = RatingFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return ratings;
    }
    
    public List<Rating> getRatingsByRatedEntityInstanceAndRatingType(EntityInstance ratedEntityInstance, RatingType ratingType) {
        return getRatingsByRatedEntityInstanceAndRatingType(ratedEntityInstance, ratingType, EntityPermission.READ_ONLY);
    }
    
    public List<Rating> getRatingsByRatedEntityInstanceAndRatingTypeForUpdate(EntityInstance ratedEntityInstance,
            RatingType ratingType) {
        return getRatingsByRatedEntityInstanceAndRatingType(ratedEntityInstance, ratingType, EntityPermission.READ_WRITE);
    }
    
    public RatingTransfer getRatingTransfer(UserVisit userVisit, Rating rating) {
        return getRatingTransferCaches(userVisit).getRatingTransferCache().getRatingTransfer(rating);
    }
    
    public List<RatingTransfer> getRatingTransfers(UserVisit userVisit, List<Rating> ratings) {
        List<RatingTransfer> ratingTransfers = new ArrayList<>(ratings.size());
        RatingTransferCache ratingTransferCache = getRatingTransferCaches(userVisit).getRatingTransferCache();
        
        ratings.forEach((rating) ->
                ratingTransfers.add(ratingTransferCache.getRatingTransfer(rating))
        );
        
        return ratingTransfers;
    }
    
    public List<RatingTransfer> getRatingTransfersByRatedEntityInstance(UserVisit userVisit, EntityInstance ratedEntityInstance) {
        return getRatingTransfers(userVisit, getRatingsByRatedEntityInstance(ratedEntityInstance));
    }
    
    public List<RatingTransfer> getRatingTransfersByRatedEntityInstanceAndRatingType(UserVisit userVisit, EntityInstance ratedEntityInstance,
            RatingType ratingType) {
        return getRatingTransfers(userVisit, getRatingsByRatedEntityInstanceAndRatingType(ratedEntityInstance, ratingType));
    }
    
    public void updateRatingFromValue(RatingDetailValue ratingDetailValue,  BasePK updatedBy) {
        if(ratingDetailValue.hasBeenModified()) {
            Rating rating = RatingFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, ratingDetailValue.getRatingPK());
            RatingDetail ratingDetail = rating.getActiveDetailForUpdate();
            
            ratingDetail.setThruTime(session.START_TIME_LONG);
            ratingDetail.store();
            
            RatingPK ratingPK = ratingDetail.getRatingPK(); // Not updated
            String ratingName = ratingDetail.getRatingName(); // Not updated
            RatingTypeListItemPK ratingTypeListItemPK = ratingDetailValue.getRatingTypeListItemPK();
            EntityInstancePK ratedEntityInstancePK = ratingDetail.getRatedEntityInstancePK(); // Not updated
            EntityInstancePK ratedByEntityInstancePK = ratingDetail.getRatedByEntityInstancePK(); // Not updated
            
            ratingDetail = RatingDetailFactory.getInstance().create(ratingPK, ratingName, ratingTypeListItemPK, ratedEntityInstancePK, ratedByEntityInstancePK,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            rating.setActiveDetail(ratingDetail);
            rating.setLastDetail(ratingDetail);
            
            sendEventUsingNames(rating.getPrimaryKey(), EventTypes.MODIFY, null, null, updatedBy);
            sendEventUsingNames(ratingDetail.getRatedEntityInstance(), EventTypes.TOUCH, rating.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteRating(Rating rating, BasePK deletedBy) {
        RatingDetail ratingDetail = rating.getLastDetailForUpdate();
        
        ratingDetail.setThruTime(session.START_TIME_LONG);
        rating.setActiveDetail(null);
        rating.store();
        
        sendEventUsingNames(rating.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
        sendEventUsingNames(ratingDetail.getRatedEntityInstance(), EventTypes.TOUCH, rating.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteRatings(List<Rating> ratings, BasePK deletedBy) {
        ratings.forEach((rating) -> 
                deleteRating(rating, deletedBy)
        );
    }
    
    public void deleteRatingsByRatedEntityInstance(EntityInstance ratedEntityInstance, BasePK deletedBy) {
        deleteRatings(getRatingsByRatedEntityInstanceForUpdate(ratedEntityInstance),  deletedBy);
    }
    
    public void deleteRatingsByRatedByEntityInstance(EntityInstance ratedEntityInstance, BasePK deletedBy) {
        deleteRatings(getRatingsByRatedByEntityInstanceForUpdate(ratedEntityInstance),  deletedBy);
    }
    
    public void deleteRatingsByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteRatingsByRatedEntityInstance(entityInstance, deletedBy);
        deleteRatingsByRatedByEntityInstance(entityInstance, deletedBy);
    }
    
    public void deleteRatingsByRatingTypeListItem(RatingTypeListItem ratingTypeListItem, BasePK deletedBy) {
        deleteRatings(getRatingsByRatingTypeListItemForUpdate(ratingTypeListItem), deletedBy);
    }
    
}

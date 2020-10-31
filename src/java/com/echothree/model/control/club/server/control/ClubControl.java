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

package com.echothree.model.control.club.server.control;

import com.echothree.model.control.club.common.choice.ClubItemTypeChoicesBean;
import com.echothree.model.control.club.common.transfer.ClubDescriptionTransfer;
import com.echothree.model.control.club.common.transfer.ClubItemTransfer;
import com.echothree.model.control.club.common.transfer.ClubItemTypeTransfer;
import com.echothree.model.control.club.common.transfer.ClubTransfer;
import com.echothree.model.control.club.server.transfer.ClubDescriptionTransferCache;
import com.echothree.model.control.club.server.transfer.ClubItemTransferCache;
import com.echothree.model.control.club.server.transfer.ClubItemTypeTransferCache;
import com.echothree.model.control.club.server.transfer.ClubTransferCache;
import com.echothree.model.control.club.server.transfer.ClubTransferCaches;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.data.accounting.common.pk.CurrencyPK;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.club.common.pk.ClubItemTypePK;
import com.echothree.model.data.club.common.pk.ClubPK;
import com.echothree.model.data.club.server.entity.Club;
import com.echothree.model.data.club.server.entity.ClubDescription;
import com.echothree.model.data.club.server.entity.ClubDetail;
import com.echothree.model.data.club.server.entity.ClubItem;
import com.echothree.model.data.club.server.entity.ClubItemType;
import com.echothree.model.data.club.server.entity.ClubItemTypeDescription;
import com.echothree.model.data.club.server.factory.ClubDescriptionFactory;
import com.echothree.model.data.club.server.factory.ClubDetailFactory;
import com.echothree.model.data.club.server.factory.ClubFactory;
import com.echothree.model.data.club.server.factory.ClubItemFactory;
import com.echothree.model.data.club.server.factory.ClubItemTypeDescriptionFactory;
import com.echothree.model.data.club.server.factory.ClubItemTypeFactory;
import com.echothree.model.data.club.server.value.ClubDescriptionValue;
import com.echothree.model.data.club.server.value.ClubDetailValue;
import com.echothree.model.data.club.server.value.ClubItemValue;
import com.echothree.model.data.filter.common.pk.FilterPK;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.item.common.pk.ItemPK;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.subscription.common.pk.SubscriptionTypePK;
import com.echothree.model.data.subscription.server.entity.SubscriptionType;
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

public class ClubControl
        extends BaseModelControl {
    
    /** Creates a new instance of ClubControl */
    public ClubControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Club Transfer Caches
    // --------------------------------------------------------------------------------
    
    private ClubTransferCaches clubTransferCaches;
    
    public ClubTransferCaches getClubTransferCaches(UserVisit userVisit) {
        if(clubTransferCaches == null) {
            clubTransferCaches = new ClubTransferCaches(userVisit, this);
        }
        
        return clubTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Clubs
    // --------------------------------------------------------------------------------
    
    public Club createClub(String clubName, SubscriptionType subscriptionType, Filter clubPriceFilter, Currency currency,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        Club defaultClub = getDefaultClub();
        boolean defaultFound = defaultClub != null;
        
        if(defaultFound && isDefault) {
            ClubDetailValue defaultClubDetailValue = getDefaultClubDetailValueForUpdate();
            
            defaultClubDetailValue.setIsDefault(Boolean.FALSE);
            updateClubFromValue(defaultClubDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        Club club = ClubFactory.getInstance().create();
        ClubDetail clubDetail = ClubDetailFactory.getInstance().create(club, clubName, subscriptionType, clubPriceFilter,
                currency, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        club = ClubFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                club.getPrimaryKey());
        club.setActiveDetail(clubDetail);
        club.setLastDetail(clubDetail);
        club.store();
        
        sendEventUsingNames(club.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return club;
    }
    
    private List<Club> getClubs(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM clubs, clubdetails " +
                    "WHERE clb_activedetailid = clbdt_clubdetailid " +
                    "ORDER BY clbdt_sortorder, clbdt_clubname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM clubs, clubdetails " +
                    "WHERE clb_activedetailid = clbdt_clubdetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = ClubFactory.getInstance().prepareStatement(query);
        
        return ClubFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<Club> getClubs() {
        return getClubs(EntityPermission.READ_ONLY);
    }
    
    public List<Club> getClubsForUpdate() {
        return getClubs(EntityPermission.READ_WRITE);
    }
    
    private Club getDefaultClub(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM clubs, clubdetails " +
                    "WHERE clb_activedetailid = clbdt_clubdetailid AND clbdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM clubs, clubdetails " +
                    "WHERE clb_activedetailid = clbdt_clubdetailid AND clbdt_isdefault = 1 " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = ClubFactory.getInstance().prepareStatement(query);
        
        return ClubFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public Club getDefaultClub() {
        return getDefaultClub(EntityPermission.READ_ONLY);
    }
    
    public Club getDefaultClubForUpdate() {
        return getDefaultClub(EntityPermission.READ_WRITE);
    }
    
    public ClubDetailValue getDefaultClubDetailValueForUpdate() {
        return getDefaultClubForUpdate().getLastDetailForUpdate().getClubDetailValue().clone();
    }
    
    private Club getClubByName(String clubName, EntityPermission entityPermission) {
        Club club;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM clubs, clubdetails " +
                        "WHERE clb_activedetailid = clbdt_clubdetailid AND clbdt_clubname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM clubs, clubdetails " +
                        "WHERE clb_activedetailid = clbdt_clubdetailid AND clbdt_clubname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ClubFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, clubName);
            
            club = ClubFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return club;
    }
    
    public Club getClubByName(String clubName) {
        return getClubByName(clubName, EntityPermission.READ_ONLY);
    }
    
    public Club getClubByNameForUpdate(String clubName) {
        return getClubByName(clubName, EntityPermission.READ_WRITE);
    }
    
    public ClubDetailValue getClubDetailValueForUpdate(Club club) {
        return club == null? null: club.getLastDetailForUpdate().getClubDetailValue().clone();
    }
    
    public ClubDetailValue getClubDetailValueByNameForUpdate(String clubName) {
        return getClubDetailValueForUpdate(getClubByNameForUpdate(clubName));
    }
    
    private Club getClubBySubscriptionType(SubscriptionType subscriptionType, EntityPermission entityPermission) {
        Club club;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM clubs, clubdetails " +
                        "WHERE clb_activedetailid = clbdt_clubdetailid AND clbdt_clubname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM clubs, clubdetails " +
                        "WHERE clb_activedetailid = clbdt_clubdetailid AND clbdt_subscrtyp_subscriptiontypeid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ClubFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, subscriptionType.getPrimaryKey().getEntityId());
            
            club = ClubFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return club;
    }
    
    public Club getClubBySubscriptionType(SubscriptionType subscriptionType) {
        return getClubBySubscriptionType(subscriptionType, EntityPermission.READ_ONLY);
    }
    
    public Club getClubBySubscriptionTypeForUpdate(SubscriptionType subscriptionType) {
        return getClubBySubscriptionType(subscriptionType, EntityPermission.READ_WRITE);
    }
    
    public ClubTransfer getClubTransfer(UserVisit userVisit, Club club) {
        return getClubTransferCaches(userVisit).getClubTransferCache().getClubTransfer(club);
    }
    
    public List<ClubTransfer> getClubTransfers(UserVisit userVisit) {
        List<Club> clubs = getClubs();
        List<ClubTransfer> clubTransfers = new ArrayList<>(clubs.size());
        ClubTransferCache clubTransferCache = getClubTransferCaches(userVisit).getClubTransferCache();
        
        clubs.forEach((club) ->
                clubTransfers.add(clubTransferCache.getClubTransfer(club))
        );
        
        return clubTransfers;
    }
    
    private void updateClubFromValue(ClubDetailValue clubDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(clubDetailValue.hasBeenModified()) {
            Club club = ClubFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     clubDetailValue.getClubPK());
            ClubDetail clubDetail = club.getActiveDetailForUpdate();
            
            clubDetail.setThruTime(session.START_TIME_LONG);
            clubDetail.store();
            
            ClubPK clubPK = clubDetail.getClubPK();
            String clubName = clubDetailValue.getClubName();
            SubscriptionTypePK subscriptionTypePK = clubDetailValue.getSubscriptionTypePK();
            FilterPK clubPriceFilterPK = clubDetailValue.getClubPriceFilterPK();
            CurrencyPK currencyPK = clubDetailValue.getCurrencyPK();
            Boolean isDefault = clubDetailValue.getIsDefault();
            Integer sortOrder = clubDetailValue.getSortOrder();
            
            if(checkDefault) {
                Club defaultClub = getDefaultClub();
                boolean defaultFound = defaultClub != null && !defaultClub.equals(club);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ClubDetailValue defaultClubDetailValue = getDefaultClubDetailValueForUpdate();
                    
                    defaultClubDetailValue.setIsDefault(Boolean.FALSE);
                    updateClubFromValue(defaultClubDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            clubDetail = ClubDetailFactory.getInstance().create(clubPK, clubName, subscriptionTypePK, clubPriceFilterPK,
                    currencyPK, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            club.setActiveDetail(clubDetail);
            club.setLastDetail(clubDetail);
            
            sendEventUsingNames(clubPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateClubFromValue(ClubDetailValue clubDetailValue, BasePK updatedBy) {
        updateClubFromValue(clubDetailValue, true, updatedBy);
    }
    
    public void deleteClub(Club club, BasePK deletedBy) {
        deleteClubDescriptionsByClub(club, deletedBy);
        deleteClubItemsByClub(club, deletedBy);
        
        ClubDetail clubDetail = club.getLastDetailForUpdate();
        clubDetail.setThruTime(session.START_TIME_LONG);
        club.setActiveDetail(null);
        club.store();
        
        // Check for default, and pick one if necessary
        Club defaultClub = getDefaultClub();
        if(defaultClub == null) {
            List<Club> clubs = getClubsForUpdate();
            
            if(!clubs.isEmpty()) {
                Iterator<Club> iter = clubs.iterator();
                if(iter.hasNext()) {
                    defaultClub = iter.next();
                }
                ClubDetailValue clubDetailValue = defaultClub.getLastDetailForUpdate().getClubDetailValue().clone();
                
                clubDetailValue.setIsDefault(Boolean.TRUE);
                updateClubFromValue(clubDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(club.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteClubBySubscriptionType(SubscriptionType subscriptionType, BasePK deletedBy) {
        Club club = getClubBySubscriptionType(subscriptionType);
        
        if(club != null) {
            deleteClub(club, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Club Descriptions
    // --------------------------------------------------------------------------------
    
    public ClubDescription createClubDescription(Club club, Language language, String description, BasePK createdBy) {
        ClubDescription clubDescription = ClubDescriptionFactory.getInstance().create(club,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(club.getPrimaryKey(), EventTypes.MODIFY.name(), clubDescription.getPrimaryKey(),
                null, createdBy);
        
        return clubDescription;
    }
    
    private ClubDescription getClubDescription(Club club, Language language,
            EntityPermission entityPermission) {
        ClubDescription clubDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM clubdescriptions " +
                        "WHERE clbd_clb_clubid = ? AND clbd_lang_languageid = ? AND clbd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM clubdescriptions " +
                        "WHERE clbd_clb_clubid = ? AND clbd_lang_languageid = ? AND clbd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ClubDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, club.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            clubDescription = ClubDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return clubDescription;
    }
    
    public ClubDescription getClubDescription(Club club, Language language) {
        return getClubDescription(club, language, EntityPermission.READ_ONLY);
    }
    
    public ClubDescription getClubDescriptionForUpdate(Club club, Language language) {
        return getClubDescription(club, language, EntityPermission.READ_WRITE);
    }
    
    public ClubDescriptionValue getClubDescriptionValue(ClubDescription clubDescription) {
        return clubDescription == null? null: clubDescription.getClubDescriptionValue().clone();
    }
    
    public ClubDescriptionValue getClubDescriptionValueForUpdate(Club club, Language language) {
        return getClubDescriptionValue(getClubDescriptionForUpdate(club, language));
    }
    
    private List<ClubDescription> getClubDescriptionsByClub(Club club,
            EntityPermission entityPermission) {
        List<ClubDescription> clubDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM clubdescriptions, languages " +
                        "WHERE clbd_clb_clubid = ? AND clbd_thrutime = ? AND clbd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM clubdescriptions " +
                        "WHERE clbd_clb_clubid = ? AND clbd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ClubDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, club.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            clubDescriptions = ClubDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return clubDescriptions;
    }
    
    public List<ClubDescription> getClubDescriptionsByClub(Club club) {
        return getClubDescriptionsByClub(club, EntityPermission.READ_ONLY);
    }
    
    public List<ClubDescription> getClubDescriptionsByClubForUpdate(Club club) {
        return getClubDescriptionsByClub(club, EntityPermission.READ_WRITE);
    }
    
    public String getBestClubDescription(Club club, Language language) {
        String description;
        ClubDescription clubDescription = getClubDescription(club, language);
        
        if(clubDescription == null && !language.getIsDefault()) {
            clubDescription = getClubDescription(club, getPartyControl().getDefaultLanguage());
        }
        
        if(clubDescription == null) {
            description = club.getLastDetail().getClubName();
        } else {
            description = clubDescription.getDescription();
        }
        
        return description;
    }
    
    public ClubDescriptionTransfer getClubDescriptionTransfer(UserVisit userVisit,
            ClubDescription clubDescription) {
        return getClubTransferCaches(userVisit).getClubDescriptionTransferCache().getClubDescriptionTransfer(clubDescription);
    }
    
    public List<ClubDescriptionTransfer> getClubDescriptionTransfersByClub(UserVisit userVisit,
            Club club) {
        List<ClubDescription> clubDescriptions = getClubDescriptionsByClub(club);
        List<ClubDescriptionTransfer> clubDescriptionTransfers = new ArrayList<>(clubDescriptions.size());
        ClubDescriptionTransferCache clubDescriptionTransferCache = getClubTransferCaches(userVisit).getClubDescriptionTransferCache();
        
        clubDescriptions.forEach((clubDescription) ->
                clubDescriptionTransfers.add(clubDescriptionTransferCache.getClubDescriptionTransfer(clubDescription))
        );
        
        return clubDescriptionTransfers;
    }
    
    public void updateClubDescriptionFromValue(ClubDescriptionValue clubDescriptionValue, BasePK updatedBy) {
        if(clubDescriptionValue.hasBeenModified()) {
            ClubDescription clubDescription = ClubDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     clubDescriptionValue.getPrimaryKey());
            
            clubDescription.setThruTime(session.START_TIME_LONG);
            clubDescription.store();
            
            Club club = clubDescription.getClub();
            Language language = clubDescription.getLanguage();
            String description = clubDescriptionValue.getDescription();
            
            clubDescription = ClubDescriptionFactory.getInstance().create(club, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(club.getPrimaryKey(), EventTypes.MODIFY.name(), clubDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteClubDescription(ClubDescription clubDescription, BasePK deletedBy) {
        clubDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(clubDescription.getClubPK(), EventTypes.MODIFY.name(),
                clubDescription.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteClubDescriptionsByClub(Club club, BasePK deletedBy) {
        List<ClubDescription> clubDescriptions = getClubDescriptionsByClubForUpdate(club);
        
        clubDescriptions.forEach((clubDescription) -> 
                deleteClubDescription(clubDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Club Item Types
    // --------------------------------------------------------------------------------
    
    public ClubItemType createClubItemType(String clubItemTypeName, Boolean isDefault, Integer sortOrder) {
        return ClubItemTypeFactory.getInstance().create(clubItemTypeName, isDefault, sortOrder);
    }
    
    public List<ClubItemType> getClubItemTypes() {
        PreparedStatement ps = ClubItemTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM clubitemtypes " +
                "ORDER BY clbitmtyp_sortorder, clbitmtyp_clubitemtypename");
        
        return ClubItemTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public ClubItemType getClubItemTypeByName(String clubItemTypeName) {
        ClubItemType clubItemType = null;

        try {
            PreparedStatement ps = ClubItemTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM clubitemtypes " +
                    "WHERE clbitmtyp_clubitemtypename = ?");
            
            ps.setString(1, clubItemTypeName);
            
            clubItemType = ClubItemTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return clubItemType;
    }
    
    public ClubItemTypeChoicesBean getClubItemTypeChoices(String defaultClubItemTypeChoice, Language language,
            boolean allowNullChoice) {
        List<ClubItemType> clubItemTypes = getClubItemTypes();
        int size = clubItemTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
        }
        
        for(ClubItemType clubItemType: clubItemTypes) {
            String label = getBestClubItemTypeDescription(clubItemType, language);
            String value = clubItemType.getClubItemTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultClubItemTypeChoice != null && defaultClubItemTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && clubItemType.getIsDefault()))
                defaultValue = value;
        }
        
        return new ClubItemTypeChoicesBean(labels, values, defaultValue);
    }
    
    public ClubItemTypeTransfer getClubItemTypeTransfer(UserVisit userVisit, ClubItemType clubItemType) {
        return getClubTransferCaches(userVisit).getClubItemTypeTransferCache().getClubItemTypeTransfer(clubItemType);
    }
    
    public List<ClubItemTypeTransfer> getClubItemTypeTransfers(UserVisit userVisit) {
        List<ClubItemType> clubItemTypes = getClubItemTypes();
        List<ClubItemTypeTransfer> clubItemTypeTransfers = new ArrayList<>(clubItemTypes.size());
        ClubItemTypeTransferCache clubItemTypeTransferCache = getClubTransferCaches(userVisit).getClubItemTypeTransferCache();
        
        clubItemTypes.forEach((clubItemType) ->
                clubItemTypeTransfers.add(clubItemTypeTransferCache.getClubItemTypeTransfer(clubItemType))
        );
        
        return clubItemTypeTransfers;
    }
    
    // --------------------------------------------------------------------------------
    //   Club Item Type Descriptions
    // --------------------------------------------------------------------------------
    
    public ClubItemTypeDescription createClubItemTypeDescription(ClubItemType clubItemType, Language language, String description) {
        return ClubItemTypeDescriptionFactory.getInstance().create(clubItemType, language, description);
    }
    
    public ClubItemTypeDescription getClubItemTypeDescription(ClubItemType clubItemType, Language language) {
        ClubItemTypeDescription clubItemTypeDescription;
        
        try {
            PreparedStatement ps = ClubItemTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM clubitemtypedescriptions " +
                    "WHERE clbitmtypd_clbitmtyp_clubitemtypeid = ? AND clbitmtypd_lang_languageid = ?");
            
            ps.setLong(1, clubItemType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            clubItemTypeDescription = ClubItemTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return clubItemTypeDescription;
    }
    
    public String getBestClubItemTypeDescription(ClubItemType clubItemType, Language language) {
        String description;
        ClubItemTypeDescription clubItemTypeDescription = getClubItemTypeDescription(clubItemType, language);
        
        if(clubItemTypeDescription == null && !language.getIsDefault()) {
            clubItemTypeDescription = getClubItemTypeDescription(clubItemType, getPartyControl().getDefaultLanguage());
        }
        
        if(clubItemTypeDescription == null) {
            description = clubItemType.getClubItemTypeName();
        } else {
            description = clubItemTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Club Items
    // --------------------------------------------------------------------------------
    
    public ClubItem createClubItem(Club club, ClubItemType clubItemType, Item item, Long subscriptionTime, BasePK createdBy) {
        ClubItem clubItem = ClubItemFactory.getInstance().create(club, clubItemType, item, subscriptionTime,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(club.getPrimaryKey(), EventTypes.MODIFY.name(), clubItem.getPrimaryKey(), null, createdBy);
        
        return clubItem;
    }
    
    private ClubItem getClubItem(Club club, ClubItemType clubItemType, Item item, EntityPermission entityPermission) {
        ClubItem clubItem;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM clubitems " +
                        "WHERE clbitm_clb_clubid = ? AND clbitm_clbitmtyp_clubitemtypeid = ? AND clbitm_itm_itemid = ? " +
                        "AND clbitm_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM clubitems " +
                        "WHERE clbitm_clb_clubid = ? AND clbitm_clbitmtyp_clubitemtypeid = ? AND clbitm_itm_itemid = ? " +
                        "AND clbitm_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ClubItemFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, club.getPrimaryKey().getEntityId());
            ps.setLong(2, clubItemType.getPrimaryKey().getEntityId());
            ps.setLong(3, item.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            clubItem = ClubItemFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return clubItem;
    }
    
    public ClubItem getClubItem(Club club, ClubItemType clubItemType, Item item) {
        return getClubItem(club, clubItemType, item, EntityPermission.READ_ONLY);
    }
    
    public ClubItem getClubItemForUpdate(Club club, ClubItemType clubItemType, Item item) {
        return getClubItem(club, clubItemType, item, EntityPermission.READ_WRITE);
    }
    
    private List<ClubItem> getClubItemsByClub(Club club, EntityPermission entityPermission) {
        List<ClubItem> clubItems = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM clubitems, clubitemtypes, items, itemdetails " +
                        "WHERE clbitm_clb_clubid = ? AND clbitm_thrutime = ? " +
                        "AND clbitm_clbitmtyp_clubitemtypeid = clbitmtyp_clubitemtypeid " +
                        "AND clbitm_itm_itemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid " +
                        "ORDER BY clbitmtyp_sortorder, clbitmtyp_clubitemtypename, itmdt_itemname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM clubitems " +
                        "WHERE clbitm_clb_clubid = ? AND clbitm_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ClubItemFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, club.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            clubItems = ClubItemFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return clubItems;
    }
    
    public List<ClubItem> getClubItemsByClub(Club club) {
        return getClubItemsByClub(club, EntityPermission.READ_ONLY);
    }
    
    public List<ClubItem> getClubItemsByClubForUpdate(Club club) {
        return getClubItemsByClub(club, EntityPermission.READ_WRITE);
    }
    
    private List<ClubItem> getClubItemsByItem(Item item, EntityPermission entityPermission) {
        List<ClubItem> clubItems = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM clubitems, clubs, clubdetails, clubitemtypes " +
                        "WHERE clbitm_itm_itemid = ? AND clbitm_thrutime = ? " +
                        "AND clbitm_clb_clubid = clb_clubid AND clb_lastdetailid = clbdt_clubdetailid" +
                        "AND clbitm_clbitmtyp_clubitemtypeid = clbitmtyp_clubitemtypeid " +
                        "ORDER BY clbdt_sortorder, clbdt_clubname, clbitmtyp_sortorder, clbitmtyp_clubitemtypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM clubitems " +
                        "WHERE clbitm_itm_itemid = ? AND clbitm_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ClubItemFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            clubItems = ClubItemFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return clubItems;
    }
    
    public List<ClubItem> getClubItemsByItem(Item item) {
        return getClubItemsByItem(item, EntityPermission.READ_ONLY);
    }
    
    public List<ClubItem> getClubItemsByItemForUpdate(Item item) {
        return getClubItemsByItem(item, EntityPermission.READ_WRITE);
    }
    
    public ClubItemTransfer getClubItemTransfer(UserVisit userVisit, ClubItem clubItem) {
        return getClubTransferCaches(userVisit).getClubItemTransferCache().getClubItemTransfer(clubItem);
    }
    
    private List<ClubItemTransfer> getClubItemTransfers(UserVisit userVisit, List<ClubItem> clubItems) {
        List<ClubItemTransfer> clubItemTransfers = new ArrayList<>(clubItems.size());
        ClubItemTransferCache clubItemTransferCache = getClubTransferCaches(userVisit).getClubItemTransferCache();
        
        clubItems.forEach((clubItem) ->
                clubItemTransfers.add(clubItemTransferCache.getClubItemTransfer(clubItem))
        );
        
        return clubItemTransfers;
    }
    
    public List<ClubItemTransfer> getClubItemTransfersByClub(UserVisit userVisit, Club club) {
        return getClubItemTransfers(userVisit, getClubItemsByClub(club));
    }
    
    public List<ClubItemTransfer> getClubItemsByItem(UserVisit userVisit, Item item) {
        return getClubItemTransfers(userVisit, getClubItemsByItem(item));
    }
    
    public void updateClubItemFromValue(ClubItemValue clubItemValue, BasePK updatedBy) {
        if(clubItemValue.hasBeenModified()) {
            ClubItem clubItem = ClubItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    clubItemValue.getPrimaryKey());
            
            clubItem.setThruTime(session.START_TIME_LONG);
            clubItem.store();
            
            ClubPK subscriptionTypePK = clubItem.getClubPK(); // Not updated
            ClubItemTypePK clubItemTypePK = clubItem.getClubItemTypePK(); // Not updated
            ItemPK itemPK = clubItem.getItemPK(); // Not updated
            Long subscriptionTime = clubItemValue.getSubscriptionTime();
            
            clubItem = ClubItemFactory.getInstance().create(subscriptionTypePK, clubItemTypePK, itemPK,
                    subscriptionTime, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(clubItem.getClubPK(), EventTypes.MODIFY.name(),
                    clubItem.getPrimaryKey(), null, updatedBy);
        }
    }
    
    public void deleteClubItem(ClubItem clubItem, BasePK deletedBy) {
        clubItem.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(clubItem.getClubPK(), EventTypes.MODIFY.name(), clubItem.getPrimaryKey(), null, deletedBy);
    }
    
    private void deleteClubItems(List<ClubItem> clubItems, BasePK deletedBy) {
        clubItems.forEach((clubItem) -> 
                deleteClubItem(clubItem, deletedBy)
        );
    }
    
    public void deleteClubItemsByClub(Club club, BasePK deletedBy) {
        deleteClubItems(getClubItemsByClubForUpdate(club), deletedBy);
    }
    
    public void deleteClubItemsByItem(Item item, BasePK deletedBy) {
        deleteClubItems(getClubItemsByItemForUpdate(item), deletedBy);
    }
    
}

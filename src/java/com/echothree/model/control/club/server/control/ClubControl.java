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
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.club.common.pk.ClubPK;
import com.echothree.model.data.club.server.entity.Club;
import com.echothree.model.data.club.server.entity.ClubDescription;
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
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.subscription.server.entity.SubscriptionType;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.club.ClubForeignKeys.CLUBS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.club.ClubForeignKeys.CLUBS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.club.ClubForeignKeys.CLUB_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.club.ClubForeignKeys.CLUB_ITEMS_CLUB_FK;
import static com.echothree.model.jooq.server.keys.club.ClubForeignKeys.CLUB_ITEMS_CLUB_ITEM_TYPE_FK;
import static com.echothree.model.jooq.server.keys.club.ClubForeignKeys.CLUB_ITEMS_ITEM_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEMS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.club.ClubDescriptions.ClubDescriptions;
import static com.echothree.model.jooq.server.tables.club.ClubDetails.ClubDetails;
import static com.echothree.model.jooq.server.tables.club.ClubItemTypeDescriptions.ClubItemTypeDescriptions;
import static com.echothree.model.jooq.server.tables.club.ClubItemTypes.ClubItemTypes;
import static com.echothree.model.jooq.server.tables.club.ClubItems.ClubItems;
import static com.echothree.model.jooq.server.tables.club.Clubs.Clubs;
import static com.echothree.model.jooq.server.tables.item.ItemDetails.ItemDetails;
import static com.echothree.model.jooq.server.tables.item.Items.Items;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;

@CommandScope
public class ClubControl
        extends BaseModelControl {
    
    /** Creates a new instance of ClubControl */
    protected ClubControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Clubs
    // --------------------------------------------------------------------------------

    @Inject
    protected ClubDetailFactory clubDetailFactory;

    @Inject
    protected ClubFactory clubFactory;

    @Inject
    ClubTransferCache clubTransferCache;

    public Club createClub(String clubName, SubscriptionType subscriptionType, Filter clubPriceFilter, Currency currency,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultClub = getDefaultClub();
        var defaultFound = defaultClub != null;
        
        if(defaultFound && isDefault) {
            var defaultClubDetailValue = getDefaultClubDetailValueForUpdate();
            
            defaultClubDetailValue.setIsDefault(false);
            updateClubFromValue(defaultClubDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var club = clubFactory.create();
        var clubDetail = clubDetailFactory.create(club, clubName, subscriptionType, clubPriceFilter,
                currency, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        club = clubFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                club.getPrimaryKey());
        club.setActiveDetail(clubDetail);
        club.setLastDetail(clubDetail);
        club.store();
        
        sendEvent(club.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return club;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Club */
    public Club getClubByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ClubPK(entityInstance.getEntityUniqueId());

        return clubFactory.getEntityFromPK(entityPermission, pk);
    }

    public Club getClubByEntityInstance(EntityInstance entityInstance) {
        return getClubByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Club getClubByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getClubByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countClubs() {
        return session.getDslContext()
                .selectCount()
                .from(Clubs)
                .join(ClubDetails).onKey(CLUBS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countClubsBySubscriptionType(SubscriptionType subscriptionType) {
        return session.getDslContext()
                .selectCount()
                .from(Clubs)
                .join(ClubDetails).onKey(CLUBS_ACTIVE_DETAIL_FK)
                .where(ClubDetails.SUBSCRIPTION_TYPE.eq(subscriptionType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countClubsByFilter(Filter filter) {
        return session.getDslContext()
                .selectCount()
                .from(Clubs)
                .join(ClubDetails).onKey(CLUBS_ACTIVE_DETAIL_FK)
                .where(ClubDetails.CLUB_PRICE_FILTER.eq(filter.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<Club> getClubs(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Clubs.fields())
                .from(Clubs)
                .join(ClubDetails).onKey(CLUBS_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ClubDetails.SORT_ORDER, ClubDetails.CLUB_NAME),
                    ClubFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return clubFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<Club> getClubs() {
        return getClubs(EntityPermission.READ_ONLY);
    }
    
    public List<Club> getClubsForUpdate() {
        return getClubs(EntityPermission.READ_WRITE);
    }
    
    private Club getDefaultClub(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Clubs.fields())
                .from(Clubs)
                .join(ClubDetails).onKey(CLUBS_ACTIVE_DETAIL_FK)
                .where(ClubDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return clubFactory.getEntityFromQuery(entityPermission, query);
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
    
    public Club getClubByName(String clubName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Clubs.fields())
                .from(Clubs)
                .join(ClubDetails).onKey(CLUBS_ACTIVE_DETAIL_FK)
                .where(ClubDetails.CLUB_NAME.eq(clubName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return clubFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.getDslContext()
                    .select(Clubs.fields())
                    .from(Clubs)
                    .join(ClubDetails).onKey(CLUBS_ACTIVE_DETAIL_FK)
                    .where(ClubDetails.SUBSCRIPTION_TYPE.eq(subscriptionType.getPrimaryKey()));
            case READ_WRITE -> session.getDslContext()
                    .select(Clubs.fields())
                    .from(Clubs)
                    .join(ClubDetails).onKey(CLUBS_ACTIVE_DETAIL_FK)
                    .where(ClubDetails.SUBSCRIPTION_TYPE.eq(subscriptionType.getPrimaryKey()))
                    .forUpdate();
        };

        return clubFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public Club getClubBySubscriptionType(SubscriptionType subscriptionType) {
        return getClubBySubscriptionType(subscriptionType, EntityPermission.READ_ONLY);
    }
    
    public Club getClubBySubscriptionTypeForUpdate(SubscriptionType subscriptionType) {
        return getClubBySubscriptionType(subscriptionType, EntityPermission.READ_WRITE);
    }
    
    public ClubTransfer getClubTransfer(UserVisit userVisit, Club club) {
        return clubTransferCache.getClubTransfer(userVisit, club);
    }
    
    public List<ClubTransfer> getClubTransfers(UserVisit userVisit, Collection<Club> clubs) {
        List<ClubTransfer> clubTransfers = new ArrayList<>(clubs.size());

        clubs.forEach((club) ->
                clubTransfers.add(clubTransferCache.getClubTransfer(userVisit, club))
        );

        return clubTransfers;
    }

    public List<ClubTransfer> getClubTransfers(UserVisit userVisit) {
        return getClubTransfers(userVisit, getClubs());
    }
    
    private void updateClubFromValue(ClubDetailValue clubDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(clubDetailValue.hasBeenModified()) {
            var club = clubFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     clubDetailValue.getClubPK());
            var clubDetail = club.getActiveDetailForUpdate();
            
            clubDetail.setThruTime(session.getStartTime());
            clubDetail.store();

            var clubPK = clubDetail.getClubPK();
            var clubName = clubDetailValue.getClubName();
            var subscriptionTypePK = clubDetailValue.getSubscriptionTypePK();
            var clubPriceFilterPK = clubDetailValue.getClubPriceFilterPK();
            var currencyPK = clubDetailValue.getCurrencyPK();
            var isDefault = clubDetailValue.getIsDefault();
            var sortOrder = clubDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultClub = getDefaultClub();
                var defaultFound = defaultClub != null && !defaultClub.equals(club);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultClubDetailValue = getDefaultClubDetailValueForUpdate();
                    
                    defaultClubDetailValue.setIsDefault(false);
                    updateClubFromValue(defaultClubDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            clubDetail = clubDetailFactory.create(clubPK, clubName, subscriptionTypePK, clubPriceFilterPK,
                    currencyPK, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            club.setActiveDetail(clubDetail);
            club.setLastDetail(clubDetail);
            
            sendEvent(clubPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateClubFromValue(ClubDetailValue clubDetailValue, BasePK updatedBy) {
        updateClubFromValue(clubDetailValue, true, updatedBy);
    }
    
    public void deleteClub(Club club, BasePK deletedBy) {
        deleteClubDescriptionsByClub(club, deletedBy);
        deleteClubItemsByClub(club, deletedBy);

        var clubDetail = club.getLastDetailForUpdate();
        clubDetail.setThruTime(session.getStartTime());
        club.setActiveDetail(null);
        club.store();
        
        // Check for default, and pick one if necessary
        var defaultClub = getDefaultClub();
        if(defaultClub == null) {
            var clubs = getClubsForUpdate();
            
            if(!clubs.isEmpty()) {
                var iter = clubs.iterator();
                if(iter.hasNext()) {
                    defaultClub = iter.next();
                }
                var clubDetailValue = Objects.requireNonNull(defaultClub).getLastDetailForUpdate().getClubDetailValue().clone();
                
                clubDetailValue.setIsDefault(true);
                updateClubFromValue(clubDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(club.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteClubBySubscriptionType(SubscriptionType subscriptionType, BasePK deletedBy) {
        var club = getClubBySubscriptionType(subscriptionType);
        
        if(club != null) {
            deleteClub(club, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Club Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ClubDescriptionFactory clubDescriptionFactory;

    @Inject
    ClubDescriptionTransferCache clubDescriptionTransferCache;

    public ClubDescription createClubDescription(Club club, Language language, String description, BasePK createdBy) {
        var clubDescription = clubDescriptionFactory.create(club,
                language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(club.getPrimaryKey(), EventTypes.MODIFY, clubDescription.getPrimaryKey(),
                null, createdBy);
        
        return clubDescription;
    }
    
    private ClubDescription getClubDescription(Club club, Language language,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ClubDescriptions.fields())
                .from(ClubDescriptions)
                .where(ClubDescriptions.CLUB.eq(club.getPrimaryKey()),
                        ClubDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ClubDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return clubDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ClubDescriptions.fields())
                    .from(ClubDescriptions)
                    .join(Languages).onKey(CLUB_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ClubDescriptions.CLUB.eq(club.getPrimaryKey()),
                            ClubDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ClubDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ClubDescriptions.fields())
                    .from(ClubDescriptions)
                    .where(ClubDescriptions.CLUB.eq(club.getPrimaryKey()),
                            ClubDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return clubDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ClubDescription> getClubDescriptionsByClub(Club club) {
        return getClubDescriptionsByClub(club, EntityPermission.READ_ONLY);
    }
    
    public List<ClubDescription> getClubDescriptionsByClubForUpdate(Club club) {
        return getClubDescriptionsByClub(club, EntityPermission.READ_WRITE);
    }
    
    public String getBestClubDescription(Club club, Language language) {
        String description;
        var clubDescription = getClubDescription(club, language);
        
        if(clubDescription == null && !language.getIsDefault()) {
            clubDescription = getClubDescription(club, partyControl.getDefaultLanguage());
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
        return clubDescriptionTransferCache.getClubDescriptionTransfer(userVisit, clubDescription);
    }
    
    public List<ClubDescriptionTransfer> getClubDescriptionTransfersByClub(UserVisit userVisit,
            Club club) {
        var clubDescriptions = getClubDescriptionsByClub(club);
        List<ClubDescriptionTransfer> clubDescriptionTransfers = new ArrayList<>(clubDescriptions.size());
        
        clubDescriptions.forEach((clubDescription) ->
                clubDescriptionTransfers.add(clubDescriptionTransferCache.getClubDescriptionTransfer(userVisit, clubDescription))
        );
        
        return clubDescriptionTransfers;
    }
    
    public void updateClubDescriptionFromValue(ClubDescriptionValue clubDescriptionValue, BasePK updatedBy) {
        if(clubDescriptionValue.hasBeenModified()) {
            var clubDescription = clubDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     clubDescriptionValue.getPrimaryKey());
            
            clubDescription.setThruTime(session.getStartTime());
            clubDescription.store();

            var club = clubDescription.getClub();
            var language = clubDescription.getLanguage();
            var description = clubDescriptionValue.getDescription();
            
            clubDescription = clubDescriptionFactory.create(club, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(club.getPrimaryKey(), EventTypes.MODIFY, clubDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteClubDescription(ClubDescription clubDescription, BasePK deletedBy) {
        clubDescription.setThruTime(session.getStartTime());
        
        sendEvent(clubDescription.getClubPK(), EventTypes.MODIFY,
                clubDescription.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteClubDescriptionsByClub(Club club, BasePK deletedBy) {
        var clubDescriptions = getClubDescriptionsByClubForUpdate(club);
        
        clubDescriptions.forEach((clubDescription) -> 
                deleteClubDescription(clubDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Club Item Types
    // --------------------------------------------------------------------------------

    @Inject
    protected ClubItemTypeFactory clubItemTypeFactory;

    @Inject
    ClubItemTypeTransferCache clubItemTypeTransferCache;

    public ClubItemType createClubItemType(String clubItemTypeName, Boolean isDefault, Integer sortOrder) {
        return clubItemTypeFactory.create(clubItemTypeName, isDefault, sortOrder);
    }
    
    public List<ClubItemType> getClubItemTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(ClubItemTypes.fields())
                .from(ClubItemTypes)
                .orderBy(ClubItemTypes.SORT_ORDER, ClubItemTypes.CLUB_ITEM_TYPE_NAME),
                ClubItemTypeFactory.class);

        return clubItemTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public ClubItemType getClubItemTypeByName(String clubItemTypeName) {
        var query = session.getDslContext()
                .select(ClubItemTypes.fields())
                .from(ClubItemTypes)
                .where(ClubItemTypes.CLUB_ITEM_TYPE_NAME.eq(clubItemTypeName));

        return clubItemTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public ClubItemTypeChoicesBean getClubItemTypeChoices(String defaultClubItemTypeChoice, Language language,
            boolean allowNullChoice) {
        var clubItemTypes = getClubItemTypes();
        var size = clubItemTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
        }
        
        for(var clubItemType : clubItemTypes) {
            var label = getBestClubItemTypeDescription(clubItemType, language);
            var value = clubItemType.getClubItemTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultClubItemTypeChoice != null && defaultClubItemTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && clubItemType.getIsDefault()))
                defaultValue = value;
        }
        
        return new ClubItemTypeChoicesBean(labels, values, defaultValue);
    }
    
    public ClubItemTypeTransfer getClubItemTypeTransfer(UserVisit userVisit, ClubItemType clubItemType) {
        return clubItemTypeTransferCache.getClubItemTypeTransfer(userVisit, clubItemType);
    }
    
    public List<ClubItemTypeTransfer> getClubItemTypeTransfers(UserVisit userVisit) {
        var clubItemTypes = getClubItemTypes();
        List<ClubItemTypeTransfer> clubItemTypeTransfers = new ArrayList<>(clubItemTypes.size());
        
        clubItemTypes.forEach((clubItemType) ->
                clubItemTypeTransfers.add(clubItemTypeTransferCache.getClubItemTypeTransfer(userVisit, clubItemType))
        );
        
        return clubItemTypeTransfers;
    }
    
    // --------------------------------------------------------------------------------
    //   Club Item Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ClubItemTypeDescriptionFactory clubItemTypeDescriptionFactory;

    public ClubItemTypeDescription createClubItemTypeDescription(ClubItemType clubItemType, Language language, String description) {
        return clubItemTypeDescriptionFactory.create(clubItemType, language, description);
    }
    
    public ClubItemTypeDescription getClubItemTypeDescription(ClubItemType clubItemType, Language language) {
        var query = session.getDslContext()
                .select(ClubItemTypeDescriptions.fields())
                .from(ClubItemTypeDescriptions)
                .where(ClubItemTypeDescriptions.CLUB_ITEM_TYPE.eq(clubItemType.getPrimaryKey()),
                        ClubItemTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return clubItemTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public String getBestClubItemTypeDescription(ClubItemType clubItemType, Language language) {
        String description;
        var clubItemTypeDescription = getClubItemTypeDescription(clubItemType, language);
        
        if(clubItemTypeDescription == null && !language.getIsDefault()) {
            clubItemTypeDescription = getClubItemTypeDescription(clubItemType, partyControl.getDefaultLanguage());
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

    @Inject
    protected ClubItemFactory clubItemFactory;

    @Inject
    ClubItemTransferCache clubItemTransferCache;

    public ClubItem createClubItem(Club club, ClubItemType clubItemType, Item item, Long subscriptionTime, BasePK createdBy) {
        var clubItem = clubItemFactory.create(club, clubItemType, item, subscriptionTime,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(club.getPrimaryKey(), EventTypes.MODIFY, clubItem.getPrimaryKey(), null, createdBy);
        
        return clubItem;
    }

    public long countClubItemsByClub(final Club club) {
        return session.getDslContext()
                .selectCount()
                .from(ClubItems)
                .where(ClubItems.CLUB.eq(club.getPrimaryKey()),
                        ClubItems.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countClubItemsByClubItemType(final ClubItemType clubItemType) {
        return session.getDslContext()
                .selectCount()
                .from(ClubItems)
                .where(ClubItems.CLUB_ITEM_TYPE.eq(clubItemType.getPrimaryKey()),
                        ClubItems.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countClubItemsByItem(final Item item) {
        return session.getDslContext()
                .selectCount()
                .from(ClubItems)
                .where(ClubItems.ITEM.eq(item.getPrimaryKey()),
                        ClubItems.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ClubItem getClubItem(Club club, ClubItemType clubItemType, Item item, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ClubItems.fields())
                .from(ClubItems)
                .where(ClubItems.CLUB.eq(club.getPrimaryKey()),
                        ClubItems.CLUB_ITEM_TYPE.eq(clubItemType.getPrimaryKey()),
                        ClubItems.ITEM.eq(item.getPrimaryKey()),
                        ClubItems.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return clubItemFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ClubItem getClubItem(Club club, ClubItemType clubItemType, Item item) {
        return getClubItem(club, clubItemType, item, EntityPermission.READ_ONLY);
    }
    
    public ClubItem getClubItemForUpdate(Club club, ClubItemType clubItemType, Item item) {
        return getClubItem(club, clubItemType, item, EntityPermission.READ_WRITE);
    }
    
    private List<ClubItem> getClubItemsByClub(Club club, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ClubItems.fields())
                    .from(ClubItems)
                    .join(ClubItemTypes).onKey(CLUB_ITEMS_CLUB_ITEM_TYPE_FK)
                    .join(Items).onKey(CLUB_ITEMS_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .where(ClubItems.CLUB.eq(club.getPrimaryKey()),
                            ClubItems.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ClubItemTypes.SORT_ORDER, ClubItemTypes.CLUB_ITEM_TYPE_NAME, ItemDetails.ITEM_NAME),
                    ClubItemFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ClubItems.fields())
                    .from(ClubItems)
                    .where(ClubItems.CLUB.eq(club.getPrimaryKey()),
                            ClubItems.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return clubItemFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ClubItem> getClubItemsByClub(Club club) {
        return getClubItemsByClub(club, EntityPermission.READ_ONLY);
    }
    
    public List<ClubItem> getClubItemsByClubForUpdate(Club club) {
        return getClubItemsByClub(club, EntityPermission.READ_WRITE);
    }
    
    private List<ClubItem> getClubItemsByItem(Item item, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ClubItems.fields())
                    .from(ClubItems)
                    .join(Clubs).onKey(CLUB_ITEMS_CLUB_FK)
                    .join(ClubDetails).onKey(CLUBS_LAST_DETAIL_FK)
                    .join(ClubItemTypes).onKey(CLUB_ITEMS_CLUB_ITEM_TYPE_FK)
                    .where(ClubItems.ITEM.eq(item.getPrimaryKey()),
                            ClubItems.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ClubDetails.SORT_ORDER, ClubDetails.CLUB_NAME, ClubItemTypes.SORT_ORDER, ClubItemTypes.CLUB_ITEM_TYPE_NAME),
                    ClubItemFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ClubItems.fields())
                    .from(ClubItems)
                    .where(ClubItems.ITEM.eq(item.getPrimaryKey()),
                            ClubItems.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return clubItemFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ClubItem> getClubItemsByItem(Item item) {
        return getClubItemsByItem(item, EntityPermission.READ_ONLY);
    }
    
    public List<ClubItem> getClubItemsByItemForUpdate(Item item) {
        return getClubItemsByItem(item, EntityPermission.READ_WRITE);
    }
    
    public ClubItemTransfer getClubItemTransfer(UserVisit userVisit, ClubItem clubItem) {
        return clubItemTransferCache.getClubItemTransfer(userVisit, clubItem);
    }
    
    public List<ClubItemTransfer> getClubItemTransfers(UserVisit userVisit, Collection<ClubItem> clubItems) {
        List<ClubItemTransfer> clubItemTransfers = new ArrayList<>(clubItems.size());
        
        clubItems.forEach((clubItem) ->
                clubItemTransfers.add(clubItemTransferCache.getClubItemTransfer(userVisit, clubItem))
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
            var clubItem = clubItemFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    clubItemValue.getPrimaryKey());
            
            clubItem.setThruTime(session.getStartTime());
            clubItem.store();

            var subscriptionTypePK = clubItem.getClubPK(); // Not updated
            var clubItemTypePK = clubItem.getClubItemTypePK(); // Not updated
            var itemPK = clubItem.getItemPK(); // Not updated
            var subscriptionTime = clubItemValue.getSubscriptionTime();
            
            clubItem = clubItemFactory.create(subscriptionTypePK, clubItemTypePK, itemPK,
                    subscriptionTime, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(clubItem.getClubPK(), EventTypes.MODIFY,
                    clubItem.getPrimaryKey(), null, updatedBy);
        }
    }
    
    public void deleteClubItem(ClubItem clubItem, BasePK deletedBy) {
        clubItem.setThruTime(session.getStartTime());
        
        sendEvent(clubItem.getClubPK(), EventTypes.MODIFY, clubItem.getPrimaryKey(), null, deletedBy);
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
